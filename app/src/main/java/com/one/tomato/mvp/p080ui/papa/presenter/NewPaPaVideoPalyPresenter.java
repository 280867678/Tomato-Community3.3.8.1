package com.one.tomato.mvp.p080ui.papa.presenter;

import com.broccoli.p150bh.R;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact$IPaPaPresenter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaVideoPalyPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter */
/* loaded from: classes3.dex */
public final class NewPaPaVideoPalyPresenter extends MvpBasePresenter<IPaPaPlayContact> implements IPaPaPlayContact$IPaPaPresenter {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void thumbUp(final PostList postList) {
        if (postList != null) {
            Observable<R> compose = ApiImplService.Companion.getApiImplService().faverPost(DBUtil.getMemberId(), postList.getId()).compose(RxUtils.schedulersTransformer());
            IPaPaPlayContact mView = getMView();
            compose.compose(RxUtils.bindToLifecycler(mView != null ? mView.getLifecycleProvider() : null)).subscribe(new ApiDisposableObserver<Object>(postList) { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$thumbUp$$inlined$let$lambda$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    LogUtil.m3785e("yan", "點贊");
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPaPaPlayContact mView2;
                    mView2 = NewPaPaVideoPalyPresenter.this.getMView();
                    String str = null;
                    if (mView2 != null) {
                        mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("怕怕點贊失敗---------");
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    sb.append(str);
                    LogUtil.m3785e("yan", sb.toString());
                }
            });
        }
    }

    public void requestPostList(int i, int i2) {
        ApiImplService.Companion.getApiImplService().getNewPaPaListData(i, i2, DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$requestPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaPlayContact mView;
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaPlayContact mView;
                StringBuilder sb = new StringBuilder();
                sb.append("拍拍 -獲取數據失敗=");
                String str = null;
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3788d(sb.toString());
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    mView.onError(str);
                }
            }
        });
    }

    public final void requestPersonPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postPersonPost(paramsMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$requestPersonPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaPlayContact mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaPlayContact mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestSerachPost(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postSeachPost(paramsMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$requestSerachPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaPlayContact mView;
                LogUtil.m3787d("yan", "t" + arrayList);
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaPlayContact mView;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void downLoadVideoCheck(final boolean z) {
        ApiImplService.Companion.getApiImplService().downLoadVideoCheck(DBUtil.getMemberId(), 0).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$downLoadVideoCheck$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewPaPaVideoPalyPresenter.this.showDialog();
            }
        }).subscribe(new Consumer<BaseResponse<VideoPay>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$downLoadVideoCheck$2
            /* JADX WARN: Code restructure failed: missing block: B:5:0x0011, code lost:
                r0 = r2.this$0.getMView();
             */
            @Override // io.reactivex.functions.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(BaseResponse<VideoPay> baseResponse) {
                VideoPay data;
                IPaPaPlayContact mView;
                NewPaPaVideoPalyPresenter.this.dismissDialog();
                if (!(baseResponse instanceof BaseResponse) || (data = baseResponse.getData()) == null || mView == null) {
                    return;
                }
                mView.handlerDownCheck(data, z);
            }
        });
    }

    public final void downRecord(Integer num, int i) {
        ApiImplService.Companion.getApiImplService().downLoadRecordSave(DBUtil.getMemberId(), num, i).compose(RxUtils.schedulersTransformer()).subscribe(NewPaPaVideoPalyPresenter$downRecord$1.INSTANCE);
    }

    public final void foucs(final PostList postList, final int i, final int i2) {
        if (postList != null) {
            ApiImplService.Companion.getApiImplService().postFoucs(DBUtil.getMemberId(), postList.getMemberId(), i).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>(postList, i, i2) { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$foucs$$inlined$also$lambda$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    IPaPaPlayContact mView;
                    mView = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView != null) {
                        mView.showDialog();
                    }
                }
            }).subscribe(new ApiDisposableObserver<Object>(postList, i, i2) { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$foucs$$inlined$also$lambda$2
                final /* synthetic */ int $position$inlined;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$position$inlined = i2;
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    IPaPaPlayContact mView;
                    IPaPaPlayContact mView2;
                    mView = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    mView2 = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView2 != null) {
                        mView2.handlerFoucs(this.$position$inlined);
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPaPaPlayContact mView;
                    IPaPaPlayContact mView2;
                    mView = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    mView2 = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView2 != null) {
                        mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    }
                }
            });
        }
    }

    public final void collect(final PostList postList) {
        if (postList != null) {
            ApiImplService.Companion.getApiImplService().postCollect(DBUtil.getMemberId(), postList.getId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>(postList) { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$collect$$inlined$also$lambda$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    IPaPaPlayContact mView;
                    mView = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView != null) {
                        mView.showDialog();
                    }
                }
            }).subscribe(new Consumer<BaseResponse<Object>>(postList) { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$collect$$inlined$also$lambda$2
                /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:
                    r0 = r1.this$0.getMView();
                 */
                @Override // io.reactivex.functions.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void accept(BaseResponse<Object> baseResponse) {
                    IPaPaPlayContact mView;
                    IPaPaPlayContact mView2;
                    mView = NewPaPaVideoPalyPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    if (!(baseResponse instanceof BaseResponse) || mView2 == null) {
                        return;
                    }
                    mView2.handlerCollect(baseResponse.getCode());
                }
            });
        }
    }

    public final void addTagToPost(int i, String tagName, int i2, final Tag data, final int i3) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        Intrinsics.checkParameterIsNotNull(data, "data");
        ApiImplService.Companion.getApiImplService().requestAddTagToPost(i, tagName, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$addTagToPost$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewPaPaVideoPalyPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$addTagToPost$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPaPaPlayContact mView;
                NewPaPaVideoPalyPresenter.this.dismissDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_post_add_tag_sucess));
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerAddTagSuccess(data, i3);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaPlayContact mView;
                NewPaPaVideoPalyPresenter.this.dismissDialog();
                mView = NewPaPaVideoPalyPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerAddTagError(i3);
                }
            }
        });
    }
}
