package sj.keyboard.interfaces;

import android.view.ViewGroup;
import sj.keyboard.adpater.EmoticonsAdapter;

/* loaded from: classes4.dex */
public interface EmoticonDisplayListener<T> {
    void onBindView(int i, ViewGroup viewGroup, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean z);
}
