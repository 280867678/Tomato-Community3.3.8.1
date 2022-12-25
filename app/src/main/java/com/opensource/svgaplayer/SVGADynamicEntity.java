package com.opensource.svgaplayer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.BoringLayout;
import android.text.StaticLayout;
import android.text.TextPaint;
import java.util.HashMap;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SVGADynamicEntity.kt */
/* loaded from: classes3.dex */
public final class SVGADynamicEntity {
    private boolean isTextDirty;
    private HashMap<String, Boolean> dynamicHidden = new HashMap<>();
    private HashMap<String, Bitmap> dynamicImage = new HashMap<>();
    private HashMap<String, String> dynamicText = new HashMap<>();
    private HashMap<String, TextPaint> dynamicTextPaint = new HashMap<>();
    private HashMap<String, StaticLayout> dynamicStaticLayoutText = new HashMap<>();
    private HashMap<String, BoringLayout> dynamicBoringLayoutText = new HashMap<>();
    private HashMap<String, Function2<Canvas, Integer, Boolean>> dynamicDrawer = new HashMap<>();
    private HashMap<String, int[]> mClickMap = new HashMap<>();
    private HashMap<String, IClickAreaListener> dynamicIClickArea = new HashMap<>();
    private HashMap<String, Function4<Canvas, Integer, Integer, Integer, Boolean>> dynamicDrawerSized = new HashMap<>();

    public final HashMap<String, Boolean> getDynamicHidden$library_release() {
        return this.dynamicHidden;
    }

    public final HashMap<String, Bitmap> getDynamicImage$library_release() {
        return this.dynamicImage;
    }

    public final HashMap<String, String> getDynamicText$library_release() {
        return this.dynamicText;
    }

    public final HashMap<String, TextPaint> getDynamicTextPaint$library_release() {
        return this.dynamicTextPaint;
    }

    public final HashMap<String, StaticLayout> getDynamicStaticLayoutText$library_release() {
        return this.dynamicStaticLayoutText;
    }

    public final HashMap<String, BoringLayout> getDynamicBoringLayoutText$library_release() {
        return this.dynamicBoringLayoutText;
    }

    public final HashMap<String, Function2<Canvas, Integer, Boolean>> getDynamicDrawer$library_release() {
        return this.dynamicDrawer;
    }

    public final HashMap<String, int[]> getMClickMap$library_release() {
        return this.mClickMap;
    }

    public final HashMap<String, IClickAreaListener> getDynamicIClickArea$library_release() {
        return this.dynamicIClickArea;
    }

    public final HashMap<String, Function4<Canvas, Integer, Integer, Integer, Boolean>> getDynamicDrawerSized$library_release() {
        return this.dynamicDrawerSized;
    }

    public final boolean isTextDirty$library_release() {
        return this.isTextDirty;
    }

    public final void setTextDirty$library_release(boolean z) {
        this.isTextDirty = z;
    }

    public final void setDynamicImage(Bitmap bitmap, String forKey) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.dynamicImage.put(forKey, bitmap);
    }

    public final void setDynamicText(String text, TextPaint textPaint, String forKey) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(textPaint, "textPaint");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.isTextDirty = true;
        this.dynamicText.put(forKey, text);
        this.dynamicTextPaint.put(forKey, textPaint);
    }
}
