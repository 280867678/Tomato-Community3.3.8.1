package com.p076mh.webappStart.android_plugin_impl.plugins.plugin;

import com.gen.p059mh.webapps.Plugin;
import com.p076mh.webappStart.android_plugin_impl.beans.CompressImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ImagePathParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ImageSrcParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.PreviewImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.CompressImageImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetImageInfoImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.SaveImageToPhotosAlbumImpl;
import com.p076mh.webappStart.util.GsonUtil;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.DMImagePlugin */
/* loaded from: classes3.dex */
public class DMImagePlugin extends BasePlugin {
    public DMImagePlugin() {
        super("image.tools");
    }

    private void compressImage(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(CompressImageImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) CompressImageParamsBean.class), pluginCallback);
    }

    private void getImageInfo(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetImageInfoImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ImageSrcParamsBean.class), pluginCallback);
    }

    private void saveImage(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SaveImageToPhotosAlbumImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ImagePathParamsBean.class), pluginCallback);
    }

    private void saveVideo(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SaveImageToPhotosAlbumImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ImagePathParamsBean.class), pluginCallback);
    }

    private void previewImage(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(PreviewImageOnWebViewFragmentImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) PreviewImageParamsBean.class), pluginCallback);
    }
}
