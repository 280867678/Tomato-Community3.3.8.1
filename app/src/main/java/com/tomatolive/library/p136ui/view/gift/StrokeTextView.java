package com.tomatolive.library.p136ui.view.gift;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.p005v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.gift.StrokeTextView */
/* loaded from: classes3.dex */
public class StrokeTextView extends AppCompatTextView {
    private static final int DEFAULT_BORDER_COLOR = -1;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private int mBorderColor;
    private int mBorderWidth;
    private TextView outlineTextView;

    public StrokeTextView(Context context) {
        super(context);
        this.outlineTextView = null;
        this.mBorderColor = -1;
        this.mBorderWidth = 0;
        this.outlineTextView = new TextView(context);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.outlineTextView = null;
        this.mBorderColor = -1;
        this.mBorderWidth = 0;
        this.outlineTextView = new TextView(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StrokeTextView, i, 0);
        this.mBorderWidth = obtainStyledAttributes.getDimensionPixelSize(R$styleable.StrokeTextView_strokeWidth, 0);
        this.mBorderColor = obtainStyledAttributes.getColor(R$styleable.StrokeTextView_strokeColor, -1);
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        TextPaint paint = this.outlineTextView.getPaint();
        paint.setStrokeWidth(this.mBorderWidth);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        this.outlineTextView.setTextColor(this.mBorderColor);
        this.outlineTextView.setGravity(getGravity());
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        this.outlineTextView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        CharSequence text = this.outlineTextView.getText();
        if (text == null || !text.equals(getText())) {
            this.outlineTextView.setText(getText());
            postInvalidate();
        }
        super.onMeasure(i, i2);
        this.outlineTextView.measure(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.outlineTextView.layout(i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }
}
