package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.http.RequestParams;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StartReviewPostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity */
/* loaded from: classes3.dex */
public final class StartReviewPostActivity extends MvpBaseRecyclerViewActivity<IBaseView, MvpBasePresenter<IBaseView>, NewPostTabListAdapter, PostList> {
    private HashMap _$_findViewCache;
    private int currentIndex;
    private final ArrayList<PostList> originData = new ArrayList<>();
    private PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_start_review_post;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public NewPostTabListAdapter mo6446createAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            return new NewPostTabListAdapter(mContext, getRecyclerView(), new StartReviewCallBack(), this.postEvenOneTabVideoListManger);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
        setState(getGET_LIST_LOAD());
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        new StartReviewCallBack();
        this.postEvenOneTabVideoListManger = new PostEvenOneTabVideoListManger();
        super.initView();
        initTitleBar();
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setBusinessType("review_post");
        }
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postEvenOneTabVideoListManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.setIsReviewPost(true);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setOnClickListener(new StartReviewPostActivity$initView$1(this));
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StartReviewPostActivity.this.initData();
                }
            });
        }
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StartReviewPostActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageLoaderUtil.loadNormalDrawableGif(getMContext(), (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main), R.drawable.refresh_empty_load_gif);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        requestData(true);
    }

    private final void requestData(final boolean z) {
        String url;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        String serverUrl = domainServer.getServerUrl();
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        if (userInfo.isOfficial()) {
            url = serverUrl + "/app/officialAuditor/listPending";
        } else {
            url = serverUrl + "/app/userAuditor/listPending";
        }
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        Intrinsics.checkExpressionValueIsNotNull(url, "url");
        apiImplService.getReviewPostPre(url, linkedHashMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                int state;
                RecyclerView recyclerView;
                ArrayList arrayList2;
                RecyclerView recyclerView2;
                LogUtil.m3787d("yan", "t" + arrayList);
                StartReviewPostActivity.this.cancelRefresh();
                state = StartReviewPostActivity.this.getState();
                boolean z2 = true;
                if (state == StartReviewPostActivity.this.getGET_LIST_REFRESH()) {
                    if (arrayList == null || arrayList.isEmpty()) {
                        recyclerView2 = StartReviewPostActivity.this.getRecyclerView();
                        if (recyclerView2 != null) {
                            recyclerView2.setVisibility(8);
                        }
                        RelativeLayout relativeLayout = (RelativeLayout) StartReviewPostActivity.this._$_findCachedViewById(R$id.relate_no_data);
                        if (relativeLayout == null) {
                            return;
                        }
                        relativeLayout.setVisibility(0);
                        return;
                    }
                }
                recyclerView = StartReviewPostActivity.this.getRecyclerView();
                if (recyclerView != null) {
                    recyclerView.setVisibility(0);
                }
                RelativeLayout relativeLayout2 = (RelativeLayout) StartReviewPostActivity.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(8);
                }
                Button button = (Button) StartReviewPostActivity.this._$_findCachedViewById(R$id.button);
                if (button != null) {
                    button.setVisibility(0);
                }
                if (z) {
                    StartReviewPostActivity.this.distributionData(arrayList);
                    return;
                }
                if (arrayList != null && !arrayList.isEmpty()) {
                    z2 = false;
                }
                if (z2) {
                    return;
                }
                arrayList2 = StartReviewPostActivity.this.originData;
                arrayList2.addAll(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RecyclerView recyclerView;
                StringBuilder sb = new StringBuilder();
                sb.append("获取频道帖子报错e");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
                StartReviewPostActivity.this.cancelRefresh();
                recyclerView = StartReviewPostActivity.this.getRecyclerView();
                if (recyclerView != null) {
                    recyclerView.setVisibility(8);
                }
                Button button = (Button) StartReviewPostActivity.this._$_findCachedViewById(R$id.button);
                if (button != null) {
                    button.setVisibility(8);
                }
                RelativeLayout relativeLayout = (RelativeLayout) StartReviewPostActivity.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelRefresh() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void distributionData(ArrayList<PostList> arrayList) {
        if (!(arrayList == null || arrayList.isEmpty())) {
            this.originData.addAll(arrayList);
            PostList postList = this.originData.get(this.currentIndex);
            Intrinsics.checkExpressionValueIsNotNull(postList, "originData[currentIndex]");
            addToList(postList);
        }
    }

    private final void addToList(PostList postList) {
        List<PostList> data;
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null && (data = adapter.getData()) != null) {
            data.clear();
        }
        NewPostTabListAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.addData((NewPostTabListAdapter) postList);
        }
        NewPostTabListAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.setEnableLoadMore(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void nextPost() {
        List<PostList> data;
        this.currentIndex++;
        int size = this.originData.size();
        int i = this.currentIndex;
        if (size > i) {
            PostList postList = this.originData.get(i);
            Intrinsics.checkExpressionValueIsNotNull(postList, "originData[currentIndex]");
            addToList(postList);
        }
        if (this.currentIndex > this.originData.size() - 2 && this.currentIndex < this.originData.size()) {
            requestData(false);
        }
        if (this.currentIndex >= this.originData.size()) {
            NewPostTabListAdapter adapter = getAdapter();
            if (adapter != null && (data = adapter.getData()) != null) {
                data.clear();
            }
            NewPostTabListAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                adapter2.notifyDataSetChanged();
            }
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setVisibility(8);
            }
            Button button = (Button) _$_findCachedViewById(R$id.button);
            if (button != null) {
                button.setVisibility(8);
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.setVisibility(0);
        }
    }

    /* compiled from: StartReviewPostActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$StartReviewCallBack */
    /* loaded from: classes3.dex */
    public final class StartReviewCallBack implements NewPostItemOnClickCallBack {
        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void callVideoDialog(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemADClick(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemClick(PostList postList, View view, int i) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public boolean itemClickZan(PostList postList, int i) {
            return false;
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemConmment(PostList postList, int i, View view) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemDelete(PostList postList, int i, View view) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemNeedPay(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemPostCollect(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemPostFoucs(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemPostPayComplete(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemShare(PostList postList) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemShowAuthInfo(PostList postList, int i) {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void jumpPostDetailActivity(int i) {
        }

        public StartReviewCallBack() {
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemHead(PostList postList) {
            Context mContext;
            if (postList == null || (mContext = StartReviewPostActivity.this.getMContext()) == null) {
                return;
            }
            NewMyHomePageActivity.Companion.startActivity(mContext, postList.getMemberId());
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemCircle(PostList postList) {
            if (postList != null) {
                CircleDetail circleDetail = new CircleDetail();
                circleDetail.setName(postList.getGroupName());
                circleDetail.setId(postList.getGroupId());
                CircleSingleActivity.startActivity(StartReviewPostActivity.this.getMContext(), circleDetail);
            }
        }

        @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
        public void itemTagDelete(Tag tag, int i, int i2, int i3) {
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            if (tag.getTagId() > 0) {
                StartReviewPostActivity.this.deleteTag(tag.getTagId(), "", i, i2, i3);
                return;
            }
            StartReviewPostActivity startReviewPostActivity = StartReviewPostActivity.this;
            String tagName = tag.getTagName();
            Intrinsics.checkExpressionValueIsNotNull(tagName, "tag.tagName");
            startReviewPostActivity.deleteTag(0, tagName, i, i2, i3);
        }
    }

    public final void deleteTag(int i, String tagName, int i2, final int i3, final int i4) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        ApiImplService.Companion.getApiImplService().deleteTag(i, tagName, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$deleteTag$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                StartReviewPostActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$deleteTag$2
            /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
                r0 = r2.this$0.getAdapter();
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResult(Object obj) {
                NewPostTabListAdapter adapter;
                NewPostTabListAdapter adapter2;
                NewPostTabListAdapter adapter3;
                PostList item;
                ArrayList<Tag> tagList;
                StartReviewPostActivity.this.hideWaitingDialog();
                try {
                    adapter = StartReviewPostActivity.this.getAdapter();
                    if (adapter != null && (item = adapter.getItem(i4)) != null && (tagList = item.getTagList()) != null) {
                        tagList.remove(i3);
                    }
                    adapter2 = StartReviewPostActivity.this.getAdapter();
                    PostList item2 = adapter2 != null ? adapter2.getItem(i4) : null;
                    if (item2 == null || adapter3 == null) {
                        return;
                    }
                    adapter3.setData(i4, item2);
                } catch (Exception unused) {
                    LogUtil.m3788d("yan");
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StartReviewPostActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postEvenOneTabVideoListManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.onReleaseVideoManger();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postEvenOneTabVideoListManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.activityOnStop();
        }
    }
}
