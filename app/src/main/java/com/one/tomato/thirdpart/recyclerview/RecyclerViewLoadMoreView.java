package com.one.tomato.thirdpart.recyclerview;

import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/* loaded from: classes3.dex */
public class RecyclerViewLoadMoreView extends LoadMoreView {
    private int loadStatus = 1;

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    public int getLayoutId() {
        return R.layout.recyclerview_load_more_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    public void setLoadMoreStatus(int i) {
        super.setLoadMoreStatus(i);
        this.loadStatus = i;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    public void convert(BaseViewHolder baseViewHolder) {
        super.convert(baseViewHolder);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) baseViewHolder.getView(R.id.loading_progress);
        if (lottieAnimationView == null) {
            return;
        }
        if (lottieAnimationView.getAnimation() != null && lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
        int i = this.loadStatus;
        if (i == 2) {
            lottieAnimationView.setAnimation("loading_more.json");
        } else if (i == 3) {
        }
    }
}
