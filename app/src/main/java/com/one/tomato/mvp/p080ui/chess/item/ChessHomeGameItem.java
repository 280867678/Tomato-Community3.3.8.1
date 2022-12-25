package com.one.tomato.mvp.p080ui.chess.item;

import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.ChessTypeSubBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.view.ChessGameActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessHomeGameItem.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.item.ChessHomeGameItem */
/* loaded from: classes3.dex */
public final class ChessHomeGameItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<ChessTypeSubBean> adapter;

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
    public ChessHomeGameItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.chess_home_game_item, this);
        initAdapter();
    }

    private final void initAdapter() {
        final Context context = getContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        this.adapter = new BaseRecyclerViewAdapter<ChessTypeSubBean>(this, context, R.layout.item_chess_home_game_item, recyclerView) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeGameItem$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
            /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void convert(BaseViewHolder baseViewHolder, ChessTypeSubBean chessTypeSubBean) {
                String str;
                super.convert(baseViewHolder, (BaseViewHolder) chessTypeSubBean);
                String str2 = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_type) : null;
                TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
                if (imageView != null) {
                    imageView.setImageBitmap(null);
                }
                ImageLoaderUtil.loadRecyclerThumbImageNoCrop(this.mContext, imageView, new ImageBean(chessTypeSubBean != null ? chessTypeSubBean.getAdArticleAvatarSec() : null));
                if (!Intrinsics.areEqual(chessTypeSubBean != null ? chessTypeSubBean.getAdType() : null, "3")) {
                    if (chessTypeSubBean != null) {
                        str2 = chessTypeSubBean.getAdType();
                    }
                    if (!Intrinsics.areEqual(str2, "8")) {
                        if (imageView2 != null) {
                            imageView2.setVisibility(8);
                        }
                        if (textView != null) {
                            return;
                        }
                        if (chessTypeSubBean == null || (str = chessTypeSubBean.getAdName()) == null) {
                            str = "";
                        }
                        textView.setText(str);
                        return;
                    }
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (textView != null) {
                }
            }
        };
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_12);
        builder.setVerticalSpan(R.dimen.dimen_12);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView3 != null) {
            recyclerView3.addItemDecoration(build);
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView4 != null) {
            recyclerView4.setAdapter(this.adapter);
        }
        BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter = this.adapter;
        if (baseRecyclerViewAdapter != null) {
            baseRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeGameItem$initAdapter$2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    List<Object> data;
                    ArrayList<SubGamesBean> chessH5Game = GameUtils.INSTANCE.getChessH5Game(false);
                    Object obj = (baseQuickAdapter == null || (data = baseQuickAdapter.getData()) == null) ? null : data.get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.ChessTypeSubBean");
                    }
                    ChessTypeSubBean chessTypeSubBean = (ChessTypeSubBean) obj;
                    if (Intrinsics.areEqual(chessTypeSubBean.getAdType(), "7")) {
                        boolean z = true;
                        if (!(chessH5Game == null || chessH5Game.isEmpty())) {
                            Iterator<SubGamesBean> it2 = chessH5Game.iterator();
                            while (it2.hasNext()) {
                                SubGamesBean sub = it2.next();
                                Intrinsics.checkExpressionValueIsNotNull(sub, "sub");
                                sub.setSelector(chessTypeSubBean.getId() == sub.getGameId());
                            }
                        }
                        if (chessH5Game != null && !chessH5Game.isEmpty()) {
                            z = false;
                        }
                        if (z) {
                            return;
                        }
                        ChessGameActivity.Companion companion = ChessGameActivity.Companion;
                        Context context2 = ChessHomeGameItem.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
                        companion.startAct(context2, chessH5Game);
                    } else if (Intrinsics.areEqual(chessTypeSubBean.getAdType(), "9")) {
                        ChessHomeGameItem.this.requestBGH5Login(GameUtils.INSTANCE.encapsulationSubGame(chessTypeSubBean));
                    } else {
                        GameUtils gameUtils = GameUtils.INSTANCE;
                        Context context3 = ChessHomeGameItem.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context3, "context");
                        gameUtils.clickGame(context3, GameUtils.INSTANCE.encapsulationSubGame(chessTypeSubBean));
                    }
                }
            });
        }
    }

    public final void addData(ChessTypeBean chessTypeBean) {
        Intrinsics.checkParameterIsNotNull(chessTypeBean, "chessTypeBean");
        ArrayList<ChessTypeSubBean> data = chessTypeBean.getData();
        if (!(data == null || data.isEmpty())) {
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter = this.adapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.setNewData(data);
            }
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter2 = this.adapter;
            if (baseRecyclerViewAdapter2 == null) {
                return;
            }
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestBGH5Login(SubGamesBean subGamesBean) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("adId", Integer.valueOf(subGamesBean.getGameId()));
        linkedHashMap.put("gameBrandId", subGamesBean.getAdBrandId());
        requestBGH5Login(linkedHashMap, subGamesBean);
    }

    public final void requestBGH5Login(Map<String, Object> mutableMap, final SubGamesBean subGamesBean) {
        Intrinsics.checkParameterIsNotNull(mutableMap, "mutableMap");
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        ApiImplService.Companion.getApiImplService().requestLiveGameBGLogin(mutableMap).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeGameItem$requestBGH5Login$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                if (ChessHomeGameItem.this.getContext() instanceof MvpBaseActivity) {
                    Context context = ChessHomeGameItem.this.getContext();
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<GameBGLoginBean>() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeGameItem$requestBGH5Login$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameBGLoginBean gameBGLoginBean) {
                String str;
                if (ChessHomeGameItem.this.getContext() instanceof MvpBaseActivity) {
                    Context context = ChessHomeGameItem.this.getContext();
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
                GameUtils gameUtils = GameUtils.INSTANCE;
                Context context2 = ChessHomeGameItem.this.getContext();
                if (context2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                SubGamesBean subGamesBean2 = subGamesBean;
                if (gameBGLoginBean == null || (str = gameBGLoginBean.getUrl()) == null) {
                    str = "";
                }
                gameUtils.clickGame(context2, subGamesBean2, str);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                if (ChessHomeGameItem.this.getContext() instanceof MvpBaseActivity) {
                    Context context = ChessHomeGameItem.this.getContext();
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
            }
        });
    }
}
