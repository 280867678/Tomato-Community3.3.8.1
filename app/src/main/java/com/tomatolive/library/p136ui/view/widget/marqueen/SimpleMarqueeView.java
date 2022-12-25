package com.tomatolive.library.p136ui.view.widget.marqueen;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.p136ui.view.widget.marqueen.utli.Util;

/* renamed from: com.tomatolive.library.ui.view.widget.marqueen.SimpleMarqueeView */
/* loaded from: classes4.dex */
public class SimpleMarqueeView<E> extends MarqueeView<TextView, E> {
    private ColorStateList smvTextColor;
    private TextUtils.TruncateAt smvTextEllipsize;
    private int smvTextGravity;
    private boolean smvTextSingleLine;
    private float smvTextSize;

    public SimpleMarqueeView(Context context) {
        this(context, null);
    }

    public SimpleMarqueeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.smvTextColor = null;
        this.smvTextSize = 15.0f;
        this.smvTextGravity = 0;
        this.smvTextSingleLine = false;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        int i = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SimpleMarqueeView, 0, 0);
            this.smvTextColor = obtainStyledAttributes.getColorStateList(R$styleable.SimpleMarqueeView_smvTextColor);
            if (obtainStyledAttributes.hasValue(R$styleable.SimpleMarqueeView_smvTextSize)) {
                this.smvTextSize = obtainStyledAttributes.getDimension(R$styleable.SimpleMarqueeView_smvTextSize, this.smvTextSize);
                this.smvTextSize = Util.px2Sp(getContext(), this.smvTextSize);
            }
            this.smvTextGravity = obtainStyledAttributes.getInt(R$styleable.SimpleMarqueeView_smvTextGravity, this.smvTextGravity);
            this.smvTextSingleLine = obtainStyledAttributes.getBoolean(R$styleable.SimpleMarqueeView_smvTextSingleLine, this.smvTextSingleLine);
            i = obtainStyledAttributes.getInt(R$styleable.SimpleMarqueeView_smvTextEllipsize, -1);
            obtainStyledAttributes.recycle();
        }
        if (this.smvTextSingleLine && i < 0) {
            i = 3;
        }
        if (i == 1) {
            this.smvTextEllipsize = TextUtils.TruncateAt.START;
        } else if (i == 2) {
            this.smvTextEllipsize = TextUtils.TruncateAt.MIDDLE;
        } else if (i != 3) {
        } else {
            this.smvTextEllipsize = TextUtils.TruncateAt.END;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeView
    public void refreshChildViews() {
        super.refreshChildViews();
        for (TextView textView : this.factory.getMarqueeViews()) {
            textView.setTextSize(this.smvTextSize);
            textView.setGravity(this.smvTextGravity);
            ColorStateList colorStateList = this.smvTextColor;
            if (colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
            textView.setSingleLine(this.smvTextSingleLine);
            textView.setEllipsize(this.smvTextEllipsize);
        }
    }

    public void setTextSize(float f) {
        this.smvTextSize = f;
        MarqueeFactory<T, E> marqueeFactory = this.factory;
        if (marqueeFactory != 0) {
            for (TextView textView : marqueeFactory.getMarqueeViews()) {
                textView.setTextSize(f);
            }
        }
    }

    public void setTextColor(@ColorInt int i) {
        setTextColor(ColorStateList.valueOf(i));
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.smvTextColor = colorStateList;
        MarqueeFactory<T, E> marqueeFactory = this.factory;
        if (marqueeFactory != 0) {
            for (TextView textView : marqueeFactory.getMarqueeViews()) {
                textView.setTextColor(this.smvTextColor);
            }
        }
    }

    public void setTextGravity(int i) {
        this.smvTextGravity = i;
        MarqueeFactory<T, E> marqueeFactory = this.factory;
        if (marqueeFactory != 0) {
            for (TextView textView : marqueeFactory.getMarqueeViews()) {
                textView.setGravity(this.smvTextGravity);
            }
        }
    }

    public void setTextSingleLine(boolean z) {
        this.smvTextSingleLine = z;
        MarqueeFactory<T, E> marqueeFactory = this.factory;
        if (marqueeFactory != 0) {
            for (TextView textView : marqueeFactory.getMarqueeViews()) {
                textView.setSingleLine(this.smvTextSingleLine);
            }
        }
    }

    public void setTextEllipsize(TextUtils.TruncateAt truncateAt) {
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) {
            throw new UnsupportedOperationException("The type MARQUEE is not supported!");
        }
        this.smvTextEllipsize = truncateAt;
        MarqueeFactory<T, E> marqueeFactory = this.factory;
        if (marqueeFactory == 0) {
            return;
        }
        for (TextView textView : marqueeFactory.getMarqueeViews()) {
            textView.setEllipsize(truncateAt);
        }
    }
}
