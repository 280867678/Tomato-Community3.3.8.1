package com.p089pm.liquidlink.listener;

import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.model.Error;

/* renamed from: com.pm.liquidlink.listener.AppWakeUpAdapter */
/* loaded from: classes3.dex */
public abstract class AppWakeUpAdapter implements AppWakeUpListener {
    public abstract void onWakeUp(AppData appData);

    @Override // com.p089pm.liquidlink.listener.AppWakeUpListener
    public void onWakeUpFinish(AppData appData, Error error) {
        if (error == null && appData != null && !appData.isEmpty()) {
            onWakeUp(appData);
        }
    }
}
