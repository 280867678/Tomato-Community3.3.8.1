package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p002v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ChangePoneVirify;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.view.CountryCodeActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.p085ui.feedback.FeedbackEnterActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_phone_bind)
/* renamed from: com.one.tomato.ui.setting.PhoneBindActivity */
/* loaded from: classes3.dex */
public class PhoneBindActivity extends BaseActivity {
    @ViewInject(R.id.cur_content)
    private TextView cur_content;
    @ViewInject(R.id.cur_type)
    private TextView cur_type;
    @ViewInject(R.id.et_code)
    private ClearEditText et_code;
    @ViewInject(R.id.et_content)
    private ClearEditText et_content;
    private boolean isCodeInput;
    private boolean isContentInput;
    @ViewInject(R.id.ll_change)
    private LinearLayout ll_change;
    private String sec;
    @ViewInject(R.id.tv_country_code)
    private TextView tv_country_code;
    @ViewInject(R.id.tv_error)
    private TextView tv_error;
    @ViewInject(R.id.tv_get_code)
    private TextView tv_get_code;
    @ViewInject(R.id.tv_next)
    private TextView tv_next;
    @ViewInject(R.id.tv_tip)
    private TextView tv_tip;
    private String type;
    private UserInfo userInfo;

    public static void startActivity(Context context, String str) {
        Intent intent = new Intent();
        intent.setClass(context, PhoneBindActivity.class);
        intent.putExtra("type", str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.type = getIntent().getExtras().getString("type");
        initTitleBar();
        init();
        setListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void init() {
        char c;
        this.userInfo = DBUtil.getUserInfo();
        String str = this.type;
        switch (str.hashCode()) {
            case -1584915102:
                if (str.equals("bind_change_phone_confirm")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1404232304:
                if (str.equals("bind_change_email_confirm")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -846655729:
                if (str.equals("bind_change_email")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -836632351:
                if (str.equals("bind_change_phone")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1220407578:
                if (str.equals("bind_email")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1230430956:
                if (str.equals("bind_phone")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.titleTV.setText(R.string.account_bind_phone);
            if (!TextUtils.isEmpty(PreferencesUtil.getInstance().getString("country_code"))) {
                this.tv_country_code.setText(PreferencesUtil.getInstance().getString("country_code"));
            }
            this.et_content.setHint(R.string.account_bind_phone_hint);
        } else if (c == 1) {
            this.titleTV.setText(R.string.account_bind_verify);
            this.tv_country_code.setText(this.userInfo.getCountryCodeStr());
            this.tv_country_code.setEnabled(false);
            this.et_content.setText(this.userInfo.getPhone());
            this.et_content.setEnabled(false);
            this.tv_next.setText(R.string.common_next_step);
            this.isContentInput = true;
            this.tv_get_code.setEnabled(true);
            this.tv_next.setEnabled(false);
        } else if (c == 2) {
            this.titleTV.setText(R.string.account_change_phone);
            this.ll_change.setVisibility(0);
            this.cur_content.setVisibility(0);
            this.cur_content.setText(this.userInfo.getPhone());
            this.tv_country_code.setText(this.userInfo.getCountryCodeStr());
            this.tv_country_code.setEnabled(true);
            this.et_content.setEnabled(true);
            this.et_content.setText("");
            this.et_content.setHint(R.string.account_change_phone_hint);
            this.et_code.setText("");
            this.tv_get_code.setEnabled(false);
            this.tv_get_code.setText(R.string.get_code);
            this.tv_get_code.setTextColor(getResources().getColor(R.color.text_middle));
            this.tv_next.setText(R.string.common_confirm);
            this.isContentInput = false;
            this.isCodeInput = false;
            this.tv_next.setEnabled(false);
        } else if (c == 3) {
            this.titleTV.setText(AppUtil.getString(R.string.account_bind_email));
            this.tv_country_code.setVisibility(8);
            this.et_content.setHint(R.string.register_email);
        } else if (c == 4) {
            this.titleTV.setText(R.string.account_bind_verify);
            this.tv_country_code.setVisibility(8);
            this.et_content.setText(this.userInfo.getEmail());
            this.et_content.setEnabled(false);
            this.tv_next.setText(R.string.common_next_step);
            this.isContentInput = true;
            this.tv_get_code.setEnabled(true);
            this.tv_next.setEnabled(false);
        } else if (c != 5) {
        } else {
            this.titleTV.setText(getString(R.string.account_change_email));
            this.ll_change.setVisibility(0);
            this.cur_type.setText(getString(R.string.bind_curent_email));
            this.cur_content.setVisibility(0);
            this.cur_content.setText(this.userInfo.getEmail());
            this.tv_country_code.setText(this.userInfo.getCountryCodeStr());
            this.tv_country_code.setEnabled(true);
            this.tv_country_code.setVisibility(8);
            this.et_content.setEnabled(true);
            this.et_content.setText("");
            this.et_content.setHint(R.string.register_email);
            this.et_code.setText("");
            this.tv_get_code.setEnabled(false);
            this.tv_get_code.setText(R.string.get_code);
            this.tv_get_code.setTextColor(getResources().getColor(R.color.text_middle));
            this.tv_next.setText(R.string.common_confirm);
            this.isContentInput = false;
            this.isCodeInput = false;
            this.tv_next.setEnabled(false);
        }
    }

    private void setListener() {
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    PhoneBindActivity.this.isContentInput = true;
                    PhoneBindActivity.this.tv_get_code.setEnabled(true);
                } else {
                    PhoneBindActivity.this.isContentInput = false;
                    PhoneBindActivity.this.tv_get_code.setEnabled(false);
                }
                if (!PhoneBindActivity.this.isContentInput || !PhoneBindActivity.this.isCodeInput) {
                    PhoneBindActivity.this.tv_next.setEnabled(false);
                } else {
                    PhoneBindActivity.this.tv_next.setEnabled(true);
                }
            }
        });
        this.et_code.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    PhoneBindActivity.this.isCodeInput = true;
                } else {
                    PhoneBindActivity.this.isCodeInput = false;
                }
                if (!PhoneBindActivity.this.isContentInput || !PhoneBindActivity.this.isCodeInput) {
                    PhoneBindActivity.this.tv_next.setEnabled(false);
                } else {
                    PhoneBindActivity.this.tv_next.setEnabled(true);
                }
            }
        });
        this.tv_country_code.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(((BaseActivity) PhoneBindActivity.this).mContext, 111);
            }
        });
        this.tv_get_code.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.4
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                char c;
                String str = PhoneBindActivity.this.type;
                switch (str.hashCode()) {
                    case -1584915102:
                        if (str.equals("bind_change_phone_confirm")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1404232304:
                        if (str.equals("bind_change_email_confirm")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -846655729:
                        if (str.equals("bind_change_email")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -836632351:
                        if (str.equals("bind_change_phone")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1220407578:
                        if (str.equals("bind_email")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1230430956:
                        if (str.equals("bind_phone")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    PhoneBindActivity.this.getPhoneCode();
                } else if (c == 1) {
                    PhoneBindActivity.this.getChangePhoneCode();
                } else if (c == 2) {
                    PhoneBindActivity.this.changePhoneGetNewCode();
                } else if (c == 3) {
                    PhoneBindActivity.this.getEmailCode();
                } else if (c == 4) {
                    PhoneBindActivity.this.getEmailCode();
                } else if (c != 5) {
                } else {
                    PhoneBindActivity.this.getEmailCode();
                }
            }
        });
        this.tv_next.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                char c;
                String str = PhoneBindActivity.this.type;
                switch (str.hashCode()) {
                    case -1584915102:
                        if (str.equals("bind_change_phone_confirm")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1404232304:
                        if (str.equals("bind_change_email_confirm")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -846655729:
                        if (str.equals("bind_change_email")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -836632351:
                        if (str.equals("bind_change_phone")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1220407578:
                        if (str.equals("bind_email")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1230430956:
                        if (str.equals("bind_phone")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    PhoneBindActivity.this.bindPhone();
                } else if (c == 1) {
                    PhoneBindActivity.this.changePhoneByVerify();
                } else if (c == 2) {
                    PhoneBindActivity.this.changePhoneConfirm();
                } else if (c == 3) {
                    PhoneBindActivity.this.bindEmail();
                } else if (c == 4) {
                    PhoneBindActivity.this.changeEmailByVerify();
                } else if (c != 5) {
                } else {
                    PhoneBindActivity.this.bindChangeEmail();
                }
            }
        });
        this.tv_tip.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FeedbackEnterActivity.startActivity(PhoneBindActivity.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPhoneCode() {
        this.et_code.setText("");
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfo/sendVerifyCode");
        tomatoParams.addParameter("phone", this.et_content.getText().toString());
        tomatoParams.addParameter("moduleType", 8);
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.post(new TomatoCallback(this, 1));
        TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.7
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
                TextView textView = PhoneBindActivity.this.tv_get_code;
                textView.setText(i + "s");
                PhoneBindActivity.this.tv_get_code.setEnabled(false);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.tip_color));
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                PhoneBindActivity.this.tv_get_code.setText(R.string.get_code);
                PhoneBindActivity.this.tv_get_code.setEnabled(true);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.text_middle));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getEmailCode() {
        this.et_code.setText("");
        if (this.type.equals("bind_email") || this.type.equals("bind_change_email_confirm")) {
            String trim = this.et_content.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.login_common_pelase_email));
                return;
            } else {
                ApiImplService.Companion.getApiImplService().senMail(trim, 8).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.9
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Disposable disposable) throws Exception {
                        PhoneBindActivity.this.showWaitingDialog();
                    }
                }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.8
                    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                    public void onResult(Object obj) {
                        PhoneBindActivity.this.hideWaitingDialog();
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.get_code_success));
                        TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.8.1
                            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                            public void position(int i) {
                                TextView textView = PhoneBindActivity.this.tv_get_code;
                                textView.setText(i + "s");
                                PhoneBindActivity.this.tv_get_code.setEnabled(false);
                                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.tip_color));
                            }

                            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                            public void stop() {
                                PhoneBindActivity.this.tv_get_code.setText(R.string.get_code);
                                PhoneBindActivity.this.tv_get_code.setEnabled(true);
                                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.text_middle));
                            }
                        });
                    }

                    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                    public void onResultError(ResponseThrowable responseThrowable) {
                        PhoneBindActivity.this.hideWaitingDialog();
                    }
                });
                return;
            }
        }
        sendVerifyEmailCode();
    }

    private void sendVerifyEmailCode() {
        ApiImplService.Companion.getApiImplService().sendVerifyEmailCode(DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.11
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PhoneBindActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.10
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PhoneBindActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.get_code_success));
                TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.10.1
                    @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                    public void position(int i) {
                        TextView textView = PhoneBindActivity.this.tv_get_code;
                        textView.setText(i + "s");
                        PhoneBindActivity.this.tv_get_code.setEnabled(false);
                        PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.tip_color));
                    }

                    @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                    public void stop() {
                        PhoneBindActivity.this.tv_get_code.setText(R.string.get_code);
                        PhoneBindActivity.this.tv_get_code.setEnabled(true);
                        PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.text_middle));
                    }
                });
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PhoneBindActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPhone() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfo/bindPhone");
        tomatoParams.addParameter("phone", this.et_content.getText().toString());
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.addParameter("verifyCode", this.et_code.getText().toString());
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getChangePhoneCode() {
        this.et_code.setText("");
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfoPhone/changePhone/getCodeByMemberId");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.post(new TomatoCallback(this, 3));
        TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.12
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
                TextView textView = PhoneBindActivity.this.tv_get_code;
                textView.setText(i + "s");
                PhoneBindActivity.this.tv_get_code.setEnabled(false);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.tip_color));
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                PhoneBindActivity.this.tv_get_code.setText(R.string.get_code);
                PhoneBindActivity.this.tv_get_code.setEnabled(true);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.text_middle));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindEmail() {
        HashMap hashMap = new HashMap();
        hashMap.put(NotificationCompat.CATEGORY_EMAIL, this.et_content.getText().toString());
        hashMap.put("verifyCode", this.et_code.getText().toString());
        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        ApiImplService.Companion.getApiImplService().bindEmail(hashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PhoneBindActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.13
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PhoneBindActivity.this.hideWaitingDialog();
                PhoneBindActivity.this.userInfo.setEmail(PhoneBindActivity.this.et_content.getText().toString());
                DBUtil.saveUserInfo(PhoneBindActivity.this.userInfo);
                PreferencesUtil.getInstance().putString("login_user_account", PhoneBindActivity.this.et_content.getText().toString().trim());
                PhoneBindActivity.this.finish();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PhoneBindActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindChangeEmail() {
        HashMap hashMap = new HashMap();
        hashMap.put(NotificationCompat.CATEGORY_EMAIL, this.et_content.getText().toString());
        hashMap.put("verifyCode", this.et_code.getText().toString());
        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        hashMap.put("sec", this.sec);
        ApiImplService.Companion.getApiImplService().bindChangeEmail(hashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.16
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PhoneBindActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.15
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PhoneBindActivity.this.hideWaitingDialog();
                PhoneBindActivity.this.userInfo.setEmail(PhoneBindActivity.this.et_content.getText().toString());
                DBUtil.saveUserInfo(PhoneBindActivity.this.userInfo);
                PreferencesUtil.getInstance().putString("login_user_account", PhoneBindActivity.this.et_content.getText().toString().trim());
                PhoneBindActivity.this.finish();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PhoneBindActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePhoneByVerify() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfoPhone/changePhone/auth");
        tomatoParams.addParameter("verifyCode", this.et_code.getText().toString());
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 4, ChangePoneVirify.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEmailByVerify() {
        ApiImplService.Companion.getApiImplService().changeEmailByVerify(DBUtil.getMemberId(), this.et_code.getText().toString().trim()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PhoneBindActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<ChangePoneVirify>() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.17
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ChangePoneVirify changePoneVirify) {
                PhoneBindActivity.this.hideWaitingDialog();
                PhoneBindActivity.this.sec = changePoneVirify.getSec();
                PhoneBindActivity.this.type = "bind_change_email_confirm";
                TimerTaskUtil.getInstance().onStop();
                PhoneBindActivity.this.init();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PhoneBindActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePhoneGetNewCode() {
        this.et_code.setText("");
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfoPhone/changePhone/getCodeByNewPhone");
        tomatoParams.addParameter("phone", this.et_content.getText().toString());
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 5));
        TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.19
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
                TextView textView = PhoneBindActivity.this.tv_get_code;
                textView.setText(i + "s");
                PhoneBindActivity.this.tv_get_code.setEnabled(false);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.tip_color));
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                PhoneBindActivity.this.tv_get_code.setText(R.string.get_code);
                PhoneBindActivity.this.tv_get_code.setEnabled(true);
                PhoneBindActivity.this.tv_get_code.setTextColor(PhoneBindActivity.this.getResources().getColor(R.color.text_middle));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePhoneConfirm() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfoPhone/changePhone/bindNewPhone");
        tomatoParams.addParameter("verifyCode", this.et_code.getText().toString());
        tomatoParams.addParameter("sec", this.sec);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 6));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        switch (message.what) {
            case 1:
            case 3:
            case 5:
                ToastUtil.showCenterToast((int) R.string.get_code_success);
                return;
            case 2:
            case 6:
                this.userInfo.setPhone(this.et_content.getText().toString());
                this.userInfo.setCountryCodeStr(this.tv_country_code.getText().toString());
                DBUtil.saveUserInfo(this.userInfo);
                PreferencesUtil.getInstance().putString("country_code", this.tv_country_code.getText().toString());
                finish();
                return;
            case 4:
                this.sec = ((ChangePoneVirify) baseModel.obj).getSec();
                this.type = "bind_change_phone_confirm";
                TimerTaskUtil.getInstance().onStop();
                init();
                return;
            default:
                return;
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        setErrorStr(((BaseModel) message.obj).message);
        return false;
    }

    private void setErrorStr(String str) {
        this.tv_error.setVisibility(0);
        this.tv_error.setText(str);
        this.tv_error.postDelayed(new Runnable() { // from class: com.one.tomato.ui.setting.PhoneBindActivity.20
            @Override // java.lang.Runnable
            public void run() {
                PhoneBindActivity.this.tv_error.setVisibility(4);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 111 && i2 == -1) {
            this.tv_country_code.setText(intent.getExtras().getString("country_code", "+86"));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
    }
}
