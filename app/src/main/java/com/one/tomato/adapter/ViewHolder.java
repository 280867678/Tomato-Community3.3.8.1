package com.one.tomato.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class ViewHolder {
    private View mConvertView;
    private final SparseArray<View> mViews = new SparseArray<>();

    private ViewHolder(Context context, ViewGroup viewGroup, int i, int i2) {
        this.mConvertView = LayoutInflater.from(context).inflate(i, viewGroup, false);
        this.mConvertView.setTag(R.string.app_name, this);
    }

    public static ViewHolder get(Context context, View view, ViewGroup viewGroup, int i, int i2) {
        if (view == null) {
            return new ViewHolder(context, viewGroup, i, i2);
        }
        return (ViewHolder) view.getTag(R.string.app_name);
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public <T extends View> T getView(int i) {
        T t = (T) this.mViews.get(i);
        if (t == null) {
            T t2 = (T) this.mConvertView.findViewById(i);
            this.mViews.put(i, t2);
            return t2;
        }
        return t;
    }

    public ViewHolder setText(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        ((TextView) getView(i)).setText(str);
        return this;
    }
}
