package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/* loaded from: classes2.dex */
public class NetworkFetchProducer implements Producer<EncodedImage> {
    private final ByteArrayPool mByteArrayPool;
    private final NetworkFetcher mNetworkFetcher;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public NetworkFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, NetworkFetcher networkFetcher) {
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mNetworkFetcher = networkFetcher;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        producerContext.getListener().onProducerStart(producerContext.getId(), "NetworkFetchProducer");
        final FetchState createFetchState = this.mNetworkFetcher.createFetchState(consumer, producerContext);
        this.mNetworkFetcher.fetch(createFetchState, new NetworkFetcher.Callback() { // from class: com.facebook.imagepipeline.producers.NetworkFetchProducer.1
            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onResponse(InputStream inputStream, int i) throws IOException {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("NetworkFetcher->onResponse");
                }
                NetworkFetchProducer.this.onResponse(createFetchState, inputStream, i);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onFailure(Throwable th) {
                NetworkFetchProducer.this.onFailure(createFetchState, th);
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onCancellation() {
                NetworkFetchProducer.this.onCancellation(createFetchState);
            }
        });
    }

    protected void onResponse(FetchState fetchState, InputStream inputStream, int i) throws IOException {
        PooledByteBufferOutputStream mo5952newOutputStream;
        if (i > 0) {
            mo5952newOutputStream = this.mPooledByteBufferFactory.mo5953newOutputStream(i);
        } else {
            mo5952newOutputStream = this.mPooledByteBufferFactory.mo5952newOutputStream();
        }
        byte[] mo5947get = this.mByteArrayPool.mo5947get(16384);
        while (true) {
            try {
                int read = inputStream.read(mo5947get);
                if (read < 0) {
                    this.mNetworkFetcher.onFetchCompletion(fetchState, mo5952newOutputStream.size());
                    handleFinalResult(mo5952newOutputStream, fetchState);
                    return;
                } else if (read > 0) {
                    mo5952newOutputStream.write(mo5947get, 0, read);
                    maybeHandleIntermediateResult(mo5952newOutputStream, fetchState);
                    fetchState.getConsumer().onProgressUpdate(calculateProgress(mo5952newOutputStream.size(), i));
                }
            } finally {
                this.mByteArrayPool.release(mo5947get);
                mo5952newOutputStream.close();
            }
        }
    }

    protected static float calculateProgress(int i, int i2) {
        return i2 > 0 ? i / i2 : 1.0f - ((float) Math.exp((-i) / 50000.0d));
    }

    protected void maybeHandleIntermediateResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (!shouldPropagateIntermediateResults(fetchState) || uptimeMillis - fetchState.getLastIntermediateResultTimeMs() < 100) {
            return;
        }
        fetchState.setLastIntermediateResultTimeMs(uptimeMillis);
        fetchState.getListener().onProducerEvent(fetchState.getId(), "NetworkFetchProducer", "intermediate_result");
        notifyConsumer(pooledByteBufferOutputStream, fetchState.getOnNewResultStatusFlags(), fetchState.getResponseBytesRange(), fetchState.getConsumer());
    }

    protected void handleFinalResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        Map<String, String> extraMap = getExtraMap(fetchState, pooledByteBufferOutputStream.size());
        ProducerListener listener = fetchState.getListener();
        listener.onProducerFinishWithSuccess(fetchState.getId(), "NetworkFetchProducer", extraMap);
        listener.onUltimateProducerReached(fetchState.getId(), "NetworkFetchProducer", true);
        notifyConsumer(pooledByteBufferOutputStream, fetchState.getOnNewResultStatusFlags() | 1, fetchState.getResponseBytesRange(), fetchState.getConsumer());
    }

    protected static void notifyConsumer(PooledByteBufferOutputStream pooledByteBufferOutputStream, int i, BytesRange bytesRange, Consumer<EncodedImage> consumer) {
        EncodedImage encodedImage;
        CloseableReference m4130of = CloseableReference.m4130of(pooledByteBufferOutputStream.mo5954toByteBuffer());
        try {
            encodedImage = new EncodedImage(m4130of);
        } catch (Throwable th) {
            th = th;
            encodedImage = null;
        }
        try {
            encodedImage.setBytesRange(bytesRange);
            encodedImage.parseMetaData();
            consumer.onNewResult(encodedImage, i);
            EncodedImage.closeSafely(encodedImage);
            CloseableReference.closeSafely(m4130of);
        } catch (Throwable th2) {
            th = th2;
            EncodedImage.closeSafely(encodedImage);
            CloseableReference.closeSafely(m4130of);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFailure(FetchState fetchState, Throwable th) {
        fetchState.getListener().onProducerFinishWithFailure(fetchState.getId(), "NetworkFetchProducer", th, null);
        fetchState.getListener().onUltimateProducerReached(fetchState.getId(), "NetworkFetchProducer", false);
        fetchState.getConsumer().onFailure(th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCancellation(FetchState fetchState) {
        fetchState.getListener().onProducerFinishWithCancellation(fetchState.getId(), "NetworkFetchProducer", null);
        fetchState.getConsumer().onCancellation();
    }

    private boolean shouldPropagateIntermediateResults(FetchState fetchState) {
        if (!fetchState.getContext().isIntermediateResultExpected()) {
            return false;
        }
        return this.mNetworkFetcher.shouldPropagate(fetchState);
    }

    private Map<String, String> getExtraMap(FetchState fetchState, int i) {
        if (!fetchState.getListener().requiresExtraMap(fetchState.getId())) {
            return null;
        }
        return this.mNetworkFetcher.getExtraMap(fetchState, i);
    }
}
