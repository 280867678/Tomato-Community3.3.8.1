package com.tomatolive.library.p136ui.view.sticker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGColorGroup */
/* loaded from: classes3.dex */
public class IMGColorGroup extends RadioGroup {
    public IMGColorGroup(Context context) {
        super(context);
    }

    public IMGColorGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getCheckColor() {
        IMGColorRadio iMGColorRadio = (IMGColorRadio) findViewById(getCheckedRadioButtonId());
        if (iMGColorRadio != null) {
            return iMGColorRadio.getColor();
        }
        return -1;
    }

    public void setCheckColor(int i) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            IMGColorRadio iMGColorRadio = (IMGColorRadio) getChildAt(i2);
            if (iMGColorRadio.getColor() == i) {
                iMGColorRadio.setChecked(true);
                return;
            }
        }
    }
}
