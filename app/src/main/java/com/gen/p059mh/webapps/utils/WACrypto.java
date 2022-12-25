package com.gen.p059mh.webapps.utils;

import java.io.Serializable;

/* renamed from: com.gen.mh.webapps.utils.WACrypto */
/* loaded from: classes2.dex */
public class WACrypto implements Serializable {
    long defaultCrypto;
    long workCrypto;

    public void setWorkKey(String str) {
        this.workCrypto = Utils.initCrypto(str);
    }

    public void setDefaultKey(String str) {
        this.defaultCrypto = Utils.initCrypto(str);
    }

    public long getWorkCrypto() {
        if (this.workCrypto == 0) {
            this.workCrypto = Utils.initCrypto(null);
        }
        return this.workCrypto;
    }

    public long getDefaultCrypto() {
        if (this.defaultCrypto == 0) {
            this.defaultCrypto = Utils.initCrypto(null);
        }
        return this.defaultCrypto;
    }

    public void release() {
        Utils.deleteCrypto(this.defaultCrypto);
        Utils.deleteCrypto(this.workCrypto);
    }
}
