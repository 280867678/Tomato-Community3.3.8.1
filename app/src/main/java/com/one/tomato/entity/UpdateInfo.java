package com.one.tomato.entity;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class UpdateInfo implements Serializable {
    private int status;
    private ArrayList<VersionListBean> versionList;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public ArrayList<VersionListBean> getVersionList() {
        return this.versionList;
    }

    public void setVersionList(ArrayList<VersionListBean> arrayList) {
        this.versionList = arrayList;
    }

    /* loaded from: classes3.dex */
    public static class VersionListBean {
        private String changId;
        private String createTime;
        private String description;
        private Object endDate;
        private Object flag;

        /* renamed from: id */
        private int f1742id;
        private String isUpdate;
        private int level;
        private int mobileType;
        private String numberId;
        private int size;
        private Object startDate;
        private String status;
        private int type;
        private String updateTime;
        private String url;
        private String versionId;

        public int getId() {
            return this.f1742id;
        }

        public void setId(int i) {
            this.f1742id = i;
        }

        public String getNumberId() {
            return this.numberId;
        }

        public void setNumberId(String str) {
            this.numberId = str;
        }

        public String getVersionId() {
            return this.versionId;
        }

        public void setVersionId(String str) {
            this.versionId = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public Object getFlag() {
            return this.flag;
        }

        public void setFlag(Object obj) {
            this.flag = obj;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public Object getStartDate() {
            return this.startDate;
        }

        public void setStartDate(Object obj) {
            this.startDate = obj;
        }

        public Object getEndDate() {
            return this.endDate;
        }

        public void setEndDate(Object obj) {
            this.endDate = obj;
        }

        public String getIsUpdate() {
            return this.isUpdate;
        }

        public void setIsUpdate(String str) {
            this.isUpdate = str;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public int getMobileType() {
            return this.mobileType;
        }

        public void setMobileType(int i) {
            this.mobileType = i;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int i) {
            this.size = i;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String str) {
            this.createTime = str;
        }

        public String getUpdateTime() {
            return this.updateTime;
        }

        public void setUpdateTime(String str) {
            this.updateTime = str;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(int i) {
            this.level = i;
        }

        public String getChangId() {
            return this.changId;
        }

        public void setChangId(String str) {
            this.changId = str;
        }
    }
}
