package com.one.tomato.constants;

import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.lockscreen.LockScreenPwdEnterActivity;
import com.one.tomato.p085ui.lockscreen.LoginAccountVerityActivity;
import com.one.tomato.utils.AppUtil;

/* loaded from: classes3.dex */
public class Constants {
    public static final Class[] FILTER_LOCK_SCREEN_ACTIVITY = {LockScreenPwdEnterActivity.class, LoginAccountVerityActivity.class};

    /* loaded from: classes3.dex */
    public interface AppKey {
        public static final String HEADER_KEY_USER_AGENT = "tomato/" + AppUtil.getVersionName();
    }

    /* loaded from: classes3.dex */
    public enum OrderStatus {
        APPLY(0, AppUtil.getString(R.string.income_order_status_0)),
        CHECKED(1, AppUtil.getString(R.string.income_order_status_1)),
        UNCHECKED(2, AppUtil.getString(R.string.income_order_status_2)),
        HANDLE(3, AppUtil.getString(R.string.income_order_status_3)),
        CASH_SUCCESS(4, AppUtil.getString(R.string.income_order_status_4)),
        CASH_FAIL(5, AppUtil.getString(R.string.income_order_status_5)),
        PAYMENT_FAIL(6, AppUtil.getString(R.string.income_order_status_6));
        
        private final int key;
        private final String value;

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        OrderStatus(int i, String str) {
            this.key = i;
            this.value = str;
        }

        public static String getValueByKey(int i) {
            OrderStatus[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getKey() == i) {
                    return values[i2].getValue();
                }
            }
            return "";
        }
    }

    /* loaded from: classes3.dex */
    public enum IncomeTypePlant {
        INCOME(2, AppUtil.getString(R.string.income_detail_type_in)),
        COST(1, AppUtil.getString(R.string.income_detail_type_out)),
        MINUS_MONEY(3, AppUtil.getString(R.string.income_minus_money)),
        ADD_MONEY(4, AppUtil.getString(R.string.income_add_money));
        
        private final int key;
        private final String value;

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        IncomeTypePlant(int i, String str) {
            this.key = i;
            this.value = str;
        }

        public static String getValueByKey(int i) {
            IncomeTypePlant[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getKey() == i) {
                    return values[i2].getValue();
                }
            }
            return "";
        }
    }

    /* loaded from: classes3.dex */
    public enum IncomeStatus {
        INCOME(2, AppUtil.getString(R.string.income_type_in)),
        COST(1, AppUtil.getString(R.string.income_type_out));
        
        private final int key;
        private final String value;

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        IncomeStatus(int i, String str) {
            this.key = i;
            this.value = str;
        }

        public static String getValueByKey(int i) {
            IncomeStatus[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getKey() == i) {
                    return values[i2].getValue();
                }
            }
            return "";
        }
    }
}
