package com.sensorsdata.analytics.android.sdk.http;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.ConnectErrorException;
import com.sensorsdata.analytics.android.sdk.exceptions.ResponseErrorException;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes3.dex */
public class OriginalHttpProcessor implements IhttpProcessor {
    public static final String TAG = "OriginalHttpProcessor";
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void get(String str, Map<String, Object> map, ICallBack iCallBack) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, Map<String, Object> map, ICallBack iCallBack) {
    }

    public OriginalHttpProcessor(Context context) {
        this.mContext = context;
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, String str2, ICallBack iCallBack) {
        try {
            sendHttpRequest(str, encodeData(str2), iCallBack);
        } catch (ConnectErrorException e) {
            e.printStackTrace();
        } catch (ResponseErrorException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    private void sendHttpRequest(String str, String str2, final ICallBack iCallBack) throws ConnectErrorException, ResponseErrorException {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedOutputStream bufferedOutputStream;
        InputStream inputStream;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (httpURLConnection == null) {
                closeStream(null, null, null, httpURLConnection);
                return;
            }
            try {
                if (SensorsDataAPI.sharedInstance().getSSLSocketFactory() != null && (httpURLConnection instanceof HttpsURLConnection)) {
                    ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(SensorsDataAPI.sharedInstance().getSSLSocketFactory());
                }
                httpURLConnection.setInstanceFollowRedirects(false);
                if (SensorsDataAPI.sharedInstance(this.mContext).getDebugMode() == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
                    httpURLConnection.addRequestProperty("Dry-Run", "true");
                }
                httpURLConnection.setRequestProperty("Cookie", SensorsDataAPI.sharedInstance(this.mContext).getCookie(false));
                Uri.Builder builder = new Uri.Builder();
                if (!TextUtils.isEmpty(str2)) {
                    builder.appendQueryParameter("crc", String.valueOf(str2.hashCode()));
                }
                builder.appendQueryParameter("gzip", "1");
                builder.appendQueryParameter("data_list", str2);
                String encodedQuery = builder.build().getEncodedQuery();
                if (TextUtils.isEmpty(encodedQuery)) {
                    closeStream(null, null, null, httpURLConnection);
                    return;
                }
                httpURLConnection.setFixedLengthStreamingMode(encodedQuery.getBytes("UTF-8").length);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                outputStream = httpURLConnection.getOutputStream();
                try {
                    bufferedOutputStream = new BufferedOutputStream(outputStream);
                } catch (IOException e) {
                    e = e;
                    bufferedOutputStream = null;
                    inputStream = bufferedOutputStream;
                    bufferedOutputStream2 = httpURLConnection;
                    try {
                        throw new ConnectErrorException(e);
                    } catch (Throwable th) {
                        th = th;
                        httpURLConnection = bufferedOutputStream2;
                        bufferedOutputStream2 = bufferedOutputStream;
                        closeStream(bufferedOutputStream2, outputStream, inputStream, httpURLConnection);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = null;
                    closeStream(bufferedOutputStream2, outputStream, inputStream, httpURLConnection);
                    throw th;
                }
                try {
                    bufferedOutputStream.write(encodedQuery.getBytes("UTF-8"));
                    bufferedOutputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    try {
                        inputStream = httpURLConnection.getInputStream();
                    } catch (FileNotFoundException unused) {
                        inputStream = httpURLConnection.getErrorStream();
                    }
                    try {
                        byte[] slurp = slurp(inputStream);
                        inputStream.close();
                        final String str3 = new String(slurp, "UTF-8");
                        if (SALog.isLogEnabled()) {
                            if (responseCode < 200 || responseCode >= 300) {
                                this.mHandler.post(new Runnable() { // from class: com.sensorsdata.analytics.android.sdk.http.OriginalHttpProcessor.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        iCallBack.onFailed(str3);
                                    }
                                });
                            } else {
                                this.mHandler.post(new Runnable() { // from class: com.sensorsdata.analytics.android.sdk.http.OriginalHttpProcessor.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        iCallBack.onSuccess(str3);
                                    }
                                });
                            }
                        }
                        if (responseCode < 200 || responseCode >= 300) {
                            throw new ResponseErrorException(String.format("flush failure with response '%s', the response code is '%d'", str3, Integer.valueOf(responseCode)), responseCode);
                        }
                        closeStream(bufferedOutputStream, outputStream, null, httpURLConnection);
                    } catch (IOException e2) {
                        e = e2;
                        bufferedOutputStream2 = httpURLConnection;
                        throw new ConnectErrorException(e);
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream2 = bufferedOutputStream;
                        closeStream(bufferedOutputStream2, outputStream, inputStream, httpURLConnection);
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    inputStream = null;
                    bufferedOutputStream2 = httpURLConnection;
                    throw new ConnectErrorException(e);
                } catch (Throwable th4) {
                    th = th4;
                    inputStream = null;
                    bufferedOutputStream2 = bufferedOutputStream;
                    closeStream(bufferedOutputStream2, outputStream, inputStream, httpURLConnection);
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = null;
                bufferedOutputStream = null;
            } catch (Throwable th5) {
                th = th5;
                outputStream = null;
                inputStream = outputStream;
                closeStream(bufferedOutputStream2, outputStream, inputStream, httpURLConnection);
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            outputStream = null;
            bufferedOutputStream = null;
            inputStream = null;
        } catch (Throwable th6) {
            th = th6;
            httpURLConnection = null;
            outputStream = null;
        }
    }

    private String encodeData(String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes("UTF-8").length);
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes("UTF-8"));
        gZIPOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return new String(Base64Coder.encode(byteArray));
    }

    private byte[] slurp(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private void closeStream(BufferedOutputStream bufferedOutputStream, OutputStream outputStream, InputStream inputStream, HttpURLConnection httpURLConnection) {
        if (bufferedOutputStream != null) {
            try {
                bufferedOutputStream.close();
            } catch (Exception e) {
                SALog.m3674i(TAG, e.getMessage());
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (Exception e2) {
                SALog.m3674i(TAG, e2.getMessage());
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e3) {
                SALog.m3674i(TAG, e3.getMessage());
            }
        }
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e4) {
                SALog.m3674i(TAG, e4.getMessage());
            }
        }
    }
}
