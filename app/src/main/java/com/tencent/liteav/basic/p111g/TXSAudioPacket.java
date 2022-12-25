package com.tencent.liteav.basic.p111g;

/* renamed from: com.tencent.liteav.basic.g.a */
/* loaded from: classes3.dex */
public class TXSAudioPacket implements Cloneable {
    public byte[] audioData;
    public int bitsPerChannel;
    public int channelsPerSample;
    public int packetType;
    public int sampleRate;
    public long timestamp;

    public Object clone() {
        try {
            return (TXSAudioPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
