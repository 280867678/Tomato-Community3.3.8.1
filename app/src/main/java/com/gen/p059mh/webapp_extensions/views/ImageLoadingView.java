package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.utils.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.ImageLoadingView */
/* loaded from: classes2.dex */
public class ImageLoadingView extends View {
    Bitmap bitmap;
    Integer[] defaultResources;
    Thread loadingThread;
    int targetHeight;
    Rect targetRect;
    int targetWidth;
    List<Rect> targets = new ArrayList();
    private boolean startLoading = false;
    boolean isDefault = false;
    Paint paint = new Paint();

    public ImageLoadingView(Context context) {
        super(context);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (this.isDefault) {
            canvas.drawBitmap(this.bitmap, (getWidth() / 2) - (this.bitmap.getWidth() / 2), 0.0f, this.paint);
            return;
        }
        Rect rect = this.targetRect;
        if (rect == null) {
            return;
        }
        canvas.drawBitmap(this.bitmap, rect, new Rect((getWidth() / 2) - (this.targetWidth / 2), 0, (getWidth() / 2) + (this.targetWidth / 2), this.targetHeight), this.paint);
    }

    public void setDefault(boolean z) {
        if (this.startLoading) {
            return;
        }
        this.isDefault = z;
        startDefaultThread();
    }

    public void startLoading(File file, AppResponse.LoadingImgBean loadingImgBean) {
        boolean z = this.startLoading;
        if (z) {
            return;
        }
        if (z && this.bitmap == null) {
            return;
        }
        this.startLoading = true;
        this.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        calculateSale(loadingImgBean);
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = this.bitmap.getHeight();
        this.targetWidth = loadingImgBean.cropWidth;
        this.targetHeight = loadingImgBean.cropHeight;
        this.targets = new ArrayList();
        int i = width / loadingImgBean.cropWidth;
        int i2 = height / loadingImgBean.cropHeight;
        for (int i3 = 0; i3 < loadingImgBean.spriteCount; i3++) {
            int i4 = i3 / i;
            int i5 = i3 % i;
            Rect rect = new Rect();
            int i6 = loadingImgBean.cropWidth;
            rect.left = i5 * i6;
            int i7 = loadingImgBean.cropHeight;
            rect.top = i4 * i7;
            rect.right = (i5 + 1) * i6;
            rect.bottom = (i4 + 1) * i7;
            this.targets.add(rect);
        }
        startThread(loadingImgBean.spriteFps);
    }

    private void calculateSale(AppResponse.LoadingImgBean loadingImgBean) {
        Logger.m4115e(loadingImgBean.toString());
        int i = loadingImgBean.cropWidth;
        float f = i / loadingImgBean.cropHeight;
        if (getHeight() * f <= getWidth()) {
            loadingImgBean.cropHeight = getHeight();
            loadingImgBean.cropWidth = (int) (f * getHeight());
        } else {
            loadingImgBean.cropWidth = getWidth();
            loadingImgBean.cropHeight = (int) (getHeight() / f);
        }
        int width = this.bitmap.getWidth();
        int height = this.bitmap.getHeight();
        float f2 = loadingImgBean.cropWidth / i;
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f2);
        this.bitmap = Bitmap.createBitmap(this.bitmap, 0, 0, width, height, matrix, true);
    }

    public void startThread(final int i) {
        this.loadingThread = new Thread() { // from class: com.gen.mh.webapp_extensions.views.ImageLoadingView.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                int i2 = 0;
                while (true) {
                    ImageLoadingView imageLoadingView = ImageLoadingView.this;
                    if (imageLoadingView.isDefault || !imageLoadingView.startLoading || i2 >= ImageLoadingView.this.targets.size()) {
                        return;
                    }
                    ImageLoadingView imageLoadingView2 = ImageLoadingView.this;
                    imageLoadingView2.targetRect = imageLoadingView2.targets.get(i2);
                    i2++;
                    if (i2 == ImageLoadingView.this.targets.size()) {
                        i2 = 0;
                    }
                    ImageLoadingView.this.postInvalidate();
                    try {
                        Thread.sleep(1000 / i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.loadingThread.start();
    }

    public void startDefaultThread() {
        this.defaultResources = new Integer[]{Integer.valueOf(R$mipmap.loading_1), Integer.valueOf(R$mipmap.loading_2), Integer.valueOf(R$mipmap.loading_3)};
        this.startLoading = true;
        this.loadingThread = new Thread() { // from class: com.gen.mh.webapp_extensions.views.ImageLoadingView.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                int i = 0;
                while (true) {
                    ImageLoadingView imageLoadingView = ImageLoadingView.this;
                    if (!imageLoadingView.isDefault || !imageLoadingView.startLoading || i >= 3) {
                        return;
                    }
                    ImageLoadingView imageLoadingView2 = ImageLoadingView.this;
                    imageLoadingView2.bitmap = BitmapFactory.decodeResource(imageLoadingView2.getResources(), ImageLoadingView.this.defaultResources[i].intValue());
                    i++;
                    if (i == 3) {
                        i = 0;
                    }
                    ImageLoadingView.this.postInvalidate();
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.loadingThread.start();
    }

    public void onStop() {
        this.startLoading = false;
    }

    public void onRelease() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            this.startLoading = false;
            bitmap.recycle();
        }
    }
}
