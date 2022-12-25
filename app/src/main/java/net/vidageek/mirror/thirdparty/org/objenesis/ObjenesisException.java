package net.vidageek.mirror.thirdparty.org.objenesis;

/* loaded from: classes4.dex */
public class ObjenesisException extends RuntimeException {
    private static final boolean jdk14;
    private static final long serialVersionUID = -2677230016262426968L;

    static {
        jdk14 = Double.parseDouble(System.getProperty("java.specification.version")) > 1.3d;
    }

    public ObjenesisException(String str) {
        super(str);
    }

    public ObjenesisException(Throwable th) {
        super(th == null ? null : th.toString());
        if (jdk14) {
            initCause(th);
        }
    }

    public ObjenesisException(String str, Throwable th) {
        super(str);
        if (jdk14) {
            initCause(th);
        }
    }
}
