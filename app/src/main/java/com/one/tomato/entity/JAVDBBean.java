package com.one.tomato.entity;

import java.util.List;

/* loaded from: classes3.dex */
public class JAVDBBean {
    private List<ActorsBean> actors;
    private String official_url;

    public String getOfficial_url() {
        return this.official_url;
    }

    public void setOfficial_url(String str) {
        this.official_url = str;
    }

    public List<ActorsBean> getActors() {
        return this.actors;
    }

    public void setActors(List<ActorsBean> list) {
        this.actors = list;
    }

    /* loaded from: classes3.dex */
    public static class ActorsBean {
        private String avatar_url;

        /* renamed from: id */
        private String f1713id;
        private String name;
        private Object other_name;
        private int percentage;
        private String type;
        private String url;

        public String getId() {
            return this.f1713id;
        }

        public void setId(String str) {
            this.f1713id = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public Object getOther_name() {
            return this.other_name;
        }

        public void setOther_name(Object obj) {
            this.other_name = obj;
        }

        public String getAvatar_url() {
            return this.avatar_url;
        }

        public void setAvatar_url(String str) {
            this.avatar_url = str;
        }

        public int getPercentage() {
            return this.percentage;
        }

        public void setPercentage(int i) {
            this.percentage = i;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String str) {
            this.url = str;
        }
    }
}
