package com.one.tomato.mvp.p080ui.chess.item;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.ChessHomePostFeatruesBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.ChessTypeSubBean;
import com.one.tomato.mvp.p080ui.chess.view.ChessPostHomeActivity;
import com.one.tomato.mvp.p080ui.p082up.view.UpHomeActivity;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.mvp.p080ui.post.view.PostHotListActivity;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageActivity;
import com.one.tomato.mvp.p080ui.sign.view.UserSignActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessHomePostItem.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem */
/* loaded from: classes3.dex */
public final class ChessHomePostItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<ChessTypeSubBean> postAdapter;

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
    public ChessHomePostItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.chess_home_post_item, this);
        initAdapter();
    }

    private final void initAdapter() {
        final Context context = getContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_post);
        this.postAdapter = new BaseRecyclerViewAdapter<ChessTypeSubBean>(this, context, R.layout.item_chess_home_post_item, recyclerView) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ChessTypeSubBean chessTypeSubBean) {
                super.convert(baseViewHolder, (BaseViewHolder) chessTypeSubBean);
                Integer num = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_type) : null;
                if (chessTypeSubBean != null) {
                    num = Integer.valueOf(chessTypeSubBean.getPostType());
                }
                if (num != null && num.intValue() == 1) {
                    if (imageView2 != null) {
                        imageView2.setImageResource(R.drawable.circle_image);
                    }
                    PostList postList = new PostList();
                    postList.setSecImageUrl(chessTypeSubBean.getSecImageUrl());
                    postList.setSize(chessTypeSubBean.getSize());
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, PostUtils.INSTANCE.createImageBeanList(postList).get(0));
                } else if (num != null && num.intValue() == 2) {
                    if (imageView2 != null) {
                        imageView2.setImageResource(R.drawable.circle_video);
                    }
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, new ImageBean(chessTypeSubBean.getSecVideoCover()));
                } else if (num == null || num.intValue() != 3 || imageView2 == null) {
                } else {
                    imageView2.setImageResource(R.drawable.circle_text);
                }
            }
        };
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_12);
        builder.setVerticalSpan(R.dimen.dimen_12);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_post);
        if (recyclerView2 != null) {
            recyclerView2.addItemDecoration(build);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_post);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recycler_post);
        if (recyclerView4 != null) {
            recyclerView4.setAdapter(this.postAdapter);
        }
        final Context context2 = getContext();
        final RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        BaseRecyclerViewAdapter<ChessHomePostFeatruesBean> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<ChessHomePostFeatruesBean>(this, context2, R.layout.item_chess_home_post_featrues_item, recyclerView5) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$featuresAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ChessHomePostFeatruesBean chessHomePostFeatruesBean) {
                String str;
                super.convert(baseViewHolder, (BaseViewHolder) chessHomePostFeatruesBean);
                TextView textView = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                if (baseViewHolder != null) {
                    textView = (TextView) baseViewHolder.getView(R.id.text);
                }
                if (imageView != null) {
                    imageView.setImageResource(chessHomePostFeatruesBean != null ? chessHomePostFeatruesBean.getResId() : 0);
                }
                if (textView != null) {
                    if (chessHomePostFeatruesBean == null || (str = chessHomePostFeatruesBean.getText()) == null) {
                        str = "";
                    }
                    textView.setText(str);
                }
            }
        };
        RecyclerView recyclerView6 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView6 != null) {
            recyclerView6.addItemDecoration(build);
        }
        RecyclerView recyclerView7 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView7 != null) {
            recyclerView7.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        RecyclerView recyclerView8 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView8 != null) {
            recyclerView8.setAdapter(baseRecyclerViewAdapter);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ChessHomePostFeatruesBean(R.drawable.chess_home_post_hot_list, AppUtil.getString(R.string.post_home_hot_list), 1));
        arrayList.add(new ChessHomePostFeatruesBean(R.drawable.chess_home_post_signed, AppUtil.getString(R.string.post_home_qiandao), 2));
        baseRecyclerViewAdapter.setNewData(arrayList);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
        baseRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                Object obj = adapter.getData().get(i);
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.ChessHomePostFeatruesBean");
                }
                int itemId = ((ChessHomePostFeatruesBean) obj).getItemId();
                if (itemId == 1) {
                    Intent intent = new Intent(ChessHomePostItem.this.getContext(), PostHotListActivity.class);
                    Context context3 = ChessHomePostItem.this.getContext();
                    if (context3 == null) {
                        return;
                    }
                    context3.startActivity(intent);
                } else if (itemId != 2) {
                } else {
                    Intent intent2 = new Intent(ChessHomePostItem.this.getContext(), UserSignActivity.class);
                    Context context4 = ChessHomePostItem.this.getContext();
                    if (context4 == null) {
                        return;
                    }
                    context4.startActivity(intent2);
                }
            }
        });
        BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter2 = this.postAdapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    BaseRecyclerViewAdapter baseRecyclerViewAdapter3;
                    List<T> data;
                    baseRecyclerViewAdapter3 = ChessHomePostItem.this.postAdapter;
                    ChessTypeSubBean chessTypeSubBean = (baseRecyclerViewAdapter3 == null || (data = baseRecyclerViewAdapter3.getData()) == 0) ? null : (ChessTypeSubBean) data.get(i);
                    NewPostDetailViewPagerActivity.Companion.startActivity(ChessHomePostItem.this.getContext(), chessTypeSubBean != null ? chessTypeSubBean.getId() : 0, false, false, false);
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_top);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent(ChessHomePostItem.this.getContext(), PostHotMessageActivity.class);
                    Context context3 = ChessHomePostItem.this.getContext();
                    if (context3 != null) {
                        context3.startActivity(intent);
                    }
                }
            });
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_goin);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent(ChessHomePostItem.this.getContext(), ChessPostHomeActivity.class);
                    Context context3 = ChessHomePostItem.this.getContext();
                    if (context3 != null) {
                        context3.startActivity(intent);
                    }
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_up);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePostItem$initAdapter$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpHomeActivity.Companion.startAct(ChessHomePostItem.this.getContext(), 1);
                }
            });
        }
    }

    public final void addData(ChessTypeBean chessTypeBean) {
        Intrinsics.checkParameterIsNotNull(chessTypeBean, "chessTypeBean");
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_up);
        boolean z = true;
        if (textView != null) {
            String string = AppUtil.getString(R.string.chess_post_become_up);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.chess_post_become_up)");
            Object[] objArr = {Integer.valueOf(chessTypeBean.getCountOfUpHost())};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        ArrayList<ChessTypeSubBean> data = chessTypeBean.getData();
        if (data != null && !data.isEmpty()) {
            z = false;
        }
        if (!z) {
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter = this.postAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.setNewData(data);
            }
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter2 = this.postAdapter;
            if (baseRecyclerViewAdapter2 == null) {
                return;
            }
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        }
    }
}
