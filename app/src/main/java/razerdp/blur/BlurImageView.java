package razerdp.blur;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import razerdp.blur.thread.ThreadPoolManager;
import razerdp.util.log.PopupLog;

/* loaded from: classes4.dex */
public class BlurImageView extends ImageView {
    private volatile boolean abortBlur;
    private AtomicBoolean blurFinish;
    private volatile boolean isAnimating;
    private boolean isAttachedToWindow;
    private CacheAction mAttachedCache;
    private WeakReference<PopupBlurOption> mBlurOption;
    private CacheAction mCacheAction;
    private long startDuration;

    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BlurImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.abortBlur = false;
        this.blurFinish = new AtomicBoolean(false);
        this.isAnimating = false;
        this.isAttachedToWindow = false;
        init();
    }

    private void init() {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setScaleType(ImageView.ScaleType.MATRIX);
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(null);
        } else {
            setBackgroundDrawable(null);
        }
    }

    public void applyBlurOption(PopupBlurOption popupBlurOption) {
        applyBlurOption(popupBlurOption, false);
    }

    private void applyBlurOption(PopupBlurOption popupBlurOption, boolean z) {
        if (popupBlurOption == null) {
            return;
        }
        this.mBlurOption = new WeakReference<>(popupBlurOption);
        View blurView = popupBlurOption.getBlurView();
        if (blurView == null) {
            PopupLog.m27e("BlurImageView", "模糊锚点View为空，放弃模糊操作...");
            destroy();
        } else if (popupBlurOption.isBlurAsync() && !z) {
            PopupLog.m25i("BlurImageView", "子线程blur");
            startBlurTask(blurView);
        } else {
            try {
                PopupLog.m25i("BlurImageView", "主线程blur");
                if (!BlurHelper.renderScriptSupported()) {
                    PopupLog.m27e("BlurImageView", "不支持脚本模糊。。。最低支持api 17(Android 4.2.2)，将采用fastblur");
                }
                setImageBitmapOnUiThread(BlurHelper.blur(getContext(), blurView, popupBlurOption.getBlurPreScaleRatio(), popupBlurOption.getBlurRadius(), popupBlurOption.isFullScreen()), z);
            } catch (Exception e) {
                PopupLog.m27e("BlurImageView", "模糊异常", e);
                e.printStackTrace();
                destroy();
            }
        }
    }

    PopupBlurOption getOption() {
        WeakReference<PopupBlurOption> weakReference = this.mBlurOption;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.abortBlur = true;
    }

    public void update() {
        if (getOption() != null) {
            applyBlurOption(getOption(), true);
        }
    }

    public void start(long j) {
        this.startDuration = j;
        if (!this.blurFinish.get()) {
            if (this.mCacheAction != null) {
                return;
            }
            this.mCacheAction = new CacheAction(new Runnable() { // from class: razerdp.blur.BlurImageView.1
                @Override // java.lang.Runnable
                public void run() {
                    BlurImageView blurImageView = BlurImageView.this;
                    blurImageView.start(blurImageView.startDuration);
                }
            }, 0L);
            PopupLog.m27e("BlurImageView", "缓存模糊动画，等待模糊完成");
            return;
        }
        CacheAction cacheAction = this.mCacheAction;
        if (cacheAction != null) {
            cacheAction.destroy();
            this.mCacheAction = null;
        }
        if (this.isAnimating) {
            return;
        }
        PopupLog.m25i("BlurImageView", "开始模糊alpha动画");
        this.isAnimating = true;
        if (j > 0) {
            startAlphaInAnimation(j);
        } else if (j == -2) {
            startAlphaInAnimation(getOption() == null ? 500L : getOption().getBlurInDuration());
        } else {
            setImageAlpha(255);
        }
    }

    private void startAlphaInAnimation(long j) {
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        ofInt.setDuration(j);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: razerdp.blur.BlurImageView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BlurImageView.this.isAnimating = false;
            }
        });
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: razerdp.blur.BlurImageView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BlurImageView.this.setImageAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        ofInt.start();
    }

    public void dismiss(long j) {
        this.isAnimating = false;
        PopupLog.m25i("BlurImageView", "dismiss模糊imageview alpha动画");
        if (j > 0) {
            startAlphaOutAnimation(j);
        } else if (j == -2) {
            startAlphaOutAnimation(getOption() == null ? 500L : getOption().getBlurOutDuration());
        } else {
            setImageAlpha(0);
        }
    }

    private void startAlphaOutAnimation(long j) {
        ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
        ofInt.setDuration(j);
        ofInt.setInterpolator(new AccelerateInterpolator());
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: razerdp.blur.BlurImageView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BlurImageView.this.isAnimating = false;
            }
        });
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: razerdp.blur.BlurImageView.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BlurImageView.this.setImageAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        ofInt.start();
    }

    private void startBlurTask(View view) {
        ThreadPoolManager.execute(new CreateBlurBitmapRunnable(view));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageBitmapOnUiThread(final Bitmap bitmap, final boolean z) {
        if (isUiThread()) {
            handleSetImageBitmap(bitmap, z);
        } else if (!this.isAttachedToWindow) {
            this.mAttachedCache = new CacheAction(new Runnable() { // from class: razerdp.blur.BlurImageView.6
                @Override // java.lang.Runnable
                public void run() {
                    BlurImageView.this.handleSetImageBitmap(bitmap, z);
                }
            }, 0L);
        } else {
            post(new Runnable() { // from class: razerdp.blur.BlurImageView.7
                @Override // java.lang.Runnable
                public void run() {
                    BlurImageView.this.handleSetImageBitmap(bitmap, z);
                }
            });
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;
        CacheAction cacheAction = this.mAttachedCache;
        if (cacheAction != null) {
            cacheAction.forceRestore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetImageBitmap(Bitmap bitmap, boolean z) {
        if (bitmap != null) {
            PopupLog.m26i("bitmap: 【" + bitmap.getWidth() + "," + bitmap.getHeight() + "】");
        }
        setImageAlpha(z ? 255 : 0);
        setImageBitmap(bitmap);
        PopupBlurOption option = getOption();
        if (option != null && !option.isFullScreen()) {
            View blurView = option.getBlurView();
            if (blurView == null) {
                return;
            }
            Rect rect = new Rect();
            blurView.getGlobalVisibleRect(rect);
            Matrix imageMatrix = getImageMatrix();
            imageMatrix.setTranslate(rect.left, rect.top);
            setImageMatrix(imageMatrix);
        }
        this.blurFinish.compareAndSet(false, true);
        PopupLog.m25i("BlurImageView", "设置成功：" + this.blurFinish.get());
        if (this.mCacheAction != null) {
            PopupLog.m25i("BlurImageView", "恢复缓存动画");
            this.mCacheAction.restore();
        }
        CacheAction cacheAction = this.mAttachedCache;
        if (cacheAction != null) {
            cacheAction.destroy();
            this.mAttachedCache = null;
        }
    }

    private boolean isUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public void destroy() {
        setImageBitmap(null);
        this.abortBlur = true;
        WeakReference<PopupBlurOption> weakReference = this.mBlurOption;
        if (weakReference != null) {
            weakReference.clear();
            this.mBlurOption = null;
        }
        CacheAction cacheAction = this.mCacheAction;
        if (cacheAction != null) {
            cacheAction.destroy();
            this.mCacheAction = null;
        }
        this.blurFinish.set(false);
        this.isAnimating = false;
        this.startDuration = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class CreateBlurBitmapRunnable implements Runnable {
        private Bitmap mBitmap;
        private int outHeight;
        private int outWidth;

        CreateBlurBitmapRunnable(View view) {
            this.outWidth = view.getWidth();
            this.outHeight = view.getHeight();
            this.mBitmap = BlurHelper.getViewBitmap(view, BlurImageView.this.getOption().getBlurPreScaleRatio(), BlurImageView.this.getOption().isFullScreen());
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BlurImageView.this.abortBlur || BlurImageView.this.getOption() == null) {
                PopupLog.m27e("BlurImageView", "放弃模糊，可能是已经移除了布局");
                return;
            }
            PopupLog.m25i("BlurImageView", "子线程模糊执行");
            BlurImageView blurImageView = BlurImageView.this;
            blurImageView.setImageBitmapOnUiThread(BlurHelper.blur(blurImageView.getContext(), this.mBitmap, this.outWidth, this.outHeight, BlurImageView.this.getOption().getBlurRadius()), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class CacheAction {
        Runnable action;
        final long startTime = System.currentTimeMillis();

        CacheAction(Runnable runnable, long j) {
            this.action = runnable;
        }

        void restore() {
            if (isOverTime()) {
                PopupLog.m27e("BlurImageView", "模糊超时");
                destroy();
                return;
            }
            Runnable runnable = this.action;
            if (runnable == null) {
                return;
            }
            BlurImageView.this.post(runnable);
        }

        void forceRestore() {
            Runnable runnable = this.action;
            if (runnable != null) {
                BlurImageView.this.post(runnable);
            }
        }

        boolean isOverTime() {
            return System.currentTimeMillis() - this.startTime > 1000;
        }

        public void destroy() {
            Runnable runnable = this.action;
            if (runnable != null) {
                BlurImageView.this.removeCallbacks(runnable);
            }
            this.action = null;
        }
    }
}
