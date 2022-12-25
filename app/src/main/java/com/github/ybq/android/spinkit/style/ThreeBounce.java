package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class ThreeBounce extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Bounce(this), new Bounce(this), new Bounce(this)};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(160);
        spriteArr[2].setAnimationDelay(320);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = clipSquare.width() / 8;
        int centerY = clipSquare.centerY() - width;
        int centerY2 = clipSquare.centerY() + width;
        for (int i = 0; i < getChildCount(); i++) {
            int width2 = ((clipSquare.width() * i) / 3) + clipSquare.left;
            getChildAt(i).setDrawBounds(width2, centerY, (width * 2) + width2, centerY2);
        }
    }

    /* loaded from: classes2.dex */
    private class Bounce extends CircleSprite {
        Bounce(ThreeBounce threeBounce) {
            setScale(0.0f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.4f, 0.8f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.0f);
            spriteAnimatorBuilder.scale(fArr, valueOf, Float.valueOf(1.0f), valueOf, valueOf);
            spriteAnimatorBuilder.duration(1400L);
            spriteAnimatorBuilder.easeInOut(fArr);
            return spriteAnimatorBuilder.build();
        }
    }
}
