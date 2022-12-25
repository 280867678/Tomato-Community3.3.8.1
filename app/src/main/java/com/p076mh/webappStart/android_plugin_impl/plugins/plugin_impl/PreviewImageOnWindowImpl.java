package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.PreviewImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.CZImagePreviewOnWindow;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWindowImpl */
/* loaded from: classes3.dex */
public class PreviewImageOnWindowImpl extends BasePluginImpl<PreviewImageParamsBean> {
    private WindowManager mWindowManager;

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<WindowManager, WindowManager.LayoutParams> initWindowManager(IWebFragmentController iWebFragmentController) {
        WindowManager windowManager = (WindowManager) iWebFragmentController.getContext().getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1);
        if (Build.VERSION.SDK_INT >= 23) {
            layoutParams.type = 2038;
        } else {
            layoutParams.type = 2003;
        }
        layoutParams.format = 1;
        layoutParams.flags = 40;
        layoutParams.x = 0;
        layoutParams.y = 0;
        return new Pair<>(windowManager, layoutParams);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final PreviewImageParamsBean previewImageParamsBean, Plugin.PluginCallback pluginCallback) {
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWindowImpl.1
            @Override // java.lang.Runnable
            public void run() {
                Pair initWindowManager = PreviewImageOnWindowImpl.this.initWindowManager(iWebFragmentController);
                PreviewImageOnWindowImpl.this.mWindowManager = (WindowManager) initWindowManager.first;
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) initWindowManager.second;
                CZImagePreviewOnWindow cZImagePreviewOnWindow = new CZImagePreviewOnWindow(iWebFragmentController.getContext());
                PreviewImageOnWindowImpl.this.mWindowManager.addView(cZImagePreviewOnWindow, layoutParams);
                PreviewImageOnWindowImpl.this.mWindowManager.updateViewLayout(cZImagePreviewOnWindow, layoutParams);
                cZImagePreviewOnWindow.init(new WindowViewController(PreviewImageOnWindowImpl.this.mWindowManager, cZImagePreviewOnWindow), iWebFragmentController, previewImageParamsBean);
            }
        });
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWindowImpl$WindowViewController */
    /* loaded from: classes3.dex */
    public static class WindowViewController {
        private WindowManager mWindowManager;
        private View view;

        public WindowViewController(WindowManager windowManager, View view) {
            this.mWindowManager = windowManager;
            this.view = view;
        }

        public void remove() {
            this.mWindowManager.removeView(this.view);
        }
    }
}
