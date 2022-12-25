package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ShowLoadingParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.view.LoadingView;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.WebViewFragmentController;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.LoadingImpl */
/* loaded from: classes3.dex */
public class LoadingImpl extends BasePairSharePluginImpl {
    private WebViewFragmentController webViewFragmentController;

    public LoadingImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    public void baseAction(final IWebFragmentController iWebFragmentController, final Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn) {
            iWebFragmentController.addListener(new BackListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.LoadingImpl.1
                @Override // com.gen.p059mh.webapps.listener.BackListener
                public boolean onBackPressed() {
                    if (LoadingImpl.this.webViewFragmentController != null) {
                        LoadingImpl.this.hideLoadingWork();
                        return true;
                    }
                    return false;
                }
            });
            MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.LoadingImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    LoadingView loadingView = new LoadingView(iWebFragmentController.getContext());
                    iWebFragmentController.getContentView().addView(loadingView);
                    LoadingImpl.this.webViewFragmentController = new WebViewFragmentController(iWebFragmentController, loadingView);
                    loadingView.init((ShowLoadingParamsBean) obj);
                }
            });
            responseSuccess(pluginCallback);
            return;
        }
        hideLoadingWork();
        responseSuccess(pluginCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadingWork() {
        WebViewFragmentController webViewFragmentController = this.webViewFragmentController;
        if (webViewFragmentController != null) {
            webViewFragmentController.remove();
            this.webViewFragmentController = null;
        }
    }

    public void action3(IWebFragmentController iWebFragmentController, ShowLoadingParamsBean showLoadingParamsBean, Plugin.PluginCallback pluginCallback) {
        hideLoadingWork();
        baseAction(iWebFragmentController, showLoadingParamsBean, pluginCallback);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        action3(iWebFragmentController, obj == null ? null : (ShowLoadingParamsBean) obj, pluginCallback);
    }
}
