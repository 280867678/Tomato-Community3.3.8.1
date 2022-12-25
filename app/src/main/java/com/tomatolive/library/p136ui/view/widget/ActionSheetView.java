package com.tomatolive.library.p136ui.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$style;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.adapter.ActionSheetDialogAdapter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.ActionSheetView */
/* loaded from: classes4.dex */
public class ActionSheetView {

    /* renamed from: com.tomatolive.library.ui.view.widget.ActionSheetView$ActionSheetOperateListener */
    /* loaded from: classes4.dex */
    public interface ActionSheetOperateListener {
        void onCancel();

        void onOperateListener(MenuEntity menuEntity, int i);
    }

    private ActionSheetView() {
    }

    public static void showOperateCancelDialog(Context context, List<MenuEntity> list, final ActionSheetOperateListener actionSheetOperateListener) {
        final Dialog dialog = new Dialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.fq_layout_actionsheet_cancel_view, (ViewGroup) new LinearLayout(context), false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R$id.rv_operate);
        ActionSheetDialogAdapter actionSheetDialogAdapter = new ActionSheetDialogAdapter(R$layout.fq_item_actionsheet_text, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RVDividerLinear(context, R$color.fq_view_divider_color));
        actionSheetDialogAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(actionSheetDialogAdapter);
        actionSheetDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$ActionSheetView$m3FX6dfJ089LEZhEhUXj2wydaW4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActionSheetView.lambda$showOperateCancelDialog$0(dialog, actionSheetOperateListener, baseQuickAdapter, view, i);
            }
        });
        inflate.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$ActionSheetView$QxApjZRnIv55i3AFsnu6CE6OlaQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActionSheetView.lambda$showOperateCancelDialog$1(dialog, actionSheetOperateListener, view);
            }
        });
        inflate.setMinimumWidth(context.getResources().getDisplayMetrics().widthPixels);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.x = 0;
        attributes.gravity = 80;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(inflate);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showOperateCancelDialog$0(Dialog dialog, ActionSheetOperateListener actionSheetOperateListener, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        dialog.dismiss();
        if (actionSheetOperateListener != null) {
            actionSheetOperateListener.onOperateListener((MenuEntity) baseQuickAdapter.getItem(i), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showOperateCancelDialog$1(Dialog dialog, ActionSheetOperateListener actionSheetOperateListener, View view) {
        dialog.dismiss();
        if (actionSheetOperateListener != null) {
            actionSheetOperateListener.onCancel();
        }
    }

    public static void showFixedHeightDialog(Context context, List<MenuEntity> list, final ActionSheetOperateListener actionSheetOperateListener) {
        final Dialog dialog = new Dialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.fq_layout_actionsheet_fixed_height_view, (ViewGroup) new LinearLayout(context), false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R$id.rv_operate);
        ActionSheetDialogAdapter actionSheetDialogAdapter = new ActionSheetDialogAdapter(R$layout.fq_item_actionsheet_text, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RVDividerLinear(context, R$color.fq_view_divider_color));
        actionSheetDialogAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(actionSheetDialogAdapter);
        actionSheetDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.widget.ActionSheetView.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                dialog.dismiss();
                if (actionSheetOperateListener != null) {
                    actionSheetOperateListener.onOperateListener((MenuEntity) baseQuickAdapter.getItem(i), i);
                }
            }
        });
        inflate.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.ActionSheetView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
                ActionSheetOperateListener actionSheetOperateListener2 = actionSheetOperateListener;
                if (actionSheetOperateListener2 != null) {
                    actionSheetOperateListener2.onCancel();
                }
            }
        });
        inflate.setMinimumWidth(context.getResources().getDisplayMetrics().widthPixels);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.x = 0;
        attributes.gravity = 80;
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(inflate);
        dialog.show();
    }
}
