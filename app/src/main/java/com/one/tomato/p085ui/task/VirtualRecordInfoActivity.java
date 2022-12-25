package com.one.tomato.p085ui.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_virtual_record_info)
/* renamed from: com.one.tomato.ui.task.VirtualRecordInfoActivity */
/* loaded from: classes3.dex */
public class VirtualRecordInfoActivity extends BaseActivity {
    @ViewInject(R.id.tab_layout)
    private PagerSlidingTabStrip tab_layout;
    @ViewInject(R.id.tv_current_earn_strategy)
    private TextView tv_current_earn_strategy;
    @ViewInject(R.id.tv_current_virtual)
    private TextView tv_current_virtual;
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    private List<String> urlList = new ArrayList();
    private List<String> businessList = new ArrayList();
    private List<String> stringList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();
    private TabFragmentAdapter fragmentAdapters = null;

    public static void startActivity(Context context, String str) {
        Intent intent = new Intent();
        intent.setClass(context, VirtualRecordInfoActivity.class);
        intent.putExtra("virtual_current", str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
    }

    private void initView() {
        initTitleBar();
        this.titleTV.setText(R.string.common_potato_virtual);
        this.tv_current_virtual.setText(getIntent().getExtras().getString("virtual_current"));
        this.stringList.add(AppUtil.getString(R.string.virtual_income_record));
        this.stringList.add(AppUtil.getString(R.string.virtual_consume_record));
        this.urlList.add("/app/account/list");
        this.urlList.add("/app/account/list");
        this.businessList.add("in");
        this.businessList.add("out");
        if (this.fragmentAdapters == null) {
            for (int i = 0; i < this.urlList.size(); i++) {
                this.fragmentList.add(VirtualRecordFragment.getInstance(this.urlList.get(i), this.businessList.get(i)));
            }
            this.fragmentAdapters = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            this.viewpager.setAdapter(this.fragmentAdapters);
        }
        this.tab_layout.setViewPager(this.viewpager);
        this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
        this.viewpager.setCurrentItem(0);
        PagerSlidingTabUtil.setAllTabsValue(this, this.tab_layout, true);
        this.tv_current_earn_strategy.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.task.VirtualRecordInfoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HtmlConfig htmlConfig = new HtmlConfig();
                htmlConfig.setUrl(DomainServer.getInstance().getServerUrl() + "/active/getbi.html");
                htmlConfig.setTitle(AppUtil.getString(R.string.virtual_earn));
                HtmlShowActivity.startActivity(((BaseActivity) VirtualRecordInfoActivity.this).mContext, htmlConfig);
            }
        });
    }
}
