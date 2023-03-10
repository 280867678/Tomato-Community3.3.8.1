package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;

/* loaded from: classes2.dex */
public class PoolParams {
    public final SparseIntArray bucketSizes;
    public boolean fixBucketsReinitialization;
    public final int maxNumThreads;
    public final int maxSizeHardCap;
    public final int maxSizeSoftCap;

    public PoolParams(int i, int i2, SparseIntArray sparseIntArray) {
        this(i, i2, sparseIntArray, 0, Integer.MAX_VALUE, -1);
    }

    public PoolParams(int i, int i2, SparseIntArray sparseIntArray, int i3, int i4, int i5) {
        Preconditions.checkState(i >= 0 && i2 >= i);
        this.maxSizeSoftCap = i;
        this.maxSizeHardCap = i2;
        this.bucketSizes = sparseIntArray;
        this.maxNumThreads = i5;
    }
}
