package com.gen.p059mh.webapps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.database.Table;
import com.gen.p059mh.webapps.listener.ActivityResultListener;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.ControllerProvider;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.ImageCallBack;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.listener.OnBackPressedListener;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.listener.WebappLifecycleSubject;
import com.gen.p059mh.webapps.modules.AppData;
import com.gen.p059mh.webapps.modules.LoadErrorInfo;
import com.gen.p059mh.webapps.pugins.ClosePlugin;
import com.gen.p059mh.webapps.pugins.CommonJSPlugin;
import com.gen.p059mh.webapps.pugins.CompletePlugin;
import com.gen.p059mh.webapps.pugins.HrefPlugin;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.pugins.RequestPlugin;
import com.gen.p059mh.webapps.pugins.ResponsePlugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.pugins.SettingsPlugin;
import com.gen.p059mh.webapps.pugins.StoragePlugin;
import com.gen.p059mh.webapps.pugins.SystemInfoPlugin;
import com.gen.p059mh.webapps.pugins.WorkPlugin;
import com.gen.p059mh.webapps.server.AndroidDefaultServer;
import com.gen.p059mh.webapps.server.AndroidWorkServer;
import com.gen.p059mh.webapps.server.worker.GameWorker;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.unity.UnityPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.NetUtils;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.SoftKeyBoardListener;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.utils.WACrypto;
import com.gen.p059mh.webapps.utils.interpolator.EaseInCubicInterpolator;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;
import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.gen.mh.webapps.WebViewFragment */
/* loaded from: classes2.dex */
public class WebViewFragment extends Fragment implements Plugin.Executor, IWebBizOperation, IWebFragmentController, PageLoadFinishListener {
    public static String currentApkId = "";
    public static String currentDefaultKey;
    AndroidDefaultServer androidWebServer;
    AndroidWorkServer androidWorkServer;
    private AppData appData;
    File appFilesDir;
    protected String appID;
    private FrameLayout backgroundNativeLayer;
    FrameLayout contentView;
    RelativeLayout coverView;
    String defaultsKey;
    private String defaultsPath;
    protected Handler handler;
    String hostStart;
    private boolean isLocalDefaults;
    ResourcesLoader loader;
    private Context mContext;
    private WebViewRunnable mWebViewRunnable;
    private FrameLayout nativeLayer;
    private NativeViewPlugin nativeViewPlugin;
    OnBackPressedListener onBackPressedListener;
    ResourcesLoader.ResourceType resourceType;
    ResponsePlugin responsePlugin;
    ServerPlugin serverPlugin;
    SoftKeyBoardListener softKeyboardController;
    File tempDir;
    private UnityPlugin unityPlugin;
    WACrypto waCrypto;
    LinearLayout webParentContainer;
    private WebEngine webView;
    protected WebappLifecycleSubject webappLifecycleSubject;
    String workHost;
    private String workPath;
    NativeViewPlugin workerNativeViewPlugin;
    UnityPlugin workerUnityPlugin;
    boolean webViewLoaded = false;
    boolean isLoaded = false;
    protected boolean isNeedBoardAppear = false;
    boolean isCloseButtonHidden = false;
    private boolean startLoaded = false;
    private Hashtable<String, Plugin> plugins = new Hashtable<>();
    private Hashtable<String, String> cacheData = new Hashtable<>();
    protected HashMap<String, Object> initParams = new HashMap<>();
    ArrayList<BackListener> listeners = new ArrayList<>();
    public boolean isForeground = false;
    private List<ActivityResultListener> activityResultListeners = new ArrayList();
    Runnable closeCallback = new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.8
        @Override // java.lang.Runnable
        public void run() {
            Logger.m4113i("close call back");
            WebViewFragment webViewFragment = WebViewFragment.this;
            if (webViewFragment.webViewLoaded) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(webViewFragment.coverView, "alpha", 1.0f, 0.0f);
                ofFloat.setInterpolator(new EaseInCubicInterpolator());
                ofFloat.setDuration(300L);
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.gen.mh.webapps.WebViewFragment.8.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        WebViewFragment.this.getContentView().removeView(WebViewFragment.this.coverView);
                        WebViewFragment.this.animationEnd();
                    }
                });
                WebViewFragment.this.loadEnd();
                ofFloat.start();
            }
        }
    };

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void animationEnd() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void checkPermissionAndStart(Intent intent, int i, PhotoSwitchListener photoSwitchListener) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void closeButtonHidden(boolean z) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void dismiss(WebViewLaunchFragment webViewLaunchFragment) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public /* synthetic */ void display(WebViewLaunchFragment webViewLaunchFragment) {
        IWebFragmentController.CC.$default$display(this, webViewLaunchFragment);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void doSwitchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public AppController getAppController() {
        return null;
    }

    public Context getBaseContext() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public IWebBizOperation getBizOperation() {
        return this;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public /* synthetic */ WebViewLaunchFragment getFragment() {
        return IWebFragmentController.CC.$default$getFragment(this);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Handler getHandler() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Map getWindowConfig() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void gotoNewWebApp(Uri uri, int i) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public /* synthetic */ void gotoNewWebApp(Map<String, Object> map, int i) {
        IWebFragmentController.CC.$default$gotoNewWebApp(this, map, i);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkNativeView(String str) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkUnity(String str) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerUnitObject() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isAem() {
        return false;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isOnline() {
        return false;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isUseApi() {
        return false;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void loadApp() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void loadEnd() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void loadImage(String str, ImageCallBack<Drawable> imageCallBack) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController, com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageError(RuntimeException runtimeException) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void onRotateLandscape() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void processConfigs() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void reloadAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void requestAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void setMenuIcons(List<Map> list) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setNavigationBarTextStyle() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setRequestedOrientation(int i) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setScriptMenuIcons(List<Map> list) {
    }

    WebViewFragment() {
    }

    @Override // android.support.p002v4.app.Fragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    @Nullable
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getWorkHost() {
        return this.workHost;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getHostStart() {
        return this.hostStart;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public View provideView() {
        return getWebView().provideView();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public RelativeLayout getCoverView() {
        return this.coverView;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addResultListeners(ActivityResultListener activityResultListener) {
        if (activityResultListener != null) {
            this.activityResultListeners.add(activityResultListener);
        }
    }

    public void removeResultListener(ActivityResultListener activityResultListener) {
        if (activityResultListener != null) {
            this.activityResultListeners.remove(activityResultListener);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public WebappLifecycleSubject getWebappLifecycleSubject() {
        return this.webappLifecycleSubject;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public HashMap<String, Object> getInitParams() {
        return this.initParams;
    }

    public void setInitParams(HashMap<String, Object> hashMap) {
        this.initParams = hashMap;
    }

    public void setCloseButtonHidden(boolean z) {
        this.isCloseButtonHidden = z;
    }

    public boolean isCloseButtonHidden() {
        return this.isCloseButtonHidden;
    }

    public WebEngine getWebView() {
        return this.webView;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public FrameLayout getNativeLayer() {
        return this.nativeLayer;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public FrameLayout getBackgroundNativeLayer() {
        return this.backgroundNativeLayer;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getDefaultsPath() {
        return this.defaultsPath;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getWorkPath() {
        return this.workPath;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: com.gen.mh.webapps.WebViewFragment$JavascriptObject */
    /* loaded from: classes2.dex */
    public class JavascriptObject {
        public JavascriptObject() {
            Logger.m4115e("start construct javascriptObject");
        }

        @JavascriptInterface
        public void _call(String str, String str2, final String str3) {
            if (WebViewFragment.this.plugins.containsKey(str)) {
                try {
                    ((Plugin) WebViewFragment.this.plugins.get(str)).process(str2, new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.WebViewFragment.JavascriptObject.1
                        @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                        public void response(Object obj) {
                            final String json = new Gson().toJson(obj);
                            WebViewFragment.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.JavascriptObject.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    WebViewFragment.this.cacheData.put(str3, Base64.encodeToString(json.getBytes(), 0));
                                    WebEngine webEngine = WebViewFragment.this.webView;
                                    webEngine.loadUrl("javascript:window.api._callback('" + str3 + "')");
                                }
                            });
                        }
                    });
                    return;
                } catch (Exception e) {
                    Log.i("ProcessError", "Error when process " + str);
                    e.printStackTrace();
                    WebViewFragment.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.JavascriptObject.2
                        @Override // java.lang.Runnable
                        public void run() {
                            WebViewFragment.this.cacheData.put(str3, Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0));
                            WebEngine webEngine = WebViewFragment.this.webView;
                            webEngine.loadUrl("javascript:window.api._callback('" + str3 + "')");
                        }
                    });
                    return;
                }
            }
            WebViewFragment.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.JavascriptObject.3
                @Override // java.lang.Runnable
                public void run() {
                    WebViewFragment.this.cacheData.put(str3, Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0));
                    WebEngine webEngine = WebViewFragment.this.webView;
                    webEngine.loadUrl("javascript:window.api._callback('" + str3 + "')");
                }
            });
        }

        @JavascriptInterface
        public String _callSync(String str, String str2) {
            if (WebViewFragment.this.plugins.containsKey(str)) {
                Plugin plugin = (Plugin) WebViewFragment.this.plugins.get(str);
                try {
                    final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                    final StringBuilder sb = new StringBuilder();
                    plugin.process(str2, new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.WebViewFragment.JavascriptObject.4
                        boolean called = false;

                        @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                        public void response(Object obj) {
                            this.called = true;
                            sb.append(Base64.encodeToString(new Gson().toJson(obj).getBytes(), 0));
                            synchronized (atomicBoolean) {
                                atomicBoolean.set(true);
                                atomicBoolean.notify();
                            }
                        }

                        protected void finalize() throws Throwable {
                            if (!this.called) {
                                response(null);
                            }
                            super.finalize();
                        }
                    });
                    synchronized (atomicBoolean) {
                        if (atomicBoolean.get()) {
                            return sb.toString();
                        }
                        atomicBoolean.wait();
                        return sb.toString();
                    }
                } catch (Exception e) {
                    Log.i("ProcessError", "Error when process " + str);
                    e.printStackTrace();
                    return Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0);
                }
            }
            return Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0);
        }

        @JavascriptInterface
        public String _getData(String str) {
            String str2 = (String) WebViewFragment.this.cacheData.get(str);
            WebViewFragment.this.cacheData.remove(str);
            return str2;
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public FrameLayout getContentView() {
        return this.contentView;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public LinearLayout getWebParentContainer() {
        return this.webParentContainer;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public NativeViewPlugin getNativeViewPlugin() {
        if (this.nativeViewPlugin == null) {
            this.nativeViewPlugin = new NativeViewPlugin();
        }
        return this.nativeViewPlugin;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public File getTempDir() {
        return this.tempDir;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public File getAppFilesDir() {
        if (this.appFilesDir == null) {
            if (this.workPath == null || !isAdded()) {
                return null;
            }
            String[] split = this.workPath.split("/");
            if (split.length > 0) {
                String str = split[split.length - 1];
                this.appFilesDir = new File(getContext().getFilesDir().toString() + "/webapps.datas/" + str);
                if (!this.appFilesDir.exists()) {
                    this.appFilesDir.mkdirs();
                }
            }
        }
        return this.appFilesDir;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String realPath(String str) {
        URI create;
        try {
            create = URI.create(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("tmp".equals(create.getScheme())) {
            return (getTempDir().toString() + "/" + create.getPath()).replace("//", "/");
        }
        if ("app".equals(create.getScheme())) {
            return (getAppFilesDir().toString() + "/" + create.getPath()).replace("//", "/");
        }
        return str;
    }

    public void setWorkKey(String str) {
        this.waCrypto.setWorkKey(str);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setDefaultsKey(String str) {
        this.defaultsKey = str;
        if (str != null) {
            currentDefaultKey = str;
        }
        if (this.defaultsKey == null) {
            this.defaultsKey = currentDefaultKey;
        }
        this.waCrypto.setDefaultKey(str);
    }

    public void setAppID(String str) {
        this.appID = str;
        currentApkId = str;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.tempDir = new File(getContext().getFilesDir().getAbsoluteFile() + "/.temp" + Math.random());
        if (!this.tempDir.exists()) {
            this.tempDir.mkdirs();
        }
        this.waCrypto = new WACrypto();
        this.webappLifecycleSubject = new WebappLifecycleSubject();
        this.webappLifecycleSubject.onCreate();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    /* renamed from: close */
    public void lambda$null$2$WebViewFragment() {
        if (this.webViewLoaded) {
            executeEvent("exit", null, new JSResponseListener() { // from class: com.gen.mh.webapps.WebViewFragment.1
                @Override // com.gen.p059mh.webapps.listener.JSResponseListener
                public void onResponse(Object obj) {
                    WebViewFragment webViewFragment = WebViewFragment.this;
                    webViewFragment.webViewLoaded = false;
                    webViewFragment.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WebViewFragment.this.lambda$null$2$WebViewFragment();
                        }
                    });
                }
            });
            return;
        }
        for (String str : this.plugins.keySet()) {
            this.plugins.get(str).unload();
        }
        this.tempDir.deleteOnExit();
        OnBackPressedListener onBackPressedListener = this.onBackPressedListener;
        if (onBackPressedListener == null) {
            return;
        }
        onBackPressedListener.onPressed();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void removePlugins() {
        new Handler().post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.2
            @Override // java.lang.Runnable
            public void run() {
                for (String str : WebViewFragment.this.plugins.keySet()) {
                    ((Plugin) WebViewFragment.this.plugins.get(str)).unload();
                }
                WebViewFragment.this.plugins.clear();
                WebViewFragment.this.tempDir.deleteOnExit();
                Logger.m4113i("remove plugins");
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        WebViewRunnable webViewRunnable;
        super.onDestroy();
        WebappLifecycleSubject webappLifecycleSubject = this.webappLifecycleSubject;
        if (webappLifecycleSubject != null) {
            webappLifecycleSubject.onDestroy();
        }
        HashMap hashMap = new HashMap();
        hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "background");
        executeEvent("window.state", hashMap, null);
        webServerStop();
        for (String str : this.plugins.keySet()) {
            this.plugins.get(str).unload();
        }
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.destroy();
        }
        Handler handler = this.handler;
        if (handler == null || (webViewRunnable = this.mWebViewRunnable) == null) {
            return;
        }
        handler.removeCallbacks(webViewRunnable);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void webServerStop() {
        if (this.androidWebServer == null || this.androidWorkServer == null) {
            return;
        }
        Logger.m4113i("stop sever");
        this.androidWebServer.stop();
        this.androidWorkServer.stop();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addCoverView() {
        getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewFragment$YbZGlQOMyHd5hJHJDDkBVVGdCFA
            @Override // java.lang.Runnable
            public final void run() {
                WebViewFragment.this.lambda$addCoverView$0$WebViewFragment();
            }
        });
    }

    public /* synthetic */ void lambda$addCoverView$0$WebViewFragment() {
        this.coverView.setAlpha(1.0f);
        this.coverView.setBackgroundColor(-1);
        this.contentView.addView(this.coverView);
        Logger.m4113i("addCoverView");
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setStartLoaded(boolean z) {
        this.startLoaded = z;
        Logger.m4113i("setStartLoaded");
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.onPause();
            if (this.isNeedBoardAppear) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.webView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                this.webView.setLayoutParams(layoutParams);
            }
        }
        HashMap hashMap = new HashMap();
        if (!Utils.isAppOnForeground(getContext())) {
            this.isForeground = false;
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "disappear");
        } else {
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "background");
        }
        executeEvent("window.state", hashMap, null);
        executeEvent("window.state", hashMap, null);
        for (String str : this.plugins.keySet()) {
            this.plugins.get(str).onHide();
        }
        this.webappLifecycleSubject.onPause();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.onResume();
        }
        HashMap hashMap = new HashMap();
        if (!this.isForeground) {
            this.isForeground = true;
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "appear");
        } else {
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "foreground");
        }
        executeEvent("window.state", hashMap, null);
        for (String str : this.plugins.keySet()) {
            this.plugins.get(str).onShow();
        }
        this.webappLifecycleSubject.onResume();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public SoftKeyBoardListener getSoftKeyboardController() {
        return this.softKeyboardController;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // android.support.p002v4.app.Fragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    @Nullable
    public Context getContext() {
        if (super.getContext() != null) {
            return super.getContext();
        }
        Context context = this.mContext;
        return context != null ? context : getBaseContext();
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Table.setup(getContext());
        this.softKeyboardController = new SoftKeyBoardListener(getActivity());
        this.softKeyboardController.addListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() { // from class: com.gen.mh.webapps.WebViewFragment.3
            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardShow(int i) {
                WebViewFragment webViewFragment = WebViewFragment.this;
                if (webViewFragment.isNeedBoardAppear) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webViewFragment.webView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, i);
                    WebViewFragment.this.webView.setLayoutParams(layoutParams);
                    WebViewFragment.this.webView.requestLayout();
                }
            }

            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardHide(int i) {
                WebViewFragment webViewFragment = WebViewFragment.this;
                if (webViewFragment.isNeedBoardAppear) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webViewFragment.webView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    WebViewFragment.this.webView.setLayoutParams(layoutParams);
                    WebViewFragment.this.webView.requestLayout();
                }
            }
        });
        this.handler = new Handler();
        this.contentView = new FrameLayout(getContext());
        this.contentView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.webParentContainer = new LinearLayout(getContext());
        this.webParentContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.webParentContainer.setOrientation(1);
        this.backgroundNativeLayer = new FrameLayout(getContext());
        this.contentView.addView(this.backgroundNativeLayer);
        initWebView();
        this.isLoaded = true;
        if (this.defaultsPath != null && this.workPath != null) {
            loadWebView();
        }
        return this.contentView;
    }

    private void initWebView() {
        this.contentView.addView(this.webParentContainer);
        this.webView = WebEngineManager.getInstance().initWebEngine().init(getContext());
        this.webView.setPadding(0, 0, 0, 0);
        this.webView.setUserAgent(this.webView.getUserAgentString() + " WebApp/Android/" + ((Map) Utils.launchSettings(getActivity(), this.workPath).get("systemInfo")).get("SDKVersion"));
        this.webView.addJavascriptInterface(new JavascriptObject(), "_api");
        this.webView.setWebViewCallback(this);
        this.webView.setPageLoadFinishCallBack(this);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.webParentContainer.addView(this.webView.provideView());
        this.webView.setBackgroundColor(Color.parseColor("#00000000"));
        this.nativeLayer = new FrameLayout(getContext());
        this.contentView.addView(this.nativeLayer);
        this.coverView = new RelativeLayout(getContext());
        this.coverView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.contentView.addView(this.coverView);
        MimeTypes.getInstance().register(new MimeType("text/css", "wxss"));
        MimeTypes.getInstance().register(new MimeType("text/xml", "wxml"));
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        List<ActivityResultListener> list = this.activityResultListeners;
        if (list != null) {
            Iterator<ActivityResultListener> it2 = list.iterator();
            while (it2.hasNext() && !it2.next().onSdkActivityResult(i, i2, intent)) {
            }
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void onScrollerChange(int i, int i2) {
        getWebappLifecycleSubject().onWebScrollerChange(i, i2);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public WebEngine getController() {
        return this.webView;
    }

    public <T extends NativeViewPlugin.NativeView> void registerNativeView(Class<T> cls, String str) {
        getNativeViewPlugin().registerNativeView(cls, str);
    }

    public <T extends Unity> void registerUnity(Class<T> cls, String str) {
        getUnityPlugin().registerUnity(cls, str);
    }

    public UnityPlugin getUnityPlugin() {
        if (this.unityPlugin == null) {
            this.unityPlugin = new UnityPlugin();
        }
        return this.unityPlugin;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public ServerPlugin getServerPlugin() {
        if (this.serverPlugin == null) {
            this.serverPlugin = new ServerPlugin();
        }
        return this.serverPlugin;
    }

    public void setServerPlugin(ServerPlugin serverPlugin) {
        this.serverPlugin = serverPlugin;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setServerPluginNone() {
        this.serverPlugin = null;
        Logger.m4113i("setServerPlugin");
    }

    public void setDefaultsPath(String str) {
        this.defaultsPath = str;
        if (this.defaultsPath == null || this.workPath == null || !this.isLoaded) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.4
            @Override // java.lang.Runnable
            public void run() {
                WebViewFragment.this.loadWebView();
            }
        });
    }

    public void setLocalDefaults(boolean z) {
        this.isLocalDefaults = z;
    }

    public boolean isLocalDefaults() {
        return this.isLocalDefaults;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setWorkPath(String str) {
        Logger.m4112i("setWorkPath", "setWorkPath" + str);
        this.workPath = str;
        if (this.defaultsPath == null || this.workPath == null || !this.isLoaded) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewFragment.5
            @Override // java.lang.Runnable
            public void run() {
                WebViewFragment.this.loadWebView();
            }
        });
    }

    protected void loadFail(IErrorInfo iErrorInfo) {
        this.startLoaded = false;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public ResourcesLoader.ResourceType getResourceType() {
        return this.resourceType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadWebView() {
        Logger.m4112i("loadWebView", this.appID + " ___ " + this.workPath);
        if (this.startLoaded) {
            return;
        }
        Logger.m4112i("loadWebView", this.appID + " ___ " + this.workPath);
        initHost();
        initializerPlugins();
        initializerUnitObject();
        this.loader = new ResourcesLoader(new File(this.defaultsPath), this.waCrypto);
        this.loader.setControllerProvider(new ControllerProvider() { // from class: com.gen.mh.webapps.WebViewFragment.6
            GameWorker gameWorker;

            @Override // com.gen.p059mh.webapps.listener.ControllerProvider
            public void ready2Load(String str, String str2, String str3) {
                char c;
                int hashCode = str.hashCode();
                if (hashCode != 117588) {
                    if (hashCode == 3165170 && str.equals("game")) {
                        c = 1;
                    }
                    c = 65535;
                } else {
                    if (str.equals("web")) {
                        c = 0;
                    }
                    c = 65535;
                }
                if (c == 0) {
                    WebViewFragment.this.webView.loadUrl(str2);
                } else if (c != 1) {
                    WebViewFragment.this.webView.loadDataWithBaseURL(str2, str3, "text/html", "utf8", str2);
                } else {
                    if (this.gameWorker == null) {
                        this.gameWorker = new GameWorker(WebViewFragment.this);
                    }
                    this.gameWorker.start(str2);
                }
            }
        });
        this.resourceType = this.loader.typeFor(new File(this.workPath));
        ResourcesLoader.ResourceType resourceType = this.resourceType;
        if (resourceType != null) {
            processConfigs(resourceType.configs());
            this.resourceType.checkLoad();
            this.startLoaded = true;
            return;
        }
        loadFail(LoadErrorInfo.UNKNOWN_TYPE);
    }

    private void initHost() {
        String[] split = getWorkPath().substring(getWorkPath().indexOf("files")).replaceAll("[.]", "/").split("/");
        StringBuffer stringBuffer = new StringBuffer();
        for (int length = split.length - 1; length >= 0; length--) {
            stringBuffer.append(split[length]);
            if (length != 0) {
                stringBuffer.append("/");
            }
        }
        this.workHost = "";
        Logger.m4112i("host", this.workHost);
        for (int i = 0; i < 3; i++) {
            this.androidWebServer = new AndroidDefaultServer("127.0.0.1", NetUtils.createPort(), this);
            ResourcesLoader.DEFAULTS_HOST = String.format("%s", "127.0.0.1:" + NetUtils.server_port + AndroidDefaultServer.THE_DEFAULTS_HOST);
            try {
                this.androidWebServer.start();
                StringBuilder sb = new StringBuilder();
                sb.append("default server start ");
                sb.append(this.androidWebServer.wasStarted() ? "success" : "failed");
                Logger.m4112i("default_server", sb.toString());
                break;
            } catch (IOException e) {
                this.androidWebServer = null;
                Logger.m4112i("default_server ", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        for (int i2 = 0; i2 < 3; i2++) {
            this.androidWorkServer = new AndroidWorkServer(NetUtils.createPort(), this);
            ResourcesLoader.WORK_HOST = String.format("%s", "127.0.0.1:" + NetUtils.server_port);
            this.hostStart = String.format("%s", "127.0.0.1:" + NetUtils.server_port);
            try {
                this.androidWorkServer.start();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("worker server start ");
                sb2.append(this.androidWorkServer.wasStarted() ? "success" : "failed");
                Logger.m4112i("worker_server", sb2.toString());
                return;
            } catch (IOException e2) {
                Logger.m4112i("worker_server", e2.getLocalizedMessage());
                e2.printStackTrace();
            }
        }
    }

    protected void processConfigs(Map map) {
        try {
            Map map2 = (Map) map.get("window");
            if (map2 == null) {
                return;
            }
            String str = (String) map2.get("backgroundColor");
            if (str != null) {
                this.coverView.setBackgroundColor(Utils.colorFromCSS(str));
            }
            String str2 = (String) map2.get("pageOrientation");
            if (str2 == null) {
                return;
            }
            if (str2.equals("portrait")) {
                getActivity().setRequestedOrientation(1);
            } else if (str2.equals("landscape")) {
                onRotateLandscape();
                getActivity().setRequestedOrientation(0);
            } else if (!str2.equals("auto")) {
            } else {
                getActivity().setRequestedOrientation(4);
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void registerPlugin(Plugin plugin) {
        plugin.webViewFragment = this;
        plugin.executor = this;
        this.plugins.put(plugin.getName(), plugin);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getOriginalUrl() {
        return getWebView().getOriginalUrl();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Plugin findPlugin(String str) {
        if (this.plugins.containsKey(str)) {
            return this.plugins.get(str);
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.Plugin.Executor
    public void executeEvent(String str, Object obj, JSResponseListener jSResponseListener) {
        String encodeToString;
        if (this.webView == null) {
            Log.e(WebViewFragment.class.getSimpleName(), "WebView not init!");
            return;
        }
        String str2 = "" + new Date().getTime() + Math.random();
        if (jSResponseListener != null) {
            this.responsePlugin.getResponseListeners().put(str2, jSResponseListener);
        }
        if (obj == null) {
            encodeToString = Base64.encodeToString("null".getBytes(), 0);
        } else {
            encodeToString = Base64.encodeToString(new Gson().toJson(obj).getBytes(), 0);
        }
        this.mWebViewRunnable = new WebViewRunnable(str2, encodeToString, str);
        this.handler.post(this.mWebViewRunnable);
    }

    /* renamed from: com.gen.mh.webapps.WebViewFragment$WebViewRunnable */
    /* loaded from: classes2.dex */
    public class WebViewRunnable implements Runnable {
        private String base64;

        /* renamed from: id */
        private String f1299id;
        private String name;

        public WebViewRunnable(String str, String str2, String str3) {
            this.f1299id = str;
            this.base64 = str2;
            this.name = str3;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                WebViewFragment.this.cacheData.put(this.f1299id, this.base64);
                WebEngine webEngine = WebViewFragment.this.webView;
                webEngine.loadUrl("javascript:window.native.notify2('" + this.name + "', '" + this.f1299id + "')");
            } catch (Exception e) {
                Logger.m4112i("dirty", Boolean.valueOf(WebViewFragment.this.webView.isDirty()));
                e.printStackTrace();
            }
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerPlugins() {
        ResponsePlugin responsePlugin = new ResponsePlugin();
        this.responsePlugin = responsePlugin;
        registerPlugin(responsePlugin);
        registerPlugin(new CompletePlugin());
        registerPlugin(new CommonJSPlugin());
        registerPlugin(new StoragePlugin());
        registerPlugin(new SettingsPlugin());
        registerPlugin(new RequestPlugin());
        registerPlugin(new HrefPlugin());
        registerPlugin(new SystemInfoPlugin());
        registerPlugin(new ClosePlugin());
        registerPlugin(getNativeViewPlugin());
        registerPlugin(getUnityPlugin());
        registerPlugin(new WorkPlugin());
        registerPlugin(getServerPlugin());
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str) {
        registerWorkerPlugin(new RequestPlugin(), hashtable, executor);
        registerWorkerPlugin(new HrefPlugin(), hashtable, executor);
        registerWorkerPlugin(getWorkerNativeViewPlugin(), hashtable, executor);
        if (((str.hashCode() == -1563874768 && str.equals("GameWorker")) ? (char) 0 : (char) 65535) != 0) {
            return;
        }
        registerWorkerPlugin(getWorkerUnityPlugin(), hashtable, executor);
    }

    public UnityPlugin getWorkerUnityPlugin() {
        if (this.workerUnityPlugin == null) {
            this.workerUnityPlugin = new UnityPlugin();
        }
        return this.workerUnityPlugin;
    }

    public NativeViewPlugin getWorkerNativeViewPlugin() {
        if (this.workerNativeViewPlugin == null) {
            this.workerNativeViewPlugin = new NativeViewPlugin();
        }
        return this.workerNativeViewPlugin;
    }

    public void registerWorkerPlugin(Plugin plugin, Hashtable<String, Plugin> hashtable, Plugin.Executor executor) {
        plugin.webViewFragment = this;
        plugin.executor = executor;
        hashtable.put(plugin.getName(), plugin);
    }

    public void registerObject(Unity unity, String str) {
        getUnityPlugin().registerObject(unity, str);
    }

    private AppData getAppData() {
        if (this.appData == null) {
            this.appData = AppData.fromAppID(new File(this.workPath).getName());
        }
        return this.appData;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void loadComplete(int i) {
        if (getActivity() != null) {
            HashMap launchSettings = Utils.launchSettings(getActivity(), this.workPath);
            launchSettings.put("localStorage", getAppData().getStorageBlobMap());
            launchSettings.put("configs", this.resourceType.configs());
            ((HashMap) launchSettings.get("launchOptions")).put("appType", this.resourceType.getType());
            Enumeration<String> keys = this.plugins.keys();
            while (keys.hasMoreElements()) {
                this.plugins.get(keys.nextElement()).ready();
            }
            if (i == 0) {
                ready2Complete(launchSettings);
            } else if (i != 2) {
            } else {
                this.webViewLoaded = true;
                this.handler.post(this.closeCallback);
            }
        }
    }

    public void ready2Complete(HashMap hashMap) {
        executeEvent("ready", hashMap, new JSResponseListener() { // from class: com.gen.mh.webapps.WebViewFragment.7
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                WebViewFragment webViewFragment = WebViewFragment.this;
                webViewFragment.webViewLoaded = true;
                webViewFragment.handler.post(webViewFragment.closeCallback);
            }
        });
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController, com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageFinish(String str) {
        Logger.m4113i("close call onLoadPageFinish");
        if (this.webViewLoaded || this.coverView.getParent() == null) {
            return;
        }
        Logger.m4113i("close do onLoadPageFinish");
        this.webViewLoaded = true;
        this.handler.post(this.closeCallback);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public WACrypto getWACrypto() {
        return this.waCrypto;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addListener(BackListener backListener) {
        if (backListener != null) {
            this.listeners.add(backListener);
        }
    }

    public void addBackListener(BackListener backListener) {
        if (backListener != null) {
            this.listeners.remove(backListener);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void onBackPressed() {
        if (this.webViewLoaded) {
            Iterator<BackListener> it2 = this.listeners.iterator();
            while (it2.hasNext()) {
                if (it2.next().onBackPressed()) {
                    return;
                }
            }
            executeEvent("backPressed", null, new JSResponseListener() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewFragment$GskVCrX6tbIHWYzimWs-ioJC8FQ
                @Override // com.gen.p059mh.webapps.listener.JSResponseListener
                public final void onResponse(Object obj) {
                    WebViewFragment.this.lambda$onBackPressed$3$WebViewFragment(obj);
                }
            });
            return;
        }
        lambda$null$2$WebViewFragment();
    }

    public /* synthetic */ void lambda$onBackPressed$3$WebViewFragment(Object obj) {
        try {
            if (!"exit".equals((String) ((Map) obj).get(LogConstants.FOLLOW_OPERATION_TYPE))) {
                return;
            }
            this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewFragment$OrzABhvMbuC5sRm2oDLFupg0S_I
                @Override // java.lang.Runnable
                public final void run() {
                    WebViewFragment.this.lambda$null$1$WebViewFragment();
                }
            });
        } catch (Exception unused) {
            this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewFragment$v1_Y1XTx2ZNglxT3DgLsbE-wiYE
                @Override // java.lang.Runnable
                public final void run() {
                    WebViewFragment.this.lambda$null$2$WebViewFragment();
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getAppID() {
        return this.appID;
    }
}
