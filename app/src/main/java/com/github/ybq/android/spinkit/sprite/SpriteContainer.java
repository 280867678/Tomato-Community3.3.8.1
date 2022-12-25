package com.github.ybq.android.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.AnimationUtils;

/* loaded from: classes2.dex */
public abstract class SpriteContainer extends Sprite {
    private int color;
    private Sprite[] sprites = onCreateChild();

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    protected void drawSelf(Canvas canvas) {
    }

    public void onChildCreated(Sprite... spriteArr) {
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    public abstract Sprite[] onCreateChild();

    public SpriteContainer() {
        initCallBack();
        onChildCreated(this.sprites);
    }

    private void initCallBack() {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr != null) {
            for (Sprite sprite : spriteArr) {
                sprite.setCallback(this);
            }
        }
    }

    public int getChildCount() {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr == null) {
            return 0;
        }
        return spriteArr.length;
    }

    public Sprite getChildAt(int i) {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr == null) {
            return null;
        }
        return spriteArr[i];
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public void setColor(int i) {
        this.color = i;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            getChildAt(i2).setColor(i);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public int getColor() {
        return this.color;
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChild(canvas);
    }

    public void drawChild(Canvas canvas) {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr != null) {
            for (Sprite sprite : spriteArr) {
                int save = canvas.save();
                sprite.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        for (Sprite sprite : this.sprites) {
            sprite.setBounds(rect);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Animatable
    public void start() {
        super.start();
        AnimationUtils.start(this.sprites);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Animatable
    public void stop() {
        super.stop();
        AnimationUtils.stop(this.sprites);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Animatable
    public boolean isRunning() {
        return AnimationUtils.isRunning(this.sprites) || super.isRunning();
    }
}
