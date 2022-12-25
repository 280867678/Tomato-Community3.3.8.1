package com.tencent.liteav.videodecoder;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.tencent.liteav.videodecoder.b */
/* loaded from: classes3.dex */
public class TXCVideoDecoder implements TXINotifyListener, TXIVideoDecoderListener {

    /* renamed from: e */
    Surface f5411e;

    /* renamed from: f */
    TXIVideoDecoderListener f5412f;

    /* renamed from: g */
    private int f5413g;

    /* renamed from: h */
    private ByteBuffer f5414h;

    /* renamed from: i */
    private ByteBuffer f5415i;

    /* renamed from: j */
    private long f5416j;

    /* renamed from: m */
    private HandlerC3658a f5419m;

    /* renamed from: n */
    private WeakReference<TXINotifyListener> f5420n;

    /* renamed from: k */
    private boolean f5417k = false;

    /* renamed from: l */
    private ArrayList<TXSNALPacket> f5418l = new ArrayList<>();

    /* renamed from: b */
    boolean f5408b = true;

    /* renamed from: c */
    boolean f5409c = false;

    /* renamed from: a */
    boolean f5407a = true;

    /* renamed from: d */
    boolean f5410d = false;

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        TXCSystemUtil.m2884a(this.f5420n, this.f5416j, i, bundle);
    }

    /* renamed from: a */
    public void m620a(long j) {
        this.f5416j = j;
    }

    /* renamed from: a */
    public void m615a(TXIVideoDecoderListener tXIVideoDecoderListener) {
        this.f5412f = tXIVideoDecoderListener;
    }

    /* renamed from: a */
    public boolean m621a() {
        return this.f5408b;
    }

    /* renamed from: a */
    public void m617a(TXINotifyListener tXINotifyListener) {
        this.f5420n = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: a */
    public int m619a(SurfaceTexture surfaceTexture, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        return m618a(new Surface(surfaceTexture), byteBuffer, byteBuffer2, z);
    }

    /* renamed from: a */
    public int m618a(Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z) {
        this.f5411e = surface;
        this.f5414h = byteBuffer;
        this.f5415i = byteBuffer2;
        this.f5407a = z;
        return 0;
    }

    /* renamed from: a */
    public void m614a(boolean z) {
        this.f5408b = z;
    }

    /* renamed from: b */
    private void m611b(TXSNALPacket tXSNALPacket) {
        boolean z = tXSNALPacket.nalType == 0;
        Bundle bundle = new Bundle();
        bundle.putBoolean("iframe", z);
        bundle.putByteArray("nal", tXSNALPacket.nalData);
        bundle.putLong("pts", tXSNALPacket.pts);
        bundle.putLong("dts", tXSNALPacket.dts);
        bundle.putInt("codecId", tXSNALPacket.codecId);
        Message message = new Message();
        message.what = 101;
        message.setData(bundle);
        HandlerC3658a handlerC3658a = this.f5419m;
        if (handlerC3658a != null) {
            handlerC3658a.sendMessage(message);
        }
        this.f5413g++;
    }

    /* renamed from: a */
    public void m616a(TXSNALPacket tXSNALPacket) {
        try {
            boolean z = tXSNALPacket.nalType == 0;
            if (!this.f5410d && !z) {
                TXCLog.m2913i("TXCVideoDecoder", "play:decode: push nal ignore p frame when not got i frame");
                return;
            }
            if (!this.f5410d && z) {
                TXCLog.m2911w("TXCVideoDecoder", "play:decode: push first i frame");
                this.f5410d = true;
            }
            if (!this.f5417k && tXSNALPacket.codecId == 1 && !this.f5408b) {
                TXCLog.m2911w("TXCVideoDecoder", "play:decode: hevc decode error  ");
                TXCSystemUtil.m2885a(this.f5420n, (int) TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL, "h265解码失败");
                this.f5417k = true;
            }
            if (this.f5419m != null) {
                if (!this.f5418l.isEmpty()) {
                    Iterator<TXSNALPacket> it2 = this.f5418l.iterator();
                    while (it2.hasNext()) {
                        m611b(it2.next());
                    }
                }
                this.f5418l.clear();
                m611b(tXSNALPacket);
                return;
            }
            if (z && !this.f5418l.isEmpty()) {
                this.f5418l.clear();
            }
            this.f5418l.add(tXSNALPacket);
            if (this.f5417k) {
                return;
            }
            m612b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    public int m612b() {
        if (this.f5408b && this.f5411e == null) {
            TXCLog.m2913i("TXCVideoDecoder", "play:decode: start decoder error when not setup surface");
            return -1;
        }
        synchronized (this) {
            if (this.f5419m != null) {
                TXCLog.m2914e("TXCVideoDecoder", "play:decode: start decoder error when decoder is started");
                return -1;
            }
            this.f5413g = 0;
            this.f5417k = false;
            HandlerThread handlerThread = new HandlerThread("VDecoder");
            handlerThread.start();
            handlerThread.setName("VDecoder" + handlerThread.getId());
            HandlerC3658a handlerC3658a = new HandlerC3658a(handlerThread.getLooper());
            handlerC3658a.m603a(this.f5409c, this.f5408b, this.f5411e, this.f5414h, this.f5415i, this, this);
            TXCLog.m2911w("TXCVideoDecoder", "play:decode: start decode thread");
            Message obtain = Message.obtain();
            obtain.what = 100;
            obtain.obj = Boolean.valueOf(this.f5407a);
            handlerC3658a.sendMessage(obtain);
            this.f5419m = handlerC3658a;
            Bundle bundle = new Bundle();
            bundle.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putCharSequence("EVT_MSG", this.f5408b ? "启动硬解" : "启动软解");
            bundle.putInt("EVT_PARAM1", this.f5408b ? 1 : 2);
            TXCSystemUtil.m2884a(this.f5420n, this.f5416j, (int) TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER, bundle);
            return 0;
        }
    }

    /* renamed from: c */
    public void m609c() {
        synchronized (this) {
            if (this.f5419m != null) {
                this.f5419m.sendEmptyMessage(102);
            }
            this.f5419m = null;
        }
        this.f5418l.clear();
        this.f5410d = false;
        this.f5413g = 0;
    }

    /* renamed from: b */
    public void m610b(boolean z) {
        synchronized (this) {
            this.f5408b = z;
            this.f5418l.clear();
            int i = 0;
            this.f5410d = false;
            this.f5413g = 0;
            Message obtain = Message.obtain();
            obtain.what = 103;
            obtain.arg1 = this.f5408b ? 1 : 0;
            if (this.f5407a) {
                i = 1;
            }
            obtain.arg2 = i;
            if (this.f5419m != null) {
                this.f5419m.sendMessage(obtain);
            }
        }
    }

    /* renamed from: a */
    public void m613a(byte[] bArr, long j, int i) {
        IVideoDecoder iVideoDecoder;
        HandlerC3658a handlerC3658a = this.f5419m;
        if (handlerC3658a == null || handlerC3658a.f5424d || (iVideoDecoder = handlerC3658a.f5421a) == null) {
            return;
        }
        ((TXCVideoFfmpegDecoder) iVideoDecoder).loadNativeData(bArr, j, i);
    }

    /* renamed from: d */
    public int m608d() {
        return this.f5413g + this.f5418l.size();
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo588a(SurfaceTexture surfaceTexture, int i, int i2, long j, long j2) {
        TXIVideoDecoderListener tXIVideoDecoderListener = this.f5412f;
        if (tXIVideoDecoderListener != null) {
            tXIVideoDecoderListener.mo588a(surfaceTexture, i, i2, j, j2);
        }
        int i3 = this.f5413g;
        if (i3 > 0) {
            this.f5413g = i3 - 1;
        }
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo589a(long j, int i, int i2, long j2, long j3) {
        TXIVideoDecoderListener tXIVideoDecoderListener = this.f5412f;
        if (tXIVideoDecoderListener != null) {
            tXIVideoDecoderListener.mo589a(j, i, i2, j2, j3);
        }
        int i3 = this.f5413g;
        if (i3 > 0) {
            this.f5413g = i3 - 1;
        }
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo590a(int i, int i2) {
        TXIVideoDecoderListener tXIVideoDecoderListener = this.f5412f;
        if (tXIVideoDecoderListener != null) {
            tXIVideoDecoderListener.mo590a(i, i2);
        }
    }

    /* renamed from: e */
    public boolean m607e() {
        HandlerC3658a handlerC3658a = this.f5419m;
        if (handlerC3658a != null) {
            return handlerC3658a.m606a();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCVideoDecoder.java */
    /* renamed from: com.tencent.liteav.videodecoder.b$a */
    /* loaded from: classes3.dex */
    public static class HandlerC3658a extends Handler {

        /* renamed from: a */
        IVideoDecoder f5421a;

        /* renamed from: b */
        TXIVideoDecoderListener f5422b;

        /* renamed from: c */
        WeakReference<TXINotifyListener> f5423c;

        /* renamed from: d */
        boolean f5424d;

        /* renamed from: e */
        boolean f5425e;

        /* renamed from: f */
        Surface f5426f;

        /* renamed from: g */
        private ByteBuffer f5427g;

        /* renamed from: h */
        private ByteBuffer f5428h;

        public HandlerC3658a(Looper looper) {
            super(looper);
        }

        /* renamed from: a */
        public void m603a(boolean z, boolean z2, Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, TXIVideoDecoderListener tXIVideoDecoderListener, TXINotifyListener tXINotifyListener) {
            this.f5425e = z;
            this.f5424d = z2;
            this.f5426f = surface;
            this.f5427g = byteBuffer;
            this.f5428h = byteBuffer2;
            this.f5422b = tXIVideoDecoderListener;
            this.f5423c = new WeakReference<>(tXINotifyListener);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 100:
                    m605a(((Boolean) message.obj).booleanValue());
                    return;
                case 101:
                    try {
                        Bundle data = message.getData();
                        m602a(data.getByteArray("nal"), data.getLong("pts"), data.getLong("dts"), data.getInt("codecId"));
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 102:
                    m601b();
                    return;
                case 103:
                    boolean z = false;
                    boolean z2 = message.arg1 == 1;
                    if (message.arg2 == 1) {
                        z = true;
                    }
                    m604a(z2, z);
                    return;
                default:
                    return;
            }
        }

        /* renamed from: a */
        public boolean m606a() {
            IVideoDecoder iVideoDecoder = this.f5421a;
            if (iVideoDecoder != null) {
                return iVideoDecoder.isHevc();
            }
            return false;
        }

        /* renamed from: a */
        private void m602a(byte[] bArr, long j, long j2, int i) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.pts = j;
            tXSNALPacket.dts = j2;
            tXSNALPacket.codecId = i;
            IVideoDecoder iVideoDecoder = this.f5421a;
            if (iVideoDecoder != null) {
                iVideoDecoder.decode(tXSNALPacket);
            }
        }

        /* renamed from: b */
        private void m601b() {
            IVideoDecoder iVideoDecoder = this.f5421a;
            if (iVideoDecoder != null) {
                iVideoDecoder.stop();
                this.f5421a.setListener(null);
                this.f5421a.setNotifyListener(null);
                this.f5421a = null;
            }
            Looper.myLooper().quit();
            TXCLog.m2911w("TXCVideoDecoder", "play:decode: stop decode hwdec: " + this.f5424d);
        }

        /* renamed from: a */
        private void m604a(boolean z, boolean z2) {
            this.f5424d = z;
            TXCLog.m2911w("TXCVideoDecoder", "play:decode: restart decode hwdec: " + this.f5424d);
            IVideoDecoder iVideoDecoder = this.f5421a;
            if (iVideoDecoder != null) {
                iVideoDecoder.stop();
                this.f5421a.setListener(null);
                this.f5421a.setNotifyListener(null);
                this.f5421a = null;
            }
            m605a(z2);
        }

        /* renamed from: a */
        private void m605a(boolean z) {
            if (this.f5421a != null) {
                TXCLog.m2913i("TXCVideoDecoder", "play:decode: start decode ignore hwdec: " + this.f5424d);
                return;
            }
            if (this.f5424d) {
                this.f5421a = new TXCVideoMediaCodecDecoder();
            } else {
                this.f5421a = new TXCVideoFfmpegDecoder();
            }
            this.f5421a.setListener(this.f5422b);
            this.f5421a.setNotifyListener(this.f5423c);
            this.f5421a.config(this.f5426f);
            this.f5421a.start(this.f5427g, this.f5428h, z, this.f5425e);
            TXCLog.m2911w("TXCVideoDecoder", "play:decode: start decode hwdec: " + this.f5424d + ", hevc: " + this.f5425e);
        }
    }
}
