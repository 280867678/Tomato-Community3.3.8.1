package com.taobao.gcanvas.adapters.img.impl.fresco;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.taobao.gcanvas.adapters.img.IGImageLoader;

/* loaded from: classes3.dex */
public class GCanvasFrescoImageLoader implements IGImageLoader {
    private static final String TAG = "GCanvasFrescoImageLoader";
    private DraweeConfig draweeConfig;
    private ImagePipelineConfig imagePipelineConfig;
    private Handler mHandler;

    public GCanvasFrescoImageLoader() {
        this(null, null);
    }

    public GCanvasFrescoImageLoader(ImagePipelineConfig imagePipelineConfig, DraweeConfig draweeConfig) {
        this.imagePipelineConfig = imagePipelineConfig;
        this.draweeConfig = draweeConfig;
    }

    @Override // com.taobao.gcanvas.adapters.img.IGImageLoader
    public void load(Context context, String str, IGImageLoader.ImageCallback imageCallback) {
        if (TextUtils.isEmpty(str)) {
            Log.i(TAG, "url is null or empty");
            return;
        }
        if (!Fresco.hasBeenInitialized()) {
            Log.i(TAG, "fresco not init, do initialization");
            Fresco.initialize(context, this.imagePipelineConfig, this.draweeConfig);
        }
        ImageLoadRunnable imageLoadRunnable = new ImageLoadRunnable(context, str, imageCallback);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper());
            }
            this.mHandler.post(imageLoadRunnable);
            return;
        }
        imageLoadRunnable.run();
    }

    /* loaded from: classes3.dex */
    private static class ImageLoadRunnable implements Runnable {
        private final IGImageLoader.ImageCallback callback;
        private String url;

        public ImageLoadRunnable(Context context, String str, IGImageLoader.ImageCallback imageCallback) {
            this.url = str;
            this.callback = imageCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            DataSource<CloseableReference<CloseableImage>> fetchDecodedImage;
            Uri parse = Uri.parse(this.url);
            ImageRequestBuilder newBuilderWithSource = ImageRequestBuilder.newBuilderWithSource(parse);
            newBuilderWithSource.setAutoRotateEnabled(false);
            newBuilderWithSource.setRotationOptions(RotationOptions.disableRotation());
            ImageRequest build = newBuilderWithSource.build();
            if (Fresco.getImagePipeline().isInBitmapMemoryCache(parse)) {
                fetchDecodedImage = Fresco.getImagePipeline().fetchImageFromBitmapCache(build, this);
            } else {
                fetchDecodedImage = Fresco.getImagePipeline().fetchDecodedImage(build, this);
            }
            fetchDecodedImage.subscribe(new DataSubscriber<CloseableReference<CloseableImage>>() { // from class: com.taobao.gcanvas.adapters.img.impl.fresco.GCanvasFrescoImageLoader.ImageLoadRunnable.1
                @Override // com.facebook.datasource.DataSubscriber
                public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
                }

                @Override // com.facebook.datasource.DataSubscriber
                public void onNewResult(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    CloseableReference<CloseableImage> mo5940getResult;
                    if (dataSource.isFinished() && (mo5940getResult = dataSource.mo5940getResult()) != null) {
                        try {
                            CloseableImage closeableImage = mo5940getResult.get();
                            if (closeableImage instanceof CloseableBitmap) {
                                ImageLoadRunnable.this.callback.onSuccess(((CloseableBitmap) closeableImage).getUnderlyingBitmap());
                            } else {
                                byte[] bArr = new byte[closeableImage.getSizeInBytes()];
                                ImageLoadRunnable.this.callback.onSuccess(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
                            }
                        } finally {
                            try {
                            } finally {
                            }
                        }
                    }
                }

                @Override // com.facebook.datasource.DataSubscriber
                public void onFailure(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    ImageLoadRunnable.this.callback.onFail(dataSource.getFailureCause() != null ? dataSource.getFailureCause().getMessage() : null);
                }

                @Override // com.facebook.datasource.DataSubscriber
                public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    ImageLoadRunnable.this.callback.onCancel();
                }
            }, CallerThreadExecutor.getInstance());
        }
    }
}
