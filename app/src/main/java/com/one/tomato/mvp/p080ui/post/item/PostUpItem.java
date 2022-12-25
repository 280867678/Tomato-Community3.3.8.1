package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.utils.CircleAllUtils;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;

/* compiled from: PostUpItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostUpItem */
/* loaded from: classes3.dex */
public final class PostUpItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private BaseQuickAdapter<UpRecommentBean, BaseViewHolder> adapter;

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

    public PostUpItem(final Context context) {
        super(context);
        if (context != null) {
            LayoutInflater.from(context).inflate(R.layout.item_up_recomment_recycler, this);
            BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.recycler_view_up);
            if (betterHorScrollRecyclerView != null) {
                betterHorScrollRecyclerView.setLayoutManager(new LinearLayoutManager(context, 0, false));
            }
            this.adapter = new BaseQuickAdapter<UpRecommentBean, BaseViewHolder>(context, R.layout.item_recomment_up, this, context) { // from class: com.one.tomato.mvp.ui.post.item.PostUpItem$$special$$inlined$let$lambda$1
                final /* synthetic */ Context $it;
                final /* synthetic */ PostUpItem this$0;

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder baseViewHolder, final UpRecommentBean upRecommentBean) {
                    String signature;
                    String str;
                    String str2 = null;
                    RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_head) : null;
                    TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
                    TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_description) : null;
                    TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs) : null;
                    Context context2 = this.$it;
                    if (upRecommentBean != null) {
                        str2 = upRecommentBean.getAvatar();
                    }
                    ImageLoaderUtil.loadHeadImage(context2, roundedImageView, new ImageBean(str2));
                    String str3 = "";
                    if (textView != null) {
                        if (upRecommentBean == null || (str = upRecommentBean.getName()) == null) {
                            str = str3;
                        }
                        textView.setText(str);
                    }
                    if (textView2 != null) {
                        if (upRecommentBean != null && (signature = upRecommentBean.getSignature()) != null) {
                            str3 = signature;
                        }
                        textView2.setText(str3);
                    }
                    if (textView3 != null) {
                        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostUpItem$$special$$inlined$let$lambda$1.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                PostUpItem$$special$$inlined$let$lambda$1.this.this$0.foucs(upRecommentBean, 1);
                            }
                        });
                    }
                }
            };
            BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.recycler_view_up);
            if (betterHorScrollRecyclerView2 == null) {
                return;
            }
            betterHorScrollRecyclerView2.setAdapter(this.adapter);
        }
    }

    public final BaseQuickAdapter<UpRecommentBean, BaseViewHolder> getAdapter() {
        return this.adapter;
    }

    public final void setAdapter(BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter) {
        this.adapter = baseQuickAdapter;
    }

    public final void setData(ArrayList<UpRecommentBean> arrayList, boolean z) {
        List<UpRecommentBean> data;
        if (!(arrayList == null || arrayList.isEmpty())) {
            BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter = this.adapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.setNewData(arrayList);
            }
            BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter2 = this.adapter;
            if (baseQuickAdapter2 != null) {
                baseQuickAdapter2.setEnableLoadMore(false);
            }
        }
        if (z) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_ti);
            if (textView != null) {
                textView.setVisibility(0);
            }
        } else {
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_ti);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        }
        BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter3 = this.adapter;
        if (baseQuickAdapter3 != null && (data = baseQuickAdapter3.getData()) != null) {
            if (data == null || data.isEmpty()) {
                TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_inte_up);
                if (textView3 == null) {
                    return;
                }
                textView3.setVisibility(8);
                return;
            }
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_inte_up);
        if (textView4 != null) {
            textView4.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void foucs(final UpRecommentBean upRecommentBean, final int i) {
        if (upRecommentBean != null) {
            Observable<R> compose = ApiImplService.Companion.getApiImplService().postFoucs(DBUtil.getMemberId(), upRecommentBean.getUpId(), i).compose(RxUtils.schedulersTransformer());
            Context context = getContext();
            if (context == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.trello.rxlifecycle2.components.support.RxAppCompatActivity");
            }
            compose.compose(RxUtils.bindToLifecycler((RxAppCompatActivity) context)).subscribe(new ApiDisposableObserver<Object>(this, i) { // from class: com.one.tomato.mvp.ui.post.item.PostUpItem$foucs$$inlined$also$lambda$1
                final /* synthetic */ PostUpItem this$0;

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    UserInfoManager.setUserFollowCount(true);
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.common_focus_success));
                    this.this$0.removeItemOrAddItem(UpRecommentBean.this);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("推荐up里关注用户失败=----");
                    sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                    LogUtil.m3787d("yan", sb.toString());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeItemOrAddItem(UpRecommentBean upRecommentBean) {
        BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter;
        List<UpRecommentBean> data;
        BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter2 = this.adapter;
        Integer valueOf = (baseQuickAdapter2 == null || (data = baseQuickAdapter2.getData()) == null) ? null : Integer.valueOf(data.indexOf(upRecommentBean));
        if (valueOf != null) {
            int intValue = valueOf.intValue();
            BaseQuickAdapter<UpRecommentBean, BaseViewHolder> baseQuickAdapter3 = this.adapter;
            if (baseQuickAdapter3 != null) {
                baseQuickAdapter3.remove(intValue);
            }
            CircleAllUtils.INSTANCE.removeUp(upRecommentBean);
            UpRecommentBean upBean = CircleAllUtils.INSTANCE.getUpBean();
            if (upBean == null || (baseQuickAdapter = this.adapter) == null) {
                return;
            }
            baseQuickAdapter.addData((BaseQuickAdapter<UpRecommentBean, BaseViewHolder>) upBean);
        }
    }
}
