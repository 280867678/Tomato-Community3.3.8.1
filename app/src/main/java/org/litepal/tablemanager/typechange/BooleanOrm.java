package org.litepal.tablemanager.typechange;

/* loaded from: classes4.dex */
public class BooleanOrm extends OrmChange {
    @Override // org.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str != null) {
            if (!str.equals("boolean") && !str.equals("java.lang.Boolean")) {
                return null;
            }
            return "integer";
        }
        return null;
    }
}
