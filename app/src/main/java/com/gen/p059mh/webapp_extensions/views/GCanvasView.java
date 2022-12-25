package com.gen.p059mh.webapp_extensions.views;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.NativeMethod;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.taobao.gcanvas.GCanvasJNI;
import com.taobao.gcanvas.adapters.img.impl.fresco.GCanvasFrescoImageLoader;
import com.taobao.gcanvas.bridges.p102rn.bridge.WAEJSCallbackArray;
import com.taobao.gcanvas.bridges.p102rn.bridge.WAEJSCallbackDataFactory;
import com.taobao.gcanvas.bridges.p102rn.bridge.WAEJSCallbackMap;
import com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackDataFactory;
import com.taobao.gcanvas.bridges.spec.module.AbsGBridgeModule;
import com.taobao.gcanvas.bridges.spec.module.IGBridgeModule;
import com.taobao.gcanvas.surface.GTextureView;
import com.taobao.gcanvas.util.GLog;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.gen.mh.webapp_extensions.views.GCanvasView */
/* loaded from: classes2.dex */
public class GCanvasView extends NativeViewPlugin.NativeView {
    static GCanvasModule module = new GCanvasModule();
    IGBridgeModule.ContextType mType;
    GTextureView textureView;
    NativeViewPlugin.NativeView.MethodHandler setContextType = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.2
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() < 1 || !(list.get(0) instanceof Number)) {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            GCanvasView.module.impl.setContextType(GCanvasView.this.getHandlerID(), ((Number) list.get(0)).intValue() == 0 ? IGBridgeModule.ContextType._2D : IGBridgeModule.ContextType._3D);
            methodCallback.run(null);
        }
    };
    NativeViewPlugin.NativeView.MethodHandler render = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.3
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() < 1 || !(list.get(0) instanceof String)) {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            GCanvasView.module.impl.render(GCanvasView.this.getHandlerID(), (String) list.get(0));
            methodCallback.run(null);
        }
    };
    NativeViewPlugin.NativeView.MethodHandler bindImageTexture = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.4
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, final NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() < 1 || !(list.get(0) instanceof List)) {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            List list2 = (List) list.get(0);
            if (list2.size() != 2 || !(list2.get(0) instanceof String)) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("error", "Input Param Error");
                methodCallback.run(hashMap2);
                return;
            }
            Object obj = list2.get(1);
            GCanvasView.module.impl.bindImageTexture(GCanvasView.this.getHandlerID(), (String) list2.get(0), obj instanceof String ? Integer.parseInt((String) obj) : obj instanceof Number ? ((Number) obj).intValue() : 0, new JSCallback(this) { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.4.1
                @Override // com.gen.p059mh.webapp_extensions.views.GCanvasView.JSCallback
                public void invoke(Object obj2) {
                    methodCallback.run(obj2);
                }
            });
        }
    };
    NativeViewPlugin.NativeView.MethodHandler texImage2D = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.5
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            int intValue;
            int intValue2;
            int intValue3;
            int intValue4;
            int intValue5;
            String str;
            if (list.size() == 6) {
                intValue = ((Number) list.get(0)).intValue();
                intValue2 = ((Number) list.get(1)).intValue();
                intValue3 = ((Number) list.get(2)).intValue();
                intValue4 = ((Number) list.get(3)).intValue();
                intValue5 = ((Number) list.get(4)).intValue();
                str = (String) list.get(5);
            } else if (list.size() == 9) {
                intValue = ((Number) list.get(0)).intValue();
                intValue2 = ((Number) list.get(1)).intValue();
                intValue3 = ((Number) list.get(2)).intValue();
                intValue4 = ((Number) list.get(6)).intValue();
                intValue5 = ((Number) list.get(7)).intValue();
                str = (String) list.get(8);
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            GCanvasView.module.impl.texImage2D(GCanvasView.this.getHandlerID(), intValue, intValue2, intValue3, intValue4, intValue5, str);
            methodCallback.run(null);
        }
    };
    NativeViewPlugin.NativeView.MethodHandler texSubImage2D = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.6
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() == 7) {
                GCanvasView.module.impl.texSubImage2D(GCanvasView.this.getHandlerID(), ((Number) list.get(0)).intValue(), ((Number) list.get(1)).intValue(), ((Number) list.get(2)).intValue(), ((Number) list.get(3)).intValue(), ((Number) list.get(4)).intValue(), ((Number) list.get(5)).intValue(), (String) list.get(6));
                methodCallback.run(null);
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("error", "Input Param Error");
            methodCallback.run(hashMap);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.views.GCanvasView$JSCallback */
    /* loaded from: classes2.dex */
    public interface JSCallback {
        void invoke(Object obj);
    }

    public void sendEvent() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.views.GCanvasView$GCanvasModule */
    /* loaded from: classes2.dex */
    public static class GCanvasModule extends Plugin {
        public Map<String, GCanvasView> mComponentMap = new HashMap();
        GCanvasModuleImpl impl = new GCanvasModuleImpl(this);

        @Override // com.gen.p059mh.webapps.Plugin
        public void process(String str, Plugin.PluginCallback pluginCallback) {
        }

        protected GCanvasModule() {
            super("canvas.module");
            this.impl.setImageLoader(new GCanvasFrescoImageLoader());
        }

        public Context getContext() {
            return getWebViewFragment().getContext();
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.GCanvasView$GCanvasModuleImpl */
    /* loaded from: classes2.dex */
    private static class GCanvasModuleImpl extends AbsGBridgeModule<JSCallback> {
        WAEJSCallbackDataFactory mFactory = new WAEJSCallbackDataFactory();
        WeakReference<GCanvasModule> mOutRef;

        public GCanvasModuleImpl(GCanvasModule gCanvasModule) {
            this.mOutRef = new WeakReference<>(gCanvasModule);
            GCanvasJNI.setFontFamilies();
        }

        public String enable(JSONObject jSONObject) {
            GCanvasModule gCanvasModule = this.mOutRef.get();
            if (gCanvasModule == null) {
                return Boolean.FALSE.toString();
            }
            try {
                if (gCanvasModule.mComponentMap.containsKey(jSONObject.getString("componentId"))) {
                    return Boolean.TRUE.toString();
                }
                return Boolean.FALSE.toString();
            } catch (JSONException unused) {
                return Boolean.FALSE.toString();
            }
        }

        public void setContextType(String str, IGBridgeModule.ContextType contextType) {
            GCanvasModule gCanvasModule = this.mOutRef.get();
            if (gCanvasModule == null) {
                return;
            }
            int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
            double d = width / 750.0d;
            String str2 = AbsGBridgeModule.TAG;
            GLog.m3564d(str2, "enable width " + width);
            String str3 = AbsGBridgeModule.TAG;
            GLog.m3564d(str3, "enable devicePixelRatio " + d);
            GCanvasJNI.setWrapperHiQuality(str, true);
            GCanvasJNI.setWrapperDevicePixelRatio(str, d);
            GCanvasJNI.setWrapperContextType(str, contextType.value());
            if (GCanvasJNI.sendEvent(str)) {
                GLog.m3565d("start to send event in module.");
                GCanvasView gCanvasView = gCanvasModule.mComponentMap.get(str);
                if (gCanvasView != null) {
                    gCanvasView.sendEvent();
                }
            }
            GCanvasView gCanvasView2 = gCanvasModule.mComponentMap.get(str);
            if (gCanvasView2 == null) {
                return;
            }
            gCanvasView2.mType = contextType;
        }

        public void render(String str, String str2) {
            GCanvasView gCanvasView = GCanvasView.module.mComponentMap.get(str);
            if (gCanvasView == null) {
                String str3 = AbsGBridgeModule.TAG;
                GLog.m3563e(str3, "can not find canvas with id ===> " + str);
                return;
            }
            GCanvasJNI.render(gCanvasView.getTextureView().getCanvasKey(), str2);
        }

        @Override // com.taobao.gcanvas.bridges.spec.module.AbsGBridgeModule
        public Context getContext() {
            GCanvasModule gCanvasModule = this.mOutRef.get();
            if (gCanvasModule == null) {
                return null;
            }
            return gCanvasModule.getContext();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.taobao.gcanvas.bridges.spec.module.AbsGBridgeModule
        public void invokeCallback(JSCallback jSCallback, Object obj) {
            if (jSCallback != null) {
                if (obj instanceof WAEJSCallbackMap) {
                    jSCallback.invoke(((WAEJSCallbackMap) obj).getMap());
                } else if (!(obj instanceof WAEJSCallbackArray)) {
                } else {
                    jSCallback.invoke(((WAEJSCallbackArray) obj).getArray());
                }
            }
        }

        @Override // com.taobao.gcanvas.bridges.spec.module.AbsGBridgeModule
        protected IJSCallbackDataFactory getDataFactory() {
            return this.mFactory;
        }
    }

    @NativeMethod("preloadImage")
    public static void preloadImage(List list, final NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        if (list.size() != 1 || !(list.get(0) instanceof List)) {
            HashMap hashMap = new HashMap();
            hashMap.put("error", "Input Param Error");
            methodCallback.run(hashMap);
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (Object obj : (List) list.get(0)) {
            jSONArray.put(obj);
        }
        module.impl.preLoadImage(jSONArray, new JSCallback() { // from class: com.gen.mh.webapp_extensions.views.GCanvasView.1
            @Override // com.gen.p059mh.webapp_extensions.views.GCanvasView.JSCallback
            public void invoke(Object obj2) {
                NativeViewPlugin.NativeView.MethodCallback.this.run(obj2);
            }
        });
    }

    public GTextureView getTextureView() {
        return this.textureView;
    }

    public GCanvasView(Context context) {
        super(context);
        registerMethod("setContextType", this.setContextType);
        registerMethod("render", this.render);
        registerMethod("bindImageTexture", this.bindImageTexture);
        registerMethod("texImage2D", this.texImage2D);
        registerMethod("texSubImage2D", this.texSubImage2D);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        if (getWebViewFragment().findPlugin("canvas.module") == null) {
            getWebViewFragment().registerPlugin(module);
        }
        module.mComponentMap.put(getHandlerID(), this);
        this.textureView = new GTextureView(getContext(), getHandlerID());
        this.textureView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        addView(this.textureView);
        JSONObject jSONObject = new JSONObject();
        if (obj != null) {
            Map map = (Map) obj;
            for (String str : map.keySet()) {
                try {
                    jSONObject.put(str, map.get(str));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        module.impl.enable(jSONObject);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
        module.mComponentMap.remove(getHandlerID());
    }
}
