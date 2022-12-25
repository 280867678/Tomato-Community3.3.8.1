package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapp_extensions.AppControl;
import com.gen.p059mh.webapp_extensions.listener.AppControlListener;
import com.gen.p059mh.webapp_extensions.listener.DownloadListener;
import com.gen.p059mh.webapp_extensions.modules.AppInfo;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.unity.UpdateManager */
/* loaded from: classes2.dex */
public class UpdateManager extends Unity {
    AppControl appControl;
    String appKey;
    Unity.Method applyUpdate = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            UpdateManager.this.getWebViewFragment().removePlugins();
            UpdateManager.this.getWebViewFragment().setServerPluginNone();
            UpdateManager.this.getWebViewFragment().addCoverView();
            UpdateManager.this.getWebViewFragment().webServerStop();
            UpdateManager.this.getWebViewFragment().setStartLoaded(false);
            UpdateManager.this.getWebViewFragment().setWorkPath(null);
            UpdateManager.this.getWebViewFragment().setDefaultsKey(null);
            UpdateManager.this.getWebViewFragment().loadApp();
            methodCallback.run("success");
        }
    };
    Unity.Method onCheckForUpdate = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
        }
    };
    Unity.Method checkUpdate = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, final Object... objArr) {
            UpdateManager.this.getWebViewFragment().reloadAppInfo(UpdateManager.this.getWebViewFragment().getAppID(), new OnAppInfoResponse() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.3.1
                @Override // com.gen.p059mh.webapps.listener.OnAppInfoResponse
                public void onComplete(AppResponse appResponse) {
                    try {
                        Logger.m4112i("TTT", "AppResponse:" + appResponse.toString());
                        AppInfo fromAppID = AppInfo.fromAppID(UpdateManager.this.getWebViewFragment().getAppID());
                        Logger.m4112i("TTT", "appInfo:" + fromAppID.toString());
                        Function function = (Function) ((List) objArr[0]).get(0);
                        if (appResponse == null) {
                            function.invoke(null, false);
                        } else if (fromAppID.isNewVersion(appResponse.version)) {
                            function.invoke(null, true);
                        } else {
                            function.invoke(null, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((Function) ((List) objArr[0]).get(0)).invoke(null, false);
                    }
                }

                @Override // com.gen.p059mh.webapps.listener.OnAppInfoResponse
                public void onFail(IErrorInfo iErrorInfo) {
                    ((Function) ((List) objArr[0]).get(0)).invoke(null, false);
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method loadApp = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            UpdateManager.this.appKey = ((List) objArr[0]).get(0).toString();
            UpdateManager updateManager = UpdateManager.this;
            updateManager.appControl = new AppControl(updateManager.appKey);
            UpdateManager updateManager2 = UpdateManager.this;
            updateManager2.appControl.setHandler(updateManager2.getWebViewFragment().getHandler());
            UpdateManager.this.appControl.setDownloadListener(new DownloadListener() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.4.1
                @Override // com.gen.p059mh.webapp_extensions.listener.DownloadListener
                public void onDownloadFail(IErrorInfo iErrorInfo) {
                    Logger.m4112i("onDownloadFail", iErrorInfo.toString());
                    UpdateManager updateManager3 = UpdateManager.this;
                    updateManager3.event("failed", null, updateManager3.appKey);
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.DownloadListener
                public void onRequestAppInfo(String str, OnAppInfoResponse onAppInfoResponse) {
                    UpdateManager.this.getWebViewFragment().requestAppInfo(str, onAppInfoResponse);
                }
            });
            UpdateManager.this.appControl.setListener(new AppControlListener() { // from class: com.gen.mh.webapp_extensions.unity.UpdateManager.4.2
                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onStart() {
                    Logger.m4113i("onStart");
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onReceiveInfo(AppResponse appResponse) {
                    Logger.m4113i("onReceiveInfo");
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onProgress(long j, long j2) {
                    UpdateManager updateManager3 = UpdateManager.this;
                    updateManager3.event("process", null, updateManager3.appKey, Float.valueOf(((float) j) / ((float) j2)));
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onReady() {
                    UpdateManager updateManager3 = UpdateManager.this;
                    updateManager3.event("complete", null, updateManager3.appKey);
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onFail(IErrorInfo iErrorInfo) {
                    UpdateManager updateManager3 = UpdateManager.this;
                    updateManager3.event("failed", null, updateManager3.appKey);
                }

                @Override // com.gen.p059mh.webapp_extensions.listener.AppControlListener
                public void onUpdate() {
                    Logger.m4113i("onUpdate");
                }
            });
            UpdateManager.this.appControl.weakUpdate();
            methodCallback.run("success");
        }
    };

    public UpdateManager() {
        registerMethod("applyUpdate", this.applyUpdate);
        registerMethod("onCheckForUpdate", this.onCheckForUpdate);
        registerMethod("checkUpdate", this.checkUpdate);
        registerMethod("loadApp", this.loadApp);
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onHide() {
        super.onHide();
        if (this.appControl == null || !WebViewLaunchFragment.currentDefaultKey.equals(this.appKey)) {
            return;
        }
        this.appControl.cancel();
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void unload() {
        super.unload();
        AppControl appControl = this.appControl;
        if (appControl != null) {
            appControl.cancel();
        }
    }
}
