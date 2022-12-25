package com.tencent.liteav.audio.impl.Play;

import android.content.Context;
import android.media.AudioTrack;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.impl.TXCAudioRouteMgr;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* renamed from: com.tencent.liteav.audio.impl.Play.d */
/* loaded from: classes3.dex */
public class TXCMultAudioTrackPlayer {

    /* renamed from: c */
    private C3300a f2081c;

    /* renamed from: d */
    private boolean f2082d = false;

    /* renamed from: e */
    private volatile boolean f2083e = false;

    /* renamed from: f */
    private Context f2084f = null;

    /* renamed from: g */
    private int f2085g = TXEAudioDef.TXE_AUDIO_MODE_SPEAKER;

    /* renamed from: h */
    private volatile boolean f2086h = false;

    /* renamed from: i */
    private int f2087i = TXEAudioTypeDef.f2318e;

    /* renamed from: j */
    private int f2088j = TXEAudioTypeDef.f2320g;

    /* renamed from: k */
    private int f2089k = TXEAudioTypeDef.f2321h;

    /* renamed from: b */
    private static final String f2080b = "AudioCenter:" + TXCMultAudioTrackPlayer.class.getSimpleName();

    /* renamed from: a */
    static TXCMultAudioTrackPlayer f2079a = new TXCMultAudioTrackPlayer();

    /* compiled from: TXCMultAudioTrackPlayer.java */
    /* renamed from: com.tencent.liteav.audio.impl.Play.d$a */
    /* loaded from: classes3.dex */
    class C3300a extends Thread {

        /* renamed from: b */
        volatile boolean f2091b = false;

        public C3300a(String str) {
            super(str);
        }

        /* renamed from: a */
        public void m3412a() {
            this.f2091b = true;
        }

        /* renamed from: b */
        public void m3411b() {
            this.f2091b = false;
        }
    }

    private TXCMultAudioTrackPlayer() {
    }

    /* renamed from: a */
    public static TXCMultAudioTrackPlayer m3425a() {
        return f2079a;
    }

    /* renamed from: b */
    public void m3421b() {
        TXCLog.m2911w(f2080b, "mult-track-player start!");
        if (this.f2083e) {
            TXCLog.m2914e(f2080b, "mult-track-player can not start because of has started!");
        } else if (this.f2087i == 0 || this.f2088j == 0) {
            String str = f2080b;
            TXCLog.m2914e(str, "strat mult-track-player failed with invalid audio info , samplerate:" + this.f2087i + ", channels:" + this.f2088j);
        } else {
            this.f2083e = true;
            if (this.f2081c == null) {
                this.f2081c = new C3300a("AUDIO_TRACK") { // from class: com.tencent.liteav.audio.impl.Play.d.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        m3412a();
                        try {
                            int i = TXCMultAudioTrackPlayer.this.f2088j == 1 ? 2 : 3;
                            int i2 = TXCMultAudioTrackPlayer.this.f2089k == 8 ? 3 : 2;
                            AudioTrack audioTrack = new AudioTrack(3, TXCMultAudioTrackPlayer.this.f2087i, i, i2, AudioTrack.getMinBufferSize(TXCMultAudioTrackPlayer.this.f2087i, i, i2), 1);
                            String str2 = TXCMultAudioTrackPlayer.f2080b;
                            TXCLog.m2913i(str2, "create audio track, samplerate:" + TXCMultAudioTrackPlayer.this.f2087i + ", channels:" + TXCMultAudioTrackPlayer.this.f2088j + ", bits:" + TXCMultAudioTrackPlayer.this.f2089k);
                            try {
                                audioTrack.play();
                                TXCMultAudioTrackPlayer.this.f2086h = true;
                                TXCMultAudioTrackPlayer tXCMultAudioTrackPlayer = TXCMultAudioTrackPlayer.this;
                                tXCMultAudioTrackPlayer.m3424a(tXCMultAudioTrackPlayer.f2084f, TXCMultAudioTrackPlayer.this.f2085g);
                                int i3 = 100;
                                int i4 = 0;
                                while (this.f2091b) {
                                    byte[] nativeGetMixedTracksData = TXCAudioBasePlayController.nativeGetMixedTracksData(TXCMultAudioTrackPlayer.this.f2088j * 2048);
                                    if (nativeGetMixedTracksData != null && nativeGetMixedTracksData.length > 0) {
                                        if (TXCMultAudioTrackPlayer.this.f2082d) {
                                            Arrays.fill(nativeGetMixedTracksData, (byte) 0);
                                        }
                                        if (i3 != 0 && i4 < 800) {
                                            short[] sArr = new short[nativeGetMixedTracksData.length / 2];
                                            ByteBuffer.wrap(nativeGetMixedTracksData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(sArr);
                                            for (int i5 = 0; i5 < sArr.length; i5++) {
                                                sArr[i5] = (short) (sArr[i5] / i3);
                                            }
                                            ByteBuffer.wrap(nativeGetMixedTracksData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(sArr);
                                            i4 += nativeGetMixedTracksData.length / ((TXCMultAudioTrackPlayer.this.f2087i * 2) / 1000);
                                            i3 = (i3 * (800 - i4)) / 800;
                                        }
                                        audioTrack.write(nativeGetMixedTracksData, 0, nativeGetMixedTracksData.length);
                                    } else {
                                        try {
                                            Thread.sleep(5L);
                                        } catch (InterruptedException unused) {
                                        }
                                    }
                                }
                                try {
                                    audioTrack.pause();
                                    audioTrack.flush();
                                    audioTrack.stop();
                                    audioTrack.release();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                TXCLog.m2914e(TXCMultAudioTrackPlayer.f2080b, "mult-player thread stop finish!");
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                };
                this.f2081c.start();
            }
            TXCLog.m2911w(f2080b, "mult-track-player thread start finish!");
        }
    }

    /* renamed from: c */
    public void m3419c() {
        TXCLog.m2911w(f2080b, "mult-track-player stop!");
        if (!this.f2083e) {
            TXCLog.m2911w(f2080b, "mult-track-player can not stop because of not started yet!");
            return;
        }
        C3300a c3300a = this.f2081c;
        if (c3300a != null) {
            c3300a.m3411b();
            this.f2081c = null;
        }
        this.f2085g = TXEAudioDef.TXE_AUDIO_MODE_SPEAKER;
        this.f2084f = null;
        this.f2086h = false;
        this.f2083e = false;
        TXCLog.m2911w(f2080b, "mult-track-player stop finish!");
    }

    /* renamed from: a */
    public synchronized void m3424a(Context context, int i) {
        this.f2084f = context;
        this.f2085g = i;
        if (this.f2086h) {
            TXCAudioRouteMgr.m3371a().m3370a(i);
        }
    }

    /* renamed from: d */
    public boolean m3417d() {
        return this.f2083e;
    }
}
