package com.tomatolive.library.p136ui.activity.live;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.rtmp.TXLivePusher;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.AnchorStartLiveEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.model.CoverEntity;
import com.tomatolive.library.model.GiftBatchItemEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftIndexEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveItemEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.RelationLastLiveEntity;
import com.tomatolive.library.model.SendMessageEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.model.StartLiveVerifyEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.WatermarkConfigEntity;
import com.tomatolive.library.model.p135db.AnchorLiveDataEntity;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.model.p135db.MsgListEntity;
import com.tomatolive.library.model.p135db.MsgStatusEntity;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter;
import com.tomatolive.library.p136ui.adapter.PopularCardAdapter;
import com.tomatolive.library.p136ui.interfaces.CommonConfirmClickListener;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.presenter.PrePareLivePresenter;
import com.tomatolive.library.p136ui.view.custom.AnchorLiveEndView;
import com.tomatolive.library.p136ui.view.custom.GiftBoxView;
import com.tomatolive.library.p136ui.view.custom.HdLotteryWindowView;
import com.tomatolive.library.p136ui.view.custom.LiveAdBannerBottomView;
import com.tomatolive.library.p136ui.view.custom.LiveAnimationView;
import com.tomatolive.library.p136ui.view.custom.LiveChatMsgView;
import com.tomatolive.library.p136ui.view.custom.LivePusherInfoView;
import com.tomatolive.library.p136ui.view.custom.PreStartLiveView;
import com.tomatolive.library.p136ui.view.custom.StickerAddView;
import com.tomatolive.library.p136ui.view.dialog.AnchorEndLiveDialog;
import com.tomatolive.library.p136ui.view.dialog.BeautyDialog;
import com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils;
import com.tomatolive.library.p136ui.view.dialog.ChargeTypeDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftBoxPresenterDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftRecordDialog;
import com.tomatolive.library.p136ui.view.dialog.GiftWallDialog;
import com.tomatolive.library.p136ui.view.dialog.GuardListDialog;
import com.tomatolive.library.p136ui.view.dialog.GuardOpenContentDialog;
import com.tomatolive.library.p136ui.view.dialog.InputTextMsgDialog;
import com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog;
import com.tomatolive.library.p136ui.view.dialog.NobilityHdRecommendDialog;
import com.tomatolive.library.p136ui.view.dialog.PayAudienceListDialog;
import com.tomatolive.library.p136ui.view.dialog.PopularCardDialog;
import com.tomatolive.library.p136ui.view.dialog.PrivateMsgDialog;
import com.tomatolive.library.p136ui.view.dialog.QMInteractDialog;
import com.tomatolive.library.p136ui.view.dialog.QMTaskListDialog;
import com.tomatolive.library.p136ui.view.dialog.UpdateTitleDialog;
import com.tomatolive.library.p136ui.view.dialog.UserAchieveDialog;
import com.tomatolive.library.p136ui.view.dialog.UserGuardAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNobilityAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNormalAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserSuperAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.AnchorOpenNoticeDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.CoverSettingDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.NetworkPromptDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.PermissionDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.TransDialog;
import com.tomatolive.library.p136ui.view.faceunity.BeautyControlView;
import com.tomatolive.library.p136ui.view.faceunity.FaceConstant;
import com.tomatolive.library.p136ui.view.gift.GiftAnimModel;
import com.tomatolive.library.p136ui.view.iview.IPrepareLiveView;
import com.tomatolive.library.p136ui.view.widget.QMNoticeAnimView;
import com.tomatolive.library.p136ui.view.widget.QMNoticeWindow;
import com.tomatolive.library.p136ui.view.widget.badgeView.QBadgeView;
import com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils;
import com.tomatolive.library.service.TokenDialogService;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.HandlerUtils;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.MD5Utils;
import com.tomatolive.library.utils.NetUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxTimerUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.SwipeAnimationController;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.TranslationUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.emoji.EmojiParser;
import com.tomatolive.library.utils.live.PushManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack;
import com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener;
import com.tomatolive.library.websocket.nvwebsocket.ConnectSocketParams;
import com.tomatolive.library.websocket.nvwebsocket.MessageHelper;
import com.tomatolive.library.websocket.nvwebsocket.WsManager;
import com.tomatolive.library.websocket.nvwebsocket.WsStatus;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* renamed from: com.tomatolive.library.ui.activity.live.PrepareLiveActivity */
/* loaded from: classes3.dex */
public class PrepareLiveActivity extends BaseActivity<PrePareLivePresenter> implements IPrepareLiveView, InputTextMsgDialog.OnTextSendListener, BackgroundSocketCallBack, PrivateMsgDialog.SendPrivateMsgListener {
    private static final long DURATION_GET_MESSAGE = 1000;
    private static final int MAX_GET_ITEM_NUM_ONCE = 5;
    private static final int MAX_ITEM_NUM = 10000;
    private static final long PUASE_TIME = 80;
    private UserNormalAvatarDialog anchorAvatarDialog;
    private AnchorLiveEndView anchorLiveEndView;
    private UserNobilityAvatarDialog anchorNobilityAvatarDialog;
    private BeautyDialog beautyDialog;
    private Dialog bottomMoreDialog;
    private Bundle bundleArgs;
    private ChargeTypeDialog chargeTypeDialog;
    private String chatContent;
    private AnimatorSet countDownAnimSet;
    private String defaultSpeakLevel;
    private String enableLiveNotify;
    private WarnDialog endWarnDialog;
    private RelativeLayout faceRoot;
    private GiftRecordDialog giftRecordDialog;
    private GiftWallDialog giftWallDialog;
    private GuardItemEntity guardItemEntity;
    private GuardListDialog guardListDialog;
    private GuardOpenContentDialog guardOpenContentDialog;
    private String isAllowTicket;
    private boolean isBanPostAll;
    private boolean isBtnStartClick;
    private boolean isCameraOpenFail;
    private boolean isCameraPermissions;
    private volatile boolean isLiving;
    private boolean isMicPermissions;
    private boolean isNoNetEvent;
    private volatile boolean isPushInBackground;
    private boolean isPushSuc;
    private boolean isReConnectStatus;
    private String isRelation;
    private boolean isStartGetAnchorInfo;
    private boolean isStartGiftUpdate;
    private boolean isStartPopularCard;
    private boolean isSuperAdmin;
    private volatile boolean isTranOpen;
    private boolean isWarn;
    private ImageView ivPrivateMsg;
    private ImageView ivTrans;
    private ImageView ivWatermarkLogo;
    private int lastBeautyProgress;
    private boolean lastMirrorOpen;
    private int lastRuddyProgress;
    private int lastWhiteProgress;
    private LiveAnimationView liveAnimationView;
    private RelativeLayout liveBottomBar;
    private LiveChatMsgView liveChatMsgView;
    private int liveCount;
    private String liveId;
    private String liveLabel;
    private LinearLayout llChatRoot;
    private ImageView mAnchorCoverImg;
    private PermissionDialog mCameraDialog;
    private BeautyControlView mFaceUnityControlView;
    private GiftBoxView mGiftBoxView;
    private InputTextMsgDialog mInputTextMsgDialog;
    private LiveAdBannerBottomView mLiveAdBannerBottomView;
    private LivePusherInfoView mLivePusherInfoView;
    private Disposable mPopularDisposable;
    private PreStartLiveView mPreStartLiveView;
    private StickerAddView mStickerAddView;
    private Handler mainHandler;
    private List<String> myMarkUrls;
    private String myUserGrade;
    private SocketMessageEvent myselfEnterMessageEvent;
    private UserCardCallback onUserCardCallback;
    private Disposable pauseTimer;
    private PayAudienceListDialog payAudienceListDialog;
    private PopularCardDialog popularCardDialog;
    private PrivateMsgDialog privateMsgDialog;
    private PushManager pushManager;
    private String pushUrl;
    private QBadgeView qBadgeView;
    private QMInteractDialog qmInteractDialog;
    private QMNoticeAnimView qmNoticeAnimView;
    private QMTaskListDialog qmTaskListDialog;
    private boolean reConnectCountOver;
    private RelationLastLiveEntity relationLastLiveEntity;
    private RelativeLayout rlLiveRoot;
    private RelativeLayout rlWatermarkBg;
    private List<String> shieldedList;
    private RelativeLayout shootContainer;
    private String socketUrl;
    private AnchorStartLiveEntity startLiveInfoEntity;
    private Disposable startPlayAnimDisposable;
    private WarnDialog startWarnDialog;
    private ViewStub stickerViewStub;
    private String streamName;
    private SwipeAnimationController swipeAnimationController;
    private BaseQuickAdapter<MenuEntity, BaseViewHolder> tempAdapter;
    private MenuEntity tempFlashMenuEntity;
    private int tempPos;
    private String ticketPrice;
    private TransDialog transDialog;
    private TextView tvCloseBigSize;
    private TextView tvCountDown;
    private TextView tvPopularCard;
    private TextView tvTitle;
    private TextView tvWatermarkTitle;
    private TextView tvWatermarkUrl;
    private UserAchieveDialog userAchieveDialog;
    private UserNormalAvatarDialog userAvatarDialog;
    private UserGuardAvatarDialog userGuardAvatarDialog;
    private String userId;
    private UserNobilityAvatarDialog userNobilityAvatarDialog;
    private UserSuperAvatarDialog userSuperAvatarDialog;
    private Handler workHandler;
    private WsManager wsManager;
    private String speakCDTime = "1";
    private boolean isFontCamera = true;
    private AtomicLong onLineCount = new AtomicLong(0);
    private int reCount = 3;
    private String speakLevel = "1";
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
    private AtomicLong chatCount = new AtomicLong(0);
    private AtomicLong tempIncomePrice = new AtomicLong(0);
    private long giftTrumpetPlayPeriod = 9000;
    private long nobilityPlayPeriod = 6000;
    private long trumpetPlayPeriod = 5000;
    private final String enterType = "1";
    private boolean isConnectingChatService = true;
    private boolean msgSleep = true;
    private volatile boolean isShieldSmallGift = false;
    private boolean isSocketClose = true;
    private boolean isSocketError = true;
    private boolean isBanGroup = false;
    private Map<String, Map<String, GiftIndexEntity>> receiveGiftMap = new HashMap(128);
    private Map<String, Map<String, GiftIndexEntity>> receivePropMap = new HashMap(128);
    private ConcurrentLinkedQueue<ChatEntity> receiveMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> enterMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> guardEnterMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> giftNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> sysNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> anchorInfoNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> gameNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> privateMsgQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> luckNoticeQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<SocketMessageEvent.ResultData> intimateTaskQueue = new ConcurrentLinkedQueue<>();
    private long startLiveTimeMillis = 0;
    private String chargeType = "0";
    private Handler.Callback workCallBack = new Handler.Callback() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$WlwbFn2ijwccp8cA-GjX5blbfFc
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return PrepareLiveActivity.this.lambda$new$41$PrepareLiveActivity(message);
        }
    };
    Object synchronizedObj = new Object();

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onLiveEndFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onQMInteractShowTaskFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onTagListFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onUserCardInfoFail(int i, String str) {
    }

    static /* synthetic */ int access$110(PrepareLiveActivity prepareLiveActivity) {
        int i = prepareLiveActivity.reCount;
        prepareLiveActivity.reCount = i - 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public PrePareLivePresenter mo6636createPresenter() {
        return new PrePareLivePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_prepare_live;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.statusBarDarkFont(false);
        immersionBar.init();
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.workHandler = HandlerUtils.getInstance().startIOThread(PrepareLiveActivity.class.getName(), this.workCallBack);
        this.mainHandler = new Handler(Looper.getMainLooper());
        getLastSettingFormSp();
        initPrepareView();
        initPermission();
        initPushConfig();
        showPrepareView();
        initLiveView();
        getGiftRes();
        ((PrePareLivePresenter) this.mPresenter).getTagList();
        ((PrePareLivePresenter) this.mPresenter).getPreStartLiveInfo(true);
        ((PrePareLivePresenter) this.mPresenter).initLocalComponentsCache();
    }

    private void initPrepareView() {
        this.mFaceUnityControlView = (BeautyControlView) findViewById(R$id.faceunity_control);
        this.mAnchorCoverImg = (ImageView) findViewById(R$id.iv_anchor_cover);
        this.shootContainer = (RelativeLayout) findViewById(R$id.rl_prepare_live_root);
        this.faceRoot = (RelativeLayout) findViewById(R$id.fq_face_root);
        this.rlLiveRoot = (RelativeLayout) findViewById(R$id.rl_start_live_root);
        this.tvTitle = (TextView) findViewById(R$id.tv_anchor_live_title);
        this.tvPopularCard = (TextView) findViewById(R$id.tv_popular_card);
        this.tvCountDown = (TextView) findViewById(R$id.tv_down_count);
        this.mLivePusherInfoView = (LivePusherInfoView) findViewById(R$id.ll_pusher_info);
        this.mGiftBoxView = (GiftBoxView) findViewById(R$id.gift_box_view);
        this.anchorLiveEndView = (AnchorLiveEndView) findViewById(R$id.end_view);
        this.ivTrans = (ImageView) findViewById(R$id.iv_trans);
        this.mPreStartLiveView = (PreStartLiveView) findViewById(R$id.pre_start_live_view);
        this.ivTrans.setImageResource(R$drawable.fq_ic_anchor_bottom_translate_normal);
        this.mPreStartLiveView.setTMirrorDrawableTop(this.lastMirrorOpen ? R$drawable.fq_ic_anchor_mirror_selected : R$drawable.fq_ic_anchor_mirror);
        this.transDialog = TransDialog.newInstance(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$n7uPOMQ7mrXpvVj5kTfvzOYziY4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$initPrepareView$0$PrepareLiveActivity(view);
            }
        });
        this.mCameraDialog = PermissionDialog.newInstance(PermissionDialog.CAMERA_TIP);
    }

    public /* synthetic */ void lambda$initPrepareView$0$PrepareLiveActivity(View view) {
        this.isTranOpen = true;
        this.ivTrans.setImageResource(R$drawable.fq_ic_anchor_bottom_translate_selected);
    }

    private void initPermission() {
        new RxPermissions(this).requestEach("android.permission.CAMERA", "android.permission.RECORD_AUDIO").compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$teoqk3XluUW1H8tPCUQ3TeLeK5s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PrepareLiveActivity.this.lambda$initPermission$1$PrepareLiveActivity((Permission) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initPermission$1$PrepareLiveActivity(Permission permission) throws Exception {
        if (permission.name.equals("android.permission.CAMERA")) {
            if (permission.granted) {
                this.isCameraPermissions = true;
            } else {
                this.isCameraPermissions = false;
            }
        }
        if (permission.name.equals("android.permission.RECORD_AUDIO")) {
            this.isMicPermissions = permission.granted;
        }
    }

    private void initPushConfig() {
        this.pushManager = new PushManager(this);
        this.pushManager.initPushConfig(this.shootContainer);
        enableBeautyFilter();
        this.pushManager.setOnPushListener(new C39321());
        lambda$null$5$PrepareLiveActivity();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.PrepareLiveActivity$1 */
    /* loaded from: classes3.dex */
    public class C39321 implements PushManager.OnPushListener {
        C39321() {
        }

        @Override // com.tomatolive.library.utils.live.PushManager.OnPushListener
        public void onPushSuccess() {
            PrepareLiveActivity.this.isPushSuc = true;
            PrepareLiveActivity.this.reCount = 3;
        }

        @Override // com.tomatolive.library.utils.live.PushManager.OnPushListener
        public void onMicError() {
            ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).setEnterOrLeaveLiveRoomMsg(PrepareLiveActivity.this.liveId, "leave");
            PrepareLiveActivity.this.dismissCameraDialog();
            PermissionDialog.newInstance(PermissionDialog.MIC_TIP).show(PrepareLiveActivity.this.getSupportFragmentManager());
            PrepareLiveActivity.this.sendLeaveMessage();
        }

        @Override // com.tomatolive.library.utils.live.PushManager.OnPushListener
        public void onCameraError() {
            PrepareLiveActivity.this.isCameraOpenFail = true;
            PrepareLiveActivity.this.showCameraDialog();
            PrepareLiveActivity.this.sendLeaveMessage();
        }

        @Override // com.tomatolive.library.utils.live.PushManager.OnPushListener
        public void onCameraSuccess() {
            PrepareLiveActivity.this.isCameraOpenFail = false;
            PrepareLiveActivity.this.dismissCameraDialog();
        }

        @Override // com.tomatolive.library.utils.live.PushManager.OnPushListener
        public void onRePush() {
            if (PrepareLiveActivity.this.reCount > 0) {
                PrepareLiveActivity.this.isPushSuc = false;
                if (PrepareLiveActivity.this.pushManager != null) {
                    PrepareLiveActivity.this.pushManager.restartStream();
                }
            } else if (PrepareLiveActivity.this.reCount == 0) {
                RxTimerUtils.getInstance().timerBindDestroy(PrepareLiveActivity.this.getLifecycleProvider(), 5L, TimeUnit.SECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$1$zCucoq56G5akEBfX2ry59WQeV8E
                    @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
                    public final void action(long j) {
                        PrepareLiveActivity.C39321.this.lambda$onRePush$0$PrepareLiveActivity$1(j);
                    }
                });
            }
            PrepareLiveActivity.access$110(PrepareLiveActivity.this);
        }

        public /* synthetic */ void lambda$onRePush$0$PrepareLiveActivity$1(long j) {
            if (PrepareLiveActivity.this.isPushSuc || !NetUtils.isNetworkAvailable()) {
                return;
            }
            ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).uploadErrorReport(PrepareLiveActivity.this.liveId);
            PrepareLiveActivity.this.finishLive();
        }
    }

    private void showPrepareView() {
        this.rlLiveRoot.setVisibility(4);
        this.tvCountDown.setVisibility(4);
        this.mPreStartLiveView.setVisibility(0);
    }

    private void initLiveView() {
        this.liveAnimationView = (LiveAnimationView) findViewById(R$id.live_anim_view);
        this.llChatRoot = (LinearLayout) findViewById(R$id.ll_live_chat_msg);
        this.tvCloseBigSize = (TextView) findViewById(R$id.tv_close_big_size);
        this.tvCloseBigSize.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$CN42HFBiAifjkTQPWu0gXADf6ZY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$initLiveView$2$PrepareLiveActivity(view);
            }
        });
        this.liveBottomBar = (RelativeLayout) findViewById(R$id.rl_live_bottom_bar);
        this.stickerViewStub = (ViewStub) findViewById(R$id.sticker_view_stub);
        this.swipeAnimationController = new SwipeAnimationController(this.mContext, this.rlLiveRoot);
        this.mLivePusherInfoView.setRootView(this.shootContainer, this.swipeAnimationController);
        this.ivPrivateMsg = (ImageView) findViewById(R$id.iv_private_message);
        this.ivPrivateMsg.setVisibility(AppUtils.isEnablePrivateMsg() ? 0 : 8);
        this.rlWatermarkBg = (RelativeLayout) findViewById(R$id.rl_watermark_bg);
        this.tvWatermarkTitle = (TextView) findViewById(R$id.tv_watermark_title);
        this.tvWatermarkUrl = (TextView) findViewById(R$id.tv_watermark_url);
        this.ivWatermarkLogo = (ImageView) findViewById(R$id.iv_watermark_logo);
        this.mLiveAdBannerBottomView = (LiveAdBannerBottomView) findViewById(R$id.live_bottom_banner_view);
        if (AppUtils.isEnablePrivateMsg()) {
            this.qBadgeView = new QBadgeView(this.mContext);
            int i = -1;
            this.qBadgeView.bindTarget(this.ivPrivateMsg).setBadgeTextColor(-1).setBadgePadding(1.0f, true).isNoNumber(true).setBadgeGravity(8388661).setBadgeBackgroundColor(ContextCompat.getColor(this.mContext, R$color.fq_colorRed)).stroke(-1, 1.0f, true);
            QBadgeView qBadgeView = this.qBadgeView;
            if (!DBUtils.isUnReadBoolean()) {
                i = 0;
            }
            qBadgeView.setBadgeNumber(i);
        }
        initChatList();
        initInputChat();
        initDownCountAnim();
    }

    public /* synthetic */ void lambda$initLiveView$2$PrepareLiveActivity(View view) {
        setBigSize(false);
    }

    private void initChatList() {
        this.liveChatMsgView = (LiveChatMsgView) findViewById(R$id.live_chat_msg_view);
        this.liveChatMsgView.setOnChatMsgItemClickListener(new ChatMsgListAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.2
            @Override // com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter.OnItemClickListener
            public void onItemTextClick(Object obj) {
            }

            @Override // com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter.OnItemClickListener
            public void onItemClick(ChatEntity chatEntity) {
                if (chatEntity.getMsgType() == 2) {
                    if (PrepareLiveActivity.this.wsManager == null) {
                        return;
                    }
                    PrepareLiveActivity.this.wsManager.resetCount();
                    PrepareLiveActivity.this.wsManager.reconnect();
                } else if (!AppUtils.isShowUserAvatarDialog(chatEntity)) {
                } else {
                    PrepareLiveActivity.this.showUserCard(AppUtils.formatUserEntity(chatEntity));
                }
            }
        });
    }

    private void initInputChat() {
        this.mInputTextMsgDialog = new InputTextMsgDialog(this.mContext, this);
        this.mInputTextMsgDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$n_z4-Es-GVlB1L6L9rc02S2o8ac
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PrepareLiveActivity.this.lambda$initInputChat$3$PrepareLiveActivity(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initInputChat$3$PrepareLiveActivity(DialogInterface dialogInterface) {
        moveUpViews(false);
    }

    private void initDownCountAnim() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.tvCountDown, "scaleX", 1.0f, 5.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.tvCountDown, "scaleY", 1.0f, 5.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.tvCountDown, "alpha", 1.0f, 0.0f);
        this.countDownAnimSet = new AnimatorSet();
        this.countDownAnimSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        this.countDownAnimSet.setDuration(800L);
    }

    private void getGiftRes() {
        List<GiftDownloadItemEntity> localDownloadList = GiftDownLoadManager.getInstance().getLocalDownloadList();
        if ((localDownloadList == null || localDownloadList.size() == 0) && !this.isStartGiftUpdate) {
            this.isStartGiftUpdate = true;
            ((PrePareLivePresenter) this.mPresenter).getGiftList();
        }
    }

    private void getLastSettingFormSp() {
        this.lastBeautyProgress = SPUtils.getInstance().getInt(ConstantUtils.BIGEYE_KEY, -1);
        this.lastRuddyProgress = SPUtils.getInstance().getInt(ConstantUtils.SHORTENFACE_KEY, -1);
        this.lastWhiteProgress = SPUtils.getInstance().getInt(ConstantUtils.WHITE_KEY, -1);
        this.lastMirrorOpen = SPUtils.getInstance().getBoolean(ConstantUtils.LIVE_MIRROR_KEY, false);
    }

    private boolean isOpenFuRender() {
        return SPUtils.getInstance().getBoolean(FaceConstant.IS_OPEN_BEAUTY, false);
    }

    private void enableBeautyFilter() {
        if (isOpenFuRender()) {
            this.pushManager.enableHighBeauty(this.mFaceUnityControlView);
            this.faceRoot.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$cHgIDLfvkER3i-Lpa5Tuvd8tkGo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PrepareLiveActivity.this.lambda$enableBeautyFilter$4$PrepareLiveActivity(view);
                }
            });
            return;
        }
        final TXLivePusher enableBasicBeauty = this.pushManager.enableBasicBeauty();
        this.beautyDialog = BeautyDialog.newInstance(this.lastRuddyProgress, this.lastBeautyProgress, this.lastWhiteProgress);
        this.beautyDialog.setBeautyParamsListener(new BeautyDialog.OnBeautyParamsChangeListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.3
            @Override // com.tomatolive.library.p136ui.view.dialog.BeautyDialog.OnBeautyParamsChangeListener
            public void onBeautyParamsChange(BeautyDialog.BeautyParams beautyParams, int i) {
                TXLivePusher tXLivePusher = enableBasicBeauty;
                if (tXLivePusher == null) {
                    return;
                }
                tXLivePusher.setBeautyFilter(0, beautyParams.beauty, beautyParams.whiten, beautyParams.ruddy);
                if (i == 3) {
                    SPUtils.getInstance().put(ConstantUtils.BIGEYE_KEY, beautyParams.beauty);
                } else if (i == 2) {
                    SPUtils.getInstance().put(ConstantUtils.SHORTENFACE_KEY, beautyParams.ruddy);
                } else if (i != 1) {
                } else {
                    SPUtils.getInstance().put(ConstantUtils.WHITE_KEY, beautyParams.whiten);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.dialog.BeautyDialog.OnBeautyParamsChangeListener
            public void onDismiss() {
                if (!PrepareLiveActivity.this.isLiving) {
                    PrepareLiveActivity.this.mPreStartLiveView.setVisibility(0);
                }
            }
        });
    }

    public /* synthetic */ void lambda$enableBeautyFilter$4$PrepareLiveActivity(View view) {
        this.faceRoot.setVisibility(4);
        if (!this.isLiving) {
            this.mPreStartLiveView.setVisibility(0);
        }
    }

    private void initBottomMoreDialog() {
        this.bottomMoreDialog = BottomDialogUtils.getLiveBottomDialog(this.mContext, this.lastMirrorOpen, new BottomDialogUtils.LiveBottomMoreMenuListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.4
            @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.LiveBottomMoreMenuListener
            public void onLiveBottomMoreMenuListener(BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter, MenuEntity menuEntity, int i) {
                switch (menuEntity.getMenuType()) {
                    case 0:
                        PrepareLiveActivity.this.clickBeautyDialog();
                        return;
                    case 1:
                        PrepareLiveActivity.this.setMic(menuEntity);
                        return;
                    case 2:
                        PrepareLiveActivity.this.setFlash(baseQuickAdapter, menuEntity, i);
                        return;
                    case 3:
                        PrepareLiveActivity.this.setBigSize(true);
                        return;
                    case 4:
                        if (!PrepareLiveActivity.this.isFontCamera) {
                            menuEntity.isSelected = !menuEntity.isSelected;
                            baseQuickAdapter.setData(i, menuEntity);
                        }
                        PrepareLiveActivity.this.setMirror();
                        return;
                    case 5:
                        PrepareLiveActivity.this.showSpeakDialog();
                        return;
                    case 6:
                        PrepareLiveActivity.this.showInputTitleDialog();
                        return;
                    case 7:
                        PrepareLiveActivity.this.initStickerAddView();
                        return;
                    case 8:
                        PrepareLiveActivity.this.showPopularCarDialog();
                        return;
                    case 9:
                    case 11:
                    default:
                        return;
                    case 10:
                        PrepareLiveActivity.this.isShieldSmallGift = menuEntity.isSelected();
                        PrepareLiveActivity prepareLiveActivity = PrepareLiveActivity.this;
                        prepareLiveActivity.showToast(prepareLiveActivity.isShieldSmallGift ? R$string.fq_shield_small_gift_open_tips : R$string.fq_shield_small_gift_close_tips);
                        return;
                    case 12:
                        if (TextUtils.equals(PrepareLiveActivity.this.chargeType, "0")) {
                            ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).onPayStartLiveVerify(true);
                            return;
                        } else {
                            PrepareLiveActivity.this.showToast(R$string.fq_pay_live_start_toast);
                            return;
                        }
                }
            }

            @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.LiveBottomMoreMenuListener
            public void onLiveBottomLotteryMenuListener(BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter, MenuEntity menuEntity, int i) {
                if (menuEntity.getMenuType() != 13) {
                    return;
                }
                PrepareLiveActivity.this.showQMInteractDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initStickerAddView() {
        if (this.mStickerAddView == null) {
            this.mStickerAddView = (StickerAddView) this.stickerViewStub.inflate();
            this.mStickerAddView.initData(getSupportFragmentManager());
            this.mStickerAddView.setOnAddStickerCallback(new StickerAddView.OnAddStickerCallback() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$KcnBPpzG4Sbud8xXRh4qKwJftdQ
                @Override // com.tomatolive.library.p136ui.view.custom.StickerAddView.OnAddStickerCallback
                public final void onSaveStickerClick() {
                    PrepareLiveActivity.this.lambda$initStickerAddView$6$PrepareLiveActivity();
                }
            });
        }
        this.mStickerAddView.setVisibility(0);
        this.mAnchorCoverImg.setVisibility(4);
    }

    public /* synthetic */ void lambda$initStickerAddView$6$PrepareLiveActivity() {
        this.pushManager.showWaterLogo();
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$VMmRa_1fYV0CGKiIL4cP1eQN358
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$null$5$PrepareLiveActivity();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopularCarDialog() {
        PopularCardDialog popularCardDialog = this.popularCardDialog;
        if (popularCardDialog == null) {
            this.popularCardDialog = PopularCardDialog.newInstance(new PopularCardAdapter.OnPopularCardListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$7PyOapMpWiQf6BwFFFfu3_nAwqE
                @Override // com.tomatolive.library.p136ui.adapter.PopularCardAdapter.OnPopularCardListener
                public final void onItemClick(PopularCardEntity popularCardEntity) {
                    PrepareLiveActivity.this.lambda$showPopularCarDialog$7$PrepareLiveActivity(popularCardEntity);
                }
            });
        } else {
            popularCardDialog.refresh();
        }
        this.popularCardDialog.show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$showPopularCarDialog$7$PrepareLiveActivity(PopularCardEntity popularCardEntity) {
        if (this.isStartPopularCard) {
            showToast(R$string.fq_using_popular_card);
        } else {
            ((PrePareLivePresenter) this.mPresenter).usePopularCard(popularCardEntity);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onUsePopularSuccess(PopularCardEntity popularCardEntity) {
        showToast(R$string.fq_use_popular_success);
        ((PrePareLivePresenter) this.mPresenter).getLivePopularity(this.liveId);
        this.tvPopularCard.setVisibility(0);
        long string2long = NumberUtils.string2long(popularCardEntity.duration) * 60;
        setPopularCardValue(DateUtils.secondToMinutesString(string2long));
        executePoplarCard(string2long);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onUsePopularFail() {
        PopularCardDialog popularCardDialog = this.popularCardDialog;
        if (popularCardDialog != null) {
            popularCardDialog.refresh();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onPoplarCardRemaining(long j) {
        this.tvPopularCard.setVisibility(0);
        setPopularCardValue(DateUtils.secondToMinutesString(j));
        executePoplarCard(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLiveRoomInfo() {
        String liveTitle = this.mPreStartLiveView.getLiveTitle();
        this.liveLabel = this.mPreStartLiveView.getLiveTag();
        ((PrePareLivePresenter) this.mPresenter).startLive(EmojiParser.removeAllEmojis(liveTitle), this.liveLabel, this.enableLiveNotify, this.chargeType, this.ticketPrice, this.isAllowTicket, this.isRelation, this.relationLastLiveEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onRemainCountSuccess(String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "0")) {
            this.enableLiveNotify = "0";
            getLiveRoomInfo();
            return;
        }
        AnchorOpenNoticeDialog.newInstance(str, new CommonConfirmClickListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.5
            @Override // com.tomatolive.library.p136ui.interfaces.CommonConfirmClickListener
            public void onSure(View view) {
                PrepareLiveActivity.this.enableLiveNotify = "1";
                PrepareLiveActivity.this.getLiveRoomInfo();
            }

            @Override // com.tomatolive.library.p136ui.interfaces.CommonConfirmClickListener
            public void onCancel(View view) {
                PrepareLiveActivity.this.enableLiveNotify = "0";
                PrepareLiveActivity.this.getLiveRoomInfo();
            }
        }).show(getSupportFragmentManager());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onRemainCountFail() {
        this.enableLiveNotify = "0";
        getLiveRoomInfo();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onFeedbackSuccess() {
        showToast(R$string.fq_feedback_submit_success);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onStartLiveSubmitSuccess(boolean z, String str, String str2, String str3, String str4, final String str5, long j, RelationLastLiveEntity relationLastLiveEntity) {
        showToast(R$string.fq_submit_suc);
        this.chargeType = str;
        this.ticketPrice = str2;
        this.isAllowTicket = str3;
        this.isRelation = str4;
        this.relationLastLiveEntity = relationLastLiveEntity;
        this.mPreStartLiveView.setChargeTypeTips(str);
        if (z) {
            this.mLivePusherInfoView.setChargeTypeTips(isPayLive(), str2);
            if (SysConfigInfoManager.getInstance().isEnablePaidLiveGuide() && isPayLive()) {
                this.mLivePusherInfoView.showGuidePaidLive(this.mActivity);
            }
            if (TextUtils.isEmpty(str5) || j <= 0) {
                return;
            }
            ((PrePareLivePresenter) this.mPresenter).changeLiveStream(j, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.6
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str6) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    PrepareLiveActivity.this.pushUrl = str5;
                    PrepareLiveActivity.this.startStream();
                }
            });
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onStartPayLiveVerifySuccess(final boolean z, StartLiveVerifyEntity startLiveVerifyEntity) {
        SimplePayLiveCallback simplePayLiveCallback = new SimplePayLiveCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.7
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimplePayLiveCallback, com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback
            public void onPayLiveInfoSubmit(String str, String str2, String str3, String str4, RelationLastLiveEntity relationLastLiveEntity) {
                super.onPayLiveInfoSubmit(str, str2, str3, str4, relationLastLiveEntity);
                if (TextUtils.equals("0", str)) {
                    PrepareLiveActivity.this.chargeType = "0";
                    PrepareLiveActivity.this.ticketPrice = "";
                    PrepareLiveActivity.this.mPreStartLiveView.setChargeTypeTips(str);
                    PrepareLiveActivity.this.showToast(R$string.fq_submit_suc);
                    return;
                }
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).onStartLiveSubmit(z, str, str2, str3, str4, relationLastLiveEntity);
            }
        };
        if (this.chargeTypeDialog == null) {
            this.chargeTypeDialog = ChargeTypeDialog.newInstance(startLiveVerifyEntity, simplePayLiveCallback);
            this.chargeTypeDialog.show(getSupportFragmentManager());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, startLiveVerifyEntity);
        this.chargeTypeDialog.setArguments(bundle);
        this.chargeTypeDialog.setOnSubmitListener(simplePayLiveCallback);
        showDialogFragment(this.chargeTypeDialog);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onStartPayLiveVerifyFail() {
        WarnDialog.newInstance(WarnDialog.OPERATION_AUTHORITY).show(getSupportFragmentManager());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onUserCardInfoSuccess(UserEntity userEntity) {
        showUserCard(userEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onQMInteractShowTaskSuccess(List<QMInteractTaskEntity> list) {
        this.mLiveAdBannerBottomView.initIntimateTaskList(true, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPopularCardValue(String str) {
        this.tvPopularCard.setText(getString(R$string.fq_popular_card_used_time, new Object[]{str}));
    }

    private void executePoplarCard(final long j) {
        Observable.intervalRange(0L, j + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.8
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                PrepareLiveActivity.this.mPopularDisposable = disposable;
                PrepareLiveActivity.this.isStartPopularCard = true;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                PrepareLiveActivity.this.setPopularCardValue(DateUtils.secondToMinutesString(j - l.longValue()));
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                PrepareLiveActivity.this.isStartPopularCard = false;
                PrepareLiveActivity.this.tvPopularCard.setVisibility(8);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                PrepareLiveActivity.this.isStartPopularCard = false;
                PrepareLiveActivity.this.tvPopularCard.setVisibility(8);
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getLivePopularity(PrepareLiveActivity.this.liveId);
            }
        });
    }

    private boolean isSocketClose() {
        return this.isSocketClose || this.isSocketError;
    }

    private void initSocket() {
        addSocketTipMsg(R$string.fq_start_connect_socket);
        this.wsManager = WsManager.getInstance();
        this.wsManager.init(this, this.socketUrl, AppUtils.getSocketHeartBeatInterval());
        this.wsManager.setOnWebSocketListener(new C39659());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.PrepareLiveActivity$9 */
    /* loaded from: classes3.dex */
    public class C39659 implements OnWebSocketStatusListener {
        C39659() {
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onOpen(boolean z) {
            PrepareLiveActivity.this.isReConnectStatus = z;
            PrepareLiveActivity.this.isConnectingChatService = false;
            PrepareLiveActivity.this.reConnectCountOver = false;
            if (z) {
                PrepareLiveActivity.this.addSocketTipMsg(R$string.fq_reconnect_suc);
                PrepareLiveActivity.this.handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$9$xzsDW-dtHBfhgsC7cGdA-UR8GWw
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.C39659.this.lambda$onOpen$0$PrepareLiveActivity$9();
                    }
                });
            } else {
                PrepareLiveActivity.this.addSocketTipMsg(R$string.fq_connect_suc);
                if (PrepareLiveActivity.this.myselfEnterMessageEvent != null) {
                    PrepareLiveActivity prepareLiveActivity = PrepareLiveActivity.this;
                    prepareLiveActivity.onBackThreadReceiveMessage(prepareLiveActivity.myselfEnterMessageEvent);
                }
            }
            PrepareLiveActivity.this.isSocketClose = false;
            PrepareLiveActivity.this.isSocketError = false;
            if (PrepareLiveActivity.this.isPushSuc || PrepareLiveActivity.this.reCount > 0) {
                return;
            }
            PrepareLiveActivity.this.pushManager.restartStream();
        }

        public /* synthetic */ void lambda$onOpen$0$PrepareLiveActivity$9() {
            PrepareLiveActivity.this.changePrivateMessageNetStatus(true);
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onClose() {
            PrepareLiveActivity.this.isSocketClose = true;
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void onError(boolean z, String str) {
            if (str == null || !str.contains("HTTP/1.1 403")) {
                PrepareLiveActivity.this.addSocketTipMsg(R$string.fq_connect_fail);
                PrepareLiveActivity.this.isSocketError = true;
                return;
            }
            PrepareLiveActivity.this.startTokenDialogService();
            PrepareLiveActivity.this.isLiving = false;
            PrepareLiveActivity.this.onBackPressed();
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void reConnecting() {
            PrepareLiveActivity.this.isConnectingChatService = true;
            PrepareLiveActivity.this.addSocketTipMsg(R$string.fq_start_reconnect_socket);
            ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getWebSocketAddress(PrepareLiveActivity.this.liveId, "1");
        }

        @Override // com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener
        public void reConnectCountOver() {
            PrepareLiveActivity.this.isConnectingChatService = false;
            PrepareLiveActivity.this.reConnectCountOver = true;
            PrepareLiveActivity.this.addSocketReconnectMsg();
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        this.liveAnimationView.setAnimationCallback(new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.10
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                PrepareLiveActivity.this.liveAnimationView.setGiftAnimViewVisibility(4);
                PrepareLiveActivity.this.wsManagerNotifyBigAnim();
            }
        }, new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.11
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                PrepareLiveActivity.this.liveAnimationView.setGuardEnterAnimViewVisibility(4);
                PrepareLiveActivity.this.canShowGuardEnterMsg.set(true);
            }
        }, new LiveAnimationView.OnLeftGiftCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.12
            @Override // com.tomatolive.library.p136ui.view.custom.LiveAnimationView.OnLeftGiftCallback
            public void onLeftGiftDeleteListener(GiftAnimModel giftAnimModel) {
            }

            @Override // com.tomatolive.library.p136ui.view.custom.LiveAnimationView.OnLeftGiftCallback
            public void onLeftGiftClickListener(GiftAnimModel giftAnimModel) {
                PrepareLiveActivity.this.showUserCard(AppUtils.formatUserEntity(giftAnimModel));
            }
        }, new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.13
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                PrepareLiveActivity.this.carFullAnimFinish.set(true);
            }
        });
        this.mLivePusherInfoView.initLivePusherInfoCallback(1, getSupportFragmentManager(), new SimpleLivePusherInfoCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.14
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAnchorAvatarListener(View view) {
                if (!PrepareLiveActivity.this.isStartGetAnchorInfo) {
                    ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getAnchorInfo(PrepareLiveActivity.this.userId);
                    PrepareLiveActivity.this.isStartGetAnchorInfo = true;
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickUserAvatarListener(UserEntity userEntity) {
                PrepareLiveActivity.this.showUserCard(userEntity);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickGuardListener(GuardItemEntity guardItemEntity) {
                super.onClickGuardListener(guardItemEntity);
                PrepareLiveActivity.this.showGuardListDialog(guardItemEntity);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleLivePusherInfoCallback, com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback
            public void onClickAudienceListener(View view) {
                super.onClickAudienceListener(view);
                PrepareLiveActivity.this.showPayAudienceListDialog();
            }
        });
        this.mLivePusherInfoView.setGiftAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.15
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PrepareLiveActivity.this.canShowGiftNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PrepareLiveActivity.this.canShowGiftNotice.set(true);
                if (PrepareLiveActivity.this.giftNoticeQueue != null) {
                    if (PrepareLiveActivity.this.giftNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideGiftNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10004);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PrepareLiveActivity.this.canShowGiftNotice.set(true);
                if (PrepareLiveActivity.this.giftNoticeQueue != null) {
                    if (PrepareLiveActivity.this.giftNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideGiftNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10004);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setCharmAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.16
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PrepareLiveActivity.this.canShowAnchorInfoNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PrepareLiveActivity.this.canShowAnchorInfoNotice.set(true);
                if (PrepareLiveActivity.this.anchorInfoNoticeQueue != null) {
                    if (PrepareLiveActivity.this.anchorInfoNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideCharmNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10011);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PrepareLiveActivity.this.canShowAnchorInfoNotice.set(true);
                if (PrepareLiveActivity.this.anchorInfoNoticeQueue != null) {
                    if (PrepareLiveActivity.this.anchorInfoNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideCharmNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10011);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setSysNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.17
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PrepareLiveActivity.this.canShowSysNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PrepareLiveActivity.this.canShowSysNotice.set(true);
                if (PrepareLiveActivity.this.sysNoticeQueue != null) {
                    if (PrepareLiveActivity.this.sysNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideSysNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10005);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PrepareLiveActivity.this.canShowSysNotice.set(true);
                if (PrepareLiveActivity.this.sysNoticeQueue != null) {
                    if (PrepareLiveActivity.this.sysNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideSysNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(10005);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setLuckNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.18
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PrepareLiveActivity.this.canShowLuckNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PrepareLiveActivity.this.canShowLuckNotice.set(true);
                if (PrepareLiveActivity.this.luckNoticeQueue != null) {
                    if (PrepareLiveActivity.this.luckNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideLuckNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.SYS_LUCK_HIT);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PrepareLiveActivity.this.canShowLuckNotice.set(true);
                if (PrepareLiveActivity.this.luckNoticeQueue != null) {
                    if (PrepareLiveActivity.this.luckNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideLuckNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.SYS_LUCK_HIT);
                    }
                }
            }
        });
        this.mLivePusherInfoView.setGameNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.19
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PrepareLiveActivity.this.canShowGameNotice.set(false);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PrepareLiveActivity.this.canShowGameNotice.set(true);
                if (PrepareLiveActivity.this.gameNoticeQueue != null) {
                    if (PrepareLiveActivity.this.gameNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideGameNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
                    }
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PrepareLiveActivity.this.canShowGameNotice.set(true);
                if (PrepareLiveActivity.this.gameNoticeQueue != null) {
                    if (PrepareLiveActivity.this.gameNoticeQueue.isEmpty()) {
                        PrepareLiveActivity.this.mLivePusherInfoView.hideGameNoticeView();
                    } else {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.GAME_NOTICE);
                    }
                }
            }
        });
        this.mPreStartLiveView.setOnPreStartLiveCallback(new PreStartLiveView.OnPreStartLiveCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.20
            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickClosedListener() {
                PrepareLiveActivity.this.onBackPressed();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickCameraListener() {
                PrepareLiveActivity.this.clickCamera();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickBeautyListener() {
                PrepareLiveActivity.this.clickBeauty();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickMirrorListener() {
                PrepareLiveActivity.this.clickMirror();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickStartLiveListener(String str, String str2) {
                if (!PrepareLiveActivity.this.isBtnStartClick) {
                    ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getPreStartLiveInfo(false);
                    PrepareLiveActivity.this.isBtnStartClick = true;
                    return;
                }
                PrepareLiveActivity.this.showToast(R$string.fq_dont_start_live_repeat);
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onClickPaySetupListener() {
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).onPayStartLiveVerify(false);
            }

            @Override // com.tomatolive.library.p136ui.view.custom.PreStartLiveView.OnPreStartLiveCallback
            public void onCancelUpload() {
                PrepareLiveActivity.this.isBtnStartClick = false;
            }
        });
        this.ivTrans.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$I6NdD9TFHhcJ3-uMv-R8WF2QxL8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$initListener$8$PrepareLiveActivity(view);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_input), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$ENMywKCnJ4VeLtvmiD4cn1sD4kw
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$9$PrepareLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_sw_camera), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$7k0PRG1LivOHy_Xs1R_NrnTH48o
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$10$PrepareLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_setting), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$m9pHI-Yai9AJqciZPieyriHdBrE
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$11$PrepareLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_back), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$p8pjkdoH4sFwVlTSvpLGVlemITs
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$12$PrepareLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_turnover), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$mbpUrjaOZ8Y0-fy_LAPhCWF3F48
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$13$PrepareLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivPrivateMsg, 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$1Ybq0FgjRrQixWGmKJHg7RvKXZw
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                PrepareLiveActivity.this.lambda$initListener$14$PrepareLiveActivity(obj);
            }
        });
        this.mGiftBoxView.setOnSendGiftBoxMsgListener(new GiftBoxView.OnSendGiftBoxMsgListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.21
            @Override // com.tomatolive.library.p136ui.view.custom.GiftBoxView.OnSendGiftBoxMsgListener
            public void onSendGiftBoxMsg(GiftBoxEntity giftBoxEntity) {
                PrepareLiveActivity.this.wsManager.sendGrabGiftBoxMessage(MessageHelper.convertToGrabGiftBoxMsg(giftBoxEntity.giftBoxUniqueCode));
            }

            @Override // com.tomatolive.library.p136ui.view.custom.GiftBoxView.OnSendGiftBoxMsgListener
            public void onShowDialog(GiftBoxEntity giftBoxEntity) {
                GiftBoxPresenterDialog.newInstance(giftBoxEntity.presenterAvatar, giftBoxEntity.presenterName).show(PrepareLiveActivity.this.getSupportFragmentManager());
            }
        });
        this.anchorLiveEndView.setOnLiveEndClosedListener(new AnchorLiveEndView.OnLiveEndClosedListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.22
            @Override // com.tomatolive.library.p136ui.view.custom.AnchorLiveEndView.OnLiveEndClosedListener
            public void onClosedListener() {
                PrepareLiveActivity.this.isLiving = false;
                PrepareLiveActivity.this.onBackPressed();
            }

            @Override // com.tomatolive.library.p136ui.view.custom.AnchorLiveEndView.OnLiveEndClosedListener
            public void onFeedbackSubmitListener(String str) {
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).onFeedbackSubmit(PrepareLiveActivity.this.liveId, PrepareLiveActivity.this.liveCount, str, PrepareLiveActivity.this.chargeType);
            }
        });
        this.mLiveAdBannerBottomView.setOnInteractWindowClickListener(new TopBannerUtils.InteractWindowListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.23
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils.InteractWindowListener
            public void onClick(View view) {
                if (view instanceof QMNoticeWindow) {
                    PrepareLiveActivity.this.mLiveAdBannerBottomView.hideRedPoint();
                    PrepareLiveActivity.this.showQMTaskListDialog();
                }
            }

            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils.InteractWindowListener
            public void onDelete(View view) {
                if (view instanceof HdLotteryWindowView) {
                    PrepareLiveActivity.this.mLiveAdBannerBottomView.onDeleteHdLotteryWindowView();
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$8$PrepareLiveActivity(View view) {
        if (this.isTranOpen) {
            this.isTranOpen = false;
            this.ivTrans.setImageResource(R$drawable.fq_ic_anchor_bottom_translate_normal);
            showToast(R$string.fq_close_tran);
            return;
        }
        TransDialog transDialog = this.transDialog;
        if (transDialog == null) {
            return;
        }
        transDialog.show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$initListener$9$PrepareLiveActivity(Object obj) {
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
            showInputMsgDialog();
            moveUpViews(true);
        }
    }

    public /* synthetic */ void lambda$initListener$10$PrepareLiveActivity(Object obj) {
        clickCamera();
    }

    public /* synthetic */ void lambda$initListener$11$PrepareLiveActivity(Object obj) {
        Dialog dialog = this.bottomMoreDialog;
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        this.bottomMoreDialog.show();
    }

    public /* synthetic */ void lambda$initListener$12$PrepareLiveActivity(Object obj) {
        onBackPressed();
    }

    public /* synthetic */ void lambda$initListener$13$PrepareLiveActivity(Object obj) {
        showGiftListRecord();
    }

    public /* synthetic */ void lambda$initListener$14$PrepareLiveActivity(Object obj) {
        showPrivateMessageDialog();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onTagListSuccess(List<LabelEntity> list) {
        this.mPreStartLiveView.onTagListSuccess(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onPreStartLiveInfoSuccess(CoverEntity coverEntity, boolean z) {
        if (coverEntity == null) {
            showToast(R$string.fq_cover_error);
            return;
        }
        this.mPreStartLiveView.updatePreStartLiveInfo(coverEntity.liveCoverUrl);
        if (z || this.isLiving) {
            return;
        }
        String str = coverEntity.isChecked;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            if (hashCode != 49) {
                if (hashCode != 1444) {
                    if (hashCode == 1445 && str.equals(ConstantUtils.BAN_MSG)) {
                        c = 1;
                    }
                } else if (str.equals("-1")) {
                    c = 0;
                }
            } else if (str.equals("1")) {
                c = 3;
            }
        } else if (str.equals("0")) {
            c = 2;
        }
        if (c == 0) {
            showCoverDialog(getString(R$string.fq_cover_nopass), getString(R$string.fq_cover_retry_upload));
        } else if (c == 1) {
            showCoverDialog(getString(R$string.fq_cover_no_upload), getString(R$string.fq_cover_upload_orno));
        } else if (c == 2) {
            showCoverDialog(getString(R$string.fq_cover_wait_check), getString(R$string.fq_cover_upload_orno));
        } else if (c != 3) {
        } else {
            startLive();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onPreStartLiveInfoFail() {
        this.isBtnStartClick = false;
        showToast(R$string.fq_net_poor_live);
    }

    private void goToLive(AnchorStartLiveEntity anchorStartLiveEntity) {
        this.startLiveTimeMillis = System.currentTimeMillis();
        this.startLiveInfoEntity = anchorStartLiveEntity;
        AnchorStartLiveEntity anchorStartLiveEntity2 = this.startLiveInfoEntity;
        this.pushUrl = anchorStartLiveEntity2.pushStreamUrl;
        this.myMarkUrls = anchorStartLiveEntity2.markUrls;
        startStream();
        showLiveView();
        AnchorStartLiveEntity anchorStartLiveEntity3 = this.startLiveInfoEntity;
        String str = anchorStartLiveEntity3.speakLevel;
        this.speakLevel = str;
        this.defaultSpeakLevel = str;
        this.myUserGrade = anchorStartLiveEntity3.expGrade;
        this.liveCount = NumberUtils.string2int(anchorStartLiveEntity3.liveCount);
        this.tvTitle.setText(getString(R$string.fq_text_live_title, new Object[]{this.startLiveInfoEntity.topic}));
        startHideTitleTimer();
        this.liveId = this.startLiveInfoEntity.liveId;
        this.userId = UserInfoManager.getInstance().getUserId();
        AnchorStartLiveEntity anchorStartLiveEntity4 = this.startLiveInfoEntity;
        this.socketUrl = AppUtils.formatLiveSocketUrl(anchorStartLiveEntity4.wsAddress, this.liveId, this.userId, "1", anchorStartLiveEntity4.f5841k);
        AnchorStartLiveEntity anchorStartLiveEntity5 = this.startLiveInfoEntity;
        this.streamName = anchorStartLiveEntity5.streamName;
        anchorStartLiveEntity5.anchorId = this.userId;
        this.isSuperAdmin = AppUtils.isSuperAdmin();
        this.giftTrumpetPlayPeriod = AppUtils.getGiftNoticeInterval();
        this.myselfEnterMessageEvent = this.startLiveInfoEntity.formatMyselfEnterMessageEvent();
        if (this.isSuperAdmin) {
            InputTextMsgDialog inputTextMsgDialog = this.mInputTextMsgDialog;
            if (inputTextMsgDialog != null) {
                inputTextMsgDialog.setBandPostBySuperManager();
            }
        } else {
            this.mInputTextMsgDialog.cancelBandPost();
        }
        this.shieldedList = DBUtils.getShieldList();
        this.isBanPostAll = anchorStartLiveEntity.isBanAll();
        this.isBanGroup = UserInfoManager.getInstance().isInBanGroup();
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.avatar = UserInfoManager.getInstance().getAvatar();
        anchorEntity.nickname = UserInfoManager.getInstance().getUserNickname();
        anchorEntity.userId = UserInfoManager.getInstance().getUserId();
        anchorEntity.liveCount = anchorStartLiveEntity.liveCount;
        anchorEntity.liveId = this.liveId;
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.setVisibility(0);
            LiveItemEntity liveItemEntity = new LiveItemEntity();
            AnchorStartLiveEntity anchorStartLiveEntity6 = this.startLiveInfoEntity;
            liveItemEntity.anchorContribution = anchorStartLiveEntity6.anchorContribution;
            liveItemEntity.anchorGuardCount = anchorStartLiveEntity6.anchorGuardCount;
            liveItemEntity.vipCount = String.valueOf(anchorStartLiveEntity6.vipCount);
            this.guardItemEntity = new GuardItemEntity();
            GuardItemEntity guardItemEntity = this.guardItemEntity;
            guardItemEntity.anchorGuardCount = this.startLiveInfoEntity.anchorGuardCount;
            guardItemEntity.anchorId = this.userId;
            this.mLivePusherInfoView.initData(liveItemEntity, anchorEntity, guardItemEntity);
            this.mLivePusherInfoView.setChargeTypeTips(isPayLive(), this.ticketPrice);
            if (SysConfigInfoManager.getInstance().isEnablePaidLiveGuide() && isPayLive()) {
                this.mLivePusherInfoView.showGuidePaidLive(this.mActivity);
            }
        }
        initWatermarkConfig();
        ((PrePareLivePresenter) this.mPresenter).getLiveAdNoticeList();
        ((PrePareLivePresenter) this.mPresenter).getPopularCardRemainingTime();
        initSocket();
        startPlayAnim();
        ((PrePareLivePresenter) this.mPresenter).setEnterOrLeaveLiveRoomMsg(this.liveId, ConstantUtils.ENTER_TYPE);
        ((PrePareLivePresenter) this.mPresenter).getGiftBoxList(this.liveId);
        ((PrePareLivePresenter) this.mPresenter).sendAnchorShowTaskListRequest(5L, this.liveId);
        Observable.interval(0L, AppUtils.getOnlineCountSynInterval(), TimeUnit.SECONDS).observeOn(Schedulers.m90io()).subscribeOn(Schedulers.m90io()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.24
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getCurrentOnlineUserList(PrepareLiveActivity.this.liveId);
            }
        });
        Observable.interval(0L, 15L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.25
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getLivePopularity(PrepareLiveActivity.this.liveId);
            }
        });
        AnchorLiveEndView anchorLiveEndView = this.anchorLiveEndView;
        if (anchorLiveEndView != null) {
            anchorLiveEndView.setStartLiveTimeMillis(System.currentTimeMillis());
        }
        LogEventUtils.startLiveDataTimerTask(LogConstants.END_LIVE_EVENT_NAME, new Runnable() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.26
            @Override // java.lang.Runnable
            public void run() {
                PrepareLiveActivity.this.saveLiveUploadData();
            }
        }, DateUtils.ONE_MINUTE_MILLIONS, 10000L);
    }

    private void startHideTitleTimer() {
        this.tvTitle.setVisibility(0);
        RxTimerUtils.getInstance().timerBindDestroy(getLifecycleProvider(), 10L, TimeUnit.SECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$tfllrKjjFIPeb58-oJjthXc_HPw
            @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
            public final void action(long j) {
                PrepareLiveActivity.this.lambda$startHideTitleTimer$15$PrepareLiveActivity(j);
            }
        });
    }

    public /* synthetic */ void lambda$startHideTitleTimer$15$PrepareLiveActivity(long j) {
        this.tvTitle.setVisibility(8);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onStartLiveFail() {
        this.isBtnStartClick = false;
        this.mLivePusherInfoView.setVisibility(4);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onLiveAdNoticeSuccess(BannerEntity bannerEntity) {
        if (!TextUtils.isEmpty(bannerEntity.content)) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(10);
            chatEntity.setMsgText(bannerEntity.content);
            this.liveChatMsgView.addChatMsg(chatEntity);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onGiftBoxListSuccess(List<GiftBoxEntity> list) {
        GiftBoxView giftBoxView = this.mGiftBoxView;
        if (giftBoxView != null) {
            giftBoxView.showBoxList(list, this.liveId);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onLiveAudiencesSuccess(OnLineUsersEntity onLineUsersEntity) {
        if (onLineUsersEntity != null && onLineUsersEntity.getUserEntityList() != null) {
            List<UserEntity> userEntityList = onLineUsersEntity.getUserEntityList();
            if (userEntityList == null || userEntityList.size() == 0) {
                return;
            }
            this.mLivePusherInfoView.replaceData(userEntityList);
        }
        this.mLivePusherInfoView.updateVipCount(onLineUsersEntity.vipCount);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onLivePopularitySuccess(long j) {
        this.onLineCount.set(j);
        this.mLivePusherInfoView.setLivePopularityCount(this.onLineCount.get());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onAnchorInfoSuccess(AnchorEntity anchorEntity) {
        if (AppUtils.isNobilityUser(anchorEntity.nobilityType)) {
            this.onUserCardCallback = new UserCardCallback(true);
            if (this.anchorNobilityAvatarDialog == null) {
                this.anchorNobilityAvatarDialog = UserNobilityAvatarDialog.newInstance(anchorEntity, 1, false, false, false, this.onUserCardCallback);
                this.anchorNobilityAvatarDialog.show(getSupportFragmentManager());
            } else {
                anchorEntity.userRole = "1";
                anchorEntity.role = "1";
                this.bundleArgs = new Bundle();
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, false);
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
                this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
                this.bundleArgs.putInt("liveType", 1);
                this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
                this.anchorNobilityAvatarDialog.setArguments(this.bundleArgs);
                this.anchorNobilityAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
                showDialogFragment(this.anchorNobilityAvatarDialog);
            }
            this.isStartGetAnchorInfo = false;
            return;
        }
        this.onUserCardCallback = new UserCardCallback(true);
        if (this.anchorAvatarDialog == null) {
            this.anchorAvatarDialog = UserNormalAvatarDialog.newInstance(anchorEntity, 1, false, false, false, this.onUserCardCallback);
            this.anchorAvatarDialog.show(getSupportFragmentManager());
        } else {
            anchorEntity.userRole = "1";
            anchorEntity.role = "1";
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putInt("liveType", 1);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
            this.anchorAvatarDialog.setArguments(this.bundleArgs);
            this.anchorAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.anchorAvatarDialog);
        }
        this.isStartGetAnchorInfo = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onAnchorInfoFail(int i, String str) {
        this.isStartGetAnchorInfo = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onGiftListSuccess(final List<GiftDownloadItemEntity> list) {
        Handler handler = this.workHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$4BhhXf9R3DDuD4kbPVZwdvvw4rY
                @Override // java.lang.Runnable
                public final void run() {
                    GiftDownLoadManager.getInstance().updateLocalDownloadList(list);
                }
            });
        }
        this.isStartGiftUpdate = false;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onGiftListFail() {
        this.isStartGiftUpdate = false;
        showToast(R$string.fq_gift_fail);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onWebSocketAddressSuccess(LiveEntity liveEntity) {
        this.socketUrl = AppUtils.formatLiveSocketUrl(liveEntity.wsAddress, this.liveId, this.userId, "1", liveEntity.f5841k);
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.reconnect(this.socketUrl);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onWebSocketAddressFail() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.resetCount();
            this.wsManager.setStatus(WsStatus.CONNECT_FAIL);
            this.isConnectingChatService = false;
            this.reConnectCountOver = true;
        }
        changePrivateMessageNetStatus(false);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onLiveEndSuccess(int i, LiveEndEntity liveEndEntity) {
        liveEndEntity.liveId = this.liveId;
        liveEndEntity.avatar = UserInfoManager.getInstance().getAvatar();
        liveEndEntity.userId = UserInfoManager.getInstance().getUserId();
        liveEndEntity.nickname = UserInfoManager.getInstance().getUserNickname();
        liveEndEntity.sex = UserInfoManager.getInstance().getUserSex();
        liveEndEntity.expGrade = this.myUserGrade;
        liveEndEntity.nobilityType = UserInfoManager.getInstance().getNobilityType();
        AnchorLiveEndView anchorLiveEndView = this.anchorLiveEndView;
        if (anchorLiveEndView != null) {
            anchorLiveEndView.initData(i, this.startLiveTimeMillis, liveEndEntity);
        }
        if (i == 2) {
            WarnDialog.newInstance(WarnDialog.BAN_TIP).show(getSupportFragmentManager());
        }
        this.isLiving = false;
    }

    private void showCoverDialog(String str, String str2) {
        CoverSettingDialog.newInstance(str, str2, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$VBrIXqoVJzCAtNCl0wbfUAbnYd4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$showCoverDialog$17$PrepareLiveActivity(view);
            }
        }, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$TRE1gzy2ivRS5SYDnaflG2czs2g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$showCoverDialog$18$PrepareLiveActivity(view);
            }
        }).show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$showCoverDialog$17$PrepareLiveActivity(View view) {
        goToUploadCover();
    }

    public /* synthetic */ void lambda$showCoverDialog$18$PrepareLiveActivity(View view) {
        startLive();
    }

    private void goToUploadCover() {
        this.mPreStartLiveView.goToUploadCover();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        this.isBtnStartClick = false;
        if (i == 69) {
            if (i2 == -1) {
                this.mPreStartLiveView.picCompression();
            } else if (i2 != 96) {
            } else {
                showToast(R$string.fq_pic_cut_fail);
            }
        } else if (i == 908) {
            if (i2 != -1) {
                return;
            }
            this.mPreStartLiveView.onCameraActivityResult();
        } else if (i != 909 || i2 != -1) {
        } else {
            this.mPreStartLiveView.onAlbumActivityResult(intent);
        }
    }

    private void stopStream() {
        this.pushManager.stopStream();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startStream() {
        this.isLiving = true;
        this.pushManager.startStream(this.pushUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showWaterLogo */
    public void lambda$null$5$PrepareLiveActivity() {
        Bitmap waterLogo;
        if (SysConfigInfoManager.getInstance().isEnableSticker() && (waterLogo = this.pushManager.getWaterLogo()) != null) {
            this.mAnchorCoverImg.setImageBitmap(waterLogo);
            this.mAnchorCoverImg.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFlash(BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter, MenuEntity menuEntity, int i) {
        if (this.isFontCamera) {
            menuEntity.isSelected = !menuEntity.isSelected;
            baseQuickAdapter.setData(i, menuEntity);
            showToast(R$string.fq_dont_use_flash);
        } else {
            this.pushManager.toggleTorch(menuEntity.isSelected);
        }
        this.tempFlashMenuEntity = menuEntity;
        this.tempAdapter = baseQuickAdapter;
        this.tempPos = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMic(MenuEntity menuEntity) {
        showToast(menuEntity.isSelected ? R$string.fq_close_mic : R$string.fq_open_mic);
        this.pushManager.setMuteAudio(menuEntity.isSelected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMirror() {
        if (!this.isFontCamera) {
            showToast(R$string.fq_back_camera_no_mirror);
            return;
        }
        this.pushManager.setFrontCameraMirror(!this.lastMirrorOpen);
        this.lastMirrorOpen = !this.lastMirrorOpen;
        if (this.lastMirrorOpen) {
            showToast(R$string.fq_the_same_picture);
            this.mPreStartLiveView.setTMirrorDrawableTop(R$drawable.fq_ic_anchor_mirror_selected);
        } else {
            showToast(R$string.fq_the_diff_picture);
            this.mPreStartLiveView.setTMirrorDrawableTop(R$drawable.fq_ic_anchor_mirror);
        }
        SPUtils.getInstance().put(ConstantUtils.LIVE_MIRROR_KEY, this.lastMirrorOpen);
    }

    private void switchCamera() {
        BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter;
        this.pushManager.switchCamera();
        this.isFontCamera = !this.isFontCamera;
        MenuEntity menuEntity = this.tempFlashMenuEntity;
        if (menuEntity == null || (baseQuickAdapter = this.tempAdapter) == null) {
            return;
        }
        menuEntity.isSelected = false;
        baseQuickAdapter.setData(this.tempPos, menuEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickBeautyDialog() {
        this.mPreStartLiveView.setVisibility(4);
        if (isOpenFuRender()) {
            this.faceRoot.setVisibility(0);
            return;
        }
        BeautyDialog beautyDialog = this.beautyDialog;
        if (beautyDialog == null) {
            return;
        }
        if (beautyDialog.isAdded()) {
            this.beautyDialog.dismiss();
        } else {
            this.beautyDialog.show(getSupportFragmentManager(), "beautyDialog");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInputTitleDialog() {
        UpdateTitleDialog.newInstance(new UpdateTitleDialog.OnUpdateLiveTitleListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$cW3JpZ_cRWc1VsMILhOA6CdkUXA
            @Override // com.tomatolive.library.p136ui.view.dialog.UpdateTitleDialog.OnUpdateLiveTitleListener
            public final void onUpdateLiveTitle(String str) {
                PrepareLiveActivity.this.lambda$showInputTitleDialog$19$PrepareLiveActivity(str);
            }
        }).show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$showInputTitleDialog$19$PrepareLiveActivity(String str) {
        if (EmojiFilter.containsEmoji(str)) {
            showToast(R$string.fq_live_title_no_emoji);
            return;
        }
        this.tvTitle.setText(getString(R$string.fq_text_live_title, new Object[]{EmojiParser.removeAllEmojis(str)}));
        startHideTitleTimer();
        WsManager wsManager = this.wsManager;
        if (wsManager == null) {
            return;
        }
        wsManager.sendNotifyMessage(MessageHelper.convertToNotifyMsg(this.liveId, str));
    }

    private void showInputMsgDialog() {
        InputTextMsgDialog inputTextMsgDialog = this.mInputTextMsgDialog;
        if (inputTextMsgDialog != null) {
            inputTextMsgDialog.show();
        }
    }

    private void moveUpViews(boolean z) {
        float f = 0.0f;
        this.llChatRoot.setTranslationY(z ? -(ScreenUtils.getScreenHeight() * 0.42f) : 0.0f);
        this.mLivePusherInfoView.setTranslationY(z ? -(ScreenUtils.getScreenHeight() / 4.0f) : 0.0f);
        LiveAnimationView liveAnimationView = this.liveAnimationView;
        if (z) {
            f = -(ScreenUtils.getScreenHeight() / 3.0f);
        }
        liveAnimationView.setTranslationY(f);
    }

    private void showGiftListRecord() {
        if (this.giftRecordDialog == null) {
            this.giftRecordDialog = GiftRecordDialog.newInstance(this.liveCount, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.27
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserItemClickListener(UserEntity userEntity) {
                    super.onUserItemClickListener(userEntity);
                    PrepareLiveActivity.this.showUserAvatarDialog(userEntity);
                }
            });
            this.giftRecordDialog.show(getSupportFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putInt(ConstantUtils.RESULT_COUNT, this.liveCount);
        this.giftRecordDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.giftRecordDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserAvatarDialog(UserEntity userEntity) {
        String avatar = userEntity.getAvatar();
        String name = userEntity.getName();
        String userId = userEntity.getUserId();
        String sex = userEntity.getSex();
        String expGrade = userEntity.getExpGrade();
        String role = userEntity.getRole();
        int guardType = userEntity.getGuardType();
        int nobilityType = userEntity.getNobilityType();
        userEntity.setAppId(null);
        userEntity.setOpenId(null);
        boolean isHouseManager = AppUtils.isHouseManager(role);
        boolean contains = this.shieldedList.contains(userId);
        boolean isNobilityUser = AppUtils.isNobilityUser(nobilityType);
        if (AppUtils.isHouseSuperManager(role)) {
            this.userSuperAvatarDialog = UserSuperAvatarDialog.newInstance(userId, avatar, name, sex, "", expGrade, guardType);
            this.userSuperAvatarDialog.show(getSupportFragmentManager());
        } else if (isNobilityUser) {
            this.onUserCardCallback = new UserCardCallback(userId, name, 1, isHouseManager, contains);
            if (this.userNobilityAvatarDialog == null) {
                this.userNobilityAvatarDialog = UserNobilityAvatarDialog.newInstance(userEntity, true, (OnUserCardCallback) this.onUserCardCallback);
                this.userNobilityAvatarDialog.show(getSupportFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putInt("liveType", 2);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
            this.userNobilityAvatarDialog.setArguments(this.bundleArgs);
            this.userNobilityAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.userNobilityAvatarDialog);
        } else if (AppUtils.isGuardUser(guardType)) {
            this.onUserCardCallback = new UserCardCallback(userId, name, 2, isHouseManager, contains);
            if (this.userGuardAvatarDialog == null) {
                this.userGuardAvatarDialog = UserGuardAvatarDialog.newInstance(userEntity, true, this.onUserCardCallback);
                this.userGuardAvatarDialog.show(getSupportFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
            this.userGuardAvatarDialog.setArguments(this.bundleArgs);
            this.userGuardAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.userGuardAvatarDialog);
        } else {
            this.onUserCardCallback = new UserCardCallback(userId, name, 3, isHouseManager, contains);
            if (this.userAvatarDialog == null) {
                this.userAvatarDialog = UserNormalAvatarDialog.newInstance(userEntity, true, (OnUserCardCallback) this.onUserCardCallback);
                this.userAvatarDialog.show(getSupportFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putInt("liveType", 2);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, AppUtils.formatAnchorEntity(userEntity));
            this.userAvatarDialog.setArguments(this.bundleArgs);
            this.userAvatarDialog.setOnUserCardCallback(this.onUserCardCallback);
            showDialogFragment(this.userAvatarDialog);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendShowUserManageMenuRequest(int i, boolean z, boolean z2, String str, String str2) {
        ((PrePareLivePresenter) this.mPresenter).showUserManageMenu(i, this.liveId, z, z2, str, str2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onShowUserManageMenu(int i, boolean z, boolean z2, boolean z3, String str, String str2) {
        boolean z4 = false;
        if (i == 1 ? !(!isShowDialogFragment(this.userNobilityAvatarDialog) || !TextUtils.equals(str2, this.userNobilityAvatarDialog.getTargetId())) : !(i == 2 ? !isShowDialogFragment(this.userGuardAvatarDialog) || !TextUtils.equals(str2, this.userGuardAvatarDialog.getTargetId()) : i != 3 || !isShowDialogFragment(this.userAvatarDialog) || !TextUtils.equals(str2, this.userAvatarDialog.getTargetId()))) {
            z4 = true;
        }
        if (z4) {
            userAvatarDialogManager(z, z2, z3, str, str2);
        }
    }

    private void userAvatarDialogManager(boolean z, boolean z2, boolean z3, final String str, final String str2) {
        LiveActionBottomDialog.create("1", z, z3, z2, true, new LiveActionBottomDialog.OnLiveActionListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$VUeGSjQAt17gWrEbEcJknJAaW3g
            @Override // com.tomatolive.library.p136ui.view.dialog.LiveActionBottomDialog.OnLiveActionListener
            public final void onLiveAction(int i, boolean z4) {
                PrepareLiveActivity.this.lambda$userAvatarDialogManager$21$PrepareLiveActivity(str, str2, i, z4);
            }
        }).show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$userAvatarDialogManager$21$PrepareLiveActivity(final String str, final String str2, int i, boolean z) {
        dismissUserAvatarDialog();
        boolean z2 = true;
        if (i == 1) {
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.sendCtrlMessage(MessageHelper.convertToCtrlMsg(this.liveId, str2, str, z));
        } else if (i == 2) {
            if (z) {
                BottomDialogUtils.showBannedDialog(this.mContext, new BottomDialogUtils.LiveBottomBannedMenuListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$mnxcDafYJo2C_yt8Hqhyqa4jP3U
                    @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.LiveBottomBannedMenuListener
                    public final void onLiveBottomBannedMenuListener(long j) {
                        PrepareLiveActivity.this.lambda$null$20$PrepareLiveActivity(str2, str, j);
                    }
                });
                return;
            }
            WsManager wsManager2 = this.wsManager;
            if (wsManager2 == null) {
                return;
            }
            wsManager2.sendBannedMessage(MessageHelper.convertToBanMsg(str2, str, "-1", "2"));
        } else if (i == 3) {
            if (z) {
                if (!this.shieldedList.contains(str2)) {
                    this.shieldedList.add(str2);
                    DBUtils.updateShieldUser(str2, true);
                    showToast(getString(R$string.fq_shielded) + str);
                }
            } else {
                this.shieldedList.remove(str2);
                DBUtils.updateShieldUser(str2, false);
                showToast(getString(R$string.fq_cancel_shielded, new Object[]{str}));
            }
            WsManager wsManager3 = this.wsManager;
            if (wsManager3 == null) {
                return;
            }
            wsManager3.sendShieldMessage(MessageHelper.convertToShieldMsg(this.liveId, str2, z));
        } else if (i == 4) {
            WsManager wsManager4 = this.wsManager;
            if (wsManager4 == null) {
                return;
            }
            wsManager4.sendKickOutMessage(MessageHelper.convertToKickOutMsg(this.liveId, str2, str));
        } else if (i != 5) {
        } else {
            dismissDialogFragment(this.giftRecordDialog);
            PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
            if (privateMsgDialog == null) {
                if (!this.isSocketError && !this.isSocketClose) {
                    z2 = false;
                }
                this.privateMsgDialog = PrivateMsgDialog.newInstance(str, str2, z2);
                this.privateMsgDialog.setSendPrivateMsgListener(this);
                this.privateMsgDialog.show(getSupportFragmentManager());
            } else if (privateMsgDialog.isAdded()) {
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean(PrivateMsgDialog.TYPE_FORM_ANCHOR, true);
                bundle.putInt(PrivateMsgDialog.CONTENT_TYPE_KEY, 2);
                bundle.putString(PrivateMsgDialog.TARGET_ID_KEY, str2);
                bundle.putString(PrivateMsgDialog.TARGET_NAME_KEY, str);
                String str3 = PrivateMsgDialog.TYPE_SOCKET_STATUS;
                if (!this.isSocketError && !this.isSocketClose) {
                    z2 = false;
                }
                bundle.putBoolean(str3, z2);
                this.privateMsgDialog.setArguments(bundle);
                this.privateMsgDialog.show(getSupportFragmentManager());
            }
        }
    }

    public /* synthetic */ void lambda$null$20$PrepareLiveActivity(String str, String str2, long j) {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.sendBannedMessage(MessageHelper.convertToBanMsg(str, str2, String.valueOf(j), "1"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBigSize(boolean z) {
        showBigSizeTip(z);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.llChatRoot.getLayoutParams();
        layoutParams.height = z ? ScreenUtils.getScreenHeight() / 2 : SizeUtils.dp2px(220.0f);
        this.llChatRoot.setLayoutParams(layoutParams);
        this.liveChatMsgView.setChatMsgBigSize(z);
    }

    private void showLiveView() {
        initBottomMoreDialog();
        this.rlLiveRoot.setVisibility(0);
        this.tvCountDown.setVisibility(0);
        this.rlWatermarkBg.setVisibility(0);
        this.mPreStartLiveView.setVisibility(4);
    }

    private void startLive() {
        if (this.isCameraOpenFail || !this.isCameraPermissions) {
            showCameraDialog();
        } else if (!this.isMicPermissions) {
            PermissionDialog.newInstance(PermissionDialog.MIC_TIP).show(getSupportFragmentManager());
        } else {
            ((PrePareLivePresenter) this.mPresenter).getRemainCount();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IPrepareLiveView
    public void onStartLiveSuccess(AnchorStartLiveEntity anchorStartLiveEntity) {
        this.isBtnStartClick = false;
        if (anchorStartLiveEntity == null) {
            showToast(R$string.fq_start_live_error);
            return;
        }
        LogEventUtils.uploadStartLive(this.liveLabel, anchorStartLiveEntity.expGrade, anchorStartLiveEntity.liveId);
        goToLive(anchorStartLiveEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSpeakDialog() {
        BottomDialogUtils.showBottomSpeakSettingDialog(this, this.speakCDTime, this.speakLevel, this.isBanPostAll, new BottomDialogUtils.OnSpeakSettingListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$NsweqA_lBAX_6GOz-Gl3PnnaA9s
            @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.OnSpeakSettingListener
            public final void speakSettingDone(boolean z, String str, String str2) {
                PrepareLiveActivity.this.lambda$showSpeakDialog$22$PrepareLiveActivity(z, str, str2);
            }
        });
    }

    public /* synthetic */ void lambda$showSpeakDialog$22$PrepareLiveActivity(boolean z, String str, String str2) {
        if (!TextUtils.equals(this.speakLevel, str2)) {
            if (NumberUtils.string2int(this.defaultSpeakLevel) > NumberUtils.string2int(str2)) {
                showToast(R$string.fq_speak_level_too_low);
            } else {
                this.speakLevel = str2;
                WsManager wsManager = this.wsManager;
                if (wsManager != null) {
                    wsManager.sendSpeakLevelMessage(MessageHelper.convertToSpeakLevelMsg(this.liveId, str2));
                }
            }
        }
        if (this.isBanPostAll != z) {
            this.isBanPostAll = z;
            WsManager wsManager2 = this.wsManager;
            if (wsManager2 != null) {
                wsManager2.sendBannedAllMessage(MessageHelper.convertToAllBanMsg(this.liveId, z));
            }
        }
        if (!TextUtils.equals(this.speakCDTime, str)) {
            this.speakCDTime = str;
            WsManager wsManager3 = this.wsManager;
            if (wsManager3 == null) {
                return;
            }
            wsManager3.sendPostIntervalMessage(MessageHelper.convertToPostInterval(this.liveId, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickMirror() {
        if (this.isCameraOpenFail) {
            showCameraDialog();
        } else {
            setMirror();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickBeauty() {
        if (this.isCameraOpenFail) {
            showCameraDialog();
        } else {
            clickBeautyDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickCamera() {
        if (this.isCameraOpenFail) {
            showCameraDialog();
        } else {
            switchCamera();
        }
    }

    private void showBigSizeTip(boolean z) {
        int i = 4;
        this.liveBottomBar.setVisibility(z ? 4 : 0);
        TextView textView = this.tvCloseBigSize;
        if (z) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserCard(UserEntity userEntity) {
        if (TextUtils.equals(userEntity.getUserId(), this.userId)) {
            if (this.isStartGetAnchorInfo) {
                return;
            }
            ((PrePareLivePresenter) this.mPresenter).getAnchorInfo(userEntity.getUserId());
            this.isStartGetAnchorInfo = true;
            return;
        }
        showUserAvatarDialog(userEntity);
    }

    private void setAnchorCoverImg() {
        this.mAnchorCoverImg.setVisibility(0);
        AnchorStartLiveEntity anchorStartLiveEntity = this.startLiveInfoEntity;
        if (anchorStartLiveEntity == null) {
            GlideUtils.loadImageBlur(this.mContext, this.mAnchorCoverImg, R$drawable.fq_shape_default_cover_bg);
        } else {
            GlideUtils.loadImageBlur(this.mContext, this.mAnchorCoverImg, TextUtils.isEmpty(anchorStartLiveEntity.liveCoverUrl) ? this.startLiveInfoEntity.avatar : this.startLiveInfoEntity.liveCoverUrl, R$drawable.fq_shape_default_cover_bg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCameraDialog() {
        PermissionDialog permissionDialog = this.mCameraDialog;
        if (permissionDialog == null || permissionDialog.isAdded()) {
            return;
        }
        this.mCameraDialog.show(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissCameraDialog() {
        PermissionDialog permissionDialog = this.mCameraDialog;
        if (permissionDialog == null || !permissionDialog.isAdded()) {
            return;
        }
        this.mCameraDialog.dismiss();
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.pushManager.onResume(this.isCameraPermissions, this.isLiving);
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onResume();
        }
        if (this.isLiving) {
            if (!this.isNoNetEvent) {
                cancelPausedTimer();
            }
            this.isPushInBackground = false;
            if (!this.isWarn) {
                return;
            }
            WarnDialog warnDialog = this.endWarnDialog;
            if (warnDialog != null && warnDialog.isAdded()) {
                this.endWarnDialog.dismiss();
                this.endWarnDialog = null;
            }
            this.startWarnDialog = WarnDialog.newInstance(WarnDialog.WARN_TIP);
            this.startWarnDialog.show(getSupportFragmentManager());
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.pushManager.onPause();
        if (this.isLiving) {
            this.isPushInBackground = true;
            this.isNoNetEvent = false;
            startPausedTimer();
        }
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onPause();
        }
    }

    private void startPlayAnim() {
        this.tvCountDown.setVisibility(0);
        Observable.interval(1L, TimeUnit.SECONDS).take(4L).map($$Lambda$PrepareLiveActivity$vO3w6MUi69BGJRwTYCY1aYyOg.INSTANCE).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.28
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                PrepareLiveActivity.this.startPlayAnimDisposable = disposable;
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                PrepareLiveActivity.this.tvCountDown.setText(String.valueOf(l));
                if (PrepareLiveActivity.this.countDownAnimSet != null) {
                    PrepareLiveActivity.this.countDownAnimSet.start();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                PrepareLiveActivity.this.tvCountDown.setVisibility(8);
            }
        });
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.InputTextMsgDialog.OnTextSendListener
    public void onTextSend(String str) {
        if (isSocketClose()) {
            showToast(R$string.fq_text_network_error_chat);
        } else if (this.isBanGroup) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgSendName(UserInfoManager.getInstance().getUserNickname());
            chatEntity.setMsgText(str);
            chatEntity.setMsgType(3);
            chatEntity.setSex(UserInfoManager.getInstance().getUserSex());
            chatEntity.setRole("1");
            chatEntity.setUserRole("1");
            chatEntity.setUid(this.userId);
            chatEntity.setUserAvatar(UserInfoManager.getInstance().getAvatar());
            chatEntity.setExpGrade(AppUtils.formatExpGrade(this.myUserGrade));
            chatEntity.setMarkUrls(this.myMarkUrls);
            this.liveChatMsgView.addChatMsg(chatEntity);
        } else {
            WsManager wsManager = this.wsManager;
            if (wsManager == null) {
                return;
            }
            wsManager.sendChatMessage(MessageHelper.convertToChatMsg(str, 0));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack
    public void onBackThreadReceiveMessage(SocketMessageEvent socketMessageEvent) {
        char c;
        if (!AppUtils.isSocketEventSuc(socketMessageEvent.code)) {
            showToast(socketMessageEvent.message);
            return;
        }
        final SocketMessageEvent.ResultData resultData = socketMessageEvent.resultData;
        if (resultData == null || isFinishing() || this.workHandler == null || this.mainHandler == null) {
            return;
        }
        String str = socketMessageEvent.messageType;
        char c2 = 65535;
        int i = 0;
        switch (str.hashCode()) {
            case -1981791680:
                if (str.equals(ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -1603387347:
                if (str.equals(ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1598856750:
                if (str.equals(ConnectSocketParams.MESSAGE_BANPOSTALL_TYPE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1330790228:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_ACCEPT)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1302490523:
                if (str.equals(ConnectSocketParams.MESSAGE_CONSUME_NOTIFY_TYPE)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1268961704:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1256385881:
                if (str.equals(ConnectSocketParams.MESSAGE_TOKEN_INVALID_NOTIFY_TYPE)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1242004544:
                if (str.equals(ConnectSocketParams.MESSAGE_CHAT_RECEIPT_TYPE)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1039689911:
                if (str.equals(ConnectSocketParams.MESSAGE_NOTIFY_TYPE)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -993690229:
                if (str.equals(ConnectSocketParams.MESSAGE_PROP_SEND_TYPE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -992867598:
                if (str.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_BROADCAST_TYPE)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -941691210:
                if (str.equals(ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -842142792:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_REFUSE)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -634778976:
                if (str.equals(ConnectSocketParams.MESSAGE_FORBID_LIVE_TYPE)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -566357442:
                if (str.equals(ConnectSocketParams.MESSAGE_CHAT_BUY_LIVE_TICKET_TYPE)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -370196576:
                if (str.equals(ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -337843889:
                if (str.equals(ConnectSocketParams.MESSAGE_BAN_POST_TYPE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -236148015:
                if (str.equals(ConnectSocketParams.MESSAGE_LIVECONTROL_TYPE)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 3052376:
                if (str.equals(ConnectSocketParams.MESSAGE_CHAT_TYPE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3172656:
                if (str.equals(ConnectSocketParams.MESSAGE_GIFT_TYPE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3641990:
                if (str.equals(ConnectSocketParams.MESSAGE_WARN_TYPE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 96667762:
                if (str.equals(ConnectSocketParams.MESSAGE_ENTER_TYPE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 98509126:
                if (str.equals(ConnectSocketParams.MESSAGE_GOOUT_TYPE)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 99368259:
                if (str.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_GOOUT_TYPE)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 102846135:
                if (str.equals("leave")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 317295308:
                if (str.equals(ConnectSocketParams.MESSAGE_USER_GRADE_TYPE)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 395254178:
                if (str.equals(ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 441119852:
                if (str.equals(ConnectSocketParams.MESSAGE_PUT_GIFT_BOX_TYPE)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 441614241:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_SHOW)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 487782924:
                if (str.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_BANPOST_TYPE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 720694193:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE_COMPLETE)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 798249924:
                if (str.equals(ConnectSocketParams.MESSAGE_LIVE_SETTING_TYPE)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 805483582:
                if (str.equals(ConnectSocketParams.MESSAGE_START_INTIMATE_TASK)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1316826486:
                if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_BID_FAILED)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1680327801:
                if (str.equals(ConnectSocketParams.MESSAGE_GIFT_TRUMPET_TYPE)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 2021199175:
                if (str.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_NOTIFIED_TYPE)) {
                    c = 23;
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
                        dealReceiveAnimFromSocket(resultData);
                        i++;
                    }
                    return;
                }
                dealReceiveAnimFromSocket(resultData);
                return;
            case 1:
                dealReceivePropMsgFromSocket(resultData);
                return;
            case 2:
                dealEnterMsgFromSocket(resultData);
                return;
            case 3:
                if (resultData.isPrivateMsg()) {
                    dealPrivateMsgFromSocket(resultData);
                    return;
                } else {
                    dealChatMsgFromSocket(resultData);
                    return;
                }
            case 4:
                dealWarnMsgFromSocket(resultData);
                return;
            case 5:
                dealLeaveMsgFromSocket(resultData);
                return;
            case 6:
                dealSuperBanPostMsgFromSocket(resultData);
                return;
            case 7:
                dealBanPostMsgFromSocket(resultData);
                return;
            case '\b':
                dealBanPostAllMsgFromSocket(resultData);
                return;
            case '\t':
                dealNotifyMsgFromSocket(resultData);
                return;
            case '\n':
                if (resultData.isUnFreeze()) {
                    return;
                }
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$4yvOUl6FYimrJ_T21K7LQhRhYPU
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.this.dealForbidMsgFromSocket();
                    }
                });
                return;
            case 11:
            case '\f':
                dealKickOutMsgFromSocket(resultData);
                return;
            case '\r':
                dealLiveControlMsgFromSocket(resultData);
                return;
            case 14:
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$n85-lZc3StdsI8l2nvAufU6vfSg
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.this.dealTokenInvalidMsgFromSocket();
                    }
                });
                return;
            case 15:
                if (resultData.isQMTaskGift() && NumberUtils.string2int(resultData.giftNum) > 1) {
                    int string2int2 = NumberUtils.string2int(resultData.giftNum);
                    resultData.giftNum = "1";
                    while (i < string2int2) {
                        dealGiftTrumpetMsgFromSocket(resultData);
                        i++;
                    }
                    return;
                }
                dealGiftTrumpetMsgFromSocket(resultData);
                return;
            case 16:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 17:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 18:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE;
                dealSysNoticeMsgFromSocket(resultData);
                return;
            case 19:
                resultData.sysNoticeType = ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE;
                String str2 = resultData.type;
                switch (str2.hashCode()) {
                    case -1671569065:
                        if (str2.equals(ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN)) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case -648591709:
                        if (str2.equals(ConnectSocketParams.RESULT_DATA_DAY_RANK_UP)) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 989204668:
                        if (str2.equals("recommend")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 1525144764:
                        if (str2.equals(ConnectSocketParams.RESULT_DATA_TYPE_HITNOTIFY)) {
                            c2 = 1;
                            break;
                        }
                        break;
                }
                if (c2 == 0) {
                    dealSysNoticeMsgFromSocket(resultData);
                    return;
                } else if (c2 == 1) {
                    if (!AppUtils.isEnableTurntable()) {
                        return;
                    }
                    dealSysLuckMsgFromSocket(resultData);
                    return;
                } else if (c2 == 2) {
                    resultData.sysNoticeType = ConnectSocketParams.RESULT_DATA_DAY_RANK_UP;
                    dealAnchorInfoNoticeMsgFromSocket(resultData);
                    return;
                } else if (c2 != 3) {
                    return;
                } else {
                    resultData.sysNoticeType = ConnectSocketParams.RESULT_DATA_ANCHOR_OPEN;
                    dealAnchorInfoNoticeMsgFromSocket(resultData);
                    return;
                }
            case 20:
                dealConsumeMsgFormSocket(resultData);
                return;
            case 21:
                dealLiveSettingMsgFromSocket(resultData);
                return;
            case 22:
                dealGiftBoxPutMsgFromSocket(resultData);
                return;
            case 23:
                showToast(getString(R$string.fq_gift_box_toast_tips, new Object[]{resultData.presenterName, resultData.propNum, resultData.propName}));
                return;
            case 24:
                dealGiftBoxBroadcastMsgFromSocket(resultData);
                return;
            case 25:
                dealExpGradeUpdate(resultData);
                return;
            case 26:
                if (!CacheUtils.isLocalCacheComponents(resultData.gameId)) {
                    return;
                }
                String str3 = resultData.type;
                int hashCode = str3.hashCode();
                if (hashCode != 49) {
                    if (hashCode == 50 && str3.equals("2")) {
                        c2 = 1;
                    }
                } else if (str3.equals("1")) {
                    c2 = 0;
                }
                if (c2 == 0) {
                    dealBroadcastMsgFromSocket(resultData);
                    return;
                } else if (c2 != 1 || !SPUtils.getInstance().getBoolean(ConstantUtils.NOTICE_GAME_KEY, true)) {
                    return;
                } else {
                    dealGameNoticeMsgFromSocket(resultData);
                    return;
                }
            case 27:
                DBUtils.updatePrivateMsgDetail(resultData.senderId, resultData.messageId, resultData.status);
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$uEm3Afrvrlo5o3ocWkMrhvcuNOE
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.this.lambda$onBackThreadReceiveMessage$24$PrepareLiveActivity(resultData);
                    }
                });
                return;
            case 28:
                updateAnchorContribution(resultData);
                return;
            case 29:
                dealIntimateTaskFromSocket(resultData);
                return;
            case 30:
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.29
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PrepareLiveActivity.this.mLiveAdBannerBottomView.isShowQMInviteChatMsg(resultData)) {
                            ChatEntity chatEntity = new ChatEntity();
                            chatEntity.setMsgType(10);
                            chatEntity.setMsgText(((BaseActivity) PrepareLiveActivity.this).mContext.getString(R$string.fq_qm_receive_interactive_invite));
                            PrepareLiveActivity.this.liveChatMsgView.addChatMsg(chatEntity);
                        }
                        PrepareLiveActivity.this.mLiveAdBannerBottomView.dealIntimateTaskShowFromSocket(true, resultData);
                        PrepareLiveActivity prepareLiveActivity = PrepareLiveActivity.this;
                        if (prepareLiveActivity.isDialogFragmentAdded(prepareLiveActivity.qmTaskListDialog)) {
                            PrepareLiveActivity.this.qmTaskListDialog.sendTaskListRequest();
                        } else if (TextUtils.equals(resultData.putUserId, UserInfoManager.getInstance().getUserId()) || PrepareLiveActivity.this.mLiveAdBannerBottomView.getTaskListSize() <= 1) {
                        } else {
                            PrepareLiveActivity.this.mLiveAdBannerBottomView.showRedPoint();
                        }
                    }
                });
                return;
            case 31:
            case ' ':
            case '!':
            default:
                return;
            case '\"':
                if (isDialogFragmentAdded(this.qmTaskListDialog)) {
                    this.qmTaskListDialog.updateTaskChargeList(resultData.intimateTaskChargeList);
                }
                this.mLiveAdBannerBottomView.dealIntimateTaskChargeFormSocket(resultData);
                return;
            case '#':
                this.mLiveAdBannerBottomView.dealIntimateTaskChargeCompleteFromSocket(resultData);
                if (isDialogFragmentAdded(this.qmTaskListDialog)) {
                    this.qmTaskListDialog.completeTaskCharge(resultData.taskId);
                    return;
                } else {
                    handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$Mnp_IoEcoskHHtNqE4KSXkHZeAE
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrepareLiveActivity.this.lambda$onBackThreadReceiveMessage$25$PrepareLiveActivity();
                        }
                    });
                    return;
                }
        }
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$24$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null || !privateMsgDialog.isAdded()) {
            return;
        }
        this.privateMsgDialog.changeMsgStatus(resultData.messageId, resultData.status);
    }

    public /* synthetic */ void lambda$onBackThreadReceiveMessage$25$PrepareLiveActivity() {
        this.mLiveAdBannerBottomView.showRedPoint();
    }

    private void dealIntimateTaskFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
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
        synchronized (PrepareLiveActivity.class) {
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

    private void dealBroadcastMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(resultData.content);
        chatEntity.setPropId(resultData.gameId);
        chatEntity.setMsgType(16);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$VYHs3umDsHBBNxy5kz-OYfx_NTU
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealBroadcastMsgFromSocket$26$PrepareLiveActivity(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$dealBroadcastMsgFromSocket$26$PrepareLiveActivity(ChatEntity chatEntity) {
        this.liveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealGameNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
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

    private void dealExpGradeUpdate(SocketMessageEvent.ResultData resultData) {
        showReceiveMsgOnChatList(resultData, "dealExpGradeUpdate", 15);
    }

    private void dealGiftTrumpetMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
            if (this.giftNoticeQueue == null) {
                this.giftNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.giftNoticeQueue.size() == 9999) {
                this.giftNoticeQueue.poll();
            }
            this.giftNoticeQueue.offer(resultData);
            sendWorkHandlerEmptyMessage(10004);
        }
    }

    private void dealLiveControlMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        final String str = resultData.isManager() ? "3" : "2";
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$LrN6FDAFDBy7DnPzWEnlXPt9sbo
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealLiveControlMsgFromSocket$27$PrepareLiveActivity(resultData, str);
            }
        });
        this.mLivePusherInfoView.sortUserList(resultData.targetId, null, null, -1, str);
        showReceiveMsgOnChatList(resultData, resultData.userName, 6);
    }

    public /* synthetic */ void lambda$dealLiveControlMsgFromSocket$27$PrepareLiveActivity(SocketMessageEvent.ResultData resultData, String str) {
        this.liveChatMsgView.updateRoleItemDataByUid(resultData.targetId, str);
    }

    private void dealBanPostAllMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        showReceiveMsgOnChatList(resultData, getString(TextUtils.equals("1", resultData.banPostAllStatus) ? R$string.fq_anchor_start_banned : R$string.fq_anchor_cancel_banned), 5);
    }

    private void dealReceiveAnimFromSocket(SocketMessageEvent.ResultData resultData) {
        WsManager wsManager;
        if (!resultData.isScoreGift()) {
            updateAnchorContribution(resultData);
        }
        if (!this.isShieldSmallGift || resultData.isPriceProps()) {
            long currentTimeMillis = System.currentTimeMillis();
            GiftItemEntity giftItemEntity = GiftDownLoadManager.getInstance().getGiftItemEntity(resultData.markId);
            if (giftItemEntity == null) {
                if (this.isStartGiftUpdate) {
                    return;
                }
                this.isStartGiftUpdate = true;
                ((PrePareLivePresenter) this.mPresenter).getGiftList();
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
            giftItemEntity.sex = resultData.sex;
            giftItemEntity.expGrade = resultData.expGrade;
            giftItemEntity.weekStar = resultData.isWeekStar;
            giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
            giftItemEntity.nobilityType = resultData.nobilityType;
            giftItemEntity.appId = resultData.appId;
            giftItemEntity.openId = resultData.openId;
            giftItemEntity.giftNum = resultData.giftNum;
            playReceiveAnimGift(giftItemEntity);
            if (z2) {
                if (z && (wsManager = this.wsManager) != null) {
                    wsManager.addReceiveBigAnim(giftItemEntity);
                }
                if (giftItemEntity.sendIndex == 0) {
                    giftItemEntity.sendIndex = 1;
                }
                showReceiveMsgOnChatList(resultData, AppUtils.appendBatchGiftString(giftItemEntity), 1);
            } else if (giftItemEntity.isBigAnim()) {
                WsManager wsManager2 = this.wsManager;
                if (wsManager2 != null) {
                    wsManager2.addReceiveBigAnim(giftItemEntity);
                }
                if (giftItemEntity.sendIndex == 0) {
                    giftItemEntity.sendIndex = 1;
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
    }

    private void dealSysLuckMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
            if (this.luckNoticeQueue == null) {
                this.luckNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.luckNoticeQueue.size() == 9999) {
                this.luckNoticeQueue.poll();
            }
            this.luckNoticeQueue.offer(resultData);
        }
        sendWorkHandlerEmptyMessage(ConstantUtils.SYS_LUCK_HIT);
    }

    private void dealSysNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
            if (this.sysNoticeQueue == null) {
                this.sysNoticeQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.sysNoticeQueue.size() == 9999) {
                this.sysNoticeQueue.poll();
            }
            this.sysNoticeQueue.offer(resultData);
            sendWorkHandlerEmptyMessage(10005);
        }
    }

    private void dealAnchorInfoNoticeMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (PrepareLiveActivity.class) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void dealConsumeMsgFormSocket(final SocketMessageEvent.ResultData resultData) {
        char c;
        updateAnchorContribution(resultData);
        String str = resultData.type;
        switch (str.hashCode()) {
            case -903754697:
                if (str.equals(ConstantUtils.SOCKET_CONSUME_TYPE_RENEW_NOBILITY)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -736437516:
                if (str.equals("openNobility")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 989204668:
                if (str.equals("recommend")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1524196987:
                if (str.equals(ConstantUtils.SOCKET_CONSUME_TYPE_OPEN_GUARD)) {
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
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$6yA3Erkkdh0gPw4_eNS8h7IlrnI
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealConsumeMsgFormSocket$28$PrepareLiveActivity(resultData);
                }
            });
            addBigAnim(resultData, ConstantUtils.BIG_ANIM_OPEN_GUARD_TYPE);
            this.liveAnimationView.loadGuardOpenAnimation(resultData);
        } else if (c != 1) {
            if (c == 2) {
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$nrvNjYnQZY3njo0lSXBPOCwMIpM
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.this.lambda$dealConsumeMsgFormSocket$30$PrepareLiveActivity(resultData);
                    }
                });
            } else if (c != 3) {
            } else {
                dismissDialogs();
                NobilityHdRecommendDialog.newInstance(resultData.isAnonymousRecommendBoolean(), resultData.userName, resultData.recommendTime).show(getSupportFragmentManager());
            }
        } else {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$IqQh-5xL2CbApLUKmn3oaHJZuhQ
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealConsumeMsgFormSocket$29$PrepareLiveActivity(resultData);
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

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$28$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.updateOpenGuardCount(resultData.anchorGuardCount);
        }
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgType(12);
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setExpGrade(resultData.expGrade);
        chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        chatEntity.setMsgText(AppUtils.getGuardTypeStr(this.mContext, resultData.guardType));
        chatEntity.setRole(resultData.role);
        chatEntity.setMarkUrls(resultData.markUrls);
        this.liveChatMsgView.addChatMsg(chatEntity);
        this.liveChatMsgView.updateGuardTypeItemDataByUid(resultData.userId, NumberUtils.string2int(resultData.guardType));
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$29$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        if (resultData.isChatRegionShowNotify()) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(14);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(resultData.expGrade);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setMarkUrls(resultData.markUrls);
            chatEntity.setMsgText(getString(R$string.fq_nobility_msg_open_tips, new Object[]{AppUtils.getNobilityBadgeName(this.mContext, resultData.nobilityType)}));
            this.liveChatMsgView.addChatMsg(chatEntity);
        }
        this.liveChatMsgView.updateNobilityTypeItemDataByUid(resultData.userId, resultData.nobilityType);
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
    }

    public /* synthetic */ void lambda$dealConsumeMsgFormSocket$30$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        if (resultData.isChatRegionShowNotify()) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(14);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(resultData.expGrade);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setMarkUrls(resultData.markUrls);
            chatEntity.setMsgText(getString(R$string.fq_nobility_msg_renewal_tips, new Object[]{AppUtils.getNobilityBadgeName(this.mContext, resultData.nobilityType)}));
            this.liveChatMsgView.addChatMsg(chatEntity);
        }
        this.liveChatMsgView.updateNobilityTypeItemDataByUid(resultData.userId, resultData.nobilityType);
        this.mLivePusherInfoView.sortUserList(resultData.userId, resultData.guardType, resultData.expGrade, resultData.nobilityType, resultData.role);
    }

    private void dealReceivePropMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        if (resultData.isPriceProps()) {
            updateAnchorContribution(resultData);
        }
        if (!this.isShieldSmallGift || resultData.isPriceProps()) {
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
                giftItemEntity.sendIndex = giftIndexEntity.sendIndex;
                hashMap.put(resultData.propId, giftIndexEntity);
                this.receivePropMap.put(resultData.userId, hashMap);
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
            giftItemEntity.avatar = resultData.avatar;
            giftItemEntity.role = resultData.role;
            giftItemEntity.userRole = resultData.userRole;
            giftItemEntity.sex = resultData.sex;
            giftItemEntity.nobilityType = resultData.nobilityType;
            giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
            giftItemEntity.expGrade = resultData.expGrade;
            resultData.giftName = resultData.propName;
            resultData.markId = resultData.propId;
            giftItemEntity.effect_type = NumberUtils.string2int(resultData.animalType);
            giftItemEntity.animalType = giftItemEntity.effect_type;
            giftItemEntity.imgurl = resultData.coverUrl;
            giftItemEntity.name = resultData.propName;
            giftItemEntity.markId = resultData.propId;
            giftItemEntity.giftNum = resultData.giftNum;
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
    }

    private void dealGiftBoxPutMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$XO1oYqwbLF8X8fBqN2s1X3aVjgs
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealGiftBoxPutMsgFromSocket$31$PrepareLiveActivity(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$dealGiftBoxPutMsgFromSocket$31$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        GiftBoxEntity giftBoxEntity = new GiftBoxEntity();
        giftBoxEntity.expirationTime = NumberUtils.string2long(resultData.expirationTime);
        giftBoxEntity.openTime = NumberUtils.string2long(resultData.openTime);
        giftBoxEntity.giftBoxUniqueCode = resultData.giftBoxUniqueCode;
        giftBoxEntity.presenterAvatar = resultData.presenterAvatar;
        giftBoxEntity.presenterId = resultData.presenterId;
        giftBoxEntity.presenterName = resultData.presenterName;
        giftBoxEntity.liveId = this.liveId;
        giftBoxEntity.userId = this.userId;
        this.mGiftBoxView.addOneBox(giftBoxEntity);
    }

    private void dealLiveSettingMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        String str = resultData.changeType;
        if (((str.hashCode() == 1559674578 && str.equals(ConnectSocketParams.MESSAGE_SPEAKLEVEL_TYPE)) ? (char) 0 : (char) 65535) != 0) {
            return;
        }
        this.speakLevel = resultData.changeValue;
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(getString(R$string.fq_speak_level_tip_for_socket, new Object[]{this.speakLevel}));
        chatEntity.setExpGrade(this.speakLevel);
        chatEntity.setMsgType(11);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$XbDq-Hyc6hSfBHbLGuqngHo2NxM
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealLiveSettingMsgFromSocket$32$PrepareLiveActivity(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$dealLiveSettingMsgFromSocket$32$PrepareLiveActivity(ChatEntity chatEntity) {
        this.liveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealSuperBanPostMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        showReceiveMsgOnChatList(resultData, resultData.userName, 4);
    }

    private void dealKickOutMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(this.userId, resultData.userId)) {
            resultData.userName = getString(R$string.fq_anchor);
        }
        showReceiveMsgOnChatList(resultData, resultData.userName, 9);
    }

    private void dealEnterMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        addEnterMsgToQueue(resultData);
    }

    private void dealChatMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        if (this.shieldedList.contains(resultData.userId)) {
            return;
        }
        this.chatContent = resultData.content;
        if (this.isTranOpen) {
            TranslationUtils.translationFromText(this.chatContent, new TranslationUtils.OnTransListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$OZCDCgnsqyP-OR-eQrRyH_ivfPw
                @Override // com.tomatolive.library.utils.TranslationUtils.OnTransListener
                public final void onSuc(String str) {
                    PrepareLiveActivity.this.lambda$dealChatMsgFromSocket$33$PrepareLiveActivity(resultData, str);
                }
            });
        } else {
            showReceiveMsgOnChatList(resultData, this.chatContent, 3);
        }
        this.chatCount.incrementAndGet();
    }

    public /* synthetic */ void lambda$dealChatMsgFromSocket$33$PrepareLiveActivity(SocketMessageEvent.ResultData resultData, String str) {
        showReceiveMsgOnChatList(resultData, str, 3);
    }

    private void dealWarnMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals("1", resultData.action)) {
            this.pushManager.setWarnPush();
        } else {
            this.pushManager.removeWarnPush();
        }
        if (this.isPushInBackground) {
            this.isWarn = TextUtils.equals("1", resultData.action);
        } else {
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$cQJKwimknNoxZLZKK52E-KOVDQg
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealWarnMsgFromSocket$34$PrepareLiveActivity(resultData);
                }
            });
        }
    }

    public /* synthetic */ void lambda$dealWarnMsgFromSocket$34$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals("1", resultData.action)) {
            WarnDialog warnDialog = this.endWarnDialog;
            if (warnDialog != null && warnDialog.isAdded()) {
                this.endWarnDialog.dismiss();
                this.endWarnDialog = null;
            }
            this.startWarnDialog = WarnDialog.newInstance(WarnDialog.WARN_TIP);
            this.startWarnDialog.show(getSupportFragmentManager());
            return;
        }
        WarnDialog warnDialog2 = this.startWarnDialog;
        if (warnDialog2 != null && warnDialog2.isAdded()) {
            this.startWarnDialog.dismiss();
            this.startWarnDialog = null;
        }
        this.endWarnDialog = WarnDialog.newInstance(WarnDialog.STOP_WARN_TIP);
        this.endWarnDialog.show(getSupportFragmentManager());
    }

    private void dealLeaveMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(resultData.userId, this.userId)) {
            return;
        }
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$TKkrTuEv86pWNVSZTBqwsoRGrXY
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealLeaveMsgFromSocket$35$PrepareLiveActivity(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$dealLeaveMsgFromSocket$35$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.removeUserItemById(resultData.userId);
        this.mLivePusherInfoView.setLivePopularityCount(this.onLineCount.get());
    }

    private void dealBanPostMsgFromSocket(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.equals(getString(R$string.fq_system), resultData.userName) || TextUtils.equals(this.userId, resultData.userId)) {
            resultData.userName = getString(R$string.fq_anchor);
        }
        showReceiveMsgOnChatList(resultData, resultData.userName, 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealTokenInvalidMsgFromSocket() {
        ((PrePareLivePresenter) this.mPresenter).setEnterOrLeaveLiveRoomMsg(this.liveId, "leave");
        startTokenDialogService();
        sendLeaveMessage();
        closePusher();
        this.isLiving = false;
        RxTimerUtils.getInstance().timerBindDestroy(getLifecycleProvider(), 300L, TimeUnit.MILLISECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$-vT6fUwaMi1jowdNLr6Be1sU6l8
            @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
            public final void action(long j) {
                PrepareLiveActivity.this.lambda$dealTokenInvalidMsgFromSocket$36$PrepareLiveActivity(j);
            }
        });
    }

    public /* synthetic */ void lambda$dealTokenInvalidMsgFromSocket$36$PrepareLiveActivity(long j) {
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealForbidMsgFromSocket() {
        WarnDialog warnDialog = this.endWarnDialog;
        if (warnDialog != null && warnDialog.isAdded()) {
            this.endWarnDialog.dismiss();
            this.endWarnDialog = null;
        }
        WarnDialog warnDialog2 = this.startWarnDialog;
        if (warnDialog2 != null && warnDialog2.isAdded()) {
            this.startWarnDialog.dismiss();
            this.startWarnDialog = null;
        }
        prepareFinish();
        ((PrePareLivePresenter) this.mPresenter).getLiveEndInfo(this.liveId, this.streamName, 2);
    }

    private void dealNotifyMsgFromSocket(final SocketMessageEvent.ResultData resultData) {
        String str = resultData.type;
        if (TextUtils.isEmpty(str)) {
            return;
        }
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
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$bwO6MpBW_kmc9Ef6NDUNcEtT1h4
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealNotifyMsgFromSocket$37$PrepareLiveActivity(resultData);
                }
            });
        } else if (c != 1) {
        } else {
            this.isBanGroup = true;
        }
    }

    public /* synthetic */ void lambda$dealNotifyMsgFromSocket$37$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgType(8);
        chatEntity.setMsgText(resultData.typeMsg);
        this.liveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealGiftBoxBroadcastMsgFromSocket(SocketMessageEvent.ResultData resultData) {
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

    private void stopSocket() {
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.setOnBackgroundSocketCallBack(null);
            this.wsManager.stopService();
            this.wsManager = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSocketTipMsg(int i) {
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText(getString(i));
        chatEntity.setMsgType(7);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$hjGIRTEnPK6irHE9Wb_QVnfUlvI
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$addSocketTipMsg$38$PrepareLiveActivity(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$addSocketTipMsg$38$PrepareLiveActivity(ChatEntity chatEntity) {
        this.liveChatMsgView.addChatMsg(chatEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSocketReconnectMsg() {
        final ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMsgText("reconnect");
        chatEntity.setMsgType(2);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$3p5wZjgANqhEgDYtWvMgcDxvHSI
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$addSocketReconnectMsg$39$PrepareLiveActivity(chatEntity);
            }
        });
    }

    public /* synthetic */ void lambda$addSocketReconnectMsg$39$PrepareLiveActivity(ChatEntity chatEntity) {
        this.liveChatMsgView.addChatMsg(chatEntity);
        changePrivateMessageNetStatus(false);
    }

    private void addBigAnim(SocketMessageEvent.ResultData resultData, int i) {
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.name = resultData.userName;
        giftItemEntity.bigAnimType = i;
        giftItemEntity.avatar = resultData.avatar;
        giftItemEntity.nobilityType = resultData.nobilityType;
        giftItemEntity.guardType = NumberUtils.string2int(resultData.guardType);
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.addReceiveBigAnim(giftItemEntity);
        }
    }

    @Override // com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack
    public void onBackThreadReceiveBigAnimMsg(GiftItemEntity giftItemEntity) {
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

    private void playReceiveAnimGift(final GiftItemEntity giftItemEntity) {
        if (giftItemEntity == null) {
            return;
        }
        final GiftAnimModel giftAnimModel = new GiftAnimModel();
        giftAnimModel.setGiftId(giftItemEntity.markId).setProp(giftItemEntity.isPropBigAnimType()).setOnLineUrl(giftItemEntity.animalUrl).setOnlineDefaultUrl(giftItemEntity.onlineDefaultUrl).setEffectType(String.valueOf(giftItemEntity.effect_type)).setGiftName(giftItemEntity.name).setGiftCount(1).setGiftPic(giftItemEntity.imgurl).setGiftPrice(giftItemEntity.price).setSendGiftTime(Long.valueOf(System.currentTimeMillis())).setCurrentStart(true).setGiftDirPath(giftItemEntity.giftDirPath).setGiftShowTime(NumberUtils.formatMillisecond(giftItemEntity.duration)).setSendIndex(giftItemEntity.sendIndex).setAnimationListener(null).setGiftNum(giftItemEntity.giftNum).setSendUserId(giftItemEntity.userId).setSendUserName(giftItemEntity.sendUserName).setSendUserExpGrade(giftItemEntity.expGrade).setSendUserGuardType(giftItemEntity.guardType).setSendUserNobilityType(giftItemEntity.nobilityType).setSendUserRole(giftItemEntity.userRole).setSendRole(giftItemEntity.role).setSendUserSex(giftItemEntity.sex).setAppId(giftItemEntity.appId).setOpenId(giftItemEntity.openId).setSendUserAvatar(giftItemEntity.avatar);
        giftAnimModel.setJumpCombo(giftItemEntity.sendIndex);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$DMbUqGlIh3o9tXcRLwRCi5puHZE
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$playReceiveAnimGift$40$PrepareLiveActivity(giftAnimModel, giftItemEntity);
            }
        });
    }

    public /* synthetic */ void lambda$playReceiveAnimGift$40$PrepareLiveActivity(GiftAnimModel giftAnimModel, GiftItemEntity giftItemEntity) {
        this.liveAnimationView.loadReceiveGift(giftAnimModel, giftItemEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wsManagerNotifyBigAnim() {
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

    private void showReceiveMsgOnChatList(SocketMessageEvent.ResultData resultData, String str, int i) {
        String str2 = resultData.role;
        if (TextUtils.equals(resultData.userId, this.userId) && TextUtils.equals(str2, "5")) {
            str2 = "1";
        }
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setAnchorId(this.userId);
        chatEntity.setMsgSendName(resultData.userName);
        chatEntity.setMsgText(str);
        chatEntity.setMsgType(i);
        chatEntity.setSex(resultData.sex);
        chatEntity.setUid(resultData.userId);
        chatEntity.setRole(str2);
        chatEntity.setUserRole(resultData.userRole);
        chatEntity.setWeekStar(resultData.isWeekStar);
        chatEntity.setGiftName(resultData.giftName);
        chatEntity.setGiftNum(resultData.giftNum);
        chatEntity.setUserAvatar(resultData.avatar);
        chatEntity.setTargetAvatar(resultData.targetAvatar);
        chatEntity.setTargetName(resultData.targetName);
        chatEntity.setTargetId(resultData.targetId);
        chatEntity.setExpGrade(AppUtils.formatExpGrade(resultData.expGrade));
        chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        chatEntity.setOpenId(resultData.openId);
        chatEntity.setAppId(resultData.appId);
        chatEntity.setSomeoneBanPost(resultData.isSomeoneBanPost());
        chatEntity.setNobilityType(resultData.nobilityType);
        chatEntity.setMarkUrls(resultData.markUrls);
        if (resultData.isOpenGuardDanmu()) {
            chatEntity.setDanmuType(1);
        }
        if (resultData.isOpenNobilityDanmu()) {
            chatEntity.setDanmuType(2);
        }
        if (i == 6) {
            chatEntity.setSetManager(TextUtils.equals(resultData.action, "1"));
        }
        if (i == 15) {
            chatEntity.setMsgSendName(TextUtils.equals(resultData.userId, this.userId) ? this.mContext.getString(R$string.fq_anchor) : resultData.userName);
            chatEntity.setExpGrade(resultData.afterGrade);
        }
        addMsgToQueue(chatEntity);
        if (i == 3) {
            if (!resultData.isOpenGuardDanmu() && !resultData.isOpenNobilityDanmu()) {
                return;
            }
            if (!AppUtils.isGuardUser(NumberUtils.string2int(resultData.guardType)) && !AppUtils.isNobilityUser(resultData.nobilityType)) {
                return;
            }
            this.mLivePusherInfoView.addDanmuMsg(chatEntity);
        }
    }

    private void addMsgToQueue(ChatEntity chatEntity) {
        synchronized (PrepareLiveActivity.class) {
            if (this.receiveMsgQueue == null) {
                this.receiveMsgQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.receiveMsgQueue.size() == 9999) {
                this.receiveMsgQueue.poll();
            }
            this.receiveMsgQueue.offer(chatEntity);
            if (this.msgSleep) {
                this.msgSleep = false;
                sendWorkHandlerEmptyMessage(10001);
            }
        }
    }

    private void addEnterMsgToQueue(SocketMessageEvent.ResultData resultData) {
        if (resultData.isEnterHideBoolean()) {
            return;
        }
        synchronized (PrepareLiveActivity.class) {
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
            synchronized (PrepareLiveActivity.class) {
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
        if (!resultData.isHighNobility() || !resultData.isPlayNobilityEnterAnim()) {
            return;
        }
        this.liveAnimationView.loadNobilityEnterAnimation(resultData);
    }

    public /* synthetic */ boolean lambda$new$41$PrepareLiveActivity(Message message) {
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
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$eLslPhq_DrlPCOZMep5I9hhRoA8
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealIntimateTask$42$PrepareLiveActivity(poll);
            }
        });
    }

    private void dealGameNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!SPUtils.getInstance().getBoolean(ConstantUtils.NOTICE_GAME_KEY, true)) {
            this.gameNoticeQueue.clear();
        } else if (!this.canShowGameNotice.get() || (poll = this.gameNoticeQueue.poll()) == null) {
        } else {
            this.canShowGameNotice.set(false);
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$0NSyxZowkkny2IpsBHmJIZB5VmI
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealGameNotice$43$PrepareLiveActivity(poll);
                }
            });
        }
    }

    public /* synthetic */ void lambda$dealGameNotice$43$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setGameNoticeAnim(resultData, this.trumpetPlayPeriod);
    }

    private void dealSysLuckNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowLuckNotice.get() || (poll = this.luckNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowLuckNotice.set(false);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$43mvPUuJ7JQHHr-XNUYHqvEehFI
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealSysLuckNotice$44$PrepareLiveActivity(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealSysLuckNotice$44$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
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
        this.liveChatMsgView.addChatMsg(chatEntity);
    }

    private void dealGuardEnterMsg() {
        final SocketMessageEvent.ResultData poll;
        if (this.canShowGuardEnterMsg.get() && this.carFullAnimFinish.get() && (poll = this.guardEnterMsgQueue.poll()) != null) {
            this.canShowGuardEnterMsg.set(false);
            final String formatExpGrade = AppUtils.formatExpGrade(poll.expGrade);
            handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$deTRvmyztikFF1schX1aAfvBMxg
                @Override // java.lang.Runnable
                public final void run() {
                    PrepareLiveActivity.this.lambda$dealGuardEnterMsg$45$PrepareLiveActivity(poll, formatExpGrade);
                }
            });
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.guardEnterMsgQueue;
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
            return;
        }
        sendWorkHandlerEmptyMessage(10003);
    }

    public /* synthetic */ void lambda$dealGuardEnterMsg$45$PrepareLiveActivity(SocketMessageEvent.ResultData resultData, String str) {
        if (resultData.isEnterGuardType() && AppUtils.hasCar(resultData.carId)) {
            this.liveAnimationView.loadLiveEnterAnimation(resultData.guardType, GlideUtils.getGuardSVGADynamicEntity(this.mContext, resultData.avatar, resultData.userName, str, resultData.guardType));
            if (!resultData.isOnPlayCarAnim()) {
                return;
            }
            this.carFullAnimFinish.set(false);
            this.liveAnimationView.loadCarJoinAnimation(resultData, false);
        } else if (resultData.isEnterGuardType()) {
            this.liveAnimationView.loadLiveEnterAnimation(resultData.guardType, GlideUtils.getGuardSVGADynamicEntity(this.mContext, resultData.avatar, resultData.userName, str, resultData.guardType));
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
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$QuJtopfw1JiEa9xZQJj-MCUMUMo
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealGiftNotice$46$PrepareLiveActivity(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealGiftNotice$46$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setGiftNoticeAnim(resultData.userName, resultData.anchorName, resultData.giftNum, resultData.giftName, resultData.markId, this.giftTrumpetPlayPeriod);
    }

    private void dealAnchorInfoNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowAnchorInfoNotice.get() || (poll = this.anchorInfoNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowAnchorInfoNotice.set(false);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$42JYv4uZGkF292Wh6C8edFbTUSc
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealAnchorInfoNotice$47$PrepareLiveActivity(poll);
            }
        });
    }

    public /* synthetic */ void lambda$dealAnchorInfoNotice$47$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
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

    private void dealPrivateMsg() {
        SocketMessageEvent.ResultData poll;
        if (!this.canShowPrivateMsg.get() || (poll = this.privateMsgQueue.poll()) == null) {
            return;
        }
        if (TextUtils.equals(this.userId, poll.userId)) {
            sendWorkHandlerEmptyMessage(10006);
            return;
        }
        this.canShowPrivateMsg.set(false);
        final MsgDetailListEntity msgDetailListEntity = new MsgDetailListEntity();
        msgDetailListEntity.userId = this.userId;
        msgDetailListEntity.targetId = poll.userId;
        msgDetailListEntity.targetAvatar = poll.avatar;
        msgDetailListEntity.msg = poll.content;
        msgDetailListEntity.type = 2;
        msgDetailListEntity.targetName = poll.userName;
        msgDetailListEntity.time = String.valueOf(System.currentTimeMillis());
        msgDetailListEntity.status = 1;
        msgDetailListEntity.messageId = poll.messageId;
        DBUtils.saveOnePrivateMsgDetail(msgDetailListEntity);
        final MsgListEntity msgListEntity = new MsgListEntity();
        msgListEntity.userId = UserInfoManager.getInstance().getUserId();
        msgListEntity.appId = UserInfoManager.getInstance().getAppId();
        msgListEntity.targetId = poll.userId;
        msgListEntity.time = String.valueOf(System.currentTimeMillis());
        DBUtils.saveOrUpdateMsgList(msgListEntity);
        this.wsManager.sendChatReceiptMessage(MessageHelper.convertToChatReceiptMsg(poll.userId, poll.messageId));
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$SQXMYTUJOl7BEwYGSB5BbMsuLHk
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealPrivateMsg$48$PrepareLiveActivity(msgListEntity, msgDetailListEntity);
            }
        });
    }

    public /* synthetic */ void lambda$dealPrivateMsg$48$PrepareLiveActivity(MsgListEntity msgListEntity, MsgDetailListEntity msgDetailListEntity) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog != null && privateMsgDialog.isAdded()) {
            this.privateMsgDialog.dealMsg(msgListEntity, msgDetailListEntity);
        } else {
            MsgStatusEntity msgStatusEntity = new MsgStatusEntity();
            msgStatusEntity.appId = UserInfoManager.getInstance().getAppId();
            msgStatusEntity.userId = UserInfoManager.getInstance().getUserId();
            msgStatusEntity.readStatus = "0";
            DBUtils.saveOrUpdateMsgStatus(msgStatusEntity);
            QBadgeView qBadgeView = this.qBadgeView;
            if (qBadgeView != null) {
                qBadgeView.setBadgeNumber(-1);
            }
        }
        this.canShowPrivateMsg.set(true);
        sendWorkHandlerEmptyMessage(10006);
    }

    private void dealSysNotice() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowSysNotice.get() || (poll = this.sysNoticeQueue.poll()) == null) {
            return;
        }
        this.canShowSysNotice.set(false);
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$yh6XBR_K0Ja_4DTDi34m3JNznX0
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealSysNotice$49$PrepareLiveActivity(poll);
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ void lambda$dealSysNotice$49$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
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
            this.liveChatMsgView.addChatMsg(chatEntity);
        } else if (c == 1) {
            this.mLivePusherInfoView.setSysNoticeAnim(resultData.content, this.nobilityPlayPeriod);
            ChatEntity chatEntity2 = new ChatEntity();
            chatEntity2.setMsgText(resultData.content);
            chatEntity2.setMsgType(21);
            this.liveChatMsgView.addChatMsg(chatEntity2);
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

    private void dealEnterMsg() {
        final SocketMessageEvent.ResultData poll;
        if (!this.canShowEnterMsg.get() || (poll = this.enterMsgQueue.poll()) == null) {
            return;
        }
        this.canShowEnterMsg.set(false);
        if (AppUtils.isNobilityUser(poll.nobilityType) && !poll.isPlayNobilityEnterAnim()) {
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
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$FxyrofSJhvT0aYZeuq07wwA2myc
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$dealEnterMsg$50$PrepareLiveActivity(poll, formatExpGrade);
            }
        });
    }

    public /* synthetic */ void lambda$dealEnterMsg$50$PrepareLiveActivity(SocketMessageEvent.ResultData resultData, String str) {
        if (!TextUtils.isEmpty(resultData.userId) || AppUtils.isChatEnterMsg(resultData.role, resultData.guardType, resultData.carId, resultData.nobilityType)) {
            updateUserList(resultData);
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsgType(0);
            chatEntity.setMsgSendName(resultData.userName);
            chatEntity.setExpGrade(str);
            chatEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
            chatEntity.setMsgText(getString(R$string.fq_live_join_notify_nobility));
            chatEntity.setUserAvatar(resultData.avatar);
            chatEntity.setUid(resultData.userId);
            chatEntity.setRole(resultData.role);
            chatEntity.setUserRole(resultData.userRole);
            chatEntity.setSex(resultData.sex);
            chatEntity.setCarIcon(resultData.carIcon);
            chatEntity.setNobilityType(resultData.nobilityType);
            chatEntity.setWeekStar(resultData.isWeekStar);
            chatEntity.setMarkUrls(resultData.markUrls);
            this.liveChatMsgView.addChatMsg(chatEntity);
        } else {
            this.liveChatMsgView.setUserGradeInfo(str, resultData.userName);
        }
        this.canShowEnterMsg.set(true);
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue = this.enterMsgQueue;
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
            return;
        }
        sendWorkHandlerEmptyMessageDelayed(10002, DURATION_GET_MESSAGE);
    }

    private void dealChatMsg() {
        ChatEntity poll;
        final LinkedList linkedList = new LinkedList();
        synchronized (PrepareLiveActivity.class) {
            if (this.receiveMsgQueue != null && !this.receiveMsgQueue.isEmpty()) {
                for (int i = 0; i < 5 && (poll = this.receiveMsgQueue.poll()) != null; i++) {
                    linkedList.add(poll);
                }
                handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$QQ08T7B5HPJOgDYRsxDHjHCMlro
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrepareLiveActivity.this.lambda$dealChatMsg$51$PrepareLiveActivity(linkedList);
                    }
                });
                return;
            }
            this.msgSleep = true;
        }
    }

    public /* synthetic */ void lambda$dealChatMsg$51$PrepareLiveActivity(List list) {
        this.liveChatMsgView.addChatMsgList(list);
        sendWorkHandlerEmptyMessageDelayed(10001, DURATION_GET_MESSAGE);
    }

    private void updateUserList(SocketMessageEvent.ResultData resultData) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(resultData.avatar);
        userEntity.setUserId(resultData.userId);
        userEntity.setName(resultData.userName);
        userEntity.setExpGrade(AppUtils.formatExpGrade(resultData.expGrade));
        userEntity.setGuardType(NumberUtils.string2int(resultData.guardType));
        userEntity.setNobilityType(resultData.nobilityType);
        userEntity.setRole(resultData.role);
        userEntity.setUserRole(resultData.userRole);
        this.mLivePusherInfoView.addUserItem(userEntity);
    }

    public /* synthetic */ void lambda$onNetChangeListener$52$PrepareLiveActivity(long j) {
        if (NetUtils.isNetworkAvailable()) {
            return;
        }
        showToast(R$string.fq_text_no_network);
        this.isNoNetEvent = true;
        if (!this.isLiving) {
            return;
        }
        startPausedTimer();
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.tomatolive.library.service.NetworkChangeReceiver.NetChangeListener
    public void onNetChangeListener(int i) {
        if (i == -1) {
            RxTimerUtils.getInstance().timerBindDestroy(getLifecycleProvider(), 2L, TimeUnit.SECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$eZecRgwSf6xvvTA-IZ9tmpiwRvs
                @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
                public final void action(long j) {
                    PrepareLiveActivity.this.lambda$onNetChangeListener$52$PrepareLiveActivity(j);
                }
            });
        } else if (i == 0) {
            if (this.isNoNetEvent && this.isLiving) {
                cancelPausedTimer();
            }
            if (this.isPushInBackground) {
                return;
            }
            showMobileNetDialog();
        } else if (i != 1 || !this.isNoNetEvent || !this.isLiving) {
        } else {
            cancelPausedTimer();
        }
    }

    private void showMobileNetDialog() {
        if (SPUtils.getInstance().getBoolean(ConstantUtils.SHOW_MOBIE_TIP, false)) {
            if (BaseActivity.hasRemindTraffic) {
                return;
            }
            BaseActivity.hasRemindTraffic = true;
            showToast(R$string.fq_mobile_tip);
            return;
        }
        NetworkPromptDialog.newInstance(getResources().getString(R$string.fq_text_mobile_net_live), getResources().getString(R$string.fq_text_go_on_live), getResources().getString(R$string.fq_text_stop_live), $$Lambda$PrepareLiveActivity$tUr31D3kocaLw5HzSkVG92HtAwk.INSTANCE, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$LxgGmAONEzGBj2CaItqY4-2iunA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$showMobileNetDialog$54$PrepareLiveActivity(view);
            }
        }).show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$showMobileNetDialog$54$PrepareLiveActivity(View view) {
        if (this.isLiving) {
            finishLive();
        } else {
            finish();
        }
        this.isLiving = false;
    }

    private void cancelPausedTimer() {
        Disposable disposable = this.pauseTimer;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.pauseTimer.dispose();
        this.pauseTimer = null;
    }

    private void startPausedTimer() {
        if (this.pauseTimer == null) {
            this.pauseTimer = Observable.timer(PUASE_TIME, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$6xT7TFdZLDLEOMQWp2FJZ7df20I
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PrepareLiveActivity.this.lambda$startPausedTimer$55$PrepareLiveActivity((Long) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$startPausedTimer$55$PrepareLiveActivity(Long l) throws Exception {
        this.isLiving = false;
        if (NetUtils.isNetworkAvailable()) {
            finishLive();
        } else {
            finishLiveOnError();
        }
    }

    private void finishLiveOnError() {
        prepareFinish();
        LiveEndEntity liveEndEntity = new LiveEndEntity();
        liveEndEntity.liveId = this.liveId;
        liveEndEntity.avatar = UserInfoManager.getInstance().getAvatar();
        liveEndEntity.userId = UserInfoManager.getInstance().getUserId();
        liveEndEntity.nickname = UserInfoManager.getInstance().getUserNickname();
        liveEndEntity.sex = UserInfoManager.getInstance().getUserSex();
        liveEndEntity.expGrade = this.myUserGrade;
        liveEndEntity.nobilityType = UserInfoManager.getInstance().getNobilityType();
        liveEndEntity.maxPopularity = String.valueOf(this.onLineCount.get());
        liveEndEntity.giftIncomePrice = String.valueOf(this.tempIncomePrice.get());
        AnchorLiveEndView anchorLiveEndView = this.anchorLiveEndView;
        if (anchorLiveEndView != null) {
            anchorLiveEndView.initData(3, this.startLiveTimeMillis, liveEndEntity);
        }
        WsManager wsManager = this.wsManager;
        if (wsManager != null) {
            wsManager.stopService();
            this.wsManager = null;
        }
        this.isLiving = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTokenDialogService() {
        AppUtils.startDialogService(this.mContext, TokenDialogService.class);
    }

    private void closePusher() {
        InputTextMsgDialog inputTextMsgDialog = this.mInputTextMsgDialog;
        if (inputTextMsgDialog != null && inputTextMsgDialog.isShowing()) {
            this.mInputTextMsgDialog.dismiss();
        }
        stopStream();
    }

    private void prepareFinish() {
        stopCountDownAnim();
        dismissDialogs();
        setAnchorCoverImg();
        this.anchorLiveEndView.setVisibility(0);
        PushManager pushManager = this.pushManager;
        if (pushManager != null) {
            pushManager.hidePreviewView();
        }
        this.rlLiveRoot.setVisibility(4);
        StickerAddView stickerAddView = this.mStickerAddView;
        if (stickerAddView != null) {
            stickerAddView.setVisibility(4);
        }
        this.mPreStartLiveView.setVisibility(4);
        closePusher();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLive() {
        finishLiveUploadData();
        if (NetUtils.isNetworkAvailable()) {
            prepareFinish();
            sendLeaveMessage();
            ((PrePareLivePresenter) this.mPresenter).setEnterOrLeaveLiveRoomMsg(this.liveId, "leave");
            ((PrePareLivePresenter) this.mPresenter).getLiveEndInfo(this.liveId, this.streamName, 1);
            return;
        }
        finishLiveOnError();
    }

    private void dismissDialogs() {
        dismissUserAvatarDialog();
        dismissDialogFragment(this.giftWallDialog, this.userAchieveDialog, this.guardOpenContentDialog, this.guardListDialog, this.privateMsgDialog, this.anchorAvatarDialog, this.endWarnDialog, this.startWarnDialog, this.popularCardDialog, this.qmInteractDialog, this.qmTaskListDialog);
        Dialog dialog = this.bottomMoreDialog;
        if (dialog != null && dialog.isShowing()) {
            this.bottomMoreDialog.dismiss();
        }
        BeautyDialog beautyDialog = this.beautyDialog;
        if (beautyDialog == null || !beautyDialog.isResumed()) {
            return;
        }
        this.beautyDialog.dismiss();
    }

    private void dismissUserAvatarDialog() {
        dismissDialogFragment(this.userAvatarDialog, this.userSuperAvatarDialog, this.userGuardAvatarDialog, this.userNobilityAvatarDialog, this.anchorNobilityAvatarDialog, this.anchorAvatarDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuardListDialog(final GuardItemEntity guardItemEntity) {
        this.guardItemEntity = guardItemEntity;
        this.guardListDialog = GuardListDialog.newInstance(1, guardItemEntity, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$d6dZ6ynfYO4CXxjp3JNagZ9iaJ0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrepareLiveActivity.this.lambda$showGuardListDialog$56$PrepareLiveActivity(guardItemEntity, view);
            }
        });
        this.guardListDialog.show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$showGuardListDialog$56$PrepareLiveActivity(GuardItemEntity guardItemEntity, View view) {
        if (!AppUtils.isEnableGuard()) {
            return;
        }
        this.guardOpenContentDialog = GuardOpenContentDialog.newInstance(guardItemEntity, null);
        this.guardOpenContentDialog.show(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGiftWallDialog(AnchorEntity anchorEntity) {
        if (this.giftWallDialog == null) {
            this.giftWallDialog = GiftWallDialog.newInstance(anchorEntity);
            this.giftWallDialog.show(getSupportFragmentManager());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        this.giftWallDialog.setArguments(bundle);
        showDialogFragment(this.giftWallDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserAchieveDialog(UserEntity userEntity, String str) {
        if (this.userAchieveDialog == null) {
            this.userAchieveDialog = UserAchieveDialog.newInstance(userEntity, str);
            this.userAchieveDialog.show(getSupportFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, userEntity);
        this.bundleArgs.putString(ConstantUtils.RESULT_COUNT, str);
        this.userAchieveDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.userAchieveDialog);
    }

    private void showPrivateMessageDialog() {
        MsgStatusEntity msgStatusEntity = new MsgStatusEntity();
        msgStatusEntity.appId = UserInfoManager.getInstance().getAppId();
        msgStatusEntity.userId = UserInfoManager.getInstance().getUserId();
        msgStatusEntity.readStatus = "1";
        DBUtils.saveOrUpdateMsgStatus(msgStatusEntity);
        QBadgeView qBadgeView = this.qBadgeView;
        boolean z = false;
        if (qBadgeView != null) {
            qBadgeView.setBadgeNumber(0);
        }
        if (this.isSocketError || this.isSocketClose) {
            z = true;
        }
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null) {
            this.privateMsgDialog = PrivateMsgDialog.newInstance(true, z);
            this.privateMsgDialog.setSendPrivateMsgListener(this);
            this.privateMsgDialog.show(getSupportFragmentManager());
        } else if (privateMsgDialog.isAdded()) {
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean(PrivateMsgDialog.TYPE_FORM_ANCHOR, true);
            bundle.putInt(PrivateMsgDialog.CONTENT_TYPE_KEY, 1);
            bundle.putBoolean(PrivateMsgDialog.TYPE_SOCKET_STATUS, z);
            this.privateMsgDialog.setArguments(bundle);
            this.privateMsgDialog.show(getSupportFragmentManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPayAudienceListDialog() {
        if (this.payAudienceListDialog == null) {
            this.payAudienceListDialog = PayAudienceListDialog.newInstance(this.liveId, String.valueOf(this.liveCount), this.startLiveTimeMillis, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.30
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserItemClickListener(UserEntity userEntity) {
                    super.onUserItemClickListener(userEntity);
                    PrepareLiveActivity.this.showUserCard(userEntity);
                }
            });
            this.payAudienceListDialog.show(getSupportFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putString(ConstantUtils.RESULT_ID, this.liveId);
        this.bundleArgs.putString(ConstantUtils.RESULT_COUNT, String.valueOf(this.liveCount));
        this.bundleArgs.putLong(ConstantUtils.RESULT_ITEM, this.startLiveTimeMillis);
        this.payAudienceListDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.payAudienceListDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQMInteractDialog() {
        if (this.qmInteractDialog == null) {
            this.qmInteractDialog = QMInteractDialog.newInstance();
            this.qmInteractDialog.show(getSupportFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.qmInteractDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.qmInteractDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQMTaskListDialog() {
        if (this.qmTaskListDialog == null) {
            this.qmTaskListDialog = QMTaskListDialog.newInstance(this.liveId, new SimpleQMInteractCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.31
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onBackQMInteractConfigListener() {
                    super.onBackQMInteractConfigListener();
                    PrepareLiveActivity.this.showQMInteractDialog();
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onTaskStatusUpdateListener(String str, String str2) {
                    super.onTaskStatusUpdateListener(str, str2);
                    if (TextUtils.equals(str2, ConstantUtils.QM_TASK_STATUS_205)) {
                        PrepareLiveActivity.this.canShowIntimateNotice.set(true);
                        if (!PrepareLiveActivity.this.intimateTaskQueue.isEmpty()) {
                            PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.INTIMATE_TASK_NOTICE);
                        }
                        if (PrepareLiveActivity.this.qmNoticeAnimView == null) {
                            return;
                        }
                        PrepareLiveActivity.this.qmNoticeAnimView.onCancelAnim();
                        PrepareLiveActivity.this.qmNoticeAnimView.setVisibility(8);
                    }
                }
            });
            this.qmTaskListDialog.show(getSupportFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putString(ConstantUtils.RESULT_ID, this.liveId);
        this.qmTaskListDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.qmTaskListDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePrivateMessageNetStatus(boolean z) {
        PrivateMsgDialog privateMsgDialog = this.privateMsgDialog;
        if (privateMsgDialog == null || !privateMsgDialog.isAdded()) {
            return;
        }
        this.privateMsgDialog.changeNetStatus(z);
    }

    private void showDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (baseRxDialogFragment == null || baseRxDialogFragment.isAdded()) {
            return;
        }
        baseRxDialogFragment.show(getSupportFragmentManager());
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
    public boolean isDialogFragmentAdded(BaseRxDialogFragment baseRxDialogFragment) {
        return baseRxDialogFragment != null && baseRxDialogFragment.isAdded();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.isLiving) {
            StickerAddView stickerAddView = this.mStickerAddView;
            if (stickerAddView != null && stickerAddView.getVisibility() == 0) {
                this.mStickerAddView.onBackListener();
                return;
            } else {
                AnchorEndLiveDialog.newInstance(isPayLive(), new BottomDialogUtils.BottomPromptMenuListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.32
                    @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.BottomPromptMenuListener
                    public void onCancel() {
                    }

                    @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.BottomPromptMenuListener
                    public void onSure() {
                        if (PrepareLiveActivity.this.mLiveAdBannerBottomView == null || !PrepareLiveActivity.this.mLiveAdBannerBottomView.isShowCloseLiveTips()) {
                            PrepareLiveActivity.this.finishLive();
                        } else {
                            SureCancelDialog.newInstance(null, ((BaseActivity) PrepareLiveActivity.this).mContext.getString(R$string.fq_qm_task_close_live_tips), ((BaseActivity) PrepareLiveActivity.this).mContext.getString(R$string.fq_qm_task_close_live_continue_tips), ((BaseActivity) PrepareLiveActivity.this).mContext.getString(R$string.fq_qm_task_close_live_colse_tips), R$color.fq_colorBlack, null, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.32.1
                                @Override // android.view.View.OnClickListener
                                public void onClick(View view) {
                                    PrepareLiveActivity.this.finishLive();
                                }
                            }).show(PrepareLiveActivity.this.getSupportFragmentManager());
                        }
                    }
                }).show(getSupportFragmentManager());
                return;
            }
        }
        finish();
    }

    private boolean isPayLive() {
        return TextUtils.equals(this.chargeType, "1");
    }

    private void stopCountDownAnim() {
        Disposable disposable = this.startPlayAnimDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.startPlayAnimDisposable.dispose();
        this.tvCountDown.setVisibility(8);
        AnimatorSet animatorSet = this.countDownAnimSet;
        if (animatorSet == null) {
            return;
        }
        animatorSet.cancel();
    }

    private void finishLiveUploadData() {
        LogEventUtils.shutDownTimerTask(LogConstants.END_LIVE_EVENT_NAME, true);
        long currentTimeMillis = ((System.currentTimeMillis() - this.startLiveTimeMillis) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
        if (currentTimeMillis < 1) {
            return;
        }
        LogEventUtils.uploadEndLive(this.liveLabel, this.startLiveInfoEntity.expGrade, String.valueOf(this.tempIncomePrice.get()), String.valueOf(this.chatCount.get()), String.valueOf(currentTimeMillis), this.liveId, String.valueOf(this.onLineCount.get()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLeaveMessage() {
        WsManager wsManager;
        if (!NetUtils.isNetworkAvailable() || (wsManager = this.wsManager) == null) {
            return;
        }
        wsManager.sendLeaveMessage(new SendMessageEntity());
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
            sb.append(getString(R$string.fq_live_room_num, new Object[]{this.liveId}));
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

    /* JADX INFO: Access modifiers changed from: private */
    public void saveLiveUploadData() {
        AnchorLiveDataEntity anchorLiveDataEntity = new AnchorLiveDataEntity();
        anchorLiveDataEntity.anchorId = UserInfoManager.getInstance().getAppOpenId();
        anchorLiveDataEntity.appId = UserInfoManager.getInstance().getAppId();
        AnchorStartLiveEntity anchorStartLiveEntity = this.startLiveInfoEntity;
        anchorLiveDataEntity.tag = anchorStartLiveEntity.topic;
        anchorLiveDataEntity.expGrade = anchorStartLiveEntity.expGrade;
        anchorLiveDataEntity.nickname = UserInfoManager.getInstance().getUserNickname();
        anchorLiveDataEntity.coinNum = String.valueOf(this.tempIncomePrice.get());
        anchorLiveDataEntity.barrageNum = String.valueOf(this.chatCount.get());
        anchorLiveDataEntity.liveId = this.liveId;
        anchorLiveDataEntity.startTime = this.anchorLiveEndView.getStartLiveTimeMillis();
        anchorLiveDataEntity.endTime = System.currentTimeMillis();
        anchorLiveDataEntity.viewerCount = String.valueOf(this.onLineCount.get());
        DBUtils.saveOrUpdateAnchorLiveData(anchorLiveDataEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: addQMNoticeAnimView */
    public void lambda$dealIntimateTask$42$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        if (this.qmNoticeAnimView == null) {
            this.qmNoticeAnimView = new QMNoticeAnimView(this.mContext);
            this.qmNoticeAnimView.setOnQMInteractCallback(new SimpleQMInteractCallback() { // from class: com.tomatolive.library.ui.activity.live.PrepareLiveActivity.33
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onTaskStatusUpdateListener(String str, String str2) {
                    super.onTaskStatusUpdateListener(str, str2);
                    onNoticeAnimViewDismissListener();
                    if (PrepareLiveActivity.this.qmTaskListDialog != null) {
                        PrepareLiveActivity.this.qmTaskListDialog.sendTaskStatusUpdateRequest(str, str2);
                    }
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onNoticeAnimViewDismissListener() {
                    super.onNoticeAnimViewDismissListener();
                    PrepareLiveActivity.this.canShowIntimateNotice.set(true);
                    if (!PrepareLiveActivity.this.intimateTaskQueue.isEmpty()) {
                        PrepareLiveActivity.this.sendWorkHandlerEmptyMessage(ConstantUtils.INTIMATE_TASK_NOTICE);
                    }
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleQMInteractCallback, com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback
                public void onUserCardListener(String str) {
                    super.onUserCardListener(str);
                    ((PrePareLivePresenter) ((BaseActivity) PrepareLiveActivity.this).mPresenter).getUserCardInfo(str);
                }
            });
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13);
            this.shootContainer.addView(this.qmNoticeAnimView, layoutParams);
        }
        this.qmNoticeAnimView.showNoticeAnimView(true, resultData.putAvatar, resultData.putName, resultData.giftName, resultData.giftNum, resultData.taskName, resultData.putUserId, resultData.taskId);
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SPUtils.getInstance().put(ConstantUtils.NOTICE_GAME_KEY, true);
        cancelPausedTimer();
        HandlerUtils.getInstance().stopIOThread();
        stopSocket();
        clearAllMapData();
        PushManager pushManager = this.pushManager;
        if (pushManager != null) {
            pushManager.release();
            this.pushManager = null;
        }
        LiveAnimationView liveAnimationView = this.liveAnimationView;
        if (liveAnimationView != null) {
            liveAnimationView.onDestroy();
        }
        LivePusherInfoView livePusherInfoView = this.mLivePusherInfoView;
        if (livePusherInfoView != null) {
            livePusherInfoView.onDestroy();
        }
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mainHandler = null;
        }
        Handler handler2 = this.workHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.workHandler = null;
        }
        GiftBoxView giftBoxView = this.mGiftBoxView;
        if (giftBoxView != null) {
            giftBoxView.release();
        }
        InputTextMsgDialog inputTextMsgDialog = this.mInputTextMsgDialog;
        if (inputTextMsgDialog != null) {
            inputTextMsgDialog.onDestroy();
            this.mInputTextMsgDialog = null;
        }
        SwipeAnimationController swipeAnimationController = this.swipeAnimationController;
        if (swipeAnimationController != null) {
            swipeAnimationController.onDestroy();
            this.swipeAnimationController = null;
        }
        Disposable disposable = this.mPopularDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mPopularDisposable.dispose();
        }
        QMNoticeAnimView qMNoticeAnimView = this.qmNoticeAnimView;
        if (qMNoticeAnimView != null) {
            qMNoticeAnimView.onRelease();
            this.shootContainer.removeView(this.qmNoticeAnimView);
            this.qmNoticeAnimView = null;
        }
        LiveAdBannerBottomView liveAdBannerBottomView = this.mLiveAdBannerBottomView;
        if (liveAdBannerBottomView != null) {
            liveAdBannerBottomView.releaseWebView();
            this.mLiveAdBannerBottomView.onReleaseTopBanner();
        }
        super.onDestroy();
    }

    private void clearAllMapData() {
        List<String> list = this.shieldedList;
        if (list != null) {
            list.clear();
        }
        Map<String, Map<String, GiftIndexEntity>> map = this.receiveGiftMap;
        if (map != null) {
            map.clear();
        }
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
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue6 = this.anchorInfoNoticeQueue;
        if (concurrentLinkedQueue6 != null) {
            concurrentLinkedQueue6.clear();
        }
        Map<String, Map<String, GiftIndexEntity>> map2 = this.receivePropMap;
        if (map2 != null) {
            map2.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue7 = this.privateMsgQueue;
        if (concurrentLinkedQueue7 != null) {
            concurrentLinkedQueue7.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue8 = this.gameNoticeQueue;
        if (concurrentLinkedQueue8 != null) {
            concurrentLinkedQueue8.clear();
        }
        ConcurrentLinkedQueue<SocketMessageEvent.ResultData> concurrentLinkedQueue9 = this.intimateTaskQueue;
        if (concurrentLinkedQueue9 != null) {
            concurrentLinkedQueue9.clear();
        }
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

    private boolean isShowDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        return baseRxDialogFragment != null && baseRxDialogFragment.isResumed();
    }

    private void updateAnchorContribution(final SocketMessageEvent.ResultData resultData) {
        if (resultData == null) {
            return;
        }
        this.tempIncomePrice.addAndGet(NumberUtils.string2long(resultData.price, 0L));
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$PrepareLiveActivity$UeHoVtnVafmn9H_vfGxO8aeFvgQ
            @Override // java.lang.Runnable
            public final void run() {
                PrepareLiveActivity.this.lambda$updateAnchorContribution$57$PrepareLiveActivity(resultData);
            }
        });
    }

    public /* synthetic */ void lambda$updateAnchorContribution$57$PrepareLiveActivity(SocketMessageEvent.ResultData resultData) {
        this.mLivePusherInfoView.setAnchorContribution(AppUtils.formatContributionValue(resultData));
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

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.activity.live.PrepareLiveActivity$UserCardCallback */
    /* loaded from: classes3.dex */
    public class UserCardCallback extends SimpleUserCardCallback {
        private boolean isAnchorUserCard;
        private boolean isCtrlTarget;
        private boolean isShieldTarget;
        private String targetId;
        private String targetName;
        private int userDialogType;

        public UserCardCallback(boolean z) {
            this.isAnchorUserCard = false;
            this.isAnchorUserCard = z;
        }

        public UserCardCallback(String str, String str2, int i, boolean z, boolean z2) {
            this.isAnchorUserCard = false;
            this.targetId = str;
            this.targetName = str2;
            this.userDialogType = i;
            this.isCtrlTarget = z;
            this.isShieldTarget = z2;
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickManageListener(View view) {
            super.onClickManageListener(view);
            if (this.isAnchorUserCard) {
                return;
            }
            PrepareLiveActivity.this.sendShowUserManageMenuRequest(this.userDialogType, this.isCtrlTarget, this.isShieldTarget, this.targetName, this.targetId);
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickNobilityListener(View view) {
            super.onClickNobilityListener(view);
            PrepareLiveActivity.this.showToast(R$string.fq_start_live_open_nobility_tips);
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onUserAchieveListener(UserEntity userEntity, String str) {
            super.onUserAchieveListener(userEntity, str);
            PrepareLiveActivity.this.showUserAchieveDialog(userEntity, str);
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onGiftWallClickListener(AnchorEntity anchorEntity) {
            super.onGiftWallClickListener(anchorEntity);
            PrepareLiveActivity.this.showGiftWallDialog(anchorEntity);
        }

        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
        public void onClickGuardListener() {
            super.onClickGuardListener();
            if (this.isAnchorUserCard) {
                PrepareLiveActivity prepareLiveActivity = PrepareLiveActivity.this;
                prepareLiveActivity.dismissDialogFragment(prepareLiveActivity.anchorNobilityAvatarDialog);
                PrepareLiveActivity prepareLiveActivity2 = PrepareLiveActivity.this;
                prepareLiveActivity2.dismissDialogFragment(prepareLiveActivity2.anchorAvatarDialog);
                PrepareLiveActivity prepareLiveActivity3 = PrepareLiveActivity.this;
                prepareLiveActivity3.showGuardListDialog(prepareLiveActivity3.guardItemEntity);
            }
        }
    }
}
