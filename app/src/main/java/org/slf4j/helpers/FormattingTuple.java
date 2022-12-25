package org.slf4j.helpers;

/* loaded from: classes4.dex */
public class FormattingTuple {
    private String message;
    private Throwable throwable;

    static {
        new FormattingTuple(null);
    }

    public FormattingTuple(String str) {
        this(str, null, null);
    }

    public FormattingTuple(String str, Object[] objArr, Throwable th) {
        this.message = str;
        this.throwable = th;
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
