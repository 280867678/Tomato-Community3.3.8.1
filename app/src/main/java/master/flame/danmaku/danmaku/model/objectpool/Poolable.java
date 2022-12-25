package master.flame.danmaku.danmaku.model.objectpool;

/* loaded from: classes4.dex */
public interface Poolable<T> {
    T getNextPoolable();

    boolean isPooled();

    void setNextPoolable(T t);

    void setPooled(boolean z);
}
