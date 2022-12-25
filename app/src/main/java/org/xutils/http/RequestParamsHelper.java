package org.xutils.http;

import android.os.Parcelable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class RequestParamsHelper {
    private static final ClassLoader BOOT_CL = String.class.getClassLoader();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface ParseKVListener {
        void onParseKV(String str, Object obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void parseKV(Object obj, Class<?> cls, ParseKVListener parseKVListener) {
        ClassLoader classLoader;
        if (obj == null || cls == null || cls == RequestParams.class || cls == Object.class || (classLoader = cls.getClassLoader()) == null || classLoader == BOOT_CL) {
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                if (!Modifier.isTransient(field.getModifiers()) && field.getType() != Parcelable.Creator.class) {
                    field.setAccessible(true);
                    try {
                        String name = field.getName();
                        Object obj2 = field.get(obj);
                        if (obj2 != null) {
                            parseKVListener.onParseKV(name, obj2);
                        }
                    } catch (IllegalAccessException e) {
                        LogUtil.m43e(e.getMessage(), e);
                    }
                }
            }
        }
        parseKV(obj, cls.getSuperclass(), parseKVListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object parseJSONObject(Object obj) throws JSONException {
        if (obj == null) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            JSONArray jSONArray = new JSONArray();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                jSONArray.put(parseJSONObject(Array.get(obj, i)));
            }
            return jSONArray;
        } else if (obj instanceof Iterable) {
            JSONArray jSONArray2 = new JSONArray();
            for (Object obj2 : (Iterable) obj) {
                jSONArray2.put(parseJSONObject(obj2));
            }
            return jSONArray2;
        } else if (obj instanceof Map) {
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    jSONObject.put(String.valueOf(key), parseJSONObject(value));
                }
            }
            return jSONObject;
        } else {
            ClassLoader classLoader = cls.getClassLoader();
            if (classLoader == null || classLoader == BOOT_CL) {
                return obj;
            }
            final JSONObject jSONObject2 = new JSONObject();
            parseKV(obj, cls, new ParseKVListener() { // from class: org.xutils.http.RequestParamsHelper.1
                @Override // org.xutils.http.RequestParamsHelper.ParseKVListener
                public void onParseKV(String str, Object obj3) {
                    try {
                        jSONObject2.put(str, RequestParamsHelper.parseJSONObject(obj3));
                    } catch (JSONException e) {
                        throw new IllegalArgumentException("parse RequestParams to json failed", e);
                    }
                }
            });
            return jSONObject2;
        }
    }
}
