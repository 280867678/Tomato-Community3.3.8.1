package com.zzhoujay.richtext.callback;

import com.zzhoujay.richtext.ImageHolder;

/* loaded from: classes4.dex */
public interface ImageFixCallback {
    void onFailure(ImageHolder imageHolder, Exception exc);

    void onImageReady(ImageHolder imageHolder, int i, int i2);

    void onInit(ImageHolder imageHolder);

    void onLoading(ImageHolder imageHolder);

    void onSizeReady(ImageHolder imageHolder, int i, int i2, ImageHolder.SizeHolder sizeHolder);
}
