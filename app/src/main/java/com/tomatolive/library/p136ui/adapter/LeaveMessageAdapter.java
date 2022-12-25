package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MessageDetailEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.LeaveMessageAdapter */
/* loaded from: classes3.dex */
public class LeaveMessageAdapter extends BaseMultiItemQuickAdapter<MessageDetailEntity, BaseViewHolder> {
    private View.OnClickListener onGivenPrizeClickListener;

    public LeaveMessageAdapter(List<MessageDetailEntity> list) {
        super(list);
        addItemType();
    }

    private void addItemType() {
        addItemType(288, R$layout.fq_item_leave_msg_my);
        addItemType(ConstantUtils.MSG_TYPE_OTHER, R$layout.fq_item_leave_msg_other);
        addItemType(ConstantUtils.MSG_TYPE_ADDRESS, R$layout.fq_hd_address_info_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MessageDetailEntity messageDetailEntity) {
        if (messageDetailEntity == null) {
            return;
        }
        switch (baseViewHolder.getItemViewType()) {
            case 288:
                baseViewHolder.setText(R$id.tv_my_msg_content, messageDetailEntity.getContent()).setText(R$id.tv_time, getTimeStr(messageDetailEntity));
                return;
            case ConstantUtils.MSG_TYPE_OTHER /* 289 */:
                GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), messageDetailEntity.getAvatar(), 6, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
                baseViewHolder.setText(R$id.tv_other_msg_content, messageDetailEntity.getContent()).setText(R$id.tv_time, getTimeStr(messageDetailEntity));
                return;
            case ConstantUtils.MSG_TYPE_ADDRESS /* 290 */:
                baseViewHolder.setText(R$id.tv_address, this.mContext.getString(R$string.fq_hd_receive_address, messageDetailEntity.getContent())).setText(R$id.tv_time, getTimeStr(messageDetailEntity));
                TextView textView = (TextView) baseViewHolder.getView(R$id.tv_has_given_prize);
                if (!TextUtils.isEmpty(messageDetailEntity.getReceiverUserId())) {
                    textView.setVisibility(0);
                    if (messageDetailEntity.isSelected()) {
                        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_white_color));
                        textView.setBackgroundResource(0);
                        textView.setEnabled(false);
                    } else {
                        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_hd_lottery_primary));
                        textView.setBackgroundResource(R$drawable.fq_achieve_bg_shape_white_round_corner);
                        textView.setEnabled(true);
                    }
                } else {
                    textView.setVisibility(8);
                }
                View.OnClickListener onClickListener = this.onGivenPrizeClickListener;
                if (onClickListener == null) {
                    return;
                }
                textView.setOnClickListener(onClickListener);
                return;
            default:
                return;
        }
    }

    public View.OnClickListener getOnGivenPrizeClickListener() {
        return this.onGivenPrizeClickListener;
    }

    public void setOnGivenPrizeClickListener(View.OnClickListener onClickListener) {
        this.onGivenPrizeClickListener = onClickListener;
    }

    private String getTimeStr(MessageDetailEntity messageDetailEntity) {
        return DateUtils.formatSecondToDateFormat(messageDetailEntity.getSendTime(), DateUtils.C_DATE_PATTON_DATE_CHINA_7);
    }
}
