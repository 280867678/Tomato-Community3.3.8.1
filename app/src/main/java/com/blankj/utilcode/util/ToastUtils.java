package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.p002v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.blankj.utilcode.util.Utils;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public final class ToastUtils {
    private static IToast iToast = null;
    private static int sBgColor = -16777217;
    private static int sBgResource = -1;
    private static int sGravity = -1;
    private static int sMsgColor = -16777217;
    private static int sMsgTextSize = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface IToast {
        void cancel();

        View getView();

        void setGravity(int i, int i2, int i3);

        void show();
    }

    public static void showShort(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "null";
        }
        show(charSequence, 0);
    }

    public static void showShort(@StringRes int i) {
        show(i, 0);
    }

    public static void cancel() {
        IToast iToast2 = iToast;
        if (iToast2 != null) {
            iToast2.cancel();
        }
    }

    private static void show(int i, int i2) {
        try {
            show(Utils.getApp().getResources().getText(i), i2);
        } catch (Exception unused) {
            show(String.valueOf(i), i2);
        }
    }

    private static void show(final CharSequence charSequence, final int i) {
        Utils.runOnUiThread(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.1
            @Override // java.lang.Runnable
            @SuppressLint({"ShowToast"})
            public void run() {
                ToastUtils.cancel();
                IToast unused = ToastUtils.iToast = ToastFactory.makeToast(Utils.getApp(), charSequence, i);
                View view = ToastUtils.iToast.getView();
                if (view == null) {
                    return;
                }
                TextView textView = (TextView) view.findViewById(16908299);
                if (ToastUtils.sMsgColor != -16777217) {
                    textView.setTextColor(ToastUtils.sMsgColor);
                }
                if (ToastUtils.sMsgTextSize != -1) {
                    textView.setTextSize(ToastUtils.sMsgTextSize);
                }
                if (ToastUtils.sGravity != -1 || ToastUtils.sXOffset != -1 || ToastUtils.sYOffset != -1) {
                    ToastUtils.iToast.setGravity(ToastUtils.sGravity, ToastUtils.sXOffset, ToastUtils.sYOffset);
                }
                ToastUtils.setBg(textView);
                ToastUtils.iToast.show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setBg(TextView textView) {
        if (sBgResource != -1) {
            iToast.getView().setBackgroundResource(sBgResource);
            textView.setBackgroundColor(0);
        } else if (sBgColor == -16777217) {
        } else {
            View view = iToast.getView();
            Drawable background = view.getBackground();
            Drawable background2 = textView.getBackground();
            if (background != null && background2 != null) {
                background.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                textView.setBackgroundColor(0);
            } else if (background != null) {
                background.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (background2 != null) {
                background2.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                view.setBackgroundColor(sBgColor);
            }
        }
    }

    /* loaded from: classes2.dex */
    static class ToastFactory {
        static IToast makeToast(Context context, CharSequence charSequence, int i) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                return new SystemToast(makeNormalToast(context, charSequence, i));
            }
            return new ToastWithoutNotification(makeNormalToast(context, charSequence, i));
        }

        private static Toast makeNormalToast(Context context, CharSequence charSequence, int i) {
            Toast makeText = Toast.makeText(context, "", i);
            makeText.setText(charSequence);
            return makeText;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class SystemToast extends AbsToast {
        SystemToast(Toast toast) {
            super(toast);
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    Field declaredField = Toast.class.getDeclaredField("mTN");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(toast);
                    Field declaredField2 = declaredField.getType().getDeclaredField("mHandler");
                    declaredField2.setAccessible(true);
                    declaredField2.set(obj, new SafeHandler((Handler) declaredField2.get(obj)));
                } catch (Exception unused) {
                }
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public void show() {
            this.mToast.show();
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public void cancel() {
            this.mToast.cancel();
        }

        /* loaded from: classes2.dex */
        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler handler) {
                this.impl = handler;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                this.impl.handleMessage(message);
            }

            @Override // android.os.Handler
            public void dispatchMessage(Message message) {
                try {
                    this.impl.dispatchMessage(message);
                } catch (Exception e) {
                    Log.e("ToastUtils", e.toString());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class ToastWithoutNotification extends AbsToast {
        private static final Utils.OnActivityDestroyedListener LISTENER = new Utils.OnActivityDestroyedListener() { // from class: com.blankj.utilcode.util.ToastUtils.ToastWithoutNotification.1
            @Override // com.blankj.utilcode.util.Utils.OnActivityDestroyedListener
            public void onActivityDestroyed(Activity activity) {
                if (ToastUtils.iToast == null) {
                    return;
                }
                activity.getWindow().getDecorView().setVisibility(8);
                ToastUtils.iToast.cancel();
            }
        };
        private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        private View mView;
        private WindowManager mWM;

        ToastWithoutNotification(Toast toast) {
            super(toast);
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public void show() {
            this.mView = this.mToast.getView();
            if (this.mView == null) {
                return;
            }
            Context context = this.mToast.getView().getContext();
            if (Build.VERSION.SDK_INT < 25) {
                this.mWM = (WindowManager) context.getSystemService("window");
                this.mParams.type = TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;
            } else {
                Context topActivityOrApp = Utils.getTopActivityOrApp();
                if (!(topActivityOrApp instanceof Activity)) {
                    Log.e("ToastUtils", "Couldn't get top Activity.");
                    return;
                }
                Activity activity = (Activity) topActivityOrApp;
                if (activity.isFinishing() || activity.isDestroyed()) {
                    Log.e("ToastUtils", activity + " is useless");
                    return;
                }
                this.mWM = activity.getWindowManager();
                this.mParams.type = 99;
                Utils.getActivityLifecycle().addOnActivityDestroyedListener(activity, LISTENER);
            }
            WindowManager.LayoutParams layoutParams = this.mParams;
            layoutParams.height = -2;
            layoutParams.width = -2;
            layoutParams.format = -3;
            layoutParams.windowAnimations = 16973828;
            layoutParams.setTitle("ToastWithoutNotification");
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            layoutParams2.flags = 152;
            layoutParams2.packageName = Utils.getApp().getPackageName();
            this.mParams.gravity = this.mToast.getGravity();
            WindowManager.LayoutParams layoutParams3 = this.mParams;
            if ((layoutParams3.gravity & 7) == 7) {
                layoutParams3.horizontalWeight = 1.0f;
            }
            WindowManager.LayoutParams layoutParams4 = this.mParams;
            if ((layoutParams4.gravity & 112) == 112) {
                layoutParams4.verticalWeight = 1.0f;
            }
            this.mParams.x = this.mToast.getXOffset();
            this.mParams.y = this.mToast.getYOffset();
            this.mParams.horizontalMargin = this.mToast.getHorizontalMargin();
            this.mParams.verticalMargin = this.mToast.getVerticalMargin();
            try {
                if (this.mWM != null) {
                    this.mWM.addView(this.mView, this.mParams);
                }
            } catch (Exception unused) {
            }
            Utils.runOnUiThreadDelayed(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.ToastWithoutNotification.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastWithoutNotification.this.cancel();
                }
            }, this.mToast.getDuration() == 0 ? 2000L : 3500L);
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public void cancel() {
            try {
                if (this.mWM != null) {
                    this.mWM.removeViewImmediate(this.mView);
                }
            } catch (Exception unused) {
            }
            this.mView = null;
            this.mWM = null;
            this.mToast = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class AbsToast implements IToast {
        Toast mToast;

        AbsToast(Toast toast) {
            this.mToast = toast;
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public View getView() {
            return this.mToast.getView();
        }

        @Override // com.blankj.utilcode.util.ToastUtils.IToast
        public void setGravity(int i, int i2, int i3) {
            this.mToast.setGravity(i, i2, i3);
        }
    }
}
