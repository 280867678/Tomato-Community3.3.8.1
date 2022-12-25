package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BarUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;

/* renamed from: com.tomatolive.library.ui.view.headview.LotteryRecordHeadView */
/* loaded from: classes3.dex */
public class LotteryRecordHeadView extends LinearLayout {
    private Context mContext;
    private OnDateSelectedListener onDateSelectedListener;
    private TimePickerView pvTime;
    private TextView tvCalendar;

    /* renamed from: com.tomatolive.library.ui.view.headview.LotteryRecordHeadView$OnDateSelectedListener */
    /* loaded from: classes3.dex */
    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    public LotteryRecordHeadView(Context context) {
        super(context);
        initView(context);
    }

    public LotteryRecordHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_lottery_record, this);
        this.tvCalendar = (TextView) findViewById(R$id.tv_date);
        initTimePickerView();
        setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.LotteryRecordHeadView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LotteryRecordHeadView.this.pvTime != null) {
                    LotteryRecordHeadView.this.pvTime.show();
                }
            }
        });
    }

    private void initTimePickerView() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2017, 0, 1);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2030, 11, 1);
        this.tvCalendar.setText(DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DATE_CHINA_2));
        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this.mContext, new OnTimeSelectListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$LotteryRecordHeadView$XEeK_3QlW5CUnEncuYxsl3GLrsI
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public final void onTimeSelect(Date date, View view) {
                LotteryRecordHeadView.this.lambda$initTimePickerView$0$LotteryRecordHeadView(date, view);
            }
        });
        timePickerBuilder.setType(new boolean[]{true, true, true, false, false, false});
        timePickerBuilder.setCancelText(this.mContext.getString(R$string.fq_btn_cancel));
        timePickerBuilder.setSubmitText(this.mContext.getString(R$string.fq_done));
        timePickerBuilder.setOutSideCancelable(true);
        timePickerBuilder.isCyclic(false);
        timePickerBuilder.setTitleColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        timePickerBuilder.setSubmitColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
        timePickerBuilder.setCancelColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        timePickerBuilder.setTitleBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        timePickerBuilder.setBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        timePickerBuilder.setContentTextSize(20);
        timePickerBuilder.setDate(calendar);
        timePickerBuilder.setRangDate(calendar2, calendar3);
        timePickerBuilder.setLabel("年", "月", "日", "时", "分", "秒");
        timePickerBuilder.isDialog(true);
        this.pvTime = timePickerBuilder.build();
        if (BarUtils.isSupportNavBar()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.pvTime.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin = BarUtils.getNavBarHeight();
            this.pvTime.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
    }

    public /* synthetic */ void lambda$initTimePickerView$0$LotteryRecordHeadView(Date date, View view) {
        this.tvCalendar.setText(DateUtils.dateToString(date, DateUtils.C_DATE_PATTON_DATE_CHINA_2));
        OnDateSelectedListener onDateSelectedListener = this.onDateSelectedListener;
        if (onDateSelectedListener != null) {
            onDateSelectedListener.onDateSelected(date);
        }
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }
}
