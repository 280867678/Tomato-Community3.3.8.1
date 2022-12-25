package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.mvp.p080ui.post.view.MyHomePagePostFragment;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_collect_post)
/* renamed from: com.one.tomato.ui.mine.MyCollectPostActivity */
/* loaded from: classes3.dex */
public class MyCollectPostActivity extends BaseActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyCollectPostActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.my_collect_title);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(R.id.framelayout, MyHomePagePostFragment.Companion.getInstance(-1, "collect", true));
        beginTransaction.commit();
    }
}
