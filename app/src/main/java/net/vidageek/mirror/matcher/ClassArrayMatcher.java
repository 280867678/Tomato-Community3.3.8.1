package net.vidageek.mirror.matcher;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ClassArrayMatcher {
    private static Map<Class<?>, Class<?>> primitiveToWrapper = new HashMap();
    private final Class<?>[] baseClasses;

    static {
        primitiveToWrapper.put(Boolean.TYPE, Boolean.class);
        primitiveToWrapper.put(Byte.TYPE, Byte.class);
        primitiveToWrapper.put(Character.TYPE, Character.class);
        primitiveToWrapper.put(Short.TYPE, Short.class);
        primitiveToWrapper.put(Integer.TYPE, Integer.class);
        primitiveToWrapper.put(Long.TYPE, Long.class);
        primitiveToWrapper.put(Float.TYPE, Float.class);
        primitiveToWrapper.put(Double.TYPE, Double.class);
    }

    public ClassArrayMatcher(Class<?>... clsArr) {
        if (clsArr == null) {
            throw new IllegalArgumentException("argument baseClasses cannot be null.");
        }
        this.baseClasses = clsArr;
    }

    public MatchType match(Class<?>... clsArr) {
        if (clsArr == null) {
            throw new IllegalArgumentException("argument classes cannot be null.");
        }
        if (this.baseClasses.length != clsArr.length) {
            return MatchType.DONT_MATCH;
        }
        if (isPerfectMatch(clsArr)) {
            return MatchType.PERFECT;
        }
        if (isMatch(clsArr)) {
            return MatchType.MATCH;
        }
        return MatchType.DONT_MATCH;
    }

    private boolean isMatch(Class<?>[] clsArr) {
        int i = 0;
        while (true) {
            Class<?>[] clsArr2 = this.baseClasses;
            if (i < clsArr2.length) {
                if (!areClassesAssignable(clsArr[i], clsArr2[i])) {
                    return false;
                }
                i++;
            } else {
                return true;
            }
        }
    }

    private boolean isPerfectMatch(Class<?>[] clsArr) {
        int i = 0;
        while (true) {
            Class<?>[] clsArr2 = this.baseClasses;
            if (i < clsArr2.length) {
                if (!areClassesEqual(clsArr2[i], clsArr[i])) {
                    return false;
                }
                i++;
            } else {
                return true;
            }
        }
    }

    private boolean areClassesAssignable(Class<?> cls, Class<?> cls2) {
        if (!(cls.isPrimitive() ^ cls2.isPrimitive())) {
            return cls.isAssignableFrom(cls2);
        }
        if (cls.isPrimitive()) {
            return primitiveToWrapper.get(cls).isAssignableFrom(cls2);
        }
        return primitiveToWrapper.get(cls2).isAssignableFrom(cls);
    }

    private boolean areClassesEqual(Class<?> cls, Class<?> cls2) {
        if (!(cls.isPrimitive() ^ cls2.isPrimitive())) {
            return cls.equals(cls2);
        }
        if (cls.isPrimitive()) {
            return primitiveToWrapper.get(cls).equals(cls2);
        }
        return primitiveToWrapper.get(cls2).equals(cls);
    }
}
