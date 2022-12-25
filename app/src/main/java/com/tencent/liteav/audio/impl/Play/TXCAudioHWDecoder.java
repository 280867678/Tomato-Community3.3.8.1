package com.tencent.liteav.audio.impl.Play;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioPlayListener;
import com.tencent.liteav.audio.impl.TXCAudioUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/* renamed from: com.tencent.liteav.audio.impl.Play.a */
/* loaded from: classes3.dex */
public class TXCAudioHWDecoder implements Runnable {

    /* renamed from: a */
    private static final String f2064a = "AudioCenter:" + TXCAudioHWDecoder.class.getSimpleName();

    /* renamed from: d */
    private MediaCodec.BufferInfo f2067d;

    /* renamed from: e */
    private MediaFormat f2068e;

    /* renamed from: h */
    private Vector<TXSAudioPacket> f2071h;

    /* renamed from: i */
    private List f2072i;

    /* renamed from: b */
    private WeakReference<TXIAudioPlayListener> f2065b = null;

    /* renamed from: c */
    private MediaCodec f2066c = null;

    /* renamed from: f */
    private long f2069f = 0;

    /* renamed from: g */
    private volatile boolean f2070g = false;

    /* renamed from: j */
    private Thread f2073j = null;

    /* renamed from: a */
    public void m3433a(WeakReference<TXIAudioPlayListener> weakReference) {
        if (this.f2070g) {
            m3432b();
        }
        this.f2065b = weakReference;
        this.f2069f = 0L;
        this.f2071h = new Vector<>();
        this.f2072i = new ArrayList();
        this.f2070g = true;
        this.f2073j = new Thread(this);
        this.f2073j.setName(f2064a);
        this.f2073j.start();
    }

    /* renamed from: a */
    public void m3435a(TXSAudioPacket tXSAudioPacket) {
        if (!this.f2070g) {
            return;
        }
        synchronized (this.f2071h) {
            if (this.f2071h != null) {
                this.f2071h.add(tXSAudioPacket);
            }
        }
    }

    /* renamed from: a */
    public long m3436a() {
        float integer;
        MediaFormat mediaFormat = this.f2068e;
        if (mediaFormat != null) {
            if (mediaFormat.getInteger("sample-rate") != 0.0f) {
                return ((this.f2072i.size() * 1024.0f) * 1000.0f) / integer;
            }
        }
        return 0L;
    }

    /* renamed from: b */
    public void m3432b() {
        this.f2070g = false;
        Thread thread = this.f2073j;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.f2073j = null;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean isEmpty;
        int i;
        TXSAudioPacket remove;
        while (true) {
            ByteBuffer[] byteBufferArr = null;
            if (this.f2070g) {
                synchronized (this.f2071h) {
                    isEmpty = this.f2071h.isEmpty();
                }
                if (isEmpty) {
                    try {
                        Thread.sleep(10L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    int i2 = -1;
                    MediaCodec mediaCodec = this.f2066c;
                    if (mediaCodec != null) {
                        try {
                            byteBufferArr = mediaCodec.getInputBuffers();
                            i = 1;
                        } catch (Exception e2) {
                            e = e2;
                            i = 0;
                        }
                        try {
                            i2 = this.f2066c.dequeueInputBuffer(10000L);
                            if (i2 < 0) {
                                return;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            String str = f2064a;
                            TXCLog.m2914e(str, "Exception. step: " + i + ", error: " + e);
                            return;
                        }
                    }
                    synchronized (this.f2071h) {
                        remove = this.f2071h.remove(0);
                    }
                    int i3 = remove.packetType;
                    if (i3 == TXEAudioTypeDef.f2324k) {
                        m3431b(remove);
                    } else if (i3 == TXEAudioTypeDef.f2325l) {
                        this.f2072i.add(new Long(remove.timestamp));
                        m3434a(remove, byteBufferArr, i2);
                    } else {
                        TXCLog.m2914e(f2064a, "not support audio format");
                    }
                }
            } else {
                MediaCodec mediaCodec2 = this.f2066c;
                if (mediaCodec2 == null) {
                    return;
                }
                mediaCodec2.stop();
                this.f2066c.release();
                this.f2066c = null;
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x017b  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int m3431b(TXSAudioPacket tXSAudioPacket) {
        int i;
        MediaCodec mediaCodec;
        int i2;
        int i3;
        if (tXSAudioPacket.audioData.length != 2) {
            String str = f2064a;
            TXCLog.m2911w(str, "aac seq header len not equal to 2 , with len " + tXSAudioPacket.audioData.length);
        }
        byte[] bArr = tXSAudioPacket.audioData;
        if (bArr != null) {
            byte b = bArr[0];
            int m3350a = TXCAudioUtil.m3350a(((bArr[1] & 128) >> 7) | ((bArr[0] & 7) << 1));
            int i4 = (tXSAudioPacket.audioData[1] & 120) >> 3;
            this.f2068e = MediaFormat.createAudioFormat("audio/mp4a-latm", m3350a, i4);
            this.f2068e.setInteger("bitrate", 64000);
            this.f2068e.setInteger("is-adts", 0);
            this.f2068e.setInteger("aac-profile", 2);
            String str2 = f2064a;
            TXCLog.m2913i(str2, "audio decoder media format: " + this.f2068e);
            WeakReference<TXIAudioPlayListener> weakReference = this.f2065b;
            if (weakReference != null) {
                TXIAudioPlayListener tXIAudioPlayListener = weakReference.get();
                TXSAudioPacket tXSAudioPacket2 = new TXSAudioPacket();
                tXSAudioPacket2.bitsPerChannel = TXEAudioTypeDef.f2321h;
                tXSAudioPacket2.channelsPerSample = i4;
                tXSAudioPacket2.sampleRate = m3350a;
                if (tXIAudioPlayListener != null) {
                    tXIAudioPlayListener.onPlayAudioInfoChanged(tXSAudioPacket2, tXSAudioPacket2);
                }
            }
            MediaCodec mediaCodec2 = this.f2066c;
            if (mediaCodec2 != null) {
                try {
                    mediaCodec2.stop();
                } catch (Exception e) {
                    e = e;
                    i3 = 0;
                }
                try {
                    this.f2066c.release();
                } catch (Exception e2) {
                    e = e2;
                    i3 = 1;
                    String str3 = f2064a;
                    TXCLog.m2914e(str3, "hw audio decoder release error: " + i3 + ". error: " + e);
                    this.f2066c = MediaCodec.createDecoderByType("audio/mp4a-latm");
                    if (Build.VERSION.SDK_INT < 21) {
                    }
                    mediaCodec = this.f2066c;
                    if (mediaCodec != null) {
                    }
                    return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
                }
            }
            try {
                this.f2066c = MediaCodec.createDecoderByType("audio/mp4a-latm");
            } catch (IOException e3) {
                e3.printStackTrace();
                String str4 = f2064a;
                TXCLog.m2914e(str4, "createDecoderByType exception: " + e3.getMessage());
            }
            if (Build.VERSION.SDK_INT < 21) {
                int i5 = 0;
                while (true) {
                    try {
                        this.f2066c.configure(this.f2068e, (Surface) null, (MediaCrypto) null, 0);
                        try {
                            this.f2066c.start();
                            break;
                        } catch (MediaCodec.CodecException e4) {
                            e = e4;
                            i2 = 1;
                            String str5 = f2064a;
                            TXCLog.m2914e(str5, "CodecException: " + e + ". step: " + i2 + ", mediaformat: " + this.f2068e);
                            i5++;
                            if (i5 > 1) {
                                TXCLog.m2914e(f2064a, "decoder start error!");
                                this.f2066c.release();
                                this.f2066c = null;
                                return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
                            } else if (e.isRecoverable()) {
                                try {
                                    this.f2066c.stop();
                                } catch (Exception unused) {
                                }
                            } else if (e.isTransient()) {
                                try {
                                    Thread.sleep(20L);
                                } catch (InterruptedException e5) {
                                    e5.printStackTrace();
                                }
                            } else {
                                TXCLog.m2914e(f2064a, "decoder cath unrecoverable error!");
                                this.f2066c.release();
                                this.f2066c = null;
                                return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
                            }
                        }
                    } catch (MediaCodec.CodecException e6) {
                        e = e6;
                        i2 = 0;
                    }
                }
            } else {
                int i6 = 0;
                while (true) {
                    try {
                        this.f2066c.configure(this.f2068e, (Surface) null, (MediaCrypto) null, 0);
                        try {
                            this.f2066c.start();
                            break;
                        } catch (IllegalStateException e7) {
                            e = e7;
                            i = 1;
                            String str6 = f2064a;
                            TXCLog.m2914e(str6, "CodecException1: " + e + ". step: " + i);
                            i6++;
                            if (i6 > 1) {
                                TXCLog.m2914e(f2064a, "decoder start error!");
                                this.f2066c.release();
                                this.f2066c = null;
                                return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
                            }
                            try {
                                this.f2066c.reset();
                            } catch (Exception unused2) {
                            }
                        }
                    } catch (IllegalStateException e8) {
                        e = e8;
                        i = 0;
                    }
                }
            }
            mediaCodec = this.f2066c;
            if (mediaCodec != null) {
                m3434a(tXSAudioPacket, mediaCodec.getInputBuffers(), this.f2066c.dequeueInputBuffer(10000L));
            }
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
    }

    /* renamed from: a */
    private int m3434a(TXSAudioPacket tXSAudioPacket, ByteBuffer[] byteBufferArr, int i) {
        int dequeueOutputBuffer;
        TXIAudioPlayListener tXIAudioPlayListener;
        if (i >= 0) {
            try {
                if (tXSAudioPacket.audioData != null) {
                    ByteBuffer byteBuffer = byteBufferArr[i];
                    byteBuffer.clear();
                    byteBuffer.put(tXSAudioPacket.audioData);
                }
                if (tXSAudioPacket != null && tXSAudioPacket.audioData.length > 0) {
                    this.f2066c.queueInputBuffer(i, 0, tXSAudioPacket.audioData.length, m3430c(), 0);
                } else {
                    this.f2066c.queueInputBuffer(i, 0, 0, m3430c(), 4);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i == -1) {
            return -1;
        }
        ByteBuffer[] outputBuffers = this.f2066c.getOutputBuffers();
        if (this.f2067d == null) {
            this.f2067d = new MediaCodec.BufferInfo();
        }
        do {
            dequeueOutputBuffer = this.f2066c.dequeueOutputBuffer(this.f2067d, 10000L);
            if (dequeueOutputBuffer == -3) {
                outputBuffers = this.f2066c.getOutputBuffers();
                continue;
            } else if (dequeueOutputBuffer == -2) {
                this.f2068e = this.f2066c.getOutputFormat();
                if (this.f2065b != null) {
                    TXIAudioPlayListener tXIAudioPlayListener2 = this.f2065b.get();
                    TXSAudioPacket tXSAudioPacket2 = new TXSAudioPacket();
                    tXSAudioPacket2.bitsPerChannel = TXEAudioTypeDef.f2321h;
                    tXSAudioPacket2.channelsPerSample = this.f2068e.getInteger("channel-count");
                    tXSAudioPacket2.sampleRate = this.f2068e.getInteger("sample-rate");
                    if (tXIAudioPlayListener2 != null) {
                        tXIAudioPlayListener2.onPlayAudioInfoChanged(tXSAudioPacket2, tXSAudioPacket2);
                        continue;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else if (dequeueOutputBuffer >= 0) {
                ByteBuffer byteBuffer2 = outputBuffers[dequeueOutputBuffer];
                byteBuffer2.position(this.f2067d.offset);
                byteBuffer2.limit(this.f2067d.offset + this.f2067d.size);
                byte[] bArr = new byte[this.f2067d.size];
                byteBuffer2.get(bArr);
                long longValue = ((Long) this.f2072i.get(0)).longValue();
                this.f2072i.remove(0);
                if (this.f2065b != null && (tXIAudioPlayListener = this.f2065b.get()) != null) {
                    tXIAudioPlayListener.onPlayPcmData(bArr, longValue);
                }
                this.f2066c.releaseOutputBuffer(dequeueOutputBuffer, false);
                continue;
            } else {
                continue;
            }
        } while (dequeueOutputBuffer >= 0);
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    /* renamed from: c */
    private long m3430c() {
        long timeTick = TXCTimeUtil.getTimeTick();
        long j = this.f2069f;
        if (timeTick < j) {
            timeTick += j - timeTick;
        }
        this.f2069f = timeTick;
        return timeTick;
    }
}
