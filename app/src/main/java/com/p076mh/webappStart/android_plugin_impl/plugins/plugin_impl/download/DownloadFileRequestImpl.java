package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.PoolDownloadManager;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadFileRequestImpl */
/* loaded from: classes3.dex */
public class DownloadFileRequestImpl extends BasePluginImpl<DownloadFileParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, DownloadFileParamsBean downloadFileParamsBean, Plugin.PluginCallback pluginCallback) throws Exception {
        String str = this.TAG;
        Logger.m4112i(str, "action: " + downloadFileParamsBean);
        PoolDownloadManager.getInstance().download(downloadFileParamsBean, iWebFragmentController, pluginCallback, this.executor);
    }
}
