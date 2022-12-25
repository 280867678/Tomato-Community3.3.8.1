package com.SecShell.SecShell;

import android.os.Build;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

/* renamed from: com.SecShell.SecShell.a */
/* loaded from: classes5.dex */
public class C0834a {

    /* renamed from: a */
    private static ArrayList<DexFile> f735a = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.SecShell.SecShell.a$a */
    /* loaded from: classes5.dex */
    public static final class C0835a {
        /* renamed from: a */
        private static Object[] m4892a(Object obj, ArrayList<File> arrayList, File file) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) C0834a.m4896b(obj, "makeDexElements", (Class<?>[]) new Class[]{ArrayList.class, File.class}).invoke(obj, arrayList, file);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: b */
        public static void m4891b(ClassLoader classLoader, List<File> list, File file) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object obj = C0834a.m4897b(classLoader, "pathList").get(classLoader);
            C0834a.m4895b(obj, "dexElements", m4892a(obj, new ArrayList(list), file));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.SecShell.SecShell.a$b */
    /* loaded from: classes5.dex */
    public static final class C0836b {
        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: b */
        public static void m4888b(ClassLoader classLoader, List<File> list, File file) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            IOException[] iOExceptionArr;
            Object obj = C0834a.m4897b(classLoader, "pathList").get(classLoader);
            ArrayList arrayList = new ArrayList();
            C0834a.m4894b(obj, "dexElements", m4887b(obj, new ArrayList(list), file, arrayList), Build.VERSION.SDK_INT < 28);
            if (arrayList.size() > 0) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    IOException iOException = (IOException) it2.next();
                }
                Field m4897b = C0834a.m4897b(obj, "dexElementsSuppressedExceptions");
                IOException[] iOExceptionArr2 = (IOException[]) m4897b.get(obj);
                if (iOExceptionArr2 == null) {
                    iOExceptionArr = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
                } else {
                    IOException[] iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length];
                    arrayList.toArray(iOExceptionArr3);
                    System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length);
                    iOExceptionArr = iOExceptionArr3;
                }
                m4897b.set(obj, iOExceptionArr);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: b */
        public static Object[] m4887b(Object obj, ArrayList<File> arrayList, File file, ArrayList<IOException> arrayList2) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            Method method;
            try {
                method = C0834a.m4896b(obj, "makeDexElements", (Class<?>[]) new Class[]{ArrayList.class, File.class, ArrayList.class});
            } catch (Exception unused) {
                method = null;
            }
            if (method == null) {
                try {
                    method = C0834a.m4896b(obj, "makePathElements", (Class<?>[]) new Class[]{List.class, File.class, List.class});
                } catch (Exception unused2) {
                }
            }
            return (Object[]) method.invoke(obj, arrayList, file, arrayList2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.SecShell.SecShell.a$c */
    /* loaded from: classes5.dex */
    public static final class C0837c {
        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: b */
        public static void m4885b(ClassLoader classLoader, List<File> list) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int size = list.size();
            Field m4897b = C0834a.m4897b(classLoader, "path");
            StringBuilder sb = new StringBuilder((String) m4897b.get(classLoader));
            String[] strArr = new String[size];
            File[] fileArr = new File[size];
            ZipFile[] zipFileArr = new ZipFile[size];
            DexFile[] dexFileArr = new DexFile[size];
            ListIterator<File> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                File next = listIterator.next();
                String absolutePath = next.getAbsolutePath();
                sb.append(':');
                sb.append(absolutePath);
                int previousIndex = listIterator.previousIndex();
                strArr[previousIndex] = absolutePath;
                fileArr[previousIndex] = next;
                zipFileArr[previousIndex] = new ZipFile(next);
                dexFileArr[previousIndex] = DexFile.loadDex(absolutePath, absolutePath.replace(".jar", ".dex"), 0);
            }
            m4897b.set(classLoader, sb.toString());
            C0834a.m4895b(classLoader, "mPaths", strArr);
            C0834a.m4895b(classLoader, "mFiles", fileArr);
            C0834a.m4895b(classLoader, "mZips", zipFileArr);
            C0834a.m4895b(classLoader, "mDexs", dexFileArr);
        }
    }

    /* renamed from: a */
    private static void m4905a(ClassLoader classLoader, File file, List<File> list) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
        if (!list.isEmpty()) {
            if ((Build.VERSION.RELEASE.equals("P") || Build.VERSION.SDK_INT >= 28) && !Boolean.parseBoolean(C0833H.ISMPASS)) {
                m4904a(classLoader, list.get(0).getAbsolutePath());
                return;
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 19) {
                C0836b.m4888b(classLoader, list, file);
            } else if (i >= 14) {
                C0835a.m4891b(classLoader, list, file);
            } else {
                C0837c.m4885b(classLoader, list);
            }
        }
    }

    /* renamed from: a */
    private static void m4904a(ClassLoader classLoader, String str) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        IOException[] iOExceptionArr;
        Object obj = m4897b(classLoader, "pathList").get(classLoader);
        ArrayList arrayList = new ArrayList();
        m4894b(obj, "dexElements", C0833H.m4919e(obj, arrayList, str), false);
        if (arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                IOException iOException = (IOException) it2.next();
            }
            Field m4897b = m4897b(obj, "dexElementsSuppressedExceptions");
            IOException[] iOExceptionArr2 = (IOException[]) m4897b.get(obj);
            if (iOExceptionArr2 == null) {
                iOExceptionArr = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
            } else {
                IOException[] iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length];
                arrayList.toArray(iOExceptionArr3);
                System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length);
                iOExceptionArr = iOExceptionArr3;
            }
            m4897b.set(obj, iOExceptionArr);
        }
    }

    /* renamed from: a */
    public static void m4903a(ClassLoader classLoader, String str, String str2) {
        try {
            m4905a(classLoader, new File(str2), Arrays.asList(new File(str)));
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public static void m4902a(Object obj) {
        Object[] objArr;
        try {
            ArrayList arrayList = new ArrayList();
            Field m4897b = m4897b(obj, "dexElements");
            for (Object obj2 : (Object[]) m4897b.get(obj)) {
                DexFile dexFile = (DexFile) m4897b(obj2, "dexFile").get(obj2);
                if (dexFile != null) {
                    arrayList.add(new File(dexFile.getName()));
                    f735a.add(dexFile);
                }
            }
            m4897b.set(obj, C0836b.m4887b(obj, arrayList, null, new ArrayList()));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static Field m4897b(Object obj, String str) throws NoSuchFieldException {
        for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
            }
        }
        throw new NoSuchFieldException("Field " + str + " not found in " + obj.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static Method m4896b(Object obj, String str, Class<?>... clsArr) throws NoSuchMethodException {
        for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
            try {
                Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
            }
        }
        throw new NoSuchMethodException("Method " + str + " with parameters " + Arrays.asList(clsArr) + " not found in " + obj.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static void m4895b(Object obj, String str, Object[] objArr) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        m4894b(obj, str, objArr, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static void m4894b(Object obj, String str, Object[] objArr, boolean z) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field m4897b = m4897b(obj, str);
        Object[] objArr2 = (Object[]) m4897b.get(obj);
        Object[] objArr3 = (Object[]) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length);
        if (z) {
            System.arraycopy(objArr2, 0, objArr3, objArr.length, objArr2.length);
            System.arraycopy(objArr, 0, objArr3, 0, objArr.length);
        } else {
            System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length);
            System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length);
        }
        m4897b.set(obj, objArr3);
    }
}
