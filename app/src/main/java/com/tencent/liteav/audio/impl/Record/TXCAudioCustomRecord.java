package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;

/* renamed from: com.tencent.liteav.audio.impl.Record.d */
/* loaded from: classes3.dex */
public class TXCAudioCustomRecord extends TXCAudioBaseRecord implements Runnable {

    /* renamed from: d */
    private boolean f2105d = false;

    /* renamed from: e */
    private Thread f2106e = null;

    /* renamed from: f */
    private byte[] f2107f = new byte[20480];

    /* renamed from: g */
    private int f2108g = 0;

    /* renamed from: h */
    private int f2109h = 0;

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecord
    /* renamed from: a */
    public void mo3399a(Context context, int i, int i2, int i3) {
        super.mo3399a(context, i, i2, i3);
        m3397c();
        this.f2105d = true;
        this.f2106e = new Thread(this, "AudioCustomRecord Thread");
        this.f2106e.start();
    }

    /* renamed from: c */
    public void m3397c() {
        this.f2105d = false;
        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = this.f2106e;
        if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.f2106e.getId()) {
            try {
                this.f2106e.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                TXCLog.m2914e("AudioCenter:TXCAudioCustomRecord", "custom record stop Exception: " + e.getMessage());
            }
        }
        TXCLog.m2913i("AudioCenter:TXCAudioCustomRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.f2106e = null;
    }

    /* renamed from: d */
    public boolean m3396d() {
        return this.f2105d;
    }

    /* renamed from: a */
    public synchronized void m3398a(byte[] bArr) {
        if (bArr != null) {
            if (m3394f() >= bArr.length) {
                if (this.f2108g + bArr.length <= this.f2107f.length) {
                    System.arraycopy(bArr, 0, this.f2107f, this.f2108g, bArr.length);
                    this.f2108g += bArr.length;
                } else {
                    int length = this.f2107f.length - this.f2108g;
                    System.arraycopy(bArr, 0, this.f2107f, this.f2108g, length);
                    this.f2108g = bArr.length - length;
                    System.arraycopy(bArr, length, this.f2107f, 0, this.f2108g);
                }
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("缓冲区不够. 自定义数据长度 = ");
        sb.append(bArr == null ? -1 : bArr.length);
        sb.append(", 剩余缓冲区长度 = ");
        sb.append(m3394f());
        TXCLog.m2914e("AudioCenter:TXCAudioCustomRecord", sb.toString());
    }

    /* renamed from: e */
    private int m3395e() {
        int i = this.f2108g;
        byte[] bArr = this.f2107f;
        return ((i + bArr.length) - this.f2109h) % bArr.length;
    }

    /* renamed from: f */
    private int m3394f() {
        return this.f2107f.length - m3395e();
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.f2105d) {
            TXCLog.m2911w("AudioCenter:TXCAudioCustomRecord", "audio custom record: abandom start audio sys record thread!");
            return;
        }
        m3403a();
        int i = ((this.f2102b * 1024) * this.f2103c) / 8;
        byte[] bArr = new byte[i];
        while (this.f2105d && !Thread.interrupted()) {
            if (i <= m3395e()) {
                synchronized (this) {
                    if (this.f2109h + i <= this.f2107f.length) {
                        System.arraycopy(this.f2107f, this.f2109h, bArr, 0, i);
                        this.f2109h += i;
                    } else {
                        int length = this.f2107f.length - this.f2109h;
                        System.arraycopy(this.f2107f, this.f2109h, bArr, 0, length);
                        this.f2109h = i - length;
                        System.arraycopy(this.f2107f, 0, bArr, length, this.f2109h);
                    }
                }
                m3401a(bArr, bArr.length, TXCTimeUtil.getTimeTick());
            } else {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        m3400b();
    }
}
