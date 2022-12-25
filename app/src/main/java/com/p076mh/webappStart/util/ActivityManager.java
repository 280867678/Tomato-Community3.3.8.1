package com.p076mh.webappStart.util;

import android.app.Activity;
import java.util.Iterator;
import java.util.Stack;

/* renamed from: com.mh.webappStart.util.ActivityManager */
/* loaded from: classes3.dex */
public class ActivityManager {
    private static ActivityManager instance;
    private Stack<Activity> activityStack;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (this.activityStack == null) {
            this.activityStack = new Stack<>();
        }
        this.activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (this.activityStack.contains(activity)) {
            this.activityStack.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        this.activityStack.remove(activity);
        activity.finish();
    }

    public void finishActivity(Class<?> cls) {
        Iterator<Activity> it2 = this.activityStack.iterator();
        while (it2.hasNext()) {
            Activity next = it2.next();
            if (next.getClass().equals(cls)) {
                finishActivity(next);
                return;
            }
        }
    }

    public Activity getActivity(Class<?> cls) {
        Stack<Activity> stack = this.activityStack;
        if (stack != null) {
            Iterator<Activity> it2 = stack.iterator();
            while (it2.hasNext()) {
                Activity next = it2.next();
                if (next.getClass().equals(cls)) {
                    return next;
                }
            }
            return null;
        }
        return null;
    }
}
