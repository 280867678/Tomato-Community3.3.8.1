package com.youdao.sdk.app.other;

import android.net.http.AndroidHttpClient;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.youdao.sdk.app.HttpErrorCode;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.common.DownloadResponse;
import com.youdao.sdk.common.HttpClient;
import com.youdao.sdk.common.HttpResponses;
import com.youdao.sdk.common.YouDaoLog;
import java.util.ArrayList;
import java.util.Map;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

/* renamed from: com.youdao.sdk.app.other.c */
/* loaded from: classes4.dex */
public final class RunnableC5158c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Map f5895a;

    /* renamed from: b */
    final /* synthetic */ String f5896b;

    /* renamed from: c */
    final /* synthetic */ int f5897c;

    /* renamed from: d */
    final /* synthetic */ HttpHelper.HttpJsonListener f5898d;

    public RunnableC5158c(Map map, String str, int i, HttpHelper.HttpJsonListener httpJsonListener) {
        this.f5895a = map;
        this.f5896b = str;
        this.f5897c = i;
        this.f5898d = httpJsonListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f5895a.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str2 != null) {
                arrayList.add(new BasicNameValuePair(str, str2));
            }
        }
        HttpPost httpPost = new HttpPost(this.f5896b);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        AndroidHttpClient androidHttpClient = null;
        try {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList, EncryptUtil.CHARSET));
                androidHttpClient = HttpClient.getHttpClient(this.f5897c);
                DownloadResponse downloadResponse = new DownloadResponse(androidHttpClient.execute(httpPost));
                if (androidHttpClient != null) {
                    androidHttpClient.close();
                }
                this.f5898d.onResult(HttpResponses.asResponseString(downloadResponse));
            } catch (Exception e) {
                YouDaoLog.m166e("Download task threw an internal exception", e);
                this.f5898d.onError(HttpErrorCode.REQUEST_ERROR);
                if (androidHttpClient == null) {
                    return;
                }
                androidHttpClient.close();
            }
        } catch (Throwable th) {
            if (androidHttpClient != null) {
                androidHttpClient.close();
            }
            throw th;
        }
    }
}
