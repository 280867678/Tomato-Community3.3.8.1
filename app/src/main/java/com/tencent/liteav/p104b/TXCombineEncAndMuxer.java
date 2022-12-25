package com.tencent.liteav.p104b;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.p104b.TXCombine;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p120e.AudioMediaCodecEncoder;
import com.tencent.liteav.p120e.IAudioDecodeCallback;
import com.tencent.liteav.p120e.TXHAudioEncoderParam;
import com.tencent.liteav.p120e.TXIAudioEncoderListener;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.tencent.liteav.b.d */
/* loaded from: classes3.dex */
public class TXCombineEncAndMuxer {

    /* renamed from: b */
    protected TXCVideoEncoder f2222b;

    /* renamed from: c */
    protected TXCMP4Muxer f2223c;

    /* renamed from: e */
    private Context f2225e;

    /* renamed from: f */
    private AudioMediaCodecEncoder f2226f;

    /* renamed from: k */
    private String f2231k;

    /* renamed from: m */
    private long f2233m;

    /* renamed from: o */
    private int f2235o;

    /* renamed from: p */
    private int f2236p;

    /* renamed from: r */
    private boolean f2238r;

    /* renamed from: s */
    private boolean f2239s;

    /* renamed from: t */
    private TXCombine.AbstractC3313b f2240t;

    /* renamed from: d */
    private final String f2224d = "TXCombineEncAndMuxer";

    /* renamed from: a */
    protected boolean f2221a = false;

    /* renamed from: g */
    private int f2227g = 1;

    /* renamed from: h */
    private int f2228h = 98304;

    /* renamed from: i */
    private int f2229i = ConstantUtils.MAX_ITEM_NUM;

    /* renamed from: j */
    private int f2230j = TXRecordCommon.AUDIO_SAMPLERATE_48000;

    /* renamed from: l */
    private boolean f2232l = false;

    /* renamed from: n */
    private int f2234n = 13000;

    /* renamed from: u */
    private IAudioDecodeCallback f2241u = new IAudioDecodeCallback() { // from class: com.tencent.liteav.b.d.1
        @Override // com.tencent.liteav.p120e.IAudioDecodeCallback
        /* renamed from: a */
        public void mo1531a(int i) {
        }
    };

    /* renamed from: v */
    private TXIVideoEncoderListener f2242v = new TXIVideoEncoderListener() { // from class: com.tencent.liteav.b.d.2
        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
            if (i != 0) {
                TXCLog.m2913i("TXCombineEncAndMuxer", "mVideoEncodeListener, errCode = " + i);
            } else if (tXSNALPacket == null || tXSNALPacket.nalData == null) {
                TXCLog.m2913i("TXCombineEncAndMuxer", "===Video onEncodeComplete===");
                TXCombineEncAndMuxer.this.f2238r = true;
                if (!TXCombineEncAndMuxer.this.f2239s) {
                    return;
                }
                TXCLog.m2913i("TXCombineEncAndMuxer", "===Video onEncodeComplete=== mAudioEncEnd is true");
                TXCombineEncAndMuxer.this.m3277a();
                TXCombineEncAndMuxer.this.m3265b();
            } else {
                Frame frame = null;
                try {
                    TXCLog.m2913i("TXCombineEncAndMuxer", "onEncodeNAL, before take mVideoQueue size = " + TXCombineEncAndMuxer.this.f2237q.size());
                    frame = (Frame) TXCombineEncAndMuxer.this.f2237q.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    if (TXCombineEncAndMuxer.this.f2223c != null && tXSNALPacket != null && tXSNALPacket.nalData != null) {
                        if (TXCombineEncAndMuxer.this.f2232l) {
                            m3252a(tXSNALPacket, frame);
                        } else if (tXSNALPacket.nalType == 0) {
                            MediaFormat m2882a = TXCSystemUtil.m2882a(tXSNALPacket.nalData, TXCombineEncAndMuxer.this.f2235o, TXCombineEncAndMuxer.this.f2236p);
                            if (m2882a != null) {
                                TXCombineEncAndMuxer.this.f2223c.mo1232a(m2882a);
                                TXCombineEncAndMuxer.this.f2223c.mo1235a();
                                TXCombineEncAndMuxer.this.f2232l = true;
                            }
                            m3252a(tXSNALPacket, frame);
                        }
                    }
                }
                TXCombineEncAndMuxer.this.m3256d(frame);
            }
        }

        /* renamed from: a */
        private void m3252a(TXSNALPacket tXSNALPacket, Frame frame) {
            int i;
            long m2329e = frame.m2329e();
            TXCLog.m2913i("TXCombineEncAndMuxer", "Muxer writeVideoData :" + m2329e);
            MediaCodec.BufferInfo bufferInfo = tXSNALPacket.info;
            if (bufferInfo == null) {
                i = tXSNALPacket.nalType == 0 ? 1 : 0;
            } else {
                i = bufferInfo.flags;
            }
            TXCMP4Muxer tXCMP4Muxer = TXCombineEncAndMuxer.this.f2223c;
            if (tXCMP4Muxer != null) {
                byte[] bArr = tXSNALPacket.nalData;
                tXCMP4Muxer.mo1224b(bArr, 0, bArr.length, m2329e, i);
            }
        }

        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeFormat(MediaFormat mediaFormat) {
            TXCLog.m2913i("TXCombineEncAndMuxer", "Video onEncodeFormat format:" + mediaFormat);
            TXCMP4Muxer tXCMP4Muxer = TXCombineEncAndMuxer.this.f2223c;
            if (tXCMP4Muxer != null) {
                tXCMP4Muxer.mo1232a(mediaFormat);
                if (!TXCombineEncAndMuxer.this.f2223c.mo1221d()) {
                    return;
                }
                TXCLog.m2913i("TXCombineEncAndMuxer", "Has Audio, Video Muxer start");
                TXCombineEncAndMuxer.this.f2223c.mo1235a();
                TXCombineEncAndMuxer.this.f2232l = true;
            }
        }
    };

    /* renamed from: w */
    private TXIAudioEncoderListener f2243w = new TXIAudioEncoderListener() { // from class: com.tencent.liteav.b.d.3
        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1528a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            TXCLog.m2913i("TXCombineEncAndMuxer", "Audio onEncodeAAC");
            if (TXCombineEncAndMuxer.this.f2223c != null) {
                TXCLog.m2913i("TXCombineEncAndMuxer", "Muxer writeAudioData :" + bufferInfo.presentationTimeUs);
                TXCombineEncAndMuxer.this.f2223c.mo1230a(byteBuffer, bufferInfo);
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1529a(MediaFormat mediaFormat) {
            TXCLog.m2913i("TXCombineEncAndMuxer", "Audio onEncodeFormat format:" + mediaFormat);
            TXCMP4Muxer tXCMP4Muxer = TXCombineEncAndMuxer.this.f2223c;
            if (tXCMP4Muxer != null) {
                tXCMP4Muxer.mo1226b(mediaFormat);
                if (!TXCombineEncAndMuxer.this.f2223c.mo1223c()) {
                    return;
                }
                TXCombineEncAndMuxer.this.f2223c.mo1235a();
                TXCombineEncAndMuxer.this.f2232l = true;
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1530a() {
            TXCLog.m2913i("TXCombineEncAndMuxer", "===Audio onEncodeComplete===");
            TXCombineEncAndMuxer.this.f2239s = true;
            if (TXCombineEncAndMuxer.this.f2238r) {
                TXCLog.m2913i("TXCombineEncAndMuxer", "===Audio onEncodeComplete=== mVideoEncEnd is true");
                TXCombineEncAndMuxer.this.m3277a();
                TXCombineEncAndMuxer.this.m3265b();
            }
        }
    };

    /* renamed from: q */
    private LinkedBlockingQueue<Frame> f2237q = new LinkedBlockingQueue<>();

    public TXCombineEncAndMuxer(Context context) {
        this.f2225e = context;
    }

    /* renamed from: a */
    public void m3271a(TXCombine.AbstractC3313b abstractC3313b) {
        this.f2240t = abstractC3313b;
    }

    /* renamed from: a */
    public void m3275a(int i, int i2) {
        this.f2235o = i;
        this.f2236p = i2;
        int i3 = 0;
        this.f2238r = false;
        this.f2239s = false;
        int i4 = 1;
        this.f2221a = this.f2235o < 1280 && this.f2236p < 1280;
        if (this.f2221a) {
            i4 = 2;
        }
        this.f2222b = new TXCVideoEncoder(i4);
        Context context = this.f2225e;
        if (!this.f2221a) {
            i3 = 2;
        }
        this.f2223c = new TXCMP4Muxer(context, i3);
        if (!TextUtils.isEmpty(this.f2231k)) {
            this.f2223c.mo1231a(this.f2231k);
        }
    }

    /* renamed from: a */
    public void m3266a(String str) {
        this.f2231k = str;
        TXCMP4Muxer tXCMP4Muxer = this.f2223c;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1231a(this.f2231k);
        }
    }

    /* renamed from: a */
    public void m3273a(long j) {
        this.f2233m = j;
    }

    /* renamed from: a */
    public void m3276a(int i) {
        this.f2229i = i;
    }

    /* renamed from: b */
    public void m3264b(int i) {
        this.f2230j = i;
    }

    /* renamed from: a */
    public void m3272a(EGLContext eGLContext) {
        TXCLog.m2915d("TXCombineEncAndMuxer", "OnContextListener onContext");
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        tXSVideoEncoderParam.width = this.f2235o;
        tXSVideoEncoderParam.height = this.f2236p;
        tXSVideoEncoderParam.fps = 20;
        tXSVideoEncoderParam.glContext = eGLContext;
        tXSVideoEncoderParam.enableEGL14 = true;
        tXSVideoEncoderParam.enableBlackList = false;
        tXSVideoEncoderParam.appendSpsPps = false;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.fullIFrame = false;
        tXSVideoEncoderParam.gop = 3;
        if (this.f2221a) {
            tXSVideoEncoderParam.encoderMode = 1;
            tXSVideoEncoderParam.encoderProfile = 3;
            tXSVideoEncoderParam.record = true;
        } else {
            tXSVideoEncoderParam.encoderMode = 3;
            tXSVideoEncoderParam.encoderProfile = 1;
        }
        this.f2222b.m433a(this.f2234n);
        this.f2222b.m419a(this.f2242v);
        this.f2222b.m427a(tXSVideoEncoderParam);
        this.f2226f = new AudioMediaCodecEncoder();
        this.f2226f.m2286a(this.f2241u);
        this.f2226f.m2284a(this.f2243w);
        TXHAudioEncoderParam tXHAudioEncoderParam = new TXHAudioEncoderParam();
        tXHAudioEncoderParam.channelCount = this.f2227g;
        tXHAudioEncoderParam.sampleRate = this.f2230j;
        tXHAudioEncoderParam.maxInputSize = this.f2229i;
        tXHAudioEncoderParam.audioBitrate = this.f2228h;
        TXCLog.m2913i("TXCombineEncAndMuxer", "AudioEncoder.start");
        this.f2226f.m2285a(tXHAudioEncoderParam);
    }

    /* renamed from: a */
    public void m3274a(int i, int i2, int i3, Frame frame) {
        if (this.f2222b != null) {
            this.f2237q.add(frame);
            this.f2222b.m414b(i, i2, i3, frame.m2329e() / 1000);
        }
    }

    /* renamed from: a */
    public void m3267a(Frame frame) {
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f2226f;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2290a(frame);
        }
    }

    /* renamed from: b */
    public void m3261b(Frame frame) {
        this.f2237q.add(frame);
        this.f2222b.m415b();
    }

    /* renamed from: c */
    public void m3258c(Frame frame) {
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f2226f;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2290a(frame);
        }
    }

    /* renamed from: a */
    public void m3277a() {
        TXCLog.m2913i("TXCombineEncAndMuxer", "stopEncAndMuxer()");
        TXCVideoEncoder tXCVideoEncoder = this.f2222b;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m434a();
            this.f2222b = null;
        }
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f2226f;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2292a();
            this.f2226f = null;
        }
        TXCMP4Muxer tXCMP4Muxer = this.f2223c;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1227b();
            this.f2223c = null;
            this.f2232l = false;
        }
        this.f2231k = null;
        this.f2237q.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m3256d(Frame frame) {
        if (this.f2240t != null) {
            this.f2240t.mo3232a((((float) frame.m2329e()) * 1.0f) / ((float) this.f2233m));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3265b() {
        TXCombine.AbstractC3313b abstractC3313b = this.f2240t;
        if (abstractC3313b != null) {
            abstractC3313b.mo3231a(0, "");
        }
    }
}
