package com.one.tomato.mvp.p080ui.circle.view;

import android.content.Intent;
import com.one.tomato.entity.p079db.DefaultChannelBean;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.one.tomato.mvp.p080ui.circle.adapter.MyChannelAdapter;
import com.one.tomato.mvp.p080ui.circle.utils.ChannelManger;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewChannelChooseActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$callBackToHome$1 */
/* loaded from: classes3.dex */
final class NewChannelChooseActivity$callBackToHome$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ NewChannelChooseActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewChannelChooseActivity$callBackToHome$1(NewChannelChooseActivity newChannelChooseActivity) {
        super(0);
        this.this$0 = newChannelChooseActivity;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0083  */
    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo6822invoke() {
        MyChannelAdapter myChannelAdapter;
        MyChannelAdapter myChannelAdapter2;
        int i;
        int i2;
        Intent intent = new Intent();
        myChannelAdapter = this.this$0.myChannelAdapter;
        DefaultChannelBean defaultChannelBean = null;
        Collection<? extends DefaultChannelBean> data = myChannelAdapter != null ? myChannelAdapter.getData() : null;
        myChannelAdapter2 = this.this$0.recommentChannelAdapter;
        Collection<? extends DefaultChannelBean> data2 = myChannelAdapter2 != null ? myChannelAdapter2.getData() : null;
        ArrayList<DefaultChannelBean> userChannel = ChannelManger.INSTANCE.getUserChannel(UserChannelBean.TYPE_DEFAULT);
        ArrayList<DefaultChannelBean> userChannel2 = ChannelManger.INSTANCE.getUserChannel(UserChannelBean.TYPE_NO_DEFAULT);
        if (data != null) {
            if (!(data == null || data.isEmpty())) {
                userChannel = new ArrayList<>();
                userChannel.addAll(data);
                ChannelManger.INSTANCE.saveUserChannel(userChannel, UserChannelBean.TYPE_DEFAULT);
            }
        }
        if (data2 != null) {
            if (!(data2 == null || data2.isEmpty())) {
                ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
                arrayList.addAll(data2);
                ChannelManger.INSTANCE.saveUserChannel(arrayList, UserChannelBean.TYPE_NO_DEFAULT, false);
                i = this.this$0.clickItemPos;
                if (Intrinsics.compare(i, -1) == 1) {
                    if (userChannel != null) {
                        i2 = this.this$0.clickItemPos;
                        defaultChannelBean = userChannel.get(i2);
                    }
                    intent.putExtra("clickItem", defaultChannelBean);
                }
                this.this$0.setResult(-1, intent);
                this.this$0.finish();
            }
        }
        ChannelManger.INSTANCE.saveUserChannel(userChannel2, UserChannelBean.TYPE_NO_DEFAULT, true);
        i = this.this$0.clickItemPos;
        if (Intrinsics.compare(i, -1) == 1) {
        }
        this.this$0.setResult(-1, intent);
        this.this$0.finish();
    }
}
