package com.tencent.liteav.network;

import android.content.Context;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.util.regex.Pattern;

/* renamed from: com.tencent.liteav.network.n */
/* loaded from: classes3.dex */
public class UploadQualityReport {

    /* renamed from: a */
    private Context f4934a;

    /* renamed from: b */
    private String f4935b;

    /* renamed from: c */
    private String f4936c;

    /* renamed from: e */
    private String f4938e;

    /* renamed from: f */
    private long f4939f;

    /* renamed from: g */
    private long f4940g;

    /* renamed from: h */
    private String f4941h;

    /* renamed from: i */
    private long f4942i;

    /* renamed from: j */
    private long f4943j;

    /* renamed from: k */
    private long f4944k;

    /* renamed from: l */
    private long f4945l;

    /* renamed from: m */
    private long f4946m;

    /* renamed from: n */
    private long f4947n;

    /* renamed from: o */
    private long f4948o;

    /* renamed from: p */
    private long f4949p;

    /* renamed from: q */
    private long f4950q;

    /* renamed from: r */
    private long f4951r;

    /* renamed from: s */
    private long f4952s;

    /* renamed from: t */
    private long f4953t;

    /* renamed from: u */
    private long f4954u;

    /* renamed from: v */
    private long f4955v;

    /* renamed from: w */
    private boolean f4956w = true;

    /* renamed from: d */
    private String f4937d = "Android";

    public UploadQualityReport(Context context) {
        this.f4934a = context.getApplicationContext();
        this.f4935b = TXCSystemUtil.m2890a(this.f4934a);
        UploadQualityData.m1095a().m1094a(this.f4934a);
        m1084a();
    }

    /* renamed from: a */
    public void m1084a() {
        this.f4936c = "";
        this.f4939f = 0L;
        this.f4940g = -1L;
        this.f4941h = "";
        this.f4942i = -1L;
        this.f4943j = -1L;
        this.f4944k = -1L;
        this.f4945l = -1L;
        this.f4938e = "";
        this.f4946m = 0L;
        this.f4947n = 0L;
        this.f4948o = 0L;
        this.f4949p = 0L;
        this.f4950q = 0L;
        this.f4951r = 0L;
        this.f4952s = 0L;
        this.f4953t = 0L;
        this.f4954u = 0L;
        this.f4955v = 0L;
        this.f4956w = true;
    }

    /* renamed from: e */
    private void m1072e() {
        long j = this.f4949p;
        long j2 = this.f4950q;
        m1084a();
        this.f4947n = j;
        this.f4948o = j2;
    }

    /* renamed from: b */
    public void m1078b() {
        this.f4939f = System.currentTimeMillis();
        this.f4936c = UploadQualityData.m1095a().m1089b();
    }

    /* renamed from: c */
    public void m1075c() {
        m1071f();
        m1072e();
    }

    /* renamed from: a */
    public void m1080a(boolean z) {
        this.f4945l = z ? 2L : 1L;
        if (z) {
            this.f4956w = false;
        }
    }

    /* renamed from: a */
    public void m1081a(String str) {
        this.f4938e = str;
    }

    /* renamed from: a */
    public void m1079a(boolean z, String str) {
        this.f4941h = str;
        if (z) {
            this.f4940g = 1L;
        } else if (str != null) {
            int indexOf = str.indexOf(":");
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            if (str == null) {
                return;
            }
            for (String str2 : str.split("[.]")) {
                if (!m1074c(str2)) {
                    this.f4940g = 3L;
                    return;
                }
            }
            this.f4940g = 2L;
        }
    }

    /* renamed from: a */
    public void m1082a(long j, long j2, long j3) {
        this.f4942i = j;
        this.f4943j = j2;
        this.f4944k = j3;
    }

    /* renamed from: d */
    public void m1073d() {
        this.f4946m++;
    }

    /* renamed from: a */
    public void m1083a(long j, long j2) {
        this.f4949p = j;
        this.f4950q = j2;
    }

    /* renamed from: b */
    public void m1077b(long j, long j2) {
        this.f4955v++;
        this.f4951r += j;
        this.f4952s += j2;
        if (j > this.f4953t) {
            this.f4953t = j;
        }
        if (j2 > this.f4954u) {
            this.f4954u = j2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x015d  */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void m1071f() {
        long j;
        long j2;
        float f;
        long j3;
        float f2;
        float f3;
        if (this.f4939f == 0 || m1076b(this.f4935b) || m1076b(this.f4938e)) {
            return;
        }
        String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(this.f4938e);
        long currentTimeMillis = System.currentTimeMillis() - this.f4939f;
        long j4 = this.f4949p;
        long j5 = this.f4947n;
        long j6 = j4 > j5 ? j4 - j5 : 0L;
        long j7 = this.f4950q;
        long j8 = this.f4948o;
        long j9 = j7 > j8 ? j7 - j8 : 0L;
        long j10 = this.f4955v;
        if (j10 > 0) {
            j2 = this.f4951r / j10;
            j = this.f4952s / j10;
        } else {
            j = 0;
            j2 = 0;
        }
        String txCreateToken = TXCDRApi.txCreateToken();
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.f4938e;
        TXCDRApi.InitEvent(this.f4934a, txCreateToken, TXCDRDef.f2416T, TXCDRDef.f2461al, tXCDRExtInfo);
        TXCDRApi.txSetEventValue(txCreateToken, TXCDRDef.f2416T, "str_user_id", this.f4935b);
        TXCDRApi.txSetEventValue(txCreateToken, TXCDRDef.f2416T, "str_stream_id", streamIDByStreamUrl);
        TXCDRApi.txSetEventValue(txCreateToken, TXCDRDef.f2416T, "str_access_id", this.f4936c);
        TXCDRApi.txSetEventValue(txCreateToken, TXCDRDef.f2416T, "str_platform", this.f4937d);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_server_type", this.f4940g);
        TXCDRApi.txSetEventValue(txCreateToken, TXCDRDef.f2416T, "str_server_addr", this.f4941h);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_dns_timecost", this.f4942i);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_connect_timecost", this.f4943j);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_handshake_timecost", this.f4944k);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_push_type", this.f4945l);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_totaltime", currentTimeMillis);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_block_count", this.f4946m);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_video_drop", j6);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_audio_drop", j9);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_video_que_avg", j2);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_audio_que_avg", j);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_video_que_max", this.f4953t);
        TXCDRApi.txSetEventIntValue(txCreateToken, TXCDRDef.f2416T, "u32_audio_que_max", this.f4954u);
        TXCDRApi.nativeReportEvent(txCreateToken, TXCDRDef.f2416T);
        if (currentTimeMillis > 0) {
            long j11 = this.f4946m;
            if (j11 != 0) {
                f = (((float) j11) * 60000.0f) / ((float) currentTimeMillis);
                j3 = this.f4955v;
                if (j3 <= 0) {
                    long j12 = this.f4951r;
                    float f4 = j12 == 0 ? 0.0f : ((float) j12) / ((float) j3);
                    long j13 = this.f4952s;
                    if (j13 != 0) {
                        f3 = ((float) j13) / ((float) this.f4955v);
                        f2 = f4;
                        if (this.f4956w || m1076b(this.f4936c) || this.f4944k == -1) {
                            return;
                        }
                        UploadQualityData.m1095a().m1091a(this.f4936c, this.f4940g, currentTimeMillis, this.f4944k, f, f2, f3);
                        return;
                    }
                    f2 = f4;
                } else {
                    f2 = 0.0f;
                }
                f3 = 0.0f;
                if (this.f4956w) {
                    return;
                }
                return;
            }
        }
        f = 0.0f;
        j3 = this.f4955v;
        if (j3 <= 0) {
        }
        f3 = 0.0f;
        if (this.f4956w) {
        }
    }

    /* renamed from: b */
    private boolean m1076b(String str) {
        return str == null || str.length() == 0;
    }

    /* renamed from: c */
    private boolean m1074c(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }
}
