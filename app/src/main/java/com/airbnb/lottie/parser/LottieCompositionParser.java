package com.airbnb.lottie.parser;

import android.graphics.Rect;
import android.support.p002v4.util.LongSparseArray;
import android.support.p002v4.util.SparseArrayCompat;
import android.util.JsonReader;
import com.airbnb.lottie.C0839L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Utils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.C2516Ad;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class LottieCompositionParser {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static LottieComposition parse(JsonReader jsonReader) throws IOException {
        char c;
        SparseArrayCompat<FontCharacter> sparseArrayCompat;
        HashMap hashMap;
        float dpScale = Utils.dpScale();
        LongSparseArray<Layer> longSparseArray = new LongSparseArray<>();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        SparseArrayCompat<FontCharacter> sparseArrayCompat2 = new SparseArrayCompat<>();
        LottieComposition lottieComposition = new LottieComposition();
        jsonReader.beginObject();
        int i = 0;
        int i2 = 0;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            switch (nextName.hashCode()) {
                case -1408207997:
                    if (nextName.equals("assets")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1109732030:
                    if (nextName.equals("layers")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 104:
                    if (nextName.equals("h")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 118:
                    if (nextName.equals("v")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3276:
                    if (nextName.equals("fr")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3367:
                    if (nextName.equals("ip")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3553:
                    if (nextName.equals("op")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 94623709:
                    if (nextName.equals("chars")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 97615364:
                    if (nextName.equals("fonts")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    sparseArrayCompat = sparseArrayCompat2;
                    hashMap = hashMap4;
                    i = jsonReader.nextInt();
                    continue;
                    hashMap4 = hashMap;
                    sparseArrayCompat2 = sparseArrayCompat;
                case 1:
                    sparseArrayCompat = sparseArrayCompat2;
                    hashMap = hashMap4;
                    i2 = jsonReader.nextInt();
                    continue;
                    hashMap4 = hashMap;
                    sparseArrayCompat2 = sparseArrayCompat;
                case 2:
                    sparseArrayCompat = sparseArrayCompat2;
                    hashMap = hashMap4;
                    f = (float) jsonReader.nextDouble();
                    continue;
                    hashMap4 = hashMap;
                    sparseArrayCompat2 = sparseArrayCompat;
                case 3:
                    sparseArrayCompat = sparseArrayCompat2;
                    hashMap = hashMap4;
                    f2 = ((float) jsonReader.nextDouble()) - 0.01f;
                    continue;
                    hashMap4 = hashMap;
                    sparseArrayCompat2 = sparseArrayCompat;
                case 4:
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 5:
                    String[] split = jsonReader.nextString().split("\\.");
                    if (!Utils.isAtLeastVersion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 4, 4, 0)) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                        break;
                    }
                    break;
                case 6:
                    parseLayers(jsonReader, lottieComposition, arrayList, longSparseArray);
                    break;
                case 7:
                    parseAssets(jsonReader, lottieComposition, hashMap2, hashMap3);
                    break;
                case '\b':
                    parseFonts(jsonReader, hashMap4);
                    break;
                case '\t':
                    parseChars(jsonReader, lottieComposition, sparseArrayCompat2);
                    break;
                default:
                    sparseArrayCompat = sparseArrayCompat2;
                    hashMap = hashMap4;
                    jsonReader.skipValue();
                    continue;
                    hashMap4 = hashMap;
                    sparseArrayCompat2 = sparseArrayCompat;
            }
            sparseArrayCompat = sparseArrayCompat2;
            hashMap = hashMap4;
            hashMap4 = hashMap;
            sparseArrayCompat2 = sparseArrayCompat;
        }
        jsonReader.endObject();
        lottieComposition.init(new Rect(0, 0, (int) (i * dpScale), (int) (i2 * dpScale)), f, f2, f3, arrayList, longSparseArray, hashMap2, hashMap3, sparseArrayCompat2, hashMap4);
        return lottieComposition;
    }

    private static void parseLayers(JsonReader jsonReader, LottieComposition lottieComposition, List<Layer> list, LongSparseArray<Layer> longSparseArray) throws IOException {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer parse = LayerParser.parse(jsonReader, lottieComposition);
            if (parse.getLayerType() == Layer.LayerType.Image) {
                i++;
            }
            list.add(parse);
            longSparseArray.put(parse.getId(), parse);
            if (i > 4) {
                C0839L.warn("You have " + i + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        jsonReader.endArray();
    }

    private static void parseAssets(JsonReader jsonReader, LottieComposition lottieComposition, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            ArrayList arrayList = new ArrayList();
            LongSparseArray longSparseArray = new LongSparseArray();
            jsonReader.beginObject();
            String str = null;
            String str2 = null;
            String str3 = null;
            int i = 0;
            int i2 = 0;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                char c = 65535;
                int hashCode = nextName.hashCode();
                if (hashCode != -1109732030) {
                    if (hashCode != 104) {
                        if (hashCode != 112) {
                            if (hashCode != 117) {
                                if (hashCode != 119) {
                                    if (hashCode == 3355 && nextName.equals(DatabaseFieldConfigLoader.FIELD_NAME_ID)) {
                                        c = 0;
                                    }
                                } else if (nextName.equals("w")) {
                                    c = 2;
                                }
                            } else if (nextName.equals("u")) {
                                c = 5;
                            }
                        } else if (nextName.equals("p")) {
                            c = 4;
                        }
                    } else if (nextName.equals("h")) {
                        c = 3;
                    }
                } else if (nextName.equals("layers")) {
                    c = 1;
                }
                if (c == 0) {
                    str = jsonReader.nextString();
                } else if (c == 1) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Layer parse = LayerParser.parse(jsonReader, lottieComposition);
                        longSparseArray.put(parse.getId(), parse);
                        arrayList.add(parse);
                    }
                    jsonReader.endArray();
                } else if (c == 2) {
                    i = jsonReader.nextInt();
                } else if (c == 3) {
                    i2 = jsonReader.nextInt();
                } else if (c == 4) {
                    str2 = jsonReader.nextString();
                } else if (c == 5) {
                    str3 = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (str2 != null) {
                LottieImageAsset lottieImageAsset = new LottieImageAsset(i, i2, str, str2, str3);
                map2.put(lottieImageAsset.getId(), lottieImageAsset);
            } else {
                map.put(str, arrayList);
            }
        }
        jsonReader.endArray();
    }

    private static void parseFonts(JsonReader jsonReader, Map<String, Font> map) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            char c = 65535;
            if (nextName.hashCode() == 3322014 && nextName.equals(C2516Ad.TYPE_LIST)) {
                c = 0;
            }
            if (c == 0) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Font parse = FontParser.parse(jsonReader);
                    map.put(parse.getName(), parse);
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    private static void parseChars(JsonReader jsonReader, LottieComposition lottieComposition, SparseArrayCompat<FontCharacter> sparseArrayCompat) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            FontCharacter parse = FontCharacterParser.parse(jsonReader, lottieComposition);
            sparseArrayCompat.put(parse.hashCode(), parse);
        }
        jsonReader.endArray();
    }
}
