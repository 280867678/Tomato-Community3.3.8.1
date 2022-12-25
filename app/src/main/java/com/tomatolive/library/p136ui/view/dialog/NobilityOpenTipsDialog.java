package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.view.dialog.NobilityOpenTipsDialog */
/* loaded from: classes3.dex */
public class NobilityOpenTipsDialog extends BaseDialogFragment {
    private static final String TIP_KEY = "TIP_KEY";
    public static final int TYPE_NOBILITY_OPEN = 11;
    public static final int TYPE_SMR_OPEN = 13;
    public static final int TYPE_TRUMPET_OPEN = 12;
    private View.OnClickListener openClickListener;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.7d;
    }

    private void setOpenClickListener(View.OnClickListener onClickListener) {
        this.openClickListener = onClickListener;
    }

    public static NobilityOpenTipsDialog newInstance(int i, View.OnClickListener onClickListener) {
        NobilityOpenTipsDialog nobilityOpenTipsDialog = new NobilityOpenTipsDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TIP_KEY, i);
        nobilityOpenTipsDialog.setArguments(bundle);
        nobilityOpenTipsDialog.setOpenClickListener(onClickListener);
        return nobilityOpenTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_open_nobility_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_content);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_tips);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_open_room);
        ImageView imageView = (ImageView) view.findViewById(R$id.iv_bg);
        View findViewById = view.findViewById(R$id.ib_close);
        switch (getArgumentsInt(TIP_KEY)) {
            case 11:
                imageView.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_nobility_dialog_bg_ktgz));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
                textView.setText(R$string.fq_nobility_danmu_tip);
                textView2.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_danmu_open_tip)));
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_red_shape_nobility_open));
                textView3.setText(getSMRTips(4));
                break;
            case 12:
                imageView.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_nobility_dialog_bg_ktgz_trumpet));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorYellow));
                textView.setText(R$string.fq_nobility_trumpet_tip);
                textView2.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_trumpet_open_tip)));
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_yellow_shape_nobility_open));
                textView3.setText(getSMRTips(4));
                break;
            case 13:
                imageView.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_nobility_dialog_bg_ktgz_smr));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPurple));
                textView.setText(R$string.fq_nobility_smr_tip);
                textView2.setText(R$string.fq_nobility_hide_tip);
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_purple_shape_nobility_open));
                textView3.setText(getSMRTips(6));
                break;
        }
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$NobilityOpenTipsDialog$AKV1bLlyKwrUPeTjG085ZZfsyHE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NobilityOpenTipsDialog.this.lambda$initView$0$NobilityOpenTipsDialog(view2);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$NobilityOpenTipsDialog$9bS2SuJaEmYaOrFZPVqqVXTm2Lk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NobilityOpenTipsDialog.this.lambda$initView$1$NobilityOpenTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$NobilityOpenTipsDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$NobilityOpenTipsDialog(View view) {
        dismiss();
        this.openClickListener.onClick(view);
    }

    private String getSMRTips(int i) {
        if (UserInfoManager.getInstance().getNobilityType() >= i) {
            return getString(R$string.fq_nobility_renewal_fee);
        }
        if (AppUtils.isNobilityUser()) {
            return getString(R$string.fq__nobility_upgrade);
        }
        return getString(R$string.fq_open_nobility);
    }
}
