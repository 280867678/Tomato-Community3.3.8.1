package com.airbnb.lottie.parser;

import android.util.JsonReader;
import android.util.Log;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.Mask;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class MaskParser {
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008e, code lost:
        if (r0.equals("a") != false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0094 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Mask parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        char c;
        jsonReader.beginObject();
        Mask.MaskMode maskMode = null;
        AnimatableShapeValue animatableShapeValue = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            char c2 = 0;
            if (hashCode == 111) {
                if (nextName.equals("o")) {
                    c = 2;
                }
                c = 65535;
            } else if (hashCode != 3588) {
                if (hashCode == 3357091 && nextName.equals("mode")) {
                    c = 0;
                }
                c = 65535;
            } else {
                if (nextName.equals("pt")) {
                    c = 1;
                }
                c = 65535;
            }
            if (c == 0) {
                String nextString = jsonReader.nextString();
                int hashCode2 = nextString.hashCode();
                if (hashCode2 != 97) {
                    if (hashCode2 != 105) {
                        if (hashCode2 == 115 && nextString.equals("s")) {
                            c2 = 1;
                            if (c2 != 0) {
                                maskMode = Mask.MaskMode.MaskModeAdd;
                            } else if (c2 == 1) {
                                maskMode = Mask.MaskMode.MaskModeSubtract;
                            } else if (c2 == 2) {
                                maskMode = Mask.MaskMode.MaskModeIntersect;
                            } else {
                                Log.w("LOTTIE", "Unknown mask mode " + nextName + ". Defaulting to Add.");
                                maskMode = Mask.MaskMode.MaskModeAdd;
                            }
                        }
                        c2 = 65535;
                        if (c2 != 0) {
                        }
                    } else {
                        if (nextString.equals("i")) {
                            c2 = 2;
                            if (c2 != 0) {
                            }
                        }
                        c2 = 65535;
                        if (c2 != 0) {
                        }
                    }
                }
            } else if (c == 1) {
                animatableShapeValue = AnimatableValueParser.parseShapeData(jsonReader, lottieComposition);
            } else if (c == 2) {
                animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new Mask(maskMode, animatableShapeValue, animatableIntegerValue);
    }
}
