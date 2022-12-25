package com.sensorsdata.analytics.android.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.sensorsdata.analytics.android.sdk.data.DbAdapter;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class AnalyticsMessages {
    private static final int DELETE_ALL = 4;
    private static final int FLUSH_QUEUE = 3;
    private static final Map<Context, AnalyticsMessages> S_INSTANCES = new HashMap();
    private static final String TAG = "SA.AnalyticsMessages";
    private GetAnalyticsDataListener listener;
    private final Context mContext;
    private final DbAdapter mDbAdapter = DbAdapter.getInstance();
    private final Worker mWorker = new Worker();

    public void setAnalyticsDataListener(GetAnalyticsDataListener getAnalyticsDataListener) {
        this.listener = getAnalyticsDataListener;
    }

    private AnalyticsMessages(Context context) {
        this.mContext = context;
    }

    public static AnalyticsMessages getInstance(Context context) {
        AnalyticsMessages analyticsMessages;
        synchronized (S_INSTANCES) {
            Context applicationContext = context.getApplicationContext();
            if (!S_INSTANCES.containsKey(applicationContext)) {
                analyticsMessages = new AnalyticsMessages(applicationContext);
                S_INSTANCES.put(applicationContext, analyticsMessages);
            } else {
                analyticsMessages = S_INSTANCES.get(applicationContext);
            }
        }
        return analyticsMessages;
    }

    private static byte[] slurp(InputStream inputStream) throws IOException {
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enqueueEventMessage(JSONObject jSONObject) {
        try {
            synchronized (this.mDbAdapter) {
                this.mDbAdapter.addJSON(jSONObject);
                Message obtain = Message.obtain();
                obtain.what = 3;
                this.mWorker.runMessageOnce(obtain, SensorsDataAPI.sharedInstance(this.mContext).getFlushInterval());
            }
        } catch (Exception e) {
            SALog.m3674i(TAG, "enqueueEventMessage error:" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void flush() {
        Message obtain = Message.obtain();
        obtain.what = 3;
        this.mWorker.runMessage(obtain);
    }

    void flush(long j) {
        Message obtain = Message.obtain();
        obtain.what = 3;
        this.mWorker.runMessageOnce(obtain, j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void deleteAll() {
        Message obtain = Message.obtain();
        obtain.what = 4;
        this.mWorker.runMessage(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendData() {
        List<JSONObject> generateDataEntity;
        GetAnalyticsDataListener getAnalyticsDataListener;
        try {
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (SensorsDataAPI.sharedInstance(this.mContext).isNetworkRequestEnable() && SensorsDataAPI.mIsMainProcess && NetworkUtils.isNetworkAvailable(this.mContext)) {
            if (!NetworkUtils.isShouldFlush(NetworkUtils.networkType(this.mContext), SensorsDataAPI.sharedInstance(this.mContext).getFlushNetworkPolicy())) {
                return;
            }
            synchronized (this.mDbAdapter) {
                generateDataEntity = this.mDbAdapter.generateDataEntity();
            }
            if ((generateDataEntity == null && generateDataEntity.size() == 0) || (getAnalyticsDataListener = this.listener) == null) {
                return;
            }
            getAnalyticsDataListener.getAnalyticsDataList(generateDataEntity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class Worker {
        private Handler mHandler;
        private final Object mHandlerLock = new Object();

        Worker() {
            HandlerThread handlerThread = new HandlerThread("com.sensorsdata.analytics.android.sdk.AnalyticsMessages.Worker", 1);
            handlerThread.start();
            this.mHandler = new AnalyticsMessageHandler(handlerThread.getLooper());
        }

        void runMessage(Message message) {
            synchronized (this.mHandlerLock) {
                if (this.mHandler != null) {
                    this.mHandler.sendMessage(message);
                }
            }
        }

        void runMessageOnce(Message message, long j) {
            synchronized (this.mHandlerLock) {
                if (this.mHandler != null && !this.mHandler.hasMessages(message.what)) {
                    this.mHandler.sendMessageDelayed(message, j);
                }
            }
        }

        /* loaded from: classes3.dex */
        private class AnalyticsMessageHandler extends Handler {
            AnalyticsMessageHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    if (message.what == 3) {
                        AnalyticsMessages.this.sendData();
                    } else if (message.what == 4) {
                        try {
                            AnalyticsMessages.this.mDbAdapter.deleteAllEvents();
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                        }
                    } else {
                        SALog.m3674i(AnalyticsMessages.TAG, "Unexpected message received by SensorsData worker: " + message);
                    }
                } catch (RuntimeException e2) {
                    SALog.m3673i(AnalyticsMessages.TAG, "Worker threw an unhandled exception", e2);
                }
            }
        }
    }
}
