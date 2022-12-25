package com.taobao.gcanvas.surface;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.taobao.gcanvas.GCanvasJNI;
import com.taobao.gcanvas.util.GLog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class GTextureViewCallback implements TextureView.SurfaceTextureListener {
    private String mBackgroundColor = "#ffffff";
    private ArrayList<TextureView.SurfaceTextureListener> mDelegateLists;
    private final String mKey;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private TextureView mTextureview;

    private native void onRenderExit(String str);

    private native void onSurfaceChanged(String str, Surface surface, int i, int i2, int i3, String str2);

    private native void onSurfaceCreated(String str, Surface surface);

    private native void onSurfaceDestroyed(String str, Surface surface);

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    static {
        GCanvasJNI.load();
    }

    public GTextureViewCallback(TextureView textureView, String str) {
        this.mKey = str;
        this.mTextureview = textureView;
    }

    public void setBackgroundColor(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mBackgroundColor = str;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    @TargetApi(16)
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        GLog.m3565d("on surfaceTexture Available.");
        SurfaceTexture surfaceTexture2 = this.mSurfaceTexture;
        if (surfaceTexture2 == null) {
            this.mSurface = new Surface(surfaceTexture);
            this.mSurfaceTexture = surfaceTexture;
        } else {
            this.mTextureview.setSurfaceTexture(surfaceTexture2);
        }
        onSurfaceChanged(this.mKey, this.mSurface, 0, i, i2, this.mBackgroundColor);
        GCanvasJNI.refreshArguments(this.mKey);
        if (GCanvasJNI.sendEvent(this.mKey) && (this.mTextureview instanceof GTextureView)) {
            GLog.m3565d("start to send event in GSurfaceCallback.");
            ((GTextureView) this.mTextureview).sendEvent();
        }
        ArrayList<TextureView.SurfaceTextureListener> arrayList = this.mDelegateLists;
        if (arrayList != null) {
            Iterator<TextureView.SurfaceTextureListener> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().onSurfaceTextureAvailable(surfaceTexture, i, i2);
            }
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        GLog.m3565d("on surfaceTexture changed.");
        if (this.mSurface == null) {
            this.mSurface = new Surface(surfaceTexture);
            this.mSurfaceTexture = surfaceTexture;
        }
        onSurfaceChanged(this.mKey, this.mSurface, 0, i, i2, this.mBackgroundColor);
        ArrayList<TextureView.SurfaceTextureListener> arrayList = this.mDelegateLists;
        if (arrayList != null) {
            Iterator<TextureView.SurfaceTextureListener> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
            }
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        GLog.m3565d("on surfaceTexture destroyed.");
        if (this.mSurfaceTexture != null && this.mSurface != null) {
            ArrayList<TextureView.SurfaceTextureListener> arrayList = this.mDelegateLists;
            if (arrayList != null) {
                Iterator<TextureView.SurfaceTextureListener> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    it2.next().onSurfaceTextureDestroyed(surfaceTexture);
                }
            }
            onSurfaceDestroyed(this.mKey, this.mSurface);
        }
        return true;
    }

    public String getKey() {
        return this.mKey;
    }
}
