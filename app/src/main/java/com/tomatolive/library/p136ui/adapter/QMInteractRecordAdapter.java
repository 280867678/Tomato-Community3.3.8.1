package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.QMInteractRecordAdapter */
/* loaded from: classes3.dex */
public class QMInteractRecordAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    public QMInteractRecordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        if (qMInteractTaskEntity == null) {
            return;
        }
        BaseViewHolder text = baseViewHolder.setText(R$id.tv_task_name, qMInteractTaskEntity.taskName);
        int i = R$id.tv_gift_num;
        text.setText(i, "x" + qMInteractTaskEntity.giftNum).setText(R$id.tv_anchor_name, StringUtils.formatStrLen(qMInteractTaskEntity.anchorName, 5)).setText(R$id.tv_time, DateUtils.formatSecondToDateFormat(qMInteractTaskEntity.createTime, DateUtils.C_TIME_PATTON_DEFAULT));
        initStatusView((TextView) baseViewHolder.getView(R$id.tv_status), qMInteractTaskEntity);
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift), qMInteractTaskEntity.giftUrl, R$drawable.fq_ic_gift_default);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void initStatusView(TextView textView, QMInteractTaskEntity qMInteractTaskEntity) {
        char c;
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_qm_record_status);
        String str = qMInteractTaskEntity.status;
        switch (str.hashCode()) {
            case 49588:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_202)) {
                    c = 3;
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
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 49591:
                if (str.equals(ConstantUtils.QM_TASK_STATUS_205)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 49592:
                if (str.equals("206")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 49593:
                if (str.equals("207")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 49594:
                if (str.equals("208")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 49595:
                if (str.equals("209")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                textView.setText(stringArray[0]);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_task_status_1));
                return;
            case 2:
                textView.setText(stringArray[1]);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_task_status_2));
                return;
            case 3:
            case 4:
                textView.setText(stringArray[2]);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_task_status_3));
                return;
            case 5:
            case 6:
            case 7:
                textView.setText(stringArray[3]);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_task_status_4));
                return;
            default:
                return;
        }
    }
}
