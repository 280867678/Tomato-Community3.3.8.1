package com.one.tomato.mvp.p080ui.download.presenter;

import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.p080ui.download.impl.IDownloadContact;
import com.one.tomato.mvp.p080ui.download.impl.IDownloadContact$IDownloadView;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DownloadPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.download.presenter.DownloadPresenter */
/* loaded from: classes3.dex */
public final class DownloadPresenter extends MvpBasePresenter<IDownloadContact$IDownloadView> implements IDownloadContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void findDataFromDB() {
        Observable.create(new ObservableOnSubscribe<ArrayList<VideoDownload>>() { // from class: com.one.tomato.mvp.ui.download.presenter.DownloadPresenter$findDataFromDB$1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<ArrayList<VideoDownload>> emitter) {
                Intrinsics.checkParameterIsNotNull(emitter, "emitter");
                M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(m3U8DownloadManager, "M3U8DownloadManager.getInstance()");
                ArrayList<VideoDownload> videoBeanFormFileList = m3U8DownloadManager.getVideoBeanFormFileList();
                Iterator<VideoDownload> it2 = videoBeanFormFileList.iterator();
                while (it2.hasNext()) {
                    VideoDownload item = it2.next();
                    Intrinsics.checkExpressionValueIsNotNull(item, "item");
                    if (item.getState() != 3) {
                        item.setState(0);
                    }
                }
                emitter.onNext(videoBeanFormFileList);
                emitter.onComplete();
            }
        }).timeout(30L, TimeUnit.SECONDS).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new Observer<ArrayList<VideoDownload>>() { // from class: com.one.tomato.mvp.ui.download.presenter.DownloadPresenter$findDataFromDB$2
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LogUtil.m3788d("yan");
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<VideoDownload> t) {
                IDownloadContact$IDownloadView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = DownloadPresenter.this.getMView();
                if (mView != null) {
                    mView.setDataFromDB(t);
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                IDownloadContact$IDownloadView mView;
                Intrinsics.checkParameterIsNotNull(e, "e");
                mView = DownloadPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(e.getMessage());
                }
                Thread.currentThread();
            }
        });
    }

    public void findCachedSize() {
        Observable.create(new ObservableOnSubscribe<String[]>() { // from class: com.one.tomato.mvp.ui.download.presenter.DownloadPresenter$findCachedSize$1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<String[]> emitter) {
                Intrinsics.checkParameterIsNotNull(emitter, "emitter");
                M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(m3U8DownloadManager, "M3U8DownloadManager.getInstance()");
                Iterator<VideoDownload> it2 = m3U8DownloadManager.getVideoBeanFormFileList().iterator();
                long j = 0;
                while (it2.hasNext()) {
                    VideoDownload bean = it2.next();
                    Intrinsics.checkExpressionValueIsNotNull(bean, "bean");
                    j += FileUtil.getFolderSize(new File(MUtils.generateM3U8Folder(bean.getUrl(), true)));
                }
                String cachedSize = FileUtil.formatFileSize(j);
                String sdcardFree = FileUtil.formatFileSize(FileUtil.getFreeSpaceOfExternalStorage());
                Intrinsics.checkExpressionValueIsNotNull(cachedSize, "cachedSize");
                Intrinsics.checkExpressionValueIsNotNull(sdcardFree, "sdcardFree");
                emitter.onNext(new String[]{cachedSize, sdcardFree});
                emitter.onComplete();
            }
        }).delay(1L, TimeUnit.SECONDS).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new Observer<String[]>() { // from class: com.one.tomato.mvp.ui.download.presenter.DownloadPresenter$findCachedSize$2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public void onNext(String[] t) {
                IDownloadContact$IDownloadView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = DownloadPresenter.this.getMView();
                if (mView != null) {
                    mView.setCachedSize(t);
                }
            }
        });
    }
}
