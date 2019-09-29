package cn.vove7.plugin.rest.lineprovider

import cn.vove7.plugin.rest.model.RequestModel
import cn.vove7.plugin.rest.tool.open
import cn.vove7.plugin.rest.tool.trimValue
import cn.vove7.plugin.rest.tool.virtualFile
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.ui.ex.MessagesEx
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.psi.*
import com.intellij.ui.awt.RelativePoint
import java.awt.event.MouseEvent
import java.io.File
import java.util.*


/**
 * # BaseRetrofitLineMarkerProvider
 * Created by 11324.
 * Date: 2019/9/28
 */
abstract class RetrofitLineMarkerProvider : LineMarkerProvider {
    private val icon = AllIcons.Actions.Execute

    abstract fun getMethod(ele: PsiElement): PsiMethod?

    @Suppress("UNCHECKED_CAST")
    override fun getLineMarkerInfo(ele: PsiElement): LineMarkerInfo<*>? {
        val target = getMethod(ele) ?: return null
        if (target.isRetrofitMethod()) {
            return LineMarkerInfo(ele, ele.textRange, icon, 0,
                    { "Run Request" },
                    this::runRetrofitApiSafely,
                    GutterIconRenderer.Alignment.CENTER
            )
        }
        return null
    }

    /**
     * 当前元素
     * @receiver PsiMethod
     * @return Boolean
     */
    private fun PsiMethod.isRetrofitMethod(): Boolean {
        return annotations.any { it.qualifiedName in supportAnnotationNames }
    }

    /**
     * 生成rest文件名
     * @receiver PsiMethod
     * @param random Boolean
     * @return String
     */
    private fun PsiMethod.fileName(random: Boolean = false): String {
        return this.containingClass?.qualifiedName?.replace('.', '_') +
                "/" + name + (
                if (random) Random().nextInt(1000) else ""
                ) + ".rest"
    }

    /**
     * 根据注解解析 request url and method
     * @receiver PsiMethod
     * @return Pair<String?, String?>
     */
    private fun PsiMethod.parseMethodAndUrl(): Pair<String?, String?> {
        return annotations.find { it.qualifiedName in supportAnnotationNames }!!.let { ano ->
            if (ano.qualifiedName != "retrofit2.http.HTTP") {
                ano.qualifiedName!!.let {
                    it.substring(it.lastIndexOf('.') + 1)
                } to ano.parameterList.attributes[0].trimValue
            } else {
                val attrs = ano.parameterList.attributes
                Pair(
                        (attrs.find { it.name == "method" }?.trimValue ?: attrs[0].trimValue ?: ""),
                        (attrs.find { it.name == "path" }?.trimValue ?: "")
                )
            }
        }
    }


    private fun PsiMethod.apiMethodToRequestModel(): RequestModel? {

        val (methodStr, urlValue) = parseMethodAndUrl()

        if (methodStr == null) {
            throw Exception("Get request method failed!")
        }
        if (urlValue == null) {
            throw Exception("Get request url failed!")
        }
        return RequestModel().apply {
            method = RequestModel.Method.valueOf(methodStr)
            url = urlValue.let {
                if (it.startsWith("http")) it else "{BASE_URL}$it"
            }
            this@apiMethodToRequestModel.parseParametersAnnotation(this)
            if (method != RequestModel.Method.GET) {
                headers["Content-Type"] = contentType()
            }
        }
    }

    /**
     * 根据api fun解析contentType
     * @receiver PsiMethod
     * @return String
     */
    private fun PsiMethod.contentType(): String {
        //form
        if (annotations.any { it.qualifiedName == "retrofit2.http.FormUrlEncoded" }) {
            return "application/x-www-form-urlencoded"
        }
        if (parameterList.parameters.any { it.annotations.any { it.qualifiedName == "retrofit2.http.Body" } }) {
            return "application/json"
        }
        return ""
    }

    private fun runRetrofitApiSafely(e: MouseEvent, ele: PsiElement) {
        try {
            runRetrofitApi(e, ele)
        } catch (e: Throwable) {
            MessagesEx.showInfoMessage(e.message, "An error happened")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun runRetrofitApi(e: MouseEvent, element: PsiElement) {
        val ele = getMethod(element) ?: return
        val reqModel = ele.apiMethodToRequestModel() ?: return
        //目录
        val dir = ele.project.basePath + "/rest-client"
        val fileName = ele.fileName()
        val targetFile = File(dir, fileName).also {
            if (!it.parentFile.exists()) {
                it.parentFile.mkdirs()
            }
        }


        if (targetFile.exists().not()) {
            targetFile.writeText(reqModel.toRestFileContent(element.project))
            targetFile.virtualFile()?.open(ele.project)
        } else {
            JBPopupFactory.getInstance().createPopupChooserBuilder<String>(listOf(
                    "Open", "Create New File", "Overwrite"
            )).setTitle("File Already Exists").setItemChosenCallback {
                when (it) {
                    "Open" -> {
                        targetFile.virtualFile()?.open(ele.project)
                    }
                    "Overwrite" -> {
                        targetFile.virtualFile()?.apply {
                            ApplicationManager.getApplication().runWriteAction {
                                setBinaryContent(reqModel.toRestFileContent(element.project).toByteArray())
                                refresh(true, false)
                                open(ele.project)
                            }
                        }
                    }
                    "Create New File" -> {
                        val nf = File(dir, ele.fileName(true))
                        nf.writeText(reqModel.toRestFileContent(element.project))
                        nf.virtualFile()?.open(ele.project)
                    }
                }
            }.setCouldPin {
                true
            }.createPopup().show(RelativePoint(e))
        }
    }

    companion object {

        /**
         * 注解处理
         * Path -> url
         * Header -> headers
         * Field -> Form
         * Query -> url
         * Body -> json/xml
         * FieldMap 不处理
         * @receiver PsiMethod
         * @param reqModel RequestModel
         */
        fun PsiMethod.parseParametersAnnotation(reqModel: RequestModel) {
            parameterList.parameters.forEach {
                if (it.annotations.isNullOrEmpty())
                    return@forEach
                val anno = it.annotations[0]
                when (anno.qualifiedName) {
                    //解析Url Path 注解
                    "retrofit2.http.Path" -> {
                        val atts = anno.parameterList.attributes
                        val pathName = atts.find { it.name == "value" }?.trimValue
                            ?: atts[0].trimValue ?: return@forEach
                        reqModel.urlPaths.add(pathName)
                    }
                    "retrofit2.http.Header" -> {
                        val hname = anno.parameterList.attributes[0].trimValue ?: return@forEach
                        reqModel.headers[hname] = "{$hname}"
                    }
                    "retrofit2.http.Field" -> {
                        val fname = anno.parameterList.attributes[0].trimValue ?: return@forEach
                        reqModel.params[fname] = "{$fname}"
                    }
                    "retrofit2.http.Query" -> {
                        val qname = anno.parameterList.attributes[0].trimValue ?: return@forEach
                        reqModel.queries[qname] = "{$qname}"
                    }
                    "retrofit2.http.Body" -> {
                        it.type.getClass(this)?.allFields?.forEach { f ->
                            reqModel.bodyFields[f.name] = "{${f.name}}"
                        }
                    }
                }
            }
        }

        fun PsiType.getClass(ele: PsiElement): PsiClass? {
            if (this is PsiClassType) {
                return resolve()
            } else if (this is PsiPrimitiveType) {
                return getBoxedType(ele)!!.resolve()
            }
            return null
        }
    }

    private val supportAnnotationNames by lazy {
        arrayOf(
                "retrofit2.http.GET",
                "retrofit2.http.POST",
                "retrofit2.http.PATCH",
                "retrofit2.http.DELETE",
                "retrofit2.http.PUT",
                "retrofit2.http.OPTIONS",
                "retrofit2.http.HTTP"
        )
    }

}
