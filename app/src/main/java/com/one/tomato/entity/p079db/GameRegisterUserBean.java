package com.one.tomato.entity.p079db;

import java.util.ArrayList;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.GameRegisterUserBean */
/* loaded from: classes3.dex */
public class GameRegisterUserBean extends LitePalSupport {
    private ArrayList<String> memberNames;

    public ArrayList<String> getMemberNames() {
        return this.memberNames;
    }

    public void setMemberNames(ArrayList<String> arrayList) {
        this.memberNames = arrayList;
    }
}
