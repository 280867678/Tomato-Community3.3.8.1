package com.tomatolive.library.utils;

import android.location.Address;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.PropertyBuilder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataDynamicSuperProperties;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.p135db.AnchorLiveDataEntity;
import com.tomatolive.library.model.p135db.LiveDataEntity;
import com.tomatolive.library.utils.live.LiveDataTimer;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class LogEventUtils {
    private static final String ATTENTION_NO = "0";
    private static final String ATTENTION_YES = "1";
    public static volatile List<String> eventName;

    private LogEventUtils() {
    }

    private static boolean isEnable(String str) {
        return eventName != null && eventName.contains(str);
    }

    public static boolean intercept(String str) {
        return !isReport() || !isEnable(str);
    }

    public static void uploadStartLive(String str, String str2, String str3) {
        if (intercept(LogConstants.START_LIVE_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.START_LIVE_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, UserInfoManager.getInstance().getAppOpenId()).append(LogConstants.ANCHOR_LABEL, str).append(LogConstants.ANCHOR_LEVEL, str2).append(LogConstants.ANCHOR_APP_ID, UserInfoManager.getInstance().getAppId()).append(LogConstants.ANCHOR_NICK_NAME, UserInfoManager.getInstance().getUserNickname()).append("liveId", str3).toJSONObject());
    }

    public static void uploadLiveListDuration(String str, String str2) {
        if (!intercept(LogConstants.LIVE_LIST_DURATION_EVENT_NAME) && !TextUtils.isEmpty(str)) {
            SensorsDataAPI.sharedInstance().trackTimerEnd(str, PropertyBuilder.newInstance().append("type", str2).toJSONObject());
        }
    }

    public static String startLiveListDuration() {
        if (intercept(LogConstants.LIVE_LIST_DURATION_EVENT_NAME)) {
            return null;
        }
        return SensorsDataAPI.sharedInstance().trackTimerStart(LogConstants.LIVE_LIST_DURATION_EVENT_NAME);
    }

    public static void uploadSearchResultEvent(String str, String str2, String str3, String str4) {
        if (intercept(LogConstants.SEARCH_RESULT_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.SEARCH_RESULT_CLICK_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, str).append(LogConstants.ANCHOR_APP_ID, str2).append(LogConstants.ANCHOR_NICK_NAME, str3).append(LogConstants.KEY_WORD, str4).toJSONObject());
    }

    public static void uploadRechargeClick(String str) {
        if (intercept(LogConstants.RECHARGE_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.RECHARGE_CLICK_EVENT_NAME, PropertyBuilder.newInstance().append("entrance", str).toJSONObject());
    }

    public static void uploadBarrageSend(AnchorEntity anchorEntity, int i, String str) {
        if (!intercept(LogConstants.BARRAGE_SEND_EVENT_NAME) && anchorEntity != null) {
            SensorsDataAPI.sharedInstance().track(LogConstants.BARRAGE_SEND_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorEntity.openId).append(LogConstants.ANCHOR_APP_ID, anchorEntity.appId).append(LogConstants.ANCHOR_LABEL, anchorEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorEntity.nickname).append(LogConstants.BARRAGE_TYPE, String.valueOf(i)).append(LogConstants.IS_FOLLOW_ANCHOR, DBUtils.isAttentionAnchor(anchorEntity.userId) ? "1" : "0").append("liveId", anchorEntity.liveId).append(LogConstants.VIEWER_LEVEL, str).toJSONObject());
        }
    }

    public static void uploadSendGift(AnchorEntity anchorEntity, GiftItemEntity giftItemEntity, String str, String str2, String str3) {
        String valueOf;
        if (intercept(LogConstants.GIFT_SEND_EVENT_NAME) || anchorEntity == null || giftItemEntity == null) {
            return;
        }
        String str4 = "0";
        if (giftItemEntity.isScoreGift()) {
            valueOf = str4;
        } else {
            str4 = formatPrice(giftItemEntity.price);
            valueOf = String.valueOf(NumberUtils.string2int(str4) * 10);
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.GIFT_SEND_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorEntity.openId).append(LogConstants.ANCHOR_APP_ID, anchorEntity.appId).append(LogConstants.ANCHOR_LABEL, anchorEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorEntity.nickname).append("cost", str4).append("exp", valueOf).append(LogConstants.GIFT_NAME, giftItemEntity.name).append(LogConstants.GIFT_TYPE, giftItemEntity.isScoreGift() ? "1" : "2").append(LogConstants.ANIMATION_TYPE, String.valueOf(giftItemEntity.effect_type)).append("giftNumber", str).append("liveId", str2).append(LogConstants.VIEWER_LEVEL, str3).toJSONObject());
    }

    public static void uploadSendProp(AnchorEntity anchorEntity, GiftItemEntity giftItemEntity, String str, String str2, String str3) {
        if (intercept(LogConstants.PROP_SEND_EVENT_NAME) || anchorEntity == null || giftItemEntity == null) {
            return;
        }
        boolean z = NumberUtils.string2int(giftItemEntity.price) > 0;
        SensorsDataAPI sharedInstance = SensorsDataAPI.sharedInstance();
        String str4 = "0";
        PropertyBuilder append = PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorEntity.openId).append(LogConstants.ANCHOR_APP_ID, anchorEntity.appId).append(LogConstants.ANCHOR_LABEL, anchorEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorEntity.nickname).append("cost", z ? formatPrice(giftItemEntity.price) : str4).append(LogConstants.PROP_NAME, giftItemEntity.name);
        if (z) {
            str4 = "1";
        }
        sharedInstance.track(LogConstants.PROP_SEND_EVENT_NAME, append.append(LogConstants.PROP_TYPE, str4).append("giftNumber", str).append("liveId", str2).append(LogConstants.VIEWER_LEVEL, str3).toJSONObject());
    }

    public static void uploadGetCodeResult(String str, int i) {
        if (intercept(LogConstants.GET_CODE_RESULT_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.GET_CODE_RESULT_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.IS_SUCCESS, String.valueOf(i)).append("phone", str).append(LogConstants.SERVICE_TYPE, SystemUtils.getResString(R$string.fq_anchor_identy)).toJSONObject());
    }

    public static void uploadSendSearchRequest(boolean z, boolean z2, boolean z3, String str) {
        if (intercept(LogConstants.SEND_SEARCH_REQUEST_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI sharedInstance = SensorsDataAPI.sharedInstance();
        String str2 = "1";
        PropertyBuilder append = PropertyBuilder.newInstance().append(LogConstants.HAS_RESULT, z ? str2 : "0").append(LogConstants.IS_HISTORY_WORD_USED, z2 ? str2 : "0");
        if (!z3) {
            str2 = "0";
        }
        sharedInstance.track(LogConstants.SEND_SEARCH_REQUEST_EVENT_NAME, append.append(LogConstants.IS_RECOMMEND_WORD_USED, str2).append(LogConstants.KEY_WORD, str).toJSONObject());
    }

    public static void uploadGiftButtonClick(String str, String str2, String str3, String str4, String str5) {
        if (intercept(LogConstants.GIFT_BUTTON_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.GIFT_BUTTON_CLICK_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, str).append(LogConstants.ANCHOR_APP_ID, str2).append(LogConstants.ANCHOR_NICK_NAME, str3).append("liveId", str4).append(LogConstants.VIEWER_LEVEL, str5).toJSONObject());
    }

    public static void uploadSearchButtonClick() {
        if (intercept(LogConstants.SEARCH_BUTTON_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.SEARCH_BUTTON_CLICK_EVENT_NAME);
    }

    public static void uploadSearchColumClick() {
        if (intercept(LogConstants.SEARCH_COLUM_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.SEARCH_COLUM_CLICK_EVENT_NAME);
    }

    public static void uploadLoginButtonClick() {
        if (intercept(LogConstants.LOGIN_BUTTON_CLICK_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.LOGIN_BUTTON_CLICK_EVENT_NAME);
    }

    public static void uploadStartRealNameAuthentication(String str) {
        if (intercept(LogConstants.START_REALNAME_AUTHENTICATION_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.START_REALNAME_AUTHENTICATION_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.RESULTS_SUBMITTED, str).toJSONObject());
    }

    public static void uploadFollow(String str, String str2, String str3, String str4, String str5, String str6, boolean z, String str7) {
        if (intercept(LogConstants.FOLLOW_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.FOLLOW_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, str).append(LogConstants.ANCHOR_APP_ID, str2).append(LogConstants.ANCHOR_LABEL, str3).append(LogConstants.ANCHOR_LEVEL, str4).append(LogConstants.ANCHOR_NICK_NAME, str5).append("entrance", str6).append(LogConstants.FOLLOW_OPERATION_TYPE, z ? "1" : "0").append("liveId", str7).toJSONObject());
    }

    public static void uploadLeaveRoom(AnchorEntity anchorEntity, String str, String str2, String str3) {
        if (!intercept(LogConstants.LEAVE_ROOM_EVENT_NAME) && anchorEntity != null) {
            SensorsDataAPI.sharedInstance().track(LogConstants.LEAVE_ROOM_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorEntity.openId).append(LogConstants.ANCHOR_LABEL, anchorEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorEntity.nickname).append(LogConstants.ANCHOR_APP_ID, anchorEntity.appId).append("duration", str).append(LogConstants.IS_FOLLOW_ANCHOR, DBUtils.isAttentionAnchor(anchorEntity.userId) ? "1" : "0").append("liveId", str2).append(LogConstants.VIEWER_LEVEL, str3).toJSONObject());
        }
    }

    public static void uploadLeaveRoom(LiveDataEntity liveDataEntity) {
        if (!intercept(LogConstants.LEAVE_ROOM_EVENT_NAME) && liveDataEntity != null) {
            long j = ((liveDataEntity.endTime - liveDataEntity.startTime) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
            if (j < 1) {
                return;
            }
            SensorsDataAPI.sharedInstance().track(LogConstants.LEAVE_ROOM_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, liveDataEntity.anchorId).append(LogConstants.ANCHOR_LABEL, liveDataEntity.tag).append(LogConstants.ANCHOR_LEVEL, liveDataEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, liveDataEntity.nickname).append(LogConstants.ANCHOR_APP_ID, liveDataEntity.appId).append("duration", Long.valueOf(j)).append(LogConstants.IS_FOLLOW_ANCHOR, DBUtils.isAttentionAnchor(liveDataEntity.anchorId) ? "1" : "0").append("liveId", liveDataEntity.liveId).append(LogConstants.VIEWER_LEVEL, liveDataEntity.viewerLevel).toJSONObject());
        }
    }

    public static void uploadRegisterSuccess(String str, String str2, int i) {
        if (intercept(LogConstants.REGISTER_SUCCESS_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.REGISTER_SUCCESS_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ACCOUNT, str).append(LogConstants.REGISTER_METHOD, str2).append(LogConstants.IS_SUCCESS, String.valueOf(i)).toJSONObject());
    }

    public static void uploadLoginSuccess(String str, boolean z) {
        if (intercept(LogConstants.LOGIN_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.LOGIN_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ACCOUNT, str).append(LogConstants.IS_SUCCESS, z ? "1" : "0").toJSONObject());
    }

    public static void uploadEndLive(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (intercept(LogConstants.END_LIVE_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.END_LIVE_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, UserInfoManager.getInstance().getAppOpenId()).append(LogConstants.ANCHOR_APP_ID, UserInfoManager.getInstance().getAppId()).append(LogConstants.ANCHOR_NICK_NAME, UserInfoManager.getInstance().getUserNickname()).append(LogConstants.ANCHOR_LABEL, str).append(LogConstants.ANCHOR_LEVEL, str2).append(LogConstants.COIN_NUM, formatPrice(str3)).append(LogConstants.BARRAGE_NUM, str4).append("duration", str5).append(LogConstants.VIEWER_NUM, str7).append("liveId", str6).toJSONObject());
    }

    public static void uploadEndLive(AnchorLiveDataEntity anchorLiveDataEntity) {
        if (!intercept(LogConstants.END_LIVE_EVENT_NAME) && anchorLiveDataEntity != null) {
            long j = ((anchorLiveDataEntity.endTime - anchorLiveDataEntity.startTime) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
            if (j < 1) {
                return;
            }
            SensorsDataAPI.sharedInstance().track(LogConstants.END_LIVE_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorLiveDataEntity.anchorId).append(LogConstants.ANCHOR_APP_ID, anchorLiveDataEntity.appId).append(LogConstants.ANCHOR_LABEL, anchorLiveDataEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorLiveDataEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorLiveDataEntity.nickname).append(LogConstants.COIN_NUM, formatPrice(anchorLiveDataEntity.coinNum)).append(LogConstants.BARRAGE_NUM, anchorLiveDataEntity.barrageNum).append("duration", String.valueOf(j)).append(LogConstants.VIEWER_NUM, anchorLiveDataEntity.viewerCount).append("liveId", anchorLiveDataEntity.liveId).toJSONObject());
        }
    }

    public static void addAddressProperties(final Address address) {
        if (!isReport()) {
            return;
        }
        SensorsDataAPI.sharedInstance().registerDynamicSuperProperties(new SensorsDataDynamicSuperProperties() { // from class: com.tomatolive.library.utils.-$$Lambda$LogEventUtils$iKH9QLcpJ2XQmx_MxjTpZ_GvkV4
            @Override // com.sensorsdata.analytics.android.sdk.SensorsDataDynamicSuperProperties
            public final JSONObject getDynamicSuperProperties() {
                JSONObject jSONObject;
                jSONObject = PropertyBuilder.newInstance().append(AopConstants.COUNTRY, r0.getCountryName()).append(AopConstants.PROVINCE, r0.getAdminArea()).append(AopConstants.CITY, address.getLocality()).toJSONObject();
                return jSONObject;
            }
        });
    }

    public static void uploadInRoom(AnchorEntity anchorEntity, String str, String str2, String str3) {
        if (!intercept(LogConstants.INROOM_EVENT_NAME) && anchorEntity != null) {
            SensorsDataAPI.sharedInstance().track(LogConstants.INROOM_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.ANCHOR_ID, anchorEntity.openId).append(LogConstants.ANCHOR_APP_ID, anchorEntity.appId).append(LogConstants.ANCHOR_LABEL, anchorEntity.tag).append(LogConstants.ANCHOR_LEVEL, anchorEntity.expGrade).append(LogConstants.ANCHOR_NICK_NAME, anchorEntity.nickname).append(LogConstants.ENTER_SOURCE, str3).append(LogConstants.IS_FOLLOW_ANCHOR, DBUtils.isAttentionAnchor(anchorEntity.userId) ? "1" : "0").append("liveId", str).append(LogConstants.VIEWER_LEVEL, str2).toJSONObject());
        }
    }

    public static void uploadBuyCar(String str, String str2, String str3, String str4, String str5, String str6) {
        if (intercept(LogConstants.BUYCAR_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.BUYCAR_EVENT_NAME, PropertyBuilder.newInstance().append(LogConstants.CAR_ID, str).append(LogConstants.CAR_TYPE, str2).append("duration", str3).append(LogConstants.CAR_NAME, str4).append("cost", formatPrice(str5)).append(LogConstants.VIEWER_LEVEL, str6).toJSONObject());
    }

    public static void uploadAppInstall() {
        if (intercept(LogConstants.APP_INSTALL_EVENT_NAME)) {
            return;
        }
        SensorsDataAPI.sharedInstance().track(LogConstants.APP_INSTALL_EVENT_NAME);
    }

    public static void startLiveDataTimerTask(String str, Runnable runnable, long j, long j2) {
        if (intercept(str)) {
            return;
        }
        LiveDataTimer.getInstance().timer(runnable, j, j2);
    }

    public static void shutDownTimerTask(String str, boolean z) {
        if (intercept(str)) {
            return;
        }
        if (z) {
            LiveDataTimer.getInstance().shutdownTimerTask();
            DBUtils.deleteAll(AnchorLiveDataEntity.class);
            return;
        }
        LiveDataTimer.getInstance().shutdownTimerTask();
        DBUtils.deleteAll(LiveDataEntity.class);
    }

    private static String formatPrice(String str) {
        return AppUtils.formatDisplayPrice(str, false);
    }

    private static boolean isReport() {
        return AppUtils.isEnableLogEventReport();
    }
}
