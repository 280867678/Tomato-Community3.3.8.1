package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.content.Intent;
import android.net.Uri;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.MakePhoneCallParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.MainHandler;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.MakePhoneCallImpl */
/* loaded from: classes3.dex */
public class MakePhoneCallImpl extends BasePluginImpl<MakePhoneCallParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final MakePhoneCallParamsBean makePhoneCallParamsBean, Plugin.PluginCallback pluginCallback) {
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.MakePhoneCallImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:" + makePhoneCallParamsBean.getPhoneNumber()));
                    iWebFragmentController.getActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
