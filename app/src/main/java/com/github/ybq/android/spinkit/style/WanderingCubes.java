package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class WanderingCubes extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Cube(this, 0), new Cube(this, 3)};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        if (Build.VERSION.SDK_INT < 24) {
            spriteArr[1].setAnimationDelay(-900);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Rect clipSquare = clipSquare(rect);
        super.onBoundsChange(clipSquare);
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int i2 = clipSquare.left;
            childAt.setDrawBounds(i2, clipSquare.top, (clipSquare.width() / 4) + i2, clipSquare.top + (clipSquare.height() / 4));
        }
    }

    /* loaded from: classes2.dex */
    private class Cube extends RectSprite {
        int startFrame;

        public Cube(WanderingCubes wanderingCubes, int i) {
            this.startFrame = i;
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.25f, 0.5f, 0.51f, 0.75f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            spriteAnimatorBuilder.rotate(fArr, 0, -90, -179, -180, -270, -360);
            Float valueOf = Float.valueOf(0.0f);
            Float valueOf2 = Float.valueOf(0.75f);
            spriteAnimatorBuilder.translateXPercentage(fArr, valueOf, valueOf2, valueOf2, valueOf2, valueOf, valueOf);
            spriteAnimatorBuilder.translateYPercentage(fArr, valueOf, valueOf, valueOf2, valueOf2, valueOf2, valueOf);
            Float valueOf3 = Float.valueOf(1.0f);
            Float valueOf4 = Float.valueOf(0.5f);
            spriteAnimatorBuilder.scale(fArr, valueOf3, valueOf4, valueOf3, valueOf3, valueOf4, valueOf3);
            spriteAnimatorBuilder.duration(1800L);
            spriteAnimatorBuilder.easeInOut(fArr);
            if (Build.VERSION.SDK_INT >= 24) {
                spriteAnimatorBuilder.startFrame(this.startFrame);
            }
            return spriteAnimatorBuilder.build();
        }
    }
}
