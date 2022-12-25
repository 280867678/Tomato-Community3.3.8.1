package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.utils.recharge.AlipayUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_alipay_bind_success)
/* renamed from: com.one.tomato.ui.income.AlipayBindSuccessActivity */
/* loaded from: classes3.dex */
public class AlipayBindSuccessActivity extends BaseActivity {
    private String alipayAccount;
    @ViewInject(R.id.tv_alipay_bind_account)
    private TextView tv_alipay_bind_account;

    public static void startActivity(Context context, String str) {
        Intent intent = new Intent();
        intent.putExtra("alipay_account", str);
        intent.setClass(context, AlipayBindSuccessActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        init();
        fetchIntentArg();
    }

    private void init() {
        this.titleTV.setText(R.string.alipay_bind_title);
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        if (intent == null || !intent.getExtras().containsKey("alipay_account")) {
            return;
        }
        this.alipayAccount = intent.getExtras().getString("alipay_account");
        setAlipayAccount(this.alipayAccount);
    }

    private void setAlipayAccount(String str) {
        this.tv_alipay_bind_account.setText(AlipayUtils.getInstance().hideAlipayAccount(str));
    }

    @Event({R.id.tv_alipay_update})
    private void onClick(View view) {
        if (view.getId() != R.id.tv_alipay_update) {
            return;
        }
        AlipayBindActivity.startActivity((Context) this, true);
        finish();
    }
}
