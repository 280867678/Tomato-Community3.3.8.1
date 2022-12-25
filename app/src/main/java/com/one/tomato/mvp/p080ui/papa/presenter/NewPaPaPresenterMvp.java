package com.one.tomato.mvp.p080ui.papa.presenter;

import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaContact;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaContact$IPaPaView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaPresenterMvp.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp */
/* loaded from: classes3.dex */
public final class NewPaPaPresenterMvp extends MvpBasePresenter<IPaPaContact$IPaPaView> implements IPaPaContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestPostList(int i, int i2, int i3) {
        if (i3 == 1) {
            ApiImplService.Companion.getApiImplService().getNewPaPaListData(i, i2, DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestPostList$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(ArrayList<PostList> arrayList) {
                    IPaPaContact$IPaPaView mView;
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        mView.handlerPostListData(arrayList);
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPaPaContact$IPaPaView mView;
                    StringBuilder sb = new StringBuilder();
                    sb.append("拍拍 -獲取數據失敗=");
                    String str = null;
                    sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    LogUtil.m3788d(sb.toString());
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        if (responseThrowable != null) {
                            str = responseThrowable.getThrowableMessage();
                        }
                        mView.onError(str);
                    }
                }
            });
        } else if (i3 == 3) {
            ApiImplService.Companion.getApiImplService().getNewPaPaPayListData(i, i2, DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestPostList$2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(ArrayList<PostList> arrayList) {
                    IPaPaContact$IPaPaView mView;
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        mView.handlerPostListData(arrayList);
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPaPaContact$IPaPaView mView;
                    StringBuilder sb = new StringBuilder();
                    sb.append("拍拍 -獲取數據失敗=");
                    String str = null;
                    sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    LogUtil.m3788d(sb.toString());
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        if (responseThrowable != null) {
                            str = responseThrowable.getThrowableMessage();
                        }
                        mView.onError(str);
                    }
                }
            });
        } else if (i3 != 4) {
        } else {
            ApiImplService.Companion.getApiImplService().requestVideoNew(i, i2, DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestPostList$3
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(ArrayList<PostList> arrayList) {
                    IPaPaContact$IPaPaView mView;
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        mView.handlerPostListData(arrayList);
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPaPaContact$IPaPaView mView;
                    StringBuilder sb = new StringBuilder();
                    sb.append("拍拍 -獲取數據失敗=");
                    String str = null;
                    sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    LogUtil.m3788d(sb.toString());
                    mView = NewPaPaPresenterMvp.this.getMView();
                    if (mView != null) {
                        if (responseThrowable != null) {
                            str = responseThrowable.getThrowableMessage();
                        }
                        mView.onError(str);
                    }
                }
            });
        }
    }

    public void requestArticleList(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().postPersonPost(map).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestArticleList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaContact$IPaPaView mView;
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaContact$IPaPaView mView;
                StringBuilder sb = new StringBuilder();
                sb.append("拍拍 -獲取數據失敗=");
                String str = null;
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3788d(sb.toString());
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    mView.onError(str);
                }
            }
        });
    }

    public void requestRankPaPa(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestRankPaPa(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestRankPaPa$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaContact$IPaPaView mView;
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaContact$IPaPaView mView;
                StringBuilder sb = new StringBuilder();
                sb.append("拍拍 -獲取數據失敗=");
                String str = null;
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3788d(sb.toString());
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    mView.onError(str);
                }
            }
        });
    }

    public final void requestTagPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postTagPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestTagPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaContact$IPaPaView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaContact$IPaPaView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestHotMessage(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestHotEventList(map).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostHotMessageBean>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestHotMessage$1
            /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
                r0 = r1.this$0.getMView();
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResult(ArrayList<PostHotMessageBean> arrayList) {
                IPaPaContact$IPaPaView mView;
                if (arrayList != null) {
                    if ((arrayList == null || arrayList.isEmpty()) || mView == null) {
                        return;
                    }
                    mView.handlerHotMessageData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("請求papa推荐的热点事件失敗 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3783i("yan", sb.toString());
            }
        });
    }

    public final void requestHotMessagePostList(String str) {
        ApiImplService.Companion.getApiImplService().requestHotEventPostList(str).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaPresenterMvp$requestHotMessagePostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaContact$IPaPaView mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaContact$IPaPaView mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPaPaPresenterMvp.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
