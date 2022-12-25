package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.support.p002v4.app.NotificationManagerCompat;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class ChasingDots extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Dot(this), new Dot(this)};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        if (Build.VERSION.SDK_INT >= 24) {
            spriteArr[1].setAnimationDelay(1000);
        } else {
            spriteArr[1].setAnimationDelay(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        spriteAnimatorBuilder.rotate(new float[]{0.0f, 1.0f}, 0, 360);
        spriteAnimatorBuilder.duration(2000L);
        spriteAnimatorBuilder.interpolator(new LinearInterpolator());
        return spriteAnimatorBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (clipSquare.width() * 0.6f);
        Sprite childAt = getChildAt(0);
        int i = clipSquare.right;
        int i2 = clipSquare.top;
        childAt.setDrawBounds(i - width, i2, i, i2 + width);
        Sprite childAt2 = getChildAt(1);
        int i3 = clipSquare.right;
        int i4 = clipSquare.bottom;
        childAt2.setDrawBounds(i3 - width, i4 - width, i3, i4);
    }

    /* loaded from: classes2.dex */
    private class Dot extends CircleSprite {
        Dot(ChasingDots chasingDots) {
            setScale(0.0f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.0f);
            spriteAnimatorBuilder.scale(fArr, valueOf, Float.valueOf(1.0f), valueOf);
            spriteAnimatorBuilder.duration(2000L);
            spriteAnimatorBuilder.easeInOut(fArr);
            return spriteAnimatorBuilder.build();
        }
    }
}
