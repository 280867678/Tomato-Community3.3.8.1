package com.tomatolive.library.download;

import android.text.TextUtils;
import com.tomatolive.library.model.CarDownloadEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class CarDownLoaderImpl implements ICarDownLoader {
    private CarDownLoaderImpl() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final CarDownLoaderImpl INSTANCE = new CarDownLoaderImpl();

        private SingletonHolder() {
        }
    }

    public static CarDownLoaderImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.tomatolive.library.download.ICarDownLoader
    public void startDownLoad(List<CarDownloadEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (CarDownloadEntity carDownloadEntity : list) {
            if (carDownloadEntity != null) {
                if (!TextUtils.isEmpty(carDownloadEntity.animalUrl)) {
                    downloadFile(carDownloadEntity.animalUrl, GiftConfig.INSTANCE.carAnimResRootPath, FileUtils.formatSVGAFileName(carDownloadEntity.getCarFileName()), carDownloadEntity);
                }
                if (!TextUtils.isEmpty(carDownloadEntity.imgUrl)) {
                    String str = carDownloadEntity.imgUrl;
                    downloadFile(str, GiftConfig.INSTANCE.carAnimResRootPath, str, null);
                }
            }
        }
    }

    public void downloadFile(String str, final String str2, final String str3, final CarDownloadEntity carDownloadEntity) {
        if (!AppUtils.isDownloadService()) {
            return;
        }
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function<ResponseBody, File>() { // from class: com.tomatolive.library.download.CarDownLoaderImpl.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(ResponseBody responseBody) throws Exception {
                return FileUtils.saveFile(responseBody, str2, str3);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).delay(1L, TimeUnit.SECONDS).subscribe(new FileDownLoadObserver<File>() { // from class: com.tomatolive.library.download.CarDownLoaderImpl.1
            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onDownLoadFail(Throwable th) {
            }

            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onProgress(int i, long j) {
            }

            @Override // com.tomatolive.library.download.FileDownLoadObserver
            public void onDownLoadSuccess(File file) {
                CarDownloadEntity carDownloadEntity2 = carDownloadEntity;
                if (carDownloadEntity2 == null) {
                    return;
                }
                CarDownLoaderImpl.this.dealDownLoadItem(carDownloadEntity2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealDownLoadItem(CarDownloadEntity carDownloadEntity) {
        if (carDownloadEntity != null) {
            carDownloadEntity.animLocalPath = FileUtils.formatSVGAFileName(getGiftDirPath(carDownloadEntity.getCarFileName()));
            CarDownLoadManager.getInstance().updateDownloadItem(carDownloadEntity);
        }
    }

    private String getGiftDirPath(String str) {
        return GiftConfig.INSTANCE.carAnimResRootPath + str;
    }
}
