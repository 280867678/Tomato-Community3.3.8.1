package com.tencent.liteav.network.p128a;

import java.io.IOException;

/* renamed from: com.tencent.liteav.network.a.a */
/* loaded from: classes3.dex */
public class DnsException extends IOException {
    public DnsException(String str, String str2) {
        super(str + ": " + str2);
    }
}
