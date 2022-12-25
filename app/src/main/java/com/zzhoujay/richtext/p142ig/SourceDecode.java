package com.zzhoujay.richtext.p142ig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.drawable.GifDrawable;
import com.zzhoujay.richtext.ext.ImageKit;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.zzhoujay.richtext.ig.SourceDecode */
/* loaded from: classes4.dex */
abstract class SourceDecode<T> {
    static SourceDecode<byte[]> BASE64_SOURCE_DECODE = new SourceDecode<byte[]>() { // from class: com.zzhoujay.richtext.ig.SourceDecode.1
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public void decodeSize(byte[] bArr, BitmapFactory.Options options) {
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }

        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsBitmap(byte[] bArr, BitmapFactory.Options options) {
            return ImageWrapper.createAsBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsGif(byte[] bArr, BitmapFactory.Options options) {
            return ImageWrapper.createAsGif(new GifDrawable(Movie.decodeByteArray(bArr, 0, bArr.length), options.outHeight, options.outWidth));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public boolean isGif(byte[] bArr, BitmapFactory.Options options) {
            return ImageKit.isGif(bArr);
        }
    };
    static SourceDecode<String> LOCAL_FILE_SOURCE_DECODE = new SourceDecode<String>() { // from class: com.zzhoujay.richtext.ig.SourceDecode.2
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public void decodeSize(String str, BitmapFactory.Options options) {
            BitmapFactory.decodeFile(str, options);
        }

        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsBitmap(String str, BitmapFactory.Options options) {
            return ImageWrapper.createAsBitmap(BitmapFactory.decodeFile(str, options));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsGif(String str, BitmapFactory.Options options) {
            return ImageWrapper.createAsGif(new GifDrawable(Movie.decodeFile(str), options.outHeight, options.outWidth));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public boolean isGif(String str, BitmapFactory.Options options) {
            return ImageKit.isGif(str);
        }
    };
    static SourceDecode<InputStream> INPUT_STREAM_DECODE = new SourceDecode<InputStream>() { // from class: com.zzhoujay.richtext.ig.SourceDecode.3
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public void decodeSize(InputStream inputStream, BitmapFactory.Options options) {
            BufferedInputStream bufferedInputStream;
            if (inputStream instanceof BufferedInputStream) {
                bufferedInputStream = (BufferedInputStream) inputStream;
            } else {
                bufferedInputStream = new BufferedInputStream(inputStream);
            }
            if (options.inJustDecodeBounds) {
                bufferedInputStream.mark(1048576);
            }
            BitmapFactory.decodeStream(bufferedInputStream, null, options);
            if (options.inJustDecodeBounds) {
                try {
                    bufferedInputStream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsBitmap(InputStream inputStream, BitmapFactory.Options options) {
            BufferedInputStream bufferedInputStream;
            if (inputStream instanceof BufferedInputStream) {
                bufferedInputStream = (BufferedInputStream) inputStream;
            } else {
                bufferedInputStream = new BufferedInputStream(inputStream);
            }
            if (options.inJustDecodeBounds) {
                bufferedInputStream.mark(1048576);
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            if (options.inJustDecodeBounds) {
                try {
                    bufferedInputStream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ImageWrapper.createAsBitmap(decodeStream);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public ImageWrapper decodeAsGif(InputStream inputStream, BitmapFactory.Options options) {
            return ImageWrapper.createAsGif(new GifDrawable(Movie.decodeStream(inputStream), options.outHeight, options.outWidth));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.zzhoujay.richtext.p142ig.SourceDecode
        public boolean isGif(InputStream inputStream, BitmapFactory.Options options) {
            return ImageKit.isGif(inputStream);
        }
    };

    abstract ImageWrapper decodeAsBitmap(T t, BitmapFactory.Options options);

    abstract ImageWrapper decodeAsGif(T t, BitmapFactory.Options options);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void decodeSize(T t, BitmapFactory.Options options);

    abstract boolean isGif(T t, BitmapFactory.Options options);

    SourceDecode() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImageWrapper decode(ImageHolder imageHolder, T t, BitmapFactory.Options options) {
        if (imageHolder.isAutoPlay() && (imageHolder.isGif() || isGif(t, options))) {
            imageHolder.setIsGif(true);
            return decodeAsGif(t, options);
        }
        return decodeAsBitmap(t, options);
    }
}
