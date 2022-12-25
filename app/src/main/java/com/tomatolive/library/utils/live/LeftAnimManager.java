package com.tomatolive.library.utils.live;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import com.tomatolive.library.model.LeftAnimEntity;
import com.tomatolive.library.p136ui.interfaces.OnAnimPlayListener;
import com.tomatolive.library.p136ui.view.custom.GuardOpenDanmuView;
import com.tomatolive.library.p136ui.view.custom.LeftAnimView;
import com.tomatolive.library.p136ui.view.custom.NobilityEnterDanmuView;
import com.tomatolive.library.p136ui.view.custom.NobilityOpenDanmuView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes4.dex */
public class LeftAnimManager implements OnAnimPlayListener {
    private LinearLayout llAnimParent;
    private Context mContext;
    private int mAnimLayoutMaxNums = 2;
    private ConcurrentLinkedQueue<LeftAnimEntity> leftAnimMsgQueue = new ConcurrentLinkedQueue<>();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override // com.tomatolive.library.p136ui.interfaces.OnAnimPlayListener
    public void onStart() {
    }

    public void addLeftAnim(LeftAnimEntity leftAnimEntity) {
        synchronized (LeftAnimManager.class) {
            if (this.leftAnimMsgQueue == null) {
                this.leftAnimMsgQueue = new ConcurrentLinkedQueue<>();
            }
            if (this.leftAnimMsgQueue.size() == 9999) {
                this.leftAnimMsgQueue.poll();
            }
            this.leftAnimMsgQueue.offer(leftAnimEntity);
        }
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.utils.live.-$$Lambda$LeftAnimManager$v69hacWhxOQqsGHYcrwLiQVw1Ks
            @Override // java.lang.Runnable
            public final void run() {
                LeftAnimManager.this.lambda$addLeftAnim$0$LeftAnimManager();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getAnim */
    public void lambda$addLeftAnim$0$LeftAnimManager() {
        if (hasAnim()) {
            LeftAnimEntity poll = this.leftAnimMsgQueue.poll();
            int childCount = this.llAnimParent.getChildCount();
            LeftAnimView leftAnimView = null;
            if (childCount == 0) {
                switch (poll.leftAnimType) {
                    case ConstantUtils.LEFT_ANIM_OPEN_NOBILITY_TYPE /* 2308 */:
                        leftAnimView = new NobilityOpenDanmuView(this.mContext);
                        break;
                    case ConstantUtils.LEFT_ANIM_OPEN_GUARD_TYPE /* 2309 */:
                        leftAnimView = new GuardOpenDanmuView(this.mContext);
                        break;
                    case ConstantUtils.LEFT_ANIM_ENTER_NOBILITY_TYPE /* 2310 */:
                        leftAnimView = new NobilityEnterDanmuView(this.mContext);
                        break;
                }
                leftAnimView.setOnAnimPlayListener(this);
                this.llAnimParent.addView(leftAnimView);
                leftAnimView.addItemInfo(poll);
                return;
            }
            int i = 0;
            if (childCount < this.mAnimLayoutMaxNums) {
                switch (poll.leftAnimType) {
                    case ConstantUtils.LEFT_ANIM_OPEN_NOBILITY_TYPE /* 2308 */:
                        leftAnimView = new NobilityOpenDanmuView(this.mContext);
                        leftAnimView.setOnAnimPlayListener(this);
                        this.llAnimParent.addView(leftAnimView);
                        leftAnimView.addItemInfo(poll);
                        return;
                    case ConstantUtils.LEFT_ANIM_OPEN_GUARD_TYPE /* 2309 */:
                        leftAnimView = new GuardOpenDanmuView(this.mContext);
                        leftAnimView.setOnAnimPlayListener(this);
                        this.llAnimParent.addView(leftAnimView);
                        leftAnimView.addItemInfo(poll);
                        return;
                    case ConstantUtils.LEFT_ANIM_ENTER_NOBILITY_TYPE /* 2310 */:
                        while (i < childCount) {
                            LeftAnimView leftAnimView2 = (LeftAnimView) this.llAnimParent.getChildAt(i);
                            if (leftAnimView2.animType == 2310) {
                                if (poll.isLocalAnim()) {
                                    this.llAnimParent.removeView(leftAnimView2);
                                    NobilityEnterDanmuView nobilityEnterDanmuView = new NobilityEnterDanmuView(this.mContext);
                                    nobilityEnterDanmuView.setOnAnimPlayListener(this);
                                    this.llAnimParent.addView(nobilityEnterDanmuView);
                                    nobilityEnterDanmuView.addItemInfo(poll);
                                    return;
                                }
                                this.leftAnimMsgQueue.offer(poll);
                                return;
                            }
                            i++;
                        }
                        leftAnimView = new NobilityEnterDanmuView(this.mContext);
                        leftAnimView.setOnAnimPlayListener(this);
                        this.llAnimParent.addView(leftAnimView);
                        leftAnimView.addItemInfo(poll);
                        return;
                    default:
                        leftAnimView.setOnAnimPlayListener(this);
                        this.llAnimParent.addView(leftAnimView);
                        leftAnimView.addItemInfo(poll);
                        return;
                }
            } else if (!poll.isLocalAnim()) {
                switch (poll.leftAnimType) {
                    case ConstantUtils.LEFT_ANIM_OPEN_NOBILITY_TYPE /* 2308 */:
                        while (i < childCount) {
                            LeftAnimView leftAnimView3 = (LeftAnimView) this.llAnimParent.getChildAt(i);
                            if (!leftAnimView3.leftAnimEntity.isLocalAnim()) {
                                if (leftAnimView3.animType == 2308) {
                                    if (poll.nobilityType > leftAnimView3.leftAnimEntity.nobilityType) {
                                        NobilityOpenDanmuView nobilityOpenDanmuView = new NobilityOpenDanmuView(this.mContext);
                                        this.llAnimParent.removeView(leftAnimView3);
                                        nobilityOpenDanmuView.setOnAnimPlayListener(this);
                                        this.llAnimParent.addView(nobilityOpenDanmuView);
                                        nobilityOpenDanmuView.addItemInfo(poll);
                                        return;
                                    }
                                } else {
                                    NobilityOpenDanmuView nobilityOpenDanmuView2 = new NobilityOpenDanmuView(this.mContext);
                                    this.llAnimParent.removeView(leftAnimView3);
                                    nobilityOpenDanmuView2.setOnAnimPlayListener(this);
                                    this.llAnimParent.addView(nobilityOpenDanmuView2);
                                    nobilityOpenDanmuView2.addItemInfo(poll);
                                    return;
                                }
                            }
                            i++;
                        }
                        return;
                    case ConstantUtils.LEFT_ANIM_OPEN_GUARD_TYPE /* 2309 */:
                        while (i < childCount) {
                            LeftAnimView leftAnimView4 = (LeftAnimView) this.llAnimParent.getChildAt(i);
                            if (!leftAnimView4.leftAnimEntity.isLocalAnim()) {
                                int i2 = leftAnimView4.animType;
                                if (i2 == 2310) {
                                    GuardOpenDanmuView guardOpenDanmuView = new GuardOpenDanmuView(this.mContext);
                                    this.llAnimParent.removeView(leftAnimView4);
                                    guardOpenDanmuView.setOnAnimPlayListener(this);
                                    this.llAnimParent.addView(guardOpenDanmuView);
                                    guardOpenDanmuView.addItemInfo(poll);
                                    return;
                                } else if (i2 == 2309 && NumberUtils.string2int(poll.guardType) > NumberUtils.string2int(leftAnimView4.leftAnimEntity.guardType)) {
                                    GuardOpenDanmuView guardOpenDanmuView2 = new GuardOpenDanmuView(this.mContext);
                                    this.llAnimParent.removeView(leftAnimView4);
                                    guardOpenDanmuView2.setOnAnimPlayListener(this);
                                    this.llAnimParent.addView(guardOpenDanmuView2);
                                    guardOpenDanmuView2.addItemInfo(poll);
                                    return;
                                }
                            }
                            i++;
                        }
                        return;
                    case ConstantUtils.LEFT_ANIM_ENTER_NOBILITY_TYPE /* 2310 */:
                        while (i < childCount) {
                            LeftAnimView leftAnimView5 = (LeftAnimView) this.llAnimParent.getChildAt(i);
                            if (leftAnimView5.animType == 2310 && !leftAnimView5.leftAnimEntity.isLocalAnim() && leftAnimView5.leftAnimEntity.nobilityType < poll.nobilityType) {
                                this.llAnimParent.removeView(leftAnimView5);
                                NobilityEnterDanmuView nobilityEnterDanmuView2 = new NobilityEnterDanmuView(this.mContext);
                                nobilityEnterDanmuView2.setOnAnimPlayListener(this);
                                this.llAnimParent.addView(nobilityEnterDanmuView2);
                                nobilityEnterDanmuView2.addItemInfo(poll);
                                return;
                            }
                            i++;
                        }
                        return;
                    default:
                        return;
                }
            } else {
                switch (poll.leftAnimType) {
                    case ConstantUtils.LEFT_ANIM_OPEN_NOBILITY_TYPE /* 2308 */:
                        leftAnimView = new NobilityOpenDanmuView(this.mContext);
                        break;
                    case ConstantUtils.LEFT_ANIM_OPEN_GUARD_TYPE /* 2309 */:
                        leftAnimView = new GuardOpenDanmuView(this.mContext);
                        break;
                    case ConstantUtils.LEFT_ANIM_ENTER_NOBILITY_TYPE /* 2310 */:
                        for (int i3 = 0; i3 < childCount; i3++) {
                            LeftAnimView leftAnimView6 = (LeftAnimView) this.llAnimParent.getChildAt(i3);
                            if (leftAnimView6.animType == 2310) {
                                this.llAnimParent.removeView(leftAnimView6);
                                NobilityEnterDanmuView nobilityEnterDanmuView3 = new NobilityEnterDanmuView(this.mContext);
                                nobilityEnterDanmuView3.setOnAnimPlayListener(this);
                                this.llAnimParent.addView(nobilityEnterDanmuView3);
                                nobilityEnterDanmuView3.addItemInfo(poll);
                                return;
                            }
                        }
                        leftAnimView = new NobilityEnterDanmuView(this.mContext);
                        break;
                }
                this.llAnimParent.removeViewAt(0);
                leftAnimView.setOnAnimPlayListener(this);
                this.llAnimParent.addView(leftAnimView);
                leftAnimView.addItemInfo(poll);
            }
        }
    }

    private synchronized boolean hasAnim() {
        boolean z;
        if (this.leftAnimMsgQueue != null) {
            if (!this.leftAnimMsgQueue.isEmpty()) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    public LeftAnimManager(Context context) {
        this.mContext = context;
    }

    public LeftAnimManager setAnimLayoutMaxNum(int i) {
        this.mAnimLayoutMaxNums = i;
        return this;
    }

    public LeftAnimManager setAnimLayout(LinearLayout linearLayout) {
        this.llAnimParent = linearLayout;
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(0, layoutTransition.getAnimator(0));
        layoutTransition.setAnimator(2, layoutTransition.getAnimator(2));
        layoutTransition.setAnimator(3, layoutTransition.getAnimator(0));
        layoutTransition.setAnimator(1, layoutTransition.getAnimator(3));
        this.llAnimParent.setLayoutTransition(layoutTransition);
        return this;
    }

    @Override // com.tomatolive.library.p136ui.interfaces.OnAnimPlayListener
    public void onEnd(final LeftAnimView leftAnimView) {
        handlerMainPost(new Runnable() { // from class: com.tomatolive.library.utils.live.-$$Lambda$LeftAnimManager$QbTDjhujvGfbGdFVIg3ON_y4bm8
            @Override // java.lang.Runnable
            public final void run() {
                LeftAnimManager.this.lambda$onEnd$1$LeftAnimManager(leftAnimView);
            }
        });
    }

    public /* synthetic */ void lambda$onEnd$1$LeftAnimManager(LeftAnimView leftAnimView) {
        this.llAnimParent.removeView(leftAnimView);
        lambda$addLeftAnim$0$LeftAnimManager();
    }

    public synchronized void cleanAll() {
        if (this.llAnimParent == null) {
            return;
        }
        if (this.leftAnimMsgQueue != null) {
            this.leftAnimMsgQueue.clear();
        }
        for (int i = 0; i < this.llAnimParent.getChildCount(); i++) {
            View childAt = this.llAnimParent.getChildAt(i);
            if (childAt instanceof LeftAnimView) {
                ((LeftAnimView) childAt).onRelease();
            }
        }
        this.llAnimParent.removeAllViews();
        if (this.mainHandler != null) {
            this.mainHandler.removeCallbacksAndMessages(null);
        }
    }

    private void handlerMainPost(Runnable runnable) {
        Handler handler = this.mainHandler;
        if (handler == null || runnable == null) {
            return;
        }
        handler.post(runnable);
    }
}
