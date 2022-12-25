package com.tomatolive.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.eclipsesource.p056v8.Platform;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.download.CarDownLoadManager;
import com.tomatolive.library.download.DownLoadRetrofit;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.EventReportRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.utils.Base64Util;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.CarDownloadEntity;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.IconEntity;
import com.tomatolive.library.model.ImpressionEntity;
import com.tomatolive.library.model.LiveAdEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.activity.live.TomatoLiveActivity;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenActivity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.p136ui.view.dialog.alert.TokenInvalidDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.gift.GiftAnimModel;
import com.tomatolive.library.p136ui.view.span.NetImageSpan;
import com.tomatolive.library.p136ui.view.span.VerticalImageSpan;
import com.tomatolive.library.service.TokenDialogService;
import com.tomatolive.library.utils.live.LiveManagerUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class AppUtils {
    public static final int ANCHOR_GRADE_MAX = 30;
    public static final int USER_GRADE_MAX = getUserGradeMax();
    private static final int invalidImgResID = -1;
    private static final String placeholderStr = "  ";

    public static boolean cannotBannedNobility(int i, int i2) {
        return i >= i2;
    }

    public static int getAnchorGradeInterval(int i) {
        if (i < 1 || i > 5) {
            if (i >= 6 && i <= 10) {
                return 2;
            }
            if (i >= 11 && i <= 15) {
                return 3;
            }
            if (i >= 16 && i <= 20) {
                return 4;
            }
            if (i >= 21 && i <= 25) {
                return 5;
            }
            return (i < 26 || i > 30) ? 1 : 6;
        }
        return 1;
    }

    public static String getLocalGiftFilePath(String str) {
        return str;
    }

    public static boolean highThanBoJue(int i) {
        return i > 3;
    }

    public static boolean isAnchorLiveType(int i) {
        return i == 1;
    }

    public static boolean isAudienceLiveType(int i) {
        return i == 2;
    }

    public static boolean isCanShowOpenNobilityAnim(int i) {
        return i > 2;
    }

    public static boolean isKickOutErrorCode(int i) {
        return i == 200023;
    }

    public static boolean isNoEnterLivePermissionErrorCode(int i) {
        return i == 200163;
    }

    public static boolean isNobilityUser(int i) {
        return i > 0;
    }

    public static boolean isTokenInvalidErrorCode(int i) {
        return i == 101001;
    }

    public static boolean isYearGuard(int i) {
        return i == 3;
    }

    private AppUtils() {
    }

    public static String getApiURl() {
        return formatUrl(TomatoLiveSDK.getSingleton().API_URL);
    }

    public static String getUploadUrl() {
        return formatUrl(TomatoLiveSDK.getSingleton().IMG_UP_URL);
    }

    public static String getImgDownloadURl() {
        return formatUrl(TomatoLiveSDK.getSingleton().IMG_DOWN_URL);
    }

    public static String getImgUploadUrl() {
        String formatUrl = formatUrl(TomatoLiveSDK.getSingleton().IMG_UP_URL);
        return formatUrl + "v2/stream_upload_chunk";
    }

    public static String getDataReportUrl() {
        return formatUrl(TomatoLiveSDK.getSingleton().DATA_REPORT_URL);
    }

    public static String getDataReportConfigUrl() {
        return formatUrl(TomatoLiveSDK.getSingleton().DATA_REPORT_CONFIG_URL);
    }

    private static String formatUrl(String str) {
        if (!str.endsWith("/")) {
            return str + "/";
        }
        return str;
    }

    public static boolean isApiService() {
        return ApiRetrofit.getInstance().isApiService();
    }

    public static boolean isDownloadService() {
        return DownLoadRetrofit.getInstance().isApiService();
    }

    public static boolean isEventStatisticsService() {
        return EventReportRetrofit.getInstance().isApiService();
    }

    public static boolean isLiving(String str) {
        return TextUtils.equals("1", str);
    }

    public static boolean isVisitor() {
        return UserInfoManager.getInstance().isVisitorUser();
    }

    public static boolean isConsumptionPermissionUser() {
        if (isLogin()) {
            return true;
        }
        return isVisitor() && SysConfigInfoManager.getInstance().isEnableVisitorPermission();
    }

    public static boolean isConsumptionPermissionUser(Context context) {
        if (!isConsumptionPermissionUser()) {
            onLoginListener(context);
            return false;
        }
        return true;
    }

    public static boolean isLogin() {
        return UserInfoManager.getInstance().isLogin() && !isVisitor();
    }

    public static boolean isLogin(Context context) {
        if (!isLogin()) {
            onLoginListener(context);
            return false;
        }
        return true;
    }

    public static void onLoginListener(Context context) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onLoginListener(context);
        }
    }

    public static void onRechargeListener(Context context) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onGiftRechargeListener(context);
        }
    }

    public static void onScoreListener(Context context) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onScoreListener(context);
        }
    }

    public static void onCustomAdBannerClickListener(Context context, String str) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdClickListener(context, str);
        }
    }

    public static void onAdvChannelHitsListener(Context context, String str, String str2) {
        TomatoLiveSDK.getSingleton().clickBannerReport(context, str);
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel() || TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
            return;
        }
        TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelHitsListener(context, str, str2);
    }

    public static void startTomatoLiveActivity(Context context, LiveEntity liveEntity, String str, String str2) {
        if (TextUtils.isEmpty(UserInfoManager.getInstance().getToken())) {
            onLoginListener(context);
        } else if (isVisitor() && !SysConfigInfoManager.getInstance().isEnableVisitorLive()) {
            onLoginListener(context);
        } else if (!UserInfoManager.getInstance().isEnterLivePermission()) {
            if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
                return;
            }
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onEnterLivePermissionListener(context, 1);
        } else {
            LiveManagerUtils.getInstance().initCurrentLiveItemInfo(liveEntity.liveId, liveEntity);
            Intent intent = new Intent(context, TomatoLiveActivity.class);
            intent.putExtra(ConstantUtils.RESULT_FLAG, str);
            intent.putExtra(ConstantUtils.RESULT_ENTER_SOURCE, str2);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public static LiveEntity formatLiveEntity(AnchorEntity anchorEntity) {
        LiveEntity liveEntity = new LiveEntity();
        liveEntity.anchorId = anchorEntity.userId;
        liveEntity.liveId = anchorEntity.liveId;
        liveEntity.isLiving = anchorEntity.isLiving;
        liveEntity.liveStatus = anchorEntity.liveStatus;
        liveEntity.streamName = anchorEntity.streamName;
        liveEntity.avatar = anchorEntity.avatar;
        liveEntity.pullStreamUrl = anchorEntity.pullStreamUrl;
        liveEntity.sex = anchorEntity.sex;
        liveEntity.nickname = anchorEntity.nickname;
        return liveEntity;
    }

    public static LiveEntity formatLiveEntity(WeekStarAnchorEntity weekStarAnchorEntity) {
        LiveEntity liveEntity = new LiveEntity();
        liveEntity.liveId = weekStarAnchorEntity.liveId;
        liveEntity.userId = weekStarAnchorEntity.anchorId;
        liveEntity.avatar = weekStarAnchorEntity.avatar;
        liveEntity.sex = weekStarAnchorEntity.sex;
        liveEntity.nickname = weekStarAnchorEntity.anchorName;
        liveEntity.expGrade = weekStarAnchorEntity.expGrade;
        return liveEntity;
    }

    public static boolean isAttentionUser(Context context, String str) {
        if (context != null && isConsumptionPermissionUser(context)) {
            if (!TextUtils.equals(UserInfoManager.getInstance().getUserId(), str)) {
                return true;
            }
            ToastUtils.showShort(context.getString(R$string.fq_text_no_attention_oneself));
            return false;
        }
        return false;
    }

    public static String getLiveSysMsgStr() {
        Application application = TomatoLiveSDK.getSingleton().getApplication();
        return application != null ? application.getResources().getString(R$string.fq_system_msg) : "";
    }

    public static int getLiveSysMsgStrLen() {
        return getLiveSysMsgStr().length();
    }

    public static RequestBody getImgUploadRequestBody(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("offset", "0");
        builder.addFormDataPart("clienttime", System.currentTimeMillis() + "");
        builder.addFormDataPart("clientver", "1001");
        builder.addFormDataPart("md5", StringUtils.getFileMD5(file));
        builder.addFormDataPart("mid", "1f76c6e987e4bc406ac6092fb127e2ae370dd078");
        builder.addFormDataPart("size", file.length() + "");
        builder.addFormDataPart("key", MD5Utils.getMd5(file.getName()));
        builder.addFormDataPart("type", "cover");
        builder.addFormDataPart("extend_name", getExtendName(file.getName()));
        builder.addFormDataPart("appid", "1000");
        builder.addFormDataPart("chunk_size", file.length() + "");
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(com.iceteck.silicompressorr.FileUtils.MIME_TYPE_IMAGE), file));
        return builder.build();
    }

    private static String getExtendName(String str) {
        try {
            return str.split("\\.")[1];
        } catch (Exception unused) {
            return "jpg";
        }
    }

    public static String formatLivePopularityCount(long j) {
        int i = (j > 10000L ? 1 : (j == 10000L ? 0 : -1));
        if (i < 0) {
            return String.valueOf(j);
        }
        if (i < 0) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("###0.0");
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return NumberUtils.CovertFloat(decimalFormat.format(Math.ceil(Math.floor(j / 100) / 10.0d) / 10.0d)) + "万";
    }

    public static String formatTenThousandUnit(String str) {
        return TextUtils.isEmpty(str) ? "0" : NumberUtils.formatStarNum(NumberUtils.string2long(str), false);
    }

    public static String formatOnlineUserCount(String str) {
        return formatTenThousandUnit(str);
    }

    public static void formatTvNumTypeface(Context context, TextView textView, String str) {
        textView.setText(str);
    }

    @DrawableRes
    public static int getGenderRes(String str) {
        if (TextUtils.equals("1", str)) {
            return R$drawable.fq_ic_gender_male;
        }
        if (TextUtils.equals("2", str)) {
            return R$drawable.fq_ic_gender_female;
        }
        return R$drawable.fq_ic_gender_male;
    }

    public static int getUserGradeMax() {
        return SysConfigInfoManager.getInstance().getUserGradeMax();
    }

    public static int getUserGradeInterval(int i) {
        if (isUserGradeMax60()) {
            if (i >= 1 && i <= 9) {
                return 1;
            }
            if (i >= 10 && i <= 29) {
                return 2;
            }
            if (i >= 30 && i <= 39) {
                return 3;
            }
            if (i >= 40 && i <= 49) {
                return 4;
            }
            if (i >= 50 && i <= 59) {
                return 5;
            }
            if (i >= USER_GRADE_MAX) {
                return 6;
            }
        } else if (i >= 1 && i <= 14) {
            return 1;
        } else {
            if (i >= 15 && i <= 29) {
                return 2;
            }
            if (i >= 30 && i <= 39) {
                return 3;
            }
            if (i >= 40 && i <= 49) {
                return 4;
            }
            if (i >= 50 && i <= 59) {
                return 5;
            }
            if (i >= 60 && i <= 69) {
                return 6;
            }
            if (i >= 70 && i <= 79) {
                return 7;
            }
            if (i >= 80 && i <= 89) {
                return 8;
            }
            if (i >= 90 && i <= 99) {
                return 9;
            }
            if (i >= 100 && i <= 109) {
                return 10;
            }
            if (i >= 110 && i <= 119) {
                return 11;
            }
            if (i >= USER_GRADE_MAX) {
                return 12;
            }
        }
        return 1;
    }

    @DrawableRes
    public static int getUserGradeBackgroundResource(boolean z, int i) {
        int userGradeInterval = getUserGradeInterval(i);
        if (isUserGradeMax60()) {
            switch (userGradeInterval) {
                case 1:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_1 : R$drawable.fq_shape_user_grade_bg_1;
                case 2:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_2 : R$drawable.fq_shape_user_grade_bg_2;
                case 3:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_3 : R$drawable.fq_shape_user_grade_bg_3;
                case 4:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_4 : R$drawable.fq_shape_user_grade_bg_4;
                case 5:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_5 : R$drawable.fq_shape_user_grade_bg_5;
                case 6:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_6 : R$drawable.fq_shape_user_grade_bg_6;
                default:
                    return z ? R$drawable.fq_shape_user_grade_bg_white_1 : R$drawable.fq_shape_user_grade_bg_1;
            }
        }
        switch (userGradeInterval) {
            case 1:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_1 : R$drawable.fq_shape_user_grade_second_bg_1;
            case 2:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_2 : R$drawable.fq_shape_user_grade_second_bg_2;
            case 3:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_3 : R$drawable.fq_shape_user_grade_second_bg_3;
            case 4:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_4 : R$drawable.fq_shape_user_grade_second_bg_4;
            case 5:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_5 : R$drawable.fq_shape_user_grade_second_bg_5;
            case 6:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_6 : R$drawable.fq_shape_user_grade_second_bg_6;
            case 7:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_7 : R$drawable.fq_shape_user_grade_second_bg_7;
            case 8:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_8 : R$drawable.fq_shape_user_grade_second_bg_8;
            case 9:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_9 : R$drawable.fq_shape_user_grade_second_bg_9;
            case 10:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_10 : R$drawable.fq_shape_user_grade_second_bg_10;
            case 11:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_11 : R$drawable.fq_shape_user_grade_second_bg_11;
            case 12:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_12 : R$drawable.fq_shape_user_grade_second_bg_12;
            default:
                return z ? R$drawable.fq_shape_user_grade_second_bg_white_1 : R$drawable.fq_shape_user_grade_second_bg_1;
        }
    }

    @DrawableRes
    public static int getUserGradeIconResource(boolean z, int i) {
        int userGradeInterval = getUserGradeInterval(i);
        if (isUserGradeMax60()) {
            switch (userGradeInterval) {
                case 1:
                    return z ? R$drawable.fq_ic_grade_user_1_small : R$drawable.fq_ic_grade_user_1;
                case 2:
                    return z ? R$drawable.fq_ic_grade_user_2_small : R$drawable.fq_ic_grade_user_2;
                case 3:
                    return z ? R$drawable.fq_ic_grade_user_3_small : R$drawable.fq_ic_grade_user_3;
                case 4:
                    return z ? R$drawable.fq_ic_grade_user_4_small : R$drawable.fq_ic_grade_user_4;
                case 5:
                    return z ? R$drawable.fq_ic_grade_user_5_small : R$drawable.fq_ic_grade_user_5;
                case 6:
                    return z ? R$drawable.fq_ic_grade_user_6_small : R$drawable.fq_ic_grade_user_6;
                default:
                    return z ? R$drawable.fq_ic_grade_user_1_small : R$drawable.fq_ic_grade_user_1;
            }
        }
        switch (userGradeInterval) {
            case 1:
                return z ? R$drawable.fq_ic_grade_user_second_1_small : R$drawable.fq_ic_grade_user_second_1;
            case 2:
                return z ? R$drawable.fq_ic_grade_user_second_2_small : R$drawable.fq_ic_grade_user_second_2;
            case 3:
                return z ? R$drawable.fq_ic_grade_user_second_3_small : R$drawable.fq_ic_grade_user_second_3;
            case 4:
                return z ? R$drawable.fq_ic_grade_user_second_4_small : R$drawable.fq_ic_grade_user_second_4;
            case 5:
                return z ? R$drawable.fq_ic_grade_user_second_5_small : R$drawable.fq_ic_grade_user_second_5;
            case 6:
                return z ? R$drawable.fq_ic_grade_user_second_6_small : R$drawable.fq_ic_grade_user_second_6;
            case 7:
                return z ? R$drawable.fq_ic_grade_user_second_7_small : R$drawable.fq_ic_grade_user_second_7;
            case 8:
                return z ? R$drawable.fq_ic_grade_user_second_8_small : R$drawable.fq_ic_grade_user_second_8;
            case 9:
                return z ? R$drawable.fq_ic_grade_user_second_9_small : R$drawable.fq_ic_grade_user_second_9;
            case 10:
                return z ? R$drawable.fq_ic_grade_user_second_10_small : R$drawable.fq_ic_grade_user_second_10;
            case 11:
                return z ? R$drawable.fq_ic_grade_user_second_11_small : R$drawable.fq_ic_grade_user_second_11;
            case 12:
                return z ? R$drawable.fq_ic_grade_user_second_12_small : R$drawable.fq_ic_grade_user_second_12;
            default:
                return z ? R$drawable.fq_ic_grade_user_second_1_small : R$drawable.fq_ic_grade_user_second_1;
        }
    }

    @DrawableRes
    public static int getUserGradeBgDrawableRes(String str) {
        int userGradeInterval = getUserGradeInterval(NumberUtils.string2int(str));
        if (isUserGradeMax60()) {
            switch (userGradeInterval) {
                case 1:
                    return R$drawable.fq_ic_grade_user_bg_1;
                case 2:
                    return R$drawable.fq_ic_grade_user_bg_2;
                case 3:
                    return R$drawable.fq_ic_grade_user_bg_3;
                case 4:
                    return R$drawable.fq_ic_grade_user_bg_4;
                case 5:
                    return R$drawable.fq_ic_grade_user_bg_5;
                case 6:
                    return R$drawable.fq_ic_grade_user_bg_6;
                default:
                    return R$drawable.fq_ic_grade_user_bg_1;
            }
        }
        switch (userGradeInterval) {
            case 1:
                return R$drawable.fq_ic_grade_user_second_bg_1;
            case 2:
                return R$drawable.fq_ic_grade_user_second_bg_2;
            case 3:
                return R$drawable.fq_ic_grade_user_second_bg_3;
            case 4:
                return R$drawable.fq_ic_grade_user_second_bg_4;
            case 5:
                return R$drawable.fq_ic_grade_user_second_bg_5;
            case 6:
                return R$drawable.fq_ic_grade_user_second_bg_6;
            case 7:
                return R$drawable.fq_ic_grade_user_second_bg_7;
            case 8:
                return R$drawable.fq_ic_grade_user_second_bg_8;
            case 9:
                return R$drawable.fq_ic_grade_user_second_bg_9;
            case 10:
                return R$drawable.fq_ic_grade_user_second_bg_10;
            case 11:
                return R$drawable.fq_ic_grade_user_second_bg_11;
            case 12:
                return R$drawable.fq_ic_grade_user_second_bg_12;
            default:
                return R$drawable.fq_ic_grade_user_second_bg_1;
        }
    }

    public static boolean isMaxGradeForAnchor(String str) {
        return NumberUtils.string2int(formatExpGrade(str)) >= 30;
    }

    public static boolean isUserGradeMax60() {
        return getUserGradeMax() == 60;
    }

    public static boolean isUserGradeMax120() {
        return getUserGradeMax() == 120;
    }

    public static List<BannerEntity> getImgBannerItem(List<BannerEntity> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (BannerEntity bannerEntity : list) {
                if (bannerEntity != null && (bannerEntity.terminal.contains("all") || bannerEntity.terminal.contains(Platform.ANDROID))) {
                    arrayList.add(bannerEntity);
                }
            }
        }
        return arrayList;
    }

    public static String formatExpGrade(String str) {
        return TextUtils.isEmpty(str) ? "1" : str;
    }

    public static String formatExp(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }

    public static long getOnlineCountSynInterval() {
        return NumberUtils.string2long(SysConfigInfoManager.getInstance().getOnlineCountSynInterval(), NumberUtils.string2long("10"));
    }

    public static long getGiftNoticeInterval() {
        return NumberUtils.string2long(SysConfigInfoManager.getInstance().getGiftTrumpetPlayPeriod(), NumberUtils.string2long(ConstantUtils.DEFAULT_GIFT_INTERVAL_MILLISECOND));
    }

    public static String getEnableTranslationLevel() {
        String enableTranslationLevel = SysConfigInfoManager.getInstance().getEnableTranslationLevel();
        return TextUtils.isEmpty(enableTranslationLevel) ? "5" : enableTranslationLevel;
    }

    public static boolean isEnableTranslation(String str) {
        return NumberUtils.string2int(str) >= NumberUtils.string2int(getEnableTranslationLevel(), NumberUtils.string2int("5"));
    }

    public static String getEnableJoinLevel() {
        String entryNoticeLevelThreshold = SysConfigInfoManager.getInstance().getEntryNoticeLevelThreshold();
        return TextUtils.isEmpty(entryNoticeLevelThreshold) ? "10" : entryNoticeLevelThreshold;
    }

    public static boolean isEnableJoinLevel(String str) {
        return NumberUtils.string2int(str) >= NumberUtils.string2int(getEnableJoinLevel(), NumberUtils.string2int("10"));
    }

    public static int getGradeSet10CharacterLimit() {
        return NumberUtils.string2int(SysConfigInfoManager.getInstance().getGradeSet10CharacterLimit(), NumberUtils.string2int(ConstantUtils.DEFAULT_CHARACTER_LIMIT));
    }

    public static int getNobilityTypeThresholdForHasPreventBanned() {
        return SysConfigInfoManager.getInstance().getNobilityTypeThresholdForHasPreventBanned();
    }

    public static long getSocketHeartBeatInterval() {
        return SysConfigInfoManager.getInstance().getSocketHeartBeatInterval() * 1000;
    }

    public static LiveEntity formatLiveEntity(BannerEntity bannerEntity) {
        LiveEntity liveEntity = new LiveEntity();
        if (bannerEntity == null) {
            return null;
        }
        liveEntity.liveId = bannerEntity.url;
        return liveEntity;
    }

    public static String formatLiveSocketUrl(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("&");
        sb.append(str3);
        sb.append("&");
        sb.append(str4);
        sb.append("&");
        sb.append(UserInfoManager.getInstance().getAppId());
        sb.append("/");
        sb.append(UserInfoManager.getInstance().getToken());
        sb.append("/");
        sb.append(str5);
        if (!TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            sb.append("/");
            sb.append(TomatoLiveSDK.getSingleton().ADV_CHANNEL_ID);
        } else {
            sb.append("/");
            sb.append("-1");
        }
        sb.append("/");
        sb.append("v300");
        sb.append("/");
        sb.append("1");
        try {
            String DESEncrypt = EncryptUtil.DESEncrypt(TomatoLiveSDK.getSingleton().ENCRYPT_SOCKET_KEY, sb.toString());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str);
            stringBuffer.append("/");
            stringBuffer.append(DESEncrypt);
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(str);
            stringBuffer2.append("/");
            stringBuffer2.append(sb.toString());
            return stringBuffer2.toString();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getGuardTypeStr(Context context, String str) {
        char c;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1) {
                return context.getString(R$string.fq_guard_month);
            }
            return c != 2 ? "" : context.getString(R$string.fq_guard_year);
        }
        return context.getString(R$string.fq_guard_week);
    }

    public static boolean isShowUserAvatarDialog(ChatEntity chatEntity) {
        if (chatEntity == null) {
            return false;
        }
        return chatEntity.getMsgType() == 3 || chatEntity.getMsgType() == 1 || chatEntity.getMsgType() == 0 || chatEntity.getMsgType() == 15;
    }

    public static void startDialogService(Context context, Class<? extends IntentService> cls) {
        startDialogService(context, cls, null);
    }

    public static void startDialogService(Context context, Class<? extends IntentService> cls, String str) {
        if (isRunBackground(context)) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isFinishing()) {
            return;
        }
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(ConstantUtils.RESULT_ITEM, str);
        }
        context.startService(intent);
    }

    public static boolean isRunBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager != null ? activityManager.getRunningAppProcesses() : null;
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(context.getPackageName())) {
                return runningAppProcessInfo.importance == 400;
            }
        }
        return false;
    }

    public static boolean isHouseManager(String str) {
        return TextUtils.equals(str, "3");
    }

    public static boolean isHouseSuperManager(String str) {
        return TextUtils.equals(str, "5");
    }

    public static boolean isAudience(String str) {
        return TextUtils.equals(str, "2");
    }

    public static boolean isAnchor(String str) {
        return TextUtils.equals(str, "1");
    }

    public static boolean isGuardUser(int i) {
        return i > NumberUtils.string2int("0");
    }

    public static boolean isSuperAdmin() {
        return UserInfoManager.getInstance().isSuperAdmin();
    }

    public static boolean isSuperAdmin(String str) {
        return TextUtils.equals(UserInfoManager.getInstance().getAppId(), str) && UserInfoManager.getInstance().isSuperAdmin();
    }

    public static boolean isYearGuard(String str) {
        return TextUtils.equals(str, "3");
    }

    public static boolean isSocketEventSuc(String str) {
        return TextUtils.equals(str, "1");
    }

    public static boolean isGiftNeedUpdate(String str) {
        return TextUtils.equals(str, ConstantUtils.GIFT_NEED_UPDATE);
    }

    public static boolean hasCar(String str) {
        return !TextUtils.isEmpty(str) && !TextUtils.equals(str, "-1");
    }

    public static File getLocalCarResAbsoluteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return FileUtils.getFileByPath(str);
    }

    public static InputStream getCarSVGAFileInputStream(String str) throws Exception {
        File localCarResAbsoluteFile;
        CarDownloadEntity carItemEntity = CarDownLoadManager.getInstance().getCarItemEntity(str);
        if (carItemEntity != null && !TextUtils.isEmpty(carItemEntity.animLocalPath) && (localCarResAbsoluteFile = getLocalCarResAbsoluteFile(carItemEntity.animLocalPath)) != null && localCarResAbsoluteFile.exists()) {
            return new FileInputStream(localCarResAbsoluteFile);
        }
        return null;
    }

    public static InputStream getCarSVGAFileInputStream(String str, String str2) throws Exception {
        File localCarResAbsoluteFile;
        CarDownloadEntity carItemEntity = CarDownLoadManager.getInstance().getCarItemEntity(str);
        if (carItemEntity != null && TextUtils.equals(carItemEntity.versionCode, str2) && !TextUtils.isEmpty(carItemEntity.animLocalPath) && (localCarResAbsoluteFile = getLocalCarResAbsoluteFile(carItemEntity.animLocalPath)) != null && localCarResAbsoluteFile.exists()) {
            return new FileInputStream(localCarResAbsoluteFile);
        }
        return null;
    }

    public static String getStickerWaterImgPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("waterLogo");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        sb.append(File.separator);
        sb.append("waterLogo");
        sb.append(UserInfoManager.getInstance().getUserId());
        sb.append(".png");
        return sb.toString();
    }

    public static String formatContributionValue(SocketMessageEvent.ResultData resultData) {
        return String.valueOf(new Double(NumberUtils.mul(getFormatVirtualGold(resultData.price), NumberUtils.string2Double(TextUtils.isEmpty(resultData.giftNum) ? "1" : resultData.giftNum))).longValue());
    }

    public static double getFormatVirtualGold(String str) {
        return NumberUtils.mul(changeF2Y(str), 10.0d);
    }

    public static HttpResultPageModel<LiveEntity> formatHttpResultPageModel(HttpResultPageModel<LiveAdEntity> httpResultPageModel) {
        HttpResultPageModel<LiveEntity> httpResultPageModel2 = new HttpResultPageModel<>();
        httpResultPageModel2.pageNumber = httpResultPageModel.pageNumber;
        httpResultPageModel2.pageSize = httpResultPageModel.pageSize;
        httpResultPageModel2.totalRowsCount = httpResultPageModel.totalRowsCount;
        httpResultPageModel2.totalPageCount = httpResultPageModel.totalPageCount;
        httpResultPageModel2.dataList = formatLiveEntityList(httpResultPageModel.dataList);
        return httpResultPageModel2;
    }

    private static List<LiveEntity> formatLiveEntityList(List<LiveAdEntity> list) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (LiveAdEntity liveAdEntity : list) {
            arrayList.add(liveAdEntity.getLiveEntity());
        }
        return arrayList;
    }

    public static void clickBannerEvent(Context context, BannerEntity bannerEntity) {
        if (bannerEntity == null) {
            return;
        }
        onAdvChannelHitsListener(context, bannerEntity.f5830id, "2");
        if (bannerEntity.isLiveSDKCallback()) {
            onCustomAdBannerClickListener(context, bannerEntity.url);
        } else if (bannerEntity.isJumpLiveRoom()) {
            LiveEntity formatLiveEntity = formatLiveEntity(bannerEntity);
            if (bannerEntity == null) {
                return;
            }
            startTomatoLiveActivity(context, formatLiveEntity, "2", context.getString(R$string.fq_live_enter_source_ad));
        } else if (!bannerEntity.isJumpWebUrl()) {
        } else {
            if (bannerEntity.isJumpCustomUrl()) {
                onSysWebView(context, bannerEntity.url);
                return;
            }
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", bannerEntity.url);
            intent.putExtra("title", bannerEntity.name);
            context.startActivity(intent);
        }
    }

    public static void onLiveListClickAdEvent(Context context, LiveEntity liveEntity) {
        if (liveEntity == null) {
            return;
        }
        onAdvChannelHitsListener(context, liveEntity.f5840id, "2");
        if (TextUtils.equals(liveEntity.format, "3")) {
            onCustomAdBannerClickListener(context, liveEntity.label);
        } else if (TextUtils.equals(liveEntity.role, "1")) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", liveEntity.label);
            intent.putExtra("title", liveEntity.nickname);
            context.startActivity(intent);
        } else {
            onSysWebView(context, liveEntity.label);
        }
    }

    public static void onSysWebView(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str) || !RegexUtils.isURL(str)) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public static String getScreenshotPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppCachePath());
        sb.append(File.separator);
        sb.append("imgCache");
        com.blankj.utilcode.util.FileUtils.createOrExistsDir(sb.toString());
        sb.append(File.separator);
        sb.append("screenshot.png");
        return sb.toString();
    }

    public static Bitmap getViewBitmap(Activity activity, View view) {
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Bitmap createBitmap = Bitmap.createBitmap(drawingCache, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.destroyDrawingCache();
        return createBitmap;
    }

    public static Bitmap toConformBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null || bitmap.isRecycled() || bitmap2 == null || bitmap2.isRecycled()) {
            return null;
        }
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        canvas.save();
        canvas.restore();
        return createBitmap;
    }

    public static void updateRefreshLayoutFinishStatus(SmartRefreshLayout smartRefreshLayout, boolean z, boolean z2) {
        if (z2) {
            smartRefreshLayout.resetNoMoreData();
            if (!z) {
                return;
            }
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else if (z) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            smartRefreshLayout.finishLoadMore();
        }
    }

    @DrawableRes
    public static int getNobilityBadgeDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_badge_1;
            case 2:
                return R$drawable.fq_ic_nobility_badge_2;
            case 3:
                return R$drawable.fq_ic_nobility_badge_3;
            case 4:
                return R$drawable.fq_ic_nobility_badge_4;
            case 5:
                return R$drawable.fq_ic_nobility_badge_5;
            case 6:
                return R$drawable.fq_ic_nobility_badge_6;
            case 7:
                return R$drawable.fq_ic_nobility_badge_7;
            default:
                return R$drawable.fq_ic_nobility_badge_1;
        }
    }

    @DrawableRes
    public static int getNobilityBadgeMsgDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_badge_msg_1;
            case 2:
                return R$drawable.fq_ic_nobility_badge_msg_2;
            case 3:
                return R$drawable.fq_ic_nobility_badge_msg_3;
            case 4:
                return R$drawable.fq_ic_nobility_badge_msg_4;
            case 5:
                return R$drawable.fq_ic_nobility_badge_msg_5;
            case 6:
                return R$drawable.fq_ic_nobility_badge_msg_6;
            case 7:
                return R$drawable.fq_ic_nobility_badge_msg_7;
            default:
                return R$drawable.fq_ic_nobility_badge_msg_1;
        }
    }

    @DrawableRes
    public static int getNobilityEnterBadgeDrawableRes(int i) {
        if (i != 5) {
            if (i == 6) {
                return R$drawable.fq_ic_nobility_welcome_avatar_bg_gw;
            }
            if (i == 7) {
                return R$drawable.fq_ic_nobility_welcome_avatar_bg_hd;
            }
            return -1;
        }
        return R$drawable.fq_ic_nobility_welcome_avatar_bg_gj;
    }

    @DrawableRes
    public static int getNobilityAvatarBgDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_shape_nobility_avatar_bg_1;
            case 2:
                return R$drawable.fq_shape_nobility_avatar_bg_2;
            case 3:
                return R$drawable.fq_shape_nobility_avatar_bg_3;
            case 4:
                return R$drawable.fq_shape_nobility_avatar_bg_4;
            case 5:
                return R$drawable.fq_shape_nobility_avatar_bg_5;
            case 6:
                return R$drawable.fq_shape_nobility_avatar_bg_6;
            case 7:
                return R$drawable.fq_shape_nobility_avatar_bg_7;
            default:
                return R$drawable.fq_shape_nobility_avatar_bg_1;
        }
    }

    @DrawableRes
    public static int getNobilityCardBgDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_card_bg_1;
            case 2:
                return R$drawable.fq_ic_nobility_card_bg_2;
            case 3:
                return R$drawable.fq_ic_nobility_card_bg_3;
            case 4:
                return R$drawable.fq_ic_nobility_card_bg_4;
            case 5:
                return R$drawable.fq_ic_nobility_card_bg_5;
            case 6:
                return R$drawable.fq_ic_nobility_card_bg_6;
            case 7:
                return R$drawable.fq_ic_nobility_card_bg_7;
            default:
                return R$drawable.fq_ic_nobility_card_bg_1;
        }
    }

    public static String getNobilityBadgeName(Context context, int i) {
        String[] stringArray = context.getResources().getStringArray(R$array.fq_nobility_name);
        switch (i) {
            case 1:
                return stringArray[0];
            case 2:
                return stringArray[1];
            case 3:
                return stringArray[2];
            case 4:
                return stringArray[3];
            case 5:
                return stringArray[4];
            case 6:
                return stringArray[5];
            case 7:
                return stringArray[6];
            default:
                return "";
        }
    }

    public static boolean isNobilityUser() {
        return isNobilityUser(UserInfoManager.getInstance().getNobilityType());
    }

    public static AnchorEntity formatAnchorEntity(UserEntity userEntity) {
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.userId = userEntity.getUserId();
        anchorEntity.avatar = userEntity.getAvatar();
        anchorEntity.nickname = TextUtils.isEmpty(userEntity.getName()) ? userEntity.nickname : userEntity.getName();
        anchorEntity.sex = userEntity.getSex();
        anchorEntity.expGrade = userEntity.getExpGrade();
        anchorEntity.role = userEntity.getRole();
        anchorEntity.signature = userEntity.getSignature();
        anchorEntity.openId = userEntity.getOpenId();
        anchorEntity.appId = userEntity.getAppId();
        if (TextUtils.isEmpty(userEntity.getUserRole())) {
            anchorEntity.userRole = isAnchor(userEntity.getRole()) ? "1" : userEntity.getUserRole();
        } else {
            anchorEntity.userRole = userEntity.getUserRole();
        }
        anchorEntity.nobilityType = userEntity.getNobilityType();
        anchorEntity.guardType = userEntity.guardType;
        anchorEntity.isRankHide = userEntity.isRankHide;
        return anchorEntity;
    }

    public static UserEntity formatUserEntity(AnchorEntity anchorEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(anchorEntity.userId);
        userEntity.setAvatar(anchorEntity.avatar);
        userEntity.setName(!TextUtils.isEmpty(anchorEntity.nickname) ? anchorEntity.nickname : anchorEntity.name);
        userEntity.setSex(anchorEntity.sex);
        userEntity.setExpGrade(anchorEntity.expGrade);
        userEntity.setGuardType(NumberUtils.string2int(anchorEntity.guardType));
        userEntity.setRole(anchorEntity.role);
        userEntity.setNobilityType(anchorEntity.nobilityType);
        userEntity.setLiveId(anchorEntity.liveId);
        userEntity.setOpenId(anchorEntity.openId);
        userEntity.setAppId(anchorEntity.appId);
        userEntity.setLiveStatus(anchorEntity.isLiving);
        userEntity.setUserRole(anchorEntity.userRole);
        userEntity.isRankHide = anchorEntity.isRankHide;
        return userEntity;
    }

    public static UserEntity formatUserEntity(ChatEntity chatEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(chatEntity.getUid());
        userEntity.setAvatar(chatEntity.getUserAvatar());
        userEntity.setName(chatEntity.getMsgSendName());
        userEntity.setSex(chatEntity.getSex());
        userEntity.setExpGrade(chatEntity.getExpGrade());
        userEntity.setGuardType(chatEntity.getGuardType());
        userEntity.setRole(chatEntity.getRole());
        userEntity.setNobilityType(chatEntity.getNobilityType());
        userEntity.setOpenId(chatEntity.getOpenId());
        userEntity.setAppId(chatEntity.getAppId());
        userEntity.setUserRole(chatEntity.getUserRole());
        return userEntity;
    }

    public static UserEntity formatUserEntity(GiftAnimModel giftAnimModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(giftAnimModel.getSendUserAvatar());
        userEntity.setUserId(giftAnimModel.getSendUserId());
        userEntity.setName(giftAnimModel.getSendUserName());
        userEntity.setSex(giftAnimModel.getSendUserSex());
        userEntity.setExpGrade(giftAnimModel.getSendUserExpGrade());
        userEntity.setGuardType(giftAnimModel.getSendUserGuardType());
        userEntity.setRole(giftAnimModel.getSendRole());
        userEntity.setUserRole(giftAnimModel.getSendUserRole());
        userEntity.setNobilityType(giftAnimModel.getSendUserNobilityType());
        userEntity.setOpenId(giftAnimModel.openId);
        userEntity.setAppId(giftAnimModel.appId);
        return userEntity;
    }

    public static String formatLiveVipCount(long j) {
        if (j > 10000) {
            return formatTenThousandUnit(String.valueOf(j));
        }
        return NumberUtils.formatThreeNumStr(j);
    }

    public static boolean isEnableTurntable() {
        return SysConfigInfoManager.getInstance().isEnableTurntable();
    }

    public static boolean isEnableNobility() {
        return SysConfigInfoManager.getInstance().isEnableNobility();
    }

    public static boolean isEnableGuard() {
        return SysConfigInfoManager.getInstance().isEnableGuard();
    }

    public static boolean isEnableScore() {
        return SysConfigInfoManager.getInstance().isEnableScore();
    }

    public static boolean isEnableWeekStar() {
        return SysConfigInfoManager.getInstance().isEnableWeekStar();
    }

    public static boolean isEnableGiftWall() {
        return SysConfigInfoManager.getInstance().isEnableGiftWall();
    }

    public static boolean isEnableAchievement() {
        return SysConfigInfoManager.getInstance().isEnableAchievement();
    }

    public static boolean isEnableComponents() {
        return SysConfigInfoManager.getInstance().isEnableComponents();
    }

    public static boolean isEnablePrivateMsg() {
        return SysConfigInfoManager.getInstance().isEnablePrivateMsg();
    }

    public static boolean isEnableLiveHelperJump() {
        return SysConfigInfoManager.getInstance().isEnableLiveHelperJump();
    }

    public static boolean isEnableLogEventReport() {
        return SysConfigInfoManager.getInstance().isEnableLogEventReport();
    }

    public static boolean isEnableQMInteract() {
        return SysConfigInfoManager.getInstance().isEnableQMInteract();
    }

    public static boolean isAttentionAnchor(String str) {
        return isConsumptionPermissionUser() && DBUtils.isAttentionAnchor(str);
    }

    public static void toNobilityOpenActivity(Context context, AnchorEntity anchorEntity) {
        if (context != null && isConsumptionPermissionUser(context)) {
            if (!isEnableNobility()) {
                ToastUtils.showShort(context.getString(R$string.fq_nobility_model_close));
                return;
            }
            Intent intent = new Intent(context, NobilityOpenActivity.class);
            if (anchorEntity != null) {
                intent.putExtra(ConstantUtils.RESULT_ITEM, anchorEntity);
            }
            context.startActivity(intent);
        }
    }

    public static void onUserHomepageListener(Context context, AnchorEntity anchorEntity) {
        anchorEntity.role = "1";
        onUserHomepageListener(context, formatUserEntity(anchorEntity));
    }

    public static void onUserHomepageListener(Context context, UserEntity userEntity) {
        if (context == null || !isConsumptionPermissionUser(context) || userEntity == null) {
            return;
        }
        if (!TextUtils.equals(userEntity.getAppId(), UserInfoManager.getInstance().getAppId())) {
            ToastUtils.showShort(context.getString(R$string.fq_open_homepage_tips));
            return;
        }
        userEntity.setUserId(userEntity.getOpenId());
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
            return;
        }
        TomatoLiveSDK.getSingleton().sdkCallbackListener.onUserHomepageListener(context, userEntity);
    }

    public static String getUploadPicPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append(ConstantUtils.IMG_DIR);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    public static List<LiveEntity> removeDuplicateList(List<LiveEntity> list, List<LiveEntity> list2) {
        ArrayList arrayList = new ArrayList();
        if (list2 != null && list != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap((int) ((list2.size() / 0.75f) + 1.0f));
            for (LiveEntity liveEntity : list2) {
                linkedHashMap.put(liveEntity.userId, liveEntity);
            }
            for (LiveEntity liveEntity2 : list) {
                if (linkedHashMap.containsKey(liveEntity2.userId)) {
                    linkedHashMap.remove(liveEntity2.userId);
                }
            }
            arrayList.addAll(linkedHashMap.values());
        }
        return arrayList;
    }

    public static List<GiftDownloadItemEntity> formatWeekStarGiftList(Context context, List<GiftDownloadItemEntity> list) {
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < 5; i++) {
                GiftDownloadItemEntity giftDownloadItemEntity = new GiftDownloadItemEntity();
                giftDownloadItemEntity.name = context.getString(R$string.fq_week_star_unspecified);
                giftDownloadItemEntity.imgurl = "";
                giftDownloadItemEntity.markId = "";
                arrayList.add(giftDownloadItemEntity);
            }
            return arrayList;
        }
        return list;
    }

    public static ImpressionEntity getAnchorImpressionEntity(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return null;
        }
        ImpressionEntity impressionEntity = new ImpressionEntity();
        impressionEntity.anchorId = anchorEntity.userId;
        impressionEntity.anchorName = anchorEntity.nickname;
        impressionEntity.anchorOpenId = anchorEntity.openId;
        impressionEntity.anchorAppId = anchorEntity.appId;
        return impressionEntity;
    }

    public static String formatUserNickName(String str) {
        return StringUtils.formatStrLen(str, 7);
    }

    public static String formatUserNickNameByNotice(String str) {
        return StringUtils.formatStrLen(str, 5);
    }

    public static String formatDisplayPrice(double d, boolean z) {
        if (d < 0.0d) {
            d = 0.0d;
        }
        return formatDisplayPrice(String.valueOf(new Double(d).longValue()), z);
    }

    public static String formatDisplayPrice(String str, boolean z) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "0")) {
            return "0";
        }
        double formatPriceExchangeProportion = formatPriceExchangeProportion(str);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(z);
        if (formatPriceExchangeProportion > 0.0d) {
            numberFormat.setMinimumFractionDigits(2);
        }
        String format = numberFormat.format(formatPriceExchangeProportion);
        return format.endsWith(".00") ? format.substring(0, format.length() - 3) : format;
    }

    public static double formatPriceExchangeProportion(String str) {
        double changeF2Y = changeF2Y(str);
        return SysConfigInfoManager.getInstance().isEnableExchangeProportion() ? changeF2Y : NumberUtils.mul(changeF2Y, 10.0d);
    }

    public static double changeF2Y(String str) {
        return BigDecimal.valueOf(NumberUtils.string2long(str)).divide(new BigDecimal(100)).doubleValue();
    }

    public static String formatMoneyUnitStr(Context context, String str, boolean z) {
        return getLiveMoneyUnitStr(context) + formatDisplayPrice(str, z);
    }

    public static String getLiveMoneyUnitStr(Context context) {
        return context.getString(R$string.fq_tomato_money_str) + ConstantUtils.PLACEHOLDER_STR_ONE;
    }

    public static String getLiveMoneyUnitStr(Context context, String str) {
        return getLiveMoneyUnitStr(context) + str;
    }

    public static String getNobilityGoldUnitStr(Context context) {
        return context.getString(R$string.fq_nobility_gold);
    }

    public static String getComponentsGameWebUrl(String str, String str2, String str3) {
        String str4 = TomatoLiveSDK.getSingleton().GAME_CHANNEL;
        String str5 = TomatoLiveSDK.getSingleton().APP_NAME;
        String formatUrl = formatUrl(str);
        StringBuilder sb = new StringBuilder();
        sb.append(formatUrl);
        sb.append("?");
        if (!TextUtils.isEmpty(str2)) {
            sb.append("game_id");
            sb.append(SimpleComparison.EQUAL_TO_OPERATION);
            sb.append(str2);
            sb.append("&");
        }
        sb.append("token");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(str3);
        sb.append("&");
        sb.append("appid");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(str4);
        sb.append("&");
        sb.append("app_name");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(str5);
        sb.append("&");
        sb.append("platform");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(UserInfoManager.getInstance().getAppId());
        sb.append("&");
        sb.append(LogConstants.ACCOUNT);
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(UserInfoManager.getInstance().getAppOpenId());
        return sb.toString();
    }

    public static void initUserCardDynamicMark(Context context, TextView textView, UserGradeView userGradeView, String str, String str2, int i, List<String> list, boolean z) {
        IconEntity dynamicMarkIconEntity = getDynamicMarkIconEntity(context, userGradeView, str, str2, i, list, z);
        List<String> markUrls = dynamicMarkIconEntity.getMarkUrls();
        SpannableString spannableString = new SpannableString(dynamicMarkIconEntity.value);
        int i2 = 0;
        for (int i3 = 0; i3 < markUrls.size(); i3++) {
            String str3 = markUrls.get(i3);
            int i4 = (i2 + 2) - 1;
            char c = 65535;
            switch (str3.hashCode()) {
                case -2082608924:
                    if (str3.equals(ConstantUtils.NOBILITY_TYPE_ICON_KEY)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1962995782:
                    if (str3.equals(ConstantUtils.EXP_GRADE_ICON_KEY)) {
                        c = 0;
                        break;
                    }
                    break;
                case -186628225:
                    if (str3.equals(ConstantUtils.GUARD_TYPE_ICON_KEY)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3506294:
                    if (str3.equals(ConstantUtils.ROLE_ICON_KEY)) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                Drawable drawable = dynamicMarkIconEntity.userGradeDrawable;
                if (drawable != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable), i2, i4, 33);
                }
            } else if (c == 1) {
                Drawable drawable2 = dynamicMarkIconEntity.roleDrawable;
                if (drawable2 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable2), i2, i4, 33);
                }
            } else if (c == 2) {
                Drawable drawable3 = dynamicMarkIconEntity.guardDrawable;
                if (drawable3 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable3), i2, i4, 33);
                }
            } else if (c == 3) {
                Drawable drawable4 = dynamicMarkIconEntity.nobilityDrawable;
                if (drawable4 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable4), i2, i4, 33);
                }
            } else if (textView != null) {
                NetImageSpan netImageSpan = new NetImageSpan(textView);
                netImageSpan.setUrl(str3);
                netImageSpan.setHeight(ConvertUtils.dp2px(20.0f));
                spannableString.setSpan(netImageSpan, i2, i4, 33);
            }
            i2 = i4 + 1;
        }
        textView.setText(spannableString);
    }

    private static IconEntity getDynamicMarkIconEntity(Context context, UserGradeView userGradeView, String str, String str2, int i, List<String> list, boolean z) {
        char c;
        IconEntity iconEntity = new IconEntity();
        iconEntity.urls = new ArrayList();
        if (list != null) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < list.size(); i2++) {
                String str3 = list.get(i2);
                int hashCode = str3.hashCode();
                if (hashCode == -1962995782) {
                    if (str3.equals(ConstantUtils.EXP_GRADE_ICON_KEY)) {
                        c = 0;
                    }
                    c = 65535;
                } else if (hashCode != -186628225) {
                    if (hashCode == 3506294 && str3.equals(ConstantUtils.ROLE_ICON_KEY)) {
                        c = 1;
                    }
                    c = 65535;
                } else {
                    if (str3.equals(ConstantUtils.GUARD_TYPE_ICON_KEY)) {
                        c = 2;
                    }
                    c = 65535;
                }
                if (c == 0) {
                    userGradeView.initUserGradeMsg(str2, z);
                    Bitmap viewBitmap = getViewBitmap(userGradeView);
                    if (viewBitmap != null) {
                        iconEntity.userGradeDrawable = formatDrawableBounds(ImageUtils.bitmap2Drawable(viewBitmap));
                        if (iconEntity.userGradeDrawable != null) {
                            sb.append("  ");
                            iconEntity.urls.add(str3);
                        }
                    }
                } else if (c == 1) {
                    int roleMsgIconRes = getRoleMsgIconRes(str);
                    if (roleMsgIconRes != -1) {
                        iconEntity.roleDrawable = formatDrawableBounds(ContextCompat.getDrawable(context, roleMsgIconRes));
                        if (iconEntity.roleDrawable != null) {
                            sb.append("  ");
                            iconEntity.urls.add(str3);
                        }
                    }
                } else if (c == 2) {
                    int guardIconRes = getGuardIconRes(i);
                    if (guardIconRes != -1) {
                        iconEntity.guardDrawable = formatDrawableBounds(ContextCompat.getDrawable(context, guardIconRes));
                        if (iconEntity.guardDrawable != null) {
                            sb.append("  ");
                            iconEntity.urls.add(str3);
                        }
                    }
                } else if (!TextUtils.isEmpty(str3)) {
                    iconEntity.urls.add(str3);
                    sb.append("  ");
                }
            }
            iconEntity.value = sb.toString();
        }
        return iconEntity;
    }

    public static Drawable formatDrawableBounds(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static Bitmap getViewBitmap(View view) {
        try {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            return Bitmap.createBitmap(view.getDrawingCache());
        } catch (Exception unused) {
            return null;
        }
    }

    @DrawableRes
    private static int getRoleMsgIconRes(String str) {
        if (isAnchor(str)) {
            return R$drawable.fq_ic_live_msg_anchor_big_2;
        }
        if (isHouseManager(str)) {
            return R$drawable.fq_ic_live_msg_manager_big_2;
        }
        if (!isHouseSuperManager(str)) {
            return -1;
        }
        return R$drawable.fq_ic_live_msg_super_big_2;
    }

    @DrawableRes
    public static int getGuardIconRes(int i) {
        if (i == 1 || i == 2) {
            return R$drawable.fq_ic_live_msg_mouth_guard_big;
        }
        if (i == 3) {
            return R$drawable.fq_ic_live_msg_year_guard_big;
        }
        return -1;
    }

    public static String appendGiftStringWithIndex(GiftItemEntity giftItemEntity) {
        return com.blankj.utilcode.util.StringUtils.getString(R$string.fq_give_to_anchor) + giftItemEntity.name + com.blankj.utilcode.util.StringUtils.getString(R$string.fq_text_gift_multiple) + giftItemEntity.sendIndex;
    }

    public static String appendGiftStringNoIndex(GiftItemEntity giftItemEntity) {
        return com.blankj.utilcode.util.StringUtils.getString(R$string.fq_give_to_anchor) + giftItemEntity.name;
    }

    public static String appendBatchGiftString(GiftItemEntity giftItemEntity) {
        return com.blankj.utilcode.util.StringUtils.getString(R$string.fq_give_to_anchor) + giftItemEntity.name + ConstantUtils.PLACEHOLDER_STR_ONE + giftItemEntity.giftNum + com.blankj.utilcode.util.StringUtils.getString(R$string.fq_text_gift_multiple) + giftItemEntity.sendIndex;
    }

    public static void setTextViewLeftDrawable(Context context, TextView textView, @DrawableRes int i, int i2, int i3) {
        Drawable drawable = ContextCompat.getDrawable(context, i);
        drawable.setBounds(0, 0, ConvertUtils.dp2px(i2), ConvertUtils.dp2px(i3));
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static boolean isChatEnterMsg(String str, String str2, String str3, int i) {
        return isGuardUser(NumberUtils.string2int(str2)) || hasCar(str3) || isHouseSuperManager(str) || isHouseManager(str) || isNobilityUser(i);
    }

    public static String getCurrentOnlineUserXORField(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                sb.append((char) (str.charAt(i) ^ 486179962918L));
            }
            return new String(Base64Util.decode(sb.toString(), "UTF-8"));
        } catch (Exception unused) {
            return "";
        }
    }

    public static void toLiveHelperApp(final Context context, final String str, final String str2, FragmentManager fragmentManager) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (com.blankj.utilcode.util.AppUtils.isAppInstalled(str)) {
            if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
                return;
            }
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAppCommonCallbackListener(context, 275, new TomatoLiveSDK.OnCommonCallbackListener() { // from class: com.tomatolive.library.utils.AppUtils.1
                @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
                public void onDataSuccess(Context context2, Object obj) {
                    try {
                        if (!(obj instanceof String)) {
                            return;
                        }
                        String DESEncrypt = EncryptUtil.DESEncrypt("246887c3-ee20-4fe8-a320-1fde4a8d10b6", (String) obj);
                        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
                        launchIntentForPackage.addFlags(268435456);
                        launchIntentForPackage.putExtra("branchChannelId", TomatoLiveSDK.getSingleton().ADV_CHANNEL_ID);
                        launchIntentForPackage.putExtra("channelToken", DESEncrypt);
                        context2.startActivity(launchIntentForPackage);
                    } catch (Exception unused) {
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
                public void onDataFail(Throwable th, int i) {
                    Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
                    launchIntentForPackage.addFlags(268435456);
                    launchIntentForPackage.putExtra("branchChannelId", TomatoLiveSDK.getSingleton().ADV_CHANNEL_ID);
                    launchIntentForPackage.putExtra("channelToken", "");
                    context.startActivity(launchIntentForPackage);
                }
            });
            return;
        }
        SureCancelDialog.newInstance(context.getString(R$string.fq_tip), context.getString(R$string.fq_live_helper_download_tips), context.getString(R$string.fq_btn_cancel), context.getString(R$string.fq_download_now), new View.OnClickListener() { // from class: com.tomatolive.library.utils.AppUtils.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppUtils.onSysWebView(context, str2);
            }
        }).show(fragmentManager);
    }

    public static void handlerTokenInvalid(Context context) {
        if (context instanceof PrepareLiveActivity) {
            startDialogService(context, TokenDialogService.class);
            ((PrepareLiveActivity) context).finish();
        } else if (context instanceof TomatoLiveActivity) {
            startDialogService(context, TokenDialogService.class);
            ((TomatoLiveActivity) context).finish();
        } else if (TextUtils.isEmpty(UserInfoManager.getInstance().getToken())) {
        } else {
            TokenInvalidDialog.newInstance().show(((FragmentActivity) context).getSupportFragmentManager());
        }
    }
}
