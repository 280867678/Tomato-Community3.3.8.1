package com.sensorsdata.analytics.android.sdk;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class PropertyBuilder {
    private static final String TAG = "PropertyBuilder";
    private final LinkedHashMap<String, Object> innerPropertyMap = new LinkedHashMap<>();

    private PropertyBuilder() {
    }

    public static PropertyBuilder newInstance() {
        return new PropertyBuilder();
    }

    public PropertyBuilder append(String str, Object obj) {
        this.innerPropertyMap.put(str, obj);
        return this;
    }

    public PropertyBuilder append(Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            this.innerPropertyMap.putAll(map);
        }
        return this;
    }

    public PropertyBuilder append(Object... objArr) {
        if (objArr == null || objArr.length <= 1) {
            SALog.m3674i(TAG, "The key value pair is incorrect.");
            return this;
        }
        int i = 0;
        while (i < objArr.length) {
            Object obj = objArr[i];
            int i2 = i + 1;
            if (i2 >= objArr.length) {
                SALog.m3674i(TAG, "this element key[index= " + i2 + "] will be ignored, because no element can pair with it. ");
                return this;
            }
            Object obj2 = objArr[i2];
            if (obj instanceof String) {
                this.innerPropertyMap.put((String) obj, obj2);
            } else {
                SALog.m3674i(TAG, "this element key[index= " + i2 + "] is not a String, the method will ignore the element and the next element. ");
            }
            i = i2 + 1;
        }
        return this;
    }

    public JSONObject toJSONObject() {
        this.innerPropertyMap.remove(null);
        if (this.innerPropertyMap.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : this.innerPropertyMap.keySet()) {
            try {
                jSONObject.put(str, this.innerPropertyMap.get(str));
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
        return jSONObject;
    }

    public int size() {
        return this.innerPropertyMap.size();
    }

    public Object remove(String str) {
        return this.innerPropertyMap.remove(str);
    }

    public void clear() {
        this.innerPropertyMap.clear();
    }
}
