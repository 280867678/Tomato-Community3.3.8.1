package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class GradientStrokeParser {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static GradientStroke parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        char c;
        ShapeStroke.LineCapType lineCapType;
        ShapeStroke.LineJoinType lineJoinType;
        char c2;
        char c3;
        ArrayList arrayList = new ArrayList();
        String str = null;
        GradientType gradientType = null;
        AnimatableGradientColorValue animatableGradientColorValue = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatablePointValue animatablePointValue = null;
        AnimatablePointValue animatablePointValue2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        ShapeStroke.LineCapType lineCapType2 = null;
        ShapeStroke.LineJoinType lineJoinType2 = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            AnimatableFloatValue animatableFloatValue3 = animatableFloatValue2;
            if (hashCode == 100) {
                if (nextName.equals("d")) {
                    c = '\t';
                }
                c = 65535;
            } else if (hashCode == 101) {
                if (nextName.equals("e")) {
                    c = 5;
                }
                c = 65535;
            } else if (hashCode == 103) {
                if (nextName.equals("g")) {
                    c = 1;
                }
                c = 65535;
            } else if (hashCode == 111) {
                if (nextName.equals("o")) {
                    c = 2;
                }
                c = 65535;
            } else if (hashCode == 119) {
                if (nextName.equals("w")) {
                    c = 6;
                }
                c = 65535;
            } else if (hashCode == 3447) {
                if (nextName.equals("lc")) {
                    c = 7;
                }
                c = 65535;
            } else if (hashCode == 3454) {
                if (nextName.equals("lj")) {
                    c = '\b';
                }
                c = 65535;
            } else if (hashCode == 3519) {
                if (nextName.equals("nm")) {
                    c = 0;
                }
                c = 65535;
            } else if (hashCode != 115) {
                if (hashCode == 116 && nextName.equals("t")) {
                    c = 3;
                }
                c = 65535;
            } else {
                if (nextName.equals("s")) {
                    c = 4;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    lineCapType = lineCapType2;
                    str = jsonReader.nextString();
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case 1:
                    lineCapType = lineCapType2;
                    lineJoinType = lineJoinType2;
                    jsonReader.beginObject();
                    int i = -1;
                    while (jsonReader.hasNext()) {
                        String nextName2 = jsonReader.nextName();
                        int hashCode2 = nextName2.hashCode();
                        if (hashCode2 != 107) {
                            if (hashCode2 == 112 && nextName2.equals("p")) {
                                c2 = 0;
                            }
                            c2 = 65535;
                        } else {
                            if (nextName2.equals("k")) {
                                c2 = 1;
                            }
                            c2 = 65535;
                        }
                        if (c2 == 0) {
                            i = jsonReader.nextInt();
                        } else if (c2 == 1) {
                            animatableGradientColorValue = AnimatableValueParser.parseGradientColor(jsonReader, lottieComposition, i);
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    animatableFloatValue2 = animatableFloatValue3;
                    lineJoinType2 = lineJoinType;
                    break;
                case 2:
                    lineCapType = lineCapType2;
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case 3:
                    lineCapType = lineCapType2;
                    lineJoinType = lineJoinType2;
                    gradientType = jsonReader.nextInt() == 1 ? GradientType.Linear : GradientType.Radial;
                    animatableFloatValue2 = animatableFloatValue3;
                    lineJoinType2 = lineJoinType;
                    break;
                case 4:
                    lineCapType = lineCapType2;
                    animatablePointValue = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case 5:
                    lineCapType = lineCapType2;
                    animatablePointValue2 = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case 6:
                    lineCapType = lineCapType2;
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case 7:
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    animatableFloatValue2 = animatableFloatValue3;
                    lineJoinType2 = lineJoinType2;
                    break;
                case '\b':
                    lineCapType = lineCapType2;
                    lineJoinType2 = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
                case '\t':
                    jsonReader.beginArray();
                    AnimatableFloatValue animatableFloatValue4 = animatableFloatValue3;
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String str2 = null;
                        AnimatableFloatValue animatableFloatValue5 = null;
                        while (jsonReader.hasNext()) {
                            AnimatableFloatValue animatableFloatValue6 = animatableFloatValue4;
                            String nextName3 = jsonReader.nextName();
                            ShapeStroke.LineJoinType lineJoinType3 = lineJoinType2;
                            int hashCode3 = nextName3.hashCode();
                            ShapeStroke.LineCapType lineCapType3 = lineCapType2;
                            if (hashCode3 != 110) {
                                if (hashCode3 == 118 && nextName3.equals("v")) {
                                    c3 = 1;
                                }
                                c3 = 65535;
                            } else {
                                if (nextName3.equals("n")) {
                                    c3 = 0;
                                }
                                c3 = 65535;
                            }
                            if (c3 == 0) {
                                str2 = jsonReader.nextString();
                            } else if (c3 == 1) {
                                animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                            } else {
                                jsonReader.skipValue();
                            }
                            animatableFloatValue4 = animatableFloatValue6;
                            lineJoinType2 = lineJoinType3;
                            lineCapType2 = lineCapType3;
                        }
                        ShapeStroke.LineCapType lineCapType4 = lineCapType2;
                        ShapeStroke.LineJoinType lineJoinType4 = lineJoinType2;
                        AnimatableFloatValue animatableFloatValue7 = animatableFloatValue4;
                        jsonReader.endObject();
                        if (str2.equals("o")) {
                            animatableFloatValue4 = animatableFloatValue5;
                        } else {
                            if (str2.equals("d") || str2.equals("g")) {
                                arrayList.add(animatableFloatValue5);
                            }
                            animatableFloatValue4 = animatableFloatValue7;
                        }
                        lineJoinType2 = lineJoinType4;
                        lineCapType2 = lineCapType4;
                    }
                    lineCapType = lineCapType2;
                    lineJoinType = lineJoinType2;
                    AnimatableFloatValue animatableFloatValue8 = animatableFloatValue4;
                    jsonReader.endArray();
                    if (arrayList.size() == 1) {
                        arrayList.add(arrayList.get(0));
                    }
                    animatableFloatValue2 = animatableFloatValue8;
                    lineJoinType2 = lineJoinType;
                    break;
                default:
                    lineCapType = lineCapType2;
                    jsonReader.skipValue();
                    animatableFloatValue2 = animatableFloatValue3;
                    break;
            }
            lineCapType2 = lineCapType;
        }
        return new GradientStroke(str, gradientType, animatableGradientColorValue, animatableIntegerValue, animatablePointValue, animatablePointValue2, animatableFloatValue, lineCapType2, lineJoinType2, arrayList, animatableFloatValue2);
    }
}
