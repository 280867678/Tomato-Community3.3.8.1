package com.one.tomato.mvp.p080ui.post.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.one.tomato.R$id;
import com.one.tomato.adapter.GameDialogAdapter;
import com.one.tomato.adapter.ImageGridAdapter;
import com.one.tomato.adapter.PublishHotTagAdapter;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.GamePlayDialog;
import com.one.tomato.dialog.ShowUpPopWindow;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact;
import com.one.tomato.mvp.p080ui.post.item.PostSerializeItem;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.TagPostListAct;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.p085ui.tag.TagSelectActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import com.tomatolive.library.utils.DateUtils;
import com.zzhoujay.richtext.RichText;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: NewCommentHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView */
/* loaded from: classes3.dex */
public final class NewCommentHeadView extends RelativeLayout {
    private HashMap _$_findViewCache;
    private Fragment fragment;
    private LinearLayout.LayoutParams gridParams2;
    private LinearLayout.LayoutParams gridParams3;
    private PostList intentPostList;
    private boolean isReviewPost;
    private IPostCommentContact itemToViewCallBack;
    private int logzhu;
    private PostSerializeItem serializeItem;
    private LinearLayout.LayoutParams singleHeightParams;
    private LinearLayout.LayoutParams singleWidthParams;
    private PublishHotTagAdapter tagAdapter;
    private String timeSortType;

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

    public final void setItemToViewCallBack(IPostCommentContact itemToViewCallBack) {
        Intrinsics.checkParameterIsNotNull(itemToViewCallBack, "itemToViewCallBack");
        this.itemToViewCallBack = itemToViewCallBack;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView(Context context, PostList postList, IPostCommentContact itemToViewCallBack, Fragment fragment) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(itemToViewCallBack, "itemToViewCallBack");
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        this.timeSortType = "DESC";
        LayoutInflater.from(context).inflate(R.layout.new_detail_item_header, this);
        this.intentPostList = postList;
        this.itemToViewCallBack = itemToViewCallBack;
        this.fragment = fragment;
        PostUtils.INSTANCE.getWidthAndHegiht();
        PostUtils.INSTANCE.getScreenWidth();
        this.singleWidthParams = PostUtils.INSTANCE.getSingleWidthParams();
        this.singleHeightParams = PostUtils.INSTANCE.getSingleHeightParams();
        this.gridParams2 = PostUtils.INSTANCE.getGridParams2();
        this.gridParams3 = PostUtils.INSTANCE.getGridParams3();
        initView(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attrs, "attrs");
        this.timeSortType = "DESC";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView(Context context, AttributeSet attrs, int i) {
        super(context, attrs, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attrs, "attrs");
        this.timeSortType = "DESC";
    }

    private final void initView(final Context context) {
        refreshData(this.intentPostList);
        addGame();
        ((TextView) _$_findCachedViewById(R$id.tv_sort_type)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String str;
                IPostCommentContact iPostCommentContact;
                String str2;
                str = NewCommentHeadView.this.timeSortType;
                if (Intrinsics.areEqual("DESC", str)) {
                    NewCommentHeadView.this.timeSortType = "ASC";
                    Drawable drawable = context.getResources().getDrawable(R.drawable.post_sort_hot);
                    ((TextView) NewCommentHeadView.this._$_findCachedViewById(R$id.tv_sort_type)).setText(R.string.post_comment_hot);
                    ((TextView) NewCommentHeadView.this._$_findCachedViewById(R$id.tv_sort_type)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                } else {
                    NewCommentHeadView.this.timeSortType = "DESC";
                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.post_sort_last);
                    ((TextView) NewCommentHeadView.this._$_findCachedViewById(R$id.tv_sort_type)).setText(R.string.post_comment_last);
                    ((TextView) NewCommentHeadView.this._$_findCachedViewById(R$id.tv_sort_type)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                }
                iPostCommentContact = NewCommentHeadView.this.itemToViewCallBack;
                if (iPostCommentContact != null) {
                    str2 = NewCommentHeadView.this.timeSortType;
                    iPostCommentContact.callSortType(str2);
                }
            }
        });
        ((Switch) _$_findCachedViewById(R$id.switchBtn)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IPostCommentContact iPostCommentContact;
                int i;
                Switch switchBtn = (Switch) NewCommentHeadView.this._$_findCachedViewById(R$id.switchBtn);
                Intrinsics.checkExpressionValueIsNotNull(switchBtn, "switchBtn");
                if (switchBtn.isChecked()) {
                    NewCommentHeadView.this.logzhu = 1;
                } else {
                    NewCommentHeadView.this.logzhu = 0;
                }
                iPostCommentContact = NewCommentHeadView.this.itemToViewCallBack;
                if (iPostCommentContact != null) {
                    i = NewCommentHeadView.this.logzhu;
                    iPostCommentContact.callLouZhu(i);
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_post_head)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostList postList;
                postList = NewCommentHeadView.this.intentPostList;
                if (postList != null) {
                    NewMyHomePageActivity.Companion.startActivity(context, postList.getMemberId());
                }
            }
        });
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back_person);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context context2 = context;
                    if (context2 instanceof Activity) {
                        ((Activity) context2).onBackPressed();
                    }
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.text_tag)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostList postList;
                PostList postList2;
                CircleDetail circleDetail = new CircleDetail();
                postList = NewCommentHeadView.this.intentPostList;
                circleDetail.setName(postList != null ? postList.getGroupName() : null);
                postList2 = NewCommentHeadView.this.intentPostList;
                circleDetail.setId(postList2 != null ? postList2.getGroupId() : 0);
                CircleSingleActivity.startActivity(context, circleDetail);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.text_foucs)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IPostCommentContact iPostCommentContact;
                iPostCommentContact = NewCommentHeadView.this.itemToViewCallBack;
                if (iPostCommentContact != null) {
                    iPostCommentContact.callFoucs();
                }
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_click_all);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NewCommentHeadView.this.clickPostPay();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_need_pay);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$8

                /* compiled from: NewCommentHeadView.kt */
                /* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$8$1 */
                /* loaded from: classes3.dex */
                static final class C26391 extends Lambda implements Functions<Unit> {
                    C26391() {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
                        mo6822invoke();
                        return Unit.INSTANCE;
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke  reason: collision with other method in class */
                    public final void mo6822invoke() {
                        PostList postList;
                        PostList postList2;
                        postList = NewCommentHeadView.this.intentPostList;
                        if (postList == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        postList.setAlreadyPaid(true);
                        NewCommentHeadView newCommentHeadView = NewCommentHeadView.this;
                        postList2 = newCommentHeadView.intentPostList;
                        newCommentHeadView.refreshData(postList2);
                    }
                }

                /* compiled from: NewCommentHeadView.kt */
                /* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$8$2 */
                /* loaded from: classes3.dex */
                static final class C26402 extends Lambda implements Functions<Unit> {
                    public static final C26402 INSTANCE = new C26402();

                    C26402() {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke  reason: collision with other method in class */
                    public final void mo6822invoke() {
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
                        mo6822invoke();
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0043, code lost:
                    r2 = r7.this$0.intentPostList;
                 */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onClick(View view) {
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    PostList postList4;
                    PostList postList5;
                    postList = NewCommentHeadView.this.intentPostList;
                    if (postList == null || postList.isAlreadyPaid()) {
                        return;
                    }
                    PostUtils postUtils = PostUtils.INSTANCE;
                    Context context2 = context;
                    postList2 = NewCommentHeadView.this.intentPostList;
                    if (postList2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    String valueOf = String.valueOf(postList2.getPrice());
                    postList3 = NewCommentHeadView.this.intentPostList;
                    if (postList3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    String valueOf2 = String.valueOf(postList3.getId());
                    postList4 = NewCommentHeadView.this.intentPostList;
                    int i = 0;
                    if (postList4 != null && postList4.getSubscribeSwitch() == 1 && postList5 != null) {
                        i = postList5.getMemberId();
                    }
                    postUtils.showImageNeedPayDialog(context2, valueOf, valueOf2, i, new C26391(), C26402.INSTANCE);
                }
            });
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$9
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    PostList postList;
                    PostList postList2;
                    PostList postList3;
                    PostList postList4;
                    PostList postList5;
                    PostList postList6;
                    int i;
                    ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(context);
                    postList = NewCommentHeadView.this.intentPostList;
                    if (postList == null || postList.getMemberIsOriginal() != 1) {
                        postList2 = NewCommentHeadView.this.intentPostList;
                        if (postList2 == null || postList2.getUpLevel() != 1) {
                            postList3 = NewCommentHeadView.this.intentPostList;
                            if (postList3 == null || postList3.getUpLevel() != 2) {
                                postList4 = NewCommentHeadView.this.intentPostList;
                                if (postList4 == null || postList4.getUpLevel() != 3) {
                                    postList5 = NewCommentHeadView.this.intentPostList;
                                    if (postList5 == null || postList5.getUpLevel() != 4) {
                                        postList6 = NewCommentHeadView.this.intentPostList;
                                        i = (postList6 == null || postList6.getUpLevel() != 5) ? 0 : 13;
                                    } else {
                                        i = 12;
                                    }
                                } else {
                                    i = 11;
                                }
                            } else {
                                i = 9;
                            }
                        } else {
                            i = 8;
                        }
                    } else {
                        i = 7;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    showUpPopWindow.showDown(it2, i);
                }
            });
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_post_member_porter);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$initView$10
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    ShowUpPopWindow showUpPopWindow = new ShowUpPopWindow(context);
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    showUpPopWindow.showDown(it2, 6);
                }
            });
        }
    }

    public final void setIsReviewPost(boolean z) {
        this.isReviewPost = z;
    }

    public final void refreshData(final PostList postList) {
        String str;
        int indexOf$default;
        int indexOf$default2;
        ImageView imageView;
        if (postList != null) {
            this.intentPostList = postList;
            addSerializeItem();
            ImageLoaderUtil.loadHeadImage(getContext(), (ImageView) _$_findCachedViewById(R$id.iv_post_head), new ImageBean(postList.getAvatar()));
            TextView tv_post_name = (TextView) _$_findCachedViewById(R$id.tv_post_name);
            Intrinsics.checkExpressionValueIsNotNull(tv_post_name, "tv_post_name");
            tv_post_name.setText(postList.getName());
            UserPermissionUtil.getInstance().userLevelNickShow((ImageView) _$_findCachedViewById(R$id.iv_post_member_level_nick), new LevelBean(postList.getCurrentLevelIndex()));
            if (!TextUtils.isEmpty(postList.getCreateTime())) {
                Date formatTimeToDate = FormatUtil.formatTimeToDate(DateUtils.C_TIME_PATTON_DEFAULT, postList.getCreateTime());
                TextView tv_post_time = (TextView) _$_findCachedViewById(R$id.tv_post_time);
                Intrinsics.checkExpressionValueIsNotNull(tv_post_time, "tv_post_time");
                tv_post_time.setText(FormatUtil.formatTime(formatTimeToDate, new Date()));
            }
            boolean z = true;
            if (postList.getMemberId() == DBUtil.getMemberId()) {
                TextView text_foucs = (TextView) _$_findCachedViewById(R$id.text_foucs);
                Intrinsics.checkExpressionValueIsNotNull(text_foucs, "text_foucs");
                text_foucs.setVisibility(8);
            } else {
                TextView text_foucs2 = (TextView) _$_findCachedViewById(R$id.text_foucs);
                Intrinsics.checkExpressionValueIsNotNull(text_foucs2, "text_foucs");
                text_foucs2.setVisibility(0);
                if (postList.getIsAttention() == 1) {
                    ((TextView) _$_findCachedViewById(R$id.text_foucs)).setText(R.string.common_focus_y);
                } else {
                    ((TextView) _$_findCachedViewById(R$id.text_foucs)).setText(R.string.common_focus_n_add);
                }
            }
            TextView text_tag = (TextView) _$_findCachedViewById(R$id.text_tag);
            Intrinsics.checkExpressionValueIsNotNull(text_tag, "text_tag");
            text_tag.setText(postList.getGroupName());
            String str2 = "";
            if (!TextUtils.isEmpty(postList.getTitle())) {
                str = postList.getTitle();
                Intrinsics.checkExpressionValueIsNotNull(str, "it.title");
            } else if (!TextUtils.isEmpty(postList.getDescription())) {
                str = postList.getDescription();
                Intrinsics.checkExpressionValueIsNotNull(str, "it.description");
            } else {
                str = str2;
            }
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, "</", 0, false, 6, (Object) null);
            if (indexOf$default != -1) {
                RichText.fromHtml(str).into((TextView) _$_findCachedViewById(R$id.text_title));
                RichText.fromHtml(str).into((TextView) _$_findCachedViewById(R$id.text_pic_title));
            } else {
                TextView textView = (TextView) _$_findCachedViewById(R$id.text_title);
                if (textView != null) {
                    textView.setText(str);
                }
                TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_pic_title);
                if (textView2 != null) {
                    textView2.setText(str);
                }
            }
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
            flexboxLayoutManager.setFlexWrap(1);
            flexboxLayoutManager.setAlignItems(0);
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_tag);
            if (recyclerView != null) {
                recyclerView.setLayoutManager(flexboxLayoutManager);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList<Tag> tagList = postList.getTagList();
            if (!(tagList == null || tagList.isEmpty())) {
                if (postList.getTagList().size() > 10) {
                    List<Tag> subList = postList.getTagList().subList(0, 10);
                    Intrinsics.checkExpressionValueIsNotNull(subList, "postList.tagList.subList(0, 10)");
                    arrayList.addAll(subList);
                } else {
                    arrayList.addAll(postList.getTagList());
                }
            }
            if (arrayList.size() < 10) {
                Tag tag = new Tag(AppUtil.getString(R.string.publish_add_tag_jianyi));
                tag.setTagId(-10);
                arrayList.add(tag);
            }
            if (this.isReviewPost) {
                this.tagAdapter = new PublishHotTagAdapter("review_post");
            } else {
                this.tagAdapter = new PublishHotTagAdapter(null);
            }
            RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_tag);
            if (recyclerView2 != null) {
                recyclerView2.setAdapter(this.tagAdapter);
            }
            PublishHotTagAdapter publishHotTagAdapter = this.tagAdapter;
            if (publishHotTagAdapter != null) {
                publishHotTagAdapter.setNewData(arrayList);
                Unit unit = Unit.INSTANCE;
            }
            PublishHotTagAdapter publishHotTagAdapter2 = this.tagAdapter;
            if (publishHotTagAdapter2 != null) {
                publishHotTagAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                    public final void onItemClick(final BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, final int i) {
                        boolean z2;
                        Fragment fragment;
                        Object item = baseQuickAdapter.getItem(i);
                        if (item instanceof Tag) {
                            if (((Tag) item).getTagId() != -10) {
                                z2 = NewCommentHeadView.this.isReviewPost;
                                if (z2) {
                                    final CustomAlertDialog customAlertDialog = new CustomAlertDialog(NewCommentHeadView.this.getContext());
                                    customAlertDialog.bottomButtonVisiblity(0);
                                    customAlertDialog.setMessage(AppUtil.getString(R.string.publish_delete_tag_tip));
                                    customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
                                    customAlertDialog.setConfirmButtonTextColor(R.color.white);
                                    customAlertDialog.setConfirmButtonBackgroundDrable(ContextCompat.getDrawable(NewCommentHeadView.this.getContext(), R.drawable.common_selector_solid_corner5_coloraccent));
                                    customAlertDialog.setCancelButtonTextColor(R.color.white);
                                    customAlertDialog.setCancleButtonBackgroundDrable(ContextCompat.getDrawable(NewCommentHeadView.this.getContext(), R.drawable.common_shape_solid_corner5_color_ebebebeb));
                                    customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$1.1
                                        /* JADX WARN: Code restructure failed: missing block: B:3:0x0011, code lost:
                                            r0 = r3.this$0.this$0.itemToViewCallBack;
                                         */
                                        @Override // android.view.View.OnClickListener
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final void onClick(View view2) {
                                            IPostCommentContact iPostCommentContact;
                                            customAlertDialog.dismiss();
                                            Tag tag2 = (Tag) baseQuickAdapter.getItem(i);
                                            if (tag2 == null || iPostCommentContact == null) {
                                                return;
                                            }
                                            iPostCommentContact.itemTagDelete(tag2, postList.getId(), i);
                                        }
                                    });
                                    customAlertDialog.setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$1.2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            CustomAlertDialog.this.dismiss();
                                        }
                                    });
                                    customAlertDialog.show();
                                    return;
                                }
                                TagPostListAct.Companion companion = TagPostListAct.Companion;
                                Context context = NewCommentHeadView.this.getContext();
                                Intrinsics.checkExpressionValueIsNotNull(context, "context");
                                Object item2 = baseQuickAdapter.getItem(i);
                                if (item2 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.Tag");
                                }
                                companion.startAct(context, (Tag) item2);
                                return;
                            }
                            List<Object> data = baseQuickAdapter != null ? baseQuickAdapter.getData() : null;
                            if (data != null) {
                                fragment = NewCommentHeadView.this.fragment;
                                TagSelectActivity.startActivity(fragment, NewCommentHeadView.this.getContext(), (ArrayList) data, 1);
                                return;
                            }
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.Tag> /* = java.util.ArrayList<com.one.tomato.entity.db.Tag> */");
                        }
                    }
                });
                Unit unit2 = Unit.INSTANCE;
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_pic_title);
            if (textView3 != null) {
                textView3.setTypeface(Typeface.defaultFromStyle(0));
            }
            if (postList.getVipType() > 0) {
                ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_post_member_vip);
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
            } else {
                ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_post_member_vip);
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
            }
            if (postList.isMemberIsUp()) {
                ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                if (imageView4 != null) {
                    imageView4.setVisibility(0);
                }
                if (postList.getPrice() > 0) {
                    ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView5 != null) {
                        imageView5.setImageResource(R.drawable.my_up_y2);
                        Unit unit3 = Unit.INSTANCE;
                    }
                } else {
                    ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView6 != null) {
                        imageView6.setImageResource(R.drawable.my_up_y1);
                        Unit unit4 = Unit.INSTANCE;
                    }
                }
            } else {
                ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                if (imageView7 != null) {
                    imageView7.setVisibility(8);
                }
            }
            if (postList.isMemberIsUp()) {
                ImageView imageView8 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                if (imageView8 != null) {
                    imageView8.setVisibility(0);
                }
                if (postList != null && postList.getMemberIsOriginal() == 1) {
                    ImageView imageView9 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView9 != null) {
                        imageView9.setImageResource(R.drawable.up_original);
                        Unit unit5 = Unit.INSTANCE;
                    }
                } else if (postList != null && postList.getUpLevel() == 1) {
                    ImageView imageView10 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView10 != null) {
                        imageView10.setImageResource(R.drawable.my_up_y1);
                        Unit unit6 = Unit.INSTANCE;
                    }
                } else if (postList != null && postList.getUpLevel() == 2) {
                    ImageView imageView11 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView11 != null) {
                        imageView11.setImageResource(R.drawable.my_up_y2);
                        Unit unit7 = Unit.INSTANCE;
                    }
                } else if (postList != null && postList.getUpLevel() == 3) {
                    ImageView imageView12 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView12 != null) {
                        imageView12.setImageResource(R.drawable.my_up_y3_v);
                        Unit unit8 = Unit.INSTANCE;
                    }
                } else if (postList != null && postList.getUpLevel() == 4) {
                    ImageView imageView13 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                    if (imageView13 != null) {
                        imageView13.setImageResource(R.drawable.my_up_y4);
                        Unit unit9 = Unit.INSTANCE;
                    }
                } else if (postList != null && postList.getUpLevel() == 5 && (imageView = (ImageView) _$_findCachedViewById(R$id.image_post_member_up)) != null) {
                    imageView.setImageResource(R.drawable.my_up_y5);
                    Unit unit10 = Unit.INSTANCE;
                }
            } else {
                ImageView imageView14 = (ImageView) _$_findCachedViewById(R$id.image_post_member_up);
                if (imageView14 != null) {
                    imageView14.setVisibility(8);
                }
            }
            if (postList.getGoldPorterFlag() == 1) {
                ImageView imageView15 = (ImageView) _$_findCachedViewById(R$id.image_post_member_porter);
                if (imageView15 != null) {
                    imageView15.setVisibility(0);
                }
            } else {
                ImageView imageView16 = (ImageView) _$_findCachedViewById(R$id.image_post_member_porter);
                if (imageView16 != null) {
                    imageView16.setVisibility(8);
                }
            }
            if (postList.isMemberIsAnchor()) {
                ImageView imageView17 = (ImageView) _$_findCachedViewById(R$id.image_post_member_zb);
                if (imageView17 != null) {
                    imageView17.setVisibility(0);
                }
            } else {
                ImageView imageView18 = (ImageView) _$_findCachedViewById(R$id.image_post_member_zb);
                if (imageView18 != null) {
                    imageView18.setVisibility(8);
                }
            }
            if (postList.getOriginalFlag() == 1) {
                TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_original);
                if (textView4 != null) {
                    textView4.setVisibility(0);
                }
            } else {
                TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_original);
                if (textView5 != null) {
                    textView5.setVisibility(8);
                }
            }
            boolean isAlreadyPaid = postList.isAlreadyPaid();
            if (isAlreadyPaid) {
                ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setVisibility(0);
                ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.common_shape_solid_corner30_f0f7ee));
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.post_alread_pay);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setCompoundDrawables(drawable, null, null, null);
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setTextColor(ContextCompat.getColor(getContext(), R.color.color_2ADD93));
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setText(AppUtil.getString(R.string.post_already_pay));
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            } else if (!isAlreadyPaid && postList.getPrice() > 0) {
                ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setVisibility(0);
                ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.common_shape_solid_corner30_grey));
                Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.post_need_pay);
                if (drawable2 != null) {
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setCompoundDrawables(drawable2, null, null, null);
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setTextColor(ContextCompat.getColor(getContext(), R.color.color_FC4C7B));
                    ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setText(FormatUtil.formatTomato2RMB(postList.getPrice()));
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            } else {
                ((TextView) _$_findCachedViewById(R$id.text_need_pay)).setVisibility(8);
            }
            if (postList.getPostType() == 1) {
                FrameLayout rl_single_img = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                Intrinsics.checkExpressionValueIsNotNull(rl_single_img, "rl_single_img");
                rl_single_img.setVisibility(0);
                TextView text_pic_title = (TextView) _$_findCachedViewById(R$id.text_pic_title);
                Intrinsics.checkExpressionValueIsNotNull(text_pic_title, "text_pic_title");
                text_pic_title.setVisibility(0);
                NoScrollGridView head_gridView = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                Intrinsics.checkExpressionValueIsNotNull(head_gridView, "head_gridView");
                head_gridView.setVisibility(0);
                TextView tv_post_content = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                Intrinsics.checkExpressionValueIsNotNull(tv_post_content, "tv_post_content");
                tv_post_content.setVisibility(8);
                TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_title);
                if (textView6 != null) {
                    textView6.setVisibility(8);
                }
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(PostUtils.INSTANCE.createImageBeanList(postList));
                if (arrayList2.size() == 1) {
                    FrameLayout rl_single_img2 = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                    Intrinsics.checkExpressionValueIsNotNull(rl_single_img2, "rl_single_img");
                    rl_single_img2.setVisibility(0);
                    Object obj = arrayList2.get(0);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "imageBeanList[0]");
                    ImageBean imageBean = (ImageBean) obj;
                    int imageType = imageBean.getImageType();
                    if (imageType == 0 || imageType == 1) {
                        FrameLayout rl_single_img3 = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                        Intrinsics.checkExpressionValueIsNotNull(rl_single_img3, "rl_single_img");
                        rl_single_img3.setLayoutParams(this.singleWidthParams);
                        LinearLayout.LayoutParams layoutParams = this.singleWidthParams;
                        if (layoutParams == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        int i = layoutParams.width;
                        LinearLayout.LayoutParams layoutParams2 = this.singleWidthParams;
                        if (layoutParams2 != null) {
                            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(i, layoutParams2.height);
                            ImageView iv_single_image = (ImageView) _$_findCachedViewById(R$id.iv_single_image);
                            Intrinsics.checkExpressionValueIsNotNull(iv_single_image, "iv_single_image");
                            iv_single_image.setLayoutParams(layoutParams3);
                            LinearLayout.LayoutParams layoutParams4 = this.singleWidthParams;
                            if (layoutParams4 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            imageBean.setThumbWidth(layoutParams4.width);
                            LinearLayout.LayoutParams layoutParams5 = this.singleWidthParams;
                            if (layoutParams5 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            imageBean.setThumbHeight(layoutParams5.height);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    } else if (imageType == 2 || imageType == 3) {
                        FrameLayout rl_single_img4 = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                        Intrinsics.checkExpressionValueIsNotNull(rl_single_img4, "rl_single_img");
                        rl_single_img4.setLayoutParams(this.singleHeightParams);
                        LinearLayout.LayoutParams layoutParams6 = this.singleHeightParams;
                        if (layoutParams6 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        int i2 = layoutParams6.width;
                        LinearLayout.LayoutParams layoutParams7 = this.singleHeightParams;
                        if (layoutParams7 != null) {
                            RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(i2, layoutParams7.height);
                            ImageView iv_single_image2 = (ImageView) _$_findCachedViewById(R$id.iv_single_image);
                            Intrinsics.checkExpressionValueIsNotNull(iv_single_image2, "iv_single_image");
                            iv_single_image2.setLayoutParams(layoutParams8);
                            LinearLayout.LayoutParams layoutParams9 = this.singleHeightParams;
                            if (layoutParams9 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            imageBean.setThumbWidth(layoutParams9.width);
                            LinearLayout.LayoutParams layoutParams10 = this.singleHeightParams;
                            if (layoutParams10 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            imageBean.setThumbHeight(layoutParams10.height);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    if (imageType == 3) {
                        TextView tv_single_image = (TextView) _$_findCachedViewById(R$id.tv_single_image);
                        Intrinsics.checkExpressionValueIsNotNull(tv_single_image, "tv_single_image");
                        tv_single_image.setVisibility(0);
                        ((TextView) _$_findCachedViewById(R$id.tv_single_image)).setText(R.string.post_img_long);
                    }
                    if (postList.getPrice() > 0 && !postList.isAlreadyPaid() && postList.getMemberId() != DBUtil.getMemberId() && !this.isReviewPost) {
                        RelativeLayout relate_image_layer = (RelativeLayout) _$_findCachedViewById(R$id.relate_image_layer);
                        Intrinsics.checkExpressionValueIsNotNull(relate_image_layer, "relate_image_layer");
                        relate_image_layer.setVisibility(0);
                        ImageLoaderUtil.loadViewPagerOriginImageBlurs(getContext(), (ImageView) _$_findCachedViewById(R$id.iv_single_image), null, imageBean, new NewCommentHeadView$refreshData$$inlined$let$lambda$2(this, postList));
                    } else {
                        RelativeLayout relate_image_layer2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_image_layer);
                        Intrinsics.checkExpressionValueIsNotNull(relate_image_layer2, "relate_image_layer");
                        relate_image_layer2.setVisibility(8);
                        ImageLoaderUtil.loadRecyclerThumbImage(getContext(), (ImageView) _$_findCachedViewById(R$id.iv_single_image), imageBean);
                    }
                    ImageView iv_single_image3 = (ImageView) _$_findCachedViewById(R$id.iv_single_image);
                    Intrinsics.checkExpressionValueIsNotNull(iv_single_image3, "iv_single_image");
                    iv_single_image3.setTransitionName(imageBean.getImage());
                    ((ImageView) _$_findCachedViewById(R$id.iv_single_image)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            boolean z2;
                            boolean z3;
                            if (!PostList.this.isAd()) {
                                if (PostList.this.getPrice() > 0 && !PostList.this.isAlreadyPaid() && PostList.this.getMemberId() != DBUtil.getMemberId()) {
                                    z3 = this.isReviewPost;
                                    if (!z3) {
                                        this.clickPostPay();
                                        return;
                                    }
                                }
                                ImageShowActivity.Companion companion = ImageShowActivity.Companion;
                                Context context = this.getContext();
                                Intrinsics.checkExpressionValueIsNotNull(context, "context");
                                ArrayList<ImageBean> arrayList3 = arrayList2;
                                PostList postList2 = postList;
                                z2 = this.isReviewPost;
                                companion.startActivity(context, arrayList3, 0, postList2, z2);
                            }
                        }
                    });
                } else {
                    NoScrollGridView head_gridView2 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                    Intrinsics.checkExpressionValueIsNotNull(head_gridView2, "head_gridView");
                    head_gridView2.setVisibility(0);
                    if (arrayList2.size() > 9) {
                        NoScrollGridView head_gridView3 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                        Intrinsics.checkExpressionValueIsNotNull(head_gridView3, "head_gridView");
                        head_gridView3.setNumColumns(3);
                        NoScrollGridView head_gridView4 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                        Intrinsics.checkExpressionValueIsNotNull(head_gridView4, "head_gridView");
                        head_gridView4.setLayoutParams(this.gridParams3);
                        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getContext(), arrayList2, R.layout.item_img_grid);
                        NoScrollGridView head_gridView5 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                        Intrinsics.checkExpressionValueIsNotNull(head_gridView5, "head_gridView");
                        head_gridView5.setAdapter((ListAdapter) imageGridAdapter);
                    } else {
                        if (arrayList2.size() == 2 || arrayList2.size() == 4) {
                            NoScrollGridView head_gridView6 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                            Intrinsics.checkExpressionValueIsNotNull(head_gridView6, "head_gridView");
                            head_gridView6.setNumColumns(2);
                            NoScrollGridView head_gridView7 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                            Intrinsics.checkExpressionValueIsNotNull(head_gridView7, "head_gridView");
                            head_gridView7.setLayoutParams(this.gridParams2);
                        } else {
                            NoScrollGridView head_gridView8 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                            Intrinsics.checkExpressionValueIsNotNull(head_gridView8, "head_gridView");
                            head_gridView8.setNumColumns(3);
                            NoScrollGridView head_gridView9 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                            Intrinsics.checkExpressionValueIsNotNull(head_gridView9, "head_gridView");
                            head_gridView9.setLayoutParams(this.gridParams3);
                        }
                        ImageGridAdapter imageGridAdapter2 = new ImageGridAdapter(getContext(), arrayList2, R.layout.item_img_grid);
                        NoScrollGridView head_gridView10 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                        Intrinsics.checkExpressionValueIsNotNull(head_gridView10, "head_gridView");
                        head_gridView10.setAdapter((ListAdapter) imageGridAdapter2);
                        if (postList.getPrice() > 0 && !postList.isAlreadyPaid() && postList.getMemberId() != DBUtil.getMemberId() && !this.isReviewPost) {
                            imageGridAdapter2.setReward(true);
                        }
                    }
                    NoScrollGridView head_gridView11 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                    Intrinsics.checkExpressionValueIsNotNull(head_gridView11, "head_gridView");
                    head_gridView11.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$4
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                            boolean z2;
                            boolean z3;
                            if (PostList.this.getPrice() > 0 && !PostList.this.isAlreadyPaid() && PostList.this.getMemberId() != DBUtil.getMemberId()) {
                                z3 = this.isReviewPost;
                                if (!z3) {
                                    this.clickPostPay();
                                    return;
                                }
                            }
                            ImageShowActivity.Companion companion = ImageShowActivity.Companion;
                            Context context = this.getContext();
                            Intrinsics.checkExpressionValueIsNotNull(context, "context");
                            ArrayList<ImageBean> arrayList3 = arrayList2;
                            PostList postList2 = postList;
                            z2 = this.isReviewPost;
                            companion.startActivity(context, arrayList3, i3, postList2, z2);
                        }
                    });
                }
            } else if (postList.getPostType() == 3) {
                FrameLayout rl_single_img5 = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                Intrinsics.checkExpressionValueIsNotNull(rl_single_img5, "rl_single_img");
                rl_single_img5.setVisibility(8);
                NoScrollGridView head_gridView12 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                Intrinsics.checkExpressionValueIsNotNull(head_gridView12, "head_gridView");
                head_gridView12.setVisibility(8);
                TextView tv_post_content2 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                Intrinsics.checkExpressionValueIsNotNull(tv_post_content2, "tv_post_content");
                tv_post_content2.setVisibility(0);
                TextView text_pic_title2 = (TextView) _$_findCachedViewById(R$id.text_pic_title);
                Intrinsics.checkExpressionValueIsNotNull(text_pic_title2, "text_pic_title");
                text_pic_title2.setTypeface(Typeface.defaultFromStyle(1));
                TextView text_pic_title3 = (TextView) _$_findCachedViewById(R$id.text_pic_title);
                Intrinsics.checkExpressionValueIsNotNull(text_pic_title3, "text_pic_title");
                text_pic_title3.setVisibility(0);
                TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_title);
                if (textView7 != null) {
                    textView7.setVisibility(8);
                }
                if (postList.getPrice() > 0 && !postList.isAlreadyPaid() && !this.isReviewPost) {
                    if (postList.getMemberId() == DBUtil.getMemberId()) {
                        TextView tv_post_content3 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                        Intrinsics.checkExpressionValueIsNotNull(tv_post_content3, "tv_post_content");
                        tv_post_content3.setVisibility(0);
                        ((LinearLayout) _$_findCachedViewById(R$id.liner_read)).setVisibility(8);
                    } else {
                        TextView tv_post_content4 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                        Intrinsics.checkExpressionValueIsNotNull(tv_post_content4, "tv_post_content");
                        tv_post_content4.setVisibility(8);
                        ((LinearLayout) _$_findCachedViewById(R$id.liner_read)).setVisibility(0);
                    }
                } else {
                    TextView tv_post_content5 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                    Intrinsics.checkExpressionValueIsNotNull(tv_post_content5, "tv_post_content");
                    tv_post_content5.setVisibility(0);
                    ((LinearLayout) _$_findCachedViewById(R$id.liner_read)).setVisibility(8);
                }
                if (!TextUtils.isEmpty(postList.getDescription())) {
                    str2 = postList.getDescription();
                    Intrinsics.checkExpressionValueIsNotNull(str2, "it.description");
                } else if (!TextUtils.isEmpty(postList.getTitle())) {
                    str2 = postList.getTitle();
                    Intrinsics.checkExpressionValueIsNotNull(str2, "it.title");
                }
                String description = postList.getDescription();
                if (description != null) {
                    indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) description, "</", 0, false, 6, (Object) null);
                    if (indexOf$default2 == -1) {
                        TextView tv_post_content6 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                        Intrinsics.checkExpressionValueIsNotNull(tv_post_content6, "tv_post_content");
                        tv_post_content6.setText(str2);
                        ((TextView) _$_findCachedViewById(R$id.tv_post_reward_content)).setText(str2);
                    }
                }
                RichText.fromHtml(str2).into((TextView) _$_findCachedViewById(R$id.tv_post_content));
                RichText.fromHtml(str2).into((TextView) _$_findCachedViewById(R$id.tv_post_reward_content));
            } else {
                FrameLayout rl_single_img6 = (FrameLayout) _$_findCachedViewById(R$id.rl_single_img);
                Intrinsics.checkExpressionValueIsNotNull(rl_single_img6, "rl_single_img");
                rl_single_img6.setVisibility(8);
                NoScrollGridView head_gridView13 = (NoScrollGridView) _$_findCachedViewById(R$id.head_gridView);
                Intrinsics.checkExpressionValueIsNotNull(head_gridView13, "head_gridView");
                head_gridView13.setVisibility(8);
                TextView tv_post_content7 = (TextView) _$_findCachedViewById(R$id.tv_post_content);
                Intrinsics.checkExpressionValueIsNotNull(tv_post_content7, "tv_post_content");
                tv_post_content7.setVisibility(8);
                TextView text_title = (TextView) _$_findCachedViewById(R$id.text_title);
                Intrinsics.checkExpressionValueIsNotNull(text_title, "text_title");
                text_title.setVisibility(0);
            }
            Switch switchBtn = (Switch) _$_findCachedViewById(R$id.switchBtn);
            Intrinsics.checkExpressionValueIsNotNull(switchBtn, "switchBtn");
            if (this.logzhu == 0) {
                z = false;
            }
            switchBtn.setChecked(z);
            if (Intrinsics.areEqual("DESC", this.timeSortType)) {
                Context context = getContext();
                Intrinsics.checkExpressionValueIsNotNull(context, "context");
                Drawable drawable3 = context.getResources().getDrawable(R.drawable.post_sort_last);
                ((TextView) _$_findCachedViewById(R$id.tv_sort_type)).setText(R.string.post_comment_last);
                ((TextView) _$_findCachedViewById(R$id.tv_sort_type)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable3, (Drawable) null);
            } else {
                Context context2 = getContext();
                Intrinsics.checkExpressionValueIsNotNull(context2, "context");
                Drawable drawable4 = context2.getResources().getDrawable(R.drawable.post_sort_hot);
                ((TextView) _$_findCachedViewById(R$id.tv_sort_type)).setText(R.string.post_comment_hot);
                ((TextView) _$_findCachedViewById(R$id.tv_sort_type)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable4, (Drawable) null);
            }
            Unit unit11 = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickPostPay() {
        PostList postList;
        PostUtils postUtils = PostUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        PostList postList2 = this.intentPostList;
        String str = null;
        String valueOf = postList2 != null ? String.valueOf(postList2.getPrice()) : null;
        PostList postList3 = this.intentPostList;
        if (postList3 != null) {
            str = String.valueOf(postList3.getId());
        }
        PostList postList4 = this.intentPostList;
        postUtils.showImageNeedPayDialog(context, valueOf, str, (postList4 == null || postList4.getSubscribeSwitch() != 1 || (postList = this.intentPostList) == null) ? 0 : postList.getMemberId(), new NewCommentHeadView$clickPostPay$1(this), NewCommentHeadView$clickPostPay$2.INSTANCE);
    }

    public final void addTagToPost(Tag tag, boolean z) {
        if (!z || tag == null) {
            return;
        }
        PublishHotTagAdapter publishHotTagAdapter = this.tagAdapter;
        List<Tag> data = publishHotTagAdapter != null ? publishHotTagAdapter.getData() : null;
        if (data != null && data.size() == 10) {
            Tag tag2 = data.get(data.size() - 1);
            Intrinsics.checkExpressionValueIsNotNull(tag2, "data[data.size - 1]");
            if (tag2.getTagId() == -10) {
                data.remove(data.size() - 1);
            }
        }
        PublishHotTagAdapter publishHotTagAdapter2 = this.tagAdapter;
        if (publishHotTagAdapter2 == null) {
            return;
        }
        publishHotTagAdapter2.addData(0, (int) tag);
    }

    public final Tag compareAdd(ArrayList<Tag> addTag) {
        Intrinsics.checkParameterIsNotNull(addTag, "addTag");
        PublishHotTagAdapter publishHotTagAdapter = this.tagAdapter;
        List<Tag> data = publishHotTagAdapter != null ? publishHotTagAdapter.getData() : null;
        if (data instanceof ArrayList) {
            ArrayList arrayList = new ArrayList();
            Iterator<Tag> it2 = addTag.iterator();
            while (true) {
                boolean z = false;
                if (!it2.hasNext()) {
                    break;
                }
                Tag it3 = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                if (it3.getTagId() != -10) {
                    for (Tag it1 : data) {
                        int tagId = it3.getTagId();
                        Intrinsics.checkExpressionValueIsNotNull(it1, "it1");
                        if (tagId == it1.getTagId() && Intrinsics.areEqual(it3.getTagName(), it1.getTagName())) {
                            z = true;
                        }
                    }
                    if (!z) {
                        arrayList.add(it3);
                    }
                }
            }
            if (arrayList.size() > 0) {
                return (Tag) arrayList.get(0);
            }
        }
        return null;
    }

    public final void notifyItemDeleteTag(int i) {
        try {
            PublishHotTagAdapter publishHotTagAdapter = this.tagAdapter;
            if (publishHotTagAdapter != null) {
                publishHotTagAdapter.remove(i);
            }
            PublishHotTagAdapter publishHotTagAdapter2 = this.tagAdapter;
            if (publishHotTagAdapter2 == null) {
                return;
            }
            publishHotTagAdapter2.notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }

    private final void addGame() {
        final GameDialogAdapter gameDialogAdapter = new GameDialogAdapter();
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(gameDialogAdapter);
        }
        boolean z = true;
        gameDialogAdapter.setGameType(1);
        ArrayList<SubGamesBean> postGameBean = DBUtil.getPostGameBean(C2516Ad.TYPE_ARTICLE_REC);
        if (postGameBean != null && !postGameBean.isEmpty()) {
            z = false;
        }
        if (z && postGameBean.size() == 0) {
            View _$_findCachedViewById = _$_findCachedViewById(R$id.game_line);
            if (_$_findCachedViewById != null) {
                _$_findCachedViewById.setVisibility(8);
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_game);
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
        } else if (postGameBean instanceof ArrayList) {
            View _$_findCachedViewById2 = _$_findCachedViewById(R$id.game_line);
            if (_$_findCachedViewById2 != null) {
                _$_findCachedViewById2.setVisibility(0);
            }
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.liner_game);
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
            gameDialogAdapter.setNewData(postGameBean);
            gameDialogAdapter.setEnableLoadMore(false);
        }
        gameDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$addGame$1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                Context context = NewCommentHeadView.this.getContext();
                Intrinsics.checkExpressionValueIsNotNull(context, "context");
                GamePlayDialog gamePlayDialog = new GamePlayDialog(context);
                List<SubGamesBean> data = gameDialogAdapter.getData();
                if (data != null) {
                    gamePlayDialog.showGame((ArrayList) data, i);
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.SubGamesBean> /* = java.util.ArrayList<com.one.tomato.entity.db.SubGamesBean> */");
            }
        });
    }

    private final void addSerializeItem() {
        PostList postList = this.intentPostList;
        if (postList == null || postList.getSerialGroupId() != 0) {
            if (this.serializeItem == null) {
                this.serializeItem = new PostSerializeItem(getContext());
                PostSerializeItem postSerializeItem = this.serializeItem;
                if (postSerializeItem != null) {
                    postSerializeItem.addOnClickCallBack(new NewCommentHeadView$addSerializeItem$1(this));
                }
                PostSerializeItem postSerializeItem2 = this.serializeItem;
                if (postSerializeItem2 != null) {
                    postSerializeItem2.addOnClickAllPostCallBack(new NewCommentHeadView$addSerializeItem$2(this));
                }
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize);
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize);
            if (relativeLayout2 != null) {
                relativeLayout2.addView(this.serializeItem);
            }
            RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize);
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(0);
            }
            PostSerializeItem postSerializeItem3 = this.serializeItem;
            if (postSerializeItem3 == null) {
                return;
            }
            postSerializeItem3.setSerializeId(this.intentPostList);
            return;
        }
        RelativeLayout relativeLayout4 = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize);
        if (relativeLayout4 != null) {
            relativeLayout4.removeAllViews();
        }
        RelativeLayout relativeLayout5 = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize);
        if (relativeLayout5 == null) {
            return;
        }
        relativeLayout5.setVisibility(8);
    }
}
