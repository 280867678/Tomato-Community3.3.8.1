package com.alipay.sdk.packet;

import com.alipay.sdk.cons.C0961a;
import com.alipay.sdk.encrypt.C0969c;
import com.alipay.sdk.encrypt.C0971e;
import com.alipay.sdk.encrypt.C0972f;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1008n;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Locale;

/* renamed from: com.alipay.sdk.packet.c */
/* loaded from: classes2.dex */
public final class C0978c {

    /* renamed from: a */
    private boolean f997a;

    /* renamed from: b */
    private String f998b = C1008n.m4397a(24);

    public C0978c(boolean z) {
        this.f997a = z;
    }

    /* renamed from: a */
    public C0979d m4525a(C0977b c0977b, boolean z, String str) {
        byte[] m4520a;
        if (c0977b == null) {
            return null;
        }
        byte[] bytes = c0977b.m4529a().getBytes();
        byte[] bytes2 = c0977b.m4528b().getBytes();
        if (z) {
            try {
                bytes2 = C0969c.m4549a(bytes2);
            } catch (Exception unused) {
                z = false;
            }
        }
        if (this.f997a) {
            m4520a = m4520a(bytes, m4522a(this.f998b, C0961a.f960e), m4521a(this.f998b, bytes2, str));
        } else {
            m4520a = m4520a(bytes, bytes2);
        }
        return new C0979d(z, m4520a);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C0977b m4524a(C0979d c0979d, String str) {
        ByteArrayInputStream byteArrayInputStream;
        String str2;
        String str3;
        try {
            byteArrayInputStream = new ByteArrayInputStream(c0979d.m4517b());
            try {
                try {
                    byte[] bArr = new byte[5];
                    byteArrayInputStream.read(bArr);
                    byte[] bArr2 = new byte[m4523a(new String(bArr))];
                    byteArrayInputStream.read(bArr2);
                    str2 = new String(bArr2);
                } catch (Exception e) {
                    e = e;
                    str2 = null;
                }
                try {
                    byte[] bArr3 = new byte[5];
                    byteArrayInputStream.read(bArr3);
                    int m4523a = m4523a(new String(bArr3));
                    if (m4523a > 0) {
                        byte[] bArr4 = new byte[m4523a];
                        byteArrayInputStream.read(bArr4);
                        if (this.f997a) {
                            bArr4 = m4519b(this.f998b, bArr4, str);
                        }
                        if (c0979d.m4518a()) {
                            bArr4 = C0969c.m4548b(bArr4);
                        }
                        str3 = new String(bArr4);
                    } else {
                        str3 = null;
                    }
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused) {
                    }
                } catch (Exception e2) {
                    e = e2;
                    C0996c.m4436a(e);
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (Exception unused2) {
                        }
                    }
                    str3 = null;
                    if (str2 == null) {
                    }
                    return new C0977b(str2, str3);
                }
            } catch (Throwable th) {
                th = th;
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayInputStream = null;
            str2 = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream = null;
            if (byteArrayInputStream != null) {
            }
            throw th;
        }
        if (str2 == null || str3 != null) {
            return new C0977b(str2, str3);
        }
        return null;
    }

    /* renamed from: a */
    private static byte[] m4522a(String str, String str2) {
        return C0971e.m4546a(str, str2);
    }

    /* renamed from: a */
    private static byte[] m4521a(String str, byte[] bArr, String str2) {
        return C0972f.m4543a(str, bArr, str2);
    }

    /* renamed from: b */
    private static byte[] m4519b(String str, byte[] bArr, String str2) {
        return C0972f.m4541b(str, bArr, str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0051, code lost:
        if (r2 == null) goto L24;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.OutputStream, java.io.ByteArrayOutputStream] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] m4520a(byte[]... bArr) {
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2;
        byte[] bArr2 = null;
        if (bArr != null) {
            ?? length = bArr.length;
            try {
                if (length != 0) {
                    try {
                        length = new ByteArrayOutputStream();
                    } catch (Exception e) {
                        e = e;
                        length = 0;
                        dataOutputStream2 = null;
                    } catch (Throwable th) {
                        th = th;
                        length = 0;
                        dataOutputStream = null;
                    }
                    try {
                        dataOutputStream2 = new DataOutputStream(length);
                        try {
                            for (byte[] bArr3 : bArr) {
                                dataOutputStream2.write(m4526a(bArr3.length).getBytes());
                                dataOutputStream2.write(bArr3);
                            }
                            dataOutputStream2.flush();
                            bArr2 = length.toByteArray();
                            try {
                                length.close();
                            } catch (Exception unused) {
                            }
                        } catch (Exception e2) {
                            e = e2;
                            C0996c.m4436a(e);
                            if (length != 0) {
                                try {
                                    length.close();
                                } catch (Exception unused2) {
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        dataOutputStream2 = null;
                    } catch (Throwable th2) {
                        th = th2;
                        dataOutputStream = null;
                        if (length != 0) {
                            try {
                                length.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (Exception unused4) {
                            }
                        }
                        throw th;
                    }
                    try {
                        dataOutputStream2.close();
                    } catch (Exception unused5) {
                        return bArr2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return null;
    }

    /* renamed from: a */
    private static String m4526a(int i) {
        return String.format(Locale.getDefault(), "%05d", Integer.valueOf(i));
    }

    /* renamed from: a */
    private static int m4523a(String str) {
        return Integer.parseInt(str);
    }
}
