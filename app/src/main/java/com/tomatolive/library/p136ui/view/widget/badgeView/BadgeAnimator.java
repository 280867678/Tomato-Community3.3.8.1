package com.tomatolive.library.p136ui.view.widget.badgeView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.Random;

/* renamed from: com.tomatolive.library.ui.view.widget.badgeView.BadgeAnimator */
/* loaded from: classes4.dex */
public class BadgeAnimator extends ValueAnimator {
    private BitmapFragment[][] mFragments;
    private WeakReference<QBadgeView> mWeakBadge;

    public BadgeAnimator(Bitmap bitmap, PointF pointF, QBadgeView qBadgeView) {
        this.mWeakBadge = new WeakReference<>(qBadgeView);
        setFloatValues(0.0f, 1.0f);
        setDuration(500L);
        this.mFragments = getFragments(bitmap, pointF);
        addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.ui.view.widget.badgeView.BadgeAnimator.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                QBadgeView qBadgeView2 = (QBadgeView) BadgeAnimator.this.mWeakBadge.get();
                if (qBadgeView2 == null || !qBadgeView2.isShown()) {
                    BadgeAnimator.this.cancel();
                } else {
                    qBadgeView2.invalidate();
                }
            }
        });
        addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.widget.badgeView.BadgeAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                QBadgeView qBadgeView2 = (QBadgeView) BadgeAnimator.this.mWeakBadge.get();
                if (qBadgeView2 != null) {
                    qBadgeView2.reset();
                }
            }
        });
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < this.mFragments.length; i++) {
            int i2 = 0;
            while (true) {
                BitmapFragment[][] bitmapFragmentArr = this.mFragments;
                if (i2 < bitmapFragmentArr[i].length) {
                    bitmapFragmentArr[i][i2].updata(Float.parseFloat(getAnimatedValue().toString()), canvas);
                    i2++;
                }
            }
        }
    }

    private BitmapFragment[][] getFragments(Bitmap bitmap, PointF pointF) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float min = Math.min(width, height) / 6.0f;
        float width2 = pointF.x - (bitmap.getWidth() / 2.0f);
        float height2 = pointF.y - (bitmap.getHeight() / 2.0f);
        BitmapFragment[][] bitmapFragmentArr = (BitmapFragment[][]) Array.newInstance(BitmapFragment.class, (int) (height / min), (int) (width / min));
        for (int i = 0; i < bitmapFragmentArr.length; i++) {
            for (int i2 = 0; i2 < bitmapFragmentArr[i].length; i2++) {
                BitmapFragment bitmapFragment = new BitmapFragment();
                float f = i2 * min;
                float f2 = i * min;
                bitmapFragment.color = bitmap.getPixel((int) f, (int) f2);
                bitmapFragment.f5866x = f + width2;
                bitmapFragment.f5867y = f2 + height2;
                bitmapFragment.size = min;
                bitmapFragment.maxSize = Math.max(width, height);
                bitmapFragmentArr[i][i2] = bitmapFragment;
            }
        }
        bitmap.recycle();
        return bitmapFragmentArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.badgeView.BadgeAnimator$BitmapFragment */
    /* loaded from: classes4.dex */
    public class BitmapFragment {
        int color;
        int maxSize;
        Paint paint = new Paint();
        Random random = new Random();
        float size;

        /* renamed from: x */
        float f5866x;

        /* renamed from: y */
        float f5867y;

        public BitmapFragment() {
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.FILL);
        }

        public void updata(float f, Canvas canvas) {
            this.paint.setColor(this.color);
            this.f5866x += this.random.nextInt(this.maxSize) * 0.1f * (this.random.nextFloat() - 0.5f);
            this.f5867y += this.random.nextInt(this.maxSize) * 0.1f * (this.random.nextFloat() - 0.5f);
            float f2 = this.f5866x;
            float f3 = this.f5867y;
            float f4 = this.size;
            canvas.drawCircle(f2, f3, f4 - (f * f4), this.paint);
        }
    }
}
