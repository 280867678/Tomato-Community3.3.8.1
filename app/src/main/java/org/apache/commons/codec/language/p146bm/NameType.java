package org.apache.commons.codec.language.p146bm;

/* renamed from: org.apache.commons.codec.language.bm.NameType */
/* loaded from: classes4.dex */
public enum NameType {
    ASHKENAZI("ash"),
    GENERIC("gen"),
    SEPHARDIC("sep");
    
    private final String name;

    NameType(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
