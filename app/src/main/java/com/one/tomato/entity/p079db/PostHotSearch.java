package com.one.tomato.entity.p079db;

import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.PostHotSearch */
/* loaded from: classes3.dex */
public class PostHotSearch extends LitePalSupport {
    private String createTime;
    private String hotWord;

    /* renamed from: id */
    private int f1746id;

    public int getId() {
        return this.f1746id;
    }

    public void setId(int i) {
        this.f1746id = i;
    }

    public String getHotWord() {
        return this.hotWord;
    }

    public void setHotWord(String str) {
        this.hotWord = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && PostHotSearch.class == obj.getClass()) {
            return this.hotWord.equals(((PostHotSearch) obj).hotWord);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.hotWord);
    }
}
