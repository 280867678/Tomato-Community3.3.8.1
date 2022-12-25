package com.github.ybq.android.spinkit.style;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* loaded from: classes2.dex */
public class MultiplePulseRing extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new PulseRing(), new PulseRing(), new PulseRing()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        int i = 0;
        while (i < spriteArr.length) {
            Sprite sprite = spriteArr[i];
            i++;
            sprite.setAnimationDelay(i * 200);
        }
    }
}
