package com.one.tomato.p085ui.lockscreen;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.p079db.LockScreenInfo;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.widget.SeparatedEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_lock_screen_entry_pwd)
/* renamed from: com.one.tomato.ui.lockscreen.LockScreenPwdEnterActivity */
/* loaded from: classes3.dex */
public class LockScreenPwdEnterActivity extends BaseActivity {
    private TextView cententText;
    @ViewInject(R.id.cl_root_view)
    private View cl_root_view;
    @ViewInject(R.id.et_lock_screen_pwd)
    private SeparatedEditText et_lock_screen_pwd;
    private Integer failCount = 5;
    private LockScreenInfo lockScreenInfo;
    @ViewInject(R.id.tv_lock_screen_bottom_tip)
    private TextView tv_lock_screen_bottom_tip;
    @ViewInject(R.id.tv_lock_screen_tip)
    private TextView tv_lock_screen_tip;
    @ViewInject(R.id.view_pop)
    private View view_pop;

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LockScreenPwdEnterActivity.class);
        ((Activity) context).startActivityForResult(intent, 1);
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.lockScreenInfo = DBUtil.getLockScreenInfo();
        if (this.lockScreenInfo.getErrorCount() <= 1) {
            LoginAccountVerityActivity.startActivity((Context) this, true);
            return;
        }
        initTitleBar();
        init();
        setListener();
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.et_lock_screen_pwd.setFocusable(true);
        this.et_lock_screen_pwd.setFocusableInTouchMode(true);
        this.et_lock_screen_pwd.requestFocusFromTouch();
        this.et_lock_screen_pwd.requestFocus();
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.et_lock_screen_pwd.clearAnimation();
    }

    @TargetApi(21)
    private void init() {
        getWindow().getDecorView().setBackgroundResource(R.color.white);
        this.failCount = Integer.valueOf(this.lockScreenInfo.getErrorCount());
        this.et_lock_screen_pwd.setShowSoftInputOnFocus(true);
        this.tv_lock_screen_tip.setText(R.string.lock_screen_entry_pwd_tip);
        this.rl_title_bg.setVisibility(8);
        this.backImg.setVisibility(8);
        this.tipDialog = new CustomAlertDialog(this, false);
        this.tipDialog.setBackgroundResource(R.drawable.common_shape_solid_corner12_white);
        this.tipDialog.setCanceledOnTouchOutside(false);
        this.cententText = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.dimen_14);
        layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.dimen_16);
        layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.dimen_16);
        this.cententText.setLayoutParams(layoutParams);
        this.cententText.setTextSize(2, 14.0f);
        this.cententText.setTextColor(getResources().getColor(R.color.text_969696));
        this.cententText.setGravity(1);
        this.tipDialog.setContentView(this.cententText);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.dimen_24);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.dimen_10);
        this.tipDialog.getCancelButton().setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
        this.tipDialog.getConfirmButton().setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
    }

    private void setListener() {
        this.et_lock_screen_pwd.setTextChangedListener(new SeparatedEditText.TextChangedListener() { // from class: com.one.tomato.ui.lockscreen.LockScreenPwdEnterActivity.1
            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textChanged(CharSequence charSequence) {
            }

            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textCompleted(CharSequence charSequence) {
                if (LockScreenPwdEnterActivity.this.veitifyLockPwd(charSequence.toString())) {
                    if (LockScreenPwdEnterActivity.this.failCount.intValue() < 5) {
                        LockScreenPwdEnterActivity.this.lockScreenInfo.setErrorCount(5);
                        DBUtil.saveLockScreenInfo(LockScreenPwdEnterActivity.this.lockScreenInfo);
                    }
                    LockScreenPwdEnterActivity.this.setResult(800);
                    LockScreenPwdEnterActivity.this.finish();
                    return;
                }
                LockScreenPwdEnterActivity lockScreenPwdEnterActivity = LockScreenPwdEnterActivity.this;
                lockScreenPwdEnterActivity.errorAnim(lockScreenPwdEnterActivity.et_lock_screen_pwd);
                LockScreenPwdEnterActivity.this.et_lock_screen_pwd.clearText();
                if (LockScreenPwdEnterActivity.this.tv_lock_screen_bottom_tip.getVisibility() != 0) {
                    LockScreenPwdEnterActivity.this.tv_lock_screen_bottom_tip.setVisibility(0);
                }
                if (LockScreenPwdEnterActivity.this.failCount.intValue() <= 1) {
                    LockScreenPwdEnterActivity.this.showLockScreenPwdErrorDialog();
                    return;
                }
                LockScreenPwdEnterActivity lockScreenPwdEnterActivity2 = LockScreenPwdEnterActivity.this;
                lockScreenPwdEnterActivity2.failCount = Integer.valueOf(lockScreenPwdEnterActivity2.failCount.intValue() - 1);
                LockScreenPwdEnterActivity.this.lockScreenInfo.setErrorCount(LockScreenPwdEnterActivity.this.failCount.intValue());
                DBUtil.saveLockScreenInfo(LockScreenPwdEnterActivity.this.lockScreenInfo);
                Integer[] numArr = {LockScreenPwdEnterActivity.this.failCount};
                LockScreenPwdEnterActivity.this.tv_lock_screen_tip.setTextColor(LockScreenPwdEnterActivity.this.getResources().getColor(R.color.red_ff5252));
                LockScreenPwdEnterActivity.this.tv_lock_screen_tip.setText(LockScreenPwdEnterActivity.this.getResources().getString(R.string.lock_screen_verifty_fail, numArr));
            }
        });
        this.tv_lock_screen_bottom_tip.post(new Runnable() { // from class: com.one.tomato.ui.lockscreen.LockScreenPwdEnterActivity.2
            @Override // java.lang.Runnable
            public void run() {
                ViewUtil.controlKeyboardLayout(LockScreenPwdEnterActivity.this.cl_root_view, LockScreenPwdEnterActivity.this.tv_lock_screen_bottom_tip, new ViewUtil.OnResizeListener() { // from class: com.one.tomato.ui.lockscreen.LockScreenPwdEnterActivity.2.1
                    @Override // com.one.tomato.utils.ViewUtil.OnResizeListener
                    public void OnSoftPop(int i) {
                        if (i > 0) {
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) LockScreenPwdEnterActivity.this.view_pop.getLayoutParams();
                            ((ViewGroup.MarginLayoutParams) layoutParams).height = i + LockScreenPwdEnterActivity.this.getResources().getDimensionPixelSize(R.dimen.dimen_16);
                            LockScreenPwdEnterActivity.this.view_pop.setLayoutParams(layoutParams);
                            LockScreenPwdEnterActivity.this.view_pop.setVisibility(0);
                            return;
                        }
                        LockScreenPwdEnterActivity.this.view_pop.setVisibility(8);
                    }

                    @Override // com.one.tomato.utils.ViewUtil.OnResizeListener
                    public void OnSoftClose() {
                        LockScreenPwdEnterActivity.this.view_pop.setVisibility(8);
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void errorAnim(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", -30.0f, 30.0f, -20.0f, 20.0f, -10.0f, 10.0f, -5.0f, 5.0f, 0.0f);
        ofFloat.setInterpolator(new AnticipateOvershootInterpolator());
        ofFloat.setDuration(1000L);
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean veitifyLockPwd(String str) {
        try {
            String lockScreenPwd = this.lockScreenInfo.getLockScreenPwd();
            if (TextUtils.isEmpty(lockScreenPwd)) {
                return false;
            }
            return str.equals(lockScreenPwd);
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLockScreenPwdErrorDialog() {
        this.tipDialog.bottomButtonVisiblity(2);
        this.tipDialog.setTitle(R.string.lock_screen_fail);
        this.cententText.setText(R.string.lock_screen_fail_five_times);
        this.tipDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        this.tipDialog.setConfirmButtonTextColor(R.color.white);
        this.tipDialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.lockscreen.LockScreenPwdEnterActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LoginAccountVerityActivity.startActivity((Context) LockScreenPwdEnterActivity.this, true);
                ((BaseActivity) LockScreenPwdEnterActivity.this).tipDialog.dismiss();
            }
        });
        this.tipDialog.show();
    }

    @Event({R.id.tv_lock_screen_bottom_tip})
    private void onClick(View view) {
        if (view.getId() != R.id.tv_lock_screen_bottom_tip) {
            return;
        }
        LoginAccountVerityActivity.startActivity(this, this.lockScreenInfo.getErrorCount() <= 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (800 == i2) {
            setResult(800);
            finish();
        }
    }
}
