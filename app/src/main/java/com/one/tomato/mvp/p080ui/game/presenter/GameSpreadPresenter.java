package com.one.tomato.mvp.p080ui.game.presenter;

import com.one.tomato.entity.GameSpreadDetail;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.game.impl.IGameSpreadContact;
import com.one.tomato.mvp.p080ui.game.impl.IGameSpreadContact$IGameSpreadView;
import com.one.tomato.utils.RxUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameSpreadPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.presenter.GameSpreadPresenter */
/* loaded from: classes3.dex */
public final class GameSpreadPresenter extends MvpBasePresenter<IGameSpreadContact$IGameSpreadView> implements IGameSpreadContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestCashDetail(int i) {
        ApiImplService.Companion.getApiImplService().requestGameSpreadDetail(i).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameSpreadDetail>() { // from class: com.one.tomato.mvp.ui.game.presenter.GameSpreadPresenter$requestCashDetail$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameSpreadDetail t) {
                IGameSpreadContact$IGameSpreadView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = GameSpreadPresenter.this.getMView();
                if (mView != null) {
                    mView.handleCashDetail(t);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IGameSpreadContact$IGameSpreadView mView;
                mView = GameSpreadPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
