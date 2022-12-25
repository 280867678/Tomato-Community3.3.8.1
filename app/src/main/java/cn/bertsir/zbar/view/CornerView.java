package cn.bertsir.zbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$styleable;

/* loaded from: classes2.dex */
public class CornerView extends View {
    private static final int LEFT_BOTTOM = 1;
    private static final int LEFT_TOP = 0;
    private static final int RIGHT_BOTTOM = 3;
    private static final int RIGHT_TOP = 2;
    private static final String TAG = "CornerView";
    private Canvas canvas;
    private int cornerColor;
    private int cornerGravity;
    private int cornerWidth;
    private int height;
    private Paint paint;
    private int width;

    public CornerView(Context context) {
        super(context, null);
        this.width = 0;
        this.height = 0;
    }

    public CornerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.width = 0;
        this.height = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SDK_CornerView);
        this.cornerColor = obtainStyledAttributes.getColor(R$styleable.SDK_CornerView_corner_color, getResources().getColor(R$color.web_sdk_common_color));
        this.cornerWidth = (int) obtainStyledAttributes.getDimension(R$styleable.SDK_CornerView_corner_width, 10.0f);
        this.cornerGravity = obtainStyledAttributes.getInt(R$styleable.SDK_CornerView_corner_gravity, 1);
        obtainStyledAttributes.recycle();
        this.paint = new Paint();
        this.canvas = new Canvas();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeWidth(this.cornerWidth);
        this.paint.setColor(this.cornerColor);
        this.paint.setAntiAlias(true);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.cornerGravity;
        if (i == 0) {
            canvas.drawLine(0.0f, 0.0f, this.width, 0.0f, this.paint);
            canvas.drawLine(0.0f, 0.0f, 0.0f, this.height, this.paint);
        } else if (i == 1) {
            canvas.drawLine(0.0f, 0.0f, 0.0f, this.height, this.paint);
            int i2 = this.height;
            canvas.drawLine(0.0f, i2, this.width, i2, this.paint);
        } else if (i == 2) {
            canvas.drawLine(0.0f, 0.0f, this.width, 0.0f, this.paint);
            int i3 = this.width;
            canvas.drawLine(i3, 0.0f, i3, this.height, this.paint);
        } else if (i != 3) {
        } else {
            int i4 = this.width;
            canvas.drawLine(i4, 0.0f, i4, this.height, this.paint);
            int i5 = this.height;
            canvas.drawLine(0.0f, i5, this.width, i5, this.paint);
        }
    }

    public void setColor(int i) {
        this.cornerColor = i;
        this.paint.setColor(this.cornerColor);
        invalidate();
    }

    public void setLineWidth(int i) {
        this.cornerWidth = dip2px(i);
        this.paint.setStrokeWidth(this.cornerWidth);
        invalidate();
    }

    public int dip2px(int i) {
        return (int) ((i * getContext().getResources().getDisplayMetrics().density) + 0.5d);
    }
}
