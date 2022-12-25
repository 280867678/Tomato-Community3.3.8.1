package com.security.sdk.p094b;

import android.text.TextUtils;
import com.security.sdk.p095c.C3084a;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.security.sdk.b.c */
/* loaded from: classes3.dex */
public class C3082c {
    /* renamed from: a */
    public String m3699a(String str) {
        String str2 = "";
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    httpURLConnection.setReadTimeout(3000);
                    if (httpURLConnection.getResponseCode() == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = inputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                byteArrayOutputStream2.write(bArr, 0, read);
                            }
                            byteArrayOutputStream2.flush();
                            str2 = byteArrayOutputStream2.toString();
                            byteArrayOutputStream = byteArrayOutputStream2;
                        } catch (Exception e) {
                            e = e;
                            byteArrayOutputStream = byteArrayOutputStream2;
                            e.printStackTrace();
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return str2;
                        } catch (Throwable th) {
                            th = th;
                            byteArrayOutputStream = byteArrayOutputStream2;
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return str2;
    }

    /* renamed from: a */
    public boolean m3698a(String str, String str2, String str3) {
        FileOutputStream fileOutputStream;
        String str4 = str2 + ".tmp";
        boolean z = false;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    httpURLConnection.setReadTimeout(3000);
                    if (httpURLConnection.getResponseCode() == 200) {
                        File file = new File(str4);
                        if (file.exists()) {
                            file.delete();
                        }
                        InputStream inputStream = httpURLConnection.getInputStream();
                        fileOutputStream = new FileOutputStream(file);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = inputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            fileOutputStream.flush();
                            String m3689a = C3084a.m3689a(file);
                            if (TextUtils.isEmpty(str3) || str3.equalsIgnoreCase(m3689a)) {
                                z = file.renameTo(new File(str2));
                            }
                        } catch (Exception e) {
                            e = e;
                            fileOutputStream2 = fileOutputStream;
                            e.printStackTrace();
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            return z;
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } else {
                        fileOutputStream = null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return z;
    }
}
