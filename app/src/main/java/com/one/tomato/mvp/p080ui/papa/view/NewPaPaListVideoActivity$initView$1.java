package com.one.tomato.mvp.p080ui.papa.view;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.mine.view.NewHomePageFragment;
import com.one.tomato.utils.LogUtil;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaListVideoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaListVideoActivity$initView$1 */
/* loaded from: classes3.dex */
public final class NewPaPaListVideoActivity$initView$1 implements ViewPager.OnPageChangeListener {
    final /* synthetic */ NewPaPaListVideoActivity this$0;

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NewPaPaListVideoActivity$initView$1(NewPaPaListVideoActivity newPaPaListVideoActivity) {
        this.this$0 = newPaPaListVideoActivity;
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        LogUtil.m3787d("yang", "拖動的方向---------" + f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005b, code lost:
        r4 = r3.this$0.newHomePageFragment;
     */
    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onPageSelected(int i) {
        NewHomePageFragment newHomePageFragment;
        NewHomePageFragment newHomePageFragment2;
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment;
        NewPaPaVideoPlayFragment newPaPaVideoPlayFragment2;
        NewHomePageFragment newHomePageFragment3;
        if (i == 0) {
            this.this$0.onBackPressed();
        } else if (i == 1) {
            PreviewViewPager previewViewPager = (PreviewViewPager) this.this$0._$_findCachedViewById(R$id.view_pager);
            if (previewViewPager == null) {
                return;
            }
            previewViewPager.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.NewPaPaListVideoActivity$initView$1$onPageSelected$1
                @Override // java.lang.Runnable
                public final void run() {
                    NewHomePageFragment newHomePageFragment4;
                    NewPaPaVideoPlayFragment newPaPaVideoPlayFragment3;
                    newHomePageFragment4 = NewPaPaListVideoActivity$initView$1.this.this$0.newHomePageFragment;
                    if (newHomePageFragment4 != null) {
                        newHomePageFragment4.onPusePlay();
                    }
                    newPaPaVideoPlayFragment3 = NewPaPaListVideoActivity$initView$1.this.this$0.newPaPaFragment;
                    if (newPaPaVideoPlayFragment3 != null) {
                        newPaPaVideoPlayFragment3.callVideoResume();
                    }
                }
            }, 300L);
        } else if (i != 2) {
        } else {
            newHomePageFragment = this.this$0.newHomePageFragment;
            if (newHomePageFragment == null) {
                return;
            }
            ArrayList<Fragment> listFragment = this.this$0.getListFragment();
            newHomePageFragment2 = this.this$0.newHomePageFragment;
            PostList postList = null;
            if (newHomePageFragment2 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!listFragment.contains(newHomePageFragment2)) {
            } else {
                newPaPaVideoPlayFragment = this.this$0.newPaPaFragment;
                if (newPaPaVideoPlayFragment != null) {
                    newPaPaVideoPlayFragment.onVideoStop();
                }
                newPaPaVideoPlayFragment2 = this.this$0.newPaPaFragment;
                if (newPaPaVideoPlayFragment2 != null) {
                    postList = newPaPaVideoPlayFragment2.getPostList();
                }
                if (postList == null || newHomePageFragment3 == null) {
                    return;
                }
                newHomePageFragment3.refreshNewPresonId(postList.getMemberId());
            }
        }
    }
}
