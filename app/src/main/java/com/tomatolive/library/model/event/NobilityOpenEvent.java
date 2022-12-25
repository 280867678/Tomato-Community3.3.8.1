package com.tomatolive.library.model.event;

/* loaded from: classes3.dex */
public class NobilityOpenEvent extends BaseEvent {
    public String accountBalance;
    public boolean isOpenSuccess;
    public int nobilityType;
    public String toastTips;

    public NobilityOpenEvent() {
        this.isOpenSuccess = false;
    }

    public NobilityOpenEvent(int i) {
        this.isOpenSuccess = false;
        this.nobilityType = i;
    }

    public NobilityOpenEvent(boolean z, String str, String str2) {
        this.isOpenSuccess = false;
        this.isOpenSuccess = z;
        this.toastTips = str;
        this.accountBalance = str2;
    }
}
