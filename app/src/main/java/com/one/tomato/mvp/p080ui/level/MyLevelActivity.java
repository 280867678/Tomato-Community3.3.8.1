package com.one.tomato.mvp.p080ui.level;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.LevelExpire;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.level.LevelFragment;
import com.one.tomato.p085ui.task.TaskCenterActivity;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.collections.MutableCollections;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MyLevelActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.level.MyLevelActivity */
/* loaded from: classes3.dex */
public final class MyLevelActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private LevelBean levelBean;
    private UserInfo userInfo;
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

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
        return R.layout.activity_my_level_new;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    /* compiled from: MyLevelActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.level.MyLevelActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, MyLevelActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        this.levelBean = DBUtil.getLevelBean();
        this.userInfo = DBUtil.getUserInfo();
        initInfo();
        initAdapter();
        addListener();
        initTabs();
    }

    private final void initInfo() {
        Integer num;
        Float f;
        LevelBean levelBean;
        Context mContext = getMContext();
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_head);
        UserInfo userInfo = this.userInfo;
        Integer num2 = null;
        ImageLoaderUtil.loadHeadImage(mContext, imageView, new ImageBean(userInfo != null ? userInfo.getAvatar() : null), 100, 100);
        String[] nicks = getResources().getStringArray(R.array.level_nick);
        ArrayList<String> arrayList = this.stringList;
        Intrinsics.checkExpressionValueIsNotNull(nicks, "nicks");
        MutableCollections.addAll(arrayList, nicks);
        LevelBean levelBean2 = this.levelBean;
        Integer valueOf = levelBean2 != null ? Integer.valueOf(levelBean2.getLevelNickIndex() - 1) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        String str = nicks[valueOf.intValue()];
        LevelBean levelBean3 = this.levelBean;
        ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText("Lv." + (levelBean3 != null ? Integer.valueOf(levelBean3.getCurrentLevelIndex()) : null));
        ((TextView) _$_findCachedViewById(R$id.tv_level_nick)).setText(getString(R.string.common_tomato) + "·" + str);
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_cur_level_num);
        LevelBean levelBean4 = this.levelBean;
        textView.setText(levelBean4 != null ? String.valueOf(levelBean4.getCurrentLevelValue()) : null);
        LevelBean levelBean5 = this.levelBean;
        if (levelBean5 != null) {
            int nextLevelValue = levelBean5.getNextLevelValue();
            LevelBean levelBean6 = this.levelBean;
            Integer valueOf2 = levelBean6 != null ? Integer.valueOf(levelBean6.getCurrentLevelValue()) : null;
            if (valueOf2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            num = Integer.valueOf(nextLevelValue - valueOf2.intValue());
        } else {
            num = null;
        }
        ((TextView) _$_findCachedViewById(R$id.tv_next_level_num_need)).setText(getString(R.string.level_next_num_need, new Object[]{num}));
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R$id.progressBar);
        Intrinsics.checkExpressionValueIsNotNull(progressBar, "progressBar");
        LevelBean levelBean7 = this.levelBean;
        Integer valueOf3 = levelBean7 != null ? Integer.valueOf(levelBean7.getNextLevelValue()) : null;
        if (valueOf3 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        progressBar.setMax(valueOf3.intValue());
        ProgressBar progressBar2 = (ProgressBar) _$_findCachedViewById(R$id.progressBar);
        Intrinsics.checkExpressionValueIsNotNull(progressBar2, "progressBar");
        LevelBean levelBean8 = this.levelBean;
        Integer valueOf4 = levelBean8 != null ? Integer.valueOf(levelBean8.getCurrentLevelValue()) : null;
        if (valueOf4 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        progressBar2.setProgress(valueOf4.intValue());
        LevelBean levelBean9 = this.levelBean;
        if (levelBean9 != null) {
            float currentLevelValue = levelBean9.getCurrentLevelValue();
            Float valueOf5 = this.levelBean != null ? Float.valueOf(levelBean.getNextLevelValue()) : null;
            if (valueOf5 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            f = Float.valueOf(currentLevelValue / valueOf5.floatValue());
        } else {
            f = null;
        }
        float width = DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(60.0f);
        if (f == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        float floatValue = width * f.floatValue();
        RelativeLayout rl_cur_level_num = (RelativeLayout) _$_findCachedViewById(R$id.rl_cur_level_num);
        Intrinsics.checkExpressionValueIsNotNull(rl_cur_level_num, "rl_cur_level_num");
        ViewGroup.LayoutParams layoutParams = rl_cur_level_num.getLayoutParams();
        if (layoutParams != null) {
            ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams((ConstraintLayout.LayoutParams) layoutParams);
            ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = (int) (floatValue + DisplayMetricsUtils.dp2px(10.0f));
            RelativeLayout rl_cur_level_num2 = (RelativeLayout) _$_findCachedViewById(R$id.rl_cur_level_num);
            Intrinsics.checkExpressionValueIsNotNull(rl_cur_level_num2, "rl_cur_level_num");
            rl_cur_level_num2.setLayoutParams(layoutParams2);
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_xinyu_num);
            LevelBean levelBean10 = this.levelBean;
            if (levelBean10 != null) {
                num2 = Integer.valueOf(levelBean10.getPrestigeBalance());
            }
            textView2.setText(String.valueOf(num2));
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.support.constraint.ConstraintLayout.LayoutParams");
    }

    private final void initAdapter() {
        int[] iArr = {R.drawable.level_expire_1, R.drawable.level_expire_2, R.drawable.level_expire_3, R.drawable.level_expire_4};
        String[] stringArray = getResources().getStringArray(R.array.level_get_expire_title);
        String[] stringArray2 = getResources().getStringArray(R.array.level_get_expire_desc);
        final ArrayList arrayList = new ArrayList();
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            LevelExpire levelExpire = new LevelExpire();
            levelExpire.setIcon(iArr[i]);
            levelExpire.setTitle(stringArray[i]);
            levelExpire.setContent(stringArray2[i]);
            arrayList.add(levelExpire);
        }
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        BaseRecyclerViewAdapter<LevelExpire> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<LevelExpire>(this, arrayList, this, R.layout.item_level_expire, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.level.MyLevelActivity$initAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i2) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(this, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, LevelExpire itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                TextView tv_content = (TextView) holder.getView(R.id.tv_content);
                ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(itemData.getIcon());
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData.getTitle());
                Intrinsics.checkExpressionValueIsNotNull(tv_content, "tv_content");
                tv_content.setText(itemData.getContent());
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setLayoutManager(new FullyGridLayoutManager(getMContext(), 2));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.level.MyLevelActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLevelActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_prompt)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.level.MyLevelActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MyLevelActivity.this.getMContext();
                TaskCenterActivity.startEarnActivity(mContext);
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_xinyu)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.level.MyLevelActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreditActivity.Companion.startActivity(MyLevelActivity.this);
            }
        });
    }

    private final void initTabs() {
        this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        int size = this.stringList.size();
        for (int i = 0; i < size; i++) {
            LevelFragment.Companion companion = LevelFragment.Companion;
            LevelBean levelBean = this.levelBean;
            String str = this.stringList.get(i);
            Intrinsics.checkExpressionValueIsNotNull(str, "stringList[i]");
            this.fragmentList.add(companion.getInstance(levelBean, str, i));
        }
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((ViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setOffscreenPageLimit(this.fragmentList.size());
    }
}
