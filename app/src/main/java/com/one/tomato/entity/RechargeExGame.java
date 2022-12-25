package com.one.tomato.entity;

import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes3.dex */
public class RechargeExGame implements Serializable {
    public double balance;
    public String brandId;
    public String logo;
    public String name;

    public RechargeExGame(String str, String str2, double d) {
        this.brandId = str;
        this.name = str2;
        this.balance = d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && RechargeExGame.class == obj.getClass()) {
            return this.brandId.equals(((RechargeExGame) obj).brandId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.brandId);
    }
}
