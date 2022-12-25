package com.tomatolive.library.p136ui.activity.live;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.R$style;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BackpackItemEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.BaseGiftBackpackEntity;
import com.tomatolive.library.model.BoomStatusEntity;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.model.CheckTicketEntity;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.model.GiftBatchItemEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftIndexEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveInitInfoEntity;
import com.tomatolive.library.model.LiveItemEntity;
import com.tomatolive.library.model.LotteryBoomDetailEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PKRecordEntity;
import com.tomatolive.library.model.PropConfigEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.model.TrumpetStatusEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.UserPrivateMessageEntity;
import com.tomatolive.library.model.WatermarkConfigEntity;
import com.tomatolive.library.model.event.AttentionEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.model.event.LiveTopAttentionEvent;
import com.tomatolive.library.model.event.LoginEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.model.event.UpdateBalanceEvent;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import com.tomatolive.library.model.p135db.LiveDataEntity;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.model.p135db.MsgListEntity;
import com.tomatolive.library.model.p135db.MsgStatusEntity;
import com.tomatolive.library.model.p135db.WatchRecordEntity;
import com.tomatolive.library.p136ui.activity.live.TomatoLiveFragment;
import com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener;
import com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback;
import com.tomatolive.library.p136ui.interfaces.OnPkViewListener;
import com.tomatolive.library.p136ui.interfaces.WebViewJSCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleHdLotteryCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.presenter.TomatoLivePresenter;
import com.tomatolive.library.p136ui.view.custom.ComponentsView;
import com.tomatolive.library.p136ui.view.custom.GiftBoxView;
import com.tomatolive.library.p136ui.view.custom.HdLotteryWindowView;
import com.tomatolive.library.p136ui.view.custom.LiveAdBannerBottomView;
import com.tomatolive.library.p136ui.view.custom.LiveAnimationView;
import com.tomatolive.library.p136ui.view.custom.LiveChatMsgView;
import com.tomatolive.library.p136ui.view.custom.LiveEndInfoView;
import com.tomatolive.library.p136ui.view.custom.LiveLoadingView;
import com.tomatolive.library.p136ui.view.custom.LivePayEnterView;
import com.tomatolive.library.p136ui.view.custom.LivePusherInfoView;
import com.tomatolive.library.p136ui.view.custom.PKInfoView;
import com.tomatolive.library.p136ui.view.custom.TaskBoxView;
import com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils;
import com.tomatolive.library.p136ui.view.dialog.ComponentsDialog;
import com.tomatolive.library.p136ui.view.dialog.ComponentsWebViewDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftBoxPresenterDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftWallDialog;
import com.tomatolive.library.p136ui.view.dialog.GuardListDialog;
import com.tomatolive.library.p136ui.view.dialog.GuardOpenContentDialog;
import com.tomatolive.library.p136ui.view.dialog.GuardOpenTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.HdLotteryDrawingDialog;
import com.tomatolive.library.p136ui.view.dialog.InputTextMsgForAudienceDialog;
import com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog;
import com.tomatolive.library.p136ui.view.dialog.LiveEndEvaluationDialog;
import com.tomatolive.library.p136ui.view.dialog.LiveMoreDialog;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import com.tomatolive.library.p136ui.view.dialog.NobilityOpenTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.PKRankDialog;
import com.tomatolive.library.p136ui.view.dialog.PayLiveTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.PrivateMsgDialog;
import com.tomatolive.library.p136ui.view.dialog.QMInteractUserDialog;
import com.tomatolive.library.p136ui.view.dialog.QMTaskListUserDialog;
import com.tomatolive.library.p136ui.view.dialog.TaskBottomDialog;
import com.tomatolive.library.p136ui.view.dialog.UserAchieveDialog;
import com.tomatolive.library.p136ui.view.dialog.UserGuardAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNobilityAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNormalAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserSuperAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.WebViewDialog;
import com.tomatolive.library.p136ui.view.dialog.WeekStarRankingDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.ChatTipDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.ComposeDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.ComposeSuccessDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.NetworkPromptDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.TransDialog;
import com.tomatolive.library.p136ui.view.gift.GiftAnimModel;
import com.tomatolive.library.p136ui.view.gift.GiftFrameLayout;
import com.tomatolive.library.p136ui.view.iview.ITomatoLiveView;
import com.tomatolive.library.p136ui.view.task.TaskBoxUtils;
import com.tomatolive.library.p136ui.view.widget.QMNoticeAnimView;
import com.tomatolive.library.p136ui.view.widget.QMNoticeWindow;
import com.tomatolive.library.p136ui.view.widget.badgeView.QBadgeView;
import com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils;
import com.tomatolive.library.service.KickDialogService;
import com.tomatolive.library.service.TokenDialogService;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.HandlerUtils;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.MD5Utils;
import com.tomatolive.library.utils.NetUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.ReSizeUtils;
import com.tomatolive.library.utils.RxTimerUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.SwipeAnimationController;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.SystemUtils;
import com.tomatolive.library.utils.TranslationUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.PlayManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack;
import com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener;
import com.tomatolive.library.websocket.nvwebsocket.ConnectSocketParams;
import com.tomatolive.library.websocket.nvwebsocket.MessageHelper;
import com.tomatolive.library.websocket.nvwebsocket.WsManager;
import com.tomatolive.library.websocket.nvwebsocket.WsStatus;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment */
/* loaded from: classes3.dex */
public class TomatoLiveFragment extends BaseFragment<TomatoLivePresenter> implements ITomatoLiveView, BackgroundSocketCallBack, InputTextMsgForAudienceDialog.OnTextSendListener, GiftFrameLayout.BarrageEndAnimationListener, GuardOpenContentDialog.OnOpenGuardCallbackListener, PrivateMsgDialog.SendPrivateMsgListener {
    private static final long DURATION_GET_MESSAGE = 1000;
    private static final String LIVE_ITEM = "LIVE_ITEM";
    private static final int MAX_GET_ITEM_NUM_ONCE = 5;
    private static final int POST_INTERVAL_BASE = 3;
    private String anchorAppId;
    private UserNormalAvatarDialog anchorAvatarDialog;
    private String anchorId;
    private AnchorEntity anchorItemEntity;
    private UserNobilityAvatarDialog anchorNobilityAvatarDialog;
    private String banPostTimeLeft;
    private Bundle bundleArgs;
    private ComponentsEntity cacheRecommendComponents;
    private Disposable cdDisposable;
    private String chatContent;
    private ComponentsDialog componentsDialog;
    private ComponentsWebViewDialog componentsWebViewDialog;
    private SocketMessageEvent.ResultData curAnchorInfoNoticeEntity;
    private String curBigAnimSendUserId;
    private SocketMessageEvent.ResultData curGameNoticeEntity;
    private volatile boolean getUserBalanceFail;
    private GiftBackpackDialog giftBottomDialog;
    private View giftButton;
    private GiftWallDialog giftWallDialog;
    private GuardItemEntity guardItemEntity;
    private GuardListDialog guardListDialog;
    private GuardOpenContentDialog guardOpenContentDialog;
    private HdLotteryDrawingDialog hdLotteryDrawingDialog;
    private volatile boolean isAllBan;
    private boolean isBanGroup;
    private boolean isContinueCombo;
    private volatile boolean isGetGiftListFail;
    private boolean isGiftListUpdating;
    private volatile boolean isNormalBan;
    private boolean isPausing;
    private boolean isShowRetryRoomInfo;
    private volatile boolean isSocketReConn;
    private boolean isStartGetAnchorInfo;
    private volatile boolean isStartGetRoomInfo;
    private volatile boolean isSuperBan;
    private boolean isTranOpen;
    private ImageView ivClosed;
    private ComponentsView ivComponentsView;
    private ImageView ivMore;
    private ComponentsView ivRecommendComponents;
    private ImageView ivWatermarkLogo;
    private LiveEndEntity lastLiveEndEntity;
    private String lastMsg;
    private LiveAnimationView liveAnimationView;
    private String liveCount;
    private LiveEndEvaluationDialog liveEndEvaluationDialog;
    private String liveId;
    private LiveItemEntity liveItemEntity;
    private LiveEntity liveListItemEntity;
    private LiveMoreDialog liveMoreDialog;
    private LivePayEnterView livePayEnterView;
    private LoadingDialog loadingDialog;
    private String luckNoticeLiveId;
    private ImageView mAnchorCoverImg;
    private GiftBoxView mGiftBoxView;
    private InputTextMsgForAudienceDialog mInputTextMsgDialog;
    private LiveAdBannerBottomView mLiveAdBannerBottomView;
    private RelativeLayout mLiveBgView;
    private LiveChatMsgView mLiveChatMsgView;
    private LiveEndInfoView mLiveEndInfoView;
    private View mLiveGuideView;
    private ViewStub mLiveGuideViewVs;
    private LiveLoadingView mLiveLoadingView;
    private LivePusherInfoView mLivePusherInfoView;
    private LotteryDialog mLotteryDialog;
    private PKInfoView mPKInfoView;
    private Disposable mPKTimerDisposable;
    private RelativeLayout mRlControllerView;
    private TaskBottomDialog mTaskBottomDialog;
    private WeekStarRankingDialog mWeekStarRankingDialog;
    private Handler mainHandler;
    private int myNobilityType;
    private UserEntity myUserInfoEntity;
    private String myWeekStar;
    private SocketMessageEvent myselfEnterMessageEvent;
    private SureCancelDialog offlinePrivateMsgDialog;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private UserCardCallback onUserCardCallback;
    private PayLiveTipsDialog payLiveTipsDialog;
    private PKRankDialog pkRankDialog;
    private PlayManager playManager;
    private PrivateMsgDialog privateMsgDialog;
    private String pullStreamUrl;
    private Disposable pullTimeOutTimer;
    private QBadgeView qBadgeView;
    private QMInteractUserDialog qmInteractUserDialog;
    private SureCancelDialog qmInviteSureDialog;
    private QMNoticeAnimView qmNoticeAnimView;
    private QMTaskListUserDialog qmTaskListUserDialog;
    private volatile boolean reConnectCountOver;
    private RelativeLayout rlWatermarkShadowBg;
    private FrameLayout rootView;
    private String socketEncryptionKey;
    private String socketUrl;
    private boolean startGetGiftListInfo;
    private SwipeAnimationController swipeAnimationController;
    private SocketMessageEvent.ResultData tempSysNoticeResultData;
    private View titleTopView;
    private TransDialog transDialog;
    private boolean trumpetStatus;
    private TextView tvInput;
    private TextView tvWatermarkTitle;
    private TextView tvWatermarkUrl;
    private String uploadDataEnterSource;
    private UserAchieveDialog userAchieveDialog;
    private UserNormalAvatarDialog userAvatarDialog;
    private UserGuardAvatarDialog userGuardAvatarDialog;
    private UserNobilityAvatarDialog userNobilityAvatarDialog;
    private UserSuperAvatarDialog userSuperAvatarDialog;
    private WebViewDialog webViewDialog;
    private Handler workHandler;
    private WsManager wsManager;
    private final int CONTENT_TYPE_LIVE = 256;
    private final int CONTENT_TYPE_LIVE_END = 257;
    private final int CONTENT_TYPE_LIVE_RESET = 258;
    private final int CONTENT_TYPE_LIVE_PAY = 259;
    private volatile boolean isTaskSocket = false;
    private volatile boolean isFirstLoadTask = true;
    private volatile boolean isConnectingChatService = true;
    private volatile boolean isSocketClose = true;
    private volatile boolean isSocketError = true;
    private int postIntervalTimes = 1;
    private AtomicInteger clickCount = new AtomicInteger(0);
    private volatile long countDownTime = this.postIntervalTimes * 3;
    private double myPriceBalance = 0.0d;
    private double myScoreBalance = 0.0d;
    private AtomicLong onLineCount = new AtomicLong(0);
    private String speakLevel = "1";
    private boolean isLiveEnd = false;
    private volatile boolean isFirstGetMyBalanceGift = true;
    private volatile boolean isFirstGetMyBalanceLottery = true;
    private boolean isEnablePK = false;
    private boolean liveStatus = false;
    private long nobilityPlayPeriod = 6000;
    private long trumpetPlayPeriod = 5000;
    private int nobilityTypeThresholdForHasPreventBanned = 7;
    private volatile boolean asleep = true;
    private volatile boolean isLotteryBoomStatus = false;
    private boolean isLotteryDialogFlag = false;
    private boolean isAutoGiftDialogFromWeekStar = true;
    private Map<String, GiftIndexEntity> myGiftIndexMap = new HashMap(8);
    private Map<String, GiftIndexEntity> myPropIndexMap = new HashMap(8);
    private Map<String, Map<String, GiftIndexEntity>> receiveGiftMap = new HashMap(128);
    private Map<String, Map<String, GiftIndexEntity>> receivePropMap = new HashMap(128);
    private ConcurrentLinkedQueue<ChatEntity> receiveMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> enterMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> guardEnterMsgQueue = new ConcurrentLinkedQueue<>();
    private List<String> pullStreamUrlList = new ArrayList(3);
    private volatile List<String> shieldedList = new ArrayList();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> giftNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> anchorInfoNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> sysNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> luckNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> gameNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> privateMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> intimateTaskQueue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean canShowGiftNotice = new AtomicBoolean(true);
    private AtomicBoolean canShowEnterMsg = new AtomicBoolean(true);
    private AtomicBoolean canShowSysNotice = new AtomicBoolean(true);
    private AtomicBoolean canShowGuardEnterMsg = new AtomicBoolean(true);
    private AtomicBoolean carFullAnimFinish = new AtomicBoolean(true);
    private AtomicBoolean canShowPrivateMsg = new AtomicBoolean(true);
    private AtomicBoolean canShowLuckNotice = new AtomicBoolean(true);
    private AtomicBoolean canShowAnchorInfoNotice = new AtomicBoolean(true);
    private AtomicBoolean canShowGameNotice = new AtomicBoolean(true);
    private AtomicBoolean canShowIntimateNotice = new AtomicBoolean(true);
    private AtomicInteger curTrumpetCount = new AtomicInteger(0);
    private long livingStartTime = 0;
    private long livingEndTime = 0;
    private AtomicLong speakTotalCount = new AtomicLong(0);
    private String chargeType = "0";
    private String ticketPrice = "0";
    private String liveEnterWay = "2";
    private volatile boolean isPayLive = false;
    private volatile boolean isBuyTicket = false;
    private volatile boolean isPayLiveTipsDialog = false;
    private volatile boolean showGuideRating = true;
    private volatile boolean isLoginRequest = false;
    private String giftNoticeLiveId = "";
    private AtomicInteger linkMicPKType = new AtomicInteger(288);
    private boolean isFirstInitPlayManager = false;
    private Handler.Callback workCallBack = new Handler.Callback() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$-EZOLmh3b-v6IukC5MLY4gptqR0
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return TomatoLiveFragment.this.lambda$new$64$TomatoLiveFragment(message);
        }
    };
    Object synchronizedObj = new Object();

    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$OnFragmentInteractionListener */
    /* loaded from: classes3.dex */
    public interface OnFragmentInteractionListener {
        void setViewPagerScroll(boolean z);

        void updateLiveRoomInfo();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.GuardOpenContentDialog.OnOpenGuardCallbackListener
    public void OnOpenGuardFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveAdListFail(String str) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onPKLiveRoomFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onPersonalGuardInfoFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onQMInteractShowTaskFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    @Override // com.tomatolive.library.p136ui.view.gift.GiftFrameLayout.BarrageEndAnimationListener
    public void onStartAnimation(GiftAnimModel giftAnimModel) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskChangeFail(TaskBoxEntity taskBoxEntity) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskTakeFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTrumpetStatusFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserCardInfoFail(int i, String str) {
    }

    public static TomatoLiveFragment newInstance(LiveEntity liveEntity, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(LIVE_ITEM, liveEntity);
        bundle.putString(ConstantUtils.RESULT_FLAG, str);
        TomatoLiveFragment tomatoLiveFragment = new TomatoLiveFragment();
        tomatoLiveFragment.setArguments(bundle);
        return tomatoLiveFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onAttachToContext(Context context) {
        super.onAttachToContext(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveListItemEntity = (LiveEntity) getArguments().getParcelable(LIVE_ITEM);
        this.liveEnterWay = bundle.getString(ConstantUtils.RESULT_FLAG, "2");
        this.uploadDataEnterSource = bundle.getString(ConstantUtils.RESULT_ENTER_SOURCE, getString(R$string.fq_hot_list));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public TomatoLivePresenter mo6641createPresenter() {
        return new TomatoLivePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_tomato_live;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.workHandler = HandlerUtils.getInstance().startIOThread(TomatoLiveFragment.class.getName(), this.workCallBack);
        this.mainHandler = new MainHandler(Looper.getMainLooper());
        this.playManager = new PlayManager(this.mContext);
        this.shieldedList = DBUtils.getShieldList();
        ((TomatoLivePresenter) this.mPresenter).initLocalComponentsCache();
        CacheUtils.updateCacheVersion();
        initControlView(view);
        initGiftDownloadData();
        if (this.liveListItemEntity != null) {
            this.isFirstInitPlayManager = true;
            initSendLiveInitInfoRequest();
        }
    }

    public void resetLiveRoom(LiveEntity liveEntity, String str) {
        this.uploadDataEnterSource = getString(R$string.fq_hot_list);
        releasePlay();
        onReleaseViewData();
        initGiftDownloadData();
        this.workHandler = HandlerUtils.getInstance().startIOThread(TomatoLiveFragment.class.getName(), this.workCallBack);
        this.mainHandler = new MainHandler(Looper.getMainLooper());
        this.liveEnterWay = str;
        this.liveListItemEntity = liveEntity;
        if (this.liveListItemEntity != null) {
            this.isFirstInitPlayManager = false;
            initSendLiveInitInfoRequest();
        }
        initListener(this.mFragmentRootView);
    }

    private void initSendLiveInitInfoRequest() {
        SPUtils.getInstance().put(ConstantUtils.IS_CLOSE_QM_WINDOW, false);
        this.isStartGetRoomInfo = true;
        LiveEntity liveEntity = this.liveListItemEntity;
        this.liveId = liveEntity.liveId;
        this.liveCount = liveEntity.liveCount;
        this.liveStatus = liveEntity.isOnLiving();
        this.anchorItemEntity = getAnchorEntityInfo(this.liveListItemEntity);
        setAnchorCoverImg();
        if (this.isFirstInitPlayManager) {
            updatePullStreamUrl();
            this.playManager.initRoomPlayManager(this.rootView, this.pullStreamUrl);
        }
        if (TextUtils.equals("2", this.liveEnterWay)) {
            ((TomatoLivePresenter) this.mPresenter).onUserCheckTicket(this.liveId, true);
        } else if (this.liveListItemEntity.isTimePayLive()) {
            showToast(R$string.fq_pay_time_live_toast);
            onFinishActivity();
        } else if (this.liveListItemEntity.isRelationBoolean()) {
            ((TomatoLivePresenter) this.mPresenter).onUserCheckTicket(this.liveId, false);
        } else if (isPayLiveNeedBuyTicket()) {
            if (DBUtils.isPayLiveValidState(this.liveId, this.liveCount)) {
                sendLiveInitInfoRequest(false, true, false);
            } else {
                ((TomatoLivePresenter) this.mPresenter).onUserCheckTicket(this.liveId, false);
            }
        } else {
            if (this.liveListItemEntity.isPayLiveTicket()) {
                this.isPayLive = isPayLiveTicket();
                this.isBuyTicket = false;
                this.chargeType = this.liveListItemEntity.chargeType;
            }
            sendLiveInitInfoRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLiveInitInfoRequest() {
        sendLiveInitInfoRequest(false, this.isPayLive, this.isBuyTicket);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLiveInitInfoRequest(boolean z, boolean z2, boolean z3) {
        this.isPayLive = z2;
        this.isBuyTicket = z3;
        ((TomatoLivePresenter) this.mPresenter).getLiveInitInfo(this.liveId, this.liveEnterWay, z, z2, z3, this.isLoginRequest);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveInitInfoSuccess(String str, String str2, LiveInitInfoEntity liveInitInfoEntity, boolean z, boolean z2) {
        String str3;
        if (liveInitInfoEntity == null || !TextUtils.equals(this.liveId, str)) {
            return;
        }
        this.isStartGetRoomInfo = false;
        this.socketEncryptionKey = liveInitInfoEntity.f5842k;
        this.liveItemEntity = liveInitInfoEntity.liveDto;
        this.myUserInfoEntity = liveInitInfoEntity.myUserDto;
        this.myUserInfoEntity.setUserId(UserInfoManager.getInstance().getUserId());
        if (TextUtils.equals(str2, "2")) {
            AnchorEntity anchorEntity = liveInitInfoEntity.anchorDto;
            AnchorEntity anchorEntity2 = this.anchorItemEntity;
            anchorEntity2.userId = anchorEntity.userId;
            anchorEntity2.appId = anchorEntity.appId;
            anchorEntity2.openId = anchorEntity.openId;
            anchorEntity2.tag = anchorEntity.tag;
            anchorEntity2.avatar = anchorEntity.avatar;
            anchorEntity2.sex = anchorEntity.sex;
            anchorEntity2.nickname = anchorEntity.nickname;
            anchorEntity2.expGrade = anchorEntity.expGrade;
            this.anchorId = anchorEntity2.userId;
            this.anchorAppId = anchorEntity2.appId;
        }
        this.liveStatus = liveInitInfoEntity.isLiving();
        LiveItemEntity liveItemEntity = this.liveItemEntity;
        this.liveCount = liveItemEntity.liveCount;
        AnchorEntity anchorEntity3 = this.anchorItemEntity;
        anchorEntity3.liveCount = this.liveCount;
        anchorEntity3.topic = liveItemEntity.topic;
        this.ticketPrice = liveItemEntity.ticketPrice;
        this.speakLevel = liveItemEntity.speakLevel;
        this.isBanGroup = UserInfoManager.getInstance().isInBanGroup();
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), this.anchorItemEntity.userId)) {
            this.myUserInfoEntity.setRole("1");
        }
        this.isAllBan = this.liveItemEntity.isBanAll();
        this.isSuperBan = this.myUserInfoEntity.isSuperBanPost();
        lambda$onBackThreadReceiveMessage$26$TomatoLiveFragment();
        this.nobilityTypeThresholdForHasPreventBanned = AppUtils.getNobilityTypeThresholdForHasPreventBanned();
        this.guardItemEntity = formatAnchorGuardInfo(liveInitInfoEntity.guardDto);
        this.myselfEnterMessageEvent = liveInitInfoEntity.formatMyselfEnterMessageEvent();
        int string2int = NumberUtils.string2int(this.liveItemEntity.postIntervalTimes);
        if (string2int >= 0) {
            this.postIntervalTimes = string2int;
        }
        startHideTitleTimer(this.liveItemEntity.topic);
        this.onLineCount.set(NumberUtils.string2long(this.liveItemEntity.onlineUserCount));
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog != null) {
            inputTextMsgForAudienceDialog.setMyGuardType(NumberUtils.string2int(this.guardItemEntity.userGuardType));
            this.mInputTextMsgDialog.setSpeakWordLimit(AppUtils.getGradeSet10CharacterLimit());
            this.mInputTextMsgDialog.setMyRole(this.myUserInfoEntity.getRole());
            this.mInputTextMsgDialog.setMyUserGrade(this.myUserInfoEntity.getExpGrade());
        }
        if (AppUtils.isNobilityUser()) {
            ((TomatoLivePresenter) this.mPresenter).getTrumpetStatus();
        }
        if (this.liveItemEntity.isEnableHdLottery()) {
            LiveItemEntity liveItemEntity2 = this.liveItemEntity;
            str3 = "2";
            loadHdLotteryDrawInfo(liveItemEntity2.liveDrawRecordId, liveItemEntity2.prizeName, liveItemEntity2.prizeNum, liveItemEntity2.liveDrawScope, liveItemEntity2.giftMarkId, liveItemEntity2.joinNum, liveItemEntity2.giftName, liveItemEntity2.giftPrice, liveItemEntity2.giftImg, NumberUtils.string2long(liveItemEntity2.liveDrawTimeRemain), this.liveItemEntity.drawStatus);
        } else {
            str3 = "2";
        }
        if (!this.isFirstInitPlayManager || TextUtils.equals(str3, this.liveEnterWay) || z2) {
            this.pullStreamUrl = this.liveItemEntity.getDefPullStreamUrlStr();
            switchStream();
        }
        this.liveListItemEntity.pullStreamUrl = this.liveItemEntity.getDefPullStreamUrlStr();
        updatePullStreamUrl();
        showContentView(256, true);
        if (isPayLiveNeedBuyTicket() && !DBUtils.isPayLiveValidState(str, this.liveCount)) {
            long longValue = new Double(AppUtils.getFormatVirtualGold(this.ticketPrice)).longValue();
            this.liveItemEntity.anchorContribution = String.valueOf(NumberUtils.string2long(this.liveItemEntity.anchorContribution) + longValue);
        }
        this.mLivePusherInfoView.initData(this.liveItemEntity, this.anchorItemEntity, this.guardItemEntity);
        this.mLivePusherInfoView.setFollowed(AppUtils.isAttentionAnchor(this.anchorId));
        showPayLiveTips();
        if (z && isLiving()) {
            payLiveTipsDialogOnRelease();
            this.livePayEnterView.setEnterEnable(true);
            this.livePayEnterView.onRelease();
            if (this.isBuyTicket) {
                DBUtils.savePayLiveInfo(str, this.liveCount, String.valueOf(System.currentTimeMillis()));
            }
            showToast(R$string.fq_pay_live_ticket_verification_toast);
        }
        if (z2) {
            stopSocket();
            this.socketUrl = AppUtils.formatLiveSocketUrl(this.liveItemEntity.wsAddress, str, this.myUserInfoEntity.getUserId(), str3, this.socketEncryptionKey);
            initSocket();
        } else if (this.isSocketReConn) {
        } else {
            initRoomInfo(liveInitInfoEntity);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveInitInfoFail(int i, String str) {
        if (this.livePayEnterView.getVisibility() == 0) {
            this.livePayEnterView.setEnterEnable(true);
        }
        if (AppUtils.isTokenInvalidErrorCode(i)) {
            return;
        }
        if (AppUtils.isNoEnterLivePermissionErrorCode(i)) {
            onNoEnterLivePermission(str);
        } else if (i == 200164 || i == 300004 || i == 200157 || i == 200171 || i == 200169 || i == 200165) {
            onFinishActivity();
        } else if (AppUtils.isKickOutErrorCode(i)) {
            startKickDialogService();
            onFinishActivity();
        } else {
            this.isStartGetRoomInfo = false;
            if (i == 300006) {
                if (isPayLiveNeedBuyTicket()) {
                    this.livePayEnterView.onReset();
                }
                AppUtils.onRechargeListener(this.mContext);
            } else if (i == 200166) {
                this.liveCount = "";
                ((TomatoLivePresenter) this.mPresenter).onUserCheckTicket(this.liveId, true);
            } else {
                this.isLiveEnd = true;
                showContentView(258, true);
                this.playManager.stopLastPlay();
                showToast(R$string.fq_live_room_loading_fail_tips);
                showRoomInfoReload();
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserCheckTicketSuccess(CheckTicketEntity checkTicketEntity) {
        if (checkTicketEntity == null) {
            showToast(R$string.fq_room_info_fail);
            onFinishActivity();
            return;
        }
        this.chargeType = checkTicketEntity.chargeType;
        this.ticketPrice = checkTicketEntity.getPayLivePrice();
        LiveEntity liveEntity = this.liveListItemEntity;
        liveEntity.chargeType = this.chargeType;
        liveEntity.ticketPrice = this.ticketPrice;
        liveEntity.isPrivateAnchor = checkTicketEntity.isPrivateAnchor;
        liveEntity.privateAnchorPrice = checkTicketEntity.privateAnchorPrice;
        liveEntity.appId = checkTicketEntity.anchorAppId;
        liveEntity.pullStreamUrl = checkTicketEntity.pullStreamUrl;
        liveEntity.liveStatus = checkTicketEntity.liveStatus;
        liveEntity.avatar = checkTicketEntity.avatar;
        liveEntity.liveCoverUrl = checkTicketEntity.getLiveCoverUrl();
        if (TextUtils.equals("2", this.liveEnterWay)) {
            setAnchorCoverImg();
            updatePullStreamUrl();
        }
        if (checkTicketEntity.isNeedBuyTicket()) {
            if (this.liveListItemEntity.isTimePayLive()) {
                showToast(R$string.fq_pay_time_live_toast);
                onFinishActivity();
                return;
            } else if (DBUtils.isPayLiveValidState(this.liveId, this.liveCount)) {
                sendLiveInitInfoRequest(false, true, false);
                return;
            } else {
                showContentView(259, false);
                this.livePayEnterView.initData(this.chargeType, this.ticketPrice, checkTicketEntity.startTotalTime, checkTicketEntity.historyLiveEvaluation, checkTicketEntity.payLiveCount);
                return;
            }
        }
        if (this.liveListItemEntity.isPayLiveTicket()) {
            this.isPayLive = isPayLiveTicket();
            this.isBuyTicket = false;
            this.chargeType = this.liveListItemEntity.chargeType;
        }
        sendLiveInitInfoRequest();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserCheckTicketFail(int i, String str) {
        if (AppUtils.isTokenInvalidErrorCode(i)) {
            return;
        }
        if (AppUtils.isNoEnterLivePermissionErrorCode(i)) {
            onNoEnterLivePermission(str);
        } else if (AppUtils.isKickOutErrorCode(i)) {
            startKickDialogService();
            onFinishActivity();
        } else {
            onFinishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: initBan */
    public void lambda$onBackThreadReceiveMessage$26$TomatoLiveFragment() {
        UserEntity userEntity;
        if (this.isAllBan && (userEntity = this.myUserInfoEntity) != null && AppUtils.isAudience(userEntity.getRole())) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog == null) {
                return;
            }
            inputTextMsgForAudienceDialog.setBanedAllPost();
            showReceiveMsgOnChatList(new SocketMessageEvent.ResultData(), getString(R$string.fq_anchor_start_banned), 5);
        } else if (this.isSuperBan) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog2 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog2 == null) {
                return;
            }
            inputTextMsgForAudienceDialog2.setBandPostBySuperManager();
        } else if (this.isNormalBan) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog3 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog3 == null) {
                return;
            }
            inputTextMsgForAudienceDialog3.setBandOnePost(DateUtils.getClearTime(this.banPostTimeLeft));
        } else {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog4 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog4 == null) {
                return;
            }
            inputTextMsgForAudienceDialog4.cancelBandPost();
        }
    }

    private void initRoomInfo(LiveInitInfoEntity liveInitInfoEntity) {
        if (!isLiving() && !this.isSocketReConn) {
            LiveEndEntity liveEndEntity = liveInitInfoEntity.lastLiveData;
            if (liveEndEntity == null) {
                liveEndEntity = new LiveEndEntity();
            }
            this.lastLiveEndEntity = liveEndEntity;
            LiveEndEntity liveEndEntity2 = this.lastLiveEndEntity;
            AnchorEntity anchorEntity = this.anchorItemEntity;
            liveEndEntity2.liveId = anchorEntity.liveId;
            liveEndEntity2.userId = anchorEntity.userId;
            liveEndEntity2.avatar = anchorEntity.avatar;
            liveEndEntity2.sex = anchorEntity.sex;
            liveEndEntity2.nickname = anchorEntity.nickname;
            liveEndEntity2.expGrade = anchorEntity.expGrade;
            goToEnd();
        } else {
            this.livingStartTime = System.currentTimeMillis();
            LogEventUtils.startLiveDataTimerTask(LogConstants.LEAVE_ROOM_EVENT_NAME, new Runnable() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    TomatoLiveFragment.this.saveLiveUploadData();
                }
            }, DateUtils.ONE_MINUTE_MILLIONS, 10000L);
            if (isLiving() && !TextUtils.equals(this.myUserInfoEntity.getUserId(), this.anchorItemEntity.userId) && this.isFirstLoadTask) {
                this.isFirstLoadTask = false;
                initTaskDialog();
            }
            goToLive();
        }
        LogEventUtils.uploadInRoom(this.anchorItemEntity, this.liveId, this.myUserInfoEntity.expGrade, this.uploadDataEnterSource);
        WatchRecordEntity watchRecordEntity = new WatchRecordEntity();
        watchRecordEntity.userId = this.myUserInfoEntity.getUserId();
        AnchorEntity anchorEntity2 = this.anchorItemEntity;
        watchRecordEntity.liveId = anchorEntity2.liveId;
        watchRecordEntity.coverUrl = anchorEntity2.liveCoverUrl;
        watchRecordEntity.label = anchorEntity2.tag;
        watchRecordEntity.title = this.liveItemEntity.topic;
        watchRecordEntity.anchorNickname = anchorEntity2.nickname;
        watchRecordEntity.liveTime = System.currentTimeMillis();
        DBUtils.saveOrUpdateWatchRecord(watchRecordEntity);
    }

    private void initControlView(View view) {
        int i = 0;
        try {
            this.mImmersionBar = ImmersionBar.with(this.mActivity);
            ImmersionBar immersionBar = this.mImmersionBar;
            immersionBar.transparentStatusBar();
            immersionBar.statusBarView(view.findViewById(R$id.title_top_view));
            immersionBar.statusBarDarkFont(false);
            immersionBar.init();
        } catch (Exception unused) {
        }
        this.rootView = (FrameLayout) view.findViewById(R$id.rl_play_root);
        this.mLiveBgView = (RelativeLayout) view.findViewById(R$id.rl_live_bg);
        this.ivMore = (ImageView) view.findViewById(R$id.iv_private_message);
        this.tvInput = (TextView) view.findViewById(R$id.iv_input);
        this.mLivePusherInfoView = (LivePusherInfoView) view.findViewById(R$id.ll_pusher_info);
        this.mRlControllerView = (RelativeLayout) view.findViewById(R$id.rl_control_layout);
        this.mAnchorCoverImg = (ImageView) view.findViewById(R$id.iv_anchor_cover);
        this.mLiveLoadingView = (LiveLoadingView) view.findViewById(R$id.live_loading_view);
        this.mLiveEndInfoView = (LiveEndInfoView) view.findViewById(R$id.live_end_view);
        this.liveAnimationView = (LiveAnimationView) view.findViewById(R$id.live_anim_view);
        this.mLiveChatMsgView = (LiveChatMsgView) view.findViewById(R$id.live_chat_msg_view);
        this.giftButton = view.findViewById(R$id.iv_gift);
        this.ivClosed = (ImageView) view.findViewById(R$id.iv_closed);
        this.mLiveGuideViewVs = (ViewStub) view.findViewById(R$id.live_guide_view);
        this.ivComponentsView = (ComponentsView) view.findViewById(R$id.iv_components);
        this.mLiveAdBannerBottomView = (LiveAdBannerBottomView) view.findViewById(R$id.live_bottom_banner_view);
        this.ivRecommendComponents = (ComponentsView) view.findViewById(R$id.iv_recommend_components);
        this.mGiftBoxView = (GiftBoxView) this.mLivePusherInfoView.findViewById(R$id.gift_box_view);
        this.livePayEnterView = (LivePayEnterView) view.findViewById(R$id.pay_enter_view);
        this.mPKInfoView = (PKInfoView) view.findViewById(R$id.fq_pk_info_view);
        this.titleTopView = view.findViewById(R$id.title_top_view);
        this.rlWatermarkShadowBg = (RelativeLayout) view.findViewById(R$id.rl_watermark_shadow_bg);
        this.tvWatermarkTitle = (TextView) view.findViewById(R$id.tv_watermark_title);
        this.tvWatermarkUrl = (TextView) view.findViewById(R$id.tv_watermark_url);
        this.ivWatermarkLogo = (ImageView) view.findViewById(R$id.iv_watermark_logo);
        this.swipeAnimationController = new SwipeAnimationController(this.mContext, this.mRlControllerView);
        this.loadingDialog = new LoadingDialog(this.mContext);
        ComponentsView componentsView = this.ivComponentsView;
        if (!AppUtils.isEnableComponents()) {
            i = 8;
        }
        componentsView.setVisibility(i);
        this.ivComponentsView.initCoverDrawableRes(R$drawable.fq_ic_live_game);
        if (AppUtils.isEnablePrivateMsg() || AppUtils.isEnableQMInteract()) {
            this.qBadgeView = new QBadgeView(this.mContext);
            this.qBadgeView.bindTarget(this.ivMore).setBadgeTextColor(-1).setBadgePadding(1.0f, true).isNoNumber(true).setBadgeGravity(8388661).setBadgeBackgroundColor(ContextCompat.getColor(this.mContext, R$color.fq_colorRed)).stroke(-1, 1.0f, true);
            updateMoreRedDot();
        }
        this.mInputTextMsgDialog = new InputTextMsgForAudienceDialog(this.mActivity, this);
        this.transDialog = TransDialog.newInstance(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$774B5zHs2rEfrI9koc022hfXoGM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TomatoLiveFragment.this.lambda$initControlView$0$TomatoLiveFragment(view2);
            }
        });
        this.componentsWebViewDialog = new ComponentsWebViewDialog(this.mContext, new WebViewJSCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.2
            @Override // com.tomatolive.library.p136ui.interfaces.WebViewJSCallback
            public void onLiveBalanceUpdate() {
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getUserOver();
            }
        });
        this.hdLotteryDrawingDialog = new HdLotteryDrawingDialog(this.mContext, R$style.fq_GeneralDialogStyle, new SimpleHdLotteryCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.3
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleHdLotteryCallback, com.tomatolive.library.p136ui.interfaces.OnHdLotteryCallback
            public void onJoinLotteryListener(final GiftDownloadItemEntity giftDownloadItemEntity, final String str) {
                super.onJoinLotteryListener(giftDownloadItemEntity, str);
                if (TomatoLiveFragment.this.isConsumptionPermissionUser() && TomatoLiveFragment.this.isCanSendGift()) {
                    if (TomatoLiveFragment.this.isFirstGetMyBalanceGift) {
                        TomatoLiveFragment.this.isFirstGetMyBalanceGift = false;
                        ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getUserOver(true, new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.3.1
                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onSuccess(MyAccountEntity myAccountEntity) {
                                TomatoLiveFragment.this.sendHdLotteryGift(giftDownloadItemEntity, str);
                            }

                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onError(int i2, String str2) {
                                TomatoLiveFragment.this.showToast(R$string.fq_userover_loading_fail);
                            }
                        });
                        return;
                    }
                    TomatoLiveFragment.this.sendHdLotteryGift(giftDownloadItemEntity, str);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleHdLotteryCallback, com.tomatolive.library.p136ui.interfaces.OnHdLotteryCallback
            public void onFloatingWindowCloseListener() {
                super.onFloatingWindowCloseListener();
                TomatoLiveFragment.this.mLiveAdBannerBottomView.onReleaseHdLotteryWindowView();
            }
        });
    }

    public /* synthetic */ void lambda$initControlView$0$TomatoLiveFragment(View view) {
        this.isTranOpen = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$4 */
    /* loaded from: classes3.dex */
    public class C40104 implements PlayManager.OnPlayListener {
        C40104() {
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onStartBuffering() {
            TomatoLiveFragment.this.showLoadingAnim();
            TomatoLiveFragment.this.startPullTimeOut();
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onEndBuffering() {
            TomatoLiveFragment.this.hideLoadingAnim();
            TomatoLiveFragment.this.cancelPullTimeOut();
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onPlaySuccess() {
            TomatoLiveFragment.this.showLiveLoadingView(4);
            AnimUtils.playHideAnimation(TomatoLiveFragment.this.mAnchorCoverImg);
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onPlayError() {
            TomatoLiveFragment.this.dealPlayError();
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onNetError() {
            TomatoLiveFragment.this.dealPlayError();
        }

        @Override // com.tomatolive.library.utils.live.PlayManager.OnPlayListener
        public void onScreenshot(final Bitmap bitmap) {
            Observable.just(AppUtils.getViewBitmap(((BaseFragment) TomatoLiveFragment.this).mActivity, TomatoLiveFragment.this.mLiveBgView)).flatMap(new Function() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$4$oOilKAmBlxC8PCQJvJWL315JH7k
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    return TomatoLiveFragment.C40104.lambda$onScreenshot$0(bitmap, (Bitmap) obj);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(TomatoLiveFragment.this.bindUntilEvent(FragmentEvent.DESTROY)).subscribe(new SimpleRxObserver<Bitmap>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.4.1
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).compositeDisposableAdd(disposable);
                    if (TomatoLiveFragment.this.loadingDialog != null) {
                        TomatoLiveFragment.this.loadingDialog.show();
                    }
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Bitmap bitmap2) {
                    ImageUtils.save(bitmap2, AppUtils.getScreenshotPath(), Bitmap.CompressFormat.PNG, true);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onComplete() {
                    super.onComplete();
                    if (TomatoLiveFragment.this.loadingDialog != null && TomatoLiveFragment.this.loadingDialog.isShowing()) {
                        TomatoLiveFragment.this.loadingDialog.dismiss();
                    }
                    Intent intent = new Intent(((BaseFragment) TomatoLiveFragment.this).mContext, ReportLiveActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_ITEM, TomatoLiveFragment.this.anchorItemEntity);
                    TomatoLiveFragment.this.startActivity(intent);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    super.onError(th);
                    if (TomatoLiveFragment.this.loadingDialog == null || !TomatoLiveFragment.this.loadingDialog.isShowing()) {
                        return;
                    }
                    TomatoLiveFragment.this.loadingDialog.dismiss();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ ObservableSource lambda$onScreenshot$0(Bitmap bitmap, Bitmap bitmap2) throws Exception {
            if (bitmap == null) {
                return Observable.just(bitmap2);
            }
            ImageUtils.save(bitmap2, AppUtils.getScreenshotPath(), Bitmap.CompressFormat.PNG, true);
            return Observable.just(AppUtils.toConformBitmap(bitmap, ImageUtils.getBitmap(AppUtils.getScreenshotPath())));
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        this.playManager.setOnPlayListener(new C40104());
        this.mLivePusherInfoView.setRootView(this.rootView, this.swipeAnimationController);
        this.mLivePusherInfoView.setGiftAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.5
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                TomatoLiveFragment.this.canShowGiftNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TomatoLiveFragment.this.canShowGiftNotice.set(true);
                if (TomatoLiveFragment.this.giftNoticeQueue != null) {
                    if (TomatoLiveFragment.this.giftNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideGiftNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10004);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TomatoLiveFragment.this.canShowGiftNotice.set(true);
                if (TomatoLiveFragment.this.giftNoticeQueue != null) {
                    if (TomatoLiveFragment.this.giftNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideGiftNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10004);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setCharmAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.6
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                TomatoLiveFragment.this.canShowAnchorInfoNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TomatoLiveFragment.this.canShowAnchorInfoNotice.set(true);
                if (TomatoLiveFragment.this.anchorInfoNoticeQueue != null) {
                    if (TomatoLiveFragment.this.anchorInfoNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideCharmNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10011);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TomatoLiveFragment.this.canShowAnchorInfoNotice.set(true);
                if (TomatoLiveFragment.this.anchorInfoNoticeQueue != null) {
                    if (TomatoLiveFragment.this.anchorInfoNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideCharmNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10011);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setSysNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.7
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                TomatoLiveFragment.this.canShowSysNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TomatoLiveFragment.this.canShowSysNotice.set(true);
                if (TomatoLiveFragment.this.sysNoticeQueue != null) {
                    if (TomatoLiveFragment.this.sysNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideSysNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10005);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TomatoLiveFragment.this.canShowSysNotice.set(true);
                if (TomatoLiveFragment.this.sysNoticeQueue != null) {
                    if (TomatoLiveFragment.this.sysNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideSysNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(10005);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setLuckNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.8
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                TomatoLiveFragment.this.canShowLuckNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TomatoLiveFragment.this.canShowLuckNotice.set(true);
                if (TomatoLiveFragment.this.luckNoticeQueue != null) {
                    if (TomatoLiveFragment.this.luckNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideLuckNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(ConstantUtils.SYS_LUCK_HIT);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TomatoLiveFragment.this.canShowLuckNotice.set(true);
                if (TomatoLiveFragment.this.luckNoticeQueue != null) {
                    if (TomatoLiveFragment.this.luckNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideLuckNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(ConstantUtils.SYS_LUCK_HIT);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setGameNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.9
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                TomatoLiveFragment.this.canShowGameNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TomatoLiveFragment.this.canShowGameNotice.set(true);
                if (TomatoLiveFragment.this.gameNoticeQueue != null) {
                    if (TomatoLiveFragment.this.gameNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideGameNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TomatoLiveFragment.this.canShowGameNotice.set(true);
                if (TomatoLiveFragment.this.gameNoticeQueue != null) {
                    if (TomatoLiveFragment.this.gameNoticeQueue.isEmpty()) {
                        TomatoLiveFragment.this.mLivePusherInfoView.hideGameNoticeView();
                    } else {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
                    }
                }
            }
        });
        this.mLivePusherInfoView.initLivePusherInfoCallback(2, getChildFragmentManager(), new SimpleLivePusherInfoCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.10
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAnchorAvatarListener(View view2) {
                if (!TomatoLiveFragment.this.isStartGetAnchorInfo) {
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getAnchorInfo(TomatoLiveFragment.this.anchorId);
                    TomatoLiveFragment.this.isStartGetAnchorInfo = true;
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickGiftNoticeListener(View view2) {
                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                tomatoLiveFragment.startActivityById(tomatoLiveFragment.giftNoticeLiveId);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAnchorInfoNoticeListener(View view2) {
                if (TomatoLiveFragment.this.curAnchorInfoNoticeEntity != null) {
                    if (TextUtils.equals(TomatoLiveFragment.this.curAnchorInfoNoticeEntity.type, ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN)) {
                        ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).updateStartLiveNoticeCount(TomatoLiveFragment.this.curAnchorInfoNoticeEntity.forwardLiveId);
                    }
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.startActivityById(tomatoLiveFragment.curAnchorInfoNoticeEntity.forwardLiveId);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickSysNoticeListener(View view2) {
                if (TomatoLiveFragment.this.tempSysNoticeResultData == null) {
                    return;
                }
                String str = TomatoLiveFragment.this.tempSysNoticeResultData.sysNoticeType;
                char c = 65535;
                switch (str.hashCode()) {
                    case -1603387347:
                        if (str.equals(ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE)) {
                            c = 2;
                            break;
                        }
                        break;
                    case -941691210:
                        if (str.equals(ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE)) {
                            c = 1;
                            break;
                        }
                        break;
                    case -370196576:
                        if (str.equals(ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 395254178:
                        if (str.equals(ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                if (c == 0) {
                    TomatoLiveFragment.this.trumpetNoticeClick();
                } else if (c == 1) {
                    TomatoLiveFragment.this.sysNoticeClick();
                } else if (c == 2) {
                    TomatoLiveFragment.this.nobilityNoticeClick();
                } else if (c != 3) {
                } else {
                    TomatoLiveFragment.this.generalNoticeClick();
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickLuckNoticeListener(View view2) {
                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                tomatoLiveFragment.startActivityById(tomatoLiveFragment.luckNoticeLiveId);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickGameNoticeListener(View view2) {
                ComponentsEntity localCacheComponentsByGameId;
                if (TomatoLiveFragment.this.curGameNoticeEntity == null || (localCacheComponentsByGameId = CacheUtils.getLocalCacheComponentsByGameId(TomatoLiveFragment.this.curGameNoticeEntity.gameId)) == null) {
                    return;
                }
                TomatoLiveFragment.this.showComponentsWebViewDialog(true, localCacheComponentsByGameId);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onFollowAnchorListener(View view2) {
                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                tomatoLiveFragment.attentionAnchorAction(view2, tomatoLiveFragment.anchorId);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickUserAvatarListener(UserEntity userEntity) {
                TomatoLiveFragment.this.showUserCard(userEntity);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAdBannerListener(BannerEntity bannerEntity) {
                TomatoLiveFragment.this.lambda$initListener$11$TomatoLiveFragment(bannerEntity);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onNobilityOpenListener() {
                TomatoLiveFragment.this.toNobilityOpenActivity();
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickGuardListener(GuardItemEntity guardItemEntity) {
                super.onClickGuardListener(guardItemEntity);
                TomatoLiveFragment.this.showGuardListDialog(guardItemEntity);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAudienceListener(View view2) {
                super.onClickAudienceListener(view2);
                TomatoLiveFragment.this.showPayLiveEvaluationDialog();
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivMore, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$bfVqRPtF-fv6_8fBtIyA8KMCpYE
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$1$TomatoLiveFragment(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.giftButton, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$hCEW14henNP7gGJw28rO3QhboAE
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$2$TomatoLiveFragment(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivClosed, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$5MaLqCGJZmamXWp004hacnE8PPQ
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$3$TomatoLiveFragment(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivRecommendComponents, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$93c9oSRzIx6IVSC-zFl0jxQpEck
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$4$TomatoLiveFragment(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivComponentsView, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ZnXUyTmxO2qhXotB8j37IOH3nFs
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$5$TomatoLiveFragment(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvInput, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$kL-YvYsfsODIKMOkPpiCWrfS69s
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TomatoLiveFragment.this.lambda$initListener$6$TomatoLiveFragment(obj);
            }
        });
        this.mInputTextMsgDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Rmys28pvkmpzh9tSCWrKjgCxeEI
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                TomatoLiveFragment.this.lambda$initListener$7$TomatoLiveFragment(dialogInterface);
            }
        });
        this.mLiveLoadingView.setOnLiveLoadingListener(new LiveLoadingView.OnLiveLoadingListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.11
            @Override // com.tomatolive.library.p136ui.view.custom.LiveLoadingView.OnLiveLoadingListener
            public void onReloadClickListener(int i) {
                if (i != 1) {
                    if (i != 2) {
                        return;
                    }
                    TomatoLiveFragment.this.changeLineReloadLoading(0);
                } else if (NetUtils.getNetWorkState() != -1) {
                    TomatoLiveFragment.this.showLoadingAnim();
                    TomatoLiveFragment.this.isStartGetRoomInfo = true;
                    TomatoLiveFragment.this.pullStreamUrl = "";
                    TomatoLiveFragment.this.sendLiveInitInfoRequest();
                } else {
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.showToast(tomatoLiveFragment.getResources().getString(R$string.fq_text_no_network));
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.LiveLoadingView.OnLiveLoadingListener
            public void onChangeLineClickListener(int i) {
                if (i == 1) {
                    TomatoLiveFragment.this.changeLineReloadLoading(0);
                } else if (i == 2) {
                    TomatoLiveFragment.this.changeLineReloadLoading(1);
                } else if (i != 3) {
                } else {
                    TomatoLiveFragment.this.changeLineReloadLoading(2);
                }
            }
        });
        this.liveAnimationView.setAnimationCallback(new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.12
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                TomatoLiveFragment.this.liveAnimationView.setGiftAnimViewVisibility(4);
                TomatoLiveFragment.this.wsManagerNotifyBigAnim();
            }
        }, new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.13
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                TomatoLiveFragment.this.liveAnimationView.setGuardEnterAnimViewVisibility(4);
                TomatoLiveFragment.this.canShowGuardEnterMsg.set(true);
            }
        }, new LiveAnimationView.OnLeftGiftCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.14
            @Override // com.tomatolive.library.p136ui.view.custom.LiveAnimationView.OnLeftGiftCallback
            public void onLeftGiftDeleteListener(GiftAnimModel giftAnimModel) {
                if (TextUtils.equals(giftAnimModel.getSendUserId(), TomatoLiveFragment.this.myUserInfoEntity.getUserId())) {
                    if (giftAnimModel.isProp) {
                        ((GiftIndexEntity) TomatoLiveFragment.this.myPropIndexMap.get(giftAnimModel.getGiftId())).countDownStartTime = System.currentTimeMillis();
                        return;
                    }
                    ((GiftIndexEntity) TomatoLiveFragment.this.myGiftIndexMap.get(giftAnimModel.getGiftId())).countDownStartTime = System.currentTimeMillis();
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.LiveAnimationView.OnLeftGiftCallback
            public void onLeftGiftClickListener(GiftAnimModel giftAnimModel) {
                TomatoLiveFragment.this.showUserCard(AppUtils.formatUserEntity(giftAnimModel));
            }
        }, new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.15
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                TomatoLiveFragment.this.carFullAnimFinish.set(true);
            }
        });
        this.mLiveChatMsgView.setOnChatMsgItemClickListener(new ChatMsgListAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.16
            @Override // com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter.OnItemClickListener
            public void onItemTextClick(Object obj) {
            }

            @Override // com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter.OnItemClickListener
            public void onItemClick(ChatEntity chatEntity) {
                if (AppUtils.isShowUserAvatarDialog(chatEntity)) {
                    TomatoLiveFragment.this.showUserCard(AppUtils.formatUserEntity(chatEntity));
                    return;
                }
                switch (chatEntity.getMsgType()) {
                    case 16:
                        TomatoLiveFragment.this.showComponentsWebViewDialog(true, CacheUtils.getLocalCacheComponentsByGameId(chatEntity.getPropId()));
                        return;
                    case 17:
                        TomatoLiveFragment.this.toNobilityOpenActivity();
                        return;
                    case 18:
                        if (AppUtils.isAttentionAnchor(TomatoLiveFragment.this.anchorId)) {
                            return;
                        }
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        tomatoLiveFragment.attentionAnchorAction(true, tomatoLiveFragment.anchorId);
                        return;
                    case 19:
                        TomatoLiveFragment.this.showLotteryDialog();
                        return;
                    case 20:
                        TomatoLiveFragment.this.trumpetNoticeClick();
                        return;
                    case 21:
                        TomatoLiveFragment.this.sysNoticeClick();
                        return;
                    default:
                        return;
                }
            }
        });
        this.mLiveEndInfoView.setLiveEndClickListener(new LiveEndInfoView.LiveEndClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.17
            @Override // com.tomatolive.library.p136ui.view.custom.LiveEndInfoView.LiveEndClickListener
            public void onAttentionClick(View view2) {
                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                tomatoLiveFragment.attentionAnchorAction(view2, tomatoLiveFragment.anchorId);
            }

            @Override // com.tomatolive.library.p136ui.view.custom.LiveEndInfoView.LiveEndClickListener
            public void onGoHomeClick() {
                TomatoLiveFragment.this.onFinishActivity();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.LiveEndInfoView.LiveEndClickListener
            public void onNavBackClick() {
                TomatoLiveFragment.this.setViewPagerScrollEnable(true);
                TomatoLiveFragment.this.mPKInfoView.onRelease();
                TomatoLiveFragment.this.showContentView(256, true);
                if (TomatoLiveFragment.this.isLiving() || TomatoLiveFragment.this.liveListItemEntity == null) {
                    return;
                }
                TomatoLiveFragment.this.goToLive();
            }
        });
        this.mGiftBoxView.setOnSendGiftBoxMsgListener(new GiftBoxView.OnSendGiftBoxMsgListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.18
            @Override // com.tomatolive.library.p136ui.view.custom.GiftBoxView.OnSendGiftBoxMsgListener
            public void onSendGiftBoxMsg(GiftBoxEntity giftBoxEntity) {
                if (TomatoLiveFragment.this.wsManager == null || TomatoLiveFragment.this.wsManager.getSocketStatus() != WsStatus.CONNECT_SUCCESS) {
                    return;
                }
                TomatoLiveFragment.this.wsManager.sendGrabGiftBoxMessage(MessageHelper.convertToGrabGiftBoxMsg(giftBoxEntity.giftBoxUniqueCode));
            }

            @Override // com.tomatolive.library.p136ui.view.custom.GiftBoxView.OnSendGiftBoxMsgListener
            public void onShowDialog(GiftBoxEntity giftBoxEntity) {
                GiftBoxPresenterDialog.newInstance(giftBoxEntity.presenterAvatar, giftBoxEntity.presenterName).show(TomatoLiveFragment.this.getChildFragmentManager());
            }
        });
        this.mLiveAdBannerBottomView.setOnRefreshTaskListener(new TaskBoxView.OnRefreshTaskListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.19
            @Override // com.tomatolive.library.p136ui.view.custom.TaskBoxView.OnRefreshTaskListener
            public void onRefreshTask(TaskBoxEntity taskBoxEntity) {
                TomatoLiveFragment.this.mTaskBottomDialog.updateSingleData(taskBoxEntity);
            }

            @Override // com.tomatolive.library.p136ui.view.custom.TaskBoxView.OnRefreshTaskListener
            public void onShowDialog() {
                if (TomatoLiveFragment.this.isConsumptionPermissionUser() && TomatoLiveFragment.this.mTaskBottomDialog != null && !TomatoLiveFragment.this.mTaskBottomDialog.isAdded()) {
                    TomatoLiveFragment.this.mTaskBottomDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.TaskBoxView.OnRefreshTaskListener
            public void onTaskComplete(TaskBoxEntity taskBoxEntity) {
                if (TomatoLiveFragment.this.isSocketError) {
                    TomatoLiveFragment.this.mLiveAdBannerBottomView.releaseForTaskBox();
                    TomatoLiveFragment.this.isTaskSocket = true;
                    return;
                }
                TomatoLiveFragment.this.isTaskSocket = false;
                if (!TomatoLiveFragment.this.isLiving()) {
                    return;
                }
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).changeTaskState(taskBoxEntity);
            }
        });
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.setOnDismissListener(new BaseRxDialogFragment.DialogDismissListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$_V5TXXya2RaStIuFKDPSOR87G4o
                @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment.DialogDismissListener
                public final void onDialogDismiss(BaseRxDialogFragment baseRxDialogFragment) {
                    TomatoLiveFragment.this.lambda$initListener$8$TomatoLiveFragment(baseRxDialogFragment);
                }
            });
        }
        this.ivComponentsView.setOnLotteryBoomEndCallback(new OnLotteryBoomCallback() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$9p8zNY6UAgO9EpAkopq6zc8RDp0
            @Override // com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback
            public final void onBoomCountDownEnd() {
                TomatoLiveFragment.this.lambda$initListener$9$TomatoLiveFragment();
            }
        });
        this.ivRecommendComponents.setOnLotteryBoomEndCallback(new OnLotteryBoomCallback() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$vKa5jggIKiLiu-4oejVTQGd7QPo
            @Override // com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback
            public final void onBoomCountDownEnd() {
                TomatoLiveFragment.this.lambda$initListener$10$TomatoLiveFragment();
            }
        });
        this.mLiveAdBannerBottomView.setOnAdBannerClickListener(new OnLiveAdBannerClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$U-qVBpZMU6iaH5yEYniTSv1EUPU
            @Override // com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener
            public final void onAdBannerClick(BannerEntity bannerEntity) {
                TomatoLiveFragment.this.lambda$initListener$11$TomatoLiveFragment(bannerEntity);
            }
        });
        this.livePayEnterView.setOnPayLiveEnterCallback(new SimplePayLiveCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.20
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
            public void onPayExitClickListener() {
                TomatoLiveFragment.this.onFinishActivity();
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
            public void onPayEnterClickListener(View view2) {
                if (TomatoLiveFragment.this.isConsumptionPermissionUser()) {
                    TomatoLiveFragment.this.livePayEnterView.onRelease();
                    TomatoLiveFragment.this.livePayEnterView.setEnterEnable(false);
                    TomatoLiveFragment.this.sendLiveInitInfoRequest(true, true, true);
                    return;
                }
                AppUtils.onLoginListener(((BaseFragment) TomatoLiveFragment.this).mContext);
            }
        });
        this.mPKInfoView.setOnPkViewListener(new OnPkViewListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.21
            @Override // com.tomatolive.library.p136ui.interfaces.OnPkViewListener
            public void onReadyPK(View view2) {
            }

            @Override // com.tomatolive.library.p136ui.interfaces.OnPkViewListener
            public void onAttentionAnchor(String str, View view2) {
                if (!TomatoLiveFragment.this.isConsumptionPermissionUser()) {
                    return;
                }
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).attentionAnchor(str, 1);
                TomatoLiveFragment.this.showToast(R$string.fq_text_attention_success);
                DBUtils.attentionAnchor(str, true);
                view2.setVisibility(4);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.OnPkViewListener
            public void onEnterLiveRoom(String str) {
                TomatoLiveFragment.this.startActivityById(str);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.OnPkViewListener
            public void onShowPKRanking() {
                TomatoLiveFragment.this.showPKRankDialog();
            }

            @Override // com.tomatolive.library.p136ui.interfaces.OnPkViewListener
            public void onPkCountDownComplete() {
                TomatoLiveFragment.this.mPKTimerDisposable = Observable.timer(30L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.21.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Long l) throws Exception {
                        if (TomatoLiveFragment.this.linkMicPKType.get() == 281 && TomatoLiveFragment.this.linkMicPKType.get() == 288) {
                            return;
                        }
                        ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).onGetFP(TomatoLiveFragment.this.liveId, TomatoLiveFragment.this.liveItemEntity.isPKEnd(), true);
                    }
                });
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).compositeDisposableAdd(TomatoLiveFragment.this.mPKTimerDisposable);
            }
        });
        this.mLiveAdBannerBottomView.setOnInteractWindowClickListener(new TopBannerUtils.InteractWindowListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.22
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils.InteractWindowListener
            public void onClick(View view2) {
                if (view2 instanceof HdLotteryWindowView) {
                    TomatoLiveFragment.this.showHdLotteryDrawDialog();
                }
                if (view2 instanceof QMNoticeWindow) {
                    TomatoLiveFragment.this.mLiveAdBannerBottomView.hideRedPoint();
                    TomatoLiveFragment.this.showQMInteractTaskListDialog(null);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils.InteractWindowListener
            public void onDelete(View view2) {
                if (view2 instanceof HdLotteryWindowView) {
                    if (TomatoLiveFragment.this.hdLotteryDrawingDialog != null) {
                        TomatoLiveFragment.this.hdLotteryDrawingDialog.onReleaseData();
                    }
                    TomatoLiveFragment.this.mLiveAdBannerBottomView.onDeleteHdLotteryWindowView();
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$TomatoLiveFragment(Object obj) {
        showLiveMoreDialog();
    }

    public /* synthetic */ void lambda$initListener$2$TomatoLiveFragment(Object obj) {
        showGiftPanel();
    }

    public /* synthetic */ void lambda$initListener$3$TomatoLiveFragment(Object obj) {
        onFinishActivity();
    }

    public /* synthetic */ void lambda$initListener$4$TomatoLiveFragment(Object obj) {
        ComponentsEntity componentsEntity = this.cacheRecommendComponents;
        if (componentsEntity != null) {
            if (componentsEntity.isCacheLotteryComponents()) {
                showLotteryDialog();
            } else {
                showComponentsWebViewDialog(true, this.cacheRecommendComponents);
            }
        }
    }

    public /* synthetic */ void lambda$initListener$5$TomatoLiveFragment(Object obj) {
        showComponentsDialog();
    }

    public /* synthetic */ void lambda$initListener$6$TomatoLiveFragment(Object obj) {
        showChatFrames();
    }

    public /* synthetic */ void lambda$initListener$7$TomatoLiveFragment(DialogInterface dialogInterface) {
        moveUpViews(false);
    }

    public /* synthetic */ void lambda$initListener$8$TomatoLiveFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (this.isLotteryDialogFlag) {
            showDialogFragment(this.mLotteryDialog);
        }
    }

    public /* synthetic */ void lambda$initListener$9$TomatoLiveFragment() {
        if (AppUtils.isEnableTurntable()) {
            ((TomatoLivePresenter) this.mPresenter).getBoomStatus(this.liveId);
        }
    }

    public /* synthetic */ void lambda$initListener$10$TomatoLiveFragment() {
        if (AppUtils.isEnableTurntable()) {
            ((TomatoLivePresenter) this.mPresenter).getBoomStatus(this.liveId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generalNoticeClick() {
        if (!TextUtils.equals("recommend", this.tempSysNoticeResultData.type) || !TextUtils.equals(this.tempSysNoticeResultData.clickEvent, ConstantUtils.SOCKET_MSG_CLICK_EVENT_LIVE)) {
            return;
        }
        noticeClickToActivity(this.tempSysNoticeResultData.forwardLiveId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nobilityNoticeClick() {
        if (AppUtils.highThanBoJue(this.tempSysNoticeResultData.nobilityType) && TextUtils.equals(this.tempSysNoticeResultData.clickEvent, ConstantUtils.SOCKET_MSG_CLICK_EVENT_LIVE)) {
            noticeClickToActivity(this.tempSysNoticeResultData.forwardLiveId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trumpetNoticeClick() {
        SocketMessageEvent.ResultData resultData = this.tempSysNoticeResultData;
        if (resultData == null) {
            return;
        }
        ((TomatoLivePresenter) this.mPresenter).updateTrumpetClickCount(resultData.trumpetId);
        noticeClickToActivity(this.tempSysNoticeResultData.liveId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sysNoticeClick() {
        SocketMessageEvent.ResultData resultData = this.tempSysNoticeResultData;
        if (resultData == null) {
            return;
        }
        String str = resultData.f5847id;
        String str2 = resultData.clickEvent;
        String str3 = resultData.forwardText;
        char c = 65535;
        int hashCode = str2.hashCode();
        if (hashCode != -370076372) {
            if (hashCode == -289024721 && str2.equals(ConstantUtils.SOCKET_MSG_CLICK_EVENT_URL)) {
                c = 1;
            }
        } else if (str2.equals(ConstantUtils.SOCKET_MSG_CLICK_EVENT_LIVE)) {
            c = 0;
        }
        if (c == 0) {
            ((TomatoLivePresenter) this.mPresenter).getBroadcastClick(str);
            noticeClickToActivity(str3);
        }
        if (c == 1) {
            try {
                WebViewDialog.newInstance("    ", str3).show(getChildFragmentManager());
                ((TomatoLivePresenter) this.mPresenter).getBroadcastClick(str);
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToLive() {
        if (SysConfigInfoManager.getInstance().isEnableLiveGuide()) {
            this.showGuideRating = false;
            setViewPagerScrollEnable(false);
            if (this.mLiveGuideView == null) {
                this.mLiveGuideView = this.mLiveGuideViewVs.inflate();
            }
            this.mLiveGuideView.findViewById(R$id.iv_know).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$vGJR1cDoW9mad7Km8vCUFpgiQY0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TomatoLiveFragment.this.lambda$goToLive$12$TomatoLiveFragment(view);
                }
            });
        } else {
            setViewPagerScrollEnable(true);
        }
        initWatermarkConfig();
        if (this.liveItemEntity.isPKLiveRoom()) {
            GlideUtils.loadImage(this.mContext, this.mAnchorCoverImg, R$drawable.fq_ic_pk_main_bottom_bg);
            adjustPkInfoViewLayout();
            PKInfoView pKInfoView = this.mPKInfoView;
            String str = this.liveId;
            AnchorEntity anchorEntity = this.anchorItemEntity;
            String str2 = anchorEntity.nickname;
            String str3 = anchorEntity.avatar;
            LiveItemEntity liveItemEntity = this.liveItemEntity;
            pKInfoView.showLMSuccessView(str, str2, str3, liveItemEntity.lianmaiTargetAnchorId, liveItemEntity.lianmaiTargetLiveId, liveItemEntity.lianmaiTargetAnchorAvatar, liveItemEntity.lianmaiTargetAnchorName, NumberUtils.string2long(liveItemEntity.pkTimeRemain), NumberUtils.string2long(this.liveItemEntity.pkPunishTime), this.liveItemEntity.isPKStart());
            if (this.liveItemEntity.isPKStart()) {
                ((TomatoLivePresenter) this.mPresenter).onGetFP(this.liveId, this.liveItemEntity.isPKEnd(), false);
            }
        }
        this.cacheRecommendComponents = CacheUtils.getLocalCacheRecommendComponents();
        ComponentsEntity componentsEntity = this.cacheRecommendComponents;
        if (componentsEntity != null) {
            if (componentsEntity.isCacheLotteryComponents()) {
                if (AppUtils.isEnableTurntable()) {
                    this.ivRecommendComponents.setVisibility(0);
                    this.ivRecommendComponents.initCoverDrawableRes(R$drawable.fq_ic_lottery);
                } else {
                    this.ivRecommendComponents.setVisibility(8);
                }
            } else if (!TextUtils.isEmpty(this.cacheRecommendComponents.imgUrl)) {
                this.ivRecommendComponents.setVisibility(0);
                this.ivRecommendComponents.initCoverImgUrl(this.cacheRecommendComponents.imgUrl);
            } else {
                this.ivRecommendComponents.setVisibility(8);
            }
        } else {
            this.ivRecommendComponents.setVisibility(8);
        }
        this.socketUrl = AppUtils.formatLiveSocketUrl(this.liveItemEntity.wsAddress, this.liveId, this.myUserInfoEntity.getUserId(), "2", this.socketEncryptionKey);
        initSocket();
        ((TomatoLivePresenter) this.mPresenter).getCurrentOnlineUserList(this.liveId, AppUtils.getOnlineCountSynInterval());
        ((TomatoLivePresenter) this.mPresenter).getLivePopularity(this.liveId, this.anchorAppId);
        ((TomatoLivePresenter) this.mPresenter).getGiftBoxList(5L, this.liveId);
        sendAdImageRequest();
        ((TomatoLivePresenter) this.mPresenter).setEnterOrLeaveLiveRoomMsg(ConstantUtils.ENTER_TYPE);
        ((TomatoLivePresenter) this.mPresenter).sendUserShowTaskListRequest(5L, this.liveId);
        if (AppUtils.isEnableTurntable()) {
            ((TomatoLivePresenter) this.mPresenter).getBoomStatus(this.liveId);
        }
        ((TomatoLivePresenter) this.mPresenter).onAttentionMsgNotice(new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.23
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str4) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (AppUtils.isAttentionAnchor(TomatoLiveFragment.this.anchorId)) {
                    return;
                }
                ChatEntity chatEntity = new ChatEntity();
                chatEntity.setUid(UserInfoManager.getInstance().getUserId());
                chatEntity.setMsgText(TomatoLiveFragment.this.getString(R$string.fq_msg_attention_tips));
                chatEntity.setMsgType(18);
                TomatoLiveFragment.this.mLiveChatMsgView.addChatMsg(chatEntity);
            }
        });
        ((TomatoLivePresenter) this.mPresenter).onOpenNobilityMsgNotice(new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.24
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str4) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ChatEntity chatEntity = new ChatEntity();
                chatEntity.setMsgText(TomatoLiveFragment.this.getString(AppUtils.isNobilityUser() ? R$string.fq_msg_renew_nobility_tips : R$string.fq_msg_open_nobility_tips));
                chatEntity.setMsgType(17);
                TomatoLiveFragment.this.mLiveChatMsgView.addChatMsg(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$goToLive$12$TomatoLiveFragment(View view) {
        this.mLiveGuideView.setVisibility(8);
        SysConfigInfoManager.getInstance().setEnableLiveGuide(false);
        setViewPagerScrollEnable(true);
        this.showGuideRating = true;
        if (!SysConfigInfoManager.getInstance().isEnableRatingGuide() || !this.isPayLive) {
            return;
        }
        this.mLivePusherInfoView.showGuideRating(this.mActivity);
    }

    private void goToEnd() {
        dismissDialogs();
        this.playManager.stopLastPlay();
        this.isLiveEnd = true;
        setAnchorCoverImg();
        showContentView(257, false);
        showLiveLoadingView(4);
        setViewPagerScrollEnable(false);
        this.mLiveEndInfoView.initData(this.lastLiveEndEntity);
    }

    private void startHideTitleTimer(String str) {
        this.mLiveChatMsgView.setLiveTitle(str);
    }

    private void startKickDialogService() {
        AppUtils.startDialogService(this.mContext, KickDialogService.class);
    }

    private void startKickDialogService(String str) {
        AppUtils.startDialogService(this.mContext, KickDialogService.class, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTokenDialogService() {
        AppUtils.startDialogService(this.mContext, TokenDialogService.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startActivityById(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (TextUtils.equals(str, this.liveId)) {
            showToast(R$string.fq_already_in_room);
            return;
        }
        LiveEntity liveEntity = new LiveEntity();
        liveEntity.liveId = str;
        resetLiveRoom(liveEntity, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingAnim() {
        showLiveLoadingView(0);
        this.mLiveLoadingView.showLoadingView();
    }

    private void showReloadButton() {
        showLiveLoadingView(0);
        this.mLiveLoadingView.showReloadView(2);
        dismissDialogs();
    }

    private void showRoomInfoReload() {
        this.isShowRetryRoomInfo = true;
        showLiveLoadingView(0);
        this.mLiveLoadingView.showReloadView(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadingAnim() {
        this.mLiveLoadingView.stopLoadingViewAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLiveLoadingView(int i) {
        this.mLiveLoadingView.setVisibility(i);
        if (i == 4 || i == 8) {
            hideLoadingAnim();
            cancelPullTimeOut();
        }
    }

    private void setAnchorCoverImg() {
        if (this.mAnchorCoverImg.getVisibility() != 0) {
            this.mAnchorCoverImg.setVisibility(0);
        }
        GlideUtils.loadImageBlur(this.mContext, this.mAnchorCoverImg, this.anchorItemEntity.liveCoverUrl, R$drawable.fq_shape_default_cover_bg);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onGiftListSuccess(List<GiftDownloadItemEntity> list) {
        LiveItemEntity liveItemEntity;
        HdLotteryDrawingDialog hdLotteryDrawingDialog;
        if (this.isGiftListUpdating && (liveItemEntity = this.liveItemEntity) != null && liveItemEntity.isEnableHdLottery() && (hdLotteryDrawingDialog = this.hdLotteryDrawingDialog) != null) {
            hdLotteryDrawingDialog.updateGiftInfo();
        }
        this.isGiftListUpdating = false;
        this.isGetGiftListFail = false;
        this.startGetGiftListInfo = false;
        initGiftDialog(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onGiftListFail() {
        showToast(R$string.fq_gift_fail);
        this.isGetGiftListFail = true;
        this.isGiftListUpdating = false;
        this.startGetGiftListInfo = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onAttentionSuccess() {
        EventBus.getDefault().postSticky(new AttentionEvent());
        EventBus.getDefault().postSticky(new LiveTopAttentionEvent());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onAnchorInfoSuccess(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return;
        }
        anchorEntity.userRole = "1";
        anchorEntity.role = "1";
        if (AppUtils.isNobilityUser(anchorEntity.nobilityType)) {
            this.onUserCardCallback = new UserCardCallback(anchorEntity.userId, true);
            if (this.anchorNobilityAvatarDialog == null) {
                this.anchorNobilityAvatarDialog = UserNobilityAvatarDialog.newInstance(anchorEntity, this.onUserCardCallback);
                this.anchorNobilityAvatarDialog.show(getChildFragmentManager());
            } else {
                this.bundleArgs = new Bundle();
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, true);
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, true);
                this.bundleArgs.putInt("liveType", 2);
                this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
                this.anchorNobilityAvatarDialog.setArguments(this.bundleArgs);
                this.anchorNobilityAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
                showDialogFragment(this.anchorNobilityAvatarDialog);
            }
            this.isStartGetAnchorInfo = false;
            return;
        }
        this.onUserCardCallback = new UserCardCallback(anchorEntity.userId, true);
        if (this.anchorAvatarDialog == null) {
            this.anchorAvatarDialog = UserNormalAvatarDialog.newInstance(anchorEntity, this.onUserCardCallback);
            this.anchorAvatarDialog.show(getChildFragmentManager());
        } else {
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, true);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, true);
            this.bundleArgs.putInt("liveType", 2);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
            this.anchorAvatarDialog.setArguments(this.bundleArgs);
            this.anchorAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.anchorAvatarDialog);
        }
        this.isStartGetAnchorInfo = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onAnchorInfoFail() {
        this.isStartGetAnchorInfo = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserCardInfoSuccess(UserEntity userEntity) {
        showUserCard(userEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserBalance(int i) {
        if (!AppUtils.isConsumptionPermissionUser()) {
            this.isFirstGetMyBalanceGift = false;
            this.isFirstGetMyBalanceLottery = false;
            this.myPriceBalance = 0.0d;
            this.myScoreBalance = 0.0d;
            GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
            if (giftBackpackDialog != null) {
                giftBackpackDialog.setUserBalance(this.myPriceBalance);
                this.giftBottomDialog.setUserScore(this.myScoreBalance);
            }
            LotteryDialog lotteryDialog = this.mLotteryDialog;
            if (lotteryDialog == null) {
                return;
            }
            lotteryDialog.setUserBalance(this.myPriceBalance);
        } else if (i == 0) {
            if (this.isFirstGetMyBalanceGift) {
                this.isFirstGetMyBalanceGift = false;
                this.giftBottomDialog.setUserBalanceTip(R$string.fq_userover_loading);
                ((TomatoLivePresenter) this.mPresenter).getUserOver();
            } else if (!this.getUserBalanceFail) {
            } else {
                this.giftBottomDialog.setUserBalanceTip(R$string.fq_userover_loading);
                ((TomatoLivePresenter) this.mPresenter).getUserOver();
            }
        } else if (i != 1) {
        } else {
            if (this.isFirstGetMyBalanceLottery) {
                this.isFirstGetMyBalanceLottery = false;
                this.mLotteryDialog.setUserBalanceTip(R$string.fq_userover_loading);
                ((TomatoLivePresenter) this.mPresenter).getUserOver();
            } else if (!this.getUserBalanceFail) {
            } else {
                this.mLotteryDialog.setUserBalanceTip(R$string.fq_userover_loading);
                ((TomatoLivePresenter) this.mPresenter).getUserOver();
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserOverSuccess(MyAccountEntity myAccountEntity) {
        if (myAccountEntity == null) {
            this.getUserBalanceFail = true;
            return;
        }
        this.getUserBalanceFail = false;
        if (!TextUtils.isEmpty(myAccountEntity.getAccountBalance())) {
            if (this.giftBottomDialog != null) {
                this.myPriceBalance = NumberUtils.string2Double(myAccountEntity.getAccountBalance());
                this.giftBottomDialog.setUserBalance(this.myPriceBalance);
            }
            if (this.mLotteryDialog != null) {
                this.myPriceBalance = NumberUtils.string2Double(myAccountEntity.getAccountBalance());
                this.mLotteryDialog.setUserBalance(this.myPriceBalance);
            }
        }
        if (TextUtils.isEmpty(myAccountEntity.score) || this.giftBottomDialog == null) {
            return;
        }
        this.myScoreBalance = NumberUtils.string2Double(myAccountEntity.score);
        this.giftBottomDialog.setUserScore(NumberUtils.string2Double(myAccountEntity.score));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUserOverFail() {
        this.getUserBalanceFail = true;
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.setUserBalanceTip(R$string.fq_userover_loading_fail);
        }
        LotteryDialog lotteryDialog = this.mLotteryDialog;
        if (lotteryDialog != null) {
            lotteryDialog.setUserBalanceTip(R$string.fq_userover_loading_fail);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveAudiencesSuccess(OnLineUsersEntity onLineUsersEntity) {
        if (onLineUsersEntity != null && onLineUsersEntity.getUserEntityList() != null) {
            this.mLivePusherInfoView.replaceData(onLineUsersEntity.getUserEntityList());
        }
        this.mLivePusherInfoView.updateVipCount(onLineUsersEntity.vipCount);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onPersonalGuardInfoSuccess(GuardItemEntity guardItemEntity) {
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.updateOpenGuardInfo(guardItemEntity);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLivePopularitySuccess(long j) {
        this.onLineCount.set(j);
        this.mLivePusherInfoView.setLivePopularityCount(this.onLineCount.get());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskListSuccess(List<TaskBoxEntity> list, boolean z) {
        this.mLiveAdBannerBottomView.setTaskBoxVisibility((list == null || list.isEmpty()) ? 4 : 0);
        TaskBoxUtils.getInstance().clearList();
        TaskBoxUtils.getInstance().setData(list);
        this.mTaskBottomDialog.setmData(list);
        this.mLiveAdBannerBottomView.refreshTaskButtonForTaskBox();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskListFail() {
        this.mLiveAdBannerBottomView.setTaskBoxVisibility(4);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskTakeSuccess(TaskBoxEntity taskBoxEntity) {
        taskBoxEntity.setStatus(2);
        this.mLiveAdBannerBottomView.changeRedCountForTaskBox(false);
        this.mLiveAdBannerBottomView.checkToCountdownForTaskBox();
        this.mTaskBottomDialog.updateSingleData(taskBoxEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTaskChangeSuccess(TaskBoxEntity taskBoxEntity) {
        this.mLiveAdBannerBottomView.changeRedCountForTaskBox(true);
        taskBoxEntity.setStatus(1);
        this.mTaskBottomDialog.updateSingleData(taskBoxEntity);
        List<TaskBoxEntity> data = TaskBoxUtils.getInstance().getData();
        int indexOf = data.indexOf(taskBoxEntity);
        int i = indexOf + 1;
        if (i < data.size()) {
            data.get(i).setStatus(0);
            this.mTaskBottomDialog.updateSingleData(data.get(indexOf));
        }
        this.mLiveAdBannerBottomView.checkToCountdownForTaskBox();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTrumpetStatusSuccess(TrumpetStatusEntity trumpetStatusEntity) {
        this.trumpetStatus = trumpetStatusEntity.isEnable();
        this.curTrumpetCount.set(trumpetStatusEntity.count);
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog != null) {
            inputTextMsgForAudienceDialog.setTrumpetCount(trumpetStatusEntity.isEnable(), trumpetStatusEntity.count);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTrumpetSendSuccess() {
        showToast(R$string.fq_send_trumpet_suc);
        this.curTrumpetCount.decrementAndGet();
        if (this.curTrumpetCount.get() == 0) {
            this.curTrumpetCount.set(0);
        }
        this.mInputTextMsgDialog.setTrumpetCount(this.trumpetStatus, this.curTrumpetCount.get());
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.updateBackPackCount();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onTrumpetSendFail(int i) {
        if (i == 200107) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog == null) {
                return;
            }
            this.trumpetStatus = false;
            inputTextMsgForAudienceDialog.setTrumpetCount(false, this.curTrumpetCount.get());
            return;
        }
        switch (i) {
            case 200099:
                this.myNobilityType = -1;
                UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
                if (this.mInputTextMsgDialog == null) {
                    return;
                }
                this.trumpetStatus = false;
                this.curTrumpetCount.set(0);
                this.mInputTextMsgDialog.setTrumpetCount(false, 0);
                return;
            case 200100:
                showToast(R$string.fq_nobility_expire);
                this.myNobilityType = -1;
                UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
                InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog2 = this.mInputTextMsgDialog;
                if (inputTextMsgForAudienceDialog2 == null) {
                    return;
                }
                this.trumpetStatus = false;
                inputTextMsgForAudienceDialog2.setTrumpetCount(false, this.curTrumpetCount.get());
                return;
            case 200101:
                showToast(R$string.fq_trumpet_freezen);
                InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog3 = this.mInputTextMsgDialog;
                if (inputTextMsgForAudienceDialog3 == null) {
                    return;
                }
                this.trumpetStatus = false;
                inputTextMsgForAudienceDialog3.setTrumpetCount(false, this.curTrumpetCount.get());
                return;
            case 200102:
                showToast(R$string.fq_trumpet_count_not_enough);
                if (this.mInputTextMsgDialog == null) {
                    return;
                }
                this.trumpetStatus = true;
                this.curTrumpetCount.set(0);
                this.mInputTextMsgDialog.setTrumpetCount(true, 0);
                return;
            default:
                return;
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onFragmentConfigSuccess(PropConfigEntity propConfigEntity) {
        ComposeDialog.newInstance(propConfigEntity, true, new ComposeDialog.ComposeListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$jYpUVLpVEqjZhgLA6iwqrn-VFH4
            @Override // com.tomatolive.library.p136ui.view.dialog.alert.ComposeDialog.ComposeListener
            public final void onClick(PropConfigEntity propConfigEntity2) {
                TomatoLiveFragment.this.lambda$onFragmentConfigSuccess$13$TomatoLiveFragment(propConfigEntity2);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$onFragmentConfigSuccess$13$TomatoLiveFragment(PropConfigEntity propConfigEntity) {
        ((TomatoLivePresenter) this.mPresenter).getUsePropService(propConfigEntity, this.liveId);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onUseFragmentSuccess(PropConfigEntity propConfigEntity) {
        ComposeSuccessDialog newInstance = ComposeSuccessDialog.newInstance(propConfigEntity.propUrl);
        newInstance.show(getChildFragmentManager());
        newInstance.setOnDismissListener(new BaseRxDialogFragment.DialogDismissListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$JqbiTbgvSw79V8RSny6RsnZyebo
            @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment.DialogDismissListener
            public final void onDialogDismiss(BaseRxDialogFragment baseRxDialogFragment) {
                TomatoLiveFragment.this.lambda$onUseFragmentSuccess$14$TomatoLiveFragment(baseRxDialogFragment);
            }
        });
    }

    public /* synthetic */ void lambda$onUseFragmentSuccess$14$TomatoLiveFragment(BaseRxDialogFragment baseRxDialogFragment) {
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.updateBackPackCount();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onBoomStatusSuccess(BoomStatusEntity boomStatusEntity) {
        LotteryBoomDetailEntity lotteryBoomDetailEntity = boomStatusEntity.rich;
        LotteryBoomDetailEntity lotteryBoomDetailEntity2 = boomStatusEntity.normal;
        if (lotteryBoomDetailEntity != null && lotteryBoomDetailEntity.boomStatus > -1) {
            onLotteryBoomOpen(lotteryBoomDetailEntity.boomPropUrl, lotteryBoomDetailEntity.boomMultiple, lotteryBoomDetailEntity.boomRemainTime, lotteryBoomDetailEntity.boomTotalTime, 20);
        } else if (lotteryBoomDetailEntity2 != null && lotteryBoomDetailEntity2.boomStatus > -1) {
            onLotteryBoomOpen(lotteryBoomDetailEntity2.boomPropUrl, lotteryBoomDetailEntity2.boomMultiple, lotteryBoomDetailEntity2.boomRemainTime, lotteryBoomDetailEntity2.boomTotalTime, 1);
        } else {
            onLotteryBoomClosed();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onBoomStatusFail() {
        onLotteryBoomClosed();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onPKLiveRoomFPSuccess(boolean z, boolean z2, PKRecordEntity pKRecordEntity) {
        if (z) {
            this.linkMicPKType.set(ConstantUtils.PK_TYPE_PK_ENDING);
            this.mPKInfoView.onPKEnd(pKRecordEntity.anchorALiveId, pKRecordEntity.anchorAFP, pKRecordEntity.anchorAPopularity, pKRecordEntity.anchorAAssists, pKRecordEntity.anchorBLiveId, pKRecordEntity.anchorBFP, pKRecordEntity.anchorBPopularity, pKRecordEntity.anchorBAssists, z2);
            return;
        }
        this.mPKInfoView.initAssistData(pKRecordEntity.anchorALiveId, pKRecordEntity.anchorAFP, pKRecordEntity.anchorAPopularity, pKRecordEntity.anchorAAssists, pKRecordEntity.anchorBLiveId, pKRecordEntity.anchorBFP, pKRecordEntity.anchorBPopularity, pKRecordEntity.anchorBAssists);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onQMInteractShowTaskSuccess(List<QMInteractTaskEntity> list) {
        this.mLiveAdBannerBottomView.initIntimateTaskList(false, list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onGiftBoxListSuccess(List<GiftBoxEntity> list) {
        GiftBoxView giftBoxView = this.mGiftBoxView;
        if (giftBoxView != null) {
            giftBoxView.showBoxList(list, this.liveId);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.GuardOpenContentDialog.OnOpenGuardCallbackListener
    public void OnOpenGuardSuccess(GuardItemEntity guardItemEntity) {
        this.myPriceBalance = NumberUtils.string2Double(guardItemEntity.getAccountBalance());
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.setUserBalance(this.myPriceBalance);
        }
        ((TomatoLivePresenter) this.mPresenter).getPersonalGuardInfo(this.anchorId);
    }

    private void sendAdImageRequest() {
        ((TomatoLivePresenter) this.mPresenter).getAdImageList("2");
        ((TomatoLivePresenter) this.mPresenter).getAdImageList("7");
        ((TomatoLivePresenter) this.mPresenter).getAdImageList("3");
        ((TomatoLivePresenter) this.mPresenter).getLiveAdNoticeList();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveAdListSuccess(String str, List<BannerEntity> list) {
        LivePusherInfoView livePusherInfoView;
        LiveAdBannerBottomView liveAdBannerBottomView;
        LivePusherInfoView livePusherInfoView2;
        AnchorEntity anchorEntity = this.anchorItemEntity;
        String str2 = anchorEntity == null ? "" : anchorEntity.openId;
        if (TextUtils.equals(str, "2") && (livePusherInfoView2 = this.mLivePusherInfoView) != null) {
            livePusherInfoView2.initAdBannerImages(this.anchorAppId, str2, list);
        }
        if (TextUtils.equals(str, "7") && (liveAdBannerBottomView = this.mLiveAdBannerBottomView) != null) {
            liveAdBannerBottomView.initAdBannerImages(this.anchorAppId, str2, list);
        }
        if (!TextUtils.equals(str, "3") || (livePusherInfoView = this.mLivePusherInfoView) == null) {
            return;
        }
        livePusherInfoView.initVerticalAdImage(this.anchorAppId, str2, list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onLiveAdNoticeSuccess(BannerEntity bannerEntity) {
        if (!TextUtils.isEmpty(bannerEntity.content)) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(10);
            chatEntity.setMsgText(bannerEntity.content);
            this.mLiveChatMsgView.addChatMsg(chatEntity);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        resumePlay();
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onResume();
        }
        LivePayEnterView livePayEnterView = this.livePayEnterView;
        if (livePayEnterView != null) {
            livePayEnterView.onResume();
        }
        if (TaskBoxUtils.getInstance().isPushInBackground() && this.mTaskBottomDialog != null) {
            this.mLiveAdBannerBottomView.checkToCountdownForTaskBox();
            TaskBoxUtils.getInstance().setPushInBackground(false);
        }
        if (!this.isPayLiveTipsDialog || !isPayLiveNeedBuyTicket()) {
            return;
        }
        showDialogFragment(this.payLiveTipsDialog);
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        pausePlay();
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onPause();
        }
        LivePayEnterView livePayEnterView = this.livePayEnterView;
        if (livePayEnterView != null) {
            livePayEnterView.onPause();
        }
        if (SystemUtils.isApplicationInBackground(this.mContext)) {
            TaskBoxUtils.getInstance().setPushInBackground(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeLineReloadLoading(int i) {
        if (i != -1) {
            String pullStreamUrl = getPullStreamUrl(i);
            if (!TextUtils.isEmpty(pullStreamUrl)) {
                this.pullStreamUrl = pullStreamUrl;
            }
        }
        if (NetUtils.getNetWorkState() == -1) {
            showToast(getResources().getString(R$string.fq_text_no_network));
        } else {
            switchStream();
        }
    }

    private String getPullStreamUrl(int i) {
        try {
            return this.pullStreamUrlList.get(i);
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchStream() {
        if (isLiving()) {
            showLoadingAnim();
            this.playManager.switchStream(this.pullStreamUrl);
        }
    }

    private void resumePlay() {
        if (!isLiveEnd()) {
            this.playManager.resumePlay(this.isPausing);
        }
        this.isPausing = false;
    }

    private void pausePlay() {
        if (!isLiveEnd()) {
            this.playManager.pausePlay();
        }
        this.isPausing = true;
    }

    private void stopPlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.stopPlay();
        }
    }

    private void releasePlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.release();
        }
    }

    private void onDestroyPlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.onDestroyPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPullTimeOut() {
        this.pullTimeOutTimer = Observable.timer(10L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$beUUYshjfO1zvzMvhSIQSAad_bA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TomatoLiveFragment.this.lambda$startPullTimeOut$15$TomatoLiveFragment((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startPullTimeOut$15$TomatoLiveFragment(Long l) throws Exception {
        if (this.pullTimeOutTimer == null || this.playManager.getListener() == null) {
            return;
        }
        this.playManager.getListener().onNetError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPullTimeOut() {
        Disposable disposable = this.pullTimeOutTimer;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.pullTimeOutTimer.dispose();
        this.pullTimeOutTimer = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealPlayError() {
        cancelPullTimeOut();
        if (!isLiveEnd()) {
            this.isAutoGiftDialogFromWeekStar = false;
            showReloadButton();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.gift.GiftFrameLayout.BarrageEndAnimationListener
    public void onEndAnimation(GiftAnimModel giftAnimModel) {
        if (this.myUserInfoEntity == null || !TextUtils.equals(giftAnimModel.getSendUserId(), this.myUserInfoEntity.getUserId())) {
            return;
        }
        if (giftAnimModel.isProp) {
            this.myPropIndexMap.get(giftAnimModel.getGiftId()).countDownStartTime = System.currentTimeMillis();
            return;
        }
        this.myGiftIndexMap.get(giftAnimModel.getGiftId()).countDownStartTime = System.currentTimeMillis();
    }

    private void playBigAnim(GiftItemEntity giftItemEntity) {
        switch (giftItemEntity.bigAnimType) {
            case ConstantUtils.BIG_ANIM_GIFT_TYPE /* 2304 */:
                if (giftItemEntity.isSendSingleGift()) {
                    if (TextUtils.isEmpty(giftItemEntity.giftDirPath) || !FileUtils.isFile(AppUtils.getLocalGiftFilePath(giftItemEntity.giftDirPath))) {
                        this.liveAnimationView.loadOnlineGiftAnim(giftItemEntity);
                        return;
                    } else {
                        this.liveAnimationView.loadLocalGiftAnim(giftItemEntity);
                        return;
                    }
                }
                this.liveAnimationView.loadPropAnimation(giftItemEntity.animalUrl);
                return;
            case ConstantUtils.BIG_ANIM_PROP_TYPE /* 2305 */:
                this.liveAnimationView.loadPropAnimation(giftItemEntity.animalUrl);
                return;
            case ConstantUtils.BIG_ANIM_OPEN_NOBILITY_TYPE /* 2306 */:
                this.liveAnimationView.loadNobilityOpenBigAnimation(giftItemEntity);
                return;
            case ConstantUtils.BIG_ANIM_OPEN_GUARD_TYPE /* 2307 */:
                this.liveAnimationView.loadGuardOpenBigAnim(giftItemEntity.guardType);
                return;
            default:
                return;
        }
    }

    @Override // com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack
    public void onBackThreadReceiveBigAnimMsg(GiftItemEntity giftItemEntity) {
        this.curBigAnimSendUserId = giftItemEntity.userId;
        playBigAnim(giftItemEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wsManagerNotifyBigAnim() {
        this.curBigAnimSendUserId = "";
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.notifyBigAnim();
        }
    }

    private void wsManagerNotifyAnim() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.notifyAnim();
        }
    }

    private void initGiftDownloadData() {
        List<GiftDownloadItemEntity> localDownloadListFilterLuckyGift = GiftDownLoadManager.getInstance().getLocalDownloadListFilterLuckyGift();
        if (localDownloadListFilterLuckyGift == null || localDownloadListFilterLuckyGift.size() == 0) {
            this.isGetGiftListFail = true;
            return;
        }
        this.isGetGiftListFail = false;
        initGiftDialog(localDownloadListFilterLuckyGift);
    }

    private void initGiftDialog(List<GiftDownloadItemEntity> list) {
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog == null) {
            this.giftBottomDialog = GiftBackpackDialog.create(getChildFragmentManager(), formatGiftList(list), new C398825());
        } else {
            giftBackpackDialog.updateGiftList(formatGiftList(list));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$25 */
    /* loaded from: classes3.dex */
    public class C398825 implements GiftBackpackDialog.SendClickListener {
        C398825() {
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog.SendClickListener
        public void onSendCallback(boolean z, boolean z2, BaseGiftBackpackEntity baseGiftBackpackEntity) {
            if (z2) {
                if (!(baseGiftBackpackEntity instanceof GiftDownloadItemEntity)) {
                    return;
                }
                TomatoLiveFragment.this.sendGift((GiftDownloadItemEntity) baseGiftBackpackEntity);
            } else if (!(baseGiftBackpackEntity instanceof BackpackItemEntity)) {
            } else {
                BackpackItemEntity backpackItemEntity = (BackpackItemEntity) baseGiftBackpackEntity;
                if (backpackItemEntity.isNobilityTrumpetBoolean()) {
                    TomatoLiveFragment.this.isLotteryDialogFlag = false;
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.giftBottomDialog);
                    TomatoLiveFragment.this.showInputMsgDialog(true);
                } else if (!backpackItemEntity.isPropFragmentBoolean()) {
                    TomatoLiveFragment.this.sendProp(backpackItemEntity, z);
                } else if (NumberUtils.string2int(backpackItemEntity.count) >= 50) {
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getFragmentConfig(TomatoLiveFragment.this.liveId);
                } else {
                    ComposeDialog.newInstance(null, false, null).show(TomatoLiveFragment.this.getChildFragmentManager());
                }
            }
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog.SendClickListener
        public void onRechargeCallback(View view) {
            AppUtils.onRechargeListener(((BaseFragment) TomatoLiveFragment.this).mContext);
            LogEventUtils.uploadRechargeClick(TomatoLiveFragment.this.getString(R$string.fq_gift_recharge_entrance));
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog.SendClickListener
        public void onScoreCallback(View view) {
            AppUtils.onScoreListener(((BaseFragment) TomatoLiveFragment.this).mContext);
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog.SendClickListener
        public void onGoToWeekStarList() {
            TomatoLiveFragment.this.isAutoGiftDialogFromWeekStar = true;
            TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
            tomatoLiveFragment.mWeekStarRankingDialog = WeekStarRankingDialog.newInstance(tomatoLiveFragment.anchorItemEntity, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.25.1
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                    super.onAnchorItemClickListener(anchorEntity);
                    TomatoLiveFragment.this.isAutoGiftDialogFromWeekStar = false;
                    TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                    tomatoLiveFragment2.dismissDialogFragment(tomatoLiveFragment2.mWeekStarRankingDialog);
                    TomatoLiveFragment.this.startActivityById(anchorEntity.liveId);
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserItemClickListener(UserEntity userEntity) {
                    super.onUserItemClickListener(userEntity);
                    if (TextUtils.isEmpty(userEntity.getUserId())) {
                        return;
                    }
                    TomatoLiveFragment.this.showUserCard(userEntity);
                }
            });
            TomatoLiveFragment.this.mWeekStarRankingDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
            if (TomatoLiveFragment.this.mWeekStarRankingDialog != null) {
                TomatoLiveFragment.this.mWeekStarRankingDialog.setOnDismissListener(new BaseRxDialogFragment.DialogDismissListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$25$wr-poED-graFev1Nk4D12FCe5z8
                    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment.DialogDismissListener
                    public final void onDialogDismiss(BaseRxDialogFragment baseRxDialogFragment) {
                        TomatoLiveFragment.C398825.this.lambda$onGoToWeekStarList$0$TomatoLiveFragment$25(baseRxDialogFragment);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onGoToWeekStarList$0$TomatoLiveFragment$25(BaseRxDialogFragment baseRxDialogFragment) {
            if (!TomatoLiveFragment.this.isAutoGiftDialogFromWeekStar || TomatoLiveFragment.this.giftBottomDialog == null || TomatoLiveFragment.this.giftBottomDialog.isAdded()) {
                return;
            }
            TomatoLiveFragment.this.giftBottomDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.GiftBackpackDialog.SendClickListener
        public void onOpenNobilityCallback() {
            TomatoLiveFragment.this.toNobilityOpenActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendProp(BackpackItemEntity backpackItemEntity, boolean z) {
        if (!NetUtils.isNetworkAvailable() || isSocketClose()) {
            showToast(R$string.fq_text_no_network_send_prop);
        } else if (backpackItemEntity == null) {
            showToast(R$string.fq_please_choose_prop);
        } else {
            GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
            if (giftBackpackDialog != null && giftBackpackDialog.isResumed()) {
                this.giftBottomDialog.showDownCountAndFlyAnim(z);
            }
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.sendPropSendMessage(MessageHelper.convertToPropSend("1", backpackItemEntity.f5831id, this.liveCount));
        }
    }

    private void initTaskDialog() {
        this.mLiveAdBannerBottomView.setTaskBoxVisibility(4);
        if (this.mTaskBottomDialog == null) {
            this.mTaskBottomDialog = TaskBottomDialog.create(new TaskBottomDialog.TaskClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ESMhpiRW0Ge971DH8jDBVylXKEk
                @Override // com.tomatolive.library.p136ui.view.dialog.TaskBottomDialog.TaskClickListener
                public final void onTaskCallback(TaskBoxEntity taskBoxEntity) {
                    TomatoLiveFragment.this.lambda$initTaskDialog$16$TomatoLiveFragment(taskBoxEntity);
                }
            });
        }
        ((TomatoLivePresenter) this.mPresenter).getTaskList(false);
    }

    public /* synthetic */ void lambda$initTaskDialog$16$TomatoLiveFragment(TaskBoxEntity taskBoxEntity) {
        if (taskBoxEntity.getStatus() != 1 || this.isSocketError) {
            return;
        }
        ((TomatoLivePresenter) this.mPresenter).getTaskTake(taskBoxEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGift(GiftDownloadItemEntity giftDownloadItemEntity) {
        GiftItemEntity formatGiftItemEntity = GiftDownLoadManager.getInstance().formatGiftItemEntity(giftDownloadItemEntity);
        if (!NetUtils.isNetworkAvailable()) {
            showToast(R$string.fq_text_no_network_send_gift);
        } else if (isSocketClose()) {
            showToast(R$string.fq_text_network_error_send_gift);
        } else if (formatGiftItemEntity == null) {
            showToast(R$string.fq_please_choose_gift);
        } else if (this.getUserBalanceFail) {
            showToast(R$string.fq_userover_loading_fail);
            this.giftBottomDialog.setUserBalanceTip(R$string.fq_userover_loading);
            ((TomatoLivePresenter) this.mPresenter).getUserOver();
        } else if (!isSendGift(formatGiftItemEntity)) {
            dismissDialogFragment(this.giftBottomDialog);
            if (formatGiftItemEntity.isScoreGift()) {
                if (AppUtils.isEnableScore()) {
                    AppUtils.onScoreListener(this.mContext);
                    return;
                } else {
                    showToast(R$string.fq_score_not_enough_tips);
                    return;
                }
            }
            AppUtils.onRechargeListener(this.mContext);
            LogEventUtils.uploadRechargeClick(getString(R$string.fq_gift_recharge_entrance));
        } else if (!GiftDownLoadManager.getInstance().checkGiftExist(formatGiftItemEntity)) {
            showToast(R$string.fq_gift_res_update);
            if (!this.isGiftListUpdating) {
                this.isGiftListUpdating = true;
                ((TomatoLivePresenter) this.mPresenter).getGiftList();
            }
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$lTaps8mlnajXL7t1dJoZsgV517o
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$sendGift$17$TomatoLiveFragment();
                }
            });
        } else {
            GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
            if (giftBackpackDialog != null && giftBackpackDialog.isResumed()) {
                this.giftBottomDialog.showDownCountAndFlyAnim(false);
            }
            String iPAddress = NetworkUtils.getIPAddress(true);
            formatGiftItemEntity.userId = this.myUserInfoEntity.getUserId();
            formatGiftItemEntity.avatar = UserInfoManager.getInstance().getAvatar();
            formatGiftItemEntity.role = this.myUserInfoEntity.getRole();
            formatGiftItemEntity.anchorId = this.anchorId;
            formatGiftItemEntity.anchorName = this.anchorItemEntity.nickname;
            formatGiftItemEntity.liveId = this.liveId;
            formatGiftItemEntity.expGrade = this.myUserInfoEntity.getExpGrade();
            formatGiftItemEntity.liveCount = this.liveCount;
            if (TextUtils.isEmpty(iPAddress)) {
                iPAddress = "172.19.24.10";
            }
            formatGiftItemEntity.clientIp = iPAddress;
            formatGiftItemEntity.guardType = NumberUtils.string2int(this.guardItemEntity.userGuardType);
            if (formatGiftItemEntity.isScoreGift()) {
                this.myScoreBalance -= NumberUtils.mul(this.giftBottomDialog.getGiftNum(), NumberUtils.string2Double(formatGiftItemEntity.price));
                if (this.myScoreBalance < 0.0d) {
                    this.myScoreBalance = 0.0d;
                }
                this.giftBottomDialog.setUserScore(this.myScoreBalance);
            } else {
                this.myPriceBalance -= NumberUtils.mul(this.giftBottomDialog.getGiftNum(), NumberUtils.string2Double(formatGiftItemEntity.price));
                if (this.myPriceBalance < 0.0d) {
                    this.myPriceBalance = 0.0d;
                }
                this.giftBottomDialog.setUserBalance(this.myPriceBalance);
            }
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.sendGiftMessage(MessageHelper.convertToGiftMsg(formatGiftItemEntity));
        }
    }

    public /* synthetic */ void lambda$sendGift$17$TomatoLiveFragment() {
        dismissDialogFragment(this.giftBottomDialog);
    }

    private boolean isSendGift(GiftItemEntity giftItemEntity) {
        int giftNum = this.giftBottomDialog.getGiftNum() == 0 ? 1 : this.giftBottomDialog.getGiftNum();
        giftItemEntity.isScoreGift();
        return (giftItemEntity.isScoreGift() ? this.myScoreBalance : this.myPriceBalance) >= NumberUtils.mul((double) giftNum, NumberUtils.string2Double(giftItemEntity.price));
    }

    private List<GiftDownloadItemEntity> formatGiftList(List<GiftDownloadItemEntity> list) {
        ArrayList arrayList = new ArrayList(list);
        int i = 0;
        if (arrayList.isEmpty()) {
            while (i < 8) {
                arrayList.add(new GiftDownloadItemEntity(getString(R$string.fq_come_soon), "0", true));
                i++;
            }
            return arrayList;
        }
        int size = arrayList.size() % 8;
        if (size <= 0) {
            return arrayList;
        }
        int i2 = 8 - size;
        while (i < i2) {
            arrayList.add(new GiftDownloadItemEntity(getString(R$string.fq_come_soon), "0", true));
            i++;
        }
        return arrayList;
    }

    private void showGiftPanel() {
        AnchorEntity anchorEntity = this.anchorItemEntity;
        LogEventUtils.uploadGiftButtonClick(anchorEntity.openId, anchorEntity.appId, anchorEntity.nickname, this.liveId, this.myUserInfoEntity.expGrade);
        AnimUtils.playScaleAnim(this.giftButton);
        if (!isCanSendGift() || this.giftBottomDialog == null) {
            return;
        }
        getUserBalance(0);
        if (this.giftBottomDialog.isAdded()) {
            return;
        }
        this.isLotteryDialogFlag = false;
        this.giftBottomDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCanSendGift() {
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), this.anchorId)) {
            showToast(R$string.fq_no_send_gift_myself);
            return false;
        } else if (this.isGetGiftListFail) {
            showToast(R$string.fq_gift_loading);
            if (!this.startGetGiftListInfo) {
                this.startGetGiftListInfo = true;
                ((TomatoLivePresenter) this.mPresenter).getGiftList();
            }
            return false;
        } else if (!this.isGiftListUpdating) {
            return true;
        } else {
            showToast(R$string.fq_gift_res_update);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void playMySelfAnimOnMainThread(SocketMessageEvent.ResultData resultData) {
        boolean z;
        GiftIndexEntity giftIndexEntity;
        GiftItemEntity giftItemEntity = GiftDownLoadManager.getInstance().getGiftItemEntity(resultData.markId);
        if (giftItemEntity == null) {
            showToast(R$string.fq_gift_res_update);
            if (!this.isGiftListUpdating) {
                this.isGiftListUpdating = true;
                ((TomatoLivePresenter) this.mPresenter).getGiftList();
            }
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$8_xhYPy-aByU_Ua43m0ra8Gcu-s
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$playMySelfAnimOnMainThread$18$TomatoLiveFragment();
                }
            });
            return;
        }
        boolean z2 = !TextUtils.equals(resultData.giftNum, "1");
        giftItemEntity.onlineDefaultUrl = giftItemEntity.animalUrl;
        if (z2) {
            GiftBatchItemEntity giftBatchByNum = giftItemEntity.getGiftBatchByNum(resultData.giftNum);
            giftItemEntity.markId = MD5Utils.getMd5(resultData.userId + resultData.markId + resultData.giftNum);
            if (giftBatchByNum != null) {
                giftItemEntity.animalUrl = giftBatchByNum.animalUrl;
                giftItemEntity.active_time = giftBatchByNum.active_time;
                giftItemEntity.duration = giftBatchByNum.duration;
                if (!TextUtils.isEmpty(giftItemEntity.animalUrl)) {
                    z = true;
                    giftIndexEntity = this.myGiftIndexMap.get(giftItemEntity.markId);
                    if (giftIndexEntity != null) {
                        giftIndexEntity = new GiftIndexEntity();
                        giftIndexEntity.sendIndex++;
                        this.isContinueCombo = false;
                        this.myGiftIndexMap.put(giftItemEntity.markId, giftIndexEntity);
                    } else if (giftIndexEntity.countDownStartTime == 0) {
                        giftIndexEntity.sendIndex++;
                        this.isContinueCombo = false;
                    } else if (System.currentTimeMillis() - giftIndexEntity.countDownStartTime > NumberUtils.formatMillisecond(giftItemEntity.active_time)) {
                        giftIndexEntity.sendIndex = 1;
                        giftIndexEntity.countDownStartTime = 0L;
                        this.isContinueCombo = false;
                    } else {
                        giftIndexEntity.sendIndex++;
                        this.isContinueCombo = true;
                        giftIndexEntity.countDownStartTime = 0L;
                    }
                    giftItemEntity.sendUserName = resultData.userName;
                    giftItemEntity.userId = resultData.userId;
                    giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
                    giftItemEntity.avatar = resultData.avatar;
                    giftItemEntity.role = resultData.role;
                    giftItemEntity.userRole = resultData.userRole;
                    giftItemEntity.sex = resultData.sex;
                    giftItemEntity.expGrade = AppUtils.formatExpGrade(resultData.expGrade);
                    giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
                    giftItemEntity.nobilityType = resultData.nobilityType;
                    giftItemEntity.weekStar = resultData.isWeekStar;
                    giftItemEntity.openId = resultData.openId;
                    giftItemEntity.appId = resultData.appId;
                    giftItemEntity.giftNum = resultData.giftNum;
                    giftItemEntity.marks = resultData.markUrls;
                    if (!z2) {
                        if (z) {
                            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$lKOBVNj5q47ELAoRJiu-H2kz99o
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TomatoLiveFragment.this.lambda$playMySelfAnimOnMainThread$19$TomatoLiveFragment();
                                }
                            });
                            WsManager wsManager = this.wsManager;
                            if (wsManager != null) {
                                wsManager.addLocalAnim(giftItemEntity);
                            }
                        }
                    } else if (giftItemEntity.isBigAnim()) {
                        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$qSFTw3RX7BR_Q_P8EsuJAzLSMmc
                            @Override // java.lang.Runnable
                            public final void run() {
                                TomatoLiveFragment.this.lambda$playMySelfAnimOnMainThread$20$TomatoLiveFragment();
                            }
                        });
                        WsManager wsManager2 = this.wsManager;
                        if (wsManager2 != null) {
                            wsManager2.addLocalAnim(giftItemEntity);
                        }
                    }
                    playMySelfAnimGift(giftItemEntity);
                    showSelfGiftMsgOnChatList(giftItemEntity);
                    LogEventUtils.uploadSendGift(this.anchorItemEntity, giftItemEntity, resultData.giftNum, this.liveId, this.myUserInfoEntity.expGrade);
                }
            }
        }
        z = false;
        giftIndexEntity = this.myGiftIndexMap.get(giftItemEntity.markId);
        if (giftIndexEntity != null) {
        }
        giftItemEntity.sendUserName = resultData.userName;
        giftItemEntity.userId = resultData.userId;
        giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.role = resultData.role;
        giftItemEntity.userRole = resultData.userRole;
        giftItemEntity.sex = resultData.sex;
        giftItemEntity.expGrade = AppUtils.formatExpGrade(resultData.expGrade);
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.weekStar = resultData.isWeekStar;
        giftItemEntity.openId = resultData.openId;
        giftItemEntity.appId = resultData.appId;
        giftItemEntity.giftNum = resultData.giftNum;
        giftItemEntity.marks = resultData.markUrls;
        if (!z2) {
        }
        playMySelfAnimGift(giftItemEntity);
        showSelfGiftMsgOnChatList(giftItemEntity);
        LogEventUtils.uploadSendGift(this.anchorItemEntity, giftItemEntity, resultData.giftNum, this.liveId, this.myUserInfoEntity.expGrade);
    }

    public /* synthetic */ void lambda$playMySelfAnimOnMainThread$18$TomatoLiveFragment() {
        dismissDialogFragment(this.giftBottomDialog);
    }

    private void playReceiveAnimGift(final GiftItemEntity giftItemEntity) {
        if (giftItemEntity == null) {
            return;
        }
        final GiftAnimModel giftAnimModel = new GiftAnimModel();
        giftAnimModel.setGiftId(giftItemEntity.markId).setProp(giftItemEntity.isPropBigAnimType()).setOnLineUrl(giftItemEntity.animalUrl).setOnlineDefaultUrl(giftItemEntity.onlineDefaultUrl).setEffectType(String.valueOf(giftItemEntity.effect_type)).setGiftName(giftItemEntity.name).setGiftCount(1).setGiftPic(giftItemEntity.imgurl).setGiftPrice(giftItemEntity.price).setSendGiftTime(Long.valueOf(System.currentTimeMillis())).setCurrentStart(true).setGiftDirPath(giftItemEntity.giftDirPath).setGiftShowTime(NumberUtils.formatMillisecond(giftItemEntity.duration)).setSendIndex(giftItemEntity.sendIndex).setAnimationListener(this).setGiftNum(giftItemEntity.giftNum).setSendUserId(giftItemEntity.userId).setSendUserName(giftItemEntity.sendUserName).setSendUserExpGrade(giftItemEntity.expGrade).setSendUserGuardType(giftItemEntity.guardType).setSendUserNobilityType(giftItemEntity.nobilityType).setSendUserRole(giftItemEntity.userRole).setSendRole(giftItemEntity.role).setSendUserSex(giftItemEntity.sex).setAppId(giftItemEntity.appId).setOpenId(giftItemEntity.openId).setSendUserAvatar(giftItemEntity.avatar);
        giftAnimModel.setJumpCombo(giftItemEntity.sendIndex);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$rDvGFfPcVsaNtR7BnS2uoOdFP5o
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$playReceiveAnimGift$21$TomatoLiveFragment(giftAnimModel, giftItemEntity);
            }
        });
    }

    public /* synthetic */ void lambda$playReceiveAnimGift$21$TomatoLiveFragment(GiftAnimModel giftAnimModel, GiftItemEntity giftItemEntity) {
        this.liveAnimationView.loadReceiveGift(giftAnimModel, giftItemEntity);
    }

    private void playReceiveAnimOnMainThread(SocketMessageEvent.ResultData resultData) {
        WsManager wsManager;
        long currentTimeMillis = System.currentTimeMillis();
        GiftItemEntity giftItemEntity = GiftDownLoadManager.getInstance().getGiftItemEntity(resultData.markId);
        if (giftItemEntity == null) {
            showToast(R$string.fq_gift_res_update);
            if (!this.isGiftListUpdating) {
                this.isGiftListUpdating = true;
                ((TomatoLivePresenter) this.mPresenter).getGiftList();
            }
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$UDsoZCi9dBZEz9rkiToi9rUGR6k
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$playReceiveAnimOnMainThread$22$TomatoLiveFragment();
                }
            });
            return;
        }
        giftItemEntity.onlineDefaultUrl = giftItemEntity.animalUrl;
        boolean z = false;
        boolean z2 = !TextUtils.equals(resultData.giftNum, "1");
        if (z2) {
            GiftBatchItemEntity giftBatchByNum = giftItemEntity.getGiftBatchByNum(resultData.giftNum);
            giftItemEntity.markId = MD5Utils.getMd5(resultData.userId + resultData.markId + resultData.giftNum);
            if (giftBatchByNum != null) {
                giftItemEntity.animalUrl = giftBatchByNum.animalUrl;
                giftItemEntity.active_time = giftBatchByNum.active_time;
                giftItemEntity.duration = giftBatchByNum.duration;
                if (!TextUtils.isEmpty(giftItemEntity.animalUrl)) {
                    z = true;
                }
            }
        }
        if (!this.receiveGiftMap.containsKey(resultData.userId)) {
            HashMap hashMap = new HashMap(8);
            GiftIndexEntity giftIndexEntity = new GiftIndexEntity();
            giftIndexEntity.createTime = currentTimeMillis;
            giftIndexEntity.sendIndex = 1;
            hashMap.put(giftItemEntity.markId, giftIndexEntity);
            this.receiveGiftMap.put(resultData.userId, hashMap);
            giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
        } else {
            Map<String, GiftIndexEntity> map = this.receiveGiftMap.get(resultData.userId);
            GiftIndexEntity giftIndexEntity2 = map.get(giftItemEntity.markId);
            if (giftIndexEntity2 == null) {
                giftIndexEntity2 = new GiftIndexEntity();
                giftIndexEntity2.createTime = currentTimeMillis;
                giftIndexEntity2.sendIndex = 1;
                map.put(giftItemEntity.markId, giftIndexEntity2);
            } else {
                if (currentTimeMillis - giftIndexEntity2.createTime < NumberUtils.formatMillisecond(giftItemEntity.active_time + giftItemEntity.duration)) {
                    giftIndexEntity2.sendIndex++;
                } else {
                    giftIndexEntity2.sendIndex = 1;
                }
                giftIndexEntity2.createTime = currentTimeMillis;
            }
            giftItemEntity.sendIndex = giftIndexEntity2.sendIndex;
        }
        giftItemEntity.sendUserName = resultData.userName;
        giftItemEntity.userId = resultData.userId;
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.role = resultData.role;
        giftItemEntity.userRole = resultData.userRole;
        giftItemEntity.expGrade = AppUtils.formatExpGrade(resultData.expGrade);
        giftItemEntity.sex = resultData.sex;
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        giftItemEntity.openId = resultData.openId;
        giftItemEntity.appId = resultData.appId;
        giftItemEntity.giftNum = resultData.giftNum;
        if (z2) {
            if (z && (wsManager = this.wsManager) != null) {
                wsManager.addReceiveBigAnim(giftItemEntity);
            }
            if (giftItemEntity.sendIndex == 0) {
                giftItemEntity.sendIndex = 1;
            }
        } else if (giftItemEntity.isBigAnim()) {
            WsManager wsManager2 = this.wsManager;
            if (wsManager2 != null) {
                wsManager2.addReceiveBigAnim(giftItemEntity);
            }
            if (giftItemEntity.sendIndex == 0) {
                giftItemEntity.sendIndex = 1;
            }
        }
        if (NumberUtils.string2int(resultData.giftNum) > 1) {
            showReceiveMsgOnChatList(resultData, AppUtils.appendBatchGiftString(giftItemEntity), 1);
        } else if (giftItemEntity.isBigAnim()) {
            showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringWithIndex(giftItemEntity), 1);
        } else {
            int i = giftItemEntity.sendIndex;
            if (i == 1) {
                showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringNoIndex(giftItemEntity), 1);
            } else if (i != 0 && i % 10 == 0) {
                showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringWithIndex(giftItemEntity), 1);
            }
        }
        playReceiveAnimGift(giftItemEntity);
        wsManagerNotifyAnim();
    }

    public /* synthetic */ void lambda$playReceiveAnimOnMainThread$22$TomatoLiveFragment() {
        dismissDialogFragment(this.giftBottomDialog);
    }

    private void playMySelfAnimGift(final GiftItemEntity giftItemEntity) {
        if (giftItemEntity == null) {
            return;
        }
        final GiftAnimModel giftAnimModel = new GiftAnimModel();
        giftAnimModel.setGiftId(giftItemEntity.markId).setProp(giftItemEntity.isPropBigAnimType()).setOnLineUrl(giftItemEntity.animalUrl).setOnlineDefaultUrl(giftItemEntity.onlineDefaultUrl).setEffectType(String.valueOf(giftItemEntity.effect_type)).setGiftName(giftItemEntity.name).setGiftCount(1).setGiftPic(giftItemEntity.imgurl).setGiftPrice(giftItemEntity.price).setSendGiftTime(Long.valueOf(System.currentTimeMillis())).setCurrentStart(true).setGiftDirPath(giftItemEntity.giftDirPath).setGiftShowTime(NumberUtils.formatMillisecond(giftItemEntity.duration)).setSendIndex(giftItemEntity.sendIndex).setAnimationListener(this).setGiftNum(giftItemEntity.giftNum).setSendUserId(this.myUserInfoEntity.getUserId()).setSendUserName(UserInfoManager.getInstance().getUserNickname()).setSendUserExpGrade(giftItemEntity.expGrade).setSendUserGuardType(giftItemEntity.guardType).setSendUserNobilityType(giftItemEntity.nobilityType).setSendUserRole(giftItemEntity.userRole).setSendRole(giftItemEntity.role).setSendUserSex(giftItemEntity.sex).setAppId(giftItemEntity.appId).setOpenId(giftItemEntity.openId).setSendUserAvatar(giftItemEntity.avatar);
        if (this.isContinueCombo) {
            giftAnimModel.setJumpCombo(giftItemEntity.sendIndex);
        } else {
            giftAnimModel.setJumpCombo(0);
        }
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Ta-AbfXl4SXuhCFcKZk4qOY-yrc
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$playMySelfAnimGift$23$TomatoLiveFragment(giftAnimModel, giftItemEntity);
            }
        });
    }

    public /* synthetic */ void lambda$playMySelfAnimGift$23$TomatoLiveFragment(GiftAnimModel giftAnimModel, GiftItemEntity giftItemEntity) {
        this.liveAnimationView.loadGift(giftAnimModel, giftItemEntity);
    }

    private boolean isSocketClose() {
        return this.isSocketClose || this.isSocketError;
    }

    private void initSocket() {
        if (this.wsManager != null) {
            return;
        }
        addSocketTipMsg(R$string.fq_start_connect_socket);
        this.wsManager = WsManager.getInstance();
        this.wsManager.init(this, this.socketUrl, AppUtils.getSocketHeartBeatInterval());
        this.wsManager.setOnWebSocketListener(new C399026());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$26 */
    /* loaded from: classes3.dex */
    public class C399026 implements OnWebSocketStatusListener {
        C399026() {
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onOpen(boolean z) {
            if (TomatoLiveFragment.this.isTaskSocket) {
                TomatoLiveFragment.this.isTaskSocket = false;
            }
            TomatoLiveFragment.this.isSocketReConn = z;
            TomatoLiveFragment.this.isConnectingChatService = false;
            TomatoLiveFragment.this.reConnectCountOver = false;
            if (z) {
                TomatoLiveFragment.this.addSocketTipMsg(R$string.fq_reconnect_suc);
                TomatoLiveFragment.this.isStartGetRoomInfo = true;
                TomatoLiveFragment.this.sendLiveInitInfoRequest();
                TomatoLiveFragment.this.handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$26$etW1kpfBvq2Cf3gURaTKUgLaokU
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.C399026.this.lambda$onOpen$0$TomatoLiveFragment$26();
                    }
                });
            } else {
                TomatoLiveFragment.this.addSocketTipMsg(R$string.fq_connect_suc);
                if (AppUtils.isConsumptionPermissionUser() && TomatoLiveFragment.this.myselfEnterMessageEvent != null) {
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.onBackThreadReceiveMessage(tomatoLiveFragment.myselfEnterMessageEvent);
                }
            }
            if (TomatoLiveFragment.this.myUserInfoEntity != null && TomatoLiveFragment.this.myUserInfoEntity.isOfflinePrivateMsg()) {
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).onSendOfflinePrivateMsg();
            }
            TomatoLiveFragment.this.isSocketClose = false;
            TomatoLiveFragment.this.isSocketError = false;
            TomatoLiveFragment.this.handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$26$RW5tencLy7nhdqwNiHEiDL7vcJc
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.C399026.this.lambda$onOpen$1$TomatoLiveFragment$26();
                }
            });
        }

        public /* synthetic */ void lambda$onOpen$0$TomatoLiveFragment$26() {
            TomatoLiveFragment.this.changePrivateMessageNetStatus(true);
        }

        public /* synthetic */ void lambda$onOpen$1$TomatoLiveFragment$26() {
            TomatoLiveFragment.this.tvInput.setText(R$string.fq_talk_something);
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onClose() {
            TomatoLiveFragment.this.isSocketClose = true;
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onError(boolean z, String str) {
            if (str == null || !str.contains("HTTP/1.1 403")) {
                TomatoLiveFragment.this.isSocketError = true;
                TomatoLiveFragment.this.addSocketTipMsg(R$string.fq_connect_fail);
                return;
            }
            TomatoLiveFragment.this.startTokenDialogService();
            TomatoLiveFragment.this.onFinishActivity();
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void reConnecting() {
            TomatoLiveFragment.this.isConnectingChatService = true;
            TomatoLiveFragment.this.addSocketTipMsg(R$string.fq_start_reconnect_socket);
            TomatoLiveFragment.this.handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$26$Oe5MGWbeD0pSiMetRt9rV7g4xrY
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.C399026.this.lambda$reConnecting$2$TomatoLiveFragment$26();
                }
            });
            ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getWebSocketAddress(TomatoLiveFragment.this.liveId, "2");
        }

        public /* synthetic */ void lambda$reConnecting$2$TomatoLiveFragment$26() {
            TomatoLiveFragment.this.tvInput.setText(R$string.fq_start_reconnect_socket);
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void reConnectCountOver() {
            TomatoLiveFragment.this.isConnectingChatService = false;
            TomatoLiveFragment.this.reConnectCountOver = true;
            TomatoLiveFragment.this.handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$26$5JJT_TKxrtiSVSDkbuwj7C-hJ6o
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.C399026.this.lambda$reConnectCountOver$3$TomatoLiveFragment$26();
                }
            });
        }

        public /* synthetic */ void lambda$reConnectCountOver$3$TomatoLiveFragment$26() {
            TomatoLiveFragment.this.tvInput.setText(R$string.fq_click_reconnect);
            TomatoLiveFragment.this.changePrivateMessageNetStatus(false);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onWebSocketAddressSuccess(LiveEntity liveEntity) {
        this.socketUrl = AppUtils.formatLiveSocketUrl(liveEntity.wsAddress, this.liveId, this.myUserInfoEntity.getUserId(), "2", liveEntity.f5841k);
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.reconnect(this.socketUrl);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ITomatoLiveView
    public void onWebSocketAddressFail() {
        addSocketTipMsg(R$string.fq_connect_fail);
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.resetCount();
            this.wsManager.setStatus(WsStatus.CONNECT_FAIL);
            this.isConnectingChatService = false;
            this.reConnectCountOver = true;
            TextView textView = this.tvInput;
            if (textView != null) {
                textView.setText(R$string.fq_click_reconnect);
            }
        }
        changePrivateMessageNetStatus(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSocketTipMsg(@StringRes int i) {
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(getString(i));
        chatEntity.setMsgType(7);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$xs77xG601qNd3prhjTo0eYyVMZg
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$addSocketTipMsg$24$TomatoLiveFragment(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$addSocketTipMsg$24$TomatoLiveFragment(ChatEntity chatEntity) {
        this.mLiveChatMsgView.addChatMsg(chatEntity);
    }

    private void stopSocket() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.stopService();
            this.wsManager = null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0425, code lost:
        if (r1.equals("1") != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x04a3, code lost:
        if (r1.equals("recommend") != false) goto L131;
     */
    @Override // com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBackThreadReceiveMessage(SocketMessageEvent socketMessageEvent) {
        char c;
        char c2;
        int i = 0;
        if (!AppUtils.isSocketEventSuc(socketMessageEvent.code)) {
            showToast(socketMessageEvent.message);
            String str = socketMessageEvent.code;
            int hashCode = str.hashCode();
            if (hashCode != 1445) {
                if (hashCode == 1477268995 && str.equals(ConstantUtils.GIFT_NEED_UPDATE)) {
                    c2 = 0;
                }
                c2 = 65535;
            } else {
                if (str.equals(ConstantUtils.BAN_MSG)) {
                    c2 = 1;
                }
                c2 = 65535;
            }
            if (c2 == 0) {
                if (!this.isGiftListUpdating) {
                    this.isGiftListUpdating = true;
                    ((TomatoLivePresenter) this.mPresenter).getGiftList();
                }
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ZlG_04c1YG5baA_zsyiVdaxzF24
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$25$TomatoLiveFragment();
                    }
                });
                return;
            } else if (c2 != 1) {
                return;
            } else {
                this.isNormalBan = true;
                this.banPostTimeLeft = socketMessageEvent.resultData.remainTime;
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$6F6rM8sccdkAcB9ZDIlGJb-CeJ8
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$26$TomatoLiveFragment();
                    }
                });
                return;
            }
        }
        final SocketMessageEvent.ResultData resultData = socketMessageEvent.resultData;
        if (resultData == null || this.mActivity.isFinishing() || this.mainHandler == null || this.workHandler == null) {
            return;
        }
        String str2 = socketMessageEvent.messageType;
        switch (str2.hashCode()) {
            case -1981791680:
                if (str2.equals(ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1603387347:
                if (str2.equals(ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1598856750:
                if (str2.equals(ConnectSocketParams.MESSAGE_BANPOSTALL_TYPE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1483460454:
                if (str2.equals(ConnectSocketParams.MESSAGE_CONVERT_PAID_ROOM)) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -1330790228:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_ACCEPT)) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case -1302490523:
                if (str2.equals(ConnectSocketParams.MESSAGE_CONSUME_NOTIFY_TYPE)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -1268961704:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE)) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -1256385881:
                if (str2.equals(ConnectSocketParams.MESSAGE_TOKEN_INVALID_NOTIFY_TYPE)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1244355024:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_LIANMAI_END_TYPE)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1242004544:
                if (str2.equals(ConnectSocketParams.MESSAGE_CHAT_RECEIPT_TYPE)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1052404422:
                if (str2.equals(ConnectSocketParams.MESSAGE_UPDATE_PAID_ROOM_PULL_STREAM_URL)) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case -1039689911:
                if (str2.equals(ConnectSocketParams.MESSAGE_NOTIFY_TYPE)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1033917736:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_LIANMAI_SUCCESS_TYPE)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -993690229:
                if (str2.equals(ConnectSocketParams.MESSAGE_PROP_SEND_TYPE)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -992867598:
                if (str2.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_BROADCAST_TYPE)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -941691210:
                if (str2.equals(ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -842142792:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_REFUSE)) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case -634778976:
                if (str2.equals(ConnectSocketParams.MESSAGE_FORBID_LIVE_TYPE)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -566357442:
                if (str2.equals(ConnectSocketParams.MESSAGE_CHAT_BUY_LIVE_TICKET_TYPE)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -535278809:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_START_TYPE)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -370196576:
                if (str2.equals(ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -355282718:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVE_DRAW_FINISHED)) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case -339185956:
                if (str2.equals(ConnectSocketParams.MESSAGE_BALANCE_TYPE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -337843889:
                if (str2.equals(ConnectSocketParams.MESSAGE_BAN_POST_TYPE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -236148015:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVECONTROL_TYPE)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -148968596:
                if (str2.equals(ConnectSocketParams.MESSAGE_TURNTABLE_STATUS_UPDATE_TYPE)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -21216891:
                if (str2.equals(ConnectSocketParams.MESSAGE_POSTINTERVAL_TYPE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3052376:
                if (str2.equals(ConnectSocketParams.MESSAGE_CHAT_TYPE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3172656:
                if (str2.equals(ConnectSocketParams.MESSAGE_GIFT_TYPE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 16548271:
                if (str2.equals(ConnectSocketParams.MESSAGE_USER_PRIVATE_MESSAGE)) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case 96667762:
                if (str2.equals(ConnectSocketParams.MESSAGE_ENTER_TYPE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 98509126:
                if (str2.equals(ConnectSocketParams.MESSAGE_GOOUT_TYPE)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 99368259:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_GOOUT_TYPE)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 102846135:
                if (str2.equals("leave")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 106691808:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_END_TYPE)) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 132753966:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_FIRST_KILL_TYPE)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 317295308:
                if (str2.equals(ConnectSocketParams.MESSAGE_USER_GRADE_TYPE)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 395254178:
                if (str2.equals(ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 441119852:
                if (str2.equals(ConnectSocketParams.MESSAGE_PUT_GIFT_BOX_TYPE)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 441614241:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_SHOW)) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 487782924:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_BANPOST_TYPE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 720694193:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE_COMPLETE)) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case 798249924:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVE_SETTING_TYPE)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 805483582:
                if (str2.equals(ConnectSocketParams.MESSAGE_START_INTIMATE_TASK)) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 1225787890:
                if (str2.equals(ConnectSocketParams.MESSAGE_LIVE_DRAW_START)) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1316826486:
                if (str2.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_BID_FAILED)) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case 1374344608:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_ASSIST_KING)) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1585377747:
                if (str2.equals(ConnectSocketParams.MESSAGE_PK_NOTIFY_FP_TYPE)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1680327801:
                if (str2.equals(ConnectSocketParams.MESSAGE_GIFT_TRUMPET_TYPE)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 2021199175:
                if (str2.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_NOTIFIED_TYPE)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (resultData.isQMTaskGift() && NumberUtils.string2int(resultData.giftNum) > 1) {
                    int string2int = NumberUtils.string2int(resultData.giftNum);
                    resultData.giftNum = "1";
                    while (i < string2int) {
                        dealGiftMsgFormSocket(resultData);
                        i++;
                    }
                    return;
                }
                dealGiftMsgFormSocket(resultData);
                return;
            case 1:
                dealEnterMsgFromSocket(resultData);
                return;
            case 2:
                if (resultData.isPrivateMsg()) {
                    resultData.sysNoticeType = ConnectSocketParams.MESSAGE_CHAT_TYPE;
                    dealPrivateMsgFromSocket(resultData);
                    return;
                }
                dealChatMsgFromSocket(resultData);
                return;
            case 3:
                dealUserBalanceMsgFromSocket(resultData);
                return;
            case 4:
                int string2int2 = NumberUtils.string2int(resultData.postIntervalTimes);
                if (string2int2 < 0) {
                    return;
                }
                this.postIntervalTimes = string2int2;
                return;
            case 5:
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$6NpejthXrh303EFCPhbsPxNVx1M
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$27$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case 6:
                dealSuperBanPostMsgFromSocket(resultData);
                return;
            case 7:
                dealBanPostMsgFromSocket(resultData);
                return;
            case '\b':
                dealBannedAllPostMsgFormSocket(resultData);
                return;
            case '\t':
            case '\n':
                dealKickOutMsgFromSocket(resultData);
                return;
            case 11:
                dealNotifyMsgFromSocket(resultData);
                return;
            case '\f':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$pn7wo3grIHldVjTFEQLyJCNBnKA
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$28$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case '\r':
                return;
            case 14:
                dealLiveSettingMsgFromSocket(resultData);
                return;
            case 15:
                startTokenDialogService();
                onFinishActivity();
                return;
            case 16:
                if (TextUtils.equals(resultData.userId, this.myUserInfoEntity.getUserId())) {
                    this.myUserInfoEntity.setExpGrade(resultData.afterGrade);
                    InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
                    if (inputTextMsgForAudienceDialog != null) {
                        inputTextMsgForAudienceDialog.setMyUserGrade(this.myUserInfoEntity.getExpGrade());
                    }
                }
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$rcxc6hVQdTUkYa0NMlSvNyD8LuI
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$29$TomatoLiveFragment();
                    }
                });
                dealExpGradeUpdate(resultData);
                return;
            case 17:
                if (resultData.isQMTaskGift() && NumberUtils.string2int(resultData.giftNum) > 1) {
                    int string2int3 = NumberUtils.string2int(resultData.giftNum);
                    resultData.giftNum = "1";
                    while (i < string2int3) {
                        dealGiftNoticeMsgFromSocket(resultData);
                        i++;
                    }
                    return;
                }
                dealGiftNoticeMsgFromSocket(resultData);
                return;
            case 18:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 19:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 20:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 21:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE;
                String str3 = resultData.type;
                switch (str3.hashCode()) {
                    case -1671569065:
                        if (str3.equals(ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN)) {
                            i = 3;
                            break;
                        }
                        i = -1;
                        break;
                    case -648591709:
                        if (str3.equals(ConnectSocketParams.RESULT_DATA_DAY_RANK_UP)) {
                            i = 2;
                            break;
                        }
                        i = -1;
                        break;
                    case 989204668:
                        break;
                    case 1525144764:
                        if (str3.equals(ConnectSocketParams.RESULT_DATA_TYPE_HITNOTIFY)) {
                            i = 1;
                            break;
                        }
                        i = -1;
                        break;
                    default:
                        i = -1;
                        break;
                }
                if (i == 0) {
                    dealSysNoticeMsgFromSocket(resultData);
                    return;
                } else if (i == 1) {
                    if (!AppUtils.isEnableTurntable()) {
                        return;
                    }
                    dealSysLuckMsgFromSocket(resultData);
                    return;
                } else if (i == 2) {
                    resultData.sysNoticeType = ConnectSocketParams.RESULT_DATA_DAY_RANK_UP;
                    dealAnchorInfoNoticeMsgFromSocket(resultData);
                    return;
                } else if (i != 3) {
                    return;
                } else {
                    resultData.sysNoticeType = ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN;
                    dealAnchorInfoNoticeMsgFromSocket(resultData);
                    return;
                }
            case 22:
                dealConsumeMsgFormSocket(resultData);
                return;
            case 23:
                dealGiftBoxMsgFromSocket(resultData);
                return;
            case 24:
                showToast(getString(R$string.fq_gift_box_toast_tips, resultData.presenterName, resultData.propNum, resultData.propName));
                return;
            case 25:
                dealGiftBoxMsg(resultData);
                return;
            case 26:
                dealPropMsgFormSocket(resultData);
                return;
            case 27:
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$vrB1YxInFuLW-mYcSU8z-6I50q0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$30$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case 28:
                if (!CacheUtils.isLocalCacheComponents(resultData.gameId)) {
                    return;
                }
                String str4 = resultData.type;
                int hashCode2 = str4.hashCode();
                if (hashCode2 == 49) {
                    break;
                } else {
                    if (hashCode2 == 50 && str4.equals("2")) {
                        i = 1;
                    }
                    i = -1;
                }
                if (i == 0) {
                    dealBroadcastMsgFromSocket(resultData);
                    return;
                } else if (i != 1) {
                    return;
                } else {
                    resultData.sysNoticeType = ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE_GAME;
                    if (!SPUtils.getInstance().getBoolean(ConstantUtils.NOTICE_GAME_KEY, true)) {
                        return;
                    }
                    dealGameNoticeMsgFromSocket(resultData);
                    return;
                }
            case 29:
                DBUtils.updatePrivateMsgDetail(resultData.senderId, resultData.messageId, resultData.status);
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ZxC9nRSr6C92FvhPtnTW43Q0fy8
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$31$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case 30:
                updateAnchorContribution(resultData);
                return;
            case 31:
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$mc-ZBTUMjjtQYc0t6w4x0tnyHRM
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$32$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case ' ':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$QMZ0jtH6KCzUqoqjvU6T7nvEAis
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$33$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case '!':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$kTH5yHaYTiK88XzQHA5kHG30loU
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$34$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case '\"':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$hdoi_eScjkqLGiXEgntgsKBFp0Y
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$35$TomatoLiveFragment();
                    }
                });
                return;
            case '#':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Ksaia3WYksOwcDLuIy6fRgj7e5o
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$36$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case '$':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$S5hTtYEia3HgEcagGclFbJlUXm4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$onBackThreadReceiveMessage$37$TomatoLiveFragment(resultData);
                    }
                });
                return;
            case '%':
                if (isLiveEnd()) {
                    return;
                }
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_PK_ASSIST_KING;
                dealGameNoticeMsgFromSocket(resultData);
                return;
            case '&':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.27
                    @Override // java.lang.Runnable
                    public void run() {
                        SocketMessageEvent.ResultData resultData2 = resultData;
                        String str5 = resultData2.joinNum;
                        String str6 = resultData2.content;
                        String str7 = resultData2.price;
                        String str8 = resultData2.icon;
                        long string2int4 = NumberUtils.string2int(resultData2.duration, 0) * 60;
                        TomatoLiveFragment.this.liveItemEntity.giftMarkId = resultData.markId;
                        TomatoLiveFragment.this.liveItemEntity.drawStatus = "1";
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        SocketMessageEvent.ResultData resultData3 = resultData;
                        tomatoLiveFragment.loadHdLotteryDrawInfo(resultData3.liveDrawRecordId, resultData3.prizeName, resultData3.prizeNum, resultData3.scope, resultData3.markId, str5, str6, str7, str8, string2int4, "1");
                    }
                });
                return;
            case '\'':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.28
                    @Override // java.lang.Runnable
                    public void run() {
                        TomatoLiveFragment.this.mLiveAdBannerBottomView.onLotteryEnd();
                        TomatoLiveFragment.this.hdLotteryDrawingDialog.onLotteryEnd();
                    }
                });
                return;
            case '(':
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_USER_PRIVATE_MESSAGE;
                dealPrivateMsgFromSocket(resultData);
                return;
            case ')':
                if (isLiveEnd()) {
                    return;
                }
                this.isPayLive = true;
                this.chargeType = resultData.chargeType;
                this.ticketPrice = resultData.chargePrice;
                LiveEntity liveEntity = this.liveListItemEntity;
                liveEntity.chargeType = this.chargeType;
                liveEntity.ticketPrice = this.ticketPrice;
                handlerMainPost(new RunnableC399329(resultData));
                return;
            case '*':
                ((TomatoLivePresenter) this.mPresenter).onTimerDelayAction(3L, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.30
                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onError(int i2, String str5) {
                    }

                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onSuccess(Object obj) {
                        EventBus.getDefault().post(new ListDataUpdateEvent(true));
                        TomatoLiveFragment.this.liveListItemEntity.pullStreamUrl = resultData.pullStreamUrl;
                        TomatoLiveFragment.this.updatePullStreamUrl();
                        TomatoLiveFragment.this.switchStream();
                    }
                });
                return;
            case '+':
                dealIntimateTaskFromSocket(resultData);
                return;
            case ',':
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.31
                    @Override // java.lang.Runnable
                    public void run() {
                        TomatoLiveFragment.this.mLiveAdBannerBottomView.dealIntimateTaskShowFromSocket(false, resultData);
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        if (tomatoLiveFragment.isDialogFragmentAdded(tomatoLiveFragment.qmTaskListUserDialog)) {
                            TomatoLiveFragment.this.qmTaskListUserDialog.onSendTaskListRequest();
                        } else if (TextUtils.equals(resultData.putUserId, UserInfoManager.getInstance().getUserId()) || TomatoLiveFragment.this.mLiveAdBannerBottomView.getTaskListSize() <= 1) {
                        } else {
                            TomatoLiveFragment.this.mLiveAdBannerBottomView.showRedPoint();
                        }
                    }
                });
                return;
            case '-':
            case '.':
            case '/':
                showToast(resultData.text);
                return;
            case '0':
                if (isDialogFragmentAdded(this.qmTaskListUserDialog)) {
                    this.qmTaskListUserDialog.updateTaskChargeList(resultData.intimateTaskChargeList);
                }
                this.mLiveAdBannerBottomView.dealIntimateTaskChargeFormSocket(resultData);
                return;
            case '1':
                if (isDialogFragmentAdded(this.qmTaskListUserDialog)) {
                    this.qmTaskListUserDialog.completeTaskCharge(resultData.taskId);
                }
                this.mLiveAdBannerBottomView.dealIntimateTaskChargeCompleteFromSocket(resultData);
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$25$TomatoLiveFragment() {
        dismissDialogFragment(this.giftBottomDialog);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$29$TomatoLiveFragment() {
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.updateUserGradeInfo(this.myUserInfoEntity.getExpGrade());
        }
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$31$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null || !privateMsgDialog.isAdded()) {
            return;
        }
        this.privateMsgDialog.changeMsgStatus(resultData.messageId, resultData.status);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$32$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (isLiveEnd()) {
            return;
        }
        adjustPkInfoViewLayout();
        PKInfoView pKInfoView = this.mPKInfoView;
        String str = this.liveId;
        AnchorEntity anchorEntity = this.anchorItemEntity;
        pKInfoView.showLMSuccessView(str, anchorEntity.nickname, anchorEntity.avatar, resultData.matcherUserId, resultData.matcherLiveId, resultData.matcherAvatar, resultData.matcherUserName, true);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$33$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (isLiveEnd()) {
            return;
        }
        this.linkMicPKType.set(ConstantUtils.PK_TYPE_PK_PROCESSING);
        this.mPKInfoView.onPKStart(NumberUtils.string2long(resultData.pkCountDownTime), NumberUtils.string2long(resultData.punishCountDownTime));
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$34$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (isLiveEnd()) {
            return;
        }
        this.mPKInfoView.initAssistData(resultData.anchorALiveId, resultData.anchorAFP, resultData.anchorAPopularity, resultData.anchorAAssists, resultData.anchorBLiveId, resultData.anchorBFP, resultData.anchorBPopularity, resultData.anchorBAssists);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$35$TomatoLiveFragment() {
        if (isLiveEnd()) {
            return;
        }
        dismissDialogFragment(this.pkRankDialog);
        stopLinkMicPk();
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$36$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (isLiveEnd()) {
            return;
        }
        this.mPKInfoView.dealFirstKill(resultData.liveId, resultData.avatar);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$37$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (isLiveEnd()) {
            return;
        }
        stopPKTimerDisposable();
        this.linkMicPKType.set(ConstantUtils.PK_TYPE_PK_ENDING);
        this.mPKInfoView.onPKEnd(resultData.anchorALiveId, resultData.anchorAFP, resultData.anchorAPopularity, resultData.anchorAAssists, resultData.anchorBLiveId, resultData.anchorBFP, resultData.anchorBPopularity, resultData.anchorBAssists);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$29 */
    /* loaded from: classes3.dex */
    public class RunnableC399329 implements Runnable {
        final /* synthetic */ SocketMessageEvent.ResultData val$resultData;

        RunnableC399329(SocketMessageEvent.ResultData resultData) {
            this.val$resultData = resultData;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TomatoLiveFragment.this.onFragmentInteractionListener != null) {
                ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).onTimerDelayAction(4L, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.29.1
                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onError(int i, String str) {
                    }

                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onSuccess(Object obj) {
                        TomatoLiveFragment.this.onFragmentInteractionListener.updateLiveRoomInfo();
                    }
                });
            }
            if (!this.val$resultData.isNeedBuyTicket()) {
                TomatoLiveFragment.this.showPayLiveTips();
                return;
            }
            TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
            tomatoLiveFragment.payLiveTipsDialog = PayLiveTipsDialog.newInstance(this.val$resultData.freeWatchTime, AppUtils.formatMoneyUnitStr(((BaseFragment) tomatoLiveFragment).mContext, TomatoLiveFragment.this.ticketPrice, false), new SimplePayLiveCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.29.2
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
                public void onPayEnterClickListener(final View view) {
                    super.onPayEnterClickListener(view);
                    TomatoLiveFragment.this.isPayLiveTipsDialog = false;
                    if (!TomatoLiveFragment.this.isConsumptionPermissionUser()) {
                        AppUtils.onLoginListener(((BaseFragment) TomatoLiveFragment.this).mContext);
                        return;
                    }
                    view.setEnabled(false);
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).onBuyLiveTicket(TomatoLiveFragment.this.liveId, new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.29.2.1
                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onSuccess(MyAccountEntity myAccountEntity) {
                            view.setEnabled(true);
                            DBUtils.savePayLiveInfo(TomatoLiveFragment.this.liveId, TomatoLiveFragment.this.liveCount, String.valueOf(System.currentTimeMillis()));
                            TomatoLiveFragment.this.showToast(R$string.fq_pay_live_ticket_verification_toast);
                            TomatoLiveFragment.this.updateUserBalance(myAccountEntity.getAccountBalance());
                            TomatoLiveFragment.this.isFirstGetMyBalanceGift = false;
                            TomatoLiveFragment.this.showPayLiveTips();
                            TomatoLiveFragment.this.payLiveTipsDialogOnRelease();
                        }

                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onError(int i, String str) {
                            view.setEnabled(true);
                            if (i == 300006) {
                                AppUtils.onRechargeListener(((BaseFragment) TomatoLiveFragment.this).mContext);
                            }
                        }
                    });
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
                public void onPayExitClickListener() {
                    super.onPayExitClickListener();
                    TomatoLiveFragment.this.isPayLiveTipsDialog = false;
                    TomatoLiveFragment.this.onFinishActivity();
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
                public void onPayCancelListener() {
                    super.onPayCancelListener();
                    TomatoLiveFragment.this.isPayLiveTipsDialog = false;
                }
            });
            TomatoLiveFragment.this.payLiveTipsDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
            TomatoLiveFragment.this.isPayLiveTipsDialog = true;
        }
    }

    private void dealIntimateTaskFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.intimateTaskQueue == null) {
                this.intimateTaskQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.intimateTaskQueue.size() == 9999) {
                this.intimateTaskQueue.poll();
            }
            this.intimateTaskQueue.offer(resultData);
            sendWorkHandlerEmptyMessage(ConstantUtils.INTIMATE_TASK_NOTICE);
        }
    }

    private void dealPrivateMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.privateMsgQueue == null) {
                this.privateMsgQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.privateMsgQueue.size() == 9999) {
                this.privateMsgQueue.poll();
            }
            this.privateMsgQueue.offer(resultData);
            sendWorkHandlerEmptyMessage(10006);
        }
    }

    private void dealGameNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.gameNoticeQueue == null) {
                this.gameNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.gameNoticeQueue.size() == 9999) {
                this.gameNoticeQueue.poll();
            }
            this.gameNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dealLotteryMsgFromSocket */
    public void lambda$onBackThreadReceiveMessage$30$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (AppUtils.isEnableTurntable() && !isLuxuryBoomStatus()) {
            onLotteryBoomOpen(resultData.propUrl, resultData.boomMultiple, resultData.boomRemainTime, resultData.boomTotalTime, resultData.turntableType);
            LotteryDialog lotteryDialog = this.mLotteryDialog;
            if (lotteryDialog == null || !lotteryDialog.isResumed()) {
                return;
            }
            this.mLotteryDialog.executeBoom(resultData.turntableType == 20);
        }
    }

    private void dealSysLuckMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.luckNoticeQueue == null) {
                this.luckNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.luckNoticeQueue.size() == 9999) {
                this.luckNoticeQueue.poll();
            }
            this.luckNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessageDelayed(ConstantUtils.SYS_LUCK_HIT, 4000L);
    }

    private void dealGiftBoxMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$3__1pqNtXMPWBN77hsgMX-mUrpo
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealGiftBoxMsgFromSocket$38$TomatoLiveFragment(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$dealGiftBoxMsgFromSocket$38$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        GiftBoxEntity giftBoxEntity = new GiftBoxEntity();
        giftBoxEntity.expirationTime = NumberUtils.string2long(resultData.expirationTime);
        giftBoxEntity.openTime = NumberUtils.string2long(resultData.openTime);
        giftBoxEntity.giftBoxUniqueCode = resultData.giftBoxUniqueCode;
        giftBoxEntity.presenterAvatar = resultData.presenterAvatar;
        giftBoxEntity.presenterId = resultData.presenterId;
        giftBoxEntity.presenterName = resultData.presenterName;
        giftBoxEntity.userId = this.myUserInfoEntity.getUserId();
        giftBoxEntity.liveId = this.liveId;
        this.mGiftBoxView.addOneBox(giftBoxEntity);
    }

    private void dealGiftNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.giftNoticeQueue == null) {
                this.giftNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.giftNoticeQueue.size() == 9999) {
                this.giftNoticeQueue.poll();
            }
            this.giftNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessage(10004);
    }

    private void dealAnchorInfoNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.anchorInfoNoticeQueue == null) {
                this.anchorInfoNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.anchorInfoNoticeQueue.size() == 9999) {
                this.anchorInfoNoticeQueue.poll();
            }
            this.anchorInfoNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessage(10011);
    }

    private void dealSysNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (TomatoLiveFragment.class) {
            if (this.sysNoticeQueue == null) {
                this.sysNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.sysNoticeQueue.size() == 9999) {
                this.sysNoticeQueue.poll();
            }
            this.sysNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessage(10005);
    }

    private void dealConsumeMsgFormSocket(final SocketMessageEvent.ResultData resultData) {
        char c;
        updateAnchorContribution(resultData);
        String str = resultData.type;
        int hashCode = str.hashCode();
        if (hashCode == -903754697) {
            if (str.equals(ConstantUtils.SOCKET_CONSUME_TYPE_RENEW_NOBILITY)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != -736437516) {
            if (hashCode == 1524196987 && str.equals(ConstantUtils.SOCKET_CONSUME_TYPE_OPEN_GUARD)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("openNobility")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$oUi_Tu0ly5JAjynjSGSmqaZw6lQ
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealConsumeMsgFormSocket$39$TomatoLiveFragment(resultData);
                }
            });
            addBigAnim(resultData, ConstantUtils.BIG_ANIM_OPEN_GUARD_TYPE);
            this.liveAnimationView.loadGuardOpenAnimation(resultData);
        } else if (c != 1) {
            if (c != 2) {
                return;
            }
            if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId)) {
                ((TomatoLivePresenter) this.mPresenter).getTrumpetStatus();
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$yemdds3iMC5bSSgYH4KFiaJ3_GI
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealConsumeMsgFormSocket$42$TomatoLiveFragment();
                    }
                });
                this.myNobilityType = resultData.nobilityType;
                UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
            }
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$lU89njnWurWrgk-E1KNt8hsDRuE
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealConsumeMsgFormSocket$43$TomatoLiveFragment(resultData);
                }
            });
        } else {
            if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId)) {
                ((TomatoLivePresenter) this.mPresenter).getTrumpetStatus();
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$DhBvwZjfrQS4uUs7NHKX3d4LEFg
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealConsumeMsgFormSocket$40$TomatoLiveFragment();
                    }
                });
                this.myNobilityType = resultData.nobilityType;
                UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
            }
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$0cxoddLwQ69u8_TFTc3wx3q2-d8
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealConsumeMsgFormSocket$41$TomatoLiveFragment(resultData);
                }
            });
            if (resultData.isBigAnimRegionShowNotify() && AppUtils.isCanShowOpenNobilityAnim(resultData.nobilityType)) {
                addBigAnim(resultData, ConstantUtils.BIG_ANIM_OPEN_NOBILITY_TYPE);
            }
            if (!resultData.isLeftAnimRegionShowNotify()) {
                return;
            }
            this.liveAnimationView.loadNobilityOpenAnimation(resultData);
        }
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$39$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.updateOpenGuardCount(resultData.anchorGuardCount);
        }
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgType(12);
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setExpGrade(resultData.expGrade);
        chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        chatEntity.setNobilityType(resultData.nobilityType);
        chatEntity.setMarkUrls(resultData.markUrls);
        chatEntity.setMsgText(AppUtils.getGuardTypeStr(this.mContext, resultData.guardType));
        this.mLiveChatMsgView.addChatMsg(chatEntity);
        this.mLiveChatMsgView.updateGuardTypeItemDataByUid(resultData.userId, NumberUtils.string2int(resultData.guardType));
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
        if (TextUtils.equals(resultData.userId, this.myUserInfoEntity.getUserId())) {
            GuardItemEntity guardItemEntity = this.guardItemEntity;
            guardItemEntity.userGuardType = resultData.guardType;
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog == null) {
                return;
            }
            inputTextMsgForAudienceDialog.setMyGuardType(NumberUtils.string2int(guardItemEntity.userGuardType));
        }
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$40$TomatoLiveFragment() {
        this.giftBottomDialog.updateBackPackCount();
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$41$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (resultData.isChatRegionShowNotify()) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(14);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(resultData.expGrade);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setMarkUrls(resultData.markUrls);
            chatEntity.setMsgText(getString(R$string.fq_nobility_msg_open_tips, AppUtils.getNobilityBadgeName(this.mContext, resultData.nobilityType)));
            this.mLiveChatMsgView.addChatMsg(chatEntity);
        }
        this.mLiveChatMsgView.updateNobilityTypeItemDataByUid(resultData.userId, resultData.nobilityType);
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$42$TomatoLiveFragment() {
        this.giftBottomDialog.updateBackPackCount();
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$43$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (resultData.isChatRegionShowNotify()) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(14);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(resultData.expGrade);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setMarkUrls(resultData.markUrls);
            chatEntity.setMsgText(getString(R$string.fq_nobility_msg_renewal_tips, AppUtils.getNobilityBadgeName(this.mContext, resultData.nobilityType)));
            this.mLiveChatMsgView.addChatMsg(chatEntity);
        }
        this.mLiveChatMsgView.updateNobilityTypeItemDataByUid(resultData.userId, resultData.nobilityType);
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
    }

    private void dealMyPropMsgFormSocket(SocketMessageEvent.ResultData resultData) {
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.bigAnimType = ConstantUtils.BIG_ANIM_PROP_TYPE;
        giftItemEntity.duration = NumberUtils.string2int(resultData.duration);
        giftItemEntity.activeTime = NumberUtils.string2int(resultData.activeTime);
        GiftIndexEntity giftIndexEntity = this.myPropIndexMap.get(resultData.propId);
        if (giftIndexEntity == null) {
            giftIndexEntity = new GiftIndexEntity();
            giftIndexEntity.sendIndex++;
            this.isContinueCombo = false;
            this.myPropIndexMap.put(resultData.propId, giftIndexEntity);
        } else if (giftIndexEntity.countDownStartTime == 0) {
            giftIndexEntity.sendIndex++;
            this.isContinueCombo = false;
        } else if (System.currentTimeMillis() - giftIndexEntity.countDownStartTime > NumberUtils.formatMillisecond(giftItemEntity.activeTime)) {
            giftIndexEntity.sendIndex = 1;
            giftIndexEntity.countDownStartTime = 0L;
            this.isContinueCombo = false;
        } else {
            giftIndexEntity.sendIndex++;
            this.isContinueCombo = true;
            giftIndexEntity.countDownStartTime = 0L;
        }
        giftItemEntity.sendUserName = resultData.userName;
        giftItemEntity.userId = resultData.userId;
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.role = resultData.role;
        giftItemEntity.userRole = resultData.userRole;
        giftItemEntity.sex = resultData.sex;
        giftItemEntity.weekStar = resultData.isWeekStar;
        giftItemEntity.expGrade = AppUtils.formatExpGrade(resultData.expGrade);
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.appId = resultData.appId;
        giftItemEntity.openId = resultData.openId;
        giftItemEntity.animalUrl = resultData.animalUrl;
        giftItemEntity.markId = resultData.propId;
        giftItemEntity.name = resultData.propName;
        giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
        giftItemEntity.effect_type = NumberUtils.string2int(resultData.animalType);
        giftItemEntity.animalType = giftItemEntity.effect_type;
        giftItemEntity.imgurl = resultData.coverUrl;
        giftItemEntity.giftNum = "1";
        giftItemEntity.marks = resultData.markUrls;
        if (giftItemEntity.isBigProp()) {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Y9ogEn6p_VRyC4KQAM70YNDmS0k
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealMyPropMsgFormSocket$44$TomatoLiveFragment();
                }
            });
            WsManager wsManager = this.wsManager;
            if (wsManager != null) {
                wsManager.addLocalAnim(giftItemEntity);
            }
        }
        playMySelfAnimGift(giftItemEntity);
        showSelfGiftMsgOnChatList(giftItemEntity);
        LogEventUtils.uploadSendProp(this.anchorItemEntity, giftItemEntity, resultData.giftNum, this.liveId, this.myUserInfoEntity.expGrade);
    }

    private void dealReceivePropMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        long currentTimeMillis = System.currentTimeMillis();
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.bigAnimType = ConstantUtils.BIG_ANIM_PROP_TYPE;
        giftItemEntity.animalUrl = resultData.animalUrl;
        giftItemEntity.duration = NumberUtils.string2int(resultData.duration);
        giftItemEntity.activeTime = NumberUtils.string2int(resultData.activeTime);
        if (!this.receivePropMap.containsKey(resultData.userId)) {
            HashMap hashMap = new HashMap(8);
            GiftIndexEntity giftIndexEntity = new GiftIndexEntity();
            giftIndexEntity.createTime = currentTimeMillis;
            giftIndexEntity.sendIndex = 1;
            hashMap.put(resultData.propId, giftIndexEntity);
            this.receivePropMap.put(resultData.userId, hashMap);
            giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
        } else {
            Map<String, GiftIndexEntity> map = this.receivePropMap.get(resultData.userId);
            GiftIndexEntity giftIndexEntity2 = map.get(resultData.propId);
            if (giftIndexEntity2 == null) {
                giftIndexEntity2 = new GiftIndexEntity();
                giftIndexEntity2.createTime = currentTimeMillis;
                giftIndexEntity2.sendIndex = 1;
                map.put(resultData.propId, giftIndexEntity2);
            } else {
                if (currentTimeMillis - giftIndexEntity2.createTime < NumberUtils.formatMillisecond(giftItemEntity.activeTime + giftItemEntity.duration)) {
                    giftIndexEntity2.sendIndex++;
                } else {
                    giftIndexEntity2.sendIndex = 1;
                }
                giftIndexEntity2.createTime = currentTimeMillis;
            }
            giftItemEntity.sendIndex = giftIndexEntity2.sendIndex;
        }
        giftItemEntity.sendUserName = resultData.userName;
        giftItemEntity.userId = resultData.userId;
        giftItemEntity.role = resultData.role;
        giftItemEntity.userRole = resultData.userRole;
        giftItemEntity.sex = resultData.sex;
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.expGrade = AppUtils.formatExpGrade(resultData.expGrade);
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.effect_type = NumberUtils.string2int(resultData.animalType);
        giftItemEntity.animalType = giftItemEntity.effect_type;
        giftItemEntity.imgurl = resultData.coverUrl;
        String str = resultData.propName;
        giftItemEntity.name = str;
        String str2 = resultData.propId;
        giftItemEntity.markId = str2;
        resultData.giftName = str;
        resultData.markId = str2;
        giftItemEntity.giftNum = "1";
        giftItemEntity.marks = resultData.markUrls;
        playReceiveAnimGift(giftItemEntity);
        if (giftItemEntity.isBigProp()) {
            WsManager wsManager = this.wsManager;
            if (wsManager != null) {
                wsManager.addReceiveBigAnim(giftItemEntity);
            }
            showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringWithIndex(giftItemEntity), 1);
        } else {
            int i = giftItemEntity.sendIndex;
            if (i == 1) {
                showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringNoIndex(giftItemEntity), 1);
            } else if (i != 0 && i % 10 == 0) {
                showReceiveMsgOnChatList(resultData, AppUtils.appendGiftStringWithIndex(giftItemEntity), 1);
            }
        }
        wsManagerNotifyAnim();
    }

    private void dealLiveSettingMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        String str = resultData.changeType;
        if (((str.hashCode() == 1559674578 && str.equals(ConnectSocketParams.MESSAGE_SPEAKLEVEL_TYPE)) ? (char) 0 : (char) 65535) != 0) {
            return;
        }
        this.speakLevel = resultData.changeValue;
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(getString(R$string.fq_speak_level_tip_for_socket, this.speakLevel));
        chatEntity.setExpGrade(this.speakLevel);
        chatEntity.setMsgType(11);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$c3_HzRuuwCoP-TMIyRME0M8OUNM
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealLiveSettingMsgFromSocket$45$TomatoLiveFragment(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$dealLiveSettingMsgFromSocket$45$TomatoLiveFragment(ChatEntity chatEntity) {
        this.mLiveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealBroadcastMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(resultData.content);
        chatEntity.setPropId(resultData.gameId);
        chatEntity.setMsgType(16);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$-BxdyvmiSf-EJ4RpTyQ8CMJikRA
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealBroadcastMsgFromSocket$46$TomatoLiveFragment(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$dealBroadcastMsgFromSocket$46$TomatoLiveFragment(ChatEntity chatEntity) {
        this.mLiveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealKickOutMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(this.anchorId, resultData.userId)) {
            resultData.userName = getString(R$string.fq_anchor);
        }
        showReceiveMsgOnChatList(resultData, resultData.userName, 9);
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.targetId)) {
            startKickDialogService(resultData.tipsText);
            onFinishActivity();
        }
    }

    private void dealGiftMsgFormSocket(final SocketMessageEvent.ResultData resultData) {
        if (!resultData.isScoreGift()) {
            updateAnchorContribution(resultData);
        }
        UserEntity userEntity = this.myUserInfoEntity;
        if (userEntity != null && TextUtils.equals(userEntity.getUserId(), resultData.userId)) {
            playMySelfAnimOnMainThread(resultData);
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.32
                @Override // java.lang.Runnable
                public void run() {
                    if (!TomatoLiveFragment.this.liveItemEntity.isHdLotterySuccessToast(resultData.markId) || TomatoLiveFragment.this.hdLotteryDrawingDialog == null) {
                        return;
                    }
                    TomatoLiveFragment.this.hdLotteryDrawingDialog.updatePartCount(NumberUtils.string2int(resultData.giftNum, 1));
                }
            });
            return;
        }
        playReceiveAnimOnMainThread(resultData);
    }

    private void dealPropMsgFormSocket(SocketMessageEvent.ResultData resultData) {
        if (resultData.isPriceProps()) {
            updateAnchorContribution(resultData);
        }
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId)) {
            dealMyPropMsgFormSocket(resultData);
        } else {
            dealReceivePropMsgFromSocket(resultData);
        }
    }

    private void dealEnterMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        this.isConnectingChatService = false;
        this.isSocketClose = false;
        this.isSocketError = false;
        if (resultData.isEnterHideBoolean()) {
            if (!TextUtils.equals(resultData.userId, this.myUserInfoEntity.getUserId())) {
                return;
            }
            showToast(R$string.fq_hide_enter_live);
            this.myNobilityType = resultData.nobilityType;
            UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
            return;
        }
        synchronized (TomatoLiveFragment.class) {
            if (this.enterMsgQueue == null) {
                this.enterMsgQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.enterMsgQueue.size() == 9999) {
                this.enterMsgQueue.poll();
            }
            this.enterMsgQueue.offer(resultData);
            sendWorkHandlerEmptyMessage(10002);
        }
        if (resultData.isEnterGuardType() || AppUtils.hasCar(resultData.carId)) {
            synchronized (TomatoLiveFragment.class) {
                if (this.guardEnterMsgQueue == null) {
                    this.guardEnterMsgQueue = new ConcurrentLinkedQueue<>();
                }
                if (this.guardEnterMsgQueue.size() == 9999) {
                    this.guardEnterMsgQueue.poll();
                }
                this.guardEnterMsgQueue.offer(resultData);
                sendWorkHandlerEmptyMessage(10003);
            }
        }
        if (!resultData.isHighNobility()) {
            return;
        }
        if (!TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId) && !resultData.isPlayNobilityEnterAnim()) {
            return;
        }
        this.liveAnimationView.loadNobilityEnterAnimation(resultData);
    }

    private void dealChatMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        if (this.shieldedList.contains(resultData.userId)) {
            return;
        }
        this.chatContent = resultData.content;
        if (this.isTranOpen) {
            TranslationUtils.translationFromText(this.chatContent, new TranslationUtils.OnTransListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$JBRv7TmdCibj5Fpa6x_wslaEsi4
                @Override // com.tomatolive.library.utils.TranslationUtils.OnTransListener
                public final void onSuc(String str) {
                    TomatoLiveFragment.this.lambda$dealChatMsgFromSocket$47$TomatoLiveFragment(resultData, str);
                }
            });
        } else {
            showReceiveMsgOnChatList(resultData, this.chatContent, 3);
        }
    }

    public /* synthetic */ void lambda$dealChatMsgFromSocket$47$TomatoLiveFragment(SocketMessageEvent.ResultData resultData, String str) {
        showReceiveMsgOnChatList(resultData, str, 3);
    }

    private void dealUserBalanceMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$b2dKcKTghX17nY39jGb5mCTMkzQ
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealUserBalanceMsgFromSocket$48$TomatoLiveFragment(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$dealUserBalanceMsgFromSocket$48$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        updateUserBalance(resultData.getAccountBalance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dealLiveControlMsgFromSocket */
    public void lambda$onBackThreadReceiveMessage$28$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog;
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog2;
        boolean isManager = resultData.isManager();
        if (isManager) {
            showReceiveMsgOnChatList(resultData, resultData.userName, 6);
            if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.targetId)) {
                this.myUserInfoEntity.setRole("3");
                if (this.isSuperBan) {
                    InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog3 = this.mInputTextMsgDialog;
                    if (inputTextMsgForAudienceDialog3 != null) {
                        inputTextMsgForAudienceDialog3.setBandPostBySuperManager();
                    }
                } else if (this.isNormalBan) {
                    InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog4 = this.mInputTextMsgDialog;
                    if (inputTextMsgForAudienceDialog4 != null) {
                        inputTextMsgForAudienceDialog4.setBandOnePost(DateUtils.getClearTime(this.banPostTimeLeft));
                    }
                } else if (this.isAllBan && (inputTextMsgForAudienceDialog2 = this.mInputTextMsgDialog) != null) {
                    inputTextMsgForAudienceDialog2.cancelBandPost();
                }
            }
        } else if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.targetId)) {
            this.myUserInfoEntity.setRole("2");
            if (this.isAllBan && (inputTextMsgForAudienceDialog = this.mInputTextMsgDialog) != null) {
                inputTextMsgForAudienceDialog.setBanedAllPost();
            }
        }
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog5 = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog5 != null) {
            inputTextMsgForAudienceDialog5.setMyRole(this.myUserInfoEntity.getRole());
        }
        String str = isManager ? "3" : "2";
        this.mLiveChatMsgView.updateRoleItemDataByUid(resultData.targetId, str);
        this.mLivePusherInfoView.sortUserList(resultData.targetId, null, null, -1, str);
    }

    private void dealNotifyMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        String str = resultData.type;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        final String str2 = resultData.typeMsg;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 50) {
            if (hashCode == 1568 && str.equals("11")) {
                c = 1;
            }
        } else if (str.equals("2")) {
            c = 0;
        }
        if (c == 0) {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$CqB-tKiD9VHz7BI8CjuOzXAJplQ
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealNotifyMsgFromSocket$49$TomatoLiveFragment(str2);
                }
            });
        } else if (c != 1) {
        } else {
            this.isBanGroup = true;
        }
    }

    public /* synthetic */ void lambda$dealNotifyMsgFromSocket$49$TomatoLiveFragment(String str) {
        startHideTitleTimer(str);
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgType(8);
        chatEntity.setMsgText(str);
        this.mLiveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealBannedAllPostMsgFormSocket(SocketMessageEvent.ResultData resultData) {
        this.isAllBan = resultData.isBanAll();
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$1UhaNVOGh0NRD6Ps8zo-l7jtL7w
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealBannedAllPostMsgFormSocket$50$TomatoLiveFragment();
            }
        });
        showReceiveMsgOnChatList(resultData, getString(this.isAllBan ? R$string.fq_text_input_banned_hint : R$string.fq_anchor_cancel_banned), 5);
    }

    public /* synthetic */ void lambda$dealBannedAllPostMsgFormSocket$50$TomatoLiveFragment() {
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog;
        if (this.isAllBan) {
            if (!AppUtils.isAudience(this.myUserInfoEntity.getRole()) || (inputTextMsgForAudienceDialog = this.mInputTextMsgDialog) == null) {
                return;
            }
            inputTextMsgForAudienceDialog.setBanedAllPost();
        } else if (this.isSuperBan) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog2 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog2 == null) {
                return;
            }
            inputTextMsgForAudienceDialog2.setBandPostBySuperManager();
        } else if (this.isNormalBan) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog3 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog3 == null) {
                return;
            }
            inputTextMsgForAudienceDialog3.setBandOnePost(DateUtils.getClearTime(this.banPostTimeLeft));
        } else {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog4 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog4 == null) {
                return;
            }
            inputTextMsgForAudienceDialog4.cancelBandPost();
        }
    }

    private void dealBanPostMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        final boolean isSomeoneBanPost = resultData.isSomeoneBanPost();
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.targetId)) {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Tnm3Tlc39zqqTuIJWWi4KToJ0lg
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealBanPostMsgFromSocket$51$TomatoLiveFragment(isSomeoneBanPost, resultData);
                }
            });
        }
        if (TextUtils.equals(getString(R$string.fq_system), resultData.userName) || TextUtils.equals(this.anchorId, resultData.userId)) {
            resultData.userName = getString(R$string.fq_anchor);
        }
        showReceiveMsgOnChatList(resultData, resultData.userName, 4);
    }

    public /* synthetic */ void lambda$dealBanPostMsgFromSocket$51$TomatoLiveFragment(boolean z, SocketMessageEvent.ResultData resultData) {
        if (z) {
            executeMyNormalBan(resultData.duration);
        } else {
            clearMyNormalBan();
        }
    }

    private void dealSuperBanPostMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.targetId)) {
            this.isSuperBan = true;
            if (!this.isAllBan && this.mInputTextMsgDialog != null) {
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ZQHFUwZ-MgTLpMVOao-VqQDXrX4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealSuperBanPostMsgFromSocket$52$TomatoLiveFragment();
                    }
                });
            }
        }
        showReceiveMsgOnChatList(resultData, resultData.userName, 4);
    }

    public /* synthetic */ void lambda$dealSuperBanPostMsgFromSocket$52$TomatoLiveFragment() {
        this.mInputTextMsgDialog.setBandPostBySuperManager();
    }

    private void executeMyNormalBan(String str) {
        this.isNormalBan = true;
        this.banPostTimeLeft = str;
        if (this.mInputTextMsgDialog != null) {
            if (!this.isAllBan && !this.isSuperBan) {
                this.mInputTextMsgDialog.setBandOnePost(DateUtils.getClearTime(this.banPostTimeLeft));
            }
            dismissInputMsgDialog();
        }
    }

    private void clearMyNormalBan() {
        this.isNormalBan = false;
        if (this.isAllBan && AppUtils.isAudience(this.myUserInfoEntity.getRole())) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog == null) {
                return;
            }
            inputTextMsgForAudienceDialog.setBanedAllPost();
        } else if (this.isSuperBan) {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog2 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog2 == null) {
                return;
            }
            inputTextMsgForAudienceDialog2.setBandPostBySuperManager();
        } else {
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog3 = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog3 == null) {
                return;
            }
            inputTextMsgForAudienceDialog3.cancelBandPost();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dealLeaveMsgFromSocket */
    public void lambda$onBackThreadReceiveMessage$27$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (AppUtils.isAnchor(resultData.role)) {
            this.isAutoGiftDialogFromWeekStar = false;
            LiveEndEntity liveEndEntity = resultData.lastLiveData;
            if (liveEndEntity == null) {
                liveEndEntity = new LiveEndEntity();
            }
            this.lastLiveEndEntity = liveEndEntity;
            LiveEndEntity liveEndEntity2 = this.lastLiveEndEntity;
            String str = liveEndEntity2.appId;
            String str2 = liveEndEntity2.openId;
            AnchorEntity anchorEntity = this.anchorItemEntity;
            liveEndEntity2.userId = anchorEntity.userId;
            liveEndEntity2.nickname = anchorEntity.nickname;
            liveEndEntity2.avatar = anchorEntity.avatar;
            liveEndEntity2.expGrade = anchorEntity.expGrade;
            liveEndEntity2.liveId = anchorEntity.liveId;
            liveEndEntity2.sex = anchorEntity.sex;
            liveEndEntity2.appId = str;
            liveEndEntity2.openId = str2;
            clearQMNoticeAnimView();
            ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.intimateTaskQueue;
            if (concurrentLinkedQueue != null) {
                concurrentLinkedQueue.clear();
            }
            goToEnd();
            if (isPayLiveTicket() && isLiving()) {
                payLiveTipsDialogOnRelease();
                if (DBUtils.isPayLiveValidState(this.liveId, this.liveCount)) {
                    showPayLiveEvaluationDialog();
                } else {
                    dismissDialogFragment(this.liveEndEvaluationDialog);
                }
                DBUtils.deletePayLiveInfo(this.liveId, this.liveCount);
            }
            watchRecordReport();
            this.livingStartTime = 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserCard(UserEntity userEntity) {
        if (TextUtils.equals(userEntity.getUserId(), this.myUserInfoEntity.getUserId())) {
            showUserAvatarDialog(userEntity, false);
        } else if (TextUtils.equals(userEntity.getUserId(), this.anchorId)) {
            if (this.isStartGetAnchorInfo) {
                return;
            }
            ((TomatoLivePresenter) this.mPresenter).getAnchorInfo(userEntity.getUserId());
            this.isStartGetAnchorInfo = true;
        } else if (userEntity.isRankHideBoolean()) {
            NobilityOpenTipsDialog.newInstance(13, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Mw2EzMkl2MAkj0YXjqKlhKKLTI4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TomatoLiveFragment.this.lambda$showUserCard$53$TomatoLiveFragment(view);
                }
            }).show(getChildFragmentManager());
        } else {
            showUserAvatarDialog(userEntity, true);
        }
    }

    public /* synthetic */ void lambda$showUserCard$53$TomatoLiveFragment(View view) {
        toNobilityOpenActivity();
    }

    private void showUserAvatarDialog(UserEntity userEntity, boolean z) {
        String avatar = userEntity.getAvatar();
        String name = TextUtils.isEmpty(userEntity.getName()) ? userEntity.nickname : userEntity.getName();
        String userId = userEntity.getUserId();
        String sex = userEntity.getSex();
        String signature = userEntity.getSignature();
        String expGrade = userEntity.getExpGrade();
        int guardType = userEntity.getGuardType();
        String role = userEntity.getRole();
        int nobilityType = userEntity.getNobilityType();
        boolean contains = this.shieldedList.contains(userId);
        boolean isHouseManager = AppUtils.isHouseManager(role);
        boolean isYearGuard = AppUtils.isYearGuard(guardType);
        boolean z2 = (!isHouseManager || !AppUtils.isAudience(this.myUserInfoEntity.getRole())) ? z : false;
        if (AppUtils.isHouseSuperManager(role)) {
            this.userSuperAvatarDialog = UserSuperAvatarDialog.newInstance(userId, avatar, name, sex, signature, expGrade, guardType);
            this.userSuperAvatarDialog.show(getChildFragmentManager());
        } else if (AppUtils.isNobilityUser(nobilityType)) {
            boolean z3 = z2;
            this.onUserCardCallback = new UserCardCallback(userId, name, 1, isHouseManager, contains, isYearGuard, AppUtils.cannotBannedNobility(nobilityType, this.nobilityTypeThresholdForHasPreventBanned));
            if (this.userNobilityAvatarDialog == null) {
                this.userNobilityAvatarDialog = UserNobilityAvatarDialog.newInstance(userEntity, z3, this.onUserCardCallback);
                this.userNobilityAvatarDialog.show(getChildFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z3);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
            this.userNobilityAvatarDialog.setArguments(this.bundleArgs);
            this.userNobilityAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.userNobilityAvatarDialog);
        } else {
            boolean z4 = z2;
            if (AppUtils.isGuardUser(guardType)) {
                this.onUserCardCallback = new UserCardCallback(userId, name, 2, isHouseManager, contains, isYearGuard, false);
                if (this.userGuardAvatarDialog == null) {
                    this.userGuardAvatarDialog = UserGuardAvatarDialog.newInstance(userEntity, z4, this.onUserCardCallback);
                    this.userGuardAvatarDialog.show(getChildFragmentManager());
                    return;
                }
                this.bundleArgs = new Bundle();
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z4);
                this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
                this.userGuardAvatarDialog.setArguments(this.bundleArgs);
                this.userGuardAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
                showDialogFragment(this.userGuardAvatarDialog);
                return;
            }
            this.onUserCardCallback = new UserCardCallback(userId, name, 3, isHouseManager, contains, isYearGuard, false);
            if (this.userAvatarDialog == null) {
                this.onUserCardCallback = new UserCardCallback(userId, name, 3, isHouseManager, contains, isYearGuard, false);
                this.userAvatarDialog = UserNormalAvatarDialog.newInstance(userEntity, z4, this.onUserCardCallback);
                this.userAvatarDialog.show(getChildFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z4);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
            this.userAvatarDialog.setArguments(this.bundleArgs);
            this.userAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.userAvatarDialog);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void userAvatarDialogManager(final int i, final boolean z, final boolean z2, boolean z3, boolean z4, final String str, final String str2) {
        char c;
        String role = this.myUserInfoEntity.getRole();
        switch (role.hashCode()) {
            case 49:
                if (role.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (role.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (role.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
            default:
                c = 65535;
                break;
            case 53:
                if (role.equals("5")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
        }
        if (c == 0) {
            ((TomatoLivePresenter) this.mPresenter).showUserManageMenu(this.liveId, str2, new ResultCallBack<UserEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.33
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str3) {
                }

                /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
                    if (android.text.TextUtils.equals(r3, r9.this$0.userAvatarDialog.getTargetId()) != false) goto L11;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:11:0x002b, code lost:
                    r2 = true;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:20:0x0049, code lost:
                    if (android.text.TextUtils.equals(r3, r9.this$0.userGuardAvatarDialog.getTargetId()) != false) goto L11;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
                    if (android.text.TextUtils.equals(r3, r9.this$0.userNobilityAvatarDialog.getTargetId()) != false) goto L11;
                 */
                @Override // com.tomatolive.library.http.ResultCallBack
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void onSuccess(UserEntity userEntity) {
                    int i2 = i;
                    boolean z5 = false;
                    if (i2 == 1) {
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        if (tomatoLiveFragment.isShowDialogFragment(tomatoLiveFragment.userNobilityAvatarDialog)) {
                        }
                    } else if (i2 == 2) {
                        TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                        if (tomatoLiveFragment2.isShowDialogFragment(tomatoLiveFragment2.userGuardAvatarDialog)) {
                        }
                    } else if (i2 == 3) {
                        TomatoLiveFragment tomatoLiveFragment3 = TomatoLiveFragment.this;
                        if (tomatoLiveFragment3.isShowDialogFragment(tomatoLiveFragment3.userAvatarDialog)) {
                        }
                    }
                    if (z5) {
                        TomatoLiveFragment.this.showAnchorPermissionDialog(z, userEntity.isBanPostBoolean(), z2, str2, str);
                    }
                }
            });
        } else if (c == 1) {
            showAudiencePermissionDialog(z2, str2, str);
        } else if (c != 2) {
            if (c != 3) {
                return;
            }
            showSuperControlPermissionDialog(str2, str);
        } else if (z || z3 || z4) {
            showAudiencePermissionDialog(z2, str2, str);
        } else {
            ((TomatoLivePresenter) this.mPresenter).showUserManageMenu(this.liveId, str2, new ResultCallBack<UserEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.34
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str3) {
                }

                /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
                    if (android.text.TextUtils.equals(r3, r4.this$0.userAvatarDialog.getTargetId()) != false) goto L11;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:11:0x002b, code lost:
                    r2 = true;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:20:0x0049, code lost:
                    if (android.text.TextUtils.equals(r3, r4.this$0.userGuardAvatarDialog.getTargetId()) != false) goto L11;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
                    if (android.text.TextUtils.equals(r3, r4.this$0.userNobilityAvatarDialog.getTargetId()) != false) goto L11;
                 */
                @Override // com.tomatolive.library.http.ResultCallBack
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void onSuccess(UserEntity userEntity) {
                    int i2 = i;
                    boolean z5 = false;
                    if (i2 == 1) {
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        if (tomatoLiveFragment.isShowDialogFragment(tomatoLiveFragment.userNobilityAvatarDialog)) {
                        }
                    } else if (i2 == 2) {
                        TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                        if (tomatoLiveFragment2.isShowDialogFragment(tomatoLiveFragment2.userGuardAvatarDialog)) {
                        }
                    } else if (i2 == 3) {
                        TomatoLiveFragment tomatoLiveFragment3 = TomatoLiveFragment.this;
                        if (tomatoLiveFragment3.isShowDialogFragment(tomatoLiveFragment3.userAvatarDialog)) {
                        }
                    }
                    if (z5) {
                        TomatoLiveFragment.this.showControlPermissionDialog(z2, userEntity.isBanPostBoolean(), str2, str);
                    }
                }
            });
        }
    }

    private void showAudiencePermissionDialog(boolean z, final String str, final String str2) {
        LiveActionBottomDialog.create("2", z, new LiveActionBottomDialog.OnLiveActionListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$EeE5UHyfNIftbxtaCMPF0EBDy-s
            @Override // com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog.OnLiveActionListener
            public final void onLiveAction(int i, boolean z2) {
                TomatoLiveFragment.this.lambda$showAudiencePermissionDialog$54$TomatoLiveFragment(str, str2, i, z2);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$showAudiencePermissionDialog$54$TomatoLiveFragment(String str, String str2, int i, boolean z) {
        if (i == 3) {
            dismissUserAvatarDialog();
            clickShielded(z, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showControlPermissionDialog(boolean z, boolean z2, final String str, final String str2) {
        LiveActionBottomDialog.create("3", z, z2, isPayLiveNeedBuyTicket(), new LiveActionBottomDialog.OnLiveActionListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$9PrDZHJFSnRLnTUNrNxJmBp7-dQ
            @Override // com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog.OnLiveActionListener
            public final void onLiveAction(int i, boolean z3) {
                TomatoLiveFragment.this.lambda$showControlPermissionDialog$55$TomatoLiveFragment(str, str2, i, z3);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$showControlPermissionDialog$55$TomatoLiveFragment(String str, String str2, int i, boolean z) {
        dismissUserAvatarDialog();
        if (i == 2) {
            clickBanned(z, str, str2);
        } else if (i == 3) {
            clickShielded(z, str, str2);
        } else if (i != 4) {
        } else {
            clickKickOut(str, str2);
        }
    }

    private void showSuperControlPermissionDialog(final String str, final String str2) {
        LiveActionBottomDialog.create("5", new LiveActionBottomDialog.OnLiveActionListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$wwTPbW-O4Zp59nxx4yDRKRb09rU
            @Override // com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog.OnLiveActionListener
            public final void onLiveAction(int i, boolean z) {
                TomatoLiveFragment.this.lambda$showSuperControlPermissionDialog$56$TomatoLiveFragment(str, str2, i, z);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$showSuperControlPermissionDialog$56$TomatoLiveFragment(String str, String str2, int i, boolean z) {
        WsManager wsManager;
        dismissUserAvatarDialog();
        if (i != 2) {
            if (i != 4 || (wsManager = this.wsManager) == null) {
                return;
            }
            wsManager.sendSuperGoOutMessage(MessageHelper.convertToSuperGoOutMsg(this.liveId, str, str2));
            return;
        }
        WsManager wsManager2 = this.wsManager;
        if (wsManager2 == null) {
            return;
        }
        wsManager2.sendSuperBannedMessage(MessageHelper.convertToSuperBanMsg(this.liveId, str, str2));
    }

    private void clickBanned(boolean z, final String str, final String str2) {
        if (z) {
            BottomDialogUtils.showBannedDialog(this.mContext, new BottomDialogUtils.LiveBottomBannedMenuListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$jFljenHDFwiUCShlX3Mtz1-90go
                @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.LiveBottomBannedMenuListener
                public final void onLiveBottomBannedMenuListener(long j) {
                    TomatoLiveFragment.this.lambda$clickBanned$57$TomatoLiveFragment(str, str2, j);
                }
            });
            return;
        }
        WsManager wsManager = this.wsManager;
        if (wsManager == null) {
            return;
        }
        wsManager.sendBannedMessage(MessageHelper.convertToBanMsg(str, str2, "-1", "2"));
    }

    public /* synthetic */ void lambda$clickBanned$57$TomatoLiveFragment(String str, String str2, long j) {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendBannedMessage(MessageHelper.convertToBanMsg(str, str2, String.valueOf(j), "1"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAnchorPermissionDialog(boolean z, boolean z2, boolean z3, final String str, final String str2) {
        LiveActionBottomDialog.create("1", z, z3, z2, true, new LiveActionBottomDialog.OnLiveActionListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Sjv2SyXXVoNAcmrBo7eOFqAERE8
            @Override // com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog.OnLiveActionListener
            public final void onLiveAction(int i, boolean z4) {
                TomatoLiveFragment.this.lambda$showAnchorPermissionDialog$58$TomatoLiveFragment(str2, str, i, z4);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$showAnchorPermissionDialog$58$TomatoLiveFragment(final String str, final String str2, int i, boolean z) {
        dismissUserAvatarDialog();
        if (i == 1) {
            clickCtrl(z, str2, str);
        } else if (i == 2) {
            clickBanned(z, str2, str);
        } else if (i == 3) {
            clickShielded(z, str2, str);
        } else if (i == 4) {
            clickKickOut(str2, str);
        } else if (i != 5) {
        } else {
            ((TomatoLivePresenter) this.mPresenter).getAnchorFrozenStatus(new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.35
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(AnchorEntity anchorEntity) {
                    if (anchorEntity != null && !anchorEntity.isFrozenFlag()) {
                        boolean z2 = false;
                        if (TomatoLiveFragment.this.privateMsgDialog != null) {
                            if (TomatoLiveFragment.this.privateMsgDialog.isAdded()) {
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(PrivateMsgDialog.TYPE_FORM_ANCHOR, true);
                            bundle.putInt(PrivateMsgDialog.CONTENT_TYPE_KEY, 2);
                            bundle.putString(PrivateMsgDialog.TARGET_ID_KEY, str2);
                            bundle.putString(PrivateMsgDialog.TARGET_NAME_KEY, str);
                            String str3 = PrivateMsgDialog.TYPE_SOCKET_STATUS;
                            if (TomatoLiveFragment.this.isSocketError || TomatoLiveFragment.this.isSocketClose) {
                                z2 = true;
                            }
                            bundle.putBoolean(str3, z2);
                            TomatoLiveFragment.this.privateMsgDialog.setArguments(bundle);
                            TomatoLiveFragment.this.privateMsgDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
                            return;
                        }
                        TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                        String str4 = str;
                        String str5 = str2;
                        if (tomatoLiveFragment.isSocketError || TomatoLiveFragment.this.isSocketClose) {
                            z2 = true;
                        }
                        tomatoLiveFragment.privateMsgDialog = PrivateMsgDialog.newInstance(str4, str5, z2);
                        TomatoLiveFragment.this.privateMsgDialog.setSendPrivateMsgListener(TomatoLiveFragment.this);
                        TomatoLiveFragment.this.privateMsgDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
                        return;
                    }
                    TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                    tomatoLiveFragment2.showToast(tomatoLiveFragment2.getString(R$string.fq_create_chat_fail));
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str3) {
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.showToast(tomatoLiveFragment.getString(R$string.fq_create_chat_fail));
                }
            });
        }
    }

    private void clickKickOut(String str, String str2) {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendKickOutMessage(MessageHelper.convertToKickOutMsg(this.liveId, str, str2));
        }
    }

    private void clickCtrl(boolean z, String str, String str2) {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendCtrlMessage(MessageHelper.convertToCtrlMsg(this.liveId, str, str2, z));
        }
    }

    private void clickShielded(boolean z, String str, String str2) {
        if (z) {
            if (!this.shieldedList.contains(str)) {
                this.shieldedList.add(str);
                DBUtils.updateShieldUser(str, true);
                showToast(getString(R$string.fq_shielded) + str2);
            }
        } else {
            this.shieldedList.remove(str);
            DBUtils.updateShieldUser(str, false);
            showToast(getString(R$string.fq_cancel_shielded, str2));
        }
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendShieldMessage(MessageHelper.convertToShieldMsg(this.liveId, str, z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleTrans() {
        if (AppUtils.isEnableTranslation(this.myUserInfoEntity.getExpGrade()) || AppUtils.isHouseSuperManager(this.myUserInfoEntity.getRole())) {
            if (this.isTranOpen) {
                this.isTranOpen = false;
                showToast(R$string.fq_close_tran);
                return;
            }
            TransDialog transDialog = this.transDialog;
            if (transDialog == null) {
                return;
            }
            transDialog.show(getChildFragmentManager());
            return;
        }
        WarnDialog.newInstance(WarnDialog.TRANSLATION_TIP, getString(R$string.fq_enable_translation_level_tips, AppUtils.getEnableTranslationLevel())).show(getChildFragmentManager());
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.InputTextMsgForAudienceDialog.OnTextSendListener
    public void onTextSend(String str, int i) {
        if (!isLoginUser()) {
            return;
        }
        if (isSocketClose()) {
            showToast(R$string.fq_text_network_error_chat);
        } else if (i == 0 || i == 1 || i == 2) {
            onSendSocketMsg(str, i);
        } else if (i != 3) {
        } else {
            if (!this.trumpetStatus) {
                showToast(R$string.fq_trumpet_freezen);
            } else if (this.curTrumpetCount.get() == 0) {
                showToast(R$string.fq_trumpet_count_not_enough);
                NobilityOpenTipsDialog.newInstance(12, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$Egns5nJpBftl_AL35rWJKKmFmmE
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TomatoLiveFragment.this.lambda$onTextSend$59$TomatoLiveFragment(view);
                    }
                }).show(getFragmentManager());
            } else {
                ((TomatoLivePresenter) this.mPresenter).sendTrumpet(str);
            }
        }
    }

    public /* synthetic */ void lambda$onTextSend$59$TomatoLiveFragment(View view) {
        toNobilityOpenActivity();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.InputTextMsgForAudienceDialog.OnTextSendListener
    public void selectTypeDialog(View view, int i) {
        if (!isConsumptionPermissionUser()) {
            return;
        }
        if (i == 1) {
            if (AppUtils.isGuardUser(NumberUtils.string2int(this.guardItemEntity.userGuardType))) {
                this.mInputTextMsgDialog.selectGuard();
                return;
            }
            dismissInputMsgDialog();
            GuardOpenTipsDialog.newInstance(11, this.guardItemEntity, this).show(getChildFragmentManager());
        } else if (i == 2) {
            if (AppUtils.highThanBoJue(this.myNobilityType)) {
                this.mInputTextMsgDialog.selectNobility();
                return;
            }
            dismissInputMsgDialog();
            NobilityOpenTipsDialog.newInstance(11, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$5Ytn6FDkxj0lkHiXatP9fhb7jPY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    TomatoLiveFragment.this.lambda$selectTypeDialog$60$TomatoLiveFragment(view2);
                }
            }).show(getFragmentManager());
        } else if (i != 3) {
        } else {
            if (AppUtils.highThanBoJue(this.myNobilityType)) {
                this.mInputTextMsgDialog.selectTrumpet(view);
                return;
            }
            dismissInputMsgDialog();
            NobilityOpenTipsDialog.newInstance(12, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$A6PJc2jdxTHHvzfVTDUbA7WNiE8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    TomatoLiveFragment.this.lambda$selectTypeDialog$61$TomatoLiveFragment(view2);
                }
            }).show(getFragmentManager());
        }
    }

    public /* synthetic */ void lambda$selectTypeDialog$60$TomatoLiveFragment(View view) {
        toNobilityOpenActivity();
    }

    public /* synthetic */ void lambda$selectTypeDialog$61$TomatoLiveFragment(View view) {
        toNobilityOpenActivity();
    }

    public void onSendSocketMsg(String str, int i) {
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), this.anchorId)) {
            this.myUserInfoEntity.setRole("1");
            InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
            if (inputTextMsgForAudienceDialog != null) {
                inputTextMsgForAudienceDialog.setMyRole(this.myUserInfoEntity.getRole());
            }
        }
        if (AppUtils.isAudience(this.myUserInfoEntity.getRole())) {
            if (!AppUtils.isGuardUser(NumberUtils.string2int(this.guardItemEntity.userGuardType)) && !AppUtils.isNobilityUser() && NumberUtils.string2int(this.speakLevel) > NumberUtils.string2int(this.myUserInfoEntity.getExpGrade())) {
                ChatTipDialog.newInstance(getString(R$string.fq_banned_speak_level_tip, this.speakLevel), true).show(getChildFragmentManager(), ConnectSocketParams.MESSAGE_SPEAKLEVEL_TYPE);
                return;
            }
            if (!AppUtils.isNobilityUser()) {
                this.clickCount.incrementAndGet();
                if (this.postIntervalTimes == 0) {
                    this.clickCount.getAndSet(2);
                } else {
                    if (this.clickCount.get() == 3) {
                        startCDCountDown(this.postIntervalTimes * 3);
                    }
                    if (this.clickCount.get() > 3) {
                        ChatTipDialog.newInstance(String.format(getString(R$string.fq_text_CD), Long.valueOf(this.countDownTime))).show(getChildFragmentManager(), "CD");
                        return;
                    }
                }
            }
            if (TextUtils.equals(str, this.lastMsg)) {
                ChatTipDialog.newInstance(getString(R$string.fq_text_same_content)).show(getChildFragmentManager(), "same_content");
                return;
            }
            this.lastMsg = str;
        }
        if (this.isBanGroup) {
            final ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgSendName(UserInfoManager.getInstance().getUserNickname());
            chatEntity.setMsgText(str);
            chatEntity.setMsgType(3);
            chatEntity.setSex(UserInfoManager.getInstance().getUserSex());
            chatEntity.setRole(this.myUserInfoEntity.getRole());
            chatEntity.setUserRole(this.myUserInfoEntity.getUserRole());
            chatEntity.setUid(this.myUserInfoEntity.getUserId());
            chatEntity.setUserAvatar(UserInfoManager.getInstance().getAvatar());
            chatEntity.setExpGrade(this.myUserInfoEntity.getExpGrade());
            chatEntity.setGuardType(NumberUtils.string2int(this.guardItemEntity.userGuardType));
            chatEntity.setWeekStar(this.myWeekStar);
            chatEntity.setDanmuType(i);
            chatEntity.setNobilityType(this.myNobilityType);
            chatEntity.setMarkUrls(this.myUserInfoEntity.getMarkUrls());
            this.mLiveChatMsgView.addChatMsg(chatEntity);
            if (i != 1 && i != 2) {
                return;
            }
            handlerWorkPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$P3AUD_pg_IL0sXMap23GD5_1VrE
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$onSendSocketMsg$62$TomatoLiveFragment(chatEntity);
                }
            });
            return;
        }
        WsManager wsManager = this.wsManager;
        if (wsManager == null) {
            return;
        }
        wsManager.sendChatMessage(MessageHelper.convertToChatMsg(str, i));
    }

    public /* synthetic */ void lambda$onSendSocketMsg$62$TomatoLiveFragment(ChatEntity chatEntity) {
        this.mLivePusherInfoView.addDanmuMsg(chatEntity);
    }

    private void startCDCountDown(final int i) {
        Observable.interval(1L, TimeUnit.SECONDS).map(new Function() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$O7G62--ybhSJbR68TUUoIeGfW-I
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                Long valueOf;
                valueOf = Long.valueOf(i - ((Long) obj).longValue());
                return valueOf;
            }
        }).take(i + 1).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.36
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                TomatoLiveFragment.this.cdDisposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                TomatoLiveFragment.this.countDownTime = l.longValue();
                if (TomatoLiveFragment.this.countDownTime == 0) {
                    TomatoLiveFragment.this.clickCount.getAndSet(2);
                }
            }
        });
    }

    private void showChatFrames() {
        if (this.isConnectingChatService) {
            showToast(R$string.fq_start_connect_socket);
        } else if (this.reConnectCountOver) {
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.resetCount();
            this.wsManager.reconnect();
        } else {
            showInputMsgDialog(false);
            moveUpViews(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInputMsgDialog(boolean z) {
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog == null || inputTextMsgForAudienceDialog.isShowing()) {
            return;
        }
        this.mInputTextMsgDialog.show(z);
    }

    private void moveUpViews(boolean z) {
        float f = 0.0f;
        this.mLiveChatMsgView.setTranslationY(z ? -(ScreenUtils.getScreenHeight() * 0.42f) : 0.0f);
        this.mLivePusherInfoView.setTranslationY(z ? -(ScreenUtils.getScreenHeight() / 4.0f) : 0.0f);
        LiveAnimationView liveAnimationView = this.liveAnimationView;
        if (z) {
            f = -(ScreenUtils.getScreenHeight() / 3.0f);
        }
        liveAnimationView.setTranslationY(f);
    }

    private void addMsgToQueue(ChatEntity chatEntity) {
        synchronized (TomatoLiveFragment.class) {
            if (this.receiveMsgQueue == null) {
                this.receiveMsgQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.receiveMsgQueue.size() == 9999) {
                this.receiveMsgQueue.poll();
            }
            this.receiveMsgQueue.offer(chatEntity);
            if (this.asleep) {
                this.asleep = false;
                sendWorkHandlerEmptyMessage(10001);
            }
        }
    }

    private void showReceiveMsgOnChatList(SocketMessageEvent.ResultData resultData, String str, int i) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setAnchorId(this.anchorId);
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setMsgText(str);
        chatEntity.setMsgType(i);
        chatEntity.setSex(resultData.sex);
        chatEntity.setRole(resultData.role);
        chatEntity.setUserRole(resultData.userRole);
        chatEntity.setUid(resultData.userId);
        chatEntity.setWeekStar(resultData.isWeekStar);
        chatEntity.setGiftName(resultData.giftName);
        chatEntity.setGiftNum(resultData.giftNum);
        chatEntity.setUserAvatar(resultData.avatar);
        chatEntity.setTargetAvatar(resultData.targetAvatar);
        chatEntity.setTargetName(resultData.targetName);
        chatEntity.setTargetId(resultData.targetId);
        chatEntity.setExpGrade(AppUtils.formatExpGrade(resultData.expGrade));
        chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        chatEntity.setSomeoneBanPost(resultData.isSomeoneBanPost());
        chatEntity.setAppId(resultData.appId);
        chatEntity.setOpenId(resultData.openId);
        chatEntity.setMarkUrls(resultData.markUrls);
        if (resultData.isOpenGuardDanmu()) {
            chatEntity.setDanmuType(1);
        }
        if (resultData.isOpenNobilityDanmu()) {
            chatEntity.setDanmuType(2);
        }
        chatEntity.setNobilityType(resultData.nobilityType);
        if (i == 6) {
            chatEntity.setSetManager(TextUtils.equals(resultData.action, "1"));
        }
        if (i == 15) {
            chatEntity.setMsgSendName(TextUtils.equals(resultData.userId, this.anchorId) ? this.mContext.getString(R$string.fq_anchor) : resultData.userName);
            chatEntity.setExpGrade(resultData.afterGrade);
        }
        addMsgToQueue(chatEntity);
        if (i == 3 && ((resultData.isOpenGuardDanmu() || resultData.isOpenNobilityDanmu()) && (AppUtils.isGuardUser(NumberUtils.string2int(resultData.guardType)) || AppUtils.isNobilityUser(resultData.nobilityType)))) {
            this.mLivePusherInfoView.addDanmuMsg(chatEntity);
        }
        if (TextUtils.equals(resultData.userId, UserInfoManager.getInstance().getUserId())) {
            this.speakTotalCount.incrementAndGet();
        }
        if (TextUtils.equals(chatEntity.getUid(), UserInfoManager.getInstance().getUserId())) {
            LogEventUtils.uploadBarrageSend(this.anchorItemEntity, chatEntity.getDanmuType(), this.myUserInfoEntity.expGrade);
        }
    }

    private void showSelfGiftMsgOnChatList(GiftItemEntity giftItemEntity) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgSendName(UserInfoManager.getInstance().getUserNickname());
        chatEntity.setUid(this.myUserInfoEntity.getUserId());
        chatEntity.setMsgType(1);
        chatEntity.setGiftName(giftItemEntity.name);
        chatEntity.setUserAvatar(giftItemEntity.avatar);
        chatEntity.setRole(giftItemEntity.role);
        chatEntity.setUserRole(giftItemEntity.userRole);
        chatEntity.setExpGrade(AppUtils.formatExpGrade(giftItemEntity.expGrade));
        chatEntity.setGuardType(giftItemEntity.guardType);
        chatEntity.setSex(giftItemEntity.sex);
        chatEntity.setNobilityType(giftItemEntity.nobilityType);
        chatEntity.setWeekStar(giftItemEntity.weekStar);
        chatEntity.setGiftNum(giftItemEntity.giftNum);
        chatEntity.setOpenId(giftItemEntity.openId);
        chatEntity.setAppId(giftItemEntity.appId);
        chatEntity.setMarkUrls(giftItemEntity.marks);
        if (giftItemEntity.isSendSingleGift()) {
            if (giftItemEntity.isBigAnim()) {
                chatEntity.setMsgText(AppUtils.appendGiftStringWithIndex(giftItemEntity));
            } else {
                int i = giftItemEntity.sendIndex;
                if (i == 1) {
                    chatEntity.setMsgText(AppUtils.appendGiftStringNoIndex(giftItemEntity));
                } else if (i == 0 || i % 10 != 0) {
                    return;
                } else {
                    chatEntity.setMsgText(AppUtils.appendGiftStringWithIndex(giftItemEntity));
                }
            }
        } else {
            chatEntity.setMsgText(AppUtils.appendBatchGiftString(giftItemEntity));
        }
        addMsgToQueue(chatEntity);
    }

    public /* synthetic */ boolean lambda$new$64$TomatoLiveFragment(Message message) {
        switch (message.what) {
            case 10001:
                dealChatMsg();
                return true;
            case 10002:
                dealEnterMsg();
                return true;
            case 10003:
                dealGuardEnterMsg();
                return true;
            case 10004:
                dealGiftNotice();
                return true;
            case 10005:
                dealSysNotice();
                return true;
            case 10006:
                dealPrivateMsg();
                return true;
            case 10007:
            case 10008:
            case 10009:
            default:
                return true;
            case ConstantUtils.SYS_LUCK_HIT /* 10010 */:
                dealSysLuckNotice();
                return true;
            case 10011:
                dealAnchorInfoNotice();
                return true;
            case ConstantUtils.GAME_NOTICE /* 10012 */:
                dealGameNotice();
                return true;
            case ConstantUtils.INTIMATE_TASK_NOTICE /* 10013 */:
                dealIntimateTask();
                return true;
        }
    }

    private void dealIntimateTask() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowIntimateNotice.get() || (poll = this.intimateTaskQueue.poll()) == null) {
            return;
        }
        this.canShowIntimateNotice.set(false);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$mPH3zOcBC4MWsFKTRb_PkdtzOMk
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealIntimateTask$65$TomatoLiveFragment(poll);
            }
        });
    }

    private void dealPrivateMsg() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowPrivateMsg.get() || (poll = this.privateMsgQueue.poll()) == null) {
            return;
        }
        if (TextUtils.equals(poll.sysNoticeType, ConnectSocketParams.MESSAGE_USER_PRIVATE_MESSAGE)) {
            this.canShowPrivateMsg.set(false);
            final List<UserPrivateMessageEntity> list = poll.userPrivateMessageDetailsDTOList;
            if (list != null && !list.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$jU-_eenyYBVnB-RnBRMecY23Yfw
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealPrivateMsg$66$TomatoLiveFragment(poll, list);
                    }
                });
                for (UserPrivateMessageEntity userPrivateMessageEntity : list) {
                    if (userPrivateMessageEntity != null) {
                        arrayList.add(userPrivateMessageEntity.messageId);
                        addPrivateMsgData(userPrivateMessageEntity.messageId, userPrivateMessageEntity.userId, userPrivateMessageEntity.avatar, userPrivateMessageEntity.userName, userPrivateMessageEntity.content, userPrivateMessageEntity.winningFlag, userPrivateMessageEntity.flagContent);
                    }
                }
                WsManager wsManager = this.wsManager;
                if (wsManager != null) {
                    wsManager.sendUserPrivateMsgReceiptMessage(MessageHelper.convertUserPrivateMsgReceiptMsg(poll.offlineFlag, arrayList));
                }
            }
            this.canShowPrivateMsg.set(true);
            sendWorkHandlerEmptyMessage(10006);
        } else if (TextUtils.equals(UserInfoManager.getInstance().getUserId(), poll.userId)) {
            sendWorkHandlerEmptyMessage(10006);
        } else {
            this.canShowPrivateMsg.set(false);
            addPrivateMsgData(poll.messageId, poll.userId, poll.avatar, poll.userName, poll.content, "", "");
            WsManager wsManager2 = this.wsManager;
            if (wsManager2 != null) {
                wsManager2.sendChatReceiptMessage(MessageHelper.convertToChatReceiptMsg(poll.userId, poll.messageId));
            }
            this.canShowPrivateMsg.set(true);
            sendWorkHandlerEmptyMessage(10006);
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$0f9kgXceqvhQoHN-ot7v_JMbKsQ
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealPrivateMsg$67$TomatoLiveFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$dealPrivateMsg$66$TomatoLiveFragment(SocketMessageEvent.ResultData resultData, List list) {
        LiveMoreDialog liveMoreDialog = this.liveMoreDialog;
        if (liveMoreDialog != null && liveMoreDialog.isAdded()) {
            this.liveMoreDialog.updatePanelView();
        }
        if (!resultData.isOfflinePrivateMsgFlag() || !DBUtils.isShowOfflinePrivateMsgDialog(list)) {
            return;
        }
        this.offlinePrivateMsgDialog = SureCancelDialog.newInstance(null, this.mContext.getString(R$string.fq_hd_offline_private_msg_tips), this.mContext.getString(R$string.fq_hd_not_prompt_tips), this.mContext.getString(R$string.fq_hd_view_detail_tips), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        }, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TomatoLiveFragment.this.showPrivateMessageDialog();
            }
        });
        this.offlinePrivateMsgDialog.show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$dealPrivateMsg$67$TomatoLiveFragment() {
        LiveMoreDialog liveMoreDialog = this.liveMoreDialog;
        if (liveMoreDialog == null || !liveMoreDialog.isAdded()) {
            return;
        }
        this.liveMoreDialog.updatePanelView();
    }

    private void dealGameNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowGameNotice.get() || (poll = this.gameNoticeQueue.poll()) == null) {
            return;
        }
        if (!SPUtils.getInstance().getBoolean(ConstantUtils.NOTICE_GAME_KEY, true) && TextUtils.equals(poll.sysNoticeType, ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE_GAME)) {
            if (this.gameNoticeQueue.isEmpty()) {
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$YWrEoOxXoXbPGbXbA00sn3IsjYM
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealGameNotice$68$TomatoLiveFragment();
                    }
                });
                return;
            } else {
                sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
                return;
            }
        }
        this.canShowGameNotice.set(false);
        this.curGameNoticeEntity = poll;
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$z_XAQpuAjI6jgiOu-oLV0NfC9Ho
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealGameNotice$69$TomatoLiveFragment(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealGameNotice$68$TomatoLiveFragment() {
        this.mLivePusherInfoView.hideGameNoticeView();
    }

    public /* synthetic */ void lambda$dealGameNotice$69$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(resultData.sysNoticeType, ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE_GAME)) {
            this.mLivePusherInfoView.setGameNoticeAnim(resultData, this.trumpetPlayPeriod);
        } else if (!TextUtils.equals(resultData.sysNoticeType, ConnectSocketParams.MESSAGE_PK_ASSIST_KING)) {
        } else {
            this.mLivePusherInfoView.setPkAssistNoticeAnim(resultData, this.trumpetPlayPeriod);
        }
    }

    private void dealSysLuckNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowLuckNotice.get() || (poll = this.luckNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowLuckNotice.set(false);
        this.luckNoticeLiveId = poll.forwardLiveId;
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$zJC8xTjeChmtcRsnRnhATWpTbLo
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealSysLuckNotice$70$TomatoLiveFragment(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealSysLuckNotice$70$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setLuckNoticeAnim(resultData, this.liveId, this.trumpetPlayPeriod);
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(getString(R$string.fq_msg_attention_tips));
        chatEntity.setMsgType(19);
        chatEntity.setTargetName(resultData.anchorName);
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setPropId(resultData.drawWay);
        chatEntity.setPropName(resultData.propName);
        chatEntity.setPropNum(resultData.propCount);
        chatEntity.setTargetId(TextUtils.equals(this.liveId, resultData.forwardLiveId) ? null : resultData.forwardLiveId);
        this.mLiveChatMsgView.addChatMsg(chatEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelBigAnim */
    public void lambda$playMySelfAnimOnMainThread$20$TomatoLiveFragment() {
        if (!this.liveAnimationView.isGiftAnimating() || TextUtils.equals(this.myUserInfoEntity.getUserId(), this.curBigAnimSendUserId)) {
            return;
        }
        this.liveAnimationView.stopGiftAnimating();
    }

    private void addBigAnim(SocketMessageEvent.ResultData resultData, int i) {
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.name = resultData.userName;
        giftItemEntity.bigAnimType = i;
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        if (TextUtils.equals(resultData.userId, this.myUserInfoEntity.getUserId())) {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$qefmP7gSv9Zio1i1aKaGNn75xWY
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$addBigAnim$71$TomatoLiveFragment();
                }
            });
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.addLocalAnim(giftItemEntity);
            return;
        }
        WsManager wsManager2 = this.wsManager;
        if (wsManager2 == null) {
            return;
        }
        wsManager2.addReceiveBigAnim(giftItemEntity);
    }

    private void dealGuardEnterMsg() {
        final SocketMessageEvent.ResultData poll;
        if (this.canShowGuardEnterMsg.get() && this.carFullAnimFinish.get() && (poll = this.guardEnterMsgQueue.poll()) != null) {
            this.canShowGuardEnterMsg.set(false);
            final String formatExpGrade = AppUtils.formatExpGrade(poll.expGrade);
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$AFb8mhn2d2mJoG1pXIDW6rpVLZ8
                @Override // java.lang.Runnable
                public final void run() {
                    TomatoLiveFragment.this.lambda$dealGuardEnterMsg$72$TomatoLiveFragment(poll, formatExpGrade);
                }
            });
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.guardEnterMsgQueue;
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
            return;
        }
        sendWorkHandlerEmptyMessage(10003);
    }

    public /* synthetic */ void lambda$dealGuardEnterMsg$72$TomatoLiveFragment(SocketMessageEvent.ResultData resultData, String str) {
        if (resultData.isEnterGuardType() && AppUtils.hasCar(resultData.carId)) {
            if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId)) {
                this.liveAnimationView.loadLiveEnterAnimation(resultData.guardType, GlideUtils.getGuardSVGADynamicEntity(this.mContext, resultData.avatar, resultData.userName, str, resultData.guardType));
                this.liveAnimationView.loadCarJoinAnimation(resultData, false);
                this.carFullAnimFinish.set(false);
                return;
            }
            this.liveAnimationView.loadLiveEnterAnimation(resultData.guardType, GlideUtils.getGuardSVGADynamicEntity(this.mContext, resultData.avatar, resultData.userName, str, resultData.guardType));
            if (!resultData.isOnPlayCarAnim()) {
                return;
            }
            this.carFullAnimFinish.set(false);
            this.liveAnimationView.loadCarJoinAnimation(resultData, false);
        } else if (resultData.isEnterGuardType()) {
            this.liveAnimationView.loadLiveEnterAnimation(resultData.guardType, GlideUtils.getGuardSVGADynamicEntity(this.mContext, resultData.avatar, resultData.userName, str, resultData.guardType));
        } else if (TextUtils.equals(this.myUserInfoEntity.getUserId(), resultData.userId)) {
            this.carFullAnimFinish.set(false);
            this.liveAnimationView.loadCarJoinAnimation(resultData, true);
        } else if (resultData.isOnPlayCarAnim()) {
            this.carFullAnimFinish.set(false);
            this.liveAnimationView.loadCarJoinAnimation(resultData, true);
        } else {
            this.canShowGuardEnterMsg.set(true);
        }
    }

    private void dealGiftNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowGiftNotice.get() || (poll = this.giftNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowGiftNotice.set(false);
        this.giftNoticeLiveId = poll.liveId;
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$AQtmj8JKhkyJkaeSEc-0J8wLz_k
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealGiftNotice$73$TomatoLiveFragment(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealGiftNotice$73$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setGiftNoticeAnim(resultData.userName, resultData.anchorName, resultData.giftNum, resultData.giftName, resultData.markId, AppUtils.getGiftNoticeInterval());
    }

    private void dealAnchorInfoNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowAnchorInfoNotice.get() || (poll = this.anchorInfoNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowAnchorInfoNotice.set(false);
        this.curAnchorInfoNoticeEntity = poll;
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$ipWfj-KMQAy93PWpeEtcKO2dXWs
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealAnchorInfoNotice$74$TomatoLiveFragment(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealAnchorInfoNotice$74$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        char c;
        String str = resultData.sysNoticeType;
        int hashCode = str.hashCode();
        if (hashCode != -1671569065) {
            if (hashCode == -648591709 && str.equals(ConnectSocketParams.RESULT_DATA_DAY_RANK_UP)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN)) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            this.mLivePusherInfoView.setCharmNoticeAnim(resultData.anchorName, resultData.anchorNewRank, AppUtils.getGiftNoticeInterval());
        } else if (c != 1) {
        } else {
            this.mLivePusherInfoView.setAnchorOpenNoticeAnim(resultData, AppUtils.getGiftNoticeInterval());
        }
    }

    private void dealSysNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowSysNotice.get() || (poll = this.sysNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowSysNotice.set(false);
        this.tempSysNoticeResultData = poll;
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$kPUtMOcd9ZN0SWtfRqny00qrb58
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealSysNotice$75$TomatoLiveFragment(poll);
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ void lambda$dealSysNotice$75$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        char c;
        String str = resultData.sysNoticeType;
        switch (str.hashCode()) {
            case -1603387347:
                if (str.equals(ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -941691210:
                if (str.equals(ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -370196576:
                if (str.equals(ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 395254178:
                if (str.equals(ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.mLivePusherInfoView.setSysNobilityTrumpetAnim(resultData, this.trumpetPlayPeriod);
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgText(resultData.content);
            chatEntity.setMsgType(20);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setTargetName(resultData.anchorName);
            this.mLiveChatMsgView.addChatMsg(chatEntity);
        } else if (c == 1) {
            this.mLivePusherInfoView.setSysNoticeAnim(resultData.content, this.nobilityPlayPeriod);
            ChatEntity chatEntity2 = new ChatEntity();
            chatEntity2.setMsgText(resultData.content);
            chatEntity2.setMsgType(21);
            this.mLiveChatMsgView.addChatMsg(chatEntity2);
        } else if (c == 2) {
            if (resultData.nobilityType == 1 && TextUtils.equals(resultData.type, "2")) {
                return;
            }
            this.mLivePusherInfoView.setSysNobilityOpenAnim(resultData, this.liveId, this.nobilityPlayPeriod);
        } else if (c != 3 || !TextUtils.equals("recommend", resultData.type)) {
        } else {
            this.mLivePusherInfoView.setSysNobilityRecommendHotAnim(resultData, this.nobilityPlayPeriod);
        }
    }

    private void dealChatMsg() {
        ChatEntity poll;
        final LinkedList linkedList = new LinkedList();
        synchronized (TomatoLiveFragment.class) {
            if (this.receiveMsgQueue != null && !this.receiveMsgQueue.isEmpty()) {
                for (int i = 0; i < 5 && (poll = this.receiveMsgQueue.poll()) != null; i++) {
                    linkedList.add(poll);
                }
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$WuJGk5MVWppxw-G8UJR0pgPZO6Q
                    @Override // java.lang.Runnable
                    public final void run() {
                        TomatoLiveFragment.this.lambda$dealChatMsg$76$TomatoLiveFragment(linkedList);
                    }
                });
                return;
            }
            this.asleep = true;
        }
    }

    public /* synthetic */ void lambda$dealChatMsg$76$TomatoLiveFragment(List list) {
        this.mLiveChatMsgView.addChatMsgList(list);
        sendWorkHandlerEmptyMessageDelayed(10001, DURATION_GET_MESSAGE);
    }

    private void dealEnterMsg() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowEnterMsg.get() || (poll = this.enterMsgQueue.poll()) == null) {
            return;
        }
        this.canShowEnterMsg.set(false);
        if (TextUtils.equals(this.myUserInfoEntity.getUserId(), poll.userId)) {
            this.myNobilityType = poll.nobilityType;
            UserInfoManager.getInstance().setNobilityType(this.myNobilityType);
            this.myWeekStar = poll.isWeekStar;
        } else if (AppUtils.isNobilityUser(poll.nobilityType) && !poll.isPlayNobilityEnterAnim()) {
            this.canShowEnterMsg.set(true);
            ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.enterMsgQueue;
            if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
                return;
            }
            sendWorkHandlerEmptyMessageDelayed(10002, DURATION_GET_MESSAGE);
            return;
        }
        final String formatExpGrade = AppUtils.formatExpGrade(poll.expGrade);
        if (TextUtils.isEmpty(formatExpGrade)) {
            return;
        }
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$wb9uYwjeWM6jm7nsInWdDN2l4_Q
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$dealEnterMsg$77$TomatoLiveFragment(poll, formatExpGrade);
            }
        });
    }

    public /* synthetic */ void lambda$dealEnterMsg$77$TomatoLiveFragment(SocketMessageEvent.ResultData resultData, String str) {
        if (!TextUtils.isEmpty(resultData.userId) || AppUtils.isChatEnterMsg(resultData.role, resultData.guardType, resultData.carId, resultData.nobilityType)) {
            updateUserList(resultData);
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(0);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(str);
            chatEntity.setRole(resultData.role);
            chatEntity.setUserRole(resultData.userRole);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setMsgText(getString(R$string.fq_live_join_notify_nobility));
            chatEntity.setUserAvatar(resultData.avatar);
            chatEntity.setUid(resultData.userId);
            chatEntity.setSex(resultData.sex);
            chatEntity.setCarIcon(resultData.carIcon);
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setWeekStar(resultData.isWeekStar);
            chatEntity.setOpenId(resultData.openId);
            chatEntity.setAppId(resultData.appId);
            chatEntity.setMarkUrls(resultData.markUrls);
            this.mLiveChatMsgView.addChatMsg(chatEntity);
        } else {
            this.mLiveChatMsgView.setUserGradeInfo(str, resultData.userName);
        }
        this.canShowEnterMsg.set(true);
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.enterMsgQueue;
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
            return;
        }
        sendWorkHandlerEmptyMessageDelayed(10002, DURATION_GET_MESSAGE);
    }

    private void updateUserList(SocketMessageEvent.ResultData resultData) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(resultData.avatar);
        userEntity.setUserId(resultData.userId);
        userEntity.setName(resultData.userName);
        userEntity.setSex(resultData.sex);
        userEntity.setExpGrade(AppUtils.formatExpGrade(resultData.expGrade));
        userEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        userEntity.setRole(resultData.role);
        userEntity.setUserRole(resultData.userRole);
        userEntity.setNobilityType(resultData.nobilityType);
        this.mLivePusherInfoView.addUserItem(userEntity);
    }

    private void dealGiftBoxMsg(SocketMessageEvent.ResultData resultData) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setMsgText(getString(R$string.fq_giftbox_tips));
        chatEntity.setMsgType(13);
        chatEntity.setTargetName(resultData.presenterName);
        chatEntity.setPropId(resultData.propId);
        chatEntity.setPropName(resultData.propName);
        chatEntity.setPropNum(resultData.propNum);
        addMsgToQueue(chatEntity);
    }

    private void dealExpGradeUpdate(SocketMessageEvent.ResultData resultData) {
        showReceiveMsgOnChatList(resultData, "dealExpGradeUpdate", 15);
    }

    private void dismissDialogs() {
        dismissUserAvatarDialog();
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.dismissDedicateTopDialog();
        }
        ComponentsWebViewDialog componentsWebViewDialog = this.componentsWebViewDialog;
        if (componentsWebViewDialog != null && componentsWebViewDialog.isShowing()) {
            this.componentsWebViewDialog.dismiss();
        }
        HdLotteryDrawingDialog hdLotteryDrawingDialog = this.hdLotteryDrawingDialog;
        if (hdLotteryDrawingDialog != null && hdLotteryDrawingDialog.isShowing()) {
            this.hdLotteryDrawingDialog.dismiss();
        }
        dismissInputMsgDialog();
        dismissDialogFragment(this.giftBottomDialog, this.mTaskBottomDialog, this.mLotteryDialog, this.mWeekStarRankingDialog, this.guardListDialog, this.guardOpenContentDialog, this.giftWallDialog, this.userAchieveDialog, this.componentsDialog, this.privateMsgDialog, this.liveMoreDialog, this.webViewDialog, this.pkRankDialog, this.offlinePrivateMsgDialog, this.payLiveTipsDialog, this.qmInteractUserDialog, this.qmInviteSureDialog, this.qmTaskListUserDialog);
        LivePusherInfoView livePusherInfoView2 = this.mLivePusherInfoView;
        if (livePusherInfoView2 != null) {
            livePusherInfoView2.clearLiveGuide();
        }
    }

    private void dismissUserAvatarDialog() {
        dismissDialogFragment(this.userAvatarDialog, this.userSuperAvatarDialog, this.userGuardAvatarDialog, this.userNobilityAvatarDialog, this.anchorAvatarDialog, this.anchorNobilityAvatarDialog);
    }

    private void dismissInputMsgDialog() {
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog == null || !inputTextMsgForAudienceDialog.isShowing()) {
            return;
        }
        this.mInputTextMsgDialog.dismiss();
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        onReleaseViewData();
        onDestroyViewData();
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWorkHandlerEmptyMessage(int i) {
        synchronized (this.synchronizedObj) {
            if (this.workHandler != null && !this.workHandler.hasMessages(i)) {
                this.workHandler.sendEmptyMessage(i);
            }
        }
    }

    private void sendWorkHandlerEmptyMessageDelayed(int i, long j) {
        synchronized (this.synchronizedObj) {
            if (this.workHandler != null && !this.workHandler.hasMessages(i)) {
                this.workHandler.sendEmptyMessageDelayed(i, j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerMainPost(Runnable runnable) {
        synchronized (this.synchronizedObj) {
            if (this.mainHandler != null && runnable != null) {
                this.mainHandler.post(runnable);
            }
        }
    }

    private void handlerWorkPost(Runnable runnable) {
        synchronized (this.synchronizedObj) {
            if (this.workHandler != null && runnable != null) {
                this.workHandler.post(runnable);
            }
        }
    }

    private GuardItemEntity formatAnchorGuardInfo(GuardItemEntity guardItemEntity) {
        if (guardItemEntity == null) {
            GuardItemEntity guardItemEntity2 = new GuardItemEntity();
            AnchorEntity anchorEntity = this.anchorItemEntity;
            guardItemEntity2.anchorId = anchorEntity.userId;
            guardItemEntity2.anchorName = anchorEntity.nickname;
            guardItemEntity2.expGrade = this.myUserInfoEntity.getExpGrade();
            guardItemEntity2.anchorGuardCount = this.liveItemEntity.anchorGuardCount;
            guardItemEntity2.liveCount = this.liveCount;
            return guardItemEntity2;
        }
        AnchorEntity anchorEntity2 = this.anchorItemEntity;
        guardItemEntity.anchorId = anchorEntity2.userId;
        guardItemEntity.anchorName = anchorEntity2.nickname;
        guardItemEntity.expGrade = this.myUserInfoEntity.getExpGrade();
        guardItemEntity.anchorGuardCount = this.liveItemEntity.anchorGuardCount;
        guardItemEntity.liveCount = this.liveCount;
        return guardItemEntity;
    }

    private AnchorEntity getAnchorEntityInfo(LiveEntity liveEntity) {
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.appId = liveEntity.appId;
        anchorEntity.openId = liveEntity.openId;
        anchorEntity.liveId = liveEntity.liveId;
        anchorEntity.liveCount = liveEntity.liveCount;
        anchorEntity.tag = liveEntity.tag;
        anchorEntity.userId = TextUtils.isEmpty(liveEntity.anchorId) ? liveEntity.userId : liveEntity.anchorId;
        anchorEntity.avatar = liveEntity.avatar;
        anchorEntity.sex = liveEntity.sex;
        anchorEntity.nickname = liveEntity.nickname;
        anchorEntity.expGrade = liveEntity.expGrade;
        anchorEntity.liveCoverUrl = TextUtils.isEmpty(liveEntity.liveCoverUrl) ? liveEntity.avatar : liveEntity.liveCoverUrl;
        this.anchorId = anchorEntity.userId;
        this.anchorAppId = anchorEntity.appId;
        return anchorEntity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toNobilityOpenActivity() {
        AppUtils.toNobilityOpenActivity(this.mContext, this.anchorItemEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attentionAnchorAction(View view, String str) {
        if (isConsumptionPermissionUser() && AppUtils.isAttentionUser(this.mContext, str)) {
            boolean z = !view.isSelected();
            view.setSelected(z);
            attentionAnchorAction(z, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attentionAnchorAction(boolean z, String str) {
        if (!isConsumptionPermissionUser()) {
            return;
        }
        showToast(z ? R$string.fq_text_attention_success : R$string.fq_text_attention_cancel_success);
        ((TomatoLivePresenter) this.mPresenter).attentionAnchor(str, z ? 1 : 0);
        this.mLivePusherInfoView.setFollowed(z);
        DBUtils.attentionAnchor(str, z);
        this.mLiveEndInfoView.setTvAttentionText(z);
        this.mLiveChatMsgView.updateAttentionAnchor(z);
        AnchorEntity anchorEntity = this.anchorItemEntity;
        LogEventUtils.uploadFollow(anchorEntity.openId, anchorEntity.appId, anchorEntity.tag, anchorEntity.expGrade, anchorEntity.nickname, getString(R$string.fq_live_room), z, this.liveId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuardListDialog(final GuardItemEntity guardItemEntity) {
        this.guardItemEntity = guardItemEntity;
        this.guardListDialog = GuardListDialog.newInstance(2, guardItemEntity, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$bdVXuXd4aC6V9HXr1MGMnH90-qc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TomatoLiveFragment.this.lambda$showGuardListDialog$78$TomatoLiveFragment(guardItemEntity, view);
            }
        });
        this.guardListDialog.show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$showGuardListDialog$78$TomatoLiveFragment(GuardItemEntity guardItemEntity, View view) {
        if (isConsumptionPermissionUser() && AppUtils.isEnableGuard()) {
            this.guardOpenContentDialog = GuardOpenContentDialog.newInstance(guardItemEntity, this);
            this.guardOpenContentDialog.show(getChildFragmentManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGiftWallDialog(AnchorEntity anchorEntity) {
        if (this.giftWallDialog == null) {
            this.giftWallDialog = GiftWallDialog.newInstance(anchorEntity);
            this.giftWallDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        this.giftWallDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.giftWallDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserAchieveDialog(UserEntity userEntity, String str) {
        if (this.userAchieveDialog == null) {
            this.userAchieveDialog = UserAchieveDialog.newInstance(userEntity, str);
            this.userAchieveDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, userEntity);
        this.bundleArgs.putString(ConstantUtils.RESULT_COUNT, str);
        this.userAchieveDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.userAchieveDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showComponentsWebViewDialog(boolean z, ComponentsEntity componentsEntity) {
        if (!isConsumptionPermissionUser()) {
            return;
        }
        if (componentsEntity != null && componentsEntity.isCorrectLink()) {
            ComponentsWebViewDialog componentsWebViewDialog = this.componentsWebViewDialog;
            if (componentsWebViewDialog == null) {
                return;
            }
            componentsWebViewDialog.showDialog(z, componentsEntity);
            return;
        }
        showToast(R$string.fq_game_url_invalid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPrivateMessageDialog() {
        if (!isConsumptionPermissionUser()) {
            return;
        }
        boolean equals = TextUtils.equals(this.anchorId, this.myUserInfoEntity.userId);
        boolean z = this.isSocketError || this.isSocketClose;
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null) {
            if (equals) {
                this.privateMsgDialog = PrivateMsgDialog.newInstance(true, z);
            } else {
                this.privateMsgDialog = PrivateMsgDialog.newInstance(z);
            }
            this.privateMsgDialog.setSendPrivateMsgListener(this);
            this.privateMsgDialog.show(getChildFragmentManager());
        } else if (!privateMsgDialog.isAdded()) {
            Bundle bundle = new Bundle();
            if (equals) {
                bundle.putBoolean(PrivateMsgDialog.TYPE_FORM_ANCHOR, true);
            }
            bundle.putInt(PrivateMsgDialog.CONTENT_TYPE_KEY, 1);
            bundle.putBoolean(PrivateMsgDialog.TYPE_SOCKET_STATUS, z);
            this.privateMsgDialog.setArguments(bundle);
            this.privateMsgDialog.show(getChildFragmentManager());
        }
        MsgStatusEntity msgStatusEntity = new MsgStatusEntity();
        msgStatusEntity.appId = UserInfoManager.getInstance().getAppId();
        msgStatusEntity.userId = UserInfoManager.getInstance().getUserId();
        msgStatusEntity.readStatus = "1";
        DBUtils.saveOrUpdateMsgStatus(msgStatusEntity);
        updateMoreRedDot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePrivateMessageNetStatus(boolean z) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null || !privateMsgDialog.isAdded()) {
            return;
        }
        this.privateMsgDialog.changeNetStatus(z);
    }

    private void showLiveMoreDialog() {
        if (this.liveMoreDialog == null) {
            this.liveMoreDialog = LiveMoreDialog.newInstance(this.isTranOpen, new LiveMoreDialog.OnMenuItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.39
                @Override // com.tomatolive.library.p136ui.view.dialog.LiveMoreDialog.OnMenuItemClickListener
                public void onItemClick(int i, MenuEntity menuEntity) {
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.liveMoreDialog);
                    switch (menuEntity.getMenuType()) {
                        case 273:
                            if (!TomatoLiveFragment.this.isConsumptionPermissionUser()) {
                                return;
                            }
                            TomatoLiveFragment.this.toggleTrans();
                            return;
                        case 274:
                            TomatoLiveFragment.this.showPrivateMessageDialog();
                            return;
                        case 275:
                            if (!TomatoLiveFragment.this.isConsumptionPermissionUser()) {
                                return;
                            }
                            if (!TomatoLiveFragment.this.isLiveEnd()) {
                                if (TomatoLiveFragment.this.mLiveAdBannerBottomView.isOneselfInitiateTask()) {
                                    TomatoLiveFragment.this.showQMInteractTaskListDialog(null);
                                    return;
                                } else {
                                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).sendUserPendingTaskRequest(TomatoLiveFragment.this.liveId, new ResultCallBack<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.39.1
                                        @Override // com.tomatolive.library.http.ResultCallBack
                                        public void onSuccess(QMInteractTaskEntity qMInteractTaskEntity) {
                                            TomatoLiveFragment.this.showQMInteractTaskListDialog(qMInteractTaskEntity);
                                        }

                                        @Override // com.tomatolive.library.http.ResultCallBack
                                        public void onError(int i2, String str) {
                                            TomatoLiveFragment.this.showQMInteractUserDialog();
                                        }
                                    });
                                    return;
                                }
                            }
                            TomatoLiveFragment.this.showToast(R$string.fq_qm_live_end_start_task_tips);
                            return;
                        default:
                            return;
                    }
                }
            });
            this.liveMoreDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putBoolean(ConstantUtils.RESULT_FLAG, this.isTranOpen);
        this.liveMoreDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.liveMoreDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLotteryDialog() {
        if (this.mLotteryDialog == null) {
            this.mLotteryDialog = LotteryDialog.newInstance(this.liveId, new LotteryDialog.OnLotteryDialogCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.40
                @Override // com.tomatolive.library.p136ui.view.dialog.LotteryDialog.OnLotteryDialogCallback
                public void onClickAnchorAvatarListener(AnchorEntity anchorEntity) {
                    TomatoLiveFragment.this.startActivityById(anchorEntity.liveId);
                }

                @Override // com.tomatolive.library.p136ui.view.dialog.LotteryDialog.OnLotteryDialogCallback
                public void onClickUserAvatarListener(UserEntity userEntity) {
                    TomatoLiveFragment.this.showUserCard(userEntity);
                }

                @Override // com.tomatolive.library.p136ui.view.dialog.LotteryDialog.OnLotteryDialogCallback
                public void onJumpBackpackDialogListener() {
                    if (TomatoLiveFragment.this.giftBottomDialog != null) {
                        TomatoLiveFragment.this.getUserBalance(0);
                        TomatoLiveFragment.this.isLotteryDialogFlag = true;
                        TomatoLiveFragment.this.giftBottomDialog.initTagSelector(false);
                        TomatoLiveFragment.this.giftBottomDialog.show(TomatoLiveFragment.this.getChildFragmentManager());
                    }
                }
            });
            this.mLotteryDialog.show(getChildFragmentManager());
        } else {
            this.bundleArgs = new Bundle();
            this.bundleArgs.putString(ConstantUtils.RESULT_ITEM, this.liveId);
            this.mLotteryDialog.setArguments(this.bundleArgs);
            showDialogFragment(this.mLotteryDialog);
        }
        getUserBalance(1);
    }

    private void showComponentsDialog() {
        if (this.componentsDialog == null) {
            this.componentsDialog = ComponentsDialog.newInstance(this.isLotteryBoomStatus, new ComponentsDialog.OnGameItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.41
                @Override // com.tomatolive.library.p136ui.view.dialog.ComponentsDialog.OnGameItemClickListener
                public void onItemClick(int i, ComponentsEntity componentsEntity) {
                    if (componentsEntity == null) {
                        return;
                    }
                    TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                    tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.componentsDialog);
                    if (componentsEntity.isCacheLotteryComponents()) {
                        TomatoLiveFragment.this.showLotteryDialog();
                    } else {
                        TomatoLiveFragment.this.showComponentsWebViewDialog(true, componentsEntity);
                    }
                }
            });
            this.componentsDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putBoolean(ConstantUtils.RESULT_FLAG, this.isLotteryBoomStatus);
        this.componentsDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.componentsDialog);
    }

    private void showWebViewH5Dialog(BannerEntity bannerEntity) {
        this.webViewDialog = WebViewDialog.newInstance(bannerEntity.name, bannerEntity.url);
        this.webViewDialog.show(getChildFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPKRankDialog() {
        if (this.pkRankDialog == null) {
            this.pkRankDialog = PKRankDialog.newInstance(this.liveId, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.42
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                    super.onAnchorItemClickListener(anchorEntity);
                    TomatoLiveFragment.this.showUserCard(AppUtils.formatUserEntity(anchorEntity));
                }
            });
            this.pkRankDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putString(ConstantUtils.RESULT_ID, this.liveId);
        this.pkRankDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.pkRankDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQMInteractUserDialog() {
        SimpleQMInteractCallback simpleQMInteractCallback = new SimpleQMInteractCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.43
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
            public void onSendInvitationListener(final String str, final String str2, final String str3, final String str4) {
                if (TomatoLiveFragment.this.isFirstGetMyBalanceGift) {
                    TomatoLiveFragment.this.isFirstGetMyBalanceGift = false;
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getUserOver(true, new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.43.1
                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onSuccess(MyAccountEntity myAccountEntity) {
                            TomatoLiveFragment.this.showQMInviteSureDialog(str, str2, str3, str4);
                        }

                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onError(int i, String str5) {
                            TomatoLiveFragment.this.showToast(R$string.fq_userover_loading_fail);
                        }
                    });
                    return;
                }
                TomatoLiveFragment.this.showQMInviteSureDialog(str, str2, str3, str4);
            }
        };
        if (this.qmInteractUserDialog == null) {
            this.qmInteractUserDialog = QMInteractUserDialog.newInstance(this.liveId, this.anchorId, simpleQMInteractCallback);
            this.qmInteractUserDialog.show(getChildFragmentManager());
        } else {
            this.bundleArgs = new Bundle();
            this.bundleArgs.putString(ConstantUtils.RESULT_ID, this.liveId);
            this.bundleArgs.putString(ConstantUtils.RESULT_ITEM, this.anchorId);
            this.qmInteractUserDialog.setArguments(this.bundleArgs);
            this.qmInteractUserDialog.setOnQMInteractCallback(simpleQMInteractCallback);
            showDialogFragment(this.qmInteractUserDialog);
        }
        SysConfigInfoManager.getInstance().setEnableQMInteractRedDot(false);
        updateMoreRedDot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQMInviteSureDialog(final String str, final String str2, final String str3, final String str4) {
        if (this.myPriceBalance <= 0.0d) {
            AppUtils.onRechargeListener(this.mContext);
        } else if (NumberUtils.mul(NumberUtils.string2Double(str3), NumberUtils.string2Double(str4)) > this.myPriceBalance) {
            AppUtils.onRechargeListener(this.mContext);
        } else {
            this.qmInviteSureDialog = SureCancelDialog.newInstance(null, this.mContext.getString(R$string.fq_qm_invite_sure_tips), this.mContext.getString(R$string.fq_btn_cancel), this.mContext.getString(R$string.fq_qm_sure_send), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.44
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                }
            }, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.45
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).sendQMInteractInviteRequest(TomatoLiveFragment.this.liveId, str, str2, str3, str4, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.45.1
                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onSuccess(Object obj) {
                            TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                            if (tomatoLiveFragment.isDialogFragmentAdded(tomatoLiveFragment.qmInteractUserDialog)) {
                                TomatoLiveFragment.this.qmInteractUserDialog.resetTask();
                            }
                            TomatoLiveFragment.this.showToast(R$string.fq_qm_interact_task_publish_success);
                            TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                            tomatoLiveFragment2.dismissDialogFragment(tomatoLiveFragment2.qmInteractUserDialog);
                        }

                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onError(int i, String str5) {
                            if (TextUtils.equals(String.valueOf(i), ConstantUtils.GIFT_NEED_UPDATE)) {
                                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                                if (!tomatoLiveFragment.isDialogFragmentAdded(tomatoLiveFragment.qmInteractUserDialog)) {
                                    return;
                                }
                                TomatoLiveFragment.this.qmInteractUserDialog.sendGiftRequest(true);
                            }
                        }
                    });
                }
            });
            this.qmInviteSureDialog.show(getChildFragmentManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQMInteractTaskListDialog(QMInteractTaskEntity qMInteractTaskEntity) {
        SimpleQMInteractCallback simpleQMInteractCallback = new SimpleQMInteractCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.46
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
            public void onBackQMInteractConfigListener() {
                super.onBackQMInteractConfigListener();
                if (!TomatoLiveFragment.this.isLiveEnd()) {
                    if (!TomatoLiveFragment.this.mLiveAdBannerBottomView.isOneselfInitiateTask()) {
                        ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).sendUserPendingTaskRequest(TomatoLiveFragment.this.liveId, new ResultCallBack<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.46.1
                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onSuccess(QMInteractTaskEntity qMInteractTaskEntity2) {
                                TomatoLiveFragment.this.showToast(R$string.fq_qm_start_task_wait_tips);
                            }

                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onError(int i, String str) {
                                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                                tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.qmTaskListUserDialog);
                                TomatoLiveFragment.this.showQMInteractUserDialog();
                            }
                        });
                        return;
                    } else {
                        TomatoLiveFragment.this.showToast(R$string.fq_qm_start_task_wait_tips);
                        return;
                    }
                }
                TomatoLiveFragment.this.showToast(R$string.fq_qm_live_end_start_task_tips);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
            public void onSendGiftListener(final GiftDownloadItemEntity giftDownloadItemEntity) {
                super.onSendGiftListener(giftDownloadItemEntity);
                if (!TomatoLiveFragment.this.isCanSendGift()) {
                    return;
                }
                if (TomatoLiveFragment.this.isFirstGetMyBalanceGift) {
                    TomatoLiveFragment.this.isFirstGetMyBalanceGift = false;
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getUserOver(true, new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.46.2
                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onSuccess(MyAccountEntity myAccountEntity) {
                            TomatoLiveFragment.this.sendGift(giftDownloadItemEntity);
                        }

                        @Override // com.tomatolive.library.http.ResultCallBack
                        public void onError(int i, String str) {
                            TomatoLiveFragment.this.showToast(R$string.fq_userover_loading_fail);
                        }
                    });
                    return;
                }
                TomatoLiveFragment.this.sendGift(giftDownloadItemEntity);
            }
        };
        if (this.qmTaskListUserDialog == null) {
            this.qmTaskListUserDialog = QMTaskListUserDialog.newInstance(this.liveId, qMInteractTaskEntity, simpleQMInteractCallback);
            this.qmTaskListUserDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putString(ConstantUtils.RESULT_ID, this.liveId);
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, qMInteractTaskEntity);
        this.qmTaskListUserDialog.setArguments(this.bundleArgs);
        this.qmTaskListUserDialog.setOnQMInteractCallback(simpleQMInteractCallback);
        showDialogFragment(this.qmTaskListUserDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadHdLotteryDrawInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, long j, String str10) {
        if (TextUtils.equals("1", str10)) {
            this.mLiveAdBannerBottomView.addHdLotteryWindowView(false, str, j);
            UserEntity userEntity = this.myUserInfoEntity;
            int partHdLotteryCount = userEntity == null ? 0 : userEntity.getPartHdLotteryCount();
            AnchorEntity anchorEntity = this.anchorItemEntity;
            this.hdLotteryDrawingDialog.initDrawInfo(str, str2, str3, str4, str5, str6, str7, str8, str9, j, anchorEntity != null ? anchorEntity.nickname : "", partHdLotteryCount);
            return;
        }
        this.mLiveAdBannerBottomView.onLotteryEnd();
        if (TextUtils.equals("3", str10)) {
            this.hdLotteryDrawingDialog.onCompleteLottery(str);
        } else {
            this.hdLotteryDrawingDialog.onStartTimerLotteryEnd(str);
        }
    }

    public void sendHdLotteryGift(GiftDownloadItemEntity giftDownloadItemEntity, String str) {
        if (this.myPriceBalance <= 0.0d) {
            AppUtils.onRechargeListener(this.mContext);
            return;
        }
        if (TextUtils.equals("2", str) && !AppUtils.isAttentionAnchor(this.anchorId)) {
            attentionAnchorAction(true, this.anchorId);
        }
        sendGift(giftDownloadItemEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHdLotteryDrawDialog() {
        HdLotteryDrawingDialog hdLotteryDrawingDialog = this.hdLotteryDrawingDialog;
        if (hdLotteryDrawingDialog != null) {
            hdLotteryDrawingDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPayLiveEvaluationDialog() {
        if (this.liveEndEvaluationDialog == null) {
            this.liveEndEvaluationDialog = LiveEndEvaluationDialog.newInstance(this.liveId, this.liveCount, this.chargeType, this.livingStartTime, this.ticketPrice);
            this.liveEndEvaluationDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putString("liveId", this.liveId);
        this.bundleArgs.putString("liveCount", this.liveCount);
        this.bundleArgs.putString(LiveEndEvaluationDialog.CHARGE_TYPE, this.chargeType);
        this.bundleArgs.putLong(LiveEndEvaluationDialog.WATCH_DURATION, this.livingStartTime);
        this.bundleArgs.putString(LiveEndEvaluationDialog.TICKET_PRICE, this.ticketPrice);
        this.liveEndEvaluationDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.liveEndEvaluationDialog);
    }

    private void showDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (baseRxDialogFragment == null || baseRxDialogFragment.isAdded()) {
            return;
        }
        baseRxDialogFragment.show(getChildFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (baseRxDialogFragment == null || !baseRxDialogFragment.isAdded()) {
            return;
        }
        baseRxDialogFragment.dismiss();
    }

    private void dismissDialogFragment(BaseRxDialogFragment... baseRxDialogFragmentArr) {
        for (BaseRxDialogFragment baseRxDialogFragment : baseRxDialogFragmentArr) {
            if (baseRxDialogFragment != null && baseRxDialogFragment.isAdded()) {
                baseRxDialogFragment.dismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        return baseRxDialogFragment != null && baseRxDialogFragment.isResumed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDialogFragmentAdded(BaseRxDialogFragment baseRxDialogFragment) {
        return baseRxDialogFragment != null && baseRxDialogFragment.isAdded();
    }

    private void noticeClickToActivity(final String str) {
        RxTimerUtils.getInstance().timer(this.mContext, 200L, TimeUnit.MILLISECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$xjbSLMzM6-FupaXCDxJqi-IOigM
            @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
            public final void action(long j) {
                TomatoLiveFragment.this.lambda$noticeClickToActivity$79$TomatoLiveFragment(str, j);
            }
        });
    }

    public /* synthetic */ void lambda$noticeClickToActivity$79$TomatoLiveFragment(String str, long j) {
        startActivityById(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onAdBannerClick */
    public void lambda$initListener$11$TomatoLiveFragment(BannerEntity bannerEntity) {
        if (bannerEntity == null) {
            return;
        }
        if (bannerEntity.isLiveSDKCallback()) {
            AppUtils.onCustomAdBannerClickListener(this.mContext, bannerEntity.url);
        } else if (bannerEntity.isGameComponents()) {
            ComponentsEntity localCacheComponentsById = CacheUtils.getLocalCacheComponentsById(bannerEntity.componentId);
            if (localCacheComponentsById != null && localCacheComponentsById.isCacheLotteryComponents()) {
                showLotteryDialog();
            } else {
                showComponentsWebViewDialog(true, localCacheComponentsById);
            }
        } else if (bannerEntity.isJumpLiveRoom()) {
            startActivityById(bannerEntity.url);
        } else if (!bannerEntity.isJumpWebUrl()) {
        } else {
            if (bannerEntity.isJumpCustomUrl()) {
                AppUtils.onSysWebView(this.mContext, bannerEntity.url);
            } else {
                showWebViewH5Dialog(bannerEntity);
            }
        }
    }

    private void onLotteryBoomOpen(String str, int i, int i2, int i3, int i4) {
        this.isLotteryBoomStatus = true;
        ComponentsEntity componentsEntity = this.cacheRecommendComponents;
        if (componentsEntity != null && componentsEntity.isCacheLotteryComponents()) {
            this.ivRecommendComponents.onLotteryBoomOpen(str, i, i2, i3, i4);
        } else {
            this.ivComponentsView.onLotteryBoomOpen(str, i, i2, i3, i4);
        }
    }

    private void onLotteryBoomClosed() {
        this.isLotteryBoomStatus = false;
        ComponentsEntity componentsEntity = this.cacheRecommendComponents;
        if (componentsEntity != null && componentsEntity.isCacheLotteryComponents()) {
            this.ivRecommendComponents.onLotteryBoomClosed();
        } else {
            this.ivComponentsView.onLotteryBoomClosed();
        }
    }

    private boolean isLuxuryBoomStatus() {
        ComponentsEntity componentsEntity = this.cacheRecommendComponents;
        return (componentsEntity == null || !componentsEntity.isCacheLotteryComponents()) ? this.ivComponentsView.isBoomStatus() && this.ivComponentsView.isLuxuryBoomType() : this.ivRecommendComponents.isBoomStatus() && this.ivRecommendComponents.isLuxuryBoomType();
    }

    private void adjustPkInfoViewLayout() {
        this.isEnablePK = true;
        this.mLivePusherInfoView.setAdBannerViewVisibility(4);
        this.rootView.setBackgroundResource(R$drawable.fq_ic_pk_main_bottom_bg);
        ReSizeUtils.reSizeAudienceViewSmall(this.rootView.getChildAt(0), getContributionViewTopMarginHeight());
        this.mPKInfoView.adjustViewLayout(ReSizeUtils.getPKVideoViewHeight(), getPKViewTopMarginHeight());
    }

    private void stopLinkMicPk() {
        stopPKTimerDisposable();
        this.mLivePusherInfoView.setAdBannerViewVisibility(0);
        this.linkMicPKType.set(288);
        ReSizeUtils.reAudienceSizeViewBig(this.rootView.getChildAt(0));
        this.rootView.setBackgroundResource(0);
        this.mPKInfoView.onRelease();
        this.isEnablePK = false;
    }

    private int getContributionViewTopMarginHeight() {
        return this.mLivePusherInfoView.getContributionViewTopMarginHeight();
    }

    private int getPKViewTopMarginHeight() {
        return getContributionViewTopMarginHeight() - this.titleTopView.getHeight();
    }

    private void watchRecordReport() {
        if (AppUtils.isConsumptionPermissionUser() && this.livingStartTime != 0 && isLiving()) {
            this.livingEndTime = System.currentTimeMillis();
            long j = ((this.livingEndTime - this.livingStartTime) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
            if (j < 1) {
                return;
            }
            ((TomatoLivePresenter) this.mPresenter).watchHistoryReport(this.liveId, j);
        }
    }

    private boolean isPayLiveNeedBuyTicket() {
        LiveEntity liveEntity = this.liveListItemEntity;
        return liveEntity != null && liveEntity.isPayLiveNeedBuyTicket();
    }

    private boolean isPayLiveTicket() {
        LiveEntity liveEntity = this.liveListItemEntity;
        return liveEntity != null && liveEntity.isPayLiveTicket();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLiving() {
        return this.liveStatus;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLiveEnd() {
        return this.isLiveEnd;
    }

    private void updateAnchorContribution(final SocketMessageEvent.ResultData resultData) {
        if (resultData == null) {
            return;
        }
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$pEAjH4T8LCpMp2mIBLfZMDn6tyw
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$updateAnchorContribution$80$TomatoLiveFragment(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$updateAnchorContribution$80$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setAnchorContribution(AppUtils.formatContributionValue(resultData));
    }

    private void onNoEnterLivePermission(String str) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            onFinishActivity();
            if (TomatoLiveSDK.getSingleton().sdkCallbackListener.onEnterLivePermissionListener(this.mContext, 2)) {
                return;
            }
            showToast(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishActivity() {
        stopSocket();
        this.mActivity.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i, boolean z) {
        int i2 = 0;
        this.ivClosed.setVisibility(z ? 0 : 4);
        this.mRlControllerView.setVisibility(i == 256 ? 0 : 4);
        this.rlWatermarkShadowBg.setVisibility(i == 256 ? 0 : 4);
        this.mLiveEndInfoView.setVisibility(i == 257 ? 0 : 4);
        LivePayEnterView livePayEnterView = this.livePayEnterView;
        if (i != 259) {
            i2 = 4;
        }
        livePayEnterView.setVisibility(i2);
    }

    private void stopPKTimerDisposable() {
        Disposable disposable = this.mPKTimerDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mPKTimerDisposable.dispose();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewPagerScrollEnable(boolean z) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.onFragmentInteractionListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.setViewPagerScroll(z);
        }
    }

    private void initWatermarkConfig() {
        WatermarkConfigEntity watermarkConfig = SysConfigInfoManager.getInstance().getWatermarkConfig();
        if (watermarkConfig == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(watermarkConfig.platform);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        if (watermarkConfig.isEnableLiveRoom()) {
            sb.append(getString(R$string.fq_live_room_num, this.liveId));
        }
        if (watermarkConfig.isEnableDate()) {
            if (watermarkConfig.isEnableLiveRoom()) {
                sb.append(" | ");
            }
            sb.append(DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DEFAULT_1));
        }
        this.tvWatermarkTitle.setText(sb.toString());
        this.tvWatermarkUrl.setText(watermarkConfig.downloadUrl);
        if (TextUtils.isEmpty(watermarkConfig.logoUrl)) {
            return;
        }
        GlideUtils.loadImage(this.mContext, this.ivWatermarkLogo, watermarkConfig.logoUrl, R$drawable.fq_ic_live_watermark);
    }

    private void uploadLeaveLiveEvent() {
        LogEventUtils.shutDownTimerTask(LogConstants.LEAVE_ROOM_EVENT_NAME, false);
        if (this.livingStartTime != 0 && isLiving()) {
            this.livingEndTime = System.currentTimeMillis();
            long j = ((this.livingEndTime - this.livingStartTime) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
            if (j < 1) {
                return;
            }
            LogEventUtils.uploadLeaveRoom(this.anchorItemEntity, String.valueOf(j), this.liveId, this.myUserInfoEntity.expGrade);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveLiveUploadData() {
        LiveDataEntity liveDataEntity = new LiveDataEntity();
        liveDataEntity.liveId = this.liveId;
        AnchorEntity anchorEntity = this.anchorItemEntity;
        liveDataEntity.anchorId = anchorEntity.openId;
        liveDataEntity.appId = anchorEntity.appId;
        liveDataEntity.endTime = System.currentTimeMillis();
        liveDataEntity.startTime = this.livingStartTime;
        AnchorEntity anchorEntity2 = this.anchorItemEntity;
        liveDataEntity.expGrade = anchorEntity2.expGrade;
        liveDataEntity.nickname = anchorEntity2.nickname;
        liveDataEntity.tag = anchorEntity2.tag;
        liveDataEntity.viewerLevel = this.myUserInfoEntity.expGrade;
        DBUtils.saveOrUpdateLiveData(liveDataEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserBalance(String str) {
        this.myPriceBalance = NumberUtils.string2Double(str);
        if (this.myPriceBalance < 0.0d) {
            this.myPriceBalance = 0.0d;
        }
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.setUserBalance(this.myPriceBalance);
        }
        LotteryDialog lotteryDialog = this.mLotteryDialog;
        if (lotteryDialog != null) {
            lotteryDialog.setUserBalance(this.myPriceBalance);
        }
    }

    private void addPrivateMsgData(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        final MsgDetailListEntity msgDetailListEntity = new MsgDetailListEntity();
        msgDetailListEntity.userId = UserInfoManager.getInstance().getUserId();
        msgDetailListEntity.targetId = str2;
        msgDetailListEntity.targetAvatar = str3;
        msgDetailListEntity.msg = str5;
        msgDetailListEntity.type = 2;
        msgDetailListEntity.targetName = str4;
        msgDetailListEntity.time = String.valueOf(System.currentTimeMillis());
        msgDetailListEntity.status = 1;
        msgDetailListEntity.winningFlag = str6;
        msgDetailListEntity.flagContent = str7;
        msgDetailListEntity.messageId = str;
        DBUtils.saveOnePrivateMsgDetail(msgDetailListEntity);
        final MsgListEntity msgListEntity = new MsgListEntity();
        msgListEntity.userId = UserInfoManager.getInstance().getUserId();
        msgListEntity.appId = UserInfoManager.getInstance().getAppId();
        msgListEntity.targetId = str2;
        msgListEntity.time = String.valueOf(System.currentTimeMillis());
        DBUtils.saveOrUpdateMsgList(msgListEntity);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$X1xEd5ptdocLVoIMFvshUSIQNWA
            @Override // java.lang.Runnable
            public final void run() {
                TomatoLiveFragment.this.lambda$addPrivateMsgData$81$TomatoLiveFragment(msgListEntity, msgDetailListEntity);
            }
        });
    }

    public /* synthetic */ void lambda$addPrivateMsgData$81$TomatoLiveFragment(MsgListEntity msgListEntity, MsgDetailListEntity msgDetailListEntity) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog != null && privateMsgDialog.isAdded()) {
            this.privateMsgDialog.dealMsg(msgListEntity, msgDetailListEntity);
            return;
        }
        MsgStatusEntity msgStatusEntity = new MsgStatusEntity();
        msgStatusEntity.appId = UserInfoManager.getInstance().getAppId();
        msgStatusEntity.userId = UserInfoManager.getInstance().getUserId();
        msgStatusEntity.readStatus = "0";
        DBUtils.saveOrUpdateMsgStatus(msgStatusEntity);
        updateMoreRedDot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPayLiveTips() {
        this.mLivePusherInfoView.setChargeTypeTips(isPayLiveTicket(), this.ticketPrice);
        if (!SysConfigInfoManager.getInstance().isEnableRatingGuide() || !isPayLiveTicket() || !this.showGuideRating) {
            return;
        }
        this.mLivePusherInfoView.showGuideRating(this.mActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void payLiveTipsDialogOnRelease() {
        PayLiveTipsDialog payLiveTipsDialog = this.payLiveTipsDialog;
        if (payLiveTipsDialog != null) {
            this.isPayLiveTipsDialog = false;
            payLiveTipsDialog.compositeDisposableClear();
            dismissDialogFragment(this.payLiveTipsDialog);
            this.payLiveTipsDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePullStreamUrl() {
        this.pullStreamUrl = this.liveListItemEntity.getDefPullStreamUrlStr();
        this.pullStreamUrlList.clear();
        this.pullStreamUrlList.addAll(this.liveListItemEntity.getPullStreamUrlList());
    }

    private void updateMoreRedDot() {
        if (this.qBadgeView != null) {
            this.qBadgeView.setBadgeNumber((DBUtils.isUnReadBoolean() || SysConfigInfoManager.getInstance().isEnableQMInteractRedDot()) ? -1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: addQMNoticeAnimView */
    public void lambda$dealIntimateTask$65$TomatoLiveFragment(SocketMessageEvent.ResultData resultData) {
        if (this.qmNoticeAnimView == null) {
            this.qmNoticeAnimView = new QMNoticeAnimView(this.mContext);
            this.qmNoticeAnimView.setOnQMInteractCallback(new SimpleQMInteractCallback() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveFragment.47
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onTaskStatusUpdateListener(String str, String str2) {
                    super.onTaskStatusUpdateListener(str, str2);
                    onNoticeAnimViewDismissListener();
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onNoticeAnimViewDismissListener() {
                    super.onNoticeAnimViewDismissListener();
                    TomatoLiveFragment.this.canShowIntimateNotice.set(true);
                    if (!TomatoLiveFragment.this.intimateTaskQueue.isEmpty()) {
                        TomatoLiveFragment.this.sendWorkHandlerEmptyMessage(ConstantUtils.INTIMATE_TASK_NOTICE);
                    }
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onUserCardListener(String str) {
                    super.onUserCardListener(str);
                    ((TomatoLivePresenter) ((BaseFragment) TomatoLiveFragment.this).mPresenter).getUserCardInfo(str);
                }
            });
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            this.rootView.addView(this.qmNoticeAnimView, layoutParams);
        }
        this.qmNoticeAnimView.showNoticeAnimView(false, resultData.putAvatar, resultData.putName, resultData.giftName, resultData.giftNum, resultData.taskName, resultData.putUserId, resultData.taskId);
    }

    private void clearQMNoticeAnimView() {
        QMNoticeAnimView qMNoticeAnimView = this.qmNoticeAnimView;
        if (qMNoticeAnimView != null) {
            qMNoticeAnimView.onRelease();
            this.rootView.removeView(this.qmNoticeAnimView);
            this.qmNoticeAnimView = null;
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof LoginEvent) {
            this.isLoginRequest = true;
            ((TomatoLivePresenter) this.mPresenter).getUserOver();
            if (isPayLiveNeedBuyTicket()) {
                ((TomatoLivePresenter) this.mPresenter).onUserCheckTicket(this.liveId, false);
                return;
            } else {
                sendLiveInitInfoRequest();
                return;
            }
        }
        if (baseEvent instanceof UpdateBalanceEvent) {
            ((TomatoLivePresenter) this.mPresenter).getUserOver();
        }
        if (!(baseEvent instanceof NobilityOpenEvent)) {
            return;
        }
        NobilityOpenEvent nobilityOpenEvent = (NobilityOpenEvent) baseEvent;
        if (!nobilityOpenEvent.isOpenSuccess) {
            return;
        }
        showToast(nobilityOpenEvent.toastTips);
        updateUserBalance(nobilityOpenEvent.accountBalance);
    }

    public void onNetNone() {
        RxTimerUtils.getInstance().timerBindDestroyFragment(getLifecycleProvider(), 2L, TimeUnit.SECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$5XChLDmeOI33puhOg234e8vJ0EI
            @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
            public final void action(long j) {
                TomatoLiveFragment.this.lambda$onNetNone$82$TomatoLiveFragment(j);
            }
        });
    }

    public /* synthetic */ void lambda$onNetNone$82$TomatoLiveFragment(long j) {
        if (!NetUtils.isNetworkAvailable() && !this.isShowRetryRoomInfo && !this.isStartGetRoomInfo) {
            dealPlayError();
            showToast(R$string.fq_text_no_network);
        }
    }

    public void on4G() {
        NetworkPromptDialog.newInstance(getResources().getString(R$string.fq_text_mobile_net), getResources().getString(R$string.fq_text_go_on), getResources().getString(R$string.fq_text_stop), $$Lambda$TomatoLiveFragment$sbzdA0Np5XL6HAGJ3I0pCpWpaA.INSTANCE, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveFragment$6DZv1uzm34mBZlKjHIdgwuDpKnc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TomatoLiveFragment.this.lambda$on4G$84$TomatoLiveFragment(view);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$on4G$84$TomatoLiveFragment(View view) {
        onFinishActivity();
    }

    private void resetAllField() {
        this.isShowRetryRoomInfo = false;
        this.liveId = "";
        this.liveCount = "";
        this.pullStreamUrl = "";
        this.luckNoticeLiveId = "";
        this.giftNoticeLiveId = "";
        this.curAnchorInfoNoticeEntity = null;
        this.curBigAnimSendUserId = "";
        this.asleep = true;
        this.lastMsg = "";
        this.liveStatus = false;
        this.canShowGiftNotice.set(true);
        this.canShowAnchorInfoNotice.set(true);
        this.canShowGameNotice.set(true);
        this.canShowEnterMsg.set(true);
        this.canShowGuardEnterMsg.set(true);
        this.canShowSysNotice.set(true);
        this.canShowLuckNotice.set(true);
        this.carFullAnimFinish.set(true);
        this.canShowIntimateNotice.set(true);
        this.isPausing = false;
        this.isLiveEnd = false;
        this.isNormalBan = false;
        this.isAllBan = false;
        this.isSuperBan = false;
        this.reConnectCountOver = false;
        this.isStartGetAnchorInfo = false;
        this.isStartGetRoomInfo = false;
        this.isGiftListUpdating = false;
        this.getUserBalanceFail = false;
        this.isGetGiftListFail = false;
        this.startGetGiftListInfo = false;
        this.isContinueCombo = false;
        this.isSocketReConn = false;
        this.isTaskSocket = false;
        this.isFirstLoadTask = true;
        this.isConnectingChatService = true;
        this.isSocketClose = true;
        this.isSocketError = true;
        this.postIntervalTimes = 1;
        this.clickCount.set(0);
        this.curTrumpetCount.set(0);
        this.countDownTime = this.postIntervalTimes * 3;
        this.myPriceBalance = 0.0d;
        this.myScoreBalance = 0.0d;
        this.onLineCount.set(0L);
        this.speakLevel = "1";
        this.myUserInfoEntity = null;
        this.guardItemEntity = null;
        this.anchorItemEntity = null;
        this.isFirstGetMyBalanceLottery = true;
        this.isFirstGetMyBalanceGift = true;
        this.livingStartTime = 0L;
        this.livingEndTime = 0L;
        this.speakTotalCount.set(0L);
        this.chargeType = "0";
        this.isPayLive = false;
        this.isBuyTicket = false;
        this.isPayLiveTipsDialog = false;
        this.showGuideRating = true;
        this.ticketPrice = "0";
        this.isFirstInitPlayManager = false;
        this.isLotteryBoomStatus = false;
        this.isLoginRequest = false;
        this.uploadDataEnterSource = getString(R$string.fq_hot_list);
    }

    private void onReleaseViewData() {
        watchRecordReport();
        uploadLeaveLiveEvent();
        if (isLiving()) {
            ((TomatoLivePresenter) this.mPresenter).setEnterOrLeaveLiveRoomMsg("leave");
        }
        SPUtils.getInstance().put(ConstantUtils.NOTICE_GAME_KEY, true);
        this.swipeAnimationController.resetClearScreen();
        showContentView(258, true);
        showLiveLoadingView(4);
        this.mLiveAdBannerBottomView.setTaskBoxVisibility(4);
        resetAllField();
        HandlerUtils.getInstance().stopIOThread();
        stopSocket();
        clearAllMapData();
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        Handler handler2 = this.workHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        dismissDialogs();
        GiftBoxView giftBoxView = this.mGiftBoxView;
        if (giftBoxView != null) {
            giftBoxView.release();
        }
        this.mLiveAdBannerBottomView.releaseForTaskBox();
        this.mLiveAdBannerBottomView.onReleaseTopBanner();
        TaskBottomDialog taskBottomDialog = this.mTaskBottomDialog;
        if (taskBottomDialog != null) {
            taskBottomDialog.onDestroy();
            this.mTaskBottomDialog = null;
        }
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onRelease();
        }
        LiveChatMsgView liveChatMsgView = this.mLiveChatMsgView;
        if (liveChatMsgView != null) {
            liveChatMsgView.onRelease();
        }
        LivePayEnterView livePayEnterView = this.livePayEnterView;
        if (livePayEnterView != null) {
            livePayEnterView.onRelease();
        }
        LotteryDialog lotteryDialog = this.mLotteryDialog;
        if (lotteryDialog != null) {
            lotteryDialog.onRelease();
            this.mLotteryDialog = null;
        }
        onLotteryBoomClosed();
        cancelPullTimeOut();
        ((TomatoLivePresenter) this.mPresenter).clearCompositeDisposable();
        Disposable disposable = this.cdDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.cdDisposable.dispose();
            this.cdDisposable = null;
        }
        GiftBackpackDialog giftBackpackDialog = this.giftBottomDialog;
        if (giftBackpackDialog != null) {
            giftBackpackDialog.release();
            this.giftBottomDialog = null;
        }
        ComponentsWebViewDialog componentsWebViewDialog = this.componentsWebViewDialog;
        if (componentsWebViewDialog != null) {
            componentsWebViewDialog.onRelease();
        }
        HdLotteryDrawingDialog hdLotteryDrawingDialog = this.hdLotteryDrawingDialog;
        if (hdLotteryDrawingDialog != null) {
            hdLotteryDrawingDialog.onReleaseData();
        }
        if (this.isEnablePK) {
            stopLinkMicPk();
        }
        LiveAnimationView liveAnimationView = this.liveAnimationView;
        if (liveAnimationView != null) {
            liveAnimationView.onDestroy();
        }
        if (this.liveEndEvaluationDialog != null) {
            this.liveEndEvaluationDialog = null;
        }
        if (this.qmInteractUserDialog != null) {
            this.qmInteractUserDialog = null;
        }
        clearQMNoticeAnimView();
        payLiveTipsDialogOnRelease();
    }

    private void clearAllMapData() {
        ConcurrentLinkedQueue<ChatEntity> concurrentLinkedQueue = this.receiveMsgQueue;
        if (concurrentLinkedQueue != null) {
            concurrentLinkedQueue.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue2 = this.enterMsgQueue;
        if (concurrentLinkedQueue2 != null) {
            concurrentLinkedQueue2.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue3 = this.guardEnterMsgQueue;
        if (concurrentLinkedQueue3 != null) {
            concurrentLinkedQueue3.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue4 = this.giftNoticeQueue;
        if (concurrentLinkedQueue4 != null) {
            concurrentLinkedQueue4.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue5 = this.sysNoticeQueue;
        if (concurrentLinkedQueue5 != null) {
            concurrentLinkedQueue5.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue6 = this.gameNoticeQueue;
        if (concurrentLinkedQueue6 != null) {
            concurrentLinkedQueue6.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue7 = this.anchorInfoNoticeQueue;
        if (concurrentLinkedQueue7 != null) {
            concurrentLinkedQueue7.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue8 = this.luckNoticeQueue;
        if (concurrentLinkedQueue8 != null) {
            concurrentLinkedQueue8.clear();
        }
        Map<String, GiftIndexEntity> map = this.myGiftIndexMap;
        if (map != null) {
            map.clear();
        }
        Map<String, GiftIndexEntity> map2 = this.myPropIndexMap;
        if (map2 != null) {
            map2.clear();
        }
        Map<String, Map<String, GiftIndexEntity>> map3 = this.receivePropMap;
        if (map3 != null) {
            map3.clear();
        }
        Map<String, Map<String, GiftIndexEntity>> map4 = this.receiveGiftMap;
        if (map4 != null) {
            map4.clear();
        }
        List<String> list = this.pullStreamUrlList;
        if (list != null) {
            list.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue9 = this.privateMsgQueue;
        if (concurrentLinkedQueue9 != null) {
            concurrentLinkedQueue9.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue10 = this.intimateTaskQueue;
        if (concurrentLinkedQueue10 != null) {
            concurrentLinkedQueue10.clear();
        }
    }

    public void onDestroyViewData() {
        onDestroyPlay();
        InputTextMsgForAudienceDialog inputTextMsgForAudienceDialog = this.mInputTextMsgDialog;
        if (inputTextMsgForAudienceDialog != null) {
            inputTextMsgForAudienceDialog.onDestroy();
            this.mInputTextMsgDialog = null;
        }
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onDestroy();
        }
        LiveLoadingView liveLoadingView = this.mLiveLoadingView;
        if (liveLoadingView != null) {
            liveLoadingView.onDestroy();
        }
        SwipeAnimationController swipeAnimationController = this.swipeAnimationController;
        if (swipeAnimationController != null) {
            swipeAnimationController.onDestroy();
        }
        LiveAdBannerBottomView liveAdBannerBottomView = this.mLiveAdBannerBottomView;
        if (liveAdBannerBottomView != null) {
            liveAdBannerBottomView.releaseWebView();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.PrivateMsgDialog.SendPrivateMsgListener
    public void sendPrivateMsg(MsgDetailListEntity msgDetailListEntity) {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendChatMessage(MessageHelper.convertToPrivateChatMsg(msgDetailListEntity));
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.PrivateMsgDialog.SendPrivateMsgListener
    public void onReConnSocket() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.resetCount();
            this.wsManager.reconnect();
        }
    }

    public void onFragmentPageChangeListener() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.clearAnimQueue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$MainHandler */
    /* loaded from: classes3.dex */
    public class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveFragment$UserCardCallback */
    /* loaded from: classes3.dex */
    public class UserCardCallback extends SimpleUserCardCallback {
        private boolean isAnchorUserCard;
        private boolean isCtrlTarget;
        private boolean isEmperorNobilityTarget;
        private boolean isShieldTarget;
        private boolean isYearGuardTarget;
        private String targetId;
        private String targetName;
        private int userDialogType;

        public UserCardCallback(String str, boolean z) {
            this.isAnchorUserCard = false;
            this.targetId = str;
            this.isAnchorUserCard = z;
        }

        public UserCardCallback(String str, String str2, int i, boolean z, boolean z2, boolean z3, boolean z4) {
            this.isAnchorUserCard = false;
            this.targetId = str;
            this.targetName = str2;
            this.userDialogType = i;
            this.isCtrlTarget = z;
            this.isShieldTarget = z2;
            this.isYearGuardTarget = z3;
            this.isEmperorNobilityTarget = z4;
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickAttentionListener(View view) {
            super.onClickAttentionListener(view);
            if (this.isAnchorUserCard) {
                TomatoLiveFragment.this.attentionAnchorAction(view, this.targetId);
            }
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickManageListener(View view) {
            super.onClickManageListener(view);
            if (!this.isAnchorUserCard) {
                TomatoLiveFragment.this.userAvatarDialogManager(this.userDialogType, this.isCtrlTarget, this.isShieldTarget, this.isYearGuardTarget, this.isEmperorNobilityTarget, this.targetName, this.targetId);
                return;
            }
            TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
            tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.anchorNobilityAvatarDialog);
            TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
            tomatoLiveFragment2.dismissDialogFragment(tomatoLiveFragment2.anchorAvatarDialog);
            TomatoLiveFragment.this.playManager.onScreenshot();
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickGuardListener() {
            super.onClickGuardListener();
            if (this.isAnchorUserCard) {
                TomatoLiveFragment tomatoLiveFragment = TomatoLiveFragment.this;
                tomatoLiveFragment.dismissDialogFragment(tomatoLiveFragment.anchorNobilityAvatarDialog);
                TomatoLiveFragment tomatoLiveFragment2 = TomatoLiveFragment.this;
                tomatoLiveFragment2.dismissDialogFragment(tomatoLiveFragment2.anchorAvatarDialog);
                TomatoLiveFragment tomatoLiveFragment3 = TomatoLiveFragment.this;
                tomatoLiveFragment3.showGuardListDialog(tomatoLiveFragment3.guardItemEntity);
            }
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickNobilityListener(View view) {
            super.onClickNobilityListener(view);
            TomatoLiveFragment.this.toNobilityOpenActivity();
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onUserAchieveListener(UserEntity userEntity, String str) {
            super.onUserAchieveListener(userEntity, str);
            TomatoLiveFragment.this.showUserAchieveDialog(userEntity, str);
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onGiftWallClickListener(AnchorEntity anchorEntity) {
            super.onGiftWallClickListener(anchorEntity);
            TomatoLiveFragment.this.showGiftWallDialog(anchorEntity);
        }
    }
}
