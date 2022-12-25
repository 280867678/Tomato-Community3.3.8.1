package com.tomatolive.library.utils.litepal.tablemanager.typechange;

/* loaded from: classes4.dex */
public class NumericOrm extends OrmChange {
    @Override // com.tomatolive.library.utils.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str != null) {
            if (!str.equals("int") && !str.equals("java.lang.Integer") && !str.equals("long") && !str.equals("java.lang.Long") && !str.equals("short") && !str.equals("java.lang.Short")) {
                return null;
            }
            return "integer";
        }
        return null;
    }
}
