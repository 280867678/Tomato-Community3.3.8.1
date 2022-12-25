package com.one.tomato.mvp.p080ui.circle.presenter;

import android.support.p005v7.widget.RecyclerView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.CircleCategory;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.anko.AsyncKt;

/* compiled from: CircleAllPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter */
/* loaded from: classes3.dex */
public final class CircleAllPresenter extends MvpBasePresenter<ICircleAllContact$ICircleAllView> implements ICircleAllContact {
    private ArrayList<CircleCategory> categoryList;
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

    public void requestCategoryCircleAll(int i, int i2) {
        ApiImplService.Companion.getApiImplService().requestCategoryCircleAll(1, 100).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$requestCategoryCircleAll$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                CircleAllPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<CircleCategory>>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$requestCategoryCircleAll$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CircleCategory> arrayList) {
                ICircleAllContact$ICircleAllView mView;
                if (arrayList != null) {
                    arrayList.add(0, new CircleCategory(-10, AppUtil.getString(R.string.common_focus_y)));
                }
                CircleAllPresenter.this.categoryList = arrayList;
                mView = CircleAllPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerCategoryCircleAllSucess(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ICircleAllContact$ICircleAllView mView;
                CircleAllPresenter.this.dismissDialog();
                mView = CircleAllPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerCategoryCircleAllError();
                }
            }
        });
    }

    public void requestCircleAll(int i, int i2) {
        ApiImplService.Companion.getApiImplService().requestCircleAll(i, i2).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<CircleAllBean>>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$requestCircleAll$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CircleAllBean> arrayList) {
                CircleAllPresenter.this.dismissDialog();
                DBUtil.saveCiecleAllBean(arrayList);
                CircleAllPresenter.this.circleSort(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CircleAllPresenter.this.dismissDialog();
                StringBuilder sb = new StringBuilder();
                sb.append("請求圈子全部失敗 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    public void requestCircleFllow(int i, final int i2, String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        ApiImplService.Companion.getApiImplService().circleFllow(url, DBUtil.getMemberId(), i).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$requestCircleFllow$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                CircleAllPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$requestCircleFllow$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ICircleAllContact$ICircleAllView mView;
                mView = CircleAllPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerCircleFllowSuccess(i2);
                }
                CircleAllPresenter.this.dismissDialog();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CircleAllPresenter.this.dismissDialog();
                StringBuilder sb = new StringBuilder();
                sb.append("圈子關注失敗 +++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    public final void circleSort(ArrayList<CircleAllBean> arrayList) {
        AsyncKt.doAsync$default(this, null, new CircleAllPresenter$circleSort$1(this, arrayList), 1, null);
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
