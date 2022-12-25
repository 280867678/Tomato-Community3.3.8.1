package com.chad.library.adapter.base.util;

/* loaded from: classes2.dex */
public class TouchEventUtil {
    public static String getTouchAction(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "Unknow:id=" + i : "ACTION_OUTSIDE" : "ACTION_CANCEL" : "ACTION_MOVE" : "ACTION_UP" : "ACTION_DOWN";
    }
}
