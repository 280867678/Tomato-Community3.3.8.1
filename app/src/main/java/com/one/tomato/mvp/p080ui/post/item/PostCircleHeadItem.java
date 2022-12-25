package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.circle.view.CircleTabActivity;
import com.one.tomato.mvp.p080ui.post.adapter.CircleRecommentAdapter;
import com.one.tomato.mvp.p080ui.post.item.PostCircleItem;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostCircleHeadItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem */
/* loaded from: classes3.dex */
public final class PostCircleHeadItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private CircleRecommentAdapter adapter;
    private final PostCircleHeadItem$itemClick$1 itemClick = new PostCircleItem.ItemClick() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$itemClick$1
        @Override // com.one.tomato.mvp.p080ui.post.item.PostCircleItem.ItemClick
        public void clickItemFollow(int i, int i2) {
            CircleRecommentAdapter circleRecommentAdapter;
            circleRecommentAdapter = PostCircleHeadItem.this.adapter;
            CircleDiscoverListBean item = circleRecommentAdapter != null ? circleRecommentAdapter.getItem(i2) : null;
            PostCircleHeadItem.this.followFoucs(i, i2, (item == null || item.followFlag != 0) ? "/app/follow/delete" : "/app/follow/save");
        }
    };

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
    /* JADX WARN: Type inference failed for: r0v1, types: [com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$itemClick$1] */
    public PostCircleHeadItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.post_new_circle_head_item, this);
        initRecyclerView();
    }

    public final void setData(ArrayList<CircleDiscoverListBean> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.better_recycler);
            if (betterHorScrollRecyclerView != null) {
                betterHorScrollRecyclerView.setVisibility(8);
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.setVisibility(8);
            return;
        }
        CircleRecommentAdapter circleRecommentAdapter = this.adapter;
        if (circleRecommentAdapter != null) {
            circleRecommentAdapter.setNewData(arrayList);
        }
        CircleRecommentAdapter circleRecommentAdapter2 = this.adapter;
        if (circleRecommentAdapter2 == null) {
            return;
        }
        circleRecommentAdapter2.setEnableLoadMore(false);
    }

    private final void initRecyclerView() {
        requestCircleDiscover();
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.better_recycler);
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        }
        this.adapter = new CircleRecommentAdapter(this.itemClick);
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.better_recycler);
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.setAdapter(this.adapter);
        }
        CircleRecommentAdapter circleRecommentAdapter = this.adapter;
        if (circleRecommentAdapter != null) {
            circleRecommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$initRecyclerView$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    CircleDetail circleDetail = new CircleDetail();
                    Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                    if (item instanceof CircleDiscoverListBean) {
                        CircleDiscoverListBean circleDiscoverListBean = (CircleDiscoverListBean) item;
                        circleDetail.setArticleCount(circleDiscoverListBean.articleCount);
                        circleDetail.setLogo(circleDiscoverListBean.groupImage);
                        circleDetail.setBrief(circleDiscoverListBean.brief);
                        circleDetail.setCategoryName(circleDiscoverListBean.categoryName);
                        circleDetail.setFollowCount(circleDiscoverListBean.followCount);
                        circleDetail.setFollowFlag(circleDiscoverListBean.followFlag);
                        circleDetail.setId(circleDiscoverListBean.groupId);
                        circleDetail.setName(circleDiscoverListBean.groupName);
                        circleDetail.setOfficial(circleDiscoverListBean.official);
                    }
                    CircleSingleActivity.startActivity(PostCircleHeadItem.this.getContext(), circleDetail);
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_more_circle);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$initRecyclerView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostCircleHeadItem.this.getContext().startActivity(new Intent(PostCircleHeadItem.this.getContext(), CircleTabActivity.class));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void followFoucs(int i, final int i2, String str) {
        ApiImplService.Companion.getApiImplService().circleFllow(str, DBUtil.getMemberId(), i).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$followFoucs$1
            /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
                r0 = r3.this$0.adapter;
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResult(Object obj) {
                CircleRecommentAdapter circleRecommentAdapter;
                CircleRecommentAdapter circleRecommentAdapter2;
                Integer num;
                circleRecommentAdapter = PostCircleHeadItem.this.adapter;
                CircleDiscoverListBean item = circleRecommentAdapter != null ? circleRecommentAdapter.getItem(i2) : null;
                Integer valueOf = item != null ? Integer.valueOf(item.followFlag) : null;
                if (valueOf != null) {
                    valueOf.intValue();
                    if (valueOf != null && valueOf.intValue() == 1) {
                        num = 0;
                        ToastUtil.showCenterToast((int) R.string.common_cancel_focus_success);
                    } else {
                        num = 1;
                        ToastUtil.showCenterToast((int) R.string.common_focus_success);
                    }
                    if (item == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    item.followFlag = num.intValue();
                }
                if (item == null || circleRecommentAdapter2 == null) {
                    return;
                }
                circleRecommentAdapter2.setData(i2, item);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("圈子關注失敗 +++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    public final void requestCircleDiscover() {
        ApiImplService.Companion.getApiImplService().requestCircleDiscoverHome(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<CircleDiscoverListBean>>() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleHeadItem$requestCircleDiscover$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CircleDiscoverListBean> arrayList) {
                PostCircleHeadItem.this.setData(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("推荐的圈子错误 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }
}
