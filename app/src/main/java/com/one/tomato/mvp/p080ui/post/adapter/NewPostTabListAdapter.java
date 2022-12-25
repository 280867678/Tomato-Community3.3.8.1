package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.adapter.PublishHotTagAdapter;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.ShowUpPopWindow;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.item.PostCircleItem;
import com.one.tomato.mvp.p080ui.post.item.PostHotMessageItem;
import com.one.tomato.mvp.p080ui.post.item.PostImageItem;
import com.one.tomato.mvp.p080ui.post.item.PostReadItem;
import com.one.tomato.mvp.p080ui.post.item.PostReviewItem;
import com.one.tomato.mvp.p080ui.post.item.PostTopItem;
import com.one.tomato.mvp.p080ui.post.item.PostUpItem;
import com.one.tomato.mvp.p080ui.post.item.PostVideoItem;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.TagPostListAct;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.anko.AsyncKt;

/* compiled from: NewPostTabListAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter */
/* loaded from: classes3.dex */
public final class NewPostTabListAdapter extends BaseRecyclerViewAdapter<PostList> {
    private String businessType;
    private NewPostItemOnClickCallBack newPostItemOnClickCallBack;
    private PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
    private boolean isShowImageGuide = true;
    private int currentPostion = -1;
    private Vector<View> vector = new Vector<>();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostTabListAdapter(Context context, RecyclerView recyclerView, NewPostItemOnClickCallBack newPostItemOnClickCallBack, PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(context, R.layout.new_tab_list_post_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.newPostItemOnClickCallBack = newPostItemOnClickCallBack;
        this.postEvenOneTabVideoListManger = postEvenOneTabVideoListManger;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0551  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0577  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x058c  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0ba3  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0eac  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0f0e  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0fa4  */
    /* JADX WARN: Removed duplicated region for block: B:489:0x0fac  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0fb4  */
    /* JADX WARN: Removed duplicated region for block: B:493:0x0fbc  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x0fc4  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0fcc  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0fd4  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0fdc  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x0fe4  */
    /* JADX WARN: Removed duplicated region for block: B:505:0x0fec  */
    /* JADX WARN: Removed duplicated region for block: B:507:0x0ff4  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0ffc  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x1022  */
    /* JADX WARN: Removed duplicated region for block: B:519:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0ef8  */
    /* JADX WARN: Removed duplicated region for block: B:599:0x0f00  */
    /* JADX WARN: Removed duplicated region for block: B:790:0x094a  */
    /* JADX WARN: Removed duplicated region for block: B:792:0x0951  */
    /* JADX WARN: Removed duplicated region for block: B:794:0x0958  */
    /* JADX WARN: Removed duplicated region for block: B:870:0x0a52  */
    /* JADX WARN: Removed duplicated region for block: B:889:0x0aac  */
    /* JADX WARN: Removed duplicated region for block: B:891:0x0ab1  */
    /* JADX WARN: Removed duplicated region for block: B:894:0x0ac0  */
    /* JADX WARN: Removed duplicated region for block: B:897:0x0ae2  */
    /* JADX WARN: Removed duplicated region for block: B:900:0x0af9  */
    /* JADX WARN: Removed duplicated region for block: B:903:0x0b09  */
    /* JADX WARN: Removed duplicated region for block: B:922:0x0b52  */
    /* JADX WARN: Removed duplicated region for block: B:925:0x0b86  */
    /* JADX WARN: Removed duplicated region for block: B:927:0x0b91  */
    /* JADX WARN: Removed duplicated region for block: B:929:0x0b98  */
    /* JADX WARN: Removed duplicated region for block: B:930:0x0b63  */
    /* JADX WARN: Removed duplicated region for block: B:937:0x0b4f  */
    /* JADX WARN: Removed duplicated region for block: B:938:0x0b02  */
    /* JADX WARN: Removed duplicated region for block: B:939:0x0aee  */
    /* JADX WARN: Removed duplicated region for block: B:941:0x0ac5  */
    /* JADX WARN: Removed duplicated region for block: B:944:0x0570  */
    /* JADX WARN: Removed duplicated region for block: B:947:0x055c  */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void convert(BaseViewHolder baseViewHolder, final PostList postList) {
        LinearLayout linearLayout;
        final NewPostTabListAdapter newPostTabListAdapter;
        String str;
        String str2;
        int i;
        int i2;
        int i3;
        int postType;
        TextView textView;
        int i4;
        int indexOf$default;
        Vector vector;
        PostImageItem postImageItem;
        RelativeLayout relativeLayout;
        BaseViewHolder baseViewHolder2;
        RelativeLayout relativeLayout2;
        final ImageView imageView;
        final TextView textView2;
        int i5;
        String str3;
        ImageView imageView2;
        int i6;
        boolean z;
        ArrayList<Tag> tagList;
        boolean z2;
        RecyclerView recyclerView;
        int i7;
        int indexOf$default2;
        PostVideoItem postVideoItem;
        int indexOf$default3;
        PostReadItem postReadItem;
        String description;
        PostImageItem postImageItem2;
        PostVideoItem postVideoItem2;
        String name;
        ArrayList arrayListOf;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        RelativeLayout relativeLayout3 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.cons_post_auth) : null;
        LinearLayout linearLayout2 = baseViewHolder != null ? (LinearLayout) baseViewHolder.getView(R.id.liner_content) : null;
        RelativeLayout relativeLayout4 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relative_image_guide) : null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_head) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.post_auth) : null;
        ImageView imageView3 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_post_member_level_nick) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_tag) : null;
        TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_content) : null;
        TextView textView6 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        LinearLayout linearLayout3 = baseViewHolder != null ? (LinearLayout) baseViewHolder.getView(R.id.cons_shar_zan) : null;
        TextView textView7 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_need_pay) : null;
        ImageView imageView4 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_vip) : null;
        ImageView imageView5 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_up) : null;
        RoundedImageView roundedImageView2 = roundedImageView;
        ImageView imageView6 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_zb) : null;
        TextView textView8 = textView3;
        ImageView imageView7 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_review) : null;
        LinearLayout linearLayout4 = linearLayout2;
        TextView textView9 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_ad) : null;
        LinearLayout linearLayout5 = baseViewHolder != null ? (LinearLayout) baseViewHolder.getView(R.id.liner_ad) : null;
        TextView textView10 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_do) : null;
        TextView textView11 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_look_time) : null;
        TextView textView12 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_ad_title) : null;
        LinearLayout linearLayout6 = baseViewHolder != null ? (LinearLayout) baseViewHolder.getView(R.id.line_zan_share) : null;
        ImageView imageView8 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_review_pass) : null;
        TextView textView13 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_signature) : null;
        RelativeLayout relativeLayout5 = relativeLayout4;
        RelativeLayout relativeLayout6 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_tag) : null;
        RecyclerView recyclerView2 = baseViewHolder != null ? (RecyclerView) baseViewHolder.getView(R.id.recycler_tag) : null;
        TextView textView14 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_serialize) : null;
        TextView textView15 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_original) : null;
        LinearLayout linearLayout7 = linearLayout5;
        TextView textView16 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_papa_no_tag) : null;
        TextView textView17 = textView9;
        TextView textView18 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_hot_list_title) : null;
        TextView textView19 = textView16;
        ImageView imageView9 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_porter) : null;
        ImageView imageView10 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_post_delete) : null;
        TextView textView20 = textView15;
        ImageView imageView11 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_post_detail) : null;
        TextView textView21 = textView13;
        RelativeLayout relativeLayout7 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_zan) : null;
        RelativeLayout relativeLayout8 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_conmment) : null;
        ImageView imageView12 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_zan) : null;
        TextView textView22 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_conmment_num) : null;
        TextView textView23 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_zan_num) : null;
        TextView textView24 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_share_num) : null;
        RelativeLayout relativeLayout9 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_reward) : null;
        TextView textView25 = textView18;
        TextView textView26 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_reward) : null;
        View view = baseViewHolder != null ? baseViewHolder.getView(R.id.view_line) : null;
        if (imageView10 != null) {
            imageView10.setVisibility(8);
        }
        if (imageView11 != null) {
            imageView11.setVisibility(0);
        }
        if (linearLayout3 != null) {
            linearLayout3.setVisibility(0);
        }
        if (relativeLayout3 != null) {
            relativeLayout3.setVisibility(0);
        }
        if (imageView3 != null) {
            imageView3.setVisibility(0);
        }
        if (textView6 != null) {
            textView6.setVisibility(8);
        }
        if (textView5 != null) {
            textView5.setVisibility(8);
        }
        if (textView4 != null) {
            textView4.setVisibility(0);
        }
        if (textView7 != null) {
            textView7.setVisibility(8);
        }
        if (imageView4 != null) {
            imageView4.setVisibility(8);
        }
        if (imageView5 != null) {
            imageView5.setVisibility(8);
        }
        if (imageView6 != null) {
            imageView6.setVisibility(8);
        }
        if (imageView7 != null) {
            imageView7.setVisibility(8);
        }
        if (relativeLayout9 != null) {
            relativeLayout9.setVisibility(8);
        }
        if (textView25 != null) {
            textView25.setVisibility(8);
        }
        ImageView imageView13 = imageView10;
        if (textView21 != null) {
            textView21.setVisibility(8);
        }
        if (textView20 != null) {
            textView20.setVisibility(8);
        }
        if (textView19 != null) {
            textView19.setVisibility(8);
        }
        if (textView17 != null) {
            textView17.setVisibility(8);
        }
        if (linearLayout7 != null) {
            linearLayout7.setVisibility(8);
        }
        TextView textView27 = textView7;
        if (relativeLayout5 != null) {
            relativeLayout5.setVisibility(8);
        }
        RelativeLayout relativeLayout10 = relativeLayout9;
        if (imageView8 != null) {
            imageView8.setVisibility(8);
        }
        if (view != null) {
            view.setVisibility(0);
        }
        if (postList == null) {
            linearLayout = linearLayout4;
            newPostTabListAdapter = this;
        } else if (postList.getTipFlag() == 1) {
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            if (view != null) {
                view.setVisibility(8);
            }
            Context context = this.mContext;
            NewPostItemOnClickCallBack newPostItemOnClickCallBack = this.newPostItemOnClickCallBack;
            arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(postList);
            PostTopItem postTopItem = new PostTopItem(context, newPostItemOnClickCallBack, arrayListOf);
            if (linearLayout4 != null) {
                linearLayout4.removeAllViews();
                Unit unit = Unit.INSTANCE;
            }
            if (linearLayout4 == null) {
                return;
            }
            linearLayout4.addView(postTopItem);
            Unit unit2 = Unit.INSTANCE;
            return;
        } else {
            newPostTabListAdapter = this;
            linearLayout = linearLayout4;
        }
        View view2 = view;
        if (postList != null && postList.getInsertFlag() == 1) {
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            PostCircleItem postCircleItem = new PostCircleItem(newPostTabListAdapter.mContext);
            postCircleItem.setData(postList.getCircleDiscoverListBeans());
            LinearLayout linearLayout8 = linearLayout;
            if (linearLayout8 != null) {
                linearLayout8.removeAllViews();
                Unit unit3 = Unit.INSTANCE;
            }
            if (linearLayout8 == null) {
                return;
            }
            linearLayout8.addView(postCircleItem);
            Unit unit4 = Unit.INSTANCE;
            return;
        }
        LinearLayout linearLayout9 = linearLayout;
        TextView textView28 = textView4;
        if (postList != null && postList.getInsertFlag() == 2) {
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            PostUpItem postUpItem = new PostUpItem(newPostTabListAdapter.mContext);
            postUpItem.setData(postList.getUpRecommentBeans(), false);
            if (linearLayout9 != 0) {
                linearLayout9.removeAllViews();
                Unit unit5 = Unit.INSTANCE;
            }
            if (linearLayout9 == 0) {
                return;
            }
            linearLayout9.addView(postUpItem);
            Unit unit6 = Unit.INSTANCE;
        } else if (postList != null && postList.getInsertFlag() == 3) {
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            PostReviewItem postReviewItem = new PostReviewItem(newPostTabListAdapter.mContext, 1);
            if (linearLayout9 != 0) {
                linearLayout9.removeAllViews();
                Unit unit7 = Unit.INSTANCE;
            }
            if (linearLayout9 == 0) {
                return;
            }
            linearLayout9.addView(postReviewItem);
            Unit unit8 = Unit.INSTANCE;
        } else if (postList != null && postList.getInsertFlag() == 4) {
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            PostReviewItem postReviewItem2 = new PostReviewItem(newPostTabListAdapter.mContext, 2);
            if (linearLayout9 != 0) {
                linearLayout9.removeAllViews();
                Unit unit9 = Unit.INSTANCE;
            }
            if (linearLayout9 == 0) {
                return;
            }
            linearLayout9.addView(postReviewItem2);
            Unit unit10 = Unit.INSTANCE;
        } else {
            TextView textView29 = textView5;
            if (postList != null && postList.getInsertFlag() == 5) {
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(8);
                }
                if (relativeLayout3 != null) {
                    relativeLayout3.setVisibility(8);
                }
                if (linearLayout7 != null) {
                    linearLayout7.setVisibility(8);
                }
                Context mContext = newPostTabListAdapter.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                PostHotMessageItem postHotMessageItem = new PostHotMessageItem(mContext);
                postHotMessageItem.setData(postList.getPostHotMessageBeans());
                if (linearLayout9 != 0) {
                    linearLayout9.removeAllViews();
                    Unit unit11 = Unit.INSTANCE;
                }
                if (linearLayout9 == 0) {
                    return;
                }
                linearLayout9.addView(postHotMessageItem);
                Unit unit12 = Unit.INSTANCE;
                return;
            }
            if (textView8 != null) {
                textView8.setText((postList == null || (name = postList.getName()) == null) ? "" : name);
            }
            Context context2 = newPostTabListAdapter.mContext;
            if (postList != null) {
                str = "";
                str2 = postList.getAvatar();
            } else {
                str = "";
                str2 = null;
            }
            ImageLoaderUtil.loadHeadImage(context2, roundedImageView2, new ImageBean(str2));
            if (imageView3 != null) {
                imageView3.setVisibility(0);
            }
            UserPermissionUtil userPermissionUtil = UserPermissionUtil.getInstance();
            Integer valueOf = postList != null ? Integer.valueOf(postList.getCurrentLevelIndex()) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            userPermissionUtil.userLevelNickShow(imageView3, new LevelBean(valueOf.intValue()));
            if (postList.getVipType() > 0) {
                i = 0;
                if (imageView4 != null) {
                    imageView4.setVisibility(0);
                }
            } else {
                i = 0;
                if (imageView4 != null) {
                    imageView4.setVisibility(8);
                }
            }
            if (postList.isMemberIsUp()) {
                if (imageView5 != null) {
                    imageView5.setVisibility(i);
                }
                if (postList == null || postList.getMemberIsOriginal() != 1) {
                    if (postList == null || postList.getUpLevel() != 1) {
                        if (postList == null || postList.getUpLevel() != 2) {
                            if (postList == null || postList.getUpLevel() != 3) {
                                if (postList == null || postList.getUpLevel() != 4) {
                                    if (postList != null && postList.getUpLevel() == 5 && imageView5 != null) {
                                        imageView5.setImageResource(R.drawable.my_up_y5);
                                        Unit unit13 = Unit.INSTANCE;
                                    }
                                } else if (imageView5 != null) {
                                    imageView5.setImageResource(R.drawable.my_up_y4);
                                    Unit unit14 = Unit.INSTANCE;
                                }
                            } else if (imageView5 != null) {
                                imageView5.setImageResource(R.drawable.my_up_y3_v);
                                Unit unit15 = Unit.INSTANCE;
                            }
                        } else if (imageView5 != null) {
                            imageView5.setImageResource(R.drawable.my_up_y2);
                            Unit unit16 = Unit.INSTANCE;
                        }
                    } else if (imageView5 != null) {
                        imageView5.setImageResource(R.drawable.my_up_y1);
                        Unit unit17 = Unit.INSTANCE;
                    }
                } else if (imageView5 != null) {
                    imageView5.setImageResource(R.drawable.up_original);
                    Unit unit18 = Unit.INSTANCE;
                }
            } else if (imageView5 != null) {
                i2 = 8;
                imageView5.setVisibility(8);
                if (postList.getGoldPorterFlag() == 1) {
                    i3 = 0;
                    if (imageView9 != null) {
                        imageView9.setVisibility(i2);
                    }
                } else if (imageView9 != null) {
                    i3 = 0;
                    imageView9.setVisibility(0);
                } else {
                    i3 = 0;
                }
                if (!postList.isMemberIsAnchor()) {
                    if (imageView6 != null) {
                        imageView6.setVisibility(i3);
                    }
                } else if (imageView6 != null) {
                    imageView6.setVisibility(i2);
                }
                if (textView26 != null) {
                    textView26.setText(String.valueOf(postList.getPayTimes()));
                }
                postType = postList.getPostType();
                if (postType == 1) {
                    if (postType != 2) {
                        if (postType == 3) {
                            if (textView6 != null) {
                                textView6.setVisibility(8);
                            }
                            if (textView29 != null) {
                                textView29.setVisibility(0);
                            }
                            String content = !TextUtils.isEmpty(postList.getDescription()) ? postList.getDescription() : postList.getTitle();
                            Intrinsics.checkExpressionValueIsNotNull(content, "content");
                            indexOf$default3 = StringsKt__StringsKt.indexOf$default((CharSequence) content, "</", 0, false, 6, (Object) null);
                            if (indexOf$default3 != -1) {
                                RichText.fromHtml(content).into(textView29);
                            } else if (textView29 != null) {
                                textView29.setText(content);
                            }
                            Vector vector2 = this.vector;
                            if ((vector2 != null ? Integer.valueOf(vector2.size()) : null).intValue() > 0) {
                                Vector<View> vector3 = new Vector();
                                vector3.addAll(this.vector);
                                postReadItem = null;
                                for (View view3 : vector3) {
                                    if (view3 instanceof PostReadItem) {
                                        postReadItem = (PostReadItem) view3;
                                    }
                                }
                                Unit unit19 = Unit.INSTANCE;
                                vector3.clear();
                                Unit unit20 = Unit.INSTANCE;
                                if (postReadItem != null) {
                                    Vector vector4 = this.vector;
                                    Boolean.valueOf((vector4 != null ? Boolean.valueOf(vector4.removeElement(postReadItem)) : null).booleanValue());
                                }
                            } else {
                                postReadItem = null;
                            }
                            if (postReadItem == null) {
                                Context mContext2 = this.mContext;
                                Intrinsics.checkExpressionValueIsNotNull(mContext2, "mContext");
                                postReadItem = new PostReadItem(mContext2);
                            } else {
                                ViewParent parent = postReadItem != null ? postReadItem.getParent() : null;
                                if (parent != null && (parent instanceof LinearLayout)) {
                                    ((LinearLayout) parent).removeAllViews();
                                }
                            }
                            if (!TextUtils.isEmpty(postList != null ? postList.getTitle() : null)) {
                                if (postList != null) {
                                    description = postList.getTitle();
                                    if (postReadItem != null) {
                                        postReadItem.setReadText(description);
                                        Unit unit21 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.removeAllViews();
                                        Unit unit22 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.addView(postReadItem);
                                        Unit unit23 = Unit.INSTANCE;
                                    }
                                }
                                description = null;
                                if (postReadItem != null) {
                                }
                                if (linearLayout9 != 0) {
                                }
                                if (linearLayout9 != 0) {
                                }
                            } else {
                                if (postList != null) {
                                    description = postList.getDescription();
                                    if (postReadItem != null) {
                                    }
                                    if (linearLayout9 != 0) {
                                    }
                                    if (linearLayout9 != 0) {
                                    }
                                }
                                description = null;
                                if (postReadItem != null) {
                                }
                                if (linearLayout9 != 0) {
                                }
                                if (linearLayout9 != 0) {
                                }
                            }
                        } else if (postType != 4) {
                            if (postType == 9) {
                                if (imageView3 != null) {
                                    imageView3.setVisibility(8);
                                }
                                if (imageView4 != null) {
                                    imageView4.setVisibility(8);
                                }
                                if (imageView5 != null) {
                                    imageView5.setVisibility(8);
                                }
                                if (imageView6 != null) {
                                    imageView6.setVisibility(8);
                                }
                                if (imageView11 != null) {
                                    imageView11.setVisibility(4);
                                }
                                if (linearLayout3 != null) {
                                    linearLayout3.setVisibility(8);
                                }
                                if (linearLayout7 != null) {
                                    linearLayout7.setVisibility(0);
                                }
                                if (textView17 != null) {
                                    textView17.setVisibility(0);
                                }
                                if (textView6 != null) {
                                    textView6.setVisibility(8);
                                }
                                if (textView29 != null) {
                                    textView29.setVisibility(8);
                                }
                                if (textView12 != null) {
                                    String description2 = postList.getDescription();
                                    if (description2 == null) {
                                        description2 = str;
                                    }
                                    textView12.setText(description2);
                                }
                                if (postList.getSecVideoCover() != null) {
                                    Vector vector5 = this.vector;
                                    if ((vector5 != null ? Integer.valueOf(vector5.size()) : null).intValue() > 0) {
                                        Vector<View> vector6 = new Vector();
                                        vector6.addAll(this.vector);
                                        postVideoItem2 = null;
                                        for (View view4 : vector6) {
                                            if (view4 instanceof PostVideoItem) {
                                                postVideoItem2 = (PostVideoItem) view4;
                                            }
                                        }
                                        Unit unit24 = Unit.INSTANCE;
                                        vector6.clear();
                                        Unit unit25 = Unit.INSTANCE;
                                        if (postVideoItem2 != null) {
                                            Vector vector7 = this.vector;
                                            Boolean.valueOf((vector7 != null ? Boolean.valueOf(vector7.removeElement(postVideoItem2)) : null).booleanValue());
                                        }
                                    } else {
                                        postVideoItem2 = null;
                                    }
                                    if (postVideoItem2 == null) {
                                        Context mContext3 = this.mContext;
                                        Intrinsics.checkExpressionValueIsNotNull(mContext3, "mContext");
                                        postVideoItem2 = new PostVideoItem(mContext3, this.postEvenOneTabVideoListManger);
                                    } else {
                                        ViewParent parent2 = postVideoItem2 != null ? postVideoItem2.getParent() : null;
                                        if (parent2 != null && (parent2 instanceof LinearLayout)) {
                                            ((LinearLayout) parent2).removeAllViews();
                                        }
                                    }
                                    if (postVideoItem2 != null) {
                                        postVideoItem2.setPostListData(postList);
                                        Unit unit26 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.removeAllViews();
                                        Unit unit27 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.addView(postVideoItem2);
                                        Unit unit28 = Unit.INSTANCE;
                                    }
                                    Unit unit29 = Unit.INSTANCE;
                                }
                                if (postList.getSecImageUrl() != null) {
                                    Vector vector8 = this.vector;
                                    if ((vector8 != null ? Integer.valueOf(vector8.size()) : null).intValue() > 0) {
                                        Vector<View> vector9 = new Vector();
                                        vector9.addAll(this.vector);
                                        postImageItem2 = null;
                                        for (View view5 : vector9) {
                                            if (view5 instanceof PostImageItem) {
                                                postImageItem2 = (PostImageItem) view5;
                                            }
                                        }
                                        Unit unit30 = Unit.INSTANCE;
                                        vector9.clear();
                                        Unit unit31 = Unit.INSTANCE;
                                        if (postImageItem2 != null) {
                                            Vector vector10 = this.vector;
                                            Boolean.valueOf((vector10 != null ? Boolean.valueOf(vector10.removeElement(postImageItem2)) : null).booleanValue());
                                        }
                                    } else {
                                        postImageItem2 = null;
                                    }
                                    if (postImageItem2 == null) {
                                        Context mContext4 = this.mContext;
                                        Intrinsics.checkExpressionValueIsNotNull(mContext4, "mContext");
                                        postImageItem2 = new PostImageItem(mContext4, this.newPostItemOnClickCallBack);
                                    } else {
                                        ViewParent parent3 = postImageItem2 != null ? postImageItem2.getParent() : null;
                                        if (parent3 != null && (parent3 instanceof LinearLayout)) {
                                            ((LinearLayout) parent3).removeAllViews();
                                        }
                                    }
                                    ArrayList<ImageBean> arrayList = new ArrayList<>();
                                    arrayList.addAll(PostUtils.INSTANCE.createImageBeanList(postList));
                                    if (postImageItem2 != null) {
                                        postImageItem2.setImageBeanList(postList, arrayList, this.businessType);
                                        Unit unit32 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.removeAllViews();
                                        Unit unit33 = Unit.INSTANCE;
                                    }
                                    if (linearLayout9 != 0) {
                                        linearLayout9.addView(postImageItem2);
                                        Unit unit34 = Unit.INSTANCE;
                                    }
                                    Unit unit35 = Unit.INSTANCE;
                                }
                                AdPage page = postList.getPage();
                                Intrinsics.checkExpressionValueIsNotNull(page, "itemData.page");
                                if (page.getAdType() == 1) {
                                    if (textView11 != null) {
                                        AdPage page2 = postList.getPage();
                                        Intrinsics.checkExpressionValueIsNotNull(page2, "itemData.page");
                                        textView11.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(page2.getViewsNum())));
                                    }
                                    if (textView10 != null) {
                                        textView10.setText(R.string.post_ad_view_detail);
                                        Unit unit36 = Unit.INSTANCE;
                                    }
                                    if (textView17 != null) {
                                        textView17.setText(R.string.common_ad);
                                        Unit unit37 = Unit.INSTANCE;
                                    }
                                } else {
                                    AdPage page3 = postList.getPage();
                                    Intrinsics.checkExpressionValueIsNotNull(page3, "itemData.page");
                                    if (page3.getAdType() == 2) {
                                        AdPage page4 = postList.getPage();
                                        Intrinsics.checkExpressionValueIsNotNull(page4, "itemData.page");
                                        if (Intrinsics.areEqual("1", page4.getAdLinkType())) {
                                            if (textView11 != null) {
                                                AdPage page5 = postList.getPage();
                                                Intrinsics.checkExpressionValueIsNotNull(page5, "itemData.page");
                                                textView11.setText(AppUtil.getString(R.string.post_ad_download_num, FormatUtil.formatNumOverTenThousand(page5.getViewsNum())));
                                            }
                                            if (textView10 != null) {
                                                textView10.setText(R.string.post_ad_download);
                                                Unit unit38 = Unit.INSTANCE;
                                            }
                                        } else {
                                            AdPage page6 = postList.getPage();
                                            Intrinsics.checkExpressionValueIsNotNull(page6, "itemData.page");
                                            if (Intrinsics.areEqual("2", page6.getAdLinkType())) {
                                                if (textView11 != null) {
                                                    AdPage page7 = postList.getPage();
                                                    Intrinsics.checkExpressionValueIsNotNull(page7, "itemData.page");
                                                    textView11.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(page7.getViewsNum())));
                                                }
                                                if (textView10 != null) {
                                                    textView10.setText(R.string.post_ad_view_detail);
                                                    Unit unit39 = Unit.INSTANCE;
                                                }
                                            }
                                        }
                                        if (textView17 != null) {
                                            textView17.setText(R.string.common_ad);
                                            Unit unit40 = Unit.INSTANCE;
                                        }
                                    } else {
                                        AdPage page8 = postList.getPage();
                                        Intrinsics.checkExpressionValueIsNotNull(page8, "itemData.page");
                                        if (page8.getAdType() == 3) {
                                            if (textView11 != null) {
                                                AdPage page9 = postList.getPage();
                                                Intrinsics.checkExpressionValueIsNotNull(page9, "itemData.page");
                                                textView11.setText(AppUtil.getString(R.string.post_ad_view_num, FormatUtil.formatNumOverTenThousand(page9.getViewsNum())));
                                            }
                                            if (textView10 != null) {
                                                textView10.setText(R.string.post_ad_web_app_start);
                                                Unit unit41 = Unit.INSTANCE;
                                            }
                                            if (textView17 != null) {
                                                textView17.setText(R.string.common_web_app);
                                                Unit unit42 = Unit.INSTANCE;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        textView = textView28;
                    }
                    if (postList.getPostType() != 4 || textView28 == null) {
                        textView = textView28;
                        i7 = 8;
                    } else {
                        textView = textView28;
                        i7 = 8;
                        textView.setVisibility(8);
                    }
                    if (textView6 != null) {
                        textView6.setVisibility(0);
                    }
                    if (textView29 != null) {
                        textView29.setVisibility(i7);
                    }
                    String title = !TextUtils.isEmpty(postList.getTitle()) ? postList.getTitle() : postList.getDescription();
                    Intrinsics.checkExpressionValueIsNotNull(title, "title");
                    indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) title, "</", 0, false, 6, (Object) null);
                    if (indexOf$default2 != -1) {
                        RichText.fromHtml(title).into(textView6);
                    } else if (textView6 != null) {
                        textView6.setText(title);
                    }
                    Vector vector11 = this.vector;
                    if ((vector11 != null ? Integer.valueOf(vector11.size()) : null).intValue() > 0) {
                        Vector<View> vector12 = new Vector();
                        vector12.addAll(this.vector);
                        postVideoItem = null;
                        for (View view6 : vector12) {
                            if (view6 instanceof PostVideoItem) {
                                postVideoItem = (PostVideoItem) view6;
                            }
                        }
                        Unit unit43 = Unit.INSTANCE;
                        vector12.clear();
                        Unit unit44 = Unit.INSTANCE;
                        if (postVideoItem != null) {
                            Vector vector13 = this.vector;
                            Boolean.valueOf((vector13 != null ? Boolean.valueOf(vector13.removeElement(postVideoItem)) : null).booleanValue());
                        }
                    } else {
                        postVideoItem = null;
                    }
                    if (postVideoItem == null) {
                        Context mContext5 = this.mContext;
                        Intrinsics.checkExpressionValueIsNotNull(mContext5, "mContext");
                        postVideoItem = new PostVideoItem(mContext5, this.postEvenOneTabVideoListManger);
                    } else {
                        ViewParent parent4 = postVideoItem != null ? postVideoItem.getParent() : null;
                        if (parent4 != null && (parent4 instanceof LinearLayout)) {
                            ((LinearLayout) parent4).removeAllViews();
                        }
                    }
                    if (postVideoItem != null) {
                        postVideoItem.setPostListData(postList);
                        Unit unit45 = Unit.INSTANCE;
                    }
                    if (linearLayout9 != 0) {
                        linearLayout9.removeAllViews();
                        Unit unit46 = Unit.INSTANCE;
                    }
                    if (linearLayout9 != 0) {
                        linearLayout9.addView(postVideoItem);
                        Unit unit47 = Unit.INSTANCE;
                    }
                } else {
                    textView = textView28;
                    if (postList.getPicNum() > 1) {
                        if (!newPostTabListAdapter.isShowImageGuide || PreferencesUtil.getInstance().getBoolean("show_post_image_prompt")) {
                            relativeLayout = relativeLayout5;
                        } else {
                            relativeLayout = relativeLayout5;
                            if (relativeLayout5 != null) {
                                relativeLayout.setVisibility(0);
                            }
                            newPostTabListAdapter.currentPostion = getData().indexOf(postList);
                            newPostTabListAdapter.isShowImageGuide = false;
                        }
                        if (!newPostTabListAdapter.isShowImageGuide && !PreferencesUtil.getInstance().getBoolean("show_post_image_prompt") && newPostTabListAdapter.currentPostion == getData().indexOf(postList) && relativeLayout != null) {
                            i4 = 0;
                            relativeLayout.setVisibility(0);
                            if (textView6 != null) {
                                textView6.setVisibility(i4);
                            }
                            if (textView29 != null) {
                                textView29.setVisibility(8);
                            }
                            String title2 = TextUtils.isEmpty(postList.getTitle()) ? postList.getTitle() : postList.getDescription();
                            Intrinsics.checkExpressionValueIsNotNull(title2, "title");
                            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) title2, "</", 0, false, 6, (Object) null);
                            if (indexOf$default == -1) {
                                RichTextConfig.RichTextConfigBuild fromHtml = RichText.fromHtml(title2);
                                fromHtml.clickable(true);
                                fromHtml.into(textView6);
                            } else if (textView6 != null) {
                                textView6.setText(title2);
                            }
                            vector = this.vector;
                            if ((vector == null ? Integer.valueOf(vector.size()) : null).intValue() <= 0) {
                                Vector<View> vector14 = new Vector();
                                vector14.addAll(this.vector);
                                postImageItem = null;
                                for (View view7 : vector14) {
                                    if (view7 instanceof PostImageItem) {
                                        postImageItem = (PostImageItem) view7;
                                    }
                                }
                                Unit unit48 = Unit.INSTANCE;
                                vector14.clear();
                                Unit unit49 = Unit.INSTANCE;
                                if (postImageItem != null) {
                                    Vector vector15 = this.vector;
                                    Boolean.valueOf((vector15 != null ? Boolean.valueOf(vector15.removeElement(postImageItem)) : null).booleanValue());
                                }
                            } else {
                                postImageItem = null;
                            }
                            if (postImageItem != null) {
                                Context mContext6 = this.mContext;
                                Intrinsics.checkExpressionValueIsNotNull(mContext6, "mContext");
                                postImageItem = new PostImageItem(mContext6, this.newPostItemOnClickCallBack);
                            } else {
                                ViewParent parent5 = postImageItem != null ? postImageItem.getParent() : null;
                                if (parent5 != null && (parent5 instanceof LinearLayout)) {
                                    ((LinearLayout) parent5).removeAllViews();
                                }
                            }
                            ArrayList<ImageBean> arrayList2 = new ArrayList<>();
                            arrayList2.addAll(PostUtils.INSTANCE.createImageBeanList(postList));
                            if (postImageItem != null) {
                                postImageItem.setImageBeanList(postList, arrayList2, this.businessType);
                                Unit unit50 = Unit.INSTANCE;
                            }
                            if (linearLayout9 != null) {
                                linearLayout9.removeAllViews();
                                Unit unit51 = Unit.INSTANCE;
                            }
                            if (linearLayout9 != null) {
                                linearLayout9.addView(postImageItem);
                                Unit unit52 = Unit.INSTANCE;
                            }
                        }
                    }
                    i4 = 0;
                    if (textView6 != null) {
                    }
                    if (textView29 != null) {
                    }
                    if (TextUtils.isEmpty(postList.getTitle())) {
                    }
                    Intrinsics.checkExpressionValueIsNotNull(title2, "title");
                    indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) title2, "</", 0, false, 6, (Object) null);
                    if (indexOf$default == -1) {
                    }
                    vector = this.vector;
                    if ((vector == null ? Integer.valueOf(vector.size()) : null).intValue() <= 0) {
                    }
                    if (postImageItem != null) {
                    }
                    ArrayList<ImageBean> arrayList22 = new ArrayList<>();
                    arrayList22.addAll(PostUtils.INSTANCE.createImageBeanList(postList));
                    if (postImageItem != null) {
                    }
                    if (linearLayout9 != null) {
                    }
                    if (linearLayout9 != null) {
                    }
                }
                if (postList.isAd()) {
                    if (TextUtils.isEmpty(postList.getGroupName())) {
                        i5 = 8;
                        if (textView != null) {
                            textView.setVisibility(8);
                        }
                    } else {
                        i5 = 8;
                        if (textView != null) {
                            textView.setText(postList.getGroupName());
                        }
                    }
                    if (postList.getCommentTimes() >= 10000) {
                        if (textView22 != null) {
                            textView22.setText(FormatUtil.formatOne(Double.valueOf(postList.getCommentTimes() / ConstantUtils.MAX_ITEM_NUM)) + 'w');
                        }
                    } else if (textView22 != null) {
                        textView22.setText(String.valueOf(postList.getCommentTimes()));
                    }
                    if (postList.getGoodNum() < 10000) {
                        textView2 = textView23;
                        if (textView2 != null) {
                            textView2.setText(String.valueOf(postList.getGoodNum()));
                        }
                    } else if (textView23 != null) {
                        textView2 = textView23;
                        textView2.setText(FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + 'w');
                    } else {
                        textView2 = textView23;
                    }
                    if (!TextUtils.isEmpty(postList.getShareTimes())) {
                        String shareTimes = postList.getShareTimes();
                        Intrinsics.checkExpressionValueIsNotNull(shareTimes, "itemData.shareTimes");
                        int parseInt = Integer.parseInt(shareTimes);
                        if (parseInt >= 10000) {
                            if (textView24 != null) {
                                textView24.setText(FormatUtil.formatOne(Double.valueOf(parseInt / ConstantUtils.MAX_ITEM_NUM)) + 'w');
                            }
                        } else if (textView24 != null) {
                            textView24.setText(String.valueOf(parseInt));
                        }
                        baseViewHolder2 = baseViewHolder;
                    } else {
                        baseViewHolder2 = baseViewHolder;
                        if (baseViewHolder2 != null) {
                            baseViewHolder2.setText(R.id.text_share_num, "0");
                        }
                    }
                    int intValue = (postList != null ? Integer.valueOf(postList.getIsThumbUp()) : null).intValue();
                    if (intValue != 0) {
                        imageView = imageView12;
                        if (intValue == 1 && imageView != null) {
                            imageView.setImageResource(R.drawable.icon_new_post_zan_ok);
                            Unit unit53 = Unit.INSTANCE;
                        }
                    } else if (imageView12 != null) {
                        imageView = imageView12;
                        imageView.setImageResource(R.drawable.icon_new_post_zan);
                        Unit unit54 = Unit.INSTANCE;
                    } else {
                        imageView = imageView12;
                    }
                    if (postList.isMemberIsUp()) {
                        if (relativeLayout7 != null) {
                            relativeLayout2 = relativeLayout7;
                            relativeLayout2.setGravity(17);
                        } else {
                            relativeLayout2 = relativeLayout7;
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) (relativeLayout2 != null ? relativeLayout2.getLayoutParams() : null);
                        if (layoutParams != null) {
                            layoutParams.setMarginStart(50);
                        }
                        if (relativeLayout2 != null) {
                            relativeLayout2.setLayoutParams(layoutParams);
                        }
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) (relativeLayout8 != null ? relativeLayout8.getLayoutParams() : null);
                        if (layoutParams2 != null) {
                            layoutParams2.rightMargin = 50;
                        }
                        if (relativeLayout8 != null) {
                            relativeLayout8.setLayoutParams(layoutParams2);
                        }
                        if (relativeLayout10 != null) {
                            relativeLayout10.setVisibility(0);
                        }
                    } else {
                        relativeLayout2 = relativeLayout7;
                        if (relativeLayout2 != null) {
                            relativeLayout2.setGravity(5);
                        }
                        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) (relativeLayout2 != null ? relativeLayout2.getLayoutParams() : null);
                        if (layoutParams3 != null) {
                            layoutParams3.setMarginStart(0);
                        }
                        if (relativeLayout2 != null) {
                            relativeLayout2.setLayoutParams(layoutParams3);
                        }
                        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) (relativeLayout8 != 0 ? relativeLayout8.getLayoutParams() : null);
                        if (layoutParams4 != null) {
                            layoutParams4.rightMargin = 0;
                        }
                        if (relativeLayout8 != 0) {
                            relativeLayout8.setLayoutParams(layoutParams4);
                        }
                        if (relativeLayout10 != null) {
                            relativeLayout10.setVisibility(i5);
                        }
                    }
                    boolean isAlreadyPaid = postList.isAlreadyPaid();
                    if (isAlreadyPaid) {
                        if (textView27 != null) {
                            textView27.setVisibility(0);
                        }
                        if (textView27 != null) {
                            textView27.setBackground(ContextCompat.getDrawable(newPostTabListAdapter.mContext, R.drawable.common_shape_solid_corner30_f0f7ee));
                        }
                        Drawable drawable = ContextCompat.getDrawable(newPostTabListAdapter.mContext, R.drawable.post_alread_pay);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            Unit unit55 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setCompoundDrawables(drawable, null, null, null);
                            Unit unit56 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setTextColor(ContextCompat.getColor(newPostTabListAdapter.mContext, R.color.color_2ADD93));
                            Unit unit57 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setText(AppUtil.getString(R.string.post_already_pay));
                        }
                    } else if (!isAlreadyPaid && postList.getPrice() > 0) {
                        if (textView27 != null) {
                            textView27.setVisibility(0);
                        }
                        if (textView27 != null) {
                            textView27.setBackground(ContextCompat.getDrawable(newPostTabListAdapter.mContext, R.drawable.common_shape_solid_corner30_grey));
                        }
                        Drawable drawable2 = ContextCompat.getDrawable(newPostTabListAdapter.mContext, R.drawable.post_need_pay);
                        if (drawable2 != null) {
                            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                            Unit unit58 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setCompoundDrawables(drawable2, null, null, null);
                            Unit unit59 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setTextColor(ContextCompat.getColor(newPostTabListAdapter.mContext, R.color.color_FC4C7B));
                            Unit unit60 = Unit.INSTANCE;
                        }
                        if (textView27 != null) {
                            textView27.setText(FormatUtil.formatTomato2RMB(postList.getPrice()));
                        }
                    } else if (textView27 != null) {
                        textView27.setVisibility(i5);
                    }
                    if (postList.getSerialGroupId() == 0) {
                        i6 = 0;
                        if (textView14 != null) {
                            textView14.setVisibility(i5);
                        }
                    } else if (textView14 != null) {
                        i6 = 0;
                        textView14.setVisibility(0);
                    } else {
                        i6 = 0;
                    }
                    if (postList.getOriginalFlag() == 1) {
                        if (textView20 != null) {
                            textView20.setVisibility(i6);
                        }
                    } else if (textView20 != null) {
                        textView20.setVisibility(i5);
                    }
                    if (!TextUtils.isEmpty(postList.getDayInfo())) {
                        if (getData().indexOf(postList) == 0) {
                            if (textView25 != null) {
                                textView25.setVisibility(0);
                            }
                            if (textView25 != null) {
                                String dayInfo = postList.getDayInfo();
                                if (dayInfo == null) {
                                    dayInfo = str;
                                }
                                textView25.setText(dayInfo);
                            }
                        } else {
                            z = true;
                            PostList get = getData().get(getData().indexOf(postList) - 1);
                            Intrinsics.checkExpressionValueIsNotNull(get, "get");
                            if (!Intrinsics.areEqual(get.getDayInfo(), postList.getDayInfo())) {
                                if (textView25 != null) {
                                    textView25.setVisibility(0);
                                }
                                if (textView25 != null) {
                                    String dayInfo2 = postList.getDayInfo();
                                    if (dayInfo2 == null) {
                                        dayInfo2 = str;
                                    }
                                    textView25.setText(dayInfo2);
                                }
                            }
                            tagList = postList.getTagList();
                            if (tagList != null && !tagList.isEmpty()) {
                                z = false;
                            }
                            if (z) {
                                if (relativeLayout6 != null) {
                                    z2 = false;
                                    relativeLayout6.setVisibility(0);
                                } else {
                                    z2 = false;
                                }
                                if (recyclerView2 != null) {
                                    Context context3 = newPostTabListAdapter.mContext;
                                    int i8 = z2 ? 1 : 0;
                                    int i9 = z2 ? 1 : 0;
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context3, i8, z2);
                                    recyclerView = recyclerView2;
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                } else {
                                    recyclerView = recyclerView2;
                                }
                                PublishHotTagAdapter publishHotTagAdapter = new PublishHotTagAdapter(newPostTabListAdapter.businessType);
                                if (recyclerView != null) {
                                    recyclerView.setAdapter(publishHotTagAdapter);
                                }
                                if (tagList.size() > 10) {
                                    List<Tag> subList = tagList.subList(0, 10);
                                    Intrinsics.checkExpressionValueIsNotNull(subList, "tagList.subList(0, 10)");
                                    publishHotTagAdapter.setNewData(subList);
                                } else {
                                    publishHotTagAdapter.setNewData(tagList);
                                }
                                publishHotTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$convert$3
                                    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                                    public final void onItemClick(final BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view8, final int i10) {
                                        if (Intrinsics.areEqual(NewPostTabListAdapter.this.businessType, "review_post") || Intrinsics.areEqual(NewPostTabListAdapter.this.businessType, "review_post_pre")) {
                                            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(((BaseQuickAdapter) NewPostTabListAdapter.this).mContext);
                                            customAlertDialog.bottomButtonVisiblity(0);
                                            customAlertDialog.setMessage(AppUtil.getString(R.string.publish_delete_tag_tip));
                                            customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
                                            customAlertDialog.setConfirmButtonTextColor(R.color.white);
                                            customAlertDialog.setConfirmButtonBackgroundDrable(ContextCompat.getDrawable(((BaseQuickAdapter) NewPostTabListAdapter.this).mContext, R.drawable.common_selector_solid_corner5_coloraccent));
                                            customAlertDialog.setCancelButtonTextColor(R.color.text_light);
                                            customAlertDialog.setCancleButtonBackgroundDrable(ContextCompat.getDrawable(((BaseQuickAdapter) NewPostTabListAdapter.this).mContext, R.drawable.common_shape_solid_corner5_color_ebebebeb));
                                            customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$convert$3.1
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view9) {
                                                    NewPostItemOnClickCallBack newPostItemOnClickCallBack2;
                                                    customAlertDialog.dismiss();
                                                    Tag tag = (Tag) baseQuickAdapter.getItem(i10);
                                                    if (tag == null || (newPostItemOnClickCallBack2 = NewPostTabListAdapter.this.newPostItemOnClickCallBack) == null) {
                                                        return;
                                                    }
                                                    newPostItemOnClickCallBack2.itemTagDelete(tag, postList.getId(), i10, NewPostTabListAdapter.this.getData().indexOf(postList));
                                                }
                                            });
                                            customAlertDialog.setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$convert$3.2
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view9) {
                                                    CustomAlertDialog.this.dismiss();
                                                }
                                            });
                                            customAlertDialog.show();
                                            return;
                                        }
                                        TagPostListAct.Companion companion = TagPostListAct.Companion;
                                        Context mContext7 = ((BaseQuickAdapter) NewPostTabListAdapter.this).mContext;
                                        Intrinsics.checkExpressionValueIsNotNull(mContext7, "mContext");
                                        Object item = baseQuickAdapter.getItem(i10);
                                        if (item == null) {
                                            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.Tag");
                                        }
                                        companion.startAct(mContext7, (Tag) item);
                                    }
                                });
                            } else if (relativeLayout6 != null) {
                                relativeLayout6.setVisibility(i5);
                            }
                        }
                    }
                    z = true;
                    tagList = postList.getTagList();
                    if (tagList != null) {
                        z = false;
                    }
                    if (z) {
                    }
                } else {
                    baseViewHolder2 = baseViewHolder;
                    relativeLayout2 = relativeLayout7;
                    imageView = imageView12;
                    textView2 = textView23;
                    i5 = 8;
                }
                str3 = newPostTabListAdapter.businessType;
                if (str3 != null) {
                    if (Intrinsics.areEqual(str3, "publish_n")) {
                        return;
                    }
                    if (Intrinsics.areEqual(newPostTabListAdapter.businessType, "publish_y")) {
                        imageView2 = imageView13;
                        if (imageView13 != null) {
                            imageView2.setVisibility(0);
                        }
                        if (imageView11 != null) {
                            imageView11.setVisibility(i5);
                        }
                    } else {
                        imageView2 = imageView13;
                    }
                    if (Intrinsics.areEqual(newPostTabListAdapter.businessType, "review_post") || Intrinsics.areEqual(newPostTabListAdapter.businessType, "review_post_pre")) {
                        if (linearLayout6 != null) {
                            linearLayout6.setVisibility(i5);
                        }
                        if (textView21 != null) {
                            textView21.setVisibility(0);
                        }
                        if (imageView2 != null) {
                            imageView2.setVisibility(i5);
                        }
                        if (imageView11 != null) {
                            imageView11.setVisibility(i5);
                        }
                        if (view2 != null) {
                            view2.setVisibility(i5);
                        }
                        if (!TextUtils.isEmpty(postList.getMemberSign())) {
                            if (textView21 != null) {
                                textView21.setText(postList.getMemberSign());
                            }
                        } else if (textView21 != null) {
                            textView21.setText(AppUtil.getString(R.string.review_signature));
                        }
                        if (postList.getPostType() == 4) {
                            if (textView19 != null) {
                                textView19.setVisibility(0);
                            }
                        } else if (textView19 != null) {
                            textView19.setVisibility(i5);
                        }
                    }
                    Unit unit61 = Unit.INSTANCE;
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.image_head);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.relate_share);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.relate_conmment);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.image_post_detail);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.image_post_delete);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.text_tag);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.relative_image_guide);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.relate_reward);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.text_title);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.text_content);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.image_up);
                }
                if (baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.image_porter);
                }
                if (!Intrinsics.areEqual(newPostTabListAdapter.businessType, "review_post") && !Intrinsics.areEqual(newPostTabListAdapter.businessType, "review_post_pre") && baseViewHolder2 != null) {
                    baseViewHolder2.addOnClickListener(R.id.text_need_pay);
                }
                if (relativeLayout2 != null) {
                    return;
                }
                relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$convert$5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view8) {
                        NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = NewPostTabListAdapter.this.newPostItemOnClickCallBack;
                        Integer num = null;
                        Boolean valueOf2 = newPostItemOnClickCallBack2 != null ? Boolean.valueOf(newPostItemOnClickCallBack2.itemClickZan(postList, NewPostTabListAdapter.this.getData().indexOf(postList))) : null;
                        if (valueOf2 != null) {
                            valueOf2.booleanValue();
                            if (!valueOf2.booleanValue()) {
                                return;
                            }
                            PostList postList2 = postList;
                            if (postList2 != null) {
                                num = Integer.valueOf(postList2.getIsThumbUp());
                            }
                            if (num.intValue() == 0) {
                                postList.setIsThumbUp(1);
                                PostList postList3 = postList;
                                postList3.setGoodNum(postList3.getGoodNum() + 1);
                                if (postList.getGoodNum() >= 10000) {
                                    TextView textView30 = textView2;
                                    if (textView30 != null) {
                                        textView30.setText(FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + (char) 19975);
                                    }
                                } else {
                                    TextView textView31 = textView2;
                                    if (textView31 != null) {
                                        textView31.setText(String.valueOf(postList.getGoodNum()));
                                    }
                                }
                                ImageView imageView14 = imageView;
                                if (imageView14 == null) {
                                    return;
                                }
                                imageView14.setImageResource(R.drawable.icon_new_post_zan_ok);
                                return;
                            }
                            PostList postList4 = postList;
                            if (postList4 == null) {
                                return;
                            }
                            postList4.setIsThumbUp(0);
                            PostList postList5 = postList;
                            postList5.setGoodNum(postList5.getGoodNum() - 1);
                            if (postList.getGoodNum() >= 10000) {
                                TextView textView32 = textView2;
                                if (textView32 != null) {
                                    textView32.setText(FormatUtil.formatOne(Double.valueOf(postList.getGoodNum() / ConstantUtils.MAX_ITEM_NUM)) + (char) 19975);
                                }
                            } else {
                                TextView textView33 = textView2;
                                if (textView33 != null) {
                                    textView33.setText(String.valueOf(postList.getGoodNum()));
                                }
                            }
                            ImageView imageView15 = imageView;
                            if (imageView15 == null) {
                                return;
                            }
                            imageView15.setImageResource(R.drawable.icon_new_post_zan);
                        }
                    }
                });
                Unit unit62 = Unit.INSTANCE;
                return;
            }
            i2 = 8;
            if (postList.getGoldPorterFlag() == 1) {
            }
            if (!postList.isMemberIsAnchor()) {
            }
            if (textView26 != null) {
            }
            postType = postList.getPostType();
            if (postType == 1) {
            }
            if (postList.isAd()) {
            }
            str3 = newPostTabListAdapter.businessType;
            if (str3 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (baseViewHolder2 != null) {
            }
            if (!Intrinsics.areEqual(newPostTabListAdapter.businessType, "review_post")) {
                baseViewHolder2.addOnClickListener(R.id.text_need_pay);
            }
            if (relativeLayout2 != null) {
            }
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        PostList postList = getData().get(i);
        Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
        if (valueOf != null && valueOf.intValue() == R.id.image_head) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack == null) {
                return;
            }
            newPostItemOnClickCallBack.itemHead(postList);
        } else if (valueOf != null && valueOf.intValue() == R.id.relate_share) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack2 == null) {
                return;
            }
            newPostItemOnClickCallBack2.itemShare(postList);
        } else if (valueOf != null && valueOf.intValue() == R.id.relate_conmment) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack3 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack3 == null) {
                return;
            }
            newPostItemOnClickCallBack3.itemConmment(postList, i, view);
        } else if (valueOf != null && valueOf.intValue() == R.id.image_post_detail) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack4 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack4 == null) {
                return;
            }
            newPostItemOnClickCallBack4.itemShowAuthInfo(postList, i);
        } else if (valueOf != null && valueOf.intValue() == R.id.image_post_delete) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack5 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack5 == null) {
                return;
            }
            newPostItemOnClickCallBack5.itemDelete(postList, i, view);
        } else if (valueOf != null && valueOf.intValue() == R.id.text_tag) {
            NewPostItemOnClickCallBack newPostItemOnClickCallBack6 = this.newPostItemOnClickCallBack;
            if (newPostItemOnClickCallBack6 == null) {
                return;
            }
            newPostItemOnClickCallBack6.itemCircle(postList);
        } else {
            int i2 = 8;
            if (valueOf != null && valueOf.intValue() == R.id.relative_image_guide) {
                view.setVisibility(8);
                PreferencesUtil.getInstance().putBoolean("show_post_image_prompt", true);
            } else if (valueOf != null && valueOf.intValue() == R.id.relate_reward) {
                NewPostItemOnClickCallBack newPostItemOnClickCallBack7 = this.newPostItemOnClickCallBack;
                if (newPostItemOnClickCallBack7 == null) {
                    return;
                }
                newPostItemOnClickCallBack7.itemNeedPay(postList);
            } else if (valueOf != null && valueOf.intValue() == R.id.text_title) {
                NewPostItemOnClickCallBack newPostItemOnClickCallBack8 = this.newPostItemOnClickCallBack;
                if (newPostItemOnClickCallBack8 == null) {
                    return;
                }
                newPostItemOnClickCallBack8.itemClick(postList, view, i);
            } else if (valueOf != null && valueOf.intValue() == R.id.text_content) {
                NewPostItemOnClickCallBack newPostItemOnClickCallBack9 = this.newPostItemOnClickCallBack;
                if (newPostItemOnClickCallBack9 == null) {
                    return;
                }
                newPostItemOnClickCallBack9.itemClick(postList, view, i);
            } else if (valueOf != null && valueOf.intValue() == R.id.text_need_pay) {
                Intrinsics.checkExpressionValueIsNotNull(postList, "postList");
                if (postList.isAlreadyPaid()) {
                    return;
                }
                PostUtils postUtils = PostUtils.INSTANCE;
                Context mContext = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                postUtils.showImageNeedPayDialog(mContext, String.valueOf(postList.getPrice()), String.valueOf(postList.getId()), postList.getSubscribeSwitch() == 1 ? postList.getMemberId() : 0, new NewPostTabListAdapter$onRecyclerItemChildClick$1(postList, baseQuickAdapter, i), NewPostTabListAdapter$onRecyclerItemChildClick$2.INSTANCE);
            } else if (valueOf != null && valueOf.intValue() == R.id.image_up) {
                ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(this.mContext);
                if (postList != null && postList.getMemberIsOriginal() == 1) {
                    i2 = 7;
                } else if (postList == null || postList.getUpLevel() != 1) {
                    if (postList != null && postList.getUpLevel() == 2) {
                        i2 = 9;
                    } else if (postList != null && postList.getUpLevel() == 3) {
                        i2 = 11;
                    } else if (postList == null || postList.getUpLevel() != 4) {
                        i2 = (postList == null || postList.getUpLevel() != 5) ? 0 : 13;
                    } else {
                        i2 = 12;
                    }
                }
                showUpPopWindow.showDown(view, i2);
            } else if (valueOf == null || valueOf.intValue() != R.id.image_porter) {
            } else {
                new ShowUpPopWindow(this.mContext).showDown(view, 6);
            }
        }
    }

    public final void setBusinessType(String str) {
        this.businessType = str;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder holder) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.onViewRecycled((NewPostTabListAdapter) holder);
        AsyncKt.doAsync$default(this, null, new NewPostTabListAdapter$onViewRecycled$1(this, holder), 1, null);
    }
}
