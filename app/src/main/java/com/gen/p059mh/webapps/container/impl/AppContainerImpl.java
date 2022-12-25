package com.gen.p059mh.webapps.container.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.NotificationCompat;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.build.PagePanelBuilder;
import com.gen.p059mh.webapps.build.Panel;
import com.gen.p059mh.webapps.build.tabbar.TabBar;
import com.gen.p059mh.webapps.build.tabbar.TabBarImpl;
import com.gen.p059mh.webapps.container.BaseContainerFragment;
import com.gen.p059mh.webapps.container.impl.AppContainerImpl;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.ITabBarOperation;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.ImageCallBack;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.listener.WebAppCallBack;
import com.gen.p059mh.webapps.pugins.WebAppPlugin;
import com.gen.p059mh.webapps.server.worker.AppRuntime;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.one.tomato.entity.C2516Ad;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.gen.mh.webapps.container.impl.AppContainerImpl */
/* loaded from: classes2.dex */
public class AppContainerImpl extends BaseContainerFragment {
    static int pageID = 696593;
    AppRuntime appRuntime;
    boolean enablePullDownRefresh;
    LinearLayout linearLayout;
    PagePanelBuilder pagePanelBuilder;
    List<String> pages;
    FrameLayout rootLayout;
    String startUrl;
    TabBar tabBar;
    Map tabBarParams;
    LinearLayout tabLayout;
    int width;
    List<String> templateEnd = Arrays.asList("wxml", "axml", "swan");
    List<String> styleEnd = Arrays.asList("css", "wxss", "acss");
    Map navigationBarParams = new HashMap();
    boolean isDefaultNavigationBar = false;
    boolean isShowTabBar = false;
    AppController appController = new C17731();
    WebAppCallBack webAppCallBack = new C17792();

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void initializerPlugins() {
        super.initializerPlugins();
        registerPlugin(new WebAppPlugin(this.webAppCallBack));
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void initWorkUnity() {
        Logger.m4115e("initWorkUnity");
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (this.appRuntime == null) {
            IWebFragmentController iWebFragmentController = this.webFragmentController;
            this.appRuntime = new AppRuntime(iWebFragmentController, iWebFragmentController.getHandler(), this.appController);
        }
        this.webFragmentController.initializerPlugins();
        this.webFragmentController.initializerUnitObject();
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.rootLayout == null) {
            this.linearLayout = new LinearLayout(viewGroup.getContext());
            this.linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
            this.linearLayout.setOrientation(1);
            this.rootLayout = new FrameLayout(viewGroup.getContext());
            this.rootLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
            new LayoutTransition();
            this.width = Utils.displaymetrics(getActivity()).widthPixels;
            this.linearLayout.addView(this.rootLayout);
            this.tabLayout = new LinearLayout(getContext());
            this.tabLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) Utils.d2p(viewGroup.getContext(), 60)));
            this.tabLayout.setVisibility(8);
            this.linearLayout.addView(this.tabLayout);
        }
        return this.linearLayout;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        List<String> list = this.pages;
        if (list != null) {
            this.appRuntime.setPageList(list);
        }
        if (this.startUrl != null) {
            Logger.m4112i("AppFragment", "onViewCreated" + this.startUrl);
            this.appRuntime.start(this.startUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.container.impl.AppContainerImpl$1 */
    /* loaded from: classes2.dex */
    public class C17731 implements AppController {
        C17731() {
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void setData(final String str) {
            final WebEngine currentWebEngine = AppContainerImpl.this.appRuntime.getCurrentWebEngine();
            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$1$UO0ziQqyzLHvBO22yMoNoQvv560
                @Override // java.lang.Runnable
                public final void run() {
                    WebEngine.this.loadUrl("javascript:Bridge._$setData(" + str + ")");
                }
            });
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void setComponentData(final String str, final String str2) {
            final WebEngine currentWebEngine = AppContainerImpl.this.appRuntime.getCurrentWebEngine();
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$1$iBj0QiaVbQxZf7t9BSVS_3b3aM8
                @Override // java.lang.Runnable
                public final void run() {
                    AppContainerImpl.C17731.lambda$setComponentData$1(WebEngine.this, str, str2, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$setComponentData$1(WebEngine webEngine, String str, String str2, CountDownLatch countDownLatch) {
            webEngine.loadUrl("javascript:Bridge._setData('" + str + "','" + str2 + "')");
            countDownLatch.countDown();
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void handlePageData(String str, Object obj) {
            HashMap hashMap = new HashMap();
            hashMap.put("_title", "");
            hashMap.put(AopConstants.APP_PROPERTIES_KEY, obj);
            StringBuilder sb = new StringBuilder();
            sb.append("p_");
            int i = AppContainerImpl.pageID;
            AppContainerImpl.pageID = i + 1;
            sb.append(i);
            hashMap.put("pageID", sb.toString());
            hashMap.put("url", str);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("app", "http://" + ResourcesLoader.WORK_HOST + "/_res_/");
            hashMap2.put("tmp", "http://" + ResourcesLoader.WORK_HOST + "/_tmp_/");
            hashMap.put("urls", hashMap2);
            HashMap hashMap3 = new HashMap();
            hashMap.put("contents", hashMap3);
            HashMap hashMap4 = new HashMap();
            hashMap.put("usingComponents", hashMap4);
            AppRuntime appRuntime = AppContainerImpl.this.appRuntime;
            hashMap.put("baseStyle", appRuntime.loadDataFromPath(((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getDefaultsPath() + "/app.css"));
            File file = new File(((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getWorkPath());
            boolean z = false;
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                int length = listFiles.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    File file2 = listFiles[i2];
                    String name = file2.getName();
                    if (AppContainerImpl.this.styleEnd.contains(name.substring(name.lastIndexOf(".") + 1))) {
                        hashMap.put("style", AppContainerImpl.this.appRuntime.compileCss(file2.getAbsolutePath(), null, false));
                        break;
                    }
                    i2++;
                }
            }
            AppContainerImpl.this.loadContents(str, hashMap3, hashMap4, false);
            AppContainerImpl appContainerImpl = AppContainerImpl.this;
            Map map = appContainerImpl.navigationBarParams;
            if (appContainerImpl.appRuntime.getPageStack().size() == 1) {
                z = true;
            }
            map.put("isHome", Boolean.valueOf(z));
            AppContainerImpl.this.appRuntime.render(hashMap);
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void renderPage(Map<String, String> map) {
            AppContainerImpl appContainerImpl = AppContainerImpl.this;
            appContainerImpl.pagePanelBuilder = new PagePanelBuilder(appContainerImpl.getContext());
            AppContainerImpl appContainerImpl2 = AppContainerImpl.this;
            PagePanelBuilder pagePanelBuilder = appContainerImpl2.pagePanelBuilder;
            pagePanelBuilder.buildSwipeRefreshView(appContainerImpl2.enablePullDownRefresh);
            AppContainerImpl appContainerImpl3 = AppContainerImpl.this;
            pagePanelBuilder.buildNavigationBar(appContainerImpl3.isDefaultNavigationBar, appContainerImpl3.navigationBarParams);
            pagePanelBuilder.buildTabBar(AppContainerImpl.this.tabBarParams);
            pagePanelBuilder.addWidth(AppContainerImpl.this.width);
            Panel build = pagePanelBuilder.build();
            if (AppContainerImpl.this.isDefaultNavigationBar) {
                build.getNavigationBar().setBackClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$1$x-GDHZzKJJ1VZskdFzfaYLYZZqE
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppContainerImpl.C17731.this.lambda$renderPage$2$AppContainerImpl$1(view);
                    }
                });
            }
            AppContainerImpl.this.appRuntime.addWebPanel(build);
            WebEngine webView = build.getWebView();
            if (webView != null) {
                AppContainerImpl appContainerImpl4 = AppContainerImpl.this;
                if (appContainerImpl4.rootLayout != null) {
                    webView.addJavascriptInterface(new EventBridge(), "_event");
                    build.getRefreshView().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.1.1
                        @Override // android.support.p002v4.widget.SwipeRefreshLayout.OnRefreshListener
                        public void onRefresh() {
                            AppContainerImpl.this.appRuntime.onRefresh();
                        }
                    });
                    webView.loadDataWithBaseURL(map.get("baseUrl"), map.get(AopConstants.APP_PROPERTIES_KEY), map.get("mimeType"), map.get("encoding"), map.get("historyUrl"));
                    webView.setPageLoadFinishCallBack(new PageLoadFinishListener() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.1.2
                        @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
                        public void onLoadPageError(RuntimeException runtimeException) {
                        }

                        @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
                        public void onLoadPageFinish(String str) {
                            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.loadComplete(1);
                        }
                    });
                }
            }
            AppContainerImpl.this.rootLayout.addView(build.getPanelView());
            boolean z = true;
            if (AppContainerImpl.this.appRuntime.getPageStack().size() != 1) {
                z = false;
            }
            build.animIn(z, new AnimatorListenerAdapter() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.1.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator, boolean z2) {
                    AppContainerImpl appContainerImpl5 = AppContainerImpl.this;
                    appContainerImpl5.tabLayout.setVisibility(appContainerImpl5.isShowTabBar ? 0 : 8);
                }
            });
        }

        public /* synthetic */ void lambda$renderPage$2$AppContainerImpl$1(View view) {
            AppContainerImpl.this.pagePop();
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void onCallBack(String str, String str2, Object obj) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final WebEngine currentWebEngine = AppContainerImpl.this.appRuntime.getCurrentWebEngine();
            final String format = String.format("javascript:Bridge.%s('%s',%s)", str, str2, obj);
            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$1$Jld38WK7uCHTVl46zLEYeJJr7Ko
                @Override // java.lang.Runnable
                public final void run() {
                    AppContainerImpl.C17731.lambda$onCallBack$3(WebEngine.this, format, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onCallBack$3(WebEngine webEngine, String str, CountDownLatch countDownLatch) {
            webEngine.loadUrl(str);
            countDownLatch.countDown();
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void checkTabBar(String str) {
            AppContainerImpl appContainerImpl = AppContainerImpl.this;
            appContainerImpl.isShowTabBar = false;
            Map map = appContainerImpl.tabBarParams;
            if (map != null && map.size() > 0 && AppContainerImpl.this.tabBarParams.get(C2516Ad.TYPE_LIST) != null) {
                for (Map map2 : (List) AppContainerImpl.this.tabBarParams.get(C2516Ad.TYPE_LIST)) {
                    if (str.contains(map2.get("pagePath").toString())) {
                        AppContainerImpl.this.isShowTabBar = true;
                    }
                }
            }
            AppContainerImpl appContainerImpl2 = AppContainerImpl.this;
            if (appContainerImpl2.tabLayout == null || appContainerImpl2.tabBar != null) {
                return;
            }
            appContainerImpl2.tabBar = new TabBarImpl();
            AppContainerImpl.this.tabBar.setOperation(new ITabBarOperation() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.1.4
                @Override // com.gen.p059mh.webapps.listener.ITabBarOperation
                public void loadIcon(String str2, ImageCallBack<Drawable> imageCallBack) {
                    ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.loadImage(str2, imageCallBack);
                }

                @Override // com.gen.p059mh.webapps.listener.ITabBarOperation
                public void onClick(int i, String str2) {
                    AppContainerImpl.this.pagePop();
                    AppContainerImpl.this.webAppCallBack.navigateTo(str2);
                }
            });
            AppContainerImpl appContainerImpl3 = AppContainerImpl.this;
            Map map3 = appContainerImpl3.tabBarParams;
            if (map3 == null) {
                return;
            }
            appContainerImpl3.tabBar.init(appContainerImpl3.tabLayout, map3);
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void showTabBar(boolean z, boolean z2) {
            if (!z || AppContainerImpl.this.tabLayout.getVisibility() != 0) {
                int i = 8;
                if (!z && AppContainerImpl.this.tabLayout.getVisibility() == 8) {
                    return;
                }
                if (!z2) {
                    LinearLayout linearLayout = AppContainerImpl.this.tabLayout;
                    if (z) {
                        i = 0;
                    }
                    linearLayout.setVisibility(i);
                } else if (z) {
                    ObjectAnimator.ofFloat(AppContainerImpl.this.tabLayout, "translationY", 240.0f, 0.0f).start();
                    AppContainerImpl.this.tabLayout.setVisibility(0);
                } else {
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(AppContainerImpl.this.tabLayout, "translationY", 0.0f, 240.0f);
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.1.5
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator, boolean z3) {
                            AppContainerImpl.this.tabLayout.setVisibility(8);
                        }
                    });
                    ofFloat.start();
                }
            }
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void showTabBarRedDot(boolean z, int i) {
            TabBar tabBar = AppContainerImpl.this.tabBar;
            if (tabBar != null) {
                tabBar.showTabBarRedDot(z, i);
            }
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void setTabBarBadge(boolean z, int i, String str) {
            TabBar tabBar = AppContainerImpl.this.tabBar;
            if (tabBar != null) {
                tabBar.setTabBarBadge(z, i, str);
            }
        }

        @Override // com.gen.p059mh.webapps.listener.AppController
        public void selectComponent(final String str) {
            final String str2 = "selectComponent_" + new Date().getTime();
            final WebEngine currentWebEngine = AppContainerImpl.this.appRuntime.getCurrentWebEngine();
            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$1$9gWvlLLwVSK7aFAK_x9gtMih_CE
                @Override // java.lang.Runnable
                public final void run() {
                    WebEngine.this.loadUrl("javascript:Bridge._$invoke('selectComponent','" + str + "','" + str2 + "')");
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public AppController getAppController() {
        return this.appController;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.container.impl.AppContainerImpl$2 */
    /* loaded from: classes2.dex */
    public class C17792 implements WebAppCallBack {
        C17792() {
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void navigateTo(String str) {
            if (AppContainerImpl.this.appRuntime != null) {
                HashMap hashMap = new HashMap();
                if (str.contains("?")) {
                    String[] split = str.split("[?]");
                    String str2 = split[0];
                    if (split.length > 1 && split[1] != null) {
                        String[] split2 = split[1].split("&");
                        if (split2.length > 0) {
                            for (String str3 : split2) {
                                if (str3.contains(SimpleComparison.EQUAL_TO_OPERATION)) {
                                    String[] split3 = str3.split(SimpleComparison.EQUAL_TO_OPERATION);
                                    hashMap.put(split3[0], split3[1]);
                                }
                            }
                        }
                    }
                    str = str2;
                }
                if (!str.startsWith("/")) {
                    try {
                        String replace = new File(new File(AppContainerImpl.this.appRuntime.getPageStack().peek().getCurrentPath()).getParent(), str).getCanonicalPath().replace(new File(((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getWorkPath()).getCanonicalPath(), "");
                        Logger.m4114e("navigateTo", replace);
                        str = replace;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AppContainerImpl.this.appRuntime.loadPage(str, hashMap);
                    return;
                }
                AppContainerImpl.this.appRuntime.loadPage(str, hashMap);
            }
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void onRefresh(boolean z) {
            AppContainerImpl.this.appRuntime.getCurrentRefresh().setRefreshing(z);
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void scroll(int i, int i2) {
            AppContainerImpl.this.appRuntime.getCurrentWebEngine().provideView().scrollBy(0, i);
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void navigateBack(int i) {
            if (i >= AppContainerImpl.this.appRuntime.getPageStack().size()) {
                i = AppContainerImpl.this.appRuntime.getPageStack().size() - 1;
            }
            while (i > 0) {
                AppContainerImpl.this.pagePop();
                i--;
            }
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void emit(String str, List<String> list) {
            AppContainerImpl.this.appRuntime.emit(str, list);
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void reLaunch(final String str) {
            ((BaseContainerFragment) AppContainerImpl.this).webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.container.impl.-$$Lambda$AppContainerImpl$2$bxJGFBNghr6eva-iK_gmHow7IX0
                @Override // java.lang.Runnable
                public final void run() {
                    AppContainerImpl.C17792.this.lambda$reLaunch$0$AppContainerImpl$2(str);
                }
            });
        }

        public /* synthetic */ void lambda$reLaunch$0$AppContainerImpl$2(String str) {
            AppContainerImpl.this.pagePopAll(false);
            navigateTo(str);
        }

        @Override // com.gen.p059mh.webapps.listener.WebAppCallBack
        public void redirectTo(String str) {
            AppContainerImpl.this.pagePop(false);
            navigateTo(str);
        }
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void start(String str, String str2) {
        this.startUrl = str;
        AppRuntime appRuntime = this.appRuntime;
        if (appRuntime != null) {
            appRuntime.start(this.startUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadContents(String str, Map<String, Object> map, Map<String, Object> map2, boolean z) throws RuntimeException {
        File file;
        File[] listFiles;
        if (str.startsWith("http")) {
            file = this.appRuntime.loadFileFromUrl(str.substring(0, str.lastIndexOf("/")));
        } else {
            file = new File(this.webFragmentController.getWorkPath() + str.substring(0, str.lastIndexOf("/")));
        }
        if (file.exists() && file.isDirectory()) {
            File file2 = null;
            File file3 = null;
            File file4 = null;
            for (File file5 : file.listFiles()) {
                String endStr = getEndStr(file5.getName());
                if (this.templateEnd.contains(endStr)) {
                    file2 = file5;
                } else if (this.styleEnd.contains(endStr)) {
                    file3 = file5;
                } else if (endStr.contains("json")) {
                    file4 = file5;
                }
            }
            if (!map.containsKey(str)) {
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                hashMap2.put("type", getEndStr(file2.getName()));
                hashMap2.put("content", this.appRuntime.loadDataFromPath(file2.getPath()));
                hashMap.put("template", hashMap2);
                HashMap hashMap3 = new HashMap();
                if (file3 != null) {
                    hashMap3.put("type", getEndStr(file3.getName()));
                    hashMap3.put("content", this.appRuntime.compileCss(file3.getPath(), str, z));
                    hashMap.put("style", hashMap3);
                }
                map.put(str, hashMap);
            }
            if (file4 == null || !file4.exists()) {
                return;
            }
            Map map3 = (Map) new Gson().fromJson(this.appRuntime.loadDataFromPath(file4.getPath()), (Class<Object>) Map.class);
            if (map3.get("navigationBarTitleText") != null) {
                this.navigationBarParams.put("navigationBarTitleText", map3.get("navigationBarTitleText").toString());
            }
            if (map3.get("usingComponents") == null) {
                return;
            }
            Map map4 = (Map) map3.get("usingComponents");
            for (String str2 : map4.keySet()) {
                String str3 = (String) map4.get(str2);
                try {
                    HashMap hashMap4 = new HashMap();
                    if (!str3.startsWith("/")) {
                        str3 = new File(file.getAbsolutePath(), str3).getCanonicalPath().replace(new File(this.webFragmentController.getWorkPath()).getCanonicalPath(), "");
                    }
                    hashMap4.put("path", str3);
                    HashMap hashMap5 = new HashMap();
                    hashMap4.put("usingComponents", hashMap5);
                    map2.put(str2, hashMap4);
                    loadContents(str3, map, hashMap5, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        Logger.m4115e("nothing happen");
        throw new RuntimeException("target file not exist");
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void onBackPressed() {
        super.onBackPressed();
        if (this.appRuntime.getPageStack().size() > 1) {
            pagePop();
        } else {
            this.webFragmentController.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pagePopAll(boolean z) {
        int size = this.appRuntime.getPageStack().size();
        for (int i = 0; i < size; i++) {
            pagePop(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pagePop() {
        pagePop(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pagePop(boolean z) {
        if (z) {
            boolean z2 = true;
            if (this.rootLayout.getChildCount() != 1) {
                z2 = false;
            }
            final Panel panel = this.appRuntime.getPageStack().peek().getPanel();
            this.appRuntime.pop(false);
            panel.animOut(z2, new AnimatorListenerAdapter() { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator, boolean z3) {
                    AppContainerImpl.this.rootLayout.removeView(panel.getPanelView());
                    panel.release();
                    AppContainerImpl appContainerImpl = AppContainerImpl.this;
                    if (appContainerImpl.tabBarParams == null || appContainerImpl.tabLayout == null) {
                        return;
                    }
                    appContainerImpl.appController.checkTabBar(appContainerImpl.appRuntime.getPageStack().peek().getCurrentPath());
                    AppContainerImpl appContainerImpl2 = AppContainerImpl.this;
                    if (!appContainerImpl2.isShowTabBar) {
                        return;
                    }
                    appContainerImpl2.tabLayout.setVisibility(0);
                }
            });
            return;
        }
        Panel panel2 = this.appRuntime.getPageStack().peek().getPanel();
        this.appRuntime.pop(false);
        this.rootLayout.removeView(panel2.getPanelView());
        panel2.release();
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public Hashtable<String, Plugin> getPlugins() {
        AppRuntime appRuntime = this.appRuntime;
        if (appRuntime != null) {
            return appRuntime.getPlugins();
        }
        return super.getPlugins();
    }

    public String getEndStr(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void processConfigs(Map map) {
        super.processConfigs(map);
        if (map.get("window") != null) {
            Map map2 = (Map) map.get("window");
            boolean z = true;
            this.enablePullDownRefresh = map2.get("enablePullDownRefresh") != null && ((Boolean) map2.get("enablePullDownRefresh")).booleanValue();
            if (map2.get("navigationStyle") != null && map2.get("navigationStyle").equals("custom")) {
                z = false;
            }
            this.isDefaultNavigationBar = z;
            if (this.isDefaultNavigationBar) {
                this.navigationBarParams = map2;
            }
        }
        if (map.get("tabBar") != null) {
            this.tabBarParams = (Map) map.get("tabBar");
            TabBar tabBar = this.tabBar;
            if (tabBar != null) {
                tabBar.init(this.rootLayout, this.tabBarParams);
            }
        }
        this.pages = (List) map.get("pages");
        AppRuntime appRuntime = this.appRuntime;
        if (appRuntime != null) {
            appRuntime.setPageList(this.pages);
        }
    }

    void navigate(Map map) {
        if (map.get(AopConstants.APP_PROPERTIES_KEY) != null) {
            Map map2 = (Map) new Gson().fromJson(map.get(AopConstants.APP_PROPERTIES_KEY).toString(), (Class<Object>) Map.class);
            if (!"self".equals(map2.get("target").toString()) || !"navigate".equals(map2.get("open-type").toString())) {
                return;
            }
            this.webAppCallBack.navigateTo(map2.get("url").toString());
        }
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void registerPlugin(Plugin plugin) {
        plugin.webViewFragment = this.webFragmentController;
        AppRuntime appRuntime = this.appRuntime;
        plugin.executor = appRuntime;
        appRuntime.getPlugins().put(plugin.getName(), plugin);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void release() {
        AppRuntime appRuntime = this.appRuntime;
        if (appRuntime != null) {
            appRuntime.release();
        }
        PagePanelBuilder pagePanelBuilder = this.pagePanelBuilder;
        if (pagePanelBuilder != null) {
            pagePanelBuilder.release();
            this.pagePanelBuilder = null;
        }
        LinearLayout linearLayout = this.linearLayout;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            this.linearLayout = null;
        }
        LinearLayout linearLayout2 = this.tabLayout;
        if (linearLayout2 != null) {
            linearLayout2.removeAllViews();
            this.tabLayout = null;
        }
        FrameLayout frameLayout = this.rootLayout;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            this.rootLayout = null;
        }
        TabBar tabBar = this.tabBar;
        if (tabBar != null) {
            tabBar.release();
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    /* renamed from: com.gen.mh.webapps.container.impl.AppContainerImpl$EventBridge */
    /* loaded from: classes2.dex */
    public class EventBridge {
        public EventBridge() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0052, code lost:
            if (r0 == 1) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0054, code lost:
            if (r0 == 2) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0057, code lost:
            printMessage(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x005b, code lost:
            invokeMessage(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:?, code lost:
            return;
         */
        @JavascriptInterface
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void _dispatchMessage(String str, String str2) {
            Logger.m4112i("type", str);
            Logger.m4112i("_dispatchMessage", str2);
            if (AppContainerImpl.this.appRuntime == null) {
                Logger.m4115e("runtime init error");
                return;
            }
            char c = 65535;
            try {
                int hashCode = str.hashCode();
                if (hashCode != -1183693704) {
                    if (hashCode != 3446944) {
                        if (hashCode == 106934957 && str.equals("print")) {
                            c = 2;
                        }
                    } else if (str.equals("post")) {
                        c = 0;
                    }
                } else if (str.equals("invoke")) {
                    c = 1;
                }
                postMessage((Map) new Gson().fromJson(str2, new TypeToken<HashMap<String, Object>>(this) { // from class: com.gen.mh.webapps.container.impl.AppContainerImpl.EventBridge.1
                }.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void postMessage(Map map) {
            char c;
            String obj = map.get("type").toString();
            switch (obj.hashCode()) {
                case -1821010580:
                    if (obj.equals("create-component")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -934978833:
                    if (obj.equals("ready2")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3045982:
                    if (obj.equals(NotificationCompat.CATEGORY_CALL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2102494577:
                    if (obj.equals("navigate")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                AppContainerImpl.this.appRuntime.onPageReady();
            } else if (c == 1) {
                AppContainerImpl.this.appRuntime.call(map);
            } else if (c == 2) {
                AppContainerImpl.this.appRuntime.createComponent(map);
            } else if (c != 3) {
            } else {
                AppContainerImpl.this.navigate(map);
            }
        }

        public void invokeMessage(String str) {
            Logger.m4112i("invokeMessage", str);
        }

        public void printMessage(String str) {
            Logger.m4112i("printMessage", str);
        }
    }
}
