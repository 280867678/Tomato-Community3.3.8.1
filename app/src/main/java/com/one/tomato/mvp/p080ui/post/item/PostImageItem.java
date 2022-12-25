package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.PagerSnapHelper;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostImageItemAdapter;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostImageItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostImageItem */
/* loaded from: classes3.dex */
public final class PostImageItem extends FrameLayout {
    private HashMap _$_findViewCache;
    private NewPostImageItemAdapter adapter;
    private boolean isAlreadyUpData;
    private boolean isReviewPost;
    private NewPostItemOnClickCallBack newPostItemOnClickCallBack;
    private PagerSnapHelper pageSnapHelper;
    private PostList postList;

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
    public PostImageItem(final Context context, final NewPostItemOnClickCallBack newPostItemOnClickCallBack) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.item_new_post_image, (ViewGroup) this, true);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.frame_image_background);
        if (relativeLayout != null) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(context, PostUtils.INSTANCE.getBackGround()));
        }
        this.newPostItemOnClickCallBack = newPostItemOnClickCallBack;
        RecyclerView image_recycle = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        Intrinsics.checkExpressionValueIsNotNull(image_recycle, "image_recycle");
        this.adapter = new NewPostImageItemAdapter(context, image_recycle);
        final BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(context, 0, false);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
        this.pageSnapHelper = new PagerSnapHelper();
        PagerSnapHelper pagerSnapHelper = this.pageSnapHelper;
        if (pagerSnapHelper != null) {
            pagerSnapHelper.attachToRecyclerView((RecyclerView) _$_findCachedViewById(R$id.image_recycle));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        if (recyclerView2 != null) {
            recyclerView2.setHasFixedSize(true);
        }
        baseLinearLayoutManager.setItemPrefetchEnabled(true);
        baseLinearLayoutManager.setInitialPrefetchItemCount(2);
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.adapter);
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        if (recyclerView4 != null) {
            recyclerView4.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.post.item.PostImageItem.1
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView5, int i) {
                    List<ImageBean> data;
                    Intrinsics.checkParameterIsNotNull(recyclerView5, "recyclerView");
                    super.onScrollStateChanged(recyclerView5, i);
                    if (i == 0) {
                        if (!PostImageItem.this.isAlreadyUpData) {
                            PostImageItem.this.isAlreadyUpData = true;
                            PostUtils.INSTANCE.updatePostBrowse(PostImageItem.this.postList);
                        }
                        int findFirstCompletelyVisibleItemPosition = baseLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                        TextView textView = (TextView) PostImageItem.this._$_findCachedViewById(R$id.text_show_index);
                        if (textView != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(findFirstCompletelyVisibleItemPosition + 1);
                            sb.append('/');
                            NewPostImageItemAdapter newPostImageItemAdapter = PostImageItem.this.adapter;
                            sb.append((newPostImageItemAdapter == null || (data = newPostImageItemAdapter.getData()) == null) ? null : Integer.valueOf(data.size()));
                            textView.setText(sb.toString());
                        }
                        int i2 = findFirstCompletelyVisibleItemPosition + 1;
                        try {
                            NewPostImageItemAdapter newPostImageItemAdapter2 = PostImageItem.this.adapter;
                            if (newPostImageItemAdapter2 != null) {
                                int i3 = 0;
                                if (i2 == newPostImageItemAdapter2.getData().size() / 2) {
                                    PostList postList = PostImageItem.this.postList;
                                    DataUploadUtil.uploadVideoPlayHalf(postList != null ? postList.getId() : 0);
                                }
                                NewPostImageItemAdapter newPostImageItemAdapter3 = PostImageItem.this.adapter;
                                if (newPostImageItemAdapter3 == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                } else if (i2 != newPostImageItemAdapter3.getData().size()) {
                                    return;
                                } else {
                                    PostList postList2 = PostImageItem.this.postList;
                                    if (postList2 != null) {
                                        i3 = postList2.getId();
                                    }
                                    DataUploadUtil.uploadVideoPlayWhole(i3);
                                    return;
                                }
                            }
                            Intrinsics.throwNpe();
                            throw null;
                        } catch (Exception unused) {
                        }
                    }
                }
            });
        }
        NewPostImageItemAdapter newPostImageItemAdapter = this.adapter;
        if (newPostImageItemAdapter != null) {
            newPostImageItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostImageItem.2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    PostList postList;
                    PostList postList2 = PostImageItem.this.postList;
                    if (postList2 != null && !postList2.isAd()) {
                        PostList postList3 = PostImageItem.this.postList;
                        if (postList3 != null && !postList3.isAlreadyPaid()) {
                            PostList postList4 = PostImageItem.this.postList;
                            if ((postList4 != null ? postList4.getPrice() : 0) > 0 && (((postList = PostImageItem.this.postList) == null || postList.getMemberId() != DBUtil.getMemberId()) && !PostImageItem.this.isReviewPost)) {
                                PostImageItem.this.isShowRewardDialog();
                                return;
                            }
                        }
                        ImageShowActivity.Companion companion = ImageShowActivity.Companion;
                        Context context2 = context;
                        List<Object> data = baseQuickAdapter != null ? baseQuickAdapter.getData() : null;
                        if (data == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.util.ArrayList<com.one.tomato.entity.ImageBean>");
                        }
                        companion.startActivity(context2, (ArrayList) data, i, PostImageItem.this.postList, PostImageItem.this.isReviewPost);
                        return;
                    }
                    NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = newPostItemOnClickCallBack;
                    if (newPostItemOnClickCallBack2 == null) {
                        return;
                    }
                    newPostItemOnClickCallBack2.itemADClick(PostImageItem.this.postList);
                }
            });
        }
    }

    public final void setImageBeanList(PostList postList, ArrayList<ImageBean> list, String str) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        this.postList = postList;
        this.isAlreadyUpData = false;
        this.isReviewPost = false;
        int width = (int) DisplayMetricsUtils.getWidth();
        String pullPostImageFirstSize = PostUtils.INSTANCE.pullPostImageFirstSize(postList);
        if (!TextUtils.isEmpty(pullPostImageFirstSize)) {
            PostUtils postUtils = PostUtils.INSTANCE;
            width = postUtils.calculationItemMaxHeight(postUtils.getPicWidth(pullPostImageFirstSize), PostUtils.INSTANCE.getPicHeight(pullPostImageFirstSize));
        }
        setLayoutParams(new RelativeLayout.LayoutParams(-1, width));
        if (list.size() == 0 || list.size() == 1) {
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
            if (recyclerView != null) {
                recyclerView.setTag(R.id.image_list_item_id, true);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_show_index);
            if (textView != null) {
                textView.setVisibility(8);
            }
        } else {
            RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
            if (recyclerView2 != null) {
                recyclerView2.setTag(R.id.image_list_item_id, false);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_show_index);
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_show_index);
            if (textView3 != null) {
                textView3.setText("1/" + list.size());
            }
        }
        NewPostImageItemAdapter newPostImageItemAdapter = this.adapter;
        if (newPostImageItemAdapter != null) {
            newPostImageItemAdapter.setNewData(list);
        }
        NewPostImageItemAdapter newPostImageItemAdapter2 = this.adapter;
        if (newPostImageItemAdapter2 != null) {
            newPostImageItemAdapter2.setEnableLoadMore(false);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.image_recycle);
        if (recyclerView3 != null) {
            recyclerView3.post(new Runnable() { // from class: com.one.tomato.mvp.ui.post.item.PostImageItem$setImageBeanList$1
                @Override // java.lang.Runnable
                public final void run() {
                    AppUtil.recyclerViewScroll((RecyclerView) PostImageItem.this._$_findCachedViewById(R$id.image_recycle), 0, 0, 100);
                }
            });
        }
        int memberId = DBUtil.getMemberId();
        if (postList != null && memberId == postList.getMemberId()) {
            NewPostImageItemAdapter newPostImageItemAdapter3 = this.adapter;
            if (newPostImageItemAdapter3 == null) {
                return;
            }
            newPostImageItemAdapter3.isReward(false);
        } else if (Intrinsics.areEqual(str, "review_post_pre") || Intrinsics.areEqual(str, "review_post")) {
            NewPostImageItemAdapter newPostImageItemAdapter4 = this.adapter;
            if (newPostImageItemAdapter4 != null) {
                newPostImageItemAdapter4.isReward(false);
            }
            this.isReviewPost = true;
        } else if (postList != null && !postList.isAlreadyPaid() && postList.getPrice() > 0) {
            NewPostImageItemAdapter newPostImageItemAdapter5 = this.adapter;
            if (newPostImageItemAdapter5 == null) {
                return;
            }
            newPostImageItemAdapter5.isReward(true);
        } else {
            NewPostImageItemAdapter newPostImageItemAdapter6 = this.adapter;
            if (newPostImageItemAdapter6 == null) {
                return;
            }
            newPostImageItemAdapter6.isReward(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void isShowRewardDialog() {
        PostList postList;
        PostList postList2 = this.postList;
        String str = null;
        if (postList2 == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (postList2.isAlreadyPaid()) {
        } else {
            PostUtils postUtils = PostUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkExpressionValueIsNotNull(context, "context");
            PostList postList3 = this.postList;
            if (postList3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            String valueOf = String.valueOf(postList3.getPrice());
            PostList postList4 = this.postList;
            if (postList4 != null) {
                str = String.valueOf(postList4.getId());
            }
            String str2 = str;
            PostList postList5 = this.postList;
            postUtils.showImageNeedPayDialog(context, valueOf, str2, (postList5 == null || postList5.getSubscribeSwitch() != 1 || (postList = this.postList) == null) ? 0 : postList.getMemberId(), new PostImageItem$isShowRewardDialog$1(this), PostImageItem$isShowRewardDialog$2.INSTANCE);
        }
    }
}
