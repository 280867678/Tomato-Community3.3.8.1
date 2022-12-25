package com.alipay.sdk.app;

import com.zzz.ipfssdk.callback.exception.CodeState;

/* renamed from: com.alipay.sdk.app.k */
/* loaded from: classes2.dex */
public enum EnumC0953k {
    SUCCEEDED(9000, "处理成功"),
    FAILED(CodeState.CODES.CODE_PLAYER_PLAY_ERROR, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(4001, "参数错误"),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");
    

    /* renamed from: h */
    private int f944h;

    /* renamed from: i */
    private String f945i;

    EnumC0953k(int i, String str) {
        this.f944h = i;
        this.f945i = str;
    }

    /* renamed from: a */
    public void m4639a(int i) {
        this.f944h = i;
    }

    /* renamed from: a */
    public int m4640a() {
        return this.f944h;
    }

    /* renamed from: a */
    public void m4638a(String str) {
        this.f945i = str;
    }

    /* renamed from: b */
    public String m4637b() {
        return this.f945i;
    }

    /* renamed from: b */
    public static EnumC0953k m4636b(int i) {
        if (i != 4001) {
            if (i == 5000) {
                return DOUBLE_REQUEST;
            }
            if (i == 8000) {
                return PAY_WAITTING;
            }
            if (i == 9000) {
                return SUCCEEDED;
            }
            if (i == 6001) {
                return CANCELED;
            }
            if (i == 6002) {
                return NETWORK_ERROR;
            }
            return FAILED;
        }
        return PARAMS_ERROR;
    }
}
