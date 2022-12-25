package com.tencent.liteav;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.tencent.liteav.n */
/* loaded from: classes3.dex */
public class TXCVodPlayCollection {

    /* renamed from: b */
    private Context f4731b;

    /* renamed from: m */
    private int f4742m;

    /* renamed from: v */
    private int f4751v;

    /* renamed from: w */
    private int f4752w;

    /* renamed from: x */
    private int f4753x;

    /* renamed from: a */
    private final String f4730a = "TXCVodPlayCollection";

    /* renamed from: c */
    private String f4732c = null;

    /* renamed from: d */
    private long f4733d = 0;

    /* renamed from: e */
    private long f4734e = 0;

    /* renamed from: f */
    private boolean f4735f = false;

    /* renamed from: g */
    private int f4736g = 0;

    /* renamed from: h */
    private int f4737h = 0;

    /* renamed from: i */
    private int f4738i = 0;

    /* renamed from: j */
    private int f4739j = 0;

    /* renamed from: k */
    private int f4740k = 0;

    /* renamed from: l */
    private int f4741l = 0;

    /* renamed from: o */
    private boolean f4744o = false;

    /* renamed from: p */
    private boolean f4745p = false;

    /* renamed from: q */
    private int f4746q = 0;

    /* renamed from: r */
    private int f4747r = 0;

    /* renamed from: s */
    private int f4748s = 0;

    /* renamed from: t */
    private int f4749t = 0;

    /* renamed from: u */
    private int f4750u = 0;

    /* renamed from: n */
    private String f4743n = TXCCommonUtil.getAppVersion();

    public TXCVodPlayCollection(Context context) {
        this.f4731b = context;
    }

    /* renamed from: a */
    public void m1200a(String str) {
        this.f4732c = str;
    }

    /* renamed from: a */
    public String m1202a() {
        Context context = this.f4731b;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int i = applicationInfo.labelRes;
        return i == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i);
    }

    /* renamed from: m */
    private void m1186m() {
        String str;
        long j;
        String m2875d = TXCSystemUtil.m2875d();
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.f4732c;
        TXCDRApi.InitEvent(this.f4731b, m2875d, TXCDRDef.f2453ad, TXCDRDef.f2468as, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_timeuse", this.f4737h);
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_stream_url", this.f4732c);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_videotime", this.f4736g);
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_device_type", TXCSystemUtil.m2877c());
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_network_type", TXCSystemUtil.m2876c(this.f4731b));
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_user_id", TXCSystemUtil.m2890a(this.f4731b));
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_package_name", TXCSystemUtil.m2880b(this.f4731b));
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_app_version", this.f4743n);
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "dev_uuid", TXCSystemUtil.m2874d(this.f4731b));
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_first_i_frame", this.f4738i);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_isp2p", this.f4739j);
        int i = TXCDRDef.f2453ad;
        int i2 = this.f4740k;
        TXCDRApi.txSetEventIntValue(m2875d, i, "u32_avg_load", i2 == 0 ? 0L : this.f4741l / i2);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_load_cnt", this.f4740k);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_max_load", this.f4742m);
        TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_player_type", this.f4747r);
        TXCDRApi.txSetEventValue(m2875d, TXCDRDef.f2453ad, "str_app_name", m1202a());
        int i3 = this.f4749t;
        if (i3 > 0) {
            str = "u32_videotime";
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_dns_time", i3);
        } else {
            str = "u32_videotime";
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_dns_time", -1L);
        }
        int i4 = this.f4748s;
        if (i4 > 0) {
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_tcp_did_connect", i4);
            j = -1;
        } else {
            j = -1;
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_tcp_did_connect", -1L);
        }
        int i5 = this.f4750u;
        if (i5 > 0) {
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_first_video_packet", i5);
        } else {
            TXCDRApi.txSetEventIntValue(m2875d, TXCDRDef.f2453ad, "u32_first_video_packet", j);
        }
        TXCDRApi.nativeReportEvent(m2875d, TXCDRDef.f2453ad);
        StringBuilder sb = new StringBuilder();
        sb.append("report evt 40301: token=");
        sb.append(m2875d);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_timeuse");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4737h);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("str_stream_url");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4732c);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append(str);
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4736g);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("str_device_type");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(TXCSystemUtil.m2877c());
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_network_type");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(TXCSystemUtil.m2876c(this.f4731b));
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("str_user_id");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(TXCSystemUtil.m2890a(this.f4731b));
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("str_package_name");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(TXCSystemUtil.m2880b(this.f4731b));
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("str_app_version");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4743n);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("dev_uuid");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(TXCSystemUtil.m2874d(this.f4731b));
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_first_i_frame");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4738i);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_isp2p");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4739j);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_avg_load");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        int i6 = this.f4740k;
        sb.append(i6 == 0 ? 0 : this.f4741l / i6);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_load_cnt");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4740k);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_max_load");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4742m);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_player_type");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4747r);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_dns_time");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4749t);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_tcp_did_connect");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4748s);
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append("u32_first_video_packet");
        sb.append(SimpleComparison.EQUAL_TO_OPERATION);
        sb.append(this.f4750u);
        TXCLog.m2911w("TXCVodPlayCollection", sb.toString());
    }

    /* renamed from: a */
    public void m1201a(int i) {
        this.f4736g = i;
    }

    /* renamed from: b */
    public void m1198b() {
        this.f4735f = true;
        this.f4733d = System.currentTimeMillis();
    }

    /* renamed from: c */
    public void m1196c() {
        if (this.f4735f) {
            this.f4737h = (int) ((System.currentTimeMillis() - this.f4733d) / 1000);
            m1186m();
            this.f4735f = false;
        }
        this.f4744o = false;
        this.f4745p = false;
    }

    /* renamed from: d */
    public void m1195d() {
        if (this.f4738i != 0 && this.f4745p) {
            int currentTimeMillis = (int) (System.currentTimeMillis() - this.f4734e);
            this.f4741l += currentTimeMillis;
            this.f4740k++;
            if (this.f4742m < currentTimeMillis) {
                this.f4742m = currentTimeMillis;
            }
            this.f4745p = false;
        }
        if (this.f4744o) {
            this.f4744o = false;
        }
    }

    /* renamed from: e */
    public void m1194e() {
        if (this.f4738i == 0) {
            this.f4738i = (int) (System.currentTimeMillis() - this.f4733d);
        }
    }

    /* renamed from: f */
    public void m1193f() {
        if (this.f4748s == 0) {
            this.f4748s = (int) (System.currentTimeMillis() - this.f4733d);
        }
    }

    /* renamed from: g */
    public void m1192g() {
        if (this.f4749t == 0) {
            this.f4749t = (int) (System.currentTimeMillis() - this.f4733d);
        }
    }

    /* renamed from: h */
    public void m1191h() {
        if (this.f4750u == 0) {
            this.f4750u = (int) (System.currentTimeMillis() - this.f4733d);
        }
    }

    /* renamed from: i */
    public void m1190i() {
        this.f4734e = System.currentTimeMillis();
        this.f4745p = true;
    }

    /* renamed from: j */
    public void m1189j() {
        this.f4744o = true;
        this.f4746q++;
        TXCDRApi.txReportDAU(this.f4731b, TXCDRDef.f2504bv);
    }

    /* renamed from: b */
    public void m1197b(int i) {
        this.f4747r = i;
    }

    /* renamed from: a */
    public void m1199a(boolean z) {
        if (z) {
            this.f4751v = 1;
            TXCDRApi.txReportDAU(this.f4731b, TXCDRDef.f2506bx);
            return;
        }
        this.f4751v = 0;
    }

    /* renamed from: k */
    public void m1188k() {
        this.f4753x++;
        TXCDRApi.txReportDAU(this.f4731b, TXCDRDef.f2507by);
    }

    /* renamed from: l */
    public void m1187l() {
        this.f4752w++;
        TXCDRApi.txReportDAU(this.f4731b, TXCDRDef.f2505bw);
    }
}
