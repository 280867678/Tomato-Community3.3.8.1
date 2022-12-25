package com.alipay.security.mobile.module.http.model;

import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.security.mobile.module.http.model.b */
/* loaded from: classes2.dex */
public class C1061b {
    /* renamed from: a */
    public static C1062c m4192a(DataReportResult dataReportResult) {
        C1062c c1062c = new C1062c();
        if (dataReportResult == null) {
            return null;
        }
        c1062c.f1144a = dataReportResult.success;
        c1062c.f1145b = dataReportResult.resultCode;
        Map<String, String> map = dataReportResult.resultData;
        if (map != null) {
            c1062c.f1146h = map.get("apdid");
            c1062c.f1147i = map.get("apdidToken");
            c1062c.f1150l = map.get("dynamicKey");
            c1062c.f1151m = map.get("timeInterval");
            c1062c.f1152n = map.get("webrtcUrl");
            c1062c.f1153o = "";
            String str = map.get("drmSwitch");
            if (C1037a.m4299b(str)) {
                if (str.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str.charAt(0));
                    c1062c.f1148j = sb.toString();
                }
                if (str.length() >= 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str.charAt(2));
                    c1062c.f1149k = sb2.toString();
                }
            }
            if (map.containsKey("apse_degrade")) {
                c1062c.f1154p = map.get("apse_degrade");
            }
        }
        return c1062c;
    }

    /* renamed from: a */
    public static DataReportRequest m4193a(C1063d c1063d) {
        DataReportRequest dataReportRequest = new DataReportRequest();
        if (c1063d == null) {
            return null;
        }
        dataReportRequest.f1164os = c1063d.f1155a;
        dataReportRequest.rpcVersion = c1063d.f1161j;
        dataReportRequest.bizType = "1";
        dataReportRequest.bizData = new HashMap();
        dataReportRequest.bizData.put("apdid", c1063d.f1156b);
        dataReportRequest.bizData.put("apdidToken", c1063d.f1157c);
        dataReportRequest.bizData.put("umidToken", c1063d.f1158d);
        dataReportRequest.bizData.put("dynamicKey", c1063d.f1159e);
        dataReportRequest.deviceData = c1063d.f1160f;
        return dataReportRequest;
    }
}
