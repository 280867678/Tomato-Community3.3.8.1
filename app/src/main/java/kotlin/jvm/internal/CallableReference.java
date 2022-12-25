package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KType;
import kotlin.reflect.KVisibility;

/* loaded from: classes4.dex */
public abstract class CallableReference implements KCallable, Serializable {
    public static final Object NO_RECEIVER = NoReceiver.INSTANCE;
    protected final Object receiver;
    private transient KCallable reflected;

    protected abstract KCallable computeReflected();

    /* loaded from: classes4.dex */
    private static class NoReceiver implements Serializable {
        private static final NoReceiver INSTANCE = new NoReceiver();

        private NoReceiver() {
        }

        private Object readResolve() throws ObjectStreamException {
            return INSTANCE;
        }
    }

    public CallableReference() {
        this(NO_RECEIVER);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CallableReference(Object obj) {
        this.receiver = obj;
    }

    public Object getBoundReceiver() {
        return this.receiver;
    }

    public KCallable compute() {
        KCallable kCallable = this.reflected;
        if (kCallable == null) {
            KCallable computeReflected = computeReflected();
            this.reflected = computeReflected;
            return computeReflected;
        }
        return kCallable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: getReflected */
    public KCallable mo6790getReflected() {
        KCallable compute = compute();
        if (compute != this) {
            return compute;
        }
        throw new KotlinReflectionNotSupportedError();
    }

    public KDeclarationContainer getOwner() {
        throw new AbstractMethodError();
    }

    public String getName() {
        throw new AbstractMethodError();
    }

    public String getSignature() {
        throw new AbstractMethodError();
    }

    @Override // kotlin.reflect.KCallable
    public List<Object> getParameters() {
        return mo6790getReflected().getParameters();
    }

    @Override // kotlin.reflect.KCallable
    public KType getReturnType() {
        return mo6790getReflected().getReturnType();
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        return mo6790getReflected().getAnnotations();
    }

    @Override // kotlin.reflect.KCallable
    public List<Object> getTypeParameters() {
        return mo6790getReflected().getTypeParameters();
    }

    @Override // kotlin.reflect.KCallable
    public Object call(Object... objArr) {
        return mo6790getReflected().call(objArr);
    }

    @Override // kotlin.reflect.KCallable
    public Object callBy(Map map) {
        return mo6790getReflected().callBy(map);
    }

    @Override // kotlin.reflect.KCallable
    public KVisibility getVisibility() {
        return mo6790getReflected().getVisibility();
    }

    @Override // kotlin.reflect.KCallable
    public boolean isFinal() {
        return mo6790getReflected().isFinal();
    }

    @Override // kotlin.reflect.KCallable
    public boolean isOpen() {
        return mo6790getReflected().isOpen();
    }

    @Override // kotlin.reflect.KCallable
    public boolean isAbstract() {
        return mo6790getReflected().isAbstract();
    }

    @Override // kotlin.reflect.KCallable
    public boolean isSuspend() {
        return mo6790getReflected().isSuspend();
    }
}
