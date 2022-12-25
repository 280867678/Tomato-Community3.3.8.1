package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.NotificationCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.PostDetailListDataEvent;
import com.one.tomato.entity.event.PostRefreshEvent;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.view.AlphaFragment;
import com.one.tomato.mvp.p080ui.post.adapter.NewDetailViewPagerAdapter;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack;
import com.one.tomato.mvp.p080ui.post.presenter.NewPostTabListPresenter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.utils.PostVideoMenuUtils;
import com.one.tomato.mvp.p080ui.post.view.NewDetailViewPagerRecyclerFragment;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.widget.image.MNGestureView;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Standard;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPostDetailViewPagerActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostDetailViewPagerActivity */
/* loaded from: classes3.dex */
public final class NewPostDetailViewPagerActivity extends MvpBaseActivity<IPostTabListContact$IPostTabListView, NewPostTabListPresenter> implements IPostTabListContact$IPostTabListView, PostDetailCallBack, NewPostItemOnClickCallBack {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private NewDetailViewPagerAdapter adapater;
    private String businessType;
    private long currentPostion;
    private int groundId;
    private boolean isInit;
    private boolean isScrollTop;
    private boolean isSuporrtLoadMore;
    private int personMemberId;
    private PostEvenOneTabVideoListManger postVideoManger;
    private int singlePostId;
    private int category = -1;
    private int channelId = -1;
    private String seachKey = "";
    private int pageNo = 1;
    private int pageSize = 10;
    private int currentIndex = 1;
    private ArrayList<PostList> postListData = new ArrayList<>();
    private final ArrayList<Fragment> listFragment = new ArrayList<>();

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
        return R.layout.activity_new_post_detail_view_pager;
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerDeleteTagSuccess(int i, int i2) {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemNeedPay(PostList postList) {
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemTagDelete(Tag tag, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void jumpPostDetailActivity(int i) {
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getCategory() {
        return this.category;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getChannelId() {
        return this.channelId;
    }

    /* compiled from: NewPostDetailViewPagerActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.NewPostDetailViewPagerActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, ArrayList<PostList> arrayList, int i, int i2, String str, int i3, boolean z, Bundle bundle, int i4, int i5, int i6, String str2) {
            Intent intent = new Intent(context, NewPostDetailViewPagerActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList(AopConstants.APP_PROPERTIES_KEY, arrayList);
            bundle2.putInt("pageNum", i3);
            bundle2.putInt("category", i);
            bundle2.putString("business", str);
            bundle2.putBoolean("scroll_comment_top", z);
            bundle2.putInt("person_id", i4);
            bundle2.putInt("groundId", i5);
            bundle2.putInt(ConstantUtils.TAB_TAG_ID, i6);
            bundle2.putString("seachKey", str2);
            bundle2.putInt("channel_id", i2);
            intent.putExtras(bundle2);
            if (context != null) {
                context.startActivity(intent);
            }
        }

        public final void startActivity(Context context, ArrayList<PostList> arrayList, int i, int i2, String str, int i3, boolean z, Bundle bundle, int i4, int i5, int i6, String str2, long j) {
            Intent intent = new Intent(context, NewPostDetailViewPagerActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList(AopConstants.APP_PROPERTIES_KEY, arrayList);
            bundle2.putInt("pageNum", i3);
            bundle2.putInt("category", i);
            bundle2.putString("business", str);
            bundle2.putBoolean("scroll_comment_top", z);
            bundle2.putInt("person_id", i4);
            bundle2.putInt("groundId", i5);
            bundle2.putInt(ConstantUtils.TAB_TAG_ID, i6);
            bundle2.putString("seachKey", str2);
            bundle2.putLong("video_postion", j);
            bundle2.putInt("channel_id", i2);
            intent.putExtras(bundle2);
            if (context != null) {
                context.startActivity(intent);
            }
        }

        public final void startActivity(Context context, int i, boolean z, boolean z2, boolean z3) {
            Intent intent = new Intent(context, NewPostDetailViewPagerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("post_id", i);
            bundle.putBoolean("scroll_comment_top", z);
            bundle.putBoolean("isOfflinePost", z2);
            if (z3) {
                intent.setFlags(268435456);
                intent.setFlags(67108864);
            }
            intent.putExtras(bundle);
            if (context != null) {
                context.startActivity(intent);
            }
        }

        public final void startActivity(Context context, ArrayList<PostList> arrayList, int i, int i2, String str, int i3, boolean z, Bundle bundle, int i4, int i5, int i6, String str2, boolean z2) {
            Intent intent = new Intent(context, NewPostDetailViewPagerActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList(AopConstants.APP_PROPERTIES_KEY, arrayList);
            bundle2.putInt("pageNum", i3);
            bundle2.putInt("category", i);
            bundle2.putString("business", str);
            bundle2.putBoolean("scroll_comment_top", z);
            bundle2.putInt("person_id", i4);
            bundle2.putInt("groundId", i5);
            bundle2.putInt(ConstantUtils.TAB_TAG_ID, i6);
            bundle2.putString("seachKey", str2);
            bundle2.putBoolean("isLoadmore", z2);
            bundle2.putInt("channel_id", i2);
            intent.putExtras(bundle2);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList<PostList> arrayList2 = this.postListData;
        int size = arrayList2 != null ? arrayList2.size() : 0;
        int size2 = arrayList.size();
        for (int i = 0; i < size2; i++) {
            int i2 = size > 0 ? size + i : 0;
            NewDetailViewPagerRecyclerFragment.Companion companion = NewDetailViewPagerRecyclerFragment.Companion;
            PostList postList = arrayList.get(i);
            Intrinsics.checkExpressionValueIsNotNull(postList, "dataList[it]");
            NewDetailViewPagerRecyclerFragment companion2 = companion.getInstance(postList, i2 + 1, false, this.businessType);
            companion2.setPostDetailCallBack(this);
            this.listFragment.add(companion2);
        }
        this.pageNo++;
        ArrayList<PostList> arrayList3 = this.postListData;
        if (arrayList3 != null) {
            arrayList3.addAll(arrayList);
        }
        NewDetailViewPagerAdapter newDetailViewPagerAdapter = this.adapater;
        if (newDetailViewPagerAdapter == null) {
            return;
        }
        newDetailViewPagerAdapter.notifyDataSetChanged();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerInsertAd(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void HandlerPostMainNotify(ArrayList<MainNotifyBean> arrayList) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerRemoveItem(int i) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewPostTabListPresenter mo6439createPresenter() {
        return new NewPostTabListPresenter();
    }

    public final void loadMore() {
        requestType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.one.tomato.mvp.ui.papa.view.AlphaFragment] */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        String str;
        ?? companion;
        this.postVideoManger = new PostEvenOneTabVideoListManger();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postVideoManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.setNewPostItemClickCallBack(this);
        }
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger2 = this.postVideoManger;
        if (postEvenOneTabVideoListManger2 != null) {
            postEvenOneTabVideoListManger2.initVideoCommonManger(this);
        }
        Intent intent = getIntent();
        Bundle extras = intent != null ? intent.getExtras() : null;
        this.category = extras != null ? extras.getInt("category") : -1;
        this.businessType = extras != null ? extras.getString("business") : null;
        this.pageNo = extras != null ? extras.getInt("pageNum") : 1;
        this.personMemberId = extras != null ? extras.getInt("person_id") : 0;
        this.groundId = extras != null ? extras.getInt("groundId") : 0;
        if (extras != null) {
            extras.getInt(ConstantUtils.TAB_TAG_ID);
        }
        if (extras == null || (str = extras.getString("seachKey")) == null) {
            str = "";
        }
        this.seachKey = str;
        ArrayList<PostList> parcelableArrayList = extras != null ? extras.getParcelableArrayList(AopConstants.APP_PROPERTIES_KEY) : null;
        if (parcelableArrayList != null) {
            this.postListData = parcelableArrayList;
        }
        this.isScrollTop = extras != null ? extras.getBoolean("scroll_comment_top") : false;
        if (extras != null) {
            extras.getBoolean("isOfflinePost");
        }
        this.channelId = extras != null ? extras.getInt("channel_id") : -1;
        this.currentPostion = extras != null ? extras.getLong("video_postion") : 0L;
        this.singlePostId = extras != null ? extras.getInt("post_id") : 0;
        this.isSuporrtLoadMore = extras != null ? extras.getBoolean("isLoadmore") : false;
        Intent intent2 = getIntent();
        if (Intrinsics.areEqual(intent2 != null ? intent2.getAction() : null, "android.intent.action.VIEW")) {
            Intent intent3 = getIntent();
            this.singlePostId = intent3 != null ? intent3.getIntExtra("postId", 0) : 0;
            Intent intent4 = getIntent();
            Uri data = intent4 != null ? intent4.getData() : null;
            String queryParameter = data != null ? data.getQueryParameter("postId") : null;
            this.singlePostId = queryParameter != null ? Integer.parseInt(queryParameter) : 0;
        }
        PostList postList = new PostList();
        postList.setId(-100);
        ArrayList<PostList> arrayList = this.postListData;
        if (arrayList != null) {
            arrayList.add(0, postList);
        }
        addListener();
        if (this.singlePostId != 0) {
            this.channelId = -1;
            this.category = -1;
            PostList postList2 = new PostList();
            postList2.setId(this.singlePostId);
            ArrayList<PostList> arrayList2 = this.postListData;
            if (arrayList2 != null) {
                arrayList2.add(1, postList2);
            }
        }
        ArrayList<PostList> arrayList3 = this.postListData;
        if (arrayList3 != null) {
            if ((arrayList3 != null ? arrayList3.size() : 0) > 0) {
                ArrayList<PostList> arrayList4 = this.postListData;
                if (arrayList4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                Iterator<PostList> it2 = arrayList4.iterator();
                while (it2.hasNext()) {
                    PostList it3 = it2.next();
                    ArrayList<PostList> arrayList5 = this.postListData;
                    if (arrayList5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    if (arrayList5.indexOf(it3) == 0) {
                        companion = new AlphaFragment();
                    } else {
                        ArrayList<PostList> arrayList6 = this.postListData;
                        if (arrayList6 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (arrayList6.indexOf(it3) == 1) {
                            NewDetailViewPagerRecyclerFragment.Companion companion2 = NewDetailViewPagerRecyclerFragment.Companion;
                            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                            ArrayList<PostList> arrayList7 = this.postListData;
                            if (arrayList7 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            } else {
                                companion = companion2.getInstance(it3, arrayList7.indexOf(it3), this.isScrollTop, this.businessType);
                                companion.setPostDetailCallBack(this);
                            }
                        } else {
                            NewDetailViewPagerRecyclerFragment.Companion companion3 = NewDetailViewPagerRecyclerFragment.Companion;
                            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                            ArrayList<PostList> arrayList8 = this.postListData;
                            if (arrayList8 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            } else {
                                companion = companion3.getInstance(it3, arrayList8.indexOf(it3), false, this.businessType);
                                companion.setPostDetailCallBack(this);
                            }
                        }
                    }
                    this.listFragment.add(companion);
                }
            }
        }
        this.isInit = true;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkExpressionValueIsNotNull(supportFragmentManager, "supportFragmentManager");
        this.adapater = new NewDetailViewPagerAdapter(supportFragmentManager, this.listFragment);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.adapater);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setOffscreenPageLimit(1);
        }
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setCurrentItem(1);
        }
    }

    public final void addListener() {
        MNGestureView mNGestureView = (MNGestureView) _$_findCachedViewById(R$id.detail_mn_view);
        if (mNGestureView != null) {
            mNGestureView.setOnSwipeListener(new MNGestureView.OnSwipeListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostDetailViewPagerActivity$addListener$1
                @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
                public void downSwipe() {
                    NewPostDetailViewPagerActivity.this.onBackPressed();
                }

                @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
                public void overSwipe() {
                    RelativeLayout relativeLayout = (RelativeLayout) NewPostDetailViewPagerActivity.this.findViewById(R.id.post_activity_background);
                    if (relativeLayout != null) {
                        relativeLayout.setAlpha(1.0f);
                    }
                }

                @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
                public void onSwiping(float f) {
                    if (NewPostDetailViewPagerActivity.this.getCategory() != -1 || NewPostDetailViewPagerActivity.this.getChannelId() > 0) {
                        NewPostDetailViewPagerActivity.this.sendResultData(true);
                    }
                    float f2 = 1;
                    float f3 = f2 - (f / 500);
                    if (f3 < 0.3d) {
                        f3 = 0.3f;
                    }
                    if (f3 > f2) {
                        f3 = 1.0f;
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) NewPostDetailViewPagerActivity.this.findViewById(R.id.post_activity_background);
                    if (relativeLayout != null) {
                        relativeLayout.setAlpha(f3);
                    }
                }
            });
        }
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        if (previewViewPager != null) {
            previewViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostDetailViewPagerActivity$addListener$2
                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                    LogUtil.m3787d("yang", "拖動的方向---------");
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                    LogUtil.m3787d("yang", "拖動的方向---------" + f);
                    RelativeLayout relativeLayout = (RelativeLayout) NewPostDetailViewPagerActivity.this.findViewById(R.id.post_activity_background);
                    if (relativeLayout != null) {
                        relativeLayout.setAlpha(0.0f);
                    }
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    ArrayList arrayList;
                    boolean z;
                    ArrayList arrayList2;
                    boolean z2;
                    LogUtil.m3787d("yang", "拖動的方向---------" + i);
                    if (i != 0) {
                        arrayList = NewPostDetailViewPagerActivity.this.postListData;
                        if (arrayList != null) {
                            if (NewPostDetailViewPagerActivity.this.getCategory() != -1 || NewPostDetailViewPagerActivity.this.getChannelId() > 0) {
                                arrayList2 = NewPostDetailViewPagerActivity.this.postListData;
                                if (arrayList2 == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                } else if (i == arrayList2.size() - 1) {
                                    NewPostDetailViewPagerActivity.this.loadMore();
                                }
                            }
                            z2 = NewPostDetailViewPagerActivity.this.isSuporrtLoadMore;
                            if (z2) {
                                NewPostDetailViewPagerActivity.this.loadMore();
                            }
                        }
                        z = NewPostDetailViewPagerActivity.this.isInit;
                        if (z) {
                            NewPostDetailViewPagerActivity.this.isInit = false;
                            return;
                        } else if (NewPostDetailViewPagerActivity.this.getCategory() == -1 && NewPostDetailViewPagerActivity.this.getChannelId() <= 0) {
                            return;
                        } else {
                            NewPostDetailViewPagerActivity.this.sendResultData(false);
                            return;
                        }
                    }
                    NewPostDetailViewPagerActivity.this.onBackPressed();
                }
            });
        }
    }

    public final void sendResultData(boolean z) {
        getCurrentIndexView();
        PostDetailListDataEvent postDetailListDataEvent = new PostDetailListDataEvent();
        postDetailListDataEvent.setPageNo(this.pageNo);
        postDetailListDataEvent.setCategory(this.category);
        postDetailListDataEvent.setShow(z);
        postDetailListDataEvent.setChannelId(this.channelId);
        postDetailListDataEvent.setPostion(this.currentIndex - 1);
        ArrayList<PostList> arrayList = this.postListData;
        if ((arrayList != null ? arrayList.size() : 0) > 1) {
            ArrayList<PostList> arrayList2 = this.postListData;
            postDetailListDataEvent.setPostList(arrayList2 != null ? arrayList2.get(this.currentIndex) : null);
        }
        RxBus.getDefault().post(postDetailListDataEvent);
    }

    public final void getCurrentIndexView() {
        NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment;
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        Integer valueOf = previewViewPager != null ? Integer.valueOf(previewViewPager.getCurrentItem()) : null;
        if (valueOf != null) {
            if (valueOf.intValue() == 0) {
                this.currentIndex = 1;
            } else {
                this.currentIndex = valueOf.intValue();
            }
            if (this.listFragment.size() <= 1 || !(this.listFragment.get(this.currentIndex) instanceof NewDetailViewPagerRecyclerFragment) || (newDetailViewPagerRecyclerFragment = (NewDetailViewPagerRecyclerFragment) this.listFragment.get(this.currentIndex)) == null) {
                return;
            }
            newDetailViewPagerRecyclerFragment.getShareViewFormFragment();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public MNGestureView getMNView() {
        return (MNGestureView) _$_findCachedViewById(R$id.detail_mn_view);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void postAttetion(PostList postList, int i) {
        NewPostTabListPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.foucs(postList, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void callJumpAd(PostList postList) {
        NewPostTabListPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.startPostAd(postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void callTumb(PostList postList) {
        NewPostTabListPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.thumbUp(postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void callCollect(PostList postList) {
        NewPostTabListPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.collect(postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void callFragmentToActVideoMenu(PostList postList) {
        callVideoDialog(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemShare(PostList postList) {
        if (postList != null) {
            PostShareActivity.Companion.startAct(getMContext(), postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public boolean itemClickZan(PostList postList, int i) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemShowAuthInfo(PostList postList, int i) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemADClick(PostList postList) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemHead(PostList postList) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemConmment(PostList postList, int i, View view) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemDelete(PostList postList, int i, View view) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemCircle(PostList postList) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemClick(PostList postList, View view, int i) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostCollect(PostList postList) {
        callCollect(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostFoucs(PostList postList) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostPayComplete(PostList postList) {
        int i;
        if (postList != null) {
            ArrayList<PostList> arrayList = this.postListData;
            if (arrayList != null) {
                i = -1;
                int i2 = 0;
                for (Object obj : arrayList) {
                    int i3 = i2 + 1;
                    if (i2 >= 0) {
                        if (postList.getId() == ((PostList) obj).getId()) {
                            i = i2;
                        }
                        i2 = i3;
                    } else {
                        CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                }
            } else {
                i = -1;
            }
            if (i == -1) {
                return;
            }
            ArrayList<PostList> arrayList2 = this.postListData;
            if (arrayList2 != null) {
                arrayList2.set(i, postList);
            }
            refreshListData(postList);
            for (Fragment fragment : this.listFragment) {
                if (fragment instanceof NewDetailViewPagerRecyclerFragment) {
                    NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment = (NewDetailViewPagerRecyclerFragment) fragment;
                    PostList itemData = newDetailViewPagerRecyclerFragment.getItemData();
                    int id = postList.getId();
                    if (itemData != null && id == itemData.getId()) {
                        newDetailViewPagerRecyclerFragment.setItemData(postList);
                        return;
                    }
                }
            }
        }
    }

    private final void refreshListData(PostList postList) {
        if (postList != null) {
            RxBus.getDefault().post(new PostRefreshEvent(postList, this.category, this.channelId));
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public void callPostItemPayCom(PostList postList) {
        itemPostPayComplete(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void callVideoDialog(PostList postList) {
        Context mContext = getMContext();
        if (mContext != null) {
            new PostVideoMenuUtils(mContext, postList, this).showMenuDialog();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public PostEvenOneTabVideoListManger callPostVideoManger() {
        return this.postVideoManger;
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public long callPostVideoPostion() {
        return this.currentPostion;
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack
    public boolean callCurrentIndex(NewDetailViewPagerRecyclerFragment fragement) {
        Intrinsics.checkParameterIsNotNull(fragement, "fragement");
        int indexOf = this.listFragment.indexOf(fragement);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.detail_view_pager);
        return previewViewPager != null && previewViewPager.getCurrentItem() == indexOf;
    }

    @Override // android.app.Activity
    public void finishAfterTransition() {
        super.finishAfterTransition();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (PostUtils.INSTANCE.notifyBackObserver(true)) {
            return;
        }
        if (this.category != -1 || this.channelId > 0) {
            sendResultData(true);
        }
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void requestType() {
        NewPostTabListPresenter mPresenter;
        NewPostTabListPresenter mPresenter2;
        NewPostTabListPresenter mPresenter3;
        NewPostTabListPresenter mPresenter4;
        NewPostTabListPresenter mPresenter5;
        NewPostTabListPresenter mPresenter6;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(this.pageNo));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        int i = this.category;
        if (i > 0) {
            linkedHashMap.put("postType", Integer.valueOf(i));
        }
        int i2 = this.channelId;
        if (i2 > 0) {
            linkedHashMap.put("channelId", Integer.valueOf(i2));
            NewPostTabListPresenter mPresenter7 = getMPresenter();
            if (mPresenter7 == null) {
                return;
            }
            mPresenter7.requestPostToChannelId(linkedHashMap);
            return;
        }
        String str = this.businessType;
        if (str == null) {
            return;
        }
        switch (str.hashCode()) {
            case -1791534392:
                if (!str.equals("postSearch0")) {
                    return;
                }
                linkedHashMap.put("key", this.seachKey);
                mPresenter6 = getMPresenter();
                if (mPresenter6 != null) {
                    return;
                }
                mPresenter6.requestSerachPost(linkedHashMap);
                return;
            case -1360216880:
                if (!str.equals(C2516Ad.TYPE_CIRCLE)) {
                    return;
                }
                linkedHashMap.put("groupId", Integer.valueOf(this.groundId));
                NewPostTabListPresenter mPresenter8 = getMPresenter();
                if (mPresenter8 == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                DomainServer domainServer = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                sb.append(domainServer.getServerUrl());
                sb.append("/app/article/group/list");
                mPresenter8.requestCirclePost(sb.toString(), linkedHashMap);
                return;
            case -1110540878:
                if (!str.equals("circle_all")) {
                    return;
                }
                linkedHashMap.put("postType", 2);
                linkedHashMap.put("groupId", Integer.valueOf(this.groundId));
                NewPostTabListPresenter mPresenter9 = getMPresenter();
                if (mPresenter9 == null) {
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                DomainServer domainServer2 = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
                sb2.append(domainServer2.getServerUrl());
                sb2.append("/app/article/group/list");
                mPresenter9.requestCirclePost(sb2.toString(), linkedHashMap);
                return;
            case -485371922:
                if (!str.equals("homepage")) {
                    return;
                }
                linkedHashMap.put("personMemberId", Integer.valueOf(this.personMemberId));
                NewPostTabListPresenter mPresenter10 = getMPresenter();
                if (mPresenter10 == null) {
                    return;
                }
                mPresenter10.requestPersonPost(linkedHashMap);
                return;
            case 104387:
                if (!str.equals("img")) {
                    return;
                }
                mPresenter3 = getMPresenter();
                if (mPresenter3 == null) {
                    return;
                }
                mPresenter3.requestImageVideoPostList(linkedHashMap);
                return;
            case 3496342:
                if (!str.equals("read")) {
                    return;
                }
                mPresenter3 = getMPresenter();
                if (mPresenter3 == null) {
                }
                break;
            case 97604824:
                if (!str.equals("focus") || (mPresenter = getMPresenter()) == null) {
                    return;
                }
                mPresenter.requestFoucsPostList(linkedHashMap);
                return;
            case 110342614:
                if (!str.equals("thumb") || (mPresenter2 = getMPresenter()) == null) {
                    return;
                }
                mPresenter2.requestThumbPost(linkedHashMap);
                return;
            case 112202875:
                if (!str.equals("video")) {
                    return;
                }
                mPresenter3 = getMPresenter();
                if (mPresenter3 == null) {
                }
                break;
            case 949444906:
                if (!str.equals("collect") || (mPresenter4 = getMPresenter()) == null) {
                    return;
                }
                mPresenter4.requestCollectPost(linkedHashMap);
                return;
            case 989204668:
                if (!str.equals("recommend") || (mPresenter5 = getMPresenter()) == null) {
                    return;
                }
                mPresenter5.requestRecommendPostList(linkedHashMap);
                return;
            case 1447403838:
                if (!str.equals("publish_n")) {
                    return;
                }
                linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, "0");
                NewPostTabListPresenter mPresenter11 = getMPresenter();
                if (mPresenter11 == null) {
                    return;
                }
                mPresenter11.requestPushPost(linkedHashMap);
                return;
            case 1447403849:
                if (!str.equals("publish_y")) {
                    return;
                }
                linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, "1");
                NewPostTabListPresenter mPresenter12 = getMPresenter();
                if (mPresenter12 == null) {
                    return;
                }
                mPresenter12.requestPushPost(linkedHashMap);
                return;
            case 1604776552:
                if (!str.equals("postSearch")) {
                    return;
                }
                linkedHashMap.put("key", this.seachKey);
                mPresenter6 = getMPresenter();
                if (mPresenter6 != null) {
                }
                break;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postVideoManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.activityOnStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postVideoManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.activityOnStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postVideoManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.activityOnResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.postVideoManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.onReleaseVideoManger();
        }
    }
}
