package cn.vove7.plugin.rest.model

import okhttp3.ResponseBody

class ResponseModel(
        val status: Int,
        val headers: List<String>,
        val contentType: String?,
        val body: ResponseBody?
) {

    fun headersString(): String = buildString {
        appendln()
        for (header in headers) {
            append("@").append(header).append("\n")
        }
        appendln()
    }

    fun getFilePostfix(): String = mapOf(
            "application/json" to ".json",
            "application/octet-stream" to "",
            "image/tiff" to ".tif",
            "application/x-001" to ".001",
            "application/x-301" to ".301",
            "text/h323" to ".323",
            "application/x-906" to ".906",
            "drawing/907" to ".907",
            "application/x-a11" to ".a11",
            "audio/x-mei-aac" to ".acp",
            "application/postscript" to ".ai",
            "audio/aiff" to ".aif",
            "audio/aiff" to ".aifc",
            "audio/aiff" to ".aiff",
            "application/x-anv" to ".anv",
            "text/asa" to ".asa",
            "video/x-ms-asf" to ".asf",
            "text/asp" to ".asp",
            "video/x-ms-asf" to ".asx",
            "audio/basic" to ".au",
            "video/avi" to ".avi",
            "application/vnd.adobe.workflow" to ".awf",
            "application/x-bmp" to ".bmp",
            "application/x-bot" to ".bot",
            "application/x-c4t" to ".c4t",
            "application/x-c90" to ".c90",
            "application/x-cals" to ".cal",
            "application/vnd.ms-pki.seccat" to ".cat",
            "application/x-netcdf" to ".cdf",
            "application/x-cdr" to ".cdr",
            "application/x-cel" to ".cel",
            "application/x-x509-ca-cert" to ".cer",
            "application/x-g4" to ".cg4",
            "application/x-cgm" to ".cgm",
            "application/x-cit" to ".cit",
            "java/*" to ".class",
            "application/x-cmp" to ".cmp",
            "application/x-cmx" to ".cmx",
            "application/x-cot" to ".cot",
            "application/pkix-crl" to ".crl",
            "application/x-x509-ca-cert" to ".crt",
            "application/x-csi" to ".csi",
            "text/css" to ".css",
            "application/x-cut" to ".cut",
            "application/x-dbf" to ".dbf",
            "application/x-dbm" to ".dbm",
            "application/x-dbx" to ".dbx",
            "application/x-dcx" to ".dcx",
            "application/x-x509-ca-cert" to ".der",
            "application/x-dgn" to ".dgn",
            "application/x-dib" to ".dib",
            "application/x-msdownload" to ".dll",
            "application/msword" to ".doc",
            "application/msword" to ".dot",
            "application/x-drw" to ".drw",
            "Model/vnd.dwf" to ".dwf",
            "application/x-dwf" to ".dwf",
            "application/x-dwg" to ".dwg",
            "application/x-dxb" to ".dxb",
            "application/x-dxf" to ".dxf",
            "application/vnd.adobe.edn" to ".edn",
            "application/x-emf" to ".emf",
            "message/rfc822" to ".eml",
            "application/x-epi" to ".epi",
            "application/x-ps" to ".eps",
            "application/postscript" to ".eps",
            "application/x-ebx" to ".etd",
            "application/x-msdownload" to ".exe",
            "image/fax" to ".fax",
            "application/vnd.fdf" to ".fdf",
            "application/fractals" to ".fif",
            "application/x-frm" to ".frm",
            "application/x-g4" to ".g4",
            "application/x-gbr" to ".gbr",
            "application/x-" to ".",
            "image/gif" to ".gif",
            "application/x-gl2" to ".gl2",
            "application/x-gp4" to ".gp4",
            "application/x-hgl" to ".hgl",
            "application/x-hmr" to ".hmr",
            "application/x-hpgl" to ".hpg",
            "application/x-hpl" to ".hpl",
            "application/mac-binhex40" to ".hqx",
            "application/x-hrf" to ".hrf",
            "application/hta" to ".hta",
            "text/x-component" to ".htc",
            "text/html" to ".html",
            "text/webviewhtml" to ".htt",
            "application/x-icb" to ".icb",
            "image/x-icon" to ".ico",
            "application/x-ico" to ".ico",
            "application/x-iff" to ".iff",
            "application/x-g4" to ".ig4",
            "application/x-igs" to ".igs",
            "application/x-iphone" to ".iii",
            "application/x-img" to ".img",
            "application/x-internet-signup" to ".ins",
            "application/x-internet-signup" to ".isp",
            "video/x-ivf" to ".IVF",
            "java/*" to ".java",
            "image/jpeg" to ".jfif",
            "image/jpeg" to ".jpe",
            "application/x-jpe" to ".jpe",
            "image/jpeg" to ".jpg",
            "application/x-jpg" to ".jpg",
            "application/x-javascript" to ".js",
            "audio/x-liquid-file" to ".la1",
            "application/x-laplayer-reg" to ".lar",
            "application/x-latex" to ".latex",
            "audio/x-liquid-secure" to ".lavs",
            "application/x-lbm" to ".lbm",
            "audio/x-la-lms" to ".lmsff",
            "application/x-javascript" to ".ls",
            "application/x-ltr" to ".ltr",
            "video/x-mpeg" to ".m2v",
            "audio/mpegurl" to ".m3u",
            "video/mpeg4" to ".m4e",
            "application/x-mac" to ".mac",
            "application/x-troff-man" to ".man",
            "application/msaccess" to ".mdb",
            "application/x-mdb" to ".mdb",
            "application/x-shockwave-flash" to ".mfp",
            "message/rfc822" to ".mht",
            "message/rfc822" to ".mhtml",
            "application/x-mi" to ".mi",
            "audio/mid" to ".mid",
            "application/x-mil" to ".mil",
            "audio/x-musicnet-download" to ".mnd",
            "audio/x-musicnet-stream" to ".mns",
            "application/x-javascript" to ".mocha",
            "video/x-sgi-movie" to ".movie",
            "audio/mp1" to ".mp1",
            "audio/mp2" to ".mp2",
            "video/mpeg" to ".mp2v",
            "audio/mp3" to ".mp3",
            "video/mpeg4" to ".mp4",
            "video/x-mpg" to ".mpa",
            "application/vnd.ms-project" to ".mpd",
            "video/x-mpeg" to ".mpe",
            "video/mpg" to ".mpeg",
            "audio/rn-mpeg" to ".mpga",
            "application/vnd.ms-project" to ".mpp",
            "video/x-mpeg" to ".mps",
            "application/vnd.ms-project" to ".mpt",
            "video/mpg" to ".mpv",
            "video/mpeg" to ".mpv2",
            "application/vnd.ms-project" to ".mpw",
            "application/vnd.ms-project" to ".mpx",
            "application/x-mmxp" to ".mxp",
            "image/pnetvue" to ".net",
            "application/x-nrf" to ".nrf",
            "message/rfc822" to ".nws",
            "text/x-ms-odc" to ".odc",
            "application/x-out" to ".out",
            "application/pkcs10" to ".p10",
            "application/x-pkcs12" to ".p12",
            "application/x-pkcs7-certificates" to ".p7b",
            "application/pkcs7-mime" to ".p7m",
            "application/x-pkcs7-certreqresp" to ".p7r",
            "application/pkcs7-signature" to ".p7s",
            "application/x-pc5" to ".pc5",
            "application/x-pci" to ".pci",
            "application/x-pcl" to ".pcl",
            "application/x-pcx" to ".pcx",
            "application/pdf" to ".pdf",
            "application/pdf" to ".pdf",
            "application/vnd.adobe.pdx" to ".pdx",
            "application/x-pkcs12" to ".pfx",
            "application/x-pgl" to ".pgl",
            "application/x-pic" to ".pic",
            "application/vnd.ms-pki.pko" to ".pko",
            "application/x-perl" to ".pl",
            "audio/scpls" to ".pls",
            "application/x-plt" to ".plt",
            "image/png" to ".png",
            "application/x-png" to ".png",
            "application/vnd.ms-powerpoint" to ".pot",
            "application/x-ppm" to ".ppm",
            "application/vnd.ms-powerpoint" to ".ppt",
            "application/x-ppt" to ".ppt",
            "application/x-pr" to ".pr",
            "application/pics-rules" to ".prf",
            "application/x-prn" to ".prn",
            "application/x-prt" to ".prt",
            "application/x-ps" to ".ps",
            "application/postscript" to ".ps",
            "application/x-ptn" to ".ptn",
            "application/vnd.ms-powerpoint" to ".pwz",
            "text/vnd.rn-realtext3d" to ".r3t",
            "audio/vnd.rn-realaudio" to ".ra",
            "audio/x-pn-realaudio" to ".ram",
            "application/x-ras" to ".ras",
            "application/rat-file" to ".rat",
            "application/vnd.rn-recording" to ".rec",
            "application/x-red" to ".red",
            "application/x-rgb" to ".rgb",
            "application/vnd.rn-realsystem-rjs" to ".rjs",
            "application/vnd.rn-realsystem-rjt" to ".rjt",
            "application/x-rlc" to ".rlc",
            "application/x-rle" to ".rle",
            "application/vnd.rn-realmedia" to ".rm",
            "application/vnd.adobe.rmf" to ".rmf",
            "audio/mid" to ".rmi",
            "application/vnd.rn-realsystem-rmj" to ".rmj",
            "audio/x-pn-realaudio" to ".rmm",
            "application/vnd.rn-rn_music_package" to ".rmp",
            "application/vnd.rn-realmedia-secure" to ".rms",
            "application/vnd.rn-realmedia-vbr" to ".rmvb",
            "application/vnd.rn-realsystem-rmx" to ".rmx",
            "application/vnd.rn-realplayer" to ".rnx",
            "image/vnd.rn-realpix" to ".rp",
            "audio/x-pn-realaudio-plugin" to ".rpm",
            "application/vnd.rn-rsml" to ".rsml",
            "text/vnd.rn-realtext" to ".rt",
            "application/msword" to ".rtf",
            "application/x-rtf" to ".rtf",
            "video/vnd.rn-realvideo" to ".rv",
            "application/x-sam" to ".sam",
            "application/x-sat" to ".sat",
            "application/sdp" to ".sdp",
            "application/x-sdw" to ".sdw",
            "application/x-stuffit" to ".sit",
            "application/x-slb" to ".slb",
            "application/x-sld" to ".sld",
            "drawing/x-slk" to ".slk",
            "application/smil" to ".smi",
            "application/smil" to ".smil",
            "application/x-smk" to ".smk",
            "audio/basic" to ".snd",
            "application/x-pkcs7-certificates" to ".spc",
            "application/futuresplash" to ".spl",
            "application/streamingmedia" to ".ssm",
            "application/vnd.ms-pki.certstore" to ".sst",
            "application/vnd.ms-pki.stl" to ".stl",
            "application/x-sty" to ".sty",
            "application/x-shockwave-flash" to ".swf",
            "application/x-tdf" to ".tdf",
            "application/x-tg4" to ".tg4",
            "application/x-tga" to ".tga",
            "image/tiff" to ".tif",
            "image/svg+xml" to ".svg",
            "application/x-tif" to ".tif",
            "image/tiff" to ".tiff",
            "drawing/x-top" to ".top",
            "application/x-bittorrent" to ".torrent",
            "text/plain" to ".txt",
            "application/x-icq" to ".uin",
            "text/iuls" to ".uls",
            "text/x-vcard" to ".vcf",
            "application/x-vda" to ".vda",
            "application/vnd.visio" to ".vdx",
            "application/x-vpeg005" to ".vpg",
            "application/vnd.visio" to ".vsd",
            "application/x-vsd" to ".vsd",
            "application/vnd.visio" to ".vss",
            "application/vnd.visio" to ".vst",
            "application/x-vst" to ".vst",
//            "application/vnd.visio" to ".vsw",
//            "application/vnd.visio" to ".vsx",
            "application/vnd.visio" to ".vtx",
            "audio/wav" to ".wav",
            "audio/x-ms-wax" to ".wax",
            "application/x-wb1" to ".wb1",
            "application/x-wb2" to ".wb2",
            "application/x-wb3" to ".wb3",
            "image/vnd.wap.wbmp" to ".wbmp",
            "application/msword" to ".wiz",
            "application/x-wk3" to ".wk3",
            "application/x-wk4" to ".wk4",
            "application/x-wkq" to ".wkq",
            "application/x-wks" to ".wks",
            "video/x-ms-wm" to ".wm",
            "audio/x-ms-wma" to ".wma",
            "application/x-ms-wmd" to ".wmd",
            "application/x-wmf" to ".wmf",
            "text/vnd.wap.wml" to ".wml",
            "video/x-ms-wmv" to ".wmv",
            "video/x-ms-wmx" to ".wmx",
            "application/x-ms-wmz" to ".wmz",
            "application/x-wp6" to ".wp6",
            "application/x-wpd" to ".wpd",
            "application/x-wpg" to ".wpg",
            "application/vnd.ms-wpl" to ".wpl",
            "application/x-wq1" to ".wq1",
            "application/x-wr1" to ".wr1",
            "application/x-wri" to ".wri",
            "application/x-wrk" to ".wrk",
            "application/x-ws" to ".ws",
            "application/x-ws" to ".ws2",
            "text/scriptlet" to ".wsc",
            "video/x-ms-wvx" to ".wvx",
            "application/vnd.adobe.xdp" to ".xdp",
            "application/vnd.adobe.xfd" to ".xfd",
            "application/vnd.adobe.xfdf" to ".xfdf",
            "application/vnd.ms-excel" to ".xls",
            "application/x-xls" to ".xls",
            "application/x-xlw" to ".xlw",
            "text/xml" to ".xml",
            "audio/scpls" to ".xpl",
            "application/x-xwd" to ".xwd",
            "application/x-x_b" to ".x_b",
            "application/vnd.symbian.install" to ".sisx",
            "application/x-x_t" to ".x_t",
            "application/vnd.iphone" to ".ipa",
            "application/vnd.android.package-archive" to ".apk",
            "application/x-silverlight-app" to ".xap"
    )[contentType] ?: ""

    fun isUnText(): Boolean {
        //TODO
        contentType ?: return false
        return contentType.startsWith("audio/") ||
                contentType.startsWith("video/") ||
                contentType.startsWith("image/") ||
                contentType == "application/vnd.android.package-archive" ||
                contentType == "application/x-silverlight-app" ||
                contentType == "application/x-msdownload"
    }

}