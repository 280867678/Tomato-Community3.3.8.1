package com.amazonaws.auth.policy;

@Deprecated
/* loaded from: classes2.dex */
public enum STSActions implements Action {
    AssumeRole("sts:AssumeRole"),
    AssumeRoleWithWebIdentity("sts:AssumeRoleWithWebIdentity");
    
    private final String action;

    STSActions(String str) {
        this.action = str;
    }

    public String getActionName() {
        return this.action;
    }
}
