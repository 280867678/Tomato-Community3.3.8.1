package com.gen.p059mh.webapp_extensions.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.p002v4.app.NotificationCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.utils.interpolator.EaseInBackInterpolator;
import com.gen.p059mh.webapps.utils.interpolator.EaseOutBackInterpolator;
import com.gen.p059mh.webapps.utils.interpolator.EaseOutBounceInterpolator;
import com.gen.p059mh.webapps.utils.interpolator.EaseOutCubicInterpolator;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(api = 11)
/* renamed from: com.gen.mh.webapp_extensions.views.LiquidView */
/* loaded from: classes2.dex */
public class LiquidView extends RelativeLayout {
    private int borderType;
    Paint circlePaint;
    float firstX;
    float firstY;
    int height;
    LiquidButton liquid;
    float moveX;
    float moveY;
    OnButtonClicked onButtonClicked;
    OnButtonClicked onScriptButtonClicked;
    float positionX;
    float positionY;
    IconButton[] scriptIcons;
    int touchState;
    float touchX;
    float touchY;
    ArrayList<IconButton> icons = new ArrayList<>();
    boolean isAniEnd = true;
    Runnable missHandler = new Runnable() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.5
        @Override // java.lang.Runnable
        public void run() {
            LiquidView.this.missSubButtons();
        }
    };
    boolean first = true;

    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$OnButtonClicked */
    /* loaded from: classes2.dex */
    public interface OnButtonClicked {
        void onClick(View view, int i);
    }

    float distance(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (float) Math.sqrt((f5 * f5) + (f6 * f6));
    }

    public void setOnButtonClicked(OnButtonClicked onButtonClicked) {
        this.onButtonClicked = onButtonClicked;
    }

    public void setOnScriptButtonClicked(OnButtonClicked onButtonClicked) {
        this.onScriptButtonClicked = onButtonClicked;
    }

    public LiquidView(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        windowManager.getDefaultDisplay().getWidth();
        this.height = windowManager.getDefaultDisplay().getHeight();
        this.liquid = new LiquidButton(context);
        int d2p = (int) Utils.d2p(getContext(), 40);
        this.liquid.setLayoutParams(new RelativeLayout.LayoutParams(d2p, d2p));
        addView(this.liquid);
        final float d2p2 = Utils.d2p(getContext(), 1);
        this.liquid.setOnTouchListener(new View.OnTouchListener() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    LiquidView liquidView = LiquidView.this;
                    liquidView.touchState = 1;
                    liquidView.touchX = motionEvent.getRawX();
                    LiquidView.this.touchY = motionEvent.getRawY();
                } else if (action == 1) {
                    int i = LiquidView.this.touchState;
                    if (i == 3 || i == 1) {
                        LiquidView liquidView2 = LiquidView.this;
                        if (liquidView2.isAniEnd) {
                            liquidView2.liquidClicked();
                        }
                    }
                    LiquidView liquidView3 = LiquidView.this;
                    liquidView3.touchState = 0;
                    liquidView3.moveToBorder(true);
                } else if (action == 2) {
                    LiquidView liquidView4 = LiquidView.this;
                    int i2 = liquidView4.touchState;
                    if (i2 == 1) {
                        if (liquidView4.distance(liquidView4.touchX, liquidView4.touchY, motionEvent.getRawX(), motionEvent.getRawY()) > d2p2 * 2.0f) {
                            if (LiquidView.this.canDrag()) {
                                LiquidView liquidView5 = LiquidView.this;
                                liquidView5.touchState = 2;
                                liquidView5.move(motionEvent.getRawX(), motionEvent.getRawY());
                                LiquidView.this.updateAfter();
                            } else {
                                LiquidView.this.touchState = 3;
                            }
                        }
                    } else if (i2 == 2) {
                        liquidView4.move(motionEvent.getRawX(), motionEvent.getRawY());
                    }
                } else if (action == 3) {
                    LiquidView liquidView6 = LiquidView.this;
                    liquidView6.touchState = 0;
                    liquidView6.moveToBorder(true);
                }
                return true;
            }
        });
    }

    public void setIcons(IconButton[] iconButtonArr) {
        Iterator<IconButton> it2 = this.icons.iterator();
        while (it2.hasNext()) {
            IconButton next = it2.next();
            next.setOnClickListener(null);
            ViewGroup viewGroup = (ViewGroup) next.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(next);
            }
        }
        this.icons.clear();
        int d2p = (int) Utils.d2p(getContext(), 40);
        int length = iconButtonArr.length;
        for (final int i = 0; i < length; i++) {
            IconButton iconButton = iconButtonArr[i];
            iconButton.setLayoutParams(new RelativeLayout.LayoutParams(d2p, d2p));
            this.icons.add(iconButton);
            iconButton.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    OnButtonClicked onButtonClicked = LiquidView.this.onButtonClicked;
                    if (onButtonClicked != null) {
                        onButtonClicked.onClick(view, i);
                    }
                }
            });
        }
    }

    public void addIcon(IconButton[] iconButtonArr) {
        IconButton[] iconButtonArr2 = this.scriptIcons;
        if (iconButtonArr2 != null) {
            for (IconButton iconButton : iconButtonArr2) {
                iconButton.setOnClickListener(null);
                ViewGroup viewGroup = (ViewGroup) iconButton.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(iconButton);
                }
            }
            clearScriptIcons();
        }
        this.scriptIcons = iconButtonArr;
        int d2p = (int) Utils.d2p(getContext(), 40);
        int length = iconButtonArr.length;
        for (final int i = 0; i < length; i++) {
            IconButton iconButton2 = iconButtonArr[i];
            iconButton2.setLayoutParams(new RelativeLayout.LayoutParams(d2p, d2p));
            this.icons.add(iconButton2);
            iconButton2.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    OnButtonClicked onButtonClicked = LiquidView.this.onScriptButtonClicked;
                    if (onButtonClicked != null) {
                        onButtonClicked.onClick(view, i);
                    }
                }
            });
        }
        Logger.m4112i("icon", "icons = " + this.icons.size());
    }

    public void clearScriptIcons() {
        IconButton[] iconButtonArr = this.scriptIcons;
        if (iconButtonArr != null) {
            for (IconButton iconButton : iconButtonArr) {
                this.icons.remove(iconButton);
            }
        }
    }

    boolean canDrag() {
        return this.liquid.getProgress() <= 0.0f;
    }

    void updateAfter() {
        if (this.touchState == 2) {
            if (Math.abs(this.moveX - this.positionX) > 0.1d || Math.abs(this.moveY - this.positionY) > 0.1d) {
                float f = this.positionX;
                float f2 = f + ((this.moveX - f) * 0.15f);
                float f3 = this.positionY;
                setPosition(f2, f3 + ((this.moveY - f3) * 0.15f));
            }
            getHandler().postDelayed(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.4
                @Override // java.lang.Runnable
                public void run() {
                    LiquidView.this.updateAfter();
                }
            }, 20L);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    void move(float f, float f2) {
        ViewGroup viewGroup;
        if (f2 <= this.liquid.getWidth() * 2 || f2 >= this.height - (this.liquid.getWidth() * 2) || (viewGroup = (ViewGroup) getParent()) == null) {
            return;
        }
        Rect rect = new Rect();
        viewGroup.getDrawingRect(rect);
        this.moveX = f - rect.left;
        this.moveY = f2 - rect.top;
    }

    void liquidClicked() {
        if (this.liquid.getProgress() == 0.0f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.liquid, NotificationCompat.CATEGORY_PROGRESS, 0.0f, 1.0f);
            ofFloat.setInterpolator(new EaseOutCubicInterpolator());
            ofFloat.setDuration(600L);
            if (Build.VERSION.SDK_INT >= 18) {
                ofFloat.setAutoCancel(true);
            }
            ofFloat.start();
            double d = 0.0d;
            double d2 = 0.9424778335276408d;
            if (this.icons.size() * 0.9424778335276408d > 3.141592653589793d) {
                d2 = 3.141592653589793d / this.icons.size();
            }
            int i = this.borderType;
            if (i == 1) {
                d = 1.5707963267948966d - (((this.icons.size() - 1) * d2) / 2.0d);
            } else if (i == 2) {
                d2 = -d2;
                d = 4.71238898038469d - (((this.icons.size() - 1) * d2) / 2.0d);
            } else if (i == 3) {
                d = 3.141592653589793d - (((this.icons.size() - 1) * d2) / 2.0d);
            }
            float d2p = Utils.d2p(getContext(), 58);
            int i2 = 0;
            int size = this.icons.size();
            while (i2 < size) {
                IconButton iconButton = this.icons.get(i2);
                iconButton.setPosition(this.positionX, this.positionY);
                float f = this.positionX;
                double d3 = (i2 * d2) + d;
                int i3 = i2;
                iconButton.setTarget(f, this.positionY, (((float) Math.sin(d3)) * d2p) + f, this.positionY + (((float) Math.cos(d3)) * d2p));
                iconButton.setAlpha(0.0f);
                if (iconButton.getParent() != null) {
                    ((ViewGroup) iconButton.getParent()).removeView(iconButton);
                }
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.addView(iconButton, viewGroup.indexOfChild(this));
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(iconButton, NotificationCompat.CATEGORY_PROGRESS, 0.0f, 1.0f);
                ofFloat2.setInterpolator(new EaseOutBackInterpolator());
                ofFloat2.setDuration(600L);
                ofFloat2.setStartDelay(i3 * 200);
                if (Build.VERSION.SDK_INT >= 18) {
                    ofFloat2.setAutoCancel(true);
                }
                ofFloat2.start();
                i2 = i3 + 1;
            }
            getHandler().postDelayed(this.missHandler, 3000L);
        } else if (this.liquid.getProgress() != 1.0f) {
        } else {
            missSubButtons();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void missSubButtons() {
        if (getHandler() == null) {
            return;
        }
        this.isAniEnd = false;
        getHandler().removeCallbacks(this.missHandler);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.liquid, NotificationCompat.CATEGORY_PROGRESS, 1.0f, 0.0f);
        ofFloat.setInterpolator(new EaseOutCubicInterpolator());
        ofFloat.setDuration(600L);
        if (Build.VERSION.SDK_INT >= 18) {
            ofFloat.setAutoCancel(true);
        }
        ofFloat.start();
        final int size = this.icons.size();
        for (final int i = 0; i < size; i++) {
            final IconButton iconButton = this.icons.get(i);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(iconButton, NotificationCompat.CATEGORY_PROGRESS, 1.0f, 0.0f);
            ofFloat2.setInterpolator(new EaseInBackInterpolator());
            ofFloat2.setDuration(600L);
            ofFloat2.setStartDelay(i * 200);
            if (Build.VERSION.SDK_INT >= 18) {
                ofFloat2.setAutoCancel(true);
            }
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ViewGroup viewGroup = (ViewGroup) iconButton.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(iconButton);
                    }
                    if (i == size - 1) {
                        LiquidView.this.isAniEnd = true;
                    }
                }
            });
            ofFloat2.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$CircleView */
    /* loaded from: classes2.dex */
    public static class CircleView extends RelativeLayout {
        public Paint paint;

        public CircleView(Context context) {
            super(context);
            setBackgroundColor(0);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            Paint paint = this.paint;
            if (paint != null) {
                float f = width / 2;
                canvas.drawCircle(f, f, f, paint);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$DotView */
    /* loaded from: classes2.dex */
    public class DotView extends View {
        private Paint clearPaint = new Paint();
        public Paint paint;
        private float progress;

        public DotView(Context context) {
            super(context);
            setLayerType(1, null);
            this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            this.clearPaint.setAntiAlias(true);
            this.clearPaint.setColor(0);
            this.clearPaint.setDither(true);
        }

        public void setProgress(float f) {
            if (this.progress != f) {
                this.progress = f;
                invalidate();
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            if (this.paint != null) {
                float d2p = Utils.d2p(getContext(), 1);
                float f = width / 2;
                canvas.drawCircle(f, f, ((this.progress * 4.0f) + 4.0f) * d2p, this.paint);
                float f2 = this.progress;
                if (f2 <= 0.1d) {
                    return;
                }
                canvas.drawCircle(f, f, f2 * 6.0f * d2p, this.clearPaint);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$LiquidButton */
    /* loaded from: classes2.dex */
    public class LiquidButton extends CircleView {
        CircleView dot1;
        DotView dot2;
        CircleView dot3;
        private float progress = 0.0f;
        int strokeWidth = (int) Utils.d2p(getContext(), 1);

        public LiquidButton(Context context) {
            super(context);
            this.paint = new Paint();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(1275068416);
            this.paint.setAntiAlias(true);
            this.paint.setDither(true);
            LiquidView.this.circlePaint = new Paint();
            LiquidView.this.circlePaint.setColor(-1);
            LiquidView.this.circlePaint.setStyle(Paint.Style.STROKE);
            LiquidView.this.circlePaint.setStrokeWidth(this.strokeWidth);
            LiquidView.this.circlePaint.setAntiAlias(true);
            LiquidView.this.circlePaint.setDither(true);
            this.dot1 = new CircleView(context);
            this.dot2 = new DotView(context);
            this.dot3 = new CircleView(context);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(-1);
            paint.setAntiAlias(true);
            paint.setDither(true);
            CircleView circleView = this.dot1;
            DotView dotView = this.dot2;
            this.dot3.paint = paint;
            dotView.paint = paint;
            circleView.paint = paint;
            float d2p = Utils.d2p(getContext(), 1);
            int i = (int) (4.0f * d2p);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, i);
            layoutParams.addRule(13, -1);
            this.dot1.setLayoutParams(layoutParams);
            addView(this.dot1);
            int i2 = (int) (d2p * 16.0f);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i2, i2);
            layoutParams2.addRule(13, -1);
            this.dot2.setLayoutParams(layoutParams2);
            addView(this.dot2);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(i, i);
            layoutParams3.addRule(13, -1);
            this.dot3.setLayoutParams(layoutParams3);
            addView(this.dot3);
            update();
        }

        public float getProgress() {
            return this.progress;
        }

        public void setProgress(float f) {
            if (this.progress != f) {
                this.progress = f;
                update();
            }
        }

        private void update() {
            float d2p = Utils.d2p(getContext(), 1);
            this.dot1.setTranslationX(((this.progress * 10.0f) - 10.0f) * d2p);
            this.dot2.setTranslationX(0.0f);
            this.dot3.setTranslationX((10.0f - (this.progress * 10.0f)) * d2p);
            this.paint.setColor(-1291845632);
            invalidate();
            float f = this.progress;
            if (f < 0.2d) {
                this.dot2.setProgress(0.0f);
            } else {
                this.dot2.setProgress((f - 0.2f) / 0.8f);
            }
        }

        @Override // com.gen.p059mh.webapp_extensions.views.LiquidView.CircleView, android.view.View
        protected void onDraw(Canvas canvas) {
            int width;
            super.onDraw(canvas);
            float width2 = getWidth() / 2;
            canvas.drawCircle(width2, width2, width - (this.strokeWidth / 2), LiquidView.this.circlePaint);
        }
    }

    public void setPosition(float f, float f2) {
        this.positionX = f;
        this.positionY = f2;
        updatePosition();
    }

    void updatePosition() {
        ViewGroup viewGroup;
        int i;
        int i2;
        float f = this.positionX;
        float f2 = this.positionY;
        if (((ViewGroup) getParent()) != null) {
            int width = getWidth();
            int height = getHeight();
            float f3 = width / 2;
            float min = Math.min(viewGroup.getWidth() - i, Math.max(f3, f));
            float f4 = height / 2;
            float min2 = Math.min(viewGroup.getHeight() - i2, Math.max(f4, f2));
            setTranslationX(min - f3);
            setTranslationY(min2 - f4);
        }
    }

    public void setFirstPosition(float f, float f2) {
        this.firstX = f;
        this.firstY = f2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$PositionAnimator */
    /* loaded from: classes2.dex */
    public class PositionAnimator extends ValueAnimator {
        public PositionAnimator(final float f, final float f2, final float f3, final float f4) {
            setFloatValues(0.0f, 1.0f);
            addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.gen.mh.webapp_extensions.views.LiquidView.PositionAnimator.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    float f5 = 1.0f - floatValue;
                    LiquidView.this.setPosition((f * f5) + (f3 * floatValue), (f2 * f5) + (f4 * floatValue));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveToBorder(boolean z) {
        float f;
        float min;
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            float d2p = Utils.d2p(getContext(), 20);
            float width = viewGroup.getWidth();
            float height = viewGroup.getHeight();
            double d = this.moveX / width;
            double d2 = this.moveY / height;
            if (Math.abs(d - 0.5d) <= Math.abs(d2 - 0.5d) && d2 >= 0.5d) {
                f = Math.min(width - d2p, Math.max(d2p, this.moveX));
                min = height - d2p;
                this.borderType = 3;
            } else if (d < 0.5d) {
                min = Math.min(height - d2p, Math.max(d2p, this.moveY));
                this.borderType = 1;
                f = d2p;
            } else {
                f = width - d2p;
                min = Math.min(height - d2p, Math.max(d2p, this.moveY));
                this.borderType = 2;
            }
            if (z) {
                PositionAnimator positionAnimator = new PositionAnimator(this.positionX, this.positionY, f, min);
                positionAnimator.setDuration(500L);
                positionAnimator.setInterpolator(new EaseOutBounceInterpolator());
                savePosition(f, min);
                positionAnimator.start();
                return;
            }
            setPosition(f, min);
        }
    }

    public void savePosition(float f, float f2) {
        SharedPreferences.Editor edit = getContext().getSharedPreferences("SP_WEB_APP", 0).edit();
        edit.putFloat("L_TARGET_X", f);
        edit.putFloat("L_TARGET_Y", f2);
        edit.commit();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.first) {
            this.first = false;
            ViewGroup viewGroup = (ViewGroup) getParent();
            Utils.d2p(getContext(), 1);
            this.positionX = this.firstX;
            this.positionY = this.firstY;
        }
        updatePosition();
        this.moveX = this.positionX;
        this.moveY = this.positionY;
        moveToBorder(false);
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.LiquidView$IconButton */
    /* loaded from: classes2.dex */
    public static class IconButton extends CircleView {
        private float fromX;
        private float fromY;
        ImageView imageView;
        private float positionX;
        private float positionY;
        private float toX;
        private float toY;

        public IconButton(Context context) {
            super(context);
            this.paint = new Paint();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(1275068416);
            this.paint.setAntiAlias(true);
            this.paint.setDither(true);
            this.imageView = new ImageView(context);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13, -1);
            this.imageView.setLayoutParams(layoutParams);
            addView(this.imageView);
            this.imageView.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        public ImageView getImageView() {
            return this.imageView;
        }

        public void setPosition(float f, float f2) {
            this.positionX = f;
            this.positionY = f2;
            float d2p = (int) Utils.d2p(getContext(), 20);
            setTranslationX(f - d2p);
            setTranslationY(f2 - d2p);
        }

        public void setTarget(float f, float f2, float f3, float f4) {
            this.fromX = f;
            this.fromY = f2;
            this.toX = f3;
            this.toY = f4;
        }

        public void setProgress(float f) {
            setAlpha(f);
            float f2 = 1.0f - f;
            setPosition((this.fromX * f2) + (this.toX * f), (this.fromY * f2) + (this.toY * f));
            setRotation(f2 * 180.0f * 8.0f);
            this.paint.setColor((((int) (((f * 0.3d) + 0.3d) * 255.0d)) << 24) | 0);
            invalidate();
        }
    }
}
