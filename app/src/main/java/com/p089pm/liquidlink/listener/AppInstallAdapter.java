package com.p089pm.liquidlink.listener;

import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.model.Error;

/* renamed from: com.pm.liquidlink.listener.AppInstallAdapter */
/* loaded from: classes3.dex */
public abstract class AppInstallAdapter implements AppInstallListener {
    public abstract void onInstall(AppData appData);

    @Override // com.p089pm.liquidlink.listener.AppInstallListener
    public void onInstallFinish(AppData appData, Error error) {
        if (appData == null) {
            appData = new AppData();
        }
        onInstall(appData);
    }
}
