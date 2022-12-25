package com.opensource.svgaplayer.entities;

import android.support.media.ExifInterface;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: SVGAPathEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAPathEntityKt {
    private static final Set<String> VALID_METHODS;

    static {
        Set<String> of;
        of = SetsKt__SetsKt.setOf((Object[]) new String[]{"M", "L", "H", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "C", ExifInterface.LATITUDE_SOUTH, "Q", "R", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "Z", "m", "l", "h", "v", "c", "s", "q", "r", "a", "z"});
        VALID_METHODS = of;
    }
}
