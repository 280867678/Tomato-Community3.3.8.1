package com.one.tomato.mvp.p080ui.post.controller;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.ProgressManager;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.LoginInfoEvent;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack;
import com.one.tomato.mvp.p080ui.post.item.PostVideoLookPayItem;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.PostVideoIjkPlayView;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.ipfs.IpfsUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.anko.AsyncKt;

/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger implements LifecycleObserver {
    private Context context;
    private NewPostVideoController controller;
    private PostVideoIjkPlayView ijkVideoView;
    private boolean isAlreadyUpData;
    private boolean isAlreadyUpVideoCom;
    private boolean isAlreadyUpVideoHalf;
    private boolean isReviewPost;
    private Disposable loginAndOutSubscribe;
    private NewPostItemOnClickCallBack newPostItemCallBack;
    private PostList postList;
    private PostVideoLookPayItem postVideoLookPayItem;
    private PostVideoLookPayItem showRewardVideoView;
    private boolean isVideoResume = true;
    private boolean subLookTime = true;
    private boolean isPostPay = true;
    private final Functions<Unit> callBack = new PostEvenOneTabVideoListManger$callBack$1(this);
    private final Functions<Unit> payCallBack = new PostEvenOneTabVideoListManger$payCallBack$1(this);

    public final void initVideoCommonManger(final Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (this.ijkVideoView == null) {
            this.context = context;
            new IjkplayProgressManger();
            this.ijkVideoView = new PostVideoIjkPlayView(context);
            this.controller = new NewPostVideoController(context);
            PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
            if (postVideoIjkPlayView != null) {
                postVideoIjkPlayView.setVideoController(this.controller);
            }
            NewPostVideoController newPostVideoController = this.controller;
            if (newPostVideoController != null) {
                newPostVideoController.setFullScreamCallBack(new BaseVideoController.FullScreamCallBack() { // from class: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initVideoCommonManger$1
                    @Override // com.dueeeke.videoplayer.controller.BaseVideoController.FullScreamCallBack
                    public final void fullCallBack(boolean z) {
                        PostVideoIjkPlayView postVideoIjkPlayView2;
                        PostVideoIjkPlayView postVideoIjkPlayView3;
                        if (z) {
                            postVideoIjkPlayView3 = PostEvenOneTabVideoListManger.this.ijkVideoView;
                            if (postVideoIjkPlayView3 == null) {
                                return;
                            }
                            postVideoIjkPlayView3.setmPlayerContainerBackGraoundColor(ContextCompat.getColor(context, R.color.black));
                            return;
                        }
                        postVideoIjkPlayView2 = PostEvenOneTabVideoListManger.this.ijkVideoView;
                        if (postVideoIjkPlayView2 == null) {
                            return;
                        }
                        postVideoIjkPlayView2.setmPlayerContainerBackGraoundColor(ContextCompat.getColor(context, R.color.transparent));
                    }
                });
            }
            NewPostVideoController newPostVideoController2 = this.controller;
            if (newPostVideoController2 != null) {
                newPostVideoController2.clickMenuCallBack(new PostEvenOneTabVideoListManger$initVideoCommonManger$2(this));
            }
            NewPostVideoController newPostVideoController3 = this.controller;
            if (newPostVideoController3 != null) {
                newPostVideoController3.clickFullScremCallBack(new PostEvenOneTabVideoListManger$initVideoCommonManger$3(this));
            }
            NewPostVideoController newPostVideoController4 = this.controller;
            if (newPostVideoController4 != null) {
                newPostVideoController4.payCompleteCallBack(new PostEvenOneTabVideoListManger$initVideoCommonManger$4(this));
            }
            this.postVideoLookPayItem = new PostVideoLookPayItem(context, this.postList);
            this.showRewardVideoView = new PostVideoLookPayItem(context, this.postList);
            setListenerCallBack();
            initRxBus();
        }
        release();
    }

    public final void recoverInitPlay(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        onReleaseVideoManger();
        initVideoCommonManger(context);
    }

    public final IjkVideoView getIjkPlayInstance() {
        return this.ijkVideoView;
    }

    public final void setNewPostItemClickCallBack(NewPostItemOnClickCallBack newPostItemCallBack) {
        Intrinsics.checkParameterIsNotNull(newPostItemCallBack, "newPostItemCallBack");
        this.newPostItemCallBack = newPostItemCallBack;
    }

    public final void setPostDetailSeekBarCallBack(PostDetailSeekBarCallBack postDetailSeekBarCallBack) {
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.setPostDetailSeekBarCallBack(postDetailSeekBarCallBack);
        }
    }

    /* compiled from: PostEvenOneTabVideoListManger.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$IjkplayProgressManger */
    /* loaded from: classes3.dex */
    public static final class IjkplayProgressManger extends ProgressManager {
        private Map<String, Long> mutableMap = new LinkedHashMap();

        @Override // com.dueeeke.videoplayer.player.ProgressManager
        public void saveProgress(String str, long j) {
            this.mutableMap.put(str, Long.valueOf(j));
        }

        @Override // com.dueeeke.videoplayer.player.ProgressManager
        public long getSavedProgress(String str) {
            Long l = this.mutableMap.get(str);
            if (l != null) {
                return l.longValue();
            }
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startVideo(PostList postList, String str) {
        if (TextUtils.isEmpty(postList.getSecVideoUrl())) {
            return;
        }
        this.subLookTime = true;
        this.isPostPay = true;
        this.isAlreadyUpData = false;
        this.isAlreadyUpVideoHalf = false;
        this.isAlreadyUpVideoCom = false;
        this.postList = postList;
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.setPost(postList, this.isReviewPost);
        }
        NewPostVideoController newPostVideoController2 = this.controller;
        if (newPostVideoController2 != null) {
            newPostVideoController2.setVideoPlayType(str);
        }
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        String str2 = domainServer.getTtViewVideoView2() + postList.getSecVideoUrl();
        String syncProxyUrl = IpfsUtil.getSyncProxyUrl(String.valueOf(postList.getId()), str2);
        postList.getSecVideoUrl();
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            postVideoIjkPlayView.setUrl(str2);
        }
        PostVideoIjkPlayView postVideoIjkPlayView2 = this.ijkVideoView;
        if (postVideoIjkPlayView2 != null) {
            postVideoIjkPlayView2.setIpfsUrl(syncProxyUrl);
        }
        PostVideoIjkPlayView postVideoIjkPlayView3 = this.ijkVideoView;
        if (postVideoIjkPlayView3 != null) {
            postVideoIjkPlayView3.setPlayState(0);
        }
        resumeVideo();
    }

    public final void setIsReviewPost(boolean z) {
        this.isReviewPost = z;
    }

    public final boolean isPlayCommplete() {
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            return newPostVideoController.isPlayCommplete();
        }
        return false;
    }

    public final boolean isShowPayView() {
        PostVideoLookPayItem postVideoLookPayItem = this.postVideoLookPayItem;
        ViewParent viewParent = null;
        if ((postVideoLookPayItem != null ? postVideoLookPayItem.getParent() : null) == null) {
            PostVideoLookPayItem postVideoLookPayItem2 = this.showRewardVideoView;
            if (postVideoLookPayItem2 != null) {
                viewParent = postVideoLookPayItem2.getParent();
            }
            return viewParent != null;
        }
        return true;
    }

    public final void doPauseResume() {
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView == null || postVideoIjkPlayView.getCurrentPlayState() != 6) {
            PostVideoIjkPlayView postVideoIjkPlayView2 = this.ijkVideoView;
            if (postVideoIjkPlayView2 != null && postVideoIjkPlayView2.isPlaying()) {
                PostVideoIjkPlayView postVideoIjkPlayView3 = this.ijkVideoView;
                if (postVideoIjkPlayView3 == null) {
                    return;
                }
                postVideoIjkPlayView3.pause();
                return;
            }
            PostVideoIjkPlayView postVideoIjkPlayView4 = this.ijkVideoView;
            if (postVideoIjkPlayView4 != null && postVideoIjkPlayView4.getCurrentPlayState() == 4) {
                PostVideoIjkPlayView postVideoIjkPlayView5 = this.ijkVideoView;
                if (postVideoIjkPlayView5 == null) {
                    return;
                }
                postVideoIjkPlayView5.resume();
                return;
            }
            resumeVideo();
        }
    }

    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.onProgressChanged(seekBar, i, z);
        }
    }

    public final void onStartTrackingTouch(SeekBar seekBar) {
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.onStartTrackingTouch(seekBar);
        }
    }

    public final void onStopTrackingTouch(SeekBar seekBar) {
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.onStopTrackingTouch(seekBar);
        }
    }

    public final void pauseVideo() {
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            postVideoIjkPlayView.stopPlayback();
        }
        PostVideoIjkPlayView postVideoIjkPlayView2 = this.ijkVideoView;
        if (postVideoIjkPlayView2 != null) {
            postVideoIjkPlayView2.release();
        }
    }

    public final void activityOnStop() {
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            postVideoIjkPlayView.pause();
        }
    }

    public final void activityOnResume() {
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            postVideoIjkPlayView.resume();
        }
    }

    public final void resumeVideo() {
        PostList postList = this.postList;
        if (postList != null && postList.isAd()) {
            this.isVideoResume = true;
        } else {
            PostList postList2 = this.postList;
            if ((postList2 != null ? postList2.getPrice() : 0) > 0) {
                this.isVideoResume = true;
            } else {
                VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils, "VideoPlayCountUtils.getInstance()");
                if (videoPlayCountUtils.isFreePlay()) {
                    this.isVideoResume = true;
                } else if (VideoPlayCountUtils.getInstance().isVideoPlay(this.postList)) {
                    this.isVideoResume = true;
                } else {
                    VideoPlayCountUtils videoPlayCountUtils2 = VideoPlayCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils2, "VideoPlayCountUtils.getInstance()");
                    if (videoPlayCountUtils2.getRemainTimes() > 0) {
                        this.isVideoResume = true;
                    }
                }
            }
        }
        if (this.isVideoResume) {
            try {
                NewPostVideoController newPostVideoController = this.controller;
                if (newPostVideoController != null) {
                    newPostVideoController.removeView(this.postVideoLookPayItem);
                }
                NewPostVideoController newPostVideoController2 = this.controller;
                if (newPostVideoController2 != null) {
                    newPostVideoController2.removeView(this.showRewardVideoView);
                }
            } catch (Exception unused) {
            }
            this.isPostPay = true;
            PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
            if (postVideoIjkPlayView != null) {
                postVideoIjkPlayView.start();
            }
            LogUtil.m3787d("yan2", "開始播放");
        }
    }

    public final void showNeedPayView() {
        PostList postList = this.postList;
        if (postList == null) {
            return;
        }
        if (postList != null && postList.isAd()) {
            return;
        }
        PostList postList2 = this.postList;
        if (postList2 != null && postList2.getMemberId() == DBUtil.getMemberId()) {
            return;
        }
        PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
        PostList postList3 = this.postList;
        if (postList3 == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (postRewardPayUtils.isAreadlyPay(postList3.getId())) {
        } else {
            PostList postList4 = this.postList;
            if (postList4 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (postList4.isAlreadyPaid()) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_isareadly_pay));
            } else {
                PostVideoLookPayItem postVideoLookPayItem = this.showRewardVideoView;
                if (postVideoLookPayItem != null) {
                    postVideoLookPayItem.setPostListData(this.postList);
                }
                pauseVideo();
                PostUtils.INSTANCE.requestBalance(this.context, new PostEvenOneTabVideoListManger$showNeedPayView$1(this), PostEvenOneTabVideoListManger$showNeedPayView$2.INSTANCE);
            }
        }
    }

    public final void checkLookTimeIsEnd() {
        PostList postList;
        NewPostVideoController newPostVideoController;
        PostList postList2 = this.postList;
        if (postList2 != null && (postList2 == null || !postList2.isAd())) {
            VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils, "VideoPlayCountUtils.getInstance()");
            if (!videoPlayCountUtils.isFreePlay() && !VideoPlayCountUtils.getInstance().isVideoPlay(this.postList)) {
                UserInfo userInfo = DBUtil.getUserInfo();
                Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                if (userInfo.getVipType() <= 0 && ((postList = this.postList) == null || postList.getFreeLookFlag() != 1)) {
                    VideoPlayCountUtils videoPlayCountUtils2 = VideoPlayCountUtils.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils2, "VideoPlayCountUtils.getInstance()");
                    if (videoPlayCountUtils2.getRemainTimes() == 0) {
                        PostVideoLookPayItem postVideoLookPayItem = this.postVideoLookPayItem;
                        if (postVideoLookPayItem != null) {
                            postVideoLookPayItem.setPostListData(this.postList);
                        }
                        PostVideoLookPayItem postVideoLookPayItem2 = this.postVideoLookPayItem;
                        if (postVideoLookPayItem2 != null) {
                            postVideoLookPayItem2.setLookTimes();
                        }
                        if (this.isVideoResume && (newPostVideoController = this.controller) != null) {
                            newPostVideoController.addView(this.postVideoLookPayItem);
                        }
                        pauseVideo();
                        this.isVideoResume = false;
                        return;
                    }
                    VideoPlayCountUtils.getInstance().addVideoPlayList(this.postList);
                    if (this.isAlreadyUpData) {
                        return;
                    }
                    PostUtils.INSTANCE.updatePostBrowse(this.postList);
                    this.isAlreadyUpData = true;
                    return;
                }
            }
        }
        if (!this.isAlreadyUpData) {
            PostUtils.INSTANCE.updatePostBrowse(this.postList);
            this.isAlreadyUpData = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void payPostDeductionLookTime() {
        VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils, "VideoPlayCountUtils.getInstance()");
        if (!videoPlayCountUtils.isFreePlay() && !VideoPlayCountUtils.getInstance().isVideoPlay(this.postList)) {
            VideoPlayCountUtils.getInstance().addVideoPlayList(this.postList);
        }
    }

    private final void setListenerCallBack() {
        PostVideoLookPayItem postVideoLookPayItem = this.postVideoLookPayItem;
        if (postVideoLookPayItem != null) {
            postVideoLookPayItem.setCallBackComplete(this.callBack);
        }
        PostVideoLookPayItem postVideoLookPayItem2 = this.showRewardVideoView;
        if (postVideoLookPayItem2 != null) {
            postVideoLookPayItem2.setPayCallBackComplete(this.payCallBack);
        }
        NewPostVideoController newPostVideoController = this.controller;
        if (newPostVideoController != null) {
            newPostVideoController.setonProgressListener(new PostEvenOneTabVideoListManger$setListenerCallBack$1(this));
        }
    }

    private final void initRxBus() {
        this.loginAndOutSubscribe = RxBus.getDefault().toObservable(LoginInfoEvent.class).subscribe(new Consumer<LoginInfoEvent>() { // from class: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initRxBus$1
            /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
                r5 = r4.this$0.controller;
             */
            /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:
                r5 = r4.this$0.controller;
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x0082, code lost:
                r5 = r4.this$0.controller;
             */
            /* JADX WARN: Code restructure failed: missing block: B:49:0x00a1, code lost:
                r5 = r4.this$0.controller;
             */
            @Override // io.reactivex.functions.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(LoginInfoEvent loginInfoEvent) {
                boolean z;
                PostVideoLookPayItem postVideoLookPayItem;
                PostVideoLookPayItem postVideoLookPayItem2;
                NewPostVideoController newPostVideoController;
                NewPostVideoController newPostVideoController2;
                PostVideoLookPayItem postVideoLookPayItem3;
                NewPostVideoController newPostVideoController3;
                PostVideoLookPayItem postVideoLookPayItem4;
                boolean z2;
                PostVideoLookPayItem postVideoLookPayItem5;
                PostVideoLookPayItem postVideoLookPayItem6;
                NewPostVideoController newPostVideoController4;
                PostVideoLookPayItem postVideoLookPayItem7;
                NewPostVideoController newPostVideoController5;
                PostVideoLookPayItem postVideoLookPayItem8;
                if (loginInfoEvent instanceof LoginInfoEvent) {
                    int i = loginInfoEvent.loginType;
                    ViewParent viewParent = null;
                    if (i != 1) {
                        if (i != 3) {
                            return;
                        }
                        try {
                            z2 = PostEvenOneTabVideoListManger.this.isVideoResume;
                            if (z2) {
                                return;
                            }
                            PostEvenOneTabVideoListManger.this.isVideoResume = true;
                            postVideoLookPayItem5 = PostEvenOneTabVideoListManger.this.postVideoLookPayItem;
                            if ((postVideoLookPayItem5 != null ? postVideoLookPayItem5.getParent() : null) != null && newPostVideoController5 != null) {
                                postVideoLookPayItem8 = PostEvenOneTabVideoListManger.this.postVideoLookPayItem;
                                newPostVideoController5.removeView(postVideoLookPayItem8);
                            }
                            postVideoLookPayItem6 = PostEvenOneTabVideoListManger.this.showRewardVideoView;
                            if (postVideoLookPayItem6 != null) {
                                viewParent = postVideoLookPayItem6.getParent();
                            }
                            if (viewParent == null || newPostVideoController4 == null) {
                                return;
                            }
                            postVideoLookPayItem7 = PostEvenOneTabVideoListManger.this.showRewardVideoView;
                            newPostVideoController4.removeView(postVideoLookPayItem7);
                            return;
                        } catch (Exception e) {
                            LogUtil.m3785e("", e);
                            return;
                        }
                    }
                    try {
                        z = PostEvenOneTabVideoListManger.this.isVideoResume;
                        if (z) {
                            return;
                        }
                        PostEvenOneTabVideoListManger.this.isVideoResume = true;
                        postVideoLookPayItem = PostEvenOneTabVideoListManger.this.postVideoLookPayItem;
                        if ((postVideoLookPayItem != null ? postVideoLookPayItem.getParent() : null) != null && newPostVideoController3 != null) {
                            postVideoLookPayItem4 = PostEvenOneTabVideoListManger.this.postVideoLookPayItem;
                            newPostVideoController3.removeView(postVideoLookPayItem4);
                        }
                        postVideoLookPayItem2 = PostEvenOneTabVideoListManger.this.showRewardVideoView;
                        if (postVideoLookPayItem2 != null) {
                            viewParent = postVideoLookPayItem2.getParent();
                        }
                        if (viewParent != null && newPostVideoController2 != null) {
                            postVideoLookPayItem3 = PostEvenOneTabVideoListManger.this.showRewardVideoView;
                            newPostVideoController2.removeView(postVideoLookPayItem3);
                        }
                        newPostVideoController = PostEvenOneTabVideoListManger.this.controller;
                        if (newPostVideoController == null) {
                            return;
                        }
                        newPostVideoController.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initRxBus$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NewPostVideoController newPostVideoController6;
                                NewPostVideoController newPostVideoController7;
                                newPostVideoController6 = PostEvenOneTabVideoListManger.this.controller;
                                Boolean bool = null;
                                Boolean valueOf = newPostVideoController6 != null ? Boolean.valueOf(newPostVideoController6.isShown()) : null;
                                Rect rect = new Rect();
                                newPostVideoController7 = PostEvenOneTabVideoListManger.this.controller;
                                if (newPostVideoController7 != null) {
                                    bool = Boolean.valueOf(newPostVideoController7.getGlobalVisibleRect(rect));
                                }
                                if (!Intrinsics.areEqual(valueOf, true) || !Intrinsics.areEqual(bool, true)) {
                                    return;
                                }
                                PostEvenOneTabVideoListManger.this.resumeVideo();
                            }
                        }, 1000L);
                    } catch (Exception e2) {
                        LogUtil.m3785e("", e2);
                    }
                }
            }
        });
        RxSubscriptions.add(this.loginAndOutSubscribe);
    }

    public final void release() {
        ImageView thumb;
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            ViewParent parent = postVideoIjkPlayView != null ? postVideoIjkPlayView.getParent() : null;
            if (parent != null && (parent instanceof FrameLayout)) {
                PostVideoIjkPlayView postVideoIjkPlayView2 = this.ijkVideoView;
                if (postVideoIjkPlayView2 != null && postVideoIjkPlayView2.getCurrentPlayState() == 5) {
                    PostVideoIjkPlayView postVideoIjkPlayView3 = this.ijkVideoView;
                    String currentUrl = postVideoIjkPlayView3 != null ? postVideoIjkPlayView3.getCurrentUrl() : null;
                    PostVideoIjkPlayView postVideoIjkPlayView4 = this.ijkVideoView;
                    ProgressManager progressManger = postVideoIjkPlayView4 != null ? postVideoIjkPlayView4.getProgressManger() : null;
                    if (currentUrl != null && progressManger != null) {
                        progressManger.saveProgress(currentUrl, 0L);
                    }
                }
                PostVideoIjkPlayView postVideoIjkPlayView5 = this.ijkVideoView;
                if (postVideoIjkPlayView5 != null) {
                    postVideoIjkPlayView5.stopPlayback();
                }
                NewPostVideoController newPostVideoController = this.controller;
                if (newPostVideoController != null && (thumb = newPostVideoController.getThumb()) != null) {
                    thumb.setImageBitmap(null);
                }
                FrameLayout frameLayout = (FrameLayout) parent;
                frameLayout.removeAllViews();
                frameLayout.setVisibility(8);
                ViewParent parent2 = frameLayout.getParent();
                if (parent2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup");
                }
                ViewGroup viewGroup = (ViewGroup) parent2;
                if (viewGroup != null) {
                    ImageView imageView = (ImageView) viewGroup.findViewById(R.id.image_video_cove);
                    ImageView imageView2 = (ImageView) viewGroup.findViewById(R.id.image_video_play);
                    TextView textView = (TextView) viewGroup.findViewById(R.id.tv_video_time);
                    if (imageView != null) {
                        imageView.setVisibility(0);
                    }
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                    if (textView != null) {
                        textView.setVisibility(0);
                    }
                }
            }
        }
        IpfsUtil.syncStopAll();
    }

    public final void autoPostVideoPlay(RecyclerView recyclerView, boolean z) {
        if (recyclerView == null || !z) {
            return;
        }
        AsyncKt.doAsync$default(this, null, new PostEvenOneTabVideoListManger$autoPostVideoPlay$1(this, recyclerView), 1, null);
    }

    public final void pressPostVideoPlay(ImageView imageView, FrameLayout frameLayout, ImageView imageView2, TextView textView, String videoPlayType) {
        Context context;
        Intrinsics.checkParameterIsNotNull(videoPlayType, "videoPlayType");
        Drawable drawable = null;
        PostList postList = (PostList) (frameLayout != null ? frameLayout.getTag(R.id.video_list_item_id) : null);
        if (TextUtils.isEmpty(postList != null ? postList.getSecVideoUrl() : null)) {
            return;
        }
        IjkVideoView ijkPlayInstance = getIjkPlayInstance();
        if (ijkPlayInstance == null && imageView != null && (context = imageView.getContext()) != null) {
            recoverInitPlay(context);
        }
        if (ijkPlayInstance == null) {
            return;
        }
        release();
        if (imageView != null) {
            drawable = imageView.getDrawable();
        }
        if (drawable != null) {
            setThumbImage(imageView.getDrawable());
        }
        if (frameLayout != null) {
            frameLayout.setVisibility(0);
        }
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (textView != null) {
            textView.setVisibility(8);
        }
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        if (frameLayout != null) {
            frameLayout.addView(ijkPlayInstance);
        }
        if (postList == null) {
            return;
        }
        startVideo(postList, videoPlayType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setThumbImage(Drawable drawable) {
        ImageView thumb;
        ImageView thumb2;
        if (drawable != null) {
            NewPostVideoController newPostVideoController = this.controller;
            if (newPostVideoController != null && (thumb2 = newPostVideoController.getThumb()) != null) {
                thumb2.setVisibility(0);
            }
            NewPostVideoController newPostVideoController2 = this.controller;
            if (newPostVideoController2 == null || (thumb = newPostVideoController2.getThumb()) == null) {
                return;
            }
            thumb.setImageDrawable(drawable);
        }
    }

    public final void onReleaseVideoManger() {
        release();
        PostVideoIjkPlayView postVideoIjkPlayView = this.ijkVideoView;
        if (postVideoIjkPlayView != null) {
            postVideoIjkPlayView.release();
        }
        this.ijkVideoView = null;
        this.controller = null;
        this.postList = null;
        this.isVideoResume = true;
        this.subLookTime = true;
        this.isPostPay = true;
        this.isAlreadyUpData = false;
        this.isAlreadyUpVideoHalf = false;
        this.isAlreadyUpVideoCom = false;
        RxSubscriptions.remove(this.loginAndOutSubscribe);
    }
}
