package com.SecShell.SecShell;

import dalvik.system.PathClassLoader;

/* renamed from: com.SecShell.SecShell.b */
/* loaded from: classes5.dex */
class C0838b extends PathClassLoader {

    /* renamed from: a */
    private final ClassLoader f736a;

    public C0838b(String str, ClassLoader classLoader) {
        super(str, classLoader.getParent());
        this.f736a = classLoader;
    }

    @Override // dalvik.system.BaseDexClassLoader, java.lang.ClassLoader
    public Class<?> findClass(String str) throws ClassNotFoundException {
        return ((str == null || !str.equals(C0833H.class.getName())) && (str == null || !str.equals(H1.class.getName()))) ? super.findClass(str) : this.f736a.loadClass(str);
    }
}
