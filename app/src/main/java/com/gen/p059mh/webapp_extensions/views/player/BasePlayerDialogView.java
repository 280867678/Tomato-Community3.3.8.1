package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.gen.mh.webapp_extensions.views.player.BasePlayerDialogView */
/* loaded from: classes2.dex */
public abstract class BasePlayerDialogView<T> implements View.OnClickListener {
    protected View contentView;
    protected T data;
    protected Context mContext;
    protected PlayerDialogCallback playerDialogCallback;

    public void destroyView() {
    }

    protected abstract int getViewForRes();

    /* JADX INFO: Access modifiers changed from: protected */
    public void initData() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initView() {
    }

    public void setHorizontal(boolean z) {
    }

    public BasePlayerDialogView(Context context) {
        this.mContext = context;
        this.contentView = LayoutInflater.from(context).inflate(getViewForRes(), (ViewGroup) null);
        initView();
    }

    public View provideView() {
        return this.contentView;
    }

    public void setPlayerDialogCallback(PlayerDialogCallback playerDialogCallback) {
        this.playerDialogCallback = playerDialogCallback;
    }

    public void setData(T t) {
        this.data = t;
        initData();
    }
}
