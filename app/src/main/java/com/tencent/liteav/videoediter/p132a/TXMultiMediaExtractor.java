package com.tencent.liteav.videoediter.p132a;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.tencent.liteav.videoediter.a.d */
/* loaded from: classes3.dex */
public class TXMultiMediaExtractor extends TXMediaExtractor {

    /* renamed from: a */
    private static final String f5484a = "com.tencent.liteav.videoediter.a.d";

    /* renamed from: d */
    private long f5487d = 0;

    /* renamed from: e */
    private long f5488e = 0;

    /* renamed from: b */
    private ArrayList<String> f5485b = new ArrayList<>();

    /* renamed from: c */
    private int f5486c = -1;

    /* renamed from: a */
    public synchronized void m558a(List<String> list) {
        if (list != null) {
            if (list.size() > 0) {
                this.f5485b.addAll(list);
            }
        }
    }

    @Override // com.tencent.liteav.videoediter.p132a.TXMediaExtractor
    /* renamed from: a */
    public synchronized void mo560a(long j) {
        if (j <= 0) {
            m554g();
            return;
        }
        m554g();
        if (this.f5485b.size() > 0) {
            TXMediaExtractor tXMediaExtractor = new TXMediaExtractor();
            int i = 0;
            while (i < this.f5485b.size()) {
                try {
                    tXMediaExtractor.m570a(this.f5485b.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                    String str = f5484a;
                    TXCLog.m2914e(str, "setDataSource IOException: " + e);
                }
                if (tXMediaExtractor.mo557c() + 0 > j) {
                    break;
                }
                i++;
            }
            tXMediaExtractor.mo556e();
            if (i < this.f5485b.size()) {
                this.f5486c = i;
                this.f5487d = 0L;
                try {
                    super.m570a(this.f5485b.get(this.f5486c));
                } catch (IOException e2) {
                    e2.printStackTrace();
                    String str2 = f5484a;
                    TXCLog.m2914e(str2, "setDataSource IOException: " + e2);
                }
                super.mo560a(j - this.f5487d);
                this.f5488e = super.m568d();
            }
        }
    }

    @TargetApi(16)
    /* renamed from: f */
    public int m555f() {
        if (this.f5485b.size() > 0) {
            TXMediaExtractor tXMediaExtractor = new TXMediaExtractor();
            Iterator<String> it2 = this.f5485b.iterator();
            MediaFormat mediaFormat = null;
            MediaFormat mediaFormat2 = null;
            while (it2.hasNext()) {
                try {
                    tXMediaExtractor.m570a(it2.next());
                    MediaFormat m571a = tXMediaExtractor.m571a();
                    MediaFormat m569b = tXMediaExtractor.m569b();
                    if (mediaFormat == null && mediaFormat2 == null) {
                        mediaFormat = m571a;
                        mediaFormat2 = m569b;
                    } else if (mediaFormat != null && m571a == null) {
                        return -2;
                    } else {
                        if (mediaFormat == null && m571a != null) {
                            return -2;
                        }
                        if (mediaFormat2 != null && m569b == null) {
                            return -2;
                        }
                        if (mediaFormat2 == null && m569b != null) {
                            return -2;
                        }
                        if (mediaFormat != null && m571a != null) {
                            try {
                                if (Math.abs(mediaFormat.getInteger("frame-rate") - m571a.getInteger("frame-rate")) > 3) {
                                    return -4;
                                }
                                if (mediaFormat.getInteger("width") != m571a.getInteger("width")) {
                                    return -5;
                                }
                                if (mediaFormat.getInteger("height") != m571a.getInteger("height")) {
                                    return -6;
                                }
                            } catch (NullPointerException unused) {
                                return -3;
                            }
                        } else if (mediaFormat2 != null && m569b != null) {
                            if (mediaFormat2.getInteger("sample-rate") != m569b.getInteger("sample-rate")) {
                                return -7;
                            }
                            if (mediaFormat2.getInteger("channel-count") != m569b.getInteger("channel-count")) {
                                return -8;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    TXCLog.m2914e(f5484a, "setDataSource IOException: " + e);
                }
            }
            tXMediaExtractor.mo556e();
            return 0;
        }
        return -1;
    }

    @Override // com.tencent.liteav.videoediter.p132a.TXMediaExtractor
    /* renamed from: c */
    public synchronized long mo557c() {
        long j;
        j = 0;
        if (this.f5485b.size() > 0) {
            TXMediaExtractor tXMediaExtractor = new TXMediaExtractor();
            for (int i = 0; i < this.f5485b.size(); i++) {
                try {
                    tXMediaExtractor.m570a(this.f5485b.get(i));
                    j += tXMediaExtractor.mo557c();
                } catch (IOException e) {
                    e.printStackTrace();
                    String str = f5484a;
                    TXCLog.m2914e(str, "setDataSource IOException: " + e);
                }
            }
            tXMediaExtractor.mo556e();
        }
        return j;
    }

    @Override // com.tencent.liteav.videoediter.p132a.TXMediaExtractor
    /* renamed from: a */
    public synchronized int mo559a(Frame frame) {
        int mo559a;
        mo559a = super.mo559a(frame);
        while (mo559a < 0 && this.f5486c < this.f5485b.size() - 1) {
            this.f5487d = this.f5488e + 1000;
            this.f5486c++;
            try {
                m570a(this.f5485b.get(this.f5486c));
                mo559a = super.mo559a(frame);
            } catch (IOException e) {
                TXCLog.m2914e(f5484a, "setDataSource IOException: " + e);
                e.printStackTrace();
            }
        }
        if (mo559a >= 0) {
            long m2329e = frame.m2329e() + this.f5487d;
            frame.m2343a(m2329e);
            if (this.f5488e < m2329e) {
                this.f5488e = m2329e;
            }
        } else {
            TXCLog.m2915d(f5484a, "readSampleData length = " + mo559a);
        }
        return mo559a;
    }

    /* renamed from: g */
    private synchronized void m554g() {
        super.mo556e();
        this.f5486c = -1;
        this.f5487d = 0L;
        this.f5488e = 0L;
    }

    @Override // com.tencent.liteav.videoediter.p132a.TXMediaExtractor
    /* renamed from: e */
    public synchronized void mo556e() {
        super.mo556e();
        this.f5485b.clear();
        this.f5486c = -1;
        this.f5487d = 0L;
        this.f5488e = 0L;
    }
}
