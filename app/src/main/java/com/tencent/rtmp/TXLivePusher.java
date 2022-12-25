package com.tencent.rtmp;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.p002v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.Surface;
import com.tencent.liteav.TXCCaptureAndEnc;
import com.tencent.liteav.TXCDataReport;
import com.tencent.liteav.TXCLivePushConfig;
import com.tencent.liteav.TXICustomPreprocessListenner;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXCAudioUGCRecorder;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.p107c.LicenceCheck;
import com.tencent.liteav.basic.p107c.LicenceInfo;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXITakePhotoListener;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.network.TXCStreamUploader;
import com.tencent.liteav.network.TXSStreamUploaderParam;
import com.tencent.liteav.qos.TXCQoS;
import com.tencent.liteav.qos.TXIQoSListener;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class TXLivePusher implements TXINotifyListener, TXCCaptureAndEnc.AbstractC3410a, TXIQoSListener, TXICustomPreprocessListenner {
    public static final int RGB_BGRA = 4;
    public static final int RGB_RGBA = 5;
    private static final byte SEI_MSG_TYPE = -14;
    private static final String TAG = "TXLivePusher";
    public static final int YUV_420P = 3;
    public static final int YUV_420SP = 1;
    public static final int YUV_420YpCbCr = 2;
    private AudioCustomProcessListener mAudioProcessListener;
    private TXCCaptureAndEnc mCaptureAndEnc;
    private Context mContext;
    private Handler mMainHandler;
    private TXCLivePushConfig mNewConfig;
    private VideoCustomProcessListener mPreprocessListener;
    private TXRecordCommon.ITXVideoRecordListener mRecordListener;
    private TXCloudVideoView mTXCloudVideoView;
    private TXLivePushConfig mConfig = null;
    private ITXLivePushListener mListener = null;
    private int mVideoQuality = -1;
    private TXCStreamUploader mStreamUploader = null;
    private TXCQoS mQos = null;
    private TXCDataReport mDataReport = null;
    private String mPushUrl = "";
    private String mID = "";
    private boolean mSnapshotRunning = false;
    private int mVoiceKind = -1;
    private int mVoiceEnvironment = -1;
    private float mBgmPitch = 0.0f;
    private ArrayList<MsgInfo> mMsgArray = new ArrayList<>();
    private Runnable mSnapShotTimeOutRunnable = new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.2
        @Override // java.lang.Runnable
        public void run() {
            TXLivePusher.this.mSnapshotRunning = false;
        }
    };
    private TXCMP4Muxer mMP4Muxer = null;
    private boolean mStartMuxer = false;
    private String mVideoFilePath = "";
    private long mRecordStartTime = 0;
    private boolean mIsRecording = false;
    private boolean mNotifyStatus = false;

    /* loaded from: classes3.dex */
    public interface AudioCustomProcessListener {
        void onRecordPcmData(byte[] bArr, long j, int i, int i2, int i3);

        void onRecordRawPcmData(byte[] bArr, long j, int i, int i2, int i3, boolean z);
    }

    /* loaded from: classes3.dex */
    public interface ITXSnapshotListener {
        void onSnapshot(Bitmap bitmap);
    }

    /* loaded from: classes3.dex */
    public interface OnBGMNotify {
        void onBGMComplete(int i);

        void onBGMProgress(long j, long j2);

        void onBGMStart();
    }

    /* loaded from: classes3.dex */
    public interface VideoCustomProcessListener {
        void onDetectFacePoints(float[] fArr);

        int onTextureCustomProcess(int i, int i2, int i3);

        void onTextureDestoryed();
    }

    private int getAdjustStrategy(boolean z, boolean z2) {
        if (z) {
            return z2 ? 1 : 0;
        }
        return -1;
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetVideoQueueMaxCount() {
        return 5;
    }

    /* loaded from: classes3.dex */
    private class MsgInfo {
        byte[] msg;

        /* renamed from: ts */
        long f5644ts;

        private MsgInfo() {
        }
    }

    public TXLivePusher(Context context) {
        this.mNewConfig = null;
        this.mCaptureAndEnc = null;
        this.mContext = null;
        this.mMainHandler = null;
        TXCLog.init();
        this.mNewConfig = new TXCLivePushConfig();
        this.mContext = context.getApplicationContext();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mCaptureAndEnc = new TXCCaptureAndEnc(this.mContext);
        this.mCaptureAndEnc.m2582a((TXINotifyListener) this);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
    }

    public void setConfig(TXLivePushConfig tXLivePushConfig) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setConfig " + tXLivePushConfig + ", " + this);
        if (tXLivePushConfig == null) {
            tXLivePushConfig = new TXLivePushConfig();
        }
        this.mConfig = tXLivePushConfig;
        transferConfig(tXLivePushConfig);
        applyConfig();
    }

    public TXLivePushConfig getConfig() {
        return this.mConfig;
    }

    public void setPushListener(ITXLivePushListener iTXLivePushListener) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setPushListener " + iTXLivePushListener);
        this.mListener = iTXLivePushListener;
    }

    public int startPusher(String str) {
        String str2 = TAG;
        TXCLog.m2915d(str2, "liteav_api startPusher " + this);
        if (TextUtils.isEmpty(str)) {
            String str3 = TAG;
            TXCLog.m2914e(str3, "start push error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty(this.mPushUrl) && isPushing()) {
            if (this.mPushUrl.equalsIgnoreCase(str)) {
                String str4 = TAG;
                TXCLog.m2911w(str4, "ignore start push when new url is the same with old url  " + this);
                return -1;
            }
            String str5 = TAG;
            TXCLog.m2911w(str5, " stop old push when new url is not the same with old url  " + this);
            stopPusher();
        }
        TXCLog.m2915d(TAG, "================================================================================================================================================");
        TXCLog.m2915d(TAG, "================================================================================================================================================");
        String str6 = TAG;
        TXCLog.m2915d(str6, "============= startPush pushUrl = " + str + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "=============");
        TXCLog.m2915d(TAG, "================================================================================================================================================");
        TXCLog.m2915d(TAG, "================================================================================================================================================");
        this.mPushUrl = str;
        updateId(this.mPushUrl);
        startNetworkModule();
        startEncoder();
        startQosModule();
        startDataReportModule();
        startStatusNotify();
        TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
        if (tXCloudVideoView == null) {
            return 0;
        }
        tXCloudVideoView.clearLog();
        return 0;
    }

    public void stopPusher() {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api stopPusher " + this);
        stopRecord();
        stopStatusNotify();
        stopDataReportModule();
        stopQosModule();
        stopEncoder();
        this.mNewConfig.f4287I = false;
        stopNetworkModule();
    }

    public void pausePusher() {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api pausePusher " + this);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2528g();
        }
    }

    public void resumePusher() {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api resumePusher " + this);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2525h();
        }
    }

    public boolean isPushing() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            return tXCCaptureAndEnc.m2522i();
        }
        return false;
    }

    public void startCameraPreview(TXCloudVideoView tXCloudVideoView) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api startCameraPreview " + tXCloudVideoView + ", " + this);
        setConfig(this.mConfig);
        if (this.mNewConfig.f4284F) {
            TXCLog.m2914e(TAG, "enable pure audio push , so can not start preview!");
            return;
        }
        TXCloudVideoView tXCloudVideoView2 = this.mTXCloudVideoView;
        if (tXCloudVideoView2 != tXCloudVideoView && tXCloudVideoView2 != null) {
            tXCloudVideoView2.removeVideoView();
        }
        this.mTXCloudVideoView = tXCloudVideoView;
        if (this.mCaptureAndEnc == null) {
            this.mCaptureAndEnc = new TXCCaptureAndEnc(this.mContext);
        }
        this.mCaptureAndEnc.m2582a((TXINotifyListener) this);
        this.mCaptureAndEnc.m2577a((TXCCaptureAndEnc.AbstractC3410a) this);
        this.mCaptureAndEnc.m2568a(tXCloudVideoView);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        TXLivePushConfig tXLivePushConfig = this.mConfig;
        tXCCaptureAndEnc.m2558b(tXLivePushConfig.mBeautyLevel, tXLivePushConfig.mWhiteningLevel, tXLivePushConfig.mRuddyLevel);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
    }

    public void stopCameraPreview(boolean z) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api stopCameraPreview " + z + ", " + this);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2566a(z);
    }

    public void setSurface(Surface surface) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setSurface " + surface);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2583a(surface);
    }

    public void setSurfaceSize(int i, int i2) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setSurfaceSize " + i + "," + i2);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2591a(i, i2);
    }

    public void switchCamera() {
        TXCLog.m2915d(TAG, "liteav_api switchCamera ");
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2520j();
    }

    public boolean turnOnFlashLight(boolean z) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api turnOnFlashLight " + z);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return false;
        }
        return tXCCaptureAndEnc.m2553b(z);
    }

    public void setMute(boolean z) {
        TXCStreamUploader tXCStreamUploader;
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setMute " + z);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2537d(z);
        }
        if (!this.mConfig.mEnablePureAudioPush || (tXCStreamUploader = this.mStreamUploader) == null) {
            return;
        }
        tXCStreamUploader.setAudioMute(z);
    }

    public void onLogRecord(String str) {
        TXCLog.m2915d("User", str);
    }

    public int sendCustomVideoData(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.mCaptureAndEnc != null) {
            if (i == 3) {
                i4 = 1;
            } else if (i != 5) {
                return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
            } else {
                i4 = 2;
            }
            return this.mCaptureAndEnc.m2564a(bArr, i4, i2, i3);
        }
        return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
    }

    public int sendCustomVideoTexture(int i, int i2, int i3) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        return tXCCaptureAndEnc != null ? tXCCaptureAndEnc.m2548c(i, i2, i3) : NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
    }

    public void sendCustomPCMData(byte[] bArr) {
        this.mCaptureAndEnc.m2565a(bArr);
    }

    public int getMaxZoom() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return 0;
        }
        return tXCCaptureAndEnc.m2514n();
    }

    public boolean setZoom(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setZoom " + i);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return false;
        }
        return tXCCaptureAndEnc.m2521i(i);
    }

    public void setFocusPosition(float f, float f2) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2593a(f, f2);
        }
    }

    public boolean setMirror(boolean z) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setMirror " + z);
        TXLivePushConfig tXLivePushConfig = this.mConfig;
        if (tXLivePushConfig != null) {
            tXLivePushConfig.setVideoEncoderXMirror(z);
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return false;
        }
        tXCCaptureAndEnc.m2532e(z);
        return true;
    }

    public void setExposureCompensation(float f) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setExposureCompensation " + f);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2551c(f);
    }

    public void setBGMNofify(OnBGMNotify onBGMNotify) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setBGMNofify " + onBGMNotify);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2569a(onBGMNotify);
        }
    }

    public boolean playBGM(String str) {
        String str2 = TAG;
        TXCLog.m2915d(str2, "liteav_api playBGM " + str);
        return this.mCaptureAndEnc.m2546c(str);
    }

    public boolean stopBGM() {
        TXCLog.m2915d(TAG, "liteav_api stopBGM ");
        return this.mCaptureAndEnc.m2513o();
    }

    public boolean pauseBGM() {
        TXCLog.m2915d(TAG, "liteav_api pauseBGM ");
        return this.mCaptureAndEnc.m2512p();
    }

    public boolean resumeBGM() {
        TXCLog.m2915d(TAG, "liteav_api resumeBGM ");
        return this.mCaptureAndEnc.m2511q();
    }

    public boolean setMicVolume(float f) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setMicVolume " + f);
        return this.mCaptureAndEnc.m2543d(f);
    }

    public boolean setBGMVolume(float f) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setBGMVolume " + f);
        return this.mCaptureAndEnc.m2535e(f);
    }

    public void setBgmPitch(float f) {
        this.mBgmPitch = f;
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2561b(f);
        }
    }

    public int getMusicDuration(String str) {
        return this.mCaptureAndEnc.m2538d(str);
    }

    public void startScreenCapture() {
        TXCLog.m2915d(TAG, "liteav_api startScreenCapture ");
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2518k();
    }

    public void stopScreenCapture() {
        TXCLog.m2915d(TAG, "liteav_api stopScreenCapture ");
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2516l();
    }

    public void setRenderRotation(int i) {
        TXCLog.m2915d(TAG, "liteav_api setRenderRotation ");
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2592a(i);
    }

    public void setVideoProcessListener(VideoCustomProcessListener videoCustomProcessListener) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setVideoProcessListener " + videoCustomProcessListener);
        this.mPreprocessListener = videoCustomProcessListener;
        if (this.mPreprocessListener == null) {
            TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
            if (tXCCaptureAndEnc == null) {
                return;
            }
            tXCCaptureAndEnc.m2571a((TXICustomPreprocessListenner) null);
            return;
        }
        TXCCaptureAndEnc tXCCaptureAndEnc2 = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc2 == null) {
            return;
        }
        tXCCaptureAndEnc2.m2571a((TXICustomPreprocessListenner) this);
    }

    public void setAudioProcessListener(AudioCustomProcessListener audioCustomProcessListener) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setAudioProcessListener " + audioCustomProcessListener);
        this.mAudioProcessListener = audioCustomProcessListener;
    }

    public void snapshot(final ITXSnapshotListener iTXSnapshotListener) {
        TXCCaptureAndEnc tXCCaptureAndEnc;
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api snapshot " + iTXSnapshotListener);
        if (this.mSnapshotRunning || iTXSnapshotListener == null || (tXCCaptureAndEnc = this.mCaptureAndEnc) == null) {
            return;
        }
        if (tXCCaptureAndEnc != null) {
            this.mSnapshotRunning = true;
            tXCCaptureAndEnc.m2581a(new TXITakePhotoListener() { // from class: com.tencent.rtmp.TXLivePusher.1
                @Override // com.tencent.liteav.basic.p109e.TXITakePhotoListener
                public void onTakePhotoComplete(Bitmap bitmap) {
                    TXLivePusher.this.postBitmapToMainThread(iTXSnapshotListener, bitmap);
                    TXLivePusher.this.mSnapshotRunning = false;
                    TXLivePusher.this.mMainHandler.removeCallbacks(TXLivePusher.this.mSnapShotTimeOutRunnable);
                }
            });
            this.mMainHandler.postDelayed(this.mSnapShotTimeOutRunnable, 2000L);
            return;
        }
        this.mSnapshotRunning = false;
    }

    public int startRecord(String str) {
        String str2 = TAG;
        TXCLog.m2915d(str2, "liteav_api startRecord " + str);
        if (Build.VERSION.SDK_INT < 18) {
            String str3 = TAG;
            TXCLog.m2914e(str3, "API levl is too low (record need 18, current is" + Build.VERSION.SDK_INT + ")");
            return -3;
        } else if (this.mIsRecording) {
            TXCLog.m2911w(TAG, "ignore start record when recording");
            return -1;
        } else {
            TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
            if (tXCCaptureAndEnc == null || !tXCCaptureAndEnc.m2522i()) {
                TXCLog.m2911w(TAG, "ignore start record when not pushing");
                return -2;
            }
            TXCLog.m2911w(TAG, "start record ");
            this.mIsRecording = true;
            this.mVideoFilePath = str;
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            this.mMP4Muxer = new TXCMP4Muxer(this.mContext, 2);
            this.mStartMuxer = false;
            this.mMP4Muxer.mo1231a(this.mVideoFilePath);
            addAudioTrack();
            TXCDRApi.txReportDAU(this.mContext.getApplicationContext(), TXCDRDef.f2431aH);
            TXCCaptureAndEnc tXCCaptureAndEnc2 = this.mCaptureAndEnc;
            if (tXCCaptureAndEnc2 != null) {
                tXCCaptureAndEnc2.m2510s();
            }
            return 0;
        }
    }

    public void stopRecord() {
        TXCMP4Muxer tXCMP4Muxer;
        TXCLog.m2915d(TAG, "liteav_api stopRecord ");
        if (!this.mIsRecording || (tXCMP4Muxer = this.mMP4Muxer) == null) {
            return;
        }
        int mo1227b = tXCMP4Muxer.mo1227b();
        TXCLog.m2911w(TAG, "start record ");
        this.mIsRecording = false;
        if (mo1227b == 0) {
            final String str = this.mVideoFilePath;
            AsyncTask.execute(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.3
                @Override // java.lang.Runnable
                public void run() {
                    File parentFile = new File(str).getParentFile();
                    String format = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date(System.currentTimeMillis()));
                    String str2 = parentFile + File.separator + String.format("TXUGCCover_%s.jpg", format);
                    TXCSystemUtil.m2887a(str, str2);
                    TXLivePusher.this.callbackRecordSuccess(str, str2);
                }
            });
            return;
        }
        callbackRecordFail();
    }

    public void setVideoRecordListener(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setVideoRecordListener " + iTXVideoRecordListener);
        this.mRecordListener = iTXVideoRecordListener;
    }

    public boolean setBeautyFilter(int i, int i2, int i3, int i4) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setBeautyFilter " + i + ", " + i2 + ", " + i3 + ", " + i4);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2560b(i);
            this.mCaptureAndEnc.m2558b(i2, i3, i4);
        }
        TXLivePushConfig tXLivePushConfig = this.mConfig;
        if (tXLivePushConfig != null) {
            tXLivePushConfig.mBeautyLevel = i2;
            tXLivePushConfig.mWhiteningLevel = i3;
            tXLivePushConfig.mRuddyLevel = i4;
            return true;
        }
        return true;
    }

    public void setFilter(Bitmap bitmap) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setFilter " + bitmap);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2585a(bitmap);
        }
    }

    public void setMotionTmpl(String str) {
        String str2 = TAG;
        TXCLog.m2915d(str2, "liteav_api motionPath " + str);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2567a(str);
        }
    }

    public void setMotionMute(boolean z) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setMotionMute " + z);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2545c(z);
        }
    }

    @TargetApi(18)
    public boolean setGreenScreenFile(String str) {
        String str2 = TAG;
        TXCLog.m2915d(str2, "liteav_api setGreenScreenFile " + str);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            return tXCCaptureAndEnc.m2554b(str);
        }
        return false;
    }

    public void setEyeScaleLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setEyeScaleLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXLivePushConfig tXLivePushConfig = this.mConfig;
        if (tXLivePushConfig != null) {
            tXLivePushConfig.setEyeScaleLevel(i);
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2550c(i);
        }
    }

    public void setFaceSlimLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setFaceSlimLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXLivePushConfig tXLivePushConfig = this.mConfig;
        if (tXLivePushConfig != null) {
            tXLivePushConfig.setFaceSlimLevel(i);
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2542d(i);
        }
    }

    public void setFaceVLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setFaceVLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2534e(i);
        }
    }

    public void setSpecialRatio(float f) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setSpecialRatio " + f);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2594a(f);
        }
    }

    public void setFaceShortLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setFaceShortLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2530f(i);
        }
    }

    public void setChinLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setChinLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2527g(i);
        }
    }

    public void setNoseSlimLevel(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setNoseSlimLevel " + i);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2524h(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x020f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVideoQuality(int i, boolean z, boolean z2) {
        boolean z3;
        TXCLivePushConfig tXCLivePushConfig;
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setVideoQuality " + i + ", " + z + ", " + z2);
        int i2 = 3;
        if (Build.VERSION.SDK_INT < 18 && (i == 2 || i == 3)) {
            i = 1;
        }
        if (this.mConfig == null) {
            this.mConfig = new TXLivePushConfig();
        }
        this.mConfig.setVideoFPS(18);
        boolean z4 = false;
        switch (i) {
            case 1:
                this.mConfig.enableAEC(false);
                this.mConfig.setHardwareAcceleration(2);
                this.mConfig.setVideoResolution(0);
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                setAdjustStrategy(z, z2);
                this.mConfig.setMinVideoBitrate(301);
                this.mConfig.setVideoBitrate(800);
                this.mConfig.setMaxVideoBitrate(800);
                z3 = false;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig = this.mConfig;
                if (z4) {
                    i2 = 1;
                }
                tXLivePushConfig.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                    tXCLivePushConfig.f4287I = z4;
                    tXCLivePushConfig.f4288J = z3;
                }
                setConfig(this.mConfig);
                return;
            case 2:
                this.mConfig.enableAEC(false);
                this.mConfig.setHardwareAcceleration(2);
                this.mConfig.setVideoResolution(1);
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                setAdjustStrategy(z, z2);
                this.mConfig.setMinVideoBitrate(600);
                this.mConfig.setVideoBitrate(TXLivePushConfig.DEFAULT_MAX_VIDEO_BITRATE);
                this.mConfig.setMaxVideoBitrate(1500);
                z3 = false;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig2 = this.mConfig;
                if (z4) {
                }
                tXLivePushConfig2.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                }
                setConfig(this.mConfig);
                return;
            case 3:
                this.mConfig.enableAEC(false);
                this.mConfig.setHardwareAcceleration(1);
                this.mConfig.setVideoResolution(2);
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                setAdjustStrategy(z, z2);
                this.mConfig.setMinVideoBitrate(600);
                this.mConfig.setVideoBitrate(1800);
                this.mConfig.setMaxVideoBitrate(1800);
                z3 = false;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig22 = this.mConfig;
                if (z4) {
                }
                tXLivePushConfig22.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                }
                setConfig(this.mConfig);
                return;
            case 4:
                if (Build.VERSION.SDK_INT < 18) {
                    this.mConfig.enableAEC(true);
                    this.mConfig.setHardwareAcceleration(0);
                    this.mConfig.setVideoResolution(0);
                    this.mConfig.setAutoAdjustBitrate(true);
                    this.mConfig.setAutoAdjustStrategy(4);
                    this.mConfig.setMinVideoBitrate(301);
                    this.mConfig.setVideoBitrate(800);
                    this.mConfig.setMaxVideoBitrate(800);
                } else {
                    int i3 = this.mVideoQuality;
                    if (i3 == 1) {
                        this.mConfig.enableAEC(true);
                        this.mConfig.setHardwareAcceleration(1);
                        this.mConfig.setVideoResolution(0);
                        this.mConfig.setAutoAdjustBitrate(true);
                        this.mConfig.setAutoAdjustStrategy(4);
                        this.mConfig.setMinVideoBitrate(301);
                        this.mConfig.setVideoBitrate(800);
                        this.mConfig.setMaxVideoBitrate(800);
                    } else if (i3 == 3) {
                        this.mConfig.enableAEC(true);
                        this.mConfig.setHardwareAcceleration(1);
                        this.mConfig.setVideoResolution(2);
                        this.mConfig.setAutoAdjustBitrate(true);
                        this.mConfig.setAutoAdjustStrategy(4);
                        this.mConfig.setMinVideoBitrate(600);
                        this.mConfig.setVideoBitrate(1800);
                        this.mConfig.setMaxVideoBitrate(1800);
                    } else {
                        this.mConfig.enableAEC(true);
                        this.mConfig.setHardwareAcceleration(1);
                        this.mConfig.setVideoResolution(1);
                        this.mConfig.setAutoAdjustBitrate(true);
                        this.mConfig.setAutoAdjustStrategy(4);
                        this.mConfig.setMinVideoBitrate(600);
                        this.mConfig.setVideoBitrate(TXLivePushConfig.DEFAULT_MAX_VIDEO_BITRATE);
                        this.mConfig.setMaxVideoBitrate(TXLivePushConfig.DEFAULT_MAX_VIDEO_BITRATE);
                    }
                }
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                z3 = false;
                z4 = true;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig222 = this.mConfig;
                if (z4) {
                }
                tXLivePushConfig222.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                }
                setConfig(this.mConfig);
                return;
            case 5:
                this.mConfig.enableAEC(true);
                this.mConfig.setHardwareAcceleration(1);
                this.mConfig.setVideoResolution(6);
                this.mConfig.setAutoAdjustBitrate(false);
                this.mConfig.setVideoBitrate(350);
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                z3 = false;
                z4 = true;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig2222 = this.mConfig;
                if (z4) {
                }
                tXLivePushConfig2222.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                }
                setConfig(this.mConfig);
                return;
            case 6:
                this.mConfig.enableAEC(true);
                this.mConfig.setHardwareAcceleration(1);
                this.mConfig.setVideoResolution(0);
                this.mConfig.setAudioSampleRate(TXRecordCommon.AUDIO_SAMPLERATE_48000);
                this.mConfig.setAutoAdjustBitrate(true);
                this.mConfig.setAutoAdjustStrategy(5);
                this.mConfig.setMinVideoBitrate(190);
                this.mConfig.setVideoBitrate(LotteryDialog.MAX_VALUE);
                this.mConfig.setMaxVideoBitrate(810);
                z3 = true;
                z4 = true;
                this.mVideoQuality = i;
                this.mConfig.enableVideoHardEncoderMainProfile(!z4);
                TXLivePushConfig tXLivePushConfig22222 = this.mConfig;
                if (z4) {
                }
                tXLivePushConfig22222.setVideoEncodeGop(i2);
                tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig != null) {
                }
                setConfig(this.mConfig);
                return;
            default:
                this.mConfig.setHardwareAcceleration(2);
                String str2 = TAG;
                TXCLog.m2914e(str2, "setVideoPushQuality: invalid quality " + i);
                return;
        }
    }

    public void setReverb(int i) {
        String str = TAG;
        TXCLog.m2915d(str, "liteav_api setReverb " + i);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2519j(i);
    }

    public void setVoiceChangerType(int i) {
        switch (i) {
            case 1:
                this.mVoiceKind = 6;
                this.mVoiceEnvironment = -1;
                break;
            case 2:
                this.mVoiceKind = 4;
                this.mVoiceEnvironment = -1;
                break;
            case 3:
                this.mVoiceKind = 5;
                this.mVoiceEnvironment = -1;
                break;
            case 4:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 9;
                break;
            case 5:
                this.mVoiceKind = 536936433;
                this.mVoiceEnvironment = 50;
                break;
            case 6:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 5;
                break;
            case 7:
                this.mVoiceKind = 13;
                this.mVoiceEnvironment = 1;
                break;
            case 8:
                this.mVoiceKind = 13;
                this.mVoiceEnvironment = -1;
                break;
            case 9:
                this.mVoiceKind = 10;
                this.mVoiceEnvironment = 4;
                break;
            case 10:
                this.mVoiceKind = 10;
                this.mVoiceEnvironment = 20;
                break;
            case 11:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 2;
                break;
            default:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = -1;
                break;
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2559b(this.mVoiceKind, this.mVoiceEnvironment);
        }
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(final int i, final Bundle bundle) {
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.4
                @Override // java.lang.Runnable
                public void run() {
                    if (TXLivePusher.this.mTXCloudVideoView != null) {
                        TXLivePusher.this.mTXCloudVideoView.setLogText(null, bundle, i);
                    }
                }
            });
        }
        transferPushEvent(i, bundle);
    }

    @Override // com.tencent.liteav.TXICustomPreprocessListenner
    public int onTextureCustomProcess(int i, int i2, int i3) {
        VideoCustomProcessListener videoCustomProcessListener = this.mPreprocessListener;
        if (videoCustomProcessListener != null) {
            return videoCustomProcessListener.onTextureCustomProcess(i, i2, i3);
        }
        return 0;
    }

    public void onDetectFacePoints(float[] fArr) {
        VideoCustomProcessListener videoCustomProcessListener = this.mPreprocessListener;
        if (videoCustomProcessListener != null) {
            videoCustomProcessListener.onDetectFacePoints(fArr);
        }
    }

    @Override // com.tencent.liteav.TXICustomPreprocessListenner
    public void onTextureDestoryed() {
        VideoCustomProcessListener videoCustomProcessListener = this.mPreprocessListener;
        if (videoCustomProcessListener != null) {
            videoCustomProcessListener.onTextureDestoryed();
        }
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetEncoderRealBitrate() {
        return TXCStatus.m2904d(this.mID, 4002);
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetQueueInputSize() {
        int m2904d;
        int m2904d2 = TXCStatus.m2904d(this.mID, 7002);
        if (this.mNewConfig.f4287I) {
            m2904d = TXCStatus.m2904d(this.mID, 7001);
        } else {
            m2904d = TXCStatus.m2904d(this.mID, 4002);
        }
        return m2904d2 + m2904d;
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetQueueOutputSize() {
        if (this.mStreamUploader == null) {
            return 0;
        }
        return TXCStatus.m2904d(this.mID, 7004) + TXCStatus.m2904d(this.mID, 7003);
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetVideoQueueCurrentCount() {
        return TXCStatus.m2904d(this.mID, 7005);
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public int onGetVideoDropCount() {
        return TXCStatus.m2904d(this.mID, 7007);
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public void onEncoderParamsChanged(int i, int i2, int i3) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2590a(i, i2, i3);
        }
        if (i2 != 0 && i3 != 0) {
            TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
            tXCLivePushConfig.f4293a = i2;
            tXCLivePushConfig.f4294b = i3;
        }
        if (i != 0) {
            this.mNewConfig.f4295c = i;
            String str = this.mPushUrl;
            int i4 = TXCDRDef.f2410N;
            TXCDRApi.reportEvent40003(str, i4, "Qos Result", "mode:" + this.mNewConfig.f4298f + " bitrate:" + i + " videosize:" + this.mNewConfig.f4293a + " * " + this.mNewConfig.f4294b);
        }
    }

    @Override // com.tencent.liteav.qos.TXIQoSListener
    public void onEnableDropStatusChanged(boolean z) {
        TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
        if (tXCStreamUploader != null) {
            tXCStreamUploader.setDropEanble(z);
        }
    }

    @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
    public void onEncAudio(byte[] bArr, long j, int i, int i2) {
        TXCMP4Muxer tXCMP4Muxer;
        TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
        if (tXCStreamUploader != null && bArr != null) {
            tXCStreamUploader.pushAAC(bArr, j);
        }
        if (!this.mIsRecording || (tXCMP4Muxer = this.mMP4Muxer) == null || !this.mStartMuxer || bArr == null) {
            return;
        }
        tXCMP4Muxer.mo1228a(bArr, 0, bArr.length, j * 1000, 0);
    }

    @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
    public void onEncVideo(TXSNALPacket tXSNALPacket) {
        byte[] bArr;
        TXCQoS tXCQoS = this.mQos;
        if (tXCQoS != null) {
            tXCQoS.setHasVideo(true);
        }
        if (this.mStreamUploader != null && tXSNALPacket != null && tXSNALPacket.nalData != null) {
            synchronized (this) {
                if (this.mMsgArray != null && !this.mMsgArray.isEmpty()) {
                    Iterator<MsgInfo> it2 = this.mMsgArray.iterator();
                    int i = 0;
                    while (true) {
                        int i2 = 10240;
                        if (!it2.hasNext()) {
                            break;
                        }
                        MsgInfo next = it2.next();
                        if (next.f5644ts > tXSNALPacket.pts) {
                            break;
                        }
                        if (next.msg.length <= 10240) {
                            i2 = next.msg.length;
                        }
                        i += i2 + 5;
                    }
                    if (i != 0) {
                        byte[] bArr2 = new byte[i + tXSNALPacket.nalData.length];
                        byte[] bArr3 = new byte[5];
                        Iterator<MsgInfo> it3 = this.mMsgArray.iterator();
                        int i3 = 0;
                        int i4 = 0;
                        while (it3.hasNext()) {
                            MsgInfo next2 = it3.next();
                            if (next2.f5644ts > tXSNALPacket.pts) {
                                break;
                            }
                            i3++;
                            int length = next2.msg.length <= 10240 ? next2.msg.length : 10240;
                            int i5 = length + 1;
                            bArr3[0] = (byte) ((i5 >> 24) & 255);
                            bArr3[1] = (byte) ((i5 >> 16) & 255);
                            bArr3[2] = (byte) ((i5 >> 8) & 255);
                            bArr3[3] = (byte) (i5 & 255);
                            bArr3[4] = 6;
                            System.arraycopy(bArr3, 0, bArr2, i4, bArr3.length);
                            int length2 = i4 + bArr3.length;
                            System.arraycopy(next2.msg, 0, bArr2, length2, length);
                            i4 = length2 + length;
                        }
                        long j = tXSNALPacket.pts;
                        for (int i6 = 0; i6 < i3; i6++) {
                            this.mMsgArray.remove(0);
                        }
                        System.arraycopy(tXSNALPacket.nalData, 0, bArr2, i4, tXSNALPacket.nalData.length);
                        tXSNALPacket.nalData = bArr2;
                    }
                }
            }
            this.mStreamUploader.pushNAL(tXSNALPacket);
        }
        if (!this.mIsRecording || this.mMP4Muxer == null || tXSNALPacket == null || (bArr = tXSNALPacket.nalData) == null) {
            return;
        }
        byte[] transferAvccToAnnexb = transferAvccToAnnexb(bArr);
        if (this.mStartMuxer) {
            recordVideoData(tXSNALPacket, transferAvccToAnnexb);
        } else if (tXSNALPacket.nalType == 0) {
            MediaFormat m2882a = TXCSystemUtil.m2882a(transferAvccToAnnexb, this.mCaptureAndEnc.m2562b(), this.mCaptureAndEnc.m2552c());
            if (m2882a != null) {
                this.mMP4Muxer.mo1232a(m2882a);
                this.mMP4Muxer.mo1235a();
                this.mStartMuxer = true;
                this.mRecordStartTime = 0L;
            }
            recordVideoData(tXSNALPacket, transferAvccToAnnexb);
        }
    }

    @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
    public void onRecordRawPcm(byte[] bArr, long j, int i, int i2, int i3, boolean z) {
        AudioCustomProcessListener audioCustomProcessListener = this.mAudioProcessListener;
        if (audioCustomProcessListener != null) {
            audioCustomProcessListener.onRecordRawPcmData(bArr, j, i, i2, i3, z);
        }
    }

    @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
    public void onRecordPcm(byte[] bArr, long j, int i, int i2, int i3) {
        AudioCustomProcessListener audioCustomProcessListener = this.mAudioProcessListener;
        if (audioCustomProcessListener != null) {
            audioCustomProcessListener.onRecordPcmData(bArr, j, i, i2, i3);
        }
    }

    @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
    public void onEncVideoFormat(MediaFormat mediaFormat) {
        TXCMP4Muxer tXCMP4Muxer;
        if (!this.mIsRecording || (tXCMP4Muxer = this.mMP4Muxer) == null) {
            return;
        }
        tXCMP4Muxer.mo1232a(mediaFormat);
        if (this.mStartMuxer) {
            return;
        }
        this.mMP4Muxer.mo1235a();
        this.mStartMuxer = true;
        this.mRecordStartTime = 0L;
    }

    private void callbackRecordFail() {
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.5
            @Override // java.lang.Runnable
            public void run() {
                TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                tXRecordResult.retCode = -1;
                tXRecordResult.descMsg = "record video failed";
                if (TXLivePusher.this.mRecordListener != null) {
                    TXLivePusher.this.mRecordListener.onRecordComplete(tXRecordResult);
                }
                TXCLog.m2911w(TXLivePusher.TAG, "record complete fail");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackRecordSuccess(final String str, final String str2) {
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.6
            @Override // java.lang.Runnable
            public void run() {
                TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                tXRecordResult.retCode = 0;
                tXRecordResult.descMsg = "record success";
                tXRecordResult.videoPath = str;
                tXRecordResult.coverPath = str2;
                if (TXLivePusher.this.mRecordListener != null) {
                    TXLivePusher.this.mRecordListener.onRecordComplete(tXRecordResult);
                }
                TXCLog.m2911w(TXLivePusher.TAG, "record complete success");
            }
        });
    }

    @TargetApi(16)
    private void addAudioTrack() {
        MediaFormat m2892a = TXCSystemUtil.m2892a(TXCAudioUGCRecorder.getInstance().getSampleRate(), TXCAudioUGCRecorder.getInstance().getChannels(), 2);
        TXCMP4Muxer tXCMP4Muxer = this.mMP4Muxer;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1226b(m2892a);
        }
    }

    private void recordVideoData(TXSNALPacket tXSNALPacket, byte[] bArr) {
        int i;
        if (this.mRecordStartTime == 0) {
            this.mRecordStartTime = tXSNALPacket.pts;
        }
        final long j = tXSNALPacket.pts - this.mRecordStartTime;
        MediaCodec.BufferInfo bufferInfo = tXSNALPacket.info;
        if (bufferInfo == null) {
            i = tXSNALPacket.nalType == 0 ? 1 : 0;
        } else {
            i = bufferInfo.flags;
        }
        this.mMP4Muxer.mo1224b(bArr, 0, bArr.length, tXSNALPacket.pts * 1000, i);
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.7
            @Override // java.lang.Runnable
            public void run() {
                if (TXLivePusher.this.mRecordListener != null) {
                    TXLivePusher.this.mRecordListener.onRecordProgress(j);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postBitmapToMainThread(final ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.8
            @Override // java.lang.Runnable
            public void run() {
                ITXSnapshotListener iTXSnapshotListener2 = iTXSnapshotListener;
                if (iTXSnapshotListener2 != null) {
                    iTXSnapshotListener2.onSnapshot(bitmap);
                }
            }
        });
    }

    private void setAdjustStrategy(boolean z, boolean z2) {
        int adjustStrategy = getAdjustStrategy(z, z2);
        if (adjustStrategy == -1) {
            this.mConfig.setAutoAdjustBitrate(false);
            this.mConfig.setAutoAdjustStrategy(-1);
            return;
        }
        this.mConfig.setAutoAdjustBitrate(true);
        this.mConfig.setAutoAdjustStrategy(adjustStrategy);
    }

    private void updateId(String str) {
        String format = String.format("%s-%d", str, Long.valueOf(TXCTimeUtil.getTimeTick() % 10000));
        TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
        if (tXCStreamUploader != null) {
            tXCStreamUploader.setID(format);
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.setID(format);
        }
        TXCDataReport tXCDataReport = this.mDataReport;
        if (tXCDataReport != null) {
            tXCDataReport.m2373d(format);
        }
        this.mID = format;
    }

    private void startStatusNotify() {
        this.mNotifyStatus = true;
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.9
                @Override // java.lang.Runnable
                public void run() {
                    if (TXLivePusher.this.mNotifyStatus) {
                        TXLivePusher.this.statusNotify();
                    }
                }
            }, 2000L);
        }
    }

    private void stopStatusNotify() {
        this.mNotifyStatus = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void statusNotify() {
        int[] m2894a = TXCSystemUtil.m2894a();
        String str = (m2894a[0] / 10) + "/" + (m2894a[1] / 10) + "%";
        int m2904d = TXCStatus.m2904d(this.mID, 7004);
        int m2904d2 = TXCStatus.m2904d(this.mID, 7003);
        int m2904d3 = TXCStatus.m2904d(this.mID, 7002);
        int m2904d4 = TXCStatus.m2904d(this.mID, 7001);
        int m2904d5 = TXCStatus.m2904d(this.mID, 7007);
        int m2904d6 = TXCStatus.m2904d(this.mID, 7005);
        int m2904d7 = TXCStatus.m2904d(this.mID, 7006);
        String m2905c = TXCStatus.m2905c(this.mID, 7012);
        double m2903e = TXCStatus.m2903e(this.mID, 4001);
        int m2904d8 = TXCStatus.m2904d(this.mID, 4003);
        Bundle bundle = new Bundle();
        bundle.putInt("NET_SPEED", m2904d2 + m2904d);
        bundle.putInt("VIDEO_FPS", (int) m2903e);
        if (m2903e < 1.0d) {
            m2903e = 15.0d;
        }
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) ((((m2904d8 * 10) / ((int) m2903e)) / 10.0f) + 0.5d));
        bundle.putInt("DROP_SIZE", m2904d5);
        bundle.putInt("VIDEO_BITRATE", m2904d4);
        bundle.putInt("AUDIO_BITRATE", m2904d3);
        bundle.putInt("CODEC_CACHE", m2904d7);
        bundle.putInt("CACHE_SIZE", m2904d6);
        bundle.putCharSequence("SERVER_IP", m2905c);
        bundle.putCharSequence("CPU_USAGE", str);
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, tXCCaptureAndEnc.m2544d());
            bundle.putInt("VIDEO_WIDTH", this.mCaptureAndEnc.m2562b());
            bundle.putInt("VIDEO_HEIGHT", this.mCaptureAndEnc.m2552c());
        }
        TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setLogText(bundle, null, 0);
        }
        ITXLivePushListener iTXLivePushListener = this.mListener;
        if (iTXLivePushListener != null) {
            iTXLivePushListener.onNetStatus(bundle);
        }
        TXCDataReport tXCDataReport = this.mDataReport;
        if (tXCDataReport != null) {
            tXCDataReport.m2372e();
        }
        Handler handler = this.mMainHandler;
        if (handler == null || !this.mNotifyStatus) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.10
            @Override // java.lang.Runnable
            public void run() {
                if (TXLivePusher.this.mNotifyStatus) {
                    TXLivePusher.this.statusNotify();
                }
            }
        }, 2000L);
    }

    private void startNetworkModule() {
        TXCCaptureAndEnc tXCCaptureAndEnc;
        TXSStreamUploaderParam tXSStreamUploaderParam = new TXSStreamUploaderParam();
        tXSStreamUploaderParam.f4913d = TXCAudioRecorder.m3456a().m3441d();
        tXSStreamUploaderParam.f4914e = TXCAudioRecorder.m3456a().m3439e();
        tXSStreamUploaderParam.f4910a = 0;
        tXSStreamUploaderParam.f4912c = 20;
        tXSStreamUploaderParam.f4911b = 0;
        tXSStreamUploaderParam.f4915f = 3;
        tXSStreamUploaderParam.f4919j = true;
        tXSStreamUploaderParam.f4921l = true;
        tXSStreamUploaderParam.f4920k = false;
        tXSStreamUploaderParam.f4917h = 40;
        tXSStreamUploaderParam.f4918i = 5000;
        TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
        tXSStreamUploaderParam.f4922m = tXCLivePushConfig.f4287I;
        tXSStreamUploaderParam.f4923n = tXCLivePushConfig.f4288J;
        tXSStreamUploaderParam.f4924o = getQuicMode(this.mVideoQuality);
        this.mStreamUploader = new TXCStreamUploader(this.mContext, tXSStreamUploaderParam);
        this.mStreamUploader.setID(this.mID);
        TXCLivePushConfig tXCLivePushConfig2 = this.mNewConfig;
        if ((tXCLivePushConfig2.f4289K & 1) != 0) {
            TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
            if (tXCStreamUploader != null) {
                tXCStreamUploader.setAudioInfo(tXCLivePushConfig2.f4309q, tXCLivePushConfig2.f4310r);
            }
        } else {
            TXCStreamUploader tXCStreamUploader2 = this.mStreamUploader;
            if (tXCStreamUploader2 != null) {
                tXCStreamUploader2.setAudioInfo(tXCLivePushConfig2.f4309q, 1);
            }
        }
        this.mStreamUploader.setNotifyListener(this);
        if (this.mConfig.mEnablePureAudioPush && (tXCCaptureAndEnc = this.mCaptureAndEnc) != null) {
            this.mStreamUploader.setAudioMute(tXCCaptureAndEnc.m2515m());
        }
        TXCStreamUploader tXCStreamUploader3 = this.mStreamUploader;
        String str = this.mPushUrl;
        TXCLivePushConfig tXCLivePushConfig3 = this.mNewConfig;
        this.mPushUrl = tXCStreamUploader3.start(str, tXCLivePushConfig3.f4285G, tXCLivePushConfig3.f4286H);
        if (this.mNewConfig.f4284F) {
            this.mStreamUploader.setMode(1);
        }
        TXCLivePushConfig tXCLivePushConfig4 = this.mNewConfig;
        if (tXCLivePushConfig4.f4287I) {
            int i = tXCLivePushConfig4.f4307o;
            int i2 = tXCLivePushConfig4.f4308p;
            if (i < 5) {
                i = 5;
            }
            if (i2 > 1) {
                i2 = 1;
            }
            this.mStreamUploader.setRetryInterval(i2);
            this.mStreamUploader.setRetryTimes(i);
            this.mStreamUploader.setVideoDropParams(false, this.mNewConfig.f4300h, 1000);
        } else {
            this.mStreamUploader.setRetryInterval(tXCLivePushConfig4.f4308p);
            this.mStreamUploader.setRetryTimes(this.mNewConfig.f4307o);
            this.mStreamUploader.setVideoDropParams(true, 40, 3000);
        }
        TXCStreamUploader tXCStreamUploader4 = this.mStreamUploader;
        TXCLivePushConfig tXCLivePushConfig5 = this.mNewConfig;
        tXCStreamUploader4.setSendStrategy(tXCLivePushConfig5.f4287I, tXCLivePushConfig5.f4288J);
    }

    private void stopNetworkModule() {
        TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
        if (tXCStreamUploader != null) {
            tXCStreamUploader.stop();
            this.mStreamUploader.setNotifyListener(null);
            this.mStreamUploader = null;
        }
    }

    private void startQosModule() {
        this.mQos = new TXCQoS(true);
        this.mQos.setListener(this);
        this.mQos.setNotifyListener(this);
        this.mQos.setAutoAdjustBitrate(this.mNewConfig.f4299g);
        this.mQos.setAutoAdjustStrategy(this.mNewConfig.f4298f);
        this.mQos.setDefaultVideoResolution(this.mNewConfig.f4303k);
        TXCQoS tXCQoS = this.mQos;
        TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
        tXCQoS.setVideoEncBitrate(tXCLivePushConfig.f4297e, tXCLivePushConfig.f4296d, tXCLivePushConfig.f4295c);
        if (this.mNewConfig.f4299g) {
            this.mQos.start(2000L);
        }
    }

    private void stopQosModule() {
        TXCQoS tXCQoS = this.mQos;
        if (tXCQoS != null) {
            tXCQoS.stop();
            this.mQos.setListener(null);
            this.mQos.setNotifyListener(null);
            this.mQos = null;
        }
    }

    private void startDataReportModule() {
        this.mDataReport = new TXCDataReport(this.mContext);
        this.mDataReport.m2373d(this.mID);
        this.mDataReport.m2384a(this.mNewConfig.f4295c);
        this.mDataReport.m2379b(this.mNewConfig.f4309q);
        TXCDataReport tXCDataReport = this.mDataReport;
        TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
        tXCDataReport.m2383a(tXCLivePushConfig.f4293a, tXCLivePushConfig.f4294b);
        this.mDataReport.m2382a(this.mPushUrl);
        this.mDataReport.m2385a();
    }

    private void stopDataReportModule() {
        TXCDataReport tXCDataReport = this.mDataReport;
        if (tXCDataReport != null) {
            tXCDataReport.m2380b();
            this.mDataReport = null;
        }
    }

    private void startEncoder() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.setID(this.mID);
            this.mCaptureAndEnc.m2577a((TXCCaptureAndEnc.AbstractC3410a) this);
            this.mCaptureAndEnc.m2559b(this.mVoiceKind, this.mVoiceEnvironment);
            this.mCaptureAndEnc.m2561b(this.mBgmPitch);
            this.mCaptureAndEnc.m2536e();
        }
    }

    private void stopEncoder() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2577a((TXCCaptureAndEnc.AbstractC3410a) null);
            this.mCaptureAndEnc.m2531f();
            this.mCaptureAndEnc.m2577a((TXCCaptureAndEnc.AbstractC3410a) null);
        }
    }

    private void transferConfig(TXLivePushConfig tXLivePushConfig) {
        TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
        tXCLivePushConfig.f4295c = tXLivePushConfig.mVideoBitrate;
        tXCLivePushConfig.f4297e = tXLivePushConfig.mMinVideoBitrate;
        tXCLivePushConfig.f4296d = tXLivePushConfig.mMaxVideoBitrate;
        tXCLivePushConfig.f4298f = tXLivePushConfig.mAutoAdjustStrategy;
        tXCLivePushConfig.f4299g = tXLivePushConfig.mAutoAdjustBitrate;
        tXCLivePushConfig.f4300h = tXLivePushConfig.mVideoFPS;
        tXCLivePushConfig.f4301i = tXLivePushConfig.mVideoEncodeGop;
        tXCLivePushConfig.f4302j = tXLivePushConfig.mHardwareAccel;
        tXCLivePushConfig.f4303k = tXLivePushConfig.mVideoResolution;
        tXCLivePushConfig.f4306n = tXLivePushConfig.mEnableVideoHardEncoderMainProfile;
        tXCLivePushConfig.f4309q = tXLivePushConfig.mAudioSample;
        tXCLivePushConfig.f4310r = tXLivePushConfig.mAudioChannels;
        tXCLivePushConfig.f4311s = tXLivePushConfig.mEnableAec;
        tXCLivePushConfig.f4315w = tXLivePushConfig.mPauseFlag;
        tXCLivePushConfig.f4314v = tXLivePushConfig.mPauseFps;
        tXCLivePushConfig.f4312t = tXLivePushConfig.mPauseImg;
        tXCLivePushConfig.f4313u = tXLivePushConfig.mPauseTime;
        tXCLivePushConfig.f4284F = tXLivePushConfig.mEnablePureAudioPush;
        tXCLivePushConfig.f4282D = tXLivePushConfig.mTouchFocus;
        tXCLivePushConfig.f4283E = tXLivePushConfig.mEnableZoom;
        tXCLivePushConfig.f4316x = tXLivePushConfig.mWatermark;
        tXCLivePushConfig.f4317y = tXLivePushConfig.mWatermarkX;
        tXCLivePushConfig.f4318z = tXLivePushConfig.mWatermarkY;
        tXCLivePushConfig.f4279A = tXLivePushConfig.mWatermarkXF;
        tXCLivePushConfig.f4280B = tXLivePushConfig.mWatermarkYF;
        tXCLivePushConfig.f4281C = tXLivePushConfig.mWatermarkWidth;
        tXCLivePushConfig.f4304l = tXLivePushConfig.mHomeOrientation;
        tXCLivePushConfig.f4285G = tXLivePushConfig.mEnableNearestIP;
        tXCLivePushConfig.f4286H = tXLivePushConfig.mRtmpChannelType;
        tXCLivePushConfig.f4307o = tXLivePushConfig.mConnectRetryCount;
        tXCLivePushConfig.f4308p = tXLivePushConfig.mConnectRetryInterval;
        tXCLivePushConfig.f4305m = tXLivePushConfig.mFrontCamera;
        tXCLivePushConfig.f4289K = tXLivePushConfig.mCustomModeType;
        tXCLivePushConfig.f4290L = tXLivePushConfig.mVideoEncoderXMirror;
        tXCLivePushConfig.f4291M = tXLivePushConfig.mEnableHighResolutionCapture;
        tXCLivePushConfig.f4292N = tXLivePushConfig.mEnableScreenCaptureAutoRotate;
        tXCLivePushConfig.m1465a();
    }

    private void applyConfig() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mCaptureAndEnc;
        if (tXCCaptureAndEnc == null) {
            return;
        }
        tXCCaptureAndEnc.m2572a(this.mNewConfig);
        int i = 1;
        if (this.mCaptureAndEnc.m2522i()) {
            TXCStreamUploader tXCStreamUploader = this.mStreamUploader;
            if (tXCStreamUploader != null) {
                TXCLivePushConfig tXCLivePushConfig = this.mNewConfig;
                if (tXCLivePushConfig.f4287I) {
                    int i2 = tXCLivePushConfig.f4307o;
                    int i3 = tXCLivePushConfig.f4308p;
                    if (i2 < 5) {
                        i2 = 5;
                    }
                    if (i3 <= 1) {
                        i = i3;
                    }
                    this.mStreamUploader.setRetryInterval(i);
                    this.mStreamUploader.setRetryTimes(i2);
                    this.mStreamUploader.setVideoDropParams(false, this.mNewConfig.f4300h, 1000);
                } else {
                    tXCStreamUploader.setRetryInterval(tXCLivePushConfig.f4308p);
                    this.mStreamUploader.setRetryTimes(this.mNewConfig.f4307o);
                    this.mStreamUploader.setVideoDropParams(true, 40, 3000);
                }
                TXCStreamUploader tXCStreamUploader2 = this.mStreamUploader;
                TXCLivePushConfig tXCLivePushConfig2 = this.mNewConfig;
                tXCStreamUploader2.setSendStrategy(tXCLivePushConfig2.f4287I, tXCLivePushConfig2.f4288J);
            }
            TXCQoS tXCQoS = this.mQos;
            if (tXCQoS == null) {
                return;
            }
            tXCQoS.stop();
            this.mQos.setAutoAdjustBitrate(this.mNewConfig.f4299g);
            this.mQos.setAutoAdjustStrategy(this.mNewConfig.f4298f);
            this.mQos.setDefaultVideoResolution(this.mNewConfig.f4303k);
            TXCQoS tXCQoS2 = this.mQos;
            TXCLivePushConfig tXCLivePushConfig3 = this.mNewConfig;
            tXCQoS2.setVideoEncBitrate(tXCLivePushConfig3.f4297e, tXCLivePushConfig3.f4296d, tXCLivePushConfig3.f4295c);
            if (!this.mNewConfig.f4299g) {
                return;
            }
            this.mQos.start(2000L);
            return;
        }
        TXCLivePushConfig tXCLivePushConfig4 = this.mNewConfig;
        if ((tXCLivePushConfig4.f4289K & 1) != 0) {
            TXCStreamUploader tXCStreamUploader3 = this.mStreamUploader;
            if (tXCStreamUploader3 == null) {
                return;
            }
            tXCStreamUploader3.setAudioInfo(tXCLivePushConfig4.f4309q, tXCLivePushConfig4.f4310r);
            return;
        }
        TXCStreamUploader tXCStreamUploader4 = this.mStreamUploader;
        if (tXCStreamUploader4 == null) {
            return;
        }
        tXCStreamUploader4.setAudioInfo(tXCLivePushConfig4.f4309q, 1);
    }

    private void transferPushEvent(int i, final Bundle bundle) {
        final int i2 = TXLiveConstants.PUSH_WARNING_VIDEO_ENCODE_SW_SWITCH_HW;
        if (i == -1313) {
            i2 = TXLiveConstants.PUSH_ERR_INVALID_ADDRESS;
        } else if (i != 1107) {
            switch (i) {
                case TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT /* -1309 */:
                    i2 = TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT;
                    break;
                case TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED /* -1308 */:
                    i2 = TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED;
                    break;
                case TXLiveConstants.PUSH_ERR_NET_DISCONNECT /* -1307 */:
                    i2 = TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
                    break;
                default:
                    switch (i) {
                        case TXLiveConstants.PUSH_ERR_VIDEO_ENCODE_FAIL /* -1303 */:
                            i2 = TXLiveConstants.PUSH_ERR_VIDEO_ENCODE_FAIL;
                            break;
                        case TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL /* -1302 */:
                            i2 = TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL;
                            break;
                        case TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL /* -1301 */:
                            i2 = TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL;
                            break;
                        default:
                            switch (i) {
                                case 1001:
                                    i2 = 1001;
                                    break;
                                case 1002:
                                    i2 = 1002;
                                    break;
                                case 1003:
                                    i2 = 1003;
                                    break;
                                case 1004:
                                    i2 = 1004;
                                    break;
                                case 1005:
                                    i2 = 1005;
                                    break;
                                case 1006:
                                    i2 = 1006;
                                    break;
                                case 1007:
                                    i2 = 1007;
                                    break;
                                case 1008:
                                    i2 = 1008;
                                    break;
                                default:
                                    switch (i) {
                                        case 1018:
                                            i2 = 1018;
                                            break;
                                        case 1019:
                                            i2 = 1019;
                                            break;
                                        case 1020:
                                            i2 = 1020;
                                            break;
                                        case 1021:
                                            i2 = 1021;
                                            break;
                                        default:
                                            switch (i) {
                                                case TXLiveConstants.PUSH_WARNING_NET_BUSY /* 1101 */:
                                                    i2 = TXLiveConstants.PUSH_WARNING_NET_BUSY;
                                                    break;
                                                case TXLiveConstants.PUSH_WARNING_RECONNECT /* 1102 */:
                                                    i2 = TXLiveConstants.PUSH_WARNING_RECONNECT;
                                                    break;
                                                case TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL /* 1103 */:
                                                    i2 = TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL;
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case 3002:
                                                            i2 = 3002;
                                                            break;
                                                        case 3003:
                                                            i2 = 3003;
                                                            break;
                                                        case 3004:
                                                            i2 = 3004;
                                                            break;
                                                        case 3005:
                                                            i2 = 3005;
                                                            break;
                                                        default:
                                                            String str = TAG;
                                                            TXCLog.m2911w(str, "unhandled event : " + i);
                                                            return;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.rtmp.TXLivePusher.11
                @Override // java.lang.Runnable
                public void run() {
                    if (TXLivePusher.this.mListener != null) {
                        TXLivePusher.this.mListener.onPushEvent(i2, bundle);
                    }
                }
            });
        }
    }

    private byte[] transferAvccToAnnexb(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        int i = 0;
        while (true) {
            int i2 = i + 4;
            if (i2 < length) {
                int i3 = ByteBuffer.wrap(bArr, i, 4).getInt();
                if (i2 + i3 <= length) {
                    bArr2[i] = 0;
                    bArr2[i + 1] = 0;
                    bArr2[i + 2] = 0;
                    bArr2[i + 3] = 1;
                }
                i = i + i3 + 4;
            } else {
                return bArr2;
            }
        }
    }

    private byte[] add_emulation_prevention_three_byte(byte[] bArr) {
        int length = ((bArr.length * 4) / 3) + 2;
        byte[] bArr2 = new byte[length];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length && i2 < length) {
            if (i + 3 < bArr.length && bArr[i] == 0) {
                int i3 = i + 1;
                if (bArr[i3] == 0) {
                    int i4 = i + 2;
                    if (bArr[i4] >= 0 && bArr[i4] <= 3) {
                        int i5 = i2 + 1;
                        bArr2[i2] = bArr[i];
                        int i6 = i5 + 1;
                        int i7 = i3 + 1;
                        bArr2[i5] = bArr[i3];
                        int i8 = i6 + 1;
                        bArr2[i6] = 3;
                        i = i7;
                        i2 = i8;
                    }
                }
            }
            bArr2[i2] = bArr[i];
            i++;
            i2++;
        }
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr2, 0, bArr3, 0, i2);
        return bArr3;
    }

    @Deprecated
    public void sendMessage(byte[] bArr) {
        synchronized (this) {
            if (this.mMsgArray != null) {
                MsgInfo msgInfo = new MsgInfo();
                msgInfo.f5644ts = TXCTimeUtil.getTimeTick();
                msgInfo.msg = add_emulation_prevention_three_byte(bArr);
                this.mMsgArray.add(msgInfo);
            }
        }
    }

    public boolean sendMessageEx(byte[] bArr) {
        if (bArr.length <= 0 || bArr.length > 2048) {
            return false;
        }
        synchronized (this) {
            if (this.mMsgArray != null) {
                MsgInfo msgInfo = new MsgInfo();
                msgInfo.f5644ts = TXCTimeUtil.getTimeTick();
                msgInfo.msg = appendSEIBuffer(bArr.length, add_emulation_prevention_three_byte(bArr));
                this.mMsgArray.add(msgInfo);
            }
        }
        return true;
    }

    private byte[] appendSEIBuffer(int i, byte[] bArr) {
        byte[] intToBytes = intToBytes(i);
        byte[] bArr2 = new byte[intToBytes.length + 1 + bArr.length + 1];
        bArr2[0] = SEI_MSG_TYPE;
        System.arraycopy(intToBytes, 0, bArr2, 1, intToBytes.length);
        int length = 1 + intToBytes.length;
        System.arraycopy(bArr, 0, bArr2, length, bArr.length);
        bArr2[length + bArr.length] = Byte.MIN_VALUE;
        return bArr2;
    }

    private byte[] intToBytes(int i) {
        int i2 = (i / 255) + 1;
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i3 < i4) {
                bArr[i3] = -1;
                i3++;
            } else {
                bArr[i4] = (byte) ((i % 255) & 255);
                return bArr;
            }
        }
    }

    private int getQuicMode(int i) {
        long m2979a;
        switch (i) {
            case 1:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "Live");
                break;
            case 2:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "Live");
                break;
            case 3:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "Live");
                break;
            case 4:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "LinkMain");
                break;
            case 5:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "LinkSub");
                break;
            case 6:
                m2979a = TXCConfigCenter.m2988a().m2979a("QUICMode", "RTC");
                break;
            default:
                return 0;
        }
        return (int) m2979a;
    }
}
