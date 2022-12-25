package com.tencent.liteav;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.p128a.Domain;
import com.tencent.liteav.network.p128a.Record;
import com.tencent.liteav.network.p128a.p129a.AndroidDnsServer;
import com.tencent.rtmp.TXLiveConstants;
import com.tomatolive.library.utils.ConstantUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* renamed from: com.tencent.liteav.d */
/* loaded from: classes3.dex */
public class TXCDataReport {

    /* renamed from: a */
    private static String f3419a = "TXCDataReport";

    /* renamed from: w */
    private static HashMap<String, EnumC3415a> f3420w = new HashMap<>();

    /* renamed from: c */
    private String f3422c;

    /* renamed from: d */
    private int f3423d;

    /* renamed from: e */
    private int f3424e;

    /* renamed from: f */
    private int f3425f;

    /* renamed from: g */
    private int f3426g;

    /* renamed from: h */
    private Context f3427h;

    /* renamed from: j */
    private long f3429j;

    /* renamed from: k */
    private int f3430k;

    /* renamed from: l */
    private long f3431l;

    /* renamed from: m */
    private boolean f3432m;

    /* renamed from: n */
    private long f3433n;

    /* renamed from: u */
    private long f3440u;

    /* renamed from: v */
    private String f3441v;

    /* renamed from: p */
    private boolean f3435p = false;

    /* renamed from: q */
    private long f3436q = 0;

    /* renamed from: r */
    private long f3437r = 0;

    /* renamed from: s */
    private long f3438s = 0;

    /* renamed from: t */
    private long f3439t = 0;

    /* renamed from: x */
    private String f3442x = "";

    /* renamed from: b */
    private HashMap f3421b = new HashMap(100);

    /* renamed from: i */
    private String f3428i = TXCCommonUtil.getAppVersion();

    /* renamed from: o */
    private int f3434o = 5000;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCDataReport.java */
    /* renamed from: com.tencent.liteav.d$a */
    /* loaded from: classes3.dex */
    public enum EnumC3415a {
        PENDING,
        CONFIRM,
        NEGATIVE
    }

    public TXCDataReport(Context context) {
        this.f3440u = 0L;
        this.f3427h = context.getApplicationContext();
        this.f3440u = 0L;
    }

    /* renamed from: a */
    public void m2385a() {
        m2358r();
        this.f3431l = -1L;
        this.f3436q = System.currentTimeMillis();
    }

    /* renamed from: b */
    public void m2380b() {
        if (this.f3432m) {
            m2360p();
            return;
        }
        String str = f3419a;
        TXCLog.m2914e(str, "push " + this.f3422c + " failed!");
        m2362n();
    }

    /* renamed from: c */
    public void m2376c() {
        if (this.f3432m) {
            if (this.f3435p) {
                m2357s();
            } else {
                m2365k();
            }
        } else {
            String str = f3419a;
            TXCLog.m2914e(str, "play " + this.f3422c + " failed");
            if (this.f3435p) {
                m2377b(false);
            } else {
                m2366j();
            }
        }
        if (this.f3435p) {
            m2363m();
        }
    }

    /* renamed from: a */
    public void m2381a(boolean z) {
        this.f3435p = z;
    }

    /* renamed from: a */
    public void m2382a(String str) {
        this.f3422c = str;
        m2378b(str);
    }

    /* renamed from: b */
    public void m2378b(String str) {
        if (str == null) {
            return;
        }
        this.f3441v = str;
    }

    /* renamed from: d */
    protected EnumC3415a m2374d() {
        Uri parse;
        try {
            parse = Uri.parse(this.f3441v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (parse == null) {
            return EnumC3415a.PENDING;
        }
        final String host = parse.getHost();
        if (TextUtils.isEmpty(host)) {
            return EnumC3415a.PENDING;
        }
        String scheme = parse.getScheme();
        if (scheme == null) {
            return EnumC3415a.PENDING;
        }
        if (!scheme.equals("rtmp") && !scheme.equals("http") && !scheme.equals("https")) {
            return EnumC3415a.PENDING;
        }
        if (m2375c(host)) {
            return EnumC3415a.CONFIRM;
        }
        Set<String> queryParameterNames = parse.getQueryParameterNames();
        if (queryParameterNames != null && (queryParameterNames.contains("bizid") || queryParameterNames.contains("txTime") || queryParameterNames.contains("txSecret"))) {
            return EnumC3415a.CONFIRM;
        }
        if (f3420w.containsKey(host)) {
            return f3420w.get(host);
        }
        f3420w.put(host, EnumC3415a.PENDING);
        new Thread(new Runnable() { // from class: com.tencent.liteav.d.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Record[] mo1167a = AndroidDnsServer.m1180c().mo1167a(new Domain(host, true), null);
                    int length = mo1167a.length;
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Record record = mo1167a[i];
                        if (record.m1166a() && TXCDataReport.m2375c(record.f4814a)) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                    TXCDataReport.f3420w.put(host, z ? EnumC3415a.CONFIRM : EnumC3415a.NEGATIVE);
                    String str = TXCDataReport.f3419a;
                    TXCLog.m2915d(str, host + " isTencent " + z);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }).start();
        return EnumC3415a.PENDING;
    }

    /* renamed from: c */
    protected static boolean m2375c(String str) {
        if (str == null || !str.contains("myqcloud")) {
            return TXCConfigCenter.m2988a().m2981a(str);
        }
        return true;
    }

    /* renamed from: d */
    public void m2373d(String str) {
        this.f3442x = str;
    }

    /* renamed from: a */
    public void m2383a(int i, int i2) {
        this.f3423d = i;
        this.f3424e = i2;
    }

    /* renamed from: a */
    public void m2384a(int i) {
        this.f3425f = i;
    }

    /* renamed from: b */
    public void m2379b(int i) {
        this.f3426g = i;
    }

    /* renamed from: e */
    public void m2372e() {
        if (!this.f3432m && !TextUtils.isEmpty(TXCStatus.m2905c(this.f3442x, 7012))) {
            m2361o();
            this.f3432m = true;
        }
        if (this.f3433n <= 0) {
            this.f3433n = TXCTimeUtil.getTimeTick();
        }
        if (!this.f3432m || TXCTimeUtil.getTimeTick() - this.f3433n <= 5000) {
            return;
        }
        m2359q();
        this.f3433n = TXCTimeUtil.getTimeTick();
    }

    /* renamed from: f */
    public void m2370f() {
        if (!this.f3432m) {
            long m2906b = TXCStatus.m2906b(this.f3442x, 6001);
            long m2906b2 = TXCStatus.m2906b(this.f3442x, 7104);
            if (m2906b != 0 || m2906b2 != 0) {
                if (this.f3435p) {
                    m2377b(true);
                } else {
                    m2367i();
                }
                this.f3434o = 5000;
                this.f3432m = true;
            }
            String m2905c = TXCStatus.m2905c(this.f3442x, 7119);
            if (m2905c != null) {
                m2378b(m2905c);
            }
        }
        if (this.f3433n <= 0) {
            this.f3433n = TXCTimeUtil.getTimeTick();
        }
        if (!this.f3432m || TXCTimeUtil.getTimeTick() <= this.f3433n + this.f3434o) {
            return;
        }
        if (this.f3435p) {
            m2356t();
            this.f3434o = 5000;
        } else if (m2374d() == EnumC3415a.NEGATIVE) {
            return;
        } else {
            m2364l();
            this.f3434o = TXCDRApi.getStatusReportInterval();
            if (this.f3434o < 5000) {
                this.f3434o = 5000;
            }
            if (this.f3434o > 300000) {
                this.f3434o = 300000;
            }
        }
        this.f3431l = TXCStatus.m2906b(this.f3442x, 6004);
        this.f3433n = TXCTimeUtil.getTimeTick();
    }

    /* renamed from: i */
    private void m2367i() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2417U, TXCDRDef.f2462am, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_network_type", m2371e("u32_network_type"));
        long m2906b = TXCStatus.m2906b(this.f3442x, 7107);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 7108);
        if (m2906b2 != -1) {
            m2906b2 -= m2906b;
        }
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_dns_time", m2906b2);
        String m2905c = TXCStatus.m2905c(this.f3442x, 7110);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "u32_server_ip", m2905c);
        long m2906b3 = TXCStatus.m2906b(this.f3442x, 7109);
        if (m2906b3 != -1) {
            m2906b3 -= m2906b;
        }
        long j = m2906b3;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_connect_server_time", j);
        long j2 = m2906b2;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_stream_begin", -1L);
        this.f3429j = TXCStatus.m2906b(this.f3442x, 6001) - m2906b;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_first_i_frame", this.f3429j);
        long m2906b4 = TXCStatus.m2906b(this.f3442x, 7103) - m2906b;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_first_frame_down", m2906b4);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_isp2p", this.f3430k);
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2417U);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40101: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_device_type")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_dns_time" + SimpleComparison.EQUAL_TO_OPERATION + j2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_server_ip" + SimpleComparison.EQUAL_TO_OPERATION + m2905c + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_connect_server_time" + SimpleComparison.EQUAL_TO_OPERATION + j + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_stream_begin=-1 u32_first_i_frame" + SimpleComparison.EQUAL_TO_OPERATION + this.f3429j + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_first_frame_down" + SimpleComparison.EQUAL_TO_OPERATION + m2906b4 + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_user_id")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_package_name")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("dev_uuid")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_isp2p" + SimpleComparison.EQUAL_TO_OPERATION + this.f3430k);
    }

    /* renamed from: j */
    private void m2366j() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2417U, TXCDRDef.f2462am, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_network_type", m2371e("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_dns_time", -1L);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "u32_server_ip", "");
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_connect_server_time", -1L);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_stream_begin", -1L);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_first_i_frame", -1L);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_first_frame_down", -1L);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2417U, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2417U, "u32_isp2p", this.f3430k);
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2417U);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40101: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_device_type")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_dns_time=-1 u32_server_ip= u32_connect_server_time=-1 u32_stream_begin=-1 u32_first_i_frame=-1 u32_first_frame_down=-1 str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_user_id")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_package_name")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("dev_uuid")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_isp2p" + SimpleComparison.EQUAL_TO_OPERATION + this.f3430k);
    }

    /* renamed from: k */
    private void m2365k() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2419W, TXCDRDef.f2462am, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u64_timestamp", utcTimeTick);
        long timeTick = (TXCTimeUtil.getTimeTick() - TXCStatus.m2906b(this.f3442x, 7107)) / 1000;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_result", timeTick);
        long m2906b = TXCStatus.m2906b(this.f3442x, 6003);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_avg_block_time", m2906b);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2419W, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_isp2p", this.f3430k);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_avg_load", TXCStatus.m2906b(this.f3442x, 2001));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_load_cnt", TXCStatus.m2906b(this.f3442x, 2002));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_max_load", TXCStatus.m2906b(this.f3442x, 2003));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_first_i_frame", this.f3429j);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_speed_cnt", TXCStatus.m2906b(this.f3442x, 2004));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_nodata_cnt", TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_PROGRESS));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_avg_cache_time", TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_LOADING));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2419W, "u32_is_real_time", TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2419W);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40102: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "str_stream_url" + SimpleComparison.EQUAL_TO_OPERATION + this.f3422c + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_result" + SimpleComparison.EQUAL_TO_OPERATION + timeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_avg_block_time" + SimpleComparison.EQUAL_TO_OPERATION + m2906b + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_isp2p" + SimpleComparison.EQUAL_TO_OPERATION + this.f3430k + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_avg_load" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, 2001) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_load_cnt" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, 2002) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_max_load" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, 2003) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_first_i_frame" + SimpleComparison.EQUAL_TO_OPERATION + this.f3429j + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_speed_cnt" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, 2004) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_nodata_cnt" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_avg_cache_time" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_LOADING) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_is_real_time" + SimpleComparison.EQUAL_TO_OPERATION + TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER));
    }

    /* renamed from: l */
    private void m2364l() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2418V, TXCDRDef.f2462am, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_avg_net_speed", TXCStatus.m2904d(this.f3442x, 7102) + TXCStatus.m2904d(this.f3442x, 7101));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_fps", (int) TXCStatus.m2903e(this.f3442x, 6002));
        long m2906b = TXCStatus.m2906b(this.f3442x, 6004);
        long j = this.f3431l;
        if (j == -1) {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_avg_block_count", 0L);
        } else if (m2906b >= j) {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_avg_block_count", m2906b - j);
        } else {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_avg_block_count", 0L);
        }
        this.f3431l = m2906b;
        int[] m2894a = TXCSystemUtil.m2894a();
        int m2881b = TXCSystemUtil.m2881b();
        long m2906b2 = TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_END);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_avg_cache_count", m2906b2);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_cpu_usage", m2894a[1]);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_app_cpu_usage", m2894a[0]);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_app_mem_usage", m2881b);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2418V, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2418V, "u32_isp2p", this.f3430k);
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2418V);
        if (this.f3435p) {
            this.f3439t++;
            this.f3438s += m2906b2;
            if (m2906b2 <= this.f3437r) {
                return;
            }
            this.f3437r = m2906b2;
        }
    }

    /* renamed from: m */
    private void m2363m() {
        HashMap hashMap = new HashMap();
        String m2905c = TXCStatus.m2905c(this.f3442x, 7113);
        String m2905c2 = TXCStatus.m2905c(this.f3442x, 7114);
        String m2905c3 = TXCStatus.m2905c(this.f3442x, 7115);
        int m2904d = TXCStatus.m2904d(this.f3442x, 7105);
        String m2905c4 = TXCStatus.m2905c(this.f3442x, 7106);
        int m2904d2 = TXCStatus.m2904d(this.f3442x, 7111);
        hashMap.put("stream_url", m2905c);
        hashMap.put("stream_id", m2905c2);
        hashMap.put("bizid", m2905c3);
        hashMap.put("err_code", String.valueOf(m2904d));
        hashMap.put("err_info", m2905c4);
        hashMap.put("channel_type", String.valueOf(m2904d2));
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - this.f3436q;
        hashMap.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(this.f3436q)));
        hashMap.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(currentTimeMillis)));
        hashMap.put("total_time", String.valueOf(j));
        long m2906b = TXCStatus.m2906b(this.f3442x, 6003);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 6006);
        long m2906b3 = TXCStatus.m2906b(this.f3442x, 6005);
        long j2 = m2906b != 0 ? m2906b2 / m2906b : 0L;
        hashMap.put("block_count", String.valueOf(m2906b));
        hashMap.put("block_duration_max", String.valueOf(m2906b3));
        hashMap.put("block_duration_avg", String.valueOf(j2));
        long j3 = this.f3439t;
        long j4 = j3 != 0 ? this.f3438s / j3 : 0L;
        hashMap.put("jitter_cache_max", String.valueOf(this.f3437r));
        hashMap.put("jitter_cache_avg", String.valueOf(j4));
        String txCreateToken = TXCDRApi.txCreateToken();
        int i = TXCDRDef.f2455af;
        int i2 = TXCDRDef.f2461al;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.command_id_comment = "LINKMIC";
        TXCDRApi.InitEvent(this.f3427h, txCreateToken, i, i2, tXCDRExtInfo);
        String str = f3419a;
        TXCLog.m2915d(str, "report evt 40402: token=" + txCreateToken);
        for (Map.Entry entry : hashMap.entrySet()) {
            String str2 = (String) entry.getKey();
            String str3 = (String) entry.getValue();
            String str4 = f3419a;
            TXCLog.m2914e(str4, "RealTimePlayStatisticInfo: " + str2 + " = " + str3);
            if (str2 != null && str2.length() > 0 && str3 != null) {
                TXCDRApi.txSetEventValue(txCreateToken, i, str2, str3);
            }
        }
        TXCDRApi.nativeReportEvent(txCreateToken, i);
        this.f3435p = false;
        this.f3436q = 0L;
        this.f3439t = 0L;
        this.f3438s = 0L;
        this.f3437r = 0L;
    }

    /* renamed from: n */
    private void m2362n() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.f3422c;
        long m2906b = TXCStatus.m2906b(this.f3442x, 7013);
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2412P, TXCDRDef.f2461al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u64_timestamp", TXCTimeUtil.getUtcTimeTick());
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_network_type", m2371e("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_dns_time", -1L);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_connect_server_time", -1L);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "u32_server_ip", "");
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_video_resolution", (this.f3423d << 16) | this.f3424e);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_audio_samplerate", this.f3426g);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_video_bitrate", this.f3425f);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_channel_type", m2906b);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2412P);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40001: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "str_stream_url" + SimpleComparison.EQUAL_TO_OPERATION + this.f3422c + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_device_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_dns_time=-1 u32_connect_server_time=-1 u32_server_ip= u32_video_resolution" + SimpleComparison.EQUAL_TO_OPERATION + ((this.f3423d << 16) | this.f3424e) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_audio_samplerate" + SimpleComparison.EQUAL_TO_OPERATION + this.f3426g + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_video_bitrate" + SimpleComparison.EQUAL_TO_OPERATION + this.f3425f + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_user_id") + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_package_name") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_channel_type" + SimpleComparison.EQUAL_TO_OPERATION + m2906b + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("dev_uuid") + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_max_load"));
    }

    /* renamed from: o */
    private void m2361o() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.f3422c;
        String m2905c = TXCStatus.m2905c(this.f3442x, 7012);
        long m2906b = TXCStatus.m2906b(this.f3442x, 7009);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 7010);
        if (m2906b2 != -1) {
            m2906b2 -= m2906b;
        }
        long m2906b3 = TXCStatus.m2906b(this.f3442x, 7011);
        if (m2906b3 != -1) {
            m2906b3 -= m2906b;
        }
        long m2906b4 = TXCStatus.m2906b(this.f3442x, 7013);
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2412P, TXCDRDef.f2461al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_network_type", m2371e("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_dns_time", m2906b2);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_connect_server_time", m2906b3);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "u32_server_ip", m2905c);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_video_resolution", this.f3424e | (this.f3423d << 16));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_audio_samplerate", this.f3426g);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_video_bitrate", this.f3425f);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2412P, "u32_channel_type", m2906b4);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2412P, "str_nearest_ip_list", TXCStatus.m2905c(this.f3442x, 7019));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2412P);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40001: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "str_stream_url" + SimpleComparison.EQUAL_TO_OPERATION + this.f3422c + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_device_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_dns_time" + SimpleComparison.EQUAL_TO_OPERATION + m2906b2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_connect_server_time" + SimpleComparison.EQUAL_TO_OPERATION + m2906b3 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_server_ip" + SimpleComparison.EQUAL_TO_OPERATION + m2905c + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_video_resolution" + SimpleComparison.EQUAL_TO_OPERATION + ((this.f3423d << 16) | this.f3424e) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_audio_samplerate" + SimpleComparison.EQUAL_TO_OPERATION + this.f3426g + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_video_bitrate" + SimpleComparison.EQUAL_TO_OPERATION + this.f3425f + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_user_id") + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_package_name") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_channel_type" + SimpleComparison.EQUAL_TO_OPERATION + m2906b4 + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("dev_uuid") + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_max_load"));
    }

    /* renamed from: p */
    private void m2360p() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.f3422c;
        long m2906b = TXCStatus.m2906b(this.f3442x, 7009);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 7013);
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2414R, TXCDRDef.f2461al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2414R, "u64_timestamp", utcTimeTick);
        long timeTick = (TXCTimeUtil.getTimeTick() - m2906b) / 1000;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2414R, "u32_result", timeTick);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2414R, "u32_channel_type", m2906b2);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "u32_ip_count_quic", String.valueOf(TXCStatus.m2904d(this.f3442x, 7016)));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "u32_connect_count_quic", String.valueOf(TXCStatus.m2904d(this.f3442x, 7017)));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2414R, "u32_connect_count_tcp", String.valueOf(TXCStatus.m2904d(this.f3442x, 7018)));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2414R);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40002: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "str_stream_url" + SimpleComparison.EQUAL_TO_OPERATION + this.f3422c + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_result" + SimpleComparison.EQUAL_TO_OPERATION + timeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_user_id") + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("str_package_name") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_channel_type" + SimpleComparison.EQUAL_TO_OPERATION + m2906b2 + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + this.f3421b.get("dev_uuid"));
    }

    /* renamed from: q */
    private void m2359q() {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        tXCDRExtInfo.url = this.f3422c;
        int[] m2894a = TXCSystemUtil.m2894a();
        int i = m2894a[0];
        int i2 = m2894a[1];
        int m2881b = TXCSystemUtil.m2881b();
        long m2906b = TXCStatus.m2906b(this.f3442x, 7013);
        int m2904d = TXCStatus.m2904d(this.f3442x, 7004);
        int m2904d2 = TXCStatus.m2904d(this.f3442x, 7003);
        double m2903e = TXCStatus.m2903e(this.f3442x, 4001);
        int m2904d3 = TXCStatus.m2904d(this.f3442x, 7005);
        int m2904d4 = TXCStatus.m2904d(this.f3442x, 7002);
        int m2904d5 = TXCStatus.m2904d(this.f3442x, 7001);
        int m2904d6 = TXCStatus.m2904d(this.f3442x, 4004);
        String m2905c = TXCStatus.m2905c(this.f3442x, 7012);
        String m2905c2 = TXCStatus.m2905c(this.f3442x, 7014);
        String m2905c3 = TXCStatus.m2905c(this.f3442x, 7015);
        String m2905c4 = TXCStatus.m2905c(this.f3442x, 3001);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 3002);
        double m2903e2 = TXCStatus.m2903e(this.f3442x, 3003);
        int m2904d7 = TXCStatus.m2904d(this.f3442x, 7020);
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2413Q, TXCDRDef.f2461al, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_avg_audio_bitrate", m2904d4);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_avg_video_bitrate", m2904d5);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_avg_net_speed", m2904d2 + m2904d);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_fps", (int) m2903e);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_avg_cache_size", m2904d3);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_cpu_usage", i2);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_app_cpu_usage", i);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_app_mem_usage", m2881b);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_channel_type", m2906b);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_hw_enc", m2904d6);
        try {
            if (this.f3427h != null && TXCSystemUtil.m2876c(this.f3427h) == 1 && (wifiManager = (WifiManager) this.f3427h.getSystemService(AopConstants.WIFI)) != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_wifi_ssid", connectionInfo.getSSID());
                TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "str_wifi_signal_strength", WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 32));
                TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "str_wifi_link_speed", connectionInfo.getLinkSpeed());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_server_ip", m2905c);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_quic_connection_id", m2905c2);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_quic_connection_stats", m2905c3);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2413Q, "str_beauty_stats", m2905c4);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_send_strategy", m2904d7);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_preprocess_timecost", m2906b2);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2413Q, "u32_preprocess_fps_out", (int) m2903e2);
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2413Q);
    }

    /* renamed from: e */
    private int m2371e(String str) {
        Number number = (Number) this.f3421b.get(str);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    /* renamed from: r */
    private void m2358r() {
        this.f3432m = false;
        this.f3433n = 0L;
        this.f3421b.put("str_user_id", TXCSystemUtil.m2890a(this.f3427h));
        this.f3421b.put("str_device_type", TXCSystemUtil.m2877c());
        this.f3421b.put("str_device_type", TXCSystemUtil.m2877c());
        this.f3421b.put("u32_network_type", Integer.valueOf(TXCSystemUtil.m2876c(this.f3427h)));
        this.f3421b.put("token", TXCSystemUtil.m2875d());
        this.f3421b.put("str_package_name", TXCSystemUtil.m2880b(this.f3427h));
        this.f3421b.put("dev_uuid", TXCSystemUtil.m2874d(this.f3427h));
    }

    /* renamed from: b */
    private void m2377b(boolean z) {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2420X, TXCDRDef.f2462am, tXCDRExtInfo);
        this.f3440u = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u64_timestamp", String.valueOf(this.f3440u));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_network_type", m2371e("u32_network_type"));
        long m2906b = TXCStatus.m2906b(this.f3442x, 7107);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 7108);
        if (m2906b2 != -1) {
            m2906b2 -= m2906b;
        }
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_dns_time", z ? m2906b2 : -1L);
        String m2905c = TXCStatus.m2905c(this.f3442x, 7110);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u32_server_ip", z ? m2905c : "");
        long m2906b3 = TXCStatus.m2906b(this.f3442x, 7109);
        if (m2906b3 != -1) {
            m2906b3 -= m2906b;
        }
        long j = m2906b3;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_connect_server_time", z ? j : -1L);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_stream_begin", -1L);
        this.f3429j = TXCStatus.m2906b(this.f3442x, 6001) - m2906b;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_first_i_frame", this.f3429j);
        long m2906b4 = TXCStatus.m2906b(this.f3442x, 7103) - m2906b;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2420X, "u32_first_frame_down", m2906b4);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        int m2904d = TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u32_max_cache_time", String.valueOf(m2904d));
        int m2904d2 = TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_GET_MESSAGE);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u32_min_cache_time", String.valueOf(m2904d2));
        int m2904d3 = TXCStatus.m2904d(this.f3442x, 7105);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u64_err_code", String.valueOf(m2904d3));
        String m2905c2 = TXCStatus.m2905c(this.f3442x, 7106);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "str_err_info", m2905c2);
        int m2904d4 = TXCStatus.m2904d(this.f3442x, 7112);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u32_link_type", String.valueOf(m2904d4));
        int m2904d5 = TXCStatus.m2904d(this.f3442x, 7111);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2420X, "u32_channel_type", String.valueOf(m2904d5));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2420X);
        String str2 = f3419a;
        TXCLog.m2915d(str2, "report evt 40501: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + this.f3440u + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_device_type")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_dns_time" + SimpleComparison.EQUAL_TO_OPERATION + m2906b2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_server_ip" + SimpleComparison.EQUAL_TO_OPERATION + m2905c + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_connect_server_time" + SimpleComparison.EQUAL_TO_OPERATION + j + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_stream_begin=-1 u32_first_i_frame" + SimpleComparison.EQUAL_TO_OPERATION + this.f3429j + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_first_frame_down" + SimpleComparison.EQUAL_TO_OPERATION + m2906b4 + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_user_id")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_package_name")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("dev_uuid")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_max_cache_time" + SimpleComparison.EQUAL_TO_OPERATION + m2904d + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_min_cache_time" + SimpleComparison.EQUAL_TO_OPERATION + m2904d2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_err_code" + SimpleComparison.EQUAL_TO_OPERATION + m2904d3 + ConstantUtils.PLACEHOLDER_STR_ONE + "str_err_info" + SimpleComparison.EQUAL_TO_OPERATION + m2905c2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_link_type" + SimpleComparison.EQUAL_TO_OPERATION + m2904d4 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_channel_type" + SimpleComparison.EQUAL_TO_OPERATION + m2904d5);
    }

    /* renamed from: s */
    private void m2357s() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2422Z, TXCDRDef.f2462am, tXCDRExtInfo);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u64_begin_timestamp", String.valueOf(this.f3440u));
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_end_timestamp", utcTimeTick);
        long j = (utcTimeTick - this.f3440u) / 1000;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_playtime", j);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "str_device_type", (String) this.f3421b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u32_network_type", m2371e("u32_network_type"));
        String m2905c = TXCStatus.m2905c(this.f3442x, 7110);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_server_ip", m2905c);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "str_user_id", (String) this.f3421b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "str_package_name", (String) this.f3421b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "dev_uuid", (String) this.f3421b.get("dev_uuid"));
        long m2906b = TXCStatus.m2906b(this.f3442x, 6003);
        long m2906b2 = TXCStatus.m2906b(this.f3442x, 6005);
        long m2906b3 = TXCStatus.m2906b(this.f3442x, 6006);
        long j2 = 0;
        if (m2906b > 0) {
            j2 = m2906b3 / m2906b;
        }
        long j3 = j2;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_block_count", m2906b);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_block_duration_max", m2906b2);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_block_duration_avg", j3);
        long m2906b4 = TXCStatus.m2906b(this.f3442x, 6009);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_jitter_cache_max", m2906b4);
        long m2906b5 = TXCStatus.m2906b(this.f3442x, 6008);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_jitter_cache_avg", m2906b5);
        long m2906b6 = TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_LOADING);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2422Z, "u64_audio_cache_avg", m2906b6);
        int m2904d = TXCStatus.m2904d(this.f3442x, 7112);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_link_type", String.valueOf(m2904d));
        long m2904d2 = TXCStatus.m2904d(this.f3442x, 2001);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_avg_load", String.valueOf(m2904d2));
        long m2904d3 = TXCStatus.m2904d(this.f3442x, 2002);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_load_cnt", String.valueOf(m2904d3));
        long m2904d4 = TXCStatus.m2904d(this.f3442x, 2003);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_max_load", String.valueOf(m2904d4));
        int m2904d5 = TXCStatus.m2904d(this.f3442x, 7111);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_channel_type", String.valueOf(m2904d5));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_ip_count_quic", String.valueOf(TXCStatus.m2904d(this.f3442x, 7116)));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_connect_count_quic", String.valueOf(TXCStatus.m2904d(this.f3442x, 7117)));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2422Z, "u32_connect_count_tcp", String.valueOf(TXCStatus.m2904d(this.f3442x, 7118)));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2422Z);
        TXCLog.m2915d(f3419a, "report evt 40502: token=" + str + ConstantUtils.PLACEHOLDER_STR_ONE + "str_stream_url" + SimpleComparison.EQUAL_TO_OPERATION + this.f3422c + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_begin_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + this.f3440u + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_end_timestamp" + SimpleComparison.EQUAL_TO_OPERATION + utcTimeTick + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_playtime" + SimpleComparison.EQUAL_TO_OPERATION + j + ConstantUtils.PLACEHOLDER_STR_ONE + "str_device_type" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_device_type")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_network_type" + SimpleComparison.EQUAL_TO_OPERATION + m2371e("u32_network_type") + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_server_ip" + SimpleComparison.EQUAL_TO_OPERATION + m2905c + ConstantUtils.PLACEHOLDER_STR_ONE + "str_user_id" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_user_id")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_package_name" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("str_package_name")) + ConstantUtils.PLACEHOLDER_STR_ONE + "str_app_version" + SimpleComparison.EQUAL_TO_OPERATION + this.f3428i + ConstantUtils.PLACEHOLDER_STR_ONE + "dev_uuid" + SimpleComparison.EQUAL_TO_OPERATION + ((String) this.f3421b.get("dev_uuid")) + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_block_count" + SimpleComparison.EQUAL_TO_OPERATION + m2906b + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_block_duration_max" + SimpleComparison.EQUAL_TO_OPERATION + m2906b2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_block_duration_avg" + SimpleComparison.EQUAL_TO_OPERATION + j3 + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_jitter_cache_max" + SimpleComparison.EQUAL_TO_OPERATION + m2906b4 + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_jitter_cache_avg" + SimpleComparison.EQUAL_TO_OPERATION + m2906b5 + ConstantUtils.PLACEHOLDER_STR_ONE + "u64_audio_cache_avg" + SimpleComparison.EQUAL_TO_OPERATION + m2906b6 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_link_type" + SimpleComparison.EQUAL_TO_OPERATION + m2904d + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_avg_load" + SimpleComparison.EQUAL_TO_OPERATION + m2904d2 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_load_cnt" + SimpleComparison.EQUAL_TO_OPERATION + m2904d3 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_max_load" + SimpleComparison.EQUAL_TO_OPERATION + m2904d4 + ConstantUtils.PLACEHOLDER_STR_ONE + "u32_channel_type" + SimpleComparison.EQUAL_TO_OPERATION + m2904d5);
    }

    /* renamed from: t */
    private void m2356t() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f3422c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        String str = (String) this.f3421b.get("token");
        TXCDRApi.InitEvent(this.f3427h, str, TXCDRDef.f2421Y, TXCDRDef.f2462am, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_net_speed", TXCStatus.m2904d(this.f3442x, 7102) + TXCStatus.m2904d(this.f3442x, 7101));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_fps", (int) TXCStatus.m2903e(this.f3442x, 6002));
        long m2906b = TXCStatus.m2906b(this.f3442x, 6004);
        long j = this.f3431l;
        if (j == -1) {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_block_count", 0L);
        } else if (m2906b >= j) {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_block_count", m2906b - j);
        } else {
            TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_block_count", 0L);
        }
        this.f3431l = m2906b;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_cache_count", TXCStatus.m2906b(this.f3442x, TXLiveConstants.PLAY_EVT_PLAY_END));
        int[] m2894a = TXCSystemUtil.m2894a();
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_cpu_usage", m2894a[1]);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_app_cpu_usage", m2894a[0]);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_app_mem_usage", TXCSystemUtil.m2881b());
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2421Y, "str_app_version", this.f3428i);
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2421Y, "str_device_type", (String) this.f3421b.get("str_device_type"));
        int i = 2;
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_decode_type", TXCStatus.m2904d(this.f3442x, 5002) == 0 ? 2 : 1);
        if (TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC) != 0) {
            i = 1;
        }
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_audio_decode_type", i);
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_network_type", m2371e("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_video_cache_time", TXCStatus.m2904d(this.f3442x, 6007));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_audio_cache_time", TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_audio_jitter", TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_CHANGE_ROTATION));
        TXCDRApi.txSetEventIntValue(str, TXCDRDef.f2421Y, "u32_audio_drop", TXCStatus.m2904d(this.f3442x, TXLiveConstants.PLAY_EVT_VOD_LOADING_END));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2421Y, "u64_playtime", String.valueOf((TXCTimeUtil.getUtcTimeTick() - this.f3440u) / 1000));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2421Y, "u32_link_type", String.valueOf(TXCStatus.m2904d(this.f3442x, 7112)));
        TXCDRApi.txSetEventValue(str, TXCDRDef.f2421Y, "u32_channel_type", String.valueOf(TXCStatus.m2904d(this.f3442x, 7111)));
        TXCDRApi.nativeReportEvent(str, TXCDRDef.f2421Y);
    }
}
