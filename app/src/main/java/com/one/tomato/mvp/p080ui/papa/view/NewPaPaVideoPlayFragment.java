package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.R$id;
import com.one.tomato.adapter.PapaVideoListAdapter;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.PapaCommentDialog;
import com.one.tomato.dialog.PostRewardDialog;
import com.one.tomato.dialog.VideoCacheTipDialog;
import com.one.tomato.dialog.VideoSaveIngDialog;
import com.one.tomato.dialog.VideoSaveTipDialog;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.entity.event.PostCollectOrThumbEvent;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.p082up.view.UpSubscribeActivity;
import com.one.tomato.mvp.p080ui.papa.contants.PaPaContants;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact;
import com.one.tomato.mvp.p080ui.papa.presenter.NewPaPaVideoPalyPresenter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.p085ui.mine.MyShareActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.ipfs.IpfsUtil;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener;
import com.one.tomato.thirdpart.recyclerview.ViewPagerLayoutManager;
import com.one.tomato.thirdpart.video.controller.PapaVideoController;
import com.one.tomato.thirdpart.video.player.PapaVideoView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.utils.post.PapaPublishUtil;
import com.one.tomato.utils.post.VideoDownloadCountUtils;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import de.greenrobot.event.EventBus;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPaPaVideoPlayFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment */
/* loaded from: classes3.dex */
public final class NewPaPaVideoPlayFragment extends MvpBaseRecyclerViewFragment<IPaPaPlayContact, NewPaPaVideoPalyPresenter, PapaVideoListAdapter, PostList> implements IPaPaPlayContact {
    private HashMap _$_findViewCache;
    private int adPageIndex;
    private AdUtil adUtil;
    private String businessType;
    private int curPosition;
    private int downloadType;
    private int insertLoopNum;
    private boolean isAlreadyUpData;
    private boolean isAlreadyUpVideoCom;
    private boolean isAlreadyUpVideoHalf;
    private boolean isResume;
    private ArrayList<AdPage> listPages;
    private boolean needVirtualConsume;
    private Function1<? super Boolean, Unit> observerPostIsAd;
    private PapaCommentDialog papaCommentDialog;
    private PapaVideoController papaVideoController;
    private PapaVideoView papaVideoView;
    private int personMemberId;
    private PostList postList;
    private ArrayList<PostList> postListArrayList;
    private String searchKey;
    private int startPosition;
    private VideoCacheTipDialog videoCacheTipDialog;
    private VideoSaveTipDialog videoSaveTipDialog;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    private int insertPageIndex = 1;
    private boolean subLookTime = true;
    private boolean isPostPay = true;
    private final ArrayList<PostList> uploadData = new ArrayList<>();
    private final NewPaPaVideoPlayFragment$papaListener$1 papaListener = new PapaPublishUtil.PublishListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$papaListener$1
        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishDefault() {
            ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.video_publish);
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishIng(boolean z) {
            ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.publish_ing);
            if (!z) {
            }
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishSuccess() {
            ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.publish_success);
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishFail(Context context, PublishInfo publishInfo) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(publishInfo, "publishInfo");
            ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_publish_fail);
        }
    };

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
        return R.layout.activity_papa_video_list;
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerAddTagError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
    }

    public static final /* synthetic */ NewPaPaVideoPalyPresenter access$getMPresenter$p(NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        return (NewPaPaVideoPalyPresenter) newPaPaVideoPlayFragment.getMPresenter();
    }

    public final ArrayList<PostList> get_UploadData() {
        return this.uploadData;
    }

    public final int get_PageNo() {
        return getPageNo();
    }

    public final int get_CurrentPostion() {
        return this.curPosition;
    }

    public final int get_StartPosition() {
        return this.startPosition;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        List<PostList> data;
        PostList postList;
        List<PostList> data2;
        PostList postList2;
        Bundle extras;
        Bundle extras2;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 4) {
                if (i != 1212) {
                    return;
                }
                videoPayInfo();
                return;
            }
            ArrayList<Tag> parcelableArrayList = (intent == null || (extras2 = intent.getExtras()) == null) ? null : extras2.getParcelableArrayList("intent_list");
            Integer valueOf = (intent == null || (extras = intent.getExtras()) == null) ? null : Integer.valueOf(extras.getInt("intent_position"));
            if (!(parcelableArrayList instanceof ArrayList)) {
                return;
            }
            if (valueOf != null) {
                Tag compareAdd = compareAdd(parcelableArrayList, valueOf.intValue());
                if (!(compareAdd instanceof Tag)) {
                    return;
                }
                if (compareAdd.getTagId() > 0) {
                    NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter();
                    if (newPaPaVideoPalyPresenter == null) {
                        return;
                    }
                    int tagId = compareAdd.getTagId();
                    PapaVideoListAdapter adapter = getAdapter();
                    newPaPaVideoPalyPresenter.addTagToPost(tagId, "", (adapter == null || (data2 = adapter.getData()) == null || (postList2 = data2.get(valueOf.intValue())) == null) ? 0 : postList2.getId(), compareAdd, valueOf.intValue());
                    return;
                }
                NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter2 = (NewPaPaVideoPalyPresenter) getMPresenter();
                if (newPaPaVideoPalyPresenter2 == null) {
                    return;
                }
                String tagName = compareAdd.getTagName();
                Intrinsics.checkExpressionValueIsNotNull(tagName, "compareAdd.tagName");
                PapaVideoListAdapter adapter2 = getAdapter();
                newPaPaVideoPalyPresenter2.addTagToPost(0, tagName, (adapter2 == null || (data = adapter2.getData()) == null || (postList = data.get(valueOf.intValue())) == null) ? 0 : postList.getId(), compareAdd, valueOf.intValue());
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }

    private final Tag compareAdd(ArrayList<Tag> arrayList, int i) {
        List<PostList> data;
        PostList postList;
        PapaVideoListAdapter adapter = getAdapter();
        ArrayList<Tag> tagList = (adapter == null || (data = adapter.getData()) == null || (postList = data.get(i)) == null) ? null : postList.getTagList();
        if (tagList instanceof ArrayList) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<Tag> it2 = arrayList.iterator();
            while (true) {
                boolean z = false;
                if (!it2.hasNext()) {
                    break;
                }
                Tag it3 = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                if (it3.getTagId() != -10) {
                    Iterator<Tag> it4 = tagList.iterator();
                    while (it4.hasNext()) {
                        Tag it1 = it4.next();
                        int tagId = it3.getTagId();
                        Intrinsics.checkExpressionValueIsNotNull(it1, "it1");
                        if (tagId == it1.getTagId() && Intrinsics.areEqual(it3.getTagName(), it1.getTagName())) {
                            z = true;
                        }
                    }
                    if (!z) {
                        arrayList2.add(it3);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                return (Tag) arrayList2.get(0);
            }
        }
        return null;
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerAddTagSuccess(Tag data, int i) {
        List<PostList> data2;
        PostList postList;
        ArrayList<Tag> tagList;
        List<PostList> data3;
        PostList postList2;
        Intrinsics.checkParameterIsNotNull(data, "data");
        PapaVideoListAdapter adapter = getAdapter();
        ArrayList<Tag> tagList2 = (adapter == null || (data3 = adapter.getData()) == null || (postList2 = data3.get(i)) == null) ? null : postList2.getTagList();
        if (tagList2 != null && tagList2.size() == 6) {
            Tag tag = tagList2.get(0);
            Intrinsics.checkExpressionValueIsNotNull(tag, "tagList[0]");
            if (tag.getTagId() == -10) {
                tagList2.remove(0);
            }
        }
        PapaVideoListAdapter adapter2 = getAdapter();
        if (adapter2 != null && (data2 = adapter2.getData()) != null && (postList = data2.get(i)) != null && (tagList = postList.getTagList()) != null) {
            tagList.add(data);
        }
        PapaVideoListAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.notifyItemChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        if (TextUtils.isEmpty(this.businessType)) {
            NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter();
            if (newPaPaVideoPalyPresenter == null) {
                return;
            }
            newPaPaVideoPalyPresenter.requestPostList(getPageNo(), getPageSize());
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("postType", 4);
        String str = this.businessType;
        if (Intrinsics.areEqual(str, PaPaContants.getBUSINESS_PAPA_MINE())) {
            linkedHashMap.put("personMemberId", Integer.valueOf(this.personMemberId));
            NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter2 = (NewPaPaVideoPalyPresenter) getMPresenter();
            if (newPaPaVideoPalyPresenter2 == null) {
                return;
            }
            newPaPaVideoPalyPresenter2.requestPersonPost(linkedHashMap);
        } else if (!Intrinsics.areEqual(str, PaPaContants.getBUSINESS_PAPA_SEARCH())) {
        } else {
            linkedHashMap.put("key", this.searchKey);
            NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter3 = (NewPaPaVideoPalyPresenter) getMPresenter();
            if (newPaPaVideoPalyPresenter3 == null) {
                return;
            }
            newPaPaVideoPalyPresenter3.requestSerachPost(linkedHashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public PapaVideoListAdapter mo6434createAdapter() {
        return new PapaVideoListAdapter(getContext(), getRecyclerView(), this.papaVideoController, this);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewPaPaVideoPalyPresenter mo6441createPresenter() {
        return new NewPaPaVideoPalyPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Intent intent;
        FragmentActivity activity = getActivity();
        PostList postList = null;
        Bundle extras = (activity == null || (intent = activity.getIntent()) == null) ? null : intent.getExtras();
        if (extras != null) {
            this.postListArrayList = extras.getParcelableArrayList("postList");
            this.businessType = extras.getString("business");
            this.searchKey = extras.getString("search_key");
            this.personMemberId = extras.getInt("person_member_id");
            setPageNo(extras.getInt("page_no", 1));
            this.startPosition = extras.getInt("start_position", 0);
            extras.getBoolean("isLoadEnable");
        }
        PapaPublishUtil.getInstance().setContext(getActivity(), this.papaListener);
        PapaVideoListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setNewData(this.postListArrayList);
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.scrollToPosition(this.curPosition);
        }
        PapaVideoListAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            postList = adapter2.getItem(this.curPosition);
        }
        this.postList = postList;
        LogUtil.m3785e("yan", "开始播放视频url-----------");
        startPlayVideo(500);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        initPlayer();
        super.initView();
        this.adUtil = new AdUtil(getActivity());
        this.listPages = DBUtil.getAdPage(C2516Ad.TYPE_PAPA);
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        this.insertLoopNum = systemParam.getPaPaFrequencyOnList();
        if (TextUtils.isEmpty(this.businessType)) {
            ((SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh)).setColorSchemeResources(R.color.colorAccent, R.color.color_FF2877);
            ((SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh)).setProgressViewOffset(false, 50, 150);
        } else {
            ArrayList<PostList> arrayList = this.postListArrayList;
            this.postList = arrayList != null ? arrayList.get(0) : null;
        }
        SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
        Intrinsics.checkExpressionValueIsNotNull(swipeRefresh, "swipeRefresh");
        swipeRefresh.setRefreshing(false);
        SwipeRefreshLayout swipeRefresh2 = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
        Intrinsics.checkExpressionValueIsNotNull(swipeRefresh2, "swipeRefresh");
        swipeRefresh2.setEnabled(false);
        this.viewPagerLayoutManager = new ViewPagerLayoutManager(getContext(), 1);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setLayoutManager(this.viewPagerLayoutManager);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.setHasFixedSize(true);
        }
        ViewPagerLayoutManager viewPagerLayoutManager = this.viewPagerLayoutManager;
        if (viewPagerLayoutManager != null) {
            viewPagerLayoutManager.setOnViewPagerListener(new ViewPagerLayouListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$1
                @Override // com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener
                public void onInitComplete() {
                }

                @Override // com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener
                public void onPageRelease(boolean z, int i) {
                    LogUtil.m3785e("ViewPagerLayouListener", "滑动释放：isNext = " + z + ", position = " + i);
                }

                @Override // com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener
                public void onPageSelected(int i, boolean z) {
                    int i2;
                    PapaVideoListAdapter adapter;
                    PostList postList;
                    PostList postList2;
                    String str;
                    PostList postList3;
                    int i3;
                    LogUtil.m3785e("ViewPagerLayouListener", "滑动静止：isBottom = " + z + ", position = " + i);
                    i2 = NewPaPaVideoPlayFragment.this.curPosition;
                    if (i2 == i) {
                        return;
                    }
                    NewPaPaVideoPlayFragment.this.curPosition = i;
                    IpfsUtil.syncStopAll();
                    NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment.this;
                    adapter = newPaPaVideoPlayFragment.getAdapter();
                    if (adapter != null) {
                        i3 = NewPaPaVideoPlayFragment.this.curPosition;
                        postList = adapter.getItem(i3);
                    } else {
                        postList = null;
                    }
                    newPaPaVideoPlayFragment.postList = postList;
                    NewPaPaVideoPlayFragment.this.startPlayVideo(100);
                    LogUtil.m3785e("yan", "onPageSelected -----");
                    postList2 = NewPaPaVideoPlayFragment.this.postList;
                    if (postList2 == null || !postList2.isAd()) {
                        str = NewPaPaVideoPlayFragment.this.businessType;
                        if (TextUtils.isEmpty(str)) {
                            ((ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish)).setVisibility(0);
                            return;
                        } else {
                            ((ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish)).setVisibility(8);
                            return;
                        }
                    }
                    postList3 = NewPaPaVideoPlayFragment.this.postList;
                    if (postList3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    DataUploadUtil.uploadAD(postList3.getId(), 1);
                    ImageView iv_publish = (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish);
                    Intrinsics.checkExpressionValueIsNotNull(iv_publish, "iv_publish");
                    iv_publish.setVisibility(8);
                }

                @Override // com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener
                public void onPageSettling(int i) {
                    int i2;
                    PapaVideoView papaVideoView;
                    PapaVideoView papaVideoView2;
                    LogUtil.m3785e("ViewPagerLayouListener", "滑动中：position = " + i);
                    i2 = NewPaPaVideoPlayFragment.this.curPosition;
                    if (i2 == i) {
                        return;
                    }
                    papaVideoView = NewPaPaVideoPlayFragment.this.papaVideoView;
                    if (papaVideoView == null) {
                        return;
                    }
                    papaVideoView2 = NewPaPaVideoPlayFragment.this.papaVideoView;
                    if (papaVideoView2 != null) {
                        papaVideoView2.release();
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }

                @Override // com.one.tomato.thirdpart.recyclerview.ViewPagerLayouListener
                public void onPageDragging(int i) {
                    LogUtil.m3785e("ViewPagerLayouListener", "Dragging：position = " + i);
                }
            });
        }
        ((ImageView) _$_findCachedViewById(R$id.iv_publish)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewPaPaVideoPlayFragment.this.startPublishActivity();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_wifi_tip_play)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewPaPaVideoPlayFragment.this.onVideoResume();
                BaseApplication application = BaseApplication.getApplication();
                Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
                application.setVideoPlayWifiTip(false);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_virtual_play)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = NewPaPaVideoPlayFragment.this.getMContext();
                if (mContext != null) {
                    MyShareActivity.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_currency_play)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                VipActivity.Companion companion = VipActivity.Companion;
                mContext = NewPaPaVideoPlayFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_post_currency_subscribe);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    PostList postList;
                    UpSubscribeActivity.Companion companion = UpSubscribeActivity.Companion;
                    mContext = NewPaPaVideoPlayFragment.this.getMContext();
                    postList = NewPaPaVideoPlayFragment.this.postList;
                    companion.startAct(mContext, postList != null ? postList.getMemberId() : 0);
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_play);
        if (textView2 != null) {
            textView2.setOnClickListener(new NewPaPaVideoPlayFragment$initView$7(this));
        }
        ((RelativeLayout) _$_findCachedViewById(R$id.relative_commpent)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PapaVideoListAdapter adapter;
                Integer num;
                ViewPagerLayoutManager viewPagerLayoutManager2;
                PostList postList;
                List<PostList> data;
                PostList postList2;
                try {
                    adapter = NewPaPaVideoPlayFragment.this.getAdapter();
                    View view2 = null;
                    if (adapter == null || (data = adapter.getData()) == null) {
                        num = null;
                    } else {
                        postList2 = NewPaPaVideoPlayFragment.this.postList;
                        num = Integer.valueOf(data.indexOf(postList2));
                    }
                    if (num == null) {
                        return;
                    }
                    num.intValue();
                    viewPagerLayoutManager2 = NewPaPaVideoPlayFragment.this.viewPagerLayoutManager;
                    if (viewPagerLayoutManager2 != null) {
                        view2 = viewPagerLayoutManager2.findViewByPosition(num.intValue());
                    }
                    if (view2 == null) {
                        return;
                    }
                    NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment.this;
                    postList = NewPaPaVideoPlayFragment.this.postList;
                    View findViewById = view2.findViewById(R.id.tv_comment_num);
                    if (findViewById == null) {
                        throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
                    }
                    newPaPaVideoPlayFragment.onCommentClick(postList, (TextView) findViewById);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FragmentActivity activity = NewPaPaVideoPlayFragment.this.getActivity();
                if (activity != null) {
                    activity.onBackPressed();
                }
            }
        });
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.virtual_back);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.virtual_back);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FragmentActivity activity = NewPaPaVideoPlayFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            });
        }
    }

    private final void initPlayer() {
        Context context = getContext();
        if (context != null) {
            this.papaVideoView = new PapaVideoView(context);
            this.papaVideoController = new PapaVideoController(this);
            PapaVideoView papaVideoView = this.papaVideoView;
            if (papaVideoView != null) {
                papaVideoView.setVideoController(this.papaVideoController);
            }
            PapaVideoController papaVideoController = this.papaVideoController;
            if (papaVideoController == null) {
                return;
            }
            papaVideoController.setOnProgressListener(new PapaVideoController.OnProgressListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initPlayer$1
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
                    r0 = r11.this$0.postList;
                 */
                @Override // com.one.tomato.thirdpart.video.controller.PapaVideoController.OnProgressListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onProgress(long j, long j2) {
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    boolean z;
                    boolean z2;
                    PostList postList4;
                    PostList postList5;
                    PostList postList6;
                    PostList postList7;
                    PostList postList8;
                    boolean z3;
                    boolean z4;
                    PostList postList9;
                    PostList postList10;
                    boolean z5;
                    boolean z6;
                    PostList postList11;
                    PostList postList12;
                    boolean z7;
                    PostList postList13;
                    boolean z8;
                    PostList postList14;
                    postList = NewPaPaVideoPlayFragment.this.postList;
                    if (postList != null) {
                        postList2 = NewPaPaVideoPlayFragment.this.postList;
                        if (postList2 != null && postList2.isAd()) {
                            return;
                        }
                        long j3 = 1000;
                        long j4 = j2 / j3;
                        long j5 = j / j3;
                        PostUtils postUtils = PostUtils.INSTANCE;
                        postList3 = NewPaPaVideoPlayFragment.this.postList;
                        if (!postUtils.isLongPaPaVideo(postList3 != null ? postList3.getVideoTime() : null) || postList12 == null || postList12.isAd()) {
                            z = NewPaPaVideoPlayFragment.this.isAlreadyUpVideoHalf;
                            if (z || j5 <= 0 || j5 <= j4 / 2) {
                                z2 = NewPaPaVideoPlayFragment.this.isAlreadyUpVideoCom;
                                if (!z2 && j5 > 0 && j5 > j4 - 2) {
                                    postList4 = NewPaPaVideoPlayFragment.this.postList;
                                    if (postList4 == null) {
                                        Intrinsics.throwNpe();
                                        throw null;
                                    } else if (!postList4.isAd()) {
                                        NewPaPaVideoPlayFragment.this.isAlreadyUpVideoCom = true;
                                        postList5 = NewPaPaVideoPlayFragment.this.postList;
                                        if (postList5 == null) {
                                            Intrinsics.throwNpe();
                                            throw null;
                                        }
                                        DataUploadUtil.uploadVideoPlayWhole(postList5.getId());
                                    }
                                }
                            } else {
                                NewPaPaVideoPlayFragment.this.isAlreadyUpVideoHalf = true;
                                postList6 = NewPaPaVideoPlayFragment.this.postList;
                                DataUploadUtil.uploadVideoPlayHalf(postList6 != null ? postList6.getId() : 0);
                            }
                        } else {
                            if (j5 > 15) {
                                z8 = NewPaPaVideoPlayFragment.this.isAlreadyUpVideoHalf;
                                if (!z8) {
                                    NewPaPaVideoPlayFragment.this.isAlreadyUpVideoHalf = true;
                                    postList14 = NewPaPaVideoPlayFragment.this.postList;
                                    DataUploadUtil.uploadVideoPlayHalf(postList14 != null ? postList14.getId() : 0);
                                }
                            }
                            z7 = NewPaPaVideoPlayFragment.this.isAlreadyUpVideoCom;
                            if (!z7 && j5 >= 30) {
                                NewPaPaVideoPlayFragment.this.isAlreadyUpVideoCom = true;
                                postList13 = NewPaPaVideoPlayFragment.this.postList;
                                DataUploadUtil.uploadVideoPlayWhole(postList13 != null ? postList13.getId() : 0);
                            }
                        }
                        if (j <= 3) {
                            return;
                        }
                        postList7 = NewPaPaVideoPlayFragment.this.postList;
                        if (postList7 != null && !postList7.isAlreadyPaid()) {
                            postList10 = NewPaPaVideoPlayFragment.this.postList;
                            if ((postList10 != null ? postList10.getPrice() : 0) > 0) {
                                z5 = NewPaPaVideoPlayFragment.this.isPostPay;
                                if (z5) {
                                    NewPaPaVideoPlayFragment.this.isPostPay = false;
                                    NewPaPaVideoPlayFragment.this.showRewardView();
                                }
                                z6 = NewPaPaVideoPlayFragment.this.isAlreadyUpData;
                                if (z6) {
                                    return;
                                }
                                PostUtils postUtils2 = PostUtils.INSTANCE;
                                postList11 = NewPaPaVideoPlayFragment.this.postList;
                                postUtils2.updatePostBrowse(postList11);
                                NewPaPaVideoPlayFragment.this.isAlreadyUpData = true;
                                return;
                            }
                        }
                        postList8 = NewPaPaVideoPlayFragment.this.postList;
                        if (postList8 == null || !postList8.isAlreadyPaid()) {
                            z3 = NewPaPaVideoPlayFragment.this.subLookTime;
                            if (!z3) {
                                return;
                            }
                            NewPaPaVideoPlayFragment.this.subLookTime = false;
                            NewPaPaVideoPlayFragment.this.videoPayInfo();
                            return;
                        }
                        z4 = NewPaPaVideoPlayFragment.this.isAlreadyUpData;
                        if (z4) {
                            return;
                        }
                        PostUtils postUtils3 = PostUtils.INSTANCE;
                        postList9 = NewPaPaVideoPlayFragment.this.postList;
                        postUtils3.updatePostBrowse(postList9);
                        NewPaPaVideoPlayFragment.this.isAlreadyUpData = true;
                    }
                }
            });
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void showRewardView() {
        PostList postList;
        if (getContext() == null || (postList = this.postList) == null) {
            return;
        }
        if (postList != null && postList.isAd()) {
            return;
        }
        PostList postList2 = this.postList;
        if (postList2 != null && postList2.getMemberId() == DBUtil.getMemberId()) {
            return;
        }
        PostList postList3 = this.postList;
        if (postList3 != null && postList3.isAlreadyPaid()) {
            return;
        }
        PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
        PostList postList4 = this.postList;
        if (postRewardPayUtils.isAreadlyPay(postList4 != null ? postList4.getId() : 0)) {
            return;
        }
        onVideoPause();
        setPostNeedPay();
    }

    public final void cancelAnimation(LottieAnimationView iv_loading) {
        Intrinsics.checkParameterIsNotNull(iv_loading, "iv_loading");
        if (iv_loading.getAnimation() == null || !iv_loading.isAnimating()) {
            return;
        }
        iv_loading.cancelAnimation();
    }

    public final void showAnimation(LottieAnimationView iv_loading) {
        Intrinsics.checkParameterIsNotNull(iv_loading, "iv_loading");
        if (iv_loading.getAnimation() != null && iv_loading.isAnimating()) {
            iv_loading.cancelAnimation();
        }
        iv_loading.setAnimation("loading_more.json");
    }

    public final void videoPayInfo() {
        PostList postList;
        if (getContext() != null && (postList = this.postList) != null && (postList == null || !postList.isAd())) {
            VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils, "VideoPlayCountUtils.getInstance()");
            if (!videoPlayCountUtils.isFreePlay() && !VideoPlayCountUtils.getInstance().isVideoPlay(this.postList)) {
                VideoPlayCountUtils videoPlayCountUtils2 = VideoPlayCountUtils.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils2, "VideoPlayCountUtils.getInstance()");
                if (videoPlayCountUtils2.getRemainTimes() == 0) {
                    onVideoRelease();
                    this.needVirtualConsume = true;
                    setVirtualConsume(false);
                    return;
                }
                if (!this.isAlreadyUpData) {
                    PostUtils.INSTANCE.updatePostBrowse(this.postList);
                    this.isAlreadyUpData = true;
                }
                VideoPlayCountUtils.getInstance().addVideoPlayList(this.postList);
                return;
            }
        }
        if (!this.isAlreadyUpData) {
            PostUtils.INSTANCE.updatePostBrowse(this.postList);
            this.isAlreadyUpData = true;
        }
    }

    public final void onVideoRelease() {
        PapaVideoView papaVideoView = this.papaVideoView;
        if (papaVideoView != null && papaVideoView != null) {
            papaVideoView.release();
        }
        IpfsUtil.syncStopAll();
    }

    public final void onVideoResume() {
        List<PostList> data;
        PapaVideoView papaVideoView;
        PapaVideoView papaVideoView2 = this.papaVideoView;
        if (papaVideoView2 != null && papaVideoView2 != null && papaVideoView2.isInPlaybackState()) {
            if (this.needVirtualConsume || (papaVideoView = this.papaVideoView) == null) {
                return;
            }
            papaVideoView.resume();
        } else if (getAdapter() == null) {
        } else {
            PapaVideoListAdapter adapter = getAdapter();
            if ((adapter != null && (data = adapter.getData()) != null && data.isEmpty()) || this.curPosition == -1 || this.needVirtualConsume) {
                return;
            }
            LogUtil.m3785e("yan", "onVideoResume");
            startPlayVideo(100);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void payCompelete() {
        List<PostList> data;
        this.isPostPay = true;
        View rl_wifi_and_virtual = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_and_virtual, "rl_wifi_and_virtual");
        rl_wifi_and_virtual.setVisibility(8);
        if (getAdapter() != null) {
            PapaVideoListAdapter adapter = getAdapter();
            if ((adapter != null && (data = adapter.getData()) != null && data.isEmpty()) || this.curPosition == -1) {
                return;
            }
            LogUtil.m3785e("yan", "onVideoResume");
            startPlayVideo(100);
        }
    }

    public final void onVideoPause() {
        PapaVideoView papaVideoView = this.papaVideoView;
        if (papaVideoView == null || papaVideoView == null) {
            return;
        }
        papaVideoView.pause();
    }

    public final void onVideoStop() {
        PapaVideoView papaVideoView = this.papaVideoView;
        if (papaVideoView == null || papaVideoView == null) {
            return;
        }
        papaVideoView.stopPlayback();
    }

    public final boolean getIsResume() {
        return this.isResume;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.isResume = false;
        onVideoPause();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.isResume = true;
        isShowPayView();
    }

    public final void callVideoResume() {
        onVideoResume();
    }

    private final void isShowPayView() {
        View _$_findCachedViewById = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
        if (_$_findCachedViewById == null || _$_findCachedViewById.getVisibility() != 0) {
            return;
        }
        View _$_findCachedViewById2 = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
        if (_$_findCachedViewById2 != null) {
            _$_findCachedViewById2.setVisibility(8);
        }
        startPlayVideo(100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startPlayVideo(int i) {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$startPlayVideo$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewPaPaVideoPlayFragment.this.startPlay();
                    LogUtil.m3785e("yan", "startPlay -----");
                }
            }, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startPlay() {
        PostList postList;
        View findViewByPosition;
        PapaVideoController papaVideoController;
        ImageView thumb;
        LogUtil.m3785e("yan", "开始播放视频url");
        if (this.papaVideoView == null || (postList = this.postList) == null) {
            return;
        }
        if (postList != null && postList.isAd()) {
            Function1<? super Boolean, Unit> function1 = this.observerPostIsAd;
            if (function1 != null) {
                function1.mo6794invoke(true);
            }
        } else {
            Function1<? super Boolean, Unit> function12 = this.observerPostIsAd;
            if (function12 != null) {
                function12.mo6794invoke(false);
            }
        }
        PapaVideoView papaVideoView = this.papaVideoView;
        if (papaVideoView != null) {
            papaVideoView.release();
        }
        PapaVideoView papaVideoView2 = this.papaVideoView;
        String str = null;
        ViewParent parent = papaVideoView2 != null ? papaVideoView2.getParent() : null;
        if (parent != null && (parent instanceof FrameLayout)) {
            ((FrameLayout) parent).removeView(this.papaVideoView);
        }
        ViewPagerLayoutManager viewPagerLayoutManager = this.viewPagerLayoutManager;
        if (viewPagerLayoutManager == null || (findViewByPosition = viewPagerLayoutManager.findViewByPosition(this.curPosition)) == null) {
            return;
        }
        Intrinsics.checkExpressionValueIsNotNull(findViewByPosition, "viewPagerLayoutManager?.…on(curPosition) ?: return");
        FrameLayout frameLayout = (FrameLayout) findViewByPosition.findViewById(R.id.container);
        ImageView imageView = (ImageView) findViewByPosition.findViewById(R.id.thumbImageView);
        ImageView imageView2 = (ImageView) findViewByPosition.findViewById(R.id.iv_favor);
        TextView textView = (TextView) findViewByPosition.findViewById(R.id.tv_favor_num);
        if (imageView != null && imageView.getDrawable() != null && (papaVideoController = this.papaVideoController) != null && (thumb = papaVideoController.getThumb()) != null) {
            thumb.setImageDrawable(imageView.getDrawable());
        }
        PapaVideoController papaVideoController2 = this.papaVideoController;
        if (papaVideoController2 != null) {
            papaVideoController2.setOnPlayViewClickListener(new PapaVideoController.OnFavorDoubleClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$startPlay$1
                @Override // com.one.tomato.thirdpart.video.controller.PapaVideoController.OnFavorDoubleClickListener
                public final boolean onFavourDoubleClick(PostList postList2) {
                    NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(postList2, "postList");
                    return newPaPaVideoPlayFragment.onFavourClick(postList2);
                }
            }, imageView2, textView);
        }
        PapaVideoController papaVideoController3 = this.papaVideoController;
        if (papaVideoController3 != null) {
            papaVideoController3.setPostList(this.postList);
        }
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        String ttViewVideoView2 = domainServer.getTtViewVideoView2();
        StringBuilder sb = new StringBuilder();
        sb.append(ttViewVideoView2);
        PostList postList2 = this.postList;
        sb.append(postList2 != null ? postList2.getSecVideoUrl() : null);
        String sb2 = sb.toString();
        PostList postList3 = this.postList;
        String syncProxyUrl = IpfsUtil.getSyncProxyUrl(String.valueOf(postList3 != null ? Integer.valueOf(postList3.getId()) : null), sb2);
        LogUtil.m3786e("播放的视频Url：" + sb2);
        frameLayout.addView(this.papaVideoView);
        PapaVideoView papaVideoView3 = this.papaVideoView;
        if (papaVideoView3 != null) {
            papaVideoView3.setUrl(sb2);
        }
        PapaVideoView papaVideoView4 = this.papaVideoView;
        if (papaVideoView4 != null) {
            papaVideoView4.setIpfsUrl(syncProxyUrl);
        }
        PapaVideoView papaVideoView5 = this.papaVideoView;
        if (papaVideoView5 != null) {
            papaVideoView5.setScreenScale(0);
        }
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (!application.isVideoPlayWifiTip() || NetWorkUtil.isWiFi()) {
            View rl_wifi_and_virtual = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
            Intrinsics.checkExpressionValueIsNotNull(rl_wifi_and_virtual, "rl_wifi_and_virtual");
            rl_wifi_and_virtual.setVisibility(8);
            PapaVideoView papaVideoView6 = this.papaVideoView;
            if (papaVideoView6 != null) {
                papaVideoView6.start();
            }
        } else {
            View rl_wifi_and_virtual2 = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
            Intrinsics.checkExpressionValueIsNotNull(rl_wifi_and_virtual2, "rl_wifi_and_virtual");
            rl_wifi_and_virtual2.setVisibility(0);
            RelativeLayout rl_wifi_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_wifi_tip);
            Intrinsics.checkExpressionValueIsNotNull(rl_wifi_tip, "rl_wifi_tip");
            rl_wifi_tip.setVisibility(0);
            RelativeLayout rl_virtual_currency_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_virtual_currency_tip);
            Intrinsics.checkExpressionValueIsNotNull(rl_virtual_currency_tip, "rl_virtual_currency_tip");
            rl_virtual_currency_tip.setVisibility(8);
            TextView tv_duration = (TextView) _$_findCachedViewById(R$id.tv_duration);
            Intrinsics.checkExpressionValueIsNotNull(tv_duration, "tv_duration");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(AppUtil.getString(R.string.post_video_duration));
            sb3.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            PostList postList4 = this.postList;
            if (postList4 != null) {
                str = postList4.getVideoTime();
            }
            sb3.append(str);
            tv_duration.setText(sb3.toString());
        }
        this.subLookTime = true;
        this.isPostPay = true;
        this.isAlreadyUpData = false;
        this.isAlreadyUpVideoHalf = false;
        this.isAlreadyUpVideoCom = false;
    }

    private final void setVirtualConsume(boolean z) {
        if (z) {
            onVideoResume();
            return;
        }
        View rl_wifi_and_virtual = _$_findCachedViewById(R$id.rl_wifi_and_virtual);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_and_virtual, "rl_wifi_and_virtual");
        rl_wifi_and_virtual.setVisibility(0);
        RelativeLayout rl_wifi_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_wifi_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_tip, "rl_wifi_tip");
        rl_wifi_tip.setVisibility(8);
        RelativeLayout rl_virtual_currency_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_virtual_currency_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_virtual_currency_tip, "rl_virtual_currency_tip");
        rl_virtual_currency_tip.setVisibility(0);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_post_need_pay);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_virtual_currency);
        if (textView != null) {
            textView.setText(AppUtil.getString(R.string.post_open_vip_tip));
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_virtual_play);
        if (textView2 != null) {
            textView2.setText(AppUtil.getString(R.string.post_goto_promote));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_currency_play);
        if (textView3 == null) {
            return;
        }
        textView3.setText(AppUtil.getString(R.string.post_goto_open_vip));
    }

    public final void setPostNeedPay() {
        PostUtils.INSTANCE.requestBalance(getMContext(), new NewPaPaVideoPlayFragment$setPostNeedPay$1(this), new NewPaPaVideoPlayFragment$setPostNeedPay$2(this));
    }

    public final void onCommentClick(final PostList postList, final TextView textView) {
        Intrinsics.checkParameterIsNotNull(textView, "textView");
        if (postList == null) {
            return;
        }
        if (postList.isAd()) {
            ToastUtil.showCenterToast((int) R.string.video_comment_no_support);
            return;
        }
        this.papaCommentDialog = new PapaCommentDialog(getMContext());
        PapaCommentDialog papaCommentDialog = this.papaCommentDialog;
        if (papaCommentDialog != null) {
            papaCommentDialog.setVideoCommentSuccessListener(new PapaCommentDialog.VideoCommentSuccessListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$onCommentClick$1
                @Override // com.one.tomato.dialog.PapaCommentDialog.VideoCommentSuccessListener
                public final void commentSuccess(int i) {
                    PostList.this.setVideoCommentTimes(i);
                    textView.setText(FormatUtil.formatNumOverTenThousand(String.valueOf(PostList.this.getVideoCommentTimes()) + ""));
                }
            });
        }
        PapaCommentDialog papaCommentDialog2 = this.papaCommentDialog;
        if (papaCommentDialog2 != null) {
            papaCommentDialog2.setData(postList.getId(), postList.getVideoCommentTimes(), postList);
        }
        PapaCommentDialog papaCommentDialog3 = this.papaCommentDialog;
        if (papaCommentDialog3 != null) {
            papaCommentDialog3.show();
        }
        PapaCommentDialog papaCommentDialog4 = this.papaCommentDialog;
        if (papaCommentDialog4 == null) {
            return;
        }
        papaCommentDialog4.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$onCommentClick$2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ((ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish)).requestFocus();
                ((ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$onCommentClick$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FragmentActivity it1 = NewPaPaVideoPlayFragment.this.getActivity();
                        if (it1 != null) {
                            NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment.this;
                            Intrinsics.checkExpressionValueIsNotNull(it1, "it1");
                            ImageView iv_publish = (ImageView) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.iv_publish);
                            Intrinsics.checkExpressionValueIsNotNull(iv_publish, "iv_publish");
                            newPaPaVideoPlayFragment.closeKeyBoard(it1, iv_publish);
                        }
                    }
                }, 300L);
            }
        });
    }

    public final void videoDown() {
        downloadOrSaveDCIM(true);
    }

    public final void saveDCIM() {
        downloadOrSaveDCIM(false);
    }

    private final void downloadOrSaveDCIM(boolean z) {
        NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter;
        VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
        PostList postList = this.postList;
        boolean hasDownloadRecord = videoDownloadCountUtils.hasDownloadRecord(String.valueOf(postList != null ? Integer.valueOf(postList.getId()) : null));
        VideoDownloadCountUtils videoDownloadCountUtils2 = VideoDownloadCountUtils.getInstance();
        PostList postList2 = this.postList;
        boolean hasSaveLocalRecord = videoDownloadCountUtils2.hasSaveLocalRecord(String.valueOf(postList2 != null ? Integer.valueOf(postList2.getId()) : null));
        if (!z) {
            hasDownloadRecord = hasSaveLocalRecord;
        }
        PostList postList3 = this.postList;
        if ((postList3 != null && postList3.getMemberId() == DBUtil.getMemberId()) || hasDownloadRecord) {
            if (z) {
                download(false);
                return;
            } else {
                showSaveIngDialog(false);
                return;
            }
        }
        if (z) {
            PostList postList4 = this.postList;
            if (postList4 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (Intrinsics.compare(postList4.getPrice(), 0) == 1) {
                PostList postList5 = this.postList;
                if (postList5 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (postList5.isAlreadyPaid()) {
                    download(true);
                    return;
                } else {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_video_need_pay));
                    PostUtils postUtils = PostUtils.INSTANCE;
                    Context mContext = getMContext();
                    if (mContext == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    PostList postList6 = this.postList;
                    if (postList6 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    String valueOf = String.valueOf(postList6.getPrice());
                    PostList postList7 = this.postList;
                    if (postList7 != null) {
                        postUtils.showImageNeedPayDialog(mContext, valueOf, String.valueOf(postList7.getId()), 0, new NewPaPaVideoPlayFragment$downloadOrSaveDCIM$1(this), NewPaPaVideoPlayFragment$downloadOrSaveDCIM$2.INSTANCE);
                        return;
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        } else {
            PostList postList8 = this.postList;
            if (postList8 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (Intrinsics.compare(postList8.getDownPrice(), 0) == 1) {
                PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
                PostList postList9 = this.postList;
                if (postList9 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (postRewardPayUtils.isAreadlyDownPay(postList9.getId())) {
                    showSaveIngDialog(true);
                    return;
                } else {
                    UserInfo userInfo = DBUtil.getUserInfo();
                    Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                    if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
                        VideoDownloadCountUtils videoDownloadCountUtils3 = VideoDownloadCountUtils.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils3, "VideoDownloadCountUtils.getInstance()");
                        if (Intrinsics.compare(videoDownloadCountUtils3.getVipSaveCount(), 0) == 1) {
                            PostList postList10 = this.postList;
                            if (postList10 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            } else if (Intrinsics.compare(postList10.getPrice(), 0) == 0) {
                                showSaveIngDialog(true);
                                return;
                            }
                        }
                    }
                    PostList postList11 = this.postList;
                    if (postList11 != null) {
                        showNeedPayVideoDialog(postList11, z);
                        return;
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        }
        UserInfo userInfo2 = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
        if (Intrinsics.compare(userInfo2.getVipType(), 0) != 1) {
            if (!UserPermissionUtil.getInstance().isPermissionEnable(2) || (newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter()) == null) {
                return;
            }
            newPaPaVideoPalyPresenter.downLoadVideoCheck(z);
        } else if (z) {
            download(false);
        } else {
            VideoDownloadCountUtils videoDownloadCountUtils4 = VideoDownloadCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils4, "VideoDownloadCountUtils.getInstance()");
            if (Intrinsics.compare(videoDownloadCountUtils4.getVipSaveCount(), 0) == 1) {
                showSaveIngDialog(true);
                return;
            }
            NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter2 = (NewPaPaVideoPalyPresenter) getMPresenter();
            if (newPaPaVideoPalyPresenter2 == null) {
                return;
            }
            newPaPaVideoPalyPresenter2.downLoadVideoCheck(z);
        }
    }

    private final void showNeedPayVideoDialog(final PostList postList, boolean z) {
        VideoSaveTipDialog videoSaveTipDialog = new VideoSaveTipDialog(getContext());
        videoSaveTipDialog.setVideoSaveTipListener(new VideoSaveTipDialog.VideoSaveTipListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showNeedPayVideoDialog$1

            /* compiled from: NewPaPaVideoPlayFragment.kt */
            /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showNeedPayVideoDialog$1$1 */
            /* loaded from: classes3.dex */
            static final class C25841 extends Lambda implements Function1<String, Unit> {
                C25841() {
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
                    NewPaPaVideoPlayFragment.this.showSaveIngDialog(true);
                }
            }

            /* compiled from: NewPaPaVideoPlayFragment.kt */
            /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showNeedPayVideoDialog$1$2 */
            /* loaded from: classes3.dex */
            static final class C25852 extends Lambda implements Function1<ResponseThrowable, Unit> {
                public static final C25852 INSTANCE = new C25852();

                C25852() {
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
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                linkedHashMap.put("articleId", Integer.valueOf(postList.getId()));
                linkedHashMap.put("payType", 3);
                linkedHashMap.put("money", Integer.valueOf(postList.getDownPrice()));
                VideoPlayCountUtils.getInstance().postRewardPay(NewPaPaVideoPlayFragment.this.getContext(), linkedHashMap, new C25841(), C25852.INSTANCE);
            }
        });
        videoSaveTipDialog.setData(postList.getDownPrice());
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerDownCheck(VideoPay videoPay, boolean z) {
        if (z) {
            showDownloadTipDialog(videoPay);
            return;
        }
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
            showSaveTipDialog(videoPay);
            return;
        }
        VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils, "VideoDownloadCountUtils.getInstance()");
        if (videoDownloadCountUtils.isOpenVipTip()) {
            showSaveTipDialog(videoPay);
            return;
        }
        showOpenVipDialog(videoPay);
        VideoDownloadCountUtils videoDownloadCountUtils2 = VideoDownloadCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils2, "VideoDownloadCountUtils.getInstance()");
        videoDownloadCountUtils2.setOpenVipTip(true);
    }

    private final void showDownloadTipDialog(final VideoPay videoPay) {
        if (this.videoCacheTipDialog == null) {
            this.videoCacheTipDialog = new VideoCacheTipDialog(getContext());
            VideoCacheTipDialog videoCacheTipDialog = this.videoCacheTipDialog;
            if (videoCacheTipDialog != null) {
                videoCacheTipDialog.setVideoDownloadTipListener(new VideoCacheTipDialog.VideoDownloadTipListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showDownloadTipDialog$1
                    @Override // com.one.tomato.dialog.VideoCacheTipDialog.VideoDownloadTipListener
                    public void currencyDownload() {
                        PostList postList;
                        VideoCacheTipDialog videoCacheTipDialog2;
                        VideoCacheTipDialog videoCacheTipDialog3;
                        VideoPay videoPay2 = videoPay;
                        if (videoPay2 == null) {
                            return;
                        }
                        if (videoPay2.getTmtBalance() >= videoPay.getVideoPayTomatoAmount()) {
                            postList = NewPaPaVideoPlayFragment.this.postList;
                            if (postList == null) {
                                return;
                            }
                            NewPaPaVideoPlayFragment.this.downloadType = 1;
                            videoCacheTipDialog2 = NewPaPaVideoPlayFragment.this.videoCacheTipDialog;
                            if (videoCacheTipDialog2 != null) {
                                videoCacheTipDialog2.dismiss();
                            }
                            NewPaPaVideoPlayFragment.this.download(true);
                        } else if (NewPaPaVideoPlayFragment.this.getContext() == null) {
                        } else {
                            videoCacheTipDialog3 = NewPaPaVideoPlayFragment.this.videoCacheTipDialog;
                            if (videoCacheTipDialog3 != null) {
                                videoCacheTipDialog3.dismiss();
                            }
                            RechargeActivity.startActivity(NewPaPaVideoPlayFragment.this.getContext());
                        }
                    }
                });
            }
        }
        VideoCacheTipDialog videoCacheTipDialog2 = this.videoCacheTipDialog;
        if (videoCacheTipDialog2 != null) {
            videoCacheTipDialog2.setInfo(videoPay);
        }
    }

    private final void showOpenVipDialog(final VideoPay videoPay) {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
        customAlertDialog.setTitle(R.string.video_save_tip_title);
        customAlertDialog.setMessage(R.string.video_save_tip_message);
        customAlertDialog.setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner5_disable);
        customAlertDialog.setCancelButtonTextColor(R.color.white);
        customAlertDialog.setCancelButton(R.string.video_save_tip_cancel_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showOpenVipDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customAlertDialog.dismiss();
                NewPaPaVideoPlayFragment.this.showSaveTipDialog(videoPay);
            }
        });
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner5_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.video_save_tip_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showOpenVipDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customAlertDialog.dismiss();
                VipActivity.Companion companion = VipActivity.Companion;
                Context context = NewPaPaVideoPlayFragment.this.getContext();
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
    public final void showSaveTipDialog(VideoPay videoPay) {
        if (this.videoSaveTipDialog == null) {
            this.videoSaveTipDialog = new VideoSaveTipDialog(getContext());
            VideoSaveTipDialog videoSaveTipDialog = this.videoSaveTipDialog;
            if (videoSaveTipDialog != null) {
                videoSaveTipDialog.setVideoSaveTipListener(new VideoSaveTipDialog.VideoSaveTipListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showSaveTipDialog$1
                    @Override // com.one.tomato.dialog.VideoSaveTipDialog.VideoSaveTipListener
                    public void potatoDownload() {
                        NewPaPaVideoPlayFragment.this.showSaveIngDialog(true);
                    }
                });
            }
        }
        VideoSaveTipDialog videoSaveTipDialog2 = this.videoSaveTipDialog;
        if (videoSaveTipDialog2 != null) {
            videoSaveTipDialog2.setInfo(videoPay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSaveIngDialog(final boolean z) {
        Context context = getContext();
        PostList postList = this.postList;
        Integer num = null;
        String secVideoUrl = postList != null ? postList.getSecVideoUrl() : null;
        PostList postList2 = this.postList;
        if (postList2 != null) {
            num = Integer.valueOf(postList2.getId());
        }
        String valueOf = String.valueOf(num);
        PostList postList3 = this.postList;
        new VideoSaveIngDialog(context, secVideoUrl, valueOf, postList3 != null ? postList3.getVideoView() : 0).setVideoSaveLocalListener(new VideoSaveIngDialog.VideoSaveLocalListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$showSaveIngDialog$1
            @Override // com.one.tomato.dialog.VideoSaveIngDialog.VideoSaveLocalListener
            public void fail() {
            }

            @Override // com.one.tomato.dialog.VideoSaveIngDialog.VideoSaveLocalListener
            public void success() {
                PostList postList4;
                PostList postList5;
                NewPaPaVideoPalyPresenter access$getMPresenter$p;
                PostList postList6;
                Integer num2 = null;
                if (z && (access$getMPresenter$p = NewPaPaVideoPlayFragment.access$getMPresenter$p(NewPaPaVideoPlayFragment.this)) != null) {
                    postList6 = NewPaPaVideoPlayFragment.this.postList;
                    access$getMPresenter$p.downRecord(postList6 != null ? Integer.valueOf(postList6.getId()) : null, 5);
                }
                VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
                postList4 = NewPaPaVideoPlayFragment.this.postList;
                if (postList4 != null) {
                    num2 = Integer.valueOf(postList4.getId());
                }
                String valueOf2 = String.valueOf(num2);
                postList5 = NewPaPaVideoPlayFragment.this.postList;
                boolean z2 = false;
                if ((postList5 != null ? postList5.getPrice() : 0) == 0) {
                    z2 = true;
                }
                videoDownloadCountUtils.addSaveLocalRecord(valueOf2, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void download(final Boolean bool) {
        if (getContext() instanceof MvpBaseActivity) {
            Context context = getContext();
            if (context == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
            }
            new RxPermissions((MvpBaseActivity) context).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$download$1
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
                        NewPaPaVideoPlayFragment.this.startDownload(bool2.booleanValue());
                        return;
                    }
                    ToastUtil.showCenterToast((int) R.string.permission_storage);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startDownload(boolean z) {
        NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter;
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
        if (z && (newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter()) != null) {
            PostList postList8 = this.postList;
            newPaPaVideoPalyPresenter.downRecord(postList8 != null ? Integer.valueOf(postList8.getId()) : null, this.downloadType);
        }
        VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
        PostList postList9 = this.postList;
        if (postList9 != null) {
            num = Integer.valueOf(postList9.getId());
        }
        videoDownloadCountUtils.addDownloadRecord(String.valueOf(num));
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        updateData(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void updateData(ArrayList<PostList> arrayList) {
        PapaVideoListAdapter adapter;
        List<PostList> data;
        List<PostList> data2;
        if (arrayList == null || arrayList.isEmpty()) {
            if (getState() == getGET_LIST_REFRESH()) {
                SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
                Intrinsics.checkExpressionValueIsNotNull(swipeRefresh, "swipeRefresh");
                if (!swipeRefresh.isRefreshing()) {
                    return;
                }
                ((SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh)).post(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$updateData$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SwipeRefreshLayout swipeRefresh2 = (SwipeRefreshLayout) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.swipeRefresh);
                        Intrinsics.checkExpressionValueIsNotNull(swipeRefresh2, "swipeRefresh");
                        swipeRefresh2.setRefreshing(false);
                    }
                });
                return;
            }
            PapaVideoListAdapter adapter2 = getAdapter();
            if (adapter2 == null) {
                return;
            }
            adapter2.loadMoreEnd();
            return;
        }
        if (TextUtils.isEmpty(this.businessType)) {
            this.uploadData.addAll(arrayList);
        }
        setPageNo(getPageNo() + 1);
        Integer num = null;
        if (getState() == getGET_LIST_REFRESH()) {
            SwipeRefreshLayout swipeRefresh2 = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
            Intrinsics.checkExpressionValueIsNotNull(swipeRefresh2, "swipeRefresh");
            if (swipeRefresh2.isRefreshing()) {
                ((SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh)).post(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$updateData$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SwipeRefreshLayout swipeRefresh3 = (SwipeRefreshLayout) NewPaPaVideoPlayFragment.this._$_findCachedViewById(R$id.swipeRefresh);
                        Intrinsics.checkExpressionValueIsNotNull(swipeRefresh3, "swipeRefresh");
                        swipeRefresh3.setRefreshing(false);
                    }
                });
            }
            this.curPosition = 0;
            PapaVideoListAdapter adapter3 = getAdapter();
            if (adapter3 != null && (data2 = adapter3.getData()) != null) {
                data2.clear();
            }
            PapaVideoListAdapter adapter4 = getAdapter();
            if (adapter4 != null) {
                adapter4.notifyDataSetChanged();
            }
            PapaVideoListAdapter adapter5 = getAdapter();
            if (adapter5 != null) {
                adapter5.setNewData(arrayList);
            }
            AppUtil.recyclerViewScroll(getRecyclerView(), 0, 0, 100);
            if (this.isResume) {
                PapaVideoListAdapter adapter6 = getAdapter();
                this.postList = adapter6 != null ? adapter6.getItem(this.curPosition) : null;
                LogUtil.m3785e("yan", "updateData -----");
                startPlayVideo(1000);
            }
            this.insertPageIndex = 1;
        } else {
            PapaVideoListAdapter adapter7 = getAdapter();
            if (adapter7 != null) {
                adapter7.loadMoreComplete();
            }
            PapaVideoListAdapter adapter8 = getAdapter();
            if (adapter8 != null) {
                adapter8.addData((Collection) arrayList);
            }
        }
        if (!TextUtils.isEmpty(this.businessType)) {
            return;
        }
        PapaVideoListAdapter adapter9 = getAdapter();
        if (adapter9 != null && (data = adapter9.getData()) != null) {
            num = Integer.valueOf(data.size());
        }
        if (num == null) {
            return;
        }
        num.intValue();
        while (true) {
            int i = this.insertLoopNum;
            int i2 = this.insertPageIndex;
            int i3 = ((i * i2) + i2) - 1;
            if (i3 >= num.intValue() + 1) {
                return;
            }
            PostList postList = AdUtil.getPostList(getAdPage());
            if (postList != null && (adapter = getAdapter()) != null) {
                adapter.addData(i3, (int) postList);
            }
            this.insertPageIndex++;
        }
    }

    private final AdPage getAdPage() {
        ArrayList<AdPage> arrayList = this.listPages;
        AdPage adPage = null;
        if (arrayList == null || arrayList == null || arrayList.isEmpty()) {
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

    public final boolean onFavourClick(PostList postList) {
        NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter;
        Intrinsics.checkParameterIsNotNull(postList, "postList");
        if (postList.isAd() || (newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter()) == null) {
            return false;
        }
        newPaPaVideoPalyPresenter.thumbUp(postList);
        return false;
    }

    public final void onHeadClick(PostList postList) {
        Intrinsics.checkParameterIsNotNull(postList, "postList");
        Context mContext = getMContext();
        if (mContext != null) {
            NewMyHomePageActivity.Companion.startActivity(mContext, postList.getMemberId());
        }
    }

    public final void showRewardDialog() {
        PostRewardDialog.Companion.showDialog(this.postList).show(getChildFragmentManager(), "PostRewardDialog");
    }

    public final void focus(PostList memberList, int i) {
        Intrinsics.checkParameterIsNotNull(memberList, "memberList");
        NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter();
        if (newPaPaVideoPalyPresenter != null) {
            newPaPaVideoPalyPresenter.foucs(memberList, 1, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerFoucs(int i) {
        RecyclerView.LayoutManager layoutManager;
        PostList item;
        PapaVideoListAdapter adapter = getAdapter();
        setMemberFocusEvent(1, (adapter == null || (item = adapter.getItem(i)) == null) ? 0 : item.getMemberId());
        RecyclerView recyclerView = getRecyclerView();
        View findViewByPosition = (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null) ? null : layoutManager.findViewByPosition(i);
        if (findViewByPosition != null) {
            View findViewById = findViewByPosition.findViewById(R.id.iv_add_person);
            if (findViewById == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.ImageView");
            }
            ImageView imageView = (ImageView) findViewById;
            imageView.setImageResource(R.drawable.papa_focus_success);
            imageView.setVisibility(8);
        }
    }

    public final void setMemberFocusEvent(int i, int i2) {
        MemberFocusEvent memberFocusEvent = new MemberFocusEvent();
        memberFocusEvent.followFlag = i;
        memberFocusEvent.f1748id = i2;
        EventBus.getDefault().post(memberFocusEvent);
    }

    public final void startPostAd(PostList postList) {
        AdUtil adUtil;
        AdPage page = postList != null ? postList.getPage() : null;
        if (page == null || (adUtil = this.adUtil) == null) {
            return;
        }
        adUtil.clickAd(postList.getId(), page.getAdType(), page.getAdJumpModule(), page.getAdJumpDetail(), page.getOpenType(), page.getAdLink());
    }

    public final void collect(PostList postList) {
        Intrinsics.checkParameterIsNotNull(postList, "postList");
        NewPaPaVideoPalyPresenter newPaPaVideoPalyPresenter = (NewPaPaVideoPalyPresenter) getMPresenter();
        if (newPaPaVideoPalyPresenter != null) {
            newPaPaVideoPalyPresenter.collect(postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaPlayContact
    public void handlerCollect(int i) {
        if (i == 2) {
            PostList postList = this.postList;
            if (postList == null) {
                return;
            }
            if (postList != null) {
                postList.setIsFavor(0);
                sendCollectOrThumbEvent();
                UserInfoManager.setFavorCount(false);
                ToastUtil.showCenterToast((int) R.string.post_collect_cancel);
                PostList postList2 = this.postList;
                if (postList2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (postList2 != null) {
                    postList2.setFavorTimes(postList2.getFavorTimes() - 1);
                    return;
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
            Intrinsics.throwNpe();
            throw null;
        }
        PostList postList3 = this.postList;
        if (postList3 == null) {
            return;
        }
        if (postList3 != null) {
            postList3.setIsFavor(1);
            sendCollectOrThumbEvent();
            UserInfoManager.setFavorCount(true);
            ToastUtil.showCenterToast((int) R.string.post_collect_success);
            PostList postList4 = this.postList;
            if (postList4 == null) {
                return;
            }
            if (postList4 != null) {
                postList4.setFavorTimes(postList4.getFavorTimes() + 1);
                return;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        Intrinsics.throwNpe();
        throw null;
    }

    private final void sendCollectOrThumbEvent() {
        PostCollectOrThumbEvent postCollectOrThumbEvent = new PostCollectOrThumbEvent();
        postCollectOrThumbEvent.postList = this.postList;
        EventBus.getDefault().post(postCollectOrThumbEvent);
    }

    public final void startPublishActivity() {
        PublishInfo publishInfo = new PublishInfo();
        publishInfo.setPostType(4);
        PapaPublishUtil.getInstance().startPublishActivity(publishInfo);
    }

    public final void onEventMainThread(MemberFocusEvent event) {
        List<PostList> data;
        List<PostList> data2;
        Intrinsics.checkParameterIsNotNull(event, "event");
        int i = event.f1748id;
        int i2 = event.followFlag;
        if (getAdapter() != null) {
            PapaVideoListAdapter adapter = getAdapter();
            if (((adapter == null || (data2 = adapter.getData()) == null) ? 0 : data2.size()) <= 0) {
                return;
            }
            PapaVideoListAdapter adapter2 = getAdapter();
            Integer valueOf = (adapter2 == null || (data = adapter2.getData()) == null) ? null : Integer.valueOf(data.size());
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            int intValue = valueOf.intValue();
            for (int i3 = 0; i3 < intValue; i3++) {
                PapaVideoListAdapter adapter3 = getAdapter();
                if (adapter3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                PostList postList = adapter3.getData().get(i3);
                Intrinsics.checkExpressionValueIsNotNull(postList, "postList");
                if (i == postList.getMemberId()) {
                    postList.setIsAttention(i2);
                    PapaVideoListAdapter adapter4 = getAdapter();
                    if (adapter4 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    adapter4.setData(i3, postList);
                    PapaVideoView papaVideoView = this.papaVideoView;
                    if (papaVideoView != null) {
                        if (papaVideoView == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (papaVideoView.isInPlaybackState()) {
                            PapaVideoView papaVideoView2 = this.papaVideoView;
                            if (papaVideoView2 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            papaVideoView2.setPlayState(0);
                        }
                    }
                    if (this.isResume) {
                        onVideoResume();
                    }
                }
            }
        }
    }

    public final PostList getPostList() {
        return this.postList;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        onVideoRelease();
        this.papaVideoView = null;
        _$_clearFindViewByIdCache();
    }

    public final void setObseverIsAd(Function1<? super Boolean, Unit> function1) {
        this.observerPostIsAd = function1;
    }
}
