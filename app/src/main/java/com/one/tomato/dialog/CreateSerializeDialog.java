package com.one.tomato.dialog;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreateSerializeDialog.kt */
/* loaded from: classes3.dex */
public final class CreateSerializeDialog extends CustomAlertDialog {
    private Function1<? super String, Unit> createCallBack;
    private String ids = "";
    private int serializeId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateSerializeDialog(Context context, final int i) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setCancleButtonBackgroundDrable(ContextCompat.getDrawable(context, R.drawable.common_shape_solid_corner5_disable));
        setBottomHorizontalLineVisible(false);
        setTitleBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.common_shape_solid_corner12_white));
        setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.dialog.CreateSerializeDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreateSerializeDialog.this.dismiss();
            }
        });
        final EditText editText = null;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_create_serialize_title, (ViewGroup) null);
        LinearLayout linearLayout = this.contentView;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        this.contentView.addView(inflate);
        editText = inflate != null ? (EditText) inflate.findViewById(R.id.edit) : editText;
        setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.dialog.CreateSerializeDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Editable text;
                EditText editText2 = editText;
                String obj = (editText2 == null || (text = editText2.getText()) == null) ? null : text.toString();
                if (!TextUtils.isEmpty(obj)) {
                    if (i != 1) {
                        if (TextUtils.isEmpty(CreateSerializeDialog.this.ids)) {
                            return;
                        }
                        CreateSerializeDialog createSerializeDialog = CreateSerializeDialog.this;
                        if (obj != null) {
                            createSerializeDialog.requestCreate(obj, createSerializeDialog.ids);
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    CreateSerializeDialog createSerializeDialog2 = CreateSerializeDialog.this;
                    if (obj == null) {
                        obj = "";
                    }
                    createSerializeDialog2.renameTitle(obj);
                }
            }
        });
    }

    public final void addCreateSerialCallBack(Function1<? super String, Unit> function1) {
        this.createCallBack = function1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestCreate(final String str, String str2) {
        ApiImplService.Companion.getApiImplService().createSerialize(str, str2).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.dialog.CreateSerializeDialog$requestCreate$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.dialog.CreateSerializeDialog$requestCreate$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                Function1 function1;
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_create_seria_sucess));
                function1 = CreateSerializeDialog.this.createCallBack;
                if (function1 != null) {
                    Unit unit = (Unit) function1.mo6794invoke(str);
                }
                CreateSerializeDialog.this.dismiss();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renameTitle(final String str) {
        ApiImplService.Companion.getApiImplService().renameSerializeTitle(str, this.serializeId).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.dialog.CreateSerializeDialog$renameTitle$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.dialog.CreateSerializeDialog$renameTitle$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                Function1 function1;
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_serialize_rename_sucess));
                CreateSerializeDialog.this.dismiss();
                function1 = CreateSerializeDialog.this.createCallBack;
                if (function1 != null) {
                    Unit unit = (Unit) function1.mo6794invoke(str);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                Context context = CreateSerializeDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
            }
        });
    }

    public final void setPostIds(String ids) {
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        this.ids = ids;
    }

    public final void setSerialize(int i) {
        this.serializeId = i;
    }
}
