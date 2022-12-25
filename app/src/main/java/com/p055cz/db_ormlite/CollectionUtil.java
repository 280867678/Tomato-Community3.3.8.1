package com.p055cz.db_ormlite;

import java.util.Collection;
import java.util.List;

/* renamed from: com.cz.db_ormlite.CollectionUtil */
/* loaded from: classes2.dex */
public class CollectionUtil {
    public static boolean existValue(String str, List<String> list) {
        if (list != null && str != null) {
            for (String str2 : list) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }
}
