package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;

/* renamed from: com.tomatolive.library.ui.view.emptyview.RecyclerIncomeEmptyView */
/* loaded from: classes3.dex */
public class RecyclerIncomeEmptyView extends LinearLayout {
    private int emptyType;
    private Context mContext;
    private TextView tvText;

    public RecyclerIncomeEmptyView(Context context) {
        this(context, null, 0);
    }

    public RecyclerIncomeEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public RecyclerIncomeEmptyView(Context context, int i) {
        this(context, null, i);
    }

    public RecyclerIncomeEmptyView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, 0, i);
    }

    public RecyclerIncomeEmptyView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mContext = context;
        this.emptyType = i2;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_empty_view_income, this);
        this.tvText = (TextView) findViewById(R$id.tv_empty_text);
        this.tvText.setText(getEmptyText());
        this.tvText.setTextColor(ContextCompat.getColor(this.mContext, getTextColor()));
        setDrawableTop();
    }

    private String getEmptyText() {
        int i = this.emptyType;
        if (i != 32) {
            if (i == 33) {
                return this.mContext.getString(R$string.fq_text_list_empty_top);
            }
            if (i == 45) {
                return this.mContext.getString(R$string.fq_lottery_not_record);
            }
            if (i == 46) {
                return this.mContext.getString(R$string.fq_no_popular_card);
            }
            if (i == 49) {
                return this.mContext.getString(R$string.fq_msg_empty_tips);
            }
            if (i == 50) {
                return this.mContext.getString(R$string.fq_pay_audience_list_empty_tips);
            }
            if (i != 100) {
                switch (i) {
                    case 35:
                        return this.mContext.getString(R$string.fq_text_list_empty_house_manager);
                    case 36:
                        return this.mContext.getString(R$string.fq_text_list_empty_banned_setting);
                    case 37:
                        return this.mContext.getString(R$string.fq_text_list_empty_consume);
                    case 38:
                        return this.mContext.getString(R$string.fq_text_list_empty_top);
                    case 39:
                        return this.mContext.getString(R$string.fq_text_list_empty_watch_record);
                    case 40:
                        return this.mContext.getString(R$string.fq_gift_empty_prompt);
                    case 41:
                        return this.mContext.getString(R$string.fq_car_list_empty_tips);
                    case 42:
                        return this.mContext.getString(R$string.fq_text_list_empty_waiting);
                    default:
                        switch (i) {
                            case 53:
                                return this.mContext.getString(R$string.fq_hd_given_award_history_empty);
                            case 54:
                                return this.mContext.getString(R$string.fq_hd_award_history_empty);
                            case 55:
                                return this.mContext.getString(R$string.fq_hd_appeal_history_empty);
                            default:
                                return this.mContext.getString(R$string.fq_text_list_empty);
                        }
                }
            }
            return this.mContext.getString(R$string.fq_lottery_not_record);
        }
        return this.mContext.getString(R$string.fq_text_list_empty_income);
    }

    private void setDrawableTop() {
        Drawable drawable;
        int i = this.emptyType;
        if (i == 45) {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_lottery_record);
        } else if (i == 46) {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_popular_card_default);
        } else if (i == 49) {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_private_msg);
        } else if (i == 50) {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_user_list_record);
        } else if (i != 100) {
            switch (i) {
                case 32:
                case 37:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_income);
                    break;
                case 33:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_top);
                    break;
                case 34:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_head);
                    break;
                case 35:
                case 36:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_head);
                    break;
                case 38:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_top_gray_2);
                    break;
                case 39:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_watch_record);
                    break;
                case 40:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_gift_record);
                    break;
                case 41:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_car_record);
                    break;
                case 42:
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_top);
                    break;
                default:
                    switch (i) {
                        case 53:
                        case 54:
                        case 55:
                            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_user_list_history);
                            break;
                        default:
                            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_income);
                            break;
                    }
            }
        } else {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_empty_watch_record);
        }
        this.tvText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, drawable, (Drawable) null, (Drawable) null);
    }

    @ColorRes
    private int getTextColor() {
        int i = this.emptyType;
        if (i == 45) {
            return R$color.fq_lottery_empty_record;
        }
        return (i == 40 || i == 38) ? R$color.fq_gray66 : R$color.fq_text_gray;
    }
}
