package com.gen.p059mh.webapp_extensions.matisse;

import android.content.Context;
import android.graphics.Point;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.filter.Filter;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.GifSizeFilter */
/* loaded from: classes2.dex */
public class GifSizeFilter extends Filter {
    private int mMaxSize;
    private int mMinHeight;
    private int mMinWidth;

    public GifSizeFilter(int i, int i2, int i3) {
        this.mMinWidth = i;
        this.mMinHeight = i2;
        this.mMaxSize = i3;
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.filter.Filter
    public Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() { // from class: com.gen.mh.webapp_extensions.matisse.GifSizeFilter.1
            {
                add(MimeType.GIF);
            }
        };
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.filter.Filter
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item)) {
            return null;
        }
        Point bitmapBound = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
        if (bitmapBound.x >= this.mMinWidth && bitmapBound.y >= this.mMinHeight && item.size <= this.mMaxSize) {
            return null;
        }
        return new IncapableCause(1, context.getString(R$string.error_gif, Integer.valueOf(this.mMinWidth), String.valueOf(PhotoMetadataUtils.getSizeInMB(this.mMaxSize))));
    }
}
