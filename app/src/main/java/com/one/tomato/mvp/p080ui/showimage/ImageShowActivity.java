package com.one.tomato.mvp.p080ui.showimage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.adapter.ImagePagerAdapter;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.widget.image.MNGestureView;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageShowActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.showimage.ImageShowActivity */
/* loaded from: classes3.dex */
public final class ImageShowActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private ArrayList<ImageBean> imageBeans;
    private ImagePagerAdapter pagerAdapter;
    private int position;
    private PostList postList;
    public static final Companion Companion = new Companion(null);
    private static final String INTENT_IMGS = INTENT_IMGS;
    private static final String INTENT_IMGS = INTENT_IMGS;
    private static final String INTENT_POSITION = INTENT_POSITION;
    private static final String INTENT_POSITION = INTENT_POSITION;
    private static final String INTENT_POST_LIST = INTENT_POST_LIST;
    private static final String INTENT_POST_LIST = INTENT_POST_LIST;
    private static final String INTENT_REVIEW_POST = INTENT_REVIEW_POST;
    private static final String INTENT_REVIEW_POST = INTENT_REVIEW_POST;

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

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_image_show;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public ImageShowActivity() {
        Boolean.valueOf(false);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        this.imageBeans = getIntent().getParcelableArrayListExtra(INTENT_IMGS);
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        this.position = intent.getExtras().getInt(INTENT_POSITION);
        Intent intent2 = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent2, "intent");
        this.postList = (PostList) intent2.getExtras().getParcelable(INTENT_POST_LIST);
        Intent intent3 = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent3, "intent");
        Boolean.valueOf(intent3.getExtras().getBoolean(INTENT_REVIEW_POST));
        TextView tv_no = (TextView) _$_findCachedViewById(R$id.tv_no);
        Intrinsics.checkExpressionValueIsNotNull(tv_no, "tv_no");
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.position + 1));
        sb.append("/");
        ArrayList<ImageBean> arrayList = this.imageBeans;
        sb.append(arrayList != null ? Integer.valueOf(arrayList.size()) : null);
        tv_no.setText(sb.toString());
        this.pagerAdapter = new ImagePagerAdapter(getMContext(), this.imageBeans);
        ImagePagerAdapter imagePagerAdapter = this.pagerAdapter;
        if (imagePagerAdapter != null) {
            imagePagerAdapter.setPostList(this.postList);
        }
        PreviewViewPager viewpager = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setAdapter(this.pagerAdapter);
        PreviewViewPager viewpager2 = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager2, "viewpager");
        viewpager2.setCurrentItem(this.position);
        ((PreviewViewPager) _$_findCachedViewById(R$id.viewpager)).setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.showimage.ImageShowActivity$initView$1
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                ArrayList arrayList2;
                ArrayList arrayList3;
                ArrayList arrayList4;
                PostList postList;
                PostList postList2;
                TextView tv_no2 = (TextView) ImageShowActivity.this._$_findCachedViewById(R$id.tv_no);
                Intrinsics.checkExpressionValueIsNotNull(tv_no2, "tv_no");
                StringBuilder sb2 = new StringBuilder();
                int i2 = i + 1;
                sb2.append(String.valueOf(i2));
                sb2.append("/");
                arrayList2 = ImageShowActivity.this.imageBeans;
                sb2.append(arrayList2 != null ? Integer.valueOf(arrayList2.size()) : null);
                tv_no2.setText(sb2.toString());
                try {
                    arrayList3 = ImageShowActivity.this.imageBeans;
                    if (arrayList3 != null) {
                        int i3 = 0;
                        if (i2 == arrayList3.size() / 2) {
                            postList2 = ImageShowActivity.this.postList;
                            DataUploadUtil.uploadVideoPlayHalf(postList2 != null ? postList2.getId() : 0);
                        }
                        arrayList4 = ImageShowActivity.this.imageBeans;
                        if (arrayList4 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        } else if (i2 != arrayList4.size()) {
                            return;
                        } else {
                            postList = ImageShowActivity.this.postList;
                            if (postList != null) {
                                i3 = postList.getId();
                            }
                            DataUploadUtil.uploadVideoPlayWhole(i3);
                            return;
                        }
                    }
                    Intrinsics.throwNpe();
                    throw null;
                } catch (Exception unused) {
                }
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                PostList postList;
                PostUtils postUtils = PostUtils.INSTANCE;
                postList = ImageShowActivity.this.postList;
                postUtils.updatePostBrowse(postList);
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        ((MNGestureView) _$_findCachedViewById(R$id.mn_view)).setOnSwipeListener(new MNGestureView.OnSwipeListener() { // from class: com.one.tomato.mvp.ui.showimage.ImageShowActivity$initData$1
            @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
            public void downSwipe() {
                ImageShowActivity.this.onBackPressed();
            }

            @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
            public void overSwipe() {
                RelativeLayout rl_black_bg = (RelativeLayout) ImageShowActivity.this._$_findCachedViewById(R$id.rl_black_bg);
                Intrinsics.checkExpressionValueIsNotNull(rl_black_bg, "rl_black_bg");
                rl_black_bg.setAlpha(1.0f);
                RelativeLayout relativeLayout = (RelativeLayout) ImageShowActivity.this._$_findCachedViewById(R$id.relate_text_num);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
            }

            @Override // com.one.tomato.widget.image.MNGestureView.OnSwipeListener
            public void onSwiping(float f) {
                RelativeLayout relativeLayout = (RelativeLayout) ImageShowActivity.this._$_findCachedViewById(R$id.relate_text_num);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(8);
                }
                float f2 = 1;
                float f3 = f2 - (f / 500);
                if (f3 < 0.3d) {
                    f3 = 0.3f;
                }
                if (f3 > f2) {
                    f3 = 1.0f;
                }
                RelativeLayout rl_black_bg = (RelativeLayout) ImageShowActivity.this._$_findCachedViewById(R$id.rl_black_bg);
                Intrinsics.checkExpressionValueIsNotNull(rl_black_bg, "rl_black_bg");
                rl_black_bg.setAlpha(f3);
            }
        });
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        this.pagerAdapter = null;
        PreviewViewPager viewpager = (PreviewViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setAdapter(null);
        finish();
        overridePendingTransition(0, R.anim.mn_show_image_exit);
    }

    /* compiled from: ImageShowActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.showimage.ImageShowActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getINTENT_IMGS() {
            return ImageShowActivity.INTENT_IMGS;
        }

        public final String getINTENT_POSITION() {
            return ImageShowActivity.INTENT_POSITION;
        }

        public final String getINTENT_POST_LIST() {
            return ImageShowActivity.INTENT_POST_LIST;
        }

        public final String getINTENT_REVIEW_POST() {
            return ImageShowActivity.INTENT_REVIEW_POST;
        }

        public final void startActivity(Context context, ArrayList<ImageBean> imageBeans, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(imageBeans, "imageBeans");
            Intent intent = new Intent();
            intent.setClass(context, ImageShowActivity.class);
            intent.putParcelableArrayListExtra(getINTENT_IMGS(), imageBeans);
            intent.putExtra(getINTENT_POSITION(), i);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.scale_small_in, 0);
        }

        public final void startActivity(Context context, ArrayList<ImageBean> imageBeans, int i, PostList postList, boolean z) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(imageBeans, "imageBeans");
            Intent intent = new Intent();
            intent.setClass(context, ImageShowActivity.class);
            intent.putParcelableArrayListExtra(getINTENT_IMGS(), imageBeans);
            intent.putExtra(getINTENT_POSITION(), i);
            intent.putExtra(getINTENT_POST_LIST(), postList);
            intent.putExtra(getINTENT_REVIEW_POST(), z);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.scale_small_in, 0);
        }
    }
}
