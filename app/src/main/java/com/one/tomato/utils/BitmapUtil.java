package com.one.tomato.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public class BitmapUtil {
    public static Bitmap compressImage(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int i = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
            i -= 10;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Bitmap decodeStream = BitmapFactory.decodeStream(byteArrayInputStream, null, null);
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byteArrayInputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return decodeStream;
    }

    public static boolean saveImage(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(str);
        } catch (IOException e) {
            e = e;
            fileOutputStream = null;
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (bitmap == null || bitmap.isRecycled()) {
                return true;
            }
            bitmap.recycle();
            return true;
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (fileOutputStream == null) {
                return false;
            }
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
                return false;
            } catch (IOException e3) {
                e3.printStackTrace();
                return false;
            }
        }
    }
}
