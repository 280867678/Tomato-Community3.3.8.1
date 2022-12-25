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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.JAVDBBean;
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
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* compiled from: PicSearchResultActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity */
/* loaded from: classes3.dex */
public final class PicSearchResultActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private JavDBSysteamBean javDBSysteamBean;
    private JavDBAdapter searchAdapter;

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
        return R.layout.activity_pic_search_result;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PicSearchResultActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, String imageUrl) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(imageUrl, "imageUrl");
            Intent intent = new Intent(context, PicSearchResultActivity.class);
            intent.putExtra("image", imageUrl);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        int indexOf$default;
        initTitleBar();
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        String javDbParameter = systemParam.getJavDbParameter();
        if (!TextUtils.isEmpty(javDbParameter)) {
            this.javDBSysteamBean = (JavDBSysteamBean) BaseApplication.gson.fromJson(javDbParameter, (Class<Object>) JavDBSysteamBean.class);
        }
        SpannableString spannableString = new SpannableString(AppUtil.getString(R.string.pic_search_3));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5B92E1"));
        indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) spannableString, "B", 0, false, 6, (Object) null);
        spannableString.setSpan(foregroundColorSpan, 1, indexOf$default + 1, 18);
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_provide_bottom);
        if (textView != null) {
            textView.setText(spannableString);
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_javdb_bottom);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PicSearchResultActivity.this.jumpJavDBWeb();
                }
            });
        }
        this.searchAdapter = new JavDBAdapter((RecyclerView) _$_findCachedViewById(R$id.search_recycler), getMContext());
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_8);
        builder.setVerticalSpan(R.dimen.dimen_16);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.search_recycler);
        if (recyclerView != null) {
            recyclerView.addItemDecoration(build);
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.search_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new GridLayoutManager(getMContext(), 3));
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.search_recycler);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.searchAdapter);
        }
        JavDBAdapter javDBAdapter = this.searchAdapter;
        if (javDBAdapter != null) {
            javDBAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$initView$2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.JAVDBBean.ActorsBean");
                    }
                    PicSearchResultActivity.this.jumpHtml((JAVDBBean.ActorsBean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startSearch() {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.relate_search);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        startAnim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopSearch() {
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_javdb_bottom);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        cancelAnim();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        requestJAVDB();
    }

    private final void startAnim() {
        LottieAnimationView iv_loading = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
        Intrinsics.checkExpressionValueIsNotNull(iv_loading, "iv_loading");
        iv_loading.setVisibility(0);
        LottieAnimationView iv_loading2 = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
        Intrinsics.checkExpressionValueIsNotNull(iv_loading2, "iv_loading");
        if (iv_loading2.getAnimation() != null) {
            LottieAnimationView iv_loading3 = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
            Intrinsics.checkExpressionValueIsNotNull(iv_loading3, "iv_loading");
            if (iv_loading3.isAnimating()) {
                ((LottieAnimationView) _$_findCachedViewById(R$id.iv_loading)).cancelAnimation();
            }
        }
        ((LottieAnimationView) _$_findCachedViewById(R$id.iv_loading)).setAnimation("loading_refresh.json");
    }

    private final void cancelAnim() {
        LottieAnimationView iv_loading = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
        Intrinsics.checkExpressionValueIsNotNull(iv_loading, "iv_loading");
        iv_loading.setVisibility(8);
        LottieAnimationView iv_loading2 = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
        Intrinsics.checkExpressionValueIsNotNull(iv_loading2, "iv_loading");
        if (iv_loading2.getAnimation() != null) {
            LottieAnimationView iv_loading3 = (LottieAnimationView) _$_findCachedViewById(R$id.iv_loading);
            Intrinsics.checkExpressionValueIsNotNull(iv_loading3, "iv_loading");
            if (!iv_loading3.isAnimating()) {
                return;
            }
            ((LottieAnimationView) _$_findCachedViewById(R$id.iv_loading)).cancelAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void jumpHtml(JAVDBBean.ActorsBean actorsBean) {
        HtmlConfig htmlConfig = new HtmlConfig();
        htmlConfig.setUrl(actorsBean.getUrl());
        HtmlShowActivity.startActivity(getMContext(), htmlConfig);
        reportJAVDB(actorsBean);
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

    private final void requestJAVDB() {
        JavDBSysteamBean javDBSysteamBean = this.javDBSysteamBean;
        String str = null;
        String stringPlus = Intrinsics.stringPlus(javDBSysteamBean != null ? javDBSysteamBean.getJavDomain() : null, "/api/internel/v1/search_image");
        Intent intent = getIntent();
        if (intent != null) {
            str = intent.getStringExtra("image");
        }
        if (!(str == null || str.length() == 0)) {
            HashMap hashMap = new HashMap();
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "fq");
            hashMap.put("client_name", description);
            File file = new File(str);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
            Intrinsics.checkExpressionValueIsNotNull(description, "description");
            Intrinsics.checkExpressionValueIsNotNull(part, "part");
            apiImplService.requestJavDB(stringPlus, description, part).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$requestJAVDB$1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Disposable disposable) {
                    PicSearchResultActivity.this.startSearch();
                }
            }).subscribe(new ApiDisposableObserver<JAVDBBean>() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$requestJAVDB$2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(JAVDBBean jAVDBBean) {
                    JavDBAdapter javDBAdapter;
                    JavDBAdapter javDBAdapter2;
                    PicSearchResultActivity.this.stopSearch();
                    String str2 = null;
                    List<JAVDBBean.ActorsBean> actors = jAVDBBean != null ? jAVDBBean.getActors() : null;
                    if (actors == null || actors.isEmpty()) {
                        RelativeLayout relativeLayout = (RelativeLayout) PicSearchResultActivity.this._$_findCachedViewById(R$id.relate_no_data);
                        if (relativeLayout == null) {
                            return;
                        }
                        relativeLayout.setVisibility(0);
                        return;
                    }
                    LinearLayout linearLayout = (LinearLayout) PicSearchResultActivity.this._$_findCachedViewById(R$id.relate_search);
                    if (linearLayout != null) {
                        linearLayout.setVisibility(0);
                    }
                    javDBAdapter = PicSearchResultActivity.this.searchAdapter;
                    if (javDBAdapter != null) {
                        javDBAdapter.setNewData(jAVDBBean != null ? jAVDBBean.getActors() : null);
                    }
                    javDBAdapter2 = PicSearchResultActivity.this.searchAdapter;
                    if (javDBAdapter2 != null) {
                        javDBAdapter2.setEnableLoadMore(false);
                    }
                    PreferencesUtil preferencesUtil = PreferencesUtil.getInstance();
                    if (jAVDBBean != null) {
                        str2 = jAVDBBean.getOfficial_url();
                    }
                    preferencesUtil.putString("javdb_official_url", str2);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    PicSearchResultActivity.this.stopSearch();
                    RelativeLayout relativeLayout = (RelativeLayout) PicSearchResultActivity.this._$_findCachedViewById(R$id.relate_no_data);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(0);
                    }
                }
            });
        }
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
        ApiImplService.Companion.getApiImplService().requestActorReport(linkedHashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.view.PicSearchResultActivity$reportJAVDB$1
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
