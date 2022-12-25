package org.xutils.p149ex;

import java.io.IOException;

/* renamed from: org.xutils.ex.BaseException */
/* loaded from: classes4.dex */
public class BaseException extends IOException {
    public BaseException() {
    }

    public BaseException(String str) {
        super(str);
    }

    public BaseException(String str, Throwable th) {
        super(str);
        initCause(th);
    }

    public BaseException(Throwable th) {
        super(th.getMessage());
        initCause(th);
    }
}
