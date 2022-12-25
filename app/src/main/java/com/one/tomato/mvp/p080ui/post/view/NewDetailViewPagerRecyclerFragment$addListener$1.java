package com.one.tomato.mvp.p080ui.post.view;

import android.graphics.Rect;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailCallBack;
import com.one.tomato.mvp.p080ui.post.presenter.NewCommentPresenter;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.widget.image.MNGestureView;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewDetailViewPagerRecyclerFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addListener$1 */
/* loaded from: classes3.dex */
public final class NewDetailViewPagerRecyclerFragment$addListener$1 extends RecyclerView.OnScrollListener {
    final /* synthetic */ NewDetailViewPagerRecyclerFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NewDetailViewPagerRecyclerFragment$addListener$1(NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment) {
        this.this$0 = newDetailViewPagerRecyclerFragment;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0066, code lost:
        if (r6 != 1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0076, code lost:
        r6 = r4.this$0.itemData;
     */
    @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onScrollStateChanged(final RecyclerView recyclerView, int i) {
        PostList postList;
        PostList postList2;
        PostList postList3;
        int i2;
        NewCommentPresenter access$getMPresenter$p;
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        super.onScrollStateChanged(recyclerView, i);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (i == 0 && (access$getMPresenter$p = NewDetailViewPagerRecyclerFragment.access$getMPresenter$p(this.this$0)) != null && access$getMPresenter$p.getMShouldScroll()) {
                NewCommentPresenter access$getMPresenter$p2 = NewDetailViewPagerRecyclerFragment.access$getMPresenter$p(this.this$0);
                Integer valueOf = access$getMPresenter$p2 != null ? Integer.valueOf(access$getMPresenter$p2.getIndex()) : null;
                if (valueOf != null) {
                    NewCommentPresenter access$getMPresenter$p3 = NewDetailViewPagerRecyclerFragment.access$getMPresenter$p(this.this$0);
                    if (access$getMPresenter$p3 != null) {
                        access$getMPresenter$p3.setMShouldScroll(false);
                    }
                    NewCommentPresenter access$getMPresenter$p4 = NewDetailViewPagerRecyclerFragment.access$getMPresenter$p(this.this$0);
                    if (access$getMPresenter$p4 != null) {
                        access$getMPresenter$p4.smoothMoveToPosition(recyclerView, valueOf.intValue());
                    }
                }
            }
            postList = this.this$0.itemData;
            if (postList != null && postList.getPostType() == 2) {
                i2 = this.this$0.currentVideoHorizontalandVertical;
            }
            postList2 = this.this$0.itemData;
            if ((postList2 == null || postList2.getPostType() != 1) && (postList3 == null || postList3.getPostType() != 3)) {
                return;
            }
            recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$addListener$1$onScrollStateChanged$1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    MNGestureView mNView;
                    MNGestureView mNView2;
                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                    View findViewByPosition = linearLayoutManager.findViewByPosition(0);
                    RelativeLayout relativeLayout = findViewByPosition != null ? (RelativeLayout) findViewByPosition.findViewById(R.id.relate_person) : null;
                    if (relativeLayout != null) {
                        boolean globalVisibleRect = relativeLayout.getGlobalVisibleRect(new Rect());
                        try {
                            if (!relativeLayout.isShown() || !globalVisibleRect || findViewByPosition.getTop() != 0) {
                                NewDetailViewPagerRecyclerFragment$addListener$1.this.this$0.isDropDown = true;
                                PostDetailCallBack postDetailCallBack = NewDetailViewPagerRecyclerFragment$addListener$1.this.this$0.getPostDetailCallBack();
                                if (postDetailCallBack != null && (mNView = postDetailCallBack.getMNView()) != null) {
                                    mNView.isAppBarEx = true;
                                }
                            } else {
                                NewDetailViewPagerRecyclerFragment$addListener$1.this.this$0.isDropDown = false;
                                PostDetailCallBack postDetailCallBack2 = NewDetailViewPagerRecyclerFragment$addListener$1.this.this$0.getPostDetailCallBack();
                                if (postDetailCallBack2 != null && (mNView2 = postDetailCallBack2.getMNView()) != null) {
                                    mNView2.isAppBarEx = false;
                                }
                            }
                        } catch (Exception e) {
                            LogUtil.m3787d("yan6", "异常------------" + e.getMessage());
                        }
                    }
                    return true;
                }
            });
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0019, code lost:
        r7 = r5.this$0.itemData;
     */
    @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        PostList postList;
        PostList postList2;
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        super.onScrolled(recyclerView, i, i2);
        postList = this.this$0.itemData;
        if ((postList == null || postList.getPostType() != 3) && (postList2 == null || postList2.getPostType() != 1)) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
        }
        View findViewByPosition = ((LinearLayoutManager) layoutManager).findViewByPosition(0);
        View view = null;
        LinearLayout linearLayout = findViewByPosition != null ? (LinearLayout) findViewByPosition.findViewById(R.id.linear_person) : null;
        try {
            int[] iArr = new int[2];
            if (linearLayout != null) {
                linearLayout.getLocationInWindow(iArr);
            }
            int[] iArr2 = new int[2];
            RelativeLayout relativeLayout = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.image_relative);
            if (relativeLayout != null) {
                relativeLayout.getLocationInWindow(iArr2);
            }
            if (iArr.length != 2 || iArr2.length != 2) {
                return;
            }
            int i3 = iArr[1];
            int i4 = iArr2[1];
            if (i3 <= i4) {
                LinearLayout linearLayout2 = (LinearLayout) this.this$0._$_findCachedViewById(R$id.linear_person);
                if (linearLayout2 != null) {
                    linearLayout2.removeView((RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_person));
                }
                RelativeLayout relativeLayout2 = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.image_relative);
                if (relativeLayout2 == null) {
                    return;
                }
                relativeLayout2.addView((RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_person));
            } else if (i3 <= i4) {
            } else {
                LinearLayout linearLayout3 = (LinearLayout) this.this$0._$_findCachedViewById(R$id.linear_person);
                if (linearLayout3 != null) {
                    view = linearLayout3.getChildAt(0);
                }
                if (view != null) {
                    return;
                }
                RelativeLayout relativeLayout3 = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.image_relative);
                if (relativeLayout3 != null) {
                    relativeLayout3.removeView((RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_person));
                }
                LinearLayout linearLayout4 = (LinearLayout) this.this$0._$_findCachedViewById(R$id.linear_person);
                if (linearLayout4 == null) {
                    return;
                }
                linearLayout4.addView((RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_person), 0);
            }
        } catch (Exception e) {
            LogUtil.m3787d("yan6", "异常------------" + e.getMessage());
        }
    }
}
