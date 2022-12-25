package com.tencent.liteav.p120e;

import android.os.HandlerThread;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.tencent.liteav.e.w */
/* loaded from: classes3.dex */
public class VideoDecAndDemuxGenerateGivenTimes extends BasicVideoDecDemuxGenerater {

    /* renamed from: V */
    private final String f3735V = "VideoDecAndDemuxGenerateGivenTimes";

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: a */
    public void mo2045a(boolean z) {
    }

    public VideoDecAndDemuxGenerateGivenTimes() {
        this.f3615S = 0;
        this.f3616T = 0;
        this.f3617U = new AtomicBoolean(true);
        this.f3614R = new ArrayList();
        this.f3638z = new HandlerThread("video_handler_thread");
        this.f3638z.start();
        this.f3637y = new BasicVideoDecDemuxGenerater.HandlerC3439b(this.f3638z.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: l */
    public void mo2044l() {
        if (this.f3636x.get() == 2) {
            TXCLog.m2914e("VideoDecAndDemuxGenerateGivenTimes", "start ignore, state = " + this.f3636x.get());
            return;
        }
        m2161a(this.f3591g.get());
        this.f3637y.sendEmptyMessage(5);
        this.f3636x.set(2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: m */
    public void mo2043m() {
        if (this.f3636x.get() == 1) {
            TXCLog.m2914e("VideoDecAndDemuxGenerateGivenTimes", "stop ignore, mCurrentState = " + this.f3636x.get());
            return;
        }
        this.f3637y.sendEmptyMessage(7);
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemuxGenerater
    /* renamed from: p */
    public synchronized void mo2042p() {
        this.f3617U.set(true);
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemux
    /* renamed from: k */
    public void mo2017k() {
        HandlerThread handlerThread = this.f3638z;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }
}
