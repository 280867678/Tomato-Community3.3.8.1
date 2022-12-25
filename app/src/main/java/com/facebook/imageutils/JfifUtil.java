package com.facebook.imageutils;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import jp.wasabeef.glide.transformations.BuildConfig;

/* loaded from: classes2.dex */
public class JfifUtil {
    private static boolean isSOFn(int i) {
        switch (i) {
            case 192:
            case 193:
            case 194:
            case 195:
            case 197:
            case 198:
            case BuildConfig.VERSION_CODE /* 199 */:
            case 201:
            case 202:
            case 203:
            case 205:
            case 206:
            case 207:
                return true;
            case 196:
            case 200:
            case 204:
            default:
                return false;
        }
    }

    public static int getAutoRotateAngleFromOrientation(int i) {
        return TiffUtil.getAutoRotateAngleFromOrientation(i);
    }

    public static int getOrientation(InputStream inputStream) {
        try {
            int moveToAPP1EXIF = moveToAPP1EXIF(inputStream);
            if (moveToAPP1EXIF != 0) {
                return TiffUtil.readOrientationFromTIFF(inputStream, moveToAPP1EXIF);
            }
            return 0;
        } catch (IOException unused) {
            return 0;
        }
    }

    public static boolean moveToMarker(InputStream inputStream, int i) throws IOException {
        Preconditions.checkNotNull(inputStream);
        while (StreamProcessor.readPackedInt(inputStream, 1, false) == 255) {
            int i2 = 255;
            while (i2 == 255) {
                i2 = StreamProcessor.readPackedInt(inputStream, 1, false);
            }
            if ((i == 192 && isSOFn(i2)) || i2 == i) {
                return true;
            }
            if (i2 != 216 && i2 != 1) {
                if (i2 == 217 || i2 == 218) {
                    break;
                }
                inputStream.skip(StreamProcessor.readPackedInt(inputStream, 2, false) - 2);
            }
        }
        return false;
    }

    private static int moveToAPP1EXIF(InputStream inputStream) throws IOException {
        int readPackedInt;
        if (moveToMarker(inputStream, 225) && (readPackedInt = StreamProcessor.readPackedInt(inputStream, 2, false) - 2) > 6) {
            int readPackedInt2 = StreamProcessor.readPackedInt(inputStream, 4, false);
            int readPackedInt3 = StreamProcessor.readPackedInt(inputStream, 2, false);
            int i = (readPackedInt - 4) - 2;
            if (readPackedInt2 == 1165519206 && readPackedInt3 == 0) {
                return i;
            }
        }
        return 0;
    }
}
