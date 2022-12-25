package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_cash_list)
/* renamed from: com.one.tomato.ui.income.CashListActivity */
/* loaded from: classes3.dex */
public class CashListActivity extends BaseActivity {
    public static void startActivity(Context context, int i) {
        Intent intent = new Intent();
        intent.setClass(context, CashListActivity.class);
        intent.putExtra("accountType", i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.income_cash_recode_title);
        int intExtra = getIntent().getIntExtra("accountType", 0);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(R.id.frameLayout, CashListFragment.getInstance(intExtra));
        beginTransaction.commitAllowingStateLoss();
    }
}
