package com.one.tomato.mvp.p080ui.mine.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostSerializeBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.zzhoujay.richtext.RichText;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PostSerializeManageAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.adapter.PostSerializeManageAdapter */
/* loaded from: classes3.dex */
public final class PostSerializeManageAdapter extends BaseRecyclerViewAdapter<PostSerializeBean> {
    private Functions<Unit> checkBoxCallBack;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostSerializeManageAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_post_serialize_manage, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    public final void addCheckBoxCallBack(Functions<Unit> functions) {
        this.checkBoxCallBack = functions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final PostSerializeBean postSerializeBean) {
        String str;
        int indexOf$default;
        super.convert(baseViewHolder, (BaseViewHolder) postSerializeBean);
        String str2 = null;
        final CheckBox checkBox = baseViewHolder != null ? (CheckBox) baseViewHolder.getView(R.id.checkbox_serialize) : null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_comment) : null;
        if (roundedImageView != null) {
            roundedImageView.setImageBitmap(null);
        }
        if (!TextUtils.isEmpty(postSerializeBean != null ? postSerializeBean.getCover() : null)) {
            Context context = this.mContext;
            if (postSerializeBean != null) {
                str2 = postSerializeBean.getCover();
            }
            ImageLoaderUtil.loadRecyclerThumbImage(context, roundedImageView, new ImageBean(str2));
        } else if (roundedImageView != null) {
            roundedImageView.setImageResource(R.drawable.mine_post_publish_read);
        }
        if (postSerializeBean != null) {
            if (!TextUtils.isEmpty(postSerializeBean.getTitle())) {
                str = postSerializeBean.getTitle();
                Intrinsics.checkExpressionValueIsNotNull(str, "it.title");
            } else {
                str = "";
            }
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, "</", 0, false, 6, (Object) null);
            if (indexOf$default != -1) {
                RichText.fromHtml(str).into(textView);
            } else if (textView != null) {
                textView.setText(postSerializeBean.getTitle());
            }
            String string = AppUtil.getString(R.string.circle_post);
            if (textView2 != null) {
                textView2.setText(postSerializeBean.getAmountArticle() + string);
            }
            if (checkBox != null) {
                checkBox.setChecked(postSerializeBean.isSelect());
            }
            if (postSerializeBean.isShowCheckBox()) {
                if (checkBox != null) {
                    checkBox.setVisibility(0);
                }
            } else if (checkBox != null) {
                checkBox.setVisibility(8);
            }
        }
        if (checkBox != null) {
            checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.adapter.PostSerializeManageAdapter$convert$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Functions functions;
                    PostSerializeBean postSerializeBean2 = postSerializeBean;
                    if (postSerializeBean2 != null && postSerializeBean2.isSelect()) {
                        postSerializeBean.setSelect(false);
                        checkBox.setChecked(false);
                    } else {
                        PostSerializeBean postSerializeBean3 = postSerializeBean;
                        if (postSerializeBean3 != null) {
                            postSerializeBean3.setSelect(true);
                        }
                        checkBox.setChecked(true);
                    }
                    functions = PostSerializeManageAdapter.this.checkBoxCallBack;
                    if (functions != null) {
                        Unit unit = (Unit) functions.mo6822invoke();
                    }
                }
            });
        }
    }
}
