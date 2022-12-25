package com.one.tomato.mvp.base;

import android.app.Activity;
import android.os.Process;
import com.one.tomato.thirdpart.ipfs.IpfsUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UpdateManager;
import com.zzhoujay.richtext.RichText;
import java.util.Stack;

/* loaded from: classes3.dex */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
        activityStack = new Stack<>();
        new Stack();
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    public void finishActivity(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    public void exitApp() {
        try {
            IpfsUtil.release();
            finishAllActivity();
            UpdateManager.getUpdateManager().onDestroy();
            ToastUtil.destroy();
            RichText.recycle();
            System.gc();
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            activityStack.clear();
            e.printStackTrace();
        }
    }
}
