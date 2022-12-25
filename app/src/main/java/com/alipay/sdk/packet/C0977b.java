package com.alipay.sdk.packet;

import android.text.TextUtils;
import com.alipay.sdk.util.C0996c;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.packet.b */
/* loaded from: classes2.dex */
public final class C0977b {

    /* renamed from: a */
    private final String f995a;

    /* renamed from: b */
    private final String f996b;

    public C0977b(String str, String str2) {
        this.f995a = str;
        this.f996b = str2;
    }

    /* renamed from: a */
    public String m4529a() {
        return this.f995a;
    }

    /* renamed from: b */
    public String m4528b() {
        return this.f996b;
    }

    /* renamed from: c */
    public JSONObject m4527c() {
        if (TextUtils.isEmpty(this.f996b)) {
            return null;
        }
        try {
            return new JSONObject(this.f996b);
        } catch (Exception e) {
            C0996c.m4436a(e);
            return null;
        }
    }

    public String toString() {
        return String.format("<Letter envelop=%s body=%s>", this.f995a, this.f996b);
    }
}
