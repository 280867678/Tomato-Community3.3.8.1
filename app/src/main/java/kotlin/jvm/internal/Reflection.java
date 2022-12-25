package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;

/* loaded from: classes4.dex */
public class Reflection {
    private static final ReflectionFactory factory;

    static {
        ReflectionFactory reflectionFactory = null;
        try {
            reflectionFactory = (ReflectionFactory) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (reflectionFactory == null) {
            reflectionFactory = new ReflectionFactory();
        }
        factory = reflectionFactory;
    }

    public static KClass getOrCreateKotlinClass(Class cls) {
        return factory.getOrCreateKotlinClass(cls);
    }

    public static String renderLambdaToString(Lambda lambda) {
        return factory.renderLambdaToString(lambda);
    }

    public static String renderLambdaToString(FunctionBase functionBase) {
        return factory.renderLambdaToString(functionBase);
    }

    public static KFunction function(FunctionReference functionReference) {
        factory.function(functionReference);
        return functionReference;
    }

    public static KProperty0 property0(PropertyReference0 propertyReference0) {
        factory.property0(propertyReference0);
        return propertyReference0;
    }

    public static KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        factory.mutableProperty0(mutablePropertyReference0);
        return mutablePropertyReference0;
    }

    public static KProperty1 property1(PropertyReference1 propertyReference1) {
        factory.property1(propertyReference1);
        return propertyReference1;
    }

    public static KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        factory.mutableProperty1(mutablePropertyReference1);
        return mutablePropertyReference1;
    }

    public static KProperty2 property2(PropertyReference2 propertyReference2) {
        factory.property2(propertyReference2);
        return propertyReference2;
    }

    public static KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        factory.mutableProperty2(mutablePropertyReference2);
        return mutablePropertyReference2;
    }
}
