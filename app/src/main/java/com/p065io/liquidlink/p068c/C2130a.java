package com.p065io.liquidlink.p068c;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/* renamed from: com.io.liquidlink.c.a */
/* loaded from: classes3.dex */
public class C2130a {
    private C2130a() {
    }

    /* renamed from: a */
    public static final C2130a m4044a() {
        C2130a c2130a;
        c2130a = C2132c.f1389a;
        return c2130a;
    }

    /* renamed from: a */
    private static String m4043a(BufferedInputStream bufferedInputStream) {
        int read;
        if (bufferedInputStream == null) {
            return "";
        }
        byte[] bArr = new byte[512];
        StringBuilder sb = new StringBuilder();
        do {
            try {
                read = bufferedInputStream.read(bArr);
                if (read > 0) {
                    sb.append(new String(bArr, 0, read));
                    continue;
                }
            } catch (Exception unused) {
            }
        } while (read >= 512);
        return sb.toString();
    }

    /* renamed from: a */
    public String m4042a(String str) {
        try {
            Object invoke = Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
            if (invoke == null) {
                return null;
            }
            return (String) invoke;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String m4041b(String str) {
        Process process;
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        BufferedOutputStream bufferedOutputStream2;
        BufferedInputStream bufferedInputStream3 = null;
        try {
            process = Runtime.getRuntime().exec("sh");
        } catch (Exception unused) {
            process = null;
            bufferedInputStream = null;
        } catch (Throwable th) {
            th = th;
            process = null;
            bufferedOutputStream = null;
        }
        try {
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());
            try {
                bufferedInputStream2 = new BufferedInputStream(process.getInputStream());
            } catch (Exception unused2) {
                bufferedInputStream2 = null;
                bufferedOutputStream2 = bufferedOutputStream;
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedOutputStream.write(str.getBytes());
                bufferedOutputStream.write(10);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                process.waitFor();
                String m4043a = m4043a(bufferedInputStream2);
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused3) {
                }
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused4) {
                }
                if (process != null) {
                    process.destroy();
                }
                return m4043a;
            } catch (Exception unused5) {
                bufferedOutputStream2 = bufferedOutputStream;
                if (bufferedOutputStream2 != 0) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException unused6) {
                    }
                }
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused7) {
                    }
                }
                if (process != null) {
                    process.destroy();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream3 = bufferedInputStream2;
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException unused8) {
                    }
                }
                if (bufferedInputStream3 != null) {
                    try {
                        bufferedInputStream3.close();
                    } catch (IOException unused9) {
                    }
                }
                if (process != null) {
                    process.destroy();
                }
                throw th;
            }
        } catch (Exception unused10) {
            bufferedInputStream = null;
            bufferedInputStream2 = bufferedInputStream;
            bufferedOutputStream2 = bufferedInputStream;
            if (bufferedOutputStream2 != 0) {
            }
            if (bufferedInputStream2 != null) {
            }
            if (process != null) {
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
        }
    }
}
