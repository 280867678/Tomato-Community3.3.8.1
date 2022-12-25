package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.presenter.MyIncomePresenter;
import com.tomatolive.library.p136ui.view.iview.IMyIncomeView;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyIncomeActivity */
/* loaded from: classes3.dex */
public class MyIncomeActivity extends BaseActivity<MyIncomePresenter> implements IMyIncomeView {
    private TextView tvNumber;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initListener$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initListener$1(View view) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyIncomeView
    public void onDataSuccess(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
        }
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyIncomePresenter mo6636createPresenter() {
        return new MyIncomePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_my_income;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_my_income);
        this.tvNumber = (TextView) findViewById(R$id.tv_number);
        ((MyIncomePresenter) this.mPresenter).initData();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        findViewById(R$id.tv_convert).setOnClickListener($$Lambda$MyIncomeActivity$kwSyMkfnpgQEKkNOgv0uQ8GztPU.INSTANCE);
        findViewById(R$id.tv_withdraw).setOnClickListener($$Lambda$MyIncomeActivity$Z2z7EyihUoMnWmOKOT5Qy8Y2TsI.INSTANCE);
    }
}
