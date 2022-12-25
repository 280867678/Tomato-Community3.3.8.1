package com.tomatolive.library.websocket.interfaces;

import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.SocketMessageEvent;

/* loaded from: classes4.dex */
public interface BackgroundSocketCallBack extends SocketCallBack {
    void onBackThreadReceiveBigAnimMsg(GiftItemEntity giftItemEntity);

    void onBackThreadReceiveMessage(SocketMessageEvent socketMessageEvent);
}
