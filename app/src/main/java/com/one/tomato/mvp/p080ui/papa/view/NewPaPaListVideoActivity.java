package com.one.tomato.mvp.p080ui.papa.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.view.View;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewHomePageFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.NewPaPaViewPagerAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaListVideoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaListVideoActivity */
/* loaded from: classes3.dex */
public final class NewPaPaListVideoActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private NewHomePageFragment newHomePageFragment;
    private NewPaPaVideoPlayFragment newPaPaFragment;
    private final ArrayList<Fragment> listFragment = new ArrayList<>();
    private final Function1<Boolean, Unit> observerCurrentPostIsAD = new NewPaPaListVideoActivity$observerCurrentPostIsAD$1(this);

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
        return R.layout.activity_new_pa_pa_list_video;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    public final ArrayList<Fragment> getListFragment() {
        return this.listFragment;
    }

    /* compiled from: NewPaPaListVideoActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaListVideoActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, ArrayList<PostList> arrayList, String str, String str2, int i, int i2, int i3, boolean z) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("postList", arrayList);
                bundle.putString("business", str);
                bundle.putString("search_key", str2);
                bundle.putInt("person_member_id", i);
                bundle.putInt("page_no", i2);
                bundle.putInt("start_position", i3);
                bundle.putBoolean("isLoadEnable", z);
                Intent intent = new Intent(context, NewPaPaListVideoActivity.class);
                intent.putExtras(bundle);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 30);
                } else {
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void startAct(Context context, ArrayList<PostList> arrayList, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("postList", arrayList);
                bundle.putInt("person_member_id", i);
                Intent intent = new Intent(context, NewPaPaListVideoActivity.class);
                intent.putExtras(bundle);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 30);
                } else {
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        NewHomePageFragment newHomePageFragment;
        super.onActivityResult(i, i2, intent);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null && previewViewPager.getCurrentItem() == 1) {
            NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = this.newPaPaFragment;
            if (newPaPaVideoPlayFragment == null) {
                return;
            }
            newPaPaVideoPlayFragment.onActivityResult(i, i2, intent);
            return;
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager2 == null || previewViewPager2.getCurrentItem() != 2 || (newHomePageFragment = this.newHomePageFragment) == null) {
            return;
        }
        newHomePageFragment.onActivityResult(i, i2, intent);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        Bundle extras;
        this.newPaPaFragment = new NewPaPaVideoPlayFragment();
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = this.newPaPaFragment;
        if (newPaPaVideoPlayFragment != null) {
            newPaPaVideoPlayFragment.setObseverIsAd(this.observerCurrentPostIsAD);
        }
        AlphaFragment alphaFragment = new AlphaFragment();
        this.newHomePageFragment = NewHomePageFragment.Companion.getInstance(1);
        NewHomePageFragment newHomePageFragment = this.newHomePageFragment;
        if (newHomePageFragment != null) {
            Intent intent = getIntent();
            newHomePageFragment.setNewPersonId((intent == null || (extras = intent.getExtras()) == null) ? 0 : extras.getInt("person_member_id"));
        }
        this.listFragment.add(alphaFragment);
        ArrayList<Fragment> arrayList = this.listFragment;
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment2 = this.newPaPaFragment;
        if (newPaPaVideoPlayFragment2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        arrayList.add(newPaPaVideoPlayFragment2);
        ArrayList<Fragment> arrayList2 = this.listFragment;
        NewHomePageFragment newHomePageFragment2 = this.newHomePageFragment;
        if (newHomePageFragment2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        arrayList2.add(newHomePageFragment2);
        NewPaPaViewPagerAdapter newPaPaViewPagerAdapter = new NewPaPaViewPagerAdapter(getSupportFragmentManager(), this.listFragment);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(newPaPaViewPagerAdapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setCurrentItem(1);
        }
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setOffscreenPageLimit(this.listFragment.size());
        }
        PreviewViewPager previewViewPager4 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager4 == null) {
            return;
        }
        previewViewPager4.addOnPageChangeListener(new NewPaPaListVideoActivity$initView$1(this));
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = this.newPaPaFragment;
        int i = 0;
        if (newPaPaVideoPlayFragment == null || newPaPaVideoPlayFragment.get_PageNo() != 0) {
            NewPaPaVideoPlayFragment newPaPaVideoPlayFragment2 = this.newPaPaFragment;
            bundle.putInt("page_no", newPaPaVideoPlayFragment2 != null ? newPaPaVideoPlayFragment2.get_PageNo() : 0);
        }
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment3 = this.newPaPaFragment;
        bundle.putInt("start_position", newPaPaVideoPlayFragment3 != null ? newPaPaVideoPlayFragment3.get_StartPosition() : 0);
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment4 = this.newPaPaFragment;
        if (newPaPaVideoPlayFragment4 == null || newPaPaVideoPlayFragment4.get_CurrentPostion() != 0) {
            NewPaPaVideoPlayFragment newPaPaVideoPlayFragment5 = this.newPaPaFragment;
            if (newPaPaVideoPlayFragment5 != null) {
                i = newPaPaVideoPlayFragment5.get_CurrentPostion();
            }
            bundle.putInt("cur_position", i);
        }
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment6 = this.newPaPaFragment;
        bundle.putParcelableArrayList("postList", newPaPaVideoPlayFragment6 != null ? newPaPaVideoPlayFragment6.get_UploadData() : null);
        intent.putExtras(bundle);
        setResult(-1, intent);
        super.onBackPressed();
    }
}
