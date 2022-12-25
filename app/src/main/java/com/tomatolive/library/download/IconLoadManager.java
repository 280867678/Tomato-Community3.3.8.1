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
public class IconLoadManager {
    public final String destDir;

    public void dealResLoad(SysParamsInfoEntity sysParamsInfoEntity) {
    }

    private IconLoadManager() {
        this.destDir = GiftConfig.INSTANCE.resIconLoadRootPath;
    }

    /* loaded from: classes3.dex */
    private static class SingletonHolder {
        private static final IconLoadManager INSTANCE = new IconLoadManager();

        private SingletonHolder() {
        }
    }

    public static IconLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
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
        DownLoadRetrofit.getInstance().getApiService().downLoadFile(GlideUtils.formatDownUrl(str)).map(new Function() { // from class: com.tomatolive.library.download.-$$Lambda$IconLoadManager$z3MdkLdiEv3zUuCto0GbCSv0oOI
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return IconLoadManager.this.lambda$startDownloadRes$0$IconLoadManager(stringBuffer2, (ResponseBody) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<File>() { // from class: com.tomatolive.library.download.IconLoadManager.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(File file) {
                IconLoadManager.this.dealFile(file);
            }
        });
    }

    public /* synthetic */ File lambda$startDownloadRes$0$IconLoadManager(String str, ResponseBody responseBody) throws Exception {
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
            Observable.timer(5L, TimeUnit.SECONDS).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.download.IconLoadManager.2
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Long l) {
                    com.tomatolive.library.utils.FileUtils.deleteFile(str);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getDownloadFile(String str) {
        return com.tomatolive.library.utils.FileUtils.getFileByPath(this.destDir + str);
    }
}
