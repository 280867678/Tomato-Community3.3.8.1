package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.adapter.DynamicFragmentAdapter;
import com.one.tomato.dialog.PublishPostDialog;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.event.PostVideoPlayEvent;
import com.one.tomato.entity.p079db.DefaultChannelBean;
import com.one.tomato.entity.p079db.PostHotSearch;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.circle.utils.ChannelManger;
import com.one.tomato.mvp.p080ui.circle.view.NewChannelChooseActivity;
import com.one.tomato.mvp.p080ui.post.view.PicSearchActivity;
import com.one.tomato.mvp.p080ui.search.view.PostSearchActivity;
import com.one.tomato.p085ui.messge.p086ui.MyMessageNotifctionActivity;
import com.one.tomato.p085ui.publish.ChoosePublishWayActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.PublishUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import com.tomatolive.library.utils.LogConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* compiled from: NewPostTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabFragment */
/* loaded from: classes3.dex */
public final class NewPostTabFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private DynamicFragmentAdapter fragmentAdapter;
    private PublishPostDialog publishPostDialog;
    private String searchKey;
    private final List<String> tabString = new ArrayList();
    private final List<Integer> channelIdList = new ArrayList();
    private final Map<Integer, Fragment> fragmentMap = new LinkedHashMap();
    private List<Fragment> tabFragment = new ArrayList();
    private int curFragmentIndex = 1;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.new_post_tab_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public NewPostTabFragment() {
        CollectionsKt__CollectionsKt.mutableListOf("recommend", "game", "focus", "img", "read", "video");
        CollectionsKt__CollectionsKt.mutableListOf(0, 0, 0, 1, 3, 2);
    }

    /* compiled from: NewPostTabFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewPostTabFragment getInstance(int i) {
            NewPostTabFragment newPostTabFragment = new NewPostTabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("goInType", i);
            newPostTabFragment.setArguments(bundle);
            return newPostTabFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        Bundle arguments = getArguments();
        if ((arguments != null ? arguments.getInt("goInType") : 0) == 1) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_back);
            if (imageView2 != null) {
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initView$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        FragmentActivity activity = NewPostTabFragment.this.getActivity();
                        if (activity != null) {
                            activity.onBackPressed();
                        }
                    }
                });
            }
            NoHorScrollViewPager noHorScrollViewPager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
            if (noHorScrollViewPager == null) {
                return;
            }
            noHorScrollViewPager.setPadding(0, 0, 0, 0);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initListener() {
        super.initListener();
        ((ImageView) _$_findCachedViewById(R$id.iv_publish)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                FragmentActivity activity = NewPostTabFragment.this.getActivity();
                if (activity != null) {
                    PublishUtil.getInstance().setContext(activity);
                    if (!UserPermissionUtil.getInstance().isPermissionEnable(1) || PublishUtil.getInstance().startPublishActivity(null)) {
                        return;
                    }
                    mContext = NewPostTabFragment.this.getMContext();
                    NewPostTabFragment.this.startActivity(new Intent(mContext, ChoosePublishWayActivity.class));
                }
            }
        });
        PublishUtil.getInstance().setPublishListener(new PublishUtil.PublishListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$2
            @Override // com.one.tomato.utils.post.PublishUtil.PublishListener
            public void publishDefault() {
                ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPostTabFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_home_publish);
            }

            @Override // com.one.tomato.utils.post.PublishUtil.PublishListener
            public void publishIng(boolean z) {
                ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPostTabFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_update);
                if (!z) {
                }
            }

            @Override // com.one.tomato.utils.post.PublishUtil.PublishListener
            public void publishSuccess() {
                ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPostTabFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_update_com);
            }

            @Override // com.one.tomato.utils.post.PublishUtil.PublishListener
            public void publishFail(Context context, PublishInfo publishInfo) {
                Intrinsics.checkParameterIsNotNull(context, "context");
                Intrinsics.checkParameterIsNotNull(publishInfo, "publishInfo");
                ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPostTabFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_update_error);
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_search);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    String str;
                    PostSearchActivity.Companion companion = PostSearchActivity.Companion;
                    mContext = NewPostTabFragment.this.getMContext();
                    if (mContext == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    str = NewPostTabFragment.this.searchKey;
                    companion.startActivity(mContext, str);
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.post_messgae);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = NewPostTabFragment.this.getMContext();
                    MyMessageNotifctionActivity.startActivity(mContext);
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_channel);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = NewPostTabFragment.this.getMContext();
                    if (mContext != null) {
                        NewChannelChooseActivity.Companion companion = NewChannelChooseActivity.Companion;
                        FragmentActivity activity = NewPostTabFragment.this.getActivity();
                        if (activity == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        Intrinsics.checkExpressionValueIsNotNull(activity, "activity!!");
                        companion.startAct(activity);
                    }
                }
            });
        }
        ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$6
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                NewPostTabFragment.this.curFragmentIndex = i;
            }
        });
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_javdb);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewPostTabFragment$initListener$7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    PicSearchActivity.Companion companion = PicSearchActivity.Companion;
                    mContext = NewPostTabFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startAct(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        initTab();
        initAdapter();
        ArrayList<PostHotSearch> list = DBUtil.getHotSearchList();
        Intrinsics.checkExpressionValueIsNotNull(list, "list");
        if (!list.isEmpty()) {
            PostHotSearch postHotSearch = list.get(Random.Default.nextInt(list.size()));
            Intrinsics.checkExpressionValueIsNotNull(postHotSearch, "list[Random.Default.nextInt(list.size)]");
            this.searchKey = postHotSearch.getHotWord();
            ((TextView) _$_findCachedViewById(R$id.tv_search)).setText(this.searchKey);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        setMessageShow();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        setMessageShow();
        if (z) {
            RxBus.getDefault().post(new PostVideoPlayEvent(false));
        } else if (z) {
        } else {
            RxBus.getDefault().post(new PostVideoPlayEvent(true));
        }
    }

    private final void setMessageShow() {
        if (BaseApplication.isMyMessageHave() != 0) {
            TextView tv_my_message_count = (TextView) _$_findCachedViewById(R$id.tv_my_message_count);
            Intrinsics.checkExpressionValueIsNotNull(tv_my_message_count, "tv_my_message_count");
            tv_my_message_count.setVisibility(0);
            TextView tv_my_message_count2 = (TextView) _$_findCachedViewById(R$id.tv_my_message_count);
            Intrinsics.checkExpressionValueIsNotNull(tv_my_message_count2, "tv_my_message_count");
            tv_my_message_count2.setText(String.valueOf(BaseApplication.isMyMessageHave()));
            if (BaseApplication.isMyMessageHave() <= 99) {
                return;
            }
            TextView tv_my_message_count3 = (TextView) _$_findCachedViewById(R$id.tv_my_message_count);
            Intrinsics.checkExpressionValueIsNotNull(tv_my_message_count3, "tv_my_message_count");
            tv_my_message_count3.setText("99+");
            return;
        }
        TextView tv_my_message_count4 = (TextView) _$_findCachedViewById(R$id.tv_my_message_count);
        Intrinsics.checkExpressionValueIsNotNull(tv_my_message_count4, "tv_my_message_count");
        tv_my_message_count4.setVisibility(8);
    }

    public final void initTab() {
        Fragment companion;
        this.tabString.clear();
        this.channelIdList.clear();
        this.tabFragment.clear();
        LogUtil.m3787d("yanC", "初始化频道");
        ArrayList<DefaultChannelBean> userChannel = ChannelManger.INSTANCE.getUserChannel(UserChannelBean.TYPE_DEFAULT);
        if (!(userChannel == null || userChannel.isEmpty())) {
            LogUtil.m3787d("yanC", "初始化频道，获取到接口的频道");
            ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
            for (DefaultChannelBean defaultChannelBean : userChannel) {
                if (!arrayList.contains(defaultChannelBean)) {
                    arrayList.add(defaultChannelBean);
                }
            }
            ChannelManger.INSTANCE.saveUserChannel(arrayList, UserChannelBean.TYPE_DEFAULT);
            LogUtil.m3787d("yanC", "初始化频道，接口频道打印数据 " + userChannel.toString());
            for (DefaultChannelBean defaultChannelBean2 : arrayList) {
                if (defaultChannelBean2 instanceof DefaultChannelBean) {
                    LogUtil.m3787d("yanC", "初始化频道ID= " + defaultChannelBean2.getChannelId());
                    int channelId = defaultChannelBean2.getChannelId();
                    if (1 > channelId || 7 < channelId) {
                        companion = NewPostTabListFragment.Companion.getInstance(-1, "", defaultChannelBean2.getChannelId());
                    } else {
                        companion = initFragmentForomId(defaultChannelBean2.getChannelId());
                    }
                    if (companion != null) {
                        String string = PreferencesUtil.getInstance().getString("language_country");
                        if (string != null) {
                            int hashCode = string.hashCode();
                            if (hashCode != 2155) {
                                if (hashCode != 2691) {
                                    if (hashCode == 2718 && string.equals("US")) {
                                        List<String> list = this.tabString;
                                        String englishName = defaultChannelBean2.getEnglishName();
                                        Intrinsics.checkExpressionValueIsNotNull(englishName, "it.englishName");
                                        list.add(englishName);
                                    }
                                } else if (string.equals("TW")) {
                                    List<String> list2 = this.tabString;
                                    String traditionalName = defaultChannelBean2.getTraditionalName();
                                    Intrinsics.checkExpressionValueIsNotNull(traditionalName, "it.traditionalName");
                                    list2.add(traditionalName);
                                }
                            } else if (string.equals("CN")) {
                                List<String> list3 = this.tabString;
                                String name = defaultChannelBean2.getName();
                                Intrinsics.checkExpressionValueIsNotNull(name, "it.name");
                                list3.add(name);
                            }
                        }
                        this.tabFragment.add(companion);
                        this.channelIdList.add(Integer.valueOf(defaultChannelBean2.getChannelId()));
                    }
                }
            }
            return;
        }
        LogUtil.m3787d("yanC", "初始化频道，未获取到频道，初始化默认的7个频道");
        if (userChannel == null) {
            userChannel = new ArrayList<>();
        }
        for (int i = 1; i <= 7; i++) {
            if (i == 1) {
                userChannel.add(new DefaultChannelBean(i, "关注", LogConstants.FOLLOW_EVENT_NAME, "關注"));
            } else if (i == 2) {
                userChannel.add(new DefaultChannelBean(i, "推荐", "Recommend", "推薦"));
            } else if (i == 4) {
                userChannel.add(new DefaultChannelBean(i, "最新", "New", "最新"));
            } else if (i == 5) {
                userChannel.add(new DefaultChannelBean(i, "視頻", "Video", "視頻"));
            } else if (i == 6) {
                userChannel.add(new DefaultChannelBean(i, "圖片", "Image", "圖片"));
            } else if (i == 7) {
                userChannel.add(new DefaultChannelBean(i, "短文", "Read", "短文"));
            }
        }
        ChannelManger.INSTANCE.saveUserChannel(userChannel, UserChannelBean.TYPE_DEFAULT);
        ChannelManger.INSTANCE.saveSystemChannel(userChannel, UserChannelBean.TYPE_DEFAULT);
        initTab();
    }

    public final Fragment initFragmentForomId(int i) {
        Fragment postFocusFragment;
        if (this.fragmentMap.containsKey(Integer.valueOf(i))) {
            return this.fragmentMap.get(Integer.valueOf(i));
        }
        if (i == 1) {
            postFocusFragment = new PostFocusFragment();
        } else if (i == 2) {
            postFocusFragment = NewPostTabListFragment.Companion.getInstance(0, "recommend", 0);
        } else if (i == 4) {
            postFocusFragment = PostNewFragment.Companion.getInstance(-1, "new_post", 4);
        } else if (i == 5) {
            postFocusFragment = NewPostTabListFragment.Companion.getInstance(2, "video", 0);
        } else if (i == 6) {
            postFocusFragment = NewPostTabListFragment.Companion.getInstance(1, "img", 0);
        } else {
            postFocusFragment = i != 7 ? null : NewPostTabListFragment.Companion.getInstance(3, "read", 0);
        }
        if (postFocusFragment != null) {
            this.fragmentMap.put(Integer.valueOf(i), postFocusFragment);
        }
        return postFocusFragment;
    }

    private final void initAdapter() {
        this.fragmentAdapter = new DynamicFragmentAdapter(getChildFragmentManager(), this.tabFragment, this.tabString);
        NoHorScrollViewPager viewpager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setAdapter(this.fragmentAdapter);
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false);
        NoHorScrollViewPager viewpager2 = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager2, "viewpager");
        viewpager2.setOffscreenPageLimit(this.tabFragment.size());
        NoHorScrollViewPager viewpager3 = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager3, "viewpager");
        viewpager3.setCurrentItem(this.curFragmentIndex);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        PublishPostDialog publishPostDialog;
        super.onActivityResult(i, i2, intent);
        if (i != 20) {
            if (i != 188) {
                return;
            }
            ArrayList<LocalMedia> arrayList = new ArrayList<>();
            arrayList.addAll(PictureSelector.obtainMultipleResult(intent));
            if (arrayList.size() <= 0 || (publishPostDialog = this.publishPostDialog) == null) {
                return;
            }
            publishPostDialog.setSelectData(arrayList);
            return;
        }
        initTab();
        DynamicFragmentAdapter dynamicFragmentAdapter = this.fragmentAdapter;
        if (dynamicFragmentAdapter != null) {
            dynamicFragmentAdapter.updateData(this.tabFragment, this.tabString);
        }
        NoHorScrollViewPager viewpager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setOffscreenPageLimit(this.tabFragment.size());
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        if (intent != null) {
            if (intent.hasExtra("clickItem")) {
                Object obj = intent.getExtras().get("clickItem");
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.DefaultChannelBean");
                }
                this.curFragmentIndex = this.channelIdList.indexOf(Integer.valueOf(((DefaultChannelBean) obj).getChannelId()));
            }
            if (this.curFragmentIndex > this.tabFragment.size() - 1) {
                this.curFragmentIndex = 1;
            }
            ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setCurrentItem(this.curFragmentIndex, false);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }
}
