package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class CommentReplyList {
    private int articleId;
    private String avatar;
    private String commentTime;
    private String content;
    private int countNum;
    private String createTime;
    private int currentLevelIndex;
    private int goodNum;

    /* renamed from: id */
    private int f1704id;
    private String imageUrl;
    private int isLouzhu;
    private int isThumbsUp;
    private int memberId;
    private String memberName;
    private int replyId;
    private int replyType;
    private ArrayList<ReplysVoListBean> replysVoList;
    private String sex;
    private int status;
    private String thumbnail;
    private String toMemberId;
    private String toMemberName;
    private String toUserAvatar;
    private int toUserId;
    private String toUserName;
    private String toUserSex;
    private int vipType;

    public int getId() {
        return this.f1704id;
    }

    public void setId(int i) {
        this.f1704id = i;
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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public int getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(int i) {
        this.goodNum = i;
    }

    public int getToUserId() {
        return this.toUserId;
    }

    public void setToUserId(int i) {
        this.toUserId = i;
    }

    public int getIsThumbsUp() {
        return this.isThumbsUp;
    }

    public void setIsThumbsUp(int i) {
        this.isThumbsUp = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String str) {
        this.thumbnail = str;
    }

    public String getToUserSex() {
        return this.toUserSex;
    }

    public void setToUserSex(String str) {
        this.toUserSex = str;
    }

    public String getToUserName() {
        return this.toUserName;
    }

    public void setToUserName(String str) {
        this.toUserName = str;
    }

    public String getToUserAvatar() {
        return this.toUserAvatar;
    }

    public void setToUserAvatar(String str) {
        this.toUserAvatar = str;
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

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public int getCountNum() {
        return this.countNum;
    }

    public void setCountNum(int i) {
        this.countNum = i;
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

    public int getReplyType() {
        return this.replyType;
    }

    public void setReplyType(int i) {
        this.replyType = i;
    }

    public int getReplyId() {
        return this.replyId;
    }

    public void setReplyId(int i) {
        this.replyId = i;
    }

    public String getToMemberId() {
        return this.toMemberId;
    }

    public void setToMemberId(String str) {
        this.toMemberId = str;
    }

    public String getToMemberName() {
        return this.toMemberName;
    }

    public void setToMemberName(String str) {
        this.toMemberName = str;
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

    public ArrayList<ReplysVoListBean> getReplysVoList() {
        return this.replysVoList;
    }

    public void setReplysVoList(ArrayList<ReplysVoListBean> arrayList) {
        this.replysVoList = arrayList;
    }
}
