package com.taobao.gcanvas.surface;

import android.content.Context;
import android.view.TextureView;
import com.taobao.gcanvas.util.GLog;

/* loaded from: classes3.dex */
public class GTextureView extends TextureView {
    private GTextureViewCallback mCallback;

    public void sendEvent() {
    }

    public GTextureView(Context context, String str) {
        super(context);
        init(str);
    }

    public void setBackgroundColor(String str) {
        GTextureViewCallback gTextureViewCallback = this.mCallback;
        if (gTextureViewCallback != null) {
            gTextureViewCallback.setBackgroundColor(str);
        }
    }

    private void init(String str) {
        this.mCallback = new GTextureViewCallback(this, str);
        setSurfaceTextureListener(this.mCallback);
        setOpaque(false);
        setLayerType(2, null);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        GLog.m3565d("on window visibility changed.visibility=" + i);
    }

    public String getCanvasKey() {
        GTextureViewCallback gTextureViewCallback = this.mCallback;
        return gTextureViewCallback != null ? gTextureViewCallback.getKey() : "";
    }
}
