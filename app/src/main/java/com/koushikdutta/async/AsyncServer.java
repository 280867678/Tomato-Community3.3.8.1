package com.koushikdutta.async;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p007b.p014c.p015a.AbstractC0711k;
import p007b.p014c.p015a.C0604G;
import p007b.p014c.p015a.C0691d;
import p007b.p014c.p015a.C0705f;
import p007b.p014c.p015a.C0710j;
import p007b.p014c.p015a.RunnableC0707g;
import p007b.p014c.p015a.RunnableC0709i;
import p007b.p014c.p015a.p016a.AbstractC0612b;
import p007b.p014c.p015a.p016a.AbstractC0615d;
import p007b.p014c.p015a.p017b.AbstractC0619a;
import p007b.p014c.p015a.p017b.C0626h;
import p007b.p014c.p015a.p017b.C0627i;
import p007b.p014c.p015a.p023e.C0704e;

/* loaded from: classes3.dex */
public class AsyncServer {

    /* renamed from: a */
    public static AsyncServer f1499a;

    /* renamed from: b */
    public static ExecutorService f1500b;

    /* renamed from: e */
    public static final ThreadLocal<AsyncServer> f1501e;

    /* renamed from: g */
    public C0604G f1502g;

    /* renamed from: h */
    public String f1503h;

    /* renamed from: i */
    public boolean f1504i;

    /* renamed from: j */
    public int f1505j;

    /* renamed from: k */
    public PriorityQueue<RunnableC2204d> f1506k;

    /* renamed from: l */
    public Thread f1507l;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class AsyncSelectorException extends IOException {
        public AsyncSelectorException(Exception exc) {
            super(exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.koushikdutta.async.AsyncServer$a */
    /* loaded from: classes3.dex */
    public class C2201a extends C0627i<C0691d> {

        /* renamed from: k */
        public SocketChannel f1508k;

        /* renamed from: l */
        public AbstractC0612b f1509l;

        @Override // p007b.p014c.p015a.p017b.C0626h
        /* renamed from: a */
        public void mo3878a() {
            super.mo3878a();
            try {
                if (this.f1508k == null) {
                    return;
                }
                this.f1508k.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.koushikdutta.async.AsyncServer$b */
    /* loaded from: classes3.dex */
    public static class ThreadFactoryC2202b implements ThreadFactory {

        /* renamed from: a */
        public final ThreadGroup f1510a;

        /* renamed from: b */
        public final AtomicInteger f1511b = new AtomicInteger(1);

        /* renamed from: c */
        public final String f1512c;

        public ThreadFactoryC2202b(String str) {
            SecurityManager securityManager = System.getSecurityManager();
            this.f1510a = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f1512c = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            ThreadGroup threadGroup = this.f1510a;
            Thread thread = new Thread(threadGroup, runnable, this.f1512c + this.f1511b.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.koushikdutta.async.AsyncServer$c */
    /* loaded from: classes3.dex */
    public static class C2203c<T> {

        /* renamed from: a */
        public T f1513a;

        public C2203c() {
        }

        public /* synthetic */ C2203c(RunnableC0707g runnableC0707g) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.koushikdutta.async.AsyncServer$d */
    /* loaded from: classes3.dex */
    public static class RunnableC2204d implements AbstractC0619a, Runnable {

        /* renamed from: a */
        public AsyncServer f1514a;

        /* renamed from: b */
        public Runnable f1515b;

        /* renamed from: c */
        public long f1516c;

        /* renamed from: d */
        public boolean f1517d;

        public RunnableC2204d(AsyncServer asyncServer, Runnable runnable, long j) {
            this.f1514a = asyncServer;
            this.f1515b = runnable;
            this.f1516c = j;
        }

        @Override // p007b.p014c.p015a.p017b.AbstractC0619a
        public boolean cancel() {
            boolean remove;
            synchronized (this.f1514a) {
                remove = this.f1514a.f1506k.remove(this);
                this.f1517d = remove;
            }
            return remove;
        }

        @Override // p007b.p014c.p015a.p017b.AbstractC0619a
        public boolean isCancelled() {
            return this.f1517d;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f1515b.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.koushikdutta.async.AsyncServer$e */
    /* loaded from: classes3.dex */
    public static class C2205e implements Comparator<RunnableC2204d> {

        /* renamed from: a */
        public static C2205e f1518a = new C2205e();

        @Override // java.util.Comparator
        /* renamed from: a */
        public int compare(RunnableC2204d runnableC2204d, RunnableC2204d runnableC2204d2) {
            int i = (runnableC2204d.f1516c > runnableC2204d2.f1516c ? 1 : (runnableC2204d.f1516c == runnableC2204d2.f1516c ? 0 : -1));
            if (i == 0) {
                return 0;
            }
            return i > 0 ? 1 : -1;
        }
    }

    static {
        AsyncServer.class.desiredAssertionStatus();
        try {
            if (Build.VERSION.SDK_INT <= 8) {
                System.setProperty("java.net.preferIPv4Stack", "true");
                System.setProperty("java.net.preferIPv6Addresses", "false");
            }
        } catch (Throwable unused) {
        }
        f1499a = new AsyncServer();
        f1500b = m3890a("AsyncServer-worker-");
        new C0710j();
        m3890a("AsyncServer-resolver-");
        f1501e = new ThreadLocal<>();
    }

    public AsyncServer() {
        this(null);
    }

    public AsyncServer(String str) {
        this.f1505j = 0;
        this.f1506k = new PriorityQueue<>(1, C2205e.f1518a);
        this.f1503h = str == null ? "AsyncServer" : str;
    }

    /* renamed from: a */
    public static long m3894a(AsyncServer asyncServer, PriorityQueue<RunnableC2204d> priorityQueue) {
        long j = Long.MAX_VALUE;
        while (true) {
            RunnableC2204d runnableC2204d = null;
            synchronized (asyncServer) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (priorityQueue.size() > 0) {
                    RunnableC2204d remove = priorityQueue.remove();
                    if (remove.f1516c <= elapsedRealtime) {
                        runnableC2204d = remove;
                    } else {
                        j = remove.f1516c - elapsedRealtime;
                        priorityQueue.add(remove);
                    }
                }
            }
            if (runnableC2204d == null) {
                asyncServer.f1505j = 0;
                return j;
            }
            runnableC2204d.run();
        }
    }

    /* renamed from: a */
    public static ExecutorService m3890a(String str) {
        return new ThreadPoolExecutor(1, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactoryC2202b(str));
    }

    /* renamed from: a */
    public static void m3897a(C0604G c0604g) {
        m3886b(c0604g);
        C0704e.m5336a(c0604g);
    }

    /* renamed from: a */
    public static /* synthetic */ void m3891a(Runnable runnable, Semaphore semaphore) {
        runnable.run();
        semaphore.release();
    }

    /* renamed from: b */
    public static void m3886b(C0604G c0604g) {
        try {
            for (SelectionKey selectionKey : c0604g.m5485b()) {
                C0704e.m5336a(selectionKey.channel());
                try {
                    selectionKey.cancel();
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* renamed from: b */
    public static void m3885b(AsyncServer asyncServer, C0604G c0604g, PriorityQueue<RunnableC2204d> priorityQueue) {
        while (true) {
            try {
                m3881c(asyncServer, c0604g, priorityQueue);
            } catch (AsyncSelectorException e) {
                if (!(e.getCause() instanceof ClosedSelectorException)) {
                    Log.i("NIO", "Selector exception, shutting down", e);
                }
                C0704e.m5336a(c0604g);
            }
            synchronized (asyncServer) {
                if (!c0604g.isOpen() || (c0604g.m5485b().size() <= 0 && priorityQueue.size() <= 0)) {
                    break;
                }
            }
        }
        m3897a(c0604g);
        if (asyncServer.f1502g == c0604g) {
            asyncServer.f1506k = new PriorityQueue<>(1, C2205e.f1518a);
            asyncServer.f1502g = null;
            asyncServer.f1507l = null;
        }
    }

    /* renamed from: c */
    public static AsyncServer m3883c() {
        return f1499a;
    }

    /* renamed from: c */
    public static void m3882c(C0604G c0604g) {
        f1500b.execute(new RunnableC0707g(c0604g));
    }

    /* renamed from: c */
    public static void m3881c(AsyncServer asyncServer, C0604G c0604g, PriorityQueue<RunnableC2204d> priorityQueue) {
        boolean z;
        SelectionKey selectionKey;
        SocketChannel socketChannel;
        long m3894a = m3894a(asyncServer, priorityQueue);
        try {
            synchronized (asyncServer) {
                if (c0604g.m5484c() != 0) {
                    z = false;
                } else if (c0604g.m5485b().size() == 0 && m3894a == Long.MAX_VALUE) {
                    return;
                } else {
                    z = true;
                }
                if (z) {
                    if (m3894a == Long.MAX_VALUE) {
                        m3894a = 200;
                    }
                    c0604g.m5486a(m3894a);
                }
                Set<SelectionKey> m5483d = c0604g.m5483d();
                for (SelectionKey selectionKey2 : m5483d) {
                    try {
                        selectionKey = null;
                    } catch (CancelledKeyException unused) {
                    }
                    if (selectionKey2.isAcceptable()) {
                        try {
                            socketChannel = ((ServerSocketChannel) selectionKey2.channel()).accept();
                            if (socketChannel != null) {
                                try {
                                    socketChannel.configureBlocking(false);
                                    selectionKey = socketChannel.register(c0604g.m5487a(), 1);
                                    C0691d c0691d = new C0691d();
                                    c0691d.m5355a(socketChannel, (InetSocketAddress) socketChannel.socket().getRemoteSocketAddress());
                                    c0691d.m5357a(asyncServer, selectionKey);
                                    selectionKey.attach(c0691d);
                                    ((AbstractC0615d) selectionKey2.attachment()).mo5415a(c0691d);
                                } catch (IOException unused2) {
                                    C0704e.m5336a(socketChannel);
                                    if (selectionKey != null) {
                                        selectionKey.cancel();
                                    }
                                }
                            }
                        } catch (IOException unused3) {
                            socketChannel = null;
                        }
                    } else if (selectionKey2.isReadable()) {
                        asyncServer.m3898a(((C0691d) selectionKey2.attachment()).m5349j());
                    } else if (!selectionKey2.isWritable()) {
                        if (!selectionKey2.isConnectable()) {
                            Log.i("NIO", "wtf");
                            throw new RuntimeException("Unknown key state.");
                            break;
                        }
                        C2201a c2201a = (C2201a) selectionKey2.attachment();
                        SocketChannel socketChannel2 = (SocketChannel) selectionKey2.channel();
                        selectionKey2.interestOps(1);
                        try {
                            socketChannel2.finishConnect();
                            C0691d c0691d2 = new C0691d();
                            c0691d2.m5357a(asyncServer, selectionKey2);
                            c0691d2.m5355a(socketChannel2, (InetSocketAddress) socketChannel2.socket().getRemoteSocketAddress());
                            selectionKey2.attach(c0691d2);
                            if (c2201a.m5460a((C2201a) c0691d2)) {
                                c2201a.f1509l.m5477a(null, c0691d2);
                            }
                        } catch (IOException e) {
                            selectionKey2.cancel();
                            C0704e.m5336a(socketChannel2);
                            if (c2201a.m5462a((Exception) e)) {
                                c2201a.f1509l.m5477a(e, null);
                            }
                        }
                    } else {
                        ((C0691d) selectionKey2.attachment()).m5350i();
                    }
                }
                m5483d.clear();
            }
        } catch (Exception e2) {
            throw new AsyncSelectorException(e2);
        }
    }

    /* renamed from: a */
    public AbstractC0619a m3893a(Runnable runnable) {
        return m3892a(runnable, 0L);
    }

    /* renamed from: a */
    public AbstractC0619a m3892a(Runnable runnable, long j) {
        synchronized (this) {
            if (this.f1504i) {
                return C0626h.f211b;
            }
            long j2 = 0;
            int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            if (i > 0) {
                j2 = SystemClock.elapsedRealtime() + j;
            } else if (i == 0) {
                int i2 = this.f1505j;
                this.f1505j = i2 + 1;
                j2 = i2;
            } else if (this.f1506k.size() > 0) {
                j2 = Math.min(0L, this.f1506k.peek().f1516c - 1);
            }
            PriorityQueue<RunnableC2204d> priorityQueue = this.f1506k;
            RunnableC2204d runnableC2204d = new RunnableC2204d(this, runnable, j2);
            priorityQueue.add(runnableC2204d);
            if (this.f1502g == null) {
                m3879e();
            }
            if (!m3880d()) {
                m3882c(this.f1502g);
            }
            return runnableC2204d;
        }
    }

    /* renamed from: a */
    public AbstractC0711k m3889a(InetAddress inetAddress, int i, AbstractC0615d abstractC0615d) {
        C2203c c2203c = new C2203c(null);
        m3884b(new RunnableC0709i(this, inetAddress, i, abstractC0615d, c2203c));
        return (AbstractC0711k) c2203c.f1513a;
    }

    /* renamed from: a */
    public void m3898a(int i) {
    }

    /* renamed from: b */
    public Thread m3888b() {
        return this.f1507l;
    }

    /* renamed from: b */
    public void m3887b(int i) {
    }

    /* renamed from: b */
    public void m3884b(final Runnable runnable) {
        if (Thread.currentThread() == this.f1507l) {
            m3893a(runnable);
            m3894a(this, this.f1506k);
            return;
        }
        synchronized (this) {
            if (this.f1504i) {
                return;
            }
            final Semaphore semaphore = new Semaphore(0);
            m3893a(new Runnable() { // from class: com.koushikdutta.async.-$$Lambda$pd74tzmZxCXQ930PK3du0-OTS5M
                @Override // java.lang.Runnable
                public final void run() {
                    AsyncServer.m3891a(runnable, semaphore);
                }
            });
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                Log.e("NIO", "run", e);
            }
        }
    }

    /* renamed from: d */
    public boolean m3880d() {
        return this.f1507l == Thread.currentThread();
    }

    /* renamed from: e */
    public final void m3879e() {
        synchronized (this) {
            if (this.f1502g == null) {
                try {
                    C0604G c0604g = new C0604G(SelectorProvider.provider().openSelector());
                    this.f1502g = c0604g;
                    this.f1507l = new C0705f(this, this.f1503h, c0604g, this.f1506k);
                    this.f1507l.start();
                    return;
                } catch (IOException e) {
                    throw new RuntimeException("unable to create selector?", e);
                }
            }
            C0604G c0604g2 = this.f1502g;
            PriorityQueue<RunnableC2204d> priorityQueue = this.f1506k;
            try {
                m3881c(this, c0604g2, priorityQueue);
            } catch (AsyncSelectorException e2) {
                Log.i("NIO", "Selector closed", e2);
                try {
                    c0604g2.m5487a().close();
                } catch (Exception unused) {
                }
            }
        }
    }
}
