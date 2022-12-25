package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;

/* loaded from: classes2.dex */
public class CubeGrid extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        int[] iArr = {200, 300, LotteryDialog.MAX_VALUE, 100, 200, 300, 0, 100, 200};
        GridItem[] gridItemArr = new GridItem[9];
        for (int i = 0; i < gridItemArr.length; i++) {
            gridItemArr[i] = new GridItem();
            gridItemArr[i].setAnimationDelay(iArr[i]);
        }
        return gridItemArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (clipSquare.width() * 0.33f);
        int height = (int) (clipSquare.height() * 0.33f);
        for (int i = 0; i < getChildCount(); i++) {
            int i2 = clipSquare.left + ((i % 3) * width);
            int i3 = clipSquare.top + ((i / 3) * height);
            getChildAt(i).setDrawBounds(i2, i3, i2 + width, i3 + height);
        }
    }

    /* loaded from: classes2.dex */
    private class GridItem extends RectSprite {
        private GridItem(CubeGrid cubeGrid) {
        }

        @Override // com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.35f, 0.7f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(1.0f);
            spriteAnimatorBuilder.scale(fArr, valueOf, Float.valueOf(0.0f), valueOf, valueOf);
            spriteAnimatorBuilder.duration(1300L);
            spriteAnimatorBuilder.easeInOut(fArr);
            return spriteAnimatorBuilder.build();
        }
    }
}
