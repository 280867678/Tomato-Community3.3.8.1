package com.netease.nis.captcha;

/* loaded from: classes3.dex */
public interface CaptchaListener {
    void onCancel();

    void onClose();

    void onError(int i, String str);

    void onReady();

    void onValidate(String str, String str2, String str3);
}
