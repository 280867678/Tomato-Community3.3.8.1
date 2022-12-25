package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter */
/* loaded from: classes3.dex */
public class WeekStarRankingAnchorPresenter extends BasePresenter<IWeekStarRankingAnchorView> {
    private int defListCount = 5;

    public WeekStarRankingAnchorPresenter(Context context, IWeekStarRankingAnchorView iWeekStarRankingAnchorView) {
        super(context, iWeekStarRankingAnchorView);
    }

    public void getDataList(final boolean z, boolean z2, String str, final LinearLayout linearLayout, final TextView textView, final TextView textView2, final boolean z3, final boolean z4) {
        Observable<HttpResultModel<List<WeekStarAnchorEntity>>> starGiftAnchorListService;
        if (z2) {
            starGiftAnchorListService = this.mApiService.getStarGiftUserListService(new RequestParams().getStarGiftAnchorListParams(str));
        } else {
            starGiftAnchorListService = this.mApiService.getStarGiftAnchorListService(new RequestParams().getStarGiftAnchorListParams(str));
        }
        starGiftAnchorListService.map(new ServerResultFunction<List<WeekStarAnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.3
        }).flatMap(new Function<List<WeekStarAnchorEntity>, ObservableSource<List<WeekStarAnchorEntity>>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<List<WeekStarAnchorEntity>> mo6755apply(List<WeekStarAnchorEntity> list) throws Exception {
                return Observable.just(WeekStarRankingAnchorPresenter.this.formatDataList(z, list));
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<WeekStarAnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (z4) {
                    textView.setVisibility(0);
                    textView2.setVisibility(4);
                    linearLayout.setVisibility(4);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<WeekStarAnchorEntity> list) {
                textView.setVisibility(4);
                textView2.setVisibility(4);
                linearLayout.setVisibility(0);
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onDataListSuccess(list, z3);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                super.onComplete();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onDataListFail(z3);
                textView.setVisibility(4);
                textView2.setVisibility(0);
                linearLayout.setVisibility(4);
            }
        });
    }

    public void getUserRanking(boolean z, String str, String str2) {
        Observable<HttpResultModel<WeekStarAnchorEntity>> starGiftAnchorRankService;
        if (z) {
            starGiftAnchorRankService = this.mApiService.getStarGiftUserRankService(new RequestParams().getStarGiftUserRankParams(str));
        } else {
            starGiftAnchorRankService = this.mApiService.getStarGiftAnchorRankService(new RequestParams().getStarGiftAnchorRankParams(str, str2));
        }
        starGiftAnchorRankService.map(new ServerResultFunction<WeekStarAnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.5
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<WeekStarAnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(WeekStarAnchorEntity weekStarAnchorEntity) {
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onUserRankingSuccess(weekStarAnchorEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onUserRankingFail();
            }
        });
    }

    public void getDefaultHomeStarRanking(final LinearLayout linearLayout, final TextView textView, final TextView textView2, final boolean z, final boolean z2) {
        Observable.just(getDefaultList(this.defListCount)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<WeekStarAnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingAnchorPresenter.6
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (z2) {
                    textView.setVisibility(0);
                    textView2.setVisibility(4);
                    linearLayout.setVisibility(4);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<WeekStarAnchorEntity> list) {
                textView.setVisibility(4);
                textView2.setVisibility(4);
                linearLayout.setVisibility(0);
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onDataListSuccess(list, z);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                super.onComplete();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ((IWeekStarRankingAnchorView) WeekStarRankingAnchorPresenter.this.getView()).onDataListFail(z);
                textView.setVisibility(4);
                textView2.setVisibility(0);
                linearLayout.setVisibility(4);
            }
        });
    }

    private List<WeekStarAnchorEntity> getDefaultList(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            WeekStarAnchorEntity weekStarAnchorEntity = new WeekStarAnchorEntity();
            weekStarAnchorEntity.anchorName = getContext().getString(R$string.fq_text_list_empty_waiting);
            weekStarAnchorEntity.name = getContext().getString(R$string.fq_text_list_empty_waiting);
            weekStarAnchorEntity.userStarGiftNum = "0";
            weekStarAnchorEntity.anchorStarGiftNum = "0";
            weekStarAnchorEntity.giftNum = "0";
            arrayList.add(weekStarAnchorEntity);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<WeekStarAnchorEntity> formatDataList(boolean z, List<WeekStarAnchorEntity> list) {
        if (list == null || list.isEmpty()) {
            return getDefaultList(this.defListCount);
        }
        int size = list.size();
        int i = this.defListCount;
        if (size < i) {
            list.addAll(getDefaultList(i - list.size()));
            return list;
        }
        int size2 = list.size();
        int i2 = this.defListCount;
        return (size2 <= i2 || z) ? list : list.subList(0, i2);
    }
}
