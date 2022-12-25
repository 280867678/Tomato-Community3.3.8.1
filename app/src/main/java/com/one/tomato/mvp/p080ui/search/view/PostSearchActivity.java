package com.one.tomato.mvp.p080ui.search.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.post.view.PicSearchActivity;
import com.one.tomato.mvp.p080ui.search.view.SearchBeforeFragment;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.utils.LogConstants;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: PostSearchActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.PostSearchActivity */
/* loaded from: classes3.dex */
public final class PostSearchActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String randomSearchKey;
    private SearchAfterFragment searchAfterFragment;
    private SearchBeforeFragment searchBeforeFragment;
    private int type = 1;

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
        return R.layout.activity_post_search_new;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PostSearchActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.search.view.PostSearchActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, String str) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, PostSearchActivity.class);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("intent_search_key", str);
            }
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        switchContentView();
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).setOnTextClearListener(new ClearEditText.OnTextClearListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initView$1
            @Override // com.one.tomato.widget.ClearEditText.OnTextClearListener
            public final void onTextClear() {
                PostSearchActivity.this.type = 1;
                PostSearchActivity.this.switchContentView();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostSearchActivity.this.onBackPressed();
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initView$3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                SearchBeforeFragment searchBeforeFragment;
                SearchAfterFragment searchAfterFragment;
                if (i == 3) {
                    ClearEditText et_input = (ClearEditText) PostSearchActivity.this._$_findCachedViewById(R$id.et_input);
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
                    PostSearchActivity.this.type = 2;
                    searchBeforeFragment = PostSearchActivity.this.searchBeforeFragment;
                    if (searchBeforeFragment != null) {
                        searchBeforeFragment.addHistory(obj2);
                    }
                    PostSearchActivity.this.switchContentView();
                    searchAfterFragment = PostSearchActivity.this.searchAfterFragment;
                    if (searchAfterFragment != null) {
                        searchAfterFragment.startSearch(obj2);
                    }
                    return true;
                }
                return false;
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_feedback)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initView$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostSearchActivity.this.showFeedbackDialog();
            }
        });
        ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_input);
        if (clearEditText != null) {
            clearEditText.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initView$5
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    Editable text;
                    String obj;
                    ClearEditText clearEditText2 = (ClearEditText) PostSearchActivity.this._$_findCachedViewById(R$id.et_input);
                    if (clearEditText2 != null && (text = clearEditText2.getText()) != null && (obj = text.toString()) != null) {
                        if (obj == null || obj.length() == 0) {
                            ImageView imageView = (ImageView) PostSearchActivity.this._$_findCachedViewById(R$id.image_javdb_search);
                            if (imageView == null) {
                                return;
                            }
                            imageView.setVisibility(0);
                            return;
                        }
                    }
                    ImageView imageView2 = (ImageView) PostSearchActivity.this._$_findCachedViewById(R$id.image_javdb_search);
                    if (imageView2 != null) {
                        imageView2.setVisibility(8);
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        SearchBeforeFragment searchBeforeFragment = this.searchBeforeFragment;
        if (searchBeforeFragment != null) {
            searchBeforeFragment.setOnSearchListener(new SearchBeforeFragment.OnSearchListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initData$1
                @Override // com.one.tomato.mvp.p080ui.search.view.SearchBeforeFragment.OnSearchListener
                public void search(String key) {
                    SearchAfterFragment searchAfterFragment;
                    Intrinsics.checkParameterIsNotNull(key, "key");
                    PostSearchActivity.this.type = 2;
                    PostSearchActivity.this.switchContentView();
                    searchAfterFragment = PostSearchActivity.this.searchAfterFragment;
                    if (searchAfterFragment != null) {
                        searchAfterFragment.startSearch(key);
                    }
                    ((ClearEditText) PostSearchActivity.this._$_findCachedViewById(R$id.et_input)).setText(key);
                    ((ClearEditText) PostSearchActivity.this._$_findCachedViewById(R$id.et_input)).setSelection(key.length());
                }
            });
        }
        if (getIntent().hasExtra("intent_search_key")) {
            String stringExtra = getIntent().getStringExtra("intent_search_key");
            Intrinsics.checkExpressionValueIsNotNull(stringExtra, "intent.getStringExtra(INTENT_SEARCH_KEY)");
            this.randomSearchKey = stringExtra;
            ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_input);
            String str = this.randomSearchKey;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("randomSearchKey");
                throw null;
            }
            clearEditText.setText(str);
            ClearEditText clearEditText2 = (ClearEditText) _$_findCachedViewById(R$id.et_input);
            String str2 = this.randomSearchKey;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("randomSearchKey");
                throw null;
            } else {
                clearEditText2.setSelection(str2.length());
                ((ClearEditText) _$_findCachedViewById(R$id.et_input)).requestFocus();
            }
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_javdb_search);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$initData$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    PicSearchActivity.Companion companion = PicSearchActivity.Companion;
                    mContext = PostSearchActivity.this.getMContext();
                    if (mContext != null) {
                        companion.startAct(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void switchContentView() {
        hideKeyBoard(this);
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).clearFocus();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "supportFragmentManager.beginTransaction()");
        int i = this.type;
        if (i == 1) {
            TextView tv_feedback = (TextView) _$_findCachedViewById(R$id.tv_feedback);
            Intrinsics.checkExpressionValueIsNotNull(tv_feedback, "tv_feedback");
            tv_feedback.setVisibility(8);
            SearchAfterFragment searchAfterFragment = this.searchAfterFragment;
            if (searchAfterFragment != null) {
                Boolean valueOf = searchAfterFragment != null ? Boolean.valueOf(searchAfterFragment.isVisible()) : null;
                if (valueOf == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (valueOf.booleanValue()) {
                    SearchAfterFragment searchAfterFragment2 = this.searchAfterFragment;
                    if (searchAfterFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(searchAfterFragment2);
                }
            }
            SearchBeforeFragment searchBeforeFragment = this.searchBeforeFragment;
            if (searchBeforeFragment == null) {
                this.searchBeforeFragment = new SearchBeforeFragment();
                SearchBeforeFragment searchBeforeFragment2 = this.searchBeforeFragment;
                if (searchBeforeFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.framelayout, searchBeforeFragment2);
            } else if (searchBeforeFragment != null) {
                beginTransaction.show(searchBeforeFragment);
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        } else if (i == 2) {
            TextView tv_feedback2 = (TextView) _$_findCachedViewById(R$id.tv_feedback);
            Intrinsics.checkExpressionValueIsNotNull(tv_feedback2, "tv_feedback");
            tv_feedback2.setVisibility(0);
            SearchBeforeFragment searchBeforeFragment3 = this.searchBeforeFragment;
            if (searchBeforeFragment3 != null) {
                Boolean valueOf2 = searchBeforeFragment3 != null ? Boolean.valueOf(searchBeforeFragment3.isVisible()) : null;
                if (valueOf2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (valueOf2.booleanValue()) {
                    SearchBeforeFragment searchBeforeFragment4 = this.searchBeforeFragment;
                    if (searchBeforeFragment4 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(searchBeforeFragment4);
                }
            }
            SearchAfterFragment searchAfterFragment3 = this.searchAfterFragment;
            if (searchAfterFragment3 == null) {
                this.searchAfterFragment = new SearchAfterFragment();
                SearchAfterFragment searchAfterFragment4 = this.searchAfterFragment;
                if (searchAfterFragment4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.framelayout, searchAfterFragment4);
            } else if (searchAfterFragment3 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(searchAfterFragment3);
            }
        }
        beginTransaction.commitAllowingStateLoss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v12, types: [android.widget.TextView, T] */
    /* JADX WARN: Type inference failed for: r2v15, types: [android.widget.TextView, T] */
    /* JADX WARN: Type inference failed for: r2v18, types: [android.widget.TextView, T] */
    /* JADX WARN: Type inference failed for: r2v21, types: [T, android.widget.EditText] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.widget.CheckBox, T] */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.widget.CheckBox, T] */
    /* JADX WARN: Type inference failed for: r2v9, types: [android.widget.CheckBox, T] */
    public final void showFeedbackDialog() {
        hideKeyBoard(this);
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.bottomLayoutGone();
        customAlertDialog.setCanceledOnTouchOutside(true);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_search_feedback, (ViewGroup) null);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        View findViewById = inflate.findViewById(R.id.checkbox1);
        if (findViewById == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.CheckBox");
        }
        ref$ObjectRef.element = (CheckBox) findViewById;
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        View findViewById2 = inflate.findViewById(R.id.checkbox2);
        if (findViewById2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.CheckBox");
        }
        ref$ObjectRef2.element = (CheckBox) findViewById2;
        final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        View findViewById3 = inflate.findViewById(R.id.checkbox3);
        if (findViewById3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.CheckBox");
        }
        ref$ObjectRef3.element = (CheckBox) findViewById3;
        final Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
        View findViewById4 = inflate.findViewById(R.id.textView1);
        if (findViewById4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        ref$ObjectRef4.element = (TextView) findViewById4;
        final Ref$ObjectRef ref$ObjectRef5 = new Ref$ObjectRef();
        View findViewById5 = inflate.findViewById(R.id.textView2);
        if (findViewById5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        ref$ObjectRef5.element = (TextView) findViewById5;
        final Ref$ObjectRef ref$ObjectRef6 = new Ref$ObjectRef();
        View findViewById6 = inflate.findViewById(R.id.textView3);
        if (findViewById6 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        ref$ObjectRef6.element = (TextView) findViewById6;
        final Ref$ObjectRef ref$ObjectRef7 = new Ref$ObjectRef();
        View findViewById7 = inflate.findViewById(R.id.et_input);
        if (findViewById7 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.EditText");
        }
        ref$ObjectRef7.element = (EditText) findViewById7;
        View findViewById8 = inflate.findViewById(R.id.tv_submit);
        if (findViewById8 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        customAlertDialog.setContentView(inflate);
        ((TextView) findViewById8).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$showFeedbackDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HashMap hashMap = new HashMap();
                hashMap.put("memberId", String.valueOf(DBUtil.getMemberId()));
                String obj = ((EditText) ref$ObjectRef7.element).getText().toString();
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
                hashMap.put("content", obj.subSequence(i, length + 1).toString());
                ClearEditText et_input = (ClearEditText) PostSearchActivity.this._$_findCachedViewById(R$id.et_input);
                Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
                String obj2 = et_input.getText().toString();
                int length2 = obj2.length() - 1;
                int i2 = 0;
                boolean z3 = false;
                while (i2 <= length2) {
                    boolean z4 = obj2.charAt(!z3 ? i2 : length2) <= ' ';
                    if (!z3) {
                        if (!z4) {
                            z3 = true;
                        } else {
                            i2++;
                        }
                    } else if (!z4) {
                        break;
                    } else {
                        length2--;
                    }
                }
                hashMap.put(LogConstants.KEY_WORD, obj2.subSequence(i2, length2 + 1).toString());
                hashMap.put("brand", DeviceInfoUtil.getDeviceBrand());
                hashMap.put("model", DeviceInfoUtil.getDeviceTypeName());
                hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, DeviceInfoUtil.getPhoneOSVersion());
                hashMap.put("netStatus", NetWorkUtil.getNetWorkType());
                ArrayList arrayList = new ArrayList();
                if (((CheckBox) ref$ObjectRef.element).isChecked()) {
                    arrayList.add(((TextView) ref$ObjectRef4.element).getText().toString());
                }
                if (((CheckBox) ref$ObjectRef2.element).isChecked()) {
                    arrayList.add(((TextView) ref$ObjectRef5.element).getText().toString());
                }
                if (((CheckBox) ref$ObjectRef3.element).isChecked()) {
                    arrayList.add(((TextView) ref$ObjectRef6.element).getText().toString());
                }
                if (!arrayList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        sb.append((String) arrayList.get(i3));
                        if (i3 < arrayList.size() - 1) {
                            sb.append(",");
                        }
                    }
                    String sb2 = sb.toString();
                    Intrinsics.checkExpressionValueIsNotNull(sb2, "stringBuilder.toString()");
                    int length3 = sb2.length() - 1;
                    int i4 = 0;
                    boolean z5 = false;
                    while (i4 <= length3) {
                        boolean z6 = sb2.charAt(!z5 ? i4 : length3) <= ' ';
                        if (!z5) {
                            if (!z6) {
                                z5 = true;
                            } else {
                                i4++;
                            }
                        } else if (!z6) {
                            break;
                        } else {
                            length3--;
                        }
                    }
                    hashMap.put("description", sb2.subSequence(i4, length3 + 1).toString());
                } else {
                    String obj3 = ((EditText) ref$ObjectRef7.element).getText().toString();
                    int length4 = obj3.length() - 1;
                    int i5 = 0;
                    boolean z7 = false;
                    while (i5 <= length4) {
                        boolean z8 = obj3.charAt(!z7 ? i5 : length4) <= ' ';
                        if (!z7) {
                            if (!z8) {
                                z7 = true;
                            } else {
                                i5++;
                            }
                        } else if (!z8) {
                            break;
                        } else {
                            length4--;
                        }
                    }
                    if (TextUtils.isEmpty(obj3.subSequence(i5, length4 + 1).toString())) {
                        return;
                    }
                }
                PostSearchActivity.this.requestFeedbackSearch(hashMap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestFeedbackSearch(HashMap<String, String> hashMap) {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestFeedbackSearch(hashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new PostSearchActivity$requestFeedbackSearch$1(this));
    }
}
