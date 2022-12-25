package com.tomatolive.library.model;

import android.text.TextUtils;
import com.blankj.utilcode.util.FileUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class GiftDownloadItemEntity extends BaseGiftBackpackEntity {
    public int active_time;
    public String anchorName;
    public String animalUrl;
    public String avatar;
    public String boxId;
    public String boxName;
    public String boxType;
    public String broadcastRange;
    public String caption;
    public int duration;
    public int effect_type;
    public List<GiftBatchItemEntity> giftBatchItemList;
    public String giftCostType;
    public String giftDirPath;
    public String imgurl;
    public String isBroadcast;
    public String isStarGift;
    public boolean isStayTuned;
    public String name;
    public String onlineDefaultUrl;
    public String price;
    public String userName;

    public GiftBatchItemEntity getGiftBatchByNum(String str) {
        List<GiftBatchItemEntity> list = this.giftBatchItemList;
        if (list != null) {
            for (GiftBatchItemEntity giftBatchItemEntity : list) {
                if (TextUtils.equals(str, String.valueOf(giftBatchItemEntity.num))) {
                    return giftBatchItemEntity;
                }
            }
        }
        return null;
    }

    public GiftDownloadItemEntity() {
        this.giftDirPath = "";
        this.name = "";
        this.imgurl = "";
        this.price = "";
        this.isStayTuned = false;
    }

    public GiftDownloadItemEntity(String str, String str2, boolean z) {
        this.giftDirPath = "";
        this.name = "";
        this.imgurl = "";
        this.price = "";
        this.isStayTuned = false;
        this.name = str;
        this.price = str2;
        this.isStayTuned = z;
    }

    public boolean isBigAnim() {
        return this.effect_type == 2;
    }

    public boolean isBroadcastFlag() {
        return TextUtils.equals(this.isBroadcast, "1") && TextUtils.equals(this.broadcastRange, "3");
    }

    public boolean isBatchGiftFlag() {
        List<GiftBatchItemEntity> list = this.giftBatchItemList;
        return list != null && !list.isEmpty();
    }

    public boolean isLuckyGift() {
        return TextUtils.equals(this.boxType, "3");
    }

    public boolean isScoreGift() {
        return TextUtils.equals(this.giftCostType, "2");
    }

    public String getLocalDirName() {
        return FileUtils.getFileNameNoExtension(this.animalUrl);
    }

    public String toString() {
        return "GiftDownloadItemEntity{giftDirPath='" + this.giftDirPath + "', id='" + this.markId + "', name='" + this.name + "', animalUrl='" + this.animalUrl + "'}";
    }

    public boolean isCurWeekStarGift() {
        return TextUtils.equals(this.isStarGift, "1");
    }

    public boolean isLastWeekStarGift() {
        return !TextUtils.isEmpty(this.anchorName) && !TextUtils.isEmpty(this.userName);
    }
}
