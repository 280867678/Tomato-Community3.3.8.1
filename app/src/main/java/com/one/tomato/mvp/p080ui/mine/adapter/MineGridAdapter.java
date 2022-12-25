package com.one.tomato.mvp.p080ui.mine.adapter;

import android.content.Context;
import android.support.p002v4.app.NotificationCompat;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.MineItem;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MineGridAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.adapter.MineGridAdapter */
/* loaded from: classes3.dex */
public final class MineGridAdapter extends BaseRecyclerViewAdapter<MineItem> {
    public MineGridAdapterListener listener;

    /* compiled from: MineGridAdapter.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.adapter.MineGridAdapter$MineGridAdapterListener */
    /* loaded from: classes3.dex */
    public interface MineGridAdapterListener {
        void onItemClick(int i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MineGridAdapter(Context context, List<? extends MineItem> list, RecyclerView recyclerView) {
        super(context, R.layout.item_mine_grid, list, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(list, "list");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MineItem mineItem) {
        super.convert(baseViewHolder, (BaseViewHolder) mineItem);
        String str = null;
        if (baseViewHolder != null) {
            Integer valueOf = mineItem != null ? Integer.valueOf(mineItem.resId) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            baseViewHolder.setImageResource(R.id.iv_res, valueOf.intValue());
        }
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess() && baseViewHolder != null) {
            Context mContext = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
            baseViewHolder.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.white));
        }
        if (baseViewHolder != null) {
            baseViewHolder.setText(R.id.tv_title, mineItem != null ? mineItem.name : null);
        }
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_tag) : null;
        if (textView != null) {
            textView.setVisibility(8);
        }
        String str2 = mineItem != null ? mineItem.tag : null;
        if (str2 == null) {
            return;
        }
        int hashCode = str2.hashCode();
        if (hashCode == 3739) {
            if (!str2.equals("up") || PreferencesUtil.getInstance().getBoolean("up_new_tag")) {
                return;
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView == null) {
                return;
            }
            textView.setText(R.string.my_new_msg);
        } else if (hashCode != 108417) {
            if (hashCode != 3552645 || !str2.equals("task")) {
                return;
            }
            LoginInfo loginInfo = DBUtil.getLoginInfo();
            Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
            if (loginInfo.isLogin()) {
                return;
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView == null) {
                return;
            }
            textView.setText(R.string.my_new_msg_vip);
        } else if (!str2.equals(NotificationCompat.CATEGORY_MESSAGE)) {
        } else {
            Boolean valueOf2 = mineItem != null ? Boolean.valueOf(mineItem.newMsg) : null;
            if (valueOf2 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!valueOf2.booleanValue()) {
            } else {
                if (textView != null) {
                    textView.setVisibility(0);
                }
                if (textView == null) {
                    return;
                }
                if (mineItem != null) {
                    str = mineItem.systemNum;
                }
                textView.setText(str);
            }
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Object obj = this.mData.get(i);
        Intrinsics.checkExpressionValueIsNotNull(obj, "mData[position]");
        MineItem mineItem = (MineItem) obj;
        if (Intrinsics.areEqual("recharge", mineItem.tag)) {
            mineItem.newMsg = false;
            refreshNotifyItemChanged(i);
        } else if (Intrinsics.areEqual(NotificationCompat.CATEGORY_MESSAGE, mineItem.tag)) {
            mineItem.newMsg = false;
            mineItem.systemNum = "";
            BaseApplication.setIsMyMessageHave(0);
            refreshNotifyItemChanged(i);
        }
        MineGridAdapterListener mineGridAdapterListener = this.listener;
        if (mineGridAdapterListener != null) {
            mineGridAdapterListener.onItemClick(i);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("listener");
            throw null;
        }
    }

    public final void setOnMineGridAdapterListener(MineGridAdapterListener listener) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.listener = listener;
    }
}
