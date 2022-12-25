package com.one.tomato.mvp.p080ui.post.presenter;

import android.app.Activity;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.adapter.PostDetailCommentAdapter;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentPresenter;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections._Collections;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* compiled from: NewCommentPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter */
/* loaded from: classes3.dex */
public final class NewCommentPresenter extends MvpBasePresenter<IPostCommentContact$IPostCommentView> implements IPostCommentContact$IPostCommentPresenter {
    private boolean mShouldScroll;
    private boolean replySending;
    private SelectPicTypeUtil selectPicTypeUtil;
    private TTUtil ttUtil;
    private int index = -1;
    private List<LocalMedia> selectList = new ArrayList();
    private final ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public final boolean getMShouldScroll() {
        return this.mShouldScroll;
    }

    public final void setMShouldScroll(boolean z) {
        this.mShouldScroll = z;
    }

    public final int getIndex() {
        return this.index;
    }

    public final boolean getReplySending() {
        return this.replySending;
    }

    public final void setReplySending(boolean z) {
        this.replySending = z;
    }

    public final List<LocalMedia> getSelectList() {
        return this.selectList;
    }

    public final void setSelectList(List<LocalMedia> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.selectList = list;
    }

    public final void selectResource(Activity activity) {
        IPostCommentContact$IPostCommentView mView = getMView();
        if (mView == null || !mView.isStartLogin()) {
            if (activity == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                new RxPermissions(activity).request("android.permission.CAMERA").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$selectResource$1
                    @Override // io.reactivex.Observer
                    public void onComplete() {
                    }

                    @Override // io.reactivex.Observer
                    public void onError(Throwable e) {
                        Intrinsics.checkParameterIsNotNull(e, "e");
                    }

                    @Override // io.reactivex.Observer
                    public void onSubscribe(Disposable d) {
                        Intrinsics.checkParameterIsNotNull(d, "d");
                    }

                    @Override // io.reactivex.Observer
                    public /* bridge */ /* synthetic */ void onNext(Boolean bool) {
                        onNext(bool.booleanValue());
                    }

                    public void onNext(boolean z) {
                        SelectPicTypeUtil selectPicTypeUtil;
                        if (z) {
                            selectPicTypeUtil = NewCommentPresenter.this.selectPicTypeUtil;
                            if (selectPicTypeUtil == null) {
                                return;
                            }
                            selectPicTypeUtil.selectCommonPhoto(9, false, false, false, NewCommentPresenter.this.getSelectList());
                            return;
                        }
                        ToastUtil.showCenterToast((int) R.string.permission_camera);
                    }
                });
            }
        }
    }

    public final void uploadImg(final PostDetailCommentAdapter postDetailCommentAdapter, final PostList postList) {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$uploadImg$1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia media) {
                ArrayList arrayList;
                ArrayList arrayList2;
                ArrayList arrayList3;
                IPostCommentContact$IPostCommentView mView;
                ArrayList arrayList4;
                TTUtil tTUtil;
                TTUtil tTUtil2;
                ArrayList arrayList5;
                Intrinsics.checkParameterIsNotNull(media, "media");
                arrayList = NewCommentPresenter.this.uploadSuccessList;
                arrayList.add(media);
                arrayList2 = NewCommentPresenter.this.uploadSuccessList;
                if (arrayList2.size() == NewCommentPresenter.this.getSelectList().size()) {
                    PostDetailCommentAdapter postDetailCommentAdapter2 = postDetailCommentAdapter;
                    String str = null;
                    CommentList item = postDetailCommentAdapter2 != null ? postDetailCommentAdapter2.getItem(0) : null;
                    StringBuilder sb = new StringBuilder();
                    arrayList3 = NewCommentPresenter.this.uploadSuccessList;
                    int size = arrayList3.size();
                    for (int i = 0; i < size; i++) {
                        arrayList4 = NewCommentPresenter.this.uploadSuccessList;
                        Object obj = arrayList4.get(i);
                        Intrinsics.checkExpressionValueIsNotNull(obj, "uploadSuccessList[i]");
                        LocalMedia localMedia = (LocalMedia) obj;
                        tTUtil = NewCommentPresenter.this.ttUtil;
                        String ceph = tTUtil != null ? tTUtil.getCeph(localMedia) : null;
                        sb.append("/");
                        tTUtil2 = NewCommentPresenter.this.ttUtil;
                        sb.append(tTUtil2 != null ? tTUtil2.getBucketName() : null);
                        sb.append("/");
                        sb.append(ceph);
                        arrayList5 = NewCommentPresenter.this.uploadSuccessList;
                        if (i < arrayList5.size() - 1) {
                            sb.append(";");
                        }
                    }
                    if (item != null) {
                        item.setUploadUrl(sb.toString());
                    }
                    if (item != null) {
                        item.setSendStatus(2);
                    }
                    PostDetailCommentAdapter postDetailCommentAdapter3 = postDetailCommentAdapter;
                    if (postDetailCommentAdapter3 != null) {
                        postDetailCommentAdapter3.notifyDataSetChanged();
                    }
                    mView = NewCommentPresenter.this.getMView();
                    if (mView != null) {
                        if (item != null) {
                            str = item.getContent();
                        }
                        mView.handlerSendFisrtComment(str, sb.toString());
                    }
                    NewCommentPresenter.this.getSelectList().clear();
                    NewCommentPresenter.this.setReplySending(true);
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                NewCommentPresenter.this.getSelectList().clear();
                PostDetailCommentAdapter postDetailCommentAdapter2 = postDetailCommentAdapter;
                CommentList item = postDetailCommentAdapter2 != null ? postDetailCommentAdapter2.getItem(0) : null;
                if (item != null) {
                    item.setSendStatus(3);
                }
                PostDetailCommentAdapter postDetailCommentAdapter3 = postDetailCommentAdapter;
                if (postDetailCommentAdapter3 != null) {
                    postDetailCommentAdapter3.notifyDataSetChanged();
                }
                NewCommentPresenter.this.setReplySending(false);
                PostList postList2 = postList;
                if (postList2 != null) {
                    postList2.setCommentTimes(postList2.getCommentTimes() - 1);
                }
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != null) {
            tTUtil.getStsToken(this.selectList);
        }
    }

    public void requestCommentList(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().getPostCommentList(map).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<CommentList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestCommentList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CommentList> arrayList) {
                IPostCommentContact$IPostCommentView mView;
                IPostCommentContact$IPostCommentView mView2;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewCommentPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerPostCommentList(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostCommentContact$IPostCommentView mView;
                IPostCommentContact$IPostCommentView mView2;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewCommentPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void thumbComment(boolean z, CommentList commentList, PostDetailCommentAdapter postDetailCommentAdapter) {
        String str;
        IPostCommentContact$IPostCommentView mView = getMView();
        if (mView == null || !mView.isStartLogin()) {
            if (commentList == null) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_data_exception_tip));
            } else if (commentList.getId() <= 0) {
            } else {
                if (DBUtil.getMemberId() == commentList.getMemberId()) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_thump_for_self));
                    return;
                }
                if (z) {
                    if (commentList.getIsThumbUp() == 1 && commentList.getIsThumbDown() == 0) {
                        commentList.setGoodNum(commentList.getGoodNum() - 1);
                        commentList.setIsThumbUp(0);
                        commentList.setIsThumbDown(0);
                    } else if (commentList.getIsThumbUp() == 0 && commentList.getIsThumbDown() == 0) {
                        commentList.setGoodNum(commentList.getGoodNum() + 1);
                        commentList.setIsThumbUp(1);
                        commentList.setIsThumbDown(0);
                    } else if (commentList.getIsThumbUp() == 0 && commentList.getIsThumbDown() == 1) {
                        commentList.setGoodNum(commentList.getGoodNum() + 1);
                        commentList.setPointOnNum(commentList.getPointOnNum() - 1);
                        commentList.setIsThumbUp(1);
                        commentList.setIsThumbDown(0);
                    }
                    str = "/app/acticleComments/isFaver";
                } else {
                    if (commentList.getIsThumbDown() == 1 && commentList.getIsThumbUp() == 0) {
                        commentList.setPointOnNum(commentList.getPointOnNum() - 1);
                        commentList.setIsThumbDown(0);
                        commentList.setIsThumbUp(0);
                    } else if (commentList.getIsThumbDown() == 0 && commentList.getIsThumbUp() == 0) {
                        commentList.setPointOnNum(commentList.getPointOnNum() + 1);
                        commentList.setIsThumbDown(1);
                        commentList.setIsThumbUp(0);
                    } else if (commentList.getIsThumbDown() == 0 && commentList.getIsThumbUp() == 1) {
                        commentList.setPointOnNum(commentList.getPointOnNum() + 1);
                        commentList.setGoodNum(commentList.getGoodNum() - 1);
                        commentList.setIsThumbDown(1);
                        commentList.setIsThumbUp(0);
                    }
                    str = "/app/acticleComments/isPointOn";
                }
                if (postDetailCommentAdapter != null) {
                    postDetailCommentAdapter.refreshNotifyItemChanged(postDetailCommentAdapter.getCurPosition());
                }
                ApiImplService.Companion.getApiImplService().postCommentTumb(str, DBUtil.getMemberId(), commentList.getId()).compose(RxUtils.schedulersTransformer()).subscribe(NewCommentPresenter$thumbComment$1.INSTANCE);
            }
        }
    }

    public final void sendRetryComment(CommentList itemData, final int i, String str, PostDetailCommentAdapter postDetailCommentAdapter, int i2) {
        Intrinsics.checkParameterIsNotNull(itemData, "itemData");
        if (itemData.getSendStatus() == 3) {
            if (TextUtils.isEmpty(itemData.getUploadUrl())) {
                return;
            }
            uploadImageRetry(itemData, i, postDetailCommentAdapter, str, i2);
        } else if (itemData.getSendStatus() != 4) {
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("articleId", Integer.valueOf(i2));
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            linkedHashMap.put("content", AppSecretUtil.decodeResponse(itemData.getContent()));
            linkedHashMap.put("type", "1");
            if (!TextUtils.isEmpty(itemData.getUploadUrl())) {
                linkedHashMap.put("imageUrl", itemData.getUploadUrl());
            }
            linkedHashMap.put("verifyCode", str);
            ApiImplService.Companion.getApiImplService().sendReplyComment(linkedHashMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$sendRetryComment$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    IPostCommentContact$IPostCommentView mView;
                    mView = NewCommentPresenter.this.getMView();
                    if (mView != null) {
                        mView.showDialog();
                    }
                }
            }).subscribe(new ApiDisposableObserver<CommentList>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$sendRetryComment$2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(CommentList commentList) {
                    IPostCommentContact$IPostCommentView mView;
                    IPostCommentContact$IPostCommentView mView2;
                    mView = NewCommentPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    mView2 = NewCommentPresenter.this.getMView();
                    if (mView2 != null) {
                        mView2.handlerReplyComment(commentList, i);
                    }
                    NewCommentPresenter.this.setReplySending(false);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    IPostCommentContact$IPostCommentView mView;
                    IPostCommentContact$IPostCommentView mView2;
                    mView = NewCommentPresenter.this.getMView();
                    if (mView != null) {
                        mView.dismissDialog();
                    }
                    mView2 = NewCommentPresenter.this.getMView();
                    if (mView2 != null) {
                        mView2.handlerReplyCommentError(responseThrowable, i);
                    }
                    NewCommentPresenter.this.setReplySending(false);
                }
            });
            itemData.setSendStatus(1);
            if (postDetailCommentAdapter != null) {
                postDetailCommentAdapter.setData(i, itemData);
            }
            this.replySending = true;
        }
    }

    private final void uploadImageRetry(final CommentList commentList, final int i, final PostDetailCommentAdapter postDetailCommentAdapter, final String str, final int i2) {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$uploadImageRetry$1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                ArrayList arrayList;
                List emptyList;
                boolean z;
                arrayList = NewCommentPresenter.this.uploadSuccessList;
                arrayList.clear();
                NewCommentPresenter.this.getSelectList().clear();
                commentList.setSendStatus(1);
                NewCommentPresenter.this.setReplySending(true);
                PostDetailCommentAdapter postDetailCommentAdapter2 = postDetailCommentAdapter;
                if (postDetailCommentAdapter2 != null) {
                    postDetailCommentAdapter2.notifyDataSetChanged();
                }
                String uploadUrl = commentList.getUploadUrl();
                Intrinsics.checkExpressionValueIsNotNull(uploadUrl, "itemData.uploadUrl");
                List<String> split = new Regex(";").split(uploadUrl, 0);
                if (!split.isEmpty()) {
                    ListIterator<String> listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() == 0) {
                            z = true;
                            continue;
                        } else {
                            z = false;
                            continue;
                        }
                        if (!z) {
                            emptyList = _Collections.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                emptyList = CollectionsKt__CollectionsKt.emptyList();
                Object[] array = emptyList.toArray(new String[0]);
                if (array != null) {
                    for (String str2 : (String[]) array) {
                        NewCommentPresenter.this.getSelectList().add(new LocalMedia(str2));
                    }
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia media) {
                ArrayList arrayList;
                ArrayList arrayList2;
                ArrayList arrayList3;
                ArrayList arrayList4;
                TTUtil tTUtil;
                TTUtil tTUtil2;
                ArrayList arrayList5;
                Intrinsics.checkParameterIsNotNull(media, "media");
                arrayList = NewCommentPresenter.this.uploadSuccessList;
                arrayList.add(media);
                arrayList2 = NewCommentPresenter.this.uploadSuccessList;
                if (arrayList2.size() == NewCommentPresenter.this.getSelectList().size()) {
                    StringBuilder sb = new StringBuilder();
                    arrayList3 = NewCommentPresenter.this.uploadSuccessList;
                    int size = arrayList3.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        arrayList4 = NewCommentPresenter.this.uploadSuccessList;
                        Object obj = arrayList4.get(i3);
                        Intrinsics.checkExpressionValueIsNotNull(obj, "uploadSuccessList.get(i)");
                        LocalMedia localMedia = (LocalMedia) obj;
                        tTUtil = NewCommentPresenter.this.ttUtil;
                        String str2 = null;
                        String ceph = tTUtil != null ? tTUtil.getCeph(localMedia) : null;
                        sb.append("/");
                        tTUtil2 = NewCommentPresenter.this.ttUtil;
                        if (tTUtil2 != null) {
                            str2 = tTUtil2.getBucketName();
                        }
                        sb.append(str2);
                        sb.append("/");
                        sb.append(ceph);
                        arrayList5 = NewCommentPresenter.this.uploadSuccessList;
                        if (i3 < arrayList5.size() - 1) {
                            sb.append(";");
                        }
                    }
                    commentList.setSendStatus(4);
                    commentList.setUploadUrl(sb.toString());
                    PostDetailCommentAdapter postDetailCommentAdapter2 = postDetailCommentAdapter;
                    if (postDetailCommentAdapter2 != null) {
                        postDetailCommentAdapter2.notifyDataSetChanged();
                    }
                    NewCommentPresenter.this.sendRetryComment(commentList, i, str, postDetailCommentAdapter, i2);
                    NewCommentPresenter.this.setReplySending(true);
                    NewCommentPresenter.this.getSelectList().clear();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                commentList.setSendStatus(3);
                PostDetailCommentAdapter postDetailCommentAdapter2 = postDetailCommentAdapter;
                if (postDetailCommentAdapter2 != null) {
                    postDetailCommentAdapter2.notifyDataSetChanged();
                }
                NewCommentPresenter.this.setReplySending(false);
                NewCommentPresenter.this.getSelectList().clear();
            }
        });
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != null) {
            tTUtil.getStsToken(this.selectList);
        }
    }

    public void requestCommentDelete(Map<String, Object> map, final int i) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().delateComment(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestCommentDelete$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IPostCommentContact$IPostCommentView mView;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.showDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestCommentDelete$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostCommentContact$IPostCommentView mView;
                IPostCommentContact$IPostCommentView mView2;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewCommentPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerDelete(i);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostCommentContact$IPostCommentView mView;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void postMessage(String str) {
                super.postMessage(str);
                ToastUtil.showCenterToast(str);
            }
        });
    }

    public void requestAuthCommentDelete(Map<String, Object> map, final int i) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().delateAuthComment(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestAuthCommentDelete$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IPostCommentContact$IPostCommentView mView;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.showDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestAuthCommentDelete$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostCommentContact$IPostCommentView mView;
                IPostCommentContact$IPostCommentView mView2;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = NewCommentPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerDelete(i);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostCommentContact$IPostCommentView mView;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void postMessage(String str) {
                super.postMessage(str);
                ToastUtil.showCenterToast(str);
            }
        });
    }

    public void requestSendComment(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().sendComment(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestSendComment$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IPostCommentContact$IPostCommentView mView;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.showDialog();
                }
            }
        }).subscribe(new Consumer<BaseResponse<CommentList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestSendComment$2
            /* JADX WARN: Code restructure failed: missing block: B:23:0x0049, code lost:
                r0 = r1.this$0.getMView();
             */
            @Override // io.reactivex.functions.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(BaseResponse<CommentList> baseResponse) {
                IPostCommentContact$IPostCommentView mView;
                IPostCommentContact$IPostCommentView mView2;
                IPostCommentContact$IPostCommentView mView3;
                IPostCommentContact$IPostCommentView mView4;
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                if (baseResponse instanceof BaseResponse) {
                    if (baseResponse.getCode() == 0 && baseResponse.getData() != null) {
                        mView4 = NewCommentPresenter.this.getMView();
                        if (mView4 == null) {
                            return;
                        }
                        mView4.handlerSendCommentSucss(baseResponse.getData());
                        return;
                    }
                    ToastUtil.showCenterToast(baseResponse.getMessage());
                    mView3 = NewCommentPresenter.this.getMView();
                    if (mView3 == null) {
                        return;
                    }
                    mView3.handlerSendCommentError(baseResponse.getCode());
                } else if (!(baseResponse instanceof ResponseThrowable) || mView2 == null) {
                } else {
                    mView2.handlerSendCommentError(((ResponseThrowable) baseResponse).code);
                }
            }
        });
    }

    public final void smoothMoveToPosition(RecyclerView mRecyclerView, int i) {
        Intrinsics.checkParameterIsNotNull(mRecyclerView, "mRecyclerView");
        this.index = i;
        int childLayoutPosition = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int childLayoutPosition2 = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (i < childLayoutPosition) {
            mRecyclerView.smoothScrollToPosition(i);
        } else if (i <= childLayoutPosition2) {
            int i2 = i - childLayoutPosition;
            if (i2 < 0 || i2 >= mRecyclerView.getChildCount()) {
                return;
            }
            mRecyclerView.smoothScrollBy(0, mRecyclerView.getChildAt(i2).getTop());
        } else {
            mRecyclerView.smoothScrollToPosition(i);
            this.mShouldScroll = true;
        }
    }

    public final void requestSinglePostDetail(String url, Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().postSinglePostDetail(url, paramsMap).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestSinglePostDetail$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewCommentPresenter.this.showDialog();
            }
        }).subscribe(new Consumer<BaseResponse<PostList>>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$requestSinglePostDetail$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(BaseResponse<PostList> baseResponse) {
                IPostCommentContact$IPostCommentView mView;
                NewCommentPresenter.this.dismissDialog();
                if (baseResponse instanceof BaseResponse) {
                    PostList data = baseResponse.getData();
                    mView = NewCommentPresenter.this.getMView();
                    if (mView == null) {
                        return;
                    }
                    mView.handlerPostSingleDetail(data);
                }
            }
        });
    }

    public final void addTagToPost(int i, String tagName, int i2, final Tag data) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        Intrinsics.checkParameterIsNotNull(data, "data");
        ApiImplService.Companion.getApiImplService().requestAddTagToPost(i, tagName, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$addTagToPost$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewCommentPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$addTagToPost$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostCommentContact$IPostCommentView mView;
                NewCommentPresenter.this.dismissDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_post_add_tag_sucess));
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerAddTagSuccess(data);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostCommentContact$IPostCommentView mView;
                NewCommentPresenter.this.dismissDialog();
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerAddTagError();
                }
            }
        });
    }

    public final void deleteTag(int i, String tagName, int i2, final int i3) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        ApiImplService.Companion.getApiImplService().deleteTag(i, tagName, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$deleteTag$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewCommentPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.presenter.NewCommentPresenter$deleteTag$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IPostCommentContact$IPostCommentView mView;
                NewCommentPresenter.this.dismissDialog();
                mView = NewCommentPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerDeleteTagSuccess(i3);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                NewCommentPresenter.this.dismissDialog();
            }
        });
    }
}
