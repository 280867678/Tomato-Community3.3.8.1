package com.one.tomato.mvp.p080ui.game.presenter;

import android.support.p005v7.widget.RecyclerView;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.game.impl.IGameContact;
import com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GamePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.presenter.GamePresenter */
/* loaded from: classes3.dex */
public final class GamePresenter extends MvpBasePresenter<IGameContact$IGameView> implements IGameContact {
    private int index = -1;
    private boolean mShouldScroll;

    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public final boolean getMShouldScroll() {
        return this.mShouldScroll;
    }

    public final void setMShouldScroll(boolean z) {
        this.mShouldScroll = z;
    }

    public final int getIndex() {
        return this.index;
    }

    public void requestList(HashMap<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestGameList(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<AdPage>>() { // from class: com.one.tomato.mvp.ui.game.presenter.GamePresenter$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<AdPage> t) {
                IGameContact$IGameView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = GamePresenter.this.getMView();
                if (mView != null) {
                    mView.handleList(t);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IGameContact$IGameView mView;
                mView = GamePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestGameTypeList(HashMap<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestGameCenterList(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.game.presenter.GamePresenter$requestGameTypeList$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                GamePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<GameTypeData>>() { // from class: com.one.tomato.mvp.ui.game.presenter.GamePresenter$requestGameTypeList$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<GameTypeData> t) {
                IGameContact$IGameView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                GamePresenter.this.dismissDialog();
                mView = GamePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerGameType(t);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IGameContact$IGameView mView;
                GamePresenter.this.dismissDialog();
                mView = GamePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestBGH5Login(Map<String, Object> mutableMap, final SubGamesBean subGamesBean) {
        Intrinsics.checkParameterIsNotNull(mutableMap, "mutableMap");
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        ApiImplService.Companion.getApiImplService().requestLiveGameBGLogin(mutableMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.game.presenter.GamePresenter$requestBGH5Login$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                GamePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<GameBGLoginBean>() { // from class: com.one.tomato.mvp.ui.game.presenter.GamePresenter$requestBGH5Login$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameBGLoginBean gameBGLoginBean) {
                IGameContact$IGameView mView;
                GamePresenter.this.dismissDialog();
                mView = GamePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerBGLogin(gameBGLoginBean, subGamesBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                GamePresenter.this.dismissDialog();
            }
        });
    }

    public final void smoothMoveToPosition(RecyclerView mRecyclerView, int i) {
        Intrinsics.checkParameterIsNotNull(mRecyclerView, "mRecyclerView");
        this.index = i;
        int childLayoutPosition = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int childLayoutPosition2 = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (i < childLayoutPosition) {
            mRecyclerView.smoothScrollToPosition(i);
        } else if (i <= childLayoutPosition2) {
            int i2 = i - childLayoutPosition;
            if (i2 < 0 || i2 >= mRecyclerView.getChildCount()) {
                return;
            }
            mRecyclerView.smoothScrollBy(0, mRecyclerView.getChildAt(i2).getTop());
        } else {
            mRecyclerView.smoothScrollToPosition(i);
            this.mShouldScroll = true;
        }
    }
}
