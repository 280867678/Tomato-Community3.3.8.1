package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.SystemUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveEndInfoView */
/* loaded from: classes3.dex */
public class LiveEndInfoView extends RelativeLayout implements View.OnClickListener {
    private AnchorEntity anchorEntity;
    private Context mContext;
    private ImageView mHeadImg;
    private LiveEndClickListener mLiveEndClickListener;
    private TextView mTvAttention;
    private TextView mTvId;
    private TextView mTvLastEndTime;
    private TextView mTvLiveTime;
    private TextView mTvWatchNum;
    private TextView tvHomepage;
    private TextView tvLivePre;
    private UserNickNameGradeView userNickNameGradeView;

    /* renamed from: com.tomatolive.library.ui.view.custom.LiveEndInfoView$LiveEndClickListener */
    /* loaded from: classes3.dex */
    public interface LiveEndClickListener {
        void onAttentionClick(View view);

        void onGoHomeClick();

        void onNavBackClick();
    }

    public LiveEndInfoView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        int i = 0;
        setPadding(0, (int) SystemUtils.dp2px(26.0f), 0, 0);
        setBackground(new ColorDrawable(Color.parseColor("#B3000000")));
        RelativeLayout.inflate(this.mContext, R$layout.fq_live_end_cover, this);
        this.mHeadImg = (ImageView) findViewById(R$id.iv_anchor_head);
        this.userNickNameGradeView = (UserNickNameGradeView) findViewById(R$id.user_nickname);
        this.mTvId = (TextView) findViewById(R$id.tv_anchor_id);
        this.mTvAttention = (TextView) findViewById(R$id.tv_attention_anchor);
        this.mTvLastEndTime = (TextView) findViewById(R$id.tv_live_end_tips);
        this.mTvWatchNum = (TextView) findViewById(R$id.tv_personal);
        this.mTvLiveTime = (TextView) findViewById(R$id.tv_time);
        this.tvLivePre = (TextView) findViewById(R$id.tv_live_pre);
        this.tvHomepage = (TextView) findViewById(R$id.tv_homepage);
        this.mTvAttention.setOnClickListener(this);
        this.tvHomepage.setOnClickListener(this);
        findViewById(R$id.tv_home).setOnClickListener(this);
        findViewById(R$id.iv_end_back).setOnClickListener(this);
        TextView textView = this.tvHomepage;
        if (!SysConfigInfoManager.getInstance().isEnableAnchorHomepage()) {
            i = 8;
        }
        textView.setVisibility(i);
    }

    public void initData(LiveEndEntity liveEndEntity) {
        Context context;
        int i;
        if (liveEndEntity == null) {
            return;
        }
        this.anchorEntity = new AnchorEntity();
        AnchorEntity anchorEntity = this.anchorEntity;
        anchorEntity.userId = liveEndEntity.userId;
        anchorEntity.avatar = liveEndEntity.avatar;
        anchorEntity.nickname = liveEndEntity.nickname;
        anchorEntity.sex = liveEndEntity.sex;
        anchorEntity.expGrade = liveEndEntity.expGrade;
        anchorEntity.role = liveEndEntity.role;
        anchorEntity.guardType = liveEndEntity.guardType;
        anchorEntity.nobilityType = liveEndEntity.nobilityType;
        anchorEntity.openId = liveEndEntity.openId;
        anchorEntity.appId = liveEndEntity.appId;
        this.userNickNameGradeView.initAnchorData(liveEndEntity.nickname, R$color.fq_colorWhite, liveEndEntity.sex, liveEndEntity.expGrade, liveEndEntity.nobilityType);
        setHeadImg(liveEndEntity.avatar);
        setTvLastEndTime(liveEndEntity.startTime);
        setTvWatchNum(TextUtils.isEmpty(liveEndEntity.maxPopularity) ? "0" : AppUtils.formatOnlineUserCount(liveEndEntity.maxPopularity));
        setTvLiveTime(liveEndEntity.startTime, liveEndEntity.endTime);
        setTvId(liveEndEntity.liveId);
        TextView textView = this.mTvAttention;
        if (liveEndEntity.isAttention()) {
            context = this.mContext;
            i = R$string.fq_home_btn_attention_yes;
        } else {
            context = this.mContext;
            i = R$string.fq_home_attention;
        }
        textView.setText(context.getString(i));
        this.mTvAttention.setSelected(liveEndEntity.isAttention());
        if (!TextUtils.isEmpty(liveEndEntity.herald)) {
            this.tvLivePre.setVisibility(0);
            this.tvLivePre.setText(this.mContext.getString(R$string.fq_anchor_live_end_pre_notice_tips, DateUtils.getShortTime(NumberUtils.string2long(liveEndEntity.publishTime) * 1000), liveEndEntity.herald));
            return;
        }
        this.tvLivePre.setVisibility(8);
    }

    private void setTvId(String str) {
        this.mTvId.setText(this.mContext.getString(R$string.fq_live_room_id, str));
    }

    private void setTvLastEndTime(String str) {
        this.mTvLastEndTime.setText(this.mContext.getString(R$string.fq_last_live_state_time, DateUtils.getStartLiveTime(str)));
    }

    private void setTvWatchNum(String str) {
        this.mTvWatchNum.setText(str);
    }

    private void setTvLiveTime(String str, String str2) {
        long string2long = NumberUtils.string2long(str2) - NumberUtils.string2long(str);
        String stringForTime = DateUtils.stringForTime(1000 * string2long);
        if (string2long < 0) {
            stringForTime = "00:00";
        }
        this.mTvLiveTime.setText(stringForTime);
    }

    public void setTvAttentionText(boolean z) {
        this.mTvAttention.setText(this.mContext.getString(z ? R$string.fq_home_btn_attention_yes : R$string.fq_home_attention));
        this.mTvAttention.setSelected(z);
    }

    private void setHeadImg(String str) {
        Context context = this.mContext;
        GlideUtils.loadAvatar(context, this.mHeadImg, str, 6, ContextCompat.getColor(context, R$color.fq_colorWhite));
    }

    public void setLiveEndClickListener(LiveEndClickListener liveEndClickListener) {
        this.mLiveEndClickListener = liveEndClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        LiveEndClickListener liveEndClickListener;
        LiveEndClickListener liveEndClickListener2;
        LiveEndClickListener liveEndClickListener3;
        int id = view.getId();
        if (id == R$id.tv_home && (liveEndClickListener3 = this.mLiveEndClickListener) != null) {
            liveEndClickListener3.onNavBackClick();
        }
        if (id == R$id.iv_end_back && (liveEndClickListener2 = this.mLiveEndClickListener) != null) {
            liveEndClickListener2.onGoHomeClick();
        }
        if (id == R$id.tv_attention_anchor && (liveEndClickListener = this.mLiveEndClickListener) != null) {
            liveEndClickListener.onAttentionClick(view);
        }
        if (id == R$id.tv_homepage) {
            AppUtils.onUserHomepageListener(this.mContext, this.anchorEntity);
        }
    }
}
