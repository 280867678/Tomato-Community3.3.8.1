package com.one.tomato.mvp.p080ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.ApprealBean;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AprealResultActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.AprealResultActivity */
/* loaded from: classes3.dex */
public final class AprealResultActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String account;
    private ApprealBean apprealBean;
    private int typeAppreal;

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
        return R.layout.appreal_result_act;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: AprealResultActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.AprealResultActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, ApprealBean apprealBean, String str) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent(context, AprealResultActivity.class);
            intent.putExtra("bean", apprealBean);
            intent.putExtra(LogConstants.ACCOUNT, str);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.login_account_appreal));
        }
        Button button = (Button) _$_findCachedViewById(R$id.again_appreal);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AprealResultActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    String str;
                    ApprealBean apprealBean;
                    Context mContext;
                    HtmlConfig htmlConfig = new HtmlConfig();
                    htmlConfig.setTitle(AppUtil.getString(R.string.login_apreal_account));
                    StringBuilder sb = new StringBuilder();
                    DomainServer domainServer = DomainServer.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                    sb.append(domainServer.getH5Url());
                    sb.append("/h5/AccountAppeal");
                    sb.append("?isAndroid=1&account=");
                    str = AprealResultActivity.this.account;
                    sb.append(str);
                    sb.append("&memberId=");
                    apprealBean = AprealResultActivity.this.apprealBean;
                    sb.append(apprealBean != null ? Integer.valueOf(apprealBean.getMemberId()) : null);
                    htmlConfig.setUrl(sb.toString());
                    mContext = AprealResultActivity.this.getMContext();
                    HtmlShowActivity.startActivity(mContext, htmlConfig);
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        this.apprealBean = (ApprealBean) getIntent().getParcelableExtra("bean");
        this.account = getIntent().getStringExtra(LogConstants.ACCOUNT);
        apprealResult();
    }

    private final void apprealResult() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        ApprealBean apprealBean = this.apprealBean;
        if (apprealBean == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        this.typeAppreal = apprealBean.getStatus();
        int i = this.typeAppreal;
        if (i == 1) {
            RelativeLayout relate_appreal_phone = (RelativeLayout) _$_findCachedViewById(R$id.relate_appreal_phone);
            Intrinsics.checkExpressionValueIsNotNull(relate_appreal_phone, "relate_appreal_phone");
            relate_appreal_phone.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.text_account)).setText(getString(R.string.login_appreal_result_account));
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_result);
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.login_appreal_doing));
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_account_number);
            if (textView2 != null) {
                String str7 = this.account;
                if (str7 == null) {
                    str7 = "";
                }
                textView2.setText(str7);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_phone);
            if (textView3 != null) {
                textView3.setText(getString(R.string.login_appreal_bind_phone));
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_phone_number);
            if (textView4 != null) {
                ApprealBean apprealBean2 = this.apprealBean;
                if (apprealBean2 == null || (str2 = apprealBean2.getPhone()) == null) {
                    str2 = "";
                }
                textView4.setText(str2);
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_time);
            if (textView5 != null) {
                textView5.setText(getString(R.string.login_appreal_expire_time));
            }
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_time_number);
            if (textView6 != null) {
                ApprealBean apprealBean3 = this.apprealBean;
                if (apprealBean3 == null || (str = apprealBean3.getExpireTime()) == null) {
                    str = "";
                }
                textView6.setText(str);
            }
            Button button = (Button) _$_findCachedViewById(R$id.again_appreal);
            if (button != null) {
                button.setVisibility(8);
            }
            ((ImageView) _$_findCachedViewById(R$id.image)).setImageResource(R.drawable.appreal_going);
        } else if (i != 2) {
            if (i != 3) {
                return;
            }
            RelativeLayout relate_appreal_phone2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_appreal_phone);
            Intrinsics.checkExpressionValueIsNotNull(relate_appreal_phone2, "relate_appreal_phone");
            relate_appreal_phone2.setVisibility(8);
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_result);
            if (textView7 != null) {
                textView7.setText(AppUtil.getString(R.string.login_appreal_title_fail));
            }
            ((TextView) _$_findCachedViewById(R$id.text_account)).setText(getString(R.string.login_appreal_rejectReason));
            TextView textView8 = (TextView) _$_findCachedViewById(R$id.text_account_number);
            if (textView8 != null) {
                ApprealBean apprealBean4 = this.apprealBean;
                if (apprealBean4 == null || (str6 = apprealBean4.getRejectReason()) == null) {
                    str6 = "";
                }
                textView8.setText(str6);
            }
            TextView textView9 = (TextView) _$_findCachedViewById(R$id.text_time);
            if (textView9 != null) {
                textView9.setText(getString(R.string.login_appreal_rejectTime));
            }
            TextView textView10 = (TextView) _$_findCachedViewById(R$id.text_time_number);
            if (textView10 != null) {
                ApprealBean apprealBean5 = this.apprealBean;
                if (apprealBean5 == null || (str5 = apprealBean5.getUpdateTime()) == null) {
                    str5 = "";
                }
                textView10.setText(str5);
            }
            ((ImageView) _$_findCachedViewById(R$id.image)).setImageResource(R.drawable.appreal_fail);
        } else {
            RelativeLayout relate_appreal_phone3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_appreal_phone);
            Intrinsics.checkExpressionValueIsNotNull(relate_appreal_phone3, "relate_appreal_phone");
            relate_appreal_phone3.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.text_account)).setText(getString(R.string.login_appreal_result_account));
            TextView textView11 = (TextView) _$_findCachedViewById(R$id.text_result);
            if (textView11 != null) {
                textView11.setText(AppUtil.getString(R.string.login_appreal_title_pass));
            }
            TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_account_number);
            if (textView12 != null) {
                String str8 = this.account;
                if (str8 == null) {
                    str8 = "";
                }
                textView12.setText(str8);
            }
            TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_phone);
            if (textView13 != null) {
                textView13.setText(getString(R.string.login_appreal_bind_phone));
            }
            TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_phone_number);
            if (textView14 != null) {
                ApprealBean apprealBean6 = this.apprealBean;
                if (apprealBean6 == null || (str4 = apprealBean6.getPhone()) == null) {
                    str4 = "";
                }
                textView14.setText(str4);
            }
            TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_time);
            if (textView15 != null) {
                textView15.setText(getString(R.string.login_appreal_expire_time));
            }
            TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_time_number);
            if (textView16 != null) {
                ApprealBean apprealBean7 = this.apprealBean;
                if (apprealBean7 == null || (str3 = apprealBean7.getExpireTime()) == null) {
                    str3 = "";
                }
                textView16.setText(str3);
            }
            ((ImageView) _$_findCachedViewById(R$id.image)).setImageResource(R.drawable.appreal_pass);
        }
    }
}
