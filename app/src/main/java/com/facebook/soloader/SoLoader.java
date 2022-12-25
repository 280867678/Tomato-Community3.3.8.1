package com.facebook.soloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import dalvik.system.BaseDexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class SoLoader {
    static final boolean SYSTRACE_LIBRARY_LOADING;
    private static ApplicationSoSource sApplicationSoSource;
    private static UnpackingSoSource sBackupSoSource;
    private static int sFlags;
    static SoFileLoader sSoFileLoader;
    private static final ReentrantReadWriteLock sSoSourcesLock = new ReentrantReadWriteLock();
    private static SoSource[] sSoSources = null;
    private static int sSoSourcesVersion = 0;
    private static final HashSet<String> sLoadedLibraries = new HashSet<>();
    private static final Map<String, Object> sLoadingLibraries = new HashMap();
    private static final Set<String> sLoadedAndMergedLibraries = Collections.newSetFromMap(new ConcurrentHashMap());
    private static SystemLoadLibraryWrapper sSystemLoadLibraryWrapper = null;

    static {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                z = true;
            }
        } catch (NoClassDefFoundError | UnsatisfiedLinkError unused) {
        }
        SYSTRACE_LIBRARY_LOADING = z;
    }

    public static void init(Context context, int i) throws IOException {
        init(context, i, null);
    }

    private static void init(Context context, int i, SoFileLoader soFileLoader) throws IOException {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            initSoLoader(soFileLoader);
            initSoSources(context, i, soFileLoader);
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    private static void initSoSources(Context context, int i, SoFileLoader soFileLoader) throws IOException {
        int i2;
        sSoSourcesLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                Log.d("SoLoader", "init start");
                sFlags = i;
                ArrayList arrayList = new ArrayList();
                String str = System.getenv("LD_LIBRARY_PATH");
                if (str == null) {
                    str = "/vendor/lib:/system/lib";
                }
                String[] split = str.split(":");
                for (int i3 = 0; i3 < split.length; i3++) {
                    Log.d("SoLoader", "adding system library source: " + split[i3]);
                    arrayList.add(new DirectorySoSource(new File(split[i3]), 2));
                }
                if (context != null) {
                    if ((i & 1) != 0) {
                        sBackupSoSource = null;
                        Log.d("SoLoader", "adding exo package source: lib-main");
                        arrayList.add(0, new ExoSoSource(context, "lib-main"));
                    } else {
                        ApplicationInfo applicationInfo = context.getApplicationInfo();
                        if ((applicationInfo.flags & 1) != 0 && (applicationInfo.flags & 128) == 0) {
                            i2 = 0;
                        } else {
                            sApplicationSoSource = new ApplicationSoSource(context, Build.VERSION.SDK_INT <= 17 ? 1 : 0);
                            Log.d("SoLoader", "adding application source: " + sApplicationSoSource.toString());
                            arrayList.add(0, sApplicationSoSource);
                            i2 = 1;
                        }
                        sBackupSoSource = new ApkSoSource(context, "lib-main", i2);
                        Log.d("SoLoader", "adding backup  source: " + sBackupSoSource.toString());
                        arrayList.add(0, sBackupSoSource);
                    }
                }
                SoSource[] soSourceArr = (SoSource[]) arrayList.toArray(new SoSource[arrayList.size()]);
                int makePrepareFlags = makePrepareFlags();
                int length = soSourceArr.length;
                while (true) {
                    int i4 = length - 1;
                    if (length <= 0) {
                        break;
                    }
                    Log.d("SoLoader", "Preparing SO source: " + soSourceArr[i4]);
                    soSourceArr[i4].prepare(makePrepareFlags);
                    length = i4;
                }
                sSoSources = soSourceArr;
                sSoSourcesVersion++;
                Log.d("SoLoader", "init finish: " + sSoSources.length + " SO sources prepared");
            }
        } finally {
            Log.d("SoLoader", "init exiting");
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static int makePrepareFlags() {
        sSoSourcesLock.writeLock().lock();
        try {
            return (sFlags & 2) != 0 ? 1 : 0;
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static synchronized void initSoLoader(SoFileLoader soFileLoader) {
        synchronized (SoLoader.class) {
            if (soFileLoader != null) {
                sSoFileLoader = soFileLoader;
                return;
            }
            final Runtime runtime = Runtime.getRuntime();
            final Method nativeLoadRuntimeMethod = getNativeLoadRuntimeMethod();
            final boolean z = nativeLoadRuntimeMethod != null;
            final String classLoaderLdLoadLibrary = z ? Api14Utils.getClassLoaderLdLoadLibrary() : null;
            final String makeNonZipPath = makeNonZipPath(classLoaderLdLoadLibrary);
            sSoFileLoader = new SoFileLoader() { // from class: com.facebook.soloader.SoLoader.1
                /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
                    return;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:?, code lost:
                    return;
                 */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x008c -> B:27:0x008d). Please submit an issue!!! */
                @Override // com.facebook.soloader.SoFileLoader
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void load(String str, int i) {
                    if (!z) {
                        System.load(str);
                        return;
                    }
                    String str2 = (i & 4) == 4 ? classLoaderLdLoadLibrary : makeNonZipPath;
                    String str3 = null;
                    try {
                        try {
                            synchronized (runtime) {
                                try {
                                    str3 = Build.VERSION.SDK_INT <= 27 ? (String) nativeLoadRuntimeMethod.invoke(runtime, str, SoLoader.class.getClassLoader(), str2) : (String) nativeLoadRuntimeMethod.invoke(runtime, str, SoLoader.class.getClassLoader());
                                    try {
                                        if (str3 != null) {
                                            throw new UnsatisfiedLinkError(str3);
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                            throw th;
                        } finally {
                            if (str3 != null) {
                                Log.e("SoLoader", "Error when loading lib: " + str3 + " lib hash: " + getLibHash(str) + " search path is " + str2);
                            }
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw new RuntimeException("Error: Cannot load " + str, e);
                    }
                }

                private String getLibHash(String str) {
                    try {
                        File file = new File(str);
                        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read > 0) {
                                messageDigest.update(bArr, 0, read);
                            } else {
                                String format = String.format("%32x", new BigInteger(1, messageDigest.digest()));
                                fileInputStream.close();
                                return format;
                            }
                        }
                    } catch (IOException e) {
                        return e.toString();
                    } catch (NoSuchAlgorithmException e2) {
                        return e2.toString();
                    }
                }
            };
        }
    }

    private static Method getNativeLoadRuntimeMethod() {
        Method declaredMethod;
        int i = Build.VERSION.SDK_INT;
        if (i < 23) {
            return null;
        }
        try {
            if (i <= 27) {
                declaredMethod = Runtime.class.getDeclaredMethod("nativeLoad", String.class, ClassLoader.class, String.class);
            } else {
                declaredMethod = Runtime.class.getDeclaredMethod("nativeLoad", String.class, ClassLoader.class);
            }
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException | SecurityException e) {
            Log.w("SoLoader", "Cannot get nativeLoad method", e);
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static final class WrongAbiError extends UnsatisfiedLinkError {
        WrongAbiError(Throwable th) {
            super("APK was built for a different platform");
            initCause(th);
        }
    }

    public static boolean loadLibrary(String str) {
        return loadLibrary(str, 0);
    }

    public static boolean loadLibrary(String str, int i) throws UnsatisfiedLinkError {
        boolean z;
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources == null) {
                if ("http://www.android.com/".equals(System.getProperty("java.vendor.url"))) {
                    assertInitialized();
                } else {
                    synchronized (SoLoader.class) {
                        z = !sLoadedLibraries.contains(str);
                        if (z) {
                            if (sSystemLoadLibraryWrapper != null) {
                                sSystemLoadLibraryWrapper.loadLibrary(str);
                            } else {
                                System.loadLibrary(str);
                            }
                        }
                    }
                    return z;
                }
            }
            sSoSourcesLock.readLock().unlock();
            String mapLibName = MergedSoMapping.mapLibName(str);
            return loadLibraryBySoName(System.mapLibraryName(mapLibName != null ? mapLibName : str), str, mapLibName, i, null);
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void loadLibraryBySoName(String str, int i, StrictMode.ThreadPolicy threadPolicy) {
        loadLibraryBySoName(str, null, null, i, threadPolicy);
    }

    private static boolean loadLibraryBySoName(String str, String str2, String str3, int i, StrictMode.ThreadPolicy threadPolicy) {
        boolean z;
        Object obj;
        boolean z2 = false;
        if (TextUtils.isEmpty(str2) || !sLoadedAndMergedLibraries.contains(str2)) {
            synchronized (SoLoader.class) {
                if (!sLoadedLibraries.contains(str)) {
                    z = false;
                } else if (str3 == null) {
                    return false;
                } else {
                    z = true;
                }
                if (sLoadingLibraries.containsKey(str)) {
                    obj = sLoadingLibraries.get(str);
                } else {
                    obj = new Object();
                    sLoadingLibraries.put(str, obj);
                }
                synchronized (obj) {
                    if (!z) {
                        synchronized (SoLoader.class) {
                            if (sLoadedLibraries.contains(str)) {
                                if (str3 == null) {
                                    return false;
                                }
                                z = true;
                            }
                            if (!z) {
                                try {
                                    Log.d("SoLoader", "About to load: " + str);
                                    doLoadLibraryBySoName(str, i, threadPolicy);
                                    synchronized (SoLoader.class) {
                                        Log.d("SoLoader", "Loaded: " + str);
                                        sLoadedLibraries.add(str);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (UnsatisfiedLinkError e2) {
                                    String message = e2.getMessage();
                                    if (message != null && message.contains("unexpected e_machine:")) {
                                        throw new WrongAbiError(e2);
                                    }
                                    throw e2;
                                }
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(str2) && sLoadedAndMergedLibraries.contains(str2)) {
                        z2 = true;
                    }
                    if (str3 == null || z2) {
                        return !z;
                    }
                    if (SYSTRACE_LIBRARY_LOADING) {
                        Api18TraceUtils.beginTraceSection("MergedSoMapping.invokeJniOnload[" + str2 + "]");
                    }
                    Log.d("SoLoader", "About to merge: " + str2 + " / " + str);
                    MergedSoMapping.invokeJniOnload(str2);
                    throw null;
                }
            }
        }
        return false;
    }

    private static void doLoadLibraryBySoName(String str, int i, StrictMode.ThreadPolicy threadPolicy) throws IOException {
        boolean z;
        UnsatisfiedLinkError unsatisfiedLinkError;
        boolean z2;
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources == null) {
                Log.e("SoLoader", "Could not load: " + str + " because no SO source exists");
                throw new UnsatisfiedLinkError("couldn't find DSO to load: " + str);
            }
            sSoSourcesLock.readLock().unlock();
            if (threadPolicy == null) {
                threadPolicy = StrictMode.allowThreadDiskReads();
                z = true;
            } else {
                z = false;
            }
            if (SYSTRACE_LIBRARY_LOADING) {
                Api18TraceUtils.beginTraceSection("SoLoader.loadLibrary[" + str + "]");
            }
            int i2 = 0;
            do {
                try {
                    sSoSourcesLock.readLock().lock();
                    int i3 = sSoSourcesVersion;
                    int i4 = 0;
                    while (true) {
                        if (i2 != 0 || i4 >= sSoSources.length) {
                            break;
                        }
                        i2 = sSoSources[i4].loadLibrary(str, i, threadPolicy);
                        if (i2 == 3 && sBackupSoSource != null) {
                            Log.d("SoLoader", "Trying backup SoSource for " + str);
                            sBackupSoSource.prepare(str);
                            i2 = sBackupSoSource.loadLibrary(str, i, threadPolicy);
                            break;
                        }
                        i4++;
                    }
                    if (i2 == 0) {
                        sSoSourcesLock.writeLock().lock();
                        if (sApplicationSoSource != null && sApplicationSoSource.checkAndMaybeUpdate()) {
                            sSoSourcesVersion++;
                        }
                        z2 = sSoSourcesVersion != i3;
                        sSoSourcesLock.writeLock().unlock();
                        continue;
                    } else {
                        z2 = false;
                        continue;
                    }
                } finally {
                    if (i2 == 0 || i2 == r6) {
                    }
                }
            } while (z2);
            if (SYSTRACE_LIBRARY_LOADING) {
                Api18TraceUtils.endSection();
            }
            if (z) {
                StrictMode.setThreadPolicy(threadPolicy);
            }
            if (i2 != 0 && i2 != 3) {
                return;
            }
            String str2 = "couldn't find DSO to load: " + str;
            Log.e("SoLoader", str2);
            throw new UnsatisfiedLinkError(str2);
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static String makeNonZipPath(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split(":");
        ArrayList arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            if (!str2.contains("!")) {
                arrayList.add(str2);
            }
        }
        return TextUtils.join(":", arrayList);
    }

    private static void assertInitialized() {
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                return;
            }
            throw new RuntimeException("SoLoader.init() not yet called");
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @DoNotOptimize
    @TargetApi(14)
    /* loaded from: classes2.dex */
    public static class Api14Utils {
        private Api14Utils() {
        }

        public static String getClassLoaderLdLoadLibrary() {
            ClassLoader classLoader = SoLoader.class.getClassLoader();
            if (!(classLoader instanceof BaseDexClassLoader)) {
                throw new IllegalStateException("ClassLoader " + classLoader.getClass().getName() + " should be of type BaseDexClassLoader");
            }
            try {
                return (String) BaseDexClassLoader.class.getMethod("getLdLibraryPath", new Class[0]).invoke((BaseDexClassLoader) classLoader, new Object[0]);
            } catch (Exception e) {
                throw new RuntimeException("Cannot call getLdLibraryPath", e);
            }
        }
    }
}
