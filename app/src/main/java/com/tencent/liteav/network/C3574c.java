package com.tencent.liteav.network;

import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TXCIntelligentRoute.java */
/* renamed from: com.tencent.liteav.network.c */
/* loaded from: classes3.dex */
class C3574c {

    /* renamed from: c */
    private final String f4820c = "http://tcdns.myqcloud.com/queryip";

    /* renamed from: d */
    private final String f4821d = "forward_stream";

    /* renamed from: e */
    private final String f4822e = "forward_num";

    /* renamed from: f */
    private final String f4823f = "request_type";

    /* renamed from: g */
    private final String f4824g = "sdk_version";

    /* renamed from: h */
    private final String f4825h = "51451748-d8f2-4629-9071-db2983aa7251";

    /* renamed from: a */
    public AbstractC3573b f4818a = null;

    /* renamed from: b */
    public int f4819b = 5;

    /* renamed from: i */
    private Thread f4826i = null;

    /* renamed from: a */
    public void m1162a(final String str, final int i) {
        this.f4826i = new Thread("TXCPushRoute") { // from class: com.tencent.liteav.network.c.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (C3574c.this.f4818a == null) {
                    return;
                }
                ArrayList<TXCIntelligentRoute> arrayList = new ArrayList<>();
                for (int i2 = 0; i2 < 5; i2++) {
                    try {
                        String m1157b = C3574c.this.m1157b(str, i);
                        try {
                            JSONObject jSONObject = new JSONObject(m1157b);
                            if (jSONObject.has("use") && jSONObject.getInt("use") == 0) {
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        arrayList = C3574c.this.m1163a(m1157b);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (arrayList != null && arrayList.size() > 0) {
                        break;
                    }
                    Thread.sleep(1000L, 0);
                }
                C3574c.this.f4818a.onFetchDone(0, arrayList);
            }
        };
        this.f4826i.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public String m1157b(String str, int i) {
        InputStream m1155c;
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            m1155c = m1155c(str, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (m1155c == null) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(m1155c));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            stringBuffer.append(readLine);
        }
        return stringBuffer.toString();
    }

    /* renamed from: c */
    private InputStream m1155c(String str, int i) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://tcdns.myqcloud.com/queryip").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("forward_stream", str);
            httpURLConnection.setRequestProperty("forward_num", "2");
            httpURLConnection.setRequestProperty("sdk_version", TXCCommonUtil.getSDKVersionStr());
            if (i == 1) {
                httpURLConnection.setRequestProperty("request_type", "1");
            } else if (i == 2) {
                httpURLConnection.setRequestProperty("request_type", "2");
            } else {
                httpURLConnection.setRequestProperty("request_type", "3");
            }
            if (this.f4819b > 0) {
                httpURLConnection.setConnectTimeout(this.f4819b * 1000);
                httpURLConnection.setReadTimeout(this.f4819b * 1000);
            }
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            return httpURLConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public ArrayList<TXCIntelligentRoute> m1163a(String str) {
        JSONArray jSONArray;
        ArrayList<TXCIntelligentRoute> arrayList = new ArrayList<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.getInt("state") != 0 || (jSONArray = jSONObject.getJSONObject("content").getJSONArray(C2516Ad.TYPE_LIST)) == null) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                TXCIntelligentRoute m1159a = m1159a((JSONObject) jSONArray.opt(i));
                if (m1159a != null && m1159a.f4795c) {
                    arrayList.add(m1159a);
                }
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                TXCIntelligentRoute m1159a2 = m1159a((JSONObject) jSONArray.opt(i2));
                if (m1159a2 != null && !m1159a2.f4795c) {
                    arrayList.add(m1159a2);
                }
            }
            if (TXCConfigCenter.m2988a().m2979a("Network", "EnableRouteOptimize") == 1 && UploadQualityData.m1095a().m1087c()) {
                ArrayList<TXCIntelligentRoute> m1160a = m1160a(arrayList, true);
                m1161a(m1160a);
                return m1160a;
            }
            long m2979a = TXCConfigCenter.m2988a().m2979a("Network", "RouteSamplingMaxCount");
            if (m2979a >= 1) {
                long m1093a = UploadQualityData.m1095a().m1093a("51451748-d8f2-4629-9071-db2983aa7251");
                if (m1093a <= m2979a) {
                    arrayList = m1160a(arrayList, false);
                    UploadQualityData.m1095a().m1092a("51451748-d8f2-4629-9071-db2983aa7251", m1093a + 1);
                }
            }
            m1161a(arrayList);
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    /* renamed from: a */
    private TXCIntelligentRoute m1159a(JSONObject jSONObject) {
        TXCIntelligentRoute tXCIntelligentRoute = new TXCIntelligentRoute();
        try {
            tXCIntelligentRoute.f4793a = jSONObject.getString("ip");
            tXCIntelligentRoute.f4794b = jSONObject.getString("port");
            tXCIntelligentRoute.f4797e = 0;
            tXCIntelligentRoute.f4795c = false;
            tXCIntelligentRoute.f4796d = m1158b(tXCIntelligentRoute.f4793a);
            if (jSONObject.has("type") && jSONObject.getInt("type") == 2) {
                tXCIntelligentRoute.f4795c = true;
            }
            return tXCIntelligentRoute;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: b */
    private boolean m1158b(String str) {
        if (str != null) {
            for (String str2 : str.split("[.]")) {
                if (!m1156c(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: c */
    private boolean m1156c(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    private ArrayList<TXCIntelligentRoute> m1160a(ArrayList<TXCIntelligentRoute> arrayList, boolean z) {
        TXCIntelligentRoute tXCIntelligentRoute;
        TXCIntelligentRoute tXCIntelligentRoute2;
        TXCIntelligentRoute tXCIntelligentRoute3 = null;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<TXCIntelligentRoute> it2 = arrayList.iterator();
        loop0: while (true) {
            tXCIntelligentRoute = tXCIntelligentRoute3;
            while (it2.hasNext()) {
                tXCIntelligentRoute3 = it2.next();
                if (tXCIntelligentRoute3.f4795c) {
                    arrayList2.add(tXCIntelligentRoute3);
                } else if (tXCIntelligentRoute3.f4796d) {
                    break;
                } else {
                    arrayList3.add(tXCIntelligentRoute3);
                }
            }
        }
        ArrayList<TXCIntelligentRoute> arrayList4 = new ArrayList<>();
        while (true) {
            if (arrayList2.size() <= 0 && arrayList3.size() <= 0) {
                break;
            }
            if (z) {
                if (tXCIntelligentRoute != null) {
                    arrayList4.add(tXCIntelligentRoute);
                }
                if (arrayList2.size() > 0) {
                    arrayList4.add(arrayList2.get(0));
                    arrayList2.remove(0);
                }
            } else {
                if (arrayList2.size() > 0) {
                    arrayList4.add(arrayList2.get(0));
                    arrayList2.remove(0);
                }
                if (tXCIntelligentRoute != null) {
                    arrayList4.add(tXCIntelligentRoute);
                }
            }
            if (arrayList3.size() > 0) {
                arrayList4.add(arrayList3.get(0));
                arrayList3.remove(0);
            }
        }
        int size = arrayList4.size();
        if (size > 0 && (tXCIntelligentRoute2 = (TXCIntelligentRoute) arrayList4.get(size - 1)) != null && !m1158b(tXCIntelligentRoute2.f4793a) && tXCIntelligentRoute != null) {
            arrayList4.add(tXCIntelligentRoute);
        }
        return arrayList4;
    }

    /* renamed from: a */
    private void m1161a(ArrayList<TXCIntelligentRoute> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator<TXCIntelligentRoute> it2 = arrayList.iterator();
        String str = "";
        while (it2.hasNext()) {
            TXCIntelligentRoute next = it2.next();
            str = str + " \n Nearest IP: " + next.f4793a + " Port: " + next.f4794b + " Q Channel: " + next.f4795c;
        }
        TXCLog.m2914e("TXCIntelligentRoute", str);
    }
}
