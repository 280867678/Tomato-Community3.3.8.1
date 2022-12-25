package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewCompat;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;

/* renamed from: com.tomatolive.library.ui.view.widget.FadingRecyclerView */
/* loaded from: classes4.dex */
public class FadingRecyclerView extends RecyclerView {
    private static final String TAG = "FadingRecyclerView";
    private int height;
    private Paint paint;
    private int spanPixel = 50;
    private int width;

    public FadingRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void setSpanPixel(int i) {
        this.spanPixel = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.RecyclerView, android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.height = i2;
        this.width = i;
        int i5 = this.height;
        this.paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, i5 / 2.0f, new int[]{0, ViewCompat.MEASURED_STATE_MASK, ViewCompat.MEASURED_STATE_MASK}, new float[]{0.0f, this.spanPixel / (i5 / 2.0f), 1.0f}, Shader.TileMode.MIRROR));
    }

    @Override // android.support.p005v7.widget.RecyclerView, android.view.View
    public void draw(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, this.width, this.height, null, 31);
        super.draw(canvas);
        canvas.drawRect(0.0f, 0.0f, this.width, this.height, this.paint);
        canvas.restore();
    }
}
