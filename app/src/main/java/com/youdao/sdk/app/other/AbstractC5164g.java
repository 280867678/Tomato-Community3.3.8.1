package com.youdao.sdk.app.other;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.youdao.sdk.app.other.g */
/* loaded from: classes4.dex */
public abstract class AbstractC5164g {
    protected Map<String, String> parameters = new HashMap();

    /* JADX INFO: Access modifiers changed from: protected */
    public void addParam(String str, String str2) {
        if (str2 == null || C5176y.m171a(str2)) {
            return;
        }
        this.parameters.put(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setAppVersion(String str) {
        addParam("av", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDeviceInfo(String... strArr) {
        StringBuilder sb = new StringBuilder();
        if (strArr == null || strArr.length < 1) {
            return;
        }
        for (int i = 0; i < strArr.length - 1; i++) {
            sb.append(strArr[i]);
            sb.append(",");
        }
        sb.append(strArr[strArr.length - 1]);
        addParam("dn", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setUdid(String str) {
        addParam("udid", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setAUid(String str) {
        addParam("auid", str);
    }
}
