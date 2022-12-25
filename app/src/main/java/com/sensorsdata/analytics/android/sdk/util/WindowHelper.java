package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.sensorsdata.analytics.android.sdk.AppSateManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public class WindowHelper {
    private static boolean sArrayListWindowViews = false;
    private static final String sCustomWindowPrefix = "/CustomWindow";
    private static final String sDialogWindowPrefix = "/DialogWindow";
    private static boolean sIsInitialized = false;
    private static Method sItemViewGetDataMethod = null;
    private static Class<?> sListMenuItemViewClazz = null;
    private static final String sMainWindowPrefix = "/MainWindow";
    private static Class sPhoneWindowClazz = null;
    private static Class sPopupWindowClazz = null;
    private static final String sPopupWindowPrefix = "/PopupWindow";
    private static boolean sViewArrayWindowViews;
    private static Object sWindowManger;
    private static Field viewsField;
    private static WeakHashMap<View, Long> showingToast = new WeakHashMap<>();
    private static Comparator<View> sViewSizeComparator = new Comparator<View>() { // from class: com.sensorsdata.analytics.android.sdk.util.WindowHelper.1
        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            int hashCode = view.hashCode();
            int hashCode2 = view2.hashCode();
            int currentRootWindowsHashCode = AppSateManager.getInstance().getCurrentRootWindowsHashCode();
            if (hashCode == currentRootWindowsHashCode) {
                return -1;
            }
            if (hashCode2 != currentRootWindowsHashCode) {
                return (view2.getWidth() * view2.getHeight()) - (view.getWidth() * view.getHeight());
            }
            return 1;
        }
    };

    public static String getMainWindowPrefix() {
        return sMainWindowPrefix;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x009f -> B:23:0x009f). Please submit an issue!!! */
    public static void init() {
        String str;
        if (!sIsInitialized) {
            try {
                Class<?> cls = Class.forName(Build.VERSION.SDK_INT >= 17 ? "android.view.WindowManagerGlobal" : "android.view.WindowManagerImpl");
                if (Build.VERSION.SDK_INT >= 17) {
                    str = "sDefaultWindowManager";
                } else {
                    str = Build.VERSION.SDK_INT >= 13 ? "sWindowManager" : "mWindowManager";
                }
                viewsField = cls.getDeclaredField("mViews");
                Field declaredField = cls.getDeclaredField(str);
                viewsField.setAccessible(true);
                if (viewsField.getType() == ArrayList.class) {
                    sArrayListWindowViews = true;
                } else if (viewsField.getType() == View[].class) {
                    sViewArrayWindowViews = true;
                }
                declaredField.setAccessible(true);
                sWindowManger = declaredField.get(null);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
            }
            try {
                sListMenuItemViewClazz = Class.forName("com.android.internal.view.menu.ListMenuItemView");
                sItemViewGetDataMethod = Class.forName("com.android.internal.view.menu.MenuView$ItemView").getDeclaredMethod("getItemData", new Class[0]);
            } catch (ClassNotFoundException | NoSuchMethodException unused2) {
            }
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.PhoneWindow$DecorView");
                    } catch (ClassNotFoundException unused3) {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.DecorView");
                    }
                } else {
                    sPhoneWindowClazz = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
                }
            } catch (ClassNotFoundException unused4) {
            }
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupDecorView");
                } else {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupViewContainer");
                }
            } catch (ClassNotFoundException unused5) {
            }
            sIsInitialized = true;
        }
    }

    private static View[] getWindowViews() {
        View[] viewArr = new View[0];
        Object obj = sWindowManger;
        if (obj == null) {
            Activity foregroundActivity = AppSateManager.getInstance().getForegroundActivity();
            return foregroundActivity != null ? new View[]{foregroundActivity.getWindow().getDecorView()} : viewArr;
        }
        View[] viewArr2 = null;
        try {
            if (sArrayListWindowViews) {
                viewArr2 = (View[]) ((ArrayList) viewsField.get(obj)).toArray(viewArr);
            } else if (sViewArrayWindowViews) {
                viewArr2 = (View[]) viewsField.get(obj);
            }
            if (viewArr2 != null) {
                viewArr = viewArr2;
            }
        } catch (Exception unused) {
        }
        return filterNullAndDismissToastView(viewArr);
    }

    public static View[] getSortedWindowViews() {
        View[] windowViews = getWindowViews();
        if (windowViews.length > 1) {
            View[] viewArr = (View[]) Arrays.copyOf(windowViews, windowViews.length);
            Arrays.sort(viewArr, sViewSizeComparator);
            return viewArr;
        }
        return windowViews;
    }

    private static View[] filterNullAndDismissToastView(View[] viewArr) {
        Long l;
        ArrayList arrayList = new ArrayList(viewArr.length);
        long currentTimeMillis = System.currentTimeMillis();
        for (View view : viewArr) {
            if (view != null && (showingToast.isEmpty() || (l = showingToast.get(view)) == null || currentTimeMillis <= l.longValue())) {
                arrayList.add(view);
            }
        }
        View[] viewArr2 = new View[arrayList.size()];
        arrayList.toArray(viewArr2);
        return viewArr2;
    }

    public static boolean isDecorView(Class cls) {
        if (!sIsInitialized) {
            init();
        }
        return cls == sPhoneWindowClazz || cls == sPopupWindowClazz;
    }

    @SuppressLint({"RestrictedApi"})
    private static Object getMenuItemData(View view) throws InvocationTargetException, IllegalAccessException {
        if (view.getClass() == sListMenuItemViewClazz) {
            return sItemViewGetDataMethod.invoke(view, new Object[0]);
        }
        if (!ViewUtil.instanceOfAndroidXListMenuItemView(view) && !ViewUtil.instanceOfSupportListMenuItemView(view) && !ViewUtil.instanceOfBottomNavigationItemView(view)) {
            return null;
        }
        return ViewUtil.getItemData(view);
    }

    private static View findMenuItemView(View view, MenuItem menuItem) throws InvocationTargetException, IllegalAccessException {
        if (getMenuItemData(view) == menuItem) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        int i = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (i >= viewGroup.getChildCount()) {
                return null;
            }
            View findMenuItemView = findMenuItemView(viewGroup.getChildAt(i), menuItem);
            if (findMenuItemView != null) {
                return findMenuItemView;
            }
            i++;
        }
    }

    public static View getClickView(MenuItem menuItem) {
        View findMenuItemView;
        View findMenuItemView2;
        if (menuItem == null) {
            return null;
        }
        init();
        View[] windowViews = getWindowViews();
        try {
            for (View view : windowViews) {
                if (view.getClass() == sPopupWindowClazz && (findMenuItemView2 = findMenuItemView(view, menuItem)) != null) {
                    return findMenuItemView2;
                }
            }
            for (View view2 : windowViews) {
                if (view2.getClass() != sPopupWindowClazz && (findMenuItemView = findMenuItemView(view2, menuItem)) != null) {
                    return findMenuItemView;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
        return null;
    }

    public static String getWindowPrefix(View view) {
        if (view.hashCode() == AppSateManager.getInstance().getCurrentRootWindowsHashCode()) {
            return getMainWindowPrefix();
        }
        return getSubWindowPrefix(view);
    }

    private static String getSubWindowPrefix(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && (layoutParams instanceof WindowManager.LayoutParams)) {
            int i = ((WindowManager.LayoutParams) layoutParams).type;
            if (i == 1) {
                return sMainWindowPrefix;
            }
            if (i < 99 && view.getClass() == sPhoneWindowClazz) {
                return sDialogWindowPrefix;
            }
            if (i < 1999 && view.getClass() == sPopupWindowClazz) {
                return sPopupWindowPrefix;
            }
            if (i < 2999) {
                return sCustomWindowPrefix;
            }
        }
        Class<?> cls = view.getClass();
        return cls == sPhoneWindowClazz ? sDialogWindowPrefix : cls == sPopupWindowClazz ? sPopupWindowPrefix : sCustomWindowPrefix;
    }

    public static boolean isMainWindow(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof WindowManager.LayoutParams) && ((WindowManager.LayoutParams) layoutParams).type == 1;
    }
}
