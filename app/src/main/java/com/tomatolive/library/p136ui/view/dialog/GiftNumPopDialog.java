package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.GiftBatchItemEntity;
import com.tomatolive.library.p136ui.view.divider.RecyclerViewCornerRadius;
import com.tomatolive.library.p136ui.view.gift.GiftNumAdapter;
import com.tomatolive.library.utils.SystemUtils;
import java.util.ArrayList;
import java.util.List;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.GiftNumPopDialog */
/* loaded from: classes3.dex */
public class GiftNumPopDialog extends BasePopupWindow {
    private List<GiftBatchItemEntity> giftBatchItemEntityList;
    private OnGiftNumSelectListener listener;
    private GiftNumAdapter mAdapter;
    private RecyclerViewCornerRadius radiusItemDecoration;
    private RecyclerView recyclerView;

    /* renamed from: com.tomatolive.library.ui.view.dialog.GiftNumPopDialog$OnGiftNumSelectListener */
    /* loaded from: classes3.dex */
    public interface OnGiftNumSelectListener {
        void onGiftNumSelect(GiftBatchItemEntity giftBatchItemEntity);
    }

    public GiftNumPopDialog(Context context) {
        super(context);
        onInit();
    }

    @Override // razerdp.basepopup.BasePopup
    public View onCreateContentView() {
        return createPopupById(R$layout.fq_dialog_pop_gift_num_choose);
    }

    private void onInit() {
        this.giftBatchItemEntityList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R$id.fq_rv_num_list);
        this.mAdapter = new GiftNumAdapter(R$layout.fq_item_gift_num, this.giftBatchItemEntityList);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.radiusItemDecoration = new RecyclerViewCornerRadius();
        this.radiusItemDecoration.setCornerRadius((int) SystemUtils.dp2px(10.0f));
        this.recyclerView.addItemDecoration(this.radiusItemDecoration);
        this.mAdapter.bindToRecyclerView(this.recyclerView);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftNumPopDialog$3cCa1a8pkFSCM1VyhPIEyLC8cag
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                GiftNumPopDialog.this.lambda$onInit$0$GiftNumPopDialog(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$onInit$0$GiftNumPopDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        GiftBatchItemEntity giftBatchItemEntity = (GiftBatchItemEntity) baseQuickAdapter.getItem(i);
        if (giftBatchItemEntity == null) {
            return;
        }
        this.mAdapter.setSelectPos(i);
        if (this.listener == null) {
            return;
        }
        dismiss();
        this.listener.onGiftNumSelect(giftBatchItemEntity);
    }

    public void updateAdapterData(List<GiftBatchItemEntity> list) {
        RecyclerView recyclerView;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.giftBatchItemEntityList.clear();
        this.giftBatchItemEntityList.addAll(list);
        this.giftBatchItemEntityList.add(new GiftBatchItemEntity("一心一意", 1));
        this.mAdapter.setSelectPos(-1);
        RecyclerViewCornerRadius recyclerViewCornerRadius = this.radiusItemDecoration;
        if (recyclerViewCornerRadius == null || (recyclerView = this.recyclerView) == null) {
            return;
        }
        recyclerViewCornerRadius.setRecyclerViewRoundRect(recyclerView);
    }

    public GiftNumPopDialog setOnGiftNumSelectListener(OnGiftNumSelectListener onGiftNumSelectListener) {
        this.listener = onGiftNumSelectListener;
        return this;
    }
}
