package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.util.Log;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ImageSrcParamsBean;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.ImgUtils;
import com.p076mh.webappStart.util.bean.ImageInfo;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetImageInfoImpl */
/* loaded from: classes3.dex */
public class GetImageInfoImpl extends BasePluginImpl<ImageSrcParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ImageSrcParamsBean imageSrcParamsBean, final Plugin.PluginCallback pluginCallback) {
        final HashMap hashMap = new HashMap();
        if (imageSrcParamsBean.getSrc().startsWith("app")) {
            ImageInfo localImageInfo = ImgUtils.getLocalImageInfo(iWebFragmentController.realPath(imageSrcParamsBean.getSrc()));
            String str = this.TAG;
            Log.e(str, "imageInfo:" + localImageInfo);
            hashMap.put("success", Boolean.valueOf(localImageInfo != null));
            hashMap.put("width", Integer.valueOf(localImageInfo.getWidth()));
            hashMap.put("height", Integer.valueOf(localImageInfo.getHeight()));
            hashMap.put("orientation", localImageInfo.getOrientation());
            hashMap.put("type", localImageInfo.getType());
            pluginCallback.response(hashMap);
            return;
        }
        ImgUtils.getNetImageInfo(iWebFragmentController.getContext(), imageSrcParamsBean.getSrc(), new CommonCallBack<ImageInfo>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetImageInfoImpl.1
            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onResult(ImageInfo imageInfo) {
                String str2 = ((BasePluginImpl) GetImageInfoImpl.this).TAG;
                Log.e(str2, "imageInfo:" + imageInfo);
                hashMap.put("success", Boolean.valueOf(imageInfo != null));
                hashMap.put("width", Integer.valueOf(imageInfo.getWidth()));
                hashMap.put("height", Integer.valueOf(imageInfo.getHeight()));
                hashMap.put("orientation", imageInfo.getOrientation());
                hashMap.put("type", imageInfo.getType());
                pluginCallback.response(hashMap);
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onFailure(Exception exc) {
                hashMap.put("success", false);
                hashMap.put("complete", true);
                pluginCallback.response(hashMap);
            }
        });
    }
}
