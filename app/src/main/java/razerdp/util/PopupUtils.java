package razerdp.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.List;
import razerdp.basepopup.BasePopupSupporterManager;

/* loaded from: classes4.dex */
public class PopupUtils {
    public static boolean isBackgroundInvalidated(Drawable drawable) {
        return drawable == null || ((drawable instanceof ColorDrawable) && ((ColorDrawable) drawable).getColor() == 0);
    }

    public static View clearViewFromParent(View view) {
        if (view == null) {
            return view;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        return view;
    }

    public static boolean isListEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static Activity scanForActivity(Context context, int i) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        int i2 = 0;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (i2 > i) {
                return BasePopupSupporterManager.getInstance().getTopActivity();
            }
            context = ((ContextWrapper) context).getBaseContext();
            i2++;
        }
        return BasePopupSupporterManager.getInstance().getTopActivity();
    }

    public static int range(int i, int i2, int i3) {
        return Math.max(i2, Math.min(i, i3));
    }
}
