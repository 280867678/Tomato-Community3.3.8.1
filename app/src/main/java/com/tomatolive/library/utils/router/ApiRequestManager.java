package com.tomatolive.library.utils.router;

import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.tomatolive.library.utils.router.callbacks.AnchorAuthCallBack;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes4.dex */
public class ApiRequestManager {
    private ApiRequestManager() {
    }

    /* loaded from: classes4.dex */
    private static class SingletonHolder {
        private static final ApiRequestManager INSTANCE = new ApiRequestManager();

        private SingletonHolder() {
        }
    }

    public static ApiRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void onAnchorAuth(final AnchorAuthCallBack anchorAuthCallBack) {
        ApiRetrofit.getInstance().getApiService().getAnchorAuthService(new RequestParams().getUserIdParams()).map(new ServerResultFunction<AnchorEntity>() { // from class: com.tomatolive.library.utils.router.ApiRequestManager.3
        }).onErrorResumeNext(new HttpResultFunction<AnchorEntity>() { // from class: com.tomatolive.library.utils.router.ApiRequestManager.2
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<AnchorEntity>() { // from class: com.tomatolive.library.utils.router.ApiRequestManager.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(AnchorEntity anchorEntity) {
                AnchorAuthCallBack anchorAuthCallBack2;
                if (anchorEntity == null || (anchorAuthCallBack2 = anchorAuthCallBack) == null) {
                    return;
                }
                anchorAuthCallBack2.onAnchorAuthCallBack(anchorEntity);
            }
        });
    }
}
