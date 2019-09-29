// This is a generated file. Not intended for manual editing.
package cn.vove7.plugin.rest.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static cn.vove7.plugin.rest.psi.RestTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import cn.vove7.plugin.rest.psi.*;

public class RestParamsImpl extends ASTWrapperPsiElement implements RestParams {

  public RestParamsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RestVisitor visitor) {
    visitor.visitParams(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RestVisitor) accept((RestVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<RestEParam> getEParamList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RestEParam.class);
  }

  @Override
  @NotNull
  public List<RestWs> getWsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RestWs.class);
  }

}
