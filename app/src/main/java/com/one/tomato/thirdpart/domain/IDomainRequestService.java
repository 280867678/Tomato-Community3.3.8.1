package com.one.tomato.thirdpart.domain;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.DomainJsonBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface IDomainRequestService {
    @GET
    Observable<String> requestBlog(@Url String str);

    @GET("/app/domain/listDomainWithWeight")
    Observable<BaseResponse<DomainJsonBean>> requestDomainList();
}
