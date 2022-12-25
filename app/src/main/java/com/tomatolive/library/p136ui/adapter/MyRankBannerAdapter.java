package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.p136ui.activity.home.RankingNewActivity;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGAOnNoDoubleClickListener;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.MyRankBannerAdapter */
/* loaded from: classes3.dex */
public class MyRankBannerAdapter implements BGABanner.Adapter<View, String> {
    private List<IndexRankEntity> indexRanks;
    private Context mContext;

    public MyRankBannerAdapter(Context context) {
        this.mContext = context;
    }

    public MyRankBannerAdapter(List<IndexRankEntity> list, Context context) {
        this.mContext = context;
        this.indexRanks = list;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Adapter
    public void fillBannerItem(BGABanner bGABanner, View view, @Nullable String str, int i) {
        initView(view, i);
    }

    private void initView(View view, int i) {
        final View findViewById = view.findViewById(R$id.rl_left);
        int i2 = i * 2;
        IndexRankEntity indexRankEntity = this.indexRanks.get(i2);
        final String type = indexRankEntity.getType();
        ((TextView) view.findViewById(R$id.tv_left_title)).setText(getTitle(type));
        findViewById.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.adapter.MyRankBannerAdapter.1
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view2) {
                Intent intent = new Intent(MyRankBannerAdapter.this.mContext, RankingNewActivity.class);
                intent.putExtra(ConstantUtils.RESULT_FLAG, MyRankBannerAdapter.this.getPosition(type));
                intent.putStringArrayListExtra(ConstantUtils.RESULT_ITEM, MyRankBannerAdapter.this.getRankTypeList());
                MyRankBannerAdapter.this.mContext.startActivity(intent);
            }
        });
        List<String> avatars = indexRankEntity.getAvatars();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R$id.rv_left_avatars);
        recyclerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$MyRankBannerAdapter$XVCab14edGr9qHGcgnZDEgVcavI
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return MyRankBannerAdapter.lambda$initView$0(findViewById, view2, motionEvent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext, 0, true);
        linearLayoutManager.setStackFromEnd(true);
        RankEnterAvatarsAdapter rankEnterAvatarsAdapter = new RankEnterAvatarsAdapter(R$layout.fq_item_layout_head_view_rank_enter);
        recyclerView.setAdapter(rankEnterAvatarsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        rankEnterAvatarsAdapter.bindToRecyclerView(recyclerView);
        rankEnterAvatarsAdapter.setNewData(avatars);
        View findViewById2 = view.findViewById(R$id.line);
        TextView textView = (TextView) view.findViewById(R$id.tv_right_title);
        final View findViewById3 = view.findViewById(R$id.rl_right);
        int i3 = i2 + 1;
        if (i3 > this.indexRanks.size() - 1) {
            findViewById2.setVisibility(8);
            findViewById3.setVisibility(8);
            return;
        }
        IndexRankEntity indexRankEntity2 = this.indexRanks.get(i3);
        final String type2 = indexRankEntity2.getType();
        textView.setText(getTitle(type2));
        findViewById2.setVisibility(0);
        findViewById3.setVisibility(0);
        findViewById3.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.adapter.MyRankBannerAdapter.2
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view2) {
                Intent intent = new Intent(MyRankBannerAdapter.this.mContext, RankingNewActivity.class);
                intent.putExtra(ConstantUtils.RESULT_FLAG, MyRankBannerAdapter.this.getPosition(type2));
                intent.putStringArrayListExtra(ConstantUtils.RESULT_ITEM, MyRankBannerAdapter.this.getRankTypeList());
                MyRankBannerAdapter.this.mContext.startActivity(intent);
            }
        });
        List<String> avatars2 = indexRankEntity2.getAvatars();
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R$id.rv_right_avatars);
        recyclerView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$MyRankBannerAdapter$eUzCInhB5SD4ssseeilbHcRSUtw
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return MyRankBannerAdapter.lambda$initView$1(findViewById3, view2, motionEvent);
            }
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this.mContext, 0, true);
        linearLayoutManager2.setStackFromEnd(true);
        RankEnterAvatarsAdapter rankEnterAvatarsAdapter2 = new RankEnterAvatarsAdapter(R$layout.fq_item_layout_head_view_rank_enter);
        recyclerView2.setAdapter(rankEnterAvatarsAdapter2);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        rankEnterAvatarsAdapter2.bindToRecyclerView(recyclerView2);
        rankEnterAvatarsAdapter2.setNewData(avatars2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initView$0(View view, View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            view.performClick();
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initView$1(View view, View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            view.performClick();
            return false;
        }
        return false;
    }

    private String getTitle(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1309357992) {
            if (str.equals(ConstantUtils.RANK_TYPE_EXPENSE)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != -1184259671) {
            if (hashCode == -622460826 && str.equals(ConstantUtils.RANK_TYPE_WEEKSTAR)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(ConstantUtils.RANK_TYPE_INCOME)) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return this.mContext.getString(R$string.fq_rich_man);
            }
            return c != 2 ? "" : this.mContext.getString(R$string.fq_week_star_gift);
        }
        return this.mContext.getString(R$string.fq_idol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPosition(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1309357992) {
            if (str.equals(ConstantUtils.RANK_TYPE_EXPENSE)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != -1184259671) {
            if (hashCode == -622460826 && str.equals(ConstantUtils.RANK_TYPE_WEEKSTAR)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(ConstantUtils.RANK_TYPE_INCOME)) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return 1;
            }
            return c != 2 ? 0 : 2;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<String> getRankTypeList() {
        List<IndexRankEntity> list = this.indexRanks;
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (IndexRankEntity indexRankEntity : this.indexRanks) {
            arrayList.add(indexRankEntity.getType());
        }
        return arrayList;
    }
}
