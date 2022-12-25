package com.tencent.liteav.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.text.TextUtils;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.muxer.jni.TXSWMuxerJNI;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* renamed from: com.tencent.liteav.muxer.d */
/* loaded from: classes3.dex */
public class TXCMP4SWMuxer implements ITXCMP4Muxer {

    /* renamed from: a */
    public static float f4707a = 0.5f;

    /* renamed from: b */
    public static float f4708b = 0.8f;

    /* renamed from: c */
    public static float f4709c = 1.25f;

    /* renamed from: d */
    public static float f4710d = 2.0f;

    /* renamed from: f */
    private TXSWMuxerJNI f4712f;

    /* renamed from: e */
    private int f4711e = 2;

    /* renamed from: g */
    private String f4713g = null;

    /* renamed from: h */
    private MediaFormat f4714h = null;

    /* renamed from: i */
    private MediaFormat f4715i = null;

    /* renamed from: j */
    private int f4716j = 0;

    /* renamed from: k */
    private int f4717k = 0;

    /* renamed from: l */
    private boolean f4718l = false;

    /* renamed from: m */
    private boolean f4719m = false;

    /* renamed from: n */
    private ConcurrentLinkedQueue<C3556a> f4720n = new ConcurrentLinkedQueue<>();

    /* renamed from: o */
    private ConcurrentLinkedQueue<C3556a> f4721o = new ConcurrentLinkedQueue<>();

    /* renamed from: p */
    private long f4722p = -1;

    /* renamed from: q */
    private long f4723q = -1;

    /* renamed from: r */
    private long f4724r = -1;

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1234a(int i) {
        this.f4711e = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCMP4SWMuxer.java */
    /* renamed from: com.tencent.liteav.muxer.d$a */
    /* loaded from: classes3.dex */
    public static class C3556a {

        /* renamed from: a */
        ByteBuffer f4725a;

        /* renamed from: b */
        MediaCodec.BufferInfo f4726b;

        public C3556a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.f4725a = byteBuffer;
            this.f4726b = bufferInfo;
        }

        /* renamed from: a */
        public ByteBuffer m1213a() {
            return this.f4725a;
        }

        /* renamed from: b */
        public MediaCodec.BufferInfo m1212b() {
            return this.f4726b;
        }
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1232a(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4SWMuxer", "addVideoTrack:" + mediaFormat);
        this.f4714h = mediaFormat;
        this.f4720n.clear();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public synchronized void mo1226b(MediaFormat mediaFormat) {
        TXCLog.m2915d("TXCMP4SWMuxer", "addAudioTrack:" + mediaFormat);
        this.f4715i = mediaFormat;
        this.f4721o.clear();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: c */
    public synchronized boolean mo1223c() {
        return this.f4714h != null;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: d */
    public synchronized boolean mo1221d() {
        return this.f4715i != null;
    }

    /* renamed from: e */
    private ByteBuffer m1219e() {
        ByteBuffer byteBuffer = this.f4715i.getByteBuffer("csd-0");
        byteBuffer.position(0);
        return byteBuffer;
    }

    /* renamed from: f */
    private ByteBuffer m1218f() {
        return this.f4714h.getByteBuffer("csd-0");
    }

    /* renamed from: g */
    private ByteBuffer m1217g() {
        return this.f4714h.getByteBuffer("csd-1");
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized int mo1235a() {
        if (this.f4713g != null && !this.f4713g.isEmpty()) {
            if (!mo1223c()) {
                TXCLog.m2914e("TXCMP4SWMuxer", "video track not set yet!");
                return -2;
            } else if (this.f4712f != null) {
                TXCLog.m2911w("TXCMP4SWMuxer", "start has been called. stop must be called before start");
                return 0;
            } else {
                TXCLog.m2915d("TXCMP4SWMuxer", C2516Ad.TYPE_START);
                this.f4712f = new TXSWMuxerJNI();
                TXSWMuxerJNI.AVOptions aVOptions = new TXSWMuxerJNI.AVOptions();
                if (this.f4714h != null) {
                    int integer = this.f4714h.getInteger("width");
                    aVOptions.videoHeight = this.f4714h.getInteger("height");
                    aVOptions.videoWidth = integer;
                    aVOptions.videoGOP = this.f4714h.containsKey("i-frame-interval") ? this.f4714h.getInteger("i-frame-interval") : 3;
                }
                if (this.f4715i != null) {
                    int integer2 = this.f4715i.getInteger("channel-count");
                    int integer3 = this.f4715i.getInteger("sample-rate");
                    aVOptions.audioChannels = integer2;
                    aVOptions.audioSampleRate = integer3;
                }
                ByteBuffer m1218f = m1218f();
                ByteBuffer m1217g = m1217g();
                ByteBuffer byteBuffer = null;
                if (this.f4715i != null) {
                    byteBuffer = m1219e();
                }
                if (m1218f != null && m1217g != null) {
                    if (this.f4715i != null && byteBuffer == null) {
                        TXCLog.m2914e("TXCMP4SWMuxer", "audio format contains error csd!");
                        return -3;
                    }
                    this.f4712f.m1206a(m1218f, m1218f.capacity(), m1217g, m1217g.capacity());
                    if (this.f4715i != null) {
                        this.f4712f.m1208a(byteBuffer, byteBuffer.capacity());
                    }
                    this.f4712f.m1210a(aVOptions);
                    this.f4712f.m1209a(this.f4713g);
                    this.f4712f.m1211a();
                    this.f4722p = -1L;
                    this.f4718l = true;
                    this.f4719m = false;
                    this.f4723q = -1L;
                    this.f4724r = -1L;
                    return 0;
                }
                TXCLog.m2914e("TXCMP4SWMuxer", "video format contains error csd!");
                return -3;
            }
        }
        TXCLog.m2914e("TXCMP4SWMuxer", "target path not set yet!");
        return -1;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public synchronized int mo1227b() {
        if (this.f4712f != null) {
            m1215i();
            TXCLog.m2915d("TXCMP4SWMuxer", "stop. start flag = " + this.f4718l + ", video key frame set = " + this.f4719m);
            try {
                if (this.f4718l && this.f4719m) {
                    this.f4712f.m1205b();
                }
                this.f4712f.m1203c();
                this.f4712f = null;
                this.f4718l = false;
                this.f4712f = null;
                this.f4719m = false;
                this.f4720n.clear();
                this.f4721o.clear();
                this.f4714h = null;
                this.f4715i = null;
                this.f4723q = -1L;
                this.f4724r = -1L;
            } catch (Exception e) {
                TXCLog.m2914e("TXCMP4SWMuxer", "muxer stop/release exception: " + e);
                this.f4718l = false;
                this.f4712f = null;
                this.f4719m = false;
                this.f4720n.clear();
                this.f4721o.clear();
                this.f4714h = null;
                this.f4715i = null;
                this.f4723q = -1L;
                this.f4724r = -1L;
                return -1;
            }
        }
        return 0;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1231a(String str) {
        this.f4713g = str;
        if (!TextUtils.isEmpty(this.f4713g)) {
            File file = new File(this.f4713g);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        m1225b(allocateDirect, bufferInfo);
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
    public synchronized void m1225b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f4712f == null) {
            m1229a(true, byteBuffer, bufferInfo);
            TXCLog.m2911w("TXCMP4SWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.f4722p < 0) {
            m1229a(true, byteBuffer, bufferInfo);
            this.f4722p = m1216h();
            TXCLog.m2915d("TXCMP4SWMuxer", "first frame offset = " + this.f4722p);
            m1214j();
        } else {
            m1233a(bufferInfo.presentationTimeUs);
            m1222c(byteBuffer, bufferInfo);
        }
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public synchronized void mo1230a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        m1229a(false, byteBuffer, bufferInfo);
    }

    /* renamed from: c */
    private void m1222c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f;
        float f2;
        long j = bufferInfo.presentationTimeUs - this.f4722p;
        if (j < 0) {
            TXCLog.m2914e("TXCMP4SWMuxer", "pts error! first frame offset timeus = " + this.f4722p + ", current timeus = " + bufferInfo.presentationTimeUs);
            j = this.f4723q;
            if (j <= 0) {
                j = 0;
            }
        }
        if (j < this.f4723q) {
            TXCLog.m2911w("TXCMP4SWMuxer", "video is not in chronological order. current frame's pts(" + j + ") smaller than pre frame's pts(" + this.f4723q + ")");
        } else {
            this.f4723q = j;
        }
        int i = this.f4711e;
        if (i != 2) {
            if (i == 3) {
                f = (float) j;
                f2 = f4708b;
            } else if (i == 4) {
                f = (float) j;
                f2 = f4707a;
            } else if (i == 1) {
                f = (float) j;
                f2 = f4709c;
            } else if (i == 0) {
                f = (float) j;
                f2 = f4710d;
            }
            j = f * f2;
        }
        bufferInfo.presentationTimeUs = j;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f4712f.m1207a(byteBuffer, 1, bufferInfo.offset, bufferInfo.size, bufferInfo.flags == 1 ? 1 : 0, bufferInfo.presentationTimeUs);
            if ((bufferInfo.flags & 1) == 0) {
                return;
            }
            this.f4719m = true;
        } catch (IllegalArgumentException e) {
            TXCLog.m2914e("TXCMP4SWMuxer", "write frame IllegalArgumentException: " + e);
        } catch (IllegalStateException e2) {
            TXCLog.m2914e("TXCMP4SWMuxer", "write frame IllegalStateException: " + e2);
        }
    }

    /* renamed from: d */
    private void m1220d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f;
        float f2;
        long j = bufferInfo.presentationTimeUs;
        long j2 = this.f4722p;
        long j3 = j - j2;
        if (j2 < 0 || j3 < 0) {
            TXCLog.m2911w("TXCMP4SWMuxer", "drop sample. first frame offset timeus = " + this.f4722p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (j3 < this.f4724r) {
            TXCLog.m2914e("TXCMP4SWMuxer", "audio is not in chronological order. current audio's pts pts(" + j3 + ") must larger than pre audio's pts(" + this.f4724r + ")");
            j3 = this.f4724r + 1;
        } else {
            this.f4724r = j3;
        }
        int i = this.f4711e;
        if (i != 2) {
            if (i == 3) {
                f = (float) j3;
                f2 = f4708b;
            } else if (i == 4) {
                f = (float) j3;
                f2 = f4707a;
            } else if (i == 1) {
                f = (float) j3;
                f2 = f4709c;
            } else if (i == 0) {
                f = (float) j3;
                f2 = f4710d;
            }
            j3 = f * f2;
        }
        bufferInfo.presentationTimeUs = j3;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f4712f.m1207a(byteBuffer, 0, bufferInfo.offset, bufferInfo.size, bufferInfo.flags, bufferInfo.presentationTimeUs);
        } catch (IllegalArgumentException e) {
            TXCLog.m2914e("TXCMP4SWMuxer", "write sample IllegalArgumentException: " + e);
        } catch (IllegalStateException e2) {
            TXCLog.m2914e("TXCMP4SWMuxer", "write sample IllegalStateException: " + e2);
        }
    }

    /* renamed from: a */
    private void m1229a(boolean z, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
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
        C3556a c3556a = new C3556a(allocateDirect, bufferInfo2);
        if (z) {
            if (this.f4720n.size() < 200) {
                this.f4720n.add(c3556a);
                return;
            } else {
                TXCLog.m2914e("TXCMP4SWMuxer", "drop video frame. video cache size is larger than 200");
                return;
            }
        }
        this.f4721o.add(c3556a);
    }

    /* renamed from: h */
    private long m1216h() {
        C3556a peek;
        long j = this.f4720n.size() > 0 ? this.f4720n.peek().m1212b().presentationTimeUs : 0L;
        if (this.f4721o.size() <= 0 || (peek = this.f4721o.peek()) == null || peek.m1212b() == null) {
            return j;
        }
        long j2 = this.f4721o.peek().m1212b().presentationTimeUs;
        return j > j2 ? j2 : j;
    }

    /* renamed from: i */
    private void m1215i() {
        while (this.f4720n.size() > 0) {
            C3556a poll = this.f4720n.poll();
            m1222c(poll.m1213a(), poll.m1212b());
        }
        while (this.f4721o.size() > 0) {
            C3556a poll2 = this.f4721o.poll();
            m1220d(poll2.m1213a(), poll2.m1212b());
        }
    }

    /* renamed from: j */
    private void m1214j() {
        while (this.f4720n.size() > 0) {
            C3556a poll = this.f4720n.poll();
            m1233a(poll.m1212b().presentationTimeUs);
            m1222c(poll.m1213a(), poll.m1212b());
        }
    }

    /* renamed from: a */
    private void m1233a(long j) {
        while (this.f4721o.size() > 0) {
            if (this.f4721o.peek().m1212b() == null) {
                TXCLog.m2914e("TXCMP4SWMuxer", "flushAudioCache, bufferInfo is null");
                this.f4721o.remove();
            } else if (this.f4721o.peek().m1212b().presentationTimeUs >= j) {
                return;
            } else {
                C3556a poll = this.f4721o.poll();
                m1220d(poll.m1213a(), poll.m1212b());
            }
        }
    }
}
