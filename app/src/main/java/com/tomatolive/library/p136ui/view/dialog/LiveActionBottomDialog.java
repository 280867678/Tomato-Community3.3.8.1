package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.adapter.ActionSheetDialogAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.utils.AppUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.LiveActionBottomDialog */
/* loaded from: classes3.dex */
public class LiveActionBottomDialog extends BaseBottomDialogFragment {
    public static final int LIVE_ACTION_BANNED = 2;
    private static final String LIVE_ACTION_BANNED_STR = "isBanned";
    public static final int LIVE_ACTION_CREATE_CHAT = 5;
    private static final String LIVE_ACTION_CREATE_CHAT_STR = "isCreateChat";
    private static final String LIVE_ACTION_CREATE_PAY_LIVE = "isPayLive";
    public static final int LIVE_ACTION_CTRL = 1;
    private static final String LIVE_ACTION_CTRL_STR = "isCtrl";
    public static final int LIVE_ACTION_KICK_OUT = 4;
    private static final String LIVE_ACTION_ROLE = "role";
    public static final int LIVE_ACTION_SHIELDED = 3;
    private static final String LIVE_ACTION_SHIELDED_STR = "isShielded";
    private OnLiveActionListener onLiveActionListener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.LiveActionBottomDialog$OnLiveActionListener */
    /* loaded from: classes3.dex */
    public interface OnLiveActionListener {
        void onLiveAction(int i, boolean z);
    }

    public static LiveActionBottomDialog create(String str, OnLiveActionListener onLiveActionListener) {
        LiveActionBottomDialog liveActionBottomDialog = new LiveActionBottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role", str);
        liveActionBottomDialog.setArguments(bundle);
        liveActionBottomDialog.setOnLiveActionListener(onLiveActionListener);
        return liveActionBottomDialog;
    }

    public static LiveActionBottomDialog create(String str, boolean z, OnLiveActionListener onLiveActionListener) {
        LiveActionBottomDialog liveActionBottomDialog = new LiveActionBottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role", str);
        bundle.putBoolean(LIVE_ACTION_SHIELDED_STR, z);
        liveActionBottomDialog.setArguments(bundle);
        liveActionBottomDialog.setOnLiveActionListener(onLiveActionListener);
        return liveActionBottomDialog;
    }

    public static LiveActionBottomDialog create(String str, boolean z, boolean z2, OnLiveActionListener onLiveActionListener) {
        return create(str, z, z2, false, onLiveActionListener);
    }

    public static LiveActionBottomDialog create(String str, boolean z, boolean z2, boolean z3, OnLiveActionListener onLiveActionListener) {
        LiveActionBottomDialog liveActionBottomDialog = new LiveActionBottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role", str);
        bundle.putBoolean(LIVE_ACTION_SHIELDED_STR, z);
        bundle.putBoolean(LIVE_ACTION_BANNED_STR, z2);
        bundle.putBoolean(LIVE_ACTION_CREATE_PAY_LIVE, z3);
        liveActionBottomDialog.setArguments(bundle);
        liveActionBottomDialog.setOnLiveActionListener(onLiveActionListener);
        return liveActionBottomDialog;
    }

    public static LiveActionBottomDialog create(String str, boolean z, boolean z2, boolean z3, boolean z4, OnLiveActionListener onLiveActionListener) {
        LiveActionBottomDialog liveActionBottomDialog = new LiveActionBottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role", str);
        bundle.putBoolean(LIVE_ACTION_CTRL_STR, z);
        bundle.putBoolean(LIVE_ACTION_SHIELDED_STR, z2);
        bundle.putBoolean(LIVE_ACTION_BANNED_STR, z3);
        bundle.putBoolean(LIVE_ACTION_CREATE_CHAT_STR, z4);
        liveActionBottomDialog.setArguments(bundle);
        liveActionBottomDialog.setOnLiveActionListener(onLiveActionListener);
        return liveActionBottomDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_live_action;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R$id.rv_operate);
        ActionSheetDialogAdapter actionSheetDialogAdapter = new ActionSheetDialogAdapter(R$layout.fq_item_actionsheet_text, getMenuList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        recyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        actionSheetDialogAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(actionSheetDialogAdapter);
        actionSheetDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveActionBottomDialog.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                if (LiveActionBottomDialog.this.onLiveActionListener != null) {
                    LiveActionBottomDialog.this.dismiss();
                    MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
                    if (menuEntity == null) {
                        return;
                    }
                    LiveActionBottomDialog.this.onLiveActionListener.onLiveAction(menuEntity.menuType, !menuEntity.isSelected);
                }
            }
        });
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LiveActionBottomDialog$V8-YEHgFxfdqG2fO7_0sNk8sNpI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LiveActionBottomDialog.this.lambda$initView$0$LiveActionBottomDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$LiveActionBottomDialog(View view) {
        dismiss();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private List<MenuEntity> getMenuList() {
        char c;
        ArrayList arrayList = new ArrayList();
        String argumentsString = getArgumentsString("role");
        boolean argumentsBoolean = getArgumentsBoolean(LIVE_ACTION_CTRL_STR);
        boolean argumentsBoolean2 = getArgumentsBoolean(LIVE_ACTION_BANNED_STR);
        boolean argumentsBoolean3 = getArgumentsBoolean(LIVE_ACTION_SHIELDED_STR);
        boolean argumentsBoolean4 = getArgumentsBoolean(LIVE_ACTION_CREATE_CHAT_STR);
        boolean argumentsBoolean5 = getArgumentsBoolean(LIVE_ACTION_CREATE_PAY_LIVE);
        switch (argumentsString.hashCode()) {
            case 49:
                if (argumentsString.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (argumentsString.equals("2")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (argumentsString.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
            default:
                c = 65535;
                break;
            case 53:
                if (argumentsString.equals("5")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
        }
        if (c == 0) {
            arrayList.add(getMenuEntity(getString(R$string.fq_btn_banned_forever), 2));
            arrayList.add(getMenuEntity(getString(R$string.fq_btn_kick_out_forever), 4));
            return arrayList;
        } else if (c == 1) {
            if (argumentsBoolean4 && AppUtils.isEnablePrivateMsg()) {
                arrayList.add(getMenuEntity(getString(R$string.fq_create_chat), 5));
            }
            arrayList.add(getMenuEntity(getString(argumentsBoolean ? R$string.fq_my_live_house_manager_cancel : R$string.fq_my_live_house_manager), 1, argumentsBoolean));
            arrayList.add(getMenuEntity(getString(argumentsBoolean2 ? R$string.btn_cancel_banned : R$string.btn_banned), 2, argumentsBoolean2));
            arrayList.add(getMenuEntity(getString(R$string.btn_kick_out), 4));
            arrayList.add(getMenuEntity(getString(argumentsBoolean3 ? R$string.fq_btn_shielded_cancel : R$string.fq_btn_shielded), 3, argumentsBoolean3));
            return arrayList;
        } else if (c != 2) {
            if (c != 3) {
                arrayList.add(getMenuEntity(getString(argumentsBoolean3 ? R$string.fq_btn_shielded_cancel : R$string.fq_btn_shielded), 3, argumentsBoolean3));
                return arrayList;
            }
            arrayList.add(getMenuEntity(getString(argumentsBoolean3 ? R$string.fq_btn_shielded_cancel : R$string.fq_btn_shielded), 3, argumentsBoolean3));
            return arrayList;
        } else {
            arrayList.add(getMenuEntity(getString(argumentsBoolean2 ? R$string.btn_cancel_banned : R$string.btn_banned), 2, argumentsBoolean2));
            if (!argumentsBoolean5) {
                arrayList.add(getMenuEntity(getString(R$string.btn_kick_out), 4));
            }
            arrayList.add(getMenuEntity(getString(argumentsBoolean3 ? R$string.fq_btn_shielded_cancel : R$string.fq_btn_shielded), 3, argumentsBoolean3));
            return arrayList;
        }
    }

    private MenuEntity getMenuEntity(String str, int i) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.menuTitle = str;
        menuEntity.menuType = i;
        return menuEntity;
    }

    private MenuEntity getMenuEntity(String str, int i, boolean z) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.menuTitle = str;
        menuEntity.menuType = i;
        menuEntity.isSelected = z;
        return menuEntity;
    }

    public OnLiveActionListener getOnLiveActionListener() {
        return this.onLiveActionListener;
    }

    public void setOnLiveActionListener(OnLiveActionListener onLiveActionListener) {
        this.onLiveActionListener = onLiveActionListener;
    }
}
