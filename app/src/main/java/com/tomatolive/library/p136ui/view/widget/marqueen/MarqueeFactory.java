package com.tomatolive.library.p136ui.view.widget.marqueen;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/* renamed from: com.tomatolive.library.ui.view.widget.marqueen.MarqueeFactory */
/* loaded from: classes4.dex */
public abstract class MarqueeFactory<T extends View, E> extends Observable {
    public static final String COMMAND_UPDATE_DATA = "UPDATE_DATA";
    protected List<E> dataList;
    protected Context mContext;
    private MarqueeView mMarqueeView;
    protected List<T> mViews;

    protected abstract T generateMarqueeItemView(E e);

    public MarqueeFactory(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<T> getMarqueeViews() {
        List<T> list = this.mViews;
        return list != null ? list : Collections.EMPTY_LIST;
    }

    public void setData(List<E> list) {
        if (list == null) {
            return;
        }
        this.dataList = list;
        this.mViews = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            this.mViews.add(generateMarqueeItemView(list.get(i)));
        }
        notifyDataChanged();
    }

    public List<E> getData() {
        return this.dataList;
    }

    private boolean isAttachedToMarqueeView() {
        return this.mMarqueeView != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void attachedToMarqueeView(MarqueeView marqueeView) {
        if (!isAttachedToMarqueeView()) {
            this.mMarqueeView = marqueeView;
            addObserver(marqueeView);
            return;
        }
        throw new IllegalStateException(String.format("The %s has been attached to the %s!", toString(), this.mMarqueeView.toString()));
    }

    private void notifyDataChanged() {
        if (isAttachedToMarqueeView()) {
            setChanged();
            notifyObservers(COMMAND_UPDATE_DATA);
        }
    }
}
