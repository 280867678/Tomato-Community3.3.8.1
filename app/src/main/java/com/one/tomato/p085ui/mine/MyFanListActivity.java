package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.broccoli.p150bh.R;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_fan_list)
/* renamed from: com.one.tomato.ui.mine.MyFanListActivity */
/* loaded from: classes3.dex */
public class MyFanListActivity extends BaseRecyclerViewActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyFanListActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.my_fan_list_title);
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, MemberListFragment.getInstance("/app/memberFollow/fanslist", "fan_list")).commit();
    }
}
