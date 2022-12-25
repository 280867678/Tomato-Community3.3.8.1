package com.tomatolive.library.p136ui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.QMTaskListUserAdapter */
/* loaded from: classes3.dex */
public class QMTaskListUserAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    public QMTaskListUserAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        int i = R$id.tv_git_count;
        BaseViewHolder text = baseViewHolder.setText(i, "x" + qMInteractTaskEntity.giftNum).setText(R$id.tv_task_name, qMInteractTaskEntity.taskName).setText(R$id.tv_user_name, Html.fromHtml(this.mContext.getString(R$string.fq_qm_user_naming_tips, StringUtils.formatStrLen(qMInteractTaskEntity.putName, 5))));
        int i2 = R$id.tv_progress_count;
        text.setText(i2, qMInteractTaskEntity.getChargeGiftNum() + "/" + qMInteractTaskEntity.giftNum).setText(R$id.tv_gift_loading, TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_201) ? R$string.fq_qm_waiting_accept : R$string.fq_qm_waiting_for_performance).setText(R$id.tv_status, R$string.fq_qm_reward_one).setVisible(R$id.rl_git_count_bg, !isRechargeTask(qMInteractTaskEntity)).setVisible(R$id.ll_progress_bg, isRechargeTask(qMInteractTaskEntity)).setVisible(R$id.tv_status, isShowStatus(qMInteractTaskEntity)).setVisible(R$id.tv_gift_loading, isShowWaiting(qMInteractTaskEntity)).addOnClickListener(R$id.tv_status);
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift), qMInteractTaskEntity.giftUrl, R$drawable.fq_ic_gift_default);
        int string2int = NumberUtils.string2int(qMInteractTaskEntity.giftNum, 0);
        int string2int2 = NumberUtils.string2int(qMInteractTaskEntity.getChargeGiftNum(), 0);
        if (string2int2 > string2int) {
            string2int2 = string2int;
        }
        ProgressBar progressBar = (ProgressBar) baseViewHolder.getView(R$id.progressBar);
        progressBar.setMax(string2int);
        progressBar.setProgress(string2int2);
    }

    private boolean isRechargeTask(QMInteractTaskEntity qMInteractTaskEntity) {
        return qMInteractTaskEntity.isChargeTask() || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_101) || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_103);
    }

    private boolean isShowStatus(QMInteractTaskEntity qMInteractTaskEntity) {
        return qMInteractTaskEntity.isChargeTask() && TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_101);
    }

    private boolean isShowWaiting(QMInteractTaskEntity qMInteractTaskEntity) {
        return TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_203) || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_201) || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_204) || TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_103);
    }
}
