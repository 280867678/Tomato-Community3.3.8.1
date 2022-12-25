package com.gen.p059mh.webapp_extensions;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapp_extensions.matisse.engine.impl.GlideEngine;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;
import com.p076mh.webappStart.util.GlobalMagicJavaUtil;
import java.io.File;

/* renamed from: com.gen.mh.webapp_extensions.WebApplication */
/* loaded from: classes2.dex */
public class WebApplication {
    private static final String TAG = "WebApplication";
    private static WebApplication webApplication = new WebApplication();
    private String imgUrl;
    private Application sysApplication;
    private String tempRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "temp";

    public WebApplication init(Application application) {
        GlobalMagicJavaUtil.init(application);
        this.sysApplication = application;
        SelectionSpec.getInstance().imageEngine = new GlideEngine();
        return this;
    }

    public WebApplication setWebEngine(Object obj) {
        if (obj != null) {
            WebEngineManager.getInstance().setWebEngine(obj);
        }
        return this;
    }

    public WebApplication setDebug(boolean z) {
        Logger.DEBUG = z;
        return this;
    }

    public WebApplication setWebViewDebug(boolean z) {
        WebEngineManager.WebViewDebug = z;
        return this;
    }

    public WebApplication setImageEngine(ImageEngine imageEngine) {
        if (imageEngine != null) {
            SelectionSpec.getInstance().imageEngine = imageEngine;
            imageEngine.init(this.sysApplication);
        }
        return this;
    }

    public static WebApplication getInstance() {
        WebApplication webApplication2 = webApplication;
        if (webApplication2 != null) {
            return webApplication2;
        }
        return null;
    }

    public Application getApplication() {
        return this.sysApplication;
    }

    public void setTempRoot(String str) {
        Log.i(TAG, "setTempRoot: " + str);
        this.tempRoot = str;
    }

    public String getTempRoot() {
        return this.tempRoot;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String str) {
        this.imgUrl = str;
    }
}
