package com.tencent.ugc;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.liteav.TXCRecordConfig;
import com.tencent.liteav.audio.TXCAudioUGCRecorder;
import com.tencent.liteav.audio.TXCUGCBGMPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.TXIBGMOnPlayListener;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p107c.LicenceCheck;
import com.tencent.liteav.basic.p107c.LicenceInfo;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener;
import com.tencent.liteav.basic.p109e.TXITakePhotoListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.TXIVideoPreprocessorListener;
import com.tencent.liteav.capturer.TXCCameraCapturer;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.renderer.TXCGLSurfaceView;
import com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner;
import com.tencent.liteav.videoediter.p132a.TXMultiMediaComposer;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCPartsManager;
import com.tencent.ugc.TXVideoEditConstants;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes3.dex */
public class TXUGCRecord implements TXIAudioRecordListener, TXINotifyListener, TXIGLSurfaceTextureListener, TXIVideoPreprocessorListener, TXIVideoEncoderListener, TXUGCPartsManager.IPartsManagerListener {
    private static final int DEFAULT_CHANNEL = 1;
    public static float ENCODE_SPEED_FAST = 1.25f;
    public static float ENCODE_SPEED_FASTEST = 2.0f;
    public static float ENCODE_SPEED_SLOW = 0.8f;
    public static float ENCODE_SPEED_SLOWEST = 0.5f;
    private static final String OUTPUT_DIR_NAME = "TXUGC";
    private static final String OUTPUT_TEMP_DIR_NAME = "TXUGCParts";
    private static final String OUTPUT_VIDEO_COVER_NAME = "TXUGCCover.jpg";
    private static final String OUTPUT_VIDEO_NAME = "TXUGCRecord.mp4";
    public static float PLAY_SPEED_FAST = 0.8f;
    public static float PLAY_SPEED_FASTEST = 0.5f;
    public static float PLAY_SPEED_SLOW = 1.25f;
    public static float PLAY_SPEED_SLOWEST = 2.0f;
    private static final int STATE_NO_PERMISSION = -1;
    private static final int STATE_RECORDING = 1;
    private static final int STATE_RECORD_INIT = 1;
    private static final int STATE_RECORD_PAUSE = 3;
    private static final int STATE_RECORD_RECORDING = 2;
    private static final int STATE_SUCCESS = 0;
    private static final String TAG = "TXUGCRecord";
    private static TXUGCRecord instance;
    private long mBGMEndTime;
    private String mBGMPath;
    private boolean mBGMPlayStop;
    private long mBGMStartTime;
    private CopyOnWriteArrayList<Long> mBgmPartBytesList;
    private Context mContext;
    private int mCropHeight;
    private int mCropWidth;
    private long mCurrentRecordDuration;
    private VideoCustomProcessListener mCustomProcessListener;
    private int mDisplayType;
    private Handler mMainHandler;
    private int mMaxDuration;
    private int mMinDuration;
    private int mRecordRetCode;
    private TXCloudVideoView mTXCloudVideoView;
    private TXQuickJoiner mTXFFQuickJoiner;
    private TXUGCPartsManager mTXUGCPartsManager;
    private TXVideoEditConstants.TXRect mTxWaterMarkRect;
    private boolean mUseSWEncoder;
    private TXRecordCommon.ITXVideoRecordListener mVideoRecordListener;
    private TXCGLSurfaceView mVideoView;
    private Bitmap mWatermarkBitmap;
    private volatile int mRecordState = 1;
    private int mFps = 20;
    private int mGop = 3;
    private long mRecordStartTime = 0;
    private boolean mStartMuxer = false;
    private boolean mRecording = false;
    private boolean needCompose = false;
    private String mSaveDir = null;
    private String mVideoFileCurTempPath = null;
    private String mVideoFilePath = null;
    private String mVideoFileTempDir = null;
    private String mCoverCurTempPath = null;
    private String mCoverPath = null;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;
    private int mCameraResolution = 5;
    private boolean mStartPreview = false;
    private boolean mCapturing = false;
    private TXCRecordConfig mConfig = new TXCRecordConfig();
    private TXCCameraCapturer mCameraCapture = null;
    private TXCVideoPreprocessor mVideoPreprocessor = null;
    private TXCVideoEncoder mVideoEncoder = null;
    private TXCMP4Muxer mMP4Muxer = null;
    private TXMultiMediaComposer mTXMultiMediaComposer = null;
    private int mRenderRotationReadyChange = -1;
    private int mCameraOrientationReadyChange = -1;
    private int mRenderMode = 0;
    private boolean isReachedMaxDuration = false;
    private TXIBGMOnPlayListener mBGMNotifyProxy = null;
    private TXRecordCommon.ITXBGMNotify mBGMNotify = null;
    private boolean mBGMDeletePart = false;
    private int mRecordSpeed = 2;
    private boolean mInitCompelete = false;
    private boolean mSnapshotRunning = false;
    private int mVoiceKind = -1;
    private int mVoiceEnvironment = -1;
    private float mSpecialRadio = 0.5f;
    private boolean mSmartLicenseSupport = true;
    private RunnableC3756a mTouchFocusRunnable = new RunnableC3756a();

    /* loaded from: classes3.dex */
    public interface VideoCustomProcessListener {
        void onDetectFacePoints(float[] fArr);

        int onTextureCustomProcess(int i, int i2, int i3);

        void onTextureDestroyed();
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordPcmData(byte[] bArr, long j, int i, int i2, int i3) {
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordRawPcmData(byte[] bArr, long j, int i, int i2, int i3, boolean z) {
    }

    public static synchronized TXUGCRecord getInstance(Context context) {
        TXUGCRecord tXUGCRecord;
        synchronized (TXUGCRecord.class) {
            if (instance == null) {
                instance = new TXUGCRecord(context);
            }
            tXUGCRecord = instance;
        }
        return tXUGCRecord;
    }

    protected TXUGCRecord(Context context) {
        this.mUseSWEncoder = false;
        TXCLog.init();
        if (context != null) {
            this.mContext = context.getApplicationContext();
            this.mMainHandler = new Handler(this.mContext.getMainLooper());
            this.mTXUGCPartsManager = new TXUGCPartsManager();
            this.mBgmPartBytesList = new CopyOnWriteArrayList<>();
            LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        }
        this.mUseSWEncoder = TXCSystemUtil.m2871g();
        initFileAndFolder();
    }

    private void initFileAndFolder() {
        this.mSaveDir = getDefaultDir();
        this.mVideoFilePath = this.mSaveDir + File.separator + OUTPUT_VIDEO_NAME;
        this.mCoverPath = this.mSaveDir + File.separator + OUTPUT_VIDEO_COVER_NAME;
        this.mVideoFileTempDir = this.mSaveDir + File.separator + OUTPUT_TEMP_DIR_NAME;
        File file = new File(this.mVideoFileTempDir);
        if (!file.exists()) {
            file.mkdir();
        }
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", getTimeString());
        File file2 = new File(this.mVideoFilePath);
        if (file2.exists()) {
            file2.delete();
        }
    }

    public void setVideoRecordListener(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        this.mVideoRecordListener = iTXVideoRecordListener;
    }

    @Override // com.tencent.ugc.TXUGCPartsManager.IPartsManagerListener
    public void onDeleteLastPart() {
        if (this.mBgmPartBytesList.size() != 0) {
            CopyOnWriteArrayList<Long> copyOnWriteArrayList = this.mBgmPartBytesList;
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() - 1);
            this.mBGMDeletePart = true;
        }
    }

    @Override // com.tencent.ugc.TXUGCPartsManager.IPartsManagerListener
    public void onDeleteAllParts() {
        this.mBgmPartBytesList.clear();
        this.mBGMDeletePart = false;
    }

    /* renamed from: com.tencent.ugc.TXUGCRecord$a */
    /* loaded from: classes3.dex */
    private class RunnableC3756a implements Runnable {

        /* renamed from: b */
        private float f5785b;

        /* renamed from: c */
        private float f5786c;

        private RunnableC3756a() {
        }

        /* renamed from: a */
        public void m272a(float f, float f2) {
            this.f5785b = f;
            this.f5786c = f2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TXUGCRecord.this.mTXCloudVideoView == null) {
                return;
            }
            if (TXUGCRecord.this.mCameraCapture != null && TXUGCRecord.this.mConfig.f4401f) {
                TXUGCRecord.this.mCameraCapture.m2405a(this.f5785b / TXUGCRecord.this.mTXCloudVideoView.getWidth(), this.f5786c / TXUGCRecord.this.mTXCloudVideoView.getHeight());
            }
            if (!TXUGCRecord.this.mConfig.f4401f) {
                return;
            }
            TXUGCRecord.this.mTXCloudVideoView.onTouchFocus((int) this.f5785b, (int) this.f5786c);
        }
    }

    public int startCameraSimplePreview(TXRecordCommon.TXUGCSimpleConfig tXUGCSimpleConfig, TXCloudVideoView tXCloudVideoView) {
        if (tXCloudVideoView == null || tXUGCSimpleConfig == null) {
            TXCLog.m2914e(TAG, "startCameraPreview: invalid param");
            return -1;
        }
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        tXCRecordConfig.f4416u = tXUGCSimpleConfig.needEdit;
        tXCRecordConfig.f4396a = tXUGCSimpleConfig.videoQuality;
        tXCRecordConfig.f4410o = tXUGCSimpleConfig.isFront;
        tXCRecordConfig.f4401f = tXUGCSimpleConfig.touchFocus;
        this.mMinDuration = tXUGCSimpleConfig.minDuration;
        this.mMaxDuration = tXUGCSimpleConfig.maxDuration;
        startCameraPreviewInternal(tXCloudVideoView, tXCRecordConfig);
        return 0;
    }

    public int startCameraCustomPreview(TXRecordCommon.TXUGCCustomConfig tXUGCCustomConfig, TXCloudVideoView tXCloudVideoView) {
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        tXCRecordConfig.f4416u = tXUGCCustomConfig.needEdit;
        if (tXCloudVideoView == null || tXUGCCustomConfig == null) {
            TXCLog.m2914e(TAG, "startCameraPreview: invalid param");
            return -1;
        }
        tXCRecordConfig.f4396a = -1;
        if (tXUGCCustomConfig.videoBitrate < 600) {
            tXUGCCustomConfig.videoBitrate = 600;
        }
        if (tXUGCCustomConfig.needEdit) {
            this.mConfig.f4399d = ConstantUtils.MAX_ITEM_NUM;
        } else {
            this.mConfig.f4399d = tXUGCCustomConfig.videoBitrate;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2491bi, this.mConfig.f4399d, "");
        int i = tXUGCCustomConfig.videoFps;
        if (i < 15) {
            tXUGCCustomConfig.videoFps = 15;
        } else if (i > 30) {
            tXUGCCustomConfig.videoFps = 30;
        }
        TXCRecordConfig tXCRecordConfig2 = this.mConfig;
        tXCRecordConfig2.f4398c = tXUGCCustomConfig.videoFps;
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2492bj, tXCRecordConfig2.f4398c, "");
        int i2 = tXUGCCustomConfig.videoGop;
        if (i2 < 1) {
            tXUGCCustomConfig.videoGop = 1;
        } else if (i2 > 10) {
            tXUGCCustomConfig.videoGop = 10;
        }
        if (tXUGCCustomConfig.needEdit) {
            this.mConfig.f4400e = 0;
        } else {
            this.mConfig.f4400e = tXUGCCustomConfig.videoGop;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2493bk, this.mConfig.f4400e, "");
        int i3 = tXUGCCustomConfig.audioSampleRate;
        if (i3 != 8000 && i3 != 16000 && i3 != 32000 && i3 != 44100 && i3 != 48000) {
            this.mConfig.f4415t = TXRecordCommon.AUDIO_SAMPLERATE_48000;
        } else {
            this.mConfig.f4415t = tXUGCCustomConfig.audioSampleRate;
        }
        TXCRecordConfig tXCRecordConfig3 = this.mConfig;
        tXCRecordConfig3.f4397b = tXUGCCustomConfig.videoResolution;
        tXCRecordConfig3.f4410o = tXUGCCustomConfig.isFront;
        tXCRecordConfig3.f4401f = tXUGCCustomConfig.touchFocus;
        tXCRecordConfig3.f4412q = tXUGCCustomConfig.enableHighResolutionCapture;
        this.mMinDuration = tXUGCCustomConfig.minDuration;
        this.mMaxDuration = tXUGCCustomConfig.maxDuration;
        tXCRecordConfig3.f4416u = tXUGCCustomConfig.needEdit;
        startCameraPreviewInternal(tXCloudVideoView, tXCRecordConfig3);
        return 0;
    }

    public void setVideoResolution(int i) {
        if (this.mRecordState != 1) {
            TXCLog.m2914e(TAG, "setVideoResolution err, state not init");
        } else if (this.mTXCloudVideoView == null) {
            TXCLog.m2914e(TAG, "setVideoResolution, mTXCloudVideoView is null");
        } else {
            TXCRecordConfig tXCRecordConfig = this.mConfig;
            if (tXCRecordConfig.f4397b == i) {
                TXCLog.m2913i(TAG, "setVideoResolution, resolution not change");
                return;
            }
            tXCRecordConfig.f4396a = -1;
            tXCRecordConfig.f4397b = i;
            stopCameraPreview();
            startCameraPreviewInternal(this.mTXCloudVideoView, this.mConfig);
        }
    }

    public void setVideoBitrate(int i) {
        if (this.mRecordState != 1) {
            TXCLog.m2914e(TAG, "setVideoBitrate err, state not init");
            return;
        }
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        tXCRecordConfig.f4396a = -1;
        tXCRecordConfig.f4399d = i;
    }

    public void stopCameraPreview() {
        try {
            TXCLog.m2913i(TAG, "ugcRecord, stopCameraPreview");
            synchronized (this) {
                this.mCapturing = false;
                if (this.mCameraCapture != null) {
                    this.mCameraCapture.m2400b();
                }
            }
            if (this.mVideoView != null) {
                this.mVideoView.m1011b(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TXUGCRecord.this.mVideoPreprocessor != null) {
                            TXUGCRecord.this.mVideoPreprocessor.m2633a();
                        }
                    }
                });
                this.mVideoView.m1010b(false);
                this.mVideoView = null;
            }
            if (this.mCustomProcessListener == null) {
                return;
            }
            this.mCustomProcessListener.onTextureDestroyed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TXUGCPartsManager getPartsManager() {
        return this.mTXUGCPartsManager;
    }

    public void setMute(boolean z) {
        TXCAudioUGCRecorder.getInstance().setMute(z);
    }

    public int startRecord() {
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.m2914e(TAG, "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        } else if (this.mRecording) {
            TXCLog.m2914e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else {
            try {
                TXCDRApi.initCrashReport(this.mContext);
                this.mCoverCurTempPath = this.mCoverPath;
                File file = new File(this.mCoverPath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return startRecordInternal();
        }
    }

    public int startRecord(String str, String str2) {
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.m2914e(TAG, "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        } else if (this.mRecording) {
            TXCLog.m2914e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e(TAG, "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        } else {
            this.mVideoFilePath = str;
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            String defaultDir = getDefaultDir();
            this.mVideoFileTempDir = defaultDir + File.separator + OUTPUT_TEMP_DIR_NAME;
            File file2 = new File(this.mVideoFileTempDir);
            if (!file2.exists()) {
                file2.mkdir();
            }
            this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", getTimeString());
            this.mCoverPath = str2;
            this.mCoverCurTempPath = str2;
            return startRecordInternal();
        }
    }

    public int startRecord(String str, String str2, String str3) {
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.m2914e(TAG, "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        } else if (this.mRecording) {
            TXCLog.m2914e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e(TAG, "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        } else {
            this.mVideoFilePath = str;
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            if (!TextUtils.isEmpty(str2)) {
                this.mVideoFileTempDir = str2;
            } else {
                String defaultDir = getDefaultDir();
                this.mVideoFileTempDir = defaultDir + File.separator + OUTPUT_TEMP_DIR_NAME;
            }
            File file2 = new File(this.mVideoFileTempDir);
            if (!file2.exists()) {
                file2.mkdir();
            }
            this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", getTimeString());
            this.mCoverPath = str3;
            this.mCoverCurTempPath = str3;
            return startRecordInternal();
        }
    }

    private int startRecordInternal() {
        if (!this.mInitCompelete) {
            TXCLog.m2913i(TAG, "startRecordInternal, mInitCompelete = " + this.mInitCompelete);
            return -4;
        } else if (!checkLicenseMatch()) {
            return -5;
        } else {
            TXCAudioUGCRecorder.getInstance().setAECType(TXEAudioDef.TXE_AEC_NONE, this.mContext);
            TXCAudioUGCRecorder.getInstance().setListener(this);
            TXCAudioUGCRecorder.getInstance().setChannels(1);
            TXCAudioUGCRecorder.getInstance().setSampleRate(this.mConfig.f4415t);
            TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
            int i = this.mRecordSpeed;
            if (i == 0) {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOWEST);
            } else if (i == 1) {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOW);
            } else if (i == 2) {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
            } else if (i == 3) {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FAST);
            } else if (i == 4) {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FASTEST);
            }
            if (this.mVideoEncoder != null) {
                this.mVideoWidth = 0;
                this.mVideoHeight = 0;
            }
            TXCRecordConfig tXCRecordConfig = this.mConfig;
            this.mUseSWEncoder = tXCRecordConfig.f4402g < 1280 && tXCRecordConfig.f4403h < 1280;
            if (this.mMP4Muxer == null) {
                this.mMP4Muxer = new TXCMP4Muxer(this.mContext, this.mUseSWEncoder ? 0 : 2);
            }
            TXCLog.m2913i(TAG, "startRecord");
            this.mMP4Muxer.mo1234a(this.mRecordSpeed);
            this.mMP4Muxer.mo1231a(this.mVideoFileCurTempPath);
            addAudioTrack();
            TXCDRApi.txReportDAU(this.mContext.getApplicationContext(), TXCDRDef.f2470au);
            this.mRecordState = 2;
            this.mRecording = true;
            this.mRecordStartTime = 0L;
            if (this.mBGMDeletePart) {
                TXCAudioUGCRecorder.getInstance().clearCache();
            }
            TXCAudioUGCRecorder.getInstance().resume();
            return 0;
        }
    }

    public int stopRecord() {
        TXCLog.m2913i(TAG, "stopRecord called, mRecording = " + this.mRecording + ", needCompose = " + this.needCompose);
        if (this.mRecording) {
            this.needCompose = true;
            return stopRecordForClip();
        }
        int composeRecord = composeRecord();
        if (composeRecord == 0) {
            return 0;
        }
        callbackRecordFail(composeRecord);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int composeRecord() {
        if (this.mTXFFQuickJoiner == null) {
            this.mTXFFQuickJoiner = new TXQuickJoiner();
        }
        this.mTXFFQuickJoiner.m488a(new TXQuickJoiner.AbstractC3670a() { // from class: com.tencent.ugc.TXUGCRecord.7
            @Override // com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner.AbstractC3670a
            /* renamed from: a */
            public void mo255a(TXQuickJoiner tXQuickJoiner, int i, String str) {
                if (i == 0) {
                    TXUGCRecord.this.callbackRecordSuccess();
                } else if (i == 1) {
                    TXUGCRecord.this.callbackRecordFail(-7);
                    TXLog.m390e(TXUGCRecord.TAG, "composeRecord, quick joiner result cancel");
                } else if (i == -1) {
                    TXUGCRecord.this.callbackRecordFail(-8);
                    TXLog.m390e(TXUGCRecord.TAG, "composeRecord, quick joiner result verify fail");
                } else if (i == -2) {
                    TXUGCRecord.this.callbackRecordFail(-9);
                    TXLog.m390e(TXUGCRecord.TAG, "composeRecord, quick joiner result err");
                }
                tXQuickJoiner.m488a((TXQuickJoiner.AbstractC3670a) null);
                tXQuickJoiner.m478c();
                tXQuickJoiner.m476d();
                TXUGCRecord.this.mTXFFQuickJoiner = null;
                TXUGCRecord.this.mRecordState = 1;
            }

            @Override // com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner.AbstractC3670a
            /* renamed from: a */
            public void mo256a(TXQuickJoiner tXQuickJoiner, float f) {
                TXCLog.m2913i(TXUGCRecord.TAG, "joiner progress " + f);
            }
        });
        if (this.mTXFFQuickJoiner.m481a(this.mTXUGCPartsManager.getPartsPathList()) != 0) {
            TXLog.m390e(TAG, "composeRecord, quick joiner set src path err");
            return -4;
        } else if (this.mTXFFQuickJoiner.m482a(this.mVideoFilePath) != 0) {
            TXLog.m390e(TAG, "composeRecord, quick joiner set dst path err, mVideoFilePath = " + this.mVideoFilePath);
            return -5;
        } else if (this.mTXFFQuickJoiner.m480b() == 0) {
            return 0;
        } else {
            TXLog.m390e(TAG, "composeRecord, quick joiner start fail");
            return -6;
        }
    }

    public void release() {
        TXCAudioUGCRecorder.getInstance().stopRecord();
        TXCAudioUGCRecorder.getInstance().setChangerType(-1, -1);
        TXCAudioUGCRecorder.getInstance().setReverbType(0);
        this.mTXCloudVideoView = null;
        this.mRecordState = 1;
        this.mRenderMode = 0;
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2620a((TXIVideoPreprocessorListener) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int stopRecordForClip() {
        int i;
        if (!this.mRecording) {
            TXCLog.m2914e(TAG, "stopRecordForClip: there is no existing uncompleted record task");
            return -2;
        }
        this.mRecording = false;
        TXCAudioUGCRecorder.getInstance().pause();
        TXCVideoEncoder tXCVideoEncoder = this.mVideoEncoder;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m415b();
        }
        this.mStartMuxer = false;
        stopEncoder(this.mVideoEncoder);
        this.mVideoEncoder = null;
        synchronized (this) {
            if (this.mMP4Muxer != null) {
                i = this.mMP4Muxer.mo1227b();
                this.mMP4Muxer = null;
            } else {
                i = 0;
            }
        }
        File file = new File(this.mVideoFileCurTempPath);
        if (i != 0) {
            if (file.exists()) {
                asyncDeleteFile(this.mVideoFileCurTempPath);
                this.mVideoFileCurTempPath = null;
            }
            TXCLog.m2914e(TAG, "stopRecordForClip, maybe mMP4Muxer not write data");
            if (!TextUtils.isEmpty(this.mBGMPath)) {
                this.mBGMDeletePart = true;
            }
            if (!this.needCompose && !this.isReachedMaxDuration) {
                return -3;
            }
        }
        if (TextUtils.isEmpty(this.mVideoFileCurTempPath) || !file.exists() || file.length() == 0) {
            TXCLog.m2914e(TAG, "stopRecordForClip, file err ---> path = " + this.mVideoFileCurTempPath);
            if (!TextUtils.isEmpty(this.mBGMPath)) {
                this.mBGMDeletePart = true;
            }
            if (!this.needCompose && !this.isReachedMaxDuration) {
                return -3;
            }
        } else {
            TXCLog.m2913i(TAG, "stopRecordForClip, tempVideoFile exist, path = " + this.mVideoFileCurTempPath + ", length = " + file.length());
            PartInfo partInfo = new PartInfo();
            partInfo.setPath(this.mVideoFileCurTempPath);
            partInfo.setDuration(this.mCurrentRecordDuration);
            this.mTXUGCPartsManager.addClipInfo(partInfo);
            if (!TextUtils.isEmpty(this.mBGMPath)) {
                long curPosition = TXCUGCBGMPlayer.getInstance().getCurPosition() - TXCAudioUGCRecorder.getInstance().getPcmCacheLen();
                TXCLog.m2913i(TAG, "stopRecordForClip, bgmCurProgress = " + curPosition + ", bgm player position = " + TXCUGCBGMPlayer.getInstance().getCurPosition() + ", record bgm cache = " + TXCAudioUGCRecorder.getInstance().getPcmCacheLen());
                this.mBgmPartBytesList.add(Long.valueOf(curPosition));
                this.mBGMDeletePart = false;
            }
            callbackEvent(1, null);
        }
        String str = this.mCoverCurTempPath;
        if (!TextUtils.isEmpty(str)) {
            this.mCoverCurTempPath = null;
        }
        asyncGenCoverAndDetermineCompose(str);
        return 0;
    }

    private void asyncGenCoverAndDetermineCompose(final String str) {
        AsyncTask.execute(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (!TextUtils.isEmpty(TXUGCRecord.this.mVideoFileCurTempPath) && !TextUtils.isEmpty(str)) {
                        TXCSystemUtil.m2887a(TXUGCRecord.this.mVideoFileCurTempPath, str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TXUGCRecord.this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCLog.m2913i(TXUGCRecord.TAG, "stopRecordForClip, isReachedMaxDuration = " + TXUGCRecord.this.isReachedMaxDuration + ", needCompose = " + TXUGCRecord.this.needCompose);
                        if (TXUGCRecord.this.isReachedMaxDuration) {
                            TXUGCRecord.this.mRecordRetCode = 2;
                            int composeRecord = TXUGCRecord.this.composeRecord();
                            if (composeRecord == 0) {
                                return;
                            }
                            TXUGCRecord.this.callbackRecordFail(composeRecord);
                        } else if (!TXUGCRecord.this.needCompose) {
                        } else {
                            TXUGCRecord.this.mRecordRetCode = 0;
                            TXUGCRecord.this.needCompose = false;
                            int composeRecord2 = TXUGCRecord.this.composeRecord();
                            if (composeRecord2 == 0) {
                                return;
                            }
                            TXUGCRecord.this.callbackRecordFail(composeRecord2);
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackRecordFail(int i) {
        TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
        tXRecordResult.retCode = i;
        tXRecordResult.descMsg = "record video failed";
        TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener = this.mVideoRecordListener;
        if (iTXVideoRecordListener != null) {
            iTXVideoRecordListener.onRecordComplete(tXRecordResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackRecordSuccess() {
        TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
        if (this.mTXUGCPartsManager.getDuration() < this.mMinDuration) {
            this.mRecordRetCode = 1;
        }
        tXRecordResult.retCode = this.mRecordRetCode;
        tXRecordResult.descMsg = "record success";
        tXRecordResult.videoPath = this.mVideoFilePath;
        tXRecordResult.coverPath = this.mCoverPath;
        TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener = this.mVideoRecordListener;
        if (iTXVideoRecordListener != null) {
            iTXVideoRecordListener.onRecordComplete(tXRecordResult);
        }
    }

    private String getDefaultDir() {
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            String str = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
            return str;
        }
        File filesDir = this.mContext.getFilesDir();
        if (filesDir == null) {
            return null;
        }
        return filesDir.getPath();
    }

    private String getTimeString() {
        return new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date(System.currentTimeMillis()));
    }

    private void asyncDeleteFile(final String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        try {
            new AsyncTask() { // from class: com.tencent.ugc.TXUGCRecord.9
                @Override // android.os.AsyncTask
                protected Object doInBackground(Object[] objArr) {
                    File file = new File(str);
                    if (!file.isFile() || !file.exists()) {
                        return null;
                    }
                    file.delete();
                    return null;
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
        } catch (Exception e) {
            TXCLog.m2915d(TAG, "asyncDeleteFile, exception = " + e);
        }
    }

    public int pauseRecord() {
        if (!this.mRecording) {
            TXCLog.m2914e(TAG, "pauseRecord: there is no existing uncompleted record task");
            return -2;
        }
        TXCLog.m2913i(TAG, "pauseRecord called");
        this.mRecordState = 3;
        return stopRecordForClip();
    }

    public int resumeRecord() {
        if (this.mRecording) {
            TXCLog.m2914e(TAG, "resumeRecord: there is existing uncompleted record task");
            return -1;
        }
        TXCLog.m2913i(TAG, "resumeRecord called");
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", getTimeString());
        int startRecordInternal = startRecordInternal();
        callbackEvent(2, null);
        return startRecordInternal;
    }

    private void changeRecordSpeed() {
        int i = this.mRecordSpeed;
        if (i == 0) {
            TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_SLOWEST);
            TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOWEST);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2494bl, this.mRecordSpeed, "SLOWEST");
        } else if (i == 1) {
            TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_SLOW);
            TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOW);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2494bl, this.mRecordSpeed, "SLOW");
        } else if (i == 2) {
            TXCUGCBGMPlayer.getInstance().setSpeedRate(1.0f);
            TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2494bl, this.mRecordSpeed, "NORMAL");
        } else if (i == 3) {
            TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_FAST);
            TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FAST);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2494bl, this.mRecordSpeed, "FAST");
        } else if (i != 4) {
        } else {
            TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_FASTEST);
            TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FASTEST);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2484bb);
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2494bl, this.mRecordSpeed, "FASTEST");
        }
    }

    public boolean setMicVolume(float f) {
        TXCAudioUGCRecorder.getInstance().setVolume(f);
        return true;
    }

    public boolean switchCamera(final boolean z) {
        this.mConfig.f4410o = z;
        TXCGLSurfaceView tXCGLSurfaceView = this.mVideoView;
        if (tXCGLSurfaceView != null) {
            tXCGLSurfaceView.m1011b(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    if (TXUGCRecord.this.mCameraCapture != null) {
                        TXUGCRecord.this.mCameraCapture.m2400b();
                        TXUGCRecord.this.mVideoView.mo1015a(false);
                        TXUGCRecord.this.mCameraCapture.m2402a(TXUGCRecord.this.mVideoView.getSurfaceTexture());
                        if (TXUGCRecord.this.mCameraCapture.m2395c(z) == 0) {
                            TXUGCRecord.this.mCapturing = true;
                        } else {
                            TXUGCRecord.this.mCapturing = false;
                            TXUGCRecord.this.callbackEvent(3, null);
                        }
                        TXUGCRecord.this.mVideoView.mo1025a(TXUGCRecord.this.mConfig.f4398c);
                    }
                    TXUGCRecord tXUGCRecord = TXUGCRecord.this;
                    tXUGCRecord.setWatermark(tXUGCRecord.mWatermarkBitmap, TXUGCRecord.this.mTxWaterMarkRect);
                }
            });
            return true;
        }
        return true;
    }

    public void setAspectRatio(int i) {
        this.mDisplayType = i;
        if (i == 0) {
            TXCRecordConfig tXCRecordConfig = this.mConfig;
            this.mCropWidth = tXCRecordConfig.f4402g;
            this.mCropHeight = tXCRecordConfig.f4403h;
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2486bd);
        } else if (i == 1) {
            int i2 = this.mConfig.f4402g;
            this.mCropHeight = (((int) ((i2 * 4.0f) / 3.0f)) / 16) * 16;
            this.mCropWidth = i2;
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2485bc);
        } else if (i != 2) {
        } else {
            int i3 = this.mConfig.f4402g;
            this.mCropHeight = i3;
            this.mCropWidth = i3;
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2484bb);
        }
    }

    private boolean checkLicenseMatch() {
        int m3112a = LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        if (m3112a != 0) {
            TXCLog.m2914e(TAG, "checkLicenseMatch, checkErrCode = " + m3112a);
            return false;
        } else if (LicenceCheck.m3120a().m3101c() != 2 || this.mSmartLicenseSupport) {
            return true;
        } else {
            TXCLog.m2914e(TAG, "checkLicenseMatch, called UnSupported method!");
            return true;
        }
    }

    private boolean isSmartLicense() {
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        if (LicenceCheck.m3120a().m3101c() == -1) {
            TXCLog.m2913i(TAG, "isSmartLicense, license type is = " + LicenceCheck.m3120a().m3101c());
            this.mSmartLicenseSupport = false;
        } else if (LicenceCheck.m3120a().m3101c() == 2) {
            return true;
        }
        return false;
    }

    public void snapshot(final TXRecordCommon.ITXSnapshotListener iTXSnapshotListener) {
        if (checkLicenseMatch() && !this.mSnapshotRunning && iTXSnapshotListener != null) {
            this.mSnapshotRunning = true;
            TXCGLSurfaceView tXCGLSurfaceView = this.mVideoView;
            if (tXCGLSurfaceView != null) {
                tXCGLSurfaceView.m1022a(new TXITakePhotoListener() { // from class: com.tencent.ugc.TXUGCRecord.11
                    @Override // com.tencent.liteav.basic.p109e.TXITakePhotoListener
                    public void onTakePhotoComplete(Bitmap bitmap) {
                        iTXSnapshotListener.onSnapshot(bitmap);
                    }
                });
            }
            this.mSnapshotRunning = false;
        }
    }

    public void setRecordSpeed(int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setRecordSpeed is not supported in UGC_Smart license");
            return;
        }
        this.mRecordSpeed = i;
        if (TextUtils.isEmpty(this.mBGMPath)) {
            return;
        }
        changeRecordSpeed();
    }

    public void setVideoProcessListener(VideoCustomProcessListener videoCustomProcessListener) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setVideoProcessListener is not supported in UGC_Smart license");
        } else {
            this.mCustomProcessListener = videoCustomProcessListener;
        }
    }

    public void setReverb(int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setReverb is not supported in UGC_Smart license");
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2497bo, i, "");
        TXCAudioUGCRecorder.getInstance().setReverbType(i);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2475az);
    }

    public void setVoiceChangerType(int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setVoiceChangerType is not supported in UGC_Smart license");
            return;
        }
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
            default:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = -1;
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
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2496bn, i, "");
        TXCAudioUGCRecorder.getInstance().setChangerType(this.mVoiceKind, this.mVoiceEnvironment);
    }

    public int setBGM(String str) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGM is not supported in UGC_Smart license");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e(TAG, "setBGM, path is empty");
            stopBGM();
            TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
            return 0;
        } else {
            this.mBGMPath = str;
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2495bm);
            if (this.mBGMNotifyProxy == null) {
                this.mBGMNotifyProxy = new TXIBGMOnPlayListener() { // from class: com.tencent.ugc.TXUGCRecord.12
                    @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                    /* renamed from: a */
                    public void mo275a() {
                        TXUGCRecord.this.mBGMPlayStop = false;
                        if (TXUGCRecord.this.mBGMNotify != null) {
                            TXUGCRecord.this.mBGMNotify.onBGMStart();
                        }
                    }

                    @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                    /* renamed from: a */
                    public void mo274a(int i) {
                        if (TXUGCRecord.this.mBGMNotify != null) {
                            TXUGCRecord.this.mBGMNotify.onBGMComplete(0);
                        }
                        TXUGCRecord.this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.12.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (TXUGCRecord.this.mRecording) {
                                    TXCUGCBGMPlayer.getInstance().stopPlay();
                                    TXCUGCBGMPlayer.getInstance().playFromTime(TXUGCRecord.this.mBGMStartTime, TXUGCRecord.this.mBGMEndTime);
                                    TXCUGCBGMPlayer.getInstance().startPlay(TXUGCRecord.this.mBGMPath);
                                }
                            }
                        });
                    }

                    @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                    /* renamed from: a */
                    public void mo273a(long j, long j2) {
                        if (TXUGCRecord.this.mBGMNotify != null) {
                            TXUGCRecord.this.mBGMNotify.onBGMProgress(j, j2);
                        }
                    }
                };
            }
            TXCUGCBGMPlayer.getInstance().setOnPlayListener(this.mBGMNotifyProxy);
            return (int) TXCUGCBGMPlayer.getDurationMS(str);
        }
    }

    public void setBGMNofify(TXRecordCommon.ITXBGMNotify iTXBGMNotify) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMNofify is not supported in UGC_Smart license");
        } else if (iTXBGMNotify == null) {
            this.mBGMNotify = null;
        } else {
            this.mBGMNotify = iTXBGMNotify;
        }
    }

    public boolean playBGMFromTime(int i, int i2) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "playBGMFromTime is not supported in UGC_Smart license");
            return false;
        } else if (TextUtils.isEmpty(this.mBGMPath)) {
            TXCLog.m2914e(TAG, "playBGMFromTime, path is empty");
            return false;
        } else if (i < 0 || i2 < 0) {
            TXCLog.m2914e(TAG, "playBGMFromTime, time is negative number");
            return false;
        } else if (i >= i2) {
            TXCLog.m2914e(TAG, "playBGMFromTime, start time is bigger than end time");
            return false;
        } else {
            long j = i;
            this.mBGMStartTime = j;
            long j2 = i2;
            this.mBGMEndTime = j2;
            this.mBGMDeletePart = false;
            this.mTXUGCPartsManager.setPartsManagerObserver(this);
            changeRecordSpeed();
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2424aA);
            if (TXCAudioUGCRecorder.getInstance().isRecording()) {
                TXCAudioUGCRecorder.getInstance().enableBGMRecord(true);
                TXCAudioUGCRecorder.getInstance().setChannels(1);
                TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
            }
            TXCUGCBGMPlayer.getInstance().playFromTime(j, j2);
            TXCUGCBGMPlayer.getInstance().startPlay(this.mBGMPath);
            return true;
        }
    }

    public boolean stopBGM() {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "stopBGM is not supported in UGC_Smart license");
            return false;
        }
        this.mBGMPath = null;
        this.mTXUGCPartsManager.removePartsManagerObserver(this);
        TXCUGCBGMPlayer.getInstance().stopPlay();
        TXCAudioUGCRecorder.getInstance().enableBGMRecord(false);
        TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
        return true;
    }

    public boolean pauseBGM() {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "pauseBGM is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().pause();
        return true;
    }

    public boolean resumeBGM() {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "resumeBGM is not supported in UGC_Smart license");
            return false;
        } else if (TextUtils.isEmpty(this.mBGMPath)) {
            TXCLog.m2914e(TAG, "resumeBGM, mBGMPath is empty");
            return false;
        } else {
            changeRecordSpeed();
            if (this.mBGMDeletePart) {
                long j = 0;
                if (this.mBgmPartBytesList.size() > 0) {
                    CopyOnWriteArrayList<Long> copyOnWriteArrayList = this.mBgmPartBytesList;
                    j = copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1).longValue();
                }
                TXCLog.m2913i(TAG, "resumeBGM, curBGMBytesProgress = " + j);
                if (this.mBGMPlayStop) {
                    TXCUGCBGMPlayer.getInstance().startPlay(this.mBGMPath);
                }
                TXCUGCBGMPlayer.getInstance().seekBytes(j);
                TXCAudioUGCRecorder.getInstance().clearCache();
            }
            TXCUGCBGMPlayer.getInstance().resume();
            return true;
        }
    }

    public boolean seekBGM(int i, int i2) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "seekBGM is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().playFromTime(i, i2);
        return true;
    }

    public boolean setBGMVolume(float f) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMVolume is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().setVolume(f);
        return true;
    }

    public int getMusicDuration(String str) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "getMusicDuration is not supported in UGC_Smart license");
            return 0;
        }
        return (int) TXCUGCBGMPlayer.getDurationMS(str);
    }

    public void setWatermark(Bitmap bitmap, TXVideoEditConstants.TXRect tXRect) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setWatermark is not supported in UGC_Smart license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null && bitmap != null && tXRect != null) {
            tXCVideoPreprocessor.m2625a(bitmap, tXRect.f5789x, tXRect.f5790y, tXRect.width);
        }
        this.mWatermarkBitmap = bitmap;
        this.mTxWaterMarkRect = tXRect;
    }

    public void setMotionTmpl(String str) {
        TXCVideoPreprocessor tXCVideoPreprocessor;
        if (LicenceCheck.m3120a().m3101c() >= 4 && (tXCVideoPreprocessor = this.mVideoPreprocessor) != null) {
            tXCVideoPreprocessor.m2618a(str);
        }
    }

    public void setMotionMute(boolean z) {
        TXCVideoPreprocessor tXCVideoPreprocessor;
        if (LicenceCheck.m3120a().m3101c() >= 4 && (tXCVideoPreprocessor = this.mVideoPreprocessor) != null) {
            tXCVideoPreprocessor.m2609b(z);
        }
    }

    @TargetApi(18)
    public void setGreenScreenFile(String str, boolean z) {
        if (Build.VERSION.SDK_INT < 18) {
            return;
        }
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setGreenScreenFile is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2617a(str, z);
    }

    public void setFaceVLevel(int i) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setFaceVLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2602i(i);
    }

    public void setFaceShortLevel(int i) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setFaceShortLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2601j(i);
    }

    public void setChinLevel(int i) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setChinLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2600k(i);
    }

    public void setNoseSlimLevel(int i) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setNoseSlimLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2599l(i);
    }

    public void setEyeScaleLevel(float f) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setEyeScaleLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2604g((int) f);
    }

    public void setFaceScaleLevel(float f) {
        if (LicenceCheck.m3120a().m3101c() != 5) {
            TXCLog.m2914e(TAG, "setFaceScaleLevel is only supported in EnterprisePro license");
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor == null) {
            return;
        }
        tXCVideoPreprocessor.m2603h((int) f);
    }

    public void setBeautyStyle(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2610b(i);
        }
    }

    public void setBeautyDepth(int i, int i2, int i3, int i4) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2610b(i);
            this.mVideoPreprocessor.m2608c(i2);
            this.mVideoPreprocessor.m2607d(i3);
            this.mVideoPreprocessor.m2606e(i4);
        }
    }

    public void setFilter(Bitmap bitmap) {
        setFilter(bitmap, this.mSpecialRadio, null, 0.0f, 1.0f);
    }

    public void setFilter(Bitmap bitmap, float f, Bitmap bitmap2, float f2, float f3) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2631a(f3, bitmap, f, bitmap2, f2);
        }
    }

    public void setSpecialRatio(float f) {
        this.mSpecialRadio = f;
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2632a(f);
        }
    }

    private void setSharpenLevel(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2605f(i);
        }
    }

    public boolean toggleTorch(boolean z) {
        TXCCameraCapturer tXCCameraCapturer = this.mCameraCapture;
        if (tXCCameraCapturer != null) {
            tXCCameraCapturer.m2401a(z);
            return true;
        }
        return true;
    }

    public int getMaxZoom() {
        return this.mCameraCapture.m2407a();
    }

    public boolean setZoom(int i) {
        TXCCameraCapturer tXCCameraCapturer = this.mCameraCapture;
        if (tXCCameraCapturer != null) {
            return tXCCameraCapturer.m2396c(i);
        }
        return false;
    }

    public void setFocusPosition(float f, float f2) {
        this.mTouchFocusRunnable.m272a(f, f2);
        this.mMainHandler.postDelayed(this.mTouchFocusRunnable, 100L);
    }

    public void setVideoRenderMode(int i) {
        if (i == 1) {
            this.mRenderMode = 1;
        } else {
            this.mRenderMode = 0;
        }
    }

    private int startCameraPreviewInternal(TXCloudVideoView tXCloudVideoView, TXCRecordConfig tXCRecordConfig) {
        TXCLog.m2913i(TAG, "ugcRecord, startCameraPreviewInternal");
        this.mStartPreview = true;
        TXCloudVideoView tXCloudVideoView2 = this.mTXCloudVideoView;
        if (tXCloudVideoView2 != null) {
            tXCloudVideoView2.removeVideoView();
            this.mTXCloudVideoView.removeFocusIndicatorView();
        }
        this.mTXCloudVideoView = tXCloudVideoView;
        initConfig();
        calcVideoEncInfo();
        initModules();
        this.mInitCompelete = false;
        this.mVideoView.setRendMode(this.mRenderMode);
        this.mVideoView.setSurfaceTextureListener(this);
        return 0;
    }

    public void setHomeOrientation(int i) {
        this.mCameraOrientationReadyChange = i;
        resetRotation();
    }

    public void setRenderRotation(int i) {
        this.mRenderRotationReadyChange = i;
        resetRotation();
    }

    private void resetRotation() {
        TXCGLSurfaceView tXCGLSurfaceView = this.mVideoView;
        if (tXCGLSurfaceView != null) {
            tXCGLSurfaceView.m1011b(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    if (TXUGCRecord.this.mRenderRotationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.f4414s = TXUGCRecord.this.mRenderRotationReadyChange;
                        TXUGCRecord.this.mRenderRotationReadyChange = -1;
                    }
                    if (TXUGCRecord.this.mCameraOrientationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.f4413r = TXUGCRecord.this.mCameraOrientationReadyChange;
                        TXUGCRecord.this.mCameraCapture.m2393d(TXUGCRecord.this.mConfig.f4413r);
                        TXUGCRecord.this.mCameraOrientationReadyChange = -1;
                    }
                }
            });
            return;
        }
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        tXCRecordConfig.f4414s = this.mRenderRotationReadyChange;
        tXCRecordConfig.f4413r = this.mCameraOrientationReadyChange;
    }

    @TargetApi(16)
    private void addAudioTrack() {
        MediaFormat m2892a = TXCSystemUtil.m2892a(TXCAudioUGCRecorder.getInstance().getSampleRate(), TXCAudioUGCRecorder.getInstance().getChannels(), 2);
        TXCMP4Muxer tXCMP4Muxer = this.mMP4Muxer;
        if (tXCMP4Muxer != null) {
            tXCMP4Muxer.mo1226b(m2892a);
        }
    }

    private void initModules() {
        this.mVideoView = this.mTXCloudVideoView.getGLSurfaceView();
        if (this.mVideoView == null) {
            this.mVideoView = new TXCGLSurfaceView(this.mTXCloudVideoView.getContext());
            this.mTXCloudVideoView.addVideoView(this.mVideoView);
        }
        if (this.mCameraCapture == null) {
            this.mCameraCapture = new TXCCameraCapturer();
        }
        this.mCameraCapture.m2403a(this.mConfig.f4412q ? 7 : this.mCameraResolution);
        this.mCameraCapture.m2399b(this.mConfig.f4398c);
        if (this.mVideoPreprocessor == null) {
            this.mVideoPreprocessor = new TXCVideoPreprocessor(this.mContext, true);
        }
        this.mVideoPreprocessor.m2620a((TXIVideoPreprocessorListener) this);
        this.mVideoEncoder = null;
    }

    private boolean startCapture(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            TXCLog.m2913i(TAG, "startCapture, mCapturing = " + this.mCapturing + ", mCameraCapture = " + this.mCameraCapture);
            if (surfaceTexture == null || this.mCapturing) {
                return false;
            }
            this.mCameraCapture.m2402a(surfaceTexture);
            this.mCameraCapture.m2399b(this.mConfig.f4398c);
            this.mCameraCapture.m2398b(this.mConfig.f4401f);
            TXCLog.m2913i(TAG, "startCapture, setHomeOriention = " + this.mConfig.f4413r);
            this.mCameraCapture.m2393d(this.mConfig.f4413r);
            if (this.mCameraCapture.m2395c(this.mConfig.f4410o) == 0) {
                this.mCapturing = true;
                if (this.mVideoView != null) {
                    this.mVideoView.setFPS(this.mConfig.f4398c);
                    this.mVideoView.setSurfaceTextureListener(this);
                    this.mVideoView.setNotifyListener(this);
                    this.mVideoView.mo1025a(this.mConfig.f4398c);
                }
                return true;
            }
            this.mCapturing = false;
            TXLog.m390e(TAG, "startCapture fail!");
            onRecordError();
            return false;
        }
    }

    private void stopEncoder(final TXCVideoEncoder tXCVideoEncoder) {
        TXCGLSurfaceView tXCGLSurfaceView = this.mVideoView;
        if (tXCGLSurfaceView != null) {
            tXCGLSurfaceView.m1011b(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (tXCVideoEncoder == null) {
                            return;
                        }
                        tXCVideoEncoder.m434a();
                        tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void startEncoder(int i, int i2) {
        TXCLog.m2913i(TAG, "New encode size width = " + i + " height = " + i2 + ", mVideoView = " + this.mVideoView);
        stopEncoder(this.mVideoEncoder);
        this.mVideoEncoder = null;
        EGLContext eglGetCurrentContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        tXSVideoEncoderParam.width = i;
        tXSVideoEncoderParam.height = i2;
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        tXSVideoEncoderParam.fps = tXCRecordConfig.f4398c;
        tXSVideoEncoderParam.fullIFrame = tXCRecordConfig.f4416u;
        tXSVideoEncoderParam.glContext = eglGetCurrentContext;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.appendSpsPps = false;
        if (this.mUseSWEncoder) {
            this.mVideoEncoder = new TXCVideoEncoder(2);
            tXSVideoEncoderParam.encoderMode = 1;
            tXSVideoEncoderParam.encoderProfile = 3;
        } else {
            this.mVideoEncoder = new TXCVideoEncoder(1);
            tXSVideoEncoderParam.encoderMode = 3;
            tXSVideoEncoderParam.encoderProfile = 1;
        }
        tXSVideoEncoderParam.record = true;
        TXCRecordConfig tXCRecordConfig2 = this.mConfig;
        if (tXCRecordConfig2.f4416u) {
            if (this.mUseSWEncoder) {
                this.mVideoEncoder.m433a(24000);
            } else {
                this.mVideoEncoder.m433a(15000);
            }
        } else {
            this.mVideoEncoder.m433a(tXCRecordConfig2.f4399d);
        }
        tXSVideoEncoderParam.realTime = true;
        tXSVideoEncoderParam.enableBlackList = false;
        this.mVideoEncoder.m419a((TXIVideoEncoderListener) this);
        this.mVideoEncoder.m427a(tXSVideoEncoderParam);
    }

    private void encodeFrame(int i, int i2, int i3) {
        if (this.mVideoEncoder == null || this.mVideoWidth != i2 || this.mVideoHeight != i3) {
            startEncoder(i2, i3);
        }
        this.mVideoEncoder.m431a(i, i2, i3, TXCTimeUtil.getTimeTick());
    }

    private void onRecordError() {
        if (this.mVideoRecordListener == null || !this.mRecording) {
            return;
        }
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.2
            @Override // java.lang.Runnable
            public void run() {
                TXUGCRecord.this.stopRecordForClip();
            }
        });
        TXCUGCBGMPlayer.getInstance().pause();
        this.mRecording = false;
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.3
            @Override // java.lang.Runnable
            public void run() {
                TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                tXRecordResult.descMsg = "record video failed";
                tXRecordResult.retCode = -1;
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordComplete(tXRecordResult);
                }
            }
        });
    }

    private void initConfig() {
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        int i = tXCRecordConfig.f4396a;
        if (i >= 0) {
            if (i == 0) {
                tXCRecordConfig.f4397b = 0;
                tXCRecordConfig.f4402g = 360;
                tXCRecordConfig.f4403h = 640;
                tXCRecordConfig.f4399d = 2400;
                this.mCameraResolution = 4;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2487be);
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2491bi, 2400, "");
            } else if (i == 1) {
                tXCRecordConfig.f4397b = 1;
                tXCRecordConfig.f4402g = 540;
                tXCRecordConfig.f4403h = 960;
                tXCRecordConfig.f4399d = 6500;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2488bf);
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2491bi, 6500, "");
            } else if (i == 2) {
                tXCRecordConfig.f4397b = 2;
                tXCRecordConfig.f4402g = 720;
                tXCRecordConfig.f4403h = 1280;
                tXCRecordConfig.f4399d = 9600;
                this.mCameraResolution = 6;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2489bg);
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2491bi, 9600, "");
            } else {
                tXCRecordConfig.f4397b = 1;
                tXCRecordConfig.f4402g = 540;
                tXCRecordConfig.f4403h = 960;
                tXCRecordConfig.f4399d = 6500;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2488bf);
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2491bi, 6500, "");
            }
            TXCRecordConfig tXCRecordConfig2 = this.mConfig;
            int i2 = this.mFps;
            tXCRecordConfig2.f4398c = i2;
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2492bj, i2, "");
        } else {
            int i3 = tXCRecordConfig.f4397b;
            if (i3 == 0) {
                tXCRecordConfig.f4402g = 360;
                tXCRecordConfig.f4403h = 640;
                this.mCameraResolution = 4;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2487be, 360, "360x640");
            } else if (i3 == 1) {
                tXCRecordConfig.f4402g = 540;
                tXCRecordConfig.f4403h = 960;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2488bf, 540, "540x960");
            } else if (i3 == 2) {
                tXCRecordConfig.f4402g = 720;
                tXCRecordConfig.f4403h = 1280;
                this.mCameraResolution = 6;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2489bg, 720, "720x1280");
            } else if (i3 == 3) {
                tXCRecordConfig.f4402g = 1080;
                tXCRecordConfig.f4403h = 1920;
                this.mCameraResolution = 7;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2490bh, 1080, "1080x1920");
            } else {
                tXCRecordConfig.f4402g = 540;
                tXCRecordConfig.f4403h = 960;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2488bf, 720, "720x1280");
            }
        }
        TXCLog.m2911w(TAG, "record:camera init record param, width:" + this.mConfig.f4402g + ",height:" + this.mConfig.f4403h + ",bitrate:" + this.mConfig.f4399d + ",fps:" + this.mConfig.f4398c);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x004a, code lost:
        if (r5 < 0) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void calcVideoEncInfo() {
        int i;
        TXCRecordConfig tXCRecordConfig = this.mConfig;
        int i2 = tXCRecordConfig.f4403h;
        if (i2 == 0) {
            return;
        }
        int i3 = tXCRecordConfig.f4402g;
        double d = i3 / i2;
        tXCRecordConfig.f4402g = ((i3 + 15) / 16) * 16;
        tXCRecordConfig.f4403h = ((i2 + 15) / 16) * 16;
        int i4 = tXCRecordConfig.f4402g;
        int i5 = tXCRecordConfig.f4403h;
        double d2 = (i4 - 16) / i5;
        double d3 = (i4 / i5) - d;
        double d4 = ((i4 + 16) / i5) - d;
        if (Math.abs(d3) < Math.abs(d4)) {
            int i6 = (Math.abs(d3) > Math.abs(d2 - d) ? 1 : (Math.abs(d3) == Math.abs(d2 - d) ? 0 : -1));
            i = this.mConfig.f4402g;
        } else {
            int i7 = (Math.abs(d4) > Math.abs(d2 - d) ? 1 : (Math.abs(d4) == Math.abs(d2 - d) ? 0 : -1));
            i = this.mConfig.f4402g;
            if (i7 < 0) {
                i += 16;
                tXCRecordConfig.f4402g = i;
            }
            i -= 16;
            tXCRecordConfig.f4402g = i;
        }
    }

    private boolean onRecordProgress(long j) {
        float f;
        float f2;
        TXCLog.m2913i(TAG, "onRecordProgress = " + j);
        int i = this.mRecordSpeed;
        if (i != 2) {
            if (i == 3) {
                f = (float) j;
                f2 = ENCODE_SPEED_FAST;
            } else if (i == 4) {
                f = (float) j;
                f2 = ENCODE_SPEED_FASTEST;
            } else if (i == 1) {
                f = (float) j;
                f2 = ENCODE_SPEED_SLOW;
            } else if (i == 0) {
                f = (float) j;
                f2 = ENCODE_SPEED_SLOWEST;
            }
            j = f / f2;
        }
        this.mCurrentRecordDuration = j;
        final long duration = this.mTXUGCPartsManager.getDuration() + this.mCurrentRecordDuration;
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.4
            @Override // java.lang.Runnable
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordProgress(duration);
                }
            }
        });
        if (duration >= this.mMaxDuration) {
            this.isReachedMaxDuration = true;
            this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    TXUGCRecord.this.stopRecordForClip();
                }
            });
            return false;
        }
        this.isReachedMaxDuration = false;
        return true;
    }

    private int getSreenRotation() {
        Context context = this.mContext;
        return (context == null || context.getResources().getConfiguration().orientation != 2) ? 0 : 90;
    }

    private int getRecordState() {
        int minBufferSize = AudioRecord.getMinBufferSize(TXRecordCommon.AUDIO_SAMPLERATE_44100, 16, 2);
        AudioRecord audioRecord = new AudioRecord(0, TXRecordCommon.AUDIO_SAMPLERATE_44100, 16, 2, minBufferSize * 100);
        short[] sArr = new short[minBufferSize];
        try {
            audioRecord.startRecording();
            if (audioRecord.getRecordingState() != 3) {
                audioRecord.stop();
                audioRecord.release();
                TXCLog.m2913i("CheckAudioPermission", "录音机被占用");
                return 1;
            } else if (audioRecord.read(sArr, 0, sArr.length) <= 0) {
                audioRecord.stop();
                audioRecord.release();
                TXCLog.m2913i("CheckAudioPermission", "录音的结果为空");
                return -1;
            } else {
                audioRecord.stop();
                audioRecord.release();
                return 0;
            }
        } catch (Exception unused) {
            audioRecord.release();
            TXCLog.m2913i("CheckAudioPermission", "无法进入录音初始状态");
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackEvent(final int i, final Bundle bundle) {
        this.mMainHandler.post(new Runnable() { // from class: com.tencent.ugc.TXUGCRecord.6
            @Override // java.lang.Runnable
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordEvent(i, bundle);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        TXCMP4Muxer tXCMP4Muxer = this.mMP4Muxer;
        if (tXCMP4Muxer == null || !this.mRecording) {
            return;
        }
        tXCMP4Muxer.mo1228a(bArr, 0, bArr.length, j * 1000, 0);
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordError(int i, String str) {
        if (i == TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT) {
            TXLog.m390e(TAG, "onRecordError, audio no mic permit");
            onRecordError();
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        TXCLog.m2913i(TAG, "ugcRecord, onSurfaceTextureAvailable, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing + ", mStartPreview = " + this.mStartPreview);
        if (this.mStartPreview && surfaceTexture != null) {
            if (startCapture(surfaceTexture)) {
                if (TXCAudioUGCRecorder.getInstance().isRecording()) {
                    this.mInitCompelete = true;
                    return;
                } else if (getRecordState() == -1) {
                    callbackEvent(4, null);
                }
            } else {
                callbackEvent(3, null);
            }
            this.mInitCompelete = true;
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public void onSurfaceTextureDestroy(SurfaceTexture surfaceTexture) {
        TXCLog.m2913i(TAG, "ugcRecord, onSurfaceTextureDestroy, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing);
        VideoCustomProcessListener videoCustomProcessListener = this.mCustomProcessListener;
        if (videoCustomProcessListener != null) {
            videoCustomProcessListener.onTextureDestroyed();
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.mVideoPreprocessor;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2633a();
        }
        TXCVideoEncoder tXCVideoEncoder = this.mVideoEncoder;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m434a();
            this.mVideoEncoder.m419a((TXIVideoEncoderListener) null);
            this.mVideoEncoder = null;
        }
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public int willAddWatermark(int i, int i2, int i3) {
        VideoCustomProcessListener videoCustomProcessListener = this.mCustomProcessListener;
        if (videoCustomProcessListener != null) {
            i = videoCustomProcessListener.onTextureCustomProcess(i, i2, i3);
        }
        TXCGLSurfaceView tXCGLSurfaceView = this.mVideoView;
        if (tXCGLSurfaceView != null) {
            tXCGLSurfaceView.mo1023a(i, false, this.mConfig.f4414s, i2, i3);
        }
        return i;
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(int i, int i2, int i3, long j) {
        if (this.mRecording) {
            encodeFrame(i, i2, i3);
        }
    }

    public void didDetectFacePoints(float[] fArr) {
        VideoCustomProcessListener videoCustomProcessListener = this.mCustomProcessListener;
        if (videoCustomProcessListener != null) {
            videoCustomProcessListener.onDetectFacePoints(fArr);
        }
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
        if (i == 0) {
            synchronized (this) {
                if (this.mMP4Muxer == null) {
                    return;
                }
                if (tXSNALPacket != null && tXSNALPacket.nalData != null) {
                    if (this.mStartMuxer) {
                        recordVideoData(tXSNALPacket, tXSNALPacket.nalData);
                    } else if (tXSNALPacket.nalType == 0) {
                        MediaFormat m2882a = TXCSystemUtil.m2882a(tXSNALPacket.nalData, this.mVideoWidth, this.mVideoHeight);
                        if (m2882a != null) {
                            this.mMP4Muxer.mo1232a(m2882a);
                            this.mMP4Muxer.mo1235a();
                            this.mStartMuxer = true;
                            this.mRecordStartTime = 0L;
                            TXLog.m389i(TAG, "onEncodeNAL, mMP4Muxer.start(), mStartMuxer = true");
                        }
                        recordVideoData(tXSNALPacket, tXSNALPacket.nalData);
                    }
                }
                return;
            }
        }
        TXCLog.m2914e(TAG, "onEncodeNAL error: " + i);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeFormat(MediaFormat mediaFormat) {
        synchronized (this) {
            Log.i(TAG, "onEncodeFormat: " + mediaFormat.toString());
            if (this.mMP4Muxer != null) {
                this.mMP4Muxer.mo1232a(mediaFormat);
                if (!this.mStartMuxer) {
                    this.mMP4Muxer.mo1235a();
                    this.mStartMuxer = true;
                    TXLog.m389i(TAG, "onEncodeFormat, mMP4Muxer.start(), mStartMuxer = true");
                }
            }
        }
    }

    private void recordVideoData(TXSNALPacket tXSNALPacket, byte[] bArr) {
        int i;
        if (this.mRecordStartTime == 0) {
            this.mRecordStartTime = tXSNALPacket.pts;
        }
        MediaCodec.BufferInfo bufferInfo = tXSNALPacket.info;
        if (bufferInfo == null) {
            i = tXSNALPacket.nalType == 0 ? 1 : 0;
        } else {
            i = bufferInfo.flags;
        }
        if (onRecordProgress(tXSNALPacket.pts - this.mRecordStartTime)) {
            this.mMP4Muxer.mo1224b(bArr, 0, bArr.length, tXSNALPacket.pts * 1000, i);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public int onTextureProcess(int i, float[] fArr) {
        if (this.mVideoPreprocessor != null) {
            TXCRecordConfig tXCRecordConfig = this.mConfig;
            int i2 = tXCRecordConfig.f4402g;
            int i3 = tXCRecordConfig.f4403h;
            int i4 = this.mCropWidth;
            int i5 = this.mCropHeight;
            int i6 = tXCRecordConfig.f4413r;
            if (i6 == 2 || i6 == 0) {
                TXCRecordConfig tXCRecordConfig2 = this.mConfig;
                i2 = tXCRecordConfig2.f4403h;
                i3 = tXCRecordConfig2.f4402g;
                i4 = this.mCropHeight;
                i5 = this.mCropWidth;
            }
            if (this.mDisplayType != 0) {
                this.mVideoPreprocessor.m2623a(TXCSystemUtil.m2891a(this.mCameraCapture.m2392e(), this.mCameraCapture.m2390f(), this.mCropHeight, this.mCropWidth));
                this.mVideoPreprocessor.m2629a(i4, i5);
                this.mVideoView.setRendMode(1);
            } else {
                int m2392e = this.mCameraCapture.m2392e();
                int m2390f = this.mCameraCapture.m2390f();
                TXCRecordConfig tXCRecordConfig3 = this.mConfig;
                this.mVideoPreprocessor.m2623a(TXCSystemUtil.m2891a(m2392e, m2390f, tXCRecordConfig3.f4403h, tXCRecordConfig3.f4402g));
                this.mVideoPreprocessor.m2629a(i2, i3);
                this.mVideoView.setRendMode(this.mRenderMode);
            }
            this.mVideoPreprocessor.m2615a(false);
            this.mVideoPreprocessor.m2630a(this.mCameraCapture.m2397c());
            this.mVideoPreprocessor.m2613a(fArr);
            this.mVideoPreprocessor.m2627a(i, this.mCameraCapture.m2392e(), this.mCameraCapture.m2390f(), this.mCameraCapture.m2397c(), 4, 0);
        }
        return 0;
    }
}
