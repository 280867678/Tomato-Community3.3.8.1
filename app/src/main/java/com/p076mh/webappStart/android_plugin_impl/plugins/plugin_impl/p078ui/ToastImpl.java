package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui;

import android.text.TextUtils;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ToastParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.view.ToastView;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.WebViewFragmentController;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ToastImpl */
/* loaded from: classes3.dex */
public class ToastImpl extends BasePairSharePluginImpl {
    private WebViewFragmentController webViewFragmentController;

    public void action2(IWebFragmentController iWebFragmentController, ToastParamsBean toastParamsBean, Plugin.PluginCallback pluginCallback) {
    }

    public ToastImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    public void baseAction(final IWebFragmentController iWebFragmentController, final ToastParamsBean toastParamsBean, Plugin.PluginCallback pluginCallback) {
        if (this.isOn) {
            if (TextUtils.isEmpty(toastParamsBean.getTitle())) {
                responseFailure(pluginCallback);
                return;
            }
            iWebFragmentController.addListener(new BackListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ToastImpl.1
                @Override // com.gen.p059mh.webapps.listener.BackListener
                public boolean onBackPressed() {
                    if (ToastImpl.this.webViewFragmentController != null) {
                        ToastImpl.this.hideLoadingWork();
                        return true;
                    }
                    return false;
                }
            });
            MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ToastImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastView toastView = new ToastView(iWebFragmentController.getContext());
                    iWebFragmentController.getContentView().addView(toastView);
                    ToastImpl.this.webViewFragmentController = new WebViewFragmentController(iWebFragmentController, toastView);
                    toastView.init(iWebFragmentController, toastParamsBean);
                }
            });
            responseSuccess(pluginCallback);
            MainHandler.getInstance().postDelayed(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ToastImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    ToastImpl.this.hideLoadingWork();
                }
            }, toastParamsBean.getDuration());
            return;
        }
        hideLoadingWork();
        responseSuccess(pluginCallback);
    }

    public void action3(IWebFragmentController iWebFragmentController, ToastParamsBean toastParamsBean, Plugin.PluginCallback pluginCallback) {
        hideLoadingWork();
        baseAction(iWebFragmentController, toastParamsBean, pluginCallback);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        action3(iWebFragmentController, obj == null ? null : (ToastParamsBean) obj, pluginCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadingWork() {
        WebViewFragmentController webViewFragmentController = this.webViewFragmentController;
        if (webViewFragmentController != null) {
            webViewFragmentController.remove();
            this.webViewFragmentController = null;
        }
    }
}
