package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentTransaction;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.DBUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReviewPostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.ReviewPostActivity */
/* loaded from: classes3.dex */
public final class ReviewPostActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private Fragment fragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_review_post;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: ReviewPostActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.ReviewPostActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, ReviewPostActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.review_post_title));
        }
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        if (userInfo.isOfficial()) {
            this.fragment = ReviewFragment.Companion.getInstance(-1, "review_post", 0);
        } else if (userInfo.getReviewType() == 0 || userInfo.getReviewType() == 1) {
            this.fragment = ReviewFragment.Companion.getInstance(-1, "review_post_pre", 0);
        } else {
            this.fragment = new ReviewNoFragment();
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = this.fragment;
        if (fragment == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        FragmentTransaction add = beginTransaction.add(R.id.content, fragment);
        Intrinsics.checkExpressionValueIsNotNull(add, "supportFragmentManager.b…R.id.content, fragment!!)");
        add.commitAllowingStateLoss();
    }
}
