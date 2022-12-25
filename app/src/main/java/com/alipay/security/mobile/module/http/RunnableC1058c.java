package com.alipay.security.mobile.module.http;

import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.tscenter.biz.rpc.report.general.DataReportService;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;

/* renamed from: com.alipay.security.mobile.module.http.c */
/* loaded from: classes2.dex */
class RunnableC1058c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ DataReportRequest f1142a;

    /* renamed from: b */
    final /* synthetic */ C1057b f1143b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1058c(C1057b c1057b, DataReportRequest dataReportRequest) {
        this.f1143b = c1057b;
        this.f1142a = dataReportRequest;
    }

    @Override // java.lang.Runnable
    public void run() {
        DataReportResult dataReportResult;
        DataReportResult dataReportResult2;
        DataReportService dataReportService;
        try {
            dataReportService = this.f1143b.f1141c;
            DataReportResult unused = C1057b.f1138e = dataReportService.reportData(this.f1142a);
        } catch (Throwable th) {
            DataReportResult unused2 = C1057b.f1138e = new DataReportResult();
            dataReportResult = C1057b.f1138e;
            dataReportResult.success = false;
            dataReportResult2 = C1057b.f1138e;
            dataReportResult2.resultCode = "static data rpc upload error, " + C1037a.m4301a(th);
            new StringBuilder("rpc failed:").append(C1037a.m4301a(th));
        }
    }
}
