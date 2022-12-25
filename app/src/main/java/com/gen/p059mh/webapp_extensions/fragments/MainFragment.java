package com.gen.p059mh.webapp_extensions.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.activities.WebAppActivity;
import com.gen.p059mh.webapp_extensions.listener.DOWNLOAD_MODE;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.modules.AppInfo;
import com.gen.p059mh.webapp_extensions.utils.CryptoHelper;
import com.gen.p059mh.webapp_extensions.utils.NetWorkUtils;
import com.gen.p059mh.webapp_extensions.utils.Tool;
import com.gen.p059mh.webapp_extensions.views.ICover;
import com.gen.p059mh.webapp_extensions.views.LiquidView;
import com.gen.p059mh.webapp_extensions.views.WebViewNativeView;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import com.gen.p059mh.webapps.database.Table;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.listener.OnBackPressedListener;
import com.gen.p059mh.webapps.listener.RequestResultListener;
import com.gen.p059mh.webapps.modules.AppData;
import com.gen.p059mh.webapps.modules.ErrorInfoImpl;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.p076mh.webappStart.DemoObject;
import com.p076mh.webappStart.MyPlugin;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.DMImagePlugin;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.DMMediaPlugin;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.DMSystemPlugin;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.fragments.MainFragment */
/* loaded from: classes2.dex */
public class MainFragment extends WebAppFragment {
    public static final String APIHOST = "apihost";
    public static final String CLOSE_EVENT = "close";
    public static int WAWindowFeature = 0;
    private static String aID = null;
    private static String aKey = null;
    private static String apiHost = "";
    private static List<Map> menuIcons;
    String host;
    protected LiquidView liquidView;
    public Context mContext;
    HomeKeyEventBroadCastReceiver receiver;
    boolean isOpenSoftKeyBoard = false;
    List dataList = null;
    Runnable showRunAble = new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.5
        @Override // java.lang.Runnable
        public void run() {
            MainFragment.this.liquidView.setVisibility(0);
        }
    };

    public static native String calSign(String str, String str2, String str3, String str4);

    public static native String defaultID();

    public static native String defaultKey();

    public static List<AppInfo> findAll(Context context) {
        Table.setup(context);
        WebAppFragment.appRootDir = new File(context.getFilesDir().getAbsolutePath() + "/webapps");
        return AppInfo.findAll();
    }

    public static void setApiHost(String str) {
        apiHost = str;
    }

    public String getApiHost() {
        if (TextUtils.isEmpty(apiHost)) {
            return getActivity().getSharedPreferences("SP_devices", 0).getString("APIHOST", "");
        }
        return apiHost;
    }

    public static void saveApiHost(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP_devices", 0);
        sharedPreferences.edit().putString("APIHOST", str).commit();
        Logger.m4112i("apiHost", sharedPreferences.getString("APIHOST", ""));
    }

    public static void setWeakUpdate(DOWNLOAD_MODE download_mode) {
        WebAppFragment.download_mode = download_mode;
    }

    public static void setup(Map<String, String> map) {
        String str = map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID);
        String str2 = map.get("key");
        if (str != null && str2 != null) {
            aID = str;
            aKey = str2;
            return;
        }
        Log.e("MainFragment", "There is no id and key!");
    }

    public static void setMenuIconDatas(List<Map> list) {
        menuIcons = list;
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerPlugins() {
        super.initializerPlugins();
        WebApplication.getInstance().setTempRoot(getTempDir().getAbsolutePath());
        registerPlugin(new MyPlugin());
        registerPlugin(new DMImagePlugin());
        registerPlugin(new DMSystemPlugin());
        registerPlugin(new DMMediaPlugin());
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment
    protected void registerUnityClass() {
        super.registerUnityClass();
        registerUnity(DemoObject.class, "DemoObject");
        registerUnity(InnerAudioContextUnity.class, "DMAudioContext");
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment
    protected void registerNativeViewClass() {
        super.registerNativeViewClass();
        registerNativeView(WebViewNativeView.class, "web.view");
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerUnitObject() {
        super.initializerUnitObject();
        registerObject(BackgroundAudioManagerUnity.getInstance(), "DMAudioManager");
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void gotoNewWebApp(Uri uri, int i) {
        IWebFragmentController.CC.$default$gotoNewWebApp(this, uri, i);
        goAppWithFragment(uri, i);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void gotoNewWebApp(Map<String, Object> map, int i) {
        Intent intent = new Intent(getContext(), WebAppActivity.class);
        intent.putExtra(WebAppActivity.FRAGMENT_CLASS_KEY, getClass().getName());
        intent.putExtra(WebAppActivity.WORK_PATH_KEY, map.get("url").toString());
        if (i != -1) {
            intent.putExtra("webapp_opener_id", i);
        }
        if (map.containsKey("app")) {
            intent.putExtra(WebAppActivity.IS_ON_LINE, ((Boolean) map.get("app")).booleanValue());
            boolean z = true;
            if (map.containsKey("aem")) {
                intent.putExtra(WebAppActivity.AEM, map.get("aem") != null && ((Boolean) map.get("aem")).booleanValue());
            }
            if (map.containsKey("nem")) {
                if (map.get("nem") == null || !((Boolean) map.get("nem")).booleanValue()) {
                    z = false;
                }
                intent.putExtra(WebAppActivity.AEM, z);
            }
            intent.putExtra(WebAppActivity.ON_LINE_CRYPTO, getWACrypto());
        }
        if (map.containsKey("configs")) {
            intent.putExtra(WebAppActivity.ON_LINE_CONFIG, (LinkedTreeMap) map.get("configs"));
        }
        if (map.containsKey("cover")) {
            intent.putExtra(WebAppActivity.ON_LINE_COVER, (LinkedTreeMap) map.get("cover"));
        }
        intent.putExtra(WebAppActivity.DEFAULT_PATH_KEY, getDefaultsPath());
        startActivity(intent);
    }

    public void goAppWithActivity(Uri uri, int i) {
        Intent intent = new Intent(getContext(), WebAppActivity.class);
        intent.putExtra(WebAppActivity.FRAGMENT_CLASS_KEY, getClass().getName());
        intent.putExtra(WebAppActivity.APP_ID_KEY, uri.getHost());
        if (i != -1) {
            intent.putExtra("webapp_opener_id", i);
        }
        intent.addFlags(268435456);
        if (uri != null) {
            try {
                String query = uri.getQuery();
                if (query != null) {
                    String[] split = query.split("&");
                    HashMap hashMap = new HashMap();
                    for (String str : split) {
                        String[] split2 = str.split(SimpleComparison.EQUAL_TO_OPERATION);
                        hashMap.put(split2[0], split2[1]);
                    }
                    intent.putExtra(WebAppActivity.INIT_PARAMS, hashMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
    }

    public void goAppWithFragment(Uri uri, int i) {
        goAppWithFragment(uri, i, null);
    }

    public void goAppWithFragment(Uri uri, int i, Map<String, Object> map) {
        WebAppFragment webAppFragment;
        try {
            webAppFragment = (WebAppFragment) Class.forName(getClass().getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            webAppFragment = null;
        }
        if (webAppFragment == null) {
            webAppFragment = new MainFragment();
        }
        webAppFragment.setOnBackPressedListener(new OnBackPressedListener() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.1
            @Override // com.gen.p059mh.webapps.listener.OnBackPressedListener
            public void onPressed() {
                Tool.popBackStackImmediate(MainFragment.this.getFragmentManager());
            }
        });
        webAppFragment.setErrorEventListeners(this.errorEventListeners);
        if (map != null) {
            if (map.containsKey("configs") && map.get("configs") != null) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) map.get("configs");
                if (linkedTreeMap.containsKey("window")) {
                    Map map2 = (Map) linkedTreeMap.get("window");
                    if (map2.containsKey("pageOrientation")) {
                        Tool.checkOrientation(map2.get("pageOrientation").toString(), getActivity());
                    }
                }
                webAppFragment.setOnlineConfig(linkedTreeMap);
            }
            if (map.containsKey("app")) {
                webAppFragment.setOnline(((Boolean) map.get("app")).booleanValue());
                webAppFragment.setOnLineCrypto(getWACrypto());
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt(WebAppActivity.CONTAINER_ID, getArguments().getInt(WebAppActivity.CONTAINER_ID));
        if (i != -1) {
            bundle.putInt("webapp_opener_id", i);
        }
        webAppFragment.setArguments(bundle);
        if (webAppFragment.isOnline()) {
            webAppFragment.setWorkPath(uri.toString());
        } else {
            webAppFragment.setAppID(uri.getHost());
        }
        if (uri != null) {
            try {
                String query = uri.getQuery();
                if (query != null) {
                    String[] split = query.split("&");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    for (String str : split) {
                        String[] split2 = str.split(SimpleComparison.EQUAL_TO_OPERATION);
                        hashMap.put(split2[0], split2[1]);
                    }
                    webAppFragment.setInitParams(hashMap);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Tool.addFragment(webAppFragment, getFragmentManager(), getArguments().getInt(WebAppActivity.CONTAINER_ID));
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void dismiss(final WebViewLaunchFragment webViewLaunchFragment) {
        getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$MainFragment$bLlHvhPPfGA07wv54vJgqQ18t1Q
            @Override // java.lang.Runnable
            public final void run() {
                MainFragment.this.lambda$dismiss$0$MainFragment(webViewLaunchFragment);
            }
        });
    }

    public /* synthetic */ void lambda$dismiss$0$MainFragment(WebViewLaunchFragment webViewLaunchFragment) {
        webViewLaunchFragment.closeButtonHidden(false);
        Tool.dismiss(getFragmentManager(), this, webViewLaunchFragment);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void display(final WebViewLaunchFragment webViewLaunchFragment) {
        getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.-$$Lambda$MainFragment$NQc38b-CCiskU7WY7vVy4zCYLbY
            @Override // java.lang.Runnable
            public final void run() {
                MainFragment.this.lambda$display$1$MainFragment(webViewLaunchFragment);
            }
        });
    }

    public /* synthetic */ void lambda$display$1$MainFragment(WebViewLaunchFragment webViewLaunchFragment) {
        closeButtonHidden(true);
        Tool.display(getFragmentManager(), webViewLaunchFragment);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Logger.m4114e("isShow", getAppID() + ":" + z);
    }

    @Override // android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        Logger.m4114e("isShow", Boolean.valueOf(z));
    }

    public static void cleanAll() {
        Logger.m4113i("clean all start");
        File file = new File(WebApplication.getInstance().getApplication().getFilesDir().toString() + "/webapps.datas/");
        if (file.exists()) {
            Logger.m4113i("delete all cache file");
            Utils.deleteDirWithFile(file);
        }
        AppData[] fromAppID = AppData.fromAppID();
        if (fromAppID != null && fromAppID.length > 0) {
            for (AppData appData : fromAppID) {
                if (appData != null) {
                    Logger.m4113i("clean " + appData.getStorageBlobMap().toString() + " db record");
                    appData.clearStorageBlob();
                    appData.save();
                    Logger.m4113i("clean " + appData.getAppID() + " db record");
                }
            }
            return;
        }
        Logger.m4113i("db record is clean");
    }

    public static void cleanCacheData(String str) {
        File file = new File(WebApplication.getInstance().getApplication().getFilesDir().toString() + "/webapps.datas/" + str);
        Logger.m4113i("clean " + str + " start");
        AppData fromAppID = AppData.fromAppID(str);
        if (fromAppID != null) {
            Logger.m4113i("clean " + fromAppID.getStorageBlobMap().toString() + " db record");
            fromAppID.clearStorageBlob();
            fromAppID.save();
        }
        Logger.m4113i("clean " + str + " db record");
        if (file.exists()) {
            Utils.deleteDirWithFile(file);
        }
        Logger.m4113i("delete " + str + " cache file");
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.receiver = new HomeKeyEventBroadCastReceiver();
        getContext().registerReceiver(this.receiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    /* renamed from: com.gen.mh.webapp_extensions.fragments.MainFragment$HomeKeyEventBroadCastReceiver */
    /* loaded from: classes2.dex */
    class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
        HomeKeyEventBroadCastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            MainFragment.this.isOpenSoftKeyBoard = true;
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        FrameLayout frameLayout = (FrameLayout) super.onCreateView(layoutInflater, viewGroup, bundle);
        frameLayout.setBackgroundColor(-1);
        frameLayout.setClickable(true);
        if (Build.VERSION.SDK_INT >= 11) {
            this.liquidView = new LiquidView(getContext());
            this.liquidView.setVisibility(4);
            frameLayout.addView(this.liquidView, frameLayout.indexOfChild(getNativeLayer()) + 1);
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("SP_WEB_APP", 0);
            this.liquidView.setFirstPosition(sharedPreferences.getFloat("L_TARGET_X", 200.0f), sharedPreferences.getFloat("L_TARGET_Y", 200.0f));
            List<Map> list = menuIcons;
            if (list != null) {
                setMenuIcons(list);
            } else {
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                hashMap.put("src", Integer.valueOf(R$mipmap.close));
                hashMap.put("key", CLOSE_EVENT);
                arrayList.add(hashMap);
                setMenuIcons(arrayList);
            }
        }
        return frameLayout;
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void loadEnd() {
        IWebBizOperation.CC.$default$loadEnd(this);
        if (!isCloseButtonHidden()) {
            getHandler().post(this.showRunAble);
        }
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebBizOperation
    public void setMenuIcons(final List<Map> list) {
        IWebBizOperation.CC.$default$setMenuIcons(this, list);
        this.liquidView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.2
            @Override // java.lang.Runnable
            public void run() {
                int i;
                if (MainFragment.this.liquidView != null) {
                    LiquidView.IconButton[] iconButtonArr = new LiquidView.IconButton[list.size()];
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        iconButtonArr[i2] = new LiquidView.IconButton(MainFragment.this.getContext());
                        try {
                            i = Integer.valueOf(((Map) list.get(i2)).get("src").toString()).intValue();
                        } catch (Exception unused) {
                            Logger.m4114e("menu_icon", "icon resId error");
                            i = R$mipmap.close;
                        }
                        SelectionSpec.getInstance().imageEngine.load(iconButtonArr[i2].getImageView().imageView.getContext(), iconButtonArr[i2].getImageView().imageView, i);
                    }
                    MainFragment.this.liquidView.setIcons(iconButtonArr);
                }
                MainFragment.this.liquidView.setOnButtonClicked(new LiquidView.OnButtonClicked() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.2.1
                    @Override // com.gen.p059mh.webapp_extensions.views.LiquidView.OnButtonClicked
                    public void onClick(View view, int i3) {
                        String str = (String) ((Map) list.get(i3)).get("key");
                        if (!TextUtils.isEmpty(str) && MainFragment.CLOSE_EVENT.equals(str)) {
                            MainFragment.this.lambda$null$3$WebViewLaunchFragment();
                            return;
                        }
                        View.OnClickListener onClickListener = (View.OnClickListener) ((Map) list.get(i3)).get("onClick");
                        if (onClickListener == null) {
                            return;
                        }
                        onClickListener.onClick(view);
                    }
                });
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onStart() {
        super.onStart();
        if (!this.isOpenSoftKeyBoard || getActivity() == null || getActivity().getCurrentFocus() == null || getActivity().getCurrentFocus().getWindowToken() == null) {
            return;
        }
        ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        this.isOpenSoftKeyBoard = false;
        getActivity().getCurrentFocus().clearFocus();
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void setScriptMenuIcons(final List<Map> list) {
        IWebFragmentController.CC.$default$setScriptMenuIcons(this, list);
        this.liquidView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.3
            @Override // java.lang.Runnable
            public void run() {
                if (MainFragment.this.liquidView != null) {
                    LiquidView.IconButton[] iconButtonArr = new LiquidView.IconButton[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        iconButtonArr[i] = new LiquidView.IconButton(MainFragment.this.getContext());
                        if (((Map) list.get(i)).get("type") != null && "localResource".equals(((Map) list.get(i)).get("type"))) {
                            SelectionSpec.getInstance().imageEngine.load(iconButtonArr[i].getImageView().imageView.getContext(), iconButtonArr[i].getImageView().imageView, Integer.valueOf(((Map) list.get(i)).get("src").toString()).intValue());
                        } else {
                            SelectionSpec.getInstance().imageEngine.load(iconButtonArr[i].getImageView().imageView.getContext(), iconButtonArr[i].getImageView().imageView, Uri.parse(((Map) list.get(i)).get("src").toString()));
                        }
                    }
                    MainFragment.this.liquidView.addIcon(iconButtonArr);
                }
                MainFragment.this.liquidView.setOnScriptButtonClicked(new LiquidView.OnButtonClicked() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.3.1
                    @Override // com.gen.p059mh.webapp_extensions.views.LiquidView.OnButtonClicked
                    public void onClick(View view, int i2) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("key", (String) ((Map) list.get(i2)).get("key"));
                        MainFragment.this.executeEvent("system.menu.click", hashMap, null);
                    }
                });
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void closeButtonHidden(boolean z) {
        super.closeButtonHidden(z);
        LiquidView liquidView = this.liquidView;
        if (liquidView != null) {
            liquidView.setVisibility(z ? 0 : 4);
        }
    }

    static {
        System.loadLibrary("scan");
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void requestAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
        loadAppInfo(str, onAppInfoResponse);
    }

    @Override // com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void reloadAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
        this.dataList = null;
        loadAppInfo(str, onAppInfoResponse);
    }

    public void loadAppInfo(String str, final OnAppInfoResponse onAppInfoResponse) {
        String str2;
        byte[] bytes;
        String str3 = "";
        try {
            final boolean z = !TextUtils.isEmpty(str) && str.equals(Utils.getIoDefaultName());
            String defaultID = z ? defaultID() : aID;
            String defaultKey = z ? defaultKey() : aKey;
            StringBuilder sb = new StringBuilder();
            sb.append("/api/app/applet/applet/queryAppletV2/");
            sb.append(aID == null ? defaultID : aID);
            String sb2 = sb.toString();
            if (z) {
                setDefaultsKey(defaultKey);
            } else {
                setWorkKey(defaultKey);
            }
            if (getApiHost().equals(str3)) {
                failed(ErrorInfoImpl.newInstance(201, getString(R$string.no_api_host)));
                return;
            }
            if (aID == null && getWorkPath() != null) {
                defaultID = defaultID();
                defaultKey = defaultKey();
            }
            String str4 = getApiHost() + sb2;
            Logger.m4113i("apiPath:" + str4);
            Logger.m4113i("aID:" + defaultID + " aKey:" + defaultKey);
            Date date = new Date();
            Request request = new Request();
            request.setUrl(new URL(str4 + "?time=" + System.currentTimeMillis()));
            request.setMethod("POST");
            long time = date.getTime() / 1000;
            final CryptoHelper cryptoHelper = new CryptoHelper(time);
            request.setRequestHeaders("X-Client-TimeStamp", String.valueOf(time));
            int aPNType = NetWorkUtils.getAPNType(getContext());
            if (aPNType == 1) {
                str2 = "Wifi";
            } else if (aPNType == 2) {
                str2 = "2G";
            } else if (aPNType == 3) {
                str2 = "3G";
            } else if (aPNType != 4) {
                onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(200, getString(R$string.no_net_work)));
                return;
            } else {
                str2 = "4G";
            }
            HashMap hashMap = new HashMap();
            hashMap.put("appletAlias", this.appID);
            hashMap.put("baseAppletAlias", Utils.getIoDefaultName());
            String json = new Gson().toJson(hashMap);
            String cryptoBody = cryptoHelper.cryptoBody(json);
            if (getActivity() != null) {
                str3 = getActivity().getPackageName();
            }
            String str5 = "{\"key\":\"" + cryptoHelper.rsaEncrypt() + "\",\"data\":\"" + cryptoBody + "\"}";
            String str6 = "APP/" + str3 + " SYS/Android;" + Build.VERSION.RELEASE + " FM/android;" + Build.MODEL + " NE/" + str2 + " LANG/" + Locale.getDefault().getLanguage() + " SDK/3.1.1;" + Utils.getDevicesId(getContext());
            String valueOf = String.valueOf(time);
            if (aKey != null) {
                defaultKey = aKey;
            }
            request.setRequestHeaders("X-Client-Sign", calSign(sb2, valueOf, json, defaultKey));
            request.setRequestHeaders("X-Client-Identity", str6);
            Request.Body body = new Request.Body();
            body.contentType = "application/json";
            body.inputStream = new ByteArrayInputStream(str5.getBytes());
            body.contentLength = bytes.length;
            request.setBody(body);
            request.setRequestListener(new RequestResultListener() { // from class: com.gen.mh.webapp_extensions.fragments.MainFragment.4
                boolean isNeedDecrypt = false;

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i, String str7) {
                    onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(i, str7));
                }

                @Override // com.gen.p059mh.webapps.listener.RequestResultListener
                public void onComplete(Request.Response response, byte[] bArr) {
                    try {
                        if (response.headers.containsKey("X-Server-Encrpt")) {
                            this.isNeedDecrypt = response.headers.get("X-Server-Encrpt").get(0).equals("1");
                        }
                        String str7 = new String(bArr);
                        if (this.isNeedDecrypt) {
                            str7 = cryptoHelper.decryptBody(((Map) new Gson().fromJson(str7, (Class<Object>) Map.class)).get(AopConstants.APP_PROPERTIES_KEY).toString());
                        }
                        Logger.m4117d(str7);
                        Map map = (Map) new Gson().fromJson(str7, (Class<Object>) Map.class);
                        int checkCode = MainFragment.this.checkCode(map);
                        if (map.get("success") != null && !((Boolean) map.get("success")).booleanValue()) {
                            onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(checkCode, (String) map.get("description")));
                            return;
                        }
                        Object obj = map.get("success");
                        MainFragment.this.host = map.get("imgAddr").toString();
                        if ((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
                            MainFragment.this.dataList = (List) map.get(AopConstants.APP_PROPERTIES_KEY);
                            MainFragment.this.doResponse(z, onAppInfoResponse);
                            return;
                        }
                        onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(checkCode, (String) map.get("description")));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(203, MainFragment.this.getString(R$string.loading_fail)));
                    }
                }
            });
            if (this.dataList == null) {
                request.start();
            } else {
                doResponse(z, onAppInfoResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            onAppInfoResponse.onFail(ErrorInfoImpl.newInstance(203, getString(R$string.loading_fail)));
        }
    }

    public int checkCode(Map map) {
        if (map != null && map.containsKey("code") && map.get("code") != null) {
            if (map.get("code") instanceof String) {
                return Integer.valueOf(map.get("code").toString()).intValue();
            }
            if (map.get("code") instanceof Number) {
                return ((Number) map.get("code")).intValue();
            }
        }
        return -1;
    }

    public void doResponse(boolean z, OnAppInfoResponse onAppInfoResponse) {
        Map map;
        String str;
        AppResponse appResponse = new AppResponse();
        List list = this.dataList;
        if (list != null && list.size() > 1) {
            map = (Map) this.dataList.get(!z ? 1 : 0);
        } else {
            map = (Map) this.dataList.get(0);
        }
        appResponse.version = map.get(DatabaseFieldConfigLoader.FIELD_NAME_VERSION).toString();
        appResponse.title = map.get("title").toString();
        appResponse.imageUrl = this.host + map.get("imgPath").toString();
        appResponse.zipUrl = this.host + map.get("zipPath").toString();
        String obj = map.get("filePath").toString();
        appResponse.url = this.host + obj.substring(0, obj.lastIndexOf("/"));
        if (!z) {
            String str2 = null;
            if (map.get("loadingBgImg") != null) {
                if (TextUtils.isEmpty((String) map.get("loadingBgImg"))) {
                    str = null;
                } else {
                    str = this.host + map.get("loadingBgImg").toString();
                }
                appResponse.loadingBgImg = str;
            }
            if (map.get("loadingBgColor") != null) {
                appResponse.loadingBgColor = TextUtils.isEmpty((String) map.get("loadingBgColor")) ? null : map.get("loadingBgColor").toString();
            }
            if (map.get("loadingImg") != null) {
                try {
                    Gson gson = new Gson();
                    AppResponse.LoadingImgBean loadingImgBean = (AppResponse.LoadingImgBean) gson.fromJson(gson.toJson(map.get("loadingImg")), (Class<Object>) AppResponse.LoadingImgBean.class);
                    if (loadingImgBean.imgPath != null) {
                        str2 = this.host + loadingImgBean.imgPath;
                    }
                    loadingImgBean.imgPath = str2;
                    appResponse.loadingImg = loadingImgBean;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        onAppInfoResponse.onComplete(appResponse);
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (getHandler() != null && this.showRunAble != null) {
            getHandler().removeCallbacks(this.showRunAble);
            getContext().unregisterReceiver(this.receiver);
        }
        releaseViews();
    }

    protected void releaseViews() {
        LiquidView liquidView = this.liquidView;
        if (liquidView != null) {
            liquidView.removeAllViews();
            this.liquidView = null;
        }
        ICover iCover = this.launchCoverView;
        if (iCover != null) {
            iCover.removeAllViews();
            this.launchCoverView = null;
        }
    }
}
