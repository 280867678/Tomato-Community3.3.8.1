package com.tomatolive.library.p136ui.view.widget.matisse.filter;

import android.content.Context;
import com.tomatolive.library.p136ui.view.widget.matisse.MimeType;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.IncapableCause;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import java.util.Set;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.filter.Filter */
/* loaded from: classes4.dex */
public abstract class Filter {

    /* renamed from: K */
    public static final int f5872K = 1024;
    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = 0;

    protected abstract Set<MimeType> constraintTypes();

    public abstract IncapableCause filter(Context context, Item item);

    protected boolean needFiltering(Context context, Item item) {
        for (MimeType mimeType : constraintTypes()) {
            if (mimeType.checkType(context.getContentResolver(), item.getContentUri())) {
                return true;
            }
        }
        return false;
    }
}
