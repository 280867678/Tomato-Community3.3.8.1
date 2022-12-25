package p007b.p025d.p026a;

import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.callback.exception.ServerInnerException;
import com.zzz.ipfssdk.callback.exception.UrlNotExsitsException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;

/* renamed from: b.d.a.p */
/* loaded from: classes2.dex */
public class C0804p {

    /* renamed from: a */
    public static C0804p f715a;

    /* renamed from: e */
    public BlockingQueue<RunnableC0801m> f718e = new LinkedBlockingDeque();

    /* renamed from: f */
    public BlockingQueue<RunnableC0801m> f719f = new LinkedBlockingDeque();

    /* renamed from: b */
    public Thread f716b = new Thread(new RunnableC0802n(this));

    /* renamed from: c */
    public Thread f717c = new Thread(new RunnableC0803o(this));

    public C0804p() {
        new ConcurrentSkipListSet();
        this.f716b.start();
        this.f717c.start();
    }

    /* renamed from: a */
    public static C0804p m4938a() {
        if (f715a == null) {
            f715a = new C0804p();
        }
        return f715a;
    }

    /* renamed from: a */
    public RunnableC0801m m4936a(int i, String str, String str2, Map<String, String> map, String str3, AbstractC0806r abstractC0806r) {
        RunnableC0801m runnableC0801m = new RunnableC0801m(str, str2, map, str3, abstractC0806r);
        (i == 0 ? this.f718e : this.f719f).add(runnableC0801m);
        return runnableC0801m;
    }

    /* renamed from: a */
    public String m4934a(String str, String str2, Map<String, String> map, String str3) {
        return m4933a(str, str2, map, str3, ConstantUtils.MAX_ITEM_NUM, 5000);
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00f5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String m4933a(String str, String str2, Map<String, String> map, String str3, int i, int i2) {
        Throwable th;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        InputStream inputStream;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestMethod(str2);
                httpURLConnection.setConnectTimeout(i);
                httpURLConnection.setReadTimeout(i2);
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setDoInput(true);
                if (str2 == "POST") {
                    httpURLConnection.setDoOutput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    if (str3 != null) {
                        try {
                            outputStream.write(str3.getBytes());
                        } catch (Throwable th2) {
                            th = th2;
                            outputStream = outputStream;
                            inputStream = null;
                            if (bufferedReader2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                            throw th;
                        }
                    }
                } else {
                    outputStream = null;
                }
                try {
                    int responseCode = httpURLConnection.getResponseCode();
                    StringBuilder sb = new StringBuilder();
                    sb.append("http response:");
                    sb.append(responseCode);
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                    if (responseCode != 200 && responseCode != 206) {
                        if (responseCode != 500) {
                            throw new UrlNotExsitsException();
                        }
                        throw new ServerInnerException();
                    }
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            stringBuffer.append(readLine);
                            stringBuffer.append("\r\n");
                        }
                        String stringBuffer2 = stringBuffer.toString();
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return stringBuffer2;
                    } catch (Throwable th4) {
                        bufferedReader2 = bufferedReader;
                        th = th4;
                        if (bufferedReader2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    inputStream = null;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                outputStream = null;
                inputStream = outputStream;
                if (bufferedReader2 != null) {
                }
                if (outputStream != null) {
                }
                if (inputStream != null) {
                }
                if (httpURLConnection != null) {
                }
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            httpURLConnection = null;
            outputStream = null;
        }
    }

    /* renamed from: a */
    public void m4937a(int i, RunnableC0801m runnableC0801m) {
        (i == 0 ? this.f718e : this.f719f).remove(runnableC0801m);
    }
}
