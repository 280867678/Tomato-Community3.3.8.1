package com.opensource.svgaplayer;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.utils.SVGARange;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SVGAImageView.kt */
/* loaded from: classes3.dex */
public class SVGAImageView extends ImageView {
    private ValueAnimator animator;
    private SVGACallback callback;
    private boolean clearsAfterStop = true;
    private FillMode fillMode = FillMode.Forward;
    private boolean isAnimating;
    private int loops;
    private SVGAClickAreaListener mItemClickAreaListener;
    private SVGAVideoEntity mVideoItem;

    /* compiled from: SVGAImageView.kt */
    /* loaded from: classes3.dex */
    public enum FillMode {
        Backward,
        Forward
    }

    private final void setAnimating(boolean z) {
        this.isAnimating = z;
    }

    public final boolean isAnimating() {
        return this.isAnimating;
    }

    public final int getLoops() {
        return this.loops;
    }

    public final void setLoops(int i) {
        this.loops = i;
    }

    public final boolean getClearsAfterStop() {
        return this.clearsAfterStop;
    }

    public final void setClearsAfterStop(boolean z) {
        this.clearsAfterStop = z;
    }

    public final FillMode getFillMode() {
        return this.fillMode;
    }

    public final void setFillMode(FillMode fillMode) {
        Intrinsics.checkParameterIsNotNull(fillMode, "<set-?>");
        this.fillMode = fillMode;
    }

    public final SVGACallback getCallback() {
        return this.callback;
    }

    public final void setCallback(SVGACallback sVGACallback) {
        this.callback = sVGACallback;
    }

    public SVGAImageView(Context context) {
        super(context);
        setSoftwareLayerType();
    }

    public SVGAImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setSoftwareLayerType();
        if (attributeSet != null) {
            loadAttrs(attributeSet);
        }
    }

    public SVGAImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setSoftwareLayerType();
        if (attributeSet != null) {
            loadAttrs(attributeSet);
        }
    }

    public SVGAImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSoftwareLayerType();
        if (attributeSet != null) {
            loadAttrs(attributeSet);
        }
    }

    private final void setSoftwareLayerType() {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(1, null);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearAudio();
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.animator;
        if (valueAnimator2 != null) {
            valueAnimator2.removeAllListeners();
        }
        ValueAnimator valueAnimator3 = this.animator;
        if (valueAnimator3 != null) {
            valueAnimator3.removeAllUpdateListeners();
        }
    }

    private final void clearAudio() {
        List<SVGAAudioEntity> audios$library_release;
        SoundPool soundPool$library_release;
        SVGAVideoEntity sVGAVideoEntity = this.mVideoItem;
        if (sVGAVideoEntity == null || (audios$library_release = sVGAVideoEntity.getAudios$library_release()) == null) {
            return;
        }
        for (SVGAAudioEntity sVGAAudioEntity : audios$library_release) {
            Integer playID = sVGAAudioEntity.getPlayID();
            if (playID != null) {
                int intValue = playID.intValue();
                SVGAVideoEntity sVGAVideoEntity2 = this.mVideoItem;
                if (sVGAVideoEntity2 != null && (soundPool$library_release = sVGAVideoEntity2.getSoundPool$library_release()) != null) {
                    soundPool$library_release.stop(intValue);
                }
            }
            sVGAAudioEntity.setPlayID(null);
        }
    }

    private final void loadAttrs(AttributeSet attributeSet) {
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.SVGAImageView, 0, 0);
        this.loops = obtainStyledAttributes.getInt(R$styleable.SVGAImageView_loopCount, 0);
        this.clearsAfterStop = obtainStyledAttributes.getBoolean(R$styleable.SVGAImageView_clearsAfterStop, true);
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.SVGAImageView_antiAlias, true);
        boolean z2 = obtainStyledAttributes.getBoolean(R$styleable.SVGAImageView_autoPlay, true);
        String string = obtainStyledAttributes.getString(R$styleable.SVGAImageView_fillMode);
        if (string != null) {
            if (Intrinsics.areEqual(string, "0")) {
                this.fillMode = FillMode.Backward;
            } else if (Intrinsics.areEqual(string, "1")) {
                this.fillMode = FillMode.Forward;
            }
        }
        String string2 = obtainStyledAttributes.getString(R$styleable.SVGAImageView_source);
        if (string2 != null) {
            new Thread(new SVGAImageView$loadAttrs$$inlined$let$lambda$1(string2, new SVGAParser(getContext()), this, z, z2)).start();
        }
        obtainStyledAttributes.recycle();
    }

    public final void startAnimation() {
        startAnimation(null, false);
    }

    public final void startAnimation(final SVGARange sVGARange, final boolean z) {
        Field declaredField;
        stopAnimation(false);
        Drawable drawable = getDrawable();
        if (!(drawable instanceof SVGADrawable)) {
            drawable = null;
        }
        final SVGADrawable sVGADrawable = (SVGADrawable) drawable;
        if (sVGADrawable != null) {
            sVGADrawable.setCleared$library_release(false);
            ImageView.ScaleType scaleType = getScaleType();
            Intrinsics.checkExpressionValueIsNotNull(scaleType, "scaleType");
            sVGADrawable.setScaleType(scaleType);
            SVGAVideoEntity videoItem = sVGADrawable.getVideoItem();
            if (sVGARange == null) {
                final int max = Math.max(0, 0);
                int frames = videoItem.getFrames() - 1;
                if (sVGARange != null) {
                    sVGARange.getLocation();
                    throw null;
                } else if (sVGARange != null) {
                    sVGARange.getLength();
                    throw null;
                } else {
                    final int min = Math.min(frames, (Integer.MAX_VALUE + 0) - 1);
                    final ValueAnimator animator = ValueAnimator.ofInt(max, min);
                    double d = 1.0d;
                    try {
                        Class<?> cls = Class.forName("android.animation.ValueAnimator");
                        if (cls != null && (declaredField = cls.getDeclaredField("sDurationScale")) != null) {
                            declaredField.setAccessible(true);
                            double d2 = declaredField.getFloat(cls);
                            if (d2 == 0.0d) {
                                try {
                                    declaredField.setFloat(cls, 1.0f);
                                    Log.e("SVGAPlayer", "The animation duration scale has been reset to 1.0x, because you closed it on developer options.");
                                } catch (Exception unused) {
                                }
                            }
                            d = d2;
                        }
                    } catch (Exception unused2) {
                    }
                    Intrinsics.checkExpressionValueIsNotNull(animator, "animator");
                    animator.setInterpolator(new LinearInterpolator());
                    animator.setDuration((long) ((((min - max) + 1) * (1000 / videoItem.getFPS())) / d));
                    int i = this.loops;
                    animator.setRepeatCount(i <= 0 ? 99999 : i - 1);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(animator, this, sVGARange, sVGADrawable, z) { // from class: com.opensource.svgaplayer.SVGAImageView$startAnimation$$inlined$let$lambda$1
                        final /* synthetic */ ValueAnimator $animator;
                        final /* synthetic */ SVGADrawable $drawable$inlined;
                        final /* synthetic */ SVGAImageView this$0;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        {
                            this.$drawable$inlined = sVGADrawable;
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            SVGADrawable sVGADrawable2 = this.$drawable$inlined;
                            ValueAnimator animator2 = this.$animator;
                            Intrinsics.checkExpressionValueIsNotNull(animator2, "animator");
                            Object animatedValue = animator2.getAnimatedValue();
                            if (animatedValue == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                            }
                            sVGADrawable2.setCurrentFrame$library_release(((Integer) animatedValue).intValue());
                            SVGACallback callback = this.this$0.getCallback();
                            if (callback == null) {
                                return;
                            }
                            callback.onStep(this.$drawable$inlined.getCurrentFrame(), (this.$drawable$inlined.getCurrentFrame() + 1) / this.$drawable$inlined.getVideoItem().getFrames());
                        }
                    });
                    animator.addListener(new Animator.AnimatorListener(max, min, this, sVGARange, sVGADrawable, z) { // from class: com.opensource.svgaplayer.SVGAImageView$startAnimation$$inlined$let$lambda$2
                        final /* synthetic */ SVGADrawable $drawable$inlined;
                        final /* synthetic */ int $endFrame;
                        final /* synthetic */ int $startFrame;
                        final /* synthetic */ SVGAImageView this$0;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        {
                            this.$drawable$inlined = sVGADrawable;
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator2) {
                            SVGACallback callback = this.this$0.getCallback();
                            if (callback != null) {
                                callback.onRepeat();
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator2) {
                            this.this$0.isAnimating = false;
                            this.this$0.stopAnimation();
                            if (!this.this$0.getClearsAfterStop()) {
                                if (this.this$0.getFillMode() == SVGAImageView.FillMode.Backward) {
                                    this.$drawable$inlined.setCurrentFrame$library_release(this.$startFrame);
                                } else if (this.this$0.getFillMode() == SVGAImageView.FillMode.Forward) {
                                    this.$drawable$inlined.setCurrentFrame$library_release(this.$endFrame);
                                }
                            }
                            SVGACallback callback = this.this$0.getCallback();
                            if (callback != null) {
                                callback.onFinished();
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator2) {
                            this.this$0.isAnimating = false;
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator2) {
                            this.this$0.isAnimating = true;
                        }
                    });
                    if (z) {
                        animator.reverse();
                    } else {
                        animator.start();
                    }
                    this.animator = animator;
                    return;
                }
            }
            sVGARange.getLocation();
            throw null;
        }
    }

    public final void pauseAnimation() {
        stopAnimation(false);
        SVGACallback sVGACallback = this.callback;
        if (sVGACallback != null) {
            sVGACallback.onPause();
        }
    }

    public final void stopAnimation() {
        stopAnimation(this.clearsAfterStop);
    }

    public final void stopAnimation(boolean z) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.animator;
        if (valueAnimator2 != null) {
            valueAnimator2.removeAllListeners();
        }
        ValueAnimator valueAnimator3 = this.animator;
        if (valueAnimator3 != null) {
            valueAnimator3.removeAllUpdateListeners();
        }
        Drawable drawable = getDrawable();
        if (!(drawable instanceof SVGADrawable)) {
            drawable = null;
        }
        SVGADrawable sVGADrawable = (SVGADrawable) drawable;
        if (sVGADrawable != null) {
            sVGADrawable.setCleared$library_release(z);
        }
    }

    public final void setVideoItem(SVGAVideoEntity sVGAVideoEntity) {
        setVideoItem(sVGAVideoEntity, new SVGADynamicEntity());
    }

    public final void setVideoItem(SVGAVideoEntity sVGAVideoEntity, SVGADynamicEntity sVGADynamicEntity) {
        if (sVGAVideoEntity == null) {
            setImageDrawable(null);
            return;
        }
        if (sVGADynamicEntity == null) {
            sVGADynamicEntity = new SVGADynamicEntity();
        }
        SVGADrawable sVGADrawable = new SVGADrawable(sVGAVideoEntity, sVGADynamicEntity);
        sVGADrawable.setCleared$library_release(this.clearsAfterStop);
        setImageDrawable(sVGADrawable);
        this.mVideoItem = sVGAVideoEntity;
    }

    public final void setOnAnimKeyClickListener(SVGAClickAreaListener clickListener) {
        Intrinsics.checkParameterIsNotNull(clickListener, "clickListener");
        this.mItemClickAreaListener = clickListener;
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SVGAClickAreaListener sVGAClickAreaListener;
        if (motionEvent != null && motionEvent.getAction() == 0) {
            Drawable drawable = getDrawable();
            if (!(drawable instanceof SVGADrawable)) {
                drawable = null;
            }
            SVGADrawable sVGADrawable = (SVGADrawable) drawable;
            if (sVGADrawable == null) {
                return false;
            }
            for (Map.Entry<String, int[]> entry : sVGADrawable.getDynamicItem().getMClickMap$library_release().entrySet()) {
                String key = entry.getKey();
                int[] value = entry.getValue();
                if (motionEvent.getX() >= value[0] && motionEvent.getX() <= value[2] && motionEvent.getY() >= value[1] && motionEvent.getY() <= value[3] && (sVGAClickAreaListener = this.mItemClickAreaListener) != null) {
                    sVGAClickAreaListener.onClick(key);
                    return true;
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }
}
