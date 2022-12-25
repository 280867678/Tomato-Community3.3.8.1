package com.one.tomato.mvp.p080ui.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.mcxtzhang.indexlib.IndexBar.widget.C2277IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.one.tomato.R$id;
import com.one.tomato.adapter.CountryDividerItemDecoration;
import com.one.tomato.entity.Country;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.p080ui.login.adapter.CountryCodeAdapter;
import com.one.tomato.mvp.p080ui.login.impl.ICountryCodeContact$ICountryCodeView;
import com.one.tomato.mvp.p080ui.login.presenter.CountryCodePresenter;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.widget.ClearEditText;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CountryCodeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.CountryCodeActivity */
/* loaded from: classes3.dex */
public final class CountryCodeActivity extends MvpBaseRecyclerViewActivity<ICountryCodeContact$ICountryCodeView, CountryCodePresenter, CountryCodeAdapter, Country> implements ICountryCodeContact$ICountryCodeView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private final ArrayList<CountryDB> countryDBList = new ArrayList<>();
    private final ArrayList<Country> mDatas = new ArrayList<>();
    private BaseLinearLayoutManager mManager;
    private SuspensionDecoration suspensionDecoration;

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
        return R.layout.activity_country_code;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
    }

    /* compiled from: CountryCodeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.CountryCodeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, CountryCodeActivity.class);
            ((Activity) context).startActivityForResult(intent, i);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public CountryCodeAdapter mo6446createAdapter() {
        return new CountryCodeAdapter(getMContext(), getRecyclerView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        requestCountryList();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public CountryCodePresenter mo6439createPresenter() {
        return new CountryCodePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.country_code_title);
        }
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.one.tomato.mvp.ui.login.view.CountryCodeActivity$initView$1
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 3) {
                    ClearEditText et_input = (ClearEditText) CountryCodeActivity.this._$_findCachedViewById(R$id.et_input);
                    Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
                    String obj = et_input.getText().toString();
                    int length = obj.length() - 1;
                    int i2 = 0;
                    boolean z = false;
                    while (i2 <= length) {
                        boolean z2 = obj.charAt(!z ? i2 : length) <= ' ';
                        if (!z) {
                            if (!z2) {
                                z = true;
                            } else {
                                i2++;
                            }
                        } else if (!z2) {
                            break;
                        } else {
                            length--;
                        }
                    }
                    String obj2 = obj.subSequence(i2, length + 1).toString();
                    if (TextUtils.isEmpty(obj2)) {
                        return true;
                    }
                    ArrayList<CountryDB> countryList = DBUtil.getCountryList(obj2);
                    Intrinsics.checkExpressionValueIsNotNull(countryList, "DBUtil.getCountryList(content)");
                    if (countryList != null && !countryList.isEmpty()) {
                        CountryCodeActivity.this.updateData(countryList);
                    } else {
                        CountryCodeActivity.this.onEmpty("");
                    }
                    ((ClearEditText) CountryCodeActivity.this._$_findCachedViewById(R$id.et_input)).setSelection(obj2.length());
                    return true;
                }
                return false;
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.CountryCodeActivity$initView$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                ArrayList arrayList;
                ArrayList arrayList2;
                Intrinsics.checkParameterIsNotNull(s, "s");
                if (s.toString().length() == 0) {
                    arrayList = CountryCodeActivity.this.countryDBList;
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    CountryCodeActivity countryCodeActivity = CountryCodeActivity.this;
                    arrayList2 = countryCodeActivity.countryDBList;
                    countryCodeActivity.updateData(arrayList2);
                    return;
                }
                ClearEditText et_input = (ClearEditText) CountryCodeActivity.this._$_findCachedViewById(R$id.et_input);
                Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
                String obj = et_input.getText().toString();
                int length = obj.length() - 1;
                int i = 0;
                boolean z = false;
                while (i <= length) {
                    boolean z2 = obj.charAt(!z ? i : length) <= ' ';
                    if (!z) {
                        if (!z2) {
                            z = true;
                        } else {
                            i++;
                        }
                    } else if (!z2) {
                        break;
                    } else {
                        length--;
                    }
                }
                ArrayList<CountryDB> countryList = DBUtil.getCountryList(obj.subSequence(i, length + 1).toString());
                Intrinsics.checkExpressionValueIsNotNull(countryList, "DBUtil.getCountryList(content)");
                if (countryList != null && !countryList.isEmpty()) {
                    CountryCodeActivity.this.updateData(countryList);
                } else {
                    CountryCodeActivity.this.onEmpty("");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void initRecyclerView() {
        super.initRecyclerView();
        this.mManager = new BaseLinearLayoutManager(this, 1, false);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setLayoutManager(this.mManager);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(getAdapter());
        }
        CountryCodeAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEnableLoadMore(false);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        this.suspensionDecoration = new SuspensionDecoration(this, this.mDatas);
        SuspensionDecoration suspensionDecoration = this.suspensionDecoration;
        if (suspensionDecoration != null) {
            suspensionDecoration.setColorTitleBg(Color.parseColor("#F5F5F7"));
        }
        SuspensionDecoration suspensionDecoration2 = this.suspensionDecoration;
        if (suspensionDecoration2 != null) {
            suspensionDecoration2.setColorTitleFont(Color.parseColor("#121213"));
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            SuspensionDecoration suspensionDecoration3 = this.suspensionDecoration;
            if (suspensionDecoration3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            recyclerView.addItemDecoration(suspensionDecoration3);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.addItemDecoration(new CountryDividerItemDecoration(this, 1));
        }
        requestCountryList();
    }

    private final void requestCountryList() {
        CountryCodePresenter countryCodePresenter = (CountryCodePresenter) getMPresenter();
        if (countryCodePresenter != null) {
            countryCodePresenter.requestListFromDB();
        }
        CountryCodeAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEmptyViewState(0, null);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ICountryCodeContact$ICountryCodeView
    public void handleCountryListFromDB(ArrayList<CountryDB> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        if (list.isEmpty()) {
            CountryCodePresenter countryCodePresenter = (CountryCodePresenter) getMPresenter();
            if (countryCodePresenter == null) {
                return;
            }
            countryCodePresenter.requestListFromServer();
            return;
        }
        this.countryDBList.addAll(list);
        updateData(list);
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ICountryCodeContact$ICountryCodeView
    public void handleCountryListFromServer(ArrayList<CountryDB> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        if (list.isEmpty()) {
            onEmpty("");
            return;
        }
        this.countryDBList.addAll(list);
        DBUtil.saveCountryList(list);
        updateData(list);
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onEmpty(Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        CountryCodeAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEmptyViewState(2, null);
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        CountryCodeAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEmptyViewState(1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(ArrayList<CountryDB> arrayList) {
        this.mDatas.clear();
        Iterator<CountryDB> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CountryDB countryDB = it2.next();
            ArrayList<Country> arrayList2 = this.mDatas;
            Intrinsics.checkExpressionValueIsNotNull(countryDB, "countryDB");
            arrayList2.add(new Country(countryDB.getCountryCode(), countryDB.getCountryName()));
        }
        Country country = new Country("+86");
        Country top2 = country.setTop(true);
        Intrinsics.checkExpressionValueIsNotNull(top2, "china.setTop(true)");
        top2.setBaseIndexTag("↑");
        Country country2 = new Country("+852");
        Country top3 = country2.setTop(true);
        Intrinsics.checkExpressionValueIsNotNull(top3, "gang.setTop(true)");
        top3.setBaseIndexTag("↑");
        Country country3 = new Country("+853");
        Country top4 = country3.setTop(true);
        Intrinsics.checkExpressionValueIsNotNull(top4, "ao.setTop(true)");
        top4.setBaseIndexTag("↑");
        Country country4 = new Country("+886");
        Country top5 = country4.setTop(true);
        Intrinsics.checkExpressionValueIsNotNull(top5, "tai.setTop(true)");
        top5.setBaseIndexTag("↑");
        if (this.mDatas.contains(country4)) {
            int indexOf = this.mDatas.indexOf(country4);
            Country country5 = this.mDatas.get(indexOf);
            Intrinsics.checkExpressionValueIsNotNull(country5, "mDatas.get(index)");
            country4.setCountryName(country5.getCountryName());
            this.mDatas.remove(indexOf);
            this.mDatas.add(0, country4);
        }
        if (this.mDatas.contains(country3)) {
            int indexOf2 = this.mDatas.indexOf(country3);
            Country country6 = this.mDatas.get(indexOf2);
            Intrinsics.checkExpressionValueIsNotNull(country6, "mDatas.get(index)");
            country3.setCountryName(country6.getCountryName());
            this.mDatas.remove(indexOf2);
            this.mDatas.add(0, country3);
        }
        if (this.mDatas.contains(country2)) {
            int indexOf3 = this.mDatas.indexOf(country2);
            Country country7 = this.mDatas.get(indexOf3);
            Intrinsics.checkExpressionValueIsNotNull(country7, "mDatas.get(index)");
            country2.setCountryName(country7.getCountryName());
            this.mDatas.remove(indexOf3);
            this.mDatas.add(0, country2);
        }
        if (this.mDatas.contains(country)) {
            int indexOf4 = this.mDatas.indexOf(country);
            Country country8 = this.mDatas.get(indexOf4);
            Intrinsics.checkExpressionValueIsNotNull(country8, "mDatas.get(index)");
            country.setCountryName(country8.getCountryName());
            this.mDatas.remove(indexOf4);
            this.mDatas.add(0, country);
        }
        CountryCodeAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setNewData(this.mDatas);
        }
        CountryCodeAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setEnableLoadMore(false);
        }
        ((C2277IndexBar) _$_findCachedViewById(R$id.indexBar)).setmPressedShowTextView((TextView) _$_findCachedViewById(R$id.tvSideBarHint)).setNeedRealIndex(false).setmLayoutManager(this.mManager);
        C2277IndexBar indexBar = (C2277IndexBar) _$_findCachedViewById(R$id.indexBar);
        Intrinsics.checkExpressionValueIsNotNull(indexBar, "indexBar");
        indexBar.setVisibility(0);
        ((C2277IndexBar) _$_findCachedViewById(R$id.indexBar)).setmSourceDatas(this.mDatas).invalidate();
        SuspensionDecoration suspensionDecoration = this.suspensionDecoration;
        if (suspensionDecoration != null) {
            suspensionDecoration.setmDatas(this.mDatas);
        }
    }

    public final void onItemClick(int i) {
        Country item;
        Country item2;
        Intent intent = new Intent();
        CountryCodeAdapter adapter = getAdapter();
        String str = null;
        intent.putExtra("country_name", (adapter == null || (item2 = adapter.getItem(i)) == null) ? null : item2.getCountryName());
        CountryCodeAdapter adapter2 = getAdapter();
        if (adapter2 != null && (item = adapter2.getItem(i)) != null) {
            str = item.getCountryCode();
        }
        intent.putExtra("country_code", str);
        setResult(-1, intent);
        finish();
    }
}
