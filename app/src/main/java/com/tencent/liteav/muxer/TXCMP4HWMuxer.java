package com.tencent.liteav.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* renamed from: com.tencent.liteav.muxer.b */
/* loaded from: classes3.dex */
public class TXCMP4HWMuxer implements ITXCMP4Muxer {

    /* renamed from: a */
    public static float f4685a = 0.5f;

    /* renamed from: b */
    public static float f4686b = 0.8f;

    /* renamed from: c */
    public static float f4687c = 1.25f;

    /* renamed from: d */
    public static float f4688d = 2.0f;

    /* renamed from: f */
    private MediaMuxer f4690f;

    /* renamed from: e */
    private int f4689e = 2;

    /* renamed from: g */
    private String f4691g = null;

    /* renamed from: h */
    private MediaFormat f4692h = null;

    /* renamed from: i */
    private MediaFormat f4693i = null;

    /* renamed from: j */
    private int f4694j = 0;

    /* renamed from: k */
    private int f4695k = 0;

    /* renamed from: l */
    private boolean f4696l = false;

    /* renamed from: m */
    private boolean f4697m = false;

    /* renamed from: n */
    private ConcurrentLinkedQueue<C3555a> f4698n = new ConcurrentLinkedQueue<>();

    /* renamed from: o */
    private ConcurrentLinkedQueue<C3555a> f4699o = new ConcurrentLinkedQueue<>();

    /* renamed from: p */
    private long f4700p = -1;

    /* renamed from: q */
    private long f4701q = -1;

    /* renamed from: r */
    private long f4702r = -1;

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1234a(int i) {
        this.f4689e = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCMP4HWMuxer.java */
    /* renamed from: com.tencent.liteav.muxer.b$a */
    /* loaded from: classes3.dex */
    public static class C3555a {

        /* renamed from: a */
        ByteBuffer f4703a;

        /* renamed from: b */
        MediaCodec.BufferInfo f4704b;

        public C3555a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.f4703a = byteBuffer;
            this.f4704b = bufferInfo;
        }

        /* renamed from: a */
        public ByteBuffer m1238a() {
            return this.f4703a;
        }

        /* renamed from: b */
        public MediaCodec.BufferInfo m1237b() {
            return this.f4704b;
        }
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1232a(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4HWMuxer", "addVideoTrack:" + mediaFormat);
        this.f4692h = mediaFormat;
        this.f4698n.clear();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public synchronized void mo1226b(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4HWMuxer", "addAudioTrack:" + mediaFormat);
        this.f4693i = mediaFormat;
        this.f4699o.clear();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: c */
    public synchronized boolean mo1223c() {
        return this.f4692h != null;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: d */
    public synchronized boolean mo1221d() {
        return this.f4693i != null;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized int mo1235a() {
        if (this.f4691g != null && !this.f4691g.isEmpty()) {
            if (!mo1223c()) {
                TXCLog.m2914e("TXCMP4HWMuxer", "video track not set yet!");
                return -2;
            } else if (this.f4690f != null) {
                TXCLog.m2911w("TXCMP4HWMuxer", "start has been called. stop must be called before start");
                return 0;
            } else {
                TXCLog.m2915d("TXCMP4HWMuxer", C2516Ad.TYPE_START);
                try {
                    this.f4690f = new MediaMuxer(this.f4691g, 0);
                    if (this.f4692h != null) {
                        try {
                            try {
                                this.f4695k = this.f4690f.addTrack(this.f4692h);
                            } catch (IllegalArgumentException e) {
                                TXCLog.m2914e("TXCMP4HWMuxer", "addVideoTrack IllegalArgumentException: " + e);
                                return -5;
                            }
                        } catch (IllegalStateException e2) {
                            TXCLog.m2914e("TXCMP4HWMuxer", "addVideoTrack IllegalStateException: " + e2);
                            return -6;
                        }
                    }
                    if (this.f4693i != null) {
                        try {
                            try {
                                this.f4694j = this.f4690f.addTrack(this.f4693i);
                            } catch (IllegalArgumentException e3) {
                                TXCLog.m2914e("TXCMP4HWMuxer", "addAudioTrack IllegalArgumentException: " + e3);
                                return -7;
                            }
                        } catch (IllegalStateException e4) {
                            TXCLog.m2914e("TXCMP4HWMuxer", "addAudioTrack IllegalStateException: " + e4);
                            return -8;
                        }
                    }
                    this.f4690f.start();
                    this.f4700p = -1L;
                    this.f4696l = true;
                    this.f4697m = false;
                    this.f4701q = -1L;
                    this.f4702r = -1L;
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

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public synchronized int mo1227b() {
        if (this.f4690f != null) {
            TXCLog.m2915d("TXCMP4HWMuxer", "stop. start flag = " + this.f4696l + ", video key frame set = " + this.f4697m);
            try {
                if (this.f4696l && this.f4697m) {
                    this.f4690f.stop();
                }
                this.f4690f.release();
                this.f4696l = false;
                this.f4690f = null;
                this.f4697m = false;
                this.f4698n.clear();
                this.f4699o.clear();
                this.f4692h = null;
                this.f4693i = null;
                this.f4701q = -1L;
                this.f4702r = -1L;
            } catch (Exception e) {
                TXCLog.m2914e("TXCMP4HWMuxer", "muxer stop/release exception: " + e);
                this.f4696l = false;
                this.f4690f = null;
                this.f4697m = false;
                this.f4698n.clear();
                this.f4699o.clear();
                this.f4692h = null;
                this.f4693i = null;
                this.f4701q = -1L;
                this.f4702r = -1L;
                return -1;
            }
        }
        return 0;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1231a(String str) {
        this.f4691g = str;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public synchronized void mo1224b(byte[] bArr, int i, int i2, long j, int i3) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i2);
        allocateDirect.put(bArr, i, i2);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j;
        bufferInfo.offset = 0;
        bufferInfo.size = i2;
        bufferInfo.flags = i3;
        m1243b(allocateDirect, bufferInfo);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1228a(byte[] bArr, int i, int i2, long j, int i3) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i2);
        allocateDirect.put(bArr, i, i2);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j;
        bufferInfo.offset = 0;
        bufferInfo.size = i2;
        bufferInfo.flags = i3;
        mo1230a(allocateDirect, bufferInfo);
    }

    /* renamed from: b */
    public synchronized void m1243b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f4690f == null) {
            m1244a(true, byteBuffer, bufferInfo);
            TXCLog.m2911w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.f4700p < 0) {
            m1244a(true, byteBuffer, bufferInfo);
            this.f4700p = m1240e();
            TXCLog.m2915d("TXCMP4HWMuxer", "first frame offset = " + this.f4700p);
            m1239f();
        } else {
            m1242c(byteBuffer, bufferInfo);
        }
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1230a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f4690f != null && this.f4700p >= 0) {
            m1241d(byteBuffer, bufferInfo);
            return;
        }
        TXCLog.m2911w("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
        m1244a(false, byteBuffer, bufferInfo);
    }

    /* renamed from: c */
    private void m1242c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f;
        float f2;
        long j = bufferInfo.presentationTimeUs - this.f4700p;
        if (j < 0) {
            TXCLog.m2914e("TXCMP4HWMuxer", "pts error! first frame offset timeus = " + this.f4700p + ", current timeus = " + bufferInfo.presentationTimeUs);
            j = this.f4701q;
            if (j <= 0) {
                j = 0;
            }
        }
        if (j < this.f4701q) {
            TXCLog.m2911w("TXCMP4HWMuxer", "video is not in chronological order. current frame's pts(" + j + ") smaller than pre frame's pts(" + this.f4701q + ")");
        } else {
            this.f4701q = j;
        }
        int i = this.f4689e;
        if (i != 2) {
            if (i == 3) {
                f = (float) j;
                f2 = f4686b;
            } else if (i == 4) {
                f = (float) j;
                f2 = f4685a;
            } else if (i == 1) {
                f = (float) j;
                f2 = f4687c;
            } else if (i == 0) {
                f = (float) j;
                f2 = f4688d;
            }
            j = f * f2;
        }
        bufferInfo.presentationTimeUs = j;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f4690f.writeSampleData(this.f4695k, byteBuffer, bufferInfo);
            if ((bufferInfo.flags & 1) == 0) {
                return;
            }
            this.f4697m = true;
        } catch (IllegalArgumentException e) {
            TXCLog.m2914e("TXCMP4HWMuxer", "write frame IllegalArgumentException: " + e);
        } catch (IllegalStateException e2) {
            TXCLog.m2914e("TXCMP4HWMuxer", "write frame IllegalStateException: " + e2);
        }
    }

    /* renamed from: d */
    private void m1241d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f;
        float f2;
        long j = bufferInfo.presentationTimeUs;
        long j2 = this.f4700p;
        long j3 = j - j2;
        if (j2 < 0 || j3 < 0) {
            TXCLog.m2911w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.f4700p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (j3 < this.f4702r) {
            TXCLog.m2914e("TXCMP4HWMuxer", "audio is not in chronological order. current audio's pts pts(" + j3 + ") must larger than pre audio's pts(" + this.f4702r + ")");
            j3 = this.f4702r + 1;
        } else {
            this.f4702r = j3;
        }
        int i = this.f4689e;
        if (i != 2) {
            if (i == 3) {
                f = (float) j3;
                f2 = f4686b;
            } else if (i == 4) {
                f = (float) j3;
                f2 = f4685a;
            } else if (i == 1) {
                f = (float) j3;
                f2 = f4687c;
            } else if (i == 0) {
                f = (float) j3;
                f2 = f4688d;
            }
            j3 = f * f2;
        }
        bufferInfo.presentationTimeUs = j3;
        try {
            this.f4690f.writeSampleData(this.f4694j, byteBuffer, bufferInfo);
        } catch (IllegalArgumentException e) {
            TXCLog.m2914e("TXCMP4HWMuxer", "write sample IllegalArgumentException: " + e);
        } catch (IllegalStateException e2) {
            TXCLog.m2914e("TXCMP4HWMuxer", "write sample IllegalStateException: " + e2);
        }
    }

    /* renamed from: a */
    private void m1244a(boolean z, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
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
        C3555a c3555a = new C3555a(allocateDirect, bufferInfo2);
        if (z) {
            if (this.f4698n.size() < 200) {
                this.f4698n.add(c3555a);
            } else {
                TXCLog.m2914e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
            }
        } else if (this.f4699o.size() < 600) {
            this.f4699o.add(c3555a);
        } else {
            TXCLog.m2914e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 600");
        }
    }

    /* renamed from: e */
    private long m1240e() {
        C3555a peek;
        long j = this.f4698n.size() > 0 ? this.f4698n.peek().m1237b().presentationTimeUs : 0L;
        if (this.f4699o.size() <= 0 || (peek = this.f4699o.peek()) == null || peek.m1237b() == null) {
            return j;
        }
        long j2 = this.f4699o.peek().m1237b().presentationTimeUs;
        return j > j2 ? j2 : j;
    }

    /* renamed from: f */
    private void m1239f() {
        while (this.f4698n.size() > 0) {
            C3555a poll = this.f4698n.poll();
            m1242c(poll.m1238a(), poll.m1237b());
        }
        while (this.f4699o.size() > 0) {
            C3555a poll2 = this.f4699o.poll();
            m1241d(poll2.m1238a(), poll2.m1237b());
        }
    }
}
