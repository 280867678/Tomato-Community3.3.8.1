package com.alipay.security.mobile.module.http;

import android.content.Context;
import com.alipay.android.phone.mrpc.core.AbstractC0913w;
import com.alipay.android.phone.mrpc.core.C0885aa;
import com.alipay.android.phone.mrpc.core.C0897h;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.tscenter.biz.rpc.deviceFp.BugTrackMessageService;
import com.alipay.tscenter.biz.rpc.report.general.DataReportService;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import org.json.JSONObject;

/* renamed from: com.alipay.security.mobile.module.http.b */
/* loaded from: classes2.dex */
public class C1057b implements AbstractC1056a {

    /* renamed from: d */
    private static C1057b f1137d;

    /* renamed from: e */
    private static DataReportResult f1138e;

    /* renamed from: a */
    private AbstractC0913w f1139a;

    /* renamed from: b */
    private BugTrackMessageService f1140b;

    /* renamed from: c */
    private DataReportService f1141c;

    private C1057b(Context context, String str) {
        this.f1139a = null;
        this.f1140b = null;
        this.f1141c = null;
        C0885aa c0885aa = new C0885aa();
        c0885aa.m4868a(str);
        this.f1139a = new C0897h(context);
        this.f1140b = (BugTrackMessageService) this.f1139a.mo4797a(BugTrackMessageService.class, c0885aa);
        this.f1141c = (DataReportService) this.f1139a.mo4797a(DataReportService.class, c0885aa);
    }

    /* renamed from: a */
    public static synchronized C1057b m4201a(Context context, String str) {
        C1057b c1057b;
        synchronized (C1057b.class) {
            if (f1137d == null) {
                f1137d = new C1057b(context, str);
            }
            c1057b = f1137d;
        }
        return c1057b;
    }

    @Override // com.alipay.security.mobile.module.http.AbstractC1056a
    /* renamed from: a */
    public DataReportResult mo4199a(DataReportRequest dataReportRequest) {
        if (dataReportRequest == null) {
            return null;
        }
        if (this.f1141c != null) {
            f1138e = null;
            new Thread(new RunnableC1058c(this, dataReportRequest)).start();
            for (int i = 300000; f1138e == null && i >= 0; i -= 50) {
                Thread.sleep(50L);
            }
        }
        return f1138e;
    }

    @Override // com.alipay.security.mobile.module.http.AbstractC1056a
    /* renamed from: a */
    public boolean mo4197a(String str) {
        BugTrackMessageService bugTrackMessageService;
        if (!C1037a.m4303a(str) && (bugTrackMessageService = this.f1140b) != null) {
            String str2 = null;
            try {
                str2 = bugTrackMessageService.logCollect(C1037a.m4294f(str));
            } catch (Throwable unused) {
            }
            if (C1037a.m4303a(str2)) {
                return false;
            }
            return ((Boolean) new JSONObject(str2).get("success")).booleanValue();
        }
        return false;
    }
}
