package com.bumptech.glide.manager;

import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public interface Lifecycle {
    void addListener(@NonNull LifecycleListener lifecycleListener);

    void removeListener(@NonNull LifecycleListener lifecycleListener);
}
