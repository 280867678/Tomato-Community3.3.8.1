package com.tomatolive.library.p136ui.activity.noble;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.p136ui.presenter.RankHiddenPresenter;
import com.tomatolive.library.p136ui.view.iview.IHiddenInRankListView;
import com.tomatolive.library.utils.ConstantUtils;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobleHiddenInRankListActivity */
/* loaded from: classes3.dex */
public class NobleHiddenInRankListActivity extends BaseActivity<RankHiddenPresenter> implements IHiddenInRankListView {
    private CheckBox checkBox;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public RankHiddenPresenter mo6636createPresenter() {
        return new RankHiddenPresenter(this, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_rank_hidden;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_nobility_privilege_hidden_in_rank_list);
        this.checkBox = (CheckBox) findViewById(R$id.cb_box);
        this.checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleHiddenInRankListActivity$uIe11EHH6P0fFaQ8tliKyVK1rvs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleHiddenInRankListActivity.this.lambda$initView$0$NobleHiddenInRankListActivity(view);
            }
        });
        this.checkBox.setChecked(getIntent().getBooleanExtra(ConstantUtils.RESULT_FLAG, false));
    }

    public /* synthetic */ void lambda$initView$0$NobleHiddenInRankListActivity(View view) {
        ((RankHiddenPresenter) this.mPresenter).setHiddenInRankList(this.checkBox.isChecked());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHiddenInRankListView
    public void onModifySuccess() {
        EventBus.getDefault().post(new NobilityOpenEvent());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHiddenInRankListView
    public void onModifyFail(String str) {
        ToastUtils.showShort(str);
        this.checkBox.toggle();
    }
}
