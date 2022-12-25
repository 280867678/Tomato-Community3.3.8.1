package com.tomatolive.library.websocket.nvwebsocket;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.websocket.com.neovisionaries.p057ws.client.WebSocket;
import com.example.websocket.com.neovisionaries.p057ws.client.WebSocketAdapter;
import com.example.websocket.com.neovisionaries.p057ws.client.WebSocketException;
import com.example.websocket.com.neovisionaries.p057ws.client.WebSocketFactory;
import com.example.websocket.com.neovisionaries.p057ws.client.WebSocketFrame;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.SendMessageEntity;
import com.tomatolive.library.utils.LogManager;
import com.tomatolive.library.websocket.interfaces.BackgroundSocketCallBack;
import com.tomatolive.library.websocket.interfaces.OnWebSocketStatusListener;
import com.tomatolive.library.websocket.nvwebsocket.WsManager;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class WsManager {
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final String SEND_HEAT = "ping";
    private volatile long HEART_BEAT_TIME;
    private BackgroundSocketCallBack backgroundSocketCallBack;
    private boolean isAnimFinished;
    private boolean isClose;
    private boolean isReconnect;
    private Runnable mReconnectTask;
    private WsStatus mStatus;
    private WebSocket mWebSocket;
    private Disposable onDisDisposable;
    private Disposable onErrorDisposable;
    private Handler reConnHandler;
    private HandlerThread reConnThread;
    private AtomicInteger reconnectCount;
    private ScheduledExecutorService scheduledExecutorService;
    private String socketUrl;
    private Disposable timeOut;
    private OnWebSocketStatusListener webSocketStatusListener;
    private WsReceiver wsReceiver;
    private WsSender wsSender;

    private WsManager() {
        this.HEART_BEAT_TIME = 30000L;
        this.mStatus = WsStatus.INIT;
        this.reconnectCount = new AtomicInteger(0);
        this.socketUrl = "";
        this.isClose = false;
        this.mReconnectTask = new Runnable() { // from class: com.tomatolive.library.websocket.nvwebsocket.WsManager.1
            @Override // java.lang.Runnable
            public void run() {
                WsManager.this.closeSocket();
                try {
                    WsManager.this.mWebSocket = new WebSocketFactory().createSocket(WsManager.this.socketUrl, WsManager.CONNECT_TIMEOUT).setFrameQueueSize(5).addListener(new WsListener()).connectAsynchronously();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
    }

    /* loaded from: classes4.dex */
    private static class SingletonHolder {
        private static final WsManager INSTANCE = new WsManager();

        private SingletonHolder() {
        }
    }

    public static WsManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setOnWebSocketListener(OnWebSocketStatusListener onWebSocketStatusListener) {
        this.webSocketStatusListener = onWebSocketStatusListener;
    }

    public WsStatus getSocketStatus() {
        return this.mStatus;
    }

    public void setOnBackgroundSocketCallBack(BackgroundSocketCallBack backgroundSocketCallBack) {
        this.backgroundSocketCallBack = backgroundSocketCallBack;
    }

    public void init(BackgroundSocketCallBack backgroundSocketCallBack, String str, long j) {
        try {
            if (!TextUtils.isEmpty(str)) {
                this.socketUrl = str;
            }
            this.isAnimFinished = true;
            this.isClose = false;
            this.isReconnect = false;
            this.HEART_BEAT_TIME = j;
            this.backgroundSocketCallBack = backgroundSocketCallBack;
            closeSocket();
            this.mWebSocket = new WebSocketFactory().createSocket(this.socketUrl, CONNECT_TIMEOUT).setFrameQueueSize(5).addListener(new WsListener()).connectAsynchronously();
            setStatus(WsStatus.CONNECTING);
            startService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isShutdown() {
        ScheduledExecutorService scheduledExecutorService = this.scheduledExecutorService;
        return scheduledExecutorService == null || scheduledExecutorService.isShutdown();
    }

    public void shutdownTimerTask() {
        ScheduledExecutorService scheduledExecutorService = this.scheduledExecutorService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHeartBeat() {
        if (isShutdown()) {
            this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() { // from class: com.tomatolive.library.websocket.nvwebsocket.-$$Lambda$WsManager$WX9gjTWFof2zWHKAlqp1iK-fqCA
                @Override // java.lang.Runnable
                public final void run() {
                    WsManager.this.lambda$sendHeartBeat$0$WsManager();
                }
            }, 0L, this.HEART_BEAT_TIME, TimeUnit.MILLISECONDS);
        }
    }

    public /* synthetic */ void lambda$sendHeartBeat$0$WsManager() {
        if (this.wsSender == null || getSocketStatus() != WsStatus.CONNECT_SUCCESS) {
            return;
        }
        this.wsSender.sendSocketMsg(SEND_HEAT);
    }

    private void stopHeartBeat() {
        shutdownTimerTask();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class WsListener extends WebSocketAdapter {
        WsListener() {
        }

        @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketAdapter, com.example.websocket.com.neovisionaries.p057ws.client.WebSocketListener
        public void onTextMessage(WebSocket webSocket, String str) throws Exception {
            super.onTextMessage(webSocket, str);
            if (WsManager.this.wsReceiver != null) {
                String DESDecrypt = EncryptUtil.DESDecrypt(TomatoLiveSDK.getSingleton().ENCRYPT_SOCKET_KEY, str);
                WsManager.this.wsReceiver.putMsg(DESDecrypt);
                LogManager.m236s("socket 接收：" + DESDecrypt);
            }
        }

        @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketAdapter, com.example.websocket.com.neovisionaries.p057ws.client.WebSocketListener
        public void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception {
            super.onConnected(webSocket, map);
            WsManager.this.sendHeartBeat();
            WsManager.this.setStatus(WsStatus.CONNECT_SUCCESS);
            WsManager.this.cancelReconnect();
            if (WsManager.this.webSocketStatusListener != null) {
                WsManager.this.webSocketStatusListener.onOpen(WsManager.this.isReconnect);
            }
        }

        @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketAdapter, com.example.websocket.com.neovisionaries.p057ws.client.WebSocketListener
        public void onConnectError(WebSocket webSocket, WebSocketException webSocketException) throws Exception {
            super.onConnectError(webSocket, webSocketException);
            WsManager.this.setStatus(WsStatus.CONNECT_FAIL);
            if (WsManager.this.webSocketStatusListener != null) {
                WsManager.this.webSocketStatusListener.onError(WsManager.this.isReconnect, webSocketException.getLocalizedMessage());
            }
            if (!WsManager.this.isClose) {
                WsManager.this.onErrorDisposable = Observable.timer(2L, TimeUnit.SECONDS).subscribe(new Consumer() { // from class: com.tomatolive.library.websocket.nvwebsocket.-$$Lambda$WsManager$WsListener$Pr4K55XHgSfrgvZoqMYiiRb67cc
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        WsManager.WsListener.this.lambda$onConnectError$0$WsManager$WsListener((Long) obj);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onConnectError$0$WsManager$WsListener(Long l) throws Exception {
            WsManager.this.reconnect();
        }

        @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketAdapter, com.example.websocket.com.neovisionaries.p057ws.client.WebSocketListener
        public void onDisconnected(WebSocket webSocket, WebSocketFrame webSocketFrame, WebSocketFrame webSocketFrame2, boolean z) throws Exception {
            WsManager.this.setStatus(WsStatus.CONNECT_FAIL);
            if (!WsManager.this.isClose) {
                if (WsManager.this.webSocketStatusListener != null) {
                    WsManager.this.webSocketStatusListener.onError(WsManager.this.isReconnect, "onDisconnected");
                }
                WsManager.this.onDisDisposable = Observable.timer(2L, TimeUnit.SECONDS).subscribe(new Consumer() { // from class: com.tomatolive.library.websocket.nvwebsocket.-$$Lambda$WsManager$WsListener$hOehheJRsFXpOlTLeEdeZByycLc
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        WsManager.WsListener.this.lambda$onDisconnected$1$WsManager$WsListener((Long) obj);
                    }
                });
            } else if (WsManager.this.webSocketStatusListener != null) {
                WsManager.this.webSocketStatusListener.onClose();
            }
            super.onDisconnected(webSocket, webSocketFrame, webSocketFrame2, z);
        }

        public /* synthetic */ void lambda$onDisconnected$1$WsManager$WsListener(Long l) throws Exception {
            WsManager.this.reconnect();
        }
    }

    public void setStatus(WsStatus wsStatus) {
        this.mStatus = wsStatus;
    }

    public void reconnect() {
        if (!NetworkUtils.isConnected()) {
            this.reconnectCount.set(0);
            setStatus(WsStatus.CONNECT_FAIL);
            OnWebSocketStatusListener onWebSocketStatusListener = this.webSocketStatusListener;
            if (onWebSocketStatusListener == null) {
                return;
            }
            onWebSocketStatusListener.reConnectCountOver();
        } else if (getSocketStatus() != WsStatus.CONNECT_FAIL) {
        } else {
            stopHeartBeat();
            this.reconnectCount.incrementAndGet();
            if (this.reconnectCount.get() > 1) {
                OnWebSocketStatusListener onWebSocketStatusListener2 = this.webSocketStatusListener;
                if (onWebSocketStatusListener2 == null) {
                    return;
                }
                onWebSocketStatusListener2.reConnectCountOver();
                return;
            }
            setStatus(WsStatus.CONNECTING);
            this.isReconnect = true;
            if (this.webSocketStatusListener == null) {
                return;
            }
            closeSocket();
            this.webSocketStatusListener.reConnecting();
        }
    }

    public void reconnect(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.socketUrl = str;
        }
        if (this.timeOut == null) {
            this.timeOut = Observable.timer(5L, TimeUnit.SECONDS).observeOn(Schedulers.m90io()).subscribe(new Consumer() { // from class: com.tomatolive.library.websocket.nvwebsocket.-$$Lambda$WsManager$64nPbQ_865dJabHj1SODpv6JoVI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    WsManager.this.lambda$reconnect$1$WsManager((Long) obj);
                }
            });
        }
        this.reConnHandler.post(this.mReconnectTask);
    }

    public /* synthetic */ void lambda$reconnect$1$WsManager(Long l) throws Exception {
        if (getSocketStatus() == WsStatus.CONNECTING) {
            this.reconnectCount.set(0);
            stopHeartBeat();
            setStatus(WsStatus.CONNECT_FAIL);
            OnWebSocketStatusListener onWebSocketStatusListener = this.webSocketStatusListener;
            if (onWebSocketStatusListener != null) {
                onWebSocketStatusListener.reConnectCountOver();
            }
        }
        this.timeOut = null;
    }

    private void startService() {
        if (this.wsSender == null) {
            this.wsSender = new WsSender(this);
            this.wsSender.startSendThread();
        }
        if (this.wsReceiver == null) {
            this.wsReceiver = new WsReceiver(this);
            this.wsReceiver.startReceiveThread();
        }
        HandlerThread handlerThread = this.reConnThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            this.reConnThread = new HandlerThread("reConn");
            this.reConnThread.setPriority(4);
            this.reConnThread.start();
            this.reConnHandler = new Handler(this.reConnThread.getLooper());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelReconnect() {
        this.reconnectCount.set(0);
        this.reConnHandler.removeCallbacks(this.mReconnectTask);
    }

    public void doSendMessage(String str) {
        WebSocket webSocket;
        if (str == null || (webSocket = this.mWebSocket) == null || !webSocket.isOpen()) {
            return;
        }
        try {
            LogManager.m236s("socket 发送：" + str);
            if (TextUtils.equals(str, SEND_HEAT)) {
                this.mWebSocket.sendText(str);
            } else {
                this.mWebSocket.sendText(EncryptUtil.DESEncrypt(TomatoLiveSDK.getSingleton().ENCRYPT_SOCKET_KEY, str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        if (this.mWebSocket != null) {
            setStatus(WsStatus.CLOSE);
            this.mWebSocket.clearListeners();
            this.mWebSocket.disconnect();
            this.mWebSocket = null;
        }
    }

    public BackgroundSocketCallBack getBackgroundSocketCallBack() {
        return this.backgroundSocketCallBack;
    }

    public void setAnimFinish(boolean z) {
        this.isAnimFinished = z;
    }

    public boolean isBigAnimFinished() {
        return this.isAnimFinished;
    }

    private void closeDisposable(Disposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        disposable.dispose();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0056, code lost:
        if (r4.webSocketStatusListener != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006b, code lost:
        closeSocket();
        r4.isClose = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0069, code lost:
        r4.webSocketStatusListener = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0067, code lost:
        if (r4.webSocketStatusListener == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void stopService() {
        try {
            try {
                closeDisposable(this.timeOut);
                this.timeOut = null;
                closeDisposable(this.onDisDisposable);
                closeDisposable(this.onErrorDisposable);
                if (this.reConnThread != null) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        this.reConnThread.quitSafely();
                    } else {
                        this.reConnThread.quit();
                    }
                }
                if (this.reConnHandler != null) {
                    this.reConnHandler.removeCallbacksAndMessages(null);
                    this.reConnHandler = null;
                }
                stopHeartBeat();
                this.scheduledExecutorService = null;
                if (this.wsSender != null) {
                    this.wsSender.stopSendThread();
                    this.wsSender = null;
                }
                if (this.wsReceiver != null) {
                    this.wsReceiver.stopReceiveThread();
                    this.wsReceiver = null;
                }
                if (this.backgroundSocketCallBack != null) {
                    this.backgroundSocketCallBack = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (this.backgroundSocketCallBack != null) {
                    this.backgroundSocketCallBack = null;
                }
            }
        } catch (Throwable th) {
            if (this.backgroundSocketCallBack != null) {
                this.backgroundSocketCallBack = null;
            }
            if (this.webSocketStatusListener != null) {
                this.webSocketStatusListener = null;
            }
            closeSocket();
            this.isClose = true;
            throw th;
        }
    }

    public void clearAnimQueue() {
        WsReceiver wsReceiver = this.wsReceiver;
        if (wsReceiver != null) {
            wsReceiver.clearAnimQueue();
        }
    }

    public void addReceiveBigAnim(GiftItemEntity giftItemEntity) {
        WsReceiver wsReceiver = this.wsReceiver;
        if (wsReceiver != null) {
            wsReceiver.putReceiveBigAnim(giftItemEntity);
        }
    }

    public void addLocalAnim(GiftItemEntity giftItemEntity) {
        WsReceiver wsReceiver = this.wsReceiver;
        if (wsReceiver != null) {
            wsReceiver.putLocalAnimMsg(giftItemEntity);
        }
    }

    public void notifyAnim() {
        WsReceiver wsReceiver = this.wsReceiver;
        if (wsReceiver != null) {
            wsReceiver.notifyAnim();
        }
    }

    public void notifyBigAnim() {
        this.isAnimFinished = true;
        WsReceiver wsReceiver = this.wsReceiver;
        if (wsReceiver != null) {
            wsReceiver.notifyBigAnim();
        }
    }

    public void resetCount() {
        this.reconnectCount.set(0);
    }

    public void sendChatMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeChatMessage(sendMessageEntity));
    }

    public void sendNotifyMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeNotifyMessage(sendMessageEntity));
    }

    public void sendGiftMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeGiftMessage(sendMessageEntity));
    }

    public void sendEnterMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeEnterMessage(sendMessageEntity));
    }

    public void sendLeaveMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeLeaveMessage(sendMessageEntity));
    }

    public void sendShieldMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeShieldMessage(sendMessageEntity));
    }

    public void sendPostIntervalMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makePostIntervalMessage(sendMessageEntity));
    }

    public void sendSpeakLevelMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeSpeakLevelMessage(sendMessageEntity));
    }

    public void sendBannedAllMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeBannedAllMessage(sendMessageEntity));
    }

    public void sendBannedMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeBannedMessage(sendMessageEntity));
    }

    public void sendSuperBannedMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeSuperBannedMessage(sendMessageEntity));
    }

    public void sendSuperGoOutMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeSuperGoOutMessage(sendMessageEntity));
    }

    public void sendGrabGiftBoxMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeGrabGiftBoxMessage(sendMessageEntity));
    }

    public void sendCtrlMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeCtrlMessage(sendMessageEntity));
    }

    public void sendKickOutMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeGoOutMessage(sendMessageEntity));
    }

    public void sendPropSendMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makePropSendMessage(sendMessageEntity));
    }

    public void sendChatReceiptMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeChatReceiptMessage(sendMessageEntity));
    }

    public void sendUserPrivateMsgReceiptMessage(SendMessageEntity sendMessageEntity) {
        if (this.wsSender == null || sendMessageEntity == null) {
            return;
        }
        this.wsSender.sendSocketMsg(MessageHelper.makeUserPrivateMsgMessage(sendMessageEntity));
    }
}
