package com.zzhoujay.richtext.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class GifDrawable extends Drawable {
    private int height;
    private Movie movie;
    private boolean running;
    private long start;
    private TextView textView;
    private int width;
    private float scaleY = 1.0f;
    private float scaleX = 1.0f;
    private Paint paint = new Paint();
    private Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.zzhoujay.richtext.drawable.GifDrawable.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 855 || !GifDrawable.this.running || GifDrawable.this.textView == null) {
                return;
            }
            GifDrawable.this.textView.invalidate();
            sendEmptyMessageDelayed(855, 33L);
        }
    };

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public GifDrawable(Movie movie, int i, int i2) {
        this.movie = movie;
        this.height = i;
        this.width = i2;
        setBounds(0, 0, i2, i);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.start == 0) {
            this.start = uptimeMillis;
        }
        Movie movie = this.movie;
        if (movie != null) {
            int duration = movie.duration();
            if (duration == 0) {
                duration = 1000;
            }
            this.movie.setTime((int) ((uptimeMillis - this.start) % duration));
            Rect bounds = getBounds();
            canvas.scale(this.scaleX, this.scaleY);
            this.movie.draw(canvas, bounds.left, bounds.top);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(@NonNull Rect rect) {
        super.setBounds(rect);
        calculateScale();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        calculateScale();
    }

    private void calculateScale() {
        this.scaleX = getBounds().width() / this.width;
        this.scaleY = getBounds().height() / this.height;
    }

    public void start(TextView textView) {
        this.running = true;
        this.textView = textView;
        this.handler.sendEmptyMessage(855);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.height;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
