package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.view.iview.ISubmitAppealView;
import com.tomatolive.library.utils.AppUtils;
import java.io.File;

/* renamed from: com.tomatolive.library.ui.presenter.SubmitAppealPresenter */
/* loaded from: classes3.dex */
public class SubmitAppealPresenter extends BasePresenter<ISubmitAppealView> {
    public SubmitAppealPresenter(Context context, ISubmitAppealView iSubmitAppealView) {
        super(context, iSubmitAppealView);
    }

    public void submitAppeal(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        addMapSubscription(this.mApiService.submitAppealService(new RequestParams().getSubmitAppealParams(str, str2, str3, str4, str5, str6)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.SubmitAppealPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((ISubmitAppealView) SubmitAppealPresenter.this.getView()).onSubmitAppealSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str7) {
                ((ISubmitAppealView) SubmitAppealPresenter.this.getView()).onSubmitAppealFailure(i, str7);
            }
        }, z));
    }

    public void onUpload(String str, final int i) {
        addMapSubscription(this.mApiService.uploadFile(AppUtils.getImgUploadUrl(), AppUtils.getImgUploadRequestBody(new File(str))), new HttpRxObserver(getContext(), new ResultCallBack<UploadFileEntity>() { // from class: com.tomatolive.library.ui.presenter.SubmitAppealPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UploadFileEntity uploadFileEntity) {
                ((ISubmitAppealView) SubmitAppealPresenter.this.getView()).onUploadSuc(uploadFileEntity, i);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((ISubmitAppealView) SubmitAppealPresenter.this.getView()).onUploadFail();
            }
        }));
    }
}
