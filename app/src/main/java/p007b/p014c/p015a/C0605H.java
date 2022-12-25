package p007b.p014c.p015a;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/* renamed from: b.c.a.H */
/* loaded from: classes2.dex */
public class C0605H extends AbstractC0716o {

    /* renamed from: b */
    public static final /* synthetic */ boolean f200b = !C0605H.class.desiredAssertionStatus();

    /* renamed from: c */
    public ServerSocketChannel f201c;

    public C0605H(ServerSocketChannel serverSocketChannel) {
        super(serverSocketChannel);
        this.f201c = serverSocketChannel;
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: a */
    public int mo5297a(ByteBuffer[] byteBufferArr) {
        if (!f200b) {
            throw new AssertionError();
        }
        throw new IOException("Can't write ServerSocketChannel");
    }

    /* renamed from: a */
    public SelectionKey m5481a(Selector selector) {
        return this.f201c.register(selector, 16);
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: b */
    public boolean mo5296b() {
        if (f200b) {
            return false;
        }
        throw new AssertionError();
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: c */
    public void mo5295c() {
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) {
        if (!f200b) {
            throw new AssertionError();
        }
        throw new IOException("Can't read ServerSocketChannel");
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr) {
        if (!f200b) {
            throw new AssertionError();
        }
        throw new IOException("Can't read ServerSocketChannel");
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr, int i, int i2) {
        if (!f200b) {
            throw new AssertionError();
        }
        throw new IOException("Can't read ServerSocketChannel");
    }
}
