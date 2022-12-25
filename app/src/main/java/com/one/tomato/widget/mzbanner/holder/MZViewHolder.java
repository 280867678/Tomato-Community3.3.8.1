package com.one.tomato.widget.mzbanner.holder;

import android.content.Context;
import android.view.View;

/* loaded from: classes3.dex */
public interface MZViewHolder<T> {
    View createView(Context context);

    void onBind(Context context, int i, T t);
}
