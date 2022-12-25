package com.one.tomato.mvp.p080ui.sign.impl;

import com.one.tomato.entity.SignBean;
import com.one.tomato.mvp.base.view.IBaseView;

/* renamed from: com.one.tomato.mvp.ui.sign.impl.ISignImpl$ISignView */
/* loaded from: classes3.dex */
public interface ISignImpl extends IBaseView {
    void handlerSignDay(SignBean signBean);

    void handlerSignNumDay();
}
