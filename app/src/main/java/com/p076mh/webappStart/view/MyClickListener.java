package com.p076mh.webappStart.view;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* renamed from: com.mh.webappStart.view.MyClickListener */
/* loaded from: classes3.dex */
public class MyClickListener implements View.OnTouchListener {
    private static int timeout = 400;
    private int clickCount = 0;
    private Handler handler = new Handler();
    private Context mContext;
    private boolean mScrolling;
    private boolean mZooming;
    private MyClickCallBack myClickCallBack;
    private float touchDownX;
    private float touchDownY;

    /* renamed from: com.mh.webappStart.view.MyClickListener$MyClickCallBack */
    /* loaded from: classes3.dex */
    public interface MyClickCallBack {
        void doubleClick();

        void oneClick();
    }

    public MyClickListener(Context context, MyClickCallBack myClickCallBack) {
        this.mContext = context;
        this.myClickCallBack = myClickCallBack;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.touchDownX = motionEvent.getX();
            this.touchDownY = motionEvent.getY();
            this.mScrolling = false;
            this.mZooming = false;
        } else if (action == 1) {
            this.clickCount++;
            this.handler.postDelayed(new Runnable() { // from class: com.mh.webappStart.view.MyClickListener.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MyClickListener.this.clickCount == 1) {
                        if (!MyClickListener.this.mScrolling && !MyClickListener.this.mZooming) {
                            MyClickListener.this.myClickCallBack.oneClick();
                        }
                    } else if (MyClickListener.this.clickCount == 2) {
                        MyClickListener.this.myClickCallBack.doubleClick();
                    }
                    MyClickListener.this.handler.removeCallbacksAndMessages(null);
                    MyClickListener.this.clickCount = 0;
                    MyClickListener.this.mScrolling = false;
                    MyClickListener.this.mZooming = false;
                }
            }, timeout);
        } else if (action == 2) {
            if (Math.abs(this.touchDownX - motionEvent.getX()) >= ViewConfiguration.get(this.mContext).getScaledTouchSlop() || Math.abs(this.touchDownY - motionEvent.getY()) >= ViewConfiguration.get(this.mContext).getScaledTouchSlop()) {
                this.mScrolling = true;
            } else {
                this.mScrolling = false;
            }
            if (Math.abs(this.touchDownX - motionEvent.getX()) >= 20.0f || Math.abs(this.touchDownY - motionEvent.getY()) >= 20.0f) {
                this.mZooming = true;
            } else {
                this.mZooming = false;
            }
        }
        return false;
    }
}
