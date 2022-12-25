package com.opensource.svgaplayer.entities;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.opensource.svgaplayer.proto.ShapeEntity;
import com.opensource.svgaplayer.proto.Transform;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsJVM;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SVGAVideoShapeEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAVideoShapeEntity {
    private Map<String, ? extends Object> args;
    private Path shapePath;
    private Styles styles;
    private Matrix transform;
    private Type type = Type.shape;

    /* compiled from: SVGAVideoShapeEntity.kt */
    /* loaded from: classes3.dex */
    public enum Type {
        shape,
        rect,
        ellipse,
        keep
    }

    /* loaded from: classes3.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[ShapeEntity.ShapeType.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1 = new int[ShapeEntity.ShapeStyle.LineCap.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$2 = new int[ShapeEntity.ShapeStyle.LineJoin.values().length];

        static {
            $EnumSwitchMapping$0[ShapeEntity.ShapeType.SHAPE.ordinal()] = 1;
            $EnumSwitchMapping$0[ShapeEntity.ShapeType.RECT.ordinal()] = 2;
            $EnumSwitchMapping$0[ShapeEntity.ShapeType.ELLIPSE.ordinal()] = 3;
            $EnumSwitchMapping$0[ShapeEntity.ShapeType.KEEP.ordinal()] = 4;
            $EnumSwitchMapping$1[ShapeEntity.ShapeStyle.LineCap.LineCap_BUTT.ordinal()] = 1;
            $EnumSwitchMapping$1[ShapeEntity.ShapeStyle.LineCap.LineCap_ROUND.ordinal()] = 2;
            $EnumSwitchMapping$1[ShapeEntity.ShapeStyle.LineCap.LineCap_SQUARE.ordinal()] = 3;
            $EnumSwitchMapping$2[ShapeEntity.ShapeStyle.LineJoin.LineJoin_BEVEL.ordinal()] = 1;
            $EnumSwitchMapping$2[ShapeEntity.ShapeStyle.LineJoin.LineJoin_MITER.ordinal()] = 2;
            $EnumSwitchMapping$2[ShapeEntity.ShapeStyle.LineJoin.LineJoin_ROUND.ordinal()] = 3;
        }
    }

    /* compiled from: SVGAVideoShapeEntity.kt */
    /* loaded from: classes3.dex */
    public static final class Styles {
        private int fill;
        private int miterLimit;
        private int stroke;
        private float strokeWidth;
        private String lineCap = "butt";
        private String lineJoin = "miter";
        private float[] lineDash = new float[0];

        public final int getFill() {
            return this.fill;
        }

        public final void setFill$library_release(int i) {
            this.fill = i;
        }

        public final int getStroke() {
            return this.stroke;
        }

        public final void setStroke$library_release(int i) {
            this.stroke = i;
        }

        public final float getStrokeWidth() {
            return this.strokeWidth;
        }

        public final void setStrokeWidth$library_release(float f) {
            this.strokeWidth = f;
        }

        public final String getLineCap() {
            return this.lineCap;
        }

        public final void setLineCap$library_release(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.lineCap = str;
        }

        public final String getLineJoin() {
            return this.lineJoin;
        }

        public final void setLineJoin$library_release(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.lineJoin = str;
        }

        public final int getMiterLimit() {
            return this.miterLimit;
        }

        public final void setMiterLimit$library_release(int i) {
            this.miterLimit = i;
        }

        public final float[] getLineDash() {
            return this.lineDash;
        }

        public final void setLineDash$library_release(float[] fArr) {
            Intrinsics.checkParameterIsNotNull(fArr, "<set-?>");
            this.lineDash = fArr;
        }
    }

    public final Styles getStyles() {
        return this.styles;
    }

    public final Matrix getTransform() {
        return this.transform;
    }

    public SVGAVideoShapeEntity(JSONObject obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        parseType(obj);
        parseArgs(obj);
        parseStyles(obj);
        parseTransform(obj);
    }

    public SVGAVideoShapeEntity(ShapeEntity obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        parseType(obj);
        parseArgs(obj);
        parseStyles(obj);
        parseTransform(obj);
    }

    public final boolean isKeep() {
        return this.type == Type.keep;
    }

    public final Path getShapePath() {
        return this.shapePath;
    }

    private final void parseType(JSONObject jSONObject) {
        boolean equals;
        boolean equals2;
        boolean equals3;
        boolean equals4;
        String optString = jSONObject.optString("type");
        if (optString != null) {
            equals = StringsJVM.equals(optString, "shape", true);
            if (equals) {
                this.type = Type.shape;
                return;
            }
            equals2 = StringsJVM.equals(optString, "rect", true);
            if (equals2) {
                this.type = Type.rect;
                return;
            }
            equals3 = StringsJVM.equals(optString, "ellipse", true);
            if (equals3) {
                this.type = Type.ellipse;
                return;
            }
            equals4 = StringsJVM.equals(optString, "keep", true);
            if (!equals4) {
                return;
            }
            this.type = Type.keep;
        }
    }

    private final void parseType(ShapeEntity shapeEntity) {
        Type type;
        ShapeEntity.ShapeType shapeType = shapeEntity.type;
        if (shapeType != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[shapeType.ordinal()];
            if (i == 1) {
                type = Type.shape;
            } else if (i == 2) {
                type = Type.rect;
            } else if (i == 3) {
                type = Type.ellipse;
            } else if (i != 4) {
                throw new NoWhenBranchMatchedException();
            } else {
                type = Type.keep;
            }
            this.type = type;
        }
    }

    private final void parseArgs(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        JSONObject optJSONObject = jSONObject.optJSONObject("args");
        if (optJSONObject != null) {
            Iterator<String> keys = optJSONObject.keys();
            Intrinsics.checkExpressionValueIsNotNull(keys, "values.keys()");
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = optJSONObject.get(next);
                if (obj != null) {
                    hashMap.put(next, obj);
                }
            }
            this.args = hashMap;
        }
    }

    private final void parseArgs(ShapeEntity shapeEntity) {
        String str;
        HashMap hashMap = new HashMap();
        ShapeEntity.ShapeArgs shapeArgs = shapeEntity.shape;
        if (shapeArgs != null && (str = shapeArgs.f1775d) != null) {
            hashMap.put("d", str);
        }
        ShapeEntity.EllipseArgs ellipseArgs = shapeEntity.ellipse;
        if (ellipseArgs != null) {
            Float f = ellipseArgs.f1767x;
            if (f == null) {
                f = Float.valueOf(0.0f);
            }
            hashMap.put("x", f);
            Float f2 = ellipseArgs.f1768y;
            if (f2 == null) {
                f2 = Float.valueOf(0.0f);
            }
            hashMap.put("y", f2);
            Float f3 = ellipseArgs.radiusX;
            if (f3 == null) {
                f3 = Float.valueOf(0.0f);
            }
            hashMap.put("radiusX", f3);
            Float f4 = ellipseArgs.radiusY;
            if (f4 == null) {
                f4 = Float.valueOf(0.0f);
            }
            hashMap.put("radiusY", f4);
        }
        ShapeEntity.RectArgs rectArgs = shapeEntity.rect;
        if (rectArgs != null) {
            Float f5 = rectArgs.f1771x;
            if (f5 == null) {
                f5 = Float.valueOf(0.0f);
            }
            hashMap.put("x", f5);
            Float f6 = rectArgs.f1772y;
            if (f6 == null) {
                f6 = Float.valueOf(0.0f);
            }
            hashMap.put("y", f6);
            Float f7 = rectArgs.width;
            if (f7 == null) {
                f7 = Float.valueOf(0.0f);
            }
            hashMap.put("width", f7);
            Float f8 = rectArgs.height;
            if (f8 == null) {
                f8 = Float.valueOf(0.0f);
            }
            hashMap.put("height", f8);
            Float f9 = rectArgs.cornerRadius;
            if (f9 == null) {
                f9 = Float.valueOf(0.0f);
            }
            hashMap.put("cornerRadius", f9);
        }
        this.args = hashMap;
    }

    private final void parseStyles(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("styles");
        if (optJSONObject != null) {
            Styles styles = new Styles();
            JSONArray optJSONArray = optJSONObject.optJSONArray("fill");
            if (optJSONArray != null && optJSONArray.length() == 4) {
                double d = 255;
                styles.setFill$library_release(Color.argb((int) (optJSONArray.optDouble(3) * d), (int) (optJSONArray.optDouble(0) * d), (int) (optJSONArray.optDouble(1) * d), (int) (optJSONArray.optDouble(2) * d)));
            }
            JSONArray optJSONArray2 = optJSONObject.optJSONArray("stroke");
            if (optJSONArray2 != null && optJSONArray2.length() == 4) {
                double d2 = 255;
                styles.setStroke$library_release(Color.argb((int) (optJSONArray2.optDouble(3) * d2), (int) (optJSONArray2.optDouble(0) * d2), (int) (optJSONArray2.optDouble(1) * d2), (int) (optJSONArray2.optDouble(2) * d2)));
            }
            styles.setStrokeWidth$library_release((float) optJSONObject.optDouble("strokeWidth", 0.0d));
            String optString = optJSONObject.optString("lineCap", "butt");
            Intrinsics.checkExpressionValueIsNotNull(optString, "it.optString(\"lineCap\", \"butt\")");
            styles.setLineCap$library_release(optString);
            String optString2 = optJSONObject.optString("lineJoin", "miter");
            Intrinsics.checkExpressionValueIsNotNull(optString2, "it.optString(\"lineJoin\", \"miter\")");
            styles.setLineJoin$library_release(optString2);
            styles.setMiterLimit$library_release(optJSONObject.optInt("miterLimit", 0));
            JSONArray optJSONArray3 = optJSONObject.optJSONArray("lineDash");
            if (optJSONArray3 != null) {
                styles.setLineDash$library_release(new float[optJSONArray3.length()]);
                int length = optJSONArray3.length();
                for (int i = 0; i < length; i++) {
                    styles.getLineDash()[i] = (float) optJSONArray3.optDouble(i, 0.0d);
                }
            }
            this.styles = styles;
        }
    }

    private final void parseStyles(ShapeEntity shapeEntity) {
        ShapeEntity.ShapeStyle shapeStyle = shapeEntity.styles;
        if (shapeStyle != null) {
            Styles styles = new Styles();
            ShapeEntity.ShapeStyle.RGBAColor rGBAColor = shapeStyle.fill;
            float f = 0.0f;
            if (rGBAColor != null) {
                Float f2 = rGBAColor.f1777a;
                float f3 = 255;
                int floatValue = (int) ((f2 != null ? f2.floatValue() : 0.0f) * f3);
                Float f4 = rGBAColor.f1780r;
                int floatValue2 = (int) ((f4 != null ? f4.floatValue() : 0.0f) * f3);
                Float f5 = rGBAColor.f1779g;
                int floatValue3 = (int) ((f5 != null ? f5.floatValue() : 0.0f) * f3);
                Float f6 = rGBAColor.f1778b;
                styles.setFill$library_release(Color.argb(floatValue, floatValue2, floatValue3, (int) ((f6 != null ? f6.floatValue() : 0.0f) * f3)));
            }
            ShapeEntity.ShapeStyle.RGBAColor rGBAColor2 = shapeStyle.stroke;
            if (rGBAColor2 != null) {
                Float f7 = rGBAColor2.f1777a;
                float f8 = 255;
                int floatValue4 = (int) ((f7 != null ? f7.floatValue() : 0.0f) * f8);
                Float f9 = rGBAColor2.f1780r;
                int floatValue5 = (int) ((f9 != null ? f9.floatValue() : 0.0f) * f8);
                Float f10 = rGBAColor2.f1779g;
                int floatValue6 = (int) ((f10 != null ? f10.floatValue() : 0.0f) * f8);
                Float f11 = rGBAColor2.f1778b;
                styles.setStroke$library_release(Color.argb(floatValue4, floatValue5, floatValue6, (int) ((f11 != null ? f11.floatValue() : 0.0f) * f8)));
            }
            Float f12 = shapeStyle.strokeWidth;
            styles.setStrokeWidth$library_release(f12 != null ? f12.floatValue() : 0.0f);
            ShapeEntity.ShapeStyle.LineCap lineCap = shapeStyle.lineCap;
            if (lineCap != null) {
                int i = WhenMappings.$EnumSwitchMapping$1[lineCap.ordinal()];
                if (i == 1) {
                    styles.setLineCap$library_release("butt");
                } else if (i == 2) {
                    styles.setLineCap$library_release("round");
                } else if (i == 3) {
                    styles.setLineCap$library_release("square");
                }
            }
            ShapeEntity.ShapeStyle.LineJoin lineJoin = shapeStyle.lineJoin;
            if (lineJoin != null) {
                int i2 = WhenMappings.$EnumSwitchMapping$2[lineJoin.ordinal()];
                if (i2 == 1) {
                    styles.setLineJoin$library_release("bevel");
                } else if (i2 == 2) {
                    styles.setLineJoin$library_release("miter");
                } else if (i2 == 3) {
                    styles.setLineJoin$library_release("round");
                }
            }
            Float f13 = shapeStyle.miterLimit;
            if (f13 != null) {
                f = f13.floatValue();
            }
            styles.setMiterLimit$library_release((int) f);
            styles.setLineDash$library_release(new float[3]);
            Float f14 = shapeStyle.lineDashI;
            if (f14 != null) {
                styles.getLineDash()[0] = f14.floatValue();
            }
            Float f15 = shapeStyle.lineDashII;
            if (f15 != null) {
                styles.getLineDash()[1] = f15.floatValue();
            }
            Float f16 = shapeStyle.lineDashIII;
            if (f16 != null) {
                styles.getLineDash()[2] = f16.floatValue();
            }
            this.styles = styles;
        }
    }

    private final void parseTransform(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("transform");
        if (optJSONObject != null) {
            Matrix matrix = new Matrix();
            double optDouble = optJSONObject.optDouble("a", 1.0d);
            double optDouble2 = optJSONObject.optDouble(EGL10Helper.f2537a, 0.0d);
            float f = (float) 0.0d;
            matrix.setValues(new float[]{(float) optDouble, (float) optJSONObject.optDouble("c", 0.0d), (float) optJSONObject.optDouble("tx", 0.0d), (float) optDouble2, (float) optJSONObject.optDouble("d", 1.0d), (float) optJSONObject.optDouble("ty", 0.0d), f, f, (float) 1.0d});
            this.transform = matrix;
        }
    }

    private final void parseTransform(ShapeEntity shapeEntity) {
        Transform transform = shapeEntity.transform;
        if (transform != null) {
            Matrix matrix = new Matrix();
            float[] fArr = new float[9];
            Float f = transform.f1785a;
            float floatValue = f != null ? f.floatValue() : 1.0f;
            Float f2 = transform.f1786b;
            float floatValue2 = f2 != null ? f2.floatValue() : 0.0f;
            Float f3 = transform.f1787c;
            float floatValue3 = f3 != null ? f3.floatValue() : 0.0f;
            Float f4 = transform.f1788d;
            float floatValue4 = f4 != null ? f4.floatValue() : 1.0f;
            Float f5 = transform.f1789tx;
            float floatValue5 = f5 != null ? f5.floatValue() : 0.0f;
            Float f6 = transform.f1790ty;
            float floatValue6 = f6 != null ? f6.floatValue() : 0.0f;
            fArr[0] = floatValue;
            fArr[1] = floatValue3;
            fArr[2] = floatValue5;
            fArr[3] = floatValue2;
            fArr[4] = floatValue4;
            fArr[5] = floatValue6;
            fArr[6] = 0.0f;
            fArr[7] = 0.0f;
            fArr[8] = 1.0f;
            matrix.setValues(fArr);
            this.transform = matrix;
        }
    }

    public final void buildPath() {
        if (this.shapePath != null) {
            return;
        }
        SVGAVideoShapeEntityKt.getSharedPath().reset();
        Type type = this.type;
        Number number = null;
        if (type == Type.shape) {
            Map<String, ? extends Object> map = this.args;
            Object obj = map != null ? map.get("d") : null;
            if (!(obj instanceof String)) {
                obj = null;
            }
            String str = (String) obj;
            if (str != null) {
                new SVGAPathEntity(str).buildPath(SVGAVideoShapeEntityKt.getSharedPath());
            }
        } else if (type == Type.ellipse) {
            Map<String, ? extends Object> map2 = this.args;
            Object obj2 = map2 != null ? map2.get("x") : null;
            if (!(obj2 instanceof Number)) {
                obj2 = null;
            }
            Number number2 = (Number) obj2;
            if (number2 == null) {
                return;
            }
            Map<String, ? extends Object> map3 = this.args;
            Object obj3 = map3 != null ? map3.get("y") : null;
            if (!(obj3 instanceof Number)) {
                obj3 = null;
            }
            Number number3 = (Number) obj3;
            if (number3 == null) {
                return;
            }
            Map<String, ? extends Object> map4 = this.args;
            Object obj4 = map4 != null ? map4.get("radiusX") : null;
            if (!(obj4 instanceof Number)) {
                obj4 = null;
            }
            Number number4 = (Number) obj4;
            if (number4 == null) {
                return;
            }
            Map<String, ? extends Object> map5 = this.args;
            Object obj5 = map5 != null ? map5.get("radiusY") : null;
            if (obj5 instanceof Number) {
                number = obj5;
            }
            Number number5 = (Number) number;
            if (number5 == null) {
                return;
            }
            float floatValue = number2.floatValue();
            float floatValue2 = number3.floatValue();
            float floatValue3 = number4.floatValue();
            float floatValue4 = number5.floatValue();
            SVGAVideoShapeEntityKt.getSharedPath().addOval(new RectF(floatValue - floatValue3, floatValue2 - floatValue4, floatValue + floatValue3, floatValue2 + floatValue4), Path.Direction.CW);
        } else if (type == Type.rect) {
            Map<String, ? extends Object> map6 = this.args;
            Object obj6 = map6 != null ? map6.get("x") : null;
            if (!(obj6 instanceof Number)) {
                obj6 = null;
            }
            Number number6 = (Number) obj6;
            if (number6 == null) {
                return;
            }
            Map<String, ? extends Object> map7 = this.args;
            Object obj7 = map7 != null ? map7.get("y") : null;
            if (!(obj7 instanceof Number)) {
                obj7 = null;
            }
            Number number7 = (Number) obj7;
            if (number7 == null) {
                return;
            }
            Map<String, ? extends Object> map8 = this.args;
            Object obj8 = map8 != null ? map8.get("width") : null;
            if (!(obj8 instanceof Number)) {
                obj8 = null;
            }
            Number number8 = (Number) obj8;
            if (number8 == null) {
                return;
            }
            Map<String, ? extends Object> map9 = this.args;
            Object obj9 = map9 != null ? map9.get("height") : null;
            if (!(obj9 instanceof Number)) {
                obj9 = null;
            }
            Number number9 = (Number) obj9;
            if (number9 == null) {
                return;
            }
            Map<String, ? extends Object> map10 = this.args;
            Object obj10 = map10 != null ? map10.get("cornerRadius") : null;
            if (obj10 instanceof Number) {
                number = obj10;
            }
            Number number10 = number;
            if (number10 == null) {
                return;
            }
            float floatValue5 = number6.floatValue();
            float floatValue6 = number7.floatValue();
            float floatValue7 = number8.floatValue();
            float floatValue8 = number9.floatValue();
            float floatValue9 = number10.floatValue();
            SVGAVideoShapeEntityKt.getSharedPath().addRoundRect(new RectF(floatValue5, floatValue6, floatValue7 + floatValue5, floatValue8 + floatValue6), floatValue9, floatValue9, Path.Direction.CW);
        }
        this.shapePath = new Path();
        Path path = this.shapePath;
        if (path == null) {
            return;
        }
        path.set(SVGAVideoShapeEntityKt.getSharedPath());
    }
}
