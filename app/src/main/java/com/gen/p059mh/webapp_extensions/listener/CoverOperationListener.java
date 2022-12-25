package com.gen.p059mh.webapp_extensions.listener;

import java.io.File;

/* renamed from: com.gen.mh.webapp_extensions.listener.CoverOperationListener */
/* loaded from: classes2.dex */
public interface CoverOperationListener {
    void onClose();

    void onRefresh();

    File provideAnimFile();

    String provideConfigData();

    File provideIconFile();
}
