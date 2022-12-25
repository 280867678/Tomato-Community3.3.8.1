package p007b.p014c.p015a;

import android.annotation.TargetApi;
import android.os.Looper;
import com.koushikdutta.async.util.ArrayDeque;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import p007b.p014c.p015a.p023e.C0702c;

@TargetApi(9)
/* renamed from: b.c.a.n */
/* loaded from: classes2.dex */
public class C0714n {

    /* renamed from: i */
    public ArrayDeque<ByteBuffer> f373i = new ArrayDeque<>();

    /* renamed from: j */
    public ByteOrder f374j = ByteOrder.BIG_ENDIAN;

    /* renamed from: k */
    public int f375k = 0;

    /* renamed from: h */
    public static final /* synthetic */ boolean f372h = !C0714n.class.desiredAssertionStatus();

    /* renamed from: a */
    public static PriorityQueue<ByteBuffer> f365a = new PriorityQueue<>(8, new C0715a());

    /* renamed from: b */
    public static int f366b = 1048576;

    /* renamed from: c */
    public static int f367c = 262144;

    /* renamed from: d */
    public static int f368d = 0;

    /* renamed from: e */
    public static int f369e = 0;

    /* renamed from: f */
    public static final Object f370f = new Object();

    /* renamed from: g */
    public static final ByteBuffer f371g = ByteBuffer.allocate(0);

    /* renamed from: b.c.a.n$a */
    /* loaded from: classes2.dex */
    static class C0715a implements Comparator<ByteBuffer> {
        @Override // java.util.Comparator
        /* renamed from: a */
        public int compare(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
            if (byteBuffer.capacity() == byteBuffer2.capacity()) {
                return 0;
            }
            return byteBuffer.capacity() > byteBuffer2.capacity() ? 1 : -1;
        }
    }

    public C0714n() {
    }

    public C0714n(byte[] bArr) {
        m5326a(ByteBuffer.wrap(bArr));
    }

    /* renamed from: b */
    public static ByteBuffer m5319b(int i) {
        PriorityQueue<ByteBuffer> m5310f;
        ByteBuffer remove;
        if (i <= f369e && (m5310f = m5310f()) != null) {
            synchronized (f370f) {
                do {
                    if (m5310f.size() > 0) {
                        remove = m5310f.remove();
                        boolean z = false;
                        if (m5310f.size() == 0) {
                            f369e = 0;
                        }
                        f368d -= remove.capacity();
                        if (!f372h) {
                            boolean z2 = m5310f.size() != 0;
                            if (f368d == 0) {
                                z = true;
                            }
                            if (!(z2 ^ z)) {
                                throw new AssertionError();
                            }
                        }
                    }
                } while (remove.capacity() < i);
                return remove;
            }
        }
        return ByteBuffer.allocate(Math.max(8192, i));
    }

    /* renamed from: c */
    public static void m5314c(ByteBuffer byteBuffer) {
        PriorityQueue<ByteBuffer> m5310f;
        if (byteBuffer == null || byteBuffer.isDirect() || byteBuffer.arrayOffset() != 0 || byteBuffer.array().length != byteBuffer.capacity() || byteBuffer.capacity() < 8192 || byteBuffer.capacity() > f367c || (m5310f = m5310f()) == null) {
            return;
        }
        synchronized (f370f) {
            while (f368d > f366b && m5310f.size() > 0 && m5310f.peek().capacity() < byteBuffer.capacity()) {
                f368d -= m5310f.remove().capacity();
            }
            if (f368d > f366b) {
                return;
            }
            if (!f372h && m5312d(byteBuffer)) {
                throw new AssertionError();
            }
            boolean z = false;
            byteBuffer.position(0);
            byteBuffer.limit(byteBuffer.capacity());
            f368d += byteBuffer.capacity();
            m5310f.add(byteBuffer);
            if (!f372h) {
                boolean z2 = m5310f.size() != 0;
                if (f368d == 0) {
                    z = true;
                }
                if (!(z2 ^ z)) {
                    throw new AssertionError();
                }
            }
            f369e = Math.max(f369e, byteBuffer.capacity());
        }
    }

    /* renamed from: d */
    public static boolean m5312d(ByteBuffer byteBuffer) {
        Iterator<ByteBuffer> it2 = f365a.iterator();
        while (it2.hasNext()) {
            if (it2.next() == byteBuffer) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: f */
    public static PriorityQueue<ByteBuffer> m5310f() {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper == null || Thread.currentThread() != mainLooper.getThread()) {
            return f365a;
        }
        return null;
    }

    /* renamed from: a */
    public byte m5330a() {
        byte b = m5315c(1).get();
        this.f375k--;
        return b;
    }

    /* renamed from: a */
    public C0714n m5326a(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            m5314c(byteBuffer);
            return this;
        }
        m5329a(byteBuffer.remaining());
        if (this.f373i.size() > 0) {
            ByteBuffer last = this.f373i.getLast();
            if (last.capacity() - last.limit() >= byteBuffer.remaining()) {
                last.mark();
                last.position(last.limit());
                last.limit(last.capacity());
                last.put(byteBuffer);
                last.limit(last.position());
                last.reset();
                m5314c(byteBuffer);
                m5300p();
                return this;
            }
        }
        this.f373i.add(byteBuffer);
        m5300p();
        return this;
    }

    /* renamed from: a */
    public C0714n m5325a(ByteOrder byteOrder) {
        this.f374j = byteOrder;
        return this;
    }

    /* renamed from: a */
    public C0714n m5321a(ByteBuffer... byteBufferArr) {
        for (ByteBuffer byteBuffer : byteBufferArr) {
            m5326a(byteBuffer);
        }
        return this;
    }

    /* renamed from: a */
    public String m5324a(Charset charset) {
        byte[] array;
        int arrayOffset;
        int remaining;
        if (charset == null) {
            charset = C0702c.f348b;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<ByteBuffer> it2 = this.f373i.iterator();
        while (it2.hasNext()) {
            ByteBuffer next = it2.next();
            if (next.isDirect()) {
                array = new byte[next.remaining()];
                arrayOffset = 0;
                remaining = next.remaining();
                next.get(array);
            } else {
                array = next.array();
                arrayOffset = next.arrayOffset() + next.position();
                remaining = next.remaining();
            }
            sb.append(new String(array, arrayOffset, remaining, charset));
        }
        return sb.toString();
    }

    /* renamed from: a */
    public final void m5329a(int i) {
        if (m5303m() >= 0) {
            this.f375k += i;
        }
    }

    /* renamed from: a */
    public void m5328a(C0714n c0714n) {
        m5327a(c0714n, m5303m());
    }

    /* renamed from: a */
    public void m5327a(C0714n c0714n, int i) {
        if (m5303m() >= i) {
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    break;
                }
                ByteBuffer remove = this.f373i.remove();
                int remaining = remove.remaining();
                if (remaining == 0) {
                    m5314c(remove);
                } else {
                    int i3 = remaining + i2;
                    if (i3 > i) {
                        int i4 = i - i2;
                        ByteBuffer m5319b = m5319b(i4);
                        m5319b.limit(i4);
                        remove.get(m5319b.array(), 0, i4);
                        c0714n.m5326a(m5319b);
                        this.f373i.addFirst(remove);
                        if (!f372h && m5319b.capacity() < i4) {
                            throw new AssertionError();
                        }
                        if (!f372h && m5319b.position() != 0) {
                            throw new AssertionError();
                        }
                    } else {
                        c0714n.m5326a(remove);
                        i2 = i3;
                    }
                }
            }
            this.f375k -= i;
            return;
        }
        throw new IllegalArgumentException("length");
    }

    /* renamed from: a */
    public void m5323a(byte[] bArr) {
        m5322a(bArr, 0, bArr.length);
    }

    /* renamed from: a */
    public void m5322a(byte[] bArr, int i, int i2) {
        if (m5303m() >= i2) {
            int i3 = i;
            int i4 = i2;
            while (i4 > 0) {
                ByteBuffer peek = this.f373i.peek();
                int min = Math.min(peek.remaining(), i4);
                if (bArr != null) {
                    peek.get(bArr, i3, min);
                } else {
                    peek.position(peek.position() + min);
                }
                i4 -= min;
                i3 += min;
                if (peek.remaining() == 0) {
                    ByteBuffer remove = this.f373i.remove();
                    if (!f372h && peek != remove) {
                        throw new AssertionError();
                    }
                    m5314c(peek);
                }
            }
            this.f375k -= i2;
            return;
        }
        throw new IllegalArgumentException("length");
    }

    /* renamed from: b */
    public String m5317b(Charset charset) {
        String m5324a = m5324a(charset);
        m5304l();
        return m5324a;
    }

    /* renamed from: b */
    public void m5318b(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            m5314c(byteBuffer);
            return;
        }
        m5329a(byteBuffer.remaining());
        if (this.f373i.size() > 0) {
            ByteBuffer first = this.f373i.getFirst();
            if (first.position() >= byteBuffer.remaining()) {
                first.position(first.position() - byteBuffer.remaining());
                first.mark();
                first.put(byteBuffer);
                first.reset();
                m5314c(byteBuffer);
                return;
            }
        }
        this.f373i.addFirst(byteBuffer);
    }

    /* renamed from: b */
    public ByteBuffer[] m5320b() {
        ByteBuffer[] byteBufferArr = (ByteBuffer[]) this.f373i.toArray(new ByteBuffer[this.f373i.size()]);
        this.f373i.clear();
        this.f375k = 0;
        return byteBufferArr;
    }

    /* renamed from: c */
    public char m5316c() {
        char c = (char) m5315c(1).get();
        this.f375k--;
        return c;
    }

    /* renamed from: c */
    public final ByteBuffer m5315c(int i) {
        ByteBuffer peek;
        ByteBuffer byteBuffer;
        if (m5303m() < i) {
            throw new IllegalArgumentException("count : " + m5303m() + "/" + i);
        }
        while (true) {
            peek = this.f373i.peek();
            if (peek == null || peek.hasRemaining()) {
                break;
            }
            m5314c(this.f373i.remove());
        }
        if (peek == null) {
            return f371g;
        }
        if (peek.remaining() < i) {
            peek = m5319b(i);
            peek.limit(i);
            byte[] array = peek.array();
            int i2 = 0;
            loop1: while (true) {
                byteBuffer = null;
                while (i2 < i) {
                    byteBuffer = this.f373i.remove();
                    int min = Math.min(i - i2, byteBuffer.remaining());
                    byteBuffer.get(array, i2, min);
                    i2 += min;
                    if (byteBuffer.remaining() == 0) {
                        break;
                    }
                }
                m5314c(byteBuffer);
            }
            if (byteBuffer != null && byteBuffer.remaining() > 0) {
                this.f373i.addFirst(byteBuffer);
            }
            this.f373i.addFirst(peek);
        }
        return peek.order(this.f374j);
    }

    /* renamed from: d */
    public int m5313d() {
        int i = m5315c(4).getInt();
        this.f375k -= 4;
        return i;
    }

    /* renamed from: e */
    public long m5311e() {
        long j = m5315c(8).getLong();
        this.f375k -= 8;
        return j;
    }

    /* renamed from: g */
    public short m5309g() {
        short s = m5315c(2).getShort();
        this.f375k -= 2;
        return s;
    }

    /* renamed from: h */
    public boolean m5308h() {
        return m5303m() > 0;
    }

    /* renamed from: i */
    public boolean m5307i() {
        return this.f375k == 0;
    }

    /* renamed from: j */
    public String m5306j() {
        return m5324a((Charset) null);
    }

    /* renamed from: k */
    public String m5305k() {
        return m5317b((Charset) null);
    }

    /* renamed from: l */
    public void m5304l() {
        while (this.f373i.size() > 0) {
            m5314c(this.f373i.remove());
        }
        if (f372h || this.f373i.size() == 0) {
            this.f375k = 0;
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: m */
    public int m5303m() {
        return this.f375k;
    }

    /* renamed from: n */
    public ByteBuffer m5302n() {
        ByteBuffer remove = this.f373i.remove();
        this.f375k -= remove.remaining();
        return remove;
    }

    /* renamed from: o */
    public int m5301o() {
        return this.f373i.size();
    }

    /* renamed from: p */
    public void m5300p() {
        m5315c(0);
    }
}
