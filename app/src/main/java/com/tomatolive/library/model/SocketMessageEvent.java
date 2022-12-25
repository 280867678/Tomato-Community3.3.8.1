package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class SocketMessageEvent {
    public String code;
    public String message;
    public String messageType;
    public ResultData resultData;

    public String toString() {
        return "SocketMessageEvent{code='" + this.code + "', message='" + this.message + "', messageType='" + this.messageType + "', resultData=" + this.resultData + '}';
    }

    /* loaded from: classes3.dex */
    public static class ResultData {
        public String action;
        public String activeTime;
        public String afterGrade;
        public List<String> anchorAAssists;
        public String anchorAFP;
        public String anchorALiveId;
        public String anchorAPopularity;
        public List<String> anchorBAssists;
        public String anchorBFP;
        public String anchorBLiveId;
        public String anchorBPopularity;
        public String anchorGuardCount;
        public String anchorId;
        public String anchorName;
        public String anchorNewRank;
        public String animalType;
        public String animalUrl;
        public String appId;
        public String avatar;
        public String banPostAllStatus;
        public String banPostStatus;
        public int boomMultiple;
        public int boomRemainTime;
        public int boomTotalTime;
        public String boxType;
        public String carIcon;
        public String carId;
        public String carName;
        public String carOnlineUrl;
        public String changeType;
        public String changeValue;
        public String chargeGiftNum;
        public String chargePrice;
        public String chargeType;
        public String clickEvent;
        public String content;
        public String coverUrl;
        public String createTime;
        public String duration;
        public String expGrade;
        public String expirationTime;
        public String forwardLiveId;
        public String forwardText;

        /* renamed from: fp */
        public String f5846fp;
        public String freeWatchTime;
        public String fromUserName;
        public String gameId;
        public String giftBoxUniqueCode;
        public String giftCostType;
        public String giftMarkId;
        public String giftName;
        public String giftUrl;
        public String icon;

        /* renamed from: id */
        public String f5847id;
        public List<QMInteractTaskEntity> intimateTaskChargeList;
        public int isAnonymousRecommend;
        public String isDisPlay;
        public int isEnterHide;
        public String isNeedPaid;
        public String isPlayCarAnim;
        public String isPlayNobilityEnterAnimation;
        public String isWeekStar;
        public String joinNum;
        public LiveEndEntity lastLiveData;
        public String linkText;
        public String liveCount;
        public String liveDrawRecordId;
        public String liveId;
        public String markId;
        public List<String> markUrls;
        public String matcherAvatar;
        public String matcherLiveId;
        public String matcherUserId;
        public String matcherUserName;
        public String messageId;
        public String nobilityGoldFrozenStatus;
        public String nobilityPrice;
        public String offlineFlag;
        public String openDanmu;
        public String openId;
        public String openNobilityDanmu;
        public String openTime;
        public String pkCountDownTime;
        public String pkDuration;
        public String postIntervalTimes;
        public String presenterAvatar;
        public String presenterId;
        public String presenterName;
        public String price;
        public String privateMsg;
        public String prizeName;
        public String prizeNum;
        public String propId;
        public String propName;
        public String propNum;
        public String propUrl;
        public String pullStreamUrl;
        public String punishCountDownTime;
        public String putAvatar;
        public String putName;
        public String putUserId;
        public String recommendTime;
        public int region1ShowNotify;
        public int region2ShowNotify;
        public int region3ShowNotify;
        public int region4ShowNotify;
        public String remainTime;
        public String role;
        public String scope;
        public String sendType;
        public String senderId;
        public String sex;
        public String status;
        public String sysNoticeType;
        public String targetAvatar;
        public String targetId;
        public String targetName;
        public String targetUserName;
        public String taskId;
        public String taskName;
        public String taskRecordId;
        public String taskType;
        public String text;
        public String tipsText;
        public String token;
        public String trumpetId;
        public int turntableType;
        public String type;
        public String typeMsg;
        public String url;
        public String userId;
        public List<UserPrivateMessageEntity> userPrivateMessageDetailsDTOList;
        public String userRole;
        public String giftNum = "1";
        public String userName = "";
        public String guardType = "";
        public int nobilityType = -1;
        public String drawWay = "";
        public String propCount = "1";

        public boolean isQMTaskGift() {
            return TextUtils.equals(this.sendType, "2");
        }

        public String getAccountBalance() {
            if (TextUtils.equals(this.nobilityGoldFrozenStatus, "1")) {
                return this.price;
            }
            return String.valueOf(NumberUtils.string2long(this.price) + NumberUtils.string2long(this.nobilityPrice));
        }

        public boolean isTopAnimRegionShowNotify() {
            return this.region1ShowNotify == 1;
        }

        public boolean isLeftAnimRegionShowNotify() {
            return this.region2ShowNotify == 1;
        }

        public boolean isBigAnimRegionShowNotify() {
            return this.region3ShowNotify == 1;
        }

        public boolean isChatRegionShowNotify() {
            return this.region4ShowNotify == 1;
        }

        public boolean isAnonymousRecommendBoolean() {
            return this.isAnonymousRecommend == 1;
        }

        public boolean isEnterHideBoolean() {
            return this.isEnterHide == 1;
        }

        public boolean isLuckyGift() {
            return TextUtils.equals(this.boxType, "3");
        }

        public boolean isOnPlayCarAnim() {
            return TextUtils.equals(this.isPlayCarAnim, "1");
        }

        public boolean isPlayNobilityEnterAnim() {
            return TextUtils.equals(this.isPlayNobilityEnterAnimation, "1");
        }

        public boolean isOpenGuardDanmu() {
            return TextUtils.equals(this.openDanmu, "1");
        }

        public boolean isOpenNobilityDanmu() {
            return TextUtils.equals(this.openNobilityDanmu, "1");
        }

        public boolean isEnterGuardType() {
            return !TextUtils.isEmpty(this.guardType) && NumberUtils.string2int(this.guardType) > NumberUtils.string2int("0");
        }

        public boolean isSomeoneBanPost() {
            return TextUtils.equals(this.banPostStatus, "1");
        }

        public boolean isBanAll() {
            return TextUtils.equals(this.banPostAllStatus, "1");
        }

        public boolean isManager() {
            return TextUtils.equals(this.action, "1");
        }

        public boolean isHighNobility() {
            return this.nobilityType > 4;
        }

        public boolean isScoreGift() {
            return TextUtils.equals(this.giftCostType, "2");
        }

        public boolean isPrivateMsg() {
            return TextUtils.equals(this.privateMsg, "1");
        }

        public boolean isPriceProps() {
            return NumberUtils.string2long(this.price, 0L) > 0;
        }

        public boolean isUnFreeze() {
            return TextUtils.equals("2", this.action);
        }

        public boolean isOfflinePrivateMsgFlag() {
            return TextUtils.equals(this.offlineFlag, "1");
        }

        public boolean isNeedBuyTicket() {
            return TextUtils.equals("1", this.isNeedPaid);
        }

        public boolean isDisPlayWindow() {
            return TextUtils.equals("1", this.isDisPlay);
        }

        public boolean isShowQMInviteChatMsg() {
            return isDisPlayWindow() && TextUtils.equals(this.taskType, "2");
        }
    }
}
