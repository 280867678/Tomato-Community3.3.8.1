package com.one.tomato.mvp.base.okhttp.download;

import android.util.Log;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public abstract class ProgressCallBack<T> {
    private String destFileDir;
    private String destFileName;
    private Disposable mSubscription;

    public void onCompleted() {
    }

    public abstract void onError(Throwable th);

    public void onStart() {
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long j, long j2);

    public ProgressCallBack(String str, String str2) {
        this.destFileDir = str;
        this.destFileName = str2;
        subscribeLoadProgress();
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00ab A[Catch: IOException -> 0x00a7, TRY_LEAVE, TryCatch #8 {IOException -> 0x00a7, blocks: (B:67:0x00a3, B:59:0x00ab), top: B:66:0x00a3 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void saveFile(ResponseBody responseBody) {
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        byte[] bArr = new byte[2048];
        InputStream inputStream2 = null;
        r2 = null;
        inputStream2 = null;
        inputStream2 = null;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStream = responseBody.byteStream();
            } catch (Throwable th) {
                th = th;
                inputStream = inputStream2;
            }
        } catch (FileNotFoundException e) {
            e = e;
            fileOutputStream = null;
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
        try {
            File file = new File(this.destFileDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, this.destFileName);
            fileOutputStream = new FileOutputStream(file2);
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                } catch (FileNotFoundException e3) {
                    e = e3;
                    inputStream2 = inputStream;
                    e.printStackTrace();
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e4) {
                            e = e4;
                            Log.e("saveFile", e.getMessage());
                            unsubscribe();
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    unsubscribe();
                } catch (IOException e5) {
                    e = e5;
                    inputStream2 = inputStream;
                    e.printStackTrace();
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e6) {
                            e = e6;
                            Log.e("saveFile", e.getMessage());
                            unsubscribe();
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    unsubscribe();
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream2 = fileOutputStream;
                    if (inputStream != null) {
                    }
                    if (fileOutputStream2 != null) {
                    }
                    unsubscribe();
                    throw th;
                }
            }
            fileOutputStream.flush();
            if (file2.length() != responseBody.contentLength()) {
                onError(new Throwable("download file is not complete"));
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e7) {
                    e = e7;
                    Log.e("saveFile", e.getMessage());
                    unsubscribe();
                }
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e8) {
            e = e8;
            fileOutputStream = null;
        } catch (IOException e9) {
            e = e9;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e10) {
                    Log.e("saveFile", e10.getMessage());
                    unsubscribe();
                    throw th;
                }
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            unsubscribe();
            throw th;
        }
        unsubscribe();
    }

    public void subscribeLoadProgress() {
        this.mSubscription = RxBus.getDefault().toObservable(DownLoadStateBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DownLoadStateBean>() { // from class: com.one.tomato.mvp.base.okhttp.download.ProgressCallBack.1
            @Override // io.reactivex.functions.Consumer
            public void accept(DownLoadStateBean downLoadStateBean) throws Exception {
                ProgressCallBack.this.progress(downLoadStateBean.getBytesLoaded(), downLoadStateBean.getTotal());
            }
        });
        RxSubscriptions.add(this.mSubscription);
    }

    public void unsubscribe() {
        RxSubscriptions.remove(this.mSubscription);
    }
}
