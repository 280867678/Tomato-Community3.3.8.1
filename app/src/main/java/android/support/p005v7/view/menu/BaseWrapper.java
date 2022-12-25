package android.support.p005v7.view.menu;

/* renamed from: android.support.v7.view.menu.BaseWrapper */
/* loaded from: classes2.dex */
class BaseWrapper<T> {
    final T mWrappedObject;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseWrapper(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = t;
    }

    /* renamed from: getWrappedObject */
    public T mo5755getWrappedObject() {
        return this.mWrappedObject;
    }
}
