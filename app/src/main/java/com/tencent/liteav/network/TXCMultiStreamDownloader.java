package com.tencent.liteav.network;

import android.os.Bundle;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/* renamed from: com.tencent.liteav.network.d */
/* loaded from: classes3.dex */
public class TXCMultiStreamDownloader implements TXIStreamDownloaderListener {

    /* renamed from: f */
    private AbstractC3576a f4835f;

    /* renamed from: a */
    private TXIStreamDownloaderListener f4830a = null;

    /* renamed from: b */
    private C3577b f4831b = null;

    /* renamed from: c */
    private long f4832c = 0;

    /* renamed from: d */
    private long f4833d = 0;

    /* renamed from: e */
    private C3577b f4834e = null;

    /* renamed from: g */
    private long f4836g = 0;

    /* renamed from: h */
    private long f4837h = 0;

    /* compiled from: TXCMultiStreamDownloader.java */
    /* renamed from: com.tencent.liteav.network.d$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3576a {
        void onSwitchFinish(TXIStreamDownloader tXIStreamDownloader, boolean z);
    }

    /* renamed from: a */
    public void m1149a(TXIStreamDownloaderListener tXIStreamDownloaderListener) {
        this.f4830a = tXIStreamDownloaderListener;
    }

    public TXCMultiStreamDownloader(AbstractC3576a abstractC3576a) {
        this.f4835f = abstractC3576a;
    }

    /* renamed from: a */
    public void m1154a() {
        C3577b c3577b = this.f4831b;
        if (c3577b != null) {
            c3577b.m1141b(0L);
        }
        C3577b c3577b2 = this.f4834e;
        if (c3577b2 != null) {
            c3577b2.m1141b(0L);
        }
    }

    /* renamed from: a */
    public void m1152a(TXIStreamDownloader tXIStreamDownloader, TXIStreamDownloader tXIStreamDownloader2, long j, long j2, String str) {
        this.f4832c = j;
        this.f4833d = j2;
        this.f4831b = new C3577b(tXIStreamDownloader, this);
        this.f4831b.m1142a(this);
        Vector<TXCStreamPlayUrl> vector = new Vector<>();
        vector.add(new TXCStreamPlayUrl(str, false));
        tXIStreamDownloader2.setOriginUrl(str);
        tXIStreamDownloader2.startDownload(vector, false, false, tXIStreamDownloader.mEnableMessage);
        this.f4834e = new C3577b(tXIStreamDownloader2, this);
        this.f4834e.m1145a(this.f4832c);
    }

    /* renamed from: b */
    void m1148b() {
        this.f4831b.m1142a((TXIStreamDownloaderListener) null);
        this.f4834e.m1142a(this);
        this.f4831b = this.f4834e;
        this.f4834e = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" stream_switch end at ");
        sb.append(this.f4832c);
        sb.append(" stop ts ");
        sb.append(this.f4837h);
        sb.append(" start ts ");
        sb.append(this.f4836g);
        sb.append(" diff ts ");
        long j = this.f4837h;
        long j2 = this.f4836g;
        sb.append(j > j2 ? j - j2 : j2 - j);
        TXCLog.m2911w("TXCMultiStreamDownloader", sb.toString());
    }

    /* renamed from: a */
    void m1151a(TXIStreamDownloader tXIStreamDownloader, boolean z) {
        AbstractC3576a abstractC3576a = this.f4835f;
        if (abstractC3576a != null) {
            abstractC3576a.onSwitchFinish(tXIStreamDownloader, z);
        }
    }

    /* renamed from: a */
    long m1153a(long j) {
        C3577b c3577b = this.f4831b;
        if (c3577b != null) {
            c3577b.m1141b(this.f4832c);
        }
        TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch delay stop begin from " + this.f4832c);
        return this.f4832c;
    }

    /* renamed from: b */
    void m1147b(long j) {
        this.f4836g = j;
    }

    /* renamed from: c */
    void m1146c(long j) {
        this.f4837h = j;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullAudio(TXSAudioPacket tXSAudioPacket) {
        TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4830a;
        if (tXIStreamDownloaderListener != null) {
            tXIStreamDownloaderListener.onPullAudio(tXSAudioPacket);
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        long j = tXSNALPacket.pts;
        this.f4832c = j;
        if (tXSNALPacket.nalType == 0) {
            this.f4833d = j;
        }
        TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4830a;
        if (tXIStreamDownloaderListener != null) {
            tXIStreamDownloaderListener.onPullNAL(tXSNALPacket);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCMultiStreamDownloader.java */
    /* renamed from: com.tencent.liteav.network.d$b */
    /* loaded from: classes3.dex */
    public static class C3577b implements TXINotifyListener, TXIStreamDownloaderListener {

        /* renamed from: a */
        private final int f4838a = 2;

        /* renamed from: b */
        private long f4839b = 0;

        /* renamed from: c */
        private long f4840c = 0;

        /* renamed from: d */
        private int f4841d = 0;

        /* renamed from: e */
        private boolean f4842e = false;

        /* renamed from: f */
        private long f4843f = 0;

        /* renamed from: g */
        private long f4844g = 0;

        /* renamed from: h */
        private long f4845h = 0;

        /* renamed from: i */
        private ArrayList<TXSNALPacket> f4846i = new ArrayList<>();

        /* renamed from: j */
        private ArrayList<TXSAudioPacket> f4847j = new ArrayList<>();

        /* renamed from: k */
        private TXIStreamDownloader f4848k;

        /* renamed from: l */
        private WeakReference<TXCMultiStreamDownloader> f4849l;

        /* renamed from: m */
        private TXIStreamDownloaderListener f4850m;

        public C3577b(TXIStreamDownloader tXIStreamDownloader, TXCMultiStreamDownloader tXCMultiStreamDownloader) {
            this.f4848k = null;
            this.f4849l = new WeakReference<>(tXCMultiStreamDownloader);
            this.f4848k = tXIStreamDownloader;
            this.f4848k.setListener(this);
        }

        /* renamed from: a */
        public void m1145a(long j) {
            this.f4841d = 0;
            this.f4839b = j;
            this.f4848k.setListener(this);
            this.f4848k.setNotifyListener(this);
        }

        /* renamed from: b */
        public void m1141b(long j) {
            this.f4839b = 0L;
            this.f4843f = j;
            this.f4845h = 0L;
            this.f4844g = 0L;
            TXIStreamDownloader tXIStreamDownloader = this.f4848k;
            if (tXIStreamDownloader == null || this.f4843f != 0) {
                return;
            }
            tXIStreamDownloader.stopDownload();
            this.f4848k = null;
        }

        /* renamed from: a */
        public void m1142a(TXIStreamDownloaderListener tXIStreamDownloaderListener) {
            this.f4850m = tXIStreamDownloaderListener;
        }

        @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
        public void onPullAudio(TXSAudioPacket tXSAudioPacket) {
            if (this.f4839b > 0) {
                m1144a(tXSAudioPacket);
            } else if (this.f4843f > 0) {
                m1140b(tXSAudioPacket);
            } else {
                TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4850m;
                if (tXIStreamDownloaderListener == null) {
                    return;
                }
                tXIStreamDownloaderListener.onPullAudio(tXSAudioPacket);
            }
        }

        @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
        public void onPullNAL(TXSNALPacket tXSNALPacket) {
            if (tXSNALPacket == null) {
                return;
            }
            if (this.f4839b > 0) {
                m1143a(tXSNALPacket);
            } else if (this.f4843f > 0) {
                m1139b(tXSNALPacket);
            } else {
                TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4850m;
                if (tXIStreamDownloaderListener == null) {
                    return;
                }
                tXIStreamDownloaderListener.onPullNAL(tXSNALPacket);
            }
        }

        /* renamed from: a */
        private void m1144a(TXSAudioPacket tXSAudioPacket) {
            if (tXSAudioPacket == null) {
                return;
            }
            long j = tXSAudioPacket.timestamp;
            long j2 = this.f4840c;
            if (j < j2 || j < this.f4839b) {
                return;
            }
            TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4850m;
            if (tXIStreamDownloaderListener != null && j2 > 0 && j >= j2) {
                tXIStreamDownloaderListener.onPullAudio(tXSAudioPacket);
            } else {
                this.f4847j.add(tXSAudioPacket);
            }
        }

        /* renamed from: b */
        private void m1140b(TXSAudioPacket tXSAudioPacket) {
            if (this.f4845h > 0) {
                return;
            }
            long j = this.f4844g;
            if (j > 0 && tXSAudioPacket != null) {
                long j2 = tXSAudioPacket.timestamp;
                if (j2 >= j) {
                    this.f4845h = j2;
                    return;
                }
            }
            TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4850m;
            if (tXIStreamDownloaderListener == null) {
                return;
            }
            tXIStreamDownloaderListener.onPullAudio(tXSAudioPacket);
        }

        /* renamed from: a */
        private void m1143a(TXSNALPacket tXSNALPacket) {
            TXCMultiStreamDownloader tXCMultiStreamDownloader = this.f4849l.get();
            if (tXSNALPacket.nalType == 0 && !this.f4842e) {
                this.f4841d++;
                if (tXCMultiStreamDownloader != null && (tXCMultiStreamDownloader.f4833d <= tXSNALPacket.pts || this.f4841d == 2)) {
                    this.f4839b = tXCMultiStreamDownloader.m1153a(tXSNALPacket.pts);
                    this.f4842e = true;
                }
                if (tXCMultiStreamDownloader != null) {
                    TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch pre start begin gop " + this.f4841d + " last iframe ts " + tXCMultiStreamDownloader.f4833d + " pts " + tXSNALPacket.pts + " from " + this.f4839b + " type " + tXSNALPacket.nalType);
                }
            }
            if (!this.f4842e) {
                return;
            }
            if (tXCMultiStreamDownloader != null) {
                tXCMultiStreamDownloader.m1147b(tXSNALPacket.pts);
            }
            long j = tXSNALPacket.pts;
            if (j < this.f4839b) {
                return;
            }
            if (tXSNALPacket.nalType == 0 && this.f4840c == 0) {
                this.f4840c = j;
                TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch pre start end " + tXSNALPacket.pts + " from " + this.f4839b + " type " + tXSNALPacket.nalType);
            }
            if (this.f4840c <= 0) {
                return;
            }
            if (this.f4850m != null) {
                if (!this.f4847j.isEmpty()) {
                    Iterator<TXSAudioPacket> it2 = this.f4847j.iterator();
                    while (it2.hasNext()) {
                        TXSAudioPacket next = it2.next();
                        if (next.timestamp >= this.f4840c) {
                            TXCLog.m2913i("TXCMultiStreamDownloader", " stream_switch pre start cache audio pts " + next.timestamp + " from " + this.f4840c);
                            this.f4850m.onPullAudio(next);
                        }
                    }
                    TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch pre start end audio cache  " + this.f4847j.size());
                    this.f4847j.clear();
                }
                if (!this.f4846i.isEmpty()) {
                    TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch pre start end video cache  " + this.f4846i.size());
                    Iterator<TXSNALPacket> it3 = this.f4846i.iterator();
                    while (it3.hasNext()) {
                        this.f4850m.onPullNAL(it3.next());
                    }
                    this.f4846i.clear();
                }
                this.f4850m.onPullNAL(tXSNALPacket);
                this.f4850m = null;
                if (tXCMultiStreamDownloader != null) {
                    tXCMultiStreamDownloader.m1151a(this.f4848k, true);
                }
                this.f4848k.setNotifyListener(null);
                return;
            }
            TXCLog.m2913i("TXCMultiStreamDownloader", " stream_switch pre start cache video pts " + tXSNALPacket.pts + " from " + this.f4840c + " type " + tXSNALPacket.nalType);
            this.f4846i.add(tXSNALPacket);
        }

        /* renamed from: b */
        private void m1139b(TXSNALPacket tXSNALPacket) {
            TXCMultiStreamDownloader tXCMultiStreamDownloader = this.f4849l.get();
            if (tXCMultiStreamDownloader != null) {
                tXCMultiStreamDownloader.m1146c(tXSNALPacket.pts);
            }
            long j = tXSNALPacket.pts;
            if (j >= this.f4843f) {
                if (tXSNALPacket.nalType == 0) {
                    this.f4844g = j;
                }
                if (this.f4844g > 0) {
                    if (this.f4845h > 0) {
                        TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch delay stop end video pts " + this.f4844g + " audio ts " + this.f4845h + " from " + this.f4843f);
                        if (tXCMultiStreamDownloader != null) {
                            tXCMultiStreamDownloader.m1148b();
                        }
                        this.f4850m = null;
                        this.f4848k.setListener(null);
                        this.f4848k.stopDownload();
                        return;
                    }
                    TXCLog.m2911w("TXCMultiStreamDownloader", " stream_switch delay stop video end wait audio end video pts " + tXSNALPacket.pts + " from " + this.f4843f + " type " + tXSNALPacket.nalType);
                    return;
                }
                TXIStreamDownloaderListener tXIStreamDownloaderListener = this.f4850m;
                if (tXIStreamDownloaderListener == null) {
                    return;
                }
                tXIStreamDownloaderListener.onPullNAL(tXSNALPacket);
                return;
            }
            TXIStreamDownloaderListener tXIStreamDownloaderListener2 = this.f4850m;
            if (tXIStreamDownloaderListener2 == null) {
                return;
            }
            tXIStreamDownloaderListener2.onPullNAL(tXSNALPacket);
        }

        @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
        public void onNotifyEvent(int i, Bundle bundle) {
            if (i == 12012 || i == 12011) {
                TXCMultiStreamDownloader tXCMultiStreamDownloader = this.f4849l.get();
                if (tXCMultiStreamDownloader != null) {
                    tXCMultiStreamDownloader.m1151a(this.f4848k, false);
                }
                this.f4848k.setNotifyListener(null);
            }
        }
    }
}
