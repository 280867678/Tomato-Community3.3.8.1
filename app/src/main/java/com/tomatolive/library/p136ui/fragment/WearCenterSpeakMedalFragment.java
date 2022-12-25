package com.tomatolive.library.p136ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.ChatPreviewEntity;
import com.tomatolive.library.model.IconEntity;
import com.tomatolive.library.model.MedalEntity;
import com.tomatolive.library.p136ui.adapter.WearCenterSpeakMedalListAdapter;
import com.tomatolive.library.p136ui.adapter.WearCenterSpeakPrefixListAdapter;
import com.tomatolive.library.p136ui.fragment.WearCenterSpeakMedalFragment;
import com.tomatolive.library.p136ui.presenter.WearCenterPresenter;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerHorizontalLinear;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerWearCenterEmptyView;
import com.tomatolive.library.p136ui.view.iview.IWearCenterView;
import com.tomatolive.library.p136ui.view.span.NetImageSpan;
import com.tomatolive.library.p136ui.view.span.VerticalImageSpan;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* renamed from: com.tomatolive.library.ui.fragment.WearCenterSpeakMedalFragment */
/* loaded from: classes3.dex */
public class WearCenterSpeakMedalFragment extends BaseFragment<WearCenterPresenter> implements IWearCenterView {
    public static final int MEDAL_TYPE_OWN = 2;
    public static final int MEDAL_TYPE_USED = 1;
    public static final int PREFIX_TYPE_OWN = 2;
    public static final int PREFIX_TYPE_USED = 1;
    private String curMedalId;
    private String curPrefixId;
    private String expGrade;
    private ImageView ivSpeakMedal;
    private ImageView ivSpeakPrefix;
    private List<String> marks;
    private WearCenterSpeakMedalListAdapter medalAdapter;
    private String name;
    private WearCenterSpeakPrefixListAdapter prefixAdapter;
    private RecyclerView rvSpeakMedal;
    private RecyclerView rvSpeakPrefix;
    private TextView tvEmptyMedal;
    private TextView tvEmptyPrefix;
    private TextView tvHaveSpeakMedal;
    private TextView tvHaveSpeakPrefix;
    private TextView tvMedalDesc;
    private TextView tvMedalEndTime;
    private TextView tvPrefixDesc;
    private TextView tvPrefixEndTime;
    private TextView tvPreview;
    private TextView tvSpeakMedalTip;
    private TextView tvSpeakPrefixTip;
    private TextView tvWearSpeakMedal;
    private TextView tvWearSpeakPrefix;
    private UserGradeView userGradeView;
    private final String MEDAL_TYPE = "MEDAL_TYPE";
    private final String PREFIX_TYPE = "PREFIX_TYPE";
    private MedalEntity currentUsedMedal = null;
    private MedalEntity currentUsedPrefix = null;

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onAllDataFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static WearCenterSpeakMedalFragment newInstance() {
        Bundle bundle = new Bundle();
        WearCenterSpeakMedalFragment wearCenterSpeakMedalFragment = new WearCenterSpeakMedalFragment();
        wearCenterSpeakMedalFragment.setArguments(bundle);
        return wearCenterSpeakMedalFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public WearCenterPresenter mo6641createPresenter() {
        return new WearCenterPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_achieve_fragment_speak_medal;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.tvSpeakMedalTip = (TextView) view.findViewById(R$id.tv_achieve_speak_medal_tip);
        this.tvSpeakPrefixTip = (TextView) view.findViewById(R$id.tv_achieve_speak_prefix_tip);
        this.ivSpeakMedal = (ImageView) view.findViewById(R$id.iv_achieve_wear_speak_medal);
        this.ivSpeakPrefix = (ImageView) view.findViewById(R$id.iv_achieve_wear_speak_prefix);
        this.tvEmptyMedal = (TextView) view.findViewById(R$id.tv_empty_wear_medal);
        this.tvEmptyPrefix = (TextView) view.findViewById(R$id.tv_empty_wear_prefix);
        this.rvSpeakMedal = (RecyclerView) view.findViewById(R$id.rv_achieve_speak_medal_list);
        this.rvSpeakPrefix = (RecyclerView) view.findViewById(R$id.rv_achieve_speak_prefix_list);
        this.tvWearSpeakMedal = (TextView) view.findViewById(R$id.tv_achieve_wear_speak_medal);
        this.tvWearSpeakPrefix = (TextView) view.findViewById(R$id.tv_achieve_wear_speak_prefix);
        this.tvHaveSpeakMedal = (TextView) view.findViewById(R$id.tv_achieve_have_speak_medal);
        this.tvHaveSpeakPrefix = (TextView) view.findViewById(R$id.tv_achieve_have_speak_prefix);
        this.tvPreview = (TextView) view.findViewById(R$id.tv_chat_text);
        this.tvPrefixDesc = (TextView) view.findViewById(R$id.tv_prefix_desc);
        this.tvPrefixEndTime = (TextView) view.findViewById(R$id.tv_prefix_endTime);
        this.tvMedalDesc = (TextView) view.findViewById(R$id.tv_medal_desc);
        this.tvMedalEndTime = (TextView) view.findViewById(R$id.tv_medal_endTime);
        this.userGradeView = (UserGradeView) view.findViewById(R$id.user_grade_view);
        this.userGradeView.setVisibility(8);
        initData();
        initAdapter();
    }

    private void initData() {
        this.tvSpeakMedalTip.setText(Html.fromHtml(getString(R$string.fq_achieve_speak_medal_tip)));
        this.tvSpeakPrefixTip.setText(Html.fromHtml(getString(R$string.fq_achieve_speak_prefix_tip)));
        ((WearCenterPresenter) this.mPresenter).getAllData(this.mStateView);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        this.medalAdapter = new WearCenterSpeakMedalListAdapter(R$layout.fq_achieve_item_wear_center_speak_medal_icon);
        this.rvSpeakMedal.setAdapter(this.medalAdapter);
        this.rvSpeakMedal.setLayoutManager(linearLayoutManager);
        this.rvSpeakMedal.addItemDecoration(new RVDividerHorizontalLinear(this.mContext, R$color.fq_colorWhite, 12.0f, false));
        this.medalAdapter.bindToRecyclerView(this.rvSpeakMedal);
        this.medalAdapter.setEmptyView(new RecyclerWearCenterEmptyView(this.mContext, 47));
        this.medalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$7iUJ6EPqSr3hH5anQ999Vjih_RI
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                WearCenterSpeakMedalFragment.this.lambda$initAdapter$1$WearCenterSpeakMedalFragment(baseQuickAdapter, view, i);
            }
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this.mContext);
        linearLayoutManager2.setOrientation(1);
        this.prefixAdapter = new WearCenterSpeakPrefixListAdapter(R$layout.fq_achieve_item_wear_center_speak_prefix_icon);
        this.rvSpeakPrefix.setAdapter(this.prefixAdapter);
        this.rvSpeakPrefix.setLayoutManager(linearLayoutManager2);
        this.rvSpeakPrefix.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_colorWhite, 12.0f));
        this.prefixAdapter.bindToRecyclerView(this.rvSpeakPrefix);
        this.prefixAdapter.setEmptyView(new RecyclerWearCenterEmptyView(this.mContext, 48));
        this.prefixAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$pDNYt2gzDhruJVGdOBNZDF1NN84
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                WearCenterSpeakMedalFragment.this.lambda$initAdapter$3$WearCenterSpeakMedalFragment(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initAdapter$1$WearCenterSpeakMedalFragment(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        final MedalEntity medalEntity = (MedalEntity) baseQuickAdapter.getItem(i);
        if (medalEntity == null) {
            return;
        }
        if (this.currentUsedMedal == null) {
            ((WearCenterPresenter) this.mPresenter).equipWearCenter("MEDAL_TYPE", false, medalEntity, i);
        } else {
            SureCancelDialog.newInstance(getString(R$string.fq_achieve_replace_wear), getString(R$string.fq_achieve_sure_replace_medal), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$Iu8k8wN4FRvPDTOhEpv1kivJTsk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    WearCenterSpeakMedalFragment.this.lambda$null$0$WearCenterSpeakMedalFragment(medalEntity, i, view2);
                }
            }).show(getChildFragmentManager());
        }
    }

    public /* synthetic */ void lambda$null$0$WearCenterSpeakMedalFragment(MedalEntity medalEntity, int i, View view) {
        ((WearCenterPresenter) this.mPresenter).equipWearCenter("MEDAL_TYPE", true, medalEntity, i);
    }

    public /* synthetic */ void lambda$initAdapter$3$WearCenterSpeakMedalFragment(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        final MedalEntity medalEntity = (MedalEntity) baseQuickAdapter.getItem(i);
        if (medalEntity == null) {
            return;
        }
        if (this.currentUsedPrefix == null) {
            ((WearCenterPresenter) this.mPresenter).equipWearCenter("PREFIX_TYPE", false, medalEntity, i);
        } else {
            SureCancelDialog.newInstance(getString(R$string.fq_achieve_replace_wear), getString(R$string.fq_achieve_sure_replace_prefix), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$4ufTlEqFDYMCgGp7Dn4zbyjabmE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    WearCenterSpeakMedalFragment.this.lambda$null$2$WearCenterSpeakMedalFragment(medalEntity, i, view2);
                }
            }).show(getChildFragmentManager());
        }
    }

    public /* synthetic */ void lambda$null$2$WearCenterSpeakMedalFragment(MedalEntity medalEntity, int i, View view) {
        ((WearCenterPresenter) this.mPresenter).equipWearCenter("PREFIX_TYPE", true, medalEntity, i);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$3oANg62lqli4KfJAbhDzPbp1YxQ
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                WearCenterSpeakMedalFragment.this.lambda$initListener$4$WearCenterSpeakMedalFragment();
            }
        });
        this.ivSpeakMedal.setOnClickListener(new View$OnClickListenerC41321());
        this.ivSpeakPrefix.setOnClickListener(new View$OnClickListenerC41332());
    }

    public /* synthetic */ void lambda$initListener$4$WearCenterSpeakMedalFragment() {
        ((WearCenterPresenter) this.mPresenter).getAllData(this.mStateView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.fragment.WearCenterSpeakMedalFragment$1 */
    /* loaded from: classes3.dex */
    public class View$OnClickListenerC41321 implements View.OnClickListener {
        View$OnClickListenerC41321() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SureCancelDialog.newInstance(WearCenterSpeakMedalFragment.this.getString(R$string.fq_achieve_cancel_wear), WearCenterSpeakMedalFragment.this.getString(R$string.fq_achieve_sure_remove_medal), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$1$HBTN4_EijA-DOEjpgmiW8xr7n4c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    WearCenterSpeakMedalFragment.View$OnClickListenerC41321.this.lambda$onClick$0$WearCenterSpeakMedalFragment$1(view2);
                }
            }).show(WearCenterSpeakMedalFragment.this.getChildFragmentManager());
        }

        public /* synthetic */ void lambda$onClick$0$WearCenterSpeakMedalFragment$1(View view) {
            ((WearCenterPresenter) ((BaseFragment) WearCenterSpeakMedalFragment.this).mPresenter).cancelWearCenter("MEDAL_TYPE", WearCenterSpeakMedalFragment.this.curMedalId);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.fragment.WearCenterSpeakMedalFragment$2 */
    /* loaded from: classes3.dex */
    public class View$OnClickListenerC41332 implements View.OnClickListener {
        View$OnClickListenerC41332() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SureCancelDialog.newInstance(WearCenterSpeakMedalFragment.this.getString(R$string.fq_achieve_cancel_wear), WearCenterSpeakMedalFragment.this.getString(R$string.fq_achieve_sure_remove_prefix), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$WearCenterSpeakMedalFragment$2$cCwVEWHC6xbACEVv61_g5fj-_og
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    WearCenterSpeakMedalFragment.View$OnClickListenerC41332.this.lambda$onClick$0$WearCenterSpeakMedalFragment$2(view2);
                }
            }).show(WearCenterSpeakMedalFragment.this.getChildFragmentManager());
        }

        public /* synthetic */ void lambda$onClick$0$WearCenterSpeakMedalFragment$2(View view) {
            ((WearCenterPresenter) ((BaseFragment) WearCenterSpeakMedalFragment.this).mPresenter).cancelWearCenter("PREFIX_TYPE", WearCenterSpeakMedalFragment.this.curPrefixId);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onAllDataSuccess(Map<String, Object> map) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            char c = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c = 2;
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4")) {
                        c = 3;
                        break;
                    }
                    break;
                case 53:
                    if (str.equals("5")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            if (c != 0) {
                if (c != 1) {
                    if (c != 2) {
                        if (c == 3) {
                            if (obj instanceof Collection) {
                                onPrefixDataSuccess(1, (List) obj);
                            }
                        } else if (c == 4 && (obj instanceof Collection)) {
                            onPrefixDataSuccess(2, (List) obj);
                        }
                    } else if (obj instanceof Collection) {
                        onMedalDataSuccess(2, (List) obj);
                    }
                } else if (obj instanceof Collection) {
                    onMedalDataSuccess(1, (List) obj);
                }
            } else if (obj instanceof ChatPreviewEntity) {
                onChatPreviewSuccess((ChatPreviewEntity) obj);
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onChatPreviewSuccess(ChatPreviewEntity chatPreviewEntity) {
        this.name = chatPreviewEntity.name;
        this.expGrade = chatPreviewEntity.expGrade;
        this.marks = chatPreviewEntity.markUrls;
        setPreviewText();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onMedalDataSuccess(int i, List<MedalEntity> list) {
        if (i == 1) {
            if (list == null || list.isEmpty()) {
                onMedalDataFail(1);
                return;
            }
            this.currentUsedMedal = list.get(0);
            updateCurrentUsedMedalView(true);
        } else if (i != 2) {
        } else {
            if (list == null) {
                onMedalDataFail(2);
                return;
            }
            this.tvHaveSpeakMedal.setText(getString(R$string.fq_achieve_already_have, String.valueOf(list.size())));
            this.medalAdapter.setNewData(list);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onMedalDataFail(int i) {
        if (i == 1) {
            updateCurrentUsedMedalView(false);
        } else if (i != 2) {
        } else {
            this.tvHaveSpeakMedal.setText(getString(R$string.fq_achieve_already_have, "0"));
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onPrefixDataSuccess(int i, List<MedalEntity> list) {
        if (i == 1) {
            if (list == null || list.isEmpty()) {
                onPrefixDataFail(1);
                return;
            }
            this.currentUsedPrefix = list.get(0);
            updateCurrentUsedPrefixView(true);
        } else if (i != 2) {
        } else {
            if (list == null) {
                onPrefixDataFail(2);
                return;
            }
            this.tvHaveSpeakPrefix.setText(getString(R$string.fq_achieve_already_have, String.valueOf(list.size())));
            this.prefixAdapter.setNewData(list);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onPrefixDataFail(int i) {
        if (i == 1) {
            updateCurrentUsedPrefixView(false);
        } else if (i != 2) {
        } else {
            this.tvHaveSpeakPrefix.setText(getString(R$string.fq_achieve_already_have, "0"));
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onEquipSuccess(String str, boolean z, MedalEntity medalEntity, int i) {
        MedalEntity medalEntity2;
        MedalEntity medalEntity3;
        showToast(z ? R$string.fq_achieve_wear_replace_success : R$string.fq_achieve_wear_success);
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -701111097) {
            if (hashCode == -35620958 && str.equals("MEDAL_TYPE")) {
                c = 0;
            }
        } else if (str.equals("PREFIX_TYPE")) {
            c = 1;
        }
        if (c == 0) {
            this.medalAdapter.remove(i);
            if (z && (medalEntity2 = this.currentUsedMedal) != null) {
                this.medalAdapter.addData((WearCenterSpeakMedalListAdapter) medalEntity2);
            }
            this.currentUsedMedal = medalEntity;
            updateCurrentUsedMedalView(true);
            this.tvHaveSpeakMedal.setText(getString(R$string.fq_achieve_already_have, String.valueOf(this.medalAdapter.getData().size())));
        } else if (c == 1) {
            this.prefixAdapter.remove(i);
            if (z && (medalEntity3 = this.currentUsedPrefix) != null) {
                this.prefixAdapter.addData((WearCenterSpeakPrefixListAdapter) medalEntity3);
            }
            this.currentUsedPrefix = medalEntity;
            updateCurrentUsedPrefixView(true);
            this.tvHaveSpeakPrefix.setText(getString(R$string.fq_achieve_already_have, String.valueOf(this.prefixAdapter.getData().size())));
        }
        ((WearCenterPresenter) this.mPresenter).getChatPreview();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onEquipFail(boolean z) {
        showToast(z ? R$string.fq_achieve_wear_replace_fail : R$string.fq_achieve_wear_fail);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onCancelWearCenterSuccess(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -701111097) {
            if (hashCode == -35620958 && str.equals("MEDAL_TYPE")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("PREFIX_TYPE")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            updateCurrentUsedMedalView(false);
            MedalEntity medalEntity = this.currentUsedMedal;
            if (medalEntity != null) {
                this.medalAdapter.addData((WearCenterSpeakMedalListAdapter) medalEntity);
                this.tvHaveSpeakMedal.setText(getString(R$string.fq_achieve_already_have, String.valueOf(this.medalAdapter.getData().size())));
                this.currentUsedMedal = null;
            }
        } else if (c == 1) {
            updateCurrentUsedPrefixView(false);
            MedalEntity medalEntity2 = this.currentUsedPrefix;
            if (medalEntity2 != null) {
                this.prefixAdapter.addData((WearCenterSpeakPrefixListAdapter) medalEntity2);
                this.tvHaveSpeakPrefix.setText(getString(R$string.fq_achieve_already_have, String.valueOf(this.prefixAdapter.getData().size())));
                this.currentUsedPrefix = null;
            }
        }
        ((WearCenterPresenter) this.mPresenter).getChatPreview();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWearCenterView
    public void onCancelWearCenterFail() {
        showToast(R$string.fq_achieve_drop_wear_fail);
    }

    private void updateCurrentUsedMedalView(boolean z) {
        switchMedalForUsed(z);
        if (!z) {
            this.tvWearSpeakMedal.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "0")));
            return;
        }
        MedalEntity medalEntity = this.currentUsedMedal;
        if (medalEntity == null) {
            switchMedalForUsed(false);
            this.tvWearSpeakMedal.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "0")));
            return;
        }
        this.curMedalId = medalEntity.markId;
        this.tvWearSpeakMedal.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "1")));
        GlideUtils.loadImage(this.mContext, this.ivSpeakMedal, this.currentUsedMedal.markUrl, R$drawable.fq_ic_placeholder_avatar);
        this.tvMedalDesc.setText(this.currentUsedMedal.desc);
        if (TextUtils.isEmpty(this.currentUsedMedal.getEndTimeStr())) {
            this.tvMedalEndTime.setText(R$string.fq_achieve_get_achieve_forever_valid);
        } else {
            this.tvMedalEndTime.setText(getString(R$string.fq_achieve_wear_center_end_time_2, this.currentUsedMedal.getEndTimeStr()));
        }
    }

    private void updateCurrentUsedPrefixView(boolean z) {
        switchPrefixForUsed(z);
        if (!z) {
            this.tvWearSpeakPrefix.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "0")));
            return;
        }
        MedalEntity medalEntity = this.currentUsedPrefix;
        if (medalEntity == null) {
            switchPrefixForUsed(false);
            this.tvWearSpeakPrefix.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "0")));
            return;
        }
        this.curPrefixId = medalEntity.markId;
        this.tvWearSpeakPrefix.setText(Html.fromHtml(getString(R$string.fq_achieve_already_wear, "1")));
        GlideUtils.loadImage(this.mContext, this.ivSpeakPrefix, this.currentUsedPrefix.markUrl, R$drawable.fq_shape_default_cover_bg);
        this.tvPrefixDesc.setText(this.currentUsedPrefix.desc);
        if (TextUtils.isEmpty(this.currentUsedPrefix.getEndTimeStr())) {
            this.tvPrefixEndTime.setText(R$string.fq_achieve_get_achieve_forever_valid);
        } else {
            this.tvPrefixEndTime.setText(getString(R$string.fq_achieve_wear_center_end_time, this.currentUsedPrefix.getEndTimeStr()));
        }
    }

    public void switchMedalForUsed(boolean z) {
        int i = 8;
        this.tvEmptyMedal.setVisibility(z ? 8 : 0);
        this.ivSpeakMedal.setVisibility(z ? 0 : 8);
        this.tvMedalDesc.setVisibility(z ? 0 : 8);
        TextView textView = this.tvMedalEndTime;
        if (z) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    public void switchPrefixForUsed(boolean z) {
        int i = 8;
        this.tvEmptyPrefix.setVisibility(z ? 8 : 0);
        this.ivSpeakPrefix.setVisibility(z ? 0 : 8);
        this.tvPrefixDesc.setVisibility(z ? 0 : 8);
        TextView textView = this.tvPrefixEndTime;
        if (z) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    private void setIcon(IconEntity iconEntity, SpannableString spannableString) {
        List<String> list = iconEntity.urls;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2);
            int i3 = (i + 2) - 1;
            if (TextUtils.equals(str, ConstantUtils.EXP_GRADE_ICON_KEY)) {
                spannableString.setSpan(new VerticalImageSpan(iconEntity.userGradeDrawable), i, i3, 33);
            } else {
                TextView textView = this.tvPreview;
                if (textView != null) {
                    NetImageSpan netImageSpan = new NetImageSpan(textView);
                    netImageSpan.setUrl(str);
                    netImageSpan.setHeight(ConvertUtils.dp2px(22.0f));
                    spannableString.setSpan(netImageSpan, i, i3, 33);
                }
            }
            i = i3 + 1;
        }
    }

    private void setPreviewText() {
        IconEntity iconEntity = getIconEntity(this.userGradeView);
        int length = iconEntity.value.length();
        int length2 = this.name.length() + length + 3;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(iconEntity.value);
        stringBuffer.append(this.name);
        stringBuffer.append(" : 山川异域，风月同天！");
        iconEntity.value = stringBuffer.toString();
        SpannableString spannableString = new SpannableString(iconEntity.value);
        setIcon(iconEntity, spannableString);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_audience_color)), length, length2, 33);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length2, iconEntity.value.length(), 33);
        this.tvPreview.setText(spannableString);
    }

    private Drawable formatDrawableBounds(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    private IconEntity getIconEntity(UserGradeView userGradeView) {
        IconEntity iconEntity = new IconEntity();
        iconEntity.urls = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.marks.size(); i++) {
            String str = this.marks.get(i);
            if (TextUtils.equals(str, ConstantUtils.EXP_GRADE_ICON_KEY)) {
                userGradeView.initUserGradeMsg(this.expGrade, true);
                Bitmap viewBitmap = getViewBitmap(userGradeView);
                if (viewBitmap != null) {
                    iconEntity.userGradeDrawable = formatDrawableBounds(ImageUtils.bitmap2Drawable(viewBitmap));
                    if (iconEntity.userGradeDrawable != null) {
                        stringBuffer.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                        iconEntity.urls.add(str);
                    }
                }
            } else if (!TextUtils.isEmpty(str)) {
                stringBuffer.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                iconEntity.urls.add(str);
            }
        }
        iconEntity.value = stringBuffer.toString();
        return iconEntity;
    }

    private Bitmap getViewBitmap(View view) {
        try {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            return Bitmap.createBitmap(view.getDrawingCache());
        } catch (Exception unused) {
            return null;
        }
    }
}
