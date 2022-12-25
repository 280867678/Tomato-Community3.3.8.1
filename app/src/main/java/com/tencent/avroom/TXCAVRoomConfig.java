package com.tencent.avroom;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class TXCAVRoomConfig {
    public static final int AVROOM_VIDEO_ASPECT_1_1 = 3;
    public static final int AVROOM_VIDEO_ASPECT_3_4 = 2;
    public static final int AVROOM_VIDEO_ASPECT_9_16 = 1;
    private static int LOCAL_RENDER_MODE;
    private static int REMOTE_RENDER_MODE;
    private boolean enableVideoHWAcceleration = true;
    private int homeOrientation = TXCAVRoomConstants.AVROOM_HOME_ORIENTATION_DOWN;
    private int captureFPS = 15;
    private int videoAspect = 1;
    private int videoBitrate = 600;
    private boolean frontCamera = true;
    private int pauseFps = 5;
    private Bitmap pauseImg = null;
    private int pauseFlag = 3;
    private boolean enablePureAudioPush = false;
    private boolean isHasVideo = false;

    public boolean isHasVideo() {
        return this.isHasVideo;
    }

    public void setHasVideo(boolean z) {
        this.isHasVideo = z;
    }

    public boolean isEnableVideoHWAcceleration() {
        return this.enableVideoHWAcceleration;
    }

    public TXCAVRoomConfig enableVideoHWAcceleration(boolean z) {
        this.enableVideoHWAcceleration = z;
        return this;
    }

    public int getHomeOrientation() {
        return this.homeOrientation;
    }

    public TXCAVRoomConfig homeOrientation(int i) {
        this.homeOrientation = i;
        return this;
    }

    public int getCaptureVideoFPS() {
        return this.captureFPS;
    }

    public TXCAVRoomConfig setCaptureVideoFPS(int i) {
        this.captureFPS = i;
        return this;
    }

    public int getVideoAspect() {
        return this.videoAspect;
    }

    public TXCAVRoomConfig VideoAspect(int i) {
        this.videoAspect = i;
        return this;
    }

    public int getVideoBitrate() {
        return this.videoBitrate;
    }

    public TXCAVRoomConfig videoBitrate(int i) {
        this.videoBitrate = i;
        return this;
    }

    public boolean isFrontCamera() {
        return this.frontCamera;
    }

    public TXCAVRoomConfig frontCamera(boolean z) {
        this.frontCamera = z;
        return this;
    }

    public int getPauseFps() {
        return this.pauseFps;
    }

    public TXCAVRoomConfig pauseFps(int i) {
        this.pauseFps = i;
        return this;
    }

    public boolean isEnablePureAudioPush() {
        return this.enablePureAudioPush;
    }

    public TXCAVRoomConfig enablePureAudioPush(boolean z) {
        this.enablePureAudioPush = z;
        return this;
    }

    public Bitmap getPauseImg() {
        return this.pauseImg;
    }

    public TXCAVRoomConfig pauseImg(Bitmap bitmap) {
        this.pauseImg = bitmap;
        return this;
    }

    public int getLocalRenderMode() {
        return LOCAL_RENDER_MODE;
    }

    public TXCAVRoomConfig setLocalRenderMode(int i) {
        LOCAL_RENDER_MODE = i;
        return this;
    }

    public int getRemoteRenderMode() {
        return REMOTE_RENDER_MODE;
    }

    public TXCAVRoomConfig setRemoteRenderMode(int i) {
        REMOTE_RENDER_MODE = i;
        return this;
    }

    public void setPauseFlag(int i) {
        this.pauseFlag = i;
    }

    public int getPauseFlag() {
        return this.pauseFlag;
    }
}
