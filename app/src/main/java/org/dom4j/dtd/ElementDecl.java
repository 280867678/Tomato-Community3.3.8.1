package org.dom4j.dtd;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes4.dex */
public class ElementDecl implements Decl {
    private String model;
    private String name;

    public ElementDecl() {
    }

    public ElementDecl(String str, String str2) {
        this.name = str;
        this.model = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String toString() {
        return "<!ELEMENT " + this.name + ConstantUtils.PLACEHOLDER_STR_ONE + this.model + SimpleComparison.GREATER_THAN_OPERATION;
    }
}
