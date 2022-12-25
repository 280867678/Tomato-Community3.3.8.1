package com.tomatolive.library.p136ui.view.custom.luckpan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView */
/* loaded from: classes3.dex */
public class LuckPanView extends FrameLayout {
    private ArcProgress arcProgress;
    private List<Integer> codeList;
    private float currAngle;
    private ObjectAnimator fakeAnim;
    private boolean isFirst;
    private boolean isWorking;
    private ImageView ivRed;
    private ImageView ivStart;
    private ImageView ivWord;
    private int lastPosition;
    private float mAngle;
    private int mMinTimes;
    private int mTypeNum;
    private WheelSurfPanView mWheelSurfPanView;
    private RotateListener rotateListener;

    public boolean isAniming() {
        return this.isWorking;
    }

    public LuckPanView(@NonNull Context context) {
        this(context, null);
    }

    public LuckPanView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LuckPanView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currAngle = 0.0f;
        this.lastPosition = 0;
        this.mTypeNum = 9;
        this.mMinTimes = 5;
        this.isFirst = true;
        init(context);
    }

    public void setRotateListener(RotateListener rotateListener) {
        this.mWheelSurfPanView.setRotateListener(rotateListener);
        this.rotateListener = rotateListener;
    }

    private void init(Context context) {
        FrameLayout.inflate(context, R$layout.fq_luck_pan_view, this);
        this.mWheelSurfPanView = (WheelSurfPanView) findViewById(R$id.pan_view);
        this.arcProgress = (ArcProgress) findViewById(R$id.arc_progress);
        this.ivStart = (ImageView) findViewById(R$id.iv_start);
        this.arcProgress.setMax(LotteryDialog.MAX_VALUE);
        this.ivRed = (ImageView) findViewById(R$id.iv_red);
        this.ivWord = (ImageView) findViewById(R$id.iv_text);
        this.ivStart.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.luckpan.-$$Lambda$LuckPanView$SYGAYVdHVZubRWO4Yvy-wojzMOU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LuckPanView.this.lambda$init$0$LuckPanView(view);
            }
        });
        this.mAngle = (float) (360.0d / this.mTypeNum);
    }

    public /* synthetic */ void lambda$init$0$LuckPanView(View view) {
        RotateListener rotateListener;
        if (this.isWorking || (rotateListener = this.rotateListener) == null) {
            return;
        }
        rotateListener.rotateBefore();
    }

    public void startClickLottery(int i) {
        this.mWheelSurfPanView.startLight();
        this.ivWord.setImageResource(R$drawable.fq_ic_lottery_turntable_waiting_lottery_word);
        startRotateStart(getPrizePosition(this.codeList, i));
    }

    public void fakeStart() {
        this.fakeAnim = ObjectAnimator.ofFloat(this.ivStart, "rotation", this.currAngle, 3600000);
        this.fakeAnim.setDuration(40000L);
        this.fakeAnim.setInterpolator(new LinearInterpolator());
        this.fakeAnim.start();
        this.fakeAnim.addListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator, boolean z) {
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
            }
        });
    }

    public void onErrorDeal() {
        this.fakeAnim.cancel();
        this.fakeAnim.removeAllListeners();
        this.currAngle = 0.0f;
        this.lastPosition = 0;
        this.ivStart.setRotation(0.0f);
    }

    public void startRotateStart(final int i) {
        float f = this.mAngle;
        float f2 = (this.mMinTimes * 360) + (i * f);
        float f3 = this.currAngle;
        float f4 = (int) ((f2 + f3) - (this.lastPosition * f));
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.ivStart, "rotation", f3, f4);
        this.currAngle = f4;
        this.lastPosition = i;
        ofFloat.setDuration(4000L);
        ofFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                LuckPanView.this.post(new Runnable() { // from class: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LuckPanView.this.isWorking = false;
                        LuckPanView.this.mWheelSurfPanView.stopLight();
                        LuckPanView.this.ivWord.setImageResource(R$drawable.fq_ic_lottery_turntable_start_lottery_word);
                        if (LuckPanView.this.rotateListener != null) {
                            LuckPanView.this.rotateListener.rotateEnd(i, "");
                        }
                    }
                });
            }
        });
        ofFloat.start();
        this.isWorking = true;
    }

    public void onReset() {
        this.currAngle = 0.0f;
        this.lastPosition = 0;
        this.ivStart.setRotation(0.0f);
        setProgress(0.0f);
    }

    public void onRelease() {
        WheelSurfPanView wheelSurfPanView = this.mWheelSurfPanView;
        if (wheelSurfPanView != null) {
            wheelSurfPanView.release();
        }
        if (this.rotateListener != null) {
            this.rotateListener = null;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(FrameLayout.getDefaultSize(0, i), FrameLayout.getDefaultSize(0, i2));
        int measuredHeight = getMeasuredHeight();
        if (this.isFirst) {
            this.isFirst = false;
            float f = measuredHeight;
            reMeasure(this.ivStart, f);
            reMeasure(this.ivRed, f);
            reMeasure(this.ivWord, f);
        }
        super.onMeasure(i, i2);
    }

    private void reMeasure(final View view, final float f) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            @TargetApi(19)
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float f2 = f;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = (int) (f2 * 0.25d);
                layoutParams.height = (int) (((f2 * 0.25d) * view.getMeasuredHeight()) / view.getMeasuredWidth());
                view.setLayoutParams(layoutParams);
            }
        });
    }

    public void setProgress(float f) {
        if (f > 400.0f) {
            f = 400.0f;
        }
        this.arcProgress.setProgress(f);
        if (f == 0.0f) {
            this.mWheelSurfPanView.startBoom(false);
            this.ivStart.setImageResource(R$drawable.fq_ic_lottery_turntable_centre_begin);
            this.ivRed.setImageResource(R$drawable.fq_ic_lottery_turntable_centre_red);
        }
    }

    public void startBoom() {
        this.mWheelSurfPanView.startBoom(true);
        this.ivStart.setImageResource(R$drawable.fq_ic_lottery_turntable_centre_begin_boom);
        this.ivRed.setImageResource(R$drawable.fq_ic_lottery_turntable_centre_red_boom);
    }

    private int getPrizePosition(List<Integer> list, int i) {
        return list.indexOf(Integer.valueOf(i));
    }

    public void setConfig(Builder builder) {
        this.codeList = builder.codeList;
        if (builder.mColors != null) {
            this.mWheelSurfPanView.setmColors(builder.mColors);
        }
        if (builder.mNum != null) {
            this.mWheelSurfPanView.setNum(builder.mNum);
        }
        if (builder.mNames != null) {
            this.mWheelSurfPanView.setName(builder.mNames);
        }
        if (builder.mHuanImgRes.intValue() != 0) {
            this.mWheelSurfPanView.setmHuanImgRes(builder.mHuanImgRes);
        }
        if (builder.mIcons != null) {
            this.mWheelSurfPanView.setmIcons(builder.mIcons);
        }
        if (builder.mHuanImgRes.intValue() != 0) {
            this.mWheelSurfPanView.setmHuanImgRes(builder.mHuanImgRes);
        }
        if (builder.mTypeNum != 0) {
            this.mWheelSurfPanView.setmTypeNum(builder.mTypeNum);
        }
        this.mWheelSurfPanView.setBoomStatus(getPrizePosition(builder.codeList, builder.boomStatus));
        this.mWheelSurfPanView.setBoomMultiple(builder.boomMultiple);
        this.mWheelSurfPanView.show();
    }

    /* renamed from: com.tomatolive.library.ui.view.custom.luckpan.LuckPanView$Builder */
    /* loaded from: classes3.dex */
    public static final class Builder {
        private int boomMultiple;
        private List<Integer> codeList;
        private Integer[] mColors;
        private List<Bitmap> mIcons;
        private String[] mNames;
        private String[] mNum;
        private int mTypeNum = 0;
        private Integer mHuanImgRes = 0;
        private int boomStatus = -1;

        public final Builder build() {
            return this;
        }

        public final Builder setmTypeNum(int i) {
            this.mTypeNum = i;
            return this;
        }

        public final Builder setmNum(String[] strArr) {
            this.mNum = strArr;
            return this;
        }

        public final Builder setmName(String[] strArr) {
            this.mNames = strArr;
            return this;
        }

        public final Builder setmIcons(List<Bitmap> list) {
            this.mIcons = list;
            return this;
        }

        public final Builder setmColors(Integer[] numArr) {
            this.mColors = numArr;
            return this;
        }

        public final Builder setCodeList(List<Integer> list) {
            this.codeList = list;
            return this;
        }

        public Builder setBoomStatus(int i) {
            this.boomStatus = i;
            return this;
        }

        public Builder setBoomMultiple(int i) {
            this.boomMultiple = i;
            return this;
        }
    }
}
