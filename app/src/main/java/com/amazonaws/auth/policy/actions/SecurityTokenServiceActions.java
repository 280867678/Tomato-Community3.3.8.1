package com.amazonaws.auth.policy.actions;

import com.amazonaws.auth.policy.Action;

/* loaded from: classes2.dex */
public enum SecurityTokenServiceActions implements Action {
    AllSecurityTokenServiceActions("sts:*"),
    AssumeRole("sts:AssumeRole"),
    AssumeRoleWithWebIdentity("sts:AssumeRoleWithWebIdentity"),
    GetCallerIdentity("sts:GetCallerIdentity"),
    GetFederationToken("sts:GetFederationToken"),
    GetSessionToken("sts:GetSessionToken");
    
    private final String action;

    SecurityTokenServiceActions(String str) {
        this.action = str;
    }

    public String getActionName() {
        return this.action;
    }
}
