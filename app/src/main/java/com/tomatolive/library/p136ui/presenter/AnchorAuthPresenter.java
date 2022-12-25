package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.CountryCodeEntity;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.view.iview.IAnchorAuthView;
import com.tomatolive.library.utils.AppUtils;
import java.io.File;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.AnchorAuthPresenter */
/* loaded from: classes3.dex */
public class AnchorAuthPresenter extends BasePresenter<IAnchorAuthView> {
    public AnchorAuthPresenter(Context context, IAnchorAuthView iAnchorAuthView) {
        super(context, iAnchorAuthView);
    }

    public void onAnchorAuth(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        addMapSubscription(this.mApiService.getSubmitAnchorAuthService(new RequestParams().getAnchorAuthParams(str, str2, str3, str4, str5, str6, str7)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onAuthSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str8) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onResultError(i);
            }
        }, true));
    }

    public void onSendPhoneCode(String str, String str2) {
        addMapSubscription(this.mApiService.getPhoneCodeService(new RequestParams().getSendPhoneCodeParams(str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onSendPhoneCodeSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onSendPhoneCodeFail();
            }
        }, true));
    }

    public void onCountryCode() {
        addMapSubscription(this.mApiService.getCountryCodeService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<CountryCodeEntity>>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<CountryCodeEntity> list) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onCountryPhoneCodeSuccess(list);
            }
        }, true));
    }

    public void onUpload(String str, final boolean z) {
        addMapSubscription(this.mApiService.uploadFile(AppUtils.getImgUploadUrl(), AppUtils.getImgUploadRequestBody(new File(str))), new HttpRxObserver(getContext(), new ResultCallBack<UploadFileEntity>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthPresenter.4
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UploadFileEntity uploadFileEntity) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onUploadSuc(uploadFileEntity, z);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IAnchorAuthView) AnchorAuthPresenter.this.getView()).onUploadFail(z);
            }
        }));
    }
}
