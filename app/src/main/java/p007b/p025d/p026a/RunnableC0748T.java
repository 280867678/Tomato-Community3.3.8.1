package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: b.d.a.T */
/* loaded from: classes2.dex */
public class RunnableC0748T implements Runnable {

    /* renamed from: a */
    public P2PUdpUtil f457a;

    /* renamed from: b */
    public final /* synthetic */ P2PUdpUtil f458b;

    public RunnableC0748T(P2PUdpUtil p2PUdpUtil) {
        this.f458b = p2PUdpUtil;
        this.f457a = this.f458b;
    }

    /* JADX WARN: Incorrect condition in loop: B:3:0x0013 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        AtomicBoolean atomicBoolean;
        Object obj;
        Set<Long> set;
        Set set2;
        BlockingQueue blockingQueue;
        ConcurrentHashMap concurrentHashMap;
        AbstractC0808t m5225a;
        ConcurrentHashMap concurrentHashMap2;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2PUdp DataThread Running!!!!!");
        while (atomicBoolean.get()) {
            try {
                obj = this.f457a.f5973p;
                synchronized (obj) {
                    set = this.f457a.f5972o;
                    for (Long l : set) {
                        long longValue = l.longValue();
                        concurrentHashMap2 = this.f457a.f5968k;
                        concurrentHashMap2.remove(Long.valueOf(longValue));
                        this.f457a.nativeCloseTaskSync(longValue);
                    }
                    set2 = this.f457a.f5972o;
                    set2.clear();
                }
                blockingQueue = this.f457a.f5971n;
                C0747S c0747s = (C0747S) blockingQueue.poll(300L, TimeUnit.MILLISECONDS);
                if (c0747s != null) {
                    long j = c0747s.f454a;
                    concurrentHashMap = this.f457a.f5968k;
                    C0746Q c0746q = (C0746Q) concurrentHashMap.get(Long.valueOf(j));
                    if (c0746q != null && (m5225a = c0746q.m5225a()) != null) {
                        m5225a.mo4923a(j, c0747s.f455b, c0747s.f456c);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
