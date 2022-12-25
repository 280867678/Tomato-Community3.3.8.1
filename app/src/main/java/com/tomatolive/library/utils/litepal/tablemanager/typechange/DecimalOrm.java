package com.tomatolive.library.utils.litepal.tablemanager.typechange;

/* loaded from: classes4.dex */
public class DecimalOrm extends OrmChange {
    @Override // com.tomatolive.library.utils.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str != null) {
            if (!str.equals("float") && !str.equals("java.lang.Float") && !str.equals("double") && !str.equals("java.lang.Double")) {
                return null;
            }
            return "real";
        }
        return null;
    }
}
