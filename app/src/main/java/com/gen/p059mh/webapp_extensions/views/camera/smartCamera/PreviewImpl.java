package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.view.SurfaceHolder;
import android.view.View;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.PreviewImpl */
/* loaded from: classes2.dex */
public abstract class PreviewImpl {
    private Callback mCallback;
    private int mHeight;
    private int mWidth;

    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.PreviewImpl$Callback */
    /* loaded from: classes2.dex */
    public interface Callback {
        void onSurfaceChanged();
    }

    public abstract Class getOutputClass();

    public SurfaceHolder getSurfaceHolder() {
        return null;
    }

    /* renamed from: getSurfaceTexture */
    public Object mo6190getSurfaceTexture() {
        return null;
    }

    public abstract View getView();

    public abstract boolean isReady();

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void dispatchSurfaceChanged() {
        this.mCallback.onSurfaceChanged();
    }

    public void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }
}
