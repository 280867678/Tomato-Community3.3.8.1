package com.facebook.imagepipeline.bitmaps;

import com.facebook.common.webp.BitmapCreator;
import com.facebook.imagepipeline.memory.PoolFactory;

/* loaded from: classes2.dex */
public class HoneycombBitmapCreator implements BitmapCreator {
    public HoneycombBitmapCreator(PoolFactory poolFactory) {
        poolFactory.getFlexByteArrayPool();
        new EmptyJpegGenerator(poolFactory.getPooledByteBufferFactory());
    }
}
