package com.tomatolive.library.p136ui.activity.home;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.p136ui.fragment.HomeAllFragment;
import com.tomatolive.library.p136ui.fragment.HomeAttentionFragment;
import com.tomatolive.library.p136ui.fragment.HomeHotFragment;
import com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.tomatolive.library.ui.activity.home.TargetFragmentActivity */
/* loaded from: classes3.dex */
public class TargetFragmentActivity extends BaseActivity {
    private BGATitleBar bgaTitleBar;
    private FrameLayout flContentView;
    private LinearLayout llTitleTopBg;
    private String typeFlag;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_target_fragment;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.typeFlag = getIntent().getStringExtra(ConstantUtils.RESULT_FLAG);
        this.llTitleTopBg = (LinearLayout) findViewById(R$id.ll_title_top_bg);
        this.bgaTitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        this.flContentView = (FrameLayout) findViewById(R$id.fl_content_view);
        initTitleBar();
        BaseFragment baseFragment = getBaseFragment(this.typeFlag);
        if (baseFragment == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(R$id.fl_content_view, baseFragment).commitAllowingStateLoss();
    }

    private void initTitleBar() {
        this.bgaTitleBar.setLeftDrawable(R$drawable.fq_ic_title_back);
        this.bgaTitleBar.setTitleText(getTitleStr(this.typeFlag));
        this.bgaTitleBar.setDelegate(new BGATitleBar.Delegate() { // from class: com.tomatolive.library.ui.activity.home.TargetFragmentActivity.1
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickRightCtv() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickRightSecondaryCtv() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickTitleCtv() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickLeftCtv() {
                TargetFragmentActivity.this.onBackPressed();
            }
        });
    }

    private BaseFragment getBaseFragment(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -353951458) {
            if (str.equals(ConstantUtils.ROUTER_TYPE_ATTENTION)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 96673) {
            if (hashCode == 989204668 && str.equals("recommend")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("all")) {
                c = 2;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return HomeHotFragment.newInstance(true);
            }
            if (c == 2) {
                return HomeAllFragment.newInstance();
            }
            return null;
        }
        return HomeAttentionFragment.newInstance(true);
    }

    private String getTitleStr(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -353951458) {
            if (str.equals(ConstantUtils.ROUTER_TYPE_ATTENTION)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 96673) {
            if (hashCode == 989204668 && str.equals("recommend")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("all")) {
                c = 2;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return getString(R$string.fq_home_hot);
            }
            return c != 2 ? "" : getString(R$string.fq_home_all);
        }
        return getString(R$string.fq_home_attention);
    }
}
