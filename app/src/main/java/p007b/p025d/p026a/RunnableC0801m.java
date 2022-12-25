package p007b.p025d.p026a;

import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: b.d.a.m */
/* loaded from: classes2.dex */
public class RunnableC0801m implements Runnable {

    /* renamed from: a */
    public AbstractC0806r f704a;

    /* renamed from: b */
    public String f705b;

    /* renamed from: c */
    public String f706c;

    /* renamed from: d */
    public Map<String, String> f707d;

    /* renamed from: e */
    public String f708e;

    /* renamed from: f */
    public HttpURLConnection f709f;

    /* renamed from: g */
    public InputStream f710g;

    /* renamed from: h */
    public OutputStream f711h;

    /* renamed from: i */
    public AtomicBoolean f712i = new AtomicBoolean(true);

    public RunnableC0801m(String str, String str2, Map<String, String> map, String str3, AbstractC0806r abstractC0806r) {
        this.f709f = null;
        this.f710g = null;
        this.f711h = null;
        this.f705b = str;
        this.f706c = str2;
        this.f707d = map;
        this.f708e = str3;
        this.f704a = abstractC0806r;
        this.f709f = null;
        this.f710g = null;
        this.f711h = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m4943a(HttpURLConnection httpURLConnection) {
        IOException e;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        ?? r1 = 0;
        try {
            try {
                if (this.f704a != null && this.f712i.get()) {
                    this.f704a.mo4931a(httpURLConnection.getResponseCode(), httpURLConnection.getHeaderFields());
                }
                this.f710g = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(this.f710g, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuffer.append(readLine);
                        stringBuffer.append("/r/n");
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        if (bufferedReader == null) {
                            return;
                        }
                        bufferedReader.close();
                    }
                }
                byte[] bytes = stringBuffer.toString().getBytes();
                if (this.f704a != null && this.f712i.get()) {
                    this.f704a.mo4929a(bytes, bytes.length);
                }
            } catch (Throwable th) {
                r1 = httpURLConnection;
                th = th;
                if (r1 != 0) {
                    try {
                        r1.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            if (r1 != 0) {
            }
            throw th;
        }
        try {
            bufferedReader.close();
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    /* renamed from: a */
    public boolean m4944a() {
        return this.f712i.get();
    }

    /* renamed from: b */
    public final void m4942b() {
        OutputStream outputStream = this.f711h;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStream = this.f710g;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        this.f709f.disconnect();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x0075 -> B:27:0x0078). Please submit an issue!!! */
    /* renamed from: b */
    public final void m4941b(HttpURLConnection httpURLConnection) {
        BufferedInputStream bufferedInputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
            } catch (IOException e2) {
                e = e2;
            }
            if (this.f704a == null) {
                return;
            }
            if (this.f712i.get()) {
                this.f704a.mo4931a(httpURLConnection.getResponseCode(), httpURLConnection.getHeaderFields());
            }
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
            try {
                byte[] bArr = new byte[65536];
                while (this.f712i.get()) {
                    int read = bufferedInputStream2.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("get http data: ");
                    sb.append(read);
                    sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    sb.append(this);
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                    this.f704a.mo4929a(bArr, read);
                }
                bufferedInputStream2.close();
            } catch (IOException e3) {
                e = e3;
                bufferedInputStream = bufferedInputStream2;
                e.printStackTrace();
                if (bufferedInputStream == null) {
                    return;
                }
                bufferedInputStream.close();
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* renamed from: c */
    public final int m4940c() {
        this.f709f = (HttpURLConnection) new URL(this.f705b).openConnection();
        this.f709f.setRequestMethod(this.f706c);
        this.f709f.setConnectTimeout(5000);
        this.f709f.setReadTimeout(5000);
        Map<String, String> map = this.f707d;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.f709f.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        this.f709f.setDoInput(true);
        if (this.f706c == "POST") {
            this.f709f.setDoOutput(true);
            this.f711h = this.f709f.getOutputStream();
            String str = this.f708e;
            if (str != null) {
                this.f711h.write(str.getBytes());
            }
        }
        return this.f709f.getResponseCode();
    }

    /* renamed from: d */
    public void m4939d() {
        this.f712i.set(false);
    }

    @Override // java.lang.Runnable
    public void run() {
        int i;
        AbstractC0806r abstractC0806r;
        AbstractC0806r abstractC0806r2;
        try {
            i = m4940c();
        } catch (IOException e) {
            e.printStackTrace();
            if (this.f712i.get() && (abstractC0806r = this.f704a) != null) {
                abstractC0806r.mo4930a(e);
            }
            i = 0;
        }
        if (i == 200) {
            m4943a(this.f709f);
        } else if (i == 206) {
            m4941b(this.f709f);
        }
        m4942b();
        if (!this.f712i.get() || (abstractC0806r2 = this.f704a) == null) {
            return;
        }
        abstractC0806r2.end(i);
    }
}
