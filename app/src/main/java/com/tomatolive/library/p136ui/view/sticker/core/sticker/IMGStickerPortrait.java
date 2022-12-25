package com.tomatolive.library.p136ui.view.sticker.core.sticker;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerPortrait */
/* loaded from: classes3.dex */
public interface IMGStickerPortrait {

    /* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerPortrait$Callback */
    /* loaded from: classes3.dex */
    public interface Callback {
        <V extends View & IMGSticker> void onDismiss(V v);

        <V extends View & IMGSticker> boolean onRemove(V v);

        <V extends View & IMGSticker> void onShowing(V v);
    }

    boolean dismiss();

    RectF getFrame();

    boolean isShowing();

    void onSticker(Canvas canvas);

    void registerCallback(Callback callback);

    boolean remove();

    boolean show();

    void unregisterCallback(Callback callback);
}
