package com.tomatolive.library.p136ui.view.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.interfaces.OnPkViewListener;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimationListener;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.SvgaUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.PKInfoView */
/* loaded from: classes3.dex */
public class PKInfoView extends RelativeLayout {
    private AnimatorSet aceIconAnim;
    private AnimatorSet animatorSetAce;
    private AnimatorSet animatorSetEnter;
    private RotateAnimation animatorVictoryLight;
    private AnimatorSet countDown10AnimSet;
    private String currentLiveId;
    private ImageView ivBlueAnchorAvatar;
    private ImageView ivBlueAssistAvatar_1;
    private ImageView ivBlueAssistAvatar_2;
    private ImageView ivBlueAssistAvatar_3;
    private ImageView ivCountdown;
    private ImageView ivEmblemBlueLight;
    private ImageView ivEmblemBlueSmall;
    private ImageView ivEmblemRedLight;
    private ImageView ivEmblemRedSmall;
    private ImageView ivRedAnchorAvatar;
    private ImageView ivRedAssistAvatar_1;
    private ImageView ivRedAssistAvatar_2;
    private ImageView ivRedAssistAvatar_3;
    private ImageView mBlueAceView;
    private View mBlueAssistView;
    private View mBlueBgView;
    private View mBlueStartView;
    private Disposable mClockDisposable;
    private Context mContext;
    private ImageView mEmblemBlueView;
    private ImageView mEmblemRedView;
    private View mHeightView;
    private View mOVerLayoutRedView;
    private View mOverLayoutBlueView;
    private Disposable mPunishmentClockDisposable;
    private ImageView mRedAceView;
    private View mRedAssistView;
    private View mRedBgView;
    private View mRedStartView;
    private ImageView mVsView;
    private String matcherLiveId;
    private String matcherUserId;
    private OnPkViewListener onPkViewListener;
    private PKBarView pkBarView;
    private long pkTime;
    private long punishmentStartTime;
    private SVGAImageView svgaFire;
    private SVGAImageView svgaFirstKillMedal;
    private SVGAImageView svgaFirstKillStarLight;
    private SVGAParser svgaParser;
    private View tvAttention;
    private TextView tvBlueAnchorName;
    private TextView tvBluePopularity;
    private TextView tvClock;
    private TextView tvClock60;
    private View tvEnter;
    private View tvHomeCourt;
    private TextView tvPrepare;
    private TextView tvRedAnchorName;
    private TextView tvRedPopularity;
    private int[] countRes = {R$drawable.fq_ic_pk_number_1, R$drawable.fq_ic_pk_number_2, R$drawable.fq_ic_pk_number_3, R$drawable.fq_ic_pk_number_4, R$drawable.fq_ic_pk_number_5, R$drawable.fq_ic_pk_number_6, R$drawable.fq_ic_pk_number_7, R$drawable.fq_ic_pk_number_8, R$drawable.fq_ic_pk_number_9, R$drawable.fq_ic_pk_number_10};
    private final String WIN = "win";
    private final String DEFEAT = "defeat";
    private final String TIE = "tie";
    private long punishmentTime = 20;
    private long dur = 500;
    private long aceDur = 300;
    private long dur2 = 900;
    private long dur3 = 200;
    private long rotationDur = 1000;

    public PKInfoView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    public void setOnPkViewListener(OnPkViewListener onPkViewListener) {
        this.onPkViewListener = onPkViewListener;
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_layout_pk_info_view, this);
        this.tvHomeCourt = findViewById(R$id.tv_home_court);
        this.tvAttention = findViewById(R$id.tv_attention_anchor);
        this.tvEnter = findViewById(R$id.tv_enter_live);
        this.mVsView = (ImageView) findViewById(R$id.iv_vs_status);
        this.mRedBgView = findViewById(R$id.rl_versus_red_bg);
        this.mBlueBgView = findViewById(R$id.rl_versus_blue_bg);
        this.tvPrepare = (TextView) findViewById(R$id.tv_prepare);
        this.tvClock = (TextView) findViewById(R$id.tv_clock);
        this.tvClock60 = (TextView) findViewById(R$id.tv_clock_60);
        this.mRedStartView = findViewById(R$id.rl_red_start_bg);
        this.mBlueStartView = findViewById(R$id.rl_blue_start_bg);
        this.mRedAssistView = findViewById(R$id.ll_red_assist_bg);
        this.mBlueAssistView = findViewById(R$id.ll_blue_assist_bg);
        this.mRedAceView = (ImageView) findViewById(R$id.iv_red_group_fail);
        this.mBlueAceView = (ImageView) findViewById(R$id.iv_blue_group_fail);
        this.mEmblemRedView = (ImageView) findViewById(R$id.iv_red_pk_emblem);
        this.mEmblemBlueView = (ImageView) findViewById(R$id.iv_blue_pk_emblem);
        this.ivEmblemRedLight = (ImageView) findViewById(R$id.iv_red_pk_emblem_light);
        this.ivEmblemBlueLight = (ImageView) findViewById(R$id.iv_blue_pk_emblem_light);
        this.ivEmblemRedSmall = (ImageView) findViewById(R$id.iv_red_pk_emblem_small);
        this.ivEmblemBlueSmall = (ImageView) findViewById(R$id.iv_blue_pk_emblem_small);
        this.mOverLayoutBlueView = findViewById(R$id.iv_blue_shadow_bg);
        this.mOVerLayoutRedView = findViewById(R$id.iv_red_shadow_bg);
        this.ivCountdown = (ImageView) findViewById(R$id.iv_countdown_num);
        this.mHeightView = findViewById(R$id.rl_player_bg);
        this.pkBarView = (PKBarView) findViewById(R$id.pk_bar);
        this.ivRedAnchorAvatar = (ImageView) findViewById(R$id.iv_red_anchor_avatar);
        this.tvRedAnchorName = (TextView) findViewById(R$id.tv_red_anchor_name);
        this.ivBlueAnchorAvatar = (ImageView) findViewById(R$id.iv_blue_anchor_avatar);
        this.tvBlueAnchorName = (TextView) findViewById(R$id.tv_blue_anchor_name);
        this.tvRedPopularity = (TextView) findViewById(R$id.tv_red_popularity);
        this.ivRedAssistAvatar_1 = (ImageView) findViewById(R$id.iv_red_assist_avatar_1);
        this.ivRedAssistAvatar_2 = (ImageView) findViewById(R$id.iv_red_assist_avatar_2);
        this.ivRedAssistAvatar_3 = (ImageView) findViewById(R$id.iv_red_assist_avatar_3);
        this.tvBluePopularity = (TextView) findViewById(R$id.tv_blue_popularity);
        this.ivBlueAssistAvatar_1 = (ImageView) findViewById(R$id.iv_blue_assist_avatar_1);
        this.ivBlueAssistAvatar_2 = (ImageView) findViewById(R$id.iv_blue_assist_avatar_2);
        this.ivBlueAssistAvatar_3 = (ImageView) findViewById(R$id.iv_blue_assist_avatar_3);
        this.mOVerLayoutRedView.setPivotX(0.0f);
        this.mOVerLayoutRedView.setPivotY(0.0f);
        this.mOverLayoutBlueView.setPivotX(0.0f);
        this.mOverLayoutBlueView.setPivotY(0.0f);
        this.svgaFirstKillStarLight = (SVGAImageView) findViewById(R$id.svga_first_kill_star_light);
        this.svgaFirstKillMedal = (SVGAImageView) findViewById(R$id.svga_first_kill_medal);
        this.svgaFire = (SVGAImageView) findViewById(R$id.svga_fire);
        this.ivEmblemRedLight.setImageResource(R$drawable.fq_ic_pk_emblem_victory_light);
        this.ivEmblemBlueLight.setImageResource(R$drawable.fq_ic_pk_emblem_victory_light);
        initListener();
    }

    public void showLMSuccessView(String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, long j2, boolean z) {
        this.currentLiveId = str;
        this.matcherUserId = str4;
        this.matcherLiveId = str5;
        this.pkTime = j;
        this.punishmentTime = j2;
        loadRedAssistAvatar(this.ivRedAnchorAvatar, str3);
        this.tvRedAnchorName.setText(StringUtils.formatStrLen(str2, 7));
        loadBlueAssistAvatar(this.ivBlueAnchorAvatar, str6);
        this.tvBlueAnchorName.setText(StringUtils.formatStrLen(str7, 7));
        initDefaultView(true);
        initDownCountAnim();
        if (z && j > 0) {
            onPKStart(j, j2);
        }
        setVisibility(0);
    }

    public void showLMSuccessView(String str, String str2, String str3, String str4, String str5, String str6, String str7, boolean z) {
        this.currentLiveId = str;
        this.matcherUserId = str4;
        this.matcherLiveId = str5;
        loadRedAssistAvatar(this.ivRedAnchorAvatar, str3);
        this.tvRedAnchorName.setText(StringUtils.formatStrLen(str2, 7));
        loadBlueAssistAvatar(this.ivBlueAnchorAvatar, str6);
        this.tvBlueAnchorName.setText(StringUtils.formatStrLen(str7, 7));
        initDefaultView(z);
        initDownCountAnim();
        setVisibility(0);
    }

    public void onPKStart(long j, long j2) {
        this.pkTime = j;
        this.punishmentTime = j2;
        if (j <= 0) {
            return;
        }
        Disposable disposable = this.mClockDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }
        startPk();
    }

    public void startPk() {
        showPkCountDown();
        playEnterAnimation();
    }

    public void dealFirstKill(String str, String str2) {
        showFirstKillAnim(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPkCountDownText(long j) {
        if (j <= 60) {
            this.tvClock.setVisibility(4);
            this.tvClock60.setVisibility(0);
            this.tvClock60.setText(String.valueOf(j));
            return;
        }
        this.tvClock.setText(this.mContext.getString(R$string.fq_clock_down, DateUtils.secondToString(j)));
    }

    private void initDefaultView(boolean z) {
        int i = 0;
        if (!TextUtils.isEmpty(this.matcherUserId)) {
            this.tvAttention.setVisibility(AppUtils.isAttentionAnchor(this.matcherUserId) ? 4 : 0);
        }
        this.tvEnter.setVisibility(z ? 0 : 8);
        this.tvHomeCourt.setVisibility(0);
        this.ivCountdown.setVisibility(4);
        this.tvClock.setVisibility(4);
        this.tvClock60.setVisibility(4);
        this.mRedBgView.setVisibility(4);
        this.mRedStartView.setAlpha(0.0f);
        this.mRedAssistView.setAlpha(0.0f);
        this.mEmblemRedView.setAlpha(0.0f);
        this.mRedAceView.setVisibility(4);
        this.ivEmblemRedSmall.setVisibility(4);
        this.ivEmblemRedLight.setVisibility(4);
        this.mOVerLayoutRedView.setAlpha(0.0f);
        this.mBlueBgView.setVisibility(4);
        this.mBlueStartView.setAlpha(0.0f);
        this.mBlueAssistView.setAlpha(0.0f);
        this.mEmblemBlueView.setAlpha(0.0f);
        this.mBlueAceView.setVisibility(4);
        this.ivEmblemBlueSmall.setVisibility(4);
        this.ivEmblemBlueLight.setVisibility(4);
        this.mOverLayoutBlueView.setAlpha(0.0f);
        this.pkBarView.setVisibility(4);
        TextView textView = this.tvPrepare;
        if (z) {
            i = 4;
        }
        textView.setVisibility(i);
        this.tvPrepare.setText(R$string.fq_ready_ok);
        this.tvPrepare.setEnabled(true);
        this.mVsView.setVisibility(4);
        this.mVsView.setImageResource(R$drawable.fq_ic_pk_versus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPunishmentCountDownText(long j) {
        this.tvClock60.setText(this.mContext.getString(R$string.fq_punishment_clock_down, DateUtils.secondToString(j)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFireAnim() {
        if (this.svgaParser == null) {
            this.svgaParser = new SVGAParser(getContext());
        }
        if (this.svgaFire.isAnimating()) {
            return;
        }
        SvgaUtils.playAssetRes(ConstantUtils.FIREPOWER_VALUE_SVGA_PATH, this.svgaFire, this.svgaParser);
    }

    private void showFirstKillAnim(String str, String str2) {
        if (this.svgaParser == null) {
            this.svgaParser = new SVGAParser(getContext());
        }
        boolean equals = TextUtils.equals(this.currentLiveId, str);
        SvgaUtils.playAssetRes(ConstantUtils.FIRST_KILL_STAR_LIGHT_PATH, this.svgaFirstKillStarLight, this.svgaParser);
        SvgaUtils.playAssetRes(ConstantUtils.FIRST_KILL_MEDAL_PATH, this.svgaFirstKillMedal, GlideUtils.getFirstKillSVGADynamicEntity(this.mContext, str2, equals), this.svgaParser);
        this.svgaFirstKillMedal.setCallback(new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.1
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                PKInfoView.this.svgaFirstKillStarLight.stopAnimation();
            }
        });
    }

    private void showPkCountDown() {
        this.tvClock.setVisibility(4);
        this.tvClock60.setVisibility(4);
        long j = this.pkTime;
        if (j > 60) {
            this.tvClock.setVisibility(0);
        } else if (j <= 60 && j > 10) {
            this.tvClock60.setVisibility(0);
        }
        this.pkBarView.setVisibility(0);
        setPkCountDownText(this.pkTime);
        ViewGroup.LayoutParams layoutParams = this.tvClock60.getLayoutParams();
        layoutParams.width = ConvertUtils.dp2px(22.0f);
        layoutParams.height = ConvertUtils.dp2px(22.0f);
        this.tvClock60.setPadding(0, 0, 0, 0);
        this.tvClock60.setLayoutParams(layoutParams);
        this.mClockDisposable = Flowable.intervalRange(0L, 1 + this.pkTime, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                long longValue = PKInfoView.this.pkTime - l.longValue();
                if (longValue <= 10) {
                    PKInfoView.this.tvClock60.setVisibility(4);
                    PKInfoView.this.ivCountdown.setVisibility(0);
                    PKInfoView.this.showFireAnim();
                    int i = (int) longValue;
                    if (i == 0) {
                        return;
                    }
                    PKInfoView.this.ivCountdown.setImageResource(PKInfoView.this.countRes[i - 1]);
                    if (PKInfoView.this.countDown10AnimSet == null) {
                        return;
                    }
                    PKInfoView.this.countDown10AnimSet.start();
                    return;
                }
                PKInfoView pKInfoView = PKInfoView.this;
                pKInfoView.setPkCountDownText(pKInfoView.pkTime - l.longValue());
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$PKInfoView$mJiDvhBdjMW3MCDXbrBlXKJY6vg
            @Override // io.reactivex.functions.Action
            public final void run() {
                PKInfoView.this.lambda$showPkCountDown$0$PKInfoView();
            }
        }).subscribe();
    }

    public /* synthetic */ void lambda$showPkCountDown$0$PKInfoView() throws Exception {
        this.punishmentStartTime = System.currentTimeMillis();
        this.ivCountdown.setVisibility(8);
        stopFireAnim();
        OnPkViewListener onPkViewListener = this.onPkViewListener;
        if (onPkViewListener != null) {
            onPkViewListener.onPkCountDownComplete();
        }
    }

    public void showPunishmentCountDown() {
        if (this.punishmentTime <= 0) {
            showPunishmentEndView();
            return;
        }
        Disposable disposable = this.mPunishmentClockDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }
        this.mPunishmentClockDisposable = Flowable.intervalRange(0L, this.punishmentTime + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                PKInfoView pKInfoView = PKInfoView.this;
                pKInfoView.setPunishmentCountDownText(pKInfoView.punishmentTime - l.longValue());
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.3
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PKInfoView.this.showPunishmentEndView();
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPunishmentEndView() {
        stopVictoryLightAnim();
        this.tvClock60.setVisibility(4);
        this.pkBarView.setVisibility(4);
        this.mRedBgView.setVisibility(4);
        this.mBlueBgView.setVisibility(4);
        this.ivEmblemRedLight.setVisibility(4);
        this.ivEmblemBlueLight.setVisibility(4);
        this.ivEmblemRedSmall.setVisibility(4);
        this.ivEmblemBlueSmall.setVisibility(4);
        this.mVsView.setVisibility(4);
        this.mEmblemRedView.setAlpha(0.0f);
        this.mEmblemBlueView.setAlpha(0.0f);
        this.mOVerLayoutRedView.setAlpha(0.0f);
        this.mOverLayoutBlueView.setAlpha(0.0f);
    }

    private void initListener() {
        this.tvEnter.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$PKInfoView$fV-7jjOStJcwnMDHx9gF2Jjh5aE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKInfoView.this.lambda$initListener$1$PKInfoView(view);
            }
        });
        this.tvAttention.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$PKInfoView$LZ_2PMvOoBNefSqRfxfWMU9IZyM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKInfoView.this.lambda$initListener$2$PKInfoView(view);
            }
        });
        this.tvPrepare.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$PKInfoView$C8LZ0X0iLqlgKdcUaTpR063T8t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKInfoView.this.lambda$initListener$3$PKInfoView(view);
            }
        });
        this.mRedBgView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$PKInfoView$Y7nBGujSpIJw38YZlLTJCewUhUQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKInfoView.this.lambda$initListener$4$PKInfoView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$PKInfoView(View view) {
        OnPkViewListener onPkViewListener = this.onPkViewListener;
        if (onPkViewListener != null) {
            onPkViewListener.onEnterLiveRoom(this.matcherLiveId);
        }
    }

    public /* synthetic */ void lambda$initListener$2$PKInfoView(View view) {
        OnPkViewListener onPkViewListener = this.onPkViewListener;
        if (onPkViewListener != null) {
            onPkViewListener.onAttentionAnchor(this.matcherUserId, view);
        }
    }

    public /* synthetic */ void lambda$initListener$3$PKInfoView(View view) {
        OnPkViewListener onPkViewListener = this.onPkViewListener;
        if (onPkViewListener != null) {
            onPkViewListener.onReadyPK(view);
        }
    }

    public /* synthetic */ void lambda$initListener$4$PKInfoView(View view) {
        OnPkViewListener onPkViewListener = this.onPkViewListener;
        if (onPkViewListener != null) {
            onPkViewListener.onShowPKRanking();
        }
    }

    public void updatePkBar(long j, long j2) {
        this.pkBarView.updatePress(j, j2);
    }

    private void initDownCountAnim() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.ivCountdown, "scaleX", 1.0f, 1.6f, 1.2f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.ivCountdown, "scaleY", 1.0f, 1.6f, 1.2f);
        this.countDown10AnimSet = new AnimatorSet();
        this.countDown10AnimSet.playTogether(ofFloat, ofFloat2);
        this.countDown10AnimSet.setDuration(800L);
    }

    public void adjustViewLayout(int i, int i2) {
        ViewGroup.LayoutParams layoutParams = this.mHeightView.getLayoutParams();
        layoutParams.height = i;
        this.mHeightView.setLayoutParams(layoutParams);
        findViewById(R$id.margin_placeholder).setTranslationY(i2);
    }

    public void onRelease() {
        updateDefaultAssistData();
        this.mOVerLayoutRedView.setAlpha(0.0f);
        this.mOverLayoutBlueView.setAlpha(0.0f);
        this.ivEmblemRedLight.setVisibility(4);
        this.ivEmblemBlueLight.setVisibility(4);
        this.tvHomeCourt.setVisibility(4);
        this.tvEnter.setVisibility(4);
        this.tvAttention.setVisibility(4);
        this.tvPrepare.setVisibility(4);
        releasePkClock();
        releasePunishmentClock();
        stopFireAnim();
        stopCountDownAnim();
        stopEnterAnim();
        stopAceAnim();
        stopFirstKillAnim();
        stopVictoryLightAnim();
        setVisibility(4);
    }

    private void stopAceAnim() {
        AnimatorSet animatorSet = this.animatorSetAce;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.animatorSetAce.cancel();
            this.animatorSetAce = null;
        }
        AnimatorSet animatorSet2 = this.aceIconAnim;
        if (animatorSet2 == null || !animatorSet2.isRunning()) {
            return;
        }
        this.aceIconAnim.cancel();
        this.aceIconAnim = null;
    }

    private void stopFirstKillAnim() {
        SVGAImageView sVGAImageView = this.svgaFirstKillStarLight;
        if (sVGAImageView != null && sVGAImageView.isAnimating()) {
            this.svgaFirstKillStarLight.stopAnimation();
        }
        SVGAImageView sVGAImageView2 = this.svgaFirstKillMedal;
        if (sVGAImageView2 == null || !sVGAImageView2.isAnimating()) {
            return;
        }
        this.svgaFirstKillMedal.stopAnimation();
    }

    private void stopEnterAnim() {
        AnimatorSet animatorSet = this.animatorSetEnter;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.animatorSetEnter.cancel();
        this.animatorSetEnter = null;
    }

    private void stopCountDownAnim() {
        AnimatorSet animatorSet = this.countDown10AnimSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.countDown10AnimSet.cancel();
        this.countDown10AnimSet = null;
    }

    private void stopFireAnim() {
        SVGAImageView sVGAImageView = this.svgaFire;
        if (sVGAImageView == null || !sVGAImageView.isAnimating()) {
            return;
        }
        this.svgaFire.stopAnimation();
    }

    private void stopVictoryLightAnim() {
        RotateAnimation rotateAnimation = this.animatorVictoryLight;
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
            this.animatorVictoryLight.setAnimationListener(null);
        }
        this.ivEmblemRedLight.clearAnimation();
        this.ivEmblemBlueLight.clearAnimation();
        this.ivEmblemRedLight.setVisibility(4);
        this.ivEmblemBlueLight.setVisibility(4);
    }

    private void releasePkClock() {
        Disposable disposable = this.mClockDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mClockDisposable.dispose();
        this.mClockDisposable = null;
    }

    private void releasePunishmentClock() {
        Disposable disposable = this.mPunishmentClockDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mPunishmentClockDisposable.dispose();
        this.mPunishmentClockDisposable = null;
    }

    public void playEnterAnimation() {
        this.tvPrepare.setVisibility(4);
        this.mRedStartView.setAlpha(1.0f);
        this.mRedAssistView.setAlpha(0.0f);
        this.mBlueStartView.setAlpha(1.0f);
        this.mBlueAssistView.setAlpha(0.0f);
        this.mRedBgView.setVisibility(0);
        this.mBlueBgView.setVisibility(0);
        this.mVsView.setVisibility(0);
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.mRedBgView, "translationX", -507.0f, 0.0f).setDuration(this.dur);
        duration.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(this.mBlueBgView, "translationX", 507.0f, 0.0f).setDuration(this.dur);
        duration2.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.mVsView, "scaleX", 0.0f, 1.0f).setDuration(this.dur);
        duration3.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration4 = ObjectAnimator.ofFloat(this.mVsView, "scaleY", 0.0f, 1.0f).setDuration(this.dur);
        duration4.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration5 = ObjectAnimator.ofFloat(this.mVsView, "scaleX", 1.0f, 1.9f, 1.0f).setDuration(this.dur2);
        duration5.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration6 = ObjectAnimator.ofFloat(this.mVsView, "scaleY", 1.0f, 1.9f, 1.0f).setDuration(this.dur2);
        duration6.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration7 = ObjectAnimator.ofFloat(this.mRedStartView, "alpha", 1.0f, 0.0f).setDuration(this.dur3);
        duration7.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration8 = ObjectAnimator.ofFloat(this.mBlueStartView, "alpha", 1.0f, 0.0f).setDuration(this.dur3);
        duration7.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration9 = ObjectAnimator.ofFloat(this.mRedAssistView, "alpha", 0.0f, 1.0f).setDuration(this.dur3);
        duration7.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator duration10 = ObjectAnimator.ofFloat(this.mBlueAssistView, "alpha", 0.0f, 1.0f).setDuration(this.dur3);
        duration7.setInterpolator(new AccelerateInterpolator());
        this.animatorSetEnter = new AnimatorSet();
        this.animatorSetEnter.play(duration).with(duration2).with(duration3).with(duration4).before(duration5).before(duration6);
        this.animatorSetEnter.play(duration5).before(duration7).before(duration8);
        this.animatorSetEnter.play(duration8).before(duration9).before(duration10);
        this.animatorSetEnter.addListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.5
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }
        });
        this.animatorSetEnter.start();
    }

    public void playAceAnimation(final String str) {
        char c;
        View view;
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("rotation", 0.0f, 720.0f);
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.5f, 1.0f);
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.5f, 1.0f);
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f);
        ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.mEmblemRedView, ofFloat, ofFloat2, ofFloat3, ofFloat4).setDuration(this.rotationDur);
        ObjectAnimator duration2 = ObjectAnimator.ofPropertyValuesHolder(this.mEmblemBlueView, ofFloat, ofFloat2, ofFloat3, ofFloat4).setDuration(this.rotationDur);
        int hashCode = str.hashCode();
        if (hashCode == -1335637709) {
            if (str.equals("defeat")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 114832) {
            if (hashCode == 117724 && str.equals("win")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("tie")) {
                c = 1;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                this.mEmblemRedView.setImageResource(R$drawable.fq_ic_pk_emblem_draw);
                this.ivEmblemRedSmall.setImageResource(R$drawable.fq_ic_pk_emblem_draw);
                this.mEmblemBlueView.setImageResource(R$drawable.fq_ic_pk_emblem_draw);
                this.ivEmblemBlueSmall.setImageResource(R$drawable.fq_ic_pk_emblem_draw);
                this.mVsView.setImageResource(R$drawable.fq_ic_pk_versus_tie);
            } else if (c == 2) {
                this.mEmblemRedView.setImageResource(R$drawable.fq_ic_pk_emblem_defeat);
                this.ivEmblemRedSmall.setImageResource(R$drawable.fq_ic_pk_emblem_defeat);
                this.mEmblemBlueView.setImageResource(R$drawable.fq_ic_pk_emblem_victory);
                this.ivEmblemBlueSmall.setImageResource(R$drawable.fq_ic_pk_emblem_victory);
                this.mVsView.setImageResource(R$drawable.fq_ic_pk_versus_ko);
                view = this.mOVerLayoutRedView;
            }
            view = null;
        } else {
            this.mEmblemRedView.setImageResource(R$drawable.fq_ic_pk_emblem_victory);
            this.ivEmblemRedSmall.setImageResource(R$drawable.fq_ic_pk_emblem_victory);
            this.mEmblemBlueView.setImageResource(R$drawable.fq_ic_pk_emblem_defeat);
            this.ivEmblemBlueSmall.setImageResource(R$drawable.fq_ic_pk_emblem_defeat);
            this.mVsView.setImageResource(R$drawable.fq_ic_pk_versus_ko);
            view = this.mOverLayoutBlueView;
        }
        this.animatorSetAce = new AnimatorSet();
        if (view != null) {
            this.animatorSetAce.play(duration).with(duration2).before(ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f).setDuration(this.rotationDur)).before(ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).setDuration(100L));
        } else {
            this.animatorSetAce.playTogether(duration, duration2);
        }
        this.animatorSetAce.addListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.6
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PKInfoView.this.showAceIconAnim(str);
            }
        });
        this.animatorSetAce.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAceIconAnim(String str) {
        char c;
        ImageView imageView;
        int hashCode = str.hashCode();
        if (hashCode != -1335637709) {
            if (hashCode == 117724 && str.equals("win")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("defeat")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            this.ivEmblemRedLight.setVisibility(0);
            this.ivEmblemBlueLight.setVisibility(4);
            this.mBlueAceView.setVisibility(0);
            playVictoryLightAnim(this.ivEmblemRedLight);
            imageView = this.mBlueAceView;
        } else if (c != 1) {
            imageView = null;
        } else {
            this.ivEmblemRedLight.setVisibility(4);
            this.ivEmblemBlueLight.setVisibility(0);
            this.mRedAceView.setVisibility(0);
            playVictoryLightAnim(this.ivEmblemBlueLight);
            imageView = this.mRedAceView;
        }
        if (imageView != null) {
            ObjectAnimator duration = ObjectAnimator.ofFloat(imageView, "scaleX", 1.7f, 1.0f).setDuration(this.aceDur);
            duration.setInterpolator(new AccelerateInterpolator());
            ObjectAnimator duration2 = ObjectAnimator.ofFloat(imageView, "scaleY", 1.7f, 1.0f).setDuration(this.aceDur);
            duration2.setInterpolator(new AccelerateInterpolator());
            this.aceIconAnim = new AnimatorSet();
            this.aceIconAnim.playTogether(duration, duration2);
            this.aceIconAnim.start();
            this.aceIconAnim.addListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.7
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PKInfoView.this.showPunishmentView();
                }
            });
            return;
        }
        showEmblemSmallView();
        showPunishmentView();
    }

    private void playVictoryLightAnim(View view) {
        this.animatorVictoryLight = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        this.animatorVictoryLight.setDuration(4000L);
        this.animatorVictoryLight.setAnimationListener(new SimpleAnimationListener() { // from class: com.tomatolive.library.ui.view.custom.PKInfoView.8
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                PKInfoView.this.showEmblemSmallView();
            }
        });
        view.startAnimation(this.animatorVictoryLight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPunishmentView() {
        this.tvClock60.setVisibility(0);
        ViewGroup.LayoutParams layoutParams = this.tvClock60.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        this.tvClock60.setPadding(ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f));
        this.tvClock60.setLayoutParams(layoutParams);
        showPunishmentCountDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmblemSmallView() {
        this.mEmblemRedView.setAlpha(0.0f);
        this.mEmblemBlueView.setAlpha(0.0f);
        this.ivEmblemRedSmall.setVisibility(0);
        this.ivEmblemBlueSmall.setVisibility(0);
        this.ivEmblemRedLight.setVisibility(4);
        this.ivEmblemBlueLight.setVisibility(4);
    }

    public void onPKEnd(String str, String str2, String str3, List<String> list, String str4, String str5, String str6, List<String> list2, boolean z) {
        if (z) {
            this.punishmentTime -= (System.currentTimeMillis() - this.punishmentStartTime) / 1000;
        }
        onPKEnd(str, str2, str3, list, str4, str5, str6, list2);
    }

    public void onPKEnd(String str, String str2, String str3, List<String> list, String str4, String str5, String str6, List<String> list2) {
        dealPkStopFromSocket();
        initAssistData(str, str2, str3, list, str4, str5, str6, list2);
        long string2long = NumberUtils.string2long(str2);
        long string2long2 = NumberUtils.string2long(str5);
        if (TextUtils.equals(this.currentLiveId, str)) {
            string2long = NumberUtils.string2long(str2);
            string2long2 = NumberUtils.string2long(str5);
        } else if (TextUtils.equals(this.currentLiveId, str4)) {
            string2long = NumberUtils.string2long(str5);
            string2long2 = NumberUtils.string2long(str2);
        }
        int i = (string2long > string2long2 ? 1 : (string2long == string2long2 ? 0 : -1));
        playAceAnimation(i > 0 ? "win" : i < 0 ? "defeat" : "tie");
    }

    public void initAssistData(String str, String str2, String str3, List<String> list, String str4, String str5, String str6, List<String> list2) {
        if (!TextUtils.equals(this.currentLiveId, str) && TextUtils.equals(this.currentLiveId, str4)) {
            str5 = str2;
            str2 = str5;
            str6 = str3;
            str3 = str6;
            list2 = list;
            list = list2;
        }
        this.tvRedPopularity.setText(AppUtils.formatLivePopularityCount(NumberUtils.string2long(str3)));
        if (list != null && !list.isEmpty()) {
            if (list.size() >= 3) {
                loadRedAssistAvatar(this.ivRedAssistAvatar_1, list.get(0));
                loadRedAssistAvatar(this.ivRedAssistAvatar_2, list.get(1));
                loadRedAssistAvatar(this.ivRedAssistAvatar_3, list.get(2));
            } else if (list.size() >= 2) {
                loadRedAssistAvatar(this.ivRedAssistAvatar_1, list.get(0));
                loadRedAssistAvatar(this.ivRedAssistAvatar_2, list.get(1));
                loadRedAssistAvatar(this.ivRedAssistAvatar_3, null);
            } else {
                loadRedAssistAvatar(this.ivRedAssistAvatar_1, list.get(0));
                loadRedAssistAvatar(this.ivRedAssistAvatar_2, null);
                loadRedAssistAvatar(this.ivRedAssistAvatar_3, null);
            }
        } else {
            loadRedAssistAvatar(this.ivRedAssistAvatar_1, null);
            loadRedAssistAvatar(this.ivRedAssistAvatar_2, null);
            loadRedAssistAvatar(this.ivRedAssistAvatar_3, null);
        }
        this.tvBluePopularity.setText(AppUtils.formatLivePopularityCount(NumberUtils.string2long(str6)));
        if (list2 != null && !list2.isEmpty()) {
            if (list2.size() >= 3) {
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_1, list2.get(0));
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_2, list2.get(1));
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_3, list2.get(2));
            } else if (list2.size() >= 2) {
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_1, list2.get(0));
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_2, list2.get(1));
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_3, null);
            } else {
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_1, list2.get(0));
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_2, null);
                loadBlueAssistAvatar(this.ivBlueAssistAvatar_3, null);
            }
        } else {
            loadBlueAssistAvatar(this.ivBlueAssistAvatar_1, null);
            loadBlueAssistAvatar(this.ivBlueAssistAvatar_2, null);
            loadBlueAssistAvatar(this.ivBlueAssistAvatar_3, null);
        }
        updatePkBar(NumberUtils.string2long(str2), NumberUtils.string2long(str5));
    }

    private void updateDefaultAssistData() {
        loadRedAssistAvatar(this.ivRedAssistAvatar_1, null);
        loadRedAssistAvatar(this.ivRedAssistAvatar_2, null);
        loadRedAssistAvatar(this.ivRedAssistAvatar_3, null);
        loadBlueAssistAvatar(this.ivBlueAssistAvatar_1, null);
        loadBlueAssistAvatar(this.ivBlueAssistAvatar_2, null);
        loadBlueAssistAvatar(this.ivBlueAssistAvatar_3, null);
        this.tvRedPopularity.setText("0");
        this.tvBluePopularity.setText("0");
        updatePkBar(0L, 0L);
    }

    private void loadRedAssistAvatar(ImageView imageView, String str) {
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(R$drawable.fq_ic_pk_versus_red_sofa);
        } else if (TextUtils.equals("rankHide", str)) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, str, R$drawable.fq_ic_placeholder_avatar);
        }
    }

    private void loadBlueAssistAvatar(ImageView imageView, String str) {
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(R$drawable.fq_ic_pk_versus_blue_sofa);
        } else if (TextUtils.equals("rankHide", str)) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, str, R$drawable.fq_ic_placeholder_avatar);
        }
    }

    public void dealPkStopFromSocket() {
        releasePkClock();
        stopCountDownAnim();
        stopFireAnim();
        this.tvClock.setVisibility(4);
        this.tvClock60.setVisibility(4);
        this.ivCountdown.setVisibility(4);
        this.pkBarView.setVisibility(0);
        this.mRedBgView.setVisibility(0);
        this.mBlueBgView.setVisibility(0);
        this.mVsView.setVisibility(0);
        this.mRedAssistView.setAlpha(1.0f);
        this.mBlueAssistView.setAlpha(1.0f);
    }
}
