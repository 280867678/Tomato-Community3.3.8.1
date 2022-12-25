package com.p089pm.liquidlink.listener;

import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.model.Error;

/* renamed from: com.pm.liquidlink.listener.AppInstallListener */
/* loaded from: classes3.dex */
public interface AppInstallListener {
    void onInstallFinish(AppData appData, Error error);
}
