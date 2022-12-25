package com.tomatolive.library.utils.litepal.tablemanager.typechange;

/* loaded from: classes4.dex */
public class TextOrm extends OrmChange {
    @Override // com.tomatolive.library.utils.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str != null) {
            if (!str.equals("char") && !str.equals("java.lang.Character") && !str.equals("java.lang.String")) {
                return null;
            }
            return "text";
        }
        return null;
    }
}
