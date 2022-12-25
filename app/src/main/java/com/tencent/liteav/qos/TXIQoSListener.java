package com.tencent.liteav.qos;

/* renamed from: com.tencent.liteav.qos.a */
/* loaded from: classes3.dex */
public interface TXIQoSListener {
    void onEnableDropStatusChanged(boolean z);

    void onEncoderParamsChanged(int i, int i2, int i3);

    int onGetEncoderRealBitrate();

    int onGetQueueInputSize();

    int onGetQueueOutputSize();

    int onGetVideoDropCount();

    int onGetVideoQueueCurrentCount();

    int onGetVideoQueueMaxCount();
}
