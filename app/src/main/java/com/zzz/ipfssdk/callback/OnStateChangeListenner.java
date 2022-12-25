package com.zzz.ipfssdk.callback;

import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes4.dex */
public interface OnStateChangeListenner {
    void onException(CodeState codeState);

    void onIniting();

    void onInitted();
}
