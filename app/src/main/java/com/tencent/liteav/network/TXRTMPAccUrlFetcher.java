package com.tencent.liteav.network;

import android.content.Context;
import android.os.Handler;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* renamed from: com.tencent.liteav.network.k */
/* loaded from: classes3.dex */
public class TXRTMPAccUrlFetcher {

    /* renamed from: a */
    private String f4883a = "";

    /* renamed from: b */
    private String f4884b = "";

    /* renamed from: c */
    private int f4885c = 0;

    /* renamed from: d */
    private String f4886d = "";

    /* renamed from: e */
    private Handler f4887e;

    /* compiled from: TXRTMPAccUrlFetcher.java */
    /* renamed from: com.tencent.liteav.network.k$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3587a {
        /* renamed from: a */
        void mo1096a(int i, String str, Vector<TXCStreamPlayUrl> vector);
    }

    public TXRTMPAccUrlFetcher(Context context) {
        if (context != null) {
            this.f4887e = new Handler(context.getMainLooper());
        }
    }

    /* renamed from: a */
    public String m1112a() {
        return this.f4883a;
    }

    /* renamed from: b */
    public String m1103b() {
        return this.f4884b;
    }

    /* renamed from: c */
    public int m1100c() {
        return this.f4885c;
    }

    /* renamed from: d */
    public String m1098d() {
        return this.f4886d;
    }

    /* renamed from: a */
    public int m1107a(String str, int i, final AbstractC3587a abstractC3587a) {
        this.f4883a = "";
        this.f4884b = "";
        this.f4885c = 0;
        this.f4886d = "";
        if (str == null || str.isEmpty()) {
            return -1;
        }
        final String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(str);
        if (streamIDByStreamUrl == null || streamIDByStreamUrl.isEmpty()) {
            return -2;
        }
        final String m1106a = m1106a("bizid", str);
        final String m1106a2 = m1106a("txSecret", str);
        final String m1106a3 = m1106a("txTime", str);
        if (!m1104a(true, m1106a, m1106a3, m1106a2)) {
            return -3;
        }
        m1105a(streamIDByStreamUrl, m1106a, m1106a2, m1106a3, i, new AbstractC3587a() { // from class: com.tencent.liteav.network.k.1
            @Override // com.tencent.liteav.network.TXRTMPAccUrlFetcher.AbstractC3587a
            /* renamed from: a */
            public void mo1096a(int i2, String str2, Vector<TXCStreamPlayUrl> vector) {
                TXRTMPAccUrlFetcher.this.f4883a = streamIDByStreamUrl;
                TXRTMPAccUrlFetcher.this.f4884b = m1106a;
                TXRTMPAccUrlFetcher.this.f4885c = i2;
                TXRTMPAccUrlFetcher.this.f4886d = str2;
                if (vector != null && !vector.isEmpty()) {
                    Vector<TXCStreamPlayUrl> vector2 = new Vector<>();
                    Iterator<TXCStreamPlayUrl> it2 = vector.iterator();
                    while (it2.hasNext()) {
                        TXCStreamPlayUrl next = it2.next();
                        String str3 = next.f4851a;
                        if (str3.indexOf("?") != -1) {
                            str3 = str3.substring(0, str3.indexOf("?"));
                        }
                        vector2.add(new TXCStreamPlayUrl(str3 + "?txSecret=" + m1106a2 + "&txTime=" + m1106a3 + "&bizid=" + m1106a, next.f4852b));
                    }
                    if (abstractC3587a == null) {
                        return;
                    }
                    Iterator<TXCStreamPlayUrl> it3 = vector2.iterator();
                    while (it3.hasNext()) {
                        TXCStreamPlayUrl next2 = it3.next();
                        TXCLog.m2914e("TXRTMPAccUrlFetcher", "accurl = " + next2.f4851a + " quic = " + next2.f4852b);
                    }
                    abstractC3587a.mo1096a(i2, str2, vector2);
                    return;
                }
                AbstractC3587a abstractC3587a2 = abstractC3587a;
                if (abstractC3587a2 == null) {
                    return;
                }
                abstractC3587a2.mo1096a(i2, str2, null);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public boolean m1104a(boolean z, String str, String str2, String str3) {
        return z ? str != null && !str.isEmpty() && str2 != null && !str2.isEmpty() && str3 != null && !str3.isEmpty() : (str == null || str2 == null || str3 == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public long m1097e() {
        return TXCConfigCenter.m2988a().m2979a("Network", "AccRetryCountWithoutSecret");
    }

    /* renamed from: a */
    private void m1105a(final String str, final String str2, final String str3, final String str4, final int i, final AbstractC3587a abstractC3587a) {
        new Thread("getRTMPAccUrl") { // from class: com.tencent.liteav.network.k.2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:78:0x0218 A[Catch: Exception -> 0x025b, TryCatch #1 {Exception -> 0x025b, blocks: (B:97:0x0203, B:100:0x0209, B:78:0x0218, B:80:0x021d, B:83:0x0223, B:86:0x0232, B:87:0x0236, B:89:0x023c, B:91:0x0246, B:94:0x024c), top: B:96:0x0203 }] */
            /* JADX WARN: Removed duplicated region for block: B:96:0x0203 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r12v0 */
            /* JADX WARN: Type inference failed for: r12v1, types: [boolean] */
            /* JADX WARN: Type inference failed for: r12v4 */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                String str5;
                JSONArray jSONArray;
                String str6 = "message";
                String str7 = str2;
                boolean z = false;
                int intValue = (str7 == null || str7.isEmpty()) ? 0 : Integer.valueOf(str2).intValue();
                int i2 = 5;
                ?? r12 = 1;
                if (!TXRTMPAccUrlFetcher.this.m1104a(true, str2, str4, str3) && (i2 = (int) TXRTMPAccUrlFetcher.this.m1097e()) <= 0) {
                    i2 = 1;
                }
                int i3 = i2;
                final String str8 = "";
                final int i4 = -1;
                while (i3 >= r12) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("bizid", intValue);
                        jSONObject.put("stream_id", str);
                        jSONObject.put("txSecret", str3);
                        jSONObject.put("txTime", str4);
                        int i5 = r12 == true ? 1 : 0;
                        int i6 = r12 == true ? 1 : 0;
                        jSONObject.put("type", i5);
                        String jSONObject2 = jSONObject.toString();
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://livepull.myqcloud.com/getpulladdr").openConnection();
                        httpsURLConnection.setDoOutput(r12);
                        httpsURLConnection.setDoInput(r12);
                        httpsURLConnection.setUseCaches(z);
                        httpsURLConnection.setConnectTimeout(5000);
                        httpsURLConnection.setReadTimeout(5000);
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setRequestProperty("Charsert", "UTF-8");
                        httpsURLConnection.setRequestProperty("Content-Type", "text/plain;");
                        httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(jSONObject2.length()));
                        TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: sendHttpRequest[ " + jSONObject2 + "] retryIndex = " + i3);
                        new DataOutputStream(httpsURLConnection.getOutputStream()).write(jSONObject2.getBytes());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                        String str9 = "";
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            str9 = str9 + readLine;
                        }
                        TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: receive response, strResponse = " + str9);
                        JSONObject jSONObject3 = (JSONObject) new JSONTokener(str9).nextValue();
                        if (jSONObject3.has("code")) {
                            i4 = jSONObject3.getInt("code");
                        }
                        if (i4 != 0) {
                            if (jSONObject3.has(str6)) {
                                str8 = jSONObject3.getString(str6);
                            }
                            TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: errorCode = " + i4 + " errorMessage = " + str8);
                        }
                        if (jSONObject3.has("pull_addr")) {
                            final Vector vector = new Vector();
                            final Vector vector2 = new Vector();
                            JSONArray jSONArray2 = jSONObject3.getJSONArray("pull_addr");
                            if (jSONArray2 != null && jSONArray2.length() != 0) {
                                int i7 = 0;
                                while (i7 < jSONArray2.length()) {
                                    JSONObject jSONObject4 = (JSONObject) jSONArray2.get(i7);
                                    if (jSONObject4 != null) {
                                        String string = jSONObject4.getString("rtmp_url");
                                        jSONArray = jSONArray2;
                                        boolean z2 = jSONObject4.getInt("proto") == 1;
                                        StringBuilder sb = new StringBuilder();
                                        str5 = str6;
                                        try {
                                            sb.append("getAccelerateStreamPlayUrl: streamUrl = ");
                                            sb.append(string);
                                            sb.append(" Q channel = ");
                                            sb.append(z2);
                                            TXCLog.m2914e("TXRTMPAccUrlFetcher", sb.toString());
                                            String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(string);
                                            if (streamIDByStreamUrl != null && streamIDByStreamUrl.equalsIgnoreCase(str)) {
                                                if (z2) {
                                                    vector.add(new TXCStreamPlayUrl(string, z2));
                                                } else {
                                                    vector2.add(new TXCStreamPlayUrl(string, z2));
                                                }
                                            }
                                        } catch (Exception e) {
                                            e = e;
                                            TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception");
                                            e.printStackTrace();
                                            z = false;
                                            Thread.sleep(1000L, 0);
                                            i3--;
                                            str6 = str5;
                                            r12 = 1;
                                        }
                                    } else {
                                        jSONArray = jSONArray2;
                                        str5 = str6;
                                    }
                                    i7++;
                                    jSONArray2 = jSONArray;
                                    str6 = str5;
                                }
                                str5 = str6;
                                if (i != 1) {
                                    try {
                                        if (vector2.size() > 0) {
                                            TXRTMPAccUrlFetcher.this.f4887e.post(new Runnable() { // from class: com.tencent.liteav.network.k.2.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    AbstractC3587a abstractC3587a2 = abstractC3587a;
                                                    if (abstractC3587a2 != null) {
                                                        abstractC3587a2.mo1096a(0, "Success", vector2);
                                                    }
                                                }
                                            });
                                            return;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception");
                                        e.printStackTrace();
                                        z = false;
                                        Thread.sleep(1000L, 0);
                                        i3--;
                                        str6 = str5;
                                        r12 = 1;
                                    }
                                } else if (i == 2) {
                                    if (vector.size() > 0) {
                                        TXRTMPAccUrlFetcher.this.f4887e.post(new Runnable() { // from class: com.tencent.liteav.network.k.2.2
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                AbstractC3587a abstractC3587a2 = abstractC3587a;
                                                if (abstractC3587a2 != null) {
                                                    abstractC3587a2.mo1096a(0, "Success", vector);
                                                }
                                            }
                                        });
                                        return;
                                    }
                                } else {
                                    Iterator it2 = vector2.iterator();
                                    while (it2.hasNext()) {
                                        vector.add((TXCStreamPlayUrl) it2.next());
                                    }
                                    if (vector.size() > 0) {
                                        TXRTMPAccUrlFetcher.this.f4887e.post(new Runnable() { // from class: com.tencent.liteav.network.k.2.3
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                AbstractC3587a abstractC3587a2 = abstractC3587a;
                                                if (abstractC3587a2 != null) {
                                                    abstractC3587a2.mo1096a(0, "Success", vector);
                                                }
                                            }
                                        });
                                        return;
                                    }
                                }
                            }
                            str5 = str6;
                            TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: no pull_addr");
                            if (i != 1) {
                            }
                        } else {
                            str5 = str6;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        str5 = str6;
                    }
                    z = false;
                    try {
                        Thread.sleep(1000L, 0);
                    } catch (Exception e4) {
                        TXCLog.m2914e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception sleep");
                        e4.printStackTrace();
                    }
                    i3--;
                    str6 = str5;
                    r12 = 1;
                }
                TXRTMPAccUrlFetcher.this.f4887e.post(new Runnable() { // from class: com.tencent.liteav.network.k.2.4
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractC3587a abstractC3587a2 = abstractC3587a;
                        if (abstractC3587a2 != null) {
                            abstractC3587a2.mo1096a(i4, str8, null);
                        }
                    }
                });
            }
        }.start();
    }

    /* renamed from: a */
    private String m1106a(String str, String str2) {
        String[] split;
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        for (String str3 : str2.split("[?&]")) {
            if (str3.indexOf(SimpleComparison.EQUAL_TO_OPERATION) != -1) {
                String[] split2 = str3.split("[=]");
                if (split2.length == 2) {
                    String str4 = split2[0];
                    String str5 = split2[1];
                    if (str4 != null && str4.toLowerCase().equalsIgnoreCase(lowerCase)) {
                        return str5;
                    }
                } else {
                    continue;
                }
            }
        }
        return "";
    }
}
