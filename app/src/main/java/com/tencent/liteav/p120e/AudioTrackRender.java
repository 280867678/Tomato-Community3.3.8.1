package com.tencent.liteav.p120e;

import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Build;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingDeque;

/* renamed from: com.tencent.liteav.e.b */
/* loaded from: classes3.dex */
public class AudioTrackRender {

    /* renamed from: a */
    private volatile AudioTrack f3577a;

    /* renamed from: b */
    private volatile Frame f3578b;

    /* renamed from: c */
    private LinkedBlockingDeque<Frame> f3579c = new LinkedBlockingDeque<>();

    /* renamed from: d */
    private C3437b f3580d;

    /* renamed from: e */
    private volatile AbstractC3436a f3581e;

    /* renamed from: f */
    private int f3582f;

    /* renamed from: g */
    private int f3583g;

    /* compiled from: AudioTrackRender.java */
    /* renamed from: com.tencent.liteav.e.b$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3436a {
        /* renamed from: a */
        void mo1485a(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2185b(Frame frame) {
        if (this.f3578b == null) {
            this.f3578b = frame;
        }
        if (frame.m2327f() == 4) {
            m2182e();
            return;
        }
        byte[] array = frame.m2338b().array();
        int remaining = frame.m2338b().remaining();
        if (remaining != 0) {
            try {
                if (this.f3577a != null && this.f3577a.getPlayState() == 3) {
                    this.f3577a.write(array, frame.m2338b().arrayOffset(), remaining);
                    this.f3579c.remove();
                    if (this.f3581e != null) {
                        this.f3581e.mo1485a(this.f3579c.size());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.f3578b = frame;
    }

    /* renamed from: a */
    public void m2189a(AbstractC3436a abstractC3436a) {
        this.f3581e = abstractC3436a;
    }

    /* renamed from: a */
    public void m2193a() {
        try {
            if (this.f3577a == null) {
                return;
            }
            this.f3577a.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    public void m2186b() {
        try {
            if (this.f3577a == null || this.f3577a.getPlayState() == 3) {
                return;
            }
            this.f3577a.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: c */
    public void m2184c() {
        m2192a(this.f3583g, this.f3582f);
    }

    /* renamed from: a */
    public void m2191a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            m2182e();
        } else if (Build.VERSION.SDK_INT < 16) {
        } else {
            int integer = mediaFormat.getInteger("sample-rate");
            int integer2 = mediaFormat.getInteger("channel-count");
            if (this.f3582f != integer || this.f3583g != integer2) {
                m2182e();
            }
            this.f3582f = integer;
            this.f3583g = integer2;
            TXCLog.m2913i("AudioTrackRender", "setAudioFormat sampleRate=" + integer + ",channelCount=" + integer2);
        }
    }

    /* renamed from: a */
    public void m2190a(Frame frame) {
        C3437b c3437b = this.f3580d;
        if (c3437b == null || !c3437b.isAlive() || this.f3580d.isInterrupted()) {
            this.f3580d = new C3437b(this);
            this.f3580d.start();
        }
        this.f3579c.add(frame);
        if (this.f3581e != null) {
            this.f3581e.mo1485a(this.f3579c.size());
        }
    }

    /* renamed from: a */
    private boolean m2192a(int i, int i2) {
        int i3;
        int i4;
        boolean z;
        int i5;
        int i6 = i == 1 ? 4 : (i == 2 || i == 3) ? 12 : (i == 4 || i == 5) ? 204 : (i == 6 || i == 7) ? 252 : i == 8 ? 6396 : 0;
        if (this.f3577a == null) {
            int minBufferSize = AudioTrack.getMinBufferSize(i2, i6, 2);
            try {
                i3 = minBufferSize;
                i4 = i6;
                z = true;
                try {
                    this.f3577a = new AudioTrack(3, i2, i6, 2, i3, 1);
                    this.f3577a.play();
                    return false;
                } catch (IllegalArgumentException e) {
                    e = e;
                    i5 = i3;
                    e.printStackTrace();
                    TXCLog.m2914e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + e + ", sampleRate: " + i2 + ", channelType: " + i4 + ", minBufferLen: " + i5);
                    this.f3577a = null;
                    return z;
                } catch (IllegalStateException e2) {
                    e = e2;
                    e.printStackTrace();
                    TXCLog.m2914e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + e + ", sampleRate: " + i2 + ", channelType: " + i4 + ", minBufferLen: " + i3);
                    if (this.f3577a != null) {
                        this.f3577a.release();
                    }
                    this.f3577a = null;
                    return z;
                }
            } catch (IllegalArgumentException e3) {
                e = e3;
                i5 = minBufferSize;
                i4 = i6;
                z = true;
            } catch (IllegalStateException e4) {
                e = e4;
                i3 = minBufferSize;
                i4 = i6;
                z = true;
            }
        } else {
            return false;
        }
    }

    /* renamed from: e */
    private void m2182e() {
        try {
            if (this.f3577a != null) {
                this.f3577a.stop();
                this.f3577a.release();
            }
            this.f3577a = null;
        } catch (Exception e) {
            this.f3577a = null;
            TXCLog.m2914e("AudioTrackRender", "audio track stop exception: " + e);
        }
    }

    /* renamed from: d */
    public void m2183d() {
        this.f3579c.clear();
        C3437b c3437b = this.f3580d;
        if (c3437b != null) {
            c3437b.m2181a();
            this.f3580d = null;
        }
        TXCLog.m2915d("AudioTrackRender", "mPlayPCMThread:" + this.f3580d);
        this.f3578b = null;
        m2182e();
    }

    /* compiled from: AudioTrackRender.java */
    /* renamed from: com.tencent.liteav.e.b$b */
    /* loaded from: classes3.dex */
    private static class C3437b extends Thread {

        /* renamed from: a */
        private WeakReference<AudioTrackRender> f3584a;

        public C3437b(AudioTrackRender audioTrackRender) {
            super("PlayPCMThread for Video Editer");
            this.f3584a = new WeakReference<>(audioTrackRender);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    Frame m2179b = m2179b();
                    if (m2179b != null) {
                        m2180a(m2179b);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        /* renamed from: a */
        private void m2180a(Frame frame) {
            m2178c();
            this.f3584a.get().m2185b(frame);
        }

        /* renamed from: b */
        private Frame m2179b() throws InterruptedException {
            m2178c();
            return (Frame) this.f3584a.get().f3579c.peek();
        }

        /* renamed from: c */
        private void m2178c() {
            if (this.f3584a.get() != null) {
                return;
            }
            throw new RuntimeException("can't reach the object: AudioTrackRender");
        }

        /* renamed from: a */
        public void m2181a() {
            interrupt();
            this.f3584a.clear();
            this.f3584a = null;
        }
    }
}
