package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class FoldingCube extends SpriteContainer {
    private boolean wrapContent = false;

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        Cube[] cubeArr = new Cube[4];
        for (int i = 0; i < cubeArr.length; i++) {
            cubeArr[i] = new Cube(this);
            if (Build.VERSION.SDK_INT >= 24) {
                cubeArr[i].setAnimationDelay(i * 300);
            } else {
                cubeArr[i].setAnimationDelay((i * 300) - 1200);
            }
        }
        return cubeArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int min = Math.min(clipSquare.width(), clipSquare.height());
        if (this.wrapContent) {
            min = (int) Math.sqrt((min * min) / 2);
            int width = (clipSquare.width() - min) / 2;
            int height = (clipSquare.height() - min) / 2;
            clipSquare = new Rect(clipSquare.left + width, clipSquare.top + height, clipSquare.right - width, clipSquare.bottom - height);
        }
        int i = min / 2;
        int i2 = clipSquare.left + i + 1;
        int i3 = clipSquare.top + i + 1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            Sprite childAt = getChildAt(i4);
            childAt.setDrawBounds(clipSquare.left, clipSquare.top, i2, i3);
            childAt.setPivotX(childAt.getDrawBounds().right);
            childAt.setPivotY(childAt.getDrawBounds().bottom);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void drawChild(Canvas canvas) {
        Rect clipSquare = clipSquare(getBounds());
        for (int i = 0; i < getChildCount(); i++) {
            int save = canvas.save();
            canvas.rotate((i * 90) + 45, clipSquare.centerX(), clipSquare.centerY());
            getChildAt(i).draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* loaded from: classes2.dex */
    private class Cube extends RectSprite {
        Cube(FoldingCube foldingCube) {
            setAlpha(0);
            setRotateX(-180);
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.1f, 0.25f, 0.75f, 0.9f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            spriteAnimatorBuilder.alpha(fArr, 0, 0, 255, 255, 0, 0);
            spriteAnimatorBuilder.rotateX(fArr, -180, -180, 0, 0, 0, 0);
            spriteAnimatorBuilder.rotateY(fArr, 0, 0, 0, 0, 180, 180);
            spriteAnimatorBuilder.duration(2400L);
            spriteAnimatorBuilder.interpolator(new LinearInterpolator());
            return spriteAnimatorBuilder.build();
        }
    }
}
