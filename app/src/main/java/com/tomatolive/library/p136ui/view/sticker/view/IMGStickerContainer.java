package com.tomatolive.library.p136ui.view.sticker.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGStickerContainer */
/* loaded from: classes3.dex */
public class IMGStickerContainer extends ViewGroup {
    public IMGStickerContainer(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt;
        if (getChildCount() != 1 || (childAt = getChildAt(0)) == null) {
            return;
        }
        int i5 = i + i3;
        int i6 = i2 + i4;
        childAt.layout(i5 >> (1 - (childAt.getMeasuredWidth() >> 1)), i6 >> (1 - (childAt.getMeasuredHeight() >> 1)), i5 >> ((childAt.getMeasuredWidth() >> 1) + 1), i6 >> ((childAt.getMeasuredHeight() >> 1) + 1));
    }
}
