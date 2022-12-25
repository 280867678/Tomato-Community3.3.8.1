package master.flame.danmaku.danmaku.model.objectpool;

import master.flame.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes4.dex */
public interface PoolableManager<T extends Poolable<T>> {
    /* renamed from: newInstance */
    T mo6800newInstance();

    void onAcquired(T t);

    void onReleased(T t);
}
