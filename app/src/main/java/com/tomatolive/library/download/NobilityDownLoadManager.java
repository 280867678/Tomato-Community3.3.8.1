package com.tomatolive.library.download;

import android.app.Dialog;
import android.text.TextUtils;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.http.utils.RetryWithDelayUtils;
import com.tomatolive.library.model.NobilityDownLoadEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.MD5Utils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.List;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class NobilityDownLoadManager {
    private final String FILE_FORMAT;

    private NobilityDownLoadManager() {
        this.FILE_FORMAT = ".svga";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final NobilityDownLoadManager INSTANCE = new NobilityDownLoadManager();

        private SingletonHolder() {
        }
    }

    public static NobilityDownLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void updateAnimOnlineAllRes() {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getNobilitySourceListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<NobilityDownLoadEntity>>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.3
        }).onErrorResumeNext(new HttpResultFunction<List<NobilityDownLoadEntity>>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.2
        }).retryWhen(new RetryWithDelayUtils(3, 5)).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<List<NobilityDownLoadEntity>>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<NobilityDownLoadEntity> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }
                for (NobilityDownLoadEntity nobilityDownLoadEntity : list) {
                    if (NobilityDownLoadManager.this.isDownloadFile(nobilityDownLoadEntity.type) && !TextUtils.isEmpty(nobilityDownLoadEntity.animalUrl)) {
                        NobilityDownLoadManager nobilityDownLoadManager = NobilityDownLoadManager.this;
                        nobilityDownLoadManager.downloadFile(nobilityDownLoadEntity.animalUrl, GiftConfig.INSTANCE.nobilityAnimResRootPath, nobilityDownLoadManager.getDESEncryptFileName(nobilityDownLoadEntity.type));
                    }
                }
            }
        });
    }

    public void updateAnimOnlineSingleRes(final int i) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getNobilitySourceListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<NobilityDownLoadEntity>>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.5
        }).flatMap(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$NobilityDownLoadManager$Vo3zCqSOqzEzlWyRkaaXoGcGcZA
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return NobilityDownLoadManager.this.lambda$updateAnimOnlineSingleRes$0$NobilityDownLoadManager(i, (List) obj);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<NobilityDownLoadEntity>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(NobilityDownLoadEntity nobilityDownLoadEntity) {
                if (nobilityDownLoadEntity != null && NobilityDownLoadManager.this.isDownloadFile(nobilityDownLoadEntity.type) && nobilityDownLoadEntity.type == i && !TextUtils.isEmpty(nobilityDownLoadEntity.animalUrl)) {
                    NobilityDownLoadManager nobilityDownLoadManager = NobilityDownLoadManager.this;
                    nobilityDownLoadManager.downloadFile(nobilityDownLoadEntity.animalUrl, GiftConfig.INSTANCE.nobilityAnimResRootPath, nobilityDownLoadManager.getDESEncryptFileName(nobilityDownLoadEntity.type));
                }
            }
        });
    }

    public /* synthetic */ ObservableSource lambda$updateAnimOnlineSingleRes$0$NobilityDownLoadManager(int i, List list) throws Exception {
        return Observable.just(getNobilityItemByType(list, i));
    }

    public void updateAnimOnlineSingleRes(final int i, final Dialog dialog, final ResultCallBack<String> resultCallBack) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getNobilitySourceListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<NobilityDownLoadEntity>>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.7
        }).flatMap(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$NobilityDownLoadManager$V_puY5QyUrnhSYxsRYJE_g1TIWo
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return NobilityDownLoadManager.this.lambda$updateAnimOnlineSingleRes$1$NobilityDownLoadManager(i, (List) obj);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<NobilityDownLoadEntity>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.6
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                Dialog dialog2 = dialog;
                if (dialog2 != null) {
                    dialog2.show();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(NobilityDownLoadEntity nobilityDownLoadEntity) {
                if (nobilityDownLoadEntity == null) {
                    return;
                }
                if (!NobilityDownLoadManager.this.isDownloadFile(nobilityDownLoadEntity.type)) {
                    ResultCallBack resultCallBack2 = resultCallBack;
                    if (resultCallBack2 == null) {
                        return;
                    }
                    resultCallBack2.onError(0, "");
                } else if (nobilityDownLoadEntity.type == i && !TextUtils.isEmpty(nobilityDownLoadEntity.animalUrl)) {
                    NobilityDownLoadManager nobilityDownLoadManager = NobilityDownLoadManager.this;
                    nobilityDownLoadManager.downloadFile(nobilityDownLoadEntity.animalUrl, GiftConfig.INSTANCE.nobilityAnimResRootPath, nobilityDownLoadManager.getDESEncryptFileName(nobilityDownLoadEntity.type), resultCallBack);
                } else {
                    ResultCallBack resultCallBack3 = resultCallBack;
                    if (resultCallBack3 == null) {
                        return;
                    }
                    resultCallBack3.onError(0, "");
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                super.onComplete();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(0, th.getMessage());
                }
            }
        });
    }

    public /* synthetic */ ObservableSource lambda$updateAnimOnlineSingleRes$1$NobilityDownLoadManager(int i, List list) throws Exception {
        return Observable.just(getNobilityItemByType(list, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadFile(String str, final String str2, final String str3) {
        if (!AppUtils.isDownloadService()) {
            return;
        }
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$NobilityDownLoadManager$bEAHcOaYfzZgJ6n_gnqDYkRMdik
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                File saveFile;
                saveFile = FileUtils.saveFile((ResponseBody) obj, str2, str3);
                return saveFile;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<File>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.8
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(File file) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadFile(String str, final String str2, final String str3, final ResultCallBack<String> resultCallBack) {
        if (!AppUtils.isDownloadService()) {
            return;
        }
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$NobilityDownLoadManager$DQf6qJ1gS3qfNZywmEU2YOGD2P4
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                File saveFile;
                saveFile = FileUtils.saveFile((ResponseBody) obj, str2, str3);
                return saveFile;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<File>() { // from class: com.tomatolive.library.download.NobilityDownLoadManager.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(File file) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                super.onComplete();
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess("");
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(0, th.getMessage());
                }
            }
        });
    }

    public String getDESEncryptFileName(int i) {
        return MD5Utils.getMd5(getNobilityName(i)) + ".svga";
    }

    public String getNobilityFilePath(int i) {
        return GiftConfig.INSTANCE.nobilityAnimResRootPath + getDESEncryptFileName(i);
    }

    private String getNobilityName(int i) {
        return "nobilityName_" + i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDownloadFile(int i) {
        if (i == 1 || i == 2) {
            return false;
        }
        return !FileUtils.isFileExists(getNobilityFilePath(i));
    }

    private NobilityDownLoadEntity getNobilityItemByType(List<NobilityDownLoadEntity> list, int i) {
        NobilityDownLoadEntity nobilityDownLoadEntity = new NobilityDownLoadEntity();
        for (NobilityDownLoadEntity nobilityDownLoadEntity2 : list) {
            if (nobilityDownLoadEntity2.type == i) {
                return nobilityDownLoadEntity2;
            }
        }
        return nobilityDownLoadEntity;
    }
}
