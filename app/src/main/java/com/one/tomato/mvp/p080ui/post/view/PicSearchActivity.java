package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.JAVDBBean;
import com.one.tomato.entity.JavActionBean;
import com.one.tomato.entity.JavDBSysteamBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.post.adapter.JavDBAdapter;
import com.one.tomato.mvp.p080ui.post.view.PicSearchResultActivity;
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PicSearchActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PicSearchActivity */
/* loaded from: classes3.dex */
public final class PicSearchActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private JavDBAdapter hotAdapter;
    private JavDBSysteamBean javDBSysteamBean;
    private ArrayList<LocalMedia> selectList;
    private SelectPicTypeUtil selectPicTypeUtil;

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
        return R.layout.activity_pic_serach;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PicSearchActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PicSearchActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, PicSearchActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        int indexOf$default;
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(AppUtil.getString(R.string.pic_search_title));
        }
        SpannableString spannableString = new SpannableString(AppUtil.getString(R.string.pic_search_3));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5B92E1"));
        indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) spannableString, "B", 0, false, 6, (Object) null);
        spannableString.setSpan(foregroundColorSpan, 1, indexOf$default + 1, 18);
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_provide);
        if (textView != null) {
            textView.setText(spannableString);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_provide);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PicSearchActivity.this.jumpJavDBWeb();
                }
            });
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        String javDbParameter = systemParam.getJavDbParameter();
        if (!TextUtils.isEmpty(javDbParameter)) {
            this.javDBSysteamBean = (JavDBSysteamBean) BaseApplication.gson.fromJson(javDbParameter, (Class<Object>) JavDBSysteamBean.class);
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_search);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SelectPicTypeUtil selectPicTypeUtil;
                    ArrayList arrayList;
                    PicSearchActivity.this.selectList = new ArrayList();
                    PicSearchActivity picSearchActivity = PicSearchActivity.this;
                    picSearchActivity.selectPicTypeUtil = new SelectPicTypeUtil(picSearchActivity);
                    selectPicTypeUtil = PicSearchActivity.this.selectPicTypeUtil;
                    if (selectPicTypeUtil != null) {
                        arrayList = PicSearchActivity.this.selectList;
                        selectPicTypeUtil.selectCommonPhoto(1, false, false, false, arrayList);
                    }
                }
            });
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_8);
        builder.setVerticalSpan(R.dimen.dimen_16);
        GridItemDecoration build = builder.build();
        this.hotAdapter = new JavDBAdapter((RecyclerView) _$_findCachedViewById(R$id.hot_recycler), getMContext());
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        if (recyclerView != null) {
            recyclerView.addItemDecoration(build);
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new GridLayoutManager(getMContext(), 3));
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.hotAdapter);
        }
        JavDBAdapter javDBAdapter = this.hotAdapter;
        if (javDBAdapter != null) {
            javDBAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchActivity$initView$3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.JAVDBBean.ActorsBean");
                    }
                    PicSearchActivity.this.jumpHtml((JAVDBBean.ActorsBean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void jumpHtml(JAVDBBean.ActorsBean actorsBean) {
        HtmlConfig htmlConfig = new HtmlConfig();
        htmlConfig.setUrl(actorsBean.getUrl());
        HtmlShowActivity.startActivity(getMContext(), htmlConfig);
        reportJAVDB(actorsBean);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        requestJAVDB30();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        LocalMedia localMedia;
        LocalMedia localMedia2;
        String compressPath;
        super.onActivityResult(i, i2, intent);
        if (i == 188) {
            ArrayList<LocalMedia> arrayList = this.selectList;
            if (arrayList != null) {
                arrayList.addAll(PictureSelector.obtainMultipleResult(intent));
            }
            ArrayList<LocalMedia> arrayList2 = this.selectList;
            if (arrayList2 == null || arrayList2.isEmpty()) {
                return;
            }
            PicSearchResultActivity.Companion companion = PicSearchResultActivity.Companion;
            Context mContext = getMContext();
            String str = null;
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            ArrayList<LocalMedia> arrayList3 = this.selectList;
            if (arrayList3 == null || (localMedia2 = arrayList3.get(0)) == null || (compressPath = localMedia2.getCompressPath()) == null) {
                ArrayList<LocalMedia> arrayList4 = this.selectList;
                if (arrayList4 != null && (localMedia = arrayList4.get(0)) != null) {
                    str = localMedia.getPath();
                }
            } else {
                str = compressPath;
            }
            if (str == null) {
                str = "";
            }
            companion.startAct(mContext, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void jumpJavDBWeb() {
        String string = PreferencesUtil.getInstance().getString("javdb_official_url");
        if (TextUtils.isEmpty(string)) {
            JavDBSysteamBean javDBSysteamBean = this.javDBSysteamBean;
            string = javDBSysteamBean != null ? javDBSysteamBean.getJav_website_url() : null;
            if (!TextUtils.isEmpty(string)) {
                PreferencesUtil.getInstance().putString("javdb_official_url", string);
            }
        }
        if (!TextUtils.isEmpty(string)) {
            AppUtil.startBrowseView(string);
        }
    }

    private final void requestJAVDB30() {
        ApiImplService.Companion.getApiImplService().requestJAVDB30().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<ArrayList<JavActionBean>>() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchActivity$requestJAVDB30$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<JavActionBean> arrayList) {
                JavDBAdapter javDBAdapter;
                JavDBAdapter javDBAdapter2;
                if (!(arrayList == null || arrayList.isEmpty())) {
                    LinearLayout linearLayout = (LinearLayout) PicSearchActivity.this._$_findCachedViewById(R$id.line_hot);
                    if (linearLayout != null) {
                        linearLayout.setVisibility(0);
                    }
                    ArrayList arrayList2 = new ArrayList();
                    for (JavActionBean javActionBean : arrayList) {
                        JAVDBBean.ActorsBean actorsBean = new JAVDBBean.ActorsBean();
                        actorsBean.setAvatar_url(javActionBean.getActorAvatarUrl());
                        actorsBean.setId(javActionBean.getActorId());
                        actorsBean.setName(javActionBean.getActorName());
                        actorsBean.setOther_name(javActionBean.getActorOtherName());
                        actorsBean.setUrl(javActionBean.getActorLink());
                        actorsBean.setPercentage(javActionBean.getActorPercentage());
                        actorsBean.setType(javActionBean.getActorType());
                        arrayList2.add(actorsBean);
                    }
                    javDBAdapter = PicSearchActivity.this.hotAdapter;
                    if (javDBAdapter != null) {
                        javDBAdapter.setNewData(arrayList2);
                    }
                    javDBAdapter2 = PicSearchActivity.this.hotAdapter;
                    if (javDBAdapter2 == null) {
                        return;
                    }
                    javDBAdapter2.setEnableLoadMore(false);
                }
            }
        });
    }

    private final void reportJAVDB(JAVDBBean.ActorsBean actorsBean) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("actorAvatarUrl", actorsBean.getAvatar_url());
        linkedHashMap.put("actorId", actorsBean.getId());
        linkedHashMap.put("actorType", actorsBean.getType());
        linkedHashMap.put("actorName", actorsBean.getName());
        linkedHashMap.put("actorOtherName", actorsBean.getOther_name());
        linkedHashMap.put("actorLink", actorsBean.getUrl());
        linkedHashMap.put("actorPercentage", Integer.valueOf(actorsBean.getPercentage()));
        ApiImplService.Companion.getApiImplService().requestActorReport(linkedHashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchActivity$reportJAVDB$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("上报javdb点击成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3784i("上报javdb点击失败");
            }
        });
    }
}
