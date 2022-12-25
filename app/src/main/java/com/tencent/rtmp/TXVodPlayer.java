package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.TXCPlayerConfig;
import com.tencent.liteav.TXCVodPlayer;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCVodPlayerNetApi;
import com.tencent.liteav.network.TXCVodPlayerNetListener;
import com.tencent.liteav.network.TXPlayInfoResponse;
import com.tencent.liteav.txcvodplayer.TextureRenderView;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TXVodPlayer implements TXINotifyListener, TXCVodPlayerNetListener {
    public static final int PLAYER_TYPE_EXO = 1;
    public static final int PLAYER_TYPE_FFPLAY = 0;
    public static final String TAG = "TXVodPlayer";
    private int mBitrateIndex;
    private TXVodPlayConfig mConfig;
    private Context mContext;
    private boolean mIsGetPlayInfo;
    private boolean mLoop;
    private boolean mMirror;
    private TXCVodPlayerNetApi mNetApi;
    private TXCVodPlayer mPlayer;
    private int mRenderMode;
    private int mRenderRotation;
    protected float mStartTime;
    private Surface mSurface;
    private TXCloudVideoView mTXCloudVideoView;
    private TextureRenderView mTextureView;
    private String mToken;
    private boolean mEnableHWDec = false;
    private boolean mIsNeedClearLastImg = true;
    private String mPlayUrl = "";
    private boolean mMute = false;
    private boolean mAutoPlay = true;
    private float mRate = 1.0f;
    private boolean mSnapshotRunning = false;
    private ITXLivePlayListener mListener = null;
    private ITXVodPlayListener mNewListener = null;

    public TXVodPlayer(Context context) {
        TXCLog.init();
        this.mContext = context.getApplicationContext();
    }

    public void setConfig(TXVodPlayConfig tXVodPlayConfig) {
        this.mConfig = tXVodPlayConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXVodPlayConfig();
        }
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            TXCPlayerConfig m815p = tXCVodPlayer.m815p();
            if (m815p == null) {
                m815p = new TXCPlayerConfig();
            }
            TXVodPlayConfig tXVodPlayConfig2 = this.mConfig;
            m815p.f4343e = tXVodPlayConfig2.mConnectRetryCount;
            m815p.f4344f = tXVodPlayConfig2.mConnectRetryInterval;
            m815p.f4355q = tXVodPlayConfig2.mTimeout;
            m815p.f4347i = this.mEnableHWDec;
            m815p.f4351m = tXVodPlayConfig2.mCacheFolderPath;
            m815p.f4352n = tXVodPlayConfig2.mMaxCacheItems;
            m815p.f4353o = tXVodPlayConfig2.mPlayerType;
            m815p.f4354p = tXVodPlayConfig2.mHeaders;
            m815p.f4356r = tXVodPlayConfig2.enableAccurateSeek;
            m815p.f4357s = tXVodPlayConfig2.autoRotate;
            m815p.f4358t = tXVodPlayConfig2.smoothSwitchBitrate;
            m815p.f4359u = tXVodPlayConfig2.cacheMp4ExtName;
            m815p.f4360v = tXVodPlayConfig2.progressInterval;
            this.mPlayer.mo836a(m815p);
        }
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        this.mTXCloudVideoView = tXCloudVideoView;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo833a(tXCloudVideoView);
        }
    }

    public void setPlayerView(TextureRenderView textureRenderView) {
        this.mTextureView = textureRenderView;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.m1067a(textureRenderView);
        }
    }

    public void setSurface(Surface surface) {
        this.mSurface = surface;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo838a(this.mSurface);
        }
    }

    public int startPlay(String str) {
        String path;
        if (str == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        TXCDRApi.initCrashReport(this.mContext);
        int i = this.mBitrateIndex;
        stopPlay(this.mIsNeedClearLastImg);
        this.mBitrateIndex = i;
        if (this.mToken != null && (path = Uri.parse(str).getPath()) != null) {
            String[] split = path.split("/");
            if (split.length > 0) {
                int lastIndexOf = str.lastIndexOf(split[split.length - 1]);
                str = str.substring(0, lastIndexOf) + "voddrm.token." + this.mToken + "." + str.substring(lastIndexOf);
            }
        }
        this.mPlayUrl = checkPlayUrl(str);
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "=====  StartPlay url = " + this.mPlayUrl + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        TXCLog.m2915d(TAG, "===========================================================================================================================================================");
        this.mPlayer = new TXCVodPlayer(this.mContext);
        updateConfig();
        TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
            this.mPlayer.mo833a(this.mTXCloudVideoView);
        } else {
            Surface surface = this.mSurface;
            if (surface != null) {
                this.mPlayer.mo838a(surface);
            } else {
                TextureRenderView textureRenderView = this.mTextureView;
                if (textureRenderView != null) {
                    this.mPlayer.m1067a(textureRenderView);
                }
            }
        }
        this.mPlayer.m1061e(this.mBitrateIndex);
        this.mPlayer.m837a(this);
        this.mPlayer.mo820c(this.mAutoPlay);
        this.mPlayer.m1065c(this.mStartTime);
        this.mPlayer.mo830a(this.mPlayUrl, 0);
        this.mPlayer.mo823b(this.mMute);
        this.mPlayer.mo825b(this.mRate);
        this.mPlayer.mo824b(this.mRenderRotation);
        this.mPlayer.mo841a(this.mRenderMode);
        this.mPlayer.m1062d(this.mLoop);
        setMirror(this.mMirror);
        return 0;
    }

    public int startPlay(TXPlayerAuthBuilder tXPlayerAuthBuilder) {
        this.mNetApi = new TXCVodPlayerNetApi();
        this.mNetApi.m1129a(tXPlayerAuthBuilder.isHttps);
        this.mNetApi.m1133a(this);
        return this.mNetApi.m1137a(tXPlayerAuthBuilder.appId, tXPlayerAuthBuilder.fileId, tXPlayerAuthBuilder.timeout, tXPlayerAuthBuilder.f5645us, tXPlayerAuthBuilder.exper, tXPlayerAuthBuilder.sign);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0066, code lost:
        com.tencent.liteav.basic.log.TXCLog.m2911w(com.tencent.rtmp.TXVodPlayer.TAG, "传入URL已转码");
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x006f, code lost:
        return r8;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String checkPlayUrl(String str) {
        if (str.startsWith("http")) {
            try {
                byte[] bytes = str.getBytes("UTF-8");
                StringBuilder sb = new StringBuilder(bytes.length);
                for (int i = 0; i < bytes.length; i++) {
                    int i2 = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
                    if (i2 > 32 && i2 < 127 && i2 != 34 && i2 != 37 && i2 != 60 && i2 != 62 && i2 != 91 && i2 != 125 && i2 != 92 && i2 != 93 && i2 != 94 && i2 != 96 && i2 != 123 && i2 != 124) {
                        sb.append((char) i2);
                    }
                    sb.append(String.format("%%%02X", Integer.valueOf(i2)));
                }
                str = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.trim();
    }

    public int stopPlay(boolean z) {
        TXCloudVideoView tXCloudVideoView;
        if (z && (tXCloudVideoView = this.mTXCloudVideoView) != null) {
            tXCloudVideoView.setVisibility(8);
        }
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo829a(z);
        }
        this.mPlayUrl = "";
        TXCVodPlayerNetApi tXCVodPlayerNetApi = this.mNetApi;
        if (tXCVodPlayerNetApi != null) {
            tXCVodPlayerNetApi.m1133a((TXCVodPlayerNetListener) null);
            this.mNetApi = null;
        }
        this.mBitrateIndex = 0;
        this.mIsGetPlayInfo = false;
        return 0;
    }

    public boolean isPlaying() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.mo822c();
        }
        return false;
    }

    public void pause() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo842a();
        }
    }

    public void resume() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo826b();
        }
    }

    public void seek(int i) {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo827a_(i);
        }
    }

    public void seek(float f) {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.m1070a(f);
        }
    }

    public float getCurrentPlaybackTime() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1059h();
        }
        return 0.0f;
    }

    public float getBufferDuration() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1058i();
        }
        return 0.0f;
    }

    public float getDuration() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1057j();
        }
        return 0.0f;
    }

    public float getPlayableDuration() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1056k();
        }
        return 0.0f;
    }

    public int getWidth() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1055l();
        }
        return 0;
    }

    public int getHeight() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1054m();
        }
        return 0;
    }

    @Deprecated
    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        this.mListener = iTXLivePlayListener;
    }

    public void setVodListener(ITXVodPlayListener iTXVodPlayListener) {
        this.mNewListener = iTXVodPlayListener;
    }

    public void setRenderMode(int i) {
        this.mRenderMode = i;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo841a(i);
        }
    }

    public void setRenderRotation(int i) {
        this.mRenderRotation = i;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo824b(i);
        }
    }

    public boolean enableHardwareDecode(boolean z) {
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
        updateConfig();
        return true;
    }

    public void setMute(boolean z) {
        this.mMute = z;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo823b(z);
        }
    }

    public void setAutoPlay(boolean z) {
        this.mAutoPlay = z;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo820c(z);
        }
    }

    public void setRate(float f) {
        this.mRate = f;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.mo825b(f);
        }
    }

    public int getBitrateIndex() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1053n();
        }
        return 0;
    }

    public void setBitrateIndex(int i) {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.m1061e(i);
        }
        this.mBitrateIndex = i;
    }

    public ArrayList<TXBitrateItem> getSupportedBitrates() {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            return tXCVodPlayer.m1052o();
        }
        return new ArrayList<>();
    }

    public void snapshot(TXLivePlayer.ITXSnapshotListener iTXSnapshotListener) {
        Bitmap bitmap;
        if (this.mSnapshotRunning || iTXSnapshotListener == null) {
            return;
        }
        this.mSnapshotRunning = true;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        TextureView mo819d = tXCVodPlayer != null ? tXCVodPlayer.mo819d() : null;
        if (mo819d != null) {
            Bitmap bitmap2 = mo819d.getBitmap();
            if (bitmap2 != null) {
                Matrix transform = mo819d.getTransform(null);
                if (this.mMirror) {
                    transform.postScale(-1.0f, 1.0f);
                }
                bitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), transform, true);
                bitmap2.recycle();
            } else {
                bitmap = bitmap2;
            }
            postBitmapToMainThread(iTXSnapshotListener, bitmap);
            return;
        }
        this.mSnapshotRunning = false;
    }

    public void setMirror(boolean z) {
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.m1060e(z);
        }
        this.mMirror = z;
    }

    public void setStartTime(float f) {
        this.mStartTime = f;
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 15001) {
            TXCloudVideoView tXCloudVideoView = this.mTXCloudVideoView;
            if (tXCloudVideoView != null) {
                tXCloudVideoView.setLogText(bundle, null, 0);
            }
            ITXLivePlayListener iTXLivePlayListener = this.mListener;
            if (iTXLivePlayListener != null) {
                iTXLivePlayListener.onNetStatus(bundle);
            }
            ITXVodPlayListener iTXVodPlayListener = this.mNewListener;
            if (iTXVodPlayListener == null) {
                return;
            }
            iTXVodPlayListener.onNetStatus(this, bundle);
            return;
        }
        TXCloudVideoView tXCloudVideoView2 = this.mTXCloudVideoView;
        if (tXCloudVideoView2 != null) {
            tXCloudVideoView2.setLogText(null, bundle, i);
        }
        ITXLivePlayListener iTXLivePlayListener2 = this.mListener;
        if (iTXLivePlayListener2 != null) {
            iTXLivePlayListener2.onPlayEvent(i, bundle);
        }
        ITXVodPlayListener iTXVodPlayListener2 = this.mNewListener;
        if (iTXVodPlayListener2 == null) {
            return;
        }
        iTXVodPlayListener2.onPlayEvent(this, i, bundle);
    }

    private boolean isAVCDecBlacklistDevices() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00");
    }

    private void postBitmapToMainThread(final TXLivePlayer.ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.TXVodPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                TXLivePlayer.ITXSnapshotListener iTXSnapshotListener2 = iTXSnapshotListener;
                if (iTXSnapshotListener2 != null) {
                    iTXSnapshotListener2.onSnapshot(bitmap);
                }
                TXVodPlayer.this.mSnapshotRunning = false;
            }
        });
    }

    void updateConfig() {
        setConfig(this.mConfig);
    }

    @Override // com.tencent.liteav.network.TXCVodPlayerNetListener
    public void onNetSuccess(TXCVodPlayerNetApi tXCVodPlayerNetApi) {
        if (tXCVodPlayerNetApi != this.mNetApi) {
            return;
        }
        TXPlayInfoResponse m1138a = tXCVodPlayerNetApi.m1138a();
        if (!this.mIsGetPlayInfo) {
            startPlay(m1138a.m1128a());
        }
        this.mIsGetPlayInfo = false;
        Bundle bundle = new Bundle();
        bundle.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putString("EVT_MSG", "文件信息请求成功");
        bundle.putString(TXLiveConstants.EVT_PLAY_URL, m1138a.m1128a());
        bundle.putString(TXLiveConstants.EVT_PLAY_COVER_URL, m1138a.m1126b());
        bundle.putString(TXLiveConstants.EVT_PLAY_NAME, m1138a.m1122f());
        bundle.putString(TXLiveConstants.EVT_PLAY_DESCRIPTION, m1138a.m1121g());
        if (m1138a.m1124d() != null) {
            bundle.putInt(TXLiveConstants.EVT_PLAY_DURATION, m1138a.m1124d().m1115c());
        }
        onNotifyEvent(TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC, bundle);
    }

    @Override // com.tencent.liteav.network.TXCVodPlayerNetListener
    public void onNetFailed(TXCVodPlayerNetApi tXCVodPlayerNetApi, String str, int i) {
        if (tXCVodPlayerNetApi != this.mNetApi) {
            return;
        }
        this.mIsGetPlayInfo = false;
        Bundle bundle = new Bundle();
        bundle.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putString("EVT_MSG", str);
        bundle.putInt("EVT_PARAM1", i);
        onNotifyEvent(TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL, bundle);
    }

    public void setToken(String str) {
        this.mToken = str;
    }

    public void setLoop(boolean z) {
        this.mLoop = z;
        TXCVodPlayer tXCVodPlayer = this.mPlayer;
        if (tXCVodPlayer != null) {
            tXCVodPlayer.m1062d(this.mLoop);
        }
    }

    public boolean isLoop() {
        return this.mLoop;
    }
}
