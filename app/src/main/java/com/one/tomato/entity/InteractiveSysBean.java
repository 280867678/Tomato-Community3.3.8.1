package com.one.tomato.entity;

import java.util.ArrayList;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.annotation.Table;

/* loaded from: classes3.dex */
public class InteractiveSysBean {
    private ArrayList<SystemNoticeListBean> systemNoticeList;

    public ArrayList<SystemNoticeListBean> getSystemNoticeList() {
        return this.systemNoticeList;
    }

    public void setSystemNoticeList(ArrayList<SystemNoticeListBean> arrayList) {
        this.systemNoticeList = arrayList;
    }

    @Table(name = "SystemNoticeListBean")
    /* loaded from: classes3.dex */
    public static class SystemNoticeListBean {
        @Column(name = "create_time")
        private String create_time;
        @Column(isId = true, name = "ID")

        /* renamed from: id */
        private int f1712id;
        @Column(name = "image_url")
        private String image_url;
        @Column(name = "logo_url")
        private String logo_url;
        @Column(name = "notice_content")
        private String notice_content;
        @Column(name = "notice_id")
        private String notice_id;
        @Column(name = "notice_title")
        private String notice_title;
        @Column(name = "reply")
        private String reply;
        @Column(name = "reply_time")
        private String reply_time;
        @Column(name = "type")
        private String type;
        @Column(name = "userNmae")
        private String userNmae;

        public String getNotice_id() {
            return this.notice_id;
        }

        public void setNotice_id(String str) {
            this.notice_id = str;
        }

        public String getUserNmae() {
            return this.userNmae;
        }

        public void setUserNmae(String str) {
            this.userNmae = str;
        }

        public int getId() {
            return this.f1712id;
        }

        public void setId(int i) {
            this.f1712id = i;
        }

        public String getNotice_title() {
            return this.notice_title;
        }

        public void setNotice_title(String str) {
            this.notice_title = str;
        }

        public String getNotice_content() {
            return this.notice_content;
        }

        public void setNotice_content(String str) {
            this.notice_content = str;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public void setCreate_time(String str) {
            this.create_time = str;
        }

        public String getLogo_url() {
            return this.logo_url;
        }

        public void setLogo_url(String str) {
            this.logo_url = str;
        }

        public String getImage_url() {
            return this.image_url;
        }

        public void setImage_url(String str) {
            this.image_url = str;
        }

        public String getReply_time() {
            return this.reply_time;
        }

        public void setReply_time(String str) {
            this.reply_time = str;
        }

        public String getReply() {
            return this.reply;
        }

        public void setReply(String str) {
            this.reply = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }
    }
}
