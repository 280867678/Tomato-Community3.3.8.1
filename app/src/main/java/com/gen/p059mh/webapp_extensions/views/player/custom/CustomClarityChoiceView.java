package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView;
import com.gen.p059mh.webapp_extensions.views.player.PlayerDialogCallback;
import com.gen.p059mh.webapp_extensions.views.player.custom.CustomResolutionAdapter;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomClarityChoiceView */
/* loaded from: classes2.dex */
public class CustomClarityChoiceView extends BasePlayerDialogView<List> {
    private CustomResolutionAdapter commonAdapter;
    private int defaultPosition;
    private RecyclerView recyclerView;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomClarityChoiceView$ClarityChoiceViewCallback */
    /* loaded from: classes2.dex */
    public interface ClarityChoiceViewCallback extends PlayerDialogCallback {
        void onChoiceClarity(int i);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    public CustomClarityChoiceView(Context context, int i) {
        super(context);
        this.defaultPosition = i;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.custom_view_video_clarity_layout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.recyclerView = (RecyclerView) this.contentView.findViewById(R$id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.commonAdapter = new CustomResolutionAdapter(R$layout.custom_item_player_resolution);
        this.recyclerView.setAdapter(this.commonAdapter);
        this.commonAdapter.setResolutionClickListener(new CustomResolutionAdapter.ResolutionClickListener() { // from class: com.gen.mh.webapp_extensions.views.player.custom.CustomClarityChoiceView.1
            @Override // com.gen.p059mh.webapp_extensions.views.player.custom.CustomResolutionAdapter.ResolutionClickListener
            public void onResolutionItemClick(boolean z, int i, ResourceEntity resourceEntity) {
                if (!z && ((BasePlayerDialogView) CustomClarityChoiceView.this).playerDialogCallback != null && (((BasePlayerDialogView) CustomClarityChoiceView.this).playerDialogCallback instanceof ClarityChoiceViewCallback)) {
                    ((ClarityChoiceViewCallback) ((BasePlayerDialogView) CustomClarityChoiceView.this).playerDialogCallback).onChoiceClarity(i);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initData() {
        super.initData();
        if (this.commonAdapter != null) {
            Logger.m4113i(((List) this.data).toString());
            this.commonAdapter.refreshData((List) this.data, this.defaultPosition);
        }
    }
}
