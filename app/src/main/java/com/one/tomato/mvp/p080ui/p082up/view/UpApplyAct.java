package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView;
import com.one.tomato.mvp.p080ui.p082up.presenter.UpHomePresenter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Standard;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: UpApplyAct.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpApplyAct */
/* loaded from: classes3.dex */
public final class UpApplyAct extends MvpBaseActivity<UpContarct$UpIView, UpHomePresenter> implements UpContarct$UpIView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int applyType = 1;

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
        return R.layout.activity_up_apply;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public UpHomePresenter mo6439createPresenter() {
        return new UpHomePresenter();
    }

    /* compiled from: UpApplyAct.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpApplyAct$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, int i) {
            Intent intent = new Intent(context, UpApplyAct.class);
            intent.putExtra("type", i);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        Intent intent = getIntent();
        int i = 1;
        if (intent != null) {
            i = intent.getIntExtra("type", 1);
        }
        this.applyType = i;
        if (this.applyType == 2) {
            TextView titleTV = getTitleTV();
            if (titleTV != null) {
                titleTV.setText(AppUtil.getString(R.string.up_apply_title_original));
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_desc);
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.up_apply_original_desc));
            }
        } else {
            TextView titleTV2 = getTitleTV();
            if (titleTV2 != null) {
                titleTV2.setText(AppUtil.getString(R.string.up_apply_title));
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_desc);
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.up_apply_tishi_reason));
            }
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_commit);
        if (button != null) {
            button.setEnabled(false);
        }
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpApplyAct$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpApplyAct.this.onBackPressed();
                }
            });
        }
        EditText editText = (EditText) _$_findCachedViewById(R$id.edit);
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.up.view.UpApplyAct$initView$2
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    String obj = charSequence != null ? charSequence.toString() : null;
                    Button button2 = (Button) UpApplyAct.this._$_findCachedViewById(R$id.button_commit);
                    if (button2 != null) {
                        button2.setEnabled(!TextUtils.isEmpty(obj));
                    }
                }
            });
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_commit);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpApplyAct$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpHomePresenter mPresenter;
                    String str;
                    int i2;
                    Editable text;
                    mPresenter = UpApplyAct.this.getMPresenter();
                    if (mPresenter != null) {
                        EditText editText2 = (EditText) UpApplyAct.this._$_findCachedViewById(R$id.edit);
                        if (editText2 == null || (text = editText2.getText()) == null || (str = text.toString()) == null) {
                            str = "";
                        }
                        i2 = UpApplyAct.this.applyType;
                        mPresenter.requestApplyUp(str, i2 == 2 ? 1 : 0);
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryUpStatusInfo(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyUpSuccess() {
        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_apply_comit_suc));
        finish();
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyError() {
        Button button = (Button) _$_findCachedViewById(R$id.button_commit);
        if (button != null) {
            button.setEnabled(true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryAchiSucess(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerRankList(ArrayList<UpRankListBean> arrayList) {
        throw new Standard("An operation is not implemented: not implemented");
    }
}
