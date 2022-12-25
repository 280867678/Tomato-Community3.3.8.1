package com.youdao.sdk.app.other;

import android.content.Context;
import com.youdao.sdk.app.Auth;
import com.youdao.sdk.app.HttpErrorCode;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.common.YouDaoLog;

/* renamed from: com.youdao.sdk.app.other.b */
/* loaded from: classes4.dex */
public final class C5157b implements HttpHelper.HttpJsonListener {

    /* renamed from: a */
    final /* synthetic */ Context f5894a;

    public C5157b(Context context) {
        this.f5894a = context;
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onResult(String str) {
        Auth.initAuth(str, this.f5894a);
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onError(HttpErrorCode httpErrorCode) {
        YouDaoLog.m165w("check auth error:" + httpErrorCode.name());
    }
}
