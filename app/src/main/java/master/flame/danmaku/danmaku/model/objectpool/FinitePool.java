package master.flame.danmaku.danmaku.model.objectpool;

import master.flame.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes4.dex */
class FinitePool<T extends Poolable<T>> implements Pool<T> {
    private final boolean mInfinite;
    private final int mLimit;
    private final PoolableManager<T> mManager;
    private int mPoolCount;
    private T mRoot;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FinitePool(PoolableManager<T> poolableManager, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The pool limit must be > 0");
        }
        this.mManager = poolableManager;
        this.mLimit = i;
        this.mInfinite = false;
    }

    @Override // master.flame.danmaku.danmaku.model.objectpool.Pool
    public T acquire() {
        T t = this.mRoot;
        if (t != null) {
            this.mRoot = (T) t.getNextPoolable();
            this.mPoolCount--;
        } else {
            t = this.mManager.mo6800newInstance();
        }
        if (t != null) {
            t.setNextPoolable(null);
            t.setPooled(false);
            this.mManager.onAcquired(t);
        }
        return t;
    }

    @Override // master.flame.danmaku.danmaku.model.objectpool.Pool
    public void release(T t) {
        if (!t.isPooled()) {
            if (this.mInfinite || this.mPoolCount < this.mLimit) {
                this.mPoolCount++;
                t.setNextPoolable(this.mRoot);
                t.setPooled(true);
                this.mRoot = t;
            }
            this.mManager.onReleased(t);
            return;
        }
        System.out.print("[FinitePool] Element is already in pool: " + t);
    }
}
