package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleLayoutContainer;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;

/* loaded from: classes2.dex */
public class FadingCircle extends CircleLayoutContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        Dot[] dotArr = new Dot[12];
        for (int i = 0; i < dotArr.length; i++) {
            dotArr[i] = new Dot(this);
            if (Build.VERSION.SDK_INT >= 24) {
                dotArr[i].setAnimationDelay(i * 100);
            } else {
                dotArr[i].setAnimationDelay((i * 100) - 1200);
            }
        }
        return dotArr;
    }

    /* loaded from: classes2.dex */
    private class Dot extends CircleSprite {
        Dot(FadingCircle fadingCircle) {
            setAlpha(0);
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.39f, 0.4f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            spriteAnimatorBuilder.alpha(fArr, 0, 0, 255, 0);
            spriteAnimatorBuilder.duration(1200L);
            spriteAnimatorBuilder.easeInOut(fArr);
            return spriteAnimatorBuilder.build();
        }
    }
}
