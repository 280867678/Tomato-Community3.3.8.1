package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class TraeAudioSessionHost {
    private ArrayList<C3732a> _sessionInfoList = new ArrayList<>();
    private ReentrantLock mLock = new ReentrantLock();

    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioSessionHost$a */
    /* loaded from: classes3.dex */
    public class C3732a {

        /* renamed from: a */
        public long f5742a;

        public C3732a() {
        }
    }

    public C3732a find(long j) {
        C3732a c3732a;
        this.mLock.lock();
        int i = 0;
        while (true) {
            if (i >= this._sessionInfoList.size()) {
                c3732a = null;
                break;
            }
            c3732a = this._sessionInfoList.get(i);
            if (c3732a.f5742a == j) {
                break;
            }
            i++;
        }
        this.mLock.unlock();
        return c3732a;
    }

    public void add(long j, Context context) {
        if (find(j) != null) {
            return;
        }
        C3732a c3732a = new C3732a();
        c3732a.f5742a = j;
        this.mLock.lock();
        this._sessionInfoList.add(c3732a);
        this.mLock.unlock();
    }

    public void remove(long j) {
        this.mLock.lock();
        int i = 0;
        while (true) {
            if (i >= this._sessionInfoList.size()) {
                break;
            } else if (this._sessionInfoList.get(i).f5742a == j) {
                this._sessionInfoList.remove(i);
                break;
            } else {
                i++;
            }
        }
        this.mLock.unlock();
    }
}
