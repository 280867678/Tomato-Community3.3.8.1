package com.tencent.rtmp.p133a;

import android.graphics.Bitmap;
import java.util.List;

/* renamed from: com.tencent.rtmp.a.a */
/* loaded from: classes3.dex */
public interface TXIImageSprite {
    Bitmap getThumbnail(float f);

    void release();

    void setVTTUrlAndImageUrls(String str, List<String> list);
}
