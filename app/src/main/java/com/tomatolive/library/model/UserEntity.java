package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class UserEntity extends BaseUserEntity implements Comparable<UserEntity>, Parcelable {
    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() { // from class: com.tomatolive.library.model.UserEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public UserEntity mo6592createFromParcel(Parcel parcel) {
            return new UserEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public UserEntity[] mo6593newArray(int i) {
            return new UserEntity[i];
        }
    };
    private List<String> followTargetIds;
    private String inBanGroup;
    private String isBanPost;
    private String isNewUser;
    private String isRisk;
    private String isSuperAdmin;
    private String liveAdminBanPost;
    private List<String> markUrls;
    private String offlinePrivateMessageFlag;
    private String partDrawNumber;
    private List<String> shieldTargetIds;
    private String staySeconds;
    private String token;
    private String type;

    @Override // com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<String> getMarkUrls() {
        return this.markUrls;
    }

    public void setMarkUrls(List<String> list) {
        this.markUrls = list;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String str) {
        this.userRole = str;
    }

    public int getWeight() {
        if (getNobilityType() == -1) {
            this.nobilityType = 0;
        }
        return (getNobilityType() * ConstantUtils.MAX_ITEM_NUM) + (getGuardType() * 1000) + NumberUtils.string2int(this.expGrade);
    }

    public int getGuardType() {
        return NumberUtils.string2int(this.guardType);
    }

    public void setGuardType(int i) {
        this.guardType = String.valueOf(i);
    }

    public UserEntity() {
        this.isRisk = "";
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String str) {
        this.openId = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getExpGrade() {
        return this.expGrade;
    }

    public void setExpGrade(String str) {
        this.expGrade = str;
    }

    public int getNobilityType() {
        return this.nobilityType;
    }

    public void setNobilityType(int i) {
        this.nobilityType = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getLiveStatus() {
        return this.liveStatus;
    }

    public void setLiveStatus(String str) {
        this.liveStatus = str;
    }

    public boolean isVisitorUser() {
        return TextUtils.equals(this.type, "2");
    }

    public boolean isInBanGroup() {
        return TextUtils.equals(this.inBanGroup, "1");
    }

    public List<String> getFollowTargetIds() {
        return this.followTargetIds;
    }

    public void setFollowTargetIds(List<String> list) {
        this.followTargetIds = list;
    }

    public String getLiveStaySeconds() {
        return this.staySeconds;
    }

    public String getLiveId() {
        return this.liveId;
    }

    public void setLiveId(String str) {
        this.liveId = str;
    }

    public List<String> getShieldTargetIds() {
        return this.shieldTargetIds;
    }

    public void setShieldTargetIds(List<String> list) {
        this.shieldTargetIds = list;
    }

    public boolean isSuperBanPost() {
        return TextUtils.equals(this.liveAdminBanPost, "1");
    }

    public boolean isBanPostBoolean() {
        return TextUtils.equals(this.isBanPost, "1");
    }

    public boolean isSuperAdmin() {
        return TextUtils.equals(this.isSuperAdmin, "1");
    }

    public String getIsRisk() {
        return this.isRisk;
    }

    public void setIsRisk(String str) {
        this.isRisk = str;
    }

    public boolean isNewUserBoolean() {
        return TextUtils.equals(this.isNewUser, "1");
    }

    public int getPartHdLotteryCount() {
        if (TextUtils.isEmpty(this.partDrawNumber)) {
            return 0;
        }
        return NumberUtils.string2int(this.partDrawNumber);
    }

    public boolean isOfflinePrivateMsg() {
        return TextUtils.equals(this.offlinePrivateMessageFlag, "1");
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull UserEntity userEntity) {
        return getWeight() > userEntity.getWeight() ? -1 : 1;
    }

    public String toString() {
        return "UserEntity{userId='" + this.userId + "', nickname='" + this.nickname + "', role='" + this.role + "', guardType='" + this.guardType + "', nobilityType=" + this.nobilityType + '}';
    }

    @Override // com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.liveAdminBanPost);
        parcel.writeString(this.type);
        parcel.writeString(this.token);
        parcel.writeString(this.inBanGroup);
        parcel.writeString(this.isBanPost);
        parcel.writeString(this.staySeconds);
        parcel.writeString(this.isSuperAdmin);
        parcel.writeString(this.isRisk);
        parcel.writeStringList(this.followTargetIds);
        parcel.writeStringList(this.shieldTargetIds);
        parcel.writeStringList(this.markUrls);
        parcel.writeString(this.isNewUser);
        parcel.writeString(this.offlinePrivateMessageFlag);
        parcel.writeString(this.partDrawNumber);
    }

    protected UserEntity(Parcel parcel) {
        super(parcel);
        this.isRisk = "";
        this.liveAdminBanPost = parcel.readString();
        this.type = parcel.readString();
        this.token = parcel.readString();
        this.inBanGroup = parcel.readString();
        this.isBanPost = parcel.readString();
        this.staySeconds = parcel.readString();
        this.isSuperAdmin = parcel.readString();
        this.isRisk = parcel.readString();
        this.followTargetIds = parcel.createStringArrayList();
        this.shieldTargetIds = parcel.createStringArrayList();
        this.markUrls = parcel.createStringArrayList();
        this.isNewUser = parcel.readString();
        this.offlinePrivateMessageFlag = parcel.readString();
        this.partDrawNumber = parcel.readString();
    }
}
