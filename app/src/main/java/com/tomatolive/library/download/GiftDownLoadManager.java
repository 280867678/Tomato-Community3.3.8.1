package com.tomatolive.library.download;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.http.utils.RetryWithDelayUtils;
import com.tomatolive.library.model.GiftBatchItemEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.p135db.GiftDownloadItemDBEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class GiftDownLoadManager {
    private GiftDownLoadManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final GiftDownLoadManager INSTANCE = new GiftDownLoadManager();

        private SingletonHolder() {
        }
    }

    public static GiftDownLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void updateAnimOnlineRes() {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().giftListV2(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.3
        }).onErrorResumeNext(new HttpResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.2
        }).retryWhen(new RetryWithDelayUtils(3, 3)).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<GiftDownloadItemEntity> list) {
                GiftDownLoadManager.this.updateLocalAnim(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLocalAnim(List<GiftDownloadItemEntity> list) {
        List<GiftDownloadItemEntity> localDownloadList = getLocalDownloadList();
        if (localDownloadList != null && !localDownloadList.isEmpty()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (GiftDownloadItemEntity giftDownloadItemEntity : localDownloadList) {
                linkedHashMap.put(giftDownloadItemEntity.markId, giftDownloadItemEntity);
            }
            for (GiftDownloadItemEntity giftDownloadItemEntity2 : list) {
                if (linkedHashMap.containsKey(giftDownloadItemEntity2.markId)) {
                    GiftDownloadItemEntity giftDownloadItemEntity3 = (GiftDownloadItemEntity) linkedHashMap.get(giftDownloadItemEntity2.markId);
                    if (FileUtils.isFile(AppUtils.getLocalGiftFilePath(giftDownloadItemEntity3.giftDirPath))) {
                        giftDownloadItemEntity2.giftDirPath = giftDownloadItemEntity3.giftDirPath;
                    }
                }
                linkedHashMap2.put(giftDownloadItemEntity2.markId, giftDownloadItemEntity2);
            }
            linkedHashMap.clear();
            localDownloadList.clear();
            localDownloadList.addAll(linkedHashMap2.values());
            list = localDownloadList;
        }
        setLocalDownloadList(list);
        startLoadRes(list);
    }

    public synchronized void updateLocalDownloadList(List<GiftDownloadItemEntity> list) {
        List<GiftDownloadItemEntity> localDownloadList = getLocalDownloadList();
        if (localDownloadList != null && !localDownloadList.isEmpty()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (GiftDownloadItemEntity giftDownloadItemEntity : localDownloadList) {
                linkedHashMap.put(giftDownloadItemEntity.markId, giftDownloadItemEntity);
            }
            for (GiftDownloadItemEntity giftDownloadItemEntity2 : list) {
                if (linkedHashMap.containsKey(giftDownloadItemEntity2.markId)) {
                    GiftDownloadItemEntity giftDownloadItemEntity3 = (GiftDownloadItemEntity) linkedHashMap.get(giftDownloadItemEntity2.markId);
                    if (FileUtils.isFile(AppUtils.getLocalGiftFilePath(giftDownloadItemEntity3.giftDirPath))) {
                        giftDownloadItemEntity2.giftDirPath = giftDownloadItemEntity3.giftDirPath;
                    }
                }
                linkedHashMap2.put(giftDownloadItemEntity2.markId, giftDownloadItemEntity2);
            }
            linkedHashMap.clear();
            localDownloadList.clear();
            localDownloadList.addAll(linkedHashMap2.values());
            list = localDownloadList;
        }
        setLocalDownloadList(list);
    }

    private void startLoadRes(List<GiftDownloadItemEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
            if (isNeedDownload(giftDownloadItemEntity.giftDirPath)) {
                arrayList.add(giftDownloadItemEntity);
            }
        }
        if (arrayList.size() > 0) {
            GiftDownLoaderImpl.getInstance().startDownLoad(arrayList);
        }
    }

    public synchronized List<GiftDownloadItemEntity> getLocalDownloadList() {
        try {
            List<GiftDownloadItemDBEntity> findAll = DBUtils.findAll(GiftDownloadItemDBEntity.class);
            if (findAll == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (GiftDownloadItemDBEntity giftDownloadItemDBEntity : findAll) {
                arrayList.add(formatGiftDownloadItemEntity(giftDownloadItemDBEntity));
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public GiftDownloadItemEntity getLocalDownloadGiftItem(String str) {
        List<GiftDownloadItemEntity> localDownloadList = getLocalDownloadList();
        if (localDownloadList == null || localDownloadList.isEmpty()) {
            return null;
        }
        for (GiftDownloadItemEntity giftDownloadItemEntity : localDownloadList) {
            if (TextUtils.equals(str, giftDownloadItemEntity.markId)) {
                return giftDownloadItemEntity;
            }
        }
        return null;
    }

    public GiftDownloadItemEntity getLuckyGiftItem() {
        List<GiftDownloadItemEntity> localDownloadList = getLocalDownloadList();
        if (localDownloadList == null || localDownloadList.isEmpty()) {
            return null;
        }
        for (GiftDownloadItemEntity giftDownloadItemEntity : localDownloadList) {
            if (giftDownloadItemEntity.isLuckyGift()) {
                return giftDownloadItemEntity;
            }
        }
        return null;
    }

    public synchronized List<GiftDownloadItemEntity> getLocalDownloadListFilterLuckyGift() {
        try {
            List<GiftDownloadItemDBEntity> findAll = DBUtils.findAll(GiftDownloadItemDBEntity.class);
            if (findAll == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (GiftDownloadItemDBEntity giftDownloadItemDBEntity : findAll) {
                if (!giftDownloadItemDBEntity.isLuckyGift()) {
                    arrayList.add(formatGiftDownloadItemEntity(giftDownloadItemDBEntity));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public synchronized List<GiftDownloadItemEntity> getLocalDownloadListFilterLuckyScoreGift() {
        try {
            List<GiftDownloadItemDBEntity> findAll = DBUtils.findAll(GiftDownloadItemDBEntity.class);
            if (findAll == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (GiftDownloadItemDBEntity giftDownloadItemDBEntity : findAll) {
                if (!giftDownloadItemDBEntity.isLuckyGift() && !giftDownloadItemDBEntity.isScoreGift()) {
                    arrayList.add(formatGiftDownloadItemEntity(giftDownloadItemDBEntity));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public synchronized void setLocalDownloadList(List<GiftDownloadItemEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
            arrayList.add(formatGiftDownloadItemDBEntity(giftDownloadItemEntity));
        }
        DBUtils.saveGiftDownloadItemList(arrayList);
    }

    private boolean isNeedDownload(String str) {
        return TextUtils.isEmpty(str);
    }

    public synchronized void updateDownloadItem(String str, String str2, int i) {
        DBUtils.updateGiftDownloadItem(str, str2, i);
    }

    public GiftItemEntity getGiftItemEntity(String str) {
        GiftDownloadItemDBEntity giftDownloadItemDBEntity = DBUtils.getGiftDownloadItemDBEntity(str);
        if (giftDownloadItemDBEntity == null) {
            return null;
        }
        if (!FileUtils.isFile(AppUtils.getLocalGiftFilePath(giftDownloadItemDBEntity.giftDirPath))) {
            giftDownloadItemDBEntity.giftDirPath = "";
            DBUtils.updateGiftDownloadItem(giftDownloadItemDBEntity.markId, giftDownloadItemDBEntity.giftDirPath, 0);
        }
        return formatGiftItemEntity(giftDownloadItemDBEntity);
    }

    public String getGiftItemImgUrl(String str) {
        GiftDownloadItemDBEntity giftDownloadItemDBEntity = DBUtils.getGiftDownloadItemDBEntity(str);
        return giftDownloadItemDBEntity == null ? "" : giftDownloadItemDBEntity.imgurl;
    }

    public GiftItemEntity formatGiftItemEntity(GiftDownloadItemEntity giftDownloadItemEntity) {
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.markId = giftDownloadItemEntity.markId;
        giftItemEntity.giftDirPath = giftDownloadItemEntity.giftDirPath;
        giftItemEntity.name = giftDownloadItemEntity.name;
        giftItemEntity.imgurl = giftDownloadItemEntity.imgurl;
        giftItemEntity.price = giftDownloadItemEntity.price;
        giftItemEntity.giftNum = giftDownloadItemEntity.giftNum;
        giftItemEntity.duration = giftDownloadItemEntity.duration;
        giftItemEntity.active_time = giftDownloadItemEntity.active_time;
        giftItemEntity.effect_type = giftDownloadItemEntity.effect_type;
        giftItemEntity.animalUrl = giftDownloadItemEntity.animalUrl;
        giftItemEntity.broadcastRange = giftDownloadItemEntity.broadcastRange;
        giftItemEntity.boxType = giftDownloadItemEntity.boxType;
        giftItemEntity.boxId = giftDownloadItemEntity.boxId;
        giftItemEntity.boxName = giftDownloadItemEntity.boxName;
        giftItemEntity.isBroadcast = giftDownloadItemEntity.isBroadcast;
        giftItemEntity.isStayTuned = giftDownloadItemEntity.isStayTuned;
        giftItemEntity.giftCostType = giftDownloadItemEntity.giftCostType;
        giftItemEntity.giftBatchItemList = giftDownloadItemEntity.giftBatchItemList;
        giftItemEntity.avatar = giftDownloadItemEntity.avatar;
        giftItemEntity.anchorName = giftDownloadItemEntity.anchorName;
        giftItemEntity.userName = giftDownloadItemEntity.userName;
        giftItemEntity.isStarGift = giftDownloadItemEntity.isStarGift;
        giftItemEntity.caption = giftDownloadItemEntity.caption;
        return giftItemEntity;
    }

    public GiftItemEntity formatGiftItemEntity(GiftDownloadItemDBEntity giftDownloadItemDBEntity) {
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.markId = giftDownloadItemDBEntity.markId;
        giftItemEntity.giftDirPath = giftDownloadItemDBEntity.giftDirPath;
        giftItemEntity.name = giftDownloadItemDBEntity.name;
        giftItemEntity.imgurl = giftDownloadItemDBEntity.imgurl;
        giftItemEntity.price = giftDownloadItemDBEntity.price;
        giftItemEntity.giftNum = String.valueOf(giftDownloadItemDBEntity.num);
        giftItemEntity.duration = giftDownloadItemDBEntity.duration;
        giftItemEntity.active_time = giftDownloadItemDBEntity.active_time;
        giftItemEntity.effect_type = giftDownloadItemDBEntity.effect_type;
        giftItemEntity.animalUrl = giftDownloadItemDBEntity.animalUrl;
        giftItemEntity.broadcastRange = giftDownloadItemDBEntity.broadcastRange;
        giftItemEntity.boxType = giftDownloadItemDBEntity.boxType;
        giftItemEntity.boxId = giftDownloadItemDBEntity.boxId;
        giftItemEntity.boxName = giftDownloadItemDBEntity.boxName;
        giftItemEntity.isBroadcast = giftDownloadItemDBEntity.isBroadcast;
        giftItemEntity.giftCostType = giftDownloadItemDBEntity.giftCostType;
        giftItemEntity.giftBatchItemList = formatJsonToGiftBatchList(giftDownloadItemDBEntity.giftBatchJson);
        giftItemEntity.avatar = giftDownloadItemDBEntity.avatar;
        giftItemEntity.anchorName = giftDownloadItemDBEntity.anchorName;
        giftItemEntity.userName = giftDownloadItemDBEntity.userName;
        giftItemEntity.isStarGift = giftDownloadItemDBEntity.isStarGift;
        giftItemEntity.caption = giftDownloadItemDBEntity.caption;
        return giftItemEntity;
    }

    public GiftDownloadItemDBEntity formatGiftDownloadItemDBEntity(GiftDownloadItemEntity giftDownloadItemEntity) {
        GiftDownloadItemDBEntity giftDownloadItemDBEntity = new GiftDownloadItemDBEntity();
        giftDownloadItemDBEntity.markId = giftDownloadItemEntity.markId;
        giftDownloadItemDBEntity.giftDirPath = giftDownloadItemEntity.giftDirPath;
        giftDownloadItemDBEntity.name = giftDownloadItemEntity.name;
        giftDownloadItemDBEntity.imgurl = giftDownloadItemEntity.imgurl;
        giftDownloadItemDBEntity.price = giftDownloadItemEntity.price;
        giftDownloadItemDBEntity.num = NumberUtils.string2int(giftDownloadItemEntity.giftNum);
        giftDownloadItemDBEntity.duration = giftDownloadItemEntity.duration;
        giftDownloadItemDBEntity.active_time = giftDownloadItemEntity.active_time;
        giftDownloadItemDBEntity.effect_type = giftDownloadItemEntity.effect_type;
        giftDownloadItemDBEntity.animalUrl = giftDownloadItemEntity.animalUrl;
        giftDownloadItemDBEntity.broadcastRange = giftDownloadItemEntity.broadcastRange;
        giftDownloadItemDBEntity.boxType = giftDownloadItemEntity.boxType;
        giftDownloadItemDBEntity.boxId = giftDownloadItemEntity.boxId;
        giftDownloadItemDBEntity.boxName = giftDownloadItemEntity.boxName;
        giftDownloadItemDBEntity.isBroadcast = giftDownloadItemEntity.isBroadcast;
        giftDownloadItemDBEntity.giftCostType = giftDownloadItemEntity.giftCostType;
        giftDownloadItemDBEntity.giftBatchJson = formatGiftBatchListToJson(giftDownloadItemEntity.giftBatchItemList);
        giftDownloadItemDBEntity.avatar = giftDownloadItemEntity.avatar;
        giftDownloadItemDBEntity.anchorName = giftDownloadItemEntity.anchorName;
        giftDownloadItemDBEntity.userName = giftDownloadItemEntity.userName;
        giftDownloadItemDBEntity.isStarGift = giftDownloadItemEntity.isStarGift;
        giftDownloadItemDBEntity.caption = giftDownloadItemEntity.caption;
        return giftDownloadItemDBEntity;
    }

    public GiftDownloadItemEntity formatGiftDownloadItemEntity(GiftDownloadItemDBEntity giftDownloadItemDBEntity) {
        GiftDownloadItemEntity giftDownloadItemEntity = new GiftDownloadItemEntity();
        giftDownloadItemEntity.markId = giftDownloadItemDBEntity.markId;
        giftDownloadItemEntity.giftDirPath = giftDownloadItemDBEntity.giftDirPath;
        giftDownloadItemEntity.name = giftDownloadItemDBEntity.name;
        giftDownloadItemEntity.imgurl = giftDownloadItemDBEntity.imgurl;
        giftDownloadItemEntity.price = giftDownloadItemDBEntity.price;
        giftDownloadItemEntity.giftNum = String.valueOf(giftDownloadItemDBEntity.num);
        giftDownloadItemEntity.duration = giftDownloadItemDBEntity.duration;
        giftDownloadItemEntity.active_time = giftDownloadItemDBEntity.active_time;
        giftDownloadItemEntity.effect_type = giftDownloadItemDBEntity.effect_type;
        giftDownloadItemEntity.animalUrl = giftDownloadItemDBEntity.animalUrl;
        giftDownloadItemEntity.broadcastRange = giftDownloadItemDBEntity.broadcastRange;
        giftDownloadItemEntity.boxType = giftDownloadItemDBEntity.boxType;
        giftDownloadItemEntity.boxId = giftDownloadItemDBEntity.boxId;
        giftDownloadItemEntity.boxName = giftDownloadItemDBEntity.boxName;
        giftDownloadItemEntity.isBroadcast = giftDownloadItemDBEntity.isBroadcast;
        giftDownloadItemEntity.giftCostType = giftDownloadItemDBEntity.giftCostType;
        giftDownloadItemEntity.giftBatchItemList = formatJsonToGiftBatchList(giftDownloadItemDBEntity.giftBatchJson);
        giftDownloadItemEntity.avatar = giftDownloadItemDBEntity.avatar;
        giftDownloadItemEntity.anchorName = giftDownloadItemDBEntity.anchorName;
        giftDownloadItemEntity.userName = giftDownloadItemDBEntity.userName;
        giftDownloadItemEntity.isStarGift = giftDownloadItemDBEntity.isStarGift;
        giftDownloadItemEntity.caption = giftDownloadItemDBEntity.caption;
        return giftDownloadItemEntity;
    }

    public boolean checkGiftExist(GiftItemEntity giftItemEntity) {
        return DBUtils.isGiftDownloadItem(giftItemEntity);
    }

    public void updateAnimOnlineSingleRes(final GiftItemEntity giftItemEntity) {
        if (giftItemEntity == null) {
            return;
        }
        Observable.just(DBUtils.getGiftDownloadItemDBEntity(giftItemEntity.markId)).map(new Function<GiftDownloadItemDBEntity, Boolean>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.5
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Boolean mo6755apply(GiftDownloadItemDBEntity giftDownloadItemDBEntity) throws Exception {
                boolean z = true;
                if (giftDownloadItemDBEntity == null || giftDownloadItemDBEntity.downloadStatus == 1) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                if (bool.booleanValue()) {
                    GiftDownLoaderImpl giftDownLoaderImpl = GiftDownLoaderImpl.getInstance();
                    GiftItemEntity giftItemEntity2 = giftItemEntity;
                    giftDownLoaderImpl.downloadFile(giftItemEntity2.animalUrl, GiftConfig.INSTANCE.animResRootPath, FileUtils.formatSVGAFileName(giftItemEntity2.getLocalDirName()), giftItemEntity);
                }
            }
        });
    }

    private List<GiftBatchItemEntity> formatJsonToGiftBatchList(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (List) GsonUtils.fromJson(EncryptUtil.DESDecrypt("246887c3-ee20-4fe8-a320-1fde4a8d10b6", StringUtils.uncompress(str)), new TypeToken<List<GiftBatchItemEntity>>() { // from class: com.tomatolive.library.download.GiftDownLoadManager.6
            }.getType());
        } catch (Exception unused) {
            return null;
        }
    }

    private String formatGiftBatchListToJson(List<GiftBatchItemEntity> list) {
        if (list != null && !list.isEmpty()) {
            try {
                return StringUtils.compress(EncryptUtil.DESEncrypt("246887c3-ee20-4fe8-a320-1fde4a8d10b6", GsonUtils.toJson(list)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
