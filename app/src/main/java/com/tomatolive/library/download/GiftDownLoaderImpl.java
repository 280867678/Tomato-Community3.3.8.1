package com.tomatolive.library.download;

import android.text.TextUtils;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.List;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class GiftDownLoaderImpl implements IGiftDownLoader {
    private GiftDownLoaderImpl() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final GiftDownLoaderImpl INSTANCE = new GiftDownLoaderImpl();

        private SingletonHolder() {
        }
    }

    public static GiftDownLoaderImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.tomatolive.library.download.IGiftDownLoader
    public void startDownLoad(List<GiftDownloadItemEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
            if (giftDownloadItemEntity != null && !TextUtils.isEmpty(giftDownloadItemEntity.animalUrl)) {
                downloadFile(giftDownloadItemEntity.animalUrl, GiftConfig.INSTANCE.animResRootPath, FileUtils.formatSVGAFileName(giftDownloadItemEntity.getLocalDirName()), giftDownloadItemEntity);
            }
        }
    }

    public synchronized void downloadFile(String str, final String str2, final String str3, final GiftDownloadItemEntity giftDownloadItemEntity) {
        if (!AppUtils.isDownloadService()) {
            return;
        }
        GiftDownLoadManager.getInstance().updateDownloadItem(giftDownloadItemEntity.markId, giftDownloadItemEntity.giftDirPath, 1);
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function<ResponseBody, File>() { // from class: com.tomatolive.library.download.GiftDownLoaderImpl.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(ResponseBody responseBody) throws Exception {
                return FileUtils.saveFile(responseBody, str2, str3);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new FileDownLoadObserver<File>() { // from class: com.tomatolive.library.download.GiftDownLoaderImpl.1
            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onProgress(int i, long j) {
            }

            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onDownLoadSuccess(File file) {
                GiftDownLoaderImpl.this.dealDownLoadItem(giftDownloadItemEntity);
            }

            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onDownLoadFail(Throwable th) {
                GiftDownLoadManager giftDownLoadManager = GiftDownLoadManager.getInstance();
                GiftDownloadItemEntity giftDownloadItemEntity2 = giftDownloadItemEntity;
                giftDownLoadManager.updateDownloadItem(giftDownloadItemEntity2.markId, giftDownloadItemEntity2.giftDirPath, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealDownLoadItem(GiftDownloadItemEntity giftDownloadItemEntity) {
        if (giftDownloadItemEntity != null) {
            giftDownloadItemEntity.giftDirPath = FileUtils.formatSVGAFileName(getGiftDirPath(giftDownloadItemEntity.getLocalDirName()));
            GiftDownLoadManager.getInstance().updateDownloadItem(giftDownloadItemEntity.markId, giftDownloadItemEntity.giftDirPath, 2);
        }
    }

    private String getGiftDirPath(String str) {
        return GiftConfig.INSTANCE.animResRootPath + str;
    }
}
