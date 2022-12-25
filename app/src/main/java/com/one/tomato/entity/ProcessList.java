package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class ProcessList implements Serializable {
    private Object anonymous;
    private Object articleGroup;
    private Object avatar;
    private Object begTime;
    private Object canReply;
    private Object channel;
    private Object checkAdmin;
    private Object commentTimes;
    private String createTime;
    private Object createTimeStr;
    private Object creator;
    private String description;
    private Object endTime;
    private Object favorTimes;
    private Object goodNum;
    private int groupId;
    private Object groupLogo;
    private String groupName;
    private Object hotFlag;

    /* renamed from: id */
    private int f1722id;
    private Object imageUrl;
    private Object isFavor;
    private Object isThumbUp;
    private Object lastReply;
    private Object level;
    private int managerId;
    private String managerName;
    private Object member;
    private int memberId;
    private Object name;
    private Object picNum;
    private int postType;
    private String publishTime;
    private Object recommendFlag;
    private Object sex;
    private Object size;
    private Object sort;
    private Object source;
    private Object status;
    private Object tabType;
    private Object thumbnail;
    private String title;
    private Object updateTime;
    private Object videoCover;
    private Object videoTime;
    private Object videoUrl;
    private Object viewCount;

    public ProcessList(String str, String str2) {
        this.title = str;
        this.createTime = str2;
    }

    public int getId() {
        return this.f1722id;
    }

    public void setId(int i) {
        this.f1722id = i;
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int i) {
        this.postType = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Object getName() {
        return this.name;
    }

    public void setName(Object obj) {
        this.name = obj;
    }

    public Object getAvatar() {
        return this.avatar;
    }

    public void setAvatar(Object obj) {
        this.avatar = obj;
    }

    public Object getSex() {
        return this.sex;
    }

    public void setSex(Object obj) {
        this.sex = obj;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Object getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Object obj) {
        this.viewCount = obj;
    }

    public Object getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(Object obj) {
        this.thumbnail = obj;
    }

    public Object getLastReply() {
        return this.lastReply;
    }

    public void setLastReply(Object obj) {
        this.lastReply = obj;
    }

    public Object getCanReply() {
        return this.canReply;
    }

    public void setCanReply(Object obj) {
        this.canReply = obj;
    }

    public Object getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(Object obj) {
        this.goodNum = obj;
    }

    public Object getCheckAdmin() {
        return this.checkAdmin;
    }

    public void setCheckAdmin(Object obj) {
        this.checkAdmin = obj;
    }

    public Object getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(Object obj) {
        this.imageUrl = obj;
    }

    public Object getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(Object obj) {
        this.videoUrl = obj;
    }

    public Object getVideoCover() {
        return this.videoCover;
    }

    public void setVideoCover(Object obj) {
        this.videoCover = obj;
    }

    public Object getFavorTimes() {
        return this.favorTimes;
    }

    public void setFavorTimes(Object obj) {
        this.favorTimes = obj;
    }

    public Object getChannel() {
        return this.channel;
    }

    public void setChannel(Object obj) {
        this.channel = obj;
    }

    public Object getStatus() {
        return this.status;
    }

    public void setStatus(Object obj) {
        this.status = obj;
    }

    public int getManagerId() {
        return this.managerId;
    }

    public void setManagerId(int i) {
        this.managerId = i;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public void setManagerName(String str) {
        this.managerName = str;
    }

    public Object getRecommendFlag() {
        return this.recommendFlag;
    }

    public void setRecommendFlag(Object obj) {
        this.recommendFlag = obj;
    }

    public Object getHotFlag() {
        return this.hotFlag;
    }

    public void setHotFlag(Object obj) {
        this.hotFlag = obj;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public Object getPicNum() {
        return this.picNum;
    }

    public void setPicNum(Object obj) {
        this.picNum = obj;
    }

    public Object getVideoTime() {
        return this.videoTime;
    }

    public void setVideoTime(Object obj) {
        this.videoTime = obj;
    }

    public Object getCommentTimes() {
        return this.commentTimes;
    }

    public void setCommentTimes(Object obj) {
        this.commentTimes = obj;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(String str) {
        this.publishTime = str;
    }

    public Object getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Object obj) {
        this.updateTime = obj;
    }

    public Object getCreator() {
        return this.creator;
    }

    public void setCreator(Object obj) {
        this.creator = obj;
    }

    public Object getMember() {
        return this.member;
    }

    public void setMember(Object obj) {
        this.member = obj;
    }

    public Object getArticleGroup() {
        return this.articleGroup;
    }

    public void setArticleGroup(Object obj) {
        this.articleGroup = obj;
    }

    public Object getSort() {
        return this.sort;
    }

    public void setSort(Object obj) {
        this.sort = obj;
    }

    public Object getBegTime() {
        return this.begTime;
    }

    public void setBegTime(Object obj) {
        this.begTime = obj;
    }

    public Object getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Object obj) {
        this.endTime = obj;
    }

    public Object getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(Object obj) {
        this.anonymous = obj;
    }

    public Object getSource() {
        return this.source;
    }

    public void setSource(Object obj) {
        this.source = obj;
    }

    public Object getLevel() {
        return this.level;
    }

    public void setLevel(Object obj) {
        this.level = obj;
    }

    public Object getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(Object obj) {
        this.createTimeStr = obj;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public Object getGroupLogo() {
        return this.groupLogo;
    }

    public void setGroupLogo(Object obj) {
        this.groupLogo = obj;
    }

    public Object getSize() {
        return this.size;
    }

    public void setSize(Object obj) {
        this.size = obj;
    }

    public Object getIsThumbUp() {
        return this.isThumbUp;
    }

    public void setIsThumbUp(Object obj) {
        this.isThumbUp = obj;
    }

    public Object getIsFavor() {
        return this.isFavor;
    }

    public void setIsFavor(Object obj) {
        this.isFavor = obj;
    }

    public Object getTabType() {
        return this.tabType;
    }

    public void setTabType(Object obj) {
        this.tabType = obj;
    }
}
