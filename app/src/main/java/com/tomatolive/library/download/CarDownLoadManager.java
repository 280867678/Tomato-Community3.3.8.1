package com.tomatolive.library.download;

import android.text.TextUtils;
import com.tomatolive.library.model.CarDownloadEntity;
import com.tomatolive.library.model.CarDownloadListEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class CarDownLoadManager {
    private volatile CarDownloadListEntity mDownloadListConfig;

    public void updateAnimOnlineAllRes() {
    }

    public void updateAnimOnlineSingleRes(CarDownloadEntity carDownloadEntity) {
    }

    private CarDownLoadManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final CarDownLoadManager INSTANCE = new CarDownLoadManager();

        private SingletonHolder() {
        }
    }

    public static CarDownLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private synchronized void updateLocalAnim(List<CarDownloadEntity> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                CarDownloadListEntity localDownloadList = getLocalDownloadList();
                CarDownloadListEntity carDownloadListEntity = new CarDownloadListEntity();
                carDownloadListEntity.carList = list;
                if (localDownloadList == null) {
                    setLocalDownloadList(carDownloadListEntity);
                    localDownloadList = carDownloadListEntity;
                } else {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    for (CarDownloadEntity carDownloadEntity : localDownloadList.carList) {
                        linkedHashMap.put(carDownloadEntity.f5835id, carDownloadEntity);
                    }
                    for (CarDownloadEntity carDownloadEntity2 : carDownloadListEntity.carList) {
                        CarDownloadEntity carDownloadEntity3 = (CarDownloadEntity) linkedHashMap.get(carDownloadEntity2.f5835id);
                        if (carDownloadEntity3 != null && TextUtils.equals(carDownloadEntity3.versionCode, carDownloadEntity2.versionCode) && FileUtils.isFile(AppUtils.getLocalCarResAbsoluteFile(carDownloadEntity3.animLocalPath))) {
                            carDownloadEntity2.animLocalPath = carDownloadEntity3.animLocalPath;
                        }
                        linkedHashMap2.put(carDownloadEntity2.f5835id, carDownloadEntity2);
                    }
                    linkedHashMap.clear();
                    localDownloadList.carList.clear();
                    localDownloadList.carList.addAll(linkedHashMap2.values());
                    setLocalDownloadList(localDownloadList);
                }
                ArrayList arrayList = new ArrayList();
                for (CarDownloadEntity carDownloadEntity4 : localDownloadList.carList) {
                    if (!isResValid(carDownloadEntity4)) {
                        arrayList.add(carDownloadEntity4);
                    }
                }
                if (arrayList.size() > 0) {
                    CarDownLoaderImpl.getInstance().startDownLoad(arrayList);
                }
            }
        }
    }

    private CarDownloadListEntity getLocalDownloadList() {
        try {
            return (CarDownloadListEntity) GsonUtils.fromJson(FileUtils.readerByEncrypt(getDownConfigPath()), (Class<Object>) CarDownloadListEntity.class);
        } catch (Exception unused) {
            return null;
        }
    }

    private synchronized void setLocalDownloadList(CarDownloadListEntity carDownloadListEntity) {
        if (carDownloadListEntity == null) {
            return;
        }
        try {
            this.mDownloadListConfig = carDownloadListEntity;
            FileUtils.writByEncrypt(GsonUtils.toJson(carDownloadListEntity), getDownConfigPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isResValid(CarDownloadEntity carDownloadEntity) {
        return !TextUtils.isEmpty(carDownloadEntity.animLocalPath);
    }

    public synchronized void updateDownloadItem(CarDownloadEntity carDownloadEntity) {
        CarDownloadListEntity localDownloadList = getLocalDownloadList();
        Iterator<CarDownloadEntity> it2 = localDownloadList.carList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            CarDownloadEntity next = it2.next();
            if (TextUtils.equals(next.f5835id, carDownloadEntity.f5835id)) {
                next.animLocalPath = carDownloadEntity.animLocalPath;
                break;
            }
        }
        setLocalDownloadList(localDownloadList);
    }

    public CarDownloadEntity getCarItemEntity(String str) {
        CarDownloadEntity carDownloadEntity = null;
        if (this.mDownloadListConfig != null && this.mDownloadListConfig.carList != null && this.mDownloadListConfig.carList.size() > 0) {
            for (CarDownloadEntity carDownloadEntity2 : this.mDownloadListConfig.carList) {
                if (TextUtils.equals(carDownloadEntity2.f5835id, str) && !TextUtils.isEmpty(carDownloadEntity2.animLocalPath)) {
                    if (FileUtils.isExist(carDownloadEntity2.animLocalPath)) {
                        carDownloadEntity = carDownloadEntity2;
                    } else {
                        updateLocalCarItem(str);
                    }
                }
            }
        }
        return carDownloadEntity;
    }

    public File getDownloadFile(String str) {
        return FileUtils.getFileByPath(GiftConfig.INSTANCE.carAnimResRootPath + str);
    }

    private void updateLocalCarItem(String str) {
        if (this.mDownloadListConfig == null || this.mDownloadListConfig.carList == null || this.mDownloadListConfig.carList.size() <= 0) {
            return;
        }
        CarDownloadListEntity carDownloadListEntity = this.mDownloadListConfig;
        Iterator<CarDownloadEntity> it2 = carDownloadListEntity.carList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            CarDownloadEntity next = it2.next();
            if (next.f5835id.equals(str)) {
                next.animLocalPath = "";
                break;
            }
        }
        setLocalDownloadList(carDownloadListEntity);
    }

    private String getDownConfigPath() {
        return GiftConfig.INSTANCE.carAnimResRootPath + GiftConfig.INSTANCE.CAR_CONFIG_NAME;
    }
}
