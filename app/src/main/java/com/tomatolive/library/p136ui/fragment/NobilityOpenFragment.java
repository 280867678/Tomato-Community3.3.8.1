package com.tomatolive.library.p136ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.download.NobilityDownLoadManager;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenOrderActivity;
import com.tomatolive.library.p136ui.adapter.NobilityOpenAdapter;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerNobilityOpenGrid;
import com.tomatolive.library.p136ui.view.headview.NobilityOpenHeadView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxViewUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.NobilityOpenFragment */
/* loaded from: classes3.dex */
public class NobilityOpenFragment extends BaseFragment {
    private LinearLayout llOpenBg;
    private LoadingDialog loadingDialog;
    private NobilityOpenAdapter mAdapter;
    private NobilityOpenHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private NobilityEntity nobilityEntity;
    private int nobleGrade = -1;
    private TextView tvFirstOpen;
    private TextView tvOpen;
    private TextView tvRenewalFee;
    private TextView tvTips;

    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter */
    protected BasePresenter mo6641createPresenter() {
        return null;
    }

    public static NobilityOpenFragment newInstance(NobilityEntity nobilityEntity) {
        Bundle bundle = new Bundle();
        NobilityOpenFragment nobilityOpenFragment = new NobilityOpenFragment();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, nobilityEntity);
        nobilityOpenFragment.setArguments(bundle);
        return nobilityOpenFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_nobility_open;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.nobilityEntity = (NobilityEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        NobilityEntity nobilityEntity = this.nobilityEntity;
        this.nobleGrade = nobilityEntity != null ? NumberUtils.string2int(nobilityEntity.type) : 0;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.tvFirstOpen = (TextView) view.findViewById(R$id.tv_first_open);
        this.tvRenewalFee = (TextView) view.findViewById(R$id.tv_renewal_fee);
        this.tvOpen = (TextView) view.findViewById(R$id.tv_open);
        this.tvTips = (TextView) view.findViewById(R$id.tv_tips);
        this.llOpenBg = (LinearLayout) view.findViewById(R$id.ll_open_bg);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.loadingDialog = new LoadingDialog(this.mContext, getString(R$string.fq_nobility_open_loading_tips));
        this.tvOpen.setEnabled(AppUtils.isEnableNobility());
        initAdapter();
        NobilityEntity nobilityEntity = this.nobilityEntity;
        if (nobilityEntity != null) {
            this.tvFirstOpen.setText(Html.fromHtml(getString(R$string.fq_nobility_open_first_tips, getPriceStr(nobilityEntity.getOpenPrice()), AppUtils.formatDisplayPrice(this.nobilityEntity.getRebatePrice(), false), AppUtils.getNobilityGoldUnitStr(this.mContext))));
            this.tvRenewalFee.setText(Html.fromHtml(getString(R$string.fq_nobility_open_fee_tips, getPriceStr(this.nobilityEntity.getRenewPrice()), AppUtils.formatDisplayPrice(this.nobilityEntity.getRebatePrice(), false), AppUtils.getNobilityGoldUnitStr(this.mContext))));
            int i = 4;
            this.tvTips.setVisibility(this.nobilityEntity.isBanBuy() ? 0 : 4);
            LinearLayout linearLayout = this.llOpenBg;
            if (!this.nobilityEntity.isBanBuy()) {
                i = 0;
            }
            linearLayout.setVisibility(i);
            if (this.nobilityEntity.isBanBuy()) {
                return;
            }
            this.tvOpen.setText(this.nobilityEntity.isRenew() ? R$string.fq_nobility_renewal_fee : R$string.fq_btn_open);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        RxViewUtils.getInstance().throttleFirst(this.tvOpen, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$NobilityOpenFragment$LC8lZQIgQk1uJBeolsbtXwldlso
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                NobilityOpenFragment.this.lambda$initListener$0$NobilityOpenFragment(obj);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityOpenFragment(Object obj) {
        if (!AppUtils.isCanShowOpenNobilityAnim(this.nobleGrade)) {
            toNobilityOpenOrderActivity();
        } else if (!FileUtils.isFileExists(NobilityDownLoadManager.getInstance().getNobilityFilePath(this.nobleGrade))) {
            NobilityDownLoadManager.getInstance().updateAnimOnlineSingleRes(this.nobleGrade, this.loadingDialog, new ResultCallBack<String>() { // from class: com.tomatolive.library.ui.fragment.NobilityOpenFragment.1
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(String str) {
                    NobilityOpenFragment.this.dismissDialog();
                    NobilityOpenFragment.this.toNobilityOpenOrderActivity();
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str) {
                    NobilityOpenFragment.this.dismissDialog();
                    NobilityOpenFragment.this.showToast(R$string.fq_nobility_open_loading_fail_tips);
                }
            });
        } else {
            toNobilityOpenOrderActivity();
        }
    }

    private void initAdapter() {
        this.mHeadView = new NobilityOpenHeadView(this.mContext);
        this.mAdapter = new NobilityOpenAdapter(R$layout.fq_item_grid_nobility_open);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext, 4);
        this.mRecyclerView.addItemDecoration(new RVDividerNobilityOpenGrid(getContext(), R$color.fq_colorWhite));
        this.mRecyclerView.setLayoutManager(gridLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
        this.mAdapter.setNewData(getList());
        this.mHeadView.initData(this.nobleGrade);
    }

    private List<MenuEntity> getList() {
        ArrayList arrayList = new ArrayList();
        int[] iArr = {R$drawable.fq_ic_nobility_privilege_label_2, R$drawable.fq_ic_nobility_privilege_label_5, R$drawable.fq_ic_nobility_privilege_label_4, R$drawable.fq_ic_nobility_privilege_label_3, R$drawable.fq_ic_nobility_privilege_label_1, R$drawable.fq_ic_nobility_privilege_label_6, R$drawable.fq_ic_nobility_privilege_label_7, R$drawable.fq_ic_nobility_privilege_label_8, R$drawable.fq_ic_nobility_privilege_label_12, R$drawable.fq_ic_nobility_stealth_in_list, R$drawable.fq_ic_nobility_privilege_label_11, R$drawable.fq_ic_nobility_privilege_label_10, R$drawable.fq_ic_nobility_privilege_label_9, R$drawable.fq_ic_nobility_privilege_label_14};
        String[] desc = getDesc();
        String[] stringArray = getResources().getStringArray(R$array.fq_nobility_privilege_open_tips);
        for (int i = 0; i < desc.length; i++) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.menuTitle = stringArray[i];
            menuEntity.menuDesc = desc[i];
            menuEntity.menuIcon = iArr[i];
            if (this.nobleGrade == 1 && i >= 5) {
                menuEntity.isSelected = true;
            }
            if (this.nobleGrade == 2 && i >= 6) {
                menuEntity.isSelected = true;
            }
            if (this.nobleGrade == 3 && i >= 6) {
                menuEntity.isSelected = true;
            }
            if (this.nobleGrade == 4 && i >= 8) {
                menuEntity.isSelected = true;
            }
            if (this.nobleGrade == 5 && i >= 9) {
                menuEntity.isSelected = true;
            }
            if (this.nobleGrade == 6 && i >= 10) {
                menuEntity.isSelected = true;
            }
            arrayList.add(menuEntity);
        }
        return arrayList;
    }

    private String[] getDesc() {
        switch (this.nobleGrade) {
            case 1:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_1);
            case 2:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_2);
            case 3:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_3);
            case 4:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_4);
            case 5:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_5);
            case 6:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_6);
            case 7:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_7);
            default:
                return getResources().getStringArray(R$array.fq_nobility_privilege_open_tips_desc_1);
        }
    }

    private String getPriceStr(String str) {
        return AppUtils.formatMoneyUnitStr(this.mContext, str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toNobilityOpenOrderActivity() {
        Intent intent = new Intent(this.mContext, NobilityOpenOrderActivity.class);
        intent.putExtra(ConstantUtils.RESULT_ITEM, this.nobilityEntity);
        startActivity(intent);
    }
}
