package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.p002v4.content.ContextCompat;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes3.dex */
public class SeparatedEditText extends EditText {
    private int blockColor;
    private Paint blockPaint;
    private int borderColor;
    private Paint borderPaint;
    private RectF borderRectF;
    private int borderWidth;
    private int boxHeight;
    private RectF boxRectF;
    private int boxWidth;
    private CharSequence contentText;
    private int corner;
    private Paint cornerPaint;
    private int cursorColor;
    private int cursorDuration;
    private Paint cursorPaint;
    private int cursorWidth;
    private int height;
    private boolean isCursorShowing;
    private int maxLength;
    private boolean password;
    private boolean showCursor;
    private int spacing;
    private TextChangedListener textChangedListener;
    private int textColor;
    private Paint textPaint;
    private Timer timer;
    private TimerTask timerTask;
    private int type;
    private int width;

    /* loaded from: classes3.dex */
    public interface TextChangedListener {
        void textChanged(CharSequence charSequence);

        void textCompleted(CharSequence charSequence);
    }

    public SeparatedEditText(Context context) {
        this(context, null);
    }

    public SeparatedEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeparatedEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeparatedEditText);
        this.password = obtainStyledAttributes.getBoolean(9, false);
        this.showCursor = obtainStyledAttributes.getBoolean(12, true);
        this.borderColor = obtainStyledAttributes.getColor(2, ContextCompat.getColor(getContext(), R.color.lignt_grey));
        this.blockColor = obtainStyledAttributes.getColor(0, ContextCompat.getColor(getContext(), R.color.colorPrimary));
        this.textColor = obtainStyledAttributes.getColor(10, ContextCompat.getColor(getContext(), R.color.lignt_grey));
        this.cursorColor = obtainStyledAttributes.getColor(5, ContextCompat.getColor(getContext(), R.color.lignt_grey));
        this.corner = (int) obtainStyledAttributes.getDimension(4, 0.0f);
        this.spacing = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.type = obtainStyledAttributes.getInt(11, 1);
        this.maxLength = obtainStyledAttributes.getInt(8, 6);
        this.cursorDuration = obtainStyledAttributes.getInt(6, 500);
        this.cursorWidth = (int) obtainStyledAttributes.getDimension(7, 2.0f);
        this.borderWidth = (int) obtainStyledAttributes.getDimension(3, 5.0f);
        obtainStyledAttributes.recycle();
        init();
    }

    public void setSpacing(int i) {
        this.spacing = i;
        postInvalidate();
    }

    public void setCorner(int i) {
        this.corner = i;
        postInvalidate();
    }

    public void setMaxLength(int i) {
        this.maxLength = i;
        postInvalidate();
    }

    public void setBorderWidth(int i) {
        this.borderWidth = i;
        postInvalidate();
    }

    public void setPassword(boolean z) {
        this.password = z;
        postInvalidate();
    }

    public void setShowCursor(boolean z) {
        this.showCursor = z;
        postInvalidate();
    }

    public void setCursorDuration(int i) {
        this.cursorDuration = i;
        postInvalidate();
    }

    public void setCursorWidth(int i) {
        this.cursorWidth = i;
        postInvalidate();
    }

    public void setCursorColor(int i) {
        this.cursorColor = i;
        postInvalidate();
    }

    public void setType(int i) {
        this.type = i;
        postInvalidate();
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
        postInvalidate();
    }

    public void setBlockColor(int i) {
        this.blockColor = i;
        postInvalidate();
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        this.textColor = i;
        postInvalidate();
    }

    private void init() {
        setCursorVisible(false);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.maxLength)});
        this.blockPaint = new Paint();
        this.blockPaint.setAntiAlias(true);
        this.blockPaint.setColor(this.blockColor);
        this.blockPaint.setStyle(Paint.Style.FILL);
        this.blockPaint.setStrokeWidth(1.0f);
        this.textPaint = new Paint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(this.textColor);
        this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.textPaint.setStrokeWidth(1.0f);
        this.borderPaint = new Paint();
        this.borderPaint.setAntiAlias(true);
        this.borderPaint.setColor(this.borderColor);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(this.borderWidth);
        this.cornerPaint = new Paint();
        this.cornerPaint.setAntiAlias(true);
        this.cornerPaint.setColor(this.borderColor);
        this.cornerPaint.setStyle(Paint.Style.STROKE);
        this.cornerPaint.setStrokeWidth(this.borderWidth);
        this.cursorPaint = new Paint();
        this.cursorPaint.setAntiAlias(true);
        this.cursorPaint.setColor(this.cursorColor);
        this.cursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.cursorPaint.setStrokeWidth(this.cursorWidth);
        this.borderRectF = new RectF();
        this.boxRectF = new RectF();
        if (this.type == 1) {
            this.spacing = 0;
        }
        initTimer();
    }

    private void initTimer() {
        this.timerTask = new TimerTask() { // from class: com.one.tomato.widget.SeparatedEditText.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                SeparatedEditText separatedEditText = SeparatedEditText.this;
                separatedEditText.isCursorShowing = !separatedEditText.isCursorShowing;
                SeparatedEditText.this.postInvalidate();
            }
        };
        this.timer = new Timer();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.width = i;
        this.height = i2;
        if (this.type == 4) {
            int i5 = this.width;
            int i6 = this.spacing;
            int i7 = this.maxLength;
            this.boxWidth = ((i5 - (i6 * (i7 - 1))) - ((this.borderWidth * i7) * 2)) / i7;
        } else {
            int i8 = this.width;
            int i9 = this.spacing;
            int i10 = this.maxLength;
            this.boxWidth = (i8 - (i9 * (i10 + 1))) / i10;
        }
        int i11 = this.height;
        this.boxHeight = i11;
        this.borderRectF.set(0.0f, 0.0f, this.width, i11);
        this.textPaint.setTextSize(this.boxWidth / 2);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        drawRect(canvas);
        drawText(canvas, this.contentText);
        drawCursor(canvas);
    }

    private void drawCursor(Canvas canvas) {
        if (this.type != 4 && !this.isCursorShowing && this.showCursor && this.contentText.length() < this.maxLength && hasFocus()) {
            int length = this.contentText.length() + 1;
            int i = this.boxWidth;
            int i2 = (this.spacing * length) + ((length - 1) * i) + (i / 2);
            int i3 = this.boxHeight;
            float f = i2;
            canvas.drawLine(f, i3 / 4, f, i3 - (i3 / 4), this.cursorPaint);
        }
    }

    private void drawRect(Canvas canvas) {
        int i = 0;
        while (i < this.maxLength) {
            RectF rectF = this.boxRectF;
            int i2 = this.spacing;
            int i3 = i + 1;
            int i4 = this.boxWidth;
            rectF.set((i2 * i3) + (i4 * i), 0.0f, (i2 * i3) + (i4 * i) + i4, this.boxHeight);
            int i5 = this.type;
            if (i5 == 2) {
                RectF rectF2 = this.boxRectF;
                int i6 = this.corner;
                canvas.drawRoundRect(rectF2, i6, i6, this.blockPaint);
            } else if (i5 == 3) {
                RectF rectF3 = this.boxRectF;
                float f = rectF3.left;
                float f2 = rectF3.bottom;
                canvas.drawLine(f, f2, rectF3.right, f2, this.borderPaint);
            } else if (i5 == 1) {
                if (i != 0 && i != this.maxLength) {
                    RectF rectF4 = this.boxRectF;
                    float f3 = rectF4.left;
                    canvas.drawLine(f3, rectF4.top, f3, rectF4.bottom, this.borderPaint);
                }
            } else if (i5 == 4) {
                int i7 = this.spacing;
                int i8 = (i7 / 2) + (i7 * i);
                int i9 = this.boxWidth;
                float f4 = i8 + (i * i9) + (i9 / 2);
                int i10 = this.boxHeight;
                canvas.drawCircle(f4, i10 / 2, Math.min(i9, i10) / 2, this.cornerPaint);
            }
            i = i3;
        }
        if (this.type == 1) {
            RectF rectF5 = this.borderRectF;
            int i11 = this.corner;
            canvas.drawRoundRect(rectF5, i11, i11, this.borderPaint);
        }
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        this.contentText = charSequence;
        invalidate();
        if (this.textChangedListener != null) {
            if (charSequence.length() == this.maxLength) {
                this.textChangedListener.textCompleted(charSequence);
            } else {
                this.textChangedListener.textChanged(charSequence);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.timer == null) {
            initTimer();
        }
        this.timer.scheduleAtFixedRate(this.timerTask, 0L, this.cursorDuration);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.timer.cancel();
        this.timer = null;
    }

    private void drawText(Canvas canvas, CharSequence charSequence) {
        int i;
        int i2 = 0;
        while (i2 < charSequence.length()) {
            int i3 = i2 + 1;
            int i4 = (this.spacing * i3) + (this.boxWidth * i2);
            int measureText = (int) (((i / 2) + i4) - (this.textPaint.measureText(String.valueOf(charSequence.charAt(i2))) / 2.0f));
            int descent = (int) (((this.boxHeight / 2) + 0) - ((this.textPaint.descent() + this.textPaint.ascent()) / 2.0f));
            int i5 = this.boxWidth;
            int i6 = i4 + (i5 / 2);
            int i7 = this.boxHeight;
            int i8 = (i7 / 2) + 0;
            int min = Math.min(i5, i7) / 6;
            if (this.type == 4) {
                int i9 = this.spacing;
                int i10 = (i9 / 2) + (i9 * i2);
                int i11 = this.boxWidth;
                float f = i10 + (i2 * i11) + (i11 / 2);
                int i12 = this.boxHeight;
                canvas.drawCircle(f, i12 / 2, (Math.min(i11, i12) / 2) + this.borderWidth, this.textPaint);
            } else if (this.password) {
                canvas.drawCircle(i6, i8, min, this.textPaint);
            } else {
                canvas.drawText(String.valueOf(charSequence.charAt(i2)), measureText, descent, this.textPaint);
            }
            i2 = i3;
        }
    }

    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public void clearText() {
        setText("");
    }
}
