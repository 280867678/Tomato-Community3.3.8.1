package com.one.tomato.entity;

import java.util.List;

/* loaded from: classes3.dex */
public class TaskList {
    private boolean flag;
    private List<TaskBean> list;
    private List<TaskBean> spreadTaskList;

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean z) {
        this.flag = z;
    }

    public List<TaskBean> getSpreadTaskList() {
        return this.spreadTaskList;
    }

    public void setSpreadTaskList(List<TaskBean> list) {
        this.spreadTaskList = list;
    }

    public List<TaskBean> getList() {
        return this.list;
    }

    public void setList(List<TaskBean> list) {
        this.list = list;
    }
}
