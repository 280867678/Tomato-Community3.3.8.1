package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.PostPayEvent;
import com.one.tomato.entity.event.VideoTabStatusChange;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.NewPaPaAdapter;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaContact$IPaPaView;
import com.one.tomato.mvp.p080ui.papa.presenter.NewPaPaPresenterMvp;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageDetailActivity;
import com.one.tomato.thirdpart.recyclerview.BaseStaggerGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.DividerItemDecoration;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.widget.scrolltextview.AutoVerticalScrollTextView;
import com.one.tomato.widget.scrolltextview.AutoVerticalScrollTextViewUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Tuples;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Maps;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment */
/* loaded from: classes3.dex */
public class NewPaPaTabFragment extends MvpBaseRecyclerViewFragment<IPaPaContact$IPaPaView, NewPaPaPresenterMvp, NewPaPaAdapter, PostList> implements IPaPaContact$IPaPaView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int adPageIndex;
    private int dataType;
    private String eventId;
    private ArrayList<PostHotMessageBean> hotMessageListData;
    private int insertPageIndex;
    private boolean isPaPaTumbe;
    private ArrayList<AdPage> listPages;
    private Disposable postPayComplete;
    private int rankType;
    private Disposable refreshBus;
    private int tagId;
    private AutoVerticalScrollTextViewUtil textViewUtil;
    private final int spanCount = 2;
    private int insertLoopNum = 10;
    private int papaTabType = 1;
    private boolean isAddAD = true;

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
        return R.layout.fragement_new_papa;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public int getPreLoadNumber() {
        return 5;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        requestData();
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
    public NewPaPaAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            return new NewPaPaAdapter(mContext, getRecyclerView());
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void setAddAD(boolean z) {
        this.isAddAD = z;
    }

    public final AutoVerticalScrollTextViewUtil getTextViewUtil() {
        return this.textViewUtil;
    }

    public final ArrayList<PostHotMessageBean> getHotMessageListData() {
        return this.hotMessageListData;
    }

    /* compiled from: NewPaPaTabFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewPaPaTabFragment initInstance(boolean z, int i, int i2) {
            Bundle bundle = new Bundle();
            NewPaPaTabFragment newPaPaTabFragment = new NewPaPaTabFragment();
            bundle.putBoolean("isPaPaTumbe", z);
            bundle.putInt("rankType", i2);
            bundle.putInt("dateType", i);
            newPaPaTabFragment.setArguments(bundle);
            return newPaPaTabFragment;
        }

        public final NewPaPaTabFragment initInstance(int i) {
            Bundle bundle = new Bundle();
            NewPaPaTabFragment newPaPaTabFragment = new NewPaPaTabFragment();
            bundle.putInt("papa_tab", i);
            newPaPaTabFragment.setArguments(bundle);
            return newPaPaTabFragment;
        }

        public final NewPaPaTabFragment initInstance(int i, int i2) {
            Bundle bundle = new Bundle();
            NewPaPaTabFragment newPaPaTabFragment = new NewPaPaTabFragment();
            bundle.putInt("papa_tab", i);
            bundle.putInt(ConstantUtils.TAB_TAG_ID, i2);
            newPaPaTabFragment.setArguments(bundle);
            return newPaPaTabFragment;
        }

        public final NewPaPaTabFragment initInstance(String eventId, int i) {
            Intrinsics.checkParameterIsNotNull(eventId, "eventId");
            Bundle bundle = new Bundle();
            NewPaPaTabFragment newPaPaTabFragment = new NewPaPaTabFragment();
            bundle.putString("event_id", eventId);
            bundle.putInt("papa_tab", i);
            newPaPaTabFragment.setArguments(bundle);
            return newPaPaTabFragment;
        }
    }

    public void requestData() {
        Map<String, Object> mutableMapOf;
        if (this.isPaPaTumbe) {
            mutableMapOf = Maps.mutableMapOf(new Tuples("memberId", Integer.valueOf(DBUtil.getMemberId())), new Tuples("rankType", Integer.valueOf(this.rankType)), new Tuples("dateType", Integer.valueOf(this.dataType)), new Tuples("pageNo", Integer.valueOf(getPageNo())), new Tuples(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize())));
            NewPaPaPresenterMvp newPaPaPresenterMvp = (NewPaPaPresenterMvp) getMPresenter();
            if (newPaPaPresenterMvp == null) {
                return;
            }
            newPaPaPresenterMvp.requestRankPaPa(mutableMapOf);
            return;
        }
        int i = this.papaTabType;
        if (i == 10) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
            linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            linkedHashMap.put(ConstantUtils.TAB_TAG_ID, Integer.valueOf(this.tagId));
            linkedHashMap.put("postType", 4);
            NewPaPaPresenterMvp newPaPaPresenterMvp2 = (NewPaPaPresenterMvp) getMPresenter();
            if (newPaPaPresenterMvp2 == null) {
                return;
            }
            newPaPaPresenterMvp2.requestTagPost(linkedHashMap);
        } else if (i == 11) {
            NewPaPaPresenterMvp newPaPaPresenterMvp3 = (NewPaPaPresenterMvp) getMPresenter();
            if (newPaPaPresenterMvp3 == null) {
                return;
            }
            newPaPaPresenterMvp3.requestHotMessagePostList(this.eventId);
        } else {
            NewPaPaPresenterMvp newPaPaPresenterMvp4 = (NewPaPaPresenterMvp) getMPresenter();
            if (newPaPaPresenterMvp4 == null) {
                return;
            }
            newPaPaPresenterMvp4.requestPostList(getPageNo(), getPageSize(), this.papaTabType);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaContact$IPaPaView
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        updateData(arrayList);
        NewPaPaAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setPageNumber(getPageNo());
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
        configStaggerLayoutManager(getRecyclerView(), this.spanCount);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getMContext(), 2, 10, -1);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.setItemViewCacheSize(20);
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        this.insertLoopNum = systemParam.getPaPaFrequencyOnList();
        RecyclerView recyclerView3 = getRecyclerView();
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(getAdapter());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
        registerRxBus();
        requestHotMessage();
    }

    private final void requestHotMessage() {
        if (this.papaTabType == 1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("eventPosition", "2");
            linkedHashMap.put("pageNo", 1);
            linkedHashMap.put(RequestParams.PAGE_SIZE, 3);
            NewPaPaPresenterMvp newPaPaPresenterMvp = (NewPaPaPresenterMvp) getMPresenter();
            if (newPaPaPresenterMvp == null) {
                return;
            }
            newPaPaPresenterMvp.requestHotMessage(linkedHashMap);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        this.listPages = DBUtil.getAdPage(C2516Ad.TYPE_PAPA);
        Bundle arguments = getArguments();
        int i = 0;
        this.isPaPaTumbe = arguments != null ? arguments.getBoolean("isPaPaTumbe") : false;
        this.rankType = arguments != null ? arguments.getInt("rankType") : 0;
        this.dataType = arguments != null ? arguments.getInt("dateType") : 0;
        this.papaTabType = arguments != null ? arguments.getInt("papa_tab") : 0;
        if (arguments != null) {
            i = arguments.getInt(ConstantUtils.TAB_TAG_ID);
        }
        this.tagId = i;
        ViewGroup.LayoutParams layoutParams = null;
        this.eventId = arguments != null ? arguments.getString("event_id", "0") : null;
        NewPaPaAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setIsPaPaTumbe(this.isPaPaTumbe);
        }
        if (this.isPaPaTumbe) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.papa_background);
            if (relativeLayout != null) {
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
            }
            SmartRefreshLayout refreshLayout = getRefreshLayout();
            if (refreshLayout != null) {
                layoutParams = refreshLayout.getLayoutParams();
            }
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
            if (layoutParams2 != null) {
                layoutParams2.leftMargin = (int) DisplayMetricsUtils.dp2px(16.0f);
            }
            if (layoutParams2 != null) {
                layoutParams2.rightMargin = (int) DisplayMetricsUtils.dp2px(16.0f);
            }
            if (this.dataType == 1) {
                setUserVisibleHint(true);
            }
        } else {
            int i2 = this.papaTabType;
            if (i2 == 1 || i2 == 11) {
                setUserVisibleHint(true);
            }
        }
        initTextHotMessage();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewPaPaPresenterMvp mo6441createPresenter() {
        return new NewPaPaPresenterMvp();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<PostList> data;
        NewPaPaAdapter adapter;
        NewPaPaAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        NewPaPaAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void updateData(ArrayList<PostList> arrayList) {
        NewPaPaAdapter adapter;
        List<PostList> data;
        super.updateData(arrayList);
        if (this.isAddAD) {
            if (getState() == getGET_LIST_REFRESH()) {
                this.insertPageIndex = 1;
            }
            PostList postList = AdUtil.getPostList(getAdPage());
            if (postList == null) {
                return;
            }
            NewPaPaAdapter adapter2 = getAdapter();
            Integer valueOf = (adapter2 == null || (data = adapter2.getData()) == null) ? null : Integer.valueOf(data.size());
            while (true) {
                int i = this.insertLoopNum;
                int i2 = this.insertPageIndex;
                int i3 = ((i * i2) + i2) - 1;
                if (valueOf != null && i3 >= valueOf.intValue() + 1) {
                    return;
                }
                if (postList != null && (adapter = getAdapter()) != null) {
                    adapter.addData(i3, (int) postList);
                }
                this.insertPageIndex++;
            }
        }
    }

    private final AdPage getAdPage() {
        ArrayList<AdPage> arrayList = this.listPages;
        AdPage adPage = null;
        if (arrayList != null) {
            if (arrayList != null && arrayList.isEmpty()) {
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
        return null;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        NewPaPaAdapter adapter;
        super.onActivityResult(i, i2, intent);
        if (i == 30) {
            final Integer num = null;
            Bundle extras = intent != null ? intent.getExtras() : null;
            Integer valueOf = extras != null ? Integer.valueOf(extras.getInt("page_no")) : null;
            ArrayList parcelableArrayList = extras != null ? extras.getParcelableArrayList("postList") : null;
            final Integer valueOf2 = extras != null ? Integer.valueOf(extras.getInt("start_position")) : null;
            if (extras != null) {
                num = Integer.valueOf(extras.getInt("cur_position"));
            }
            if (valueOf != null) {
                valueOf.intValue();
                setPageNo(valueOf.intValue());
            }
            if (parcelableArrayList != null && (!parcelableArrayList.isEmpty()) && (adapter = getAdapter()) != null) {
                adapter.addData((Collection) parcelableArrayList);
            }
            if (valueOf2 == null) {
                return;
            }
            valueOf2.intValue();
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView == null) {
                return;
            }
            recyclerView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$onActivityResult$$inlined$let$lambda$1
                @Override // java.lang.Runnable
                public void run() {
                    RecyclerView recyclerView2;
                    recyclerView2 = NewPaPaTabFragment.this.getRecyclerView();
                    if (recyclerView2 != null) {
                        int intValue = valueOf2.intValue();
                        Integer num2 = num;
                        if (num2 != null) {
                            recyclerView2.smoothScrollToPosition(intValue + num2.intValue());
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unRegisterRxBus();
    }

    private final void registerRxBus() {
        this.refreshBus = RxBus.getDefault().toObservable(VideoTabStatusChange.class).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<VideoTabStatusChange>() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$registerRxBus$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(VideoTabStatusChange videoTabStatusChange) {
                RecyclerView recyclerView;
                SmartRefreshLayout refreshLayout;
                if (!videoTabStatusChange.refresh || !NewPaPaTabFragment.this.isVisible()) {
                    return;
                }
                recyclerView = NewPaPaTabFragment.this.getRecyclerView();
                if (recyclerView != null) {
                    recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$registerRxBus$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecyclerView recyclerView2;
                            recyclerView2 = NewPaPaTabFragment.this.getRecyclerView();
                            BaseStaggerGridLayoutManager baseStaggerGridLayoutManager = (BaseStaggerGridLayoutManager) (recyclerView2 != null ? recyclerView2.getLayoutManager() : null);
                            if (baseStaggerGridLayoutManager != null) {
                                baseStaggerGridLayoutManager.scrollToPositionWithOffset(0, 0);
                            } else {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                        }
                    }, 50L);
                }
                refreshLayout = NewPaPaTabFragment.this.getRefreshLayout();
                if (refreshLayout != null) {
                    refreshLayout.autoRefresh();
                }
                LogUtil.m3787d("yana", "調用怕怕推薦刷新");
            }
        });
        this.postPayComplete = RxBus.getDefault().toObservable(PostPayEvent.class).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PostPayEvent>() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$registerRxBus$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(PostPayEvent postPayEvent) {
                NewPaPaAdapter adapter;
                int i;
                NewPaPaAdapter adapter2;
                if (postPayEvent instanceof PostPayEvent) {
                    postPayEvent.isPay();
                    int artcId = postPayEvent.getArtcId();
                    adapter = NewPaPaTabFragment.this.getAdapter();
                    PostList postList = null;
                    List<PostList> data = adapter != null ? adapter.getData() : null;
                    if (data != null) {
                        i = -1;
                        int i2 = 0;
                        for (T t : data) {
                            int i3 = i2 + 1;
                            if (i2 >= 0) {
                                PostList postList2 = (PostList) t;
                                Intrinsics.checkExpressionValueIsNotNull(postList2, "postList");
                                if (artcId == postList2.getId()) {
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
                    if (data != null) {
                        postList = data.get(i);
                    }
                    if (postList != null) {
                        postList.setAlreadyPaid(true);
                    }
                    adapter2 = NewPaPaTabFragment.this.getAdapter();
                    if (adapter2 == null) {
                        return;
                    }
                    adapter2.notifyItemChanged(i);
                }
            }
        });
        RxSubscriptions.add(this.refreshBus);
        RxSubscriptions.add(this.postPayComplete);
    }

    private final void unRegisterRxBus() {
        RxSubscriptions.remove(this.refreshBus);
        RxSubscriptions.remove(this.postPayComplete);
    }

    private final void initTextHotMessage() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_close);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$initTextHotMessage$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AutoVerticalScrollTextViewUtil textViewUtil = NewPaPaTabFragment.this.getTextViewUtil();
                    if (textViewUtil != null) {
                        textViewUtil.stop();
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) NewPaPaTabFragment.this._$_findCachedViewById(R$id.relate_hot);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(8);
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaContact$IPaPaView
    public void handlerHotMessageData(ArrayList<PostHotMessageBean> arrayList) {
        if (!(arrayList == null || arrayList.isEmpty())) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_hot);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            this.hotMessageListData = arrayList;
            ArrayList arrayList2 = new ArrayList();
            Iterator<PostHotMessageBean> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                PostHotMessageBean it3 = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                arrayList2.add(it3.getEventDesc());
            }
            this.textViewUtil = new AutoVerticalScrollTextViewUtil((AutoVerticalScrollTextView) _$_findCachedViewById(R$id.text_hot_name), arrayList2);
            AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil = this.textViewUtil;
            if (autoVerticalScrollTextViewUtil != null) {
                autoVerticalScrollTextViewUtil.setDuration(3000L);
            }
            AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil2 = this.textViewUtil;
            if (autoVerticalScrollTextViewUtil2 != null) {
                autoVerticalScrollTextViewUtil2.start();
            }
            AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil3 = this.textViewUtil;
            if (autoVerticalScrollTextViewUtil3 == null) {
                return;
            }
            autoVerticalScrollTextViewUtil3.setOnMyClickListener(new AutoVerticalScrollTextViewUtil.OnMyClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaTabFragment$handlerHotMessageData$1
                @Override // com.one.tomato.widget.scrolltextview.AutoVerticalScrollTextViewUtil.OnMyClickListener
                public final void onMyClickListener(int i, CharSequence charSequence) {
                    Context mContext;
                    try {
                        ArrayList<PostHotMessageBean> hotMessageListData = NewPaPaTabFragment.this.getHotMessageListData();
                        PostHotMessageBean postHotMessageBean = hotMessageListData != null ? hotMessageListData.get(i) : null;
                        PostHotMessageDetailActivity.Companion companion = PostHotMessageDetailActivity.Companion;
                        mContext = NewPaPaTabFragment.this.getMContext();
                        if (mContext != null) {
                            companion.startAct(mContext, postHotMessageBean);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    } catch (Exception unused) {
                    }
                }
            });
        }
    }
}
