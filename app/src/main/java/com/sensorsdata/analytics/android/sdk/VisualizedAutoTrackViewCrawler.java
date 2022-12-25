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

/* JADX INFO: Access modifiers changed from: package-private */
@TargetApi(16)
/* loaded from: classes3.dex */
public class VisualizedAutoTrackViewCrawler implements VTrack {
    private static final int MESSAGE_SEND_STATE_FOR_EDITING = 1;
    private static final String TAG = "SA.VisualizedAutoTrackViewCrawler";
    private final Activity mActivity;
    private String mAppVersion;
    private String mFeatureCode;
    private JSONObject mMessageObject;
    private final ViewCrawlerHandler mMessageThreadHandler;
    private String mPostUrl;
    private boolean mVisualizedAutoTrackRunning = false;
    private final EditState mEditState = new EditState();
    private final LifecycleCallbacks mLifecycleCallbacks = new LifecycleCallbacks();

    /* JADX INFO: Access modifiers changed from: package-private */
    public VisualizedAutoTrackViewCrawler(Activity activity, String str, String str2, String str3) {
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
        HandlerThread handlerThread = new HandlerThread(VisualizedAutoTrackViewCrawler.class.getCanonicalName(), 10);
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
            this.mVisualizedAutoTrackRunning = true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
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
        this.mVisualizedAutoTrackRunning = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isVisualizedAutoTrackRunning() {
        return this.mVisualizedAutoTrackRunning;
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

        private LifecycleCallbacks() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            VisualizedAutoTrackViewCrawler.this.mEditState.add(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            VisualizedAutoTrackViewCrawler.this.mEditState.remove(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class ViewCrawlerHandler extends Handler {
        private final EditProtocol mProtocol;
        private ViewSnapshot mSnapshot;
        private boolean mUseGzip;

        public void start() {
        }

        private ViewCrawlerHandler(Context context, Looper looper, String str) {
            super(looper);
            this.mSnapshot = null;
            this.mProtocol = new EditProtocol(new ResourceReader.Ids(str, context));
            this.mUseGzip = true;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            sendSnapshot(VisualizedAutoTrackViewCrawler.this.mMessageObject);
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
                    SALog.m3674i(VisualizedAutoTrackViewCrawler.TAG, "Snapshot should be initialize at first calling.");
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
                            outputStreamWriter.write("\"feature_code\": \"" + VisualizedAutoTrackViewCrawler.this.mFeatureCode + "\",");
                            outputStreamWriter.write("\"app_version\": \"" + VisualizedAutoTrackViewCrawler.this.mAppVersion + "\",");
                            outputStreamWriter.write("\"lib_version\": \"4.0.0\",");
                            outputStreamWriter.write("\"os\": \"Android\",");
                            outputStreamWriter.write("\"lib\": \"Android\",");
                            if (this.mUseGzip) {
                                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(byteArrayOutputStream2);
                                outputStreamWriter2.write("{\"activities\":");
                                outputStreamWriter2.flush();
                                snapshots = this.mSnapshot.snapshots(VisualizedAutoTrackViewCrawler.this.mEditState, byteArrayOutputStream2);
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
                                snapshots = this.mSnapshot.snapshots(VisualizedAutoTrackViewCrawler.this.mEditState, byteArrayOutputStream);
                                outputStreamWriter.write(",\"snapshot_time_millis\": ");
                                outputStreamWriter.write(Long.toString(System.currentTimeMillis() - currentTimeMillis));
                                outputStreamWriter.write("}");
                            }
                            if (!TextUtils.isEmpty(snapshots.screenName)) {
                                outputStreamWriter.write(",\"screen_name\": \"" + snapshots.screenName + "\"");
                            }
                            if (!TextUtils.isEmpty(snapshots.h5Url)) {
                                outputStreamWriter.write(",\"h5_url\": \"" + snapshots.h5Url + "\"");
                            }
                            if (!TextUtils.isEmpty(snapshots.h5Title)) {
                                outputStreamWriter.write(",\"h5_title\": \"" + snapshots.h5Title + "\"");
                            }
                            if (!TextUtils.isEmpty(snapshots.title)) {
                                outputStreamWriter.write(",\"title\": \"" + snapshots.title + "\"");
                            }
                            outputStreamWriter.write(",\"is_webview\": " + snapshots.isWebView);
                            outputStreamWriter.write("}");
                            outputStreamWriter.flush();
                            outputStreamWriter.close();
                        } catch (IOException e) {
                            SALog.m3673i(VisualizedAutoTrackViewCrawler.TAG, "Can't write snapshot request to server", e);
                            outputStreamWriter.close();
                        }
                    } catch (Throwable th) {
                        try {
                            outputStreamWriter.close();
                        } catch (IOException e2) {
                            SALog.m3673i(VisualizedAutoTrackViewCrawler.TAG, "Can't close writer.", e2);
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    SALog.m3673i(VisualizedAutoTrackViewCrawler.TAG, "Can't close writer.", e3);
                }
                postSnapshot(byteArrayOutputStream);
            } catch (EditProtocol.BadInstructionsException e4) {
                SALog.m3673i(VisualizedAutoTrackViewCrawler.TAG, "VisualizedAutoTrack server sent malformed message with snapshot request", e4);
            } catch (JSONException e5) {
                SALog.m3673i(VisualizedAutoTrackViewCrawler.TAG, "Payload with snapshot config required with snapshot request", e5);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x00de  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00f4  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void postSnapshot(ByteArrayOutputStream byteArrayOutputStream) {
            boolean z;
            int responseCode;
            InputStream errorStream;
            JSONObject jSONObject;
            if (TextUtils.isEmpty(VisualizedAutoTrackViewCrawler.this.mFeatureCode) || TextUtils.isEmpty(VisualizedAutoTrackViewCrawler.this.mPostUrl)) {
                return;
            }
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(VisualizedAutoTrackViewCrawler.this.mPostUrl).openConnection();
                if (SensorsDataAPI.sharedInstance().getSSLSocketFactory() != null && (httpURLConnection instanceof HttpsURLConnection)) {
                    ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(SensorsDataAPI.sharedInstance().getSSLSocketFactory());
                }
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-type", "text/plain");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                bufferedOutputStream.write(byteArrayOutputStream.toString().getBytes("UTF-8"));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                byteArrayOutputStream.close();
                responseCode = httpURLConnection.getResponseCode();
                try {
                    errorStream = httpURLConnection.getInputStream();
                } catch (FileNotFoundException unused) {
                    errorStream = httpURLConnection.getErrorStream();
                }
                byte[] slurp = slurp(errorStream);
                errorStream.close();
                outputStream.close();
                String str = new String(slurp, "UTF-8");
                SALog.m3674i(VisualizedAutoTrackViewCrawler.TAG, "responseCode=" + responseCode);
                SALog.m3674i(VisualizedAutoTrackViewCrawler.TAG, "response=" + str);
                jSONObject = new JSONObject(str);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
            if (responseCode == 200) {
                if (jSONObject.getInt("delay") < 0) {
                    z = false;
                    if (!z) {
                        VisualizedAutoTrackViewCrawler.this.mMessageThreadHandler.sendMessageDelayed(VisualizedAutoTrackViewCrawler.this.mMessageThreadHandler.obtainMessage(1), 1000L);
                        return;
                    } else {
                        VisualizedAutoTrackViewCrawler.this.stopUpdates(true);
                        return;
                    }
                }
            }
            z = true;
            if (!z) {
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
