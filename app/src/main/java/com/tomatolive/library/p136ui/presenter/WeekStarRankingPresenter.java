package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.model.WeekStarRankingEntity;
import com.tomatolive.library.p136ui.view.iview.IWeekStarRankingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.WeekStarRankingPresenter */
/* loaded from: classes3.dex */
public class WeekStarRankingPresenter extends BasePresenter<IWeekStarRankingView> {
    public WeekStarRankingPresenter(Context context, IWeekStarRankingView iWeekStarRankingView) {
        super(context, iWeekStarRankingView);
    }

    public void getDataList(StateView stateView, boolean z, final boolean z2) {
        Observable.zip(this.mApiService.getStarGifListService(new RequestParams().getAppIdParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingPresenter.2
        }).onErrorResumeNext(new HttpResultFunction()), this.mApiService.getLastStarGiftAnchorListService(new RequestParams().getAppIdParams()).map(new ServerResultFunction<List<WeekStarAnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingPresenter.1
        }).onErrorResumeNext(new HttpResultFunction()), new BiFunction<List<GiftDownloadItemEntity>, List<WeekStarAnchorEntity>, List<WeekStarRankingEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingPresenter.4
            @Override // io.reactivex.functions.BiFunction
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public List<WeekStarRankingEntity> mo6745apply(List<GiftDownloadItemEntity> list, List<WeekStarAnchorEntity> list2) throws Exception {
                return WeekStarRankingPresenter.this.getResultList(list, list2);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(getContext(), new ResultCallBack<List<WeekStarRankingEntity>>() { // from class: com.tomatolive.library.ui.presenter.WeekStarRankingPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<WeekStarRankingEntity> list) {
                ((IWeekStarRankingView) WeekStarRankingPresenter.this.getView()).onDataListSuccess(list, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IWeekStarRankingView) WeekStarRankingPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }

    private List<MenuEntity> getAnchorRewardList() {
        ArrayList arrayList = new ArrayList();
        int[] iArr = {R$drawable.fq_ic_week_star_rank_anchor_reward_1, R$drawable.fq_ic_week_star_rank_anchor_reward_2, R$drawable.fq_ic_week_star_rank_anchor_reward_3, R$drawable.fq_ic_week_star_rank_anchor_reward_4};
        String[] stringArray = getContext().getResources().getStringArray(R$array.fq_week_star_anchor_reward);
        for (int i = 0; i < iArr.length; i++) {
            arrayList.add(new MenuEntity(stringArray[i], iArr[i]));
        }
        return arrayList;
    }

    private List<MenuEntity> getUserRewardList() {
        ArrayList arrayList = new ArrayList();
        int[] iArr = {R$drawable.fq_ic_week_star_rank_user_reward_1, R$drawable.fq_ic_week_star_rank_user_reward_2, R$drawable.fq_ic_week_star_rank_user_reward_3};
        String[] stringArray = getContext().getResources().getStringArray(R$array.fq_week_star_user_reward);
        for (int i = 0; i < iArr.length; i++) {
            arrayList.add(new MenuEntity(stringArray[i], iArr[i]));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<WeekStarRankingEntity> getResultList(List<GiftDownloadItemEntity> list, List<WeekStarAnchorEntity> list2) {
        ArrayList arrayList = new ArrayList();
        WeekStarRankingEntity weekStarRankingEntity = new WeekStarRankingEntity();
        weekStarRankingEntity.itemType = 1;
        weekStarRankingEntity.shineList = formatLastAnchorList(list2);
        arrayList.add(weekStarRankingEntity);
        WeekStarRankingEntity weekStarRankingEntity2 = new WeekStarRankingEntity();
        weekStarRankingEntity2.itemType = 2;
        weekStarRankingEntity2.giftLabelList = AppUtils.formatWeekStarGiftList(getContext(), list);
        arrayList.add(weekStarRankingEntity2);
        WeekStarRankingEntity weekStarRankingEntity3 = new WeekStarRankingEntity();
        weekStarRankingEntity3.itemType = 3;
        weekStarRankingEntity3.anchorRewardList = getAnchorRewardList();
        arrayList.add(weekStarRankingEntity3);
        WeekStarRankingEntity weekStarRankingEntity4 = new WeekStarRankingEntity();
        weekStarRankingEntity4.itemType = 4;
        weekStarRankingEntity4.userRewardList = getUserRewardList();
        arrayList.add(weekStarRankingEntity4);
        return arrayList;
    }

    private List<WeekStarAnchorEntity> formatLastAnchorList(List<WeekStarAnchorEntity> list) {
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < 5; i++) {
                WeekStarAnchorEntity weekStarAnchorEntity = new WeekStarAnchorEntity();
                weekStarAnchorEntity.anchorName = getContext().getString(R$string.fq_week_star_no_one_ranking);
                weekStarAnchorEntity.giftName = getContext().getString(R$string.fq_week_star_unspecified);
                weekStarAnchorEntity.avatar = "";
                weekStarAnchorEntity.imgurl = "";
                arrayList.add(weekStarAnchorEntity);
            }
            return arrayList;
        }
        return list;
    }
}
