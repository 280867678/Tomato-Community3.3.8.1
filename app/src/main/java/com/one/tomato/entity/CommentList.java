package com.one.tomato.entity;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class CommentList implements Serializable {
    private int articleId;
    private String avatar;
    private String commentTime;
    private String content;
    private String createTime;
    private int currentLevelIndex;
    private int goodNum;
    private Object hotCommentList;

    /* renamed from: id */
    private int f1703id;
    private boolean isGold;
    private int isLouzhu;
    private int isThumbDown;
    private int isThumbUp;
    private int isVisibleSelf;
    private int loadStatus;
    private int memberId;
    private String memberName;
    private int pointOnNum;
    private int replyCount;
    private List<ReplysVoListBean> replysVoList;
    private String secImageUrl;
    private int sendStatus;
    private String sex;
    private String status;
    private String uploadUrl;
    private int vipType;

    public int getVipType() {
        return this.vipType;
    }

    public void setVipType(int i) {
        this.vipType = i;
    }

    public CommentList(int i) {
        this.loadStatus = i;
    }

    public int getLoadStatus() {
        return this.loadStatus;
    }

    public void setLoadStatus(int i) {
        this.loadStatus = i;
    }

    public CommentList() {
    }

    public CommentList(int i, int i2) {
        this.loadStatus = i;
        this.f1703id = i2;
    }

    public int getIsVisibleSelf() {
        return this.isVisibleSelf;
    }

    public void setIsVisibleSelf(int i) {
        this.isVisibleSelf = i;
    }

    public Object getHotCommentList() {
        return this.hotCommentList;
    }

    public void setHotCommentList(Object obj) {
        this.hotCommentList = obj;
    }

    public int getId() {
        return this.f1703id;
    }

    public void setId(int i) {
        this.f1703id = i;
    }

    public boolean isGold() {
        return this.isGold;
    }

    public void setGold(boolean z) {
        this.isGold = z;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public void setArticleId(int i) {
        this.articleId = i;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String str) {
        this.memberName = str;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public int getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(int i) {
        this.goodNum = i;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTime(String str) {
        this.commentTime = str;
    }

    public int getIsLouzhu() {
        return this.isLouzhu;
    }

    public void setIsLouzhu(int i) {
        this.isLouzhu = i;
    }

    public List<ReplysVoListBean> getReplysVoList() {
        return this.replysVoList;
    }

    public void setReplysVoList(List<ReplysVoListBean> list) {
        this.replysVoList = list;
    }

    public int getReplyCount() {
        return this.replyCount;
    }

    public void setReplyCount(int i) {
        this.replyCount = i;
    }

    public String getSecImageUrl() {
        return this.secImageUrl;
    }

    public void setSecImageUrl(String str) {
        this.secImageUrl = str;
    }

    public int getIsThumbUp() {
        return this.isThumbUp;
    }

    public void setIsThumbUp(int i) {
        this.isThumbUp = i;
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

    public int getPointOnNum() {
        return this.pointOnNum;
    }

    public void setPointOnNum(int i) {
        this.pointOnNum = i;
    }

    public int getIsThumbDown() {
        return this.isThumbDown;
    }

    public void setIsThumbDown(int i) {
        this.isThumbDown = i;
    }

    public int getCurrentLevelIndex() {
        return this.currentLevelIndex;
    }

    public void setCurrentLevelIndex(int i) {
        this.currentLevelIndex = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommentList)) {
            return false;
        }
        CommentList commentList = (CommentList) obj;
        return this == commentList || this.f1703id == commentList.getId();
    }

    public int hashCode() {
        return this.f1703id;
    }
}
