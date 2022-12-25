package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_income_and_cash)
/* renamed from: com.one.tomato.ui.income.IncomeAndCashActivity */
/* loaded from: classes3.dex */
public class IncomeAndCashActivity extends BaseActivity {
    @ViewInject(R.id.back)
    private ImageView back;
    private TabFragmentAdapter fragmentAdapter;
    @ViewInject(R.id.tab_layout)
    private PagerSlidingTabStrip tab_layout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    private List<String> stringList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();

    public static void startActivity(Context context, int i) {
        Intent intent = new Intent();
        intent.setClass(context, IncomeAndCashActivity.class);
        intent.putExtra("accountType", i);
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

    private void initTabs() {
        int intExtra = getIntent().getIntExtra("accountType", 1);
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            this.viewpager.setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(getString(R.string.income_income_recode_title));
        this.stringList.add(getString(R.string.income_cash_recode_title));
        IncomeListFragment incomeListFragment = IncomeListFragment.getInstance(intExtra);
        CashListFragment cashListFragment = CashListFragment.getInstance(intExtra);
        this.fragmentList.add(incomeListFragment);
        this.fragmentList.add(cashListFragment);
        this.fragmentAdapter.notifyDataSetChanged();
        this.tab_layout.setViewPager(this.viewpager);
        PagerSlidingTabUtil.setExpandTabsValue(this, this.tab_layout);
        this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
        this.viewpager.setCurrentItem(0);
    }

    private void setListener() {
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeAndCashActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                IncomeAndCashActivity.this.onBackPressed();
            }
        });
    }
}
