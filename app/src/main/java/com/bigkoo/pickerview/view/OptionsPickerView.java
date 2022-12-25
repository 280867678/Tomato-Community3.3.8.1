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
import com.bigkoo.pickerview.R$string;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import java.util.List;

/* loaded from: classes2.dex */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {
    private WheelOptions wheelOptions;

    public OptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        initEvents();
        CustomListener customListener = this.mPickerOptions.customListener;
        if (customListener == null) {
            LayoutInflater.from(context).inflate(this.mPickerOptions.layoutRes, this.contentContainer);
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
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.optionspicker);
        linearLayout.setBackgroundColor(this.mPickerOptions.bgColorWheel);
        this.wheelOptions = new WheelOptions(linearLayout, this.mPickerOptions.isRestoreItem);
        OnOptionsSelectChangeListener onOptionsSelectChangeListener = this.mPickerOptions.optionsSelectChangeListener;
        if (onOptionsSelectChangeListener != null) {
            this.wheelOptions.setOptionsSelectChangeListener(onOptionsSelectChangeListener);
        }
        this.wheelOptions.setTextContentSize(this.mPickerOptions.textSizeContent);
        WheelOptions wheelOptions = this.wheelOptions;
        PickerOptions pickerOptions = this.mPickerOptions;
        wheelOptions.setLabels(pickerOptions.label1, pickerOptions.label2, pickerOptions.label3);
        WheelOptions wheelOptions2 = this.wheelOptions;
        PickerOptions pickerOptions2 = this.mPickerOptions;
        wheelOptions2.setTextXOffset(pickerOptions2.x_offset_one, pickerOptions2.x_offset_two, pickerOptions2.x_offset_three);
        WheelOptions wheelOptions3 = this.wheelOptions;
        PickerOptions pickerOptions3 = this.mPickerOptions;
        wheelOptions3.setCyclic(pickerOptions3.cyclic1, pickerOptions3.cyclic2, pickerOptions3.cyclic3);
        this.wheelOptions.setTypeface(this.mPickerOptions.font);
        setOutSideCancelable(this.mPickerOptions.cancelable);
        this.wheelOptions.setDividerColor(this.mPickerOptions.dividerColor);
        this.wheelOptions.setDividerType(this.mPickerOptions.dividerType);
        this.wheelOptions.setLineSpacingMultiplier(this.mPickerOptions.lineSpacingMultiplier);
        this.wheelOptions.setTextColorOut(this.mPickerOptions.textColorOut);
        this.wheelOptions.setTextColorCenter(this.mPickerOptions.textColorCenter);
        this.wheelOptions.isCenterLabel(this.mPickerOptions.isCenterLabel);
    }

    private void reSetCurrentItems() {
        WheelOptions wheelOptions = this.wheelOptions;
        if (wheelOptions != null) {
            PickerOptions pickerOptions = this.mPickerOptions;
            wheelOptions.setCurrentItems(pickerOptions.option1, pickerOptions.option2, pickerOptions.option3);
        }
    }

    public void setPicker(List<T> list) {
        setPicker(list, null, null);
    }

    public void setPicker(List<T> list, List<List<T>> list2, List<List<List<T>>> list3) {
        this.wheelOptions.setPicker(list, list2, list3);
        reSetCurrentItems();
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
        if (this.mPickerOptions.optionsSelectListener != null) {
            int[] currentItems = this.wheelOptions.getCurrentItems();
            this.mPickerOptions.optionsSelectListener.onOptionsSelect(currentItems[0], currentItems[1], currentItems[2], this.clickView);
        }
    }

    @Override // com.bigkoo.pickerview.view.BasePickerView
    public boolean isDialog() {
        return this.mPickerOptions.isDialog;
    }
}
