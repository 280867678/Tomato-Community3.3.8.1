package com.tomatolive.library.utils.emoji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EmojiLoader {
    private EmojiLoader() {
    }

    public static List<Emoji> loadEmojis(InputStream inputStream) throws IOException {
        ArrayList arrayList = null;
        try {
            JSONArray jSONArray = new JSONArray(inputStreamToString(inputStream));
            ArrayList arrayList2 = new ArrayList(jSONArray.length());
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    Emoji buildEmojiFromJSON = buildEmojiFromJSON(jSONArray.getJSONObject(i));
                    if (buildEmojiFromJSON != null) {
                        arrayList2.add(buildEmojiFromJSON);
                    }
                } catch (JSONException e) {
                    arrayList = arrayList2;
                    e = e;
                    e.printStackTrace();
                    return arrayList;
                }
            }
            return arrayList2;
        } catch (JSONException e2) {
            e = e2;
        }
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    protected static Emoji buildEmojiFromJSON(JSONObject jSONObject) throws UnsupportedEncodingException, JSONException {
        String str = null;
        if (!jSONObject.has("emoji")) {
            return null;
        }
        byte[] bytes = jSONObject.getString("emoji").getBytes("UTF-8");
        if (jSONObject.has("description")) {
            str = jSONObject.getString("description");
        }
        return new Emoji(str, jSONObject.has("supports_fitzpatrick") ? jSONObject.getBoolean("supports_fitzpatrick") : false, jsonArrayToStringList(jSONObject.getJSONArray("aliases")), jsonArrayToStringList(jSONObject.getJSONArray("tags")), bytes);
    }

    private static List<String> jsonArrayToStringList(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        return arrayList;
    }
}
