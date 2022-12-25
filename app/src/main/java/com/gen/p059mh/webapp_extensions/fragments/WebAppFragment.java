package com.gen.p059mh.webapp_extensions.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.NotificationCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.gen.p059mh.webapp_extensions.AppControl;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.fragments.WebAppFragment;
import com.gen.p059mh.webapp_extensions.listener.AppControlListener;
import com.gen.p059mh.webapp_extensions.listener.CoverOperationListener;
import com.gen.p059mh.webapp_extensions.listener.DOWNLOAD_MODE;
import com.gen.p059mh.webapp_extensions.listener.DownloadListener;
import com.gen.p059mh.webapp_extensions.listener.ErrorEventListener;
import com.gen.p059mh.webapp_extensions.matisse.Matisse;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.plugins.FilePlugin;
import com.gen.p059mh.webapp_extensions.plugins.FileSavedPlugin;
import com.gen.p059mh.webapp_extensions.plugins.GyroscopePlugin;
import com.gen.p059mh.webapp_extensions.plugins.ImageServerPlugin;
import com.gen.p059mh.webapp_extensions.plugins.OkWebSocketPlugin;
import com.gen.p059mh.webapp_extensions.plugins.Open2Plugin;
import com.gen.p059mh.webapp_extensions.plugins.OpenPlugin;
import com.gen.p059mh.webapp_extensions.plugins.ParamsPlugin;
import com.gen.p059mh.webapp_extensions.plugins.PickImagePlugin;
import com.gen.p059mh.webapp_extensions.plugins.PickerPlugin;
import com.gen.p059mh.webapp_extensions.plugins.ScreenShotPlugin;
import com.gen.p059mh.webapp_extensions.plugins.SheetPlugin;
import com.gen.p059mh.webapp_extensions.plugins.StupidPlugin;
import com.gen.p059mh.webapp_extensions.plugins.SystemMenuPlugin;
import com.gen.p059mh.webapp_extensions.plugins.TimerPlugin;
import com.gen.p059mh.webapp_extensions.plugins.VolumePlugin;
import com.gen.p059mh.webapp_extensions.plugins.WindowPlugin;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapp_extensions.unity.AdManager;
import com.gen.p059mh.webapp_extensions.unity.Canvas;
import com.gen.p059mh.webapp_extensions.unity.CssProcesser;
import com.gen.p059mh.webapp_extensions.unity.FileSystem;
import com.gen.p059mh.webapp_extensions.unity.KeyboardManager;
import com.gen.p059mh.webapp_extensions.unity.ModalView;
import com.gen.p059mh.webapp_extensions.unity.ReadableStream;
import com.gen.p059mh.webapp_extensions.unity.Share;
import com.gen.p059mh.webapp_extensions.unity.StyleManager;
import com.gen.p059mh.webapp_extensions.unity.UpdateManager;
import com.gen.p059mh.webapp_extensions.unity.UserManager;
import com.gen.p059mh.webapp_extensions.unity.VideoDownloadManager;
import com.gen.p059mh.webapp_extensions.unity.WritableStream;
import com.gen.p059mh.webapp_extensions.utils.ResourcePool;
import com.gen.p059mh.webapp_extensions.views.BlockView;
import com.gen.p059mh.webapp_extensions.views.CoverView;
import com.gen.p059mh.webapp_extensions.views.CustomerView;
import com.gen.p059mh.webapp_extensions.views.FloatWebView;
import com.gen.p059mh.webapp_extensions.views.GCanvasView;
import com.gen.p059mh.webapp_extensions.views.ICover;
import com.gen.p059mh.webapp_extensions.views.PickerView;
import com.gen.p059mh.webapp_extensions.views.SmartCoverView;
import com.gen.p059mh.webapp_extensions.views.WAEFGCanvas;
import com.gen.p059mh.webapp_extensions.views.camera.CameraView;
import com.gen.p059mh.webapp_extensions.views.dialog.SwitchPhotoDialog;
import com.gen.p059mh.webapp_extensions.views.player.HPlayer;
import com.gen.p059mh.webapp_extensions.views.player.PlayerView;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.ImageCallBack;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.modules.ErrorInfoImpl;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.pugins.SystemInfoPlugin;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.fragments.WebAppFragment */
/* loaded from: classes2.dex */
public class WebAppFragment extends WebViewLaunchFragment implements DownloadListener {
    public static File appRootDir = null;
    public static DOWNLOAD_MODE download_mode = DOWNLOAD_MODE.VERSION;
    public static final String fileLoadBg = "web_sdk_longingBg.png";
    public static final String fileLoadImg = "web_sdk_longing.png";
    private static String serverURL = "http://192.126.125.210:3007/apps/";
    protected AppControl appControl;
    Bitmap bitmap;
    private String configKey;
    Context context;
    private AppControl defaultsControl;
    protected List<ErrorEventListener> errorEventListeners;
    private String iconUrl;
    ImageServerPlugin imageServerPlugin;
    ICover launchCoverView;
    ResourcePool pool;
    FilePlugin filePlugin = new FilePlugin();
    PickImagePlugin pickImagePlugin = new PickImagePlugin();
    private String navigationBarTextStyle = "white";
    boolean isKeepScreen = false;
    String defaultsID = Utils.getIoDefaultName();
    CoverOperationListener coverOperationListener = new CoverOperationListener() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.1
        @Override // com.gen.p059mh.webapp_extensions.listener.CoverOperationListener
        public File provideIconFile() {
            return WebAppFragment.this.appControl.iconFile();
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.CoverOperationListener
        public void onClose() {
            WebAppFragment.this.lambda$null$3$WebViewLaunchFragment();
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.CoverOperationListener
        public void onRefresh() {
            WebAppFragment.this.loadApp();
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.CoverOperationListener
        public File provideAnimFile() {
            return new File(WebAppFragment.this.getContext().getFilesDir().toString() + "/webapps/" + WebAppFragment.this.appControl.getAppInfo().getAppID() + "/" + WebAppFragment.fileLoadImg);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.CoverOperationListener
        public String provideConfigData() {
            return WebAppFragment.this.getConfig().getString(WebAppFragment.this.configKey, null);
        }
    };
    private boolean destroyed = false;

    public static void setServerURL(String str) {
        serverURL = str;
    }

    public static void setAppRootDir(File file) {
        appRootDir = file;
    }

    public static File getAppRootDir() {
        return appRootDir;
    }

    public void setDefaultsID(String str) {
        this.defaultsID = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setErrorEventListeners(List<ErrorEventListener> list) {
        if (list != null) {
            this.errorEventListeners = list;
        }
    }

    public void addEventListener(ErrorEventListener errorEventListener) {
        if (this.errorEventListeners == null) {
            this.errorEventListeners = new ArrayList();
        }
        if (errorEventListener != null) {
            this.errorEventListeners.add(errorEventListener);
        }
    }

    public void removeEventListener(ErrorEventListener errorEventListener) {
        List<ErrorEventListener> list = this.errorEventListeners;
        if (list == null || errorEventListener == null) {
            return;
        }
        list.remove(errorEventListener);
    }

    public void setupCoverView(ICover iCover) {
        if (iCover != null) {
            this.launchCoverView = iCover;
            if (getCoverView() == null) {
                return;
            }
            getCoverView().removeAllViews();
            getCoverView().addView(iCover.provideView());
        }
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.context = super.getContext().getApplicationContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void registerNativeViewClass() {
        registerNativeView(CameraView.class, "camera");
        registerNativeView(PlayerView.class, "player");
        registerNativeView(BlockView.class, "view");
        registerNativeView(FloatWebView.class, "web.view");
        registerNativeView(PickerView.class, "picker.view");
        registerNativeView(GCanvasView.class, "gcanvas");
        registerNativeView(HPlayer.class, "hv-player");
        registerNativeView(CustomerView.class, "customer");
        registerNativeView(WAEFGCanvas.class, "fgcanvas");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void registerUnityClass() {
        registerUnity(Function.class, "Function");
        registerUnity(WritableStream.class, "WritableStream");
        registerUnity(ReadableStream.class, "ReadableStream");
        registerUnity(ModalView.class, "ModalView");
    }

    @Override // android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.pool = new ResourcePool();
        if (appRootDir == null) {
            appRootDir = new File(getContext().getFilesDir().getAbsolutePath() + "/webapps");
        }
        if (!appRootDir.exists()) {
            appRootDir.mkdirs();
        }
        if (this.launchCoverView == null) {
            if (this.onLineCover.values().size() != 0) {
                this.launchCoverView = configCover();
            } else {
                this.launchCoverView = new CoverView(getContext(), this.coverOperationListener);
            }
        }
        if (this.launchCoverView.provideView().getParent() == null) {
            getCoverView().addView(this.launchCoverView.provideView());
        }
        closeButtonHidden(!isCloseButtonHidden());
        loadApp();
        return onCreateView;
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void closeButtonHidden(boolean z) {
        IWebFragmentController.CC.$default$closeButtonHidden(this, z);
        ICover iCover = this.launchCoverView;
        if (iCover != null) {
            iCover.setCloseBtnShow(z);
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setNavigationBarTextStyle() {
        View decorView = getActivity().getWindow().getDecorView();
        String str = this.navigationBarTextStyle;
        if (str == null || "white".equals(str)) {
            if (Build.VERSION.SDK_INT < 11) {
                return;
            }
            decorView.setSystemUiVisibility(1280);
        } else if (!"black".equals(this.navigationBarTextStyle) || Build.VERSION.SDK_INT < 11) {
        } else {
            decorView.setSystemUiVisibility(9216);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0137, code lost:
        if (r3 == 1) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0139, code lost:
        if (r3 == 2) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x013c, code lost:
        setRequestedOrientation(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0141, code lost:
        setRequestedOrientation(1);
     */
    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void processConfigs(Map map) {
        Map map2;
        super.processConfigs(map);
        if (map != null) {
            try {
                if (map.get("window") == null || (map2 = (Map) map.get("window")) == null) {
                    return;
                }
                this.navigationBarTextStyle = (String) map2.get("navigationBarTextStyle");
                setNavigationBarTextStyle();
                Object obj = map2.get("statusBarHidden");
                if (obj instanceof String) {
                    if (!obj.equals("true") && !obj.equals("yes") && !obj.equals("hidden")) {
                        getActivity().getWindow().clearFlags(1024);
                    }
                    getActivity().getWindow().addFlags(1024);
                } else if (obj instanceof Boolean) {
                    if (((Boolean) obj).booleanValue()) {
                        getActivity().getWindow().addFlags(1024);
                    } else {
                        getActivity().getWindow().clearFlags(1024);
                    }
                } else {
                    getActivity().getWindow().clearFlags(1024);
                }
                if (map2.get("fitSafeArea") != null && ((Boolean) map2.get("fitSafeArea")).booleanValue()) {
                    getWebParentContainer().setPadding(0, Utils.getStatusBarHeight(this.context), 0, 0);
                    getNativeLayer().setPadding(0, Utils.getStatusBarHeight(this.context), 0, 0);
                    getBackgroundNativeLayer().setPadding(0, Utils.getStatusBarHeight(this.context), 0, 0);
                } else {
                    getWebParentContainer().setPadding(0, 0, 0, 0);
                    getNativeLayer().setPadding(0, 0, 0, 0);
                    getBackgroundNativeLayer().setPadding(0, 0, 0, 0);
                }
                if (map2.get("pageOrientation") != null) {
                    String str = (String) map2.get("pageOrientation");
                    char c = 65535;
                    int hashCode = str.hashCode();
                    if (hashCode != 3005871) {
                        if (hashCode != 729267099) {
                            if (hashCode == 1430647483 && str.equals("landscape")) {
                                c = 0;
                            }
                        } else if (str.equals("portrait")) {
                            c = 1;
                        }
                    } else if (str.equals("auto")) {
                        c = 2;
                    }
                    setRequestedOrientation(0);
                } else {
                    setRequestedOrientation(1);
                }
                if (map2.get("backgroundColor") != null) {
                    getWebParentContainer().setBackgroundColor(Utils.colorFromCSS((String) map2.get("backgroundColor")));
                }
                this.isNeedBoardAppear = "resize".equals((String) map2.get("keyboardAppear"));
                if (map.get("secure") == null) {
                    return;
                }
                ((Boolean) map.get("secure")).booleanValue();
                Logger.m4113i(getDefaultsPath());
                Logger.m4113i(getWorkPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ICover configCover() {
        Logger.m4112i("config", this.onLineCover.toString());
        if (this.onLineCover.values().size() > 0) {
            if (this.onLineCover.containsKey("background") && this.onLineCover.get("background") != null && getCoverView() != null) {
                String str = (String) this.onLineCover.get("background");
                SelectionSpec.getInstance().imageEngine.loadImage(getContext(), str, new CommonCallBack<Drawable>() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.2
                    @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                    public void onFailure(Exception exc) {
                    }

                    @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                    public void onResult(Drawable drawable) {
                        if (WebAppFragment.this.getCoverView() != null) {
                            WebAppFragment.this.getCoverView().setBackground(drawable);
                        }
                    }
                }, str.endsWith(Utils.DECODE_END));
            }
            return SmartCoverView.create(getActivity(), this.onLineCover, this.coverOperationListener);
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void loadApp() {
        if (this.destroyed) {
            return;
        }
        if (this.isOnline.get()) {
            startLoading(this.appID);
        }
        String str = this.defaultsID;
        if (str != null) {
            this.defaultsControl = new AppControl(str);
            this.defaultsControl.setHandler(this.handler);
            this.defaultsControl.setDownloadListener(this);
        }
        String str2 = this.appID;
        if (str2 != null) {
            this.appControl = new AppControl(str2);
            this.configKey = this.appControl.getAppInfo().getAppID() + "_config";
            Logger.m4112i("configKey", this.configKey);
            initLoadingBg();
            this.appControl.setHandler(this.handler);
            this.appControl.setDownloadListener(this);
            if (this.appControl.iconFile().exists()) {
                this.launchCoverView.setIconFile(this.appControl.iconFile());
            }
            if (this.appControl.getAppInfo().getTitle() != null) {
                this.launchCoverView.setTitle(this.appControl.getAppInfo().getTitle());
            }
            checkApp();
            return;
        }
        checkDefaultApp();
    }

    public void checkDefaultApp() {
        if (isLocalDefaults()) {
            return;
        }
        this.defaultsControl.setListener(new C14403());
        if (this.defaultsControl.weakUpdate()) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$GAbE44_i1WGapKTyn1F6FGGOQJ0
            @Override // java.lang.Runnable
            public final void run() {
                WebAppFragment.this.lambda$checkDefaultApp$0$WebAppFragment();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.fragments.WebAppFragment$3 */
    /* loaded from: classes2.dex */
    public class C14403 implements AppControlListener {
        C14403() {
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onStart() {
            WebAppFragment webAppFragment = WebAppFragment.this;
            webAppFragment.startLoading(((WebViewLaunchFragment) webAppFragment).appID);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onReceiveInfo(AppResponse appResponse) {
            if (!TextUtils.isEmpty(WebAppFragment.this.iconUrl)) {
                WebAppFragment webAppFragment = WebAppFragment.this;
                webAppFragment.launchCoverView.setIconUrl(webAppFragment.iconUrl);
            }
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onProgress(long j, long j2) {
            WebAppFragment.this.setProcess(j, j2);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onReady() {
            ((WebViewLaunchFragment) WebAppFragment.this).handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$3$U7s1uR05a5cyf3nK10JpCMvjfPc
                @Override // java.lang.Runnable
                public final void run() {
                    WebAppFragment.C14403.this.lambda$onReady$0$WebAppFragment$3();
                }
            });
        }

        public /* synthetic */ void lambda$onReady$0$WebAppFragment$3() {
            ICover iCover = WebAppFragment.this.launchCoverView;
            if (iCover != null) {
                iCover.onReady();
            }
            WebAppFragment webAppFragment = WebAppFragment.this;
            webAppFragment.setDefaultsPath(webAppFragment.defaultsControl.getAppDir().getAbsolutePath());
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onFail(IErrorInfo iErrorInfo) {
            WebAppFragment.this.failed(iErrorInfo);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onUpdate() {
            ((WebViewLaunchFragment) WebAppFragment.this).handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$3$ZCHNE5ImL43LpgRpu6wwYKqHyE4
                @Override // java.lang.Runnable
                public final void run() {
                    WebAppFragment.C14403.this.lambda$onUpdate$1$WebAppFragment$3();
                }
            });
        }

        public /* synthetic */ void lambda$onUpdate$1$WebAppFragment$3() {
            ICover iCover = WebAppFragment.this.launchCoverView;
            if (iCover != null) {
                iCover.onUpdate();
            }
        }
    }

    public /* synthetic */ void lambda$checkDefaultApp$0$WebAppFragment() {
        setDefaultsPath(this.defaultsControl.getAppDir().getAbsolutePath());
    }

    private void initLoadingBg() {
        File file = new File(getContext().getFilesDir().toString() + "/webapps/" + this.appControl.getAppInfo().getAppID() + "/" + fileLoadBg);
        if (file.exists()) {
            this.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
            getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$Hq-89n5ShwsXJLZdOA6lFOr39LA
                @Override // java.lang.Runnable
                public final void run() {
                    WebAppFragment.this.lambda$initLoadingBg$1$WebAppFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$initLoadingBg$1$WebAppFragment() {
        getCoverView().setBackground(new BitmapDrawable(this.bitmap));
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment
    public Context getBaseContext() {
        return WebApplication.getInstance().getApplication();
    }

    void startLoading(String str) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.4
            @Override // java.lang.Runnable
            public void run() {
                WebAppFragment.this.keepScreen(false);
                ICover iCover = WebAppFragment.this.launchCoverView;
                if (iCover != null) {
                    iCover.startLoading();
                }
            }
        });
    }

    String sizeString(long j) {
        String str;
        float f = ((float) j) / 1024.0f;
        if (f > 1024.0f) {
            f /= 1024.0f;
            str = "MB";
        } else {
            str = "KB";
        }
        return String.format("%.2f%s", Float.valueOf(f), str);
    }

    void setProcess(final long j, final long j2) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.5
            @Override // java.lang.Runnable
            public void run() {
                Logger.m4112i("download", "loaded progress = " + j + "  total = " + j2);
                StringBuilder sb = new StringBuilder();
                sb.append(WebAppFragment.this.sizeString(j));
                sb.append("/");
                sb.append(WebAppFragment.this.sizeString(j2));
                String sb2 = sb.toString();
                SpannableString spannableString = new SpannableString(sb2);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7cd120")), 0, sb2.indexOf("/"), 34);
                WebAppFragment.this.keepScreen(true);
                ICover iCover = WebAppFragment.this.launchCoverView;
                if (iCover != null) {
                    iCover.loadProcess(spannableString);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void keepScreen(boolean z) {
        if (getActivity() == null || getActivity().getWindow() == null || this.isKeepScreen) {
            return;
        }
        this.isKeepScreen = true;
        if (z) {
            getActivity().getWindow().addFlags(128);
        } else {
            getActivity().getWindow().clearFlags(128);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void failed(IErrorInfo iErrorInfo) {
        if (iErrorInfo != null) {
            loadFail(iErrorInfo);
        }
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment
    protected void loadFail(final IErrorInfo iErrorInfo) {
        if (iErrorInfo != null) {
            iErrorInfo.setAppInfo(getAppID());
        }
        Logger.m4113i(iErrorInfo.toString());
        ICover iCover = this.launchCoverView;
        if (iCover != null) {
            iCover.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$fNR44mxxLppqSBxOlcIbV5i7SI0
                @Override // java.lang.Runnable
                public final void run() {
                    WebAppFragment.this.lambda$loadFail$2$WebAppFragment(iErrorInfo);
                }
            });
        }
        List<ErrorEventListener> list = this.errorEventListeners;
        if (list != null) {
            for (ErrorEventListener errorEventListener : list) {
                errorEventListener.onErrorEvent(iErrorInfo);
            }
        }
    }

    public /* synthetic */ void lambda$loadFail$2$WebAppFragment(IErrorInfo iErrorInfo) {
        this.launchCoverView.loadFail(iErrorInfo.getMessage());
        keepScreen(false);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerPlugins() {
        super.initializerPlugins();
        registerNativeViewClass();
        registerUnityClass();
        registerPlugin(new GyroscopePlugin());
        registerPlugin(this.filePlugin);
        registerPlugin(new ParamsPlugin());
        registerPlugin(new SheetPlugin());
        registerPlugin(new PickerPlugin());
        registerPlugin(new OpenPlugin());
        registerPlugin(this.pickImagePlugin);
        registerPlugin(new WindowPlugin());
        registerPlugin(new StupidPlugin());
        registerPlugin(new OkWebSocketPlugin());
        registerPlugin(new TimerPlugin());
        registerPlugin(new VolumePlugin());
        registerPlugin(new SystemMenuPlugin());
        registerPlugin(new Open2Plugin());
        registerPlugin(new ScreenShotPlugin());
        registerPlugin(getServerPlugin());
        registerPlugin(new FileSavedPlugin());
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str) {
        super.initWorkPlugin(hashtable, executor, str);
        registerWorkerPlugin(new TimerPlugin(), hashtable, executor);
        registerWorkerPlugin(new SystemInfoPlugin(), hashtable, executor);
        registerWorkerPlugin(new OkWebSocketPlugin(), hashtable, executor);
        registerWorkerPlugin(new FilePlugin(), hashtable, executor);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkUnity(String str) {
        super.initWorkUnity(str);
        if (((str.hashCode() == -1563874768 && str.equals("GameWorker")) ? (char) 0 : (char) 65535) != 0) {
            return;
        }
        getWorkerUnityPlugin().registerObject(new KeyboardManager(), "@KeyboardManager");
        getWorkerUnityPlugin().registerUnity(Canvas.class, "Canvas");
        getWorkerUnityPlugin().registerUnity(InnerAudioContextUnity.class, "DMAudioContext");
        getWorkerUnityPlugin().registerObject(BackgroundAudioManagerUnity.getInstance(), "DMAudioManager");
        getWorkerUnityPlugin().registerUnity(Function.class, "Function");
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public ServerPlugin getServerPlugin() {
        if (this.imageServerPlugin == null) {
            this.imageServerPlugin = new ImageServerPlugin();
        }
        return this.imageServerPlugin;
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerUnitObject() {
        super.initializerUnitObject();
        registerObject(new StyleManager(), "@StyleManager");
        registerObject(new KeyboardManager(), "@KeyboardManager");
        registerObject(new FileSystem(), "@FileSystem");
        registerObject(new UpdateManager(), "@UpdateManager");
        registerObject(new AdManager(), "@AdManager");
        registerObject(new UserManager(), "@UserManager");
        registerObject(new CssProcesser(), "@CssProcesser");
        try {
            registerObject(VideoDownloadManager.getInstance(), "@VideoDownloadManager");
        } catch (NoClassDefFoundError unused) {
            Logger.m4113i("视频下载模块初始化失败");
        }
        registerObject(new Share(), "@share");
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initWorkNativeView(String str) {
        super.initWorkNativeView(str);
        if (getWorkerNativeViewPlugin() != null) {
            getWorkerNativeViewPlugin().registerNativeView(WAEFGCanvas.class, "fgcanvas");
        }
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 13158 && i2 == -1) {
            this.pickImagePlugin.finishPick(Matisse.obtainPathResult(intent));
        } else if (i == 36865 || i == 36866) {
            getController().onActivityResult(i, i2, intent);
        }
        super.onActivityResult(i, i2, intent);
    }

    public SharedPreferences getConfig() {
        return getContext().getSharedPreferences("app_config", 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.fragments.WebAppFragment$6 */
    /* loaded from: classes2.dex */
    public class C14436 implements AppControlListener {
        C14436() {
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onStart() {
            WebAppFragment webAppFragment = WebAppFragment.this;
            webAppFragment.startLoading(((WebViewLaunchFragment) webAppFragment).appID);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onReceiveInfo(final AppResponse appResponse) {
            WebAppFragment.this.iconUrl = appResponse.imageUrl;
            ((WebViewLaunchFragment) WebAppFragment.this).handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.6.1
                @Override // java.lang.Runnable
                public void run() {
                    if (appResponse.loadingBgImg != null) {
                        try {
                            SelectionSpec.getInstance().imageEngine.load(WebAppFragment.this.getContext(), (ImageView) null, appResponse.loadingBgImg, new CommonCallBack<Drawable>() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.6.1.1
                                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                                public void onResult(Drawable drawable) {
                                    String string;
                                    File file = new File(WebAppFragment.this.getContext().getFilesDir().toString() + "/webapps/" + WebAppFragment.this.appControl.getAppInfo().getAppID() + "/" + WebAppFragment.fileLoadBg);
                                    SharedPreferences config = WebAppFragment.this.getConfig();
                                    if (config.getString("loadingBg", null) == null || (string = config.getString("loadingBg", null)) == null || !string.equals(appResponse.loadingBgImg) || !file.exists()) {
                                        try {
                                            WebAppFragment.this.getCoverView().setBackground(drawable);
                                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                                            ((BitmapDrawable) drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                            fileOutputStream.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        config.edit().putString("loadingBg", appResponse.loadingBgImg).apply();
                                    }
                                }

                                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                                public void onFailure(Exception exc) {
                                    exc.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ICover iCover = WebAppFragment.this.launchCoverView;
                    if (iCover != null) {
                        iCover.setIconUrl(appResponse.imageUrl);
                        WebAppFragment webAppFragment = WebAppFragment.this;
                        webAppFragment.launchCoverView.setTitle(webAppFragment.appControl.getAppInfo().getTitle());
                        WebAppFragment.this.launchCoverView.setWAppIconShow(false);
                    }
                    if (appResponse.loadingImg != null) {
                        try {
                            SelectionSpec.getInstance().imageEngine.load(WebAppFragment.this.getContext(), (ImageView) null, appResponse.loadingImg.imgPath, new CommonCallBack<Drawable>() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.6.1.2
                                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                                public void onResult(Drawable drawable) {
                                    AppResponse.LoadingImgBean loadingImgBean;
                                    File file = new File(WebAppFragment.this.getContext().getFilesDir().toString() + "/webapps/" + WebAppFragment.this.appControl.getAppInfo().getAppID() + "/" + WebAppFragment.fileLoadImg);
                                    SharedPreferences config = WebAppFragment.this.getConfig();
                                    if (config.getString(WebAppFragment.this.configKey, null) == null || (loadingImgBean = (AppResponse.LoadingImgBean) new Gson().fromJson(config.getString(WebAppFragment.this.configKey, null), (Class<Object>) AppResponse.LoadingImgBean.class)) == null || !loadingImgBean.imgPath.equals(appResponse.loadingImg.imgPath) || !file.exists()) {
                                        try {
                                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                                            ((BitmapDrawable) drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                            fileOutputStream.close();
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        config.edit().putString(WebAppFragment.this.configKey, new Gson().toJson(appResponse.loadingImg)).apply();
                                    }
                                }

                                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                                public void onFailure(Exception exc) {
                                    exc.printStackTrace();
                                }
                            });
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onProgress(long j, long j2) {
            WebAppFragment.this.setProcess(j, j2);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onReady() {
            ((WebViewLaunchFragment) WebAppFragment.this).handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.6.2
                @Override // java.lang.Runnable
                public void run() {
                    ICover iCover = WebAppFragment.this.launchCoverView;
                    if (iCover != null) {
                        iCover.onReady();
                    }
                    WebAppFragment webAppFragment = WebAppFragment.this;
                    webAppFragment.setWorkPath(webAppFragment.appControl.getAppDir().getAbsolutePath());
                    WebAppFragment.this.checkDefaultApp();
                }
            });
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onFail(IErrorInfo iErrorInfo) {
            WebAppFragment.this.failed(iErrorInfo);
        }

        @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
        public void onUpdate() {
            WebAppFragment webAppFragment = WebAppFragment.this;
            if (webAppFragment.launchCoverView != null) {
                ((WebViewLaunchFragment) webAppFragment).handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$WebAppFragment$6$mos5pae7vQ4VBGGpD-g6Crz57KA
                    @Override // java.lang.Runnable
                    public final void run() {
                        WebAppFragment.C14436.this.lambda$onUpdate$0$WebAppFragment$6();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onUpdate$0$WebAppFragment$6() {
            ICover iCover = WebAppFragment.this.launchCoverView;
            if (iCover != null) {
                iCover.onUpdate();
            }
        }
    }

    private void checkApp() {
        AppControl appControl = this.appControl;
        if (appControl == null) {
            return;
        }
        appControl.setListener(new C14436());
        if (this.appControl.weakUpdate()) {
            return;
        }
        setWorkPath(this.appControl.getAppDir().getAbsolutePath());
        checkDefaultApp();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroyView() {
        this.pool.release();
        AppControl appControl = this.defaultsControl;
        if (appControl != null) {
            appControl.cancel();
        }
        AppControl appControl2 = this.appControl;
        if (appControl2 != null) {
            appControl2.cancel();
        }
        this.destroyed = true;
        this.launchCoverView.loadingRelease();
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        List<ErrorEventListener> list = this.errorEventListeners;
        if (list != null) {
            list.clear();
        }
        super.onDestroyView();
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void animationEnd() {
        IWebBizOperation.CC.$default$animationEnd(this);
        this.launchCoverView.loadingStop();
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void onRotateLandscape() {
        if (Build.VERSION.SDK_INT >= 11) {
            this.launchCoverView.onRotateLandscape();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.listener.DownloadListener
    public void onDownloadFail(IErrorInfo iErrorInfo) {
        loadFail(iErrorInfo);
    }

    @Override // com.gen.p059mh.webapp_extensions.listener.DownloadListener
    public void onRequestAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
        requestAppInfo(str, onAppInfoResponse);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void requestAppInfo(String str, final OnAppInfoResponse onAppInfoResponse) {
        try {
            Request request = new Request();
            request.setUrl(new URL(serverURL + str));
            request.setRequestListener(new Request.RequestListener(this) { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.7
                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onProgress(long j, long j2) {
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public boolean onReceiveResponse(Request.Response response) {
                    return false;
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i, String str2) {
                    onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(i, str2));
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onComplete(int i, byte[] bArr) {
                    String str2;
                    Map map = (Map) new Gson().fromJson(new String(bArr), (Class<Object>) Map.class);
                    if (((Number) map.get("code")).intValue() == 0) {
                        Map map2 = (Map) map.get("result");
                        AppResponse appResponse = new AppResponse();
                        appResponse.title = (String) map2.get("title");
                        appResponse.imageUrl = (String) map2.get("image_url");
                        List list = (List) map2.get("versions");
                        Map map3 = (Map) list.get(list.size() - 1);
                        appResponse.url = "http://192.126.125.210:3007" + map3.get("url");
                        appResponse.version = (String) map3.get(DatabaseFieldConfigLoader.FIELD_NAME_VERSION);
                        onAppInfoResponse.onComplete(appResponse);
                        return;
                    }
                    try {
                        str2 = (String) map.get(NotificationCompat.CATEGORY_MESSAGE);
                    } catch (Exception unused) {
                        str2 = "failed!";
                    }
                    onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(i, str2));
                }
            });
            request.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void checkPermissionAndStart(final Intent intent, final int i, final PhotoSwitchListener photoSwitchListener) {
        new RxPermissions(getActivity()).request(PickImagePlugin.CAMERA).subscribe(new Observer<Boolean>() { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.8
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    WebAppFragment.this.startActivityForResult(intent, i);
                    return;
                }
                PhotoSwitchListener photoSwitchListener2 = photoSwitchListener;
                if (photoSwitchListener2 == null) {
                    return;
                }
                photoSwitchListener2.cancel();
            }
        });
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void loadImage(String str, final ImageCallBack<Drawable> imageCallBack) {
        super.loadImage(str, imageCallBack);
        SelectionSpec.getInstance().imageEngine.loadImage(getContext(), str, new CommonCallBack<Drawable>(this) { // from class: com.gen.mh.webapp_extensions.fragments.WebAppFragment.9
            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onResult(Drawable drawable) {
                imageCallBack.onResult(drawable);
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onFailure(Exception exc) {
                imageCallBack.onFailure(exc);
            }
        });
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void doSwitchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener) {
        Logger.m4114e("doSwitchPhotoOrAlbum", "show");
        new SwitchPhotoDialog(getContext(), photoSwitchListener).show();
    }
}
