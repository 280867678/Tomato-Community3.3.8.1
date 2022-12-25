package com.one.tomato.mvp.p080ui.login.impl;

import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.view.IBaseView;

/* compiled from: ILoginContact.kt */
/* renamed from: com.one.tomato.mvp.ui.login.impl.ILoginContact$ILoginView */
/* loaded from: classes3.dex */
public interface ILoginContact$ILoginView extends IBaseView {
    void handleVerifyPhoneCode(ForgetPsSec forgetPsSec);

    void handlerLogin(LoginInfo loginInfo);

    void handlerSafetyVerify(LoginInfo loginInfo);
}
