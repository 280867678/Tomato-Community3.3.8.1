package com.p076mh.webappStart.util;

import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import java.io.File;

/* renamed from: com.mh.webappStart.util.TempUtil */
/* loaded from: classes3.dex */
public class TempUtil {
    public static String getTempFilePathFromSimpleFileName(String str) {
        return WebApplication.getInstance().getTempRoot() + File.separator + "dm_" + TimeUtils.getCurrentTimeForMakeName() + str;
    }

    public static String getTempFilePathFromAbsoluteFilePath(String str) {
        return WebApplication.getInstance().getTempRoot() + File.separator + "dm_" + TimeUtils.getCurrentTimeForMakeName() + new File(str).getName();
    }

    public static String getWxTempPathFromLocalPath(IWebFragmentController iWebFragmentController, String str) {
        return "tmp:///" + str.replace(iWebFragmentController.getTempDir().getAbsolutePath(), "");
    }
}
