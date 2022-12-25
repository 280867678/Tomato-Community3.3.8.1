package com.tomatolive.library.p136ui.view.gift;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/* renamed from: com.tomatolive.library.ui.view.gift.GiftFrameLayout */
/* loaded from: classes3.dex */
public class GiftFrameLayout extends RelativeLayout implements Handler.Callback {
    private static final int INTERVAL = 200;
    private static final int RESTART_GIFT_ANIMATION_CODE = 1002;
    private static final String TAG = "GiftFrameLayout";
    private BarrageEndAnimationListener barrageEndAnimationListener;
    private Handler comboHandler;
    private ICustomAnim customAnim;
    private final int[] giftNumbers;
    private boolean isDelete;
    private boolean isEnd;
    private boolean isHideMode;
    private boolean isShowing;
    private ImageView ivAvatar;
    public SVGAImageView ivSvgaImageView;
    private LinearLayout llBatchNum;
    private LinearLayout llGiftNum;
    private LinearLayout llGiftNumBg;
    private volatile int mCombo;
    private Context mContext;
    private Runnable mCurrentAnimRunnable;
    private GiftAnimModel mGift;
    private LeftGiftAnimDismissListener mGiftAnimationListener;
    private volatile int mGiftCount;
    private Handler mHandler;
    private int mIndex;
    private volatile int mJumpCombo;
    private RelativeLayout rlBaseInfo;
    private View rootView;
    private Runnable runnable;
    private SVGAParser svgaParser;
    private TextView tvGiftName;
    private TextView tvNickName;

    /* renamed from: com.tomatolive.library.ui.view.gift.GiftFrameLayout$BarrageEndAnimationListener */
    /* loaded from: classes3.dex */
    public interface BarrageEndAnimationListener {
        void onEndAnimation(GiftAnimModel giftAnimModel);

        void onStartAnimation(GiftAnimModel giftAnimModel);
    }

    /* renamed from: com.tomatolive.library.ui.view.gift.GiftFrameLayout$LeftGiftAnimDismissListener */
    /* loaded from: classes3.dex */
    public interface LeftGiftAnimDismissListener {
        void dismiss(GiftFrameLayout giftFrameLayout);

        void onClick(GiftAnimModel giftAnimModel);
    }

    public GiftFrameLayout(Context context) {
        this(context, null);
    }

    public GiftFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new Handler(this);
        this.comboHandler = new Handler(this);
        this.mIndex = 1;
        this.mCombo = 0;
        this.mJumpCombo = 1;
        this.isShowing = false;
        this.isEnd = true;
        this.isHideMode = false;
        this.giftNumbers = new int[]{R$drawable.fq_ic_gift_num_0, R$drawable.fq_ic_gift_num_1, R$drawable.fq_ic_gift_num_2, R$drawable.fq_ic_gift_num_3, R$drawable.fq_ic_gift_num_4, R$drawable.fq_ic_gift_num_5, R$drawable.fq_ic_gift_num_6, R$drawable.fq_ic_gift_num_7, R$drawable.fq_ic_gift_num_8, R$drawable.fq_ic_gift_num_9};
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.rootView = LayoutInflater.from(this.mContext).inflate(R$layout.fq_layout_gift_anim_view, (ViewGroup) null);
        this.rlBaseInfo = (RelativeLayout) this.rootView.findViewById(R$id.fq_rl_name_bg);
        this.tvNickName = (TextView) this.rootView.findViewById(R$id.fq_stv_name);
        this.tvGiftName = (TextView) this.rootView.findViewById(R$id.fq_tv_gift_name);
        this.ivAvatar = (ImageView) this.rootView.findViewById(R$id.iv_avatar);
        this.ivSvgaImageView = (SVGAImageView) this.rootView.findViewById(R$id.fq_iv_gift_svga_view);
        this.llGiftNumBg = (LinearLayout) this.rootView.findViewById(R$id.ll_gift_num_bg);
        this.llGiftNum = (LinearLayout) this.rootView.findViewById(R$id.ll_gift_num);
        this.llBatchNum = (LinearLayout) this.rootView.findViewById(R$id.ll_batch_num);
        this.svgaParser = new SVGAParser(this.mContext);
        this.ivSvgaImageView.setCallback(new SVGAAnimationListener());
        this.tvNickName.setSelected(true);
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setVisibility(8);
            this.llGiftNum.addView(imageView);
        }
        for (int i2 = 0; i2 < 5; i2++) {
            ImageView imageView2 = new ImageView(this.mContext);
            imageView2.setVisibility(8);
            this.llBatchNum.addView(imageView2);
        }
        addView(this.rootView);
        findViewById(R$id.fq_rl_name_bg).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.gift.-$$Lambda$GiftFrameLayout$_B7MiT3o_dqazn1_TM0FrY4OhIU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GiftFrameLayout.this.lambda$initView$0$GiftFrameLayout(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$GiftFrameLayout(View view) {
        LeftGiftAnimDismissListener leftGiftAnimDismissListener = this.mGiftAnimationListener;
        if (leftGiftAnimDismissListener != null) {
            leftGiftAnimDismissListener.onClick(this.mGift);
        }
    }

    public void firstHideLayout() {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f));
        ofPropertyValuesHolder.setStartDelay(0L);
        ofPropertyValuesHolder.setDuration(0L);
        ofPropertyValuesHolder.start();
    }

    public void setHideMode(boolean z) {
        this.isHideMode = z;
    }

    public void setGiftViewEndVisibility(boolean z) {
        if (this.isHideMode && z) {
            setVisibility(8);
        } else {
            setVisibility(4);
        }
    }

    public boolean setGift(GiftAnimModel giftAnimModel) {
        if (giftAnimModel == null) {
            return false;
        }
        this.mGift = giftAnimModel;
        if (this.mGift.getJumpCombo() == 0) {
            this.mGiftCount = this.mGift.getGiftCount();
            this.mCombo = 1;
        } else {
            this.mGiftCount = this.mGift.getJumpCombo();
            this.mCombo = this.mGift.getJumpCombo();
        }
        this.mJumpCombo = this.mGift.getJumpCombo();
        if (!TextUtils.isEmpty(giftAnimModel.getSendUserName())) {
            this.tvNickName.setText(giftAnimModel.getSendUserName());
        }
        if (!TextUtils.isEmpty(giftAnimModel.getGiftId())) {
            this.tvGiftName.setText(this.mContext.getString(R$string.fq_send) + this.mContext.getString(R$string.fq_black_space) + giftAnimModel.getGiftName());
        }
        this.barrageEndAnimationListener = giftAnimModel.getAnimationListener();
        if (!this.mGift.isSendSingleGift()) {
            initLuckyGiftBatchCount(this.mGift.giftNum);
        }
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, giftAnimModel.getSendUserAvatar(), R$drawable.fq_ic_placeholder_avatar);
        this.isShowing = true;
        return true;
    }

    public GiftAnimModel getGift() {
        return this.mGift;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1002) {
            this.mCombo++;
            initGiftCount(String.valueOf(this.mCombo));
            comboAnimation(false);
            removeDismissGiftCallback();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissGiftLayout() {
        removeDismissGiftCallback();
        LeftGiftAnimDismissListener leftGiftAnimDismissListener = this.mGiftAnimationListener;
        if (leftGiftAnimDismissListener != null) {
            leftGiftAnimDismissListener.dismiss(this);
        }
    }

    private void removeDismissGiftCallback() {
        stopCheckGiftCount();
        Runnable runnable = this.mCurrentAnimRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mCurrentAnimRunnable = null;
        }
    }

    public void setJumpCombo(int i) {
        this.mJumpCombo = i;
    }

    public void setDelete(boolean z) {
        this.isDelete = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.gift.GiftFrameLayout$GiftNumAnimRunnable */
    /* loaded from: classes3.dex */
    public class GiftNumAnimRunnable implements Runnable {
        private GiftNumAnimRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GiftFrameLayout.this.dismissGiftLayout();
        }
    }

    public void setIndex(int i) {
        this.mIndex = i;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setGiftAnimationListener(LeftGiftAnimDismissListener leftGiftAnimDismissListener) {
        this.mGiftAnimationListener = leftGiftAnimDismissListener;
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    public void setCurrentShowStatus(boolean z) {
        this.mCombo = 0;
        this.isShowing = z;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public void CurrentEndStatus(boolean z) {
        this.isEnd = z;
    }

    public String getCurrentSendUserId() {
        GiftAnimModel giftAnimModel = this.mGift;
        if (giftAnimModel != null) {
            return giftAnimModel.getSendUserId();
        }
        return null;
    }

    public String getCurrentGiftId() {
        GiftAnimModel giftAnimModel = this.mGift;
        if (giftAnimModel != null) {
            return giftAnimModel.getGiftId();
        }
        return null;
    }

    public synchronized void setGiftCount(int i) {
        this.mGiftCount += i;
        if (this.mGift != null) {
            this.mGift.setGiftCount(this.mGiftCount);
        }
    }

    public synchronized void setGiftAddCount(int i) {
        this.mGiftCount = i;
        if (this.mGift != null) {
            this.mGift.setGiftCount(this.mGiftCount);
        }
    }

    public int getGiftCount() {
        int i;
        synchronized (this) {
            i = this.mGiftCount;
        }
        return i;
    }

    public synchronized void setSendGiftTime(long j) {
        this.mGift.setSendGiftTime(Long.valueOf(j));
    }

    public long getSendGiftTime() {
        long longValue;
        synchronized (this) {
            longValue = this.mGift.getSendGiftTime().longValue();
        }
        return longValue;
    }

    public boolean isCurrentStart() {
        return this.mGift.isCurrentStart();
    }

    public void setCurrentStart(boolean z) {
        this.mGift.setCurrentStart(z);
    }

    public int getCombo() {
        return this.mCombo;
    }

    public int getJumpCombo() {
        return this.mJumpCombo;
    }

    private void checkGiftCountSubscribe() {
        this.runnable = new Runnable() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (GiftFrameLayout.this.mGiftCount > GiftFrameLayout.this.mCombo) {
                    GiftFrameLayout.this.mHandler.sendEmptyMessage(1002);
                }
                GiftFrameLayout.this.comboHandler.postDelayed(GiftFrameLayout.this.runnable, 200L);
            }
        };
        this.comboHandler.postDelayed(this.runnable, 200L);
    }

    private void stopCheckGiftCount() {
        this.comboHandler.removeCallbacksAndMessages(null);
    }

    public void clearHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        Handler handler2 = this.comboHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.comboHandler = null;
        }
        if (this.mGiftAnimationListener != null) {
            this.mGiftAnimationListener = null;
        }
        if (this.barrageEndAnimationListener != null) {
            this.barrageEndAnimationListener = null;
        }
        SVGAImageView sVGAImageView = this.ivSvgaImageView;
        if (sVGAImageView != null) {
            sVGAImageView.setCallback(null);
        }
        if (this.svgaParser != null) {
            this.svgaParser = null;
        }
        resetGift();
    }

    private void resetGift() {
        this.runnable = null;
        this.mCurrentAnimRunnable = null;
        this.mGift = null;
        this.mIndex = -1;
        this.mGiftCount = 0;
        this.mCombo = 0;
        this.mJumpCombo = 0;
        this.isShowing = false;
        this.isEnd = true;
        this.isHideMode = false;
    }

    public void initLayoutState() {
        if (this.mGift == null) {
            return;
        }
        setVisibility(0);
        this.isEnd = false;
        loadGiftImage();
    }

    public void comboEndAnim() {
        if (this.mHandler != null) {
            if (this.mGiftCount > this.mCombo) {
                this.mHandler.sendEmptyMessage(1002);
                return;
            }
            this.mCurrentAnimRunnable = new GiftNumAnimRunnable();
            Handler handler = this.mHandler;
            Runnable runnable = this.mCurrentAnimRunnable;
            GiftAnimModel giftAnimModel = this.mGift;
            handler.postDelayed(runnable, giftAnimModel == null ? 3000L : giftAnimModel.getGiftShowTime());
            checkGiftCountSubscribe();
        }
    }

    public void startAnimation(ICustomAnim iCustomAnim) {
        GiftAnimModel gift = getGift();
        gift.setGiftTotalCount(this.mCombo);
        BarrageEndAnimationListener barrageEndAnimationListener = this.barrageEndAnimationListener;
        if (barrageEndAnimationListener != null) {
            barrageEndAnimationListener.onStartAnimation(gift);
        }
        this.customAnim = iCustomAnim;
        if (iCustomAnim == null) {
            ObjectAnimator createFlyFromLtoR = GiftAnimationUtil.createFlyFromLtoR(this.rlBaseInfo, -getWidth(), 0.0f, LotteryDialog.MAX_VALUE, new OvershootInterpolator());
            createFlyFromLtoR.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    GiftFrameLayout.this.initLayoutState();
                }
            });
            ObjectAnimator createFlyFromLtoR2 = GiftAnimationUtil.createFlyFromLtoR(this.ivSvgaImageView, -getWidth(), 0.0f, LotteryDialog.MAX_VALUE, new DecelerateInterpolator());
            createFlyFromLtoR2.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    GiftFrameLayout.this.ivSvgaImageView.setVisibility(0);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GiftFrameLayout.this.comboAnimation(true);
                }
            });
            GiftAnimationUtil.startAnimation(createFlyFromLtoR, createFlyFromLtoR2);
            return;
        }
        iCustomAnim.startAnim(this, this.rootView);
    }

    public void comboAnimation(boolean z) {
        ICustomAnim iCustomAnim = this.customAnim;
        if (iCustomAnim != null) {
            iCustomAnim.comboAnim(this, this.rootView, z);
        } else if (z) {
            comboEndAnim();
        } else {
            ObjectAnimator scaleGiftNum = GiftAnimationUtil.scaleGiftNum(this.llGiftNumBg);
            scaleGiftNum.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GiftFrameLayout.this.comboEndAnim();
                }
            });
            scaleGiftNum.start();
        }
    }

    public AnimatorSet endAnimation(ICustomAnim iCustomAnim) {
        GiftAnimModel gift = getGift();
        gift.setGiftTotalCount(this.mCombo);
        BarrageEndAnimationListener barrageEndAnimationListener = this.barrageEndAnimationListener;
        if (barrageEndAnimationListener != null && !this.isDelete) {
            barrageEndAnimationListener.onEndAnimation(gift);
        }
        if (iCustomAnim == null) {
            ObjectAnimator createFadeAnimator = GiftAnimationUtil.createFadeAnimator(this, 0.0f, -80.0f, 300, 0);
            createFadeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                }
            });
            return GiftAnimationUtil.startAnimation(createFadeAnimator, GiftAnimationUtil.createFadeAnimator(this, 100.0f, 0.0f, 0, 0));
        }
        return iCustomAnim.endAnim(this, this.rootView);
    }

    private void startOnLineSVGAAnim(String str) {
        SVGAImageView sVGAImageView;
        if (!TextUtils.isEmpty(str) && (sVGAImageView = this.ivSvgaImageView) != null && !sVGAImageView.isAnimating()) {
            try {
                if (this.svgaParser == null) {
                    this.svgaParser = new SVGAParser(this.mContext);
                }
                this.svgaParser.decodeFromURL(new URL(GlideUtils.formatDownUrl(str)), new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.6
                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onError() {
                    }

                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                        GiftFrameLayout.this.ivSvgaImageView.setVisibility(0);
                        GiftFrameLayout.this.ivSvgaImageView.setVideoItem(sVGAVideoEntity);
                        GiftFrameLayout.this.ivSvgaImageView.startAnimation();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startSVGAAnim(final String str) {
        SVGAImageView sVGAImageView;
        if (!TextUtils.isEmpty(str) && (sVGAImageView = this.ivSvgaImageView) != null && !sVGAImageView.isAnimating()) {
            try {
                Observable.just(true).map(new Function<Boolean, InputStream>() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.8
                    @Override // io.reactivex.functions.Function
                    /* renamed from: apply  reason: avoid collision after fix types in other method */
                    public InputStream mo6755apply(Boolean bool) throws Exception {
                        return GiftFrameLayout.this.getSVGAFileInputStream(str);
                    }
                }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<InputStream>() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.7
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(InputStream inputStream) {
                        if (inputStream == null) {
                            return;
                        }
                        if (GiftFrameLayout.this.svgaParser == null) {
                            GiftFrameLayout giftFrameLayout = GiftFrameLayout.this;
                            giftFrameLayout.svgaParser = new SVGAParser(giftFrameLayout.mContext);
                        }
                        GiftFrameLayout.this.svgaParser.decodeFromInputStream(inputStream, str, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.gift.GiftFrameLayout.7.1
                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onError() {
                            }

                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                                GiftFrameLayout.this.ivSvgaImageView.setVisibility(0);
                                GiftFrameLayout.this.ivSvgaImageView.setVideoItem(sVGAVideoEntity);
                                GiftFrameLayout.this.ivSvgaImageView.startAnimation();
                            }
                        }, true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputStream getSVGAFileInputStream(String str) throws Exception {
        File file = new File(AppUtils.getLocalGiftFilePath(str));
        if (!file.exists()) {
            return null;
        }
        return new FileInputStream(file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadGiftImage() {
        if (this.mGift == null) {
            return;
        }
        GlideUtils.loadImage(getContext(), this.ivSvgaImageView, this.mGift.getGiftPic(), ConvertUtils.dp2px(35.0f), ConvertUtils.dp2px(35.0f));
    }

    public void initGiftCount(String str) {
        this.llGiftNum.setVisibility(8);
        int childCount = this.llGiftNum.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.llGiftNum.getChildAt(i).setVisibility(8);
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            int parseInt = Integer.parseInt(String.valueOf(str.charAt(i2)));
            ImageView imageView = (ImageView) this.llGiftNum.getChildAt(i2);
            imageView.setVisibility(0);
            if (imageView.getTag() == null || ((Integer) imageView.getTag()).intValue() != parseInt) {
                switch (parseInt) {
                    case 0:
                        imageView.setImageResource(this.giftNumbers[0]);
                        continue;
                    case 1:
                        imageView.setImageResource(this.giftNumbers[1]);
                        continue;
                    case 2:
                        imageView.setImageResource(this.giftNumbers[2]);
                        continue;
                    case 3:
                        imageView.setImageResource(this.giftNumbers[3]);
                        continue;
                    case 4:
                        imageView.setImageResource(this.giftNumbers[4]);
                        continue;
                    case 5:
                        imageView.setImageResource(this.giftNumbers[5]);
                        continue;
                    case 6:
                        imageView.setImageResource(this.giftNumbers[6]);
                        continue;
                    case 7:
                        imageView.setImageResource(this.giftNumbers[7]);
                        continue;
                    case 8:
                        imageView.setImageResource(this.giftNumbers[8]);
                        continue;
                    case 9:
                        imageView.setImageResource(this.giftNumbers[9]);
                        continue;
                    default:
                        imageView.setTag(Integer.valueOf(parseInt));
                        continue;
                }
            }
        }
        this.llGiftNum.setVisibility(0);
    }

    public void initLuckyGiftBatchCount(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.llBatchNum.setVisibility(8);
        int childCount = this.llBatchNum.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.llBatchNum.getChildAt(i).setVisibility(8);
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            int parseInt = Integer.parseInt(String.valueOf(str.charAt(i2)));
            ImageView imageView = (ImageView) this.llBatchNum.getChildAt(i2);
            imageView.setVisibility(0);
            if (imageView.getTag() == null || ((Integer) imageView.getTag()).intValue() != parseInt) {
                switch (parseInt) {
                    case 0:
                        imageView.setImageResource(this.giftNumbers[0]);
                        continue;
                    case 1:
                        imageView.setImageResource(this.giftNumbers[1]);
                        continue;
                    case 2:
                        imageView.setImageResource(this.giftNumbers[2]);
                        continue;
                    case 3:
                        imageView.setImageResource(this.giftNumbers[3]);
                        continue;
                    case 4:
                        imageView.setImageResource(this.giftNumbers[4]);
                        continue;
                    case 5:
                        imageView.setImageResource(this.giftNumbers[5]);
                        continue;
                    case 6:
                        imageView.setImageResource(this.giftNumbers[6]);
                        continue;
                    case 7:
                        imageView.setImageResource(this.giftNumbers[7]);
                        continue;
                    case 8:
                        imageView.setImageResource(this.giftNumbers[8]);
                        continue;
                    case 9:
                        imageView.setImageResource(this.giftNumbers[9]);
                        continue;
                    default:
                        imageView.setTag(Integer.valueOf(parseInt));
                        continue;
                }
            }
        }
        this.llBatchNum.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.gift.GiftFrameLayout$SVGAAnimationListener */
    /* loaded from: classes3.dex */
    public class SVGAAnimationListener implements SVGACallback {
        @Override // com.opensource.svgaplayer.SVGACallback
        public void onPause() {
        }

        @Override // com.opensource.svgaplayer.SVGACallback
        public void onRepeat() {
        }

        @Override // com.opensource.svgaplayer.SVGACallback
        public void onStep(int i, double d) {
        }

        private SVGAAnimationListener() {
        }

        @Override // com.opensource.svgaplayer.SVGACallback
        public void onFinished() {
            if (GiftFrameLayout.this.mGift == null) {
                return;
            }
            GiftFrameLayout.this.loadGiftImage();
        }
    }

    public void onRelease() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        Handler handler2 = this.comboHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.comboHandler = null;
        }
        if (this.mGiftAnimationListener != null) {
            this.mGiftAnimationListener = null;
        }
        if (this.barrageEndAnimationListener != null) {
            this.barrageEndAnimationListener = null;
        }
        SVGAImageView sVGAImageView = this.ivSvgaImageView;
        if (sVGAImageView != null) {
            sVGAImageView.setCallback(null);
        }
        if (this.svgaParser != null) {
            this.svgaParser = null;
        }
    }
}
