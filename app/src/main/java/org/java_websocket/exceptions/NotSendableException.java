package org.java_websocket.exceptions;

/* loaded from: classes4.dex */
public class NotSendableException extends RuntimeException {
    private static final long serialVersionUID = -6468967874576651628L;

    public NotSendableException(String str) {
        super(str);
    }

    public NotSendableException(Throwable th) {
        super(th);
    }

    public NotSendableException(String str, Throwable th) {
        super(str, th);
    }
}
