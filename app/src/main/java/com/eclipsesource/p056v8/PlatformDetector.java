package com.eclipsesource.p056v8;

import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/* renamed from: com.eclipsesource.v8.PlatformDetector */
/* loaded from: classes2.dex */
public class PlatformDetector {

    /* renamed from: com.eclipsesource.v8.PlatformDetector$Arch */
    /* loaded from: classes2.dex */
    public static class Arch {
        public static String getName() {
            String property = System.getProperty("os.arch");
            String normalizeArch = PlatformDetector.normalizeArch(property);
            if (!normalizeArch.equals("unknown")) {
                return normalizeArch;
            }
            throw new UnsatisfiedLinkError("Unsupported arch: " + property);
        }
    }

    /* renamed from: com.eclipsesource.v8.PlatformDetector$OS */
    /* loaded from: classes2.dex */
    public static class C1255OS {
        public static String getName() {
            String property = System.getProperty("os.name");
            String normalizeOs = PlatformDetector.normalizeOs(property);
            String property2 = System.getProperty("java.specification.vendor");
            if (PlatformDetector.normalize(property2).contains(Platform.ANDROID) || normalizeOs.contains(Platform.ANDROID)) {
                return Platform.ANDROID;
            }
            if (!normalizeOs.equals("unknown")) {
                return normalizeOs;
            }
            throw new UnsatisfiedLinkError("Unsupported platform/vendor: " + property + " / " + property2);
        }

        public static boolean isWindows() {
            return getName().equals(Platform.WINDOWS);
        }

        public static boolean isMac() {
            return getName().equals(Platform.MACOSX);
        }

        public static boolean isLinux() {
            return getName().equals(Platform.LINUX);
        }

        public static boolean isNativeClient() {
            return getName().equals(Platform.NATIVE_CLIENT);
        }

        public static boolean isAndroid() {
            return getName().equals(Platform.ANDROID);
        }

        public static String getLibFileExtension() {
            if (isWindows()) {
                return "dll";
            }
            if (isMac()) {
                return "dylib";
            }
            if (isLinux() || isAndroid() || isNativeClient()) {
                return "so";
            }
            throw new UnsatisfiedLinkError("Unsupported platform library-extension for: " + getName());
        }
    }

    /* renamed from: com.eclipsesource.v8.PlatformDetector$Vendor */
    /* loaded from: classes2.dex */
    public static class Vendor {
        private static final String LINUX_ID_PREFIX = "ID=";
        private static final String[] LINUX_OS_RELEASE_FILES = {"/etc/os-release", "/usr/lib/os-release"};
        private static final String REDHAT_RELEASE_FILE = "/etc/redhat-release";

        public static String getName() {
            if (C1255OS.isWindows()) {
                return "microsoft";
            }
            if (C1255OS.isMac()) {
                return "apple";
            }
            if (C1255OS.isLinux()) {
                return getLinuxOsReleaseId();
            }
            if (C1255OS.isAndroid()) {
                return "google";
            }
            throw new UnsatisfiedLinkError("Unsupported vendor: " + getName());
        }

        private static String getLinuxOsReleaseId() {
            for (String str : LINUX_OS_RELEASE_FILES) {
                File file = new File(str);
                if (file.exists()) {
                    return parseLinuxOsReleaseFile(file);
                }
            }
            File file2 = new File(REDHAT_RELEASE_FILE);
            if (file2.exists()) {
                return parseLinuxRedhatReleaseFile(file2);
            }
            throw new UnsatisfiedLinkError("Unsupported linux vendor: " + getName());
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
            r0 = com.eclipsesource.p056v8.PlatformDetector.normalizeOsReleaseValue(r4.substring(3));
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static String parseLinuxOsReleaseFile(File file) {
            BufferedReader bufferedReader;
            BufferedReader bufferedReader2 = null;
            String str = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), EncryptUtil.CHARSET));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (readLine.startsWith(LINUX_ID_PREFIX)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    } catch (IOException unused) {
                        closeQuietly(bufferedReader);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        closeQuietly(bufferedReader2);
                        throw th;
                    }
                }
                closeQuietly(bufferedReader);
                return str;
            } catch (IOException unused2) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
            }
        }

        private static String parseLinuxRedhatReleaseFile(File file) {
            BufferedReader bufferedReader;
            String str = "centos";
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), EncryptUtil.CHARSET));
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        String lowerCase = readLine.toLowerCase(Locale.US);
                        if (!lowerCase.contains(str)) {
                            if (lowerCase.contains("fedora")) {
                                str = "fedora";
                            } else if (!lowerCase.contains("red hat enterprise linux")) {
                                closeQuietly(bufferedReader);
                                return null;
                            } else {
                                str = "rhel";
                            }
                        }
                        closeQuietly(bufferedReader);
                        return str;
                    }
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (IOException unused2) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
            closeQuietly(bufferedReader);
            return null;
        }

        private static void closeQuietly(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String normalizeOsReleaseValue(String str) {
        return str.trim().replace("\"", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String normalizeOs(String str) {
        String normalize = normalize(str);
        return normalize.startsWith("aix") ? "aix" : normalize.startsWith("hpux") ? "hpux" : (!normalize.startsWith("os400") || (normalize.length() > 5 && Character.isDigit(normalize.charAt(5)))) ? normalize.startsWith(Platform.ANDROID) ? Platform.ANDROID : normalize.startsWith(Platform.LINUX) ? Platform.LINUX : normalize.startsWith(Platform.NATIVE_CLIENT) ? Platform.NATIVE_CLIENT : (normalize.startsWith(Platform.MACOSX) || normalize.startsWith("osx")) ? Platform.MACOSX : normalize.startsWith("freebsd") ? "freebsd" : normalize.startsWith("openbsd") ? "openbsd" : normalize.startsWith("netbsd") ? "netbsd" : (normalize.startsWith("solaris") || normalize.startsWith("sunos")) ? "sunos" : normalize.startsWith(Platform.WINDOWS) ? Platform.WINDOWS : "unknown" : "os400";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String normalizeArch(String str) {
        String normalize = normalize(str);
        return normalize.matches("^(x8664|amd64|ia32e|em64t|x64)$") ? "x86_64" : normalize.matches("^(x8632|x86|i[3-6]86|ia32|x32)$") ? "x86_32" : normalize.matches("^(ia64|itanium64)$") ? "itanium_64" : normalize.matches("^(sparc|sparc32)$") ? "sparc_32" : normalize.matches("^(sparcv9|sparc64)$") ? "sparc_64" : (normalize.matches("^(arm|arm32)$") || normalize.startsWith("armv7")) ? "arm_32" : ("aarch64".equals(normalize) || normalize.startsWith("armv8")) ? "aarch_64" : normalize.matches("^(ppc|ppc32)$") ? "ppc_32" : "ppc64".equals(normalize) ? "ppc_64" : "ppc64le".equals(normalize) ? "ppcle_64" : "s390".equals(normalize) ? "s390_32" : "s390x".equals(normalize) ? "s390_64" : "unknown";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String normalize(String str) {
        return str == null ? "" : str.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }
}
