package com.youdao.sdk.app.other;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.app.other.C5168p;
import com.youdao.sdk.ydtranslate.TranslateSdk;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/* renamed from: com.youdao.sdk.app.other.e */
/* loaded from: classes4.dex */
public class C5160e {

    /* renamed from: a */
    private C5159d f5906a;

    /* renamed from: b */
    private C5156a f5907b;

    /* renamed from: c */
    private RunnableC5162b f5908c;

    /* renamed from: e */
    private C5168p.C5169a f5909e;

    /* renamed from: f */
    private Context f5910f;

    public C5160e(Context context, String str) {
        SQLiteDatabase sQLiteDatabase;
        this.f5908c = null;
        if (context == null) {
            return;
        }
        this.f5910f = context.getApplicationContext();
        this.f5907b = new C5156a(context);
        try {
            sQLiteDatabase = context.getApplicationContext().openOrCreateDatabase(str, 0, null);
        } catch (SQLiteException unused) {
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase != null) {
            this.f5906a = new C5159d(sQLiteDatabase);
        }
        this.f5909e = new C5168p.C5169a(context.getApplicationContext());
        this.f5908c = new RunnableC5162b();
        new Thread(this.f5908c).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public synchronized void m210c() {
        if (this.f5906a == null) {
            return;
        }
        if (this.f5906a.m224a() == 0) {
            return;
        }
        long m220b = this.f5906a.m220b();
        StringBuffer stringBuffer = new StringBuffer();
        Cursor m223a = this.f5906a.m223a(m220b);
        try {
            stringBuffer.append('[');
            if (m223a.moveToFirst()) {
                stringBuffer.append(m223a.getString(0));
                while (m223a.moveToNext()) {
                    stringBuffer.append(',');
                    stringBuffer.append(m223a.getString(0));
                }
                stringBuffer.append(']');
                m223a.close();
                if (m211b(stringBuffer.toString())) {
                    this.f5906a.m219b(m220b);
                }
                return;
            }
            m223a.close();
        } catch (Exception unused) {
            m223a.close();
        }
    }

    /* renamed from: b */
    private boolean m211b(String str) {
        boolean z = false;
        if (str == null || str.length() <= 0 || this.f5910f == null) {
            return false;
        }
        HttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            try {
                HttpPost httpPost = new HttpPost(YouDaoApplication.isForeignVersion() ? "http://openapi-sg.youdao.com/log" : "http://openapi.youdao.com/log");
                httpPost.addHeader("Accept-Encoding", "gzip");
                ArrayList arrayList = new ArrayList();
                Map<String, String> m227b = this.f5907b.m227b();
                TranslateSdk translateSdk = new TranslateSdk();
                int nextInt = new Random().nextInt(1000);
                String sign = translateSdk.sign(this.f5910f, YouDaoApplication.mAppKey, "", String.valueOf(nextInt));
                m227b.put("q", "");
                m227b.put("salt", String.valueOf(nextInt));
                m227b.put("signType", "v1");
                m227b.put("sign", sign);
                m227b.put("batchLog", str);
                m227b.put("method", "batchLog");
                m227b.put("appKey", YouDaoApplication.mAppKey);
                String[] generateEncryptV1 = EncryptHelper.generateEncryptV1(m214a(m227b));
                arrayList.add(new BasicNameValuePair("s", generateEncryptV1[0]));
                arrayList.add(new BasicNameValuePair("et", generateEncryptV1[1]));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                httpPost.setEntity(urlEncodedFormEntity);
                this.f5909e.m180a(defaultHttpClient);
                if (defaultHttpClient.execute(httpPost).getStatusLine().getStatusCode() / 100 == 2) {
                    z = true;
                }
                urlEncodedFormEntity.consumeContent();
            } catch (Throwable th) {
                try {
                    defaultHttpClient.getConnectionManager().shutdown();
                } catch (Exception unused) {
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            defaultHttpClient.getConnectionManager().shutdown();
        } catch (Exception unused2) {
            return z;
        }
    }

    /* renamed from: a */
    String m214a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                sb.append(key);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                sb.append(Uri.encode(value));
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    public boolean m215a(String str) {
        RunnableC5162b runnableC5162b;
        Handler handler;
        C5159d c5159d = this.f5906a;
        if ((c5159d != null ? c5159d.m224a() : 0) >= 512 || (runnableC5162b = this.f5908c) == null || (handler = runnableC5162b.f5912a) == null) {
            return false;
        }
        return this.f5908c.f5912a.sendMessage(handler.obtainMessage(3, str));
    }

    /* renamed from: a */
    public void m217a() {
        this.f5906a.m218c();
    }

    /* renamed from: b */
    public void m213b() {
        Handler handler;
        RunnableC5162b runnableC5162b = this.f5908c;
        if (runnableC5162b == null || (handler = runnableC5162b.f5912a) == null) {
            return;
        }
        handler.sendEmptyMessage(1);
    }

    /* renamed from: com.youdao.sdk.app.other.e$b */
    /* loaded from: classes4.dex */
    class RunnableC5162b implements Runnable {

        /* renamed from: a */
        Handler f5912a;

        private RunnableC5162b() {
            this.f5912a = null;
        }

        @Override // java.lang.Runnable
        public void run() {
            Looper.prepare();
            this.f5912a = new HandlerC5161a();
            Looper.loop();
        }
    }

    /* renamed from: com.youdao.sdk.app.other.e$a */
    /* loaded from: classes4.dex */
    class HandlerC5161a extends Handler {
        HandlerC5161a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int m224a;
            int i = message.what;
            if (i == 1) {
                if (C5160e.this.f5906a == null || C5160e.this.f5906a.m224a() < 16) {
                    return;
                }
                C5160e.this.m210c();
            } else if (i == 2) {
                C5160e.this.m210c();
            } else if (i != 3) {
            } else {
                String str = (String) message.obj;
                if (C5160e.this.f5906a == null || (m224a = C5160e.this.f5906a.m224a()) >= 512 || C5160e.this.f5906a.m222a(str) <= 0) {
                    return;
                }
                if (m224a >= 16) {
                    C5160e.this.m210c();
                } else if (m224a != 0) {
                } else {
                    removeMessages(2);
                    sendEmptyMessageDelayed(2, 15000L);
                }
            }
        }
    }
}
