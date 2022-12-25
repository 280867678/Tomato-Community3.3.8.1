package p007b.p014c.p015a;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/* renamed from: b.c.a.I */
/* loaded from: classes2.dex */
public class C0606I extends AbstractC0716o {

    /* renamed from: b */
    public SocketChannel f202b;

    public C0606I(SocketChannel socketChannel) {
        super(socketChannel);
        this.f202b = socketChannel;
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: a */
    public int mo5297a(ByteBuffer[] byteBufferArr) {
        return (int) this.f202b.write(byteBufferArr);
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: b */
    public boolean mo5296b() {
        return this.f202b.isConnected();
    }

    @Override // p007b.p014c.p015a.AbstractC0716o
    /* renamed from: c */
    public void mo5295c() {
        try {
            this.f202b.socket().shutdownOutput();
        } catch (Exception unused) {
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) {
        return this.f202b.read(byteBuffer);
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr) {
        return this.f202b.read(byteBufferArr);
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr, int i, int i2) {
        return this.f202b.read(byteBufferArr, i, i2);
    }
}
