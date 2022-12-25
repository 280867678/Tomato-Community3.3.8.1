package razerdp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;

/* loaded from: classes4.dex */
public class PopupUiUtils {
    private static int statusBarHeight;
    private static volatile Point[] mRealSizes = new Point[2];
    private static final Point point = new Point();
    private static Rect navigationBarRect = new Rect();

    private static int getNavigationBarHeightInternal(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", Platform.ANDROID);
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @SuppressLint({"NewApi"})
    public static boolean checkHasNavigationBar(Context context) {
        navigationBarRect.setEmpty();
        Activity scanForActivity = PopupUtils.scanForActivity(context, 15);
        if (scanForActivity == null) {
            return false;
        }
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT < 21) {
            Window window = scanForActivity.getWindow();
            if (window == null) {
                return false;
            }
            window.getWindowManager().getDefaultDisplay().getRealSize(point);
            View decorView = window.getDecorView();
            if (2 == configuration.orientation) {
                boolean z = point.x != decorView.findViewById(16908290).getWidth();
                if (z) {
                    if (decorView.getLeft() > 0) {
                        navigationBarRect.set(1, 0, 0, 0);
                    } else {
                        navigationBarRect.set(0, 0, 1, 0);
                    }
                }
                return z;
            }
            decorView.getWindowVisibleDisplayFrame(navigationBarRect);
            boolean z2 = navigationBarRect.bottom != point.y;
            navigationBarRect.setEmpty();
            if (z2) {
                navigationBarRect.set(0, 0, 0, 1);
            }
            return z2;
        }
        ViewGroup viewGroup = (ViewGroup) scanForActivity.getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getId() != -1) {
                try {
                    String resourceEntryName = scanForActivity.getResources().getResourceEntryName(childAt.getId());
                    if (!TextUtils.isEmpty(resourceEntryName) && childAt.getId() != -1 && childAt.isShown() && (TextUtils.equals("navigationbarbackground", resourceEntryName.toLowerCase()) || TextUtils.equals("immersion_navigation_bar_view", resourceEntryName.toLowerCase()))) {
                        if (configuration.orientation == 2) {
                            if (childAt.getWidth() > childAt.getHeight()) {
                                navigationBarRect.set(0, 0, 0, 1);
                            } else if (childAt.getLeft() == 0) {
                                navigationBarRect.set(1, 0, 0, 0);
                            } else {
                                navigationBarRect.set(0, 0, 1, 0);
                            }
                        } else {
                            navigationBarRect.set(0, 0, 0, 1);
                        }
                        return true;
                    }
                } catch (Exception unused) {
                }
            }
        }
        return false;
    }

    public static int getScreenHeightCompat(Context context) {
        char c = 1;
        int i = 0;
        if (context.getResources().getConfiguration().orientation == 1) {
            c = 0;
        }
        if (Build.VERSION.SDK_INT < 17) {
            int i2 = context.getResources().getDisplayMetrics().heightPixels;
            if (c == 0) {
                i = getFixedPortratiNavigationBarHeight(context);
            }
            return i2 + i;
        }
        if (mRealSizes[c] == null) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                int i3 = context.getResources().getDisplayMetrics().heightPixels;
                if (c == 0) {
                    i = getFixedPortratiNavigationBarHeight(context);
                }
                return i3 + i;
            }
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point2 = new Point();
            defaultDisplay.getRealSize(point2);
            mRealSizes[c] = point2;
        }
        if (checkHasNavigationBar(context) && navigationBarRect.bottom != 0) {
            i = getNavigationBarHeightInternal(context);
        }
        return mRealSizes[c].y - i;
    }

    public static int getScreenWidthCompat(Context context) {
        int i = 0;
        char c = context.getResources().getConfiguration().orientation == 1 ? (char) 0 : (char) 1;
        if (Build.VERSION.SDK_INT < 17) {
            int i2 = context.getResources().getDisplayMetrics().widthPixels;
            if (c == 1) {
                i = getFixedLandScapeNavigationBarHeight(context);
            }
            return i2 + i;
        }
        if (mRealSizes[c] == null) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                int i3 = context.getResources().getDisplayMetrics().widthPixels;
                if (c == 1) {
                    i = getFixedLandScapeNavigationBarHeight(context);
                }
                return i3 + i;
            }
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point2 = new Point();
            defaultDisplay.getRealSize(point2);
            mRealSizes[c] = point2;
        }
        if (checkHasNavigationBar(context) && navigationBarRect.right != 0) {
            i = getNavigationBarHeightInternal(context);
        }
        return mRealSizes[c].x - i;
    }

    private static int getFixedPortratiNavigationBarHeight(Context context) {
        if (checkHasNavigationBar(context) || navigationBarRect.bottom == 0) {
            return 0;
        }
        return getNavigationBarHeightInternal(context);
    }

    private static int getFixedLandScapeNavigationBarHeight(Context context) {
        if (checkHasNavigationBar(context) || navigationBarRect.right == 0) {
            return 0;
        }
        return getNavigationBarHeightInternal(context);
    }

    public static int getScreenOrientation(Context context) {
        if (context == null) {
            return 1;
        }
        return context.getResources().getConfiguration().orientation;
    }

    public static int getStatusBarHeight(Context context) {
        checkStatusBarHeight(context);
        return statusBarHeight;
    }

    private static void checkStatusBarHeight(Context context) {
        if (statusBarHeight != 0 || context == null) {
            return;
        }
        int i = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        statusBarHeight = i;
    }
}
