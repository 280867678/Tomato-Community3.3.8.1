package com.king.zxing.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.p002v4.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class CodeUtils {
    public static Bitmap createQRCode(String str, int i) {
        return createQRCode(str, i, null);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap) {
        return createQRCode(str, i, bitmap, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, int i2) {
        return createQRCode(str, i, bitmap, 0.2f, i2);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, @FloatRange(from = 0.0d, m5592to = 1.0d) float f, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET, EncryptUtil.CHARSET);
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hashMap.put(EncodeHintType.MARGIN, 1);
        return createQRCode(str, i, bitmap, f, hashMap, i2);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, @FloatRange(from = 0.0d, m5592to = 1.0d) float f, Map<EncodeHintType, ?> map, int i2) {
        try {
            BitMatrix encode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i, i, map);
            int[] iArr = new int[i * i];
            for (int i3 = 0; i3 < i; i3++) {
                for (int i4 = 0; i4 < i; i4++) {
                    if (encode.get(i4, i3)) {
                        iArr[(i3 * i) + i4] = i2;
                    } else {
                        iArr[(i3 * i) + i4] = -1;
                    }
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, i, 0, 0, i, i);
            return bitmap != null ? addLogo(createBitmap, bitmap, f) : createBitmap;
        } catch (WriterException e) {
            LogUtils.m3902w(e.getMessage());
            return null;
        }
    }

    private static Bitmap addLogo(Bitmap bitmap, Bitmap bitmap2, @FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap2 == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        if (width2 == 0 || height2 == 0) {
            return bitmap;
        }
        float f2 = (width * f) / width2;
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            canvas.scale(f2, f2, width / 2, height / 2);
            canvas.drawBitmap(bitmap2, (width - width2) / 2, (height - height2) / 2, (Paint) null);
            canvas.save();
            canvas.restore();
            return createBitmap;
        } catch (Exception e) {
            LogUtils.m3902w(e.getMessage());
            return null;
        }
    }
}
