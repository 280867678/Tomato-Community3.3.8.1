package com.youdao.sdk.ydonlinetranslate.other;

import android.util.Log;
import com.youdao.sdk.ydonlinetranslate.OCRTranslateResult;
import com.youdao.sdk.ydonlinetranslate.Region;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.youdao.sdk.ydonlinetranslate.other.a */
/* loaded from: classes4.dex */
public class C5179a {
    /* renamed from: a */
    public static void m161a(OCRTranslateResult oCRTranslateResult) {
        try {
            JSONObject jSONObject = new JSONObject(oCRTranslateResult.getJson());
            oCRTranslateResult.setErrorCode(Integer.parseInt(jSONObject.getString("errorCode")));
            oCRTranslateResult.setFrom(jSONObject.getString("lanFrom"));
            oCRTranslateResult.setTo(jSONObject.getString("lanTo"));
            oCRTranslateResult.setOrientation(jSONObject.getString("orientation"));
            oCRTranslateResult.setTextAngle(jSONObject.getInt("textAngle"));
            JSONArray jSONArray = jSONObject.getJSONArray("resRegions");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                Region region = new Region();
                m160a(region, jSONObject2);
                oCRTranslateResult.getRegions().add(region);
            }
        } catch (Exception e) {
            Log.w("ocr parse", e);
        }
    }

    /* renamed from: a */
    private static void m160a(Region region, JSONObject jSONObject) {
        region.setBoundingBox(m159a(jSONObject.getString("boundingBox")));
        region.setContext(jSONObject.getString("context"));
        region.setTranContent(jSONObject.getString("tranContent"));
        region.setLinesCount(jSONObject.getInt("linesCount"));
        region.setLineheight(jSONObject.getInt("lineheight"));
    }

    /* renamed from: a */
    private static C5182d m159a(String str) {
        C5182d c5182d = new C5182d();
        String[] split = str.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        int parseInt3 = Integer.parseInt(split[2]);
        int parseInt4 = Integer.parseInt(split[3]);
        c5182d.m155a(parseInt);
        c5182d.m154b(parseInt2);
        c5182d.m153c(parseInt3);
        c5182d.m152d(parseInt4);
        return c5182d;
    }
}
