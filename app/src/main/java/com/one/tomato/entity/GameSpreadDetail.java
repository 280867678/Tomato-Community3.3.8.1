package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class GameSpreadDetail {
    public String balance;
    public ArrayList<ServiceList> customerServiceList;
    public ArrayList<GameList> gameList;
    public ArrayList<SpreadList> spreadList;
    public String totalEarn;
    public String yesterdayEarn;

    /* loaded from: classes3.dex */
    public static class GameList {
        public String channelId;
        public String createTime;
        public String desc;

        /* renamed from: id */
        public String f1708id;
        public String link;
        public String logo;
        public String name;
        public String poster;
        public String rate;
        public String thumbnail;
    }

    /* loaded from: classes3.dex */
    public static class ServiceList {
        public String account;
        public String serviceType;
    }

    /* loaded from: classes3.dex */
    public static class SpreadList {
        public String amount;
        public String gameName;
        public String member_id;
        public String phone;
        public String systemId;
        public String userName;
    }
}
