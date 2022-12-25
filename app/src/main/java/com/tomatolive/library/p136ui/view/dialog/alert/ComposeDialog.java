package com.tomatolive.library.p136ui.view.dialog.alert;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.PropConfigEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.alert.ComposeDialog */
/* loaded from: classes3.dex */
public class ComposeDialog extends BaseDialogFragment implements View.OnClickListener {
    private static final String KEY_SUC = "KEY_SUC";
    private ComposeListener composeListener;
    private PropConfigEntity entity;

    /* renamed from: com.tomatolive.library.ui.view.dialog.alert.ComposeDialog$ComposeListener */
    /* loaded from: classes3.dex */
    public interface ComposeListener {
        void onClick(PropConfigEntity propConfigEntity);
    }

    public static ComposeDialog newInstance(PropConfigEntity propConfigEntity, boolean z, ComposeListener composeListener) {
        Bundle bundle = new Bundle();
        ComposeDialog composeDialog = new ComposeDialog();
        bundle.putBoolean(KEY_SUC, z);
        composeDialog.setArguments(bundle);
        composeDialog.setFragmentConfigEntity(propConfigEntity);
        composeDialog.setComposeListener(composeListener);
        return composeDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_compose_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_tips);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_content_tips);
        TextView textView4 = (TextView) view.findViewById(R$id.tv_start);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.ll_root);
        TextView textView5 = (TextView) view.findViewById(R$id.tv_know);
        if (getArguments().getBoolean(KEY_SUC)) {
            linearLayout.setVisibility(0);
            textView5.setVisibility(8);
            textView2.setText(getString(R$string.fq_sure_compose, this.entity.propName));
            textView3.setText(getString(R$string.fq_compose_sure_tip, "50", this.entity.propName));
        } else {
            linearLayout.setVisibility(8);
            textView5.setVisibility(0);
            textView2.setText(R$string.fq_compose_fail_tips);
            textView3.setText(getString(R$string.fq_compose_tips, "50"));
        }
        textView5.setOnClickListener(this);
        textView.setOnClickListener(this);
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.alert.-$$Lambda$ComposeDialog$2jO6I5hP3D7yfX4FLKRDt7ZFtc4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ComposeDialog.this.lambda$initView$0$ComposeDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$ComposeDialog(View view) {
        if (this.composeListener != null) {
            dismiss();
            this.composeListener.onClick(this.entity);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        dismiss();
    }

    private void setComposeListener(ComposeListener composeListener) {
        this.composeListener = composeListener;
    }

    private void setFragmentConfigEntity(PropConfigEntity propConfigEntity) {
        this.entity = propConfigEntity;
    }
}
