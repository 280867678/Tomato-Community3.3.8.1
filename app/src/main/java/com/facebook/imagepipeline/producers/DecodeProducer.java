package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.decoder.DecodeException;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    private final ByteArrayPool mByteArrayPool;
    private final boolean mDecodeCancellationEnabled;
    private final boolean mDownsampleEnabled;
    private final boolean mDownsampleEnabledForNetwork;
    private final Executor mExecutor;
    private final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final int mMaxBitmapSize;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    public DecodeProducer(ByteArrayPool byteArrayPool, Executor executor, ImageDecoder imageDecoder, ProgressiveJpegConfig progressiveJpegConfig, boolean z, boolean z2, boolean z3, Producer<EncodedImage> producer, int i) {
        Preconditions.checkNotNull(byteArrayPool);
        this.mByteArrayPool = byteArrayPool;
        Preconditions.checkNotNull(executor);
        this.mExecutor = executor;
        Preconditions.checkNotNull(imageDecoder);
        this.mImageDecoder = imageDecoder;
        Preconditions.checkNotNull(progressiveJpegConfig);
        this.mProgressiveJpegConfig = progressiveJpegConfig;
        this.mDownsampleEnabled = z;
        this.mDownsampleEnabledForNetwork = z2;
        Preconditions.checkNotNull(producer);
        this.mInputProducer = producer;
        this.mDecodeCancellationEnabled = z3;
        this.mMaxBitmapSize = i;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        Consumer<EncodedImage> networkImagesProgressiveDecoder;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("DecodeProducer#produceResults");
            }
            if (!UriUtil.isNetworkUri(producerContext.getImageRequest().getSourceUri())) {
                networkImagesProgressiveDecoder = new LocalImagesProgressiveDecoder(this, consumer, producerContext, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            } else {
                networkImagesProgressiveDecoder = new NetworkImagesProgressiveDecoder(this, consumer, producerContext, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            }
            this.mInputProducer.produceResults(networkImagesProgressiveDecoder, producerContext);
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    /* loaded from: classes2.dex */
    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private final ImageDecodeOptions mImageDecodeOptions;
        private boolean mIsFinished = false;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;
        private final ProducerListener mProducerListener;

        protected abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        protected abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, boolean z, int i) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getListener();
            this.mImageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, new JobScheduler.JobRunnable(DecodeProducer.this, producerContext, i) { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.1
                final /* synthetic */ int val$maxBitmapSize;
                final /* synthetic */ ProducerContext val$producerContext;

                {
                    this.val$producerContext = producerContext;
                    this.val$maxBitmapSize = i;
                }

                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, int i2) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled || !BaseConsumer.statusHasFlag(i2, 16)) {
                            ImageRequest imageRequest = this.val$producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(imageRequest.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(imageRequest.getRotationOptions(), imageRequest.getResizeOptions(), encodedImage, this.val$maxBitmapSize));
                            }
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, i2);
                    }
                }
            }, this.mImageDecodeOptions.minDecodeIntervalMs);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(DecodeProducer.this, z) { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.2
                final /* synthetic */ boolean val$decodeCancellationEnabled;

                {
                    this.val$decodeCancellationEnabled = z;
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    if (this.val$decodeCancellationEnabled) {
                        ProgressiveDecoder.this.handleCancellation();
                    }
                }
            });
        }

        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage encodedImage, int i) {
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("DecodeProducer#onNewResultImpl");
                }
                boolean isLast = BaseConsumer.isLast(i);
                if (isLast && !EncodedImage.isValid(encodedImage)) {
                    handleError(new ExceptionWithNoStacktrace("Encoded image is not valid."));
                } else if (!updateDecodeJob(encodedImage, i)) {
                    if (!FrescoSystrace.isTracing()) {
                        return;
                    }
                    FrescoSystrace.endSection();
                } else {
                    boolean statusHasFlag = BaseConsumer.statusHasFlag(i, 4);
                    if (isLast || statusHasFlag || this.mProducerContext.isIntermediateResultExpected()) {
                        this.mJobScheduler.scheduleJob();
                    }
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

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onProgressUpdateImpl(float f) {
            super.onProgressUpdateImpl(f * 0.99f);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onFailureImpl(Throwable th) {
            handleError(th);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onCancellationImpl() {
            handleCancellation();
        }

        protected boolean updateDecodeJob(EncodedImage encodedImage, int i) {
            return this.mJobScheduler.updateJob(encodedImage, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00e0  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doDecode(EncodedImage encodedImage, int i) {
            ResizeOptions resizeOptions;
            int size;
            QualityInfo qualityInfo;
            CloseableImage closeableImage;
            CloseableImage decode;
            int i2 = i;
            if ((encodedImage.getImageFormat() == DefaultImageFormats.JPEG || !BaseConsumer.isNotLast(i)) && !isFinished() && EncodedImage.isValid(encodedImage)) {
                ImageFormat imageFormat = encodedImage.getImageFormat();
                String str = "unknown";
                String name = imageFormat != null ? imageFormat.getName() : str;
                String str2 = encodedImage.getWidth() + "x" + encodedImage.getHeight();
                String valueOf = String.valueOf(encodedImage.getSampleSize());
                boolean isLast = BaseConsumer.isLast(i);
                boolean z = isLast && !BaseConsumer.statusHasFlag(i2, 8);
                boolean statusHasFlag = BaseConsumer.statusHasFlag(i2, 4);
                if (this.mProducerContext.getImageRequest().getResizeOptions() != null) {
                    str = resizeOptions.width + "x" + resizeOptions.height;
                }
                String str3 = str;
                try {
                    long queuedTime = this.mJobScheduler.getQueuedTime();
                    String valueOf2 = String.valueOf(this.mProducerContext.getImageRequest().getSourceUri());
                    try {
                        try {
                            try {
                                if (!z && !statusHasFlag) {
                                    size = getIntermediateImageEndOffset(encodedImage);
                                    if (!z && !statusHasFlag) {
                                        qualityInfo = getQualityInfo();
                                        this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                                        decode = DecodeProducer.this.mImageDecoder.decode(encodedImage, size, qualityInfo, this.mImageDecodeOptions);
                                        if (encodedImage.getSampleSize() != 1) {
                                            i2 |= 16;
                                        }
                                        this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", getExtraMap(decode, queuedTime, qualityInfo, isLast, name, str2, str3, valueOf));
                                        handleResult(decode, i2);
                                        return;
                                    }
                                    qualityInfo = ImmutableQualityInfo.FULL_QUALITY;
                                    this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                                    decode = DecodeProducer.this.mImageDecoder.decode(encodedImage, size, qualityInfo, this.mImageDecodeOptions);
                                    if (encodedImage.getSampleSize() != 1) {
                                    }
                                    this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", getExtraMap(decode, queuedTime, qualityInfo, isLast, name, str2, str3, valueOf));
                                    handleResult(decode, i2);
                                    return;
                                }
                                if (!z) {
                                    qualityInfo = getQualityInfo();
                                    this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                                    decode = DecodeProducer.this.mImageDecoder.decode(encodedImage, size, qualityInfo, this.mImageDecodeOptions);
                                    if (encodedImage.getSampleSize() != 1) {
                                    }
                                    this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", getExtraMap(decode, queuedTime, qualityInfo, isLast, name, str2, str3, valueOf));
                                    handleResult(decode, i2);
                                    return;
                                }
                                if (encodedImage.getSampleSize() != 1) {
                                }
                                this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", getExtraMap(decode, queuedTime, qualityInfo, isLast, name, str2, str3, valueOf));
                                handleResult(decode, i2);
                                return;
                            } catch (Exception e) {
                                e = e;
                                closeableImage = decode;
                                this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), "DecodeProducer", e, getExtraMap(closeableImage, queuedTime, qualityInfo, isLast, name, str2, str3, valueOf));
                                handleError(e);
                                return;
                            }
                            decode = DecodeProducer.this.mImageDecoder.decode(encodedImage, size, qualityInfo, this.mImageDecodeOptions);
                        } catch (DecodeException e2) {
                            EncodedImage encodedImage2 = e2.getEncodedImage();
                            FLog.m4138w("ProgressiveDecoder", "%s, {uri: %s, firstEncodedBytes: %s, length: %d}", e2.getMessage(), valueOf2, encodedImage2.getFirstBytesAsHexString(10), Integer.valueOf(encodedImage2.getSize()));
                            throw e2;
                        }
                        this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                    } catch (Exception e3) {
                        e = e3;
                        closeableImage = null;
                    }
                    size = encodedImage.getSize();
                    qualityInfo = ImmutableQualityInfo.FULL_QUALITY;
                } finally {
                    EncodedImage.closeSafely(encodedImage);
                }
            }
        }

        private Map<String, String> getExtraMap(CloseableImage closeableImage, long j, QualityInfo qualityInfo, boolean z, String str, String str2, String str3, String str4) {
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String valueOf = String.valueOf(j);
            String valueOf2 = String.valueOf(qualityInfo.isOfGoodEnoughQuality());
            String valueOf3 = String.valueOf(z);
            if (closeableImage instanceof CloseableStaticBitmap) {
                Bitmap underlyingBitmap = ((CloseableStaticBitmap) closeableImage).getUnderlyingBitmap();
                HashMap hashMap = new HashMap(8);
                hashMap.put("bitmapSize", underlyingBitmap.getWidth() + "x" + underlyingBitmap.getHeight());
                hashMap.put("queueTime", valueOf);
                hashMap.put("hasGoodQuality", valueOf2);
                hashMap.put("isFinal", valueOf3);
                hashMap.put("encodedImageSize", str2);
                hashMap.put("imageFormat", str);
                hashMap.put("requestedImageSize", str3);
                hashMap.put("sampleSize", str4);
                return ImmutableMap.copyOf((Map) hashMap);
            }
            HashMap hashMap2 = new HashMap(7);
            hashMap2.put("queueTime", valueOf);
            hashMap2.put("hasGoodQuality", valueOf2);
            hashMap2.put("isFinal", valueOf3);
            hashMap2.put("encodedImageSize", str2);
            hashMap2.put("imageFormat", str);
            hashMap2.put("requestedImageSize", str3);
            hashMap2.put("sampleSize", str4);
            return ImmutableMap.copyOf((Map) hashMap2);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        private void maybeFinish(boolean z) {
            synchronized (this) {
                if (z) {
                    if (!this.mIsFinished) {
                        getConsumer().onProgressUpdate(1.0f);
                        this.mIsFinished = true;
                        this.mJobScheduler.clearJob();
                    }
                }
            }
        }

        private void handleResult(CloseableImage closeableImage, int i) {
            CloseableReference<CloseableImage> m4130of = CloseableReference.m4130of(closeableImage);
            try {
                maybeFinish(BaseConsumer.isLast(i));
                getConsumer().onNewResult(m4130of, i);
            } finally {
                CloseableReference.closeSafely(m4130of);
            }
        }

        private void handleError(Throwable th) {
            maybeFinish(true);
            getConsumer().onFailure(th);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    /* loaded from: classes2.dex */
    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(DecodeProducer decodeProducer, Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, boolean z, int i) {
            super(consumer, producerContext, z, i);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(EncodedImage encodedImage, int i) {
            if (BaseConsumer.isNotLast(i)) {
                return false;
            }
            return super.updateDecodeJob(encodedImage, i);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.m4127of(0, false, false);
        }
    }

    /* loaded from: classes2.dex */
    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber = 0;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(DecodeProducer decodeProducer, Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig, boolean z, int i) {
            super(consumer, producerContext, z, i);
            Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegParser = progressiveJpegParser;
            Preconditions.checkNotNull(progressiveJpegConfig);
            this.mProgressiveJpegConfig = progressiveJpegConfig;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(EncodedImage encodedImage, int i) {
            boolean updateDecodeJob = super.updateDecodeJob(encodedImage, i);
            if ((BaseConsumer.isNotLast(i) || BaseConsumer.statusHasFlag(i, 8)) && !BaseConsumer.statusHasFlag(i, 4) && EncodedImage.isValid(encodedImage) && encodedImage.getImageFormat() == DefaultImageFormats.JPEG) {
                if (!this.mProgressiveJpegParser.parseMoreData(encodedImage)) {
                    return false;
                }
                int bestScanNumber = this.mProgressiveJpegParser.getBestScanNumber();
                if (bestScanNumber <= this.mLastScheduledScanNumber) {
                    return false;
                }
                if (bestScanNumber < this.mProgressiveJpegConfig.getNextScanNumberToDecode(this.mLastScheduledScanNumber) && !this.mProgressiveJpegParser.isEndMarkerRead()) {
                    return false;
                }
                this.mLastScheduledScanNumber = bestScanNumber;
            }
            return updateDecodeJob;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }
}
