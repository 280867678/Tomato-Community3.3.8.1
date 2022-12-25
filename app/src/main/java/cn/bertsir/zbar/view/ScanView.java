package cn.bertsir.zbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import cn.bertsir.zbar.p036Qr.Symbol;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ScanView extends FrameLayout {
    private TranslateAnimation animation;
    private CornerView cnv_left_bottom;
    private CornerView cnv_left_top;
    private CornerView cnv_right_bottom;
    private CornerView cnv_right_top;
    private ArrayList<CornerView> cornerViews;
    private FrameLayout fl_scan;
    private LineView iv_scan_line;
    private int CURRENT_TYEP = 1;
    private int line_speed = 3000;

    public ScanView(Context context) {
        super(context);
        initView(context);
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public ScanView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R$layout.web_sdk_view_scan, this);
        this.cnv_left_top = (CornerView) inflate.findViewById(R$id.cnv_left_top);
        this.cnv_left_bottom = (CornerView) inflate.findViewById(R$id.cnv_left_bottom);
        this.cnv_right_top = (CornerView) inflate.findViewById(R$id.cnv_right_top);
        this.cnv_right_bottom = (CornerView) inflate.findViewById(R$id.cnv_right_bottom);
        this.cornerViews = new ArrayList<>();
        this.cornerViews.add(this.cnv_left_top);
        this.cornerViews.add(this.cnv_left_bottom);
        this.cornerViews.add(this.cnv_right_top);
        this.cornerViews.add(this.cnv_right_bottom);
        this.iv_scan_line = (LineView) inflate.findViewById(R$id.iv_scan_line);
        this.fl_scan = (FrameLayout) inflate.findViewById(R$id.fl_scan);
        this.animation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 0.9f);
        this.animation.setDuration(this.line_speed);
        this.animation.setRepeatCount(-1);
        this.animation.setRepeatMode(1);
    }

    public void setLineSpeed(int i) {
        this.animation.setDuration(i);
    }

    public void startScan() {
        this.iv_scan_line.startAnimation(this.animation);
        getViewWidthHeight();
    }

    public void onPause() {
        LineView lineView = this.iv_scan_line;
        if (lineView != null) {
            lineView.clearAnimation();
            this.iv_scan_line.setVisibility(8);
        }
    }

    public void onResume() {
        LineView lineView = this.iv_scan_line;
        if (lineView != null) {
            lineView.startAnimation(this.animation);
        }
    }

    public void setType(int i) {
        this.CURRENT_TYEP = i;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.fl_scan.getLayoutParams();
        int i2 = this.CURRENT_TYEP;
        if (i2 == 1) {
            layoutParams.width = dip2px(200);
            layoutParams.height = dip2px(200);
        } else if (i2 == 2) {
            layoutParams.width = dip2px(300);
            layoutParams.height = dip2px(100);
        }
        this.fl_scan.setLayoutParams(layoutParams);
    }

    public void setCornerColor(int i) {
        for (int i2 = 0; i2 < this.cornerViews.size(); i2++) {
            this.cornerViews.get(i2).setColor(i);
        }
    }

    public void setCornerWidth(int i) {
        for (int i2 = 0; i2 < this.cornerViews.size(); i2++) {
            this.cornerViews.get(i2).setLineWidth(i);
        }
    }

    public void setLineColor(int i) {
        this.iv_scan_line.setLinecolor(i);
    }

    public int dip2px(int i) {
        return (int) ((i * getContext().getResources().getDisplayMetrics().density) + 0.5d);
    }

    public void getViewWidthHeight() {
        this.fl_scan.post(new Runnable() { // from class: cn.bertsir.zbar.view.ScanView.1
            @Override // java.lang.Runnable
            public void run() {
                Symbol.cropWidth = ScanView.this.fl_scan.getWidth();
                Symbol.cropHeight = ScanView.this.fl_scan.getHeight();
            }
        });
    }
}
