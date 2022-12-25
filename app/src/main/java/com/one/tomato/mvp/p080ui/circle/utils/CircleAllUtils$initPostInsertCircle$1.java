package com.one.tomato.mvp.p080ui.circle.utils;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.utils.LogUtil;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CircleAllUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$initPostInsertCircle$1 */
/* loaded from: classes3.dex */
public final class CircleAllUtils$initPostInsertCircle$1<T> implements Consumer<BaseResponse<ArrayList<CircleDiscoverListBean>>> {
    public static final CircleAllUtils$initPostInsertCircle$1 INSTANCE = new CircleAllUtils$initPostInsertCircle$1();

    CircleAllUtils$initPostInsertCircle$1() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(BaseResponse<ArrayList<CircleDiscoverListBean>> baseResponse) {
        ArrayList arrayList;
        if (baseResponse instanceof BaseResponse) {
            ArrayList<CircleDiscoverListBean> data = baseResponse.getData();
            if (data == null || data.isEmpty()) {
                return;
            }
            CircleAllUtils circleAllUtils = CircleAllUtils.INSTANCE;
            arrayList = CircleAllUtils.postInsertCircle;
            arrayList.addAll(baseResponse.getData());
        } else if (!(baseResponse instanceof Throwable)) {
        } else {
            LogUtil.m3787d("yan", "推荐的圈子错误 ++" + ((Throwable) baseResponse).getMessage());
        }
    }
}
