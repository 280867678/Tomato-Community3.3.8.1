package com.shizhefei.view.largeimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.shizhefei.view.largeimage.BlockImageLoader;
import com.shizhefei.view.largeimage.factory.BitmapDecoderFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LargeImageView extends View implements BlockImageLoader.OnImageLoadListener, ILargeImageView {
    private AccelerateInterpolator accelerateInterpolator;
    private CriticalScaleValueHook criticalScaleValueHook;
    private DecelerateInterpolator decelerateInterpolator;
    private List<BlockImageLoader.DrawData> drawDatas;
    private float fitScale;
    private final GestureDetector gestureDetector;
    private final BlockImageLoader imageBlockImageLoader;
    private Rect imageRect;
    private boolean isAttachedWindow;
    private Drawable mDrawable;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private BitmapDecoderFactory mFactory;
    private int mLevel;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;
    private BlockImageLoader.OnImageLoadListener mOnImageLoadListener;
    private float mScale;
    private final ScrollerCompat mScroller;
    private float maxScale;
    private float minScale;
    private View.OnClickListener onClickListener;
    private OnDoubleClickListener onDoubleClickListener;
    private View.OnLongClickListener onLongClickListener;
    private ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener;
    private final Paint paint;
    private final ScaleGestureDetector scaleGestureDetector;
    private ScaleHelper scaleHelper;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    /* loaded from: classes3.dex */
    public interface CriticalScaleValueHook {
        float getMaxScale(LargeImageView largeImageView, int i, int i2, float f);

        float getMinScale(LargeImageView largeImageView, int i, int i2, float f);
    }

    /* loaded from: classes3.dex */
    public interface OnDoubleClickListener {
        boolean onDoubleClick(LargeImageView largeImageView, MotionEvent motionEvent);
    }

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LargeImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScale = 1.0f;
        this.drawDatas = new ArrayList();
        this.imageRect = new Rect();
        this.simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.shizhefei.view.largeimage.LargeImageView.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onShowPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (!LargeImageView.this.mScroller.isFinished()) {
                    LargeImageView.this.mScroller.abortAnimation();
                    return true;
                }
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (!LargeImageView.this.isEnabled()) {
                    return false;
                }
                if (LargeImageView.this.onClickListener == null || !LargeImageView.this.isClickable()) {
                    return true;
                }
                LargeImageView.this.onClickListener.onClick(LargeImageView.this);
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (!LargeImageView.this.isEnabled()) {
                    return false;
                }
                LargeImageView largeImageView = LargeImageView.this;
                largeImageView.overScrollByCompat((int) f, (int) f2, largeImageView.getScrollX(), LargeImageView.this.getScrollY(), LargeImageView.this.getScrollRangeX(), LargeImageView.this.getScrollRangeY(), 0, 0, false);
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
                if (LargeImageView.this.isEnabled() && LargeImageView.this.onLongClickListener != null && LargeImageView.this.isLongClickable()) {
                    LargeImageView.this.onLongClickListener.onLongClick(LargeImageView.this);
                }
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (!LargeImageView.this.isEnabled()) {
                    return false;
                }
                LargeImageView.this.fling((int) (-f), (int) (-f2));
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (!LargeImageView.this.isEnabled()) {
                    return false;
                }
                if (LargeImageView.this.onDoubleClickListener != null && LargeImageView.this.onDoubleClickListener.onDoubleClick(LargeImageView.this, motionEvent)) {
                    return true;
                }
                if (!LargeImageView.this.hasLoad()) {
                    return false;
                }
                float f = 2.0f;
                if (LargeImageView.this.fitScale >= 2.0f) {
                    f = LargeImageView.this.fitScale > LargeImageView.this.maxScale ? LargeImageView.this.maxScale : LargeImageView.this.fitScale;
                }
                float f2 = 1.0f;
                if (LargeImageView.this.mScale >= 1.0f && LargeImageView.this.mScale < f) {
                    f2 = f;
                }
                LargeImageView.this.smoothScale(f2, (int) motionEvent.getX(), (int) motionEvent.getY());
                return true;
            }
        };
        this.onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.shizhefei.view.largeimage.LargeImageView.3
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                if (LargeImageView.this.isEnabled() && LargeImageView.this.hasLoad()) {
                    float scaleFactor = LargeImageView.this.mScale * scaleGestureDetector.getScaleFactor();
                    if (scaleFactor > LargeImageView.this.maxScale) {
                        scaleFactor = LargeImageView.this.maxScale;
                    } else if (scaleFactor < LargeImageView.this.minScale) {
                        scaleFactor = LargeImageView.this.minScale;
                    }
                    LargeImageView.this.setScale(scaleFactor, (int) scaleGestureDetector.getFocusX(), (int) scaleGestureDetector.getFocusY());
                    return true;
                }
                return false;
            }
        };
        this.mScroller = ScrollerCompat.create(getContext(), null);
        this.scaleHelper = new ScaleHelper();
        setFocusable(true);
        setWillNotDraw(false);
        this.gestureDetector = new GestureDetector(context, this.simpleOnGestureListener);
        this.scaleGestureDetector = new ScaleGestureDetector(context, this.onScaleGestureListener);
        this.imageBlockImageLoader = new BlockImageLoader(context);
        this.imageBlockImageLoader.setOnImageLoadListener(this);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.paint = new Paint();
        this.paint.setColor(-7829368);
        this.paint.setAntiAlias(true);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.scaleGestureDetector.onTouchEvent(motionEvent);
        this.gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.scaleHelper.computeScrollOffset()) {
            setScale(this.scaleHelper.getCurScale(), this.scaleHelper.getStartX(), this.scaleHelper.getStartY());
        }
        if (this.mScroller.computeScrollOffset()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                int i = currX - scrollX;
                int i2 = currY - scrollY;
                overScrollByCompat(i, i2, scrollX, scrollY, getScrollRangeX(), getScrollRangeY(), 0, 0, false);
            }
            if (this.mScroller.isFinished()) {
                return;
            }
            notifyInvalidate();
        }
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        return i > 0 ? getScrollX() < getScrollRangeX() : getScrollX() > 0 && getScrollRangeX() > 0;
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i) {
        return i > 0 ? getScrollY() < getScrollRangeY() : getScrollY() > 0 && getScrollRangeY() > 0;
    }

    @Override // com.shizhefei.view.largeimage.ILargeImageView
    public void setOnImageLoadListener(BlockImageLoader.OnImageLoadListener onImageLoadListener) {
        this.mOnImageLoadListener = onImageLoadListener;
    }

    public void setOnLoadStateChangeListener(BlockImageLoader.OnLoadStateChangeListener onLoadStateChangeListener) {
        BlockImageLoader blockImageLoader = this.imageBlockImageLoader;
        if (blockImageLoader != null) {
            blockImageLoader.setOnLoadStateChangeListener(onLoadStateChangeListener);
        }
    }

    public BlockImageLoader.OnImageLoadListener getOnImageLoadListener() {
        return this.mOnImageLoadListener;
    }

    public void setImage(Bitmap bitmap) {
        setImageDrawable(new BitmapDrawable(getResources(), bitmap));
    }

    public void setImage(Drawable drawable) {
        setImageDrawable(drawable);
    }

    public void setImage(@DrawableRes int i) {
        setImageDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setImageDrawable(Drawable drawable) {
        this.mFactory = null;
        this.mScale = 1.0f;
        scrollTo(0, 0);
        if (this.mDrawable != drawable) {
            int i = this.mDrawableWidth;
            int i2 = this.mDrawableHeight;
            updateDrawable(drawable);
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
                requestLayout();
            }
            notifyInvalidate();
        }
    }

    public void setImage(BitmapDecoderFactory bitmapDecoderFactory) {
        setImage(bitmapDecoderFactory, null);
    }

    public void setImage(BitmapDecoderFactory bitmapDecoderFactory, Drawable drawable) {
        this.mScale = 1.0f;
        this.mFactory = bitmapDecoderFactory;
        scrollTo(0, 0);
        updateDrawable(drawable);
        this.imageBlockImageLoader.setBitmapDecoderFactory(bitmapDecoderFactory);
        invalidate();
    }

    private void updateDrawable(Drawable drawable) {
        Drawable drawable2 = this.mDrawable;
        boolean z = false;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mDrawable);
            if (this.isAttachedWindow) {
                this.mDrawable.setVisible(false, false);
            }
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (this.isAttachedWindow) {
                if (getWindowVisibility() == 0 && isShown()) {
                    z = true;
                }
                drawable.setVisible(z, true);
            }
            drawable.setLevel(this.mLevel);
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            this.mDrawableHeight = drawable.getIntrinsicHeight();
            return;
        }
        this.mDrawableHeight = -1;
        this.mDrawableWidth = -1;
    }

    public boolean hasLoad() {
        if (this.mDrawable != null) {
            return true;
        }
        if (this.mFactory == null) {
            return false;
        }
        return this.imageBlockImageLoader.hasLoad();
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int contentHeight = getContentHeight();
        int scrollY = getScrollY();
        int max = Math.max(0, contentHeight - height);
        return scrollY < 0 ? contentHeight - scrollY : scrollY > max ? contentHeight + (scrollY - max) : contentHeight;
    }

    public float getMinScale() {
        return this.minScale;
    }

    public float getMaxScale() {
        return this.maxScale;
    }

    public float getFitScale() {
        return this.fitScale;
    }

    public int getImageWidth() {
        if (this.mDrawable != null) {
            return this.mDrawableWidth;
        }
        if (this.mFactory != null && hasLoad()) {
            return this.mDrawableWidth;
        }
        return 0;
    }

    public int getImageHeight() {
        if (this.mDrawable != null) {
            return this.mDrawableHeight;
        }
        if (this.mFactory != null && hasLoad()) {
            return this.mDrawableHeight;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScrollRangeY() {
        return getContentHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop());
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
        int contentWidth = getContentWidth();
        int scrollX = getScrollX();
        int max = Math.max(0, contentWidth - width);
        return scrollX < 0 ? contentWidth - scrollX : scrollX > max ? contentWidth + (scrollX - max) : contentWidth;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScrollRangeX() {
        return getContentWidth() - ((getWidth() - getPaddingRight()) - getPaddingLeft());
    }

    private int getContentWidth() {
        if (hasLoad()) {
            return (int) (getMeasuredWidth() * this.mScale);
        }
        return 0;
    }

    private int getContentHeight() {
        if (!hasLoad() || getImageWidth() == 0) {
            return 0;
        }
        return (int) ((((getMeasuredWidth() * 1.0f) * getImageHeight()) / getImageWidth()) * this.mScale);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width == 0) {
            return;
        }
        int contentWidth = getContentWidth();
        int contentHeight = getContentHeight();
        int i = width > contentWidth ? (width - contentWidth) / 2 : 0;
        int i2 = height > contentHeight ? (height - contentHeight) / 2 : 0;
        if (this.mFactory == null) {
            Drawable drawable = this.mDrawable;
            if (drawable == null) {
                return;
            }
            drawable.setBounds(i, i2, contentWidth + i, contentHeight + i2);
            this.mDrawable.draw(canvas);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        float width2 = this.imageBlockImageLoader.getWidth() / (this.mScale * getWidth());
        int i3 = i;
        this.imageRect.left = (int) Math.ceil((scrollX - 0) * width2);
        this.imageRect.top = (int) Math.ceil((scrollY - 0) * width2);
        this.imageRect.right = (int) Math.ceil(((scrollX + width) - 0) * width2);
        this.imageRect.bottom = (int) Math.ceil(((scrollY + height) - 0) * width2);
        int save = canvas.save();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (this.mDrawable == null || !this.imageBlockImageLoader.hasLoad() || this.imageBlockImageLoader.getWidth() * this.imageBlockImageLoader.getHeight() > displayMetrics.widthPixels * displayMetrics.heightPixels) {
            this.imageBlockImageLoader.loadImageBlocks(this.drawDatas, width2, this.imageRect, width, height);
        }
        if (BlockImageLoader.DEBUG) {
            for (int i4 = 0; i4 < this.drawDatas.size(); i4++) {
                BlockImageLoader.DrawData drawData = this.drawDatas.get(i4);
                Rect rect = drawData.imageRect;
                double d = 0;
                rect.left = ((int) (Math.ceil(rect.left / width2) + d)) + i3;
                rect.top = ((int) (Math.ceil(rect.top / width2) + d)) + i2;
                rect.right = ((int) (Math.ceil(rect.right / width2) + d)) + i3;
                rect.bottom = ((int) (Math.ceil(rect.bottom / width2) + d)) + i2;
                if (i4 == 0) {
                    canvas.drawRect(drawData.imageRect, this.paint);
                } else {
                    rect.left += 3;
                    rect.top += 3;
                    rect.bottom -= 3;
                    rect.right -= 3;
                    canvas.drawBitmap(drawData.bitmap, drawData.srcRect, rect, (Paint) null);
                }
            }
        } else if (this.drawDatas.isEmpty()) {
            Drawable drawable2 = this.mDrawable;
            if (drawable2 != null) {
                drawable2.setBounds(i3, i2, i3 + contentWidth, contentHeight + i2);
                this.mDrawable.draw(canvas);
            }
        } else {
            for (BlockImageLoader.DrawData drawData2 : this.drawDatas) {
                Rect rect2 = drawData2.imageRect;
                double d2 = 0;
                rect2.left = ((int) (Math.ceil(rect2.left / width2) + d2)) + i3;
                rect2.top = ((int) (Math.ceil(rect2.top / width2) + d2)) + i2;
                rect2.right = ((int) (Math.ceil(rect2.right / width2) + d2)) + i3;
                rect2.bottom = ((int) (Math.ceil(rect2.bottom / width2) + d2)) + i2;
                canvas.drawBitmap(drawData2.bitmap, drawData2.srcRect, rect2, (Paint) null);
            }
        }
        canvas.restoreToCount(save);
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onBlockImageLoadFinished() {
        notifyInvalidate();
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.mOnImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onBlockImageLoadFinished();
        }
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onLoadImageSize(final int i, final int i2) {
        this.mDrawableWidth = i;
        this.mDrawableHeight = i2;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            post(new Runnable() { // from class: com.shizhefei.view.largeimage.LargeImageView.1
                @Override // java.lang.Runnable
                public void run() {
                    LargeImageView.this.initFitImageScale(i, i2);
                }
            });
        } else {
            initFitImageScale(i, i2);
        }
        notifyInvalidate();
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.mOnImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onLoadImageSize(i, i2);
        }
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onLoadFail(Exception exc) {
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.mOnImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onLoadFail(exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFitImageScale(int i, int i2) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (i > i2) {
            float f = (i * 1.0f) / measuredWidth;
            this.fitScale = (measuredHeight * f) / i2;
            this.maxScale = f * 4.0f;
            this.minScale = f / 4.0f;
            if (this.minScale > 1.0f) {
                this.minScale = 1.0f;
            }
        } else {
            this.fitScale = 1.0f;
            this.minScale = 0.25f;
            float f2 = (i * 1.0f) / measuredWidth;
            this.maxScale = f2;
            float f3 = (f2 * measuredHeight) / i2;
            this.maxScale *= getContext().getResources().getDisplayMetrics().density;
            if (this.maxScale < 4.0f) {
                this.maxScale = 4.0f;
            }
            if (this.minScale > f3) {
                this.minScale = f3;
            }
        }
        CriticalScaleValueHook criticalScaleValueHook = this.criticalScaleValueHook;
        if (criticalScaleValueHook != null) {
            this.minScale = criticalScaleValueHook.getMinScale(this, i, i2, this.minScale);
            this.maxScale = this.criticalScaleValueHook.getMaxScale(this, i, i2, this.maxScale);
        }
    }

    private void notifyInvalidate() {
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedWindow = false;
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setVisible(getVisibility() == 0, false);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedWindow = true;
        this.imageBlockImageLoader.stopLoad();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setVisible(false, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean overScrollByCompat(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        boolean z3;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i9 = i3 + i;
        int i10 = i4 + i2;
        int i11 = -i7;
        int i12 = i5 + i7;
        int i13 = -i8;
        int i14 = i6 + i8;
        if (i9 > i12) {
            i9 = i12;
        } else if (i9 >= i11) {
            z2 = false;
            if (i10 <= i14) {
                i10 = i14;
            } else if (i10 >= i13) {
                z3 = false;
                if (i9 < 0) {
                    i9 = 0;
                }
                if (i10 < 0) {
                    i10 = 0;
                }
                onOverScrolled(i9, i10, z2, z3);
                return getScrollX() - scrollX != i || getScrollY() - scrollY == i2;
            } else {
                i10 = i13;
            }
            z3 = true;
            if (i9 < 0) {
            }
            if (i10 < 0) {
            }
            onOverScrolled(i9, i10, z2, z3);
            if (getScrollX() - scrollX != i) {
                return true;
            }
        } else {
            i9 = i11;
        }
        z2 = true;
        if (i10 <= i14) {
        }
        z3 = true;
        if (i9 < 0) {
        }
        if (i10 < 0) {
        }
        onOverScrolled(i9, i10, z2, z3);
        if (getScrollX() - scrollX != i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean fling(int i, int i2) {
        int i3 = Math.abs(i) < this.mMinimumVelocity ? 0 : i;
        int i4 = Math.abs(i2) < this.mMinimumVelocity ? 0 : i2;
        int scrollY = getScrollY();
        int scrollX = getScrollX();
        if (((scrollY > 0 || i4 > 0) && (scrollY < getScrollRangeY() || i4 < 0)) || ((scrollX > 0 || i3 > 0) && (scrollX < getScrollRangeX() || i3 < 0))) {
            int i5 = this.mMaximumVelocity;
            int max = Math.max(-i5, Math.min(i3, i5));
            int i6 = this.mMaximumVelocity;
            int max2 = Math.max(-i6, Math.min(i4, i6));
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            this.mScroller.fling(getScrollX(), getScrollY(), max, max2, 0, Math.max(0, getContentWidth() - width), 0, Math.max(0, getContentHeight() - height), width / 2, height / 2);
            notifyInvalidate();
            return true;
        }
        return false;
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (hasLoad()) {
            initFitImageScale(this.mDrawableWidth, this.mDrawableHeight);
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.onClickListener = onClickListener;
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
        this.onLongClickListener = onLongClickListener;
    }

    public void setCriticalScaleValueHook(CriticalScaleValueHook criticalScaleValueHook) {
        this.criticalScaleValueHook = criticalScaleValueHook;
    }

    public void smoothScale(float f, int i, int i2) {
        if (this.mScale > f) {
            if (this.accelerateInterpolator == null) {
                this.accelerateInterpolator = new AccelerateInterpolator();
            }
            this.scaleHelper.startScale(this.mScale, f, i, i2, this.accelerateInterpolator);
        } else {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            this.scaleHelper.startScale(this.mScale, f, i, i2, this.decelerateInterpolator);
        }
        notifyInvalidate();
    }

    public void setScale(float f) {
        setScale(f, getMeasuredWidth() >> 1, getMeasuredHeight() >> 1);
    }

    public float getScale() {
        return this.mScale;
    }

    public void setScale(float f, int i, int i2) {
        if (!hasLoad()) {
            return;
        }
        float f2 = this.mScale;
        this.mScale = f;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        float f3 = (f / f2) - 1.0f;
        overScrollByCompat((int) ((i + scrollX) * f3), (int) ((i2 + scrollY) * f3), scrollX, scrollY, getScrollRangeX(), getScrollRangeY(), 0, 0, false);
        notifyInvalidate();
    }

    public OnDoubleClickListener getOnDoubleClickListener() {
        return this.onDoubleClickListener;
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }
}
