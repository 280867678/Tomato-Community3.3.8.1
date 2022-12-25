package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$string;

/* renamed from: com.gen.mh.webapp_extensions.views.player.VideoScaleView */
/* loaded from: classes2.dex */
public class VideoScaleView extends BasePlayerDialogView {
    public static int videoPlaySaleType = 1;
    ImageView ivVideoScaleFour;
    ImageView ivVideoScaleOne;
    ImageView ivVideoScaleThree;
    ImageView ivVideoScaleTwo;
    TextView tvVideoScaleBitFour;
    TextView tvVideoScaleBitOne;
    TextView tvVideoScaleBitThree;
    TextView tvVideoScaleBitTwo;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.VideoScaleView$VideoScalePlayCallback */
    /* loaded from: classes2.dex */
    public interface VideoScalePlayCallback extends PlayerDialogCallback {
        void scaleChange(int i, int i2, String str);
    }

    public VideoScaleView(Context context) {
        super(context);
    }

    public static String provideSelectName(Context context) {
        return getVideoScaledPlay(context, videoPlaySaleType).scaleSign;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.web_sdk_video_scale_play;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.tvVideoScaleBitOne = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_scale_bit_one);
        this.tvVideoScaleBitTwo = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_scale_bit_two);
        this.tvVideoScaleBitThree = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_scale_bit_three);
        this.tvVideoScaleBitFour = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_scale_bit_four);
        this.ivVideoScaleOne = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_scale_bit_one);
        this.ivVideoScaleTwo = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_scale_bit_two);
        this.ivVideoScaleThree = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_scale_bit_three);
        this.ivVideoScaleFour = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_scale_bit_four);
        this.tvVideoScaleBitOne.setOnClickListener(this);
        this.tvVideoScaleBitTwo.setOnClickListener(this);
        this.tvVideoScaleBitThree.setOnClickListener(this);
        this.tvVideoScaleBitFour.setOnClickListener(this);
        setPlayScale(videoPlaySaleType, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initData() {
        super.initData();
        setPlayScale(videoPlaySaleType, false);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.tv_sdk_video_scale_bit_one) {
            setPlayScale(1, true);
        } else if (id == R$id.tv_sdk_video_scale_bit_two) {
            setPlayScale(2, true);
        } else if (id == R$id.tv_sdk_video_scale_bit_three) {
            setPlayScale(3, true);
        } else if (id != R$id.tv_sdk_video_scale_bit_four) {
        } else {
            setPlayScale(4, true);
        }
    }

    public void setPlayScale(int i, boolean z) {
        PlayerDialogCallback playerDialogCallback;
        resolveNormalUI(this.tvVideoScaleBitOne);
        resolveNormalUI(this.tvVideoScaleBitTwo);
        resolveNormalUI(this.tvVideoScaleBitThree);
        resolveNormalUI(this.tvVideoScaleBitFour);
        resolveNormalUI(this.ivVideoScaleOne);
        resolveNormalUI(this.ivVideoScaleTwo);
        resolveNormalUI(this.ivVideoScaleThree);
        resolveNormalUI(this.ivVideoScaleFour);
        videoPlaySaleType = i;
        VideoScalePlay videoScaledPlay = getVideoScaledPlay(this.mContext, i);
        if (i == 1) {
            checkScaleUI(this.tvVideoScaleBitOne);
            checkScaleUI(this.ivVideoScaleOne);
        } else if (i == 2) {
            checkScaleUI(this.tvVideoScaleBitTwo);
            checkScaleUI(this.ivVideoScaleTwo);
        } else if (i == 3) {
            checkScaleUI(this.tvVideoScaleBitThree);
            checkScaleUI(this.ivVideoScaleThree);
        } else if (i == 4) {
            checkScaleUI(this.tvVideoScaleBitFour);
            checkScaleUI(this.ivVideoScaleFour);
        }
        if (videoScaledPlay != null && z && (playerDialogCallback = this.playerDialogCallback) != null && (playerDialogCallback instanceof VideoScalePlayCallback)) {
            ((VideoScalePlayCallback) playerDialogCallback).scaleChange(videoScaledPlay.videoScaleType, videoScaledPlay.checkScaleType, videoScaledPlay.scaleSign);
            Toast.makeText(this.contentView.getContext(), videoScaledPlay.scaleTip, 0).show();
        }
    }

    private void resolveNormalUI(TextView textView) {
        textView.setTextColor(Color.parseColor("#333333"));
    }

    public void resolveNormalUI(ImageView imageView) {
        imageView.setVisibility(4);
    }

    private void checkScaleUI(TextView textView) {
        textView.setTextColor(Color.parseColor("#333333"));
    }

    private void checkScaleUI(ImageView imageView) {
        imageView.setVisibility(0);
    }

    public static VideoScalePlay getVideoScaledPlay(Context context, int i) {
        if (i != 1) {
            if (i == 2) {
                return new VideoScalePlay(1, context.getString(R$string.aspect_fit), 1, context.getString(R$string.aspect_fit));
            }
            if (i == 3) {
                return new VideoScalePlay(-4, context.getString(R$string.aspect_fill), 1, context.getString(R$string.aspect_fill));
            }
            if (i == 4) {
                return new VideoScalePlay(4, context.getString(R$string.fill), 1, context.getString(R$string.fill));
            }
            return new VideoScalePlay(0, context.getString(R$string.no_scale), 1, context.getString(R$string.no_scale));
        }
        return new VideoScalePlay(0, context.getString(R$string.no_scale), 1, context.getString(R$string.no_scale));
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.player.VideoScaleView$VideoScalePlay */
    /* loaded from: classes2.dex */
    public static class VideoScalePlay {
        public int checkScaleType;
        public String scaleSign;
        public String scaleTip;
        public int videoScaleType;

        public VideoScalePlay(int i, String str, int i2, String str2) {
            this.videoScaleType = i;
            this.scaleSign = str;
            this.checkScaleType = i2;
            this.scaleTip = str2;
        }
    }
}
