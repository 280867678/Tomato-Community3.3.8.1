package cn.bertsir.zbar.p036Qr;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: cn.bertsir.zbar.Qr.SymbolIterator */
/* loaded from: classes2.dex */
public class SymbolIterator implements Iterator<Symbol> {
    private Symbol current;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SymbolIterator(Symbol symbol) {
        this.current = symbol;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.current != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    /* renamed from: next */
    public Symbol mo5794next() {
        Symbol symbol = this.current;
        if (symbol == null) {
            throw new NoSuchElementException("access past end of SymbolIterator");
        }
        long next = symbol.next();
        if (next != 0) {
            this.current = new Symbol(next);
        } else {
            this.current = null;
        }
        return symbol;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("SymbolIterator is immutable");
    }
}
