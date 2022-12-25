package com.tencent.liteav.videodecoder;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.iceteck.silicompressorr.videocompression.MediaController;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* renamed from: com.tencent.liteav.videodecoder.c */
/* loaded from: classes3.dex */
public class TXCVideoMediaCodecDecoder implements IVideoDecoder {

    /* renamed from: a */
    private MediaCodec.BufferInfo f5429a = new MediaCodec.BufferInfo();

    /* renamed from: b */
    private MediaCodec f5430b = null;

    /* renamed from: c */
    private String f5431c = MediaController.MIME_TYPE;

    /* renamed from: d */
    private int f5432d = 540;

    /* renamed from: e */
    private int f5433e = 960;

    /* renamed from: f */
    private long f5434f = 0;

    /* renamed from: g */
    private long f5435g = 0;

    /* renamed from: h */
    private boolean f5436h = true;

    /* renamed from: i */
    private boolean f5437i = false;

    /* renamed from: j */
    private boolean f5438j = false;

    /* renamed from: k */
    private Surface f5439k = null;

    /* renamed from: l */
    private int f5440l = 0;

    /* renamed from: m */
    private ArrayList<TXSNALPacket> f5441m = new ArrayList<>();

    /* renamed from: n */
    private TXIVideoDecoderListener f5442n;

    /* renamed from: o */
    private WeakReference<TXINotifyListener> f5443o;

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void setListener(TXIVideoDecoderListener tXIVideoDecoderListener) {
        this.f5442n = tXIVideoDecoderListener;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void setNotifyListener(WeakReference<TXINotifyListener> weakReference) {
        this.f5443o = weakReference;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public int config(Surface surface) {
        if (surface == null) {
            return -1;
        }
        this.f5439k = surface;
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void decode(TXSNALPacket tXSNALPacket) {
        boolean z = true;
        if (tXSNALPacket.codecId != 1) {
            z = false;
        }
        m597a(z);
        this.f5441m.add(tXSNALPacket);
        while (!this.f5441m.isEmpty()) {
            int size = this.f5441m.size();
            m596b();
            if (size == this.f5441m.size()) {
                return;
            }
        }
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, boolean z2) {
        return m598a(byteBuffer, byteBuffer2, z2);
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void stop() {
        m600a();
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public boolean isHevc() {
        return this.f5438j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.media.MediaCodec, android.media.MediaCrypto] */
    /* renamed from: a */
    private int m598a(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        MediaCodec mediaCodec = 0;
        int i = 0;
        int i2 = -1;
        try {
            if (this.f5430b == null && this.f5439k != null) {
                this.f5438j = z;
                if (this.f5438j) {
                    this.f5431c = "video/hevc";
                } else {
                    this.f5431c = MediaController.MIME_TYPE;
                }
                MediaFormat createVideoFormat = MediaFormat.createVideoFormat(this.f5431c, this.f5432d, this.f5433e);
                if (byteBuffer != null) {
                    createVideoFormat.setByteBuffer("csd-0", byteBuffer);
                }
                if (byteBuffer2 != null) {
                    createVideoFormat.setByteBuffer("csd-1", byteBuffer2);
                }
                mediaCodec = MediaCodec.createDecoderByType(this.f5431c);
                try {
                    this.f5430b.configure(createVideoFormat, this.f5439k, (MediaCrypto) mediaCodec, 0);
                    try {
                        this.f5430b.setVideoScalingMode(1);
                        try {
                            this.f5430b.start();
                            try {
                                TXCLog.m2911w("MediaCodecDecoder", "decode: start decoder success, is hevc: " + this.f5438j);
                            } catch (Exception e) {
                                e = e;
                                i = 4;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            i = 3;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        i = 2;
                    }
                    try {
                        this.f5440l = 0;
                        return 0;
                    } catch (Exception e4) {
                        e = e4;
                        i = 4;
                        i2 = 0;
                        MediaCodec mediaCodec2 = this.f5430b;
                        if (mediaCodec2 != null) {
                            try {
                                try {
                                    mediaCodec2.release();
                                    TXCLog.m2911w("MediaCodecDecoder", "decode: , decoder release success");
                                } catch (Exception e5) {
                                    TXCLog.m2914e("MediaCodecDecoder", "decode: , decoder release exception: " + e.toString());
                                    e5.printStackTrace();
                                    TXCLog.m2914e("MediaCodecDecoder", "decode: init decoder " + i + " step exception: " + e.toString());
                                    e.printStackTrace();
                                    m592f();
                                    return i2;
                                }
                            } finally {
                                this.f5430b = mediaCodec;
                            }
                        }
                        TXCLog.m2914e("MediaCodecDecoder", "decode: init decoder " + i + " step exception: " + e.toString());
                        e.printStackTrace();
                        m592f();
                        return i2;
                    }
                } catch (Exception e6) {
                    e = e6;
                    i = 1;
                }
            }
            TXCLog.m2914e("MediaCodecDecoder", "decode: init decoder error, can not init for decoder=" + this.f5430b + ",surface=" + this.f5439k);
            return -1;
        } catch (Exception e7) {
            e = e7;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* renamed from: a */
    private void m600a() {
        String str = "decode: release decoder exception: ";
        String e = "decode: release decoder sucess";
        MediaCodec mediaCodec = this.f5430b;
        if (mediaCodec != null) {
            try {
                try {
                    try {
                        mediaCodec.stop();
                        TXCLog.m2911w("MediaCodecDecoder", "decode: stop decoder sucess");
                        try {
                            this.f5430b.release();
                            TXCLog.m2911w("MediaCodecDecoder", e);
                            e = e;
                        } catch (Exception e2) {
                            str = str + e2.toString();
                            TXCLog.m2914e("MediaCodecDecoder", str);
                            e2.printStackTrace();
                            e = e2;
                        }
                    } finally {
                    }
                } catch (Exception e3) {
                    TXCLog.m2914e("MediaCodecDecoder", "decode: stop decoder Exception: " + e3.toString());
                    e3.printStackTrace();
                    try {
                        try {
                            this.f5430b.release();
                            TXCLog.m2911w("MediaCodecDecoder", e);
                        } catch (Exception e4) {
                            e = e4;
                            str = str + e.toString();
                            TXCLog.m2914e("MediaCodecDecoder", str);
                            e.printStackTrace();
                        }
                    } finally {
                    }
                }
                this.f5441m.clear();
                this.f5434f = 0L;
                this.f5436h = true;
            } catch (Throwable th) {
                try {
                    try {
                        this.f5430b.release();
                        TXCLog.m2911w("MediaCodecDecoder", e);
                    } finally {
                    }
                } catch (Exception e5) {
                    TXCLog.m2914e("MediaCodecDecoder", str + e5.toString());
                    e5.printStackTrace();
                }
                throw th;
            }
        }
    }

    @TargetApi(16)
    /* renamed from: b */
    private void m596b() {
        int i;
        int i2;
        if (this.f5430b == null) {
            TXCLog.m2914e("MediaCodecDecoder", "null decoder");
            return;
        }
        TXSNALPacket tXSNALPacket = this.f5441m.get(0);
        if (tXSNALPacket == null || tXSNALPacket.nalData.length == 0) {
            TXCLog.m2914e("MediaCodecDecoder", "decode: empty buffer");
            this.f5441m.remove(0);
            return;
        }
        ByteBuffer[] inputBuffers = this.f5430b.getInputBuffers();
        if (inputBuffers == null || inputBuffers.length == 0) {
            TXCLog.m2914e("MediaCodecDecoder", "decode: getInputBuffers failed");
            return;
        }
        try {
            i = this.f5430b.dequeueInputBuffer(10000L);
        } catch (Exception e) {
            TXCLog.m2914e("MediaCodecDecoder", "decode: dequeueInputBuffer Exception!! " + e);
            i = -10000;
        }
        if (i >= 0) {
            inputBuffers[i].put(tXSNALPacket.nalData);
            this.f5430b.queueInputBuffer(i, 0, tXSNALPacket.nalData.length, tXSNALPacket.pts, 0);
            this.f5441m.remove(0);
            if (this.f5434f == 0) {
                TXCLog.m2911w("MediaCodecDecoder", "decode: input buffer available, dequeueInputBuffer index: " + i);
            }
        } else {
            TXCLog.m2911w("MediaCodecDecoder", "decode: input buffer not available, dequeueInputBuffer failed");
        }
        try {
            i2 = this.f5430b.dequeueOutputBuffer(this.f5429a, 10000L);
        } catch (Exception e2) {
            m591g();
            TXCLog.m2914e("MediaCodecDecoder", "decode: dequeueOutputBuffer exception!!" + e2);
            i2 = -10000;
        }
        if (i2 >= 0) {
            long j = this.f5429a.presentationTimeUs;
            m599a(i2, j, j);
            this.f5440l = 0;
        } else if (i2 == -1) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
            TXCLog.m2915d("MediaCodecDecoder", "decode: no output from decoder available when timeout");
            m591g();
        } else if (i2 == -3) {
            TXCLog.m2915d("MediaCodecDecoder", "decode: output buffers changed");
        } else if (i2 == -2) {
            m595c();
        } else {
            TXCLog.m2914e("MediaCodecDecoder", "decode: unexpected result from decoder.dequeueOutputBuffer: " + i2);
        }
    }

    /* renamed from: a */
    private void m599a(int i, long j, long j2) {
        this.f5430b.releaseOutputBuffer(i, true);
        if ((this.f5429a.flags & 4) != 0) {
            TXCLog.m2915d("MediaCodecDecoder", "output EOS");
        }
        try {
            if (this.f5442n != null) {
                this.f5442n.mo588a((SurfaceTexture) null, this.f5432d, this.f5433e, j, j2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        m594d();
    }

    /* renamed from: c */
    private void m595c() {
        int i;
        MediaFormat outputFormat = this.f5430b.getOutputFormat();
        TXCLog.m2915d("MediaCodecDecoder", "decode output format changed: " + outputFormat);
        int integer = outputFormat.getInteger("width");
        int integer2 = outputFormat.getInteger("height");
        int min = Math.min(Math.abs(outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1, integer);
        int min2 = Math.min(Math.abs(outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1, integer2);
        int i2 = this.f5432d;
        if (min != i2 || min2 != (i = this.f5433e)) {
            this.f5432d = min;
            this.f5433e = min2;
            try {
                if (this.f5442n != null) {
                    this.f5442n.mo590a(this.f5432d, this.f5433e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TXCLog.m2915d("MediaCodecDecoder", "decode: video size change to w:" + min + ",h:" + min2);
        } else if (!this.f5436h) {
        } else {
            this.f5436h = false;
            TXIVideoDecoderListener tXIVideoDecoderListener = this.f5442n;
            if (tXIVideoDecoderListener == null) {
                return;
            }
            tXIVideoDecoderListener.mo590a(i2, i);
        }
    }

    /* renamed from: d */
    private void m594d() {
        if (this.f5434f == 0) {
            TXCLog.m2911w("MediaCodecDecoder", "decode first frame sucess");
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.f5434f;
        if (j > 0 && currentTimeMillis > j + 1000) {
            long j2 = this.f5435g;
            if (currentTimeMillis > 2000 + j2 && j2 != 0) {
                TXCLog.m2914e("MediaCodecDecoder", "frame interval[" + (currentTimeMillis - this.f5434f) + "] > 1000");
                this.f5435g = currentTimeMillis;
            }
        }
        if (this.f5435g == 0) {
            this.f5435g = currentTimeMillis;
        }
        this.f5434f = currentTimeMillis;
    }

    /* renamed from: e */
    private boolean m593e() {
        MediaCodecInfo codecInfoAt;
        MediaCodecInfo[] codecInfos;
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                for (String str : mediaCodecInfo.getSupportedTypes()) {
                    if (str.contains("video/hevc")) {
                        TXCLog.m2914e("MediaCodecDecoder", "decode: video/hevc MediaCodecInfo: " + mediaCodecInfo.getName() + ",encoder:" + mediaCodecInfo.isEncoder());
                        return true;
                    }
                }
            }
            return false;
        }
        if (i >= 16) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i2 = 0; i2 < codecCount; i2++) {
                for (String str2 : MediaCodecList.getCodecInfoAt(i2).getSupportedTypes()) {
                    if (str2.contains("video/hevc")) {
                        TXCLog.m2914e("MediaCodecDecoder", "video/hevc MediaCodecInfo: " + codecInfoAt.getName() + ",encoder:" + codecInfoAt.isEncoder());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: f */
    private void m592f() {
        if (!this.f5437i) {
            TXCLog.m2911w("MediaCodecDecoder", "decode hw decode error, hevc: " + this.f5438j);
            if (this.f5438j) {
                TXCSystemUtil.m2885a(this.f5443o, (int) TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL, "h265解码失败");
            } else {
                TXCSystemUtil.m2885a(this.f5443o, (int) TXLiveConstants.PLAY_WARNING_HW_ACCELERATION_FAIL, "硬解启动失败，采用软解");
            }
            this.f5437i = true;
        }
    }

    /* renamed from: g */
    private void m591g() {
        int i = this.f5440l;
        if (i >= 40) {
            m592f();
            this.f5440l = 0;
            return;
        }
        this.f5440l = i + 1;
    }

    /* renamed from: a */
    private void m597a(boolean z) {
        if (this.f5438j != z) {
            this.f5438j = z;
            if (this.f5437i) {
                return;
            }
            if (this.f5438j && !m593e()) {
                m600a();
                m592f();
                return;
            }
            m600a();
            m598a((ByteBuffer) null, (ByteBuffer) null, this.f5438j);
        }
    }
}
