package com.gen.p059mh.webapp_extensions.unity;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import com.gen.p059mh.webapp_extensions.unity.Canvas;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
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
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.gen.mh.webapp_extensions.unity.Canvas */
/* loaded from: classes2.dex */
public class Canvas extends Unity {
    static float devicePixelRatio;
    static GCanvasModule module = new GCanvasModule();
    public IGBridgeModule.ContextType mType;
    GTextureView textureView;
    Unity.Method setContextType = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            List list = (List) objArr[0];
            if (list.size() < 1 || !(list.get(0) instanceof Number)) {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            Canvas.module.impl.setContextType(Canvas.this.getId(), ((Number) list.get(0)).intValue() == 0 ? IGBridgeModule.ContextType._2D : IGBridgeModule.ContextType._3D);
            methodCallback.run(null);
        }
    };
    Unity.Method render = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            List list = (List) objArr[0];
            if (list.size() < 1 || !(list.get(0) instanceof String)) {
                HashMap hashMap = new HashMap();
                hashMap.put("error", "Input Param Error");
                methodCallback.run(hashMap);
                return;
            }
            Canvas.module.impl.render(Canvas.this.getId(), (String) list.get(0));
            methodCallback.run(null);
        }
    };
    Unity.Method textImage2D = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            int intValue;
            int intValue2;
            int intValue3;
            int intValue4;
            int intValue5;
            String str;
            List list = (List) objArr[0];
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
            Canvas.module.impl.texImage2D(Canvas.this.getId(), intValue, intValue2, intValue3, intValue4, intValue5, str);
            methodCallback.run(null);
        }
    };
    Unity.Method textSubImage2D = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            List list = (List) objArr[0];
            if (list.size() == 7) {
                Canvas.module.impl.texSubImage2D(Canvas.this.getId(), ((Number) list.get(0)).intValue(), ((Number) list.get(1)).intValue(), ((Number) list.get(2)).intValue(), ((Number) list.get(3)).intValue(), ((Number) list.get(4)).intValue(), ((Number) list.get(5)).intValue(), (String) list.get(6));
                methodCallback.run(null);
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("error", "Input Param Error");
            methodCallback.run(hashMap);
        }
    };
    Unity.Method bindImageTexture = new C15355();
    Unity.Method extendCallNative = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.Canvas.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            methodCallback.run(null);
        }
    };
    Unity.Method snapshot = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.Canvas.7
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
        }
    };
    Unity.Method getImageData = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.Canvas.8
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
        }
    };
    Unity.Method putImageData = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.Canvas.9
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
        }
    };
    Unity.Method setHeight = new C152710();
    Unity.Method setWidth = new C152811();
    Unity.Method getHeight = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.12
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("getHeight", "getHeight" + Canvas.this.textureView.getHeight());
            methodCallback.run(Integer.valueOf(Canvas.this.textureView.getHeight()));
        }
    };
    Unity.Method getWidth = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.Canvas.13
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("getWidth", "getWidth" + Canvas.this.textureView.getWidth());
            methodCallback.run(Integer.valueOf(Canvas.this.textureView.getWidth()));
        }
    };
    Unity.Method measureText = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.Canvas.14
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$JSCallback */
    /* loaded from: classes2.dex */
    public interface JSCallback {
        void invoke(Object obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent() {
    }

    public Canvas() {
        registerMethod("setContextType", this.setContextType);
        registerMethod("render", this.render);
        registerMethod("textImage2D", this.textImage2D);
        registerMethod("textSubImage2D", this.textSubImage2D);
        registerMethod("bindImageTexture", this.bindImageTexture);
        registerMethod("extendCallNative", this.extendCallNative);
        registerMethod("snapshot", this.snapshot);
        registerMethod("getImageData", this.getImageData);
        registerMethod("putImageData", this.putImageData);
        registerMethod("setHeight", this.setHeight);
        registerMethod("getHeight", this.getHeight);
        registerMethod("setWidth", this.setWidth);
        registerMethod("getWidth", this.getWidth);
        registerMethod("measureText", this.measureText);
    }

    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$5 */
    /* loaded from: classes2.dex */
    class C15355 implements Unity.Method {
        C15355() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(final Unity.MethodCallback methodCallback, Object... objArr) {
            List list = (List) objArr[0];
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
            Canvas.module.impl.bindImageTexture(Canvas.this.getId(), (String) list2.get(0), obj instanceof String ? Integer.parseInt((String) obj) : obj instanceof Number ? ((Number) obj).intValue() : 0, new JSCallback() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$Canvas$5$0VWGs1aQVwgB-isim1A8Y5KzMyk
                @Override // com.gen.p059mh.webapp_extensions.unity.Canvas.JSCallback
                public final void invoke(Object obj2) {
                    Canvas.C15355.lambda$call$0(Unity.MethodCallback.this, obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$call$0(Unity.MethodCallback methodCallback, Object obj) {
            Logger.m4112i("bindImageTexture", obj.toString());
            methodCallback.run(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$10 */
    /* loaded from: classes2.dex */
    public class C152710 implements Unity.Method {
        C152710() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final int intValue = ((Number) ((List) objArr[0]).get(0)).intValue();
            Logger.m4112i("setHeight", Float.valueOf(intValue / Canvas.devicePixelRatio));
            Canvas canvas = Canvas.this;
            if (canvas.textureView != null) {
                canvas.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$Canvas$10$0R1U_WpU0XcvRcckXQPJ65_rieI
                    @Override // java.lang.Runnable
                    public final void run() {
                        Canvas.C152710.this.lambda$call$0$Canvas$10(intValue);
                    }
                });
            }
            methodCallback.run(null);
        }

        public /* synthetic */ void lambda$call$0$Canvas$10(int i) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) Canvas.this.textureView.getLayoutParams();
            layoutParams.height = i;
            Canvas.this.textureView.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$11 */
    /* loaded from: classes2.dex */
    public class C152811 implements Unity.Method {
        C152811() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final int intValue = ((Number) ((List) objArr[0]).get(0)).intValue();
            Logger.m4112i("setWidth", Float.valueOf(intValue / Canvas.devicePixelRatio));
            Canvas canvas = Canvas.this;
            if (canvas.textureView != null) {
                canvas.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$Canvas$11$ysh7G9LXnxJ3_4MBeQ9Ip_obGlQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        Canvas.C152811.this.lambda$call$0$Canvas$11(intValue);
                    }
                });
            }
            methodCallback.run(null);
        }

        public /* synthetic */ void lambda$call$0$Canvas$11(int i) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) Canvas.this.textureView.getLayoutParams();
            layoutParams.width = i;
            Canvas.this.textureView.setLayoutParams(layoutParams);
        }
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        if (getWebViewFragment().findPlugin("canvas.unity") == null) {
            getWebViewFragment().registerPlugin(module);
            devicePixelRatio = DeviceUtils.dpToPixel(getWebViewFragment().getContext(), 1.0f);
        }
        module.mComponentMap.put(getId(), this);
        this.textureView = new GTextureView(getWebViewFragment().getContext(), getId());
        this.textureView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        final JSONObject jSONObject = new JSONObject();
        if (obj != null && (obj instanceof Map)) {
            try {
                Map map = (Map) obj;
                for (String str : map.keySet()) {
                    try {
                        jSONObject.put(str, map.get(str));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClassCastException e2) {
                e2.printStackTrace();
            }
        }
        try {
            jSONObject.put("componentId", getId());
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$Canvas$JhgQaLksRyLYT1dcbFsH-BCK63M
            @Override // java.lang.Runnable
            public final void run() {
                Canvas.this.lambda$onInitialize$1$Canvas(jSONObject);
            }
        });
    }

    public /* synthetic */ void lambda$onInitialize$1$Canvas(JSONObject jSONObject) {
        getWebViewFragment().getWebParentContainer().removeView(getWebViewFragment().provideView());
        Logger.m4112i("childCount", Integer.valueOf(getWebViewFragment().getWebParentContainer().getChildCount()));
        if (getWebViewFragment().getWebParentContainer().getChildCount() == 0) {
            getWebViewFragment().getWebParentContainer().addView(this.textureView);
            this.textureView.setBackgroundColor("#ffc933");
        }
        getWebViewFragment().loadComplete(2);
        this.textureView.setVisibility(0);
        module.impl.enable(jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$GCanvasModule */
    /* loaded from: classes2.dex */
    public static class GCanvasModule extends Plugin {
        public Map<String, Canvas> mComponentMap = new HashMap();
        GCanvasModuleImpl impl = new GCanvasModuleImpl(this);

        @Override // com.gen.p059mh.webapps.Plugin
        public void process(String str, Plugin.PluginCallback pluginCallback) {
        }

        protected GCanvasModule() {
            super("canvas.unity");
            this.impl.setImageLoader(new GCanvasFrescoImageLoader());
        }

        public Context getContext() {
            return getWebViewFragment().getContext();
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.unity.Canvas$GCanvasModuleImpl */
    /* loaded from: classes2.dex */
    public static class GCanvasModuleImpl extends AbsGBridgeModule<JSCallback> {
        WAEJSCallbackDataFactory mFactory = new WAEJSCallbackDataFactory();
        WeakReference<GCanvasModule> mOutRef;

        public GCanvasModuleImpl(GCanvasModule gCanvasModule) {
            this.mOutRef = new WeakReference<>(gCanvasModule);
            GCanvasJNI.setFontFamilies();
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
            } catch (JSONException e) {
                e.printStackTrace();
                return Boolean.TRUE.toString();
            }
        }

        public void setContextType(String str, IGBridgeModule.ContextType contextType) {
            GCanvasModule gCanvasModule = this.mOutRef.get();
            if (gCanvasModule == null) {
                return;
            }
            int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
            String str2 = AbsGBridgeModule.TAG;
            Logger.m4116d(str2, "enable width " + width);
            String str3 = AbsGBridgeModule.TAG;
            Logger.m4116d(str3, "enable devicePixelRatio " + Canvas.devicePixelRatio);
            GCanvasJNI.setWrapperHiQuality(str, true);
            GCanvasJNI.setWrapperDevicePixelRatio(str, (double) Canvas.devicePixelRatio);
            GCanvasJNI.setWrapperContextType(str, contextType.value());
            if (GCanvasJNI.sendEvent(str)) {
                GLog.m3565d("start to send event in module.");
                Canvas canvas = gCanvasModule.mComponentMap.get(str);
                if (canvas != null) {
                    canvas.sendEvent();
                }
            }
            Canvas canvas2 = gCanvasModule.mComponentMap.get(str);
            if (canvas2 == null) {
                return;
            }
            canvas2.mType = contextType;
        }

        public void render(String str, String str2) {
            Canvas canvas = Canvas.module.mComponentMap.get(str);
            if (canvas != null) {
                GCanvasJNI.render(canvas.getTextureView().getCanvasKey(), str2);
                return;
            }
            String str3 = AbsGBridgeModule.TAG;
            Logger.m4108w(str3, "can not find canvas with id ===> " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public GTextureView getTextureView() {
        return this.textureView;
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void unload() {
        super.unload();
        module.mComponentMap.remove(getId());
    }
}
