package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.king.zxing.util.CodeUtils;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: MyShareFragment.kt */
/* renamed from: com.one.tomato.ui.mine.MyShareFragment */
/* loaded from: classes3.dex */
public final class MyShareFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private ImageView iv_dialog_qrcode;
    private View spreadView;
    private TextView tv_dialog_invite_code;
    private TextView tv_dialog_website;
    private UserInfo userInfo;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_my_share;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            TextView tv_share_list = (TextView) _$_findCachedViewById(R$id.tv_share_list);
            Intrinsics.checkExpressionValueIsNotNull(tv_share_list, "tv_share_list");
            tv_share_list.setVisibility(0);
        }
        ((TextView) _$_findCachedViewById(R$id.tv_share_list)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyShareFragment$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MyShareFragment.this.getMContext();
                MyShareListActivity.startActivity(mContext);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_save)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyShareFragment$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyShareFragment.this.saveSpreadImg();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_copy)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyShareFragment$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostUtils.INSTANCE.copyShare("", 3);
                MyShareFragment myShareFragment = MyShareFragment.this;
                String string = AppUtil.getString(R.string.my_spread_dialog_copy_url_tip);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…read_dialog_copy_url_tip)");
                myShareFragment.showDialog(string);
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        this.userInfo = DBUtil.getUserInfo();
        initRQCode();
    }

    private final void initRQCode() {
        int lastIndexOf$default;
        UserInfo userInfo;
        UserInfo userInfo2 = this.userInfo;
        String str = null;
        if (TextUtils.isEmpty(userInfo2 != null ? userInfo2.getInviteCode() : null) && (userInfo = this.userInfo) != null) {
            userInfo.setInviteCode("--");
        }
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getShareUrl());
        sb.append("?inviteCode=");
        UserInfo userInfo3 = this.userInfo;
        sb.append(userInfo3 != null ? userInfo3.getInviteCode() : null);
        Bitmap createQRCode = CodeUtils.createQRCode(sb.toString(), (int) DisplayMetricsUtils.dp2px(200.0f));
        ((ImageView) _$_findCachedViewById(R$id.iv_code)).setImageBitmap(createQRCode);
        TextView tv_code = (TextView) _$_findCachedViewById(R$id.tv_code);
        Intrinsics.checkExpressionValueIsNotNull(tv_code, "tv_code");
        Object[] objArr = new Object[1];
        UserInfo userInfo4 = this.userInfo;
        objArr[0] = userInfo4 != null ? userInfo4.getInviteCode() : null;
        tv_code.setText(AppUtil.getString(R.string.my_spread_invite_code, objArr));
        View inflate = LayoutInflater.from(getMContext()).inflate(R.layout.activity_my_share_save, (ViewGroup) null);
        Intrinsics.checkExpressionValueIsNotNull(inflate, "LayoutInflater.from(mCon…vity_my_share_save, null)");
        this.spreadView = inflate;
        View view = this.spreadView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        }
        this.tv_dialog_website = (TextView) view.findViewById(R.id.tv_dialog_website);
        View view2 = this.spreadView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        }
        this.tv_dialog_invite_code = (TextView) view2.findViewById(R.id.tv_dialog_invite_code);
        View view3 = this.spreadView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        }
        this.iv_dialog_qrcode = (ImageView) view3.findViewById(R.id.iv_dialog_qrcode);
        DomainServer domainServer2 = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
        String website = domainServer2.getWebsiteUrl();
        Intrinsics.checkExpressionValueIsNotNull(website, "website");
        lastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(website, "/", 0, false, 6, null);
        String substring = website.substring(lastIndexOf$default + 1);
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
        TextView textView = this.tv_dialog_website;
        if (textView != null) {
            textView.setText(substring);
        }
        TextView textView2 = this.tv_dialog_invite_code;
        if (textView2 != null) {
            Object[] objArr2 = new Object[1];
            UserInfo userInfo5 = this.userInfo;
            if (userInfo5 != null) {
                str = userInfo5.getInviteCode();
            }
            objArr2[0] = str;
            textView2.setText(AppUtil.getString(R.string.my_spread_invite_code_1, objArr2));
        }
        ImageView imageView = this.iv_dialog_qrcode;
        if (imageView == null) {
            return;
        }
        imageView.setImageBitmap(createQRCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveSpreadImg() {
        showWaitingDialog();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) DisplayMetricsUtils.getWidth(), 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((int) DisplayMetricsUtils.getHeight(), 1073741824);
        View view = this.spreadView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        }
        view.measure(makeMeasureSpec, makeMeasureSpec2);
        View view2 = this.spreadView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        } else if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spreadView");
            throw null;
        } else {
            int measuredWidth = view2.getMeasuredWidth();
            View view3 = this.spreadView;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spreadView");
                throw null;
            }
            view2.layout(0, 0, measuredWidth, view3.getMeasuredHeight());
            View view4 = this.spreadView;
            if (view4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spreadView");
                throw null;
            }
            int width = view4.getWidth();
            View view5 = this.spreadView;
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spreadView");
                throw null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(width, view5.getHeight(), Bitmap.Config.ARGB_8888);
            if (createBitmap != null) {
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawColor(-1);
                View view6 = this.spreadView;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("spreadView");
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
                    sb.append("share");
                    sb.append(".png");
                    File file = new File(sb.toString());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(file));
                    Context mContext = getMContext();
                    if (mContext == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    mContext.sendBroadcast(intent);
                    hideWaitingDialog();
                    String string = AppUtil.getString(R.string.my_spread_dialog_save_qr_code_tip);
                    Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…_dialog_save_qr_code_tip)");
                    showDialog(string);
                    DataUploadUtil.uploadQRToServer();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    hideWaitingDialog();
                    return;
                }
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDialog(String str) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(getMContext());
        customAlertDialog.bottomButtonVisiblity(2);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(str);
        customAlertDialog.setConfirmButton(R.string.common_dialog_ok);
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.chess_bright_bg);
        } else {
            customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner5_coloraccent);
        }
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
    }
}
