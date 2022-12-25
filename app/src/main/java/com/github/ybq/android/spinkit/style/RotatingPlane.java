package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;

/* loaded from: classes2.dex */
public class RotatingPlane extends RectSprite {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        setDrawBounds(clipSquare(rect));
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 0.5f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        spriteAnimatorBuilder.rotateX(fArr, 0, -180, -180);
        spriteAnimatorBuilder.rotateY(fArr, 0, 0, -180);
        spriteAnimatorBuilder.duration(1200L);
        spriteAnimatorBuilder.easeInOut(fArr);
        return spriteAnimatorBuilder.build();
    }
}
