package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import com.facebook.soloader.SoLoader;
import java.io.IOException;

/* loaded from: classes2.dex */
public class Fresco {
    private static final Class<?> TAG = Fresco.class;
    private static PipelineDraweeControllerBuilderSupplier sDraweeControllerBuilderSupplier;
    private static volatile boolean sIsInitialized;

    private Fresco() {
    }

    public static void initialize(Context context, ImagePipelineConfig imagePipelineConfig, DraweeConfig draweeConfig) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("Fresco#initialize");
        }
        if (sIsInitialized) {
            FLog.m4142w(TAG, "Fresco has already been initialized! `Fresco.initialize(...)` should only be called 1 single time to avoid memory leaks!");
        } else {
            sIsInitialized = true;
        }
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("Fresco.initialize->SoLoader.init");
            }
            SoLoader.init(context, 0);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            Context applicationContext = context.getApplicationContext();
            if (imagePipelineConfig == null) {
                ImagePipelineFactory.initialize(applicationContext);
            } else {
                ImagePipelineFactory.initialize(imagePipelineConfig);
            }
            initializeDrawee(applicationContext, draweeConfig);
            if (!FrescoSystrace.isTracing()) {
                return;
            }
            FrescoSystrace.endSection();
        } catch (IOException e) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw new RuntimeException("Could not initialize SoLoader", e);
        }
    }

    private static void initializeDrawee(Context context, DraweeConfig draweeConfig) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("Fresco.initializeDrawee");
        }
        sDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context, draweeConfig);
        SimpleDraweeView.initialize(sDraweeControllerBuilderSupplier);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    public static ImagePipelineFactory getImagePipelineFactory() {
        return ImagePipelineFactory.getInstance();
    }

    public static ImagePipeline getImagePipeline() {
        return getImagePipelineFactory().getImagePipeline();
    }

    public static boolean hasBeenInitialized() {
        return sIsInitialized;
    }
}
