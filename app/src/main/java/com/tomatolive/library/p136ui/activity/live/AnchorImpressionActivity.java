package com.tomatolive.library.p136ui.activity.live;

import android.os.Bundle;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.ImpressionEntity;
import com.tomatolive.library.model.p135db.LightImpressionDBEntity;
import com.tomatolive.library.p136ui.adapter.AnchorImpressionAdapter;
import com.tomatolive.library.p136ui.presenter.AnchorImpressionPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerGridItem3;
import com.tomatolive.library.p136ui.view.iview.IAnchorImpressionView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.live.AnchorImpressionActivity */
/* loaded from: classes3.dex */
public class AnchorImpressionActivity extends BaseActivity<AnchorImpressionPresenter> implements IAnchorImpressionView {
    private String anchorAppId;
    private String anchorId;
    private AnchorImpressionAdapter anchorImpressionAdapter;
    private String anchorOpenId;
    private ImpressionEntity impressionEntity;
    private List<ImpressionEntity> impressionEntityList = new ArrayList();
    private List<String> localIdList;
    private RecyclerView recyclerView;
    private TextView tvAnchorNameTips;

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorImpressionView
    public void onImpressionListFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public AnchorImpressionPresenter mo6636createPresenter() {
        return new AnchorImpressionPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_achieve_activity_anchor_impression;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.tvAnchorNameTips = (TextView) findViewById(R$id.tv_anchor_name);
        this.recyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        setActivityRightTitle(R$string.fq_achieve_add_anchor_impression, R$string.fq_btn_save, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$AnchorImpressionActivity$UOwvIQKOxKUycAxgWGbMcGTmhw8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorImpressionActivity.this.lambda$initView$0$AnchorImpressionActivity(view);
            }
        });
        initAdapter();
    }

    public /* synthetic */ void lambda$initView$0$AnchorImpressionActivity(View view) {
        List<ImpressionEntity> list = this.impressionEntityList;
        if (list == null || list.isEmpty()) {
            return;
        }
        List<String> desList = this.anchorImpressionAdapter.getDesList();
        String commaSpliceStrByList = StringUtils.getCommaSpliceStrByList(desList);
        this.localIdList = ((AnchorImpressionPresenter) this.mPresenter).getLocalLightImpressionList(this.anchorId, this.anchorAppId);
        List<String> list2 = this.localIdList;
        String str = "";
        if (list2 != null && !list2.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String str2 : this.localIdList) {
                if (!desList.contains(str2)) {
                    stringBuffer.append(str2);
                    stringBuffer.append(",");
                }
            }
            if (stringBuffer.length() != 0) {
                str = stringBuffer.substring(0, stringBuffer.length() - 1);
            }
        }
        ((AnchorImpressionPresenter) this.mPresenter).updateImpressionList(this.anchorAppId, this.anchorOpenId, commaSpliceStrByList, str);
    }

    private void initAdapter() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.anchorImpressionAdapter = new AnchorImpressionAdapter(R$layout.fq_achieve_item_impression_label, this.impressionEntityList);
        this.recyclerView.addItemDecoration(new RVDividerGridItem3(this.mContext, 17170445));
        this.recyclerView.setAdapter(this.anchorImpressionAdapter);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initData() {
        this.impressionEntity = (ImpressionEntity) getIntent().getParcelableExtra(ConstantUtils.RESULT_ITEM);
        ImpressionEntity impressionEntity = this.impressionEntity;
        if (impressionEntity == null) {
            return;
        }
        this.tvAnchorNameTips.setText(Html.fromHtml(getString(R$string.fq_achieve_add_anchor_impression_tips, new Object[]{impressionEntity.anchorName})));
        ImpressionEntity impressionEntity2 = this.impressionEntity;
        this.anchorId = impressionEntity2.anchorId;
        this.anchorOpenId = impressionEntity2.anchorOpenId;
        this.anchorAppId = impressionEntity2.anchorAppId;
        this.localIdList = ((AnchorImpressionPresenter) this.mPresenter).getLocalLightImpressionList(this.anchorId, this.anchorAppId);
        ((AnchorImpressionPresenter) this.mPresenter).getImpressionList(this.anchorAppId, this.anchorOpenId, this.mStateView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.anchorImpressionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$AnchorImpressionActivity$0il4Ji5Ex0o138ivKZ2wygNPNjo
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AnchorImpressionActivity.this.lambda$initListener$1$AnchorImpressionActivity(baseQuickAdapter, view, i);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.live.AnchorImpressionActivity.1
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                if (AnchorImpressionActivity.this.impressionEntity == null) {
                    return;
                }
                ((AnchorImpressionPresenter) ((BaseActivity) AnchorImpressionActivity.this).mPresenter).getImpressionList(AnchorImpressionActivity.this.anchorAppId, AnchorImpressionActivity.this.anchorOpenId, ((BaseActivity) AnchorImpressionActivity.this).mStateView);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$AnchorImpressionActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.anchorImpressionAdapter.addCheckPos(String.valueOf(i), this.impressionEntityList.get(i));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorImpressionView
    public void onImpressionListSuccess(List<ImpressionEntity> list) {
        this.impressionEntityList.clear();
        this.impressionEntityList.addAll(list);
        List<String> list2 = this.localIdList;
        if (list2 == null || list2.isEmpty()) {
            this.anchorImpressionAdapter.notifyDataSetChanged();
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ImpressionEntity impressionEntity = list.get(i);
            if (this.localIdList.contains(impressionEntity.impressionId)) {
                this.anchorImpressionAdapter.addLocalCheckPos(String.valueOf(i), impressionEntity.impressionId);
            }
        }
        this.anchorImpressionAdapter.notifyDataSetChanged();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorImpressionView
    public void onImpressionListUpdateSuccess(List<ImpressionEntity> list, String str) {
        saveLightImpressionDBEntity(this.anchorId, this.anchorAppId, str);
        showToast(R$string.fq_achieve_save_success);
        finish();
    }

    private void saveLightImpressionDBEntity(String str, String str2, String str3) {
        LightImpressionDBEntity lightImpressionDBEntity = new LightImpressionDBEntity();
        lightImpressionDBEntity.appId = UserInfoManager.getInstance().getAppId();
        lightImpressionDBEntity.userId = UserInfoManager.getInstance().getUserId();
        lightImpressionDBEntity.anchorId = str;
        lightImpressionDBEntity.anchorAppId = str2;
        lightImpressionDBEntity.impressionIds = str3;
        DBUtils.updateLightImpression(lightImpressionDBEntity);
    }
}
