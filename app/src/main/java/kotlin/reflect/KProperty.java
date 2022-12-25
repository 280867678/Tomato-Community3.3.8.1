package kotlin.reflect;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KProperty<R> extends KCallable<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Accessor<R> {
    }

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Getter<R> extends Accessor<R>, KFunction<R> {
    }

    boolean isConst();

    boolean isLateinit();
}
