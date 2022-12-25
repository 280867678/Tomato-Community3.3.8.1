package org.xutils.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/* loaded from: classes4.dex */
final class ReusableBitmapDrawable extends BitmapDrawable implements ReusableDrawable {
    private MemCacheKey key;

    public ReusableBitmapDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override // org.xutils.image.ReusableDrawable
    public MemCacheKey getMemCacheKey() {
        return this.key;
    }

    @Override // org.xutils.image.ReusableDrawable
    public void setMemCacheKey(MemCacheKey memCacheKey) {
        this.key = memCacheKey;
    }
}
