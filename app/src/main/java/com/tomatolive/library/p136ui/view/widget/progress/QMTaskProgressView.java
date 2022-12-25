package com.tomatolive.library.p136ui.view.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$string;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.widget.progress.QMTaskProgressView */
/* loaded from: classes4.dex */
public class QMTaskProgressView extends View {
    private boolean enableGradient;
    private float mAboveTextSize;
    private RectF mBackgroundBounds;
    private int[] mBackgroundColor;
    private Paint mBackgroundPaint;
    private int mBackgroundSecondColor;
    private float mButtonRadius;
    private CharSequence mCurrentText;
    private int mMaxProgress;
    private int mMinProgress;
    private float mProgress;
    private LinearGradient mProgressBgGradient;
    private float mProgressPercent;
    private int mTextColor;
    private Paint mTextPaint;
    private int offset;
    private int originBgColor;

    public QMTaskProgressView(Context context) {
        this(context, null);
    }

    public QMTaskProgressView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMTaskProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProgress = -1.0f;
        this.mAboveTextSize = ConvertUtils.sp2px(10.0f);
        this.offset = ConvertUtils.dp2px(2.0f);
        initAttrs(context, attributeSet);
        init();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.QMTaskProgressView);
        this.originBgColor = obtainStyledAttributes.getColor(R$styleable.QMTaskProgressView_qm_background_origin_color, -1);
        int color = obtainStyledAttributes.getColor(R$styleable.QMTaskProgressView_qm_background_color, Color.parseColor("#6699ff"));
        int color2 = obtainStyledAttributes.getColor(R$styleable.QMTaskProgressView_qm_background_gradient_color, Color.parseColor("#6699ff"));
        initGradientColor(color, color);
        this.mBackgroundSecondColor = obtainStyledAttributes.getColor(R$styleable.QMTaskProgressView_qm_background_second_color, -3355444);
        this.mButtonRadius = obtainStyledAttributes.getFloat(R$styleable.QMTaskProgressView_qm_radius, ConvertUtils.dp2px(10.0f));
        this.mAboveTextSize = obtainStyledAttributes.getFloat(R$styleable.QMTaskProgressView_qm_text_size, ConvertUtils.sp2px(10.0f));
        this.mTextColor = obtainStyledAttributes.getColor(R$styleable.QMTaskProgressView_qm_text_color, -1);
        this.enableGradient = obtainStyledAttributes.getBoolean(R$styleable.QMTaskProgressView_qm_enable_gradient, false);
        if (this.enableGradient) {
            initGradientColor(color, color2);
        }
        obtainStyledAttributes.recycle();
    }

    private int[] initGradientColor(int i, int i2) {
        this.mBackgroundColor = new int[2];
        int[] iArr = this.mBackgroundColor;
        iArr[0] = i;
        iArr[1] = i2;
        return iArr;
    }

    private void init() {
        this.mMaxProgress = 100;
        this.mMinProgress = 0;
        this.mProgress = 0.0f;
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setAntiAlias(true);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(this.mAboveTextSize);
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, this.mTextPaint);
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawTextAbove(canvas);
    }

    private void drawTextAbove(Canvas canvas) {
        float height = (canvas.getHeight() / 2) - ((this.mTextPaint.descent() / 2.0f) + (this.mTextPaint.ascent() / 2.0f));
        if (this.mCurrentText == null) {
            this.mCurrentText = "";
        }
        float measureText = this.mTextPaint.measureText(this.mCurrentText.toString());
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mAboveTextSize);
        canvas.drawText(this.mCurrentText.toString(), (getMeasuredWidth() - measureText) / 2.0f, height, this.mTextPaint);
    }

    private void drawBackground(Canvas canvas) {
        this.mBackgroundBounds = new RectF();
        if (this.mButtonRadius == 0.0f) {
            this.mButtonRadius = getMeasuredHeight() / 2;
        }
        RectF rectF = this.mBackgroundBounds;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = getMeasuredWidth();
        this.mBackgroundBounds.bottom = getMeasuredHeight();
        this.mBackgroundPaint.setShader(null);
        this.mBackgroundPaint.setColor(this.originBgColor);
        RectF rectF2 = this.mBackgroundBounds;
        float f = this.mButtonRadius;
        canvas.drawRoundRect(rectF2, f, f, this.mBackgroundPaint);
        RectF rectF3 = this.mBackgroundBounds;
        int i = this.offset;
        rectF3.set(i, i, getMeasuredWidth() - this.offset, getMeasuredHeight() - this.offset);
        if (this.enableGradient) {
            this.mProgressPercent = this.mProgress / (this.mMaxProgress + 0.0f);
            int[] iArr = this.mBackgroundColor;
            int[] iArr2 = {iArr[0], iArr[1], this.mBackgroundSecondColor};
            float f2 = this.mProgressPercent;
            this.mProgressBgGradient = new LinearGradient(0.0f, 0.0f, getMeasuredWidth(), 0.0f, iArr2, new float[]{0.0f, f2, f2 + 0.001f}, Shader.TileMode.CLAMP);
            this.mBackgroundPaint.setShader(this.mProgressBgGradient);
        } else {
            this.mProgressPercent = this.mProgress / (this.mMaxProgress + 0.0f);
            float measuredWidth = getMeasuredWidth();
            int[] iArr3 = {this.mBackgroundColor[0], this.mBackgroundSecondColor};
            float f3 = this.mProgressPercent;
            this.mProgressBgGradient = new LinearGradient(0.0f, 0.0f, measuredWidth, 0.0f, iArr3, new float[]{f3, f3 + 0.001f}, Shader.TileMode.CLAMP);
            this.mBackgroundPaint.setColor(this.mBackgroundColor[0]);
            this.mBackgroundPaint.setShader(this.mProgressBgGradient);
        }
        RectF rectF4 = this.mBackgroundBounds;
        float f4 = this.mButtonRadius;
        canvas.drawRoundRect(rectF4, f4, f4, this.mBackgroundPaint);
    }

    public void setMaxProgress(int i) {
        this.mMaxProgress = i;
    }

    public void setProgressValue(int i) {
        int i2 = this.mMinProgress;
        if (i < i2) {
            i = i2;
        }
        this.mProgress = i;
        this.mCurrentText = getResources().getString(R$string.fq_qm_task_progress_text, Integer.valueOf(i), Integer.valueOf(this.mMaxProgress));
        invalidate();
    }
}
