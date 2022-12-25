package com.google.android.exoplayer2.p063ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.p002v4.view.ViewCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Util;

/* renamed from: com.google.android.exoplayer2.ui.SubtitlePainter */
/* loaded from: classes.dex */
final class SubtitlePainter {
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private int backgroundColor;
    private Rect bitmapRect;
    private float bottomPaddingFraction;
    private final float cornerRadius;
    private Bitmap cueBitmap;
    private float cueBitmapHeight;
    private float cueLine;
    private int cueLineAnchor;
    private int cueLineType;
    private float cuePosition;
    private int cuePositionAnchor;
    private float cueSize;
    private CharSequence cueText;
    private Layout.Alignment cueTextAlignment;
    private float cueTextSizePx;
    private float defaultTextSizePx;
    private int edgeColor;
    private int edgeType;
    private int foregroundColor;
    private final float outlineWidth;
    private int parentBottom;
    private int parentLeft;
    private int parentRight;
    private int parentTop;
    private final float shadowOffset;
    private final float shadowRadius;
    private final float spacingAdd;
    private final float spacingMult;
    private StaticLayout textLayout;
    private int textLeft;
    private int textPaddingX;
    private int textTop;
    private int windowColor;
    private final RectF lineBounds = new RectF();
    private final TextPaint textPaint = new TextPaint();
    private final Paint paint = new Paint();

    public SubtitlePainter(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, new int[]{16843287, 16843288}, 0, 0);
        this.spacingAdd = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.spacingMult = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
        float round = Math.round((context.getResources().getDisplayMetrics().densityDpi * 2.0f) / 160.0f);
        this.cornerRadius = round;
        this.outlineWidth = round;
        this.shadowRadius = round;
        this.shadowOffset = round;
        this.textPaint.setAntiAlias(true);
        this.textPaint.setSubpixelText(true);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Cue cue, boolean z, boolean z2, CaptionStyleCompat captionStyleCompat, float f, float f2, float f3, Canvas canvas, int i, int i2, int i3, int i4) {
        boolean z3 = cue.bitmap == null;
        int i5 = ViewCompat.MEASURED_STATE_MASK;
        if (z3) {
            if (TextUtils.isEmpty(cue.text)) {
                return;
            }
            i5 = (!cue.windowColorSet || !z) ? captionStyleCompat.windowColor : cue.windowColor;
        }
        if (areCharSequencesEqual(this.cueText, cue.text) && Util.areEqual(this.cueTextAlignment, cue.textAlignment) && this.cueBitmap == cue.bitmap && this.cueLine == cue.line && this.cueLineType == cue.lineType && Util.areEqual(Integer.valueOf(this.cueLineAnchor), Integer.valueOf(cue.lineAnchor)) && this.cuePosition == cue.position && Util.areEqual(Integer.valueOf(this.cuePositionAnchor), Integer.valueOf(cue.positionAnchor)) && this.cueSize == cue.size && this.cueBitmapHeight == cue.bitmapHeight && this.applyEmbeddedStyles == z && this.applyEmbeddedFontSizes == z2 && this.foregroundColor == captionStyleCompat.foregroundColor && this.backgroundColor == captionStyleCompat.backgroundColor && this.windowColor == i5 && this.edgeType == captionStyleCompat.edgeType && this.edgeColor == captionStyleCompat.edgeColor && Util.areEqual(this.textPaint.getTypeface(), captionStyleCompat.typeface) && this.defaultTextSizePx == f && this.cueTextSizePx == f2 && this.bottomPaddingFraction == f3 && this.parentLeft == i && this.parentTop == i2 && this.parentRight == i3 && this.parentBottom == i4) {
            drawLayout(canvas, z3);
            return;
        }
        this.cueText = cue.text;
        this.cueTextAlignment = cue.textAlignment;
        this.cueBitmap = cue.bitmap;
        this.cueLine = cue.line;
        this.cueLineType = cue.lineType;
        this.cueLineAnchor = cue.lineAnchor;
        this.cuePosition = cue.position;
        this.cuePositionAnchor = cue.positionAnchor;
        this.cueSize = cue.size;
        this.cueBitmapHeight = cue.bitmapHeight;
        this.applyEmbeddedStyles = z;
        this.applyEmbeddedFontSizes = z2;
        this.foregroundColor = captionStyleCompat.foregroundColor;
        this.backgroundColor = captionStyleCompat.backgroundColor;
        this.windowColor = i5;
        this.edgeType = captionStyleCompat.edgeType;
        this.edgeColor = captionStyleCompat.edgeColor;
        this.textPaint.setTypeface(captionStyleCompat.typeface);
        this.defaultTextSizePx = f;
        this.cueTextSizePx = f2;
        this.bottomPaddingFraction = f3;
        this.parentLeft = i;
        this.parentTop = i2;
        this.parentRight = i3;
        this.parentBottom = i4;
        if (z3) {
            setupTextLayout();
        } else {
            setupBitmapLayout();
        }
        drawLayout(canvas, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d2 A[LOOP:0: B:17:0x00d0->B:18:0x00d2, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011d  */
    /* JADX WARN: Type inference failed for: r9v4, types: [android.text.SpannableStringBuilder] */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [android.text.SpannableStringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setupTextLayout() {
        ?? spannableStringBuilder;
        String str;
        Layout.Alignment alignment;
        int lineCount;
        int i;
        int i2;
        float f;
        int i3;
        int i4;
        int i5;
        int i6;
        int round;
        int i7;
        String str2;
        int i8 = this.parentRight - this.parentLeft;
        int i9 = this.parentBottom - this.parentTop;
        this.textPaint.setTextSize(this.defaultTextSizePx);
        int i10 = (int) ((this.defaultTextSizePx * 0.125f) + 0.5f);
        int i11 = i10 * 2;
        int i12 = i8 - i11;
        float f2 = this.cueSize;
        if (f2 != Float.MIN_VALUE) {
            i12 = (int) (i12 * f2);
        }
        if (i12 <= 0) {
            Log.w("SubtitlePainter", "Skipped drawing subtitle cue (insufficient space)");
            return;
        }
        CharSequence charSequence = this.cueText;
        if (!this.applyEmbeddedStyles) {
            str2 = charSequence.toString();
        } else {
            if (!this.applyEmbeddedFontSizes) {
                spannableStringBuilder = new SpannableStringBuilder(charSequence);
                int length = spannableStringBuilder.length();
                AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannableStringBuilder.getSpans(0, length, AbsoluteSizeSpan.class);
                RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) spannableStringBuilder.getSpans(0, length, RelativeSizeSpan.class);
                for (AbsoluteSizeSpan absoluteSizeSpan : absoluteSizeSpanArr) {
                    spannableStringBuilder.removeSpan(absoluteSizeSpan);
                }
                for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
                    spannableStringBuilder.removeSpan(relativeSizeSpan);
                }
            } else {
                str2 = charSequence;
                if (this.cueTextSizePx > 0.0f) {
                    spannableStringBuilder = new SpannableStringBuilder(charSequence);
                    spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) this.cueTextSizePx), 0, spannableStringBuilder.length(), 16711680);
                }
            }
            str = spannableStringBuilder;
            alignment = this.cueTextAlignment;
            if (alignment == null) {
                alignment = Layout.Alignment.ALIGN_CENTER;
            }
            Layout.Alignment alignment2 = alignment;
            this.textLayout = new StaticLayout(str, this.textPaint, i12, alignment2, this.spacingMult, this.spacingAdd, true);
            int height = this.textLayout.getHeight();
            lineCount = this.textLayout.getLineCount();
            i2 = 0;
            for (i = 0; i < lineCount; i++) {
                i2 = Math.max((int) Math.ceil(this.textLayout.getLineWidth(i)), i2);
            }
            if (this.cueSize != Float.MIN_VALUE || i2 >= i12) {
                i12 = i2;
            }
            int i13 = i12 + i11;
            f = this.cuePosition;
            if (f == Float.MIN_VALUE) {
                int round2 = Math.round(i8 * f) + this.parentLeft;
                int i14 = this.cuePositionAnchor;
                if (i14 == 2) {
                    round2 -= i13;
                } else if (i14 == 1) {
                    round2 = ((round2 * 2) - i13) / 2;
                }
                i3 = Math.max(round2, this.parentLeft);
                i4 = Math.min(i13 + i3, this.parentRight);
            } else {
                i3 = (i8 - i13) / 2;
                i4 = i3 + i13;
            }
            i5 = i4 - i3;
            if (i5 > 0) {
                Log.w("SubtitlePainter", "Skipped drawing subtitle cue (invalid horizontal positioning)");
                return;
            }
            float f3 = this.cueLine;
            if (f3 != Float.MIN_VALUE) {
                if (this.cueLineType == 0) {
                    round = Math.round(i9 * f3);
                    i7 = this.parentTop;
                } else {
                    int lineBottom = this.textLayout.getLineBottom(0) - this.textLayout.getLineTop(0);
                    float f4 = this.cueLine;
                    if (f4 >= 0.0f) {
                        round = Math.round(f4 * lineBottom);
                        i7 = this.parentTop;
                    } else {
                        round = Math.round((f4 + 1.0f) * lineBottom);
                        i7 = this.parentBottom;
                    }
                }
                i6 = round + i7;
                int i15 = this.cueLineAnchor;
                if (i15 == 2) {
                    i6 -= height;
                } else if (i15 == 1) {
                    i6 = ((i6 * 2) - height) / 2;
                }
                int i16 = i6 + height;
                int i17 = this.parentBottom;
                if (i16 > i17) {
                    i6 = i17 - height;
                } else {
                    int i18 = this.parentTop;
                    if (i6 < i18) {
                        i6 = i18;
                    }
                }
            } else {
                i6 = (this.parentBottom - height) - ((int) (i9 * this.bottomPaddingFraction));
            }
            this.textLayout = new StaticLayout(str, this.textPaint, i5, alignment2, this.spacingMult, this.spacingAdd, true);
            this.textLeft = i3;
            this.textTop = i6;
            this.textPaddingX = i10;
            return;
        }
        str = str2;
        alignment = this.cueTextAlignment;
        if (alignment == null) {
        }
        Layout.Alignment alignment22 = alignment;
        this.textLayout = new StaticLayout(str, this.textPaint, i12, alignment22, this.spacingMult, this.spacingAdd, true);
        int height2 = this.textLayout.getHeight();
        lineCount = this.textLayout.getLineCount();
        i2 = 0;
        while (i < lineCount) {
        }
        if (this.cueSize != Float.MIN_VALUE) {
        }
        i12 = i2;
        int i132 = i12 + i11;
        f = this.cuePosition;
        if (f == Float.MIN_VALUE) {
        }
        i5 = i4 - i3;
        if (i5 > 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setupBitmapLayout() {
        float f;
        int i;
        float f2;
        int i2 = this.parentRight;
        int i3 = this.parentLeft;
        int i4 = this.parentBottom;
        int i5 = this.parentTop;
        float f3 = i2 - i3;
        float f4 = i3 + (this.cuePosition * f3);
        float f5 = i4 - i5;
        float f6 = i5 + (this.cueLine * f5);
        int round = Math.round(f3 * this.cueSize);
        float f7 = this.cueBitmapHeight;
        int round2 = f7 != Float.MIN_VALUE ? Math.round(f5 * f7) : Math.round(round * (this.cueBitmap.getHeight() / this.cueBitmap.getWidth()));
        int i6 = this.cueLineAnchor;
        if (i6 != 2) {
            if (i6 == 1) {
                f = round / 2;
            }
            int round3 = Math.round(f4);
            i = this.cuePositionAnchor;
            if (i == 2) {
                if (i == 1) {
                    f2 = round2 / 2;
                }
                int round4 = Math.round(f6);
                this.bitmapRect = new Rect(round3, round4, round + round3, round2 + round4);
            }
            f2 = round2;
            f6 -= f2;
            int round42 = Math.round(f6);
            this.bitmapRect = new Rect(round3, round42, round + round3, round2 + round42);
        }
        f = round;
        f4 -= f;
        int round32 = Math.round(f4);
        i = this.cuePositionAnchor;
        if (i == 2) {
        }
        f6 -= f2;
        int round422 = Math.round(f6);
        this.bitmapRect = new Rect(round32, round422, round + round32, round2 + round422);
    }

    private void drawLayout(Canvas canvas, boolean z) {
        if (z) {
            drawTextLayout(canvas);
        } else {
            drawBitmapLayout(canvas);
        }
    }

    private void drawTextLayout(Canvas canvas) {
        StaticLayout staticLayout = this.textLayout;
        if (staticLayout == null) {
            return;
        }
        int save = canvas.save();
        canvas.translate(this.textLeft, this.textTop);
        if (Color.alpha(this.windowColor) > 0) {
            this.paint.setColor(this.windowColor);
            canvas.drawRect(-this.textPaddingX, 0.0f, staticLayout.getWidth() + this.textPaddingX, staticLayout.getHeight(), this.paint);
        }
        if (Color.alpha(this.backgroundColor) > 0) {
            this.paint.setColor(this.backgroundColor);
            int lineCount = staticLayout.getLineCount();
            float lineTop = staticLayout.getLineTop(0);
            int i = 0;
            while (i < lineCount) {
                float lineLeft = staticLayout.getLineLeft(i);
                float lineRight = staticLayout.getLineRight(i);
                RectF rectF = this.lineBounds;
                int i2 = this.textPaddingX;
                rectF.left = lineLeft - i2;
                rectF.right = i2 + lineRight;
                rectF.top = lineTop;
                rectF.bottom = staticLayout.getLineBottom(i);
                RectF rectF2 = this.lineBounds;
                float f = rectF2.bottom;
                if (lineRight - lineLeft > 0.0f) {
                    float f2 = this.cornerRadius;
                    canvas.drawRoundRect(rectF2, f2, f2, this.paint);
                }
                i++;
                lineTop = f;
            }
        }
        int i3 = this.edgeType;
        boolean z = true;
        if (i3 == 1) {
            this.textPaint.setStrokeJoin(Paint.Join.ROUND);
            this.textPaint.setStrokeWidth(this.outlineWidth);
            this.textPaint.setColor(this.edgeColor);
            this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            staticLayout.draw(canvas);
        } else if (i3 == 2) {
            TextPaint textPaint = this.textPaint;
            float f3 = this.shadowRadius;
            float f4 = this.shadowOffset;
            textPaint.setShadowLayer(f3, f4, f4, this.edgeColor);
        } else if (i3 == 3 || i3 == 4) {
            if (this.edgeType != 3) {
                z = false;
            }
            int i4 = -1;
            int i5 = z ? -1 : this.edgeColor;
            if (z) {
                i4 = this.edgeColor;
            }
            float f5 = this.shadowRadius / 2.0f;
            this.textPaint.setColor(this.foregroundColor);
            this.textPaint.setStyle(Paint.Style.FILL);
            float f6 = -f5;
            this.textPaint.setShadowLayer(this.shadowRadius, f6, f6, i5);
            staticLayout.draw(canvas);
            this.textPaint.setShadowLayer(this.shadowRadius, f5, f5, i4);
        }
        this.textPaint.setColor(this.foregroundColor);
        this.textPaint.setStyle(Paint.Style.FILL);
        staticLayout.draw(canvas);
        this.textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas.restoreToCount(save);
    }

    private void drawBitmapLayout(Canvas canvas) {
        canvas.drawBitmap(this.cueBitmap, (Rect) null, this.bitmapRect, (Paint) null);
    }

    private static boolean areCharSequencesEqual(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence == charSequence2 || (charSequence != null && charSequence.equals(charSequence2));
    }
}
