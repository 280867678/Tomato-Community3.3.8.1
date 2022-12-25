package com.tencent.liteav.p122g;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.p118c.VideoPreProcessConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.AudioMediaCodecEncoder;
import com.tencent.liteav.p120e.IAudioDecodeCallback;
import com.tencent.liteav.p120e.IAudioPreprocessListener;
import com.tencent.liteav.p120e.IVideoProcessorListener;
import com.tencent.liteav.p120e.MoovHeaderProcessor;
import com.tencent.liteav.p120e.OnContextListener;
import com.tencent.liteav.p120e.TXHAudioEncoderParam;
import com.tencent.liteav.p120e.TXIAudioEncoderListener;
import com.tencent.liteav.p121f.AudioPreprocessChain;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoJoiner;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.tencent.liteav.g.p */
/* loaded from: classes3.dex */
public class VideoJoinGenerate {

    /* renamed from: b */
    private Context f4204b;

    /* renamed from: f */
    private VideoJoinPreprocessChain f4208f;

    /* renamed from: i */
    private AudioPreprocessChain f4211i;

    /* renamed from: j */
    private TXCVideoJoiner.AbstractC3527a f4212j;

    /* renamed from: k */
    private TXCVideoEncoder f4213k;

    /* renamed from: l */
    private AudioMediaCodecEncoder f4214l;

    /* renamed from: m */
    private TXCMP4Muxer f4215m;

    /* renamed from: n */
    private boolean f4216n;

    /* renamed from: o */
    private VideoExtractListConfig f4217o;

    /* renamed from: p */
    private OnContextListener f4218p = new OnContextListener() { // from class: com.tencent.liteav.g.p.1
        @Override // com.tencent.liteav.p120e.OnContextListener
        /* renamed from: a */
        public void mo1532a(EGLContext eGLContext) {
            TXCLog.m2915d("VideoJoinGenerate", "OnContextListener onContext");
            if (VideoJoinGenerate.this.f4213k != null) {
                TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
                tXSVideoEncoderParam.width = VideoJoinGenerate.this.f4210h.f3370h.f3467a;
                tXSVideoEncoderParam.height = VideoJoinGenerate.this.f4210h.f3370h.f3468b;
                tXSVideoEncoderParam.fps = VideoJoinGenerate.this.f4210h.m2435j();
                tXSVideoEncoderParam.glContext = eGLContext;
                tXSVideoEncoderParam.enableEGL14 = true;
                tXSVideoEncoderParam.enableBlackList = false;
                tXSVideoEncoderParam.appendSpsPps = false;
                tXSVideoEncoderParam.annexb = true;
                tXSVideoEncoderParam.fullIFrame = false;
                tXSVideoEncoderParam.gop = VideoJoinGenerate.this.f4210h.m2434k();
                if (VideoJoinGenerate.this.f4205c) {
                    tXSVideoEncoderParam.encoderMode = 1;
                    tXSVideoEncoderParam.encoderProfile = 3;
                    tXSVideoEncoderParam.record = true;
                } else {
                    tXSVideoEncoderParam.encoderMode = 3;
                    tXSVideoEncoderParam.encoderProfile = 1;
                }
                VideoJoinGenerate.this.f4213k.m433a(VideoJoinGenerate.this.f4210h.m2437i());
                VideoJoinGenerate.this.f4213k.m419a(VideoJoinGenerate.this.f4224v);
                VideoJoinGenerate.this.f4213k.m427a(tXSVideoEncoderParam);
            }
            VideoJoinGenerate.this.f4214l = new AudioMediaCodecEncoder();
            VideoJoinGenerate.this.f4214l.m2286a(VideoJoinGenerate.this.f4221s);
            VideoJoinGenerate.this.f4214l.m2284a(VideoJoinGenerate.this.f4223u);
            TXHAudioEncoderParam tXHAudioEncoderParam = new TXHAudioEncoderParam();
            tXHAudioEncoderParam.channelCount = VideoJoinGenerate.this.f4210h.f3364b;
            tXHAudioEncoderParam.sampleRate = VideoJoinGenerate.this.f4210h.f3363a;
            tXHAudioEncoderParam.maxInputSize = VideoJoinGenerate.this.f4210h.f3365c;
            tXHAudioEncoderParam.audioBitrate = VideoJoinGenerate.this.f4210h.m2439h();
            VideoJoinGenerate.this.f4214l.m2285a(tXHAudioEncoderParam);
            if (VideoJoinGenerate.this.f4206d != null) {
                VideoJoinGenerate.this.f4206d.m1666a(VideoJoinGenerate.this.f4217o);
                VideoJoinGenerate.this.f4206d.m1667a(VideoJoinGenerate.this.f4219q);
                VideoJoinGenerate.this.f4206d.m1668a(VideoJoinGenerate.this.f4220r);
                VideoJoinGenerate.this.f4206d.m1671a();
            }
        }
    };

    /* renamed from: q */
    private IVideoJoinDecoderListener f4219q = new IVideoJoinDecoderListener() { // from class: com.tencent.liteav.g.p.4
        @Override // com.tencent.liteav.p122g.IVideoJoinDecoderListener
        /* renamed from: a */
        public void mo1494a(Frame frame, VideoExtractConfig videoExtractConfig) {
            try {
                VideoJoinGenerate.this.f4203a.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (VideoJoinGenerate.this.f4207e != null) {
                VideoJoinGenerate.this.f4207e.m1606a(frame, videoExtractConfig);
            }
        }
    };

    /* renamed from: r */
    private IAudioJoinDecoderListener f4220r = new IAudioJoinDecoderListener() { // from class: com.tencent.liteav.g.p.5
        @Override // com.tencent.liteav.p122g.IAudioJoinDecoderListener
        /* renamed from: a */
        public void mo1493a(Frame frame, VideoExtractConfig videoExtractConfig) {
            if (VideoJoinGenerate.this.f4211i != null) {
                VideoJoinGenerate.this.f4211i.m1921a(frame);
            }
        }
    };

    /* renamed from: s */
    private IAudioDecodeCallback f4221s = new IAudioDecodeCallback() { // from class: com.tencent.liteav.g.p.6
        @Override // com.tencent.liteav.p120e.IAudioDecodeCallback
        /* renamed from: a */
        public void mo1531a(int i) {
            VideoJoinGenerate.this.f4206d.m1664a(i <= 5);
        }
    };

    /* renamed from: t */
    private IAudioPreprocessListener f4222t = new IAudioPreprocessListener() { // from class: com.tencent.liteav.g.p.7
        @Override // com.tencent.liteav.p120e.IAudioPreprocessListener
        /* renamed from: a */
        public void mo1486a(Frame frame) {
            if (frame == null) {
                return;
            }
            TXCLog.m2915d("VideoJoinGenerate", "didAudioProcessFrame frame:" + frame.m2329e());
            if (!frame.m2309p() || VideoJoinGenerate.this.f4213k == null) {
                if (VideoJoinGenerate.this.f4214l != null) {
                    VideoJoinGenerate.this.f4214l.m2290a(frame);
                }
                if (VideoJoinGenerate.this.f4211i == null) {
                    return;
                }
                VideoJoinGenerate.this.f4211i.m1890i();
                return;
            }
            VideoJoinGenerate.this.f4213k.m415b();
            TXCLog.m2915d("VideoJoinGenerate", "signalEOSAndFlush");
        }
    };

    /* renamed from: u */
    private TXIAudioEncoderListener f4223u = new TXIAudioEncoderListener() { // from class: com.tencent.liteav.g.p.8
        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1528a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            if (VideoJoinGenerate.this.f4215m != null) {
                VideoJoinGenerate.this.f4215m.mo1230a(byteBuffer, bufferInfo);
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1529a(MediaFormat mediaFormat) {
            TXCLog.m2913i("VideoJoinGenerate", "Audio onEncodeFormat format:" + mediaFormat);
            if (VideoJoinGenerate.this.f4215m != null) {
                VideoJoinGenerate.this.f4215m.mo1226b(mediaFormat);
                if (!VideoJoinGenerate.this.f4215m.mo1223c()) {
                    return;
                }
                VideoJoinGenerate.this.f4215m.mo1235a();
                VideoJoinGenerate.this.f4216n = true;
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1530a() {
            TXCLog.m2913i("VideoJoinGenerate", "===Audio onEncodeComplete===");
            VideoJoinGenerate.this.m1552b();
            VideoJoinGenerate.this.m1550c();
        }
    };

    /* renamed from: v */
    private TXIVideoEncoderListener f4224v = new TXIVideoEncoderListener() { // from class: com.tencent.liteav.g.p.9
        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
            if (i != 0) {
                TXCLog.m2913i("VideoJoinGenerate", "mVideoEncodeListener, errCode = " + i);
            } else if (tXSNALPacket != null && tXSNALPacket.nalData != null) {
                Frame frame = null;
                try {
                    frame = (Frame) VideoJoinGenerate.this.f4203a.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (frame.m2309p()) {
                    TXCLog.m2913i("VideoJoinGenerate", "frame.isEnd===Video onEncodeComplete===:" + frame.m2309p() + ", nal:" + tXSNALPacket);
                    VideoJoinGenerate.this.m1552b();
                    VideoJoinGenerate.this.m1550c();
                    return;
                }
                synchronized (this) {
                    if (VideoJoinGenerate.this.f4215m != null && tXSNALPacket != null && tXSNALPacket.nalData != null) {
                        if (VideoJoinGenerate.this.f4216n) {
                            m1527a(tXSNALPacket, frame);
                        } else if (tXSNALPacket.nalType == 0) {
                            MediaFormat m2882a = TXCSystemUtil.m2882a(tXSNALPacket.nalData, VideoJoinGenerate.this.f4210h.f3370h.f3467a, VideoJoinGenerate.this.f4210h.f3370h.f3468b);
                            if (m2882a != null) {
                                VideoJoinGenerate.this.f4215m.mo1232a(m2882a);
                                VideoJoinGenerate.this.f4215m.mo1235a();
                                VideoJoinGenerate.this.f4216n = true;
                            }
                            m1527a(tXSNALPacket, frame);
                        }
                    }
                }
                VideoJoinGenerate.this.m1558a(frame.m2329e());
            } else {
                TXCLog.m2913i("VideoJoinGenerate", "nal is null ===Video onEncodeComplete===");
                VideoJoinGenerate.this.m1552b();
                VideoJoinGenerate.this.m1550c();
            }
        }

        /* renamed from: a */
        private void m1527a(TXSNALPacket tXSNALPacket, Frame frame) {
            int i;
            MediaCodec.BufferInfo bufferInfo = tXSNALPacket.info;
            if (bufferInfo == null) {
                i = tXSNALPacket.nalType == 0 ? 1 : 0;
            } else {
                i = bufferInfo.flags;
            }
            if (VideoJoinGenerate.this.f4215m != null) {
                TXCMP4Muxer tXCMP4Muxer = VideoJoinGenerate.this.f4215m;
                byte[] bArr = tXSNALPacket.nalData;
                tXCMP4Muxer.mo1224b(bArr, 0, bArr.length, frame.m2329e(), i);
            }
        }

        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeFormat(MediaFormat mediaFormat) {
            TXCLog.m2913i("VideoJoinGenerate", "Video onEncodeFormat format:" + mediaFormat);
            if (VideoJoinGenerate.this.f4215m != null) {
                VideoJoinGenerate.this.f4215m.mo1232a(mediaFormat);
                if (!VideoJoinGenerate.this.f4215m.mo1221d()) {
                    return;
                }
                TXCLog.m2913i("VideoJoinGenerate", "Has Audio, Video Muxer start");
                VideoJoinGenerate.this.f4215m.mo1235a();
                VideoJoinGenerate.this.f4216n = true;
            }
        }
    };

    /* renamed from: w */
    private IVideoJoinRenderListener f4225w = new IVideoJoinRenderListener() { // from class: com.tencent.liteav.g.p.10
        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public void mo1490a(List<Surface> list) {
            TXCLog.m2913i("VideoJoinGenerate", "IVideoJoinRenderListener onSurfaceTextureAvailable");
            if (VideoJoinGenerate.this.f4208f != null) {
                VideoJoinGenerate.this.f4208f.m1526a();
                VideoJoinGenerate.this.f4208f.m1520b();
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public void mo1492a(int i, int i2) {
            if (VideoJoinGenerate.this.f4208f != null) {
                Resolution resolution = new Resolution();
                int m2419e = VideoPreProcessConfig.m2427a().m2419e();
                if (m2419e == 90 || m2419e == 270) {
                    resolution.f3467a = i2;
                    resolution.f3468b = i;
                } else {
                    resolution.f3467a = i;
                    resolution.f3468b = i2;
                }
                VideoJoinGenerate.this.f4208f.m1523a(resolution);
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: b */
        public void mo1489b(List<Surface> list) {
            TXCLog.m2913i("VideoJoinGenerate", "IVideoJoinRenderListener onSurfaceTextureDestroy");
            if (VideoJoinGenerate.this.f4208f != null) {
                VideoJoinGenerate.this.f4208f.m1518c();
                VideoJoinGenerate.this.f4208f.m1516d();
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public int mo1491a(int i, float[] fArr, Frame frame) {
            if (VideoJoinGenerate.this.f4208f != null) {
                VideoJoinGenerate.this.f4208f.m1521a(fArr);
                VideoJoinGenerate.this.f4208f.m1525a(i, frame);
                return 0;
            }
            return 0;
        }
    };

    /* renamed from: x */
    private IVideoProcessorListener f4226x = new IVideoProcessorListener() { // from class: com.tencent.liteav.g.p.11
        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: b */
        public int mo1487b(int i, Frame frame) {
            return i;
        }

        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: a */
        public void mo1488a(int i, Frame frame) {
            TXCLog.m2913i("VideoJoinGenerate", "didProcessFrame frame:" + frame.m2329e());
            if (!frame.m2309p() || VideoJoinGenerate.this.f4213k == null) {
                if (VideoJoinGenerate.this.f4213k != null) {
                    VideoJoinGenerate.this.f4213k.m414b(i, frame.m2313m(), frame.m2311n(), frame.m2329e() / 1000);
                }
                VideoJoinGenerate.this.f4206d.m1660c();
                return;
            }
            VideoJoinGenerate.this.f4213k.m415b();
            TXCLog.m2915d("VideoJoinGenerate", "signalEOSAndFlush");
        }
    };

    /* renamed from: y */
    private Handler f4227y = new Handler(Looper.getMainLooper());

    /* renamed from: a */
    private LinkedBlockingQueue<Frame> f4203a = new LinkedBlockingQueue<>();

    /* renamed from: e */
    private VideoJoinGLGenerate f4207e = new VideoJoinGLGenerate();

    /* renamed from: d */
    private VideoJoinDecAndDemuxGenerate f4206d = new VideoJoinDecAndDemuxGenerate();

    /* renamed from: g */
    private VideoSourceListConfig f4209g = VideoSourceListConfig.m1480a();

    /* renamed from: h */
    private VideoOutputListConfig f4210h = VideoOutputListConfig.m1481r();

    /* renamed from: c */
    private boolean f4205c = TXCSystemUtil.m2871g();

    public VideoJoinGenerate(Context context) {
        this.f4204b = context;
        this.f4208f = new VideoJoinPreprocessChain(context);
        this.f4208f.m1522a(this.f4226x);
    }

    /* renamed from: a */
    public void m1553a(TXCVideoJoiner.AbstractC3527a abstractC3527a) {
        this.f4212j = abstractC3527a;
    }

    /* renamed from: a */
    public void m1559a() {
        TXCLog.m2913i("VideoJoinGenerate", C2516Ad.TYPE_START);
        this.f4210h.m2441g();
        this.f4203a.clear();
        this.f4210h.f3373k = this.f4209g.m1467m();
        this.f4211i = new AudioPreprocessChain();
        this.f4211i.m1927a();
        this.f4211i.m1920a(this.f4222t);
        MediaFormat m1473g = this.f4209g.m1473g();
        if (m1473g != null) {
            this.f4210h.m1483d(m1473g);
            this.f4211i.m1922a(m1473g);
        }
        Resolution m1484a = this.f4210h.m1484a(this.f4209g.m1472h());
        this.f4210h.f3370h = m1484a;
        this.f4208f.m1523a(m1484a);
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        this.f4217o = new VideoExtractListConfig();
        this.f4217o.m1679a(m1477c);
        this.f4207e.m1602a(this.f4217o);
        this.f4207e.m1605a(m1484a);
        this.f4207e.m1604a(this.f4218p);
        this.f4207e.m1603a(this.f4225w);
        this.f4207e.m1607a();
        TXCLog.m2913i("VideoJoinGenerate", "mUseSWEncoder:" + this.f4205c);
        int i = 2;
        if (this.f4213k == null) {
            this.f4213k = new TXCVideoEncoder(this.f4205c ? 2 : 1);
        }
        Context context = this.f4204b;
        if (this.f4205c) {
            i = 0;
        }
        this.f4215m = new TXCMP4Muxer(context, i);
        this.f4215m.mo1231a(this.f4210h.f3371i);
    }

    /* renamed from: b */
    public void m1552b() {
        TXCLog.m2913i("VideoJoinGenerate", "stop");
        VideoJoinDecAndDemuxGenerate videoJoinDecAndDemuxGenerate = this.f4206d;
        if (videoJoinDecAndDemuxGenerate != null) {
            videoJoinDecAndDemuxGenerate.m1667a((IVideoJoinDecoderListener) null);
            this.f4206d.m1668a((IAudioJoinDecoderListener) null);
            this.f4206d.m1663b();
        }
        VideoJoinGLGenerate videoJoinGLGenerate = this.f4207e;
        if (videoJoinGLGenerate != null) {
            videoJoinGLGenerate.m1604a((OnContextListener) null);
            this.f4207e.m1603a((IVideoJoinRenderListener) null);
            this.f4207e.m1599b();
        }
        AudioPreprocessChain audioPreprocessChain = this.f4211i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1899d();
            this.f4211i.m1920a((IAudioPreprocessListener) null);
            this.f4211i.m1911b();
            this.f4211i = null;
        }
        TXCVideoEncoder tXCVideoEncoder = this.f4213k;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
            this.f4213k.m434a();
        }
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f4214l;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2284a((TXIAudioEncoderListener) null);
            this.f4214l.m2286a((IAudioDecodeCallback) null);
            this.f4214l.m2292a();
        }
        TXCLog.m2913i("VideoJoinGenerate", "stop muxer :" + this.f4216n);
        this.f4216n = false;
        TXCMP4Muxer tXCMP4Muxer = this.f4215m;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1227b();
            this.f4215m = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m1550c() {
        MoovHeaderProcessor.m2096a().m2095b();
        this.f4227y.post(new Runnable() { // from class: com.tencent.liteav.g.p.2
            @Override // java.lang.Runnable
            public void run() {
                if (VideoJoinGenerate.this.f4212j != null) {
                    TXCVideoEditConstants.C3514d c3514d = new TXCVideoEditConstants.C3514d();
                    c3514d.f4372a = 0;
                    c3514d.f4373b = "Join Complete";
                    TXCLog.m2915d("VideoJoinGenerate", "===onJoinComplete===");
                    VideoJoinGenerate.this.f4212j.mo257a(c3514d);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m1558a(final long j) {
        this.f4227y.post(new Runnable() { // from class: com.tencent.liteav.g.p.3
            @Override // java.lang.Runnable
            public void run() {
                if (VideoJoinGenerate.this.f4212j != null) {
                    long j2 = VideoJoinGenerate.this.f4210h.f3373k;
                    if (j2 <= 0) {
                        return;
                    }
                    float f = (((float) j) * 1.0f) / ((float) j2);
                    TXCLog.m2915d("VideoJoinGenerate", "onJoinProgress timestamp:" + j + ",progress:" + f + ",duration:" + j2);
                    VideoJoinGenerate.this.f4212j.mo258a(f);
                }
            }
        });
    }
}
