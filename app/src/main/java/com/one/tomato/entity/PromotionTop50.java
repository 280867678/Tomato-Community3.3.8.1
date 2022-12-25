package com.one.tomato.entity;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PromotionTop50 {
    public String code;
    public int currentPage;
    public ArrayList<C2525Record> data;
    public String description;
    public int pageSize;
    public boolean success;
    public int totalCount;
    public int totalPage;

    /* renamed from: com.one.tomato.entity.PromotionTop50$Record */
    /* loaded from: classes3.dex */
    public static class C2525Record implements Serializable {

        /* renamed from: id */
        public String f1723id;
        public String money;
        public String telephone;
    }
}
