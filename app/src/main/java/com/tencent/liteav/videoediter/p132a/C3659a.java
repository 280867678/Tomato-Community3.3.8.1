package com.tencent.liteav.videoediter.p132a;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: TXCMP4Muxer.java */
@TargetApi(18)
/* renamed from: com.tencent.liteav.videoediter.a.a */
/* loaded from: classes3.dex */
public class C3659a {

    /* renamed from: b */
    private MediaMuxer f5445b;

    /* renamed from: a */
    private final boolean f5444a = false;

    /* renamed from: c */
    private String f5446c = null;

    /* renamed from: d */
    private MediaFormat f5447d = null;

    /* renamed from: e */
    private MediaFormat f5448e = null;

    /* renamed from: f */
    private int f5449f = 1;

    /* renamed from: g */
    private int f5450g = 0;

    /* renamed from: h */
    private int f5451h = 0;

    /* renamed from: i */
    private int f5452i = 0;

    /* renamed from: j */
    private boolean f5453j = false;

    /* renamed from: k */
    private boolean f5454k = false;

    /* renamed from: l */
    private ConcurrentLinkedQueue<C3660a> f5455l = new ConcurrentLinkedQueue<>();

    /* renamed from: m */
    private ConcurrentLinkedQueue<C3660a> f5456m = new ConcurrentLinkedQueue<>();

    /* renamed from: n */
    private long f5457n = -1;

    /* renamed from: o */
    private long f5458o = -1;

    /* renamed from: p */
    private long f5459p = -1;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCMP4Muxer.java */
    /* renamed from: com.tencent.liteav.videoediter.a.a$a */
    /* loaded from: classes3.dex */
    public static class C3660a {

        /* renamed from: a */
        ByteBuffer f5460a;

        /* renamed from: b */
        MediaCodec.BufferInfo f5461b;

        public C3660a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.f5460a = byteBuffer;
            this.f5461b = bufferInfo;
        }

        /* renamed from: a */
        public ByteBuffer m573a() {
            return this.f5460a;
        }

        /* renamed from: b */
        public MediaCodec.BufferInfo m572b() {
            return this.f5461b;
        }
    }

    /* renamed from: a */
    public synchronized void m586a(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4HWMuxer", "addVideoTrack:" + mediaFormat);
        this.f5447d = mediaFormat;
        this.f5450g = this.f5450g | 1;
        this.f5455l.clear();
    }

    /* renamed from: b */
    public synchronized void m581b(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4HWMuxer", "addAudioTrack:" + mediaFormat);
        this.f5448e = mediaFormat;
        this.f5450g = this.f5450g | 2;
        this.f5456m.clear();
    }

    /* renamed from: a */
    public synchronized boolean m587a() {
        boolean z = true;
        if ((this.f5449f & 1) == 0) {
            return true;
        }
        if ((this.f5450g & 1) == 0) {
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    public synchronized boolean m582b() {
        boolean z = true;
        if ((this.f5449f & 2) == 0) {
            return true;
        }
        if ((this.f5450g & 2) == 0) {
            z = false;
        }
        return z;
    }

    /* renamed from: c */
    public synchronized int m579c() {
        if (this.f5446c != null && !this.f5446c.isEmpty()) {
            if (!m587a()) {
                TXCLog.m2914e("TXCMP4HWMuxer", "video track not set yet!");
                return -2;
            } else if (!m582b()) {
                TXCLog.m2914e("TXCMP4HWMuxer", "audio track not set yet!");
                return -3;
            } else if (this.f5445b != null) {
                TXCLog.m2911w("TXCMP4HWMuxer", "start has been called. stop must be called before start");
                return 0;
            } else {
                TXCLog.m2915d("TXCMP4HWMuxer", C2516Ad.TYPE_START);
                try {
                    this.f5445b = new MediaMuxer(this.f5446c, 0);
                    if (this.f5447d != null) {
                        try {
                            try {
                                this.f5452i = this.f5445b.addTrack(this.f5447d);
                            } catch (IllegalArgumentException e) {
                                TXCLog.m2914e("TXCMP4HWMuxer", "addVideoTrack IllegalArgumentException: " + e);
                                return -5;
                            }
                        } catch (IllegalStateException e2) {
                            TXCLog.m2914e("TXCMP4HWMuxer", "addVideoTrack IllegalStateException: " + e2);
                            return -6;
                        }
                    }
                    if (this.f5448e != null) {
                        try {
                            try {
                                this.f5451h = this.f5445b.addTrack(this.f5448e);
                            } catch (IllegalArgumentException e3) {
                                TXCLog.m2914e("TXCMP4HWMuxer", "addAudioTrack IllegalArgumentException: " + e3);
                                return -7;
                            }
                        } catch (IllegalStateException e4) {
                            TXCLog.m2914e("TXCMP4HWMuxer", "addAudioTrack IllegalStateException: " + e4);
                            return -8;
                        }
                    }
                    this.f5445b.start();
                    this.f5457n = -1L;
                    this.f5453j = true;
                    this.f5454k = false;
                    this.f5458o = -1L;
                    this.f5459p = -1L;
                    return 0;
                } catch (IOException e5) {
                    e5.printStackTrace();
                    TXCLog.m2914e("TXCMP4HWMuxer", "create MediaMuxer exception:" + e5);
                    return -4;
                }
            }
        }
        TXCLog.m2914e("TXCMP4HWMuxer", "target path not set yet!");
        return -1;
    }

    /* renamed from: d */
    public synchronized int m577d() {
        if (this.f5445b != null) {
            TXCLog.m2915d("TXCMP4HWMuxer", "stop. start flag = " + this.f5453j + ", video key frame set = " + this.f5454k);
            try {
                if (this.f5453j && this.f5454k) {
                    this.f5445b.stop();
                }
                this.f5445b.release();
                this.f5453j = false;
                this.f5445b = null;
                this.f5450g = 0;
                this.f5454k = false;
                this.f5455l.clear();
                this.f5456m.clear();
                this.f5447d = null;
                this.f5448e = null;
                this.f5458o = -1L;
                this.f5459p = -1L;
            } catch (Exception e) {
                TXCLog.m2914e("TXCMP4HWMuxer", "muxer stop/release exception: " + e);
                this.f5453j = false;
                this.f5445b = null;
                this.f5450g = 0;
                this.f5454k = false;
                this.f5455l.clear();
                this.f5456m.clear();
                this.f5447d = null;
                this.f5448e = null;
                this.f5458o = -1L;
                this.f5459p = -1L;
                return -1;
            }
        }
        return 0;
    }

    /* renamed from: a */
    public synchronized void m585a(String str) {
        this.f5446c = str;
    }

    /* renamed from: a */
    public synchronized void m584a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f5445b == null) {
            m583a(true, byteBuffer, bufferInfo);
            TXCLog.m2911w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.f5457n < 0) {
            this.f5457n = m575e();
            m574f();
        }
        m578c(byteBuffer, bufferInfo);
    }

    /* renamed from: b */
    public synchronized void m580b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f5445b != null && this.f5457n >= 0) {
            m576d(byteBuffer, bufferInfo);
            return;
        }
        TXCLog.m2911w("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
        m583a(false, byteBuffer, bufferInfo);
    }

    /* renamed from: c */
    private void m578c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        long j = bufferInfo.presentationTimeUs - this.f5457n;
        if (j < 0) {
            TXCLog.m2914e("TXCMP4HWMuxer", "drop frame. first frame offset timeus = " + this.f5457n + ", current timeus = " + bufferInfo.presentationTimeUs);
        } else if (j < this.f5458o) {
            TXCLog.m2914e("TXCMP4HWMuxer", "drop frame. current frame's pts(" + j + ") must larger than pre frame's pts(" + this.f5458o + ")");
        } else {
            this.f5458o = j;
            bufferInfo.presentationTimeUs = j;
            try {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.f5445b.writeSampleData(this.f5452i, byteBuffer, bufferInfo);
                if ((bufferInfo.flags & 1) == 0) {
                    return;
                }
                this.f5454k = true;
            } catch (IllegalStateException e) {
                TXCLog.m2914e("TXCMP4HWMuxer", "write frame IllegalStateException: " + e);
            }
        }
    }

    /* renamed from: d */
    private void m576d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        long j = bufferInfo.presentationTimeUs;
        long j2 = this.f5457n;
        long j3 = j - j2;
        if (j2 < 0 || j3 < 0) {
            TXCLog.m2911w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.f5457n + ", current sample timeus = " + bufferInfo.presentationTimeUs);
        } else if (j3 < this.f5459p) {
            TXCLog.m2914e("TXCMP4HWMuxer", "drop sample. current sample's pts pts(" + j3 + ") must larger than pre frame's pts(" + this.f5459p + ")");
        } else {
            this.f5459p = j3;
            bufferInfo.presentationTimeUs = j3;
            try {
                this.f5445b.writeSampleData(this.f5451h, byteBuffer, bufferInfo);
            } catch (IllegalStateException e) {
                TXCLog.m2914e("TXCMP4HWMuxer", "write sample IllegalStateException: " + e);
            }
        }
    }

    /* renamed from: a */
    private void m583a(boolean z, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (byteBuffer == null || bufferInfo == null) {
            return;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
        byteBuffer.rewind();
        if (bufferInfo.size > 0) {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.size);
        }
        allocateDirect.rewind();
        allocateDirect.put(byteBuffer);
        MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
        bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        C3660a c3660a = new C3660a(allocateDirect, bufferInfo2);
        if (z) {
            if (this.f5455l.size() < 200) {
                this.f5455l.add(c3660a);
            } else {
                TXCLog.m2914e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
            }
        } else if (this.f5456m.size() < 300) {
            this.f5456m.add(c3660a);
        } else {
            TXCLog.m2914e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 300");
        }
    }

    /* renamed from: e */
    private long m575e() {
        C3660a peek;
        long j = this.f5455l.size() > 0 ? this.f5455l.peek().m572b().presentationTimeUs : 0L;
        if (this.f5456m.size() <= 0 || (peek = this.f5456m.peek()) == null || peek.m572b() == null) {
            return j;
        }
        long j2 = this.f5456m.peek().m572b().presentationTimeUs;
        return j > j2 ? j2 : j;
    }

    /* renamed from: f */
    private void m574f() {
        while (this.f5455l.size() > 0) {
            C3660a poll = this.f5455l.poll();
            m578c(poll.m573a(), poll.m572b());
        }
        while (this.f5456m.size() > 0) {
            C3660a poll2 = this.f5456m.poll();
            m576d(poll2.m573a(), poll2.m572b());
        }
    }
}
