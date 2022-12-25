package com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl;

@TargetApi(14)
/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.TextureViewPreview */
/* loaded from: classes2.dex */
public class TextureViewPreview extends PreviewImpl {
    private int mDisplayOrientation;
    private final TextureView mTextureView;

    public TextureViewPreview(Context context, ViewGroup viewGroup) {
        this.mTextureView = (TextureView) View.inflate(context, R$layout.web_sdk_texture_view, viewGroup).findViewById(R$id.texture_view);
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.TextureViewPreview.1
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                TextureViewPreview.this.setSize(i, i2);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                TextureViewPreview.this.setSize(i, i2);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                TextureViewPreview.this.setSize(0, 0);
                return true;
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    /* renamed from: getSurfaceTexture */
    public SurfaceTexture mo6190getSurfaceTexture() {
        return this.mTextureView.getSurfaceTexture();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public View getView() {
        return this.mTextureView;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public Class getOutputClass() {
        return SurfaceTexture.class;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl
    public boolean isReady() {
        return this.mTextureView.getSurfaceTexture() != null;
    }

    void configureTransform() {
        Matrix matrix = new Matrix();
        int i = this.mDisplayOrientation;
        if (i % 180 == 90) {
            float width = getWidth();
            float height = getHeight();
            matrix.setPolyToPoly(new float[]{0.0f, 0.0f, width, 0.0f, 0.0f, height, width, height}, 0, this.mDisplayOrientation == 90 ? new float[]{0.0f, height, 0.0f, 0.0f, width, height, width, 0.0f} : new float[]{width, 0.0f, width, height, 0.0f, 0.0f, 0.0f, height}, 0, 4);
        } else if (i == 180) {
            matrix.postRotate(180.0f, getWidth() / 2, getHeight() / 2);
        }
        this.mTextureView.setTransform(matrix);
    }
}
