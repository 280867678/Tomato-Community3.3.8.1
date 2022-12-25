package com.one.tomato.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.p085ui.publish.PublishActivity;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PublishPostDialog.kt */
/* loaded from: classes3.dex */
public final class PublishPostDialog extends Dialog {
    private PublishInfo publish;
    private ArrayList<LocalMedia> selectList;
    private SelectPicTypeUtil selectPicTypeUtil;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PublishPostDialog(Context context) {
        super(context, R.style.PostRewardDialog);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_publish_post, (ViewGroup) null));
        Window window = getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        initView();
    }

    private final void initView() {
        ImageView imageView = (ImageView) findViewById(R$id.image_close);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PublishPostDialog$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishPostDialog.this.dismiss();
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R$id.relate_read);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PublishPostDialog$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishInfo publishInfo;
                    PublishInfo publishInfo2;
                    PublishPostDialog.this.publish = new PublishInfo();
                    publishInfo = PublishPostDialog.this.publish;
                    if (publishInfo != null) {
                        publishInfo.setPostType(3);
                    }
                    Context context = PublishPostDialog.this.getContext();
                    publishInfo2 = PublishPostDialog.this.publish;
                    PublishActivity.startActivity(context, publishInfo2);
                    PublishPostDialog.this.dismiss();
                    Activity ownerActivity = PublishPostDialog.this.getOwnerActivity();
                    if (ownerActivity != null) {
                        ownerActivity.finish();
                    }
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R$id.relate_video);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PublishPostDialog$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishInfo publishInfo;
                    SelectPicTypeUtil selectPicTypeUtil;
                    ArrayList arrayList;
                    PublishPostDialog.this.publish = new PublishInfo();
                    publishInfo = PublishPostDialog.this.publish;
                    if (publishInfo != null) {
                        publishInfo.setPostType(2);
                    }
                    PublishPostDialog.this.selectList = new ArrayList();
                    PublishPostDialog publishPostDialog = PublishPostDialog.this;
                    publishPostDialog.selectPicTypeUtil = new SelectPicTypeUtil(publishPostDialog.getOwnerActivity());
                    selectPicTypeUtil = PublishPostDialog.this.selectPicTypeUtil;
                    if (selectPicTypeUtil != null) {
                        arrayList = PublishPostDialog.this.selectList;
                        selectPicTypeUtil.selectCommonVideo(1, arrayList);
                    }
                    PublishPostDialog.this.dismiss();
                }
            });
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R$id.relate_pic);
        if (relativeLayout3 != null) {
            relativeLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PublishPostDialog$initView$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishInfo publishInfo;
                    SelectPicTypeUtil selectPicTypeUtil;
                    ArrayList arrayList;
                    PublishPostDialog.this.publish = new PublishInfo();
                    publishInfo = PublishPostDialog.this.publish;
                    if (publishInfo != null) {
                        publishInfo.setPostType(1);
                    }
                    PublishPostDialog.this.selectList = new ArrayList();
                    PublishPostDialog publishPostDialog = PublishPostDialog.this;
                    publishPostDialog.selectPicTypeUtil = new SelectPicTypeUtil(publishPostDialog.getOwnerActivity());
                    selectPicTypeUtil = PublishPostDialog.this.selectPicTypeUtil;
                    if (selectPicTypeUtil != null) {
                        arrayList = PublishPostDialog.this.selectList;
                        selectPicTypeUtil.selectCommonPhoto(9, false, false, false, arrayList);
                    }
                    PublishPostDialog.this.dismiss();
                }
            });
        }
    }

    public final void setSelectData(ArrayList<LocalMedia> data) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        ArrayList<LocalMedia> arrayList = this.selectList;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList<LocalMedia> arrayList2 = this.selectList;
        if (arrayList2 != null) {
            arrayList2.addAll(data);
        }
        PublishInfo publishInfo = this.publish;
        if (publishInfo != null) {
            publishInfo.setSelectList(this.selectList);
        }
        PublishActivity.startActivity(getContext(), this.publish);
        dismiss();
    }
}
