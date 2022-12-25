package com.one.tomato.net;

import com.one.tomato.utils.AppSecretUtil;
import com.tomatolive.library.utils.ConstantUtils;
import org.xutils.C5540x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

@HttpRequest(builder = TomatoParamsBuilder.class, host = "http://www.fanqie345.com", path = "xxx")
/* loaded from: classes3.dex */
public class TomatoParams extends RequestParams {
    private String path;

    public TomatoParams(String str) {
        super(str);
        setReadTimeout(ConstantUtils.MAX_ITEM_NUM);
        setConnectTimeout(ConstantUtils.MAX_ITEM_NUM);
    }

    public TomatoParams(String str, String str2) {
        this(str, str2, 10);
    }

    public TomatoParams(String str, String str2, int i) {
        super(str + AppSecretUtil.encodePath(str2));
        this.path = str2;
        int i2 = i * 1000;
        setReadTimeout(i2);
        setConnectTimeout(i2);
    }

    public <T> Callback.Cancelable post(TomatoCallback tomatoCallback) {
        try {
            init();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return C5540x.http().post(this, tomatoCallback);
    }

    public <T> Callback.Cancelable get(TomatoCallback tomatoCallback) {
        try {
            init();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return C5540x.http().get(this, tomatoCallback);
    }

    public String getSourcePath() {
        return this.path;
    }

    @Override // org.xutils.http.RequestParams, org.xutils.http.BaseParams
    public String toString() {
        return super.toString();
    }
}
