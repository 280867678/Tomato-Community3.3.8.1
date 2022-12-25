package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UpRecommentBean;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PostList implements Parcelable, MultiItemEntity {
    public static final Parcelable.Creator<PostList> CREATOR = new Parcelable.Creator<PostList>() { // from class: com.one.tomato.entity.PostList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public PostList mo6361createFromParcel(Parcel parcel) {
            return new PostList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public PostList[] mo6362newArray(int i) {
            return new PostList[i];
        }
    };
    public static final int POST_TYPE_AD = 9;
    private boolean alreadyPaid;
    private String articleHash;
    private String avatar;
    private int blackFlag;
    private int canDownload;
    private ArrayList<CircleDiscoverListBean> circleDiscoverListBeans;
    private int commentTimes;
    private String createTime;
    private String createTimeStr;
    private int currentLevelIndex;
    private String dayInfo;
    private String description;
    private int downPrice;
    private int favorTimes;
    private int freeLookFlag;
    private int goldPorterFlag;
    private int goodNum;
    private int groupId;
    private String groupName;

    /* renamed from: id */
    private int f1720id;
    private int ifAllFlag;
    private int insertFlag;
    private int isAttention;
    private int isFavor;
    private boolean isSelectMinePostDelete;
    private boolean isSerializeSelect;
    private boolean isShowSelectPostDelete;
    private int isThumbUp;
    private int isView;
    private int memberId;
    private boolean memberIsAnchor;
    private int memberIsOriginal;
    private boolean memberIsUp;
    private String memberSign;
    private int msgType;
    private String name;
    private int originalFlag;
    private AdPage page;
    private int payTimes;
    private int picNum;
    private ArrayList<PostHotMessageBean> postHotMessageBeans;
    private int postType;
    private int price;
    private String random;
    private double score;
    private String secImageUrl;
    private String secVideoCover;
    private String secVideoUrl;
    private ArrayList<PostList> serialGroupArticles;
    private int serialGroupId;
    private String serialGroupTitle;
    private String sex;
    private String shareTimes;
    private String size;
    private long sortNum;
    private int source;
    private int subscribeSwitch;
    private ArrayList<Tag> tagList;
    private boolean tagMistakeReport;
    private int tipFlag;
    private String title;
    private int upLevel;
    private ArrayList<UpRecommentBean> upRecommentBeans;
    private int videoCommentTimes;
    private String videoTime;
    private int videoView;
    private int viewCount;
    private int vipType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 0;
    }

    protected PostList(Parcel parcel) {
        this.f1720id = parcel.readInt();
        this.postType = parcel.readInt();
        this.title = parcel.readString();
        this.memberId = parcel.readInt();
        this.name = parcel.readString();
        this.avatar = parcel.readString();
        this.sex = parcel.readString();
        this.groupId = parcel.readInt();
        this.groupName = parcel.readString();
        this.description = parcel.readString();
        this.viewCount = parcel.readInt();
        this.goodNum = parcel.readInt();
        this.secImageUrl = parcel.readString();
        this.secVideoUrl = parcel.readString();
        this.secVideoCover = parcel.readString();
        this.articleHash = parcel.readString();
        this.random = parcel.readString();
        this.favorTimes = parcel.readInt();
        this.picNum = parcel.readInt();
        this.videoTime = parcel.readString();
        this.commentTimes = parcel.readInt();
        this.videoCommentTimes = parcel.readInt();
        this.createTime = parcel.readString();
        this.createTimeStr = parcel.readString();
        this.isFavor = parcel.readInt();
        this.isThumbUp = parcel.readInt();
        this.tipFlag = parcel.readInt();
        this.size = parcel.readString();
        this.blackFlag = parcel.readInt();
        this.isAttention = parcel.readInt();
        this.shareTimes = parcel.readString();
        this.ifAllFlag = parcel.readInt();
        this.msgType = parcel.readInt();
        boolean z = true;
        this.tagMistakeReport = parcel.readByte() != 0;
        this.page = (AdPage) parcel.readParcelable(AdPage.class.getClassLoader());
        this.tagList = parcel.createTypedArrayList(Tag.CREATOR);
        this.currentLevelIndex = parcel.readInt();
        this.score = parcel.readDouble();
        this.source = parcel.readInt();
        this.originalFlag = parcel.readInt();
        this.price = parcel.readInt();
        this.memberIsUp = parcel.readByte() != 0;
        this.alreadyPaid = parcel.readByte() != 0;
        this.memberIsAnchor = parcel.readByte() != 0;
        this.vipType = parcel.readInt();
        this.payTimes = parcel.readInt();
        this.videoView = parcel.readInt();
        this.isView = parcel.readInt();
        this.memberSign = parcel.readString();
        this.downPrice = parcel.readInt();
        this.canDownload = parcel.readInt();
        this.isSelectMinePostDelete = parcel.readByte() != 0;
        this.isShowSelectPostDelete = parcel.readByte() != 0;
        this.isSerializeSelect = parcel.readByte() == 0 ? false : z;
        this.serialGroupId = parcel.readInt();
        this.sortNum = parcel.readLong();
        this.dayInfo = parcel.readString();
        this.serialGroupTitle = parcel.readString();
        this.upLevel = parcel.readInt();
        this.goldPorterFlag = parcel.readInt();
        this.subscribeSwitch = parcel.readInt();
        this.memberIsOriginal = parcel.readInt();
        this.freeLookFlag = parcel.readInt();
        this.insertFlag = parcel.readInt();
        this.postHotMessageBeans = parcel.createTypedArrayList(PostHotMessageBean.CREATOR);
        this.serialGroupArticles = parcel.createTypedArrayList(CREATOR);
    }

    public int getFreeLookFlag() {
        return this.freeLookFlag;
    }

    public void setFreeLookFlag(int i) {
        this.freeLookFlag = i;
    }

    public int getMemberIsOriginal() {
        return this.memberIsOriginal;
    }

    public void setMemberIsOriginal(int i) {
        this.memberIsOriginal = i;
    }

    public int getSubscribeSwitch() {
        return this.subscribeSwitch;
    }

    public void setSubscribeSwitch(int i) {
        this.subscribeSwitch = i;
    }

    public int getUpLevel() {
        return this.upLevel;
    }

    public void setUpLevel(int i) {
        this.upLevel = i;
    }

    public int getGoldPorterFlag() {
        return this.goldPorterFlag;
    }

    public void setGoldPorterFlag(int i) {
        this.goldPorterFlag = i;
    }

    public ArrayList<UpRecommentBean> getUpRecommentBeans() {
        return this.upRecommentBeans;
    }

    public void setUpRecommentBeans(ArrayList<UpRecommentBean> arrayList) {
        this.upRecommentBeans = arrayList;
    }

    public ArrayList<CircleDiscoverListBean> getCircleDiscoverListBeans() {
        return this.circleDiscoverListBeans;
    }

    public void setCircleDiscoverListBeans(ArrayList<CircleDiscoverListBean> arrayList) {
        this.circleDiscoverListBeans = arrayList;
    }

    public ArrayList<PostHotMessageBean> getPostHotMessageBeans() {
        return this.postHotMessageBeans;
    }

    public void setPostHotMessageBeans(ArrayList<PostHotMessageBean> arrayList) {
        this.postHotMessageBeans = arrayList;
    }

    public boolean isSelectMinePostDelete() {
        return this.isSelectMinePostDelete;
    }

    public void setSelectMinePostDelete(boolean z) {
        this.isSelectMinePostDelete = z;
    }

    public boolean isShowSelectPostDelete() {
        return this.isShowSelectPostDelete;
    }

    public void setShowSelectPostDelete(boolean z) {
        this.isShowSelectPostDelete = z;
    }

    public ArrayList<PostList> getSerialGroupArticles() {
        return this.serialGroupArticles;
    }

    public void setSerialGroupArticles(ArrayList<PostList> arrayList) {
        this.serialGroupArticles = arrayList;
    }

    public String getSerialGroupTitle() {
        return this.serialGroupTitle;
    }

    public void setSerialGroupTitle(String str) {
        this.serialGroupTitle = str;
    }

    public int getOriginalFlag() {
        return this.originalFlag;
    }

    public void setOriginalFlag(int i) {
        this.originalFlag = i;
    }

    public long getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(long j) {
        this.sortNum = j;
    }

    public boolean isSerializeSelect() {
        return this.isSerializeSelect;
    }

    public void setSerializeSelect(boolean z) {
        this.isSerializeSelect = z;
    }

    public int getSerialGroupId() {
        return this.serialGroupId;
    }

    public void setSerialGroupId(int i) {
        this.serialGroupId = i;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int i) {
        this.source = i;
    }

    public int getDownPrice() {
        return this.downPrice;
    }

    public void setDownPrice(int i) {
        this.downPrice = i;
    }

    public int getCanDownload() {
        return this.canDownload;
    }

    public void setCanDownload(int i) {
        this.canDownload = i;
    }

    public String getMemberSign() {
        return this.memberSign;
    }

    public void setMemberSign(String str) {
        this.memberSign = str;
    }

    public int getIsView() {
        return this.isView;
    }

    public void setIsView(int i) {
        this.isView = i;
    }

    public int getPayTimes() {
        return this.payTimes;
    }

    public void setPayTimes(int i) {
        this.payTimes = i;
    }

    public int getVipType() {
        return this.vipType;
    }

    public void setVipType(int i) {
        this.vipType = i;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int i) {
        this.price = i;
    }

    public boolean isMemberIsUp() {
        return this.memberIsUp;
    }

    public void setMemberIsUp(boolean z) {
        this.memberIsUp = z;
    }

    public boolean isAlreadyPaid() {
        return this.alreadyPaid;
    }

    public void setAlreadyPaid(boolean z) {
        this.alreadyPaid = z;
    }

    public int getInsertFlag() {
        return this.insertFlag;
    }

    public void setInsertFlag(int i) {
        this.insertFlag = i;
    }

    public PostList(int i) {
        this.f1720id = i;
    }

    public int getCurrentLevelIndex() {
        return this.currentLevelIndex;
    }

    public void setCurrentLevelIndex(int i) {
        this.currentLevelIndex = i;
    }

    public String getDayInfo() {
        return this.dayInfo;
    }

    public void setDayInfo(String str) {
        this.dayInfo = str;
    }

    public String getShareTimes() {
        return this.shareTimes;
    }

    public void setShareTimes(String str) {
        this.shareTimes = str;
    }

    public int getId() {
        return this.f1720id;
    }

    public void setId(int i) {
        this.f1720id = i;
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int i) {
        this.postType = i;
    }

    public int getIsAttention() {
        return this.isAttention;
    }

    public void setIsAttention(int i) {
        this.isAttention = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public int getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(int i) {
        this.viewCount = i;
    }

    public int getVideoView() {
        return this.videoView;
    }

    public void setVideoView(int i) {
        this.videoView = i;
    }

    public int getGoodNum() {
        if (this.goodNum < 0) {
            this.goodNum = 0;
        }
        return this.goodNum;
    }

    public int getVideoCommentTimes() {
        if (this.videoCommentTimes < 0) {
            this.videoCommentTimes = 0;
        }
        return this.videoCommentTimes;
    }

    public void setVideoCommentTimes(int i) {
        this.videoCommentTimes = i;
    }

    public boolean isTagMistakeReport() {
        return this.tagMistakeReport;
    }

    public void setTagMistakeReport(boolean z) {
        this.tagMistakeReport = z;
    }

    public String getSecVideoCover() {
        return this.secVideoCover;
    }

    public void setSecVideoCover(String str) {
        this.secVideoCover = str;
    }

    public void setGoodNum(int i) {
        this.goodNum = i;
    }

    public String getSecImageUrl() {
        return this.secImageUrl;
    }

    public void setSecImageUrl(String str) {
        this.secImageUrl = str;
    }

    public String getSecVideoUrl() {
        return this.secVideoUrl;
    }

    public void setSecVideoUrl(String str) {
        this.secVideoUrl = str;
    }

    public String getRandom() {
        return this.random;
    }

    public void setRandom(String str) {
        this.random = str;
    }

    public int getFavorTimes() {
        if (this.favorTimes < 0) {
            this.favorTimes = 0;
        }
        return this.favorTimes;
    }

    public void setFavorTimes(int i) {
        this.favorTimes = i;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public int getPicNum() {
        return this.picNum;
    }

    public void setPicNum(int i) {
        this.picNum = i;
    }

    public String getVideoTime() {
        return this.videoTime;
    }

    public void setVideoTime(String str) {
        this.videoTime = str;
    }

    public int getCommentTimes() {
        if (this.commentTimes < 0) {
            this.commentTimes = 0;
        }
        return this.commentTimes;
    }

    public void setCommentTimes(int i) {
        this.commentTimes = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(String str) {
        this.createTimeStr = str;
    }

    public int getIsFavor() {
        return this.isFavor;
    }

    public void setIsFavor(int i) {
        this.isFavor = i;
    }

    public int getIsThumbUp() {
        return this.isThumbUp;
    }

    public void setIsThumbUp(int i) {
        this.isThumbUp = i;
    }

    public String getSex() {
        if (TextUtils.isEmpty(this.sex)) {
            this.sex = "1";
        }
        return this.sex;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public int getBlackFlag() {
        return this.blackFlag;
    }

    public void setBlackFlag(int i) {
        this.blackFlag = i;
    }

    public int getTipFlag() {
        return this.tipFlag;
    }

    public void setTipFlag(int i) {
        this.tipFlag = i;
    }

    public int getIfAllFlag() {
        return this.ifAllFlag;
    }

    public void setIfAllFlag(int i) {
        this.ifAllFlag = i;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int i) {
        this.msgType = i;
    }

    public AdPage getPage() {
        return this.page;
    }

    public void setPage(AdPage adPage) {
        this.page = adPage;
    }

    public ArrayList<Tag> getTagList() {
        return this.tagList;
    }

    public void setTagList(ArrayList<Tag> arrayList) {
        this.tagList = arrayList;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double d) {
        this.score = d;
    }

    public boolean isMemberIsAnchor() {
        return this.memberIsAnchor;
    }

    public void setMemberIsAnchor(boolean z) {
        this.memberIsAnchor = z;
    }

    public boolean equals(Object obj) {
        PostList postList;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof PostList) && (this == (postList = (PostList) obj) || this.f1720id == postList.getId());
    }

    public int hashCode() {
        return this.f1720id;
    }

    public boolean isAd() {
        return this.postType == 9;
    }

    public PostList() {
    }

    public String getArticleHash() {
        return this.articleHash;
    }

    public void setArticleHash(String str) {
        this.articleHash = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1720id);
        parcel.writeInt(this.postType);
        parcel.writeString(this.title);
        parcel.writeInt(this.memberId);
        parcel.writeString(this.name);
        parcel.writeString(this.avatar);
        parcel.writeString(this.sex);
        parcel.writeInt(this.groupId);
        parcel.writeString(this.groupName);
        parcel.writeString(this.description);
        parcel.writeInt(this.viewCount);
        parcel.writeInt(this.goodNum);
        parcel.writeString(this.secImageUrl);
        parcel.writeString(this.secVideoUrl);
        parcel.writeString(this.secVideoCover);
        parcel.writeString(this.articleHash);
        parcel.writeString(this.random);
        parcel.writeInt(this.favorTimes);
        parcel.writeInt(this.picNum);
        parcel.writeString(this.videoTime);
        parcel.writeInt(this.commentTimes);
        parcel.writeInt(this.videoCommentTimes);
        parcel.writeString(this.createTime);
        parcel.writeString(this.createTimeStr);
        parcel.writeInt(this.isFavor);
        parcel.writeInt(this.isThumbUp);
        parcel.writeInt(this.tipFlag);
        parcel.writeString(this.size);
        parcel.writeInt(this.blackFlag);
        parcel.writeInt(this.isAttention);
        parcel.writeString(this.shareTimes);
        parcel.writeInt(this.ifAllFlag);
        parcel.writeInt(this.msgType);
        parcel.writeByte(this.tagMistakeReport ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.page, i);
        parcel.writeTypedList(this.tagList);
        parcel.writeInt(this.currentLevelIndex);
        parcel.writeDouble(this.score);
        parcel.writeInt(this.source);
        parcel.writeInt(this.originalFlag);
        parcel.writeInt(this.price);
        parcel.writeByte(this.memberIsUp ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.alreadyPaid ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.memberIsAnchor ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.vipType);
        parcel.writeInt(this.payTimes);
        parcel.writeInt(this.videoView);
        parcel.writeInt(this.isView);
        parcel.writeString(this.memberSign);
        parcel.writeInt(this.downPrice);
        parcel.writeInt(this.canDownload);
        parcel.writeByte(this.isSelectMinePostDelete ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isShowSelectPostDelete ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isSerializeSelect ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.serialGroupId);
        parcel.writeLong(this.sortNum);
        parcel.writeString(this.dayInfo);
        parcel.writeString(this.serialGroupTitle);
        parcel.writeInt(this.upLevel);
        parcel.writeInt(this.goldPorterFlag);
        parcel.writeInt(this.subscribeSwitch);
        parcel.writeInt(this.memberIsOriginal);
        parcel.writeInt(this.freeLookFlag);
        parcel.writeInt(this.insertFlag);
        parcel.writeTypedList(this.postHotMessageBeans);
        parcel.writeTypedList(this.serialGroupArticles);
    }
}
