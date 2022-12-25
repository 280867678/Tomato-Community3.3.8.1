package com.tencent.liteav.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.tencent.liteav.network.m */
/* loaded from: classes3.dex */
public class UploadQualityData {

    /* renamed from: a */
    protected static UploadQualityData f4925a = new UploadQualityData();

    /* renamed from: b */
    private Context f4926b = null;

    /* renamed from: c */
    private long f4927c = 3;

    /* renamed from: a */
    public static UploadQualityData m1095a() {
        return f4925a;
    }

    protected UploadQualityData() {
    }

    /* renamed from: a */
    public void m1094a(Context context) {
        if (this.f4926b == null) {
            this.f4926b = context.getApplicationContext();
        }
    }

    /* renamed from: b */
    public String m1089b() {
        WifiInfo connectionInfo;
        String str = "";
        try {
            if (this.f4926b != null) {
                int m2876c = TXCSystemUtil.m2876c(this.f4926b);
                if (m2876c == 255) {
                    return str;
                }
                if (m2876c != 1) {
                    return m2876c == 2 ? "4g:" : m2876c == 3 ? "3g:" : m2876c == 4 ? "2g:" : m2876c == 5 ? "ethernet:" : "xg:";
                }
                WifiManager wifiManager = (WifiManager) this.f4926b.getSystemService(AopConstants.WIFI);
                if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                    str = "wifi:" + connectionInfo.getSSID();
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /* renamed from: a */
    public void m1091a(String str, long j, long j2, long j3, float f, float f2, float f3) {
        if (!(TXCConfigCenter.m2988a().m2979a("Network", "QualityDataCacheCount") > 0)) {
            return;
        }
        TXCLog.m2914e("UploadQualityData", String.format("updateQualityData: accessID = %s serverType = %d totalTime = %d networkRTT = %d avgBlockCnt = %f avgVideoQue = %f avgAudioQue = %f", str, Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3), Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)));
        if (m1088b(str)) {
            return;
        }
        try {
            SharedPreferences sharedPreferences = this.f4926b.getSharedPreferences("com.tencent.liteav.network", 0);
            JSONObject m1086c = m1086c(sharedPreferences.getString("34238512-C08C-4931-A000-40E1D8B5BA5B", ""));
            JSONObject optJSONObject = m1086c.optJSONObject(str);
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            String str2 = j == 3 ? "DomainArrayData" : "OriginArrayData";
            JSONArray optJSONArray = optJSONObject.optJSONArray(str2);
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("totalTime", j2);
            jSONObject.put("networkRTT", j3);
            jSONObject.put("avgBlockCnt", f);
            jSONObject.put("avgVideoQue", f2);
            jSONObject.put("avgAudioQue", f3);
            optJSONArray.put(jSONObject);
            int length = optJSONArray.length();
            long j4 = length;
            if (j4 > this.f4927c) {
                JSONArray jSONArray = new JSONArray();
                for (int i = (int) (j4 - this.f4927c); i < length; i++) {
                    jSONArray.put(optJSONArray.get(i));
                }
                optJSONArray = jSONArray;
            }
            optJSONObject.put(str2, optJSONArray);
            m1086c.put(str, optJSONObject);
            sharedPreferences.edit().putString("34238512-C08C-4931-A000-40E1D8B5BA5B", m1086c.toString()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: c */
    public boolean m1087c() {
        m1085d();
        String m1089b = m1089b();
        String str = "isDomainAddressBetter: accessID = " + m1089b + " minQualityDataCount = " + this.f4927c;
        C3588a m1090a = m1090a(m1089b, true);
        C3588a m1090a2 = m1090a(m1089b, false);
        if (m1090a != null) {
            str = String.format("%s \n isDomainAddressBetter：domainQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str, Long.valueOf(m1090a.f4932e), Float.valueOf(m1090a.f4928a), Float.valueOf(m1090a.f4929b), Float.valueOf(m1090a.f4930c), Float.valueOf(m1090a.f4931d));
        }
        if (m1090a2 != null) {
            str = String.format("%s \n isDomainAddressBetter：originQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str, Long.valueOf(m1090a2.f4932e), Float.valueOf(m1090a2.f4928a), Float.valueOf(m1090a2.f4929b), Float.valueOf(m1090a2.f4930c), Float.valueOf(m1090a2.f4931d));
        }
        TXCLog.m2914e("UploadQualityData", str);
        if (m1090a != null) {
            long j = m1090a.f4932e;
            long j2 = this.f4927c;
            if (j >= j2 && m1090a2 != null && m1090a2.f4932e >= j2 && m1090a.f4929b < m1090a2.f4929b && m1090a.f4930c < m1090a2.f4930c && m1090a.f4931d < m1090a2.f4931d) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* compiled from: UploadQualityData.java */
    /* renamed from: com.tencent.liteav.network.m$a */
    /* loaded from: classes3.dex */
    public class C3588a {

        /* renamed from: a */
        public float f4928a = 0.0f;

        /* renamed from: b */
        public float f4929b = 0.0f;

        /* renamed from: c */
        public float f4930c = 0.0f;

        /* renamed from: d */
        public float f4931d = 0.0f;

        /* renamed from: e */
        public long f4932e = 0;

        protected C3588a() {
        }
    }

    /* renamed from: a */
    private C3588a m1090a(String str, boolean z) {
        JSONObject optJSONObject;
        if (m1088b(str)) {
            return null;
        }
        try {
            String string = this.f4926b.getSharedPreferences("com.tencent.liteav.network", 0).getString("34238512-C08C-4931-A000-40E1D8B5BA5B", "");
            if (m1088b(string) || (optJSONObject = new JSONObject(string).optJSONObject(str)) == null) {
                return null;
            }
            JSONArray optJSONArray = optJSONObject.optJSONArray(z ? "DomainArrayData" : "OriginArrayData");
            if (optJSONArray == null) {
                return null;
            }
            long length = optJSONArray.length();
            if (length == 0) {
                return null;
            }
            String str2 = "";
            float f = 0.0f;
            int i = 0;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            while (i < length) {
                JSONObject jSONObject = optJSONArray.getJSONObject(i);
                f2 = (float) (f2 + jSONObject.optDouble("avgBlockCnt"));
                f3 = (float) (f3 + jSONObject.optDouble("avgVideoQue"));
                f4 = (float) (f4 + jSONObject.optDouble("avgAudioQue"));
                str2 = String.format("%s \n isDomainAddressBetter：itemData domain = %b NetworkRTT = %d avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str2, Boolean.valueOf(z), Long.valueOf(jSONObject.optLong("networkRTT")), Double.valueOf(jSONObject.optDouble("avgBlockCnt")), Double.valueOf(jSONObject.optDouble("avgVideoQue")), Double.valueOf(jSONObject.optDouble("avgAudioQue")));
                i++;
                f += (float) jSONObject.optLong("networkRTT");
                length = length;
            }
            long j = length;
            float f5 = (float) j;
            float f6 = f / f5;
            float f7 = f2 / f5;
            float f8 = f3 / f5;
            float f9 = f4 / f5;
            C3588a c3588a = new C3588a();
            c3588a.f4928a = f6;
            c3588a.f4929b = f7;
            c3588a.f4930c = f8;
            c3588a.f4931d = f9;
            c3588a.f4932e = j;
            return c3588a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: b */
    private boolean m1088b(String str) {
        return str == null || str.length() == 0;
    }

    /* renamed from: c */
    private JSONObject m1086c(String str) {
        if (!m1088b(str)) {
            try {
                return new JSONObject(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    /* renamed from: d */
    private void m1085d() {
        this.f4927c = TXCConfigCenter.m2988a().m2979a("Network", "QualityDataCacheCount");
        long j = this.f4927c;
        if (j == -1 || j < 3) {
            this.f4927c = 3L;
        }
    }

    /* renamed from: a */
    public long m1093a(String str) {
        Context context = this.f4926b;
        if (context != null) {
            return context.getSharedPreferences("com.tencent.liteav.network", 0).getLong(str, 0L);
        }
        return 0L;
    }

    /* renamed from: a */
    public void m1092a(String str, long j) {
        Context context = this.f4926b;
        if (context != null) {
            context.getSharedPreferences("com.tencent.liteav.network", 0).edit().putLong(str, j).commit();
        }
    }
}
