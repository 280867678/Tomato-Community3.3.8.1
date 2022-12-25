package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.UpSubscribePayDialog;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.SubscribeUpList;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.adapter.UpSubscribeAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: UpSubscribeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity */
/* loaded from: classes3.dex */
public final class UpSubscribeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private UpSubscribeAdapter adapter;
    private boolean isMySelf;
    private int memberId;
    private int subscribeFlag;
    private UserInfo userInfo;
    private String notice = "";
    private ArrayList<Integer> listPrice = new ArrayList<>();

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
        return R.layout.activity_up_subscribe;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public final UpSubscribeAdapter getAdapter() {
        return this.adapter;
    }

    public final int getMemberId() {
        return this.memberId;
    }

    public final ArrayList<Integer> getListPrice() {
        return this.listPrice;
    }

    public final void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /* compiled from: UpSubscribeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, int i) {
            Intent intent = new Intent(context, UpSubscribeActivity.class);
            intent.putExtra("memberId", i);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        Button button;
        this.adapter = new UpSubscribeAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.recycler_view));
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        boolean z = false;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.adapter);
        }
        Intent intent = getIntent();
        this.memberId = intent != null ? intent.getIntExtra("memberId", 0) : 0;
        if (this.memberId == DBUtil.getMemberId()) {
            z = true;
        }
        this.isMySelf = z;
        if (this.isMySelf) {
            this.userInfo = DBUtil.getUserInfo();
            initUserInfo();
        } else {
            requestPersonInfo(this.memberId, DBUtil.getMemberId());
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpSubscribeActivity.this.onBackPressed();
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_no_data);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpSubscribeActivity.this.requestList();
                }
            });
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_subscribe);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$initView$3

                /* compiled from: UpSubscribeActivity.kt */
                /* renamed from: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$initView$3$1 */
                /* loaded from: classes3.dex */
                static final class C26791 extends Lambda implements Functions<Unit> {
                    C26791() {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
                        mo6822invoke();
                        return Unit.INSTANCE;
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke  reason: collision with other method in class */
                    public final void mo6822invoke() {
                        UpSubscribeActivity.this.requestList();
                        Button button = (Button) UpSubscribeActivity.this._$_findCachedViewById(R$id.button_subscribe);
                        if (button != null) {
                            button.setText(AppUtil.getString(R.string.up_person_home_subscribed));
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpSubscribePayDialog upSubscribePayDialog = new UpSubscribePayDialog(UpSubscribeActivity.this);
                    upSubscribePayDialog.setOwnerActivity(UpSubscribeActivity.this);
                    upSubscribePayDialog.setListPrice(UpSubscribeActivity.this.getListPrice(), UpSubscribeActivity.this.getMemberId());
                    upSubscribePayDialog.addSubscribeCallBack(new C26791());
                    upSubscribePayDialog.show();
                }
            });
        }
        int i = this.subscribeFlag;
        if (i == 0) {
            Button button3 = (Button) _$_findCachedViewById(R$id.button_subscribe);
            if (button3 == null) {
                return;
            }
            button3.setText(AppUtil.getString(R.string.up_subscribe_right_now));
        } else if (i != 1) {
            if (i != 2 || (button = (Button) _$_findCachedViewById(R$id.button_subscribe)) == null) {
                return;
            }
            button.setText(AppUtil.getString(R.string.up_subscribe_renew));
        } else {
            Button button4 = (Button) _$_findCachedViewById(R$id.button_subscribe);
            if (button4 == null) {
                return;
            }
            button4.setText(AppUtil.getString(R.string.up_person_home_subscribed));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initUserInfo() {
        String str;
        UserInfo userInfo = this.userInfo;
        if (userInfo == null || (str = userInfo.getNotice()) == null) {
            str = "";
        }
        this.notice = str;
        UserInfo userInfo2 = this.userInfo;
        int i = 0;
        this.subscribeFlag = userInfo2 != null ? userInfo2.getSubscribeFlag() : 0;
        ArrayList<Integer> arrayList = this.listPrice;
        UserInfo userInfo3 = this.userInfo;
        arrayList.add(Integer.valueOf(userInfo3 != null ? userInfo3.getPriceMonth() : 0));
        ArrayList<Integer> arrayList2 = this.listPrice;
        UserInfo userInfo4 = this.userInfo;
        arrayList2.add(Integer.valueOf(userInfo4 != null ? userInfo4.getPriceSeason() : 0));
        ArrayList<Integer> arrayList3 = this.listPrice;
        UserInfo userInfo5 = this.userInfo;
        if (userInfo5 != null) {
            i = userInfo5.getPriceYear();
        }
        arrayList3.add(Integer.valueOf(i));
        if (TextUtils.isEmpty(this.notice)) {
            String string = AppUtil.getString(R.string.up_subscribe_no_notice);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.up_subscribe_no_notice)");
            this.notice = string;
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_notice);
        if (textView != null) {
            textView.setText(this.notice);
        }
        if (this.isMySelf) {
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.line_notice);
            if (linearLayout != null) {
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$initUserInfo$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Context mContext;
                        mContext = UpSubscribeActivity.this.getMContext();
                        UpSubscribeActivity.this.startActivityForResult(new Intent(mContext, UpEditNoticeActivity.class), 100);
                    }
                });
            }
            Button button = (Button) _$_findCachedViewById(R$id.button_subscribe);
            if (button != null) {
                button.setVisibility(8);
            }
            String str2 = this.notice + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.common_edit);
            SpannableString spannableString = new SpannableString(str2);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5B92E1")), this.notice.length(), str2.length(), 18);
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_notice);
            if (textView2 == null) {
                return;
            }
            textView2.setText(spannableString);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100) {
            String stringExtra = intent != null ? intent.getStringExtra("text") : null;
            if (stringExtra == null) {
                return;
            }
            String str = stringExtra + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.common_edit);
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5B92E1")), stringExtra.length(), str.length(), 18);
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_notice);
            if (textView == null) {
                return;
            }
            textView.setText(spannableString);
        }
    }

    public final void requestPersonInfo(int i, int i2) {
        ApiImplService.Companion.getApiImplService().getPersonInfo(i, i2).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$requestPersonInfo$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpSubscribeActivity.this.showWaitingDialog();
            }
        }).subscribe(new Consumer<BaseResponse<UserInfo>>() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$requestPersonInfo$2
            @Override // io.reactivex.functions.Consumer
            public final void accept(BaseResponse<UserInfo> baseResponse) {
                UpSubscribeActivity.this.hideWaitingDialog();
                if (baseResponse instanceof BaseResponse) {
                    UpSubscribeActivity.this.setUserInfo(baseResponse.getData());
                    UpSubscribeActivity.this.initUserInfo();
                    return;
                }
                boolean z = baseResponse instanceof Throwable;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestList() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(this.memberId));
        linkedHashMap.put("pageNo", 1);
        linkedHashMap.put(RequestParams.PAGE_SIZE, 50);
        ApiImplService.Companion.getApiImplService().requestUpListSubscribe(linkedHashMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$requestList$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpSubscribeActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<SubscribeUpList>>() { // from class: com.one.tomato.mvp.ui.up.view.UpSubscribeActivity$requestList$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<SubscribeUpList> arrayList) {
                UpSubscribeActivity.this.hideWaitingDialog();
                if (arrayList == null || arrayList.isEmpty()) {
                    RelativeLayout relativeLayout = (RelativeLayout) UpSubscribeActivity.this._$_findCachedViewById(R$id.relate_no_data);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(0);
                    }
                    RecyclerView recyclerView = (RecyclerView) UpSubscribeActivity.this._$_findCachedViewById(R$id.recycler_view);
                    if (recyclerView == null) {
                        return;
                    }
                    recyclerView.setVisibility(8);
                    return;
                }
                RelativeLayout relativeLayout2 = (RelativeLayout) UpSubscribeActivity.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(8);
                }
                RecyclerView recyclerView2 = (RecyclerView) UpSubscribeActivity.this._$_findCachedViewById(R$id.recycler_view);
                if (recyclerView2 != null) {
                    recyclerView2.setVisibility(0);
                }
                UpSubscribeAdapter adapter = UpSubscribeActivity.this.getAdapter();
                if (adapter != null) {
                    adapter.setNewData(arrayList);
                }
                UpSubscribeAdapter adapter2 = UpSubscribeActivity.this.getAdapter();
                if (adapter2 == null) {
                    return;
                }
                adapter2.setEnableLoadMore(false);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpSubscribeActivity.this.hideWaitingDialog();
                RelativeLayout relativeLayout = (RelativeLayout) UpSubscribeActivity.this._$_findCachedViewById(R$id.relate_no_data);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
                RecyclerView recyclerView = (RecyclerView) UpSubscribeActivity.this._$_findCachedViewById(R$id.recycler_view);
                if (recyclerView != null) {
                    recyclerView.setVisibility(8);
                }
            }
        });
    }
}
