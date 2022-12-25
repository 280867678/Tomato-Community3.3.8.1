package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.NoticeDialog;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReviewFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.ReviewFragment */
/* loaded from: classes3.dex */
public final class ReviewFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.layout_review_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        Context mContext = getMContext();
        if (mContext != null) {
            ReviewPostHeadView reviewPostHeadView = new ReviewPostHeadView(mContext);
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_head);
            if (relativeLayout != null) {
                relativeLayout.addView(reviewPostHeadView);
            }
            Button button = (Button) _$_findCachedViewById(R$id.button);
            if (button == null) {
                return;
            }
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.ReviewFragment$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent(ReviewFragment.this.getContext(), StartReviewPostActivity.class);
                    Context context = ReviewFragment.this.getContext();
                    if (context != null) {
                        context.startActivity(intent);
                    }
                }
            });
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        requestReviewNotice();
    }

    /* compiled from: ReviewFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.ReviewFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ReviewFragment getInstance(int i, String businessType, int i2) {
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            ReviewFragment reviewFragment = new ReviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            bundle.putInt("channel_id", i2);
            reviewFragment.setArguments(bundle);
            return reviewFragment;
        }
    }

    private final void requestReviewNotice() {
        ApiImplService.Companion.getApiImplService().requestReviewNotice("5").compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.ReviewFragment$requestReviewNotice$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                ReviewFragment.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.post.view.ReviewFragment$requestReviewNotice$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<MainNotifyBean> arrayList) {
                Context mContext;
                ReviewFragment.this.dismissDialog();
                Button button = (Button) ReviewFragment.this._$_findCachedViewById(R$id.button);
                if (button != null) {
                    button.setVisibility(0);
                }
                if (!(arrayList == null || arrayList.isEmpty())) {
                    mContext = ReviewFragment.this.getMContext();
                    new NoticeDialog(mContext, arrayList.get(0)).setTv_title_labelText(AppUtil.getString(R.string.post_review_notice));
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ReviewFragment.this.dismissDialog();
                Button button = (Button) ReviewFragment.this._$_findCachedViewById(R$id.button);
                if (button != null) {
                    button.setVisibility(0);
                }
            }
        });
    }
}
