package com.youdao.sdk.app;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class JsonHelper {
    public static List<String> parseList(JSONObject jSONObject, String str) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject != null && !jSONObject.isNull(str)) {
            JSONArray jSONArray = jSONObject.getJSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(String.valueOf(jSONArray.get(i)));
            }
            return arrayList;
        }
        return arrayList;
    }

    public static String parseValue(JSONObject jSONObject, String str, String str2) {
        return (jSONObject != null && !jSONObject.isNull(str)) ? jSONObject.getString(str) : str2;
    }

    public static int parseValue(JSONObject jSONObject, String str, int i) {
        return (jSONObject != null && !jSONObject.isNull(str)) ? jSONObject.getInt(str) : i;
    }

    public static JSONObject parseJSONObject(JSONObject jSONObject, String str) {
        if (jSONObject != null && !jSONObject.isNull(str)) {
            return jSONObject.getJSONObject(str);
        }
        return null;
    }
}
