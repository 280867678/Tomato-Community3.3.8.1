package com.tomatolive.library.p136ui.view.task;

import com.tomatolive.library.model.TaskBoxEntity;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.task.TaskBoxUtils */
/* loaded from: classes2.dex */
public class TaskBoxUtils {
    private List<TaskBoxEntity> mData = new ArrayList();
    private String openTime = "0";
    private boolean isPushInBackground = false;

    public String getOpenTime() {
        return this.openTime;
    }

    public void setOpenTime(String str) {
        this.openTime = str;
    }

    public boolean isPushInBackground() {
        return this.isPushInBackground;
    }

    public void setPushInBackground(boolean z) {
        this.isPushInBackground = z;
    }

    public List<TaskBoxEntity> getData() {
        return this.mData;
    }

    public void setData(List<TaskBoxEntity> list) {
        this.mData = list;
    }

    public void clear() {
        clearList();
        setOpenTime("0");
    }

    public void clearList() {
        this.mData.clear();
    }

    /* renamed from: com.tomatolive.library.ui.view.task.TaskBoxUtils$LazyHolder */
    /* loaded from: classes2.dex */
    private static class LazyHolder {
        private static final TaskBoxUtils INSTANCE = new TaskBoxUtils();

        private LazyHolder() {
        }
    }

    public static TaskBoxUtils getInstance() {
        return LazyHolder.INSTANCE;
    }
}
