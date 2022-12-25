package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_my_share)
/* renamed from: com.one.tomato.ui.mine.MyShareActivity */
/* loaded from: classes3.dex */
public class MyShareActivity extends BaseActivity {
    private MyShareFragment myShareFragment;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MyShareActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.my_spread_title);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.my_spread_sub_title);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.myShareFragment = new MyShareFragment();
        beginTransaction.add(R.id.framelayout, this.myShareFragment);
        beginTransaction.commitAllowingStateLoss();
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyShareActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyShareListActivity.startActivity(((BaseActivity) MyShareActivity.this).mContext);
            }
        });
    }
}
