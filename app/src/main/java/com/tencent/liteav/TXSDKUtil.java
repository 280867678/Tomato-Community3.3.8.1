package com.tencent.liteav;

import android.content.Context;
import com.tencent.liteav.p103a.TXCBuildsUtil;

/* renamed from: com.tencent.liteav.u */
/* loaded from: classes3.dex */
public class TXSDKUtil {
    /* renamed from: a */
    public static TXIPlayer m622a(Context context, int i) {
        if (i == 2 || i == 4 || i == 4 || i == 6 || i == 3) {
            return new TXCVodPlayer(context);
        }
        return new TXCLivePlayer(context);
    }

    /* renamed from: a */
    public static String m623a() {
        return TXCBuildsUtil.m3480a();
    }
}
