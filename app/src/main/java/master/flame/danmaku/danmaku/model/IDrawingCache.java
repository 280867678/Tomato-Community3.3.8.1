package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public interface IDrawingCache<T> {
    void decreaseReference();

    void destroy();

    T get();

    boolean hasReferences();

    int height();

    int size();

    int width();
}
