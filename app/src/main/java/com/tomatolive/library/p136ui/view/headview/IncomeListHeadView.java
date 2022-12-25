package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BarUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;

/* renamed from: com.tomatolive.library.ui.view.headview.IncomeListHeadView */
/* loaded from: classes3.dex */
public class IncomeListHeadView extends LinearLayout {
    private Date currentDate;
    private boolean freeType = true;
    private int incomeType;
    private Context mContext;
    private OnDateSelectedListener onDateSelectedListener;
    private OnPropsDateSelectedListener onPropsDateSelectedListener;
    private TimePickerView pvTime;
    private RelativeLayout rlPropsBg;
    private TextView tvCalendar;
    private TextView tvCalendarProps;
    private TextView tvFree;
    private TextView tvPrice;
    private TextView tvSettle;
    private TextView tvUnit;

    /* renamed from: com.tomatolive.library.ui.view.headview.IncomeListHeadView$OnDateSelectedListener */
    /* loaded from: classes3.dex */
    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    /* renamed from: com.tomatolive.library.ui.view.headview.IncomeListHeadView$OnPropsDateSelectedListener */
    /* loaded from: classes3.dex */
    public interface OnPropsDateSelectedListener {
        void onDateSelected(Date date, boolean z);
    }

    public IncomeListHeadView(Context context) {
        super(context);
        initView(context);
    }

    public IncomeListHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_income, this);
        this.tvPrice = (TextView) findViewById(R$id.tv_gold);
        this.tvCalendar = (TextView) findViewById(R$id.tv_calendar);
        this.tvCalendarProps = (TextView) findViewById(R$id.tv_calendar_props);
        this.tvFree = (TextView) findViewById(R$id.tv_free);
        this.tvSettle = (TextView) findViewById(R$id.tv_settle);
        this.tvUnit = (TextView) findViewById(R$id.tv_unit);
        this.rlPropsBg = (RelativeLayout) findViewById(R$id.rl_props_bg);
        this.tvCalendar.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.IncomeListHeadView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (IncomeListHeadView.this.pvTime != null) {
                    IncomeListHeadView.this.pvTime.show();
                }
            }
        });
        this.tvCalendarProps.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.IncomeListHeadView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (IncomeListHeadView.this.pvTime != null) {
                    IncomeListHeadView.this.pvTime.show();
                }
            }
        });
        this.tvFree.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.IncomeListHeadView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                IncomeListHeadView.this.initLabelView(true);
                if (IncomeListHeadView.this.onPropsDateSelectedListener != null) {
                    if (IncomeListHeadView.this.currentDate == null) {
                        IncomeListHeadView.this.currentDate = Calendar.getInstance().getTime();
                    }
                    IncomeListHeadView.this.onPropsDateSelectedListener.onDateSelected(IncomeListHeadView.this.currentDate, IncomeListHeadView.this.freeType);
                }
            }
        });
        this.tvSettle.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.IncomeListHeadView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                IncomeListHeadView.this.initLabelView(false);
                if (IncomeListHeadView.this.onPropsDateSelectedListener != null) {
                    if (IncomeListHeadView.this.currentDate == null) {
                        IncomeListHeadView.this.currentDate = Calendar.getInstance().getTime();
                    }
                    IncomeListHeadView.this.onPropsDateSelectedListener.onDateSelected(IncomeListHeadView.this.currentDate, IncomeListHeadView.this.freeType);
                }
            }
        });
    }

    public void initData(int i, boolean z) {
        this.incomeType = i;
        int i2 = 4;
        boolean z2 = i == 4;
        this.rlPropsBg.setVisibility(z2 ? 0 : 4);
        TextView textView = this.tvCalendar;
        if (!z2) {
            i2 = 0;
        }
        textView.setVisibility(i2);
        initLabelView(true);
        initTimePickerView(z2);
        if (i == 7) {
            this.tvUnit.setVisibility(0);
            if (z) {
                this.tvUnit.setText(R$string.fq_score);
                return;
            } else {
                this.tvUnit.setText(R$string.fq_popularity);
                return;
            }
        }
        this.tvUnit.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLabelView(boolean z) {
        this.freeType = z;
        this.tvFree.setSelected(z);
        this.tvSettle.setSelected(!z);
    }

    private void initTimePickerView(final boolean z) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2017, 0, 1);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2030, 11, 1);
        if (z) {
            this.tvCalendarProps.setText(DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DATE_CHINA_2));
        } else {
            this.tvCalendar.setText(DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DATE_CHINA_2));
        }
        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this.mContext, new OnTimeSelectListener() { // from class: com.tomatolive.library.ui.view.headview.IncomeListHeadView.5
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public void onTimeSelect(Date date, View view) {
                IncomeListHeadView.this.currentDate = date;
                if (z) {
                    IncomeListHeadView.this.tvCalendarProps.setText(DateUtils.dateToString(date, DateUtils.C_DATE_PATTON_DATE_CHINA_2));
                    if (IncomeListHeadView.this.onPropsDateSelectedListener == null) {
                        return;
                    }
                    IncomeListHeadView.this.onPropsDateSelectedListener.onDateSelected(date, IncomeListHeadView.this.freeType);
                    return;
                }
                IncomeListHeadView.this.tvCalendar.setText(DateUtils.dateToString(date, DateUtils.C_DATE_PATTON_DATE_CHINA_2));
                if (IncomeListHeadView.this.onDateSelectedListener == null) {
                    return;
                }
                IncomeListHeadView.this.onDateSelectedListener.onDateSelected(date);
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
        this.pvTime = timePickerBuilder.build();
        if (BarUtils.isSupportNavBar()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.pvTime.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin = BarUtils.getNavBarHeight();
            this.pvTime.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public void setOnPropsDateSelectedListener(OnPropsDateSelectedListener onPropsDateSelectedListener) {
        this.onPropsDateSelectedListener = onPropsDateSelectedListener;
    }

    public void setCurrentGold(String str) {
        if (this.incomeType == 7) {
            this.tvPrice.setText(str);
        } else {
            this.tvPrice.setText(AppUtils.formatDisplayPrice(str, true));
        }
    }

    public void setSelectDate(String str) {
        String[] split = str.split("-");
        this.tvCalendar.setText(String.format("%s年%s月%s日", split[0], split[1], split[2]));
        this.tvCalendarProps.setText(String.format("%s年%s月%s日", split[0], split[1], split[2]));
        this.currentDate = DateUtils.getCalendarFormat(str).getTime();
        this.pvTime.setDate(DateUtils.getCalendarFormat(str));
    }
}
