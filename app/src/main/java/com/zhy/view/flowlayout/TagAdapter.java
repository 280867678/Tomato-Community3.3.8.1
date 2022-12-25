package com.zhy.view.flowlayout;

import android.util.Log;
import android.view.View;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class TagAdapter<T> {
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<>();
    private OnDataChangedListener mOnDataChangedListener;
    private List<T> mTagDatas;

    /* loaded from: classes4.dex */
    interface OnDataChangedListener {
        void onChanged();
    }

    public abstract View getView(FlowLayout flowLayout, int i, T t);

    public boolean setSelected(int i, T t) {
        return false;
    }

    public TagAdapter(List<T> list) {
        this.mTagDatas = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.mOnDataChangedListener = onDataChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Deprecated
    public HashSet<Integer> getPreCheckedList() {
        return this.mCheckedPosList;
    }

    public int getCount() {
        List<T> list = this.mTagDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void notifyDataChanged() {
        OnDataChangedListener onDataChangedListener = this.mOnDataChangedListener;
        if (onDataChangedListener != null) {
            onDataChangedListener.onChanged();
        }
    }

    /* renamed from: getItem */
    public T mo6350getItem(int i) {
        return this.mTagDatas.get(i);
    }

    public void onSelected(int i, View view) {
        Log.d("zhy", "onSelected " + i);
    }

    public void unSelected(int i, View view) {
        Log.d("zhy", "unSelected " + i);
    }
}
