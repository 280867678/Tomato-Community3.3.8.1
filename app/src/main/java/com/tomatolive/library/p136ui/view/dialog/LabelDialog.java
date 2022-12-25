package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.LabelMenuAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.dialog.LabelDialog */
/* loaded from: classes3.dex */
public class LabelDialog extends BaseDialogFragment {
    private LabelMenuAdapter labelMenuAdapter;
    private RecyclerView recyclerView;

    /* renamed from: com.tomatolive.library.ui.view.dialog.LabelDialog$OnLabelSelectedListener */
    /* loaded from: classes3.dex */
    public interface OnLabelSelectedListener {
        void onLabelSelected(LabelMenuAdapter labelMenuAdapter, int i, LabelEntity labelEntity);
    }

    public static LabelDialog newInstance(LabelMenuAdapter labelMenuAdapter) {
        Bundle bundle = new Bundle();
        LabelDialog labelDialog = new LabelDialog();
        labelDialog.setArguments(bundle);
        labelDialog.setLabelMenuAdapter(labelMenuAdapter);
        return labelDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_layout_label;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        initAdapter();
        view.findViewById(R$id.rl_label_root).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LabelDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LabelDialog.this.dismiss();
            }
        });
    }

    private void initAdapter() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.recyclerView.addItemDecoration(new RVDividerDropGrid(this.mContext, 17170445));
        LabelMenuAdapter labelMenuAdapter = this.labelMenuAdapter;
        if (labelMenuAdapter != null) {
            this.recyclerView.setAdapter(labelMenuAdapter);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment, com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        super.onStart();
        if (((BaseDialogFragment) this).mDialog != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            ((BaseDialogFragment) this).mDialog.getWindow().setLayout(-1, -1);
            ((BaseDialogFragment) this).mDialog.setCancelable(false);
            ((BaseDialogFragment) this).mDialog.getWindow().setDimAmount(0.5f);
            ((BaseDialogFragment) this).mDialog.setCanceledOnTouchOutside(false);
        }
    }

    public LabelMenuAdapter getLabelMenuAdapter() {
        return this.labelMenuAdapter;
    }

    private void setLabelMenuAdapter(LabelMenuAdapter labelMenuAdapter) {
        this.labelMenuAdapter = labelMenuAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.LabelDialog$RVDividerDropGrid */
    /* loaded from: classes3.dex */
    public static class RVDividerDropGrid extends Y_DividerItemDecoration {
        private int colorRes;
        private final Context context;

        private RVDividerDropGrid(Context context, @ColorRes int i) {
            super(context);
            this.context = context;
            this.colorRes = i;
        }

        @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
        public Y_Divider getDivider(int i) {
            int i2 = i % 2;
            if (i2 == 0 || i2 == 1) {
                return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 20.0f, 0.0f, 0.0f).create();
            }
            return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 20.0f, 0.0f, 0.0f).create();
        }
    }
}
