package com.one.tomato.entity;

import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public class VideoCommentList {
    private int articleId;
    private String avatar;
    private int commentId;
    private String commentTime;
    private String content;
    private String createTime;
    private String fromContent;
    private int goodNum;

    /* renamed from: id */
    private int f1743id;
    private boolean isReply;
    private int isThumbsUp;
    private int isVisibleSelf;
    private int memberId;
    private String name;
    private int parentMemberId;
    private ArrayList<VideoCommentList> replyList;
    private int replyNum;
    private int replyStatus;
    private String toMemberAvatar;
    private int toMemberId;
    private String toMemberName;
    private Object type;
    private int vipType;

    public int getVipType() {
        return this.vipType;
    }

    public void setVipType(int i) {
        this.vipType = i;
    }

    public VideoCommentList() {
    }

    public int getIsVisibleSelf() {
        return this.isVisibleSelf;
    }

    public void setIsVisibleSelf(int i) {
        this.isVisibleSelf = i;
    }

    public VideoCommentList(int i) {
        this.f1743id = i;
    }

    public int getId() {
        return this.f1743id;
    }

    public void setId(int i) {
        this.f1743id = i;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public int getToMemberId() {
        return this.toMemberId;
    }

    public void setToMemberId(int i) {
        this.toMemberId = i;
    }

    public String getFromContent() {
        return this.fromContent;
    }

    public void setFromContent(String str) {
        this.fromContent = str;
    }

    public Object getType() {
        return this.type;
    }

    public void setType(Object obj) {
        this.type = obj;
    }

    public int getIsThumbsUp() {
        return this.isThumbsUp;
    }

    public void setIsThumbsUp(int i) {
        this.isThumbsUp = i;
    }

    public int getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(int i) {
        this.goodNum = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
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

    public String getToMemberName() {
        return this.toMemberName;
    }

    public void setToMemberName(String str) {
        this.toMemberName = str;
    }

    public String getToMemberAvatar() {
        return this.toMemberAvatar;
    }

    public void setToMemberAvatar(String str) {
        this.toMemberAvatar = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public ArrayList<VideoCommentList> getReplyList() {
        return this.replyList;
    }

    public void setReplyList(ArrayList<VideoCommentList> arrayList) {
        this.replyList = arrayList;
    }

    public int getReplyStatus() {
        return this.replyStatus;
    }

    public void setReplyStatus(int i) {
        this.replyStatus = i;
    }

    public int getCommentId() {
        return this.commentId;
    }

    public void setCommentId(int i) {
        this.commentId = i;
    }

    public int getReplyNum() {
        return this.replyNum;
    }

    public void setReplyNum(int i) {
        this.replyNum = i;
    }

    public boolean isReply() {
        return this.isReply;
    }

    public void setReply(boolean z) {
        this.isReply = z;
    }

    public int getParentMemberId() {
        return this.parentMemberId;
    }

    public void setParentMemberId(int i) {
        this.parentMemberId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof VideoCommentList) && getId() == ((VideoCommentList) obj).getId();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(getId()));
    }
}
