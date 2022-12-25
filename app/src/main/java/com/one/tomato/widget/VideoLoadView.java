package com.one.tomato.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class VideoLoadView extends LinearLayout {
    private ProgressBar pb_left;
    private ProgressBar pb_right;
    private boolean sendMsg;
    private int maxProgress = 100;
    private int curProgress = 1;
    private Handler handler = new Handler() { // from class: com.one.tomato.widget.VideoLoadView.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (1 == message.what) {
                VideoLoadView.access$008(VideoLoadView.this);
                if (VideoLoadView.this.curProgress == VideoLoadView.this.maxProgress) {
                    VideoLoadView.this.curProgress = 1;
                }
                VideoLoadView.this.pb_left.setProgress(VideoLoadView.this.curProgress);
                VideoLoadView.this.pb_right.setProgress(VideoLoadView.this.curProgress);
                if (!VideoLoadView.this.sendMsg) {
                    return;
                }
                VideoLoadView.this.start();
            }
        }
    };

    static /* synthetic */ int access$008(VideoLoadView videoLoadView) {
        int i = videoLoadView.curProgress;
        videoLoadView.curProgress = i + 1;
        return i;
    }

    public VideoLoadView(Context context) {
        super(context);
        init(context);
    }

    public VideoLoadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public VideoLoadView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_video_loading, (ViewGroup) this, true);
        this.pb_left = (ProgressBar) inflate.findViewById(R.id.pb_left);
        this.pb_right = (ProgressBar) inflate.findViewById(R.id.pb_right);
        this.pb_left.setMax(this.maxProgress);
        this.pb_right.setMax(this.maxProgress);
    }

    public void start() {
        this.sendMsg = true;
        this.handler.sendEmptyMessageDelayed(1, 10L);
    }

    public void stop() {
        this.sendMsg = false;
        this.handler.removeMessages(1);
    }
}
