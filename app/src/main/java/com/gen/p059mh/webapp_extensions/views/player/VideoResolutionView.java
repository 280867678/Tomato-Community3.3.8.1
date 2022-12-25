package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.adapter.ResolutionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.player.VideoResolutionView */
/* loaded from: classes2.dex */
public class VideoResolutionView extends BasePlayerDialogView {
    public static int SELECT_POSITION;
    static List<Map> mapList = new ArrayList();
    RecyclerView recyclerView;
    ResolutionAdapter resolutionAdapter;

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    public VideoResolutionView(Context context) {
        super(context);
    }

    public static String provideSelectName() {
        return (String) mapList.get(SELECT_POSITION).get("title");
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.dialog_web_sdk_resolution;
    }

    public void setClickListener(ResolutionAdapter.ClickListener clickListener) {
        this.resolutionAdapter.setOnClickListener(clickListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.recyclerView = (RecyclerView) this.contentView.findViewById(R$id.rv_recycler_resolution);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.resolutionAdapter = new ResolutionAdapter();
        this.recyclerView.setAdapter(this.resolutionAdapter);
    }

    public static void setMapList(List<Map> list) {
        mapList = list;
    }

    public void refresh(List<Map> list) {
        this.resolutionAdapter.setmList(list);
    }
}
