package com.contrarywind.timer;

import android.os.Handler;
import android.os.Message;
import com.contrarywind.view.WheelView;

/* loaded from: classes2.dex */
public final class MessageHandler extends Handler {
    private final WheelView wheelView;

    public MessageHandler(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1000) {
            this.wheelView.invalidate();
        } else if (i == 2000) {
            this.wheelView.smoothScroll(WheelView.ACTION.FLING);
        } else if (i != 3000) {
        } else {
            this.wheelView.onItemSelected();
        }
    }
}
