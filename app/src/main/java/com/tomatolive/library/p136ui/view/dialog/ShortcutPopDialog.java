package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.p135db.ShortcutItemEntity;
import com.tomatolive.library.p136ui.adapter.ShortcutItemAdapter;
import com.tomatolive.library.p136ui.view.divider.RVDividerListShortcutMsg;
import com.tomatolive.library.p136ui.view.widget.MaxHeightRecyclerView;
import java.util.List;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.ShortcutPopDialog */
/* loaded from: classes3.dex */
public class ShortcutPopDialog extends BasePopupWindow {
    private OnShortcutListener listener;
    private ShortcutItemAdapter mAdapter;
    private MaxHeightRecyclerView recyclerView;

    /* renamed from: com.tomatolive.library.ui.view.dialog.ShortcutPopDialog$OnShortcutListener */
    /* loaded from: classes3.dex */
    public interface OnShortcutListener {
        void createShortcut();

        void onSelect(ShortcutItemEntity shortcutItemEntity);
    }

    public ShortcutPopDialog(Context context) {
        super(context);
        onInit();
    }

    public ShortcutPopDialog(Context context, int i, int i2) {
        super(context, i, i2);
        onInit();
    }

    @Override // razerdp.basepopup.BasePopup
    public View onCreateContentView() {
        return createPopupById(R$layout.fq_dialog_pop_shortcut);
    }

    private void onInit() {
        setPopupWindowFullScreen(true);
        this.recyclerView = (MaxHeightRecyclerView) findViewById(R$id.rv_shortcut_list);
        this.mAdapter = new ShortcutItemAdapter(R$layout.fq_item_shortcut);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.addItemDecoration(new RVDividerListShortcutMsg(getContext(), R$color.fq_msg_1AFFFFFF));
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.fq_item_add_shortcut, (ViewGroup) null, false);
        this.mAdapter.addFooterView(inflate);
        this.mAdapter.bindToRecyclerView(this.recyclerView);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$ShortcutPopDialog$KOY4nOpaJtbbximYzO-kP24YPPM
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ShortcutPopDialog.this.lambda$onInit$0$ShortcutPopDialog(baseQuickAdapter, view, i);
            }
        });
        inflate.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$ShortcutPopDialog$XJALp7uB5IIO3ubBaElB-n-3ggU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShortcutPopDialog.this.lambda$onInit$1$ShortcutPopDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInit$0$ShortcutPopDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ShortcutItemEntity shortcutItemEntity = (ShortcutItemEntity) baseQuickAdapter.getItem(i);
        if (shortcutItemEntity == null || this.listener == null) {
            return;
        }
        dismiss();
        this.listener.onSelect(shortcutItemEntity);
    }

    public /* synthetic */ void lambda$onInit$1$ShortcutPopDialog(View view) {
        if (this.listener != null) {
            dismiss();
            this.listener.createShortcut();
        }
    }

    public void setNewData(List<ShortcutItemEntity> list) {
        ShortcutItemAdapter shortcutItemAdapter = this.mAdapter;
        if (shortcutItemAdapter != null) {
            shortcutItemAdapter.setNewData(list);
            ((LinearLayoutManager) this.recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
        }
    }

    public void addShortcutMsg(ShortcutItemEntity shortcutItemEntity) {
        ShortcutItemAdapter shortcutItemAdapter = this.mAdapter;
        if (shortcutItemAdapter != null) {
            shortcutItemAdapter.addMsg(shortcutItemEntity);
            ((LinearLayoutManager) this.recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
        }
    }

    public ShortcutPopDialog setOnShortcutListener(OnShortcutListener onShortcutListener) {
        this.listener = onShortcutListener;
        return this;
    }
}
