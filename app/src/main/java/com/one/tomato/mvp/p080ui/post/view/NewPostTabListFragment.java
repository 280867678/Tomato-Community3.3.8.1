package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.p002v4.app.NotificationCompat;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.one.tomato.R$id;
import com.one.tomato.dialog.AdNoticeDialog;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.NoticeDialog;
import com.one.tomato.dialog.PostActionDialog;
import com.one.tomato.dialog.PostRewardDialog;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.NetWorkEvent;
import com.one.tomato.entity.event.PostCollectOrThumbEvent;
import com.one.tomato.entity.event.PostDetailListDataEvent;
import com.one.tomato.entity.event.PostRefreshEvent;
import com.one.tomato.entity.event.PostTabStatusChange;
import com.one.tomato.entity.event.PostVideoPlayEvent;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.RecommentActivity;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.presenter.NewPostTabListPresenter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.utils.PostVideoMenuUtils;
import com.one.tomato.popup.PostAdDeletePop;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.EmptyViewLayout;
import com.one.tomato.widget.MarqueeTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogConstants;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.anko.AsyncKt;

/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment */
/* loaded from: classes3.dex */
public class NewPostTabListFragment extends MvpBaseRecyclerViewFragment<IPostTabListContact$IPostTabListView, NewPostTabListPresenter, NewPostTabListAdapter, PostList> implements IPostTabListContact$IPostTabListView, NewPostItemOnClickCallBack {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String businessType;
    private int channelId;
    private CustomAlertDialog deletePublishDialog;
    private boolean isJumpPostDetail;
    private boolean isVisibility;
    private Disposable netWorkObserver;
    private Disposable observerPostDetailListData;
    private PostAdDeletePop postAdDeletePop;
    private Disposable postRefreshEvent;
    private BetterHorScrollRecyclerView recyclerViewBetterHorScroll;
    private boolean requestRecommend;
    private boolean showActivity;
    private SystemParam systemParam;
    private Disposable tabRefreshObserver;
    private PostEvenOneTabVideoListManger videoPlayManger;
    private Disposable videoPlayOrStopObserber;
    private int category = -1;
    private Boolean isNeedFresh = false;
    private int postion = -1;

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

    public void addHeaderView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.new_post_tab_list_fragment;
    }

    public void filterSearchView() {
    }

    public int getAttentionFlag() {
        return 0;
    }

    public int getAttentionMemberId() {
        return 0;
    }

    public int getDateType() {
        return 1;
    }

    public String getEventId() {
        return "1";
    }

    public int getGroundId() {
        return 0;
    }

    public int getPersonMemberId() {
        return 0;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public int getPreLoadNumber() {
        return 5;
    }

    public int getRankType() {
        return 1;
    }

    public String getSeachKey() {
        return null;
    }

    public String getSearchSort() {
        return AopConstants.TIME_KEY;
    }

    public int getTagId() {
        return 0;
    }

    public int postRankType() {
        return 0;
    }

    public String postRankUrl() {
        return "/app/articleRank/hotValueList";
    }

    public NewPostTabListFragment() {
        new ArrayList();
    }

    public static final /* synthetic */ NewPostTabListPresenter access$getMPresenter$p(NewPostTabListFragment newPostTabListFragment) {
        return (NewPostTabListPresenter) newPostTabListFragment.getMPresenter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String getBusinessType() {
        return this.businessType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setBusinessType(String str) {
        this.businessType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getCategory() {
        return this.category;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setCategory(int i) {
        this.category = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Boolean isNeedFresh() {
        return this.isNeedFresh;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setNeedFresh(Boolean bool) {
        this.isNeedFresh = bool;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getChannelId() {
        return this.channelId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setChannelId(int i) {
        this.channelId = i;
    }

    public final BetterHorScrollRecyclerView getRecyclerViewBetterHorScroll() {
        return this.recyclerViewBetterHorScroll;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean isVisibility() {
        return this.isVisibility;
    }

    /* compiled from: NewPostTabListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final synchronized NewPostTabListFragment getInstance(int i, String businessType, int i2) {
            NewPostTabListFragment newPostTabListFragment;
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            newPostTabListFragment = new NewPostTabListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            bundle.putInt("channel_id", i2);
            newPostTabListFragment.setArguments(bundle);
            return newPostTabListFragment;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void initRecyclerView() {
        View layoutView = getLayoutView();
        RecyclerView.ItemAnimator itemAnimator = null;
        this.recyclerViewBetterHorScroll = layoutView != null ? (BetterHorScrollRecyclerView) layoutView.findViewById(R.id.recyclerView) : null;
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            itemAnimator = betterHorScrollRecyclerView.getItemAnimator();
        }
        if (itemAnimator == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.SimpleItemAnimator");
        }
        ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 1, false);
        baseLinearLayoutManager.setInitialPrefetchItemCount(2);
        baseLinearLayoutManager.setItemPrefetchEnabled(true);
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.setLayoutManager(baseLinearLayoutManager);
        }
        BetterHorScrollRecyclerView betterHorScrollRecyclerView3 = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView3 != null) {
            betterHorScrollRecyclerView3.setHasFixedSize(true);
        }
        this.videoPlayManger = new PostEvenOneTabVideoListManger();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    protected void initAdapter() {
        EmptyViewLayout emptyViewLayout;
        setAdapter(mo6434createAdapter());
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setPreLoadNumber(getPreLoadNumber());
        }
        NewPostTabListAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setOnLoadMoreListener(getOnLodeMore(), this.recyclerViewBetterHorScroll);
        }
        NewPostTabListAdapter adapter3 = getAdapter();
        if (adapter3 != null && (emptyViewLayout = adapter3.getEmptyViewLayout()) != null) {
            emptyViewLayout.setButtonClickListener(getEmptyOnClick());
        }
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setAdapter(getAdapter());
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initListener() {
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$initListener$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter2, View view, int i) {
                    Intrinsics.checkExpressionValueIsNotNull(adapter2, "adapter");
                    Object obj = adapter2.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                    }
                    NewPostTabListFragment.this.clickItem((PostList) obj, view, i);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void requestType() {
        NewPostTabListPresenter newPostTabListPresenter;
        NewPostTabListPresenter newPostTabListPresenter2;
        NewPostTabListPresenter newPostTabListPresenter3;
        NewPostTabListPresenter newPostTabListPresenter4;
        NewPostTabListPresenter newPostTabListPresenter5;
        NewPostTabListPresenter newPostTabListPresenter6;
        NewPostTabListPresenter newPostTabListPresenter7;
        NewPostTabListPresenter newPostTabListPresenter8;
        NewPostTabListPresenter newPostTabListPresenter9;
        NewPostTabListPresenter newPostTabListPresenter10;
        NewPostTabListPresenter newPostTabListPresenter11;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        int i = this.category;
        if (i > 0) {
            linkedHashMap.put("postType", Integer.valueOf(i));
        }
        int i2 = this.channelId;
        if (i2 > 0) {
            linkedHashMap.put("channelId", Integer.valueOf(i2));
            NewPostTabListPresenter newPostTabListPresenter12 = (NewPostTabListPresenter) getMPresenter();
            if (newPostTabListPresenter12 == null) {
                return;
            }
            newPostTabListPresenter12.requestPostToChannelId(linkedHashMap);
            return;
        }
        String str = this.businessType;
        if (str == null) {
            return;
        }
        int i3 = 1;
        switch (str.hashCode()) {
            case -1791534392:
                if (!str.equals("postSearch0")) {
                    return;
                }
                linkedHashMap.put("key", getSeachKey());
                linkedHashMap.put("sort", getSearchSort());
                newPostTabListPresenter11 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter11 != null) {
                    return;
                }
                newPostTabListPresenter11.requestSerachPost(linkedHashMap);
                return;
            case -1360216880:
                if (!str.equals(C2516Ad.TYPE_CIRCLE)) {
                    return;
                }
                linkedHashMap.put("groupId", Integer.valueOf(getGroundId()));
                NewPostTabListPresenter newPostTabListPresenter13 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter13 == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                DomainServer domainServer = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                sb.append(domainServer.getServerUrl());
                sb.append("/app/article/group/list");
                newPostTabListPresenter13.requestCirclePost(sb.toString(), linkedHashMap);
                return;
            case -1110534050:
                if (!str.equals("circle_hot")) {
                    return;
                }
                linkedHashMap.put("groupId", Integer.valueOf(getGroundId()));
                linkedHashMap.put(LogConstants.APP_ID, 1);
                linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                NewPostTabListPresenter newPostTabListPresenter14 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter14 == null) {
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                DomainServer domainServer2 = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
                sb2.append(domainServer2.getServerUrl());
                sb2.append("/app/article/hotListByGroup");
                newPostTabListPresenter14.requestCirclePost(sb2.toString(), linkedHashMap);
                return;
            case -1025231948:
                if (!str.equals("postRecommend0")) {
                    return;
                }
                if (!this.requestRecommend) {
                    i3 = getRankType();
                }
                linkedHashMap.put("rankType", Integer.valueOf(i3));
                linkedHashMap.put("dateType", Integer.valueOf(!this.requestRecommend ? 3 : getDateType()));
                newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter != null) {
                    return;
                }
                newPostTabListPresenter.requestSearchRecommendPost(linkedHashMap);
                return;
            case -542753442:
                if (!str.equals("browse_rank_post")) {
                    return;
                }
                newPostTabListPresenter8 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter8 != null) {
                    return;
                }
                StringBuilder sb3 = new StringBuilder();
                DomainServer domainServer3 = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer3, "DomainServer.getInstance()");
                sb3.append(domainServer3.getServerUrl());
                sb3.append(postRankUrl());
                newPostTabListPresenter8.requestPostRankList(sb3.toString(), getPageNo(), getPageSize(), postRankType());
                return;
            case -485371922:
                if (!str.equals("homepage")) {
                    return;
                }
                linkedHashMap.put("personMemberId", Integer.valueOf(getPersonMemberId()));
                NewPostTabListPresenter newPostTabListPresenter15 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter15 == null) {
                    return;
                }
                newPostTabListPresenter15.requestPersonPost(linkedHashMap);
                return;
            case -171619332:
                if (!str.equals("postRecommend")) {
                    return;
                }
                if (!this.requestRecommend) {
                }
                linkedHashMap.put("rankType", Integer.valueOf(i3));
                linkedHashMap.put("dateType", Integer.valueOf(!this.requestRecommend ? 3 : getDateType()));
                newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter != null) {
                }
                break;
            case 104387:
                if (!str.equals("img")) {
                    return;
                }
                newPostTabListPresenter4 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter4 == null) {
                    return;
                }
                newPostTabListPresenter4.requestImageVideoPostList(linkedHashMap);
                return;
            case 3496342:
                if (!str.equals("read")) {
                    return;
                }
                newPostTabListPresenter4 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter4 == null) {
                }
                break;
            case 97604824:
                if (!str.equals("focus") || (newPostTabListPresenter2 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter2.requestFoucsPostList(linkedHashMap);
                return;
            case 110342614:
                if (!str.equals("thumb") || (newPostTabListPresenter3 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter3.requestThumbPost(linkedHashMap);
                return;
            case 112202875:
                if (!str.equals("video")) {
                    return;
                }
                newPostTabListPresenter4 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter4 == null) {
                }
                break;
            case 552212025:
                if (!str.equals("homepage_subscribe")) {
                    return;
                }
                linkedHashMap.put("upHostId", Integer.valueOf(getPersonMemberId()));
                NewPostTabListPresenter newPostTabListPresenter16 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter16 == null) {
                    return;
                }
                newPostTabListPresenter16.requestPostSubscribeList(linkedHashMap);
                return;
            case 616217084:
                if (!str.equals("pay_rank_post")) {
                    return;
                }
                newPostTabListPresenter8 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter8 != null) {
                }
                break;
            case 870454113:
                if (!str.equals("hot_rank_post")) {
                    return;
                }
                newPostTabListPresenter8 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter8 != null) {
                }
                break;
            case 949444906:
                if (!str.equals("collect") || (newPostTabListPresenter5 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter5.requestCollectPost(linkedHashMap);
                return;
            case 981643482:
                if (!str.equals("no_focus") || (newPostTabListPresenter6 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter6.requestNoFoucsHotPostList(linkedHashMap);
                return;
            case 989204668:
                if (!str.equals("recommend") || (newPostTabListPresenter7 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter7.requestRecommendPostList(linkedHashMap);
                return;
            case 1041707006:
                if (!str.equals("collect_rank_post")) {
                    return;
                }
                newPostTabListPresenter8 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter8 != null) {
                }
                break;
            case 1099011240:
                if (!str.equals("hot_tag")) {
                    return;
                }
                linkedHashMap.put(ConstantUtils.TAB_TAG_ID, Integer.valueOf(getTagId()));
                NewPostTabListPresenter newPostTabListPresenter17 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter17 == null) {
                    return;
                }
                newPostTabListPresenter17.requestTagPost(linkedHashMap);
                return;
            case 1339678562:
                if (!str.equals("event_message") || (newPostTabListPresenter9 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter9.requestHotMessagePostList(getEventId());
                return;
            case 1377217503:
                if (!str.equals("new_post") || (newPostTabListPresenter10 = (NewPostTabListPresenter) getMPresenter()) == null) {
                    return;
                }
                newPostTabListPresenter10.requestNewPost(linkedHashMap);
                return;
            case 1447403838:
                if (!str.equals("publish_n")) {
                    return;
                }
                linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, "0");
                NewPostTabListPresenter newPostTabListPresenter18 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter18 == null) {
                    return;
                }
                newPostTabListPresenter18.requestPushPost(linkedHashMap);
                return;
            case 1447403849:
                if (!str.equals("publish_y")) {
                    return;
                }
                linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, "1");
                NewPostTabListPresenter newPostTabListPresenter19 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter19 == null) {
                    return;
                }
                newPostTabListPresenter19.requestPushPost(linkedHashMap);
                return;
            case 1604776552:
                if (!str.equals("postSearch")) {
                    return;
                }
                linkedHashMap.put("key", getSeachKey());
                linkedHashMap.put("sort", getSearchSort());
                newPostTabListPresenter11 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter11 != null) {
                }
                break;
            case 1845545083:
                if (!str.equals("new_tag")) {
                    return;
                }
                linkedHashMap.put(ConstantUtils.TAB_TAG_ID, Integer.valueOf(getTagId()));
                linkedHashMap.put("sortType", 0);
                NewPostTabListPresenter newPostTabListPresenter20 = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter20 == null) {
                    return;
                }
                newPostTabListPresenter20.requestTagPost(linkedHashMap);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickItem(PostList postList, View view, int i) {
        if (!Intrinsics.areEqual("publish_n", this.businessType) && postList != null) {
            if (isBlack(postList.getBlackFlag())) {
                ToastUtil.showCenterToast((int) R.string.post_been_black);
            } else if (postList.isAd()) {
                NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
                if (newPostTabListPresenter == null) {
                    return;
                }
                newPostTabListPresenter.startPostAd(postList);
            } else {
                postList.setViewCount(postList.getViewCount() + 1);
                jumpPostDetail(postList, view, i, false);
            }
        }
    }

    public final void jumpPostDetail(PostList postList, View view, int i, boolean z) {
        this.postion = i;
        LogUtil.m3787d("yan1", "jumpPostDetail 跳轉的角標---------" + i);
        AsyncKt.doAsync$default(this, null, new NewPostTabListFragment$jumpPostDetail$1(this, postList, i, z), 1, null);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
        super.onResume();
        if (!this.isVisibility || (postEvenOneTabVideoListManger = this.videoPlayManger) == null) {
            return;
        }
        postEvenOneTabVideoListManger.activityOnResume();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onVisibleLoad() {
        super.onVisibleLoad();
        this.isVisibility = true;
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            postEvenOneTabVideoListManger.recoverInitPlay(mContext);
        }
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger2 = this.videoPlayManger;
        if (postEvenOneTabVideoListManger2 != null) {
            postEvenOneTabVideoListManger2.setNewPostItemClickCallBack(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void rePlaceLazyLoad() {
        super.rePlaceLazyLoad();
        this.isVisibility = true;
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            postEvenOneTabVideoListManger.recoverInitPlay(mContext);
        }
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger2 = this.videoPlayManger;
        if (postEvenOneTabVideoListManger2 != null) {
            postEvenOneTabVideoListManger2.setNewPostItemClickCallBack(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onInvisibleLoad() {
        super.onInvisibleLoad();
        this.isVisibility = false;
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.onReleaseVideoManger();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerInsertAd(Map<String, Object> map) {
        NewPostTabListAdapter adapter;
        Intrinsics.checkParameterIsNotNull(map, "map");
        Object obj = map.get("num");
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        int intValue = ((Integer) obj).intValue();
        PostList postList = (PostList) map.get("post");
        if (postList == null || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.addData(intValue, (int) postList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
        requestType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public NewPostTabListAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            return new NewPostTabListAdapter(mContext, this.recyclerViewBetterHorScroll, this, this.videoPlayManger);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewPostTabListPresenter mo6441createPresenter() {
        return new NewPostTabListPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Bundle arguments = getArguments();
        this.category = arguments != null ? arguments.getInt("category") : -1;
        String str = null;
        this.businessType = arguments != null ? arguments.getString("business") : null;
        this.isNeedFresh = arguments != null ? Boolean.valueOf(arguments.getBoolean("need_refresh")) : null;
        int i = 0;
        this.channelId = arguments != null ? arguments.getInt("channel_id") : 0;
        boolean z = true;
        if (Intrinsics.areEqual("recommend", this.businessType) || Intrinsics.areEqual("focus", this.businessType) || Intrinsics.areEqual("publish_y", this.businessType) || Intrinsics.areEqual("collect", this.businessType) || Intrinsics.areEqual("thumb", this.businessType) || Intrinsics.areEqual(C2516Ad.TYPE_CIRCLE, this.businessType) || Intrinsics.areEqual("homepage", this.businessType) || Intrinsics.areEqual("postSearch0", this.businessType) || Intrinsics.areEqual("postRecommend0", this.businessType) || Intrinsics.areEqual("no_focus", this.businessType) || Intrinsics.areEqual("review_post", this.businessType) || Intrinsics.areEqual(this.businessType, "review_post_pre") || Intrinsics.areEqual(this.businessType, "hot_tag") || Intrinsics.areEqual(this.businessType, "event_message") || postRankType() == 1) {
            setUserVisibleHint(true);
        }
        if (postRankType() >= 1) {
            setPageSize(1);
        }
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setBusinessType(this.businessType);
        }
        this.systemParam = DBUtil.getSystemParam();
        Gson gson = BaseApplication.getGson();
        SystemParam systemParam = this.systemParam;
        if (systemParam != null) {
            str = systemParam.getActivityJson();
        }
        final RecommentActivity recommentActivity = (RecommentActivity) gson.fromJson(str, (Class<Object>) RecommentActivity.class);
        if (recommentActivity == null) {
            this.showActivity = false;
        } else {
            if (recommentActivity.showEntrance != 1) {
                z = false;
            }
            this.showActivity = z;
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_activity);
            if (imageView != null) {
                if (!Intrinsics.areEqual(this.businessType, "recommend") || !this.showActivity) {
                    i = 8;
                }
                imageView.setVisibility(i);
            }
            ImageLoaderUtil.loadSecImage(getMContext(), (ImageView) _$_findCachedViewById(R$id.iv_activity), new ImageBean(recommentActivity.picUrl), ImageLoaderUtil.getDefaultImageOptions((ImageView) _$_findCachedViewById(R$id.iv_activity)));
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.iv_activity);
            if (imageView2 != null) {
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$inintData$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Context mContext;
                        RecommentActivity recommentActivity2 = recommentActivity;
                        int i2 = recommentActivity2.openType;
                        String str2 = recommentActivity2.activityLink;
                        mContext = NewPostTabListFragment.this.getMContext();
                        AppUtil.startActionView(i2, str2, mContext);
                    }
                });
            }
        }
        autoRecycylerViewPlay();
        registerRxBus();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<PostList> data;
        NewPostTabListAdapter adapter;
        NewPostTabListAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        cancleLoading();
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        NewPostTabListAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        this.isVisibility = true;
        this.requestRecommend = false;
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageLoaderUtil.loadNormalDrawableGif(getContext(), (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main), R.drawable.refresh_empty_load_gif);
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$onLazyLoad$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewPostTabListFragment.this.refresh();
                }
            }, 100L);
        }
        String str = this.businessType;
        if (str == null) {
            return;
        }
        int hashCode = str.hashCode();
        if (hashCode != -1025231948) {
            if (hashCode != -171619332 || !str.equals("postRecommend")) {
                return;
            }
        } else if (!str.equals("postRecommend0")) {
            return;
        }
        filterSearchView();
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        updateData(arrayList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void HandlerPostMainNotify(final ArrayList<MainNotifyBean> arrayList) {
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$HandlerPostMainNotify$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewPostTabListFragment.this.showNoticeDialog(arrayList);
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void registerRxBus() {
        this.videoPlayOrStopObserber = RxBus.getDefault().toObservable(PostVideoPlayEvent.class).subscribe(new Consumer<PostVideoPlayEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$1
            /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
                r2 = r1.this$0.videoPlayManger;
             */
            /* JADX WARN: Code restructure failed: missing block: B:7:0x001f, code lost:
                r2 = r1.this$0.videoPlayManger;
             */
            @Override // io.reactivex.functions.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(PostVideoPlayEvent postVideoPlayEvent) {
                PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                PostEvenOneTabVideoListManger postEvenOneTabVideoListManger2;
                Boolean valueOf = postVideoPlayEvent != null ? Boolean.valueOf(postVideoPlayEvent.getBoolean()) : null;
                if (Intrinsics.areEqual(valueOf, true)) {
                    if (!NewPostTabListFragment.this.isVisibility() || postEvenOneTabVideoListManger2 == null) {
                        return;
                    }
                    postEvenOneTabVideoListManger2.activityOnResume();
                } else if (!Intrinsics.areEqual(valueOf, false) || postEvenOneTabVideoListManger == null) {
                } else {
                    postEvenOneTabVideoListManger.activityOnStop();
                }
            }
        });
        this.tabRefreshObserver = RxBus.getDefault().toObservable(PostTabStatusChange.class).subscribe(new Consumer<PostTabStatusChange>() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(PostTabStatusChange postTabStatusChange) {
                boolean isVisibleToUser;
                SmartRefreshLayout refreshLayout;
                if (!(postTabStatusChange instanceof PostTabStatusChange) || !postTabStatusChange.refresh) {
                    return;
                }
                isVisibleToUser = NewPostTabListFragment.this.isVisibleToUser();
                if (!isVisibleToUser) {
                    return;
                }
                AppUtil.recyclerViewScroll(NewPostTabListFragment.this.getRecyclerViewBetterHorScroll(), 0, 0, 100);
                NewPostTabListFragment.this.setLazyLoad(false);
                refreshLayout = NewPostTabListFragment.this.getRefreshLayout();
                if (refreshLayout == null) {
                    return;
                }
                refreshLayout.autoRefresh(100);
            }
        });
        this.netWorkObserver = RxBus.getDefault().toObservable(NetWorkEvent.class).subscribe(new Consumer<NetWorkEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$3
            @Override // io.reactivex.functions.Consumer
            public final void accept(NetWorkEvent netWorkEvent) {
                NewPostTabListAdapter adapter;
                if (!(netWorkEvent instanceof NetWorkEvent) || !Intrinsics.areEqual("recommend", NewPostTabListFragment.this.getBusinessType())) {
                    return;
                }
                adapter = NewPostTabListFragment.this.getAdapter();
                if (adapter == null || NewPostTabListFragment.this._$_findCachedViewById(R$id.tipHeadView) == null) {
                    return;
                }
                LinearLayout linearLayout = (LinearLayout) NewPostTabListFragment.this._$_findCachedViewById(R$id.ll_notice);
                if (linearLayout != null) {
                    linearLayout.setVisibility(8);
                }
                if (netWorkEvent.isNetCollected) {
                    LinearLayout linearLayout2 = (LinearLayout) NewPostTabListFragment.this._$_findCachedViewById(R$id.ll_net);
                    if (linearLayout2 != null) {
                        linearLayout2.setVisibility(8);
                    }
                    View _$_findCachedViewById = NewPostTabListFragment.this._$_findCachedViewById(R$id.tipHeadView);
                    if (_$_findCachedViewById == null) {
                        return;
                    }
                    _$_findCachedViewById.setVisibility(8);
                    return;
                }
                LinearLayout linearLayout3 = (LinearLayout) NewPostTabListFragment.this._$_findCachedViewById(R$id.ll_net);
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(0);
                }
                View _$_findCachedViewById2 = NewPostTabListFragment.this._$_findCachedViewById(R$id.tipHeadView);
                if (_$_findCachedViewById2 == null) {
                    return;
                }
                _$_findCachedViewById2.setVisibility(0);
            }
        });
        this.observerPostDetailListData = RxBus.getDefault().toObservable(PostDetailListDataEvent.class).subscribe(new Consumer<PostDetailListDataEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$4
            @Override // io.reactivex.functions.Consumer
            public final void accept(PostDetailListDataEvent postDetailListDataEvent) {
                if (postDetailListDataEvent instanceof PostDetailListDataEvent) {
                    NewPostTabListFragment.this.setScrollPostion(postDetailListDataEvent);
                }
            }
        });
        this.postRefreshEvent = RxBus.getDefault().toObservable(PostRefreshEvent.class).subscribe(new Consumer<PostRefreshEvent>() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$5
            @Override // io.reactivex.functions.Consumer
            public final void accept(PostRefreshEvent postRefreshEvent) {
                if (postRefreshEvent instanceof PostRefreshEvent) {
                    if ((postRefreshEvent.getChannelId() > 0 && postRefreshEvent.getChannelId() == NewPostTabListFragment.this.getChannelId()) || NewPostTabListFragment.this.isVisibility()) {
                        NewPostTabListFragment.this.postRefreshPay(postRefreshEvent);
                    } else if ((postRefreshEvent.getCategory() != NewPostTabListFragment.this.getCategory() || postRefreshEvent.getCategory() <= -1) && !NewPostTabListFragment.this.isVisibility()) {
                    } else {
                        NewPostTabListFragment.this.postRefreshPay(postRefreshEvent);
                    }
                }
            }
        });
        RxBus.getDefault().toObservable(PostCollectOrThumbEvent.class).subscribe(NewPostTabListFragment$registerRxBus$6.INSTANCE);
        RxSubscriptions.add(this.videoPlayOrStopObserber);
        RxSubscriptions.add(this.tabRefreshObserver);
        RxSubscriptions.add(this.netWorkObserver);
        RxSubscriptions.add(this.observerPostDetailListData);
        RxSubscriptions.add(this.postRefreshEvent);
    }

    private final void unRegisterRxBus() {
        RxSubscriptions.remove(this.tabRefreshObserver);
        RxSubscriptions.remove(this.videoPlayOrStopObserber);
        RxSubscriptions.remove(this.netWorkObserver);
        RxSubscriptions.remove(this.observerPostDetailListData);
        RxSubscriptions.remove(this.postRefreshEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void postRefreshPay(PostRefreshEvent postRefreshEvent) {
        NewPostTabListAdapter adapter;
        PostList data = postRefreshEvent.getData();
        NewPostTabListAdapter adapter2 = getAdapter();
        List<PostList> data2 = adapter2 != null ? adapter2.getData() : null;
        if (data2 == null || data2.isEmpty()) {
            return;
        }
        int size = data2.size();
        for (int i = 0; i < size; i++) {
            PostList postList = data2.get(i);
            Intrinsics.checkExpressionValueIsNotNull(postList, "data1[its]");
            if (postList.getId() == data.getId() && (adapter = getAdapter()) != null) {
                adapter.setData(i, data);
            }
        }
    }

    public final void setScrollPostion(final PostDetailListDataEvent it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$setScrollPostion$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (it2.getChannelId() > 0 && it2.getChannelId() == NewPostTabListFragment.this.getChannelId() && NewPostTabListFragment.this.isVisibility()) {
                        NewPostTabListFragment.this.setScrollPostion2(it2);
                    } else if (it2.getCategory() != NewPostTabListFragment.this.getCategory() || it2.getCategory() <= -1 || !NewPostTabListFragment.this.isVisibility()) {
                    } else {
                        NewPostTabListFragment.this.setScrollPostion2(it2);
                    }
                }
            });
        }
    }

    public final synchronized void setScrollPostion2(PostDetailListDataEvent it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.post(new NewPostTabListFragment$setScrollPostion2$1(this, it2));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void autoRecycylerViewPlay() {
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$autoRecycylerViewPlay$1
                @Override // android.support.p005v7.widget.RecyclerView.OnChildAttachStateChangeListener
                public void onChildViewAttachedToWindow(View p0) {
                    Intrinsics.checkParameterIsNotNull(p0, "p0");
                }

                /* JADX WARN: Code restructure failed: missing block: B:5:0x0017, code lost:
                    r2 = r1.this$0.videoPlayManger;
                 */
                @Override // android.support.p005v7.widget.RecyclerView.OnChildAttachStateChangeListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void onChildViewDetachedFromWindow(View p0) {
                    PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                    Intrinsics.checkParameterIsNotNull(p0, "p0");
                    FrameLayout frameLayout = (FrameLayout) p0.findViewById(R.id.fram_ijkplay_view);
                    if (frameLayout == null || frameLayout.getChildCount() <= 0 || postEvenOneTabVideoListManger == null) {
                        return;
                    }
                    postEvenOneTabVideoListManger.release();
                }
            });
        }
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = this.recyclerViewBetterHorScroll;
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$autoRecycylerViewPlay$2
                /* JADX WARN: Code restructure failed: missing block: B:5:0x0022, code lost:
                    r1 = r4.this$0.videoPlayManger;
                 */
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    boolean z;
                    NewPostTabListAdapter adapter;
                    NewPostTabListPresenter access$getMPresenter$p;
                    PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                    Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 0) {
                        if (Intrinsics.areEqual(PreferencesUtil.getInstance().getString("video_auto"), "1") && postEvenOneTabVideoListManger != null) {
                            postEvenOneTabVideoListManger.autoPostVideoPlay(NewPostTabListFragment.this.getRecyclerViewBetterHorScroll(), NewPostTabListFragment.this.isVisibility());
                        }
                        BaseLinearLayoutManager baseLinearLayoutManager = (BaseLinearLayoutManager) recyclerView.getLayoutManager();
                        if (baseLinearLayoutManager != null) {
                            int findFirstVisibleItemPosition = baseLinearLayoutManager.findFirstVisibleItemPosition();
                            adapter = NewPostTabListFragment.this.getAdapter();
                            PostList item = adapter != null ? adapter.getItem(findFirstVisibleItemPosition) : null;
                            NewPostTabListPresenter access$getMPresenter$p2 = NewPostTabListFragment.access$getMPresenter$p(NewPostTabListFragment.this);
                            if (access$getMPresenter$p2 != null) {
                                access$getMPresenter$p2.lookADUp(item);
                            }
                            if (Intrinsics.areEqual("recommend", NewPostTabListFragment.this.getBusinessType()) && (access$getMPresenter$p = NewPostTabListFragment.access$getMPresenter$p(NewPostTabListFragment.this)) != null) {
                                access$getMPresenter$p.viewedPostUpload(item);
                            }
                        }
                    }
                    if (Intrinsics.areEqual(NewPostTabListFragment.this.getBusinessType(), "recommend") && i == 0) {
                        z = NewPostTabListFragment.this.showActivity;
                        if (z) {
                            ImageView imageView = (ImageView) NewPostTabListFragment.this._$_findCachedViewById(R$id.iv_activity);
                            if (imageView == null) {
                                return;
                            }
                            imageView.setVisibility(0);
                            return;
                        }
                    }
                    ImageView imageView2 = (ImageView) NewPostTabListFragment.this._$_findCachedViewById(R$id.iv_activity);
                    if (imageView2 != null) {
                        imageView2.setVisibility(8);
                    }
                }
            });
        }
    }

    public final void cancleLoading() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003a, code lost:
        if (r2.equals("postSearch") != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009d, code lost:
        r2 = getAdapter();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a3, code lost:
        if (r2 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a5, code lost:
        r2.setNewData(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a8, code lost:
        addHeaderView();
        r2 = getAdapter();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00b2, code lost:
        if (r2 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b4, code lost:
        r2 = r2.getData();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00b8, code lost:
        if (r2 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ba, code lost:
        r2 = java.lang.Boolean.valueOf(r2.isEmpty());
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00c4, code lost:
        if (r2 == null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ca, code lost:
        if (r2.booleanValue() == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00cc, code lost:
        r4.businessType = "postRecommend0";
        r4.requestRecommend = true;
        requestType();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d6, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d7, code lost:
        kotlin.jvm.internal.Intrinsics.throwNpe();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00da, code lost:
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c3, code lost:
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x009b, code lost:
        if (r2.equals("postSearch0") != false) goto L16;
     */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateData(ArrayList<PostList> arrayList) {
        List<PostList> data;
        List<PostList> data2;
        String str;
        if (arrayList != null && (!arrayList.isEmpty()) && Intrinsics.areEqual(this.businessType, "recommend")) {
            PostUtils.INSTANCE.preloadListVideo(arrayList);
        }
        if (getState() == getGET_LIST_REFRESH() && (str = this.businessType) != null) {
            switch (str.hashCode()) {
                case -1791534392:
                    break;
                case 981643482:
                    if (str.equals("no_focus")) {
                        NewPostTabListAdapter adapter = getAdapter();
                        if (adapter != null) {
                            adapter.removeAllHeaderView();
                        }
                        addHeaderView();
                        break;
                    }
                    break;
                case 989204668:
                    if (str.equals("recommend")) {
                        NewPostTabListAdapter adapter2 = getAdapter();
                        if (adapter2 != null) {
                            adapter2.removeAllHeaderView();
                        }
                        PostHotHeadView postHotHeadView = new PostHotHeadView(getMContext());
                        NewPostTabListAdapter adapter3 = getAdapter();
                        if (adapter3 != null) {
                            adapter3.addHeaderView(postHotHeadView);
                            break;
                        }
                    }
                    break;
                case 1377217503:
                    if (str.equals("new_post")) {
                        NewPostTabListAdapter adapter4 = getAdapter();
                        if (adapter4 != null) {
                            adapter4.removeAllHeaderView();
                        }
                        addHeaderView();
                        break;
                    }
                    break;
                case 1604776552:
                    break;
            }
        }
        cancleLoading();
        super.updateData(arrayList);
        if (Intrinsics.areEqual("review_post", this.businessType) || Intrinsics.areEqual(this.businessType, "review_post_pre")) {
            addHeaderView();
            return;
        }
        if (getState() == getGET_LIST_REFRESH()) {
            NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
            if (newPostTabListPresenter != null) {
                newPostTabListPresenter.insertPageIndex(1);
            }
            BetterHorScrollRecyclerView betterHorScrollRecyclerView = this.recyclerViewBetterHorScroll;
            if (betterHorScrollRecyclerView != null) {
                betterHorScrollRecyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$updateData$1
                    /* JADX WARN: Code restructure failed: missing block: B:3:0x0014, code lost:
                        r0 = r3.this$0.videoPlayManger;
                     */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                        if (!Intrinsics.areEqual(PreferencesUtil.getInstance().getString("video_auto"), "1") || postEvenOneTabVideoListManger == null) {
                            return;
                        }
                        postEvenOneTabVideoListManger.autoPostVideoPlay(NewPostTabListFragment.this.getRecyclerViewBetterHorScroll(), NewPostTabListFragment.this.isVisibility());
                    }
                }, 200L);
            }
        }
        if (this.category <= 0 && this.channelId <= 0 && !Intrinsics.areEqual(this.businessType, "recommend")) {
            return;
        }
        NewPostTabListPresenter newPostTabListPresenter2 = (NewPostTabListPresenter) getMPresenter();
        int i = 0;
        if (newPostTabListPresenter2 != null) {
            NewPostTabListAdapter adapter5 = getAdapter();
            newPostTabListPresenter2.insertAd((adapter5 == null || (data2 = adapter5.getData()) == null) ? 0 : data2.size());
        }
        NewPostTabListPresenter newPostTabListPresenter3 = (NewPostTabListPresenter) getMPresenter();
        if (newPostTabListPresenter3 == null) {
            return;
        }
        NewPostTabListAdapter adapter6 = getAdapter();
        if (adapter6 != null && (data = adapter6.getData()) != null) {
            i = data.size();
        }
        newPostTabListPresenter3.insertCircle(i);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemShare(PostList postList) {
        PostShareActivity.Companion.startAct(getMContext(), postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public boolean itemClickZan(PostList postList, int i) {
        if (postList != null) {
            if (AppUtil.isFastClick(postList.getId(), ConstantUtils.MAX_ITEM_NUM)) {
                ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                return false;
            }
            NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
            if (newPostTabListPresenter == null) {
                return true;
            }
            newPostTabListPresenter.thumbUp(postList);
            return true;
        }
        return false;
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemShowAuthInfo(PostList postList, int i) {
        if (getMContext() != null) {
            PostActionDialog postActionDialog = new PostActionDialog(getMContext());
            int attentionMemberId = getAttentionMemberId();
            if (postList != null && attentionMemberId == postList.getMemberId()) {
                postList.setIsAttention(getAttentionFlag());
            }
            postActionDialog.setPostList(i, postList);
            postActionDialog.initCallBack(this);
            postActionDialog.show();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemADClick(PostList postList) {
        NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
        if (newPostTabListPresenter != null) {
            newPostTabListPresenter.startPostAd(postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemHead(PostList postList) {
        Context mContext;
        if (postList == null || !postList.isAd()) {
            if (postList == null || (mContext = getMContext()) == null) {
                return;
            }
            NewMyHomePageActivity.Companion.startActivity(mContext, postList.getMemberId());
            return;
        }
        NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
        if (newPostTabListPresenter == null) {
            return;
        }
        newPostTabListPresenter.startPostAd(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemConmment(PostList postList, int i, View view) {
        if (postList != null) {
            jumpPostDetail(postList, view, i, true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerRemoveItem(int i) {
        hideWaitingDialog();
        ToastUtil.showCenterToast((int) R.string.post_delete_success);
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.remove(i);
        }
        UserInfoManager.setPublishCount(false);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemDelete(PostList postList, int i, View view) {
        showDeletePostDialog(i, view, postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemCircle(PostList postList) {
        if (postList != null) {
            CircleDetail circleDetail = new CircleDetail();
            circleDetail.setName(postList.getGroupName());
            circleDetail.setId(postList.getGroupId());
            CircleSingleActivity.startActivity(getMContext(), circleDetail);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemClick(PostList postList, View view, int i) {
        clickItem(postList, view, i);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostCollect(PostList postList) {
        NewPostTabListPresenter newPostTabListPresenter;
        if (postList == null || (newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter()) == null) {
            return;
        }
        newPostTabListPresenter.collect(postList);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostFoucs(PostList postList) {
        if (postList != null) {
            int i = 1;
            if (postList.getIsAttention() == 1) {
                i = 0;
            }
            NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
            if (newPostTabListPresenter == null) {
                return;
            }
            newPostTabListPresenter.foucs(postList, i);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemNeedPay(PostList postList) {
        PostRewardDialog.Companion.showDialog(postList).show(getChildFragmentManager(), "PostRewardDialog");
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemPostPayComplete(PostList postList) {
        int i;
        NewPostTabListAdapter adapter;
        if (postList != null) {
            NewPostTabListAdapter adapter2 = getAdapter();
            List<PostList> data = adapter2 != null ? adapter2.getData() : null;
            if (data != null) {
                i = -1;
                int i2 = 0;
                for (Object obj : data) {
                    int i3 = i2 + 1;
                    if (i2 >= 0) {
                        PostList postList2 = (PostList) obj;
                        int id = postList.getId();
                        Intrinsics.checkExpressionValueIsNotNull(postList2, "postList");
                        if (id == postList2.getId()) {
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
            if (i == -1 || (adapter = getAdapter()) == null) {
                return;
            }
            adapter.setData(i, postList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void itemTagDelete(Tag tag, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        if (tag.getTagId() > 0) {
            NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
            if (newPostTabListPresenter == null) {
                return;
            }
            newPostTabListPresenter.deleteTag(tag.getTagId(), "", i, i2, i3);
            return;
        }
        NewPostTabListPresenter newPostTabListPresenter2 = (NewPostTabListPresenter) getMPresenter();
        if (newPostTabListPresenter2 == null) {
            return;
        }
        String tagName = tag.getTagName();
        Intrinsics.checkExpressionValueIsNotNull(tagName, "tag.tagName");
        newPostTabListPresenter2.deleteTag(0, tagName, i, i2, i3);
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerDeleteTagSuccess(int i, int i2) {
        NewPostTabListAdapter adapter;
        PostList item;
        ArrayList<Tag> tagList;
        try {
            NewPostTabListAdapter adapter2 = getAdapter();
            if (adapter2 != null && (item = adapter2.getItem(i2)) != null && (tagList = item.getTagList()) != null) {
                tagList.remove(i);
            }
            NewPostTabListAdapter adapter3 = getAdapter();
            PostList item2 = adapter3 != null ? adapter3.getItem(i2) : null;
            if (item2 == null || (adapter = getAdapter()) == null) {
                return;
            }
            adapter.setData(i2, item2);
        } catch (Exception unused) {
            LogUtil.m3788d("yan");
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void callVideoDialog(PostList postList) {
        Context mContext = getMContext();
        if (mContext != null) {
            new PostVideoMenuUtils(mContext, postList, this).showMenuDialog();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack
    public void jumpPostDetailActivity(int i) {
        NewPostTabListAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if (data != null) {
            int i2 = 0;
            for (Object obj : data) {
                int i3 = i2 + 1;
                if (i2 >= 0) {
                    PostList postList = (PostList) obj;
                    Intrinsics.checkExpressionValueIsNotNull(postList, "postList");
                    if (postList.getId() == i) {
                        jumpPostDetail(postList, null, i2, false);
                    }
                    i2 = i3;
                } else {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
        }
    }

    public final void showDeletePostDialog(final int i, View view, final PostList postList) {
        if (postList == null || view == null) {
            return;
        }
        if (postList.isAd()) {
            if (this.postAdDeletePop == null) {
                this.postAdDeletePop = new PostAdDeletePop(getMContext(), new PostAdDeletePop.PostAdDeleteSuccessListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showDeletePostDialog$1
                    @Override // com.one.tomato.popup.PostAdDeletePop.PostAdDeleteSuccessListener
                    public void deletePostAdIng(int i2) {
                        NewPostTabListFragment.this.showWaitingDialog();
                    }

                    @Override // com.one.tomato.popup.PostAdDeletePop.PostAdDeleteSuccessListener
                    public void deletePostAdSuccess(int i2) {
                        NewPostTabListAdapter adapter;
                        NewPostTabListFragment.this.hideWaitingDialog();
                        adapter = NewPostTabListFragment.this.getAdapter();
                        if (adapter != null) {
                            adapter.remove(i2);
                        }
                    }

                    @Override // com.one.tomato.popup.PostAdDeletePop.PostAdDeleteSuccessListener
                    public void deletePostAdFail(int i2) {
                        ToastUtil.showCenterToast((int) R.string.post_delete_fail);
                        NewPostTabListFragment.this.hideWaitingDialog();
                    }
                });
            }
            PostAdDeletePop postAdDeletePop = this.postAdDeletePop;
            if (postAdDeletePop == null) {
                return;
            }
            if (postAdDeletePop != null) {
                postAdDeletePop.setPostList(i, postList);
            }
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int measuredHeight = view.getMeasuredHeight();
            view.getMeasuredWidth();
            int height = ((((int) DisplayMetricsUtils.getHeight()) - iArr[1]) - measuredHeight) - ((int) DisplayMetricsUtils.dp2px(50.0f));
            PostAdDeletePop postAdDeletePop2 = this.postAdDeletePop;
            Integer valueOf = postAdDeletePop2 != null ? Integer.valueOf(postAdDeletePop2.getHeight()) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            if (height <= valueOf.intValue()) {
                PostAdDeletePop postAdDeletePop3 = this.postAdDeletePop;
                if (postAdDeletePop3 != null) {
                    postAdDeletePop3.setBackground(true);
                }
                PostAdDeletePop postAdDeletePop4 = this.postAdDeletePop;
                if (postAdDeletePop4 != null) {
                    int i2 = iArr[1];
                    if (postAdDeletePop4 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    postAdDeletePop4.showAtLocation(view, 8388659, 30, i2 - postAdDeletePop4.getHeight());
                }
            } else {
                PostAdDeletePop postAdDeletePop5 = this.postAdDeletePop;
                if (postAdDeletePop5 != null) {
                    postAdDeletePop5.setBackground(false);
                }
                PostAdDeletePop postAdDeletePop6 = this.postAdDeletePop;
                if (postAdDeletePop6 != null) {
                    postAdDeletePop6.showAtLocation(view, 8388659, 30, iArr[1] + measuredHeight);
                }
            }
            backgroundAlpha(0.5f);
            PostAdDeletePop postAdDeletePop7 = this.postAdDeletePop;
            if (postAdDeletePop7 == null) {
                return;
            }
            postAdDeletePop7.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showDeletePostDialog$3
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    PostAdDeletePop postAdDeletePop8;
                    NewPostTabListFragment.this.backgroundAlpha(1.0f);
                    postAdDeletePop8 = NewPostTabListFragment.this.postAdDeletePop;
                    if (postAdDeletePop8 != null) {
                        postAdDeletePop8.init();
                    }
                }
            });
        } else if (!Intrinsics.areEqual("publish_y", this.businessType)) {
        } else {
            if (this.deletePublishDialog == null) {
                this.deletePublishDialog = new CustomAlertDialog(getMContext(), false);
                CustomAlertDialog customAlertDialog = this.deletePublishDialog;
                if (customAlertDialog != null) {
                    customAlertDialog.setMessage(R.string.post_delete_tip);
                }
                CustomAlertDialog customAlertDialog2 = this.deletePublishDialog;
                if (customAlertDialog2 != null) {
                    customAlertDialog2.setCancelButton(R.string.common_cancel);
                }
                CustomAlertDialog customAlertDialog3 = this.deletePublishDialog;
                if (customAlertDialog3 != null) {
                    customAlertDialog3.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showDeletePostDialog$4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CustomAlertDialog customAlertDialog4;
                            customAlertDialog4 = NewPostTabListFragment.this.deletePublishDialog;
                            if (customAlertDialog4 != null) {
                                customAlertDialog4.dismiss();
                            }
                            NewPostTabListPresenter access$getMPresenter$p = NewPostTabListFragment.access$getMPresenter$p(NewPostTabListFragment.this);
                            if (access$getMPresenter$p != null) {
                                access$getMPresenter$p.requestDeletePushPost(String.valueOf(postList.getId()), DBUtil.getMemberId(), i);
                            }
                        }
                    });
                }
            }
            CustomAlertDialog customAlertDialog4 = this.deletePublishDialog;
            if (customAlertDialog4 == null) {
                return;
            }
            customAlertDialog4.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showNoticeDialog(ArrayList<MainNotifyBean> arrayList) {
        if (arrayList != null && (!arrayList.isEmpty())) {
            MainNotifyBean mainNotifyBean = null;
            Iterator<MainNotifyBean> it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                MainNotifyBean item = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(item, "item");
                if ("0".equals(item.getWindowType())) {
                    mainNotifyBean = item;
                    break;
                }
            }
            if (mainNotifyBean != null) {
                showNoticeView(mainNotifyBean);
                if (PreferencesUtil.getInstance().getBoolean("need_upgrade")) {
                    BaseApplication baseApplication = BaseApplication.instance;
                    Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
                    if (!baseApplication.isChess()) {
                        return;
                    }
                }
                if (Intrinsics.areEqual("0", mainNotifyBean.getIsWindow())) {
                    showForceAdNoticeDialog();
                    return;
                } else {
                    showTextNoticeDialog(mainNotifyBean).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showNoticeDialog$1
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            NewPostTabListFragment.this.showForceAdNoticeDialog();
                        }
                    });
                    return;
                }
            }
            showForceAdNoticeDialog();
            return;
        }
        showForceAdNoticeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NoticeDialog showTextNoticeDialog(MainNotifyBean mainNotifyBean) {
        return new NoticeDialog(getMContext(), mainNotifyBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showForceAdNoticeDialog() {
        ArrayList<AdPage> adPage;
        if (PreferencesUtil.getInstance().getBoolean("need_upgrade") || (adPage = DBUtil.getAdPage(C2516Ad.TYPE_FORCE)) == null || !(!adPage.isEmpty())) {
            return;
        }
        new AdNoticeDialog(getMContext(), adPage.get(0));
    }

    private final void showNoticeView(final MainNotifyBean mainNotifyBean) {
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.ll_net);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.ll_notice);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(0);
        }
        View _$_findCachedViewById = _$_findCachedViewById(R$id.tipHeadView);
        if (_$_findCachedViewById != null) {
            _$_findCachedViewById.setVisibility(0);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t");
        sb.append(mainNotifyBean != null ? mainNotifyBean.getNoticeTitle() : null);
        SpannableString spannableString = new SpannableString(sb.toString());
        spannableString.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showNoticeView$titleClickSpan$1
            @Override // android.text.style.ClickableSpan
            public void onClick(View widget) {
                Intrinsics.checkParameterIsNotNull(widget, "widget");
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                Intrinsics.checkParameterIsNotNull(ds, "ds");
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#515569"));
            }
        }, 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        SpannableString spannableString2 = new SpannableString("\t\t\t" + AppUtil.getString(R.string.post_notice_detail));
        spannableString2.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showNoticeView$detailClickSpan$1
            @Override // android.text.style.ClickableSpan
            public void onClick(View widget) {
                Intrinsics.checkParameterIsNotNull(widget, "widget");
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                Intrinsics.checkParameterIsNotNull(ds, "ds");
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.parseColor("#ff5252"));
            }
        }, 0, spannableString2.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString2);
        MarqueeTextView marqueeTextView = (MarqueeTextView) _$_findCachedViewById(R$id.tv_notice);
        if (marqueeTextView != null) {
            marqueeTextView.setText(spannableStringBuilder);
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_delete_notice);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showNoticeView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    View _$_findCachedViewById2 = NewPostTabListFragment.this._$_findCachedViewById(R$id.tipHeadView);
                    if (_$_findCachedViewById2 != null) {
                        _$_findCachedViewById2.setVisibility(8);
                    }
                    LinearLayout linearLayout3 = (LinearLayout) NewPostTabListFragment.this._$_findCachedViewById(R$id.ll_notice);
                    if (linearLayout3 != null) {
                        linearLayout3.setVisibility(8);
                    }
                }
            });
        }
        LinearLayout linearLayout3 = (LinearLayout) _$_findCachedViewById(R$id.ll_notice);
        if (linearLayout3 != null) {
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$showNoticeView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewPostTabListFragment newPostTabListFragment = NewPostTabListFragment.this;
                    MainNotifyBean mainNotifyBean2 = mainNotifyBean;
                    if (mainNotifyBean2 != null) {
                        newPostTabListFragment.showTextNoticeDialog(mainNotifyBean2);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        cancleLoading();
        unRegisterRxBus();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.onReleaseVideoManger();
        }
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.activityOnStop();
        }
    }

    public final void videoStop() {
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger = this.videoPlayManger;
        if (postEvenOneTabVideoListManger != null) {
            postEvenOneTabVideoListManger.release();
        }
    }
}
