package com.tencent.liteav.p120e;

import android.annotation.TargetApi;
import android.os.HandlerThread;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.RepeatPlayConfig;
import com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater;
import com.tencent.liteav.p124i.TXCVideoEditConstants;

@TargetApi(16)
/* renamed from: com.tencent.liteav.e.v */
/* loaded from: classes3.dex */
public class VideoDecAndDemuxGenerate extends BasicVideoDecDemuxGenerater {

    /* renamed from: V */
    private final String f3734V = "VideoDecAndDemuxGenerate";

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: a */
    public void mo2045a(boolean z) {
    }

    public VideoDecAndDemuxGenerate() {
        this.f3638z = new HandlerThread("video_handler_thread");
        this.f3638z.start();
        this.f3637y = new BasicVideoDecDemuxGenerater.HandlerC3439b(this.f3638z.getLooper());
        this.f3598B = new HandlerThread("audio_handler_thread");
        this.f3598B.start();
        this.f3597A = new BasicVideoDecDemuxGenerater.HandlerC3438a(this.f3598B.getLooper());
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: l */
    public synchronized void mo2044l() {
        if (this.f3636x.get() != 2 && this.f3636x.get() != 3) {
            this.f3636x.set(2);
            this.f3612P.set(false);
            this.f3613Q.getAndSet(false);
            this.f3632t.getAndSet(false);
            this.f3633u.getAndSet(false);
            this.f3634v.getAndSet(false);
            this.f3635w.getAndSet(false);
            this.f3593i = 0;
            TXCVideoEditConstants.C3518h m2480b = RepeatPlayConfig.m2482a().m2480b();
            if (m2480b == null) {
                m2155b(0L, 0L);
            } else {
                m2155b(m2480b.f4383a * 1000, m2480b.f4384b * 1000);
            }
            m2161a(this.f3591g.get());
            this.f3637y.sendEmptyMessage(101);
            if (m2164h()) {
                this.f3597A.sendEmptyMessage(201);
            }
            return;
        }
        TXCLog.m2914e("VideoDecAndDemuxGenerate", "start ignore, mCurrentState = " + this.f3636x.get());
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: m */
    public synchronized void mo2043m() {
        if (this.f3636x.get() == 1) {
            TXCLog.m2914e("VideoDecAndDemuxGenerate", "stop ignore, mCurrentState is STATE_INIT");
            return;
        }
        this.f3636x.set(1);
        this.f3637y.sendEmptyMessage(103);
        if (m2164h()) {
            this.f3597A.sendEmptyMessage(203);
        }
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemux
    /* renamed from: k */
    public void mo2017k() {
        HandlerThread handlerThread = this.f3638z;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        HandlerThread handlerThread2 = this.f3598B;
        if (handlerThread2 != null) {
            handlerThread2.quit();
        }
    }
}
