package com.one.tomato.utils;

import android.content.Context;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.dialog.LoadingDialog;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.ChessTypeSubBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.game.view.GameWebActivity;
import com.one.tomato.thirdpart.webapp.WebAppUtil;
import com.one.tomato.utils.GameUtils;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.LogConstants;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections._Collections;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* compiled from: GameUtils.kt */
/* loaded from: classes3.dex */
public final class GameUtils {
    private static Function1<? super SubGamesBean, Unit> clickWebAppListener;
    private static LoadingDialog loadingDialog;
    private static WebAppUtil webAppUtil;
    public static final GameUtils INSTANCE = new GameUtils();
    private static final String GAME_BINGBING_TOKEN = GAME_BINGBING_TOKEN;
    private static final String GAME_BINGBING_TOKEN = GAME_BINGBING_TOKEN;
    private static final String GAME_DASHENG_WEBAPP = GAME_DASHENG_WEBAPP;
    private static final String GAME_DASHENG_WEBAPP = GAME_DASHENG_WEBAPP;
    private static final String GAME_DASHENG_H5 = GAME_DASHENG_H5;
    private static final String GAME_DASHENG_H5 = GAME_DASHENG_H5;

    /* compiled from: GameUtils.kt */
    /* loaded from: classes3.dex */
    public interface RequestGameResponseListener {
        void requestFail();

        void requestGameBingBingToken(GameBingBingToken gameBingBingToken);

        void requestGameDaShengH5(GameDaShengH5 gameDaShengH5);

        void requestGameDaShengWebapp(GameDaShengWebapp gameDaShengWebapp);
    }

    public final void gameToPurse(SubGamesBean gamesBean) {
        Intrinsics.checkParameterIsNotNull(gamesBean, "gamesBean");
    }

    public final void purseToGame(SubGamesBean gamesBean) {
        Intrinsics.checkParameterIsNotNull(gamesBean, "gamesBean");
    }

    private GameUtils() {
    }

    public final String getGAME_BINGBING_TOKEN() {
        return GAME_BINGBING_TOKEN;
    }

    public final String getGAME_DASHENG_H5() {
        return GAME_DASHENG_H5;
    }

    public final void saveGameBean(SubGamesBean bean) {
        Intrinsics.checkParameterIsNotNull(bean, "bean");
        ArrayList<SubGamesBean> gameBean = DBUtil.getGameBean();
        if (gameBean.size() > 0) {
            Iterator<SubGamesBean> it2 = gameBean.iterator();
            while (it2.hasNext()) {
                SubGamesBean it3 = it2.next();
                int gameId = bean.getGameId();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                if (gameId == it3.getGameId()) {
                    return;
                }
            }
        }
        bean.setMemberId(String.valueOf(DBUtil.getMemberId()));
        if (gameBean.size() < 6) {
            if (gameBean.size() == 0) {
                gameBean.add(bean);
            } else {
                gameBean.add(0, bean);
            }
        } else {
            gameBean.remove(5);
            gameBean.add(0, bean);
        }
        DBUtil.saveGameBean(gameBean);
    }

    public final ArrayList<SubGamesBean> getGameBean() {
        ArrayList<SubGamesBean> gameBean = DBUtil.getGameBean();
        Intrinsics.checkExpressionValueIsNotNull(gameBean, "gameBean");
        return gameBean;
    }

    public final void deleteGameBean() {
        DBUtil.deleteGameBean();
    }

    public final void clickGame(final Context context, final SubGamesBean gamesBean) {
        String adType;
        String str;
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(gamesBean, "gamesBean");
        if (!TextUtils.isEmpty(gamesBean.getAdLink()) && (adType = gamesBean.getAdType()) != null) {
            int hashCode = adType.hashCode();
            if (hashCode == 50) {
                if (!adType.equals("2")) {
                    return;
                }
                AppUtil.startActionView(gamesBean.getOpenType(), gamesBean.getAdLink(), context);
                return;
            }
            if (hashCode != 51) {
                if (hashCode != 56 || !adType.equals("8")) {
                    return;
                }
            } else if (!adType.equals("3")) {
                return;
            }
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(context);
            }
            LoadingDialog loadingDialog2 = loadingDialog;
            Boolean valueOf = loadingDialog2 != null ? Boolean.valueOf(loadingDialog2.isShowing()) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            if (!valueOf.booleanValue()) {
                try {
                    LoadingDialog loadingDialog3 = loadingDialog;
                    if (loadingDialog3 != null) {
                        loadingDialog3.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ("1".equals(gamesBean.getAdBrandId())) {
                str = GAME_BINGBING_TOKEN;
            } else {
                str = GAME_DASHENG_WEBAPP;
                if (Intrinsics.areEqual("8", gamesBean.getAdType())) {
                    str = GAME_DASHENG_H5;
                }
            }
            String adBrandId = gamesBean.getAdBrandId();
            Intrinsics.checkExpressionValueIsNotNull(adBrandId, "gamesBean.adBrandId");
            String adGameId = gamesBean.getAdGameId();
            Intrinsics.checkExpressionValueIsNotNull(adGameId, "gamesBean.adGameId");
            requestGameResponse(str, adBrandId, adGameId, gamesBean.getAdType(), new RequestGameResponseListener() { // from class: com.one.tomato.utils.GameUtils$clickGame$1
                @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
                public void requestGameBingBingToken(GameBingBingToken gameBingBingToken) {
                    LoadingDialog loadingDialog4;
                    List emptyList;
                    Function1 function1;
                    WebAppUtil webAppUtil2;
                    WebAppUtil webAppUtil3;
                    WebAppUtil webAppUtil4;
                    boolean z;
                    Intrinsics.checkParameterIsNotNull(gameBingBingToken, "gameBingBingToken");
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    loadingDialog4 = GameUtils.loadingDialog;
                    if (loadingDialog4 != null) {
                        loadingDialog4.dismiss();
                    }
                    String adLink = SubGamesBean.this.getAdLink();
                    Intrinsics.checkExpressionValueIsNotNull(adLink, "gamesBean.adLink");
                    boolean z2 = false;
                    List<String> split = new Regex(";").split(adLink, 0);
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
                    if (array != null) {
                        String[] strArr = (String[]) array;
                        if (strArr.length < 4) {
                            return;
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdLogoType(), "1")) {
                            GameUtils.INSTANCE.saveGameBean(SubGamesBean.this);
                        }
                        GameUtils gameUtils2 = GameUtils.INSTANCE;
                        function1 = GameUtils.clickWebAppListener;
                        if (function1 != null) {
                            Unit unit = (Unit) function1.mo6794invoke(SubGamesBean.this);
                        }
                        GameUtils.INSTANCE.purseToGame(SubGamesBean.this);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("alias", strArr[0]);
                        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, strArr[1]);
                        hashMap.put("host", strArr[2]);
                        hashMap.put("key", strArr[3]);
                        HashMap<String, String> hashMap2 = new HashMap<>();
                        String adGameId2 = SubGamesBean.this.getAdGameId();
                        Intrinsics.checkExpressionValueIsNotNull(adGameId2, "gamesBean.adGameId");
                        hashMap2.put("game_id", adGameId2);
                        String adGameFlag = SubGamesBean.this.getAdGameFlag();
                        if (adGameFlag == null) {
                            adGameFlag = "";
                        }
                        hashMap2.put("game_path", adGameFlag);
                        String str2 = gameBingBingToken.gameChannel;
                        Intrinsics.checkExpressionValueIsNotNull(str2, "gameBingBingToken.gameChannel");
                        hashMap2.put("appid", str2);
                        String str3 = gameBingBingToken.platformId;
                        Intrinsics.checkExpressionValueIsNotNull(str3, "gameBingBingToken.platformId");
                        hashMap2.put("platform", str3);
                        String str4 = gameBingBingToken.token;
                        Intrinsics.checkExpressionValueIsNotNull(str4, "gameBingBingToken.token");
                        hashMap2.put("token", str4);
                        hashMap2.put(LogConstants.ACCOUNT, String.valueOf(DBUtil.getMemberId()));
                        hashMap2.put("tourist", "0");
                        GameUtils gameUtils3 = GameUtils.INSTANCE;
                        webAppUtil2 = GameUtils.webAppUtil;
                        if (webAppUtil2 == null) {
                            GameUtils gameUtils4 = GameUtils.INSTANCE;
                            GameUtils.webAppUtil = new WebAppUtil();
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdType(), "3")) {
                            GameUtils gameUtils5 = GameUtils.INSTANCE;
                            webAppUtil4 = GameUtils.webAppUtil;
                            if (webAppUtil4 != null) {
                                webAppUtil4.startWebAppActivity(context, hashMap, hashMap2);
                            }
                        } else if (Intrinsics.areEqual(SubGamesBean.this.getAdType(), "8")) {
                            String str5 = SubGamesBean.this.getAdGameLink() + "/?game_id=" + SubGamesBean.this.getAdGameId() + "&appid=" + gameBingBingToken.gameChannel + "&platform=" + gameBingBingToken.platformId + "&account=" + String.valueOf(DBUtil.getMemberId()) + "&token=" + URLEncoder.encode(gameBingBingToken.token, EncryptUtil.CHARSET) + "&tourist=0";
                            GameUtils gameUtils6 = GameUtils.INSTANCE;
                            webAppUtil3 = GameUtils.webAppUtil;
                            if (webAppUtil3 != null) {
                                Context context2 = context;
                                if (SubGamesBean.this.getAdGameDirection() == 1) {
                                    z2 = true;
                                }
                                webAppUtil3.startWebGameActivity(context2, hashMap, z2, str5);
                            }
                        }
                        String adGameId3 = SubGamesBean.this.getAdGameId();
                        Intrinsics.checkExpressionValueIsNotNull(adGameId3, "gamesBean.adGameId");
                        DataUploadUtil.uploadAD(Integer.parseInt(adGameId3), 2);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }

                @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
                public void requestGameDaShengWebapp(GameDaShengWebapp gameDaShengWebapp) {
                    LoadingDialog loadingDialog4;
                    List emptyList;
                    WebAppUtil webAppUtil2;
                    WebAppUtil webAppUtil3;
                    boolean z;
                    Intrinsics.checkParameterIsNotNull(gameDaShengWebapp, "gameDaShengWebapp");
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    loadingDialog4 = GameUtils.loadingDialog;
                    if (loadingDialog4 != null) {
                        loadingDialog4.dismiss();
                    }
                    String adLink = SubGamesBean.this.getAdLink();
                    Intrinsics.checkExpressionValueIsNotNull(adLink, "gamesBean.adLink");
                    List<String> split = new Regex(";").split(adLink, 0);
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
                    if (array != null) {
                        String[] strArr = (String[]) array;
                        if (strArr.length < 4) {
                            return;
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdLogoType(), "1")) {
                            GameUtils.INSTANCE.saveGameBean(SubGamesBean.this);
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("alias", strArr[0]);
                        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, strArr[1]);
                        hashMap.put("host", strArr[2]);
                        hashMap.put("key", strArr[3]);
                        HashMap<String, String> hashMap2 = new HashMap<>();
                        String str2 = gameDaShengWebapp.params;
                        Intrinsics.checkExpressionValueIsNotNull(str2, "gameDaShengWebapp.params");
                        hashMap2.put("params", str2);
                        GameUtils gameUtils2 = GameUtils.INSTANCE;
                        webAppUtil2 = GameUtils.webAppUtil;
                        if (webAppUtil2 == null) {
                            GameUtils gameUtils3 = GameUtils.INSTANCE;
                            GameUtils.webAppUtil = new WebAppUtil();
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdType(), "3")) {
                            GameUtils gameUtils4 = GameUtils.INSTANCE;
                            webAppUtil3 = GameUtils.webAppUtil;
                            if (webAppUtil3 != null) {
                                webAppUtil3.startWebAppActivity(context, hashMap, hashMap2);
                            }
                        }
                        String adGameId2 = SubGamesBean.this.getAdGameId();
                        Intrinsics.checkExpressionValueIsNotNull(adGameId2, "gamesBean.adGameId");
                        DataUploadUtil.uploadAD(Integer.parseInt(adGameId2), 2);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }

                @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
                public void requestGameDaShengH5(GameDaShengH5 gameDaShengH5) {
                    LoadingDialog loadingDialog4;
                    List emptyList;
                    WebAppUtil webAppUtil2;
                    WebAppUtil webAppUtil3;
                    boolean z;
                    Intrinsics.checkParameterIsNotNull(gameDaShengH5, "gameDaShengH5");
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    loadingDialog4 = GameUtils.loadingDialog;
                    if (loadingDialog4 != null) {
                        loadingDialog4.dismiss();
                    }
                    String adLink = SubGamesBean.this.getAdLink();
                    Intrinsics.checkExpressionValueIsNotNull(adLink, "gamesBean.adLink");
                    boolean z2 = false;
                    List<String> split = new Regex(";").split(adLink, 0);
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
                    if (array != null) {
                        String[] strArr = (String[]) array;
                        if (strArr.length < 4) {
                            return;
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdLogoType(), "1")) {
                            GameUtils.INSTANCE.saveGameBean(SubGamesBean.this);
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("alias", strArr[0]);
                        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, strArr[1]);
                        hashMap.put("host", strArr[2]);
                        hashMap.put("key", strArr[3]);
                        GameUtils gameUtils2 = GameUtils.INSTANCE;
                        webAppUtil2 = GameUtils.webAppUtil;
                        if (webAppUtil2 == null) {
                            GameUtils gameUtils3 = GameUtils.INSTANCE;
                            GameUtils.webAppUtil = new WebAppUtil();
                        }
                        if (Intrinsics.areEqual(SubGamesBean.this.getAdType(), "8")) {
                            GameUtils gameUtils4 = GameUtils.INSTANCE;
                            webAppUtil3 = GameUtils.webAppUtil;
                            if (webAppUtil3 != null) {
                                Context context2 = context;
                                if (SubGamesBean.this.getAdGameDirection() == 1) {
                                    z2 = true;
                                }
                                webAppUtil3.startWebGameActivity(context2, hashMap, z2, gameDaShengH5.url);
                            }
                        }
                        String adGameId2 = SubGamesBean.this.getAdGameId();
                        Intrinsics.checkExpressionValueIsNotNull(adGameId2, "gamesBean.adGameId");
                        DataUploadUtil.uploadAD(Integer.parseInt(adGameId2), 2);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }

                @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
                public void requestFail() {
                    LoadingDialog loadingDialog4;
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    loadingDialog4 = GameUtils.loadingDialog;
                    if (loadingDialog4 != null) {
                        loadingDialog4.dismiss();
                    }
                    ToastUtil.showCenterToast((int) R.string.post_comment_load_fail);
                }
            });
        }
    }

    public final void clickGame(Context context, SubGamesBean gamesBean, String url) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(gamesBean, "gamesBean");
        Intrinsics.checkParameterIsNotNull(url, "url");
        String adType = gamesBean.getAdType();
        if (adType != null && adType.hashCode() == 57 && adType.equals("9")) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            GameWebActivity.Companion companion = GameWebActivity.Companion;
            RechargeExGame rechargeExGame = new RechargeExGame(gamesBean.getAdBrandId(), gamesBean.getAdBrandName(), 0.0d);
            boolean z = true;
            if (gamesBean.getAdGameDirection() != 1) {
                z = false;
            }
            companion.startActivity(context, rechargeExGame, url, z);
            return;
        }
        clickGame(context, gamesBean);
    }

    public final void requestGameResponse(String type, String gameBrandId, String gameId, String str, final RequestGameResponseListener gameTokenListener) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(gameBrandId, "gameBrandId");
        Intrinsics.checkParameterIsNotNull(gameId, "gameId");
        Intrinsics.checkParameterIsNotNull(gameTokenListener, "gameTokenListener");
        if (Intrinsics.areEqual(type, GAME_BINGBING_TOKEN)) {
            ApiImplService.Companion.getApiImplService().requestGameBingBingToken(gameBrandId).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameBingBingToken>() { // from class: com.one.tomato.utils.GameUtils$requestGameResponse$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(GameBingBingToken gameBingBingToken) {
                    Intrinsics.checkParameterIsNotNull(gameBingBingToken, "gameBingBingToken");
                    GameUtils.RequestGameResponseListener.this.requestGameBingBingToken(gameBingBingToken);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable e) {
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    LogUtil.m3786e("点击游戏获取内容失败");
                    GameUtils.RequestGameResponseListener.this.requestFail();
                }
            });
        } else if (Intrinsics.areEqual(type, GAME_DASHENG_WEBAPP)) {
            ApiImplService.Companion.getApiImplService().requestGameDaShengWebapp(DBUtil.getMemberId(), gameId).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameDaShengWebapp>() { // from class: com.one.tomato.utils.GameUtils$requestGameResponse$2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(GameDaShengWebapp gameDaShengWebapp) {
                    Intrinsics.checkParameterIsNotNull(gameDaShengWebapp, "gameDaShengWebapp");
                    GameUtils.RequestGameResponseListener.this.requestGameDaShengWebapp(gameDaShengWebapp);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable e) {
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    LogUtil.m3786e("点击游戏获取内容失败");
                    GameUtils.RequestGameResponseListener.this.requestFail();
                }
            });
        } else if (!Intrinsics.areEqual(type, GAME_DASHENG_H5)) {
        } else {
            ApiImplService.Companion.getApiImplService().requestGameDaShengH5(DBUtil.getMemberId(), gameId, Intrinsics.areEqual("8", str) ? 2 : 1).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameDaShengH5>() { // from class: com.one.tomato.utils.GameUtils$requestGameResponse$3
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(GameDaShengH5 gameDaShengH5) {
                    Intrinsics.checkParameterIsNotNull(gameDaShengH5, "gameDaShengH5");
                    GameUtils.RequestGameResponseListener.this.requestGameDaShengH5(gameDaShengH5);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable e) {
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    LogUtil.m3786e("点击游戏获取内容失败");
                    GameUtils.RequestGameResponseListener.this.requestFail();
                }
            });
        }
    }

    public final ArrayList<SubGamesBean> getChessH5Game(boolean z) {
        ArrayList<SubGamesBean> arrayList = new ArrayList<>();
        String string = PreferencesUtil.getInstance().getString("chess_home_data");
        if (!TextUtils.isEmpty(string)) {
            ArrayList arrayList2 = (ArrayList) BaseApplication.getGson().fromJson(string, new TypeToken<ArrayList<ChessTypeBean>>() { // from class: com.one.tomato.utils.GameUtils$getChessH5Game$fromJson$1
            }.getType());
            if (!(arrayList2 == null || arrayList2.isEmpty())) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    ChessTypeBean it3 = (ChessTypeBean) it2.next();
                    Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                    if (it3.getChessId() > 3) {
                        ArrayList<ChessTypeSubBean> data = it3.getData();
                        if (!(data == null || data.isEmpty())) {
                            Iterator<ChessTypeSubBean> it4 = it3.getData().iterator();
                            while (it4.hasNext()) {
                                ChessTypeSubBean subBean = it4.next();
                                Intrinsics.checkExpressionValueIsNotNull(subBean, "subBean");
                                if (Intrinsics.areEqual(subBean.getAdType(), "7") || z) {
                                    arrayList.add(encapsulationSubGame(subBean));
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final SubGamesBean encapsulationSubGame(ChessTypeSubBean it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        SubGamesBean subGamesBean = new SubGamesBean();
        subGamesBean.setCreateTime(it2.getCreateTime());
        subGamesBean.setCategoryType(it2.getCategoryType());
        subGamesBean.setUpdateTime(it2.getUpdateTime());
        subGamesBean.setSubChannelId(it2.getSubChannelId());
        subGamesBean.setAdGameDirection(it2.getAdGameDirection());
        subGamesBean.setAdGameLink(it2.getAdGameLink());
        subGamesBean.setAdGameId(it2.getAdGameId());
        subGamesBean.setType(it2.getType());
        subGamesBean.setTerminal(it2.getTerminal());
        subGamesBean.setSubGames(it2.getSubGames());
        subGamesBean.setSpreadChannelNo(it2.getSpreadChannelNo());
        subGamesBean.setOpenType(it2.getOpenType());
        subGamesBean.setMaterialType(it2.getMaterialType());
        subGamesBean.setImageUrlSec(it2.getImageUrlSec());
        subGamesBean.setAdType(it2.getAdType());
        subGamesBean.setAdLinkType(it2.getAdLinkType());
        subGamesBean.setAdGameWidth(it2.getAdGameWidth());
        subGamesBean.setAdGameHeight(it2.getAdGameHeight());
        subGamesBean.setAdGameFlag(it2.getAdGameFlag());
        subGamesBean.setAdArticleContent(it2.getAdArticleContent());
        subGamesBean.setAdBrandId(it2.getAdBrandId());
        subGamesBean.setAdArticleName(it2.getAdArticleName());
        subGamesBean.setAdArticleAvatarSec(it2.getAdArticleAvatarSec());
        subGamesBean.setAdDesc(it2.getAdDesc());
        subGamesBean.setGameId(it2.getId());
        subGamesBean.setAdName(it2.getAdName());
        subGamesBean.setAdLogoType(it2.getAdLogoType());
        subGamesBean.setMemberId(it2.getMemberId());
        subGamesBean.setAdBrandName(it2.getAdBrandName());
        subGamesBean.setAdLink(it2.getAdLink());
        subGamesBean.setAppId(it2.getAppId());
        subGamesBean.setStatus(it2.getStatus());
        subGamesBean.setAdGameBGFishType(it2.getAdGameBGFishType());
        subGamesBean.setAdGameBGType(it2.getAdGameBGType());
        return subGamesBean;
    }
}
