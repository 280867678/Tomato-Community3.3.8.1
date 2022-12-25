package com.tomatolive.library.p136ui.view.faceunity;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.blankj.utilcode.util.SPUtils;
import com.faceunity.beautycontrolview.FilterEnum;
import com.faceunity.beautycontrolview.OnFaceUnityControlListener;
import com.faceunity.beautycontrolview.entity.Effect;
import com.faceunity.beautycontrolview.entity.Filter;
import com.faceunity.wrapper.C1363faceunity;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer */
/* loaded from: classes3.dex */
public class FURenderer implements OnFaceUnityControlListener {
    public static final String BUNDLE_animoji_3d = "fxaa.bundle";
    public static final String BUNDLE_face_beautification = "face_beautification.bundle";
    public static final String BUNDLE_v3 = "v3.bundle";
    private static final int ITEM_ARRAYS_COUNT = 3;
    private static final int ITEM_ARRAYS_EFFECT = 1;
    private static final int ITEM_ARRAYS_EFFECT_ABIMOJI_3D = 2;
    private static final int ITEM_ARRAYS_FACE_BEAUTY_INDEX = 0;
    private static final float NANO_IN_ONE_MILLI_SECOND = 1000000.0f;
    private static final String TAG = "FURenderer";
    private static final float TIME = 5.0f;
    private static boolean isInit;
    private float[] expressionData;
    private boolean isNeedAnimoji3D;
    private boolean isNeedFaceBeauty;
    private boolean isNeedUpdateFaceBeauty;
    private float[] landmarksData;
    private float mBeautyTeethLevel;
    private float mBrightEyesLevel;
    private int mCameraFacing;
    private float mChinLevel;
    private Context mContext;
    private int mCurrentFrameCnt;
    private Effect mDefaultEffect;
    private ArrayList<Runnable> mEventQueue;
    private float mFaceBeautyALLBlurLevel;
    private float mFaceBeautyBlurLevel;
    private float mFaceBeautyCheekThin;
    private float mFaceBeautyColorLevel;
    private float mFaceBeautyEnlargeEye;
    private float mFaceBeautyFaceShape;
    private float mFaceBeautyFilterLevel;
    private float mFaceBeautyRedLevel;
    private float mFaceBeautyType;
    private float mFaceShapeLevel;
    private Filter mFilterName;
    private float mForeheadLevel;
    private int mFrameId;
    private long mFuCallStartTime;
    private Handler mFuItemHandler;
    private HandlerThread mFuItemHandlerThread;
    private int mInputImageFormat;
    private int mInputImageOrientation;
    private int mInputTextureType;
    private float mIsCalibrating;
    private boolean mIsCreateEGLContext;
    private final int[] mItemsArray;
    private long mLastOneHundredFrameTimeStamp;
    private int mMaxFaces;
    private float mMouthShape;
    private boolean mNeedBenchmark;
    private boolean mNeedReadBackImage;
    private OnCalibratingListener mOnCalibratingListener;
    private OnFUDebugListener mOnFUDebugListener;
    private OnSystemErrorListener mOnSystemErrorListener;
    private OnTrackingStatusChangedListener mOnTrackingStatusChangedListener;
    private long mOneHundredFrameFUTime;
    private float mThinNoseLevel;
    private int mTrackingStatus;
    private float[] pupilPosData;
    private float[] rotationData;
    private float[] rotationModeData;

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$OnCalibratingListener */
    /* loaded from: classes3.dex */
    public interface OnCalibratingListener {
        void OnCalibrating(float f);
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$OnFUDebugListener */
    /* loaded from: classes3.dex */
    public interface OnFUDebugListener {
        void onFpsChange(double d, double d2);
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$OnSystemErrorListener */
    /* loaded from: classes3.dex */
    public interface OnSystemErrorListener {
        void onSystemError(String str);
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$OnTrackingStatusChangedListener */
    /* loaded from: classes3.dex */
    public interface OnTrackingStatusChangedListener {
        void onTrackingStatusChanged(int i);
    }

    private void getLastParam() {
        String string = SPUtils.getInstance().getString(FaceConstant.FILTER_NAME, "ziran");
        this.mFaceBeautyFilterLevel = SPUtils.getInstance().getFloat(string, 1.0f);
        if (TextUtils.equals(string, "origin")) {
            string = "ziran";
        }
        this.mFilterName = FilterEnum.valueOf(string).filter();
        this.mFaceBeautyALLBlurLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTY_ALLBLUR_LEVEL, 1.0f);
        this.mFaceBeautyType = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYTYPE, 0.0f);
        this.mFaceBeautyBlurLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYBLUR_LEVEL, 0.7f);
        this.mFaceBeautyColorLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYCOLOR_LEVEL, 0.5f);
        this.mFaceBeautyRedLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYRED_LEVEL, 0.5f);
        this.mBrightEyesLevel = SPUtils.getInstance().getFloat(FaceConstant.BRIGHTEYES_LEVEL, 0.0f);
        this.mBeautyTeethLevel = SPUtils.getInstance().getFloat(FaceConstant.BEAUTYTEETH_LEVEL, 0.0f);
        this.mFaceBeautyFaceShape = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_FACESHAPE, 4.0f);
        this.mFaceBeautyEnlargeEye = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_ENLARGEEYE, 0.4f);
        this.mFaceBeautyCheekThin = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_CHEEKTHIN, 0.4f);
        this.mChinLevel = SPUtils.getInstance().getFloat(FaceConstant.CHIN_LEVEL, 0.3f);
        this.mForeheadLevel = SPUtils.getInstance().getFloat(FaceConstant.FOREHEAD_LEVEL, 0.3f);
        this.mThinNoseLevel = SPUtils.getInstance().getFloat(FaceConstant.THINNOSE_LEVEL, 0.5f);
        this.mMouthShape = SPUtils.getInstance().getFloat(FaceConstant.Mouth_Shape, 0.4f);
    }

    public static void initFURenderer(Context context) {
        try {
            String DESDecrypt = EncryptUtil.DESDecrypt("246887c3-ee20-4fe8-a320-1fde4a8d10b6", StringUtils.uncompress(SPUtils.getInstance().getString(FaceConstant.AUTH_ENCRYPT_STR)));
            InputStream open = context.getAssets().open(BUNDLE_v3);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            C1363faceunity.fuSetup(bArr, null, StringUtils.toByteArray(DESDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        return C1363faceunity.fuGetVersion();
    }

    public static int getModuleCode() {
        return C1363faceunity.fuGetModuleCode(0);
    }

    private FURenderer(Context context, boolean z) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyFilterLevel = 1.0f;
        this.mFilterName = FilterEnum.ziran.filter();
        this.mFaceBeautyALLBlurLevel = 1.0f;
        this.mFaceBeautyType = 0.0f;
        this.mFaceBeautyBlurLevel = 0.7f;
        this.mFaceBeautyColorLevel = 0.5f;
        this.mFaceBeautyRedLevel = 0.5f;
        this.mBrightEyesLevel = 0.0f;
        this.mBeautyTeethLevel = 0.0f;
        this.mFaceBeautyFaceShape = 4.0f;
        this.mFaceShapeLevel = 1.0f;
        this.mFaceBeautyEnlargeEye = 0.4f;
        this.mFaceBeautyCheekThin = 0.4f;
        this.mChinLevel = 0.3f;
        this.mForeheadLevel = 0.3f;
        this.mThinNoseLevel = 0.5f;
        this.mMouthShape = 0.4f;
        this.mFrameId = 0;
        this.mItemsArray = new int[3];
        this.isNeedFaceBeauty = true;
        this.isNeedAnimoji3D = false;
        this.mMaxFaces = 4;
        this.mInputTextureType = 0;
        this.mInputImageFormat = 0;
        this.mNeedReadBackImage = false;
        this.mInputImageOrientation = 0;
        this.mCameraFacing = 1;
        this.landmarksData = new float[150];
        this.expressionData = new float[46];
        this.rotationData = new float[4];
        this.pupilPosData = new float[2];
        this.rotationModeData = new float[1];
        this.mEventQueue = new ArrayList<>();
        this.mTrackingStatus = 0;
        this.mIsCalibrating = 0.0f;
        this.mCurrentFrameCnt = 0;
        this.mLastOneHundredFrameTimeStamp = 0L;
        this.mOneHundredFrameFUTime = 0L;
        this.mNeedBenchmark = true;
        this.mFuCallStartTime = 0L;
        this.mContext = context;
        this.mIsCreateEGLContext = z;
        this.mFuItemHandlerThread = new HandlerThread("FUItemHandlerThread");
        this.mFuItemHandlerThread.start();
        this.mFuItemHandler = new FUItemHandler(this.mFuItemHandlerThread.getLooper());
        getLastParam();
    }

    public void onSurfaceCreated() {
        Log.e(TAG, "onSurfaceCreated");
        if (this.mIsCreateEGLContext) {
            C1363faceunity.fuCreateEGLContext();
        }
        this.mFrameId = 0;
        C1363faceunity.fuSetExpressionCalibration(2);
        C1363faceunity.fuSetMaxFaces(this.mMaxFaces);
        if (this.isNeedFaceBeauty) {
            this.mFuItemHandler.sendEmptyMessage(2);
        }
    }

    public int drawFrame(int i, int i2, int i3) {
        prepareDrawFrame();
        int i4 = this.mFrameId;
        this.mFrameId = i4 + 1;
        return C1363faceunity.fuRenderToTexture(i, i2, i3, i4, this.mItemsArray, 0);
    }

    public int onDrawFrame(byte[] bArr, int i, int i2) {
        if (bArr == null || i <= 0 || i2 <= 0) {
            Log.e(TAG, "onDrawFrame date null");
            return 0;
        }
        prepareDrawFrame();
        int i3 = this.mInputImageFormat;
        if (this.mCameraFacing != 1) {
            i3 |= 32;
        }
        int i4 = i3;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i5 = this.mFrameId;
        this.mFrameId = i5 + 1;
        int fuRenderToNV21Image = C1363faceunity.fuRenderToNV21Image(bArr, i, i2, i5, this.mItemsArray, i4);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuRenderToNV21Image;
    }

    public int onDrawFrame(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (bArr == null || i <= 0 || i2 <= 0 || bArr2 == null || i3 <= 0 || i4 <= 0) {
            Log.e(TAG, "onDrawFrame date null");
            return 0;
        }
        prepareDrawFrame();
        int i5 = this.mInputImageFormat;
        if (this.mCameraFacing != 1) {
            i5 |= 32;
        }
        int i6 = i5;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i7 = this.mFrameId;
        this.mFrameId = i7 + 1;
        int fuRenderToNV21Image = C1363faceunity.fuRenderToNV21Image(bArr, i, i2, i7, this.mItemsArray, i6, i3, i4, bArr2);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuRenderToNV21Image;
    }

    public int onDrawFrame(byte[] bArr, int i, int i2, int i3) {
        if (i <= 0 || bArr == null || i2 <= 0 || i3 <= 0) {
            Log.e(TAG, "onDrawFrame date null");
            return 0;
        }
        prepareDrawFrame();
        int i4 = this.mInputTextureType | this.mInputImageFormat;
        if (this.mCameraFacing != 1) {
            i4 |= 32;
        }
        int i5 = i4;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i6 = this.mFrameId;
        this.mFrameId = i6 + 1;
        int fuDualInputToTexture = C1363faceunity.fuDualInputToTexture(bArr, i, i5, i2, i3, i6, this.mItemsArray);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuDualInputToTexture;
    }

    public int onDrawFrame(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4, int i5) {
        if (i <= 0 || bArr == null || i2 <= 0 || i3 <= 0 || bArr2 == null || i4 <= 0 || i5 <= 0) {
            Log.e(TAG, "onDrawFrame date null");
            return 0;
        }
        prepareDrawFrame();
        int i6 = this.mInputTextureType | this.mInputImageFormat;
        if (this.mCameraFacing != 1) {
            i6 |= 32;
        }
        int i7 = i6;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i8 = this.mFrameId;
        this.mFrameId = i8 + 1;
        int fuDualInputToTexture = C1363faceunity.fuDualInputToTexture(bArr, i, i7, i2, i3, i8, this.mItemsArray, i4, i5, bArr2);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuDualInputToTexture;
    }

    public int onDrawFrame(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0 || i3 <= 0) {
            Log.e(TAG, "onDrawFrame data null");
            return 0;
        }
        prepareDrawFrame();
        int i4 = this.mInputTextureType;
        if (this.mCameraFacing != 1) {
            i4 |= 32;
        }
        int i5 = i4;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i6 = this.mFrameId;
        this.mFrameId = i6 + 1;
        int fuRenderToTexture = C1363faceunity.fuRenderToTexture(i, i2, i3, i6, this.mItemsArray, i5);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuRenderToTexture;
    }

    public int onDrawFrameBeautify(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0 || i3 <= 0) {
            Log.e(TAG, "onDrawFrame data null");
            return 0;
        }
        prepareDrawFrame();
        int i4 = this.mInputTextureType;
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        int i5 = this.mFrameId;
        this.mFrameId = i5 + 1;
        int fuBeautifyImage = C1363faceunity.fuBeautifyImage(i, i4, i2, i3, i5, this.mItemsArray);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuBeautifyImage;
    }

    public int onDrawFrameAvatar(byte[] bArr, int i, int i2) {
        if (bArr == null || i <= 0 || i2 <= 0) {
            Log.e(TAG, "onDrawFrameAvatar date null");
            return 0;
        }
        prepareDrawFrame();
        int i3 = this.mInputImageFormat;
        if (this.mCameraFacing != 1) {
            i3 |= 32;
        }
        if (this.mNeedBenchmark) {
            this.mFuCallStartTime = System.nanoTime();
        }
        C1363faceunity.fuTrackFace(bArr, i3, i, i2);
        Arrays.fill(this.landmarksData, 0.0f);
        C1363faceunity.fuGetFaceInfo(0, "landmarks", this.landmarksData);
        Arrays.fill(this.rotationData, 0.0f);
        C1363faceunity.fuGetFaceInfo(0, "rotation", this.rotationData);
        Arrays.fill(this.expressionData, 0.0f);
        C1363faceunity.fuGetFaceInfo(0, "expression", this.expressionData);
        Arrays.fill(this.pupilPosData, 0.0f);
        C1363faceunity.fuGetFaceInfo(0, "pupil_pos", this.pupilPosData);
        Arrays.fill(this.rotationModeData, 0.0f);
        C1363faceunity.fuGetFaceInfo(0, "rotation_mode", this.rotationModeData);
        int fuIsTracking = C1363faceunity.fuIsTracking();
        if (fuIsTracking <= 0) {
            this.rotationData[3] = 1.0f;
            this.rotationModeData[0] = (360 - this.mInputImageOrientation) / 90.0f;
        }
        float[] fArr = this.pupilPosData;
        float[] fArr2 = this.expressionData;
        float[] fArr3 = this.rotationData;
        float[] fArr4 = this.rotationModeData;
        int i4 = this.mFrameId;
        this.mFrameId = i4 + 1;
        int fuAvatarToTexture = C1363faceunity.fuAvatarToTexture(fArr, fArr2, fArr3, fArr4, 0, i, i2, i4, this.mItemsArray, fuIsTracking);
        if (this.mNeedBenchmark) {
            this.mOneHundredFrameFUTime += System.nanoTime() - this.mFuCallStartTime;
        }
        return fuAvatarToTexture;
    }

    public void onSurfaceDestroyed() {
        this.mFuItemHandler.removeMessages(1);
        this.mFrameId = 0;
        this.isNeedUpdateFaceBeauty = true;
        Arrays.fill(this.mItemsArray, 0);
        C1363faceunity.fuDestroyAllItems();
        C1363faceunity.fuOnDeviceLost();
        this.mEventQueue.clear();
        if (this.mIsCreateEGLContext) {
            C1363faceunity.fuReleaseEGLContext();
        }
    }

    private void prepareDrawFrame() {
        benchmarkFPS();
        int fuIsTracking = C1363faceunity.fuIsTracking();
        OnTrackingStatusChangedListener onTrackingStatusChangedListener = this.mOnTrackingStatusChangedListener;
        if (onTrackingStatusChangedListener != null && this.mTrackingStatus != fuIsTracking) {
            this.mTrackingStatus = fuIsTracking;
            onTrackingStatusChangedListener.onTrackingStatusChanged(fuIsTracking);
        }
        int fuGetSystemError = C1363faceunity.fuGetSystemError();
        OnSystemErrorListener onSystemErrorListener = this.mOnSystemErrorListener;
        if (onSystemErrorListener != null && fuGetSystemError != 0) {
            onSystemErrorListener.onSystemError(C1363faceunity.fuGetSystemErrorString(fuGetSystemError));
        }
        float[] fArr = new float[1];
        C1363faceunity.fuGetFaceInfo(0, "is_calibrating", fArr);
        OnCalibratingListener onCalibratingListener = this.mOnCalibratingListener;
        if (onCalibratingListener != null && fArr[0] != this.mIsCalibrating) {
            float f = fArr[0];
            this.mIsCalibrating = f;
            onCalibratingListener.OnCalibrating(f);
        }
        if (this.isNeedUpdateFaceBeauty) {
            int[] iArr = this.mItemsArray;
            if (iArr[0] != 0) {
                C1363faceunity.fuItemSetParam(iArr[0], "filter_level", this.mFaceBeautyFilterLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "filter_name", this.mFilterName.filterName());
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "skin_detect", this.mFaceBeautyALLBlurLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "heavy_blur", this.mFaceBeautyType);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "blur_level", this.mFaceBeautyBlurLevel * 6.0f);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "color_level", this.mFaceBeautyColorLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "red_level", this.mFaceBeautyRedLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "eye_bright", this.mBrightEyesLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "tooth_whiten", this.mBeautyTeethLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "face_shape_level", this.mFaceShapeLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "face_shape", this.mFaceBeautyFaceShape);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "eye_enlarging", this.mFaceBeautyEnlargeEye);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "cheek_thinning", this.mFaceBeautyCheekThin);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "intensity_chin", this.mChinLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "intensity_forehead", this.mForeheadLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "intensity_nose", this.mThinNoseLevel);
                C1363faceunity.fuItemSetParam(this.mItemsArray[0], "intensity_mouth", this.mMouthShape);
                this.isNeedUpdateFaceBeauty = false;
            }
        }
        while (!this.mEventQueue.isEmpty()) {
            this.mEventQueue.remove(0).run();
        }
    }

    public void queueEvent(Runnable runnable) {
        this.mEventQueue.add(runnable);
    }

    public void setMaxFaces(final int i) {
        if (this.mMaxFaces == i || i <= 0) {
            return;
        }
        queueEvent(new Runnable() { // from class: com.tomatolive.library.ui.view.faceunity.FURenderer.1
            @Override // java.lang.Runnable
            public void run() {
                FURenderer.this.mMaxFaces = i;
                C1363faceunity.fuSetMaxFaces(i);
            }
        });
    }

    public void onCameraChange(final int i, final int i2) {
        if (this.mCameraFacing == i && this.mInputImageOrientation == i2) {
            return;
        }
        queueEvent(new Runnable() { // from class: com.tomatolive.library.ui.view.faceunity.FURenderer.2
            @Override // java.lang.Runnable
            public void run() {
                FURenderer.this.mCameraFacing = i;
                FURenderer.this.mInputImageOrientation = i2;
                C1363faceunity.fuClearReadbackRelated();
                C1363faceunity.fuOnCameraChange();
                FURenderer.this.mFrameId = 0;
            }
        });
    }

    public void onMusicFilterTime(final long j) {
        queueEvent(new Runnable() { // from class: com.tomatolive.library.ui.view.faceunity.FURenderer.3
            @Override // java.lang.Runnable
            public void run() {
                C1363faceunity.fuItemSetParam(FURenderer.this.mItemsArray[1], "music_time", j);
            }
        });
    }

    public void onEffectSelected(Effect effect) {
        this.mDefaultEffect = effect;
        createItem(effect);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onFilterLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyFilterLevel = f;
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onFilterSelected(Filter filter) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFilterName = filter;
        SPUtils.getInstance().put(FaceConstant.FILTER_NAME, this.mFilterName.filterName());
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onALLBlurLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyALLBlurLevel = f;
        SPUtils.getInstance().put(FaceConstant.FACE_BEAUTY_ALLBLUR_LEVEL, this.mFaceBeautyALLBlurLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onBeautySkinTypeSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyType = f;
        SPUtils.getInstance().put(FaceConstant.FACE_BEAUTYTYPE, this.mFaceBeautyType);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onBlurLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyBlurLevel = f;
        SPUtils.getInstance().put(FaceConstant.FACE_BEAUTYBLUR_LEVEL, this.mFaceBeautyBlurLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onColorLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyColorLevel = f;
        SPUtils.getInstance().put(FaceConstant.FACE_BEAUTYCOLOR_LEVEL, this.mFaceBeautyColorLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onRedLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyRedLevel = f;
        SPUtils.getInstance().put(FaceConstant.FACE_BEAUTYRED_LEVEL, this.mFaceBeautyRedLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onBrightEyesSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mBrightEyesLevel = f;
        SPUtils.getInstance().put(FaceConstant.BRIGHTEYES_LEVEL, this.mBrightEyesLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onBeautyTeethSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mBeautyTeethLevel = f;
        SPUtils.getInstance().put(FaceConstant.BEAUTYTEETH_LEVEL, this.mBeautyTeethLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onFaceShapeSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyFaceShape = f;
        SPUtils.getInstance().put(FaceConstant.FACEBEAUTY_FACESHAPE, this.mFaceBeautyFaceShape);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onEnlargeEyeSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyEnlargeEye = f;
        SPUtils.getInstance().put(FaceConstant.FACEBEAUTY_ENLARGEEYE, this.mFaceBeautyEnlargeEye);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onCheekThinSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mFaceBeautyCheekThin = f;
        SPUtils.getInstance().put(FaceConstant.FACEBEAUTY_CHEEKTHIN, this.mFaceBeautyCheekThin);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onChinLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mChinLevel = f;
        SPUtils.getInstance().put(FaceConstant.CHIN_LEVEL, this.mChinLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onForeheadLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mForeheadLevel = f;
        SPUtils.getInstance().put(FaceConstant.FOREHEAD_LEVEL, this.mForeheadLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onThinNoseLevelSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mThinNoseLevel = f;
        SPUtils.getInstance().put(FaceConstant.THINNOSE_LEVEL, this.mThinNoseLevel);
    }

    @Override // com.faceunity.beautycontrolview.OnFaceUnityControlListener
    public void onMouthShapeSelected(float f) {
        this.isNeedUpdateFaceBeauty = true;
        this.mMouthShape = f;
        SPUtils.getInstance().put(FaceConstant.Mouth_Shape, this.mMouthShape);
    }

    private void benchmarkFPS() {
        if (!this.mNeedBenchmark) {
            return;
        }
        int i = this.mCurrentFrameCnt + 1;
        this.mCurrentFrameCnt = i;
        if (i != TIME) {
            return;
        }
        this.mCurrentFrameCnt = 0;
        long nanoTime = System.nanoTime();
        double d = 1.0E9f / (((float) (nanoTime - this.mLastOneHundredFrameTimeStamp)) / TIME);
        this.mLastOneHundredFrameTimeStamp = nanoTime;
        double d2 = (((float) this.mOneHundredFrameFUTime) / TIME) / NANO_IN_ONE_MILLI_SECOND;
        this.mOneHundredFrameFUTime = 0L;
        OnFUDebugListener onFUDebugListener = this.mOnFUDebugListener;
        if (onFUDebugListener == null) {
            return;
        }
        onFUDebugListener.onFpsChange(d, d2);
    }

    public void createItem(Effect effect) {
        if (effect == null) {
            return;
        }
        this.mFuItemHandler.removeMessages(1);
        Handler handler = this.mFuItemHandler;
        handler.sendMessage(Message.obtain(handler, 1, effect));
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$FUItemHandler */
    /* loaded from: classes3.dex */
    class FUItemHandler extends Handler {
        static final int HANDLE_CREATE_ANIMOJI3D_ITEM = 3;
        static final int HANDLE_CREATE_BEAUTY_ITEM = 2;
        static final int HANDLE_CREATE_ITEM = 1;

        FUItemHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i != 1 && i == 2) {
                try {
                    InputStream open = FURenderer.this.mContext.getAssets().open(FURenderer.BUNDLE_face_beautification);
                    byte[] bArr = new byte[open.available()];
                    open.read(bArr);
                    open.close();
                    FURenderer.this.mItemsArray[0] = C1363faceunity.fuCreateItemFromPackage(bArr);
                    FURenderer.this.isNeedUpdateFaceBeauty = true;
                    String str = FURenderer.TAG;
                    Log.e(str, "face beauty item handle " + FURenderer.this.mItemsArray[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int loadItem(Effect effect) {
        try {
            if (effect.effectType() == 0) {
                return 0;
            }
            InputStream open = this.mContext.getAssets().open(effect.path());
            int read = open.read(new byte[open.available()]);
            String str = TAG;
            Log.e(str, effect.path() + " len " + read);
            open.close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.FURenderer$Builder */
    /* loaded from: classes3.dex */
    public static class Builder {
        private Context context;
        private Effect defaultEffect;
        private OnCalibratingListener onCalibratingListener;
        private OnFUDebugListener onFUDebugListener;
        private OnSystemErrorListener onSystemErrorListener;
        private OnTrackingStatusChangedListener onTrackingStatusChangedListener;
        private boolean createEGLContext = false;
        private int maxFaces = 4;
        private int inputTextureType = 0;
        private boolean needReadBackImage = false;
        private int inputImageFormat = 0;
        private int inputImageRotation = 90;
        private boolean isNeedAnimoji3D = false;
        private boolean isNeedFaceBeauty = true;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder createEGLContext(boolean z) {
            this.createEGLContext = z;
            return this;
        }

        public Builder defaultEffect(Effect effect) {
            this.defaultEffect = effect;
            return this;
        }

        public Builder maxFaces(int i) {
            this.maxFaces = i;
            return this;
        }

        public Builder inputTextureType(int i) {
            this.inputTextureType = i;
            return this;
        }

        public Builder needReadBackImage(boolean z) {
            this.needReadBackImage = z;
            return this;
        }

        public Builder inputImageFormat(int i) {
            this.inputImageFormat = i;
            return this;
        }

        public Builder inputImageOrientation(int i) {
            this.inputImageRotation = i;
            return this;
        }

        public Builder setNeedAnimoji3D(boolean z) {
            this.isNeedAnimoji3D = z;
            return this;
        }

        public Builder setNeedFaceBeauty(boolean z) {
            this.isNeedFaceBeauty = z;
            return this;
        }

        public Builder setOnFUDebugListener(OnFUDebugListener onFUDebugListener) {
            this.onFUDebugListener = onFUDebugListener;
            return this;
        }

        public Builder setOnTrackingStatusChangedListener(OnTrackingStatusChangedListener onTrackingStatusChangedListener) {
            this.onTrackingStatusChangedListener = onTrackingStatusChangedListener;
            return this;
        }

        public Builder setOnCalibratingListener(OnCalibratingListener onCalibratingListener) {
            this.onCalibratingListener = onCalibratingListener;
            return this;
        }

        public Builder setOnSystemErrorListener(OnSystemErrorListener onSystemErrorListener) {
            this.onSystemErrorListener = onSystemErrorListener;
            return this;
        }

        public FURenderer build() {
            FURenderer fURenderer = new FURenderer(this.context, this.createEGLContext);
            fURenderer.mMaxFaces = this.maxFaces;
            fURenderer.mInputTextureType = this.inputTextureType;
            fURenderer.mNeedReadBackImage = this.needReadBackImage;
            fURenderer.mInputImageFormat = this.inputImageFormat;
            fURenderer.mInputImageOrientation = this.inputImageRotation;
            fURenderer.mDefaultEffect = this.defaultEffect;
            fURenderer.isNeedAnimoji3D = this.isNeedAnimoji3D;
            fURenderer.isNeedFaceBeauty = this.isNeedFaceBeauty;
            fURenderer.mOnFUDebugListener = this.onFUDebugListener;
            fURenderer.mOnTrackingStatusChangedListener = this.onTrackingStatusChangedListener;
            fURenderer.mOnCalibratingListener = this.onCalibratingListener;
            fURenderer.mOnSystemErrorListener = this.onSystemErrorListener;
            return fURenderer;
        }
    }

    public void loadItems() {
        if (!isInit) {
            isInit = true;
            initFURenderer(this.mContext);
        }
        onSurfaceCreated();
    }

    public void destroyItems() {
        onSurfaceDestroyed();
    }
}
