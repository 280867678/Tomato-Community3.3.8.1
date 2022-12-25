package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.content.ClipboardManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ClipboardDataParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetClipboardDataImpl */
/* loaded from: classes3.dex */
public class GetClipboardDataImpl extends BasePluginImpl<ClipboardDataParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ClipboardDataParamsBean clipboardDataParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        ClipboardManager clipboardManager = (ClipboardManager) WebApplication.getInstance().getApplication().getSystemService("clipboard");
        hashMap.put(AopConstants.APP_PROPERTIES_KEY, clipboardManager.getText() == null ? "" : clipboardManager.getText().toString());
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
