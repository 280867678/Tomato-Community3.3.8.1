package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.adapter.PostDetailCommentAdapter;
import com.one.tomato.adapter.PostDetailImgUploadAdapter;
import com.one.tomato.dialog.ImageVerifyDialog;
import com.one.tomato.dialog.PostRewardDialog;
import com.one.tomato.dialog.ShowUpPopWindow;
import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.entity.event.PostCollectOrThumbEvent;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack;
import com.one.tomato.mvp.p080ui.post.presenter.NewCommentPresenter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.emotion.EmotionEditText;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.emotion.PostKeyboard;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.CommentCheckUtil;
import com.one.tomato.widget.image.MNGestureView;
import com.p096sj.emoji.EmojiBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Standard;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;

/* compiled from: NewDetailViewPagerRecyclerFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment */
/* loaded from: classes3.dex */
public final class NewDetailViewPagerRecyclerFragment extends MvpBaseRecyclerViewFragment<IPostCommentContact$IPostCommentView, NewCommentPresenter, PostDetailCommentAdapter, CommentList> implements IPostCommentContact$IPostCommentView, IPostCommentContact, PostDetailSeekBarCallBack {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String businessType;
    private Disposable collectSubscribe;
    private int currentIndex;
    private EmotionEditText et_input;
    private Disposable foucsSubscribe;
    private FrameLayout fragment_video_controller;
    private NewCommentHeadView headView;
    private ImageVerifyDialog imageVerifyDialog;
    private boolean isDropDown;
    private int isLouzh;
    private boolean isOnePlayed;
    private boolean isScrollTop;
    private boolean isScrolled;
    private PostList itemData;
    private ImageView iv_choose_img;
    private ImageView iv_emotion;
    private LevelBean levelBean;
    private int maxHeight;
    private PostDetailCallBack postDetailCallBack;
    private RecyclerView recyclerView_upload;
    private int tempCommentId;
    private TextView tv_comment_bottom_num;
    private PostDetailImgUploadAdapter uploadAdapter;
    private int currentVideoHorizontalandVertical = 1;
    private String timeSortType = "DESC";

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.new_post_detail_viewpager_fragment;
    }

    public static final /* synthetic */ NewCommentPresenter access$getMPresenter$p(NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment) {
        return (NewCommentPresenter) newDetailViewPagerRecyclerFragment.getMPresenter();
    }

    public final PostDetailCallBack getPostDetailCallBack() {
        return this.postDetailCallBack;
    }

    public final void setPostDetailCallBack(PostDetailCallBack postDetailCallBack) {
        this.postDetailCallBack = postDetailCallBack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final EmotionEditText getEt_input() {
        return this.et_input;
    }

    /* compiled from: NewDetailViewPagerRecyclerFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewDetailViewPagerRecyclerFragment getInstance(PostList list, int i, boolean z, String str) {
            Intrinsics.checkParameterIsNotNull(list, "list");
            NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment = new NewDetailViewPagerRecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data_list_post", list);
            bundle.putInt("current_index", i);
            bundle.putBoolean("scroll_comment_top", z);
            bundle.putString("business", str);
            newDetailViewPagerRecyclerFragment.setArguments(bundle);
            return newDetailViewPagerRecyclerFragment;
        }
    }

    public final void setItemData(PostList postList) {
        Intrinsics.checkParameterIsNotNull(postList, "postList");
        this.itemData = postList;
        NewCommentHeadView newCommentHeadView = this.headView;
        if (newCommentHeadView != null) {
            newCommentHeadView.refreshData(postList);
        }
        initDataToView();
        setVideoState();
    }

    public final PostList getItemData() {
        return this.itemData;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewCommentPresenter mo6441createPresenter() {
        return new NewCommentPresenter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        setState(getGET_LIST_REFRESH());
        setPageNo(1);
        setRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        setRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        throw new Standard("An operation is not implemented: not implemented");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public PostDetailCommentAdapter mo6434createAdapter() {
        return new PostDetailCommentAdapter(getMContext(), getRecyclerView(), this.itemData);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        if (this.currentIndex == 1) {
            setUserVisibleHint(true);
        }
    }

    private final void cancelLoading() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_loading);
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.refresh_loading);
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        PostList postList;
        super.onLazyLoad();
        refreshData();
        PostList postList2 = this.itemData;
        if ((postList2 == null || postList2.getIfAllFlag() != 0) && ((postList = this.itemData) == null || postList.getMsgType() != 1)) {
            return;
        }
        refreshCurrentPage();
    }

    private final void refreshData() {
        NewCommentHeadView newCommentHeadView;
        List<CommentList> data;
        cancelLoading();
        videoIsAutoPlay();
        initDataToView();
        PostDetailCommentAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.removeHeaderView(this.headView);
        }
        Context mContext = getMContext();
        if (mContext != null) {
            this.headView = new NewCommentHeadView(mContext, this.itemData, this, this);
            if ((Intrinsics.areEqual(this.businessType, "review_post") || Intrinsics.areEqual(this.businessType, "review_post_pre")) && (newCommentHeadView = this.headView) != null) {
                newCommentHeadView.setIsReviewPost(true);
            }
            NewCommentHeadView newCommentHeadView2 = this.headView;
            if (newCommentHeadView2 != null) {
                newCommentHeadView2.setItemToViewCallBack(this);
            }
            PostDetailCommentAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                adapter2.addHeaderView(this.headView);
            }
            setVideoState();
            PostDetailCommentAdapter adapter3 = getAdapter();
            if (adapter3 != null && (data = adapter3.getData()) != null) {
                data.clear();
            }
            PostDetailCommentAdapter adapter4 = getAdapter();
            if (adapter4 != null) {
                adapter4.setEnableLoadMore(false);
            }
            PostDetailCommentAdapter adapter5 = getAdapter();
            if (adapter5 != null) {
                adapter5.addData((PostDetailCommentAdapter) new CommentList(1, 0));
            }
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView == null) {
                return;
            }
            recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$refreshData$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewDetailViewPagerRecyclerFragment.this.refresh();
                }
            }, 200L);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onVisibleLoad() {
        PostDetailCallBack postDetailCallBack;
        super.onVisibleLoad();
        if (!getUserVisibleHint() || (postDetailCallBack = this.postDetailCallBack) == null || !postDetailCallBack.callCurrentIndex(this)) {
            return;
        }
        videoIsAutoPlay();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void rePlaceLazyLoad() {
        PostDetailCallBack postDetailCallBack;
        super.rePlaceLazyLoad();
        if (!getUserVisibleHint() || (postDetailCallBack = this.postDetailCallBack) == null || !postDetailCallBack.callCurrentIndex(this)) {
            return;
        }
        videoIsAutoPlay();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onInvisibleLoad() {
        super.onInvisibleLoad();
        reloadResetState();
    }

    public final void videoIsAutoPlay() {
        PostDetailCallBack postDetailCallBack;
        PostEvenOneTabVideoListManger callPostVideoManger;
        PostDetailCallBack postDetailCallBack2;
        PostEvenOneTabVideoListManger callPostVideoManger2;
        PostEvenOneTabVideoListManger callPostVideoManger3;
        PostEvenOneTabVideoListManger callPostVideoManger4;
        final PostList postList = this.itemData;
        if (postList != null) {
            PostDetailCallBack postDetailCallBack3 = this.postDetailCallBack;
            if (postDetailCallBack3 != null && (callPostVideoManger4 = postDetailCallBack3.callPostVideoManger()) != null) {
                callPostVideoManger4.onReleaseVideoManger();
            }
            if (postList.getPostType() == 2) {
                PostDetailCallBack postDetailCallBack4 = this.postDetailCallBack;
                if (postDetailCallBack4 != null && (callPostVideoManger3 = postDetailCallBack4.callPostVideoManger()) != null) {
                    Context mContext = getMContext();
                    if (mContext == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    callPostVideoManger3.recoverInitPlay(mContext);
                }
                if ((Intrinsics.areEqual(this.businessType, "review_post") || Intrinsics.areEqual(this.businessType, "review_post_pre")) && (postDetailCallBack = this.postDetailCallBack) != null && (callPostVideoManger = postDetailCallBack.callPostVideoManger()) != null) {
                    callPostVideoManger.setIsReviewPost(true);
                }
                if (this.currentVideoHorizontalandVertical == 2 && (postDetailCallBack2 = this.postDetailCallBack) != null && (callPostVideoManger2 = postDetailCallBack2.callPostVideoManger()) != null) {
                    callPostVideoManger2.setPostDetailSeekBarCallBack(this);
                }
                reloadResetState();
            }
            FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout == null) {
                return;
            }
            frameLayout.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$videoIsAutoPlay$$inlined$let$lambda$1
                /* JADX WARN: Code restructure failed: missing block: B:3:0x000a, code lost:
                    r0 = r2.itemData;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    int i;
                    PostDetailCallBack postDetailCallBack5;
                    PostEvenOneTabVideoListManger callPostVideoManger5;
                    PostEvenOneTabVideoListManger callPostVideoManger6;
                    PostList postList2;
                    boolean z;
                    PostEvenOneTabVideoListManger callPostVideoManger7;
                    IjkVideoView ijkPlayInstance;
                    PostEvenOneTabVideoListManger callPostVideoManger8;
                    i = this.currentIndex;
                    if (i == 1 && postList2 != null && postList2.getPostType() == 2) {
                        PostDetailCallBack postDetailCallBack6 = this.getPostDetailCallBack();
                        if ((postDetailCallBack6 != null ? postDetailCallBack6.callPostVideoPostion() : 0L) > 0) {
                            z = this.isOnePlayed;
                            if (!z) {
                                PostDetailCallBack postDetailCallBack7 = this.getPostDetailCallBack();
                                if (postDetailCallBack7 != null && (callPostVideoManger8 = postDetailCallBack7.callPostVideoManger()) != null) {
                                    callPostVideoManger8.pressPostVideoPlay((ImageView) this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) this._$_findCachedViewById(R$id.image_video_play), (TextView) this._$_findCachedViewById(R$id.tv_video_time), "帖子詳情");
                                }
                                PostDetailCallBack postDetailCallBack8 = this.getPostDetailCallBack();
                                if (postDetailCallBack8 != null && (callPostVideoManger7 = postDetailCallBack8.callPostVideoManger()) != null && (ijkPlayInstance = callPostVideoManger7.getIjkPlayInstance()) != null) {
                                    PostDetailCallBack postDetailCallBack9 = this.getPostDetailCallBack();
                                    if (postDetailCallBack9 == null) {
                                        Intrinsics.throwNpe();
                                        throw null;
                                    }
                                    ijkPlayInstance.seekTo(postDetailCallBack9.callPostVideoPostion());
                                }
                                this.isOnePlayed = true;
                                return;
                            }
                        }
                    }
                    if (PostList.this.getPostType() != 2) {
                        PostDetailCallBack postDetailCallBack10 = this.getPostDetailCallBack();
                        if (postDetailCallBack10 == null || (callPostVideoManger6 = postDetailCallBack10.callPostVideoManger()) == null) {
                            return;
                        }
                        callPostVideoManger6.onReleaseVideoManger();
                    } else if (!Intrinsics.areEqual(PreferencesUtil.getInstance().getString("video_auto"), "1") || (postDetailCallBack5 = this.getPostDetailCallBack()) == null || (callPostVideoManger5 = postDetailCallBack5.callPostVideoManger()) == null) {
                    } else {
                        callPostVideoManger5.pressPostVideoPlay((ImageView) this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) this._$_findCachedViewById(R$id.image_video_play), (TextView) this._$_findCachedViewById(R$id.tv_video_time), "帖子詳情");
                    }
                }
            }, 100L);
        }
    }

    public final void refreshCurrentPage() {
        Intent intent;
        Bundle extras;
        PostList postList = this.itemData;
        if (postList != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(postList.getId()));
            FragmentActivity activity = getActivity();
            String str = (activity == null || (intent = activity.getIntent()) == null || (extras = intent.getExtras()) == null) ? false : extras.getBoolean("isOfflinePost") ? "/app/messageCenter/queryOfflineArticle" : "/app/article/recommend/detail";
            NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
            if (newCommentPresenter == null) {
                return;
            }
            newCommentPresenter.requestSinglePostDetail(str, linkedHashMap);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        Bundle arguments = getArguments();
        String str = null;
        this.itemData = arguments != null ? (PostList) arguments.getParcelable("data_list_post") : null;
        this.currentIndex = arguments != null ? arguments.getInt("current_index") : 1;
        this.isScrollTop = arguments != null ? arguments.getBoolean("scroll_comment_top") : false;
        if (arguments != null) {
            str = arguments.getString("business");
        }
        this.businessType = str;
        super.initView();
        addClick();
        addListener();
        PostDetailCommentAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setItemToViewCallBack(this);
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_loading);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageLoaderUtil.loadNormalDrawableGif(getContext(), (ImageView) _$_findCachedViewById(R$id.refresh_loading), R.drawable.refresh_empty_load_gif);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void initRecyclerView() {
        super.initRecyclerView();
        configLinearLayoutVerticalManager(getRecyclerView());
    }

    private final void addListener() {
        initKeyboard();
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new NewDetailViewPagerRecyclerFragment$addListener$1(this));
        }
        this.foucsSubscribe = RxBus.getDefault().toObservable(MemberFocusEvent.class).subscribe(new Consumer<MemberFocusEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addListener$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(MemberFocusEvent memberFocusEvent) {
                PostList postList;
                PostList postList2;
                NewCommentHeadView newCommentHeadView;
                PostList postList3;
                if (memberFocusEvent instanceof MemberFocusEvent) {
                    int i = memberFocusEvent.f1748id;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList == null || i != postList.getMemberId()) {
                        return;
                    }
                    int i2 = memberFocusEvent.followFlag;
                    if (i2 == 1) {
                        TextView textView = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_foucs);
                        if (textView != null) {
                            textView.setText(R.string.common_focus_y);
                        }
                    } else {
                        TextView textView2 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_foucs);
                        if (textView2 != null) {
                            textView2.setText(R.string.common_focus_n_add);
                        }
                    }
                    postList2 = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList2 != null) {
                        postList2.setIsAttention(i2);
                    }
                    newCommentHeadView = NewDetailViewPagerRecyclerFragment.this.headView;
                    if (newCommentHeadView == null) {
                        return;
                    }
                    postList3 = NewDetailViewPagerRecyclerFragment.this.itemData;
                    newCommentHeadView.refreshData(postList3);
                }
            }
        });
        this.collectSubscribe = RxBus.getDefault().toObservable(PostCollectOrThumbEvent.class).subscribe(new Consumer<PostCollectOrThumbEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addListener$3
            @Override // io.reactivex.functions.Consumer
            public final void accept(PostCollectOrThumbEvent postCollectOrThumbEvent) {
                PostList postList;
                Context mContext;
                Context mContext2;
                if (postCollectOrThumbEvent instanceof PostCollectOrThumbEvent) {
                    PostList postList2 = postCollectOrThumbEvent.postList;
                    if (!(postList2 instanceof PostList)) {
                        return;
                    }
                    int id = postList2.getId();
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList == null || id != postList.getId()) {
                        return;
                    }
                    if (postList2.getIsFavor() == 1) {
                        ImageView imageView = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_collect);
                        if (imageView != null) {
                            imageView.setImageResource(R.drawable.icon_post_collected);
                        }
                        TextView textView = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_collect);
                        if (textView == null) {
                            return;
                        }
                        textView.setText(AppUtil.getString(R.string.post_collect_s));
                        return;
                    }
                    TextView textView2 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_collect);
                    if (textView2 != null) {
                        textView2.setText(AppUtil.getString(R.string.post_collect));
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.relative_video_top_bar);
                    if (relativeLayout != null && relativeLayout.getVisibility() == 0) {
                        ImageView imageView2 = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_collect);
                        if (imageView2 == null) {
                            return;
                        }
                        mContext2 = NewDetailViewPagerRecyclerFragment.this.getMContext();
                        if (mContext2 != null) {
                            imageView2.setImageDrawable(ContextCompat.getDrawable(mContext2, R.drawable.icon_new_post_video_white_collect));
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    ImageView imageView3 = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_collect);
                    if (imageView3 == null) {
                        return;
                    }
                    mContext = NewDetailViewPagerRecyclerFragment.this.getMContext();
                    if (mContext != null) {
                        imageView3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_new_post_video_collect));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        });
    }

    private final void initKeyboard() {
        PostKeyboard postKeyboard;
        LinearLayout linear_bottom_post_detail;
        RelativeLayout rl_comment;
        PostKeyboard postKeyboard2 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard2 != null) {
            postKeyboard2.setAdapter(EmotionUtil.getCommonAdapter(getContext(), new EmoticonClickListener<Object>() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$initKeyboard$1
                @Override // sj.keyboard.interfaces.EmoticonClickListener
                public void onEmoticonClick(Object obj, int i, boolean z) {
                    String content;
                    Editable editable = null;
                    EmotionEditText emotionEditText = null;
                    if (z) {
                        PostKeyboard postKeyboard3 = (PostKeyboard) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.post_keyboard);
                        if (postKeyboard3 != null) {
                            emotionEditText = postKeyboard3.getEtChat();
                        }
                        EmotionUtil.delClick(emotionEditText);
                    } else if (obj == null) {
                    } else {
                        if (obj instanceof EmojiBean) {
                            content = ((EmojiBean) obj).emoji;
                        } else {
                            content = obj instanceof EmoticonEntity ? ((EmoticonEntity) obj).getContent() : null;
                        }
                        if (TextUtils.isEmpty(content)) {
                            return;
                        }
                        EmotionEditText et_input = NewDetailViewPagerRecyclerFragment.this.getEt_input();
                        int selectionStart = et_input != null ? et_input.getSelectionStart() : 0;
                        EmotionEditText et_input2 = NewDetailViewPagerRecyclerFragment.this.getEt_input();
                        if (et_input2 != null) {
                            editable = et_input2.getText();
                        }
                        if (editable == null) {
                            return;
                        }
                        editable.insert(selectionStart, content);
                    }
                }
            }));
        }
        PostKeyboard postKeyboard3 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        TextView textView = null;
        this.recyclerView_upload = postKeyboard3 != null ? postKeyboard3.getRecyclerView_upload() : null;
        RecyclerView recyclerView = this.recyclerView_upload;
        if (recyclerView != null) {
            recyclerView.setVisibility(8);
        }
        PostKeyboard postKeyboard4 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        this.iv_emotion = postKeyboard4 != null ? postKeyboard4.getIv_emotion() : null;
        ImageView imageView = this.iv_emotion;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        PostKeyboard postKeyboard5 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        this.et_input = postKeyboard5 != null ? postKeyboard5.getEtChat() : null;
        PostKeyboard postKeyboard6 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        this.iv_choose_img = postKeyboard6 != null ? postKeyboard6.getIv_choose_img() : null;
        ImageView imageView2 = this.iv_choose_img;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
        PostKeyboard postKeyboard7 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard7 != null && (rl_comment = postKeyboard7.getRl_comment()) != null) {
            rl_comment.setVisibility(8);
        }
        PostKeyboard postKeyboard8 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard8 != null) {
            postKeyboard8.getIv_comment_tip();
        }
        PostKeyboard postKeyboard9 = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard9 != null) {
            textView = postKeyboard9.getTv_comment_bottom_num();
        }
        this.tv_comment_bottom_num = textView;
        if ((Intrinsics.areEqual("review_post", this.businessType) || Intrinsics.areEqual("review_post_pre", this.businessType)) && (postKeyboard = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard)) != null && (linear_bottom_post_detail = postKeyboard.getLinear_bottom_post_detail()) != null) {
            linear_bottom_post_detail.setVisibility(8);
        }
        setListener();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        this.maxHeight = ((int) DisplayMetricsUtils.getHeight()) - ((int) DisplayMetricsUtils.dp2px(53.0f));
    }

    public final void setVideoState() {
        MNGestureView mNView;
        MNGestureView mNView2;
        AppBarLayout appBarLayout;
        if (this.currentVideoHorizontalandVertical == 2 && (appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout)) != null) {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$setVideoState$1
                @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
                public void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
                    NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment = NewDetailViewPagerRecyclerFragment.this;
                    newDetailViewPagerRecyclerFragment.appBarOffset(i, appBarLayout2, (RelativeLayout) newDetailViewPagerRecyclerFragment._$_findCachedViewById(R$id.image_back_ground));
                }
            });
        }
        PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
        if (postDetailCallBack != null && (mNView2 = postDetailCallBack.getMNView()) != null) {
            mNView2.isAppBarEx = this.isDropDown;
        }
        PostDetailCallBack postDetailCallBack2 = this.postDetailCallBack;
        Boolean valueOf = (postDetailCallBack2 == null || (mNView = postDetailCallBack2.getMNView()) == null) ? null : Boolean.valueOf(mNView.isAppBarEx);
        LogUtil.m3787d("assas", "appBarEx =" + valueOf);
    }

    private final void addClick() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_video_play);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null) {
                        return;
                    }
                    callPostVideoManger.pressPostVideoPlay((ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_play), (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.tv_video_time), "帖子詳情");
                }
            });
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FragmentActivity activity = NewDetailViewPagerRecyclerFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_foucs);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    PostList postList2;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    int i = 1;
                    if (postList != null && postList.getIsAttention() == 1) {
                        i = 0;
                    }
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack != null) {
                        postList2 = NewDetailViewPagerRecyclerFragment.this.itemData;
                        postDetailCallBack.postAttetion(postList2, i);
                    }
                }
            });
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.iv_head);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    Context mContext;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList != null) {
                        if (!postList.isAd()) {
                            mContext = NewDetailViewPagerRecyclerFragment.this.getMContext();
                            if (mContext == null) {
                                return;
                            }
                            NewMyHomePageActivity.Companion.startActivity(mContext, postList.getMemberId());
                            return;
                        }
                        PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                        if (postDetailCallBack == null) {
                            return;
                        }
                        postDetailCallBack.callJumpAd(postList);
                    }
                }
            });
        }
        ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
        if (imageView4 != null) {
            imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FragmentActivity activity = NewDetailViewPagerRecyclerFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            });
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_menu);
        if (imageView5 != null) {
            imageView5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack != null) {
                        postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                        postDetailCallBack.callFragmentToActVideoMenu(postList);
                    }
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_conmment);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewDetailViewPagerRecyclerFragment.this.clickCommentScrollTop();
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_zan);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    PostList postList4;
                    PostList postList5;
                    PostList postList6;
                    PostList postList7;
                    PostList postList8;
                    Context mContext;
                    Context mContext2;
                    PostList postList9;
                    PostList postList10;
                    PostList postList11;
                    PostList postList12;
                    PostList postList13;
                    PostList postList14;
                    PostList postList15;
                    PostList postList16;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList != null) {
                        postList2 = NewDetailViewPagerRecyclerFragment.this.itemData;
                        if (postList2 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (AppUtil.isFastClick(postList2.getId(), ConstantUtils.MAX_ITEM_NUM)) {
                            ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                        } else {
                            PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                            if (postDetailCallBack != null) {
                                postList16 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                postDetailCallBack.callTumb(postList16);
                            }
                            postList3 = NewDetailViewPagerRecyclerFragment.this.itemData;
                            if (postList3 == null || postList3.getIsThumbUp() != 0) {
                                postList4 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                if (postList4 == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                                postList5 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                if (postList5 != null) {
                                    postList4.setGoodNum(postList5.getGoodNum() - 1);
                                    postList6 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                    if (postList6 != null) {
                                        if (postList6.getGoodNum() >= 10000) {
                                            TextView textView2 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_zan);
                                            if (textView2 != null) {
                                                StringBuilder sb = new StringBuilder();
                                                postList9 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                                if (postList9 == null) {
                                                    Intrinsics.throwNpe();
                                                    throw null;
                                                }
                                                sb.append(FormatUtil.formatOne(Double.valueOf(postList9.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)));
                                                sb.append((char) 19975);
                                                textView2.setText(sb.toString());
                                            }
                                        } else {
                                            TextView textView3 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_zan);
                                            if (textView3 != null) {
                                                postList7 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                                if (postList7 == null) {
                                                    Intrinsics.throwNpe();
                                                    throw null;
                                                }
                                                textView3.setText(String.valueOf(postList7.getGoodNum()));
                                            }
                                        }
                                        postList8 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                        if (postList8 == null) {
                                            Intrinsics.throwNpe();
                                            throw null;
                                        }
                                        postList8.setIsThumbUp(0);
                                        RelativeLayout relativeLayout3 = (RelativeLayout) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.relative_video_top_bar);
                                        if (relativeLayout3 != null && relativeLayout3.getVisibility() == 0) {
                                            ImageView imageView6 = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_zan);
                                            if (imageView6 == null) {
                                                return;
                                            }
                                            mContext2 = NewDetailViewPagerRecyclerFragment.this.getMContext();
                                            if (mContext2 != null) {
                                                imageView6.setImageDrawable(ContextCompat.getDrawable(mContext2, R.drawable.icon_new_post_video_white_zan));
                                                return;
                                            } else {
                                                Intrinsics.throwNpe();
                                                throw null;
                                            }
                                        }
                                        ImageView imageView7 = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_zan);
                                        if (imageView7 == null) {
                                            return;
                                        }
                                        mContext = NewDetailViewPagerRecyclerFragment.this.getMContext();
                                        if (mContext != null) {
                                            imageView7.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_new_post_zan));
                                            return;
                                        } else {
                                            Intrinsics.throwNpe();
                                            throw null;
                                        }
                                    }
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            postList10 = NewDetailViewPagerRecyclerFragment.this.itemData;
                            if (postList10 != null) {
                                postList10.setIsThumbUp(1);
                                postList11 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                if (postList11 == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                                postList12 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                if (postList12 != null) {
                                    postList11.setGoodNum(postList12.getGoodNum() + 1);
                                    postList13 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                    if (postList13 == null) {
                                        Intrinsics.throwNpe();
                                        throw null;
                                    }
                                    if (postList13.getGoodNum() >= 10000) {
                                        TextView textView4 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_zan);
                                        if (textView4 != null) {
                                            StringBuilder sb2 = new StringBuilder();
                                            postList15 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                            if (postList15 == null) {
                                                Intrinsics.throwNpe();
                                                throw null;
                                            }
                                            sb2.append(FormatUtil.formatOne(Double.valueOf(postList15.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)));
                                            sb2.append((char) 19975);
                                            textView4.setText(sb2.toString());
                                        }
                                    } else {
                                        TextView textView5 = (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.text_video_zan);
                                        if (textView5 != null) {
                                            postList14 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                            if (postList14 == null) {
                                                Intrinsics.throwNpe();
                                                throw null;
                                            }
                                            textView5.setText(String.valueOf(postList14.getGoodNum()));
                                        }
                                    }
                                    ImageView imageView8 = (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_zan);
                                    if (imageView8 == null) {
                                        return;
                                    }
                                    imageView8.setImageResource(R.drawable.icon_new_post_zan_ok);
                                    return;
                                }
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_collect);
        if (relativeLayout3 != null) {
            relativeLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList != null) {
                        postList2 = NewDetailViewPagerRecyclerFragment.this.itemData;
                        if (postList2 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (AppUtil.isFastClick(postList2.getId(), ConstantUtils.MAX_ITEM_NUM)) {
                            ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                        } else {
                            PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                            if (postDetailCallBack == null) {
                                return;
                            }
                            postList3 = NewDetailViewPagerRecyclerFragment.this.itemData;
                            postDetailCallBack.callCollect(postList3);
                        }
                    }
                }
            });
        }
        RelativeLayout relativeLayout4 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_reward);
        if (relativeLayout4 != null) {
            relativeLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList;
                    PostRewardDialog.Companion companion = PostRewardDialog.Companion;
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    companion.showDialog(postList).show(NewDetailViewPagerRecyclerFragment.this.getChildFragmentManager(), "PostRewardDialog");
                }
            });
        }
        ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_up);
        if (imageView6 != null) {
            imageView6.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$11
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    Context mContext;
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    PostList postList4;
                    PostList postList5;
                    PostList postList6;
                    int i;
                    mContext = NewDetailViewPagerRecyclerFragment.this.getMContext();
                    ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(mContext);
                    postList = NewDetailViewPagerRecyclerFragment.this.itemData;
                    if (postList == null || postList.getMemberIsOriginal() != 1) {
                        postList2 = NewDetailViewPagerRecyclerFragment.this.itemData;
                        if (postList2 == null || postList2.getUpLevel() != 1) {
                            postList3 = NewDetailViewPagerRecyclerFragment.this.itemData;
                            if (postList3 == null || postList3.getUpLevel() != 2) {
                                postList4 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                if (postList4 == null || postList4.getUpLevel() != 3) {
                                    postList5 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                    if (postList5 == null || postList5.getUpLevel() != 4) {
                                        postList6 = NewDetailViewPagerRecyclerFragment.this.itemData;
                                        i = (postList6 == null || postList6.getUpLevel() != 5) ? 0 : 13;
                                    } else {
                                        i = 12;
                                    }
                                } else {
                                    i = 11;
                                }
                            } else {
                                i = 9;
                            }
                        } else {
                            i = 8;
                        }
                    } else {
                        i = 7;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    showUpPopWindow.showDown(it2, i);
                }
            });
        }
        ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_porter);
        if (imageView7 != null) {
            imageView7.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addClick$12
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(NewDetailViewPagerRecyclerFragment.this.getContext());
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    showUpPopWindow.showDown(it2, 6);
                }
            });
        }
        controllerClickListener();
    }

    public final void clickCommentScrollTop() {
        AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setExpanded(false);
        }
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                newCommentPresenter.smoothMoveToPosition(recyclerView, 1);
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
    }

    public final RelativeLayout getShareViewFormFragment() {
        return (RelativeLayout) _$_findCachedViewById(R$id.image_back_ground);
    }

    public final void appBarOffset(int i, AppBarLayout appBarLayout, RelativeLayout relativeLayout) {
        MNGestureView mNView;
        MNGestureView mNView2;
        Boolean bool = null;
        Integer valueOf = appBarLayout != null ? Integer.valueOf(appBarLayout.getTotalScrollRange()) : null;
        PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
        boolean z = false;
        if (postDetailCallBack != null && (mNView2 = postDetailCallBack.getMNView()) != null) {
            mNView2.isAppBarEx = i != 0;
        }
        if (i != 0) {
            z = true;
        }
        this.isDropDown = z;
        StringBuilder sb = new StringBuilder();
        sb.append("滑動AppBarLayout---------------------------------------mn_view?.isAppBarEx------");
        PostDetailCallBack postDetailCallBack2 = this.postDetailCallBack;
        if (postDetailCallBack2 != null && (mNView = postDetailCallBack2.getMNView()) != null) {
            bool = Boolean.valueOf(mNView.isAppBarEx);
        }
        sb.append(bool);
        LogUtil.m3787d("yan", sb.toString());
        changeUI(i);
        if (valueOf != null) {
            valueOf.intValue();
            float abs = Math.abs(i / ((valueOf.intValue() + ((int) DisplayMetricsUtils.get16To9Height())) - DisplayMetricsUtils.dp2px(0.5f)));
            if (abs < 0.0d) {
                return;
            }
            LogUtil.m3787d("yan", "滑動AppBarLayout當前的偏移百分比------" + abs);
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            if (imageView != null) {
                imageView.setScaleY(1 - abs);
            }
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            if (imageView2 != null) {
                imageView2.setScaleX(1 - abs);
            }
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
            if (imageView3 != null) {
                imageView3.setScaleY(1 - abs);
            }
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
            if (imageView4 != null) {
                imageView4.setScaleX(1 - abs);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            if (textView != null) {
                textView.setScaleY(1 - abs);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            if (textView2 != null) {
                textView2.setScaleX(1 - abs);
            }
            FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout != null) {
                frameLayout.setScaleY(1 - abs);
            }
            FrameLayout frameLayout2 = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout2 == null) {
                return;
            }
            frameLayout2.setScaleX(1 - abs);
        }
    }

    public final void changeUI(int i) {
        ImageView imageView;
        ImageView imageView2;
        if (i == 0) {
            if (this.currentVideoHorizontalandVertical == 2) {
                videoControllVisiby(false);
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_person);
            if (relativeLayout != null) {
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    Unit unit = Unit.INSTANCE;
                }
            }
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
            if (imageView3 != null) {
                imageView3.setVisibility(0);
            }
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_time);
            if (textView != null) {
                Context mContext2 = getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView.setTextColor(ContextCompat.getColor(mContext2, R.color.white));
                    Unit unit2 = Unit.INSTANCE;
                }
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView2 != null) {
                Context mContext3 = getMContext();
                if (mContext3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView2.setTextColor(ContextCompat.getColor(mContext3, R.color.white));
                    Unit unit3 = Unit.INSTANCE;
                }
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_name);
            if (textView3 != null) {
                Context mContext4 = getMContext();
                if (mContext4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView3.setTextColor(ContextCompat.getColor(mContext4, R.color.white));
                    Unit unit4 = Unit.INSTANCE;
                }
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView4 != null) {
                Context mContext5 = getMContext();
                if (mContext5 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView4.setBackground(ContextCompat.getDrawable(mContext5, R.drawable.common_shape_stroke_corner5_white_divider));
            }
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
            if (imageView4 != null) {
                Context mContext6 = getMContext();
                if (mContext6 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    imageView4.setImageDrawable(ContextCompat.getDrawable(mContext6, R.drawable.icon_back_white));
                    Unit unit5 = Unit.INSTANCE;
                }
            }
            ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_menu);
            if (imageView5 != null) {
                Context mContext7 = getMContext();
                if (mContext7 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    imageView5.setImageDrawable(ContextCompat.getDrawable(mContext7, R.drawable.post_detail_menu_white));
                    Unit unit6 = Unit.INSTANCE;
                }
            }
            AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
            Integer valueOf = appBarLayout != null ? Integer.valueOf(appBarLayout.getMeasuredHeight()) : null;
            if ((valueOf instanceof Integer) && valueOf.intValue() < this.maxHeight) {
                return;
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.linear_bottom_post_detail);
            if (linearLayout != null) {
                Context mContext8 = getMContext();
                if (mContext8 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    linearLayout.setBackgroundColor(ContextCompat.getColor(mContext8, R.color.black));
                    Unit unit7 = Unit.INSTANCE;
                }
            }
            ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_video_conmment);
            if (imageView6 != null) {
                Context mContext9 = getMContext();
                if (mContext9 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    imageView6.setImageDrawable(ContextCompat.getDrawable(mContext9, R.drawable.icon_new_post_video_whitre_conmment));
                    Unit unit8 = Unit.INSTANCE;
                }
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_video_conmment_num);
            if (textView5 != null) {
                Context mContext10 = getMContext();
                if (mContext10 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView5.setTextColor(ContextCompat.getColor(mContext10, R.color.white));
                    Unit unit9 = Unit.INSTANCE;
                }
            }
            PostList postList = this.itemData;
            Integer valueOf2 = postList != null ? Integer.valueOf(postList.getIsThumbUp()) : null;
            if (valueOf2 == null || valueOf2.intValue() == 0) {
                ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_video_zan);
                if (imageView7 != null) {
                    Context mContext11 = getMContext();
                    if (mContext11 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        imageView7.setImageDrawable(ContextCompat.getDrawable(mContext11, R.drawable.icon_new_post_video_white_zan));
                        Unit unit10 = Unit.INSTANCE;
                    }
                }
            } else if (valueOf2.intValue() == 1 && (imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_zan)) != null) {
                imageView2.setImageResource(R.drawable.icon_new_post_zan_ok);
                Unit unit11 = Unit.INSTANCE;
            }
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_video_zan);
            if (textView6 != null) {
                Context mContext12 = getMContext();
                if (mContext12 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView6.setTextColor(ContextCompat.getColor(mContext12, R.color.white));
                    Unit unit12 = Unit.INSTANCE;
                }
            }
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_video_collect);
            if (textView7 != null) {
                Context mContext13 = getMContext();
                if (mContext13 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView7.setTextColor(ContextCompat.getColor(mContext13, R.color.white));
                    Unit unit13 = Unit.INSTANCE;
                }
            }
            PostList postList2 = this.itemData;
            if (postList2 != null && postList2.getIsFavor() == 1) {
                ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
                if (imageView8 != null) {
                    imageView8.setImageResource(R.drawable.icon_post_collected);
                    Unit unit14 = Unit.INSTANCE;
                }
            } else {
                ImageView imageView9 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
                if (imageView9 != null) {
                    imageView9.setImageResource(R.drawable.icon_new_post_video_white_collect);
                    Unit unit15 = Unit.INSTANCE;
                }
            }
            ImageView imageView10 = (ImageView) _$_findCachedViewById(R$id.image_video_reward);
            if (imageView10 != null) {
                imageView10.setImageResource(R.drawable.post_reward_white);
                Unit unit16 = Unit.INSTANCE;
            }
            TextView textView8 = (TextView) _$_findCachedViewById(R$id.text_video_reward);
            if (textView8 != null) {
                Context mContext14 = getMContext();
                if (mContext14 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView8.setTextColor(ContextCompat.getColor(mContext14, R.color.white));
                    Unit unit17 = Unit.INSTANCE;
                }
            }
            EmotionEditText emotionEditText = this.et_input;
            if (emotionEditText != null) {
                Context mContext15 = getMContext();
                if (mContext15 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                emotionEditText.setBackground(ContextCompat.getDrawable(mContext15, R.drawable.shape_post_input_wthtie_comment));
            }
            EmotionEditText emotionEditText2 = this.et_input;
            if (emotionEditText2 == null) {
                return;
            }
            Context mContext16 = getMContext();
            if (mContext16 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            emotionEditText2.setHintTextColor(ContextCompat.getColor(mContext16, R.color.white));
            Unit unit18 = Unit.INSTANCE;
            return;
        }
        if (this.currentVideoHorizontalandVertical == 2) {
            videoControllVisiby(true);
        }
        ImageView imageView11 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
        if (imageView11 != null) {
            imageView11.setVisibility(8);
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
        if (relativeLayout3 != null) {
            relativeLayout3.setVisibility(8);
        }
        TextView textView9 = (TextView) _$_findCachedViewById(R$id.tv_time);
        if (textView9 != null) {
            Context mContext17 = getMContext();
            if (mContext17 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView9.setTextColor(ContextCompat.getColor(mContext17, R.color.text_light));
                Unit unit19 = Unit.INSTANCE;
            }
        }
        TextView textView10 = (TextView) _$_findCachedViewById(R$id.text_foucs);
        if (textView10 != null) {
            Context mContext18 = getMContext();
            if (mContext18 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView10.setTextColor(ContextCompat.getColor(mContext18, R.color.text_light));
                Unit unit20 = Unit.INSTANCE;
            }
        }
        TextView textView11 = (TextView) _$_findCachedViewById(R$id.tv_name);
        if (textView11 != null) {
            Context mContext19 = getMContext();
            if (mContext19 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView11.setTextColor(ContextCompat.getColor(mContext19, R.color.text_dark));
                Unit unit21 = Unit.INSTANCE;
            }
        }
        TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_foucs);
        if (textView12 != null) {
            Context mContext20 = getMContext();
            if (mContext20 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView12.setBackground(ContextCompat.getDrawable(mContext20, R.drawable.common_shape_stroke_corner5_disable));
        }
        ImageView imageView12 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
        if (imageView12 != null) {
            Context mContext21 = getMContext();
            if (mContext21 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView12.setImageDrawable(ContextCompat.getDrawable(mContext21, R.drawable.icon_back_black));
                Unit unit22 = Unit.INSTANCE;
            }
        }
        ImageView imageView13 = (ImageView) _$_findCachedViewById(R$id.image_menu);
        if (imageView13 != null) {
            Context mContext22 = getMContext();
            if (mContext22 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView13.setImageDrawable(ContextCompat.getDrawable(mContext22, R.drawable.post_detail_menu));
                Unit unit23 = Unit.INSTANCE;
            }
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.linear_bottom_post_detail);
        if (linearLayout2 != null) {
            Context mContext23 = getMContext();
            if (mContext23 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                linearLayout2.setBackgroundColor(ContextCompat.getColor(mContext23, R.color.white));
                Unit unit24 = Unit.INSTANCE;
            }
        }
        ImageView imageView14 = (ImageView) _$_findCachedViewById(R$id.image_video_conmment);
        if (imageView14 != null) {
            Context mContext24 = getMContext();
            if (mContext24 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView14.setImageDrawable(ContextCompat.getDrawable(mContext24, R.drawable.icon_new_post_conmment));
                Unit unit25 = Unit.INSTANCE;
            }
        }
        TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_video_conmment_num);
        if (textView13 != null) {
            Context mContext25 = getMContext();
            if (mContext25 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView13.setTextColor(ContextCompat.getColor(mContext25, R.color.text_dark));
                Unit unit26 = Unit.INSTANCE;
            }
        }
        PostList postList3 = this.itemData;
        Integer valueOf3 = postList3 != null ? Integer.valueOf(postList3.getIsThumbUp()) : null;
        if (valueOf3 == null || valueOf3.intValue() == 0) {
            ImageView imageView15 = (ImageView) _$_findCachedViewById(R$id.image_video_zan);
            if (imageView15 != null) {
                imageView15.setImageResource(R.drawable.icon_new_post_zan);
                Unit unit27 = Unit.INSTANCE;
            }
        } else if (valueOf3.intValue() == 1 && (imageView = (ImageView) _$_findCachedViewById(R$id.image_video_zan)) != null) {
            imageView.setImageResource(R.drawable.icon_new_post_zan_ok);
            Unit unit28 = Unit.INSTANCE;
        }
        TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_video_zan);
        if (textView14 != null) {
            Context mContext26 = getMContext();
            if (mContext26 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView14.setTextColor(ContextCompat.getColor(mContext26, R.color.text_dark));
                Unit unit29 = Unit.INSTANCE;
            }
        }
        TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_video_collect);
        if (textView15 != null) {
            Context mContext27 = getMContext();
            if (mContext27 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView15.setTextColor(ContextCompat.getColor(mContext27, R.color.text_dark));
                Unit unit30 = Unit.INSTANCE;
            }
        }
        PostList postList4 = this.itemData;
        if (postList4 != null && postList4.getIsFavor() == 1) {
            ImageView imageView16 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
            if (imageView16 != null) {
                imageView16.setImageResource(R.drawable.icon_post_collected);
                Unit unit31 = Unit.INSTANCE;
            }
        } else {
            ImageView imageView17 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
            if (imageView17 != null) {
                Context mContext28 = getMContext();
                if (mContext28 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    imageView17.setImageDrawable(ContextCompat.getDrawable(mContext28, R.drawable.icon_new_post_video_collect));
                    Unit unit32 = Unit.INSTANCE;
                }
            }
        }
        ImageView imageView18 = (ImageView) _$_findCachedViewById(R$id.image_video_reward);
        if (imageView18 != null) {
            imageView18.setImageResource(R.drawable.post_reward_black);
            Unit unit33 = Unit.INSTANCE;
        }
        TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_video_reward);
        if (textView16 != null) {
            Context mContext29 = getMContext();
            if (mContext29 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView16.setTextColor(ContextCompat.getColor(mContext29, R.color.text_dark));
                Unit unit34 = Unit.INSTANCE;
            }
        }
        EmotionEditText emotionEditText3 = this.et_input;
        if (emotionEditText3 != null) {
            Context mContext30 = getMContext();
            if (mContext30 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            emotionEditText3.setBackground(ContextCompat.getDrawable(mContext30, R.drawable.shape_post_input_comment));
        }
        EmotionEditText emotionEditText4 = this.et_input;
        if (emotionEditText4 == null) {
            return;
        }
        Context mContext31 = getMContext();
        if (mContext31 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        emotionEditText4.setHintTextColor(ContextCompat.getColor(mContext31, R.color.text_light));
        Unit unit35 = Unit.INSTANCE;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
    }

    public final void initDataToView() {
        ImageView imageView;
        ImageView imageView2;
        String str;
        ImageView imageView3;
        LevelBean levelBean = DBUtil.getLevelBean();
        Intrinsics.checkExpressionValueIsNotNull(levelBean, "DBUtil.getLevelBean()");
        this.levelBean = levelBean;
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.post_detail_bottom_bar);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        ImageView imageView4 = this.iv_choose_img;
        if (imageView4 != null) {
            imageView4.setVisibility(8);
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.rl_comment);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        View _$_findCachedViewById = _$_findCachedViewById(R$id.view_divider);
        if (_$_findCachedViewById != null) {
            _$_findCachedViewById.setVisibility(0);
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
        if (relativeLayout2 != null) {
            relativeLayout2.setVisibility(8);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.linear_bottom_post_detail);
        ViewGroup.LayoutParams layoutParams = null;
        String str2 = null;
        ViewGroup.LayoutParams layoutParams2 = null;
        if (linearLayout2 != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                linearLayout2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                Unit unit = Unit.INSTANCE;
            }
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_video_conmment);
        if (imageView5 != null) {
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView5.setImageDrawable(ContextCompat.getDrawable(mContext2, R.drawable.icon_new_post_conmment));
                Unit unit2 = Unit.INSTANCE;
            }
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_video_conmment_num);
        if (textView != null) {
            Context mContext3 = getMContext();
            if (mContext3 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext3, R.color.text_dark));
                Unit unit3 = Unit.INSTANCE;
            }
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_video_zan);
        if (textView2 != null) {
            Context mContext4 = getMContext();
            if (mContext4 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView2.setTextColor(ContextCompat.getColor(mContext4, R.color.text_dark));
                Unit unit4 = Unit.INSTANCE;
            }
        }
        ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_video_zan);
        if (imageView6 != null) {
            Context mContext5 = getMContext();
            if (mContext5 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView6.setImageDrawable(ContextCompat.getDrawable(mContext5, R.drawable.icon_new_post_zan));
                Unit unit5 = Unit.INSTANCE;
            }
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_video_collect);
        if (textView3 != null) {
            Context mContext6 = getMContext();
            if (mContext6 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView3.setTextColor(ContextCompat.getColor(mContext6, R.color.text_dark));
                Unit unit6 = Unit.INSTANCE;
            }
        }
        ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
        if (imageView7 != null) {
            Context mContext7 = getMContext();
            if (mContext7 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView7.setImageDrawable(ContextCompat.getDrawable(mContext7, R.drawable.icon_new_post_video_collect));
                Unit unit7 = Unit.INSTANCE;
            }
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_video_reward);
        if (textView4 != null) {
            Context mContext8 = getMContext();
            if (mContext8 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                textView4.setTextColor(ContextCompat.getColor(mContext8, R.color.text_dark));
                Unit unit8 = Unit.INSTANCE;
            }
        }
        ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_video_reward);
        if (imageView8 != null) {
            Context mContext9 = getMContext();
            if (mContext9 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                imageView8.setImageDrawable(ContextCompat.getDrawable(mContext9, R.drawable.post_reward_black));
                Unit unit9 = Unit.INSTANCE;
            }
        }
        EmotionEditText emotionEditText = this.et_input;
        if (emotionEditText != null) {
            Context mContext10 = getMContext();
            if (mContext10 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            emotionEditText.setBackground(ContextCompat.getDrawable(mContext10, R.drawable.shape_post_input_comment));
        }
        EmotionEditText emotionEditText2 = this.et_input;
        if (emotionEditText2 != null) {
            Context mContext11 = getMContext();
            if (mContext11 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                emotionEditText2.setHintTextColor(ContextCompat.getColor(mContext11, R.color.text_light));
                Unit unit10 = Unit.INSTANCE;
            }
        }
        if (isLogin()) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            if (userInfo.getVipType() > 0) {
                EmotionEditText emotionEditText3 = this.et_input;
                if (emotionEditText3 != null) {
                    emotionEditText3.setHint(R.string.post_comment_hint_text);
                    Unit unit11 = Unit.INSTANCE;
                }
            } else {
                LevelBean levelBean2 = this.levelBean;
                if (levelBean2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("levelBean");
                    throw null;
                } else if (levelBean2.getCurrentLevelIndex() < 2) {
                    EmotionEditText emotionEditText4 = this.et_input;
                    if (emotionEditText4 != null) {
                        emotionEditText4.setHint(R.string.level_tip_dialog);
                        Unit unit12 = Unit.INSTANCE;
                    }
                } else {
                    LevelBean levelBean3 = this.levelBean;
                    if (levelBean3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("levelBean");
                        throw null;
                    } else if (levelBean3.getCommentCount() == 0) {
                        EmotionEditText emotionEditText5 = this.et_input;
                        if (emotionEditText5 != null) {
                            emotionEditText5.setHint(R.string.credit_edit_comment_deny);
                            Unit unit13 = Unit.INSTANCE;
                        }
                    } else {
                        LevelBean levelBean4 = this.levelBean;
                        if (levelBean4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
                            throw null;
                        }
                        int commentCount_times = levelBean4.getCommentCount_times();
                        LevelBean levelBean5 = this.levelBean;
                        if (levelBean5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
                            throw null;
                        } else if (commentCount_times == levelBean5.getCommentCount()) {
                            EmotionEditText emotionEditText6 = this.et_input;
                            if (emotionEditText6 != null) {
                                emotionEditText6.setHint(R.string.credit_edit_comment_no_left);
                                Unit unit14 = Unit.INSTANCE;
                            }
                        } else {
                            EmotionEditText emotionEditText7 = this.et_input;
                            if (emotionEditText7 != null) {
                                emotionEditText7.setHint(R.string.post_comment_hint_text);
                                Unit unit15 = Unit.INSTANCE;
                            }
                        }
                    }
                }
            }
        } else {
            EmotionEditText emotionEditText8 = this.et_input;
            if (emotionEditText8 != null) {
                emotionEditText8.setHint(R.string.post_comment_hint_text);
                Unit unit16 = Unit.INSTANCE;
            }
        }
        PostList postList = this.itemData;
        if (Intrinsics.areEqual(postList != null ? Boolean.valueOf(postList.isMemberIsUp()) : null, true)) {
            RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_reward);
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(0);
            }
        } else {
            RelativeLayout relativeLayout4 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video_reward);
            if (relativeLayout4 != null) {
                relativeLayout4.setVisibility(8);
            }
        }
        PostList postList2 = this.itemData;
        if (postList2 != null && postList2.isMemberIsUp()) {
            ImageView imageView9 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView9 != null) {
                imageView9.setVisibility(0);
            }
            PostList postList3 = this.itemData;
            if (postList3 != null && postList3.getMemberIsOriginal() == 1) {
                ImageView imageView10 = (ImageView) _$_findCachedViewById(R$id.image_up);
                if (imageView10 != null) {
                    imageView10.setImageResource(R.drawable.up_original);
                    Unit unit17 = Unit.INSTANCE;
                }
            } else {
                PostList postList4 = this.itemData;
                if (postList4 != null && postList4.getUpLevel() == 1) {
                    ImageView imageView11 = (ImageView) _$_findCachedViewById(R$id.image_up);
                    if (imageView11 != null) {
                        imageView11.setImageResource(R.drawable.my_up_y1);
                        Unit unit18 = Unit.INSTANCE;
                    }
                } else {
                    PostList postList5 = this.itemData;
                    if (postList5 != null && postList5.getUpLevel() == 2) {
                        ImageView imageView12 = (ImageView) _$_findCachedViewById(R$id.image_up);
                        if (imageView12 != null) {
                            imageView12.setImageResource(R.drawable.my_up_y2);
                            Unit unit19 = Unit.INSTANCE;
                        }
                    } else {
                        PostList postList6 = this.itemData;
                        if (postList6 != null && postList6.getUpLevel() == 3) {
                            ImageView imageView13 = (ImageView) _$_findCachedViewById(R$id.image_up);
                            if (imageView13 != null) {
                                imageView13.setImageResource(R.drawable.my_up_y3_v);
                                Unit unit20 = Unit.INSTANCE;
                            }
                        } else {
                            PostList postList7 = this.itemData;
                            if (postList7 != null && postList7.getUpLevel() == 4) {
                                ImageView imageView14 = (ImageView) _$_findCachedViewById(R$id.image_up);
                                if (imageView14 != null) {
                                    imageView14.setImageResource(R.drawable.my_up_y4);
                                    Unit unit21 = Unit.INSTANCE;
                                }
                            } else {
                                PostList postList8 = this.itemData;
                                if (postList8 != null && postList8.getUpLevel() == 5 && (imageView3 = (ImageView) _$_findCachedViewById(R$id.image_up)) != null) {
                                    imageView3.setImageResource(R.drawable.my_up_y5);
                                    Unit unit22 = Unit.INSTANCE;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            ImageView imageView15 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView15 != null) {
                imageView15.setVisibility(8);
            }
        }
        PostList postList9 = this.itemData;
        if (postList9 != null && postList9.getGoldPorterFlag() == 1) {
            ImageView imageView16 = (ImageView) _$_findCachedViewById(R$id.image_porter);
            if (imageView16 != null) {
                imageView16.setVisibility(0);
            }
        } else {
            ImageView imageView17 = (ImageView) _$_findCachedViewById(R$id.image_porter);
            if (imageView17 != null) {
                imageView17.setVisibility(8);
            }
        }
        PostList postList10 = this.itemData;
        if (postList10 != null && postList10.isMemberIsAnchor()) {
            ImageView imageView18 = (ImageView) _$_findCachedViewById(R$id.image_anchor);
            if (imageView18 != null) {
                imageView18.setVisibility(0);
            }
        } else {
            ImageView imageView19 = (ImageView) _$_findCachedViewById(R$id.image_anchor);
            if (imageView19 != null) {
                imageView19.setVisibility(8);
            }
        }
        PostList postList11 = this.itemData;
        if ((postList11 != null ? postList11.getVipType() : 0) > 0) {
            ImageView image_vip = (ImageView) _$_findCachedViewById(R$id.image_vip);
            Intrinsics.checkExpressionValueIsNotNull(image_vip, "image_vip");
            image_vip.setVisibility(0);
        } else {
            ImageView imageView20 = (ImageView) _$_findCachedViewById(R$id.image_vip);
            if (imageView20 != null) {
                imageView20.setVisibility(8);
            }
        }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setMinimumHeight(0);
        }
        CollapsingToolbarLayout collapsingToolbarLayout2 = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
        if (collapsingToolbarLayout2 != null) {
            Context mContext12 = getMContext();
            if (mContext12 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                collapsingToolbarLayout2.setBackgroundColor(ContextCompat.getColor(mContext12, R.color.white));
                Unit unit23 = Unit.INSTANCE;
            }
        }
        PostList postList12 = this.itemData;
        if (postList12 != null) {
            if (postList12 != null) {
                if (postList12.getCommentTimes() >= 10000) {
                    TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_video_conmment_num);
                    if (textView5 != null) {
                        StringBuilder sb = new StringBuilder();
                        PostList postList13 = this.itemData;
                        if (postList13 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        sb.append(FormatUtil.formatOne(Double.valueOf(postList13.getCommentTimes() / ConstantUtils.MAX_ITEM_NUM)));
                        sb.append((char) 19975);
                        textView5.setText(sb.toString());
                    }
                } else {
                    TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_video_conmment_num);
                    if (textView6 != null) {
                        PostList postList14 = this.itemData;
                        if (postList14 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        textView6.setText(String.valueOf(postList14.getCommentTimes()));
                    }
                }
                PostList postList15 = this.itemData;
                if (postList15 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                if (postList15.getGoodNum() >= 10000) {
                    TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_video_zan);
                    if (textView7 != null) {
                        StringBuilder sb2 = new StringBuilder();
                        PostList postList16 = this.itemData;
                        if (postList16 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        sb2.append(FormatUtil.formatOne(Double.valueOf(postList16.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)));
                        sb2.append((char) 19975);
                        textView7.setText(sb2.toString());
                    }
                } else {
                    TextView textView8 = (TextView) _$_findCachedViewById(R$id.text_video_zan);
                    if (textView8 != null) {
                        PostList postList17 = this.itemData;
                        if (postList17 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        textView8.setText(String.valueOf(postList17.getGoodNum()));
                    }
                }
                Unit unit24 = Unit.INSTANCE;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        Context mContext13 = getMContext();
        ImageView imageView21 = (ImageView) _$_findCachedViewById(R$id.iv_head);
        PostList postList18 = this.itemData;
        ImageLoaderUtil.loadHeadImage(mContext13, imageView21, new ImageBean(postList18 != null ? postList18.getAvatar() : null));
        TextView textView9 = (TextView) _$_findCachedViewById(R$id.tv_name);
        if (textView9 != null) {
            PostList postList19 = this.itemData;
            if (postList19 == null || (str = postList19.getName()) == null) {
                str = "";
            }
            textView9.setText(str);
        }
        UserPermissionUtil userPermissionUtil = UserPermissionUtil.getInstance();
        ImageView imageView22 = (ImageView) _$_findCachedViewById(R$id.iv_post_member_level_nick);
        PostList postList20 = this.itemData;
        Integer valueOf = postList20 != null ? Integer.valueOf(postList20.getCurrentLevelIndex()) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        userPermissionUtil.userLevelNickShow(imageView22, new LevelBean(valueOf.intValue()));
        PostList postList21 = this.itemData;
        if (!TextUtils.isEmpty(postList21 != null ? postList21.getCreateTime() : null)) {
            PostList postList22 = this.itemData;
            Date formatTimeToDate = FormatUtil.formatTimeToDate(DateUtils.C_TIME_PATTON_DEFAULT, postList22 != null ? postList22.getCreateTime() : null);
            TextView textView10 = (TextView) _$_findCachedViewById(R$id.tv_time);
            if (textView10 != null) {
                textView10.setText(FormatUtil.formatTime(formatTimeToDate, new Date()));
            }
        }
        PostList postList23 = this.itemData;
        if (postList23 != null && postList23.getMemberId() == DBUtil.getMemberId()) {
            TextView textView11 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView11 != null) {
                textView11.setVisibility(8);
            }
        } else {
            TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView12 != null) {
                textView12.setVisibility(0);
            }
            PostList postList24 = this.itemData;
            if (postList24 != null && postList24.getIsAttention() == 1) {
                TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_foucs);
                if (textView13 != null) {
                    textView13.setText(R.string.common_focus_y);
                    Unit unit25 = Unit.INSTANCE;
                }
            } else {
                TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_foucs);
                if (textView14 != null) {
                    textView14.setText(R.string.common_focus_n_add);
                    Unit unit26 = Unit.INSTANCE;
                }
            }
        }
        PostList postList25 = this.itemData;
        Integer valueOf2 = postList25 != null ? Integer.valueOf(postList25.getIsThumbUp()) : null;
        if (valueOf2 == null || valueOf2.intValue() == 0) {
            RelativeLayout relativeLayout5 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout5 != null && relativeLayout5.getVisibility() == 0) {
                ImageView imageView23 = (ImageView) _$_findCachedViewById(R$id.image_video_zan);
                if (imageView23 != null) {
                    Context mContext14 = getMContext();
                    if (mContext14 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        imageView23.setImageDrawable(ContextCompat.getDrawable(mContext14, R.drawable.icon_new_post_video_white_zan));
                        Unit unit27 = Unit.INSTANCE;
                    }
                }
            } else {
                ImageView imageView24 = (ImageView) _$_findCachedViewById(R$id.image_video_zan);
                if (imageView24 != null) {
                    Context mContext15 = getMContext();
                    if (mContext15 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        imageView24.setImageDrawable(ContextCompat.getDrawable(mContext15, R.drawable.icon_new_post_zan));
                        Unit unit28 = Unit.INSTANCE;
                    }
                }
            }
        } else if (valueOf2.intValue() == 1 && (imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_zan)) != null) {
            imageView2.setImageResource(R.drawable.icon_new_post_zan_ok);
            Unit unit29 = Unit.INSTANCE;
        }
        PostList postList26 = this.itemData;
        if (postList26 != null && postList26.getIsFavor() == 1) {
            ImageView imageView25 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
            if (imageView25 != null) {
                imageView25.setImageResource(R.drawable.icon_post_collected);
                Unit unit30 = Unit.INSTANCE;
            }
            TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_video_collect);
            if (textView15 != null) {
                textView15.setText(AppUtil.getString(R.string.post_collect_s));
            }
        } else {
            TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_video_collect);
            if (textView16 != null) {
                textView16.setText(AppUtil.getString(R.string.post_collect));
            }
            RelativeLayout relativeLayout6 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout6 != null && relativeLayout6.getVisibility() == 0) {
                ImageView imageView26 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
                if (imageView26 != null) {
                    Context mContext16 = getMContext();
                    if (mContext16 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        imageView26.setImageDrawable(ContextCompat.getDrawable(mContext16, R.drawable.icon_new_post_video_white_collect));
                        Unit unit31 = Unit.INSTANCE;
                    }
                }
            } else {
                ImageView imageView27 = (ImageView) _$_findCachedViewById(R$id.image_video_collect);
                if (imageView27 != null) {
                    Context mContext17 = getMContext();
                    if (mContext17 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        imageView27.setImageDrawable(ContextCompat.getDrawable(mContext17, R.drawable.icon_new_post_video_collect));
                        Unit unit32 = Unit.INSTANCE;
                    }
                }
            }
        }
        RelativeLayout relativeLayout7 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
        if (relativeLayout7 != null && relativeLayout7.getVisibility() == 0) {
            ImageView imageView28 = (ImageView) _$_findCachedViewById(R$id.image_video_reward);
            if (imageView28 != null) {
                imageView28.setImageResource(R.drawable.post_reward_white);
                Unit unit33 = Unit.INSTANCE;
            }
        } else {
            ImageView imageView29 = (ImageView) _$_findCachedViewById(R$id.image_video_reward);
            if (imageView29 != null) {
                imageView29.setImageResource(R.drawable.post_reward_black);
                Unit unit34 = Unit.INSTANCE;
            }
        }
        TextView textView17 = (TextView) _$_findCachedViewById(R$id.text_video_reward);
        if (textView17 != null) {
            PostList postList27 = this.itemData;
            textView17.setText(String.valueOf(postList27 != null ? Integer.valueOf(postList27.getPayTimes()) : null));
        }
        AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
        CoordinatorLayout.LayoutParams layoutParams3 = (CoordinatorLayout.LayoutParams) (appBarLayout != null ? appBarLayout.getLayoutParams() : null);
        if (layoutParams3 != null) {
            ((ViewGroup.MarginLayoutParams) layoutParams3).width = -1;
        }
        if (layoutParams3 != null) {
            ((ViewGroup.MarginLayoutParams) layoutParams3).height = -2;
        }
        AppBarLayout appBarLayout2 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
        if (appBarLayout2 != null) {
            appBarLayout2.setLayoutParams(layoutParams3);
        }
        PostList postList28 = this.itemData;
        Integer valueOf3 = postList28 != null ? Integer.valueOf(postList28.getPostType()) : null;
        if (valueOf3 != null && valueOf3.intValue() == 2) {
            ImageView imageView30 = (ImageView) _$_findCachedViewById(R$id.image_menu);
            if (imageView30 != null) {
                imageView30.setVisibility(0);
            }
            RelativeLayout relativeLayout8 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout8 != null) {
                relativeLayout8.setVisibility(0);
            }
            Context mContext18 = getMContext();
            ImageView imageView31 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            PostList postList29 = this.itemData;
            ImageLoaderUtil.loadViewPagerOriginImage(mContext18, imageView31, null, new ImageBean(postList29 != null ? postList29.getSecVideoCover() : null), 0);
            ImageView imageView32 = (ImageView) _$_findCachedViewById(R$id.image_bg_blur);
            if (imageView32 != null) {
                PostList postList30 = this.itemData;
                imageView32.setTag(R.id.glide_load_image_id, postList30 != null ? postList30.getSecVideoCover() : null);
                Unit unit35 = Unit.INSTANCE;
            }
            Context mContext19 = getMContext();
            ImageView imageView33 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            PostList postList31 = this.itemData;
            ImageLoaderUtil.loadViewPagerOriginImageBlurs(mContext19, imageView33, null, new ImageBean(postList31 != null ? postList31.getSecVideoCover() : null), new NewDetailViewPagerRecyclerFragment$initDataToView$2(this));
            TextView textView18 = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            if (textView18 != null) {
                PostList postList32 = this.itemData;
                textView18.setText(postList32 != null ? postList32.getVideoTime() : null);
            }
            TextView textView19 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            ViewGroup.LayoutParams layoutParams4 = textView19 != null ? textView19.getLayoutParams() : null;
            if (layoutParams4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
            }
            RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) layoutParams4;
            layoutParams5.rightMargin = (int) DisplayMetricsUtils.dp2px(40.0f);
            TextView textView20 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView20 != null) {
                textView20.setLayoutParams(layoutParams5);
            }
            PostList postList33 = this.itemData;
            if (!PostUtils.INSTANCE.isWidthVideo(postList33 != null ? postList33.getSize() : null)) {
                this.currentVideoHorizontalandVertical = 2;
                AppBarLayout appBarLayout3 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                CoordinatorLayout.LayoutParams layoutParams6 = (CoordinatorLayout.LayoutParams) (appBarLayout3 != null ? appBarLayout3.getLayoutParams() : null);
                if (layoutParams6 != null) {
                    ((ViewGroup.MarginLayoutParams) layoutParams6).width = -1;
                }
                PostUtils postUtils = PostUtils.INSTANCE;
                PostList postList34 = this.itemData;
                int picWidth = postUtils.getPicWidth(postList34 != null ? postList34.getSize() : null);
                PostUtils postUtils2 = PostUtils.INSTANCE;
                PostList postList35 = this.itemData;
                if (postList35 != null) {
                    str2 = postList35.getSize();
                }
                int calculationItemMaxHeightForPostDetail = postUtils.calculationItemMaxHeightForPostDetail(picWidth, postUtils2.getPicHeight(str2));
                if (layoutParams6 != null) {
                    ((ViewGroup.MarginLayoutParams) layoutParams6).height = calculationItemMaxHeightForPostDetail;
                }
                AppBarLayout appBarLayout4 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                if (appBarLayout4 != null) {
                    appBarLayout4.setLayoutParams(layoutParams6);
                }
                View _$_findCachedViewById2 = _$_findCachedViewById(R$id.view_divider);
                if (_$_findCachedViewById2 != null) {
                    _$_findCachedViewById2.setVisibility(8);
                }
                if (this.currentIndex == 1 && this.isScrollTop) {
                    AppBarLayout appBarLayout5 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                    if (appBarLayout5 != null) {
                        appBarLayout5.setExpanded(false);
                        Unit unit36 = Unit.INSTANCE;
                    }
                } else {
                    AppBarLayout appBarLayout6 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                    if (appBarLayout6 != null) {
                        appBarLayout6.setExpanded(true);
                        Unit unit37 = Unit.INSTANCE;
                    }
                    changeUI(0);
                }
            } else {
                AppBarLayout appBarLayout7 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                CoordinatorLayout.LayoutParams layoutParams7 = (CoordinatorLayout.LayoutParams) (appBarLayout7 != null ? appBarLayout7.getLayoutParams() : null);
                if (layoutParams7 != null) {
                    ((ViewGroup.MarginLayoutParams) layoutParams7).width = -1;
                }
                if (layoutParams7 != null) {
                    ((ViewGroup.MarginLayoutParams) layoutParams7).height = (int) DisplayMetricsUtils.get16To9Height();
                }
                AppBarLayout appBarLayout8 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                if (appBarLayout8 != null) {
                    appBarLayout8.setLayoutParams(layoutParams7);
                }
                RelativeLayout relativeLayout9 = (RelativeLayout) _$_findCachedViewById(R$id.relate_person);
                if (relativeLayout9 != null) {
                    Context mContext20 = getMContext();
                    if (mContext20 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        relativeLayout9.setBackgroundColor(ContextCompat.getColor(mContext20, R.color.transparent));
                        Unit unit38 = Unit.INSTANCE;
                    }
                }
                AppBarLayout appBarLayout9 = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                if (appBarLayout9 != null) {
                    appBarLayout9.setExpanded(false);
                    Unit unit39 = Unit.INSTANCE;
                }
                changeUI(1);
                ImageView iv_head = (ImageView) _$_findCachedViewById(R$id.iv_head);
                Intrinsics.checkExpressionValueIsNotNull(iv_head, "iv_head");
                iv_head.setVisibility(8);
                TextView textView21 = (TextView) _$_findCachedViewById(R$id.tv_time);
                if (textView21 != null) {
                    textView21.setVisibility(8);
                }
                TextView textView22 = (TextView) _$_findCachedViewById(R$id.text_foucs);
                if (textView22 != null) {
                    textView22.setVisibility(8);
                }
                TextView textView23 = (TextView) _$_findCachedViewById(R$id.tv_name);
                if (textView23 != null) {
                    textView23.setVisibility(8);
                }
                ImageView imageView34 = (ImageView) _$_findCachedViewById(R$id.iv_post_member_level_nick);
                if (imageView34 != null) {
                    imageView34.setVisibility(8);
                }
                ImageView imageView35 = (ImageView) _$_findCachedViewById(R$id.image_vip);
                if (imageView35 != null) {
                    imageView35.setVisibility(8);
                }
                ImageView imageView36 = (ImageView) _$_findCachedViewById(R$id.image_up);
                if (imageView36 != null) {
                    imageView36.setVisibility(8);
                }
                ImageView imageView37 = (ImageView) _$_findCachedViewById(R$id.image_anchor);
                if (imageView37 != null) {
                    imageView37.setVisibility(8);
                }
                ImageView imageView38 = (ImageView) _$_findCachedViewById(R$id.image_review);
                if (imageView38 != null) {
                    imageView38.setVisibility(8);
                }
                ImageView imageView39 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
                if (imageView39 != null) {
                    imageView39.setVisibility(0);
                }
                ImageView imageView40 = (ImageView) _$_findCachedViewById(R$id.image_menu);
                if (imageView40 != null) {
                    imageView40.setVisibility(0);
                }
                ImageView imageView41 = (ImageView) _$_findCachedViewById(R$id.image_porter);
                if (imageView41 != null) {
                    imageView41.setVisibility(8);
                }
                ImageView imageView42 = (ImageView) _$_findCachedViewById(R$id.image_back_person);
                if (imageView42 != null) {
                    imageView42.setImageResource(R.drawable.icon_back_white);
                    Unit unit40 = Unit.INSTANCE;
                }
                ImageView imageView43 = (ImageView) _$_findCachedViewById(R$id.image_menu);
                if (imageView43 != null) {
                    imageView43.setImageResource(R.drawable.icon_menu_white);
                    Unit unit41 = Unit.INSTANCE;
                }
                RelativeLayout relativeLayout10 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
                if (relativeLayout10 != null) {
                    relativeLayout10.setVisibility(0);
                }
                reloadResetState();
            }
            CollapsingToolbarLayout collapsingToolbarLayout3 = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
            if (collapsingToolbarLayout3 != null) {
                collapsingToolbarLayout3.setMinimumHeight((int) DisplayMetricsUtils.get16To9Height());
            }
            RelativeLayout relativeLayout11 = (RelativeLayout) _$_findCachedViewById(R$id.image_back_ground);
            if (relativeLayout11 != null) {
                relativeLayout11.setVisibility(0);
            }
            ImageView imageView44 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            if (imageView44 != null) {
                imageView44.setVisibility(0);
            }
            CollapsingToolbarLayout collapsingToolbarLayout4 = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
            if (collapsingToolbarLayout4 != null) {
                collapsingToolbarLayout4.setVisibility(0);
            }
            FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout != null) {
                frameLayout.setVisibility(4);
            }
            FrameLayout frameLayout2 = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout2 != null) {
                frameLayout2.setTag(R.id.video_list_item_id, this.itemData);
                Unit unit42 = Unit.INSTANCE;
            }
            if ((!Intrinsics.areEqual(this.businessType, "review_post_pre") && !Intrinsics.areEqual(this.businessType, "review_post")) || (imageView = (ImageView) _$_findCachedViewById(R$id.image_menu)) == null) {
                return;
            }
            imageView.setVisibility(8);
        } else if (valueOf3 != null && valueOf3.intValue() == 1) {
            CollapsingToolbarLayout collapsingToolbarLayout5 = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
            if (collapsingToolbarLayout5 != null) {
                collapsingToolbarLayout5.setVisibility(0);
            }
            RelativeLayout relativeLayout12 = (RelativeLayout) _$_findCachedViewById(R$id.image_back_ground);
            if (relativeLayout12 != null) {
                relativeLayout12.setVisibility(8);
            }
            RelativeLayout relativeLayout13 = (RelativeLayout) _$_findCachedViewById(R$id.image_relative);
            if (relativeLayout13 != null) {
                relativeLayout13.setVisibility(0);
            }
            ImageView imageView45 = (ImageView) _$_findCachedViewById(R$id.image_menu);
            if (imageView45 != null) {
                imageView45.setVisibility(8);
            }
            RelativeLayout relativeLayout14 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout14 != null) {
                relativeLayout14.setVisibility(8);
            }
            TextView textView24 = (TextView) _$_findCachedViewById(R$id.image_title);
            if (textView24 != null) {
                Context mContext21 = getMContext();
                textView24.setText(mContext21 != null ? mContext21.getString(R.string.post_detail_img) : null);
            }
            TextView textView25 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView25 != null) {
                layoutParams2 = textView25.getLayoutParams();
            }
            if (layoutParams2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
            }
            RelativeLayout.LayoutParams layoutParams8 = (RelativeLayout.LayoutParams) layoutParams2;
            layoutParams8.rightMargin = (int) DisplayMetricsUtils.dp2px(0.0f);
            TextView textView26 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView26 == null) {
                return;
            }
            textView26.setLayoutParams(layoutParams8);
        } else if (valueOf3 == null || valueOf3.intValue() != 3) {
        } else {
            CollapsingToolbarLayout collapsingToolbarLayout6 = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
            if (collapsingToolbarLayout6 != null) {
                collapsingToolbarLayout6.setVisibility(0);
            }
            RelativeLayout relativeLayout15 = (RelativeLayout) _$_findCachedViewById(R$id.image_back_ground);
            if (relativeLayout15 != null) {
                relativeLayout15.setVisibility(8);
            }
            RelativeLayout relativeLayout16 = (RelativeLayout) _$_findCachedViewById(R$id.relative_video_top_bar);
            if (relativeLayout16 != null) {
                relativeLayout16.setVisibility(8);
            }
            TextView textView27 = (TextView) _$_findCachedViewById(R$id.image_title);
            if (textView27 != null) {
                Context mContext22 = getMContext();
                textView27.setText(mContext22 != null ? mContext22.getString(R.string.post_detail_read) : null);
            }
            RelativeLayout relativeLayout17 = (RelativeLayout) _$_findCachedViewById(R$id.image_relative);
            if (relativeLayout17 != null) {
                relativeLayout17.setVisibility(0);
            }
            ImageView imageView46 = (ImageView) _$_findCachedViewById(R$id.image_menu);
            if (imageView46 != null) {
                imageView46.setVisibility(8);
            }
            TextView textView28 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView28 != null) {
                layoutParams = textView28.getLayoutParams();
            }
            if (layoutParams == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
            }
            RelativeLayout.LayoutParams layoutParams9 = (RelativeLayout.LayoutParams) layoutParams;
            layoutParams9.rightMargin = (int) DisplayMetricsUtils.dp2px(0.0f);
            TextView textView29 = (TextView) _$_findCachedViewById(R$id.text_foucs);
            if (textView29 == null) {
                return;
            }
            textView29.setLayoutParams(layoutParams9);
        }
    }

    public final void scorllCOmment() {
        ViewTreeObserver viewTreeObserver;
        if (this.currentIndex != 1 || !this.isScrollTop || this.isScrolled) {
            return;
        }
        this.isScrolled = true;
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null || (viewTreeObserver = recyclerView.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$scorllCOmment$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                RecyclerView recyclerView2;
                ViewTreeObserver viewTreeObserver2;
                recyclerView2 = NewDetailViewPagerRecyclerFragment.this.getRecyclerView();
                if (recyclerView2 != null && (viewTreeObserver2 = recyclerView2.getViewTreeObserver()) != null) {
                    viewTreeObserver2.removeOnPreDrawListener(this);
                }
                NewDetailViewPagerRecyclerFragment.this.clickCommentScrollTop();
                return true;
            }
        });
    }

    public final void setRequest() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        PostList postList = this.itemData;
        Integer num = null;
        if (TextUtils.isEmpty(String.valueOf(postList != null ? Integer.valueOf(postList.getId()) : null))) {
            return;
        }
        PostList postList2 = this.itemData;
        linkedHashMap.put("articleId", postList2 != null ? Integer.valueOf(postList2.getId()) : "");
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("landlord", Integer.valueOf(this.isLouzh));
        linkedHashMap.put("timeSort", this.timeSortType);
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.requestCommentList(linkedHashMap);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("新的帖子詳情頁評論請求的帖子ID ---------");
        PostList postList3 = this.itemData;
        if (postList3 != null) {
            num = Integer.valueOf(postList3.getId());
        }
        sb.append(num);
        LogUtil.m3787d("yan2", sb.toString());
    }

    private final void setListener() {
        ImageView imageView = this.iv_choose_img;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$setListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (NewDetailViewPagerRecyclerFragment.this.startLoginActivity()) {
                        return;
                    }
                    SystemParam systemParam = DBUtil.getSystemParam();
                    Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
                    if (systemParam.getPinglunPic() != 0) {
                        NewDetailViewPagerRecyclerFragment.this.initUploadAdapter();
                        NewCommentPresenter access$getMPresenter$p = NewDetailViewPagerRecyclerFragment.access$getMPresenter$p(NewDetailViewPagerRecyclerFragment.this);
                        if (access$getMPresenter$p == null) {
                            return;
                        }
                        access$getMPresenter$p.selectResource(NewDetailViewPagerRecyclerFragment.this.getActivity());
                        return;
                    }
                    ToastUtil.showCenterToast((int) R.string.post_comment_image_not_support);
                }
            });
        }
        EmotionEditText emotionEditText = this.et_input;
        if (emotionEditText != null) {
            emotionEditText.setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$setListener$2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent event) {
                    Context mContext;
                    Intrinsics.checkExpressionValueIsNotNull(event, "event");
                    if (event.getAction() == 0) {
                        LoginInfo loginInfo = DBUtil.getLoginInfo();
                        Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
                        if (!loginInfo.isLogin()) {
                            LoginActivity.Companion companion = LoginActivity.Companion;
                            mContext = NewDetailViewPagerRecyclerFragment.this.getMContext();
                            if (mContext != null) {
                                companion.startActivity(mContext);
                                return true;
                            }
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (!UserPermissionUtil.getInstance().isPermissionEnable(3)) {
                            return true;
                        }
                    }
                    EmotionEditText et_input = NewDetailViewPagerRecyclerFragment.this.getEt_input();
                    if (et_input != null) {
                        et_input.setFocusable(true);
                    }
                    EmotionEditText et_input2 = NewDetailViewPagerRecyclerFragment.this.getEt_input();
                    if (et_input2 != null) {
                        et_input2.setFocusableInTouchMode(true);
                        return false;
                    }
                    return false;
                }
            });
        }
        EmotionEditText emotionEditText2 = this.et_input;
        if (emotionEditText2 != null) {
            emotionEditText2.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$setListener$3
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == 4) {
                        NewDetailViewPagerRecyclerFragment.this.sendComment();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public final void sendComment() {
        List<LocalMedia> selectList;
        PostKeyboard postKeyboard = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard != null) {
            postKeyboard.reset();
        }
        LoginInfo loginInfo = DBUtil.getLoginInfo();
        Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
        if (!loginInfo.isLogin()) {
            return;
        }
        if (this.itemData == null) {
            ToastUtil.showCenterToast((int) R.string.post_data_exception_tip);
        } else if (!UserPermissionUtil.getInstance().isPermissionEnable(3)) {
        } else {
            NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
            if (newCommentPresenter != null && newCommentPresenter.getReplySending()) {
                ToastUtil.showCenterToast((int) R.string.post_comment_send_ing_tip);
                return;
            }
            EmotionEditText emotionEditText = this.et_input;
            String valueOf = String.valueOf(emotionEditText != null ? emotionEditText.getText() : null);
            int i = 0;
            int length = valueOf.length() - 1;
            int i2 = 0;
            boolean z = false;
            while (i2 <= length) {
                boolean z2 = valueOf.charAt(!z ? i2 : length) <= ' ';
                if (!z) {
                    if (!z2) {
                        z = true;
                    } else {
                        i2++;
                    }
                } else if (!z2) {
                    break;
                } else {
                    length--;
                }
            }
            String obj = valueOf.subSequence(i2, length + 1).toString();
            if (!CommentCheckUtil.isValid(this.et_input, obj)) {
                return;
            }
            buildTempComment();
            NewCommentPresenter newCommentPresenter2 = (NewCommentPresenter) getMPresenter();
            if (newCommentPresenter2 != null && (selectList = newCommentPresenter2.getSelectList()) != null) {
                i = selectList.size();
            }
            if (i > 0) {
                NewCommentPresenter newCommentPresenter3 = (NewCommentPresenter) getMPresenter();
                if (newCommentPresenter3 == null) {
                    return;
                }
                newCommentPresenter3.uploadImg(getAdapter(), this.itemData);
                return;
            }
            sendFirstComment(obj, "");
        }
    }

    private final void sendFirstComment(String str, String str2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        PostList postList = this.itemData;
        Integer num = null;
        linkedHashMap.put("articleId", postList != null ? Integer.valueOf(postList.getId()) : null);
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("content", AppSecretUtil.decodeResponse(str));
        linkedHashMap.put("type", "1");
        if (!TextUtils.isEmpty(str2)) {
            linkedHashMap.put("imageUrl", str2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("新的帖子詳情頁評論請求的帖子ID ---------");
        PostList postList2 = this.itemData;
        if (postList2 != null) {
            num = Integer.valueOf(postList2.getId());
        }
        sb.append(num);
        LogUtil.m3787d("yan2", sb.toString());
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.requestSendComment(linkedHashMap);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerSendFisrtComment(String str, String str2) {
        sendFirstComment(str, str2);
    }

    private final void buildTempComment() {
        List<LocalMedia> selectList;
        Ranges indices;
        List<LocalMedia> selectList2;
        List<LocalMedia> selectList3;
        LocalMedia localMedia;
        List<LocalMedia> selectList4;
        List<LocalMedia> selectList5;
        List<CommentList> data;
        List<CommentList> data2;
        CommentList commentList;
        PostDetailCommentAdapter adapter;
        if (this.itemData == null) {
            ToastUtil.showCenterToast((int) R.string.post_data_exception_tip);
            return;
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        int i = 1;
        if (adapter2 != null && (data = adapter2.getData()) != null && data.size() == 1) {
            try {
                PostDetailCommentAdapter adapter3 = getAdapter();
                if (adapter3 != null && (data2 = adapter3.getData()) != null && (commentList = data2.get(0)) != null && commentList.getId() == 0 && (adapter = getAdapter()) != null) {
                    adapter.remove(0);
                }
            } catch (Exception unused) {
            }
        }
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.setReplySending(true);
        }
        CommentList commentList2 = new CommentList();
        commentList2.setSendStatus(1);
        EmotionEditText emotionEditText = this.et_input;
        String valueOf = String.valueOf(emotionEditText != null ? emotionEditText.getText() : null);
        int length = valueOf.length() - 1;
        int i2 = 0;
        boolean z = false;
        while (i2 <= length) {
            boolean z2 = valueOf.charAt(!z ? i2 : length) <= ' ';
            if (!z) {
                if (!z2) {
                    z = true;
                } else {
                    i2++;
                }
            } else if (!z2) {
                break;
            } else {
                length--;
            }
        }
        commentList2.setContent(valueOf.subSequence(i2, length + 1).toString());
        NewCommentPresenter newCommentPresenter2 = (NewCommentPresenter) getMPresenter();
        if (((newCommentPresenter2 == null || (selectList5 = newCommentPresenter2.getSelectList()) == null) ? 0 : selectList5.size()) > 0) {
            StringBuilder sb = new StringBuilder();
            NewCommentPresenter newCommentPresenter3 = (NewCommentPresenter) getMPresenter();
            if (newCommentPresenter3 != null && (selectList = newCommentPresenter3.getSelectList()) != null) {
                indices = CollectionsKt__CollectionsKt.getIndices(selectList);
                if (indices != null) {
                    NewCommentPresenter newCommentPresenter4 = (NewCommentPresenter) getMPresenter();
                    Ranges indices2 = (newCommentPresenter4 == null || (selectList4 = newCommentPresenter4.getSelectList()) == null) ? null : CollectionsKt__CollectionsKt.getIndices(selectList4);
                    if (indices2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    int first = indices2.getFirst();
                    int last = indices2.getLast();
                    if (first <= last) {
                        while (true) {
                            NewCommentPresenter newCommentPresenter5 = (NewCommentPresenter) getMPresenter();
                            sb.append((newCommentPresenter5 == null || (selectList3 = newCommentPresenter5.getSelectList()) == null || (localMedia = selectList3.get(first)) == null) ? null : localMedia.getPath());
                            NewCommentPresenter newCommentPresenter6 = (NewCommentPresenter) getMPresenter();
                            if (first < ((newCommentPresenter6 == null || (selectList2 = newCommentPresenter6.getSelectList()) == null) ? 0 : selectList2.size()) - 1) {
                                sb.append(";");
                            }
                            if (first == last) {
                                break;
                            }
                            first++;
                        }
                    }
                }
            }
            commentList2.setUploadUrl(sb.toString());
            commentList2.setSecImageUrl(sb.toString());
        }
        this.tempCommentId--;
        commentList2.setId(this.tempCommentId);
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        commentList2.setMemberName(userInfo.getName());
        UserInfo userInfo2 = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
        commentList2.setMemberId(userInfo2.getMemberId());
        commentList2.setStatus("0");
        UserInfo userInfo3 = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo3, "DBUtil.getUserInfo()");
        commentList2.setAvatar(userInfo3.getAvatar());
        commentList2.setIsThumbUp(0);
        commentList2.setGoodNum(0);
        int memberId = DBUtil.getMemberId();
        PostList postList = this.itemData;
        commentList2.setIsLouzhu((postList == null || memberId != postList.getMemberId()) ? 0 : 1);
        UserInfo userInfo4 = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo4, "DBUtil.getUserInfo()");
        commentList2.setSex(userInfo4.getSex());
        commentList2.setCommentTime(AppUtil.getString(R.string.post_comment_recent));
        PostDetailCommentAdapter adapter4 = getAdapter();
        if (adapter4 != null) {
            adapter4.addData(0, (int) commentList2);
        }
        EmotionEditText emotionEditText2 = this.et_input;
        if (emotionEditText2 != null) {
            emotionEditText2.setText("");
        }
        RecyclerView recyclerView = this.recyclerView_upload;
        if (recyclerView != null) {
            recyclerView.setVisibility(8);
        }
        PostKeyboard postKeyboard = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard != null) {
            postKeyboard.reset();
        }
        PostList postList2 = this.itemData;
        if (postList2 != null) {
            if (postList2 != null) {
                i = postList2.getCommentTimes();
            }
            postList2.setCommentTimes(i);
        }
        updateCommentNum();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        List<String> data;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            int i3 = 0;
            Tag tag = null;
            if (i == 4) {
                ArrayList<Tag> parcelableArrayList = (intent == null || (extras = intent.getExtras()) == null) ? null : extras.getParcelableArrayList("intent_list");
                if (!(parcelableArrayList instanceof ArrayList)) {
                    return;
                }
                NewCommentHeadView newCommentHeadView = this.headView;
                if (newCommentHeadView != null) {
                    tag = newCommentHeadView.compareAdd(parcelableArrayList);
                }
                if (!(tag instanceof Tag)) {
                    return;
                }
                if (tag.getTagId() > 0) {
                    NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
                    if (newCommentPresenter == null) {
                        return;
                    }
                    int tagId = tag.getTagId();
                    PostList postList = this.itemData;
                    if (postList != null) {
                        i3 = postList.getId();
                    }
                    newCommentPresenter.addTagToPost(tagId, "", i3, tag);
                    return;
                }
                NewCommentPresenter newCommentPresenter2 = (NewCommentPresenter) getMPresenter();
                if (newCommentPresenter2 == null) {
                    return;
                }
                String tagName = tag.getTagName();
                Intrinsics.checkExpressionValueIsNotNull(tagName, "compareAdd.tagName");
                PostList postList2 = this.itemData;
                newCommentPresenter2.addTagToPost(0, tagName, postList2 != null ? postList2.getId() : 0, tag);
            } else if (i == 188) {
                RecyclerView recyclerView = this.recyclerView_upload;
                if (recyclerView != null) {
                    recyclerView.setVisibility(0);
                }
                NewCommentPresenter newCommentPresenter3 = (NewCommentPresenter) getMPresenter();
                if (newCommentPresenter3 != null) {
                    List<LocalMedia> obtainMultipleResult = PictureSelector.obtainMultipleResult(intent);
                    Intrinsics.checkExpressionValueIsNotNull(obtainMultipleResult, "PictureSelector.obtainMultipleResult(data)");
                    newCommentPresenter3.setSelectList(obtainMultipleResult);
                }
                ArrayList arrayList = new ArrayList();
                NewCommentPresenter newCommentPresenter4 = (NewCommentPresenter) getMPresenter();
                if (newCommentPresenter4 != null && newCommentPresenter4.getSelectList() != null) {
                    NewCommentPresenter newCommentPresenter5 = (NewCommentPresenter) getMPresenter();
                    List<LocalMedia> selectList = newCommentPresenter5 != null ? newCommentPresenter5.getSelectList() : null;
                    if (selectList == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    for (LocalMedia localMedia : selectList) {
                        arrayList.add(localMedia.getPath());
                        Log.i("图片-----》", localMedia.getPath());
                    }
                }
                PostDetailImgUploadAdapter postDetailImgUploadAdapter = this.uploadAdapter;
                if (postDetailImgUploadAdapter != null && (data = postDetailImgUploadAdapter.getData()) != null) {
                    data.clear();
                }
                PostDetailImgUploadAdapter postDetailImgUploadAdapter2 = this.uploadAdapter;
                if (postDetailImgUploadAdapter2 != null) {
                    postDetailImgUploadAdapter2.addData((Collection) arrayList);
                }
                PostDetailImgUploadAdapter postDetailImgUploadAdapter3 = this.uploadAdapter;
                if (postDetailImgUploadAdapter3 == null) {
                    return;
                }
                postDetailImgUploadAdapter3.notifyDataSetChanged();
            }
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerAddTagSuccess(Tag tag) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        NewCommentHeadView newCommentHeadView = this.headView;
        if (newCommentHeadView != null) {
            newCommentHeadView.addTagToPost(tag, true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerAddTagError() {
        NewCommentHeadView newCommentHeadView = this.headView;
        if (newCommentHeadView != null) {
            newCommentHeadView.addTagToPost(null, false);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerSendCommentSucss(CommentList commentList) {
        ToastUtil.showCenterToast((int) R.string.post_comment_send_success);
        PostDetailCommentAdapter adapter = getAdapter();
        CommentList item = adapter != null ? adapter.getItem(0) : null;
        if (item != null) {
            item.setSendStatus(2);
        }
        if (item != null) {
            item.setId(commentList != null ? commentList.getId() : -1);
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.setReplySending(false);
        }
        DBUtil.saveLevelBean(2);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerSendCommentError(int i) {
        PostDetailCommentAdapter adapter;
        PostDetailCommentAdapter adapter2 = getAdapter();
        CommentList item = adapter2 != null ? adapter2.getItem(0) : null;
        if (item != null) {
            item.setSendStatus(4);
        }
        if (item != null && (adapter = getAdapter()) != null) {
            adapter.setData(0, item);
        }
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.setReplySending(false);
        }
        PostList postList = this.itemData;
        if (postList != null) {
            postList.setCommentTimes(postList != null ? postList.getCommentTimes() : -1);
        }
        updateCommentNum();
        if (i == -5) {
            showImageVerifyDialog(item, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initUploadAdapter() {
        if (this.uploadAdapter == null) {
            this.uploadAdapter = new PostDetailImgUploadAdapter(getActivity(), R.layout.layout_comment_send_pic, this.recyclerView_upload);
            RecyclerView recyclerView = this.recyclerView_upload;
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(true);
            }
            configLinearLayoutHorizontalManager(this.recyclerView_upload);
            PostDetailImgUploadAdapter postDetailImgUploadAdapter = this.uploadAdapter;
            if (postDetailImgUploadAdapter != null) {
                postDetailImgUploadAdapter.setEnableLoadMore(false);
            }
            RecyclerView recyclerView2 = this.recyclerView_upload;
            if (recyclerView2 == null) {
                return;
            }
            recyclerView2.setAdapter(this.uploadAdapter);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerReplyComment(CommentList commentList, int i) {
        dismissDialog();
        ToastUtil.showCenterToast((int) R.string.post_comment_send_success);
        PostDetailCommentAdapter adapter = getAdapter();
        CommentList item = adapter != null ? adapter.getItem(i) : null;
        if (item != null) {
            item.setSendStatus(2);
        }
        if (item != null) {
            item.setId(commentList != null ? commentList.getId() : 0);
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        PostList postList = this.itemData;
        if (postList != null) {
            postList.setCommentTimes(postList != null ? postList.getCommentTimes() : 1);
        }
        updateCommentNum();
    }

    public final void updateCommentNum() {
        PostList postList;
        PostList postList2 = this.itemData;
        if ((postList2 != null ? postList2.getCommentTimes() : 0) < 0 && (postList = this.itemData) != null) {
            postList.setCommentTimes(0);
        }
        PostList postList3 = this.itemData;
        if ((postList3 != null ? postList3.getCommentTimes() : 0) > 0) {
            TextView textView = this.tv_comment_bottom_num;
            if (textView != null) {
                textView.setVisibility(0);
            }
            TextView textView2 = this.tv_comment_bottom_num;
            if (textView2 != null) {
                StringBuilder sb = new StringBuilder();
                PostList postList4 = this.itemData;
                sb.append(String.valueOf(postList4 != null ? Integer.valueOf(postList4.getCommentTimes()) : null));
                sb.append("");
                textView2.setText(FormatUtil.formatNumOverTenThousand(sb.toString()));
            }
        } else {
            TextView textView3 = this.tv_comment_bottom_num;
            if (textView3 != null) {
                textView3.setVisibility(4);
            }
        }
        sendCollectOrThumbEvent();
    }

    public final void sendCollectOrThumbEvent() {
        PostCollectOrThumbEvent postCollectOrThumbEvent = new PostCollectOrThumbEvent();
        postCollectOrThumbEvent.postList = this.itemData;
        EventBus.getDefault().post(postCollectOrThumbEvent);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerReplyCommentError(ResponseThrowable responseThrowable, int i) {
        dismissDialog();
        PostDetailCommentAdapter adapter = getAdapter();
        CommentList item = adapter != null ? adapter.getItem(i) : null;
        if (item != null) {
            item.setSendStatus(4);
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        if (responseThrowable == null || responseThrowable.code != -5) {
            return;
        }
        showImageVerifyDialog(item, i);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerDelete(int i) {
        List<CommentList> data;
        dismissDialog();
        PostDetailCommentAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.remove(i);
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        if (adapter2 != null && (data = adapter2.getData()) != null && data.isEmpty()) {
            PostDetailCommentAdapter adapter3 = getAdapter();
            if (adapter3 != null) {
                adapter3.addData((PostDetailCommentAdapter) new CommentList(2));
            }
            PostDetailCommentAdapter adapter4 = getAdapter();
            if (adapter4 != null) {
                adapter4.loadMoreEnd();
            }
        }
        PostList postList = this.itemData;
        if (postList != null) {
            postList.setCommentTimes(postList != null ? postList.getCommentTimes() : -1);
        }
        updateCommentNum();
    }

    private final void showImageVerifyDialog(final CommentList commentList, final int i) {
        ImageVerifyDialog imageVerifyDialog = this.imageVerifyDialog;
        if (imageVerifyDialog == null) {
            this.imageVerifyDialog = new ImageVerifyDialog(getContext(), AppUtil.getString(R.string.post_comment_img_verify));
        } else if (imageVerifyDialog != null) {
            imageVerifyDialog.show();
        }
        ImageVerifyDialog imageVerifyDialog2 = this.imageVerifyDialog;
        if (imageVerifyDialog2 != null) {
            imageVerifyDialog2.getVerifyImage();
        }
        ImageVerifyDialog imageVerifyDialog3 = this.imageVerifyDialog;
        if (imageVerifyDialog3 != null) {
            imageVerifyDialog3.setImageVerifyConfirmListener(new ImageVerifyDialog.ImageVerifyConfirmListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$showImageVerifyDialog$1
                @Override // com.one.tomato.dialog.ImageVerifyDialog.ImageVerifyConfirmListener
                public final void imageVerifyConfirm(String str) {
                    CommentList commentList2 = commentList;
                    if (commentList2 != null) {
                        NewDetailViewPagerRecyclerFragment.this.sendRetryComment(commentList2, i, str);
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<CommentList> data;
        PostDetailCommentAdapter adapter;
        List<CommentList> data2;
        PostDetailCommentAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        dismissDialog();
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        PostDetailCommentAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 1) {
            return;
        }
        PostDetailCommentAdapter adapter4 = getAdapter();
        CommentList commentList = (adapter4 == null || (data2 = adapter4.getData()) == null) ? null : data2.get(0);
        if (commentList == null || commentList.getId() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setData(0, new CommentList(3));
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerPostCommentList(ArrayList<CommentList> arrayList) {
        List<CommentList> data;
        PostDetailCommentAdapter adapter;
        List<CommentList> data2;
        PostDetailCommentAdapter adapter2;
        List<CommentList> data3;
        dismissDialog();
        boolean z = false;
        if (arrayList == null || arrayList.isEmpty()) {
            if (getState() == getGET_LIST_REFRESH()) {
                SmartRefreshLayout refreshLayout = getRefreshLayout();
                if (refreshLayout != null) {
                    refreshLayout.mo6481finishRefresh();
                }
                PostDetailCommentAdapter adapter3 = getAdapter();
                if (adapter3 != null && (data3 = adapter3.getData()) != null) {
                    data3.clear();
                }
                PostDetailCommentAdapter adapter4 = getAdapter();
                if (adapter4 == null) {
                    return;
                }
                adapter4.addData((PostDetailCommentAdapter) new CommentList(2));
                return;
            }
            if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
                adapter2.loadMoreEnd();
            }
            PostDetailCommentAdapter adapter5 = getAdapter();
            if (adapter5 == null || (data = adapter5.getData()) == null || data.size() != 1) {
                return;
            }
            PostDetailCommentAdapter adapter6 = getAdapter();
            CommentList commentList = (adapter6 == null || (data2 = adapter6.getData()) == null) ? null : data2.get(0);
            if (commentList == null || commentList.getId() != 0 || (adapter = getAdapter()) == null) {
                return;
            }
            adapter.setData(0, new CommentList(2));
            return;
        }
        if (getState() == getGET_LIST_REFRESH()) {
            SmartRefreshLayout refreshLayout2 = getRefreshLayout();
            if (refreshLayout2 != null) {
                refreshLayout2.mo6481finishRefresh();
            }
            setPageNo(2);
            PostDetailCommentAdapter adapter7 = getAdapter();
            if (adapter7 != null) {
                adapter7.setNewData(arrayList);
            }
        } else {
            setPageNo(getPageNo() + 1);
            PostDetailCommentAdapter adapter8 = getAdapter();
            if (adapter8 != null) {
                adapter8.addData((Collection) arrayList);
            }
        }
        if (arrayList.size() >= getPageSize()) {
            z = true;
        }
        if (z) {
            PostDetailCommentAdapter adapter9 = getAdapter();
            if (adapter9 != null) {
                adapter9.loadMoreComplete();
            }
        } else {
            PostDetailCommentAdapter adapter10 = getAdapter();
            if (adapter10 != null) {
                adapter10.loadMoreEnd();
            }
        }
        scorllCOmment();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerPostSingleDetail(PostList postList) {
        if (postList != null) {
            this.itemData = postList;
            refreshData();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void postCommentTumb(boolean z, CommentList commentList) {
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.thumbComment(z, commentList, getAdapter());
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void sendRetryComment(CommentList itemData, int i, String str) {
        Intrinsics.checkParameterIsNotNull(itemData, "itemData");
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            PostDetailCommentAdapter adapter = getAdapter();
            PostList postList = this.itemData;
            newCommentPresenter.sendRetryComment(itemData, i, str, adapter, postList != null ? postList.getId() : 0);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void postRefresh() {
        showDialog();
        refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void requestCommentDelete(String str, String str2, int i) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("type", str);
        linkedHashMap.put("businessId", str2);
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.requestCommentDelete(linkedHashMap, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void requestAuthorCommentDelete(int i, int i2, int i3, int i4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("articleId", Integer.valueOf(i2));
        linkedHashMap.put("type", Integer.valueOf(i4));
        linkedHashMap.put("commentId", Integer.valueOf(i3));
        NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter != null) {
            newCommentPresenter.requestAuthCommentDelete(linkedHashMap, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void callSortType(String sort) {
        Intrinsics.checkParameterIsNotNull(sort, "sort");
        this.timeSortType = sort;
        showDialog();
        refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void callLouZhu(int i) {
        this.isLouzh = i;
        PostKeyboard postKeyboard = (PostKeyboard) _$_findCachedViewById(R$id.post_keyboard);
        if (postKeyboard != null) {
            postKeyboard.reset();
        }
        showDialog();
        refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void callDeleteComment(int i) {
        PostDetailCommentAdapter adapter;
        List<CommentList> data;
        if (i == 0 && (adapter = getAdapter()) != null && (data = adapter.getData()) != null && data.size() == 1) {
            PostDetailCommentAdapter adapter2 = getAdapter();
            if (adapter2 == null) {
                return;
            }
            adapter2.setData(0, new CommentList(2, 0));
            return;
        }
        PostDetailCommentAdapter adapter3 = getAdapter();
        if (adapter3 == null) {
            return;
        }
        adapter3.remove(i);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void callRewardFinsh(PostList postList) {
        PostDetailCallBack postDetailCallBack;
        if (postList == null || (postDetailCallBack = this.postDetailCallBack) == null) {
            return;
        }
        postDetailCallBack.callPostItemPayCom(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void itemClickRefreshPost(PostList postList) {
        List<CommentList> data;
        this.itemData = postList;
        videoIsAutoPlay();
        initDataToView();
        setVideoState();
        PostDetailCommentAdapter adapter = getAdapter();
        if (adapter != null && (data = adapter.getData()) != null) {
            data.clear();
        }
        PostDetailCommentAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setEnableLoadMore(false);
        }
        PostDetailCommentAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.addData((PostDetailCommentAdapter) new CommentList(1, 0));
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$itemClickRefreshPost$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewDetailViewPagerRecyclerFragment.this.refresh();
                }
            }, 200L);
        }
        refreshCurrentPage();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void callFoucs() {
        PostList postList = this.itemData;
        int i = 1;
        if (postList != null && postList.getIsAttention() == 1) {
            i = 0;
        }
        PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
        if (postDetailCallBack != null) {
            postDetailCallBack.postAttetion(this.itemData, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack
    public void getFrameVideoController(FrameLayout frameLayout) {
        this.fragment_video_controller = frameLayout;
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact
    public void itemTagDelete(Tag tag, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        if (tag.getTagId() > 0) {
            NewCommentPresenter newCommentPresenter = (NewCommentPresenter) getMPresenter();
            if (newCommentPresenter == null) {
                return;
            }
            newCommentPresenter.deleteTag(tag.getTagId(), "", i, i2);
            return;
        }
        NewCommentPresenter newCommentPresenter2 = (NewCommentPresenter) getMPresenter();
        if (newCommentPresenter2 == null) {
            return;
        }
        String tagName = tag.getTagName();
        Intrinsics.checkExpressionValueIsNotNull(tagName, "tag.tagName");
        newCommentPresenter2.deleteTag(0, tagName, i, i2);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact$IPostCommentView
    public void handlerDeleteTagSuccess(int i) {
        NewCommentHeadView newCommentHeadView = this.headView;
        if (newCommentHeadView != null) {
            newCommentHeadView.notifyItemDeleteTag(i);
        }
    }

    public final void videoControllVisiby(boolean z) {
        PostEvenOneTabVideoListManger callPostVideoManger;
        LinearLayout linearLayout;
        if (this.currentVideoHorizontalandVertical == 1) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            if (textView == null) {
                return;
            }
            textView.setVisibility(0);
        } else if (z) {
            FrameLayout frameLayout = this.fragment_video_controller;
            if (frameLayout != null) {
                frameLayout.setVisibility(8);
            }
            FrameLayout frameLayout2 = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout2 != null && frameLayout2.getChildCount() == 0) {
                ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView3 != null) {
                    imageView3.setVisibility(0);
                }
                ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
                if (imageView4 != null) {
                    imageView4.setVisibility(0);
                }
                ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
                if (imageView5 != null) {
                    imageView5.setVisibility(8);
                }
                TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_video_time);
                if (textView2 != null) {
                    textView2.setVisibility(0);
                }
            }
            ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
            if (progressBar != null) {
                progressBar.setVisibility(0);
            }
            PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
            if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null || !callPostVideoManger.isPlayCommplete() || (linearLayout = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container)) == null) {
                return;
            }
            linearLayout.setVisibility(0);
        } else {
            FrameLayout frameLayout3 = this.fragment_video_controller;
            if (frameLayout3 != null) {
                frameLayout3.setVisibility(0);
            }
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(8);
            }
            ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
            if (imageView6 != null) {
                imageView6.setVisibility(8);
            }
            LinearLayout linearLayout3 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            FrameLayout frameLayout4 = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
            if (frameLayout4 == null || frameLayout4.getChildCount() != 0) {
                return;
            }
            ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
            if (imageView7 != null) {
                imageView7.setVisibility(0);
            }
            ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
            if (imageView8 != null) {
                imageView8.setVisibility(0);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            if (textView3 == null) {
                return;
            }
            textView3.setVisibility(0);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack
    public void getTimeProgess(String totalTime, String currentTime, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(totalTime, "totalTime");
        Intrinsics.checkParameterIsNotNull(currentTime, "currentTime");
        TextView textView = (TextView) _$_findCachedViewById(R$id.detail_curr_time);
        if (textView != null) {
            textView.setText(currentTime);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.detail_total_time);
        if (textView2 != null) {
            textView2.setText(totalTime);
        }
        SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
        if (seekBar != null) {
            seekBar.setProgress(i);
        }
        SeekBar seekBar2 = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
        if (seekBar2 != null) {
            seekBar2.setSecondaryProgress(i2);
        }
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
        if (progressBar != null) {
            progressBar.setSecondaryProgress(i2);
        }
        ProgressBar progressBar2 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
        if (progressBar2 != null) {
            progressBar2.setProgress(i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack
    public void getPlayState(int i) {
        PostEvenOneTabVideoListManger callPostVideoManger;
        if (this.currentVideoHorizontalandVertical == 1) {
            return;
        }
        PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
        if (postDetailCallBack != null && (callPostVideoManger = postDetailCallBack.callPostVideoManger()) != null && callPostVideoManger.isShowPayView()) {
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
            if (linearLayout2 == null) {
                return;
            }
            linearLayout2.setVisibility(8);
            return;
        }
        switch (i) {
            case -1:
                ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
                ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView2 != null) {
                    imageView2.setSelected(false);
                }
                ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                LinearLayout linearLayout3 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout3 == null) {
                    return;
                }
                linearLayout3.setVisibility(8);
                return;
            case 0:
                ProgressBar progressBar2 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar2 != null) {
                    progressBar2.setProgress(0);
                }
                ProgressBar progressBar3 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar3 != null) {
                    progressBar3.setSecondaryProgress(0);
                }
                SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
                if (seekBar != null) {
                    seekBar.setProgress(0);
                }
                SeekBar seekBar2 = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
                if (seekBar2 != null) {
                    seekBar2.setSecondaryProgress(0);
                }
                LinearLayout linearLayout4 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(8);
                }
                ProgressBar progressBar4 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar4 != null) {
                    progressBar4.setVisibility(8);
                }
                ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView4 != null) {
                    imageView4.setSelected(false);
                }
                ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView5 != null) {
                    imageView5.setVisibility(0);
                }
                LinearLayout linearLayout5 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout5 == null) {
                    return;
                }
                linearLayout5.setVisibility(8);
                return;
            case 1:
                LinearLayout linearLayout6 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
                if (linearLayout6 != null) {
                    linearLayout6.setVisibility(8);
                }
                ProgressBar progressBar5 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar5 != null) {
                    progressBar5.setVisibility(0);
                }
                ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView6 != null) {
                    imageView6.setSelected(false);
                }
                ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView7 != null) {
                    imageView7.setVisibility(8);
                }
                LinearLayout linearLayout7 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout7 == null) {
                    return;
                }
                linearLayout7.setVisibility(0);
                return;
            case 2:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
                return;
            case 3:
                LinearLayout linearLayout8 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
                if (linearLayout8 != null) {
                    linearLayout8.setVisibility(8);
                }
                ProgressBar progressBar6 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar6 != null) {
                    progressBar6.setVisibility(0);
                }
                ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView8 != null) {
                    imageView8.setSelected(true);
                }
                ImageView imageView9 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView9 != null) {
                    imageView9.setVisibility(8);
                }
                LinearLayout linearLayout9 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout9 == null) {
                    return;
                }
                linearLayout9.setVisibility(8);
                return;
            case 4:
                LinearLayout linearLayout10 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
                if (linearLayout10 != null) {
                    linearLayout10.setVisibility(8);
                }
                ImageView imageView10 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView10 != null) {
                    imageView10.setSelected(false);
                }
                ImageView imageView11 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView11 != null) {
                    imageView11.setVisibility(8);
                }
                LinearLayout linearLayout11 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout11 == null) {
                    return;
                }
                linearLayout11.setVisibility(0);
                return;
            case 5:
                LinearLayout linearLayout12 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
                if (linearLayout12 != null) {
                    linearLayout12.setVisibility(0);
                }
                ProgressBar progressBar7 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar7 != null) {
                    progressBar7.setVisibility(8);
                }
                ImageView imageView12 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView12 != null) {
                    imageView12.setSelected(false);
                }
                ImageView imageView13 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
                if (imageView13 != null) {
                    imageView13.setVisibility(8);
                }
                LinearLayout linearLayout13 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
                if (linearLayout13 != null) {
                    linearLayout13.setVisibility(8);
                }
                ProgressBar progressBar8 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar8 != null) {
                    progressBar8.setProgress(0);
                }
                ProgressBar progressBar9 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
                if (progressBar9 != null) {
                    progressBar9.setSecondaryProgress(0);
                }
                SeekBar seekBar3 = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
                if (seekBar3 != null) {
                    seekBar3.setProgress(0);
                }
                SeekBar seekBar4 = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
                if (seekBar4 == null) {
                    return;
                }
                seekBar4.setSecondaryProgress(0);
                return;
        }
    }

    public final void controllerClickListener() {
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$controllerClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    IjkVideoView ijkPlayInstance;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null || (ijkPlayInstance = callPostVideoManger.getIjkPlayInstance()) == null) {
                        return;
                    }
                    ijkPlayInstance.replay(true);
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$controllerClickListener$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    PostEvenOneTabVideoListManger callPostVideoManger2;
                    FrameLayout frameLayout = (FrameLayout) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.fram_ijkplay_view);
                    if (frameLayout != null && frameLayout.getChildCount() == 0) {
                        PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                        if (postDetailCallBack == null || (callPostVideoManger2 = postDetailCallBack.callPostVideoManger()) == null) {
                            return;
                        }
                        callPostVideoManger2.pressPostVideoPlay((ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.image_video_play), (TextView) NewDetailViewPagerRecyclerFragment.this._$_findCachedViewById(R$id.tv_video_time), "帖子詳情");
                        return;
                    }
                    PostDetailCallBack postDetailCallBack2 = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack2 == null || (callPostVideoManger = postDetailCallBack2.callPostVideoManger()) == null) {
                        return;
                    }
                    callPostVideoManger.doPauseResume();
                }
            });
        }
        SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$controllerClickListener$3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null) {
                        return;
                    }
                    callPostVideoManger.onProgressChanged(seekBar2, i, z);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null) {
                        return;
                    }
                    callPostVideoManger.onStartTrackingTouch(seekBar2);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                    PostEvenOneTabVideoListManger callPostVideoManger;
                    PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment.this.getPostDetailCallBack();
                    if (postDetailCallBack == null || (callPostVideoManger = postDetailCallBack.callPostVideoManger()) == null) {
                        return;
                    }
                    callPostVideoManger.onStopTrackingTouch(seekBar2);
                }
            });
        }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) _$_findCachedViewById(R$id.coll_toolbar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setOnClickListener(NewDetailViewPagerRecyclerFragment$controllerClickListener$4.INSTANCE);
        }
    }

    public final void reloadResetState() {
        PostList postList = this.itemData;
        if (postList == null || postList.getPostType() != 2) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
        if (imageView != null) {
            imageView.setSelected(false);
        }
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
        ProgressBar progressBar2 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
        if (progressBar2 != null) {
            progressBar2.setSecondaryProgress(0);
        }
        SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
        if (seekBar != null) {
            seekBar.setProgress(0);
        }
        SeekBar seekBar2 = (SeekBar) _$_findCachedViewById(R$id.detail_seekBar);
        if (seekBar2 != null) {
            seekBar2.setSecondaryProgress(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.detail_complete_container);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        ProgressBar progressBar3 = (ProgressBar) _$_findCachedViewById(R$id.detail_bottom_progress);
        if (progressBar3 != null) {
            progressBar3.setVisibility(8);
        }
        if (this.currentVideoHorizontalandVertical == 2) {
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
            if (imageView2 == null) {
                return;
            }
            imageView2.setVisibility(0);
            return;
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
        if (imageView3 == null) {
            return;
        }
        imageView3.setVisibility(8);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack
    public void isShowOrHide(boolean z) {
        MNGestureView mNView;
        if (z) {
            PostDetailCallBack postDetailCallBack = this.postDetailCallBack;
            if (!Intrinsics.areEqual((postDetailCallBack == null || (mNView = postDetailCallBack.getMNView()) == null) ? null : Boolean.valueOf(mNView.isAppBarEx), true)) {
                return;
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(0);
            return;
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.liner_detail_progress);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_detail_play);
        if (imageView2 == null) {
            return;
        }
        imageView2.setVisibility(8);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        Disposable disposable;
        Disposable disposable2;
        super.onDestroy();
        Disposable disposable3 = this.foucsSubscribe;
        if (disposable3 != null && !disposable3.isDisposed() && (disposable2 = this.foucsSubscribe) != null) {
            disposable2.dispose();
        }
        Disposable disposable4 = this.collectSubscribe;
        if (disposable4 == null || disposable4.isDisposed() || (disposable = this.collectSubscribe) == null) {
            return;
        }
        disposable.dispose();
    }
}
