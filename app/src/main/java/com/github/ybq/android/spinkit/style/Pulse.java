package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;

/* loaded from: classes2.dex */
public class Pulse extends CircleSprite {
    public Pulse() {
        setScale(0.0f);
    }

    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        spriteAnimatorBuilder.scale(fArr, Float.valueOf(0.0f), Float.valueOf(1.0f));
        spriteAnimatorBuilder.alpha(fArr, 255, 0);
        spriteAnimatorBuilder.duration(1000L);
        spriteAnimatorBuilder.easeInOut(fArr);
        return spriteAnimatorBuilder.build();
    }
}
