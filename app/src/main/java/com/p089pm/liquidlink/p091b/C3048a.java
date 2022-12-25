package com.p089pm.liquidlink.p091b;

import android.text.TextUtils;
import com.p089pm.liquidlink.p092c.C3055c;
import com.p089pm.liquidlink.p092c.C3056d;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* renamed from: com.pm.liquidlink.b.a */
/* loaded from: classes3.dex */
public class C3048a {

    /* renamed from: b */
    private SSLSocketFactory f1810b;

    /* renamed from: c */
    private boolean f1811c;

    private C3048a(boolean z) {
        C3056d.m3731a(C3048a.class);
        this.f1811c = z;
    }

    /* renamed from: a */
    public static C3048a m3749a(boolean z) {
        return new C3048a(z);
    }

    /* renamed from: a */
    private void m3751a(HttpURLConnection httpURLConnection) {
        if (httpURLConnection instanceof HttpsURLConnection) {
            m3750a((HttpsURLConnection) httpURLConnection);
        }
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(5000);
    }

    /* renamed from: a */
    private void m3750a(HttpsURLConnection httpsURLConnection) {
        if (this.f1810b == null) {
            C3052e c3052e = new C3052e();
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{c3052e}, null);
            this.f1810b = sSLContext.getSocketFactory();
        }
        httpsURLConnection.setSSLSocketFactory(this.f1810b);
    }

    /* renamed from: a */
    public C3049b m3754a(String str, Map map, String str2) {
        C3055c.m3735a("LLink", str);
        return m3753a(str, map, str2, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01a2 A[ADDED_TO_REGION, EDGE_INSN: B:37:0x01a2->B:34:0x01a2 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0187 A[Catch: IOException -> 0x0183, TryCatch #13 {IOException -> 0x0183, blocks: (B:57:0x017f, B:50:0x0187, B:52:0x018c), top: B:56:0x017f }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x018c A[Catch: IOException -> 0x0183, TRY_LEAVE, TryCatch #13 {IOException -> 0x0183, blocks: (B:57:0x017f, B:50:0x0187, B:52:0x018c), top: B:56:0x017f }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x016e A[Catch: IOException -> 0x016a, TryCatch #9 {IOException -> 0x016a, blocks: (B:75:0x0166, B:66:0x016e, B:68:0x0173), top: B:74:0x0166 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0173 A[Catch: IOException -> 0x016a, TRY_LEAVE, TryCatch #9 {IOException -> 0x016a, blocks: (B:75:0x0166, B:66:0x016e, B:68:0x0173), top: B:74:0x0166 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0166 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0146 A[Catch: IOException -> 0x0142, TryCatch #15 {IOException -> 0x0142, blocks: (B:90:0x013e, B:82:0x0146, B:84:0x014b), top: B:89:0x013e }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x014b A[Catch: IOException -> 0x0142, TRY_LEAVE, TryCatch #15 {IOException -> 0x0142, blocks: (B:90:0x013e, B:82:0x0146, B:84:0x014b), top: B:89:0x013e }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x013e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C3049b m3753a(String str, Map map, String str2, boolean z) {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        String str3;
        InputStream inputStream2;
        BufferedOutputStream bufferedOutputStream;
        InputStream errorStream;
        C3049b c3049b = new C3049b();
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        char c = 0;
        C3049b c3049b2 = c3049b;
        int i = 0;
        boolean z2 = false;
        String str4 = str;
        while (true) {
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                str4 = str4 + "?" + C3051d.m3736a(map);
                Object[] objArr = new Object[1];
                objArr[c] = "requestUrl and params:" + str4;
                C3055c.m3735a("LLink", objArr);
                httpURLConnection = (HttpURLConnection) new URL(str4).openConnection();
                try {
                    m3751a(httpURLConnection);
                    int length = str2.getBytes("UTF-8").length;
                    httpURLConnection.setFixedLengthStreamingMode(length);
                    if (z) {
                        httpURLConnection.setRequestProperty("content-type", "text/plain;charset=utf-8");
                        httpURLConnection.setRequestProperty("content-length", String.valueOf(length));
                    }
                    httpURLConnection.connect();
                    if (!TextUtils.isEmpty(str2)) {
                        outputStream = httpURLConnection.getOutputStream();
                        try {
                            bufferedOutputStream = new BufferedOutputStream(outputStream);
                            try {
                                bufferedOutputStream.write(str2.getBytes("UTF-8"));
                                bufferedOutputStream.flush();
                            } catch (EOFException unused) {
                                str3 = str4;
                                inputStream2 = null;
                                bufferedOutputStream2 = bufferedOutputStream;
                                i++;
                                try {
                                    Thread.sleep(300L);
                                } catch (InterruptedException unused2) {
                                } catch (Throwable th) {
                                    th = th;
                                    inputStream = inputStream2;
                                    if (bufferedOutputStream2 != null) {
                                    }
                                    if (outputStream != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    if (httpURLConnection != null) {
                                    }
                                    throw th;
                                }
                                if (bufferedOutputStream2 != null) {
                                    try {
                                        bufferedOutputStream2.close();
                                    } catch (IOException unused3) {
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                        }
                                        str4 = str3;
                                        if (this.f1811c) {
                                            break;
                                        }
                                        c = 0;
                                        return c3049b2;
                                    }
                                }
                                if (outputStream != null) {
                                    outputStream.close();
                                }
                                if (inputStream2 != null) {
                                    inputStream2.close();
                                }
                                if (httpURLConnection != null) {
                                }
                                str4 = str3;
                                if (this.f1811c) {
                                }
                                return c3049b2;
                            } catch (Exception e) {
                                e = e;
                                inputStream = null;
                                bufferedOutputStream2 = bufferedOutputStream;
                                try {
                                    c3049b2.m3746a(EnumC3050c.FAIL);
                                    c3049b2.m3747a(-1);
                                    c3049b2.m3743b(e.toString());
                                    if (bufferedOutputStream2 != null) {
                                        try {
                                            bufferedOutputStream2.close();
                                        } catch (IOException unused4) {
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            return c3049b2;
                                        }
                                    }
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (httpURLConnection != null) {
                                    }
                                    return c3049b2;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (bufferedOutputStream2 != null) {
                                        try {
                                            bufferedOutputStream2.close();
                                        } catch (IOException unused5) {
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            throw th;
                                        }
                                    }
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (httpURLConnection != null) {
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                inputStream = null;
                                bufferedOutputStream2 = bufferedOutputStream;
                                if (bufferedOutputStream2 != null) {
                                }
                                if (outputStream != null) {
                                }
                                if (inputStream != null) {
                                }
                                if (httpURLConnection != null) {
                                }
                                throw th;
                            }
                        } catch (EOFException unused6) {
                            str3 = str4;
                            inputStream2 = null;
                            i++;
                            Thread.sleep(300L);
                            if (bufferedOutputStream2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                            str4 = str3;
                            if (this.f1811c) {
                            }
                            return c3049b2;
                        } catch (Exception e2) {
                            e = e2;
                            inputStream = null;
                            c3049b2.m3746a(EnumC3050c.FAIL);
                            c3049b2.m3747a(-1);
                            c3049b2.m3743b(e.toString());
                            if (bufferedOutputStream2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                            return c3049b2;
                        } catch (Throwable th4) {
                            th = th4;
                            inputStream = null;
                            if (bufferedOutputStream2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                            throw th;
                        }
                    } else {
                        outputStream = null;
                        bufferedOutputStream = null;
                    }
                    if (httpURLConnection.getResponseCode() == 200) {
                        errorStream = httpURLConnection.getInputStream();
                        c3049b2 = C3049b.m3745a(C3051d.m3737a(errorStream));
                    } else {
                        int responseCode = httpURLConnection.getResponseCode();
                        errorStream = httpURLConnection.getErrorStream();
                        String m3737a = C3051d.m3737a(errorStream);
                        c3049b2.m3746a(EnumC3050c.FAIL);
                        c3049b2.m3747a(-1);
                        c3049b2.m3743b(responseCode + " : " + m3737a);
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException unused7) {
                        }
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (errorStream != null) {
                        errorStream.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    z2 = true;
                } catch (EOFException unused8) {
                    str3 = str4;
                    inputStream2 = null;
                    outputStream = null;
                } catch (Exception e3) {
                    e = e3;
                    inputStream = null;
                    outputStream = null;
                } catch (Throwable th5) {
                    th = th5;
                    inputStream = null;
                    outputStream = null;
                }
            } catch (EOFException unused9) {
                str3 = str4;
                inputStream2 = null;
                httpURLConnection = null;
                outputStream = null;
            } catch (Exception e4) {
                e = e4;
                inputStream = null;
                httpURLConnection = null;
                outputStream = null;
            } catch (Throwable th6) {
                th = th6;
                inputStream = null;
                httpURLConnection = null;
                outputStream = null;
            }
            if (this.f1811c || i >= 3 || z2) {
                break;
                break;
            }
            c = 0;
        }
        return c3049b2;
    }

    /* renamed from: a */
    public C3049b m3752a(String str, Map map, Map map2) {
        C3055c.m3735a("LLink", str);
        C3049b c3049b = new C3049b();
        try {
            return m3753a(str, map, C3051d.m3736a(map2), false);
        } catch (UnsupportedEncodingException e) {
            c3049b.m3746a(EnumC3050c.FAIL);
            c3049b.m3747a(-1);
            c3049b.m3743b(e.toString());
            return c3049b;
        }
    }
}
