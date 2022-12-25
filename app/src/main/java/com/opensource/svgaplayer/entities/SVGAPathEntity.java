package com.opensource.svgaplayer.entities;

import android.graphics.Path;
import android.support.media.ExifInterface;
import com.opensource.svgaplayer.utils.SVGAStructs;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.Set;
import java.util.StringTokenizer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsJVM;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: SVGAPathEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAPathEntity {
    private Path cachedPath;
    private final String replacedValue;

    public SVGAPathEntity(String originValue) {
        boolean contains$default;
        Intrinsics.checkParameterIsNotNull(originValue, "originValue");
        contains$default = StringsKt__StringsKt.contains$default(originValue, ",", false, 2, null);
        this.replacedValue = contains$default ? StringsJVM.replace$default(originValue, ",", ConstantUtils.PLACEHOLDER_STR_ONE, false, 4, null) : originValue;
    }

    public final void buildPath(Path toPath) {
        Set set;
        Intrinsics.checkParameterIsNotNull(toPath, "toPath");
        Path path = this.cachedPath;
        if (path != null) {
            toPath.set(path);
            return;
        }
        Path path2 = new Path();
        StringTokenizer stringTokenizer = new StringTokenizer(this.replacedValue, "MLHVCSQRAZmlhvcsqraz", true);
        String str = "";
        while (stringTokenizer.hasMoreTokens()) {
            String segment = stringTokenizer.nextToken();
            Intrinsics.checkExpressionValueIsNotNull(segment, "segment");
            if (!(segment.length() == 0)) {
                set = SVGAPathEntityKt.VALID_METHODS;
                if (set.contains(segment)) {
                    if (Intrinsics.areEqual(segment, "Z") || Intrinsics.areEqual(segment, "z")) {
                        operate(path2, segment, new StringTokenizer("", ""));
                    }
                    str = segment;
                } else {
                    operate(path2, str, new StringTokenizer(segment, ConstantUtils.PLACEHOLDER_STR_ONE));
                }
            }
        }
        this.cachedPath = path2;
        toPath.set(path2);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void operate(Path path, String str, StringTokenizer stringTokenizer) {
        SVGAStructs sVGAStructs;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            try {
                String s = stringTokenizer.nextToken();
                Intrinsics.checkExpressionValueIsNotNull(s, "s");
                if (!(s.length() == 0)) {
                    if (i == 0) {
                        f = Float.parseFloat(s);
                    }
                    if (i == 1) {
                        f2 = Float.parseFloat(s);
                    }
                    if (i == 2) {
                        f3 = Float.parseFloat(s);
                    }
                    if (i == 3) {
                        f4 = Float.parseFloat(s);
                    }
                    if (i == 4) {
                        f5 = Float.parseFloat(s);
                    }
                    if (i == 5) {
                        f6 = Float.parseFloat(s);
                    }
                    i++;
                }
            } catch (Exception unused) {
            }
        }
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f4;
        SVGAStructs sVGAStructs2 = new SVGAStructs(0.0f, 0.0f, 0.0f);
        if (Intrinsics.areEqual(str, "M")) {
            path.moveTo(f7, f8);
            sVGAStructs2 = new SVGAStructs(f7, f8, 0.0f);
        } else if (Intrinsics.areEqual(str, "m")) {
            path.rMoveTo(f7, f8);
            sVGAStructs = new SVGAStructs(sVGAStructs2.getX() + f7, sVGAStructs2.getY() + f8, 0.0f);
            if (!Intrinsics.areEqual(str, "L")) {
                path.lineTo(f7, f8);
            } else if (Intrinsics.areEqual(str, "l")) {
                path.rLineTo(f7, f8);
            }
            if (!Intrinsics.areEqual(str, "C")) {
                path.cubicTo(f7, f8, f9, f10, f5, f6);
            } else if (Intrinsics.areEqual(str, "c")) {
                path.rCubicTo(f7, f8, f9, f10, f5, f6);
            }
            if (!Intrinsics.areEqual(str, "Q")) {
                path.quadTo(f7, f8, f9, f10);
            } else if (Intrinsics.areEqual(str, "q")) {
                path.rQuadTo(f7, f8, f9, f10);
            }
            if (!Intrinsics.areEqual(str, "H")) {
                path.lineTo(f7, sVGAStructs.getY());
            } else if (Intrinsics.areEqual(str, "h")) {
                path.rLineTo(f7, 0.0f);
            }
            if (!Intrinsics.areEqual(str, ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
                path.lineTo(sVGAStructs.getX(), f7);
            } else if (Intrinsics.areEqual(str, "v")) {
                path.rLineTo(0.0f, f7);
            }
            if (!Intrinsics.areEqual(str, "Z")) {
                path.close();
                return;
            } else if (!Intrinsics.areEqual(str, "z")) {
                return;
            } else {
                path.close();
                return;
            }
        }
        sVGAStructs = sVGAStructs2;
        if (!Intrinsics.areEqual(str, "L")) {
        }
        if (!Intrinsics.areEqual(str, "C")) {
        }
        if (!Intrinsics.areEqual(str, "Q")) {
        }
        if (!Intrinsics.areEqual(str, "H")) {
        }
        if (!Intrinsics.areEqual(str, ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
        }
        if (!Intrinsics.areEqual(str, "Z")) {
        }
    }
}
