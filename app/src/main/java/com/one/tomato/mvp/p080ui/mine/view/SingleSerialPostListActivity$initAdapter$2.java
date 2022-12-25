package com.one.tomato.mvp.p080ui.mine.view;

import android.widget.Button;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.mine.adapter.MinePostPublishAdapter;
import com.one.tomato.utils.AppUtil;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SingleSerialPostListActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initAdapter$2 */
/* loaded from: classes3.dex */
public final class SingleSerialPostListActivity$initAdapter$2 extends Lambda implements Function1<PostList, Unit> {
    final /* synthetic */ SingleSerialPostListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingleSerialPostListActivity$initAdapter$2(SingleSerialPostListActivity singleSerialPostListActivity) {
        super(1);
        this.this$0 = singleSerialPostListActivity;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(PostList postList) {
        invoke2(postList);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(PostList it2) {
        MinePostPublishAdapter minePostPublishAdapter;
        boolean z;
        int i;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        minePostPublishAdapter = this.this$0.adapter;
        List<PostList> data = minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null;
        if ((data == null || data.isEmpty()) || data.size() <= 0) {
            z = false;
            i = 0;
        } else {
            z = false;
            i = 0;
            for (PostList it3 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                if (it3.isSelectMinePostDelete()) {
                    i++;
                    z = true;
                }
            }
        }
        Button button_delete = (Button) this.this$0._$_findCachedViewById(R$id.button_delete);
        Intrinsics.checkExpressionValueIsNotNull(button_delete, "button_delete");
        button_delete.setEnabled(z);
        TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.text_post_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {String.valueOf(i)};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
    }
}
