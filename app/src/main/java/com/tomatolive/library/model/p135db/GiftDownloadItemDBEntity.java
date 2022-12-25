package com.tomatolive.library.model.p135db;

import android.text.TextUtils;

/* renamed from: com.tomatolive.library.model.db.GiftDownloadItemDBEntity */
/* loaded from: classes3.dex */
public class GiftDownloadItemDBEntity extends BaseDBEntity {
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
    public String giftBatchJson;
    public String giftCostType;
    public String isBroadcast;
    public String isStarGift;
    public String markId;
    public String userName;
    public String giftDirPath = "";
    public String typeid = "";
    public String name = "";
    public String imgurl = "";
    public String price = "";
    public int num = 1;
    public int downloadStatus = 0;

    public boolean isLuckyGift() {
        return TextUtils.equals(this.boxType, "3");
    }

    public boolean isScoreGift() {
        return TextUtils.equals(this.giftCostType, "2");
    }

    public String toString() {
        return "GiftDownloadItemDBEntity{markId='" + this.markId + "', giftDirPath='" + this.giftDirPath + "', name='" + this.name + "'}";
    }
}
