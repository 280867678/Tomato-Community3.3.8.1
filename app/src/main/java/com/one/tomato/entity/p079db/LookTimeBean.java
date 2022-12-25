package com.one.tomato.entity.p079db;

import java.util.List;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.LookTimeBean */
/* loaded from: classes3.dex */
public class LookTimeBean extends LitePalSupport {
    private int freeTimes;
    private boolean hasPay;
    private List<String> lookedIds;
    private int memberId;
    private int remainTimes;

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public boolean isHasPay() {
        return this.hasPay;
    }

    public void setHasPay(boolean z) {
        this.hasPay = z;
    }

    public int getRemainTimes() {
        return this.remainTimes;
    }

    public void setRemainTimes(int i) {
        this.remainTimes = i;
    }

    public int getFreeTimes() {
        return this.freeTimes;
    }

    public void setFreeTimes(int i) {
        this.freeTimes = i;
    }

    public List<String> getLookedIds() {
        return this.lookedIds;
    }

    public void setLookedIds(List<String> list) {
        this.lookedIds = list;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && LookTimeBean.class == obj.getClass() && this.memberId == ((LookTimeBean) obj).memberId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.memberId));
    }
}
