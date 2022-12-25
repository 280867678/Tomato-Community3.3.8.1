package com.tomatolive.library.model.p135db;

import com.tomatolive.library.utils.DBUtils;

/* renamed from: com.tomatolive.library.model.db.MsgListEntity */
/* loaded from: classes3.dex */
public class MsgListEntity extends BaseDBEntity {
    public String appId;
    public String targetId;
    public String time;
    public String userId;

    public MsgDetailListEntity getLastMsgDetailListEntity() {
        return (MsgDetailListEntity) DBUtils.selectOneItemByOrder(MsgDetailListEntity.class, "time desc", "userId = ? and targetId = ?", this.userId, this.targetId);
    }
}
