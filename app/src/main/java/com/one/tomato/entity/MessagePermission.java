package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class MessagePermission {
    private int articleFlag;
    private int commentFlag;
    private String createTime;

    /* renamed from: id */
    private int f1717id;
    private int interactiveMsg;
    private int memberId;
    private int noticeMsg;
    private int replyFlag;
    private int systemMsg;

    public int getInteractiveMsg() {
        return this.interactiveMsg;
    }

    public void setInteractiveMsg(int i) {
        this.interactiveMsg = i;
    }

    public int getNoticeMsg() {
        return this.noticeMsg;
    }

    public void setNoticeMsg(int i) {
        this.noticeMsg = i;
    }

    public int getSystemMsg() {
        return this.systemMsg;
    }

    public void setSystemMsg(int i) {
        this.systemMsg = i;
    }

    public int getId() {
        return this.f1717id;
    }

    public void setId(int i) {
        this.f1717id = i;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public int getArticleFlag() {
        return this.articleFlag;
    }

    public void setArticleFlag(int i) {
        this.articleFlag = i;
    }

    public int getReplyFlag() {
        return this.replyFlag;
    }

    public void setReplyFlag(int i) {
        this.replyFlag = i;
    }

    public int getCommentFlag() {
        return this.commentFlag;
    }

    public void setCommentFlag(int i) {
        this.commentFlag = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }
}
