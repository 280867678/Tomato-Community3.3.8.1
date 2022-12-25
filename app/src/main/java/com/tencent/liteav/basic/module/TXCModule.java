package com.tencent.liteav.basic.module;

/* renamed from: com.tencent.liteav.basic.module.a */
/* loaded from: classes3.dex */
public class TXCModule {
    private String mID = "";

    public void finalize() throws Throwable {
        clearID();
        super.finalize();
    }

    public void setID(String str) {
        clearID();
        synchronized (this) {
            if (str.length() != 0) {
                this.mID = str;
                TXCStatus.m2910a(this.mID);
            }
        }
    }

    public void clearID() {
        synchronized (this) {
            if (this.mID.length() != 0) {
                TXCStatus.m2907b(this.mID);
                this.mID = "";
            }
        }
    }

    public String getID() {
        return this.mID;
    }

    public boolean setStatusValue(int i, Object obj) {
        return TXCStatus.m2908a(this.mID, i, obj);
    }

    public Object getStatusValue(int i) {
        return TXCStatus.m2909a(this.mID, i);
    }
}
