package com.gen.p059mh.webapp_extensions.utils;

import android.app.Activity;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import com.gen.p059mh.webapp_extensions.R$anim;
import com.gen.p059mh.webapp_extensions.fragments.WebAppFragment;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.gen.mh.webapp_extensions.utils.Tool */
/* loaded from: classes2.dex */
public class Tool {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    static {
        Pattern.compile("[A-Z]");
    }

    public static String lineToHump(char c, String str) {
        String lowerCase = str.toLowerCase();
        linePattern = Pattern.compile(c + "(\\w)");
        Matcher matcher = linePattern.matcher(lowerCase);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static void checkOrientation(String str, Activity activity) {
        if (activity == null) {
            return;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 3005871) {
            if (hashCode != 729267099) {
                if (hashCode == 1430647483 && str.equals("landscape")) {
                    c = 0;
                }
            } else if (str.equals("portrait")) {
                c = 1;
            }
        } else if (str.equals("auto")) {
            c = 2;
        }
        if (c == 0) {
            activity.setRequestedOrientation(0);
        } else if (c == 1) {
            activity.setRequestedOrientation(1);
        } else if (c != 2) {
        } else {
            activity.setRequestedOrientation(4);
        }
    }

    public static List<WebAppFragment> getFragmentList(FragmentManager fragmentManager) {
        ArrayList arrayList = new ArrayList();
        if (fragmentManager != null) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment != null && (fragment instanceof WebAppFragment)) {
                    arrayList.add((WebAppFragment) fragment);
                }
            }
        }
        return arrayList;
    }

    public static WebAppFragment getTopFragment(FragmentManager fragmentManager) {
        List<WebAppFragment> fragmentList = getFragmentList(fragmentManager);
        return fragmentList.get(fragmentList.size() - 1);
    }

    public static void addFragment(WebAppFragment webAppFragment, FragmentManager fragmentManager, int i) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R$anim.web_sdk_translate_right_to_center, R$anim.web_sdk_translate_center_to_left, R$anim.web_sdk_translate_left_to_center, R$anim.web_sdk_translate_center_to_right).add(i, webAppFragment).addToBackStack("web_app_back").commitAllowingStateLoss();
        }
    }

    public static void popBackStackImmediate(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.popBackStackImmediate();
            getTopFragment(fragmentManager).processConfigs();
        }
    }

    public static void dismiss(FragmentManager fragmentManager, Fragment fragment, WebViewLaunchFragment webViewLaunchFragment) {
        fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        webViewLaunchFragment.processConfigs();
    }

    public static void display(FragmentManager fragmentManager, WebViewLaunchFragment webViewLaunchFragment) {
        fragmentManager.beginTransaction().show(webViewLaunchFragment).commitAllowingStateLoss();
        webViewLaunchFragment.processConfigs();
    }
}
