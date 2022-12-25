package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.view.Surface;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.PicConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoPreProcessConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p121f.AudioPreprocessChain;
import com.tencent.liteav.p121f.SpeedFilterChain;
import com.tencent.liteav.p121f.TailWaterMarkChain;
import com.tencent.liteav.p121f.VideoPreprocessChain;
import com.tencent.liteav.p125j.FrameCounter;
import com.tencent.liteav.p125j.TimeProvider;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.tencent.liteav.e.e */
/* loaded from: classes3.dex */
public abstract class BasicVideoGenerate {

    /* renamed from: a */
    protected Context f3647a;

    /* renamed from: b */
    protected boolean f3648b;

    /* renamed from: c */
    protected BasicVideoDecDemuxGenerater f3649c;

    /* renamed from: d */
    protected PicDec f3650d;

    /* renamed from: f */
    protected VideoPreprocessChain f3652f;

    /* renamed from: g */
    protected AudioPreprocessChain f3653g;

    /* renamed from: h */
    protected TXCVideoEncoder f3654h;

    /* renamed from: i */
    protected TXCMP4Muxer f3655i;

    /* renamed from: o */
    private BitmapCombineRender f3661o;

    /* renamed from: p */
    private Surface f3662p;

    /* renamed from: q */
    private AudioMediaCodecEncoder f3663q;

    /* renamed from: t */
    private Frame f3666t;

    /* renamed from: u */
    private Frame f3667u;

    /* renamed from: n */
    private final String f3660n = "BasicVideoGenerate";

    /* renamed from: r */
    private boolean f3664r = false;

    /* renamed from: v */
    private Object f3668v = new Object();

    /* renamed from: w */
    private OnContextListener f3669w = new OnContextListener() { // from class: com.tencent.liteav.e.e.1
        @Override // com.tencent.liteav.p120e.OnContextListener
        /* renamed from: a */
        public void mo1532a(EGLContext eGLContext) {
            BasicVideoGenerate basicVideoGenerate;
            PicDec picDec;
            BasicVideoGenerate basicVideoGenerate2;
            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater;
            TXCLog.m2915d("BasicVideoGenerate", "OnContextListener onContext");
            if (BasicVideoGenerate.this.f3662p == null) {
                return;
            }
            synchronized (BasicVideoGenerate.this.f3668v) {
                if (BasicVideoGenerate.this.f3654h != null) {
                    TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
                    tXSVideoEncoderParam.width = BasicVideoGenerate.this.f3658l.f3370h.f3467a;
                    tXSVideoEncoderParam.height = BasicVideoGenerate.this.f3658l.f3370h.f3468b;
                    tXSVideoEncoderParam.fps = BasicVideoGenerate.this.f3658l.m2435j();
                    tXSVideoEncoderParam.glContext = eGLContext;
                    tXSVideoEncoderParam.enableEGL14 = true;
                    tXSVideoEncoderParam.enableBlackList = false;
                    tXSVideoEncoderParam.appendSpsPps = false;
                    tXSVideoEncoderParam.annexb = true;
                    tXSVideoEncoderParam.fullIFrame = BasicVideoGenerate.this.f3658l.f3375m;
                    tXSVideoEncoderParam.gop = BasicVideoGenerate.this.f3658l.m2434k();
                    if (BasicVideoGenerate.this.f3648b) {
                        tXSVideoEncoderParam.encoderMode = 1;
                        tXSVideoEncoderParam.encoderProfile = 3;
                        tXSVideoEncoderParam.record = true;
                    } else {
                        tXSVideoEncoderParam.encoderMode = 3;
                        tXSVideoEncoderParam.encoderProfile = 1;
                    }
                    BasicVideoGenerate.this.f3654h.m433a(BasicVideoGenerate.this.f3658l.m2437i());
                    BasicVideoGenerate.this.f3654h.m419a(BasicVideoGenerate.this.f3644D);
                    BasicVideoGenerate.this.f3654h.m427a(tXSVideoEncoderParam);
                }
            }
            if (BasicVideoGenerate.this.f3658l.m2433l()) {
                BasicVideoGenerate.this.f3663q = new AudioMediaCodecEncoder();
                BasicVideoGenerate.this.f3663q.m2286a(BasicVideoGenerate.this.f3646F);
                BasicVideoGenerate.this.f3663q.m2284a(BasicVideoGenerate.this.f3645E);
                TXHAudioEncoderParam tXHAudioEncoderParam = new TXHAudioEncoderParam();
                VideoOutputConfig videoOutputConfig = BasicVideoGenerate.this.f3658l;
                tXHAudioEncoderParam.channelCount = videoOutputConfig.f3364b;
                tXHAudioEncoderParam.sampleRate = videoOutputConfig.f3363a;
                tXHAudioEncoderParam.maxInputSize = videoOutputConfig.f3365c;
                tXHAudioEncoderParam.audioBitrate = videoOutputConfig.m2439h();
                TXCLog.m2913i("BasicVideoGenerate", "AudioEncoder.start");
                BasicVideoGenerate.this.f3663q.m2285a(tXHAudioEncoderParam);
                AudioPreprocessChain audioPreprocessChain = BasicVideoGenerate.this.f3653g;
                if (audioPreprocessChain != null) {
                    audioPreprocessChain.m1897e();
                }
            }
            if (VideoSourceConfig.m2416a().m2411d() == 1 && (basicVideoDecDemuxGenerater = (basicVideoGenerate2 = BasicVideoGenerate.this).f3649c) != null) {
                basicVideoDecDemuxGenerater.m2175a(basicVideoGenerate2.f3662p);
                BasicVideoGenerate basicVideoGenerate3 = BasicVideoGenerate.this;
                basicVideoGenerate3.f3649c.m2172a(basicVideoGenerate3.f3670x);
                BasicVideoGenerate basicVideoGenerate4 = BasicVideoGenerate.this;
                basicVideoGenerate4.f3649c.m2173a(basicVideoGenerate4.f3671y);
                BasicVideoGenerate.this.f3649c.mo2044l();
            } else if (VideoSourceConfig.m2416a().m2411d() == 2 && (picDec = (basicVideoGenerate = BasicVideoGenerate.this).f3650d) != null) {
                picDec.m2088a(basicVideoGenerate.f3672z);
                BasicVideoGenerate.this.f3650d.m2074d();
            }
            FrameCounter.m1432h();
            TimeProvider.m1429a().m1426b();
        }
    };

    /* renamed from: x */
    private IVideoEditDecoderListener f3670x = new IVideoEditDecoderListener() { // from class: com.tencent.liteav.e.e.4
        @Override // com.tencent.liteav.p120e.IVideoEditDecoderListener
        /* renamed from: a */
        public void mo1939a(Frame frame) {
            FrameCounter.m1439a();
            TimeProvider.m1429a().m1425b(frame.m2329e());
            try {
                BasicVideoGenerate.this.f3665s.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VideoGLGenerate videoGLGenerate = BasicVideoGenerate.this.f3651e;
            if (videoGLGenerate != null) {
                videoGLGenerate.m2265a(frame);
            }
        }
    };

    /* renamed from: y */
    private IAudioEditDecoderListener f3671y = new IAudioEditDecoderListener() { // from class: com.tencent.liteav.e.e.5
        @Override // com.tencent.liteav.p120e.IAudioEditDecoderListener
        /* renamed from: a */
        public void mo1938a(Frame frame) {
            FrameCounter.m1438b();
            TimeProvider.m1429a().m1428a(frame.m2329e());
            AudioPreprocessChain audioPreprocessChain = BasicVideoGenerate.this.f3653g;
            if (audioPreprocessChain != null) {
                audioPreprocessChain.m1921a(frame);
            }
        }
    };

    /* renamed from: z */
    private IPictureDecderListener f3672z = new IPictureDecderListener() { // from class: com.tencent.liteav.e.e.6
        @Override // com.tencent.liteav.p120e.IPictureDecderListener
        /* renamed from: a */
        public void mo1937a(Frame frame) {
            TXCLog.m2915d("BasicVideoGenerate", "mPicDecListener, onDecodeBitmapFrame  frame:" + frame.m2329e() + ", flag : " + frame.m2327f());
            try {
                BasicVideoGenerate.this.f3665s.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VideoGLGenerate videoGLGenerate = BasicVideoGenerate.this.f3651e;
            if (videoGLGenerate != null) {
                videoGLGenerate.m2257b(frame);
            }
        }
    };

    /* renamed from: m */
    protected TailWaterMarkListener f3659m = new TailWaterMarkListener() { // from class: com.tencent.liteav.e.e.7
        @Override // com.tencent.liteav.p120e.TailWaterMarkListener
        /* renamed from: a */
        public void mo2058a(Frame frame) {
            AudioPreprocessChain audioPreprocessChain = BasicVideoGenerate.this.f3653g;
            if (audioPreprocessChain != null) {
                audioPreprocessChain.m1921a(frame);
            }
        }

        @Override // com.tencent.liteav.p120e.TailWaterMarkListener
        /* renamed from: b */
        public void mo2057b(Frame frame) {
            TXCLog.m2915d("BasicVideoGenerate", "TailWaterMarkListener onDecodeVideoFrame  frame:" + frame.m2329e() + ", flag : " + frame.m2327f() + ", reverse time = " + frame.m2304u());
            try {
                BasicVideoGenerate.this.f3665s.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VideoGLGenerate videoGLGenerate = BasicVideoGenerate.this.f3651e;
            if (videoGLGenerate != null) {
                videoGLGenerate.m2265a(frame);
            }
        }
    };

    /* renamed from: A */
    private IVideoRenderListener f3641A = new IVideoRenderListener() { // from class: com.tencent.liteav.e.e.8
        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1941a(boolean z) {
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1942a(Surface surface) {
            TXCLog.m2913i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureAvailable");
            BasicVideoGenerate.this.f3662p = surface;
            VideoPreprocessChain videoPreprocessChain = BasicVideoGenerate.this.f3652f;
            if (videoPreprocessChain != null) {
                videoPreprocessChain.m1808a();
                BasicVideoGenerate.this.f3652f.m1799b();
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1944a(int i, int i2) {
            if (BasicVideoGenerate.this.f3652f != null) {
                Resolution resolution = new Resolution();
                int m2419e = VideoPreProcessConfig.m2427a().m2419e();
                if (m2419e == 90 || m2419e == 270) {
                    resolution.f3467a = i2;
                    resolution.f3468b = i;
                } else {
                    resolution.f3467a = i;
                    resolution.f3468b = i2;
                }
                BasicVideoGenerate.this.f3652f.m1804a(resolution);
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: b */
        public void mo1940b(Surface surface) {
            TXCLog.m2913i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureDestroy");
            BasicVideoGenerate.this.mo1987e();
            BasicVideoGenerate.this.f3662p = null;
            VideoPreprocessChain videoPreprocessChain = BasicVideoGenerate.this.f3652f;
            if (videoPreprocessChain != null) {
                videoPreprocessChain.m1795c();
                BasicVideoGenerate.this.f3652f.m1792d();
            }
            if (BasicVideoGenerate.this.f3661o != null) {
                BasicVideoGenerate.this.f3661o.m2105a();
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public int mo1943a(int i, float[] fArr, Frame frame) {
            FrameCounter.m1435e();
            if (BasicVideoGenerate.this.f3661o != null) {
                i = BasicVideoGenerate.this.f3661o.m2101a(frame, PicConfig.m2485a().m2483b(), frame.m2307r());
                frame.m2314l(i);
                frame.m2312m(0);
            }
            VideoPreprocessChain videoPreprocessChain = BasicVideoGenerate.this.f3652f;
            if (videoPreprocessChain != null) {
                videoPreprocessChain.m1800a(fArr);
                BasicVideoGenerate.this.f3652f.m1807a(i, frame);
            }
            return 0;
        }
    };

    /* renamed from: B */
    private IVideoProcessorListener f3642B = new IVideoProcessorListener() { // from class: com.tencent.liteav.e.e.9
        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: a */
        public void mo1488a(int i, Frame frame) {
            AudioPreprocessChain audioPreprocessChain;
            AudioPreprocessChain audioPreprocessChain2;
            FrameCounter.m1437c();
            long m1427a = TimeProvider.m1427a(frame);
            TimeProvider.m1429a().m1423d(m1427a);
            if (frame.m2309p()) {
                if (!BasicVideoGenerate.this.f3657k.m1819b()) {
                    if (!BasicVideoGenerate.this.f3658l.m2433l() || ((VideoSourceConfig.m2416a().m2411d() != 2 && (VideoSourceConfig.m2416a().m2411d() != 1 || BasicVideoGenerate.this.f3649c.m2164h())) || ((audioPreprocessChain2 = BasicVideoGenerate.this.f3653g) != null && audioPreprocessChain2.m1889j()))) {
                        synchronized (BasicVideoGenerate.this.f3668v) {
                            if (BasicVideoGenerate.this.f3654h != null) {
                                BasicVideoGenerate.this.f3654h.m415b();
                                TXCLog.m2915d("BasicVideoGenerate", "signalEOSAndFlush");
                                return;
                            }
                        }
                    } else {
                        TXCLog.m2911w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,BGM is not end");
                        return;
                    }
                } else if (BasicVideoGenerate.this.f3657k.m1811j()) {
                    synchronized (BasicVideoGenerate.this.f3668v) {
                        if (BasicVideoGenerate.this.f3654h != null) {
                            BasicVideoGenerate.this.f3654h.m415b();
                            TXCLog.m2915d("BasicVideoGenerate", "TailWaterMarkListener signalEOSAndFlush");
                            return;
                        }
                    }
                } else {
                    if (BasicVideoGenerate.this.f3665s != null) {
                        BasicVideoGenerate.this.f3665s.remove(frame);
                    }
                    if (BasicVideoGenerate.this.f3658l.m2433l() && ((VideoSourceConfig.m2416a().m2411d() == 2 || (VideoSourceConfig.m2416a().m2411d() == 1 && !BasicVideoGenerate.this.f3649c.m2164h())) && ((audioPreprocessChain = BasicVideoGenerate.this.f3653g) == null || !audioPreprocessChain.m1889j()))) {
                        TXCLog.m2911w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,BGM is not end");
                        return;
                    }
                    TXCLog.m2913i("BasicVideoGenerate", "Encount EOF Video didProcessFrame appendTailWaterMark, mLastVideoFrame = " + BasicVideoGenerate.this.f3667u);
                    BasicVideoGenerate basicVideoGenerate = BasicVideoGenerate.this;
                    basicVideoGenerate.f3657k.f3967a = basicVideoGenerate.f3667u;
                    BasicVideoGenerate basicVideoGenerate2 = BasicVideoGenerate.this;
                    basicVideoGenerate2.f3657k.f3968b = basicVideoGenerate2.f3666t;
                    BasicVideoGenerate.this.f3657k.m1817d();
                    TXCLog.m2913i("BasicVideoGenerate", "mLastVideoFrame width, height = " + BasicVideoGenerate.this.f3667u.m2313m() + ", " + BasicVideoGenerate.this.f3667u.m2311n());
                    return;
                }
            }
            synchronized (BasicVideoGenerate.this.f3668v) {
                if (BasicVideoGenerate.this.f3654h != null) {
                    BasicVideoGenerate.this.f3654h.m414b(i, frame.m2313m(), frame.m2311n(), m1427a / 1000);
                }
            }
            if (BasicVideoGenerate.this.f3658l.m2445e()) {
                try {
                    BasicVideoGenerate.this.f3665s.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (VideoSourceConfig.m2416a().m2411d() == 1) {
                    BasicVideoGenerate.this.f3649c.mo2042p();
                }
            } else if (VideoSourceConfig.m2416a().m2411d() == 1) {
                if (BasicVideoGenerate.this.f3649c.m2135o()) {
                    if (BasicVideoGenerate.this.f3657k.m1819b()) {
                        BasicVideoGenerate.this.f3657k.m1815f();
                    }
                } else {
                    BasicVideoGenerate.this.f3649c.mo2042p();
                }
            } else if (VideoSourceConfig.m2416a().m2411d() == 2) {
                if (BasicVideoGenerate.this.f3650d.m2077c()) {
                    if (BasicVideoGenerate.this.f3657k.m1819b()) {
                        BasicVideoGenerate.this.f3657k.m1815f();
                    }
                } else {
                    BasicVideoGenerate.this.f3650d.m2066h();
                }
            }
            BasicVideoGenerate.this.f3667u = frame;
        }

        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: b */
        public int mo1487b(int i, Frame frame) {
            return BasicVideoGenerate.this.mo1994a(i, frame.m2313m(), frame.m2311n(), frame.m2329e());
        }
    };

    /* renamed from: C */
    private IAudioPreprocessListener f3643C = new IAudioPreprocessListener() { // from class: com.tencent.liteav.e.e.10
        @Override // com.tencent.liteav.p120e.IAudioPreprocessListener
        /* renamed from: a */
        public void mo1486a(Frame frame) {
            PicDec picDec;
            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater;
            if (frame == null) {
                return;
            }
            FrameCounter.m1436d();
            TimeProvider.m1429a().m1424c(frame.m2329e());
            if (frame.m2309p()) {
                if (!BasicVideoGenerate.this.f3657k.m1819b()) {
                    if ((VideoSourceConfig.m2416a().m2411d() == 2 || (VideoSourceConfig.m2416a().m2411d() == 1 && !BasicVideoGenerate.this.f3649c.m2164h())) && (((picDec = BasicVideoGenerate.this.f3650d) != null && picDec.m2077c()) || ((basicVideoDecDemuxGenerater = BasicVideoGenerate.this.f3649c) != null && basicVideoDecDemuxGenerater.m2135o()))) {
                        synchronized (BasicVideoGenerate.this.f3668v) {
                            if (BasicVideoGenerate.this.f3654h != null) {
                                BasicVideoGenerate.this.f3654h.m415b();
                                TXCLog.m2915d("BasicVideoGenerate", "signalEOSAndFlush");
                                return;
                            }
                        }
                    }
                } else if (BasicVideoGenerate.this.f3657k.m1811j()) {
                    synchronized (BasicVideoGenerate.this.f3668v) {
                        if (BasicVideoGenerate.this.f3654h != null) {
                            BasicVideoGenerate.this.f3654h.m415b();
                            TXCLog.m2915d("BasicVideoGenerate", "Encount EOF TailWaterMarkListener signalEOSAndFlush");
                            return;
                        }
                    }
                } else if (BasicVideoGenerate.this.f3658l.m2433l() && ((VideoSourceConfig.m2416a().m2411d() == 2 && !BasicVideoGenerate.this.f3650d.m2077c()) || (VideoSourceConfig.m2416a().m2411d() == 1 && !BasicVideoGenerate.this.f3649c.m2164h() && !BasicVideoGenerate.this.f3649c.m2135o()))) {
                    TXCLog.m2911w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,Video is not end");
                    return;
                } else {
                    TXCLog.m2913i("BasicVideoGenerate", "Encount EOF Audio didProcessFrame appendTailWaterMark");
                    BasicVideoGenerate basicVideoGenerate = BasicVideoGenerate.this;
                    basicVideoGenerate.f3657k.f3967a = basicVideoGenerate.f3667u;
                    BasicVideoGenerate basicVideoGenerate2 = BasicVideoGenerate.this;
                    basicVideoGenerate2.f3657k.f3968b = basicVideoGenerate2.f3666t;
                    BasicVideoGenerate.this.f3657k.m1817d();
                    return;
                }
            }
            if (BasicVideoGenerate.this.f3663q != null) {
                BasicVideoGenerate.this.f3663q.m2290a(frame);
            }
            AudioPreprocessChain audioPreprocessChain = BasicVideoGenerate.this.f3653g;
            if (audioPreprocessChain != null) {
                audioPreprocessChain.m1890i();
            }
            BasicVideoGenerate.this.f3666t = frame;
        }
    };

    /* renamed from: D */
    private TXIVideoEncoderListener f3644D = new TXIVideoEncoderListener() { // from class: com.tencent.liteav.e.e.11
        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
            if (i != 0) {
                TXCLog.m2913i("BasicVideoGenerate", "mVideoEncodeListener, errCode = " + i);
                return;
            }
            FrameCounter.m1434f();
            if (BasicVideoGenerate.this.f3658l.m2445e()) {
                TXCLog.m2913i("BasicVideoGenerate", "mVideoEncodeListener, input is full, output is full");
            } else if (tXSNALPacket != null && tXSNALPacket.nalData != null) {
                Frame frame = null;
                try {
                    frame = (Frame) BasicVideoGenerate.this.f3665s.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (frame.m2309p()) {
                    TXCLog.m2913i("BasicVideoGenerate", "===Video onEncodeComplete===:" + frame.m2309p() + ", nal:" + tXSNALPacket);
                    BasicVideoGenerate.this.mo1989b();
                    BasicVideoGenerate.this.mo1988d();
                    return;
                }
                synchronized (this) {
                    if (BasicVideoGenerate.this.f3655i != null && tXSNALPacket != null && tXSNALPacket.nalData != null) {
                        if (BasicVideoGenerate.this.f3664r) {
                            m2106a(tXSNALPacket, frame);
                        } else if (tXSNALPacket.nalType == 0) {
                            MediaFormat m2882a = TXCSystemUtil.m2882a(tXSNALPacket.nalData, BasicVideoGenerate.this.f3658l.f3370h.f3467a, BasicVideoGenerate.this.f3658l.f3370h.f3468b);
                            if (m2882a != null) {
                                BasicVideoGenerate.this.f3655i.mo1232a(m2882a);
                                BasicVideoGenerate.this.f3655i.mo1235a();
                                BasicVideoGenerate.this.f3664r = true;
                            }
                            m2106a(tXSNALPacket, frame);
                        }
                    }
                }
                BasicVideoGenerate.this.mo1993a(frame.m2305t());
            } else {
                TXCLog.m2913i("BasicVideoGenerate", "===Video onEncodeComplete===");
                BasicVideoGenerate.this.mo1989b();
                BasicVideoGenerate.this.mo1988d();
            }
        }

        /* renamed from: a */
        private void m2106a(TXSNALPacket tXSNALPacket, Frame frame) {
            int i;
            long m1427a = TimeProvider.m1427a(frame);
            TimeProvider.m1429a().m1421f(m1427a);
            MediaCodec.BufferInfo bufferInfo = tXSNALPacket.info;
            if (bufferInfo == null) {
                i = tXSNALPacket.nalType == 0 ? 1 : 0;
            } else {
                i = bufferInfo.flags;
            }
            TXCMP4Muxer tXCMP4Muxer = BasicVideoGenerate.this.f3655i;
            if (tXCMP4Muxer != null) {
                byte[] bArr = tXSNALPacket.nalData;
                tXCMP4Muxer.mo1224b(bArr, 0, bArr.length, m1427a, i);
            }
        }

        @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
        public void onEncodeFormat(MediaFormat mediaFormat) {
            TXCMP4Muxer tXCMP4Muxer;
            TXCLog.m2913i("BasicVideoGenerate", "Video onEncodeFormat format:" + mediaFormat);
            if (!BasicVideoGenerate.this.f3658l.m2445e() && (tXCMP4Muxer = BasicVideoGenerate.this.f3655i) != null) {
                tXCMP4Muxer.mo1232a(mediaFormat);
                if (BasicVideoGenerate.this.f3658l.m2433l()) {
                    if (!BasicVideoGenerate.this.f3655i.mo1221d()) {
                        return;
                    }
                    TXCLog.m2913i("BasicVideoGenerate", "Has Audio, Video Muxer start");
                    BasicVideoGenerate.this.f3655i.mo1235a();
                    BasicVideoGenerate.this.f3664r = true;
                    return;
                }
                TXCLog.m2913i("muxer", "No Audio, Video Muxer start");
                BasicVideoGenerate.this.f3655i.mo1235a();
                BasicVideoGenerate.this.f3664r = true;
            }
        }
    };

    /* renamed from: E */
    private TXIAudioEncoderListener f3645E = new TXIAudioEncoderListener() { // from class: com.tencent.liteav.e.e.2
        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1528a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            FrameCounter.m1433g();
            TimeProvider.m1429a().m1422e(bufferInfo.presentationTimeUs);
            TXCMP4Muxer tXCMP4Muxer = BasicVideoGenerate.this.f3655i;
            if (tXCMP4Muxer != null) {
                tXCMP4Muxer.mo1230a(byteBuffer, bufferInfo);
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1529a(MediaFormat mediaFormat) {
            TXCMP4Muxer tXCMP4Muxer;
            TXCLog.m2913i("BasicVideoGenerate", "Audio onEncodeFormat format:" + mediaFormat);
            if (!BasicVideoGenerate.this.f3658l.m2445e() && (tXCMP4Muxer = BasicVideoGenerate.this.f3655i) != null) {
                tXCMP4Muxer.mo1226b(mediaFormat);
                if ((VideoSourceConfig.m2416a().m2411d() != 2 && !BasicVideoGenerate.this.f3649c.m2163i()) || !BasicVideoGenerate.this.f3655i.mo1223c()) {
                    return;
                }
                BasicVideoGenerate.this.f3655i.mo1235a();
                BasicVideoGenerate.this.f3664r = true;
            }
        }

        @Override // com.tencent.liteav.p120e.TXIAudioEncoderListener
        /* renamed from: a */
        public void mo1530a() {
            TXCLog.m2913i("BasicVideoGenerate", "===Audio onEncodeComplete===");
            BasicVideoGenerate.this.mo1989b();
            BasicVideoGenerate.this.mo1988d();
        }
    };

    /* renamed from: F */
    private IAudioDecodeCallback f3646F = new IAudioDecodeCallback() { // from class: com.tencent.liteav.e.e.3
        @Override // com.tencent.liteav.p120e.IAudioDecodeCallback
        /* renamed from: a */
        public void mo1531a(int i) {
            boolean z = false;
            if (VideoSourceConfig.m2416a().m2411d() == 1 && BasicVideoGenerate.this.f3649c.m2164h()) {
                BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = BasicVideoGenerate.this.f3649c;
                if (i <= 5) {
                    z = true;
                }
                basicVideoDecDemuxGenerater.m2150b(z);
                return;
            }
            AudioPreprocessChain audioPreprocessChain = BasicVideoGenerate.this.f3653g;
            if (audioPreprocessChain == null) {
                return;
            }
            if (i <= 5) {
                z = true;
            }
            audioPreprocessChain.m1900c(z);
        }
    };

    /* renamed from: e */
    protected VideoGLGenerate f3651e = new VideoGLGenerate();

    /* renamed from: s */
    private LinkedBlockingQueue<Frame> f3665s = new LinkedBlockingQueue<>();

    /* renamed from: l */
    protected VideoOutputConfig f3658l = VideoOutputConfig.m2457a();

    /* renamed from: j */
    protected CutTimeConfig f3656j = CutTimeConfig.m2501a();

    /* renamed from: k */
    protected TailWaterMarkChain f3657k = TailWaterMarkChain.m1822a();

    /* renamed from: a */
    protected abstract int mo1994a(int i, int i2, int i3, long j);

    /* renamed from: a */
    protected abstract void mo1993a(long j);

    /* renamed from: d */
    protected abstract void mo1988d();

    /* renamed from: e */
    protected abstract void mo1987e();

    public BasicVideoGenerate(Context context) {
        this.f3648b = false;
        this.f3647a = context;
        this.f3652f = new VideoPreprocessChain(context);
        this.f3652f.m1803a(this.f3642B);
        this.f3648b = TXCSystemUtil.m2871g();
    }

    /* renamed from: a */
    public void m2124a(String str) {
        TXCLog.m2913i("BasicVideoGenerate", "setVideoPath videoPath:" + str);
        try {
            if (this.f3649c == null) {
                this.f3649c = new VideoDecAndDemuxGenerate();
            }
            this.f3649c.m2171a(str);
            if (this.f3649c.m2164h()) {
                this.f3658l.m2455a(this.f3649c.m2166f());
            }
            this.f3658l.m2452b(this.f3649c.m2165g());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m2123a(List<Bitmap> list, int i) {
        this.f3650d = new PicDec();
        this.f3650d.m2083a(false);
        this.f3650d.m2084a(list, i);
        this.f3661o = new BitmapCombineRender(this.f3647a, this.f3650d.m2094a(), this.f3650d.m2082b());
    }

    /* renamed from: a */
    public void mo1995a() {
        TXCMP4Muxer tXCMP4Muxer;
        TXCLog.m2913i("BasicVideoGenerate", C2516Ad.TYPE_START);
        this.f3665s.clear();
        m2116f();
        if (this.f3658l.m2433l()) {
            this.f3653g = new AudioPreprocessChain();
            this.f3653g.m1927a();
            this.f3653g.m1920a(this.f3643C);
            this.f3653g.m1908b(this.f3658l.f3374l);
            if (VideoSourceConfig.m2416a().m2411d() == 1) {
                this.f3653g.m1904b(this.f3649c.m2164h());
            } else {
                this.f3653g.m1904b(false);
            }
            this.f3653g.m1903c();
            MediaFormat m2431n = this.f3658l.m2431n();
            if (m2431n != null) {
                this.f3653g.m1922a(m2431n);
            }
            if ((VideoSourceConfig.m2416a().m2411d() == 2 || !this.f3649c.m2164h()) && (tXCMP4Muxer = this.f3655i) != null) {
                tXCMP4Muxer.mo1226b(m2431n);
            }
        }
        Resolution resolution = new Resolution();
        if (VideoSourceConfig.m2416a().m2411d() == 1) {
            resolution.f3467a = this.f3649c.m2168d();
            resolution.f3468b = this.f3649c.m2167e();
            resolution.f3469c = this.f3649c.m2136n();
        } else if (VideoSourceConfig.m2416a().m2411d() == 2) {
            resolution.f3467a = this.f3650d.m2094a();
            resolution.f3468b = this.f3650d.m2082b();
        }
        Resolution m2454a = this.f3658l.m2454a(resolution);
        VideoOutputConfig videoOutputConfig = this.f3658l;
        videoOutputConfig.f3370h = m2454a;
        VideoPreprocessChain videoPreprocessChain = this.f3652f;
        if (videoPreprocessChain != null) {
            videoPreprocessChain.m1804a(videoOutputConfig.f3370h);
        }
        this.f3651e.m2264a(m2454a);
        this.f3651e.m2259a(this.f3669w);
        this.f3651e.m2260a(this.f3641A);
        this.f3651e.m2266a();
    }

    /* renamed from: b */
    public void mo1989b() {
        AudioPreprocessChain audioPreprocessChain;
        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = this.f3649c;
        if (basicVideoDecDemuxGenerater != null) {
            basicVideoDecDemuxGenerater.m2172a((IVideoEditDecoderListener) null);
            this.f3649c.m2173a((IAudioEditDecoderListener) null);
            this.f3649c.mo2043m();
        }
        PicDec picDec = this.f3650d;
        if (picDec != null) {
            picDec.m2088a((IPictureDecderListener) null);
            this.f3650d.m2072e();
        }
        VideoGLGenerate videoGLGenerate = this.f3651e;
        if (videoGLGenerate != null) {
            videoGLGenerate.m2259a((OnContextListener) null);
            this.f3651e.m2260a((IVideoRenderListener) null);
            this.f3651e.m2258b();
        }
        if (this.f3658l.m2433l() && (audioPreprocessChain = this.f3653g) != null) {
            audioPreprocessChain.m1899d();
            this.f3653g.m1920a((IAudioPreprocessListener) null);
            this.f3653g.m1911b();
            this.f3653g = null;
        }
        TXCVideoEncoder tXCVideoEncoder = this.f3654h;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
            this.f3654h.m434a();
        }
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f3663q;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2284a((TXIAudioEncoderListener) null);
            this.f3663q.m2286a((IAudioDecodeCallback) null);
            this.f3663q.m2292a();
        }
        TXCLog.m2913i("BasicVideoGenerate", "stop muxer :" + this.f3664r);
        this.f3664r = false;
        TXCMP4Muxer tXCMP4Muxer = this.f3655i;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1227b();
            this.f3655i = null;
        }
    }

    /* renamed from: c */
    public void mo2051c() {
        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = this.f3649c;
        if (basicVideoDecDemuxGenerater != null) {
            basicVideoDecDemuxGenerater.mo2017k();
        }
        this.f3649c = null;
        PicDec picDec = this.f3650d;
        if (picDec != null) {
            picDec.m2065i();
        }
        this.f3650d = null;
        VideoGLGenerate videoGLGenerate = this.f3651e;
        if (videoGLGenerate != null) {
            videoGLGenerate.m2254c();
        }
        this.f3651e = null;
        VideoPreprocessChain videoPreprocessChain = this.f3652f;
        if (videoPreprocessChain != null) {
            videoPreprocessChain.m1803a((IVideoProcessorListener) null);
        }
        this.f3652f = null;
        synchronized (this.f3668v) {
            this.f3654h = null;
        }
        AudioMediaCodecEncoder audioMediaCodecEncoder = this.f3663q;
        if (audioMediaCodecEncoder != null) {
            audioMediaCodecEncoder.m2280b();
        }
        this.f3663q = null;
        this.f3645E = null;
        this.f3643C = null;
        this.f3645E = null;
        this.f3669w = null;
        this.f3670x = null;
        this.f3671y = null;
        this.f3646F = null;
        this.f3672z = null;
        this.f3659m = null;
        this.f3642B = null;
        this.f3644D = null;
        this.f3641A = null;
    }

    /* renamed from: f */
    private void m2116f() {
        long m2093a;
        if (VideoSourceConfig.m2416a().m2411d() == 1) {
            m2093a = this.f3649c.m2169c();
        } else {
            m2093a = VideoSourceConfig.m2416a().m2411d() == 2 ? this.f3650d.m2093a(PicConfig.m2485a().m2483b()) * 1000 : 0L;
        }
        TXCLog.m2915d("BasicVideoGenerate", "calculateDuration durationUs:" + m2093a);
        long m2499b = this.f3656j.m2499b();
        long m2497c = this.f3656j.m2497c();
        long j = m2497c - m2499b;
        if (j > 0) {
            TXCLog.m2915d("BasicVideoGenerate", "calculateDuration Cut durationUs:" + j);
            if (VideoSourceConfig.m2416a().m2411d() == 1) {
                this.f3649c.m2160a(m2499b, m2497c);
            } else if (VideoSourceConfig.m2416a().m2411d() == 2) {
                this.f3650d.m2091a(m2499b / 1000, m2497c / 1000);
            }
        } else {
            if (VideoSourceConfig.m2416a().m2411d() == 1) {
                this.f3649c.m2160a(0L, m2093a);
            } else if (VideoSourceConfig.m2416a().m2411d() == 2) {
                this.f3650d.m2091a(0L, m2093a / 1000);
            }
            j = m2093a;
        }
        VideoOutputConfig videoOutputConfig = this.f3658l;
        videoOutputConfig.f3373k = j;
        videoOutputConfig.f3374l = j;
        if (SpeedFilterChain.m1849a().m1843c()) {
            this.f3658l.f3373k = SpeedFilterChain.m1849a().m1844b(this.f3658l.f3373k);
            VideoOutputConfig videoOutputConfig2 = this.f3658l;
            videoOutputConfig2.f3374l = videoOutputConfig2.f3373k;
            TXCLog.m2915d("BasicVideoGenerate", "calculateDuration Speed durationUs:" + this.f3658l.f3373k);
        }
        if (this.f3657k.m1819b()) {
            this.f3658l.f3373k += this.f3657k.m1818c();
            TXCLog.m2915d("BasicVideoGenerate", "calculateDuration AddTailWaterMark durationUs:" + this.f3658l.f3373k);
        }
    }

    /* renamed from: a */
    public void m2122a(boolean z) {
        VideoPreprocessChain videoPreprocessChain = this.f3652f;
        if (videoPreprocessChain != null) {
            videoPreprocessChain.m1796b(z);
        }
    }
}
