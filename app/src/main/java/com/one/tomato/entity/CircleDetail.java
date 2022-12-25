package com.one.tomato.entity;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class CircleDetail implements Serializable {
    private static final long serialVersionUID = 3522793922709549814L;
    private int articleCount;
    private String backgroundImage;
    private String brief;
    private String categoryName;
    private int followCount;
    private int followFlag;
    private int groupNoticeId;

    /* renamed from: id */
    private int f1700id;
    private String logo = "";
    private String name;
    private String noticeContent;
    private String noticeTime;
    private String noticeTitle;
    private int official;
    private PostList topArticle;
    private List<MemberList> userList;

    public CircleDetail() {
    }

    public CircleDetail(int i) {
        this.f1700id = i;
    }

    public int getId() {
        return this.f1700id;
    }

    public void setId(int i) {
        this.f1700id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String str) {
        this.logo = str;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String str) {
        this.brief = str;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public int getGroupNoticeId() {
        return this.groupNoticeId;
    }

    public void setGroupNoticeId(int i) {
        this.groupNoticeId = i;
    }

    public String getNoticeContent() {
        return this.noticeContent;
    }

    public void setNoticeContent(String str) {
        this.noticeContent = str;
    }

    public int getFollowCount() {
        return this.followCount;
    }

    public void setFollowCount(int i) {
        this.followCount = i;
    }

    public int getFollowFlag() {
        return this.followFlag;
    }

    public void setFollowFlag(int i) {
        this.followFlag = i;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String str) {
        this.backgroundImage = str;
    }

    public int getArticleCount() {
        return this.articleCount;
    }

    public void setArticleCount(int i) {
        this.articleCount = i;
    }

    public String getNoticeTime() {
        return this.noticeTime;
    }

    public void setNoticeTime(String str) {
        this.noticeTime = str;
    }

    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    public void setNoticeTitle(String str) {
        this.noticeTitle = str;
    }

    public int getOfficial() {
        return this.official;
    }

    public void setOfficial(int i) {
        this.official = i;
    }

    public PostList getTopArticle() {
        return this.topArticle;
    }

    public void setTopArticle(PostList postList) {
        this.topArticle = postList;
    }

    public List<MemberList> getUserList() {
        return this.userList;
    }

    public void setUserList(List<MemberList> list) {
        this.userList = list;
    }
}
