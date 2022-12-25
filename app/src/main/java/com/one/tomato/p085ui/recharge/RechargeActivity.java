package com.one.tomato.p085ui.recharge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.mvp.base.BaseApplication;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_recharge)
/* renamed from: com.one.tomato.ui.recharge.RechargeActivity */
/* loaded from: classes3.dex */
public class RechargeActivity extends BaseActivity {
    @ViewInject(R.id.iv_back)
    ImageView iv_back;
    private RechargeFragment rechargeFragment;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RechargeActivity.class);
        context.startActivity(intent);
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.rechargeFragment = new RechargeFragment();
        beginTransaction.add(R.id.framelayout, this.rechargeFragment);
        beginTransaction.commitAllowingStateLoss();
        if (BaseApplication.instance.isChess()) {
            this.iv_back.setVisibility(0);
            this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    RechargeActivity.this.onBackPressed();
                }
            });
        }
    }
}
