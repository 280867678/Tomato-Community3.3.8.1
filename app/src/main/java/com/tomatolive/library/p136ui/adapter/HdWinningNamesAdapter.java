package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.HdDrawEndEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.adapter.HdWinningNamesAdapter */
/* loaded from: classes3.dex */
public class HdWinningNamesAdapter extends BaseQuickAdapter<HdDrawEndEntity.WinningNamesEntity, BaseViewHolder> {
    public HdWinningNamesAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, HdDrawEndEntity.WinningNamesEntity winningNamesEntity) {
        if (winningNamesEntity == null) {
            return;
        }
        ((UserGradeView) baseViewHolder.getView(R$id.user_grade_view)).initUserGrade(winningNamesEntity.userGrade);
        baseViewHolder.setText(R$id.tv_nick_name, winningNamesEntity.userName).setTextColor(R$id.tv_nick_name, ContextCompat.getColor(this.mContext, TextUtils.equals(winningNamesEntity.userId, UserInfoManager.getInstance().getUserId()) ? R$color.fq_colorRed : R$color.fq_text_black));
    }
}
