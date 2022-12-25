package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8ArrayBuffer;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8TypedArray;
import com.eclipsesource.p056v8.V8Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* renamed from: com.eclipsesource.v8.utils.V8ObjectUtils */
/* loaded from: classes2.dex */
public class V8ObjectUtils {
    private static final Object IGNORE = new Object();
    private static final TypeAdapter DEFAULT_TYPE_ADAPTER = new DefaultTypeAdapter();

    public static Object getValue(Object obj) {
        return getValue(obj, DEFAULT_TYPE_ADAPTER);
    }

    public static Object getValue(Object obj, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return obj instanceof V8Value ? getValue(obj, ((V8Value) obj).getV8Type(), v8Map, typeAdapter) : obj;
        } finally {
            v8Map.close();
        }
    }

    public static Map<String, ? super Object> toMap(V8Object v8Object) {
        return toMap(v8Object, DEFAULT_TYPE_ADAPTER);
    }

    public static Map<String, ? super Object> toMap(V8Object v8Object, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return toMap(v8Object, v8Map, typeAdapter);
        } finally {
            v8Map.close();
        }
    }

    public static List<? super Object> toList(V8Array v8Array) {
        return toList(v8Array, DEFAULT_TYPE_ADAPTER);
    }

    public static List<? super Object> toList(V8Array v8Array, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return toList(v8Array, v8Map, typeAdapter);
        } finally {
            v8Map.close();
        }
    }

    public static Object getTypedArray(V8Array v8Array, int i, Object obj) {
        int length = v8Array.length();
        if (i == 1) {
            int[] iArr = (int[]) obj;
            if (iArr == null || iArr.length < length) {
                iArr = new int[length];
            }
            v8Array.getIntegers(0, length, iArr);
            return iArr;
        } else if (i == 2) {
            double[] dArr = (double[]) obj;
            if (dArr == null || dArr.length < length) {
                dArr = new double[length];
            }
            v8Array.getDoubles(0, length, dArr);
            return dArr;
        } else if (i == 3) {
            boolean[] zArr = (boolean[]) obj;
            if (zArr == null || zArr.length < length) {
                zArr = new boolean[length];
            }
            v8Array.getBooleans(0, length, zArr);
            return zArr;
        } else if (i == 4) {
            String[] strArr = (String[]) obj;
            if (strArr == null || strArr.length < length) {
                strArr = new String[length];
            }
            v8Array.getStrings(0, length, strArr);
            return strArr;
        } else if (i == 9) {
            byte[] bArr = (byte[]) obj;
            if (bArr == null || bArr.length < length) {
                bArr = new byte[length];
            }
            v8Array.getBytes(0, length, bArr);
            return bArr;
        } else {
            throw new RuntimeException("Unsupported bulk load type: " + i);
        }
    }

    public static Object getTypedArray(V8Array v8Array, int i) {
        int length = v8Array.length();
        if (i == 1) {
            return v8Array.getIntegers(0, length);
        }
        if (i == 2) {
            return v8Array.getDoubles(0, length);
        }
        if (i == 3) {
            return v8Array.getBooleans(0, length);
        }
        if (i == 4) {
            return v8Array.getStrings(0, length);
        }
        throw new RuntimeException("Unsupported bulk load type: " + i);
    }

    public static V8Object toV8Object(C1257V8 c1257v8, Map<String, ? extends Object> map) {
        Hashtable hashtable = new Hashtable();
        try {
            return toV8Object(c1257v8, map, hashtable).mo5914twin();
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    public static V8Array toV8Array(C1257V8 c1257v8, List<? extends Object> list) {
        Hashtable hashtable = new Hashtable();
        try {
            return toV8Array(c1257v8, list, hashtable).mo5914twin();
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    public static Object getV8Result(C1257V8 c1257v8, Object obj) {
        if (obj == null) {
            return null;
        }
        Hashtable hashtable = new Hashtable();
        try {
            Object v8Result = getV8Result(c1257v8, obj, hashtable);
            if (v8Result instanceof V8Value) {
                return ((V8Value) v8Result).mo5914twin();
            }
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
            return v8Result;
        } finally {
            for (V8Value v8Value2 : hashtable.values()) {
                v8Value2.close();
            }
        }
    }

    public static void pushValue(C1257V8 c1257v8, V8Array v8Array, Object obj) {
        Hashtable hashtable = new Hashtable();
        try {
            pushValue(c1257v8, v8Array, obj, hashtable);
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    public static Object getValue(V8Array v8Array, int i) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Array.get(i);
            try {
                Object value = getValue(obj, v8Array.getType(i), v8Map, DEFAULT_TYPE_ADAPTER);
                if (value == obj && (value instanceof V8Value)) {
                    V8Value mo5914twin = ((V8Value) value).mo5914twin();
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return mo5914twin;
                }
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return value;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    public static Object getValue(V8Array v8Array, int i, TypeAdapter typeAdapter) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Array.get(i);
            try {
                Object value = getValue(obj, v8Array.getType(i), v8Map, typeAdapter);
                if (value == obj && (value instanceof V8Value)) {
                    V8Value mo5914twin = ((V8Value) value).mo5914twin();
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return mo5914twin;
                }
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return value;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    public static Object getValue(V8Object v8Object, String str) {
        return getValue(v8Object, str, DEFAULT_TYPE_ADAPTER);
    }

    public static Object getValue(V8Object v8Object, String str, TypeAdapter typeAdapter) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Object.get(str);
            try {
                Object value = getValue(obj, v8Object.getType(str), v8Map, typeAdapter);
                if (value == obj && (value instanceof V8Value)) {
                    V8Value mo5914twin = ((V8Value) value).mo5914twin();
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return mo5914twin;
                }
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return value;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    private static Map<String, ? super Object> toMap(V8Object v8Object, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        String[] keys;
        if (v8Object == null) {
            return Collections.emptyMap();
        }
        if (v8Map.containsKey(v8Object)) {
            return (Map) v8Map.get(v8Object);
        }
        V8PropertyMap v8PropertyMap = new V8PropertyMap();
        v8Map.put2((V8Value) v8Object, (V8Object) v8PropertyMap);
        for (String str : v8Object.getKeys()) {
            Object obj = null;
            try {
                obj = v8Object.get(str);
                Object value = getValue(obj, v8Object.getType(str), v8Map, typeAdapter);
                if (value != IGNORE) {
                    v8PropertyMap.put((V8PropertyMap) str, (String) value);
                }
            } finally {
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
            }
        }
        return v8PropertyMap;
    }

    private static List<? super Object> toList(V8Array v8Array, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        if (v8Array == null) {
            return Collections.emptyList();
        }
        if (v8Map.containsKey(v8Array)) {
            return (List) v8Map.get(v8Array);
        }
        ArrayList arrayList = new ArrayList();
        v8Map.put2((V8Value) v8Array, (V8Array) arrayList);
        for (int i = 0; i < v8Array.length(); i++) {
            Object obj = null;
            try {
                obj = v8Array.get(i);
                Object value = getValue(obj, v8Array.getType(i), v8Map, typeAdapter);
                if (value != IGNORE) {
                    arrayList.add(value);
                }
            } finally {
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
            }
        }
        return arrayList;
    }

    private static V8TypedArray toV8TypedArray(C1257V8 c1257v8, TypedArray typedArray, Map<Object, V8Value> map) {
        if (map.containsKey(typedArray)) {
            return (V8TypedArray) map.get(typedArray);
        }
        V8TypedArray v8TypedArray = typedArray.getV8TypedArray();
        map.put(typedArray, v8TypedArray);
        return v8TypedArray;
    }

    private static V8ArrayBuffer toV8ArrayBuffer(C1257V8 c1257v8, ArrayBuffer arrayBuffer, Map<Object, V8Value> map) {
        if (map.containsKey(arrayBuffer)) {
            return (V8ArrayBuffer) map.get(arrayBuffer);
        }
        V8ArrayBuffer v8ArrayBuffer = arrayBuffer.getV8ArrayBuffer();
        map.put(arrayBuffer, v8ArrayBuffer);
        return v8ArrayBuffer;
    }

    private static V8Object toV8Object(C1257V8 c1257v8, Map<String, ? extends Object> map, Map<Object, V8Value> map2) {
        if (map2.containsKey(map)) {
            return (V8Object) map2.get(map);
        }
        V8Object v8Object = new V8Object(c1257v8);
        map2.put(map, v8Object);
        try {
            for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                setValue(c1257v8, v8Object, entry.getKey(), entry.getValue(), map2);
            }
            return v8Object;
        } catch (IllegalStateException e) {
            v8Object.close();
            throw e;
        }
    }

    private static V8Array toV8Array(C1257V8 c1257v8, List<? extends Object> list, Map<Object, V8Value> map) {
        if (map.containsKey(new ListWrapper(list))) {
            return (V8Array) map.get(new ListWrapper(list));
        }
        V8Array v8Array = new V8Array(c1257v8);
        map.put(new ListWrapper(list), v8Array);
        for (int i = 0; i < list.size(); i++) {
            try {
                pushValue(c1257v8, v8Array, list.get(i), map);
            } catch (IllegalStateException e) {
                v8Array.close();
                throw e;
            }
        }
        return v8Array;
    }

    private static Object getV8Result(C1257V8 c1257v8, Object obj, Map<Object, V8Value> map) {
        if (map.containsKey(obj)) {
            return map.get(obj);
        }
        if (obj instanceof Map) {
            return toV8Object(c1257v8, (Map) obj, map);
        }
        if (obj instanceof List) {
            return toV8Array(c1257v8, (List) obj, map);
        }
        if (obj instanceof TypedArray) {
            return toV8TypedArray(c1257v8, (TypedArray) obj, map);
        }
        return obj instanceof ArrayBuffer ? toV8ArrayBuffer(c1257v8, (ArrayBuffer) obj, map) : obj;
    }

    private static void pushValue(C1257V8 c1257v8, V8Array v8Array, Object obj, Map<Object, V8Value> map) {
        if (obj == null) {
            v8Array.pushUndefined();
        } else if (obj instanceof Integer) {
            v8Array.push(obj);
        } else if (obj instanceof Long) {
            v8Array.push(new Double(((Long) obj).longValue()));
        } else if (obj instanceof Double) {
            v8Array.push(obj);
        } else if (obj instanceof Float) {
            v8Array.push(obj);
        } else if (obj instanceof String) {
            v8Array.push((String) obj);
        } else if (obj instanceof Boolean) {
            v8Array.push(obj);
        } else if (obj instanceof TypedArray) {
            v8Array.push((V8Value) toV8TypedArray(c1257v8, (TypedArray) obj, map));
        } else if (obj instanceof ArrayBuffer) {
            v8Array.push((V8Value) toV8ArrayBuffer(c1257v8, (ArrayBuffer) obj, map));
        } else if (obj instanceof V8Value) {
            v8Array.push((V8Value) obj);
        } else if (obj instanceof Map) {
            v8Array.push((V8Value) toV8Object(c1257v8, (Map) obj, map));
        } else if (obj instanceof List) {
            v8Array.push((V8Value) toV8Array(c1257v8, (List) obj, map));
        } else {
            throw new IllegalStateException("Unsupported Object of type: " + obj.getClass());
        }
    }

    private static void setValue(C1257V8 c1257v8, V8Object v8Object, String str, Object obj, Map<Object, V8Value> map) {
        if (obj == null) {
            v8Object.addUndefined(str);
        } else if (obj instanceof Integer) {
            v8Object.add(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            v8Object.add(str, ((Long) obj).longValue());
        } else if (obj instanceof Double) {
            v8Object.add(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Float) {
            v8Object.add(str, ((Float) obj).floatValue());
        } else if (obj instanceof String) {
            v8Object.add(str, (String) obj);
        } else if (obj instanceof Boolean) {
            v8Object.add(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof TypedArray) {
            v8Object.add(str, toV8TypedArray(c1257v8, (TypedArray) obj, map));
        } else if (obj instanceof ArrayBuffer) {
            v8Object.add(str, toV8ArrayBuffer(c1257v8, (ArrayBuffer) obj, map));
        } else if (obj instanceof V8Value) {
            v8Object.add(str, (V8Value) obj);
        } else if (obj instanceof Map) {
            v8Object.add(str, toV8Object(c1257v8, (Map) obj, map));
        } else if (obj instanceof List) {
            v8Object.add(str, toV8Array(c1257v8, (List) obj, map));
        } else {
            throw new IllegalStateException("Unsupported Object of type: " + obj.getClass());
        }
    }

    private static Object getValue(Object obj, int i, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        Object adapt = typeAdapter.adapt(i, obj);
        if (TypeAdapter.DEFAULT != adapt) {
            return adapt;
        }
        if (i == 10) {
            return new ArrayBuffer((V8ArrayBuffer) obj);
        }
        if (i != 99) {
            switch (i) {
                case 0:
                    return null;
                case 1:
                case 2:
                case 3:
                case 4:
                    return obj;
                case 5:
                    return toList((V8Array) obj, v8Map, typeAdapter);
                case 6:
                    return toMap((V8Object) obj, v8Map, typeAdapter);
                case 7:
                    return IGNORE;
                case 8:
                    return new TypedArray((V8TypedArray) obj);
                default:
                    throw new IllegalStateException("Cannot convert type " + V8Value.getStringRepresentation(i));
            }
        }
        return C1257V8.getUndefined();
    }

    private V8ObjectUtils() {
    }

    /* renamed from: com.eclipsesource.v8.utils.V8ObjectUtils$DefaultTypeAdapter */
    /* loaded from: classes2.dex */
    static class DefaultTypeAdapter implements TypeAdapter {
        DefaultTypeAdapter() {
        }

        @Override // com.eclipsesource.p056v8.utils.TypeAdapter
        public Object adapt(int i, Object obj) {
            return TypeAdapter.DEFAULT;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.eclipsesource.v8.utils.V8ObjectUtils$ListWrapper */
    /* loaded from: classes2.dex */
    public static class ListWrapper {
        private List<? extends Object> list;

        public ListWrapper(List<? extends Object> list) {
            this.list = list;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ListWrapper) && ((ListWrapper) obj).list == this.list;
        }

        public int hashCode() {
            return System.identityHashCode(this.list);
        }
    }
}
