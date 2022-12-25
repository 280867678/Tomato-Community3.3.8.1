package com.one.tomato.utils.post;

import android.content.Context;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.LookTimeBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class VideoPlayCountUtils {
    private static VideoPlayCountUtils videoPlayCountUtils;
    private LookTimeBean tempBean = DBUtil.getLookTimeBean();

    private VideoPlayCountUtils() {
    }

    public static VideoPlayCountUtils getInstance() {
        if (videoPlayCountUtils == null) {
            synchronized (VideoPlayCountUtils.class) {
                if (videoPlayCountUtils == null) {
                    videoPlayCountUtils = new VideoPlayCountUtils();
                }
            }
        }
        return videoPlayCountUtils;
    }

    public void updateLookTimeBean(LookTimeBean lookTimeBean) {
        this.tempBean = lookTimeBean;
        if (this.tempBean == null) {
            this.tempBean = DBUtil.getLookTimeBean();
        }
    }

    public boolean isFreePlay() {
        if (DBUtil.getUserInfo().getVipType() > 0) {
            return true;
        }
        return this.tempBean.isHasPay();
    }

    public int getRemainTimes() {
        int remainTimes = this.tempBean.getRemainTimes();
        if (remainTimes < 0) {
            return 0;
        }
        return remainTimes;
    }

    public int getFreeTimes() {
        boolean z;
        int freeTimes = this.tempBean.getFreeTimes();
        int remainTimes = this.tempBean.getRemainTimes();
        boolean z2 = true;
        if (freeTimes == 0) {
            freeTimes = 3;
            z = true;
        } else {
            z = false;
        }
        if (freeTimes < remainTimes) {
            freeTimes = remainTimes;
        } else {
            z2 = z;
        }
        if (z2) {
            this.tempBean.setFreeTimes(freeTimes);
        }
        return freeTimes;
    }

    public boolean isVideoPlay(PostList postList) {
        List<String> lookedIds = this.tempBean.getLookedIds();
        if (postList == null) {
            return false;
        }
        String str = postList.getId() + "";
        if ((postList.getMemberId() + "").equals(DBUtil.getMemberId() + "")) {
            return true;
        }
        return lookedIds != null && !lookedIds.isEmpty() && lookedIds.contains(str);
    }

    public void addVideoPlayList(PostList postList) {
        List<String> lookedIds = this.tempBean.getLookedIds();
        if (postList == null) {
            return;
        }
        if (lookedIds == null) {
            lookedIds = new ArrayList<>();
        }
        if (!lookedIds.contains(postList.getId() + "")) {
            lookedIds.add(postList.getId() + "");
        }
        this.tempBean.setLookedIds(lookedIds);
        if (getRemainTimes() <= 0) {
            return;
        }
        this.tempBean.setRemainTimes(getRemainTimes() - 1);
    }

    public void postRewardPay(Context context, Map<String, Object> map, final Function1<String, Object> function1, final Function1<ResponseThrowable, Object> function12) {
        ApiImplService.Companion.getApiImplService().requestRewardPay(map).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) context)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<String>(this) { // from class: com.one.tomato.utils.post.VideoPlayCountUtils.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(String str) {
                function1.mo6794invoke(str);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                function12.mo6794invoke(responseThrowable);
                LogUtil.m3787d("yan", "付费帖子扣费失败---------" + responseThrowable.getThrowableMessage());
            }
        });
    }
}
