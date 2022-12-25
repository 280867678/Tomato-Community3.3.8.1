package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DebugMetadata.kt */
/* loaded from: classes4.dex */
public final class DebugMetadataKt {
    public static final StackTraceElement getStackTraceElement(BaseContinuationImpl getStackTraceElementImpl) {
        String str;
        Intrinsics.checkParameterIsNotNull(getStackTraceElementImpl, "$this$getStackTraceElementImpl");
        DebugMetadata debugMetadataAnnotation = getDebugMetadataAnnotation(getStackTraceElementImpl);
        if (debugMetadataAnnotation != null) {
            checkDebugMetadataVersion(1, debugMetadataAnnotation.m84v());
            int label = getLabel(getStackTraceElementImpl);
            int i = label < 0 ? -1 : debugMetadataAnnotation.m86l()[label];
            String moduleName = ModuleNameRetriever.INSTANCE.getModuleName(getStackTraceElementImpl);
            if (moduleName == null) {
                str = debugMetadataAnnotation.m88c();
            } else {
                str = moduleName + '/' + debugMetadataAnnotation.m88c();
            }
            return new StackTraceElement(str, debugMetadataAnnotation.m85m(), debugMetadataAnnotation.m87f(), i);
        }
        return null;
    }

    private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl baseContinuationImpl) {
        return (DebugMetadata) baseContinuationImpl.getClass().getAnnotation(DebugMetadata.class);
    }

    private static final int getLabel(BaseContinuationImpl baseContinuationImpl) {
        try {
            Field field = baseContinuationImpl.getClass().getDeclaredField("label");
            Intrinsics.checkExpressionValueIsNotNull(field, "field");
            field.setAccessible(true);
            Object obj = field.get(baseContinuationImpl);
            if (!(obj instanceof Integer)) {
                obj = null;
            }
            Integer num = (Integer) obj;
            return (num != null ? num.intValue() : 0) - 1;
        } catch (Exception unused) {
            return -1;
        }
    }

    private static final void checkDebugMetadataVersion(int i, int i2) {
        if (i2 <= i) {
            return;
        }
        throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + i + ", got " + i2 + ". Please update the Kotlin standard library.").toString());
    }
}
