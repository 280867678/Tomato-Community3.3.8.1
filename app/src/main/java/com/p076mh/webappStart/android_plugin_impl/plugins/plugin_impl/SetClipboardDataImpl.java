package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.content.ClipboardManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ClipboardDataParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetClipboardDataImpl */
/* loaded from: classes3.dex */
public class SetClipboardDataImpl extends BasePluginImpl<ClipboardDataParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ClipboardDataParamsBean clipboardDataParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        ((ClipboardManager) WebApplication.getInstance().getApplication().getSystemService("clipboard")).setText(clipboardDataParamsBean.getData());
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
