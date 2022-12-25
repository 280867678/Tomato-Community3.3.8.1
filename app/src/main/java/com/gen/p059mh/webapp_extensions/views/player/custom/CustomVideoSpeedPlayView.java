package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.common.use.util.ToastUtils;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView;
import com.gen.p059mh.webapp_extensions.views.player.PlayerDialogCallback;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSpeedPlayView */
/* loaded from: classes2.dex */
public class CustomVideoSpeedPlayView extends BasePlayerDialogView {
    public static int videoPlaySpeedType = 2;
    TextView tvVideoSpeedBitFour;
    TextView tvVideoSpeedBitOne;
    TextView tvVideoSpeedBitThree;
    TextView tvVideoSpeedBitTwo;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSpeedPlayView$VideoSpeedPlayCallback */
    /* loaded from: classes2.dex */
    public interface VideoSpeedPlayCallback extends PlayerDialogCallback {
        void speedPlay(float f, float f2, String str, int i);
    }

    public CustomVideoSpeedPlayView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.custom_view_video_speed_play;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.tvVideoSpeedBitOne = (TextView) this.contentView.findViewById(R$id.tv_video_speed_bit_one);
        this.tvVideoSpeedBitTwo = (TextView) this.contentView.findViewById(R$id.tv_video_speed_bit_two);
        this.tvVideoSpeedBitThree = (TextView) this.contentView.findViewById(R$id.tv_video_speed_bit_three);
        this.tvVideoSpeedBitFour = (TextView) this.contentView.findViewById(R$id.tv_video_speed_bit_four);
        this.tvVideoSpeedBitOne.setOnClickListener(this);
        this.tvVideoSpeedBitTwo.setOnClickListener(this);
        this.tvVideoSpeedBitThree.setOnClickListener(this);
        this.tvVideoSpeedBitFour.setOnClickListener(this);
        setPlaySpeed(videoPlaySpeedType, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initData() {
        super.initData();
        setPlaySpeed(videoPlaySpeedType, false);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.tv_video_speed_bit_one) {
            setPlaySpeed(1, true);
        } else if (id == R$id.tv_video_speed_bit_two) {
            setPlaySpeed(2, true);
        } else if (id == R$id.tv_video_speed_bit_three) {
            setPlaySpeed(3, true);
        } else if (id != R$id.tv_video_speed_bit_four) {
        } else {
            setPlaySpeed(4, true);
        }
    }

    public void setPlaySpeed(int i, boolean z) {
        PlayerDialogCallback playerDialogCallback;
        resolveNormalUI(this.tvVideoSpeedBitOne);
        resolveNormalUI(this.tvVideoSpeedBitTwo);
        resolveNormalUI(this.tvVideoSpeedBitThree);
        resolveNormalUI(this.tvVideoSpeedBitFour);
        videoPlaySpeedType = i;
        VideoSpeedPlay videoSpeedPlay = getVideoSpeedPlay(i);
        if (i == 1) {
            checkSpeedUI(this.tvVideoSpeedBitOne);
        } else if (i == 2) {
            checkSpeedUI(this.tvVideoSpeedBitTwo);
        } else if (i == 3) {
            checkSpeedUI(this.tvVideoSpeedBitThree);
        } else if (i == 4) {
            checkSpeedUI(this.tvVideoSpeedBitFour);
        }
        if (videoSpeedPlay != null && z && (playerDialogCallback = this.playerDialogCallback) != null && (playerDialogCallback instanceof VideoSpeedPlayCallback)) {
            ((VideoSpeedPlayCallback) playerDialogCallback).speedPlay(videoSpeedPlay.videoSpeed, videoSpeedPlay.danmuSpeed, videoSpeedPlay.speedSign, videoSpeedPlay.checkSpeedType);
            ToastUtils.showShort(videoSpeedPlay.speedTip);
        }
    }

    private void resolveNormalUI(TextView textView) {
        textView.setTextColor(this.mContext.getResources().getColor(R$color.white));
    }

    private void checkSpeedUI(TextView textView) {
        textView.setTextColor(this.mContext.getResources().getColor(R$color.yellow));
    }

    public VideoSpeedPlay getVideoSpeedPlay(int i) {
        if (i != 1) {
            if (i == 2) {
                return new VideoSpeedPlay(this, 1.0f, 1.0f, "倍速", 2, "正在以1.0x倍数播放");
            }
            if (i == 3) {
                return new VideoSpeedPlay(this, 1.5f, 0.8f, "1.5x", 3, "正在以1.5x倍数播放");
            }
            if (i == 4) {
                return new VideoSpeedPlay(this, 2.0f, 0.6f, "2.0x", 4, "正在以2.0x倍数播放");
            }
            return new VideoSpeedPlay(this, 1.0f, 1.0f, "倍速", 1, "正在以1.0x倍速播放");
        }
        return new VideoSpeedPlay(this, 0.5f, 1.2f, "0.5x", 1, "正在以0.5x倍速播放");
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSpeedPlayView$VideoSpeedPlay */
    /* loaded from: classes2.dex */
    public class VideoSpeedPlay {
        public int checkSpeedType;
        public float danmuSpeed;
        public String speedSign;
        public String speedTip;
        public float videoSpeed;

        public VideoSpeedPlay(CustomVideoSpeedPlayView customVideoSpeedPlayView, float f, float f2, String str, int i, String str2) {
            this.videoSpeed = f;
            this.danmuSpeed = f2;
            this.speedSign = str;
            this.speedTip = str2;
            this.checkSpeedType = i;
        }
    }
}
