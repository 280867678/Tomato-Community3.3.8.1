package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.view.custom.LiveMorePanelView;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.LiveMoreDialog */
/* loaded from: classes3.dex */
public class LiveMoreDialog extends BaseBottomDialogFragment {
    private LiveMorePanelView componentsPanelControl;
    private LinearLayout llDotBg;
    private OnMenuItemClickListener onItemClickListener;
    private TextView tvFail;
    private TextView tvLoading;
    private ViewPager viewPagerGame;
    private final int CONTENT_TYPE_LOADING = 1;
    private final int CONTENT_TYPE_CONTENT = 2;
    private final int CONTENT_TYPE_FAIL = 3;
    private int contentType = 1;
    private boolean isTranOpen = false;

    /* renamed from: com.tomatolive.library.ui.view.dialog.LiveMoreDialog$OnMenuItemClickListener */
    /* loaded from: classes3.dex */
    public interface OnMenuItemClickListener {
        void onItemClick(int i, MenuEntity menuEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static LiveMoreDialog newInstance(boolean z, OnMenuItemClickListener onMenuItemClickListener) {
        Bundle bundle = new Bundle();
        LiveMoreDialog liveMoreDialog = new LiveMoreDialog();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        liveMoreDialog.setOnItemClickListener(onMenuItemClickListener);
        liveMoreDialog.setArguments(bundle);
        return liveMoreDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_live_game_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isTranOpen = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.viewPagerGame = (ViewPager) view.findViewById(R$id.view_pager);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading_view);
        this.tvFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.llDotBg = (LinearLayout) view.findViewById(R$id.ll_dot_bg);
        showLoading(2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        LiveMorePanelView liveMorePanelView = this.componentsPanelControl;
        if (liveMorePanelView == null) {
            this.componentsPanelControl = new LiveMorePanelView(this.mContext, this.viewPagerGame, this.llDotBg, getMenuList());
            OnMenuItemClickListener onMenuItemClickListener = this.onItemClickListener;
            if (onMenuItemClickListener == null) {
                return;
            }
            this.componentsPanelControl.setOnItemClickListener(onMenuItemClickListener);
            return;
        }
        liveMorePanelView.notifyDataAdapter(this.isTranOpen);
    }

    public void setOnItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onItemClickListener = onMenuItemClickListener;
    }

    public void updatePanelView() {
        LiveMorePanelView liveMorePanelView = this.componentsPanelControl;
        if (liveMorePanelView != null) {
            liveMorePanelView.notifyDataAdapter(this.isTranOpen);
        }
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

    private List<MenuEntity> getMenuList() {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_live_more_menu);
        int[] iArr = {R$drawable.fq_ic_live_more_translate_def, R$drawable.fq_ic_live_more_msg, R$drawable.fq_ic_qm_menu};
        int[] iArr2 = {273, 274, 275};
        if (SysConfigInfoManager.getInstance().isEnableTranslation()) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.menuTitle = stringArray[0];
            menuEntity.menuIcon = iArr[0];
            menuEntity.menuType = iArr2[0];
            menuEntity.isSelected = this.isTranOpen;
            arrayList.add(menuEntity);
        }
        if (AppUtils.isEnablePrivateMsg()) {
            MenuEntity menuEntity2 = new MenuEntity();
            menuEntity2.menuTitle = stringArray[1];
            menuEntity2.menuIcon = iArr[1];
            menuEntity2.menuType = iArr2[1];
            menuEntity2.isSelected = false;
            arrayList.add(menuEntity2);
        }
        if (AppUtils.isEnableQMInteract()) {
            MenuEntity menuEntity3 = new MenuEntity();
            menuEntity3.menuTitle = stringArray[2];
            menuEntity3.menuIcon = iArr[2];
            menuEntity3.menuType = iArr2[2];
            menuEntity3.isSelected = false;
            arrayList.add(menuEntity3);
        }
        return arrayList;
    }
}
