package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.p136ui.view.custom.ComponentsPanelControl;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.ComponentsDialog */
/* loaded from: classes3.dex */
public class ComponentsDialog extends BaseBottomDialogFragment {
    private ComponentsPanelControl componentsPanelControl;
    private LinearLayout llDotBg;
    private OnGameItemClickListener onItemClickListener;
    private TextView tvFail;
    private TextView tvLoading;
    private ViewPager viewPagerGame;
    private final int CONTENT_TYPE_LOADING = 1;
    private final int CONTENT_TYPE_CONTENT = 2;
    private final int CONTENT_TYPE_FAIL = 3;
    private int contentType = 1;
    private boolean isLotteryBoomStatus = false;

    /* renamed from: com.tomatolive.library.ui.view.dialog.ComponentsDialog$OnGameItemClickListener */
    /* loaded from: classes3.dex */
    public interface OnGameItemClickListener {
        void onItemClick(int i, ComponentsEntity componentsEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static ComponentsDialog newInstance(boolean z, OnGameItemClickListener onGameItemClickListener) {
        Bundle bundle = new Bundle();
        ComponentsDialog componentsDialog = new ComponentsDialog();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        componentsDialog.setOnItemClickListener(onGameItemClickListener);
        componentsDialog.setArguments(bundle);
        return componentsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_live_game_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isLotteryBoomStatus = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.viewPagerGame = (ViewPager) view.findViewById(R$id.view_pager);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading_view);
        this.tvFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.llDotBg = (LinearLayout) view.findViewById(R$id.ll_dot_bg);
        sendRequest();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        ComponentsPanelControl componentsPanelControl = this.componentsPanelControl;
        if (componentsPanelControl != null) {
            componentsPanelControl.notifyDataAdapter(this.isLotteryBoomStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.tvFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ComponentsDialog.this.sendRequest();
            }
        });
    }

    public void setOnItemClickListener(OnGameItemClickListener onGameItemClickListener) {
        this.onItemClickListener = onGameItemClickListener;
    }

    private void showLoading(int i) {
        this.contentType = i;
        int i2 = 0;
        this.tvLoading.setVisibility(i == 1 ? 0 : 4);
        this.tvFail.setVisibility(i == 3 ? 0 : 4);
        ViewPager viewPager = this.viewPagerGame;
        if (i != 2) {
            i2 = 4;
        }
        viewPager.setVisibility(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest() {
        sendDataRequest();
    }

    private void sendDataRequest() {
        List<ComponentsEntity> localCacheComponentsList = CacheUtils.getLocalCacheComponentsList();
        showLoading(2);
        if (localCacheComponentsList != null && !localCacheComponentsList.isEmpty()) {
            initComponentsPanelControlView(formatDataList(localCacheComponentsList));
        } else {
            initComponentsPanelControlView(formatDataList(null));
        }
    }

    private void showDefaultComponents() {
        initComponentsPanelControlView(null);
    }

    private void initComponentsPanelControlView(List<ComponentsEntity> list) {
        if (list == null || list.isEmpty()) {
            list = formatDataList(null);
        }
        ComponentsPanelControl componentsPanelControl = this.componentsPanelControl;
        if (componentsPanelControl == null) {
            this.componentsPanelControl = new ComponentsPanelControl(this.mContext, this.viewPagerGame, this.llDotBg, list);
            OnGameItemClickListener onGameItemClickListener = this.onItemClickListener;
            if (onGameItemClickListener == null) {
                return;
            }
            this.componentsPanelControl.setOnItemClickListener(onGameItemClickListener);
            return;
        }
        componentsPanelControl.updateDataList(list);
    }

    private List<ComponentsEntity> formatDataList(List<ComponentsEntity> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (ComponentsEntity componentsEntity : list) {
                if (componentsEntity.isCacheLotteryComponents()) {
                    componentsEntity.name = this.mContext.getString(R$string.fq_lottery_menu);
                }
                arrayList.add(componentsEntity);
            }
        }
        return arrayList;
    }
}
