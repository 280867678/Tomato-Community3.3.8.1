package com.tomatolive.library.p136ui.view.custom.luckpan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.utils.SystemUtils;
import java.text.DecimalFormat;

/* renamed from: com.tomatolive.library.ui.view.custom.luckpan.ArcProgress */
/* loaded from: classes3.dex */
public class ArcProgress extends View {
    private static final String INSTANCE_ARC_ANGLE = "arc_angle";
    private static final String INSTANCE_BOTTOM_TEXT = "bottom_text";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding";
    private static final String INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private float arcAngle;
    private String bottomText;
    private final float default_arc_angle;
    private final float default_bottom_text_size;
    private final int default_finished_color;
    private final int default_max;
    private final float default_stroke_width;
    private final float default_suffix_padding;
    private final float default_suffix_text_size;
    private final int default_text_color;
    private float default_text_size;
    protected Paint finishPaint;
    private int finishedStrokeColor;
    private int max;
    private final int min_size;
    private float progress;
    private RectF rectF;
    private float strokeWidth;
    private float suffixTextPadding;
    private float suffixTextSize;
    private int textColor;
    private float textSize;
    private Paint unFinishPaint;

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ArcProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rectF = new RectF();
        this.progress = 0.0f;
        this.default_finished_color = Color.parseColor("#24ffffff");
        this.default_text_color = Color.rgb(66, 145, 241);
        this.default_max = 100;
        this.default_arc_angle = 288.0f;
        this.default_text_size = SystemUtils.sp2px(18.0f);
        this.min_size = (int) SystemUtils.dp2px(100.0f);
        this.default_text_size = SystemUtils.sp2px(40.0f);
        this.default_suffix_text_size = SystemUtils.sp2px(15.0f);
        this.default_suffix_padding = SystemUtils.dp2px(4.0f);
        this.default_bottom_text_size = SystemUtils.sp2px(10.0f);
        this.default_stroke_width = SystemUtils.dp2px(8.0f);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.ArcProgress, i, 0);
        initByAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        initPainters();
    }

    protected void initByAttributes(TypedArray typedArray) {
        this.finishedStrokeColor = typedArray.getColor(R$styleable.ArcProgress_arc_finished_color, this.default_finished_color);
        this.textColor = typedArray.getColor(R$styleable.ArcProgress_arc_text_color, this.default_text_color);
        this.textSize = typedArray.getDimension(R$styleable.ArcProgress_arc_text_size, this.default_text_size);
        this.arcAngle = typedArray.getFloat(R$styleable.ArcProgress_arc_angle, 288.0f);
        setMax(typedArray.getInt(R$styleable.ArcProgress_arc_max, 100));
        setProgress(typedArray.getFloat(R$styleable.ArcProgress_arc_progress, 0.0f));
        this.strokeWidth = typedArray.getDimension(R$styleable.ArcProgress_arc_stroke_width, this.default_stroke_width);
        this.suffixTextSize = typedArray.getDimension(R$styleable.ArcProgress_arc_suffix_text_size, this.default_suffix_text_size);
        this.suffixTextPadding = typedArray.getDimension(R$styleable.ArcProgress_arc_suffix_text_padding, this.default_suffix_padding);
        this.bottomText = typedArray.getString(R$styleable.ArcProgress_arc_bottom_text);
    }

    protected void initPainters() {
        this.finishPaint = new TextPaint();
        this.finishPaint.setTextSize(this.textSize);
        this.finishPaint.setAntiAlias(true);
        this.finishPaint.setStrokeWidth(this.strokeWidth);
        this.finishPaint.setStyle(Paint.Style.STROKE);
        this.finishPaint.setStrokeCap(Paint.Cap.ROUND);
        this.finishPaint.setColor(this.finishedStrokeColor);
        this.unFinishPaint = new Paint();
        this.unFinishPaint.setShader(new LinearGradient(0.0f, getHeight() / 2, getWidth(), getHeight() / 2, Color.parseColor("#FF8013"), Color.parseColor("#FF4154"), Shader.TileMode.CLAMP));
        this.unFinishPaint.setAntiAlias(true);
        this.unFinishPaint.setStrokeWidth(this.strokeWidth);
        this.unFinishPaint.setStyle(Paint.Style.STROKE);
        this.unFinishPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeWidth(float f) {
        this.strokeWidth = f;
        invalidate();
    }

    public float getSuffixTextSize() {
        return this.suffixTextSize;
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float f) {
        this.progress = Float.valueOf(new DecimalFormat("#.##").format(f)).floatValue();
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int i) {
        if (i > 0) {
            this.max = i;
            invalidate();
        }
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
        invalidate();
    }

    public int getFinishedStrokeColor() {
        return this.finishedStrokeColor;
    }

    public float getArcAngle() {
        return this.arcAngle;
    }

    public float getSuffixTextPadding() {
        return this.suffixTextPadding;
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        return this.min_size;
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        return this.min_size;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(i, i2);
        int min = Math.min(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        float f = min;
        this.rectF.set((this.strokeWidth / 2.0f) + getPaddingLeft(), (this.strokeWidth / 2.0f) + getPaddingTop(), (f - (this.strokeWidth / 2.0f)) - getPaddingRight(), (f - (this.strokeWidth / 2.0f)) - getPaddingBottom());
        setMeasuredDimension(min, min);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = 270.0f - (this.arcAngle / 2.0f);
        float max = (this.progress / getMax()) * this.arcAngle;
        float f2 = this.progress == 0.0f ? 0.01f : f;
        canvas.drawArc(this.rectF, f, this.arcAngle, false, this.finishPaint);
        canvas.drawArc(this.rectF, f2, max, false, this.unFinishPaint);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding());
        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat("progress", getProgress());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle());
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            this.suffixTextSize = bundle.getFloat(INSTANCE_SUFFIX_TEXT_SIZE);
            this.suffixTextPadding = bundle.getFloat(INSTANCE_SUFFIX_TEXT_PADDING);
            this.bottomText = bundle.getString(INSTANCE_BOTTOM_TEXT);
            this.textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            this.textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getFloat("progress"));
            this.finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }
}
