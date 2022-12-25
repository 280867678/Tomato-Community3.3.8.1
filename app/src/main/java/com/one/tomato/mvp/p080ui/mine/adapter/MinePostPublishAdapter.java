package com.one.tomato.mvp.p080ui.mine.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzhoujay.richtext.RichText;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: MinePostPublishAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.adapter.MinePostPublishAdapter */
/* loaded from: classes3.dex */
public final class MinePostPublishAdapter extends BaseRecyclerViewAdapter<PostList> {
    private Function1<? super PostList, Unit> checkBoxCallBack;
    private boolean isShowMove;

    public MinePostPublishAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_mine_post_publish, recyclerView);
    }

    public final void addCheckBoxCallBack(Function1<? super PostList, Unit> function1) {
        this.checkBoxCallBack = function1;
    }

    public final void setIsShowMove(boolean z) {
        this.isShowMove = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x025f  */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void convert(BaseViewHolder baseViewHolder, final PostList postList) {
        String str;
        int indexOf$default;
        String str2;
        int i;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        String str3 = null;
        Integer num = null;
        final CheckBox checkBox = baseViewHolder != null ? (CheckBox) baseViewHolder.getView(R.id.checkbox) : null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_comment) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_zan) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_num_time) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_move) : null;
        if (textView4 != null) {
            textView4.setVisibility(0);
        }
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (roundedImageView != null) {
            roundedImageView.setImageBitmap(null);
        }
        Integer valueOf = postList != null ? Integer.valueOf(postList.getPostType()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            if (textView4 != null) {
                StringBuilder sb = new StringBuilder();
                if (postList != null) {
                    num = Integer.valueOf(postList.getPicNum());
                }
                sb.append(num.intValue());
                sb.append(AppUtil.getString(R.string.circle_post_img_num));
                textView4.setText(sb.toString());
            }
            ArrayList<ImageBean> createImageBeanList = PostUtils.INSTANCE.createImageBeanList(postList);
            if (!(createImageBeanList == null || createImageBeanList.isEmpty()) && createImageBeanList.size() > 0) {
                ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, createImageBeanList.get(0));
            }
        } else if (valueOf != null && valueOf.intValue() == 2) {
            if (textView4 != null) {
                if (postList != null) {
                    str3 = postList.getVideoTime();
                }
                textView4.setText(String.valueOf(str3));
            }
            ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(postList.getSecVideoCover()));
        } else if (valueOf != null && valueOf.intValue() == 3) {
            if (textView4 != null) {
                textView4.setVisibility(8);
            }
            if (roundedImageView != null) {
                roundedImageView.setImageResource(R.drawable.mine_post_publish_read);
            }
        }
        if (postList != null) {
            if (!TextUtils.isEmpty(postList.getTitle())) {
                str = postList.getTitle();
                Intrinsics.checkExpressionValueIsNotNull(str, "it.title");
            } else if (!TextUtils.isEmpty(postList.getDescription())) {
                str = postList.getDescription();
                Intrinsics.checkExpressionValueIsNotNull(str, "it.description");
            } else {
                str = "";
            }
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, "</", 0, false, 6, (Object) null);
            if (indexOf$default != -1) {
                RichText.fromHtml(str).into(textView);
            } else if (textView != null) {
                textView.setText(postList.getTitle());
            }
            String stringComment = AppUtil.getString(R.string.video_comment_num);
            String stringZan = AppUtil.getString(R.string.video_zan_num);
            if (postList.getCommentTimes() < 10000) {
                str2 = "java.lang.String.format(this, *args)";
                if (textView2 != null) {
                    Intrinsics.checkExpressionValueIsNotNull(stringComment, "stringComment");
                    Object[] objArr = {String.valueOf(postList.getCommentTimes())};
                    String format = String.format(stringComment, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, str2);
                    textView2.setText(format);
                }
            } else if (textView2 != null) {
                Intrinsics.checkExpressionValueIsNotNull(stringComment, "stringComment");
                Object[] objArr2 = {FormatUtil.formatOne(Double.valueOf(postList.getCommentTimes() / ConstantUtils.MAX_ITEM_NUM)) + 'w'};
                String format2 = String.format(stringComment, Arrays.copyOf(objArr2, objArr2.length));
                str2 = "java.lang.String.format(this, *args)";
                Intrinsics.checkExpressionValueIsNotNull(format2, str2);
                textView2.setText(format2);
            } else {
                str2 = "java.lang.String.format(this, *args)";
            }
            if (postList.getGoodNum() >= 10000) {
                if (textView3 != null) {
                    Intrinsics.checkExpressionValueIsNotNull(stringZan, "stringZan");
                    Object[] objArr3 = {FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + 'w'};
                    String format3 = String.format(stringZan, Arrays.copyOf(objArr3, objArr3.length));
                    Intrinsics.checkExpressionValueIsNotNull(format3, str2);
                    textView3.setText(format3);
                }
            } else if (textView3 != null) {
                Intrinsics.checkExpressionValueIsNotNull(stringZan, "stringZan");
                i = 0;
                Object[] objArr4 = {String.valueOf(postList.getGoodNum())};
                String format4 = String.format(stringZan, Arrays.copyOf(objArr4, objArr4.length));
                Intrinsics.checkExpressionValueIsNotNull(format4, str2);
                textView3.setText(format4);
                if (!postList.isShowSelectPostDelete()) {
                    if (checkBox != null) {
                        checkBox.setVisibility(i);
                    }
                } else if (checkBox != null) {
                    checkBox.setVisibility(8);
                }
                if (checkBox != null) {
                    checkBox.setChecked(postList.isSelectMinePostDelete());
                }
                if (!this.isShowMove) {
                    if (imageView != null) {
                        imageView.setVisibility(0);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(8);
                }
                if (!postList.isSerializeSelect()) {
                    if (postList.getPostType() == 2 && textView4 != null) {
                        textView4.setText(AppUtil.getString(R.string.post_serialize_video_play));
                    }
                    if (textView != null) {
                        textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorAccent));
                    }
                } else if (textView != null) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
                }
            }
            i = 0;
            if (!postList.isShowSelectPostDelete()) {
            }
            if (checkBox != null) {
            }
            if (!this.isShowMove) {
            }
            if (!postList.isSerializeSelect()) {
            }
        }
        if (checkBox != null) {
            checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.adapter.MinePostPublishAdapter$convert$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1 function1;
                    PostList postList2 = postList;
                    if (postList2 != null && postList2.isSelectMinePostDelete()) {
                        postList.setSelectMinePostDelete(false);
                        checkBox.setChecked(false);
                    } else {
                        PostList postList3 = postList;
                        if (postList3 != null) {
                            postList3.setSelectMinePostDelete(true);
                        }
                        checkBox.setChecked(true);
                    }
                    function1 = MinePostPublishAdapter.this.checkBoxCallBack;
                    if (function1 != null) {
                        PostList postList4 = postList;
                        if (postList4 != null) {
                            Unit unit = (Unit) function1.mo6794invoke(postList4);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
    }
}
