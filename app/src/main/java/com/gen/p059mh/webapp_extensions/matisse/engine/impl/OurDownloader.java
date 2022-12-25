package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.support.annotation.NonNull;
import com.gen.p059mh.webapps.listener.RequestResultListener;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.UploadUtils;
import com.squareup.picasso.Downloader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.OurDownloader */
/* loaded from: classes2.dex */
class OurDownloader implements Downloader {
    public static final int TIME_OUT = 30;
    OkHttpClient client;
    Request mRequest;
    Response response;

    public void shutdown() {
    }

    public OurDownloader() {
        OkHttpClient.Builder builder = UploadUtils.getInstance().getBuilder();
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(30L, TimeUnit.SECONDS);
        builder.readTimeout(30L, TimeUnit.SECONDS);
        builder.writeTimeout(30L, TimeUnit.SECONDS);
        builder.build();
        this.client = builder.build();
    }

    @NonNull
    public Response load(@NonNull okhttp3.Request request) throws IOException {
        Headers headers = request.headers();
        URL url = request.url().url();
        this.mRequest = new Request();
        this.mRequest.setUrl(url);
        for (String str : headers.names()) {
            this.mRequest.setRequestHeaders(str, headers.get(str));
        }
        this.mRequest.setMethod(request.method());
        final Response.Builder builder = new Response.Builder();
        builder.message("OK");
        builder.protocol(Protocol.HTTP_1_1);
        builder.headers(headers);
        builder.request(request);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mRequest.setRequestListener(new RequestResultListener() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.OurDownloader.1
            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onFail(int i, String str2) {
                OurDownloader ourDownloader = OurDownloader.this;
                Response.Builder builder2 = builder;
                builder2.code(i);
                builder2.message(str2);
                ourDownloader.response = builder2.build();
                countDownLatch.countDown();
            }

            @Override // com.gen.p059mh.webapps.listener.RequestResultListener
            protected void onComplete(Request.Response response, byte[] bArr) {
                HashMap hashMap = new HashMap();
                for (String str2 : response.headers.keySet()) {
                    hashMap.put(str2, response.headers.get(str2).get(0).toString());
                }
                OurDownloader ourDownloader = OurDownloader.this;
                Response.Builder builder2 = builder;
                builder2.code(response.statusCode);
                builder2.body(ResponseBody.create(MediaType.get((String) hashMap.get("Content-Type")), bArr));
                ourDownloader.response = builder2.build();
                countDownLatch.countDown();
            }
        });
        this.mRequest.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.response;
    }
}
