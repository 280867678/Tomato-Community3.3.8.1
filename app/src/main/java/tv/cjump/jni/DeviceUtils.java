package tv.cjump.jni;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class DeviceUtils {
    private static ARCH sArch = ARCH.Unknown;

    /* loaded from: classes4.dex */
    public enum ARCH {
        Unknown,
        ARM,
        X86,
        MIPS,
        ARM64
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public static synchronized ARCH getMyCpuArch() {
        RandomAccessFile randomAccessFile;
        synchronized (DeviceUtils.class) {
            byte[] bArr = new byte[20];
            File file = new File(Environment.getRootDirectory(), "lib/libc.so");
            if (file.canRead()) {
                RandomAccessFile randomAccessFile2 = null;
                try {
                    try {
                        randomAccessFile = new RandomAccessFile(file, "r");
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile2;
                    }
                    try {
                        randomAccessFile.readFully(bArr);
                        ?? r3 = 8;
                        r3 = 8;
                        r3 = 8;
                        r3 = 8;
                        int i = bArr[18] | (bArr[19] << 8);
                        if (i == 3) {
                            sArch = ARCH.X86;
                        } else if (i == 8) {
                            sArch = ARCH.MIPS;
                        } else if (i == 40) {
                            sArch = ARCH.ARM;
                        } else if (i == 183) {
                            sArch = ARCH.ARM64;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("libc.so is unknown arch: ");
                            sb.append(Integer.toHexString(i));
                            Log.e("NativeBitmapFactory", sb.toString());
                            r3 = sb;
                        }
                        try {
                            randomAccessFile.close();
                            randomAccessFile2 = r3;
                        } catch (IOException e) {
                            e = e;
                            e.printStackTrace();
                            return sArch;
                        }
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        randomAccessFile2 = randomAccessFile;
                        e.printStackTrace();
                        randomAccessFile2 = randomAccessFile2;
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                                randomAccessFile2 = randomAccessFile2;
                            } catch (IOException e3) {
                                e = e3;
                                e.printStackTrace();
                                return sArch;
                            }
                        }
                        return sArch;
                    } catch (IOException e4) {
                        e = e4;
                        randomAccessFile2 = randomAccessFile;
                        e.printStackTrace();
                        randomAccessFile2 = randomAccessFile2;
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                                randomAccessFile2 = randomAccessFile2;
                            } catch (IOException e5) {
                                e = e5;
                                e.printStackTrace();
                                return sArch;
                            }
                        }
                        return sArch;
                    } catch (Throwable th2) {
                        th = th2;
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                } catch (IOException e8) {
                    e = e8;
                }
            }
        }
        return sArch;
    }

    public static String get_CPU_ABI() {
        return Build.CPU_ABI;
    }

    public static String get_CPU_ABI2() {
        try {
            Field declaredField = Build.class.getDeclaredField("CPU_ABI2");
            if (declaredField == null) {
                return null;
            }
            Object obj = declaredField.get(null);
            if (obj instanceof String) {
                return (String) obj;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean supportABI(String str) {
        String _cpu_abi = get_CPU_ABI();
        if (TextUtils.isEmpty(_cpu_abi) || !_cpu_abi.equalsIgnoreCase(str)) {
            return !TextUtils.isEmpty(get_CPU_ABI2()) && _cpu_abi.equalsIgnoreCase(str);
        }
        return true;
    }

    public static boolean isMiBox2Device() {
        return Build.MANUFACTURER.equalsIgnoreCase("Xiaomi") && Build.PRODUCT.equalsIgnoreCase("dredd");
    }

    public static boolean isMagicBoxDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase("MagicBox") && Build.PRODUCT.equalsIgnoreCase("MagicBox");
    }

    public static boolean isProblemBoxDevice() {
        return isMiBox2Device() || isMagicBoxDevice();
    }

    public static boolean isRealARMArch() {
        return (supportABI("armeabi-v7a") || supportABI("armeabi")) && ARCH.ARM.equals(getMyCpuArch());
    }

    public static boolean isRealX86Arch() {
        return supportABI("x86") || ARCH.X86.equals(getMyCpuArch());
    }
}
