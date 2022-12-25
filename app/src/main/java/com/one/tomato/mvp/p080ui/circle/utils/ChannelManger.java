package com.one.tomato.mvp.p080ui.circle.utils;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.p079db.DefaultChannelBean;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelManger.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.utils.ChannelManger */
/* loaded from: classes3.dex */
public final class ChannelManger {
    public static final ChannelManger INSTANCE = new ChannelManger();

    private ChannelManger() {
    }

    public final void requestChannelList() {
        ApiImplService.Companion.getApiImplService().requestChannelList().subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<UserChannelBean>() { // from class: com.one.tomato.mvp.ui.circle.utils.ChannelManger$requestChannelList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(UserChannelBean userChannelBean) {
                if (userChannelBean != null) {
                    ChannelManger.INSTANCE.setCircleTabBean(userChannelBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("請求頻道列表錯誤");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3785e("yan", sb.toString());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setCircleTabBean(UserChannelBean userChannelBean) {
        List<DefaultChannelBean> defaultChannel;
        List<DefaultChannelBean> defaultChannel2;
        String string = PreferencesUtil.getInstance().getString("share_channel_vision");
        if (TextUtils.isEmpty(string) || !Intrinsics.areEqual(string, userChannelBean.getVersionNo())) {
            List<DefaultChannelBean> defaultChannel3 = userChannelBean.getDefaultChannel();
            boolean z = false;
            if (defaultChannel3 == null || defaultChannel3.isEmpty()) {
                return;
            }
            PreferencesUtil.getInstance().putString("share_channel_vision", userChannelBean.getVersionNo());
            UserChannelBean userChannelBean2 = getUserChannelBean();
            if (userChannelBean2 != null && (defaultChannel2 = userChannelBean2.getDefaultChannel()) != null) {
                if (defaultChannel2 == null || defaultChannel2.isEmpty()) {
                    ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
                    ArrayList<DefaultChannelBean> arrayList2 = new ArrayList<>();
                    arrayList.addAll(userChannelBean.getDefaultChannel());
                    saveUserChannel(arrayList, UserChannelBean.TYPE_DEFAULT);
                    arrayList2.addAll(userChannelBean.getNotDefaultChannel());
                    saveUserChannel(arrayList2, UserChannelBean.TYPE_NO_DEFAULT);
                    defaultChannel = userChannelBean.getDefaultChannel();
                    if (defaultChannel != null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.DefaultChannelBean> /* = java.util.ArrayList<com.one.tomato.entity.db.DefaultChannelBean> */");
                    }
                    saveSystemChannel((ArrayList) defaultChannel, UserChannelBean.TYPE_DEFAULT);
                    List<DefaultChannelBean> notDefaultChannel = userChannelBean.getNotDefaultChannel();
                    if (notDefaultChannel == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.DefaultChannelBean> /* = java.util.ArrayList<com.one.tomato.entity.db.DefaultChannelBean> */");
                    }
                    saveSystemChannel((ArrayList) notDefaultChannel, UserChannelBean.TYPE_NO_DEFAULT);
                    return;
                }
            }
            List<DefaultChannelBean> list = null;
            List<DefaultChannelBean> defaultChannel4 = userChannelBean2 != null ? userChannelBean2.getDefaultChannel() : null;
            if (userChannelBean2 != null) {
                list = userChannelBean2.getNotDefaultChannel();
            }
            Collection<? extends DefaultChannelBean> defaultChannel5 = userChannelBean.getDefaultChannel();
            ArrayList<DefaultChannelBean> arrayList3 = new ArrayList<>();
            arrayList3.addAll(defaultChannel5);
            Collection<? extends DefaultChannelBean> commpareIsNewChannel = commpareIsNewChannel(arrayList3, UserChannelBean.TYPE_DEFAULT);
            ArrayList<DefaultChannelBean> arrayList4 = new ArrayList<>();
            ArrayList<DefaultChannelBean> arrayList5 = new ArrayList<>();
            if (defaultChannel4 != null && defaultChannel5 != null) {
                if (commpareIsNewChannel != null) {
                    defaultChannel4.addAll(commpareIsNewChannel);
                }
                for (DefaultChannelBean serviceItem : defaultChannel5) {
                    for (DefaultChannelBean it2 : defaultChannel4) {
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        int channelId = it2.getChannelId();
                        Intrinsics.checkExpressionValueIsNotNull(serviceItem, "serviceItem");
                        if (channelId == serviceItem.getChannelId()) {
                            arrayList4.add(serviceItem);
                        }
                    }
                }
            }
            arrayList3.clear();
            arrayList3.addAll(userChannelBean.getNotDefaultChannel());
            Collection<? extends DefaultChannelBean> commpareIsNewChannel2 = commpareIsNewChannel(arrayList3, UserChannelBean.TYPE_NO_DEFAULT);
            if (commpareIsNewChannel2 != null) {
                if (list != null) {
                    list.addAll(commpareIsNewChannel2);
                }
                if (list != null) {
                    arrayList5.addAll(list);
                }
            } else if (list != null) {
                arrayList5.addAll(list);
            }
            ArrayList<DefaultChannelBean> commpareIsDelete = commpareIsDelete(userChannelBean);
            if (!arrayList5.isEmpty()) {
                if (!(commpareIsDelete == null || commpareIsDelete.isEmpty())) {
                    ArrayList<DefaultChannelBean> arrayList6 = new ArrayList();
                    for (DefaultChannelBean defaultChannelBean : arrayList5) {
                        for (DefaultChannelBean defaultChannelBean2 : commpareIsDelete) {
                            if (defaultChannelBean2.getChannelId() == defaultChannelBean.getChannelId()) {
                                arrayList6.add(defaultChannelBean);
                            }
                        }
                    }
                    for (DefaultChannelBean defaultChannelBean3 : arrayList6) {
                        if (arrayList5.contains(defaultChannelBean3)) {
                            arrayList5.remove(defaultChannelBean3);
                        }
                    }
                }
            }
            ArrayList<DefaultChannelBean> changeChannelName = changeChannelName(arrayList5, userChannelBean);
            if (changeChannelName == null || changeChannelName.isEmpty()) {
                z = true;
            }
            if (!z) {
                arrayList5.clear();
                arrayList5.addAll(changeChannelName);
            }
            saveUserChannel(arrayList4, UserChannelBean.TYPE_DEFAULT);
            saveUserChannel(arrayList5, UserChannelBean.TYPE_NO_DEFAULT);
            defaultChannel = userChannelBean.getDefaultChannel();
            if (defaultChannel != null) {
            }
        }
    }

    private final UserChannelBean getUserChannelBean() {
        UserChannelBean userChannelBean = new UserChannelBean();
        ArrayList<DefaultChannelBean> userChannel = getUserChannel(UserChannelBean.TYPE_DEFAULT);
        ArrayList<DefaultChannelBean> userChannel2 = getUserChannel(UserChannelBean.TYPE_NO_DEFAULT);
        userChannelBean.setDefaultChannel(userChannel);
        userChannelBean.setNotDefaultChannel(userChannel2);
        return userChannelBean;
    }

    public final void saveUserChannel(ArrayList<DefaultChannelBean> arrayList, String type, boolean z) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (z) {
            if (Intrinsics.areEqual(type, UserChannelBean.TYPE_DEFAULT)) {
                PreferencesUtil.getInstance().putString("channel_user_defoult", "");
                return;
            } else {
                PreferencesUtil.getInstance().putString("channel_user_no_defoult", "");
                return;
            }
        }
        saveUserChannel(arrayList, type);
        LogUtil.m3788d("?---------執行存儲用戶頻道");
    }

    public final void saveUserChannel(ArrayList<DefaultChannelBean> arrayList, String type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (!(arrayList == null || arrayList.isEmpty())) {
            if (Intrinsics.areEqual(type, UserChannelBean.TYPE_DEFAULT)) {
                PreferencesUtil.getInstance().putString("channel_user_defoult", BaseApplication.getGson().toJson(arrayList));
            } else {
                PreferencesUtil.getInstance().putString("channel_user_no_defoult", BaseApplication.getGson().toJson(arrayList));
            }
        }
        LogUtil.m3788d("?---------執行存儲用戶頻道");
    }

    public final ArrayList<DefaultChannelBean> getUserChannel(String type) {
        String string;
        Intrinsics.checkParameterIsNotNull(type, "type");
        ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
        if (Intrinsics.areEqual(type, UserChannelBean.TYPE_DEFAULT)) {
            string = PreferencesUtil.getInstance().getString("channel_user_defoult");
            Intrinsics.checkExpressionValueIsNotNull(string, "PreferencesUtil.getInsta…nt.CHANNEAL_USER_DEFOULT)");
        } else {
            string = PreferencesUtil.getInstance().getString("channel_user_no_defoult");
            Intrinsics.checkExpressionValueIsNotNull(string, "PreferencesUtil.getInsta…CHANNEAL_USER_NO_DEFOULT)");
        }
        if (!TextUtils.isEmpty(string)) {
            ArrayList arrayList2 = (ArrayList) BaseApplication.getGson().fromJson(string, new TypeToken<ArrayList<DefaultChannelBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.ChannelManger$getUserChannel$fromJson$1
            }.getType());
            if (arrayList2 instanceof ArrayList) {
                arrayList.addAll(arrayList2);
            }
        }
        return arrayList;
    }

    public final void saveSystemChannel(ArrayList<DefaultChannelBean> arrayList, String type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (!(arrayList == null || arrayList.isEmpty())) {
            if (Intrinsics.areEqual(type, UserChannelBean.TYPE_DEFAULT)) {
                PreferencesUtil.getInstance().putString("channel_system_defoult", BaseApplication.getGson().toJson(arrayList));
            } else {
                PreferencesUtil.getInstance().putString("channel_system_no_defoult", BaseApplication.getGson().toJson(arrayList));
            }
        }
        LogUtil.m3788d("?---------執行存儲用戶頻道");
    }

    public final ArrayList<DefaultChannelBean> getSystemChannel(String type) {
        String string;
        Intrinsics.checkParameterIsNotNull(type, "type");
        ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
        if (Intrinsics.areEqual(type, UserChannelBean.TYPE_DEFAULT)) {
            string = PreferencesUtil.getInstance().getString("channel_system_defoult");
            Intrinsics.checkExpressionValueIsNotNull(string, "PreferencesUtil.getInsta….CHANNEAL_SYSTEM_DEFOULT)");
        } else {
            string = PreferencesUtil.getInstance().getString("channel_system_no_defoult");
            Intrinsics.checkExpressionValueIsNotNull(string, "PreferencesUtil.getInsta…ANNEAL_SYSTEM_NO_DEFOULT)");
        }
        if (!TextUtils.isEmpty(string)) {
            ArrayList arrayList2 = (ArrayList) BaseApplication.getGson().fromJson(string, new TypeToken<ArrayList<DefaultChannelBean>>() { // from class: com.one.tomato.mvp.ui.circle.utils.ChannelManger$getSystemChannel$fromJson$1
            }.getType());
            if (arrayList2 instanceof ArrayList) {
                arrayList.addAll(arrayList2);
            }
        }
        return arrayList;
    }

    private final ArrayList<DefaultChannelBean> commpareIsNewChannel(ArrayList<DefaultChannelBean> arrayList, String str) {
        ArrayList<DefaultChannelBean> arrayList2 = new ArrayList<>();
        ArrayList<DefaultChannelBean> systemChannel = getSystemChannel(str);
        if (systemChannel == null || arrayList == null) {
            return null;
        }
        ArrayList arrayList3 = new ArrayList();
        for (DefaultChannelBean defaultChannelBean : systemChannel) {
            arrayList3.add(Integer.valueOf(defaultChannelBean.getChannelId()));
        }
        for (DefaultChannelBean defaultChannelBean2 : arrayList) {
            if (!arrayList3.contains(Integer.valueOf(defaultChannelBean2.getChannelId()))) {
                arrayList2.add(defaultChannelBean2);
            }
        }
        if (!(!arrayList2.isEmpty())) {
            return null;
        }
        return arrayList2;
    }

    public final ArrayList<DefaultChannelBean> commpareIsDelete(UserChannelBean userChannelBean) {
        DefaultChannelBean defaultChannelBean;
        if (userChannelBean == null) {
            return null;
        }
        ArrayList<DefaultChannelBean> arrayList = new ArrayList<>();
        List<DefaultChannelBean> notDefaultChannel = userChannelBean.getNotDefaultChannel();
        List<DefaultChannelBean> defaultChannel = userChannelBean.getDefaultChannel();
        ArrayList<DefaultChannelBean> systemChannel = getSystemChannel(UserChannelBean.TYPE_DEFAULT);
        ArrayList<DefaultChannelBean> systemChannel2 = getSystemChannel(UserChannelBean.TYPE_NO_DEFAULT);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        if (!(defaultChannel == null || defaultChannel.isEmpty())) {
            for (DefaultChannelBean it2 : defaultChannel) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                arrayList2.add(Integer.valueOf(it2.getChannelId()));
            }
        }
        if (!(notDefaultChannel == null || notDefaultChannel.isEmpty())) {
            for (DefaultChannelBean it3 : notDefaultChannel) {
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                arrayList2.add(Integer.valueOf(it3.getChannelId()));
            }
        }
        if (systemChannel != null) {
            if (!(systemChannel == null || systemChannel.isEmpty())) {
                for (DefaultChannelBean defaultChannelBean2 : systemChannel) {
                    linkedHashMap.put(Integer.valueOf(defaultChannelBean2.getChannelId()), defaultChannelBean2);
                }
            }
        }
        if (systemChannel2 != null) {
            if (systemChannel2 == null || systemChannel2.isEmpty()) {
                z = true;
            }
            if (!z) {
                for (DefaultChannelBean defaultChannelBean3 : systemChannel2) {
                    linkedHashMap.put(Integer.valueOf(defaultChannelBean3.getChannelId()), defaultChannelBean3);
                }
            }
        }
        for (Number number : linkedHashMap.keySet()) {
            int intValue = number.intValue();
            if (!arrayList2.contains(Integer.valueOf(intValue)) && (defaultChannelBean = (DefaultChannelBean) linkedHashMap.get(Integer.valueOf(intValue))) != null) {
                arrayList.add(defaultChannelBean);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ArrayList<DefaultChannelBean> changeChannelName(ArrayList<DefaultChannelBean> arrayList, UserChannelBean serverBean) {
        Intrinsics.checkParameterIsNotNull(serverBean, "serverBean");
        List<DefaultChannelBean> notDefaultChannel = serverBean.getNotDefaultChannel();
        List<DefaultChannelBean> defaultChannel = serverBean.getDefaultChannel();
        ArrayList<DefaultChannelBean> arrayList2 = new ArrayList<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = false;
        if (!(defaultChannel == null || defaultChannel.isEmpty())) {
            for (DefaultChannelBean it2 : defaultChannel) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                linkedHashMap.put(Integer.valueOf(it2.getChannelId()), it2);
            }
        }
        if (!(notDefaultChannel == null || notDefaultChannel.isEmpty())) {
            for (DefaultChannelBean it3 : notDefaultChannel) {
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                linkedHashMap.put(Integer.valueOf(it3.getChannelId()), it3);
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            z = true;
        }
        if (z) {
            return null;
        }
        for (DefaultChannelBean defaultChannelBean : arrayList) {
            if (linkedHashMap.keySet().contains(Integer.valueOf(defaultChannelBean.getChannelId()))) {
                Object obj = linkedHashMap.get(Integer.valueOf(defaultChannelBean.getChannelId()));
                if (obj == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
