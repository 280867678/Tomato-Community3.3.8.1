package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment;
import com.one.tomato.mvp.p080ui.mine.view.PostSerializeManageFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_publish)
/* renamed from: com.one.tomato.ui.mine.MyPublishActivity */
/* loaded from: classes3.dex */
public class MyPublishActivity extends BaseActivity {
    @ViewInject(R.id.back)
    private ImageView back;
    private TabFragmentAdapter fragmentAdapter;
    @ViewInject(R.id.image_delete)
    private ImageView image_delete;
    private MinePostPublishFragment minePostPublishFragment;
    private PostSerializeManageFragment postSerializeManageFragment;
    @ViewInject(R.id.tab_layout)
    private PagerSlidingTabStrip tab_layout;
    @ViewInject(R.id.text_no)
    private TextView text_no;
    @ViewInject(R.id.viewpager)
    private NoHorScrollViewPager viewpager;
    private List<String> stringList = new ArrayList();
    private List<String> businessList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyPublishActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        initTabs();
        setListener();
    }

    private void setListener() {
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.-$$Lambda$MyPublishActivity$piC8CUCyoSQmG5Sy_nOFLiHkNu8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyPublishActivity.this.lambda$setListener$0$MyPublishActivity(view);
            }
        });
        this.image_delete.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.-$$Lambda$MyPublishActivity$qDreFtTxtN3-Y4jESs3HV2UpTKA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyPublishActivity.this.lambda$setListener$1$MyPublishActivity(view);
            }
        });
        this.text_no.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.-$$Lambda$MyPublishActivity$gqrK3dGL7yLuEES2KyRUdiMF-Dc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyPublishActivity.this.lambda$setListener$2$MyPublishActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$setListener$0$MyPublishActivity(View view) {
        onBackPressed();
    }

    public /* synthetic */ void lambda$setListener$1$MyPublishActivity(View view) {
        if (this.viewpager.getCurrentItem() == 0) {
            this.minePostPublishFragment.deleteOrCancel(1);
        } else {
            this.postSerializeManageFragment.deleteOrCancel(1);
        }
        this.text_no.setVisibility(0);
        this.image_delete.setVisibility(8);
    }

    public /* synthetic */ void lambda$setListener$2$MyPublishActivity(View view) {
        if (this.viewpager.getCurrentItem() == 0) {
            this.minePostPublishFragment.deleteOrCancel(2);
        } else {
            this.postSerializeManageFragment.deleteOrCancel(2);
        }
        this.text_no.setVisibility(8);
        this.image_delete.setVisibility(0);
    }

    private void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            this.viewpager.setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.post_my_publish_manage));
        this.stringList.add(AppUtil.getString(R.string.post_serialize_manage));
        this.businessList.add("publish_y");
        this.businessList.add("publish_n");
        this.minePostPublishFragment = new MinePostPublishFragment();
        this.postSerializeManageFragment = PostSerializeManageFragment.Companion.getInstance(DBUtil.getMemberId());
        this.fragmentList.add(this.minePostPublishFragment);
        this.fragmentList.add(this.postSerializeManageFragment);
        this.fragmentAdapter.notifyDataSetChanged();
        this.tab_layout.setViewPager(this.viewpager);
        PagerSlidingTabUtil.setExpandTabsValue(this, this.tab_layout);
        this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.ui.mine.MyPublishActivity.1
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MyPublishActivity.this.text_no.setVisibility(8);
                MyPublishActivity.this.image_delete.setVisibility(0);
                MyPublishActivity.this.minePostPublishFragment.deleteOrCancel(2);
                MyPublishActivity.this.postSerializeManageFragment.deleteOrCancel(2);
            }
        });
    }
}
