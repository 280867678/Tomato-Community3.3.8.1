package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.graphics.BitmapFactory;
import android.util.Log;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.CompressImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.ImgUtils;
import java.io.File;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.CompressImageImpl */
/* loaded from: classes3.dex */
public class CompressImageImpl extends BasePluginImpl<CompressImageParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, CompressImageParamsBean compressImageParamsBean, Plugin.PluginCallback pluginCallback) {
        doAction(iWebFragmentController, compressImageParamsBean, pluginCallback);
    }

    public void doAction(IWebFragmentController iWebFragmentController, CompressImageParamsBean compressImageParamsBean, Plugin.PluginCallback pluginCallback) {
        String realPath;
        String str;
        HashMap hashMap = new HashMap();
        try {
            boolean qualityCompress = ImgUtils.qualityCompress(BitmapFactory.decodeFile(iWebFragmentController.realPath(compressImageParamsBean.getSrc())), new File(iWebFragmentController.getTempDir().getAbsolutePath() + File.separator + "dm_" + System.currentTimeMillis() + new File(realPath).getName()), compressImageParamsBean.getQuality());
            hashMap.put("tempFilePath", "tmp:///" + str.replace(iWebFragmentController.getTempDir().getAbsolutePath(), ""));
            hashMap.put("success", Boolean.valueOf(qualityCompress));
            hashMap.put("complete", true);
            Log.e(this.TAG, "tempFilePath: tmp:///" + str.replace(iWebFragmentController.getTempDir().getAbsolutePath(), ""));
            pluginCallback.response(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("success", false);
            hashMap.put("complete", true);
            pluginCallback.response(hashMap);
        }
    }
}
