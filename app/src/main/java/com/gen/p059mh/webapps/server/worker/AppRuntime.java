package com.gen.p059mh.webapps.server.worker;

import android.os.Handler;
import android.support.p002v4.app.NotificationCompat;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.view.View;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.JavaCallback;
import com.eclipsesource.p056v8.JavaVoidCallback;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8RuntimeException;
import com.eclipsesource.p056v8.V8Value;
import com.eclipsesource.p056v8.utils.V8ObjectUtils;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.build.Panel;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.modules.Page;
import com.gen.p059mh.webapps.server.HandlerExecutor;
import com.gen.p059mh.webapps.server.runtime.V8BaseRuntime;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.C2516Ad;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.gen.mh.webapps.server.worker.AppRuntime */
/* loaded from: classes2.dex */
public class AppRuntime extends Worker {
    public static final String APP_JS = "/app/app.js";
    AppController appController;
    Handler appHandler;
    String currentPageUrl;
    List<String> pageList;
    Stack<Page> pageStack = new Stack<>();
    boolean isPage = false;
    JavaCallback getCurrentPages = new JavaCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.2
        @Override // com.eclipsesource.p056v8.JavaCallback
        public Object invoke(V8Object v8Object, V8Array v8Array) {
            V8Array v8Array2 = new V8Array(((V8BaseRuntime) AppRuntime.this).runtime);
            for (Object obj : AppRuntime.this.pageStack.toArray()) {
                v8Array2.push((V8Value) ((Page) obj).getPage());
            }
            return v8Array2;
        }
    };
    JavaCallback navigateTo = new JavaCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.3
        @Override // com.eclipsesource.p056v8.JavaCallback
        public Object invoke(V8Object v8Object, V8Array v8Array) {
            return V8ObjectUtils.toV8Array(((V8BaseRuntime) AppRuntime.this).runtime, Arrays.asList(AppRuntime.this.pageStack.toArray()));
        }
    };
    JavaVoidCallback pageSetData = new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.4
        @Override // com.eclipsesource.p056v8.JavaVoidCallback
        public void invoke(V8Object v8Object, V8Array v8Array) {
            AppRuntime.this.appController.setData(AppRuntime.this.mGson.toJson(V8ObjectUtils.toMap(v8Array.getObject(0))));
            V8Object object = v8Array.getObject(1);
            V8Array v8Array2 = new V8Array(((V8BaseRuntime) AppRuntime.this).runtime);
            v8Array2.push((V8Value) ((V8BaseRuntime) AppRuntime.this).runtime);
            object.executeFunction(NotificationCompat.CATEGORY_CALL, v8Array2);
        }
    };
    JavaVoidCallback _onCreate = new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.5
        @Override // com.eclipsesource.p056v8.JavaVoidCallback
        public void invoke(V8Object v8Object, V8Array v8Array) {
            AppRuntime appRuntime = AppRuntime.this;
            if (!appRuntime.isPage) {
                final V8Object object = ((V8BaseRuntime) appRuntime).runtime.getObject("map");
                V8Object object2 = v8Array.getObject(0);
                HashMap hashMap = new HashMap();
                final String string = object.getString(DatabaseFieldConfigLoader.FIELD_NAME_ID);
                hashMap.put("input", AppRuntime.this.mGson.fromJson(object.get("input").toString(), (Class<Object>) Map.class));
                hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, string);
                hashMap.put("path", object.get("path"));
                V8Array v8Array2 = new V8Array(((V8BaseRuntime) AppRuntime.this).runtime);
                v8Array2.push((V8Value) V8ObjectUtils.toV8Object(((V8BaseRuntime) AppRuntime.this).runtime, hashMap));
                object2.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.5.2
                    @Override // com.eclipsesource.p056v8.JavaVoidCallback
                    public void invoke(V8Object v8Object2, V8Array v8Array3) {
                        AppRuntime.this.appController.setComponentData(string, AppRuntime.this.mGson.toJson(V8ObjectUtils.toMap(v8Array3.getObject(0))));
                        if (v8Array3.length() > 1) {
                            V8Object object3 = v8Array3.getObject(1);
                            V8Array v8Array4 = new V8Array(((V8BaseRuntime) AppRuntime.this).runtime);
                            v8Array4.push((V8Value) ((V8BaseRuntime) AppRuntime.this).runtime);
                            object3.executeFunction(NotificationCompat.CATEGORY_CALL, v8Array4);
                        }
                    }
                }, "_setData");
                object2.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.5.3
                    @Override // com.eclipsesource.p056v8.JavaVoidCallback
                    public void invoke(V8Object v8Object2, V8Array v8Array3) {
                        Logger.m4115e("_selectComponent");
                        String string2 = object.getString(DatabaseFieldConfigLoader.FIELD_NAME_ID);
                        Map<String, ? super Object> map = V8ObjectUtils.toMap(v8Array3.getObject(0));
                        boolean z = v8Array3.getBoolean(1);
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put("component_id", string2);
                        hashMap2.put("selector", map);
                        hashMap2.put("all", Boolean.valueOf(z));
                        AppRuntime appRuntime2 = AppRuntime.this;
                        appRuntime2.appController.selectComponent(appRuntime2.mGson.toJson(hashMap2));
                    }
                }, "_selectComponent");
                object2.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.5.4
                    @Override // com.eclipsesource.p056v8.JavaVoidCallback
                    public void invoke(V8Object v8Object2, V8Array v8Array3) {
                    }
                }, "_hrefURL");
                object2.executeFunction("_$processInputData", v8Array2);
                object2.executeFunction("created", null);
                try {
                    object2.executeFunction("attached", null);
                } catch (V8RuntimeException e) {
                    e.printStackTrace();
                }
                AppRuntime.this.pageStack.peek().getComponents().put(string, object2);
                AppRuntime.this.appController.onCallBack("_onComponentComplete", object.getString(DatabaseFieldConfigLoader.FIELD_NAME_ID), AppRuntime.this.mGson.toJson(V8ObjectUtils.toMap(object2).get(AopConstants.APP_PROPERTIES_KEY)));
                object2.executeFunction("ready", null);
            } else if (v8Array.length() <= 0) {
            } else {
                V8Object object3 = v8Array.getObject(0);
                AppRuntime.this.pageStack.peek().setPage(object3);
                AppRuntime appRuntime2 = AppRuntime.this;
                appRuntime2.appController.handlePageData(appRuntime2.currentPageUrl, appRuntime2.mGson.toJson(V8ObjectUtils.toMap(object3).get(AopConstants.APP_PROPERTIES_KEY)));
                object3.registerJavaMethod(AppRuntime.this.pageSetData, "_setData");
                object3.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.5.1
                    @Override // com.eclipsesource.p056v8.JavaVoidCallback
                    public void invoke(V8Object v8Object2, V8Array v8Array3) {
                        Logger.m4115e("_selectComponent");
                    }
                }, "_selectComponent");
            }
        }
    };
    Gson mGson = new Gson();

    public AppRuntime(IWebFragmentController iWebFragmentController, Handler handler, AppController appController) {
        super(iWebFragmentController);
        this.appHandler = handler;
        this.appController = appController;
    }

    public File loadFileFromUrl(String str) {
        try {
            URL url = new URL(str);
            File file = new File((this.webViewFragment.getWorkPath() + url.getPath()).replace(this.webViewFragment.getWorkHost(), ""));
            if (!file.exists()) {
                return null;
            }
            return file;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start(String str) {
        HandlerExecutor handlerExecutor;
        Logger.m4114e(C2516Ad.TYPE_START, str);
        final File loadFileFromUrl = loadFileFromUrl(str);
        if (!loadFileFromUrl.exists() || (handlerExecutor = this.handler) == null) {
            return;
        }
        handlerExecutor.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.AppRuntime.1
            @Override // java.lang.Runnable
            public void run() {
                AppRuntime.this.start(loadFileFromUrl);
            }
        });
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void onInit() {
        super.onInit();
        this.runtime.executeScript("console.warn('hello error')");
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void onReady(C1257V8 c1257v8) {
        c1257v8.registerJavaMethod(this._onCreate, "_onCreate");
        c1257v8.registerJavaMethod(this.getCurrentPages, "getCurrentPages");
        c1257v8.registerJavaMethod(this.navigateTo, "navigateTo");
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker
    public void initDefault() {
        File file = new File(this.webViewFragment.getDefaultsPath() + APP_JS);
        List<File> createWorkerBaseFile = createWorkerBaseFile();
        if (file.exists()) {
            createWorkerBaseFile.add(file);
        }
        for (int i = 0; i < createWorkerBaseFile.size(); i++) {
            executeFromPath(createWorkerBaseFile.get(i).getAbsolutePath());
        }
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void beforeExecute(File file) {
        super.beforeExecute(file);
        this.runtime.executeScript("global = self = this; exports = {};navigator = null;");
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void afterExecute(File file) {
        startLoad();
    }

    private void startLoad() {
        loadPage(this.pageList.get(0), new HashMap());
    }

    public /* synthetic */ void lambda$loadPage$0$AppRuntime(String str) {
        this.appController.checkTabBar(str);
    }

    public void loadPage(final String str, final Map<String, Object> map) {
        this.appHandler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$FQIGQiMXZv8q_07y1BjrBqul5SQ
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$loadPage$0$AppRuntime(str);
            }
        });
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$RXhuG-7xhMZNuUOm7ZRpaczpfSI
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$loadPage$1$AppRuntime(str, map);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x017d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$loadPage$1$AppRuntime(String str, Map map) {
        String str2;
        String str3;
        String str4;
        String canonicalPath;
        this.isPage = true;
        if (str.startsWith(File.separator)) {
            str4 = "http://" + ResourcesLoader.WORK_HOST + str;
            str3 = this.webViewFragment.getWorkPath() + str + ".js";
        } else if (this.currentPageUrl == null) {
            str4 = "http://" + ResourcesLoader.WORK_HOST + "/" + str;
            str3 = this.webViewFragment.getWorkPath() + File.separator + str + ".js";
        } else {
            try {
                canonicalPath = new File(new File(this.pageStack.peek().getCurrentPath()).getParent(), str).getCanonicalPath();
                str2 = canonicalPath.replace(new File(this.webViewFragment.getWorkPath()).getCanonicalPath(), this.webViewFragment.getWorkPath()) + ".js";
            } catch (IOException e) {
                e = e;
                str2 = "";
            }
            try {
                str4 = "http://" + ResourcesLoader.WORK_HOST + canonicalPath.replace(new File(this.webViewFragment.getWorkPath()).getCanonicalPath(), "");
                str3 = str2;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                str3 = str2;
                str4 = "";
                C1257V8 c1257v8 = this.runtime;
                c1257v8.add("query", V8ObjectUtils.toV8Object(c1257v8, map));
                this.runtime.executeScript("global._create_page=true");
                this.runtime.executeScript(" __wxRoute = \"" + str3 + "\"");
                if (!new File(str3).exists()) {
                }
            }
        }
        C1257V8 c1257v82 = this.runtime;
        c1257v82.add("query", V8ObjectUtils.toV8Object(c1257v82, map));
        this.runtime.executeScript("global._create_page=true");
        this.runtime.executeScript(" __wxRoute = \"" + str3 + "\"");
        if (!new File(str3).exists()) {
            this.currentPageUrl = str4;
            Page page = new Page();
            page.setCurrentPath(str3);
            this.pageStack.push(page);
            String replace = str3.substring(0, str3.length() - 3).replace(this.webViewFragment.getWorkPath(), "");
            V8Object executeObjectScript = this.runtime.executeObjectScript("_$req");
            V8Array v8Array = new V8Array(this.runtime);
            v8Array.push((V8Value) this.runtime).push(replace).push("");
            executeObjectScript.executeVoidFunction(NotificationCompat.CATEGORY_CALL, v8Array);
            return;
        }
        Logger.m4115e(String.format("no target path %s found", str3));
    }

    public void render(final Map map) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$ICcJCvbTdAwcTSULCaQp9CM1jS8
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$render$3$AppRuntime(map);
            }
        });
    }

    public /* synthetic */ void lambda$render$3$AppRuntime(Map map) {
        String obj = map.get("url").toString();
        final HashMap hashMap = new HashMap();
        hashMap.put("baseUrl", obj);
        map.put("debug", false);
        hashMap.put(AopConstants.APP_PROPERTIES_KEY, renderData(this.mGson.toJson(map)));
        hashMap.put("mimeType", "text/html");
        hashMap.put("encoding", "utf8");
        hashMap.put("historyUrl", obj);
        this.appHandler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$5yHb2Hvta6igLZS_Io8t-iSngbQ
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$null$2$AppRuntime(hashMap);
            }
        });
    }

    public /* synthetic */ void lambda$null$2$AppRuntime(Map map) {
        this.appController.renderPage(map);
    }

    public void onPageReady() {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$Ii9_df65govPdGEBUrxKDKz4dDg
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$onPageReady$4$AppRuntime();
            }
        });
    }

    public /* synthetic */ void lambda$onPageReady$4$AppRuntime() {
        V8Object page = this.pageStack.peek().getPage();
        V8Array v8Array = new V8Array(this.runtime);
        v8Array.push((V8Value) this.runtime.getObject("query"));
        page.executeVoidFunction("onLoad", v8Array);
        page.executeVoidFunction("onReady", null);
    }

    public void componentCall(final Map map) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$H0wX9yRzxZsuC3toDTGZ_ssIiaY
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$componentCall$5$AppRuntime(map);
            }
        });
    }

    public /* synthetic */ void lambda$componentCall$5$AppRuntime(Map map) {
        try {
            V8Array v8Array = new V8Array(this.runtime);
            v8Array.push(map.get("func").toString());
            v8Array.push((V8Value) V8ObjectUtils.toV8Array(this.runtime, (List) map.get("argv")));
            Page peek = this.pageStack.peek();
            if (!peek.getComponents().containsKey(map.get("call_data").toString())) {
                return;
            }
            peek.getComponents().get(map.get("call_data").toString()).executeFunction("_call", v8Array);
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            this.appController.onCallBack("_callback", map.get("cid").toString(), hashMap);
        } catch (V8RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void call(final Map map) {
        if (map.containsKey("call_data")) {
            componentCall(map);
        } else {
            this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$uj2KmCZRHKqqINFQskvBRCx5jOE
                @Override // java.lang.Runnable
                public final void run() {
                    AppRuntime.this.lambda$call$6$AppRuntime(map);
                }
            });
        }
    }

    public /* synthetic */ void lambda$call$6$AppRuntime(Map map) {
        try {
            V8Object page = this.pageStack.peek().getPage();
            V8Array v8Array = new V8Array(this.runtime);
            v8Array.push(map.get("func").toString());
            v8Array.push((V8Value) V8ObjectUtils.toV8Array(this.runtime, (List) map.get("argv")));
            page.executeFunction("_call", v8Array);
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            this.appController.onCallBack("_callback", map.get("cid").toString(), new Gson().toJson(hashMap));
        } catch (V8RuntimeException e) {
            e.printStackTrace();
        }
    }

    private String renderData(String str) {
        byte[] loadData = Utils.loadData(this.webViewFragment.getDefaultsPath() + "/appx.html", Utils.ENCODE_TYPE.DEFAULT, this.webViewFragment.getWACrypto());
        StringBuilder sb = new StringBuilder();
        sb.append("<script>window._base = " + str + "</script>");
        sb.append("<script src=\"" + ("http://" + ResourcesLoader.DEFAULTS_HOST + "/app/android/bridge.js") + "\"></script>\n");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<script src=\"" + ("http://" + ResourcesLoader.DEFAULTS_HOST + "/app/renderer.js") + "\"></script>\n");
        return inject(new String(loadData), sb.toString(), sb2.toString());
    }

    public void createComponent(final Map map) {
        final String obj = map.get("path").toString();
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$LCekliSirBwCQriv5ZBkNuRQFeY
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$createComponent$7$AppRuntime(map, obj);
            }
        });
    }

    public /* synthetic */ void lambda$createComponent$7$AppRuntime(Map map, String str) {
        this.isPage = false;
        this.runtime.executeScript("global._create_page =false");
        C1257V8 c1257v8 = this.runtime;
        c1257v8.add("map", V8ObjectUtils.toV8Object(c1257v8, map));
        V8Object executeObjectScript = this.runtime.executeObjectScript("_$req");
        V8Array v8Array = new V8Array(this.runtime);
        v8Array.push((V8Value) this.runtime).push(str).push(com.j256.ormlite.logger.Logger.ARG_STRING);
        executeObjectScript.executeVoidFunction(NotificationCompat.CATEGORY_CALL, v8Array);
    }

    private static String inject(String str, String str2, String str3) {
        int indexOf;
        String str4;
        int indexOf2;
        if (str.indexOf("</head>") > 0) {
            str4 = str.substring(0, indexOf) + str2 + str.substring(indexOf);
        } else {
            str4 = "<head>" + str2 + "</head>" + str;
        }
        if (str4.indexOf("<body>") + 6 > 0) {
            return str4.substring(0, indexOf2) + str3 + str4.substring(indexOf2);
        }
        return str4;
    }

    public void setPageList(List<String> list) {
        this.pageList = list;
    }

    public void addWebPanel(final Panel panel) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$zsWZ92-6fcqLu8FcVq4miUMao8A
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$addWebPanel$8$AppRuntime(panel);
            }
        });
    }

    public /* synthetic */ void lambda$addWebPanel$8$AppRuntime(Panel panel) {
        this.pageStack.peek().setPanel(panel);
    }

    public WebEngine getCurrentWebEngine() {
        return this.pageStack.peek().getPageView();
    }

    public SwipeRefreshLayout getCurrentRefresh() {
        return this.pageStack.peek().getPanel().getRefreshView();
    }

    public View getCurrentView() {
        return this.pageStack.peek().getPanel().getPanelView();
    }

    public void onRefresh() {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$EgaZYdPe8SgE-_dd_dx3VvicTCM
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$onRefresh$9$AppRuntime();
            }
        });
    }

    public /* synthetic */ void lambda$onRefresh$9$AppRuntime() {
        Logger.m4115e("onRefresh");
        this.pageStack.peek().getPage().executeFunction("onPullDownRefresh", null);
    }

    public Stack<Page> getPageStack() {
        return this.pageStack;
    }

    public void pop(final boolean z) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$wRxMq8aZt84u3H4G7ulIMUQ2mdQ
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$pop$10$AppRuntime(z);
            }
        });
    }

    public /* synthetic */ void lambda$pop$10$AppRuntime(boolean z) {
        popSync(z);
        if (getPageStack().size() > 0) {
            C1257V8 c1257v8 = this.runtime;
            c1257v8.executeScript(" __wxRoute = \"" + this.pageStack.peek().getCurrentPath() + "\"");
        }
    }

    private synchronized void popSync(final boolean z) {
        final Page peek = this.pageStack.peek();
        peek.getPage().executeFunction("onUnload", null);
        for (String str : peek.getComponents().keySet()) {
            peek.getComponents().get(str).executeFunction("detached", null);
            peek.getComponents().get(str).close();
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.appHandler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$MQALLW6SvVUwMzX4y7PG4Ov_57c
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.lambda$popSync$11(z, peek, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        peek.release();
        getPageStack().pop();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$popSync$11(boolean z, Page page, CountDownLatch countDownLatch) {
        if (z) {
            page.getPanel().release();
        }
        countDownLatch.countDown();
    }

    public void emit(final String str, final List<String> list) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$y-UutcB33EmOsxdbCbYtCwuvYkI
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$emit$12$AppRuntime(list, str);
            }
        });
    }

    public /* synthetic */ void lambda$emit$12$AppRuntime(List list, String str) {
        this.runtime.executeFunction(str, V8ObjectUtils.toV8Array(this.runtime, list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doRelease */
    public synchronized void lambda$release$13$AppRuntime() {
        if (this.pageStack != null && this.pageStack.size() > 0) {
            Logger.m4114e("release", this.pageStack.peek().getCurrentPath());
            popSync(true);
            if (this.pageStack.size() > 0) {
                lambda$release$13$AppRuntime();
            }
        }
        if (this.pageStack != null) {
            this.pageStack.clear();
            this.pageStack = null;
        }
        destroyWorker();
    }

    public void release() {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.-$$Lambda$AppRuntime$jt5eskzvRv-thtNMtAUD-dVQGW8
            @Override // java.lang.Runnable
            public final void run() {
                AppRuntime.this.lambda$release$13$AppRuntime();
            }
        });
    }

    public Hashtable<String, Plugin> getPlugins() {
        return this.plugins;
    }
}
