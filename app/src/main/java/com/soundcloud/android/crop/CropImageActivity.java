package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.soundcloud.android.crop.ImageViewTouchBase;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
public class CropImageActivity extends MonitoredActivity {
    private int aspectX;
    private int aspectY;
    private HighlightView cropView;
    private int exifRotation;
    private final Handler handler = new Handler();
    private CropImageView imageView;
    private boolean isSaving;
    private int maxX;
    private int maxY;
    private RotateBitmap rotateBitmap;
    private int sampleSize;
    private Uri saveUri;
    private Uri sourceUri;

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // com.soundcloud.android.crop.MonitoredActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setupWindowFlags();
        setupViews();
        loadInput();
        if (this.rotateBitmap == null) {
            finish();
        } else {
            startCrop();
        }
    }

    @TargetApi(19)
    private void setupWindowFlags() {
        requestWindowFeature(1);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().clearFlags(67108864);
        }
    }

    private void setupViews() {
        setContentView(R$layout.crop__activity_crop);
        this.imageView = (CropImageView) findViewById(R$id.crop_image);
        CropImageView cropImageView = this.imageView;
        cropImageView.context = this;
        cropImageView.setRecycler(new ImageViewTouchBase.Recycler(this) { // from class: com.soundcloud.android.crop.CropImageActivity.1
            @Override // com.soundcloud.android.crop.ImageViewTouchBase.Recycler
            public void recycle(Bitmap bitmap) {
                bitmap.recycle();
                System.gc();
            }
        });
        findViewById(R$id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.soundcloud.android.crop.CropImageActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropImageActivity.this.setResult(0);
                CropImageActivity.this.finish();
            }
        });
        findViewById(R$id.btn_done).setOnClickListener(new View.OnClickListener() { // from class: com.soundcloud.android.crop.CropImageActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropImageActivity.this.onSaveClicked();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.Context, android.app.Activity, com.soundcloud.android.crop.CropImageActivity] */
    private void loadInput() {
        Throwable th;
        InputStream inputStream;
        OutOfMemoryError e;
        IOException e2;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            this.aspectX = extras.getInt("aspect_x");
            this.aspectY = extras.getInt("aspect_y");
            this.maxX = extras.getInt("max_x");
            this.maxY = extras.getInt("max_y");
            this.saveUri = (Uri) extras.getParcelable("output");
        }
        this.sourceUri = intent.getData();
        if (this.sourceUri != null) {
            ContentResolver contentResolver = getContentResolver();
            ?? r1 = this.sourceUri;
            this.exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(this, contentResolver, r1));
            try {
                try {
                    this.sampleSize = calculateBitmapSampleSize(this.sourceUri);
                    inputStream = getContentResolver().openInputStream(this.sourceUri);
                } catch (Throwable th2) {
                    th = th2;
                    CropUtil.closeSilently(r1);
                    throw th;
                }
            } catch (IOException e3) {
                inputStream = null;
                e2 = e3;
            } catch (OutOfMemoryError e4) {
                inputStream = null;
                e = e4;
            } catch (Throwable th3) {
                r1 = 0;
                th = th3;
                CropUtil.closeSilently(r1);
                throw th;
            }
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = this.sampleSize;
                this.rotateBitmap = new RotateBitmap(BitmapFactory.decodeStream(inputStream, null, options), this.exifRotation);
                r1 = inputStream;
            } catch (IOException e5) {
                e2 = e5;
                Log.m3670e("Error reading image: " + e2.getMessage(), e2);
                setResultException(e2);
                r1 = inputStream;
                CropUtil.closeSilently(r1);
            } catch (OutOfMemoryError e6) {
                e = e6;
                Log.m3670e("OOM reading image: " + e.getMessage(), e);
                setResultException(e);
                r1 = inputStream;
                CropUtil.closeSilently(r1);
            }
            CropUtil.closeSilently(r1);
        }
    }

    private int calculateBitmapSampleSize(Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        InputStream inputStream = null;
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            try {
                BitmapFactory.decodeStream(openInputStream, null, options);
                CropUtil.closeSilently(openInputStream);
                int maxImageSize = getMaxImageSize();
                while (true) {
                    if (options.outHeight / i <= maxImageSize && options.outWidth / i <= maxImageSize) {
                        return i;
                    }
                    i <<= 1;
                }
            } catch (Throwable th) {
                th = th;
                inputStream = openInputStream;
                CropUtil.closeSilently(inputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private int getMaxImageSize() {
        int maxTextureSize = getMaxTextureSize();
        if (maxTextureSize == 0) {
            return 2048;
        }
        return Math.min(maxTextureSize, 4096);
    }

    private int getMaxTextureSize() {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(3379, iArr, 0);
        return iArr[0];
    }

    private void startCrop() {
        if (isFinishing()) {
            return;
        }
        this.imageView.setImageRotateBitmapResetBase(this.rotateBitmap, true);
        CropUtil.startBackgroundJob(this, null, getResources().getString(R$string.crop__wait), new Runnable() { // from class: com.soundcloud.android.crop.CropImageActivity.4
            @Override // java.lang.Runnable
            public void run() {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                CropImageActivity.this.handler.post(new Runnable() { // from class: com.soundcloud.android.crop.CropImageActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (CropImageActivity.this.imageView.getScale() == 1.0f) {
                            CropImageActivity.this.imageView.center();
                        }
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await();
                    new Cropper().crop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, this.handler);
    }

    /* loaded from: classes3.dex */
    private class Cropper {
        private Cropper() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void makeDefault() {
            int i;
            if (CropImageActivity.this.rotateBitmap == null) {
                return;
            }
            HighlightView highlightView = new HighlightView(CropImageActivity.this.imageView);
            int width = CropImageActivity.this.rotateBitmap.getWidth();
            int height = CropImageActivity.this.rotateBitmap.getHeight();
            boolean z = false;
            Rect rect = new Rect(0, 0, width, height);
            int min = (Math.min(width, height) * 4) / 5;
            if (CropImageActivity.this.aspectX == 0 || CropImageActivity.this.aspectY == 0) {
                i = min;
            } else if (CropImageActivity.this.aspectX > CropImageActivity.this.aspectY) {
                i = (CropImageActivity.this.aspectY * min) / CropImageActivity.this.aspectX;
            } else {
                i = min;
                min = (CropImageActivity.this.aspectX * min) / CropImageActivity.this.aspectY;
            }
            int i2 = (width - min) / 2;
            int i3 = (height - i) / 2;
            RectF rectF = new RectF(i2, i3, i2 + min, i3 + i);
            Matrix unrotatedMatrix = CropImageActivity.this.imageView.getUnrotatedMatrix();
            if (CropImageActivity.this.aspectX != 0 && CropImageActivity.this.aspectY != 0) {
                z = true;
            }
            highlightView.setup(unrotatedMatrix, rect, rectF, z);
            CropImageActivity.this.imageView.add(highlightView);
        }

        public void crop() {
            CropImageActivity.this.handler.post(new Runnable() { // from class: com.soundcloud.android.crop.CropImageActivity.Cropper.1
                @Override // java.lang.Runnable
                public void run() {
                    Cropper.this.makeDefault();
                    CropImageActivity.this.imageView.invalidate();
                    if (CropImageActivity.this.imageView.highlightViews.size() == 1) {
                        CropImageActivity cropImageActivity = CropImageActivity.this;
                        cropImageActivity.cropView = cropImageActivity.imageView.highlightViews.get(0);
                        CropImageActivity.this.cropView.setFocus(true);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSaveClicked() {
        int i;
        HighlightView highlightView = this.cropView;
        if (highlightView == null || this.isSaving) {
            return;
        }
        this.isSaving = true;
        Rect scaledCropRect = highlightView.getScaledCropRect(this.sampleSize);
        int width = scaledCropRect.width();
        int height = scaledCropRect.height();
        int i2 = this.maxX;
        if (i2 > 0 && (i = this.maxY) > 0 && (width > i2 || height > i)) {
            float f = width / height;
            int i3 = this.maxX;
            int i4 = this.maxY;
            if (i3 / i4 > f) {
                width = (int) ((i4 * f) + 0.5f);
                height = i4;
            } else {
                height = (int) ((i3 / f) + 0.5f);
                width = i3;
            }
        }
        try {
            Bitmap decodeRegionCrop = decodeRegionCrop(scaledCropRect, width, height);
            if (decodeRegionCrop != null) {
                this.imageView.setImageRotateBitmapResetBase(new RotateBitmap(decodeRegionCrop, this.exifRotation), true);
                this.imageView.center();
                this.imageView.highlightViews.clear();
            }
            saveImage(decodeRegionCrop);
        } catch (IllegalArgumentException e) {
            setResultException(e);
            finish();
        }
    }

    private void saveImage(final Bitmap bitmap) {
        if (bitmap != null) {
            CropUtil.startBackgroundJob(this, null, getResources().getString(R$string.crop__saving), new Runnable() { // from class: com.soundcloud.android.crop.CropImageActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    CropImageActivity.this.saveOutput(bitmap);
                }
            }, this.handler);
        } else {
            finish();
        }
    }

    private Bitmap decodeRegionCrop(Rect rect, int i, int i2) {
        Bitmap bitmap;
        InputStream inputStream;
        Rect rect2;
        clearImageView();
        InputStream inputStream2 = null;
        try {
            try {
                inputStream = getContentResolver().openInputStream(this.sourceUri);
            } catch (Throwable th) {
                th = th;
                inputStream = null;
            }
        } catch (IOException e) {
            e = e;
            bitmap = null;
        } catch (OutOfMemoryError e2) {
            e = e2;
            bitmap = null;
        }
        try {
            BitmapRegionDecoder newInstance = BitmapRegionDecoder.newInstance(inputStream, false);
            int width = newInstance.getWidth();
            int height = newInstance.getHeight();
            if (this.exifRotation != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(-this.exifRotation);
                RectF rectF = new RectF();
                matrix.mapRect(rectF, new RectF(rect));
                float f = 0.0f;
                float f2 = rectF.left < 0.0f ? width : 0.0f;
                if (rectF.top < 0.0f) {
                    f = height;
                }
                rectF.offset(f2, f);
                rect2 = new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            } else {
                rect2 = rect;
            }
            try {
                Bitmap decodeRegion = newInstance.decodeRegion(rect2, new BitmapFactory.Options());
                if (rect2.width() > i || rect2.height() > i2) {
                    Matrix matrix2 = new Matrix();
                    matrix2.postScale(i / rect2.width(), i2 / rect2.height());
                    decodeRegion = Bitmap.createBitmap(decodeRegion, 0, 0, decodeRegion.getWidth(), decodeRegion.getHeight(), matrix2, true);
                }
                CropUtil.closeSilently(inputStream);
                return decodeRegion;
            } catch (IllegalArgumentException e3) {
                throw new IllegalArgumentException("Rectangle " + rect2 + " is outside of the image (" + width + "," + height + "," + this.exifRotation + ")", e3);
            }
        } catch (IOException e4) {
            e = e4;
            bitmap = null;
            inputStream2 = inputStream;
            Log.m3670e("Error cropping image: " + e.getMessage(), e);
            setResultException(e);
            CropUtil.closeSilently(inputStream2);
            return bitmap;
        } catch (OutOfMemoryError e5) {
            e = e5;
            bitmap = null;
            inputStream2 = inputStream;
            Log.m3670e("OOM cropping image: " + e.getMessage(), e);
            setResultException(e);
            CropUtil.closeSilently(inputStream2);
            return bitmap;
        } catch (Throwable th2) {
            th = th2;
            CropUtil.closeSilently(inputStream);
            throw th;
        }
    }

    private void clearImageView() {
        this.imageView.clear();
        RotateBitmap rotateBitmap = this.rotateBitmap;
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
        System.gc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveOutput(final Bitmap bitmap) {
        if (this.saveUri != null) {
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = getContentResolver().openOutputStream(this.saveUri);
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    }
                } catch (IOException e) {
                    setResultException(e);
                    Log.m3670e("Cannot open file: " + this.saveUri, e);
                }
                CropUtil.copyExifRotation(CropUtil.getFromMediaUri(this, getContentResolver(), this.sourceUri), CropUtil.getFromMediaUri(this, getContentResolver(), this.saveUri));
                setResultUri(this.saveUri);
            } finally {
                CropUtil.closeSilently(outputStream);
            }
        }
        this.handler.post(new Runnable() { // from class: com.soundcloud.android.crop.CropImageActivity.6
            @Override // java.lang.Runnable
            public void run() {
                CropImageActivity.this.imageView.clear();
                bitmap.recycle();
            }
        });
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.soundcloud.android.crop.MonitoredActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        RotateBitmap rotateBitmap = this.rotateBitmap;
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
    }

    public boolean isSaving() {
        return this.isSaving;
    }

    private void setResultUri(Uri uri) {
        setResult(-1, new Intent().putExtra("output", uri));
    }

    private void setResultException(Throwable th) {
        setResult(404, new Intent().putExtra("error", th));
    }
}
