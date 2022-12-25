package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.view.View;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.PreviewImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.CZImagePreviewOnWebViewFragment;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl */
/* loaded from: classes3.dex */
public class PreviewImageOnWebViewFragmentImpl extends BasePluginImpl<PreviewImageParamsBean> {
    private WebViewFragmentController webViewFragmentController;

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final PreviewImageParamsBean previewImageParamsBean, Plugin.PluginCallback pluginCallback) {
        iWebFragmentController.addListener(new BackListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl.1
            @Override // com.gen.p059mh.webapps.listener.BackListener
            public boolean onBackPressed() {
                if (PreviewImageOnWebViewFragmentImpl.this.webViewFragmentController != null) {
                    PreviewImageOnWebViewFragmentImpl.this.webViewFragmentController.remove();
                    PreviewImageOnWebViewFragmentImpl.this.webViewFragmentController = null;
                    return true;
                }
                return false;
            }
        });
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl.2
            @Override // java.lang.Runnable
            public void run() {
                CZImagePreviewOnWebViewFragment cZImagePreviewOnWebViewFragment = new CZImagePreviewOnWebViewFragment(iWebFragmentController.getContext());
                iWebFragmentController.getContentView().addView(cZImagePreviewOnWebViewFragment);
                PreviewImageOnWebViewFragmentImpl.this.webViewFragmentController = new WebViewFragmentController(iWebFragmentController, cZImagePreviewOnWebViewFragment);
                cZImagePreviewOnWebViewFragment.init(PreviewImageOnWebViewFragmentImpl.this.webViewFragmentController, iWebFragmentController, previewImageParamsBean);
            }
        });
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl$WebViewFragmentController */
    /* loaded from: classes3.dex */
    public static class WebViewFragmentController {
        private View view;
        private IWebFragmentController webViewFragment;

        public WebViewFragmentController(IWebFragmentController iWebFragmentController, View view) {
            this.webViewFragment = iWebFragmentController;
            this.view = view;
        }

        public void remove() {
            this.webViewFragment.getContentView().removeView(this.view);
        }
    }
}
