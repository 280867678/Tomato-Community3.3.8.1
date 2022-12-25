package com.tencent.liteav.videoencoder;

/* loaded from: classes3.dex */
public class TXSVideoEncoderParam {
    public int width = 360;
    public int height = 640;
    public int fps = 20;
    public int gop = 3;
    public int encoderProfile = 1;
    public int encoderMode = 1;
    public boolean enableBFrame = false;
    public Object glContext = null;
    public boolean realTime = false;
    public boolean annexb = false;
    public boolean appendSpsPps = true;
    public boolean fullIFrame = false;
    public boolean syncOutput = false;
    public boolean enableEGL14 = false;
    public boolean enableBlackList = true;
    public boolean record = false;
}
