package com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14;

import android.content.Context;
import android.support.p002v4.view.ViewCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.SurfaceViewPreview */
/* loaded from: classes2.dex */
public class SurfaceViewPreview extends PreviewImpl {
    final SurfaceView mSurfaceView;

    public SurfaceViewPreview(Context context, ViewGroup viewGroup) {
        this.mSurfaceView = (SurfaceView) View.inflate(context, R$layout.web_sdk_surface_view, viewGroup).findViewById(R$id.surface_view);
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.setType(3);
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.SurfaceViewPreview.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                SurfaceViewPreview.this.setSize(i2, i3);
                if (!ViewCompat.isInLayout(SurfaceViewPreview.this.mSurfaceView)) {
                    SurfaceViewPreview.this.dispatchSurfaceChanged();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                SurfaceViewPreview.this.setSize(0, 0);
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public SurfaceHolder getSurfaceHolder() {
        return this.mSurfaceView.getHolder();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public View getView() {
        return this.mSurfaceView;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public Class getOutputClass() {
        return SurfaceHolder.class;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public boolean isReady() {
        return (getWidth() == 0 || getHeight() == 0) ? false : true;
    }
}
