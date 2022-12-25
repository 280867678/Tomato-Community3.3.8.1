package com.one.tomato.mvp.p080ui.sign.view;

import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.SignBean;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.sign.adapter.SignAdapter;
import com.one.tomato.mvp.p080ui.sign.impl.ISignImpl;
import com.one.tomato.mvp.p080ui.sign.present.SignPresenter;
import com.one.tomato.utils.AppUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSignActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.sign.view.UserSignActivity */
/* loaded from: classes3.dex */
public final class UserSignActivity extends MvpBaseActivity<ISignImpl, SignPresenter> implements ISignImpl {
    private HashMap _$_findViewCache;
    private SignAdapter adapter;
    private int signedDay;

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
        return R.layout.activity_user_sign;
    }

    @Override // com.one.tomato.mvp.p080ui.sign.impl.ISignImpl
    public void handlerSignDay(SignBean signBean) {
        String str;
        int i;
        this.signedDay = signBean != null ? signBean.getSignInDays() : 0;
        SignAdapter signAdapter = this.adapter;
        Integer num = null;
        List<SignBean> data = signAdapter != null ? signAdapter.getData() : null;
        for (int i2 = 1; i2 <= 6; i2++) {
            if (data != null) {
                SignBean signBean2 = data.get(i2 - 1);
                if (signBean != null) {
                    Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                    signBean2.setCountSignInFirst(signBean.getCountSignInFirst());
                    Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                    signBean2.setCountSignInLast(signBean.getCountSignInLast());
                    Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                    signBean2.setGiftExp(signBean.getGiftExp());
                    Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                    signBean2.setVipGiftFirst(signBean.getVipGiftFirst());
                    Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                    signBean2.setVipGiftLast(signBean.getVipGiftLast());
                    if (signBean.getSignInDays() > 0 && 1 <= (i = this.signedDay)) {
                        int i3 = 1;
                        while (true) {
                            if (i2 == i3) {
                                Intrinsics.checkExpressionValueIsNotNull(signBean2, "signBean");
                                signBean2.setSigned(true);
                            }
                            if (i3 != i) {
                                i3++;
                            }
                        }
                    }
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        SignAdapter signAdapter2 = this.adapter;
        if (signAdapter2 != null) {
            signAdapter2.notifyDataSetChanged();
        }
        if (signBean != null && signBean.getCountSignInFirst() == 7) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_7);
            if (imageView != null) {
                imageView.setImageResource(R.drawable.sign_give_vip);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_give);
            if (textView != null) {
                String string = AppUtil.getString(R.string.sign_give_num_vip);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.sign_give_num_vip)");
                Object[] objArr = {String.valueOf(signBean.getVipGiftFirst())};
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                textView.setText(format);
            }
        } else if (signBean != null && signBean.getCountSignInLast() == 7) {
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_7);
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.sign_give_vip);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_give);
            if (textView2 != null) {
                String string2 = AppUtil.getString(R.string.sign_give_num_vip);
                Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.sign_give_num_vip)");
                Object[] objArr2 = {String.valueOf(signBean.getVipGiftLast())};
                String format2 = String.format(string2, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
                textView2.setText(format2);
            }
        } else {
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_7);
            if (imageView3 != null) {
                imageView3.setImageResource(R.drawable.sign_jingyan);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_give);
            if (textView3 != null) {
                String string3 = AppUtil.getString(R.string.sign_get_jingyan);
                Intrinsics.checkExpressionValueIsNotNull(string3, "AppUtil.getString(R.string.sign_get_jingyan)");
                Object[] objArr3 = new Object[1];
                if (signBean != null) {
                    num = Integer.valueOf(signBean.getGiftExp());
                }
                objArr3[0] = String.valueOf(num);
                String format3 = String.format(string3, Arrays.copyOf(objArr3, objArr3.length));
                Intrinsics.checkExpressionValueIsNotNull(format3, "java.lang.String.format(this, *args)");
                textView3.setText(format3);
            }
        }
        if (this.signedDay == 7) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_signed_7);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_signed_7);
            if (imageView4 != null) {
                imageView4.setVisibility(0);
            }
        }
        if (signBean != null && signBean.isCurDaySignIn()) {
            Button button = (Button) _$_findCachedViewById(R$id.button_sign);
            if (button != null) {
                button.setEnabled(false);
            }
            Button button2 = (Button) _$_findCachedViewById(R$id.button_sign);
            if (button2 != null) {
                button2.setText(AppUtil.getString(R.string.sign_current_day_signed));
            }
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_sign_desc);
        if (textView4 != null) {
            if (signBean == null || (str = signBean.getSignInCopy()) == null) {
                str = "";
            }
            textView4.setText(str);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.sign.impl.ISignImpl
    public void handlerSignNumDay() {
        List<SignBean> data;
        SignBean signBean;
        Button button = (Button) _$_findCachedViewById(R$id.button_sign);
        if (button != null) {
            button.setEnabled(false);
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_sign);
        if (button2 != null) {
            button2.setText(AppUtil.getString(R.string.sign_current_day_signed));
        }
        int i = this.signedDay;
        if (i < 7) {
            this.signedDay = i + 1;
        }
        if (this.signedDay == 7) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_signed_7);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.post(new Runnable() { // from class: com.one.tomato.mvp.ui.sign.view.UserSignActivity$handlerSignNumDay$1
                @Override // java.lang.Runnable
                public final void run() {
                    RelativeLayout relativeLayout2 = (RelativeLayout) UserSignActivity.this._$_findCachedViewById(R$id.relate_signed_7);
                    if (relativeLayout2 != null) {
                        relativeLayout2.setVisibility(0);
                    }
                    ImageView imageView = (ImageView) UserSignActivity.this._$_findCachedViewById(R$id.image_signed_7);
                    if (imageView != null) {
                        imageView.setVisibility(0);
                    }
                }
            });
            return;
        }
        SignAdapter signAdapter = this.adapter;
        if (signAdapter != null && (data = signAdapter.getData()) != null && (signBean = data.get(this.signedDay - 1)) != null) {
            signBean.setSigned(true);
        }
        SignAdapter signAdapter2 = this.adapter;
        if (signAdapter2 == null) {
            return;
        }
        signAdapter2.notifyDataSetChanged();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public SignPresenter mo6439createPresenter() {
        return new SignPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_day);
        if (textView != null) {
            String string = AppUtil.getString(R.string.sign_num_day);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.sign_num_day)");
            Object[] objArr = {"7"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        this.adapter = new SignAdapter();
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 3));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.adapter);
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i <= 5) {
            i++;
            arrayList.add(new SignBean(false, i));
        }
        SignAdapter signAdapter = this.adapter;
        if (signAdapter != null) {
            signAdapter.setNewData(arrayList);
        }
        SignAdapter signAdapter2 = this.adapter;
        if (signAdapter2 != null) {
            signAdapter2.setEnableLoadMore(false);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_sign);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.sign.view.UserSignActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SignPresenter mPresenter;
                    mPresenter = UserSignActivity.this.getMPresenter();
                    if (mPresenter != null) {
                        mPresenter.requestSign();
                    }
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.sign.view.UserSignActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserSignActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        SignPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestSignDay();
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        Button button = (Button) _$_findCachedViewById(R$id.button_sign);
        if (button != null) {
            button.setEnabled(false);
        }
    }
}
