package net.vidageek.mirror.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class Bean {
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public List<String> getter(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("get" + capitalize(str));
        arrayList.add("is" + capitalize(str));
        return arrayList;
    }

    public String setter(String str) {
        return "set" + capitalize(str);
    }
}
