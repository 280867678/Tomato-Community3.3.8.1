package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PublishPostSpecificationActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PublishPostSpecificationActivity */
/* loaded from: classes3.dex */
public final class PublishPostSpecificationActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_publish_post_specification;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: PublishPostSpecificationActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PublishPostSpecificationActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, PublishPostSpecificationActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.post_review_publish_speci));
        }
    }
}
