package com.j256.ormlite.misc;

import java.sql.SQLException;

/* loaded from: classes3.dex */
public class SqlExceptionUtil {
    public static SQLException create(String str, Throwable th) {
        SQLException sQLException = new SQLException(str);
        sQLException.initCause(th);
        return sQLException;
    }
}
