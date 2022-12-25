package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.adapter.SettingViewAdapter;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.VideoSettingView */
/* loaded from: classes2.dex */
public class VideoSettingView extends BasePlayerDialogView {
    private RecyclerView recyclerView;
    private SettingViewAdapter settingViewAdapter;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.VideoSettingView$SettingClickCallBack */
    /* loaded from: classes2.dex */
    public interface SettingClickCallBack {
        void onSettingCallBack(int i);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    public void setSettingClickCallBack(SettingClickCallBack settingClickCallBack) {
        this.settingViewAdapter.setSettingClickCallBack(settingClickCallBack);
    }

    public VideoSettingView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.web_sdk_video_setting;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.recyclerView = (RecyclerView) this.contentView.findViewById(R$id.rv_video_setting);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.settingViewAdapter = new SettingViewAdapter();
        this.recyclerView.setAdapter(this.settingViewAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initData() {
        super.initData();
    }

    public void refreshData(List<SettingViewAdapter.SettingData> list) {
        this.settingViewAdapter.refresh(list);
    }
}
