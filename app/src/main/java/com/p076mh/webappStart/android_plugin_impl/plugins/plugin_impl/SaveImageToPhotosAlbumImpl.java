package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ImagePathParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.ImgUtils;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SaveImageToPhotosAlbumImpl */
/* loaded from: classes3.dex */
public class SaveImageToPhotosAlbumImpl extends BasePluginImpl<ImagePathParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ImagePathParamsBean imagePathParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        try {
            hashMap.put("success", Boolean.valueOf(ImgUtils.copyFile2Gallery(WebApplication.getInstance().getApplication(), iWebFragmentController.realPath(imagePathParamsBean.getFilePath()))));
            hashMap.put("complete", true);
            pluginCallback.response(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("success", false);
            hashMap.put("complete", true);
            pluginCallback.response(hashMap);
        }
    }
}
