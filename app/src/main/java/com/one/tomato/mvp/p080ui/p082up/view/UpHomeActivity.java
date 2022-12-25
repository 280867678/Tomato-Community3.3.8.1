package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.dialog.NoticeDialog;
import com.one.tomato.entity.ApplyConditionBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PrivilegeBean;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.p082up.adapter.ApplyUpConditionAdapter;
import com.one.tomato.mvp.p080ui.p082up.adapter.PrivilegeAdapter;
import com.one.tomato.mvp.p080ui.p082up.adapter.PrivilegeOriginAdapter;
import com.one.tomato.mvp.p080ui.p082up.adapter.UpAchievementAdapter;
import com.one.tomato.mvp.p080ui.p082up.adapter.UpRanlImageAdapter;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView;
import com.one.tomato.mvp.p080ui.p082up.presenter.UpHomePresenter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: UpHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity */
/* loaded from: classes3.dex */
public final class UpHomeActivity extends MvpBaseActivity<UpContarct$UpIView, UpHomePresenter> implements UpContarct$UpIView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private ApplyUpConditionAdapter applyAdapter;
    private UpStatusBean info;
    private PrivilegeAdapter normalPrivilegeAdapter;
    private PrivilegeOriginAdapter originalAdapter;
    private UpAchievementAdapter upAchievementAdapter;
    private UpRanlImageAdapter upRankImageAdapter;
    private int applyType = 1;
    private final Functions<Unit> addClick = new UpHomeActivity$addClick$1(this);

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
        return R.layout.activity_up_home;
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: UpHomeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, int i) {
            Intent intent = new Intent(context, UpHomeActivity.class);
            intent.putExtra("type", i);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public UpHomePresenter mo6439createPresenter() {
        return new UpHomePresenter();
    }

    /* JADX WARN: Code restructure failed: missing block: B:91:0x01b7, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0.getUpHostType(), "3") != false) goto L92;
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0389  */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initView() {
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        RecyclerView recyclerView3;
        Context mContext;
        TextPaint paint;
        TextPaint paint2;
        Intent intent = getIntent();
        this.applyType = intent != null ? intent.getIntExtra("type", 1) : 1;
        this.addClick.mo6822invoke();
        TextView textView = (TextView) _$_findCachedViewById(R$id.up_plan);
        if (textView != null && (paint2 = textView.getPaint()) != null) {
            paint2.setFlags(8);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.up_plan);
        if (textView2 != null && (paint = textView2.getPaint()) != null) {
            paint.setAntiAlias(true);
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        ImageLoaderUtil.loadViewPagerOriginImage(getMContext(), (ImageView) _$_findCachedViewById(R$id.image_level), null, new ImageBean(systemParam.getUpCfgPicUrl()), 0);
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView3 != null) {
            textView3.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView4 != null) {
            textView4.setOnTouchListener(UpHomeActivity$initView$1.INSTANCE);
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_rank);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext2;
                    Context mContext3;
                    mContext2 = UpHomeActivity.this.getMContext();
                    Intent intent2 = new Intent(mContext2, UpRankHomeActivity.class);
                    mContext3 = UpHomeActivity.this.getMContext();
                    if (mContext3 != null) {
                        mContext3.startActivity(intent2);
                    }
                }
            });
        }
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpHomeActivity.this.onBackPressed();
                }
            });
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setVerticalSpan(R.dimen.dimen_20);
        builder.setHorizontalSpan(R.dimen.dimen_20);
        builder.setColor(getResources().getColor(R.color.white));
        builder.setShowLastLine(false);
        GridItemDecoration build = builder.build();
        if (this.applyType == 1) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            if (true ^ Intrinsics.areEqual(userInfo.getUpHostType(), "3")) {
                LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.linear_up_plan);
                if (linearLayout != null) {
                    linearLayout.setVisibility(0);
                }
                LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.liner_up_privilege);
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(0);
                }
                LinearLayout linearLayout3 = (LinearLayout) _$_findCachedViewById(R$id.line_original_condition);
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(8);
                }
                ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_original_head_bg);
                if (imageView != null) {
                    imageView.setVisibility(8);
                }
                ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_head_up_bg);
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                Button button = (Button) _$_findCachedViewById(R$id.button);
                if (button != null) {
                    button.setText(AppUtil.getString(R.string.up_home_button_apply));
                }
                this.applyAdapter = new ApplyUpConditionAdapter();
                RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_apply);
                if (recyclerView4 != null) {
                    recyclerView4.setLayoutManager(new GridLayoutManager(getMContext(), 2));
                }
                RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_apply);
                if (recyclerView5 != null) {
                    recyclerView5.setAdapter(this.applyAdapter);
                }
                RecyclerView recyclerView6 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_privilege_1);
                if (recyclerView6 != null) {
                    recyclerView6.setLayoutManager(new GridLayoutManager(getMContext(), 3));
                }
                this.normalPrivilegeAdapter = new PrivilegeAdapter();
                RecyclerView recyclerView7 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_privilege_1);
                if (recyclerView7 != null) {
                    recyclerView7.setAdapter(this.normalPrivilegeAdapter);
                }
                RecyclerView recyclerView8 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_privilege_1);
                if (recyclerView8 != null) {
                    recyclerView8.addItemDecoration(build);
                }
                GridItemDecoration.Builder builder2 = new GridItemDecoration.Builder(getMContext());
                builder2.setVerticalSpan(R.dimen.common_divider);
                builder2.setHorizontalSpan(R.dimen.common_divider);
                builder2.setColor(getResources().getColor(R.color.divider));
                builder2.setShowLastLine(false);
                GridItemDecoration build2 = builder2.build();
                this.upAchievementAdapter = new UpAchievementAdapter();
                recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
                if (recyclerView != null) {
                    recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 3));
                }
                recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
                if (recyclerView2 != null) {
                    recyclerView2.setAdapter(this.upAchievementAdapter);
                }
                recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
                if (recyclerView3 != null) {
                    recyclerView3.addItemDecoration(build2);
                }
                mContext = getMContext();
                if (mContext == null) {
                    this.upRankImageAdapter = new UpRanlImageAdapter(mContext, (RecyclerView) _$_findCachedViewById(R$id.recycler_image));
                    RecyclerView recyclerView9 = (RecyclerView) _$_findCachedViewById(R$id.recycler_image);
                    if (recyclerView9 != null) {
                        recyclerView9.setLayoutManager(new LinearLayoutManager(getMContext(), 0, false));
                    }
                    RecyclerView recyclerView10 = (RecyclerView) _$_findCachedViewById(R$id.recycler_image);
                    if (recyclerView10 != null) {
                        recyclerView10.setAdapter(this.upRankImageAdapter);
                    }
                    UpRanlImageAdapter upRanlImageAdapter = this.upRankImageAdapter;
                    if (upRanlImageAdapter == null) {
                        return;
                    }
                    upRanlImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$initView$4
                        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                        public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                            Context mContext2;
                            Object item = baseQuickAdapter.getItem(i);
                            if (item instanceof UpRankListBean) {
                                NewMyHomePageActivity.Companion companion = NewMyHomePageActivity.Companion;
                                mContext2 = UpHomeActivity.this.getMContext();
                                if (mContext2 != null) {
                                    companion.startActivity(mContext2, ((UpRankListBean) item).getMemberId());
                                } else {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                            }
                        }
                    });
                    return;
                }
                Intrinsics.throwNpe();
                throw null;
            }
        }
        if (this.applyType != 2) {
            UserInfo userInfo2 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button);
        if (button2 != null) {
            button2.setText(AppUtil.getString(R.string.up_apply_original));
        }
        LinearLayout linearLayout4 = (LinearLayout) _$_findCachedViewById(R$id.linear_up_plan);
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
        }
        LinearLayout linearLayout5 = (LinearLayout) _$_findCachedViewById(R$id.line_original_condition);
        if (linearLayout5 != null) {
            linearLayout5.setVisibility(0);
        }
        LinearLayout linearLayout6 = (LinearLayout) _$_findCachedViewById(R$id.liner_up_privilege);
        if (linearLayout6 != null) {
            linearLayout6.setVisibility(8);
        }
        RecyclerView recyclerView11 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_original);
        if (recyclerView11 != null) {
            recyclerView11.setVisibility(0);
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_original_head_bg);
        if (imageView3 != null) {
            imageView3.setVisibility(0);
        }
        ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_head_up_bg);
        if (imageView4 != null) {
            imageView4.setVisibility(8);
        }
        LinearLayout linearLayout7 = (LinearLayout) _$_findCachedViewById(R$id.line_up);
        if (linearLayout7 != null) {
            linearLayout7.setVisibility(8);
        }
        TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_join_tip);
        if (textView5 != null) {
            textView5.setVisibility(0);
        }
        String string = AppUtil.getString(R.string.up_apply_join_tip);
        String string2 = AppUtil.getString(R.string.up_apply_join_tip2);
        SpannableString spannableString = new SpannableString(string + string2);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5B92E1")), string.length(), string.length() + string2.length(), 18);
        TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_join_tip);
        if (textView6 != null) {
            textView6.setText(spannableString);
        }
        GridItemDecoration.Builder builder3 = new GridItemDecoration.Builder(getMContext());
        builder3.setVerticalSpan(R.dimen.dimen_8);
        builder3.setHorizontalSpan(R.dimen.dimen_8);
        builder3.setColor(getResources().getColor(R.color.transparent));
        builder3.setShowLastLine(false);
        GridItemDecoration build3 = builder3.build();
        RecyclerView recyclerView12 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_original);
        if (recyclerView12 != null) {
            recyclerView12.addItemDecoration(build3);
        }
        this.originalAdapter = new PrivilegeOriginAdapter();
        RecyclerView recyclerView13 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_original);
        if (recyclerView13 != null) {
            recyclerView13.setLayoutManager(new GridLayoutManager(getMContext(), 2));
        }
        RecyclerView recyclerView14 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_original);
        if (recyclerView14 != null) {
            recyclerView14.setAdapter(this.originalAdapter);
        }
        GridItemDecoration.Builder builder22 = new GridItemDecoration.Builder(getMContext());
        builder22.setVerticalSpan(R.dimen.common_divider);
        builder22.setHorizontalSpan(R.dimen.common_divider);
        builder22.setColor(getResources().getColor(R.color.divider));
        builder22.setShowLastLine(false);
        GridItemDecoration build22 = builder22.build();
        this.upAchievementAdapter = new UpAchievementAdapter();
        recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
        if (recyclerView != null) {
        }
        recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
        if (recyclerView2 != null) {
        }
        recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view_achievement);
        if (recyclerView3 != null) {
        }
        mContext = getMContext();
        if (mContext == null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        refresh();
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00e1, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1.getUpHostType(), "3") != false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initData() {
        ArrayList arrayList = new ArrayList();
        if (this.applyType == 1) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            if (!Intrinsics.areEqual(userInfo.getUpHostType(), "3")) {
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_priority_review, getString(R.string.up_privilege_review)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_recomment, getString(R.string.up_privilege_recomment)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_tag, getString(R.string.up_privilege_tag)));
                arrayList.add(new PrivilegeBean(R.drawable.up_pirvilege_show, getString(R.string.up_privilege_show)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_reward, getString(R.string.up_privilege_reward)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_down, getString(R.string.up_privilege_down)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_withdraw, getString(R.string.up_privilege_withdraw)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_publish, getString(R.string.up_privilege_publish)));
                arrayList.add(new PrivilegeBean(R.drawable.up_privilege_pay_subscribe, getString(R.string.up_privilege_pay_subscribe)));
                PrivilegeAdapter privilegeAdapter = this.normalPrivilegeAdapter;
                if (privilegeAdapter != null) {
                    privilegeAdapter.addData((Collection) arrayList);
                }
                if (this.applyType == 2) {
                    UserInfo userInfo2 = DBUtil.getUserInfo();
                    Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
                    if (Intrinsics.areEqual(userInfo2.getUpHostType(), "3")) {
                        requestReviewNotice();
                        return;
                    }
                }
                if (this.applyType == 1) {
                    return;
                }
                UserInfo userInfo3 = DBUtil.getUserInfo();
                Intrinsics.checkExpressionValueIsNotNull(userInfo3, "DBUtil.getUserInfo()");
                if (!Intrinsics.areEqual(userInfo3.getUpHostType(), "1")) {
                    UserInfo userInfo4 = DBUtil.getUserInfo();
                    Intrinsics.checkExpressionValueIsNotNull(userInfo4, "DBUtil.getUserInfo()");
                    if (!Intrinsics.areEqual(userInfo4.getUpHostType(), "2")) {
                        return;
                    }
                }
                requestReviewNotice();
                return;
            }
        }
        if (this.applyType != 2) {
            UserInfo userInfo5 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo5, "DBUtil.getUserInfo()");
        }
        arrayList.add(new PrivilegeBean(R.drawable.up_orignal_quanyi, getString(R.string.up_original_qy), getString(R.string.up_original_quanyi_dec)));
        arrayList.add(new PrivilegeBean(R.drawable.up_original_recomment, getString(R.string.up_original_recomment), getString(R.string.up_original_recoment_dec)));
        arrayList.add(new PrivilegeBean(R.drawable.up_original_income, getString(R.string.up_original_income), getString(R.string.up_original_income_dec)));
        arrayList.add(new PrivilegeBean(R.drawable.up_orignal_bs, getString(R.string.up_original_bs), getString(R.string.up_original_bs_dec)));
        PrivilegeOriginAdapter privilegeOriginAdapter = this.originalAdapter;
        if (privilegeOriginAdapter != null) {
            privilegeOriginAdapter.addData((Collection) arrayList);
        }
        if (this.applyType == 2) {
        }
        if (this.applyType == 1) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refresh() {
        String topListCfg;
        String str;
        UpHomePresenter mPresenter;
        List split$default;
        if (this.applyType == 2) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            if (!Intrinsics.areEqual(userInfo.getUpHostType(), "3")) {
                UpHomePresenter mPresenter2 = getMPresenter();
                if (mPresenter2 != null) {
                    mPresenter2.requestQueryUpStatusInfo(1);
                }
                SystemParam systemParam = DBUtil.getSystemParam();
                Intrinsics.checkExpressionValueIsNotNull(systemParam, "systemParam");
                topListCfg = systemParam.getTopListCfg();
                if (TextUtils.isEmpty(topListCfg)) {
                    Intrinsics.checkExpressionValueIsNotNull(topListCfg, "topListCfg");
                    split$default = StringsKt__StringsKt.split$default(topListCfg, new String[]{","}, false, 0, 6, null);
                    str = (String) split$default.get(0);
                } else {
                    str = "1";
                }
                mPresenter = getMPresenter();
                if (mPresenter != null) {
                    return;
                }
                mPresenter.requestUpRankList(3, Integer.parseInt(str));
                return;
            }
        }
        UserInfo userInfo2 = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
        int upLevel = userInfo2.getUpLevel();
        if (1 <= upLevel && 5 >= upLevel) {
            reviewPass();
        } else if (this.applyType == 2) {
            UpHomePresenter mPresenter3 = getMPresenter();
            if (mPresenter3 != null) {
                mPresenter3.requestQueryUpStatusInfo(1);
            }
        } else {
            UpHomePresenter mPresenter4 = getMPresenter();
            if (mPresenter4 != null) {
                mPresenter4.requestQueryUpStatusInfo(0);
            }
        }
        SystemParam systemParam2 = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam2, "systemParam");
        topListCfg = systemParam2.getTopListCfg();
        if (TextUtils.isEmpty(topListCfg)) {
        }
        mPresenter = getMPresenter();
        if (mPresenter != null) {
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryUpStatusInfo(UpStatusBean upStatusBean) {
        initUpInfo(upStatusBean);
    }

    public final void initUpInfo(UpStatusBean upStatusBean) {
        String string;
        if (upStatusBean == null) {
            return;
        }
        boolean z = true;
        if (this.applyType == 1) {
            string = AppUtil.getString(R.string.up_current_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.up_current_num)");
        } else {
            string = AppUtil.getString(R.string.up_original_current_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri….up_original_current_num)");
        }
        Object[] objArr = {FormatUtil.formatNumOverTenThousand(upStatusBean.getCurUpHostCount())};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_current_up_num);
        if (textView != null) {
            textView.setText(format);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setVisibility(0);
        }
        String status = upStatusBean.getStatus();
        int hashCode = status.hashCode();
        if (hashCode != 1444) {
            switch (hashCode) {
                case 48:
                    if (!status.equals("0")) {
                        return;
                    }
                    if (this.applyType == 1) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new ApplyConditionBean(1));
                        arrayList.add(new ApplyConditionBean(2));
                        arrayList.add(new ApplyConditionBean(3));
                        arrayList.add(new ApplyConditionBean(4));
                        ApplyUpConditionAdapter applyUpConditionAdapter = this.applyAdapter;
                        if (applyUpConditionAdapter != null) {
                            applyUpConditionAdapter.setNewData(arrayList);
                        }
                        ApplyUpConditionAdapter applyUpConditionAdapter2 = this.applyAdapter;
                        if (applyUpConditionAdapter2 != null) {
                            applyUpConditionAdapter2.setEnableLoadMore(false);
                        }
                    }
                    waitReview();
                    return;
                case 49:
                    if (!status.equals("1")) {
                        return;
                    }
                    reviewPass();
                    return;
                case 50:
                    if (!status.equals("2")) {
                        return;
                    }
                    break;
                case 51:
                    if (!status.equals("3")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            if (this.applyType == 1) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(new ApplyConditionBean(1));
                arrayList2.add(new ApplyConditionBean(2));
                arrayList2.add(new ApplyConditionBean(3));
                arrayList2.add(new ApplyConditionBean(4));
                ApplyUpConditionAdapter applyUpConditionAdapter3 = this.applyAdapter;
                if (applyUpConditionAdapter3 != null) {
                    applyUpConditionAdapter3.setNewData(arrayList2);
                }
                ApplyUpConditionAdapter applyUpConditionAdapter4 = this.applyAdapter;
                if (applyUpConditionAdapter4 != null) {
                    applyUpConditionAdapter4.setEnableLoadMore(false);
                }
            }
            applyError(upStatusBean);
        } else if (status.equals("-1")) {
            if (this.applyType != 1 && upStatusBean.getOriginalFlag() != 0) {
                return;
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.line_up);
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.line_up_apply);
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            ArrayList<ApplyConditionBean> arrayList3 = new ArrayList();
            arrayList3.add(new ApplyConditionBean(1));
            arrayList3.add(new ApplyConditionBean(2));
            arrayList3.add(new ApplyConditionBean(3));
            arrayList3.add(new ApplyConditionBean(4));
            ApplyUpConditionAdapter applyUpConditionAdapter5 = this.applyAdapter;
            if (applyUpConditionAdapter5 != null) {
                applyUpConditionAdapter5.setNewData(arrayList3);
            }
            ApplyUpConditionAdapter applyUpConditionAdapter6 = this.applyAdapter;
            if (applyUpConditionAdapter6 != null) {
                applyUpConditionAdapter6.setEnableLoadMore(false);
            }
            for (ApplyConditionBean applyConditionBean : arrayList3) {
                if (applyConditionBean.getCurrentCount() < applyConditionBean.getAllCount()) {
                    z = false;
                }
            }
            if (z) {
                Button button2 = (Button) _$_findCachedViewById(R$id.button);
                if (button2 != null) {
                    button2.setText(AppUtil.getString(R.string.up_home_button_apply));
                }
            } else {
                Button button3 = (Button) _$_findCachedViewById(R$id.button);
                if (button3 != null) {
                    button3.setText(AppUtil.getString(R.string.up_home_button_no_apply));
                }
            }
            Button button4 = (Button) _$_findCachedViewById(R$id.button);
            if (button4 == null) {
                return;
            }
            button4.setEnabled(z);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerRankList(ArrayList<UpRankListBean> arrayList) {
        if (arrayList != null) {
            if (arrayList.size() > 10) {
                List<UpRankListBean> subList = arrayList.subList(0, 9);
                Intrinsics.checkExpressionValueIsNotNull(subList, "it.subList(0, 9)");
                UpRanlImageAdapter upRanlImageAdapter = this.upRankImageAdapter;
                if (upRanlImageAdapter != null) {
                    upRanlImageAdapter.setNewData(subList);
                }
                UpRanlImageAdapter upRanlImageAdapter2 = this.upRankImageAdapter;
                if (upRanlImageAdapter2 == null) {
                    return;
                }
                upRanlImageAdapter2.setEnableLoadMore(false);
                return;
            }
            UpRanlImageAdapter upRanlImageAdapter3 = this.upRankImageAdapter;
            if (upRanlImageAdapter3 != null) {
                upRanlImageAdapter3.setNewData(arrayList);
            }
            UpRanlImageAdapter upRanlImageAdapter4 = this.upRankImageAdapter;
            if (upRanlImageAdapter4 == null) {
                return;
            }
            upRanlImageAdapter4.setEnableLoadMore(false);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyUpSuccess() {
        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_apply_comit_suc));
        waitReview();
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyError() {
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setEnabled(true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryAchiSucess(UpStatusBean upStatusBean) {
        String string;
        if (upStatusBean == null) {
            return;
        }
        this.info = upStatusBean;
        if (this.applyType == 1) {
            string = AppUtil.getString(R.string.up_current_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.up_current_num)");
        } else {
            string = AppUtil.getString(R.string.up_original_current_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri….up_original_current_num)");
        }
        Object[] objArr = {FormatUtil.formatNumOverTenThousand(upStatusBean.getCurUpHostCount())};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_current_up_num);
        if (textView != null) {
            textView.setText(format);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ApplyConditionBean(upStatusBean.getTotalIncome(), getString(R.string.up_achi_all_tumb)));
        arrayList.add(new ApplyConditionBean(upStatusBean.getCurArticleCount(), getString(R.string.up_achi_post_num)));
        arrayList.add(new ApplyConditionBean(upStatusBean.getTodayViewedCount(), getString(R.string.up_achi_today_browse)));
        arrayList.add(new ApplyConditionBean(upStatusBean.getCurViewedCount(), getString(R.string.up_achi_all_browse)));
        arrayList.add(new ApplyConditionBean(upStatusBean.getCurFansCount(), getString(R.string.up_achi_my_fans_num)));
        arrayList.add(new ApplyConditionBean(upStatusBean.getSubscribeCount(), getString(R.string.up_achi_my_obsever_num)));
        UpAchievementAdapter upAchievementAdapter = this.upAchievementAdapter;
        if (upAchievementAdapter != null) {
            upAchievementAdapter.setNewData(arrayList);
        }
        UpAchievementAdapter upAchievementAdapter2 = this.upAchievementAdapter;
        if (upAchievementAdapter2 != null) {
            upAchievementAdapter2.setEnableLoadMore(false);
        }
        if (upStatusBean.getLevel() == 1) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView != null) {
                imageView.setImageResource(R.drawable.my_up_y1);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.my_up_y1));
            }
        } else if (upStatusBean.getLevel() == 2) {
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.my_up_y2);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.my_up_y2));
            }
        } else if (upStatusBean.getLevel() == 3) {
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView3 != null) {
                imageView3.setImageResource(R.drawable.my_up_y3_v);
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.my_up_y3));
            }
        } else if (upStatusBean.getLevel() == 4) {
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView4 != null) {
                imageView4.setImageResource(R.drawable.my_up_y4);
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView5 != null) {
                textView5.setText(AppUtil.getString(R.string.my_up_y4));
            }
        } else if (upStatusBean.getLevel() == 5) {
            ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView5 != null) {
                imageView5.setImageResource(R.drawable.my_up_y5);
            }
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView6 != null) {
                textView6.setText(AppUtil.getString(R.string.my_up_y5));
            }
        }
        if (upStatusBean.getOriginalFlag() == 1) {
            ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView6 != null) {
                imageView6.setImageResource(R.drawable.up_original);
            }
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_up_le);
            if (textView7 != null) {
                textView7.setText(AppUtil.getString(R.string.up_original));
            }
        }
        TextView textView8 = (TextView) _$_findCachedViewById(R$id.my_income_text);
        if (textView8 != null) {
            textView8.setText(FormatUtil.formatTomato2RMB(upStatusBean.getProfit()));
        }
        String canProfit = AppUtil.getString(R.string.up_my_amount);
        Intrinsics.checkExpressionValueIsNotNull(canProfit, "canProfit");
        Object[] objArr2 = {upStatusBean.getCanWithdrawProfit()};
        String format2 = String.format(canProfit, Arrays.copyOf(objArr2, objArr2.length));
        Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
        TextView textView9 = (TextView) _$_findCachedViewById(R$id.my_withdraw_amount);
        if (textView9 != null) {
            textView9.setText(format2);
        }
        String formatTime = FormatUtil.formatTime("yyyy-MM-dd", upStatusBean.getCertificateTime());
        TextView textView10 = (TextView) _$_findCachedViewById(R$id.text_apply_time);
        if (textView10 != null) {
            textView10.setText(AppUtil.getString(R.string.up_apply_time) + ' ' + formatTime);
        }
        if (upStatusBean.getSubscribeSwitch() == 1) {
            TextView textView11 = (TextView) _$_findCachedViewById(R$id.text_no_set_subscribe);
            if (textView11 != null) {
                textView11.setVisibility(8);
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.linear_set_subscribe);
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            String string2 = AppUtil.getString(R.string.up_observer_yuan);
            Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.up_observer_yuan)");
            SpannableString colorString = colorString(string2);
            TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_month);
            if (textView12 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(upStatusBean.getPriceMonth());
                sb.append((Object) colorString);
                textView12.setText(sb.toString());
            }
            String string3 = AppUtil.getString(R.string.up_observer_season);
            Intrinsics.checkExpressionValueIsNotNull(string3, "AppUtil.getString(R.string.up_observer_season)");
            SpannableString colorString2 = colorString(string3);
            TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_quarterly);
            if (textView13 != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(upStatusBean.getPriceSeason());
                sb2.append((Object) colorString2);
                textView13.setText(sb2.toString());
            }
            String string4 = AppUtil.getString(R.string.up_observer_year);
            Intrinsics.checkExpressionValueIsNotNull(string4, "AppUtil.getString(R.string.up_observer_year)");
            SpannableString colorString3 = colorString(string4);
            TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_year);
            if (textView14 == null) {
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(upStatusBean.getPriceYear());
            sb3.append((Object) colorString3);
            textView14.setText(sb3.toString());
            return;
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.linear_set_subscribe);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_no_set_subscribe);
        if (textView15 == null) {
            return;
        }
        textView15.setVisibility(0);
    }

    private final SpannableString colorString(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3C3C3C")), 0, str.length(), 18);
        return spannableString;
    }

    public final void waitReview() {
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.line_up_apply);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.line_up);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setEnabled(false);
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button);
        if (button2 != null) {
            button2.setText(AppUtil.getString(R.string.up_apply_commit_button));
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
        if (textView != null) {
            textView.setVisibility(0);
        }
        if (this.applyType == 2) {
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.up_apply_original_reviewing));
            }
        } else {
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.up_apply_reviewing));
            }
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView4 != null) {
            textView4.setVisibility(0);
        }
        TextView textView5 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView5 != null) {
            textView5.setText(AppUtil.getString(R.string.up_apply_review_prompt));
        }
    }

    public final void applyError(UpStatusBean info) {
        Intrinsics.checkParameterIsNotNull(info, "info");
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.line_up_apply);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.line_up);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setEnabled(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
        if (textView != null) {
            textView.setVisibility(0);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.up_apply_failure_reason);
        if (textView3 != null) {
            textView3.setText(info.getRejectReason());
        }
        if (Intrinsics.areEqual(info.getStatus(), "2")) {
            if (this.applyType == 2) {
                TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
                if (textView4 != null) {
                    textView4.setText(AppUtil.getString(R.string.up_apply_original_error_tishi));
                }
            } else {
                TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
                if (textView5 != null) {
                    textView5.setText(AppUtil.getString(R.string.up_apply_error_tishi));
                }
            }
        } else if (Intrinsics.areEqual(info.getStatus(), "3")) {
            if (this.applyType == 2) {
                TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
                if (textView6 != null) {
                    textView6.setText(AppUtil.getString(R.string.up_apply_original_cancle));
                }
            } else {
                TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_apply_failure);
                if (textView7 != null) {
                    textView7.setText(AppUtil.getString(R.string.up_apply_cancle));
                }
            }
        }
        int remainDays = info.getRemainDays();
        if (remainDays == 0) {
            Button button2 = (Button) _$_findCachedViewById(R$id.button);
            if (button2 != null) {
                button2.setText(AppUtil.getString(R.string.up_again_apply));
            }
            Button button3 = (Button) _$_findCachedViewById(R$id.button);
            if (button3 == null) {
                return;
            }
            button3.setEnabled(true);
            return;
        }
        String string = AppUtil.getString(R.string.up_apply_error);
        Button button4 = (Button) _$_findCachedViewById(R$id.button);
        if (button4 == null) {
            return;
        }
        Intrinsics.checkExpressionValueIsNotNull(string, "string");
        Object[] objArr = {Integer.valueOf(remainDays)};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        button4.setText(format);
    }

    public final void reviewPass() {
        UpHomePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestQueryAchievement();
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.line_up);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.line_up_apply);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setVisibility(8);
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_goin_up);
        if (button2 != null) {
            button2.setVisibility(0);
        }
    }

    private final void requestReviewNotice() {
        ApiImplService.Companion.getApiImplService().requestReviewNotice("6").compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$requestReviewNotice$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpHomeActivity.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$requestReviewNotice$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<MainNotifyBean> arrayList) {
                Context mContext;
                UpHomeActivity.this.dismissDialog();
                if (!(arrayList == null || arrayList.isEmpty())) {
                    mContext = UpHomeActivity.this.getMContext();
                    new NoticeDialog(mContext, arrayList.get(0)).setTv_title_labelText(AppUtil.getString(R.string.post_up_notice));
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpHomeActivity.this.dismissDialog();
            }
        });
    }
}
