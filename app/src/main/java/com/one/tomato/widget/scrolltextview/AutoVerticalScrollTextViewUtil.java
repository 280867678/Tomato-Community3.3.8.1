package com.one.tomato.widget.scrolltextview;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class AutoVerticalScrollTextViewUtil {
    private ExecutorService executorService;
    private boolean isRunning;
    private ArrayList<CharSequence> mDatas;
    private OnMyClickListener onMyClickListener;
    private AutoVerticalScrollTextView textView;
    private CharSequence title;
    private long duration = 1000;
    private MyHandler handler = new MyHandler(this);
    private int number = 0;
    private int currentPos = 0;

    /* loaded from: classes3.dex */
    public interface OnMyClickListener {
        void onMyClickListener(int i, CharSequence charSequence);
    }

    static /* synthetic */ int access$508(AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil) {
        int i = autoVerticalScrollTextViewUtil.number;
        autoVerticalScrollTextViewUtil.number = i + 1;
        return i;
    }

    public AutoVerticalScrollTextViewUtil(AutoVerticalScrollTextView autoVerticalScrollTextView, ArrayList<CharSequence> arrayList) {
        this.mDatas = new ArrayList<>();
        this.mDatas = arrayList;
        this.textView = autoVerticalScrollTextView;
        this.textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.widget.scrolltextview.AutoVerticalScrollTextViewUtil.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ZTextViewClickUtil.isFastClick() || AutoVerticalScrollTextViewUtil.this.onMyClickListener == null) {
                    return;
                }
                AutoVerticalScrollTextViewUtil.this.onMyClickListener.onMyClickListener(AutoVerticalScrollTextViewUtil.this.currentPos, AutoVerticalScrollTextViewUtil.this.title);
            }
        });
    }

    public AutoVerticalScrollTextViewUtil setDuration(long j) {
        this.duration = j;
        return this;
    }

    public void start() {
        this.isRunning = true;
        startThread();
    }

    public void stop() {
        this.isRunning = false;
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    private void startThread() {
        Thread thread = new Thread(new Runnable() { // from class: com.one.tomato.widget.scrolltextview.AutoVerticalScrollTextViewUtil.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (AutoVerticalScrollTextViewUtil.this.mDatas == null || AutoVerticalScrollTextViewUtil.this.mDatas.size() <= 0) {
                        AutoVerticalScrollTextViewUtil.this.isRunning = false;
                        return;
                    }
                    while (AutoVerticalScrollTextViewUtil.this.isRunning) {
                        AutoVerticalScrollTextViewUtil.this.currentPos = AutoVerticalScrollTextViewUtil.this.number % AutoVerticalScrollTextViewUtil.this.mDatas.size();
                        AutoVerticalScrollTextViewUtil.this.title = (CharSequence) AutoVerticalScrollTextViewUtil.this.mDatas.get(AutoVerticalScrollTextViewUtil.this.currentPos);
                        AutoVerticalScrollTextViewUtil.access$508(AutoVerticalScrollTextViewUtil.this);
                        AutoVerticalScrollTextViewUtil.this.handler.sendEmptyMessage(200);
                        Thread.sleep(AutoVerticalScrollTextViewUtil.this.duration);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ExecutorService executorService = this.executorService;
        if (executorService == null || executorService.isShutdown()) {
            this.executorService = Executors.newSingleThreadScheduledExecutor();
        }
        this.executorService.execute(thread);
    }

    public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
        this.onMyClickListener = onMyClickListener;
    }

    /* loaded from: classes3.dex */
    private static class MyHandler extends Handler {
        private final WeakReference<AutoVerticalScrollTextViewUtil> mUtil;

        MyHandler(AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil) {
            this.mUtil = new WeakReference<>(autoVerticalScrollTextViewUtil);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AutoVerticalScrollTextViewUtil autoVerticalScrollTextViewUtil = this.mUtil.get();
            if (message.what == 200) {
                autoVerticalScrollTextViewUtil.textView.next();
                if (TextUtils.isEmpty(autoVerticalScrollTextViewUtil.title)) {
                    return;
                }
                autoVerticalScrollTextViewUtil.textView.setText(autoVerticalScrollTextViewUtil.title);
            }
        }
    }
}
