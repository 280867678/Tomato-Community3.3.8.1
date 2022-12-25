package com.one.tomato.mvp.p080ui.post.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.p079db.ShareParamsBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections._Collections;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathJVM;
import kotlin.random.Random;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;
import kotlin.text.Regex;
import kotlin.text.StringsJVM;
import kotlin.text.StringsKt__StringsKt;
import org.slf4j.Marker;

/* compiled from: PostUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils */
/* loaded from: classes3.dex */
public final class PostUtils {
    private static LinearLayout.LayoutParams gridParams2;
    private static LinearLayout.LayoutParams gridParams3;
    private static boolean is4GVideoPlay;
    private static PostList postListUpAD;
    private static int screenWidth;
    private static LinearLayout.LayoutParams singleHeightParams;
    private static LinearLayout.LayoutParams singleWidthParams;
    public static final PostUtils INSTANCE = new PostUtils();
    private static final int[] colorBackGround = {R.color.color_FFECF1, R.color.color_ECF4FF, R.color.color_FFF6EC, R.color.color_ECFAFF};
    private static ArrayList<BackObserver> backObserver = new ArrayList<>();

    /* compiled from: PostUtils.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils$BackObserver */
    /* loaded from: classes3.dex */
    public interface BackObserver {
        void update(Boolean bool);
    }

    static {
        new RecyclerView.RecycledViewPool();
        new RecyclerView.RecycledViewPool();
    }

    private PostUtils() {
    }

    public final boolean is4GVideoPlay() {
        return is4GVideoPlay;
    }

    public final void set4GVideoPlay(boolean z) {
        is4GVideoPlay = z;
    }

    public final int getScreenWidth() {
        return screenWidth;
    }

    public final LinearLayout.LayoutParams getSingleWidthParams() {
        return singleWidthParams;
    }

    public final LinearLayout.LayoutParams getSingleHeightParams() {
        return singleHeightParams;
    }

    public final LinearLayout.LayoutParams getGridParams2() {
        return gridParams2;
    }

    public final LinearLayout.LayoutParams getGridParams3() {
        return gridParams3;
    }

    public final void updatePostBrowse(PostList postList) {
        if (Intrinsics.areEqual(postListUpAD, postList)) {
            return;
        }
        postListUpAD = postList;
        if (postList == null) {
            return;
        }
        DataUploadUtil.uploadPostBrowse(postList.getId(), DBUtil.getMemberId());
    }

    public final int getBackGround() {
        int random;
        random = _Ranges.random(new Ranges(0, 3), Random.Default);
        int[] iArr = colorBackGround;
        if (random < iArr.length) {
            return iArr[random];
        }
        return iArr[0];
    }

    public final void addBackObserver(BackObserver backObserver2) {
        if (backObserver2 != null) {
            backObserver.add(backObserver2);
        }
    }

    public final boolean deleteBackObserver(BackObserver backObserver2) {
        if (backObserver2 == null || !backObserver.contains(backObserver2)) {
            return false;
        }
        backObserver.remove(backObserver2);
        return true;
    }

    public final boolean notifyBackObserver(boolean z) {
        if (backObserver.size() > 0) {
            Iterator<BackObserver> it2 = backObserver.iterator();
            while (it2.hasNext()) {
                it2.next().update(Boolean.valueOf(z));
            }
            return true;
        }
        return false;
    }

    public final void blurBitmap(final Bitmap bitmap, final Function1<? super Bitmap, Unit> outBitmap) {
        Intrinsics.checkParameterIsNotNull(outBitmap, "outBitmap");
        if (bitmap != null) {
            Observable.create(new ObservableOnSubscribe<T>(bitmap, outBitmap) { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$blurBitmap$$inlined$let$lambda$1
                final /* synthetic */ Bitmap $bitmap$inlined;

                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter<Bitmap> it2) {
                    Intrinsics.checkParameterIsNotNull(it2, "it");
                    Bitmap createBitmap = Bitmap.createBitmap(this.$bitmap$inlined.getWidth(), this.$bitmap$inlined.getHeight(), Bitmap.Config.ARGB_8888);
                    RenderScript create = RenderScript.create(BaseApplication.getApplication());
                    ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                    Allocation createFromBitmap = Allocation.createFromBitmap(create, this.$bitmap$inlined);
                    Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
                    create2.setRadius(25.0f);
                    create2.setInput(createFromBitmap);
                    create2.forEach(createFromBitmap2);
                    createFromBitmap2.copyTo(createBitmap);
                    this.$bitmap$inlined.recycle();
                    create.destroy();
                    it2.onNext(createBitmap);
                }
            }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>(bitmap, outBitmap) { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$blurBitmap$$inlined$let$lambda$2
                final /* synthetic */ Function1 $outBitmap$inlined;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$outBitmap$inlined = outBitmap;
                }

                @Override // io.reactivex.functions.Consumer
                public final void accept(Bitmap bitmap2) {
                    if (bitmap2 instanceof Bitmap) {
                        this.$outBitmap$inlined.mo6794invoke(bitmap2);
                    }
                }
            });
        }
    }

    public final void preloadListVideo(ArrayList<PostList> arrayList) {
        if (arrayList != null) {
            M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.getInstance();
            Iterator<PostList> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                PostList bean = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(bean, "bean");
                if (bean.getPostType() == 2 || bean.getPostType() == 4) {
                    m3U8DownloadManager.preDownload(bean.getSecVideoUrl(), bean.getTitle(), String.valueOf(bean.getId()), bean.getVideoView());
                }
            }
        }
    }

    public final boolean isWidthVideo(String str) {
        if (!TextUtils.isEmpty(str)) {
            List split$default = str != null ? StringsKt__StringsKt.split$default(str, new String[]{Marker.ANY_MARKER}, false, 0, 6, null) : null;
            if (split$default != null && split$default.size() == 2) {
                return Integer.parseInt((String) split$default.get(1)) <= Integer.parseInt((String) split$default.get(0));
            }
        }
        return true;
    }

    public final boolean isLongPaPaVideo(String str) {
        boolean contains$default;
        List split$default;
        if (!TextUtils.isEmpty(str) && str != null) {
            contains$default = StringsKt__StringsKt.contains$default(str, ":", false, 2, null);
            if (contains$default) {
                split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, false, 0, 6, null);
                if (Integer.parseInt((String) split$default.get(split$default.size() - 1)) + (Integer.parseInt((String) split$default.get(split$default.size() - 2)) * 60) > 30) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isPost10Video(String str) {
        boolean contains$default;
        List split$default;
        if (!TextUtils.isEmpty(str) && str != null) {
            contains$default = StringsKt__StringsKt.contains$default(str, ":", false, 2, null);
            if (contains$default) {
                split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, false, 0, 6, null);
                if (Integer.parseInt((String) split$default.get(split$default.size() - 1)) + (Integer.parseInt((String) split$default.get(split$default.size() - 2)) * 60) > 600) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void getWidthAndHegiht() {
        screenWidth = (int) (DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(30.0f));
        if (singleWidthParams == null) {
            singleWidthParams = new LinearLayout.LayoutParams(screenWidth, (int) DisplayMetricsUtils.dp2px(200.0f));
            LinearLayout.LayoutParams layoutParams = singleWidthParams;
            if (layoutParams != null) {
                layoutParams.leftMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams2 = singleWidthParams;
            if (layoutParams2 != null) {
                layoutParams2.rightMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams3 = singleWidthParams;
            if (layoutParams3 != null) {
                layoutParams3.topMargin = (int) DisplayMetricsUtils.dp2px(8.0f);
            }
            LinearLayout.LayoutParams layoutParams4 = singleWidthParams;
            if (layoutParams4 != null) {
                layoutParams4.bottomMargin = (int) DisplayMetricsUtils.dp2px(10.0f);
            }
        }
        if (singleHeightParams == null) {
            singleHeightParams = new LinearLayout.LayoutParams(screenWidth / 2, (int) DisplayMetricsUtils.dp2px(230.0f));
            LinearLayout.LayoutParams layoutParams5 = singleHeightParams;
            if (layoutParams5 != null) {
                layoutParams5.leftMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams6 = singleHeightParams;
            if (layoutParams6 != null) {
                layoutParams6.rightMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams7 = singleHeightParams;
            if (layoutParams7 != null) {
                layoutParams7.topMargin = (int) DisplayMetricsUtils.dp2px(8.0f);
            }
            LinearLayout.LayoutParams layoutParams8 = singleHeightParams;
            if (layoutParams8 != null) {
                layoutParams8.bottomMargin = (int) DisplayMetricsUtils.dp2px(10.0f);
            }
        }
        if (gridParams2 == null) {
            gridParams2 = new LinearLayout.LayoutParams((screenWidth * 2) / 3, -2);
            LinearLayout.LayoutParams layoutParams9 = gridParams2;
            if (layoutParams9 != null) {
                layoutParams9.leftMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams10 = gridParams2;
            if (layoutParams10 != null) {
                layoutParams10.rightMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams11 = gridParams2;
            if (layoutParams11 != null) {
                layoutParams11.topMargin = (int) DisplayMetricsUtils.dp2px(8.0f);
            }
            LinearLayout.LayoutParams layoutParams12 = gridParams2;
            if (layoutParams12 != null) {
                layoutParams12.bottomMargin = (int) DisplayMetricsUtils.dp2px(10.0f);
            }
        }
        if (gridParams3 == null) {
            gridParams3 = new LinearLayout.LayoutParams(screenWidth, -2);
            LinearLayout.LayoutParams layoutParams13 = gridParams3;
            if (layoutParams13 != null) {
                layoutParams13.leftMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams14 = gridParams3;
            if (layoutParams14 != null) {
                layoutParams14.rightMargin = (int) DisplayMetricsUtils.dp2px(14.0f);
            }
            LinearLayout.LayoutParams layoutParams15 = gridParams3;
            if (layoutParams15 != null) {
                layoutParams15.topMargin = (int) DisplayMetricsUtils.dp2px(8.0f);
            }
            LinearLayout.LayoutParams layoutParams16 = gridParams3;
            if (layoutParams16 == null) {
                return;
            }
            layoutParams16.bottomMargin = (int) DisplayMetricsUtils.dp2px(10.0f);
        }
    }

    public final ArrayList<ImageBean> createImageBeanList(PostList postList) {
        String[] strArr;
        List emptyList;
        boolean z;
        ArrayList<ImageBean> arrayList = new ArrayList<>();
        if (postList == null) {
            return arrayList;
        }
        if (!TextUtils.isEmpty(postList.getSecImageUrl())) {
            String secImageUrl = postList.getSecImageUrl();
            Intrinsics.checkExpressionValueIsNotNull(secImageUrl, "postList.secImageUrl");
            List<String> split = new Regex(";").split(secImageUrl, 0);
            if (!split.isEmpty()) {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        emptyList = _Collections.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            emptyList = CollectionsKt__CollectionsKt.emptyList();
            Object[] array = emptyList.toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            strArr = (String[]) array;
        } else {
            strArr = new String[0];
        }
        List<String> arrayList2 = new ArrayList<>();
        if (!TextUtils.isEmpty(postList.getSize())) {
            String size = postList.getSize();
            Intrinsics.checkExpressionValueIsNotNull(size, "postList.size");
            arrayList2 = new Regex(";").split(size, 0);
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            ImageBean imageBean = new ImageBean();
            imageBean.setSecret(true);
            imageBean.setAd(postList.isAd());
            imageBean.setImage(str);
            try {
                if (arrayList2.isEmpty()) {
                    imageBean.setWidth(0);
                    imageBean.setHeight(0);
                    imageBean.setImageType(0);
                } else {
                    String str2 = arrayList2.get(i);
                    imageBean.setWidth(Integer.parseInt(new Regex("\\*").split(str2, 0).get(0)));
                    imageBean.setHeight(Integer.parseInt(new Regex("\\*").split(str2, 0).get(1)));
                }
            } catch (Exception unused) {
                imageBean.setWidth(0);
                imageBean.setHeight(0);
            }
            arrayList.add(imageBean);
        }
        return arrayList;
    }

    public final synchronized ImageBean createImageBean(PostList postList) {
        List emptyList;
        List emptyList2;
        boolean z;
        boolean z2;
        if (postList == null) {
            return null;
        }
        String secVideoCover = postList.getSecVideoCover();
        ImageBean imageBean = new ImageBean();
        imageBean.setSecret(true);
        imageBean.setImage(secVideoCover);
        try {
            String imgSize = postList.getSize();
            if (imgSize.length() == 0) {
                imageBean.setWidth(0);
                imageBean.setHeight(0);
                imageBean.setImageType(0);
            } else {
                Intrinsics.checkExpressionValueIsNotNull(imgSize, "imgSize");
                List<String> split = new Regex("\\*").split(imgSize, 0);
                if (!split.isEmpty()) {
                    ListIterator<String> listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() == 0) {
                            z2 = true;
                            continue;
                        } else {
                            z2 = false;
                            continue;
                        }
                        if (!z2) {
                            emptyList = _Collections.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                emptyList = CollectionsKt__CollectionsKt.emptyList();
                Object[] array = emptyList.toArray(new String[0]);
                if (array != null) {
                    Integer imgWidth = Integer.valueOf(((String[]) array)[0]);
                    List<String> split2 = new Regex("\\*").split(imgSize, 0);
                    if (!split2.isEmpty()) {
                        ListIterator<String> listIterator2 = split2.listIterator(split2.size());
                        while (listIterator2.hasPrevious()) {
                            if (listIterator2.previous().length() == 0) {
                                z = true;
                                continue;
                            } else {
                                z = false;
                                continue;
                            }
                            if (!z) {
                                emptyList2 = _Collections.take(split2, listIterator2.nextIndex() + 1);
                                break;
                            }
                        }
                    }
                    emptyList2 = CollectionsKt__CollectionsKt.emptyList();
                    Object[] array2 = emptyList2.toArray(new String[0]);
                    if (array2 != null) {
                        Integer imgHeight = Integer.valueOf(((String[]) array2)[1]);
                        Intrinsics.checkExpressionValueIsNotNull(imgWidth, "imgWidth");
                        imageBean.setWidth(imgWidth.intValue());
                        Intrinsics.checkExpressionValueIsNotNull(imgHeight, "imgHeight");
                        imageBean.setHeight(imgHeight.intValue());
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            imageBean.setWidth(0);
            imageBean.setHeight(0);
        }
        return imageBean;
    }

    public final int getPicHeight(String str) {
        List emptyList;
        boolean z;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            if (str == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            List<String> split = new Regex("\\*").split(str, 0);
            if (!split.isEmpty()) {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        emptyList = _Collections.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            emptyList = CollectionsKt__CollectionsKt.emptyList();
            Object[] array = emptyList.toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            Integer valueOf = Integer.valueOf(((String[]) array)[1]);
            Intrinsics.checkExpressionValueIsNotNull(valueOf, "Integer.valueOf(imgSize!…ty() }.toTypedArray()[1])");
            return valueOf.intValue();
        } catch (Exception unused) {
            return 0;
        }
    }

    public final int getPicWidth(String str) {
        List emptyList;
        boolean z;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            if (str == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            List<String> split = new Regex("\\*").split(str, 0);
            if (!split.isEmpty()) {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        emptyList = _Collections.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            emptyList = CollectionsKt__CollectionsKt.emptyList();
            Object[] array = emptyList.toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            Integer valueOf = Integer.valueOf(((String[]) array)[0]);
            Intrinsics.checkExpressionValueIsNotNull(valueOf, "Integer.valueOf(imgSize!…ty() }.toTypedArray()[0])");
            return valueOf.intValue();
        } catch (Exception unused) {
            return 0;
        }
    }

    public final String pullPostImageFirstSize(PostList postList) {
        List split$default;
        if (!TextUtils.isEmpty(postList != null ? postList.getSize() : null)) {
            if (postList != null && postList.getPicNum() == 1) {
                String size = postList.getSize();
                Intrinsics.checkExpressionValueIsNotNull(size, "postList.size");
                return size;
            } else if (postList != null) {
                String size2 = postList.getSize();
                Intrinsics.checkExpressionValueIsNotNull(size2, "postList!!.size");
                split$default = StringsKt__StringsKt.split$default(size2, new String[]{";"}, false, 0, 6, null);
                return split$default.isEmpty() ^ true ? (String) split$default.get(0) : "";
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        return "";
    }

    public final int calculationItemMaxHeight(int i, int i2) {
        int roundToInt;
        int width = (int) DisplayMetricsUtils.getWidth();
        int width2 = (int) DisplayMetricsUtils.getWidth();
        if (i == 0 || i2 == 0) {
            return width;
        }
        double d = width2;
        double d2 = i2 * (d / i);
        if (d2 - d >= 0) {
            return width;
        }
        roundToInt = MathJVM.roundToInt(d2);
        return roundToInt;
    }

    public final int calculationItemMaxHeightForPostDetail(int i, int i2) {
        int roundToInt;
        int height = ((int) DisplayMetricsUtils.getHeight()) - ((int) DisplayMetricsUtils.dp2px(53.0f));
        int width = (int) DisplayMetricsUtils.getWidth();
        if (i == 0 || i2 == 0) {
            return height;
        }
        double d = i2 * (width / i);
        if (d - height >= 0) {
            return height;
        }
        roundToInt = MathJVM.roundToInt(d);
        return roundToInt;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void copyShare(String id, int i) {
        String postShare;
        String str;
        BaseApplication baseApplication;
        String domainServer;
        String str2;
        boolean contains$default;
        boolean contains$default2;
        boolean contains$default3;
        Intrinsics.checkParameterIsNotNull(id, "id");
        ShareParamsBean shareParams = DBUtil.getShareParams();
        UserInfo userInfo = DBUtil.getUserInfo();
        if (i == 1) {
            Intrinsics.checkExpressionValueIsNotNull(shareParams, "shareParams");
            postShare = shareParams.getPostShare();
        } else if (i == 2) {
            Intrinsics.checkExpressionValueIsNotNull(shareParams, "shareParams");
            postShare = shareParams.getVideoShare();
        } else if (i == 3) {
            Intrinsics.checkExpressionValueIsNotNull(shareParams, "shareParams");
            postShare = shareParams.getShareAddress();
        } else {
            str = "";
            baseApplication = BaseApplication.instance;
            Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
            if (!baseApplication.isChess() && 3 == i) {
                DomainServer domainServer2 = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
                domainServer = domainServer2.getWebsiteUrl();
            } else {
                DomainServer domainServer3 = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer3, "DomainServer.getInstance()");
                domainServer = domainServer3.getShareUrl();
            }
            if (str == null) {
                contains$default = StringsKt__StringsKt.contains$default(str, "${inviteCode}$", false, 2, null);
                if (contains$default) {
                    Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
                    if (TextUtils.isEmpty(userInfo.getInviteCode())) {
                        userInfo.setInviteCode("--");
                    }
                    String inviteCode = userInfo.getInviteCode();
                    Intrinsics.checkExpressionValueIsNotNull(inviteCode, "userInfo.inviteCode");
                    str = StringsJVM.replace$default(str, "${inviteCode}$", inviteCode, false, 4, null);
                }
                String str3 = str;
                contains$default2 = StringsKt__StringsKt.contains$default(str3, "${shareDomain}$", false, 2, null);
                if (contains$default2) {
                    Intrinsics.checkExpressionValueIsNotNull(domainServer, "domainServer");
                    str2 = StringsJVM.replace$default(str3, "${shareDomain}$", domainServer, false, 4, null);
                } else {
                    str2 = str3;
                }
                contains$default3 = StringsKt__StringsKt.contains$default(str2, "${id}$", false, 2, null);
                if (contains$default3) {
                    str2 = StringsJVM.replace$default(str2, "${id}$", id, false, 4, null);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(domainServer);
                sb.append("?inviteCode=");
                Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
                sb.append(userInfo.getInviteCode());
                sb.append("&id=");
                sb.append(id);
                str2 = AppUtil.getString(R.string.post_share_content) + sb.toString();
            }
            if (3 != i) {
                AppUtil.copyShareText(str2, "");
                return;
            }
            AppUtil.copyShareText(str2, AppUtil.getString(R.string.post_copy_share));
            DataUploadUtil.uploadPostShare(id);
            return;
        }
        str = postShare;
        baseApplication = BaseApplication.instance;
        Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
        if (!baseApplication.isChess()) {
        }
        DomainServer domainServer32 = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer32, "DomainServer.getInstance()");
        domainServer = domainServer32.getShareUrl();
        if (str == null) {
        }
        if (3 != i) {
        }
    }

    public final void showImageNeedPayDialog(Context mContext, String str, String str2, int i, Functions<Unit> confimCallBack, Functions<Unit> cancleCallBack) {
        Intrinsics.checkParameterIsNotNull(mContext, "mContext");
        Intrinsics.checkParameterIsNotNull(confimCallBack, "confimCallBack");
        Intrinsics.checkParameterIsNotNull(cancleCallBack, "cancleCallBack");
        requestBalance(mContext, new PostUtils$showImageNeedPayDialog$1(mContext, i, cancleCallBack, str2, str, confimCallBack), PostUtils$showImageNeedPayDialog$2.INSTANCE);
    }

    public final void requestBalance(final Context context, final Function1<? super String, Unit> function1, final Functions<Unit> functions) {
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$requestBalance$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                Context context2 = context;
                if (context2 instanceof BaseActivity) {
                    ((BaseActivity) context2).showWaitingDialog();
                }
                Context context3 = context;
                if (context3 instanceof MvpBaseActivity) {
                    ((MvpBaseActivity) context3).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$requestBalance$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount rechargeAccount) {
                Function1 function12;
                Context context2 = context;
                if (context2 instanceof BaseActivity) {
                    ((BaseActivity) context2).hideWaitingDialog();
                }
                Context context3 = context;
                if (context3 instanceof MvpBaseActivity) {
                    ((MvpBaseActivity) context3).hideWaitingDialog();
                }
                if (rechargeAccount == null || (function12 = function1) == null) {
                    return;
                }
                Unit unit = (Unit) function12.mo6794invoke(String.valueOf(rechargeAccount.balance));
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                Context context2 = context;
                if (context2 instanceof BaseActivity) {
                    ((BaseActivity) context2).hideWaitingDialog();
                }
                Context context3 = context;
                if (context3 instanceof MvpBaseActivity) {
                    ((MvpBaseActivity) context3).hideWaitingDialog();
                }
                Functions functions2 = functions;
                if (functions2 != null) {
                    Unit unit = (Unit) functions2.mo6822invoke();
                }
            }
        });
    }
}
