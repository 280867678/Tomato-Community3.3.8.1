package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

/* renamed from: com.tencent.liteav.renderer.d */
/* loaded from: classes3.dex */
public class TXCTextureViewRender extends TXCVideoRender {
    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    protected void mo891a(SurfaceTexture surfaceTexture) {
        try {
            if (this.f5148l == null) {
                return;
            }
            this.f5148l.mo861a(surfaceTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: b */
    protected void mo879b(SurfaceTexture surfaceTexture) {
        try {
            if (this.f5148l == null) {
                return;
            }
            this.f5148l.mo860b(surfaceTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    public SurfaceTexture mo898a() {
        TextureView textureView = this.f5139c;
        if (textureView == null || !textureView.isAvailable()) {
            return null;
        }
        return this.f5139c.getSurfaceTexture();
    }
}
