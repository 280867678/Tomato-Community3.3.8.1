package p007b.p014c.p015a;

import com.koushikdutta.async.AsyncServer;
import java.util.PriorityQueue;

/* renamed from: b.c.a.f */
/* loaded from: classes2.dex */
public class C0705f extends Thread {

    /* renamed from: a */
    public final /* synthetic */ C0604G f349a;

    /* renamed from: b */
    public final /* synthetic */ PriorityQueue f350b;

    /* renamed from: c */
    public final /* synthetic */ AsyncServer f351c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0705f(AsyncServer asyncServer, String str, C0604G c0604g, PriorityQueue priorityQueue) {
        super(str);
        this.f351c = asyncServer;
        this.f349a = c0604g;
        this.f350b = priorityQueue;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        ThreadLocal threadLocal;
        ThreadLocal threadLocal2;
        try {
            threadLocal2 = AsyncServer.f1501e;
            threadLocal2.set(this.f351c);
            AsyncServer.m3885b(this.f351c, this.f349a, this.f350b);
        } finally {
            threadLocal = AsyncServer.f1501e;
            threadLocal.remove();
        }
    }
}
