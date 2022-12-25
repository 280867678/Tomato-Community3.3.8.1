package com.one.tomato.mvp.p080ui.post.presenter;

import com.one.tomato.entity.BaseResponse;
import io.reactivex.functions.Consumer;

/* compiled from: NewCommentPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$thumbComment$1 */
/* loaded from: classes3.dex */
final class NewCommentPresenter$thumbComment$1<T> implements Consumer<BaseResponse<Object>> {
    public static final NewCommentPresenter$thumbComment$1 INSTANCE = new NewCommentPresenter$thumbComment$1();

    NewCommentPresenter$thumbComment$1() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(BaseResponse<Object> baseResponse) {
        boolean z = baseResponse instanceof BaseResponse;
    }
}
