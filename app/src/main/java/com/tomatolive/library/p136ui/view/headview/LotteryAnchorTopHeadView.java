package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.headview.LotteryAnchorTopHeadView */
/* loaded from: classes3.dex */
public class LotteryAnchorTopHeadView extends LinearLayout {
    private List<AnchorEntity> dataList;
    private ImageView ivAvatar;
    private ImageView ivAvatarSecond;
    private ImageView ivAvatarThird;
    private ImageView ivLabelLive;
    private ImageView ivLabelLiveSecond;
    private ImageView ivLabelLiveThird;
    private Context mContext;
    private OnAvatarClickListener onAvatarClickListener;
    private TextView tvAnchorName;
    private TextView tvAnchorNameSecond;
    private TextView tvAnchorNameThird;
    private TextView tvContribution;
    private TextView tvContributionSecond;
    private TextView tvContributionThird;

    /* renamed from: com.tomatolive.library.ui.view.headview.LotteryAnchorTopHeadView$OnAvatarClickListener */
    /* loaded from: classes3.dex */
    public interface OnAvatarClickListener {
        void onAvatarClick(AnchorEntity anchorEntity);
    }

    public LotteryAnchorTopHeadView(Context context) {
        this(context, null);
    }

    public LotteryAnchorTopHeadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LotteryAnchorTopHeadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_head_view_lottery_top_anchor, this);
        this.tvAnchorName = (TextView) findViewById(R$id.tv_anchor_name);
        this.tvAnchorNameSecond = (TextView) findViewById(R$id.tv_anchor_name_second);
        this.tvAnchorNameThird = (TextView) findViewById(R$id.tv_anchor_name_third);
        this.tvContribution = (TextView) findViewById(R$id.tv_contribution);
        this.tvContributionSecond = (TextView) findViewById(R$id.tv_contribution_second);
        this.tvContributionThird = (TextView) findViewById(R$id.tv_contribution_third);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivAvatarSecond = (ImageView) findViewById(R$id.iv_avatar_second);
        this.ivAvatarThird = (ImageView) findViewById(R$id.iv_avatar_third);
        this.ivLabelLive = (ImageView) findViewById(R$id.iv_label_live);
        this.ivLabelLiveSecond = (ImageView) findViewById(R$id.iv_label_live_second);
        this.ivLabelLiveThird = (ImageView) findViewById(R$id.iv_label_live_third);
        this.ivAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.LotteryAnchorTopHeadView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LotteryAnchorTopHeadView.this.dataList == null || LotteryAnchorTopHeadView.this.dataList.isEmpty() || LotteryAnchorTopHeadView.this.dataList.size() < 1 || LotteryAnchorTopHeadView.this.onAvatarClickListener == null) {
                    return;
                }
                LotteryAnchorTopHeadView.this.onAvatarClickListener.onAvatarClick((AnchorEntity) LotteryAnchorTopHeadView.this.dataList.get(0));
            }
        });
        this.ivAvatarSecond.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.LotteryAnchorTopHeadView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LotteryAnchorTopHeadView.this.dataList == null || LotteryAnchorTopHeadView.this.dataList.isEmpty() || LotteryAnchorTopHeadView.this.dataList.size() < 2 || LotteryAnchorTopHeadView.this.onAvatarClickListener == null) {
                    return;
                }
                LotteryAnchorTopHeadView.this.onAvatarClickListener.onAvatarClick((AnchorEntity) LotteryAnchorTopHeadView.this.dataList.get(1));
            }
        });
        this.ivAvatarThird.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.LotteryAnchorTopHeadView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LotteryAnchorTopHeadView.this.dataList == null || LotteryAnchorTopHeadView.this.dataList.isEmpty() || LotteryAnchorTopHeadView.this.dataList.size() < 3 || LotteryAnchorTopHeadView.this.onAvatarClickListener == null) {
                    return;
                }
                LotteryAnchorTopHeadView.this.onAvatarClickListener.onAvatarClick((AnchorEntity) LotteryAnchorTopHeadView.this.dataList.get(2));
            }
        });
    }

    public void initData(List<AnchorEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.dataList = list;
        if (list.size() >= 3) {
            initShowDataView(this.ivAvatar, this.tvAnchorName, this.tvContribution, this.ivLabelLive, list.get(0));
            initShowDataView(this.ivAvatarSecond, this.tvAnchorNameSecond, this.tvContributionSecond, this.ivLabelLiveSecond, list.get(1));
            initShowDataView(this.ivAvatarThird, this.tvAnchorNameThird, this.tvContributionThird, this.ivLabelLiveThird, list.get(2));
        } else if (list.size() >= 2) {
            initShowDataView(this.ivAvatar, this.tvAnchorName, this.tvContribution, this.ivLabelLive, list.get(0));
            initShowDataView(this.ivAvatarSecond, this.tvAnchorNameSecond, this.tvContributionSecond, this.ivLabelLiveSecond, list.get(1));
            initShowDataView(this.ivAvatarThird, this.tvAnchorNameThird, this.tvContributionThird, this.ivLabelLiveThird, getEmptyItem());
        } else {
            initShowDataView(this.ivAvatar, this.tvAnchorName, this.tvContribution, this.ivLabelLive, list.get(0));
            initShowDataView(this.ivAvatarSecond, this.tvAnchorNameSecond, this.tvContributionSecond, this.ivLabelLiveSecond, getEmptyItem());
            initShowDataView(this.ivAvatarThird, this.tvAnchorNameThird, this.tvContributionThird, this.ivLabelLiveThird, getEmptyItem());
        }
    }

    public OnAvatarClickListener getOnAvatarClickListener() {
        return this.onAvatarClickListener;
    }

    public void setOnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    private void initShowDataView(ImageView imageView, TextView textView, TextView textView2, ImageView imageView2, AnchorEntity anchorEntity) {
        if (TextUtils.isEmpty(anchorEntity.avatar)) {
            imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
        } else {
            Context context = this.mContext;
            GlideUtils.loadAvatar(context, imageView, anchorEntity.avatar, 6, ContextCompat.getColor(context, R$color.fq_colorWhite));
        }
        if (TextUtils.isEmpty(anchorEntity.nickname)) {
            textView.setText(R$string.fq_text_list_empty_waiting);
        } else {
            textView.setText(StringUtils.formatStrLen(anchorEntity.nickname, 5));
        }
        int i = 0;
        if (TextUtils.isEmpty(anchorEntity.count)) {
            textView2.setText("");
        } else {
            long string2long = NumberUtils.string2long(anchorEntity.count);
            if (string2long > 10000) {
                textView2.setText(this.mContext.getString(R$string.fq_number_unit_ten_thousand, AppUtils.formatTenThousandUnit(String.valueOf(string2long))));
            } else {
                textView2.setText(this.mContext.getString(R$string.fq_number_unit_one, NumberUtils.formatThreeNumStr(String.valueOf(string2long))));
            }
        }
        if (TextUtils.isEmpty(anchorEntity.liveStatus)) {
            imageView2.setVisibility(4);
            return;
        }
        imageView2.setImageResource(R$drawable.fq_ic_lottery_top_head_living);
        if (!AppUtils.isLiving(anchorEntity.liveStatus)) {
            i = 4;
        }
        imageView2.setVisibility(i);
    }

    private AnchorEntity getEmptyItem() {
        return new AnchorEntity();
    }
}
