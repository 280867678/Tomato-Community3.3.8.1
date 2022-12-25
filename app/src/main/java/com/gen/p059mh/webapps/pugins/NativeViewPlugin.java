package com.gen.p059mh.webapps.pugins;

import android.content.Context;
import android.graphics.Rect;
import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.listener.NativeMethod;
import com.gen.p059mh.webapps.listener.ViewInitCallBack;
import com.gen.p059mh.webapps.listener.WebappLifecycleObserver;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* renamed from: com.gen.mh.webapps.pugins.NativeViewPlugin */
/* loaded from: classes2.dex */
public class NativeViewPlugin extends Plugin {
    public static int backViewId = 15000;
    public static int viewId = 1000;
    int screenHeight;
    int screenWidth;
    Map<String, Class<NativeView>> nativeClasses = new HashMap();
    Map<String, Map<String, Method>> staticMethods = new HashMap();
    Map<String, Map<String, Object>> instanceMethods = new HashMap();

    public Map<String, Class<NativeView>> getNativeClasses() {
        return this.nativeClasses;
    }

    public Map<String, Map<String, Method>> getStaticMethods() {
        return this.staticMethods;
    }

    public NativeViewPlugin() {
        super("native_view");
    }

    public <T extends NativeView> void registerNativeView(Class<T> cls, String str) {
        Method[] methods;
        Map<String, Object> hashMap;
        Map<String, Method> hashMap2;
        this.nativeClasses.put(str, cls);
        for (Method method : cls.getMethods()) {
            NativeMethod nativeMethod = (NativeMethod) method.getAnnotation(NativeMethod.class);
            if (nativeMethod != null) {
                if (Modifier.isStatic(method.getModifiers())) {
                    if (this.staticMethods.containsKey(str)) {
                        hashMap2 = this.staticMethods.get(str);
                    } else {
                        hashMap2 = new HashMap<>();
                        this.staticMethods.put(str, hashMap2);
                    }
                    hashMap2.put(nativeMethod.value(), method);
                } else {
                    if (this.instanceMethods.containsKey(str)) {
                        hashMap = this.instanceMethods.get(str);
                    } else {
                        hashMap = new HashMap<>();
                        this.instanceMethods.put(str, hashMap);
                    }
                    hashMap.put(nativeMethod.value(), method);
                }
            }
        }
        try {
            getAllNativeMethods(cls, str);
            Logger.m4112i("instance:" + str, this.instanceMethods.get(str).keySet().toString());
        } catch (NoClassDefFoundError unused) {
            Log.w("registerNative", "请加入播放器版本sdk");
        }
    }

    public void getAllNativeMethods(Class<?> cls, String str) {
        Field[] declaredFields;
        Map<String, Object> hashMap;
        if (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                if (NativeView.MethodHandler.class.isAssignableFrom(field.getType())) {
                    if (this.instanceMethods.containsKey(str)) {
                        hashMap = this.instanceMethods.get(str);
                    } else {
                        hashMap = new HashMap<>();
                        this.instanceMethods.put(str, hashMap);
                    }
                    hashMap.put(field.getName(), field);
                }
            }
            getAllNativeMethods(cls.getSuperclass(), str);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        char c;
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
        switch (str2.hashCode()) {
            case -1352294148:
                if (str2.equals("create")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1183693704:
                if (str2.equals("invoke")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -838846263:
                if (str2.equals("update")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 955534258:
                if (str2.equals("methods")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1557372922:
                if (str2.equals("destroy")) {
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
            createView(map, pluginCallback);
        } else if (c == 1) {
            methods(map, pluginCallback);
        } else if (c == 2) {
            update(map, pluginCallback);
        } else if (c == 3) {
            destroyAction(map, pluginCallback);
        } else if (c == 4) {
            invokeAction(map, pluginCallback);
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "Unsupport action " + str2);
            pluginCallback.response(hashMap);
        }
    }

    public void update(Map map, Plugin.PluginCallback pluginCallback) {
        int intValue = ((Number) map.get("view_id")).intValue();
        HashMap hashMap = new HashMap();
        if (intValue != 0) {
            updateFrame(findViewById(intValue), (Rect) map.get("rect"), ((Boolean) map.get("animated")).booleanValue());
            hashMap.put("success", true);
        } else {
            hashMap.put("success", false);
        }
        pluginCallback.response(hashMap);
    }

    public void invokeAction(Map map, final Plugin.PluginCallback pluginCallback) {
        int intValue = map.containsKey("view_id") ? ((Number) map.get("view_id")).intValue() : 0;
        String str = map.containsKey("class") ? (String) map.get("class") : null;
        if (intValue != 0) {
            NativeView findViewById = findViewById(intValue);
            String str2 = (String) map.get("method");
            List list = (List) map.get("params");
            if (str2 != null) {
                if (findViewById != null && findViewById.containHandler(str2)) {
                    findViewById.actionInvoke(str2, list, new NativeView.MethodCallback() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.1
                        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodCallback
                        public void run(Object obj) {
                            pluginCallback.response(obj);
                        }
                    });
                    return;
                }
                if (findViewById != null && findViewById.getType() != null && this.instanceMethods.get(findViewById.getType()) != null && this.instanceMethods.get(findViewById.getType()).containsKey(str2)) {
                    try {
                        Object obj = this.instanceMethods.get(findViewById.getType()).get(str2);
                        if (obj instanceof Method) {
                            ((Method) obj).invoke(findViewById, list, new NativeView.MethodCallback() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.2
                                @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodCallback
                                public void run(Object obj2) {
                                    pluginCallback.response(obj2);
                                }
                            });
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        HashMap hashMap = new HashMap();
                        hashMap.put("success", false);
                        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "View not found!");
                        pluginCallback.response(hashMap);
                    }
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", false);
                hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "no view found");
                pluginCallback.response(hashMap2);
                return;
            }
            HashMap hashMap3 = new HashMap();
            hashMap3.put("success", false);
            hashMap3.put(NotificationCompat.CATEGORY_MESSAGE, "No method found!");
            pluginCallback.response(hashMap3);
        } else if (str != null && this.staticMethods.containsKey(str)) {
            String str3 = (String) map.get("method");
            List list2 = (List) map.get("params");
            Map<String, Method> map2 = this.staticMethods.get(str);
            if (map2.containsKey(str3)) {
                try {
                    map2.get(str3).invoke(null, list2, new NativeView.MethodCallback() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.3
                        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodCallback
                        public void run(Object obj2) {
                            pluginCallback.response(obj2);
                        }
                    });
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    HashMap hashMap4 = new HashMap();
                    hashMap4.put("success", false);
                    hashMap4.put(NotificationCompat.CATEGORY_MESSAGE, "View not found!");
                    pluginCallback.response(hashMap4);
                    return;
                }
            }
            HashMap hashMap5 = new HashMap();
            hashMap5.put("success", false);
            hashMap5.put(NotificationCompat.CATEGORY_MESSAGE, "View not found!");
            pluginCallback.response(hashMap5);
        } else {
            HashMap hashMap6 = new HashMap();
            hashMap6.put("success", false);
            hashMap6.put(NotificationCompat.CATEGORY_MESSAGE, "View not found!");
            pluginCallback.response(hashMap6);
        }
    }

    public void createView(Map map, Plugin.PluginCallback pluginCallback) {
        String str = (String) map.get("type");
        if (str != null) {
            Class<NativeView> cls = this.nativeClasses.get(str);
            if (cls != null) {
                getWebViewFragment().getActivity().runOnUiThread(new RunnableC17974(cls, str, map, pluginCallback));
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "No type (" + str + ") found");
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "No type");
        pluginCallback.response(hashMap2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.pugins.NativeViewPlugin$4 */
    /* loaded from: classes2.dex */
    public class RunnableC17974 implements Runnable {
        final /* synthetic */ Plugin.PluginCallback val$callback;
        final /* synthetic */ Map val$inputMap;
        final /* synthetic */ String val$type;
        final /* synthetic */ Class val$viewClass;

        RunnableC17974(Class cls, String str, Map map, Plugin.PluginCallback pluginCallback) {
            this.val$viewClass = cls;
            this.val$type = str;
            this.val$inputMap = map;
            this.val$callback = pluginCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                NativeView doCreateView = NativeViewPlugin.this.doCreateView(this.val$viewClass, this.val$type);
                NativeViewPlugin.this.setFrame(doCreateView, (Map) this.val$inputMap.get("rect"));
                doCreateView.setHandlerID((String) this.val$inputMap.get("handler"));
                doCreateView.setWebViewFragment(NativeViewPlugin.this.getWebViewFragment());
                doCreateView.setExecutor(NativeViewPlugin.this.getExecutor());
                doCreateView.setTag(this.val$type);
                doCreateView.onInitialize(this.val$inputMap.get("init"));
                NativeViewPlugin.this.addViewAndId(doCreateView);
                final HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                hashMap.put("view_id", Integer.valueOf(doCreateView.getId()));
                hashMap.put("methods", doCreateView.getMethods());
                if (doCreateView.isInitNeedWait()) {
                    final Plugin.PluginCallback pluginCallback = this.val$callback;
                    doCreateView.setViewInitCallBack(new ViewInitCallBack() { // from class: com.gen.mh.webapps.pugins.-$$Lambda$NativeViewPlugin$4$bBrgi20MCfFIjWGPrm6ehSKPjzo
                        @Override // com.gen.p059mh.webapps.listener.ViewInitCallBack
                        public final void onInitSuccess() {
                            Plugin.PluginCallback.this.response(hashMap);
                        }
                    });
                } else {
                    this.val$callback.response(hashMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", false);
                hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "Error when create NativeView");
                this.val$callback.response(hashMap2);
            }
        }
    }

    public NativeView doCreateView(Class<NativeView> cls, String str) throws Exception {
        NativeView newInstance = cls.getConstructor(Context.class).newInstance(getWebViewFragment().getContext());
        newInstance.setType(str);
        newInstance.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return newInstance;
    }

    public void methods(Map map, Plugin.PluginCallback pluginCallback) {
        String obj = map.get("type").toString();
        if (obj != null) {
            Class<NativeView> cls = this.nativeClasses.get(obj);
            HashMap hashMap = new HashMap();
            if (cls != null) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("s", this.staticMethods.get(obj) == null ? new String[0] : this.staticMethods.get(obj).keySet());
                hashMap2.put("i", this.instanceMethods.get(obj) == null ? new String[0] : this.instanceMethods.get(obj).keySet());
                hashMap.put("success", true);
                hashMap.put("methods", hashMap2);
            } else {
                hashMap.put("success", false);
            }
            pluginCallback.response(hashMap);
        }
    }

    public void destroyAction(Map map, final Plugin.PluginCallback pluginCallback) {
        final int intValue = ((Number) map.get("view_id")).intValue();
        if (intValue != 0) {
            getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.5
                @Override // java.lang.Runnable
                public void run() {
                    NativeView findViewById = NativeViewPlugin.this.findViewById(intValue);
                    if (findViewById != null) {
                        findViewById.onDestroy();
                        NativeViewPlugin.this.removeView(findViewById);
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", true);
                    pluginCallback.response(hashMap);
                }
            });
        }
    }

    public NativeView findViewById(int i) {
        if (i < 15000) {
            return (NativeView) getWebViewFragment().getNativeLayer().findViewById(i);
        }
        return (NativeView) getWebViewFragment().getBackgroundNativeLayer().findViewById(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addViewAndId(NativeView nativeView) {
        if (nativeView.isBackGround()) {
            getWebViewFragment().getBackgroundNativeLayer().addView(nativeView);
            int i = backViewId;
            backViewId = i + 1;
            nativeView.setId(i);
            return;
        }
        getWebViewFragment().getNativeLayer().addView(nativeView);
        int i2 = viewId;
        viewId = i2 + 1;
        nativeView.setId(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeView(NativeView nativeView) {
        nativeView.setVisibility(8);
        if (nativeView.isBackGround) {
            getWebViewFragment().getBackgroundNativeLayer().removeView(nativeView);
        } else {
            getWebViewFragment().getNativeLayer().removeView(nativeView);
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        for (int i = 0; i < getWebViewFragment().getNativeLayer().getChildCount(); i++) {
            View childAt = getWebViewFragment().getNativeLayer().getChildAt(i);
            if (childAt instanceof NativeView) {
                ((NativeView) childAt).onDestroy();
            }
        }
        for (int i2 = 0; i2 < getWebViewFragment().getBackgroundNativeLayer().getChildCount(); i2++) {
            View childAt2 = getWebViewFragment().getBackgroundNativeLayer().getChildAt(i2);
            if (childAt2 instanceof NativeView) {
                ((NativeView) childAt2).onDestroy();
            }
        }
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.6
            @Override // java.lang.Runnable
            public void run() {
                NativeViewPlugin.this.getWebViewFragment().getNativeLayer().removeAllViews();
                NativeViewPlugin.this.getWebViewFragment().getBackgroundNativeLayer().removeAllViews();
            }
        });
    }

    void setFrame(NativeView nativeView, Map map) {
        if (map == null) {
            return;
        }
        Logger.m4112i("setFrame", map.toString());
        float f = getWebViewFragment().getContext().getResources().getDisplayMetrics().density;
        float f2 = 0.0f;
        float floatValue = map.containsKey("x") ? ((Number) map.get("x")).floatValue() * f : 0.0f;
        if (map.containsKey("y")) {
            f2 = ((Number) map.get("y")).floatValue() * f;
        }
        if (this.screenHeight == 0) {
            this.screenHeight = Utils.getDisplayMetrics(nativeView.getContext()).heightPixels;
            this.screenWidth = Utils.getDisplayMetrics(nativeView.getContext()).widthPixels;
        }
        float floatValue2 = ((Number) map.get("width")).floatValue() * f;
        float floatValue3 = ((Number) map.get("height")).floatValue() * f;
        boolean booleanValue = map.get("fixed") == null ? false : ((Boolean) map.get("fixed")).booleanValue();
        Logger.m4112i("ssss", floatValue + "_" + f2 + "_" + floatValue2 + "_" + floatValue3);
        nativeView.setFixed(booleanValue);
        int i = -1;
        int i2 = floatValue2 > ((float) this.screenWidth) ? -1 : (int) floatValue2;
        if (floatValue3 <= this.screenHeight) {
            i = (int) floatValue3;
        }
        nativeView.setLayoutParams(new FrameLayout.LayoutParams(i2, i));
        nativeView.setXY((int) floatValue, (int) f2);
    }

    private void updateFrame(NativeView nativeView, Rect rect, boolean z) {
        nativeView.postInvalidate(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: com.gen.mh.webapps.pugins.NativeViewPlugin$NativeView */
    /* loaded from: classes2.dex */
    public static class NativeView extends RelativeLayout implements WebappLifecycleObserver {
        Plugin.Executor executor;
        String handlerID;
        public String type;
        ViewInitCallBack viewInitCallBack;
        IWebFragmentController webViewFragment;
        Map<String, MethodHandler> methodHandlers = new HashMap();
        private boolean isBackGround = false;
        private float nativeZ = 0.0f;
        private boolean fixed = false;
        private boolean initNeedWait = false;
        int firstY = 0;
        int firstX = 0;

        /* renamed from: com.gen.mh.webapps.pugins.NativeViewPlugin$NativeView$MethodCallback */
        /* loaded from: classes2.dex */
        public interface MethodCallback {
            void run(Object obj);
        }

        /* renamed from: com.gen.mh.webapps.pugins.NativeViewPlugin$NativeView$MethodHandler */
        /* loaded from: classes2.dex */
        public interface MethodHandler {
            void run(List list, MethodCallback methodCallback);
        }

        @Override // com.gen.p059mh.webapps.listener.WebappLifecycleObserver
        public void onWebCreate() {
        }

        @Override // com.gen.p059mh.webapps.listener.WebappLifecycleObserver
        public void onWebDestroy() {
        }

        public void onWebPause() {
        }

        public void onWebResume() {
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public boolean isInitNeedWait() {
            return this.initNeedWait;
        }

        public void setInitNeedWait(boolean z) {
            this.initNeedWait = z;
        }

        public ViewInitCallBack getViewInitCallBack() {
            return this.viewInitCallBack;
        }

        public void setViewInitCallBack(ViewInitCallBack viewInitCallBack) {
            this.viewInitCallBack = viewInitCallBack;
        }

        public boolean isBackGround() {
            return this.isBackGround;
        }

        public float getNativeZ() {
            return this.nativeZ;
        }

        public IWebFragmentController getWebViewFragment() {
            return this.webViewFragment;
        }

        @NativeMethod("setZ")
        public void setZ(List list, MethodCallback methodCallback) {
            this.nativeZ = ((Number) list.get(0)).intValue();
            if (list.size() > 0 && list.get(0) != null) {
                post(new Runnable() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.NativeView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewGroup viewGroup = (ViewGroup) NativeView.this.getParent();
                        int childCount = viewGroup.getChildCount();
                        TreeMap treeMap = new TreeMap();
                        if (childCount > 1) {
                            for (int i = 0; i < childCount; i++) {
                                NativeView nativeView = (NativeView) viewGroup.getChildAt(i);
                                if (treeMap.containsKey(Float.valueOf(nativeView.getNativeZ()))) {
                                    ((List) treeMap.get(Float.valueOf(nativeView.getNativeZ()))).add(nativeView);
                                } else {
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(nativeView);
                                    treeMap.put(Float.valueOf(nativeView.getNativeZ()), arrayList);
                                }
                            }
                            Set<Float> keySet = treeMap.keySet();
                            ArrayList<NativeView> arrayList2 = new ArrayList();
                            for (Float f : keySet) {
                                arrayList2.addAll((Collection) treeMap.get(f));
                            }
                            viewGroup.removeAllViews();
                            for (NativeView nativeView2 : arrayList2) {
                                viewGroup.addView(nativeView2);
                            }
                        }
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }

        @NativeMethod("hide")
        public void hide(final List list, MethodCallback methodCallback) {
            if (list.size() > 0 && list.get(0) != null) {
                post(new Runnable() { // from class: com.gen.mh.webapps.pugins.NativeViewPlugin.NativeView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        NativeView nativeView = NativeView.this;
                        int i = 0;
                        if (((Boolean) list.get(0)).booleanValue()) {
                            i = 8;
                        }
                        nativeView.setVisibility(i);
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }

        public NativeView(Context context) {
            super(context);
        }

        public Object getMethods() {
            return this.methodHandlers.keySet().toArray();
        }

        @Override // android.view.View
        public void setX(float f) {
            super.setX(f);
            if (this.firstX == 0) {
                this.firstX = (int) f;
            }
        }

        @Override // android.view.View
        public void setY(float f) {
            super.setY(f);
            if (this.firstY == 0) {
                this.firstY = (int) f;
            }
        }

        public void setFixed(boolean z) {
            this.fixed = z;
        }

        public void setXY(int i, int i2) {
            setX(i);
            setY(i2);
        }

        @Override // com.gen.p059mh.webapps.listener.WebappLifecycleObserver
        public void onWebScrollerChange(int i, int i2) {
            if (!this.fixed) {
                if (i2 > 0) {
                    setY(this.firstY - i2);
                }
                if (i <= 0) {
                    return;
                }
                setX(this.firstX - i2);
            }
        }

        public void setExecutor(Plugin.Executor executor) {
            this.executor = executor;
        }

        public String getHandlerID() {
            return this.handlerID;
        }

        public void registerMethod(String str, MethodHandler methodHandler) {
            if (methodHandler != null) {
                this.methodHandlers.put(str, methodHandler);
            }
        }

        public void onInitialize(Object obj) {
            if (obj != null) {
                Map map = (Map) obj;
                this.isBackGround = map.get("is_background") != null ? ((Boolean) map.get("is_background")).booleanValue() : false;
            }
            getWebViewFragment().getWebappLifecycleSubject().add(this);
        }

        public void onDestroy() {
            getWebViewFragment().getWebappLifecycleSubject().remove(this);
        }

        public void sendEvent(Object obj, JSResponseListener jSResponseListener) {
            String str = this.handlerID;
            if (str == null || this.webViewFragment == null) {
                return;
            }
            this.executor.executeEvent(str, obj, jSResponseListener);
        }

        public void setHandlerID(String str) {
            this.handlerID = str;
        }

        public void actionInvoke(String str, List list, MethodCallback methodCallback) {
            if (this.methodHandlers.containsKey(str)) {
                this.methodHandlers.get(str).run(list, methodCallback);
            }
        }

        public boolean containHandler(String str) {
            return this.methodHandlers.containsKey(str);
        }

        public void setWebViewFragment(IWebFragmentController iWebFragmentController) {
            this.webViewFragment = iWebFragmentController;
        }
    }
}
