package com.sensorsdata.analytics.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.EditProtocol;
import com.sensorsdata.analytics.android.sdk.ResourceReader;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import com.sensorsdata.analytics.android.sdk.visual.SnapInfo;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(16)
/* loaded from: classes3.dex */
public class HeatMapViewCrawler implements VTrack {
    private static final int MESSAGE_SEND_STATE_FOR_EDITING = 1;
    private static final String TAG = "SA.HeatMapViewCrawler";
    private final Activity mActivity;
    private String mAppVersion;
    private String mFeatureCode;
    private JSONObject mMessageObject;
    private final ViewCrawlerHandler mMessageThreadHandler;
    private String mPostUrl;
    private final EditState mEditState = new EditState();
    private final LifecycleCallbacks mLifecycleCallbacks = new LifecycleCallbacks();

    public HeatMapViewCrawler(Activity activity, String str, String str2, String str3) {
        this.mActivity = activity;
        this.mFeatureCode = str2;
        this.mEditState.add(activity);
        try {
            this.mPostUrl = URLDecoder.decode(str3, "UTF-8");
            this.mMessageObject = new JSONObject("{\"type\":\"snapshot_request\",\"payload\":{\"config\":{\"classes\":[{\"name\":\"android.view.View\",\"properties\":[{\"name\":\"importantForAccessibility\",\"get\":{\"selector\":\"isImportantForAccessibility\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}},{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]},{\"name\":\"android.widget.TextView\",\"properties\":[{\"name\":\"importantForAccessibility\",\"get\":{\"selector\":\"isImportantForAccessibility\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}},{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]},{\"name\":\"android.widget.ImageView\",\"properties\":[{\"name\":\"importantForAccessibility\",\"get\":{\"selector\":\"isImportantForAccessibility\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}},{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]}]}}}");
        } catch (Exception e) {
            SALog.printStackTrace(e);
            this.mMessageObject = null;
        }
        ((Application) this.mActivity.getApplicationContext()).registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
        try {
            this.mAppVersion = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            this.mAppVersion = "";
        }
        HandlerThread handlerThread = new HandlerThread(HeatMapViewCrawler.class.getCanonicalName(), 10);
        handlerThread.start();
        this.mMessageThreadHandler = new ViewCrawlerHandler(this.mActivity, handlerThread.getLooper(), str);
    }

    @Override // com.sensorsdata.analytics.android.sdk.VTrack
    public void startUpdates() {
        try {
            if (TextUtils.isEmpty(this.mFeatureCode) || TextUtils.isEmpty(this.mPostUrl)) {
                return;
            }
            ((Application) this.mActivity.getApplicationContext()).registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
            this.mMessageThreadHandler.start();
            this.mMessageThreadHandler.sendMessage(this.mMessageThreadHandler.obtainMessage(1));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void stopUpdates(boolean z) {
        if (z) {
            try {
                this.mFeatureCode = null;
                this.mPostUrl = null;
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return;
            }
        }
        this.mMessageThreadHandler.removeMessages(1);
        ((Application) this.mActivity.getApplicationContext()).unregisterActivityLifecycleCallbacks(this.mLifecycleCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        public LifecycleCallbacks() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            HeatMapViewCrawler.this.mEditState.add(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            HeatMapViewCrawler.this.mEditState.remove(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class ViewCrawlerHandler extends Handler {
        private final EditProtocol mProtocol;
        private ViewSnapshot mSnapshot = null;
        private boolean mUseGzip = true;

        public void start() {
        }

        public ViewCrawlerHandler(Context context, Looper looper, String str) {
            super(looper);
            this.mProtocol = new EditProtocol(new ResourceReader.Ids(str, context));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            sendSnapshot(HeatMapViewCrawler.this.mMessageObject);
        }

        private void sendSnapshot(JSONObject jSONObject) {
            SnapInfo snapshots;
            long currentTimeMillis = System.currentTimeMillis();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("payload");
                if (jSONObject2.has("config")) {
                    this.mSnapshot = this.mProtocol.readSnapshotConfig(jSONObject2);
                }
                if (this.mSnapshot == null) {
                    SALog.m3674i(HeatMapViewCrawler.TAG, "Snapshot should be initialize at first calling.");
                    return;
                }
                if (jSONObject2.has("last_image_hash")) {
                    this.mSnapshot.updateLastImageHashArray(jSONObject2.getString("last_image_hash"));
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
                try {
                    try {
                        try {
                            outputStreamWriter.write("{");
                            outputStreamWriter.write("\"type\": \"snapshot_response\",");
                            outputStreamWriter.write("\"feature_code\": \"" + HeatMapViewCrawler.this.mFeatureCode + "\",");
                            outputStreamWriter.write("\"app_version\": \"" + HeatMapViewCrawler.this.mAppVersion + "\",");
                            outputStreamWriter.write("\"os\": \"Android\",");
                            if (this.mUseGzip) {
                                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(byteArrayOutputStream2);
                                outputStreamWriter2.write("{\"activities\":");
                                outputStreamWriter2.flush();
                                snapshots = this.mSnapshot.snapshots(HeatMapViewCrawler.this.mEditState, byteArrayOutputStream2);
                                outputStreamWriter2.write(",\"snapshot_time_millis\": ");
                                outputStreamWriter2.write(Long.toString(System.currentTimeMillis() - currentTimeMillis));
                                outputStreamWriter2.write("}");
                                outputStreamWriter2.flush();
                                byteArrayOutputStream2.close();
                                byte[] bytes = byteArrayOutputStream2.toString().getBytes();
                                ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream(bytes.length);
                                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream3);
                                gZIPOutputStream.write(bytes);
                                gZIPOutputStream.close();
                                byte[] byteArray = byteArrayOutputStream3.toByteArray();
                                byteArrayOutputStream3.close();
                                outputStreamWriter.write("\"gzip_payload\": \"" + new String(Base64Coder.encode(byteArray)) + "\"");
                            } else {
                                outputStreamWriter.write("\"payload\": {");
                                outputStreamWriter.write("\"activities\":");
                                outputStreamWriter.flush();
                                snapshots = this.mSnapshot.snapshots(HeatMapViewCrawler.this.mEditState, byteArrayOutputStream);
                                outputStreamWriter.write(",\"snapshot_time_millis\": ");
                                outputStreamWriter.write(Long.toString(System.currentTimeMillis() - currentTimeMillis));
                                outputStreamWriter.write("}");
                            }
                            if (!TextUtils.isEmpty(snapshots.screenName)) {
                                outputStreamWriter.write(",\"screen_name\": \"" + snapshots.screenName + "\"");
                            }
                            outputStreamWriter.write("}");
                            outputStreamWriter.flush();
                            outputStreamWriter.close();
                        } catch (IOException e) {
                            SALog.m3673i(HeatMapViewCrawler.TAG, "Can't close writer.", e);
                        }
                    } catch (IOException e2) {
                        SALog.m3673i(HeatMapViewCrawler.TAG, "Can't write snapshot request to server", e2);
                        outputStreamWriter.close();
                    }
                    postSnapshot(byteArrayOutputStream);
                } catch (Throwable th) {
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e3) {
                        SALog.m3673i(HeatMapViewCrawler.TAG, "Can't close writer.", e3);
                    }
                    throw th;
                }
            } catch (EditProtocol.BadInstructionsException e4) {
                SALog.m3673i(HeatMapViewCrawler.TAG, "VTrack server sent malformed message with snapshot request", e4);
            } catch (JSONException e5) {
                SALog.m3673i(HeatMapViewCrawler.TAG, "Payload with snapshot config required with snapshot request", e5);
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(9:5|(11:6|7|(1:11)|12|13|14|15|16|17|18|19)|(2:21|(6:23|(2:42|43)|(2:26|27)|31|32|(2:34|35)(2:37|38)))|48|(0)|(0)|31|32|(0)(0)) */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00e8, code lost:
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00e9, code lost:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0);
         */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0120  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0136  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0149 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:88:0x013f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void postSnapshot(ByteArrayOutputStream byteArrayOutputStream) {
            OutputStream outputStream;
            BufferedOutputStream bufferedOutputStream;
            boolean z;
            int responseCode;
            JSONObject jSONObject;
            if (TextUtils.isEmpty(HeatMapViewCrawler.this.mFeatureCode) || TextUtils.isEmpty(HeatMapViewCrawler.this.mPostUrl)) {
                return;
            }
            InputStream inputStream = null;
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(HeatMapViewCrawler.this.mPostUrl).openConnection();
                if (SensorsDataAPI.sharedInstance().getSSLSocketFactory() != null && (httpURLConnection instanceof HttpsURLConnection)) {
                    ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(SensorsDataAPI.sharedInstance().getSSLSocketFactory());
                }
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-type", "text/plain");
                outputStream = httpURLConnection.getOutputStream();
                try {
                    bufferedOutputStream = new BufferedOutputStream(outputStream);
                    try {
                        try {
                            bufferedOutputStream.write(byteArrayOutputStream.toString().getBytes("UTF-8"));
                            bufferedOutputStream.flush();
                            byteArrayOutputStream.close();
                            responseCode = httpURLConnection.getResponseCode();
                            try {
                                inputStream = httpURLConnection.getInputStream();
                            } catch (FileNotFoundException unused) {
                                inputStream = httpURLConnection.getErrorStream();
                            }
                            String str = new String(slurp(inputStream), "UTF-8");
                            SALog.m3674i(HeatMapViewCrawler.TAG, "responseCode=" + responseCode);
                            SALog.m3674i(HeatMapViewCrawler.TAG, "response=" + str);
                            jSONObject = new JSONObject(str);
                        } catch (Exception e) {
                            e = e;
                            SALog.printStackTrace(e);
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e2) {
                                    SALog.printStackTrace(e2);
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Exception e3) {
                                    SALog.printStackTrace(e3);
                                }
                            }
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (Exception e4) {
                                    SALog.printStackTrace(e4);
                                }
                            }
                            z = true;
                            if (z) {
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e5) {
                                SALog.printStackTrace(e5);
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (Exception e6) {
                                SALog.printStackTrace(e6);
                            }
                        }
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e7) {
                                SALog.printStackTrace(e7);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e8) {
                    e = e8;
                    bufferedOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = null;
                    if (inputStream != null) {
                    }
                    if (outputStream != null) {
                    }
                    if (bufferedOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Exception e9) {
                e = e9;
                outputStream = null;
                bufferedOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                outputStream = null;
                bufferedOutputStream = null;
            }
            if (responseCode == 200) {
                if (jSONObject.getInt("delay") < 0) {
                    z = false;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e10) {
                            SALog.printStackTrace(e10);
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception e11) {
                            SALog.printStackTrace(e11);
                        }
                    }
                    bufferedOutputStream.close();
                    if (z) {
                        HeatMapViewCrawler.this.mMessageThreadHandler.sendMessageDelayed(HeatMapViewCrawler.this.mMessageThreadHandler.obtainMessage(1), 1000L);
                        return;
                    } else {
                        HeatMapViewCrawler.this.stopUpdates(true);
                        return;
                    }
                }
            }
            z = true;
            if (inputStream != null) {
            }
            if (outputStream != null) {
            }
            bufferedOutputStream.close();
            if (z) {
            }
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
    }
}
