package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.res.ResourcesCompat;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.CheckRadioView */
/* loaded from: classes4.dex */
public class CheckRadioView extends AppCompatImageView {
    private Drawable mDrawable;
    private int mSelectedColor;
    private int mUnSelectUdColor;

    public CheckRadioView(Context context) {
        super(context);
        init();
    }

    public CheckRadioView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mSelectedColor = ResourcesCompat.getColor(getResources(), R$color.zhihu_item_checkCircle_backgroundColor, getContext().getTheme());
        this.mUnSelectUdColor = ResourcesCompat.getColor(getResources(), R$color.zhihu_check_original_radio_disable, getContext().getTheme());
        setChecked(false);
    }

    public void setChecked(boolean z) {
        if (z) {
            setImageResource(R$drawable.fq_ic_matisse_preview_radio_on);
            this.mDrawable = getDrawable();
            this.mDrawable.setColorFilter(this.mSelectedColor, PorterDuff.Mode.SRC_IN);
            return;
        }
        setImageResource(R$drawable.fq_ic_matisse_preview_radio_off);
        this.mDrawable = getDrawable();
        this.mDrawable.setColorFilter(this.mUnSelectUdColor, PorterDuff.Mode.SRC_IN);
    }

    public void setColor(int i) {
        if (this.mDrawable == null) {
            this.mDrawable = getDrawable();
        }
        this.mDrawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
    }
}
