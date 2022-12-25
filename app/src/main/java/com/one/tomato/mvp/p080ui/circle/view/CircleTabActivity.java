package com.one.tomato.mvp.p080ui.circle.view;

import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;

/* compiled from: CircleTabActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleTabActivity */
/* loaded from: classes3.dex */
public final class CircleTabActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_circle_tab;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        getSupportFragmentManager().beginTransaction().add(R.id.content, new CircleTabFragment()).commitAllowingStateLoss();
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleTabActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CircleTabActivity.this.onBackPressed();
                }
            });
        }
    }
}
