package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class JCOrderRecord {
    public ArrayList<ListBean> data;
    public int page;
    public int resultCode;
    public String resultMsg;
    public int size;
    public long total;

    /* loaded from: classes3.dex */
    public static class ListBean {
        public String agentNickname;
        public long amount;
        public String createTime;

        /* renamed from: id */
        public String f1714id;
        public long payAmount;
        public String skuName;
        public int status;
    }
}
