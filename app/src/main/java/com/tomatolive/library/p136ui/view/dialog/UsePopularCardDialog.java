package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.UsePopularCardDialog */
/* loaded from: classes3.dex */
public class UsePopularCardDialog extends BaseDialogFragment implements View.OnClickListener {
    private PopularCardEntity popularCardEntity;
    private PopularClickListener popularClickListener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.UsePopularCardDialog$PopularClickListener */
    /* loaded from: classes3.dex */
    public interface PopularClickListener {
        void onClick();
    }

    public void setPopularCardEntity(PopularCardEntity popularCardEntity) {
        this.popularCardEntity = popularCardEntity;
    }

    public static UsePopularCardDialog newInstance(PopularCardEntity popularCardEntity, PopularClickListener popularClickListener) {
        Bundle bundle = new Bundle();
        UsePopularCardDialog usePopularCardDialog = new UsePopularCardDialog();
        usePopularCardDialog.setPopularCardEntity(popularCardEntity);
        usePopularCardDialog.setArguments(bundle);
        usePopularCardDialog.setPopularClickListener(popularClickListener);
        return usePopularCardDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_use_popular_card_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_content_tips);
        PopularCardEntity popularCardEntity = this.popularCardEntity;
        if (popularCardEntity != null) {
            textView2.setText(Html.fromHtml(getString(R$string.fq_use_popular_car_content, popularCardEntity.addition, popularCardEntity.duration)));
        }
        textView.setOnClickListener(this);
        view.findViewById(R$id.tv_start).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UsePopularCardDialog$vu8bvbxfHV941rEEsXS9Q4KQf9o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UsePopularCardDialog.this.lambda$initView$0$UsePopularCardDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$UsePopularCardDialog(View view) {
        if (this.popularClickListener != null) {
            dismiss();
            this.popularClickListener.onClick();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        dismiss();
    }

    private void setPopularClickListener(PopularClickListener popularClickListener) {
        this.popularClickListener = popularClickListener;
    }
}
