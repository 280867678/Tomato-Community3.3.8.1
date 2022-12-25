package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class BaseModel implements Serializable {
    public int code;
    public String data;
    public String message;
    public Object obj;
    public Page page;
    public String result;
    public String url;

    public BaseModel(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public BaseModel() {
    }

    /* loaded from: classes3.dex */
    public static class Page {
        private boolean firstPage;
        private boolean lastPage;
        private Object list;
        private int newDataCount;
        private int pageNo;
        private int pageSize;
        private int startRow;
        private int totalCount;
        private int totalPage;

        public Object getList() {
            return this.list;
        }

        public void setList(Object obj) {
            this.list = obj;
        }

        public int getPageNo() {
            return this.pageNo;
        }

        public void setPageNo(int i) {
            this.pageNo = i;
        }

        public int getPageSize() {
            return this.pageSize;
        }

        public void setPageSize(int i) {
            this.pageSize = i;
        }

        public int getTotalPage() {
            return this.totalPage;
        }

        public void setTotalPage(int i) {
            this.totalPage = i;
        }

        public int getTotalCount() {
            return this.totalCount;
        }

        public void setTotalCount(int i) {
            this.totalCount = i;
        }

        public int getStartRow() {
            return this.startRow;
        }

        public void setStartRow(int i) {
            this.startRow = i;
        }

        public boolean isLastPage() {
            return this.lastPage;
        }

        public void setLastPage(boolean z) {
            this.lastPage = z;
        }

        public boolean isFirstPage() {
            return this.firstPage;
        }

        public void setFirstPage(boolean z) {
            this.firstPage = z;
        }

        public int getNewDataCount() {
            return this.newDataCount;
        }

        public void setNewDataCount(int i) {
            this.newDataCount = i;
        }
    }
}
