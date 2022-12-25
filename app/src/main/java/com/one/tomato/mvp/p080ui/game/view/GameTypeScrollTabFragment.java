package com.one.tomato.mvp.p080ui.game.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.view.ChessGameActivity;
import com.one.tomato.mvp.p080ui.game.adapter.GameTypeScrollAdapter;
import com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView;
import com.one.tomato.mvp.p080ui.game.presenter.GamePresenter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.widget.MarqueeTextView;
import com.one.tomato.widget.mzbanner.MZBannerView;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* compiled from: GameTypeScrollTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment */
/* loaded from: classes3.dex */
public final class GameTypeScrollTabFragment extends MvpBaseFragment<IGameContact$IGameView, GamePresenter> implements IGameContact$IGameView {
    private HashMap _$_findViewCache;
    private AdUtil adUtil;
    private MZBannerView<AdPage> banner;
    private int curPosition;
    private GameTypeScrollAdapter gameAllAdapter;
    private BaseRecyclerViewAdapter<GameTypeData> gameTypeAdapter;
    private boolean isClick;
    private final ArrayList<String> gameRegisterUserBean = new ArrayList<>();
    private final ArrayList<GameTypeData> gameList = new ArrayList<>();

    static {
        new Companion(null);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
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
        return R.layout.game_type_scroll_tab_fragment;
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handleList(ArrayList<AdPage> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public GamePresenter mo6441createPresenter() {
        return new GamePresenter();
    }

    /* compiled from: GameTypeScrollTabFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        initGameTypeAdapter();
        addScroll();
        initBanner();
    }

    public final void initBanner() {
        View view = getView();
        this.banner = view != null ? (MZBannerView) view.findViewById(R.id.mzbanner) : null;
        this.adUtil = new AdUtil((Activity) getContext());
        final ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_GAME_CENTER_BANNER);
        if (adPage == null || adPage.isEmpty()) {
            adPage.add(new AdPage());
        }
        if (!(adPage instanceof ArrayList) || adPage.isEmpty()) {
            return;
        }
        MZBannerView<AdPage> mZBannerView = this.banner;
        if (mZBannerView != null) {
            mZBannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$initBanner$1
                @Override // com.one.tomato.widget.mzbanner.MZBannerView.BannerPageClickListener
                public final void onPageClick(View view2, int i) {
                    AdUtil adUtil;
                    Object obj = adPage.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "adPage[position]");
                    AdPage adPage2 = (AdPage) obj;
                    adUtil = GameTypeScrollTabFragment.this.adUtil;
                    if (adUtil != null) {
                        adUtil.clickAd(adPage2.getAdId(), adPage2.getAdType(), adPage2.getAdJumpModule(), adPage2.getAdJumpDetail(), adPage2.getOpenType(), adPage2.getAdLink());
                    }
                }
            });
        }
        MZBannerView<AdPage> mZBannerView2 = this.banner;
        if (mZBannerView2 == null) {
            return;
        }
        mZBannerView2.setPages(adPage, GameTypeScrollTabFragment$initBanner$2.INSTANCE);
    }

    private final void initGameTypeAdapter() {
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        this.gameTypeAdapter = new BaseRecyclerViewAdapter<GameTypeData>(mContext, R.layout.game_type_item, recyclerView) { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$initGameTypeAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, GameTypeData gameTypeData) {
                int i;
                String str;
                TextView textView = null;
                RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.background_view) : null;
                if (baseViewHolder != null) {
                    textView = (TextView) baseViewHolder.getView(R.id.text_name);
                }
                if (textView != null) {
                    if (gameTypeData == null || (str = gameTypeData.getAdName()) == null) {
                        str = "";
                    }
                    textView.setText(str);
                }
                i = GameTypeScrollTabFragment.this.curPosition;
                if (i == getData().indexOf(gameTypeData)) {
                    if (relativeLayout != null) {
                        relativeLayout.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.white));
                    }
                    if (textView == null) {
                        return;
                    }
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
                    return;
                }
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.app_bg_grey));
                }
                if (textView == null) {
                    return;
                }
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_646464));
            }
        };
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.gameTypeAdapter);
        }
        this.gameAllAdapter = new GameTypeScrollAdapter();
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.all_recycler);
        if (recyclerView4 != null) {
            recyclerView4.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.all_recycler);
        if (recyclerView5 != null) {
            recyclerView5.setAdapter(this.gameAllAdapter);
        }
        GameTypeScrollAdapter gameTypeScrollAdapter = this.gameAllAdapter;
        if (gameTypeScrollAdapter != null) {
            gameTypeScrollAdapter.addCallDeleteBackItem(new GameTypeScrollTabFragment$initGameTypeAdapter$2(this));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        setUserVisibleHint(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestList() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        hashMap.put("bgFlag", 1);
        GamePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestGameTypeList(hashMap);
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.ll_content);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerGameType(ArrayList<GameTypeData> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        ArrayList arrayList = new ArrayList();
        if (!list.isEmpty()) {
            arrayList.addAll(list);
            this.gameList.addAll(list);
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.ll_content);
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            ArrayList<SubGamesBean> gameBean = GameUtils.INSTANCE.getGameBean();
            if (gameBean.size() > 0) {
                GameTypeData gameTypeData = new GameTypeData();
                gameTypeData.setGameId(-10);
                gameTypeData.setAdName("最近玩过");
                gameTypeData.setAdType("3");
                gameTypeData.setAdLogoType("1");
                gameTypeData.setSubGames(gameBean);
                arrayList.add(0, gameTypeData);
            }
            BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter = this.gameTypeAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.setNewData(arrayList);
            }
            BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter2 = this.gameTypeAdapter;
            if (baseRecyclerViewAdapter2 != null) {
                baseRecyclerViewAdapter2.setEnableLoadMore(false);
            }
            GameTypeScrollAdapter gameTypeScrollAdapter = this.gameAllAdapter;
            if (gameTypeScrollAdapter != null) {
                gameTypeScrollAdapter.setNewData(arrayList);
            }
            GameTypeScrollAdapter gameTypeScrollAdapter2 = this.gameAllAdapter;
            if (gameTypeScrollAdapter2 != null) {
                gameTypeScrollAdapter2.setEnableLoadMore(false);
            }
            initNotice();
            return;
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout2 != null) {
            relativeLayout2.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.ll_content);
        if (linearLayout2 == null) {
            return;
        }
        linearLayout2.setVisibility(8);
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerBGLogin(GameBGLoginBean gameBGLoginBean, SubGamesBean subGamesBean) {
        String str;
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        GameUtils gameUtils = GameUtils.INSTANCE;
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (gameBGLoginBean == null || (str = gameBGLoginBean.getUrl()) == null) {
            str = "";
        }
        gameUtils.clickGame(context, subGamesBean, str);
    }

    private final void addScroll() {
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$addScroll$1
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                    GamePresenter mPresenter;
                    GamePresenter mPresenter2;
                    GamePresenter mPresenter3;
                    GamePresenter mPresenter4;
                    Intrinsics.checkParameterIsNotNull(recyclerView2, "recyclerView");
                    super.onScrolled(recyclerView2, i, i2);
                    mPresenter = GameTypeScrollTabFragment.this.getMPresenter();
                    if (mPresenter == null || !mPresenter.getMShouldScroll()) {
                        return;
                    }
                    mPresenter2 = GameTypeScrollTabFragment.this.getMPresenter();
                    Integer valueOf = mPresenter2 != null ? Integer.valueOf(mPresenter2.getIndex()) : null;
                    if (valueOf == null) {
                        return;
                    }
                    mPresenter3 = GameTypeScrollTabFragment.this.getMPresenter();
                    if (mPresenter3 != null) {
                        mPresenter3.setMShouldScroll(false);
                    }
                    mPresenter4 = GameTypeScrollTabFragment.this.getMPresenter();
                    if (mPresenter4 == null) {
                        return;
                    }
                    mPresenter4.smoothMoveToPosition(recyclerView2, valueOf.intValue());
                }
            });
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.all_recycler);
        if (recyclerView2 != null) {
            recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$addScroll$2
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView3, int i, int i2) {
                    GamePresenter mPresenter;
                    boolean z;
                    GameTypeScrollAdapter gameTypeScrollAdapter;
                    GamePresenter mPresenter2;
                    GamePresenter mPresenter3;
                    GamePresenter mPresenter4;
                    Intrinsics.checkParameterIsNotNull(recyclerView3, "recyclerView");
                    super.onScrolled(recyclerView3, i, i2);
                    mPresenter = GameTypeScrollTabFragment.this.getMPresenter();
                    GameTypeData gameTypeData = null;
                    boolean z2 = true;
                    int i3 = 0;
                    if (mPresenter != null && mPresenter.getMShouldScroll()) {
                        mPresenter2 = GameTypeScrollTabFragment.this.getMPresenter();
                        Integer valueOf = mPresenter2 != null ? Integer.valueOf(mPresenter2.getIndex()) : null;
                        if (valueOf != null) {
                            mPresenter3 = GameTypeScrollTabFragment.this.getMPresenter();
                            if (mPresenter3 != null) {
                                mPresenter3.setMShouldScroll(false);
                            }
                            mPresenter4 = GameTypeScrollTabFragment.this.getMPresenter();
                            if (mPresenter4 != null) {
                                mPresenter4.smoothMoveToPosition(recyclerView3, valueOf.intValue());
                            }
                        }
                    }
                    z = GameTypeScrollTabFragment.this.isClick;
                    if (z) {
                        return;
                    }
                    RecyclerView.LayoutManager layoutManager = recyclerView3.getLayoutManager();
                    if (layoutManager == null) {
                        throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
                    }
                    int findFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    gameTypeScrollAdapter = GameTypeScrollTabFragment.this.gameAllAdapter;
                    if (gameTypeScrollAdapter != null) {
                        gameTypeData = gameTypeScrollAdapter.getItem(findFirstVisibleItemPosition);
                    }
                    GameTypeScrollTabFragment gameTypeScrollTabFragment = GameTypeScrollTabFragment.this;
                    if (gameTypeData == null || gameTypeData.getGameId() != -10) {
                        z2 = false;
                    }
                    if (gameTypeData != null) {
                        i3 = gameTypeData.getGameId();
                    }
                    gameTypeScrollTabFragment.scorllCircleType(z2, i3);
                }

                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView3, int i) {
                    boolean z;
                    Intrinsics.checkParameterIsNotNull(recyclerView3, "recyclerView");
                    super.onScrollStateChanged(recyclerView3, i);
                    if (i == 0) {
                        z = GameTypeScrollTabFragment.this.isClick;
                        if (!z) {
                            return;
                        }
                        GameTypeScrollTabFragment.this.isClick = false;
                    }
                }
            });
        }
        BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter = this.gameTypeAdapter;
        if (baseRecyclerViewAdapter != null) {
            baseRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$addScroll$3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    BaseRecyclerViewAdapter baseRecyclerViewAdapter2;
                    BaseRecyclerViewAdapter baseRecyclerViewAdapter3;
                    GameTypeData gameTypeData;
                    GameTypeScrollTabFragment.this.isClick = true;
                    GameTypeScrollTabFragment.this.curPosition = i;
                    baseRecyclerViewAdapter2 = GameTypeScrollTabFragment.this.gameTypeAdapter;
                    if (baseRecyclerViewAdapter2 != null) {
                        baseRecyclerViewAdapter2.notifyDataSetChanged();
                    }
                    GameTypeScrollTabFragment gameTypeScrollTabFragment = GameTypeScrollTabFragment.this;
                    baseRecyclerViewAdapter3 = gameTypeScrollTabFragment.gameTypeAdapter;
                    gameTypeScrollTabFragment.scorllCircleAll((baseRecyclerViewAdapter3 == null || (gameTypeData = (GameTypeData) baseRecyclerViewAdapter3.getItem(i)) == null) ? null : Integer.valueOf(gameTypeData.getGameId()));
                }
            });
        }
        GameTypeScrollAdapter gameTypeScrollAdapter = this.gameAllAdapter;
        if (gameTypeScrollAdapter != null) {
            gameTypeScrollAdapter.addCallBackItem(new GameTypeScrollTabFragment$addScroll$4(this));
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$addScroll$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GameTypeScrollTabFragment.this.requestList();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestBGH5Login(SubGamesBean subGamesBean) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("adId", Integer.valueOf(subGamesBean.getGameId()));
        linkedHashMap.put("gameBrandId", subGamesBean.getAdBrandId());
        GamePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestBGH5Login(linkedHashMap, subGamesBean);
        }
    }

    public final void scorllCircleAll(Integer num) {
        if (num == null) {
            return;
        }
        GameTypeScrollAdapter gameTypeScrollAdapter = this.gameAllAdapter;
        List<GameTypeData> data = gameTypeScrollAdapter != null ? gameTypeScrollAdapter.getData() : null;
        if (data == null) {
            return;
        }
        int i = 0;
        for (Object obj : data) {
            int i2 = i + 1;
            if (i >= 0) {
                GameTypeData circleAllBean = (GameTypeData) obj;
                if (num != null && num.intValue() == -10) {
                    GamePresenter mPresenter = getMPresenter();
                    if (mPresenter == null) {
                        return;
                    }
                    RecyclerView all_recycler = (RecyclerView) _$_findCachedViewById(R$id.all_recycler);
                    Intrinsics.checkExpressionValueIsNotNull(all_recycler, "all_recycler");
                    mPresenter.smoothMoveToPosition(all_recycler, i);
                    return;
                }
                Intrinsics.checkExpressionValueIsNotNull(circleAllBean, "circleAllBean");
                int gameId = circleAllBean.getGameId();
                if (num != null && num.intValue() == gameId) {
                    GamePresenter mPresenter2 = getMPresenter();
                    if (mPresenter2 == null) {
                        return;
                    }
                    RecyclerView all_recycler2 = (RecyclerView) _$_findCachedViewById(R$id.all_recycler);
                    Intrinsics.checkExpressionValueIsNotNull(all_recycler2, "all_recycler");
                    mPresenter2.smoothMoveToPosition(all_recycler2, i);
                    return;
                }
                i = i2;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    public final void scorllCircleType(boolean z, int i) {
        int i2 = 0;
        if (z) {
            this.curPosition = 0;
            BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter = this.gameTypeAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.notifyDataSetChanged();
            }
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
            if (recyclerView == null) {
                return;
            }
            recyclerView.smoothScrollToPosition(0);
            return;
        }
        BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter2 = this.gameTypeAdapter;
        List<GameTypeData> data = baseRecyclerViewAdapter2 != null ? baseRecyclerViewAdapter2.getData() : null;
        if (data == null) {
            return;
        }
        for (Object obj : data) {
            int i3 = i2 + 1;
            if (i2 >= 0) {
                GameTypeData circleCategory = (GameTypeData) obj;
                Intrinsics.checkExpressionValueIsNotNull(circleCategory, "circleCategory");
                if (circleCategory.getGameId() == i) {
                    this.curPosition = i2;
                    BaseRecyclerViewAdapter<GameTypeData> baseRecyclerViewAdapter3 = this.gameTypeAdapter;
                    if (baseRecyclerViewAdapter3 != null) {
                        baseRecyclerViewAdapter3.notifyDataSetChanged();
                    }
                    RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
                    if (recyclerView2 == null) {
                        return;
                    }
                    recyclerView2.smoothScrollToPosition(this.curPosition);
                    return;
                }
                i2 = i3;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    private final void initNotice() {
        final GameRegisterUserBean gameRegisterBean = DBUtil.getGameRegisterBean();
        if (gameRegisterBean != null) {
            ArrayList<String> memberNames = gameRegisterBean.getMemberNames();
            if (!(memberNames == null || memberNames.isEmpty())) {
                this.gameRegisterUserBean.addAll(memberNames);
                startScrollNotice();
            }
        }
        ApiImplService.Companion.getApiImplService().queryGameUser().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameRegisterUserBean>() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$initNotice$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameRegisterUserBean gameRegisterUserBean) {
                ArrayList arrayList;
                if (gameRegisterUserBean != null) {
                    ArrayList<String> memberNames2 = gameRegisterUserBean.getMemberNames();
                    boolean z = false;
                    if (memberNames2 == null || memberNames2.isEmpty()) {
                        return;
                    }
                    arrayList = GameTypeScrollTabFragment.this.gameRegisterUserBean;
                    arrayList.addAll(memberNames2);
                    GameRegisterUserBean gameRegisterUserBean2 = gameRegisterBean;
                    ArrayList<String> memberNames3 = gameRegisterUserBean2 != null ? gameRegisterUserBean2.getMemberNames() : null;
                    if (memberNames3 == null || memberNames3.isEmpty()) {
                        z = true;
                    }
                    if (z) {
                        GameTypeScrollTabFragment.this.startScrollNotice();
                    }
                    DBUtil.saveGameRegisterBean(gameRegisterUserBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3788d(":");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startScrollNotice() {
        ArrayList<String> arrayList = this.gameRegisterUserBean;
        boolean z = false;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        ArrayList<GameTypeData> arrayList2 = this.gameList;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            z = true;
        }
        if (z) {
            return;
        }
        Observable.interval(0L, 20L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$startScrollNotice$subscribe$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Long l) {
                ArrayList arrayList3;
                ArrayList arrayList4;
                ArrayList arrayList5;
                ArrayList arrayList6;
                Random.Default r7 = Random.Default;
                arrayList3 = GameTypeScrollTabFragment.this.gameRegisterUserBean;
                int i = 0;
                int nextInt = r7.nextInt(0, arrayList3.size());
                arrayList4 = GameTypeScrollTabFragment.this.gameRegisterUserBean;
                Object obj = arrayList4.get(nextInt);
                Intrinsics.checkExpressionValueIsNotNull(obj, "gameRegisterUserBean[nextInt]");
                String string = AppUtil.getString(R.string.game_notice_text1);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.game_notice_text1)");
                boolean z2 = true;
                Object[] objArr = {(String) obj};
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                MarqueeTextView marqueeTextView = (MarqueeTextView) GameTypeScrollTabFragment.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView != null) {
                    marqueeTextView.setVisibility(0);
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.append((CharSequence) format);
                Random.Default r72 = Random.Default;
                arrayList5 = GameTypeScrollTabFragment.this.gameList;
                int nextInt2 = r72.nextInt(0, arrayList5.size());
                arrayList6 = GameTypeScrollTabFragment.this.gameList;
                Object obj2 = arrayList6.get(nextInt2);
                Intrinsics.checkExpressionValueIsNotNull(obj2, "gameList[gameNameIndex]");
                final GameTypeData gameTypeData = (GameTypeData) obj2;
                ArrayList<SubGamesBean> subGames = gameTypeData.getSubGames();
                if (subGames != null && !subGames.isEmpty()) {
                    z2 = false;
                }
                if (!z2) {
                    final SubGamesBean subGamesBean = gameTypeData.getSubGames().get(Random.Default.nextInt(0, gameTypeData.getSubGames().size()));
                    MarqueeTextView marqueeTextView2 = (MarqueeTextView) GameTypeScrollTabFragment.this._$_findCachedViewById(R$id.tv_notice);
                    if (marqueeTextView2 != null) {
                        marqueeTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$startScrollNotice$subscribe$1.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                Context mContext;
                                ArrayList arrayList7;
                                Context mContext2;
                                SubGamesBean subGamesBean2 = subGamesBean;
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean2, "subGamesBean");
                                if (Intrinsics.areEqual(subGamesBean2.getAdType(), "7")) {
                                    ArrayList<SubGamesBean> arrayList8 = new ArrayList<>();
                                    arrayList7 = GameTypeScrollTabFragment.this.gameList;
                                    Iterator it2 = arrayList7.iterator();
                                    while (it2.hasNext()) {
                                        GameTypeData its = (GameTypeData) it2.next();
                                        Intrinsics.checkExpressionValueIsNotNull(its, "its");
                                        Iterator<SubGamesBean> it3 = its.getSubGames().iterator();
                                        while (it3.hasNext()) {
                                            SubGamesBean sub = it3.next();
                                            Intrinsics.checkExpressionValueIsNotNull(sub, "sub");
                                            sub.setSelector(false);
                                            if (Intrinsics.areEqual(sub.getAdType(), "7")) {
                                                if (Intrinsics.areEqual(sub.getAdGameId(), gameTypeData.getAdGameId())) {
                                                    sub.setSelector(true);
                                                }
                                                arrayList8.add(sub);
                                            }
                                        }
                                    }
                                    ChessGameActivity.Companion companion = ChessGameActivity.Companion;
                                    mContext2 = GameTypeScrollTabFragment.this.getMContext();
                                    if (mContext2 != null) {
                                        companion.startAct(mContext2, arrayList8);
                                        return;
                                    } else {
                                        Intrinsics.throwNpe();
                                        throw null;
                                    }
                                }
                                SubGamesBean subGamesBean3 = subGamesBean;
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean3, "subGamesBean");
                                if (Intrinsics.areEqual(subGamesBean3.getAdType(), "9")) {
                                    GameTypeScrollTabFragment gameTypeScrollTabFragment = GameTypeScrollTabFragment.this;
                                    SubGamesBean subGamesBean4 = subGamesBean;
                                    Intrinsics.checkExpressionValueIsNotNull(subGamesBean4, "subGamesBean");
                                    gameTypeScrollTabFragment.requestBGH5Login(subGamesBean4);
                                    return;
                                }
                                GameUtils gameUtils = GameUtils.INSTANCE;
                                mContext = GameTypeScrollTabFragment.this.getMContext();
                                if (mContext == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                                SubGamesBean subGamesBean5 = subGamesBean;
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean5, "subGamesBean");
                                gameUtils.clickGame(mContext, subGamesBean5);
                            }
                        });
                    }
                    spannableStringBuilder.append((CharSequence) gameTypeData.getAdName());
                    Intrinsics.checkExpressionValueIsNotNull(subGamesBean, "subGamesBean");
                    SpannableString spannableString = new SpannableString(subGamesBean.getAdName());
                    spannableString.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$startScrollNotice$subscribe$1$titleClickSpan$1
                        @Override // android.text.style.ClickableSpan
                        public void onClick(View widget) {
                            Intrinsics.checkParameterIsNotNull(widget, "widget");
                        }

                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(TextPaint ds) {
                            Intrinsics.checkParameterIsNotNull(ds, "ds");
                            super.updateDrawState(ds);
                            ds.setUnderlineText(true);
                            ds.setColor(Color.parseColor("#FC4C7B"));
                        }
                    }, 0, spannableString.length(), 33);
                    spannableStringBuilder.append((CharSequence) spannableString);
                    spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.game_notice_text2));
                    spannableStringBuilder.append((CharSequence) String.valueOf(FormatUtil.formatTwo(Double.valueOf(Random.Default.nextDouble(950.0d, 15000.0d)))));
                    spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.common_renmingbi));
                    Paint paint = new Paint();
                    int measureText = (int) ((paint.measureText(spannableStringBuilder.toString()) / 3) / paint.measureText(ConstantUtils.PLACEHOLDER_STR_ONE));
                    if (measureText >= 0) {
                        while (true) {
                            spannableStringBuilder.append((CharSequence) ConstantUtils.PLACEHOLDER_STR_ONE);
                            if (i == measureText) {
                                break;
                            }
                            i++;
                        }
                    }
                    MarqueeTextView marqueeTextView3 = (MarqueeTextView) GameTypeScrollTabFragment.this._$_findCachedViewById(R$id.tv_notice);
                    if (marqueeTextView3 == null) {
                        return;
                    }
                    marqueeTextView3.setText(spannableStringBuilder);
                }
            }
        });
    }
}
