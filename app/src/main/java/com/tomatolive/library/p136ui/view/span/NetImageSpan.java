package com.tomatolive.library.p136ui.view.span;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.p136ui.interfaces.OnGlideDownloadCallback;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.view.span.NetImageSpan */
/* loaded from: classes3.dex */
public class NetImageSpan extends FDynamicDrawableSpan {
    private Bitmap mBitmap;
    private Drawable mDrawable;
    private String mUrl;

    @Override // com.tomatolive.library.p136ui.view.span.FDynamicDrawableSpan
    protected Bitmap onGetBitmap() {
        return null;
    }

    public NetImageSpan(View view) {
        super(view);
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    @Override // com.tomatolive.library.p136ui.view.span.FDynamicDrawableSpan
    protected int getDefaultDrawableResId() {
        return R$drawable.fq_ic_live_msg_placeholder_prefix;
    }

    @Override // com.tomatolive.library.p136ui.view.span.FDynamicDrawableSpan
    protected Drawable onGetDrawable() {
        this.mUrl = GlideUtils.formatDownUrl(this.mUrl);
        this.mDrawable = GlideUtils.getLocalCacheFile2Drawable(this.mUrl);
        if (this.mDrawable == null) {
            GlideUtils.downloadFile2Drawable(getContext(), this.mUrl, new OnGlideDownloadCallback<Drawable>() { // from class: com.tomatolive.library.ui.view.span.NetImageSpan.1
                @Override // com.tomatolive.library.p136ui.interfaces.OnGlideDownloadCallback
                public void onLoadStarted(@Nullable Drawable drawable) {
                }

                @Override // com.tomatolive.library.p136ui.interfaces.OnGlideDownloadCallback
                public void onLoadSuccess(Drawable drawable) {
                    NetImageSpan.this.mDrawable = drawable;
                    NetImageSpan.this.updateCacheDrawable();
                    NetImageSpan.this.getView().postInvalidate();
                }

                @Override // com.tomatolive.library.p136ui.interfaces.OnGlideDownloadCallback
                public void onLoadFailed(@Nullable Drawable drawable) {
                    if (NetImageSpan.this.getContext() == null) {
                        return;
                    }
                    NetImageSpan netImageSpan = NetImageSpan.this;
                    netImageSpan.mDrawable = ContextCompat.getDrawable(netImageSpan.getContext(), R$drawable.fq_ic_live_msg_placeholder_prefix);
                }
            });
        }
        return this.mDrawable;
    }
}
