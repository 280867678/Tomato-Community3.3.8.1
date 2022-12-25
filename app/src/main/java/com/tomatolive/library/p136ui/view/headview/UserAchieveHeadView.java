package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.view.headview.UserAchieveHeadView */
/* loaded from: classes3.dex */
public class UserAchieveHeadView extends LinearLayout {
    private ImageView ivAvatar;
    private LinearLayout llTagBg;
    private OnAchieveTagClickListener onAchieveTagClickListener;
    private TextView tvAchieveCount;
    private TextView tvAnchorAchieve;
    private TextView tvAnchorNickname;
    private TextView tvUserAchieve;

    /* renamed from: com.tomatolive.library.ui.view.headview.UserAchieveHeadView$OnAchieveTagClickListener */
    /* loaded from: classes3.dex */
    public interface OnAchieveTagClickListener {
        void onAnchorAchieveListener();

        void onUserAchieveListener();
    }

    public UserAchieveHeadView(Context context) {
        super(context);
        initView(context);
    }

    public UserAchieveHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_achieve_user, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.tvAnchorNickname = (TextView) findViewById(R$id.tv_anchor_nickname);
        this.tvAchieveCount = (TextView) findViewById(R$id.tv_achieve_count);
        this.tvAnchorAchieve = (TextView) findViewById(R$id.tv_anchor_achieve);
        this.tvUserAchieve = (TextView) findViewById(R$id.tv_user_achieve);
        this.llTagBg = (LinearLayout) findViewById(R$id.ll_tag_bg);
        this.tvAnchorAchieve.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$UserAchieveHeadView$a86qKG3IazTnlNJAX7uNpnhGxFQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserAchieveHeadView.this.lambda$initView$0$UserAchieveHeadView(view);
            }
        });
        this.tvUserAchieve.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$UserAchieveHeadView$sFdtWJRNgrSUKrMGuyyHhCLgjsY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserAchieveHeadView.this.lambda$initView$1$UserAchieveHeadView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$UserAchieveHeadView(View view) {
        if (this.onAchieveTagClickListener != null) {
            this.tvAnchorAchieve.setSelected(true);
            this.tvUserAchieve.setSelected(false);
            updateTagTextColor();
            this.onAchieveTagClickListener.onAnchorAchieveListener();
        }
    }

    public /* synthetic */ void lambda$initView$1$UserAchieveHeadView(View view) {
        if (this.onAchieveTagClickListener != null) {
            this.tvUserAchieve.setSelected(true);
            this.tvAnchorAchieve.setSelected(false);
            updateTagTextColor();
            this.onAchieveTagClickListener.onUserAchieveListener();
        }
    }

    public void initData(boolean z, String str, String str2, String str3) {
        this.llTagBg.setVisibility(z ? 0 : 8);
        this.tvAnchorNickname.setText(str);
        this.tvAchieveCount.setText(Html.fromHtml(getContext().getString(R$string.fq_achieve_already_get_achieve_count, str3)));
        this.tvAnchorAchieve.setSelected(z);
        this.tvUserAchieve.setSelected(!z);
        GlideUtils.loadAvatar(getContext(), this.ivAvatar, str2, 6, ContextCompat.getColor(getContext(), R$color.fq_colorWhite));
        updateTagTextColor();
    }

    public void setOnAchieveTagClickListener(OnAchieveTagClickListener onAchieveTagClickListener) {
        this.onAchieveTagClickListener = onAchieveTagClickListener;
    }

    private void updateTagTextColor() {
        this.tvAnchorAchieve.setTextColor(ContextCompat.getColor(getContext(), this.tvAnchorAchieve.isSelected() ? R$color.fq_colorWhite : R$color.fq_colorWhiteTransparent_99));
        this.tvUserAchieve.setTextColor(ContextCompat.getColor(getContext(), this.tvUserAchieve.isSelected() ? R$color.fq_colorWhite : R$color.fq_colorWhiteTransparent_99));
    }
}
