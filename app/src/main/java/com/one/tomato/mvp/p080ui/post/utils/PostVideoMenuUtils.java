package com.one.tomato.mvp.p080ui.post.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewParent;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.PostDetailMenuDialog;
import com.one.tomato.dialog.VideoCacheTipDialog;
import com.one.tomato.dialog.VideoSaveIngDialog;
import com.one.tomato.dialog.VideoSaveTipDialog;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.post.VideoDownloadCountUtils;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PostVideoMenuUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils */
/* loaded from: classes3.dex */
public final class PostVideoMenuUtils {
    private BottomSheetBehavior<?> behavior;
    private Context context;
    private int downloadType;
    private PostDetailMenuDialog menuDialog;
    private NewPostItemOnClickCallBack newPostItemCallBack;
    private PostList postList;
    private VideoCacheTipDialog videoCacheTipDialog;
    private VideoSaveTipDialog videoSaveTipDialog;

    public PostVideoMenuUtils(Context context, PostList postList, NewPostItemOnClickCallBack newPostItemOnClickCallBack) {
        this.context = context;
        this.postList = postList;
        this.newPostItemCallBack = newPostItemOnClickCallBack;
    }

    public final void showMenuDialog() {
        if (this.menuDialog == null) {
            Context context = this.context;
            ViewParent viewParent = null;
            this.menuDialog = context != null ? new PostDetailMenuDialog(context) : null;
            PostDetailMenuDialog postDetailMenuDialog = this.menuDialog;
            CoordinatorLayout coordinatorLayout = postDetailMenuDialog != null ? (CoordinatorLayout) postDetailMenuDialog.findViewById(R.id.coordinatorLayout) : null;
            if (coordinatorLayout != null) {
                viewParent = coordinatorLayout.getParent();
            }
            View view = (View) viewParent;
            if (view != null) {
                this.behavior = BottomSheetBehavior.from(view);
            }
        }
        PostDetailMenuDialog postDetailMenuDialog2 = this.menuDialog;
        if (postDetailMenuDialog2 != null) {
            postDetailMenuDialog2.setPostList(this.postList);
        }
        PostDetailMenuDialog postDetailMenuDialog3 = this.menuDialog;
        if (postDetailMenuDialog3 != null) {
            postDetailMenuDialog3.show();
        }
        BottomSheetBehavior<?> bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(3);
        }
        PostDetailMenuDialog postDetailMenuDialog4 = this.menuDialog;
        if (postDetailMenuDialog4 != null) {
            postDetailMenuDialog4.setMenuListener(new PostDetailMenuDialog.MenuListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showMenuDialog$3
                /* JADX WARN: Code restructure failed: missing block: B:4:0x0018, code lost:
                    if ((r0 instanceof com.one.tomato.mvp.base.view.MvpBaseActivity) != false) goto L14;
                 */
                @Override // com.one.tomato.dialog.PostDetailMenuDialog.MenuListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void collect(PostList postList) {
                    Context context2;
                    NewPostItemOnClickCallBack newPostItemOnClickCallBack;
                    PostDetailMenuDialog postDetailMenuDialog5;
                    Context context3;
                    Intrinsics.checkParameterIsNotNull(postList, "postList");
                    context2 = PostVideoMenuUtils.this.context;
                    if (!(context2 instanceof BaseActivity)) {
                        context3 = PostVideoMenuUtils.this.context;
                    }
                    if (AppUtil.isFastClick(postList.getId(), ConstantUtils.MAX_ITEM_NUM)) {
                        ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                        return;
                    }
                    newPostItemOnClickCallBack = PostVideoMenuUtils.this.newPostItemCallBack;
                    if (newPostItemOnClickCallBack != null) {
                        newPostItemOnClickCallBack.itemPostCollect(postList);
                    }
                    postDetailMenuDialog5 = PostVideoMenuUtils.this.menuDialog;
                    if (postDetailMenuDialog5 != null) {
                        postDetailMenuDialog5.dismiss();
                    }
                }

                @Override // com.one.tomato.dialog.PostDetailMenuDialog.MenuListener
                public void download(PostList postList) {
                    Intrinsics.checkParameterIsNotNull(postList, "postList");
                    PostVideoMenuUtils.this.downloadOrSaveDCIM(true, postList);
                }

                @Override // com.one.tomato.dialog.PostDetailMenuDialog.MenuListener
                public void saveDCIM(PostList postList) {
                    Intrinsics.checkParameterIsNotNull(postList, "postList");
                    PostVideoMenuUtils.this.downloadOrSaveDCIM(false, postList);
                }

                @Override // com.one.tomato.dialog.PostDetailMenuDialog.MenuListener
                public void share(PostList postList) {
                    PostDetailMenuDialog postDetailMenuDialog5;
                    NewPostItemOnClickCallBack newPostItemOnClickCallBack;
                    Intrinsics.checkParameterIsNotNull(postList, "postList");
                    postDetailMenuDialog5 = PostVideoMenuUtils.this.menuDialog;
                    if (postDetailMenuDialog5 != null) {
                        postDetailMenuDialog5.dismiss();
                    }
                    newPostItemOnClickCallBack = PostVideoMenuUtils.this.newPostItemCallBack;
                    if (newPostItemOnClickCallBack != null) {
                        newPostItemOnClickCallBack.itemShare(postList);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downloadOrSaveDCIM(boolean z, PostList postList) {
        Context context = this.context;
        if (context != null) {
            if (!(context instanceof BaseActivity) && !(context instanceof MvpBaseActivity)) {
                return;
            }
            boolean hasDownloadRecord = VideoDownloadCountUtils.getInstance().hasDownloadRecord(String.valueOf(postList.getId()));
            boolean hasSaveLocalRecord = VideoDownloadCountUtils.getInstance().hasSaveLocalRecord(String.valueOf(postList.getId()));
            if (!z) {
                hasDownloadRecord = hasSaveLocalRecord;
            }
            if (postList.getMemberId() == DBUtil.getMemberId() || hasDownloadRecord) {
                if (z) {
                    download(false);
                } else {
                    showSaveIngDialog(false);
                }
                PostDetailMenuDialog postDetailMenuDialog = this.menuDialog;
                if (postDetailMenuDialog == null) {
                    return;
                }
                postDetailMenuDialog.dismiss();
                return;
            }
            if (z) {
                if (Intrinsics.compare(postList.getPrice(), 0) == 1) {
                    if (postList.isAlreadyPaid()) {
                        download(true);
                    } else {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_video_need_pay));
                        Context context2 = this.context;
                        if (context2 != null) {
                            PostUtils.INSTANCE.showImageNeedPayDialog(context2, String.valueOf(postList.getPrice()), String.valueOf(postList.getId()), 0, new PostVideoMenuUtils$downloadOrSaveDCIM$$inlined$let$lambda$1(this, postList, z), PostVideoMenuUtils$downloadOrSaveDCIM$1$1$2.INSTANCE);
                        }
                    }
                    PostDetailMenuDialog postDetailMenuDialog2 = this.menuDialog;
                    if (postDetailMenuDialog2 == null) {
                        return;
                    }
                    postDetailMenuDialog2.dismiss();
                    return;
                }
            } else if (Intrinsics.compare(postList.getDownPrice(), 0) == 1) {
                if (PostRewardPayUtils.INSTANCE.isAreadlyDownPay(postList.getId())) {
                    showSaveIngDialog(true);
                    return;
                }
                UserInfo userInfo = DBUtil.getUserInfo();
                Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
                    VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils, "VideoDownloadCountUtils.getInstance()");
                    if (Intrinsics.compare(videoDownloadCountUtils.getVipSaveCount(), 0) == 1 && Intrinsics.compare(postList.getPrice(), 0) == 0) {
                        showSaveIngDialog(true);
                        return;
                    }
                }
                showNeedPayVideoDialog(postList);
                PostDetailMenuDialog postDetailMenuDialog3 = this.menuDialog;
                if (postDetailMenuDialog3 == null) {
                    return;
                }
                postDetailMenuDialog3.dismiss();
                return;
            }
            UserInfo userInfo2 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
            if (Intrinsics.compare(userInfo2.getVipType(), 0) == 1) {
                if (z) {
                    download(false);
                } else {
                    VideoDownloadCountUtils videoDownloadCountUtils2 = VideoDownloadCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils2, "VideoDownloadCountUtils.getInstance()");
                    if (Intrinsics.compare(videoDownloadCountUtils2.getVipSaveCount(), 0) == 1) {
                        showSaveIngDialog(true);
                    } else {
                        downLoadVideoCheck(z);
                    }
                }
                PostDetailMenuDialog postDetailMenuDialog4 = this.menuDialog;
                if (postDetailMenuDialog4 == null) {
                    return;
                }
                postDetailMenuDialog4.dismiss();
            } else if (!UserPermissionUtil.getInstance().isPermissionEnable(2)) {
                PostDetailMenuDialog postDetailMenuDialog5 = this.menuDialog;
                if (postDetailMenuDialog5 == null) {
                    return;
                }
                postDetailMenuDialog5.dismiss();
            } else {
                downLoadVideoCheck(z);
                PostDetailMenuDialog postDetailMenuDialog6 = this.menuDialog;
                if (postDetailMenuDialog6 == null) {
                    return;
                }
                postDetailMenuDialog6.dismiss();
            }
        }
    }

    private final void downLoadVideoCheck(final boolean z) {
        Observable<R> compose = ApiImplService.Companion.getApiImplService().downLoadVideoCheck(DBUtil.getMemberId(), 0).compose(RxUtils.schedulersTransformer());
        Context context = this.context;
        if (context == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.trello.rxlifecycle2.components.support.RxAppCompatActivity");
        }
        compose.compose(RxUtils.bindToLifecycler((RxAppCompatActivity) context)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$downLoadVideoCheck$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                Context context2;
                Context context3;
                Context context4;
                Context context5;
                context2 = PostVideoMenuUtils.this.context;
                if (context2 instanceof BaseActivity) {
                    context5 = PostVideoMenuUtils.this.context;
                    if (context5 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.base.BaseActivity");
                    }
                    ((BaseActivity) context5).showWaitingDialog();
                }
                context3 = PostVideoMenuUtils.this.context;
                if (context3 instanceof MvpBaseActivity) {
                    context4 = PostVideoMenuUtils.this.context;
                    if (context4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context4).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<VideoPay>() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$downLoadVideoCheck$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(VideoPay videoPay) {
                Context context2;
                Context context3;
                Context context4;
                Context context5;
                context2 = PostVideoMenuUtils.this.context;
                if (context2 instanceof BaseActivity) {
                    context5 = PostVideoMenuUtils.this.context;
                    if (context5 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.base.BaseActivity");
                    }
                    ((BaseActivity) context5).hideWaitingDialog();
                }
                context3 = PostVideoMenuUtils.this.context;
                if (context3 instanceof MvpBaseActivity) {
                    context4 = PostVideoMenuUtils.this.context;
                    if (context4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context4).hideWaitingDialog();
                }
                if (videoPay != null) {
                    UserInfo userInfo = DBUtil.getUserInfo();
                    Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                    if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
                        PostVideoMenuUtils.this.selectDownloadType(z, videoPay);
                        return;
                    }
                    VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils, "VideoDownloadCountUtils.getInstance()");
                    if (videoDownloadCountUtils.isOpenVipTip()) {
                        PostVideoMenuUtils.this.selectDownloadType(z, videoPay);
                        return;
                    }
                    PostVideoMenuUtils.this.showOpenVipDialog(z, videoPay);
                    VideoDownloadCountUtils videoDownloadCountUtils2 = VideoDownloadCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils2, "VideoDownloadCountUtils.getInstance()");
                    videoDownloadCountUtils2.setOpenVipTip(true);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                Context context2;
                Context context3;
                Context context4;
                Context context5;
                context2 = PostVideoMenuUtils.this.context;
                if (context2 instanceof BaseActivity) {
                    context5 = PostVideoMenuUtils.this.context;
                    if (context5 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.base.BaseActivity");
                    }
                    ((BaseActivity) context5).hideWaitingDialog();
                }
                context3 = PostVideoMenuUtils.this.context;
                if (context3 instanceof MvpBaseActivity) {
                    context4 = PostVideoMenuUtils.this.context;
                    if (context4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context4).hideWaitingDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showOpenVipDialog(final boolean z, final VideoPay videoPay) {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this.context);
        customAlertDialog.setTitle(R.string.video_save_tip_title);
        customAlertDialog.setMessage(R.string.video_save_tip_message);
        customAlertDialog.setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner5_disable);
        customAlertDialog.setCancelButtonTextColor(R.color.white);
        customAlertDialog.setCancelButton(R.string.video_save_tip_cancel_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showOpenVipDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customAlertDialog.dismiss();
                PostVideoMenuUtils.this.selectDownloadType(z, videoPay);
            }
        });
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner5_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.video_save_tip_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showOpenVipDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context context;
                customAlertDialog.dismiss();
                VipActivity.Companion companion = VipActivity.Companion;
                context = PostVideoMenuUtils.this.context;
                if (context != null) {
                    companion.startActivity(context);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectDownloadType(boolean z, VideoPay videoPay) {
        if (z) {
            showDownloadTipDialog(videoPay);
        } else {
            showSaveTipDialog(videoPay);
        }
    }

    private final void showDownloadTipDialog(final VideoPay videoPay) {
        if (this.videoCacheTipDialog == null) {
            this.videoCacheTipDialog = new VideoCacheTipDialog(this.context);
            VideoCacheTipDialog videoCacheTipDialog = this.videoCacheTipDialog;
            if (videoCacheTipDialog != null) {
                videoCacheTipDialog.setVideoDownloadTipListener(new VideoCacheTipDialog.VideoDownloadTipListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showDownloadTipDialog$1
                    @Override // com.one.tomato.dialog.VideoCacheTipDialog.VideoDownloadTipListener
                    public void currencyDownload() {
                        PostList postList;
                        VideoCacheTipDialog videoCacheTipDialog2;
                        Context context;
                        VideoCacheTipDialog videoCacheTipDialog3;
                        Context context2;
                        VideoPay videoPay2 = videoPay;
                        if (videoPay2 == null) {
                            return;
                        }
                        if (videoPay2.getTmtBalance() < videoPay.getVideoPayTomatoAmount()) {
                            context = PostVideoMenuUtils.this.context;
                            if (context == null) {
                                return;
                            }
                            videoCacheTipDialog3 = PostVideoMenuUtils.this.videoCacheTipDialog;
                            if (videoCacheTipDialog3 != null) {
                                videoCacheTipDialog3.dismiss();
                            }
                            context2 = PostVideoMenuUtils.this.context;
                            RechargeActivity.startActivity(context2);
                            return;
                        }
                        postList = PostVideoMenuUtils.this.postList;
                        if (postList == null) {
                            return;
                        }
                        PostVideoMenuUtils.this.downloadType = 1;
                        videoCacheTipDialog2 = PostVideoMenuUtils.this.videoCacheTipDialog;
                        if (videoCacheTipDialog2 != null) {
                            videoCacheTipDialog2.dismiss();
                        }
                        PostVideoMenuUtils.this.download(true);
                    }
                });
            }
        }
        VideoCacheTipDialog videoCacheTipDialog2 = this.videoCacheTipDialog;
        if (videoCacheTipDialog2 != null) {
            videoCacheTipDialog2.setInfo(videoPay);
        }
    }

    private final void showSaveTipDialog(VideoPay videoPay) {
        if (this.videoSaveTipDialog == null) {
            this.videoSaveTipDialog = new VideoSaveTipDialog(this.context);
            VideoSaveTipDialog videoSaveTipDialog = this.videoSaveTipDialog;
            if (videoSaveTipDialog != null) {
                videoSaveTipDialog.setVideoSaveTipListener(new VideoSaveTipDialog.VideoSaveTipListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showSaveTipDialog$1
                    @Override // com.one.tomato.dialog.VideoSaveTipDialog.VideoSaveTipListener
                    public void potatoDownload() {
                        PostVideoMenuUtils.this.showSaveIngDialog(true);
                    }
                });
            }
        }
        VideoSaveTipDialog videoSaveTipDialog2 = this.videoSaveTipDialog;
        if (videoSaveTipDialog2 != null) {
            videoSaveTipDialog2.setInfo(videoPay);
        }
    }

    private final void showNeedPayVideoDialog(final PostList postList) {
        VideoSaveTipDialog videoSaveTipDialog = new VideoSaveTipDialog(this.context);
        videoSaveTipDialog.setVideoSaveTipListener(new VideoSaveTipDialog.VideoSaveTipListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showNeedPayVideoDialog$1

            /* compiled from: PostVideoMenuUtils.kt */
            /* renamed from: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showNeedPayVideoDialog$1$1 */
            /* loaded from: classes3.dex */
            static final class C26261 extends Lambda implements Function1<String, Unit> {
                C26261() {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(String str) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_sucess));
                    PostRewardPayUtils.INSTANCE.setDownPayPost(postList.getId());
                    PostVideoMenuUtils.this.showSaveIngDialog(true);
                }
            }

            /* compiled from: PostVideoMenuUtils.kt */
            /* renamed from: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showNeedPayVideoDialog$1$2 */
            /* loaded from: classes3.dex */
            static final class C26272 extends Lambda implements Function1<ResponseThrowable, Unit> {
                public static final C26272 INSTANCE = new C26272();

                C26272() {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public /* bridge */ /* synthetic */ Unit mo6794invoke(ResponseThrowable responseThrowable) {
                    invoke2(responseThrowable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(ResponseThrowable responseThrowable) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_error));
                }
            }

            @Override // com.one.tomato.dialog.VideoSaveTipDialog.VideoSaveTipListener
            public final void potatoDownload() {
                Context context;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                linkedHashMap.put("articleId", Integer.valueOf(postList.getId()));
                linkedHashMap.put("payType", 3);
                linkedHashMap.put("money", Integer.valueOf(postList.getDownPrice()));
                VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
                context = PostVideoMenuUtils.this.context;
                videoPlayCountUtils.postRewardPay(context, linkedHashMap, new C26261(), C26272.INSTANCE);
            }
        });
        videoSaveTipDialog.setData(postList.getDownPrice());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSaveIngDialog(final boolean z) {
        Context context = this.context;
        PostList postList = this.postList;
        Integer num = null;
        String secVideoUrl = postList != null ? postList.getSecVideoUrl() : null;
        PostList postList2 = this.postList;
        if (postList2 != null) {
            num = Integer.valueOf(postList2.getId());
        }
        String valueOf = String.valueOf(num);
        PostList postList3 = this.postList;
        new VideoSaveIngDialog(context, secVideoUrl, valueOf, postList3 != null ? postList3.getVideoView() : 0).setVideoSaveLocalListener(new VideoSaveIngDialog.VideoSaveLocalListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$showSaveIngDialog$1
            @Override // com.one.tomato.dialog.VideoSaveIngDialog.VideoSaveLocalListener
            public void fail() {
            }

            @Override // com.one.tomato.dialog.VideoSaveIngDialog.VideoSaveLocalListener
            public void success() {
                PostList postList4;
                String str;
                PostList postList5;
                PostList postList6;
                if (z) {
                    PostVideoMenuUtils postVideoMenuUtils = PostVideoMenuUtils.this;
                    postList6 = postVideoMenuUtils.postList;
                    postVideoMenuUtils.downRecord(postList6 != null ? Integer.valueOf(postList6.getId()) : null, 5);
                }
                VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
                postList4 = PostVideoMenuUtils.this.postList;
                if (postList4 == null || (str = String.valueOf(postList4.getId())) == null) {
                    str = "";
                }
                postList5 = PostVideoMenuUtils.this.postList;
                boolean z2 = false;
                if ((postList5 != null ? postList5.getPrice() : 0) == 0) {
                    z2 = true;
                }
                videoDownloadCountUtils.addSaveLocalRecord(str, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void download(final Boolean bool) {
        Context context = this.context;
        if (context instanceof BaseActivity) {
            if (context == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.base.BaseActivity");
            }
            new RxPermissions((BaseActivity) context).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$download$1
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
                public /* bridge */ /* synthetic */ void onNext(Boolean bool2) {
                    onNext(bool2.booleanValue());
                }

                public void onNext(boolean z) {
                    if (z) {
                        Boolean bool2 = bool;
                        if (bool2 == null) {
                            return;
                        }
                        PostVideoMenuUtils.this.startDownload(bool2.booleanValue());
                        return;
                    }
                    ToastUtil.showCenterToast((int) R.string.permission_storage);
                }
            });
        } else if (!(context instanceof MvpBaseActivity)) {
        } else {
            if (context == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
            }
            new RxPermissions((MvpBaseActivity) context).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$download$2
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
                public /* bridge */ /* synthetic */ void onNext(Boolean bool2) {
                    onNext(bool2.booleanValue());
                }

                public void onNext(boolean z) {
                    if (z) {
                        Boolean bool2 = bool;
                        if (bool2 == null) {
                            return;
                        }
                        PostVideoMenuUtils.this.startDownload(bool2.booleanValue());
                        return;
                    }
                    ToastUtil.showCenterToast((int) R.string.permission_storage);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startDownload(boolean z) {
        String str;
        PostList postList = this.postList;
        Integer num = null;
        String secVideoUrl = postList != null ? postList.getSecVideoUrl() : null;
        PostList postList2 = this.postList;
        String secVideoCover = postList2 != null ? postList2.getSecVideoCover() : null;
        PostList postList3 = this.postList;
        M3U8DownloadManager.getInstance().saveVideoBeanToFile(new VideoDownload(secVideoUrl, secVideoCover, postList3 != null ? postList3.getTitle() : null));
        M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.getInstance();
        PostList postList4 = this.postList;
        String secVideoUrl2 = postList4 != null ? postList4.getSecVideoUrl() : null;
        PostList postList5 = this.postList;
        String title = postList5 != null ? postList5.getTitle() : null;
        PostList postList6 = this.postList;
        String valueOf = String.valueOf(postList6 != null ? Integer.valueOf(postList6.getId()) : null);
        PostList postList7 = this.postList;
        m3U8DownloadManager.download(secVideoUrl2, title, valueOf, postList7 != null ? postList7.getVideoView() : 0);
        ToastUtil.showCenterToast((int) R.string.common_download_queue);
        if (z) {
            PostList postList8 = this.postList;
            if (postList8 != null) {
                num = Integer.valueOf(postList8.getId());
            }
            downRecord(num, this.downloadType);
        }
        VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
        PostList postList9 = this.postList;
        if (postList9 == null || (str = String.valueOf(postList9.getId())) == null) {
            str = "";
        }
        videoDownloadCountUtils.addDownloadRecord(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downRecord(Integer num, int i) {
        ApiImplService.Companion.getApiImplService().downLoadRecordSave(DBUtil.getMemberId(), num, i).compose(RxUtils.schedulersTransformer()).subscribe(PostVideoMenuUtils$downRecord$1.INSTANCE);
    }
}
