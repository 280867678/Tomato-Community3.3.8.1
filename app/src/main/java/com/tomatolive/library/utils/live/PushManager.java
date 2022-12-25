package com.tomatolive.library.utils.live;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import com.blankj.utilcode.util.SPUtils;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.p136ui.view.faceunity.BeautyControlView;
import com.tomatolive.library.p136ui.view.faceunity.FURenderer;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.SystemUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PushManager {
    private boolean isPause;
    boolean isWarn;
    private OnPushListener listener;
    private Context mContext;
    private FURenderer mFURenderer;
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private TXCloudVideoView mPreviewView;
    private String mPushUrl;
    private Bitmap pauseBitmap;
    private ViewGroup rootView;
    private Bitmap warnBitmap;
    private Bitmap waterLogBitmap;
    private boolean mFrontCamera = true;
    private boolean mOnFirstCreate = true;
    private int mCurrentVideoResolution = 1;
    private ITXLivePushListener pushListener = new ITXLivePushListener() { // from class: com.tomatolive.library.utils.live.PushManager.1
        @Override // com.tencent.rtmp.ITXLivePushListener
        public void onNetStatus(Bundle bundle) {
        }

        @Override // com.tencent.rtmp.ITXLivePushListener
        public void onPushEvent(int i, Bundle bundle) {
            bundle.getString("EVT_MSG");
            if (i == -1307) {
                if (PushManager.this.listener == null) {
                    return;
                }
                PushManager.this.listener.onRePush();
            } else if (i == 1103) {
                PushManager.this.mLivePushConfig.setHardwareAcceleration(0);
                PushManager.this.mLivePusher.setConfig(PushManager.this.mLivePushConfig);
            } else if (i == -1302) {
                if (PushManager.this.listener == null) {
                    return;
                }
                PushManager.this.listener.onMicError();
            } else if (i == -1301) {
                if (PushManager.this.listener == null) {
                    return;
                }
                PushManager.this.listener.onCameraError();
            } else if (i != 1002) {
                if (i != 1003 || PushManager.this.listener == null) {
                    return;
                }
                PushManager.this.listener.onCameraSuccess();
            } else if (PushManager.this.listener == null) {
            } else {
                PushManager.this.listener.onPushSuccess();
            }
        }
    };
    private PhoneStateListener mPhoneListener = null;

    /* loaded from: classes4.dex */
    public interface OnPushListener {
        void onCameraError();

        void onCameraSuccess();

        void onMicError();

        void onPushSuccess();

        void onRePush();
    }

    public PushManager(Context context) {
        this.mContext = context;
        this.pauseBitmap = SystemUtils.decodeResource(this.mContext.getResources(), R$drawable.fq_ic_placeholder_anchor_push_open_mirror);
        this.warnBitmap = SystemUtils.decodeResource(this.mContext.getResources(), R$drawable.fq_ic_placeholder_anchor_warn);
    }

    public void initPushConfig(ViewGroup viewGroup) {
        this.rootView = viewGroup;
        if (viewGroup != null) {
            this.mPreviewView = new TXCloudVideoView(this.mContext);
            viewGroup.addView(this.mPreviewView, 0, new ViewGroup.LayoutParams(-1, -1));
        }
        this.mLivePusher = new TXLivePusher(this.mContext);
        this.mLivePushConfig = new TXLivePushConfig();
        this.mLivePushConfig.setVideoEncodeGop(1);
        this.mLivePushConfig.setPauseImg(this.pauseBitmap);
        this.mLivePushConfig.setPauseFlag(3);
        this.mLivePushConfig.setFrontCamera(this.mFrontCamera);
        this.mLivePushConfig.setTouchFocus(true);
        this.mLivePushConfig.setVideoResolution(this.mCurrentVideoResolution);
        setWatermarkConfig();
        this.mLivePushConfig.setBeautyFilter(7, 6, 5);
        this.mLivePushConfig.setHardwareAcceleration(2);
        this.mLivePusher.setConfig(this.mLivePushConfig);
        this.mLivePusher.startCameraPreview(this.mPreviewView);
        this.mLivePusher.setMirror(SPUtils.getInstance().getBoolean(ConstantUtils.LIVE_MIRROR_KEY, false));
        this.mLivePusher.setVideoQuality(2, false, false);
        this.mLivePusher.setPushListener(this.pushListener);
        this.mPhoneListener = new TXPhoneStateListener(this.mLivePusher);
        ((TelephonyManager) this.mContext.getApplicationContext().getSystemService("phone")).listen(this.mPhoneListener, 32);
    }

    public void setWarnPush() {
        this.mLivePushConfig.setWatermark(this.warnBitmap, 0.0f, 0.0f, 1.0f);
        this.mLivePusher.setConfig(this.mLivePushConfig);
        this.isWarn = true;
    }

    public void removeWarnPush() {
        this.isWarn = false;
        if (SysConfigInfoManager.getInstance().isEnableSticker()) {
            getWaterLogo();
            this.mLivePushConfig.setWatermark(this.waterLogBitmap, 0.2f, 0.2f, 0.7f);
            this.mLivePusher.setConfig(this.mLivePushConfig);
            return;
        }
        this.mLivePushConfig.setWatermark(null, 0.0f, 0.0f, 1.0f);
        this.mLivePusher.setConfig(this.mLivePushConfig);
    }

    public Bitmap getWaterLogo() {
        try {
            this.waterLogBitmap = BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(AppUtils.getStickerWaterImgPath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this.waterLogBitmap;
    }

    public void setOnPushListener(OnPushListener onPushListener) {
        this.listener = onPushListener;
    }

    public void hidePreviewView() {
        TXCloudVideoView tXCloudVideoView = this.mPreviewView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(4);
        }
    }

    public void showWaterLogo() {
        if (this.isWarn) {
            return;
        }
        setWatermarkConfig();
        this.mLivePusher.setConfig(this.mLivePushConfig);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXLivePusher> mPusher;

        public TXPhoneStateListener(TXLivePusher tXLivePusher) {
            this.mPusher = new WeakReference<>(tXLivePusher);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            super.onCallStateChanged(i, str);
            TXLivePusher tXLivePusher = this.mPusher.get();
            if (i == 0) {
                if (tXLivePusher == null) {
                    return;
                }
                tXLivePusher.resumePusher();
            } else if (i == 1) {
                if (tXLivePusher == null) {
                    return;
                }
                tXLivePusher.pausePusher();
            } else if (i != 2 || tXLivePusher == null) {
            } else {
                tXLivePusher.pausePusher();
            }
        }
    }

    public void enableHighBeauty(BeautyControlView beautyControlView) {
        if (beautyControlView != null) {
            this.mFURenderer = new FURenderer.Builder(this.mContext).inputTextureType(0).createEGLContext(false).needReadBackImage(false).setNeedFaceBeauty(true).build();
            this.mFURenderer.loadItems();
            beautyControlView.setOnFaceUnityControlListener(this.mFURenderer);
            this.mLivePusher.setVideoProcessListener(new TXLivePusher.VideoCustomProcessListener() { // from class: com.tomatolive.library.utils.live.PushManager.2
                @Override // com.tencent.rtmp.TXLivePusher.VideoCustomProcessListener
                public void onDetectFacePoints(float[] fArr) {
                }

                @Override // com.tencent.rtmp.TXLivePusher.VideoCustomProcessListener
                public int onTextureCustomProcess(int i, int i2, int i3) {
                    if (PushManager.this.mOnFirstCreate) {
                        PushManager.this.mFURenderer.onSurfaceCreated();
                        PushManager.this.mOnFirstCreate = false;
                    }
                    return PushManager.this.mFURenderer.onDrawFrame(i, i2, i3);
                }

                @Override // com.tencent.rtmp.TXLivePusher.VideoCustomProcessListener
                public void onTextureDestoryed() {
                    PushManager.this.mFURenderer.onSurfaceDestroyed();
                    PushManager.this.mOnFirstCreate = true;
                }
            });
        }
    }

    public TXLivePusher enableBasicBeauty() {
        return this.mLivePusher;
    }

    public void onResume(boolean z, boolean z2) {
        if (z) {
            this.mPreviewView.onResume();
        }
        if (z2) {
            this.mLivePusher.resumePusher();
        }
        if (this.isPause) {
            this.isPause = false;
            this.mLivePusher.startCameraPreview(this.mPreviewView);
        }
    }

    public void onPause() {
        this.isPause = true;
        this.mPreviewView.onPause();
        this.mLivePusher.pausePusher();
    }

    public void setFrontCameraMirror(boolean z) {
        this.mLivePusher.setMirror(z);
    }

    public void switchCamera() {
        this.mFrontCamera = !this.mFrontCamera;
        this.mLivePusher.switchCamera();
        this.mLivePushConfig.setFrontCamera(this.mFrontCamera);
        FURenderer fURenderer = this.mFURenderer;
        if (fURenderer != null) {
            fURenderer.onCameraChange(this.mFrontCamera ? 1 : 0, 0);
        }
    }

    public void startStream(String str) {
        this.mPushUrl = str;
        this.mLivePusher.startPusher(str);
    }

    public void stopStream() {
        this.mLivePusher.stopCameraPreview(true);
        this.mLivePusher.setPushListener(null);
        this.mLivePusher.stopPusher();
        this.mLivePushConfig.setPauseImg(null);
    }

    public void restartStream() {
        stopStream();
        this.mLivePusher.setPushListener(this.pushListener);
        this.mLivePushConfig.setVideoEncodeGop(1);
        this.mLivePushConfig.setPauseImg(this.pauseBitmap);
        this.mLivePushConfig.setPauseFlag(3);
        this.mLivePushConfig.setVideoResolution(this.mCurrentVideoResolution);
        this.mLivePushConfig.setBeautyFilter(7, 6, 5);
        setWatermarkConfig();
        this.mLivePusher.setConfig(this.mLivePushConfig);
        this.mLivePusher.startCameraPreview(this.mPreviewView);
        this.mLivePusher.setMirror(SPUtils.getInstance().getBoolean(ConstantUtils.LIVE_MIRROR_KEY, false));
        this.mLivePusher.startPusher(this.mPushUrl);
    }

    public void toggleTorch(boolean z) {
        this.mLivePusher.turnOnFlashLight(z);
    }

    public void setMuteAudio(boolean z) {
        this.mLivePusher.setMute(z);
    }

    public void release() {
        Bitmap bitmap = this.pauseBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.pauseBitmap.recycle();
            this.pauseBitmap = null;
        }
        Bitmap bitmap2 = this.waterLogBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.waterLogBitmap.recycle();
            this.waterLogBitmap = null;
        }
        this.mLivePusher.stopCameraPreview(true);
        ((TelephonyManager) this.mContext.getApplicationContext().getSystemService("phone")).listen(this.mPhoneListener, 0);
        this.mPhoneListener = null;
        ViewGroup viewGroup = this.rootView;
        if (viewGroup != null) {
            viewGroup.removeView(this.mPreviewView);
        }
        TXCloudVideoView tXCloudVideoView = this.mPreviewView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.onDestroy();
            this.mPreviewView = null;
        }
        this.mContext = null;
        if (this.listener != null) {
            this.listener = null;
        }
    }

    private void setWatermarkConfig() {
        if (SysConfigInfoManager.getInstance().isEnableSticker()) {
            getWaterLogo();
            this.mLivePushConfig.setWatermark(this.waterLogBitmap, 0.2f, 0.2f, 0.7f);
        }
    }
}
