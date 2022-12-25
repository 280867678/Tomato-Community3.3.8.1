package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.p002v4.util.SparseArrayCompat;
import android.support.p002v4.view.animation.PathInterpolatorCompat;
import android.util.JsonReader;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
class KeyframeParser {
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    KeyframeParser() {
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    @Nullable
    private static WeakReference<Interpolator> getInterpolator(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = pathInterpolatorCache().get(i);
        }
        return weakReference;
    }

    private static void putInterpolator(int i, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i, weakReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z) throws IOException {
        if (z) {
            return parseKeyframe(lottieComposition, jsonReader, f, valueParser);
        }
        return parseStaticValue(jsonReader, f, valueParser);
    }

    private static <T> Keyframe<T> parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        T t;
        char c;
        jsonReader.beginObject();
        boolean z = false;
        PointF pointF = null;
        PointF pointF2 = null;
        T t2 = null;
        T t3 = null;
        float f2 = 0.0f;
        PointF pointF3 = null;
        PointF pointF4 = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            boolean z2 = true;
            if (hashCode == 101) {
                if (nextName.equals("e")) {
                    c = 2;
                }
                c = 65535;
            } else if (hashCode == 111) {
                if (nextName.equals("o")) {
                    c = 3;
                }
                c = 65535;
            } else if (hashCode == 3701) {
                if (nextName.equals("ti")) {
                    c = 7;
                }
                c = 65535;
            } else if (hashCode == 3707) {
                if (nextName.equals("to")) {
                    c = 6;
                }
                c = 65535;
            } else if (hashCode == 104) {
                if (nextName.equals("h")) {
                    c = 5;
                }
                c = 65535;
            } else if (hashCode == 105) {
                if (nextName.equals("i")) {
                    c = 4;
                }
                c = 65535;
            } else if (hashCode != 115) {
                if (hashCode == 116 && nextName.equals("t")) {
                    c = 0;
                }
                c = 65535;
            } else {
                if (nextName.equals("s")) {
                    c = 1;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    f2 = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    t3 = valueParser.mo5817parse(jsonReader, f);
                    break;
                case 2:
                    t2 = valueParser.mo5817parse(jsonReader, f);
                    break;
                case 3:
                    pointF = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 4:
                    pointF2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 5:
                    if (jsonReader.nextInt() != 1) {
                        z2 = false;
                    }
                    z = z2;
                    break;
                case 6:
                    pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointF4 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolator = LINEAR_INTERPOLATOR;
            t = t3;
        } else if (pointF != null && pointF2 != null) {
            float f3 = -f;
            pointF.x = MiscUtils.clamp(pointF.x, f3, f);
            pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
            pointF2.x = MiscUtils.clamp(pointF2.x, f3, f);
            pointF2.y = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
            int hashFor = Utils.hashFor(pointF.x, pointF.y, pointF2.x, pointF2.y);
            WeakReference<Interpolator> interpolator2 = getInterpolator(hashFor);
            Interpolator interpolator3 = interpolator2 != null ? interpolator2.get() : null;
            if (interpolator2 == null || interpolator3 == null) {
                Interpolator create = PathInterpolatorCompat.create(pointF.x / f, pointF.y / f, pointF2.x / f, pointF2.y / f);
                try {
                    putInterpolator(hashFor, new WeakReference(create));
                } catch (ArrayIndexOutOfBoundsException unused) {
                }
                interpolator3 = create;
            }
            t = t2;
            interpolator = interpolator3;
        } else {
            interpolator = LINEAR_INTERPOLATOR;
            t = t2;
        }
        Keyframe<T> keyframe = new Keyframe<>(lottieComposition, t3, t, interpolator, f2, null);
        keyframe.pathCp1 = pointF3;
        keyframe.pathCp2 = pointF4;
        return keyframe;
    }

    private static <T> Keyframe<T> parseStaticValue(JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.mo5817parse(jsonReader, f));
    }
}
