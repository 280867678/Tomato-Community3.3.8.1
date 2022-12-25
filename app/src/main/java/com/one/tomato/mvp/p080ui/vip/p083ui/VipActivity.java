package com.one.tomato.mvp.p080ui.vip.p083ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.CardView;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eclipsesource.p056v8.Platform;
import com.google.gson.Gson;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.VipRechargeDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.RechargeOrder;
import com.one.tomato.entity.RechargeOrderConfirm;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.entity.VipPackage;
import com.one.tomato.entity.VipRight;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.RecommentActivity;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipRightActivity;
import com.one.tomato.thirdpart.p084jc.JCRechargeUtil;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.VideoDownloadCountUtils;
import com.security.sdk.open.RSAEncrypt;
import com.security.sdk.open.Security;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* compiled from: VipActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.ui.VipActivity */
/* loaded from: classes3.dex */
public final class VipActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean isGoPay;
    private boolean isQuery;
    private int packageSelect;
    private RechargeOrder rechargeOrder;
    private RecommentActivity recommentActivity;
    private SystemParam systemParam;
    private VipPackage vipPackage;

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
        return R.layout.activity_vip;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    /* compiled from: VipActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.vip.ui.VipActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, VipActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        initVipData();
        initVipRight();
        requestVipPackage();
        addListener();
    }

    private final void initVipData() {
        this.systemParam = DBUtil.getSystemParam();
        Gson gson = BaseApplication.getGson();
        SystemParam systemParam = this.systemParam;
        this.recommentActivity = (RecommentActivity) gson.fromJson(systemParam != null ? systemParam.getActivityJson() : null, (Class<Object>) RecommentActivity.class);
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        ImageLoaderUtil.loadHeadImage(this, (ImageView) _$_findCachedViewById(R$id.iv_head), new ImageBean(userInfo.getAvatar()));
        ((TextView) _$_findCachedViewById(R$id.tv_user_name)).setText(userInfo.getName());
        if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
            if (userInfo.getVipType() == 1) {
                ((ImageView) _$_findCachedViewById(R$id.iv_vip_bg)).setImageResource(R.drawable.vip_mid_bg_y);
                ((TextView) _$_findCachedViewById(R$id.tv_user_vip_status)).setText(R.string.vip_package_agent);
                ((ImageView) _$_findCachedViewById(R$id.iv_user_vip_status)).setImageResource(R.drawable.vip_tag_y);
                ((TextView) _$_findCachedViewById(R$id.tv_user_vip_expire)).setText(AppUtil.getString(R.string.vip_status_expire_suffix) + AppUtil.getString(R.string.vip_status_expire_forever));
                TextView tv_money = (TextView) _$_findCachedViewById(R$id.tv_money);
                Intrinsics.checkExpressionValueIsNotNull(tv_money, "tv_money");
                tv_money.setVisibility(8);
                TextView tv_open_vip = (TextView) _$_findCachedViewById(R$id.tv_open_vip);
                Intrinsics.checkExpressionValueIsNotNull(tv_open_vip, "tv_open_vip");
                tv_open_vip.setVisibility(8);
                TextView tv_vip_forever = (TextView) _$_findCachedViewById(R$id.tv_vip_forever);
                Intrinsics.checkExpressionValueIsNotNull(tv_vip_forever, "tv_vip_forever");
                tv_vip_forever.setVisibility(0);
            } else {
                ((ImageView) _$_findCachedViewById(R$id.iv_vip_bg)).setImageResource(R.drawable.vip_mid_bg_y);
                ((TextView) _$_findCachedViewById(R$id.tv_user_vip_status)).setText(R.string.vip_open_y);
                ((ImageView) _$_findCachedViewById(R$id.iv_user_vip_status)).setImageResource(R.drawable.vip_tag_y);
                ((TextView) _$_findCachedViewById(R$id.tv_user_vip_expire)).setText(AppUtil.getString(R.string.vip_status_expire_suffix) + AppUtil.getString(R.string.vip_status_expire_time, userInfo.getExpireTime()));
            }
        } else {
            ((ImageView) _$_findCachedViewById(R$id.iv_vip_bg)).setImageResource(R.drawable.vip_mid_bg_n);
            ((TextView) _$_findCachedViewById(R$id.tv_user_vip_status)).setText(R.string.vip_status_common_user);
            ((ImageView) _$_findCachedViewById(R$id.iv_user_vip_status)).setImageResource(R.drawable.vip_tag_n);
            ((TextView) _$_findCachedViewById(R$id.tv_user_vip_expire)).setText(AppUtil.getString(R.string.vip_status_expire_suffix) + AppUtil.getString(R.string.vip_status_expire_not_ope));
        }
        if (Intrinsics.compare(userInfo.getVipType(), 0) == 1) {
            VideoDownloadCountUtils videoDownloadCountUtils = VideoDownloadCountUtils.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils, "VideoDownloadCountUtils.getInstance()");
            if (Intrinsics.compare(videoDownloadCountUtils.getVipSaveCount(), 0) == 0) {
                ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_download_count), new String[]{AppUtil.getString(R.string.vip_free_save_local_count), "0", AppUtil.getString(R.string.vip_free_save_local_count_unit)}, new String[]{String.valueOf(Color.parseColor("#969696")), String.valueOf(Color.parseColor("#FF5252")), String.valueOf(Color.parseColor("#969696"))}, new String[]{"14", "14", "14"});
            } else {
                VideoDownloadCountUtils videoDownloadCountUtils2 = VideoDownloadCountUtils.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(videoDownloadCountUtils2, "VideoDownloadCountUtils.getInstance()");
                ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_download_count), new String[]{AppUtil.getString(R.string.vip_free_save_local_count), String.valueOf(videoDownloadCountUtils2.getVipSaveCount()), AppUtil.getString(R.string.vip_free_save_local_count_unit)}, new String[]{String.valueOf(Color.parseColor("#323232")), String.valueOf(Color.parseColor("#323232")), String.valueOf(Color.parseColor("#323232"))}, new String[]{"14", "14", "14"});
            }
        } else {
            ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_download_count), new String[]{AppUtil.getString(R.string.vip_free_save_local_count), "0", AppUtil.getString(R.string.vip_free_save_local_count_unit)}, new String[]{String.valueOf(Color.parseColor("#969696")), String.valueOf(Color.parseColor("#969696")), String.valueOf(Color.parseColor("#969696"))}, new String[]{"14", "14", "14"});
            ((TextView) _$_findCachedViewById(R$id.tv_download_count)).setTextColor(getResources().getColor(R.color.text_light));
        }
        updateSelectPackagePrice(0);
    }

    private final void initVipRight() {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(new VipRight(R.drawable.vip_right_look_video, R.string.vip_power1));
        arrayList.add(new VipRight(R.drawable.vip_right_cache_video, R.string.vip_power2));
        arrayList.add(new VipRight(R.drawable.vip_right_download_video, R.string.vip_power3));
        arrayList.add(new VipRight(R.drawable.vip_right_publish, R.string.vip_power4));
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_vip);
        BaseRecyclerViewAdapter<VipRight> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<VipRight>(this, arrayList, mContext, R.layout.item_vip_right, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$initVipRight$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(mContext, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, VipRight itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(itemData.resId);
                ((TextView) holder.getView(R.id.tv_title)).setText(itemData.strId);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                VipRightActivity.Companion companion = VipRightActivity.Companion;
                Context mContext2 = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext2, "mContext");
                companion.startActivity(mContext2);
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_vip)).setLayoutManager(new FullyGridLayoutManager(getMContext(), 4));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_vip)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestVipPackage() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestVipPackage().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<ArrayList<VipPackage>>() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$requestVipPackage$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<VipPackage> t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                VipActivity.this.initVipPackage(t, true);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                VipActivity.this.initVipPackage(null, false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initVipPackage(final ArrayList<VipPackage> arrayList, boolean z) {
        hideWaitingDialog();
        final UserInfo userInfo = DBUtil.getUserInfo();
        if (arrayList != null) {
            this.packageSelect = arrayList.size() - 1;
        }
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_vip);
        BaseRecyclerViewAdapter<VipPackage> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<VipPackage>(userInfo, arrayList, mContext, R.layout.item_vip_package, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$initVipPackage$adapter$1
            final /* synthetic */ UserInfo $userInfo;

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(mContext, r5, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, VipPackage itemData) {
                int i;
                RecommentActivity recommentActivity;
                String str;
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                int indexOf = this.mData.indexOf(itemData);
                ConstraintLayout constraintLayout = (ConstraintLayout) holder.getView(R.id.cl_root);
                ImageView iv_select = (ImageView) holder.getView(R.id.iv_select);
                TextView textView = (TextView) holder.getView(R.id.tv_cur_price);
                TextView tv_origin_price = (TextView) holder.getView(R.id.tv_origin_price);
                ImageView iv_activity = (ImageView) holder.getView(R.id.iv_activity);
                ((TextView) holder.getView(R.id.tv_title)).setText(itemData.name);
                ((TextView) holder.getView(R.id.tv_content)).setText(itemData.description);
                if (itemData.originalPrice == 0) {
                    Intrinsics.checkExpressionValueIsNotNull(tv_origin_price, "tv_origin_price");
                    tv_origin_price.setVisibility(4);
                } else {
                    Intrinsics.checkExpressionValueIsNotNull(tv_origin_price, "tv_origin_price");
                    tv_origin_price.setVisibility(0);
                    tv_origin_price.setText(AppUtil.getString(R.string.vip_money_origin, Integer.valueOf(itemData.originalPrice / 100)));
                }
                textView.setText(AppUtil.getString(R.string.vip_money_cur, Integer.valueOf(itemData.actualPrice / 100)));
                UserInfo userInfo2 = this.$userInfo;
                Intrinsics.checkExpressionValueIsNotNull(userInfo2, "userInfo");
                if (userInfo2.getVipType() != 1 || itemData.days != Integer.MAX_VALUE) {
                    i = VipActivity.this.packageSelect;
                    if (i == indexOf) {
                        Intrinsics.checkExpressionValueIsNotNull(iv_select, "iv_select");
                        iv_select.setVisibility(0);
                        constraintLayout.setBackgroundResource(R.drawable.vip_package_selected_bg);
                    } else {
                        Intrinsics.checkExpressionValueIsNotNull(iv_select, "iv_select");
                        iv_select.setVisibility(8);
                        constraintLayout.setBackgroundResource(0);
                    }
                } else {
                    Intrinsics.checkExpressionValueIsNotNull(iv_select, "iv_select");
                    iv_select.setVisibility(0);
                    constraintLayout.setBackgroundResource(R.drawable.vip_package_selected_bg);
                }
                if (itemData.showActivityPic) {
                    Intrinsics.checkExpressionValueIsNotNull(iv_activity, "iv_activity");
                    iv_activity.setVisibility(0);
                    Context context = this.mContext;
                    recommentActivity = VipActivity.this.recommentActivity;
                    if (recommentActivity == null || (str = recommentActivity.picUrl) == null) {
                        str = "";
                    }
                    ImageLoaderUtil.loadSecImage(context, iv_activity, new ImageBean(str), ImageLoaderUtil.getCenterInsideImageOption(iv_activity));
                    return;
                }
                Intrinsics.checkExpressionValueIsNotNull(iv_activity, "iv_activity");
                iv_activity.setVisibility(8);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                VipActivity.this.requestVipPackage();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                VipPackage vipPackage;
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                getItem(i);
                UserInfo userInfo2 = this.$userInfo;
                Intrinsics.checkExpressionValueIsNotNull(userInfo2, "userInfo");
                if (userInfo2.getVipType() != 1) {
                    VipActivity.this.vipPackage = (VipPackage) this.mData.get(i);
                    VipActivity.this.packageSelect = i;
                    notifyDataSetChanged();
                    VipActivity vipActivity = VipActivity.this;
                    vipPackage = vipActivity.vipPackage;
                    vipActivity.updateSelectPackagePrice(vipPackage != null ? Integer.valueOf(vipPackage.actualPrice) : null);
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.vip_open_vip_forever1);
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_package)).setLayoutManager(new BaseLinearLayoutManager(getMContext()));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_package)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
        RecyclerView recyclerView_package = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_package);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_package, "recyclerView_package");
        recyclerView_package.setNestedScrollingEnabled(false);
        Integer num = null;
        if (!z) {
            baseRecyclerViewAdapter.setEmptyViewState(1, null);
        } else if (arrayList == null) {
        } else {
            this.vipPackage = arrayList.get(this.packageSelect);
            VipPackage vipPackage = this.vipPackage;
            if (vipPackage != null) {
                num = Integer.valueOf(vipPackage.actualPrice);
            }
            updateSelectPackagePrice(num);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSelectPackagePrice(Integer num) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_money);
        String[] strArr = new String[2];
        strArr[0] = AppUtil.getString(R.string.vip_money_total);
        Object[] objArr = new Object[1];
        objArr[0] = num != null ? Integer.valueOf(num.intValue() / 100) : null;
        strArr[1] = AppUtil.getString(R.string.vip_money_cur, objArr);
        ViewUtil.initTextViewWithSpannableString(textView, strArr, new String[]{String.valueOf(Color.parseColor("#646464")), String.valueOf(Color.parseColor("#C88717"))}, new String[]{"16", "24"});
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_vip_history)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipHistoryActivity.Companion.startActivity(VipActivity.this);
            }
        });
        ((CardView) _$_findCachedViewById(R$id.card_vip)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                VipRightActivity.Companion companion = VipRightActivity.Companion;
                mContext = VipActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_open_vip)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipPackage vipPackage;
                Context mContext;
                vipPackage = VipActivity.this.vipPackage;
                if (vipPackage == null) {
                    ToastUtil.showCenterToast((int) R.string.vip_open_select);
                    return;
                }
                LoginInfo loginInfo = DBUtil.getLoginInfo();
                Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
                if (!loginInfo.isLogin()) {
                    mContext = VipActivity.this.getMContext();
                    final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
                    customAlertDialog.setCanceledOnTouchOutside(true);
                    customAlertDialog.setTitle(R.string.recharge_login_tip_title);
                    customAlertDialog.setMessage(R.string.recharge_login_tip_message);
                    customAlertDialog.setCancelButton(R.string.recharge_login_tip_cancel_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$4.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            Context mContext2;
                            customAlertDialog.dismiss();
                            LoginActivity.Companion companion = LoginActivity.Companion;
                            mContext2 = VipActivity.this.getMContext();
                            if (mContext2 != null) {
                                companion.startActivity(mContext2);
                            } else {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                        }
                    });
                    customAlertDialog.setConfirmButton(R.string.recharge_login_tip_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$4.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            customAlertDialog.dismiss();
                            VipActivity.this.recharge();
                        }
                    });
                    return;
                }
                VipActivity.this.recharge();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_order_history)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                JCRechargeUtil.startOrderList(VipActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_order_feedback)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FeedbackRechargeIssuesActivity.Companion.startActivity(VipActivity.this, 1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recharge() {
        String str;
        VipPackage vipPackage = this.vipPackage;
        if (TextUtils.isEmpty(vipPackage != null ? vipPackage.payMethod : null)) {
            VipPackage vipPackage2 = this.vipPackage;
            String str2 = vipPackage2 != null ? vipPackage2.skuId : null;
            VipPackage vipPackage3 = this.vipPackage;
            Integer valueOf = vipPackage3 != null ? Integer.valueOf(vipPackage3.actualPrice) : null;
            if (valueOf != null) {
                JCRechargeUtil.createOrder(this, str2, valueOf.intValue());
                return;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        ArrayList arrayList = new ArrayList();
        VipPackage vipPackage4 = this.vipPackage;
        List<String> split = (vipPackage4 == null || (str = vipPackage4.payMethod) == null) ? null : new Regex(",").split(str, 5);
        if (split == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        arrayList.addAll(split);
        VipPackage vipPackage5 = this.vipPackage;
        Integer valueOf2 = vipPackage5 != null ? Integer.valueOf(vipPackage5.vipDay) : null;
        if (valueOf2 == null) {
            Intrinsics.throwNpe();
            throw null;
        } else {
            new VipRechargeDialog(this, arrayList, valueOf2.intValue()).setVipRechargeDialogListener(new VipRechargeDialog.VipRechargeDialogListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$recharge$1
                @Override // com.one.tomato.dialog.VipRechargeDialog.VipRechargeDialogListener
                public final void onItemClick(String it2) {
                    VipPackage vipPackage6;
                    VipPackage vipPackage7;
                    if (it2 != null && it2.hashCode() == 316018012 && it2.equals(RechargeTypeAndMoney.RECHARGE_AGENT)) {
                        VipActivity vipActivity = VipActivity.this;
                        vipPackage6 = vipActivity.vipPackage;
                        String str3 = vipPackage6 != null ? vipPackage6.skuId : null;
                        vipPackage7 = VipActivity.this.vipPackage;
                        Integer valueOf3 = vipPackage7 != null ? Integer.valueOf(vipPackage7.actualPrice) : null;
                        if (valueOf3 != null) {
                            JCRechargeUtil.createOrder(vipActivity, str3, valueOf3.intValue());
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    VipActivity vipActivity2 = VipActivity.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    vipActivity2.requestRechargeOnline(it2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestRechargeOnline(String str) {
        if (!Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_PTPAY, str) || !AppUtil.showPtInstallDialog(getMContext())) {
            showWaitingDialog();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            VipPackage vipPackage = this.vipPackage;
            linkedHashMap.put("settingId", vipPackage != null ? vipPackage.f1744id : null);
            Security security = Security.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(security, "Security.getInstance()");
            linkedHashMap.put("metadata", security.getSec());
            linkedHashMap.put("payType", str);
            linkedHashMap.put("platform", Platform.ANDROID);
            linkedHashMap.put("deviceNo", DeviceInfoUtil.getUniqueDeviceID());
            linkedHashMap.put("orderCode", "P002");
            if (Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_PTPAY, str)) {
                linkedHashMap.put("redirectUrl", "tomato://pt/recharge/vip");
            }
            ApiImplService.Companion.getApiImplService().requestRechargeOnline(linkedHashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new VipActivity$requestRechargeOnline$1(this, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openPayPlatform(String str) {
        String decrypt = RSAEncrypt.decrypt(str);
        String tag = getTAG();
        LogUtil.m3785e(tag, "url>>" + str);
        String tag2 = getTAG();
        LogUtil.m3785e(tag2, "decodeUrl>>" + decrypt);
        AppUtil.startBrowseView(decrypt);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isGoPay) {
            confirmOnlineOrder();
        }
    }

    private final void confirmOnlineOrder() {
        if (this.rechargeOrder != null && !this.isQuery) {
            this.isQuery = true;
            showWaitingDialog();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            RechargeOrder rechargeOrder = this.rechargeOrder;
            linkedHashMap.put("orderId", rechargeOrder != null ? rechargeOrder.getOrderId() : null);
            ApiImplService.Companion.getApiImplService().requestRechargeOnlineConfirm(linkedHashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeOrderConfirm>() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$confirmOnlineOrder$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(RechargeOrderConfirm rechargeOrderConfirm) {
                    Context mContext;
                    VipActivity.this.isGoPay = false;
                    VipActivity.this.isQuery = false;
                    VipActivity.this.hideWaitingDialog();
                    if (rechargeOrderConfirm != null && rechargeOrderConfirm.getStatus() == 1) {
                        ToastUtil.showCenterToast((int) R.string.recharge_success_message);
                        VipActivity.this.onBackPressed();
                    } else if (rechargeOrderConfirm != null && rechargeOrderConfirm.getStatus() == 0) {
                        mContext = VipActivity.this.getMContext();
                        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
                        customAlertDialog.bottomButtonVisiblity(2);
                        customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
                        customAlertDialog.setMessage(AppUtil.getString(R.string.recharge_order_delay));
                        customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_dialog_ok), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$confirmOnlineOrder$1$onResult$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                CustomAlertDialog.this.dismiss();
                            }
                        });
                    } else if (rechargeOrderConfirm == null || rechargeOrderConfirm.getStatus() != 2) {
                    } else {
                        ToastUtil.showCenterToast((int) R.string.recharge_fail_message);
                        VipActivity.this.showRechargeFailDialog();
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    VipActivity.this.hideWaitingDialog();
                    VipActivity.this.isGoPay = false;
                    VipActivity.this.isQuery = false;
                    VipActivity.this.showRechargeFailDialog();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showRechargeFailDialog() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getMContext());
        customAlertDialog.bottomButtonVisiblity(2);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(getResources().getString(R.string.recharge_order_query_fail_message));
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$showRechargeFailDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
    }
}
