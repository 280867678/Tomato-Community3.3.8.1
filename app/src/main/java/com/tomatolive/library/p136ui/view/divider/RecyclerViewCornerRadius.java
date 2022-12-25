package com.tomatolive.library.p136ui.view.divider;

import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.p005v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

/* renamed from: com.tomatolive.library.ui.view.divider.RecyclerViewCornerRadius */
/* loaded from: classes3.dex */
public class RecyclerViewCornerRadius extends RecyclerView.ItemDecoration {
    public static final String TAG = "RecyclerViewCornerRadius";
    private Path path;
    private RectF rectF;
    private int topLeftRadius = 0;
    private int topRightRadius = 0;
    private int bottomLeftRadius = 0;
    private int bottomRightRadius = 0;

    public void setRecyclerViewRoundRect(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.divider.RecyclerViewCornerRadius.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                RecyclerViewCornerRadius.this.rectF = new RectF(0.0f, 0.0f, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());
                RecyclerViewCornerRadius.this.path = new Path();
                RecyclerViewCornerRadius.this.path.reset();
                RecyclerViewCornerRadius.this.path.addRoundRect(RecyclerViewCornerRadius.this.rectF, new float[]{RecyclerViewCornerRadius.this.topLeftRadius, RecyclerViewCornerRadius.this.topLeftRadius, RecyclerViewCornerRadius.this.topRightRadius, RecyclerViewCornerRadius.this.topRightRadius, RecyclerViewCornerRadius.this.bottomLeftRadius, RecyclerViewCornerRadius.this.bottomLeftRadius, 0.0f, 0.0f}, Path.Direction.CCW);
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setRecyclerViewAllRoundRect(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.divider.RecyclerViewCornerRadius.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                RecyclerViewCornerRadius.this.rectF = new RectF(0.0f, 0.0f, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());
                RecyclerViewCornerRadius.this.path = new Path();
                RecyclerViewCornerRadius.this.path.reset();
                RecyclerViewCornerRadius.this.path.addRoundRect(RecyclerViewCornerRadius.this.rectF, new float[]{RecyclerViewCornerRadius.this.topLeftRadius, RecyclerViewCornerRadius.this.topLeftRadius, RecyclerViewCornerRadius.this.topRightRadius, RecyclerViewCornerRadius.this.topRightRadius, RecyclerViewCornerRadius.this.bottomLeftRadius, RecyclerViewCornerRadius.this.bottomLeftRadius, RecyclerViewCornerRadius.this.bottomRightRadius, RecyclerViewCornerRadius.this.bottomRightRadius}, Path.Direction.CCW);
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setCornerRadius(int i) {
        this.topLeftRadius = i;
        this.topRightRadius = i;
        this.bottomLeftRadius = i;
        this.bottomRightRadius = i;
    }

    public void setCornerRadius(int i, int i2, int i3, int i4) {
        this.topLeftRadius = i;
        this.topRightRadius = i2;
        this.bottomLeftRadius = i3;
        this.bottomRightRadius = i4;
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        canvas.clipRect(this.rectF);
        if (Build.VERSION.SDK_INT >= 28) {
            canvas.clipPath(this.path);
        } else {
            canvas.clipPath(this.path, Region.Op.REPLACE);
        }
    }
}
