package com.facebook.imageutils;

import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.logging.FLog;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class HeifExifUtil {
    public static int getOrientation(InputStream inputStream) {
        if (Build.VERSION.SDK_INT >= 24) {
            return HeifExifUtilAndroidN.getOrientation(inputStream);
        }
        FLog.m4158d("HeifExifUtil", "Trying to read Heif Exif information before Android N -> ignoring");
        return 0;
    }

    @DoNotStrip
    /* loaded from: classes2.dex */
    private static class HeifExifUtilAndroidN {
        private HeifExifUtilAndroidN() {
        }

        @RequiresApi(api = 24)
        static int getOrientation(InputStream inputStream) {
            try {
                return new ExifInterface(inputStream).getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 1);
            } catch (IOException e) {
                FLog.m4157d("HeifExifUtil", "Failed reading Heif Exif orientation -> ignoring", e);
                return 0;
            }
        }
    }
}
