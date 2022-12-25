package com.tomatolive.library.p136ui.interfaces.impl;

import android.support.p002v4.app.FragmentActivity;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.view.widget.ActionSheetView;
import com.tomatolive.library.p136ui.view.widget.matisse.Matisse;
import com.tomatolive.library.p136ui.view.widget.matisse.MimeType;
import com.tomatolive.library.p136ui.view.widget.matisse.engine.impl.GlideEngine;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;

/* renamed from: com.tomatolive.library.ui.interfaces.impl.UploadImageActionListener */
/* loaded from: classes3.dex */
public class UploadImageActionListener implements ActionSheetView.ActionSheetOperateListener {
    private FragmentActivity mActivity;
    private RxPermissions mRxPermissions;
    private MediaStoreCompat mediaStoreCompat;

    @Override // com.tomatolive.library.p136ui.view.widget.ActionSheetView.ActionSheetOperateListener
    public void onCancel() {
    }

    public UploadImageActionListener(FragmentActivity fragmentActivity, MediaStoreCompat mediaStoreCompat) {
        this.mActivity = fragmentActivity;
        this.mRxPermissions = new RxPermissions(this.mActivity);
        this.mediaStoreCompat = mediaStoreCompat;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.ActionSheetView.ActionSheetOperateListener
    public void onOperateListener(MenuEntity menuEntity, int i) {
        if (i == 0) {
            this.mRxPermissions.request("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.interfaces.impl.UploadImageActionListener.1
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Boolean bool) {
                    if (bool.booleanValue()) {
                        if (bool.booleanValue()) {
                            if (UploadImageActionListener.this.mediaStoreCompat == null) {
                                return;
                            }
                            UploadImageActionListener.this.mediaStoreCompat.dispatchCaptureIntent(UploadImageActionListener.this.mActivity, ConstantUtils.REQUEST_CAMERA);
                            return;
                        }
                        ToastUtils.showShort(R$string.fq_no_permission);
                        return;
                    }
                    ToastUtils.showShort(R$string.fq_no_permission);
                }
            });
        } else if (i != 1) {
        } else {
            this.mRxPermissions.request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.interfaces.impl.UploadImageActionListener.2
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Boolean bool) {
                    if (bool.booleanValue()) {
                        Matisse.from(UploadImageActionListener.this.mActivity).choose(MimeType.m241of(MimeType.JPEG, MimeType.PNG)).showSingleMediaType(true).capture(false).thumbnailScale(0.85f).imageEngine(new GlideEngine()).forResult(ConstantUtils.REQUEST_ALBUM);
                    } else {
                        ToastUtils.showShort(R$string.fq_no_permission_write);
                    }
                }
            });
        }
    }
}
