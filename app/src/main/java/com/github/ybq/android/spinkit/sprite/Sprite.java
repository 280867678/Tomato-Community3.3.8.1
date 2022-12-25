package com.github.ybq.android.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.github.ybq.android.spinkit.animation.AnimationUtils;
import com.github.ybq.android.spinkit.animation.FloatProperty;
import com.github.ybq.android.spinkit.animation.IntProperty;

/* loaded from: classes2.dex */
public abstract class Sprite extends Drawable implements ValueAnimator.AnimatorUpdateListener, Animatable, Drawable.Callback {
    private int animationDelay;
    private ValueAnimator animator;
    private float pivotX;
    private float pivotY;
    private int rotate;
    private int rotateX;
    private int rotateY;
    private int translateX;
    private float translateXPercentage;
    private int translateY;
    private float translateYPercentage;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    public static final Property<Sprite, Integer> ROTATE_X = new IntProperty<Sprite>("rotateX") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.1
        @Override // com.github.ybq.android.spinkit.animation.IntProperty
        public void setValue(Sprite sprite, int i) {
            sprite.setRotateX(i);
        }

        @Override // android.util.Property
        public Integer get(Sprite sprite) {
            return Integer.valueOf(sprite.getRotateX());
        }
    };
    public static final Property<Sprite, Integer> ROTATE = new IntProperty<Sprite>("rotate") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.2
        @Override // com.github.ybq.android.spinkit.animation.IntProperty
        public void setValue(Sprite sprite, int i) {
            sprite.setRotate(i);
        }

        @Override // android.util.Property
        public Integer get(Sprite sprite) {
            return Integer.valueOf(sprite.getRotate());
        }
    };
    public static final Property<Sprite, Integer> ROTATE_Y = new IntProperty<Sprite>("rotateY") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.3
        @Override // com.github.ybq.android.spinkit.animation.IntProperty
        public void setValue(Sprite sprite, int i) {
            sprite.setRotateY(i);
        }

        @Override // android.util.Property
        public Integer get(Sprite sprite) {
            return Integer.valueOf(sprite.getRotateY());
        }
    };
    public static final Property<Sprite, Float> TRANSLATE_X_PERCENTAGE = new FloatProperty<Sprite>("translateXPercentage") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.6
        @Override // com.github.ybq.android.spinkit.animation.FloatProperty
        public void setValue(Sprite sprite, float f) {
            sprite.setTranslateXPercentage(f);
        }

        @Override // android.util.Property
        public Float get(Sprite sprite) {
            return Float.valueOf(sprite.getTranslateXPercentage());
        }
    };
    public static final Property<Sprite, Float> TRANSLATE_Y_PERCENTAGE = new FloatProperty<Sprite>("translateYPercentage") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.7
        @Override // com.github.ybq.android.spinkit.animation.FloatProperty
        public void setValue(Sprite sprite, float f) {
            sprite.setTranslateYPercentage(f);
        }

        @Override // android.util.Property
        public Float get(Sprite sprite) {
            return Float.valueOf(sprite.getTranslateYPercentage());
        }
    };
    public static final Property<Sprite, Float> SCALE_Y = new FloatProperty<Sprite>("scaleY") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.9
        @Override // com.github.ybq.android.spinkit.animation.FloatProperty
        public void setValue(Sprite sprite, float f) {
            sprite.setScaleY(f);
        }

        @Override // android.util.Property
        public Float get(Sprite sprite) {
            return Float.valueOf(sprite.getScaleY());
        }
    };
    public static final Property<Sprite, Float> SCALE = new FloatProperty<Sprite>("scale") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.10
        @Override // com.github.ybq.android.spinkit.animation.FloatProperty
        public void setValue(Sprite sprite, float f) {
            sprite.setScale(f);
        }

        @Override // android.util.Property
        public Float get(Sprite sprite) {
            return Float.valueOf(sprite.getScale());
        }
    };
    public static final Property<Sprite, Integer> ALPHA = new IntProperty<Sprite>("alpha") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.11
        @Override // com.github.ybq.android.spinkit.animation.IntProperty
        public void setValue(Sprite sprite, int i) {
            sprite.setAlpha(i);
        }

        @Override // android.util.Property
        public Integer get(Sprite sprite) {
            return Integer.valueOf(sprite.getAlpha());
        }
    };
    private float scale = 1.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private int alpha = 255;
    protected Rect drawBounds = ZERO_BOUNDS_RECT;
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();

    protected abstract void drawSelf(Canvas canvas);

    public abstract int getColor();

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public abstract ValueAnimator onCreateAnimation();

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
    }

    public abstract void setColor(int i);

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
    }

    static {
        new IntProperty<Sprite>("translateX") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.4
            @Override // com.github.ybq.android.spinkit.animation.IntProperty
            public void setValue(Sprite sprite, int i) {
                sprite.setTranslateX(i);
            }

            @Override // android.util.Property
            public Integer get(Sprite sprite) {
                return Integer.valueOf(sprite.getTranslateX());
            }
        };
        new IntProperty<Sprite>("translateY") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.5
            @Override // com.github.ybq.android.spinkit.animation.IntProperty
            public void setValue(Sprite sprite, int i) {
                sprite.setTranslateY(i);
            }

            @Override // android.util.Property
            public Integer get(Sprite sprite) {
                return Integer.valueOf(sprite.getTranslateY());
            }
        };
        new FloatProperty<Sprite>("scaleX") { // from class: com.github.ybq.android.spinkit.sprite.Sprite.8
            @Override // com.github.ybq.android.spinkit.animation.FloatProperty
            public void setValue(Sprite sprite, float f) {
                sprite.setScaleX(f);
            }

            @Override // android.util.Property
            public Float get(Sprite sprite) {
                return Float.valueOf(sprite.getScaleX());
            }
        };
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.alpha = i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.alpha;
    }

    public float getTranslateXPercentage() {
        return this.translateXPercentage;
    }

    public void setTranslateXPercentage(float f) {
        this.translateXPercentage = f;
    }

    public float getTranslateYPercentage() {
        return this.translateYPercentage;
    }

    public void setTranslateYPercentage(float f) {
        this.translateYPercentage = f;
    }

    public int getTranslateX() {
        return this.translateX;
    }

    public void setTranslateX(int i) {
        this.translateX = i;
    }

    public int getTranslateY() {
        return this.translateY;
    }

    public void setTranslateY(int i) {
        this.translateY = i;
    }

    public int getRotate() {
        return this.rotate;
    }

    public void setRotate(int i) {
        this.rotate = i;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
        setScaleX(f);
        setScaleY(f);
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float f) {
        this.scaleX = f;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float f) {
        this.scaleY = f;
    }

    public int getRotateX() {
        return this.rotateX;
    }

    public void setRotateX(int i) {
        this.rotateX = i;
    }

    public int getRotateY() {
        return this.rotateY;
    }

    public void setRotateY(int i) {
        this.rotateY = i;
    }

    public float getPivotX() {
        return this.pivotX;
    }

    public void setPivotX(float f) {
        this.pivotX = f;
    }

    public float getPivotY() {
        return this.pivotY;
    }

    public void setPivotY(float f) {
        this.pivotY = f;
    }

    public Sprite setAnimationDelay(int i) {
        this.animationDelay = i;
        return this;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (AnimationUtils.isStarted(this.animator)) {
            return;
        }
        this.animator = obtainAnimation();
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator == null) {
            return;
        }
        AnimationUtils.start(valueAnimator);
        invalidateSelf();
    }

    public ValueAnimator obtainAnimation() {
        if (this.animator == null) {
            this.animator = onCreateAnimation();
        }
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.addUpdateListener(this);
            this.animator.setStartDelay(this.animationDelay);
        }
        return this.animator;
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (AnimationUtils.isStarted(this.animator)) {
            this.animator.removeAllUpdateListeners();
            this.animator.end();
            reset();
        }
    }

    public void reset() {
        this.scale = 1.0f;
        this.rotateX = 0;
        this.rotateY = 0;
        this.translateX = 0;
        this.translateY = 0;
        this.rotate = 0;
        this.translateXPercentage = 0.0f;
        this.translateYPercentage = 0.0f;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return AnimationUtils.isRunning(this.animator);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        setDrawBounds(rect);
    }

    public void setDrawBounds(Rect rect) {
        setDrawBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setDrawBounds(int i, int i2, int i3, int i4) {
        this.drawBounds = new Rect(i, i2, i3, i4);
        setPivotX(getDrawBounds().centerX());
        setPivotY(getDrawBounds().centerY());
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public Rect getDrawBounds() {
        return this.drawBounds;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int translateX = getTranslateX();
        if (translateX == 0) {
            translateX = (int) (getBounds().width() * getTranslateXPercentage());
        }
        int translateY = getTranslateY();
        if (translateY == 0) {
            translateY = (int) (getBounds().height() * getTranslateYPercentage());
        }
        canvas.translate(translateX, translateY);
        canvas.scale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
        canvas.rotate(getRotate(), getPivotX(), getPivotY());
        if (getRotateX() != 0 || getRotateY() != 0) {
            this.mCamera.save();
            this.mCamera.rotateX(getRotateX());
            this.mCamera.rotateY(getRotateY());
            this.mCamera.getMatrix(this.mMatrix);
            this.mMatrix.preTranslate(-getPivotX(), -getPivotY());
            this.mMatrix.postTranslate(getPivotX(), getPivotY());
            this.mCamera.restore();
            canvas.concat(this.mMatrix);
        }
        drawSelf(canvas);
    }

    public Rect clipSquare(Rect rect) {
        int min = Math.min(rect.width(), rect.height());
        int centerX = rect.centerX();
        int centerY = rect.centerY();
        int i = min / 2;
        return new Rect(centerX - i, centerY - i, centerX + i, centerY + i);
    }
}
