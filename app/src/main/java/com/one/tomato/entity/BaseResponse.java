package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class BaseResponse<T> {
    private int code;
    private T data;
    private String message;
    public Page page;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public boolean isOk() {
        return this.code == 0;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
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
