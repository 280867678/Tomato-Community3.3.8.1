package com.one.tomato.net;

/* loaded from: classes3.dex */
public interface LoginResponseObserver {
    void onLoginCancel();

    void onLoginFail();

    void onLoginSuccess();
}
