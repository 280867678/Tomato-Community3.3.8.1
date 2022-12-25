package com.tencent.ugc;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p122g.TXMediaRetriever;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class TXVideoInfoReader {
    private static final int RETRY_MAX_COUNT = 3;
    private static TXVideoInfoReader sInstance;
    private int mCount;
    private C3762a mGenerateImageThread;
    private long mImageVideoDuration;
    private volatile WeakReference<OnSampleProgrocess> mListener;
    private String mVideoPath;
    private String TAG = TXVideoInfoReader.class.getSimpleName();
    private AtomicInteger mRetryGeneThreadTimes = new AtomicInteger(0);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /* loaded from: classes3.dex */
    public interface OnSampleProgrocess {
        void sampleProcess(int i, Bitmap bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getDuration(String str) {
        try {
            TXMediaRetriever tXMediaRetriever = new TXMediaRetriever();
            if (TextUtils.isEmpty(str) || !new File(str).exists()) {
                return 0L;
            }
            tXMediaRetriever.m1714a(str);
            long m1716a = tXMediaRetriever.m1716a();
            tXMediaRetriever.m1704k();
            return m1716a;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private TXVideoInfoReader() {
        TXCLog.init();
    }

    public static TXVideoInfoReader getInstance() {
        if (sInstance == null) {
            sInstance = new TXVideoInfoReader();
        }
        return sInstance;
    }

    public TXVideoEditConstants.TXVideoInfo getVideoFileInfo(String str) {
        if (Build.VERSION.SDK_INT < 18) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        TXVideoEditConstants.TXVideoInfo tXVideoInfo = new TXVideoEditConstants.TXVideoInfo();
        TXMediaRetriever tXMediaRetriever = new TXMediaRetriever();
        tXMediaRetriever.m1714a(str);
        tXVideoInfo.duration = tXMediaRetriever.m1716a();
        String str2 = this.TAG;
        TXCLog.m2913i(str2, "getVideoFileInfo: duration = " + tXVideoInfo.duration);
        tXVideoInfo.coverImage = tXMediaRetriever.m1705j();
        tXVideoInfo.fps = tXMediaRetriever.m1708g();
        tXVideoInfo.bitrate = (int) (tXMediaRetriever.m1707h() / 1024);
        tXVideoInfo.audioSampleRate = tXMediaRetriever.m1706i();
        int m1711d = tXMediaRetriever.m1711d();
        String str3 = this.TAG;
        TXCLog.m2913i(str3, "rotation: " + m1711d);
        if (m1711d == 90 || m1711d == 270) {
            tXVideoInfo.width = tXMediaRetriever.m1710e();
            tXVideoInfo.height = tXMediaRetriever.m1709f();
        } else {
            tXVideoInfo.width = tXMediaRetriever.m1709f();
            tXVideoInfo.height = tXMediaRetriever.m1710e();
        }
        tXMediaRetriever.m1704k();
        tXVideoInfo.fileSize = file.length();
        return tXVideoInfo;
    }

    public Bitmap getSampleImage(long j, String str) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.m2911w(this.TAG, "videoPath is null");
            return null;
        } else if (!new File(str).exists()) {
            TXCLog.m2911w(this.TAG, "videoPath is not exist");
            return null;
        } else {
            TXMediaRetriever tXMediaRetriever = new TXMediaRetriever();
            tXMediaRetriever.m1714a(str);
            this.mImageVideoDuration = tXMediaRetriever.m1716a() * 1000;
            long j2 = j * 1000;
            long j3 = this.mImageVideoDuration;
            if (j2 > j3) {
                j2 = j3;
            }
            if (this.mImageVideoDuration <= 0) {
                TXCLog.m2911w(this.TAG, "video duration is 0");
                tXMediaRetriever.m1704k();
                return null;
            }
            Bitmap m1715a = tXMediaRetriever.m1715a(j2);
            if (m1715a == null) {
                TXCLog.m2915d(this.TAG, "getSampleImages failed!!!");
                tXMediaRetriever.m1704k();
                return m1715a;
            }
            String str2 = this.TAG;
            TXCLog.m2915d(str2, "getSampleImages bmp  = " + m1715a + ",time=" + j2 + ",duration=" + this.mImageVideoDuration);
            tXMediaRetriever.m1704k();
            return m1715a;
        }
    }

    public void getSampleImages(int i, String str, OnSampleProgrocess onSampleProgrocess) {
        this.mCount = i;
        this.mListener = new WeakReference<>(onSampleProgrocess);
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            cancelThread();
            this.mGenerateImageThread = new C3762a(str);
            this.mGenerateImageThread.start();
            TXCLog.m2913i(this.TAG, "getSampleImages: thread start");
        }
    }

    public void cancel() {
        cancelThread();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mListener != null) {
            this.mListener.clear();
            this.mListener = null;
        }
    }

    private void cancelThread() {
        C3762a c3762a = this.mGenerateImageThread;
        if (c3762a == null || !c3762a.isAlive() || this.mGenerateImageThread.isInterrupted()) {
            return;
        }
        TXCLog.m2913i(this.TAG, "cancelThread: thread cancel");
        this.mGenerateImageThread.interrupt();
        this.mGenerateImageThread = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.ugc.TXVideoInfoReader$a */
    /* loaded from: classes3.dex */
    public class C3762a extends Thread {

        /* renamed from: b */
        private TXMediaRetriever f5797b;

        /* renamed from: c */
        private String f5798c;

        /* renamed from: d */
        private long f5799d;

        /* renamed from: e */
        private volatile Bitmap f5800e;

        /* renamed from: f */
        private int f5801f;

        public C3762a(String str) {
            this.f5801f = TXVideoInfoReader.this.mListener.hashCode();
            this.f5798c = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.f5797b = new TXMediaRetriever();
            this.f5797b.m1714a(this.f5798c);
            this.f5799d = this.f5797b.m1716a() * 1000;
            long j = this.f5799d / TXVideoInfoReader.this.mCount;
            TXCLog.m2913i(TXVideoInfoReader.this.TAG, String.format("run duration = %s ", Long.valueOf(this.f5799d)));
            TXCLog.m2913i(TXVideoInfoReader.this.TAG, String.format("run count = %s ", Integer.valueOf(TXVideoInfoReader.this.mCount)));
            final int i = 0;
            while (true) {
                if (i >= TXVideoInfoReader.this.mCount || Thread.currentThread().isInterrupted()) {
                    break;
                }
                long j2 = i * j;
                long j3 = this.f5799d;
                if (j2 > j3) {
                    j2 = j3;
                }
                TXCLog.m2913i(TXVideoInfoReader.this.TAG, String.format("current frame time = %s", Long.valueOf(j2)));
                final Bitmap m1715a = this.f5797b.m1715a(j2);
                String str = TXVideoInfoReader.this.TAG;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(i);
                objArr[1] = Boolean.valueOf(m1715a == null);
                TXCLog.m2913i(str, String.format("the %s of bitmap is null ? %s", objArr));
                if (m1715a == null) {
                    TXCLog.m2915d(TXVideoInfoReader.this.TAG, "getSampleImages failed!!!");
                    if (i == 0) {
                        if (TXVideoInfoReader.this.mRetryGeneThreadTimes.get() < 3) {
                            TXCLog.m2915d(TXVideoInfoReader.this.TAG, "retry to get sample images");
                            TXVideoInfoReader.this.mHandler.post(new Runnable() { // from class: com.tencent.ugc.TXVideoInfoReader.a.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    TXVideoInfoReader tXVideoInfoReader = TXVideoInfoReader.this;
                                    tXVideoInfoReader.getSampleImages(tXVideoInfoReader.mCount, C3762a.this.f5798c, (OnSampleProgrocess) TXVideoInfoReader.this.mListener.get());
                                    TXVideoInfoReader.this.mRetryGeneThreadTimes.getAndIncrement();
                                }
                            });
                        }
                    } else if (this.f5800e != null && !this.f5800e.isRecycled()) {
                        TXCLog.m2915d(TXVideoInfoReader.this.TAG, "copy last image");
                        m1715a = this.f5800e.copy(this.f5800e.getConfig(), true);
                    }
                }
                this.f5800e = m1715a;
                if (TXVideoInfoReader.this.mRetryGeneThreadTimes.get() != 0) {
                    TXVideoInfoReader.this.mRetryGeneThreadTimes.getAndSet(0);
                }
                if (TXVideoInfoReader.this.mListener != null && TXVideoInfoReader.this.mListener.get() != null && TXVideoInfoReader.this.mCount > 0 && TXVideoInfoReader.this.mListener.hashCode() == this.f5801f) {
                    TXVideoInfoReader.this.mHandler.post(new Runnable() { // from class: com.tencent.ugc.TXVideoInfoReader.a.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (TXVideoInfoReader.this.mListener == null || TXVideoInfoReader.this.mListener.get() == null || TXVideoInfoReader.this.mListener.hashCode() != C3762a.this.f5801f) {
                                return;
                            }
                            TXCLog.m2913i(TXVideoInfoReader.this.TAG, "return image success");
                            ((OnSampleProgrocess) TXVideoInfoReader.this.mListener.get()).sampleProcess(i, m1715a);
                        }
                    });
                }
                i++;
            }
            this.f5800e = null;
            this.f5797b.m1704k();
        }
    }
}
