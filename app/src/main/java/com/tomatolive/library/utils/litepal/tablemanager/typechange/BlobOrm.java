package com.tomatolive.library.utils.litepal.tablemanager.typechange;

/* loaded from: classes4.dex */
public class BlobOrm extends OrmChange {
    @Override // com.tomatolive.library.utils.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str == null || !str.equals("[B")) {
            return null;
        }
        return "blob";
    }
}
