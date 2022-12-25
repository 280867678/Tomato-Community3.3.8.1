package com.tencent.rtmp.p133a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.rtmp.a.b */
/* loaded from: classes3.dex */
public class TXImageSpriteImpl implements TXIImageSprite {

    /* renamed from: a */
    private final BitmapFactory.Options f5649a = new BitmapFactory.Options();

    /* renamed from: b */
    private HandlerThread f5650b;

    /* renamed from: c */
    private Handler f5651c;

    /* renamed from: d */
    private List<TXVttSegment> f5652d;

    /* renamed from: e */
    private Map<String, BitmapRegionDecoder> f5653e;

    public TXImageSpriteImpl() {
        this.f5652d = new ArrayList();
        this.f5652d = Collections.synchronizedList(this.f5652d);
        this.f5653e = new HashMap();
        this.f5653e = Collections.synchronizedMap(this.f5653e);
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public void setVTTUrlAndImageUrls(String str, List<String> list) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e("TXImageSprite", "setVTTUrlAndImageUrls: vttUrl can't be null!");
            return;
        }
        m382b();
        m387a();
        this.f5651c.post(new RunnableC3706a(this, str));
        if (list == null || list.size() == 0) {
            return;
        }
        for (String str2 : list) {
            this.f5651c.post(new RunnableC3707b(this, str, str2));
        }
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public Bitmap getThumbnail(float f) {
        TXVttSegment m386a;
        if (this.f5652d.size() == 0 || (m386a = m386a(0, this.f5652d.size() - 1, f)) == null) {
            return null;
        }
        BitmapRegionDecoder bitmapRegionDecoder = this.f5653e.get(m386a.f5663d);
        if (bitmapRegionDecoder == null) {
            return null;
        }
        Rect rect = new Rect();
        int i = m386a.f5664e;
        rect.left = i;
        int i2 = m386a.f5665f;
        rect.top = i2;
        rect.right = i + m386a.f5666g;
        rect.bottom = i2 + m386a.f5667h;
        return bitmapRegionDecoder.decodeRegion(rect, this.f5649a);
    }

    @Override // com.tencent.rtmp.p133a.TXIImageSprite
    public void release() {
        m382b();
        HandlerThread handlerThread = this.f5650b;
        if (handlerThread == null || this.f5651c == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            handlerThread.quitSafely();
        } else {
            handlerThread.quit();
        }
        this.f5651c = null;
        this.f5650b = null;
    }

    /* renamed from: a */
    private TXVttSegment m386a(int i, int i2, float f) {
        int i3 = ((i2 - i) / 2) + i;
        if (this.f5652d.get(i3).f5660a > f || this.f5652d.get(i3).f5661b <= f) {
            if (i >= i2) {
                return this.f5652d.get(i);
            }
            if (f >= this.f5652d.get(i3).f5661b) {
                return m386a(i3 + 1, i2, f);
            }
            if (f >= this.f5652d.get(i3).f5660a) {
                return null;
            }
            return m386a(i, i3 - 1, f);
        }
        return this.f5652d.get(i3);
    }

    /* renamed from: a */
    private void m387a() {
        if (this.f5650b == null) {
            this.f5650b = new HandlerThread("SuperVodThumbnailsWorkThread");
            this.f5650b.start();
            this.f5651c = new Handler(this.f5650b.getLooper());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m382b() {
        if (this.f5651c != null) {
            TXCLog.m2913i("TXImageSprite", " remove all tasks!");
            this.f5651c.removeCallbacksAndMessages(null);
            this.f5651c.post(new Runnable() { // from class: com.tencent.rtmp.a.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TXImageSpriteImpl.this.f5652d != null) {
                        TXImageSpriteImpl.this.f5652d.clear();
                    }
                    if (TXImageSpriteImpl.this.f5653e != null) {
                        for (BitmapRegionDecoder bitmapRegionDecoder : TXImageSpriteImpl.this.f5653e.values()) {
                            if (bitmapRegionDecoder != null) {
                                bitmapRegionDecoder.recycle();
                            }
                        }
                        TXImageSpriteImpl.this.f5653e.clear();
                    }
                }
            });
        }
    }

    /* compiled from: TXImageSpriteImpl.java */
    /* renamed from: com.tencent.rtmp.a.b$a */
    /* loaded from: classes3.dex */
    private static class RunnableC3706a implements Runnable {

        /* renamed from: a */
        private WeakReference<TXImageSpriteImpl> f5655a;

        /* renamed from: b */
        private String f5656b;

        public RunnableC3706a(TXImageSpriteImpl tXImageSpriteImpl, String str) {
            this.f5655a = new WeakReference<>(tXImageSpriteImpl);
            this.f5656b = str;
        }

        /* renamed from: a */
        private float m379a(String str) {
            String str2;
            String str3;
            String[] split = str.split(":");
            String str4 = null;
            if (split.length == 3) {
                str4 = split[0];
                str3 = split[1];
                str2 = split[2];
            } else if (split.length == 2) {
                str3 = split[0];
                str2 = split[1];
            } else if (split.length == 1) {
                str2 = split[0];
                str3 = null;
            } else {
                str2 = null;
                str3 = null;
            }
            float f = 0.0f;
            if (str4 != null) {
                f = 0.0f + (Float.valueOf(0.0f).floatValue() * 3600.0f);
            }
            if (str3 != null) {
                f += Float.valueOf(str3).floatValue() * 60.0f;
            }
            return str2 != null ? f + Float.valueOf(str2).floatValue() : f;
        }

        @Override // java.lang.Runnable
        public void run() {
            BufferedReader bufferedReader;
            InputStream m383a;
            String readLine;
            String readLine2;
            int i;
            TXImageSpriteImpl tXImageSpriteImpl = this.f5655a.get();
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    try {
                        m383a = tXImageSpriteImpl.m383a(this.f5656b);
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                    }
                } catch (IOException e) {
                    e = e;
                }
                if (m383a == null) {
                    return;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(m383a));
                try {
                    readLine = bufferedReader.readLine();
                } catch (IOException e2) {
                    e = e2;
                    bufferedReader2 = bufferedReader;
                    e.printStackTrace();
                    if (bufferedReader2 == null) {
                        return;
                    }
                    bufferedReader2.close();
                    bufferedReader2 = bufferedReader2;
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
                if (readLine != null && readLine.length() != 0 && readLine.contains("WEBVTT")) {
                    do {
                        readLine2 = bufferedReader.readLine();
                        if (readLine2 != null && readLine2.contains("-->")) {
                            String[] split = readLine2.split(" --> ");
                            if (split.length != 2) {
                                continue;
                            } else {
                                String readLine3 = bufferedReader.readLine();
                                TXVttSegment tXVttSegment = new TXVttSegment();
                                tXVttSegment.f5660a = m379a(split[0]);
                                tXVttSegment.f5661b = m379a(split[1]);
                                tXVttSegment.f5662c = readLine3;
                                int indexOf = readLine3.indexOf("#");
                                if (indexOf != -1) {
                                    tXVttSegment.f5663d = readLine3.substring(0, indexOf);
                                }
                                int indexOf2 = readLine3.indexOf(SimpleComparison.EQUAL_TO_OPERATION);
                                if (indexOf2 != -1 && (i = indexOf2 + 1) < readLine3.length()) {
                                    String[] split2 = readLine3.substring(i, readLine3.length()).split(",");
                                    if (split2.length == 4) {
                                        tXVttSegment.f5664e = Integer.valueOf(split2[0]).intValue();
                                        tXVttSegment.f5665f = Integer.valueOf(split2[1]).intValue();
                                        tXVttSegment.f5666g = Integer.valueOf(split2[2]).intValue();
                                        tXVttSegment.f5667h = Integer.valueOf(split2[3]).intValue();
                                    }
                                }
                                if (tXImageSpriteImpl != null && tXImageSpriteImpl.f5652d != null) {
                                    tXImageSpriteImpl.f5652d.add(tXVttSegment);
                                    continue;
                                }
                            }
                        }
                    } while (readLine2 != null);
                    bufferedReader.close();
                    bufferedReader2 = readLine2;
                    return;
                }
                TXCLog.m2914e("TXImageSprite", "DownloadAndParseVTTFileTask : getVTT File Error!");
                if (tXImageSpriteImpl != null) {
                    tXImageSpriteImpl.m382b();
                }
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public InputStream m383a(String str) throws IOException {
        URLConnection openConnection = new URL(str).openConnection();
        openConnection.connect();
        openConnection.getInputStream();
        openConnection.setConnectTimeout(15000);
        openConnection.setReadTimeout(15000);
        return openConnection.getInputStream();
    }

    /* compiled from: TXImageSpriteImpl.java */
    /* renamed from: com.tencent.rtmp.a.b$b */
    /* loaded from: classes3.dex */
    private static class RunnableC3707b implements Runnable {

        /* renamed from: a */
        private WeakReference<TXImageSpriteImpl> f5657a;

        /* renamed from: b */
        private String f5658b;

        /* renamed from: c */
        private String f5659c;

        public RunnableC3707b(TXImageSpriteImpl tXImageSpriteImpl, String str, String str2) {
            this.f5657a = new WeakReference<>(tXImageSpriteImpl);
            this.f5658b = str;
            this.f5659c = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            TXImageSpriteImpl tXImageSpriteImpl = this.f5657a.get();
            if (this.f5657a == null || tXImageSpriteImpl == null) {
                return;
            }
            InputStream inputStream = null;
            try {
                try {
                    try {
                        inputStream = tXImageSpriteImpl.m383a(this.f5659c);
                        int lastIndexOf = this.f5659c.lastIndexOf("/");
                        if (lastIndexOf != -1 && (i = lastIndexOf + 1) < this.f5659c.length()) {
                            String substring = this.f5659c.substring(i, this.f5659c.length());
                            if (tXImageSpriteImpl.f5653e != null) {
                                tXImageSpriteImpl.f5653e.put(substring, BitmapRegionDecoder.newInstance(inputStream, true));
                            }
                        }
                        if (inputStream == null) {
                            return;
                        }
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    if (inputStream == null) {
                        return;
                    }
                    inputStream.close();
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }
}
