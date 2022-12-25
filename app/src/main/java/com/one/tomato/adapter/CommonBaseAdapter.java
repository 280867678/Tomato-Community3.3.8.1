package com.one.tomato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public abstract void convert(ViewHolder viewHolder, T t, int i);

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 1;
    }

    public CommonBaseAdapter(Context context, List<T> list, int i) {
        this.mContext = context;
        LayoutInflater.from(this.mContext);
        this.mDatas = list;
        this.mItemLayoutId = i;
        if (this.mDatas == null) {
            this.mDatas = new ArrayList();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mDatas.size();
    }

    @Override // android.widget.Adapter
    public T getItem(int i) {
        return this.mDatas.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = getViewHolder(i, view, viewGroup);
        convert(viewHolder, getItem(i), i);
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int i, View view, ViewGroup viewGroup) {
        return ViewHolder.get(this.mContext, view, viewGroup, this.mItemLayoutId, i);
    }

    public void setList(List<T> list) {
        clear();
        this.mDatas = list;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public void clear() {
        List<T> list = this.mDatas;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.mDatas.clear();
    }
}
