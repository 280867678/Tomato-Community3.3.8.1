package com.gen.p059mh.webapp_extensions.websocket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* renamed from: com.gen.mh.webapp_extensions.websocket.WsManager */
/* loaded from: classes2.dex */
public class WsManager {
    private boolean isNeedReconnect;
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private Request mRequest;
    private WebSocket mWebSocket;
    private WebSocketListener mWebSocketListener;
    private String wsUrl;
    private int mCurrentStatus = -1;
    private boolean isManualClose = false;
    private Handler wsMainHandler = new Handler(Looper.getMainLooper());
    private int reconnectCount = 0;
    private Runnable reconnectRunnable = new Runnable() { // from class: com.gen.mh.webapp_extensions.websocket.WsManager.1
        @Override // java.lang.Runnable
        public void run() {
            Log.i("websocket", "服务器重连中。。。");
            WsManager.this.buildConnect();
        }
    };
    private Lock mLock = new ReentrantLock();

    public void setmWebSocketListener(WebSocketListener webSocketListener) {
        this.mWebSocketListener = webSocketListener;
    }

    public void setmWebSocket(WebSocket webSocket) {
        this.mWebSocket = webSocket;
    }

    public WsManager(Builder builder) {
        this.mContext = builder.mContext;
        this.wsUrl = builder.wsUrl;
        this.isNeedReconnect = builder.needReconnect;
        this.mOkHttpClient = builder.mOkHttpClient;
    }

    public void initWebSocket() {
        if (this.mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true);
            this.mOkHttpClient = builder.build();
        }
        if (this.mRequest == null) {
            Request.Builder builder2 = new Request.Builder();
            builder2.url(this.wsUrl);
            this.mRequest = builder2.build();
        }
        this.mOkHttpClient.dispatcher().cancelAll();
        try {
            this.mLock.lockInterruptibly();
            this.mOkHttpClient.newWebSocket(this.mRequest, this.mWebSocketListener);
            this.mLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getCurrentStatus() {
        return this.mCurrentStatus;
    }

    public synchronized void setCurrentStatus(int i) {
        this.mCurrentStatus = i;
    }

    public void startConnect() {
        this.isManualClose = false;
        buildConnect();
    }

    public void stopConnect(int i, String str) {
        this.isManualClose = true;
        disconnect(i, str);
    }

    private void tryReconnect() {
        if ((!this.isNeedReconnect) || this.isManualClose) {
            return;
        }
        Log.e("WsManager", "reconnectCount2222222[" + this.reconnectCount + "]");
        if (!isNetworkConnected(this.mContext)) {
            setCurrentStatus(-1);
            Log.e("WsManager", "[请您检查网络，未连接]");
        }
        setCurrentStatus(2);
        Log.e("WsManager", "reconnectCount11111111[" + this.reconnectCount + "]");
        this.wsMainHandler.postDelayed(this.reconnectRunnable, 10000L);
        Log.e("WsManager", "reconnectCount[" + this.reconnectCount + "]");
        this.reconnectCount = this.reconnectCount + 1;
    }

    private void cancelReconnect() {
        this.wsMainHandler.removeCallbacks(this.reconnectRunnable);
        this.reconnectCount = 0;
    }

    public void connected() {
        cancelReconnect();
    }

    private void disconnect(int i, String str) {
        if (str == null) {
            str = "normal_close";
        }
        if (this.mCurrentStatus == -1) {
            return;
        }
        cancelReconnect();
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null && !webSocket.close(i, str)) {
            Log.e("websocket", "服务器连接失败");
        }
        setCurrentStatus(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void buildConnect() {
        isNetworkConnected(this.mContext);
        int currentStatus = getCurrentStatus();
        if (currentStatus != 0 && currentStatus != 1) {
            setCurrentStatus(0);
            initWebSocket();
        }
    }

    public boolean sendMessage(String str) {
        return send(str);
    }

    public boolean sendMessage(ByteString byteString) {
        return send(byteString);
    }

    private boolean send(Object obj) {
        WebSocket webSocket = this.mWebSocket;
        boolean z = false;
        if (webSocket != null && this.mCurrentStatus == 1) {
            if (obj instanceof String) {
                z = webSocket.send((String) obj);
            } else if (obj instanceof ByteString) {
                z = webSocket.send((ByteString) obj);
            }
            if (!z) {
                tryReconnect();
            }
        }
        return z;
    }

    @SuppressLint({"MissingPermission"})
    private boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isAvailable();
    }

    /* renamed from: com.gen.mh.webapp_extensions.websocket.WsManager$Builder */
    /* loaded from: classes2.dex */
    public static final class Builder {
        private Context mContext;
        private OkHttpClient mOkHttpClient;
        private boolean needReconnect = true;
        private String wsUrl;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setWsUrl(String str) {
            this.wsUrl = str;
            return this;
        }

        public Builder setNeedReconnect(boolean z) {
            this.needReconnect = z;
            return this;
        }

        public Builder setmOkHttpClient(OkHttpClient okHttpClient) {
            this.mOkHttpClient = okHttpClient;
            return this;
        }

        public WsManager build() {
            return new WsManager(this);
        }
    }
}
