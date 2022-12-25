package com.p076mh.webappStart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.mh.webappStart.MyNativeView */
/* loaded from: classes3.dex */
public class MyNativeView extends NativeViewPlugin.NativeView {
    private NativeViewPlugin.NativeView.MethodHandler test = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.mh.webappStart.MyNativeView.1
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            Toast.makeText(MyNativeView.this.getContext(), "1111111", 0).show();
            methodCallback.run(hashMap);
        }
    };

    public MyNativeView(Context context) {
        super(context);
        registerMethod("test", this.test);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_modal, (ViewGroup) this, true);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void sendEvent(Object obj, JSResponseListener jSResponseListener) {
        super.sendEvent(obj, jSResponseListener);
    }
}
