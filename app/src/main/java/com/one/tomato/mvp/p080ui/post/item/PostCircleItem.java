package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.one.tomato.mvp.p080ui.post.adapter.CircleRecommentAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostCircleItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostCircleItem */
/* loaded from: classes3.dex */
public final class PostCircleItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private CircleRecommentAdapter adapter;
    private final PostCircleItem$itemClick$1 itemClick = new ItemClick() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleItem$itemClick$1
        @Override // com.one.tomato.mvp.p080ui.post.item.PostCircleItem.ItemClick
        public void clickItemFollow(int i, int i2) {
            CircleRecommentAdapter circleRecommentAdapter;
            circleRecommentAdapter = PostCircleItem.this.adapter;
            CircleDiscoverListBean item = circleRecommentAdapter != null ? circleRecommentAdapter.getItem(i2) : null;
            PostCircleItem.this.followFoucs(i, i2, (item == null || item.followFlag != 0) ? "/app/follow/delete" : "/app/follow/save");
        }
    };
    private Context mContex;

    /* compiled from: PostCircleItem.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.item.PostCircleItem$ItemClick */
    /* loaded from: classes3.dex */
    public interface ItemClick {
        void clickItemFollow(int i, int i2);
    }

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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.one.tomato.mvp.ui.post.item.PostCircleItem$itemClick$1] */
    public PostCircleItem(Context context) {
        super(context);
        this.mContex = context;
        LayoutInflater.from(context).inflate(R.layout.item_recomment_view, this);
        initRecyclerView();
    }

    private final void initRecyclerView() {
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
            circleRecommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleItem$initRecyclerView$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context context;
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
                    context = PostCircleItem.this.mContex;
                    CircleSingleActivity.startActivity(context, circleDetail);
                }
            });
        }
    }

    public final void setData(ArrayList<CircleDiscoverListBean> arrayList) {
        if (arrayList != null) {
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void followFoucs(int i, final int i2, String str) {
        ApiImplService.Companion.getApiImplService().circleFllow(str, DBUtil.getMemberId(), i).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.item.PostCircleItem$followFoucs$1
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
                circleRecommentAdapter = PostCircleItem.this.adapter;
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
}
