package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.papa.view.PapaGridListFragment;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* loaded from: classes3.dex */
public class PapaGridListAdapter extends BaseRecyclerViewAdapter<PostList> {
    private String businessType;
    private Context context;
    private RelativeLayout.LayoutParams layoutParams;
    private PapaGridListFragment papaGridListFragment;

    public PapaGridListAdapter(PapaGridListFragment papaGridListFragment, RecyclerView recyclerView, String str) {
        super(papaGridListFragment.getContext(), R.layout.item_papa_grid, recyclerView);
        char c;
        int i;
        this.context = papaGridListFragment.getContext();
        this.papaGridListFragment = papaGridListFragment;
        this.businessType = str;
        int hashCode = str.hashCode();
        int i2 = 0;
        if (hashCode == -1110540878) {
            if (str.equals("circle_all")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 166751952) {
            if (hashCode == 1502545125 && str.equals("papa_search")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("papa_mine")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            i2 = ((int) (DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(2.0f))) / 3;
            i = i2;
        } else if (c == 1) {
            i2 = ((int) (DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(2.0f))) / 3;
            i = (i2 * 4) / 3;
        } else if (c != 2) {
            i = 0;
        } else {
            i2 = ((int) (DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(2.0f))) / 2;
            i = (i2 * 4) / 3;
        }
        this.layoutParams = new RelativeLayout.LayoutParams(i2, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostList postList) {
        char c;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_cover);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_thumb_num);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_duration);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_video_icon);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_video_author);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_video_desc);
        ((RelativeLayout) baseViewHolder.getView(R.id.rl_root)).setLayoutParams(this.layoutParams);
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode == -1110540878) {
            if (str.equals("circle_all")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 166751952) {
            if (hashCode == 1502545125 && str.equals("papa_search")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("papa_mine")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            textView2.setVisibility(0);
            textView2.setText(postList.getVideoTime());
            imageView2.setVisibility(0);
        } else if (c == 1) {
            textView.setVisibility(0);
            textView.setText(postList.getFavorTimes() + "");
        } else if (c == 2) {
            textView.setVisibility(0);
            textView.setText(postList.getFavorTimes() + "");
            textView3.setText(postList.getName());
            textView4.setVisibility(0);
            textView4.setText(postList.getDescription());
        }
        ImageBean imageBean = new ImageBean();
        imageBean.setImage(postList.getSecVideoCover());
        ImageLoaderUtil.loadRecyclerThumbImage(this.context, imageView, imageBean);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        this.papaGridListFragment.onRecyclerItemClick(i);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.papaGridListFragment.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.papaGridListFragment.loadMore();
    }
}
