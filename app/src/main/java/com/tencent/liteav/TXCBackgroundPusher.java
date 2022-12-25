package com.tencent.liteav;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.p002v4.view.ViewCompat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.TXIVideoPreprocessorListener;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.a */
/* loaded from: classes3.dex */
public class TXCBackgroundPusher implements TXIVideoPreprocessorListener, TXIVideoEncoderListener {

    /* renamed from: a */
    private static final String f2001a = "a";

    /* renamed from: d */
    private HandlerC3295a f2004d;

    /* renamed from: e */
    private HandlerThread f2005e;

    /* renamed from: g */
    private TXCVideoEncoder f2007g;

    /* renamed from: h */
    private TXSNALPacket f2008h;

    /* renamed from: n */
    private WeakReference<AbstractC3296b> f2014n;

    /* renamed from: b */
    private int f2002b = 300;

    /* renamed from: c */
    private long f2003c = 0;

    /* renamed from: f */
    private boolean f2006f = false;

    /* renamed from: i */
    private TXCVideoPreprocessor f2009i = null;

    /* renamed from: j */
    private ByteBuffer f2010j = null;

    /* renamed from: k */
    private Bitmap f2011k = null;

    /* renamed from: l */
    private int f2012l = 0;

    /* renamed from: m */
    private int f2013m = 0;

    /* compiled from: TXCBackgroundPusher.java */
    /* renamed from: com.tencent.liteav.a$b */
    /* loaded from: classes3.dex */
    public interface AbstractC3296b {
        /* renamed from: a */
        void mo2595a();

        /* renamed from: a */
        void mo2584a(Bitmap bitmap, ByteBuffer byteBuffer, int i, int i2);

        /* renamed from: a */
        void mo2570a(TXCVideoEncoder tXCVideoEncoder);
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeFormat(MediaFormat mediaFormat) {
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public int willAddWatermark(int i, int i2, int i3) {
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(int i, int i2, int i3, long j) {
        TXCLog.m2911w(f2001a, "bkgpush: got texture");
        TXCVideoEncoder tXCVideoEncoder = this.f2007g;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m431a(i, i2, i3, TXCTimeUtil.getTimeTick());
        }
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
        AbstractC3296b abstractC3296b;
        this.f2008h = tXSNALPacket;
        String str = f2001a;
        StringBuilder sb = new StringBuilder();
        sb.append("bkgpush: got nal type: ");
        Object obj = tXSNALPacket;
        if (tXSNALPacket != null) {
            obj = Integer.valueOf(tXSNALPacket.nalType);
        }
        sb.append(obj);
        TXCLog.m2911w(str, sb.toString());
        TXCVideoEncoder tXCVideoEncoder = this.f2007g;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
            TXCVideoEncoder tXCVideoEncoder2 = this.f2007g;
            try {
                if (this.f2014n == null || (abstractC3296b = this.f2014n.get()) == null) {
                    return;
                }
                abstractC3296b.mo2570a(tXCVideoEncoder2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TXCBackgroundPusher(AbstractC3296b abstractC3296b) {
        this.f2014n = null;
        this.f2014n = new WeakReference<>(abstractC3296b);
    }

    /* renamed from: a */
    public void m3491a(int i, int i2) {
        if (this.f2006f) {
            TXCLog.m2911w(f2001a, "bkgpush: start background publish return when started");
            return;
        }
        this.f2006f = true;
        m3486b(i, i2);
        m3484c();
        HandlerC3295a handlerC3295a = this.f2004d;
        if (handlerC3295a != null) {
            handlerC3295a.sendEmptyMessageDelayed(1001, this.f2002b);
        }
        String str = f2001a;
        TXCLog.m2911w(str, "bkgpush: start background publish with time:" + ((this.f2003c - System.currentTimeMillis()) / 1000) + ", interval:" + this.f2002b);
    }

    /* renamed from: a */
    public void m3490a(int i, int i2, Bitmap bitmap, int i3, int i4) {
        if (this.f2006f) {
            TXCLog.m2911w(f2001a, "bkgpush: start background publish return when started");
            return;
        }
        if (bitmap == null) {
            try {
                TXCLog.m2911w(f2001a, "bkgpush: background publish img is empty, add default img");
                ColorDrawable colorDrawable = new ColorDrawable(ViewCompat.MEASURED_STATE_MASK);
                Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
                colorDrawable.draw(new Canvas(createBitmap));
                bitmap = createBitmap;
            } catch (Error unused) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TXCLog.m2911w(f2001a, "bkgpush: generate bitmap");
        this.f2011k = bitmap;
        this.f2012l = i3;
        this.f2013m = i4;
        m3491a(i, i2);
    }

    /* renamed from: a */
    public void m3492a() {
        this.f2006f = false;
        this.f2010j = null;
        this.f2011k = null;
        TXCLog.m2911w(f2001a, "bkgpush: stop background publish");
        m3482d();
    }

    /* renamed from: b */
    private void m3486b(int i, int i2) {
        if (i > 0) {
            if (i >= 8) {
                i = 8;
            } else if (i <= 3) {
                i = 3;
            }
            this.f2002b = 1000 / i;
        } else {
            this.f2002b = 200;
        }
        long j = i2;
        if (i2 > 0) {
            this.f2003c = System.currentTimeMillis() + (j * 1000);
        } else {
            this.f2003c = System.currentTimeMillis() + 300000;
        }
    }

    /* renamed from: c */
    private void m3484c() {
        m3482d();
        this.f2005e = new HandlerThread("TXImageCapturer");
        this.f2005e.start();
        this.f2004d = new HandlerC3295a(this.f2005e.getLooper(), this.f2002b, this.f2003c);
    }

    /* renamed from: d */
    private void m3482d() {
        HandlerC3295a handlerC3295a = this.f2004d;
        if (handlerC3295a != null) {
            handlerC3295a.removeCallbacksAndMessages(null);
            this.f2004d = null;
        }
        HandlerThread handlerThread = this.f2005e;
        if (handlerThread != null) {
            handlerThread.quit();
            this.f2005e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m3481e() {
        AbstractC3296b abstractC3296b;
        try {
            if (this.f2014n == null || !this.f2006f || (abstractC3296b = this.f2014n.get()) == null) {
                return;
            }
            Bitmap bitmap = this.f2011k;
            ByteBuffer byteBuffer = this.f2010j;
            if (byteBuffer == null && bitmap != null) {
                byteBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 4);
                bitmap.copyPixelsToBuffer(byteBuffer);
                byteBuffer.rewind();
                this.f2010j = byteBuffer;
            }
            if (bitmap == null || byteBuffer == null) {
                return;
            }
            abstractC3296b.mo2584a(bitmap, byteBuffer, this.f2012l, this.f2013m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCBackgroundPusher.java */
    /* renamed from: com.tencent.liteav.a$a */
    /* loaded from: classes3.dex */
    public class HandlerC3295a extends Handler {

        /* renamed from: b */
        private int f2016b;

        /* renamed from: c */
        private long f2017c;

        public HandlerC3295a(Looper looper, int i, long j) {
            super(looper);
            this.f2016b = 300;
            this.f2017c = 0L;
            this.f2016b = i;
            this.f2017c = j;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1001) {
                try {
                    TXCBackgroundPusher.this.m3481e();
                    if (System.currentTimeMillis() >= this.f2017c) {
                        TXCLog.m2911w(TXCBackgroundPusher.f2001a, "bkgpush:stop background publish when timeout");
                        if (TXCBackgroundPusher.this.f2014n == null || !TXCBackgroundPusher.this.f2006f) {
                            return;
                        }
                        AbstractC3296b abstractC3296b = (AbstractC3296b) TXCBackgroundPusher.this.f2014n.get();
                        if (abstractC3296b != null) {
                            abstractC3296b.mo2595a();
                        }
                        TXCBackgroundPusher.this.f2006f = false;
                        return;
                    }
                    sendEmptyMessageDelayed(1001, this.f2016b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
