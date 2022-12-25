package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: NewPostTopAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostTopAdapter */
/* loaded from: classes3.dex */
public final class NewPostTopAdapter extends BaseRecyclerViewAdapter<PostList> {
    private NewPostItemOnClickCallBack newPostItemOnClickCallBack;

    public NewPostTopAdapter(Context context, List<PostList> list, RecyclerView recyclerView, NewPostItemOnClickCallBack newPostItemOnClickCallBack) {
        super(context, R.layout.item_new_post_item_top, list, recyclerView);
        this.newPostItemOnClickCallBack = newPostItemOnClickCallBack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final PostList postList) {
        String str;
        CharSequence trim;
        CharSequence trim2;
        CharSequence trim3;
        String str2;
        String str3;
        CharSequence trim4;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_red_title) : null;
        if (textView != null) {
            textView.setVisibility(8);
        }
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_pic_title) : null;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relative_pic) : null;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_pic) : null;
        if (roundedImageView != null) {
            roundedImageView.setVisibility(8);
        }
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_play) : null;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_content) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_tag) : null;
        if (baseViewHolder != null) {
            RelativeLayout relativeLayout2 = (RelativeLayout) baseViewHolder.getView(R.id.relate_conmment);
        }
        TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_conmment_num) : null;
        RelativeLayout relativeLayout3 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_zan) : null;
        final TextView textView6 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_zan_num) : null;
        final ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_zan) : null;
        Integer valueOf = postList != null ? Integer.valueOf(postList.getPostType()) : null;
        if (valueOf != null && valueOf.intValue() == 2) {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (roundedImageView != null) {
                roundedImageView.setVisibility(0);
            }
            if (textView != null) {
                textView.setVisibility(8);
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            String title = postList.getTitle();
            if (title == null) {
                title = postList.getDescription();
            }
            if (textView2 != null) {
                if (title == null) {
                    str3 = null;
                } else if (title == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                } else {
                    trim4 = StringsKt__StringsKt.trim(title);
                    str3 = trim4.toString();
                }
                textView2.setText(String.valueOf(str3));
            }
            ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(postList.getSecVideoCover()));
        } else if (valueOf != null && valueOf.intValue() == 3) {
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
            String title2 = postList.getTitle();
            if (title2 == null) {
                title2 = "";
            }
            if (textView != null) {
                if (title2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim3 = StringsKt__StringsKt.trim(title2);
                textView.setText(trim3.toString());
            }
            String s1 = postList.getDescription();
            if (s1 == null) {
                s1 = postList.getTitle();
            }
            if (textView3 != null) {
                Intrinsics.checkExpressionValueIsNotNull(s1, "s1");
                if (s1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim2 = StringsKt__StringsKt.trim(s1);
                textView3.setText(trim2.toString());
            }
        } else if (valueOf != null && valueOf.intValue() == 1) {
            if (textView != null) {
                textView.setVisibility(8);
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            if (roundedImageView != null) {
                roundedImageView.setVisibility(0);
            }
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            String title3 = postList.getTitle();
            if (title3 == null) {
                title3 = postList.getDescription();
            }
            if (textView2 != null) {
                if (title3 == null) {
                    str = null;
                } else if (title3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                } else {
                    trim = StringsKt__StringsKt.trim(title3);
                    str = trim.toString();
                }
                textView2.setText(String.valueOf(str));
            }
            ArrayList<ImageBean> createImageBeanList = PostUtils.INSTANCE.createImageBeanList(postList);
            if (createImageBeanList != null && (!createImageBeanList.isEmpty())) {
                ImageBean imageBean = createImageBeanList.get(0);
                Intrinsics.checkExpressionValueIsNotNull(imageBean, "createImageBeanList[0]");
                ImageBean imageBean2 = imageBean;
                if (imageBean2 != null) {
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, imageBean2);
                }
            }
        }
        if (textView4 != null) {
            if (postList == null || (str2 = postList.getGroupName()) == null) {
                str2 = "";
            }
            textView4.setText(str2);
        }
        if (postList != null) {
            if (postList.getCommentTimes() >= 10000) {
                if (textView5 != null) {
                    textView5.setText(FormatUtil.formatOne(Double.valueOf(postList.getCommentTimes() / ConstantUtils.MAX_ITEM_NUM)) + (char) 19975);
                }
            } else if (textView5 != null) {
                textView5.setText(String.valueOf(postList.getCommentTimes()));
            }
            if (postList.getGoodNum() >= 10000) {
                if (textView6 != null) {
                    textView6.setText(FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + (char) 19975);
                }
            } else if (textView6 != null) {
                textView6.setText(String.valueOf(postList.getGoodNum()));
            }
        }
        Integer valueOf2 = postList != null ? Integer.valueOf(postList.getIsThumbUp()) : null;
        if (valueOf2 == null || valueOf2.intValue() == 0) {
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.icon_new_post_zan);
            }
        } else if (valueOf2.intValue() == 1 && imageView2 != null) {
            imageView2.setImageResource(R.drawable.icon_new_post_zan_ok);
        }
        if (relativeLayout3 != null) {
            relativeLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.NewPostTopAdapter$convert$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewPostItemOnClickCallBack newPostItemOnClickCallBack;
                    PostList postList2;
                    newPostItemOnClickCallBack = NewPostTopAdapter.this.newPostItemOnClickCallBack;
                    Boolean valueOf3 = newPostItemOnClickCallBack != null ? Boolean.valueOf(newPostItemOnClickCallBack.itemClickZan(postList, NewPostTopAdapter.this.getData().indexOf(postList))) : null;
                    if (valueOf3 != null) {
                        valueOf3.booleanValue();
                        if (!valueOf3.booleanValue() || (postList2 = postList) == null || postList2.getIsThumbUp() != 0) {
                            return;
                        }
                        postList.setIsThumbUp(1);
                        PostList postList3 = postList;
                        postList3.setGoodNum(postList3.getGoodNum() + 1);
                        if (postList.getGoodNum() >= 10000) {
                            TextView textView7 = textView6;
                            if (textView7 != null) {
                                textView7.setText(FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + (char) 19975);
                            }
                        } else {
                            TextView textView8 = textView6;
                            if (textView8 != null) {
                                textView8.setText(String.valueOf(postList.getGoodNum()));
                            }
                        }
                        ImageView imageView3 = imageView2;
                        if (imageView3 == null) {
                            return;
                        }
                        imageView3.setImageResource(R.drawable.icon_new_post_zan_ok);
                    }
                }
            });
        }
        if (baseViewHolder != null) {
            baseViewHolder.addOnClickListener(R.id.relate_conmment);
        }
        if (baseViewHolder != null) {
            baseViewHolder.addOnClickListener(R.id.text_tag);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        NewPostItemOnClickCallBack newPostItemOnClickCallBack;
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        PostList postList = getData().get(i);
        Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
        if (valueOf != null && valueOf.intValue() == R.id.relate_conmment) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack2 == null) {
                return;
            }
            newPostItemOnClickCallBack2.itemConmment(postList, i, view);
        } else if (valueOf == null || valueOf.intValue() != R.id.text_tag || (newPostItemOnClickCallBack = this.newPostItemOnClickCallBack) == null) {
        } else {
            newPostItemOnClickCallBack.itemCircle(postList);
        }
    }
}
