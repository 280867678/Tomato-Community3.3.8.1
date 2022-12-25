package com.amazonaws.logging;

/* loaded from: classes2.dex */
public interface Log {
    void debug(Object obj);

    void debug(Object obj, Throwable th);

    void error(Object obj);

    void error(Object obj, Throwable th);

    void info(Object obj);

    boolean isDebugEnabled();

    boolean isErrorEnabled();

    boolean isInfoEnabled();

    void trace(Object obj);

    void warn(Object obj);

    void warn(Object obj, Throwable th);
}
