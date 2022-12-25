package com.one.tomato.mvp.net;

import com.one.tomato.entity.AdLive;
import com.one.tomato.entity.AppIdHistory;
import com.one.tomato.entity.ApprealBean;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.ChangePoneVirify;
import com.one.tomato.entity.CircleCategory;
import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.CreatorCenterBean;
import com.one.tomato.entity.CreditRecord;
import com.one.tomato.entity.CreditRule;
import com.one.tomato.entity.FeedbackOrderCheck;
import com.one.tomato.entity.FocusHotPostListBean;
import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.GameBalance;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.GameSpreadDetail;
import com.one.tomato.entity.IncomeCenterAccount;
import com.one.tomato.entity.InspectRecord;
import com.one.tomato.entity.JAVDBBean;
import com.one.tomato.entity.JCOrderMsg;
import com.one.tomato.entity.JCOrderRecord;
import com.one.tomato.entity.JavActionBean;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.PostSame;
import com.one.tomato.entity.PostSerializeBean;
import com.one.tomato.entity.PromotionAccount;
import com.one.tomato.entity.PromotionBlockUrl;
import com.one.tomato.entity.PromotionFanyong;
import com.one.tomato.entity.PromotionFriend;
import com.one.tomato.entity.PromotionIncomeRecord;
import com.one.tomato.entity.PromotionMember;
import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.entity.PromotionTokenBean;
import com.one.tomato.entity.PromotionTop50;
import com.one.tomato.entity.PromotionWalletBalance;
import com.one.tomato.entity.PromotionWithdrawConfig;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.entity.RechargeFeedbackIssues;
import com.one.tomato.entity.RechargeList;
import com.one.tomato.entity.RechargeOnlineUnCallback;
import com.one.tomato.entity.RechargeOrder;
import com.one.tomato.entity.RechargeOrderConfirm;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.entity.RewardRecordBean;
import com.one.tomato.entity.SerializePostListBean;
import com.one.tomato.entity.SignBean;
import com.one.tomato.entity.SubscribeUpList;
import com.one.tomato.entity.TTSts;
import com.one.tomato.entity.TradePwdBean;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.entity.UpdateInfo;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.VipHistory;
import com.one.tomato.entity.VipPackage;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.InitAppInfo;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.LookTimeBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.PostHotSearch;
import com.one.tomato.entity.p079db.ShareParamsBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.entity.p079db.WebAppChannel;
import com.one.tomato.mvp.base.okhttp.RetrofitClient;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/* compiled from: ApiImplService.kt */
/* loaded from: classes3.dex */
public interface ApiImplService {
    public static final Companion Companion = Companion.$$INSTANCE;

    @FormUrlEncoded
    @POST("/app/advert/memberLogin")
    Observable<BaseResponse<Object>> activeApp(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("/app/serialGroupJoinArticle/add")
    Observable<BaseResponse<Object>> addPostToSerialize(@Field("articleIds") String str, @Field("serialGroupId") int i);

    @FormUrlEncoded
    @POST("/app/appeal/checkAccount")
    Observable<BaseResponse<Object>> apprealCheckAccount(@Field("account") String str, @Field("captchaCode") String str2);

    @FormUrlEncoded
    @POST("/app/appeal/checkPassword")
    Observable<BaseResponse<ApprealBean>> apprealCheckPassWord(@Field("account") String str, @Field("password") String str2, @Field("countryCode") String str3);

    @FormUrlEncoded
    @POST("/app/email/changeBindEmail")
    Observable<BaseResponse<Object>> bindChangeEmail(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/email/bindEmail")
    Observable<BaseResponse<Object>> bindEmail(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/record/shield/user/cancelByMemberId")
    Observable<BaseResponse<Object>> cancelShield(@Field("shieldMemberId") int i, @Field("memberId") int i2);

    @FormUrlEncoded
    @POST("/app/withdraw/reApply")
    Observable<BaseResponse<Object>> cashPaymentAgain(@Field("orderId") String str, @Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/withdraw/reFund")
    Observable<BaseResponse<Object>> cashReturnAccount(@Field("orderId") String str, @Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/email/changeEmail/auth")
    Observable<BaseResponse<ChangePoneVirify>> changeEmailByVerify(@Field("memberId") int i, @Field("verifyCode") String str);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<Object>> circleFllow(@Url String str, @Field("memberId") int i, @Field("groupId") int i2);

    @FormUrlEncoded
    @POST("/app/serialGroup/create")
    Observable<BaseResponse<Object>> createSerialize(@Field("title") String str, @Field("articleIds") String str2);

    @FormUrlEncoded
    @POST("/app/comment/authorDeleteComment")
    Observable<BaseResponse<Object>> delateAuthComment(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/comment/delete")
    Observable<BaseResponse<Object>> delateComment(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/publish/delete")
    Observable<BaseResponse<Object>> deleteMyPushPost(@Field("articleIds") String str, @Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/serialGroup/delete")
    Observable<BaseResponse<Object>> deleteSerialize(@Field("serialGroupIds") String str);

    @FormUrlEncoded
    @POST("/app/tag/remvTagInfo")
    Observable<BaseResponse<Object>> deleteTag(@Field("tagId") int i, @Field("tagName") String str, @Field("articleId") int i2);

    @FormUrlEncoded
    @POST("/app/article/download/save")
    Observable<BaseResponse<Object>> downLoadRecordSave(@Field("memberId") int i, @Field("articleId") Integer num, @Field("payType") int i2);

    @FormUrlEncoded
    @POST("/app/article/download/check")
    Observable<BaseResponse<VideoPay>> downLoadVideoCheck(@Field("memberId") int i, @Field("initType") int i2);

    @FormUrlEncoded
    @POST("/app/support/isFaver")
    Observable<BaseResponse<Object>> faverPost(@Field("memberId") int i, @Field("articleId") int i2);

    @FormUrlEncoded
    @POST("/app/newFeedback/save")
    Observable<BaseResponse<Object>> feedbackRecharge(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("/app/memberInfo/sendVerifyCode")
    Observable<BaseResponse<Object>> forgetPsBySendPhone(@Field("countryCode") String str, @Field("phone") String str2, @Field("moduleType") int i);

    @FormUrlEncoded
    @POST("/app/memberInfo/userAccountLogin")
    Observable<BaseResponse<LoginInfo>> getAccountInfoFromService(@Field("account") String str, @Field("countryCode") String str2, @Field("password") String str3);

    @POST("/app/appId/history")
    Observable<BaseResponse<ArrayList<AppIdHistory>>> getAppIdHistory();

    @FormUrlEncoded
    @POST("/app/memberInfo/getUserInfo")
    Observable<BaseResponse<InitAppInfo>> getAppInfoFormService(@Field("deviceNo") String str, @Field("token") String str2, @Field("versionNo") String str3, @Field("parentInviteCode") String str4);

    @POST("/app/memberInfo/country")
    Observable<BaseResponse<ArrayList<CountryDB>>> getCountryList();

    @FormUrlEncoded
    @POST("/app/article/list")
    Observable<BaseResponse<ArrayList<PostList>>> getImageVideoPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @Headers({"serverType:report"})
    @POST("/app/article/initLookTimes")
    Observable<BaseResponse<LookTimeBean>> getLookTime(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/article/video/list/new")
    Observable<BaseResponse<ArrayList<PostList>>> getNewPaPaListData(@Field("pageNo") int i, @Field("pageSize") int i2, @Field("memberId") int i3);

    @FormUrlEncoded
    @POST("/app/article/video/pay/list")
    Observable<BaseResponse<ArrayList<PostList>>> getNewPaPaPayListData(@Field("pageNo") int i, @Field("pageSize") int i2, @Field("memberId") int i3);

    @FormUrlEncoded
    @POST("/app/memberInfo/infoData")
    Observable<BaseResponse<UserInfo>> getPersonInfo(@Field("attentionMemberId") int i, @Field("memberId") int i2);

    @FormUrlEncoded
    @POST("/app/comment/commentList")
    Observable<BaseResponse<ArrayList<CommentList>>> getPostCommentList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/article/recommendArticle/new")
    Observable<BaseResponse<ArrayList<PostList>>> getRecommendPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<ArrayList<PostList>>> getReviewPostPre(@Url String str, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/sts/getToken")
    Observable<BaseResponse<TTSts>> getSTSToken(@Field("memberId") int i, @Field("md5") String str, @Field("fileSize") long j);

    @FormUrlEncoded
    @POST("/app/version/list")
    Observable<BaseResponse<UpdateInfo>> getUpdateInfo(@Field("versionNo") String str);

    @FormUrlEncoded
    @POST("/app/memberInfo/member")
    Observable<BaseResponse<UserInfo>> getUserInfoFormService(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberInfo/logout")
    Observable<BaseResponse<LoginInfo>> loginOut(@Field("deviceNo") String str, @Field("memberId") int i, @Field("token") String str2);

    @FormUrlEncoded
    @POST("/app/serialGroupJoinArticle/move")
    Observable<BaseResponse<Object>> moveSerializePost(@Field("articleId") String str, @Field("serialGroupId") int i, @Field("sortNum") long j);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<ArrayList<PostList>>> postCirclePost(@Url String str, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/favor/save")
    Observable<BaseResponse<Object>> postCollect(@Field("memberId") int i, @Field("articleId") int i2);

    @FormUrlEncoded
    @POST("/app/favor/list")
    Observable<BaseResponse<ArrayList<PostList>>> postCollectPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> postCommentTumb(@Url String str, @Field("memberId") int i, @Field("commentId") int i2);

    @FormUrlEncoded
    @POST("/app/memberFollow/saveAndCancel")
    Observable<BaseResponse<Object>> postFoucs(@Field("memberId") int i, @Field("attentionMemberId") int i2, @Field("flag") int i3);

    @POST("/app/notice/newTopNoticeList")
    Observable<BaseResponse<ArrayList<MainNotifyBean>>> postHomeMainNotify();

    @FormUrlEncoded
    @POST("/app/article/member/list")
    Observable<BaseResponse<ArrayList<PostList>>> postPersonPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/publish/list")
    Observable<BaseResponse<ArrayList<PostList>>> postPushPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/search/article")
    Observable<BaseResponse<ArrayList<PostList>>> postSeachPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/rank/article")
    Observable<BaseResponse<ArrayList<PostList>>> postSeachRecommendPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/record/shield/add")
    Observable<BaseResponse<Object>> postShield(@Field("createMemberId") int i, @Field("type") int i2, @Field("memberId") int i3);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<PostList>> postSinglePostDetail(@Url String str, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/search/tag")
    Observable<BaseResponse<ArrayList<PostList>>> postTagPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/support/list")
    Observable<BaseResponse<ArrayList<PostList>>> postThumbPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @Headers({"serverType:report"})
    @POST("/app/article/browser")
    Observable<BaseResponse<Object>> postUpdatePostBro(@Field("id") int i, @Field("memberId") int i2);

    @POST("/app/game/center/listMemberNameByRandom")
    Observable<BaseResponse<GameRegisterUserBean>> queryGameUser();

    @FormUrlEncoded
    @POST("/app/messageCenter/queryUnReadMessageCount")
    Observable<BaseResponse<String>> queryMyMessage(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/serialGroupJoinArticle/remove")
    Observable<BaseResponse<Object>> removeSerializePost(@Field("articleIds") String str, @Field("serialGroupId") int i);

    @FormUrlEncoded
    @POST("/app/serialGroup/modify")
    Observable<BaseResponse<Object>> renameSerializeTitle(@Field("title") String str, @Field("serialGroupId") int i);

    @FormUrlEncoded
    @POST("/app/log/report")
    Observable<BaseResponse<Object>> reportCrashInfo(@Field("info") String str);

    @FormUrlEncoded
    @POST("/app/share/report")
    Observable<BaseResponse<Object>> reportPostShare(@Field("id") String str);

    @FormUrlEncoded
    @POST("/app/withdraw/queryProfitBalance")
    Observable<BaseResponse<IncomeCenterAccount>> requestAccountAll(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/actor/report")
    Observable<BaseResponse<Object>> requestActorReport(@FieldMap Map<String, Object> map);

    @GET("/app/advert/init")
    Observable<BaseResponse<C2516Ad>> requestAd();

    @FormUrlEncoded
    @POST("/app/tag/addTagInfo")
    Observable<BaseResponse<Object>> requestAddTagToPost(@Field("tagId") int i, @Field("tagName") String str, @Field("articleId") int i2);

    @FormUrlEncoded
    @POST("/app/memberUp/saveForUpInfo")
    Observable<BaseResponse<Object>> requestApplyUp(@Field("memberId") int i, @Field("applyReason") String str, @Field("originalFlag") int i2);

    @FormUrlEncoded
    @POST("/app/category/all")
    Observable<BaseResponse<ArrayList<CircleCategory>>> requestCategoryCircleAll(@Field("pageNo") int i, @Field("pageSize") int i2);

    @POST("/app/channelMgr/listChannelMgr")
    Observable<BaseResponse<UserChannelBean>> requestChannelList();

    @FormUrlEncoded
    @POST("/app/payment/feedbackIsYN")
    Observable<BaseResponse<FeedbackOrderCheck>> requestCheckOrder(@Field("memberId") int i, @Field("orderId") String str);

    @POST("/app/yule/index")
    Observable<BaseResponse<ArrayList<ChessTypeBean>>> requestChessHomeData();

    @FormUrlEncoded
    @POST("app/circle/listAll")
    Observable<BaseResponse<ArrayList<CircleAllBean>>> requestCircleAll(@Field("memberId") int i, @Field("appId") int i2);

    @FormUrlEncoded
    @POST("/app/discover/queryDiscoverList")
    Observable<BaseResponse<ArrayList<CircleDiscoverListBean>>> requestCircleDiscoverHome(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberUp/creatorCenterInfo")
    Observable<BaseResponse<CreatorCenterBean>> requestCreatorCenterBean(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/prestige/selectPrestigeDetail")
    Observable<BaseResponse<ArrayList<CreditRecord>>> requestCreditRecord(@FieldMap HashMap<String, String> hashMap);

    @POST("/app/prestige/selectPrestigeRule")
    Observable<BaseResponse<CreditRule>> requestCreditRule();

    @FormUrlEncoded
    @POST("/app/comment/adminDeleteComment")
    Observable<BaseResponse<Object>> requestDownComment(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> requestEx(@Url String str, @Field("amount") String str2, @Field("gameBrandId") String str3);

    @FormUrlEncoded
    @POST("/app/walletGame/listWalletGameCfg")
    Observable<BaseResponse<ArrayList<RechargeExGame>>> requestExGameList(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/support/save")
    Observable<BaseResponse<Object>> requestFeedbackSearch(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("/app/article/followList")
    Observable<BaseResponse<FocusHotPostListBean>> requestFoucsPostList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/liveGame/queryBalance")
    Observable<BaseResponse<GameBalance>> requestGameBalance(@Field("memberId") int i, @Field("gameBrandId") String str);

    @FormUrlEncoded
    @POST("/app/liveGame/getGameToken")
    Observable<BaseResponse<GameBingBingToken>> requestGameBingBingToken(@Field("gameBrandId") String str);

    @FormUrlEncoded
    @POST("/app/game/center/listV3")
    Observable<BaseResponse<ArrayList<GameTypeData>>> requestGameCenterList(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("/app/liveGame/daSheng/loginH5")
    Observable<BaseResponse<GameDaShengH5>> requestGameDaShengH5(@Field("memberId") int i, @Field("gameId") String str, @Field("type") int i2);

    @FormUrlEncoded
    @POST("/app/liveGame/daSheng/login")
    Observable<BaseResponse<GameDaShengWebapp>> requestGameDaShengWebapp(@Field("memberId") int i, @Field("gameId") String str);

    @FormUrlEncoded
    @POST("/app/game/center/list")
    Observable<BaseResponse<ArrayList<AdPage>>> requestGameList(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("/app/spreadGame/detail")
    Observable<BaseResponse<GameSpreadDetail>> requestGameSpreadDetail(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberUp/queryNeedPaidArticles")
    Observable<BaseResponse<ArrayList<PostList>>> requestHomePageSubscribeList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/hotEvents/listByPage")
    Observable<BaseResponse<ArrayList<PostHotMessageBean>>> requestHotEventList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/hotEvents/listEventArticle")
    Observable<BaseResponse<ArrayList<PostList>>> requestHotEventPostList(@Field("eventId") String str);

    @FormUrlEncoded
    @POST("/app/search/hotList")
    Observable<BaseResponse<ArrayList<PostHotSearch>>> requestHotSearch(@Field("memberId") int i);

    @POST("/app/tag/hotTagInfo")
    Observable<BaseResponse<ArrayList<Tag>>> requestHotTag();

    @FormUrlEncoded
    @POST("/app/withdraw/money/balance")
    Observable<BaseResponse<BalanceBean>> requestIncomeAccountBalance(@Field("memberId") int i, @Field("accountType") int i2);

    @FormUrlEncoded
    @POST("/app/liveGame/recharge/auditList")
    Observable<BaseResponse<ArrayList<InspectRecord>>> requestInspectList(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/newFeedback/findFrequentQaList")
    Observable<BaseResponse<ArrayList<RechargeFeedbackIssues>>> requestIssues(@FieldMap HashMap<String, Object> hashMap);

    @POST("/app/actor/listHot")
    Observable<BaseResponse<ArrayList<JavActionBean>>> requestJAVDB30();

    @Headers({"serverType:jc"})
    @POST
    Observable<JCOrderMsg> requestJCOrderMsg(@Url String str, @Body RequestBody requestBody);

    @Headers({"serverType:jc"})
    @POST
    Observable<JCOrderRecord> requestJCOrders(@Url String str, @Body RequestBody requestBody);

    @Headers({"serverType:jav"})
    @POST
    @Multipart
    Observable<BaseResponse<JAVDBBean>> requestJavDB(@Url String str, @Part("client_name") RequestBody requestBody, @Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("/app/memberInfo/levelInfo")
    Observable<BaseResponse<LevelBean>> requestLevelInfo(@Field("memberId") int i);

    @GET("/app/advert/live/init")
    Observable<BaseResponse<ArrayList<AdLive>>> requestLiveAd();

    @FormUrlEncoded
    @POST("/app/liveGame/h5common/login")
    Observable<BaseResponse<GameBGLoginBean>> requestLiveGameBGLogin(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<LoginInfo>> requestLogin(@Url String str, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/login/qrCodeLogin")
    Observable<BaseResponse<Object>> requestLoginCode(@Field("code") String str);

    @FormUrlEncoded
    @POST("/app/rank/papa/mostHotDetail")
    Observable<BaseResponse<ArrayList<PostList>>> requestMostHotVideoDetailList(@Field("dateType") int i, @Field("pageNo") int i2, @Field("pageSize") int i3);

    @POST("/app/rank/papa/mostHot")
    Observable<BaseResponse<ArrayList<PostList>>> requestMostHotVideoList();

    @FormUrlEncoded
    @POST("/app/article/new")
    Observable<BaseResponse<ArrayList<PostList>>> requestNewPost(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/payment/getZXOrders")
    Observable<BaseResponse<ArrayList<RechargeOnlineUnCallback>>> requestOnlineProblemOrders(@Field("memberId") int i);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> requestPhoneCode(@Url String str, @Field("countryCode") String str2, @Field("phone") String str3, @Field("moduleType") int i);

    @FormUrlEncoded
    @POST("/app/publish/deleteArticle")
    Observable<BaseResponse<Object>> requestPostDown(@Field("operateType") int i, @Field("operateTableId") int i2, @Field("remark") String str);

    @FormUrlEncoded
    @POST("/app/discover/queryRecommendList")
    Observable<BaseResponse<ArrayList<CircleDiscoverListBean>>> requestPostInsertCircle(@Field("appId") int i);

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<ArrayList<PostList>>> requestPostRankList(@Url String str, @Field("pageNo") int i, @Field("pageSize") int i2, @Field("type") int i3);

    @FormUrlEncoded
    @POST("/app/channelMgr/queryArticlesByChannelId")
    Observable<BaseResponse<ArrayList<PostList>>> requestPostToChannelId(@FieldMap Map<String, Object> map);

    @Headers({"serverType:promotion"})
    @POST
    Observable<BaseResponse<PromotionAccount>> requestPromotionAccount(@Url String str, @Body RequestBody requestBody);

    @Headers({"serverType:promotion"})
    @POST
    Observable<BaseResponse<PromotionBlockUrl>> requestPromotionBlockUrl(@Url String str, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/app/vip/nonactivated/list")
    Observable<BaseResponse<ArrayList<PromotionFriend>>> requestPromotionFriend(@Field("memberId") int i, @Field("pageNo") int i2, @Field("pageSize") int i3);

    @Headers({"serverType:promotion"})
    @POST
    Observable<PromotionIncomeRecord> requestPromotionIncomeRecord(@Url String str, @Body RequestBody requestBody);

    @Headers({"serverType:promotion"})
    @POST
    Observable<PromotionOrderRecord> requestPromotionOrderRecord(@Url String str, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/app/vip/generateUserToken")
    Observable<BaseResponse<PromotionTokenBean>> requestPromotionToken(@Field("memberId") int i);

    @Headers({"serverType:promotion"})
    @POST
    Observable<PromotionTop50> requestPromotionTop50(@Url String str, @Body RequestBody requestBody);

    @Headers({"serverType:promotion"})
    @POST
    Observable<BaseResponse<PromotionWalletBalance>> requestPromotionWalletBalance(@Url String str, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/app/vip/withdraw")
    Observable<BaseResponse<Object>> requestPromotionWithdraw(@Field("memberId") int i, @Field("amount") int i2, @Field("phone") String str, @Field("countryCode") String str2, @Field("sec") String str3);

    @Headers({"serverType:promotion"})
    @POST
    Observable<BaseResponse<PromotionWithdrawConfig>> requestPromotionWithdrawConfig(@Url String str, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/app/memberUp/queryAchievement")
    Observable<BaseResponse<UpStatusBean>> requestQueryAchievement(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberUp/queryUpStatusInfo")
    Observable<BaseResponse<UpStatusBean>> requestQueryUpStatusInfo(@Field("memberId") int i, @Field("originalFlag") int i2);

    @FormUrlEncoded
    @POST("/app/rank/papa")
    Observable<BaseResponse<ArrayList<PostList>>> requestRankPaPa(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/payment/queryTomatoCoinBalance")
    Observable<BaseResponse<RechargeAccount>> requestRechargeAccount(@Field("memberId") int i);

    @POST("/app/payment/getDataList")
    Observable<BaseResponse<ArrayList<RechargeList>>> requestRechargeList();

    @FormUrlEncoded
    @POST("/app/payment/reCharge")
    Observable<BaseResponse<RechargeOrder>> requestRechargeOnline(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/payment/queryRechargeStatusByOrderId")
    Observable<BaseResponse<RechargeOrderConfirm>> requestRechargeOnlineConfirm(@FieldMap Map<String, Object> map);

    @POST("/app/payment/channelAndFaceValue")
    Observable<BaseResponse<ArrayList<RechargeTypeAndMoney>>> requestRechargeTypeAndMoneyList();

    @FormUrlEncoded
    @POST("/app/memberUp/recommendUpList")
    Observable<BaseResponse<ArrayList<UpRecommentBean>>> requestRecommentUpList(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/walletGame/recoveryFromGame")
    Observable<BaseResponse<Object>> requestRecycleAll(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberInfo/updateMemberInfo")
    Observable<BaseResponse<Object>> requestRegisterUpdate(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/notice/listAll")
    Observable<BaseResponse<ArrayList<MainNotifyBean>>> requestReviewNotice(@Field("type") String str);

    @FormUrlEncoded
    @POST("/app/upzhu/pay")
    Observable<BaseResponse<String>> requestRewardPay(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/memberUp/saveSubscribeInfo")
    Observable<BaseResponse<Object>> requestSaveSubscribeInfo(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/tag/searchTagInfo")
    Observable<BaseResponse<ArrayList<Tag>>> requestSearchTag(@Field("tagName") String str);

    @FormUrlEncoded
    @POST("/app/serialGroup/listAll")
    Observable<BaseResponse<ArrayList<PostSerializeBean>>> requestSerializeGroup(@Field("personMemberId") int i);

    @FormUrlEncoded
    @POST("/app/serialGroupJoinArticle/listAll")
    Observable<BaseResponse<SerializePostListBean>> requestSerializePostList(@Field("serialGroupId") int i);

    @FormUrlEncoded
    @POST("/app/memberInfo/signIn")
    Observable<BaseResponse<Object>> requestSign(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberInfo/querySignInDays")
    Observable<BaseResponse<SignBean>> requestSignDay(@Field("memberId") int i);

    @POST("/app/vip/readConfig")
    Observable<BaseResponse<PromotionFanyong>> requestSpreadFanyong();

    @FormUrlEncoded
    @POST("/app/vip/querySpreadMembersCount")
    Observable<BaseResponse<PromotionMember>> requestSpreadMember(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/memberUp/queryUpSubscribeInfo")
    Observable<BaseResponse<ArrayList<SubscribeUpList>>> requestSubscribeUpList(@Field("memberId") int i, @Field("pageNo") int i2, @Field("pageSize") int i3);

    @GET("/app/init/sysParam")
    Observable<BaseResponse<SystemParam>> requestSystemParams();

    @FormUrlEncoded
    @POST("/app/memberUp/listSubscriber")
    Observable<BaseResponse<ArrayList<SubscribeUpList>>> requestUpListSubscribe(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/upzhu/payList")
    Observable<BaseResponse<ArrayList<RewardRecordBean>>> requestUpPayRecordList(@Field("articleId") long j, @Field("pageNo") int i, @Field("pageSize") int i2);

    @FormUrlEncoded
    @POST("/app/memberUp/upIncomeRankList")
    Observable<BaseResponse<ArrayList<UpRankListBean>>> requestUpRankList(@Field("type") int i, @Field("topListType") int i2);

    @FormUrlEncoded
    @POST("/app/upzhu/paySubscribe")
    Observable<BaseResponse<String>> requestUpSubscribePay(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/memberInfo/modify")
    Observable<BaseResponse<Object>> requestUpdateUserInfo(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/article//video/new")
    Observable<BaseResponse<ArrayList<PostList>>> requestVideoNew(@Field("pageNo") int i, @Field("pageSize") int i2, @Field("memberId") int i3);

    @FormUrlEncoded
    @POST("/app/vip/buy/list")
    Observable<BaseResponse<ArrayList<VipHistory>>> requestVipHistory(@Field("memberId") int i, @Field("pageNo") int i2, @Field("pageSize") int i3);

    @POST("/app/vip/package/list")
    Observable<BaseResponse<ArrayList<VipPackage>>> requestVipPackage();

    @POST("/app/mini/program/getChannel")
    Observable<BaseResponse<ArrayList<WebAppChannel>>> requestWebappChannel();

    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> reviewPost(@Url String str, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/email/sendVerifyCode")
    Observable<BaseResponse<Object>> senMail(@Field("mail") String str, @Field("moduleType") int i);

    @FormUrlEncoded
    @POST("/app/acticleComments/save")
    Observable<BaseResponse<CommentList>> sendComment(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/article/publishArticle")
    Observable<BaseResponse<PostSame>> sendPost(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("/app/acticleComments/save")
    Observable<BaseResponse<CommentList>> sendReplyComment(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/email/sendVerifyCodeByMemberId")
    Observable<BaseResponse<Object>> sendVerifyEmailCode(@Field("memberId") int i);

    @FormUrlEncoded
    @POST("/app/share/address")
    Observable<BaseResponse<ShareParamsBean>> shareApp(@Field("branch") int i);

    @FormUrlEncoded
    @POST("/app/share/list")
    Observable<BaseResponse<ShareParamsBean>> sharePost(@Field("memberId") int i, @Field("branch") int i2);

    @FormUrlEncoded
    @Headers({"serverType:report"})
    @POST("/app/advert/report")
    Observable<BaseResponse<Object>> uploadAD(@Field("adId") int i, @Field("memberId") int i2, @Field("type") int i3);

    @FormUrlEncoded
    @POST("/app/report/saveReportApplication")
    Observable<BaseResponse<Object>> uploadAppListInfo(@Field("json") String str);

    @FormUrlEncoded
    @Headers({"serverType:report"})
    @POST("/app/error/report")
    Observable<BaseResponse<Object>> uploadPlayError(@Field("errType") String str, @Field("errMsg") String str2);

    @FormUrlEncoded
    @POST("/app/memberInfo/saveQrCode")
    Observable<BaseResponse<Object>> uploadQRToServer(@Field("memberId") int i);

    @FormUrlEncoded
    @Headers({"serverType:report"})
    @POST("/app/init/usedTime")
    Observable<BaseResponse<Object>> uploadUseTime(@Field("time") long j, @Field("memberId") int i, @Field("userType") int i2);

    @FormUrlEncoded
    @POST("/app/report/article/half")
    Observable<BaseResponse<Object>> uploadVideoPlayHalf(@Field("articleId") int i, @Field("memberId") int i2);

    @FormUrlEncoded
    @POST("/app/report/article/whole")
    Observable<BaseResponse<Object>> uploadVideoPlayWhole(@Field("articleId") int i, @Field("memberId") int i2);

    @FormUrlEncoded
    @POST("/app/email/validateEmailCode")
    Observable<BaseResponse<ForgetPsSec>> varifyEmail(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/memberInfo/validatePhoneAndCode")
    Observable<BaseResponse<ForgetPsSec>> verifyPhoneCode(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/app/withdraw/person/checkTransactionPwd")
    Observable<BaseResponse<TradePwdBean>> verifyTradePassword(@Field("memberId") int i, @Field("pwd") String str);

    /* compiled from: ApiImplService.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ApiImplService getApiImplService() {
            Object create = RetrofitClient.getInstance().create(ApiImplService.class);
            Intrinsics.checkExpressionValueIsNotNull(create, "RetrofitClient.getInstan…iImplService::class.java)");
            return (ApiImplService) create;
        }
    }
}
