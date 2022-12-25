package com.tomatolive.library.p136ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.p136ui.activity.mylive.AwardHistoryActivity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.MsgDetailListAdapter */
/* loaded from: classes3.dex */
public class MsgDetailListAdapter extends BaseMultiItemQuickAdapter<MsgDetailListEntity, BaseViewHolder> {
    private static final int MAX_ITEM_COUNT = 150;
    private static final long OUT_TIME = 10000;
    public static final int TYPE_MY_MSG = 1;
    public static final int TYPE_OTHER_MSG = 2;

    public MsgDetailListAdapter(List<MsgDetailListEntity> list) {
        super(list);
        addItemType();
    }

    private void addItemType() {
        addItemType(1, R$layout.fq_item_my_msg);
        addItemType(2, R$layout.fq_item_other_msg);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MsgDetailListEntity msgDetailListEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        boolean z = true;
        if (itemViewType != 1) {
            if (itemViewType != 2) {
                return;
            }
            GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), msgDetailListEntity.targetAvatar, 6, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
            TextView textView = (TextView) baseViewHolder.getView(R$id.tv_other_msg_content);
            if (msgDetailListEntity.isRedLabelFlag()) {
                textView.setText(getSpannableString(msgDetailListEntity));
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                return;
            }
            textView.setText(msgDetailListEntity.msg);
            return;
        }
        if (msgDetailListEntity.status == -1 && System.currentTimeMillis() - NumberUtils.string2long(msgDetailListEntity.time) > OUT_TIME) {
            msgDetailListEntity.status = 0;
        }
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.fq_loading_img), this.mContext, msgDetailListEntity.status == -1 ? R$drawable.fq_ic_private_msg_loading_anim : R$drawable.fq_ic_private_msg_send_fail);
        int i = R$id.fq_loading_img;
        if (msgDetailListEntity.status == 1) {
            z = false;
        }
        baseViewHolder.setVisible(i, z);
        TextView textView2 = (TextView) baseViewHolder.getView(R$id.tv_my_msg_content);
        if (msgDetailListEntity.isRedLabelFlag()) {
            textView2.setText(getSpannableString(msgDetailListEntity));
            textView2.setMovementMethod(LinkMovementMethod.getInstance());
            return;
        }
        textView2.setText(msgDetailListEntity.msg);
    }

    private void ensureMessageListNotOver(MsgDetailListEntity msgDetailListEntity) {
        if (this.mData.size() + 1 >= MAX_ITEM_COUNT) {
            this.mData.removeAll(new ArrayList<>(this.mData.subList(0, 50)));
            notifyItemRangeRemoved(getHeaderLayoutCount() + 0, 50);
            DBUtils.deleteOldPrivateMsgDetailList(msgDetailListEntity.targetId);
        }
    }

    public void addMsg(MsgDetailListEntity msgDetailListEntity) {
        synchronized (this) {
            ensureMessageListNotOver(msgDetailListEntity);
            this.mData.add(msgDetailListEntity);
            notifyItemInserted(this.mData.size());
            if (!getRecyclerView().canScrollVertically(1)) {
                ((LinearLayoutManager) getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(this.mData.size() - 1, 0);
            }
        }
    }

    public void changeMsgStatus(String str, int i) {
        synchronized (this) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mData.size()) {
                    break;
                }
                MsgDetailListEntity msgDetailListEntity = (MsgDetailListEntity) this.mData.get(i2);
                if (TextUtils.equals(msgDetailListEntity.messageId, str)) {
                    msgDetailListEntity.status = i;
                    notifyItemChanged(i2 + getHeaderLayoutCount());
                    break;
                }
                i2++;
            }
        }
    }

    private SpannableString getSpannableString(MsgDetailListEntity msgDetailListEntity) {
        String str = msgDetailListEntity.msg;
        SpannableString spannableString = new SpannableString(str);
        try {
            String str2 = msgDetailListEntity.flagContent;
            int indexOf = str.indexOf(str2);
            spannableString.setSpan(new ClickableSpan() { // from class: com.tomatolive.library.ui.adapter.MsgDetailListAdapter.1
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(@NonNull TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setColor(Color.parseColor("#FF5F52"));
                    textPaint.setUnderlineText(false);
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(@NonNull View view) {
                    Intent intent = new Intent(((BaseQuickAdapter) MsgDetailListAdapter.this).mContext, AwardHistoryActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_FLAG, true);
                    ((BaseQuickAdapter) MsgDetailListAdapter.this).mContext.startActivity(intent);
                }
            }, indexOf, str2.length() + indexOf, 33);
        } catch (Exception unused) {
        }
        return spannableString;
    }
}
