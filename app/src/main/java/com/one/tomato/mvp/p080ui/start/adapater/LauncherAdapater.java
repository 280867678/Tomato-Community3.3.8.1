package com.one.tomato.mvp.p080ui.start.adapater;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LauncherAdapater.kt */
/* renamed from: com.one.tomato.mvp.ui.start.adapater.LauncherAdapater */
/* loaded from: classes3.dex */
public final class LauncherAdapater extends BaseRecyclerViewAdapter<Integer> {
    private Functions<Unit> clickJoin;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LauncherAdapater(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_launcer, recyclerView);
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    public final void setClickJoinCallBack(Functions<Unit> functions) {
        this.clickJoin = functions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Integer num) {
        super.convert(baseViewHolder, (BaseViewHolder) num);
        TextView textView = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_to_top) : null;
        if (baseViewHolder != null) {
            textView = (TextView) baseViewHolder.getView(R.id.text_join);
        }
        if (getData().indexOf(num) < this.mData.size() - 1) {
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            if (textView != null) {
                textView.setVisibility(8);
            }
            final ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView2, "translationY", 0.0f, -70.0f);
            if (ofFloat != null) {
                ofFloat.setDuration(1000L);
            }
            if (ofFloat != null) {
                ofFloat.setRepeatCount(-1);
            }
            if (ofFloat != null) {
                ofFloat.start();
            }
            if (ofFloat != null) {
                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.one.tomato.mvp.ui.start.adapater.LauncherAdapater$convert$1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Context context;
                        ObjectAnimator objectAnimator;
                        context = ((BaseQuickAdapter) LauncherAdapater.this).mContext;
                        if (context != null || (objectAnimator = ofFloat) == null) {
                            return;
                        }
                        objectAnimator.cancel();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        Context context;
                        ObjectAnimator objectAnimator;
                        context = ((BaseQuickAdapter) LauncherAdapater.this).mContext;
                        if (context != null || (objectAnimator = ofFloat) == null) {
                            return;
                        }
                        objectAnimator.cancel();
                    }
                });
            }
        } else {
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
        }
        if (num != null) {
            num.intValue();
            if (imageView != null) {
                Context mContext = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                imageView.setImageDrawable(mContext.getResources().getDrawable(num.intValue()));
            }
        }
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.adapater.LauncherAdapater$convert$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Functions functions;
                    functions = LauncherAdapater.this.clickJoin;
                    if (functions != null) {
                        Unit unit = (Unit) functions.mo6822invoke();
                    }
                }
            });
        }
    }
}
