package com.one.tomato.mvp.p080ui.papa.view;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.PagerAdapter;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.mvp.p080ui.mine.view.NewHomePageFragment;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPaPaListVideoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaListVideoActivity$observerCurrentPostIsAD$1 */
/* loaded from: classes3.dex */
final class NewPaPaListVideoActivity$observerCurrentPostIsAD$1 extends Lambda implements Function1<Boolean, Unit> {
    final /* synthetic */ NewPaPaListVideoActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPaPaListVideoActivity$observerCurrentPostIsAD$1(NewPaPaListVideoActivity newPaPaListVideoActivity) {
        super(1);
        this.this$0 = newPaPaListVideoActivity;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Boolean bool) {
        invoke(bool.booleanValue());
        return Unit.INSTANCE;
    }

    public final void invoke(boolean z) {
        NewHomePageFragment newHomePageFragment;
        NewHomePageFragment newHomePageFragment2;
        NewHomePageFragment newHomePageFragment3;
        PagerAdapter adapter;
        NewHomePageFragment newHomePageFragment4;
        NewHomePageFragment newHomePageFragment5;
        NewHomePageFragment newHomePageFragment6;
        PagerAdapter adapter2;
        if (z) {
            if (this.this$0.getListFragment().size() <= 0) {
                return;
            }
            newHomePageFragment4 = this.this$0.newHomePageFragment;
            if (newHomePageFragment4 == null) {
                return;
            }
            ArrayList<Fragment> listFragment = this.this$0.getListFragment();
            newHomePageFragment5 = this.this$0.newHomePageFragment;
            if (newHomePageFragment5 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!listFragment.contains(newHomePageFragment5)) {
            } else {
                ArrayList<Fragment> listFragment2 = this.this$0.getListFragment();
                newHomePageFragment6 = this.this$0.newHomePageFragment;
                if (newHomePageFragment6 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                listFragment2.remove(newHomePageFragment6);
                PreviewViewPager previewViewPager = (PreviewViewPager) this.this$0._$_findCachedViewById(R$id.view_pager);
                if (previewViewPager == null || (adapter2 = previewViewPager.getAdapter()) == null) {
                    return;
                }
                adapter2.notifyDataSetChanged();
            }
        } else if (this.this$0.getListFragment().size() <= 0) {
        } else {
            newHomePageFragment = this.this$0.newHomePageFragment;
            if (newHomePageFragment == null) {
                return;
            }
            ArrayList<Fragment> listFragment3 = this.this$0.getListFragment();
            newHomePageFragment2 = this.this$0.newHomePageFragment;
            if (newHomePageFragment2 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (listFragment3.contains(newHomePageFragment2)) {
            } else {
                ArrayList<Fragment> listFragment4 = this.this$0.getListFragment();
                newHomePageFragment3 = this.this$0.newHomePageFragment;
                if (newHomePageFragment3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                listFragment4.add(newHomePageFragment3);
                PreviewViewPager previewViewPager2 = (PreviewViewPager) this.this$0._$_findCachedViewById(R$id.view_pager);
                if (previewViewPager2 == null || (adapter = previewViewPager2.getAdapter()) == null) {
                    return;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
