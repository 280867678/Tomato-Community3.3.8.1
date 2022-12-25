package com.contrarywind.timer;

import com.contrarywind.view.WheelView;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public final class InertiaTimerTask extends TimerTask {
    private float mCurrentVelocityY = 2.14748365E9f;
    private final float mFirstVelocityY;
    private final WheelView mWheelView;

    public InertiaTimerTask(WheelView wheelView, float f) {
        this.mWheelView = wheelView;
        this.mFirstVelocityY = f;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        if (this.mCurrentVelocityY == 2.14748365E9f) {
            float f = 2000.0f;
            if (Math.abs(this.mFirstVelocityY) > 2000.0f) {
                if (this.mFirstVelocityY <= 0.0f) {
                    f = -2000.0f;
                }
                this.mCurrentVelocityY = f;
            } else {
                this.mCurrentVelocityY = this.mFirstVelocityY;
            }
        }
        if (Math.abs(this.mCurrentVelocityY) >= 0.0f && Math.abs(this.mCurrentVelocityY) <= 20.0f) {
            this.mWheelView.cancelFuture();
            this.mWheelView.getHandler().sendEmptyMessage(2000);
            return;
        }
        WheelView wheelView = this.mWheelView;
        float f2 = (int) (this.mCurrentVelocityY / 100.0f);
        wheelView.setTotalScrollY(wheelView.getTotalScrollY() - f2);
        if (!this.mWheelView.isLoop()) {
            float itemHeight = this.mWheelView.getItemHeight();
            float f3 = (-this.mWheelView.getInitPosition()) * itemHeight;
            float itemsCount = ((this.mWheelView.getItemsCount() - 1) - this.mWheelView.getInitPosition()) * itemHeight;
            double d = itemHeight * 0.25d;
            if (this.mWheelView.getTotalScrollY() - d < f3) {
                f3 = this.mWheelView.getTotalScrollY() + f2;
            } else if (this.mWheelView.getTotalScrollY() + d > itemsCount) {
                itemsCount = this.mWheelView.getTotalScrollY() + f2;
            }
            if (this.mWheelView.getTotalScrollY() <= f3) {
                this.mCurrentVelocityY = 40.0f;
                this.mWheelView.setTotalScrollY((int) f3);
            } else if (this.mWheelView.getTotalScrollY() >= itemsCount) {
                this.mWheelView.setTotalScrollY((int) itemsCount);
                this.mCurrentVelocityY = -40.0f;
            }
        }
        float f4 = this.mCurrentVelocityY;
        if (f4 < 0.0f) {
            this.mCurrentVelocityY = f4 + 20.0f;
        } else {
            this.mCurrentVelocityY = f4 - 20.0f;
        }
        this.mWheelView.getHandler().sendEmptyMessage(1000);
    }
}
