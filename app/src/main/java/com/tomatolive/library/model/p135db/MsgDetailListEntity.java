package com.tomatolive.library.model.p135db;

import android.text.TextUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/* renamed from: com.tomatolive.library.model.db.MsgDetailListEntity */
/* loaded from: classes3.dex */
public class MsgDetailListEntity extends BaseDBEntity implements MultiItemEntity {
    public String messageId;
    public String msg;
    public int status;
    public String targetAvatar;
    public String targetId;
    public String targetName;
    public String time;
    public int type;
    public String userId;
    public String winningFlag = "0";
    public String flagContent = "";

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public boolean isRedLabelFlag() {
        return TextUtils.equals(this.winningFlag, "1");
    }

    public String toString() {
        return "MsgDetailListEntity{msg='" + this.msg + "', messageId='" + this.messageId + "', status=" + this.status + '}';
    }
}
