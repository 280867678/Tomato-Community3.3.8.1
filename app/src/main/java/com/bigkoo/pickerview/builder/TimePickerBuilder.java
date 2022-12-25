package com.bigkoo.pickerview.builder;

import android.content.Context;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class TimePickerBuilder {
    private PickerOptions mPickerOptions = new PickerOptions(2);

    public TimePickerBuilder(Context context, OnTimeSelectListener onTimeSelectListener) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.context = context;
        pickerOptions.timeSelectListener = onTimeSelectListener;
    }

    public TimePickerBuilder setType(boolean[] zArr) {
        this.mPickerOptions.type = zArr;
        return this;
    }

    public TimePickerBuilder setSubmitText(String str) {
        this.mPickerOptions.textContentConfirm = str;
        return this;
    }

    public TimePickerBuilder isDialog(boolean z) {
        this.mPickerOptions.isDialog = z;
        return this;
    }

    public TimePickerBuilder setCancelText(String str) {
        this.mPickerOptions.textContentCancel = str;
        return this;
    }

    public TimePickerBuilder setSubmitColor(int i) {
        this.mPickerOptions.textColorConfirm = i;
        return this;
    }

    public TimePickerBuilder setCancelColor(int i) {
        this.mPickerOptions.textColorCancel = i;
        return this;
    }

    public TimePickerBuilder setBgColor(int i) {
        this.mPickerOptions.bgColorWheel = i;
        return this;
    }

    public TimePickerBuilder setTitleBgColor(int i) {
        this.mPickerOptions.bgColorTitle = i;
        return this;
    }

    public TimePickerBuilder setTitleColor(int i) {
        this.mPickerOptions.textColorTitle = i;
        return this;
    }

    public TimePickerBuilder setContentTextSize(int i) {
        this.mPickerOptions.textSizeContent = i;
        return this;
    }

    public TimePickerBuilder setDate(Calendar calendar) {
        this.mPickerOptions.date = calendar;
        return this;
    }

    public TimePickerBuilder setRangDate(Calendar calendar, Calendar calendar2) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.startDate = calendar;
        pickerOptions.endDate = calendar2;
        return this;
    }

    public TimePickerBuilder isCyclic(boolean z) {
        this.mPickerOptions.cyclic = z;
        return this;
    }

    public TimePickerBuilder setOutSideCancelable(boolean z) {
        this.mPickerOptions.cancelable = z;
        return this;
    }

    public TimePickerBuilder setLabel(String str, String str2, String str3, String str4, String str5, String str6) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.label_year = str;
        pickerOptions.label_month = str2;
        pickerOptions.label_day = str3;
        pickerOptions.label_hours = str4;
        pickerOptions.label_minutes = str5;
        pickerOptions.label_seconds = str6;
        return this;
    }

    public TimePickerView build() {
        return new TimePickerView(this.mPickerOptions);
    }
}
