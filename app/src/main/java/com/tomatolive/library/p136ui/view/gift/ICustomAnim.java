package com.tomatolive.library.p136ui.view.gift;

import android.animation.AnimatorSet;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.gift.ICustomAnim */
/* loaded from: classes3.dex */
public interface ICustomAnim {
    AnimatorSet comboAnim(GiftFrameLayout giftFrameLayout, View view, boolean z);

    AnimatorSet endAnim(GiftFrameLayout giftFrameLayout, View view);

    AnimatorSet startAnim(GiftFrameLayout giftFrameLayout, View view);
}
