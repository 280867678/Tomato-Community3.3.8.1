package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LotteryLuckReportEntity;
import com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeFactory;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.MarqueeLotteryNoticeView */
/* loaded from: classes3.dex */
public class MarqueeLotteryNoticeView extends MarqueeFactory<LinearLayout, LotteryLuckReportEntity> {
    private LayoutInflater inflater;
    private Context mContext;

    public MarqueeLotteryNoticeView(Context context) {
        super(context);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeFactory
    public LinearLayout generateMarqueeItemView(LotteryLuckReportEntity lotteryLuckReportEntity) {
        LinearLayout linearLayout = (LinearLayout) this.inflater.inflate(R$layout.fq_layout_lottery_marqueen_notice_view, (ViewGroup) null);
        ((TextView) linearLayout.findViewById(R$id.tv_notice_tips)).setText(Html.fromHtml(this.mContext.getString(R$string.fq_lottery_notice_tips, AppUtils.formatUserNickName(lotteryLuckReportEntity.userName), String.valueOf(lotteryLuckReportEntity.drawWay), lotteryLuckReportEntity.propName)));
        return linearLayout;
    }
}
