package com.tomatolive.library.p136ui.view.sticker.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;
import com.tomatolive.library.p136ui.view.sticker.core.IMGText;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerX;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGStickerXText */
/* loaded from: classes3.dex */
public class IMGStickerXText extends IMGStickerX {
    private IMGText mText;
    private StaticLayout mTextLayout;
    private TextPaint mTextPaint = new TextPaint(1);

    public IMGStickerXText(IMGText iMGText) {
        this.mTextPaint.setTextSize(TypedValue.applyDimension(2, 22.0f, Resources.getSystem().getDisplayMetrics()));
        setText(iMGText);
    }

    public void setText(IMGText iMGText) {
        this.mText = iMGText;
        this.mTextPaint.setColor(iMGText.getColor());
        this.mTextLayout = new StaticLayout(iMGText.getText(), this.mTextPaint, Math.round(Resources.getSystem().getDisplayMetrics().widthPixels * 0.8f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        float f = 0.0f;
        for (int i = 0; i < this.mTextLayout.getLineCount(); i++) {
            f = Math.max(f, this.mTextLayout.getLineWidth(i));
        }
        onMeasure(f, this.mTextLayout.getHeight());
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerX
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        RectF rectF = this.mFrame;
        canvas.translate(rectF.left, rectF.top);
        this.mTextLayout.draw(canvas);
        canvas.restore();
    }
}
