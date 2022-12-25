package com.p065io.liquidlink.p071f;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/* renamed from: com.io.liquidlink.f.b */
/* loaded from: classes3.dex */
public class C2158b extends AbstractC2157a {
    public C2158b(Context context) {
        super(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x002a, code lost:
        if (r2 == null) goto L9;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String m3983a(File file) {
        RandomAccessFile randomAccessFile;
        byte[] bArr = new byte[0];
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (IOException unused) {
            randomAccessFile = null;
        } catch (Throwable th) {
            th = th;
            randomAccessFile = null;
        }
        try {
            bArr = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bArr);
            randomAccessFile.close();
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
        try {
            randomAccessFile.close();
        } catch (IOException unused4) {
        }
        if (bArr.length == 0) {
            return null;
        }
        return new String(bArr);
    }

    /* renamed from: a */
    private boolean m3982a(File file, String str) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(str.getBytes());
                try {
                    fileOutputStream2.close();
                    return true;
                } catch (IOException unused) {
                    return true;
                }
            } catch (IOException unused2) {
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                return false;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (IOException unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: b */
    public boolean mo3981b(String str, String str2) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            try {
                File file = new File(externalStorageDirectory.getAbsolutePath() + "/Installation", str);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                return m3982a(file, str2);
            } catch (Exception unused) {
                return false;
            }
        }
        return false;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: c */
    public String mo3980c(String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            try {
                return m3983a(new File(externalStorageDirectory.getAbsolutePath() + "/Installation", str));
            } catch (Exception unused) {
                return null;
            }
        }
        return null;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: d */
    public boolean mo3979d(String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return new File(externalStorageDirectory.getAbsolutePath() + "/Installation", str).delete();
        }
        return false;
    }
}
