package com.tomatolive.library.utils;

import android.text.TextUtils;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.LogUtils;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.http.CacheApiRetrofit;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.model.cache.BannerCacheEntity;
import com.tomatolive.library.model.cache.ComponentsCacheEntity;
import com.tomatolive.library.model.cache.VersionCacheEntity;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* loaded from: classes4.dex */
public class CacheUtils {
    private static String TAG = "CacheUtils";
    public static long lastCacheVersionTimeMillis;

    private CacheUtils() {
    }

    public static void updateCacheVersion() {
        long j = lastCacheVersionTimeMillis;
        if (!(j == 0 || ((System.currentTimeMillis() - j) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS >= 10)) {
            return;
        }
        LogUtils.iTag(TAG, "重新请求缓存版本接口");
        CacheApiRetrofit.getInstance().getApiService().getCacheVersionService(UserInfoManager.getInstance().getAppId()).map(new ServerResultFunction<VersionCacheEntity>() { // from class: com.tomatolive.library.utils.CacheUtils.3
        }).onErrorResumeNext(new HttpResultFunction<VersionCacheEntity>() { // from class: com.tomatolive.library.utils.CacheUtils.2
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<VersionCacheEntity>() { // from class: com.tomatolive.library.utils.CacheUtils.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(VersionCacheEntity versionCacheEntity) {
                CacheUtils.lastCacheVersionTimeMillis = System.currentTimeMillis();
                VersionCacheEntity versionCacheEntity2 = (VersionCacheEntity) CacheDiskUtils.getInstance().getParcelable(ConstantUtils.CACHE_DISK_VERSION_KEY, VersionCacheEntity.CREATOR);
                CacheDiskUtils.getInstance().put(ConstantUtils.CACHE_DISK_VERSION_KEY, versionCacheEntity);
                LogUtils.iTag(CacheUtils.TAG, versionCacheEntity.toString());
                if (versionCacheEntity2 == null || versionCacheEntity == null || TextUtils.equals(versionCacheEntity2.getVersion(), versionCacheEntity.getVersion())) {
                    return;
                }
                CacheUtils.cleanCacheDisk(ConstantUtils.CACHE_DISK_NOTICE_KEY, CacheUtils.getBannerTypeByCacheDisk("1"), CacheUtils.getBannerTypeByCacheDisk("5"), CacheUtils.getBannerTypeByCacheDisk("2"), CacheUtils.getBannerTypeByCacheDisk("3"), CacheUtils.getBannerTypeByCacheDisk("7"));
            }
        });
    }

    public static void saveLocalComponentsCache(List<ComponentsEntity> list) {
        ComponentsCacheEntity componentsCacheEntity = new ComponentsCacheEntity();
        componentsCacheEntity.setComponentsList(list);
        CacheDiskUtils.getInstance().put(ConstantUtils.CACHE_DISK_COMPONENTS_KEY, componentsCacheEntity);
    }

    public static boolean isLocalCacheComponents(String str) {
        return getLocalCacheComponentsByGameId(str) != null;
    }

    public static ComponentsEntity getLocalCacheComponentsById(String str) {
        List<ComponentsEntity> localCacheComponentsList;
        if (!TextUtils.isEmpty(str) && (localCacheComponentsList = getLocalCacheComponentsList()) != null && !localCacheComponentsList.isEmpty()) {
            for (ComponentsEntity componentsEntity : localCacheComponentsList) {
                if (componentsEntity != null && TextUtils.equals(componentsEntity.f5837id, str)) {
                    return componentsEntity;
                }
            }
            return null;
        }
        return null;
    }

    public static ComponentsEntity getLocalCacheComponentsByGameId(String str) {
        List<ComponentsEntity> localCacheComponentsList;
        if (!TextUtils.isEmpty(str) && (localCacheComponentsList = getLocalCacheComponentsList()) != null && !localCacheComponentsList.isEmpty()) {
            for (ComponentsEntity componentsEntity : localCacheComponentsList) {
                if (componentsEntity != null && TextUtils.equals(componentsEntity.gameId, str)) {
                    return componentsEntity;
                }
            }
            return null;
        }
        return null;
    }

    public static ComponentsEntity getLocalCacheRecommendComponents() {
        List<ComponentsEntity> localCacheComponentsList = getLocalCacheComponentsList();
        if (localCacheComponentsList == null || localCacheComponentsList.isEmpty()) {
            return null;
        }
        for (ComponentsEntity componentsEntity : localCacheComponentsList) {
            if (componentsEntity != null && componentsEntity.isRecommendComponents()) {
                return componentsEntity;
            }
        }
        return null;
    }

    public static List<ComponentsEntity> getLocalCacheComponentsList() {
        ComponentsCacheEntity componentsCacheEntity = (ComponentsCacheEntity) CacheDiskUtils.getInstance().getParcelable(ConstantUtils.CACHE_DISK_COMPONENTS_KEY, ComponentsCacheEntity.CREATOR);
        if (componentsCacheEntity == null) {
            return null;
        }
        return componentsCacheEntity.getComponentsList();
    }

    public static String getBannerTypeByCacheDisk(String str) {
        return ConstantUtils.CACHE_DISK_BANNER_KEY + str;
    }

    public static boolean isBannerListByCacheDisk(String str) {
        List<BannerEntity> bannerListByCacheDisk = getBannerListByCacheDisk(str);
        return TomatoLiveSDK.getSingleton().isLiveAdvChannel() && bannerListByCacheDisk != null && !bannerListByCacheDisk.isEmpty();
    }

    public static void saveBannerListByCacheDisk(String str, List<BannerEntity> list) {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel() && list != null && !list.isEmpty()) {
            BannerCacheEntity bannerCacheEntity = new BannerCacheEntity();
            bannerCacheEntity.setDataList(list);
            CacheDiskUtils.getInstance().put(getBannerTypeByCacheDisk(str), bannerCacheEntity);
        }
    }

    public static List<BannerEntity> getBannerListByCacheDisk(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BannerCacheEntity bannerCacheEntity = (BannerCacheEntity) CacheDiskUtils.getInstance().getParcelable(getBannerTypeByCacheDisk(str), BannerCacheEntity.CREATOR);
        if (bannerCacheEntity != null) {
            return bannerCacheEntity.getDataList();
        }
        return null;
    }

    public static boolean isLiveNoticeByCacheDisk() {
        return TomatoLiveSDK.getSingleton().isLiveAdvChannel() && getLiveNoticeByCacheDisk() != null;
    }

    public static void saveLiveNoticeByCacheDisk(BannerEntity bannerEntity) {
        if (!TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            return;
        }
        CacheDiskUtils.getInstance().put(ConstantUtils.CACHE_DISK_NOTICE_KEY, bannerEntity);
    }

    public static BannerEntity getLiveNoticeByCacheDisk() {
        return (BannerEntity) CacheDiskUtils.getInstance().getParcelable(ConstantUtils.CACHE_DISK_NOTICE_KEY, BannerEntity.CREATOR);
    }

    public static void cleanCacheDisk(String... strArr) {
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                CacheDiskUtils.getInstance().remove(str);
            }
        }
    }

    public static void clearAllCacheDisk() {
        CacheDiskUtils.getInstance().clear();
    }
}
