package com.tomatolive.library.websocket.nvwebsocket;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes4.dex */
public class WsSender implements Handler.Callback {
    private static final String SEND_HEAT = "ping";
    private Handler handler;
    private HandlerThread handlerThread;
    private boolean hasHeartBeatMessage;
    private ConcurrentLinkedQueue<String> sendQueue;
    private WsManager wsManager;

    public WsSender(WsManager wsManager) {
        if (wsManager == null) {
            throw new NullPointerException("SocketController can not be null!");
        }
        this.wsManager = wsManager;
        this.sendQueue = new ConcurrentLinkedQueue<>();
    }

    private void notifyMessage(int i) {
        Handler handler;
        if (this.sendQueue.isEmpty() || (handler = this.handler) == null) {
            return;
        }
        if (i == 0) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessageDelayed(0, i);
        }
    }

    public void startSendThread() {
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            this.handlerThread = new HandlerThread("Socket-SendThread");
            this.handlerThread.setPriority(4);
            this.handlerThread.start();
            this.handler = new Handler(this.handlerThread.getLooper(), this);
        }
    }

    public void stopSendThread() {
        this.sendQueue.clear();
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        try {
            if (this.handlerThread == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 18) {
                this.handlerThread.quitSafely();
            } else {
                this.handlerThread.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSocketMsg(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (TextUtils.equals(SEND_HEAT, str)) {
                if (this.hasHeartBeatMessage) {
                    return;
                }
                this.hasHeartBeatMessage = true;
            }
            this.sendQueue.offer(str);
            notifyMessage(0);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        try {
            if (this.wsManager.getSocketStatus() == WsStatus.CONNECT_SUCCESS) {
                String poll = this.sendQueue.poll();
                if (TextUtils.equals(SEND_HEAT, poll)) {
                    this.hasHeartBeatMessage = false;
                }
                this.wsManager.doSendMessage(poll);
                notifyMessage(0);
                return true;
            }
            this.sendQueue.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
