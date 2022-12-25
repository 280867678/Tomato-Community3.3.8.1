package com.one.tomato.mvp.p080ui.p082up;

import com.one.tomato.entity.p079db.PostDownPayBean;
import com.one.tomato.entity.p079db.PostRewardId;
import com.one.tomato.utils.DBUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostRewardPayUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.up.PostRewardPayUtils */
/* loaded from: classes3.dex */
public final class PostRewardPayUtils {
    public static final PostRewardPayUtils INSTANCE = new PostRewardPayUtils();

    private PostRewardPayUtils() {
    }

    public final void setPayPost(int i) {
        List<PostRewardId> payPostId = DBUtil.getPayPostId();
        if (!(payPostId == null || payPostId.isEmpty())) {
            for (PostRewardId it2 : payPostId) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (i == it2.getPostId()) {
                    return;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        PostRewardId postRewardId = new PostRewardId();
        postRewardId.setMembeId(DBUtil.getMemberId());
        postRewardId.setPostId(i);
        arrayList.add(postRewardId);
        DBUtil.setPayPostId(arrayList);
    }

    public final boolean isAreadlyPay(int i) {
        List<PostRewardId> payPostId = DBUtil.getPayPostId();
        if (!(payPostId == null || payPostId.isEmpty())) {
            for (PostRewardId it2 : payPostId) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (i == it2.getPostId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void setDownPayPost(int i) {
        List<PostDownPayBean> downPayPostId = DBUtil.getDownPayPostId();
        if (!(downPayPostId == null || downPayPostId.isEmpty())) {
            for (PostDownPayBean it2 : downPayPostId) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (i == it2.getPostId()) {
                    return;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        PostDownPayBean postDownPayBean = new PostDownPayBean();
        postDownPayBean.setMembeId(DBUtil.getMemberId());
        postDownPayBean.setPostId(i);
        arrayList.add(postDownPayBean);
        DBUtil.setDownPayPostId(arrayList);
    }

    public final boolean isAreadlyDownPay(int i) {
        List<PostDownPayBean> downPayPostId = DBUtil.getDownPayPostId();
        if (!(downPayPostId == null || downPayPostId.isEmpty())) {
            for (PostDownPayBean it2 : downPayPostId) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (i == it2.getPostId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
