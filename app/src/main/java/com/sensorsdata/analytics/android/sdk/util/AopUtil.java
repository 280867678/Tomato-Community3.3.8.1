package com.sensorsdata.analytics.android.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.C3089R;
import com.sensorsdata.analytics.android.sdk.Pathfinder;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataFragmentTitle;
import com.sensorsdata.analytics.android.sdk.visual.ViewNode;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AopUtil {
    private static ArrayList<String> sOSViewPackage = new ArrayList<String>() { // from class: com.sensorsdata.analytics.android.sdk.util.AopUtil.1
        {
            add("android##widget");
            add("android##support##v7##widget");
            add("android##support##design##widget");
            add("android##support##text##emoji##widget");
            add("androidx##appcompat##widget");
            add("androidx##emoji##widget");
            add("androidx##cardview##widget");
            add("com##google##android##material");
        }
    };

    public static int getChildIndex(ViewParent viewParent, View view) {
        try {
            if (!(viewParent instanceof ViewGroup)) {
                return -1;
            }
            ViewGroup viewGroup = (ViewGroup) viewParent;
            String viewId = getViewId(view);
            String canonicalName = view.getClass().getCanonicalName();
            int i = 0;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (Pathfinder.hasClassName(childAt, canonicalName)) {
                    String viewId2 = getViewId(childAt);
                    if ((viewId == null || viewId.equals(viewId2)) && childAt == view) {
                        return i;
                    }
                    i++;
                }
            }
            return -1;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return -1;
        }
    }

    public static String traverseView(StringBuilder sb, ViewGroup viewGroup) {
        if (sb == null) {
            try {
                sb = new StringBuilder();
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return sb != null ? sb.toString() : "";
            }
        }
        if (viewGroup == null) {
            return sb.toString();
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                if (childAt instanceof ViewGroup) {
                    traverseView(sb, (ViewGroup) childAt);
                } else if (!isViewIgnored(childAt)) {
                    String viewText = getViewText(childAt);
                    if (!TextUtils.isEmpty(viewText)) {
                        sb.append(viewText);
                        sb.append("-");
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String getViewText(View view) {
        Class<?> cls;
        Method method;
        if (view instanceof EditText) {
            return "";
        }
        CharSequence charSequence = null;
        try {
            cls = Class.forName("android.support.v7.widget.SwitchCompat");
        } catch (Exception unused) {
            cls = null;
        }
        if (cls == null) {
            try {
                cls = Class.forName("androidx.appcompat.widget.SwitchCompat");
            } catch (Exception unused2) {
            }
        }
        try {
            if (view instanceof CheckBox) {
                charSequence = ((CheckBox) view).getText();
            } else if (cls != null && cls.isInstance(view)) {
                if (((CompoundButton) view).isChecked()) {
                    method = view.getClass().getMethod("getTextOn", new Class[0]);
                } else {
                    method = view.getClass().getMethod("getTextOff", new Class[0]);
                }
                charSequence = (String) method.invoke(view, new Object[0]);
            } else if (view instanceof RadioButton) {
                charSequence = ((RadioButton) view).getText();
            } else if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                if (toggleButton.isChecked()) {
                    charSequence = toggleButton.getTextOn();
                } else {
                    charSequence = toggleButton.getTextOff();
                }
            } else if (view instanceof Button) {
                charSequence = ((Button) view).getText();
            } else if (view instanceof CheckedTextView) {
                charSequence = ((CheckedTextView) view).getText();
            } else if (view instanceof TextView) {
                charSequence = ((TextView) view).getText();
            } else if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (!TextUtils.isEmpty(imageView.getContentDescription())) {
                    charSequence = imageView.getContentDescription().toString();
                }
            } else {
                charSequence = view.getContentDescription();
            }
            if (TextUtils.isEmpty(charSequence) && (view instanceof TextView)) {
                charSequence = ((TextView) view).getHint();
            }
            if (!TextUtils.isEmpty(charSequence)) {
                return charSequence.toString();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return "";
    }

    public static Activity getActivityFromContext(Context context, View view) {
        Object tag;
        Activity activity;
        if (context != null) {
            try {
                if (context instanceof Activity) {
                    activity = (Activity) context;
                } else if (context instanceof ContextWrapper) {
                    while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
                        context = ((ContextWrapper) context).getBaseContext();
                    }
                    if (!(context instanceof Activity)) {
                        return null;
                    }
                    activity = (Activity) context;
                } else if (view == null || (tag = view.getTag(C3089R.C3090id.sensors_analytics_tag_view_activity)) == null || !(tag instanceof Activity)) {
                    return null;
                } else {
                    activity = (Activity) tag;
                }
                return activity;
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return null;
            }
        }
        return null;
    }

    public static void getScreenNameAndTitleFromFragment(JSONObject jSONObject, Object obj, Activity activity) {
        String str;
        SensorsDataFragmentTitle sensorsDataFragmentTitle;
        JSONObject trackProperties;
        try {
            String str2 = null;
            if (!(obj instanceof ScreenAutoTracker) || (trackProperties = ((ScreenAutoTracker) obj).getTrackProperties()) == null) {
                str = null;
            } else {
                str = trackProperties.has(AopConstants.SCREEN_NAME) ? trackProperties.optString(AopConstants.SCREEN_NAME) : null;
                if (trackProperties.has("title")) {
                    str2 = trackProperties.optString("title");
                }
            }
            if (TextUtils.isEmpty(str2) && obj.getClass().isAnnotationPresent(SensorsDataFragmentTitle.class) && (sensorsDataFragmentTitle = (SensorsDataFragmentTitle) obj.getClass().getAnnotation(SensorsDataFragmentTitle.class)) != null) {
                str2 = sensorsDataFragmentTitle.title();
            }
            boolean isEmpty = TextUtils.isEmpty(str2);
            boolean isEmpty2 = TextUtils.isEmpty(str);
            if (isEmpty || isEmpty2) {
                if (activity == null) {
                    activity = getActivityFromFragment(obj);
                }
                if (activity != null) {
                    if (isEmpty) {
                        str2 = SensorsDataUtils.getActivityTitle(activity);
                    }
                    if (isEmpty2) {
                        str = String.format(Locale.CHINA, "%s|%s", activity.getClass().getCanonicalName(), obj.getClass().getCanonicalName());
                    }
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put("title", str2);
            }
            if (TextUtils.isEmpty(str)) {
                str = obj.getClass().getCanonicalName();
            }
            jSONObject.put(AopConstants.SCREEN_NAME, str);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static Activity getActivityFromFragment(Object obj) {
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                Method method = obj.getClass().getMethod("getActivity", new Class[0]);
                if (method == null) {
                    return null;
                }
                return (Activity) method.invoke(obj, new Object[0]);
            } catch (Exception unused) {
                return null;
            }
        }
        return null;
    }

    public static JSONObject buildTitleAndScreenName(Activity activity) {
        JSONObject trackProperties;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AopConstants.SCREEN_NAME, activity.getClass().getCanonicalName());
            String activityTitle = getActivityTitle(activity);
            if (!TextUtils.isEmpty(activityTitle)) {
                jSONObject.put("title", activityTitle);
            }
            if ((activity instanceof ScreenAutoTracker) && (trackProperties = ((ScreenAutoTracker) activity).getTrackProperties()) != null) {
                if (trackProperties.has(AopConstants.SCREEN_NAME)) {
                    jSONObject.put(AopConstants.SCREEN_NAME, trackProperties.optString(AopConstants.SCREEN_NAME));
                }
                if (trackProperties.has("title")) {
                    jSONObject.put("title", trackProperties.optString("title"));
                }
            }
            return jSONObject;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return new JSONObject();
        }
    }

    public static String getCompoundButtonText(View view) {
        Method method;
        try {
            if (((CompoundButton) view).isChecked()) {
                method = view.getClass().getMethod("getTextOn", new Class[0]);
            } else {
                method = view.getClass().getMethod("getTextOff", new Class[0]);
            }
            return (String) method.invoke(view, new Object[0]);
        } catch (Exception unused) {
            return "UNKNOWN";
        }
    }

    public static String getViewId(View view) {
        String str;
        try {
            String str2 = (String) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_id);
            try {
                return (!TextUtils.isEmpty(str2) || view.getId() == -1) ? str2 : view.getContext().getResources().getResourceEntryName(view.getId());
            } catch (Exception unused) {
                return str;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public static String getViewType(String str, String str2) {
        return TextUtils.isEmpty(str) ? str2 : (!TextUtils.isEmpty(str2) && isOSViewByPackage(str)) ? str2 : str;
    }

    public static String getViewGroupTypeByReflect(View view) {
        String canonicalName = view.getClass().getCanonicalName();
        Class<?> classByName = getClassByName("android.support.v7.widget.CardView");
        if (classByName != null && classByName.isInstance(view)) {
            return getViewType(canonicalName, "CardView");
        }
        Class<?> classByName2 = getClassByName("androidx.cardview.widget.CardView");
        if (classByName2 != null && classByName2.isInstance(view)) {
            return getViewType(canonicalName, "CardView");
        }
        Class<?> classByName3 = getClassByName("android.support.design.widget.NavigationView");
        if (classByName3 != null && classByName3.isInstance(view)) {
            return getViewType(canonicalName, "NavigationView");
        }
        Class<?> classByName4 = getClassByName("com.google.android.material.navigation.NavigationView");
        return (classByName4 == null || !classByName4.isInstance(view)) ? canonicalName : getViewType(canonicalName, "NavigationView");
    }

    public static String getViewTypeByReflect(View view) {
        String canonicalName = view.getClass().getCanonicalName();
        Class<?> classByName = getClassByName("android.widget.Switch");
        if (classByName != null && classByName.isInstance(view)) {
            return getViewType(canonicalName, "Switch");
        }
        Class<?> classByName2 = getClassByName("android.support.v7.widget.SwitchCompat");
        if (classByName2 != null && classByName2.isInstance(view)) {
            return getViewType(canonicalName, "SwitchCompat");
        }
        Class<?> classByName3 = getClassByName("androidx.appcompat.widget.SwitchCompat");
        return (classByName3 == null || !classByName3.isInstance(view)) ? canonicalName : getViewType(canonicalName, "SwitchCompat");
    }

    private static Class<?> getClassByName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static boolean isViewIgnored(Class cls) {
        if (cls == null) {
            return true;
        }
        try {
            List<Class> ignoredViewTypeList = SensorsDataAPI.sharedInstance().getIgnoredViewTypeList();
            if (ignoredViewTypeList.isEmpty()) {
                return false;
            }
            for (Class cls2 : ignoredViewTypeList) {
                if (cls2.isAssignableFrom(cls)) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean isViewIgnored(View view) {
        if (view == null) {
            return true;
        }
        try {
            List<Class> ignoredViewTypeList = SensorsDataAPI.sharedInstance().getIgnoredViewTypeList();
            if (ignoredViewTypeList != null) {
                for (Class cls : ignoredViewTypeList) {
                    if (cls.isAssignableFrom(view.getClass())) {
                        return true;
                    }
                }
            }
            return "1".equals(view.getTag(C3089R.C3090id.sensors_analytics_tag_view_ignored));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return true;
        }
    }

    private static String getActivityTitle(Activity activity) {
        PackageManager packageManager;
        if (activity != null) {
            try {
                String charSequence = !TextUtils.isEmpty(activity.getTitle()) ? activity.getTitle().toString() : null;
                if (Build.VERSION.SDK_INT >= 11) {
                    String toolbarTitle = SensorsDataUtils.getToolbarTitle(activity);
                    if (!TextUtils.isEmpty(toolbarTitle)) {
                        charSequence = toolbarTitle;
                    }
                }
                if (!TextUtils.isEmpty(charSequence) || (packageManager = activity.getPackageManager()) == null) {
                    return charSequence;
                }
                ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
                return !TextUtils.isEmpty(activityInfo.loadLabel(packageManager)) ? activityInfo.loadLabel(packageManager).toString() : charSequence;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static void mergeJSONObject(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSONObject.get(next);
                if (obj instanceof Date) {
                    jSONObject2.put(next, DateFormatUtils.formatDate((Date) obj));
                } else {
                    jSONObject2.put(next, obj);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private static boolean isOSViewByPackage(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String replace = str.replace(".", "##");
        Iterator<String> it2 = sOSViewPackage.iterator();
        while (it2.hasNext()) {
            if (replace.startsWith(it2.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean injectClickInfo(View view, JSONObject jSONObject, boolean z) {
        if (view != null && jSONObject != null) {
            try {
                Activity activityFromContext = getActivityFromContext(view.getContext(), view);
                addViewPathProperties(activityFromContext, view, jSONObject);
                String viewId = getViewId(view);
                if (!TextUtils.isEmpty(viewId)) {
                    jSONObject.put(AopConstants.ELEMENT_ID, viewId);
                }
                if (activityFromContext != null) {
                    SensorsDataUtils.mergeJSONObject(buildTitleAndScreenName(activityFromContext), jSONObject);
                }
                if (!ViewUtil.isTrackEvent(view, z)) {
                    return false;
                }
                ViewNode viewContentAndType = ViewUtil.getViewContentAndType(view);
                String viewContent = viewContentAndType.getViewContent();
                if (!TextUtils.isEmpty(viewContent)) {
                    jSONObject.put(AopConstants.ELEMENT_CONTENT, viewContent);
                }
                jSONObject.put(AopConstants.ELEMENT_TYPE, viewContentAndType.getViewType());
                Object fragmentFromView = getFragmentFromView(view);
                if (fragmentFromView != null) {
                    getScreenNameAndTitleFromFragment(jSONObject, fragmentFromView, activityFromContext);
                }
                JSONObject jSONObject2 = (JSONObject) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_properties);
                if (jSONObject2 == null) {
                    return true;
                }
                mergeJSONObject(jSONObject2, jSONObject);
                return true;
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
        return false;
    }

    public static Object getFragmentFromView(View view) {
        if (view != null) {
            try {
                String str = (String) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_fragment_name);
                String str2 = (String) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_fragment_name2);
                if (TextUtils.isEmpty(str2)) {
                    str2 = str;
                }
                if (TextUtils.isEmpty(str2)) {
                    return null;
                }
                return Class.forName(str2).newInstance();
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return null;
            }
        }
        return null;
    }

    public static void addViewPathProperties(Activity activity, View view, JSONObject jSONObject) {
        try {
            if (!SensorsDataAPI.sharedInstance().isHeatMapEnabled() && !SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled()) {
                return;
            }
            if ((activity != null && !SensorsDataAPI.sharedInstance().isHeatMapActivity(activity.getClass()) && !SensorsDataAPI.sharedInstance().isVisualizedAutoTrackActivity(activity.getClass())) || view == null) {
                return;
            }
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            String elementSelector = ViewUtil.getElementSelector(view);
            if (!TextUtils.isEmpty(elementSelector)) {
                jSONObject.put(AopConstants.ELEMENT_SELECTOR, elementSelector);
            }
            ViewNode viewPathAndPosition = ViewUtil.getViewPathAndPosition(view);
            if (viewPathAndPosition == null) {
                return;
            }
            if (SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled() && !TextUtils.isEmpty(viewPathAndPosition.getViewPath())) {
                jSONObject.put(AopConstants.ELEMENT_PATH, viewPathAndPosition.getViewPath());
            }
            if (TextUtils.isEmpty(viewPathAndPosition.getViewPosition())) {
                return;
            }
            jSONObject.put(AopConstants.ELEMENT_POSITION, viewPathAndPosition.getViewPosition());
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }
}
