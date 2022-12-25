package com.gen.p059mh.webapps.utils;

import com.gen.p059mh.webapps.listener.UploadListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/* renamed from: com.gen.mh.webapps.utils.UploadUtils */
/* loaded from: classes2.dex */
public class UploadUtils {
    public static final int CONNECT_TIMEOUT = 60;
    private static volatile UploadUtils INSTANCE = null;
    public static final int READ_TIMEOUT = 100;
    public static final int WRITE_TIMEOUT = 60;
    Map<String, Call> callMap = new HashMap();
    OkHttpClient okHttpClient = new OkHttpClient();

    private UploadUtils() {
    }

    public boolean cancelRequest(String str) {
        Call call = this.callMap.get(str);
        if (call == null) {
            return false;
        }
        call.cancel();
        return true;
    }

    public OkHttpClient.Builder getBuilder() {
        return this.okHttpClient.newBuilder();
    }

    public static UploadUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (UploadUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UploadUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void upload(String str, File file, String str2, Object obj, Object obj2, Object obj3, final String str3, final UploadListener uploadListener) {
        OkHttpClient.Builder newBuilder = this.okHttpClient.newBuilder();
        newBuilder.connectTimeout(obj3 != null ? ((Number) obj3).intValue() : 60, TimeUnit.SECONDS);
        newBuilder.readTimeout(100L, TimeUnit.SECONDS);
        newBuilder.writeTimeout(60L, TimeUnit.SECONDS);
        newBuilder.build();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart(str2, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        if (obj2 != null) {
            Map map = (Map) obj2;
            for (String str4 : map.keySet()) {
                builder.addFormDataPart(str4, (String) map.get(str4));
            }
        }
        UploadRequestBody uploadRequestBody = new UploadRequestBody(builder.build());
        uploadRequestBody.setUploadListener(uploadListener);
        Request.Builder builder2 = new Request.Builder();
        if (obj != null) {
            Map map2 = (Map) obj;
            for (String str5 : map2.keySet()) {
                builder2.addHeader(str5, (String) map2.get(str5));
            }
        }
        builder2.url(str);
        builder2.post(uploadRequestBody);
        Call newCall = this.okHttpClient.newCall(builder2.build());
        this.callMap.put(str3, newCall);
        newCall.enqueue(new Callback() { // from class: com.gen.mh.webapps.utils.UploadUtils.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                iOException.printStackTrace();
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    UploadUtils.this.callMap.remove(str3);
                }
                uploadListener.onResponse(response);
            }
        });
    }

    public void upload2(String str, File file, Object obj, Object obj2, String str2, String str3, final String str4, final UploadListener uploadListener) {
        OkHttpClient.Builder newBuilder = this.okHttpClient.newBuilder();
        newBuilder.connectTimeout(obj2 != null ? ((Number) obj2).intValue() : 60, TimeUnit.SECONDS);
        newBuilder.readTimeout(100L, TimeUnit.SECONDS);
        newBuilder.writeTimeout(60L, TimeUnit.SECONDS);
        newBuilder.build();
        Request.Builder builder = new Request.Builder();
        builder.addHeader("RANGE", "bytes=" + str3 + "-");
        if (obj != null) {
            Map map = (Map) obj;
            for (String str5 : map.keySet()) {
                builder.addHeader(str5, (String) map.get(str5));
            }
        }
        UploadRequestBody uploadRequestBody = new UploadRequestBody(RequestBody.create(MediaType.parse("application/octet-stream"), file));
        builder.url(str);
        builder.method(str2, uploadRequestBody);
        Call newCall = this.okHttpClient.newCall(builder.build());
        this.callMap.put(str4, newCall);
        newCall.enqueue(new Callback() { // from class: com.gen.mh.webapps.utils.UploadUtils.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                iOException.printStackTrace();
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    UploadUtils.this.callMap.remove(str4);
                }
                uploadListener.onResponse(response);
            }
        });
    }

    /* renamed from: com.gen.mh.webapps.utils.UploadUtils$UploadRequestBody */
    /* loaded from: classes2.dex */
    class UploadRequestBody extends RequestBody {
        private int mCurrentLength;
        private RequestBody requestBody;
        private UploadListener uploadListener;

        public void setUploadListener(UploadListener uploadListener) {
            this.uploadListener = uploadListener;
        }

        public UploadRequestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() throws IOException {
            return this.requestBody.contentLength();
        }

        @Override // okhttp3.RequestBody
        public MediaType contentType() {
            return this.requestBody.contentType();
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            final long contentLength = contentLength();
            BufferedSink buffer = Okio.buffer(new ForwardingSink(bufferedSink) { // from class: com.gen.mh.webapps.utils.UploadUtils.UploadRequestBody.1
                @Override // okio.ForwardingSink, okio.Sink
                public void write(Buffer buffer2, long j) throws IOException {
                    UploadRequestBody uploadRequestBody = UploadRequestBody.this;
                    uploadRequestBody.mCurrentLength = (int) (uploadRequestBody.mCurrentLength + j);
                    if (UploadRequestBody.this.uploadListener != null) {
                        UploadRequestBody.this.uploadListener.onProgress(UploadRequestBody.this.mCurrentLength, contentLength);
                    }
                    super.write(buffer2, j);
                }
            });
            this.requestBody.writeTo(buffer);
            buffer.flush();
        }
    }
}
