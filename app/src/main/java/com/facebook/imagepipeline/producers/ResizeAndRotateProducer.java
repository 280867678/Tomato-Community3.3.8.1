package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.transcoder.ImageTranscodeResult;
import com.facebook.imagepipeline.transcoder.ImageTranscoder;
import com.facebook.imagepipeline.transcoder.ImageTranscoderFactory;
import com.facebook.imagepipeline.transcoder.JpegTranscoderUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class ResizeAndRotateProducer implements Producer<EncodedImage> {
    private final Executor mExecutor;
    private final ImageTranscoderFactory mImageTranscoderFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final boolean mIsResizingEnabled;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public ResizeAndRotateProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Producer<EncodedImage> producer, boolean z, ImageTranscoderFactory imageTranscoderFactory) {
        Preconditions.checkNotNull(executor);
        this.mExecutor = executor;
        Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        Preconditions.checkNotNull(producer);
        this.mInputProducer = producer;
        Preconditions.checkNotNull(imageTranscoderFactory);
        this.mImageTranscoderFactory = imageTranscoderFactory;
        this.mIsResizingEnabled = z;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        this.mInputProducer.produceResults(new TransformingConsumer(consumer, producerContext, this.mIsResizingEnabled, this.mImageTranscoderFactory), producerContext);
    }

    /* loaded from: classes2.dex */
    private class TransformingConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ImageTranscoderFactory mImageTranscoderFactory;
        private boolean mIsCancelled = false;
        private final boolean mIsResizingEnabled;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;

        TransformingConsumer(Consumer<EncodedImage> consumer, ProducerContext producerContext, boolean z, ImageTranscoderFactory imageTranscoderFactory) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mIsResizingEnabled = z;
            this.mImageTranscoderFactory = imageTranscoderFactory;
            this.mJobScheduler = new JobScheduler(ResizeAndRotateProducer.this.mExecutor, new JobScheduler.JobRunnable(ResizeAndRotateProducer.this) { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.1
                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, int i) {
                    TransformingConsumer transformingConsumer = TransformingConsumer.this;
                    ImageTranscoder createImageTranscoder = transformingConsumer.mImageTranscoderFactory.createImageTranscoder(encodedImage.getImageFormat(), TransformingConsumer.this.mIsResizingEnabled);
                    Preconditions.checkNotNull(createImageTranscoder);
                    transformingConsumer.doTransform(encodedImage, i, createImageTranscoder);
                }
            }, 100);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(ResizeAndRotateProducer.this, consumer) { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.2
                final /* synthetic */ Consumer val$consumer;

                {
                    this.val$consumer = consumer;
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (TransformingConsumer.this.mProducerContext.isIntermediateResultExpected()) {
                        TransformingConsumer.this.mJobScheduler.scheduleJob();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    TransformingConsumer.this.mJobScheduler.clearJob();
                    TransformingConsumer.this.mIsCancelled = true;
                    this.val$consumer.onCancellation();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage encodedImage, int i) {
            if (this.mIsCancelled) {
                return;
            }
            boolean isLast = BaseConsumer.isLast(i);
            if (encodedImage == null) {
                if (!isLast) {
                    return;
                }
                getConsumer().onNewResult(null, 1);
                return;
            }
            ImageRequest imageRequest = this.mProducerContext.getImageRequest();
            ImageTranscoder createImageTranscoder = this.mImageTranscoderFactory.createImageTranscoder(encodedImage.getImageFormat(), this.mIsResizingEnabled);
            Preconditions.checkNotNull(createImageTranscoder);
            TriState shouldTransform = ResizeAndRotateProducer.shouldTransform(imageRequest, encodedImage, createImageTranscoder);
            if (!isLast && shouldTransform == TriState.UNSET) {
                return;
            }
            if (shouldTransform != TriState.YES) {
                if (!this.mProducerContext.getImageRequest().getRotationOptions().canDeferUntilRendered() && encodedImage.getRotationAngle() != 0 && encodedImage.getRotationAngle() != -1) {
                    encodedImage = moveImage(encodedImage);
                    encodedImage.setRotationAngle(0);
                }
                getConsumer().onNewResult(encodedImage, i);
            } else if (!this.mJobScheduler.updateJob(encodedImage, i)) {
            } else {
                if (!isLast && !this.mProducerContext.isIntermediateResultExpected()) {
                    return;
                }
                this.mJobScheduler.scheduleJob();
            }
        }

        private EncodedImage moveImage(EncodedImage encodedImage) {
            EncodedImage cloneOrNull = EncodedImage.cloneOrNull(encodedImage);
            encodedImage.close();
            return cloneOrNull;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doTransform(EncodedImage encodedImage, int i, ImageTranscoder imageTranscoder) {
            this.mProducerContext.getListener().onProducerStart(this.mProducerContext.getId(), "ResizeAndRotateProducer");
            ImageRequest imageRequest = this.mProducerContext.getImageRequest();
            PooledByteBufferOutputStream mo5952newOutputStream = ResizeAndRotateProducer.this.mPooledByteBufferFactory.mo5952newOutputStream();
            try {
                ImageTranscodeResult transcode = imageTranscoder.transcode(encodedImage, mo5952newOutputStream, imageRequest.getRotationOptions(), imageRequest.getResizeOptions(), null, 85);
                if (transcode.getTranscodeStatus() == 2) {
                    throw new RuntimeException("Error while transcoding the image");
                }
                Map<String, String> extraMap = getExtraMap(encodedImage, imageRequest.getResizeOptions(), transcode, imageTranscoder.getIdentifier());
                CloseableReference m4130of = CloseableReference.m4130of(mo5952newOutputStream.mo5954toByteBuffer());
                try {
                    EncodedImage encodedImage2 = new EncodedImage(m4130of);
                    encodedImage2.setImageFormat(DefaultImageFormats.JPEG);
                    encodedImage2.parseMetaData();
                    this.mProducerContext.getListener().onProducerFinishWithSuccess(this.mProducerContext.getId(), "ResizeAndRotateProducer", extraMap);
                    if (transcode.getTranscodeStatus() != 1) {
                        i |= 16;
                    }
                    getConsumer().onNewResult(encodedImage2, i);
                    EncodedImage.closeSafely(encodedImage2);
                } finally {
                    CloseableReference.closeSafely(m4130of);
                }
            } catch (Exception e) {
                this.mProducerContext.getListener().onProducerFinishWithFailure(this.mProducerContext.getId(), "ResizeAndRotateProducer", e, null);
                if (BaseConsumer.isLast(i)) {
                    getConsumer().onFailure(e);
                }
            } finally {
                mo5952newOutputStream.close();
            }
        }

        private Map<String, String> getExtraMap(EncodedImage encodedImage, ResizeOptions resizeOptions, ImageTranscodeResult imageTranscodeResult, String str) {
            String str2;
            if (!this.mProducerContext.getListener().requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String str3 = encodedImage.getWidth() + "x" + encodedImage.getHeight();
            if (resizeOptions != null) {
                str2 = resizeOptions.width + "x" + resizeOptions.height;
            } else {
                str2 = "Unspecified";
            }
            HashMap hashMap = new HashMap();
            hashMap.put("Image format", String.valueOf(encodedImage.getImageFormat()));
            hashMap.put("Original size", str3);
            hashMap.put("Requested size", str2);
            hashMap.put("queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
            hashMap.put("Transcoder id", str);
            hashMap.put("Transcoding result", String.valueOf(imageTranscodeResult));
            return ImmutableMap.copyOf((Map) hashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TriState shouldTransform(ImageRequest imageRequest, EncodedImage encodedImage, ImageTranscoder imageTranscoder) {
        if (encodedImage == null || encodedImage.getImageFormat() == ImageFormat.UNKNOWN) {
            return TriState.UNSET;
        }
        if (!imageTranscoder.canTranscode(encodedImage.getImageFormat())) {
            return TriState.NO;
        }
        return TriState.valueOf(shouldRotate(imageRequest.getRotationOptions(), encodedImage) || imageTranscoder.canResize(encodedImage, imageRequest.getRotationOptions(), imageRequest.getResizeOptions()));
    }

    private static boolean shouldRotate(RotationOptions rotationOptions, EncodedImage encodedImage) {
        return !rotationOptions.canDeferUntilRendered() && (JpegTranscoderUtils.getRotationAngle(rotationOptions, encodedImage) != 0 || shouldRotateUsingExifOrientation(rotationOptions, encodedImage));
    }

    private static boolean shouldRotateUsingExifOrientation(RotationOptions rotationOptions, EncodedImage encodedImage) {
        if (!rotationOptions.rotationEnabled() || rotationOptions.canDeferUntilRendered()) {
            encodedImage.setExifOrientation(0);
            return false;
        }
        return JpegTranscoderUtils.INVERTED_EXIF_ORIENTATIONS.contains(Integer.valueOf(encodedImage.getExifOrientation()));
    }
}
