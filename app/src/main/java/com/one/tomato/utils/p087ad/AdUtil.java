package com.one.tomato.utils.p087ad;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.mvp.p080ui.newyear.NewYearHtmlActivity;
import com.one.tomato.thirdpart.webapp.WebAppUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.one.tomato.utils.ad.AdUtil */
/* loaded from: classes3.dex */
public class AdUtil {
    private Activity activity;
    private WebAppUtil webAppUtil = new WebAppUtil();

    public AdUtil(@Nullable Activity activity) {
        this.activity = activity;
    }

    public static int getListIndexByWeight(List<AdPage> list) {
        double d;
        double d2;
        double d3;
        if (list == null || list.size() <= 1) {
            return 0;
        }
        Double valueOf = Double.valueOf(0.0d);
        for (AdPage adPage : list) {
            try {
                d3 = adPage.getWeight();
            } catch (Exception e) {
                e.printStackTrace();
                d3 = 0.0d;
            }
            valueOf = Double.valueOf(valueOf.doubleValue() + d3);
        }
        double random = Math.random();
        double d4 = 0.0d;
        double d5 = 0.0d;
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                i = 0;
                break;
            }
            try {
                d = list.get(i).getWeight();
            } catch (Exception e2) {
                e2.printStackTrace();
                d = 0.0d;
            }
            d4 += d / valueOf.doubleValue();
            if (i == 0) {
                d5 = 0.0d;
            } else {
                try {
                    d2 = list.get(i - 1).getWeight();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    d2 = 0.0d;
                }
                d5 += d2 / valueOf.doubleValue();
            }
            if (d5 <= random && random < d4) {
                break;
            }
            i++;
        }
        if (i < list.size()) {
            return i;
        }
        return 0;
    }

    public static PostList getPostList(AdPage adPage) {
        if (adPage == null) {
            return null;
        }
        PostList postList = new PostList();
        postList.setPage(adPage);
        postList.setPostType(9);
        postList.setId(adPage.getAdId());
        postList.setAvatar(adPage.getAdArticleAvatarSec());
        postList.setName(adPage.getAdArticleName());
        postList.setDescription(adPage.getAdArticleContent());
        postList.setSecImageUrl(adPage.getImageUrlSec());
        postList.setSecVideoCover(adPage.getVideoCoverUrlSec());
        postList.setSecVideoUrl(adPage.getVideoUrlSec());
        postList.setRandom(adPage.getRandom());
        postList.setVideoTime(adPage.getVideoTime());
        postList.setSize(adPage.getSize());
        return postList;
    }

    public void clickAd(int i, int i2, String str, String str2, int i3, String str3) {
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 == 4) {
                        NewYearHtmlActivity.Companion.startActivity(this.activity);
                    }
                } else if (TextUtils.isEmpty(str3)) {
                    return;
                } else {
                    String[] split = str3.split(";");
                    if (split.length < 4) {
                        return;
                    }
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("alias", split[0]);
                    hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, split[1]);
                    hashMap.put("host", split[2]);
                    hashMap.put("key", split[3]);
                    HashMap<String, String> hashMap2 = new HashMap<>();
                    if (split.length == 5) {
                        hashMap2.put("appid", split[4]);
                        hashMap2.put("packageId", split[4]);
                    }
                    if (this.webAppUtil == null) {
                        this.webAppUtil = new WebAppUtil();
                    }
                    this.webAppUtil.startWebAppActivity(this.activity, hashMap, hashMap2);
                }
            } else if (TextUtils.isEmpty(str3)) {
                return;
            } else {
                if (str3.endsWith(".apk")) {
                    downloadApk(str3);
                } else {
                    AppUtil.startActionView(i3, str3, this.activity);
                }
            }
        } else if ("1".equals(str)) {
            if (this.activity != null) {
                UserPermissionUtil.getInstance().intentTask(this.activity, str2, 2);
            }
        } else if ("2".equals(str) && this.activity != null) {
            UserPermissionUtil.getInstance().intentTask(this.activity, str2, 6);
        }
        if (i > 0) {
            DataUploadUtil.uploadAD(i, 2);
        }
    }

    private void downloadApk(final String str) {
        Activity activity = this.activity;
        if (activity != null) {
            new RxPermissions(activity).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>(this) { // from class: com.one.tomato.utils.ad.AdUtil.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Boolean bool) {
                    if (bool.booleanValue()) {
                        AdApkDownloadUtil.getInstance().downloadApk(str);
                    } else {
                        ToastUtil.showCenterToast((int) R.string.permission_storage);
                    }
                }
            });
        }
    }
}
