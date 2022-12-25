package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.value.ScaleXY;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AnimatableTransformParser {
    public static AnimatableTransform parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        char c;
        boolean z = jsonReader.peek() == JsonToken.BEGIN_OBJECT;
        if (z) {
            jsonReader.beginObject();
        }
        AnimatablePathValue animatablePathValue = null;
        AnimatableScaleValue animatableScaleValue = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatableValue<PointF, PointF> animatableValue = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            if (hashCode == 97) {
                if (nextName.equals("a")) {
                    c = 0;
                }
                c = 65535;
            } else if (hashCode == 3242) {
                if (nextName.equals("eo")) {
                    c = 7;
                }
                c = 65535;
            } else if (hashCode == 3656) {
                if (nextName.equals("rz")) {
                    c = 3;
                }
                c = 65535;
            } else if (hashCode == 3676) {
                if (nextName.equals("so")) {
                    c = 6;
                }
                c = 65535;
            } else if (hashCode == 111) {
                if (nextName.equals("o")) {
                    c = 5;
                }
                c = 65535;
            } else if (hashCode == 112) {
                if (nextName.equals("p")) {
                    c = 1;
                }
                c = 65535;
            } else if (hashCode != 114) {
                if (hashCode == 115 && nextName.equals("s")) {
                    c = 2;
                }
                c = 65535;
            } else {
                if (nextName.equals("r")) {
                    c = 4;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.nextName().equals("k")) {
                            animatablePathValue = AnimatablePathValueParser.parse(jsonReader, lottieComposition);
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    continue;
                case 1:
                    animatableValue = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
                    continue;
                case 2:
                    animatableScaleValue = AnimatableValueParser.parseScale(jsonReader, lottieComposition);
                    continue;
                case 3:
                    lottieComposition.addWarning("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    continue;
                case 6:
                    animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                case 7:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                default:
                    jsonReader.skipValue();
                    continue;
            }
            animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
        }
        if (z) {
            jsonReader.endObject();
        }
        if (animatablePathValue == null) {
            Log.w("LOTTIE", "Layer has no transform property. You may be using an unsupported layer type such as a camera.");
            animatablePathValue = new AnimatablePathValue();
        }
        AnimatablePathValue animatablePathValue2 = animatablePathValue;
        if (animatableScaleValue == null) {
            animatableScaleValue = new AnimatableScaleValue(new ScaleXY(1.0f, 1.0f));
        }
        AnimatableScaleValue animatableScaleValue2 = animatableScaleValue;
        if (animatableIntegerValue == null) {
            animatableIntegerValue = new AnimatableIntegerValue();
        }
        return new AnimatableTransform(animatablePathValue2, animatableValue, animatableScaleValue2, animatableFloatValue, animatableIntegerValue, animatableFloatValue2, animatableFloatValue3);
    }
}
