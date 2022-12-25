package com.one.tomato.entity;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PromotionOrderRecord {
    public String code;
    public int currentPage;
    public ArrayList<C2524Record> data;
    public String description;
    public int pageSize;
    public boolean success;
    public int totalCount;
    public int totalPage;

    /* renamed from: com.one.tomato.entity.PromotionOrderRecord$Record */
    /* loaded from: classes3.dex */
    public static class C2524Record implements Serializable {
        public String accountType;
        public String businessOrderId;
        public String coinAmount;
        public String endTime;
        public String orderAmount;
        public String orderId;
        public int orderStatus;
        public String orderStatusDesc;
        public String orderTime;
        public String walletAccount;
    }
}
