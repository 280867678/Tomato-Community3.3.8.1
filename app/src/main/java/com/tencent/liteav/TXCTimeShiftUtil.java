package com.tencent.liteav;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: com.tencent.liteav.m */
/* loaded from: classes3.dex */
public class TXCTimeShiftUtil {

    /* renamed from: a */
    private String f4672a = "";

    /* renamed from: b */
    private String f4673b = "";

    /* renamed from: c */
    private String f4674c = "";

    /* renamed from: d */
    private String f4675d = "";

    /* renamed from: e */
    private long f4676e = 0;

    /* renamed from: f */
    private long f4677f = 0;

    /* renamed from: g */
    private long f4678g = 0;

    /* compiled from: TXCTimeShiftUtil.java */
    /* renamed from: com.tencent.liteav.m$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3554a {
        void onLiveTime(long j);
    }

    public TXCTimeShiftUtil(Context context) {
    }

    /* renamed from: a */
    public long m1261a() {
        this.f4678g = System.currentTimeMillis() - this.f4676e;
        return this.f4678g;
    }

    /* renamed from: a */
    public String m1260a(long j) {
        return String.format("http://%s/%s/%s/timeshift.m3u8?starttime=%s&appid=%s&txKbps=0", this.f4672a, this.f4674c, this.f4673b, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(this.f4676e + (j * 1000))), this.f4675d);
    }

    /* renamed from: a */
    public int m1255a(final String str, final String str2, final int i, final AbstractC3554a abstractC3554a) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        this.f4675d = TXCCommonUtil.getAppID();
        if (TextUtils.isEmpty(this.f4675d)) {
            return -2;
        }
        AsyncTask.execute(new Runnable() { // from class: com.tencent.liteav.m.1
            @Override // java.lang.Runnable
            public void run() {
                TXCTimeShiftUtil.this.f4677f = 0L;
                TXCTimeShiftUtil.this.f4676e = 0L;
                TXCTimeShiftUtil.this.f4678g = 0L;
                String str3 = "";
                TXCTimeShiftUtil.this.f4673b = str3;
                TXCTimeShiftUtil.this.f4674c = str3;
                int i2 = i;
                if (i2 > 0) {
                    TXCTimeShiftUtil.this.f4674c = String.valueOf(i2);
                }
                TXCTimeShiftUtil.this.f4672a = str2;
                TXCTimeShiftUtil.this.f4673b = TXCCommonUtil.getStreamIDByStreamUrl(str);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format("http://%s/%s/%s/timeshift.m3u8?delay=0&appid=%s&txKbps=0", TXCTimeShiftUtil.this.f4672a, TXCTimeShiftUtil.this.f4674c, TXCTimeShiftUtil.this.f4673b, TXCTimeShiftUtil.this.f4675d)).openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Charsert", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain;");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        str3 = str3 + readLine;
                    }
                    TXCLog.m2914e("TXCTimeShiftUtil", "prepareSeekTime: receive response, strResponse = " + str3);
                    String m1256a = TXCTimeShiftUtil.this.m1256a(str3);
                    long parseLong = Long.parseLong(m1256a);
                    long currentTimeMillis = System.currentTimeMillis();
                    TXCLog.m2913i("TXCTimeShiftUtil", "time:" + m1256a + ",currentTime:" + currentTimeMillis + ",diff:" + (currentTimeMillis - parseLong));
                    TXCTimeShiftUtil.this.f4676e = parseLong * 1000;
                    TXCTimeShiftUtil.this.f4678g = currentTimeMillis - TXCTimeShiftUtil.this.f4676e;
                    if (abstractC3554a == null) {
                        return;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.m.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RunnableC35521 runnableC35521 = RunnableC35521.this;
                            abstractC3554a.onLiveTime(TXCTimeShiftUtil.this.f4678g);
                        }
                    });
                } catch (Exception e) {
                    TXCTimeShiftUtil.this.f4676e = 0L;
                    e.printStackTrace();
                }
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public String m1256a(String str) {
        int indexOf;
        String substring;
        int indexOf2;
        if (!str.contains("#EXT-TX-TS-START-TIME") || (indexOf = str.indexOf("#EXT-TX-TS-START-TIME:") + 22) <= 0 || (indexOf2 = (substring = str.substring(indexOf)).indexOf("#")) <= 0) {
            return null;
        }
        return substring.substring(0, indexOf2).replaceAll("\r\n", "");
    }
}
