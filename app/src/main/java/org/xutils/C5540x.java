package org.xutils;

import android.app.Application;
import android.content.Context;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import org.xutils.DbManager;
import org.xutils.common.TaskController;
import org.xutils.common.task.TaskControllerImpl;
import org.xutils.http.HttpManagerImpl;
import org.xutils.image.ImageManagerImpl;
import org.xutils.p148db.DbManagerImpl;
import org.xutils.view.ViewInjectorImpl;

/* renamed from: org.xutils.x */
/* loaded from: classes4.dex */
public final class C5540x {
    private C5540x() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static Application app() {
        if (Ext.app == null) {
            Application unused = Ext.app = new MockApplication((Context) Class.forName("com.android.layoutlib.bridge.impl.RenderAction").getDeclaredMethod("getCurrentContext", new Class[0]).invoke(null, new Object[0]));
        }
        return Ext.app;
    }

    public static TaskController task() {
        return Ext.taskController;
    }

    public static HttpManager http() {
        if (Ext.httpManager == null) {
            HttpManagerImpl.registerInstance();
        }
        return Ext.httpManager;
    }

    public static ImageManager image() {
        if (Ext.imageManager == null) {
            ImageManagerImpl.registerInstance();
        }
        return Ext.imageManager;
    }

    public static ViewInjector view() {
        if (Ext.viewInjector == null) {
            ViewInjectorImpl.registerInstance();
        }
        return Ext.viewInjector;
    }

    public static DbManager getDb(DbManager.DaoConfig daoConfig) {
        return DbManagerImpl.getInstance(daoConfig);
    }

    /* renamed from: org.xutils.x$Ext */
    /* loaded from: classes4.dex */
    public static class Ext {
        private static Application app;
        private static boolean debug;
        private static HttpManager httpManager;
        private static ImageManager imageManager;
        private static TaskController taskController;
        private static ViewInjector viewInjector;

        private Ext() {
        }

        public static void init(Application application) {
            TaskControllerImpl.registerInstance();
            if (app == null) {
                app = application;
            }
        }

        public static void setDebug(boolean z) {
            debug = z;
        }

        public static void setTaskController(TaskController taskController2) {
            if (taskController == null) {
                taskController = taskController2;
            }
        }

        public static void setHttpManager(HttpManager httpManager2) {
            httpManager = httpManager2;
        }

        public static void setImageManager(ImageManager imageManager2) {
            imageManager = imageManager2;
        }

        public static void setViewInjector(ViewInjector viewInjector2) {
            viewInjector = viewInjector2;
        }

        public static void setDefaultHostnameVerifier(HostnameVerifier hostnameVerifier) {
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        }
    }

    /* renamed from: org.xutils.x$MockApplication */
    /* loaded from: classes4.dex */
    private static class MockApplication extends Application {
        public MockApplication(Context context) {
            attachBaseContext(context);
        }
    }
}
