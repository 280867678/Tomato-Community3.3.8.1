package com.tomatolive.library.p136ui.view.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.NetworkUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SoftKeyBoardListener;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.functions.Function;

/* renamed from: com.tomatolive.library.ui.view.custom.AnchorLiveEndView */
/* loaded from: classes3.dex */
public class AnchorLiveEndView extends LinearLayout {
    private AnchorGradeView anchorGradeView;
    private EditText etProblemDesc;
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private ImageView ivGender;
    private final Context mContext;
    private OnLiveEndClosedListener onLiveEndClosedListener;
    private RelativeLayout rl_top_title_bg;
    private long startLiveTimeMillis = 0;
    private TextView tvEndText;
    private TextView tvEndTips;
    private TextView tvGold;
    private TextView tvNickName;
    private TextView tvPersonal;
    private TextView tvSubmit;
    private TextView tvTime;

    /* renamed from: com.tomatolive.library.ui.view.custom.AnchorLiveEndView$OnLiveEndClosedListener */
    /* loaded from: classes3.dex */
    public interface OnLiveEndClosedListener {
        void onClosedListener();

        void onFeedbackSubmitListener(String str);
    }

    public AnchorLiveEndView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(1);
        setBackground(new ColorDrawable(Color.parseColor("#CC000000")));
        LinearLayout.inflate(this.mContext, R$layout.fq_layout_anchor_live_end_view, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_anchor_head);
        this.tvEndText = (TextView) findViewById(R$id.tv_live_end_text);
        this.tvEndTips = (TextView) findViewById(R$id.tv_live_end_tips);
        this.tvGold = (TextView) findViewById(R$id.tv_gold);
        this.tvPersonal = (TextView) findViewById(R$id.tv_personal);
        this.tvTime = (TextView) findViewById(R$id.tv_time);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvSubmit = (TextView) findViewById(R$id.tv_submit);
        this.etProblemDesc = (EditText) findViewById(R$id.et_problem_desc);
        this.ivGender = (ImageView) findViewById(R$id.iv_gender);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        this.anchorGradeView = (AnchorGradeView) findViewById(R$id.anchor_grade_view);
        this.rl_top_title_bg = (RelativeLayout) findViewById(R$id.rl_top_title_bg);
        findViewById(R$id.iv_end_back).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$AnchorLiveEndView$0Omyb3wSSnd30nqoqL4VTkJ3zpo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorLiveEndView.this.lambda$initView$0$AnchorLiveEndView(view);
            }
        });
        findViewById(R$id.tv_home).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$AnchorLiveEndView$ofBjqAUpENbLBnQ99QaYzFnj6Js
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorLiveEndView.this.lambda$initView$1$AnchorLiveEndView(view);
            }
        });
        this.tvSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$AnchorLiveEndView$5Ktqkr56WiOHLvGL_6h7GJPNjx0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorLiveEndView.this.lambda$initView$2$AnchorLiveEndView(view);
            }
        });
        RxTextView.textChanges(this.etProblemDesc).map(new Function<CharSequence, Boolean>() { // from class: com.tomatolive.library.ui.view.custom.AnchorLiveEndView.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Boolean mo6755apply(CharSequence charSequence) throws Exception {
                return Boolean.valueOf(!TextUtils.isEmpty(charSequence));
            }
        }).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.AnchorLiveEndView.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                AnchorLiveEndView.this.tvSubmit.setEnabled(bool.booleanValue());
            }
        });
        Context context = this.mContext;
        if (context instanceof Activity) {
            SoftKeyBoardListener.setListener((Activity) context, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() { // from class: com.tomatolive.library.ui.view.custom.AnchorLiveEndView.3
                @Override // com.tomatolive.library.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
                public void keyBoardShow(int i) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AnchorLiveEndView.this.rl_top_title_bg.getLayoutParams();
                    marginLayoutParams.topMargin = -((i * 2) / 3);
                    AnchorLiveEndView.this.rl_top_title_bg.setLayoutParams(marginLayoutParams);
                }

                @Override // com.tomatolive.library.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
                public void keyBoardHide(int i) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AnchorLiveEndView.this.rl_top_title_bg.getLayoutParams();
                    marginLayoutParams.topMargin = 0;
                    AnchorLiveEndView.this.rl_top_title_bg.setLayoutParams(marginLayoutParams);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initView$0$AnchorLiveEndView(View view) {
        OnLiveEndClosedListener onLiveEndClosedListener = this.onLiveEndClosedListener;
        if (onLiveEndClosedListener != null) {
            onLiveEndClosedListener.onClosedListener();
        }
    }

    public /* synthetic */ void lambda$initView$1$AnchorLiveEndView(View view) {
        OnLiveEndClosedListener onLiveEndClosedListener = this.onLiveEndClosedListener;
        if (onLiveEndClosedListener != null) {
            onLiveEndClosedListener.onClosedListener();
        }
    }

    public /* synthetic */ void lambda$initView$2$AnchorLiveEndView(View view) {
        OnLiveEndClosedListener onLiveEndClosedListener = this.onLiveEndClosedListener;
        if (onLiveEndClosedListener != null) {
            onLiveEndClosedListener.onFeedbackSubmitListener(this.etProblemDesc.getText().toString().trim());
        }
    }

    public void initData(int i, long j, LiveEndEntity liveEndEntity) {
        long string2long;
        long string2long2;
        long j2;
        if (liveEndEntity == null) {
            return;
        }
        Context context = this.mContext;
        GlideUtils.loadAvatar(context, this.ivAvatar, liveEndEntity.avatar, 6, ContextCompat.getColor(context, R$color.fq_colorWhite));
        this.tvNickName.setText(liveEndEntity.nickname);
        this.anchorGradeView.initUserGrade(liveEndEntity.expGrade);
        this.ivBadge.setVisibility(AppUtils.isNobilityUser(liveEndEntity.nobilityType) ? 0 : 8);
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(liveEndEntity.nobilityType));
        if (AppUtils.getGenderRes(liveEndEntity.sex) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(liveEndEntity.sex));
        }
        this.tvEndText.setText(R$string.fq_live_end);
        String str = "0";
        if (i == 3) {
            this.tvEndTips.setText(!NetworkUtils.isConnected() ? getErrorTips(0) : getErrorTips(1));
            j2 = System.currentTimeMillis() - j;
        } else {
            if (i == 2) {
                this.tvEndTips.setText(getErrorTips(2));
                string2long = NumberUtils.string2long(liveEndEntity.endTime);
                string2long2 = NumberUtils.string2long(liveEndEntity.startTime);
            } else {
                if (liveEndEntity.liveCount.equals(str)) {
                    this.tvEndTips.setText("");
                } else {
                    this.tvEndTips.setText(this.mContext.getString(R$string.fq_text_start_live_count_tips, liveEndEntity.liveCount));
                }
                string2long = NumberUtils.string2long(liveEndEntity.endTime);
                string2long2 = NumberUtils.string2long(liveEndEntity.startTime);
            }
            j2 = (string2long - string2long2) * 1000;
        }
        String stringForTime = DateUtils.stringForTime(j2);
        if (j2 < 0) {
            stringForTime = str;
        }
        if (!TextUtils.isEmpty(liveEndEntity.maxPopularity)) {
            str = AppUtils.formatOnlineUserCount(liveEndEntity.maxPopularity);
        }
        this.tvGold.setText(getLiveEndGoldStr(liveEndEntity.getAnchorIncomePrice()));
        this.tvPersonal.setText(str);
        this.tvTime.setText(stringForTime);
    }

    public void setOnLiveEndClosedListener(OnLiveEndClosedListener onLiveEndClosedListener) {
        this.onLiveEndClosedListener = onLiveEndClosedListener;
    }

    private String getLiveEndGoldStr(String str) {
        return TextUtils.equals("-1", str) ? this.mContext.getString(R$string.fq_acquiring) : str;
    }

    private String getErrorTips(int i) {
        return this.mContext.getResources().getStringArray(R$array.fq_anchor_live_end_error_tips)[i];
    }

    public long getStartLiveTimeMillis() {
        return this.startLiveTimeMillis;
    }

    public void setStartLiveTimeMillis(long j) {
        this.startLiveTimeMillis = j;
    }
}
