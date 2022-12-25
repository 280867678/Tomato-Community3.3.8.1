package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.utils.ResourcePool;
import com.gen.p059mh.webapps.utils.Request;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.ImageView */
/* loaded from: classes2.dex */
public class ImageView extends RelativeLayout implements ResourcePool.OnReleaseListener {
    Bitmap bitmap;
    Request currentRequest;
    public android.widget.ImageView imageView;
    public Class imageViewClass;
    OnFileDownloadListener onFileDownloadListener;
    ResourcePool pool;
    int radius;

    /* renamed from: com.gen.mh.webapp_extensions.views.ImageView$OnFileDownloadListener */
    /* loaded from: classes2.dex */
    public interface OnFileDownloadListener {
        void onDownloaded(byte[] bArr);
    }

    public void setOnFileDownloadListener(OnFileDownloadListener onFileDownloadListener) {
        this.onFileDownloadListener = onFileDownloadListener;
    }

    public ImageView(Context context) {
        super(context);
        this.imageViewClass = android.widget.ImageView.class;
        this.radius = 0;
        init(context);
    }

    public ImageView(Context context, int i) {
        super(context);
        this.imageViewClass = android.widget.ImageView.class;
        this.radius = 0;
        this.radius = i;
        init(context);
    }

    protected void finalize() throws Throwable {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        super.finalize();
    }

    void init(Context context) {
        this.pool = ResourcePool.current();
        this.pool.addRelease(this);
        try {
            if (this.radius == 0) {
                this.imageViewClass = android.widget.ImageView.class;
                this.imageView = (android.widget.ImageView) this.imageViewClass.getConstructor(Context.class).newInstance(context);
            } else {
                this.imageViewClass = RoundCornerImageView.class;
                this.imageView = ((RoundCornerImageView) this.imageViewClass.getConstructor(Context.class).newInstance(context)).setRadiusPx(this.radius);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.imageView == null) {
            this.imageView = new android.widget.ImageView(context);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.imageView.setLayoutParams(layoutParams);
        addView(this.imageView);
    }

    public void setUrl(String str) {
        setUrl(str, null);
    }

    public void setUrl(String str, Map<String, String> map) {
        Request request = this.currentRequest;
        if (request != null) {
            request.cancel();
            this.currentRequest = null;
        }
        Request request2 = new Request();
        try {
            request2.setUrl(new URL(str));
            if (map != null) {
                for (String str2 : map.keySet()) {
                    request2.setRequestHeaders(str2, map.get(str2));
                }
            }
            request2.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapp_extensions.views.ImageView.1
                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onProgress(long j, long j2) {
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public boolean onReceiveResponse(Request.Response response) {
                    return true;
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i, String str3) {
                    ImageView.this.currentRequest = null;
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onComplete(int i, final byte[] bArr) {
                    ImageView.this.imageView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.ImageView.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (bArr != null) {
                                Bitmap bitmap = ImageView.this.bitmap;
                                if (bitmap != null) {
                                    bitmap.recycle();
                                    ImageView.this.bitmap = null;
                                }
                                ImageView imageView = ImageView.this;
                                byte[] bArr2 = bArr;
                                imageView.bitmap = BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length);
                                ImageView imageView2 = ImageView.this;
                                imageView2.imageView.setImageBitmap(imageView2.bitmap);
                                OnFileDownloadListener onFileDownloadListener = ImageView.this.onFileDownloadListener;
                                if (onFileDownloadListener == null) {
                                    return;
                                }
                                onFileDownloadListener.onDownloaded(bArr);
                            }
                        }
                    });
                }
            });
            this.currentRequest = request2;
            request2.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setFile(File file) {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        Request request = this.currentRequest;
        if (request != null) {
            request.cancel();
            this.currentRequest = null;
        }
        if (file != null) {
            this.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            this.imageView.setImageBitmap(this.bitmap);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.bitmap = null;
        }
        this.bitmap = bitmap;
        this.imageView.setImageBitmap(bitmap);
    }

    @Override // com.gen.p059mh.webapp_extensions.utils.ResourcePool.OnReleaseListener
    public void onRelease() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        Request request = this.currentRequest;
        if (request != null) {
            request.cancel();
            this.currentRequest = null;
        }
    }
}
