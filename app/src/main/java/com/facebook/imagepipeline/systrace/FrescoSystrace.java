package com.facebook.imagepipeline.systrace;

/* loaded from: classes2.dex */
public class FrescoSystrace {
    private static volatile Systrace sInstance = null;

    /* loaded from: classes2.dex */
    public interface ArgsBuilder {
    }

    /* loaded from: classes2.dex */
    public interface Systrace {
        void beginSection(String str);

        void endSection();

        boolean isTracing();
    }

    static {
        new NoOpArgsBuilder();
    }

    private FrescoSystrace() {
    }

    public static void beginSection(String str) {
        getInstance().beginSection(str);
    }

    public static void endSection() {
        getInstance().endSection();
    }

    public static boolean isTracing() {
        return getInstance().isTracing();
    }

    private static Systrace getInstance() {
        if (sInstance == null) {
            synchronized (FrescoSystrace.class) {
                if (sInstance == null) {
                    sInstance = new DefaultFrescoSystrace();
                }
            }
        }
        return sInstance;
    }

    /* loaded from: classes2.dex */
    private static final class NoOpArgsBuilder implements ArgsBuilder {
        private NoOpArgsBuilder() {
        }
    }
}
