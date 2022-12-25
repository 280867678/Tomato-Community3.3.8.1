package com.gen.p059mh.webapp_extensions.matisse.filter;

import android.content.Context;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.filter.Filter */
/* loaded from: classes2.dex */
public abstract class Filter {

    /* renamed from: K */
    public static final int f1291K = 1024;
    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = 0;

    protected abstract Set<MimeType> constraintTypes();

    public abstract IncapableCause filter(Context context, Item item);

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean needFiltering(Context context, Item item) {
        for (MimeType mimeType : constraintTypes()) {
            if (mimeType.checkType(context.getContentResolver(), item.getContentUri())) {
                return true;
            }
        }
        return false;
    }
}
