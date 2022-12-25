package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.dialog.HomePageActionDialog;
import com.one.tomato.dialog.ShowUpPopWindow;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.UpSubscriberVo;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView;
import com.one.tomato.mvp.p080ui.mine.presenter.NewHomePagePresenter;
import com.one.tomato.mvp.p080ui.p082up.view.UpSubscribeActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaGridFragment;
import com.one.tomato.mvp.p080ui.post.view.CollectFragment;
import com.one.tomato.mvp.p080ui.post.view.MyHomePagePostFragment;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.p085ui.mine.MyFanListActivity;
import com.one.tomato.p085ui.mine.MyFocusMemberActivity;
import com.one.tomato.p085ui.mine.PersonInfoActivity;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import com.tomatolive.library.utils.ConstantUtils;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewHomePageFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment */
/* loaded from: classes3.dex */
public final class NewHomePageFragment extends MvpBaseFragment<IHomePageContact$IHomePageView, NewHomePagePresenter> implements IHomePageContact$IHomePageView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String bgUrl;
    private TabFragmentAdapter fragmentAdapter;
    private HomePageActionDialog homePageActionDialog;
    private boolean isLivIng;
    private boolean isMySelf;
    private CollectFragment myCollectFragment;
    private PostSerializeManageFragment mySerializeFragment;
    private NewPaPaGridFragment papaGridListFragment;
    private int personMemberId;
    private MyHomePagePostFragment postFragment;
    private MyHomePagePostFragment postSubscribeFragment;
    private TTUtil ttUtil;
    private int type;
    private UserInfo userInfo;
    private int userInfoVersion;
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<? extends LocalMedia> selectList = new ArrayList();
    private String liveId = "";

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateInfo(String str) {
    }

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
        return R.layout.activity_my_homepage;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    public final void setNewPersonId(int i) {
        this.personMemberId = i;
    }

    /* compiled from: NewHomePageFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewHomePageFragment getInstance(int i) {
            NewHomePageFragment newHomePageFragment = new NewHomePageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            newHomePageFragment.setArguments(bundle);
            return newHomePageFragment;
        }
    }

    public final void refreshNewPresonId(int i) {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.back);
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (this.personMemberId == i) {
            return;
        }
        clearState();
        this.personMemberId = i;
        refreshInfo();
        AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true);
        }
        MyHomePagePostFragment myHomePagePostFragment = this.postFragment;
        if (myHomePagePostFragment != null) {
            myHomePagePostFragment.onRefresh(i);
        }
        NewPaPaGridFragment newPaPaGridFragment = this.papaGridListFragment;
        if (newPaPaGridFragment == null) {
            return;
        }
        newPaPaGridFragment.setPersonMemberId(i);
    }

    public final void onPusePlay() {
        MyHomePagePostFragment myHomePagePostFragment = this.postFragment;
        if (myHomePagePostFragment != null) {
            myHomePagePostFragment.videoStop();
        }
    }

    private final void clearState() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_name);
        if (textView != null) {
            textView.setText("");
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_signature);
        if (textView2 != null) {
            textView2.setText("");
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_fan_num);
        if (textView3 != null) {
            textView3.setText('0' + AppUtil.getString(R.string.my_fans));
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_focus_num);
        if (textView4 != null) {
            textView4.setText('0' + AppUtil.getString(R.string.my_focus));
        }
        TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_top_focus);
        if (textView5 != null) {
            textView5.setText(R.string.common_focus_n_add);
        }
        TextView textView6 = (TextView) _$_findCachedViewById(R$id.tv_top_focus);
        if (textView6 != null) {
            textView6.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
        }
        if (getMContext() != null) {
            RoundedImageView roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.iv_head_big);
            if (roundedImageView != null) {
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                roundedImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_img_head));
            }
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_user_level);
            if (imageView != null) {
                Context mContext2 = getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext2, R.drawable.icon_user_level_5));
            }
        }
        TextView textView7 = (TextView) _$_findCachedViewById(R$id.tv_user_level);
        if (textView7 != null) {
            textView7.setText("Lv.1");
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        this.isMySelf = this.personMemberId == DBUtil.getMemberId();
        if (this.isMySelf) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            this.userInfoVersion = userInfo.getLocalVersion();
            ImageView iv_home_page = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
            Intrinsics.checkExpressionValueIsNotNull(iv_home_page, "iv_home_page");
            iv_home_page.setEnabled(true);
            this.userInfo = DBUtil.getUserInfo();
            initPersonInfo();
            return;
        }
        ImageView iv_home_page2 = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
        Intrinsics.checkExpressionValueIsNotNull(iv_home_page2, "iv_home_page");
        iv_home_page2.setEnabled(false);
        if (this.type != 0) {
            return;
        }
        getPersonMemberInfo();
    }

    private final void refreshInfo() {
        this.isMySelf = this.personMemberId == DBUtil.getMemberId();
        if (this.isMySelf) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            this.userInfoVersion = userInfo.getLocalVersion();
            ImageView iv_home_page = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
            Intrinsics.checkExpressionValueIsNotNull(iv_home_page, "iv_home_page");
            iv_home_page.setEnabled(true);
            this.userInfo = DBUtil.getUserInfo();
            initPersonInfo();
            return;
        }
        ImageView iv_home_page2 = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
        Intrinsics.checkExpressionValueIsNotNull(iv_home_page2, "iv_home_page");
        iv_home_page2.setEnabled(false);
        getPersonMemberInfo();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewHomePagePresenter mo6441createPresenter() {
        return new NewHomePagePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        Intent intent;
        Bundle extras;
        FragmentActivity activity = getActivity();
        int i = 0;
        this.personMemberId = (activity == null || (intent = activity.getIntent()) == null || (extras = intent.getExtras()) == null) ? 0 : extras.getInt(NewMyHomePageActivity.Companion.getMEMBERID());
        Bundle arguments = getArguments();
        if (arguments != null) {
            i = arguments.getInt("type");
        }
        this.type = i;
        initTabs();
        setClick();
        setListener();
        new SelectPicTypeUtil(getActivity());
    }

    @Override // com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView
    public void handlerPersonInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        if (userInfo == null) {
            this.userInfo = new UserInfo();
        }
        initPersonInfo();
    }

    private final void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), this.fragmentList, this.stringList);
            ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.home_page_active));
        this.stringList.add(AppUtil.getString(R.string.home_page_papa));
        this.stringList.add(AppUtil.getString(R.string.my_collect));
        this.stringList.add(AppUtil.getString(R.string.post_serialize_item_tip));
        UserInfo userInfo = this.userInfo;
        if (userInfo != null && userInfo.getSubscribeSwitch() == 1) {
            this.stringList.add(AppUtil.getString(R.string.post_home_page_subscribe_tab));
        }
        int size = this.stringList.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                if (this.type == 0) {
                    this.postFragment = MyHomePagePostFragment.Companion.getInstance(-1, "homepage", true);
                } else {
                    this.postFragment = MyHomePagePostFragment.Companion.getInstance(-1, "homepage", false);
                }
                MyHomePagePostFragment myHomePagePostFragment = this.postFragment;
                if (myHomePagePostFragment != null) {
                    myHomePagePostFragment.setPersonMemberId(this.personMemberId);
                }
                ArrayList<Fragment> arrayList = this.fragmentList;
                MyHomePagePostFragment myHomePagePostFragment2 = this.postFragment;
                if (myHomePagePostFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList.add(myHomePagePostFragment2);
            } else if (i == 1) {
                this.papaGridListFragment = NewPaPaGridFragment.Companion.initInstance(2, this.personMemberId);
                ArrayList<Fragment> arrayList2 = this.fragmentList;
                NewPaPaGridFragment newPaPaGridFragment = this.papaGridListFragment;
                if (newPaPaGridFragment == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList2.add(newPaPaGridFragment);
            } else if (i == 2) {
                this.myCollectFragment = new CollectFragment().getInstance(-1, "collect", this.personMemberId);
                ArrayList<Fragment> arrayList3 = this.fragmentList;
                CollectFragment collectFragment = this.myCollectFragment;
                if (collectFragment == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList3.add(collectFragment);
            } else if (i == 3) {
                this.mySerializeFragment = PostSerializeManageFragment.Companion.getInstance(this.personMemberId);
                PostSerializeManageFragment postSerializeManageFragment = this.mySerializeFragment;
                if (postSerializeManageFragment != null) {
                    postSerializeManageFragment.deleteOrCancel(2);
                }
                ArrayList<Fragment> arrayList4 = this.fragmentList;
                PostSerializeManageFragment postSerializeManageFragment2 = this.mySerializeFragment;
                if (postSerializeManageFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList4.add(postSerializeManageFragment2);
            } else if (i == 4) {
                this.postSubscribeFragment = MyHomePagePostFragment.Companion.getInstance(-1, "homepage_subscribe", true);
                MyHomePagePostFragment myHomePagePostFragment3 = this.postSubscribeFragment;
                if (myHomePagePostFragment3 != null) {
                    myHomePagePostFragment3.setPersonMemberId(this.personMemberId);
                }
                ArrayList<Fragment> arrayList5 = this.fragmentList;
                MyHomePagePostFragment myHomePagePostFragment4 = this.postSubscribeFragment;
                if (myHomePagePostFragment4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList5.add(myHomePagePostFragment4);
            } else {
                continue;
            }
        }
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false, true);
        NoHorScrollViewPager viewpager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setOffscreenPageLimit(this.fragmentList.size());
    }

    private final void setListener() {
        ((AppBarLayout) _$_findCachedViewById(R$id.appbar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setListener$1
            @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                boolean z;
                boolean z2;
                Intrinsics.checkExpressionValueIsNotNull(appBarLayout, "appBarLayout");
                if (i == (-appBarLayout.getTotalScrollRange())) {
                    ((ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.back)).setImageResource(R.drawable.icon_back_black);
                    View _$_findCachedViewById = NewHomePageFragment.this._$_findCachedViewById(R$id.view_line);
                    if (_$_findCachedViewById != null) {
                        _$_findCachedViewById.setVisibility(0);
                    }
                    ImageView iv_head_small = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_head_small);
                    Intrinsics.checkExpressionValueIsNotNull(iv_head_small, "iv_head_small");
                    iv_head_small.setVisibility(0);
                    TextView tv_title = (TextView) NewHomePageFragment.this._$_findCachedViewById(R$id.tv_title);
                    Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                    tv_title.setVisibility(0);
                    z2 = NewHomePageFragment.this.isMySelf;
                    if (z2) {
                        ImageView iv_menu = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu);
                        Intrinsics.checkExpressionValueIsNotNull(iv_menu, "iv_menu");
                        iv_menu.setVisibility(8);
                        return;
                    }
                    ImageView iv_menu2 = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu);
                    Intrinsics.checkExpressionValueIsNotNull(iv_menu2, "iv_menu");
                    iv_menu2.setVisibility(0);
                    ((ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu)).setImageResource(R.drawable.icon_menu_black);
                    TextView tv_top_focus = (TextView) NewHomePageFragment.this._$_findCachedViewById(R$id.tv_top_focus);
                    Intrinsics.checkExpressionValueIsNotNull(tv_top_focus, "tv_top_focus");
                    tv_top_focus.setVisibility(0);
                    return;
                }
                ((ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.back)).setImageResource(R.drawable.icon_back_white);
                NewHomePageFragment.this._$_findCachedViewById(R$id.view_line).setVisibility(8);
                ImageView iv_head_small2 = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_head_small);
                Intrinsics.checkExpressionValueIsNotNull(iv_head_small2, "iv_head_small");
                iv_head_small2.setVisibility(8);
                TextView tv_title2 = (TextView) NewHomePageFragment.this._$_findCachedViewById(R$id.tv_title);
                Intrinsics.checkExpressionValueIsNotNull(tv_title2, "tv_title");
                tv_title2.setVisibility(8);
                z = NewHomePageFragment.this.isMySelf;
                if (z) {
                    ImageView iv_menu3 = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu);
                    Intrinsics.checkExpressionValueIsNotNull(iv_menu3, "iv_menu");
                    iv_menu3.setVisibility(8);
                    return;
                }
                ImageView iv_menu4 = (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu);
                Intrinsics.checkExpressionValueIsNotNull(iv_menu4, "iv_menu");
                iv_menu4.setVisibility(0);
                ((ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_menu)).setImageResource(R.drawable.icon_menu_white);
                TextView tv_top_focus2 = (TextView) NewHomePageFragment.this._$_findCachedViewById(R$id.tv_top_focus);
                Intrinsics.checkExpressionValueIsNotNull(tv_top_focus2, "tv_top_focus");
                tv_top_focus2.setVisibility(8);
            }
        });
    }

    public final void setClick() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FragmentActivity activity = NewHomePageFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_top_focus);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewHomePageFragment.this.focus();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_bottom_focus);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewHomePageFragment.this.focus();
                }
            });
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.iv_menu);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewHomePageFragment.this.showHomePageActionDialog();
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_focus_num);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    boolean z;
                    Context mContext;
                    z = NewHomePageFragment.this.isMySelf;
                    if (z) {
                        mContext = NewHomePageFragment.this.getMContext();
                        MyFocusMemberActivity.startActivity(mContext);
                    }
                }
            });
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_fan_num);
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    boolean z;
                    Context mContext;
                    z = NewHomePageFragment.this.isMySelf;
                    if (z) {
                        mContext = NewHomePageFragment.this.getMContext();
                        MyFanListActivity.startActivity(mContext);
                    }
                }
            });
        }
        TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_edit);
        if (textView5 != null) {
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    Context mContext2;
                    mContext = NewHomePageFragment.this.getMContext();
                    if (mContext == null || NewHomePageFragment.this.startLoginActivity()) {
                        return;
                    }
                    mContext2 = NewHomePageFragment.this.getMContext();
                    PersonInfoActivity.startActivity(mContext2, "mine");
                }
            });
        }
        RoundedImageView roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.iv_head_big);
        if (roundedImageView != null) {
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserInfo userInfo;
                    boolean z;
                    UserInfo userInfo2;
                    String str;
                    Context mContext;
                    Context mContext2;
                    String str2;
                    userInfo = NewHomePageFragment.this.userInfo;
                    if (userInfo == null) {
                        return;
                    }
                    z = NewHomePageFragment.this.isLivIng;
                    if (z) {
                        TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                        mContext2 = NewHomePageFragment.this.getMContext();
                        str2 = NewHomePageFragment.this.liveId;
                        singleton.startLiveActivity(mContext2, str2);
                        return;
                    }
                    ArrayList<ImageBean> arrayList = new ArrayList<>();
                    ImageBean imageBean = new ImageBean();
                    userInfo2 = NewHomePageFragment.this.userInfo;
                    if (userInfo2 == null || (str = userInfo2.getAvatar()) == null) {
                        str = "";
                    }
                    imageBean.setImage(str);
                    imageBean.setHeader(true);
                    arrayList.add(imageBean);
                    mContext = NewHomePageFragment.this.getMContext();
                    if (mContext == null) {
                        return;
                    }
                    ImageShowActivity.Companion.startActivity(mContext, arrayList, 0);
                }
            });
        }
        ((LinearLayout) _$_findCachedViewById(R$id.ll_live_ing)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                String str;
                TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                mContext = NewHomePageFragment.this.getMContext();
                str = NewHomePageFragment.this.liveId;
                singleton.startLiveActivity(mContext, str);
            }
        });
        TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
        if (textView6 != null) {
            textView6.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    UserInfo userInfo;
                    UpSubscribeActivity.Companion companion = UpSubscribeActivity.Companion;
                    mContext = NewHomePageFragment.this.getMContext();
                    userInfo = NewHomePageFragment.this.userInfo;
                    companion.startAct(mContext, userInfo != null ? userInfo.getMemberId() : 0);
                }
            });
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_up);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$11
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    newHomePageFragment.showUpPop(1, it2);
                }
            });
        }
        ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_up2);
        if (imageView4 != null) {
            imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$12
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    newHomePageFragment.showUpPop(2, it2);
                }
            });
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_up3);
        if (imageView5 != null) {
            imageView5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$13
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    newHomePageFragment.showUpPop(3, it2);
                }
            });
        }
        ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_up_original);
        if (imageView6 != null) {
            imageView6.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$14
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    newHomePageFragment.showUpPop(4, it2);
                }
            });
        }
        ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_up_gold);
        if (imageView7 != null) {
            imageView7.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$setClick$15
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    newHomePageFragment.showUpPop(5, it2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showUpPop(int i, View view) {
        ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(getMContext());
        int i2 = 10;
        if (i == 1) {
            UserInfo userInfo = this.userInfo;
            if (userInfo == null || userInfo.getUpLevel() != 1) {
                UserInfo userInfo2 = this.userInfo;
                if (userInfo2 == null || userInfo2.getUpLevel() != 2) {
                    UserInfo userInfo3 = this.userInfo;
                    if (userInfo3 == null || userInfo3.getUpLevel() != 3) {
                        UserInfo userInfo4 = this.userInfo;
                        if (userInfo4 == null || userInfo4.getUpLevel() != 4) {
                            UserInfo userInfo5 = this.userInfo;
                            if (userInfo5 != null && userInfo5.getUpLevel() == 5) {
                                i2 = 13;
                            }
                            i2 = 0;
                        } else {
                            i2 = 12;
                        }
                    }
                } else {
                    i2 = 9;
                }
            } else {
                i2 = 8;
            }
        } else if (i == 2) {
            i2 = 11;
        } else if (i != 3) {
            if (i == 4) {
                i2 = 7;
            } else {
                if (i == 5) {
                    i2 = 6;
                }
                i2 = 0;
            }
        }
        showUpPopWindow.showDown(view, i2);
    }

    public final void focus() {
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            return;
        }
        if (userInfo != null) {
            int i = 1;
            if (userInfo.getFollowFlag() == 1) {
                i = 0;
            }
            NewHomePagePresenter mPresenter = getMPresenter();
            if (mPresenter == null) {
                return;
            }
            mPresenter.foucs(this.personMemberId, i);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView
    public void handlerFoucs() {
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            return;
        }
        if (userInfo != null) {
            if (userInfo.getFollowFlag() == 1) {
                UserInfo userInfo2 = this.userInfo;
                if (userInfo2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    userInfo2.setFollowFlag(0);
                    UserInfoManager.setUserFollowCount(false);
                }
            } else {
                UserInfo userInfo3 = this.userInfo;
                if (userInfo3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    userInfo3.setFollowFlag(1);
                    UserInfoManager.setUserFollowCount(true);
                }
            }
            UserInfo userInfo4 = this.userInfo;
            if (userInfo4 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            if (userInfo4.getFollowFlag() == 1) {
                ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_y);
                ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_y);
                ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                ToastUtil.showCenterToast((int) R.string.common_focus_success);
            } else {
                ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_n_add);
                ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_n_add);
                ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                ToastUtil.showCenterToast((int) R.string.common_cancel_focus_success);
            }
            Fragment fragment = this.fragmentList.get(0);
            if (fragment == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.post.view.MyHomePagePostFragment");
            }
            MyHomePagePostFragment myHomePagePostFragment = (MyHomePagePostFragment) fragment;
            int i = this.personMemberId;
            UserInfo userInfo5 = this.userInfo;
            if (userInfo5 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            myHomePagePostFragment.setAttentionMemberId(i, userInfo5.getFollowFlag());
            UserInfo userInfo6 = this.userInfo;
            if (userInfo6 != null) {
                setMemberFocusEvent(userInfo6.getFollowFlag(), this.personMemberId);
                return;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void setMemberFocusEvent(int i, int i2) {
        MemberFocusEvent memberFocusEvent = new MemberFocusEvent();
        memberFocusEvent.followFlag = i;
        memberFocusEvent.f1748id = i2;
        EventBus.getDefault().post(memberFocusEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x059c, code lost:
        if (r1 != null) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x05b7, code lost:
        if (r1 != null) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x05d2, code lost:
        if (r1 != null) goto L145;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void initPersonInfo() {
        int i;
        String country;
        ImageView imageView;
        ImageView imageView2;
        ArrayList<UpSubscriberVo> listTop3Subscriber;
        RelativeLayout relativeLayout;
        if (this.userInfo == null) {
            return;
        }
        initTabs();
        String str = null;
        if (this.isMySelf) {
            i = R.drawable.my_home_page_default;
            ImageView iv_home_page = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
            Intrinsics.checkExpressionValueIsNotNull(iv_home_page, "iv_home_page");
            iv_home_page.setEnabled(true);
            ImageView iv_menu = (ImageView) _$_findCachedViewById(R$id.iv_menu);
            Intrinsics.checkExpressionValueIsNotNull(iv_menu, "iv_menu");
            iv_menu.setVisibility(8);
            TextView tv_edit = (TextView) _$_findCachedViewById(R$id.tv_edit);
            Intrinsics.checkExpressionValueIsNotNull(tv_edit, "tv_edit");
            tv_edit.setVisibility(0);
            TextView tv_top_focus = (TextView) _$_findCachedViewById(R$id.tv_top_focus);
            Intrinsics.checkExpressionValueIsNotNull(tv_top_focus, "tv_top_focus");
            tv_top_focus.setVisibility(8);
            TextView tv_bottom_focus = (TextView) _$_findCachedViewById(R$id.tv_bottom_focus);
            Intrinsics.checkExpressionValueIsNotNull(tv_bottom_focus, "tv_bottom_focus");
            tv_bottom_focus.setVisibility(8);
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_subscribe);
            if (textView != null) {
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else {
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF9B2D));
                    Unit unit = Unit.INSTANCE;
                }
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.up_person_home_mange));
                Unit unit2 = Unit.INSTANCE;
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
            if (textView3 != null) {
                Context mContext2 = getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView3.setBackground(ContextCompat.getDrawable(mContext2, R.drawable.up_person_home_mange_subscribe_bg));
            }
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_subscribed);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
        } else {
            i = R.drawable.my_home_page_member_default;
            ImageView iv_home_page2 = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
            Intrinsics.checkExpressionValueIsNotNull(iv_home_page2, "iv_home_page");
            iv_home_page2.setEnabled(false);
            ImageView iv_menu2 = (ImageView) _$_findCachedViewById(R$id.iv_menu);
            Intrinsics.checkExpressionValueIsNotNull(iv_menu2, "iv_menu");
            iv_menu2.setVisibility(0);
            TextView tv_edit2 = (TextView) _$_findCachedViewById(R$id.tv_edit);
            Intrinsics.checkExpressionValueIsNotNull(tv_edit2, "tv_edit");
            tv_edit2.setVisibility(8);
            TextView tv_top_focus2 = (TextView) _$_findCachedViewById(R$id.tv_top_focus);
            Intrinsics.checkExpressionValueIsNotNull(tv_top_focus2, "tv_top_focus");
            tv_top_focus2.setVisibility(0);
            TextView tv_bottom_focus2 = (TextView) _$_findCachedViewById(R$id.tv_bottom_focus);
            Intrinsics.checkExpressionValueIsNotNull(tv_bottom_focus2, "tv_bottom_focus");
            tv_bottom_focus2.setVisibility(0);
            UserInfo userInfo = this.userInfo;
            if (userInfo == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (userInfo.getSubscribeFlag() == 1) {
                TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                if (textView4 != null) {
                    Context mContext3 = getMContext();
                    if (mContext3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else {
                        textView4.setTextColor(ContextCompat.getColor(mContext3, R.color.text_dark));
                        Unit unit3 = Unit.INSTANCE;
                    }
                }
                TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                if (textView5 != null) {
                    textView5.setText(AppUtil.getString(R.string.up_person_home_subscribed));
                    Unit unit4 = Unit.INSTANCE;
                }
                TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                if (textView6 != null) {
                    Context mContext4 = getMContext();
                    if (mContext4 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    textView6.setBackground(ContextCompat.getDrawable(mContext4, R.drawable.common_shape_solid_corner4_f5f5f7));
                }
                RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_subscribed);
                if (relativeLayout3 != null) {
                    relativeLayout3.setVisibility(0);
                }
            } else {
                UserInfo userInfo2 = this.userInfo;
                if (userInfo2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (userInfo2.getSubscribeFlag() == 0) {
                    TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                    if (textView7 != null) {
                        Context mContext5 = getMContext();
                        if (mContext5 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else {
                            textView7.setTextColor(ContextCompat.getColor(mContext5, R.color.color_FF9B2D));
                            Unit unit5 = Unit.INSTANCE;
                        }
                    }
                    TextView textView8 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                    if (textView8 != null) {
                        textView8.setText(AppUtil.getString(R.string.up_home_page_no_subscribe));
                        Unit unit6 = Unit.INSTANCE;
                    }
                    TextView textView9 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                    if (textView9 != null) {
                        Context mContext6 = getMContext();
                        if (mContext6 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        textView9.setBackground(ContextCompat.getDrawable(mContext6, R.drawable.up_person_home_mange_subscribe_bg));
                    }
                    TextView textView10 = (TextView) _$_findCachedViewById(R$id.text_no_subscribe);
                    if (textView10 != null) {
                        textView10.setVisibility(0);
                    }
                } else {
                    UserInfo userInfo3 = this.userInfo;
                    if (userInfo3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (userInfo3.getSubscribeFlag() == 2) {
                        TextView textView11 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                        if (textView11 != null) {
                            Context mContext7 = getMContext();
                            if (mContext7 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            } else {
                                textView11.setTextColor(ContextCompat.getColor(mContext7, R.color.color_FF9B2D));
                                Unit unit7 = Unit.INSTANCE;
                            }
                        }
                        TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                        if (textView12 != null) {
                            textView12.setText(AppUtil.getString(R.string.up_home_page_subscribe_expired));
                            Unit unit8 = Unit.INSTANCE;
                        }
                        TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_subscribe);
                        if (textView13 != null) {
                            Context mContext8 = getMContext();
                            if (mContext8 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            textView13.setBackground(ContextCompat.getDrawable(mContext8, R.drawable.up_person_home_mange_subscribe_bg));
                        }
                        TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_no_subscribe);
                        if (textView14 != null) {
                            textView14.setVisibility(0);
                        }
                    }
                }
            }
        }
        UserInfo userInfo4 = this.userInfo;
        if (userInfo4 != null && userInfo4.getSubscribeSwitch() == 1 && (relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_show_subscribed)) != null) {
            relativeLayout.setVisibility(0);
        }
        TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_subscribe_num);
        if (textView15 != null) {
            String string = AppUtil.getString(R.string.up_subsceribe_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.up_subsceribe_num)");
            Object[] objArr = new Object[1];
            UserInfo userInfo5 = this.userInfo;
            objArr[0] = String.valueOf(userInfo5 != null ? Integer.valueOf(userInfo5.getCountOfSubscriber()) : null);
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView15.setText(format);
        }
        UserInfo userInfo6 = this.userInfo;
        if (userInfo6 != null && (listTop3Subscriber = userInfo6.getListTop3Subscriber()) != null) {
            if (!(listTop3Subscriber == null || listTop3Subscriber.isEmpty())) {
                UserInfo userInfo7 = this.userInfo;
                if (userInfo7 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                ArrayList<UpSubscriberVo> listTop3Subscriber2 = userInfo7.getListTop3Subscriber();
                if (listTop3Subscriber2.size() == 1) {
                    listTop3Subscriber2.add(new UpSubscriberVo());
                    listTop3Subscriber2.add(new UpSubscriberVo());
                } else if (listTop3Subscriber2.size() == 2) {
                    listTop3Subscriber2.add(new UpSubscriberVo());
                }
                Intrinsics.checkExpressionValueIsNotNull(listTop3Subscriber2, "listTop3Subscriber");
                int i2 = 0;
                for (Object obj : listTop3Subscriber2) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    UpSubscriberVo upSubscriberVo = (UpSubscriberVo) obj;
                    if (i2 == 0) {
                        Intrinsics.checkExpressionValueIsNotNull(upSubscriberVo, "upSubscriberVo");
                        ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_01), new ImageBean(upSubscriberVo.getAvatar()));
                    } else if (i2 == 1) {
                        Intrinsics.checkExpressionValueIsNotNull(upSubscriberVo, "upSubscriberVo");
                        ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_02), new ImageBean(upSubscriberVo.getAvatar()));
                    } else if (i2 == 2) {
                        Intrinsics.checkExpressionValueIsNotNull(upSubscriberVo, "upSubscriberVo");
                        ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_03), new ImageBean(upSubscriberVo.getAvatar()));
                    }
                    i2 = i3;
                }
            }
        }
        UserInfo userInfo8 = this.userInfo;
        if (userInfo8 != null && Intrinsics.compare(userInfo8.getVipType(), 0) == 1) {
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_vip);
            if (imageView3 != null) {
                imageView3.setVisibility(0);
            }
        } else {
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_vip);
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
        }
        UserInfo userInfo9 = this.userInfo;
        if (userInfo9 != null && userInfo9.getUpLevel() == 1) {
            ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView5 != null) {
                imageView5.setVisibility(0);
            }
            ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_up);
            if (imageView6 != null) {
                imageView6.setImageResource(R.drawable.my_up_y1);
                Unit unit9 = Unit.INSTANCE;
            }
        } else {
            UserInfo userInfo10 = this.userInfo;
            if (userInfo10 != null && userInfo10.getUpLevel() == 2) {
                ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_up);
                if (imageView7 != null) {
                    imageView7.setVisibility(0);
                }
                ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_up);
                if (imageView8 != null) {
                    imageView8.setImageResource(R.drawable.my_up_y2);
                    Unit unit10 = Unit.INSTANCE;
                }
            } else {
                UserInfo userInfo11 = this.userInfo;
                if (userInfo11 != null && userInfo11.getUpLevel() == 3) {
                    ImageView imageView9 = (ImageView) _$_findCachedViewById(R$id.image_up);
                    if (imageView9 != null) {
                        imageView9.setVisibility(0);
                    }
                    ImageView imageView10 = (ImageView) _$_findCachedViewById(R$id.image_up);
                    if (imageView10 != null) {
                        imageView10.setImageResource(R.drawable.my_up_y3);
                        Unit unit11 = Unit.INSTANCE;
                    }
                    ImageView imageView11 = (ImageView) _$_findCachedViewById(R$id.image_up2);
                    if (imageView11 != null) {
                        imageView11.setVisibility(0);
                    }
                    ImageView imageView12 = (ImageView) _$_findCachedViewById(R$id.image_up2);
                    if (imageView12 != null) {
                        imageView12.setImageResource(R.drawable.my_up_y3_v);
                        Unit unit12 = Unit.INSTANCE;
                    }
                } else {
                    UserInfo userInfo12 = this.userInfo;
                    if (userInfo12 != null && userInfo12.getUpLevel() == 4) {
                        ImageView imageView13 = (ImageView) _$_findCachedViewById(R$id.image_up);
                        if (imageView13 != null) {
                            imageView13.setVisibility(0);
                        }
                        ((ImageView) _$_findCachedViewById(R$id.image_up)).setImageResource(R.drawable.my_up_y4);
                        ImageView imageView14 = (ImageView) _$_findCachedViewById(R$id.image_up2);
                        if (imageView14 != null) {
                            imageView14.setVisibility(0);
                        }
                        ImageView imageView15 = (ImageView) _$_findCachedViewById(R$id.image_up2);
                        if (imageView15 != null) {
                            imageView15.setImageResource(R.drawable.my_up_y3_v);
                            Unit unit13 = Unit.INSTANCE;
                        }
                        ImageView imageView16 = (ImageView) _$_findCachedViewById(R$id.image_up3);
                        if (imageView16 != null) {
                            imageView16.setVisibility(0);
                        }
                        ImageView imageView17 = (ImageView) _$_findCachedViewById(R$id.image_up3);
                        if (imageView17 != null) {
                            imageView17.setImageResource(R.drawable.my_up_y3);
                            Unit unit14 = Unit.INSTANCE;
                        }
                    } else {
                        UserInfo userInfo13 = this.userInfo;
                        if (userInfo13 != null && userInfo13.getUpLevel() == 5) {
                            ImageView imageView18 = (ImageView) _$_findCachedViewById(R$id.image_up);
                            if (imageView18 != null) {
                                imageView18.setVisibility(0);
                            }
                            ((ImageView) _$_findCachedViewById(R$id.image_up)).setImageResource(R.drawable.my_up_y5);
                        }
                    }
                }
            }
        }
        UserInfo userInfo14 = this.userInfo;
        if (Intrinsics.areEqual(userInfo14 != null ? userInfo14.getUpHostType() : null, "3") && (imageView2 = (ImageView) _$_findCachedViewById(R$id.image_up_original)) != null) {
            imageView2.setVisibility(0);
        }
        UserInfo userInfo15 = this.userInfo;
        if (userInfo15 != null && userInfo15.getGoldPorterFlag() == 1 && (imageView = (ImageView) _$_findCachedViewById(R$id.image_up_gold)) != null) {
            imageView.setVisibility(0);
        }
        UserInfo userInfo16 = this.userInfo;
        if (userInfo16 != null && userInfo16.isMemberIsAnchor()) {
            ImageView imageView19 = (ImageView) _$_findCachedViewById(R$id.image_anchor);
            if (imageView19 != null) {
                imageView19.setVisibility(0);
            }
        } else {
            ImageView imageView20 = (ImageView) _$_findCachedViewById(R$id.image_anchor);
            if (imageView20 != null) {
                imageView20.setVisibility(8);
            }
        }
        UserInfo userInfo17 = this.userInfo;
        if (userInfo17 != null && userInfo17.getReviewType() == 1) {
            ImageView imageView21 = (ImageView) _$_findCachedViewById(R$id.image_review);
            if (imageView21 != null) {
                imageView21.setVisibility(0);
            }
        } else {
            ImageView imageView22 = (ImageView) _$_findCachedViewById(R$id.image_review);
            if (imageView22 != null) {
                imageView22.setVisibility(8);
            }
        }
        UserInfo userInfo18 = this.userInfo;
        if (Intrinsics.areEqual(userInfo18 != null ? userInfo18.getSex() : null, "1")) {
            ImageView imageView23 = (ImageView) _$_findCachedViewById(R$id.image_sex);
            if (imageView23 != null) {
                imageView23.setImageResource(R.drawable.icon_sex_man);
                Unit unit15 = Unit.INSTANCE;
            }
        } else {
            ImageView imageView24 = (ImageView) _$_findCachedViewById(R$id.image_sex);
            if (imageView24 != null) {
                imageView24.setImageResource(R.drawable.icon_sex_woman);
                Unit unit16 = Unit.INSTANCE;
            }
        }
        UserInfo userInfo19 = this.userInfo;
        if (!TextUtils.isEmpty(userInfo19 != null ? userInfo19.getCity() : null)) {
            UserInfo userInfo20 = this.userInfo;
            if (userInfo20 != null) {
                country = userInfo20.getCity();
            }
            country = "";
        } else {
            UserInfo userInfo21 = this.userInfo;
            if (!TextUtils.isEmpty(userInfo21 != null ? userInfo21.getProvince() : null)) {
                UserInfo userInfo22 = this.userInfo;
                if (userInfo22 != null) {
                    country = userInfo22.getProvince();
                }
                country = "";
            } else {
                UserInfo userInfo23 = this.userInfo;
                if (!TextUtils.isEmpty(userInfo23 != null ? userInfo23.getCountry() : null)) {
                    UserInfo userInfo24 = this.userInfo;
                    if (userInfo24 != null) {
                        country = userInfo24.getCountry();
                    }
                }
                country = "";
            }
        }
        if (TextUtils.isEmpty(country)) {
            TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_area);
            if (textView16 != null) {
                textView16.setVisibility(8);
            }
        } else {
            TextView textView17 = (TextView) _$_findCachedViewById(R$id.text_area);
            if (textView17 != null) {
                textView17.setVisibility(0);
            }
        }
        TextView textView18 = (TextView) _$_findCachedViewById(R$id.text_area);
        if (textView18 != null) {
            textView18.setText(country);
        }
        ((ImageView) _$_findCachedViewById(R$id.iv_home_page)).setImageResource(i);
        Context mContext9 = getMContext();
        ImageView imageView25 = (ImageView) _$_findCachedViewById(R$id.iv_head_small);
        UserInfo userInfo25 = this.userInfo;
        if (userInfo25 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        ImageLoaderUtil.loadHeadImage(mContext9, imageView25, new ImageBean(userInfo25.getAvatar()));
        Context mContext10 = getMContext();
        RoundedImageView roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.iv_head_big);
        UserInfo userInfo26 = this.userInfo;
        if (userInfo26 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        ImageLoaderUtil.loadHeadImage(mContext10, roundedImageView, new ImageBean(userInfo26.getAvatar()), 186, 186);
        TextView tv_title = (TextView) _$_findCachedViewById(R$id.tv_title);
        Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
        UserInfo userInfo27 = this.userInfo;
        if (userInfo27 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        tv_title.setText(userInfo27.getName());
        UserInfo userInfo28 = this.userInfo;
        if (userInfo28 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (userInfo28.getFollowFlag() == 1) {
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_y);
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_y);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_n_add);
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_n_add);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
        }
        TextView tv_name = (TextView) _$_findCachedViewById(R$id.tv_name);
        Intrinsics.checkExpressionValueIsNotNull(tv_name, "tv_name");
        UserInfo userInfo29 = this.userInfo;
        if (userInfo29 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        tv_name.setText(userInfo29.getName());
        TextView tv_signature = (TextView) _$_findCachedViewById(R$id.tv_signature);
        Intrinsics.checkExpressionValueIsNotNull(tv_signature, "tv_signature");
        UserInfo userInfo30 = this.userInfo;
        if (userInfo30 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        tv_signature.setText(userInfo30.getSignatureHint());
        UserPermissionUtil userPermissionUtil = UserPermissionUtil.getInstance();
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.ll_user_level);
        ImageView imageView26 = (ImageView) _$_findCachedViewById(R$id.iv_user_level);
        TextView textView19 = (TextView) _$_findCachedViewById(R$id.tv_user_level);
        UserInfo userInfo31 = this.userInfo;
        if (userInfo31 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        userPermissionUtil.uerLevelShow(linearLayout, imageView26, textView19, userInfo31.getCurrentLevelIndex());
        UserInfo userInfo32 = this.userInfo;
        if (userInfo32 != null) {
            LevelBean levelBean = new LevelBean(userInfo32.getCurrentLevelIndex());
            UserPermissionUtil.getInstance().userLevelNickShow((LinearLayout) _$_findCachedViewById(R$id.ll_level_nick), (ImageView) _$_findCachedViewById(R$id.iv_level_nick), (TextView) _$_findCachedViewById(R$id.tv_level_nick), levelBean);
            TextView tv_level_nick = (TextView) _$_findCachedViewById(R$id.tv_level_nick);
            Intrinsics.checkExpressionValueIsNotNull(tv_level_nick, "tv_level_nick");
            tv_level_nick.setText(levelBean.getLevelNickName());
            TextView textView20 = (TextView) _$_findCachedViewById(R$id.tv_focus_num);
            String[] strArr = new String[2];
            StringBuilder sb = new StringBuilder();
            UserInfo userInfo33 = this.userInfo;
            if (userInfo33 != null) {
                sb.append(String.valueOf(userInfo33.getUserFollowCount()));
                sb.append("");
                strArr[0] = sb.toString();
                strArr[1] = AppUtil.getString(R.string.my_focus);
                ViewUtil.initTextViewWithSpannableString(textView20, strArr, new String[]{String.valueOf(getResources().getColor(R.color.text_light)), String.valueOf(getResources().getColor(R.color.text_light))}, new String[]{"16", "12"});
                TextView textView21 = (TextView) _$_findCachedViewById(R$id.tv_fan_num);
                String[] strArr2 = new String[2];
                StringBuilder sb2 = new StringBuilder();
                UserInfo userInfo34 = this.userInfo;
                if (userInfo34 != null) {
                    sb2.append(String.valueOf(userInfo34.getUserFansCount()));
                    sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    strArr2[0] = sb2.toString();
                    strArr2[1] = AppUtil.getString(R.string.my_fans);
                    ViewUtil.initTextViewWithSpannableString(textView21, strArr2, new String[]{String.valueOf(getResources().getColor(R.color.text_light)), String.valueOf(getResources().getColor(R.color.text_light))}, new String[]{"16", "12"});
                    UserInfo userInfo35 = this.userInfo;
                    if (userInfo35 == null || !userInfo35.isMemberIsAnchor()) {
                        return;
                    }
                    TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                    Context mContext11 = getMContext();
                    UserInfo userInfo36 = this.userInfo;
                    if (userInfo36 != null) {
                        str = String.valueOf(userInfo36.getMemberId());
                    }
                    singleton.getLiveStatus(mContext11, str, new TomatoLiveSDKUtils.LiveStatusEntityListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$initPersonInfo$2
                        @Override // com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.LiveStatusEntityListener
                        public final void callbackLiveStatus(String str2, String liveId) {
                            boolean z;
                            Context mContext12;
                            NewHomePageFragment.this.isLivIng = "1".equals(str2);
                            NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                            Intrinsics.checkExpressionValueIsNotNull(liveId, "liveId");
                            newHomePageFragment.liveId = liveId;
                            z = NewHomePageFragment.this.isLivIng;
                            if (z) {
                                LinearLayout ll_live_ing = (LinearLayout) NewHomePageFragment.this._$_findCachedViewById(R$id.ll_live_ing);
                                Intrinsics.checkExpressionValueIsNotNull(ll_live_ing, "ll_live_ing");
                                ll_live_ing.setVisibility(0);
                                mContext12 = NewHomePageFragment.this.getMContext();
                                ImageLoaderUtil.loadNormalDrawableGif(mContext12, (ImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_live_ing), R.drawable.my_home_page_live_ing);
                                RoundedImageView iv_head_big = (RoundedImageView) NewHomePageFragment.this._$_findCachedViewById(R$id.iv_head_big);
                                Intrinsics.checkExpressionValueIsNotNull(iv_head_big, "iv_head_big");
                                iv_head_big.setBorderColor(Color.parseColor("#FC4C7B"));
                                return;
                            }
                            LinearLayout ll_live_ing2 = (LinearLayout) NewHomePageFragment.this._$_findCachedViewById(R$id.ll_live_ing);
                            Intrinsics.checkExpressionValueIsNotNull(ll_live_ing2, "ll_live_ing");
                            ll_live_ing2.setVisibility(8);
                        }
                    });
                    return;
                }
                Intrinsics.throwNpe();
                throw null;
            }
            Intrinsics.throwNpe();
            throw null;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    private final void getPersonMemberInfo() {
        NewHomePagePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestPersonInfo(this.personMemberId, DBUtil.getMemberId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showHomePageActionDialog() {
        if (this.userInfo == null) {
            return;
        }
        if (this.homePageActionDialog == null && getActivity() != null) {
            this.homePageActionDialog = new HomePageActionDialog(this);
        }
        HomePageActionDialog homePageActionDialog = this.homePageActionDialog;
        if (homePageActionDialog != null) {
            homePageActionDialog.show();
        }
        HomePageActionDialog homePageActionDialog2 = this.homePageActionDialog;
        if (homePageActionDialog2 == null) {
            return;
        }
        homePageActionDialog2.setUserInfo(this.userInfo);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.isMySelf || this.userInfoVersion >= DBUtil.getUserInfo().getLocalVersion()) {
            return;
        }
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        this.userInfoVersion = userInfo.getLocalVersion();
        ImageView iv_home_page = (ImageView) _$_findCachedViewById(R$id.iv_home_page);
        Intrinsics.checkExpressionValueIsNotNull(iv_home_page, "iv_home_page");
        iv_home_page.setEnabled(true);
        this.userInfo = DBUtil.getUserInfo();
        initPersonInfo();
    }

    public final void cancelShield() {
        NewHomePagePresenter mPresenter;
        if (this.userInfo == null || (mPresenter = getMPresenter()) == null) {
            return;
        }
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            mPresenter.requestCancelShield(userInfo.getMemberId(), DBUtil.getMemberId());
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView
    public void handlerCancelShield() {
        ToastUtil.showCenterToast((int) R.string.post_cancel_shield_success);
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            userInfo.setShieldFlag(0);
        }
    }

    public final void shield() {
        NewHomePagePresenter mPresenter;
        if (this.userInfo == null || (mPresenter = getMPresenter()) == null) {
            return;
        }
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            mPresenter.requestPostShield(userInfo.getMemberId(), 1, DBUtil.getMemberId());
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView
    public void handlerShield() {
        ToastUtil.showCenterToast((int) R.string.post_shield_success);
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            userInfo.setShieldFlag(1);
        }
    }

    public final void onEventMainThread(MemberFocusEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        int i = event.f1748id;
        int i2 = event.followFlag;
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            userInfo.setFollowFlag(i2);
        }
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 != null && userInfo2.getFollowFlag() == 1) {
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_y);
            ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_y);
            ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            return;
        }
        ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setText(R.string.common_focus_n_add);
        ((TextView) _$_findCachedViewById(R$id.tv_top_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
        ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setText(R.string.common_focus_n_add);
        ((TextView) _$_findCachedViewById(R$id.tv_bottom_focus)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void uploadHomePage() {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.mine.view.NewHomePageFragment$uploadHomePage$1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                NewHomePageFragment.this.showWaitingDialog();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia media) {
                TTUtil tTUtil;
                TTUtil tTUtil2;
                String str;
                Intrinsics.checkParameterIsNotNull(media, "media");
                NewHomePageFragment newHomePageFragment = NewHomePageFragment.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                tTUtil = NewHomePageFragment.this.ttUtil;
                if (tTUtil == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                sb.append(tTUtil.getBucketName());
                sb.append("/");
                tTUtil2 = NewHomePageFragment.this.ttUtil;
                if (tTUtil2 != null) {
                    sb.append(tTUtil2.getCeph(media));
                    newHomePageFragment.bgUrl = sb.toString();
                    NewHomePageFragment newHomePageFragment2 = NewHomePageFragment.this;
                    str = newHomePageFragment2.bgUrl;
                    newHomePageFragment2.updateInfo(str);
                    return;
                }
                Intrinsics.throwNpe();
                throw null;
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                NewHomePageFragment.this.hideWaitingDialog();
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != 0) {
            tTUtil.getStsToken(this.selectList);
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            List<LocalMedia> obtainMultipleResult = PictureSelector.obtainMultipleResult(intent);
            Intrinsics.checkExpressionValueIsNotNull(obtainMultipleResult, "PictureSelector.obtainMultipleResult(data)");
            this.selectList = obtainMultipleResult;
            for (LocalMedia localMedia : this.selectList) {
                Log.i("图片-----》", localMedia.getPath());
            }
            uploadHomePage();
        }
    }
}
