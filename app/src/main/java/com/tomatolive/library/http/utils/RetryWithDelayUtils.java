package com.tomatolive.library.http.utils;

import com.tomatolive.library.http.exception.ApiException;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class RetryWithDelayUtils implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int maxRetries;
    private int retryCount = 0;
    private int retryDelaySecond;

    public RetryWithDelayUtils(int i, int i2) {
        this.maxRetries = i;
        this.retryDelaySecond = i2;
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply  reason: avoid collision after fix types in other method */
    public Observable<?> mo6755apply(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Function() { // from class: com.tomatolive.library.http.utils.-$$Lambda$RetryWithDelayUtils$ecUUqfLnPdL5VCiqJxVtqvT0HD8
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return RetryWithDelayUtils.this.lambda$apply$0$RetryWithDelayUtils((Throwable) obj);
            }
        });
    }

    public /* synthetic */ ObservableSource lambda$apply$0$RetryWithDelayUtils(Throwable th) throws Exception {
        int code;
        if ((th instanceof ApiException) && ((code = ((ApiException) th).getCode()) == 101001 || code == 200023 || code == 200163 || code == 300004 || code == 300006 || code == 200164 || code == 200165 || code == 200166 || code == 200171 || code == 1100000 || code == 200169)) {
            return Observable.error(th);
        }
        int i = this.retryCount + 1;
        this.retryCount = i;
        if (i <= this.maxRetries) {
            return Observable.timer(this.retryDelaySecond, TimeUnit.SECONDS);
        }
        return Observable.error(th);
    }
}
