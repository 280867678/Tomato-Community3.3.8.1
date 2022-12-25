package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class Wave extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        WaveItem[] waveItemArr = new WaveItem[5];
        for (int i = 0; i < waveItemArr.length; i++) {
            waveItemArr[i] = new WaveItem(this);
            if (Build.VERSION.SDK_INT >= 24) {
                waveItemArr[i].setAnimationDelay((i * 100) + 600);
            } else {
                waveItemArr[i].setAnimationDelay((i * 100) - 1200);
            }
        }
        return waveItemArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = clipSquare.width() / getChildCount();
        int width2 = ((clipSquare.width() / 5) * 3) / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int i2 = clipSquare.left + (i * width) + (width / 5);
            childAt.setDrawBounds(i2, clipSquare.top, i2 + width2, clipSquare.bottom);
        }
    }

    /* loaded from: classes2.dex */
    private class WaveItem extends RectSprite {
        WaveItem(Wave wave) {
            setScaleY(0.4f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.2f, 0.4f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.4f);
            spriteAnimatorBuilder.scaleY(fArr, valueOf, Float.valueOf(1.0f), valueOf, valueOf);
            spriteAnimatorBuilder.duration(1200L);
            spriteAnimatorBuilder.easeInOut(fArr);
            return spriteAnimatorBuilder.build();
        }
    }
}
