package com.one.tomato.mvp.p080ui.post.presenter;

import android.app.Activity;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.FocusHotPostListBean;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.entity.event.PostCollectOrThumbEvent;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.utils.CircleAllUtils;
import com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact;
import com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.p087ad.AdUtil;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.Maps;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;

/* compiled from: NewPostTabListPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter */
/* loaded from: classes3.dex */
public final class NewPostTabListPresenter extends MvpBasePresenter<IPostTabListContact$IPostTabListView> implements IPostTabListContact {
    private int adPageIndex;
    private AdUtil adUtil;
    private ArrayList<AdPage> listPages;
    private int insertLoopNum = 10;
    private int insertPageIndex = 1;
    private final HashSet<PostList> adSet = new HashSet<>();
    private final HashSet<PostList> viewedPostSet = new HashSet<>();
    private boolean hasRequestNotice = true;
    private int insertCircleLoopNum = 18;
    private int insertCircleIndex = 1;

    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
        IPostTabListContact$IPostTabListView mView = getMView();
        this.adUtil = new AdUtil((Activity) (mView != null ? mView.getContext() : null));
        this.listPages = DBUtil.getAdPage(C2516Ad.TYPE_LIST);
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        this.insertLoopNum = systemParam.getADFrequencyOnList();
    }

    public final void lookADUp(PostList postList) {
        if (postList == null || !postList.isAd() || this.adSet.contains(postList)) {
            return;
        }
        LogUtil.m3787d("yan", "瀏覽廣告上報----" + postList.getId());
        this.adSet.add(postList);
        DataUploadUtil.uploadAD(postList.getId(), 1);
    }

    public final void viewedPostUpload(PostList postList) {
        if (postList == null || postList.isAd() || this.viewedPostSet.contains(postList)) {
            return;
        }
        LogUtil.m3784i("推荐界面已浏览的帖子上报 ----" + postList.getId());
        this.viewedPostSet.add(postList);
        DataUploadUtil.uploadRecommendPostBrowse(postList.getId(), DBUtil.getMemberId());
    }

    public void insertPageIndex(int i) {
        this.insertPageIndex = i;
        this.adPageIndex = 0;
        this.insertCircleIndex = 1;
    }

    private final AdPage getAdPage() {
        ArrayList<AdPage> arrayList = this.listPages;
        AdPage adPage = null;
        if (arrayList != null) {
            if (arrayList != null && arrayList.isEmpty()) {
                return null;
            }
            ArrayList<AdPage> arrayList2 = this.listPages;
            AdPage adPage2 = arrayList2 != null ? arrayList2.get(0) : null;
            if ((adPage2 != null ? adPage2.getWeight() : 0) > 0) {
                ArrayList<AdPage> arrayList3 = this.listPages;
                if (arrayList3 == null) {
                    return null;
                }
                return arrayList3.get(AdUtil.getListIndexByWeight(arrayList3));
            }
            int i = this.adPageIndex;
            ArrayList<AdPage> arrayList4 = this.listPages;
            if (i >= (arrayList4 != null ? arrayList4.size() : 0)) {
                this.adPageIndex = 0;
            }
            LogUtil.m3785e("papa视频", "adPageIndex = " + this.adPageIndex);
            ArrayList<AdPage> arrayList5 = this.listPages;
            if (arrayList5 != null) {
                adPage = arrayList5.get(this.adPageIndex);
            }
            this.adPageIndex++;
            return adPage;
        }
        return null;
    }

    public void insertAd(int i) {
        Map<String, Object> mutableMapOf;
        PostList postList = AdUtil.getPostList(getAdPage());
        if (postList == null || i == 0) {
            return;
        }
        while (true) {
            int i2 = this.insertLoopNum;
            int i3 = this.insertPageIndex;
            int i4 = ((i2 * i3) + i3) - 1;
            if (i4 >= i + 1) {
                return;
            }
            IPostTabListContact$IPostTabListView mView = getMView();
            if (mView != null) {
                mutableMapOf = Maps.mutableMapOf(TuplesKt.m89to("num", Integer.valueOf(i4)), TuplesKt.m89to("post", postList));
                mView.handlerInsertAd(mutableMapOf);
            }
            this.insertPageIndex++;
        }
    }

    public final void insertCircle(int i) {
        int random;
        Map<String, Object> mutableMapOf;
        PostList postList = new PostList();
        if (i == 0) {
            return;
        }
        while (true) {
            int i2 = this.insertCircleLoopNum;
            int i3 = this.insertCircleIndex;
            int i4 = ((i2 * i3) + i3) - 1;
            if (i4 >= i + 1) {
                return;
            }
            random = _Ranges.random(new Ranges(0, 2), Random.Default);
            if (random == 1) {
                postList.setInsertFlag(2);
                postList.setId(-12);
                ArrayList<UpRecommentBean> pullRecommentUpInsertList = CircleAllUtils.INSTANCE.pullRecommentUpInsertList();
                if (pullRecommentUpInsertList.isEmpty()) {
                    return;
                }
                postList.setUpRecommentBeans(pullRecommentUpInsertList);
            } else if (random == 0) {
                postList.setInsertFlag(1);
                postList.setId(-11);
                ArrayList<CircleDiscoverListBean> pullRecommentCircleInsertPostList = CircleAllUtils.INSTANCE.pullRecommentCircleInsertPostList();
                if (pullRecommentCircleInsertPostList.isEmpty()) {
                    return;
                }
                pullRecommentCircleInsertPostList.add(new CircleDiscoverListBean(-100));
                postList.setCircleDiscoverListBeans(pullRecommentCircleInsertPostList);
            } else if (random == 2) {
                postList.setInsertFlag(5);
                postList.setId(-13);
                ArrayList<PostHotMessageBean> pullRecommentHotMessage = CircleAllUtils.INSTANCE.pullRecommentHotMessage();
                if (pullRecommentHotMessage == null || pullRecommentHotMessage.isEmpty()) {
                    return;
                }
                postList.setPostHotMessageBeans(pullRecommentHotMessage);
            }
            IPostTabListContact$IPostTabListView mView = getMView();
            if (mView != null) {
                mutableMapOf = Maps.mutableMapOf(TuplesKt.m89to("num", Integer.valueOf(i4)), TuplesKt.m89to("post", postList));
                mView.handlerInsertAd(mutableMapOf);
            }
            this.insertCircleIndex++;
        }
    }

    public void requestRecommendPostList(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        Observable<R> compose = ApiImplService.Companion.getApiImplService().getRecommendPost(paramsMap).compose(RxUtils.schedulersTransformer());
        IPostTabListContact$IPostTabListView mView = getMView();
        compose.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestRecommendPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView2;
                boolean z;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerPostListData(arrayList);
                }
                z = NewPostTabListPresenter.this.hasRequestNotice;
                if (z) {
                    NewPostTabListPresenter.this.requestMainNotify();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView2;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestFoucsPostList(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        Observable<BaseResponse<FocusHotPostListBean>> requestFoucsPostList = ApiImplService.Companion.getApiImplService().requestFoucsPostList(paramsMap);
        IPostTabListContact$IPostTabListView mView = getMView();
        requestFoucsPostList.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<FocusHotPostListBean>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestFoucsPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(FocusHotPostListBean focusHotPostListBean) {
                IPostTabListContact$IPostTabListView mView2;
                LogUtil.m3787d("yan", "t" + focusHotPostListBean);
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerPostListData(focusHotPostListBean != null ? focusHotPostListBean.getData() : null);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView2;
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestNoFoucsHotPostList(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        Observable<BaseResponse<FocusHotPostListBean>> requestFoucsPostList = ApiImplService.Companion.getApiImplService().requestFoucsPostList(paramsMap);
        IPostTabListContact$IPostTabListView mView = getMView();
        requestFoucsPostList.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<FocusHotPostListBean>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestNoFoucsHotPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(FocusHotPostListBean focusHotPostListBean) {
                IPostTabListContact$IPostTabListView mView2;
                LogUtil.m3787d("yan", "t" + focusHotPostListBean);
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerPostListData(focusHotPostListBean != null ? focusHotPostListBean.getHotList() : null);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView2;
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestImageVideoPostList(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        Observable<R> compose = ApiImplService.Companion.getApiImplService().getImageVideoPost(paramsMap).compose(RxUtils.schedulersTransformer());
        IPostTabListContact$IPostTabListView mView = getMView();
        compose.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestImageVideoPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView2;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView2;
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void startPostAd(PostList postList) {
        if (postList != null) {
            AdPage page = postList.getPage();
            AdUtil adUtil = this.adUtil;
            if (adUtil == null) {
                return;
            }
            int id = postList.getId();
            Intrinsics.checkExpressionValueIsNotNull(page, "page");
            adUtil.clickAd(id, page.getAdType(), page.getAdJumpModule(), page.getAdJumpDetail(), page.getOpenType(), page.getAdLink());
        }
    }

    public final void thumbUp(final PostList postList) {
        if (postList != null) {
            Observable<R> compose = ApiImplService.Companion.getApiImplService().faverPost(DBUtil.getMemberId(), postList.getId()).compose(RxUtils.schedulersTransformer());
            IPostTabListContact$IPostTabListView mView = getMView();
            compose.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$thumbUp$$inlined$let$lambda$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    PostCollectOrThumbEvent postCollectOrThumbEvent = new PostCollectOrThumbEvent();
                    postCollectOrThumbEvent.postList = postList;
                    EventBus.getDefault().post(postCollectOrThumbEvent);
                    LogUtil.m3785e("yan", "點贊");
                    RxBus.getDefault().post(postCollectOrThumbEvent);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPostTabListContact$IPostTabListView mView2;
                    mView2 = NewPostTabListPresenter.this.getMView();
                    if (mView2 != null) {
                        mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    }
                }
            });
        }
    }

    public final void foucs(final PostList postList, final int i) {
        if (postList != null) {
            ApiImplService.Companion.getApiImplService().postFoucs(DBUtil.getMemberId(), postList.getMemberId(), i).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>(postList, i) { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$foucs$$inlined$also$lambda$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    IPostTabListContact$IPostTabListView mView;
                    mView = NewPostTabListPresenter.this.getMView();
                    if (mView != null) {
                        mView.showDialog();
                    }
                }
            }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$foucs$$inlined$also$lambda$2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    IPostTabListContact$IPostTabListView mView;
                    mView = NewPostTabListPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    if (i == 1) {
                        UserInfoManager.setUserFollowCount(true);
                        postList.setIsAttention(1);
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.common_focus_success));
                    } else {
                        UserInfoManager.setUserFollowCount(false);
                        postList.setIsAttention(0);
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.common_cancel_focus_success));
                    }
                    MemberFocusEvent memberFocusEvent = new MemberFocusEvent();
                    memberFocusEvent.followFlag = i;
                    memberFocusEvent.f1748id = postList.getMemberId();
                    EventBus.getDefault().post(memberFocusEvent);
                    RxBus.getDefault().post(memberFocusEvent);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPostTabListContact$IPostTabListView mView;
                    mView = NewPostTabListPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                }
            });
        }
    }

    public final void collect(final PostList postList) {
        if (postList != null) {
            ApiImplService.Companion.getApiImplService().postCollect(DBUtil.getMemberId(), postList.getId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>(postList) { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$collect$$inlined$also$lambda$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    IPostTabListContact$IPostTabListView mView;
                    mView = NewPostTabListPresenter.this.getMView();
                    if (mView != null) {
                        mView.showDialog();
                    }
                }
            }).subscribe(new Consumer<BaseResponse<Object>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$collect$$inlined$also$lambda$2
                @Override // io.reactivex.functions.Consumer
                public final void accept(BaseResponse<Object> baseResponse) {
                    IPostTabListContact$IPostTabListView mView;
                    mView = NewPostTabListPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    if (baseResponse instanceof BaseResponse) {
                        if (postList.getIsFavor() == 1) {
                            postList.setIsFavor(0);
                            UserInfoManager.setFavorCount(false);
                            ToastUtil.showCenterToast((int) R.string.post_collect_cancel);
                        } else {
                            postList.setIsFavor(1);
                            UserInfoManager.setFavorCount(true);
                            ToastUtil.showCenterToast((int) R.string.post_collect_success);
                        }
                        PostCollectOrThumbEvent postCollectOrThumbEvent = new PostCollectOrThumbEvent();
                        postCollectOrThumbEvent.postList = postList;
                        EventBus.getDefault().post(postCollectOrThumbEvent);
                        RxBus.getDefault().post(postCollectOrThumbEvent);
                    }
                    if (baseResponse instanceof Throwable) {
                        ToastUtil.showCenterToast(((Throwable) baseResponse).getMessage());
                    }
                }
            });
        }
    }

    public final void requestMainNotify() {
        ApiImplService.Companion.getApiImplService().postHomeMainNotify().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestMainNotify$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<MainNotifyBean> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                NewPostTabListPresenter.this.hasRequestNotice = false;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.HandlerPostMainNotify(arrayList);
                }
            }
        });
    }

    public final void requestPersonPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postPersonPost(paramsMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestPersonPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestSerachPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postSeachPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestSerachPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestSearchRecommendPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postSeachRecommendPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestSearchRecommendPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestThumbPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postThumbPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestThumbPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestCirclePost(String url, Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postCirclePost(url, paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestCirclePost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestCollectPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postCollectPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestCollectPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestPushPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postPushPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestPushPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestTagPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postTagPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestTagPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestDeletePushPost(String articleIds, int i, final int i2) {
        Intrinsics.checkParameterIsNotNull(articleIds, "articleIds");
        ApiImplService.Companion.getApiImplService().deleteMyPushPost(articleIds, i).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestDeletePushPost$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IPostTabListContact$IPostTabListView mView;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.showDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestDeletePushPost$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostTabListContact$IPostTabListView mView;
                IPostTabListContact$IPostTabListView mView2;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerRemoveItem(i2);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                IPostTabListContact$IPostTabListView mView2;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewPostTabListPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestPostToChannelId(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().requestPostToChannelId(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestPostToChannelId$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                StringBuilder sb = new StringBuilder();
                sb.append("获取频道帖子报错e");
                String str = null;
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    mView.onError(str);
                }
            }
        });
    }

    public final void deleteTag(int i, String tagName, int i2, final int i3, final int i4) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        ApiImplService.Companion.getApiImplService().deleteTag(i, tagName, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$deleteTag$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewPostTabListPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$deleteTag$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostTabListContact$IPostTabListView mView;
                NewPostTabListPresenter.this.dismissDialog();
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerDeleteTagSuccess(i3, i4);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                NewPostTabListPresenter.this.dismissDialog();
            }
        });
    }

    public final void requestHotMessagePostList(String str) {
        ApiImplService.Companion.getApiImplService().requestHotEventPostList(str).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestHotMessagePostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestNewPost(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestNewPost(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestNewPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestPostRankList(String url, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        ApiImplService.Companion.getApiImplService().requestPostRankList(url, i, i2, i3).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestPostRankList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestPostSubscribeList(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestHomePageSubscribeList(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewPostTabListPresenter$requestPostSubscribeList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPostTabListContact$IPostTabListView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostTabListContact$IPostTabListView mView;
                mView = NewPostTabListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
