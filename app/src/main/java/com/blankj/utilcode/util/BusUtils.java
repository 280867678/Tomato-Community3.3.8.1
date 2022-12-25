package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class BusUtils {
    private static ConcurrentHashMap<String, MessageCallback> subscribers = new ConcurrentHashMap<>();

    /* loaded from: classes2.dex */
    public interface MessageCallback {
        void onMsgCallBack(Bundle bundle);
    }

    static {
        new HashMap();
        new HashMap();
        new HashSet();
        new HashMap();
    }

    /* loaded from: classes2.dex */
    public static class ServerService extends Service {
        private final ConcurrentHashMap<Integer, Messenger> mClientMap = new ConcurrentHashMap<>();
        @SuppressLint({"HandlerLeak"})
        private final Handler mReceiveClientMsgHandler = new Handler() { // from class: com.blankj.utilcode.util.BusUtils.ServerService.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    ServerService.this.mClientMap.put(Integer.valueOf(message.arg1), message.replyTo);
                } else if (i == 1) {
                    ServerService.this.mClientMap.remove(Integer.valueOf(message.arg1));
                } else if (i == 2) {
                    ServerService.this.sendMsg2Client(message);
                    ServerService.this.consumeServerProcessCallback(message);
                } else {
                    super.handleMessage(message);
                }
            }
        };
        private final Messenger messenger = new Messenger(this.mReceiveClientMsgHandler);

        @Override // android.app.Service
        @Nullable
        public IBinder onBind(Intent intent) {
            return this.messenger.getBinder();
        }

        @Override // android.app.Service
        public int onStartCommand(Intent intent, int i, int i2) {
            Bundle extras;
            if (intent != null && (extras = intent.getExtras()) != null) {
                Message obtain = Message.obtain(this.mReceiveClientMsgHandler, 2);
                obtain.replyTo = this.messenger;
                obtain.setData(extras);
                sendMsg2Client(obtain);
                consumeServerProcessCallback(obtain);
            }
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendMsg2Client(Message message) {
            for (Messenger messenger : this.mClientMap.values()) {
                if (messenger != null) {
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void consumeServerProcessCallback(Message message) {
            String string;
            MessageCallback messageCallback;
            Bundle data = message.getData();
            if (data == null || (string = data.getString("MESSENGER_UTILS")) == null || (messageCallback = (MessageCallback) BusUtils.subscribers.get(string)) == null) {
                return;
            }
            messageCallback.onMsgCallBack(data);
        }
    }
}
