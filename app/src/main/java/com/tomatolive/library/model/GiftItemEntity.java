package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.FileUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class GiftItemEntity extends GiftDownloadItemEntity {
    public int activeTime;
    public String anchorId;
    public int animalType;
    public String animalUrl;
    public String appId;
    public int bigAnimType = ConstantUtils.BIG_ANIM_GIFT_TYPE;
    public String clientIp;
    public String expGrade;
    public int guardType;
    public String liveCount;
    public String liveId;
    public List<String> marks;
    public int nobilityType;
    public String openId;
    public String role;
    public int sendIndex;
    public String sendUserName;
    public String sex;
    public String userId;
    public String userRole;
    public String weekStar;

    public boolean isBigProp() {
        return this.animalType == 2;
    }

    public boolean isGiftBigAnimType() {
        return this.bigAnimType == 2304;
    }

    public boolean isPropBigAnimType() {
        return this.bigAnimType == 2305;
    }

    public boolean isUpdateGiftAnimRes() {
        return isGiftBigAnimType() && !isBigAnim() && isSendSingleGift() && !FileUtils.isFile(AppUtils.getLocalGiftFilePath(this.giftDirPath));
    }

    public boolean isSendSingleGift() {
        return TextUtils.equals(this.giftNum, "1");
    }

    @Override // com.tomatolive.library.model.GiftDownloadItemEntity
    public String toString() {
        return "GiftItemEntity{giftDirPath = " + this.giftDirPath + "', markId='" + this.markId + "', name='" + this.name + "', animalUrl='" + this.animalUrl + "'}";
    }

    @Override // com.tomatolive.library.model.GiftDownloadItemEntity
    public String getLocalDirName() {
        return com.blankj.utilcode.util.FileUtils.getFileNameNoExtension(this.animalUrl);
    }
}
