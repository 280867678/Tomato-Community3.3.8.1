package com.tencent.ugc;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p107c.LicenceCheck;
import com.tencent.liteav.basic.p107c.LicenceInfo;
import com.tencent.liteav.p118c.BgmConfig;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.MotionFilterConfig;
import com.tencent.liteav.p118c.RepeatPlayConfig;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p118c.ThumbnailConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoPreProcessConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.BeautyFilter;
import com.tencent.liteav.p119d.ComStaticFilter;
import com.tencent.liteav.p119d.Motion;
import com.tencent.liteav.p119d.TailWaterMark;
import com.tencent.liteav.p119d.WaterMark;
import com.tencent.liteav.p120e.VideoAverageThumbnailGenerate;
import com.tencent.liteav.p120e.VideoEditGenerate;
import com.tencent.liteav.p120e.VideoEditerPreview;
import com.tencent.liteav.p120e.VideoProcessGenerate;
import com.tencent.liteav.p120e.VideoRecordGenerate;
import com.tencent.liteav.p120e.VideoTimelistThumbnailGenerate;
import com.tencent.liteav.p121f.AnimatedPasterFilterChain;
import com.tencent.liteav.p121f.PasterFilterChain;
import com.tencent.liteav.p121f.SpeedFilterChain;
import com.tencent.liteav.p121f.SubtitleFilterChain;
import com.tencent.liteav.p121f.TailWaterMarkChain;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoEditer;
import com.tencent.liteav.p125j.VideoUtil;
import com.tencent.ugc.TXVideoEditConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TXVideoEditer {
    private static final String TAG = "TXVideoEditer";
    private Context mContext;
    private volatile boolean mIsPreviewStart;
    private TXThumbnailListener mTXThumbnailListener;
    private TXVideoCustomProcessListener mTXVideoCustomProcessListener;
    private TXVideoGenerateListener mTXVideoGenerateListener;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private TXVideoProcessListener mTXVideoProcessListener;
    private VideoAverageThumbnailGenerate mVideoAverageThumbnailGenerate;
    private VideoEditGenerate mVideoEditGenerate;
    private VideoEditerPreview mVideoEditerPreview;
    private VideoProcessGenerate mVideoProcessGenerate;
    private VideoRecordGenerate mVideoRecordGenerate;
    private VideoTimelistThumbnailGenerate mVideoTimelistThumbnailGenerate;
    private boolean mSmartLicenseSupport = true;
    private TXCVideoEditer.AbstractC3523b mTXCVideoCustomProcessListener = new TXCVideoEditer.AbstractC3523b() { // from class: com.tencent.ugc.TXVideoEditer.1
        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3523b
        /* renamed from: a */
        public int mo270a(int i, int i2, int i3, long j) {
            if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                return TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureCustomProcess(i, i2, i3, j);
            }
            return 0;
        }

        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3523b
        /* renamed from: a */
        public void mo271a() {
            if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureDestroyed();
            }
        }
    };
    private TXCVideoEditer.AbstractC3526e mTXCVideoProcessListener = new TXCVideoEditer.AbstractC3526e() { // from class: com.tencent.ugc.TXVideoEditer.2
        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3526e
        /* renamed from: a */
        public void mo269a(float f) {
            if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                TXVideoEditer.this.mTXVideoProcessListener.onProcessProgress(f);
            }
        }

        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3526e
        /* renamed from: a */
        public void mo268a(TXCVideoEditConstants.C3513c c3513c) {
            if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                TXVideoEditConstants.TXGenerateResult tXGenerateResult = new TXVideoEditConstants.TXGenerateResult();
                tXGenerateResult.retCode = c3513c.f4370a;
                tXGenerateResult.descMsg = c3513c.f4371b;
                TXVideoEditer.this.mTXVideoProcessListener.onProcessComplete(tXGenerateResult);
            }
        }
    };
    private TXCVideoEditer.AbstractC3522a mTXCThumbnailListener = new TXCVideoEditer.AbstractC3522a() { // from class: com.tencent.ugc.TXVideoEditer.3
        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3522a
        /* renamed from: a */
        public void mo267a(int i, long j, Bitmap bitmap) {
            if (TXVideoEditer.this.mTXThumbnailListener != null) {
                TXVideoEditer.this.mTXThumbnailListener.onThumbnail(i, j, bitmap);
            }
        }
    };
    private TXCVideoEditer.AbstractC3525d mTXCVideoPreviewListener = new TXCVideoEditer.AbstractC3525d() { // from class: com.tencent.ugc.TXVideoEditer.4
        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3525d
        /* renamed from: a */
        public void mo265a(int i) {
            if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                TXVideoEditer.this.mTXVideoPreviewListener.onPreviewProgress(i);
            }
        }

        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3525d
        /* renamed from: a */
        public void mo266a() {
            if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                TXVideoEditer.this.mTXVideoPreviewListener.onPreviewFinished();
            }
        }
    };
    private TXCVideoEditer.AbstractC3524c mTXCVideoGenerateListener = new TXCVideoEditer.AbstractC3524c() { // from class: com.tencent.ugc.TXVideoEditer.5
        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3524c
        /* renamed from: a */
        public void mo264a(float f) {
            if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                TXVideoEditer.this.mTXVideoGenerateListener.onGenerateProgress(f);
            }
        }

        @Override // com.tencent.liteav.p124i.TXCVideoEditer.AbstractC3524c
        /* renamed from: a */
        public void mo263a(TXCVideoEditConstants.C3513c c3513c) {
            TXVideoEditConstants.TXGenerateResult tXGenerateResult = new TXVideoEditConstants.TXGenerateResult();
            tXGenerateResult.retCode = c3513c.f4370a;
            tXGenerateResult.descMsg = c3513c.f4371b;
            if (tXGenerateResult.retCode == 0) {
                int m2429p = VideoOutputConfig.m2457a().m2429p();
                int m2428q = VideoOutputConfig.m2457a().m2428q();
                TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, TXCDRDef.f2448aY, m2429p, "");
                TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, TXCDRDef.f2449aZ, m2428q, "");
            }
            if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                TXVideoEditer.this.mTXVideoGenerateListener.onGenerateComplete(tXGenerateResult);
            }
        }
    };
    private VideoOutputConfig mVideoOutputConfig = VideoOutputConfig.m2457a();
    private VideoSourceConfig mVideoSourceConfig = VideoSourceConfig.m2416a();
    private VideoPreProcessConfig mVideoPreProcessConfig = VideoPreProcessConfig.m2427a();
    private BgmConfig mBgmConfig = BgmConfig.m2505a();
    private MotionFilterConfig mMotionFilterConfig = MotionFilterConfig.m2491a();

    /* loaded from: classes3.dex */
    public interface TXPCMCallbackListener {
        TXAudioFrame onPCMCallback(TXAudioFrame tXAudioFrame);
    }

    /* loaded from: classes3.dex */
    public interface TXThumbnailListener {
        void onThumbnail(int i, long j, Bitmap bitmap);
    }

    /* loaded from: classes3.dex */
    public interface TXVideoCustomProcessListener {
        int onTextureCustomProcess(int i, int i2, int i3, long j);

        void onTextureDestroyed();
    }

    /* loaded from: classes3.dex */
    public interface TXVideoGenerateListener {
        void onGenerateComplete(TXVideoEditConstants.TXGenerateResult tXGenerateResult);

        void onGenerateProgress(float f);
    }

    /* loaded from: classes3.dex */
    public interface TXVideoPreviewListener {
        void onPreviewFinished();

        void onPreviewProgress(int i);
    }

    /* loaded from: classes3.dex */
    public interface TXVideoProcessListener {
        void onProcessComplete(TXVideoEditConstants.TXGenerateResult tXGenerateResult);

        void onProcessProgress(float f);
    }

    public TXVideoEditer(Context context) {
        TXCLog.init();
        this.mContext = context.getApplicationContext();
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2472aw);
        TXCDRApi.initCrashReport(this.mContext);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        this.mVideoEditerPreview = new VideoEditerPreview(this.mContext);
        this.mVideoEditGenerate = new VideoEditGenerate(this.mContext);
        this.mVideoProcessGenerate = new VideoProcessGenerate(this.mContext);
        this.mVideoRecordGenerate = new VideoRecordGenerate(this.mContext);
        this.mVideoAverageThumbnailGenerate = new VideoAverageThumbnailGenerate(this.mContext);
        this.mVideoTimelistThumbnailGenerate = new VideoTimelistThumbnailGenerate(this.mContext);
    }

    public int setVideoPath(String str) {
        TXCLog.m2913i(TAG, "=== setVideoPath === videoPath: " + str);
        VideoSourceConfig videoSourceConfig = this.mVideoSourceConfig;
        videoSourceConfig.f3394a = str;
        return videoSourceConfig.m2410e();
    }

    private boolean isSmartLicense() {
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        if (LicenceCheck.m3120a().m3101c() == -1) {
            this.mSmartLicenseSupport = false;
        } else if (LicenceCheck.m3120a().m3101c() == 2) {
            return true;
        }
        return false;
    }

    public void setCustomVideoProcessListener(TXVideoCustomProcessListener tXVideoCustomProcessListener) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setCustomVideoProcessListener is not supported in UGC_Smart license");
            return;
        }
        this.mTXVideoCustomProcessListener = tXVideoCustomProcessListener;
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1975a(this.mTXCVideoCustomProcessListener);
        }
        VideoEditGenerate videoEditGenerate = this.mVideoEditGenerate;
        if (videoEditGenerate == null) {
            return;
        }
        videoEditGenerate.m1991a(this.mTXCVideoCustomProcessListener);
    }

    public void setSpecialRatio(float f) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setSpecialRatio is not supported in UGC_Smart license");
            return;
        }
        ComStaticFilter m2420d = this.mVideoPreProcessConfig.m2420d();
        if (m2420d == null) {
            m2420d = new ComStaticFilter();
        }
        m2420d.m2353a(f);
        m2420d.m2351b(0.0f);
        this.mVideoPreProcessConfig.m2424a(m2420d);
    }

    public void setFilter(Bitmap bitmap) {
        float f;
        float f2;
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setFilter is not supported in UGC_Smart license");
            return;
        }
        ComStaticFilter m2420d = this.mVideoPreProcessConfig.m2420d();
        if (m2420d != null) {
            f = m2420d.m2352b();
            f2 = m2420d.m2350c();
        } else {
            f = 0.5f;
            f2 = 0.0f;
        }
        setFilter(bitmap, f, null, f2, 1.0f);
    }

    public void setFilter(Bitmap bitmap, float f, Bitmap bitmap2, float f2, float f3) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setFilter is not supported in UGC_Smart license");
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2439aP);
        this.mVideoPreProcessConfig.m2424a(new ComStaticFilter(f3, bitmap, f, bitmap2, f2));
    }

    public void setBeautyFilter(int i, int i2) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBeautyFilter is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setBeautyFilter ==== beautyLevel: " + i + ",whiteningLevel:" + i2);
        this.mVideoPreProcessConfig.m2425a(new BeautyFilter(i, i2));
    }

    public int setPictureList(List<Bitmap> list, int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBeautyFilter is not supported in UGC_Smart license");
            return -1;
        } else if (list == null || list.size() <= 0) {
            TXCLog.m2914e(TAG, "setPictureList, bitmapList is empty!");
            return -1;
        } else {
            if (i <= 15) {
                TXCLog.m2913i(TAG, "setPictureList, fps <= 15, set 15");
                i = 15;
            }
            if (i >= 30) {
                TXCLog.m2913i(TAG, "setPictureList, fps >= 30, set 30");
                i = 30;
            }
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2446aW);
            this.mVideoSourceConfig.m2414a(list, i);
            this.mVideoEditerPreview.m1972a(list, i);
            return 0;
        }
    }

    public long setPictureTransition(int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setPictureTransition is not supported in UGC_Smart license");
            return 0L;
        }
        long m1984a = this.mVideoEditerPreview.m1984a(i);
        VideoOutputConfig.m2457a().f3374l = 1000 * m1984a;
        return m1984a;
    }

    public int setBGM(String str) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGM is not supported in UGC_Smart license");
            return 0;
        }
        TXCLog.m2913i(TAG, "==== setBGM ==== path: " + str);
        int m2415a = !TextUtils.isEmpty(str) ? VideoSourceConfig.m2416a().m2415a(str) : 0;
        if (m2415a != 0) {
            return m2415a;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2440aQ);
        this.mBgmConfig.m2504a(str);
        this.mVideoEditerPreview.m1965b(str);
        stopPlay();
        return 0;
    }

    public void setBGMLoop(boolean z) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMLoop is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setBGMLoop ==== looping: " + z);
        this.mBgmConfig.f3331e = z;
        this.mVideoEditerPreview.m1971a(z);
    }

    public void setBGMAtVideoTime(long j) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMAtVideoTime is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setBGMAtVideoTime ==== videoStartTime: " + j);
        this.mBgmConfig.f3330d = j;
        this.mVideoEditerPreview.m1982a(j);
    }

    public void setBGMStartTime(long j, long j2) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMStartTime is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setBGMStartTime ==== startTime: " + j + ", endTime: " + j2);
        BgmConfig bgmConfig = this.mBgmConfig;
        bgmConfig.f3328b = j;
        bgmConfig.f3329c = j2;
        this.mVideoEditerPreview.m1981a(j, j2);
    }

    public void setBGMVolume(float f) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMVolume is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setBGMVolume ==== volume: " + f);
        this.mBgmConfig.f3333g = f;
        this.mVideoEditerPreview.m1969b(f);
    }

    public void setBGMFadeInOutDuration(long j, long j2) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setBGMFadeInOutDuration is not supported in UGC_Smart license");
            return;
        }
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if ((i == 0 && j2 == 0) || i < 0 || j2 < 0) {
            this.mBgmConfig.f3336j = false;
            return;
        }
        BgmConfig bgmConfig = this.mBgmConfig;
        bgmConfig.f3336j = true;
        bgmConfig.f3337k = j;
        bgmConfig.f3338l = j2;
    }

    public void setWaterMark(Bitmap bitmap, TXVideoEditConstants.TXRect tXRect) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setWaterMark is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setWaterMark ==== waterMark: " + bitmap + ", rect: " + tXRect);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2444aU);
        TXCVideoEditConstants.C3517g c3517g = new TXCVideoEditConstants.C3517g();
        c3517g.f4382c = tXRect.width;
        c3517g.f4380a = tXRect.f5789x;
        c3517g.f4381b = tXRect.f5790y;
        this.mVideoPreProcessConfig.m2423a(new WaterMark(bitmap, c3517g));
    }

    public void setTailWaterMark(Bitmap bitmap, TXVideoEditConstants.TXRect tXRect, int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setTailWaterMark is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setTailWaterMark ==== tailWaterMark: " + bitmap + ", rect: " + tXRect + ", duration: " + i);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2445aV);
        TXCVideoEditConstants.C3517g c3517g = new TXCVideoEditConstants.C3517g();
        c3517g.f4382c = tXRect.width;
        c3517g.f4380a = tXRect.f5789x;
        c3517g.f4381b = tXRect.f5790y;
        TailWaterMarkChain.m1822a().m1821a(new TailWaterMark(bitmap, c3517g, i));
    }

    public void setSubtitleList(List<TXVideoEditConstants.TXSubtitle> list) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setSubtitleList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setSubtitleList ==== subtitleList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2443aT);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TXVideoEditConstants.TXSubtitle tXSubtitle = list.get(i);
                TXCVideoEditConstants.C3520j c3520j = new TXCVideoEditConstants.C3520j();
                TXCVideoEditConstants.C3517g c3517g = new TXCVideoEditConstants.C3517g();
                TXVideoEditConstants.TXRect tXRect = tXSubtitle.frame;
                c3517g.f4382c = tXRect.width;
                c3517g.f4380a = tXRect.f5789x;
                c3517g.f4381b = tXRect.f5790y;
                c3520j.f4390b = c3517g;
                c3520j.f4389a = tXSubtitle.titleImage;
                c3520j.f4391c = tXSubtitle.startTime;
                c3520j.f4392d = tXSubtitle.endTime;
                arrayList.add(c3520j);
            }
            SubtitleFilterChain.m1841a().m1838a(arrayList);
            return;
        }
        SubtitleFilterChain.m1841a().m1838a((List<TXCVideoEditConstants.C3520j>) null);
    }

    public void setAnimatedPasterList(List<TXVideoEditConstants.TXAnimatedPaster> list) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setAnimatedPasterList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setAnimatedPasterList ==== animatedPasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2442aS);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TXVideoEditConstants.TXAnimatedPaster tXAnimatedPaster = list.get(i);
                TXCVideoEditConstants.C3512b c3512b = new TXCVideoEditConstants.C3512b();
                TXCVideoEditConstants.C3517g c3517g = new TXCVideoEditConstants.C3517g();
                TXVideoEditConstants.TXRect tXRect = tXAnimatedPaster.frame;
                c3517g.f4382c = tXRect.width;
                c3517g.f4380a = tXRect.f5789x;
                c3517g.f4381b = tXRect.f5790y;
                c3512b.f4366b = c3517g;
                c3512b.f4365a = tXAnimatedPaster.animatedPasterPathFolder;
                c3512b.f4367c = tXAnimatedPaster.startTime;
                c3512b.f4368d = tXAnimatedPaster.endTime;
                c3512b.f4369e = tXAnimatedPaster.rotation;
                arrayList.add(c3512b);
            }
            AnimatedPasterFilterChain.m1935a().m1931a(arrayList);
            return;
        }
        AnimatedPasterFilterChain.m1935a().m1931a((List<TXCVideoEditConstants.C3512b>) null);
    }

    public void setPasterList(List<TXVideoEditConstants.TXPaster> list) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setPasterList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setPasterList ==== pasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2441aR);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TXVideoEditConstants.TXPaster tXPaster = list.get(i);
                TXCVideoEditConstants.C3515e c3515e = new TXCVideoEditConstants.C3515e();
                TXCVideoEditConstants.C3517g c3517g = new TXCVideoEditConstants.C3517g();
                TXVideoEditConstants.TXRect tXRect = tXPaster.frame;
                c3517g.f4382c = tXRect.width;
                c3517g.f4380a = tXRect.f5789x;
                c3517g.f4381b = tXRect.f5790y;
                c3515e.f4375b = c3517g;
                c3515e.f4374a = tXPaster.pasterImage;
                c3515e.f4376c = tXPaster.startTime;
                c3515e.f4377d = tXPaster.endTime;
                arrayList.add(c3515e);
            }
            PasterFilterChain.m1856a().m1853a(arrayList);
            return;
        }
        PasterFilterChain.m1856a().m1853a((List<TXCVideoEditConstants.C3515e>) null);
    }

    public void setRenderRotation(int i) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setRenderRotation is not supported in UGC_Smart license");
        } else {
            VideoPreProcessConfig.m2427a().m2426a(i);
        }
    }

    public void setSpeedList(List<TXVideoEditConstants.TXSpeed> list) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setSpeedList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setSpeedList ==== ");
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2435aL);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TXVideoEditConstants.TXSpeed tXSpeed = list.get(i);
                TXCVideoEditConstants.C3519i c3519i = new TXCVideoEditConstants.C3519i();
                c3519i.f4386a = tXSpeed.speedLevel;
                c3519i.f4387b = tXSpeed.startTime;
                c3519i.f4388c = tXSpeed.endTime;
                arrayList.add(c3519i);
            }
            SpeedFilterChain.m1849a().m1846a(arrayList);
            return;
        }
        SpeedFilterChain.m1849a().m1846a((List<TXCVideoEditConstants.C3519i>) null);
    }

    public void setRepeatPlay(List<TXVideoEditConstants.TXRepeat> list) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setRepeatPlay is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setRepeatPlay ==== ");
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2436aM);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TXVideoEditConstants.TXRepeat tXRepeat = list.get(i);
                TXCVideoEditConstants.C3518h c3518h = new TXCVideoEditConstants.C3518h();
                c3518h.f4385c = tXRepeat.repeatTimes;
                c3518h.f4383a = tXRepeat.startTime;
                c3518h.f4384b = tXRepeat.endTime;
                arrayList.add(c3518h);
            }
            RepeatPlayConfig.m2482a().m2481a(arrayList);
            TXVideoEditConstants.TXRepeat tXRepeat2 = list.get(0);
            this.mVideoEditerPreview.m1962c(tXRepeat2.startTime * 1000, tXRepeat2.endTime * 1000);
            return;
        }
        TXCLog.m2913i(TAG, "==== cancel setRepeatPlay ==== ");
        RepeatPlayConfig.m2482a().m2481a(null);
        this.mVideoEditerPreview.m1962c(0L, 0L);
    }

    public void setReverse(boolean z) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "setReverse is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== setReverse ====isReverse:" + z);
        if (z) {
            TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2437aN);
        }
        this.mVideoEditerPreview.m1964c();
        ReverseConfig.m2478a().m2477a(z);
    }

    public void startEffect(int i, long j) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "startEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== startEffect ==== type: " + i + ", startTime: " + j);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2438aO, i, "");
        Motion motion = null;
        switch (i) {
            case 0:
                motion = new Motion(2);
                break;
            case 1:
                motion = new Motion(3);
                break;
            case 2:
                motion = new Motion(0);
                break;
            case 3:
                motion = new Motion(1);
                break;
            case 4:
                motion = new Motion(4);
                break;
            case 5:
                motion = new Motion(5);
                break;
            case 6:
                motion = new Motion(6);
                break;
            case 7:
                motion = new Motion(7);
                break;
            case 8:
                motion = new Motion(8);
                break;
            case 9:
                motion = new Motion(11);
                break;
            case 10:
                motion = new Motion(10);
                break;
        }
        if (motion == null) {
            return;
        }
        if (ReverseConfig.m2478a().m2476b()) {
            motion.f3466c = j * 1000;
        } else {
            motion.f3465b = j * 1000;
        }
        this.mMotionFilterConfig.m2490a(motion);
    }

    public void stopEffect(int i, long j) {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "stopEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== stopEffect ==== type: " + i + ", endTime: " + j);
        Motion m2489b = this.mMotionFilterConfig.m2489b();
        if (m2489b == null) {
            return;
        }
        if (ReverseConfig.m2478a().m2476b()) {
            m2489b.f3465b = j * 1000;
        } else {
            m2489b.f3466c = j * 1000;
        }
    }

    public void deleteLastEffect() {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "deleteLastEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== deleteLastEffect ====");
        this.mMotionFilterConfig.m2488c();
    }

    public void deleteAllEffect() {
        if (isSmartLicense()) {
            TXCLog.m2914e(TAG, "deleteAllEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.m2913i(TAG, "==== deleteAllEffect ====");
        this.mMotionFilterConfig.m2486e();
    }

    public void setVideoProcessListener(TXVideoProcessListener tXVideoProcessListener) {
        this.mTXVideoProcessListener = tXVideoProcessListener;
        if (tXVideoProcessListener == null) {
            VideoProcessGenerate videoProcessGenerate = this.mVideoProcessGenerate;
            if (videoProcessGenerate != null) {
                videoProcessGenerate.m2206a((TXCVideoEditer.AbstractC3526e) null);
            }
            VideoRecordGenerate videoRecordGenerate = this.mVideoRecordGenerate;
            if (videoRecordGenerate != null) {
                videoRecordGenerate.m2206a((TXCVideoEditer.AbstractC3526e) null);
            }
            VideoAverageThumbnailGenerate videoAverageThumbnailGenerate = this.mVideoAverageThumbnailGenerate;
            if (videoAverageThumbnailGenerate == null) {
                return;
            }
            videoAverageThumbnailGenerate.m2054a((TXCVideoEditer.AbstractC3526e) null);
            return;
        }
        VideoProcessGenerate videoProcessGenerate2 = this.mVideoProcessGenerate;
        if (videoProcessGenerate2 != null) {
            videoProcessGenerate2.m2206a(this.mTXCVideoProcessListener);
        }
        VideoRecordGenerate videoRecordGenerate2 = this.mVideoRecordGenerate;
        if (videoRecordGenerate2 != null) {
            videoRecordGenerate2.m2206a(this.mTXCVideoProcessListener);
        }
        VideoAverageThumbnailGenerate videoAverageThumbnailGenerate2 = this.mVideoAverageThumbnailGenerate;
        if (videoAverageThumbnailGenerate2 == null) {
            return;
        }
        videoAverageThumbnailGenerate2.m2054a(this.mTXCVideoProcessListener);
    }

    public void getThumbnail(List<Long> list, int i, int i2, boolean z, TXThumbnailListener tXThumbnailListener) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.mTXThumbnailListener = tXThumbnailListener;
        ThumbnailConfig.m2474a().m2460i();
        ThumbnailConfig.m2474a().m2469a(z);
        VideoTimelistThumbnailGenerate videoTimelistThumbnailGenerate = this.mVideoTimelistThumbnailGenerate;
        if (videoTimelistThumbnailGenerate == null) {
            return;
        }
        videoTimelistThumbnailGenerate.m2198a(this.mTXCThumbnailListener);
        this.mVideoTimelistThumbnailGenerate.m2200a(i);
        this.mVideoTimelistThumbnailGenerate.m2196b(i2);
        this.mVideoTimelistThumbnailGenerate.m2195b(z);
        this.mVideoTimelistThumbnailGenerate.m2197a(list);
        this.mVideoTimelistThumbnailGenerate.mo1995a();
    }

    public void getThumbnail(int i, int i2, int i3, boolean z, TXThumbnailListener tXThumbnailListener) {
        ThumbnailConfig.m2474a().m2460i();
        this.mTXThumbnailListener = tXThumbnailListener;
        TXCVideoEditConstants.C3521k c3521k = new TXCVideoEditConstants.C3521k();
        c3521k.f4393a = i;
        c3521k.f4394b = i2;
        c3521k.f4395c = i3;
        ThumbnailConfig.m2474a().m2471a(c3521k);
        ThumbnailConfig.m2474a().m2469a(z);
        VideoAverageThumbnailGenerate videoAverageThumbnailGenerate = this.mVideoAverageThumbnailGenerate;
        if (videoAverageThumbnailGenerate != null) {
            videoAverageThumbnailGenerate.m2055a(this.mTXCThumbnailListener);
            this.mVideoAverageThumbnailGenerate.m2122a(true);
            this.mVideoAverageThumbnailGenerate.m2052b(z);
            this.mVideoAverageThumbnailGenerate.mo1995a();
        }
    }

    public void setThumbnail(TXVideoEditConstants.TXThumbnail tXThumbnail) {
        TXCVideoEditConstants.C3521k c3521k = new TXCVideoEditConstants.C3521k();
        c3521k.f4393a = tXThumbnail.count;
        c3521k.f4394b = tXThumbnail.width;
        c3521k.f4395c = tXThumbnail.height;
        ThumbnailConfig.m2474a().m2471a(c3521k);
    }

    public void setThumbnailListener(TXThumbnailListener tXThumbnailListener) {
        this.mTXThumbnailListener = tXThumbnailListener;
        if (tXThumbnailListener == null) {
            VideoProcessGenerate videoProcessGenerate = this.mVideoProcessGenerate;
            if (videoProcessGenerate != null) {
                videoProcessGenerate.m2207a((TXCVideoEditer.AbstractC3522a) null);
            }
            VideoRecordGenerate videoRecordGenerate = this.mVideoRecordGenerate;
            if (videoRecordGenerate != null) {
                videoRecordGenerate.m2207a((TXCVideoEditer.AbstractC3522a) null);
            }
            VideoAverageThumbnailGenerate videoAverageThumbnailGenerate = this.mVideoAverageThumbnailGenerate;
            if (videoAverageThumbnailGenerate != null) {
                videoAverageThumbnailGenerate.m2055a((TXCVideoEditer.AbstractC3522a) null);
            }
            VideoTimelistThumbnailGenerate videoTimelistThumbnailGenerate = this.mVideoTimelistThumbnailGenerate;
            if (videoTimelistThumbnailGenerate == null) {
                return;
            }
            videoTimelistThumbnailGenerate.m2198a((TXCVideoEditer.AbstractC3522a) null);
            return;
        }
        VideoProcessGenerate videoProcessGenerate2 = this.mVideoProcessGenerate;
        if (videoProcessGenerate2 != null) {
            videoProcessGenerate2.m2207a(this.mTXCThumbnailListener);
        }
        VideoRecordGenerate videoRecordGenerate2 = this.mVideoRecordGenerate;
        if (videoRecordGenerate2 != null) {
            videoRecordGenerate2.m2207a(this.mTXCThumbnailListener);
        }
        VideoAverageThumbnailGenerate videoAverageThumbnailGenerate2 = this.mVideoAverageThumbnailGenerate;
        if (videoAverageThumbnailGenerate2 != null) {
            videoAverageThumbnailGenerate2.m2055a(this.mTXCThumbnailListener);
        }
        VideoTimelistThumbnailGenerate videoTimelistThumbnailGenerate2 = this.mVideoTimelistThumbnailGenerate;
        if (videoTimelistThumbnailGenerate2 == null) {
            return;
        }
        videoTimelistThumbnailGenerate2.m2198a(this.mTXCThumbnailListener);
    }

    public void processVideo() {
        TXCLog.m2913i(TAG, "=== processVideo ===");
        if (LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext) != 0 || (LicenceCheck.m3120a().m3101c() == 2 && !this.mSmartLicenseSupport)) {
            TXVideoEditConstants.TXGenerateResult tXGenerateResult = new TXVideoEditConstants.TXGenerateResult();
            tXGenerateResult.retCode = -5;
            tXGenerateResult.descMsg = "licence校验失败";
            TXVideoProcessListener tXVideoProcessListener = this.mTXVideoProcessListener;
            if (tXVideoProcessListener == null) {
                return;
            }
            tXVideoProcessListener.onProcessComplete(tXGenerateResult);
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2483ba);
        this.mVideoOutputConfig.f3377o = VideoUtil.m1420a(1);
        VideoOutputConfig videoOutputConfig = this.mVideoOutputConfig;
        videoOutputConfig.f3372j = 3;
        videoOutputConfig.f3375m = true;
        boolean m2409f = this.mVideoSourceConfig.m2409f();
        TXCLog.m2913i(TAG, "allFullFrame:" + m2409f);
        this.mVideoOutputConfig.f3380r = m2409f;
        if (m2409f) {
            VideoRecordGenerate videoRecordGenerate = this.mVideoRecordGenerate;
            if (videoRecordGenerate == null) {
                return;
            }
            videoRecordGenerate.mo1995a();
            return;
        }
        VideoProcessGenerate videoProcessGenerate = this.mVideoProcessGenerate;
        if (videoProcessGenerate == null) {
            return;
        }
        videoProcessGenerate.mo1995a();
    }

    public void release() {
        TXCLog.m2913i(TAG, "=== release ===");
        VideoOutputConfig.m2457a().m2430o();
        VideoSourceConfig.m2416a().m2408g();
        CutTimeConfig.m2501a().m2492h();
        ReverseConfig.m2478a().m2475c();
        RepeatPlayConfig.m2482a().m2479c();
        SpeedFilterChain.m1849a().m1842d();
        VideoPreProcessConfig.m2427a().m2418f();
        SubtitleFilterChain.m1841a().m1835c();
        PasterFilterChain.m1856a().m1850c();
        AnimatedPasterFilterChain.m1935a().m1928c();
        MotionFilterConfig.m2491a().m2486e();
        BgmConfig.m2505a().m2503b();
        TailWaterMarkChain.m1822a().m1812i();
        ThumbnailConfig.m2474a().m2459j();
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1956f();
        }
        VideoRecordGenerate videoRecordGenerate = this.mVideoRecordGenerate;
        if (videoRecordGenerate != null) {
            videoRecordGenerate.mo2051c();
        }
        VideoProcessGenerate videoProcessGenerate = this.mVideoProcessGenerate;
        if (videoProcessGenerate != null) {
            videoProcessGenerate.mo2051c();
        }
        VideoEditGenerate videoEditGenerate = this.mVideoEditGenerate;
        if (videoEditGenerate != null) {
            videoEditGenerate.mo2051c();
        }
        VideoTimelistThumbnailGenerate videoTimelistThumbnailGenerate = this.mVideoTimelistThumbnailGenerate;
        if (videoTimelistThumbnailGenerate != null) {
            videoTimelistThumbnailGenerate.mo2051c();
        }
        VideoAverageThumbnailGenerate videoAverageThumbnailGenerate = this.mVideoAverageThumbnailGenerate;
        if (videoAverageThumbnailGenerate != null) {
            videoAverageThumbnailGenerate.mo2051c();
        }
        this.mTXCThumbnailListener = null;
        this.mTXCVideoCustomProcessListener = null;
        this.mTXCVideoGenerateListener = null;
        this.mTXCVideoPreviewListener = null;
        this.mTXCVideoProcessListener = null;
    }

    public void setTXVideoPreviewListener(TXVideoPreviewListener tXVideoPreviewListener) {
        this.mTXVideoPreviewListener = tXVideoPreviewListener;
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            if (tXVideoPreviewListener == null) {
                videoEditerPreview.m1974a((TXCVideoEditer.AbstractC3525d) null);
            } else {
                videoEditerPreview.m1974a(this.mTXCVideoPreviewListener);
            }
        }
    }

    public void initWithPreview(TXVideoEditConstants.TXPreviewParam tXPreviewParam) {
        if (tXPreviewParam == null) {
            TXCLog.m2914e(TAG, "=== initWithPreview === please set param not null");
            return;
        }
        TXCLog.m2913i(TAG, "=== initWithPreview === rendeMode: " + tXPreviewParam.renderMode);
        TXCVideoEditConstants.C3516f c3516f = new TXCVideoEditConstants.C3516f();
        c3516f.f4379b = tXPreviewParam.renderMode;
        c3516f.f4378a = tXPreviewParam.videoView;
        this.mVideoOutputConfig.f3381s = c3516f.f4379b;
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview == null) {
            return;
        }
        videoEditerPreview.m1976a(c3516f);
    }

    public void startPlayFromTime(long j, long j2) {
        TXCLog.m2913i(TAG, "==== startPlayFromTime ==== startTime: " + j + ", endTime: " + j2);
        if (this.mIsPreviewStart) {
            stopPlay();
        }
        this.mIsPreviewStart = true;
        if (this.mVideoEditerPreview != null) {
            CutTimeConfig.m2501a().m2498b(j * 1000, 1000 * j2);
            this.mVideoEditerPreview.m1967b(j, j2);
            this.mVideoEditerPreview.m1970b();
        }
    }

    public void pausePlay() {
        TXCLog.m2913i(TAG, "==== pausePlay ====");
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1958e();
        }
    }

    public void resumePlay() {
        TXCLog.m2913i(TAG, "==== resumePlay ====");
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1960d();
        }
    }

    public void stopPlay() {
        TXCLog.m2913i(TAG, "==== stopPlay ====");
        if (this.mIsPreviewStart) {
            VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
            if (videoEditerPreview != null) {
                videoEditerPreview.m1964c();
            }
            this.mIsPreviewStart = false;
        }
    }

    public void previewAtTime(long j) {
        TXCLog.m2913i(TAG, "==== previewAtTime ==== timeMs: " + j);
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1968b(j);
        }
    }

    public void setVideoGenerateListener(TXVideoGenerateListener tXVideoGenerateListener) {
        TXCLog.m2913i(TAG, "=== setVideoGenerateListener === listener:" + tXVideoGenerateListener);
        this.mTXVideoGenerateListener = tXVideoGenerateListener;
        VideoEditGenerate videoEditGenerate = this.mVideoEditGenerate;
        if (videoEditGenerate != null) {
            if (tXVideoGenerateListener == null) {
                videoEditGenerate.m1990a((TXCVideoEditer.AbstractC3524c) null);
            } else {
                videoEditGenerate.m1990a(this.mTXCVideoGenerateListener);
            }
        }
    }

    public void setCutFromTime(long j, long j2) {
        TXCLog.m2913i(TAG, "==== setCutFromTime ==== startTime: " + j + ", endTime: " + j2);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2434aK);
        CutTimeConfig.m2501a().m2500a(j * 1000, j2 * 1000);
    }

    public void setVideoBitrate(int i) {
        TXCLog.m2913i(TAG, "==== setVideoBitrate ==== videoBitrate: " + i);
        this.mVideoOutputConfig.f3378p = i;
    }

    public void setAudioBitrate(int i) {
        TXCLog.m2913i(TAG, "==== setAudioBitrate ==== audioBitrate: " + i);
        this.mVideoOutputConfig.f3379q = i * 1024;
    }

    public void generateVideo(int i, String str) {
        TXCLog.m2913i(TAG, "==== generateVideo ==== videoCompressed: " + i + ", videoOutputPath: " + str);
        if (LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext) != 0 || (LicenceCheck.m3120a().m3101c() == 2 && !this.mSmartLicenseSupport)) {
            TXVideoEditConstants.TXGenerateResult tXGenerateResult = new TXVideoEditConstants.TXGenerateResult();
            tXGenerateResult.retCode = -5;
            tXGenerateResult.descMsg = "licence校验失败";
            TXVideoGenerateListener tXVideoGenerateListener = this.mTXVideoGenerateListener;
            if (tXVideoGenerateListener == null) {
                return;
            }
            tXVideoGenerateListener.onGenerateComplete(tXGenerateResult);
            return;
        }
        VideoOutputConfig videoOutputConfig = this.mVideoOutputConfig;
        videoOutputConfig.f3371i = str;
        videoOutputConfig.f3372j = i;
        videoOutputConfig.f3375m = false;
        VideoEditGenerate videoEditGenerate = this.mVideoEditGenerate;
        if (videoEditGenerate == null) {
            return;
        }
        videoEditGenerate.mo1995a();
    }

    public void cancel() {
        TXCLog.m2913i(TAG, "==== cancel ====");
        VideoAverageThumbnailGenerate videoAverageThumbnailGenerate = this.mVideoAverageThumbnailGenerate;
        if (videoAverageThumbnailGenerate != null) {
            videoAverageThumbnailGenerate.mo1989b();
        }
        VideoTimelistThumbnailGenerate videoTimelistThumbnailGenerate = this.mVideoTimelistThumbnailGenerate;
        if (videoTimelistThumbnailGenerate != null) {
            videoTimelistThumbnailGenerate.mo1989b();
        }
        VideoRecordGenerate videoRecordGenerate = this.mVideoRecordGenerate;
        if (videoRecordGenerate != null) {
            videoRecordGenerate.mo1989b();
        }
        VideoProcessGenerate videoProcessGenerate = this.mVideoProcessGenerate;
        if (videoProcessGenerate != null) {
            videoProcessGenerate.mo1989b();
        }
        VideoEditGenerate videoEditGenerate = this.mVideoEditGenerate;
        if (videoEditGenerate != null) {
            videoEditGenerate.mo1989b();
        }
    }

    public void refreshOneFrame() {
        TXCLog.m2913i(TAG, "==== refreshOneFrame ====");
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1986a();
        }
    }

    public void setVideoVolume(float f) {
        TXCLog.m2913i(TAG, "==== setVideoVolume ==== volume: " + f);
        this.mBgmConfig.f3332f = f;
        VideoEditerPreview videoEditerPreview = this.mVideoEditerPreview;
        if (videoEditerPreview != null) {
            videoEditerPreview.m1985a(f);
        }
    }
}
