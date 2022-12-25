package com.tomatolive.library.p136ui.view.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.LiveItemEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.UserAvatarListAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener;
import com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback;
import com.tomatolive.library.p136ui.view.custom.LivePusherInfoView;
import com.tomatolive.library.p136ui.view.dialog.DedicateTopDialog;
import com.tomatolive.library.p136ui.view.dialog.VipDialog;
import com.tomatolive.library.p136ui.view.headview.OnlineUserHeadView;
import com.tomatolive.library.p136ui.view.widget.guideview.Component;
import com.tomatolive.library.p136ui.view.widget.guideview.Guide;
import com.tomatolive.library.p136ui.view.widget.guideview.GuideBuilder;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.SwipeAnimationController;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.ViewCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.p144ui.widget.DanmakuTouchHelper;
import master.flame.danmaku.p144ui.widget.DanmakuView;

/* renamed from: com.tomatolive.library.ui.view.custom.LivePusherInfoView */
/* loaded from: classes3.dex */
public class LivePusherInfoView extends LinearLayout {
    private AnchorEntity anchorInfoItem;
    private AnimatorSet charmAnimatorSet;
    private DanmakuContext danmakuContext;
    private DanmakuView danmakuView;
    private DedicateTopDialog dedicateTopDialog;
    private FragmentManager fragmentManager;
    private AnimatorSet gameAnimatorSet;
    private AnimatorSet giftAnimatorSet;
    private GuardItemEntity guardInfoItem;
    private ImageView ivAvatar;
    private View ivCloseGame;
    private ImageView ivFollow;
    private ImageView ivGiftImg;
    private ImageView ivLuckGiftIcon;
    private LiveAdBannerView liveAdBannerView;
    private LiveGamePrizeView liveGamePrizeView;
    private int liveType;
    private AnimatorSet luckAnimatorSet;
    private Context mContext;
    private RecyclerView mRvUserAvatar;
    private UserAvatarListAdapter mUserAvatarListAdapter;
    private NobilityOpenTopNoticeView nobilityOpenTopNoticeView;
    private OnLivePusherInfoCallback onLivePusherInfoCallback;
    private OnlineUserHeadView onlineUserHeadView;
    private Guide payLiveGuide;
    private RelativeLayout rlCharmNoticeRoot;
    private RelativeLayout rlGameNoticeRoot;
    private RelativeLayout rlGiftNoticeRoot;
    private RelativeLayout rlLuckNoticeRoot;
    private RelativeLayout rlNobilityOpenNoticeContent;
    private RelativeLayout rlPkAssistKingBg;
    private RelativeLayout rlRecommendHotNoticeContent;
    private RelativeLayout rlSysNoticeRoot;
    private RelativeLayout rlTrumpetNoticeContent;
    private SwipeAnimationController swipeAnimationController;
    private AnimatorSet sysAnimatorSet;
    private TextView tvAnchorOpenNoticeContent;
    private TextView tvChargeType;
    private TextView tvCharmNoticeContent;
    private TextView tvGold;
    private TextView tvLuckGiftTip;
    private TextView tvName;
    private TextView tvPersonalNum;
    private TextView tvPkAssistKing;
    private TextView tvRecommendHotTips;
    private TextView tvSysNoticeContent;
    private TextView tvTopNotice;
    private TextView tvTrumpetNotice;
    private TextView tvVipCount;
    private VipDialog vipDialog;
    private long anchorContribution = 0;
    private long vipCount = 0;
    private String animPropertyName = "translationX";

    private long formatAnimatorSetDuration(long j) {
        long j2 = j - 3000;
        if (j2 < 0) {
            return 6000L;
        }
        return j2;
    }

    private long formatAnimatorSetDuration(long j, long j2) {
        long j3 = j - j2;
        if (j3 < 0) {
            return 6000L;
        }
        return j3;
    }

    public LivePusherInfoView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(1);
        LinearLayout.inflate(this.mContext, R$layout.fq_include_live_pusher_info, this);
        this.tvName = (TextView) findViewById(R$id.tv_name);
        this.tvPersonalNum = (TextView) findViewById(R$id.tv_member);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivFollow = (ImageView) findViewById(R$id.iv_follow);
        this.tvGold = (TextView) findViewById(R$id.tv_money);
        this.tvTopNotice = (TextView) findViewById(R$id.tv_top_notice);
        this.tvChargeType = (TextView) findViewById(R$id.tv_charge_type);
        this.tvCharmNoticeContent = (TextView) findViewById(R$id.tv_top_charm_notice);
        this.tvAnchorOpenNoticeContent = (TextView) findViewById(R$id.tv_anchor_open_notice);
        this.mRvUserAvatar = (RecyclerView) findViewById(R$id.recycle_user_avatar);
        this.ivGiftImg = (ImageView) findViewById(R$id.iv_gift_img);
        this.rlGiftNoticeRoot = (RelativeLayout) findViewById(R$id.rl_top_notice_bg);
        this.rlCharmNoticeRoot = (RelativeLayout) findViewById(R$id.rl_top_charm_bg);
        this.rlTrumpetNoticeContent = (RelativeLayout) findViewById(R$id.rl_trumpet_notice_bg);
        this.rlNobilityOpenNoticeContent = (RelativeLayout) findViewById(R$id.rl_nobility_open_notice_bg);
        this.rlRecommendHotNoticeContent = (RelativeLayout) findViewById(R$id.rl_recommend_hot_notice_bg);
        this.liveAdBannerView = (LiveAdBannerView) findViewById(R$id.live_banner_view);
        this.tvSysNoticeContent = (TextView) findViewById(R$id.tv_sys_notice);
        this.tvTrumpetNotice = (TextView) findViewById(R$id.tv_nobility_trumpet);
        this.rlSysNoticeRoot = (RelativeLayout) findViewById(R$id.rl_sys_notice_bg);
        this.tvVipCount = (TextView) findViewById(R$id.tv_vip_count);
        this.tvRecommendHotTips = (TextView) findViewById(R$id.tv_recommend_hot_tips);
        this.nobilityOpenTopNoticeView = (NobilityOpenTopNoticeView) findViewById(R$id.nobility_open_notice_view);
        this.ivLuckGiftIcon = (ImageView) findViewById(R$id.iv_luck_gift_icon);
        this.tvLuckGiftTip = (TextView) findViewById(R$id.tv_luck_tips);
        this.rlLuckNoticeRoot = (RelativeLayout) findViewById(R$id.rl_luck_notice_bg);
        this.rlGameNoticeRoot = (RelativeLayout) findViewById(R$id.rl_game_notice_bg);
        this.liveGamePrizeView = (LiveGamePrizeView) findViewById(R$id.game_prize_view);
        this.ivCloseGame = this.liveGamePrizeView.findViewById(R$id.iv_game_close);
        this.rlPkAssistKingBg = (RelativeLayout) findViewById(R$id.rl_pk_assist_king_bg);
        this.tvPkAssistKing = (TextView) findViewById(R$id.tv_pk_assist_king);
        this.onlineUserHeadView = new OnlineUserHeadView(this.mContext);
        initDanmakuContext();
        initUserAvatarListAdapter();
        initListener();
    }

    private Guide generateGuideView(Component component, GuideBuilder.OnVisibilityChangedListener onVisibilityChangedListener) {
        return new GuideBuilder().setTargetView(this.tvChargeType).addComponent(component).setHighlightBitmap(BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_guide_frame)).setHighTargetGraphStyle(2).setAlpha(150).setOnVisibilityChangedListener(onVisibilityChangedListener).createGuide();
    }

    public void showGuidePaidLive(Activity activity) {
        generateGuideView(new GuidePaidLiveComponent(), new GuideBuilder.OnVisibilityChangedListener() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.1
            @Override // com.tomatolive.library.p136ui.view.widget.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                SysConfigInfoManager.getInstance().setEnablePaidLiveGuide(false);
            }
        }).show(activity);
    }

    public void showGuideRating(Activity activity) {
        this.payLiveGuide = generateGuideView(new GuideRatingComponent(), new GuideBuilder.OnVisibilityChangedListener() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.2
            @Override // com.tomatolive.library.p136ui.view.widget.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                SysConfigInfoManager.getInstance().setEnableRatingGuide(false);
            }
        });
        this.payLiveGuide.show(activity);
    }

    public void clearLiveGuide() {
        Guide guide = this.payLiveGuide;
        if (guide != null) {
            guide.clear();
        }
    }

    private void initListener() {
        RxViewUtils.getInstance().throttleFirst(this.tvGold, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.3
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (LivePusherInfoView.this.fragmentManager != null) {
                    LivePusherInfoView livePusherInfoView = LivePusherInfoView.this;
                    livePusherInfoView.dedicateTopDialog = DedicateTopDialog.newInstance(livePusherInfoView.liveType, LivePusherInfoView.this.anchorInfoItem, LivePusherInfoView.this.onLivePusherInfoCallback);
                    LivePusherInfoView.this.dedicateTopDialog.show(LivePusherInfoView.this.fragmentManager);
                }
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.onlineUserHeadView, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$UQm2ZhkON98h1mp9p4aG5IqPd2c
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$0$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.rl_vip_number), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$HV7m7JdpuGmUmQVpE8xle4PPaPc
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$1$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivAvatar, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$kx_tpBuxz8paQa64OvV3HFcUgJs
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$2$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.rlGiftNoticeRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$9sL_GdTRtfGmXIraWjzEXC4UT6Y
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$3$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.rlCharmNoticeRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$wqblwquAKHFo_cjbT1ZLVWXY_90
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$4$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.rlSysNoticeRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$-QNDOvYWkiwAWSj-hg5JitvsJW8
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$5$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.rlLuckNoticeRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$6RSIDC6egCikp9_phwG50Khce8g
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$6$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.rlGameNoticeRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$huLw4JSrHKn2cYbvX8mbhgBBGc0
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$7$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivCloseGame, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$8Ow-c6OoSm55SpDQUQgVH4f-5Ew
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$8$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivFollow, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$FBOmQUNQyumXt_VCNHWzU2uLkcE
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$9$LivePusherInfoView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvChargeType, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$4SljC1_T9tfJ6x18eef6WrX31BQ
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LivePusherInfoView.this.lambda$initListener$10$LivePusherInfoView(obj);
            }
        });
        this.liveAdBannerView.setOnAdBannerClickListener(new OnLiveAdBannerClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$csokPhzkkvDpXwulGXeuzK6iN2k
            @Override // com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener
            public final void onAdBannerClick(BannerEntity bannerEntity) {
                LivePusherInfoView.this.lambda$initListener$11$LivePusherInfoView(bannerEntity);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickGuardListener(this.guardInfoItem);
        }
    }

    public /* synthetic */ void lambda$initListener$1$LivePusherInfoView(Object obj) {
        this.vipDialog = VipDialog.newInstance(this.anchorInfoItem, this.vipCount, this.liveType, this.onLivePusherInfoCallback);
        this.vipDialog.show(this.fragmentManager);
    }

    public /* synthetic */ void lambda$initListener$2$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickAnchorAvatarListener(this.ivAvatar);
        }
    }

    public /* synthetic */ void lambda$initListener$3$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickGiftNoticeListener(this.rlGiftNoticeRoot);
        }
    }

    public /* synthetic */ void lambda$initListener$4$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickAnchorInfoNoticeListener(this.rlCharmNoticeRoot);
        }
    }

    public /* synthetic */ void lambda$initListener$5$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickSysNoticeListener(this.rlSysNoticeRoot);
        }
    }

    public /* synthetic */ void lambda$initListener$6$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickLuckNoticeListener(this.rlLuckNoticeRoot);
        }
    }

    public /* synthetic */ void lambda$initListener$7$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickGameNoticeListener(this.rlGameNoticeRoot);
        }
    }

    public /* synthetic */ void lambda$initListener$8$LivePusherInfoView(Object obj) {
        this.liveGamePrizeView.setVisibility(4);
        SPUtils.getInstance().put(ConstantUtils.NOTICE_GAME_KEY, false);
    }

    public /* synthetic */ void lambda$initListener$9$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onFollowAnchorListener(this.ivFollow);
        }
    }

    public /* synthetic */ void lambda$initListener$10$LivePusherInfoView(Object obj) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickAudienceListener(this.tvChargeType);
        }
    }

    public /* synthetic */ void lambda$initListener$11$LivePusherInfoView(BannerEntity bannerEntity) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickAdBannerListener(bannerEntity);
        }
    }

    private void initUserAvatarListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        this.mUserAvatarListAdapter = new UserAvatarListAdapter(R$layout.fq_recycle_item_user_avatar);
        this.mRvUserAvatar.setAdapter(this.mUserAvatarListAdapter);
        this.mRvUserAvatar.setLayoutManager(linearLayoutManager);
        this.mUserAvatarListAdapter.bindToRecyclerView(this.mRvUserAvatar);
        if (AppUtils.isEnableGuard()) {
            this.mUserAvatarListAdapter.addHeaderView(this.onlineUserHeadView, -1, 0);
        }
        this.mUserAvatarListAdapter.setHeaderAndEmpty(true);
        this.mUserAvatarListAdapter.setOnItemClickListener(new UserAvatarListAdapter.UserListClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$oVhab4Eysn7uuMTrMpbRg7d2GLU
            @Override // com.tomatolive.library.p136ui.adapter.UserAvatarListAdapter.UserListClickListener
            public final void onUserClick(UserEntity userEntity) {
                LivePusherInfoView.this.lambda$initUserAvatarListAdapter$12$LivePusherInfoView(userEntity);
            }
        });
    }

    public /* synthetic */ void lambda$initUserAvatarListAdapter$12$LivePusherInfoView(UserEntity userEntity) {
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback != null) {
            onLivePusherInfoCallback.onClickUserAvatarListener(userEntity);
        }
    }

    public void dismissDedicateTopDialog() {
        DedicateTopDialog dedicateTopDialog = this.dedicateTopDialog;
        if (dedicateTopDialog != null && dedicateTopDialog.isAdded()) {
            this.dedicateTopDialog.dismiss();
        }
        VipDialog vipDialog = this.vipDialog;
        if (vipDialog == null || !vipDialog.isAdded()) {
            return;
        }
        this.vipDialog.dismiss();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void initLivePusherInfoCallback(int i, FragmentManager fragmentManager, OnLivePusherInfoCallback onLivePusherInfoCallback) {
        this.liveType = i;
        this.fragmentManager = fragmentManager;
        this.ivFollow.setVisibility(AppUtils.isAudienceLiveType(this.liveType) ? 0 : 8);
        this.onLivePusherInfoCallback = onLivePusherInfoCallback;
    }

    public void initData(LiveItemEntity liveItemEntity, AnchorEntity anchorEntity, GuardItemEntity guardItemEntity) {
        if (liveItemEntity == null) {
            this.tvGold.setText(this.mContext.getString(R$string.fq_top_contribution));
            return;
        }
        this.guardInfoItem = guardItemEntity;
        this.anchorInfoItem = anchorEntity;
        this.anchorContribution = NumberUtils.string2long(liveItemEntity.anchorContribution);
        GuardItemEntity guardItemEntity2 = this.guardInfoItem;
        guardItemEntity2.anchorId = anchorEntity.userId;
        String str = liveItemEntity.anchorGuardCount;
        guardItemEntity2.anchorGuardCount = str;
        this.onlineUserHeadView.updateGuardCount(str);
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, anchorEntity.avatar);
        this.tvName.setText(StringUtils.formatStrLen(anchorEntity.nickname, 6));
        updateVipCount(NumberUtils.string2long(liveItemEntity.vipCount));
        long j = this.anchorContribution;
        if (j == -1) {
            this.tvGold.setText(this.mContext.getString(R$string.fq_top_contribution));
            return;
        }
        if (j < 0) {
            this.anchorContribution = 0L;
        }
        this.tvGold.setText(NumberUtils.formatThreeNumStr(this.anchorContribution));
    }

    public void setLivePopularityCount(long j) {
        this.tvPersonalNum.setText(AppUtils.formatLivePopularityCount(j));
    }

    public void updateVipCount(long j) {
        this.vipCount = j;
        this.tvVipCount.setText(AppUtils.formatLiveVipCount(this.vipCount));
    }

    public void setAnchorContribution(String str) {
        long j = this.anchorContribution;
        if (j == -1) {
            this.tvGold.setText(this.mContext.getString(R$string.fq_top_contribution));
            return;
        }
        this.anchorContribution = j + NumberUtils.string2long(str);
        if (this.anchorContribution < 0) {
            this.anchorContribution = 0L;
        }
        this.tvGold.setText(NumberUtils.formatThreeNumStr(this.anchorContribution));
    }

    public void setGiftAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.giftAnimatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rlGiftNoticeRoot, this.animPropertyName, screenWidth, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rlGiftNoticeRoot, this.animPropertyName, 0.0f, -screenWidth);
            ofFloat2.setStartDelay(3000L);
            this.giftAnimatorSet.play(ofFloat).before(ofFloat2);
            this.giftAnimatorSet.addListener(animatorListener);
        }
    }

    public void setCharmAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.charmAnimatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rlCharmNoticeRoot, this.animPropertyName, screenWidth, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rlCharmNoticeRoot, this.animPropertyName, 0.0f, -screenWidth);
            ofFloat2.setStartDelay(3000L);
            this.charmAnimatorSet.play(ofFloat).before(ofFloat2);
            this.charmAnimatorSet.addListener(animatorListener);
        }
    }

    public void setSysNoticeAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.sysAnimatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rlSysNoticeRoot, this.animPropertyName, screenWidth, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rlSysNoticeRoot, this.animPropertyName, 0.0f, -screenWidth);
            ofFloat2.setStartDelay(3000L);
            this.sysAnimatorSet.play(ofFloat).before(ofFloat2);
            this.sysAnimatorSet.addListener(animatorListener);
        }
    }

    public void setLuckNoticeAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.luckAnimatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rlLuckNoticeRoot, this.animPropertyName, screenWidth, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rlLuckNoticeRoot, this.animPropertyName, 0.0f, -screenWidth);
            ofFloat2.setStartDelay(3000L);
            this.luckAnimatorSet.play(ofFloat).before(ofFloat2);
            this.luckAnimatorSet.addListener(animatorListener);
        }
    }

    public void setGameNoticeAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.gameAnimatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rlGameNoticeRoot, this.animPropertyName, screenWidth, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rlGameNoticeRoot, this.animPropertyName, 0.0f, -screenWidth);
            ofFloat2.setStartDelay(5000L);
            this.gameAnimatorSet.play(ofFloat).before(ofFloat2);
            this.gameAnimatorSet.addListener(animatorListener);
        }
    }

    public void setGiftNoticeAnim(String str, String str2, String str3, String str4, String str5, long j) {
        this.rlGiftNoticeRoot.setVisibility(0);
        this.tvTopNotice.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_live_gift_notice_tips, AppUtils.formatUserNickNameByNotice(str), AppUtils.formatUserNickNameByNotice(str2), str3, str4)));
        String giftItemImgUrl = GiftDownLoadManager.getInstance().getGiftItemImgUrl(str5);
        if (!TextUtils.isEmpty(giftItemImgUrl)) {
            GlideUtils.loadImage(this.mContext, this.ivGiftImg, giftItemImgUrl);
        }
        AnimatorSet animatorSet = this.giftAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.giftAnimatorSet.start();
        }
    }

    public void setCharmNoticeAnim(String str, String str2, long j) {
        this.rlCharmNoticeRoot.setVisibility(0);
        this.tvAnchorOpenNoticeContent.setVisibility(8);
        this.tvCharmNoticeContent.setVisibility(0);
        this.tvCharmNoticeContent.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_top_charm_up, AppUtils.formatUserNickNameByNotice(str), str2)));
        AnimatorSet animatorSet = this.charmAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.charmAnimatorSet.start();
        }
    }

    public void setAnchorOpenNoticeAnim(SocketMessageEvent.ResultData resultData, long j) {
        this.rlCharmNoticeRoot.setVisibility(0);
        this.tvCharmNoticeContent.setVisibility(8);
        this.tvAnchorOpenNoticeContent.setVisibility(0);
        String formatUserNickNameByNotice = AppUtils.formatUserNickNameByNotice(resultData.anchorName);
        String string = this.mContext.getString(R$string.fq_achieve_anchor_open_notice, formatUserNickNameByNotice);
        if (!TextUtils.isEmpty(resultData.content) && resultData.content.contains("${}$")) {
            string = resultData.content.replace("${}$", formatUserNickNameByNotice);
        }
        this.tvAnchorOpenNoticeContent.setText(Html.fromHtml(string));
        AnimatorSet animatorSet = this.charmAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.charmAnimatorSet.start();
        }
    }

    public void setSysNoticeAnim(String str, long j) {
        this.rlSysNoticeRoot.setVisibility(0);
        this.tvSysNoticeContent.setVisibility(0);
        this.rlTrumpetNoticeContent.setVisibility(8);
        this.rlNobilityOpenNoticeContent.setVisibility(8);
        this.rlRecommendHotNoticeContent.setVisibility(8);
        this.tvSysNoticeContent.setText(str);
        AnimatorSet animatorSet = this.sysAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.sysAnimatorSet.start();
        }
    }

    public void setSysNobilityTrumpetAnim(SocketMessageEvent.ResultData resultData, long j) {
        this.rlSysNoticeRoot.setVisibility(0);
        this.rlTrumpetNoticeContent.setVisibility(0);
        this.tvSysNoticeContent.setVisibility(8);
        this.rlNobilityOpenNoticeContent.setVisibility(8);
        this.rlRecommendHotNoticeContent.setVisibility(8);
        this.tvTrumpetNotice.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_live_trumpet_notice_tips, AppUtils.formatUserNickNameByNotice(resultData.userName), resultData.content, AppUtils.formatUserNickNameByNotice(resultData.anchorName))));
        AnimatorSet animatorSet = this.sysAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.sysAnimatorSet.start();
        }
    }

    public void setSysNobilityOpenAnim(SocketMessageEvent.ResultData resultData, String str, long j) {
        this.rlSysNoticeRoot.setVisibility(0);
        this.rlNobilityOpenNoticeContent.setVisibility(0);
        this.tvSysNoticeContent.setVisibility(8);
        this.rlTrumpetNoticeContent.setVisibility(8);
        this.rlRecommendHotNoticeContent.setVisibility(8);
        this.nobilityOpenTopNoticeView.initData(resultData, str);
        AnimatorSet animatorSet = this.sysAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.sysAnimatorSet.start();
        }
    }

    public void setSysNobilityRecommendHotAnim(SocketMessageEvent.ResultData resultData, long j) {
        this.rlSysNoticeRoot.setVisibility(0);
        this.rlRecommendHotNoticeContent.setVisibility(0);
        this.rlTrumpetNoticeContent.setVisibility(8);
        this.tvSysNoticeContent.setVisibility(8);
        this.rlNobilityOpenNoticeContent.setVisibility(8);
        String formatUserNickName = AppUtils.formatUserNickName(resultData.fromUserName);
        String formatUserNickName2 = AppUtils.formatUserNickName(resultData.targetUserName);
        if (resultData.isAnonymousRecommendBoolean()) {
            this.tvRecommendHotTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_nobility_recommend_hot_tips, formatUserNickName2)));
        } else {
            this.tvRecommendHotTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_nobility_recommend_hot_tips_2, formatUserNickName, formatUserNickName2)));
        }
        AnimatorSet animatorSet = this.sysAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.sysAnimatorSet.start();
        }
    }

    public void setLuckNoticeAnim(SocketMessageEvent.ResultData resultData, String str, long j) {
        GlideUtils.loadImage(this.mContext, this.ivLuckGiftIcon, resultData.propUrl);
        this.rlLuckNoticeRoot.setVisibility(0);
        this.tvLuckGiftTip.setText(Html.fromHtml(this.mContext.getString(R$string.fq_msg_luck_notice_tips_1, AppUtils.formatUserNickNameByNotice(resultData.userName), resultData.drawWay, resultData.propName, resultData.propCount)));
        AnimatorSet animatorSet = this.luckAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.luckAnimatorSet.start();
        }
    }

    public void setGameNoticeAnim(SocketMessageEvent.ResultData resultData, long j) {
        this.rlGameNoticeRoot.setVisibility(0);
        this.rlPkAssistKingBg.setVisibility(4);
        this.liveGamePrizeView.setVisibility(0);
        this.liveGamePrizeView.initData(resultData);
        AnimatorSet animatorSet = this.gameAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(10000L, 5000L));
            this.gameAnimatorSet.start();
        }
    }

    public void setPkAssistNoticeAnim(SocketMessageEvent.ResultData resultData, long j) {
        this.rlGameNoticeRoot.setVisibility(0);
        this.rlPkAssistKingBg.setVisibility(0);
        this.liveGamePrizeView.setVisibility(4);
        this.tvPkAssistKing.setText(Html.fromHtml(this.mContext.getString(R$string.fq_pk_assist_king_tips, AppUtils.formatUserNickNameByNotice(resultData.userName), resultData.f5846fp)));
        AnimatorSet animatorSet = this.gameAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(10000L, 5000L));
            this.gameAnimatorSet.start();
        }
    }

    public void setAdBannerViewVisibility(int i) {
        this.liveAdBannerView.setVisibility(i);
    }

    public void setChargeTypeTips(boolean z, String str) {
        long string2long = NumberUtils.string2long(str);
        if (z && !TextUtils.isEmpty(str) && string2long > 0) {
            Context context = this.mContext;
            String string = context.getString(R$string.fq_pay_ticket_price, AppUtils.formatMoneyUnitStr(context, str, false));
            this.tvChargeType.setVisibility(0);
            this.tvChargeType.setText(this.mContext.getString(R$string.fq_pay_charge_type_top_tips, string));
            this.tvChargeType.setSelected(true);
            return;
        }
        this.tvChargeType.setVisibility(4);
    }

    public void addUserItem(final UserEntity userEntity) {
        if (NumberUtils.string2int(userEntity.getExpGrade()) < NumberUtils.string2int(SysConfigInfoManager.getInstance().getOnlineUserLevelFilter())) {
            return;
        }
        Observable.just(this.mUserAvatarListAdapter.getData()).map(new Function<List<UserEntity>, List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.5
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public List<UserEntity> mo6755apply(List<UserEntity> list) throws Exception {
                boolean z = true;
                for (UserEntity userEntity2 : list) {
                    if (TextUtils.equals(userEntity2.getUserId(), userEntity.getUserId())) {
                        z = false;
                    }
                }
                if (z) {
                    list.add(userEntity);
                }
                Collections.sort(list);
                return null;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<UserEntity> list) {
                LivePusherInfoView.this.mUserAvatarListAdapter.replaceData(list);
            }
        });
    }

    public void sortUserList(final String str, final String str2, final String str3, final int i, final String str4) {
        Observable.just(this.mUserAvatarListAdapter.getData()).map(new Function<List<UserEntity>, List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.7
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public List<UserEntity> mo6755apply(List<UserEntity> list) throws Exception {
                Iterator<UserEntity> it2 = list.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    UserEntity next = it2.next();
                    if (TextUtils.equals(next.getUserId(), str)) {
                        if (!TextUtils.isEmpty(str2)) {
                            next.setGuardType(NumberUtils.string2int(str2));
                        }
                        if (!TextUtils.isEmpty(str3)) {
                            next.setExpGrade(str3);
                        }
                        if (!TextUtils.isEmpty(str4)) {
                            next.setRole(str4);
                        }
                        int i2 = i;
                        if (i2 > -1) {
                            next.setNobilityType(i2);
                        }
                    }
                }
                Collections.sort(list);
                return list;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.6
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<UserEntity> list) {
                LivePusherInfoView.this.mUserAvatarListAdapter.replaceData(list);
            }
        });
    }

    public void replaceData(List<UserEntity> list) {
        Observable.just(list).map(new Function<List<UserEntity>, List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.9
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public List<UserEntity> mo6755apply(List<UserEntity> list2) throws Exception {
                for (UserEntity userEntity : list2) {
                    if (userEntity != null) {
                        String str = userEntity.openId;
                        String str2 = userEntity.avatar;
                        String str3 = userEntity.name;
                        userEntity.openId = AppUtils.getCurrentOnlineUserXORField(str);
                        userEntity.avatar = AppUtils.getCurrentOnlineUserXORField(str2);
                        userEntity.name = AppUtils.getCurrentOnlineUserXORField(str3);
                    }
                }
                return list2;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.8
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<UserEntity> list2) {
                LivePusherInfoView.this.mUserAvatarListAdapter.replaceData(list2);
            }
        });
    }

    public int getContributionViewTopMarginHeight() {
        int[] iArr = new int[2];
        this.tvGold.getLocationInWindow(iArr);
        return iArr[1] + this.tvGold.getHeight() + ConvertUtils.dp2px(12.0f);
    }

    public void removeUserItemById(String str) {
        this.mUserAvatarListAdapter.removeItemById(str);
    }

    public void initAdBannerImages(String str, String str2, List<BannerEntity> list) {
        this.liveAdBannerView.initAdBannerImages(str, str2, list);
    }

    public void initVerticalAdImage(String str, String str2, List<BannerEntity> list) {
        this.liveAdBannerView.initVerticalAdImage(str, str2, list);
    }

    public void hideGiftNoticeView() {
        this.rlGiftNoticeRoot.setVisibility(8);
    }

    public void hideCharmNoticeView() {
        this.rlCharmNoticeRoot.setVisibility(8);
    }

    public void hideSysNoticeView() {
        this.rlSysNoticeRoot.setVisibility(8);
    }

    public void hideLuckNoticeView() {
        this.rlLuckNoticeRoot.setVisibility(8);
    }

    public void hideGameNoticeView() {
        this.rlGameNoticeRoot.setVisibility(8);
        this.liveGamePrizeView.setVisibility(4);
        this.rlPkAssistKingBg.setVisibility(4);
    }

    public void addDanmuMsg(ChatEntity chatEntity) {
        BaseDanmaku createDanmaku;
        DanmakuContext danmakuContext = this.danmakuContext;
        if (danmakuContext == null || (createDanmaku = danmakuContext.mDanmakuFactory.createDanmaku(1)) == null || this.danmakuView == null) {
            return;
        }
        createDanmaku.setTag(chatEntity);
        createDanmaku.priority = (byte) 1;
        createDanmaku.setTime(this.danmakuView.getCurrentTime() + 1200);
        createDanmaku.textShadowColor = 0;
        this.danmakuView.addDanmaku(createDanmaku);
    }

    public void onPause() {
        DanmakuView danmakuView = this.danmakuView;
        if (danmakuView == null || !danmakuView.isPrepared()) {
            return;
        }
        this.danmakuView.pause();
    }

    public void onResume() {
        DanmakuView danmakuView = this.danmakuView;
        if (danmakuView == null || !danmakuView.isPrepared() || !this.danmakuView.isPaused()) {
            return;
        }
        this.danmakuView.resume();
    }

    public void onRelease() {
        releaseAnim(this.giftAnimatorSet, this.rlGiftNoticeRoot);
        this.giftAnimatorSet = null;
        releaseAnim(this.charmAnimatorSet, this.rlCharmNoticeRoot);
        this.charmAnimatorSet = null;
        releaseAnim(this.sysAnimatorSet, this.rlSysNoticeRoot);
        this.sysAnimatorSet = null;
        releaseAnim(this.luckAnimatorSet, this.rlLuckNoticeRoot);
        this.luckAnimatorSet = null;
        releaseAnim(this.gameAnimatorSet, this.rlGameNoticeRoot);
        this.gameAnimatorSet = null;
        DanmakuView danmakuView = this.danmakuView;
        if (danmakuView != null) {
            danmakuView.clearDanmakusOnScreen();
        }
        UserAvatarListAdapter userAvatarListAdapter = this.mUserAvatarListAdapter;
        if (userAvatarListAdapter == null || userAvatarListAdapter.getData() == null) {
            return;
        }
        this.mUserAvatarListAdapter.getData().clear();
        this.mUserAvatarListAdapter.notifyDataSetChanged();
    }

    private void releaseAnim(AnimatorSet animatorSet, RelativeLayout relativeLayout) {
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet.removeAllListeners();
            relativeLayout.setVisibility(8);
        }
    }

    public void onDestroy() {
        onRelease();
        DanmakuView danmakuView = this.danmakuView;
        if (danmakuView != null) {
            danmakuView.setCallback(null);
            this.danmakuView.release();
            this.danmakuView = null;
        }
        if (this.onLivePusherInfoCallback != null) {
            this.onLivePusherInfoCallback = null;
        }
        SwipeAnimationController swipeAnimationController = this.swipeAnimationController;
        if (swipeAnimationController != null) {
            swipeAnimationController.onDestroy();
        }
    }

    public void setFollowed(boolean z) {
        this.ivFollow.setSelected(z);
    }

    public boolean isFollowed() {
        return this.ivFollow.isSelected();
    }

    public void updateOpenGuardInfo(GuardItemEntity guardItemEntity) {
        GuardItemEntity guardItemEntity2;
        if (guardItemEntity == null || (guardItemEntity2 = this.guardInfoItem) == null) {
            return;
        }
        String str = guardItemEntity.guardType;
        guardItemEntity2.userGuardType = str;
        guardItemEntity2.userGuardExpireTime = guardItemEntity.endTime;
        if (NumberUtils.string2int(str) != NumberUtils.string2int("1")) {
            return;
        }
        this.guardInfoItem.isOpenWeekGuard = "1";
    }

    public void updateOpenGuardCount(String str) {
        if (this.guardInfoItem != null && !TextUtils.isEmpty(str)) {
            GuardItemEntity guardItemEntity = this.guardInfoItem;
            guardItemEntity.anchorGuardCount = str;
            this.onlineUserHeadView.updateGuardCount(guardItemEntity.anchorGuardCount);
        }
    }

    public void updateUserGradeInfo(String str) {
        GuardItemEntity guardItemEntity = this.guardInfoItem;
        if (guardItemEntity == null) {
            return;
        }
        guardItemEntity.expGrade = str;
    }

    private void initDanmakuContext() {
        HashMap hashMap = new HashMap();
        hashMap.put(1, 3);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(1, true);
        hashMap2.put(5, true);
        this.danmakuView = (DanmakuView) findViewById(R$id.sv_danmaku);
        this.danmakuContext = DanmakuContext.create();
        this.danmakuContext.setDanmakuBold(true);
        DanmakuContext danmakuContext = this.danmakuContext;
        danmakuContext.setDuplicateMergingEnabled(false);
        danmakuContext.setScrollSpeedFactor(2.2f);
        danmakuContext.setScaleTextSize(1.2f);
        danmakuContext.setCacheStuffer(new C444410(), null);
        danmakuContext.setMaximumLines(hashMap);
        danmakuContext.preventOverlapping(hashMap2);
        this.danmakuView.setCallback(new DrawHandler.Callback() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.11
            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void danmakuShown(BaseDanmaku baseDanmaku) {
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void drawingFinished() {
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void updateTimer(DanmakuTimer danmakuTimer) {
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void prepared() {
                LivePusherInfoView.this.danmakuView.start();
            }
        });
        this.danmakuView.prepare(new BaseDanmakuParser() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.12
            @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
            protected IDanmakus parse() {
                return new Danmakus();
            }
        }, this.danmakuContext);
        this.danmakuView.showFPS(false);
        this.danmakuView.enableDanmakuDrawingCache(false);
        this.danmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() { // from class: com.tomatolive.library.ui.view.custom.LivePusherInfoView.13
            @Override // master.flame.danmaku.controller.IDanmakuView.OnDanmakuClickListener
            public boolean onDanmakuLongClick(IDanmakus iDanmakus) {
                return false;
            }

            @Override // master.flame.danmaku.controller.IDanmakuView.OnDanmakuClickListener
            public boolean onViewClick(IDanmakuView iDanmakuView) {
                return false;
            }

            @Override // master.flame.danmaku.controller.IDanmakuView.OnDanmakuClickListener
            public boolean onDanmakuClick(IDanmakus iDanmakus) {
                if (iDanmakus.last().tag instanceof ChatEntity) {
                    ChatEntity chatEntity = (ChatEntity) iDanmakus.last().tag;
                    if (LivePusherInfoView.this.onLivePusherInfoCallback == null) {
                        return false;
                    }
                    LivePusherInfoView.this.onLivePusherInfoCallback.onClickUserAvatarListener(AppUtils.formatUserEntity(chatEntity));
                    return false;
                }
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.custom.LivePusherInfoView$10 */
    /* loaded from: classes3.dex */
    public class C444410 extends ViewCacheStuffer<BaseDanmuViewHolder> {
        C444410() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer
        /* renamed from: onCreateViewHolder */
        public BaseDanmuViewHolder mo6642onCreateViewHolder(int i) {
            if (i != 1) {
                LivePusherInfoView livePusherInfoView = LivePusherInfoView.this;
                return new NobilityDanmuViewHolder(View.inflate(livePusherInfoView.mContext, R$layout.fq_layout_live_msg_danmu_nobitity_view, null));
            }
            LivePusherInfoView livePusherInfoView2 = LivePusherInfoView.this;
            return new DanmuViewHolder(View.inflate(livePusherInfoView2.mContext, R$layout.fq_layout_live_msg_danmu_view, null));
        }

        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer
        public int getItemViewType(int i, BaseDanmaku baseDanmaku) {
            if (baseDanmaku == null) {
                return super.getItemViewType(i, baseDanmaku);
            }
            Object obj = baseDanmaku.tag;
            if (obj == null) {
                return super.getItemViewType(i, baseDanmaku);
            }
            if (obj instanceof ChatEntity) {
                return ((ChatEntity) obj).getDanmuType();
            }
            return super.getItemViewType(i, baseDanmaku);
        }

        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer
        public void onBindViewHolder(int i, BaseDanmuViewHolder baseDanmuViewHolder, BaseDanmaku baseDanmaku, AndroidDisplayer.DisplayerConfig displayerConfig, TextPaint textPaint) {
            Object obj;
            if (baseDanmaku == null || (obj = baseDanmaku.tag) == null || !(obj instanceof ChatEntity)) {
                return;
            }
            final ChatEntity chatEntity = (ChatEntity) obj;
            if (baseDanmuViewHolder instanceof DanmuViewHolder) {
                final DanmuViewHolder danmuViewHolder = (DanmuViewHolder) baseDanmuViewHolder;
                danmuViewHolder.tvContent.setText(chatEntity.getMsgText());
                danmuViewHolder.tvContent.setBackground(ContextCompat.getDrawable(LivePusherInfoView.this.mContext, chatEntity.getGuardType() == NumberUtils.string2int("3") ? R$drawable.fq_shape_danmu_guard_year_text_bg : R$drawable.fq_shape_danmu_guard_month_text_bg));
                danmuViewHolder.ivAvatarBg.setImageResource(chatEntity.getGuardType() == NumberUtils.string2int("3") ? R$drawable.fq_ic_guard_year_avatar_bg : R$drawable.fq_ic_guard_month_avatar_bg);
                if (LivePusherInfoView.this.mContext instanceof Activity) {
                    ((Activity) LivePusherInfoView.this.mContext).runOnUiThread(new Runnable() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$10$Tec8A-vZhAO0ofPY5rypZfLSWUc
                        @Override // java.lang.Runnable
                        public final void run() {
                            LivePusherInfoView.C444410.this.lambda$onBindViewHolder$0$LivePusherInfoView$10(danmuViewHolder, chatEntity);
                        }
                    });
                }
            }
            if (!(baseDanmuViewHolder instanceof NobilityDanmuViewHolder)) {
                return;
            }
            final NobilityDanmuViewHolder nobilityDanmuViewHolder = (NobilityDanmuViewHolder) baseDanmuViewHolder;
            nobilityDanmuViewHolder.tvTitle.setTextColor(ContextCompat.getColor(LivePusherInfoView.this.mContext, chatEntity.getNobilityType() == 7 ? R$color.fq_nobility_badge_7_open_tips : R$color.fq_text_white_color));
            nobilityDanmuViewHolder.tvTitle.setText(chatEntity.getMsgText());
            nobilityDanmuViewHolder.flAvatarBg.setBackgroundResource(AppUtils.getNobilityAvatarBgDrawableRes(chatEntity.getNobilityType()));
            nobilityDanmuViewHolder.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(chatEntity.getNobilityType()));
            nobilityDanmuViewHolder.tvTitle.setBackgroundResource(nobilityDanmuViewHolder.getTitleBgRes(chatEntity.getNobilityType()));
            nobilityDanmuViewHolder.ivBadgeLeftBg.setImageResource(nobilityDanmuViewHolder.getLeftImgRes(chatEntity.getNobilityType()));
            nobilityDanmuViewHolder.ivBadgeRightBg.setImageResource(nobilityDanmuViewHolder.getRightImgRes(chatEntity.getNobilityType()));
            if (!(LivePusherInfoView.this.mContext instanceof Activity)) {
                return;
            }
            ((Activity) LivePusherInfoView.this.mContext).runOnUiThread(new Runnable() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$10$ZhbU-T0_aZIfAfGAXm4zb1jx-n0
                @Override // java.lang.Runnable
                public final void run() {
                    LivePusherInfoView.C444410.this.lambda$onBindViewHolder$1$LivePusherInfoView$10(nobilityDanmuViewHolder, chatEntity);
                }
            });
        }

        public /* synthetic */ void lambda$onBindViewHolder$0$LivePusherInfoView$10(DanmuViewHolder danmuViewHolder, ChatEntity chatEntity) {
            GlideUtils.loadAvatar(LivePusherInfoView.this.mContext, danmuViewHolder.ivAvatar, chatEntity.getUserAvatar(), R$drawable.fq_ic_placeholder_avatar);
        }

        public /* synthetic */ void lambda$onBindViewHolder$1$LivePusherInfoView$10(NobilityDanmuViewHolder nobilityDanmuViewHolder, ChatEntity chatEntity) {
            GlideUtils.loadAvatar(LivePusherInfoView.this.mContext, nobilityDanmuViewHolder.ivAvatar, chatEntity.getUserAvatar(), R$drawable.fq_ic_placeholder_avatar);
        }

        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer, master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
        public void releaseResource(BaseDanmaku baseDanmaku) {
            super.releaseResource(baseDanmaku);
            if (baseDanmaku != null) {
                baseDanmaku.setTag(null);
            }
        }
    }

    public void setRootView(View view, final SwipeAnimationController swipeAnimationController) {
        this.swipeAnimationController = swipeAnimationController;
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$JUI0ES8ib4kaPfeo58dTwHUdb3E
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                boolean processEvent;
                processEvent = SwipeAnimationController.this.processEvent(motionEvent);
                return processEvent;
            }
        });
        mergeTouchListener(swipeAnimationController);
    }

    private void mergeTouchListener(final SwipeAnimationController swipeAnimationController) {
        final DanmakuTouchHelper instance = DanmakuTouchHelper.instance(this.danmakuView);
        this.danmakuView.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePusherInfoView$MiQO5Zx0YJCFRnKasUbB9WQUZvI
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return LivePusherInfoView.lambda$mergeTouchListener$14(SwipeAnimationController.this, instance, view, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$mergeTouchListener$14(SwipeAnimationController swipeAnimationController, DanmakuTouchHelper danmakuTouchHelper, View view, MotionEvent motionEvent) {
        boolean processEvent = swipeAnimationController.processEvent(motionEvent);
        boolean onTouchEvent = danmakuTouchHelper != null ? danmakuTouchHelper.onTouchEvent(motionEvent) : false;
        if (processEvent || onTouchEvent) {
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.custom.LivePusherInfoView$DanmuViewHolder */
    /* loaded from: classes3.dex */
    public class DanmuViewHolder extends BaseDanmuViewHolder {
        private ImageView ivAvatar;
        private ImageView ivAvatarBg;
        private TextView tvContent;

        private DanmuViewHolder(View view) {
            super(view);
            this.tvContent = (TextView) view.findViewById(R$id.tv_msg_content);
            this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
            this.ivAvatarBg = (ImageView) view.findViewById(R$id.iv_avatar_bg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.custom.LivePusherInfoView$NobilityDanmuViewHolder */
    /* loaded from: classes3.dex */
    public class NobilityDanmuViewHolder extends BaseDanmuViewHolder {
        private FrameLayout flAvatarBg;
        private ImageView ivAvatar;
        private ImageView ivBadge;
        private ImageView ivBadgeLeftBg;
        private ImageView ivBadgeRightBg;
        private TextView tvTitle;

        public NobilityDanmuViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R$id.tv_title);
            this.flAvatarBg = (FrameLayout) view.findViewById(R$id.fl_avatar_bg);
            this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
            this.ivBadge = (ImageView) view.findViewById(R$id.iv_badge);
            this.ivBadgeLeftBg = (ImageView) view.findViewById(R$id.iv_badge_left);
            this.ivBadgeRightBg = (ImageView) view.findViewById(R$id.iv_badge_right);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @DrawableRes
        public int getTitleBgRes(int i) {
            switch (i) {
                case 1:
                    return R$drawable.fq_shape_danmu_nobility_bg_1;
                case 2:
                    return R$drawable.fq_shape_danmu_nobility_bg_2;
                case 3:
                    return R$drawable.fq_shape_danmu_nobility_bg_3;
                case 4:
                    return R$drawable.fq_shape_danmu_nobility_bg_4;
                case 5:
                    return R$drawable.fq_shape_danmu_nobility_bg_5;
                case 6:
                    return R$drawable.fq_shape_danmu_nobility_bg_6;
                case 7:
                    return R$drawable.fq_shape_danmu_nobility_bg_7;
                default:
                    return R$drawable.fq_shape_danmu_nobility_bg_1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @DrawableRes
        public int getLeftImgRes(int i) {
            switch (i) {
                case 1:
                    return R$drawable.fq_ic_nobility_dm_roll_1_left;
                case 2:
                    return R$drawable.fq_ic_nobility_dm_roll_2_left;
                case 3:
                    return R$drawable.fq_ic_nobility_dm_roll_3_left;
                case 4:
                    return R$drawable.fq_ic_nobility_dm_roll_4_left;
                case 5:
                    return R$drawable.fq_ic_nobility_dm_roll_5_left;
                case 6:
                    return R$drawable.fq_ic_nobility_dm_roll_6_left;
                case 7:
                    return R$drawable.fq_ic_nobility_dm_roll_7_left;
                default:
                    return R$drawable.fq_ic_nobility_dm_roll_1_left;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @DrawableRes
        public int getRightImgRes(int i) {
            switch (i) {
                case 1:
                    return R$drawable.fq_ic_nobility_dm_roll_1_right;
                case 2:
                    return R$drawable.fq_ic_nobility_dm_roll_2_right;
                case 3:
                    return R$drawable.fq_ic_nobility_dm_roll_3_right;
                case 4:
                    return R$drawable.fq_ic_nobility_dm_roll_4_right;
                case 5:
                    return R$drawable.fq_ic_nobility_dm_roll_5_right;
                case 6:
                    return R$drawable.fq_ic_nobility_dm_roll_6_right;
                case 7:
                    return R$drawable.fq_ic_nobility_dm_roll_7_right;
                default:
                    return R$drawable.fq_ic_nobility_dm_roll_1_right;
            }
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.custom.LivePusherInfoView$BaseDanmuViewHolder */
    /* loaded from: classes3.dex */
    private abstract class BaseDanmuViewHolder extends ViewCacheStuffer.ViewHolder {
        public BaseDanmuViewHolder(View view) {
            super(view);
        }

        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer.ViewHolder
        public void measure(int i, int i2) {
            try {
                super.measure(i, i2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // master.flame.danmaku.danmaku.model.android.ViewCacheStuffer.ViewHolder
        public void layout(int i, int i2, int i3, int i4) {
            try {
                super.layout(i, i2, i3, i4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
