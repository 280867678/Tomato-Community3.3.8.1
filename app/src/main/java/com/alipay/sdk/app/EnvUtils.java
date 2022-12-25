package com.alipay.sdk.app;

/* loaded from: classes2.dex */
public class EnvUtils {
    private static EnvEnum mEnv = EnvEnum.ONLINE;

    /* loaded from: classes2.dex */
    public enum EnvEnum {
        ONLINE,
        SANDBOX
    }

    public static boolean isSandBox() {
        return mEnv == EnvEnum.SANDBOX;
    }
}
