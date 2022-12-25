package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Map;

/* loaded from: classes2.dex */
public class BitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    protected String getProducerName() {
        return "BitmapMemoryCacheProducer";
    }

    public BitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> producer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        boolean isTracing;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BitmapMemoryCacheProducer#produceResults");
            }
            ProducerListener listener = producerContext.getListener();
            String id = producerContext.getId();
            listener.onProducerStart(id, getProducerName());
            CacheKey bitmapCacheKey = this.mCacheKeyFactory.getBitmapCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext());
            CloseableReference<CloseableImage> closeableReference = this.mMemoryCache.get(bitmapCacheKey);
            Map<String, String> map = null;
            if (closeableReference != null) {
                boolean isOfFullQuality = closeableReference.get().getQualityInfo().isOfFullQuality();
                if (isOfFullQuality) {
                    listener.onProducerFinishWithSuccess(id, getProducerName(), listener.requiresExtraMap(id) ? ImmutableMap.m4167of("cached_value_found", "true") : null);
                    listener.onUltimateProducerReached(id, getProducerName(), true);
                    consumer.onProgressUpdate(1.0f);
                }
                BaseConsumer.simpleStatusForIsLast(isOfFullQuality);
                consumer.onNewResult(closeableReference, isOfFullQuality ? 1 : 0);
                closeableReference.close();
                if (isOfFullQuality) {
                    if (!isTracing) {
                        return;
                    }
                    return;
                }
            }
            if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
                listener.onProducerFinishWithSuccess(id, getProducerName(), listener.requiresExtraMap(id) ? ImmutableMap.m4167of("cached_value_found", "false") : null);
                listener.onUltimateProducerReached(id, getProducerName(), false);
                consumer.onNewResult(null, 1);
                if (!FrescoSystrace.isTracing()) {
                    return;
                }
                FrescoSystrace.endSection();
                return;
            }
            Consumer<CloseableReference<CloseableImage>> wrapConsumer = wrapConsumer(consumer, bitmapCacheKey, producerContext.getImageRequest().isMemoryCacheEnabled());
            String producerName = getProducerName();
            if (listener.requiresExtraMap(id)) {
                map = ImmutableMap.m4167of("cached_value_found", "false");
            }
            listener.onProducerFinishWithSuccess(id, producerName, map);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("mInputProducer.produceResult");
            }
            this.mInputProducer.produceResults(wrapConsumer, producerContext);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
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

    protected Consumer<CloseableReference<CloseableImage>> wrapConsumer(Consumer<CloseableReference<CloseableImage>> consumer, final CacheKey cacheKey, final boolean z) {
        return new DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>>(consumer) { // from class: com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer.1
            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            public void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, int i) {
                CloseableReference<CloseableImage> closeableReference2;
                boolean isTracing;
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("BitmapMemoryCacheProducer#onNewResultImpl");
                    }
                    boolean isLast = BaseConsumer.isLast(i);
                    CloseableReference<CloseableImage> closeableReference3 = null;
                    if (closeableReference == null) {
                        if (isLast) {
                            getConsumer().onNewResult(null, i);
                        }
                        if (!isTracing) {
                            return;
                        }
                        return;
                    }
                    if (!closeableReference.get().isStateful() && !BaseConsumer.statusHasFlag(i, 8)) {
                        if (!isLast && (closeableReference2 = BitmapMemoryCacheProducer.this.mMemoryCache.get(cacheKey)) != null) {
                            QualityInfo qualityInfo = closeableReference.get().getQualityInfo();
                            QualityInfo qualityInfo2 = closeableReference2.get().getQualityInfo();
                            if (qualityInfo2.isOfFullQuality() || qualityInfo2.getQuality() >= qualityInfo.getQuality()) {
                                getConsumer().onNewResult(closeableReference2, i);
                                CloseableReference.closeSafely(closeableReference2);
                                if (!FrescoSystrace.isTracing()) {
                                    return;
                                }
                                FrescoSystrace.endSection();
                                return;
                            }
                            CloseableReference.closeSafely(closeableReference2);
                        }
                        if (z) {
                            closeableReference3 = BitmapMemoryCacheProducer.this.mMemoryCache.cache(cacheKey, closeableReference);
                        }
                        if (isLast) {
                            getConsumer().onProgressUpdate(1.0f);
                        }
                        Consumer<CloseableReference<CloseableImage>> consumer2 = getConsumer();
                        if (closeableReference3 != null) {
                            closeableReference = closeableReference3;
                        }
                        consumer2.onNewResult(closeableReference, i);
                        CloseableReference.closeSafely(closeableReference3);
                        if (!FrescoSystrace.isTracing()) {
                            return;
                        }
                        FrescoSystrace.endSection();
                        return;
                    }
                    getConsumer().onNewResult(closeableReference, i);
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
        };
    }
}
