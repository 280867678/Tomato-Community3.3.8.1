package com.one.tomato.mvp.base.okhttp.interceptor.logging;

import android.text.TextUtils;
import com.one.tomato.utils.AppSecretUtil;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class LoggingInterceptor implements Interceptor {
    private Builder builder;
    private boolean isDebug;

    private LoggingInterceptor(Builder builder) {
        this.builder = builder;
        this.isDebug = builder.isDebug;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (this.builder.getHeaders().size() > 0) {
            Headers headers = request.headers();
            Request.Builder newBuilder = request.newBuilder();
            newBuilder.headers(this.builder.getHeaders());
            for (String str : headers.names()) {
                newBuilder.addHeader(str, headers.get(str));
            }
            request = newBuilder.build();
        }
        long nanoTime = System.nanoTime();
        Response proceed = chain.proceed(request);
        List<String> pathSegments = request.url().pathSegments();
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
        String headers2 = proceed.headers().toString();
        int code = proceed.code();
        boolean isSuccessful = proceed.isSuccessful();
        ResponseBody body = proceed.body();
        MediaType contentType = body.contentType();
        String str2 = null;
        if (contentType != null) {
            str2 = contentType.subtype();
        }
        if (str2 != null && (str2.contains("json") || str2.contains("xml") || str2.contains("plain") || str2.contains("html"))) {
            String jsonString = Printer.getJsonString(body.string());
            String header = proceed.header("notEncry");
            String header2 = request.header("serverType");
            String decodeResponse = ((TextUtils.isEmpty(header2) || (!"agent".equals(header2) && !"jav".equals(header2))) && !TextUtils.isEmpty(header) && !header.equals("0")) ? AppSecretUtil.decodeResponse(jsonString) : jsonString;
            if (this.isDebug) {
                Printer.printJsonResponse(this.builder, millis, isSuccessful, code, headers2, decodeResponse, pathSegments);
            }
            ResponseBody create = ResponseBody.create(contentType, decodeResponse);
            Response.Builder newBuilder2 = proceed.newBuilder();
            newBuilder2.body(create);
            return newBuilder2.build();
        }
        if (this.isDebug) {
            Printer.printFileResponse(this.builder, millis, isSuccessful, code, headers2, pathSegments);
        }
        return proceed;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private static String TAG = "LoggingI";
        private boolean isDebug;
        private Logger logger;
        private String requestTag;
        private String responseTag;
        private int type = 4;
        private Level level = Level.BASIC;
        private Headers.Builder builder = new Headers.Builder();

        /* JADX INFO: Access modifiers changed from: package-private */
        public int getType() {
            return this.type;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Level getLevel() {
            return this.level;
        }

        Headers getHeaders() {
            return this.builder.build();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String getTag(boolean z) {
            return z ? TextUtils.isEmpty(this.requestTag) ? TAG : this.requestTag : TextUtils.isEmpty(this.responseTag) ? TAG : this.responseTag;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Logger getLogger() {
            return this.logger;
        }

        public Builder addHeader(String str, String str2) {
            this.builder.set(str, str2);
            return this;
        }

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public Builder request(String str) {
            this.requestTag = str;
            return this;
        }

        public Builder response(String str) {
            this.responseTag = str;
            return this;
        }

        public Builder loggable(boolean z) {
            this.isDebug = z;
            return this;
        }

        public Builder log(int i) {
            this.type = i;
            return this;
        }

        public LoggingInterceptor build() {
            return new LoggingInterceptor(this);
        }
    }
}
