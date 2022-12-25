package com.tomatolive.library.p136ui.view.widget.marqueen;

import android.content.Context;
import android.widget.TextView;
import java.lang.CharSequence;

/* renamed from: com.tomatolive.library.ui.view.widget.marqueen.SimpleMF */
/* loaded from: classes4.dex */
public class SimpleMF<E extends CharSequence> extends MarqueeFactory<TextView, E> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeFactory
    public /* bridge */ /* synthetic */ TextView generateMarqueeItemView(Object obj) {
        return generateMarqueeItemView((SimpleMF<E>) ((CharSequence) obj));
    }

    public SimpleMF(Context context) {
        super(context);
    }

    public TextView generateMarqueeItemView(E e) {
        TextView textView = new TextView(this.mContext);
        textView.setText(e);
        return textView;
    }
}
