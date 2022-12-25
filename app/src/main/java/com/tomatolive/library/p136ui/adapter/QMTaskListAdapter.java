package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.QMTaskListAdapter */
/* loaded from: classes3.dex */
public class QMTaskListAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    public QMTaskListAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        if (qMInteractTaskEntity == null) {
            return;
        }
        int i = R$id.tv_git_count;
        int i2 = 0;
        BaseViewHolder text = baseViewHolder.setText(i, "x" + qMInteractTaskEntity.giftNum).setText(R$id.tv_task_name, qMInteractTaskEntity.taskName).setText(R$id.tv_user_name, Html.fromHtml(this.mContext.getString(R$string.fq_qm_user_naming_tips, StringUtils.formatStrLen(qMInteractTaskEntity.putName, 5))));
        int i3 = R$id.tv_progress_count;
        text.setText(i3, qMInteractTaskEntity.getChargeGiftNum() + "/" + qMInteractTaskEntity.giftNum).setVisible(R$id.rl_git_count_bg, !isShowProgress(qMInteractTaskEntity)).setVisible(R$id.ll_progress_bg, isShowProgress(qMInteractTaskEntity)).setGone(R$id.tv_refuse, isTobeDealStatus(qMInteractTaskEntity)).setGone(R$id.tv_accept, isTobeDealStatus(qMInteractTaskEntity)).setVisible(R$id.tv_gift_loading, qMInteractTaskEntity.isStartTask).addOnClickListener(R$id.tv_refuse).addOnClickListener(R$id.tv_accept).addOnClickListener(R$id.tv_status);
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift), qMInteractTaskEntity.giftUrl, R$drawable.fq_ic_gift_default);
        int string2int = NumberUtils.string2int(qMInteractTaskEntity.giftNum, 0);
        int string2int2 = NumberUtils.string2int(qMInteractTaskEntity.getChargeGiftNum(), 0);
        if (string2int2 > string2int) {
            string2int2 = string2int;
        }
        ProgressBar progressBar = (ProgressBar) baseViewHolder.getView(R$id.progressBar);
        progressBar.setMax(string2int);
        progressBar.setProgress(string2int2);
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_status);
        initStatusView(qMInteractTaskEntity, textView);
        if (!isShowStatus(qMInteractTaskEntity)) {
            i2 = 8;
        }
        textView.setVisibility(i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void initStatusView(QMInteractTaskEntity qMInteractTaskEntity, TextView textView) {
        char c;
        String str = qMInteractTaskEntity.status;
        switch (str.hashCode()) {
            case 48626:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_101)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 48628:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_103)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 49589:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_203)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49590:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_204)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            textView.setText(R$string.fq_qm_task_status_start);
            textView.setBackgroundResource(R$drawable.fq_qm_primary_btn_light);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_primary));
        } else if (c == 1 || c == 2) {
            textView.setText(R$string.fq_qm_task_status_done);
            textView.setBackgroundResource(R$drawable.fq_qm_primary_btn_selector);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_white_color));
        } else if (c == 3) {
            textView.setText(R$string.fq_qm_task_status_revoke);
            textView.setBackgroundResource(R$drawable.fq_qm_shape_yellow);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_white_color));
        } else {
            textView.setText("");
            textView.setBackgroundResource(R$drawable.fq_qm_primary_btn_light);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_primary));
        }
    }

    private boolean isTobeDealStatus(QMInteractTaskEntity qMInteractTaskEntity) {
        if (qMInteractTaskEntity.isStartTask) {
            return false;
        }
        return TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_201);
    }

    private boolean isShowStatus(QMInteractTaskEntity qMInteractTaskEntity) {
        if (qMInteractTaskEntity.isStartTask) {
            return false;
        }
        return !isTobeDealStatus(qMInteractTaskEntity);
    }

    private boolean isShowProgress(QMInteractTaskEntity qMInteractTaskEntity) {
        return TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_101) || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_103);
    }
}
