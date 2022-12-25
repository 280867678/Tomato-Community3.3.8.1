package com.tomatolive.library.model;

import android.text.TextUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes3.dex */
public class MessageDetailEntity implements MultiItemEntity {
    private String avatar;
    private String content;
    private boolean isSelected = false;
    private String receiverUserId;
    private long sendTime;
    private String senderUserId;
    private int type;
    private String unReadFlag;

    public String getSenderUserId() {
        return this.senderUserId;
    }

    public void setSenderUserId(String str) {
        this.senderUserId = str;
    }

    public String getReceiverUserId() {
        return this.receiverUserId;
    }

    public void setReceiverUserId(String str) {
        this.receiverUserId = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(long j) {
        this.sendTime = j;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setType(int i) {
        this.type = i;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public int getType() {
        return this.type;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public boolean isUnReadFlag() {
        return TextUtils.equals("1", this.unReadFlag);
    }
}
