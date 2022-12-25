package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class ReplysVoListBean {
    private int articleId;
    private int commentId;
    private String createTimeStr;
    private int currentLevelIndex;
    private String fromMemberAvatar;
    private String fromMemberName;
    private int fromUserId;
    private String fromUserMsg;

    /* renamed from: id */
    private int f1735id;
    private int isLouzhu;
    private int isVisibleSelf;
    private int memberId;
    private String replyMsg;
    private int replyType;
    private String secImageUrl;
    private int sendStatus;
    private int status;
    private String toMemberName;
    private int toUserId;
    private String uploadUrl;
    private int vipType;

    public int getId() {
        return this.f1735id;
    }

    public void setId(int i) {
        this.f1735id = i;
    }

    public int getCommentId() {
        return this.commentId;
    }

    public void setCommentId(int i) {
        this.commentId = i;
    }

    public int getFromUserId() {
        return this.fromUserId;
    }

    public void setFromUserId(int i) {
        this.fromUserId = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public int getToUserId() {
        return this.toUserId;
    }

    public void setToUserId(int i) {
        this.toUserId = i;
    }

    public String getReplyMsg() {
        return this.replyMsg;
    }

    public void setReplyMsg(String str) {
        this.replyMsg = str;
    }

    public int getIsLouzhu() {
        return this.isLouzhu;
    }

    public void setIsLouzhu(int i) {
        this.isLouzhu = i;
    }

    public int getReplyType() {
        return this.replyType;
    }

    public void setReplyType(int i) {
        this.replyType = i;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public void setArticleId(int i) {
        this.articleId = i;
    }

    public String getFromUserMsg() {
        return this.fromUserMsg;
    }

    public void setFromUserMsg(String str) {
        this.fromUserMsg = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getSecImageUrl() {
        return this.secImageUrl;
    }

    public void setSecImageUrl(String str) {
        this.secImageUrl = str;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(String str) {
        this.createTimeStr = str;
    }

    public String getFromMemberName() {
        return this.fromMemberName;
    }

    public void setFromMemberName(String str) {
        this.fromMemberName = str;
    }

    public String getFromMemberAvatar() {
        return this.fromMemberAvatar;
    }

    public void setFromMemberAvatar(String str) {
        this.fromMemberAvatar = str;
    }

    public int getIsVisibleSelf() {
        return this.isVisibleSelf;
    }

    public void setIsVisibleSelf(int i) {
        this.isVisibleSelf = i;
    }

    public String getToMemberName() {
        return this.toMemberName;
    }

    public void setToMemberName(String str) {
        this.toMemberName = str;
    }

    public int getSendStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(int i) {
        this.sendStatus = i;
    }

    public String getUploadUrl() {
        return this.uploadUrl;
    }

    public void setUploadUrl(String str) {
        this.uploadUrl = str;
    }

    public int getCurrentLevelIndex() {
        return this.currentLevelIndex;
    }

    public void setCurrentLevelIndex(int i) {
        this.currentLevelIndex = i;
    }

    public int getVipType() {
        return this.vipType;
    }

    public void setVipType(int i) {
        this.vipType = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReplysVoListBean)) {
            return false;
        }
        ReplysVoListBean replysVoListBean = (ReplysVoListBean) obj;
        return this == replysVoListBean || this.f1735id == replysVoListBean.getId();
    }

    public int hashCode() {
        return this.f1735id;
    }
}
