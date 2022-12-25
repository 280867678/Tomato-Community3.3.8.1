package com.tomatolive.library.p136ui.view.widget.bgabanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.p002v4.view.PagerAdapter;
import android.support.p002v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.p136ui.view.custom.HdLotteryWindowView;
import com.tomatolive.library.p136ui.view.widget.QMNoticeWindow;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils */
/* loaded from: classes4.dex */
public class TopBannerUtils implements BGAViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {
    private static final int VEL_THRESHOLD = 400;
    private QMInteractTaskEntity curTaskEntity;
    private HdLotteryWindowView hdLotteryWindowView;
    private InteractWindowListener listener;
    private AutoPlayTask mAutoPlayTask;
    private Context mContext;
    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;
    private QMNoticeWindow qmNoticeWindow;

    /* renamed from: vp */
    private BGAViewPager f5868vp;
    private List<View> views = new ArrayList();
    private int mAutoPlayInterval = 5000;

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils$InteractWindowListener */
    /* loaded from: classes4.dex */
    public interface InteractWindowListener {
        void onClick(View view);

        void onDelete(View view);
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
    }

    public TopBannerUtils(BGAViewPager bGAViewPager, Context context) {
        this.mContext = context;
        this.f5868vp = bGAViewPager;
        initVp();
    }

    public void setListener(InteractWindowListener interactWindowListener) {
        this.listener = interactWindowListener;
    }

    private void initVp() {
        this.f5868vp.setPageChangeDuration(800);
        this.f5868vp.addOnPageChangeListener(this);
        this.f5868vp.setAutoPlayDelegate(this);
        this.f5868vp.setAdapter(new MyAdapter(this.views));
        this.mAutoPlayTask = new AutoPlayTask();
    }

    public void addHdLotteryWindowView(boolean z, String str, long j) {
        HdLotteryWindowView hdLotteryWindowView = this.hdLotteryWindowView;
        if (hdLotteryWindowView != null) {
            hdLotteryWindowView.initDrawInfo(z, j);
            return;
        }
        this.hdLotteryWindowView = new HdLotteryWindowView(this.mContext);
        this.hdLotteryWindowView.setOnHdLotteryCallback(this.listener);
        this.hdLotteryWindowView.initDrawInfo(z, j);
        this.hdLotteryWindowView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TopBannerUtils.this.listener != null) {
                    TopBannerUtils.this.listener.onClick(view);
                }
            }
        });
        this.views.add(this.hdLotteryWindowView);
        this.f5868vp.setAdapter(new MyAdapter(this.views));
        checkPlay();
    }

    public void onDeleteHdLotteryWindowView() {
        HdLotteryWindowView hdLotteryWindowView = this.hdLotteryWindowView;
        if (hdLotteryWindowView != null) {
            this.views.remove(hdLotteryWindowView);
            this.hdLotteryWindowView = null;
            this.f5868vp.setAdapter(new MyAdapter(this.views));
            checkPlay();
        }
    }

    public boolean isEmpty() {
        List<View> list = this.views;
        return list == null || list.isEmpty();
    }

    private void checkPlay() {
        if (this.views.size() > 1) {
            startPlay();
            this.f5868vp.setCurrentItem(this.views.size() - 1);
        } else if (this.views.size() == 0) {
            stopPlay();
        } else {
            stopPlay();
            this.f5868vp.setCurrentItem(0);
        }
    }

    public void addQMNoticeWindow(QMInteractTaskEntity qMInteractTaskEntity) {
        this.curTaskEntity = qMInteractTaskEntity;
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.show(this.curTaskEntity);
            return;
        }
        this.qmNoticeWindow = new QMNoticeWindow(this.mContext);
        this.qmNoticeWindow.show(this.curTaskEntity);
        this.qmNoticeWindow.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TopBannerUtils.this.listener != null) {
                    TopBannerUtils.this.listener.onClick(view);
                }
            }
        });
        this.views.add(this.qmNoticeWindow);
        this.f5868vp.setAdapter(new MyAdapter(this.views));
        checkPlay();
    }

    public void showRedPoint() {
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.showRedPoint();
        }
    }

    public void hideRedPoint() {
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.hideRedPoint();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils$MyAdapter */
    /* loaded from: classes4.dex */
    public static class MyAdapter extends PagerAdapter {
        private List<View> views;

        @Override // android.support.p002v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getItemPosition(@NonNull Object obj) {
            return -2;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        public MyAdapter(List<View> list) {
            this.views = list;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getCount() {
            if (this.views.size() > 1) {
                return Integer.MAX_VALUE;
            }
            return this.views.size();
        }

        @Override // android.support.p002v4.view.PagerAdapter
        @NonNull
        /* renamed from: instantiateItem */
        public Object mo6346instantiateItem(@NonNull ViewGroup viewGroup, int i) {
            View view = this.views.get(i % this.views.size());
            ViewParent parent = view.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(view);
            }
            viewGroup.addView(view);
            return view;
        }
    }

    public void onReleaseData() {
        stopPlay();
        this.views.clear();
        this.f5868vp.setAdapter(null);
        HdLotteryWindowView hdLotteryWindowView = this.hdLotteryWindowView;
        if (hdLotteryWindowView != null) {
            hdLotteryWindowView.onReleaseData();
            this.hdLotteryWindowView = null;
        }
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.onRelease();
            this.curTaskEntity = null;
            this.qmNoticeWindow = null;
        }
    }

    public void onReleaseLotteryWindowView() {
        HdLotteryWindowView hdLotteryWindowView = this.hdLotteryWindowView;
        if (hdLotteryWindowView != null) {
            hdLotteryWindowView.onReleaseData();
            this.views.remove(this.hdLotteryWindowView);
            this.hdLotteryWindowView = null;
            this.f5868vp.setAdapter(new MyAdapter(this.views));
            checkPlay();
        }
    }

    public void onReleaseQMNoticeWindow() {
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.onRelease();
            this.views.remove(this.qmNoticeWindow);
            this.qmNoticeWindow = null;
            this.curTaskEntity = null;
            this.f5868vp.setAdapter(new MyAdapter(this.views));
            checkPlay();
        }
    }

    public void onLotteryEnd() {
        HdLotteryWindowView hdLotteryWindowView = this.hdLotteryWindowView;
        if (hdLotteryWindowView != null) {
            hdLotteryWindowView.onLotteryEnd();
        }
    }

    public void setProgressValue(int i) {
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        if (qMNoticeWindow != null) {
            qMNoticeWindow.setProgressValue(i);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager.AutoPlayDelegate
    public void handleAutoPlayActionUpOrCancel(float f) {
        BGAViewPager bGAViewPager = this.f5868vp;
        if (bGAViewPager != null) {
            if (this.mPageScrollPosition < bGAViewPager.getCurrentItem()) {
                if (f > 400.0f || (this.mPageScrollPositionOffset < 0.7f && f > -400.0f)) {
                    this.f5868vp.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
                } else {
                    this.f5868vp.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
                }
            } else if (this.mPageScrollPosition == this.f5868vp.getCurrentItem()) {
                if (f < -400.0f || (this.mPageScrollPositionOffset > 0.3f && f < 400.0f)) {
                    this.f5868vp.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
                } else {
                    this.f5868vp.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
                }
            } else {
                this.f5868vp.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager.AutoPlayDelegate
    public void onTouchListener(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        this.mPageScrollPosition = i;
        this.mPageScrollPositionOffset = f;
    }

    public boolean hasQMTaskView() {
        QMNoticeWindow qMNoticeWindow = this.qmNoticeWindow;
        return qMNoticeWindow != null && this.views.contains(qMNoticeWindow);
    }

    public QMInteractTaskEntity getCurTaskEntity() {
        return this.curTaskEntity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.TopBannerUtils$AutoPlayTask */
    /* loaded from: classes4.dex */
    public static class AutoPlayTask implements Runnable {
        private final WeakReference<TopBannerUtils> ref;

        private AutoPlayTask(TopBannerUtils topBannerUtils) {
            this.ref = new WeakReference<>(topBannerUtils);
        }

        @Override // java.lang.Runnable
        public void run() {
            TopBannerUtils topBannerUtils = this.ref.get();
            if (topBannerUtils != null) {
                topBannerUtils.setNextItem();
                topBannerUtils.startPlay();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPlay() {
        stopPlay();
        if (this.views.size() > 1) {
            this.f5868vp.postDelayed(this.mAutoPlayTask, this.mAutoPlayInterval);
        }
    }

    private void stopPlay() {
        AutoPlayTask autoPlayTask = this.mAutoPlayTask;
        if (autoPlayTask != null) {
            this.f5868vp.removeCallbacks(autoPlayTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNextItem() {
        BGAViewPager bGAViewPager = this.f5868vp;
        bGAViewPager.setCurrentItem(bGAViewPager.getCurrentItem() + 1);
    }
}
