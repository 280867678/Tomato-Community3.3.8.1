package com.tencent.rtmp;

import android.graphics.Bitmap;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;

/* loaded from: classes3.dex */
public class TXLivePushConfig {
    public static final int DEFAULT_MAX_VIDEO_BITRATE = 1200;
    public static final int DEFAULT_MIN_VIDEO_BITRATE = 800;
    int mAudioBitrate;
    String mAudioPreProcessFuncName;
    String mAudioPreProcessLibrary;
    String mVideoPreProcessFuncName;
    String mVideoPreProcessLibrary;
    Bitmap mWatermark;
    int mCustomModeType = 0;
    int mAudioSample = TXRecordCommon.AUDIO_SAMPLERATE_48000;
    int mAudioChannels = 1;
    int mVideoFPS = 20;
    int mVideoResolution = 1;
    int mVideoBitrate = DEFAULT_MAX_VIDEO_BITRATE;
    int mMaxVideoBitrate = 1000;
    int mMinVideoBitrate = LotteryDialog.MAX_VALUE;
    int mBeautyLevel = 0;
    int mWhiteningLevel = 0;
    int mRuddyLevel = 0;
    int mEyeScaleLevel = 0;
    int mFaceSlimLevel = 0;
    int mConnectRetryCount = 3;
    int mConnectRetryInterval = 3;
    int mWatermarkX = 0;
    int mWatermarkY = 0;
    float mWatermarkXF = 0.0f;
    float mWatermarkYF = 0.0f;
    float mWatermarkWidth = -1.0f;
    int mVideoEncodeGop = 3;
    boolean mVideoEncoderXMirror = false;
    boolean mEnableHighResolutionCapture = true;
    boolean mEnableVideoHardEncoderMainProfile = true;
    boolean mFrontCamera = true;
    boolean mEnableANS = false;
    boolean mAutoAdjustBitrate = false;
    int mAutoAdjustStrategy = 0;
    int mHardwareAccel = 2;
    boolean mTouchFocus = true;
    boolean mEnableZoom = false;
    int mHomeOrientation = 1;
    Bitmap mPauseImg = null;
    int mPauseTime = 300;
    int mPauseFps = 5;
    int mPauseFlag = 1;
    boolean mEnableAec = false;
    boolean mEnableAudioPreview = false;
    boolean mEnableScreenCaptureAutoRotate = false;
    boolean mEnablePureAudioPush = false;
    boolean mEnableNearestIP = true;
    int mRtmpChannelType = 0;

    public void enableScreenCaptureAutoRotate(boolean z) {
        this.mEnableScreenCaptureAutoRotate = z;
    }

    public void enableHighResolutionCaptureMode(boolean z) {
        this.mEnableHighResolutionCapture = z;
    }

    public void enableVideoHardEncoderMainProfile(boolean z) {
        this.mEnableVideoHardEncoderMainProfile = z;
    }

    public void setCustomModeType(int i) {
        this.mCustomModeType = i;
    }

    public void setAudioSampleRate(int i) {
        this.mAudioSample = i;
    }

    public void setAudioChannels(int i) {
        this.mAudioChannels = i;
    }

    public void setANS(boolean z) {
        this.mEnableANS = z;
    }

    public void setVideoFPS(int i) {
        this.mVideoFPS = i;
    }

    public void setVideoResolution(int i) {
        this.mVideoResolution = i;
    }

    public void setAutoAdjustStrategy(int i) {
        this.mAutoAdjustStrategy = i;
    }

    public void setVideoBitrate(int i) {
        this.mVideoBitrate = i;
    }

    public void setAutoAdjustBitrate(boolean z) {
        this.mAutoAdjustBitrate = z;
    }

    public void setMaxVideoBitrate(int i) {
        this.mMaxVideoBitrate = i;
    }

    public void setMinVideoBitrate(int i) {
        this.mMinVideoBitrate = i;
    }

    public void setBeautyFilter(int i, int i2, int i3) {
        this.mBeautyLevel = i;
        this.mWhiteningLevel = i2;
        this.mRuddyLevel = i3;
    }

    public void setEyeScaleLevel(int i) {
        this.mEyeScaleLevel = i;
    }

    public void setFaceSlimLevel(int i) {
        this.mFaceSlimLevel = i;
    }

    public void setHardwareAcceleration(int i) {
        if (i < 0) {
            i = 2;
        }
        if (i > 2) {
            i = 2;
        }
        this.mHardwareAccel = i;
    }

    public void setFrontCamera(boolean z) {
        this.mFrontCamera = z;
    }

    public void setConnectRetryCount(int i) {
        this.mConnectRetryCount = i;
    }

    public void setConnectRetryInterval(int i) {
        this.mConnectRetryInterval = i;
    }

    public void setTouchFocus(boolean z) {
        this.mTouchFocus = z;
    }

    public void setEnableZoom(boolean z) {
        this.mEnableZoom = z;
    }

    public void setWatermark(Bitmap bitmap, int i, int i2) {
        this.mWatermark = bitmap;
        this.mWatermarkX = i;
        this.mWatermarkY = i2;
    }

    public void setWatermark(Bitmap bitmap, float f, float f2, float f3) {
        this.mWatermark = bitmap;
        this.mWatermarkXF = f;
        this.mWatermarkYF = f2;
        this.mWatermarkWidth = f3;
    }

    public void setVideoEncodeGop(int i) {
        this.mVideoEncodeGop = i;
    }

    public void setVideoEncoderXMirror(boolean z) {
        this.mVideoEncoderXMirror = z;
    }

    public void setHomeOrientation(int i) {
        this.mHomeOrientation = i;
    }

    public void setCustomVideoPreProcessLibrary(String str, String str2) {
        this.mVideoPreProcessLibrary = str;
        this.mVideoPreProcessFuncName = str2;
    }

    public void setCustomAudioPreProcessLibrary(String str, String str2) {
        this.mAudioPreProcessLibrary = str;
        this.mAudioPreProcessFuncName = str2;
    }

    public void setPauseImg(int i, int i2) {
        this.mPauseTime = i;
        this.mPauseFps = i2;
    }

    public void setPauseImg(Bitmap bitmap) {
        this.mPauseImg = bitmap;
    }

    public void setPauseFlag(int i) {
        this.mPauseFlag = i;
    }

    public void enableAEC(boolean z) {
        this.mEnableAec = z;
    }

    public void enablePureAudioPush(boolean z) {
        this.mEnablePureAudioPush = z;
    }

    public void enableNearestIP(boolean z) {
        this.mEnableNearestIP = z;
    }

    public void setRtmpChannelType(int i) {
        this.mRtmpChannelType = i;
    }
}
