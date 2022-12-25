package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.zzhoujay.richtext.RichText;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PostReadItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostReadItem */
/* loaded from: classes3.dex */
public final class PostReadItem extends ConstraintLayout {
    private HashMap _$_findViewCache;

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostReadItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.item_new_post_read, (ViewGroup) this, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setReadText(String str) {
        TextView textView;
        int indexOf$default;
        if (str != null) {
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, "</", 0, false, 6, (Object) null);
            if (indexOf$default == -1) {
                TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_read);
                if (textView2 != null) {
                    textView2.setText(str);
                }
                textView = (TextView) _$_findCachedViewById(R$id.text_read);
                if (textView != null) {
                    return;
                }
                textView.setOnClickListener(PostReadItem$setReadText$1.INSTANCE);
                return;
            }
        }
        RichText.fromHtml(str).into((TextView) _$_findCachedViewById(R$id.text_read));
        textView = (TextView) _$_findCachedViewById(R$id.text_read);
        if (textView != null) {
        }
    }
}
