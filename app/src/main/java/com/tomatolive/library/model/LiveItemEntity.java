package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class LiveItemEntity {
    public String anchorContribution;
    public String anchorGuardCount;
    public String banPostAllStatus;
    public String banPostStatus;
    public String banPostTimeLeft;
    public String drawStatus;
    public String drawType;
    public String giftImg;
    public String giftMarkId;
    public String giftName;
    public String giftPrice;
    public String joinNum;
    public String lianmaiTargetAnchorAvatar;
    public String lianmaiTargetAnchorId;
    public String lianmaiTargetAnchorName;
    public String lianmaiTargetLiveId;
    public String liveCount;
    public String liveDrawRecordId;
    public String liveDrawTimeRemain;
    public String onlineUserCount;
    public String pkPunishTime;
    public String pkTimeRemain;
    public String postIntervalTimes;
    public String prizeName;
    public String pullStreamUrl;
    public String speakLevel;
    public String topic;
    public String vipCount;
    public String warnStatus;
    public String wsAddress;
    public String ticketPrice = "0";
    public String lianmaiStatus = "0";
    public String prizeNum = "0";
    public String liveDrawScope = "";

    public boolean isEnableHdLottery() {
        return TextUtils.equals("1", this.drawStatus) || TextUtils.equals("2", this.drawStatus) || TextUtils.equals("3", this.drawStatus);
    }

    public boolean isHdLotterySuccessToast(String str) {
        return isEnableHdLottery() && TextUtils.equals(this.giftMarkId, str);
    }

    public boolean isBanAll() {
        return TextUtils.equals(this.banPostAllStatus, "1");
    }

    public String getDefPullStreamUrlStr() {
        List<String> pullStreamUrlList = getPullStreamUrlList();
        if (pullStreamUrlList.isEmpty()) {
            return "";
        }
        for (String str : pullStreamUrlList) {
            if (str.startsWith("rtmp://")) {
                return str;
            }
        }
        return pullStreamUrlList.get(0);
    }

    public List<String> getPullStreamUrlList() {
        if (TextUtils.isEmpty(this.pullStreamUrl)) {
            return new ArrayList();
        }
        return Arrays.asList(this.pullStreamUrl.split(","));
    }

    public boolean isBanStatus() {
        return TextUtils.equals(this.banPostStatus, "1");
    }

    public boolean isPKLiveRoom() {
        return TextUtils.equals(this.lianmaiStatus, "1") || TextUtils.equals(this.lianmaiStatus, "2");
    }

    public boolean isPKStart() {
        return TextUtils.equals(this.lianmaiStatus, "2");
    }

    public boolean isPKEnd() {
        return isPKStart() && NumberUtils.string2long(this.pkTimeRemain) <= 0;
    }
}
