package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;

/* renamed from: com.gen.mh.webapp_extensions.views.player.VideoSpeedPlayView */
/* loaded from: classes2.dex */
public class VideoSpeedPlayView extends BasePlayerDialogView {
    public static int videoPlaySpeedType = 1;
    ImageView ivVideoSpeedFour;
    ImageView ivVideoSpeedOne;
    ImageView ivVideoSpeedThree;
    ImageView ivVideoSpeedTwo;
    TextView tvVideoSpeedBitFour;
    TextView tvVideoSpeedBitOne;
    TextView tvVideoSpeedBitThree;
    TextView tvVideoSpeedBitTwo;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.VideoSpeedPlayView$VideoSpeedPlayCallback */
    /* loaded from: classes2.dex */
    public interface VideoSpeedPlayCallback extends PlayerDialogCallback {
        void speedPlay(float f, float f2, String str);
    }

    public VideoSpeedPlayView(Context context) {
        super(context);
    }

    public static String provideSelectName() {
        return getVideoSpeedPlay(videoPlaySpeedType).speedSign;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.web_sdk_video_speed_play;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        this.tvVideoSpeedBitOne = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_speed_bit_one);
        this.tvVideoSpeedBitTwo = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_speed_bit_two);
        this.tvVideoSpeedBitThree = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_speed_bit_three);
        this.tvVideoSpeedBitFour = (TextView) this.contentView.findViewById(R$id.tv_sdk_video_speed_bit_four);
        this.ivVideoSpeedOne = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_speed_bit_one);
        this.ivVideoSpeedTwo = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_speed_bit_two);
        this.ivVideoSpeedThree = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_speed_bit_three);
        this.ivVideoSpeedFour = (ImageView) this.contentView.findViewById(R$id.iv_sdk_video_speed_bit_four);
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
        if (id == R$id.tv_sdk_video_speed_bit_one) {
            setPlaySpeed(1, true);
        } else if (id == R$id.tv_sdk_video_speed_bit_two) {
            setPlaySpeed(2, true);
        } else if (id == R$id.tv_sdk_video_speed_bit_three) {
            setPlaySpeed(3, true);
        } else if (id != R$id.tv_sdk_video_speed_bit_four) {
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
        resolveNormalUI(this.ivVideoSpeedOne);
        resolveNormalUI(this.ivVideoSpeedTwo);
        resolveNormalUI(this.ivVideoSpeedThree);
        resolveNormalUI(this.ivVideoSpeedFour);
        videoPlaySpeedType = i;
        VideoSpeedPlay videoSpeedPlay = getVideoSpeedPlay(i);
        if (i == 1) {
            checkSpeedUI(this.tvVideoSpeedBitOne);
            checkSpeedUI(this.ivVideoSpeedOne);
        } else if (i == 2) {
            checkSpeedUI(this.tvVideoSpeedBitTwo);
            checkSpeedUI(this.ivVideoSpeedTwo);
        } else if (i == 3) {
            checkSpeedUI(this.tvVideoSpeedBitThree);
            checkSpeedUI(this.ivVideoSpeedThree);
        } else if (i == 4) {
            checkSpeedUI(this.tvVideoSpeedBitFour);
            checkSpeedUI(this.ivVideoSpeedFour);
        }
        if (videoSpeedPlay != null && z && (playerDialogCallback = this.playerDialogCallback) != null && (playerDialogCallback instanceof VideoSpeedPlayCallback)) {
            ((VideoSpeedPlayCallback) playerDialogCallback).speedPlay(videoSpeedPlay.videoSpeed, videoSpeedPlay.danmuSpeed, videoSpeedPlay.speedSign);
            Toast.makeText(this.contentView.getContext(), videoSpeedPlay.speedTip, 0).show();
        }
    }

    private void resolveNormalUI(TextView textView) {
        textView.setTextColor(Color.parseColor("#333333"));
    }

    public void resolveNormalUI(ImageView imageView) {
        imageView.setVisibility(4);
    }

    private void checkSpeedUI(TextView textView) {
        textView.setTextColor(Color.parseColor("#333333"));
    }

    private void checkSpeedUI(ImageView imageView) {
        imageView.setVisibility(0);
    }

    public static VideoSpeedPlay getVideoSpeedPlay(int i) {
        if (i != 1) {
            if (i == 2) {
                return new VideoSpeedPlay(1.25f, 0.9f, "1.25倍", 2, "正在以1.25倍数播放");
            }
            if (i == 3) {
                return new VideoSpeedPlay(1.5f, 0.8f, "1.5倍", 3, "正在以1.5倍数播放");
            }
            if (i == 4) {
                return new VideoSpeedPlay(2.0f, 0.6f, "2.0倍", 4, "正在以2.0倍数播放");
            }
            return new VideoSpeedPlay(1.0f, 1.0f, "倍速", 1, "正在以正常速度播放");
        }
        return new VideoSpeedPlay(1.0f, 1.0f, "1.0倍", 1, "正在以正常速度播放");
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.player.VideoSpeedPlayView$VideoSpeedPlay */
    /* loaded from: classes2.dex */
    public static class VideoSpeedPlay {
        public float danmuSpeed;
        public String speedSign;
        public String speedTip;
        public float videoSpeed;

        public VideoSpeedPlay(float f, float f2, String str, int i, String str2) {
            this.videoSpeed = f;
            this.danmuSpeed = f2;
            this.speedSign = str;
            this.speedTip = str2;
        }
    }
}
