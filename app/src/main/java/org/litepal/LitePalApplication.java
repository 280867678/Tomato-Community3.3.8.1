package org.litepal;

import android.app.Application;
import android.content.Context;
import org.litepal.exceptions.GlobalException;

/* loaded from: classes4.dex */
public class LitePalApplication extends Application {
    public static Context sContext;

    public LitePalApplication() {
        sContext = this;
    }

    public static Context getContext() {
        Context context = sContext;
        if (context != null) {
            return context;
        }
        throw new GlobalException("Application context is null. Maybe you neither configured your application name with \"org.litepal.LitePalApplication\" in your AndroidManifest.xml, nor called LitePal.initialize(Context) method.");
    }
}
