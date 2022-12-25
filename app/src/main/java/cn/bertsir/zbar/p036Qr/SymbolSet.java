package cn.bertsir.zbar.p036Qr;

import java.util.AbstractCollection;
import java.util.Iterator;

/* renamed from: cn.bertsir.zbar.Qr.SymbolSet */
/* loaded from: classes2.dex */
public class SymbolSet extends AbstractCollection<Symbol> {
    private long peer;

    private native void destroy(long j);

    private native long firstSymbol(long j);

    private static native void init();

    @Override // java.util.AbstractCollection, java.util.Collection
    public native int size();

    static {
        System.loadLibrary("zbar");
        init();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SymbolSet(long j) {
        this.peer = j;
    }

    protected void finalize() {
        destroy();
    }

    public synchronized void destroy() {
        if (this.peer != 0) {
            destroy(this.peer);
            this.peer = 0L;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<Symbol> iterator() {
        long firstSymbol = firstSymbol(this.peer);
        if (firstSymbol == 0) {
            return new SymbolIterator(null);
        }
        return new SymbolIterator(new Symbol(firstSymbol));
    }
}
