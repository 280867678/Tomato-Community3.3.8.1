package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.ReportTypeEntity;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.view.iview.IReportLiveView;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.ReportLivePresenter */
/* loaded from: classes3.dex */
public class ReportLivePresenter extends BasePresenter<IReportLiveView> {
    public ReportLivePresenter(Context context, IReportLiveView iReportLiveView) {
        super(context, iReportLiveView);
    }

    public void getReportTypeList() {
        getView().onReportTypeListSuccess(getTypeList());
    }

    public void submitReport(final String str, final String str2, final String str3, final String str4) {
        this.mApiService.uploadFile(AppUtils.getImgUploadUrl(), AppUtils.getImgUploadRequestBody(new File(AppUtils.getScreenshotPath()))).map(new ServerResultFunction<UploadFileEntity>() { // from class: com.tomatolive.library.ui.presenter.ReportLivePresenter.3
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).flatMap(new Function() { // from class: com.tomatolive.library.ui.presenter.-$$Lambda$ReportLivePresenter$5BcaFw2SG7ynnmNom6B6JuSID4g
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return ReportLivePresenter.this.lambda$submitReport$0$ReportLivePresenter(str, str2, str3, str4, (UploadFileEntity) obj);
            }
        }).onErrorResumeNext(new HttpResultFunction()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.ReportLivePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str5) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((IReportLiveView) ReportLivePresenter.this.getView()).onReportSuccess();
            }
        }, true));
    }

    public /* synthetic */ ObservableSource lambda$submitReport$0$ReportLivePresenter(String str, String str2, String str3, String str4, UploadFileEntity uploadFileEntity) throws Exception {
        return this.mApiService.getSaveReportService(new RequestParams().getReportLiveParams(str, str2, str3, uploadFileEntity.getFilename(), str4)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.ReportLivePresenter.2
        });
    }

    private List<ReportTypeEntity> getTypeList() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {getContext().getString(R$string.fq_report_live_radio_1), getContext().getString(R$string.fq_report_live_radio_2), getContext().getString(R$string.fq_report_live_radio_3), getContext().getString(R$string.fq_report_live_radio_4), getContext().getString(R$string.fq_report_live_radio_5), getContext().getString(R$string.fq_report_live_radio_6)};
        String[] strArr2 = {"1", "2", "3", "4", "5", "6"};
        for (int i = 0; i < strArr.length; i++) {
            ReportTypeEntity reportTypeEntity = new ReportTypeEntity();
            reportTypeEntity.desc = strArr[i];
            reportTypeEntity.code = strArr2[i];
            arrayList.add(reportTypeEntity);
        }
        return arrayList;
    }
}
