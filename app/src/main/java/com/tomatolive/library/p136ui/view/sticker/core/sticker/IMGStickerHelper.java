package com.tomatolive.library.p136ui.view.sticker.core.sticker;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGSticker;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerHelper */
/* loaded from: classes3.dex */
public class IMGStickerHelper<StickerView extends View & IMGSticker> implements IMGStickerPortrait, IMGStickerPortrait.Callback {
    private boolean isShowing = false;
    private IMGStickerPortrait.Callback mCallback;
    private RectF mFrame;
    private StickerView mView;

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void onSticker(Canvas canvas) {
    }

    public IMGStickerHelper(StickerView stickerview) {
        this.mView = stickerview;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean show() {
        if (!isShowing()) {
            this.isShowing = true;
            onShowing(this.mView);
            return true;
        }
        return false;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean remove() {
        return onRemove(this.mView);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean dismiss() {
        if (isShowing()) {
            this.isShowing = false;
            onDismiss(this.mView);
            return true;
        }
        return false;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean isShowing() {
        return this.isShowing;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public RectF getFrame() {
        if (this.mFrame == null) {
            this.mFrame = new RectF(0.0f, 0.0f, this.mView.getWidth(), this.mView.getHeight());
            float x = this.mView.getX() + this.mView.getPivotX();
            float y = this.mView.getY() + this.mView.getPivotY();
            Matrix matrix = new Matrix();
            matrix.setTranslate(this.mView.getX(), this.mView.getY());
            matrix.postScale(this.mView.getScaleX(), this.mView.getScaleY(), x, y);
            matrix.mapRect(this.mFrame);
        }
        return this.mFrame;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void registerCallback(IMGStickerPortrait.Callback callback) {
        this.mCallback = callback;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void unregisterCallback(IMGStickerPortrait.Callback callback) {
        this.mCallback = null;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> boolean onRemove(V v) {
        IMGStickerPortrait.Callback callback = this.mCallback;
        return callback != null && callback.onRemove(v);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> void onDismiss(V v) {
        this.mFrame = null;
        v.invalidate();
        IMGStickerPortrait.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onDismiss(v);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> void onShowing(V v) {
        v.invalidate();
        IMGStickerPortrait.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onShowing(v);
        }
    }
}
