package org.xutils.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* loaded from: classes4.dex */
public class ParameterizedTypeUtil {
    private ParameterizedTypeUtil() {
    }

    public static Type getParameterizedType(Type type, Class<?> cls, int i) {
        Class<?> cls2;
        TypeVariable<Class<?>>[] typeVariableArr;
        Type[] typeArr = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> cls3 = (Class) parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            typeVariableArr = cls3.getTypeParameters();
            typeArr = actualTypeArguments;
            cls2 = cls3;
        } else {
            cls2 = (Class) type;
            typeVariableArr = null;
        }
        if (cls == cls2) {
            if (typeArr != null) {
                return typeArr[i];
            }
            return Object.class;
        }
        Type[] genericInterfaces = cls2.getGenericInterfaces();
        if (genericInterfaces != null) {
            for (Type type2 : genericInterfaces) {
                if ((type2 instanceof ParameterizedType) && cls.isAssignableFrom((Class) ((ParameterizedType) type2).getRawType())) {
                    try {
                        return getTrueType(getParameterizedType(type2, cls, i), typeVariableArr, typeArr);
                    } catch (Throwable unused) {
                        continue;
                    }
                }
            }
        }
        Class<? super Object> superclass = cls2.getSuperclass();
        if (superclass != null && cls.isAssignableFrom(superclass)) {
            return getTrueType(getParameterizedType(cls2.getGenericSuperclass(), cls, i), typeVariableArr, typeArr);
        }
        throw new IllegalArgumentException("FindGenericType:" + type + ", declaredClass: " + cls + ", index: " + i);
    }

    private static Type getTrueType(Type type, TypeVariable<?>[] typeVariableArr, Type[] typeArr) {
        if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            String name = typeVariable.getName();
            if (typeArr != null) {
                for (int i = 0; i < typeVariableArr.length; i++) {
                    if (name.equals(typeVariableArr[i].getName())) {
                        return typeArr[i];
                    }
                }
            }
            return typeVariable;
        } else if (!(type instanceof GenericArrayType)) {
            return type;
        } else {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            return genericComponentType instanceof Class ? Array.newInstance((Class) genericComponentType, 0).getClass() : type;
        }
    }
}
