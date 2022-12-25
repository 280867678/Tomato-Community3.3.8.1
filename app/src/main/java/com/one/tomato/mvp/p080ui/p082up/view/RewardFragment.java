package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.RechargeList;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.p082up.adapter.RewardMoneyAdapter;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardContact$IRewardView;
import com.one.tomato.mvp.p080ui.p082up.presenter.RewardRechargePresenter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.NoScrollGridView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: RewardFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.RewardFragment */
/* loaded from: classes3.dex */
public final class RewardFragment extends MvpBaseFragment<RewardContact$IRewardView, RewardRechargePresenter> implements RewardContact$IRewardView {
    private HashMap _$_findViewCache;
    private String currentTomato = "";
    private boolean isClick;
    private PostList postList;
    private RewardMoneyAdapter rewardMoneyAdapter;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.reward_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public final PostList getPostList() {
        return this.postList;
    }

    public final void setPostList(PostList postList) {
        this.postList = postList;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public RewardRechargePresenter mo6441createPresenter() {
        return new RewardRechargePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        this.rewardMoneyAdapter = new RewardMoneyAdapter(getMContext(), null);
        NoScrollGridView noScrollGridView = (NoScrollGridView) _$_findCachedViewById(R$id.gridView);
        if (noScrollGridView != null) {
            noScrollGridView.setAdapter((ListAdapter) this.rewardMoneyAdapter);
        }
        NoScrollGridView gridView = (NoScrollGridView) _$_findCachedViewById(R$id.gridView);
        Intrinsics.checkExpressionValueIsNotNull(gridView, "gridView");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.up.view.RewardFragment$initView$1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                RewardMoneyAdapter rewardMoneyAdapter;
                RewardMoneyAdapter rewardMoneyAdapter2;
                rewardMoneyAdapter = RewardFragment.this.rewardMoneyAdapter;
                if (rewardMoneyAdapter != null) {
                    rewardMoneyAdapter.setSelectPosition(i);
                }
                EditText editText = (EditText) RewardFragment.this._$_findCachedViewById(R$id.edit);
                if (editText != null) {
                    editText.clearFocus();
                }
                rewardMoneyAdapter2 = RewardFragment.this.rewardMoneyAdapter;
                RechargeList item = rewardMoneyAdapter2 != null ? rewardMoneyAdapter2.getItem(i) : null;
                if (item != null) {
                    RewardFragment.this.isClick = true;
                    EditText editText2 = (EditText) RewardFragment.this._$_findCachedViewById(R$id.edit);
                    if (editText2 == null) {
                        return;
                    }
                    editText2.setText(FormatUtil.formatTomato2RMB(String.valueOf(item.getTomatoCurrency())));
                }
            }
        });
        PostUtils.INSTANCE.requestBalance(getMContext(), new RewardFragment$initView$2(this), RewardFragment$initView$3.INSTANCE);
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_reward);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.RewardFragment$initView$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View it2) {
                    String valueOf;
                    String str;
                    RewardRechargePresenter mPresenter;
                    RewardMoneyAdapter rewardMoneyAdapter;
                    Editable text;
                    PostList postList = RewardFragment.this.getPostList();
                    if (postList != null && postList.getMemberId() == DBUtil.getMemberId()) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_reward_myself));
                        return;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    it2.setEnabled(false);
                    EditText editText = (EditText) RewardFragment.this._$_findCachedViewById(R$id.edit);
                    Integer num = null;
                    String valueOf2 = String.valueOf((editText == null || (text = editText.getText()) == null) ? null : StringsKt__StringsKt.trim(text));
                    if (TextUtils.isEmpty(valueOf2)) {
                        rewardMoneyAdapter = RewardFragment.this.rewardMoneyAdapter;
                        RechargeList selectItem = rewardMoneyAdapter != null ? rewardMoneyAdapter.getSelectItem() : null;
                        if (selectItem != null) {
                            valueOf = String.valueOf(selectItem.getTomatoCurrency());
                        }
                        valueOf = "0";
                    } else {
                        try {
                            int parseDouble = ((int) Double.parseDouble(valueOf2)) * 10;
                            if (1 <= parseDouble && 3000 >= parseDouble) {
                                valueOf = String.valueOf(parseDouble);
                            }
                            ToastUtil.showCenterToast(RewardFragment.this.getString(R.string.post_reward_input_hint));
                            it2.setEnabled(true);
                            return;
                        } catch (Exception unused) {
                        }
                    }
                    if (TextUtils.isEmpty(valueOf)) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_reward_tot_0));
                        it2.setEnabled(true);
                        return;
                    }
                    try {
                        if (Long.parseLong(valueOf) == 0) {
                            ToastUtil.showCenterToast(AppUtil.getString(R.string.post_reward_tot_0));
                            it2.setEnabled(true);
                            return;
                        }
                    } catch (Exception unused2) {
                    }
                    str = RewardFragment.this.currentTomato;
                    if (!TextUtils.isEmpty(str) && Double.parseDouble(valueOf) > Double.parseDouble(str)) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_reward_pay_no));
                        it2.setEnabled(true);
                        return;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                    PostList postList2 = RewardFragment.this.getPostList();
                    if (postList2 != null) {
                        num = Integer.valueOf(postList2.getId());
                    }
                    linkedHashMap.put("articleId", num);
                    linkedHashMap.put("payType", 1);
                    linkedHashMap.put("money", valueOf);
                    mPresenter = RewardFragment.this.getMPresenter();
                    if (mPresenter == null) {
                        return;
                    }
                    mPresenter.requestRewardPay(linkedHashMap);
                }
            });
        }
        EditText editText = (EditText) _$_findCachedViewById(R$id.edit);
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.up.view.RewardFragment$initView$5
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    boolean z;
                    RewardMoneyAdapter rewardMoneyAdapter;
                    z = RewardFragment.this.isClick;
                    if (!z) {
                        rewardMoneyAdapter = RewardFragment.this.rewardMoneyAdapter;
                        if (rewardMoneyAdapter == null) {
                            return;
                        }
                        rewardMoneyAdapter.setSelectPosition(-2);
                        return;
                    }
                    RewardFragment.this.isClick = false;
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_recharge);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.RewardFragment$initView$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = RewardFragment.this.getMContext();
                    RechargeActivity.startActivity(mContext);
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        RewardRechargePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestRechargeList();
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_reward);
        if (textView != null) {
            textView.setEnabled(true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.RewardContact$IRewardView
    public void handlerRechargeList(ArrayList<RechargeList> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        for (RechargeList rechargeList : arrayList) {
            if (Intrinsics.areEqual(rechargeList.getPaytype(), "3")) {
                arrayList2.add(rechargeList);
            }
        }
        RewardMoneyAdapter rewardMoneyAdapter = this.rewardMoneyAdapter;
        if (rewardMoneyAdapter == null) {
            return;
        }
        rewardMoneyAdapter.setList(arrayList2);
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.RewardContact$IRewardView
    public void handlerRewardPayError() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_reward);
        if (textView != null) {
            textView.setEnabled(true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.RewardContact$IRewardView
    public void handlerRewardPayOk(String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_reward);
        if (textView != null) {
            textView.setEnabled(true);
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_dashang_sucess));
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_balance);
        if (textView2 != null) {
            textView2.setText(FormatUtil.formatTomato2RMB(str));
        }
    }
}
