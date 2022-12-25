package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.king.zxing.util.CodeUtils;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.post.adapter.PostSharePicAdapter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.zzhoujay.richtext.RichText;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PostShareActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostShareActivity */
/* loaded from: classes3.dex */
public final class PostShareActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PostList postList;
    private View spreadView;

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
        return R.layout.activity_post_share;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PostShareActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PostShareActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, PostList postList) {
            Intent intent = new Intent(context, PostShareActivity.class);
            intent.putExtra(AopConstants.APP_PROPERTIES_KEY, postList);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        Intent intent = getIntent();
        this.postList = intent != null ? (PostList) intent.getParcelableExtra(AopConstants.APP_PROPERTIES_KEY) : null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x018b, code lost:
        if (r1 != 4) goto L90;
     */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initData() {
        String str;
        int indexOf$default;
        RecyclerView recyclerView;
        int indexOf$default2;
        this.spreadView = (LinearLayout) _$_findCachedViewById(R$id.liner_screenshot);
        PostList postList = this.postList;
        if (postList != null) {
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.image_head), new ImageBean(postList.getAvatar()));
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_name);
            if (textView != null) {
                textView.setText(postList.getName());
            }
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
                RichText.fromHtml(str).into((TextView) _$_findCachedViewById(R$id.text_description));
            } else {
                TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_description);
                if (textView2 != null) {
                    textView2.setText(str);
                }
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_search);
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.post_share_search_name) + (char) 8220 + postList.getName() + (char) 8221);
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_description1);
            if (textView4 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(AppUtil.getString(R.string.post_share_tip_url_1));
                DomainServer domainServer = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                sb.append(domainServer.getWebsiteUrl());
                sb.append(AppUtil.getString(R.string.post_share_tip_url_2));
                textView4.setText(sb.toString());
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_yao);
            if (textView5 != null) {
                String string = AppUtil.getString(R.string.my_spread_invite_code_1);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri….my_spread_invite_code_1)");
                UserInfo userInfo = DBUtil.getUserInfo();
                Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                Object[] objArr = {userInfo.getInviteCode()};
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                textView5.setText(format);
            }
            StringBuilder sb2 = new StringBuilder();
            DomainServer domainServer2 = DomainServer.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
            sb2.append(domainServer2.getShareUrl());
            sb2.append("?inviteCode=");
            UserInfo userInfo2 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
            sb2.append(userInfo2.getInviteCode());
            Bitmap createQRCode = CodeUtils.createQRCode(sb2.toString(), (int) DisplayMetricsUtils.dp2px(80.0f));
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_code);
            if (imageView != null) {
                imageView.setImageBitmap(createQRCode);
            }
            int postType = postList.getPostType();
            if (postType == 1) {
                RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_image);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(PostUtils.INSTANCE.createImageBeanList(postList));
                if (arrayList.size() == 1) {
                    ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.iv_single_image);
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                    Object obj = arrayList.get(0);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "imageBeanList[0]");
                    ImageBean imageBean = (ImageBean) obj;
                    if (imageBean.getImageType() == 3) {
                        TextView tv_single_image = (TextView) _$_findCachedViewById(R$id.tv_single_image);
                        Intrinsics.checkExpressionValueIsNotNull(tv_single_image, "tv_single_image");
                        tv_single_image.setVisibility(0);
                        ((TextView) _$_findCachedViewById(R$id.tv_single_image)).setText(R.string.post_img_long);
                    }
                    ImageLoaderUtil.loadViewPagerOriginImage(getMContext(), (ImageView) _$_findCachedViewById(R$id.iv_single_image), null, imageBean, 0);
                } else {
                    RecyclerView head_gridView = (RecyclerView) _$_findCachedViewById(R$id.head_gridView);
                    Intrinsics.checkExpressionValueIsNotNull(head_gridView, "head_gridView");
                    head_gridView.setVisibility(0);
                    PostSharePicAdapter postSharePicAdapter = new PostSharePicAdapter();
                    RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.head_gridView);
                    if (recyclerView2 != null) {
                        recyclerView2.setAdapter(postSharePicAdapter);
                    }
                    if (arrayList.size() >= 5) {
                        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.head_gridView);
                        if (recyclerView3 != null) {
                            recyclerView3.setLayoutManager(new GridLayoutManager(getMContext(), 3));
                        }
                        TextView tv_single_image2 = (TextView) _$_findCachedViewById(R$id.tv_single_image);
                        Intrinsics.checkExpressionValueIsNotNull(tv_single_image2, "tv_single_image");
                        tv_single_image2.setVisibility(0);
                        TextView tv_single_image3 = (TextView) _$_findCachedViewById(R$id.tv_single_image);
                        Intrinsics.checkExpressionValueIsNotNull(tv_single_image3, "tv_single_image");
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(arrayList.size());
                        sb3.append((char) 24352);
                        tv_single_image3.setText(sb3.toString());
                    } else if (arrayList.size() == 4 || arrayList.size() == 2) {
                        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.head_gridView);
                        if (recyclerView4 != null) {
                            recyclerView4.setLayoutManager(new GridLayoutManager(getMContext(), 2));
                        }
                    } else if (arrayList.size() == 3 && (recyclerView = (RecyclerView) _$_findCachedViewById(R$id.head_gridView)) != null) {
                        recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 3));
                    }
                    postSharePicAdapter.setNewData(arrayList);
                    postSharePicAdapter.setEnableLoadMore(false);
                }
            } else {
                if (postType != 2) {
                    if (postType == 3) {
                        TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_read);
                        if (textView6 != null) {
                            textView6.setVisibility(0);
                        }
                        TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_description);
                        if (textView7 != null) {
                            textView7.setTypeface(Typeface.DEFAULT_BOLD);
                        }
                        if (!TextUtils.isEmpty(postList.getDescription())) {
                            str2 = postList.getDescription();
                            Intrinsics.checkExpressionValueIsNotNull(str2, "it.description");
                        } else if (!TextUtils.isEmpty(postList.getTitle())) {
                            str2 = postList.getTitle();
                            Intrinsics.checkExpressionValueIsNotNull(str2, "it.title");
                        }
                        String description = postList.getDescription();
                        Intrinsics.checkExpressionValueIsNotNull(description, "it.description");
                        indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) description, "</", 0, false, 6, (Object) null);
                        if (indexOf$default2 != -1) {
                            RichText.fromHtml(str2).into((TextView) _$_findCachedViewById(R$id.text_read));
                        } else {
                            TextView text_read = (TextView) _$_findCachedViewById(R$id.text_read);
                            Intrinsics.checkExpressionValueIsNotNull(text_read, "text_read");
                            text_read.setText(str2);
                        }
                    }
                }
                RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_video);
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(0);
                }
                ImageLoaderUtil.loadViewPagerOriginImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.image_video), null, new ImageBean(postList.getSecVideoCover()), 0);
            }
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostShareActivity$initData$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostShareActivity.this.onBackPressed();
                }
            });
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_save);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostShareActivity$initData$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ImageView imageView4 = (ImageView) PostShareActivity.this._$_findCachedViewById(R$id.image_head_bg);
                    if (imageView4 != null) {
                        imageView4.setVisibility(0);
                    }
                    RelativeLayout relativeLayout3 = (RelativeLayout) PostShareActivity.this._$_findCachedViewById(R$id.relate_title);
                    if (relativeLayout3 != null) {
                        relativeLayout3.setVisibility(8);
                    }
                    TextView textView8 = (TextView) PostShareActivity.this._$_findCachedViewById(R$id.text_tip);
                    if (textView8 != null) {
                        textView8.setVisibility(8);
                    }
                    RelativeLayout relativeLayout4 = (RelativeLayout) PostShareActivity.this._$_findCachedViewById(R$id.relate_save);
                    if (relativeLayout4 != null) {
                        relativeLayout4.setVisibility(8);
                    }
                    ImageView imageView5 = (ImageView) PostShareActivity.this._$_findCachedViewById(R$id.image_head_bg);
                    if (imageView5 != null) {
                        imageView5.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.PostShareActivity$initData$3.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PostShareActivity.this.saveSpreadImg();
                            }
                        }, 100L);
                    }
                }
            });
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_copy);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostShareActivity$initData$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostList postList2;
                    postList2 = PostShareActivity.this.postList;
                    if (postList2 != null) {
                        PostUtils.INSTANCE.copyShare(String.valueOf(postList2.getId()), 1);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveSpreadImg() {
        showWaitingDialog();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) DisplayMetricsUtils.getWidth(), 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((int) DisplayMetricsUtils.getHeight(), 1073741824);
        View view = this.spreadView;
        if (view != null) {
            view.measure(makeMeasureSpec, makeMeasureSpec2);
        }
        View view2 = this.spreadView;
        if (view2 != null) {
            if (view2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            int measuredWidth = view2.getMeasuredWidth();
            View view3 = this.spreadView;
            if (view3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            view2.layout(0, 0, measuredWidth, view3.getMeasuredHeight());
        }
        View view4 = this.spreadView;
        if (view4 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        int width = view4.getWidth();
        View view5 = this.spreadView;
        if (view5 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, view5.getHeight(), Bitmap.Config.ARGB_8888);
        if (createBitmap != null) {
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawColor(-1);
            View view6 = this.spreadView;
            if (view6 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            view6.draw(canvas);
            try {
                StringBuilder sb = new StringBuilder();
                File sDDCIMDir = FileUtil.getSDDCIMDir();
                Intrinsics.checkExpressionValueIsNotNull(sDDCIMDir, "FileUtil.getSDDCIMDir()");
                sb.append(sDDCIMDir.getPath());
                sb.append(File.separator);
                sb.append(AppUtil.getString(R.string.app_name));
                sb.append("postShare");
                sb.append(".png");
                File file = new File(sb.toString());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                Context mContext = getMContext();
                if (mContext != null) {
                    mContext.sendBroadcast(intent);
                }
                hideWaitingDialog();
                String string = AppUtil.getString(R.string.my_spread_dialog_save_qr_code_tip);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…_dialog_save_qr_code_tip)");
                showDialog(string);
                DataUploadUtil.uploadQRToServer();
            } catch (Exception e) {
                e.printStackTrace();
                hideWaitingDialog();
            }
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_head_bg);
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_title);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_tip);
            if (textView != null) {
                textView.setVisibility(0);
            }
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_save);
            if (relativeLayout2 == null) {
                return;
            }
            relativeLayout2.setVisibility(0);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    private final void showDialog(String str) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.bottomButtonVisiblity(2);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(str);
        customAlertDialog.setConfirmButton(R.string.common_dialog_ok);
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner5_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
    }
}
