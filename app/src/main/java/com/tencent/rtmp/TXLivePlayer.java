package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.TXCPlayerConfig;
import com.tencent.liteav.TXCTimeShiftUtil;
import com.tencent.liteav.TXIPlayer;
import com.tencent.liteav.TXIVideoRawDataListener;
import com.tencent.liteav.TXSDKUtil;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;

/* loaded from: classes3.dex */
public class TXLivePlayer implements TXINotifyListener {
    public static final int PLAY_TYPE_LIVE_FLV = 1;
    public static final int PLAY_TYPE_LIVE_RTMP = 0;
    public static final int PLAY_TYPE_LIVE_RTMP_ACC = 5;
    public static final int PLAY_TYPE_LOCAL_VIDEO = 6;
    public static final int PLAY_TYPE_VOD_FLV = 2;
    public static final int PLAY_TYPE_VOD_HLS = 3;
    public static final int PLAY_TYPE_VOD_MP4 = 4;
    public static final String TAG = "TXLivePlayer";
    private ITXAudioRawDataListener mAudioRawDataListener;
    private TXLivePlayConfig mConfig;
    private Context mContext;
    private boolean mIsShiftPlaying;
    private String mLivePlayUrl;
    private TXIPlayer mPlayer;
    private long mProgressStartTime;
    private int mRenderMode;
    private int mRenderRotation;
    private Surface mSurface;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    private TXCloudVideoView mTXCloudVideoView;
    private TXCTimeShiftUtil mTimeShiftUtil;
    private boolean mEnableHWDec = false;
    private boolean mIsNeedClearLastImg = true;
    private String mPlayUrl = "";
    private boolean mMute = false;
    private ITXVideoRawDataListener mVideoRawDataListener = null;
    private boolean mAutoPlay = true;
    private float mRate = 1.0f;
    private boolean mSnapshotRunning = false;
    private int mAudioRoute = 0;
    private int mCurrentPlayType = -1;
    private ITXLivePlayListener mListener = null;

    /* loaded from: classes3.dex */
    public interface ITXAudioRawDataListener {
        void onAudioInfoChanged(int i, int i2, int i3);

        void onPcmDataAvailable(byte[] bArr, long j);
    }

    /* loaded from: classes3.dex */
    public interface ITXSnapshotListener {
        void onSnapshot(Bitmap bitmap);
    }

    /* loaded from: classes3.dex */
    public interface ITXVideoRawDataListener {
        void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3);
    }

    public TXLivePlayer(Context context) {
        TXCLog.init();
        this.mContext = context.getApplicationContext();
    }

    public void setConfig(TXLivePlayConfig tXLivePlayConfig) {
        TXCLog.m2915d(TAG, "liteav_api setConfig " + this);
        this.mConfig = tXLivePlayConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXLivePlayConfig();
        }
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            TXCPlayerConfig m815p = tXIPlayer.m815p();
            if (m815p == null) {
                m815p = new TXCPlayerConfig();
            }
            TXLivePlayConfig tXLivePlayConfig2 = this.mConfig;
            m815p.f4339a = tXLivePlayConfig2.mCacheTime;
            m815p.f4345g = tXLivePlayConfig2.mAutoAdjustCacheTime;
            m815p.f4341c = tXLivePlayConfig2.mMinAutoAdjustCacheTime;
            m815p.f4340b = tXLivePlayConfig2.mMaxAutoAdjustCacheTime;
            m815p.f4342d = tXLivePlayConfig2.mVideoBlockThreshold;
            m815p.f4343e = tXLivePlayConfig2.mConnectRetryCount;
            m815p.f4344f = tXLivePlayConfig2.mConnectRetryInterval;
            m815p.f4346h = tXLivePlayConfig2.mEnableAec;
            m815p.f4348j = tXLivePlayConfig2.mEnableNearestIP;
            m815p.f4350l = tXLivePlayConfig2.mRtmpChannelType;
            m815p.f4347i = this.mEnableHWDec;
            m815p.f4351m = tXLivePlayConfig2.mCacheFolderPath;
            m815p.f4352n = tXLivePlayConfig2.mMaxCacheItems;
            m815p.f4349k = tXLivePlayConfig2.mEnableMessage;
            m815p.f4354p = tXLivePlayConfig2.mHeaders;
            this.mPlayer.mo836a(m815p);
        }
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        TXCLog.m2915d(TAG, "liteav_api setPlayerView old view : " + this.mTXCloudVideoView + ", new view : " + tXCloudVideoView + ", " + this);
        this.mTXCloudVideoView = tXCloudVideoView;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo833a(tXCloudVideoView);
        }
    }

    public void setSurface(Surface surface) {
        TXCLog.m2915d(TAG, "liteav_api setSurface old : " + this.mSurface + ", new : " + surface + ", " + this);
        this.mSurface = surface;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo838a(this.mSurface);
        }
    }

    public void setSurfaceSize(int i, int i2) {
        this.mSurfaceWidth = i;
        this.mSurfaceHeight = i2;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo840a(i, i2);
        }
    }

    public int startPlay(String str, int i) {
        TXCLog.m2915d(TAG, "liteav_api startPlay " + this);
        if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e(TAG, "start play error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty(this.mPlayUrl) && isPlaying()) {
            if (this.mPlayUrl.equalsIgnoreCase(str)) {
                TXCLog.m2914e(TAG, "start play error when new url is the same with old url  " + this);
                return -1;
            }
            TXCLog.m2911w(TAG, " stop old play when new url is not the same with old url  " + this);
            TXIPlayer tXIPlayer = this.mPlayer;
            if (tXIPlayer != null) {
                tXIPlayer.mo829a(false);
            }
            this.mPlayUrl = "";
        }
        TXCDRApi.initCrashReport(this.mContext);
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "=====  StartPlay url = " + str + " playType = " + i + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        int i2 = this.mCurrentPlayType;
        if (i2 == -1 || i2 != i) {
            this.mPlayer = TXSDKUtil.m622a(this.mContext, i);
        }
        this.mCurrentPlayType = i;
        if (this.mPlayer == null) {
            return -2;
        }
        this.mPlayUrl = checkPlayUrl(str, i);
        setConfig(this.mConfig);
        TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
        }
        this.mPlayer.mo833a(this.mTXCloudVideoView);
        this.mPlayer.m837a(this);
        this.mPlayer.mo820c(this.mAutoPlay);
        Surface surface = this.mSurface;
        if (surface != null) {
            this.mPlayer.mo838a(surface);
            this.mPlayer.mo840a(this.mSurfaceWidth, this.mSurfaceHeight);
        }
        this.mPlayer.mo830a(this.mPlayUrl, i);
        this.mPlayer.mo823b(this.mMute);
        this.mPlayer.mo825b(this.mRate);
        this.mPlayer.mo824b(this.mRenderRotation);
        this.mPlayer.mo841a(this.mRenderMode);
        setAudioRoute(this.mAudioRoute);
        this.mPlayer.mo834a(this.mAudioRawDataListener);
        setVideoRawDataListener(this.mVideoRawDataListener);
        if (this.mPlayer.mo817f()) {
            this.mLivePlayUrl = this.mPlayUrl;
            TXCTimeShiftUtil tXCTimeShiftUtil = this.mTimeShiftUtil;
            this.mProgressStartTime = tXCTimeShiftUtil != null ? tXCTimeShiftUtil.m1261a() : 0L;
            if (this.mProgressStartTime > 0) {
                this.mPlayer.mo816g();
            }
        }
        return 0;
    }

    public int switchStream(String str) {
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            return tXIPlayer.mo831a(str);
        }
        return -1;
    }

    public int stopPlay(boolean z) {
        TXCloudVideoView tXCloudVideoView;
        TXCLog.m2915d(TAG, "liteav_api stopPlay " + z + ", " + this);
        if (z && (tXCloudVideoView = this.mTXCloudVideoView) != null) {
            tXCloudVideoView.setVisibility(8);
        }
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo829a(z);
        }
        this.mPlayUrl = "";
        this.mProgressStartTime = 0L;
        this.mTimeShiftUtil = null;
        this.mIsShiftPlaying = false;
        return 0;
    }

    public boolean isPlaying() {
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            return tXIPlayer.mo822c();
        }
        return false;
    }

    public void pause() {
        TXCLog.m2915d(TAG, "liteav_api pause " + this);
        if (this.mPlayer != null) {
            TXCLog.m2911w(TAG, "pause play");
            this.mPlayer.mo842a();
        }
    }

    public void resume() {
        TXCLog.m2915d(TAG, "liteav_api resume " + this);
        if (this.mPlayer != null) {
            TXCLog.m2911w(TAG, "resume play");
            this.mPlayer.mo826b();
            setAudioRoute(this.mAudioRoute);
        }
    }

    public void seek(int i) {
        TXCLog.m2915d(TAG, "liteav_api ");
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            if (tXIPlayer.mo817f() || this.mIsShiftPlaying) {
                TXCTimeShiftUtil tXCTimeShiftUtil = this.mTimeShiftUtil;
                String m1260a = tXCTimeShiftUtil != null ? tXCTimeShiftUtil.m1260a(i) : "";
                if (!TextUtils.isEmpty(m1260a)) {
                    this.mIsShiftPlaying = startPlay(m1260a, 3) == 0;
                    if (!this.mIsShiftPlaying) {
                        return;
                    }
                    this.mProgressStartTime = i * 1000;
                    return;
                }
                ITXLivePlayListener iTXLivePlayListener = this.mListener;
                if (iTXLivePlayListener == null) {
                    return;
                }
                iTXLivePlayListener.onPlayEvent(TXLiveConstants.PLAY_ERR_NET_DISCONNECT, new Bundle());
                return;
            }
            this.mPlayer.mo827a_(i);
        }
    }

    public int prepareLiveSeek(String str, int i) {
        TXCLog.m2915d(TAG, "liteav_api prepareLiveSeek " + this);
        if (this.mTimeShiftUtil == null) {
            this.mTimeShiftUtil = new TXCTimeShiftUtil(this.mContext);
        }
        TXCTimeShiftUtil tXCTimeShiftUtil = this.mTimeShiftUtil;
        if (tXCTimeShiftUtil != null) {
            return tXCTimeShiftUtil.m1255a(this.mPlayUrl, str, i, new TXCTimeShiftUtil.AbstractC3554a() { // from class: com.tencent.rtmp.TXLivePlayer.1
                @Override // com.tencent.liteav.TXCTimeShiftUtil.AbstractC3554a
                public void onLiveTime(long j) {
                    TXLivePlayer.this.mProgressStartTime = j;
                    if (TXLivePlayer.this.mPlayer != null) {
                        TXLivePlayer.this.mPlayer.mo816g();
                    }
                }
            });
        }
        return -1;
    }

    public int resumeLive() {
        TXCLog.m2915d(TAG, "liteav_api resumeLive " + this);
        if (this.mIsShiftPlaying) {
            this.mIsShiftPlaying = false;
            return startPlay(this.mLivePlayUrl, 1);
        }
        return -1;
    }

    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        TXCLog.m2915d(TAG, "liteav_api setPlayListener " + this);
        this.mListener = iTXLivePlayListener;
    }

    public void setVideoRecordListener(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        TXCLog.m2915d(TAG, "liteav_api setVideoRecordListener");
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo832a(iTXVideoRecordListener);
        }
    }

    public int startRecord(int i) {
        TXCLog.m2915d(TAG, "liteav_api startRecord " + this);
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.m2914e(TAG, "API levl is too low (record need 18, current is" + Build.VERSION.SDK_INT + ")");
            return -3;
        } else if (!isPlaying()) {
            TXCLog.m2914e(TAG, "startRecord: there is no playing stream");
            return -1;
        } else {
            TXIPlayer tXIPlayer = this.mPlayer;
            if (tXIPlayer == null) {
                return -1;
            }
            return tXIPlayer.mo821c(i);
        }
    }

    public int stopRecord() {
        TXCLog.m2915d(TAG, "liteav_api stopRecord " + this);
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            return tXIPlayer.mo818e();
        }
        return -1;
    }

    public void setRenderMode(int i) {
        TXCLog.m2915d(TAG, "liteav_api setRenderMode " + i);
        this.mRenderMode = i;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo841a(i);
        }
    }

    public void setRenderRotation(int i) {
        TXCLog.m2915d(TAG, "liteav_api setRenderRotation " + i);
        this.mRenderRotation = i;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo824b(i);
        }
    }

    public boolean enableHardwareDecode(boolean z) {
        TXCLog.m2915d(TAG, "liteav_api enableHardwareDecode " + z);
        if (z) {
            if (Build.VERSION.SDK_INT < 18) {
                TXCLog.m2914e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + Build.VERSION.SDK_INT + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            } else if (isAVCDecBlacklistDevices()) {
                TXCLog.m2914e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + Build.MANUFACTURER + ", MODEL" + Build.MODEL);
                return false;
            }
        }
        this.mEnableHWDec = z;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            TXCPlayerConfig m815p = tXIPlayer.m815p();
            if (m815p == null) {
                m815p = new TXCPlayerConfig();
            }
            m815p.f4347i = this.mEnableHWDec;
            this.mPlayer.mo836a(m815p);
            return true;
        }
        return true;
    }

    public void setMute(boolean z) {
        TXCLog.m2915d(TAG, "liteav_api setMute " + z);
        this.mMute = z;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo823b(z);
        }
    }

    public void setAutoPlay(boolean z) {
        TXCLog.m2915d(TAG, "liteav_api setAutoPlay " + z);
        this.mAutoPlay = z;
    }

    public void setRate(float f) {
        TXCLog.m2915d(TAG, "liteav_api setRate " + f);
        this.mRate = f;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo825b(f);
        }
    }

    public void setAudioRoute(int i) {
        TXCLog.m2915d(TAG, "liteav_api setAudioRoute " + i);
        this.mAudioRoute = i;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo839a(this.mContext, this.mAudioRoute);
        }
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 15001) {
            TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
            if (tXCloudVideoView != null) {
                tXCloudVideoView.setLogText(bundle, null, 0);
            }
            ITXLivePlayListener iTXLivePlayListener = this.mListener;
            if (iTXLivePlayListener == null) {
                return;
            }
            iTXLivePlayListener.onNetStatus(bundle);
        } else if (i == 2005) {
            long j = bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS) + this.mProgressStartTime;
            if (j <= 0) {
                return;
            }
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (j / 1000));
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) j);
            ITXLivePlayListener iTXLivePlayListener2 = this.mListener;
            if (iTXLivePlayListener2 == null) {
                return;
            }
            iTXLivePlayListener2.onPlayEvent(i, bundle);
        } else {
            TXCloudVideoView tXCloudVideoView2 = this.mTXCloudVideoView;
            if (tXCloudVideoView2 != null) {
                tXCloudVideoView2.setLogText(null, bundle, i);
            }
            ITXLivePlayListener iTXLivePlayListener3 = this.mListener;
            if (iTXLivePlayListener3 == null) {
                return;
            }
            iTXLivePlayListener3.onPlayEvent(i, bundle);
        }
    }

    public boolean addVideoRawData(byte[] bArr) {
        TXCLog.m2915d(TAG, "liteav_api addVideoRawData " + bArr);
        String str = this.mPlayUrl;
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (this.mEnableHWDec) {
            TXLog.m390e(TAG, "can not addVideoRawData because of hw decode has set!");
            return false;
        }
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer == null) {
            TXCLog.m2914e(TAG, "player hasn't created or not instanceof live player");
            return false;
        }
        tXIPlayer.mo828a(bArr);
        return true;
    }

    public void setVideoRawDataListener(final ITXVideoRawDataListener iTXVideoRawDataListener) {
        TXCLog.m2915d(TAG, "liteav_api setVideoRawDataListener " + iTXVideoRawDataListener);
        this.mVideoRawDataListener = iTXVideoRawDataListener;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer == null) {
            return;
        }
        if (iTXVideoRawDataListener != null) {
            tXIPlayer.mo835a(new TXIVideoRawDataListener() { // from class: com.tencent.rtmp.TXLivePlayer.2
                @Override // com.tencent.liteav.TXIVideoRawDataListener
                public void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3) {
                    iTXVideoRawDataListener.onVideoRawDataAvailable(bArr, i, i2, i3);
                }
            });
        } else {
            tXIPlayer.mo835a((TXIVideoRawDataListener) null);
        }
    }

    public void snapshot(ITXSnapshotListener iTXSnapshotListener) {
        Bitmap bitmap;
        Bitmap bitmap2;
        TXCLog.m2915d(TAG, "liteav_api snapshot " + iTXSnapshotListener);
        if (this.mSnapshotRunning || iTXSnapshotListener == null) {
            return;
        }
        this.mSnapshotRunning = true;
        TXIPlayer tXIPlayer = this.mPlayer;
        TextureView mo819d = tXIPlayer != null ? tXIPlayer.mo819d() : null;
        if (mo819d != null) {
            try {
                bitmap = mo819d.getBitmap();
            } catch (OutOfMemoryError unused) {
                bitmap = null;
            }
            if (bitmap != null) {
                bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mo819d.getTransform(null), true);
                bitmap.recycle();
            } else {
                bitmap2 = bitmap;
            }
            postBitmapToMainThread(iTXSnapshotListener, bitmap2);
            return;
        }
        this.mSnapshotRunning = false;
    }

    public void setAudioRawDataListener(ITXAudioRawDataListener iTXAudioRawDataListener) {
        TXCLog.m2915d(TAG, "liteav_api setAudioRawDataListener " + iTXAudioRawDataListener);
        this.mAudioRawDataListener = iTXAudioRawDataListener;
        TXIPlayer tXIPlayer = this.mPlayer;
        if (tXIPlayer != null) {
            tXIPlayer.mo834a(iTXAudioRawDataListener);
        }
    }

    private boolean isAVCDecBlacklistDevices() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String checkPlayUrl(String str, int i) {
        if (i != 6) {
            try {
                byte[] bytes = str.getBytes("UTF-8");
                StringBuilder sb = new StringBuilder(bytes.length);
                for (int i2 = 0; i2 < bytes.length; i2++) {
                    int i3 = bytes[i2] < 0 ? bytes[i2] + 256 : bytes[i2];
                    if (i3 > 32 && i3 < 127 && i3 != 34 && i3 != 37 && i3 != 60 && i3 != 62 && i3 != 91 && i3 != 125 && i3 != 92 && i3 != 93 && i3 != 94 && i3 != 96 && i3 != 123 && i3 != 124) {
                        sb.append((char) i3);
                    }
                    sb.append(String.format("%%%02X", Integer.valueOf(i3)));
                }
                str = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.trim();
    }

    private void postBitmapToMainThread(final ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.TXLivePlayer.3
            @Override // java.lang.Runnable
            public void run() {
                ITXSnapshotListener iTXSnapshotListener2 = iTXSnapshotListener;
                if (iTXSnapshotListener2 != null) {
                    iTXSnapshotListener2.onSnapshot(bitmap);
                }
                TXLivePlayer.this.mSnapshotRunning = false;
            }
        });
    }
}
