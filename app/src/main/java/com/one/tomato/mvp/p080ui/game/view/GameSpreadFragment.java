package com.one.tomato.mvp.p080ui.game.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.zxing.util.CodeUtils;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.GameSpreadDetail;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.mvp.p080ui.game.impl.IGameSpreadContact$IGameSpreadView;
import com.one.tomato.mvp.p080ui.game.presenter.GameSpreadPresenter;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.thirdpart.p084jc.JCRechargeUtil;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.MarqueeTextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: GameSpreadFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameSpreadFragment */
/* loaded from: classes3.dex */
public final class GameSpreadFragment extends MvpBaseFragment<IGameSpreadContact$IGameSpreadView, GameSpreadPresenter> implements IGameSpreadContact$IGameSpreadView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<GameSpreadDetail.GameList> adapter;
    private GameSpreadDetail gameSpreadDetail;

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
        return R.layout.fragment_game_spread;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: GameSpreadFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final GameSpreadFragment getInstance() {
            return new GameSpreadFragment();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public GameSpreadPresenter mo6441createPresenter() {
        return new GameSpreadPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        initAdapter();
        GameSpreadPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCashDetail(DBUtil.getMemberId());
        }
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = GameSpreadFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 3);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_help)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                FeedbackRechargeIssuesActivity.Companion companion = FeedbackRechargeIssuesActivity.Companion;
                mContext = GameSpreadFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 3);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_online)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = GameSpreadFragment.this.getMContext();
                JCRechargeUtil.startSpreadHelp(mContext);
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_wx)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GameSpreadDetail gameSpreadDetail;
                GameSpreadDetail gameSpreadDetail2;
                gameSpreadDetail = GameSpreadFragment.this.gameSpreadDetail;
                if (gameSpreadDetail != null) {
                    gameSpreadDetail2 = GameSpreadFragment.this.gameSpreadDetail;
                    ArrayList<GameSpreadDetail.ServiceList> arrayList = gameSpreadDetail2 != null ? gameSpreadDetail2.customerServiceList : null;
                    if (arrayList == null || !(!arrayList.isEmpty())) {
                        return;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<GameSpreadDetail.ServiceList> it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        GameSpreadDetail.ServiceList next = it2.next();
                        if (Intrinsics.areEqual(next.serviceType, "1")) {
                            arrayList2.add(next);
                        }
                    }
                    if (!(!arrayList2.isEmpty())) {
                        return;
                    }
                    String str = ((GameSpreadDetail.ServiceList) arrayList2.get(new Random().nextInt(arrayList2.size()))).account;
                    AppUtil.copyShareText(str, "");
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.game_spread_wx_service, str), 1);
                }
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_pt)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GameSpreadDetail gameSpreadDetail;
                GameSpreadDetail gameSpreadDetail2;
                Context mContext;
                gameSpreadDetail = GameSpreadFragment.this.gameSpreadDetail;
                if (gameSpreadDetail != null) {
                    gameSpreadDetail2 = GameSpreadFragment.this.gameSpreadDetail;
                    ArrayList<GameSpreadDetail.ServiceList> arrayList = gameSpreadDetail2 != null ? gameSpreadDetail2.customerServiceList : null;
                    if (arrayList == null || !(!arrayList.isEmpty())) {
                        return;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<GameSpreadDetail.ServiceList> it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        GameSpreadDetail.ServiceList next = it2.next();
                        if (Intrinsics.areEqual(next.serviceType, "2")) {
                            arrayList2.add(next);
                        }
                    }
                    if (!(!arrayList2.isEmpty())) {
                        return;
                    }
                    final String str = ((GameSpreadDetail.ServiceList) arrayList2.get(new Random().nextInt(arrayList2.size()))).account;
                    mContext = GameSpreadFragment.this.getMContext();
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
                    customAlertDialog.setTitle(R.string.game_spread_pt_title);
                    customAlertDialog.setMessage(R.string.game_spread_pt_content);
                    customAlertDialog.setCancelButtonListener();
                    customAlertDialog.setConfirmButtonListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$inintData$5$1$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            AppUtil.startBrowseView(str);
                        }
                    });
                }
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        GameSpreadPresenter mPresenter;
        super.onHiddenChanged(z);
        if (z || (mPresenter = getMPresenter()) == null) {
            return;
        }
        mPresenter.requestCashDetail(DBUtil.getMemberId());
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        GameSpreadPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCashDetail(DBUtil.getMemberId());
        }
    }

    private final void initAdapter() {
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        this.adapter = new BaseRecyclerViewAdapter<GameSpreadDetail.GameList>(mContext, R.layout.item_game_tab_spread, recyclerView) { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, GameSpreadDetail.GameList gameList) {
                super.convert(baseViewHolder, (BaseViewHolder) gameList);
                String str = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_bg) : null;
                Context context = this.mContext;
                if (gameList != null) {
                    str = gameList.poster;
                }
                ImageLoaderUtil.loadRecyclerThumbImage(context, imageView, new ImageBean(str));
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                GameSpreadFragment gameSpreadFragment = GameSpreadFragment.this;
                if (item == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (item == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.GameSpreadDetail.GameList");
                } else {
                    gameSpreadFragment.showQrCodeView((GameSpreadDetail.GameList) item);
                }
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setLayoutManager(new BaseLinearLayoutManager(getMContext(), 1, false));
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        BaseRecyclerViewAdapter<GameSpreadDetail.GameList> baseRecyclerViewAdapter = this.adapter;
        if (baseRecyclerViewAdapter != null) {
            recyclerView2.setAdapter(baseRecyclerViewAdapter);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameSpreadContact$IGameSpreadView
    public void handleCashDetail(GameSpreadDetail gameSpreadDetail) {
        Intrinsics.checkParameterIsNotNull(gameSpreadDetail, "gameSpreadDetail");
        this.gameSpreadDetail = gameSpreadDetail;
        ((TextView) _$_findCachedViewById(R$id.tv_cash_last)).setText(gameSpreadDetail.yesterdayEarn);
        ((TextView) _$_findCachedViewById(R$id.tv_cash_total)).setText(gameSpreadDetail.totalEarn);
        ((TextView) _$_findCachedViewById(R$id.tv_cash_available)).setText(gameSpreadDetail.balance);
        ArrayList<GameSpreadDetail.GameList> arrayList = gameSpreadDetail.gameList;
        if (arrayList == null || arrayList.isEmpty()) {
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
            Intrinsics.checkExpressionValueIsNotNull(recyclerView, "recyclerView");
            recyclerView.setVisibility(8);
        } else {
            RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
            Intrinsics.checkExpressionValueIsNotNull(recyclerView2, "recyclerView");
            recyclerView2.setVisibility(0);
            BaseRecyclerViewAdapter<GameSpreadDetail.GameList> baseRecyclerViewAdapter = this.adapter;
            if (baseRecyclerViewAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            }
            baseRecyclerViewAdapter.setNewData(arrayList);
            BaseRecyclerViewAdapter<GameSpreadDetail.GameList> baseRecyclerViewAdapter2 = this.adapter;
            if (baseRecyclerViewAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            }
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        }
        ArrayList<GameSpreadDetail.SpreadList> arrayList2 = gameSpreadDetail.spreadList;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            MarqueeTextView marqueeTextView = (MarqueeTextView) _$_findCachedViewById(R$id.tv_msg);
            if (marqueeTextView == null) {
                return;
            }
            marqueeTextView.setVisibility(8);
            return;
        }
        MarqueeTextView marqueeTextView2 = (MarqueeTextView) _$_findCachedViewById(R$id.tv_msg);
        if (marqueeTextView2 != null) {
            marqueeTextView2.setVisibility(0);
        }
        final StringBuilder sb = new StringBuilder();
        Iterator<GameSpreadDetail.SpreadList> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            GameSpreadDetail.SpreadList next = it2.next();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AppUtil.getString(R.string.game_spread_marquee_msg, next.phone, next.gameName, next.amount));
            sb.append((CharSequence) sb2);
            sb.append("\t\t\t\t\t\t\t");
        }
        MarqueeTextView marqueeTextView3 = (MarqueeTextView) _$_findCachedViewById(R$id.tv_msg);
        if (marqueeTextView3 == null) {
            return;
        }
        marqueeTextView3.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$handleCashDetail$1
            @Override // java.lang.Runnable
            public final void run() {
                MarqueeTextView marqueeTextView4 = (MarqueeTextView) GameSpreadFragment.this._$_findCachedViewById(R$id.tv_msg);
                if (marqueeTextView4 != null) {
                    marqueeTextView4.setText(sb.toString());
                }
            }
        }, 500L);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        GameSpreadPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCashDetail(DBUtil.getMemberId());
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutSuccess() {
        super.onLoginOutSuccess();
        GameSpreadPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCashDetail(DBUtil.getMemberId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r9v1, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v10, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v25, types: [T, java.lang.String] */
    public final void showQrCodeView(GameSpreadDetail.GameList gameList) {
        boolean contains$default;
        RelativeLayout rl_share = (RelativeLayout) _$_findCachedViewById(R$id.rl_share);
        Intrinsics.checkExpressionValueIsNotNull(rl_share, "rl_share");
        rl_share.setVisibility(0);
        ((TextView) _$_findCachedViewById(R$id.tv_game_title)).setText(gameList.name);
        ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_game_desc), new String[]{AppUtil.getString(R.string.game_spread_fanli_hint), gameList.rate + "%"}, new String[]{String.valueOf(getResources().getColor(R.color.white)), String.valueOf(getResources().getColor(R.color.color_FFDD60))}, new String[]{"16", "25"});
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = gameList.link;
        String shareUrl = (String) ref$ObjectRef.element;
        Intrinsics.checkExpressionValueIsNotNull(shareUrl, "shareUrl");
        contains$default = StringsKt__StringsKt.contains$default(shareUrl, "?", false, 2, null);
        if (contains$default) {
            StringBuilder sb = new StringBuilder();
            sb.append((String) ref$ObjectRef.element);
            sb.append("&t=");
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            sb.append(userInfo.getInviteCode());
            ref$ObjectRef.element = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append((String) ref$ObjectRef.element);
            sb2.append("?t=");
            UserInfo userInfo2 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
            sb2.append(userInfo2.getInviteCode());
            ref$ObjectRef.element = sb2.toString();
        }
        ((ImageView) _$_findCachedViewById(R$id.iv_qr_code)).setImageBitmap(CodeUtils.createQRCode((String) ref$ObjectRef.element, (int) DisplayMetricsUtils.dp2px(200.0f)));
        ((ImageView) _$_findCachedViewById(R$id.iv_copy_link)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$showQrCodeView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppUtil.copyShareText((String) Ref$ObjectRef.this.element, AppUtil.getString(R.string.game_spread_copy_success));
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSpreadFragment$showQrCodeView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RelativeLayout rl_share2 = (RelativeLayout) GameSpreadFragment.this._$_findCachedViewById(R$id.rl_share);
                Intrinsics.checkExpressionValueIsNotNull(rl_share2, "rl_share");
                rl_share2.setVisibility(8);
            }
        });
    }
}
