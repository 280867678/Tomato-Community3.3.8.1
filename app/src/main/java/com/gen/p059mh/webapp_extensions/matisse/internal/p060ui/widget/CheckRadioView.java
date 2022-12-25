package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.res.ResourcesCompat;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$drawable;
import com.gen.p059mh.webapp_extensions.R$mipmap;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.widget.CheckRadioView */
/* loaded from: classes2.dex */
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
            setImageResource(R$mipmap.ic_preview_radio_on);
            this.mDrawable = getDrawable();
            this.mDrawable.setColorFilter(this.mSelectedColor, PorterDuff.Mode.SRC_IN);
            return;
        }
        setImageResource(R$drawable.ic_preview_radio_off);
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
