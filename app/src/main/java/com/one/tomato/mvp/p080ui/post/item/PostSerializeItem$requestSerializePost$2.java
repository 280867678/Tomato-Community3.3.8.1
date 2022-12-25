package com.one.tomato.mvp.p080ui.post.item;

import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.SerializePostListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.p080ui.post.adapter.PostSerializeItemAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.AppUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSerializeItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostSerializeItem$requestSerializePost$2 */
/* loaded from: classes3.dex */
public final class PostSerializeItem$requestSerializePost$2 extends ApiDisposableObserver<SerializePostListBean> {
    final /* synthetic */ int $postId;
    final /* synthetic */ PostSerializeItem this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PostSerializeItem$requestSerializePost$2(PostSerializeItem postSerializeItem, int i) {
        this.this$0 = postSerializeItem;
        this.$postId = i;
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResult(SerializePostListBean serializePostListBean) {
        PostSerializeItemAdapter postSerializeItemAdapter;
        PostSerializeItemAdapter postSerializeItemAdapter2;
        PostSerializeItemAdapter postSerializeItemAdapter3;
        List<PostList> data;
        ArrayList<PostList> data2;
        String str;
        ArrayList<PostList> data3 = serializePostListBean != null ? serializePostListBean.getData() : null;
        if (!(data3 == null || data3.isEmpty())) {
            this.this$0.serializePostListBean = serializePostListBean;
            RelativeLayout relativeLayout = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_background);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.text_serial_title);
            if (textView != null) {
                if (serializePostListBean == null || (str = serializePostListBean.getTitle()) == null) {
                    str = "";
                }
                textView.setText(str);
            }
            TextView textView2 = (TextView) this.this$0._$_findCachedViewById(R$id.text_post_num);
            if (textView2 != null) {
                String string = AppUtil.getString(R.string.post_serialize_item_post_num);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…_serialize_item_post_num)");
                Object[] objArr = new Object[1];
                objArr[0] = String.valueOf((serializePostListBean == null || (data2 = serializePostListBean.getData()) == null) ? null : Integer.valueOf(data2.size()));
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                textView2.setText(format);
            }
            postSerializeItemAdapter = this.this$0.adapter;
            if (postSerializeItemAdapter != null && (data = postSerializeItemAdapter.getData()) != null) {
                data.clear();
            }
            postSerializeItemAdapter2 = this.this$0.adapter;
            if (postSerializeItemAdapter2 != null) {
                if (serializePostListBean == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                postSerializeItemAdapter2.addData((Collection) serializePostListBean.getData());
            }
            postSerializeItemAdapter3 = this.this$0.adapter;
            if (postSerializeItemAdapter3 != null) {
                postSerializeItemAdapter3.setEnableLoadMore(false);
            }
            BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) this.this$0._$_findCachedViewById(R$id.serialize_recycler_view);
            if (betterHorScrollRecyclerView == null) {
                return;
            }
            betterHorScrollRecyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem$requestSerializePost$2$onResult$1
                @Override // java.lang.Runnable
                public final void run() {
                    PostSerializeItem$requestSerializePost$2 postSerializeItem$requestSerializePost$2 = PostSerializeItem$requestSerializePost$2.this;
                    postSerializeItem$requestSerializePost$2.this$0.scrollPostSelect(postSerializeItem$requestSerializePost$2.$postId);
                }
            }, 50L);
            return;
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_background);
        if (relativeLayout2 == null) {
            return;
        }
        relativeLayout2.setVisibility(8);
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResultError(ResponseThrowable responseThrowable) {
        RelativeLayout relativeLayout = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_background);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
    }
}
