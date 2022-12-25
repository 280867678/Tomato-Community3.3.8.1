package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PromotionIncomeRecord {
    public String code;
    public int currentPage;
    public ArrayList<C2523Record> data;
    public String description;
    public int pageSize;
    public boolean success;
    public int totalCount;
    public int totalPage;
    public String totalSettlement;

    /* renamed from: com.one.tomato.entity.PromotionIncomeRecord$Record */
    /* loaded from: classes3.dex */
    public static class C2523Record {
        public String date;
        public int level;
        public String telephone;
        public String totalSettlement;
    }
}
