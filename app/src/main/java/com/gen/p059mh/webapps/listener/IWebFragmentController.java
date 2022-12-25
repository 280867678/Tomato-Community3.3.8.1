package com.gen.p059mh.webapps.listener;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.SoftKeyBoardListener;
import com.gen.p059mh.webapps.utils.WACrypto;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.listener.IWebFragmentController */
/* loaded from: classes2.dex */
public interface IWebFragmentController {

    /* renamed from: com.gen.mh.webapps.listener.IWebFragmentController$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static void $default$closeButtonHidden(IWebFragmentController iWebFragmentController, boolean z) {
        }

        public static void $default$dismiss(IWebFragmentController iWebFragmentController, WebViewLaunchFragment webViewLaunchFragment) {
        }

        public static void $default$display(IWebFragmentController iWebFragmentController, WebViewLaunchFragment webViewLaunchFragment) {
        }

        public static WebViewLaunchFragment $default$getFragment(IWebFragmentController iWebFragmentController) {
            return null;
        }

        public static void $default$gotoNewWebApp(IWebFragmentController iWebFragmentController, Uri uri, int i) {
        }

        public static void $default$gotoNewWebApp(IWebFragmentController iWebFragmentController, Map map, int i) {
        }

        public static void $default$loadApp(IWebFragmentController iWebFragmentController) {
        }

        public static void $default$reloadAppInfo(IWebFragmentController iWebFragmentController, String str, OnAppInfoResponse onAppInfoResponse) {
        }

        public static void $default$requestAppInfo(IWebFragmentController iWebFragmentController, String str, OnAppInfoResponse onAppInfoResponse) {
        }

        public static void $default$setNavigationBarTextStyle(IWebFragmentController iWebFragmentController) {
        }

        public static void $default$setScriptMenuIcons(IWebFragmentController iWebFragmentController, List list) {
        }
    }

    void addCoverView();

    void addListener(BackListener backListener);

    void addResultListeners(ActivityResultListener activityResultListener);

    void close();

    void closeButtonHidden(boolean z);

    void dismiss(WebViewLaunchFragment webViewLaunchFragment);

    void display(WebViewLaunchFragment webViewLaunchFragment);

    void executeEvent(String str, Object obj, JSResponseListener jSResponseListener);

    Plugin findPlugin(String str);

    Activity getActivity();

    AppController getAppController();

    File getAppFilesDir();

    String getAppID();

    FrameLayout getBackgroundNativeLayer();

    IWebBizOperation getBizOperation();

    FrameLayout getContentView();

    Context getContext();

    RelativeLayout getCoverView();

    String getDefaultsPath();

    WebViewLaunchFragment getFragment();

    Handler getHandler();

    String getHostStart();

    HashMap getInitParams();

    FrameLayout getNativeLayer();

    NativeViewPlugin getNativeViewPlugin();

    String getOriginalUrl();

    ResourcesLoader.ResourceType getResourceType();

    ServerPlugin getServerPlugin();

    SoftKeyBoardListener getSoftKeyboardController();

    File getTempDir();

    WACrypto getWACrypto();

    LinearLayout getWebParentContainer();

    WebappLifecycleSubject getWebappLifecycleSubject();

    Map getWindowConfig();

    String getWorkHost();

    String getWorkPath();

    void gotoNewWebApp(Uri uri, int i);

    void gotoNewWebApp(Map<String, Object> map, int i);

    void initWorkNativeView(String str);

    void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str);

    void initWorkUnity(String str);

    void initializerPlugins();

    void initializerUnitObject();

    boolean isAem();

    boolean isOnline();

    boolean isUseApi();

    void loadApp();

    void loadComplete(int i);

    void loadImage(String str, ImageCallBack<Drawable> imageCallBack);

    void onBackPressed();

    void onLoadPageError(RuntimeException runtimeException);

    void onLoadPageFinish(String str);

    void processConfigs();

    View provideView();

    String realPath(String str);

    void registerPlugin(Plugin plugin);

    void reloadAppInfo(String str, OnAppInfoResponse onAppInfoResponse);

    void removePlugins();

    void requestAppInfo(String str, OnAppInfoResponse onAppInfoResponse);

    void setDefaultsKey(String str);

    void setNavigationBarTextStyle();

    void setOnBackPressedListener(OnBackPressedListener onBackPressedListener);

    void setRequestedOrientation(int i);

    void setScriptMenuIcons(List<Map> list);

    void setServerPluginNone();

    void setStartLoaded(boolean z);

    void setWorkPath(String str);

    void webServerStop();
}
