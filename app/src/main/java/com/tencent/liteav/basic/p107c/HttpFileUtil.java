package com.tencent.liteav.basic.p107c;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.liteav.basic.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.tencent.liteav.basic.c.c */
/* loaded from: classes3.dex */
public class HttpFileUtil extends HttpCommon {

    /* renamed from: b */
    private Context f2372b;

    /* renamed from: c */
    private String f2373c;

    /* renamed from: d */
    private String f2374d;

    /* renamed from: e */
    private String f2375e;

    /* renamed from: f */
    private HttpFileListener f2376f;

    /* renamed from: g */
    private long f2377g;

    /* renamed from: h */
    private long f2378h;

    /* renamed from: i */
    private boolean f2379i;

    public HttpFileUtil(Context context, String str, String str2, String str3, HttpFileListener httpFileListener, boolean z) {
        this.f2372b = context;
        this.f2373c = str;
        this.f2374d = str2;
        this.f2375e = str3;
        this.f2376f = httpFileListener;
        this.f2379i = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01db A[Catch: IOException -> 0x0197, TRY_ENTER, TryCatch #1 {IOException -> 0x0197, blocks: (B:53:0x01db, B:55:0x01e0, B:57:0x01e5, B:58:0x01e8, B:60:0x01ec, B:61:0x01a9, B:95:0x0193, B:97:0x019b, B:99:0x01a0, B:100:0x01a3, B:102:0x01a7), top: B:15:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01e0 A[Catch: IOException -> 0x0197, TryCatch #1 {IOException -> 0x0197, blocks: (B:53:0x01db, B:55:0x01e0, B:57:0x01e5, B:58:0x01e8, B:60:0x01ec, B:61:0x01a9, B:95:0x0193, B:97:0x019b, B:99:0x01a0, B:100:0x01a3, B:102:0x01a7), top: B:15:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01e5 A[Catch: IOException -> 0x0197, TryCatch #1 {IOException -> 0x0197, blocks: (B:53:0x01db, B:55:0x01e0, B:57:0x01e5, B:58:0x01e8, B:60:0x01ec, B:61:0x01a9, B:95:0x0193, B:97:0x019b, B:99:0x01a0, B:100:0x01a3, B:102:0x01a7), top: B:15:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ec A[Catch: IOException -> 0x0197, TRY_LEAVE, TryCatch #1 {IOException -> 0x0197, blocks: (B:53:0x01db, B:55:0x01e0, B:57:0x01e5, B:58:0x01e8, B:60:0x01ec, B:61:0x01a9, B:95:0x0193, B:97:0x019b, B:99:0x01a0, B:100:0x01a3, B:102:0x01a7), top: B:15:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01f1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c2 A[Catch: IOException -> 0x01d3, TryCatch #5 {IOException -> 0x01d3, blocks: (B:86:0x01bd, B:76:0x01c2, B:78:0x01c7, B:79:0x01ca, B:81:0x01ce), top: B:85:0x01bd }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c7 A[Catch: IOException -> 0x01d3, TryCatch #5 {IOException -> 0x01d3, blocks: (B:86:0x01bd, B:76:0x01c2, B:78:0x01c7, B:79:0x01ca, B:81:0x01ce), top: B:85:0x01bd }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01ce A[Catch: IOException -> 0x01d3, TRY_LEAVE, TryCatch #5 {IOException -> 0x01d3, blocks: (B:86:0x01bd, B:76:0x01c2, B:78:0x01c7, B:79:0x01ca, B:81:0x01ce), top: B:85:0x01bd }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v40, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v54, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v57, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v64, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.tencent.liteav.basic.c.d] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        HttpFileListener httpFileListener;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        InputStream inputStream2;
        Exception e;
        boolean z;
        Exception exc;
        String str;
        HttpFileListener httpFileListener2;
        HttpFileListener httpFileListener3;
        String httpStatusException;
        int i = 0;
        FileOutputStream fileOutputStream = null;
        if (!FileUtil.m2901a(this.f2372b) || TextUtils.isEmpty(this.f2373c) || TextUtils.isEmpty(this.f2374d) || TextUtils.isEmpty(this.f2375e) || !this.f2373c.startsWith("http")) {
            m3121a(null, 0);
            return;
        }
        File file = new File(this.f2374d);
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.isFile() && (httpFileListener = this.f2376f) != null) {
            httpFileListener.mo3087a(file, null);
            return;
        }
        String str2 = this.f2374d + File.separator + this.f2375e;
        File file2 = new File(str2);
        try {
            try {
                if (file2.exists()) {
                    try {
                        file2.delete();
                    } catch (Exception e2) {
                        httpURLConnection = null;
                        inputStream2 = null;
                        e = e2;
                        z = false;
                        if (fileOutputStream != null) {
                        }
                        if (inputStream2 != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        str = e;
                        if (this.f2376f != null) {
                        }
                        if (!z) {
                        }
                        httpFileListener2.mo3087a(file2, null);
                    } catch (Throwable th) {
                        th = th;
                        httpURLConnection = null;
                        inputStream = null;
                        if (fileOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        if (this.f2376f != null) {
                        }
                        throw th;
                    }
                }
                file2.createNewFile();
                httpURLConnection = (HttpURLConnection) new URL(this.f2373c).openConnection();
                try {
                    try {
                        httpURLConnection.setConnectTimeout(30000);
                        httpURLConnection.setReadTimeout(30000);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setRequestMethod("GET");
                        int responseCode = httpURLConnection.getResponseCode();
                        z = httpURLConnection.getResponseCode() == 200;
                        try {
                            if (z) {
                                if (this.f2379i) {
                                    try {
                                        this.f2377g = httpURLConnection.getContentLength();
                                        if (this.f2377g <= 0) {
                                            if (this.f2376f != null) {
                                                this.f2376f.mo3087a(file2, null);
                                            }
                                            if (httpURLConnection != null) {
                                                try {
                                                    httpURLConnection.disconnect();
                                                } catch (IOException unused) {
                                                    return;
                                                }
                                            }
                                            if (this.f2376f == null) {
                                                return;
                                            }
                                            this.f2376f.mo3090a();
                                            return;
                                        } else if (!FileUtil.m2902a(this.f2377g)) {
                                            if (this.f2376f != null) {
                                                this.f2376f.mo3087a(file2, null);
                                            }
                                            if (httpURLConnection != null) {
                                                try {
                                                    httpURLConnection.disconnect();
                                                } catch (IOException unused2) {
                                                    return;
                                                }
                                            }
                                            if (this.f2376f == null) {
                                                return;
                                            }
                                            this.f2376f.mo3090a();
                                            return;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        inputStream2 = null;
                                        if (fileOutputStream != null) {
                                        }
                                        if (inputStream2 != null) {
                                        }
                                        if (httpURLConnection != null) {
                                        }
                                        str = e;
                                        if (this.f2376f != null) {
                                        }
                                        if (!z) {
                                        }
                                        httpFileListener2.mo3087a(file2, null);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        inputStream = null;
                                        if (fileOutputStream != null) {
                                        }
                                        if (inputStream != null) {
                                        }
                                        if (httpURLConnection != null) {
                                        }
                                        if (this.f2376f != null) {
                                        }
                                        throw th;
                                    }
                                }
                                inputStream2 = httpURLConnection.getInputStream();
                                try {
                                    byte[] bArr = new byte[8192];
                                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                    try {
                                        this.f2378h = 0L;
                                        while (true) {
                                            int read = inputStream2.read(bArr);
                                            if (read == -1) {
                                                break;
                                            }
                                            fileOutputStream2.write(bArr, i, read);
                                            if (this.f2379i) {
                                                int i2 = (int) ((this.f2378h * 100) / this.f2377g);
                                                fileOutputStream = fileOutputStream2;
                                                try {
                                                    this.f2378h += read;
                                                    int i3 = (int) ((this.f2378h * 100) / this.f2377g);
                                                    if (i2 != i3 && this.f2376f != null) {
                                                        this.f2376f.mo3089a(i3);
                                                    }
                                                    fileOutputStream2 = fileOutputStream;
                                                    i = 0;
                                                } catch (Exception e4) {
                                                    e = e4;
                                                    if (fileOutputStream != null) {
                                                    }
                                                    if (inputStream2 != null) {
                                                    }
                                                    if (httpURLConnection != null) {
                                                    }
                                                    str = e;
                                                    if (this.f2376f != null) {
                                                    }
                                                    if (!z) {
                                                    }
                                                    httpFileListener2.mo3087a(file2, null);
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    inputStream = inputStream2;
                                                    if (fileOutputStream != null) {
                                                    }
                                                    if (inputStream != null) {
                                                    }
                                                    if (httpURLConnection != null) {
                                                    }
                                                    if (this.f2376f != null) {
                                                    }
                                                    throw th;
                                                }
                                            }
                                        }
                                        fileOutputStream = fileOutputStream2;
                                        fileOutputStream.flush();
                                        if (this.f2376f != null) {
                                            this.f2376f.mo3089a(100);
                                            this.f2376f.mo3088a(file2);
                                        }
                                        httpStatusException = null;
                                    } catch (Exception e5) {
                                        e = e5;
                                        fileOutputStream = fileOutputStream2;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        fileOutputStream = fileOutputStream2;
                                    }
                                } catch (Exception e6) {
                                    e = e6;
                                    fileOutputStream = null;
                                } catch (Throwable th5) {
                                    th = th5;
                                    inputStream = inputStream2;
                                    fileOutputStream = null;
                                }
                            } else {
                                httpStatusException = new HttpStatusException("http status got exception. code = " + responseCode);
                                fileOutputStream = null;
                                inputStream2 = null;
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            str = httpStatusException;
                        } catch (Exception e7) {
                            e = e7;
                            fileOutputStream = null;
                            inputStream2 = null;
                        }
                    } catch (Exception e8) {
                        fileOutputStream = null;
                        exc = e8;
                        inputStream2 = null;
                        e = exc;
                        z = false;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        str = e;
                        if (this.f2376f != null) {
                            httpFileListener3 = this.f2376f;
                            str2 = e;
                            httpFileListener3.mo3090a();
                            str = str2;
                        }
                        if (!z) {
                        }
                        httpFileListener2.mo3087a(file2, null);
                    }
                } catch (Throwable th6) {
                    th = th6;
                    fileOutputStream = null;
                    inputStream = null;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused3) {
                            throw th;
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (this.f2376f != null) {
                        this.f2376f.mo3090a();
                    }
                    throw th;
                }
            } catch (IOException unused4) {
                str = str2;
            }
        } catch (Exception e9) {
            fileOutputStream = null;
            httpURLConnection = null;
            exc = e9;
        } catch (Throwable th7) {
            th = th7;
            fileOutputStream = null;
            httpURLConnection = null;
        }
        if (this.f2376f != null) {
            httpFileListener3 = this.f2376f;
            str2 = httpStatusException;
            httpFileListener3.mo3090a();
            str = str2;
        }
        if ((!z && str == null) || (httpFileListener2 = this.f2376f) == null) {
            return;
        }
        httpFileListener2.mo3087a(file2, null);
    }

    /* renamed from: a */
    private void m3121a(Exception exc, int i) {
        HttpFileListener httpFileListener = this.f2376f;
        if (httpFileListener != null) {
            httpFileListener.mo3087a(null, exc);
        }
        this.f2376f = null;
    }
}
