package com.zzhoujay.markdown.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CodeBlockSpan extends ReplacementSpan implements LineHeightSpan {
    private int baseLine;
    private Drawable drawable;
    private int lineHeight;
    private List<Pair<Integer, Integer>> lines;

    /* renamed from: ls */
    private CharSequence[] f5953ls;
    private int width;

    public CodeBlockSpan(int i, int i2, CharSequence... charSequenceArr) {
        this.width = i;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i2);
        gradientDrawable.setCornerRadius(10.0f);
        this.drawable = gradientDrawable;
        this.f5953ls = charSequenceArr;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        CharSequence[] charSequenceArr;
        if (fontMetricsInt != null && this.lines == null) {
            this.lines = new ArrayList();
            for (CharSequence charSequence2 : this.f5953ls) {
                this.lines.addAll(measureTextLine(charSequence2, 0, charSequence2.length(), paint));
            }
        }
        return this.width;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        int i6 = (int) f;
        this.drawable.setBounds(i6, i3, this.width + i6, i5);
        this.drawable.draw(canvas);
        float f2 = f + 30.0f;
        int i7 = this.baseLine + (this.lineHeight / 2) + i3;
        int i8 = 0;
        for (Pair<Integer, Integer> pair : this.lines) {
            CharSequence charSequence2 = this.f5953ls[i8];
            canvas.drawText(charSequence2, ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue(), f2 + 30.0f, i7, paint);
            if (((Integer) pair.second).intValue() >= charSequence2.length()) {
                i8++;
            }
            i7 += this.lineHeight;
        }
    }

    private int getTextInLineLen(CharSequence charSequence, int i, int i2, Paint paint) {
        int i3 = i;
        while (paint.measureText(charSequence, i, i3) < this.width - 60 && (i3 = i3 + 1) <= i2) {
        }
        return i3 - 1;
    }

    private int getTextInLineLenInRange(CharSequence charSequence, int i, int i2, int i3, int i4, Paint paint) {
        if (i3 > i2) {
            return i2;
        }
        while (paint.measureText(charSequence, i, i3) < this.width - 60 && (i3 = i3 + 1) <= i2) {
            if (i3 > i4) {
                break;
            }
        }
        return i3 - 1;
    }

    private List<Pair<Integer, Integer>> measureTextLine(CharSequence charSequence, int i, int i2, Paint paint) {
        ArrayList arrayList = new ArrayList();
        int textInLineLen = getTextInLineLen(charSequence, i, i2, paint);
        arrayList.add(new Pair(Integer.valueOf(i), Integer.valueOf(textInLineLen)));
        int i3 = textInLineLen;
        while (textInLineLen < i2) {
            int i4 = i3 + textInLineLen;
            int textInLineLenInRange = getTextInLineLenInRange(charSequence, textInLineLen, i2, i4 - 4, i4 + 4, paint);
            int i5 = textInLineLenInRange - textInLineLen;
            arrayList.add(new Pair(Integer.valueOf(textInLineLen), Integer.valueOf(textInLineLenInRange)));
            textInLineLen = textInLineLenInRange;
            i3 = i5;
        }
        return arrayList;
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        int size = this.lines.size();
        int i5 = fontMetricsInt.bottom;
        int i6 = fontMetricsInt.top;
        this.lineHeight = i5 - i6;
        this.baseLine = -i6;
        fontMetricsInt.ascent = i6;
        fontMetricsInt.bottom = i5 + (size * this.lineHeight);
        fontMetricsInt.descent = fontMetricsInt.bottom;
    }
}
