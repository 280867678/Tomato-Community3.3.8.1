package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class RechargeTypeAndMoney {
    public static final String RECHARGE_AGENT = "delegatePayJC";
    public static final String RECHARGE_ALIPAY = "alipay";
    public static final String RECHARGE_CLOUD_FLASG_PAY = "cloudflashpay";
    public static final String RECHARGE_PTPAY = "Ptpay";
    public static final String RECHARGE_UNION = "unionpay";
    public static final String RECHARGE_WX = "wx";

    /* renamed from: id */
    public int f1730id;
    public int isFix;
    public String payMethod;
    public ArrayList<RechargeMoney> rechargeFaceList;

    /* loaded from: classes3.dex */
    public static class RechargeMoney {
        public int amount;

        /* renamed from: id */
        public int f1731id;
        public String skuId;
        public int tomatoCurrency;
        public int vipDay;
    }
}
