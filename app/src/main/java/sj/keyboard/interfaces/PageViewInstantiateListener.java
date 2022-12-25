package sj.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;
import sj.keyboard.data.PageEntity;

/* loaded from: classes4.dex */
public interface PageViewInstantiateListener<T extends PageEntity> {
    View instantiateItem(ViewGroup viewGroup, int i, T t);
}
