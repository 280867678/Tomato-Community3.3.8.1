package razerdp.basepopup;

import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes4.dex */
interface PopupTouchController {
    boolean callDismissAtOnce();

    boolean onBackPressed();

    boolean onBeforeDismiss();

    boolean onDispatchKeyEvent(KeyEvent keyEvent);

    boolean onInterceptTouchEvent(MotionEvent motionEvent);

    boolean onOutSideTouch();

    boolean onTouchEvent(MotionEvent motionEvent);
}
