package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaTumbeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity */
/* loaded from: classes3.dex */
public final class PaPaTumbeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PaPaTabAdapter adapter;
    private List<Fragment> listFragment = new ArrayList();
    private List<String> listStr = new ArrayList();
    private int rankType;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_pa_pa_tumbe;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PaPaTumbeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(int i, Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent(context, PaPaTumbeActivity.class);
            intent.putExtra("rankType", i);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        TabLayout.Tab tabAt;
        Intent intent = getIntent();
        this.rankType = intent != null ? intent.getIntExtra("rankType", 0) : 0;
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaTumbeActivity.this.onBackPressed();
                }
            });
        }
        List<String> list = this.listStr;
        String string = getString(R.string.papa_hot_tumbe_daliy);
        Intrinsics.checkExpressionValueIsNotNull(string, "getString(R.string.papa_hot_tumbe_daliy)");
        list.add(string);
        List<String> list2 = this.listStr;
        String string2 = getString(R.string.papa_hot_tumbe_week);
        Intrinsics.checkExpressionValueIsNotNull(string2, "getString(R.string.papa_hot_tumbe_week)");
        list2.add(string2);
        List<String> list3 = this.listStr;
        String string3 = getString(R.string.papa_hot_tumbe_quarter);
        Intrinsics.checkExpressionValueIsNotNull(string3, "getString(R.string.papa_hot_tumbe_quarter)");
        list3.add(string3);
        List<String> list4 = this.listStr;
        String string4 = getString(R.string.papa_hot_tumbe_month);
        Intrinsics.checkExpressionValueIsNotNull(string4, "getString(R.string.papa_hot_tumbe_month)");
        list4.add(string4);
        List<String> list5 = this.listStr;
        String string5 = getString(R.string.papa_hot_tumbe_year);
        Intrinsics.checkExpressionValueIsNotNull(string5, "getString(R.string.papa_hot_tumbe_year)");
        list5.add(string5);
        this.listFragment.add(NewPaPaTabFragment.Companion.initInstance(true, 1, this.rankType));
        this.listFragment.add(NewPaPaTabFragment.Companion.initInstance(true, 2, this.rankType));
        this.listFragment.add(NewPaPaTabFragment.Companion.initInstance(true, 3, this.rankType));
        this.listFragment.add(NewPaPaTabFragment.Companion.initInstance(true, 4, this.rankType));
        this.listFragment.add(NewPaPaTabFragment.Companion.initInstance(true, 5, this.rankType));
        this.adapter = new PaPaTabAdapter(getSupportFragmentManager(), this.listFragment, this.listStr);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.adapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setCurrentItem(0);
        }
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setOffscreenPageLimit(this.listFragment.size());
        }
        TabLayout tabLayout = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager((PreviewViewPager) _$_findCachedViewById(R$id.preview_pager));
        }
        int i = 0;
        while (true) {
            View view = null;
            if (i < 5) {
                TabLayout tabLayout2 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                TabLayout.Tab tabAt2 = tabLayout2 != null ? tabLayout2.getTabAt(i) : null;
                TextView textView = new TextView(getMContext());
                textView.setText(this.listStr.get(i));
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                textView.setTextSize(14.0f);
                if (tabAt2 != null) {
                    tabAt2.setCustomView(textView);
                }
                i++;
            } else {
                TabLayout tabLayout3 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                if (tabLayout3 != null && (tabAt = tabLayout3.getTabAt(0)) != null) {
                    view = tabAt.getCustomView();
                }
                TextView textView2 = (TextView) view;
                if (textView2 != null) {
                    textView2.setTextSize(18.0f);
                }
                PreviewViewPager previewViewPager4 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
                if (previewViewPager4 != null) {
                    previewViewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity$initView$2
                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageScrollStateChanged(int i2) {
                        }

                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageScrolled(int i2, float f, int i3) {
                        }

                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageSelected(int i2) {
                            CoordinatorLayout coordinatorLayout;
                            if (i2 == 0) {
                                CoordinatorLayout coordinatorLayout2 = (CoordinatorLayout) PaPaTumbeActivity.this._$_findCachedViewById(R$id.tumbe_background);
                                if (coordinatorLayout2 == null) {
                                    return;
                                }
                                coordinatorLayout2.setBackground(ContextCompat.getDrawable(PaPaTumbeActivity.this, R.drawable.papa_tumbe_one));
                            } else if (i2 == 1) {
                                CoordinatorLayout coordinatorLayout3 = (CoordinatorLayout) PaPaTumbeActivity.this._$_findCachedViewById(R$id.tumbe_background);
                                if (coordinatorLayout3 == null) {
                                    return;
                                }
                                coordinatorLayout3.setBackground(ContextCompat.getDrawable(PaPaTumbeActivity.this, R.drawable.papa_tumbe_two));
                            } else if (i2 == 2) {
                                CoordinatorLayout coordinatorLayout4 = (CoordinatorLayout) PaPaTumbeActivity.this._$_findCachedViewById(R$id.tumbe_background);
                                if (coordinatorLayout4 == null) {
                                    return;
                                }
                                coordinatorLayout4.setBackground(ContextCompat.getDrawable(PaPaTumbeActivity.this, R.drawable.papa_tumbe_three));
                            } else if (i2 != 3) {
                                if (i2 != 4 || (coordinatorLayout = (CoordinatorLayout) PaPaTumbeActivity.this._$_findCachedViewById(R$id.tumbe_background)) == null) {
                                    return;
                                }
                                coordinatorLayout.setBackground(ContextCompat.getDrawable(PaPaTumbeActivity.this, R.drawable.papa_tumbe_five));
                            } else {
                                CoordinatorLayout coordinatorLayout5 = (CoordinatorLayout) PaPaTumbeActivity.this._$_findCachedViewById(R$id.tumbe_background);
                                if (coordinatorLayout5 == null) {
                                    return;
                                }
                                coordinatorLayout5.setBackground(ContextCompat.getDrawable(PaPaTumbeActivity.this, R.drawable.papa_tumbe_four));
                            }
                        }
                    });
                }
                TabLayout tabLayout4 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                if (tabLayout4 != null) {
                    tabLayout4.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity$initView$3
                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabReselected(TabLayout.Tab tab) {
                        }

                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabUnselected(TabLayout.Tab tab) {
                            TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                            if (textView3 != null) {
                                textView3.setTextSize(14.0f);
                            }
                        }

                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabSelected(TabLayout.Tab tab) {
                            TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                            if (textView3 != null) {
                                textView3.setTextSize(18.0f);
                            }
                        }
                    });
                }
                AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                if (appBarLayout == null) {
                    return;
                }
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaTumbeActivity$initView$4
                    @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
                    public void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                        float abs = Math.abs(i2) / (appBarLayout2 != null ? appBarLayout2.getTotalScrollRange() : 0);
                        LogUtil.m3787d("yan2", "偏移量-------" + abs);
                        TextView textView3 = (TextView) PaPaTumbeActivity.this._$_findCachedViewById(R$id.toolbar_title);
                        if (textView3 != null) {
                            textView3.setAlpha(abs);
                        }
                        ImageView imageView2 = (ImageView) PaPaTumbeActivity.this._$_findCachedViewById(R$id.image_hot);
                        if (imageView2 != null) {
                            imageView2.setAlpha(1 - abs);
                        }
                    }
                });
                return;
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        int i = this.rankType;
        if (i == 1) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.toolbar_title);
            if (textView != null) {
                textView.setText(getString(R.string.papa_hot_tumbe_browse));
            }
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_hot);
            if (imageView == null) {
                return;
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.papa_hot_play_image));
        } else if (i == 2) {
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.toolbar_title);
            if (textView2 != null) {
                textView2.setText(getString(R.string.papa_hot_tumbe_tub));
            }
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_hot);
            if (imageView2 == null) {
                return;
            }
            imageView2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.papa_hot_tumbe_image));
        } else if (i != 3) {
        } else {
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.toolbar_title);
            if (textView3 != null) {
                textView3.setText(getString(R.string.papa_hot_tumbe_collect));
            }
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_hot);
            if (imageView3 == null) {
                return;
            }
            imageView3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.papa_hot_collect_image));
        }
    }
}
