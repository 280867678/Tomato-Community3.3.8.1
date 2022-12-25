package com.zzhoujay.richtext.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import java.util.List;

/* loaded from: classes4.dex */
public class ClickableImageSpan extends ImageSpan implements LongClickableSpan {
    private final List<String> imageUrls;
    private final OnImageClickListener onImageClickListener;
    private final OnImageLongClickListener onImageLongClickListener;
    private final int position;

    /* renamed from: x */
    private float f5955x;

    public ClickableImageSpan(Drawable drawable, ClickableImageSpan clickableImageSpan, OnImageClickListener onImageClickListener, OnImageLongClickListener onImageLongClickListener) {
        super(drawable, clickableImageSpan.getSource());
        this.imageUrls = clickableImageSpan.imageUrls;
        this.position = clickableImageSpan.position;
        this.onImageClickListener = onImageClickListener;
        this.onImageLongClickListener = onImageLongClickListener;
    }

    public ClickableImageSpan(Drawable drawable, List<String> list, int i, OnImageClickListener onImageClickListener, OnImageLongClickListener onImageLongClickListener) {
        super(drawable, list.get(i));
        this.imageUrls = list;
        this.position = i;
        this.onImageClickListener = onImageClickListener;
        this.onImageLongClickListener = onImageLongClickListener;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
        this.f5955x = f;
    }

    public boolean clicked(int i) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Rect bounds = drawable.getBounds();
            float f = i;
            float f2 = this.f5955x;
            return f <= ((float) bounds.right) + f2 && f >= ((float) bounds.left) + f2;
        }
        return false;
    }

    @Override // com.zzhoujay.richtext.spans.Clickable
    public void onClick(View view) {
        OnImageClickListener onImageClickListener = this.onImageClickListener;
        if (onImageClickListener != null) {
            onImageClickListener.imageClicked(this.imageUrls, this.position);
        }
    }

    @Override // com.zzhoujay.richtext.spans.LongClickable
    public boolean onLongClick(View view) {
        OnImageLongClickListener onImageLongClickListener = this.onImageLongClickListener;
        return onImageLongClickListener != null && onImageLongClickListener.imageLongClicked(this.imageUrls, this.position);
    }

    public ClickableImageSpan copy() {
        return new ClickableImageSpan(null, this.imageUrls, this.position, null, null);
    }
}
