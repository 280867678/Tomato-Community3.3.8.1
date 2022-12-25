package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.HotMessageBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.post.adapter.PostHotHeadAdapter;
import com.one.tomato.mvp.p080ui.post.view.ReviewPostActivity;
import com.one.tomato.mvp.p080ui.sign.view.UserSignActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotHeadView */
/* loaded from: classes3.dex */
public final class PostHotHeadView extends RelativeLayout {
    private HashMap _$_findViewCache;

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

    public PostHotHeadView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.post_hot_head_view, this);
        PostHotHeadAdapter postHotHeadAdapter = new PostHotHeadAdapter();
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setAdapter(postHotHeadAdapter);
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new GridLayoutManager(context, 4));
        }
        ArrayList arrayList = new ArrayList();
        String string = AppUtil.getString(R.string.post_home_review);
        Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_home_review)");
        arrayList.add(new HotMessageBean(R.drawable.home_head_review, R.drawable.shape_solid_corner4_fff1e5, string, 10));
        String string2 = AppUtil.getString(R.string.post_home_hot_list);
        Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.post_home_hot_list)");
        arrayList.add(new HotMessageBean(R.drawable.post_hot_list, R.drawable.shape_solid_corner4_e1fff4, string2, 11));
        String string3 = AppUtil.getString(R.string.post_home_hot);
        Intrinsics.checkExpressionValueIsNotNull(string3, "AppUtil.getString(R.string.post_home_hot)");
        arrayList.add(new HotMessageBean(R.drawable.home_head_hot, R.drawable.shape_solid_corner4_ffe5ec, string3, 12));
        String string4 = AppUtil.getString(R.string.post_home_qiandao);
        Intrinsics.checkExpressionValueIsNotNull(string4, "AppUtil.getString(R.string.post_home_qiandao)");
        arrayList.add(new HotMessageBean(R.drawable.home_head_qiandao, R.drawable.shape_solid_corner4_f2e2ff, string4, 13));
        postHotHeadAdapter.addData((Collection) arrayList);
        postHotHeadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotHeadView.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                Object obj = adapter.getData().get(i);
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.HotMessageBean");
                }
                switch (((HotMessageBean) obj).getId()) {
                    case 10:
                        PreferencesUtil.getInstance().putString("review_new", "1");
                        adapter.notifyDataSetChanged();
                        SystemParam systemParam = DBUtil.getSystemParam();
                        UserInfo userInfo = DBUtil.getUserInfo();
                        if ((userInfo != null && userInfo.isOfficial()) || (systemParam != null && systemParam.getReViewFlag() == 1)) {
                            ReviewPostActivity.Companion companion = ReviewPostActivity.Companion;
                            Context context2 = PostHotHeadView.this.getContext();
                            Intrinsics.checkExpressionValueIsNotNull(context2, "getContext()");
                            companion.startAct(context2);
                            return;
                        } else if ((systemParam != null && systemParam.getReViewFlag() == 0) || (systemParam != null && systemParam.getReViewFlag() == 2 && (userInfo == null || userInfo.getReviewType() != 1))) {
                            ToastUtil.showCenterToast(AppUtil.getString(R.string.review_power_close));
                            return;
                        } else {
                            ReviewPostActivity.Companion companion2 = ReviewPostActivity.Companion;
                            Context context3 = PostHotHeadView.this.getContext();
                            Intrinsics.checkExpressionValueIsNotNull(context3, "getContext()");
                            companion2.startAct(context3);
                            return;
                        }
                    case 11:
                        Intent intent = new Intent(context, PostHotListActivity.class);
                        Context context4 = context;
                        if (context4 == null) {
                            return;
                        }
                        context4.startActivity(intent);
                        return;
                    case 12:
                        Intent intent2 = new Intent(context, PostHotMessageActivity.class);
                        Context context5 = context;
                        if (context5 == null) {
                            return;
                        }
                        context5.startActivity(intent2);
                        return;
                    case 13:
                        Intent intent3 = new Intent(context, UserSignActivity.class);
                        Context context6 = context;
                        if (context6 == null) {
                            return;
                        }
                        context6.startActivity(intent3);
                        return;
                    default:
                        return;
                }
            }
        });
    }
}
