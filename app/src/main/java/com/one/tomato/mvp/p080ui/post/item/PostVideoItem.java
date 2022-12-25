package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostVideoItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoItem */
/* loaded from: classes3.dex */
public final class PostVideoItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private Context mContext;
    private PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostVideoItem(Context context, PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.mContext = context;
        this.postEvenOneTabVideoListManger = postEvenOneTabVideoListManger;
        initView();
    }

    public final void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_new_post_video, this);
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ((RelativeLayout) _$_findCachedViewById(R$id.image_back_ground)).setBackgroundColor(ContextCompat.getColor(getContext(), PostUtils.INSTANCE.getBackGround()));
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoItem$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                    if (TextUtils.isEmpty(PreferencesUtil.getInstance().getString("video_auto"))) {
                        PostVideoItem.this.autoPlay();
                        return;
                    }
                    postEvenOneTabVideoListManger = PostVideoItem.this.postEvenOneTabVideoListManger;
                    if (postEvenOneTabVideoListManger == null) {
                        return;
                    }
                    postEvenOneTabVideoListManger.pressPostVideoPlay((ImageView) PostVideoItem.this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) PostVideoItem.this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) PostVideoItem.this._$_findCachedViewById(R$id.image_video_play), (TextView) PostVideoItem.this._$_findCachedViewById(R$id.tv_video_time), "帖子列表");
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void autoPlay() {
        Context context = getContext();
        if (context != null) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
            customAlertDialog.setTitle(R.string.post_auto_play_tishi);
            customAlertDialog.setMessage(R.string.post_video_auto_play_notice);
            customAlertDialog.setBottomVerticalLineVisible(true);
            customAlertDialog.setTitleBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_custom_title_bg_white));
            customAlertDialog.setBottomHorizontalLineVisible(true);
            customAlertDialog.setCancelButton(R.string.common_cancel_no, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoItem$autoPlay$$inlined$let$lambda$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                    CustomAlertDialog customAlertDialog2 = CustomAlertDialog.this;
                    if (customAlertDialog2 != null) {
                        customAlertDialog2.dismiss();
                    }
                    PreferencesUtil.getInstance().putString("video_auto", "0");
                    postEvenOneTabVideoListManger = this.postEvenOneTabVideoListManger;
                    if (postEvenOneTabVideoListManger != null) {
                        postEvenOneTabVideoListManger.pressPostVideoPlay((ImageView) this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) this._$_findCachedViewById(R$id.image_video_play), (TextView) this._$_findCachedViewById(R$id.tv_video_time), "帖子列表");
                    }
                }
            });
            customAlertDialog.setCancelButtonTextColor(R.color.color_5B92E1);
            customAlertDialog.setConfirmButtonTextColor(R.color.color_5B92E1);
            customAlertDialog.setCancelButtonBackgroundColor(ContextCompat.getColor(context, R.color.white));
            customAlertDialog.setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.white));
            customAlertDialog.setConfirmButton(R.string.common_confirm_yes, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoItem$autoPlay$$inlined$let$lambda$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
                    CustomAlertDialog customAlertDialog2 = CustomAlertDialog.this;
                    if (customAlertDialog2 != null) {
                        customAlertDialog2.dismiss();
                    }
                    PreferencesUtil.getInstance().putString("video_auto", "1");
                    postEvenOneTabVideoListManger = this.postEvenOneTabVideoListManger;
                    if (postEvenOneTabVideoListManger != null) {
                        postEvenOneTabVideoListManger.pressPostVideoPlay((ImageView) this._$_findCachedViewById(R$id.image_video_cove), (FrameLayout) this._$_findCachedViewById(R$id.fram_ijkplay_view), (ImageView) this._$_findCachedViewById(R$id.image_video_play), (TextView) this._$_findCachedViewById(R$id.tv_video_time), "帖子列表");
                    }
                }
            });
            customAlertDialog.show();
        }
    }

    public final void setPostListData(PostList postList) {
        clearState();
        PostUtils postUtils = PostUtils.INSTANCE;
        setLayoutParams(new RelativeLayout.LayoutParams(-1, postUtils.calculationItemMaxHeight(postUtils.getPicWidth(postList != null ? postList.getSize() : null), PostUtils.INSTANCE.getPicHeight(postList != null ? postList.getSize() : null))));
        FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
        if (frameLayout != null) {
            frameLayout.setTag(R.id.video_list_item_id, postList);
        }
        if (postList != null) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_bg_blur);
            if (imageView != null) {
                imageView.setTag(R.id.glide_load_image_id, postList.getSecVideoCover());
            }
            ImageLoaderUtil.loadViewPagerOriginImageBlurs(this.mContext, (ImageView) _$_findCachedViewById(R$id.image_video_cove), null, new ImageBean(postList.getSecVideoCover()), new PostVideoItem$setPostListData$$inlined$let$lambda$1(this, postList));
            ImageLoaderUtil.loadViewPagerOriginImage(this.mContext, (ImageView) _$_findCachedViewById(R$id.image_video_cove), null, new ImageBean(postList.getSecVideoCover()), 0);
            TextView tv_video_time = (TextView) _$_findCachedViewById(R$id.tv_video_time);
            Intrinsics.checkExpressionValueIsNotNull(tv_video_time, "tv_video_time");
            tv_video_time.setText(postList.getVideoTime());
        }
    }

    public final void clearState() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_video_play);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_video_time);
        if (textView != null) {
            textView.setVisibility(0);
        }
        FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view);
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_bg_blur);
        if (imageView3 != null) {
            imageView3.setVisibility(8);
        }
        View _$_findCachedViewById = _$_findCachedViewById(R$id.background_view);
        if (_$_findCachedViewById != null) {
            _$_findCachedViewById.setVisibility(8);
        }
        ((FrameLayout) _$_findCachedViewById(R$id.fram_ijkplay_view)).removeAllViews();
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.image_back_ground);
        if (relativeLayout != null) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(getContext(), PostUtils.INSTANCE.getBackGround()));
        }
        ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_video_cove);
        if (imageView4 != null) {
            imageView4.setImageBitmap(null);
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_bg_blur);
        if (imageView5 != null) {
            imageView5.setImageBitmap(null);
        }
    }
}
