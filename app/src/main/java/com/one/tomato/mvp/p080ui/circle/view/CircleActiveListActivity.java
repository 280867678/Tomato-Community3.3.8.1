package com.one.tomato.mvp.p080ui.circle.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.MemberList;
import com.one.tomato.p085ui.mine.MemberListFragment;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_circle_active_list)
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleActiveListActivity */
/* loaded from: classes3.dex */
public class CircleActiveListActivity extends BaseRecyclerViewActivity {
    public static String INTENT_ARGS_CIRCLE_ACTIVE_INFO = "active_info";
    public static String INTENT_ARGS_CIRCLE_GROUP_ID = "group_id";
    public static String INTENT_ARGS_CIRCLE_GROUP_NAME = "group_name";
    private int groupId;
    private String groupName;
    private ArrayList<MemberList> memberInfoList;

    public static void startActivity(Context context, int i, String str) {
        Intent intent = new Intent();
        intent.setClass(context, CircleActiveListActivity.class);
        intent.putExtra(INTENT_ARGS_CIRCLE_GROUP_ID, i);
        intent.putExtra(INTENT_ARGS_CIRCLE_GROUP_NAME, str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        getIntentArgs();
        init();
    }

    private void init() {
        this.titleTV.setText(this.groupName);
        MemberListFragment memberListFragment = MemberListFragment.getInstance("/app/discover/queryDiscoverActiveUser", "circle_active_list", this.groupId);
        Bundle arguments = memberListFragment.getArguments();
        arguments.putSerializable(INTENT_ARGS_CIRCLE_ACTIVE_INFO, this.memberInfoList);
        memberListFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, memberListFragment).commit();
    }

    private void getIntentArgs() {
        Intent intent = getIntent();
        if (intent.getExtras().containsKey(INTENT_ARGS_CIRCLE_GROUP_ID)) {
            this.groupId = intent.getIntExtra(INTENT_ARGS_CIRCLE_GROUP_ID, -1);
        }
        if (intent.getExtras().containsKey(INTENT_ARGS_CIRCLE_GROUP_NAME)) {
            this.groupName = intent.getStringExtra(INTENT_ARGS_CIRCLE_GROUP_NAME);
        }
        if (intent.getExtras().containsKey(INTENT_ARGS_CIRCLE_ACTIVE_INFO)) {
            this.memberInfoList = (ArrayList) intent.getSerializableExtra(INTENT_ARGS_CIRCLE_ACTIVE_INFO);
        }
    }
}
