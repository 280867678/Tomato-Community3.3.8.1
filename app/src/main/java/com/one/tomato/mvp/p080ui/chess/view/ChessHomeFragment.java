package com.one.tomato.mvp.p080ui.chess.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.adapter.ChessHomeTypeAdapter;
import com.one.tomato.mvp.p080ui.chess.adapter.ChessHomeTypeSubAdapter;
import com.one.tomato.mvp.p080ui.chess.view.ChessGameActivity;
import com.one.tomato.mvp.p080ui.circle.utils.CircleAllUtils;
import com.one.tomato.mvp.p080ui.game.view.GameSpreadActivity;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.p085ui.recharge.RechargeExGameActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.widget.MarqueeTextView;
import com.one.tomato.widget.mzbanner.MZBannerView;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* compiled from: ChessHomeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment */
/* loaded from: classes3.dex */
public final class ChessHomeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private AdUtil adUtil;
    private MZBannerView<AdPage> banner;
    private int curPosition;
    private boolean isClick;
    private boolean mShouldScroll;
    private ChessHomeTypeAdapter typeAdapter;
    private ChessHomeTypeSubAdapter typeSubAllAdapter;
    private int index = -1;
    private final ArrayList<String> gameRegisterUserBean = new ArrayList<>();

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
        return R.layout.fragment_chess_home;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_refresh);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        ImageLoaderUtil.loadNormalDrawableGif(getContext(), (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main), R.drawable.chess_home_refresh_empty_load_gif);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.type_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        this.typeAdapter = new ChessHomeTypeAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.type_recycler));
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.type_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.typeAdapter);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
        if (recyclerView4 != null) {
            recyclerView4.setItemViewCacheSize(15);
        }
        RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
        if (recyclerView5 != null) {
            recyclerView5.setHasFixedSize(true);
        }
        this.typeSubAllAdapter = new ChessHomeTypeSubAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler));
        RecyclerView recyclerView6 = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
        if (recyclerView6 != null) {
            recyclerView6.setAdapter(this.typeSubAllAdapter);
        }
        initOnClick();
        initBanner();
        initNotice();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        ArrayList<ChessTypeBean> chessHomeData = CircleAllUtils.INSTANCE.getChessHomeData();
        if (!(chessHomeData == null || chessHomeData.isEmpty())) {
            cancleLoading();
            showContent(chessHomeData);
            return;
        }
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestData() {
        ApiImplService.Companion.getApiImplService().requestChessHomeData().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<ChessTypeBean>>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<ChessTypeBean> arrayList) {
                ChessHomeFragment.this.cancleLoading();
                if (!(arrayList == null || arrayList.isEmpty())) {
                    ChessHomeFragment.this.showContent(arrayList);
                    CircleAllUtils.INSTANCE.saveChessHomeData(arrayList);
                    return;
                }
                RelativeLayout relativeLayout = (RelativeLayout) ChessHomeFragment.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout == null) {
                    return;
                }
                relativeLayout.setVisibility(0);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("请求娱乐首页数据 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3783i("yan", sb.toString());
                ChessHomeFragment.this.cancleLoading();
                RelativeLayout relativeLayout = (RelativeLayout) ChessHomeFragment.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showContent(ArrayList<ChessTypeBean> arrayList) {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_content);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        ChessTypeBean chessTypeBean = arrayList.get(0);
        Intrinsics.checkExpressionValueIsNotNull(chessTypeBean, "t[0]");
        chessTypeBean.setSelect(true);
        ChessHomeTypeAdapter chessHomeTypeAdapter = this.typeAdapter;
        if (chessHomeTypeAdapter != null) {
            chessHomeTypeAdapter.setNewData(arrayList);
        }
        ChessHomeTypeAdapter chessHomeTypeAdapter2 = this.typeAdapter;
        if (chessHomeTypeAdapter2 != null) {
            chessHomeTypeAdapter2.setEnableLoadMore(false);
        }
        ChessHomeTypeSubAdapter chessHomeTypeSubAdapter = this.typeSubAllAdapter;
        if (chessHomeTypeSubAdapter != null) {
            chessHomeTypeSubAdapter.setNewData(arrayList);
        }
        ChessHomeTypeSubAdapter chessHomeTypeSubAdapter2 = this.typeSubAllAdapter;
        if (chessHomeTypeSubAdapter2 != null) {
            chessHomeTypeSubAdapter2.setEnableLoadMore(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancleLoading() {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_refresh);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.refresh_lottie_main);
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
    }

    private final void initOnClick() {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_recharge);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = ChessHomeFragment.this.getMContext();
                    if (mContext != null) {
                        ((ChessMainTabActivity) mContext).selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_RECHARGE());
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity");
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_tx);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    IncomeActivity.Companion companion = IncomeActivity.Companion;
                    mContext = ChessHomeFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext, 4);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_transfer);
        if (relativeLayout3 != null) {
            relativeLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    RechargeExGameActivity.Companion companion = RechargeExGameActivity.Companion;
                    mContext = ChessHomeFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        RelativeLayout relativeLayout4 = (RelativeLayout) _$_findCachedViewById(R$id.relate_spread);
        if (relativeLayout4 != null) {
            relativeLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    GameSpreadActivity.Companion companion = GameSpreadActivity.Companion;
                    mContext = ChessHomeFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        RelativeLayout relativeLayout5 = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout5 != null) {
            relativeLayout5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RelativeLayout relativeLayout6 = (RelativeLayout) ChessHomeFragment.this._$_findCachedViewById(R$id.relate_refresh);
                    if (relativeLayout6 != null) {
                        relativeLayout6.setVisibility(0);
                    }
                    ImageLoaderUtil.loadNormalDrawableGif(ChessHomeFragment.this.getContext(), (ImageView) ChessHomeFragment.this._$_findCachedViewById(R$id.refresh_lottie_main), R.drawable.chess_home_refresh_empty_load_gif);
                    RelativeLayout relativeLayout7 = (RelativeLayout) ChessHomeFragment.this._$_findCachedViewById(R$id.relate_no_data);
                    if (relativeLayout7 != null) {
                        relativeLayout7.setVisibility(8);
                    }
                    ChessHomeFragment.this.requestData();
                }
            });
        }
        ChessHomeTypeAdapter chessHomeTypeAdapter = this.typeAdapter;
        if (chessHomeTypeAdapter != null) {
            chessHomeTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$6
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    int i2;
                    ChessHomeTypeAdapter chessHomeTypeAdapter2;
                    ChessHomeTypeAdapter chessHomeTypeAdapter3;
                    ChessTypeBean item;
                    ChessHomeTypeAdapter chessHomeTypeAdapter4;
                    ChessHomeTypeAdapter chessHomeTypeAdapter5;
                    i2 = ChessHomeFragment.this.curPosition;
                    if (i2 == i) {
                        return;
                    }
                    ChessHomeFragment.this.isClick = true;
                    ChessHomeFragment.this.curPosition = i;
                    chessHomeTypeAdapter2 = ChessHomeFragment.this.typeAdapter;
                    Integer num = null;
                    List<ChessTypeBean> data = chessHomeTypeAdapter2 != null ? chessHomeTypeAdapter2.getData() : null;
                    if (data != null) {
                        int size = data.size();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= size) {
                                break;
                            }
                            ChessTypeBean chessTypeBean = data.get(i3);
                            Intrinsics.checkExpressionValueIsNotNull(chessTypeBean, "data[its]");
                            if (chessTypeBean.isSelect()) {
                                ChessTypeBean chessTypeBean2 = data.get(i3);
                                Intrinsics.checkExpressionValueIsNotNull(chessTypeBean2, "data[its]");
                                chessTypeBean2.setSelect(false);
                                chessHomeTypeAdapter5 = ChessHomeFragment.this.typeAdapter;
                                if (chessHomeTypeAdapter5 != null) {
                                    chessHomeTypeAdapter5.notifyItemChanged(i3);
                                }
                            } else {
                                i3++;
                            }
                        }
                        ChessTypeBean chessTypeBean3 = data.get(i);
                        Intrinsics.checkExpressionValueIsNotNull(chessTypeBean3, "data[position]");
                        chessTypeBean3.setSelect(true);
                        chessHomeTypeAdapter4 = ChessHomeFragment.this.typeAdapter;
                        if (chessHomeTypeAdapter4 != null) {
                            chessHomeTypeAdapter4.notifyItemChanged(i);
                        }
                    }
                    ChessHomeFragment chessHomeFragment = ChessHomeFragment.this;
                    chessHomeTypeAdapter3 = chessHomeFragment.typeAdapter;
                    if (chessHomeTypeAdapter3 != null && (item = chessHomeTypeAdapter3.getItem(i)) != null) {
                        num = Integer.valueOf(item.getChessId());
                    }
                    chessHomeFragment.scorllCircleAll(num);
                }
            });
        }
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.type_recycler);
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$7
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                    boolean z;
                    int i3;
                    Intrinsics.checkParameterIsNotNull(recyclerView2, "recyclerView");
                    super.onScrolled(recyclerView2, i, i2);
                    z = ChessHomeFragment.this.mShouldScroll;
                    if (z) {
                        ChessHomeFragment.this.mShouldScroll = false;
                        ChessHomeFragment chessHomeFragment = ChessHomeFragment.this;
                        i3 = chessHomeFragment.index;
                        chessHomeFragment.smoothMoveToPosition(recyclerView2, i3);
                    }
                }
            });
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
        if (recyclerView2 != null) {
            recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initOnClick$8
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView3, int i, int i2) {
                    boolean z;
                    boolean z2;
                    ChessHomeTypeSubAdapter chessHomeTypeSubAdapter;
                    int i3;
                    Intrinsics.checkParameterIsNotNull(recyclerView3, "recyclerView");
                    super.onScrolled(recyclerView3, i, i2);
                    z = ChessHomeFragment.this.mShouldScroll;
                    int i4 = 0;
                    if (z) {
                        ChessHomeFragment.this.mShouldScroll = false;
                        ChessHomeFragment chessHomeFragment = ChessHomeFragment.this;
                        i3 = chessHomeFragment.index;
                        chessHomeFragment.smoothMoveToPosition(recyclerView3, i3);
                    }
                    z2 = ChessHomeFragment.this.isClick;
                    if (z2) {
                        return;
                    }
                    RecyclerView.LayoutManager layoutManager = recyclerView3.getLayoutManager();
                    if (layoutManager == null) {
                        throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
                    }
                    int findFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    chessHomeTypeSubAdapter = ChessHomeFragment.this.typeSubAllAdapter;
                    ChessTypeBean item = chessHomeTypeSubAdapter != null ? chessHomeTypeSubAdapter.getItem(findFirstVisibleItemPosition) : null;
                    ChessHomeFragment chessHomeFragment2 = ChessHomeFragment.this;
                    if (item != null) {
                        i4 = item.getChessId();
                    }
                    chessHomeFragment2.scorllCircleType(i4);
                }

                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView3, int i) {
                    boolean z;
                    Intrinsics.checkParameterIsNotNull(recyclerView3, "recyclerView");
                    super.onScrollStateChanged(recyclerView3, i);
                    if (i == 0) {
                        z = ChessHomeFragment.this.isClick;
                        if (!z) {
                            return;
                        }
                        ChessHomeFragment.this.isClick = false;
                    }
                }
            });
        }
    }

    public final void initBanner() {
        View view = getView();
        this.banner = view != null ? (MZBannerView) view.findViewById(R.id.mzbanner) : null;
        this.adUtil = new AdUtil((Activity) getContext());
        final ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_CHESS_BANNER);
        if (adPage == null || adPage.isEmpty()) {
            adPage.add(new AdPage());
        }
        if (!(adPage instanceof ArrayList) || adPage.isEmpty()) {
            return;
        }
        MZBannerView<AdPage> mZBannerView = this.banner;
        if (mZBannerView != null) {
            mZBannerView.setIndicatorRes(R.drawable.chess_indicator_normal, R.drawable.chess_indicator_selected);
        }
        MZBannerView<AdPage> mZBannerView2 = this.banner;
        if (mZBannerView2 != null) {
            mZBannerView2.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initBanner$1
                @Override // com.one.tomato.widget.mzbanner.MZBannerView.BannerPageClickListener
                public final void onPageClick(View view2, int i) {
                    AdUtil adUtil;
                    Object obj = adPage.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "adPage[position]");
                    AdPage adPage2 = (AdPage) obj;
                    adUtil = ChessHomeFragment.this.adUtil;
                    if (adUtil != null) {
                        adUtil.clickAd(adPage2.getAdId(), adPage2.getAdType(), adPage2.getAdJumpModule(), adPage2.getAdJumpDetail(), adPage2.getOpenType(), adPage2.getAdLink());
                    }
                }
            });
        }
        MZBannerView<AdPage> mZBannerView3 = this.banner;
        if (mZBannerView3 == null) {
            return;
        }
        mZBannerView3.setPages(adPage, ChessHomeFragment$initBanner$2.INSTANCE);
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
        ApiImplService.Companion.getApiImplService().queryGameUser().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameRegisterUserBean>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initNotice$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameRegisterUserBean gameRegisterUserBean) {
                ArrayList arrayList;
                if (gameRegisterUserBean != null) {
                    ArrayList<String> memberNames2 = gameRegisterUserBean.getMemberNames();
                    boolean z = false;
                    if (memberNames2 == null || memberNames2.isEmpty()) {
                        return;
                    }
                    arrayList = ChessHomeFragment.this.gameRegisterUserBean;
                    arrayList.addAll(memberNames2);
                    GameRegisterUserBean gameRegisterUserBean2 = gameRegisterBean;
                    ArrayList<String> memberNames3 = gameRegisterUserBean2 != null ? gameRegisterUserBean2.getMemberNames() : null;
                    if (memberNames3 == null || memberNames3.isEmpty()) {
                        z = true;
                    }
                    if (z) {
                        ChessHomeFragment.this.startScrollNotice();
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
        final ArrayList arrayList2 = new ArrayList();
        ArrayList<SubGamesBean> chessH5Game = GameUtils.INSTANCE.getChessH5Game(true);
        if (chessH5Game == null || chessH5Game.isEmpty()) {
            z = true;
        }
        if (!z) {
            arrayList2.addAll(chessH5Game);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        Observable.interval(0L, 20L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$startScrollNotice$subscribe$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Long l) {
                ArrayList arrayList3;
                ArrayList arrayList4;
                Random.Default r7 = Random.Default;
                arrayList3 = ChessHomeFragment.this.gameRegisterUserBean;
                int i = 0;
                int nextInt = r7.nextInt(0, arrayList3.size());
                arrayList4 = ChessHomeFragment.this.gameRegisterUserBean;
                Object obj = arrayList4.get(nextInt);
                Intrinsics.checkExpressionValueIsNotNull(obj, "gameRegisterUserBean[nextInt]");
                String string = AppUtil.getString(R.string.game_notice_text1);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.game_notice_text1)");
                Object[] objArr = {(String) obj};
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                MarqueeTextView marqueeTextView = (MarqueeTextView) ChessHomeFragment.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView != null) {
                    marqueeTextView.setVisibility(0);
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.append((CharSequence) format);
                final int nextInt2 = Random.Default.nextInt(0, arrayList2.size());
                Object obj2 = arrayList2.get(nextInt2);
                Intrinsics.checkExpressionValueIsNotNull(obj2, "adPage[gameNameIndex]");
                final SubGamesBean subGamesBean = (SubGamesBean) obj2;
                MarqueeTextView marqueeTextView2 = (MarqueeTextView) ChessHomeFragment.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView2 != null) {
                    marqueeTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$startScrollNotice$subscribe$1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            Context mContext;
                            Context mContext2;
                            if (Intrinsics.areEqual(subGamesBean.getAdType(), "7")) {
                                Iterator it2 = arrayList2.iterator();
                                while (it2.hasNext()) {
                                    SubGamesBean its = (SubGamesBean) it2.next();
                                    Intrinsics.checkExpressionValueIsNotNull(its, "its");
                                    its.setSelector(false);
                                }
                                Object obj3 = arrayList2.get(nextInt2);
                                Intrinsics.checkExpressionValueIsNotNull(obj3, "adPage[gameNameIndex]");
                                ((SubGamesBean) obj3).setSelector(true);
                                ChessGameActivity.Companion companion = ChessGameActivity.Companion;
                                mContext2 = ChessHomeFragment.this.getMContext();
                                if (mContext2 != null) {
                                    companion.startAct(mContext2, arrayList2);
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            } else if (Intrinsics.areEqual(subGamesBean.getAdType(), "9")) {
                                ChessHomeFragment.this.requestBGH5Login(subGamesBean);
                            } else {
                                GameUtils gameUtils = GameUtils.INSTANCE;
                                mContext = ChessHomeFragment.this.getMContext();
                                if (mContext != null) {
                                    gameUtils.clickGame(mContext, subGamesBean);
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            }
                        }
                    });
                }
                spannableStringBuilder.append((CharSequence) subGamesBean.getAdBrandName());
                SpannableString spannableString = new SpannableString(subGamesBean.getAdName());
                spannableString.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$startScrollNotice$subscribe$1$titleClickSpan$1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View widget) {
                        Intrinsics.checkParameterIsNotNull(widget, "widget");
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        Intrinsics.checkParameterIsNotNull(ds, "ds");
                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                        ds.setColor(Color.parseColor("#E4BA9A"));
                    }
                }, 0, spannableString.length(), 33);
                spannableStringBuilder.append((CharSequence) spannableString);
                spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.game_notice_text2));
                spannableStringBuilder.append((CharSequence) String.valueOf(FormatUtil.formatTwo(Double.valueOf(Random.Default.nextDouble(950.0d, 15000.0d)))));
                spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.common_renmingbi));
                Paint paint = new Paint();
                int measureText = (int) ((paint.measureText(spannableStringBuilder.toString()) / 5) / paint.measureText(ConstantUtils.PLACEHOLDER_STR_ONE));
                if (measureText >= 0) {
                    while (true) {
                        spannableStringBuilder.append((CharSequence) ConstantUtils.PLACEHOLDER_STR_ONE);
                        if (i == measureText) {
                            break;
                        }
                        i++;
                    }
                }
                MarqueeTextView marqueeTextView3 = (MarqueeTextView) ChessHomeFragment.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView3 != null) {
                    marqueeTextView3.setText(spannableStringBuilder);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestBGH5Login(SubGamesBean subGamesBean) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("adId", Integer.valueOf(subGamesBean.getGameId()));
        linkedHashMap.put("gameBrandId", subGamesBean.getAdBrandId());
        requestBGH5Login(linkedHashMap, subGamesBean);
    }

    public final void requestBGH5Login(Map<String, Object> mutableMap, final SubGamesBean subGamesBean) {
        Intrinsics.checkParameterIsNotNull(mutableMap, "mutableMap");
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        ApiImplService.Companion.getApiImplService().requestLiveGameBGLogin(mutableMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$requestBGH5Login$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                ChessHomeFragment.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<GameBGLoginBean>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$requestBGH5Login$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameBGLoginBean gameBGLoginBean) {
                String str;
                ChessHomeFragment.this.dismissDialog();
                GameUtils gameUtils = GameUtils.INSTANCE;
                Context context = ChessHomeFragment.this.getContext();
                if (context == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                SubGamesBean subGamesBean2 = subGamesBean;
                if (gameBGLoginBean == null || (str = gameBGLoginBean.getUrl()) == null) {
                    str = "";
                }
                gameUtils.clickGame(context, subGamesBean2, str);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ChessHomeFragment.this.dismissDialog();
            }
        });
    }

    public final void scorllCircleType(int i) {
        ChessHomeTypeAdapter chessHomeTypeAdapter = this.typeAdapter;
        List<ChessTypeBean> data = chessHomeTypeAdapter != null ? chessHomeTypeAdapter.getData() : null;
        if (data != null) {
            int i2 = 0;
            for (Object obj : data) {
                int i3 = i2 + 1;
                if (i2 >= 0) {
                    ChessTypeBean circleCategory = (ChessTypeBean) obj;
                    Intrinsics.checkExpressionValueIsNotNull(circleCategory, "circleCategory");
                    if (circleCategory.getChessId() == i) {
                        if (this.curPosition == i2) {
                            return;
                        }
                        this.curPosition = i2;
                        int size = data.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size) {
                                break;
                            }
                            ChessTypeBean chessTypeBean = data.get(i4);
                            Intrinsics.checkExpressionValueIsNotNull(chessTypeBean, "it[its]");
                            if (chessTypeBean.isSelect()) {
                                ChessTypeBean chessTypeBean2 = data.get(i4);
                                Intrinsics.checkExpressionValueIsNotNull(chessTypeBean2, "it[its]");
                                chessTypeBean2.setSelect(false);
                                ChessHomeTypeAdapter chessHomeTypeAdapter2 = this.typeAdapter;
                                if (chessHomeTypeAdapter2 != null) {
                                    chessHomeTypeAdapter2.notifyItemChanged(i4);
                                }
                            } else {
                                i4++;
                            }
                        }
                        ChessTypeBean chessTypeBean3 = data.get(i2);
                        Intrinsics.checkExpressionValueIsNotNull(chessTypeBean3, "it[index]");
                        chessTypeBean3.setSelect(true);
                        ChessHomeTypeAdapter chessHomeTypeAdapter3 = this.typeAdapter;
                        if (chessHomeTypeAdapter3 != null) {
                            chessHomeTypeAdapter3.notifyItemChanged(i2);
                        }
                        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.type_recycler);
                        if (recyclerView == null) {
                            return;
                        }
                        recyclerView.smoothScrollToPosition(this.curPosition);
                        return;
                    }
                    i2 = i3;
                } else {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
        }
    }

    public final void scorllCircleAll(Integer num) {
        if (num == null) {
            return;
        }
        ChessHomeTypeSubAdapter chessHomeTypeSubAdapter = this.typeSubAllAdapter;
        List<ChessTypeBean> data = chessHomeTypeSubAdapter != null ? chessHomeTypeSubAdapter.getData() : null;
        if (data == null) {
            return;
        }
        int i = 0;
        for (Object obj : data) {
            int i2 = i + 1;
            if (i >= 0) {
                ChessTypeBean circleAllBean = (ChessTypeBean) obj;
                Intrinsics.checkExpressionValueIsNotNull(circleAllBean, "circleAllBean");
                if (num.intValue() == circleAllBean.getChessId()) {
                    RecyclerView type_sub_recycler = (RecyclerView) _$_findCachedViewById(R$id.type_sub_recycler);
                    Intrinsics.checkExpressionValueIsNotNull(type_sub_recycler, "type_sub_recycler");
                    smoothMoveToPosition(type_sub_recycler, i);
                    return;
                }
                i = i2;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
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
}
