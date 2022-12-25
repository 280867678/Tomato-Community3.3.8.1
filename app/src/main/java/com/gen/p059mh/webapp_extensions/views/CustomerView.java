package com.gen.p059mh.webapp_extensions.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.widget.RelativeLayout;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8RuntimeException;
import com.eclipsesource.p056v8.V8Value;
import com.eclipsesource.p056v8.utils.V8ObjectUtils;
import com.gen.p059mh.webapp_extensions.utils.Tool;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.server.runtime.V8BaseRuntime;
import com.gen.p059mh.webapps.utils.Logger;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/* renamed from: com.gen.mh.webapp_extensions.views.CustomerView */
/* loaded from: classes2.dex */
public class CustomerView extends NativeViewPlugin.NativeView {
    V8BaseRuntime v8BaseRuntime;
    String function = "function _createNativeView(object) {\n    object._callbacks = {};\n    object.emit = function(name) {\n        var args = [];\n        for (var  i = 1, t = arguments.length; i < t; ++i) {\n            args.push(arguments[i]);\n        }\n        var fn = this['on'+name];\n        var res = null;\n        if (typeof fn === 'function') {\n            res = fn.apply(this, args);\n        }\n        var cbs = this._callbacks[name];\n        if (cbs) {\n            console.log('on'+name);\n            for (var i = 0, t = cbs.length; i < t; ++i) {\n                try {\n                    var r = cbs[i].apply(this, args);\n                    if (!res) res = r;\n                }catch (e) {\n                    console.error(e);\n                }\n            }\n        }\n        return res;\n    };\n    object.on = function(name, fn) {\n        var cbs = this._callbacks[name];\n        if (!cbs) {\n            cbs = this._callbacks[name] = [];\n        }\n        cbs.push(fn);\n    };\n    object.off = function(name, fn) {\n        var cbs = this._callbacks[name];\n        if (cbs) {\n            var idx = cbs.indexOf(fn);\n            if (idx >= 0) {\n                cbs.splice(idx, 1);\n                if (cbs.length == 0) {\n                    delete this._callbacks[name];\n                }\n            }\n        }\n    }\n    return object;\n}\n";
    public Map<String, NativeViewPlugin.NativeView> viewMap = new HashMap();
    public Map<String, Object> methods = new HashMap();

    private String getDataFromPath(String str) {
        return "";
    }

    public CustomerView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        String dataFromPath;
        String dataFromPath2;
        super.onInitialize(obj);
        Map map = (Map) obj;
        String str = (String) map.get("path");
        if (str == null) {
            dataFromPath = (String) map.get("layout");
        } else {
            dataFromPath = getDataFromPath(str);
        }
        Logger.m4113i(dataFromPath);
        startReadXml(dataFromPath);
        String str2 = (String) map.get("src");
        if (str2 == null) {
            dataFromPath2 = (String) map.get("script");
        } else {
            dataFromPath2 = getDataFromPath(str2);
        }
        Logger.m4113i(dataFromPath2);
        doScript(dataFromPath2);
    }

    public void startReadXml(String str) {
        try {
            readChild(this, DocumentHelper.parseText(str).getRootElement());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void readChild(NativeViewPlugin.NativeView nativeView, Element element) {
        NativeViewPlugin.NativeView readValue = readValue(nativeView, element);
        if (element.hasContent()) {
            for (Element element2 : element.elements()) {
                readChild(readValue, element2);
            }
        }
    }

    private NativeViewPlugin.NativeView readValue(NativeViewPlugin.NativeView nativeView, Element element) {
        final NativeViewPlugin.NativeView nativeView2 = null;
        if (nativeView == null) {
            return null;
        }
        String replace = element.getName().replace("-", ".");
        List<Attribute> attributes = element.attributes();
        Class<NativeViewPlugin.NativeView> cls = getWebViewFragment().getNativeViewPlugin().getNativeClasses().get(replace);
        if (cls != null) {
            try {
                nativeView2 = getWebViewFragment().getNativeViewPlugin().doCreateView(cls, replace);
                nativeView2.setExecutor(new Plugin.Executor() { // from class: com.gen.mh.webapp_extensions.views.CustomerView.1
                    @Override // com.gen.p059mh.webapps.Plugin.Executor
                    public void executeEvent(String str, Object obj, JSResponseListener jSResponseListener) {
                        final Map map = (Map) obj;
                        CustomerView.this.v8BaseRuntime.getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.CustomerView.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                V8Array v8Array = new V8Array(CustomerView.this.v8BaseRuntime.getRuntime());
                                v8Array.push(map.get("type")).push((V8Value) V8ObjectUtils.toV8Object(CustomerView.this.v8BaseRuntime.getRuntime(), (Map) map.get("value")));
                                try {
                                    CustomerView.this.v8BaseRuntime.getRuntime().getObject(nativeView2.getHandlerID()).executeFunction("emit", v8Array);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            nativeView.addView(nativeView2);
        }
        if (nativeView2 != null) {
            HashMap hashMap = new HashMap();
            for (Attribute attribute : attributes) {
                String name = attribute.getName();
                char c = 65535;
                int hashCode = name.hashCode();
                if (hashCode != 3355) {
                    if (hashCode != 97692013) {
                        if (hashCode == 1587754642 && name.equals("auto-resize")) {
                            c = 2;
                        }
                    } else if (name.equals("frame")) {
                        c = 1;
                    }
                } else if (name.equals(DatabaseFieldConfigLoader.FIELD_NAME_ID)) {
                    c = 0;
                }
                if (c == 0) {
                    this.viewMap.put(attribute.getValue(), nativeView2);
                    nativeView2.setHandlerID(attribute.getValue());
                } else if (c == 1) {
                    doFrame(nativeView2, attribute.getValue());
                } else if (c == 2) {
                    doAutoResize(nativeView2, attribute.getValue());
                } else {
                    hashMap.put(Tool.lineToHump('-', attribute.getName()), attribute.getValue());
                }
            }
            nativeView2.setWebViewFragment(getWebViewFragment());
            nativeView2.onInitialize(hashMap);
        }
        return nativeView2;
    }

    private void doFrame(NativeViewPlugin.NativeView nativeView, String str) {
        doFrame(nativeView, str, false);
    }

    private void doFrame(final NativeViewPlugin.NativeView nativeView, String str, boolean z) {
        String[] split = str.split(",");
        float f = getWebViewFragment().getContext().getResources().getDisplayMetrics().density;
        if (split.length >= 4) {
            Rect rect = new Rect();
            rect.left = (int) (Integer.valueOf(split[0]).intValue() * f);
            rect.top = (int) (Integer.valueOf(split[1]).intValue() * f);
            final int intValue = (int) (Integer.valueOf(split[2]).intValue() * f);
            final int intValue2 = (int) (Integer.valueOf(split[3]).intValue() * f);
            if (split.length >= 5) {
                rect.right = (int) (Integer.valueOf(split[4]).intValue() * f);
            }
            if (split.length == 6) {
                rect.bottom = (int) (Integer.valueOf(split[5]).intValue() * f);
            }
            final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nativeView.getLayoutParams();
            if (z) {
                final int i = layoutParams.leftMargin;
                final int i2 = layoutParams.topMargin;
                final int i3 = layoutParams.rightMargin;
                final int i4 = layoutParams.bottomMargin;
                final int i5 = rect.left - i;
                final int i6 = rect.top - i2;
                final int i7 = rect.right - i3;
                final int i8 = rect.bottom - i4;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.gen.mh.webapp_extensions.views.CustomerView.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        layoutParams.setMargins(((int) (i5 * floatValue)) + i, ((int) (i6 * floatValue)) + i2, ((int) (i7 * floatValue)) + i3, ((int) (floatValue * i8)) + i4);
                        RelativeLayout.LayoutParams layoutParams2 = layoutParams;
                        int i9 = intValue;
                        if (i9 == 0) {
                            i9 = -1;
                        }
                        layoutParams2.width = i9;
                        RelativeLayout.LayoutParams layoutParams3 = layoutParams;
                        int i10 = intValue2;
                        if (i10 == 0) {
                            i10 = -1;
                        }
                        layoutParams3.height = i10;
                        CustomerView.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.CustomerView.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                C16322 c16322 = C16322.this;
                                nativeView.setLayoutParams(layoutParams);
                            }
                        });
                    }
                });
                ofFloat.setDuration(300L);
                ofFloat.start();
            } else {
                layoutParams.setMargins(rect.left, rect.top, rect.right, rect.bottom);
                int i9 = -1;
                if (intValue == 0) {
                    intValue = -1;
                }
                layoutParams.width = intValue;
                if (intValue2 != 0) {
                    i9 = intValue2;
                }
                layoutParams.height = i9;
                getWebViewFragment().getActivity().runOnUiThread(new Runnable(this) { // from class: com.gen.mh.webapp_extensions.views.CustomerView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        nativeView.setLayoutParams(layoutParams);
                    }
                });
            }
        }
    }

    private void doAutoResize(NativeViewPlugin.NativeView nativeView, String str) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nativeView.getLayoutParams();
        if (str.contains("left")) {
            layoutParams.addRule(9);
        }
        if (str.contains("right")) {
            layoutParams.addRule(11);
        }
        if (str.contains("top")) {
            layoutParams.addRule(10);
        }
        if (str.contains("bottom")) {
            layoutParams.addRule(12);
        }
        nativeView.setLayoutParams(layoutParams);
    }

    public void doScript(final String str) {
        this.v8BaseRuntime = new V8BaseRuntime(getWebViewFragment()) { // from class: com.gen.mh.webapp_extensions.views.CustomerView.4
            @Override // com.gen.p059mh.webapps.server.runtime.V8Lifecycle
            public void afterExecute(File file) {
            }

            @Override // com.gen.p059mh.webapps.server.runtime.V8Lifecycle
            public void beforeExecute(File file) {
            }

            @Override // com.gen.p059mh.webapps.server.runtime.V8Lifecycle
            public void onReady(C1257V8 c1257v8) {
                try {
                    c1257v8.executeScript(str);
                } catch (V8RuntimeException e) {
                    e.printStackTrace();
                }
            }

            @Override // com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
            public void onInit() {
                super.onInit();
                Object obj = new Object(this) { // from class: com.gen.mh.webapp_extensions.views.CustomerView.4.1
                };
                V8Object v8Object = new V8Object(this.runtime);
                this.runtime.add("document", v8Object);
                v8Object.registerJavaMethod(obj, "getElementById", "getElementById", new Class[]{String.class});
                this.runtime.executeObjectScript(CustomerView.this.function);
            }
        };
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
        Logger.m4113i("onDestroy");
        removeAllViews();
    }
}
