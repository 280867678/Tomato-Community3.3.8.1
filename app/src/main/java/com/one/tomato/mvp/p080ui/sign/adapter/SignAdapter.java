package com.one.tomato.mvp.p080ui.sign.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.SignBean;
import com.one.tomato.utils.AppUtil;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.sign.adapter.SignAdapter */
/* loaded from: classes3.dex */
public final class SignAdapter extends BaseQuickAdapter<SignBean, BaseViewHolder> {
    public SignAdapter() {
        super((int) R.layout.sign_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SignBean signBean) {
        Integer num = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_day) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_vip) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_give_num) : null;
        ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_signed) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_signed) : null;
        if (textView != null) {
            String string = AppUtil.getString(R.string.sign_num_day);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.sign_num_day)");
            Object[] objArr = new Object[1];
            objArr[0] = String.valueOf(signBean != null ? Integer.valueOf(signBean.getId()) : null);
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        if (Intrinsics.areEqual(signBean != null ? Integer.valueOf(signBean.getId()) : null, signBean != null ? Integer.valueOf(signBean.getCountSignInFirst()) : null)) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.sign_give_vip);
            }
            if (textView2 != null) {
                String string2 = AppUtil.getString(R.string.sign_give_num_vip);
                Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.sign_give_num_vip)");
                Object[] objArr2 = new Object[1];
                if (signBean != null) {
                    num = Integer.valueOf(signBean.getVipGiftFirst());
                }
                objArr2[0] = String.valueOf(num);
                String format2 = String.format(string2, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
                textView2.setText(format2);
            }
        } else {
            if (Intrinsics.areEqual(signBean != null ? Integer.valueOf(signBean.getId()) : null, signBean != null ? Integer.valueOf(signBean.getCountSignInLast()) : null)) {
                if (imageView != null) {
                    imageView.setImageResource(R.drawable.sign_give_vip);
                }
                if (textView2 != null) {
                    String string3 = AppUtil.getString(R.string.sign_give_num_vip);
                    Intrinsics.checkExpressionValueIsNotNull(string3, "AppUtil.getString(R.string.sign_give_num_vip)");
                    Object[] objArr3 = new Object[1];
                    if (signBean != null) {
                        num = Integer.valueOf(signBean.getVipGiftLast());
                    }
                    objArr3[0] = String.valueOf(num);
                    String format3 = String.format(string3, Arrays.copyOf(objArr3, objArr3.length));
                    Intrinsics.checkExpressionValueIsNotNull(format3, "java.lang.String.format(this, *args)");
                    textView2.setText(format3);
                }
            } else {
                if (imageView != null) {
                    imageView.setImageResource(R.drawable.sign_jingyan);
                }
                if (textView2 != null) {
                    String string4 = AppUtil.getString(R.string.sign_get_jingyan);
                    Intrinsics.checkExpressionValueIsNotNull(string4, "AppUtil.getString(R.string.sign_get_jingyan)");
                    Object[] objArr4 = new Object[1];
                    if (signBean != null) {
                        num = Integer.valueOf(signBean.getGiftExp());
                    }
                    objArr4[0] = String.valueOf(num);
                    String format4 = String.format(string4, Arrays.copyOf(objArr4, objArr4.length));
                    Intrinsics.checkExpressionValueIsNotNull(format4, "java.lang.String.format(this, *args)");
                    textView2.setText(format4);
                }
            }
        }
        if (signBean == null || !signBean.isSigned()) {
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.setVisibility(8);
            return;
        }
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        if (relativeLayout == null) {
            return;
        }
        relativeLayout.setVisibility(0);
    }
}
