package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;

/* loaded from: classes2.dex */
public class PoolFactory {
    private BitmapPool mBitmapPool;
    private BufferMemoryChunkPool mBufferMemoryChunkPool;
    private final PoolConfig mConfig;
    private FlexByteArrayPool mFlexByteArrayPool;
    private NativeMemoryChunkPool mNativeMemoryChunkPool;
    private PooledByteBufferFactory mPooledByteBufferFactory;
    private PooledByteStreams mPooledByteStreams;
    private ByteArrayPool mSmallByteArrayPool;

    public PoolFactory(PoolConfig poolConfig) {
        Preconditions.checkNotNull(poolConfig);
        this.mConfig = poolConfig;
    }

    public BitmapPool getBitmapPool() {
        if (this.mBitmapPool == null) {
            String bitmapPoolType = this.mConfig.getBitmapPoolType();
            char c = 65535;
            switch (bitmapPoolType.hashCode()) {
                case -1868884870:
                    if (bitmapPoolType.equals("legacy_default_params")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1106578487:
                    if (bitmapPoolType.equals("legacy")) {
                        c = 3;
                        break;
                    }
                    break;
                case -404562712:
                    if (bitmapPoolType.equals("experimental")) {
                        c = 1;
                        break;
                    }
                    break;
                case 95945896:
                    if (bitmapPoolType.equals("dummy")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                this.mBitmapPool = new DummyBitmapPool();
            } else if (c == 1) {
                this.mBitmapPool = new LruBitmapPool(this.mConfig.getBitmapPoolMaxPoolSize(), this.mConfig.getBitmapPoolMaxBitmapSize(), NoOpPoolStatsTracker.getInstance());
            } else if (c == 2) {
                this.mBitmapPool = new BucketsBitmapPool(this.mConfig.getMemoryTrimmableRegistry(), DefaultBitmapPoolParams.get(), this.mConfig.getBitmapPoolStatsTracker());
            } else {
                this.mBitmapPool = new BucketsBitmapPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getBitmapPoolParams(), this.mConfig.getBitmapPoolStatsTracker());
            }
        }
        return this.mBitmapPool;
    }

    public BufferMemoryChunkPool getBufferMemoryChunkPool() {
        if (this.mBufferMemoryChunkPool == null) {
            this.mBufferMemoryChunkPool = new BufferMemoryChunkPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
        }
        return this.mBufferMemoryChunkPool;
    }

    public FlexByteArrayPool getFlexByteArrayPool() {
        if (this.mFlexByteArrayPool == null) {
            this.mFlexByteArrayPool = new FlexByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getFlexByteArrayPoolParams());
        }
        return this.mFlexByteArrayPool;
    }

    public int getFlexByteArrayPoolMaxNumThreads() {
        return this.mConfig.getFlexByteArrayPoolParams().maxNumThreads;
    }

    public NativeMemoryChunkPool getNativeMemoryChunkPool() {
        if (this.mNativeMemoryChunkPool == null) {
            this.mNativeMemoryChunkPool = new NativeMemoryChunkPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
        }
        return this.mNativeMemoryChunkPool;
    }

    public PooledByteBufferFactory getPooledByteBufferFactory() {
        return getPooledByteBufferFactory(0);
    }

    public PooledByteBufferFactory getPooledByteBufferFactory(int i) {
        if (this.mPooledByteBufferFactory == null) {
            this.mPooledByteBufferFactory = new MemoryPooledByteBufferFactory(getMemoryChunkPool(i), getPooledByteStreams());
        }
        return this.mPooledByteBufferFactory;
    }

    public PooledByteStreams getPooledByteStreams() {
        if (this.mPooledByteStreams == null) {
            this.mPooledByteStreams = new PooledByteStreams(getSmallByteArrayPool());
        }
        return this.mPooledByteStreams;
    }

    public ByteArrayPool getSmallByteArrayPool() {
        if (this.mSmallByteArrayPool == null) {
            this.mSmallByteArrayPool = new GenericByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getSmallByteArrayPoolParams(), this.mConfig.getSmallByteArrayPoolStatsTracker());
        }
        return this.mSmallByteArrayPool;
    }

    private MemoryChunkPool getMemoryChunkPool(int i) {
        if (i != 0) {
            if (i == 1) {
                return getBufferMemoryChunkPool();
            }
            throw new IllegalArgumentException("Invalid MemoryChunkType");
        }
        return getNativeMemoryChunkPool();
    }
}
