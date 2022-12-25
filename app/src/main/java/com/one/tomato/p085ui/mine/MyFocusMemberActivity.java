package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.mvp.p080ui.circle.view.CircleListFragment;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_focus_member)
/* renamed from: com.one.tomato.ui.mine.MyFocusMemberActivity */
/* loaded from: classes3.dex */
public class MyFocusMemberActivity extends BaseRecyclerViewActivity {
    @ViewInject(R.id.back)
    private ImageView back;
    private TabFragmentAdapter fragmentAdapter;
    @ViewInject(R.id.tab_layout)
    private PagerSlidingTabStrip tab_layout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    private List<String> stringList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();

    public MyFocusMemberActivity() {
        new ArrayList();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyFocusMemberActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        initTabs();
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyFocusMemberActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyFocusMemberActivity.this.onBackPressed();
            }
        });
    }

    private void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            this.viewpager.setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.my_focus_member_list));
        this.stringList.add(AppUtil.getString(R.string.my_focus_circle_list));
        this.fragmentList.add(MemberListFragment.getInstance("/app/memberFollow/list", "focus_member_list"));
        this.fragmentList.add(CircleListFragment.getInstance("/app/follow/list", 0, "focus_circle_list"));
        this.fragmentAdapter.notifyDataSetChanged();
        this.tab_layout.setViewPager(this.viewpager);
        PagerSlidingTabUtil.setExpandTabsValue(this, this.tab_layout);
        this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
    }
}
