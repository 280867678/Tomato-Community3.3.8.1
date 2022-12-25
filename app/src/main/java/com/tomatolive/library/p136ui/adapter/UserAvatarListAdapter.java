package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.adapter.UserAvatarListAdapter */
/* loaded from: classes3.dex */
public class UserAvatarListAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    private UserListClickListener listener;

    /* renamed from: com.tomatolive.library.ui.adapter.UserAvatarListAdapter$UserListClickListener */
    /* loaded from: classes3.dex */
    public interface UserListClickListener {
        void onUserClick(UserEntity userEntity);
    }

    public UserAvatarListAdapter(int i) {
        super(i);
    }

    public void removeItemById(String str) {
        try {
            for (T t : this.mData) {
                if (TextUtils.equals(str, t.getUserId())) {
                    this.mData.remove(t);
                    notifyDataSetChanged();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final UserEntity userEntity) {
        RxView.clicks(baseViewHolder.itemView).throttleFirst(500L, TimeUnit.MILLISECONDS).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.adapter.UserAvatarListAdapter.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (UserAvatarListAdapter.this.listener != null) {
                    UserAvatarListAdapter.this.listener.onUserClick(userEntity);
                }
            }
        });
        if (userEntity == null) {
            return;
        }
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_user_avatar), userEntity.getAvatar());
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.fq_year_guard_icon);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R$id.iv_badge);
        int i = 0;
        imageView.setVisibility(userEntity.getGuardType() > NumberUtils.string2int("0") ? 0 : 4);
        imageView.setImageResource(userEntity.getGuardType() == NumberUtils.string2int("3") ? R$drawable.fq_ic_live_msg_year_guard : R$drawable.fq_ic_live_msg_mouth_guard);
        if (!AppUtils.isNobilityUser(userEntity.getNobilityType())) {
            i = 4;
        }
        imageView2.setVisibility(i);
        imageView2.setImageResource(getBadgeDrawableRes(userEntity.getNobilityType()));
    }

    @DrawableRes
    private int getBadgeDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_avatar_label_1;
            case 2:
                return R$drawable.fq_ic_nobility_avatar_label_2;
            case 3:
                return R$drawable.fq_ic_nobility_avatar_label_3;
            case 4:
                return R$drawable.fq_ic_nobility_avatar_label_4;
            case 5:
                return R$drawable.fq_ic_nobility_avatar_label_5;
            case 6:
                return R$drawable.fq_ic_nobility_avatar_label_6;
            case 7:
                return R$drawable.fq_ic_nobility_avatar_label_7;
            default:
                return R$drawable.fq_ic_nobility_avatar_label_1;
        }
    }

    public void setOnItemClickListener(UserListClickListener userListClickListener) {
        this.listener = userListClickListener;
    }
}
