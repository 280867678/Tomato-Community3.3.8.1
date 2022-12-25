package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.VerNumBean */
/* loaded from: classes3.dex */
public class VerNumBean extends LitePalSupport {
    private long currentSysTime;
    private int memberId;
    private int num;

    public VerNumBean(int i) {
        this.memberId = i;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public long getCurrentSysTime() {
        return this.currentSysTime;
    }

    public void setCurrentSysTime(long j) {
        this.currentSysTime = j;
    }
}
