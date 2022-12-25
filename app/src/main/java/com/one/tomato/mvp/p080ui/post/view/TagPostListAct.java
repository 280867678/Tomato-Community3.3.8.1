package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TagPostListAct.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.TagPostListAct */
/* loaded from: classes3.dex */
public final class TagPostListAct extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private NewPaPaTabFragment papaFragment;
    private MyHomePagePostFragment postHotFragment;
    private MyHomePagePostFragment postNewFragment;
    private Tag tag;
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
        return R.layout.activity_tag_post_list;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: TagPostListAct.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.TagPostListAct$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, Tag tag) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            Intent intent = new Intent(context, TagPostListAct.class);
            intent.putExtra("intent_tag", tag);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        String str;
        Intent intent = getIntent();
        this.tag = intent != null ? (Tag) intent.getParcelableExtra("intent_tag") : null;
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_tag_title);
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            Tag tag = this.tag;
            if (tag == null || (str = tag.getTagName()) == null) {
                str = "";
            }
            sb.append((Object) str);
            textView.setText(sb.toString());
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_tag_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.TagPostListAct$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TagPostListAct.this.onBackPressed();
                }
            });
        }
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            NoHorScrollViewPager viewpager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
            Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
            viewpager.setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.home_page_must_hot));
        this.stringList.add(AppUtil.getString(R.string.home_page_must_new));
        this.stringList.add(AppUtil.getString(R.string.home_page_papa));
        int size = this.stringList.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                this.postHotFragment = MyHomePagePostFragment.Companion.getInstance(-1, "hot_tag", true);
                MyHomePagePostFragment myHomePagePostFragment = this.postHotFragment;
                if (myHomePagePostFragment != null) {
                    Tag tag2 = this.tag;
                    myHomePagePostFragment.setTagId(tag2 != null ? tag2.getTagId() : 0);
                }
                ArrayList<Fragment> arrayList = this.fragmentList;
                MyHomePagePostFragment myHomePagePostFragment2 = this.postHotFragment;
                if (myHomePagePostFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList.add(myHomePagePostFragment2);
            } else if (i == 1) {
                this.postNewFragment = MyHomePagePostFragment.Companion.getInstance(-1, "new_tag", true);
                MyHomePagePostFragment myHomePagePostFragment3 = this.postNewFragment;
                if (myHomePagePostFragment3 != null) {
                    Tag tag3 = this.tag;
                    myHomePagePostFragment3.setTagId(tag3 != null ? tag3.getTagId() : 0);
                }
                ArrayList<Fragment> arrayList2 = this.fragmentList;
                MyHomePagePostFragment myHomePagePostFragment4 = this.postNewFragment;
                if (myHomePagePostFragment4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList2.add(myHomePagePostFragment4);
            } else if (i != 2) {
                continue;
            } else {
                NewPaPaTabFragment.Companion companion = NewPaPaTabFragment.Companion;
                Tag tag4 = this.tag;
                this.papaFragment = companion.initInstance(10, tag4 != null ? tag4.getTagId() : 0);
                ArrayList<Fragment> arrayList3 = this.fragmentList;
                NewPaPaTabFragment newPaPaTabFragment = this.papaFragment;
                if (newPaPaTabFragment == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList3.add(newPaPaTabFragment);
            }
        }
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false, true);
        NoHorScrollViewPager viewpager2 = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager2, "viewpager");
        viewpager2.setOffscreenPageLimit(this.fragmentList.size());
    }
}
