package com.tencent.liteav;

import android.content.Context;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.s */
/* loaded from: classes3.dex */
public abstract class TXIPlayer {

    /* renamed from: c */
    protected Context f5203c;

    /* renamed from: e */
    protected WeakReference<TXINotifyListener> f5205e;

    /* renamed from: b */
    protected TXCPlayerConfig f5202b = null;

    /* renamed from: d */
    protected TXCloudVideoView f5204d = null;

    /* renamed from: a */
    public int mo831a(String str) {
        return -1;
    }

    /* renamed from: a */
    public abstract int mo830a(String str, int i);

    /* renamed from: a */
    public abstract int mo829a(boolean z);

    /* renamed from: a */
    public abstract void mo841a(int i);

    /* renamed from: a */
    public void mo840a(int i, int i2) {
    }

    /* renamed from: a */
    public void mo839a(Context context, int i) {
    }

    /* renamed from: a */
    public void mo838a(Surface surface) {
    }

    /* renamed from: a */
    public void mo835a(TXIVideoRawDataListener tXIVideoRawDataListener) {
    }

    /* renamed from: a */
    public void mo834a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
    }

    /* renamed from: a */
    public void mo832a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
    }

    /* renamed from: a */
    public boolean mo828a(byte[] bArr) {
        return false;
    }

    /* renamed from: b */
    public abstract void mo824b(int i);

    /* renamed from: b */
    public abstract void mo823b(boolean z);

    /* renamed from: c */
    public abstract int mo821c(int i);

    /* renamed from: c */
    public abstract boolean mo822c();

    /* renamed from: d */
    public TextureView mo819d() {
        return null;
    }

    /* renamed from: e */
    public abstract int mo818e();

    /* renamed from: f */
    public boolean mo817f() {
        return false;
    }

    /* renamed from: g */
    public void mo816g() {
    }

    public TXIPlayer(Context context) {
        this.f5203c = null;
        if (context != null) {
            this.f5203c = context.getApplicationContext();
        }
    }

    /* renamed from: p */
    public TXCPlayerConfig m815p() {
        return this.f5202b;
    }

    /* renamed from: a */
    public void mo836a(TXCPlayerConfig tXCPlayerConfig) {
        this.f5202b = tXCPlayerConfig;
        if (this.f5202b == null) {
            this.f5202b = new TXCPlayerConfig();
        }
    }

    /* renamed from: a */
    public void mo842a() {
        TXCLog.m2911w("TXIPlayer", "pause not support");
    }

    /* renamed from: b */
    public void mo826b() {
        TXCLog.m2911w("TXIPlayer", "resume not support");
    }

    /* renamed from: a_ */
    public void mo827a_(int i) {
        TXCLog.m2911w("TXIPlayer", "seek not support");
    }

    /* renamed from: a */
    public void mo833a(TXCloudVideoView tXCloudVideoView) {
        this.f5204d = tXCloudVideoView;
    }

    /* renamed from: a */
    public void m837a(TXINotifyListener tXINotifyListener) {
        this.f5205e = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: c */
    public void mo820c(boolean z) {
        TXCLog.m2911w("TXIPlayer", "autoPlay not implement");
    }

    /* renamed from: b */
    public void mo825b(float f) {
        TXCLog.m2911w("TXIPlayer", "rate not implement");
    }
}
