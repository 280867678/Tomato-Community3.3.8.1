package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;

/* loaded from: classes3.dex */
public class RechargeExGameAdapter extends BaseRecyclerViewAdapter<RechargeExGame> {
    private RechargeAccount rechargeAccount;
    public OnRechargeExTypeListener rechargeExTypeListener;

    /* loaded from: classes3.dex */
    public interface OnRechargeExTypeListener {
        void onClickItem(RechargeExGame rechargeExGame, int i);
    }

    public RechargeExGameAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_recharge_ex_game, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeExGame rechargeExGame) {
        super.convert(baseViewHolder, (BaseViewHolder) rechargeExGame);
        this.mData.indexOf(rechargeExGame);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_in);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_out);
        baseViewHolder.addOnClickListener(R.id.tv_in).addOnClickListener(R.id.tv_out);
        ((TextView) baseViewHolder.getView(R.id.tv_name)).setText(rechargeExGame.name);
        ((TextView) baseViewHolder.getView(R.id.tv_balance)).setText(FormatUtil.formatTwo(Double.valueOf(rechargeExGame.balance / 100.0d)));
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        RechargeExGame rechargeExGame = (RechargeExGame) this.mData.get(i);
        int id = view.getId();
        if (id == R.id.tv_in) {
            RechargeAccount rechargeAccount = this.rechargeAccount;
            if (rechargeAccount == null || rechargeAccount.balance == 0.0d) {
                ToastUtil.showCenterToast((int) R.string.recharge_ex_purse_balance_enough);
                return;
            }
            OnRechargeExTypeListener onRechargeExTypeListener = this.rechargeExTypeListener;
            if (onRechargeExTypeListener == null) {
                return;
            }
            onRechargeExTypeListener.onClickItem(rechargeExGame, 2);
        } else if (id != R.id.tv_out) {
        } else {
            if (rechargeExGame == null || rechargeExGame.balance == 0.0d) {
                ToastUtil.showCenterToast((int) R.string.recharge_ex_game_balance_enough);
                return;
            }
            OnRechargeExTypeListener onRechargeExTypeListener2 = this.rechargeExTypeListener;
            if (onRechargeExTypeListener2 == null) {
                return;
            }
            onRechargeExTypeListener2.onClickItem(rechargeExGame, 1);
        }
    }

    public void setOnRechargeExTypeListener(OnRechargeExTypeListener onRechargeExTypeListener) {
        this.rechargeExTypeListener = onRechargeExTypeListener;
    }

    public void setRechargeExGame(RechargeExGame rechargeExGame) {
        int indexOf = this.mData.indexOf(rechargeExGame);
        ((RechargeExGame) this.mData.get(indexOf)).balance = rechargeExGame.balance;
        notifyItemChanged(indexOf);
    }

    public void setRechargeAccount(RechargeAccount rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }
}
