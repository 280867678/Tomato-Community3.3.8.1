package com.google.android.exoplayer2.p063ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.SubtitleView */
/* loaded from: classes3.dex */
public final class SubtitleView extends View implements TextOutput {
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private final List<SubtitlePainter> painters;
    private CaptionStyleCompat style;
    private float textSize;
    private int textSizeType;

    private float resolveTextSize(int i, float f, int i2, int i3) {
        float f2;
        if (i == 0) {
            f2 = i3;
        } else if (i != 1) {
            if (i == 2) {
                return f;
            }
            return Float.MIN_VALUE;
        } else {
            f2 = i2;
        }
        return f * f2;
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.painters = new ArrayList();
        this.textSizeType = 0;
        this.textSize = 0.0533f;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        this.style = CaptionStyleCompat.DEFAULT;
        this.bottomPaddingFraction = 0.08f;
    }

    @Override // com.google.android.exoplayer2.text.TextOutput
    public void onCues(List<Cue> list) {
        setCues(list);
    }

    public void setCues(@Nullable List<Cue> list) {
        if (this.cues == list) {
            return;
        }
        this.cues = list;
        int size = list == null ? 0 : list.size();
        while (this.painters.size() < size) {
            this.painters.add(new SubtitlePainter(getContext()));
        }
        invalidate();
    }

    public void setFixedTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        setTextSize(2, TypedValue.applyDimension(i, f, resources.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(((Util.SDK_INT < 19 || isInEditMode()) ? 1.0f : getUserCaptionFontScaleV19()) * 0.0533f);
    }

    public void setFractionalTextSize(float f) {
        setFractionalTextSize(f, false);
    }

    public void setFractionalTextSize(float f, boolean z) {
        setTextSize(z ? 1 : 0, f);
    }

    private void setTextSize(int i, float f) {
        if (this.textSizeType == i && this.textSize == f) {
            return;
        }
        this.textSizeType = i;
        this.textSize = f;
        invalidate();
    }

    public void setApplyEmbeddedStyles(boolean z) {
        if (this.applyEmbeddedStyles == z && this.applyEmbeddedFontSizes == z) {
            return;
        }
        this.applyEmbeddedStyles = z;
        this.applyEmbeddedFontSizes = z;
        invalidate();
    }

    public void setApplyEmbeddedFontSizes(boolean z) {
        if (this.applyEmbeddedFontSizes == z) {
            return;
        }
        this.applyEmbeddedFontSizes = z;
        invalidate();
    }

    public void setUserDefaultStyle() {
        setStyle((Util.SDK_INT < 19 || isInEditMode()) ? CaptionStyleCompat.DEFAULT : getUserCaptionStyleV19());
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        if (this.style == captionStyleCompat) {
            return;
        }
        this.style = captionStyleCompat;
        invalidate();
    }

    public void setBottomPaddingFraction(float f) {
        if (this.bottomPaddingFraction == f) {
            return;
        }
        this.bottomPaddingFraction = f;
        invalidate();
    }

    @Override // android.view.View
    public void dispatchDraw(Canvas canvas) {
        List<Cue> list = this.cues;
        int i = 0;
        int size = list == null ? 0 : list.size();
        int top2 = getTop();
        int bottom = getBottom();
        int left = getLeft() + getPaddingLeft();
        int paddingTop = getPaddingTop() + top2;
        int right = getRight() - getPaddingRight();
        int paddingBottom = bottom - getPaddingBottom();
        if (paddingBottom <= paddingTop || right <= left) {
            return;
        }
        int i2 = bottom - top2;
        int i3 = paddingBottom - paddingTop;
        float resolveTextSize = resolveTextSize(this.textSizeType, this.textSize, i2, i3);
        if (resolveTextSize <= 0.0f) {
            return;
        }
        while (i < size) {
            Cue cue = this.cues.get(i);
            int i4 = paddingBottom;
            int i5 = right;
            this.painters.get(i).draw(cue, this.applyEmbeddedStyles, this.applyEmbeddedFontSizes, this.style, resolveTextSize, resolveCueTextSize(cue, i2, i3), this.bottomPaddingFraction, canvas, left, paddingTop, i5, i4);
            i++;
            paddingBottom = i4;
            right = i5;
        }
    }

    private float resolveCueTextSize(Cue cue, int i, int i2) {
        int i3 = cue.textSizeType;
        if (i3 != Integer.MIN_VALUE) {
            float f = cue.textSize;
            if (f != Float.MIN_VALUE) {
                return Math.max(resolveTextSize(i3, f, i, i2), 0.0f);
            }
        }
        return 0.0f;
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        return CaptionStyleCompat.createFromCaptionStyle(((CaptioningManager) getContext().getSystemService("captioning")).getUserStyle());
    }
}
