package com.bigkoo.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.R$id;
import com.bigkoo.pickerview.R$layout;
import com.bigkoo.pickerview.R$string;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import java.text.ParseException;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private WheelTime wheelTime;

    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        CustomListener customListener = this.mPickerOptions.customListener;
        if (customListener == null) {
            LayoutInflater.from(context).inflate(R$layout.pickerview_time, this.contentContainer);
            TextView textView = (TextView) findViewById(R$id.tvTitle);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R$id.rv_topbar);
            Button button = (Button) findViewById(R$id.btnSubmit);
            Button button2 = (Button) findViewById(R$id.btnCancel);
            button.setTag("submit");
            button2.setTag("cancel");
            button.setOnClickListener(this);
            button2.setOnClickListener(this);
            button.setText(TextUtils.isEmpty(this.mPickerOptions.textContentConfirm) ? context.getResources().getString(R$string.pickerview_submit) : this.mPickerOptions.textContentConfirm);
            button2.setText(TextUtils.isEmpty(this.mPickerOptions.textContentCancel) ? context.getResources().getString(R$string.pickerview_cancel) : this.mPickerOptions.textContentCancel);
            textView.setText(TextUtils.isEmpty(this.mPickerOptions.textContentTitle) ? "" : this.mPickerOptions.textContentTitle);
            button.setTextColor(this.mPickerOptions.textColorConfirm);
            button2.setTextColor(this.mPickerOptions.textColorCancel);
            textView.setTextColor(this.mPickerOptions.textColorTitle);
            relativeLayout.setBackgroundColor(this.mPickerOptions.bgColorTitle);
            button.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            button2.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            textView.setTextSize(this.mPickerOptions.textSizeTitle);
        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(this.mPickerOptions.layoutRes, this.contentContainer));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.timepicker);
        linearLayout.setBackgroundColor(this.mPickerOptions.bgColorWheel);
        initWheelTime(linearLayout);
    }

    private void initWheelTime(LinearLayout linearLayout) {
        int i;
        PickerOptions pickerOptions = this.mPickerOptions;
        this.wheelTime = new WheelTime(linearLayout, pickerOptions.type, pickerOptions.textGravity, pickerOptions.textSizeContent);
        if (this.mPickerOptions.timeSelectChangeListener != null) {
            this.wheelTime.setSelectChangeCallback(new ISelectTimeCallback() { // from class: com.bigkoo.pickerview.view.TimePickerView.1
                @Override // com.bigkoo.pickerview.listener.ISelectTimeCallback
                public void onTimeSelectChanged() {
                    try {
                        TimePickerView.this.mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(WheelTime.dateFormat.parse(TimePickerView.this.wheelTime.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        this.wheelTime.setLunarMode(this.mPickerOptions.isLunarCalendar);
        PickerOptions pickerOptions2 = this.mPickerOptions;
        int i2 = pickerOptions2.startYear;
        if (i2 != 0 && (i = pickerOptions2.endYear) != 0 && i2 <= i) {
            setRange();
        }
        PickerOptions pickerOptions3 = this.mPickerOptions;
        Calendar calendar = pickerOptions3.startDate;
        if (calendar != null && pickerOptions3.endDate != null) {
            if (calendar.getTimeInMillis() > this.mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            }
            setRangDate();
        } else {
            PickerOptions pickerOptions4 = this.mPickerOptions;
            Calendar calendar2 = pickerOptions4.startDate;
            if (calendar2 != null) {
                if (calendar2.get(1) < 1900) {
                    throw new IllegalArgumentException("The startDate can not as early as 1900");
                }
                setRangDate();
            } else {
                Calendar calendar3 = pickerOptions4.endDate;
                if (calendar3 != null) {
                    if (calendar3.get(1) > 2100) {
                        throw new IllegalArgumentException("The endDate should not be later than 2100");
                    }
                    setRangDate();
                } else {
                    setRangDate();
                }
            }
        }
        setTime();
        WheelTime wheelTime = this.wheelTime;
        PickerOptions pickerOptions5 = this.mPickerOptions;
        wheelTime.setLabels(pickerOptions5.label_year, pickerOptions5.label_month, pickerOptions5.label_day, pickerOptions5.label_hours, pickerOptions5.label_minutes, pickerOptions5.label_seconds);
        WheelTime wheelTime2 = this.wheelTime;
        PickerOptions pickerOptions6 = this.mPickerOptions;
        wheelTime2.setTextXOffset(pickerOptions6.x_offset_year, pickerOptions6.x_offset_month, pickerOptions6.x_offset_day, pickerOptions6.x_offset_hours, pickerOptions6.x_offset_minutes, pickerOptions6.x_offset_seconds);
        setOutSideCancelable(this.mPickerOptions.cancelable);
        this.wheelTime.setCyclic(this.mPickerOptions.cyclic);
        this.wheelTime.setDividerColor(this.mPickerOptions.dividerColor);
        this.wheelTime.setDividerType(this.mPickerOptions.dividerType);
        this.wheelTime.setLineSpacingMultiplier(this.mPickerOptions.lineSpacingMultiplier);
        this.wheelTime.setTextColorOut(this.mPickerOptions.textColorOut);
        this.wheelTime.setTextColorCenter(this.mPickerOptions.textColorCenter);
        this.wheelTime.isCenterLabel(this.mPickerOptions.isCenterLabel);
    }

    public void setDate(Calendar calendar) {
        this.mPickerOptions.date = calendar;
        setTime();
    }

    private void setRange() {
        this.wheelTime.setStartYear(this.mPickerOptions.startYear);
        this.wheelTime.setEndYear(this.mPickerOptions.endYear);
    }

    private void setRangDate() {
        WheelTime wheelTime = this.wheelTime;
        PickerOptions pickerOptions = this.mPickerOptions;
        wheelTime.setRangDate(pickerOptions.startDate, pickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        PickerOptions pickerOptions = this.mPickerOptions;
        if (pickerOptions.startDate != null && pickerOptions.endDate != null) {
            Calendar calendar = pickerOptions.date;
            if (calendar != null && calendar.getTimeInMillis() >= this.mPickerOptions.startDate.getTimeInMillis() && this.mPickerOptions.date.getTimeInMillis() <= this.mPickerOptions.endDate.getTimeInMillis()) {
                return;
            }
            PickerOptions pickerOptions2 = this.mPickerOptions;
            pickerOptions2.date = pickerOptions2.startDate;
            return;
        }
        PickerOptions pickerOptions3 = this.mPickerOptions;
        Calendar calendar2 = pickerOptions3.startDate;
        if (calendar2 != null) {
            pickerOptions3.date = calendar2;
            return;
        }
        Calendar calendar3 = pickerOptions3.endDate;
        if (calendar3 == null) {
            return;
        }
        pickerOptions3.date = calendar3;
    }

    private void setTime() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = this.mPickerOptions.date;
        if (calendar2 == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            i = calendar.get(1);
            i2 = calendar.get(2);
            i3 = calendar.get(5);
            i4 = calendar.get(11);
            i5 = calendar.get(12);
            i6 = calendar.get(13);
        } else {
            i = calendar2.get(1);
            i2 = this.mPickerOptions.date.get(2);
            i3 = this.mPickerOptions.date.get(5);
            i4 = this.mPickerOptions.date.get(11);
            i5 = this.mPickerOptions.date.get(12);
            i6 = this.mPickerOptions.date.get(13);
        }
        int i7 = i4;
        int i8 = i3;
        int i9 = i2;
        WheelTime wheelTime = this.wheelTime;
        wheelTime.setPicker(i, i9, i8, i7, i5, i6);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        View.OnClickListener onClickListener;
        String str = (String) view.getTag();
        if (str.equals("submit")) {
            returnData();
        } else if (str.equals("cancel") && (onClickListener = this.mPickerOptions.cancelListener) != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    public void returnData() {
        if (this.mPickerOptions.timeSelectListener != null) {
            try {
                this.mPickerOptions.timeSelectListener.onTimeSelect(WheelTime.dateFormat.parse(this.wheelTime.getTime()), this.clickView);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.bigkoo.pickerview.view.BasePickerView
    public boolean isDialog() {
        return this.mPickerOptions.isDialog;
    }
}
