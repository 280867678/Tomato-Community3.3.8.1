package kotlin.internal;

import java.lang.reflect.Method;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;

/* compiled from: PlatformImplementations.kt */
/* loaded from: classes4.dex */
public class PlatformImplementations {

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: PlatformImplementations.kt */
    /* loaded from: classes4.dex */
    public static final class ReflectAddSuppressedMethod {
        public static final Method method;

        /* JADX WARN: Removed duplicated region for block: B:10:0x0047 A[EDGE_INSN: B:10:0x0047->B:11:0x0047 ?: BREAK  , SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0043 A[LOOP:0: B:2:0x0013->B:9:0x0043, LOOP_END] */
        static {
            Method it2;
            boolean z;
            new ReflectAddSuppressedMethod();
            Method[] methods = Throwable.class.getMethods();
            Intrinsics.checkExpressionValueIsNotNull(methods, "throwableClass.methods");
            int length = methods.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    it2 = null;
                    break;
                }
                it2 = methods[i];
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (Intrinsics.areEqual(it2.getName(), "addSuppressed")) {
                    Class<?>[] parameterTypes = it2.getParameterTypes();
                    Intrinsics.checkExpressionValueIsNotNull(parameterTypes, "it.parameterTypes");
                    if (Intrinsics.areEqual((Class) ArraysKt.singleOrNull(parameterTypes), Throwable.class)) {
                        z = true;
                        if (!z) {
                            break;
                        }
                        i++;
                    }
                }
                z = false;
                if (!z) {
                }
            }
            method = it2;
        }

        private ReflectAddSuppressedMethod() {
        }
    }

    public void addSuppressed(Throwable cause, Throwable exception) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Method method = ReflectAddSuppressedMethod.method;
        if (method != null) {
            method.invoke(cause, exception);
        }
    }

    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }
}
