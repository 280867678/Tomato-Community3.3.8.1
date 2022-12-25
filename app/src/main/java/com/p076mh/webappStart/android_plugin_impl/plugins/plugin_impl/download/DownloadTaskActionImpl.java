package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadTaskParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.PoolDownloadManager;
import java.lang.reflect.InvocationTargetException;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskActionImpl */
/* loaded from: classes3.dex */
public class DownloadTaskActionImpl extends BasePluginImpl<DownloadTaskParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, DownloadTaskParamsBean downloadTaskParamsBean, Plugin.PluginCallback pluginCallback) {
        try {
            PoolDownloadManager.getInstance().downloadTaskControl(downloadTaskParamsBean);
            responseSuccess(pluginCallback);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }
}
