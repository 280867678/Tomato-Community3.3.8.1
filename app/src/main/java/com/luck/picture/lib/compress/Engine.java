package com.luck.picture.lib.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
class Engine {
    private ExifInterface srcExif;
    private int srcHeight;
    private String srcImg;
    private int srcWidth;
    private File tagImg;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Engine(String str, File file) throws IOException {
        if (Checker.isJPG(str)) {
            this.srcExif = new ExifInterface(str);
        }
        this.tagImg = file;
        this.srcImg = str;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(str, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
    }

    private int computeSize() {
        int i = this.srcWidth;
        if (i % 2 == 1) {
            i++;
        }
        this.srcWidth = i;
        int i2 = this.srcHeight;
        if (i2 % 2 == 1) {
            i2++;
        }
        this.srcHeight = i2;
        int max = Math.max(this.srcWidth, this.srcHeight);
        float min = Math.min(this.srcWidth, this.srcHeight) / max;
        if (min > 1.0f || min <= 0.5625d) {
            double d = min;
            if (d <= 0.5625d && d > 0.5d) {
                int i3 = max / 1280;
                if (i3 != 0) {
                    return i3;
                }
                return 1;
            }
            return (int) Math.ceil(max / (1280.0d / d));
        } else if (max < 1664) {
            return 1;
        } else {
            if (max >= 1664 && max < 4990) {
                return 2;
            }
            if (max > 4990 && max < 10240) {
                return 4;
            }
            int i4 = max / 1280;
            if (i4 != 0) {
                return i4;
            }
            return 1;
        }
    }

    private Bitmap rotatingImage(Bitmap bitmap) {
        if (this.srcExif == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        int i = 0;
        int attributeInt = this.srcExif.getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            i = 180;
        } else if (attributeInt == 6) {
            i = 90;
        } else if (attributeInt == 8) {
            i = 270;
        }
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public File compress() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();
        Bitmap decodeFile = BitmapFactory.decodeFile(this.srcImg, options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap rotatingImage = rotatingImage(decodeFile);
        rotatingImage.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        rotatingImage.recycle();
        FileOutputStream fileOutputStream = new FileOutputStream(this.tagImg);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        byteArrayOutputStream.close();
        return this.tagImg;
    }
}
