package com.bigkoo.pickerview.utils;

import com.bigkoo.pickerview.R$anim;

/* loaded from: classes2.dex */
public class PickerViewAnimateUtil {
    public static int getAnimationResource(int i, boolean z) {
        if (i != 80) {
            return -1;
        }
        return z ? R$anim.pickerview_slide_in_bottom : R$anim.pickerview_slide_out_bottom;
    }
}
