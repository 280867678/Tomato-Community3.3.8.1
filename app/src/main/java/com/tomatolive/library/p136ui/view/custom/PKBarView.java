package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.PKBarView */
/* loaded from: classes3.dex */
public class PKBarView extends View {
    private float mTextOffset;
    private float progress = 0.5f;
    private final Drawable mRedDrawable = getResources().getDrawable(R$drawable.fq_pk_bar_red);
    private final Drawable mBlueDrawable = getResources().getDrawable(R$drawable.fq_pk_bar_blue);
    private Bitmap mCursorBitmap = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_pk_progress_bar_cursor);
    private Rect mCursorRect = new Rect();
    private Paint mTextPaint = new Paint();
    private int mMinWidth = ConvertUtils.dp2px(8.0f);
    private int mTextMargin = ConvertUtils.dp2px(6.0f);
    private long myNum = 0;
    private long otherNum = 0;
    private final Paint.FontMetrics mFontMetrics = this.mTextPaint.getFontMetrics();
    private StringBuilder stringBuilder = new StringBuilder();

    public PKBarView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setTextSize(ConvertUtils.dp2px(14.0f));
        this.mTextPaint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = this.mFontMetrics;
        this.mTextOffset = (fontMetrics.bottom - fontMetrics.top) / 2.0f;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        int width = (getWidth() - paddingStart) - getPaddingEnd();
        int i = (int) (width * this.progress);
        int i2 = this.mMinWidth;
        if (i < i2) {
            i = i2;
        } else if (i > width - i2) {
            i = width - i2;
        }
        int i3 = i + paddingStart;
        this.mCursorRect.set(i3 - (this.mCursorBitmap.getWidth() / 2), paddingTop, (this.mCursorBitmap.getWidth() / 2) + i3, this.mCursorBitmap.getHeight() + paddingTop);
        int height = paddingTop + (this.mCursorRect.height() / 4);
        int height2 = (this.mCursorBitmap.getHeight() / 2) + height;
        this.mRedDrawable.setBounds(paddingStart, height, i3, height2);
        this.mRedDrawable.draw(canvas);
        this.mBlueDrawable.setBounds(i3, height, getWidth() - getPaddingEnd(), height2);
        this.mBlueDrawable.draw(canvas);
        canvas.drawBitmap(this.mCursorBitmap, (Rect) null, this.mCursorRect, (Paint) null);
        float f = (((height + height2) / 2) + this.mTextOffset) - this.mFontMetrics.bottom;
        StringBuilder sb = this.stringBuilder;
        sb.delete(0, sb.length());
        StringBuilder sb2 = this.stringBuilder;
        sb2.append(getContext().getString(R$string.fq_pk_our_side));
        sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb2.append(this.myNum);
        canvas.drawText(sb2.toString(), paddingStart + this.mTextMargin, f, this.mTextPaint);
        StringBuilder sb3 = this.stringBuilder;
        sb3.delete(0, sb3.length());
        StringBuilder sb4 = this.stringBuilder;
        sb4.append(this.otherNum);
        sb4.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb4.append(getContext().getString(R$string.fq_pk_other_side));
        canvas.drawText(sb4.toString(), ((getWidth() - getPaddingEnd()) - this.mTextPaint.measureText(this.stringBuilder.toString())) - this.mTextMargin, f, this.mTextPaint);
    }

    public void updatePress(long j, long j2) {
        this.myNum = j;
        this.otherNum = j2;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i == 0 && j2 == 0) {
            this.progress = 0.5f;
        } else if (i == 0) {
            this.progress = 0.0f;
        } else if (j2 == 0) {
            this.progress = 1.0f;
        } else {
            this.progress = (((float) j) * 1.0f) / ((float) (j + j2));
        }
        invalidate();
    }
}
