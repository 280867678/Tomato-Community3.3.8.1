package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.StateManager */
/* loaded from: classes2.dex */
class StateManager {
    private CloseInitiator mCloseInitiator = CloseInitiator.NONE;
    private WebSocketState mState = WebSocketState.CREATED;

    /* renamed from: com.example.websocket.com.neovisionaries.ws.client.StateManager$CloseInitiator */
    /* loaded from: classes2.dex */
    enum CloseInitiator {
        NONE,
        SERVER,
        CLIENT
    }

    public WebSocketState getState() {
        return this.mState;
    }

    public void setState(WebSocketState webSocketState) {
        this.mState = webSocketState;
    }

    public void changeToClosing(CloseInitiator closeInitiator) {
        this.mState = WebSocketState.CLOSING;
        if (this.mCloseInitiator == CloseInitiator.NONE) {
            this.mCloseInitiator = closeInitiator;
        }
    }

    public boolean getClosedByServer() {
        return this.mCloseInitiator == CloseInitiator.SERVER;
    }
}
