package com.gen.p059mh.webapps.server.runtime;

import android.util.Base64;
import android.util.Log;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.JavaVoidCallback;
import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8RuntimeException;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.server.HandlerExecutor;
import com.gen.p059mh.webapps.server.worker.Console;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.tomatolive.library.websocket.nvwebsocket.ConnectSocketParams;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.gen.mh.webapps.server.runtime.V8BaseRuntime */
/* loaded from: classes2.dex */
public abstract class V8BaseRuntime implements Plugin.Executor, V8Lifecycle {
    protected String handleKey;
    protected HandlerExecutor handler;
    protected C1257V8 runtime;
    protected IWebFragmentController webViewFragment;
    protected Hashtable<String, Plugin> plugins = new Hashtable<>();
    protected HashMap<String, JSResponseListener> responseListeners = new HashMap<>();
    protected Hashtable<String, String> cacheData = new Hashtable<>();
    protected Stack<String> currentPathStack = new Stack<>();

    public C1257V8 getRuntime() {
        return this.runtime;
    }

    public HandlerExecutor getHandler() {
        return this.handler;
    }

    public V8BaseRuntime(IWebFragmentController iWebFragmentController) {
        this.webViewFragment = iWebFragmentController;
        this.handler = new HandlerExecutor(false, iWebFragmentController);
        initWorkPlugin();
        initWorkNativeView();
        initWorkUnity();
        this.runtime = this.handler.getV8Runtime();
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.-$$Lambda$V8BaseRuntime$1ISakbVOAjEtELYz00BKeom1eOw
            @Override // java.lang.Runnable
            public final void run() {
                V8BaseRuntime.this.lambda$new$0$V8BaseRuntime();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$V8BaseRuntime() {
        onInit();
        onReady(this.runtime);
    }

    protected void initWorkNativeView() {
        this.webViewFragment.initWorkNativeView(getClassName());
    }

    private void initWorkUnity() {
        this.webViewFragment.initWorkUnity(getClassName());
    }

    public String getClassName() {
        return getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
    }

    private void initWorkPlugin() {
        this.webViewFragment.initWorkPlugin(this.plugins, this, getClassName());
    }

    public void onInit() {
        this.runtime.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.1
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object, V8Array v8Array) {
                if (v8Array.length() > 0) {
                    Object obj = v8Array.get(0);
                    Log.e("invoke", obj.toString());
                    if (!(obj instanceof Releasable)) {
                        return;
                    }
                    ((Releasable) obj).release();
                }
            }
        }, "print");
        Console console = new Console();
        V8Object v8Object = new V8Object(this.runtime);
        v8Object.registerJavaMethod(console, "log", "log", new Class[]{Object.class});
        v8Object.registerJavaMethod(console, "error", "error", new Class[]{Object.class});
        v8Object.registerJavaMethod(console, "error1", "error", new Class[]{Object.class, Object.class});
        v8Object.registerJavaMethod(console, ConnectSocketParams.MESSAGE_WARN_TYPE, ConnectSocketParams.MESSAGE_WARN_TYPE, new Class[]{Object.class});
        this.runtime.add("console", v8Object);
        v8Object.close();
        this.runtime.executeScript("var self = this");
        this.runtime.executeScript("window = self");
        JavaVoidCallback javaVoidCallback = new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.2
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object2, V8Array v8Array) {
                if (v8Array.length() > 0) {
                    for (int i = 0; i < v8Array.length(); i++) {
                        Object obj = v8Array.get(i);
                        String peek = V8BaseRuntime.this.currentPathStack.peek();
                        String substring = peek.substring(0, peek.lastIndexOf(File.separator));
                        if (obj != null) {
                            String obj2 = obj.toString();
                            String[] split = obj2.split(File.separator);
                            if (split.length > 0 && !split[0].equals("")) {
                                ArrayList arrayList = new ArrayList(Arrays.asList(substring.split("/")));
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    if (!split[i2].equals(".")) {
                                        if (split[i2].equals("..")) {
                                            arrayList.remove(arrayList.size() - 1);
                                        } else {
                                            arrayList.add(split[i2]);
                                        }
                                    }
                                }
                                substring = V8BaseRuntime.this.join(arrayList, File.separator);
                            } else {
                                substring = substring + obj2;
                            }
                        }
                        V8BaseRuntime.this.executeFromPath(substring);
                        if (obj instanceof Releasable) {
                            ((Releasable) obj).release();
                        }
                    }
                }
            }
        };
        this.runtime.registerJavaMethod(javaVoidCallback, "importScript");
        this.runtime.registerJavaMethod(javaVoidCallback, "importScripts");
        Api api = new Api();
        V8Object v8Object2 = new V8Object(this.runtime);
        this.runtime.add("_api", v8Object2);
        v8Object2.registerJavaMethod(api, "_callSync", "_callSync", new Class[]{String.class, Object.class});
        v8Object2.registerJavaMethod(api, "_call", "_call", new Class[]{String.class, Object.class, String.class});
        v8Object2.registerJavaMethod(api, "_getData", "_getData", new Class[]{String.class});
        v8Object2.close();
    }

    public List<File> createWorkerBaseFile() {
        File file = new File(this.webViewFragment.getDefaultsPath());
        ArrayList arrayList = new ArrayList();
        if (file.exists()) {
            String absolutePath = file.getAbsolutePath();
            File file2 = new File(absolutePath + "/worker");
            if (file2.exists()) {
                for (File file3 : Arrays.asList(file2.listFiles())) {
                    if (file3.getName().contains("begin")) {
                        arrayList.add(file3);
                    } else if (file3.getName().contains("end")) {
                        arrayList.add(file3);
                    }
                }
            }
            File file4 = new File(absolutePath + "/api");
            if (file4.exists()) {
                arrayList.addAll(arrayList.size() - 1, Arrays.asList(file4.listFiles()));
            }
            File file5 = new File(absolutePath + "/android");
            if (file5.exists()) {
                arrayList.addAll(arrayList.size() - 1, Arrays.asList(file5.listFiles()));
            }
        }
        return arrayList;
    }

    @Override // com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void start(final File file) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.3
            @Override // java.lang.Runnable
            public void run() {
                File file2 = file;
                if (file2 != null) {
                    V8BaseRuntime.this.beforeExecute(file2);
                    V8BaseRuntime.this.executeFromPath(file.getAbsolutePath());
                    V8BaseRuntime.this.afterExecute(file);
                }
            }
        });
    }

    public String join(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != 0 && i != list.size() - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public String loadDataFromUrl(final String str) {
        Request request = new Request();
        request.setMethod("GET");
        try {
            request.setUrl(new URL(str));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final HashMap hashMap = new HashMap();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        request.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.4
            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onProgress(long j, long j2) {
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public boolean onReceiveResponse(Request.Response response) {
                return true;
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onFail(int i, String str2) {
                String className = V8BaseRuntime.this.getClassName();
                Log.e(className, "load data from url fail " + str + " message:" + str2);
                hashMap.put("result", "");
                countDownLatch.countDown();
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onComplete(int i, byte[] bArr) {
                hashMap.put("result", new String(bArr));
                countDownLatch.countDown();
            }
        });
        request.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return (String) hashMap.get("result");
    }

    public String loadDataFromPath(String str) {
        return readFileContent(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void executeFromPath(String str) {
        this.currentPathStack.push(str);
        try {
            this.handler.executeScript(readFileContent(str), str, 0);
        } catch (V8RuntimeException unused) {
        }
        this.currentPathStack.pop();
    }

    public String compileCss(String str, String str2, boolean z) {
        if (str.contains(Utils.getIoDefaultName())) {
            return Utils.processWithFilePath(z ? str2 : null, new String(Utils.loadData(str, Utils.ENCODE_TYPE.DEFAULT, this.webViewFragment.getWACrypto())), this.webViewFragment.getWACrypto().getDefaultCrypto(), this.webViewFragment.getWorkPath(), str);
        }
        return Utils.processWithFilePath(z ? str2 : null, new String(Utils.loadData(str, Utils.ENCODE_TYPE.WORK, this.webViewFragment.getWACrypto())), this.webViewFragment.getWACrypto().getWorkCrypto(), this.webViewFragment.getWorkPath(), str);
    }

    public String readFileContent(String str) {
        Utils.ENCODE_TYPE encode_type;
        if (str.contains(Utils.getIoDefaultName())) {
            encode_type = Utils.ENCODE_TYPE.DEFAULT;
        } else {
            encode_type = Utils.ENCODE_TYPE.WORK;
        }
        byte[] loadData = Utils.loadData(str, encode_type, this.webViewFragment.getWACrypto());
        return loadData != null ? new String(loadData) : "";
    }

    @Override // com.gen.p059mh.webapps.Plugin.Executor
    public void executeEvent(final String str, Object obj, JSResponseListener jSResponseListener) {
        final String encodeToString;
        final String str2 = "" + new Date().getTime() + Math.random();
        if (jSResponseListener != null) {
            this.responseListeners.put(str2, jSResponseListener);
        }
        if (obj == null) {
            encodeToString = Base64.encodeToString("null".getBytes(), 0);
        } else {
            encodeToString = Base64.encodeToString(new Gson().toJson(obj).getBytes(), 0);
        }
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    V8BaseRuntime.this.cacheData.put(str2, encodeToString);
                    if (V8BaseRuntime.this.runtime.isReleased()) {
                        return;
                    }
                    C1257V8 c1257v8 = V8BaseRuntime.this.runtime;
                    c1257v8.executeScript("native.notify2('" + str + "', '" + str2 + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void destroyWorker() {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.6
            @Override // java.lang.Runnable
            public void run() {
                V8BaseRuntime v8BaseRuntime = V8BaseRuntime.this;
                if (v8BaseRuntime.runtime != null) {
                    v8BaseRuntime.handler.destroy();
                    V8BaseRuntime.this.runtime.release(false);
                }
                Stack<String> stack = V8BaseRuntime.this.currentPathStack;
                if (stack != null) {
                    stack.clear();
                }
            }
        });
    }

    /* renamed from: com.gen.mh.webapps.server.runtime.V8BaseRuntime$Api */
    /* loaded from: classes2.dex */
    public class Api {
        public Api() {
        }

        public void _call(String str, Object obj, final String str2) {
            if (V8BaseRuntime.this.plugins.containsKey(str)) {
                try {
                    V8BaseRuntime.this.plugins.get(str).process(obj.toString(), new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.Api.1
                        @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                        public void response(Object obj2) {
                            final String json = new Gson().toJson(obj2);
                            V8BaseRuntime.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.Api.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    String encodeToString = Base64.encodeToString(json.getBytes(), 0);
                                    C18111 c18111 = C18111.this;
                                    V8BaseRuntime.this.cacheData.put(str2, encodeToString);
                                    try {
                                        if (V8BaseRuntime.this.runtime.isReleased()) {
                                            return;
                                        }
                                        C1257V8 c1257v8 = V8BaseRuntime.this.runtime;
                                        c1257v8.executeScript("api._callback('" + str2 + "')");
                                    } catch (RuntimeException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.i("ProcessError", "Error when process " + str);
                    e.printStackTrace();
                    V8BaseRuntime.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.Api.2
                        @Override // java.lang.Runnable
                        public void run() {
                            V8BaseRuntime.this.cacheData.put(str2, Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0));
                            try {
                                if (V8BaseRuntime.this.runtime.isReleased()) {
                                    return;
                                }
                                C1257V8 c1257v8 = V8BaseRuntime.this.runtime;
                                c1257v8.executeScript("api._callback('" + str2 + "')");
                            } catch (RuntimeException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                }
            } else {
                V8BaseRuntime.this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.Api.3
                    @Override // java.lang.Runnable
                    public void run() {
                        V8BaseRuntime.this.cacheData.put(str2, Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0));
                        try {
                            if (V8BaseRuntime.this.runtime.isReleased()) {
                                return;
                            }
                            C1257V8 c1257v8 = V8BaseRuntime.this.runtime;
                            c1257v8.executeScript("api._callback('" + str2 + "')");
                        } catch (RuntimeException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
            if (obj instanceof Releasable) {
                ((Releasable) obj).release();
            }
        }

        public String _callSync(String str, Object obj) {
            if (V8BaseRuntime.this.plugins.containsKey(str)) {
                Plugin plugin = V8BaseRuntime.this.plugins.get(str);
                try {
                    try {
                        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                        final StringBuilder sb = new StringBuilder();
                        plugin.process(obj.toString(), new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.server.runtime.V8BaseRuntime.Api.4
                            boolean called = false;

                            @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                            public void response(Object obj2) {
                                this.called = true;
                                sb.append(Base64.encodeToString(new Gson().toJson(obj2).getBytes(), 0));
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
                                String sb2 = sb.toString();
                                if (obj instanceof Releasable) {
                                    ((Releasable) obj).release();
                                }
                                return sb2;
                            }
                            atomicBoolean.wait();
                            String sb3 = sb.toString();
                            if (obj instanceof Releasable) {
                                ((Releasable) obj).release();
                            }
                            return sb3;
                        }
                    } catch (Exception e) {
                        Log.i("ProcessError", "Error when process " + str);
                        e.printStackTrace();
                        String encodeToString = Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0);
                        if (obj instanceof Releasable) {
                            ((Releasable) obj).release();
                        }
                        return encodeToString;
                    }
                } catch (Throwable th) {
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    throw th;
                }
            }
            return Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0);
        }

        public String _getData(String str) {
            String str2 = V8BaseRuntime.this.cacheData.get(str);
            V8BaseRuntime.this.cacheData.remove(str);
            return str2;
        }
    }
}
