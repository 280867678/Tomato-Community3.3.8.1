package com.opensource.svgaplayer.entities;

import android.graphics.Matrix;
import com.opensource.svgaplayer.proto.FrameEntity;
import com.opensource.svgaplayer.proto.Layout;
import com.opensource.svgaplayer.proto.ShapeEntity;
import com.opensource.svgaplayer.proto.Transform;
import com.opensource.svgaplayer.utils.SVGARect;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.Iterables;
import kotlin.collections._Collections;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SVGAVideoSpriteFrameEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAVideoSpriteFrameEntity {
    private double alpha;
    private SVGARect layout;
    private SVGAPathEntity maskPath;
    private List<SVGAVideoShapeEntity> shapes;
    private Matrix transform = new Matrix();

    public final double getAlpha() {
        return this.alpha;
    }

    public final SVGARect getLayout() {
        return this.layout;
    }

    public final Matrix getTransform() {
        return this.transform;
    }

    public final SVGAPathEntity getMaskPath() {
        return this.maskPath;
    }

    public final List<SVGAVideoShapeEntity> getShapes() {
        return this.shapes;
    }

    public final void setShapes(List<SVGAVideoShapeEntity> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.shapes = list;
    }

    public SVGAVideoSpriteFrameEntity(JSONObject obj) {
        List<SVGAVideoShapeEntity> emptyList;
        int i;
        boolean z;
        List<SVGAVideoShapeEntity> list;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.layout = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        emptyList = CollectionsKt__CollectionsKt.emptyList();
        this.shapes = emptyList;
        this.alpha = obj.optDouble("alpha", 0.0d);
        JSONObject optJSONObject = obj.optJSONObject("layout");
        if (optJSONObject != null) {
            this.layout = new SVGARect(optJSONObject.optDouble("x", 0.0d), optJSONObject.optDouble("y", 0.0d), optJSONObject.optDouble("width", 0.0d), optJSONObject.optDouble("height", 0.0d));
        }
        JSONObject optJSONObject2 = obj.optJSONObject("transform");
        if (optJSONObject2 != null) {
            i = 0;
            z = true;
            float f = (float) 0.0d;
            this.transform.setValues(new float[]{(float) optJSONObject2.optDouble("a", 1.0d), (float) optJSONObject2.optDouble("c", 0.0d), (float) optJSONObject2.optDouble("tx", 0.0d), (float) optJSONObject2.optDouble(EGL10Helper.f2537a, 0.0d), (float) optJSONObject2.optDouble("d", 1.0d), (float) optJSONObject2.optDouble("ty", 0.0d), f, f, (float) 1.0d});
        } else {
            i = 0;
            z = true;
        }
        String optString = obj.optString("clipPath");
        if (optString != null) {
            if (optString.length() <= 0 ? false : z) {
                this.maskPath = new SVGAPathEntity(optString);
            }
        }
        JSONArray optJSONArray = obj.optJSONArray("shapes");
        if (optJSONArray != null) {
            ArrayList arrayList = new ArrayList();
            int length = optJSONArray.length();
            while (i < length) {
                JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                if (optJSONObject3 != null) {
                    arrayList.add(new SVGAVideoShapeEntity(optJSONObject3));
                }
                i++;
            }
            list = _Collections.toList(arrayList);
            this.shapes = list;
        }
    }

    public SVGAVideoSpriteFrameEntity(FrameEntity obj) {
        List<SVGAVideoShapeEntity> emptyList;
        Float f;
        int collectionSizeOrDefault;
        Float f2;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.layout = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        emptyList = CollectionsKt__CollectionsKt.emptyList();
        this.shapes = emptyList;
        this.alpha = obj.alpha != null ? f.floatValue() : 0.0f;
        Layout layout = obj.layout;
        if (layout != null) {
            Float f3 = layout.f1763x;
            double floatValue = f3 != null ? f3.floatValue() : 0.0f;
            Float f4 = layout.f1764y;
            double floatValue2 = f4 != null ? f4.floatValue() : 0.0f;
            Float f5 = layout.width;
            this.layout = new SVGARect(floatValue, floatValue2, f5 != null ? f5.floatValue() : 0.0f, layout.height != null ? f2.floatValue() : 0.0f);
        }
        Transform transform = obj.transform;
        boolean z = true;
        if (transform != null) {
            float[] fArr = new float[9];
            Float f6 = transform.f1785a;
            float floatValue3 = f6 != null ? f6.floatValue() : 1.0f;
            Float f7 = transform.f1786b;
            float floatValue4 = f7 != null ? f7.floatValue() : 0.0f;
            Float f8 = transform.f1787c;
            float floatValue5 = f8 != null ? f8.floatValue() : 0.0f;
            Float f9 = transform.f1788d;
            float floatValue6 = f9 != null ? f9.floatValue() : 1.0f;
            Float f10 = transform.f1789tx;
            float floatValue7 = f10 != null ? f10.floatValue() : 0.0f;
            Float f11 = transform.f1790ty;
            float floatValue8 = f11 != null ? f11.floatValue() : 0.0f;
            fArr[0] = floatValue3;
            fArr[1] = floatValue5;
            fArr[2] = floatValue7;
            fArr[3] = floatValue4;
            fArr[4] = floatValue6;
            fArr[5] = floatValue8;
            fArr[6] = 0.0f;
            fArr[7] = 0.0f;
            fArr[8] = 1.0f;
            this.transform.setValues(fArr);
        }
        String str = obj.clipPath;
        if (str != null) {
            str = !(str.length() <= 0 ? false : z) ? null : str;
            if (str != null) {
                this.maskPath = new SVGAPathEntity(str);
            }
        }
        List<ShapeEntity> list = obj.shapes;
        Intrinsics.checkExpressionValueIsNotNull(list, "obj.shapes");
        collectionSizeOrDefault = Iterables.collectionSizeOrDefault(list, 10);
        ArrayList arrayList = new ArrayList(collectionSizeOrDefault);
        for (ShapeEntity it2 : list) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            arrayList.add(new SVGAVideoShapeEntity(it2));
        }
        this.shapes = arrayList;
    }
}
