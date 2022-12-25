package com.tomatolive.library.websocket.interfaces;

/* loaded from: classes4.dex */
public interface OnWebSocketStatusListener {
    void onClose();

    void onError(boolean z, String str);

    void onOpen(boolean z);

    void reConnectCountOver();

    void reConnecting();
}
