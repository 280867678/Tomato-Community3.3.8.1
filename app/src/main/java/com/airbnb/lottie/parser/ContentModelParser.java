package com.airbnb.lottie.parser;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class ContentModelParser {
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00bd, code lost:
        if (r2.equals("gs") != false) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044 A[SYNTHETIC] */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ContentModel parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ContentModel contentModel;
        String str;
        char c;
        jsonReader.beginObject();
        char c2 = 2;
        int i = 2;
        while (true) {
            contentModel = null;
            if (!jsonReader.hasNext()) {
                str = null;
                break;
            }
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            if (hashCode != 100) {
                if (hashCode == 3717 && nextName.equals("ty")) {
                    c = 0;
                    if (c != 0) {
                        str = jsonReader.nextString();
                        break;
                    } else if (c == 1) {
                        i = jsonReader.nextInt();
                    } else {
                        jsonReader.skipValue();
                    }
                }
                c = 65535;
                if (c != 0) {
                }
            } else {
                if (nextName.equals("d")) {
                    c = 1;
                    if (c != 0) {
                    }
                }
                c = 65535;
                if (c != 0) {
                }
            }
        }
        if (str == null) {
            return null;
        }
        switch (str.hashCode()) {
            case 3239:
                if (str.equals("el")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 3270:
                if (str.equals("fl")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 3295:
                if (str.equals("gf")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 3307:
                if (str.equals("gr")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 3308:
                break;
            case 3488:
                if (str.equals("mm")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case 3633:
                if (str.equals("rc")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 3646:
                if (str.equals("rp")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case 3669:
                if (str.equals("sh")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 3679:
                if (str.equals("sr")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 3681:
                if (str.equals("st")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 3705:
                if (str.equals("tm")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 3710:
                if (str.equals("tr")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                contentModel = ShapeGroupParser.parse(jsonReader, lottieComposition);
                break;
            case 1:
                contentModel = ShapeStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case 2:
                contentModel = GradientStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case 3:
                contentModel = ShapeFillParser.parse(jsonReader, lottieComposition);
                break;
            case 4:
                contentModel = GradientFillParser.parse(jsonReader, lottieComposition);
                break;
            case 5:
                contentModel = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                break;
            case 6:
                contentModel = ShapePathParser.parse(jsonReader, lottieComposition);
                break;
            case 7:
                contentModel = CircleShapeParser.parse(jsonReader, lottieComposition, i);
                break;
            case '\b':
                contentModel = RectangleShapeParser.parse(jsonReader, lottieComposition);
                break;
            case '\t':
                contentModel = ShapeTrimPathParser.parse(jsonReader, lottieComposition);
                break;
            case '\n':
                contentModel = PolystarShapeParser.parse(jsonReader, lottieComposition);
                break;
            case 11:
                contentModel = MergePathsParser.parse(jsonReader);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                break;
            case '\f':
                contentModel = RepeaterParser.parse(jsonReader, lottieComposition);
                break;
            default:
                Log.w("LOTTIE", "Unknown shape type " + str);
                break;
        }
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return contentModel;
    }
}
