package com.bigkoo.pickerview.adapter;

import com.contrarywind.adapter.WheelAdapter;

/* loaded from: classes2.dex */
public class NumericWheelAdapter implements WheelAdapter {
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(int i, int i2) {
        this.minValue = i;
        this.maxValue = i2;
    }

    @Override // com.contrarywind.adapter.WheelAdapter
    public Object getItem(int i) {
        if (i >= 0 && i < getItemsCount()) {
            return Integer.valueOf(this.minValue + i);
        }
        return 0;
    }

    @Override // com.contrarywind.adapter.WheelAdapter
    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }
}
