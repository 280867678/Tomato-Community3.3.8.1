package com.tencent.liteav.network;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.tencent.liteav.network.i */
/* loaded from: classes3.dex */
public class TXPlayInfoResponse {

    /* renamed from: a */
    protected JSONObject f4871a;

    /* compiled from: TXPlayInfoResponse.java */
    /* renamed from: com.tencent.liteav.network.i$a */
    /* loaded from: classes3.dex */
    public static class C3580a {

        /* renamed from: a */
        public String f4872a;

        /* renamed from: b */
        public String f4873b;

        /* renamed from: c */
        public List<Integer> f4874c;
    }

    public TXPlayInfoResponse(JSONObject jSONObject) {
        this.f4871a = jSONObject;
    }

    /* renamed from: a */
    public String m1128a() {
        if (m1123e() != null) {
            return m1123e().f4875a;
        }
        if (m1125c().size() != 0) {
            List<Integer> m1118j = m1118j();
            if (m1118j != null) {
                for (TXPlayInfoStream tXPlayInfoStream : m1125c()) {
                    if (m1118j.contains(Integer.valueOf(tXPlayInfoStream.m1117a()))) {
                        return tXPlayInfoStream.f4875a;
                    }
                }
            }
            return m1125c().get(0).f4875a;
        } else if (m1124d() != null) {
            return m1124d().f4875a;
        } else {
            return null;
        }
    }

    /* renamed from: b */
    public String m1126b() {
        try {
            JSONObject jSONObject = this.f4871a.getJSONObject("coverInfo");
            if (jSONObject == null) {
                return null;
            }
            return jSONObject.getString("coverUrl");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: c */
    public List<TXPlayInfoStream> m1125c() {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = this.f4871a.getJSONObject("videoInfo").getJSONArray("transcodeList");
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    TXPlayInfoStream tXPlayInfoStream = new TXPlayInfoStream();
                    tXPlayInfoStream.f4875a = jSONObject.getString("url");
                    tXPlayInfoStream.f4879e = jSONObject.getInt("duration");
                    tXPlayInfoStream.f4877c = jSONObject.getInt("width");
                    tXPlayInfoStream.f4876b = jSONObject.getInt("height");
                    tXPlayInfoStream.f4878d = Math.max(jSONObject.getInt("totalSize"), jSONObject.getInt("size"));
                    tXPlayInfoStream.f4880f = jSONObject.getInt("bitrate");
                    tXPlayInfoStream.f4882h = jSONObject.getInt("definition");
                    tXPlayInfoStream.f4881g = jSONObject.getString("container");
                    arrayList.add(tXPlayInfoStream);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /* renamed from: d */
    public TXPlayInfoStream m1124d() {
        try {
            JSONObject jSONObject = this.f4871a.getJSONObject("videoInfo").getJSONObject("sourceVideo");
            TXPlayInfoStream tXPlayInfoStream = new TXPlayInfoStream();
            tXPlayInfoStream.f4875a = jSONObject.getString("url");
            tXPlayInfoStream.f4879e = jSONObject.getInt("duration");
            tXPlayInfoStream.f4877c = jSONObject.getInt("width");
            tXPlayInfoStream.f4876b = jSONObject.getInt("height");
            tXPlayInfoStream.f4878d = Math.max(jSONObject.getInt("size"), jSONObject.getInt("totalSize"));
            tXPlayInfoStream.f4880f = jSONObject.getInt("bitrate");
            return tXPlayInfoStream;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: e */
    public TXPlayInfoStream m1123e() {
        try {
            JSONObject jSONObject = this.f4871a.getJSONObject("videoInfo").getJSONObject("masterPlayList");
            TXPlayInfoStream tXPlayInfoStream = new TXPlayInfoStream();
            tXPlayInfoStream.f4875a = jSONObject.getString("url");
            return tXPlayInfoStream;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: f */
    public String m1122f() {
        try {
            JSONObject jSONObject = this.f4871a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jSONObject == null) {
                return null;
            }
            return jSONObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: g */
    public String m1121g() {
        try {
            JSONObject jSONObject = this.f4871a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jSONObject == null) {
                return null;
            }
            return jSONObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: h */
    public String m1120h() {
        try {
            return this.f4871a.getJSONObject("playerInfo").getString("defaultVideoClassification");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: i */
    public List<C3580a> m1119i() {
        try {
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = this.f4871a.getJSONObject("playerInfo").getJSONArray("videoClassification");
            for (int i = 0; i < jSONArray.length(); i++) {
                C3580a c3580a = new C3580a();
                c3580a.f4872a = jSONArray.getJSONObject(i).getString(DatabaseFieldConfigLoader.FIELD_NAME_ID);
                c3580a.f4873b = jSONArray.getJSONObject(i).getString("name");
                c3580a.f4874c = new ArrayList();
                JSONArray jSONArray2 = jSONArray.getJSONObject(i).getJSONArray("definitionList");
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    c3580a.f4874c.add(Integer.valueOf(jSONArray2.getInt(i2)));
                }
                arrayList.add(c3580a);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: j */
    public List<Integer> m1118j() {
        List<C3580a> m1119i = m1119i();
        String m1120h = m1120h();
        if (m1120h == null || m1119i == null) {
            return null;
        }
        for (C3580a c3580a : m1119i) {
            if (c3580a.f4872a.equals(m1120h)) {
                return c3580a.f4874c;
            }
        }
        return null;
    }

    /* renamed from: a */
    public TXPlayInfoStream m1127a(String str, String str2) {
        List<Integer> list;
        List<C3580a> m1119i = m1119i();
        if (str != null && m1119i != null) {
            for (C3580a c3580a : m1119i) {
                if (c3580a.f4872a.equals(str)) {
                    list = c3580a.f4874c;
                    break;
                }
            }
        }
        list = null;
        if (list != null) {
            for (TXPlayInfoStream tXPlayInfoStream : m1125c()) {
                if (list.contains(Integer.valueOf(tXPlayInfoStream.f4882h)) && (tXPlayInfoStream.m1113e() == null || tXPlayInfoStream.m1113e().contains(str2))) {
                    return tXPlayInfoStream;
                }
            }
        }
        return null;
    }
}
