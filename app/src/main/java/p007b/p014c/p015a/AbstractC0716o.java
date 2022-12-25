package p007b.p014c.p015a;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/* renamed from: b.c.a.o */
/* loaded from: classes2.dex */
public abstract class AbstractC0716o implements ReadableByteChannel, ScatteringByteChannel {

    /* renamed from: a */
    public AbstractSelectableChannel f376a;

    public AbstractC0716o(AbstractSelectableChannel abstractSelectableChannel) {
        abstractSelectableChannel.configureBlocking(false);
        this.f376a = abstractSelectableChannel;
    }

    /* renamed from: a */
    public abstract int mo5297a(ByteBuffer[] byteBufferArr);

    /* renamed from: a */
    public boolean m5298a() {
        return false;
    }

    /* renamed from: b */
    public abstract boolean mo5296b();

    /* renamed from: c */
    public abstract void mo5295c();

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f376a.close();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return this.f376a.isOpen();
    }
}
