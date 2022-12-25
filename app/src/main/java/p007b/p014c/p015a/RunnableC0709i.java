package p007b.p014c.p015a;

import android.util.Log;
import com.koushikdutta.async.AsyncServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import p007b.p014c.p015a.p016a.AbstractC0615d;
import p007b.p014c.p015a.p023e.C0704e;

/* renamed from: b.c.a.i */
/* loaded from: classes2.dex */
public class RunnableC0709i implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ InetAddress f353a;

    /* renamed from: b */
    public final /* synthetic */ int f354b;

    /* renamed from: c */
    public final /* synthetic */ AbstractC0615d f355c;

    /* renamed from: d */
    public final /* synthetic */ AsyncServer.C2203c f356d;

    /* renamed from: e */
    public final /* synthetic */ AsyncServer f357e;

    public RunnableC0709i(AsyncServer asyncServer, InetAddress inetAddress, int i, AbstractC0615d abstractC0615d, AsyncServer.C2203c c2203c) {
        this.f357e = asyncServer;
        this.f353a = inetAddress;
        this.f354b = i;
        this.f355c = abstractC0615d;
        this.f356d = c2203c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, b.c.a.k, b.c.a.h] */
    @Override // java.lang.Runnable
    public void run() {
        C0605H c0605h;
        IOException e;
        ServerSocketChannel serverSocketChannel;
        C0604G c0604g;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            try {
                c0605h = new C0605H(serverSocketChannel);
            } catch (IOException e2) {
                c0605h = null;
                e = e2;
            }
            try {
                serverSocketChannel.socket().bind(this.f353a == null ? new InetSocketAddress(this.f354b) : new InetSocketAddress(this.f353a, this.f354b));
                c0604g = this.f357e.f1502g;
                SelectionKey m5481a = c0605h.m5481a(c0604g.m5487a());
                m5481a.attach(this.f355c);
                AbstractC0615d abstractC0615d = this.f355c;
                AsyncServer.C2203c c2203c = this.f356d;
                ?? c0708h = new C0708h(this, serverSocketChannel, c0605h, m5481a);
                c2203c.f1513a = c0708h;
                abstractC0615d.mo5416a((AbstractC0711k) c0708h);
            } catch (IOException e3) {
                e = e3;
                Log.e("NIO", "wtf", e);
                C0704e.m5336a(c0605h, serverSocketChannel);
                this.f355c.mo5196a(e);
            }
        } catch (IOException e4) {
            c0605h = null;
            e = e4;
            serverSocketChannel = null;
        }
    }
}
