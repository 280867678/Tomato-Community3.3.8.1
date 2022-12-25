package com.tencent.ugc;

import android.content.Context;
import android.os.Build;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p107c.LicenceCheck;
import com.tencent.liteav.basic.p107c.LicenceInfo;
import com.tencent.liteav.p104b.TXCombineVideo;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.MediaExtractorWrapper;
import com.tencent.liteav.p122g.VideoJoinGenerate;
import com.tencent.liteav.p122g.VideoJoinPreview;
import com.tencent.liteav.p122g.VideoOutputListConfig;
import com.tencent.liteav.p122g.VideoSourceListConfig;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoJoiner;
import com.tencent.liteav.p125j.TXResolutionUtils;
import com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TXVideoJoiner {
    private static final String TAG = "TXVideoJoiner";
    private Context mContext;
    private TXQuickJoiner mQuickJointer;
    private TXCombineVideo mTXCombineVideo;
    private TXVideoJoinerListener mTXVideoJoinerListener;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private VideoJoinGenerate mVideoJoinGenerate;
    private VideoJoinPreview mVideoJoinPreview;
    private List<String> mVideoPathList;
    private TXCVideoJoiner.AbstractC3528b mTXCVideoPreviewListener = new TXCVideoJoiner.AbstractC3528b() { // from class: com.tencent.ugc.TXVideoJoiner.1
        @Override // com.tencent.liteav.p124i.TXCVideoJoiner.AbstractC3528b
        /* renamed from: a */
        public void mo259a(int i) {
            if (TXVideoJoiner.this.mTXVideoPreviewListener != null) {
                TXVideoJoiner.this.mTXVideoPreviewListener.onPreviewProgress(i);
            }
        }

        @Override // com.tencent.liteav.p124i.TXCVideoJoiner.AbstractC3528b
        /* renamed from: a */
        public void mo260a() {
            if (TXVideoJoiner.this.mTXVideoPreviewListener != null) {
                TXVideoJoiner.this.mTXVideoPreviewListener.onPreviewFinished();
            }
        }
    };
    private TXCVideoJoiner.AbstractC3527a mTXCVideoJoinerListener = new TXCVideoJoiner.AbstractC3527a() { // from class: com.tencent.ugc.TXVideoJoiner.2
        @Override // com.tencent.liteav.p124i.TXCVideoJoiner.AbstractC3527a
        /* renamed from: a */
        public void mo258a(float f) {
            if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                TXVideoJoiner.this.mTXVideoJoinerListener.onJoinProgress(f);
            }
        }

        @Override // com.tencent.liteav.p124i.TXCVideoJoiner.AbstractC3527a
        /* renamed from: a */
        public void mo257a(TXCVideoEditConstants.C3514d c3514d) {
            TXVideoEditConstants.TXJoinerResult tXJoinerResult = new TXVideoEditConstants.TXJoinerResult();
            tXJoinerResult.retCode = c3514d.f4372a;
            tXJoinerResult.descMsg = c3514d.f4373b;
            if (tXJoinerResult.retCode == 0) {
                int m2429p = VideoOutputConfig.m2457a().m2429p();
                int m2428q = VideoOutputConfig.m2457a().m2428q();
                TXCDRApi.txReportDAU(TXVideoJoiner.this.mContext, TXCDRDef.f2448aY, m2429p, "");
                TXCDRApi.txReportDAU(TXVideoJoiner.this.mContext, TXCDRDef.f2449aZ, m2428q, "");
            }
            if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                TXVideoJoiner.this.mTXVideoJoinerListener.onJoinComplete(tXJoinerResult);
            }
        }
    };
    private VideoSourceListConfig mVideoSourceListConfig = VideoSourceListConfig.m1480a();
    private VideoOutputListConfig mVideoOutputListConfig = VideoOutputListConfig.m1481r();

    /* loaded from: classes3.dex */
    public interface TXVideoJoinerListener {
        void onJoinComplete(TXVideoEditConstants.TXJoinerResult tXJoinerResult);

        void onJoinProgress(float f);
    }

    /* loaded from: classes3.dex */
    public interface TXVideoPreviewListener {
        void onPreviewFinished();

        void onPreviewProgress(int i);
    }

    public TXVideoJoiner(Context context) {
        TXCLog.init();
        this.mContext = context.getApplicationContext();
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2473ax);
        TXCDRApi.initCrashReport(this.mContext);
        LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        this.mVideoJoinPreview = new VideoJoinPreview(this.mContext);
        this.mVideoJoinGenerate = new VideoJoinGenerate(this.mContext);
        this.mTXCombineVideo = new TXCombineVideo(context);
    }

    public int setVideoPathList(List<String> list) {
        if (list == null || list.size() == 0) {
            TXCLog.m2914e(TAG, "==== setVideoPathList ==== is empty");
            return 0;
        }
        this.mVideoPathList = list;
        this.mVideoSourceListConfig.m1479a(list);
        this.mTXCombineVideo.m3244a(list);
        return this.mVideoSourceListConfig.m1478b();
    }

    public void initWithPreview(TXVideoEditConstants.TXPreviewParam tXPreviewParam) {
        if (tXPreviewParam == null) {
            TXCLog.m2914e(TAG, "=== initWithPreview === please set param not null");
            return;
        }
        TXCLog.m2913i(TAG, "=== initWithPreview === rendeMode: " + tXPreviewParam.renderMode);
        TXCVideoEditConstants.C3516f c3516f = new TXCVideoEditConstants.C3516f();
        c3516f.f4378a = tXPreviewParam.videoView;
        c3516f.f4379b = tXPreviewParam.renderMode;
        this.mVideoOutputListConfig.f4272t = c3516f.f4379b;
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview == null) {
            return;
        }
        videoJoinPreview.m1510a(c3516f);
    }

    public void setTXVideoPreviewListener(TXVideoPreviewListener tXVideoPreviewListener) {
        TXCLog.m2913i(TAG, "==== setTXVideoPreviewListener ====listener:" + tXVideoPreviewListener);
        this.mTXVideoPreviewListener = tXVideoPreviewListener;
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview != null) {
            if (tXVideoPreviewListener == null) {
                videoJoinPreview.m1509a((TXCVideoJoiner.AbstractC3528b) null);
            } else {
                videoJoinPreview.m1509a(this.mTXCVideoPreviewListener);
            }
        }
    }

    public void startPlay() {
        TXCLog.m2913i(TAG, "==== startPlay ====");
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview != null) {
            videoJoinPreview.m1515a();
        }
    }

    public void pausePlay() {
        TXCLog.m2913i(TAG, "==== pausePlay ====");
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview != null) {
            videoJoinPreview.m1506c();
        }
    }

    public void resumePlay() {
        TXCLog.m2913i(TAG, "==== resumePlay ====");
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview != null) {
            videoJoinPreview.m1504d();
        }
    }

    public void stopPlay() {
        TXCLog.m2913i(TAG, "==== stopPlay ====");
        VideoJoinPreview videoJoinPreview = this.mVideoJoinPreview;
        if (videoJoinPreview != null) {
            videoJoinPreview.m1508b();
        }
    }

    public void setVideoJoinerListener(TXVideoJoinerListener tXVideoJoinerListener) {
        TXCLog.m2913i(TAG, "=== setVideoJoinerListener === listener:" + tXVideoJoinerListener);
        this.mTXVideoJoinerListener = tXVideoJoinerListener;
        VideoJoinGenerate videoJoinGenerate = this.mVideoJoinGenerate;
        if (videoJoinGenerate != null) {
            if (tXVideoJoinerListener == null) {
                videoJoinGenerate.m1553a((TXCVideoJoiner.AbstractC3527a) null);
            } else {
                videoJoinGenerate.m1553a(this.mTXCVideoJoinerListener);
            }
        }
    }

    public void joinVideo(int i, String str) {
        VideoJoinGenerate videoJoinGenerate;
        TXCLog.m2913i(TAG, "==== joinVideo ====");
        int m3112a = LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        if (m3112a != 0 || LicenceCheck.m3120a().m3101c() == 2) {
            TXCLog.m2914e(TAG, "joinVideo, checkErrCode = " + m3112a + ", licenseVersionType = " + LicenceCheck.m3120a().m3101c());
            TXVideoEditConstants.TXJoinerResult tXJoinerResult = new TXVideoEditConstants.TXJoinerResult();
            tXJoinerResult.retCode = -5;
            tXJoinerResult.descMsg = "licence校验失败";
            TXVideoJoinerListener tXVideoJoinerListener = this.mTXVideoJoinerListener;
            if (tXVideoJoinerListener == null) {
                return;
            }
            tXVideoJoinerListener.onJoinComplete(tXJoinerResult);
            return;
        }
        VideoOutputListConfig videoOutputListConfig = this.mVideoOutputListConfig;
        videoOutputListConfig.f3371i = str;
        videoOutputListConfig.f4273u = i;
        if (quickJoin() || (videoJoinGenerate = this.mVideoJoinGenerate) == null) {
            return;
        }
        videoJoinGenerate.m1559a();
    }

    public void cancel() {
        TXCLog.m2913i(TAG, "==== cancel ====");
        VideoJoinGenerate videoJoinGenerate = this.mVideoJoinGenerate;
        if (videoJoinGenerate != null) {
            videoJoinGenerate.m1552b();
        }
        TXQuickJoiner tXQuickJoiner = this.mQuickJointer;
        if (tXQuickJoiner != null) {
            tXQuickJoiner.m478c();
            this.mQuickJointer = null;
        }
        TXCombineVideo tXCombineVideo = this.mTXCombineVideo;
        if (tXCombineVideo != null) {
            tXCombineVideo.m3246a((TXCVideoJoiner.AbstractC3527a) null);
            this.mTXCombineVideo.m3242b();
        }
    }

    private boolean quickJoin() {
        this.mQuickJointer = new TXQuickJoiner();
        this.mQuickJointer.m481a(this.mVideoPathList);
        this.mQuickJointer.m482a(this.mVideoOutputListConfig.f3371i);
        boolean isMatchQuickJoin = isMatchQuickJoin();
        if (isMatchQuickJoin) {
            this.mQuickJointer.m488a(new TXQuickJoiner.AbstractC3670a() { // from class: com.tencent.ugc.TXVideoJoiner.3
                @Override // com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner.AbstractC3670a
                /* renamed from: a */
                public void mo255a(TXQuickJoiner tXQuickJoiner, int i, String str) {
                    tXQuickJoiner.m478c();
                    tXQuickJoiner.m476d();
                    TXVideoJoiner.this.mQuickJointer = null;
                    TXVideoEditConstants.TXJoinerResult tXJoinerResult = new TXVideoEditConstants.TXJoinerResult();
                    tXJoinerResult.retCode = i == 0 ? 0 : -1;
                    tXJoinerResult.descMsg = str;
                    if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                        TXVideoJoiner.this.mTXVideoJoinerListener.onJoinComplete(tXJoinerResult);
                    }
                }

                @Override // com.tencent.liteav.videoediter.ffmpeg.TXQuickJoiner.AbstractC3670a
                /* renamed from: a */
                public void mo256a(TXQuickJoiner tXQuickJoiner, float f) {
                    if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                        TXVideoJoiner.this.mTXVideoJoinerListener.onJoinProgress(f);
                    }
                }
            });
            TXCLog.m2913i(TAG, "==== quickJoin ====");
            this.mQuickJointer.m480b();
        }
        return isMatchQuickJoin;
    }

    private boolean isMatchQuickJoin() {
        boolean m490a = this.mQuickJointer.m490a();
        if (m490a) {
            int m474e = this.mQuickJointer.m474e();
            int m472f = this.mQuickJointer.m472f();
            this.mVideoOutputListConfig.m1484a(this.mVideoSourceListConfig.m1472h());
            int[] m1431a = TXResolutionUtils.m1431a(this.mVideoOutputListConfig.f4273u, m474e, m472f);
            m490a = m474e == m1431a[0] && m472f == m1431a[1];
        }
        if (m490a && isVideoDurationBiggerTooMuchThanAudio()) {
            return false;
        }
        return m490a;
    }

    private boolean isVideoDurationBiggerTooMuchThanAudio() {
        if (Build.VERSION.SDK_INT >= 16) {
            MediaExtractorWrapper mediaExtractorWrapper = new MediaExtractorWrapper();
            for (int i = 0; i < this.mVideoPathList.size(); i++) {
                try {
                    mediaExtractorWrapper.m1744a(this.mVideoPathList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long m1730j = mediaExtractorWrapper.m1730j();
                long m1729k = mediaExtractorWrapper.m1729k();
                if (m1730j <= 0 || m1729k <= 0) {
                    return true;
                }
                if (m1730j - m1729k > 400000) {
                    TXCLog.m2913i(TAG, "isVideoDurationBiggerTooMuchThanAudio, videoDuration = " + m1730j + ", audioDuration = " + m1729k);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0080, code lost:
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean hasBFrame() {
        if (Build.VERSION.SDK_INT >= 16) {
            MediaExtractorWrapper mediaExtractorWrapper = new MediaExtractorWrapper();
            int i = 0;
            while (i < this.mVideoPathList.size()) {
                long j = -1;
                try {
                    mediaExtractorWrapper.m1744a(this.mVideoPathList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int m1737d = mediaExtractorWrapper.m1737d();
                if (m1737d <= 0) {
                    m1737d = 1;
                }
                mediaExtractorWrapper.m1746a(0L);
                while (true) {
                    long m1722r = mediaExtractorWrapper.m1722r();
                    TXCLog.m2913i(TAG, "isMatchQuickJoin, video index = " + i + ", pts = " + m1722r + ", lastVideoPts = " + j);
                    if (m1722r < m1737d * 1000000) {
                        if (m1722r < j) {
                            mediaExtractorWrapper.m1725o();
                            return true;
                        } else if (mediaExtractorWrapper.m1738c(new Frame())) {
                            break;
                        } else {
                            j = m1722r;
                        }
                    }
                }
            }
            mediaExtractorWrapper.m1725o();
        }
        return false;
    }

    public void setSplitScreenList(List<TXVideoEditConstants.TXAbsoluteRect> list, int i, int i2) {
        TXCLog.m2913i(TAG, "==== setSplitScreenList ====canvasWidth:" + i + ",canvasHeight:" + i2);
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            TXVideoEditConstants.TXAbsoluteRect tXAbsoluteRect = list.get(i3);
            TXCVideoEditConstants.C3511a c3511a = new TXCVideoEditConstants.C3511a();
            c3511a.f4363c = tXAbsoluteRect.width;
            c3511a.f4364d = tXAbsoluteRect.height;
            c3511a.f4361a = tXAbsoluteRect.f5787x;
            c3511a.f4362b = tXAbsoluteRect.f5788y;
            arrayList.add(c3511a);
        }
        this.mTXCombineVideo.m3243a(arrayList, i, i2);
    }

    public void splitJoinVideo(int i, String str) {
        TXCLog.m2913i(TAG, "==== splitJoinVideo ====");
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2447aX);
        int m3112a = LicenceCheck.m3120a().m3112a((LicenceInfo) null, this.mContext);
        if (m3112a != 0 || LicenceCheck.m3120a().m3101c() == 2) {
            TXCLog.m2914e(TAG, "splitJoinVideo, checkErrCode = " + m3112a + ", licenseVersionType = " + LicenceCheck.m3120a().m3101c());
            TXVideoEditConstants.TXJoinerResult tXJoinerResult = new TXVideoEditConstants.TXJoinerResult();
            tXJoinerResult.retCode = -5;
            tXJoinerResult.descMsg = "licence校验失败";
            TXVideoJoinerListener tXVideoJoinerListener = this.mTXVideoJoinerListener;
            if (tXVideoJoinerListener == null) {
                return;
            }
            tXVideoJoinerListener.onJoinComplete(tXJoinerResult);
            return;
        }
        TXCombineVideo tXCombineVideo = this.mTXCombineVideo;
        if (tXCombineVideo == null) {
            return;
        }
        tXCombineVideo.m3245a(str);
        this.mTXCombineVideo.m3246a(this.mTXCVideoJoinerListener);
        this.mTXCombineVideo.m3248a();
    }
}
