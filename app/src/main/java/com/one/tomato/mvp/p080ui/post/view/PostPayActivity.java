package com.one.tomato.mvp.p080ui.post.view;

import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.AppUtil;

/* compiled from: PostPayActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostPayActivity */
/* loaded from: classes3.dex */
public final class PostPayActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_post_pay;
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
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(AppUtil.getString(R.string.post_home_pay));
        }
        PostPayFragment companion = PostPayFragment.Companion.getInstance(-1, "", 8);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager != null ? supportFragmentManager.beginTransaction() : null;
        if (beginTransaction != null) {
            beginTransaction.add(R.id.content, companion);
        }
        if (beginTransaction != null) {
            beginTransaction.commitAllowingStateLoss();
        }
    }
}
