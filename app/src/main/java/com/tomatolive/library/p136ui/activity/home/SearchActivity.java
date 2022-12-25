package com.tomatolive.library.p136ui.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.KeywordEvent;
import com.tomatolive.library.model.event.SearchEvent;
import com.tomatolive.library.model.p135db.SearchKeywordEntity;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.adapter.MenuTabAdapter;
import com.tomatolive.library.p136ui.adapter.SearchKeywordAdapter;
import com.tomatolive.library.p136ui.fragment.SearchAllFragment;
import com.tomatolive.library.p136ui.fragment.SearchAnchorFragment;
import com.tomatolive.library.p136ui.fragment.SearchLiveFragment;
import com.tomatolive.library.p136ui.presenter.SearchPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.headview.SearchHistoryHeadView;
import com.tomatolive.library.p136ui.view.iview.ISearchView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.p136ui.view.widget.tagview.TagView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.home.SearchActivity */
/* loaded from: classes3.dex */
public class SearchActivity extends BaseActivity<SearchPresenter> implements ISearchView, SearchAllFragment.OnFragmentInteractionListener {
    private EditText etSearch;
    private FrameLayout flKeywordBg;
    private List<BaseFragment> fragmentList;
    private SearchHistoryHeadView headView;
    private boolean isHistoryWord;
    private boolean isRecommendWord;
    private boolean isTagKey = false;
    private HomeLiveAdapter mAdapter;
    private LinearLayout mLLViewPagerBg;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewKeyword;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private SearchKeywordAdapter searchKeywordAdapter;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public SearchPresenter mo6636createPresenter() {
        return new SearchPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_search;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setTitle(getString(R$string.fq_hit_home_search));
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mRecyclerViewKeyword = (RecyclerView) findViewById(R$id.recycler_view_keyword);
        this.mLLViewPagerBg = (LinearLayout) findViewById(R$id.ll_view_pager);
        this.magicIndicator = (MagicIndicator) findViewById(R$id.magic_indicator);
        this.mViewPager = (ViewPager) findViewById(R$id.view_pager);
        this.etSearch = (EditText) findViewById(R$id.et_search);
        this.flKeywordBg = (FrameLayout) findViewById(R$id.fl_keyword_bg);
        initKeywordAdapter();
        initAdapter();
        ((SearchPresenter) this.mPresenter).getLiveEnjoyList(this.mStateView);
        ((SearchPresenter) this.mPresenter).getHotKeyList();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$uUzdjp5drpUrzLPAAaKrPNtTrgU
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                SearchActivity.this.lambda$initListener$0$SearchActivity();
            }
        });
        findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$13vrveHjt-jngE508shU4niCfyo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchActivity.this.lambda$initListener$1$SearchActivity(view);
            }
        });
        this.headView.setOnTagClickListener(new TagView.OnTagClickListener() { // from class: com.tomatolive.library.ui.activity.home.SearchActivity.1
            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onSelectedTagDrag(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagCrossClick(int i) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagLongClick(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagClick(int i, String str) {
                SearchActivity.this.isTagKey = true;
                SearchActivity.this.isRecommendWord = true;
                SearchActivity.this.isHistoryWord = false;
                SearchActivity.this.setSearchText(str);
            }
        }, new TagView.OnTagClickListener() { // from class: com.tomatolive.library.ui.activity.home.SearchActivity.2
            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onSelectedTagDrag(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagCrossClick(int i) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagLongClick(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagClick(int i, String str) {
                SearchActivity.this.isTagKey = true;
                SearchActivity.this.isHistoryWord = true;
                SearchActivity.this.isRecommendWord = false;
                SearchActivity.this.setSearchText(str);
            }
        });
        this.headView.findViewById(R$id.tv_clear).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$r9uxXfyYvxhsO_zjV_7GDUV3w0s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchActivity.this.lambda$initListener$2$SearchActivity(view);
            }
        });
        this.etSearch.setOnClickListener($$Lambda$SearchActivity$z_7bK9UUfyvVora6X_hCVfq_4w.INSTANCE);
        this.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$OnG1zGs3izPBNdw82X4hJEzyfS8
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return SearchActivity.this.lambda$initListener$4$SearchActivity(textView, i, keyEvent);
            }
        });
        this.searchKeywordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$aTZktMs4Nr_FODXV_sqRPwUmoG0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SearchActivity.this.lambda$initListener$5$SearchActivity(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$dnmnCGjlDw35HQwSRL191iqk38o
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SearchActivity.this.lambda$initListener$6$SearchActivity(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$SearchActivity() {
        ((SearchPresenter) this.mPresenter).getLiveEnjoyList(this.mStateView);
        ((SearchPresenter) this.mPresenter).getHotKeyList();
    }

    public /* synthetic */ void lambda$initListener$1$SearchActivity(View view) {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        onBackPressed();
    }

    public /* synthetic */ void lambda$initListener$2$SearchActivity(View view) {
        DBUtils.deleteAll(SearchKeywordEntity.class);
        this.headView.initHistoryTagList(new ArrayList());
    }

    public /* synthetic */ boolean lambda$initListener$4$SearchActivity(TextView textView, int i, KeyEvent keyEvent) {
        if (i == 3) {
            String trim = this.etSearch.getText().toString().trim();
            if (EmojiFilter.containsEmoji(trim)) {
                showToast(R$string.fq_no_emoji_search);
                return true;
            } else if (TextUtils.isEmpty(trim)) {
                showToast(R$string.fq_text_search_content);
                return false;
            } else {
                SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
                DBUtils.saveOrUpdateKeyword(trim);
                this.isHistoryWord = false;
                this.isRecommendWord = false;
                postSearch(trim);
            }
        }
        return true;
    }

    public /* synthetic */ void lambda$initListener$5$SearchActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LabelEntity item = this.searchKeywordAdapter.getItem(i);
        if (item == null || TextUtils.isEmpty(item.keyword)) {
            return;
        }
        this.isHistoryWord = false;
        this.isRecommendWord = false;
        postSearch(item.keyword);
    }

    public /* synthetic */ void lambda$initListener$6$SearchActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "2", getString(R$string.fq_live_enter_source_search));
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.headView = new SearchHistoryHeadView(this.mContext);
        this.mAdapter = new HomeLiveAdapter(R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite, true, false));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.headView);
        this.headView.initHistoryTagList(DBUtils.findAllWithOrder(SearchKeywordEntity.class, "insertTime desc"));
    }

    private void initKeywordAdapter() {
        this.mRecyclerViewKeyword.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerViewKeyword.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.searchKeywordAdapter = new SearchKeywordAdapter(R$layout.fq_item_list_search_keyword);
        this.mRecyclerViewKeyword.setAdapter(this.searchKeywordAdapter);
        this.searchKeywordAdapter.bindToRecyclerView(this.mRecyclerViewKeyword);
    }

    private void initFragmentList() {
        this.fragmentList = new ArrayList();
        this.fragmentList.add(SearchAllFragment.newInstance());
        this.fragmentList.add(SearchAnchorFragment.newInstance());
        this.fragmentList.add(SearchLiveFragment.newInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSearchText(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.etSearch.setText(str);
        this.etSearch.setSelection(str.length());
        DBUtils.saveOrUpdateKeyword(str);
        postSearch(str);
    }

    private void postSearch(String str) {
        if (this.fragmentList == null) {
            initFragmentList();
            initMagicIndicator(this.mContext, getSupportFragmentManager(), this.magicIndicator, this.mViewPager, this.fragmentList, Arrays.asList(this.mContext.getResources().getStringArray(R$array.fq_search_tab_menu)), 0);
        }
        int i = 4;
        this.flKeywordBg.setVisibility(4);
        this.mLLViewPagerBg.setVisibility(TextUtils.isEmpty(str) ? 4 : 0);
        RecyclerView recyclerView = this.mRecyclerView;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        }
        recyclerView.setVisibility(i);
        EventBus.getDefault().postSticky(new SearchEvent(str, this.isRecommendWord, this.isHistoryWord));
    }

    private void initMagicIndicator(Context context, FragmentManager fragmentManager, MagicIndicator magicIndicator, ViewPager viewPager, List<BaseFragment> list, List<String> list2, int i) {
        magicIndicator.setBackgroundColor(ContextCompat.getColor(context, R$color.fq_colorWhite));
        viewPager.setAdapter(new MenuTabAdapter(list, list2, fragmentManager));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new C39263(list, list2, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setCurrentItem(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.home.SearchActivity$3 */
    /* loaded from: classes3.dex */
    public class C39263 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$fragmentList;
        final /* synthetic */ List val$itemList;
        final /* synthetic */ ViewPager val$pager;

        C39263(List list, List list2, ViewPager viewPager) {
            this.val$fragmentList = list;
            this.val$itemList = list2;
            this.val$pager = viewPager;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List list = this.val$fragmentList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText((CharSequence) this.val$itemList.get(i));
            simplePagerTitleView.setTextSize(18.0f);
            simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_select_color));
            final ViewPager viewPager = this.val$pager;
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$SearchActivity$3$U31T4EzJCsGbOtD8kQg9H26dfKI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPager.this.setCurrentItem(i);
                }
            });
            return simplePagerTitleView;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(context, R$color.fq_tab_menu_text_select_color)));
            linePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(ConvertUtils.dp2px(18.0f));
            linePagerIndicator.setMode(2);
            return linePagerIndicator;
        }
    }

    @Override // com.tomatolive.library.p136ui.fragment.SearchAllFragment.OnFragmentInteractionListener
    public void onPagerSelectedListener(int i) {
        if (i < 0) {
            return;
        }
        this.mViewPager.setCurrentItem(i);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchView
    public void onLiveListSuccess(List<LiveEntity> list) {
        this.mAdapter.setNewData(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchView
    public void onHotKeyListSuccess(List<LabelEntity> list) {
        this.headView.initHotTagList(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchView
    public void onAutoKeyListSuccess(String str, List<LabelEntity> list) {
        this.flKeywordBg.setVisibility(TextUtils.isEmpty(str) ? 4 : 0);
        this.searchKeywordAdapter.setKeyWord(str);
        this.searchKeywordAdapter.setNewData(list);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof KeywordEvent) {
            this.headView.initHistoryTagList(DBUtils.findAllWithOrder(SearchKeywordEntity.class, "insertTime desc"));
        }
    }
}
