package com.one.tomato.mvp.p080ui.circle.utils;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.tomatolive.library.http.RequestParams;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;

/* compiled from: CircleAllUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils */
/* loaded from: classes3.dex */
public final class CircleAllUtils {
    public static final CircleAllUtils INSTANCE = new CircleAllUtils();
    private static final ArrayList<CircleDiscoverListBean> postInsertCircle = new ArrayList<>();
    private static final ArrayList<UpRecommentBean> postInsertUp = new ArrayList<>();
    private static final ArrayList<PostHotMessageBean> postInsertHotMessage = new ArrayList<>();

    private CircleAllUtils() {
    }

    public final void initCircle() {
        requestCircleAll();
        initPostInsertCircle();
        requestUpRecomment();
        requestHotMessage();
    }

    public final void initChessHomeData() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            requestChessHomeData();
        }
    }

    public final ArrayList<CircleAllBean> pullHomeRecommentCircle(int i) {
        List<CircleAllBean> circleAllBean = DBUtil.getCircleAllBean();
        ArrayList<CircleAllBean> arrayList = new ArrayList<>();
        if (!(circleAllBean == null || circleAllBean.isEmpty()) && i != 0 && i <= circleAllBean.size() - 1) {
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(circleAllBean.get(i2));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ArrayList<CircleDiscoverListBean> pullRecommentCircleInsertPostList() {
        Ranges until;
        int random;
        ArrayList<CircleDiscoverListBean> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(postInsertCircle);
        if (!arrayList2.isEmpty() && arrayList2.size() >= 5) {
            for (int i = 0; i <= 5; i++) {
                try {
                    until = _Ranges.until(0, arrayList2.size());
                    random = _Ranges.random(until, Random.Default);
                    arrayList.add(arrayList2.get(random));
                    arrayList2.remove(random);
                } catch (Exception unused) {
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ArrayList<UpRecommentBean> pullRecommentUpInsertList() {
        Ranges until;
        int random;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(postInsertUp);
        ArrayList<UpRecommentBean> arrayList2 = new ArrayList<>();
        if (!arrayList.isEmpty() && arrayList.size() >= 10) {
            for (int i = 0; i <= 9; i++) {
                try {
                    until = _Ranges.until(0, arrayList.size());
                    random = _Ranges.random(until, Random.Default);
                    arrayList2.add(arrayList.get(random));
                    arrayList.remove(random);
                } catch (Exception unused) {
                }
            }
        }
        return arrayList2;
    }

    public final void removeUp(UpRecommentBean item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        if (postInsertUp.contains(item)) {
            postInsertUp.remove(item);
            DBUtil.saveRecommentUpBean(postInsertUp);
        }
    }

    public final UpRecommentBean getUpBean() {
        Ranges until;
        int random;
        ArrayList<UpRecommentBean> arrayList = postInsertUp;
        if (!(arrayList == null || arrayList.isEmpty())) {
            until = _Ranges.until(0, postInsertUp.size());
            random = _Ranges.random(until, Random.Default);
            return postInsertUp.get(random);
        }
        return null;
    }

    public final ArrayList<PostHotMessageBean> pullRecommentHotMessage() {
        ArrayList<PostHotMessageBean> arrayList = postInsertHotMessage;
        if ((arrayList == null || arrayList.isEmpty()) || postInsertHotMessage.size() < 2) {
            return null;
        }
        return postInsertHotMessage;
    }

    private final void initPostInsertCircle() {
        ApiImplService.Companion.getApiImplService().requestPostInsertCircle(1).compose(RxUtils.schedulersTransformer()).subscribe(CircleAllUtils$initPostInsertCircle$1.INSTANCE);
    }

    private final void requestCircleAll() {
        ApiImplService.Companion.getApiImplService().requestCircleAll(DBUtil.getMemberId(), 1).subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<ArrayList<CircleAllBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$requestCircleAll$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CircleAllBean> arrayList) {
                if (arrayList != null) {
                    DBUtil.saveCiecleAllBean(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("請求圈子全部失敗 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    private final void requestUpRecomment() {
        ApiImplService.Companion.getApiImplService().requestRecommentUpList(DBUtil.getMemberId()).subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<ArrayList<UpRecommentBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$requestUpRecomment$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<UpRecommentBean> arrayList) {
                ArrayList arrayList2;
                if (arrayList != null) {
                    if (arrayList == null || arrayList.isEmpty()) {
                        return;
                    }
                    CircleAllUtils circleAllUtils = CircleAllUtils.INSTANCE;
                    arrayList2 = CircleAllUtils.postInsertUp;
                    arrayList2.addAll(arrayList);
                    DBUtil.saveRecommentUpBean(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("請求推荐的up主失敗 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    private final void requestHotMessage() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", 1);
        linkedHashMap.put(RequestParams.PAGE_SIZE, 10);
        linkedHashMap.put("eventPosition", "1");
        ApiImplService.Companion.getApiImplService().requestHotEventList(linkedHashMap).subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<ArrayList<PostHotMessageBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$requestHotMessage$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostHotMessageBean> arrayList) {
                ArrayList arrayList2;
                if (arrayList != null) {
                    if (arrayList == null || arrayList.isEmpty()) {
                        return;
                    }
                    CircleAllUtils circleAllUtils = CircleAllUtils.INSTANCE;
                    arrayList2 = CircleAllUtils.postInsertHotMessage;
                    arrayList2.addAll(arrayList);
                    DBUtil.saveRecommentHotMessageBean(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("請求推荐的热点事件失敗 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3783i("yan", sb.toString());
            }
        });
    }

    private final void requestChessHomeData() {
        ApiImplService.Companion.getApiImplService().requestChessHomeData().subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<ArrayList<ChessTypeBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$requestChessHomeData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<ChessTypeBean> arrayList) {
                if (!(arrayList == null || arrayList.isEmpty())) {
                    CircleAllUtils.INSTANCE.saveChessHomeData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("请求娱乐首页数据 ++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3783i("yan", sb.toString());
            }
        });
    }

    public final void saveChessHomeData(ArrayList<ChessTypeBean> data) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        String json = BaseApplication.getGson().toJson(data);
        if (!TextUtils.isEmpty(json)) {
            PreferencesUtil.getInstance().putString("chess_home_data", json);
        }
    }

    public final ArrayList<ChessTypeBean> getChessHomeData() {
        String string = PreferencesUtil.getInstance().getString("chess_home_data", "");
        if (!TextUtils.isEmpty(string)) {
            ArrayList<ChessTypeBean> arrayList = (ArrayList) BaseApplication.getGson().fromJson(string, new TypeToken<ArrayList<ChessTypeBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.CircleAllUtils$getChessHomeData$fromJson$1
            }.getType());
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        }
        return null;
    }
}
