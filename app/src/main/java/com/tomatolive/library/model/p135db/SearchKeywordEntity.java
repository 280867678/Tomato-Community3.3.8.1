package com.tomatolive.library.model.p135db;

/* renamed from: com.tomatolive.library.model.db.SearchKeywordEntity */
/* loaded from: classes3.dex */
public class SearchKeywordEntity extends BaseDBEntity {
    private String dataId;
    private long insertTime;
    private String keyword;

    public SearchKeywordEntity() {
    }

    public SearchKeywordEntity(long j, String str) {
        this.insertTime = j;
        this.keyword = str;
    }

    public long getInsertTime() {
        return this.insertTime;
    }

    public void setInsertTime(long j) {
        this.insertTime = j;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String str) {
        this.dataId = str;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String str) {
        this.keyword = str;
    }
}
