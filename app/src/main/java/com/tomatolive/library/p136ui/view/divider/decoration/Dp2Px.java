package com.tomatolive.library.p136ui.view.divider.decoration;

import android.content.Context;
import com.blankj.utilcode.util.ConvertUtils;

/* renamed from: com.tomatolive.library.ui.view.divider.decoration.Dp2Px */
/* loaded from: classes3.dex */
public class Dp2Px {
    private Dp2Px() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int convert(Context context, float f) {
        return ConvertUtils.dp2px(f);
    }
}
