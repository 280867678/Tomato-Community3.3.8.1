package sj.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import sj.keyboard.interfaces.EmoticonFilter;

/* loaded from: classes4.dex */
public class EmoticonsEditText extends EditText {
    private List<EmoticonFilter> mFilterList;
    OnBackKeyClickListener onBackKeyClickListener;
    OnSizeChangedListener onSizeChangedListener;

    /* loaded from: classes4.dex */
    public interface OnBackKeyClickListener {
        void onBackKeyClick();
    }

    /* loaded from: classes4.dex */
    public interface OnSizeChangedListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    public EmoticonsEditText(Context context) {
        this(context, null);
    }

    public EmoticonsEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmoticonsEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        try {
            super.onMeasure(i, i2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            setText(getText().toString());
            super.onMeasure(i, i2);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        OnSizeChangedListener onSizeChangedListener;
        super.onSizeChanged(i, i2, i3, i4);
        if (i4 <= 0 || (onSizeChangedListener = this.onSizeChangedListener) == null) {
            return;
        }
        onSizeChangedListener.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.TextView
    public void setGravity(int i) {
        try {
            super.setGravity(i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            setText(getText().toString());
            super.setGravity(i);
        }
    }

    @Override // android.widget.EditText, android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        try {
            super.setText(charSequence, bufferType);
        } catch (ArrayIndexOutOfBoundsException unused) {
            setText(charSequence.toString());
        }
    }

    @Override // android.widget.TextView
    protected final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        List<EmoticonFilter> list = this.mFilterList;
        if (list == null) {
            return;
        }
        for (EmoticonFilter emoticonFilter : list) {
            emoticonFilter.filter(this, charSequence, i, i2, i3);
        }
    }

    public void addEmoticonFilter(EmoticonFilter emoticonFilter) {
        if (this.mFilterList == null) {
            this.mFilterList = new ArrayList();
        }
        this.mFilterList.add(emoticonFilter);
    }

    public void removedEmoticonFilter(EmoticonFilter emoticonFilter) {
        List<EmoticonFilter> list = this.mFilterList;
        if (list == null || !list.contains(emoticonFilter)) {
            return;
        }
        this.mFilterList.remove(emoticonFilter);
    }

    @Override // android.view.View
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        OnBackKeyClickListener onBackKeyClickListener = this.onBackKeyClickListener;
        if (onBackKeyClickListener != null) {
            onBackKeyClickListener.onBackKeyClick();
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }

    public void setOnBackKeyClickListener(OnBackKeyClickListener onBackKeyClickListener) {
        this.onBackKeyClickListener = onBackKeyClickListener;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {
        this.onSizeChangedListener = onSizeChangedListener;
    }
}
