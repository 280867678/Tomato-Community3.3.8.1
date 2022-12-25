package com.tomatolive.library.p136ui.view.widget.badgeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.tomatolive.library.p136ui.view.widget.badgeView.Badge;
import com.tomatolive.library.utils.SystemUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.badgeView.QBadgeView */
/* loaded from: classes4.dex */
public class QBadgeView extends View implements Badge {
    protected ViewGroup mActivityRoot;
    protected BadgeAnimator mAnimator;
    protected float mBackgroundBorderWidth;
    protected Paint mBadgeBackgroundBorderPaint;
    protected Paint mBadgeBackgroundPaint;
    protected RectF mBadgeBackgroundRect;
    protected PointF mBadgeCenter;
    protected int mBadgeGravity;
    protected int mBadgeNumber;
    protected float mBadgePadding;
    protected String mBadgeText;
    protected Paint.FontMetrics mBadgeTextFontMetrics;
    protected TextPaint mBadgeTextPaint;
    protected RectF mBadgeTextRect;
    protected float mBadgeTextSize;
    protected Bitmap mBitmapClip;
    protected int mColorBackground;
    protected int mColorBackgroundBorder;
    protected int mColorBadgeText;
    protected PointF mControlPoint;
    protected float mDefalutRadius;
    protected PointF mDragCenter;
    protected boolean mDragOutOfRange;
    protected Path mDragPath;
    protected int mDragQuadrant;
    protected Badge.OnDragStateChangedListener mDragStateChangedListener;
    protected boolean mDraggable;
    protected boolean mDragging;
    protected Drawable mDrawableBackground;
    protected boolean mDrawableBackgroundClip;
    protected boolean mExact;
    protected float mFinalDragDistance;
    protected float mGravityOffsetX;
    protected float mGravityOffsetY;
    protected int mHeight;
    protected List<PointF> mInnertangentPoints;
    protected PointF mRowBadgeCenter;
    protected boolean mShowShadow;
    protected View mTargetView;
    protected int mWidth;
    private boolean noNumber;

    public QBadgeView(Context context) {
        this(context, null);
    }

    private QBadgeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private QBadgeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setLayerType(1, null);
        this.mBadgeTextRect = new RectF();
        this.mBadgeBackgroundRect = new RectF();
        this.mDragPath = new Path();
        this.mBadgeCenter = new PointF();
        this.mDragCenter = new PointF();
        this.mRowBadgeCenter = new PointF();
        this.mControlPoint = new PointF();
        this.mInnertangentPoints = new ArrayList();
        this.mBadgeTextPaint = new TextPaint();
        this.mBadgeTextPaint.setAntiAlias(true);
        this.mBadgeTextPaint.setSubpixelText(true);
        this.mBadgeTextPaint.setFakeBoldText(true);
        this.mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.mBadgeBackgroundPaint = new Paint();
        this.mBadgeBackgroundPaint.setAntiAlias(true);
        this.mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBadgeBackgroundBorderPaint = new Paint();
        this.mBadgeBackgroundBorderPaint.setAntiAlias(true);
        this.mBadgeBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
        this.mColorBackground = -1552832;
        this.mColorBadgeText = -1;
        this.mBadgeTextSize = SystemUtils.dp2px(11.0f);
        this.mBadgePadding = SystemUtils.dp2px(5.0f);
        this.mBadgeNumber = 0;
        this.mBadgeGravity = 8388661;
        this.mGravityOffsetX = SystemUtils.dp2px(1.0f);
        this.mGravityOffsetY = SystemUtils.dp2px(1.0f);
        this.mFinalDragDistance = SystemUtils.dp2px(90.0f);
        this.mShowShadow = true;
        this.mDrawableBackgroundClip = false;
        if (Build.VERSION.SDK_INT >= 21) {
            setTranslationZ(1000.0f);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge bindTarget(View view) {
        if (view == null) {
            throw new IllegalStateException("targetView can not be null");
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewParent parent = view.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            this.mTargetView = view;
            if (parent instanceof BadgeContainer) {
                ((BadgeContainer) parent).addView(this);
            } else {
                ViewGroup viewGroup = (ViewGroup) parent;
                int indexOfChild = viewGroup.indexOfChild(view);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                viewGroup.removeView(view);
                BadgeContainer badgeContainer = new BadgeContainer(getContext());
                if (viewGroup instanceof RelativeLayout) {
                    badgeContainer.setId(view.getId());
                }
                viewGroup.addView(badgeContainer, indexOfChild, layoutParams);
                badgeContainer.addView(view);
                badgeContainer.addView(this);
            }
            return this;
        }
        throw new IllegalStateException("targetView must have a parent");
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public View getTargetView() {
        return this.mTargetView;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mActivityRoot == null) {
            findViewRoot(this.mTargetView);
        }
    }

    private void findViewRoot(View view) {
        this.mActivityRoot = (ViewGroup) view.getRootView();
        if (this.mActivityRoot == null) {
            findActivityRoot(view);
        }
    }

    private void findActivityRoot(View view) {
        if (view.getParent() != null && (view.getParent() instanceof View)) {
            findActivityRoot((View) view.getParent());
        } else if (!(view instanceof ViewGroup)) {
        } else {
            this.mActivityRoot = (ViewGroup) view;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
        if (r0 != 6) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a7  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked != 5) {
                        }
                    }
                } else if (this.mDragging) {
                    this.mDragCenter.x = motionEvent.getRawX();
                    this.mDragCenter.y = motionEvent.getRawY();
                    invalidate();
                }
                return !this.mDragging || super.onTouchEvent(motionEvent);
            }
            if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0 && this.mDragging) {
                this.mDragging = false;
                onPointerUp();
            }
            if (!this.mDragging) {
            }
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mDraggable && motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
            RectF rectF = this.mBadgeBackgroundRect;
            if (x > rectF.left && x < rectF.right && y > rectF.top && y < rectF.bottom && this.mBadgeText != null) {
                initRowBadgeCenter();
                this.mDragging = true;
                updataListener(1);
                this.mDefalutRadius = SystemUtils.dp2px(7.0f);
                getParent().requestDisallowInterceptTouchEvent(true);
                screenFromWindow(true);
                this.mDragCenter.x = motionEvent.getRawX();
                this.mDragCenter.y = motionEvent.getRawY();
            }
        }
        if (!this.mDragging) {
        }
    }

    private void onPointerUp() {
        if (this.mDragOutOfRange) {
            animateHide(this.mDragCenter);
            updataListener(5);
            return;
        }
        reset();
        updataListener(4);
    }

    protected Bitmap createBadgeBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap((int) (this.mBadgeBackgroundRect.width() + SystemUtils.dp2px(2.0f)), (int) (this.mBadgeBackgroundRect.height() + SystemUtils.dp2px(2.0f)), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawBadge(canvas, new PointF(canvas.getWidth() / 2.0f, canvas.getHeight() / 2.0f), getBadgeCircleRadius());
        return createBitmap;
    }

    protected void screenFromWindow(boolean z) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (z) {
            this.mActivityRoot.addView(this, new FrameLayout.LayoutParams(-1, -1));
        } else {
            bindTarget(this.mTargetView);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void showShadowImpl(boolean z) {
        float dp2px;
        int dp2px2 = (int) SystemUtils.dp2px(1.0f);
        int dp2px3 = (int) SystemUtils.dp2px(1.5f);
        int i = this.mDragQuadrant;
        if (i == 1) {
            dp2px2 = (int) SystemUtils.dp2px(1.0f);
            dp2px = SystemUtils.dp2px(-1.5f);
        } else if (i == 2) {
            dp2px2 = (int) SystemUtils.dp2px(-1.0f);
            dp2px = SystemUtils.dp2px(-1.5f);
        } else if (i == 3) {
            dp2px2 = (int) SystemUtils.dp2px(-1.0f);
            dp2px = SystemUtils.dp2px(1.5f);
        } else {
            if (i == 4) {
                dp2px2 = (int) SystemUtils.dp2px(1.0f);
                dp2px = SystemUtils.dp2px(1.5f);
            }
            this.mBadgeBackgroundPaint.setShadowLayer(!z ? SystemUtils.dp2px(2.0f) : 0.0f, dp2px2, dp2px3, 855638016);
        }
        dp2px3 = (int) dp2px;
        this.mBadgeBackgroundPaint.setShadowLayer(!z ? SystemUtils.dp2px(2.0f) : 0.0f, dp2px2, dp2px3, 855638016);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        BadgeAnimator badgeAnimator = this.mAnimator;
        if (badgeAnimator != null && badgeAnimator.isRunning()) {
            this.mAnimator.draw(canvas);
        } else if (this.mBadgeText == null) {
        } else {
            initPaints();
            float badgeCircleRadius = getBadgeCircleRadius();
            float pointDistance = this.mDefalutRadius * (1.0f - (MathUtil.getPointDistance(this.mRowBadgeCenter, this.mDragCenter) / this.mFinalDragDistance));
            if (this.mDraggable && this.mDragging) {
                this.mDragQuadrant = MathUtil.getQuadrant(this.mDragCenter, this.mRowBadgeCenter);
                showShadowImpl(this.mShowShadow);
                boolean z = pointDistance < SystemUtils.dp2px(1.5f);
                this.mDragOutOfRange = z;
                if (z) {
                    updataListener(3);
                    drawBadge(canvas, this.mDragCenter, badgeCircleRadius);
                    return;
                }
                updataListener(2);
                drawDragging(canvas, pointDistance, badgeCircleRadius);
                drawBadge(canvas, this.mDragCenter, badgeCircleRadius);
                return;
            }
            findBadgeCenter();
            drawBadge(canvas, this.mBadgeCenter, badgeCircleRadius);
        }
    }

    private void initPaints() {
        showShadowImpl(this.mShowShadow);
        this.mBadgeBackgroundPaint.setColor(this.mColorBackground);
        this.mBadgeBackgroundBorderPaint.setColor(this.mColorBackgroundBorder);
        this.mBadgeBackgroundBorderPaint.setStrokeWidth(this.mBackgroundBorderWidth);
        this.mBadgeTextPaint.setColor(this.mColorBadgeText);
        this.mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void drawDragging(Canvas canvas, float f, float f2) {
        float f3;
        float f4;
        float f5;
        PointF pointF = this.mDragCenter;
        float f6 = pointF.y;
        PointF pointF2 = this.mRowBadgeCenter;
        float f7 = f6 - pointF2.y;
        float f8 = pointF.x - pointF2.x;
        this.mInnertangentPoints.clear();
        if (f8 != 0.0f) {
            double d = (-1.0d) / (f7 / f8);
            MathUtil.getInnertangentPoints(this.mDragCenter, f2, Double.valueOf(d), this.mInnertangentPoints);
            MathUtil.getInnertangentPoints(this.mRowBadgeCenter, f, Double.valueOf(d), this.mInnertangentPoints);
        } else {
            MathUtil.getInnertangentPoints(this.mDragCenter, f2, Double.valueOf(0.0d), this.mInnertangentPoints);
            MathUtil.getInnertangentPoints(this.mRowBadgeCenter, f, Double.valueOf(0.0d), this.mInnertangentPoints);
        }
        this.mDragPath.reset();
        Path path = this.mDragPath;
        PointF pointF3 = this.mRowBadgeCenter;
        float f9 = pointF3.x;
        float f10 = pointF3.y;
        int i = this.mDragQuadrant;
        path.addCircle(f9, f10, f, (i == 1 || i == 2) ? Path.Direction.CCW : Path.Direction.CW);
        PointF pointF4 = this.mControlPoint;
        PointF pointF5 = this.mRowBadgeCenter;
        float f11 = pointF5.x;
        PointF pointF6 = this.mDragCenter;
        pointF4.x = (f11 + pointF6.x) / 2.0f;
        pointF4.y = (pointF5.y + pointF6.y) / 2.0f;
        this.mDragPath.moveTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        Path path2 = this.mDragPath;
        PointF pointF7 = this.mControlPoint;
        path2.quadTo(pointF7.x, pointF7.y, this.mInnertangentPoints.get(0).x, this.mInnertangentPoints.get(0).y);
        this.mDragPath.lineTo(this.mInnertangentPoints.get(1).x, this.mInnertangentPoints.get(1).y);
        Path path3 = this.mDragPath;
        PointF pointF8 = this.mControlPoint;
        path3.quadTo(pointF8.x, pointF8.y, this.mInnertangentPoints.get(3).x, this.mInnertangentPoints.get(3).y);
        this.mDragPath.lineTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        this.mDragPath.close();
        canvas.drawPath(this.mDragPath, this.mBadgeBackgroundPaint);
        if (this.mColorBackgroundBorder == 0 || this.mBackgroundBorderWidth <= 0.0f) {
            return;
        }
        this.mDragPath.reset();
        this.mDragPath.moveTo(this.mInnertangentPoints.get(2).x, this.mInnertangentPoints.get(2).y);
        Path path4 = this.mDragPath;
        PointF pointF9 = this.mControlPoint;
        path4.quadTo(pointF9.x, pointF9.y, this.mInnertangentPoints.get(0).x, this.mInnertangentPoints.get(0).y);
        this.mDragPath.moveTo(this.mInnertangentPoints.get(1).x, this.mInnertangentPoints.get(1).y);
        Path path5 = this.mDragPath;
        PointF pointF10 = this.mControlPoint;
        path5.quadTo(pointF10.x, pointF10.y, this.mInnertangentPoints.get(3).x, this.mInnertangentPoints.get(3).y);
        int i2 = this.mDragQuadrant;
        if (i2 == 1 || i2 == 2) {
            float f12 = this.mInnertangentPoints.get(2).x;
            PointF pointF11 = this.mRowBadgeCenter;
            f3 = f12 - pointF11.x;
            f4 = pointF11.y;
            f5 = this.mInnertangentPoints.get(2).y;
        } else {
            float f13 = this.mInnertangentPoints.get(3).x;
            PointF pointF12 = this.mRowBadgeCenter;
            f3 = f13 - pointF12.x;
            f4 = pointF12.y;
            f5 = this.mInnertangentPoints.get(3).y;
        }
        double atan = Math.atan((f4 - f5) / f3);
        int i3 = this.mDragQuadrant;
        float radianToAngle = 360.0f - ((float) MathUtil.radianToAngle(MathUtil.getTanRadian(atan, i3 + (-1) == 0 ? 4 : i3 - 1)));
        if (Build.VERSION.SDK_INT >= 21) {
            Path path6 = this.mDragPath;
            PointF pointF13 = this.mRowBadgeCenter;
            float f14 = pointF13.x;
            float f15 = pointF13.y;
            path6.addArc(f14 - f, f15 - f, f14 + f, f15 + f, radianToAngle, 180.0f);
        } else {
            Path path7 = this.mDragPath;
            PointF pointF14 = this.mRowBadgeCenter;
            float f16 = pointF14.x;
            float f17 = pointF14.y;
            path7.addArc(new RectF(f16 - f, f17 - f, f16 + f, f17 + f), radianToAngle, 180.0f);
        }
        canvas.drawPath(this.mDragPath, this.mBadgeBackgroundBorderPaint);
    }

    private void drawBadge(Canvas canvas, PointF pointF, float f) {
        if (pointF.x == -1000.0f && pointF.y == -1000.0f) {
            return;
        }
        if (this.noNumber) {
            f /= 2.0f;
        }
        if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
            RectF rectF = this.mBadgeBackgroundRect;
            float f2 = pointF.x;
            float f3 = (int) f;
            rectF.left = f2 - f3;
            float f4 = pointF.y;
            rectF.top = f4 - f3;
            rectF.right = f2 + f3;
            rectF.bottom = f3 + f4;
            if (this.mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawCircle(f2, f4, f, this.mBadgeBackgroundPaint);
                if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > 0.0f) {
                    canvas.drawCircle(pointF.x, pointF.y, f, this.mBadgeBackgroundBorderPaint);
                }
            }
        } else {
            this.mBadgeBackgroundRect.left = pointF.x - ((this.mBadgeTextRect.width() / 2.0f) + this.mBadgePadding);
            this.mBadgeBackgroundRect.top = pointF.y - ((this.mBadgeTextRect.height() / 2.0f) + (this.mBadgePadding * 0.5f));
            this.mBadgeBackgroundRect.right = pointF.x + (this.mBadgeTextRect.width() / 2.0f) + this.mBadgePadding;
            this.mBadgeBackgroundRect.bottom = pointF.y + (this.mBadgeTextRect.height() / 2.0f) + (this.mBadgePadding * 0.5f);
            float height = this.mBadgeBackgroundRect.height() / 2.0f;
            if (this.mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawRoundRect(this.mBadgeBackgroundRect, height, height, this.mBadgeBackgroundPaint);
                if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > 0.0f) {
                    canvas.drawRoundRect(this.mBadgeBackgroundRect, height, height, this.mBadgeBackgroundBorderPaint);
                }
            }
        }
        if (this.mBadgeText.isEmpty() || this.mBadgeNumber <= 0) {
            return;
        }
        String str = this.mBadgeText;
        float f5 = pointF.x;
        RectF rectF2 = this.mBadgeBackgroundRect;
        float f6 = rectF2.bottom + rectF2.top;
        Paint.FontMetrics fontMetrics = this.mBadgeTextFontMetrics;
        canvas.drawText(str, f5, ((f6 - fontMetrics.bottom) - fontMetrics.top) / 2.0f, this.mBadgeTextPaint);
    }

    private void drawBadgeBackground(Canvas canvas) {
        this.mBadgeBackgroundPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        RectF rectF = this.mBadgeBackgroundRect;
        int i = (int) rectF.left;
        int i2 = (int) rectF.top;
        int i3 = (int) rectF.right;
        int i4 = (int) rectF.bottom;
        if (this.mDrawableBackgroundClip) {
            i3 = i + this.mBitmapClip.getWidth();
            i4 = this.mBitmapClip.getHeight() + i2;
            canvas.saveLayer(i, i2, i3, i4, null, 31);
        }
        this.mDrawableBackground.setBounds(i, i2, i3, i4);
        this.mDrawableBackground.draw(canvas);
        if (this.mDrawableBackgroundClip) {
            this.mBadgeBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(this.mBitmapClip, i, i2, this.mBadgeBackgroundPaint);
            canvas.restore();
            this.mBadgeBackgroundPaint.setXfermode(null);
            if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
                canvas.drawCircle(this.mBadgeBackgroundRect.centerX(), this.mBadgeBackgroundRect.centerY(), this.mBadgeBackgroundRect.width() / 2.0f, this.mBadgeBackgroundBorderPaint);
                return;
            }
            RectF rectF2 = this.mBadgeBackgroundRect;
            canvas.drawRoundRect(rectF2, rectF2.height() / 2.0f, this.mBadgeBackgroundRect.height() / 2.0f, this.mBadgeBackgroundBorderPaint);
            return;
        }
        canvas.drawRect(this.mBadgeBackgroundRect, this.mBadgeBackgroundBorderPaint);
    }

    private void createClipLayer() {
        if (this.mBadgeText != null && this.mDrawableBackgroundClip) {
            Bitmap bitmap = this.mBitmapClip;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBitmapClip.recycle();
            }
            float badgeCircleRadius = getBadgeCircleRadius();
            if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
                int i = ((int) badgeCircleRadius) * 2;
                this.mBitmapClip = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(this.mBitmapClip);
                canvas.drawCircle(canvas.getWidth() / 2.0f, canvas.getHeight() / 2.0f, canvas.getWidth() / 2.0f, this.mBadgeBackgroundPaint);
                return;
            }
            this.mBitmapClip = Bitmap.createBitmap((int) (this.mBadgeTextRect.width() + (this.mBadgePadding * 2.0f)), (int) (this.mBadgeTextRect.height() + this.mBadgePadding), Bitmap.Config.ARGB_4444);
            Canvas canvas2 = new Canvas(this.mBitmapClip);
            if (Build.VERSION.SDK_INT >= 21) {
                canvas2.drawRoundRect(0.0f, 0.0f, canvas2.getWidth(), canvas2.getHeight(), canvas2.getHeight() / 2.0f, canvas2.getHeight() / 2.0f, this.mBadgeBackgroundPaint);
            } else {
                canvas2.drawRoundRect(new RectF(0.0f, 0.0f, canvas2.getWidth(), canvas2.getHeight()), canvas2.getHeight() / 2.0f, canvas2.getHeight() / 2.0f, this.mBadgeBackgroundPaint);
            }
        }
    }

    private float getBadgeCircleRadius() {
        float width;
        float f;
        if (this.mBadgeText.isEmpty()) {
            return this.mBadgePadding;
        }
        if (this.mBadgeText.length() == 1) {
            if (this.mBadgeTextRect.height() > this.mBadgeTextRect.width()) {
                width = this.mBadgeTextRect.height() / 2.0f;
                f = this.mBadgePadding;
            } else {
                width = this.mBadgeTextRect.width() / 2.0f;
                f = this.mBadgePadding;
            }
            return width + (f * 0.5f);
        }
        return this.mBadgeBackgroundRect.height() / 2.0f;
    }

    private void findBadgeCenter() {
        float height = this.mBadgeTextRect.height() > this.mBadgeTextRect.width() ? this.mBadgeTextRect.height() : this.mBadgeTextRect.width();
        switch (this.mBadgeGravity) {
            case 17:
                PointF pointF = this.mBadgeCenter;
                pointF.x = this.mWidth / 2.0f;
                pointF.y = this.mHeight / 2.0f;
                break;
            case 49:
                PointF pointF2 = this.mBadgeCenter;
                pointF2.x = this.mWidth / 2.0f;
                pointF2.y = this.mGravityOffsetY + this.mBadgePadding + (this.mBadgeTextRect.height() / 2.0f);
                break;
            case 81:
                PointF pointF3 = this.mBadgeCenter;
                pointF3.x = this.mWidth / 2.0f;
                pointF3.y = this.mHeight - ((this.mGravityOffsetY + this.mBadgePadding) + (this.mBadgeTextRect.height() / 2.0f));
                break;
            case 8388627:
                PointF pointF4 = this.mBadgeCenter;
                pointF4.x = this.mGravityOffsetX + this.mBadgePadding + (height / 2.0f);
                pointF4.y = this.mHeight / 2.0f;
                break;
            case 8388629:
                PointF pointF5 = this.mBadgeCenter;
                pointF5.x = this.mWidth - ((this.mGravityOffsetX + this.mBadgePadding) + (height / 2.0f));
                pointF5.y = this.mHeight / 2.0f;
                break;
            case 8388659:
                PointF pointF6 = this.mBadgeCenter;
                float f = this.mGravityOffsetX;
                float f2 = this.mBadgePadding;
                pointF6.x = f + f2 + (height / 2.0f);
                pointF6.y = this.mGravityOffsetY + f2 + (this.mBadgeTextRect.height() / 2.0f);
                break;
            case 8388661:
                if (this.noNumber) {
                    PointF pointF7 = this.mBadgeCenter;
                    pointF7.x = this.mWidth - (height / 2.0f);
                    pointF7.y = this.mBadgeTextRect.height() / 2.0f;
                    break;
                } else {
                    PointF pointF8 = this.mBadgeCenter;
                    float f3 = this.mGravityOffsetX;
                    float f4 = this.mBadgePadding;
                    pointF8.x = this.mWidth - ((f3 + f4) + (height / 2.0f));
                    pointF8.y = this.mGravityOffsetY + f4 + (this.mBadgeTextRect.height() / 2.0f);
                    break;
                }
            case 8388691:
                PointF pointF9 = this.mBadgeCenter;
                float f5 = this.mGravityOffsetX;
                float f6 = this.mBadgePadding;
                pointF9.x = f5 + f6 + (height / 2.0f);
                pointF9.y = this.mHeight - ((this.mGravityOffsetY + f6) + (this.mBadgeTextRect.height() / 2.0f));
                break;
            case 8388693:
                PointF pointF10 = this.mBadgeCenter;
                float f7 = this.mGravityOffsetX;
                float f8 = this.mBadgePadding;
                pointF10.x = this.mWidth - ((f7 + f8) + (height / 2.0f));
                pointF10.y = this.mHeight - ((this.mGravityOffsetY + f8) + (this.mBadgeTextRect.height() / 2.0f));
                break;
        }
        initRowBadgeCenter();
    }

    private void measureText() {
        RectF rectF = this.mBadgeTextRect;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        if (TextUtils.isEmpty(this.mBadgeText)) {
            RectF rectF2 = this.mBadgeTextRect;
            rectF2.right = 0.0f;
            rectF2.bottom = 0.0f;
        } else {
            this.mBadgeTextPaint.setTextSize(this.mBadgeTextSize);
            this.mBadgeTextRect.right = this.mBadgeTextPaint.measureText(this.mBadgeText);
            this.mBadgeTextFontMetrics = this.mBadgeTextPaint.getFontMetrics();
            RectF rectF3 = this.mBadgeTextRect;
            Paint.FontMetrics fontMetrics = this.mBadgeTextFontMetrics;
            rectF3.bottom = fontMetrics.descent - fontMetrics.ascent;
        }
        createClipLayer();
    }

    private void initRowBadgeCenter() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        PointF pointF = this.mRowBadgeCenter;
        PointF pointF2 = this.mBadgeCenter;
        pointF.x = pointF2.x + iArr[0];
        pointF.y = pointF2.y + iArr[1];
    }

    protected void animateHide(PointF pointF) {
        if (this.mBadgeText == null) {
            return;
        }
        BadgeAnimator badgeAnimator = this.mAnimator;
        if (badgeAnimator != null && badgeAnimator.isRunning()) {
            return;
        }
        screenFromWindow(true);
        this.mAnimator = new BadgeAnimator(createBadgeBitmap(), pointF, this);
        this.mAnimator.start();
        setBadgeNumber(0);
    }

    public void reset() {
        PointF pointF = this.mDragCenter;
        pointF.x = -1000.0f;
        pointF.y = -1000.0f;
        this.mDragQuadrant = 4;
        screenFromWindow(false);
        getParent().requestDisallowInterceptTouchEvent(false);
        invalidate();
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public void hide(boolean z) {
        if (z && this.mActivityRoot != null) {
            initRowBadgeCenter();
            animateHide(this.mRowBadgeCenter);
            return;
        }
        setBadgeNumber(0);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeNumber(int i) {
        this.mBadgeNumber = i;
        int i2 = this.mBadgeNumber;
        if (i2 < 0) {
            this.mBadgeText = "有";
        } else if (i2 > 99) {
            this.mBadgeText = this.mExact ? String.valueOf(i2) : "99+";
        } else if (i2 > 0 && i2 <= 99) {
            this.mBadgeText = String.valueOf(i2);
        } else if (this.mBadgeNumber == 0) {
            this.mBadgeText = null;
        }
        measureText();
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public int getBadgeNumber() {
        return this.mBadgeNumber;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeText(String str) {
        this.mBadgeText = str;
        this.mBadgeNumber = 1;
        measureText();
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public String getBadgeText() {
        return this.mBadgeText;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setExactMode(boolean z) {
        this.mExact = z;
        int i = this.mBadgeNumber;
        if (i > 99) {
            setBadgeNumber(i);
        }
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public boolean isExactMode() {
        return this.mExact;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setShowShadow(boolean z) {
        this.mShowShadow = z;
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public boolean isShowShadow() {
        return this.mShowShadow;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeBackgroundColor(int i) {
        this.mColorBackground = i;
        if (this.mColorBackground == 0) {
            this.mBadgeTextPaint.setXfermode(null);
        } else {
            this.mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge stroke(int i, float f, boolean z) {
        this.mColorBackgroundBorder = i;
        if (z) {
            f = SystemUtils.dp2px(f);
        }
        this.mBackgroundBorderWidth = f;
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public int getBadgeBackgroundColor() {
        return this.mColorBackground;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeBackground(Drawable drawable) {
        return setBadgeBackground(drawable, false);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeBackground(Drawable drawable, boolean z) {
        this.mDrawableBackgroundClip = z;
        this.mDrawableBackground = drawable;
        createClipLayer();
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Drawable getBadgeBackground() {
        return this.mDrawableBackground;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeTextColor(int i) {
        this.mColorBadgeText = i;
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public int getBadgeTextColor() {
        return this.mColorBadgeText;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeTextSize(float f, boolean z) {
        if (z) {
            f = SystemUtils.dp2px(f);
        }
        this.mBadgeTextSize = f;
        measureText();
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public float getBadgeTextSize(boolean z) {
        return z ? SystemUtils.px2dp(this.mBadgeTextSize) : this.mBadgeTextSize;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgePadding(float f, boolean z) {
        if (z) {
            f = SystemUtils.dp2px(f);
        }
        this.mBadgePadding = f;
        createClipLayer();
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public float getBadgePadding(boolean z) {
        return z ? SystemUtils.px2dp(this.mBadgePadding) : this.mBadgePadding;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public boolean isDraggable() {
        return this.mDraggable;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setBadgeGravity(int i) {
        if (i == 8388659 || i == 8388661 || i == 8388691 || i == 8388693 || i == 17 || i == 49 || i == 81 || i == 8388627 || i == 8388629) {
            this.mBadgeGravity = i;
            invalidate();
            return this;
        }
        throw new IllegalStateException("only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP , Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM , Gravity.CENTER , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ,Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END");
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge isNoNumber(boolean z) {
        this.noNumber = z;
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public int getBadgeGravity() {
        return this.mBadgeGravity;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setGravityOffset(float f, boolean z) {
        return setGravityOffset(f, f, z);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setGravityOffset(float f, float f2, boolean z) {
        if (z) {
            f = SystemUtils.dp2px(f);
        }
        this.mGravityOffsetX = f;
        if (z) {
            f2 = SystemUtils.dp2px(f2);
        }
        this.mGravityOffsetY = f2;
        invalidate();
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public float getGravityOffsetX(boolean z) {
        return z ? SystemUtils.px2dp(this.mGravityOffsetX) : this.mGravityOffsetX;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public float getGravityOffsetY(boolean z) {
        return z ? SystemUtils.px2dp(this.mGravityOffsetY) : this.mGravityOffsetY;
    }

    private void updataListener(int i) {
        Badge.OnDragStateChangedListener onDragStateChangedListener = this.mDragStateChangedListener;
        if (onDragStateChangedListener != null) {
            onDragStateChangedListener.onDragStateChanged(i, this, this.mTargetView);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public Badge setOnDragStateChangedListener(Badge.OnDragStateChangedListener onDragStateChangedListener) {
        this.mDraggable = onDragStateChangedListener != null;
        this.mDragStateChangedListener = onDragStateChangedListener;
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.badgeView.Badge
    public PointF getDragCenter() {
        if (!this.mDraggable || !this.mDragging) {
            return null;
        }
        return this.mDragCenter;
    }

    public Badge showSinglePoint() {
        this.mBadgeNumber = -1;
        this.mBadgeText = "有";
        measureText();
        invalidate();
        return this;
    }

    public Badge hideSinglePoint() {
        this.mBadgeNumber = 0;
        this.mBadgeText = null;
        measureText();
        invalidate();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.badgeView.QBadgeView$BadgeContainer */
    /* loaded from: classes4.dex */
    public class BadgeContainer extends ViewGroup {
        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
            if (!(getParent() instanceof RelativeLayout)) {
                super.dispatchRestoreInstanceState(sparseArray);
            }
        }

        public BadgeContainer(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            View view = null;
            View view2 = null;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (!(childAt instanceof QBadgeView)) {
                    view = childAt;
                } else {
                    view2 = childAt;
                }
            }
            if (view == null) {
                super.onMeasure(i, i2);
                return;
            }
            view.measure(i, i2);
            if (view2 != null) {
                view2.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824));
            }
            setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }
}
