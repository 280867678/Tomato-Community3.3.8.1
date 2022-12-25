package org.greenrobot.eventbus.util;

import android.os.Bundle;

/* loaded from: classes4.dex */
public abstract class ErrorDialogFragmentFactory<T> {
    protected final ErrorDialogConfig config;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract T prepareErrorFragment(ThrowableFailureEvent throwableFailureEvent, boolean z, Bundle bundle);
}
