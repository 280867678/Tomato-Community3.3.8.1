package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$attr;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$mipmap;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.widget.CheckView */
/* loaded from: classes2.dex */
public class CheckView extends View {
    private static final float BG_RADIUS = 11.0f;
    private static final int CONTENT_SIZE = 16;
    private static final float SHADOW_WIDTH = 6.0f;
    private static final int SIZE = 48;
    private static final float STROKE_RADIUS = 11.5f;
    private static final float STROKE_WIDTH = 3.0f;
    public static final int UNCHECKED = Integer.MIN_VALUE;
    private Paint mBackgroundPaint;
    private Drawable mCheckDrawable;
    private Rect mCheckRect;
    private boolean mChecked;
    private int mCheckedNum;
    private boolean mCountable;
    private float mDensity;
    private boolean mEnabled = true;
    private Paint mShadowPaint;
    private Paint mStrokePaint;
    private TextPaint mTextPaint;

    public CheckView(Context context) {
        super(context);
        init(context);
    }

    public CheckView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CheckView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (this.mDensity * 48.0f), 1073741824);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    private void init(Context context) {
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mStrokePaint = new Paint();
        this.mStrokePaint.setAntiAlias(true);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.mStrokePaint.setStrokeWidth(this.mDensity * 3.0f);
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R$attr.sdk_item_checkCircle_borderColor});
        int color = obtainStyledAttributes.getColor(0, ResourcesCompat.getColor(getResources(), R$color.zhihu_item_checkCircle_borderColor, getContext().getTheme()));
        obtainStyledAttributes.recycle();
        this.mStrokePaint.setColor(color);
        this.mCheckDrawable = ResourcesCompat.getDrawable(context.getResources(), R$mipmap.ic_check_white_18dp, context.getTheme());
    }

    public void setChecked(boolean z) {
        if (this.mCountable) {
            throw new IllegalStateException("CheckView is countable, call setCheckedNum() instead.");
        }
        this.mChecked = z;
        invalidate();
    }

    public void setCountable(boolean z) {
        this.mCountable = z;
    }

    public void setCheckedNum(int i) {
        if (this.mCountable) {
            if (i != Integer.MIN_VALUE && i <= 0) {
                throw new IllegalArgumentException("checked num can't be negative.");
            }
            this.mCheckedNum = i;
            invalidate();
            return;
        }
        throw new IllegalStateException("CheckView is not countable, call setChecked() instead.");
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initShadowPaint();
        if (this.mCountable) {
            if (this.mCheckedNum != Integer.MIN_VALUE) {
                float f = this.mDensity;
                canvas.drawCircle((f * 48.0f) / 2.0f, (f * 48.0f) / 2.0f, f * 19.0f, this.mShadowPaint);
                float f2 = this.mDensity;
                canvas.drawCircle((f2 * 48.0f) / 2.0f, (f2 * 48.0f) / 2.0f, f2 * STROKE_RADIUS, this.mStrokePaint);
                initBackgroundPaint();
                float f3 = this.mDensity;
                canvas.drawCircle((f3 * 48.0f) / 2.0f, (48.0f * f3) / 2.0f, f3 * BG_RADIUS, this.mBackgroundPaint);
                initTextPaint();
                String valueOf = String.valueOf(this.mCheckedNum);
                canvas.drawText(valueOf, ((int) (canvas.getWidth() - this.mTextPaint.measureText(valueOf))) / 2, ((int) ((canvas.getHeight() - this.mTextPaint.descent()) - this.mTextPaint.ascent())) / 2, this.mTextPaint);
            }
        } else if (this.mChecked) {
            float f4 = this.mDensity;
            canvas.drawCircle((f4 * 48.0f) / 2.0f, (f4 * 48.0f) / 2.0f, f4 * 19.0f, this.mShadowPaint);
            float f5 = this.mDensity;
            canvas.drawCircle((f5 * 48.0f) / 2.0f, (f5 * 48.0f) / 2.0f, f5 * STROKE_RADIUS, this.mStrokePaint);
            initBackgroundPaint();
            float f6 = this.mDensity;
            canvas.drawCircle((f6 * 48.0f) / 2.0f, (48.0f * f6) / 2.0f, f6 * BG_RADIUS, this.mBackgroundPaint);
            this.mCheckDrawable.setBounds(getCheckRect());
            this.mCheckDrawable.draw(canvas);
        }
        setAlpha(this.mEnabled ? 1.0f : 0.5f);
    }

    private void initShadowPaint() {
        if (this.mShadowPaint == null) {
            this.mShadowPaint = new Paint();
            this.mShadowPaint.setAntiAlias(true);
            Paint paint = this.mShadowPaint;
            float f = this.mDensity;
            paint.setShader(new RadialGradient((f * 48.0f) / 2.0f, (48.0f * f) / 2.0f, 19.0f * f, new int[]{Color.parseColor("#00000000"), Color.parseColor("#0D000000"), Color.parseColor("#0D000000"), Color.parseColor("#00000000")}, new float[]{0.21052632f, 0.5263158f, 0.68421054f, 1.0f}, Shader.TileMode.CLAMP));
        }
    }

    private void initBackgroundPaint() {
        if (this.mBackgroundPaint == null) {
            this.mBackgroundPaint = new Paint();
            this.mBackgroundPaint.setAntiAlias(true);
            this.mBackgroundPaint.setStyle(Paint.Style.FILL);
            TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R$attr.sdk_item_checkCircle_backgroundColor});
            int color = obtainStyledAttributes.getColor(0, ResourcesCompat.getColor(getResources(), R$color.zhihu_item_checkCircle_backgroundColor, getContext().getTheme()));
            obtainStyledAttributes.recycle();
            this.mBackgroundPaint.setColor(color);
        }
    }

    private void initTextPaint() {
        if (this.mTextPaint == null) {
            this.mTextPaint = new TextPaint();
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
            this.mTextPaint.setTextSize(this.mDensity * 12.0f);
        }
    }

    private Rect getCheckRect() {
        if (this.mCheckRect == null) {
            float f = this.mDensity;
            int i = (int) (((f * 48.0f) / 2.0f) - ((16.0f * f) / 2.0f));
            float f2 = i;
            this.mCheckRect = new Rect(i, i, (int) ((f * 48.0f) - f2), (int) ((f * 48.0f) - f2));
        }
        return this.mCheckRect;
    }
}
