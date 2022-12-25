package com.one.tomato.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.p002v4.app.DialogFragment;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.SerializePostListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.adapter.MinePostPublishAdapter;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSerializeDialog.kt */
/* loaded from: classes3.dex */
public final class PostSerializeDialog extends DialogFragment {
    private HashMap _$_findViewCache;
    private MinePostPublishAdapter adapter;
    private ImageView image_cancel;
    private Function1<? super PostList, Unit> onClickCallBack;
    private int postId;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private int serializeId;
    private SerializePostListBean serializePostListBean;
    private TextView text_title;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Dialog dialog = getDialog();
        Intrinsics.checkExpressionValueIsNotNull(dialog, "dialog");
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(80);
        }
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        int height = ((int) DisplayMetricsUtils.getHeight()) - ((int) DisplayMetricsUtils.get16To9Height());
        if (window != null) {
            window.setLayout(-1, height);
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.post_serialize_dialog, (ViewGroup) null);
        Intrinsics.checkExpressionValueIsNotNull(inflate, "inflate");
        init(inflate);
        return inflate;
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.PostRewardDialog);
    }

    private final void init(View view) {
        String str;
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        this.text_title = (TextView) view.findViewById(R.id.text_title);
        this.image_cancel = (ImageView) view.findViewById(R.id.image_cancel);
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        boolean z = true;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableRefresh(true);
        }
        SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
        if (smartRefreshLayout2 != null) {
            smartRefreshLayout2.mo6487setEnableLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout3 = this.refreshLayout;
        if (smartRefreshLayout3 != null) {
            smartRefreshLayout3.mo6486setEnableAutoLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout4 = this.refreshLayout;
        if (smartRefreshLayout4 != null) {
            smartRefreshLayout4.setOnRefreshLoadMoreListener(new PostSerializeDialog$init$1(this));
        }
        this.adapter = new MinePostPublishAdapter(getContext(), this.recyclerView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        }
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        if (minePostPublishAdapter != null) {
            minePostPublishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.dialog.PostSerializeDialog$init$2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view2, int i) {
                    Function1 function1;
                    PostList postList = (PostList) (baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null);
                    if (postList != null) {
                        PostSerializeDialog.this.scrollPostSelect(postList.getId());
                        function1 = PostSerializeDialog.this.onClickCallBack;
                        if (function1 == null) {
                            return;
                        }
                        Unit unit = (Unit) function1.mo6794invoke(postList);
                    }
                }
            });
        }
        ImageView imageView = this.image_cancel;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostSerializeDialog$init$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    PostSerializeDialog.this.dismiss();
                }
            });
        }
        SerializePostListBean serializePostListBean = this.serializePostListBean;
        ArrayList<PostList> arrayList = null;
        ArrayList<PostList> data = serializePostListBean != null ? serializePostListBean.getData() : null;
        if (data != null && !data.isEmpty()) {
            z = false;
        }
        if (!z) {
            TextView textView = this.text_title;
            if (textView != null) {
                SerializePostListBean serializePostListBean2 = this.serializePostListBean;
                if (serializePostListBean2 == null || (str = serializePostListBean2.getTitle()) == null) {
                    str = "";
                }
                textView.setText(str);
            }
            MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
            if (minePostPublishAdapter2 != null) {
                SerializePostListBean serializePostListBean3 = this.serializePostListBean;
                if (serializePostListBean3 != null) {
                    arrayList = serializePostListBean3.getData();
                }
                minePostPublishAdapter2.setNewData(arrayList);
            }
            MinePostPublishAdapter minePostPublishAdapter3 = this.adapter;
            if (minePostPublishAdapter3 != null) {
                minePostPublishAdapter3.setEnableLoadMore(false);
            }
            scrollPostSelect(this.postId);
            return;
        }
        SmartRefreshLayout smartRefreshLayout5 = this.refreshLayout;
        if (smartRefreshLayout5 == null) {
            return;
        }
        smartRefreshLayout5.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refresh() {
        requestSerializePost(this.serializeId, this.postId);
    }

    public final void setSerializeId(int i, int i2, SerializePostListBean serializePostListBean) {
        if (this.serializeId == i) {
            scrollPostSelect(i2);
            return;
        }
        this.serializeId = i;
        this.postId = i2;
        this.serializePostListBean = serializePostListBean;
    }

    public final void addOnClickCallBack(Function1<? super PostList, Unit> function1) {
        this.onClickCallBack = function1;
    }

    private final void requestSerializePost(int i, final int i2) {
        ApiImplService.Companion.getApiImplService().requestSerializePostList(i).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<SerializePostListBean>() { // from class: com.one.tomato.dialog.PostSerializeDialog$requestSerializePost$1
            /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:
                r5 = r4.this$0.adapter;
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResult(SerializePostListBean serializePostListBean) {
                SmartRefreshLayout smartRefreshLayout;
                TextView textView;
                MinePostPublishAdapter minePostPublishAdapter;
                MinePostPublishAdapter minePostPublishAdapter2;
                String str;
                MinePostPublishAdapter minePostPublishAdapter3;
                List<PostList> data;
                MinePostPublishAdapter minePostPublishAdapter4;
                SmartRefreshLayout smartRefreshLayout2;
                smartRefreshLayout = PostSerializeDialog.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                ArrayList<PostList> data2 = serializePostListBean != null ? serializePostListBean.getData() : null;
                if (data2 == null || data2.isEmpty()) {
                    minePostPublishAdapter3 = PostSerializeDialog.this.adapter;
                    if (minePostPublishAdapter3 == null || (data = minePostPublishAdapter3.getData()) == null || data.size() != 0 || minePostPublishAdapter4 == null) {
                        return;
                    }
                    smartRefreshLayout2 = PostSerializeDialog.this.refreshLayout;
                    minePostPublishAdapter4.setEmptyViewState(2, smartRefreshLayout2);
                    return;
                }
                textView = PostSerializeDialog.this.text_title;
                if (textView != null) {
                    if (serializePostListBean == null || (str = serializePostListBean.getTitle()) == null) {
                        str = "";
                    }
                    textView.setText(str);
                }
                minePostPublishAdapter = PostSerializeDialog.this.adapter;
                if (minePostPublishAdapter != null) {
                    if (serializePostListBean == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    minePostPublishAdapter.setNewData(serializePostListBean.getData());
                }
                minePostPublishAdapter2 = PostSerializeDialog.this.adapter;
                if (minePostPublishAdapter2 != null) {
                    minePostPublishAdapter2.setEnableLoadMore(false);
                }
                PostSerializeDialog.this.scrollPostSelect(i2);
            }

            /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
                r3 = r2.this$0.adapter;
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResultError(ResponseThrowable responseThrowable) {
                SmartRefreshLayout smartRefreshLayout;
                MinePostPublishAdapter minePostPublishAdapter;
                List<PostList> data;
                MinePostPublishAdapter minePostPublishAdapter2;
                SmartRefreshLayout smartRefreshLayout2;
                smartRefreshLayout = PostSerializeDialog.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                minePostPublishAdapter = PostSerializeDialog.this.adapter;
                if (minePostPublishAdapter == null || (data = minePostPublishAdapter.getData()) == null || data.size() != 0 || minePostPublishAdapter2 == null) {
                    return;
                }
                smartRefreshLayout2 = PostSerializeDialog.this.refreshLayout;
                minePostPublishAdapter2.setEmptyViewState(1, smartRefreshLayout2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scrollPostSelect(int i) {
        RecyclerView recyclerView;
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        PostList postList = null;
        ArrayList arrayList = (ArrayList) (minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null);
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            PostList it3 = (PostList) it2.next();
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            if (it3.getId() == i) {
                it3.setSerializeSelect(true);
                postList = it3;
            } else {
                it3.setSerializeSelect(false);
            }
        }
        MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
        if (minePostPublishAdapter2 != null) {
            minePostPublishAdapter2.notifyDataSetChanged();
        }
        if (postList == null || (recyclerView = this.recyclerView) == null) {
            return;
        }
        recyclerView.smoothScrollToPosition(arrayList.indexOf(postList));
    }
}
