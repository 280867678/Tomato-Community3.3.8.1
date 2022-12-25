package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p002v4.app.FragmentManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.dialog.PostSerializeDialog;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.SerializePostListBean;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.post.adapter.PostSerializeItemAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSerializeItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostSerializeItem */
/* loaded from: classes3.dex */
public final class PostSerializeItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private PostSerializeItemAdapter adapter;
    private Functions<? extends FragmentManager> onClickAllPostCallBack;
    private Function1<? super PostList, Unit> onClickCallBack;
    private int serializeId;
    private SerializePostListBean serializePostListBean;

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

    public PostSerializeItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.post_serialize_item, this);
        this.adapter = new PostSerializeItemAdapter(context, (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.serialize_recycler_view));
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.serialize_recycler_view);
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setAdapter(this.adapter);
        }
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.serialize_recycler_view);
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.setLayoutManager(new LinearLayoutManager(context, 0, false));
        }
        PostSerializeItemAdapter postSerializeItemAdapter = this.adapter;
        if (postSerializeItemAdapter != null) {
            postSerializeItemAdapter.setEnableLoadMore(false);
        }
        PostSerializeItemAdapter postSerializeItemAdapter2 = this.adapter;
        if (postSerializeItemAdapter2 != null) {
            postSerializeItemAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem.1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    PostList postList = (PostList) (baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null);
                    if (postList != null) {
                        Function1 function1 = PostSerializeItem.this.onClickCallBack;
                        if (function1 != null) {
                            Unit unit = (Unit) function1.mo6794invoke(postList);
                        }
                        PostSerializeItem.this.scrollPostSelect(postList.getId());
                    }
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_num);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostSerializeItem.this.showSerializeDialog();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_serial_title);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostSerializeItem.this.showSerializeDialog();
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_serialize);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostSerializeItem.this.showSerializeDialog();
                }
            });
        }
    }

    public final void setSerializeId(PostList postList) {
        if ((postList != null ? postList.getSerialGroupId() : 0) == 0) {
            return;
        }
        int i = this.serializeId;
        if (postList != null && i == postList.getSerialGroupId()) {
            scrollPostSelect(postList.getId());
        } else if (postList != null) {
            this.serializeId = postList.getSerialGroupId();
            requestSerializePost(postList);
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    public final void addOnClickCallBack(Function1<? super PostList, Unit> function1) {
        this.onClickCallBack = function1;
    }

    public final void addOnClickAllPostCallBack(Functions<? extends FragmentManager> functions) {
        this.onClickAllPostCallBack = functions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSerializeDialog() {
        PostSerializeDialog postSerializeDialog = new PostSerializeDialog();
        PostSerializeItemAdapter postSerializeItemAdapter = this.adapter;
        FragmentManager fragmentManager = null;
        List<PostList> data = postSerializeItemAdapter != null ? postSerializeItemAdapter.getData() : null;
        int i = 0;
        if (!(data == null || data.isEmpty())) {
            for (PostList it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (it2.isSelectMinePostDelete()) {
                    i = it2.getId();
                }
            }
        }
        postSerializeDialog.setSerializeId(this.serializeId, i, this.serializePostListBean);
        postSerializeDialog.addOnClickCallBack(new PostSerializeItem$showSerializeDialog$1(this));
        Functions<? extends FragmentManager> functions = this.onClickAllPostCallBack;
        if (functions != null) {
            fragmentManager = functions.mo6822invoke();
        }
        postSerializeDialog.show(fragmentManager, "postserialize");
    }

    private final void requestSerializePost(final PostList postList) {
        List<PostList> data;
        ArrayList<PostList> data2;
        String str;
        ArrayList<PostList> serialGroupArticles = postList != null ? postList.getSerialGroupArticles() : null;
        if (serialGroupArticles == null || serialGroupArticles.isEmpty()) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_background);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.setVisibility(8);
        } else if (postList != null) {
            this.serializePostListBean = new SerializePostListBean(postList.getSerialGroupTitle(), postList.getSerialGroupArticles());
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_background);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_serial_title);
            if (textView != null) {
                SerializePostListBean serializePostListBean = this.serializePostListBean;
                if (serializePostListBean == null || (str = serializePostListBean.getTitle()) == null) {
                    str = "";
                }
                textView.setText(str);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_post_num);
            if (textView2 != null) {
                String string = AppUtil.getString(R.string.post_serialize_item_post_num);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…_serialize_item_post_num)");
                Object[] objArr = new Object[1];
                SerializePostListBean serializePostListBean2 = this.serializePostListBean;
                objArr[0] = String.valueOf((serializePostListBean2 == null || (data2 = serializePostListBean2.getData()) == null) ? null : Integer.valueOf(data2.size()));
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                textView2.setText(format);
            }
            PostSerializeItemAdapter postSerializeItemAdapter = this.adapter;
            if (postSerializeItemAdapter != null && (data = postSerializeItemAdapter.getData()) != null) {
                data.clear();
            }
            PostSerializeItemAdapter postSerializeItemAdapter2 = this.adapter;
            if (postSerializeItemAdapter2 != null) {
                SerializePostListBean serializePostListBean3 = this.serializePostListBean;
                if (serializePostListBean3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                postSerializeItemAdapter2.addData((Collection) serializePostListBean3.getData());
            }
            PostSerializeItemAdapter postSerializeItemAdapter3 = this.adapter;
            if (postSerializeItemAdapter3 != null) {
                postSerializeItemAdapter3.setEnableLoadMore(false);
            }
            BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.serialize_recycler_view);
            if (betterHorScrollRecyclerView != null) {
                betterHorScrollRecyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.item.PostSerializeItem$requestSerializePost$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PostSerializeItem.this.scrollPostSelect(postList.getId());
                    }
                }, 50L);
            }
            requestSerializePost(postList.getSerialGroupId(), postList.getId());
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scrollPostSelect(int i) {
        BetterHorScrollRecyclerView betterHorScrollRecyclerView;
        PostSerializeItemAdapter postSerializeItemAdapter = this.adapter;
        PostList postList = null;
        ArrayList arrayList = (ArrayList) (postSerializeItemAdapter != null ? postSerializeItemAdapter.getData() : null);
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            PostList it3 = (PostList) it2.next();
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            if (it3.getId() == i) {
                it3.setSelectMinePostDelete(true);
                postList = it3;
            } else {
                it3.setSelectMinePostDelete(false);
            }
        }
        PostSerializeItemAdapter postSerializeItemAdapter2 = this.adapter;
        if (postSerializeItemAdapter2 != null) {
            postSerializeItemAdapter2.notifyDataSetChanged();
        }
        if (postList == null || (betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.serialize_recycler_view)) == null) {
            return;
        }
        betterHorScrollRecyclerView.smoothScrollToPosition(arrayList.indexOf(postList));
    }

    private final void requestSerializePost(int i, int i2) {
        ApiImplService.Companion.getApiImplService().requestSerializePostList(i).compose(RxUtils.schedulersTransformer()).subscribe(new PostSerializeItem$requestSerializePost$2(this, i2));
    }
}
