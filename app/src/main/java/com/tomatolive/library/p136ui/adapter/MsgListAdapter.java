package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.model.p135db.MsgListEntity;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.MsgListAdapter */
/* loaded from: classes3.dex */
public class MsgListAdapter extends BaseQuickAdapter<MsgListEntity, BaseViewHolder> {
    public MsgListAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MsgListEntity msgListEntity) {
        MsgDetailListEntity lastMsgDetailListEntity = msgListEntity.getLastMsgDetailListEntity();
        String str = "";
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_msg_list_avatar), lastMsgDetailListEntity == null ? str : lastMsgDetailListEntity.targetAvatar);
        BaseViewHolder text = baseViewHolder.setText(R$id.tv_msg_list_name, StringUtils.formatStrLen(lastMsgDetailListEntity == null ? str : lastMsgDetailListEntity.targetName, 7)).setText(R$id.tv_msg_list_pre, StringUtils.formatStrLen(lastMsgDetailListEntity == null ? str : lastMsgDetailListEntity.msg, 15));
        int i = R$id.tv_msg_list_date;
        Context context = this.mContext;
        if (lastMsgDetailListEntity != null) {
            str = lastMsgDetailListEntity.time;
        }
        text.setText(i, DateUtils.getFriendlyTimeSpanByNow(context, NumberUtils.string2long(str)));
    }

    public void addMsg(MsgListEntity msgListEntity) {
        this.mData.add(0, msgListEntity);
        notifyItemInserted(0);
    }
}
