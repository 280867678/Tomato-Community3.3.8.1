package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.divider.RecyclerViewCornerRadius;
import com.tomatolive.library.utils.SystemUtils;
import java.util.List;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.SpinnerDialog */
/* loaded from: classes3.dex */
public class SpinnerDialog extends BasePopupWindow {
    private SpinnerAdapter mAdapter;
    private OnSpinnerItemClickListener onSpinnerItemClickListener;
    private RecyclerViewCornerRadius radiusItemDecoration;
    private RecyclerView recyclerView;

    /* renamed from: com.tomatolive.library.ui.view.dialog.SpinnerDialog$OnSpinnerItemClickListener */
    /* loaded from: classes3.dex */
    public interface OnSpinnerItemClickListener {
        void onItemClick(String str, int i);
    }

    public SpinnerDialog(Context context, List<String> list) {
        super(context);
        initView(list);
    }

    @Override // razerdp.basepopup.BasePopup
    public View onCreateContentView() {
        return createPopupById(R$layout.fq_dialog_pay_spinner);
    }

    private void initView(List<String> list) {
        this.recyclerView = (RecyclerView) findViewById(R$id.rv_spinner);
        this.mAdapter = new SpinnerAdapter(list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.radiusItemDecoration = new RecyclerViewCornerRadius();
        this.radiusItemDecoration.setCornerRadius((int) SystemUtils.dp2px(10.0f));
        this.recyclerView.addItemDecoration(this.radiusItemDecoration);
        this.mAdapter.bindToRecyclerView(this.recyclerView);
        this.recyclerView.setAdapter(this.mAdapter);
        this.radiusItemDecoration.setRecyclerViewAllRoundRect(this.recyclerView);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.SpinnerDialog.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SpinnerDialog.this.mAdapter.setSelectPos(i);
                if (SpinnerDialog.this.onSpinnerItemClickListener != null) {
                    SpinnerDialog.this.dismiss();
                    SpinnerDialog.this.onSpinnerItemClickListener.onItemClick((String) baseQuickAdapter.getItem(i), i);
                }
            }
        });
    }

    public void setSpinnerItemSelected(int i) {
        this.mAdapter.setSelectPos(i);
    }

    public void setOnSpinnerItemClickListener(OnSpinnerItemClickListener onSpinnerItemClickListener) {
        this.onSpinnerItemClickListener = onSpinnerItemClickListener;
    }

    public String getDefaultItemMenu() {
        return this.mAdapter.getItem(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.SpinnerDialog$SpinnerAdapter */
    /* loaded from: classes3.dex */
    public class SpinnerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private int pos = -1;

        public SpinnerAdapter(List<String> list) {
            super(R$layout.fq_item_list_spinner_pay, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, String str) {
            baseViewHolder.setText(R$id.tv_spinner_item, str);
            int i = -1;
            baseViewHolder.setBackgroundColor(R$id.rl_spinner_bg, baseViewHolder.getLayoutPosition() == this.pos ? Color.parseColor("#FF5252") : -1);
            int i2 = R$id.tv_spinner_item;
            if (baseViewHolder.getLayoutPosition() != this.pos) {
                i = Color.parseColor("#292F37");
            }
            baseViewHolder.setTextColor(i2, i);
        }

        public void setSelectPos(int i) {
            this.pos = i;
            notifyDataSetChanged();
        }
    }
}
