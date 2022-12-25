package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.net.Uri;
import android.util.Log;
import com.example.screenhotlibrary.ScreenHotListener;
import com.example.screenhotlibrary.XRScreenHot;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.io.File;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.UserCaptureScreenImpl */
/* loaded from: classes3.dex */
public class UserCaptureScreenImpl extends BasePairSharePluginImpl {
    private String preViousPath = null;

    public UserCaptureScreenImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn) {
            XRScreenHot.with(iWebFragmentController.getContext()).start(new ScreenHotListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.UserCaptureScreenImpl.1
                @Override // com.example.screenhotlibrary.ScreenHotListener
                public void onScreenHotSuccess(String str, long j) {
                    if (str.equals(UserCaptureScreenImpl.this.preViousPath)) {
                        return;
                    }
                    String str2 = ((BasePluginImpl) UserCaptureScreenImpl.this).TAG;
                    Log.e(str2, "screen shot path: " + str);
                    String str3 = ((BasePluginImpl) UserCaptureScreenImpl.this).TAG;
                    Log.e(str3, "screen shot uri: " + Uri.fromFile(new File(str)));
                    ((BasePluginImpl) UserCaptureScreenImpl.this).executor.executeEvent(JsCallBackKeys.ON_USER_CAPTURE_SCREEN, null, null);
                    UserCaptureScreenImpl.this.preViousPath = str;
                }
            });
        } else {
            XRScreenHot.with(iWebFragmentController.getContext()).recycle();
        }
        responseSuccess(pluginCallback);
    }
}
