package com.tomatolive.library.download;

import android.text.TextUtils;
import com.blankj.utilcode.util.FileUtils;
import com.tomatolive.library.model.SysParamsInfoEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class ResHotLoadManager {
    public static final String AWARD_ANIM_RES = "lottery_anim_award.svga";
    public static final String BOOM_ANIM_RES = "lottery_anim_boom.svga";
    public static final String COMPOSE_ANIM_RES = "lottery_anim_compose.svga";
    public static final String IMG_LOTTERY_TURNTABLE_LIGHT = "fq_ic_lottery_turntable_light.png";
    public static final String IMG_NOBILITY_HD_RECOMMEND_BG = "fq_ic_nobility_hd_recommend_bg.png";
    public final String destDir;

    private ResHotLoadManager() {
        this.destDir = GiftConfig.INSTANCE.resHotLoadRootPath;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final ResHotLoadManager INSTANCE = new ResHotLoadManager();

        private SingletonHolder() {
        }
    }

    public static ResHotLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void dealResLoad(SysParamsInfoEntity sysParamsInfoEntity) {
        if (!TextUtils.equals(SysConfigInfoManager.getInstance().getLocalResourceVersion(), sysParamsInfoEntity.resourceVersion)) {
            SysConfigInfoManager.getInstance().setLocalResourceVersion(sysParamsInfoEntity.resourceVersion);
            SysConfigInfoManager.getInstance().setLocalResUrl(sysParamsInfoEntity.resourceDownloadUrl);
            startDownloadRes(sysParamsInfoEntity.resourceDownloadUrl);
        }
    }

    public void reLoad() {
        startDownloadRes(SysConfigInfoManager.getInstance().getLocalResUrl());
    }

    private void startDownloadRes(String str) {
        if (!AppUtils.isDownloadService() || TextUtils.isEmpty(str)) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(FileUtils.getFileNameNoExtension(str));
        stringBuffer.append(".zip");
        final String stringBuffer2 = stringBuffer.toString();
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$ResHotLoadManager$9NRVrxlxpu5VDHgE4q8IPhG4SAw
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return ResHotLoadManager.this.lambda$startDownloadRes$0$ResHotLoadManager(stringBuffer2, (ResponseBody) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<File>() { // from class: com.tomatolive.library.download.ResHotLoadManager.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(File file) {
                ResHotLoadManager.this.dealFile(file);
            }
        });
    }

    public /* synthetic */ File lambda$startDownloadRes$0$ResHotLoadManager(String str, ResponseBody responseBody) throws Exception {
        return com.tomatolive.library.utils.FileUtils.saveFile(responseBody, this.destDir, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealFile(File file) {
        if (file != null) {
            unZipRes(file.getAbsolutePath());
        }
    }

    public void unZipRes(final String str) {
        try {
            if (!com.tomatolive.library.utils.FileUtils.isFileExists(str) || !com.tomatolive.library.utils.FileUtils.unZip(str, this.destDir)) {
                return;
            }
            Observable.timer(5L, TimeUnit.SECONDS).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.download.ResHotLoadManager.2
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Long l) {
                    com.tomatolive.library.utils.FileUtils.deleteFile(str);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResHotLoadPath(String str) {
        return this.destDir + str;
    }

    public boolean isExitResHotLoadFile(String str) {
        return com.tomatolive.library.utils.FileUtils.isFileExists(getResHotLoadPath(str));
    }
}
