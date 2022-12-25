package com.tencent.liteav.videoencoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import com.iceteck.silicompressorr.videocompression.MediaController;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import com.tencent.liteav.basic.p109e.EGL14Helper;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCThread;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import javax.microedition.khronos.egl.EGL10;

/* renamed from: com.tencent.liteav.videoencoder.a */
/* loaded from: classes3.dex */
public class TXCHWVideoEncoder extends TXIVideoEncoder {

    /* renamed from: a */
    private static final String f5554a = "a";

    /* renamed from: H */
    private int f5562H;

    /* renamed from: I */
    private int f5563I;

    /* renamed from: J */
    private boolean f5564J;

    /* renamed from: o */
    private boolean f5582o;

    /* renamed from: s */
    private TXCThread f5586s;

    /* renamed from: x */
    private Object f5591x;

    /* renamed from: b */
    private int f5569b = 0;

    /* renamed from: c */
    private long f5570c = 0;

    /* renamed from: d */
    private long f5571d = 0;

    /* renamed from: e */
    private long f5572e = 0;

    /* renamed from: f */
    private long f5573f = 0;

    /* renamed from: g */
    private int f5574g = 0;

    /* renamed from: h */
    private boolean f5575h = false;

    /* renamed from: i */
    private boolean f5576i = true;

    /* renamed from: j */
    private long f5577j = 0;

    /* renamed from: k */
    private long f5578k = 0;

    /* renamed from: l */
    private long f5579l = 0;

    /* renamed from: m */
    private long f5580m = 0;

    /* renamed from: n */
    private long f5581n = 0;

    /* renamed from: p */
    private long f5583p = 0;

    /* renamed from: q */
    private long f5584q = 0;

    /* renamed from: r */
    private MediaCodec f5585r = null;

    /* renamed from: t */
    private Runnable f5587t = new Runnable() { // from class: com.tencent.liteav.videoencoder.a.6
        @Override // java.lang.Runnable
        public void run() {
            TXCHWVideoEncoder.this.m440c();
        }
    };

    /* renamed from: u */
    private Runnable f5588u = new Runnable() { // from class: com.tencent.liteav.videoencoder.a.7
        @Override // java.lang.Runnable
        public void run() {
            TXCHWVideoEncoder.this.m443b(10);
        }
    };

    /* renamed from: v */
    private Runnable f5589v = new Runnable() { // from class: com.tencent.liteav.videoencoder.a.8
        @Override // java.lang.Runnable
        public void run() {
            TXCHWVideoEncoder.this.m443b(1);
        }
    };

    /* renamed from: w */
    private ArrayDeque<Long> f5590w = new ArrayDeque<>(10);

    /* renamed from: y */
    private Surface f5592y = null;

    /* renamed from: z */
    private boolean f5593z = true;

    /* renamed from: A */
    private boolean f5555A = true;

    /* renamed from: B */
    private boolean f5556B = false;

    /* renamed from: C */
    private ByteBuffer[] f5557C = null;

    /* renamed from: D */
    private byte[] f5558D = null;

    /* renamed from: E */
    private volatile long f5559E = 0;

    /* renamed from: F */
    private long f5560F = 0;

    /* renamed from: G */
    private long f5561G = 0;

    /* renamed from: K */
    private int f5565K = 0;

    /* renamed from: L */
    private int f5566L = 0;

    /* renamed from: M */
    private int f5567M = 0;

    /* renamed from: N */
    private int f5568N = -1;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m436d(int i) {
    }

    public TXCHWVideoEncoder() {
        this.f5586s = null;
        this.f5586s = new TXCThread("HWVideoEncoder");
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public int start(final TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        final boolean[] zArr = new boolean[1];
        if (Build.VERSION.SDK_INT < 18) {
            zArr[0] = false;
        } else {
            synchronized (this) {
                this.f5586s.m2866a(new Runnable() { // from class: com.tencent.liteav.videoencoder.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCHWVideoEncoder tXCHWVideoEncoder = TXCHWVideoEncoder.this;
                        if (tXCHWVideoEncoder.mInit) {
                            tXCHWVideoEncoder.m440c();
                        }
                        zArr[0] = TXCHWVideoEncoder.this.m450a(tXSVideoEncoderParam);
                    }
                });
            }
        }
        if (!zArr[0]) {
            callDelegate(10000004);
        }
        return zArr[0] ? 0 : 10000004;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void stop() {
        this.f5555A = true;
        synchronized (this) {
            this.f5586s.m2866a(new Runnable() { // from class: com.tencent.liteav.videoencoder.a.2
                @Override // java.lang.Runnable
                public void run() {
                    TXCHWVideoEncoder tXCHWVideoEncoder = TXCHWVideoEncoder.this;
                    if (tXCHWVideoEncoder.mInit) {
                        tXCHWVideoEncoder.m440c();
                    }
                }
            });
        }
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void setFPS(final int i) {
        this.f5586s.m2864b(new Runnable() { // from class: com.tencent.liteav.videoencoder.a.3
            @Override // java.lang.Runnable
            public void run() {
                TXCHWVideoEncoder.this.m436d(i);
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void setBitrate(final int i) {
        this.f5569b = i;
        this.f5586s.m2864b(new Runnable() { // from class: com.tencent.liteav.videoencoder.a.4
            @Override // java.lang.Runnable
            public void run() {
                TXCHWVideoEncoder.this.m439c(i);
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long getRealFPS() {
        return this.f5571d;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long getRealBitrate() {
        return this.f5570c;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long pushVideoFrame(int i, int i2, int i3, long j) {
        if (this.f5555A) {
            return 10000004L;
        }
        GLES20.glFinish();
        this.f5559E = j;
        this.f5568N = i;
        this.f5565K++;
        if (this.f5564J) {
            m437d();
        }
        this.f5586s.m2864b(this.f5588u);
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long pushVideoFrameSync(int i, int i2, int i3, long j) {
        if (this.f5555A) {
            return 10000004L;
        }
        GLES20.glFinish();
        this.f5559E = j;
        this.f5568N = i;
        this.f5565K++;
        if (this.f5564J) {
            m437d();
        }
        this.f5586s.m2866a(this.f5589v);
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void signalEOSAndFlush() {
        if (this.f5555A) {
            return;
        }
        this.f5586s.m2866a(new Runnable() { // from class: com.tencent.liteav.videoencoder.a.5
            @Override // java.lang.Runnable
            public void run() {
                if (TXCHWVideoEncoder.this.f5585r == null) {
                    return;
                }
                try {
                    TXCHWVideoEncoder.this.f5585r.signalEndOfInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                do {
                } while (TXCHWVideoEncoder.this.m456a(10) >= 0);
                TXCHWVideoEncoder.this.m440c();
            }
        });
    }

    @TargetApi(16)
    /* renamed from: a */
    private MediaFormat m455a(int i, int i2, int i3, int i4, int i5) {
        if (i == 0 || i2 == 0 || i3 == 0 || i4 == 0) {
            return null;
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(MediaController.MIME_TYPE, i, i2);
        createVideoFormat.setInteger("bitrate", i3 * 1024);
        createVideoFormat.setInteger("frame-rate", i4);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("i-frame-interval", i5);
        return createVideoFormat;
    }

    @TargetApi(16)
    /* renamed from: a */
    private MediaFormat m454a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        MediaCodecInfo m446a;
        MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr;
        MediaFormat m455a = m455a(i, i2, i3, i4, i5);
        if (m455a == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 21 || (m446a = m446a(MediaController.MIME_TYPE)) == null) {
            return m455a;
        }
        MediaCodecInfo.CodecCapabilities capabilitiesForType = m446a.getCapabilitiesForType(MediaController.MIME_TYPE);
        MediaCodecInfo.EncoderCapabilities encoderCapabilities = capabilitiesForType.getEncoderCapabilities();
        if (encoderCapabilities.isBitrateModeSupported(i6)) {
            m455a.setInteger("bitrate-mode", i6);
        } else if (encoderCapabilities.isBitrateModeSupported(2)) {
            m455a.setInteger("bitrate-mode", 2);
        }
        m455a.setInteger("complexity", encoderCapabilities.getComplexityRange().clamp(5).intValue());
        if (Build.VERSION.SDK_INT >= 23) {
            int i8 = 0;
            for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
                int i9 = codecProfileLevel.profile;
                if (i9 <= i7 && i9 > i8) {
                    m455a.setInteger("profile", i9);
                    m455a.setInteger("level", codecProfileLevel.level);
                    i8 = i9;
                }
            }
        }
        return m455a;
    }

    @TargetApi(16)
    /* renamed from: a */
    private static MediaCodecInfo m446a(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* renamed from: a */
    private void m452a(long j) {
        this.f5590w.add(Long.valueOf(j));
    }

    /* renamed from: a */
    private long m457a() {
        Long poll = this.f5590w.poll();
        if (poll == null) {
            return 0L;
        }
        return poll.longValue();
    }

    /* renamed from: a */
    private boolean m451a(Surface surface, int i, int i2) {
        if (surface == null) {
            return false;
        }
        if (this.f5556B) {
            EGLContext eGLContext = (EGLContext) this.mGLContextExternal;
            if (eGLContext == null) {
                eGLContext = EGL14.EGL_NO_CONTEXT;
            }
            this.f5591x = EGL14Helper.m3075a(null, eGLContext, surface, i, i2);
        } else {
            javax.microedition.khronos.egl.EGLContext eGLContext2 = (javax.microedition.khronos.egl.EGLContext) this.mGLContextExternal;
            if (eGLContext2 == null) {
                eGLContext2 = EGL10.EGL_NO_CONTEXT;
            }
            this.f5591x = EGL10Helper.m3082a(null, eGLContext2, surface, i, i2);
        }
        if (this.f5591x == null) {
            return false;
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.mEncodeFilter = new TXCGPUFilter();
        this.mEncodeFilter.m3026a(TXCTextureRotationUtil.f2684e, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, false));
        if (!this.mEncodeFilter.mo2653c()) {
            this.mEncodeFilter = null;
            return false;
        }
        GLES20.glViewport(0, 0, i, i2);
        return true;
    }

    /* renamed from: b */
    private void m444b() {
        TXCGPUFilter tXCGPUFilter = this.mEncodeFilter;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.mEncodeFilter = null;
        }
        Object obj = this.f5591x;
        if (obj instanceof EGL10Helper) {
            ((EGL10Helper) obj).m3081b();
            this.f5591x = null;
        }
        Object obj2 = this.f5591x;
        if (obj2 instanceof EGL14Helper) {
            ((EGL14Helper) obj2).m3074b();
            this.f5591x = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(11:11|(1:13)|14|(7:(1:(1:18))(1:89)|19|(1:21)|22|23|24|(2:26|27)(12:29|30|31|33|34|35|36|37|39|40|41|(2:53|54)(2:47|(2:49|50)(2:51|52))))|90|19|(0)|22|23|24|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0117, code lost:
        r4 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0118, code lost:
        r5 = 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b9 A[Catch: Exception -> 0x0117, TryCatch #3 {Exception -> 0x0117, blocks: (B:24:0x00a8, B:26:0x00b9, B:29:0x00bc), top: B:23:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bc A[Catch: Exception -> 0x0117, TRY_LEAVE, TryCatch #3 {Exception -> 0x0117, blocks: (B:24:0x00a8, B:26:0x00b9, B:29:0x00bc), top: B:23:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x012d A[Catch: Exception -> 0x0135, TryCatch #5 {Exception -> 0x0135, blocks: (B:67:0x011e, B:69:0x0122, B:60:0x0127, B:62:0x012d, B:63:0x0132), top: B:66:0x011e }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x011e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @TargetApi(18)
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean m450a(TXSVideoEncoderParam tXSVideoEncoderParam) {
        int i;
        int i2;
        int i3;
        Surface surface;
        MediaFormat m454a;
        this.f5555A = false;
        this.f5593z = false;
        this.f5570c = 0L;
        this.f5571d = 0L;
        this.f5572e = 0L;
        this.f5573f = 0L;
        this.f5574g = 0;
        this.f5577j = 0L;
        this.f5578k = 0L;
        this.f5579l = 0L;
        this.f5580m = 0L;
        this.f5581n = 0L;
        this.f5583p = 0L;
        this.f5584q = 0L;
        this.f5557C = null;
        this.f5558D = null;
        this.f5559E = 0L;
        this.f5562H = -1;
        this.mOutputWidth = tXSVideoEncoderParam.width;
        this.mOutputHeight = tXSVideoEncoderParam.height;
        this.f5563I = tXSVideoEncoderParam.gop;
        this.f5564J = tXSVideoEncoderParam.fullIFrame;
        this.f5582o = tXSVideoEncoderParam.syncOutput;
        this.f5556B = tXSVideoEncoderParam.enableEGL14;
        this.f5590w.clear();
        if (tXSVideoEncoderParam == null || (i = tXSVideoEncoderParam.width) == 0 || (i2 = tXSVideoEncoderParam.height) == 0 || tXSVideoEncoderParam.fps == 0 || tXSVideoEncoderParam.gop == 0) {
            this.f5593z = true;
            return false;
        }
        this.f5575h = tXSVideoEncoderParam.annexb;
        this.f5576i = tXSVideoEncoderParam.appendSpsPps;
        if (this.f5569b == 0) {
            this.f5569b = (int) (Math.sqrt((i * i * 1.0d) + (i2 * i2)) * 1.2d);
        }
        this.f5577j = this.f5569b;
        this.f5574g = tXSVideoEncoderParam.fps;
        int i4 = tXSVideoEncoderParam.encoderMode;
        char c = 2;
        if (i4 != 1) {
            if (i4 == 2) {
                i3 = 1;
            } else if (i4 == 3) {
                i3 = 0;
            }
            if (TXCConfigCenter.m2988a().m2968c() == 1) {
                tXSVideoEncoderParam.encoderProfile = 1;
            }
            int i5 = tXSVideoEncoderParam.encoderProfile;
            m454a = m454a(tXSVideoEncoderParam.width, tXSVideoEncoderParam.height, this.f5569b, tXSVideoEncoderParam.fps, tXSVideoEncoderParam.gop, i3, 1);
            if (m454a != null) {
                this.f5593z = true;
                return false;
            }
            this.f5585r = MediaCodec.createEncoderByType(MediaController.MIME_TYPE);
            try {
                this.f5585r.configure(m454a, (Surface) null, (MediaCrypto) null, 1);
            } catch (Exception e) {
                try {
                    if (!(e instanceof IllegalArgumentException) && (Build.VERSION.SDK_INT < 21 || !(e instanceof MediaCodec.CodecException))) {
                        throw e;
                    }
                    this.f5585r.configure(m455a(tXSVideoEncoderParam.width, tXSVideoEncoderParam.height, this.f5569b, tXSVideoEncoderParam.fps, tXSVideoEncoderParam.gop), (Surface) null, (MediaCrypto) null, 1);
                    e.printStackTrace();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    if (c >= 5) {
                    }
                    this.f5585r = null;
                    if (this.f5592y != null) {
                    }
                    this.f5592y = null;
                    if (this.f5585r != null) {
                    }
                    this.f5593z = true;
                    return false;
                }
            }
            try {
                this.f5592y = this.f5585r.createInputSurface();
                try {
                    this.f5585r.start();
                } catch (Exception e3) {
                    e = e3;
                    c = 4;
                }
                try {
                    this.f5557C = this.f5585r.getOutputBuffers();
                } catch (Exception e4) {
                    e = e4;
                    c = 5;
                    e.printStackTrace();
                    if (c >= 5) {
                        try {
                            if (this.f5585r != null) {
                                this.f5585r.stop();
                            }
                        } catch (Exception unused) {
                        }
                    }
                    this.f5585r = null;
                    if (this.f5592y != null) {
                        this.f5592y.release();
                    }
                    this.f5592y = null;
                    if (this.f5585r != null) {
                    }
                    this.f5593z = true;
                    return false;
                }
            } catch (Exception e5) {
                e = e5;
                c = 3;
            }
            if (this.f5585r != null || this.f5557C == null || (surface = this.f5592y) == null) {
                this.f5593z = true;
                return false;
            } else if (!m451a(surface, tXSVideoEncoderParam.width, tXSVideoEncoderParam.height)) {
                this.f5593z = true;
                return false;
            } else {
                this.mInit = true;
                return true;
            }
        }
        i3 = 2;
        if (TXCConfigCenter.m2988a().m2968c() == 1) {
        }
        int i52 = tXSVideoEncoderParam.encoderProfile;
        m454a = m454a(tXSVideoEncoderParam.width, tXSVideoEncoderParam.height, this.f5569b, tXSVideoEncoderParam.fps, tXSVideoEncoderParam.gop, i3, 1);
        if (m454a != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(8:21|(10:33|(2:41|(9:(2:47|(2:49|(1:136)(2:57|58))(2:140|141))|59|60|(2:62|(3:64|(1:66)|67))(2:68|(3:70|(1:72)(1:74)|73)(24:75|(3:77|(1:79)(1:129)|80)(3:130|(1:132)(1:134)|133)|81|(2:83|(1:85))|86|(1:88)|89|(1:91)|92|(1:94)|95|(1:97)|98|(3:100|(1:102)(1:127)|103)(1:128)|104|(1:106)(1:126)|107|(3:109|(1:111)|112)(3:121|(1:123)(1:125)|124)|113|(3:115|(1:117)(1:119)|118)(1:120)|25|26|(1:28)|30))|24|25|26|(0)|30))|142|60|(0)(0)|24|25|26|(0)|30)|23|24|25|26|(0)|30) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0263, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0264, code lost:
        r0.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x025b A[Catch: IllegalStateException -> 0x0263, TRY_LEAVE, TryCatch #0 {IllegalStateException -> 0x0263, blocks: (B:26:0x0257, B:28:0x025b), top: B:25:0x0257 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00bc  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int m456a(int i) {
        byte[] bArr;
        byte[] m445a;
        int i2;
        long j;
        long j2;
        int i3;
        TXCHWVideoEncoder tXCHWVideoEncoder;
        boolean z;
        long j3;
        long j4;
        int i4 = -1;
        if (this.f5585r == null) {
            return -1;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        try {
            int dequeueOutputBuffer = this.f5585r.dequeueOutputBuffer(bufferInfo, i * 1000);
            if (dequeueOutputBuffer == -1) {
                return 0;
            }
            if (dequeueOutputBuffer == -3) {
                this.f5557C = this.f5585r.getOutputBuffers();
                return 1;
            } else if (dequeueOutputBuffer == -2) {
                callDelegate(this.f5585r.getOutputFormat());
                return 1;
            } else if (dequeueOutputBuffer < 0) {
                return -1;
            } else {
                ByteBuffer byteBuffer = this.f5557C[dequeueOutputBuffer];
                if (byteBuffer != null) {
                    byte[] bArr2 = new byte[bufferInfo.size];
                    byteBuffer.position(bufferInfo.offset);
                    byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    byteBuffer.get(bArr2, 0, bufferInfo.size);
                    int length = bArr2.length;
                    if (bufferInfo.size > 5 && bArr2[0] == 0 && bArr2[1] == 0 && bArr2[2] == 0) {
                        int i5 = 3;
                        if (bArr2[3] == 0 && bArr2[4] == 0 && bArr2[5] == 0) {
                            while (true) {
                                if (i5 >= bArr2.length - 4) {
                                    i5 = 0;
                                    break;
                                } else if (bArr2[i5] == 0 && bArr2[i5 + 1] == 0 && bArr2[i5 + 2] == 0 && bArr2[i5 + 3] == 1) {
                                    length -= i5;
                                    break;
                                } else {
                                    i5++;
                                }
                            }
                            bArr = new byte[length];
                            System.arraycopy(bArr2, i5, bArr, 0, length);
                            if (bufferInfo.size != 0) {
                                if ((bufferInfo.flags & 4) != 0) {
                                    TXIVideoEncoderListener tXIVideoEncoderListener = this.mListener;
                                    if (tXIVideoEncoderListener != null) {
                                        tXIVideoEncoderListener.onEncodeNAL(null, 0);
                                    }
                                    tXCHWVideoEncoder = this;
                                    i3 = dequeueOutputBuffer;
                                    i4 = -2;
                                }
                            } else {
                                int i6 = bufferInfo.flags;
                                if ((i6 & 2) == 2) {
                                    if (this.f5575h) {
                                        this.f5558D = (byte[]) bArr.clone();
                                    } else {
                                        this.f5558D = m445a((byte[]) bArr.clone());
                                    }
                                    tXCHWVideoEncoder = this;
                                    i3 = dequeueOutputBuffer;
                                    i4 = 1;
                                } else {
                                    if ((i6 & 1) == 1) {
                                        this.f5562H = -1;
                                        if (this.f5575h) {
                                            byte[] bArr3 = this.f5558D;
                                            byte[] bArr4 = new byte[bArr3.length + bArr.length];
                                            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                                            System.arraycopy(bArr, 0, bArr4, this.f5558D.length, bArr.length);
                                            m445a = bArr4;
                                        } else {
                                            byte[] m445a2 = m445a(bArr);
                                            byte[] bArr5 = this.f5558D;
                                            m445a = new byte[bArr5.length + m445a2.length];
                                            System.arraycopy(bArr5, 0, m445a, 0, bArr5.length);
                                            System.arraycopy(m445a2, 0, m445a, this.f5558D.length, m445a2.length);
                                        }
                                        i2 = 0;
                                    } else {
                                        m445a = !this.f5575h ? m445a(bArr) : bArr;
                                        i2 = 1;
                                    }
                                    if (!this.f5564J) {
                                        int i7 = this.f5562H + 1;
                                        this.f5562H = i7;
                                        if (i7 == this.f5574g * this.f5563I) {
                                            m437d();
                                        }
                                    }
                                    long m457a = m457a();
                                    long j5 = bufferInfo.presentationTimeUs / 1000;
                                    if (this.f5561G == 0) {
                                        this.f5561G = m457a;
                                    }
                                    if (this.f5560F == 0) {
                                        this.f5560F = j5;
                                    }
                                    long j6 = j5 + (this.f5561G - this.f5560F);
                                    long j7 = this.f5581n;
                                    if (m457a <= j7) {
                                        m457a = j7 + 1;
                                    }
                                    if (m457a > j6) {
                                        m457a = j6;
                                    }
                                    this.f5581n = m457a;
                                    long timeTick = TXCTimeUtil.getTimeTick();
                                    if (i2 == 0) {
                                        if (timeTick > this.f5572e + 1000) {
                                            this.f5570c = (long) (((this.f5583p * 8000.0d) / (timeTick - j3)) / 1024.0d);
                                            j4 = 0;
                                            this.f5583p = 0L;
                                            this.f5572e = timeTick;
                                        } else {
                                            j4 = 0;
                                        }
                                        this.f5578k++;
                                        this.f5579l = j4;
                                    } else {
                                        this.f5579l++;
                                    }
                                    this.f5583p += m445a.length;
                                    if (timeTick > 2000 + this.f5573f) {
                                        this.f5571d = (long) ((this.f5584q * 1000.0d) / (timeTick - j));
                                        j2 = 0;
                                        this.f5584q = 0L;
                                        this.f5573f = timeTick;
                                    } else {
                                        j2 = 0;
                                    }
                                    this.f5584q++;
                                    byteBuffer.position(bufferInfo.offset);
                                    if (this.f5576i) {
                                        long j8 = this.f5578k;
                                        long j9 = this.f5579l;
                                        long j10 = this.f5580m;
                                        if (i2 != 0) {
                                            j2 = j9 - 1;
                                        }
                                        i3 = dequeueOutputBuffer;
                                        callDelegate(m445a, i2, j8, j9, j10, j2, j6, j6, 0, byteBuffer, bufferInfo);
                                    } else {
                                        i3 = dequeueOutputBuffer;
                                        long j11 = this.f5578k;
                                        long j12 = this.f5579l;
                                        callDelegate(bArr2, i2, j11, j12, this.f5580m, i2 == 0 ? j2 : j12 - 1, j6, j6, 0, byteBuffer, bufferInfo);
                                    }
                                    tXCHWVideoEncoder = this;
                                    tXCHWVideoEncoder.f5580m++;
                                    tXCHWVideoEncoder.f5567M++;
                                    if ((bufferInfo.flags & 4) != 0) {
                                        TXIVideoEncoderListener tXIVideoEncoderListener2 = tXCHWVideoEncoder.mListener;
                                        if (tXIVideoEncoderListener2 != null) {
                                            z = false;
                                            tXIVideoEncoderListener2.onEncodeNAL(null, 0);
                                        } else {
                                            z = false;
                                        }
                                        i4 = -2;
                                    } else {
                                        z = false;
                                        i4 = 1;
                                    }
                                    if (tXCHWVideoEncoder.f5585r != null) {
                                        tXCHWVideoEncoder.f5585r.releaseOutputBuffer(i3, z);
                                    }
                                    return i4;
                                }
                            }
                            z = false;
                            if (tXCHWVideoEncoder.f5585r != null) {
                            }
                            return i4;
                        }
                    }
                    bArr = bArr2;
                    if (bufferInfo.size != 0) {
                    }
                    z = false;
                    if (tXCHWVideoEncoder.f5585r != null) {
                    }
                    return i4;
                }
                tXCHWVideoEncoder = this;
                i3 = dequeueOutputBuffer;
                z = false;
                if (tXCHWVideoEncoder.f5585r != null) {
                }
                return i4;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* renamed from: a */
    private byte[] m445a(byte[] bArr) {
        int i;
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 20];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            if (bArr[i4] == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 1) {
                i3 = m453a(i4, i2, bArr2, bArr, i3);
                i4 += 3;
            } else {
                if (bArr[i4] == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 0 && bArr[i4 + 3] == 1) {
                    i3 = m453a(i4, i2, bArr2, bArr, i3);
                    i4 += 4;
                }
                if (i4 != length - 4 && (bArr[i4 + 1] != 0 || bArr[i4 + 2] != 0 || bArr[i4 + 3] != 1)) {
                    i = length;
                    break;
                }
                i4++;
            }
            i2 = i4;
            if (i4 != length - 4) {
            }
            i4++;
        }
        i = i4;
        int m453a = m453a(i, i2, bArr2, bArr, i3);
        byte[] bArr3 = new byte[m453a];
        System.arraycopy(bArr2, 0, bArr3, 0, m453a);
        return bArr3;
    }

    /* renamed from: a */
    private int m453a(int i, int i2, byte[] bArr, byte[] bArr2, int i3) {
        if (i2 <= 0 || i <= i2) {
            return i3;
        }
        int i4 = i - i2;
        try {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[4]);
            wrap.asIntBuffer().put(i4);
            wrap.order(ByteOrder.BIG_ENDIAN);
            System.arraycopy(wrap.array(), 0, bArr, i3, 4);
            System.arraycopy(bArr2, i2, bArr, i3 + 4, i4);
            return i3 + i4 + 4;
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.m2914e(f5554a, "setNalData exception");
            return i3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(18)
    /* renamed from: b */
    public void m443b(int i) {
        int m456a;
        if (this.f5593z || this.f5591x == null) {
            return;
        }
        m452a(this.f5559E);
        this.mEncodeFilter.m3025b(this.f5568N);
        Object obj = this.f5591x;
        if (obj instanceof EGL14Helper) {
            ((EGL14Helper) obj).m3077a(this.f5559E * 1000000);
            ((EGL14Helper) this.f5591x).m3073c();
        }
        Object obj2 = this.f5591x;
        if (obj2 instanceof EGL10Helper) {
            ((EGL10Helper) obj2).m3084a();
        }
        do {
            m456a = m456a(i);
        } while (m456a > 0);
        if (m456a == -1 || m456a == -2) {
            if (m456a == -1) {
                callDelegate(10000005);
            }
            this.f5593z = true;
            m440c();
            return;
        }
        this.f5566L++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0019 -> B:9:0x0028). Please submit an issue!!! */
    /* renamed from: c */
    public void m440c() {
        if (!this.mInit) {
            return;
        }
        this.f5593z = true;
        this.f5555A = true;
        m444b();
        try {
            try {
                try {
                    this.f5585r.stop();
                    this.f5585r.release();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    this.f5585r.release();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.f5585r = null;
            this.f5568N = -1;
            this.f5570c = 0L;
            this.f5571d = 0L;
            this.f5572e = 0L;
            this.f5573f = 0L;
            this.f5574g = 0;
            this.f5577j = 0L;
            this.f5578k = 0L;
            this.f5579l = 0L;
            this.f5580m = 0L;
            this.f5581n = 0L;
            this.f5583p = 0L;
            this.f5584q = 0L;
            this.mGLContextExternal = null;
            this.f5557C = null;
            this.f5558D = null;
            this.f5559E = 0L;
            this.mOutputWidth = 0;
            this.mOutputHeight = 0;
            this.mInit = false;
            this.mListener = null;
            this.f5590w.clear();
        } catch (Throwable th) {
            try {
                this.f5585r.release();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m439c(int i) {
        long j = this.f5577j;
        int i2 = this.f5569b;
        if (j == i2) {
            return;
        }
        this.f5577j = i2;
        if (Build.VERSION.SDK_INT < 19 || this.f5585r == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("video-bitrate", this.f5569b * 1024);
        this.f5585r.setParameters(bundle);
    }

    /* renamed from: d */
    private void m437d() {
        if (Build.VERSION.SDK_INT < 19 || this.f5585r == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("request-sync", 0);
        this.f5585r.setParameters(bundle);
    }
}
