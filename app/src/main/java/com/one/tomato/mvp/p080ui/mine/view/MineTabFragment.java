package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.app.NotificationCompat;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.dialog.LevelNickUpdateDialog;
import com.one.tomato.dialog.UpNotifyDialog;
import com.one.tomato.entity.CreatorCenterBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MineItem;
import com.one.tomato.entity.event.LevelBeanEvent;
import com.one.tomato.entity.event.LookTimeBeanEvent;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.view.ChessMainTabActivity;
import com.one.tomato.mvp.p080ui.download.view.VideoDownloadActivity;
import com.one.tomato.mvp.p080ui.game.view.GameSpreadActivity;
import com.one.tomato.mvp.p080ui.level.MyLevelActivity;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.mine.adapter.MineGridAdapter;
import com.one.tomato.mvp.p080ui.mine.impl.IMineTabContact$IMineTabView;
import com.one.tomato.mvp.p080ui.mine.presenter.MineTabPresenter;
import com.one.tomato.mvp.p080ui.mine.view.AppMarketHtmlActivity;
import com.one.tomato.mvp.p080ui.mine.view.CreatorCenterActivity;
import com.one.tomato.mvp.p080ui.mine.view.MyInteractionActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.p082up.view.SubscribeUpListActivity;
import com.one.tomato.mvp.p080ui.p082up.view.UpHomeActivity;
import com.one.tomato.mvp.p080ui.post.view.ReviewPostActivity;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.p085ui.feedback.FeedbackEnterActivity;
import com.one.tomato.p085ui.income.IncomeCenterActivity;
import com.one.tomato.p085ui.messge.p086ui.MyMessageNotifctionActivity;
import com.one.tomato.p085ui.mine.MyCollectPostActivity;
import com.one.tomato.p085ui.mine.MyExchangeActivity;
import com.one.tomato.p085ui.mine.MyFanListActivity;
import com.one.tomato.p085ui.mine.MyFocusMemberActivity;
import com.one.tomato.p085ui.mine.MyShareActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.p085ui.recharge.RechargeExGameActivity;
import com.one.tomato.p085ui.setting.AboutActivity;
import com.one.tomato.p085ui.setting.SettingActivity;
import com.one.tomato.p085ui.task.TaskCenterActivity;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import com.one.tomato.widget.MineCustomItemLayout;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: MineTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.MineTabFragment */
/* loaded from: classes3.dex */
public final class MineTabFragment extends MvpBaseFragment<IMineTabContact$IMineTabView, MineTabPresenter> implements IMineTabContact$IMineTabView {
    public static final Companion Companion = new Companion(null);
    private static CreatorCenterBean creatorCenterBean;
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> creatorProfitAdapter;
    private Disposable levelBeanDisposable;
    private Disposable lookTimeDisposable;
    private MineGridAdapter mineGridAdapter;
    private UserInfo userInfo;

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
        return R.layout.fragment_tab_mine;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public static final /* synthetic */ MineGridAdapter access$getMineGridAdapter$p(MineTabFragment mineTabFragment) {
        MineGridAdapter mineGridAdapter = mineTabFragment.mineGridAdapter;
        if (mineGridAdapter != null) {
            return mineGridAdapter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
        throw null;
    }

    public static final /* synthetic */ UserInfo access$getUserInfo$p(MineTabFragment mineTabFragment) {
        UserInfo userInfo = mineTabFragment.userInfo;
        if (userInfo != null) {
            return userInfo;
        }
        Intrinsics.throwUninitializedPropertyAccessException("userInfo");
        throw null;
    }

    /* compiled from: MineTabFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.MineTabFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final CreatorCenterBean getCreatorCenterBean() {
            return MineTabFragment.creatorCenterBean;
        }

        public final void setCreatorCenterBean(CreatorCenterBean creatorCenterBean) {
            MineTabFragment.creatorCenterBean = creatorCenterBean;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return super.onCreateView(inflater, viewGroup, bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MineTabPresenter mo6441createPresenter() {
        return new MineTabPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_interaction)).setRootBackgroundRes(0);
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_exchange)).setRootBackgroundRes(0);
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_offcial)).setRootBackgroundRes(0);
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_opinion)).setRootBackgroundRes(0);
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_tomato)).setRootBackgroundRes(0);
            ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_potato)).setRootBackgroundRes(0);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        registerRxBus();
        addListener();
        initMineGridAdapter();
        initCreatorProfitAdapter();
    }

    private final void registerRxBus() {
        this.lookTimeDisposable = RxBus.getDefault().toObservable(LookTimeBeanEvent.class).subscribe(new Consumer<LookTimeBeanEvent>() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$registerRxBus$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(LookTimeBeanEvent lookTimeBeanEvent) {
                MineTabFragment.this.updateLookTimes();
            }
        });
        RxSubscriptions.add(this.lookTimeDisposable);
        this.levelBeanDisposable = RxBus.getDefault().toObservable(LevelBeanEvent.class).subscribe(new Consumer<LevelBeanEvent>() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$registerRxBus$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(LevelBeanEvent levelBeanEvent) {
                ((ImageView) MineTabFragment.this._$_findCachedViewById(R$id.iv_level_nick)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$registerRxBus$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MineTabFragment.this.updateLevelInfo();
                    }
                }, 200L);
            }
        });
        RxSubscriptions.add(this.levelBeanDisposable);
    }

    private final void unRegisterRxBus() {
        RxSubscriptions.remove(this.lookTimeDisposable);
        RxSubscriptions.remove(this.levelBeanDisposable);
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_setting)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                SettingActivity.startActivity(mContext);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_head)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                NewMyHomePageActivity.Companion companion = NewMyHomePageActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, DBUtil.getMemberId());
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_nick)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                NewMyHomePageActivity.Companion companion = NewMyHomePageActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, DBUtil.getMemberId());
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_login)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                LoginActivity.Companion companion = LoginActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_level_nick)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                MyLevelActivity.Companion companion = MyLevelActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_user_level)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                MyLevelActivity.Companion companion = MyLevelActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_focus)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                MyFocusMemberActivity.startActivity(mContext);
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_fan)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                MyFanListActivity.startActivity(mContext);
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_subscribe)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                SubscribeUpListActivity.Companion companion = SubscribeUpListActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_collect)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                MyCollectPostActivity.startActivity(mContext);
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_vip)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                VipActivity.Companion companion = VipActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (!application.isChess()) {
            ((RelativeLayout) _$_findCachedViewById(R$id.rl_share_app)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = MineTabFragment.this.getMContext();
                    MyShareActivity.startActivity(mContext);
                }
            });
            ((RelativeLayout) _$_findCachedViewById(R$id.rl_game)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    AppMarketHtmlActivity.Companion companion = AppMarketHtmlActivity.Companion;
                    mContext = MineTabFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_creator)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                CreatorCenterActivity.Companion companion = CreatorCenterActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_opinion)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                FeedbackEnterActivity.startActivity(mContext);
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_tomato)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                AboutActivity.startActivity(mContext);
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_potato)).setOnClickListener(MineTabFragment$addListener$17.INSTANCE);
        MineCustomItemLayout mineCustomItemLayout = (MineCustomItemLayout) _$_findCachedViewById(R$id.rl_offcial);
        if (mineCustomItemLayout != null) {
            mineCustomItemLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    Context mContext2;
                    if (MineTabFragment.access$getUserInfo$p(MineTabFragment.this).isOfficial()) {
                        ReviewPostActivity.Companion companion = ReviewPostActivity.Companion;
                        mContext2 = MineTabFragment.this.getMContext();
                        if (mContext2 != null) {
                            companion.startAct(mContext2);
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    SystemParam systemParam = DBUtil.getSystemParam();
                    Intrinsics.checkExpressionValueIsNotNull(systemParam, "systemParam");
                    if (systemParam.getReViewFlag() == 0 || (systemParam.getReViewFlag() == 2 && MineTabFragment.access$getUserInfo$p(MineTabFragment.this).getReviewType() != 1)) {
                        ToastUtil.showCenterToast("审贴功能已关闭");
                        return;
                    }
                    ReviewPostActivity.Companion companion2 = ReviewPostActivity.Companion;
                    mContext = MineTabFragment.this.getMContext();
                    if (mContext != null) {
                        companion2.startAct(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_interaction)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                MyInteractionActivity.Companion companion = MyInteractionActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_exchange)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MineTabFragment.this.getMContext();
                MyExchangeActivity.startActivity(mContext);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.image_promote)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                GameSpreadActivity.Companion companion = GameSpreadActivity.Companion;
                mContext = MineTabFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
    }

    private final void initMineGridAdapter() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MineItem(R.drawable.my_live, AppUtil.getString(R.string.my_live), "live"));
        arrayList.add(new MineItem(R.drawable.my_purse, AppUtil.getString(R.string.my_money_beg), "recharge"));
        arrayList.add(new MineItem(R.drawable.my_exchange, AppUtil.getString(R.string.recharge_ex), "exchange"));
        arrayList.add(new MineItem(R.drawable.my_income, AppUtil.getString(R.string.my_income), ConstantUtils.RANK_TYPE_INCOME));
        arrayList.add(new MineItem(R.drawable.my_task, AppUtil.getString(R.string.my_task), "task"));
        arrayList.add(new MineItem(R.drawable.my_up_y1, AppUtil.getString(R.string.my_up_n), "up"));
        arrayList.add(new MineItem(R.drawable.my_system_msg, AppUtil.getString(R.string.my_message), NotificationCompat.CATEGORY_MESSAGE));
        arrayList.add(new MineItem(R.drawable.my_download, AppUtil.getString(R.string.common_download_offline), "cache"));
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(getMContext(), 4);
        RecyclerView recyclerView_my = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_my);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_my, "recyclerView_my");
        recyclerView_my.setLayoutManager(fullyGridLayoutManager);
        Context mContext = getMContext();
        if (mContext != null) {
            RecyclerView recyclerView_my2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_my);
            Intrinsics.checkExpressionValueIsNotNull(recyclerView_my2, "recyclerView_my");
            this.mineGridAdapter = new MineGridAdapter(mContext, arrayList, recyclerView_my2);
            RecyclerView recyclerView_my3 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_my);
            Intrinsics.checkExpressionValueIsNotNull(recyclerView_my3, "recyclerView_my");
            MineGridAdapter mineGridAdapter = this.mineGridAdapter;
            if (mineGridAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                throw null;
            }
            recyclerView_my3.setAdapter(mineGridAdapter);
            MineGridAdapter mineGridAdapter2 = this.mineGridAdapter;
            if (mineGridAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                throw null;
            }
            mineGridAdapter2.setEnableLoadMore(false);
            updateUpInfo();
            MineGridAdapter mineGridAdapter3 = this.mineGridAdapter;
            if (mineGridAdapter3 != null) {
                mineGridAdapter3.setOnMineGridAdapterListener(new MineGridAdapter.MineGridAdapterListener() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$initMineGridAdapter$1
                    @Override // com.one.tomato.mvp.p080ui.mine.adapter.MineGridAdapter.MineGridAdapterListener
                    public void onItemClick(int i) {
                        Context mContext2;
                        Context mContext3;
                        Context mContext4;
                        Context mContext5;
                        Context mContext6;
                        Context mContext7;
                        Context mContext8;
                        Context mContext9;
                        Context mContext10;
                        Context mContext11;
                        MineItem item = MineTabFragment.access$getMineGridAdapter$p(MineTabFragment.this).getItem(i);
                        String str = item != null ? item.tag : null;
                        if (str == null) {
                            return;
                        }
                        switch (str.hashCode()) {
                            case -1184259671:
                                if (!str.equals(ConstantUtils.RANK_TYPE_INCOME)) {
                                    return;
                                }
                                IncomeCenterActivity.Companion companion = IncomeCenterActivity.Companion;
                                mContext2 = MineTabFragment.this.getMContext();
                                if (mContext2 != null) {
                                    companion.startActivity(mContext2);
                                    return;
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            case -806191449:
                                if (!str.equals("recharge")) {
                                    return;
                                }
                                BaseApplication application = BaseApplication.getApplication();
                                Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
                                if (!application.isChess()) {
                                    mContext3 = MineTabFragment.this.getMContext();
                                    RechargeActivity.startActivity(mContext3);
                                    return;
                                }
                                mContext4 = MineTabFragment.this.getMContext();
                                if (mContext4 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity");
                                }
                                ((ChessMainTabActivity) mContext4).selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_RECHARGE());
                                return;
                            case 3739:
                                if (!str.equals("up")) {
                                    return;
                                }
                                if (!PreferencesUtil.getInstance().getBoolean("up_new_tag")) {
                                    PreferencesUtil.getInstance().putBoolean("up_new_tag", true);
                                }
                                MineTabFragment.access$getMineGridAdapter$p(MineTabFragment.this).refreshNotifyItemChanged(i);
                                if (Intrinsics.areEqual(MineTabFragment.access$getUserInfo$p(MineTabFragment.this).getUpHostType(), "3")) {
                                    UpHomeActivity.Companion companion2 = UpHomeActivity.Companion;
                                    mContext6 = MineTabFragment.this.getMContext();
                                    companion2.startAct(mContext6, 2);
                                    return;
                                }
                                UpHomeActivity.Companion companion3 = UpHomeActivity.Companion;
                                mContext5 = MineTabFragment.this.getMContext();
                                companion3.startAct(mContext5, 1);
                                return;
                            case 108417:
                                if (!str.equals(NotificationCompat.CATEGORY_MESSAGE)) {
                                    return;
                                }
                                mContext7 = MineTabFragment.this.getMContext();
                                MyMessageNotifctionActivity.startActivity(mContext7);
                                return;
                            case 3322092:
                                if (!str.equals("live")) {
                                    return;
                                }
                                TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                                mContext8 = MineTabFragment.this.getMContext();
                                singleton.startMyLiveActivity(mContext8);
                                return;
                            case 3552645:
                                if (!str.equals("task")) {
                                    return;
                                }
                                mContext9 = MineTabFragment.this.getMContext();
                                TaskCenterActivity.startEarnActivity(mContext9);
                                return;
                            case 94416770:
                                if (!str.equals("cache")) {
                                    return;
                                }
                                VideoDownloadActivity.Companion companion4 = VideoDownloadActivity.Companion;
                                mContext10 = MineTabFragment.this.getMContext();
                                if (mContext10 != null) {
                                    companion4.startActivity(mContext10);
                                    return;
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            case 1989774883:
                                if (!str.equals("exchange")) {
                                    return;
                                }
                                RechargeExGameActivity.Companion companion5 = RechargeExGameActivity.Companion;
                                mContext11 = MineTabFragment.this.getMContext();
                                if (mContext11 != null) {
                                    companion5.startActivity(mContext11);
                                    return;
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            default:
                                return;
                        }
                    }
                });
                return;
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                throw null;
            }
        }
        Intrinsics.throwNpe();
        throw null;
    }

    private final void initCreatorProfitAdapter() {
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.rv_profit_list);
        this.creatorProfitAdapter = new BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank>(this, mContext, R.layout.item_creator_profit_head, recyclerView) { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$initCreatorProfitAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, CreatorCenterBean.TodayRank todayRank) {
                super.convert(baseViewHolder, (BaseViewHolder) todayRank);
                String str = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_head) : null;
                Context context = this.mContext;
                if (todayRank != null) {
                    str = todayRank.avatar;
                }
                ImageLoaderUtil.loadHeadImage(context, imageView, new ImageBean(str));
            }
        };
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 0, false);
        RecyclerView rv_profit_list = (RecyclerView) _$_findCachedViewById(R$id.rv_profit_list);
        Intrinsics.checkExpressionValueIsNotNull(rv_profit_list, "rv_profit_list");
        rv_profit_list.setLayoutManager(baseLinearLayoutManager);
        RecyclerView rv_profit_list2 = (RecyclerView) _$_findCachedViewById(R$id.rv_profit_list);
        Intrinsics.checkExpressionValueIsNotNull(rv_profit_list2, "rv_profit_list");
        BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> baseRecyclerViewAdapter = this.creatorProfitAdapter;
        if (baseRecyclerViewAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("creatorProfitAdapter");
            throw null;
        }
        rv_profit_list2.setAdapter(baseRecyclerViewAdapter);
        BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> baseRecyclerViewAdapter2 = this.creatorProfitAdapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("creatorProfitAdapter");
            throw null;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            initUserInfo();
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            initUserInfo();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        if (!isHidden()) {
            initUserInfo();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x023f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void initUserInfo() {
        String str;
        MineGridAdapter mineGridAdapter;
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        this.userInfo = userInfo;
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        if (userInfo2 == null) {
            str = "";
        } else if (userInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        } else {
            str = userInfo2.getAvatar();
        }
        ImageLoaderUtil.loadHeadImage(getMContext(), (ImageView) _$_findCachedViewById(R$id.iv_head), new ImageBean(str), 100, 100);
        TextView tv_focus_num = (TextView) _$_findCachedViewById(R$id.tv_focus_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_focus_num, "tv_focus_num");
        StringBuilder sb = new StringBuilder();
        sb.append("");
        UserInfo userInfo3 = this.userInfo;
        if (userInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        sb.append(userInfo3.getUserFollowCount());
        tv_focus_num.setText(sb.toString());
        TextView tv_focus_num2 = (TextView) _$_findCachedViewById(R$id.tv_focus_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_focus_num2, "tv_focus_num");
        tv_focus_num2.setTypeface(ViewUtil.getNumFontTypeface(getMContext()));
        TextView tv_subscribe_num = (TextView) _$_findCachedViewById(R$id.tv_subscribe_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_subscribe_num, "tv_subscribe_num");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        UserInfo userInfo4 = this.userInfo;
        if (userInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        sb2.append(userInfo4.getSubscribeCount());
        tv_subscribe_num.setText(sb2.toString());
        TextView tv_subscribe_num2 = (TextView) _$_findCachedViewById(R$id.tv_subscribe_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_subscribe_num2, "tv_subscribe_num");
        tv_subscribe_num2.setTypeface(ViewUtil.getNumFontTypeface(getMContext()));
        TextView tv_collect_num = (TextView) _$_findCachedViewById(R$id.tv_collect_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_collect_num, "tv_collect_num");
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        UserInfo userInfo5 = this.userInfo;
        if (userInfo5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        sb3.append(userInfo5.getFavorCount());
        tv_collect_num.setText(sb3.toString());
        TextView tv_collect_num2 = (TextView) _$_findCachedViewById(R$id.tv_collect_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_collect_num2, "tv_collect_num");
        tv_collect_num2.setTypeface(ViewUtil.getNumFontTypeface(getMContext()));
        TextView tv_fan_num = (TextView) _$_findCachedViewById(R$id.tv_fan_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_fan_num, "tv_fan_num");
        StringBuilder sb4 = new StringBuilder();
        sb4.append("");
        UserInfo userInfo6 = this.userInfo;
        if (userInfo6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        sb4.append(userInfo6.getUserFansCount());
        tv_fan_num.setText(sb4.toString());
        TextView tv_fan_num2 = (TextView) _$_findCachedViewById(R$id.tv_fan_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_fan_num2, "tv_fan_num");
        tv_fan_num2.setTypeface(ViewUtil.getNumFontTypeface(getMContext()));
        if (isLogin()) {
            TextView tv_login = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
            tv_login.setVisibility(8);
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_nick);
            UserInfo userInfo7 = this.userInfo;
            if (userInfo7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            }
            textView.setText(userInfo7.getName());
        } else {
            TextView tv_login2 = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login2, "tv_login");
            tv_login2.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.tv_nick)).setText(R.string.user_tourist);
        }
        UserInfo userInfo8 = this.userInfo;
        if (userInfo8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        if (!userInfo8.isOfficial()) {
            UserInfo userInfo9 = this.userInfo;
            if (userInfo9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            } else if (userInfo9.getReviewType() != 0) {
                UserInfo userInfo10 = this.userInfo;
                if (userInfo10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                    throw null;
                } else if (userInfo10.getReviewType() != 1) {
                    MineCustomItemLayout mineCustomItemLayout = (MineCustomItemLayout) _$_findCachedViewById(R$id.rl_offcial);
                    if (mineCustomItemLayout != null) {
                        mineCustomItemLayout.setVisibility(8);
                    }
                    ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_review);
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                    if (Intrinsics.compare(BaseApplication.isMyMessageHave(), 0) == 1) {
                        String valueOf = String.valueOf(BaseApplication.isMyMessageHave());
                        if (BaseApplication.isMyMessageHave() > 99) {
                            valueOf = "99+";
                        }
                        MineGridAdapter mineGridAdapter2 = this.mineGridAdapter;
                        if (mineGridAdapter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                            throw null;
                        }
                        mineGridAdapter2.getData().get(5).newMsg = true;
                        MineGridAdapter mineGridAdapter3 = this.mineGridAdapter;
                        if (mineGridAdapter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                            throw null;
                        }
                        mineGridAdapter3.getData().get(5).systemNum = valueOf;
                    }
                    mineGridAdapter = this.mineGridAdapter;
                    if (mineGridAdapter != null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                        throw null;
                    }
                    mineGridAdapter.notifyDataSetChanged();
                    updateLookTimes();
                    updateLevelInfo();
                    updateVipInfo();
                    updateUpInfo();
                    requestCreatorInfo();
                    return;
                }
            }
        }
        MineCustomItemLayout mineCustomItemLayout2 = (MineCustomItemLayout) _$_findCachedViewById(R$id.rl_offcial);
        if (mineCustomItemLayout2 != null) {
            mineCustomItemLayout2.setVisibility(0);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.iv_review);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        if (Intrinsics.compare(BaseApplication.isMyMessageHave(), 0) == 1) {
        }
        mineGridAdapter = this.mineGridAdapter;
        if (mineGridAdapter != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateLookTimes() {
        String string = AppUtil.getString(R.string.my_free_look_time);
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            VideoPlayCountUtils videoPlayCountUtils = VideoPlayCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils, "VideoPlayCountUtils.getInstance()");
            if (videoPlayCountUtils.isFreePlay()) {
                String[] strArr = {string, AppUtil.getString(R.string.my_free_look_mo_limit)};
                ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_look_times), strArr, new String[]{String.valueOf(getResources().getColor(R.color.chess_A9A9C0)) + "", String.valueOf(getResources().getColor(R.color.white)) + ""}, new String[]{"14", "14"});
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            VideoPlayCountUtils videoPlayCountUtils2 = VideoPlayCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils2, "VideoPlayCountUtils.getInstance()");
            sb.append(videoPlayCountUtils2.getRemainTimes());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("/");
            VideoPlayCountUtils videoPlayCountUtils3 = VideoPlayCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils3, "VideoPlayCountUtils.getInstance()");
            sb3.append(videoPlayCountUtils3.getFreeTimes());
            String[] strArr2 = {string, sb2, sb3.toString()};
            ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_look_times), strArr2, new String[]{String.valueOf(getResources().getColor(R.color.chess_A9A9C0)) + "", String.valueOf(getResources().getColor(R.color.white)) + "", String.valueOf(getResources().getColor(R.color.chess_A9A9C0)) + ""}, new String[]{"14", "14", "14"});
            return;
        }
        VideoPlayCountUtils videoPlayCountUtils4 = VideoPlayCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils4, "VideoPlayCountUtils.getInstance()");
        if (videoPlayCountUtils4.isFreePlay()) {
            String[] strArr3 = {string, AppUtil.getString(R.string.my_free_look_mo_limit)};
            ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_look_times), strArr3, new String[]{String.valueOf(getResources().getColor(R.color.text_middle)) + "", String.valueOf(getResources().getColor(R.color.colorAccent)) + ""}, new String[]{"14", "14"});
            return;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        VideoPlayCountUtils videoPlayCountUtils5 = VideoPlayCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils5, "VideoPlayCountUtils.getInstance()");
        sb4.append(videoPlayCountUtils5.getRemainTimes());
        String sb5 = sb4.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append("/");
        VideoPlayCountUtils videoPlayCountUtils6 = VideoPlayCountUtils.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(videoPlayCountUtils6, "VideoPlayCountUtils.getInstance()");
        sb6.append(videoPlayCountUtils6.getFreeTimes());
        String[] strArr4 = {string, sb5, sb6.toString()};
        ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_look_times), strArr4, new String[]{String.valueOf(getResources().getColor(R.color.text_middle)) + "", String.valueOf(getResources().getColor(R.color.colorAccent)) + "", String.valueOf(getResources().getColor(R.color.text_middle)) + ""}, new String[]{"14", "14", "14"});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateLevelInfo() {
        LevelBean levelBean = DBUtil.getLevelBean();
        Intrinsics.checkExpressionValueIsNotNull(levelBean, "levelBean");
        if (levelBean.getCurrentLevelIndex() > 0) {
            UserPermissionUtil.getInstance().userLevelNickShow((ImageView) _$_findCachedViewById(R$id.iv_level_nick), DBUtil.getLevelBean());
            LevelBean levelBean2 = DBUtil.getLevelBean();
            Intrinsics.checkExpressionValueIsNotNull(levelBean2, "DBUtil.getLevelBean()");
            UserPermissionUtil.getInstance().uerLevelShow((ImageView) _$_findCachedViewById(R$id.iv_user_level), levelBean2.getCurrentLevelIndex());
            if (!PreferencesUtil.getInstance().getBoolean("level_first_login", true)) {
                return;
            }
            PreferencesUtil.getInstance().putBoolean("level_first_login", false);
            new LevelNickUpdateDialog(getMContext());
        }
    }

    private final void updateVipInfo() {
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        this.userInfo = userInfo;
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        } else if (Intrinsics.compare(userInfo2.getVipType(), 0) == 1) {
            ((TextView) _$_findCachedViewById(R$id.tv_vip_open_status)).setText(R.string.vip_open_y);
            UserInfo userInfo3 = this.userInfo;
            if (userInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            }
            if (userInfo3.getVipType() == 1) {
                ((TextView) _$_findCachedViewById(R$id.tv_vip_status)).setText(R.string.vip_package_agent);
            } else {
                TextView textView = (TextView) _$_findCachedViewById(R$id.tv_vip_status);
                Object[] objArr = new Object[1];
                UserInfo userInfo4 = this.userInfo;
                if (userInfo4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                    throw null;
                } else {
                    objArr[0] = userInfo4.getExpireTime();
                    textView.setText(AppUtil.getString(R.string.vip_expire_time, objArr));
                }
            }
            ImageView iv_vip = (ImageView) _$_findCachedViewById(R$id.iv_vip);
            Intrinsics.checkExpressionValueIsNotNull(iv_vip, "iv_vip");
            iv_vip.setVisibility(0);
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_vip_open_status)).setText(R.string.vip_open_n);
            ((TextView) _$_findCachedViewById(R$id.tv_vip_status)).setText(R.string.vip_open_n_hint);
            ImageView iv_vip2 = (ImageView) _$_findCachedViewById(R$id.iv_vip);
            Intrinsics.checkExpressionValueIsNotNull(iv_vip2, "iv_vip");
            iv_vip2.setVisibility(8);
        }
    }

    private final void updateUpInfo() {
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        this.userInfo = userInfo;
        MineItem mineItem = new MineItem(R.drawable.mine_my_up_1, AppUtil.getString(R.string.my_up_n), "up");
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        if (userInfo2.getUpLevel() == 1) {
            mineItem.resId = R.drawable.mine_my_up_1;
            mineItem.name = AppUtil.getString(R.string.my_up_y1);
        } else {
            UserInfo userInfo3 = this.userInfo;
            if (userInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            } else if (userInfo3.getUpLevel() == 2) {
                mineItem.resId = R.drawable.mine_my_up_2;
                mineItem.name = AppUtil.getString(R.string.my_up_y2);
            } else {
                UserInfo userInfo4 = this.userInfo;
                if (userInfo4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                    throw null;
                } else if (userInfo4.getUpLevel() == 3) {
                    mineItem.resId = R.drawable.mine_my_up_3;
                    mineItem.name = AppUtil.getString(R.string.my_up_y3);
                } else {
                    UserInfo userInfo5 = this.userInfo;
                    if (userInfo5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                        throw null;
                    } else if (userInfo5.getUpLevel() == 4) {
                        mineItem.resId = R.drawable.mine_my_up_4;
                        mineItem.name = AppUtil.getString(R.string.my_up_y4);
                    } else {
                        UserInfo userInfo6 = this.userInfo;
                        if (userInfo6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                            throw null;
                        } else if (userInfo6.getUpLevel() == 5) {
                            mineItem.resId = R.drawable.mine_my_up_5;
                            mineItem.name = AppUtil.getString(R.string.my_up_y5);
                        }
                    }
                }
            }
        }
        int i = 0;
        MineGridAdapter mineGridAdapter = this.mineGridAdapter;
        if (mineGridAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
            throw null;
        }
        Iterator<MineItem> it2 = mineGridAdapter.getData().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            MineItem next = it2.next();
            if (Intrinsics.areEqual("up", next.tag)) {
                MineGridAdapter mineGridAdapter2 = this.mineGridAdapter;
                if (mineGridAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
                    throw null;
                }
                i = mineGridAdapter2.getData().indexOf(next);
            }
        }
        MineGridAdapter mineGridAdapter3 = this.mineGridAdapter;
        if (mineGridAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mineGridAdapter");
            throw null;
        }
        mineGridAdapter3.setData(i, mineItem);
        UserInfo userInfo7 = this.userInfo;
        if (userInfo7 != null) {
            showUpDialog(userInfo7.getUpLevel());
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void showUpDialog(int i) {
        Context mContext;
        if (i == 0) {
            return;
        }
        int i2 = R.drawable.my_up_y1;
        String title = AppUtil.getString(R.string.my_up_y1_dialog_title);
        String string = AppUtil.getString(R.string.my_up_y1_dialog_message);
        String message = "";
        if (i != 1) {
            if (i == 2) {
                if (PreferencesUtil.getInstance().getBoolean("level2")) {
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("level2", true);
                i2 = R.drawable.my_up_y2;
                title = AppUtil.getString(R.string.my_up_y2_dialog_title);
            } else if (i == 3) {
                if (PreferencesUtil.getInstance().getBoolean("level3")) {
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("level3", true);
                i2 = R.drawable.my_up_y3;
                title = AppUtil.getString(R.string.my_up_y3_dialog_title);
            } else if (i == 4) {
                if (PreferencesUtil.getInstance().getBoolean("level4")) {
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("level4", true);
                i2 = R.drawable.my_up_y4;
                title = AppUtil.getString(R.string.my_up_y4_dialog_title);
            } else if (i == 5) {
                if (PreferencesUtil.getInstance().getBoolean("level5")) {
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("level5", true);
                i2 = R.drawable.my_up_y5;
                title = AppUtil.getString(R.string.my_up_y5_dialog_title);
            }
            mContext = getMContext();
            if (mContext == null) {
                UpNotifyDialog upNotifyDialog = new UpNotifyDialog(mContext);
                Context mContext2 = getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                Drawable drawable = ContextCompat.getDrawable(mContext2, i2);
                if (drawable == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                Intrinsics.checkExpressionValueIsNotNull(drawable, "ContextCompat.getDrawable(mContext!!, image)!!");
                upNotifyDialog.setImageUp(drawable);
                Intrinsics.checkExpressionValueIsNotNull(title, "title");
                upNotifyDialog.setTextTitle(title);
                Intrinsics.checkExpressionValueIsNotNull(message, "message");
                upNotifyDialog.setTextMessage(message);
                upNotifyDialog.addButtonClick(new MineTabFragment$showUpDialog$1(upNotifyDialog));
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        } else if (PreferencesUtil.getInstance().getBoolean("level1")) {
            return;
        } else {
            PreferencesUtil.getInstance().putBoolean("level1", true);
        }
        message = string;
        mContext = getMContext();
        if (mContext == null) {
        }
    }

    private final void requestCreatorInfo() {
        CreatorCenterBean creatorCenterBean2 = creatorCenterBean;
        if (creatorCenterBean2 == null) {
            if (DBUtil.getMemberId() == 0) {
                return;
            }
            ApiImplService.Companion.getApiImplService().requestCreatorCenterBean(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<CreatorCenterBean>() { // from class: com.one.tomato.mvp.ui.mine.view.MineTabFragment$requestCreatorInfo$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(CreatorCenterBean creatorCenterBean3) {
                    if (creatorCenterBean3 != null) {
                        MineTabFragment.this.updateCreatorInfo(creatorCenterBean3);
                    }
                }
            });
        } else if (creatorCenterBean2 != null) {
            updateCreatorInfo(creatorCenterBean2);
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateCreatorInfo(CreatorCenterBean creatorCenterBean2) {
        List split$default;
        ((TextView) _$_findCachedViewById(R$id.tv_creator_publish_num)).setText(FormatUtil.formatNumOverTenThousand(creatorCenterBean2.countPublished));
        ((TextView) _$_findCachedViewById(R$id.tv_creator_serialize_num)).setText(FormatUtil.formatNumOverTenThousand(creatorCenterBean2.countSerialGroup));
        SystemParam systemParam = DBUtil.getSystemParam();
        String topListCfg = systemParam != null ? systemParam.getTopListCfg() : null;
        if (TextUtils.isEmpty(topListCfg)) {
            topListCfg = "1,2,3,4";
        }
        String str = topListCfg;
        if (str == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        split$default = StringsKt__StringsKt.split$default(str, new String[]{","}, false, 0, 6, null);
        String string = AppUtil.getString(R.string.up_rank_today);
        Iterator it2 = split$default.iterator();
        if (it2.hasNext()) {
            String str2 = (String) it2.next();
            switch (str2.hashCode()) {
                case 49:
                    if (str2.equals("1")) {
                        string = string + AppUtil.getString(R.string.up_rank_income);
                        break;
                    }
                    break;
                case 50:
                    if (str2.equals("2")) {
                        string = string + AppUtil.getString(R.string.up_rank_publish);
                        break;
                    }
                    break;
                case 51:
                    if (str2.equals("3")) {
                        string = string + AppUtil.getString(R.string.up_rank_hot);
                        break;
                    }
                    break;
                case 52:
                    if (str2.equals("4")) {
                        string = string + AppUtil.getString(R.string.up_rank_fans);
                        break;
                    }
                    break;
            }
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.up_rank_title);
        if (textView != null) {
            textView.setText(string);
        }
        ArrayList arrayList = new ArrayList();
        List<CreatorCenterBean.TodayRank> list = creatorCenterBean2.todayTop3;
        if (!(list == null || list.isEmpty())) {
            arrayList.addAll(creatorCenterBean2.todayTop3);
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
        } else if (arrayList.size() == 2) {
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
        } else if (arrayList.size() == 1) {
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
            arrayList.add(new CreatorCenterBean.TodayRank("0", AppUtil.getString(R.string.up_rank_empty_name)));
        }
        BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> baseRecyclerViewAdapter = this.creatorProfitAdapter;
        if (baseRecyclerViewAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("creatorProfitAdapter");
            throw null;
        }
        baseRecyclerViewAdapter.setNewData(arrayList);
        BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> baseRecyclerViewAdapter2 = this.creatorProfitAdapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("creatorProfitAdapter");
            throw null;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unRegisterRxBus();
    }
}
