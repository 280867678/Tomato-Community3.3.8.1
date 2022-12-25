package com.bumptech.glide.gifdecoder;

import android.support.annotation.ColorInt;

/* loaded from: classes2.dex */
class GifFrame {
    static final int DISPOSAL_BACKGROUND = 2;
    static final int DISPOSAL_NONE = 1;
    static final int DISPOSAL_PREVIOUS = 3;
    static final int DISPOSAL_UNSPECIFIED = 0;
    int bufferFrameStart;
    int delay;
    int dispose;

    /* renamed from: ih */
    int f1199ih;
    boolean interlace;

    /* renamed from: iw */
    int f1200iw;

    /* renamed from: ix */
    int f1201ix;

    /* renamed from: iy */
    int f1202iy;
    @ColorInt
    int[] lct;
    int transIndex;
    boolean transparency;
}
