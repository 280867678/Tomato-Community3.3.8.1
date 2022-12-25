package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Map;

/* loaded from: classes2.dex */
public class EncodedMemoryCacheProducer implements Producer<EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;

    public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> producer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("EncodedMemoryCacheProducer#produceResults");
            }
            String id = producerContext.getId();
            ProducerListener listener = producerContext.getListener();
            listener.onProducerStart(id, "EncodedMemoryCacheProducer");
            CacheKey encodedCacheKey = this.mCacheKeyFactory.getEncodedCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext());
            CloseableReference<PooledByteBuffer> closeableReference = this.mMemoryCache.get(encodedCacheKey);
            Map<String, String> map = null;
            if (closeableReference != null) {
                EncodedImage encodedImage = new EncodedImage(closeableReference);
                try {
                    if (listener.requiresExtraMap(id)) {
                        map = ImmutableMap.m4167of("cached_value_found", "true");
                    }
                    listener.onProducerFinishWithSuccess(id, "EncodedMemoryCacheProducer", map);
                    listener.onUltimateProducerReached(id, "EncodedMemoryCacheProducer", true);
                    consumer.onProgressUpdate(1.0f);
                    consumer.onNewResult(encodedImage, 1);
                    CloseableReference.closeSafely(closeableReference);
                } finally {
                    EncodedImage.closeSafely(encodedImage);
                }
            } else if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
                listener.onProducerFinishWithSuccess(id, "EncodedMemoryCacheProducer", listener.requiresExtraMap(id) ? ImmutableMap.m4167of("cached_value_found", "false") : null);
                listener.onUltimateProducerReached(id, "EncodedMemoryCacheProducer", false);
                consumer.onNewResult(null, 1);
                CloseableReference.closeSafely(closeableReference);
                if (!FrescoSystrace.isTracing()) {
                    return;
                }
                FrescoSystrace.endSection();
            } else {
                EncodedMemoryCacheConsumer encodedMemoryCacheConsumer = new EncodedMemoryCacheConsumer(consumer, this.mMemoryCache, encodedCacheKey, producerContext.getImageRequest().isMemoryCacheEnabled());
                if (listener.requiresExtraMap(id)) {
                    map = ImmutableMap.m4167of("cached_value_found", "false");
                }
                listener.onProducerFinishWithSuccess(id, "EncodedMemoryCacheProducer", map);
                this.mInputProducer.produceResults(encodedMemoryCacheConsumer, producerContext);
                CloseableReference.closeSafely(closeableReference);
                if (!FrescoSystrace.isTracing()) {
                    return;
                }
                FrescoSystrace.endSection();
            }
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class EncodedMemoryCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final boolean mIsMemoryCacheEnabled;
        private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;
        private final CacheKey mRequestedCacheKey;

        public EncodedMemoryCacheConsumer(Consumer<EncodedImage> consumer, MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKey cacheKey, boolean z) {
            super(consumer);
            this.mMemoryCache = memoryCache;
            this.mRequestedCacheKey = cacheKey;
            this.mIsMemoryCacheEnabled = z;
        }

        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage encodedImage, int i) {
            boolean isTracing;
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("EncodedMemoryCacheProducer#onNewResultImpl");
                }
                if (!BaseConsumer.isNotLast(i) && encodedImage != null && !BaseConsumer.statusHasAnyFlag(i, 10) && encodedImage.getImageFormat() != ImageFormat.UNKNOWN) {
                    CloseableReference<PooledByteBuffer> byteBufferRef = encodedImage.getByteBufferRef();
                    if (byteBufferRef != null) {
                        CloseableReference<PooledByteBuffer> closeableReference = null;
                        if (this.mIsMemoryCacheEnabled) {
                            closeableReference = this.mMemoryCache.cache(this.mRequestedCacheKey, byteBufferRef);
                        }
                        CloseableReference.closeSafely(byteBufferRef);
                        if (closeableReference != null) {
                            EncodedImage encodedImage2 = new EncodedImage(closeableReference);
                            encodedImage2.copyMetaDataFrom(encodedImage);
                            CloseableReference.closeSafely(closeableReference);
                            getConsumer().onProgressUpdate(1.0f);
                            getConsumer().onNewResult(encodedImage2, i);
                            EncodedImage.closeSafely(encodedImage2);
                            if (!isTracing) {
                                return;
                            }
                            return;
                        }
                    }
                    getConsumer().onNewResult(encodedImage, i);
                    if (!FrescoSystrace.isTracing()) {
                        return;
                    }
                    FrescoSystrace.endSection();
                    return;
                }
                getConsumer().onNewResult(encodedImage, i);
                if (!FrescoSystrace.isTracing()) {
                    return;
                }
                FrescoSystrace.endSection();
            } finally {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }
        }
    }
}
