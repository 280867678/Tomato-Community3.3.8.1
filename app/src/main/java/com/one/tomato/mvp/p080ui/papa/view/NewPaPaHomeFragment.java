package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.PapaPublishUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaHomeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment */
/* loaded from: classes3.dex */
public final class NewPaPaHomeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PaPaTabAdapter fragmentAdapter;
    private PaPaHotHomeFragment hotHomeFragment;
    private NewPaPaTabFragment newFragment;
    private NewPaPaTabFragment recommendFragment;
    private NewPaPaTabFragment rewardFragment;
    private final List<String> tabString;
    private List<Fragment> tabFragment = new ArrayList();
    private final NewPaPaHomeFragment$papaPushListener$1 papaPushListener = new PapaPublishUtil.PublishListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment$papaPushListener$1
        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishDefault() {
            ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPaPaHomeFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.papa_tab_home_publish);
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishIng(boolean z) {
            ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPaPaHomeFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.publish_ing);
            if (!z) {
            }
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishSuccess() {
            LogUtil.m3788d("拍拍上傳回調成功");
            ImageLoaderUtil.loadNormalDrawableGif(BaseApplication.getApplication(), (ImageView) NewPaPaHomeFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.publish_success);
        }

        @Override // com.one.tomato.utils.post.PapaPublishUtil.PublishListener
        public void publishFail(Context context, PublishInfo publishInfo) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(publishInfo, "publishInfo");
            LogUtil.m3788d("拍拍上傳回調失敗");
            ImageLoaderUtil.loadNormalDrawableImg(BaseApplication.getApplication(), (ImageView) NewPaPaHomeFragment.this._$_findCachedViewById(R$id.iv_publish), R.drawable.post_publish_fail);
        }
    };

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
        return R.layout.new_papa_tab_home_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment$papaPushListener$1] */
    public NewPaPaHomeFragment() {
        List<String> mutableListOf;
        mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(AppUtil.getString(R.string.post_tab_recommend), AppUtil.getString(R.string.post_comment_last), AppUtil.getString(R.string.post_tab_hot), AppUtil.getString(R.string.papa_tab_reward));
        this.tabString = mutableListOf;
    }

    /* compiled from: NewPaPaHomeFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewPaPaHomeFragment getInstance(int i) {
            NewPaPaHomeFragment newPaPaHomeFragment = new NewPaPaHomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("goInType", i);
            newPaPaHomeFragment.setArguments(bundle);
            return newPaPaHomeFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        this.recommendFragment = NewPaPaTabFragment.Companion.initInstance(1);
        this.hotHomeFragment = new PaPaHotHomeFragment();
        this.rewardFragment = NewPaPaTabFragment.Companion.initInstance(3);
        this.newFragment = NewPaPaTabFragment.Companion.initInstance(4);
        List<Fragment> list = this.tabFragment;
        NewPaPaTabFragment newPaPaTabFragment = this.recommendFragment;
        if (newPaPaTabFragment == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        list.add(newPaPaTabFragment);
        List<Fragment> list2 = this.tabFragment;
        NewPaPaTabFragment newPaPaTabFragment2 = this.newFragment;
        if (newPaPaTabFragment2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        list2.add(newPaPaTabFragment2);
        List<Fragment> list3 = this.tabFragment;
        PaPaHotHomeFragment paPaHotHomeFragment = this.hotHomeFragment;
        if (paPaHotHomeFragment == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        list3.add(paPaHotHomeFragment);
        List<Fragment> list4 = this.tabFragment;
        NewPaPaTabFragment newPaPaTabFragment3 = this.rewardFragment;
        if (newPaPaTabFragment3 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        list4.add(newPaPaTabFragment3);
        this.fragmentAdapter = new PaPaTabAdapter(getChildFragmentManager(), this.tabFragment, this.tabString);
        PreviewViewPager viewpager = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setAdapter(this.fragmentAdapter);
        PreviewViewPager viewpager2 = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager2, "viewpager");
        viewpager2.setOffscreenPageLimit(this.tabFragment.size());
        PreviewViewPager viewpager3 = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager3, "viewpager");
        viewpager3.setCurrentItem(0);
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((PreviewViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false);
        ((ImageView) _$_findCachedViewById(R$id.iv_publish)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NewPaPaHomeFragment.this.startPublishActivity();
            }
        });
        Bundle arguments = getArguments();
        if ((arguments != null ? arguments.getInt("goInType") : 0) != 1) {
            return;
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaHomeFragment$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    FragmentActivity activity = NewPaPaHomeFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            });
        }
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        if (previewViewPager == null) {
            return;
        }
        previewViewPager.setPadding(0, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startPublishActivity() {
        PublishInfo publishInfo = new PublishInfo();
        publishInfo.setPostType(4);
        PapaPublishUtil.getInstance().startPublishActivity(publishInfo);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        PapaPublishUtil.getInstance().setContext(getActivity(), this.papaPushListener);
    }
}
