package com.one.tomato.p085ui.publish;

import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.ImmersionBarUtil;

/* compiled from: ScannerResultActivity.kt */
/* renamed from: com.one.tomato.ui.publish.ScannerResultActivity */
/* loaded from: classes3.dex */
public final class ScannerResultActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_sanner_result;
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
        ImmersionBarUtil.init(this);
        initTitleBar();
    }
}
