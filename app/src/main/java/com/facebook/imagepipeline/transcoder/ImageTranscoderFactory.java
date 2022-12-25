package com.facebook.imagepipeline.transcoder;

import android.support.annotation.Nullable;
import com.facebook.imageformat.ImageFormat;

/* loaded from: classes2.dex */
public interface ImageTranscoderFactory {
    @Nullable
    ImageTranscoder createImageTranscoder(ImageFormat imageFormat, boolean z);
}
