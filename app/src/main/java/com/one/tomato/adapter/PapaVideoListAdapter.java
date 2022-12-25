package com.one.tomato.adapter;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.dialog.GamePlayDialog;
import com.one.tomato.dialog.PapaMenuDialog;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaVideoPlayFragment;
import com.one.tomato.mvp.p080ui.papa.view.PapaReportActivity;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.PostShareActivity;
import com.one.tomato.mvp.p080ui.post.view.TagPostListAct;
import com.one.tomato.p085ui.tag.TagSelectActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.thirdpart.video.controller.PapaVideoController;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes3.dex */
public class PapaVideoListAdapter extends BaseRecyclerViewAdapter<PostList> {
    private BottomSheetBehavior behavior;
    private Context context;
    private PapaMenuDialog papaMenuDialog;
    private NewPaPaVideoPlayFragment papaTabFragment;
    private PapaVideoController papaVideoController;
    private RecyclerView recyclerView;

    public PapaVideoListAdapter(Context context, RecyclerView recyclerView, PapaVideoController papaVideoController, NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        super(context, R.layout.item_video_papa_tab, recyclerView);
        this.recyclerView = recyclerView;
        this.papaVideoController = papaVideoController;
        this.papaTabFragment = newPaPaVideoPlayFragment;
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final PostList postList) {
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        getData().indexOf(postList);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.thumbImageView);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_right);
        RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.iv_head);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_add_person);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_favor);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_favor_num);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_comment_num);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_share_num);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_bottom);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_nick);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_title);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.tv_ad_tag);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_ad);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_ad_head);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.tv_ad_nick);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.tv_ad_num);
        TextView textView9 = (TextView) baseViewHolder.getView(R.id.tv_ad_action);
        TextView textView10 = (TextView) baseViewHolder.getView(R.id.tv_ad_content);
        ImageView imageView5 = (ImageView) baseViewHolder.getView(R.id.image_post_reward);
        TextView textView11 = (TextView) baseViewHolder.getView(R.id.text_post_reward);
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recycler_tag);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relate_tag);
        baseViewHolder.addOnClickListener(R.id.iv_head).addOnClickListener(R.id.iv_add_person).addOnClickListener(R.id.iv_favor).addOnClickListener(R.id.iv_comment).addOnClickListener(R.id.iv_share).addOnClickListener(R.id.ll_ad).addOnClickListener(R.id.image_post_reward).addOnClickListener(R.id.image_game);
        if (postList == null) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            return;
        }
        imageView.setImageResource(R.drawable.video_cover_default);
        imageView.setImageBitmap(null);
        ImageLoaderUtil.loadViewPagerOriginImage(this.context, imageView, null, new ImageBean(postList.getSecVideoCover()), R.drawable.video_cover_default);
        imageView5.setVisibility(8);
        textView11.setVisibility(8);
        if (postList.isAd()) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(0);
            ImageLoaderUtil.loadHeadImage(this.mContext, imageView4, new ImageBean(postList.getAvatar()));
            textView7.setText(postList.getName());
            if (postList.getPage().getAdType() == 1) {
                textView8.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(postList.getPage().getViewsNum())));
                textView9.setText(R.string.post_ad_view_detail);
                textView6.setText(R.string.common_ad);
            } else if (postList.getPage().getAdType() == 2) {
                if ("1".equals(postList.getPage().getAdLinkType())) {
                    textView8.setText(AppUtil.getString(R.string.post_ad_download_num, FormatUtil.formatNumOverTenThousand(postList.getPage().getViewsNum())));
                    textView9.setText(R.string.post_ad_download);
                } else if ("2".equals(postList.getPage().getAdLinkType())) {
                    textView8.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(postList.getPage().getViewsNum())));
                    textView9.setText(R.string.post_ad_view_detail);
                }
                textView6.setText(R.string.common_ad);
            } else if (postList.getPage().getAdType() == 3) {
                textView8.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(postList.getPage().getViewsNum())));
                textView9.setText(R.string.post_ad_web_app_start);
                textView6.setText(R.string.common_web_app);
            }
            textView10.setText(postList.getDescription());
            return;
        }
        linearLayout.setVisibility(0);
        linearLayout2.setVisibility(0);
        linearLayout3.setVisibility(8);
        String videoTime = postList.getVideoTime();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        if (PostUtils.INSTANCE.isLongPaPaVideo(videoTime)) {
            layoutParams.bottomMargin = (int) DisplayMetricsUtils.dp2px(48.0f);
            layoutParams2.bottomMargin = (int) DisplayMetricsUtils.dp2px(48.0f);
        } else {
            layoutParams.bottomMargin = (int) DisplayMetricsUtils.dp2px(32.0f);
            layoutParams2.bottomMargin = (int) DisplayMetricsUtils.dp2px(32.0f);
        }
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout.setLayoutParams(layoutParams2);
        ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(postList.getAvatar()));
        if (postList.getIsAttention() == 1 || postList.isAd() || postList.getMemberId() == DBUtil.getMemberId()) {
            imageView2.setVisibility(8);
            imageView2.setEnabled(false);
        } else {
            imageView2.setEnabled(true);
            imageView2.setVisibility(0);
            imageView2.setImageResource(R.drawable.papa_add_person);
        }
        if (postList.getIsThumbUp() == 1) {
            imageView3.setImageResource(R.drawable.video_favor_s);
        } else {
            imageView3.setImageResource(R.drawable.video_favor_n);
        }
        textView.setText(FormatUtil.formatNumOverTenThousand(postList.getGoodNum() + ""));
        textView2.setText(FormatUtil.formatNumOverTenThousand(postList.getVideoCommentTimes() + ""));
        textView3.setText(FormatUtil.formatNumOverTenThousand(postList.getShareTimes()));
        textView4.setText("@" + postList.getName());
        textView5.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView5.setText(!TextUtils.isEmpty(postList.getTitle()) ? postList.getTitle() : postList.getDescription());
        relativeLayout.setVisibility(0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        PaPaTagAdapter paPaTagAdapter = new PaPaTagAdapter();
        recyclerView.setAdapter(paPaTagAdapter);
        if (postList.getTagList() != null && postList.getTagList().size() > 0) {
            ArrayList<Tag> tagList = postList.getTagList();
            if (tagList.size() < 6) {
                if (tagList.get(0).getTagId() != -10) {
                    Tag tag = new Tag();
                    tag.setTagId(-10);
                    tag.setTagName("");
                    paPaTagAdapter.addData((PaPaTagAdapter) tag);
                }
                paPaTagAdapter.addData((Collection) tagList);
            } else {
                paPaTagAdapter.addData((Collection) tagList.subList(0, 6));
            }
        } else {
            Tag tag2 = new Tag();
            tag2.setTagId(-10);
            tag2.setTagName("");
            paPaTagAdapter.addData((PaPaTagAdapter) tag2);
        }
        paPaTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.adapter.PapaVideoListAdapter.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Tag tag3 = (Tag) baseQuickAdapter.getItem(i);
                if (tag3.getTagId() == -10) {
                    TagSelectActivity.startActivity(PapaVideoListAdapter.this.papaTabFragment, ((BaseQuickAdapter) PapaVideoListAdapter.this).mContext, (ArrayList) baseQuickAdapter.getData(), 1, PapaVideoListAdapter.this.getData().indexOf(postList));
                } else {
                    TagPostListAct.Companion.startAct(((BaseQuickAdapter) PapaVideoListAdapter.this).mContext, tag3);
                }
            }
        });
        if (!postList.isMemberIsUp()) {
            return;
        }
        imageView5.setVisibility(0);
        textView11.setVisibility(0);
        textView11.setText(String.valueOf(postList.getPayTimes()));
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        PostList item = getItem(i);
        View findViewByPosition = this.recyclerView.getLayoutManager().findViewByPosition(i);
        int i2 = 0;
        switch (view.getId()) {
            case R.id.image_game /* 2131296950 */:
                GamePlayDialog gamePlayDialog = new GamePlayDialog(this.mContext);
                ArrayList<SubGamesBean> postGameBean = DBUtil.getPostGameBean(C2516Ad.TYPE_PAPA_REC);
                if (postGameBean != null && postGameBean.size() > 0) {
                    gamePlayDialog.showGame(postGameBean, 0);
                    return;
                } else {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_game_no));
                    return;
                }
            case R.id.image_post_reward /* 2131296981 */:
                this.papaTabFragment.showRewardDialog();
                return;
            case R.id.iv_add_person /* 2131297069 */:
                this.papaTabFragment.focus(item, i);
                return;
            case R.id.iv_comment /* 2131297148 */:
                this.papaTabFragment.onCommentClick(item, (TextView) findViewByPosition.findViewById(R.id.tv_comment_num));
                return;
            case R.id.iv_favor /* 2131297190 */:
                this.papaVideoController.setShowPauseIcon(false);
                if (this.papaTabFragment.onFavourClick(item)) {
                    return;
                }
                int goodNum = item.getGoodNum();
                if (item.getIsThumbUp() == 1) {
                    item.setIsThumbUp(0);
                    ((ImageView) findViewByPosition.findViewById(R.id.iv_favor)).setImageResource(R.drawable.video_favor_n);
                    int i3 = goodNum - 1;
                    if (i3 > 0) {
                        i2 = i3;
                    }
                } else {
                    item.setIsThumbUp(1);
                    ((ImageView) findViewByPosition.findViewById(R.id.iv_favor)).setImageResource(R.drawable.video_favor_s);
                    i2 = goodNum + 1;
                }
                item.setGoodNum(i2);
                ((TextView) findViewByPosition.findViewById(R.id.tv_favor_num)).setText(FormatUtil.formatNumOverTenThousand(item.getGoodNum() + ""));
                return;
            case R.id.iv_head /* 2131297220 */:
                this.papaVideoController.setShowPauseIcon(false);
                this.papaTabFragment.onHeadClick(item);
                return;
            case R.id.iv_share /* 2131297361 */:
                showMenuDialog(item);
                return;
            case R.id.ll_ad /* 2131297490 */:
                this.papaTabFragment.startPostAd(item);
                return;
            default:
                return;
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setPreLoadNumber(int i) {
        super.setPreLoadNumber(5);
    }

    private void showMenuDialog(PostList postList) {
        if (this.papaMenuDialog == null) {
            this.papaMenuDialog = new PapaMenuDialog(this.context);
            this.behavior = BottomSheetBehavior.from((View) ((CoordinatorLayout) this.papaMenuDialog.findViewById(R.id.coordinatorLayout)).getParent());
        }
        this.papaMenuDialog.setPostList(postList);
        this.papaMenuDialog.show();
        this.behavior.setState(3);
        this.papaMenuDialog.setMenuListener(new PapaMenuDialog.MenuListener() { // from class: com.one.tomato.adapter.PapaVideoListAdapter.2
            @Override // com.one.tomato.dialog.PapaMenuDialog.MenuListener
            public void copy(PostList postList2) {
                PostShareActivity.Companion.startAct(((BaseQuickAdapter) PapaVideoListAdapter.this).mContext, postList2);
            }

            @Override // com.one.tomato.dialog.PapaMenuDialog.MenuListener
            public void report(PostList postList2) {
                PapaReportActivity.startActivity(((BaseQuickAdapter) PapaVideoListAdapter.this).mContext, postList2.getId());
            }

            @Override // com.one.tomato.dialog.PapaMenuDialog.MenuListener
            public void download(PostList postList2) {
                PapaVideoListAdapter.this.papaTabFragment.videoDown();
            }

            @Override // com.one.tomato.dialog.PapaMenuDialog.MenuListener
            public void saveDCIM(PostList postList2) {
                if (postList2.getCanDownload() == 1) {
                    PapaVideoListAdapter.this.papaTabFragment.saveDCIM();
                } else {
                    ToastUtil.showCenterToast("发布者未开放下载！");
                }
            }

            @Override // com.one.tomato.dialog.PapaMenuDialog.MenuListener
            public void collect(PostList postList2) {
                PapaVideoListAdapter.this.papaTabFragment.collect(postList2);
            }
        });
    }
}
