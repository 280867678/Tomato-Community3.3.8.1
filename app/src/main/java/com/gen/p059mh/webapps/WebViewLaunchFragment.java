package com.gen.p059mh.webapps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.container.Container;
import com.gen.p059mh.webapps.container.ContainerFactory;
import com.gen.p059mh.webapps.container.impl.AppContainerImpl;
import com.gen.p059mh.webapps.database.Table;
import com.gen.p059mh.webapps.listener.ActivityResultListener;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.ControllerProvider;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.IWebBaseOperation;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.IWebDataOperation;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.ImageCallBack;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.listener.OnBackPressedListener;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.listener.WebappLifecycleSubject;
import com.gen.p059mh.webapps.modules.AppData;
import com.gen.p059mh.webapps.modules.LoadErrorInfo;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.server.AndroidDefaultServer;
import com.gen.p059mh.webapps.server.AndroidWorkServer;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.unity.UnityPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.MD5Utils;
import com.gen.p059mh.webapps.utils.NetUtils;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.SoftKeyBoardListener;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.utils.WACrypto;
import com.gen.p059mh.webapps.utils.interpolator.EaseInCubicInterpolator;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.gen.mh.webapps.WebViewLaunchFragment */
/* loaded from: classes2.dex */
public class WebViewLaunchFragment extends Fragment implements IWebBaseOperation, IWebBizOperation, IWebDataOperation, IWebFragmentController {
    public static String currentDefaultKey = null;
    static int viewId = 10000001;
    protected AndroidDefaultServer androidWebServer;
    protected AndroidWorkServer androidWorkServer;
    private AppData appData;
    File appFilesDir;
    protected String appID;
    FrameLayout backgroundNativeLayer;
    Container baseContainer;
    FrameLayout contentView;
    RelativeLayout coverView;
    String defaultsKey;
    private String defaultsPath;
    protected Handler handler;
    String hostStart;
    boolean isCloseButtonHidden;
    boolean isLoaded;
    boolean isLocalDefaults;
    protected boolean isNeedBoardAppear;
    ResourcesLoader loader;
    Context mContext;
    FrameLayout nativeLayer;
    OnBackPressedListener onBackPressedListener;
    String onLineUrl;
    ResourcesLoader.ResourceType resourceType;
    SoftKeyBoardListener softKeyboardController;
    boolean startLoaded;
    File tempDir;
    WACrypto waCrypto;
    LinearLayout webParentContainer;
    boolean webViewLoaded;
    WebappLifecycleSubject webappLifecycleSubject;
    private String workPath;
    ArrayList<BackListener> listeners = new ArrayList<>();
    private List<ActivityResultListener> activityResultListeners = new ArrayList();
    protected HashMap<String, Object> initParams = new HashMap<>();
    protected String workHost = "";
    protected AtomicBoolean isOnline = new AtomicBoolean(false);
    Map currentConfig = new HashMap();
    protected Map onLineCover = new HashMap();
    int pageOrientation = 1;
    boolean aem = false;
    boolean useApi = true;
    ControllerProvider controllerProvider = new ControllerProvider() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.2
        @Override // com.gen.p059mh.webapps.listener.ControllerProvider
        public void ready2Load(String str, String str2, String str3) {
            if (!WebViewLaunchFragment.this.isAdded()) {
                return;
            }
            FragmentTransaction beginTransaction = WebViewLaunchFragment.this.getChildFragmentManager().beginTransaction();
            ContainerFactory containerFactory = new ContainerFactory();
            WebViewLaunchFragment.this.baseContainer = containerFactory.createContainer(str);
            WebViewLaunchFragment webViewLaunchFragment = WebViewLaunchFragment.this;
            if (webViewLaunchFragment.baseContainer == null) {
                return;
            }
            if (webViewLaunchFragment.isOnline.get()) {
                WebViewLaunchFragment.this.processConfigs();
            } else {
                WebViewLaunchFragment webViewLaunchFragment2 = WebViewLaunchFragment.this;
                webViewLaunchFragment2.processConfigs(webViewLaunchFragment2.resourceType.configs());
            }
            WebViewLaunchFragment webViewLaunchFragment3 = WebViewLaunchFragment.this;
            webViewLaunchFragment3.baseContainer.setWebFragmentController(webViewLaunchFragment3);
            if (!str.equals("app")) {
                WebViewLaunchFragment.this.initializerPlugins();
                WebViewLaunchFragment.this.initializerUnitObject();
            }
            if (WebViewLaunchFragment.this.isOnline.get()) {
                WebViewLaunchFragment.this.loadComplete(0);
            }
            Logger.m4114e("satartLoad", str2);
            WebViewLaunchFragment.this.baseContainer.start(str2, str3);
            beginTransaction.add(WebViewLaunchFragment.this.webParentContainer.getId(), WebViewLaunchFragment.this.baseContainer.provideFragment()).commitAllowingStateLoss();
        }
    };
    Runnable closeCallback = new Runnable() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.7
        @Override // java.lang.Runnable
        public void run() {
            Logger.m4113i("close call back  webLod " + WebViewLaunchFragment.this.webViewLoaded + " loadPage" + WebViewLaunchFragment.this.loadPageError);
            WebViewLaunchFragment webViewLaunchFragment = WebViewLaunchFragment.this;
            if (webViewLaunchFragment.webViewLoaded || webViewLaunchFragment.loadPageError) {
                Logger.m4113i("close call back");
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(WebViewLaunchFragment.this.coverView, "alpha", 1.0f, 0.0f);
                ofFloat.setInterpolator(new EaseInCubicInterpolator());
                ofFloat.setDuration(300L);
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.7.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (WebViewLaunchFragment.this.getContentView() != null) {
                            WebViewLaunchFragment.this.getContentView().removeView(WebViewLaunchFragment.this.coverView);
                            WebViewLaunchFragment.this.animationEnd();
                        }
                    }
                });
                WebViewLaunchFragment.this.loadEnd();
                ofFloat.start();
            }
        }
    };
    boolean loadPageError = false;
    public boolean isForeground = false;

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addCoverView() {
    }

    public /* synthetic */ void animationEnd() {
        IWebBizOperation.CC.$default$animationEnd(this);
    }

    public /* synthetic */ void checkPermissionAndStart(Intent intent, int i, PhotoSwitchListener photoSwitchListener) {
        IWebBizOperation.CC.$default$checkPermissionAndStart(this, intent, i, photoSwitchListener);
    }

    public /* synthetic */ void closeButtonHidden(boolean z) {
        IWebFragmentController.CC.$default$closeButtonHidden(this, z);
    }

    public /* synthetic */ void dismiss(WebViewLaunchFragment webViewLaunchFragment) {
        IWebFragmentController.CC.$default$dismiss(this, webViewLaunchFragment);
    }

    public /* synthetic */ void display(WebViewLaunchFragment webViewLaunchFragment) {
        IWebFragmentController.CC.$default$display(this, webViewLaunchFragment);
    }

    public /* synthetic */ void doSwitchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener) {
        IWebBizOperation.CC.$default$doSwitchPhotoOrAlbum(this, photoSwitchListener);
    }

    public Context getBaseContext() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public IWebBizOperation getBizOperation() {
        return this;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public WebViewLaunchFragment getFragment() {
        return this;
    }

    public /* synthetic */ void gotoNewWebApp(Uri uri, int i) {
        IWebFragmentController.CC.$default$gotoNewWebApp(this, uri, i);
    }

    public /* synthetic */ void gotoNewWebApp(Map<String, Object> map, int i) {
        IWebFragmentController.CC.$default$gotoNewWebApp(this, map, i);
    }

    public void initWorkNativeView(String str) {
    }

    public /* synthetic */ void loadApp() {
        IWebFragmentController.CC.$default$loadApp(this);
    }

    public /* synthetic */ void loadEnd() {
        IWebBizOperation.CC.$default$loadEnd(this);
    }

    protected void loadFail(IErrorInfo iErrorInfo) {
    }

    public void loadImage(String str, ImageCallBack<Drawable> imageCallBack) {
    }

    public /* synthetic */ void onRotateLandscape() {
        IWebBizOperation.CC.$default$onRotateLandscape(this);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public View provideView() {
        return null;
    }

    public /* synthetic */ void reloadAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
        IWebFragmentController.CC.$default$reloadAppInfo(this, str, onAppInfoResponse);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void removePlugins() {
    }

    public /* synthetic */ void requestAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
        IWebFragmentController.CC.$default$requestAppInfo(this, str, onAppInfoResponse);
    }

    public /* synthetic */ void setMenuIcons(List<Map> list) {
        IWebBizOperation.CC.$default$setMenuIcons(this, list);
    }

    public /* synthetic */ void setNavigationBarTextStyle() {
        IWebFragmentController.CC.$default$setNavigationBarTextStyle(this);
    }

    public /* synthetic */ void setScriptMenuIcons(List<Map> list) {
        IWebFragmentController.CC.$default$setScriptMenuIcons(this, list);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setServerPluginNone() {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setStartLoaded(boolean z) {
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void webServerStop() {
    }

    @Override // android.support.p002v4.app.Fragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    @Nullable
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.tempDir = new File(getContext().getFilesDir().getAbsoluteFile() + "/.temp" + Math.random());
        if (!this.tempDir.exists()) {
            this.tempDir.mkdirs();
        }
        if (this.waCrypto == null) {
            this.waCrypto = new WACrypto();
        }
        this.webappLifecycleSubject = new WebappLifecycleSubject();
        this.webappLifecycleSubject.onCreate();
    }

    protected void initHost() {
        if (NetUtils.checkProxy(getContext()) != null) {
            Toast.makeText(getContext(), "使用代理将导致连接异常！", 1).show();
        }
        for (int i = 0; i < 10; i++) {
            if (this.androidWebServer == null) {
                this.androidWebServer = new AndroidDefaultServer("127.0.0.1", NetUtils.createPort(), this);
                ResourcesLoader.DEFAULTS_HOST = String.format("%s", "127.0.0.1:" + NetUtils.server_port + AndroidDefaultServer.THE_DEFAULTS_HOST);
            }
            try {
                this.androidWebServer.start();
                StringBuilder sb = new StringBuilder();
                sb.append("default server start ");
                sb.append(ResourcesLoader.DEFAULTS_HOST);
                sb.append(this.androidWebServer.wasStarted() ? " success" : " failed");
                Logger.m4112i("default_server", sb.toString());
                break;
            } catch (IOException e) {
                this.androidWebServer = null;
                Logger.m4112i("default_server", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        for (int i2 = 0; i2 < 10; i2++) {
            this.androidWorkServer = new AndroidWorkServer("127.0.0.1", NetUtils.createPort(), this);
            ResourcesLoader.WORK_HOST = String.format("%s", "127.0.0.1:" + NetUtils.server_port);
            this.hostStart = String.format("%s", "127.0.0.1:" + NetUtils.server_port);
            try {
                this.androidWorkServer.start();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("worker server start ");
                sb2.append(ResourcesLoader.WORK_HOST);
                sb2.append(this.androidWorkServer.wasStarted() ? " success" : " failed");
                Logger.m4112i("worker_server", sb2.toString());
                return;
            } catch (IOException e2) {
                Logger.m4112i("worker_server", e2.getLocalizedMessage());
                e2.printStackTrace();
            }
        }
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        initDatabase();
        initListener();
        initHandler();
        initLayout();
        this.isLoaded = true;
        if (this.defaultsPath != null && this.workPath != null) {
            check2Load();
        }
        return this.contentView;
    }

    private void initDatabase() {
        Table.setup(getContext());
    }

    private void initListener() {
        this.softKeyboardController = new SoftKeyBoardListener(getActivity());
        this.softKeyboardController.addListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.1
            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardShow(int i) {
                WebViewLaunchFragment webViewLaunchFragment = WebViewLaunchFragment.this;
                if (webViewLaunchFragment.isNeedBoardAppear) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) webViewLaunchFragment.getContentView().getLayoutParams();
                    marginLayoutParams.setMargins(0, 0, 0, i);
                    WebViewLaunchFragment.this.getContentView().setLayoutParams(marginLayoutParams);
                    WebViewLaunchFragment.this.getContentView().requestLayout();
                }
            }

            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardHide(int i) {
                WebViewLaunchFragment webViewLaunchFragment = WebViewLaunchFragment.this;
                if (webViewLaunchFragment.isNeedBoardAppear) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) webViewLaunchFragment.getContentView().getLayoutParams();
                    marginLayoutParams.setMargins(0, 0, 0, 0);
                    WebViewLaunchFragment.this.getContentView().setLayoutParams(marginLayoutParams);
                    WebViewLaunchFragment.this.getContentView().requestLayout();
                }
            }
        });
    }

    private void initHandler() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    private View initLayout() {
        this.contentView = new FrameLayout(getContext());
        this.contentView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.webParentContainer = new LinearLayout(getContext());
        if (Build.VERSION.SDK_INT >= 17) {
            this.webParentContainer.setId(View.generateViewId());
        } else {
            LinearLayout linearLayout = this.webParentContainer;
            int i = viewId;
            viewId = i + 1;
            linearLayout.setId(i);
        }
        this.webParentContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.webParentContainer.setOrientation(1);
        this.backgroundNativeLayer = new FrameLayout(getContext());
        this.contentView.addView(this.backgroundNativeLayer);
        this.contentView.addView(this.webParentContainer);
        this.nativeLayer = new FrameLayout(getContext());
        this.contentView.addView(this.nativeLayer);
        this.coverView = new RelativeLayout(getContext());
        this.coverView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.contentView.addView(this.coverView);
        return this.contentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void check2Load() {
        if (this.startLoaded) {
            return;
        }
        initHost();
        if (this.isOnline.get()) {
            Logger.m4115e("load online");
            this.controllerProvider.ready2Load("web", this.onLineUrl, null);
            return;
        }
        this.loader = new ResourcesLoader(new File(this.defaultsPath), this.waCrypto);
        this.loader.setControllerProvider(this.controllerProvider);
        this.resourceType = this.loader.typeFor(new File(this.workPath));
        ResourcesLoader.ResourceType resourceType = this.resourceType;
        if (resourceType != null) {
            resourceType.checkLoad();
            this.startLoaded = true;
            return;
        }
        loadFail(LoadErrorInfo.UNKNOWN_TYPE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void processConfigs(Map map) {
        this.currentConfig = map;
        this.baseContainer.processConfigs(map);
        if (getActivity() != null) {
            HashMap hashMap = (HashMap) Utils.launchSettings(getActivity(), this.workPath).get("launchOptions");
            ResourcesLoader.ResourceType resourceType = this.resourceType;
            hashMap.put("appType", resourceType == null ? "online" : resourceType.getType());
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void processConfigs() {
        processConfigs(this.currentConfig);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Map getWindowConfig() {
        if (this.currentConfig == null) {
            this.currentConfig = new HashMap();
        }
        return (Map) this.currentConfig.get("window");
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setWorkPath(String str) {
        if (this.isOnline.get()) {
            this.onLineUrl = str;
            Logger.m4114e("onlineUrl", this.onLineUrl);
            try {
                URL url = new URL(str);
                if (url.getPath() != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(url.getHost());
                    sb.append(":");
                    sb.append(url.getPort() == -1 ? url.getDefaultPort() : url.getPort());
                    String sb2 = sb.toString();
                    String encode = MD5Utils.encode(sb2);
                    Logger.m4115e(sb2);
                    str = getContext().getFilesDir().getAbsolutePath() + "/webapps/" + encode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.workPath = str;
        if (this.defaultsPath == null || this.workPath == null || !this.isLoaded) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.3
            @Override // java.lang.Runnable
            public void run() {
                WebViewLaunchFragment.this.check2Load();
            }
        });
    }

    public void setDefaultsPath(String str) {
        this.defaultsPath = str;
        if (this.defaultsPath == null || this.workPath == null || !this.isLoaded) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.4
            @Override // java.lang.Runnable
            public void run() {
                WebViewLaunchFragment.this.check2Load();
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
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

    @Override // android.support.p002v4.app.Fragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    @Nullable
    public Context getContext() {
        if (super.getContext() != null) {
            return super.getContext();
        }
        Context context = this.mContext;
        return context != null ? context : getBaseContext();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBaseOperation
    public <T extends Unity> void registerUnity(Class<T> cls, String str) {
        Container container = this.baseContainer;
        if (container != null) {
            container.registerUnity(cls, str);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void executeEvent(String str, Object obj, JSResponseListener jSResponseListener) {
        Container container = this.baseContainer;
        if (container != null) {
            container.executeEvent(str, obj, jSResponseListener);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBaseOperation
    public <T extends NativeViewPlugin.NativeView> void registerNativeView(Class<T> cls, String str) {
        Container container = this.baseContainer;
        if (container != null) {
            container.registerNativeView(cls, str);
        }
    }

    public void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str) {
        Container container = this.baseContainer;
        if (container != null) {
            container.initWorkPlugin(hashtable, executor, str);
        }
    }

    public void initializerUnitObject() {
        Container container = this.baseContainer;
        if (container != null) {
            container.initializerUnitObject();
        }
    }

    public void initializerPlugins() {
        Container container = this.baseContainer;
        if (container != null) {
            container.initializerPlugins();
        }
    }

    public void initWorkUnity(String str) {
        Container container = this.baseContainer;
        if (container != null) {
            container.initWorkUnity();
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBaseOperation
    public void registerWorkerPlugin(Plugin plugin, Hashtable<String, Plugin> hashtable, Plugin.Executor executor) {
        Container container = this.baseContainer;
        if (container != null) {
            container.registerWorkerPlugin(plugin, hashtable, executor);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBaseOperation
    public void registerObject(Unity unity, String str) {
        Container container = this.baseContainer;
        if (container != null) {
            container.registerObject(unity, str);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBaseOperation
    public NativeViewPlugin getWorkerNativeViewPlugin() {
        Container container = this.baseContainer;
        if (container != null) {
            container.getWorkerNativeViewPlugin();
            return null;
        }
        return null;
    }

    public UnityPlugin getWorkerUnityPlugin() {
        Container container = this.baseContainer;
        if (container != null) {
            return container.getWorkerUnityPlugin();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    /* renamed from: close */
    public void lambda$null$3$WebViewLaunchFragment() {
        if (this.webViewLoaded && !this.loadPageError) {
            Container container = this.baseContainer;
            if (container instanceof AppContainerImpl) {
                this.webViewLoaded = false;
                container.close();
                lambda$null$3$WebViewLaunchFragment();
                return;
            }
            if (this.isOnline.get()) {
                this.webViewLoaded = false;
                this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewLaunchFragment$YllMlcRnMR91TTZp3HijX9GMgQg
                    @Override // java.lang.Runnable
                    public final void run() {
                        WebViewLaunchFragment.this.lambda$close$0$WebViewLaunchFragment();
                    }
                });
            }
            executeEvent("exit", null, new JSResponseListener() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.5
                @Override // com.gen.p059mh.webapps.listener.JSResponseListener
                public void onResponse(Object obj) {
                    WebViewLaunchFragment webViewLaunchFragment = WebViewLaunchFragment.this;
                    webViewLaunchFragment.webViewLoaded = false;
                    webViewLaunchFragment.handler.post(new Runnable() { // from class: com.gen.mh.webapps.WebViewLaunchFragment.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WebViewLaunchFragment.this.lambda$null$3$WebViewLaunchFragment();
                        }
                    });
                }
            });
            return;
        }
        Container container2 = this.baseContainer;
        if (container2 != null) {
            for (String str : container2.getPlugins().keySet()) {
                try {
                    this.baseContainer.getPlugins().get(str).unload();
                } catch (Exception unused) {
                }
            }
        }
        this.tempDir.deleteOnExit();
        if (this.onBackPressedListener == null) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewLaunchFragment$Q4W_CGPRfTuPNGG8jUmJXN3WD18
            @Override // java.lang.Runnable
            public final void run() {
                WebViewLaunchFragment.this.lambda$close$1$WebViewLaunchFragment();
            }
        });
    }

    public /* synthetic */ void lambda$close$1$WebViewLaunchFragment() {
        this.onBackPressedListener.onPressed();
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
            Container container = this.baseContainer;
            if (container instanceof AppContainerImpl) {
                container.onBackPressed();
                return;
            } else {
                executeEvent("backPressed", null, new JSResponseListener() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewLaunchFragment$mBkU7SXvPPqj8QQoTg7tkTuETfU
                    @Override // com.gen.p059mh.webapps.listener.JSResponseListener
                    public final void onResponse(Object obj) {
                        WebViewLaunchFragment.this.lambda$onBackPressed$4$WebViewLaunchFragment(obj);
                    }
                });
                return;
            }
        }
        lambda$null$3$WebViewLaunchFragment();
    }

    public /* synthetic */ void lambda$onBackPressed$4$WebViewLaunchFragment(Object obj) {
        try {
            if (!"exit".equals((String) ((Map) obj).get(LogConstants.FOLLOW_OPERATION_TYPE))) {
                return;
            }
            this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewLaunchFragment$5q30DExRAJFo-zYLMzHsliLakNI
                @Override // java.lang.Runnable
                public final void run() {
                    WebViewLaunchFragment.this.lambda$null$2$WebViewLaunchFragment();
                }
            });
        } catch (Exception unused) {
            this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.-$$Lambda$WebViewLaunchFragment$qSuB0dZqpafGIioVePfOedHPG6A
                @Override // java.lang.Runnable
                public final void run() {
                    WebViewLaunchFragment.this.lambda$null$3$WebViewLaunchFragment();
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void registerPlugin(Plugin plugin) {
        Container container = this.baseContainer;
        if (container != null) {
            container.registerPlugin(plugin);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getOriginalUrl() {
        Container container = this.baseContainer;
        if (container != null) {
            container.getWebEngine().getOriginalUrl();
            return "";
        }
        return "";
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public HashMap getInitParams() {
        return this.initParams;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public NativeViewPlugin getNativeViewPlugin() {
        Container container = this.baseContainer;
        if (container != null) {
            return container.getNativeViewPlugin();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getHostStart() {
        return this.hostStart;
    }

    public ServerPlugin getServerPlugin() {
        Container container = this.baseContainer;
        if (container != null) {
            return container.getServerPlugin();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public ResourcesLoader.ResourceType getResourceType() {
        return this.resourceType;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public RelativeLayout getCoverView() {
        return this.coverView;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public LinearLayout getWebParentContainer() {
        return this.webParentContainer;
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
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public void setCloseButtonHidden(boolean z) {
        this.isCloseButtonHidden = z;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public boolean isCloseButtonHidden() {
        return this.isCloseButtonHidden;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public SoftKeyBoardListener getSoftKeyboardController() {
        return this.softKeyboardController;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getDefaultsPath() {
        return this.defaultsPath;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getWorkHost() {
        return this.workHost;
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
            if (this.resourceType != null) {
                launchSettings.put("localStorage", getAppData().getStorageBlobMap());
                launchSettings.put("configs", this.resourceType.configs());
                ((HashMap) launchSettings.get("launchOptions")).put("appType", this.resourceType.getType());
            }
            Enumeration<String> keys = this.baseContainer.getPlugins().keys();
            while (keys.hasMoreElements()) {
                this.baseContainer.getPlugins().get(keys.nextElement()).ready();
            }
            if (i == 0) {
                ready2Complete(launchSettings);
            } else if (i == 2) {
                this.webViewLoaded = true;
                this.handler.post(this.closeCallback);
            } else if (i != 1) {
            } else {
                this.webViewLoaded = true;
                this.handler.post(this.closeCallback);
            }
        }
    }

    private void ready2Complete(HashMap hashMap) {
        executeEvent("ready", hashMap, new JSResponseListener(this) { // from class: com.gen.mh.webapps.WebViewLaunchFragment.6
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                Logger.m4112i("onLoadPageFinish", "ready2Complete");
            }
        });
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController, com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageFinish(String str) {
        Logger.m4112i("onLoadPageFinish", "onLoadPageFinish" + this.webViewLoaded);
        if (this.webViewLoaded || this.coverView.getParent() == null) {
            return;
        }
        this.webViewLoaded = true;
        this.handler.post(this.closeCallback);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController, com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageError(RuntimeException runtimeException) {
        this.loadPageError = true;
        if (this.coverView.getParent() != null) {
            this.handler.post(this.closeCallback);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("WebView", runtimeException.getMessage());
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public WACrypto getWACrypto() {
        return this.waCrypto;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public AppController getAppController() {
        Container container = this.baseContainer;
        if (container != null) {
            return container.getAppController();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getWorkPath() {
        return this.workPath;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public void setLocalDefaults(boolean z) {
        this.isLocalDefaults = z;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public boolean isLocalDefaults() {
        return this.isLocalDefaults;
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

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public String getAppID() {
        return this.appID;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Handler getHandler() {
        return this.handler;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public Plugin findPlugin(String str) {
        if (this.baseContainer.getPlugins().containsKey(str)) {
            return this.baseContainer.getPlugins().get(str);
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public void setAppID(String str) {
        this.appID = str;
        WebViewFragment.currentApkId = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebDataOperation
    public void setInitParams(HashMap<String, Object> hashMap) {
        this.initParams = hashMap;
    }

    public void setWorkKey(String str) {
        this.waCrypto.setWorkKey(str);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public void onScrollerChange(int i, int i2) {
        this.webappLifecycleSubject.onWebScrollerChange(i, i2);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebBizOperation
    public WebEngine getController() {
        Container container = this.baseContainer;
        if (container != null) {
            return container.getWebEngine();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public WebappLifecycleSubject getWebappLifecycleSubject() {
        return this.webappLifecycleSubject;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addListener(BackListener backListener) {
        if (backListener != null) {
            this.listeners.add(backListener);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public FrameLayout getContentView() {
        return this.contentView;
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

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void addResultListeners(ActivityResultListener activityResultListener) {
        if (activityResultListener != null) {
            this.activityResultListeners.add(activityResultListener);
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (this.mContext != null) {
            this.mContext = null;
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.baseContainer != null) {
            WebappLifecycleSubject webappLifecycleSubject = this.webappLifecycleSubject;
            if (webappLifecycleSubject != null) {
                webappLifecycleSubject.onDestroy();
            }
            if (this.isOnline.get()) {
                this.waCrypto = null;
            } else {
                this.waCrypto.release();
            }
            HashMap hashMap = new HashMap();
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "background");
            executeEvent("window.state", hashMap, null);
            webServerStop();
            for (String str : this.baseContainer.getPlugins().keySet()) {
                try {
                    this.baseContainer.getPlugins().get(str).unload();
                } catch (Exception unused) {
                }
            }
            if (this.baseContainer.getWebEngine() != null) {
                this.baseContainer.getWebEngine().destroy();
            }
            if (this.handler != null && this.baseContainer.getRunnable() != null) {
                for (Runnable runnable : this.baseContainer.getRunnable()) {
                    if (runnable != null) {
                        this.handler.removeCallbacks(runnable);
                    }
                }
                this.baseContainer.releaseRunnableList();
            }
        }
        removeViews();
    }

    public void removeViews() {
        FrameLayout frameLayout = this.nativeLayer;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            this.nativeLayer = null;
        }
        FrameLayout frameLayout2 = this.backgroundNativeLayer;
        if (frameLayout2 != null) {
            frameLayout2.removeAllViews();
            this.backgroundNativeLayer = null;
        }
        FrameLayout frameLayout3 = this.contentView;
        if (frameLayout3 != null) {
            frameLayout3.removeAllViews();
            this.contentView = null;
        }
        RelativeLayout relativeLayout = this.coverView;
        if (relativeLayout != null) {
            relativeLayout.removeAllViews();
            this.coverView = null;
        }
        LinearLayout linearLayout = this.webParentContainer;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            this.webParentContainer = null;
        }
        if (this.onBackPressedListener != null) {
            this.onBackPressedListener = null;
        }
        SoftKeyBoardListener softKeyBoardListener = this.softKeyboardController;
        if (softKeyBoardListener != null) {
            softKeyBoardListener.release();
            this.softKeyboardController = null;
        }
        Container container = this.baseContainer;
        if (container != null) {
            container.release();
            this.baseContainer = null;
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        Container container = this.baseContainer;
        if (container == null) {
            return;
        }
        if (container.getWebEngine() != null) {
            this.baseContainer.getWebEngine().onResume();
        }
        HashMap hashMap = new HashMap();
        if (!this.isForeground) {
            this.isForeground = true;
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "appear");
        } else {
            hashMap.put(LogConstants.FOLLOW_OPERATION_TYPE, "foreground");
        }
        executeEvent("window.state", hashMap, null);
        for (String str : this.baseContainer.getPlugins().keySet()) {
            this.baseContainer.getPlugins().get(str).onShow();
        }
        this.webappLifecycleSubject.onResume();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        Container container = this.baseContainer;
        if (container == null) {
            return;
        }
        if (container.getWebEngine() != null) {
            this.baseContainer.getWebEngine().onPause();
            if (this.isNeedBoardAppear) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getContentView().getLayoutParams();
                marginLayoutParams.setMargins(0, 0, 0, 0);
                getContentView().setLayoutParams(marginLayoutParams);
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
        for (String str : this.baseContainer.getPlugins().keySet()) {
            this.baseContainer.getPlugins().get(str).onHide();
        }
        this.webappLifecycleSubject.onPause();
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setRequestedOrientation(int i) {
        if (getActivity() != null) {
            this.pageOrientation = i;
            getActivity().setRequestedOrientation(i);
        }
    }

    public void setCurrentRequestedOrientation() {
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(this.pageOrientation);
        }
    }

    public void setOnline(boolean z) {
        this.isOnline.set(z);
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isOnline() {
        return this.isOnline.get();
    }

    public void setAem(boolean z) {
        this.aem = z;
    }

    public void setNem(boolean z) {
        this.aem = z;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isAem() {
        return this.aem;
    }

    public void setOnlineConfig(Map map) {
        this.currentConfig = map;
    }

    public void setOnlineCover(Map map) {
        this.onLineCover = map;
    }

    public void setOnLineCrypto(WACrypto wACrypto) {
        this.waCrypto = wACrypto;
    }

    public void setPageOrientation(int i) {
        this.pageOrientation = i;
    }

    public int getPageOrientation() {
        return this.pageOrientation;
    }

    @Override // com.gen.p059mh.webapps.listener.IWebFragmentController
    public boolean isUseApi() {
        return this.useApi;
    }

    public void setUseApi(boolean z) {
        this.useApi = z;
    }
}
