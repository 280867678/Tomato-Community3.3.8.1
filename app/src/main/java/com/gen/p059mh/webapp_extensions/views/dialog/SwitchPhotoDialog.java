package com.gen.p059mh.webapp_extensions.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;

/* renamed from: com.gen.mh.webapp_extensions.views.dialog.SwitchPhotoDialog */
/* loaded from: classes2.dex */
public class SwitchPhotoDialog extends Dialog implements DialogInterface.OnCancelListener {
    PhotoSwitchListener photoSwitchListener;

    private void setupView() {
    }

    public SwitchPhotoDialog(@NonNull Context context, PhotoSwitchListener photoSwitchListener) {
        super(context, R$style.clear_dialog_theme);
        this.photoSwitchListener = photoSwitchListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.dialog_web_sdk_switch_photo);
        getWindow().setLayout(-1, -2);
        getWindow().setGravity(80);
        getWindow().setWindowAnimations(R$style.sdk_dialogWindowAnim);
        initView();
        setupView();
        setCanceledOnTouchOutside(true);
        setOnCancelListener(this);
    }

    private void initView() {
        findViewById(R$id.tv_camera).setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.dialog.SwitchPhotoDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SwitchPhotoDialog.this.dismiss();
                SwitchPhotoDialog.this.photoSwitchListener.onCamera();
            }
        });
        findViewById(R$id.tv_album).setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.dialog.SwitchPhotoDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SwitchPhotoDialog.this.dismiss();
                SwitchPhotoDialog.this.photoSwitchListener.onAlbum();
            }
        });
        findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.dialog.SwitchPhotoDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SwitchPhotoDialog.this.dismiss();
                SwitchPhotoDialog.this.photoSwitchListener.cancel();
            }
        });
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.photoSwitchListener.cancel();
    }
}
