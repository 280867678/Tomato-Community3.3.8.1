package com.one.tomato.mvp.base.okhttp.download;

import com.one.tomato.mvp.base.okhttp.interceptor.ProgressInterceptor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public class DownLoadManager {
    private static DownLoadManager instance;
    private static Retrofit retrofit;
    private Map<String, DownLoadSubscriber> subscriberMap = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public interface ApiService {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Url String str);
    }

    private DownLoadManager() {
        buildNetWork();
    }

    public static DownLoadManager getInstance() {
        if (instance == null) {
            instance = new DownLoadManager();
        }
        return instance;
    }

    public void load(String str, final ProgressCallBack progressCallBack) {
        ((ApiService) retrofit.create(ApiService.class)).download(str).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).doOnNext(new Consumer<ResponseBody>(this) { // from class: com.one.tomato.mvp.base.okhttp.download.DownLoadManager.1
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                progressCallBack.saveFile(responseBody);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DownLoadSubscriber(progressCallBack));
    }

    private void buildNetWork() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        builder.connectTimeout(120L, TimeUnit.SECONDS);
        OkHttpClient build = builder.build();
        Retrofit.Builder builder2 = new Retrofit.Builder();
        builder2.client(build);
        builder2.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder2.baseUrl("http://www.baidu.com");
        retrofit = builder2.build();
    }

    public void loadWithCancelable(String str, final ProgressCallBack progressCallBack) {
        DownLoadSubscriber downLoadSubscriber = new DownLoadSubscriber(progressCallBack);
        this.subscriberMap.put(str, downLoadSubscriber);
        ((ApiService) retrofit.create(ApiService.class)).download(str).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).doOnNext(new Consumer<ResponseBody>(this) { // from class: com.one.tomato.mvp.base.okhttp.download.DownLoadManager.2
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                progressCallBack.saveFile(responseBody);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(downLoadSubscriber);
    }

    public void cancelLoad(String str) {
        DownLoadSubscriber downLoadSubscriber = this.subscriberMap.get(str);
        if (downLoadSubscriber != null) {
            downLoadSubscriber.dispose();
        }
    }
}
