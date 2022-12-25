package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.view.iview.IRankingTabView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.RankingTabPresenter */
/* loaded from: classes3.dex */
public class RankingTabPresenter extends BasePresenter<IRankingTabView> {
    public RankingTabPresenter(Context context, IRankingTabView iRankingTabView) {
        super(context, iRankingTabView);
    }

    public void getRankConfig(StateView stateView) {
        this.mApiService.getIndexRankConfigService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<String>>() { // from class: com.tomatolive.library.ui.presenter.RankingTabPresenter.3
        }).flatMap(new Function<List<String>, ObservableSource<List<LabelEntity>>>() { // from class: com.tomatolive.library.ui.presenter.RankingTabPresenter.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<List<LabelEntity>> mo6755apply(List<String> list) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean isShowAllRanking = RankingTabPresenter.this.isShowAllRanking(list);
                for (String str : list) {
                    char c = 65535;
                    int hashCode = str.hashCode();
                    if (hashCode != 99228) {
                        if (hashCode != 3645428) {
                            if (hashCode == 104080000 && str.equals(ConstantUtils.TOP_MONTH)) {
                                c = 2;
                            }
                        } else if (str.equals(ConstantUtils.TOP_WEEK)) {
                            c = 1;
                        }
                    } else if (str.equals(ConstantUtils.TOP_DAY)) {
                        c = 0;
                    }
                    if (c == 0) {
                        arrayList.add(new LabelEntity(ConstantUtils.TOP_DAY, RankingTabPresenter.this.getContext().getString(R$string.fq_top_day), isShowAllRanking));
                    } else if (c == 1) {
                        arrayList.add(new LabelEntity(ConstantUtils.TOP_WEEK, RankingTabPresenter.this.getContext().getString(R$string.fq_top_week), isShowAllRanking));
                    } else if (c == 2) {
                        arrayList.add(new LabelEntity(ConstantUtils.TOP_MONTH, RankingTabPresenter.this.getContext().getString(R$string.fq_top_month), isShowAllRanking));
                    }
                }
                return Observable.just(arrayList);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.RankingTabPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LabelEntity> list) {
                ((IRankingTabView) RankingTabPresenter.this.getView()).onRankConfigSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IRankingTabView) RankingTabPresenter.this.getView()).onRankConfigFail();
            }
        }, stateView, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowAllRanking(List<String> list) {
        boolean z = false;
        if (list != null && !list.isEmpty()) {
            for (String str : list) {
                if (TextUtils.equals(str, "all")) {
                    z = true;
                }
            }
        }
        return z;
    }
}
