package com.tencent.ugc;

import android.content.Context;
import com.tencent.liteav.basic.p107c.LicenceCheck;
import com.tencent.liteav.basic.p107c.LicenceInfo;

/* loaded from: classes3.dex */
public class TXUGCBase {
    private static TXUGCBase sInstance;
    private LicenceCheck mUGCLicenseNewCheck;

    public static TXUGCBase getInstance() {
        if (sInstance == null) {
            synchronized (TXUGCBase.class) {
                if (sInstance == null) {
                    sInstance = new TXUGCBase();
                }
            }
        }
        return sInstance;
    }

    private TXUGCBase() {
    }

    public void setLicence(Context context, String str, String str2) {
        this.mUGCLicenseNewCheck = LicenceCheck.m3120a();
        this.mUGCLicenseNewCheck.m3117a(context, str, str2);
    }

    public String getLicenceInfo(Context context) {
        LicenceInfo licenceInfo = new LicenceInfo();
        LicenceCheck.m3120a().m3112a(licenceInfo, context);
        return licenceInfo.f2395a;
    }
}
