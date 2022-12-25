package com.mcxtzhang.indexlib.IndexBar.bean;

import com.mcxtzhang.indexlib.suspension.ISuspensionInterface;

/* loaded from: classes3.dex */
public abstract class BaseIndexBean implements ISuspensionInterface {
    private String baseIndexTag;

    @Override // com.mcxtzhang.indexlib.suspension.ISuspensionInterface
    public boolean isShowSuspension() {
        return true;
    }

    public String getBaseIndexTag() {
        return this.baseIndexTag;
    }

    public BaseIndexBean setBaseIndexTag(String str) {
        this.baseIndexTag = str;
        return this;
    }

    @Override // com.mcxtzhang.indexlib.suspension.ISuspensionInterface
    public String getSuspensionTag() {
        return this.baseIndexTag;
    }
}
