package master.flame.danmaku.danmaku.model.objectpool;

/* loaded from: classes4.dex */
public class Pools {
    public static <T extends Poolable<T>> Pool<T> finitePool(PoolableManager<T> poolableManager, int i) {
        return new FinitePool(poolableManager, i);
    }
}
