package kotlin.coroutines.experimental;

/* compiled from: CoroutineContext.kt */
/* loaded from: classes4.dex */
public interface CoroutineContext {

    /* compiled from: CoroutineContext.kt */
    /* loaded from: classes4.dex */
    public interface Element extends CoroutineContext {
    }

    /* compiled from: CoroutineContext.kt */
    /* loaded from: classes4.dex */
    public interface Key<E extends Element> {
    }

    <E extends Element> E get(Key<E> key);
}
