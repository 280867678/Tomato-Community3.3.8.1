package com.tomatolive.library.http.function;

import com.tomatolive.library.http.HttpResultModel;
import com.tomatolive.library.http.exception.ServerException;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class ServerResultFunction<T> implements Function<HttpResultModel<T>, T> {
    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public /* bridge */ /* synthetic */ Object mo6755apply(Object obj) throws Exception {
        return apply((HttpResultModel) ((HttpResultModel) obj));
    }

    public T apply(HttpResultModel<T> httpResultModel) {
        if (!httpResultModel.isSuccess()) {
            throw new ServerException(httpResultModel.getCode(), httpResultModel.getMessage());
        }
        return httpResultModel.getData();
    }
}
