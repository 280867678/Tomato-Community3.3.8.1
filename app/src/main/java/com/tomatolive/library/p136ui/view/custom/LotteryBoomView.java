package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$anim;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.LotteryBoomDetailEntity;
import com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimationListener;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.LotteryBoomView */
/* loaded from: classes3.dex */
public class LotteryBoomView extends RelativeLayout {
    private ImageView ivBoomIcon;
    private Disposable mDisposable;
    private OnLotteryBoomCallback onLotteryBoomCallback;
    private TextView tvBoomName;
    private TextView tvBoomNum;
    private TextView tvCountDown;
    private long maxSeconds = 10;
    private boolean isFirstShowAnim = true;

    public LotteryBoomView(Context context) {
        super(context);
        initView(context);
    }

    public LotteryBoomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_lottery_boom_view, this);
        this.ivBoomIcon = (ImageView) findViewById(R$id.iv_boom_icon);
        this.tvBoomName = (TextView) findViewById(R$id.tv_boom_name);
        this.tvCountDown = (TextView) findViewById(R$id.tv_countdown);
        this.tvBoomNum = (TextView) findViewById(R$id.tv_boom_num);
    }

    public void initData(LotteryBoomDetailEntity lotteryBoomDetailEntity) {
        if (lotteryBoomDetailEntity == null) {
            return;
        }
        this.maxSeconds = NumberUtils.string2long(String.valueOf(lotteryBoomDetailEntity.boomRemainTime), 180L) + 1;
        GlideUtils.loadImage(getContext(), this.ivBoomIcon, lotteryBoomDetailEntity.boomPropUrl);
        this.tvBoomNum.setText(lotteryBoomDetailEntity.getBoomMultipleStr());
    }

    public void showBoomCountDown() {
        onRelease();
        setCountDownText(this.maxSeconds);
        this.mDisposable = Flowable.intervalRange(0L, this.maxSeconds + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.LotteryBoomView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                LotteryBoomView lotteryBoomView = LotteryBoomView.this;
                lotteryBoomView.setCountDownText(lotteryBoomView.maxSeconds - l.longValue());
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.LotteryBoomView.1
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                LotteryBoomView.this.isFirstShowAnim = true;
                if (LotteryBoomView.this.getVisibility() == 0) {
                    Animation loadAnimation = AnimationUtils.loadAnimation(LotteryBoomView.this.getContext(), R$anim.anim_jump_boom_out);
                    LotteryBoomView.this.startAnimation(loadAnimation);
                    loadAnimation.setAnimationListener(new SimpleAnimationListener() { // from class: com.tomatolive.library.ui.view.custom.LotteryBoomView.1.1
                        @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            LotteryBoomView.this.setVisibility(4);
                        }
                    });
                }
                if (LotteryBoomView.this.onLotteryBoomCallback != null) {
                    LotteryBoomView.this.onLotteryBoomCallback.onBoomCountDownEnd();
                }
            }
        }).subscribe();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i == 0 && this.isFirstShowAnim) {
            this.isFirstShowAnim = false;
            startAnimation(AnimationUtils.loadAnimation(getContext(), R$anim.anim_jump_boom_in));
        }
        super.setVisibility(i);
    }

    public void onRelease() {
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    public OnLotteryBoomCallback getOnLotteryBoomCallback() {
        return this.onLotteryBoomCallback;
    }

    public void setOnLotteryBoomCallback(OnLotteryBoomCallback onLotteryBoomCallback) {
        this.onLotteryBoomCallback = onLotteryBoomCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCountDownText(long j) {
        this.tvCountDown.setText(DateUtils.secondToString(j));
    }
}
